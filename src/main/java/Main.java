import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    //I am a comment

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("TimeTracking.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 715, 800);
        primaryStage.setTitle("Internship Diary");
        primaryStage.setScene(scene);

        Controller controller = loader.getController();
        controller.init();

        scene.getStylesheets().add(Controller.class.getResource("main.css").toExternalForm());

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
