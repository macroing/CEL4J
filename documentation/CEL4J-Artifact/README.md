CEL4J Artifact
==============
CEL4J Artifact provides a `ScriptEngine` implementation that evaluates Java source code.

Supported Features
------------------
* Full Java-compatibility. Supports any Java source code that can be executed from within a Java method.
* Some of the more common packages from the standard Java library are imported by default.
* Catching an `Exception` in a catch-clause is possible but not necessary. A default catch-clause exists.
* It is possible to use return statements but not necessary. By default `null` is returned.
* The `eval(String)` method allows the script to evaluate Java source code on its own.
* Import statements in the script will be pre-processed and added to the compilation unit.
* Package statements in the script will be pre-processed and added to the compilation unit.
* The `set(String, Object)` method can be used to set a variable in the `ScriptContext`.
* The `get(String)` method can be used to get a variable from the `ScriptContext`.
* Variables starting with `$` are cast to their type, as they are stored in the `ScriptContext`.
* A script has access to the `ScriptContext` using the variable `scriptContext`.
* The `Artifact` class can be used for programmatic configuration on a global level.
* Bundled with an interactive scripting tool that can be run in CLI- or GUI mode.

Packages
--------
* `org.macroing.cel4j.artifact` - Provides the `ScriptEngine` implementation and the interactive scripting tool.

Examples
--------
Below follows a few examples that demonstrates various features in CEL4J Artifact.

#### HelloWorld Example
The following example demonstrates how CEL4J Artifact can be used in Java:

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

#### Script Example
The following example demonstrates how CEL4J Artifact scripts can be written:

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
CEL4J Artifact has four types of import statement configurations. These are the required import statements that always will be present, the default import statements that may be overridden, the global import statements that can be added via an API and the local import statements that can be added from within a script.

#### Required Import Statements
These import statements are required.

* `import javax.script.*;`
* `import org.macroing.cel4j.artifact.*;`

#### Default Import Statements
The default import statements can be separated into two groups; the internally defined and the externally defined.

The internally defined default import statements are subject to change. It may turn out that too many ambiguous classes and interfaces are imported.

The externally defined default import statements overrides the internally defined. To override them, use a system property called `org.macroing.cel4j.artifact.import`. The value supplied should be a filename pointing to a file with one import statement per line.

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

#### Global Import Statements
These import statements can be added from the `Artifact` class.

#### Local Import Statements
These import statements can be added from within a script.

Interactive Scripting Tool
--------------------------
The interactive scripting tool can be configured to run as a CLI- or a GUI-program. By default it runs as a CLI-program with Artifact as the scripting language.

The program uses a set of flags for configuration:
* `-e [extension]` - This flag sets the filename extension for the scripting language to use. By default `java` is used.
* `-g` - This flag starts the GUI-program, which has support for some syntax highlighting.

Below follows two examples for running the interactive scripting tool:
```bash
java -cp org.macroing.cel4j.jar org.macroing.cel4j.artifact.Main
java -cp org.macroing.cel4j.jar org.macroing.cel4j.artifact.Main -e java -g
```

Dependencies
------------
 - [Java 8 + tools.jar](http://www.java.com).