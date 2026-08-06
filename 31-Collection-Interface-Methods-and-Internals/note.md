## Hook: Why Does Every Collection Have Methods Like `add()`, `remove()`, and `contains()`?

Although `ArrayList`, `LinkedList`, `HashSet`, and `Queue` are completely different data structures internally, they all perform common operations such as adding, removing, searching, and checking whether they are empty. Instead of redefining these operations in every class, Java places them in the **Collection interface**. This gives all collection classes a common contract while allowing each implementation to optimize the operations internally.

---

# Java Collection Interface

## What is the Collection Interface?

The **Collection** interface is the **root interface** of the Java Collection Framework for collections that store individual elements.

It defines a common set of methods that every collection implementation must provide.

Examples of classes implementing the `Collection` interface include:

- `ArrayList`
- `LinkedList`
- `HashSet`
- `ArrayDeque`

These classes inherit the same basic operations while implementing them differently internally. The lecture notes show these implementations beneath the `Collection` interface.

---

# Collection Hierarchy

The `Collection` interface extends `Iterable`.

```text
Object
   │
Iterable
   │
Collection
┌──┼─────────┐
│  │         │
List Set    Queue
```

Since `Collection` extends `Iterable`, every collection can be traversed using an `Iterator` and the enhanced `for-each` loop.

---

# Generics in Collection

Collections use **Generics** to provide compile-time type safety.

Example:

```java
Collection<Integer c = new ArrayList<();
```

Here,

```text
Integer
```

is the type parameter.

Only `Integer` objects can be stored.

Attempting to insert another type results in a compile-time error.

---

# Polymorphism

One of the biggest advantages of interfaces is **polymorphism**.

A variable of type `Collection` can reference any implementation.

```java
Collection<Integer c = new ArrayList<();
```

Later,

```java
c = new LinkedList<();
```

or

```java
c = new HashSet<();
```

The rest of the code remains unchanged because all implementations follow the same interface contract. This polymorphic usage is highlighted in the lecture notes.

---

# Collection vs Collections

These two names are commonly confused.

## Collection

```text
Interface
```

Defines the common behavior for collection classes.

---

## Collections

```text
Utility Class
```

Contains helper methods such as:

- `sort()`
- `reverse()`
- `shuffle()`
- `binarySearch()`

**Remember:**

```text
Collection  → Interface

Collections → Utility Class
```

---

# Core Methods of Collection

The `Collection` interface defines methods that every collection supports. The conceptual method list shown in the lecture notes includes the following operations.

---

## `size()`

Returns the number of elements stored.

```java
Collection<Integer c = new ArrayList<();

c.add(10);
c.add(20);

System.out.println(c.size());
```

Output:

```text
2
```

---

## `isEmpty()`

Checks whether the collection contains any elements.

```java
Collection<Integer c = new ArrayList<();

System.out.println(c.isEmpty());
```

Output:

```text
true
```

After adding an element:

```java
c.add(10);
```

Output:

```text
false
```

---

## `contains(Object o)`

Checks whether an element exists.

```java
c.contains(20);
```

Returns:

```text
true
```

### Performance

Performance depends on the implementation:

| Collection   | Average Complexity |
| ------------ | ------------------ |
| `ArrayList`  | O(n)               |
| `LinkedList` | O(n)               |
| `HashSet`    | O(1) (average)     |

The method signature accepts `Object` so that different implementations can perform equality checks consistently.

---

## `toArray()`

Converts the collection into an array.

```java
Object[] arr = c.toArray();
```

Since this returns `Object[]`, Java also provides a more useful overloaded version:

```java
Integer[] arr = c.toArray(new Integer[0]);
```

This creates an array of the desired type.

---

# Data Modification Methods

## `add(E e)`

Adds an element to the collection.

```java
c.add(10);
```

Return type:

```java
boolean
```

Usually returns:

```text
true
```

However, collections such as `HashSet` reject duplicate values.

Example:

```java
Set<Integer set = new HashSet<();

set.add(10);
set.add(10);
```

Second call:

```text
false
```

because the duplicate is not inserted.

---

## `remove(Object o)`

Removes the specified object.

```java
c.remove(20);
```

It returns:

```java
boolean
```

