package project;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Transaction {

    public static String select(int count, Connection connection) {
        StringBuilder selectData = new StringBuilder();
        boolean flag = false;
        for (int i = 0; i < count; i++) {
            long start = System.nanoTime();
            do {
                try (Statement statement = connection.createStatement()) {
                    statement.executeQuery("SELECT * FROM insurance where one_day_price = 200");
                    flag = false;
                } catch (SQLException e) {
                    if (e.getSQLState().equals("40001")) {
                        flag = true;
                    }
                    else e.printStackTrace();
                }
            } while (flag);
            long end = System.nanoTime();
            selectData.append("SELECT ").append(end - start).append("\n");
        }
        closeConnection(connection);
        return selectData.toString();
    }

    public static String update(int count, Connection connection) {
        StringBuilder updateData = new StringBuilder();
        boolean flag = false;
        for (int i = 0; i < count; i++) {
            long start = System.nanoTime();
            do {
                try (Statement statement = connection.createStatement()) {
                    statement.executeUpdate(
                            "UPDATE insurance set one_day_price = 201 where one_day_price = 200");
                    flag = false;
                } catch (SQLException e) {
                    if (e.getSQLState().equals("40001")) {
                        flag = true;
                    }
                    else e.printStackTrace();
                }
            } while (flag);
            long end = System.nanoTime();
            updateData.append("UPDATE ").append(end - start).append("\n");
        }
        closeConnection(connection);
        return updateData.toString();
    }

    public static String insert(int count, Connection connection) {
        StringBuilder insertData = new StringBuilder();
        boolean flag = false;
        for (int i = 0; i < count; i++) {
            long start = System.nanoTime();
            String sql = "INSERT INTO insurance (name, one_day_price)" +
                    " VALUES ('%s', 200)";
            String name = "Test" + i;
            do {try (Statement statement = connection.createStatement()) {
                statement.execute(String.format(sql, name));
                flag = false;
            } catch (SQLException e) {
                if (e.getSQLState().equals("40001")) {
                    flag = true;
                }
                else e.printStackTrace();
            }
        } while (flag);
            long end = System.nanoTime();
            insertData.append("INSERT ").append(end - start).append("\n");
        }
        closeConnection(connection);
        return insertData.toString();
    }

    private static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
