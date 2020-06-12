CEL4J Java Binary
=================
CEL4J Java Binary provides functionality for reading, manipulating and writing Java bytecode.

Supported Features
------------------
* An API that models the general parts of a `ClassFile` structure.
* An API that models specific `attribute_info` structures.
* An API that models specific `cp_info` structures.
* An API that models descriptors and signatures.
* An API that provides capabilities to read the `ClassFile` models from byte sequences.

Packages
--------
* `org.macroing.cel4j.java.binary.classfile` - The Java Binary ClassFile API.
* `org.macroing.cel4j.java.binary.classfile.attributeinfo` - The Java Binary ClassFile AttributeInfo API.
* `org.macroing.cel4j.java.binary.classfile.cpinfo` - The Java Binary ClassFile CPInfo API.
* `org.macroing.cel4j.java.binary.classfile.string` - The Java Binary ClassFile String API.
* `org.macroing.cel4j.java.binary.reader` - The Java Binary Reader API.

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
        ClassFile classFile = classFileReader.read(String.class);
        
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
        ClassFile classFile = classFileReader.read(String.class);
        
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
        ClassFile classFile = classFileReader.read(java.util.ArrayList.class);
        
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