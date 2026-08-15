# Java Streams: Processing Data as a Pipeline

## The main idea

Traditional Java often uses loops, `if` statements, temporary collections, and manual sorting. This works, but the code can become long as the processing logic grows.

Java Streams were introduced in Java 8 to make data processing more declarative. Instead of explaining every step of the loop, you describe the result you want.

```text
Imperative style:
How should the data be processed?

Declarative style:
What result do I want?
```

## One-sentence summary

A Java Stream is a pipeline that processes data from a source through lazy intermediate operations and finishes with a terminal operation.

## Imperative vs declarative programming

Suppose we have employees and want the names of employees earning more than `50,000`, sorted alphabetically.

### Imperative version

```java
List<String> result = new ArrayList<>();

for (Employee employee : employees) {
    if (employee.getSalary() > 50_000) {
        result.add(employee.getName());
    }
}

Collections.sort(result);
```

This code explains every step:

1. Create a result list.
2. Visit each employee.
3. Check the salary.
4. Add the name if the condition passes.
5. Sort the result.

### Stream version

```java
List<String> result =
    employees.stream()
             .filter(employee -> employee.getSalary() > 50_000)
             .map(Employee::getName)
             .sorted()
             .toList();
```

This describes the desired result:

```text
employees
→ keep employees with salary > 50,000
→ convert employees to names
→ sort names
→ collect them into a list
```

The PDF presents this same transformation from manual loops to a stream pipeline.

## What is a Stream?

A Stream is a tool for processing a sequence of data through a chain of operations.

The data can come from:

- a `List`,
- a `Set`,
- an array,
- individual values,
- an infinite generator,
- a primitive stream such as `IntStream`.

A Stream does not replace the original collection. It processes data from that source.

## Streams are not data structures

This is one of the most important points.

A `List` stores data:

```java
List<Integer> numbers =
    List.of(7, 11, 23, 43, 71, 10);
```

A Stream does not permanently store a second copy of that data:

```java
Stream<Integer> stream = numbers.stream();
```

The stream is a processing view over `numbers`.

```text
Collection
   ↓
Stream view
   ↓
Process elements
   ↓
Result
```

The stream itself is not a replacement for `List`, `Set`, or another data structure.

## Water-pipeline analogy

The PDF compares a Java Stream to a water pipeline.

```text
Source → Filter 1 → Filter 2 → Transformation → Destination
```

For data:

```text
List
  ↓
filter
  ↓
map
  ↓
sorted
  ↓
toList
```

Data enters at the source, passes through operations, and finally reaches a result.

## Stream pipeline architecture

Every stream pipeline has three parts:

```text
Source
   ↓
Intermediate operations
   ↓
Terminal operation
```

### Source

The source provides the original data.

Examples:

```java
numbers.stream();
Arrays.stream(array);
Stream.of(1, 2, 3);
```

### Intermediate operations

Intermediate operations modify or describe the processing pipeline.

Examples:

```java
filter()
map()
sorted()
distinct()
limit()
skip()
```

They are lazy and return another Stream.

```java
Stream<Integer> result =
    numbers.stream()
           .filter(x -> x > 10)
           .map(x -> x * 2);
```

At this point, the stream has not processed the elements yet.

### Terminal operation

A terminal operation completes the pipeline and starts execution.

Examples:

```java
forEach()
toList()
collect()
count()
findFirst()
```

Example:

```java
numbers.stream()
       .filter(x -> x > 10)
       .forEach(System.out::println);
```

`forEach()` is the terminal operation.

## Example pipeline

```java
List<Integer> numbers =
    List.of(7, 11, 23, 43, 71, 10);

List<Integer> result =
    numbers.stream()
           .filter(x -> x > 10)
           .map(x -> x * 2)
           .toList();

System.out.println(result);
```

Step by step:

```text
Original:
7, 11, 23, 43, 71, 10

After filter x > 10:
11, 23, 43, 71

After map x * 2:
22, 46, 86, 142

Final list:
22, 46, 86, 142
```

## Intermediate operations

### `filter()`

`filter()` keeps elements that satisfy a condition.

```java
List<Integer> result =
    numbers.stream()
           .filter(x -> x > 10)
           .toList();
```

Input:

```text
7, 11, 23, 43, 71, 10
```

Output:

```text
11, 23, 43, 71
```

The lambda passed to `filter()` is a predicate:

```java
x -> x > 10
```

It returns `true` or `false`.

### `map()`

`map()` transforms every element.

```java
List<Integer> squares =
    numbers.stream()
           .map(x -> x * x)
           .toList();
```

Input:

```text
1, 2, 3, 4
```

Output:

```text
1, 4, 9, 16
```

Example with objects:

