# Using PMD

Pick a Java project from Github (see the [instructions](../sujet.md) for suggestions). Run PMD on its source code using any ruleset. Describe below an issue found by PMD that you think should be solved (true positive) and include below the changes you would add to the source code. Describe below an issue found by PMD that is not worth solving (false positive). Explain why you would not solve this issue.

## Answer

> As **Project**, I chose that of [Apache Commons Collection](https://github.com/apache/commons-collections)

> I executed **PMD** on this project with the *AvoidDeeplyNestedIfStmts* rule of the Design category. The **Design** category rules help to discover design problems.
> The rule **AvoidDeeplyNestedIfStmts** is defined by the following Java class: [click here](https://github.com/pmd/pmd/blob/master/pmd-java/src/main/java/net/sourceforge/pmd/lang/java/rule/design/AvoidDeeplyNestedIfStmtsRule.java)
>This rule will allow us to detect deeply nested if-then. Indeed, we must avoid creating deeply nested if-then instructions because they are more difficult to read and prone to errors to maintain.
> ```
> <rule ref="category/java/design.xml/AvoidDeeplyNestedIfStmts">
>    <properties>
>        <property name="problemDepth" value="3" />
>    </properties>
> </rule>
> ```

> The execution of this rule on the project's code source has allowed the problems whose paths are indicated by the following image:
> 
>> <img width="1476" alt="image" src="https://github.com/ZieAmara/VV-ESIR-TP2/assets/90223980/f1ed6e40-8a42-452a-8aa1-802eeb9f3475">

> **Problem detected and that should be solved: *true positive***
>
>> The first path of the image of the detected problems leads to the following code:
>>
>> <img width="627" alt="image" src="https://github.com/ZieAmara/VV-ESIR-TP2/assets/90223980/090e0a58-bb41-46a9-8ae9-5ba34549d629">
>
>> To solve this problem I propose to use a switch case instead of several if instructions. I propose the following change:
>> 
>> <img width="650" alt="image" src="https://github.com/ZieAmara/VV-ESIR-TP2/assets/90223980/22e8356e-7599-488a-91c5-1ce2d2ea8b67">

> **Problem detected and not worth solving: *false positive***
>
>> The last path of the image of the detected problems leads to the following code:
>>
>> <img width="788" alt="image" src="https://github.com/ZieAmara/VV-ESIR-TP2/assets/90223980/47c84b9f-6269-41e4-80dc-7000358d6421">
>>
>> This problem is not worth solving. Because indeed, the "AvoidDeeplyNestedIfStmts" rule suggests avoiding deeply nested control structures to improve the readability and maintainability of the code. However, in the code I shared, there are only two levels of nesting (if inside a for loop), which is generally considered acceptable in terms of complexity. In addition, the code is relatively concise, the nested conditions seem justified to deal with the specific case of a key collision in the data structure we manipulate, which can be considered a good practice for managing specific situations.

