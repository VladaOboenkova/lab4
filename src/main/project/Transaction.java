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
                    statement.executeQuery("SELECT * FROM hotel where id_city = 10");
                    flag = false;
                } catch (SQLException e) {
                    if (e.getSQLState().equals("40001")) {
                        flag = true;
                    }
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
                    statement.executeQuery(
                            "UPDATE hotel set stars = 5 where id_city = 10");
                    flag = false;
                } catch (SQLException e) {
                    if (e.getSQLState().equals("40001")) {
                        flag = true;
                    }
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
            do {try (Statement statement = connection.createStatement()) {
                statement.executeQuery(
                        "INSERT INTO hotel (name, id_city, stars)" +
                                " VALUES ('TestHotel', 10, 2)");
                flag = false;
                System.out.println("Insert worked!!!!!!!!");
            } catch (SQLException e) {
                if (e.getSQLState().equals("40001")) {
                    flag = true;
                }
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
