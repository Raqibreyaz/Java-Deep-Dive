# Java `List` Interface

> **Core idea:** A `List` is an ordered collection where every element has a position (index), duplicates are allowed, and elements can be accessed by index. Java provides multiple implementations—mainly `ArrayList`, `LinkedList`, `Vector`, and `Stack`—because different implementations make different performance trade-offs. 

---

## One-Sentence Summary

**Use `List` when order and positional access matter; prefer `ArrayList` in most cases, use `LinkedList` for specific linked-list/deque use cases, and generally avoid legacy `Vector` and `Stack`.**

---

# 1. What is a List?

A `List` is a collection with three important properties:

* **Elements have a position.**
* **Duplicates are allowed.**
* **Insertion order is preserved.**
* Elements can be accessed using an **index**.

Example:

```java
List<Integer> list = new ArrayList<>();

list.add(10);
list.add(20);
list.add(10);
```

The list is:

```text
Index:    0    1    2
         ┌────┬────┬────┐
Value:   │ 10 │ 20 │ 10 │
         └────┴────┴────┘
```

The duplicate `10` is allowed.

---

# 2. List Interface Hierarchy

The basic hierarchy is:

```text
Iterable
   │
Collection
   │
List
   │
   ├── ArrayList
   ├── LinkedList
   └── Vector
          │
         Stack
```

The lecture notes show `Iterable`, `Collection`, and `List` as interfaces, with the concrete implementations below `List`. 

---

# 3. Java 21+: `SequencedCollection`

Modern Java introduced **`SequencedCollection`** to provide common operations for collections that have a defined sequence.

Conceptually:

```text
Iterable
   │
Collection
   │
SequencedCollection
   │
List
```

The purpose is to standardize operations for working with the **first and last elements** of an ordered collection.

Important methods include:

```java
getFirst()
getLast()

addFirst()
addLast()

removeFirst()
removeLast()
```

For example:

```java
List<Integer> list = new ArrayList<>();

list.add(20);
list.add(30);

list.addFirst(10);
list.addLast(40);
```

Result:

```text
[10, 20, 30, 40]
```

The uploaded notes specifically show these `SequencedCollection` operations above `List`. 

---

# 4. Core Methods of `List`

`List` adds positional operations on top of the general `Collection` operations.

---

## `get(index)`

Returns the element at a particular index.

```java
List<Integer> list = List.of(10, 20, 30, 40);

System.out.println(list.get(2));
```

Output:

```text
30
```

Remember:

```text
Index:   0    1    2    3
         ↓    ↓    ↓    ↓
Value:  10   20   30   40
```

---

## `set(index, element)`

Replaces the element at an existing index.

```java
List<Integer> list =
    new ArrayList<>(List.of(10, 20, 30));

list.set(1, 99);
```

Result:

```text
[10, 99, 30]
```

`set()` **replaces** an element.

It does not increase the size of the list.

---

## `add(element)`

Adds an element at the end.

```java
list.add(40);
```

Result:

```text
[10, 99, 30, 40]
```

---

## `add(index, element)`

Inserts an element at a specific position.

```java
list.add(1, 50);
```

Before:

```text
[10, 99, 30, 40]
```

After:

```text
[10, 50, 99, 30, 40]
```

For an `ArrayList`, elements after the insertion point must be shifted.

For a `LinkedList`, references are adjusted once the required node position has been located.

---

## `remove(index)`

Removes the element at a particular index.

```java
list.remove(2);
```

The element at index `2` is removed, and the remaining list is rearranged according to the implementation.

---

# 5. Searching in a List

## `indexOf(object)`

Returns the index of the **first occurrence**.

```java
List<Integer> list =
    List.of(10, 20, 30, 20);

System.out.println(list.indexOf(20));
```

Output:

```text
1
```

---

## `lastIndexOf(object)`

Returns the index of the **last occurrence**.

```java
System.out.println(list.lastIndexOf(20));
```

Output:

```text
3
```

Both methods rely on equality comparison.

---

# 6. `ListIterator`

A normal `Iterator` allows traversal in the forward direction.

A `ListIterator` is more powerful.

It can:

* Move forward.
* Move backward.
* Insert elements.
* Remove elements.
* Replace elements while iterating.

Example:

```java
ListIterator<Integer> it =
    list.listIterator();
```

The lecture notes show `listIterator()` as well as the indexed version:

```java
listIterator(int index)
```

The indexed version starts iteration from a particular position. 

Conceptually:

```text
10 → 20 → 30 → 40
          ↑
       iterator
```

It can move:

```text
forward  →
backward ←
```

This makes `ListIterator` more capable than a normal `Iterator`.

---

