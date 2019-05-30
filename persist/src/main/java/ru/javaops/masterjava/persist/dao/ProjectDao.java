package ru.javaops.masterjava.persist.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import one.util.streamex.IntStreamEx;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.BatchChunkSize;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.model.Project;
import ru.javaops.masterjava.persist.model.User;

import java.util.List;

@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class ProjectDao implements AbstractDao {

    public Project insert(Project project) {
        if (project.isNew()) {
            int id = insertGeneratedId(project);
            project.setId(id);
        } else {
            insertWitId(project);
        }
        return project;
    }

    @SqlQuery("SELECT nextval('global_seq')")
    abstract int getNextVal();

    @Transaction
    public int getSeqAndSkip(int step) {
        int id = getNextVal();
        DBIProvider.getDBI().useHandle(h -> h.execute("ALTER SEQUENCE global_seq RESTART WITH " + (id + step)));
        return id;
    }

    @SqlUpdate("INSERT INTO projects (name, description) VALUES (:name, :description) ")
    @GetGeneratedKeys
    abstract int insertGeneratedId(@BindBean Project project);

    @SqlUpdate("INSERT INTO projects (id, name, description) VALUES (:id, name, description) ")
    abstract void insertWitId(@BindBean Project project);

    @SqlQuery("SELECT * FROM projects ORDER BY name, description LIMIT :it")
    public abstract List<Project> getWithLimit(@Bind int limit);

    //   http://stackoverflow.com/questions/13223820/postgresql-delete-all-content
    @SqlUpdate("TRUNCATE projects")
    @Override
    public abstract void clean();

    //    https://habrahabr.ru/post/264281/
    @SqlBatch("INSERT INTO projects (id, name, description) VALUES (:id, name, description)" +
            "ON CONFLICT DO NOTHING")
//            "ON CONFLICT (email) DO UPDATE SET full_name=:fullName, flag=CAST(:flag AS USER_FLAG)")
    public abstract int[] insertBatch(@BindBean List<Project> projects, @BatchChunkSize int chunkSize);

//
//    public List<String> insertAndGetConflictEmails(List<Project> projects) {
//        int[] result = insertBatch(projects, projects.size());
//        return IntStreamEx.range(0, users.size())
//                .filter(i -> result[i] == 0)
//                .mapToObj(index -> users.get(index).getEmail())
//                .toList();
//    }
}
