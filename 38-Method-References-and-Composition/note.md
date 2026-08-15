# Java Functional Programming

## The big idea

Java provides many ready-made functional interfaces in the `java.util.function` package. These interfaces let you represent common kinds of behavior without creating custom interfaces every time.

A functional interface has one abstract method, so a lambda can provide its implementation.

```java
Function<Integer, Integer> square = x -> x * x;
```

Here:

- `Function<Integer, Integer>` is the target functional interface.
- `x -> x * x` is the lambda.
- `apply(x)` runs the lambda.

## One-sentence summary

Java functional programming uses functional interfaces, lambdas, method references, and function composition to pass, combine, and reuse behavior in a concise way.

## Functional interface recap

A functional interface contains exactly one abstract method. It may still contain default and static methods.

```java
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);
}
```

Usage:

```java
Calculator addition = (a, b) -> a + b;

System.out.println(addition.calculate(4, 5)); // 9
```

The `@FunctionalInterface` annotation gives compile-time protection. If another abstract method is added, the compiler reports an error.

In real code, custom functional interfaces are useful sometimes, but Java’s built-in interfaces cover most common situations.

## The four core interfaces

The PDF introduces four important functional interfaces:

```text
Function   → takes input and returns output
Consumer   → takes input and returns nothing
Supplier   → takes no input and supplies output
Predicate  → takes input and returns true or false
```

## `Function<T, R>`

`Function<T, R>` takes a value of type `T` and returns a value of type `R`. Its main method is:

```java
R apply(T value);
```

The first generic type is the input type. The second is the output type.

```java
Function<Integer, Integer> square = x -> x * x;

System.out.println(square.apply(5)); // 25
```

The flow is:

```text
5 → square function → 25
```

Another example:

```java
Function<String, Integer> length =
    text -> text.length();

System.out.println(length.apply("Java")); // 4
```

Here:

```text
Input  → String
Output → Integer
```

A function transforms one value into another.

```text
T → R
```

## `Consumer<T>`

A `Consumer<T>` accepts a value but does not return anything. Its main method is:

```java
void accept(T value);
```

Use it when you want to perform an action such as printing, logging, saving, or sending a value somewhere.

```java
Consumer<String> printer =
    text -> System.out.println(text);

printer.accept("Hello"); // Hello
```

The flow is:

```text
String → action
```

There is no output returned to the caller.

Example:

```java
Consumer<String> logger =
    message -> System.out.println("LOG: " + message);

logger.accept("User logged in");
```

## `Supplier<T>`

The PDF shows `Supplier` as a functional interface that takes no input and supplies a value.

Its main method is:

```java
T get();
```

Example:

```java
Supplier<Double> randomNumber =
    () -> Math.random();

System.out.println(randomNumber.get());
```

The flow is:

```text
No input → Supplier → value
```

Another example:

```java
Supplier<String> greeting =
    () -> "Hello";

System.out.println(greeting.get()); // Hello
```

A supplier is useful when a value should be created only when requested.

## `Predicate<T>`

A `Predicate<T>` takes an input and returns a boolean. Its main method is:

```java
boolean test(T value);
```

Example:

```java
Predicate<Integer> isEven =
    x -> x % 2 == 0;

System.out.println(isEven.test(10)); // true
System.out.println(isEven.test(7));  // false
```

The flow is:

```text
T → true / false
```

Another example:

```java
Predicate<String> isLong =
    text -> text.length() > 5;

System.out.println(isLong.test("Java"));        // false
System.out.println(isLong.test("Programming")); // true
```

## Functional interface family

The basic interfaces have related versions for different purposes:

| Interface        | Input | Output  | Main method |
| ---------------- | ----- | ------- | ----------- |
| `Function<T, R>` | One   | One     | `apply()`   |
| `Consumer<T>`    | One   | None    | `accept()`  |
| `Supplier<T>`    | None  | One     | `get()`     |
| `Predicate<T>`   | One   | Boolean | `test()`    |

