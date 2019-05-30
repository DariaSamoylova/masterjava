package ru.javaops.masterjava.persist.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import one.util.streamex.IntStreamEx;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.BatchChunkSize;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.model.City;
import ru.javaops.masterjava.persist.model.Group;
import ru.javaops.masterjava.persist.model.User;

import java.util.List;
import java.util.stream.Collectors;

@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class UserDao implements AbstractDao {

    public User insert(User user) {
        if (user.isNew()) {
            int id = insertGeneratedId(user,user.getCity().getId());
            user.setId(id);
        } else {
            insertWitId(user,user.getCity().getId());
        }
        return user;
    }

    @SqlQuery("SELECT nextval('global_seq')")
    abstract int getNextVal();

    @Transaction
    public int getSeqAndSkip(int step) {
        int id = getNextVal();
        DBIProvider.getDBI().useHandle(h -> h.execute("ALTER SEQUENCE global_seq RESTART WITH " + (id + step)));
        return id;
    }

    @SqlUpdate("INSERT INTO users (full_name, email, flag, city_id) VALUES (:fullName, :email, CAST(:flag AS USER_FLAG),:city_id) ")
    @GetGeneratedKeys
    abstract int insertGeneratedId(@BindBean User user,@Bind int city_id);

    @SqlUpdate("INSERT INTO users (id, full_name, email, flag, city_id) VALUES (:id, :fullName, :email, CAST(:flag AS USER_FLAG),:city_id) ")
    abstract void insertWitId(@BindBean User user, @Bind int city_id);

    @SqlQuery("SELECT * FROM users ORDER BY full_name, email LIMIT :it")
    public abstract List<User> getWithLimit(@Bind int limit);

    //   http://stackoverflow.com/questions/13223820/postgresql-delete-all-content
    @SqlUpdate("TRUNCATE users")
    @Override
    public abstract void clean();

    //    https://habrahabr.ru/post/264281/
    @SqlBatch("INSERT INTO users (id, full_name, email, flag,city_id) VALUES (:id, :fullName, :email, CAST(:flag AS USER_FLAG),:city_id)" +
            "ON CONFLICT DO NOTHING")
//            "ON CONFLICT (email) DO UPDATE SET full_name=:fullName, flag=CAST(:flag AS USER_FLAG)")
    public abstract int[] insertBatch(@BindBean List<User> users, @BindBean List<City> cities, @BatchChunkSize int chunkSize);


    @SqlBatch("INSERT INTO users_groups ( user_id, group_id) VALUES (:user_id, :group_id)" +
            "ON CONFLICT DO NOTHING")
//            "ON CONFLICT (email) DO UPDATE SET full_name=:fullName, flag=CAST(:flag AS USER_FLAG)")
    public abstract int[] insertBatchWithGroups(@BindBean List<User> users, @BindBean List<Group> cities, @BatchChunkSize int chunkSize);


    public List<String> insertAndGetConflictEmails(List<User> users) {
        List<City> cities = users.stream().map(u->u.getCity()).collect(Collectors.toList());
        
        int[] result = insertBatch(users,cities, users.size());
        return IntStreamEx.range(0, users.size())
                .filter(i -> result[i] == 0)
                .mapToObj(index -> users.get(index).getEmail())
                .toList();
    }
}
