package ru.javaops.masterjava.service.mail.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
//import ru.javaops.masterjava.persist.dao.AbstractDao;
import ru.javaops.masterjava.service.mail.model.Email;

@RegisterMapperFactory(EntityMapperFactory.class)
public abstract  class EmailDao   {

    public Email insert(Email email) {
        if (email.isNew()) {
            int id = insertGeneratedId(email);
            email.setId(id);
        } else {
            insertWitId(email);
        }
        return email;
    }

    @SqlUpdate("INSERT INTO emails (email, sendTime, subject, body, result) VALUES (:email, :sendTime, :subject, :body, :result) ")
    @GetGeneratedKeys
    abstract int insertGeneratedId(@BindBean Email email);

    @SqlUpdate("INSERT INTO emails (id,email, sendTime, subject, body, result) VALUES (:id, :email, :sendTime, :subject, :body, :result) ")
    abstract void insertWitId(@BindBean  Email email);
}
                  /*

@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class UserDao implements AbstractDao {

    public User insert(User user) {
        if (user.isNew()) {
            int id = insertGeneratedId(user);
            user.setId(id);
        } else {
            insertWitId(user);
        }
        return user;
    }

    @SqlQuery("SELECT nextval('user_seq')")
    abstract int getNextVal();

    @Transaction
    public int getSeqAndSkip(int step) {
        int id = getNextVal();
        DBIProvider.getDBI().useHandle(h -> h.execute("ALTER SEQUENCE user_seq RESTART WITH " + (id + step)));
        return id;
    }

    @SqlUpdate("INSERT INTO users (full_name, email, flag, city_ref) VALUES (:fullName, :email, CAST(:flag AS USER_FLAG), :cityRef) ")
    @GetGeneratedKeys
    abstract int insertGeneratedId(@BindBean User user);

    @SqlUpdate("INSERT INTO users (id, full_name, email, flag, city_ref) VALUES (:id, :fullName, :email, CAST(:flag AS USER_FLAG), :cityRef) ")
    abstract void insertWitId(@BindBean User user);

    @SqlQuery("SELECT * FROM users ORDER BY full_name, email LIMIT :it")
    public abstract List<User> getWithLimit(@Bind int limit);

    //   http://stackoverflow.com/questions/13223820/postgresql-delete-all-content
    @SqlUpdate("TRUNCATE users CASCADE")
    @Override
    public abstract void clean();

    //    https://habrahabr.ru/post/264281/
    @SqlBatch("INSERT INTO users (id, full_name, email, flag, city_ref) VALUES (:id, :fullName, :email, CAST(:flag AS USER_FLAG), :cityRef)" +
            "ON CONFLICT DO NOTHING")
//            "ON CONFLICT (email) DO UPDATE SET full_name=:fullName, flag=CAST(:flag AS USER_FLAG)")
    public abstract int[] insertBatch(@BindBean List<User> users, @BatchChunkSize int chunkSize);


    public List<String> insertAndGetConflictEmails(List<User> users) {
        int[] result = insertBatch(users, users.size());
        return IntStreamEx.range(0, users.size())
                .filter(i -> result[i] == 0)
                .mapToObj(index -> users.get(index).getEmail())
                .toList();
    }
}
*/