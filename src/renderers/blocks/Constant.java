package src.renderers.blocks;

import javafx.scene.canvas.GraphicsContext;
import src.models.MDLBlock;
import src.renderers.MDLRenderer;
import src.utils.StringParsers;

public class Constant extends MDLRenderer {

    public Constant(MDLBlock node) {
        super(node);
    }

    @Override
    public void render(GraphicsContext gc, double widthMultiplier, double heightMultiplier, double offsetX,
            double offsetY, double zoomFactor) {
        int[] positions = StringParsers.parseIntArray(node.getParameter("Position"));
        int[] dimensions = new int[] { positions[2] - positions[0], positions[3] - positions[1] };
        int[] location2 = new int[] { (int) (positions[0] * widthMultiplier + offsetX),
                (int) (positions[1] * heightMultiplier + offsetY),
                (int) (dimensions[0] * widthMultiplier), (int) (dimensions[1] * heightMultiplier) };
        // get the dimensions of the text to center it
        renderCenteredText(gc, "1", location2[0] + location2[2] / 2, location2[1] + location2[3] / 2,
                gc.getFont().getSize() * zoomFactor);
    }

}