## Primitive functional interfaces

Using generic interfaces with primitive values can cause boxing and unboxing.

```java
Function<Integer, Integer> square =
    x -> x * x;
```

Here, Java may need to convert between:

```text
int ↔ Integer
```

That conversion is called boxing and unboxing. It creates extra overhead.

Java provides primitive-specialized functional interfaces to avoid that overhead, especially in performance-sensitive loops.

### Primitive Function family

```java
IntFunction<R>
LongFunction<R>
DoubleFunction<R>
```

Examples:

```java
IntFunction<String> describe =
    number -> "Number: " + number;

System.out.println(describe.apply(10));
```

There are also interfaces where the result is a primitive:

```java
ToIntFunction<T>
ToLongFunction<T>
ToDoubleFunction<T>
```

Example:

```java
ToIntFunction<String> length =
    text -> text.length();

System.out.println(length.applyAsInt("Java")); // 4
```

### Primitive Consumer family

```java
IntConsumer
LongConsumer
DoubleConsumer
```

Example:

```java
IntConsumer printer =
    number -> System.out.println(number);

printer.accept(10);
```

### Primitive Supplier family

```java
IntSupplier
LongSupplier
DoubleSupplier
```

Example:

```java
IntSupplier randomValue =
    () -> 42;

System.out.println(randomValue.getAsInt());
```

### Primitive Predicate family

```java
IntPredicate
LongPredicate
DoublePredicate
```

Example:

```java
IntPredicate positive =
    number -> number > 0;

System.out.println(positive.test(5)); // true
```

### Primitive Operator family

Operators take primitive input and return the same primitive type.

```java
IntUnaryOperator
LongUnaryOperator
DoubleUnaryOperator
IntBinaryOperator
LongBinaryOperator
DoubleBinaryOperator
```

Example:

```java
IntUnaryOperator doubleValue =
    x -> x * 2;

System.out.println(doubleValue.applyAsInt(5)); // 10
```

A binary operator takes two values:

```java
IntBinaryOperator add =
    (a, b) -> a + b;

System.out.println(add.applyAsInt(4, 6)); // 10
```

## Method references

A method reference is a shorter form of a lambda that only calls an existing method.

Lambda:

```java
x -> System.out.println(x)
```

Method reference:

```java
System.out::println
```

The method reference means:

```text
Use System.out.println whenever the consumer receives a value.
```

## Static method references

A static method reference uses:

```text
ClassName::staticMethodName
```

Example:

```java
Function<Integer, Integer> absoluteValue =
    Math::abs;

System.out.println(absoluteValue.apply(-10)); // 10
```

The lambda version is:

```java
Function<Integer, Integer> absoluteValue =
    x -> Math.abs(x);
```

Both mean the same thing.

## Instance method references

An instance method reference uses:

```text
object::methodName
```

Example:

```java
Consumer<String> printer =
    System.out::println;

printer.accept("Hello");
```

The lambda version is:

```java
Consumer<String> printer =
    text -> System.out.println(text);
```

The PDF also shows an instance method reference for checking a string:

```java
Predicate<String> emptyCheck =
    String::isEmpty;

System.out.println(emptyCheck.test(""));      // true
System.out.println(emptyCheck.test("Hello")); // false
```

This is equivalent to:

```java
Predicate<String> emptyCheck =
    text -> text.isEmpty();
```

## Constructor references

A constructor reference uses:

```text
ClassName::new
```

It represents a call to that class’s constructor.

Example:

```java
Supplier<ArrayList<String>> listCreator =
    ArrayList::new;

ArrayList<String> list = listCreator.get();
```

The lambda version is:

```java
Supplier<ArrayList<String>> listCreator =
    () -> new ArrayList<>();
```

Both create a new `ArrayList`.

Constructor references are useful in factory-style code.

```java
Supplier<Student> creator =
    Student::new;
```

This works when `Student` has a no-argument constructor.

For a constructor with parameters:

