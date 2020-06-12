CEL4J Java Decompiler
=====================
CEL4J Java Decompiler provides a decompiler that can decompile Java bytecode into Java source code.

Supported Features
------------------
* An API that defines the general contract for a Java decompiler.
* A simple implementation of the API.

Packages
--------
* `org.macroing.cel4j.java.decompiler` - The Java Decompiler API.

Examples
--------
Below follows a few examples that demonstrates various features in CEL4J Java Decompiler.

#### Decompilation Example
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
            decompiler.addClass(clazz, Consumers.file("generated", clazz));
            decompiler.addDecompilerObserver(DecompilerObserver.print());
            decompiler.getDecompilerConfiguration().setDisplayingInstructions(true);
            decompiler.decompile();
        } catch(DecompilationException e) {
            e.printStackTrace();
        }
    }
}
```