```java
List<String> names =
    employees.stream()
             .map(Employee::getName)
             .toList();
```

Each `Employee` becomes a `String`.

### `sorted()`

`sorted()` orders the elements.

```java
List<Integer> sorted =
    numbers.stream()
           .sorted()
           .toList();
```

Example:

```text
Input: 7, 2, 9, 1
Output: 1, 2, 7, 9
```

Custom sorting can use a comparator:

```java
students.stream()
        .sorted(Comparator.comparing(Student::getMarks))
        .toList();
```

### `distinct()`

`distinct()` removes duplicate elements.

```java
List<Integer> result =
    List.of(1, 2, 2, 3, 3, 3)
        .stream()
        .distinct()
        .toList();
```

Output:

```text
1, 2, 3
```

### `limit()`

`limit()` keeps only a fixed number of elements.

```java
List<Integer> firstThree =
    numbers.stream()
           .limit(3)
           .toList();
```

If the source is:

```text
10, 20, 30, 40, 50
```

The result is:

```text
10, 20, 30
```

### `skip()`

`skip()` ignores the first elements.

```java
List<Integer> remaining =
    numbers.stream()
           .skip(2)
           .toList();
```

For:

```text
10, 20, 30, 40
```

The result is:

```text
30, 40
```

## Terminal operations

### `forEach()`

`forEach()` performs an action on every element.

```java
numbers.stream()
       .forEach(System.out::println);
```

The method reference is equivalent to:

```java
numbers.stream()
       .forEach(x -> System.out.println(x));
```

### `toList()`

`toList()` collects the processed elements into a list.

```java
List<Integer> result =
    numbers.stream()
           .filter(x -> x % 2 == 0)
           .toList();
```

### `collect()`

`collect()` is a flexible terminal operation used with collectors.

```java
List<String> names =
    employees.stream()
             .map(Employee::getName)
             .collect(Collectors.toList());
```

Modern Java also supports:

```java
List<String> names =
    employees.stream()
             .map(Employee::getName)
             .toList();
```

### `count()`

`count()` returns the number of elements.

```java
long count =
    numbers.stream()
           .filter(x -> x > 10)
           .count();
```

### `findFirst()`

`findFirst()` returns the first matching element.

```java
Optional<Integer> first =
    numbers.stream()
           .filter(x -> x > 20)
           .findFirst();
```

The result is an `Optional` because there may be no matching element.

## Lazy evaluation

Streams use **lazy evaluation**. Intermediate operations do not execute immediately.

Example:

```java
Stream<Integer> stream =
    numbers.stream()
           .filter(x -> {
               System.out.println("Filtering " + x);
               return x > 10;
           });
```

Nothing is printed yet because there is no terminal operation.

Execution starts here:

```java
stream.forEach(System.out::println);
```

The terminal operation triggers the pipeline.

### Why laziness helps

Lazy evaluation allows Java to:

- avoid unnecessary work,
- combine operations,
- stop early,
- process values only when needed.

```text
Build pipeline
   ↓
No processing yet
   ↓
Terminal operation
   ↓
Pipeline executes
```

## No terminal operation means no execution

This code creates a pipeline but does not run it:

```java
numbers.stream()
       .filter(x -> x > 10)
       .map(x -> x * 2);
```

The result is ignored, and no terminal operation exists.

To execute it:

```java
numbers.stream()
       .filter(x -> x > 10)
       .map(x -> x * 2)
       .forEach(System.out::println);
```

The PDF emphasizes this rule: without a terminal operation, the stream is not executed.

## Short-circuiting

Some terminal operations stop as soon as they have enough information. This is called **short-circuiting**.

`findFirst()` is an example.

```java
Optional<Integer> result =
    numbers.stream()
           .filter(x -> x > 20)
           .findFirst();
```

If the first matching number is found, the stream does not need to process the remaining values.

This behaves somewhat like `break` in a loop.

### Example

```java
List<Integer> numbers =
    List.of(5, 12, 7, 2, 40, 60);

Optional<Integer> result =
    numbers.stream()
           .filter(x -> x > 10)
           .map(x -> x * 2)
           .findFirst();
```

Processing may look like:

```text
5  → rejected
12 → accepted → mapped to 24 → stop
```

The values `7`, `2`, `40`, and `60` may never be processed.

## Vertical processing

A stream pipeline does not always process the entire list through the first operation, then the entire list through the second operation. It can process one element through the whole pipeline before moving to the next.

Example:

```java
numbers.stream()
       .filter(x -> x > 10)
       .map(x -> x * 2)
       .forEach(System.out::println);
```

Conceptually:

```text
Element 1 → filter → map → output
Element 2 → filter → map → output
Element 3 → filter → map → output
```

This vertical flow is especially useful when short-circuiting can stop the pipeline early.

