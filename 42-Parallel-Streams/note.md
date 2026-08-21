# Java Parallel Streams and Primitive Optional Classes



## One-sentence summary

**Parallel Streams divide a stream's data into smaller chunks and process those chunks concurrently using multiple threads, while primitive Optional classes such as `OptionalInt` avoid unnecessary boxing when stream operations work with primitive values.**

---

# 1. Parallel Streams

## What is a Parallel Stream?

A normal Java Stream is usually **sequential**.

That means elements are processed one by one, generally by a single thread:

```text
Sequential Stream

[1, 2, 3, 4, 5, 6]
          ↓
       Thread 1
          ↓
    1 → 2 → 3 → 4 → 5 → 6
```

A **Parallel Stream** divides the data into smaller chunks and processes those chunks concurrently using multiple threads.

```text
Parallel Stream

             [1,2,3,4,5,6,7,8]
                      ↓
                 Split data
             ┌────────┴────────┐
             ↓                 ↓
          [1,2,3,4]        [5,6,7,8]
             ↓                 ↓
           T1/T2             T3/T4
             ↓                 ↓
             └────────┬────────┘
                      ↓
                 Combine result
```

The first two pages of the uploaded notes illustrate this process using several threads (`T1`, `T2`, `T3`, `T4`, `T5`) working on different chunks and eventually combining the results. 

---

# 2. Sequential vs Parallel Stream

## Sequential Stream

A sequential stream processes elements in sequence.

```java
list.stream()
    .map(x -> x * 2)
    .forEach(System.out::println);
```

Conceptually:

```text
Thread 1
   ↓
1 → 2 → 3 → 4 → 5
```

The work is performed sequentially.

---

## Parallel Stream

A parallel stream can process different parts concurrently:

```java
list.parallelStream()
    .map(x -> x * 2)
    .forEach(System.out::println);
```

Conceptually:

```text
             [1 2 3 4 5 6 7 8]
                      ↓
                   splitting
                /             \
          [1 2 3 4]       [5 6 7 8]
             ↓                ↓
        Thread group      Thread group
             ↓                ↓
                combine
                   ↓
                result
```

The notes describe this as:

1. Split the data into chunks.
2. Assign chunks to threads.
3. Process them independently.
4. Combine the results.



---

# 3. Fork/Join Framework

Parallel Streams internally use Java's **Fork/Join framework**.

The basic idea is:

```text
                    Large task
                       ↓
                     Fork
                  /        \
              Task 1       Task 2
              /   \        /   \
             ...  ...     ...  ...
                  \        /
                     Join
                       ↓
                   Final result
```

### Fork

The original work is divided into smaller pieces.

### Join

The results of those smaller pieces are combined.

The first two pages of the notes connect Parallel Streams with the **Fork/Join Pool** and visually show the split-and-combine process. 

---

# 4. Why Split the Data?

Suppose we have:

```text
[1, 2, 3, 4, 5, 6, 7, 8]
```

A sequential stream might do:

```text
Thread 1:
1 → 2 → 3 → 4 → 5 → 6 → 7 → 8
```

A parallel stream can divide it:

```text
Thread 1 → [1, 2]
Thread 2 → [3, 4]
Thread 3 → [5, 6]
Thread 4 → [7, 8]
```

Each thread can work independently.

If the individual operations are sufficiently expensive and the dataset is sufficiently large, this can reduce total execution time.

---

# 5. `Spliterator`

One of the most important internal concepts behind Parallel Streams is the **`Spliterator`**.

The name comes from:

```text
Split + Iterator
```

It is responsible for working with the stream's source in a way that supports both traversal and splitting.

The uploaded notes emphasize three responsibilities:

1. **Traverse the elements**
2. **Decompose/split the source into parts**
3. **Describe the source**



---

# 6. Iterator vs Spliterator

A traditional `Iterator` is mainly concerned with moving through elements.

For example:

```java
Iterator<Integer> iterator = list.iterator();

while (iterator.hasNext()) {
    Integer value = iterator.next();
}
```

