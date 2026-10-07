package com.portal.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.portal.model.Course;

/**
 * =====================================================================
 * XML PARSER: SAXParserDemo (Simple API for XML)
 * 
 * Syllabus Demonstration:
 *   - SAX Parser (Event-driven streaming model)
 *   - Standard Java SAX APIs (JDK built-in):
 *       * javax.xml.parsers.SAXParserFactory
 *       * javax.xml.parsers.SAXParser
 *       * org.xml.sax.helpers.DefaultHandler
 *       * org.xml.sax.Attributes
 * 
 * SAX Characteristics (Viva Key Concept):
 *   - Sequential, push-based event parser.
 *   - Does NOT load the XML document into memory as a tree.
 *   - Fires callback events (startElement, characters, endElement) as it scans.
 *   - Read-only; cannot modify XML document during parsing.
 *   - Highly memory-efficient and fast for arbitrarily large XML datasets.
 * 
 * Key Differences: DOM vs SAX
 *   ┌──────────────────────┬───────────────────────────────────────────┬───────────────────────────────────────────┐
 *   │ Feature              │ DOM Parser                                │ SAX Parser                                │
 *   ├──────────────────────┼───────────────────────────────────────────┼───────────────────────────────────────────┤
 *   │ Parsing Architecture │ Tree-based (Object Model)                 │ Event-driven (Push Callback)              │
 *   │ Memory Requirement   │ High (Loads full XML into RAM)            │ Very Low (Stream-based, constant memory)  │
 *   │ Access Type          │ Random access (Navigate anywhere)         │ Sequential forward-only                   │
 *   │ Modification         │ Supports node creation, update & delete   │ Read-only parsing                         │
 *   │ Best Suited For      │ Small to medium XML with frequent queries │ Large XML feeds, high-throughput parsing  │
 *   └──────────────────────┴───────────────────────────────────────────┴───────────────────────────────────────────┘
 * =====================================================================
 */
public class SAXParserDemo {

    /**
     * Resolves the path to courses.xml robustly across different execution directories.
     */
    public static File resolveXMLFile(String specifiedPath) {
        if (specifiedPath != null && !specifiedPath.trim().isEmpty()) {
            File f = new File(specifiedPath);
            if (f.exists()) return f;
        }

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
     * Parses courses.xml using SAXParser and custom DefaultHandler.
     * 
     * @param xmlPath Path to XML file
     * @return List of Course JavaBean instances
     */
    public List<Course> parseCourses(String xmlPath) {
        List<Course> coursesList = new ArrayList<>();
        File xmlFile = resolveXMLFile(xmlPath);

        System.out.println("=================================================================");
        System.out.println("             SAX PARSER DEMONSTRATION (Simple API for XML)");
        System.out.println("=================================================================");
        System.out.println("[SAX Parser] Processing file: " + xmlFile.getAbsolutePath() + "\n");

        try {
            // STEP 1: Create a SAXParserFactory instance
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(false);

            // STEP 2: Create a SAXParser instance from the factory
            SAXParser saxParser = factory.newSAXParser();

            // STEP 3: Define a custom event handler extending DefaultHandler
            CourseHandler handler = new CourseHandler();

            // STEP 4: Initiate sequential parsing
            saxParser.parse(xmlFile, handler);

            coursesList = handler.getCoursesList();
            System.out.println("-----------------------------------------------------------------");
            System.out.println("[SAX Parser] Completed parsing. Total courses retrieved: " + coursesList.size());
            System.out.println("=================================================================\n");

        } catch (Exception e) {
            System.err.println("[SAX Parser] Error during parsing: " + e.getMessage());
            e.printStackTrace();
        }

        return coursesList;
    }

    /**
     * Custom SAX Event Handler implementing callback methods.
     */
    private static class CourseHandler extends DefaultHandler {
        private List<Course> coursesList = new ArrayList<>();
        private Course currentCourse = null;
        private StringBuilder characterBuffer = new StringBuilder();
        private int count = 0;

        public List<Course> getCoursesList() {
            return coursesList;
        }

        // CALLBACK 1: startDocument - Called at the beginning of parsing
        @Override
        public void startDocument() throws SAXException {
            System.out.println("[SAX Event] startDocument() -> Parsing started.");
        }

        // CALLBACK 2: startElement - Called when an opening XML tag is encountered
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) 
                throws SAXException {
            characterBuffer.setLength(0); // Clear character buffer

            if ("course".equalsIgnoreCase(qName)) {
                currentCourse = new Course();
                String attrId = attributes.getValue("id");
                count++;
                System.out.println(">>> [SAX Event: startElement] <course id='" + attrId + "'>");
            }
        }

        // CALLBACK 3: characters - Called when text content inside tags is read
        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            characterBuffer.append(ch, start, length);
        }

        // CALLBACK 4: endElement - Called when a closing XML tag is encountered
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            String content = characterBuffer.toString().trim();

            if (currentCourse != null) {
                if ("courseId".equalsIgnoreCase(qName)) {
                    currentCourse.setCourseId(content);
                } else if ("courseName".equalsIgnoreCase(qName)) {
                    currentCourse.setCourseName(content);
                } else if ("faculty".equalsIgnoreCase(qName)) {
                    currentCourse.setFacultyName(content);
                } else if ("credits".equalsIgnoreCase(qName)) {
                    try {
                        currentCourse.setCredits(Integer.parseInt(content));
                    } catch (NumberFormatException e) {
                        currentCourse.setCredits(3);
                    }
                } else if ("courseType".equalsIgnoreCase(qName)) {
                    currentCourse.setCourseType(content);
                } else if ("academicLevel".equalsIgnoreCase(qName)) {
                    currentCourse.setAcademicLevel(content);
                } else if ("description".equalsIgnoreCase(qName)) {
                    currentCourse.setDescription(content);
                } else if ("course".equalsIgnoreCase(qName)) {
                    // Completed course node, add to list
                    coursesList.add(currentCourse);
                    System.out.printf("    Record #%d: [%s] %s | %s (%d credits)\n", 
                                      count, currentCourse.getCourseId(), currentCourse.getCourseName(), 
                                      currentCourse.getFacultyName(), currentCourse.getCredits());
                    System.out.println("<<< [SAX Event: endElement] </course>\n");
                    currentCourse = null;
                }
            }
        }

        // CALLBACK 5: endDocument - Called when the document ends
        @Override
        public void endDocument() throws SAXException {
            System.out.println("[SAX Event] endDocument() -> Parsing finished.");
        }
    }

    /**
     * Standalone main method to demonstrate SAX parsing during practical evaluation.
     */
    public static void main(String[] args) {
        String xmlPath = (args.length > 0) ? args[0] : "WebContent/xml/courses.xml";
        SAXParserDemo demo = new SAXParserDemo();
        List<Course> list = demo.parseCourses(xmlPath);
        System.out.println("[SAXParserDemo] Main execution finished. Parsed count = " + list.size());
    }
}
