# Java Stream API Methods

## The main idea

A Stream pipeline is built from:

```text
Source
   ↓
Intermediate operations
   ↓
Terminal operation
```

Example:

```java
List<Integer> result =
    List.of(3, 5, 10, 13, 17, 19)
        .stream()
        .filter(x -> x > 10)
        .map(x -> x * 2)
        .toList();
```

Here:

- `List.of(...)` is the source.
- `filter()` and `map()` are intermediate operations.
- `toList()` is the terminal operation.

The PDF groups stream methods into intermediate operations, terminal operations, and collectors.

## One-sentence summary

Intermediate operations build a lazy processing pipeline, while terminal operations start that pipeline and produce a result or side effect.

## Intermediate operations

Intermediate operations:

- return another stream,
- can be chained,
- are lazy,
- do not execute until a terminal operation is called.

```text
stream()
   .filter(...)
   .map(...)
   .sorted()
   .toList()
```

In this example, `filter()`, `map()`, and `sorted()` are intermediate operations. `toList()` is terminal.

## `filter()`

`filter()` keeps only the elements that satisfy a condition.

```java
List<Integer> result =
    List.of(3, 5, 10, 13, 17, 19)
        .stream()
        .filter(x -> x > 10)
        .toList();

System.out.println(result);
```

Output:

```text
13, 17, 19
```

The lambda returns a boolean:

```java
x -> x > 10
```

Meaning:

```text
Keep x only when x is greater than 10.
```

## `map()`

`map()` transforms every element into another value.

```java
List<Integer> result =
    List.of(1, 2, 3, 4)
        .stream()
        .map(x -> x * x)
        .toList();

System.out.println(result);
```

Output:

```text
1, 4, 9, 16
```

`map()` keeps one output for each input.

```text
Input:  1, 2, 3, 4
Output: 1, 4, 9, 16
```

It can also change the type:

```java
List<String> names =
    students.stream()
            .map(Student::getName)
            .toList();
```

```text
Student → String
```

## `flatMap()`

`flatMap()` is used when each element contains multiple elements, such as a `List<List<Integer>>`. It flattens the nested structure into one stream.

Without `flatMap()`:

```java
List<List<Integer>> numbers =
    List.of(
        List.of(1, 2),
        List.of(3, 4),
        List.of(5, 6)
    );
```

This is a list of lists:

```text
[ [1, 2], [3, 4], [5, 6] ]
```

Using `flatMap()`:

```java
List<Integer> result =
    numbers.stream()
           .flatMap(list -> list.stream())
           .toList();

System.out.println(result);
```

Output:

```text
1, 2, 3, 4, 5, 6
```

The transformation is:

```text
List<List<Integer>>
        ↓ flatMap()
Stream<Integer>
        ↓
List<Integer>
```

### `map()` vs `flatMap()`

`map()` preserves the nested structure:

```java
numbers.stream()
       .map(list -> list.stream());
```

Conceptually:

```text
List<Integer> → Stream<Integer>
```

The result becomes a stream of streams.

`flatMap()` removes one level of nesting:

```java
numbers.stream()
       .flatMap(list -> list.stream());
```

Conceptually:

```text
List<List<Integer>> → Stream<Integer>
```

Use `flatMap()` when one input can produce multiple output elements.

## `sorted()`

`sorted()` arranges stream elements in order.

```java
List<Integer> result =
    List.of(5, 1, 4, 2, 3)
        .stream()
        .sorted()
        .toList();

System.out.println(result);
```

Output:

```text
1, 2, 3, 4, 5
```

For descending order:

```java
List<Integer> result =
    List.of(5, 1, 4, 2, 3)
        .stream()
        .sorted(Comparator.reverseOrder())
        .toList();
```

### Why `sorted()` is stateful

`sorted()` is a **stateful intermediate operation**. It must see all or many elements before it knows the correct order.

For example, after seeing only `5`, Java cannot know whether a smaller value will appear later.

```text
5
5, 1
5, 1, 4
5, 1, 4, 2
...
```

The stream must collect enough information, sort it, and then pass elements forward.

## `distinct()`

`distinct()` removes duplicate elements.

```java
List<Integer> result =
    List.of(1, 2, 2, 3, 3, 3)
        .stream()
        .distinct()
        .toList();

System.out.println(result);
```

Output:

```text
1, 2, 3
```

The PDF notes that `distinct()` uses hashing to identify repeated values.

### Why `distinct()` is stateful

