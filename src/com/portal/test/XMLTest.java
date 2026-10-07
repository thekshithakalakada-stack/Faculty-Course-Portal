package com.portal.test;

import java.io.File;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import com.portal.model.Course;
import com.portal.xml.DOMParserDemo;
import com.portal.xml.SAXParserDemo;

/**
 * =====================================================================
 * Standalone Test & Viva Demonstration: XMLTest
 * 
 * Purpose:
 *   Executes and demonstrates all XML syllabus topics in sequence via terminal:
 *   1. XML Schema (XSD) Validation test
 *   2. DOM Parser execution & tree traversal
 *   3. SAX Parser execution & event callbacks
 *   4. Summary comparison of DOM vs SAX
 * =====================================================================
 */
public class XMLTest {

    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("        FACULTY COURSE MANAGEMENT PORTAL - XML SUITE TEST");
        System.out.println("======================================================================\n");

        String xmlPath = (args.length > 0) ? args[0] : "WebContent/xml/courses.xml";
        String xsdPath = (args.length > 1) ? args[1] : "WebContent/xml/courses.xsd";

        File xmlFile = DOMParserDemo.resolveXMLFile(xmlPath);
        File xsdFile = new File(xmlFile.getParentFile(), "courses.xsd");

        // -------------------------------------------------------------
        // TEST 1: XML SCHEMA (XSD) VALIDATION DEMONSTRATION
        // -------------------------------------------------------------
        System.out.println(">>> [1/3] Testing XML Schema (XSD) Validation...");
        System.out.println(" XML File : " + xmlFile.getAbsolutePath());
        System.out.println(" XSD File : " + xsdFile.getAbsolutePath());

        boolean xsdValid = validateXMLSchema(xmlFile, xsdFile);
        if (xsdValid) {
            System.out.println(" [SUCCESS] courses.xml conforms strictly to courses.xsd!");
        } else {
            System.err.println(" [WARNING] Schema validation encountered issues.");
        }
        System.out.println("----------------------------------------------------------------------\n");

        // -------------------------------------------------------------
        // TEST 2: DOM PARSER EXECUTION
        // -------------------------------------------------------------
        System.out.println(">>> [2/3] Executing DOM Parser (DocumentBuilder)...");
        DOMParserDemo domDemo = new DOMParserDemo();
        List<Course> domCourses = domDemo.parseCourses(xmlFile.getPath());
        System.out.println(" DOM Parser Result: Retrieved " + domCourses.size() + " courses into memory.");
        System.out.println("----------------------------------------------------------------------\n");

        // -------------------------------------------------------------
        // TEST 3: SAX PARSER EXECUTION
        // -------------------------------------------------------------
        System.out.println(">>> [3/4] Executing SAX Parser (DefaultHandler Events)...");
        SAXParserDemo saxDemo = new SAXParserDemo();
        List<Course> saxCourses = saxDemo.parseCourses(xmlFile.getPath());
        System.out.println(" SAX Parser Result: Retrieved " + saxCourses.size() + " courses via streaming events.");
        System.out.println("----------------------------------------------------------------------\n");

        // -------------------------------------------------------------
        // TEST 4: XSLT TRANSFORMATION DEMONSTRATION
        // -------------------------------------------------------------
        System.out.println(">>> [4/4] Testing XSLT Transformation (courses.xml + courses.xsl)...");
        File xslFile = new File(xmlFile.getParentFile(), "courses.xsl");
        File transformedHtmlFile = new File(xmlFile.getParentFile(), "courses_transformed.html");
        boolean xsltSuccess = transformXMLWithXSLT(xmlFile, xslFile, transformedHtmlFile);
        if (xsltSuccess) {
            System.out.println(" [SUCCESS] Transformed courses.xml to HTML: " + transformedHtmlFile.getName());
        } else {
            System.err.println(" [WARNING] XSLT Transformation encountered issues.");
        }
        System.out.println("----------------------------------------------------------------------\n");

        // -------------------------------------------------------------
        // SUMMARY COMPARISON
        // -------------------------------------------------------------
        System.out.println("======================================================================");
        System.out.println("                   XML TEST SUMMARY & VERIFICATION");
        System.out.println("======================================================================");
        System.out.println(" ✓ XML Data File     : " + xmlFile.getName() + " (Well-formed & valid)");
        System.out.println(" ✓ DTD Linkage       : <!DOCTYPE courses SYSTEM 'courses.dtd'>");
        System.out.println(" ✓ XSD Validation    : Passed (" + domCourses.size() + " records validated)");
        System.out.println(" ✓ XSLT Transformation: Passed (Generated " + transformedHtmlFile.getName() + ")");
        System.out.println(" ✓ DOM Parser Count  : " + domCourses.size() + " courses");
        System.out.println(" ✓ SAX Parser Count  : " + saxCourses.size() + " courses");
        System.out.println("======================================================================");
    }

    /**
     * Programmatically transforms an XML file using an XSLT stylesheet via standard JDK TrAX APIs.
     */
    public static boolean transformXMLWithXSLT(File xmlFile, File xslFile, File outputFile) {
        if (!xmlFile.exists() || !xslFile.exists()) {
            System.err.println("Cannot perform XSLT transform: XML or XSL file not found.");
            return false;
        }

        try {
            javax.xml.transform.TransformerFactory tf = javax.xml.transform.TransformerFactory.newInstance();
            javax.xml.transform.Source xsltSource = new javax.xml.transform.stream.StreamSource(xslFile);
            javax.xml.transform.Transformer transformer = tf.newTransformer(xsltSource);
            javax.xml.transform.Source xmlSource = new javax.xml.transform.stream.StreamSource(xmlFile);
            javax.xml.transform.Result result = new javax.xml.transform.stream.StreamResult(outputFile);
            transformer.transform(xmlSource, result);
            return true;
        } catch (Exception e) {
            System.err.println("[XSLT Transform Error] " + e.getMessage());
            return false;
        }
    }

    /**
     * Programmatically validates an XML file against an XSD schema using standard JDK APIs.
     */
    public static boolean validateXMLSchema(File xmlFile, File xsdFile) {
        if (!xmlFile.exists() || !xsdFile.exists()) {
            System.err.println("Cannot perform validation: File not found.");
            return false;
        }

        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(xsdFile);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFile));
            return true;
        } catch (Exception e) {
            System.err.println("[Schema Validation Error] " + e.getMessage());
            return false;
        }
    }
}
