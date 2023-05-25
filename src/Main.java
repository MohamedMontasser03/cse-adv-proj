package src;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    final static String VERSION = "0.0.1";
    final static String TITLE = "Simulink Viewer";
    final static String ICON = "file:.\\res\\favicon.png";

    @Override
    public void start(Stage stage) {
        stage.setTitle(TITLE);

        try {
            stage.getIcons().add(new Image(ICON));
        } catch (Exception e) {
            System.out.println("Error: Failed to load icon");
        }

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}