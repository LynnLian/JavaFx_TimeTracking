import com.sun.deploy.util.FXLoader;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.input.DataFormat;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class Controller {

    @FXML
    private Button btnStart;

    @FXML
    private Button btnFinish;

    @FXML
    public TextField textFieldNote;

    @FXML
    private Label labelLocalTime;

//    @FXML
//    private Label labelLocalDate;


    private long recordId = -1;
    private DbHelper dbHelper;

    public void init() {
        dbHelper = new DbHelper("timerecord.db");
        Record initRecord = dbHelper.getRecordWithoutEndTime();

        if (initRecord != null) {
            recordId = initRecord.getId();
            if (recordId > 0) {
                openRecord(initRecord);
//                System.out.println("if loop in init");
//                btnStart.setDisable(true);
//                textFieldNote.setText(initRecord.getNote());
//                btnFinish.setDisable(false);
            }
        } else {
            closedRecord();
//            btnStart.setDisable(false);
//            btnFinish.setDisable(true);
        }

        localDateTime();


    }

    public void startCal(ActionEvent actionEvent) {


        System.out.println("Start to calculate time");
        //System.out.println("This is the mark to test Db");
        recordId = dbHelper.startRecord();
        Record openRecord = dbHelper.getRecordWithoutEndTime();
//        btnStart.setDisable(true);
//        btnFinish.setDisable(false);
        openRecord(openRecord);

    }

    public void finishCal(ActionEvent actionEvent) {

        System.out.println("finish button is clicked");
        dbHelper.endRecord(recordId);
        System.out.println("finishCal action insert the endTime");
        closedRecord();
//        btnStart.setDisable(false);
//        btnFinish.setDisable(true);
    }


//    public void updateNote(ActionEvent actionEvent) {
//
//        String note = textFieldNote.getText();
//        dbHelper.updateNote(note);
//
//
//    }

    //TODO: now I just implement it static. Later I will find out how to update the label automatically
    public void localDateTime() {
//        Timeline time = new Timeline(new )


//        LocalDate date = LocalDate.now();
//        String day = Integer.toString(date.getDayOfMonth());
//        String month = Integer.toString(date.getMonthValue());
//        String year = Integer.toString(date.getYear());
//
//        LocalDateTime localTime = LocalDateTime.now();
//        String hour = Integer.toString(localTime.getHour());
//        String min = Integer.toString(localTime.getMinute());
//
//        labelLocalTime.setText(hour + " : " + min);
//        labelLocalDate.setText(day + " / " + month + " / " + year);

        String time = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date(System.currentTimeMillis()));
        labelLocalTime.setText(time);


    }

    //The openRecord status should: 1. Disabel Start Button 2. Enabel Finish Button 3. Enable Note field & display the existed note
    public void openRecord(Record openRecord) {
        System.out.println("OpenRecord method start");
        textFieldNote.setDisable(false);
        btnStart.setDisable(true);
        textFieldNote.setText(openRecord.getNote());
        btnFinish.setDisable(false);

        textFieldNote.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("Mark in textFieldNote listener and the id is " + recordId);
                if(recordId > 0) {

                    dbHelper.updateNote(newValue);
                    System.out.println("Note is update because of change and the newValue is " + newValue);

                }





            }
        });

    }

    //The closedRecord status should: 1. Ensable Start Button 2. Disable Finish Button 3. Enable Note field & remove the text in text field
    public void closedRecord() {

        btnStart.setDisable(false);
        btnFinish.setDisable(true);
        textFieldNote.setText("");
        textFieldNote.setDisable(true);
        recordId = -1;
        System.out.println("closedRecord is end");

    }
}