`distinct()` must remember values it has already seen.

```text
Input:
1, 2, 2, 3, 1

Seen values:
1 → keep
2 → keep
2 → remove
3 → keep
1 → remove
```

It is stateful because it needs memory of previous elements.

For custom objects, correct `equals()` and `hashCode()` implementations are important:

```java
class Student {
    String name;
    int rollNo;

    @Override
    public boolean equals(Object obj) {
        // logical equality
        return true;
    }

    @Override
    public int hashCode() {
        // consistent hash code
        return 1;
    }
}
```

## `limit()`

`limit(n)` keeps at most the first `n` elements.

```java
List<Integer> result =
    List.of(1, 2, 3, 4, 5)
        .stream()
        .limit(3)
        .toList();

System.out.println(result);
```

Output:

```text
1, 2, 3
```

`limit()` is especially useful with infinite streams:

```java
List<Integer> numbers =
    Stream.iterate(1, x -> x + 1)
          .limit(5)
          .toList();

System.out.println(numbers);
```

Output:

```text
1, 2, 3, 4, 5
```

Without `limit()`, the infinite stream would continue forever.

## `skip()`

`skip(n)` discards the first `n` elements.

```java
List<Integer> result =
    List.of(1, 2, 3, 4, 5)
        .stream()
        .skip(2)
        .toList();

System.out.println(result);
```

Output:

```text
3, 4, 5
```

`skip()` and `limit()` can be combined for pagination.

```java
int pageSize = 3;
int pageNumber = 2;

List<Integer> page =
    numbers.stream()
           .skip((long) (pageNumber - 1) * pageSize)
           .limit(pageSize)
           .toList();
```

For page 2, Java skips the first page and takes the next three elements.

## `peek()`

`peek()` lets you observe elements as they pass through the pipeline. It is mainly useful for debugging.

```java
List<Integer> result =
    List.of(1, 2, 3, 4)
        .stream()
        .filter(x -> x % 2 == 0)
        .peek(x -> System.out.println("After filter: " + x))
        .map(x -> x * 10)
        .peek(x -> System.out.println("After map: " + x))
        .toList();
```

Possible output:

```text
After filter: 2
After map: 20
After filter: 4
After map: 40
```

Important: `peek()` is lazy too. Without a terminal operation, it does nothing:

```java
numbers.stream()
       .peek(System.out::println);
```

Nothing is printed.

### Do not use `peek()` for important business logic

This is risky:

```java
numbers.stream()
       .peek(x -> saveToDatabase(x))
       .toList();
```

If the pipeline changes or the terminal operation is removed, the save operation may not run. Use `forEach()` or an explicit method when the side effect is important.

## Primitive stream conversions

The PDF lists `mapToInt()` and `mapToDouble()` as stream operations.

They convert object streams into primitive streams.

```java
List<String> names =
    List.of("Java", "Stream", "API");

int totalLength =
    names.stream()
         .mapToInt(String::length)
         .sum();

System.out.println(totalLength);
```

Output:

```text
13
```

The transformation is:

```text
Stream<String>
   ↓ mapToInt()
IntStream
```

Primitive streams avoid unnecessary boxing and unboxing.

## Terminal operations

Terminal operations:

- trigger stream execution,
- end the pipeline,
- produce a result or side effect,
- make the stream unusable afterward.

The PDF groups terminal operations into:

- collecting results,
- reducing values,
- searching and matching,
- iteration,
- numeric operations.

## `forEach()`

`forEach()` performs an action for every element.

```java
List.of(1, 2, 3, 4)
    .stream()
    .forEach(System.out::println);
```

Output:

```text
1
2
3
4
```

It returns `void`.

### `forEachOrdered()`

`forEachOrdered()` preserves encounter order, especially when working with parallel streams.

```java
numbers.parallelStream()
       .forEachOrdered(System.out::println);
```

For a sequential stream, `forEach()` normally follows the source order. For a parallel stream, `forEach()` may process elements in an unpredictable order.

```java
numbers.parallelStream()
       .forEach(System.out::println);
```

Possible output:

```text
3
1
4
2
```

Use `forEachOrdered()` when output order matters.

## `collect()`

`collect()` gathers stream elements into a result. It usually works with the `Collectors` utility class.

```java
List<Integer> list =
    numbers.stream()
           .filter(x -> x > 5)
           .collect(Collectors.toList());
```

Modern Java also supports:

```java
List<Integer> list =
    numbers.stream()
           .filter(x -> x > 5)
           .toList();
```

