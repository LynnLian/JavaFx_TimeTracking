import com.sun.deploy.util.FXLoader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.awt.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class Controller {

    @FXML
    private Button btnStart;

    @FXML
    private Button btnFinish;

    @FXML
    public TextField textFieldNote;

    String text = "text";

    // Need to think about how to show the existed note dynamic
    public void setTextFieldNote() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TimeTracking.fxml"));
        fxmlLoader.getNamespace().put("text",text);
    }


    long recordid = 0;



    public void startCal(ActionEvent actionEvent) {
        System.out.println("Start to calculate time");

        DbHelper newRecord = new DbHelper("timerecord.db");
        //System.out.println("This is the mark to test Db");
        newRecord.startRecord();

    }

    public void finishCal(ActionEvent actionEvent) {
        System.out.println("finish button is clicked");
        DbHelper startedRecord = new DbHelper("timerecord.db");
        startedRecord.endRecord();
//        btnStart.setDisable(false);
    }

    public void updateNote(ActionEvent actionEvent) {
        String note = textFieldNote.getText();
        DbHelper noteRecord = new DbHelper("timerecord.db");

        noteRecord.updateNote(note);



    }
}