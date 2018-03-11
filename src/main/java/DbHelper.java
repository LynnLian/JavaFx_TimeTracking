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

    private Connection connection(){
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return conn;
    }

    public void createTable() {

        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);

            stmt = conn.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS record (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
                    "startTime INTEGER NOT NULL," +
                    "endTime INTEGER NOT NULL," +
                    "note TEXT," +
                    "company Text," +
                    "username TEXT);";
            stmt.executeUpdate(sql);
            System.out.println("Sql to create table is executed.");


            stmt.close();
            conn.close();


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }


    }


    public void startRecord() {
        Connection conn;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);

            Long currentTime = (System.currentTimeMillis()) / 1000L;
            String sql = "INSERT into record(startTime) VALUES ('" + currentTime +
                    "');";


            stmt.executeUpdate(sql);
            System.out.println("Record is started.");

            stmt.close();
            conn.commit();
            conn.close();


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Inserts endTime
     *
     * @param id int
     */
    public void endRecord(int id) {
        Connection conn = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);

            stmt = conn.createStatement();

            Long currentTime = (System.currentTimeMillis()) / 1000L;

// Need to think about how to make the table name dynamic & the id;
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
//    public Record getRecordWithoutEndTime() {
//        Connection conn = null;
//        Statement stmt = null;
//        ResultSet res;
//
//
//        try {
//            Class.forName("org.sqlite.JDBC");
//            conn = DriverManager.getConnection(url);
//
//            stmt = conn.createStatement();
//
//
//            String sql = "SELECT id FROM record WHERE endTime IS NULL ";
//            res = stmt.executeQuery(sql);
//
//            //Think about how to get all the value in record
//            long id = res.getLong(0);
//            int startTime = res.getInt(1);
//            int endTime = res.getInt(2);
//
//
//
//
//        } catch (Exception e) {
//            System.out.println(e.getClass().getName() + " : " + e.getMessage());
//            System.exit(0);
//        }
//
//
//    }

    public void updateNote(int id) {

    }

}
