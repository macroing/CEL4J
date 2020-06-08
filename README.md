CEL4J - Version 0.5.2
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
This library consists of several projects. They are presented below.

#### CEL4J Java Binary
The APIs in this project allows you to read, manipulate and write Java bytecode.

* `org.macroing.cel4j.java.binary.classfile` - An API that models the general parts of a `ClassFile` structure.
* `org.macroing.cel4j.java.binary.classfile.attributeinfo` - An API that models specific `attribute_info` structures.
* `org.macroing.cel4j.java.binary.classfile.cpinfo` - An API that models specific `cp_info` structures.
* `org.macroing.cel4j.java.binary.classfile.string` - An API that models descriptors and signatures.
* `org.macroing.cel4j.java.binary.reader` - An API that provides capabilities to read the `ClassFile` models from streams of bytes.

#### CEL4J Java Decompiler
The APIs in this project allows you to decompile Java bytecode into Java source code.

* `org.macroing.cel4j.java.decompiler` - An API that provides the general contract for a Java decompiler.
* `org.macroing.cel4j.java.decompiler.simple` - An API that provides a simple Java decompiler implementation.

#### CEL4J Java Source
The APIs in this project allows you to parse, manipulate and format Java source code.

* `org.macroing.cel4j.java.source.lexical` - An API that models the lexical structure of a `CompilationUnit`.

#### CEL4J JSON
The APIs in this project allows you to parse, manipulate and format JSON.

* `org.macroing.cel4j.json` - An API that contains an object-oriented JSON model, as well as parsing and formatting functionality.

#### CEL4J Node
* `org.macroing.cel4j.node` - An API that models data types that can be filtered and traversed in various ways.

#### CEL4J PHP
The APIs in this project allows you to generate PHP source code.

* `org.macroing.cel4j.php.generator` - An API used to generate object-oriented PHP class models from simple definitions.
* `org.macroing.cel4j.php.generator.propertybuilder` - An API that contains `PropertyBuilder` implementations.
* `org.macroing.cel4j.php.model` - An API that models PHP source code in an object-oriented manner.

#### CEL4J Utilities
* `org.macroing.cel4j.util` - An API that provides unrelated functionality used by the library.

Examples
--------
#### CEL4J Java Binary
Below follows a few examples that demonstrates various features in CEL4J Java Binary.

###### Read and Write ClassFile Example
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

###### Print Instructions Example
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

###### Descriptor and Signature Example
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

#### CEL4J Java Decompiler
Below follows a few examples that demonstrates various features in CEL4J Java Decompiler.

###### Decompilation Example
The following example demonstrates how a `Decompiler` can be used to decompile a `Class` to a file.

```java
import org.macroing.cel4j.java.decompiler.Consumers;
import org.macroing.cel4j.java.decompiler.DecompilationException;
import org.macroing.cel4j.java.decompiler.Decompiler;
import org.macroing.cel4j.java.decompiler.DecompilerObserver;

public class DecompilationExample {
    public static void main(String[] args) {
        try {
            Class<?> clazz = Integer.class;
            
            Decompiler decompiler = Decompiler.newInstance();
            decompiler.addClass(clazz, Consumers.file("tmp", clazz));
            decompiler.addDecompilerObserver(DecompilerObserver.print());
            decompiler.getDecompilerConfiguration().setDisplayingInstructions(true);
            decompiler.decompile();
        } catch(DecompilationException e) {
            e.printStackTrace();
        }
    }
}
```

#### CEL4J Java Source
Below follows a few examples that demonstrates various features in CEL4J Java Source.

###### Parse Input Example
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

#### CEL4J JSON
Below follows a few examples that demonstrates various features in CEL4J JSON.

###### Parse And Format Example
The following example demonstrates how the `JSONParser` can be used to parse a `JSONType` and format it.

First the Java program has to be created.
```java
import org.macroing.cel4j.json.JSONParser;
import org.macroing.cel4j.json.JSONType;
import org.macroing.cel4j.json.JSONUtilities;

public class ParseAndFormatExample {
    public static void main(String[] args) {
        JSONParser jSONParser = new JSONParser();
        
        JSONType jSONType = jSONParser.parse("{\"name\": \"C\", \"files\": [{\"name\": \"old-files\", \"files\": [{\"name\": \"Test.txt\", \"files\": []}]}, {\"name\": \"new-files\", \"files\": []}]}");
        
        String string = JSONUtilities.format(jSONType);
        
        System.out.println(string);
    }
}
```

When the above Java program is executed, it emits the JSON to standard output.
```
{
  "name": "C",
  "files": [
    {
      "name": "old-files",
      "files": [
        {
          "name": "Test.txt",
          "files": [
          ]
        }
      ]
    },
    {
      "name": "new-files",
      "files": [
      ]
    }
  ]
}
```

#### CEL4J PHP
Below follows a few examples that demonstrates various features in CEL4J PHP.

###### Model Example
The following example demonstrates how PHP source code can be generated.

