package src.renderers.blocks;

import javafx.scene.canvas.GraphicsContext;
import src.models.MDLBlock;
import src.renderers.MDLBlockRenderer;
import src.renderers.MDLRenderer;

public class Scope extends MDLRenderer {

    public Scope(MDLBlock node) {
        super(node);
    }

    @Override
    public void render(GraphicsContext gc, double widthMultiplier, double heightMultiplier, double offsetX,
            double offsetY, double zoomFactor) {
        MDLBlock block = (MDLBlock) node;
        int[] dimensions = MDLBlockRenderer.getDimensions(block, widthMultiplier, offsetX, offsetY, heightMultiplier,
                zoomFactor);
        double xOffset = 2 * zoomFactor;
        double yOffset = 2 * zoomFactor;
        double height = dimensions[3] / 4;
        gc.strokeRect(dimensions[0] + xOffset, dimensions[1] + yOffset, dimensions[2] - xOffset * 2, height);
    }

}
