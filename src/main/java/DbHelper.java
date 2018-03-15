import jdk.nashorn.internal.runtime.ECMAException;

import java.sql.*;

public class DbHelper {
    private String url;

    public DbHelper(String dbName) {
        url = "jdbc:sqlite:" + dbName;
        createTable();
    }


    private Connection connection() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("DB is connected.");
        return conn;
    }

    public void createTable() {


        try (Connection conn = connection(); Statement stmt = conn.createStatement()) {


            String sql = "CREATE TABLE IF NOT EXISTS record (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                    "startTime INTEGER NOT NULL," +
                    "endTime INTEGER," +
                    "note TEXT," +
                    "company Text," +
                    "username TEXT);";
            stmt.executeUpdate(sql);


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }


    }


    public void startRecord() {

        try (Connection conn = connection(); Statement stmt = conn.createStatement();) {

            Long currentTime = (System.currentTimeMillis()) / 1000L;
            String sql = "INSERT into record(startTime) VALUES ('" + currentTime +
                    "');";


            stmt.executeUpdate(sql);
            System.out.println("Record is started.");


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }


    public void endRecord() {

        try (Connection conn = connection(); Statement stmt = conn.createStatement();) {


            Long currentTime = (System.currentTimeMillis()) / 1000L;
            System.out.println("endRecord connection is build");

            long id = getRecordWithoutEndTime().getId();
            System.out.println("The id is " + id);

            String sql = "UPDATE record SET endTime = '" + currentTime + "'WHERE id =" + id;
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            System.out.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }

    }


    public Record getRecordWithoutEndTime() {

        System.out.println("Running getRecordWithoutEndTime method");
        ResultSet res;


        Record recodWithoutEndTime = null;
        try (Connection conn = connection(); Statement stmt = conn.createStatement();) {


            String sql = "SELECT id FROM record WHERE endTime IS NULL ";
            res = stmt.executeQuery(sql);
            System.out.println("Sql to search id whose endTime is null");

            long id = res.getLong(1);
            System.out.println("The id which endTime is null is " + id);

            String sqlToGetRecord = "SELECT * FROM record WHERE id IS " + id;
            res = stmt.executeQuery(sqlToGetRecord);


            id = res.getLong(1);
            int startTime = res.getInt(2);
            int endTime = res.getInt(3);
            String note = res.getString(4);
            String company = res.getString(5);
            String username = res.getString(6);

            recodWithoutEndTime = new Record(id, startTime, endTime, note, company, username);


        } catch (Exception e) {
            System.out.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }

        return recodWithoutEndTime;


    }

    public void updateNote(String note) {

        try (Connection conn = connection(); Statement stmt = conn.createStatement()) {

            long id = getRecordWithoutEndTime().getId();

            String sql = "UPDATE record SET note = '" + note + "'WHERE id =" + id;
            stmt.executeUpdate(sql);


        } catch (Exception e) {
            System.out.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }

    }

}
