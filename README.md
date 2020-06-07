CEL4J - Version 0.5.0
=====================
CEL4J is a Code Engineering Library for Java. It provides functionality for JSON, Java and PHP.

Getting Started
---------------
To clone this repository and build the project, you can type the following in Git Bash. You need Apache Ant though.
```bash
git clone https://github.com/macroing/CEL4J.git
cd CEL4J
ant
```

Supported Features
------------------
This library consists of a few different more or less unrelated subprojects. They are presented below.

#### CEL4J Java Binary
* `org.macroing.cel4j.java.binary.classfile` - An API that models the general parts of a `ClassFile` structure.
* `org.macroing.cel4j.java.binary.classfile.attributeinfo` - An API that models specific `attribute_info` structures.
* `org.macroing.cel4j.java.binary.classfile.cpinfo` - An API that models specific `cp_info` structures.
* `org.macroing.cel4j.java.binary.classfile.string` - An API that models descriptors and signatures.
* `org.macroing.cel4j.java.binary.reader` - An API that provides capabilities to read the `ClassFile` models from streams of bytes.

#### CEL4J Java Decompiler
* `org.macroing.cel4j.java.decompiler` - An API that provides the general contract for a Java decompiler.
* `org.macroing.cel4j.java.decompiler.simple` - An API that provides a simple Java decompiler implementation.

#### CEL4J Java Source
* `org.macroing.cel4j.java.source.lexical` - An API that models the lexical structure of a `CompilationUnit`.

#### CEL4J JSON
* `org.macroing.cel4j.json` - An API that contains an object-oriented JSON model, as well as parsing and formatting functionality.

#### CEL4J Node
* `org.macroing.cel4j.node` - An API that models data types that can be filtered and traversed in various ways.

#### CEL4J PHP
* `org.macroing.cel4j.php.generator` - An API used to generate object-oriented PHP class models from simple definitions.
* `org.macroing.cel4j.php.generator.propertybuilder` - An API that contains `PropertyBuilder` implementations.
* `org.macroing.cel4j.php.model` - An API that models PHP source code in an object-oriented manner.

#### CEL4J Utilities
* `org.macroing.cel4j.util` - An API that provides unrelated functionality used by the library.

Examples
--------
Below follows a few examples that demonstrates various features in CEL4J Java Binary.

#### Read and Write ClassFile Example
The following example demonstrates how a `ClassFileReader` can be used to read a `ClassFile` given a `Class`. Then the `ClassFile` is written to a file.

```java
import java.io.File;

import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.reader.ClassFileReader;

public class ReadAndWriteClassFileExample {
    public static void main(String[] args) {
//      Create a ClassFileReader to read a ClassFile instance:
        ClassFileReader classFileReader = new ClassFileReader();
        
//      Read the ClassFile instance for the String class using the ClassFileReader instance:
        ClassFile classFile = classFileReader.readClassFile(String.class);
        
//      Write the ClassFile instance to a file:
        classFile.write(new File("bin/java/lang/String.class"));
    }
}
```

#### Print Instructions Example
The following example demonstrates how a `ClassFile`, or any other `Node`, can be traversed in various ways and operated on.

```java
import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.CodeAttribute;
import org.macroing.cel4j.java.binary.reader.ClassFileReader;
import org.macroing.cel4j.node.NodeFilter;

public class PrintInstructionsExample {
    public static void main(String[] args) {
//      Create a ClassFileReader to read a ClassFile instance:
        ClassFileReader classFileReader = new ClassFileReader();
        
//      Read the ClassFile instance for the String class using the ClassFileReader instance:
        ClassFile classFile = classFileReader.readClassFile(String.class);
        
//      Example #1 - Using Java 8 Stream APIs:
        classFile.getMethodInfos().forEach(methodInfo -> methodInfo.getAttributeInfos().stream().filter(node -> node instanceof CodeAttribute).map(node -> CodeAttribute.class.cast(node)).forEach(codeAttribute -> {
            System.out.println();
            
            codeAttribute.getInstructions().forEach(instruction -> System.out.println(instruction));
        }));
        
//      Example #2 - Using the default NodeFilter.filter(Node) method and Java 8 Stream APIs:
        NodeFilter.filter(classFile).stream().filter(node -> node instanceof CodeAttribute).map(node -> CodeAttribute.class.cast(node)).forEach(codeAttribute -> {
            System.out.println();
            
            codeAttribute.getInstructions().forEach(instruction -> System.out.println(instruction));
        });
        
//      Example #3 - Using the more advanced NodeFilter.filter(Node, NodeFilter, Class) method:
        NodeFilter.filter(classFile, NodeFilter.any(), CodeAttribute.class).forEach(codeAttribute -> {
            System.out.println();
            
            codeAttribute.getInstructions().forEach(instruction -> System.out.println(instruction));
        });
        
//      Example #4 - Using the CodeAttribute.filter(Node) method:
        CodeAttribute.filter(classFile).forEach(codeAttribute -> {
            System.out.println();
            
            codeAttribute.getInstructions().forEach(instruction -> System.out.println(instruction));
        });
    }
}
```

#### Descriptor and Signature Example
The following example demonstrates how some of the descriptors and signatures in a `ClassFile` instance can be parsed.

```java
import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.string.ClassSignature;
import org.macroing.cel4j.java.binary.classfile.string.FieldDescriptor;
import org.macroing.cel4j.java.binary.classfile.string.MethodDescriptor;
import org.macroing.cel4j.java.binary.classfile.string.Signature;
import org.macroing.cel4j.java.binary.reader.ClassFileReader;

public class DescriptorAndSignatureExample {
    public static void main(String[] args) {
//      Create a ClassFileReader to read a ClassFile instance:
        ClassFileReader classFileReader = new ClassFileReader();
        
//      Read the ClassFile instance for the ArrayList class using the ClassFileReader instance:
        ClassFile classFile = classFileReader.readClassFile(java.util.ArrayList.class);
        
//      Parse the optional ClassSignature instance in the ClassFile and, if present, print it in external form to standard output:
        ClassSignature.parseClassSignatureOptionally(classFile).ifPresent(classSignature -> System.out.printf("%-17s %s%n", "ClassSignature:", classSignature.toExternalForm()));
        
//      Parse the FieldDescriptor instances in the ClassFile and print them in external form to standard output:
        FieldDescriptor.parseFieldDescriptors(classFile).forEach(fieldDescriptor -> System.out.printf("%-17s %s%n", "FieldDescriptor:", fieldDescriptor.toExternalForm()));
        
//      Parse the MethodDescriptor instances in the ClassFile and print them in external form to standard output:
        MethodDescriptor.parseMethodDescriptors(classFile).forEach(methodDescriptor -> System.out.printf("%-17s %s%n", "MethodDescriptor:", methodDescriptor.toExternalForm()));
        
//      Parse the Signature instances in the ClassFile and print them in external form to standard output:
        Signature.parseSignatures(classFile).forEach(signature -> System.out.printf("%-17s %s%n", "Signature:", signature.toExternalForm()));
    }
}
```

Dependencies
------------
 - [Java 8](http://www.java.com).

Note
----
This library has not been properly released yet. This means, even though it says it's version 1.0.0 in the build.xml file and all Java source code files, it should not be treated as such. When this library gets released, it will be tagged and available on the "releases" page.
