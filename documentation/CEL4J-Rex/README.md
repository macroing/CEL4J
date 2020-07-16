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
/**
 * Rex supports comments and whitespaces. Both end-of-line comments and traditional comments are supported, as in Java.
 */
<Alternation> = (<Concatenation> & (%CommentOrWhitespace* & '|' & %CommentOrWhitespace* & <Concatenation>)*);
<Concatenation> = (<Matcher> & (%CommentOrWhitespace* & ('&' | ',' | ';') & %CommentOrWhitespace* & <Matcher>)*);
<Expression> = (<Alternation>);
<Group> = (%CommentOrWhitespace* & '(' & %CommentOrWhitespace* & <Alternation> & %CommentOrWhitespace* & ')' & %CommentOrWhitespace* & <Repetition>?);
<GroupReference> = (%CommentOrWhitespace* & '<' & %CommentOrWhitespace* & (%JavaIdentifierStart & %JavaIdentifierPart* | %Digit+) & %CommentOrWhitespace* & '>' & %CommentOrWhitespace* & <Repetition>?);
<GroupReferenceDefinition> = (%CommentOrWhitespace* & '<' & %CommentOrWhitespace* & %JavaIdentifierStart & %JavaIdentifierPart* & %CommentOrWhitespace* & '>' & %CommentOrWhitespace* & '=' & %CommentOrWhitespace* & <Group>);
<Matcher> = (<Group> | <GroupReferenceDefinition> | <GroupReference> | <Regex> | <Symbol> | <SymbolClass>);
<Regex> = (%CommentOrWhitespace* & %RegexLiteral);
<Repetition> = (%CommentOrWhitespace* & ('*' | '+' | '?'));
<Symbol> = (%CommentOrWhitespace* & (%CharacterLiteral | %StringLiteral) & %CommentOrWhitespace* & <Repetition>?);
<SymbolClass> = (%CommentOrWhitespace* & '%' & %JavaIdentifierStart & %JavaIdentifierPart* & %CommentOrWhitespace* & <Repetition>?);
<Expression>
```