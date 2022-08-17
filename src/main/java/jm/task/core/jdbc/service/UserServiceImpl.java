package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import java.util.*;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoJDBCImpl();


    public void createUsersTable() {
        userDao.createUsersTable();
        System.out.println("Таблица создана");
    }


    public void dropUsersTable() {
        userDao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        userDao.removeUserById(id);
        System.out.println("Пользователь id: " + id + " удалён");
    }

    public List<User> getAllUsers() {
        List<User> allUsers = userDao.getAllUsers();
        for (User printuser : allUsers) {
            System.out.println(printuser);
        }
        return allUsers;
    }


    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }
}