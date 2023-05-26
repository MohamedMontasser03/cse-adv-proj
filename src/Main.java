package src;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import src.models.MDLSystem;
import src.parsers.MDLParser;
import src.renderers.MDLCanvas;

public class Main extends Application {
    final static String VERSION = "0.0.1";
    final static String TITLE = "Simulink Viewer";
    final static String ICON = "file:.\\res\\favicon.png";

    private static int width = 800;
    private static int height = 600;
    private static MDLSystem system;
    private static MDLCanvas canvas;

    @Override
    public void start(Stage stage) {
        stage.setTitle(TITLE);

        try {
            stage.getIcons().add(new Image(ICON));
        } catch (Exception e) {
            System.out.println("Error: Failed to load icon");
        }

        Pane root = new Pane();
        canvas = new MDLCanvas(system, width, height);

        root.getChildren().add(canvas);
        final Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();
        registerResizeHandlers(stage);
    }

    private void registerResizeHandlers(Stage stage) {
        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            width = newVal.intValue();
            canvas.resize(width, height);
        });
        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            height = newVal.intValue();
            canvas.resize(width, height);
        });
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Error: No file specified");
            System.exit(1);
        }

        try {
            MDLParser parser = new MDLParser(args[0]);
            system = parser.getSystem();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
        launch(args);
    }
}