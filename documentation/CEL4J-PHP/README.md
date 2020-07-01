CEL4J PHP
=========
CEL4J PHP provides functionality to generate PHP source code.

Supported Features
------------------
* An API that models PHP source code in an object-oriented manner.
* An API used to generate object-oriented PHP class models from simple definitions.

Packages
--------
* `org.macroing.cel4j.php.generator` - The PHP Generator API.
* `org.macroing.cel4j.php.model` - The PHP Model API.

Examples
--------
Below follows a few examples that demonstrates various features in CEL4J PHP.

#### Model Example
The following example demonstrates how PHP source code can be generated.

First the Java program has to be created.

```java
import org.macroing.cel4j.php.model.PClass;
import org.macroing.cel4j.php.model.PConstructor;
import org.macroing.cel4j.php.model.PDocument;
import org.macroing.cel4j.php.model.PMethod;
import org.macroing.cel4j.php.model.PParameterArgument;
import org.macroing.cel4j.php.model.PReturnType;
import org.macroing.cel4j.php.model.PType;
import org.macroing.cel4j.php.model.PValue;

public class ModelExample {
    public static void main(String[] args) {
        PConstructor pConstructor = new PConstructor();
        pConstructor.getBlock().addLine("");
        pConstructor.setFinal(true);
        pConstructor.setPublic(true);
        
        PMethod pMethod = new PMethod();
        pMethod.addParameterArgument(new PParameterArgument("name", PType.STRING, PValue.valueOf("you")));
        pMethod.getBlock().addLine("echo sprintf('Hello %s!', $name);");
        pMethod.setEnclosedByClass();
        pMethod.setFinal(true);
        pMethod.setName("greet");
        pMethod.setPublic(true);
        pMethod.setReturnType(new PReturnType(PType.VOID));
        
        PClass pClass = new PClass();
        pClass.addMethod(pMethod);
        pClass.setConstructor(pConstructor);
        pClass.setFinal(true);
        pClass.setName("Greeter");
        
        PDocument pDocument = new PDocument();
        pDocument.addClass(pClass);
        pDocument.getBlock().addLine("$greeter = new Greeter();");
        pDocument.getBlock().addLine("$greeter->greet('John Doe');");
        pDocument.setNamespace(PDocument.toNamespace("com", "example", "greeter"));
        
        System.out.println(pDocument.write());
    }
}
```

When the above Java program is executed, it emits the following PHP source code to standard output.

```php
<?php
    namespace com\example\greeter;
    
    final class Greeter {
        public final function __construct() {
            
        }
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        
        public final function greet(string $name = 'you'): void {
            echo sprintf('Hello %s!', $name);
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    
    $greeter = new Greeter();
    $greeter->greet('John Doe');
```

#### Generator Example
The following example demonstrates how PHP class models can be generated with simple definitions.

First the Java program has to be created.

```java
import java.util.Arrays;

import org.macroing.cel4j.php.generator.Model;
import org.macroing.cel4j.php.generator.Property;
import org.macroing.cel4j.php.generator.PropertyBuilder;
import org.macroing.cel4j.php.model.PDocument;
import org.macroing.cel4j.php.model.PType;
import org.macroing.cel4j.php.util.Document;
import org.macroing.cel4j.php.util.Pair

public class GeneratorExample {
    public static void main(String[] args) {
        Model model = new Model("person", "Person");
        model.addProperty(new Property(PType.INT, PropertyBuilder.newDefault(), "age", "Age"));
        model.addProperty(new Property(PType.STRING, PropertyBuilder.newStringLength(1, 20), "first_name", "FirstName"));
        model.addProperty(new Property(PType.STRING, PropertyBuilder.newStringLength(1, 20), "last_name", "LastName"));
        model.addProperty(new Property(PType.STRING, PropertyBuilder.newStringOption(Arrays.asList(new Pair<>("female", "GenderFemale"), new Pair<>("male", "GenderMale"))), "gender", "Gender"));
        model.addProperty(new Property(PType.valueOf("School"), PropertyBuilder.newDefault(), "school", "School"));
        
        PDocument pDocument = model.toDocument(PDocument.toNamespace("com", "example", "person"));
        
        System.out.println(pDocument.write(new Document(), true));
    }
}
```

When the above Java program is executed, it emits the following PHP source code to standard output.