The notes associate an iterator with operations such as:

```text
hasNext()
next()
remove()
```

A `Spliterator`, however, adds the important ability to **split the source**.

Conceptually:

```text
Iterator

[1, 2, 3, 4, 5, 6]
          ↓
     traverse


Spliterator

[1, 2, 3, 4, 5, 6]
          ↓
       split()
       /    \
 [1,2,3]  [4,5,6]
```

That splitting capability is particularly important for parallel processing.

---

# 7. `trySplit()`

A `Spliterator` can split its remaining elements into another portion.

Conceptually:

```java
spliterator.trySplit();
```

Suppose the source contains:

```text
[1, 2, 3, 4, 5, 6, 7, 8]
```

It might split into:

```text
Original portion → [5, 6, 7, 8]
New portion      → [1, 2, 3, 4]
```

Those portions can then be processed independently.

The page 2 diagram shows this idea with a stream being split into `S1` and `S2`, and then further divided into smaller pieces. 

---

# 8. The Complete Parallel Stream Flow

The overall process can be remembered as:

```text
Source
  ↓
Spliterator
  ↓
Split
  ↓
Smaller chunks
  ↓
Fork
  ↓
Multiple threads
  ↓
Process independently
  ↓
Join
  ↓
Combined result
```

This is the central internal idea of Parallel Streams.

---

# 9. Example

Suppose:

```java
List<Integer> numbers =
    List.of(1, 2, 3, 4, 5, 6, 7, 8);
```

We want to multiply every number by `2`.

Sequential:

```java
numbers.stream()
       .map(x -> x * 2)
       .forEach(System.out::println);
```

Parallel:

```java
numbers.parallelStream()
       .map(x -> x * 2)
       .forEach(System.out::println);
```

The parallel version may internally divide the source:

```text
[1,2,3,4,5,6,7,8]
        ↓
 ┌──────┴──────┐
 ↓             ↓
[1,2,3,4]   [5,6,7,8]
 ↓             ↓
threads       threads
 ↓             ↓
[2,4,6,8]  [10,12,14,16]
        ↓
      result
```

The important point is that **you do not manually create these worker threads**. The Stream API and Fork/Join infrastructure manage the parallel execution.

---

# 10. Order in Parallel Streams

One major difference between sequential and parallel processing is **execution order**.

A parallel stream does not guarantee that operations will execute in the same order as the source.

For example:

```java
List<Integer> numbers =
    List.of(1, 2, 3, 4, 5);
```

With:

```java
numbers.parallelStream()
       .forEach(System.out::println);
```

you should not depend on output being:

```text
1
2
3
4
5
```

It could be processed in another order because multiple threads are working concurrently.

The notes explicitly highlight that parallel streams do not guarantee execution order. 

---

# 11. `forEach()` vs `forEachOrdered()`

## `forEach()`

With a parallel stream:

```java
numbers.parallelStream()
       .forEach(System.out::println);
```

the execution order is not guaranteed.

---

## `forEachOrdered()`

If order must be maintained:

```java
numbers.parallelStream()
       .forEachOrdered(System.out::println);
```

This tells the stream that the encounter order must be respected.

The notes emphasize that this can introduce **significant performance overhead** because maintaining order requires additional synchronization/coordination. 

So:

```text
forEach()
   ↓
better freedom for parallel execution


forEachOrdered()
   ↓
preserve order
   ↓
more coordination
   ↓
potentially lower performance
```

---

# 12. Parallel Does Not Automatically Mean Faster

This is one of the most important lessons.

A common beginner assumption is:

> "Parallel = faster."

That is **not always true**.

Parallel processing itself has overhead.

The system has to:

1. split the data,
2. schedule work,
3. coordinate threads,
4. manage the Fork/Join infrastructure,
5. combine results.

For small datasets, this overhead can be larger than the actual work.

---

# 13. Small Dataset Problem

Suppose you have:

```java
List<Integer> numbers =
    List.of(1, 2, 3, 4);
```

Doing a very simple operation in parallel may cost more than simply processing the four elements sequentially.

