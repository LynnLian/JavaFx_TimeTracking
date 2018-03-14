import java.sql.*;

public class DbHelper {
    private String url;

    public DbHelper(String dbName) {
        url = "jdbc:sqlite:" + dbName;
        createTable();


    }

//    public void connectDB() {
//        Connection conn = null;
//
//        try {
//            Class.forName("org.sqlite.JDBC");
//            conn = DriverManager.getConnection(url);
//
//            System.out.println("DB is connected successfully.");
//
//            conn.close();
//
//
//        } catch (Exception e) {
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
//        }
//
//
//    }

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


        try (Connection conn = connection(); Statement stmt = conn.createStatement();) {
//            Class.forName("org.sqlite.JDBC");
//            conn = DriverManager.getConnection(url);


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
//        Connection conn;
//        Statement stmt = null;

        try (Connection conn = connection(); Statement stmt = conn.createStatement();) {
//            Class.forName("org.sqlite.JDBC");
//            conn = DriverManager.getConnection(url);
//            conn.setAutoCommit(false);

            Long currentTime = (System.currentTimeMillis()) / 1000L;
            String sql = "INSERT into record(startTime) VALUES ('" + currentTime +
                    "');";


            stmt.executeUpdate(sql);
            System.out.println("Record is started.");

//            stmt.close();
//            conn.commit();
//            conn.close();


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }


    public void endRecord() {
//        Connection conn = null;
//        Statement stmt = null;

        try (Connection conn = connection(); Statement stmt = conn.createStatement();) {
//            Class.forName("org.sqlite.JDBC");
//            conn = DriverManager.getConnection(url);
//
//            stmt = conn.createStatement();

            Long currentTime = (System.currentTimeMillis()) / 1000L;

// Maybe get the id from method getRecordWithoutEndTime
            long id = getRecordWithoutEndTime().getId();
            System.out.println("The id is " + id);

            String sql = "UPDATE record SET endTime = '" + currentTime + "'WHERE id =" + id;
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            System.out.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }

    }

    /**
     * @return Record
     */
    public Record getRecordWithoutEndTime() {
//        Connection conn = null;
//        Statement stmt = null;
        ResultSet res;


        Record recodWithoutEndTime = null;
        try (Connection conn = connection(); Statement stmt = conn.createStatement();) {
//            Class.forName("org.sqlite.JDBC");
//            conn = DriverManager.getConnection(url);
//
//            stmt = conn.createStatement();


            String sql = "SELECT id FROM record WHERE endTime IS NULL ";
            res = stmt.executeQuery(sql);
            System.out.println("Sql to search id whose endTime is null");

            //Think about how to get all the value in record
            long id = res.getLong(0);

            String sqlToGetRecord = "SELECT * FROM record WHERE id IS " + id;
            res = stmt.executeQuery(sqlToGetRecord);


            id = res.getLong(0);
            int startTime = res.getInt(1);
            int endTime = res.getInt(2);
            String note = res.getString(3);
            String company = res.getString(4);
            String username = res.getString(5);

            recodWithoutEndTime = new Record(id, startTime, endTime, note, company, username);

            System.out.println("The record is " + recodWithoutEndTime.toString());


        } catch (Exception e) {
            System.out.println(e.getClass().getName() + " : " + e.getMessage());
            System.exit(0);
        }

        return recodWithoutEndTime;


    }

    public void updateNote(int id) {

    }

}
