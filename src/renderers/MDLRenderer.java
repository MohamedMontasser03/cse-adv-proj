package src.renderers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import src.models.MDLNode;

public abstract class MDLRenderer {
    protected final MDLNode node;

    protected MDLRenderer(MDLNode node) {
        this.node = node;
    }

    public abstract void render(GraphicsContext gc, double widthMultiplier, double heightMultiplier, double offsetX,
            double offsetY, double zoomFactor);

    protected void renderCenteredText(GraphicsContext gc, String text, double x, double y, double size) {
        Text textNode = new Text(text);
        Font newFont = new Font(size);
        textNode.setFont(newFont);
        final double width = textNode.getLayoutBounds().getWidth();
        final double height = textNode.getLayoutBounds().getHeight();
        Font originalFont = gc.getFont();
        gc.setFont(newFont);
        gc.fillText(text, x - width / 2, y + height / 4);
        gc.setFont(originalFont);
    }
}
