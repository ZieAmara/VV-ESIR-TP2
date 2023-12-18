# Extending PMD

Use XPath to define a new rule for PMD to prevent complex code. The rule should detect the use of three or more nested `if` statements in Java programs so it can detect patterns like the following:

```Java
if (...) {
    ...
    if (...) {
        ...
        if (...) {
            ....
        }
    }

}
```
Notice that the nested `if`s may not be direct children of the outer `if`s. They may be written, for example, inside a `for` loop or any other statement.
Write below the XML definition of your rule.

You can find more information on extending PMD in the following link: https://pmd.github.io/latest/pmd_userdocs_extending_writing_rules_intro.html, as well as help for using `pmd-designer` [here](https://github.com/selabs-ur1/VV-ISTIC-TP2/blob/master/exercises/designer-help.md).

Use your rule with different projects and describe you findings below. See the [instructions](../sujet.md) for suggestions on the projects to use.

## Answer

> In order to realize my new rule for Pmd, I used `pmd-designer`, then I wrote my rule with xpath. to solve the problem, I searched for the `if` declaration that had at least `min_IfStat_as_ancestor` (where min_IfStat_as_ancestor is a property that I added to the problem) ancestors also declaration `if`- `(figure 1)`. Once the rule was written, I exported it using the *Export result* button - `(figure 2)`. After all this, I opened my xml file which is supposed to welcome all the rules I will write and then I added this new rule generated thanks to pmd-designer - `(figure 3)`.
>
> Thanks to the `min_IfStat_as_ancestor` property that I added, this rule could detect 3 (4, 5, 6, ...) or more nested `if` statement depending on the value that will be given to `min_IfStat_as_ancestor` the default value being 2.

> My rule : 
```XML
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
```
> Result of execution of My ruleset on apache commons collections project:
>
> <img width="1424" alt="image" src="https://github.com/ZieAmara/VV-ESIR-TP2/assets/90223980/a8d38076-0b4a-40f9-80da-5a7b193b4126">
>
> When we click on the last path we have the following code that confirms the proper functioning of our rule.
>
> <img width="594" alt="image" src="https://github.com/ZieAmara/VV-ESIR-TP2/assets/90223980/5b2c03a3-4a61-417b-9a77-746bdd2fc008">



*figure 1:*

<img width="1104" alt="image" src="https://github.com/ZieAmara/VV-ESIR-TP2/assets/90223980/4b09815b-6ac3-4662-892a-b2d0ea29576c">

*figure 2:*

<img width="823" alt="image" src="https://github.com/ZieAmara/VV-ESIR-TP2/assets/90223980/64a026b6-9d43-4275-9ac3-17469e7ca1d2">

*figure 3:*

<img width="805" alt="image" src="https://github.com/ZieAmara/VV-ESIR-TP2/assets/90223980/96b8e7d4-09f3-490d-a721-bb3b13a500a9">


