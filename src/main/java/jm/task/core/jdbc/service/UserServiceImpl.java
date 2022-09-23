package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.naming.Name;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoJDBCImpl();
    private final Connection connection = Util.getConnection();


    public void createUsersTable() {
        try {
            connection.setAutoCommit(false);
            userDao.createUsersTable();
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void dropUsersTable() throws SQLException {
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