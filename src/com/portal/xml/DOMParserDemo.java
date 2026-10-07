package com.portal.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.portal.model.Course;

/**
 * =====================================================================
 * XML PARSER: DOMParserDemo (Document Object Model)
 * 
 * Syllabus Demonstration:
 *   - Introduction to XML Parsing
 *   - DOM Parser (Tree-based parsing model)
 *   - Standard Java XML APIs (JDK built-in):
 *       * javax.xml.parsers.DocumentBuilderFactory
 *       * javax.xml.parsers.DocumentBuilder
 *       * org.w3c.dom.Document
 *       * org.w3c.dom.Element
 *       * org.w3c.dom.NodeList
 *       * org.w3c.dom.Node
 * 
 * DOM Characteristics (Viva Key Concept):
 *   - Reads the ENTIRE XML document into memory and builds a tree structure.
 *   - Allows bi-directional traversal (parent, child, sibling nodes).
 *   - Permits random access and modification of nodes in memory.
 *   - High memory consumption for very large XML files compared to SAX.
 * =====================================================================
 */
public class DOMParserDemo {

    /**
     * Resolves the path to courses.xml robustly across different execution directories.
     */
    public static File resolveXMLFile(String specifiedPath) {
        if (specifiedPath != null && !specifiedPath.trim().isEmpty()) {
            File f = new File(specifiedPath);
            if (f.exists()) return f;
        }

        // Try standard relative paths from project root or workspace
        String[] candidatePaths = {
            "WebContent/xml/courses.xml",
            "../WebContent/xml/courses.xml",
            "xml/courses.xml"
        };

        for (String path : candidatePaths) {
            File file = new File(path);
            if (file.exists()) {
                return file;
            }
        }

        return new File("WebContent/xml/courses.xml");
    }

    /**
     * Parses courses.xml using the DOM API and returns a list of Course objects.
     * 
     * @param xmlPath Path to the XML file
     * @return List of parsed Course JavaBean instances
     */
    public List<Course> parseCourses(String xmlPath) {
        List<Course> coursesList = new ArrayList<>();
        File xmlFile = resolveXMLFile(xmlPath);

        System.out.println("=================================================================");
        System.out.println("           DOM PARSER DEMONSTRATION (Document Object Model)");
        System.out.println("=================================================================");
        System.out.println("[DOM Parser] Loading file from: " + xmlFile.getAbsolutePath());

        try {
            // STEP 1: Create a DocumentBuilderFactory instance
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            
            // Configure parser properties (Optional: namespace awareness)
            factory.setNamespaceAware(false);

            // STEP 2: Create a DocumentBuilder instance from the factory
            DocumentBuilder builder = factory.newDocumentBuilder();

            // STEP 3: Parse the XML file into a Document (In-memory DOM Tree)
            Document document = builder.parse(xmlFile);

            // Normalize the document structure (collapses adjacent text nodes)
            document.getDocumentElement().normalize();

            // STEP 4: Access the root element
            Element root = document.getDocumentElement();
            System.out.println("[DOM Parser] Root Element: <" + root.getNodeName() + ">");
            System.out.println("[DOM Parser] Department Attribute: " + root.getAttribute("department"));
            System.out.println("[DOM Parser] Academic Year: " + root.getAttribute("academicYear"));
            System.out.println("-----------------------------------------------------------------");

            // STEP 5: Retrieve all <course> nodes using getElementsByTagName()
            NodeList courseNodeList = document.getElementsByTagName("course");
            System.out.println("[DOM Parser] Found " + courseNodeList.getLength() + " course element(s).\n");

            // STEP 6: Iterate through each course node
            for (int i = 0; i < courseNodeList.getLength(); i++) {
                Node node = courseNodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element courseElement = (Element) node;

                    // Read XML attribute: id
                    String attrId = courseElement.getAttribute("id");

                    // Read child elements text content using helper method
                    String courseId = getTagValue("courseId", courseElement);
                    String courseName = getTagValue("courseName", courseElement);
                    String faculty = getTagValue("faculty", courseElement);
                    String creditsStr = getTagValue("credits", courseElement);
                    String courseType = getTagValue("courseType", courseElement);
                    String academicLevel = getTagValue("academicLevel", courseElement);
                    String description = getTagValue("description", courseElement);

                    int credits = 0;
                    try {
                        credits = Integer.parseInt(creditsStr);
                    } catch (NumberFormatException e) {
                        credits = 3;
                    }

                    // Populate Course JavaBean
                    Course course = new Course(
                        courseId, courseName, faculty, credits, 
                        courseType, academicLevel, true, description
                    );
                    coursesList.add(course);

                    // Print formatted record to console
                    System.out.printf("[%d] Course ID : %s (Attribute id='%s')\n", (i + 1), courseId, attrId);
                    System.out.printf("    Title     : %s\n", courseName);
                    System.out.printf("    Faculty   : %s\n", faculty);
                    System.out.printf("    Credits   : %d\n", credits);
                    System.out.printf("    Delivery  : %s (%s)\n", courseType, academicLevel);
                    System.out.printf("    Syllabus  : %s\n\n", description);
                }
            }

            System.out.println("-----------------------------------------------------------------");
            System.out.println("[DOM Parser] Successfully parsed " + coursesList.size() + " courses into memory.");
            System.out.println("=================================================================\n");

        } catch (Exception e) {
            System.err.println("[DOM Parser] Error parsing XML document: " + e.getMessage());
            e.printStackTrace();
        }

        return coursesList;
    }

    /**
     * Helper method to safely extract the text content of a tag within an element.
     */
    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList != null && nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            if (node != null) {
                return node.getTextContent().trim();
            }
        }
        return "";
    }

    /**
     * Standalone main method to demonstrate DOM parsing during practical evaluation.
     */
    public static void main(String[] args) {
        String xmlPath = (args.length > 0) ? args[0] : "WebContent/xml/courses.xml";
        DOMParserDemo demo = new DOMParserDemo();
        List<Course> list = demo.parseCourses(xmlPath);
        System.out.println("[DOMParserDemo] Main execution finished. Parsed count = " + list.size());
    }
}
