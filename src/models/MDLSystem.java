package src.models;

import java.util.Map;

public class MDLSystem extends MDLNode {

    public MDLSystem(Map<String, String> parameters, MDLNode[] children) {
        super("System", parameters, children);
    }
}
