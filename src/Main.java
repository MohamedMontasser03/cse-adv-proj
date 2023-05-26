package src;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import src.parsers.MDLParser;

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
        if (args.length < 1) {
            System.out.println("Error: No file specified");
            System.exit(1);
        }

        try {
            MDLParser parser = new MDLParser(args[0]);
            System.out.println(parser.getSystem());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        launch(args);
    }
}