# 7. Immutable Lists

Java provides convenient factory methods for creating unmodifiable lists.

## `List.of()`

```java
List<Integer> list =
    List.of(10, 20, 30);
```

The list is unmodifiable.

This means operations such as:

```java
list.add(40);
```

are not allowed.

---

## `List.copyOf()`

Creates an unmodifiable copy of another collection.

```java
List<Integer> original =
    new ArrayList<>();

original.add(10);
original.add(20);

List<Integer> copy =
    List.copyOf(original);
```

`copy` is unmodifiable.

The lecture notes specifically include both `List.of()` and `List.copyOf()` as ways to create fixed/unmodifiable lists. 

---

# 8. ArrayList

`ArrayList` is the most commonly used `List` implementation.

The notes emphasize that it is used in the majority of normal cases because of:

* Random access.
* Cache-friendly storage.
* Simple internal structure. 

---

## Internal Structure

`ArrayList` internally uses a **dynamic array**.

Conceptually:

```text
┌────┬────┬────┬────┐
│ 2  │ 3  │ 4  │ 5  │
└────┴────┴────┴────┘
  0    1    2    3
```

The elements are stored in an array, which gives direct index access.

---

# 9. ArrayList Random Access

Consider:

```java
list.get(2);
```

Because the underlying structure is an array, Java can directly calculate where index `2` is located.

Therefore:

```text
get() → O(1)
```

Similarly:

```text
set() → O(1)
```

This is called **random access**.

---

# 10. ArrayList Resizing

An `ArrayList` has a capacity.

Suppose its internal array is full:

```text
[2][3][4][5]
```

Now:

```java
list.add(6);
```

There is no free position.

So `ArrayList` creates a larger array and copies the existing elements.

The lecture notes give the growth formula as:

```text
newCapacity =
    oldCapacity + (oldCapacity / 2)
```

In other words, capacity grows by approximately **1.5×**. 

Example:

```text
Old capacity = 10

New capacity
= 10 + (10 / 2)
= 15
```

Then the elements are copied into the new array.

---

# 11. ArrayList Insertion Complexity

Appending to the end is generally efficient.

```java
list.add(50);
```

Usually:

```text
O(1) amortized
```

However, inserting at an arbitrary position requires shifting elements.

```java
list.add(1, 50);
```

Suppose:

```text
Before:

[10][20][30][40]
```

Insert `50` at index `1`:

```text
[10][50][20][30][40]
```

Elements had to move.

Therefore:

```text
add(index, element) → O(n)
```

---

# 12. ArrayList Removal

Similarly:

```java
list.remove(1);
```

may require elements after that position to shift left.

Therefore:

```text
remove(index) → O(n)
```

for arbitrary positions.

---

# 13. ArrayList Optimization: `ensureCapacity()`

Suppose you already know that you will add approximately 1000 elements.

Instead of allowing repeated resizing:

```java
List<Integer> list = new ArrayList<>();

// many additions...
```

you can pre-allocate capacity:

```java
ArrayList<Integer> list =
    new ArrayList<>();

list.ensureCapacity(1000);
```

This tells the `ArrayList` to prepare enough internal capacity.

It can reduce repeated resizing and copying when you know the expected size beforehand.

The uploaded notes demonstrate `ensureCapacity()` as an optimization technique. 

---

# 14. `trimToSize()`

`trimToSize()` removes unused capacity from an `ArrayList`.

Imagine:

```text
Capacity = 100
Size     = 20
```

There is a lot of unused allocated space.

Calling:

```java
list.trimToSize();
```

can reduce the internal capacity closer to the actual size.

Think:

```text
Before:

Capacity → 100
Used     → 20

After trim:

Capacity → approximately 20
Used     → 20
```

Use this when the list is unlikely to grow further and reducing unused memory is useful.

---

# 15. ArrayList Constructors

The notes show common constructor forms:

```java
List<Integer> list =
    new ArrayList<>();
```

```java
List<Integer> list =
    new ArrayList<>(10);
```

The second version provides an initial capacity.

You can also create an `ArrayList` from an existing collection:

```java
List<Integer> list =
    new ArrayList<>(otherCollection);
```

---

# 16. LinkedList

`LinkedList` uses a **doubly linked list** internally.

Each node conceptually contains:

```text
data
next
prev
```

Example:

```text
       next       next       next
[1] ⇄ [2] ⇄ [3] ⇄ [4]
       prev       prev       prev
```

The first node is the `head`, and the final node is the `tail`.

The notes show this structure using nodes containing both `next` and `prev` references. 

---

# 17. LinkedList Node Structure

Conceptually:

```java
class Node<T> {

    T data;

    Node<T> next;

    Node<T> prev;
}
```

So each node knows:

```text
previous node
      ↑
    [data]
      ↓
 next node
```

---

# 18. LinkedList and Random Access

Unlike `ArrayList`, a `LinkedList` does not have direct random access.

Suppose:

```java
list.get(2);
```

Java has to reach the appropriate node.

However, `LinkedList` can optimize the search.

If the requested index is near the beginning:

```text
head → → → target
```

it starts from the head.

If the index is near the end:

```text
target ← ← ← tail
```

it starts from the tail.

The uploaded notes specifically illustrate this head/tail optimization. 

Still, indexed access is generally:

```text
get(index) → O(n)
```

---

# 19. LinkedList Insertion

One advantage of linked lists is that once the correct node position is known, insertion only requires reference changes.

For example:

```text
Before:

1 ⇄ 2 ⇄ 4

Insert 3:

1 ⇄ 2 ⇄ 3 ⇄ 4
```

No large block of array elements needs to be shifted.

The important distinction is:

> **Finding the position can take O(n), but changing the links at a known position is O(1).**

This is why simply saying "LinkedList insertion is always O(1)" is incomplete.

---

# 20. LinkedList as Queue / Stack

Because `LinkedList` supports operations at both ends, it can also be used for queue/deque-style operations.

For example:

```java
LinkedList<Integer> list =
    new LinkedList<>();

list.addFirst(10);
list.addLast(20);

list.removeFirst();
list.removeLast();
```

However, modern Java provides `ArrayDeque` for many queue and stack use cases.

---

# 21. ArrayList vs LinkedList

| Feature             | `ArrayList`                | `LinkedList`                      |
| ------------------- | -------------------------- | --------------------------------- |
| Internal structure  | Dynamic array              | Doubly linked list                |
| Random access       | Fast                       | Slow                              |
| `get(index)`        | O(1)                       | O(n)                              |
| `set(index)`        | O(1)                       | O(n)                              |
| Arbitrary insertion | O(n)                       | O(n) to locate + O(1) link change |
| Memory locality     | Good                       | Poorer                            |
| Cache friendliness  | High                       | Lower                             |
| Typical usage       | Most general-purpose lists | Specific linked-list/deque cases  |

---

# 22. Why ArrayList Is Usually Preferred

The lecture notes point out that `ArrayList` is commonly preferred because it provides:

```text
Random Access
      +
Cache Friendly
      +
Simple Structure
```

The notes visually mark `ArrayList` as the common choice for roughly **90%** of typical `List` usage. 

Why is it cache-friendly?

Its elements are stored in an array-like structure, so nearby elements are stored close together in memory.

When the CPU accesses one element, nearby elements are more likely to already be available in the CPU cache.

This often gives `ArrayList` better practical performance than a linked list even when their theoretical complexities look similar for some operations.

---

# 23. Vector

`Vector` is another implementation of `List`.

It is a **legacy class** from early Java.

Conceptually:

```text
List
 │
Vector
 │
Stack
```

The important difference is that `Vector` is **thread-safe through synchronization**.

This synchronization introduces overhead.

Therefore, in modern single-threaded/general-purpose code:

```text
ArrayList
```

is generally preferred over:

```text
Vector
```

---

# 24. Stack

`Stack` is a legacy class that extends `Vector`.

Hierarchy:

```text
List
 │
Vector
 │
Stack
```

It represents a stack data structure.

A stack follows:

```text
LIFO

Last In
First Out
```

Example:

```text
push(10)
push(20)
push(30)
```

Stack:

```text
Top
 ↓
30
20
10
```

The first element removed is:

```text
30
```

---

# 25. Modern Alternative to Stack

Instead of using the legacy:

```java
Stack<Integer>
```

modern Java code generally uses:

```java
ArrayDeque<Integer>
```

for stack behavior.

Example:

```java
Deque<Integer> stack =
    new ArrayDeque<>();

stack.push(10);
stack.push(20);
stack.push(30);

System.out.println(stack.pop());
```

Output:

```text
30
```

The notes explicitly recommend `ArrayDeque` as the modern choice for stack functionality. 

---

# 26. Legacy Classes

The main legacy classes discussed are:

```text
Vector
Stack
```

They were designed in early versions of Java and retain synchronized behavior.

The notes summarize their modern replacements as:

```text
Vector
   ↓
ArrayList

Stack
   ↓
ArrayDeque
```

The notes also distinguish `Hashtable` from modern `HashMap` in this legacy-class discussion. 

---

# 27. Quick Decision Guide

### Need a normal ordered collection?

Use:

```java
ArrayList
```

### Need frequent indexed access?