### Collect into a `Set`

```java
Set<Integer> set =
    numbers.stream()
           .collect(Collectors.toSet());
```

A set removes duplicates.

### Collect into a `Map`

```java
Map<Integer, String> map =
    students.stream()
            .collect(Collectors.toMap(
                Student::getRollNo,
                Student::getName
            ));
```

The result may look like:

```text
1 → Anita
2 → Ravi
3 → Karan
```

The key must be unique unless you provide a merge function.

### Collect into a joined string

```java
String result =
    names.stream()
         .collect(Collectors.joining(", "));
```

Output:

```text
Ravi, Anita, Karan
```

## `reduce()`

`reduce()` combines stream elements into one final value.

For example, summing numbers:

```java
int sum =
    List.of(1, 2, 3, 4)
        .stream()
        .reduce(0, (a, b) -> a + b);

System.out.println(sum); // 10
```

The reduction happens like this:

```text
Initial value: 0
0 + 1 = 1
1 + 2 = 3
3 + 3 = 6
6 + 4 = 10
```

The general form is:

```java
reduce(identity, accumulator)
```

- `identity` is the starting value.
- `accumulator` explains how to combine values.

Another example:

```java
Optional<Integer> maximum =
    numbers.stream()
           .reduce(Integer::max);
```

Without an identity value, the result is an `Optional` because the stream might be empty.

### `reduce()` vs `collect()`

Use `reduce()` when many values become one value:

```text
Numbers → one sum
Strings → one combined result
```

Use `collect()` when you want to build a collection or structured result:

```text
Stream → List
Stream → Set
Stream → Map
```

## `count()`

`count()` returns the number of stream elements as a `long`.

```java
long count =
    List.of(1, 2, 3, 4, 5)
        .stream()
        .filter(x -> x % 2 == 0)
        .count();

System.out.println(count); // 2
```

The return type is `long`, not `int`.

```java
long total = numbers.stream().count();
```

## Short-circuiting terminal operations

Short-circuiting operations stop as soon as the final answer is known.

The PDF lists:

```text
findFirst()
findAny()
anyMatch()
allMatch()
noneMatch()
```

## `findFirst()`

`findFirst()` returns the first matching element.

```java
Optional<Integer> result =
    List.of(3, 5, 10, 13, 17)
        .stream()
        .filter(x -> x > 10)
        .findFirst();

System.out.println(result.orElse(-1)); // 13
```

Processing:

```text
3  → reject
5  → reject
10 → reject
13 → accept and stop
```

The remaining values may not be processed.

## `findAny()`

`findAny()` returns any matching element.

```java
Optional<Integer> result =
    numbers.stream()
           .filter(x -> x > 10)
           .findAny();
```

With a sequential stream, it often returns the first matching element, but that is not the guarantee. With parallel streams, it may return whichever matching element is found first.

Use:

- `findFirst()` when order matters.
- `findAny()` when any match is acceptable, especially in parallel processing.

## `anyMatch()`

`anyMatch()` returns `true` if at least one element matches.

```java
boolean hasEven =
    List.of(1, 3, 5, 8, 9)
        .stream()
        .anyMatch(x -> x % 2 == 0);

System.out.println(hasEven); // true
```

Once `8` is found, the stream can stop.

## `allMatch()`

`allMatch()` returns `true` only if every element matches.

```java
boolean allPositive =
    List.of(2, 4, 6, 8)
        .stream()
        .allMatch(x -> x > 0);

System.out.println(allPositive); // true
```

If one value fails, processing can stop:

```java
boolean allPositive =
    List.of(2, 4, -6, 8)
        .stream()
        .allMatch(x -> x > 0);

System.out.println(allPositive); // false
```

## `noneMatch()`

`noneMatch()` returns `true` if no element matches.

```java
boolean noNegative =
    List.of(2, 4, 6)
        .stream()
        .noneMatch(x -> x < 0);

System.out.println(noNegative); // true
```

If a negative value is found, the answer becomes `false`.

## Numeric operations

Primitive streams provide numeric operations such as:

```text
sum()
max()
min()
average()
count()
```

These are especially useful with `IntStream`, `LongStream`, and `DoubleStream`.

### `sum()`

```java
int total =
    IntStream.of(1, 2, 3, 4)
             .sum();

System.out.println(total); // 10
```

### `max()`

```java
OptionalInt maximum =
    IntStream.of(4, 8, 2, 10)
             .max();

System.out.println(maximum.orElse(0)); // 10
```