Conceptually:

```text
Small dataset

Actual work       → very small
Parallel overhead → relatively large

Result:
overhead > benefit
```

The page 3 notes explicitly state that parallel streams should generally be avoided when the dataset is small because the overhead can be greater than the performance benefit. 

---

# 14. Large Dataset

Parallel Streams become more useful when:

* the dataset is large,
* the operation is CPU-intensive,
* the work can be performed independently,
* the data structure can be split efficiently.

The notes list these conditions when discussing when parallel streams can help:

1. Dataset is huge.
2. Work is CPU-intensive.
3. Operations are suitable for parallel execution.
4. The data structure is optimized for parallel stream processing.



---

# 15. CPU-Intensive Work

Parallelism is especially useful when each element requires significant computation.

For example, imagine an expensive calculation:

```java
numbers.parallelStream()
       .map(x -> expensiveCalculation(x))
       .toList();
```

If:

```text
expensiveCalculation(1)
expensiveCalculation(2)
expensiveCalculation(3)
...
```

can execute independently, multiple CPU cores can work at the same time.

Conceptually:

```text
CPU Core 1 → calculation A
CPU Core 2 → calculation B
CPU Core 3 → calculation C
CPU Core 4 → calculation D
```

This is much more promising than parallelizing a tiny operation such as simply adding `1` to four integers.

---

# 16. Stateful Operations

Parallel Streams can also become less attractive when operations require significant coordination.

The notes specifically mention operations such as:

```text
sorted()
distinct()
```

as examples of **stateful operations** that can add overhead.

Why?

Because some operations cannot simply process each element completely independently.

---

## Example: `sorted()`

Suppose:

```text
[7, 2, 9, 1, 5, 3]
```

Different threads can sort chunks:

```text
Thread 1 → [7, 2, 9]
Thread 2 → [1, 5, 3]
```

But the final result still has to be globally ordered:

```text
[1, 2, 3, 5, 7, 9]
```

So the system has additional coordination work.

---

## Example: `distinct()`

Suppose:

```text
[1, 2, 2, 3, 3, 4]
```

Different threads may see duplicates independently.

Eventually, the overall stream must determine:

```text
[1, 2, 3, 4]
```

Again, coordination is required.

The notes warn that such stateful operations can reduce the advantage of parallel execution. 

---

# 17. Data Structure Matters

Not every data source is equally suitable for parallel processing.

The notes specifically call out **LinkedList** as a structure to avoid for parallel streams.

Why?

Because efficient splitting and access are important for parallel processing.

---

# 18. Array/List vs LinkedList

An array-backed list has elements stored in a way that makes splitting relatively efficient.

Conceptually:

```text
Array-backed list

[1][2][3][4][5][6][7][8]
       ↓
     split
    /     \
[1..4]  [5..8]
```

A `LinkedList` looks more like:

```text
[1] → [2] → [3] → [4] → [5] → [6]
```

Finding and splitting arbitrary portions can require following links.

Therefore, the notes recommend avoiding Parallel Streams with `LinkedList` because splitting/random-access-related costs can become expensive. 

---

# 19. Shared Mutable Resources

This is perhaps the most important safety issue.

Avoid using Parallel Streams with **shared mutable resources**.

Consider:

```java
List<Integer> result = new ArrayList<>();

numbers.parallelStream()
       .forEach(x -> result.add(x));
```

Multiple threads may try to modify `result` at the same time.

Conceptually:

```text
Thread 1 ──┐
Thread 2 ──┼──→ shared ArrayList
Thread 3 ──┤
Thread 4 ──┘
```

This can produce a **race condition**.

---

# 20. What is a Race Condition?

A race condition happens when multiple threads access or modify shared state concurrently, and the final result depends on the timing of those operations.

Suppose:

```text
Initial list:
[1, 2, 3, 4, 5]
```

Multiple threads try to add values:

```text
Thread 1 → add(6)
Thread 2 → add(7)
Thread 3 → add(8)
```

Because the resource is shared and mutable, the operations can interfere with each other.

