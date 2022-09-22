package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    public static final String URL = "jdbc:mysql://localhost:3306/newbd";
    public static final String NAME = "root";
    public static final String PASSWORD = "root";

    static Connection connection = null;

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Ошибка подключения");
            e.printStackTrace();
        }
        return connection;
    }
}
