# Java Lambdas and Functional Interfaces

## The big idea

Java was designed mainly around objects. In the traditional style, behavior is placed inside a class, and you create an object before using that behavior.

Lambdas were introduced to make behavior easier to pass around. Instead of creating a full class just to provide one small piece of logic, you can write the logic directly where it is needed.

```text
Traditional Java:
Behavior → put it inside a class → create an object → pass the object

Modern Java:
Behavior → write a lambda → pass the behavior directly
```

## One-sentence summary

A lambda is a short way to provide the implementation of a functional interface, allowing Java programs to pass behavior as easily as they pass data.

## Why Java needed lambdas

Java is strongly object-oriented. The lecture explains that Java traditionally wraps behavior inside objects.

For example:

```java
class Printer {
    void printMessage() {
        System.out.println("Hello");
    }
}

Printer p = new Printer();
p.printMessage();
```

The logic is simple, but Java requires:

1. A class.
2. A method.
3. An object.
4. A method call.

For a small piece of behavior, this creates unnecessary code.

### The behavior problem

Suppose a method needs some behavior from you:

```java
void executeSomething(??? action) {
    // use action here
}
```

With ordinary object-oriented Java, you cannot directly pass a method. You must wrap the behavior inside an object first.

That is why the lecture says behavior is treated like a **second-class citizen** in traditional Java. Data can be passed directly through variables, but behavior must be placed inside a class or object before it can be passed.

## The chef and recipe analogy

The lecture uses a chef, a person, and a recipe to explain this problem.

- The **chef** is the method that performs an action.
- The **recipe** is the behavior or logic.
- The **person carrying the recipe** is the object.

The awkward situation is that you cannot directly hand the recipe to the chef. You first need to put the recipe inside a person-like object and then pass that object.

```text
Recipe / behavior
        ↓
Put it inside an object
        ↓
Give the object to the method
```

A lambda makes this simpler:

```text
Recipe / behavior
        ↓
Pass it directly as a lambda
```

## Example: passing behavior

Imagine a method that receives an operation:

```java
interface Operation {
    int calculate(int a, int b);
}
```

A traditional implementation is:

```java
class Addition implements Operation {
    @Override
    public int calculate(int a, int b) {
        return a + b;
    }
}
```

Usage:

```java
Operation operation = new Addition();

int result = operation.calculate(10, 20);
System.out.println(result); // 30
```

The behavior is wrapped inside the `Addition` object.

With a lambda:

```java
Operation operation = (a, b) -> a + b;

int result = operation.calculate(10, 20);
System.out.println(result); // 30
```

The lambda gives the behavior directly.

## The real use case: sorting students

The lecture uses sorting custom `Student` objects as the main example.

Suppose we have:

```java
class Student {
    String name;
    int rollNo;
    int marks;

    Student(String name, int rollNo, int marks) {
        this.name = name;
        this.rollNo = rollNo;
        this.marks = marks;
    }
}
```

Now suppose we have a list:

```java
List<Student> students = new ArrayList<>();

students.add(new Student("Ravi", 3, 85));
students.add(new Student("Anita", 1, 92));
students.add(new Student("Karan", 2, 78));
```

The question is: how should the list be sorted?

- By name?
- By roll number?
- By marks?

The list cannot guess your preferred ordering. You must provide the sorting rule.

## Comparable vs Comparator

The lecture distinguishes `Comparable` and `Comparator`.

### `Comparable`

`Comparable` is used when a class defines its own **natural ordering**.

Its main method is:

```java
int compareTo(T other);
```

Example:

```java
class Student implements Comparable<Student> {
    String name;
    int marks;

    @Override
    public int compareTo(Student other) {
        return this.marks - other.marks;
    }
}
```

Now `Student` has one default ordering: by marks.

```java
Collections.sort(students);
```

The ordering is built into the `Student` class.

### `Comparator`

`Comparator` is used when sorting logic should be defined outside the class.

Its method is:

```java
int compare(T first, T second);
```

A comparator is useful when one class needs several possible orderings.

For `Student`, possible comparators include:

- Sort by name.
- Sort by roll number.
- Sort by marks.

## The traditional class approach

The lecture shows that the normal object-oriented approach creates separate classes for each sorting strategy.