### `min()`

```java
OptionalInt minimum =
    IntStream.of(4, 8, 2, 10)
             .min();

System.out.println(minimum.orElse(0)); // 2
```

### `average()`

```java
OptionalDouble average =
    IntStream.of(10, 20, 30)
             .average();

System.out.println(average.orElse(0.0)); // 20.0
```

The result is optional because an empty stream has no maximum, minimum, or average.

## `groupingBy()`

`groupingBy()` organizes elements into a `Map` using a classifier function.

Suppose we have:

```java
class Employee {
    String name;
    String department;

    String getDepartment() {
        return department;
    }
}
```

Group employees by department:

```java
Map<String, List<Employee>> employeesByDepartment =
    employees.stream()
             .collect(Collectors.groupingBy(
                 Employee::getDepartment
             ));
```

The result might look like:

```text
IT       → [Ravi, Anita]
Finance  → [Karan]
HR       → [Meera, John]
```

### Group strings by length

```java
Map<Integer, List<String>> grouped =
    names.stream()
         .collect(Collectors.groupingBy(
             String::length
         ));
```

For:

```text
Java, Code, Stream, API
```

The result is conceptually:

```text
3 → [API]
4 → [Java, Code]
6 → [Stream]
```

### Group and count

You can use another collector inside `groupingBy()`:

```java
Map<String, Long> countByDepartment =
    employees.stream()
             .collect(Collectors.groupingBy(
                 Employee::getDepartment,
                 Collectors.counting()
             ));
```

Result:

```text
IT       → 2
Finance  → 1
HR       → 2
```

## `partitioningBy()`

`partitioningBy()` creates exactly two groups:

```text
true
false
```

It is useful when every element either satisfies a condition or does not.

Example:

```java
Map<Boolean, List<Integer>> partitioned =
    numbers.stream()
           .collect(Collectors.partitioningBy(
               x -> x % 2 == 0
           ));
```

Result:

```text
true  → [2, 4, 6]
false → [1, 3, 5]
```

### `groupingBy()` vs `partitioningBy()`

| Method             | Number of groups | Use                              |
| ------------------ | ---------------: | -------------------------------- |
| `groupingBy()`     |       Any number | Group by a category or key       |
| `partitioningBy()` |      Exactly two | Divide by a true/false condition |

Example:

```java
groupingBy(Employee::getDepartment)
```

creates groups such as `IT`, `HR`, and `Finance`.

```java
partitioningBy(Employee::isActive)
```

creates only:

```text
true
false
```

## `joining()`

`joining()` combines string elements into one string with an optional delimiter.

```java
String result =
    names.stream()
         .collect(Collectors.joining(", "));

System.out.println(result);
```

Output:

```text
Ravi, Anita, Karan
```

### Prefix and suffix

```java
String result =
    names.stream()
         .collect(Collectors.joining(
             ", ",
             "[",
             "]"
         ));

System.out.println(result);
```

Output:

```text
[Ravi, Anita, Karan]
```

The arguments are:

```text
delimiter, prefix, suffix
```

## Complete example

```java
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Student {
    private final String name;
    private final String department;
    private final int marks;

    Student(String name, String department, int marks) {
        this.name = name;
        this.department = department;
        this.marks = marks;
    }

    String getName() {
        return name;
    }

    String getDepartment() {
        return department;
    }

    int getMarks() {
        return marks;
    }
}

public class Main {
    public static void main(String[] args) {
        List<Student> students = List.of(
            new Student("Ravi", "IT", 85),
            new Student("Anita", "IT", 92),
            new Student("Karan", "HR", 65),
            new Student("Meera", "HR", 78)
        );

        List<String> topStudents =
            students.stream()
                    .filter(student -> student.getMarks() >= 75)
                    .sorted((s1, s2) ->
                        Integer.compare(
                            s2.getMarks(),
                            s1.getMarks()
                        )
                    )
                    .map(Student::getName)
                    .toList();

        Map<String, List<Student>> byDepartment =
            students.stream()
                    .collect(Collectors.groupingBy(
                        Student::getDepartment
                    ));

        Map<Boolean, List<Student>> partitioned =
            students.stream()
                    .collect(Collectors.partitioningBy(
                        student -> student.getMarks() >= 75
                    ));

        String names =
            students.stream()
                    .map(Student::getName)
                    .collect(Collectors.joining(", "));

        int totalMarks =
            students.stream()
                    .mapToInt(Student::getMarks)
                    .sum();

        System.out.println(topStudents);
        System.out.println(byDepartment);
        System.out.println(partitioned);
        System.out.println(names);
        System.out.println(totalMarks);
    }
}
```