## A Stream cannot be reused

After a terminal operation, the stream is closed or “dead.”

This is invalid:

```java
Stream<Integer> stream =
    numbers.stream();

stream.count();
stream.forEach(System.out::println); // error
```

A stream must be recreated:

```java
numbers.stream().count();

numbers.stream()
       .forEach(System.out::println);
```

The collection can be reused, but the stream object cannot be reused after a terminal operation.

```text
Collection → can usually create many streams
Stream     → one-use processing pipeline
```

## Creating streams

### From a collection

```java
List<Integer> numbers =
    List.of(1, 2, 3, 4);

Stream<Integer> stream =
    numbers.stream();
```

The PDF identifies collections as one of the most common stream sources.

### Parallel collection stream

```java
Stream<Integer> stream =
    numbers.parallelStream();
```

This creates a parallel stream.

A parallel stream may process elements using multiple threads, but it should not be used automatically. The work must be suitable for parallel execution, and the overhead may not be worth it for small collections.

### From an array

```java
int[] numbers = {1, 2, 3, 4};

IntStream stream =
    Arrays.stream(numbers);
```

For an object array:

```java
String[] names = {"Ravi", "Anita"};

Stream<String> stream =
    Arrays.stream(names);
```

### Using `Stream.of()`

```java
Stream<Integer> stream =
    Stream.of(1, 2, 3, 4);
```

Example:

```java
Stream<String> names =
    Stream.of("Ravi", "Anita", "Karan");
```

### Empty stream

The PDF includes `Stream.empty()` as a source.

```java
Stream<String> empty =
    Stream.empty();
```

This is useful when a method must return a stream but has no values.

## Infinite streams

The PDF introduces two ways to create infinite streams:

```text
Stream.iterate()
Stream.generate()
```

These must usually be combined with `limit()`.

## `Stream.iterate()`

`iterate()` creates values based on the previous value.

```java
Stream.iterate(1, x -> x + 1)
       .limit(10)
       .forEach(System.out::println);
```

Output:

```text
1
2
3
4
5
6
7
8
9
10
```

The flow is:

```text
Start with 1
→ add 1
→ add 1
→ add 1
→ continue
```

Another example:

```java
List<Integer> powers =
    Stream.iterate(1, x -> x * 2)
          .limit(6)
          .toList();
```

Output:

```text
1, 2, 4, 8, 16, 32
```

Each value depends on the previous value.

## `Stream.generate()`

`generate()` creates values from a supplier. The next value does not depend on the previous value.

```java
Stream.generate(Math::random)
       .limit(5)
       .forEach(System.out::println);
```

The values are generated independently.

Another example:

```java
List<String> values =
    Stream.generate(() -> "Java")
          .limit(3)
          .toList();
```

Output:

```text
Java, Java, Java
```

## Why infinite streams need `limit()`

This can run forever:

```java
Stream.iterate(1, x -> x + 1)
       .forEach(System.out::println);
```

There is no natural endpoint.

Use `limit()`:

```java
Stream.iterate(1, x -> x + 1)
       .limit(10)
       .forEach(System.out::println);
```

`limit()` gives the infinite source a finite stopping point.

## Primitive streams

Normal object streams use wrapper types:

```java
Stream<Integer>
Stream<Long>
Stream<Double>
```

This may involve boxing and unboxing.

Java provides primitive streams to avoid that overhead:

```java
IntStream
LongStream
DoubleStream
```

The PDF specifically introduces primitive streams for performance and shows conversions between object streams and primitive streams.

## `IntStream`

```java
IntStream numbers =
    IntStream.of(1, 2, 3, 4);
```

Example:

```java
int sum =
    IntStream.of(1, 2, 3, 4)
             .sum();

System.out.println(sum); // 10
```

There is no need to use `Integer` objects for each primitive value.

## `LongStream`

```java
long total =
    LongStream.of(10L, 20L, 30L)
              .sum();

System.out.println(total); // 60
```

## `DoubleStream`

```java
double average =
    DoubleStream.of(10.5, 20.5, 30.5)
                .average()
                .orElse(0.0);

System.out.println(average); // 20.5
```

## Object stream to primitive stream

Use `mapToInt()` when converting an object stream to an `IntStream`.

```java
List<String> names =
    List.of("Java", "Stream", "API");

int totalLength =
    names.stream()
         .mapToInt(String::length)
         .sum();

System.out.println(totalLength); // 14
```

The type transformation is:

```text
Stream<String>
   ↓ mapToInt()
IntStream
```

This is often useful when extracting numeric data from objects.

### Employee example

```java
double totalSalary =
    employees.stream()
             .mapToDouble(Employee::getSalary)
             .sum();
```

