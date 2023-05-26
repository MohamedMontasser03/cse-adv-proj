package src.renderers;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import src.models.MDLBlock;
import src.models.MDLSystem;
import src.utils.StringParsers;

public class MDLCanvas extends Canvas {
    private static double mouseDownX;
    private static double mouseDownY;

    private final MDLSystem system;
    private final GraphicsContext gc;
    private double width;
    private double height;
    private double widthMultiplier;
    private double heightMultiplier;
    private double zoomFactor;
    private double offsetX;
    private double offsetY;

    public MDLCanvas(MDLSystem system, double width, double height) {
        super(width, height);
        gc = getGraphicsContext2D();
        this.system = system;

        this.width = width;
        this.height = height;
        final int[] location = StringParsers.parseIntArray(this.system.getParameter("Location"));
        zoomFactor = Double.parseDouble(this.system.getParameter("ZoomFactor")) / 100;
        // zoomFactor = 1;
        widthMultiplier = (double) width / (location[2] - location[0]) * zoomFactor;
        heightMultiplier = (double) height / (location[3] - location[1]) * zoomFactor;
        registerPanningHandelers();
        // zoom to the center of the system
        offsetX = -width / 0.7;
        offsetY = -height / 2;
        render();
    }

    public void render() {
        MDLBlock[] blocks = system.getBlocks();

        for (MDLBlock block : blocks) {
            MDLBlockRenderer renderer = new MDLBlockRenderer(block);
            renderer.render(gc, widthMultiplier, heightMultiplier, offsetX, offsetY, zoomFactor);
        }
    }

    public void registerPanningHandelers() {
        setOnMousePressed((event) -> {
            if (!event.isPrimaryButtonDown())
                return;
            mouseDownX = event.getX();
            mouseDownY = event.getY();
        });

        setOnMouseDragged((event) -> {
            if (!event.isPrimaryButtonDown())
                return;
            gc.clearRect(0, 0, width, height);
            offsetX += event.getX() - mouseDownX;
            offsetY += event.getY() - mouseDownY;
            mouseDownX = event.getX();
            mouseDownY = event.getY();
            render();
        });
    }

    @Override
    public void resize(double width, double height) {
        // counter the offset caused by the resize
        offsetX -= (width - this.width) * 2;
        offsetY -= (height - this.height);

        this.width = width;
        this.height = height;
        setWidth(width);
        setHeight(height);
        gc.clearRect(0, 0, width, height);
        final int[] location = StringParsers.parseIntArray(this.system.getParameter("Location"));
        widthMultiplier = (double) width / (location[2] - location[0]) * zoomFactor;
        heightMultiplier = (double) height / (location[3] - location[1]) * zoomFactor;
        render();
    }
}
