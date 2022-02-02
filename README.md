CEL4J - Version 0.6.0
=====================
CEL4J is a Code Engineering Library for Java. It provides functionality for HTML, Java, JSON and PHP.

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

 - [CEL4J Artifact](https://github.com/macroing/CEL4J/tree/master/documentation/CEL4J-Artifact) provides a `ScriptEngine` implementation that evaluates Java source code.
 - [CEL4J HTML](https://github.com/macroing/CEL4J/tree/master/documentation/CEL4J-HTML) provides functionality to generate HTML source code.
 - [CEL4J Java Binary](https://github.com/macroing/CEL4J/tree/master/documentation/CEL4J-Java-Binary) provides functionality for reading, manipulating and writing Java bytecode.
 - [CEL4J Java Decompiler](https://github.com/macroing/CEL4J/tree/master/documentation/CEL4J-Java-Decompiler) provides a decompiler that can decompile Java bytecode into Java source code.
 - [CEL4J Java Model](https://github.com/macroing/CEL4J/tree/master/documentation/CEL4J-Java-Model) provides a model for types, constructors, fields and methods in Java.
 - [CEL4J Java Source](https://github.com/macroing/CEL4J/tree/master/documentation/CEL4J-Java-Source) provides functionality for reading, manipulating and writing Java source code.
 - [CEL4J JSON](https://github.com/macroing/CEL4J/tree/master/documentation/CEL4J-JSON) provides functionality for reading, manipulating and writing JSON.
 - [CEL4J Lexer](https://github.com/macroing/CEL4J/tree/master/documentation/CEL4J-Lexer) provides lexing functionality for different formal languages.
 - [CEL4J Node](https://github.com/macroing/CEL4J/tree/master/documentation/CEL4J-Node) provides functionality for modeling data types that can be filtered and traversed in various ways.
 - [CEL4J PHP](https://github.com/macroing/CEL4J/tree/master/documentation/CEL4J-PHP) provides functionality to generate PHP source code.
 - [CEL4J Rex](https://github.com/macroing/CEL4J/tree/master/documentation/CEL4J-Rex) provides functionality to match text such as source code.
 - [CEL4J Scanner](https://github.com/macroing/CEL4J/tree/master/documentation/CEL4J-Scanner) provides scanning functionality for data in various forms.
 - [CEL4J Utilities](https://github.com/macroing/CEL4J/tree/master/documentation/CEL4J-Utilities) provides unrelated functionality used by the library.

Dependencies
------------
 - [Java 8](http://www.java.com).

Note
----
This library has not reached version 1.0.0 and been released to the public yet. Therefore, you can expect that backward incompatible changes are likely to occur between commits. When this library reaches version 1.0.0, it will be tagged and available on the "releases" page. At that point, backward incompatible changes should only occur when a new major release is made.