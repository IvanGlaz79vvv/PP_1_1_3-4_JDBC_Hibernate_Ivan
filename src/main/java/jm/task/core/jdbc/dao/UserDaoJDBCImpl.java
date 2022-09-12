package jm.task.core.jdbc.dao;

import com.mysql.cj.jdbc.exceptions.MySQLTransactionRollbackException;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.*;


public class UserDaoJDBCImpl implements UserDao {

    private final Connection conn = Util.getConnection();

    private static final String CREATE = "CREATE TABLE IF NOT EXISTS TableIvan (id BIGINT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(50), Last_Name VARCHAR(50), age INT)";
    private static final String DROP = "DROP TABLE IF EXISTS TableIvan";
    private static String DELETE = "DELETE FROM TableIvan WHERE id = ?";
    private static String SAVE = "INSERT INTO TableIvan (Name, Last_Name, age) VALUES (?, ?, ?)";
    private static final String SELECTALL = "SELECT * FROM TableIvan";
    private static final String TRUNCATE = "TRUNCATE TABLE TableIvan";


    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Statement statement = conn.createStatement()) {
//            conn.setAutoCommit(false);
            statement.executeUpdate(CREATE);
        } catch (SQLException e) {
//            conn.rollback();
            System.out.println("Ошибка SQLException");
        }
//        conn.setAutoCommit(true);
    }

    public void dropUsersTable() throws SQLException {
        try (Statement statement = conn.createStatement()) {

            statement.executeUpdate(DROP);
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            conn.rollback();
            System.out.println("Ошибка conn.rollback();");
        }
        conn.setAutoCommit(true);
    }


    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement =
                     conn.prepareStatement(SAVE)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("Пользователь: " + name + " " + lastName + " добавлен в базу данных");
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            System.out.println("try (Connection conn = DriverManager.getConnection(Util.URL, Util.NAME, Util.PASSWORD))");
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement =
                     conn.prepareStatement(DELETE);) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> arrayListTableIvan = new ArrayList<>();
        try (ResultSet resultSet = conn.createStatement().executeQuery(SELECTALL)) {
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));

                arrayListTableIvan.add(user);
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return arrayListTableIvan;
    }

    public void cleanUsersTable() {
        try (Statement statement = conn.createStatement()) {

            statement.executeUpdate(TRUNCATE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}