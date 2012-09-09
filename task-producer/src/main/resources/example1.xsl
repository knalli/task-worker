<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:fo="http://www.w3.org/1999/XSL/Format">

    <xsl:param name="PATH" />

    <xsl:template match="/">
        <xsl:call-template name="render" />
    </xsl:template>

    <xsl:template name="render">
        <fo:root font-family="Times Roman" font-size="12pt" text-align="center">

            <fo:layout-master-set>
                <fo:simple-page-master margin-right="1.5cm" margin-left="1.5cm" margin-bottom="2cm" margin-top="1cm" page-width="21cm" page-height="29.7cm" master-name="left">
                    <fo:region-body margin-top="1cm"/>
                    <fo:region-before extent="1cm"/>
                    <fo:region-after extent="1.5cm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence id="N2528" master-reference="left">

                <fo:static-content flow-name="xsl-region-after">
                    <fo:block text-align-last="center" font-size="10pt">
                        <fo:page-number/>
                    </fo:block>
                </fo:static-content>

                <fo:flow flow-name="xsl-region-body">
                    <xsl:for-each select="/xml/users/user">
                        <fo:block>
                            User name: <xsl:value-of select="name/text()"/>
                        </fo:block>
                    </xsl:for-each>
                    <fo:block font-size="18pt" font-weight="bold">1. FOP test for images</fo:block>
                    <fo:block>
                        <fo:block font-size="16pt" font-weight="bold" space-before.minimum="1em" space-before.optimum="1.5em" space-before.maximum="2em">Align in Smaller Viewport</fo:block>
                        <fo:block>
                            Default align:
                            (<fo:external-graphic width="50pt" height="50pt" overflow="hidden" src="{$PATH}/example1.png"/>), start
                            (<fo:external-graphic width="50pt" height="50pt" overflow="hidden" text-align="start" src="{$PATH}/example1.png"/>), center
                            (<fo:external-graphic width="50pt" height="50pt" overflow="hidden" text-align="center" src="{$PATH}/example1.png"/>), end
                            (<fo:external-graphic width="50pt" height="50pt" overflow="hidden" text-align="end" src="{$PATH}/example1.png"/>), before
                            (<fo:external-graphic width="50pt" height="50pt" overflow="hidden" display-align="before" src="{$PATH}/example1.png"/>), after
                            (<fo:external-graphic width="50pt" height="50pt" overflow="hidden" display-align="after" src="{$PATH}/example1.png"/>), center
                            (<fo:external-graphic width="50pt" height="50pt" overflow="hidden" display-align="center" src="{$PATH}/example1.png"/>).
                        </fo:block>
                        <fo:block>
                            Default align:
                            (<fo:external-graphic width="50pt" height="50pt" overflow="hidden" src="{$PATH}/example1.png"/>), start-before
                            (<fo:external-graphic width="50pt" height="50pt" overflow="hidden" text-align="start" display-align="before" src="{$PATH}/example1.png"/>), start-center
                            (<fo:external-graphic width="50pt" height="50pt" overflow="hidden" text-align="start" display-align="center" src="{$PATH}/example1.png"/>), start-after
                            (<fo:external-graphic width="50pt" height="50pt" overflow="hidden" text-align="start" display-align="after" src="{$PATH}/example1.png"/>), center-before
                            (<fo:external-graphic width="50pt" height="50pt" overflow="hidden" text-align="center" display-align="before" src="{$PATH}/example1.png"/>), center-after
                            (<fo:external-graphic width="50pt" height="50pt" overflow="hidden" text-align="center" display-align="after" src="{$PATH}/example1.png"/>), center-center
                            (<fo:external-graphic width="50pt" height="50pt" overflow="hidden" text-align="center" display-align="center" src="{$PATH}/example1.png"/>).
                        </fo:block>

                        <fo:block>
                            end-before
                            (<fo:external-graphic width="50pt" height="50pt" overflow="hidden" text-align="end" display-align="before" src="{$PATH}/example1.png"/>), end-after
                            (<fo:external-graphic width="50pt" height="50pt" overflow="hidden" text-align="end" display-align="after" src="{$PATH}/example1.png"/>), end-center
                            (<fo:external-graphic width="50pt" height="50pt" overflow="hidden" text-align="end" display-align="center" src="{$PATH}/example1.png"/>).
                        </fo:block>

                        <fo:block font-size="16pt" font-weight="bold" space-before.minimum="1em" space-before.optimum="1.5em" space-before.maximum="2em"/>
                        This section is only required to show that the layout still works.
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>

        </fo:root>
    </xsl:template>

</xsl:stylesheet>