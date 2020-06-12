CEL4J Scanner
=============
CEL4J Scanner provides scanning functionality for data in various forms.

Supported Features
------------------
* An API that provides scanning functionality for data in various forms.

Packages
--------
* `org.macroing.cel4j.scanner` - The Scanner API.

Examples
--------
Below follows a few examples that demonstrates various features in CEL4J Scanner.

#### Text Scanner Example
The following example demonstrates how a `TextScanner` can be used to scan text.

```java
import java.util.regex.Pattern;

import org.macroing.cel4j.scanner.TextScanner;

public class TextScannerExample {
    public static void main(String[] args) {
        String string = "\"Hello, World!\"";
        
        StringBuilder stringBuilder = new StringBuilder();
        
        Pattern pattern = Pattern.compile("[a-zA-Z]+|,|\\s|!");
        
        TextScanner textScanner = new TextScanner(string);
        
        while(textScanner.nextRegex(pattern) || textScanner.nextCharacter('"')) {
            System.out.print(textScanner.consumption());
            
            textScanner.consume(stringBuilder);
        }
        
        System.out.println();
        System.out.print(stringBuilder);
    }
}
```