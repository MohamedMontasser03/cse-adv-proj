package src.renderers;

import javafx.scene.canvas.GraphicsContext;
import src.models.MDLNode;

public abstract class MDLRenderer {
    protected final MDLNode node;

    protected MDLRenderer(MDLNode node) {
        this.node = node;
    }

    public abstract void render(GraphicsContext gc, double widthMultiplier, double heightMultiplier, double offsetX,
            double offsetY, double zoomFactor);
}