## Primitive stream to object stream

Use `boxed()` to convert a primitive stream back into an object stream.

```java
List<Integer> numbers =
    IntStream.of(1, 2, 3)
             .boxed()
             .toList();
```

The type transformation is:

```text
IntStream
   ↓ boxed()
Stream<Integer>
```

Use this when an API requires wrapper objects or a collection.

## Other primitive conversions

The PDF also includes:

```java
mapToLong()
mapToDouble()
```

Examples:

```java
LongStream salaries =
    employees.stream()
             .mapToLong(Employee::getSalary);
```

```java
DoubleStream prices =
    products.stream()
            .mapToDouble(Product::getPrice);
```

## Complete example

```java
import java.util.List;

class Employee {
    private final String name;
    private final int salary;

    Employee(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }

    String getName() {
        return name;
    }

    int getSalary() {
        return salary;
    }
}

public class Main {
    public static void main(String[] args) {
        List<Employee> employees = List.of(
            new Employee("Ravi", 45_000),
            new Employee("Anita", 70_000),
            new Employee("Karan", 60_000),
            new Employee("Meera", 80_000)
        );

        List<String> result =
            employees.stream()
                     .filter(employee -> employee.getSalary() > 50_000)
                     .map(Employee::getName)
                     .sorted()
                     .toList();

        System.out.println(result);
    }
}
```

Processing:

```text
Employees:
Ravi   45,000
Anita  70,000
Karan  60,000
Meera  80,000

After filter:
Anita
Karan
Meera

After map:
"Anita"
"Karan"
"Meera"

After sorted:
"Anita"
"Karan"
"Meera"
```

## Common mistakes

- A Stream is not a collection and does not store data independently.
- Intermediate operations are lazy.
- Without a terminal operation, the pipeline does not execute.
- A stream cannot be reused after a terminal operation.
- Infinite streams require a stopping operation such as `limit()`.
- `findFirst()` may stop processing early.
- `map()` transforms values; `filter()` removes values.
- `boxed()` changes a primitive stream into an object stream.
- `mapToInt()` changes an object stream into an `IntStream`.
- Primitive streams help avoid boxing and unboxing overhead.
- Do not assume `parallelStream()` is always faster.
- Avoid modifying the source collection while processing its stream.

## Important comparison

| Feature          | Collection               | Stream                        |
| ---------------- | ------------------------ | ----------------------------- |
| Stores data      | Yes                      | No                            |
| Main purpose     | Hold data                | Process data                  |
| Reusable         | Usually yes              | No, after terminal operation  |
| Processing style | Manual loops or APIs     | Pipeline operations           |
| Execution        | Immediate when code runs | Lazy until terminal operation |
| Example          | `List<Integer>`          | `numbers.stream()`            |

## Key takeaways

- Streams process sequences of data through pipelines.
- A stream has a source, intermediate operations, and a terminal operation.
- Intermediate operations are lazy.
- A terminal operation triggers execution.
- Streams do not store data.
- Streams cannot be reused after terminal operations.
- Short-circuiting operations can stop processing early.
- Collections, arrays, `Stream.of()`, empty streams, and infinite generators can all be sources.
- `iterate()` depends on the previous value.
- `generate()` creates values independently.
- `IntStream`, `LongStream`, and `DoubleStream` reduce boxing and unboxing.
- `mapToInt()`, `mapToLong()`, and `mapToDouble()` convert object streams to primitive streams.
- `boxed()` converts primitive streams back to object streams.
- Streams support a more declarative style of programming.

## Minimal self-test

1. What are the three parts of a stream pipeline?
2. Why does this code not print anything?

```java
numbers.stream()
       .filter(x -> x > 10)
       .map(x -> x * 2);
```

3. What starts stream execution?
4. Why can a stream not be reused after `count()`?
5. What is the difference between `map()` and `filter()`?
6. What is the difference between `Stream.iterate()` and `Stream.generate()`?
7. Why is `limit()` needed for many infinite streams?
8. What is the purpose of `IntStream`?
9. When would you use `boxed()`?
10. What happens in this pipeline?

```java
List.of(5, 12, 7, 2, 40)
    .stream()
    .filter(x -> x > 10)
    .map(x -> x * 2)
    .findFirst();
```

11. Rewrite a loop that selects even numbers as a stream pipeline.
12. Explain the difference between imperative and declarative programming.

## What to learn next

The most logical next topics are:

1. `map`, `filter`, and `reduce` in depth.
2. `collect()` and collectors such as `groupingBy()`.
3. `Optional` and terminal operations like `findFirst()`.
4. Stream ordering and parallel streams.
5. Stateful operations such as `sorted()` and `distinct()`.
6. Stream performance, short-circuiting, and lazy evaluation.
