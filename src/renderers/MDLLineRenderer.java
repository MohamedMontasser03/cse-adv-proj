package src.renderers;

import java.util.Vector;

import javafx.scene.canvas.GraphicsContext;
import src.Main;
import src.models.MDLBlock;
import src.models.MDLLine;
import src.models.MDLLine.MDLBranch;
import src.utils.StringParsers;

public class MDLLineRenderer extends MDLRenderer {
    protected final MDLLine node;

    public MDLLineRenderer(MDLLine node) {
        super(node);
        this.node = node;
    }

    @Override
    public void render(GraphicsContext gc, double widthMultiplier, double heightMultiplier, double offsetX,
            double offsetY, double zoomFactor) {
        final MDLBlock sourceBlock = Main.system.getBlockByID(node.getSourceID());
        int[] srcDim = MDLBlockRenderer.getDimensions(sourceBlock, widthMultiplier, offsetX, offsetY, heightMultiplier,
                zoomFactor);

        final MDLBlock destinationBlock = Main.system.getBlockByID(node.getDistID());
        int[] dstDim = MDLBlockRenderer.getDimensions(destinationBlock, widthMultiplier, offsetX, offsetY,
                heightMultiplier, zoomFactor);
        String ports = destinationBlock.getParameter("Ports");
        int numOfPort = 1;
        if (ports != null) {
            numOfPort = StringParsers.parseIntArray(ports)[0];
        }

        int startX = srcDim[0] + srcDim[2];
        int startY = srcDim[1] + srcDim[3] / 2;
        int endX = dstDim[0];
        int endY = dstDim[1] + dstDim[3] / 2 / numOfPort * (2 * node.getDistPort()
                - 1);

        Vector<int[]> points = new Vector<int[]>();
        points.add(new int[] { startX, startY });
        String pointsStr = node.getParameter("Points");
        if (pointsStr != null) {
            String[] pointsStrAsArray = pointsStr.split(";");
            for (int i = 0; i < pointsStrAsArray.length; i++) {
                String pointStr = pointsStrAsArray[i];
                int[] point = StringParsers.parseIntArray(pointStr);

                if (i == 0 && point[0] < 0) {
                    points.get(0)[0] -= srcDim[2];
                }

                int x = point[0] * (int) (widthMultiplier) + points.get(i)[0];
                int y = point[1] * (int) (heightMultiplier) + points.get(i)[1];
                points.add(new int[] { x, y });
            }
        }
        if (node.getChildren().length == 0) {
            if (points.lastElement()[0] < endX)
                points.add(new int[] { endX, endY });
            else
                points.add(new int[] { endX + dstDim[2], endY });
        }

        if (points.size() > 1) {
            renderBranch(gc, points, node.getChildren().length == 0);
        }
        if (node.getChildren().length != 0) {
            {
                MDLBranch[] branches = (MDLBranch[]) node.getChildren();
                for (MDLBranch branch : branches) {
                    final MDLBlock branchDestinationBlock = Main.system.getBlockByID(branch.getDistID());
                    int[] branchDstDim = MDLBlockRenderer.getDimensions(branchDestinationBlock,
                            widthMultiplier, offsetX, offsetY,
                            heightMultiplier, zoomFactor);
                    String branchPorts = destinationBlock.getParameter("Ports");
                    int branchNumOfPort = 1;
                    if (branchPorts != null) {
                        branchNumOfPort = StringParsers.parseIntArray(ports)[0];
                    }

                    int branchEndX = branchDstDim[0];
                    int branchEndY = branchDstDim[1] + branchDstDim[3] / 2 / branchNumOfPort * (2 * branch.getDistPort()
                            - 1);

                    Vector<int[]> branchPoints = new Vector<int[]>();
                    branchPoints.add(points.lastElement().clone());
                    String branchPointsStr = branch.getParameter("Points");
                    if (branchPointsStr != null) {
                        String[] pointsStrAsArray = branchPointsStr.split(";");
                        for (int i = 0; i < pointsStrAsArray.length; i++) {
                            String pointStr = pointsStrAsArray[i];
                            int[] point = StringParsers.parseIntArray(pointStr);

                            if (i == 0 && point[0] < 0) {
                                branchPoints.get(0)[0] -= srcDim[2];
                            }
                            int x = point[0] * (int) (widthMultiplier) +
                                    branchPoints.get(i)[0];
                            int y = point[1] * (int) (heightMultiplier) +
                                    branchPoints.get(i)[1];
                            branchPoints.add(new int[] { x, y });
                        }
                    }
                    if (branchPoints.lastElement()[0] < branchEndX)
                        branchPoints.add(new int[] { branchEndX, branchEndY });
                    else
                        branchPoints.add(new int[] { branchEndX + branchDstDim[2], branchEndY });

                    renderBranch(gc, branchPoints, true);
                }
            }
        }
    }

    public void renderBranch(GraphicsContext gc, Vector<int[]> points, boolean isLast) {

        for (int i = 0; i < points.size() - 1; i++) {
            int[] point = points.get(i);
            int[] nextPoint = points.get(i + 1);
            gc.strokeLine(point[0], point[1], nextPoint[0], nextPoint[1]);
        }

        // draw arrow head with direction
        int[] lastPoint = points.get(points.size() - 1);
        if (isLast) {
            if (points.get(points.size() - 2)[0] < lastPoint[0]) {
                gc.strokeLine(lastPoint[0], lastPoint[1], lastPoint[0] - 10, lastPoint[1] - 10);
                gc.strokeLine(lastPoint[0], lastPoint[1], lastPoint[0] - 10, lastPoint[1] + 10);
            } else {
                gc.strokeLine(lastPoint[0], lastPoint[1], lastPoint[0] + 10, lastPoint[1] - 10);
                gc.strokeLine(lastPoint[0], lastPoint[1], lastPoint[0] + 10, lastPoint[1] + 10);
            }
        }
    }
}
