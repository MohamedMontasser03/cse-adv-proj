package src.models;

import java.util.Map;

public class MDLLine extends MDLNode {
    public static class MDLBranch extends MDLNode {
        private final int distID;
        private final int distPort;

        public MDLBranch(Map<String, String> parameters) {
            super("Branch", parameters, new MDLNode[0]);
            String[] dist = parameters.get("Dst").split("#in:");
            distID = Integer.parseInt(dist[0]);
            distPort = Integer.parseInt(dist[1]);
        }

        public int getDistID() {
            return distID;
        }

        public int getDistPort() {
            return distPort;
        }
    }

    private final int sourceID;
    private final int sourcePort;
    private final int distID;
    private final int distPort;

    public MDLLine(Map<String, String> parameters, MDLBranch[] branches) {
        super("Line", parameters, branches);
        String[] source = parameters.get("Src").split("#out:");
        sourceID = Integer.parseInt(source[0]);
        sourcePort = Integer.parseInt(source[1]);
        if (parameters.get("Dst") != null) {
            String[] dist = parameters.get("Dst").split("#in:");
            distID = Integer.parseInt(dist[0]);
            distPort = Integer.parseInt(dist[1]);
        } else {
            distID = branches[0].getDistID();
            distPort = branches[0].getDistPort();
        }
    }

    public int getSourceID() {
        return sourceID;
    }

    public int getSourcePort() {
        return sourcePort;
    }

    public int getDistID() {
        return distID;
    }

    public int getDistPort() {
        return distPort;
    }
}
