package ru.javaops.masterjava.persist.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import one.util.streamex.IntStreamEx;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.BatchChunkSize;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.model.Group;
import ru.javaops.masterjava.persist.model.User;

import java.util.List;

@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class GroupDao implements AbstractDao {

    public Group insert(Group group) {
        if (group.isNew()) {
            int id = insertGeneratedId(group,group.getProject().getId());
            group.setId(id);
        } else {
            insertWitId(group,group.getProject().getId());
        }
        return group;
    }

    @SqlQuery("SELECT nextval('global_seq')")
    abstract int getNextVal();

    @Transaction
    public int getSeqAndSkip(int step) {
        int id = getNextVal();
        DBIProvider.getDBI().useHandle(h -> h.execute("ALTER SEQUENCE global_seq RESTART WITH " + (id + step)));
        return id;
    }

    @SqlUpdate("INSERT INTO groups (name, groupType, project_id) VALUES (:name,   CAST(:groupType AS group_type),:project_id ) ")
    @GetGeneratedKeys
    abstract int insertGeneratedId(@BindBean Group group, @Bind int project_id);

    @SqlUpdate("INSERT INTO groups (id, name, groupType, project_id) VALUES (:name,   CAST(:groupType AS group_type),:project_id ) ")
    abstract void insertWitId(@BindBean Group group, @Bind int project_id);

    @SqlQuery("SELECT * FROM groups ORDER BY name LIMIT :it")
    public abstract List<Group> getWithLimit(@Bind int limit);

    //   http://stackoverflow.com/questions/13223820/postgresql-delete-all-content
    @SqlUpdate("TRUNCATE groups")
    @Override
    public abstract void clean();

    //    https://habrahabr.ru/post/264281/
    @SqlBatch("INSERT INTO groups (id,  name, groupType, project_id) VALUES (:name,   CAST(:groupType AS group_type),:project_id )" +
            "ON CONFLICT DO NOTHING")
//            "ON CONFLICT (email) DO UPDATE SET full_name=:fullName, flag=CAST(:flag AS USER_FLAG)")
    public abstract int[] insertBatch(@BindBean List<Group> groups,  @BindBean  List<Integer> project_id, @BatchChunkSize int chunkSize);


//    public List<String> insertAndGetConflictEmails(List<Group> users) {
//        users.stream().map((s) -> s.g).collect(Collectors.toList())
//        int[] result = insertBatch(users, users.stream().users.size());
//
//        return IntStreamEx.range(0, users.size())
//                .filter(i -> result[i] == 0)
//                .mapToObj(index -> users.get(index).getEmail())
//                .toList();
//    }
}
