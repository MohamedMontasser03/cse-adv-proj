package src.renderers;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import src.models.MDLBlock;
import src.renderers.blocks.Constant;
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

        int[] positions = StringParsers.parseIntArray(node.getParameter("Position"));
        int[] dimensions = new int[] { positions[2] - positions[0], positions[3] - positions[1] };
        int[] location2 = new int[] { (int) (positions[0] * widthMultiplier + offsetX),
                (int) (positions[1] * heightMultiplier + offsetY),
                (int) (dimensions[0] * widthMultiplier), (int) (dimensions[1] * heightMultiplier) };
        gc.strokeRect(location2[0], location2[1], location2[2], location2[3]);
        renderCenteredText(gc, block.getName(), location2[0] + location2[2] / 2,
                location2[1] + location2[3] + zoomFactor * 4, zoomFactor * 4);
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
        renderers.put("Constant", Constant.class);
    }
}
