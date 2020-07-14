CEL4J Rex
=========
CEL4J Rex provides functionality to match text such as source code.

Supported Features
------------------
* `Alternation` - A class that can match alternations.
* `Concatenation` - A class that can match concatenations.
* `Expression` - A class that can match a full expression.
* `Group` - A class that can match a group.
* `GroupReference` - A class that can match a group via a reference to that group.
* `GroupReferenceDefinition` - A class that defines a `GroupReference`.
* `Symbol` - A class that can match a symbol.
* `SymbolClass` - A class that can match a set of predefined symbol classes.

Packages
--------
* `org.macroing.cel4j.rex` - The Rex API.

Examples
--------
Below follows a few examples that demonstrates various features in CEL4J Rex.

#### Hello World Example
The following example demonstrates how the `Expression` and `MatchResult` classes can be used.

```java
import org.macroing.cel4j.rex.Expression;
import org.macroing.cel4j.rex.MatchResult;

public class HelloWorldExample {
    public static void main(String[] args) {
        String input = "<HelloWorld> = (\"Hello\" & ',' & ' ' & \"World\" & '!') & <HelloWorld>";
        String source = "Hello, World!";
        
        Expression expression = Expression.parse(input);
        
        MatchResult matchResult = expression.match(source);
        
        System.out.println(matchResult);
    }
}
```

#### Features Example
The following example demonstrates various features in Rex by matching a subset of itself.

```
<Alternation> = (<Concatenation> & ('|' & <Concatenation>)*)
&
<Concatenation> = (<Matcher> & ('&' & <Matcher>)*)
&
<Expression> = (<Alternation>)
&
<Group> = ('(' & <Alternation> & ')' & <Repetition>?)
&
<GroupReference> = ('<' & (%JavaIdentifierStart & %JavaIdentifierPart* | %Digit+) & '>' & <Repetition>?)
&
<GroupReferenceDefinition> = ('<' & %JavaIdentifierStart & %JavaIdentifierPart* & '>' & '=' & <Group>)
&
<Matcher> = (<Group> | <GroupReferenceDefinition> | <GroupReference> | <Symbol> | <SymbolClass>)
&
<Repetition> = ('*' | '+' | '?')
&
<Symbol> = ('"' & (%LetterOrDigit | ' ' | ',' | '!')* & '"' | "'" & (%LetterOrDigit | ' ' | ',' | '!')* & "'")
&
<SymbolClass> = ('%' & %JavaIdentifierStart & %JavaIdentifierPart*)
&
<Expression>
```

The Rex expression above can match the input supplied in the `HelloWorldExample`, as long as the escape sequences for the double quotes are removed.

```
<HelloWorld>=("Hello"&','&' '&"World"&'!')&<HelloWorld>
```