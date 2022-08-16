package jm.task.core.jdbc.dao;

import com.mysql.cj.jdbc.ha.MultiHostMySQLConnection;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.*;
import java.util.stream.Stream;

public class UserDaoJDBCImpl implements UserDao {
    private static Util util = new Util();
    Connection conn = null;

    {
        try {
            conn = DriverManager.getConnection(Util.URL, Util.NAME, Util.PASSWORD);
        } catch (SQLException e) {
            System.out.println("conn = DriverManager.getConnection(Util.URL, Util.NAME, Util.PASSWORD): вызвал ошибку" + e);
        }

    }


    ;

//    static Connection connection = new Connection();//Util.runConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Statement statement = conn.createStatement()) {

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS TableIvan (id BIGINT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(50), Last_Name VARCHAR(50), age INT)");
            System.out.println("String SQL отработал");
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            System.out.println("try (Connection conn = DriverManager.getConnection(Util.URL, Util.NAME, Util.PASSWORD))");
        }
    }

    public void dropUsersTable() {
        try (Connection conn = DriverManager.getConnection(Util.URL, Util.NAME, Util.PASSWORD)) {
            Statement statement = conn.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS TableIvan");
            System.out.println("Таблица удалена");
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
            System.out.println("try (Connection conn = DriverManager.getConnection(Util.URL, Util.NAME, Util.PASSWORD))");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection conn = DriverManager.getConnection(Util.URL, Util.NAME, Util.PASSWORD)) {

            PreparedStatement preparedStatement =
                    conn.prepareStatement("INSERT INTO TableIvan (Name, Last_Name, age) VALUES (?, ?, ?)");
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
        try (Connection conn = DriverManager.getConnection(Util.URL, Util.NAME, Util.PASSWORD)) {
            PreparedStatement preparedStatement =
                    conn.prepareStatement("DELETE FROM TableIvan WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Пользователь id: " + id + " удалён");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> arrayListTableIvan = new ArrayList<>();

        try (
                Connection conn = DriverManager.getConnection(Util.URL, Util.NAME, Util.PASSWORD)) {
            ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM TableIvan");

            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));

                System.out.println(user);
                arrayListTableIvan.add(user);
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return arrayListTableIvan;
    }


    public void cleanUsersTable() {
        try (Connection conn = DriverManager.getConnection(Util.URL, Util.NAME, Util.PASSWORD)) {
            Statement statement = conn.createStatement();
            statement.executeUpdate("TRUNCATE TABLE TableIvan");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}