```java
Function<String, StringBuilder> creator =
    StringBuilder::new;

StringBuilder builder = creator.apply("Hello");
```

## Lambda and method-reference comparison

| Lambda                       | Method reference      |
| ---------------------------- | --------------------- |
| `x -> System.out.println(x)` | `System.out::println` |
| `x -> Math.abs(x)`           | `Math::abs`           |
| `x -> x.isEmpty()`           | `String::isEmpty`     |
| `() -> new ArrayList<>()`    | `ArrayList::new`      |

Use a method reference when the lambda only forwards its input to an existing method.

## Function composition

Function composition means connecting multiple functions into a pipeline.

For example:

```text
Input → trim → uppercase → length → Output
```

Instead of writing every step separately, you can combine functions.

## `andThen()`

`andThen()` executes the current function first and the next function second.

```text
f.andThen(g)
```

means:

```text
x → f(x) → g(f(x))
```

This is left-to-right execution.

### Example

```java
Function<Integer, Integer> addTwo =
    x -> x + 2;

Function<Integer, Integer> multiplyThree =
    x -> x * 3;

Function<Integer, Integer> pipeline =
    addTwo.andThen(multiplyThree);

System.out.println(pipeline.apply(4)); // 18
```

Execution:

```text
4
→ addTwo: 4 + 2 = 6
→ multiplyThree: 6 * 3 = 18
```

Mathematically:

```text
g(f(x))
```

## `compose()`

`compose()` executes the supplied function first and the current function second.

```text
f.compose(g)
```

means:

```text
x → g(x) → f(g(x))
```

This is right-to-left execution.

### Example

```java
Function<Integer, Integer> addTwo =
    x -> x + 2;

Function<Integer, Integer> multiplyThree =
    x -> x * 3;

Function<Integer, Integer> pipeline =
    addTwo.compose(multiplyThree);

System.out.println(pipeline.apply(4)); // 14
```

Execution:

```text
4
→ multiplyThree: 4 * 3 = 12
→ addTwo: 12 + 2 = 14
```

### Difference

```java
addTwo.andThen(multiplyThree)
```

means:

```text
multiplyThree(addTwo(x))
```

```java
addTwo.compose(multiplyThree)
```

means:

```text
addTwo(multiplyThree(x))
```

## Function pipeline example

The PDF shows a pipeline using uppercase conversion and string length.

```java
Function<String, String> upperCase =
    String::toUpperCase;

Function<String, Integer> length =
    String::length;

Function<String, Integer> result =
    upperCase.andThen(length);

System.out.println(result.apply("java")); // 4
```

Execution:

```text
"java"
→ "JAVA"
→ 4
```

Another example:

```java
Function<String, String> trim =
    String::trim;

Function<String, String> upperCase =
    String::toUpperCase;

Function<String, Integer> length =
    String::length;

Function<String, Integer> pipeline =
    trim.andThen(upperCase).andThen(length);

System.out.println(pipeline.apply("  java  ")); // 4
```

Execution:

```text
"  java  "
→ "java"
→ "JAVA"
→ 4
```

## Type compatibility in composition

The output type of the first function must match the input type of the next function.

This works:

```java
Function<String, String> upper =
    String::toUpperCase;

Function<String, Integer> length =
    String::length;

Function<String, Integer> pipeline =
    upper.andThen(length);
```

Why?

```text
upper:  String → String
length: String → Integer
```

The output of `upper` is a `String`, which is exactly what `length` needs.

This does not work if the types do not connect:

```text
Function<Integer, String>
and
Function<Double, Boolean>
```

The first function returns `String`, but the second requires `Double`.

## Predicate chaining

Predicates return boolean values, so they can be combined like logical conditions.

The main methods are:

```java
and()
or()
negate()
```

### `and()`

