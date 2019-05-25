package ru.javaops.masterjava.upload;

import org.slf4j.Logger;
import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.dao.UserDao;
import ru.javaops.masterjava.persist.model.User;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class UploadUsers {
    private static final Logger log = getLogger(UploadUsers.class);
    public static void saveUsers(List<User> users){
        UserDao userDao = DBIProvider.getDao(UserDao.class);
        int[] rows=userDao.insertUsers(users,100);
        System.out.println("rows="+rows.length);
        log.info("row count="+rows.length);
    }
}