Use:

```java
ArrayList
```

### Need a linked structure specifically?

Consider:

```java
LinkedList
```

### Need stack behavior?

Prefer:

```java
ArrayDeque
```

### Need a legacy synchronized dynamic array?

`Vector` exists, but it is generally not the first choice for new code.

### Need the old `Stack` class?

It exists, but prefer:

```java
ArrayDeque
```

for modern stack behavior.

---

# 28. Important Complexity Table

| Operation           |          ArrayList |                                LinkedList |
| ------------------- | -----------------: | ----------------------------------------: |
| `get(index)`        |           **O(1)** |                                  **O(n)** |
| `set(index, value)` |           **O(1)** |                                  **O(n)** |
| `add(value)` at end | **O(1) amortized** |                                  **O(1)** |
| `add(index, value)` |           **O(n)** | **O(n)** to locate + **O(1)** link change |
| `remove(index)`     |           **O(n)** | **O(n)** to locate + **O(1)** link change |
| Search by value     |               O(n) |                                      O(n) |

The key point is that **theoretical complexity is not the only factor**. Memory locality and CPU cache behavior can make `ArrayList` faster in many practical workloads.

---

# Common Mistakes / Gotchas

### 1. Thinking `List` Means No Duplicates

Wrong.

`List` **allows duplicates**.

```java
List<Integer> list =
    List.of(10, 10, 20);
```

Valid.

---

### 2. Confusing `set()` with `add()`

```java
list.set(1, 50);
```

replaces an existing element.

```java
list.add(1, 50);
```

inserts a new element and shifts existing elements when necessary.

---

### 3. Assuming LinkedList Always Has O(1) Insertion

Not exactly.

If you already have the target node:

```text
link change → O(1)
```

But finding the target index may require:

```text
O(n)
```

---

### 4. Assuming LinkedList Is Automatically Faster

A linked list avoids array shifting, but it has:

* Poor random access.
* More memory overhead per element.
* Worse cache locality.

Therefore, `LinkedList` is **not automatically faster** than `ArrayList`.

---

### 5. Using `Stack` in New Code Without a Reason

`Stack` is a legacy class.

For modern stack behavior:

```java
Deque<Integer> stack =
    new ArrayDeque<>();
```

is generally preferred.

---

### 6. Forgetting That `List.of()` Is Unmodifiable

```java
List<Integer> list =
    List.of(1, 2, 3);

list.add(4);
```

This is not allowed because the returned list is unmodifiable.

---

# Key Takeaways

* `List` is an ordered collection.
* Lists preserve insertion order.
* Lists allow duplicate elements.
* Every element has an index.
* `get(index)` and `set(index, value)` are central `List` operations.
* `indexOf()` finds the first matching element.
* `lastIndexOf()` finds the last matching element.
* `ListIterator` supports forward and backward traversal plus modification during iteration.
* `List.of()` and `List.copyOf()` create unmodifiable lists.
* `ArrayList` uses a dynamic array.
* `ArrayList` provides O(1) random access.
* The notes describe `ArrayList` growth as approximately **1.5×**:

> ```text
> newCapacity = oldCapacity + oldCapacity / 2
> ```

* `ensureCapacity()` can reduce repeated resizing when the required size is known.
* `trimToSize()` can remove unused capacity.
* `LinkedList` is a doubly linked list containing `next` and `prev` references.
* `LinkedList.get(index)` is O(n), although it can start from whichever end is closer.
* `ArrayList` is generally preferred for normal list usage because of random access, cache friendliness, and simpler structure.
* `Vector` and `Stack` are legacy synchronized classes.
* Prefer `ArrayList` over `Vector` for normal modern list usage.
* Prefer `ArrayDeque` over the legacy `Stack` class for stack behavior. 

---

# Minimal Self-Test

1. Why is `List<...>` different from `Set<...>`?
2. What is the difference between `add(index, value)` and `set(index, value)`?
3. Why is `ArrayList.get(index)` O(1)?
4. Why is `LinkedList.get(index)` O(n)?
5. How does `LinkedList` optimize indexed lookup?
6. Why does `ArrayList` need to resize its internal array?
7. What is the `ArrayList` growth formula discussed in the lecture?
8. When would `ensureCapacity()` be useful?
9. What does `trimToSize()` do?
10. Why is `ArrayList` generally preferred over `LinkedList` for normal list usage?
11. What extra capabilities does `ListIterator` provide over `Iterator`?
12. Why are `Vector` and `Stack` considered legacy classes?
13. What should you generally use instead of `Stack` in modern Java?
14. What is the purpose of `SequencedCollection` in modern Java?
15. Why can `List.of()` not be modified?