First the Java program has to be created.
```java
import org.macroing.cel4j.php.model.PClass;
import org.macroing.cel4j.php.model.PConstructor;
import org.macroing.cel4j.php.model.PDocument;
import org.macroing.cel4j.php.model.PMethod;
import org.macroing.cel4j.php.model.PParameterArgument;
import org.macroing.cel4j.php.model.PReturnType;
import org.macroing.cel4j.php.model.PType;
import org.macroing.cel4j.php.model.PValue;

public class ModelExample {
    public static void main(String[] args) {
        PConstructor pConstructor = new PConstructor();
        pConstructor.getBlock().addLine("");
        pConstructor.setFinal(true);
        pConstructor.setPublic(true);
        
        PMethod pMethod = new PMethod();
        pMethod.addParameterArgument(new PParameterArgument("name", PType.STRING, PValue.valueOf("you")));
        pMethod.getBlock().addLine("echo sprintf('Hello %s!', $name);");
        pMethod.setEnclosedByClass(true);
        pMethod.setFinal(true);
        pMethod.setName("greet");
        pMethod.setPublic(true);
        pMethod.setReturnType(new PReturnType(PType.VOID));
        
        PClass pClass = new PClass();
        pClass.addMethod(pMethod);
        pClass.setConstructor(pConstructor);
        pClass.setFinal(true);
        pClass.setName("Greeter");
        
        PDocument pDocument = new PDocument();
        pDocument.addClass(pClass);
        pDocument.getBlock().addLine("$greeter = new Greeter();");
        pDocument.getBlock().addLine("$greeter->greet('John Doe');");
        pDocument.setNamespace(PDocument.toNamespace("com", "example", "greeter"));
        
        System.out.println(pDocument.write());
    }
}
```

When the above Java program is executed, it emits the following PHP source code to standard output.
```php
<?php
    namespace com\example\greeter;
    
    final class Greeter {
        public final function __construct() {
            
        }
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        
        public final function greet(string $name = 'you'): void {
            echo sprintf('Hello %s!', $name);
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    
    $greeter = new Greeter();
    $greeter->greet('John Doe');
```

###### Generator Example
The following example demonstrates how PHP class models can be generated with simple definitions.

First the Java program has to be created.
```java
import org.macroing.cel4j.php.generator.Model;
import org.macroing.cel4j.php.generator.Property;
import org.macroing.cel4j.php.generator.propertybuilder.DefaultPropertyBuilder;
import org.macroing.cel4j.php.generator.propertybuilder.StringLengthPropertyBuilder;
import org.macroing.cel4j.php.generator.propertybuilder.StringOptionPropertyBuilder;
import org.macroing.cel4j.php.generator.propertybuilder.StringOptionPropertyBuilder.StringOption;
import org.macroing.cel4j.php.model.PDocument;
import org.macroing.cel4j.php.model.PType;
import org.macroing.cel4j.php.util.Document;

public class GeneratorExample {
    public static void main(String[] args) {
        Model model = new Model("person", "Person");
        model.addProperty(new Property(PType.INT, new DefaultPropertyBuilder(), "age", "Age"));
        model.addProperty(new Property(PType.STRING, new StringLengthPropertyBuilder(1, 20), "first_name", "FirstName"));
        model.addProperty(new Property(PType.STRING, new StringLengthPropertyBuilder(1, 20), "last_name", "LastName"));
        model.addProperty(new Property(PType.STRING, new StringOptionPropertyBuilder(new StringOption("female", "GenderFemale"), new StringOption("male", "GenderMale")), "gender", "Gender"));
        model.addProperty(new Property(PType.valueOf("School"), new DefaultPropertyBuilder(), "school", "School"));
        
        PDocument pDocument = model.toDocument(PDocument.toNamespace("com", "example", "person"));
        
        System.out.println(pDocument.write(new Document(), true));
    }
}
```