The result may be incorrect or the data structure may become corrupted.

The page 3 diagram specifically illustrates multiple parallel operations writing into a shared list and labels the problem as a **race condition**. 

---

# 21. Avoid This Pattern

Avoid:

```java
List<Integer> result = new ArrayList<>();

numbers.parallelStream()
       .forEach(x -> result.add(x));
```

The problem is not the stream itself.

The problem is:

```text
parallel threads
      +
shared mutable state
      ↓
race condition
```

---

# 22. Prefer Stream Operations That Produce Results

Instead of manually modifying shared state:

```java
List<Integer> result = new ArrayList<>();

numbers.parallelStream()
       .forEach(x -> result.add(x));
```

prefer a stream operation that produces the result:

```java
List<Integer> result =
    numbers.parallelStream()
           .map(x -> x * 2)
           .toList();
```

The stream pipeline can manage the parallel processing and result combination rather than forcing multiple threads to directly mutate one shared collection.

---

# 23. When Parallel Streams Can Help

Use the following mental checklist.

```text
Should I use parallelStream()?
             │
       ┌─────┴─────┐
       ↓           ↓
  Large data?     Small data?
       │              │
      Yes             Avoid
       ↓
CPU-intensive?
       │
      Yes
       ↓
Independent operations?
       │
      Yes
       ↓
Good data structure?
       │
      Yes
       ↓
Parallelism may help
```

According to the notes, the strongest use case is:

> **Large dataset + CPU-intensive independent work + suitable data structure.**



---

# 24. When NOT to Use Parallel Streams

Avoid them when:

### 1. Dataset is small

```text
parallel overhead > actual work
```

### 2. Using `LinkedList`

Splitting/access can be expensive.

### 3. Work is not CPU-intensive

If each operation is tiny, parallel overhead may dominate.

### 4. Stateful operations dominate

For example:

```text
sorted()
distinct()
```

may require additional coordination.

### 5. Shared mutable resources are involved

This can create:

```text
race conditions
data corruption
incorrect results
```

These warnings are directly represented in the page 3 notes. 

---

# 25. Parallel Stream Decision Table

| Situation                            | Parallel Stream?                   |
| ------------------------------------ | ---------------------------------- |
| Huge dataset                         | Potentially useful                 |
| CPU-intensive operation              | Potentially useful                 |
| Independent operations               | Good candidate                     |
| Small dataset                        | Usually avoid                      |
| Very cheap operation                 | Usually avoid                      |
| `LinkedList` source                  | Avoid                              |
| Heavy `sorted()` / `distinct()` work | Be careful                         |
| Shared mutable state                 | Avoid                              |
| Need strict ordering                 | Be careful with `forEachOrdered()` |

---

# 26. Important Parallel Stream Operations

The page 2 notes show a typical pipeline:

```java
list.parallelStream()
    .filter(x -> x > 10)
    .map(x -> x * x)
    .sorted()
    .distinct()
    .forEach(System.out::println);
```

The important point is that the same stream operations can be used with a parallel stream.

The difference is in **how the stream executes the pipeline**.

```text
stream()
       ↓
sequential execution

parallelStream()
       ↓
parallel execution
```

---

# 27. Parallelism Is About Work Distribution

A useful mental model is:

```text
Sequential:

Task
 │
 └──→ Thread
        │
        ├── item 1
        ├── item 2
        ├── item 3
        └── item 4


Parallel:

              Task
               │
             Split
          ┌────┴────┐
          ↓         ↓
       Chunk 1   Chunk 2
          ↓         ↓
       Thread     Thread
          ↓         ↓
          └────┬────┘
               ↓
             Join
```

The goal is not simply "use more threads."

The goal is:

> **Divide useful independent work so multiple CPU cores can work on it at the same time.**

---

# 28. Primitive Optional Classes

Now we move to the second major topic.

Java's normal `Optional<T>` is designed to represent a value that may or may not exist.

For example:

```java
Optional<String> name;
```

Conceptually:

