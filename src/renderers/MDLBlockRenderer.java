package src.renderers;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import src.models.MDLBlock;
import src.renderers.blocks.Constant;
import src.renderers.blocks.Scope;
import src.renderers.blocks.UnitDelay;
import src.utils.StringParsers;

public class MDLBlockRenderer extends MDLRenderer {
    private static Map<String, Class<? extends MDLRenderer>> renderers = new HashMap<String, Class<? extends MDLRenderer>>();

    protected MDLBlockRenderer(MDLBlock node) {
        super(node);
        if (renderers.isEmpty())
            registerBlockRenderers();
    }

    @Override
    public void render(GraphicsContext gc, double widthMultiplier, double heightMultiplier, double offsetX,
            double offsetY, double zoomFactor) {
        MDLBlock block = (MDLBlock) node;
        int[] dimensions = getDimensions(block, widthMultiplier, offsetX, offsetY, heightMultiplier, zoomFactor);
        gc.strokeRect(dimensions[0], dimensions[1], dimensions[2], dimensions[3]);
        renderCenteredText(gc, block.getName(), dimensions[0] + dimensions[2] / 2,
                dimensions[1] + dimensions[3] + zoomFactor * 4, zoomFactor * 4);

        try {
            Class<? extends MDLRenderer> renderer = renderers.get(block.getBlockType());
            renderer.getConstructor(MDLBlock.class).newInstance(block).render(gc, widthMultiplier,
                    heightMultiplier,
                    offsetX, offsetY, zoomFactor);
        } catch (Exception e) {
            if (renderers.get(block.getBlockType()) == null)
                return;
            System.out.println("Error: Failed to render block " + block.getName());
            System.out.println(e.getMessage());
        }
    }

    private static void registerBlockRenderers() {
        renderers.put("Scope", Scope.class);
        renderers.put("Constant", Constant.class);
        renderers.put("UnitDelay", UnitDelay.class);
    }

    public static int[] getDimensions(MDLBlock block, double widthMultiplier, double offsetX,
            double offsetY, double heightMultiplier, double zoomFactor) {
        int[] positions = StringParsers.parseIntArray(block.getParameter("Position"));
        int[] dimensions = new int[] { positions[2] - positions[0], positions[3] - positions[1] };
        int[] location2 = new int[] { (int) (positions[0] * widthMultiplier + offsetX),
                (int) (positions[1] * heightMultiplier + offsetY),
                (int) (dimensions[0] * widthMultiplier), (int) (dimensions[1] * heightMultiplier) };
        return location2;
    }
}
