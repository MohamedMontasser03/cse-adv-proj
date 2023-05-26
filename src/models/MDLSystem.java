package src.models;

import java.util.Map;

public class MDLSystem extends MDLNode {
    private int blockCount = 0;

    public MDLSystem(Map<String, String> parameters, MDLNode[] children) {
        super("System", parameters, children);
        for (MDLNode child : children) {
            if (child instanceof MDLBlock) {
                blockCount++;
            }
        }
    }

    public MDLBlock[] getBlocks() {
        MDLBlock[] blocks = new MDLBlock[blockCount];
        int blockIndex = 0;
        for (MDLNode child : this.getChildren()) {
            if (child instanceof MDLBlock) {
                blocks[blockIndex] = (MDLBlock) child;
                blockIndex++;
            }
        }
        return blocks;
    }
}