```text
Optional<String>
      ↓
┌─────────────┐
│   String    │
└─────────────┘
```

But primitive values create a special problem.

---

# 29. The Boxing Problem

Suppose we have:

```java
int number = 10;
```

`int` is a primitive.

But:

```java
Optional<Integer>
```

stores an `Integer`, which is an object.

So converting:

```text
int
 ↓
Integer
```

is called **boxing**.

And converting:

```text
Integer
 ↓
int
```

is **unboxing**.

Conceptually:

```text
int
 ↓ boxing
Integer
 ↓
Optional<Integer>
```

This can introduce additional object/memory overhead.

The notes explain that normal `Optional<T>` with primitives can involve boxing and unboxing, which can hurt performance. 

---

# 30. Primitive Optional Classes

Java provides specialized Optional classes for primitive values:

```text
OptionalInt
OptionalLong
OptionalDouble
```

They are designed to handle primitive values directly without requiring the same boxing approach as:

```text
Optional<Integer>
Optional<Long>
Optional<Double>
```

The notes introduce these as the solution to the boxing overhead problem. 

---

# 31. The Three Primitive Optional Classes

## `OptionalInt`

Used for an optional `int`.

```java
OptionalInt result;
```

---

## `OptionalLong`

Used for an optional `long`.

```java
OptionalLong result;
```

---

## `OptionalDouble`

Used for an optional `double`.

```java
OptionalDouble result;
```

Think:

```text
int     → OptionalInt
long    → OptionalLong
double  → OptionalDouble
```

---

# 32. Why Do Stream APIs Use Them?

Primitive streams exist in Java:

```text
IntStream
LongStream
DoubleStream
```

Some terminal operations may not have a value.

For example:

```java
IntStream.empty().max();
```

What should `max()` return if there are no elements?

It cannot simply return an `int`, because there is no meaningful maximum.

So Java uses:

```text
OptionalInt
```

This lets the result represent:

```text
value exists
```

or:

```text
no value exists
```

---

# 33. Example: `IntStream.max()`

Suppose:

```java
OptionalInt result =
    IntStream.of(1, 2, 3, 4)
             .max();
```

The maximum is:

```text
4
```

So:

```text
OptionalInt
     ↓
contains 4
```

The page 4 notes show an `IntStream` example with values such as:

```text
1, 2, 3, 4
```

and connect `max()` with `OptionalInt`. 

---

# 34. Accessing an `OptionalInt`

With a normal:

```java
Optional<Integer>
```

we commonly use:

```java
get()
```

But with:

```java
OptionalInt
```

we use:

```java
getAsInt()
```

Example:

```java
OptionalInt result =
    IntStream.of(1, 2, 3, 4)
             .max();

int max = result.getAsInt();
```

The notes specifically highlight `getAsInt()` rather than `.get()` for `OptionalInt`. 

---

# 35. `OptionalDouble`

For a `double`, use:

```java
OptionalDouble
```

Example:

```java
OptionalDouble average =
    IntStream.of(1, 2, 3, 4)
             .average();
```

Then:

```java
double value = average.getAsDouble();
```

The specialized accessor is:

```text
getAsDouble()
```

---

# 36. `OptionalLong`

For a `long`, use:

```java
OptionalLong
```

and retrieve the value with:

```java
getAsLong()
```

So the mapping is:

| Optional type    | Accessor        |
| ---------------- | --------------- |
| `OptionalInt`    | `getAsInt()`    |
| `OptionalLong`   | `getAsLong()`   |
| `OptionalDouble` | `getAsDouble()` |

This mapping is explicitly given in the source notes. 

---

# 37. Why Not Just Use `.get()`?

Because primitive Optional classes are specialized types.

For example:

```java
OptionalInt result;
```

does not use:

```java
result.get();
```

Instead:

```java
result.getAsInt();
```

Similarly:

```text
OptionalLong   → getAsLong()
OptionalDouble → getAsDouble()
```

This is an easy interview/detail question to remember.

---

# 38. Empty Primitive Optional

A primitive Optional can also represent the absence of a value.

