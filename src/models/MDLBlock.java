package src.models;

import java.util.Map;

public class MDLBlock extends MDLNode {
    private final String name;
    private final String BlockType;

    public MDLBlock(String name, String blockType, Map<String, String> parameters) {
        super("Block", parameters, new MDLNode[0]);
        this.name = name;
        this.BlockType = blockType;
    }

    public String getName() {
        return name;
    }

    public String getBlockType() {
        return BlockType;
    }

    @Override
    public String toString() {
        String str = super.toString();
        str += "name: " + name + "\n";
        str += "BlockType: " + BlockType + "\n";
        return str;
    }
}