Removal depends on the `equals()` method.

If `equals()` returns `true`, the matching object is removed.

---

## `clear()`

Removes every element from the collection.

```java
c.clear();
```

Afterward:

```java
c.isEmpty();
```

returns:

```text
true
```

---

# Bulk Operations

Bulk operations work with entire collections instead of individual elements.

Many of these methods use **wildcards** such as `Collection<? extends E` or `Collection<?`, allowing different but compatible collection types to participate.

---

## `addAll(Collection<? extends E c)`

Adds every element from another collection.

```java
List<Integer list1 = new ArrayList<();
List<Integer list2 = new ArrayList<();

list1.addAll(list2);
```

---

## `containsAll(Collection<? c)`

Checks whether every element from another collection exists.

Example:

```text
A = [1,2,3,4]

B = [2,3]
```

```java
A.containsAll(B);
```

Output:

```text
true
```

---

## `removeAll(Collection<? c)`

Removes all elements that also exist in another collection.

Example:

```text
A = [1,2,3,4]

B = [2,3]
```

Result:

```text
[1,4]
```

---

## `retainAll(Collection<? c)`

Performs an **intersection**.

Only common elements remain.

Example:

```text
A = [1,2,3,4]

B = [2,3]
```

Result:

```text
[2,3]
```

---

# `toString()` Override

Every collection overrides `toString()`.

Instead of printing a memory address,

it prints:

```text
[1, 2, 3]
```

This makes collections much easier to inspect during debugging.

---

# `equals()` and `hashCode()`

Every Java object inherits:

```java
equals()
```

and

```java
hashCode()
```

If you override one,

you **must** override the other.

This is known as the **equals-hashCode contract**.

Collections such as `HashSet` and `HashMap` rely on both methods to correctly identify duplicate objects and locate elements efficiently.

---

# Modern Default Methods

Since Java 8, the `Collection` interface also includes several **default methods**.

Important examples include:

## `removeIf()`

Removes elements matching a condition.

```java
c.removeIf(x - x % 2 == 0);
```

---

## `stream()`

Creates a sequential stream.

```java
c.stream();
```

Used for functional programming with operations such as:

- `filter()`
- `map()`
- `reduce()`

---

## `parallelStream()`

Creates a parallel stream for concurrent processing.

```java
c.parallelStream();
```

---

## `spliterator()`

Returns a `Spliterator`, which supports efficient traversal and parallel stream processing.

These modern methods are included in the conceptual interface shown in the lecture notes.

---

# Simplified View of the Collection Interface

```java
public interface Collection<E extends Iterable<E {

    int size();
    boolean isEmpty();
    boolean contains(Object o);

    boolean add(E e);
    boolean remove(Object o);

    void clear();

    Object[] toArray();
    <T T[] toArray(T[] a);

    boolean addAll(Collection<? extends E c);
    boolean containsAll(Collection<? c);
    boolean removeAll(Collection<? c);
    boolean retainAll(Collection<? c);

    default boolean removeIf(...);
    default Stream<E stream();
    default Stream<E parallelStream();
    default Spliterator<E spliterator();
}
```

This closely matches the conceptual view presented in the lecture notes.

---

# Key Takeaways

- `Collection` is the root interface for most collection types and extends `Iterable`.
- Common implementations include `ArrayList`, `LinkedList`, `HashSet`, and `ArrayDeque`.
- Generics provide compile-time type safety.
- Programming against the `Collection` interface enables polymorphism and flexible code.
- Do not confuse **Collection (interface)** with **Collections (utility class)**.
- `size()`, `isEmpty()`, `contains()`, `add()`, `remove()`, `clear()`, and `toArray()` are the fundamental operations supported by all collections.
- Performance of methods such as `contains()` depends on the underlying data structure.
- Bulk operations (`addAll`, `containsAll`, `removeAll`, and `retainAll`) simplify working with entire collections.
- Collections override `toString()` to produce readable output such as `[1, 2, 3]`.
- When overriding `equals()`, always override `hashCode()` to satisfy the Java object contract.
- Modern Java versions provide default methods such as `removeIf()`, `stream()`, `parallelStream()`, and `spliterator()` for functional and parallel programming.
