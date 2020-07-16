CEL4J Rex
=========
CEL4J Rex provides functionality to match text such as source code.

Supported Features
------------------
* `Alternation` - A class that can match alternations, such as `"A" | "B"`.
* `Concatenation` - A class that can match concatenations, such as `"A" & "B"`.
* `Expression` - A class that can match a full expression, such as `"A"`.
* `Group` - A class that can match a group, such as `("A")`.
* `GroupReference` - A class that can match a group via a reference to that group, such as `<GroupReferenceName>`.
* `GroupReferenceDefinition` - A class that defines a reference to a group, such as `<GroupReferenceName> = ("A")`.
* `Regex` - A class that can match Regex patterns, such as `/AB+C/`.
* `Repetition` - A class that provides the repetition bounds for different `Matcher` types, such as `"A"?` or `%Digit+`.
* `Symbol` - A class that can match a symbol, such as `"ABC"` or `'A'`.
* `SymbolClass` - A class that can match a set of predefined symbol classes, such as `%Digit`.

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
        Expression expression = Expression.parse("\"Hello\" & ',' & ' ' & \"World\" & '!'");
        
        MatchResult matchResult = expression.match("Hello, World!");
        
        System.out.println(matchResult);
    }
}
```

#### Features Example
The following example demonstrates various features in Rex by matching itself.

```
//Defines 'Alternation':
<Alternation> = (<Concatenation> & (%CommentOrWhitespace* & '|' & %CommentOrWhitespace* & <Concatenation>)*)
&
//Defines 'Concatenation':
<Concatenation> = (<Matcher> & (%CommentOrWhitespace* & '&' & %CommentOrWhitespace* & <Matcher>)*)
&
//Defines 'Expression':
<Expression> = (<Alternation>)
&
//Defines 'Group':
<Group> = (%CommentOrWhitespace* & '(' & %CommentOrWhitespace* & <Alternation> & %CommentOrWhitespace* & ')' & %CommentOrWhitespace* & <Repetition>?)
&
//Defines 'GroupReference':
<GroupReference> = (%CommentOrWhitespace* & '<' & %CommentOrWhitespace* & (%JavaIdentifierStart & %JavaIdentifierPart* | %Digit+) & %CommentOrWhitespace* & '>' & %CommentOrWhitespace* & <Repetition>?)
&
//Defines 'GroupReferenceDefinition':
<GroupReferenceDefinition> = (%CommentOrWhitespace* & '<' & %CommentOrWhitespace* & %JavaIdentifierStart & %JavaIdentifierPart* & %CommentOrWhitespace* & '>' & %CommentOrWhitespace* & '=' & %CommentOrWhitespace* & <Group>)
&
//Defines 'Matcher':
<Matcher> = (<Group> | <GroupReferenceDefinition> | <GroupReference> | <Regex> | <Symbol> | <SymbolClass>)
&
//Defines 'Regex':
<Regex> = (%CommentOrWhitespace* & %RegexLiteral)
&
//Defines 'Repetition':
<Repetition> = (%CommentOrWhitespace* & ('*' | '+' | '?'))
&
//Defines 'Symbol':
<Symbol> = (%CommentOrWhitespace* & (%CharacterLiteral | %StringLiteral) & %CommentOrWhitespace* & <Repetition>?)
&
//Defines 'SymbolClass':
<SymbolClass> = (%CommentOrWhitespace* & '%' & %JavaIdentifierStart & %JavaIdentifierPart* & %CommentOrWhitespace* & <Repetition>?)
&
//Evaluates 'Expression':
<Expression>
```