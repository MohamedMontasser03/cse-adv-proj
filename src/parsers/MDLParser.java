package src.parsers;

import java.io.File;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import src.models.MDLBlock;
import src.models.MDLLine;
import src.models.MDLNode;
import src.models.MDLSystem;
import src.models.MDLLine.MDLBranch;

public class MDLParser {
    private final File file;
    private Node systemElement;
    private MDLSystem system;

    public MDLParser(String filePath) throws Exception {
        file = new File(filePath);

        try {
            if (!file.exists()) {
                throw new Exception("File does not exist");
            }
        } catch (Exception e) {
            System.out.println("Error: Failed to access file");
        }

        final String systemXML = getSystemXML();
        try {
            systemElement = parseXML(systemXML).getFirstChild();
            system = parseSystem(systemElement);
        } catch (Exception e) {
            System.out.println("Error: Failed to parse system xml");
            System.out.println(e.getMessage());
        }
    }

    /*
     * this function returns the xml string of the system root
     * in a full parser, this function would return each xml string in the file
     */
    private String getSystemXML() {
        try {
            final Scanner scanner = new Scanner(file);
            boolean isSystem = false;
            String systemXML = "";

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (isSystem) {
                    systemXML += line + "\n";
                }

                if (line.trim().equals("__MWOPC_PART_BEGIN__ /simulink/systems/system_root.xml")) {
                    isSystem = true;
                }

                if (line.matches("</System>") && isSystem) {
                    isSystem = false;
                }
            }
            scanner.close();
            return systemXML;
        } catch (Exception e) {
            System.out.println("Error: Failed to read file");
            return "";
        }
    }

    private Document parseXML(String xml) throws Exception {
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xml));
            return builder.parse(is);
        } catch (Exception e) {
            throw new Exception("Error: Failed to parse xml");
        }
    }

    private Map<String, String> parseParameters(Node element) {
        NodeList children = element.getChildNodes();
        Map<String, String> parameters = new HashMap<String, String>();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if ("P".equals(child.getNodeName())) {
                String name = child.getAttributes().getNamedItem("Name").getNodeValue();
                String value = child.getTextContent();
                parameters.put(name, value);
            }
        }

        return parameters;
    }

    private MDLBlock parseBlock(Node block) {
        String name = block.getAttributes().getNamedItem("Name").getNodeValue();
        String type = block.getAttributes().getNamedItem("BlockType").getNodeValue();
        String id = block.getAttributes().getNamedItem("SID").getNodeValue();
        Map<String, String> parameters = parseParameters(block);
        return new MDLBlock(name, type, parameters, Integer.parseInt(id));
    }

    private MDLLine parseLine(Node line) {
        Map<String, String> parameters = parseParameters(line);
        Vector<MDLBranch> branches = new Vector<MDLBranch>();
        NodeList children = line.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if ("Branch".equals(child.getNodeName())) {
                Map<String, String> branchParameters = parseParameters(child);
                branches.add(new MDLBranch(branchParameters));
            }
        }
        return new MDLLine(parameters, branches.toArray(new MDLBranch[branches.size()]));
    }

    private MDLSystem parseSystem(Node system) {
        NodeList childNodes = system.getChildNodes();
        int childCount = 0;
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);
            if ("Block".equals(child.getNodeName()) || "Line".equals(child.getNodeName())) {
                childCount++;
            }
        }
        MDLNode[] children = new MDLNode[childCount];
        int blockIndex = 0;
        for (int i = 0; i < childNodes.getLength(); i++) {
            if ("Block".equals(childNodes.item(i).getNodeName())) {
                children[blockIndex] = parseBlock(childNodes.item(i));
                blockIndex++;
            }
            if ("Line".equals(childNodes.item(i).getNodeName())) {
                children[blockIndex] = parseLine(childNodes.item(i));
                blockIndex++;
            }
        }
        return new MDLSystem(this.parseParameters(system), children);
    }

    public MDLSystem getSystem() {
        return system;
    }
}
