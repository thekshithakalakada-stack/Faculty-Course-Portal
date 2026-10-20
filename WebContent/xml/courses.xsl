<?xml version="1.0" encoding="UTF-8"?>
<!-- ===================================================================
     XSL TRANSFORMATION STYLESHEET: courses.xsl
     
     Syllabus Demonstration:
       - XSLT (Extensible Stylesheet Language Transformations)
       - xsl:stylesheet & namespace declaration
       - xsl:template matching the root element
       - xsl:for-each loop iterating through XML course nodes
       - xsl:value-of extracting element text
       - xsl:sort sorting courses by courseId
       - Generating clean HTML5 markup with embedded CSS
     =================================================================== -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <!-- Output format specification: HTML5 -->
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <!-- Root Template: Matches the document root (/) -->
    <xsl:template match="/">
        <html lang="en">
        <head>
            <meta charset="UTF-8"/>
            <title>Course Catalog - Faculty Course Management Portal</title>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    background-color: #f4f7fb;
                    margin: 0;
                    padding: 20px;
                    color: #222;
                }
                .container {
                    max-width: 1000px;
                    margin: 0 auto;
                    background: white;
                    padding: 30px;
                    border: 1px solid #d5dce5;
                    border-radius: 8px;
                }
                header {
                    background-color: #1e3a5f;
                    color: white;
                    padding: 20px;
                    border-radius: 6px;
                    text-align: center;
                    margin-bottom: 25px;
                }
                header h1 {
                    margin: 0;
                    font-size: 26px;
                }
                header p {
                    margin: 8px 0 0 0;
                    font-size: 14px;
                    color: #cbd5e1;
                }

                table {
                    width: 100%;
                    border-collapse: collapse;
                    margin-top: 15px;
                }
                th, td {
                    border: 1px solid #b8c2cc;
                    padding: 12px;
                    text-align: left;
                    font-size: 14px;
                }
                th {
                    background-color: #1e3a5f;
                    color: white;
                }
                tr:nth-child(even) {
                    background-color: #f8fafc;
                }
                tr:hover {
                    background-color: #f1f5f9;
                }
                .badge {
                    display: inline-block;
                    padding: 4px 8px;
                    border-radius: 10px;
                    font-size: 12px;
                    font-weight: bold;
                }
                .badge-theory {
                    background-color: #e0f2fe;
                    color: #0369a1;
                }
                .badge-lab {
                    background-color: #fef3c7;
                    color: #b45309;
                }
                .badge-both {
                    background-color: #dcfce7;
                    color: #15803d;
                }
                .footer {
                    margin-top: 30px;
                    text-align: center;
                    font-size: 13px;
                    color: #64748b;
                    border-top: 1px solid #e2e8f0;
                    padding-top: 15px;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <header>
                    <h1>Faculty Course Management Portal</h1>
                    <p>Department Course Catalog</p>
                </header>

                <table>
                    <thead>
                        <tr>
                            <th>Course ID</th>
                            <th>Course Name</th>
                            <th>Faculty</th>
                            <th>Credits</th>
                            <th>Course Type</th>
                            <th>Academic Level</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Iterating through course elements using xsl:for-each -->
                        <xsl:for-each select="courses/course">
                            <!-- Optional demonstration of xsl:sort -->
                            <xsl:sort select="courseId" order="ascending"/>
                            <tr>
                                <td>
                                    <strong><xsl:value-of select="courseId"/></strong>
                                </td>
                                <td><xsl:value-of select="courseName"/></td>
                                <td><xsl:value-of select="faculty"/></td>
                                <td><xsl:value-of select="credits"/></td>
                                <td>
                                    <xsl:value-of select="courseType"/>
                                </td>
                                <td><xsl:value-of select="academicLevel"/></td>
                            </tr>
                        </xsl:for-each>
                    </tbody>
                </table>

                <div class="footer">
                    &#169; Faculty Course Management Portal
                </div>
            </div>
        </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
