package src.models;

import java.util.Map;

public class MDLBlock extends MDLNode {
    private final String name;

    public MDLBlock(String name, String type, Map<String, String> parameters) {
        super(type, parameters, new MDLNode[0]);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String str = super.toString();
        str += "name: " + name + "\n";
        return str;
    }
}