```java
Predicate<Integer> positive =
    x -> x > 0;

Predicate<Integer> even =
    x -> x % 2 == 0;

Predicate<Integer> positiveEven =
    positive.and(even);

System.out.println(positiveEven.test(4));  // true
System.out.println(positiveEven.test(-4)); // false
System.out.println(positiveEven.test(3));  // false
```

The combined condition means:

```text
positive AND even
```

### `or()`

```java
Predicate<Integer> small =
    x -> x < 10;

Predicate<Integer> large =
    x -> x > 100;

Predicate<Integer> outsideMiddle =
    small.or(large);

System.out.println(outsideMiddle.test(5));   // true
System.out.println(outsideMiddle.test(50));  // false
System.out.println(outsideMiddle.test(200)); // true
```

### `negate()`

```java
Predicate<Integer> even =
    x -> x % 2 == 0;

Predicate<Integer> odd =
    even.negate();

System.out.println(odd.test(5)); // true
System.out.println(odd.test(4)); // false
```

This is equivalent to logical `NOT`.

### Predicate pipeline

```java
Predicate<String> notEmpty =
    text -> !text.isEmpty();

Predicate<String> longText =
    text -> text.length() > 5;

Predicate<String> valid =
    notEmpty.and(longText);

System.out.println(valid.test("Java"));        // false
System.out.println(valid.test("Programming")); // true
```

## Consumer chaining

Consumers perform actions and return nothing. Their `andThen()` method allows multiple actions to be performed on the same input.

```java
Consumer<String> printName =
    name -> System.out.println(name);

Consumer<String> printUpperCase =
    name -> System.out.println(name.toUpperCase());

Consumer<String> printBoth =
    printName.andThen(printUpperCase);

printBoth.accept("java");
```

Output:

```text
java
JAVA
```

Execution:

```text
Input: "java"
→ print original value
→ print uppercase value
```

The first consumer runs before the second consumer.

## Bi-functional interfaces

The `Bi` interfaces handle two input values.

```text
BiFunction<T, U, R>  → two inputs, one output
BiConsumer<T, U>     → two inputs, no output
BiPredicate<T, U>    → two inputs, boolean output
```

## `BiFunction<T, U, R>`

A `BiFunction` takes two inputs and returns one output.

```java
BiFunction<Integer, Integer, Integer> add =
    (a, b) -> a + b;

System.out.println(add.apply(10, 20)); // 30
```

Type flow:

```text
Integer + Integer → Integer
```

Another example:

```java
BiFunction<String, String, String> join =
    (first, last) -> first + " " + last;

System.out.println(join.apply("John", "Doe"));
// John Doe
```

## `BiConsumer<T, U>`

A `BiConsumer` takes two inputs and returns nothing.

```java
BiConsumer<String, Integer> printAge =
    (name, age) -> System.out.println(name + " is " + age);

printAge.accept("Ravi", 25);
```

Output:

```text
Ravi is 25
```

## `BiPredicate<T, U>`

A `BiPredicate` takes two inputs and returns a boolean.

```java
BiPredicate<Integer, Integer> greater =
    (a, b) -> a > b;

System.out.println(greater.test(10, 5)); // true
System.out.println(greater.test(3, 8));  // false
```

Another example:

```java
BiPredicate<String, String> sameLength =
    (a, b) -> a.length() == b.length();

System.out.println(sameLength.test("Java", "Code")); // true
```

## Imperative vs declarative programming

The video ends by comparing imperative and declarative programming.

### Imperative style

Imperative code explains **how** to complete a task. It usually contains loops, variables, counters, and conditions.

Example:

```java
List<Integer> evenNumbers = new ArrayList<>();

for (Integer number : numbers) {
    if (number % 2 == 0) {
        evenNumbers.add(number);
    }
}
```

This tells Java step by step:

1. Create an empty list.
2. Visit each number.
3. Check whether it is even.
4. Add it if the condition is true.

### Declarative style

Declarative code explains **what** result you want.

```java
List<Integer> evenNumbers =
    numbers.stream()
           .filter(number -> number % 2 == 0)
           .toList();
```

This says:

