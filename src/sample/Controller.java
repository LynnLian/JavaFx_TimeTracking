package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.awt.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class Controller {

    @FXML
    private Button btnStart;

    @FXML
    private Button btnFinish;

    @FXML
    private TextField textFieldNote;

    long recordid = 0;

    public void startCal(ActionEvent actionEvent) {
        System.out.println("Start to calculate time");
        addStartRecord();
        btnStart.setDisable(true);


    }

    public void addStartRecord() {
        Connection c = null;
        Statement stmt = null;

        try {
            //connect db. If it is not exist it will create one
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:timeRecord.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();

            //create table if not exists
            System.out.println("create table sql run");
            String sql = "CREATE TABLE IF NOT EXISTS record (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, startTime REAL NOT NULL , endTime REAL NOT NULL , note TEXT, company TEXT, username TEXT);";
            stmt.executeUpdate(sql);

            long currentTime = (System.currentTimeMillis()) / 1000L;
            System.out.println("current starttime is" + currentTime);

            sql = "insert into record (startTime,endTime) values ("
                    + "'" + currentTime + "'" + ","
                    + "'" + currentTime + "'" + ");";
            stmt.executeUpdate(sql);

            //To get the insert id for finishtime recording
            ResultSet lastInsertId = stmt.getGeneratedKeys();
            recordid = lastInsertId.getLong(1);
            System.out.println("The latest inserted id is " + recordid);

            System.out.println("created record successful");

            stmt.close();
            c.commit();
            c.close();


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("StartRecords created successfully");
    }

    public void finishCal(ActionEvent actionEvent) {
        System.out.println("finish button is clicked");
        addFinishRecord();
        btnStart.setDisable(false);
    }

    public void addFinishRecord() {
        Connection c = null;
        Statement stmt = null;

        try {
            //connect db. If it is not exist it will create one
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:timeRecord.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();


            long currentTime = (System.currentTimeMillis()) / 1000L;
            System.out.println("current endtime is" + currentTime);

            //Update the endTime
            String sql = "UPDATE record SET endTime = " + currentTime + " "
                    + "WHERE id =" + recordid;
            stmt.executeUpdate(sql);
            System.out.println("created finishrecord successful");

            stmt.close();
            c.commit();
            c.close();


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("FinishRecords created successfully");
    }

    public void inputNote(ActionEvent actionEvent) {
        textFieldNote.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!textFieldNote.isFocused()) {
                    addNote();

                }
            }
        });


    }

    public void addNote() {
        Connection c = null;
        Statement statement = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:timeRecord.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            statement = c.createStatement();

            String sql = "UPDATE record SET note= " + "'" + textFieldNote.getText() + "'" + "WHERE id= " + recordid;
            statement.executeUpdate(sql);

            statement.close();
            c.commit();
            c.close();


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
            System.out.println("insert note successful");


        }
        System.out.println("Note is inserted successfully");

    }
}