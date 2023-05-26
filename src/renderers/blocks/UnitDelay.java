package src.renderers.blocks;

import javafx.scene.canvas.GraphicsContext;
import src.models.MDLBlock;
import src.renderers.MDLBlockRenderer;
import src.renderers.MDLRenderer;

public class UnitDelay extends MDLRenderer {

    public UnitDelay(MDLBlock node) {
        super(node);
    }

    @Override
    public void render(GraphicsContext gc, double widthMultiplier, double heightMultiplier, double offsetX,
            double offsetY, double zoomFactor) {
        MDLBlock block = (MDLBlock) node;
        int[] dimensions = MDLBlockRenderer.getDimensions(block, widthMultiplier, offsetX, offsetY, heightMultiplier,
                zoomFactor);
        // get the dimensions of the text to center it
        renderCenteredText(gc, "1/z", dimensions[0] + dimensions[2] / 2, dimensions[1] + dimensions[3] / 2,
                gc.getFont().getSize() * zoomFactor / 2);
    }

}