### Sort by marks

```java
class SortByMarks implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return s1.marks - s2.marks;
    }
}
```

Usage:

```java
students.sort(new SortByMarks());
```

### Sort by name

```java
class SortByName implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return s1.name.compareTo(s2.name);
    }
}
```

Usage:

```java
students.sort(new SortByName());
```

### Sort by roll number

```java
class SortByRollNumber implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return s1.rollNo - s2.rollNo;
    }
}
```

Usage:

```java
students.sort(new SortByRollNumber());
```

This works, but it creates many small classes. Each class exists mainly to implement one method.

## The evolution of the solution

The lecture presents three stages.

```text
Separate class
      ↓
Anonymous class
      ↓
Lambda expression
```

Each stage reduces unnecessary code.

## Stage 1: separate class

```java
class SortByMarks implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return s1.marks - s2.marks;
    }
}

students.sort(new SortByMarks());
```

This is clear but verbose.

## Stage 2: anonymous class

An anonymous class lets you create the comparator at the place where you need it, without giving the class a name.

```java
students.sort(new Comparator<Student>() {
    @Override
    public int compare(Student s1, Student s2) {
        return s1.marks - s2.marks;
    }
});
```

This removes the separate `SortByMarks` class, but the syntax is still long.

You still have:

- `new Comparator<Student>()`
- an overridden method
- method visibility
- return type
- parameter types
- braces

## Stage 3: lambda

The lambda version keeps only the important behavior:

```java
students.sort((s1, s2) -> s1.marks - s2.marks);
```

The lambda means:

```text
Given s1 and s2,
return s1.marks - s2.marks.
```

The three approaches express the same sorting rule:

```java
// Separate class
students.sort(new SortByMarks());

// Anonymous class
students.sort(new Comparator<Student>() {
    @Override
    public int compare(Student s1, Student s2) {
        return s1.marks - s2.marks;
    }
});

// Lambda
students.sort((s1, s2) -> s1.marks - s2.marks);
```

## Functional interfaces

A **functional interface** is an interface with exactly one abstract method.

Example:

```java
interface Task {
    void execute();
}
```

`Task` is functional because it has one abstract method: `execute()`.

A lambda can provide the implementation of that one abstract method:

```java
Task task = () -> System.out.println("Task executed");

task.execute();
```

The lambda does not need to write the method name. Java knows that it must implement `execute()` because `Task` has only one abstract method.

## `@FunctionalInterface`

You can mark an interface with `@FunctionalInterface`:

```java
@FunctionalInterface
interface Task {
    void execute();
}
```

This annotation gives compile-time protection.

If someone later adds another abstract method:

```java
@FunctionalInterface
interface Task {
    void execute();
    void stop(); // compilation error
}
```

The compiler reports an error because the interface is no longer functional.

This prevents the interface from accidentally becoming incompatible with lambda expressions.

## Why only one abstract method?

Suppose an interface had two abstract methods:

```java
interface Confusing {
    void methodA();
    void methodB();
}
```

Now imagine this lambda:

```java
Confusing c = () -> System.out.println("Hello");
```

Which method should the lambda implement?

- `methodA()`?
- `methodB()`?

Java would not know. That is why a functional interface must have exactly one abstract method.

## Static and default methods

The lecture notes that functional interfaces may still contain static and default methods.

The rule is:

> A functional interface must have exactly one abstract method, but it may have any number of static and default methods.

Example:

```java
@FunctionalInterface
interface Task {
    void execute();

    default void start() {
        System.out.println("Task started");
    }

    static void description() {
        System.out.println("A task interface");
    }
}
```

This is still a functional interface because only `execute()` is abstract.

## Common functional interfaces

The lecture connects functional interfaces with functional programming and mentions interfaces such as `Comparable` and `Predicate`.

Examples from Java include:

```java
Runnable
Comparator<T>
Predicate<T>
Function<T, R>
Consumer<T>
Supplier<T>
```

### `Runnable`

```java
Runnable task = () -> System.out.println("Running");
task.run();
```

`Runnable` has one abstract method: `run()`.

### `Comparator`

```java
Comparator<Student> byMarks =
    (s1, s2) -> s1.marks - s2.marks;
```

