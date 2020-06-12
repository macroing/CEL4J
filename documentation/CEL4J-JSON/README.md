CEL4J JSON
==========
CEL4J JSON provides functionality for reading, manipulating and writing JSON.

Supported Features
------------------
* An API that contains an object-oriented JSON model.
* An API for parsing the JSON model.
* An API for formatting the JSON model.

Packages
--------
* `org.macroing.cel4j.json` - The JSON API.

Examples
--------
Below follows a few examples that demonstrates various features in CEL4J JSON.

#### Parse And Format Example
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