When the above Java program is executed, it emits the following PHP source code to standard output.
```php
<?php
    namespace com\example\person;
    
    final class Person {
        public const AGE           = 'age';
        public const FIRST_NAME    = 'first_name';
        public const GENDER        = 'gender';
        public const GENDER_FEMALE = 'female';
        public const GENDER_MALE   = 'male';
        public const LAST_NAME     = 'last_name';
        public const PERSON        = 'person';
        public const SCHOOL        = 'school';
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        
        private $age;
        private $firstName;
        private $gender;
        private $lastName;
        private $school;
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        
        public final function __construct() {
            $this->setAge();
            $this->setFirstName();
            $this->setGender();
            $this->setLastName();
            $this->setSchool();
        }
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        
        public final function copy(): Person {
            $person = new Person();
            $person->setAge($this->getAge());
            $person->setFirstName($this->getFirstName());
            $person->setGender($this->getGender());
            $person->setLastName($this->getLastName());
            $person->setSchool($this->getSchool());
            
            return $person;
        }
        
        public final function getAge(): ?int {
            return $this->age;
        }
        
        public final function getFirstName(): ?string {
            return $this->firstName;
        }
        
        public final function getGender(): ?string {
            return $this->gender;
        }
        
        public final function getLastName(): ?string {
            return $this->lastName;
        }
        
        public final function getSchool(): ?School {
            return $this->school;
        }
        
        public final function hasAge(): bool {
            return $this->age !== null;
        }
        
        public final function hasFirstName(): bool {
            return $this->firstName !== null;
        }
        
        public final function hasGender(): bool {
            return $this->gender !== null;
        }
        
        public final function hasLastName(): bool {
            return $this->lastName !== null;
        }
        
        public final function hasSchool(): bool {
            return $this->school !== null;
        }
        
        public final function set(Person $person): void {
            $this->setAge($person->getAge());
            $this->setFirstName($person->getFirstName());
            $this->setGender($person->getGender());
            $this->setLastName($person->getLastName());
            $this->setSchool($person->getSchool());
        }
        
        public final function setAge(?int $age = null): bool {
            return ($this->age = $age) === $age;
        }
        
        public final function setFirstName(?string $firstName = null): bool {
            return ($this->firstName = self::doUpdateStringByLength($firstName, $this->firstName, 1, 20)) === $firstName;
        }
        
        public final function setGender(?string $gender = null): bool {
            return ($this->gender = self::doUpdateStringByOption($gender, $this->gender, [self::GENDER_FEMALE, self::GENDER_MALE])) === $gender;
        }
        
        public final function setLastName(?string $lastName = null): bool {
            return ($this->lastName = self::doUpdateStringByLength($lastName, $this->lastName, 1, 20)) === $lastName;
        }
        
        public final function setSchool(?School $school = null): bool {
            return ($this->school = $school) === $school;
        }
        
        public final function toArray(bool $isIncludingNull = false): array {
            $array = [];
            
            if($isIncludingNull || $this->hasAge()) {
                $array[self::AGE] = $this->getAge();
            }
            
            if($isIncludingNull || $this->hasFirstName()) {
                $array[self::FIRST_NAME] = $this->getFirstName();
            }
            
            if($isIncludingNull || $this->hasGender()) {
                $array[self::GENDER] = $this->getGender();
            }
            
            if($isIncludingNull || $this->hasLastName()) {
                $array[self::LAST_NAME] = $this->getLastName();
            }
            
            if($isIncludingNull || $this->hasSchool()) {
                $array[self::SCHOOL] = ($this->hasSchool() ? $this->getSchool()->toArray($isIncludingNull) : $this->getSchool());
            }
            
            return $array;
        }
        
        public final function toJSON(bool $isIncludingNull = false, bool $isPrettyPrinting = false): string {
            return $isPrettyPrinting ? json_encode($this->toArray($isIncludingNull), JSON_PRETTY_PRINT) : json_encode($this->toArray($isIncludingNull));
        }
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        
        public static final function parseArray(array $array): Person {
            if(array_key_exists(self::PERSON, $array)) {
                $array = $array[self::PERSON];
                $array = is_array($array) ? $array : [];
            }
            
            $person = new Person();
            $person->setAge(self::doParseInt($array, self::AGE));
            $person->setFirstName(self::doParseString($array, self::FIRST_NAME));
            $person->setGender(self::doParseString($array, self::GENDER));
            $person->setLastName(self::doParseString($array, self::LAST_NAME));
            $person->setSchool(self::doParseSchool($array, self::SCHOOL));
            
            return $person;
        }
        
        public static final function parseJSON(string $stringJSON): Person {
            return self::parseArray(json_decode($stringJSON, true));
        }
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        
        private static final function doParseInt(array $array, string $key): ?int {
            return array_key_exists($key, $array) ? ($array[$key] !== null ? intval($array[$key]) : null) : null;
        }
        
        private static final function doParseSchool(array $array, string $key): ?School {
            return array_key_exists($key, $array) ? (is_array($array[$key]) ? School::parseArray($array[$key]) : null) : null;
        }
        
        private static final function doParseString(array $array, string $key): ?string {
            return array_key_exists($key, $array) ? ($array[$key] !== null ? strval($array[$key]) : null) : null;
        }
        
        private static final function doUpdateStringByLength(?string $newString, ?string $oldString, int $minimumLength, int $maximumLength): ?string {
            if($newString === null) {
                return $newString;
            } else if(mb_strlen($newString) >= $minimumLength && mb_strlen($newString) <= $maximumLength) {
                return $newString;
            } else {
                return $oldString;
            }
        }
        
        private static final function doUpdateStringByOption(?string $newString, ?string $oldString, array $stringOptions): ?string {
            if($newString === null) {
                return $newString;
            } else if(in_array($newString, $stringOptions, true)) {
                return $newString;
            } else {
                return $oldString;
            }
        }
    }
```

Dependencies
------------
 - [Java 8](http://www.java.com).

Note
----
This library has not been properly released yet. This means, even though it says it's version 1.0.0 in all Java source code files, it should not be treated as such. When this library gets released, it will be tagged and available on the "releases" page.