The lambda implements `compare(s1, s2)`.

### `Predicate`

A `Predicate<T>` usually tests something and returns `boolean`.

```java
Predicate<Integer> isEven =
    number -> number % 2 == 0;

System.out.println(isEven.test(10)); // true
```

## Lambda syntax

The basic syntax is:

```java
(parameters) -> expression
```

or:

```java
(parameters) -> {
    statements;
}
```

The lecture shows four common forms.

### Multiple parameters

```java
(a, b) -> a + b
```

This receives two values and returns their sum.

Complete example:

```java
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);
}

Calculator addition = (a, b) -> a + b;

System.out.println(addition.calculate(4, 5)); // 9
```

### One parameter

```java
x -> x * x
```

Parentheses are optional for one parameter.

These two forms are equivalent:

```java
x -> x * x
```

```java
(x) -> x * x
```

Example:

```java
Function<Integer, Integer> square = x -> x * x;

System.out.println(square.apply(6)); // 36
```

### No parameters

```java
() -> System.out.println("Hello")
```

Empty parentheses are required because the lambda receives no arguments.

Example:

```java
Runnable greeting =
    () -> System.out.println("Hello");

greeting.run(); // Hello
```

### Multiple statements

If a lambda contains multiple statements, use braces.

```java
(a, b) -> {
    int sum = a + b;
    return sum;
}
```

Example:

```java
Calculator addition = (a, b) -> {
    int sum = a + b;
    return sum;
};
```

When braces are used for a value-returning lambda, an explicit `return` is required.

## Expression vs block lambda

Expression form:

```java
(a, b) -> a + b
```

The result is returned automatically.

Block form:

```java
(a, b) -> {
    int sum = a + b;
    return sum;
}
```

The result must be returned explicitly.

This is invalid:

```java
(a, b) -> {
    int sum = a + b;
}
```

The lambda promises to return a value, but no `return` statement exists.

## Target typing

The lecture explains **target typing**, which is one of the most important ideas behind lambda syntax.

A lambda does not have a complete type by itself. Java uses the surrounding context to determine which functional interface the lambda should implement.

Example:

```java
Comparator<Student> comparator =
    (s1, s2) -> s1.marks - s2.marks;
```

Java knows:

- the target type is `Comparator<Student>`,
- the abstract method is `compare(Student, Student)`,
- `s1` is a `Student`,
- `s2` is a `Student`,
- the result must be an `int`.

That is why you do not need to write the types:

```java
(s1, s2) -> s1.marks - s2.marks
```

Java gets them from the target interface.

### Without target context

This does not work by itself:

```java
var x = (a, b) -> a + b; // invalid
```

Java does not know which functional interface the lambda should implement.

This works:

```java
BinaryOperator<Integer> x = (a, b) -> a + b;
```

Now the target type tells Java what the lambda means.

## Lambda mapping to an interface method

Consider:

```java
Comparator<Student> comparator =
    (s1, s2) -> s1.marks - s2.marks;
```

The mapping is:

```text
Comparator<Student>
        ↓
int compare(Student s1, Student s2)
        ↓
(s1, s2) -> s1.marks - s2.marks
```

The lambda body becomes the implementation of `compare()`.

This is what the lecture means when it says the lambda maps to or gives behavior to the functional interface method.

## Complete sorting example

```java
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Student {
    String name;
    int rollNo;
    int marks;

    Student(String name, int rollNo, int marks) {
        this.name = name;
        this.rollNo = rollNo;
        this.marks = marks;
    }

    @Override
    public String toString() {
        return name + " " + rollNo + " " + marks;
    }
}

public class Main {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();

        students.add(new Student("Ravi", 3, 85));
        students.add(new Student("Anita", 1, 92));
        students.add(new Student("Karan", 2, 78));

        students.sort(
            (s1, s2) -> s1.marks - s2.marks
        );

        System.out.println(students);
    }
}
```

The lambda tells `sort()`:

```text
For two students:
return the first student's marks minus the second student's marks.
```

The result is ascending marks order:

```text
Karan 2 78
Ravi 3 85
Anita 1 92
```

### Sort by name

```java
students.sort(
    (s1, s2) -> s1.name.compareTo(s2.name)
);
```

### Sort by roll number

