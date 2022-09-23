package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.*;

import static org.hibernate.hql.internal.antlr.SqlTokenTypes.SELECT;


public class UserDaoJDBCImpl implements UserDao {

    private final Connection conn = Util.getConnection();

    private static final String CREATE = "CREATE TABLE IF NOT EXISTS TableIvan (id BIGINT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(50), Last_Name VARCHAR(50), age INT)";
    private static final String DROP = "DROP TABLE IF EXISTS TableIvan";
    private static String DELETE = "DELETE FROM TableIvan WHERE id = ?";
    private static String SAVE = "INSERT INTO TableIvan (Name, Last_Name, age) VALUES (?, ?, ?)";
    private static final String SELECTALL = "SELECT * FROM TableIvan";
    private static final String TRUNCATE = "TRUNCATE TABLE TableIvan";

    private static final String SELECTUSER = "SELECT * FROM newbd.TableIvan WHERE id = ?";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Statement statement = conn.createStatement()) {
            conn.setAutoCommit(false);
            statement.executeUpdate(CREATE);
            conn.commit();
        } catch (SQLException | NullPointerException e) {
            try {
                conn.rollback();
                System.out.println("Запущен rollback()");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Ошибка setAutoCommit(true)");
            }
        }
    }

    public void dropUsersTable() {
        try (Statement statement = conn.createStatement()) {
            conn.setAutoCommit(false);
            statement.executeUpdate(DROP);
            System.out.println("Таблица удалена");
            conn.commit();
        } catch (SQLException | NullPointerException e) {
            try {
                conn.rollback();
                System.out.println("Запущен rollback()");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Ошибка setAutoCommit(true)");
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            conn.setAutoCommit(false);
            try (PreparedStatement preparedStatement =
                         conn.prepareStatement(SAVE)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setByte(3, age);
                preparedStatement.executeUpdate();
                System.out.println("Пользователь: " + name + " " + lastName + " добавлен в базу данных");
                conn.commit();
            } catch (SQLException | NullPointerException e) {
                try {
                    conn.rollback();
                    System.out.println("Запущен rollback()");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } finally {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Ошибка setAutoCommit(true)");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            conn.setAutoCommit(false);
            try (PreparedStatement preparedStatement =
                         conn.prepareStatement(DELETE);
            ) {      /*DELETE FROM TableIvan WHERE id = ?*/
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
                conn.commit();
            } catch (SQLException | NullPointerException e) {
                try {
                    conn.rollback();
                    System.out.println("Запущен rollback()");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } finally {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Ошибка setAutoCommit(true)");
                }
            }
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
        } catch (SQLException | NullPointerException e) {
            try {
                conn.rollback();
                System.out.println("Запущен rollback()");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Ошибка setAutoCommit(true)");
            }
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