For example:

```java
OptionalInt result =
    IntStream.empty().max();
```

There is no maximum.

Therefore:

```text
OptionalInt
     ↓
empty
```

Before directly retrieving the value, check whether one exists.

For example:

```java
if (result.isPresent()) {
    int max = result.getAsInt();
}
```

The important concept is:

```text
OptionalInt
   ├── value exists
   └── value absent
```

---

# 39. `Optional<T>` vs Primitive Optional

| Normal Optional             | Primitive Optional                               |
| --------------------------- | ------------------------------------------------ |
| `Optional<Integer>`         | `OptionalInt`                                    |
| `Optional<Long>`            | `OptionalLong`                                   |
| `Optional<Double>`          | `OptionalDouble`                                 |
| Uses object wrapper types   | Specialized for primitives                       |
| Can involve boxing/unboxing | Designed to avoid that primitive boxing overhead |
| `get()`                     | `getAsInt()` / `getAsLong()` / `getAsDouble()`   |

---

# 40. Stream Operations That Can Return Primitive Optionals

The notes specifically mention operations such as:

```text
max()
min()
average()
```

For primitive streams, these can produce specialized Optional results.

For example:

```java
OptionalInt max =
    IntStream.of(10, 20, 30)
             .max();
```

```java
OptionalInt min =
    IntStream.of(10, 20, 30)
             .min();
```

And:

```java
OptionalDouble avg =
    IntStream.of(10, 20, 30)
             .average();
```

So:

```text
IntStream
   │
   ├── max()     → OptionalInt
   ├── min()     → OptionalInt
   └── average() → OptionalDouble
```

---

# 41. Complete Example

```java
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {

        OptionalInt max =
            IntStream.of(1, 2, 3, 4)
                     .max();

        if (max.isPresent()) {
            System.out.println(max.getAsInt());
        }
    }
}
```

Output:

```text
4
```

The important flow is:

```text
IntStream
   ↓
max()
   ↓
OptionalInt
   ↓
getAsInt()
   ↓
int
```

---

# 42. Complete Topic Map

```text
                         Java Streams
                              │
                 ┌────────────┴────────────┐
                 │                         │
            Sequential                 Parallel
                 │                         │
            one-by-one              split source
                                           │
                                      Spliterator
                                           │
                                         split
                                           │
                                      Fork/Join
                                           │
                                     multiple threads
                                           │
                                         Join


                    Primitive Stream Results
                              │
                 ┌────────────┼────────────┐
                 ↓            ↓            ↓
            OptionalInt  OptionalLong  OptionalDouble
                 │            │            │
             getAsInt()  getAsLong()  getAsDouble()
```

---

# 43. Important Gotchas

## Gotcha 1: Parallel does not mean automatically faster

Always consider:

```text
Dataset size
+
Work complexity
+
Splitting cost
+
Coordination cost
+
Data structure
```

---

## Gotcha 2: Small datasets

For small datasets:

```text
parallel overhead
      >
actual work
```

Sequential processing may be faster.

---

## Gotcha 3: `forEach()` ordering

Do not assume:

```java
parallelStream().forEach(...)
```

preserves encounter order.

Use:

```java
forEachOrdered()
```

when ordering is required, but remember the additional performance cost.

---

## Gotcha 4: Shared mutable state

Avoid:

```java
parallelStream()
    .forEach(x -> sharedList.add(x));
```

because multiple threads are modifying the same mutable resource.

---

## Gotcha 5: `LinkedList`

The notes specifically warn against using Parallel Streams with `LinkedList` because splitting/access can be expensive.

---

## Gotcha 6: Stateful operations

Be careful when your pipeline heavily uses:

```text
sorted()
distinct()
```

because these can reduce the benefit of parallel execution.

---

## Gotcha 7: `OptionalInt` does not use `get()`

Remember:

```text
OptionalInt    → getAsInt()
OptionalLong   → getAsLong()
OptionalDouble → getAsDouble()
```

---

# 44. Interview Revision Sheet

