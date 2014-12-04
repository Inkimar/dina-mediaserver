<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <!--<xsl:output method="html"/>-->
    <xsl:template match="/">
        <html>
            <head>
                <title>Media files</title>
            </head>
            <body>
                <h2>Images</h2>
                <table border='1'>
                    <tr bgcolor='#9acd32'>
                        <th>Image</th>
                        <th>UUID</th>
                    </tr>
                    <xsl:for-each select='collection/media'>
                        <tr>
                            <td>
                                <img>
                                    <xsl:attribute name='src'>
                                        <xsl:value-of select='mediaURL' />
                                    </xsl:attribute>
                                </img>
                            </td>
                            <td>
                                <xsl:value-of select='uuid' />
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>