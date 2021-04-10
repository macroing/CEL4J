CEL4J Java Source
=================
CEL4J Java Source provides functionality for reading, manipulating and writing Java source code.

Supported Features
------------------
* An API that models the lexical structure of a compilation unit.

Packages
--------
* `org.macroing.cel4j.java.source` - The Java Source API.
* `org.macroing.cel4j.java.source.lexical` - The Java Source Lexical API.
* `org.macroing.cel4j.java.source.syntactic` - The Java Source Syntactic API.

Examples
--------
Below follows a few examples that demonstrates various features in CEL4J Java Source.

#### Parse Input Example
The following example demonstrates how an `Input` can be parsed from a `String`, as well as how to iterate through its `InputElement` instances.

```java
import org.macroing.cel4j.java.source.lexical.Input;
import org.macroing.cel4j.java.source.lexical.InputElement;

public class ParseInputExample {
    public static void main(String[] args) {
//      Create a StringBuilder with content representing a CompilationUnit:
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("package com.example.helloworld;\n");
        stringBuilder.append("\n");
        stringBuilder.append("public class HelloWorld {\n");
        stringBuilder.append("    public static void main(String[] args) {\n");
        stringBuilder.append("        System.out.println(\"Hello, World!\");\n");
        stringBuilder.append("    }\n");
        stringBuilder.append("}");
        
//      Create an Input given the String-representation of the StringBuilder:
        Input input = Input.parseInput(stringBuilder.toString());
        
//      Iterate through all InputElements contained in the Input instance and print their source code to standard output:
        for(InputElement inputElement : input) {
            System.out.print(inputElement.getSourceCode());
        }
    }
}
```