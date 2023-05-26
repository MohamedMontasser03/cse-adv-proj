package src.models;

import java.util.Map;

public class MDLNode {
    private final String type;
    private final Map<String, String> parameters;
    private final MDLNode[] children;

    public MDLNode(String type, Map<String, String> parameters, MDLNode[] children) {
        this.type = type;
        this.parameters = parameters;
        this.children = children;
    }

    public String getType() {
        return type;
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public MDLNode[] getChildren() {
        return children;
    }

    public String toString() {
        String str = "";
        str += "Type: " + type + "\n";
        str += "Parameters: " + parameters.toString() + "\n";
        str += "Children: \n";
        for (MDLNode child : children) {
            str += "\t" + child.toString();
        }
        return str;
    }
}