```php
<?php
    namespace com\example\person;
    
    final class Person {
        public const AGE           = 'age';
        public const FIRST_NAME    = 'first_name';
        public const GENDER        = 'gender';
        public const GENDER_FEMALE = 'female';
        public const GENDER_MALE   = 'male';
        public const LAST_NAME     = 'last_name';
        public const PERSON        = 'person';
        public const SCHOOL        = 'school';
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        
        private $age;
        private $firstName;
        private $gender;
        private $lastName;
        private $school;
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        
        public final function __construct() {
            $this->setAge();
            $this->setFirstName();
            $this->setGender();
            $this->setLastName();
            $this->setSchool();
        }
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        
        public final function copy(): Person {
            $person = new Person();
            $person->setAge($this->getAge());
            $person->setFirstName($this->getFirstName());
            $person->setGender($this->getGender());
            $person->setLastName($this->getLastName());
            $person->setSchool($this->getSchool());
            
            return $person;
        }
        
        public final function getAge(): ?int {
            return $this->age;
        }
        
        public final function getFirstName(): ?string {
            return $this->firstName;
        }
        
        public final function getGender(): ?string {
            return $this->gender;
        }
        
        public final function getLastName(): ?string {
            return $this->lastName;
        }
        
        public final function getSchool(): ?School {
            return $this->school;
        }
        
        public final function hasAge(): bool {
            return $this->age !== null;
        }
        
        public final function hasFirstName(): bool {
            return $this->firstName !== null;
        }
        
        public final function hasGender(): bool {
            return $this->gender !== null;
        }
        
        public final function hasLastName(): bool {
            return $this->lastName !== null;
        }
        
        public final function hasSchool(): bool {
            return $this->school !== null;
        }
        
        public final function set(Person $person): void {
            $this->setAge($person->getAge());
            $this->setFirstName($person->getFirstName());
            $this->setGender($person->getGender());
            $this->setLastName($person->getLastName());
            $this->setSchool($person->getSchool());
        }
        
        public final function setAge(?int $age = null): bool {
            return ($this->age = $age) === $age;
        }
        
        public final function setFirstName(?string $firstName = null): bool {
            return ($this->firstName = self::doUpdateStringByLength($firstName, $this->firstName, 1, 20)) === $firstName;
        }
        
        public final function setGender(?string $gender = null): bool {
            return ($this->gender = self::doUpdateStringByOption($gender, $this->gender, [self::GENDER_FEMALE, self::GENDER_MALE])) === $gender;
        }
        
        public final function setLastName(?string $lastName = null): bool {
            return ($this->lastName = self::doUpdateStringByLength($lastName, $this->lastName, 1, 20)) === $lastName;
        }
        
        public final function setSchool(?School $school = null): bool {
            return ($this->school = $school) === $school;
        }
        
        public final function toArray(bool $isIncludingNull = false): array {
            $array = [];
            
            if($isIncludingNull || $this->hasAge()) {
                $array[self::AGE] = $this->getAge();
            }
            
            if($isIncludingNull || $this->hasFirstName()) {
                $array[self::FIRST_NAME] = $this->getFirstName();
            }
            
            if($isIncludingNull || $this->hasGender()) {
                $array[self::GENDER] = $this->getGender();
            }
            
            if($isIncludingNull || $this->hasLastName()) {
                $array[self::LAST_NAME] = $this->getLastName();
            }
            
            if($isIncludingNull || $this->hasSchool()) {
                $array[self::SCHOOL] = ($this->hasSchool() ? $this->getSchool()->toArray($isIncludingNull) : $this->getSchool());
            }
            
            return $array;
        }
        
        public final function toJSON(bool $isIncludingNull = false, bool $isPrettyPrinting = false): string {
            return $isPrettyPrinting ? json_encode($this->toArray($isIncludingNull), JSON_PRETTY_PRINT) : json_encode($this->toArray($isIncludingNull));
        }
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        
        public static final function parseArray(array $array): Person {
            if(array_key_exists(self::PERSON, $array)) {
                $array = $array[self::PERSON];
                $array = is_array($array) ? $array : [];
            }
            
            $person = new Person();
            $person->setAge(self::doParseInt($array, self::AGE));
            $person->setFirstName(self::doParseString($array, self::FIRST_NAME));
            $person->setGender(self::doParseString($array, self::GENDER));
            $person->setLastName(self::doParseString($array, self::LAST_NAME));
            $person->setSchool(self::doParseSchool($array, self::SCHOOL));
            
            return $person;
        }
        
        public static final function parseJSON(string $stringJSON): Person {
            return self::parseArray(json_decode($stringJSON, true));
        }
        
        ////////////////////////////////////////////////////////////////////////////////////////////////////
        
        private static final function doParseInt(array $array, string $key): ?int {
            return array_key_exists($key, $array) ? ($array[$key] !== null ? intval($array[$key]) : null) : null;
        }
        
        private static final function doParseSchool(array $array, string $key): ?School {
            return array_key_exists($key, $array) ? (is_array($array[$key]) ? School::parseArray($array[$key]) : null) : null;
        }
        
        private static final function doParseString(array $array, string $key): ?string {
            return array_key_exists($key, $array) ? ($array[$key] !== null ? strval($array[$key]) : null) : null;
        }
        
        private static final function doUpdateStringByLength(?string $newString, ?string $oldString, int $minimumLength, int $maximumLength): ?string {
            if($newString === null) {
                return $newString;
            } else if(mb_strlen($newString) >= $minimumLength && mb_strlen($newString) <= $maximumLength) {
                return $newString;
            } else {
                return $oldString;
            }
        }
        
        private static final function doUpdateStringByOption(?string $newString, ?string $oldString, array $stringOptions): ?string {
            if($newString === null) {
                return $newString;
            } else if(in_array($newString, $stringOptions, true)) {
                return $newString;
            } else {
                return $oldString;
            }
        }
    }
```