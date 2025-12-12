<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:fo="http://www.w3.org/1999/XSL/Format"
    version="1.0">
    <xsl:output indent="no" method="xml" encoding="UTF-8"/>

    <xsl:variable name="title" select="/document/title"/>
    <xsl:variable name="img" select="/document/img"/>

    <xsl:template match="document">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="document" page-height="29.7cm" page-width="21cm"
                    margin-top="1.04cm" margin-bottom="0.3cm" margin-left="0.6cm"
                    margin-right="0.6cm">
                    <fo:region-body margin-bottom="1.2cm"/>
                    <fo:region-after region-name="footer" extent="0.8cm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:declarations>
                <x:xmpmeta xmlns:x="adobe:ns:meta/">
                    <rdf:RDF xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#">
                        <rdf:Description xmlns:dc="http://purl.org/dc/elements/1.1/" rdf:about="">
                            <dc:title>
                                <xsl:value-of select="$title"/>
                            </dc:title>
                            <dc:creator>jdussouillez</dc:creator>
                        </rdf:Description>
                    </rdf:RDF>
                </x:xmpmeta>
            </fo:declarations>
            <fo:page-sequence master-reference="document">
                <fo:static-content flow-name="footer">
                    <fo:block font-size="20pt" font-family="Arimo">
                        <xsl:value-of select="$title"/>
                    </fo:block>
                </fo:static-content>
                <fo:flow flow-name="xsl-region-body">
                    <xsl:apply-templates select="./cars"/>
                    <fo:block text-align="center">
                        <fo:external-graphic src="url('{img}')" content-width="200pt" content-height="200pt"/>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>

    <xsl:template match="cars">
        <fo:block font-size="10pt">
            <fo:table table-layout="fixed" width="90%" border-collapse="separate">
                <fo:table-column column-width="2cm"/>
                <fo:table-column column-width="4cm"/>
                <fo:table-column column-width="5cm"/>
                <fo:table-column column-width="3cm"/>
                <fo:table-header>
                    <fo:table-row>
                        <fo:table-cell border="solid black 1px" padding="2px" font-weight="bold" text-align="center">
                            <fo:block>Id</fo:block>
                        </fo:table-cell>
                        <fo:table-cell border="solid black 1px" padding="2px" font-weight="bold" text-align="center">
                            <fo:block>Model</fo:block>
                        </fo:table-cell>
                        <fo:table-cell border="solid black 1px" padding="2px" font-weight="bold" text-align="center">
                            <fo:block>Manufacturer</fo:block>
                        </fo:table-cell>
                        <fo:table-cell border="solid black 1px" padding="2px" font-weight="bold" text-align="center">
                            <fo:block>Year</fo:block>
                        </fo:table-cell>
                    </fo:table-row>
                </fo:table-header>
                <fo:table-body>
                    <xsl:apply-templates select="./car"/>
                </fo:table-body>
            </fo:table>
        </fo:block>
    </xsl:template>

    <xsl:template match="car">
        <fo:table-row>
            <fo:table-cell>
                <fo:block>
                    <xsl:value-of select="id"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell>
                <fo:block>
                    <xsl:value-of select="model"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell>
                <fo:block>
                    <xsl:value-of select="manufacturer"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell>
                <fo:block>
                    <xsl:value-of select="year"/>
                </fo:block>
            </fo:table-cell>
        </fo:table-row>
    </xsl:template>
</xsl:stylesheet>
