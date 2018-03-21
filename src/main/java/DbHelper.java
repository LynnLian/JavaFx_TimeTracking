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
            System.out.println("DB is connected.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return conn;
    }

    private void createTable() {


        try (Connection conn = connection(); Statement stmt = conn.createStatement()) {


            String sql = "CREATE TABLE IF NOT EXISTS record (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                    "startTime INTEGER NOT NULL," +
                    "endTime INTEGER," +
                    "note TEXT," +
                    "company TEXT," +
                    "username TEXT);";
            stmt.executeUpdate(sql);


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }


    }


    public long startRecord() {

        long id = -1;

        try (Connection conn = connection();
             Statement stmt = conn.createStatement()) {

            Long currentTime = (System.currentTimeMillis()) / 1000L;
            String sql = "INSERT into record(startTime) VALUES ('" + currentTime +
                    "');";
            System.out.println("Inserted startTime");

            stmt.executeUpdate(sql);

            id = getRecordWithoutEndTime().getId();

            System.out.println("Record is started and the id is " + id);


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return id;
    }


    public void endRecord(long id) {

        try (Connection conn = connection(); Statement stmt = conn.createStatement();) {
            System.out.println("test endRecord");


            Long currentTime = (System.currentTimeMillis()) / 1000L;
            System.out.println("endRecord connection is build & the id is " + id);

            //long id = getRecordWithoutEndTime().getId();


            String sql = "UPDATE record SET endTime = '" + currentTime + "'WHERE id =" + id;
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }

    }


    public Record getRecordWithoutEndTime() {

        System.out.println("Running getRecordWithoutEndTime method");
        ResultSet res;


        Record recordWithoutEndTime = null;
        try (Connection conn = connection(); Statement stmt = conn.createStatement();) {


            String sql = "SELECT * FROM record WHERE endTime IS NULL ";
            res = stmt.executeQuery(sql);

            if (res.next()) {
                long id = res.getLong(1);
                int startTime = res.getInt(2);
                int endTime = res.getInt(3);
                String note = res.getString(4);
                String company = res.getString(5);
                String username = res.getString(6);

                recordWithoutEndTime = new Record(id, startTime, endTime, note, company, username);

            }


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + " : " + e.getMessage());
//            System.exit(0);
        }

        return recordWithoutEndTime;


    }

    public void updateNote(String note, long id) {
        if (id > 0) {
            try (Connection conn = connection(); Statement stmt = conn.createStatement()) {

//            long id = getRecordWithoutEndTime().getId();

                String sql = "UPDATE record SET note = '" + note + "'WHERE id =" + id;
                stmt.executeUpdate(sql);

            } catch (Exception e) {
                System.out.println(e.getClass().getName() + " : " + e.getMessage());
                System.out.println("It is me!");
            }
        }
    }

}
