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

    //TODO: add a label to show the current time

    private long recordId = -1;
    private DbHelper dbHelper;

    public void init() {
        dbHelper = new DbHelper("timerecord.db");
        Record initRecord = dbHelper.getRecordWithoutEndTime();

        if (initRecord != null) {
            recordId = initRecord.getId();
            if (recordId > 0) {
                System.out.println("if loop in init");
                btnStart.setDisable(true);
                textFieldNote.setText(initRecord.getNote());
                btnFinish.setDisable(false);
            }
        } else {
            btnStart.setDisable(false);
            btnFinish.setDisable(true);
        }
    }

    //TODO: To create a method for the state use(A method to init the function in open record state and closed record state
    public void startCal(ActionEvent actionEvent) {


        System.out.println("Start to calculate time");
        //System.out.println("This is the mark to test Db");
        recordId = dbHelper.startRecord();
        btnStart.setDisable(true);
        btnFinish.setDisable(false);

    }

    public void finishCal(ActionEvent actionEvent) {

        System.out.println("finish button is clicked");
        dbHelper.endRecord();
        btnStart.setDisable(false);
        btnFinish.setDisable(true);
    }

    //TODO: to remove the text in Note textfield when there is no open record (not sure this will be implement here but just write it down)
    //TODO: delete the update note button and make it save automatically
    public void updateNote(ActionEvent actionEvent) {

        String note = textFieldNote.getText();
        dbHelper.updateNote(note);


    }
}