CEL4J Artifact
==============
CEL4J Artifact provides a ScriptEngine implementation called Artifact, that evaluates a super-set of Java source code.

Supported Features
------------------
* Full Java-compatibility. You can use any Java source code that can be executed from within a Java method.
* Some of the more common packages from the standard Java library are imported by default.
* You may override most of the imported packages by specifying the system property ``-Dorg.macroing.cel4j.artifact.import="file.txt"``, which points to a file with one import statement per line.
* You don't have to catch any `Exception`s thrown by a method. A default catch-clause will take care of that for you.
* You can return anything you want from the script, but are not required to.
* You can evaluate Java source code as part of the script itself, using the `eval(String)` method.
* To import packages you can use import statements like `import javax.swing.*;`.
* To change package you can use package statements like `package com.company;`.
* You can set a variable to the `ScriptContext` using the `set(String, Object)` method.
* You can get a variable from the `ScriptContext` using the `get(String)` method.
* Variables starting with `$` are treated in a special way. They are substituted with a cast to their type, as they are stored in the `ScriptContext`.
* A script has access to the `ScriptContext` using the variable `scriptContext`.
* It's possible to dump the generated source code to system out by using the system property `-Dorg.macroing.cel4j.artifact.dump=true`.
* You can use the ``Artifact`` class to configure parts of Artifact programmatically on a global level.
* Artifact comes with an interactive scripting tool that can be run in CLI- or GUI mode.

Examples
--------
The following example demonstrates how you can use Artifact:

```java
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class HelloWorld {
    public static void main(String[] args) {
        try {
//          Create a new ScriptEngineManager:
            ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
            
//          Get the ScriptEngine for Artifact and evaluate a script:
            ScriptEngine scriptEngine = scriptEngineManager.getEngineByExtension("java");
            scriptEngine.eval("System.out.println(\"Hello, World!\");");
        } catch(ScriptException e) {
            e.printStackTrace();
        }
    }
}
```

The following example demonstrates some of the features provided by Artifact:

```java
//Changes the package:
package org.macroing.cel4j.artifact.example;

//Imports all public members of a class using a static import statement:
import static java.lang.Math.*;

double a = 3.0D;
double b = 5.0D;

//Here we use two of the public members that were statically imported:
double max = max(a, b);
double min = min(a, b);

//The eval(String) method can be called from within the script:
Object result = eval(String.format("return %s + %s;", max, min));

//You can set a value to the ScriptContext, given a key:
set("result", result);

//You can get the value from the ScriptContext, given its key:
result = get("result");

//If the key "result" was already associated with a value prior to the evaluation of this script, you could use $result to access it instead.
//$result would be converted to the following code, if it's of type Double like shown above:
//Double.class.cast(scriptContext.getBindings(ScriptContext.ENGINE_SCOPE).get("result"))

System.out.println(result);

//You may optionally return a result from the script:
return result;
```

Import Statements
-----------------
Artifact has four types of import statement configurations. These are the required import statements that always will be present, the default import statements that may be overridden, the global import statements that can be added via an API and the local import statements that can be added from within a script.

### Required Import Statements
These import statements are required.

* `import javax.script.*;`
* `import org.macroing.cel4j.artifact.*;`

### Default Import Statements
The default import statements can be separated into two groups; the internally defined and the externally defined.

The internally defined default import statements are subject to change. It may turn out that too many ambiguous classes and interfaces are imported.

The externally defined default import statements overrides the internally defined. To override them, use a system property called ``org.macroing.cel4j.artifact.import``. The value supplied should be a filename pointing to a file with one import statement per line.

The internally defined default import statements are currently the following:
* `import static java.lang.Math.*;`
* `import java.awt.*;`
* `import java.awt.color.*;`
* `import java.awt.event.*;`
* `import java.awt.font.*;`
* `import java.awt.geom.*;`
* `import java.awt.image.*;`
* `import java.lang.ref.*;`
* `import java.lang.reflect.*;`
* `import java.math.*;`
* `import java.net.*;`
* `import java.nio.*;`
* `import java.nio.channels.*;`
* `import java.nio.charset.*;`
* `import java.nio.file.*;`
* `import java.nio.file.attribute.*;`
* `import java.text.*;`
* `import java.util.*;`
* `import java.util.concurrent.*;`
* `import java.util.concurrent.atomic.*;`
* `import java.util.concurrent.locks.*;`
* `import java.util.jar.*;`
* `import java.util.logging.*;`
* `import java.util.prefs.*;`
* `import java.util.regex.*;`
* `import java.util.zip.*;`
* `import javax.swing.*;`
* `import javax.swing.border.*;`
* `import javax.swing.colorchooser.*;`
* `import javax.swing.event.*;`
* `import javax.swing.filechooser.*;`
* `import javax.swing.table.*;`
* `import javax.swing.text.*;`
* `import javax.swing.tree.*;`
* `import javax.swing.undo.*;`
* `import javax.tools.*;`

### Global Import Statements
These import statements can be added from the `Artifact` class.

### Local Import Statements
These import statements can be added from within a script.

Getting Started
---------------
### Apache Ant
To clone the CEL4J repository, build the project and run Artifact in GUI-mode, you can type the following in Git Bash.
```bash
git clone https://github.com/macroing/CEL4J.git
cd CEL4J
ant
cd distribution/org.macroing.cel4j
java -cp org.macroing.cel4j.jar org.macroing.cel4j.artifact.Main -g
```

Interactive Scripting Tool
--------------------------
The interactive scripting tool can be configured to run as either a CLI- or a GUI-program. By default it runs as a CLI-program.

The program uses a set of flags to configure itself. So you can change them to fit your needs. The currently supported flags are the following.

 - The `-e [extension]` flag sets the filename extension for the scripting language to use.
 - The `-g` flag starts the GUI-program.

When you omit the `-e [extension]` flag, Artifact will automatically be used as scripting language. This is just like using `-e java` or `-e .java`.

When you omit the `-g` flag, the CLI-program will automatically be used.

The GUI-program has support for some syntax highlighting.

Dependencies
------------
 - [Java 8 + tools.jar](http://www.java.com).