## Intermediate vs terminal operations

| Category           | Operations                          | Behavior                     |
| ------------------ | ----------------------------------- | ---------------------------- |
| Intermediate       | `filter`, `map`, `flatMap`          | Transform or select elements |
| Intermediate       | `sorted`, `distinct`                | Stateful processing          |
| Intermediate       | `limit`, `skip`                     | Slice the stream             |
| Intermediate       | `peek`                              | Observe elements             |
| Intermediate       | `mapToInt`, `mapToDouble`           | Convert to primitive streams |
| Terminal           | `forEach`, `forEachOrdered`         | Perform an action            |
| Terminal           | `toList`, `collect`                 | Build a result               |
| Terminal           | `reduce`                            | Combine values               |
| Terminal           | `count`                             | Count elements               |
| Terminal           | `findFirst`, `findAny`              | Find an element              |
| Terminal           | `anyMatch`, `allMatch`, `noneMatch` | Test conditions              |
| Primitive terminal | `sum`, `min`, `max`, `average`      | Calculate statistics         |

## Important gotchas

- Intermediate operations are lazy.
- A terminal operation is needed to execute the pipeline.
- `sorted()` and `distinct()` are stateful because they need information about multiple elements.
- `flatMap()` removes one level of nesting, while `map()` keeps the structure.
- `peek()` is mainly for debugging.
- `forEach()` may not preserve order in a parallel stream.
- Use `forEachOrdered()` when encounter order matters.
- `findFirst()` respects order; `findAny()` does not promise a specific matching element.
- `count()` returns `long`.
- `max()`, `min()`, and `average()` on primitive streams return optional numeric types because the stream may be empty.
- `groupingBy()` can create many groups.
- `partitioningBy()` creates only `true` and `false` groups.
- `reduce()` combines values into one result.
- `collect()` usually creates a collection or structured result.
- A stream cannot be reused after a terminal operation.
- Do not use stream side effects carelessly.

## Key takeaways

- Intermediate operations return streams and are lazy.
- Terminal operations start execution and finish the pipeline.
- `filter()` selects elements.
- `map()` transforms one element into one result.
- `flatMap()` flattens nested data.
- `sorted()` orders elements but must observe the stream state.
- `distinct()` removes duplicates using equality and hashing.
- `limit()` takes the first part.
- `skip()` ignores the first part.
- `peek()` helps inspect a pipeline during debugging.
- `forEach()` performs an action.
- `collect()` builds lists, sets, maps, or strings.
- `reduce()` combines elements into one value.
- Matching and finding operations can stop early.
- Primitive streams provide numeric operations efficiently.
- `groupingBy()` creates category-based groups.
- `partitioningBy()` separates values into two groups.
- `joining()` combines strings.

## Minimal self-test

1. Which operations in this pipeline are intermediate and which one is terminal?

```java
numbers.stream()
       .filter(x -> x > 10)
       .map(x -> x * 2)
       .sorted()
       .toList();
```

2. What is the difference between `map()` and `flatMap()`?
3. Why is `sorted()` stateful?
4. Why is `distinct()` stateful?
5. Why is `peek()` usually used for debugging?
6. What is the difference between `forEach()` and `forEachOrdered()`?
7. What does `reduce()` do?
8. Why does `count()` return `long`?
9. When should you use `groupingBy()`?
10. When should you use `partitioningBy()`?
11. What output does this produce?

```java
List.of(1, 2, 2, 3, 4)
    .stream()
    .distinct()
    .filter(x -> x % 2 == 0)
    .map(x -> x * 10)
    .toList();
```

12. Write a stream pipeline that:
    - removes duplicate numbers,
    - keeps only even numbers,
    - squares them,
    - collects them into a list.

13. Write a stream pipeline that groups employees by department.
14. Write a stream pipeline that joins names using `", "`.

## What to learn next

The next useful topics are:

1. `Collectors.toMap()` and duplicate-key handling.
2. `groupingBy()` with downstream collectors such as `counting()`, `mapping()`, and `summarizingInt()`.
3. `reduce()` versus `collect()`.
4. Stream ordering and parallel streams.
5. `Optional` with `findFirst()`, `min()`, and `max()`.
6. Stream performance and short-circuiting.