```text
Give me the numbers that are even.
```

The code does not focus on the loop mechanics.

### Important distinction

Declarative code does not mean there is no process underneath. The loop still happens internally. The difference is that you describe the desired operation instead of manually controlling every step.

## Full example

```java
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        Function<String, String> trim =
            String::trim;

        Function<String, String> upper =
            String::toUpperCase;

        Function<String, Integer> length =
            String::length;

        Function<String, Integer> pipeline =
            trim.andThen(upper).andThen(length);

        System.out.println(pipeline.apply("  java  ")); // 4

        Predicate<Integer> positive =
            x -> x > 0;

        Predicate<Integer> even =
            x -> x % 2 == 0;

        Predicate<Integer> valid =
            positive.and(even);

        System.out.println(valid.test(8));  // true
        System.out.println(valid.test(-8)); // false

        Consumer<String> print =
            System.out::println;

        Consumer<String> upperPrint =
            text -> System.out.println(text.toUpperCase());

        print.andThen(upperPrint).accept("java");

        BiFunction<Integer, Integer, Integer> add =
            Integer::sum;

        System.out.println(add.apply(4, 6)); // 10
    }
}
```

## Common mistakes

- `Function<T, R>` returns a result; `Consumer<T>` does not.
- `Predicate<T>` returns `boolean`; it is not used for general transformation.
- `Supplier<T>` takes no input.
- Primitive functional interfaces exist to reduce boxing and unboxing overhead.
- `andThen()` and `compose()` execute in opposite orders.
- Method references work only when an existing method matches the target functional interface.
- A constructor reference must match a constructor’s parameter shape.
- Function composition requires compatible input and output types.
- A lambda still needs a target functional interface.
- A functional interface may contain default and static methods, but only one abstract method.
- Declarative code describes the desired result; it does not mean the underlying work disappears.

## Key takeaways

- Java provides built-in functional interfaces for most common behavior patterns.
- `Function` transforms input into output.
- `Consumer` performs an action without returning a result.
- `Supplier` creates or supplies a result without input.
- `Predicate` tests a condition and returns `true` or `false`.
- Primitive versions reduce boxing and unboxing overhead.
- Method references make simple lambdas shorter.
- Constructor references refer to constructors using `ClassName::new`.
- `andThen()` runs left to right.
- `compose()` runs right to left.
- Predicates can be combined with `and()`, `or()`, and `negate()`.
- Consumers can be combined with `andThen()`.
- `BiFunction`, `BiConsumer`, and `BiPredicate` handle two inputs.
- Declarative programming focuses on what should happen instead of how to manually perform it.

## Minimal self-test

1. What is the difference between `Function`, `Consumer`, `Supplier`, and `Predicate`?
2. Why do primitive functional interfaces exist?
3. Rewrite this lambda as a method reference:

```java
x -> System.out.println(x)
```

4. What is the difference between `andThen()` and `compose()`?
5. What is the result of this pipeline?

```java
Function<Integer, Integer> f = x -> x + 2;
Function<Integer, Integer> g = x -> x * 3;

f.andThen(g).apply(4);
```

6. What is the result of this pipeline?

```java
f.compose(g).apply(4);
```

7. How do `Predicate.and()`, `Predicate.or()`, and `Predicate.negate()` work?
8. Why does a `Consumer` return `void`?
9. What is the difference between `BiFunction` and `Function`?
10. Convert an imperative even-number loop into a declarative stream pipeline.
11. Why might `IntFunction` be preferable to `Function<Integer, R>` in a performance-sensitive loop?
12. When can a lambda be replaced by a method reference?

## What to learn next

The natural next topics are:

1. Java Streams: `filter`, `map`, `reduce`, and `collect`.
2. `Optional` and functional-style null handling.
3. Method references in more detail.
4. `Comparator.comparing()` and multi-level sorting.
5. Lambda variable capture and effectively final variables.
6. Lazy evaluation and short-circuiting in stream pipelines.
