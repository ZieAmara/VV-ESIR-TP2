# Code of your exercise

```XML
<?xml version="1.0"?>

<ruleset name="Custom Rules"
    xmlns="http://pmd.sourceforge.net/ruleset/2.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://pmd.sourceforge.net/ruleset/2.0.0 https://pmd.sourceforge.io/ruleset_2_0_0.xsd">

    <description>
        My custom rules
    </description>


    <!-- Your rules will come here -->
    <!-- XPath -->
    <rule name="ThreeOrMoreNestedIf"
        language="java"
        message="Using 3 or more If statement nested"
        class="net.sourceforge.pmd.lang.rule.XPathRule">
        <description>
            Avoid us to found 3 (4, 5, 6, ...) or more if statement nested
        </description>
        <priority>3</priority>
        <properties>
            <property name="min_IfStat_as_ancestor" type="Integer" value="2" description="TODO"/>
            <property name="version" value="3.1"/>
            <property name="xpath">
                <value>
<![CDATA[
/IfStatement[count(ancestor::IfStatement)>=$min_IfStat_as_ancestor]
]]>
                </value>
            </property>
        </properties>
    </rule>

</ruleset>
```
