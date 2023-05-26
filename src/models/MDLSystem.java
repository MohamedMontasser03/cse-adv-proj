package src.models;

import java.util.HashMap;
import java.util.Map;

public class MDLSystem extends MDLNode {
    private int blockCount = 0;
    private Map<Integer, MDLBlock> blocks;

    public MDLSystem(Map<String, String> parameters, MDLNode[] children) {
        super("System", parameters, children);
        blocks = new HashMap<Integer, MDLBlock>();
        for (MDLNode child : children) {
            if (child instanceof MDLBlock) {
                blockCount++;
                MDLBlock block = (MDLBlock) child;
                blocks.put(block.getId(), block);
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

    public MDLBlock getBlockByID(int id) {
        return blocks.get(id);
    }
}
