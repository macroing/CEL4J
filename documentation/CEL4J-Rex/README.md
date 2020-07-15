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
* `Regex` - A class that can match Regex.
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
The following example demonstrates various features in Rex by matching itself.

```
<Alternation> = (<Concatenation> & (%Whitespace* & '|' & %Whitespace* & <Concatenation>)*)
&
<Concatenation> = (<Matcher> & (%Whitespace* & '&' & %Whitespace* & <Matcher>)*)
&
<Expression> = (<Alternation>)
&
<Group> = (%Whitespace* & '(' & %Whitespace* & <Alternation> & %Whitespace* & ')' & %Whitespace* & <Repetition>?)
&
<GroupReference> = (%Whitespace* & '<' & %Whitespace* & (%JavaIdentifierStart & %JavaIdentifierPart* | %Digit+) & %Whitespace* & '>' & %Whitespace* & <Repetition>?)
&
<GroupReferenceDefinition> = (%Whitespace* & '<' & %Whitespace* & %JavaIdentifierStart & %JavaIdentifierPart* & %Whitespace* & '>' & %Whitespace* & '=' & %Whitespace* & <Group>)
&
<Matcher> = (<Group> | <GroupReferenceDefinition> | <GroupReference> | <Regex> | <Symbol> | <SymbolClass>)
&
<Regex> = (%Whitespace* & %RegexLiteral)
&
<Repetition> = (%Whitespace* & ('*' | '+' | '?'))
&
<Symbol> = (%Whitespace* & (%CharacterLiteral | %StringLiteral))
&
<SymbolClass> = (%Whitespace* & '%' & %JavaIdentifierStart & %JavaIdentifierPart* & %Whitespace* & <Repetition>?)
&
<Expression>
```