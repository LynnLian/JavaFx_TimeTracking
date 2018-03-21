import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ClockThread extends Thread {
    Label labelLocalTIme;

    public ClockThread(Label labelLocalTime) {
        this.labelLocalTIme = labelLocalTime;
    }

    @Override
    public void run() {

//        int count = 0;

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        while (true) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    String time = dateFormat.format(new Date());
                    labelLocalTIme.setText(time);

//            System.out.println(count);
//            count++;

                }
            });

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