### What is a Parallel Stream?

A stream that can process different portions of its data concurrently using multiple threads.

### Which framework is used internally?

The **Fork/Join framework**.

### What is the role of `Spliterator`?

It helps traverse the source, split it into portions, and describe source characteristics.

### Is execution order guaranteed?

Not for ordinary parallel processing such as `forEach()`.

### How can order be enforced?

```java
forEachOrdered()
```

but this can introduce performance overhead.

### Are Parallel Streams always faster?

No.

### When are they useful?

Mainly when there is:

* large data,
* CPU-intensive work,
* independent operations,
* a suitable data structure.

### When should they be avoided?

Especially with:

* small datasets,
* `LinkedList`,
* expensive coordination/stateful operations,
* shared mutable resources.

### What is a race condition?

A problem caused by multiple threads concurrently accessing/modifying shared mutable state.

### Why do primitive Optional classes exist?

To avoid the unnecessary boxing/unboxing overhead associated with using wrapper types inside normal `Optional<T>` for primitive values.

### What are the primitive Optional classes?

```text
OptionalInt
OptionalLong
OptionalDouble
```

### What are their accessors?

```text
OptionalInt    → getAsInt()
OptionalLong   → getAsLong()
OptionalDouble → getAsDouble()
```

### Which stream operations can produce them?

Operations such as:

```text
max()
min()
average()
```

on primitive streams.

---

# 45. Key Takeaways

* **Sequential Stream** processes work sequentially.
* **Parallel Stream** divides work into chunks and processes chunks concurrently.
* Parallel Streams use the **Fork/Join framework** internally.
* **`Spliterator`** is important because it can traverse and split the source.
* The basic model is:

```text
Split → Fork → Process → Join
```

* Parallel Streams do **not automatically guarantee execution order**.
* `forEachOrdered()` can preserve order but may reduce performance.
* Parallelism introduces overhead, so **small datasets may become slower**.
* Parallel Streams are most useful for **large, CPU-intensive, independent workloads**.
* `LinkedList` is specifically called out as a poor candidate for Parallel Streams in the source.
* Stateful operations such as `sorted()` and `distinct()` can reduce parallel performance.
* Never casually mutate a **shared mutable resource** from a parallel stream.
* Shared mutable state can cause **race conditions and data corruption**.
* `Optional<T>` with primitives can involve **boxing/unboxing**.
* Java provides:

  * `OptionalInt`
  * `OptionalLong`
  * `OptionalDouble`
* Primitive Optional classes are commonly seen as results of primitive stream operations such as `max()`, `min()`, and `average()`.
* Use:

  * `getAsInt()`
  * `getAsLong()`
  * `getAsDouble()`
    instead of `get()`.

---

# 46. Minimal Self-Test

1. What is the difference between a sequential and parallel stream?
2. Why does a parallel stream split the source?
3. What is the Fork/Join framework doing here?
4. What is a `Spliterator`?
5. What does `trySplit()` conceptually accomplish?
6. Why is `forEach()` potentially unordered with a parallel stream?
7. What does `forEachOrdered()` do?
8. Why can `forEachOrdered()` reduce performance?
9. Why can a parallel stream be slower for a small dataset?
10. Why is `LinkedList` a poor choice for parallel processing according to the notes?
11. Why can `sorted()` and `distinct()` reduce the benefits of parallelism?
12. What is a shared mutable resource?
13. How can shared mutable state cause a race condition?
14. Why should you avoid modifying a shared `ArrayList` from `parallelStream().forEach()`?
15. What kind of workload is a good candidate for parallel streams?
16. Why does `Optional<Integer>` involve boxing when working with `int` values?
17. What is the purpose of `OptionalInt`?
18. What are the three primitive Optional classes?
19. What does `getAsInt()` return?
20. What is the difference between `OptionalInt` and `Optional<Integer>`?
21. What does `IntStream.max()` return?
22. What does `IntStream.average()` return?
23. What happens when `max()` is called on an empty `IntStream`?
24. Why are primitive Optional classes useful for performance?