```java
students.sort(
    (s1, s2) -> s1.rollNo - s2.rollNo
);
```

### Sort in descending marks order

```java
students.sort(
    (s1, s2) -> s2.marks - s1.marks
);
```

## A safer comparison style

Subtracting integers is common in beginner examples:

```java
(s1, s2) -> s1.marks - s2.marks
```

But subtraction can overflow for extreme integer values. A safer production-style version is:

```java
(s1, s2) -> Integer.compare(s1.marks, s2.marks)
```

For descending order:

```java
(s1, s2) -> Integer.compare(s2.marks, s1.marks)
```

For strings:

```java
(s1, s2) -> s1.name.compareTo(s2.name)
```

## What lambdas did not change

Lambdas did not turn Java into a language where methods exist independently like ordinary variables. A lambda still needs a **target functional interface**.

This works:

```java
Runnable r = () -> System.out.println("Hello");
```

This does not:

```java
Object x = () -> System.out.println("Hello"); // invalid
```

The lambda must be converted into an instance of a compatible functional interface.

## Evolution of Java solutions

The complete evolution is:

### Separate class

```java
class SortByMarks implements Comparator<Student> {
    public int compare(Student s1, Student s2) {
        return Integer.compare(s1.marks, s2.marks);
    }
}
```

Usage:

```java
students.sort(new SortByMarks());
```

Problem: too many small classes.

### Anonymous class

```java
students.sort(new Comparator<Student>() {
    @Override
    public int compare(Student s1, Student s2) {
        return Integer.compare(s1.marks, s2.marks);
    }
});
```

Problem: no separate class, but still verbose.

### Lambda

```java
students.sort(
    (s1, s2) -> Integer.compare(s1.marks, s2.marks)
);
```

Benefit: the actual behavior is visible immediately.

## Important misconceptions

- A lambda is not a normal standalone method.
- A lambda needs a functional-interface target.
- A functional interface may contain default and static methods; it only needs exactly one abstract method.
- A lambda is not always automatically faster than an anonymous class.
- Lambdas reduce syntax; they do not remove the underlying interface contract.
- `Comparator` defines external/custom ordering, while `Comparable` defines a class’s natural ordering.
- Parentheses are optional only for one parameter, not for zero or multiple parameters.
- A block lambda returning a value needs an explicit `return`.

## Key takeaways

- Traditional Java wraps behavior inside objects.
- This makes passing small pieces of logic verbose.
- `Comparator` is a practical example because sorting needs a comparison rule.
- Separate comparator classes are clear but repetitive.
- Anonymous classes reduce the number of named classes but still contain boilerplate.
- Lambdas pass behavior in a short form.
- A lambda works through a functional interface.
- A functional interface has exactly one abstract method.
- `@FunctionalInterface` lets the compiler protect that rule.
- Target typing tells Java which interface and parameter types a lambda uses.
- Lambdas can have zero, one, or multiple parameters.
- Multi-line lambdas use braces and normally require `return` when returning a value.
- `Comparable` gives natural ordering.
- `Comparator` gives external and multiple ordering strategies.

## Minimal self-test

1. Why was passing behavior difficult in traditional Java?
2. What is the difference between `Comparable` and `Comparator`?
3. Rewrite a separate `SortByMarks` class as an anonymous class.
4. Rewrite the anonymous comparator as a lambda.
5. Why must a functional interface have exactly one abstract method?
6. What does `@FunctionalInterface` do?
7. Why does Java know that `s1` and `s2` are `Student` objects in this expression?

```java
(s1, s2) -> s1.marks - s2.marks
```

8. What is the difference between these two forms?

```java
(a, b) -> a + b
```

```java
(a, b) -> {
    return a + b;
}
```

9. Why does this fail?

```java
var operation = (a, b) -> a + b;
```

10. Write lambdas to sort students by name, roll number, and marks.

## What to learn next

The natural next topics are:

1. Built-in functional interfaces such as `Predicate`, `Function`, `Consumer`, and `Supplier`.
2. Method references such as `Student::getName`.
3. `Comparator.comparing(...)`.
4. Stream operations such as `filter`, `map`, and `sorted`.
5. Capturing local variables inside lambdas.
6. Lambda expressions versus anonymous classes.
