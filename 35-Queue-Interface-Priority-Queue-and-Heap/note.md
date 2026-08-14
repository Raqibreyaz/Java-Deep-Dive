# Java Queue Interface



## One-sentence summary

A **Queue** is a data structure mainly used for **FIFO (First In, First Out)** processing, while Java provides `Queue`, `Deque`, `ArrayDeque`, `LinkedList`, and `PriorityQueue` to support different queue-like behaviors.

---

## 1. What is a Queue?

A **Queue** is a data structure where:

> The element inserted first is normally removed first.

This is called **FIFO — First In, First Out**.

Think of a line at a ticket counter:

```text
                    Queue
                      ↓
        ┌────┬────┬────┬────┐
Front ← │ 10 │ 20 │ 30 │ 40 │ ← Rear
        └────┴────┴────┴────┘
          ↑              ↑
       remove          insert
```

If we insert:

```text
10 → 20 → 30 → 40
```

Then removal happens as:

```text
10 → 20 → 30 → 40
```

The **front** is where elements are removed, and the **rear** is where elements are inserted.

The first page of the notes shows this basic idea using both an array and a linked-list representation. 

---

# 2. Queue vs Stack

Queue and Stack are both linear data structures, but their removal order is different.

## Queue → FIFO

**First In, First Out**

```text
Insert → 10 → 20 → 30 → 40
                 ↑
              removed first
```

The element that entered first leaves first.

### Real-life example

A queue of people waiting for a bus:

```text
Person A → Person B → Person C
   ↑
first person to enter
```

Person A gets served first.

---

## Stack → LIFO

**Last In, First Out**

A Stack inserts and removes elements from the same end, called the **top**.

```text
       TOP
        ↓
      ┌────┐
      │ 40 │ ← remove
      ├────┤
      │ 30 │
      ├────┤
      │ 20 │
      ├────┤
      │ 10 │
      └────┘
```

If we push:

```text
10 → 20 → 30 → 40
```

Then pop happens:

```text
40 → 30 → 20 → 10
```

So:

| Structure | Rule |
| --------- | ---- |
| Queue     | FIFO |
| Stack     | LIFO |

The notes compare Queue and Stack on the first two pages and show that both can be implemented using arrays or linked lists. 

---

# 3. Implementing a Queue

A queue can be implemented mainly using:

1. **Array**
2. **Linked List**

The goal is to make insertion and deletion efficient, ideally **O(1)** for the basic queue operations.

---

## 3.1 Queue using an Array

An array-based queue maintains two important positions:

```text
front
  ↓
┌────┬────┬────┬────┬────┬────┐
│ 10 │ 20 │ 30 │ 40 │    │    │
└────┴────┴────┴────┴────┴────┘
                         ↑
                        rear
```

* `front` → points to the element that should be removed.
* `rear` → represents the insertion side.

Initially, the notes show:

```text
front = -1
rear  = -1
```

When elements are inserted, these positions are updated.

### Problem with a simple array

Suppose the array becomes full:

```text
[10][20][30][40][50]
 ↑               ↑
front           rear
```

A larger array has to be created and the elements copied into it.

This resizing/copying is one reason array implementations need additional handling.

---

# 4. Circular Queue Idea

The notes on page 1 introduce the idea of a **circular queue**.

Instead of treating the array as if it ends permanently at the last index, we can wrap around to the beginning when space is available.

For example:

```text
Index:
  0    1    2    3    4    5
┌────┬────┬────┬────┬────┬────┐
│    │    │ 30 │ 40 │    │    │
└────┴────┴────┴────┴────┴────┘
              ↑         ↑
            front      rear
```

If the rear reaches the end, it can move back toward index `0` if there is free space.

This avoids unnecessarily shifting elements.

---

# 5. Queue using a Linked List

A linked-list implementation uses nodes.

A node contains:

```text
┌───────────────┐
│ data │ next   │
└───────────────┘
```

For example:

```text
front
  ↓
┌───┬───┐    ┌───┬───┐    ┌───┬───┐
│ 2 │ •─┼──→ │ 7 │ •─┼──→ │ 9 │null│
└───┴───┘    └───┴───┘    └───┴───┘
                               ↑
                              rear
```

The node structure shown in the notes is essentially:

```text
class Node {
    int data;
    Node next;
}
```

The important advantage is that we do not need to resize an array.

The notes also emphasize that a linked-list queue can maintain efficient insertion/deletion without dealing with array resizing or circular-array logic. 

---

# 6. Java Collections Hierarchy

The notes show the following important hierarchy:

```text
Iterable
   ↓
Collection
   ├── List
   │    └── LinkedList
   │
   └── Queue
        ├── Deque
        │    ├── ArrayDeque
        │    └── LinkedList
        │
        └── PriorityQueue
```

The important point is that **Queue is an interface**, not a concrete data structure.

Different classes provide different implementations.

---

# 7. What is Deque?

`Deque` means:

> **Double-Ended Queue**

A normal Queue mainly thinks in terms of:

```text
insert → rear
remove → front
```

A `Deque` allows operations from **both ends**.

```text
       front                    rear
         ↓                       ↓
    ┌────┬────┬────┬────┐
    │ 10 │ 20 │ 30 │ 40 │
    └────┴────┴────┴────┘
       ↑                 ↑
    operate           operate
    here              here
```

So you can:

* add at the front
* add at the rear
* remove from the front
* remove from the rear
* inspect the front
* inspect the rear

The page 3 hierarchy specifically shows `Queue → Deque`, with `ArrayDeque` and `LinkedList` as implementations. 

---

# 8. `ArrayDeque`

`ArrayDeque` is one of the important implementations of `Deque`.

It is implemented using an **array-based circular structure**.

Conceptually:

```text
             ┌───────────────────────┐
             │                       │
             ↓                       │
        ┌────┬────┬────┬────┬────┐  │
        │ 10 │ 20 │ 30 │ 40 │ 50 │──┘
        └────┴────┴────┴────┴────┘
```

When the end of the array is reached, the internal positions can wrap around.

This makes it possible to use previously unused positions efficiently.

The notes illustrate this on page 4 using an `ArrayDeque` containing values such as:

```text
[5, 7, 9, 10, 11, 6]
```

and show how the internal front position can move as elements are removed. 

---

## Why is `ArrayDeque` useful?

It gives efficient operations at both ends.

It can behave like a:

* Queue
* Deque
* Stack

The notes specifically recommend `ArrayDeque` rather than the old `Stack` class.

---

# 9. `Stack` vs `ArrayDeque`

Java has a legacy `Stack` class.

The notes describe it as:

> Legacy and relatively slow.

Instead, `ArrayDeque` can be used to implement stack behavior.

For example:

```java
Deque<Integer> stack = new ArrayDeque<>();

stack.push(10);
stack.push(20);
stack.push(30);
```

Conceptually:

```text
TOP
 ↓
30
20
10
```

Then:

```java
stack.pop();
```

removes:

```text
30
```

So:

```text
Stack behavior
    ↓
ArrayDeque
    ↓
push / pop / peek
```

The notes on pages 2, 3, and 5 show this connection explicitly. 

---

# 10. `ArrayDeque` and `null`

An important point from the notes:

```text
ArrayDeque → null ❌
LinkedList → null ✓
```

The intended distinction is that **`ArrayDeque` does not allow `null` elements**, whereas `LinkedList` can contain `null`.

Example:

```java
Deque<Integer> deque = new ArrayDeque<>();

deque.add(null);   // not allowed
```

This is a useful interview gotcha.

---

# 11. Queue Methods

The `Queue` interface provides methods in pairs.

There are three major operations:

1. **Insert**
2. **Remove**
3. **Inspect**

The notes divide them into methods that throw exceptions and methods that return special values. 

---

## 11.1 Insert

### `add(E e)`

Adds an element.

```java
queue.add(10);
```

If the insertion cannot be performed, it can throw an exception.

### `offer(E e)`

Also attempts to add an element:

```java
queue.offer(10);
```

It uses a return-value-based approach instead of the exception-based behavior of `add()`.

---

## 11.2 Remove

### `remove()`

Removes and returns the front element.

```java
queue.remove();
```

If the queue is empty, it throws an exception.

### `poll()`

Removes and returns the front element.

```java
queue.poll();
```

If the queue is empty, it returns:

```text
null
```

instead of throwing an exception.

---

## 11.3 Inspect

### `element()`

Returns the front element without removing it.

```java
queue.element();
```

If the queue is empty, it throws an exception.

### `peek()`

Returns the front element without removing it.

```java
queue.peek();
```

If the queue is empty, it returns:

```text
null
```

---

# 12. Queue Method Comparison

This is one of the most important tables to remember.

| Operation | Exception-based | Safer/special-value version |
| --------- | --------------- | --------------------------- |
| Insert    | `add()`         | `offer()`                   |
| Remove    | `remove()`      | `poll()`                    |
| Inspect   | `element()`     | `peek()`                    |

A useful memory trick:

```text
add     ↔ offer
remove  ↔ poll
element ↔ peek
```

The notes explicitly organize the Queue methods this way on page 5. 

---

# 13. Deque Methods

Because `Deque` works from both ends, its methods have `First` and `Last` variants.

For example:

```java
addFirst()
addLast()

removeFirst()
removeLast()

peekFirst()
peekLast()
```

There are also corresponding `offer` and `poll` forms.

Conceptually:

```text
                  Deque
                    |
       ┌────────────┴────────────┐
       ↓                         ↓
    FIRST                       LAST
       ↓                         ↓
 addFirst()                  addLast()
 removeFirst()              removeLast()
 peekFirst()                peekLast()
```

The notes on page 5 organize these operations according to **add**, **remove**, and **inspect**. 

---

# 14. Stack Operations Using Deque

A stack can be represented using one end of a `Deque`.

The common stack methods are:

```java
push()
pop()
peek()
```

These correspond conceptually to:

```text
push() → offerFirst()
pop()  → pollFirst()
peek() → peekFirst()
```

So you can think:

```text
Deque
  ↓
use one end
  ↓
Stack
```

This is why `ArrayDeque` can replace the legacy `Stack` class for typical stack usage.

---

# 15. Queue vs Deque vs Stack

| Structure | Main behavior | Ends used                 |
| --------- | ------------- | ------------------------- |
| Queue     | FIFO          | Insert rear, remove front |
| Deque     | Double-ended  | Both ends                 |
| Stack     | LIFO          | One end                   |

Mental model:

```text
Queue:

insert → [ 10 20 30 ] → remove
            FIFO


Stack:

             push
              ↓
            [ 30 ]
            [ 20 ]
            [ 10 ]
              ↑
             pop


Deque:

add/remove ← [ 10 20 30 ] → add/remove
```

---

# 16. `PriorityQueue`

A `PriorityQueue` is different from a normal FIFO queue.

A normal Queue says:

> "Who came first?"

A `PriorityQueue` says:

> "Who has the highest priority?"

Therefore:

```text
PriorityQueue ≠ FIFO
```

The page 6 notes explicitly mark PriorityQueue as **not FIFO**. 

---

## Example

Suppose we insert:

```text
5
10
1
7
2
```

In a normal queue, removal would follow insertion order:

```text
5 → 10 → 1 → 7 → 2
```

But a PriorityQueue organizes elements according to priority.

The notes describe the default behavior as a **Min-Heap**, meaning the smallest element has the highest priority.

So the smallest element comes out first.

Conceptually:

```text
1
2
5
7
10
```

---

# 17. PriorityQueue Internals: Heap

The notes explain that `PriorityQueue` internally uses a **Heap data structure**.

More specifically:

```text
PriorityQueue
      ↓
   Heap DS
      ↓
 Binary Heap
      ↓
 Array-based representation
```

A heap can be visualized as a **complete binary tree**.

---

# 18. What is a Complete Binary Tree?

A complete binary tree fills levels from left to right.

Example:

```text
          10
        /    \
      30      20
     /  \
   40    50
```

Every level is filled from left to right except possibly the last level.

The notes show this tree and then its array representation:

```text
Index:   0   1   2   3   4

Array:  [10, 30, 20, 40, 50]
```

So instead of creating separate tree nodes, the heap can be stored efficiently inside an array. 

---

# 19. Heap Index Formulas

This is extremely important for understanding how a binary heap works internally.

For a node at index `i`:

### Left child

```text
2i + 1
```

### Right child

```text
2i + 2
```

### Parent

```text
floor((i - 1) / 2)
```

For example, consider:

```text
Index:   0    1    2    3    4
Value:  10   30   20   40   50
```

For index `1`:

```text
left child  = 2(1) + 1 = 3
right child = 2(1) + 2 = 4
```

Therefore:

```text
        30 (index 1)
       /           \
40 (index 3)    50 (index 4)
```

This relationship is shown on page 6 of the notes. 

---

# 20. Min-Heap

A **Min-Heap** keeps the smallest element at the root.

Example:

```text
          10
        /    \
      30      20
     /  \
   40    50
```

Notice:

```text
10 < 30
10 < 20
30 < 40
30 < 50
```

The parent has priority over its children according to the heap rule.

Therefore, the root is the highest-priority element in a min-priority queue.

---

# 21. Max-Heap

A **Max-Heap** is the opposite.

The largest element is at the root.

Example:

```text
          50
        /    \
      30      40
     /  \
   10    20
```

So:

```text
Min-Heap → smallest element at root
Max-Heap → largest element at root
```

The notes show both **Min Heap** and **Max Heap** as types of heap data structures. 

---

# 22. `offer()` in PriorityQueue: Up-Heapify

When a new element is inserted into a heap, it is initially placed at the next available position.

Then it may need to move upward to maintain the heap property.

This process is called:

> **Up-Heapify**

The page 7 notes connect:

```text
PriorityQueue.offer()
          ↓
      Up-Heapify
```

### Example

Suppose the heap is:

```text
       10
      /  \
    20    30
```

Now insert:

```text
5
```

Initially:

```text
       10
      /  \
    20    30
   /
  5
```

`5` is smaller than its parent `20`, so they swap:

```text
       10
      /  \
     5    30
    /
   20
```

Then `5` is smaller than `10`, so it moves again:

```text
        5
      /   \
    10     30
   /
  20
```

This upward movement is **up-heapify**.

---

# 23. `poll()` in PriorityQueue: Down-Heapify

When we remove the root of a heap, the heap needs to be reorganized.

This is called:

> **Down-Heapify**

The notes connect:

```text
PriorityQueue.poll()
          ↓
      Down-Heapify
```

For a Min-Heap, suppose:

```text
        5
      /   \
    10     20
   /  \
  30   40
```

We remove `5`.

The last element is moved to the root temporarily:

```text
        40
      /    \
    10      20
   /
  30
```

Now `40` violates the Min-Heap property.

It compares with its children and moves downward:

```text
        10
      /    \
    40      20
   /
  30
```

Then it continues until the heap property is restored.

That is **down-heapify**.

---

# 24. Why is Heap Used?

A heap gives efficient access to the highest-priority element.

For a PriorityQueue using a Min-Heap:

```text
smallest element
       ↓
      root
```

The root can be accessed efficiently, while insertion and removal require reorganizing only the height of the tree.

A heap with `n` elements has a height of approximately:

```text
log₂(n)
```

Therefore:

```text
offer() → O(log n)
poll()  → O(log n)
```

The notes explicitly give **O(log n)** for these PriorityQueue operations. 

---

# 25. PriorityQueue Operation Flow

The complete picture is:

```text
PriorityQueue.offer(element)
          ↓
      Insert element
          ↓
     Up-Heapify
          ↓
   Heap property restored


PriorityQueue.poll()
          ↓
     Remove root
          ↓
    Down-Heapify
          ↓
   Heap property restored
```

The notes on page 7 illustrate both up-heapify and down-heapify and connect them directly to `offer()` and `poll()`. 

---

# 26. PriorityQueue Methods

`PriorityQueue` follows the same Queue method model.

### Insert

```java
add()
offer()
```

### Remove

```java
remove()
poll()
```

### Inspect

```java
element()
peek()
```

So:

```text
PriorityQueue

        add()      offer()
           ↓         ↓
          INSERT

        remove()    poll()
           ↓          ↓
          REMOVE

        element()   peek()
           ↓          ↓
         INSPECT
```

The page 7 notes summarize this method mapping and indicate `offer()`/`poll()`/`peek()` as the safer alternatives. 

---

# 27. Important Complexity Comparison

From the concepts shown in the notes:

| Structure / operation               | Typical idea                                     |
| ----------------------------------- | ------------------------------------------------ |
| Array Queue insertion/removal       | O(1) goal, but resizing/array management matters |
| Linked-list Queue insertion/removal | O(1) with appropriate front/rear references      |
| Deque end operations                | O(1) goal                                        |
| PriorityQueue `offer()`             | O(log n)                                         |
| PriorityQueue `poll()`              | O(log n)                                         |

The major difference is that a normal Queue cares about **position/order**, while a PriorityQueue must maintain a **heap property**.

---

# 28. `ArrayDeque` vs `LinkedList`

Both can be used as `Deque` implementations.

```text
Deque
 ├── ArrayDeque
 └── LinkedList
```

### ArrayDeque

Uses an array-based circular structure.

```text
[ ][ ][ ][ ][ ][ ][ ]
 ↑              ↑
front          rear
```

Advantages highlighted in the notes:

* array-based
* contiguous storage
* cache-friendly
* efficient deque operations
* suitable for stack behavior
* suitable for queue behavior

The page 4 notes specifically mention that arrays are **contiguous** and therefore **cache-friendly**. 

### LinkedList

Uses linked nodes:

```text
[2] → [3] → [4] → null
```

Each node is separately linked.

So the conceptual difference is:

```text
ArrayDeque:
[ 2 ][ 3 ][ 4 ][   ][   ]
      contiguous


LinkedList:
[2] → [3] → [4]
```

---

# 29. Why Contiguous Memory Matters

The notes point out an important performance idea:

> Arrays use contiguous memory and are cache-friendly.

Conceptually:

```text
Array

Memory:
[2][3][4][5][6]
 ↑ ↑ ↑ ↑ ↑
near each other
```

A CPU cache can often work efficiently with nearby memory locations.

A linked list may look like:

```text
[2] ─────→ [3]
              \
               ─────→ [4]
```

The nodes do not have to be next to each other in memory.

So even when two structures have similar Big-O complexity, their real-world performance can differ.

---

# 30. Important Mental Model

Think of the Java classes like this:

```text
                    Collection
                        │
             ┌──────────┴──────────┐
             │                     │
            List                  Queue
             │                     │
        LinkedList               Deque
                                   │
                          ┌────────┴────────┐
                          │                 │
                     ArrayDeque        LinkedList


                    Queue
                      │
                PriorityQueue
                      │
                    Heap
                      │
                Binary Heap
                      │
                  Array-based
```

This is the core architecture illustrated across pages 3, 4, 6, and 7. 

---

# 31. Common Interview Gotchas

## Gotcha 1: Queue is not always strictly FIFO

A normal Queue follows FIFO.

But:

```java
PriorityQueue
```

is a Queue implementation where elements are processed according to priority.

So:

```text
Queue ≠ necessarily "always insertion order"
```

`PriorityQueue` is the important exception discussed in the notes.

---

## Gotcha 2: `peek()` does not remove

```java
queue.peek();
```

only looks at the front.

It does **not** remove the element.

Compare:

```java
queue.peek();  // inspect
queue.poll();  // remove
```

---

## Gotcha 3: `element()` vs `peek()`

Both inspect the front.

But their empty-queue behavior differs:

```text
element() → exception
peek()    → null
```

---

## Gotcha 4: `remove()` vs `poll()`

Both remove the front element.

```text
remove() → exception if empty
poll()   → null if empty
```

---

## Gotcha 5: `add()` vs `offer()`

Both attempt insertion, but their failure behavior differs.

```text
add()   → exception-based
offer() → return-value-based
```

---

## Gotcha 6: `Stack` is legacy

The notes recommend using `ArrayDeque` for stack-like behavior rather than relying on the old `Stack` class. 

---

## Gotcha 7: `ArrayDeque` does not accept `null`

Remember:

```text
ArrayDeque → null ❌
LinkedList → null ✓
```

This is a useful distinction when choosing an implementation.

---

## Gotcha 8: PriorityQueue does not mean sorted iteration

The important concept from the notes is that the **priority element is maintained at the heap root**.

A PriorityQueue should therefore be understood as a **heap-based priority structure**, not simply as a normal sorted list.

---

# 32. Quick Method Cheat Sheet

### Queue

```java
add(e)       // insert
offer(e)     // insert

remove()     // remove
poll()       // remove

element()    // inspect
peek()       // inspect
```

Remember:

```text
ADD       → OFFER
REMOVE    → POLL
ELEMENT   → PEEK
```

---

### Deque

```java
addFirst(e)
addLast(e)

removeFirst()
removeLast()

peekFirst()
peekLast()
```

Also available are corresponding `offerFirst`, `offerLast`, `pollFirst`, `pollLast`, etc.

---

### Stack behavior with Deque

```java
push(e)
pop()
peek()
```

Mental mapping:

```text
push() → insert
pop()  → remove
peek() → inspect
```

---

# 33. Queue vs PriorityQueue

| Feature                   | Normal Queue                                    | PriorityQueue               |
| ------------------------- | ----------------------------------------------- | --------------------------- |
| Main idea                 | FIFO                                            | Priority                    |
| Removal                   | Front element                                   | Highest-priority element    |
| Internal structure        | Can use array/linked list                       | Heap                        |
| Default priority behavior | Not applicable                                  | Min-Heap                    |
| `offer()`                 | Queue insertion                                 | Heap insertion + up-heapify |
| `poll()`                  | Remove front                                    | Remove root + down-heapify  |
| `offer()` complexity      | Intended O(1) for suitable queue implementation | O(log n)                    |
| `poll()` complexity       | Intended O(1) for suitable queue implementation | O(log n)                    |

---

# 34. The Whole Topic in One Diagram

```text
                         Java Collections
                                │
                           Collection
                                │
                              Queue
                         ┌──────┴──────┐
                         │             │
                       Deque      PriorityQueue
                         │             │
                  ┌──────┴──────┐      ↓
                  │             │     Heap
             ArrayDeque      LinkedList
                  │
            ┌─────┴─────┐
            ↓           ↓
          Queue        Stack
        behavior      behavior
          FIFO         LIFO


PriorityQueue
      ↓
 Binary Heap
      ↓
Array representation
      ↓
┌────┬────┬────┬────┬────┐
│ 10 │ 30 │ 20 │ 40 │ 50 │
└────┴────┴────┴────┴────┘
  0    1    2    3    4

left child  = 2i + 1
right child = 2i + 2
parent      = floor((i - 1) / 2)

offer()
   ↓
up-heapify

poll()
   ↓
down-heapify
```

---

# 35. Key Takeaways

* **Queue = FIFO**.
* **Stack = LIFO**.
* Both Queue and Stack can be implemented using **arrays or linked lists**.
* Array-based queues need to think about **resizing and circular movement**.
* Linked-list queues use **nodes** and avoid array resizing.
* `Deque` means **Double-Ended Queue**.
* A `Deque` allows insertion/removal from **both ends**.
* `ArrayDeque` is an efficient implementation of `Deque`.
* `ArrayDeque` can be used for both **Queue and Stack behavior**.
* The notes recommend `ArrayDeque` instead of the legacy `Stack` class.
* `ArrayDeque` does **not allow `null`** elements.
* `LinkedList` can be used as both a `List` and a `Deque`.
* `add()` / `remove()` / `element()` are the exception-oriented Queue methods.
* `offer()` / `poll()` / `peek()` are the safer special-value-oriented alternatives.
* `PriorityQueue` does **not follow normal FIFO behavior**.
* `PriorityQueue` uses a **heap** internally.
* The default priority behavior discussed is a **Min-Heap**.
* A binary heap is a **complete binary tree represented efficiently using an array**.
* For array index `i`:

  * left child = `2i + 1`
  * right child = `2i + 2`
  * parent = `floor((i - 1) / 2)`
* `offer()` in a PriorityQueue uses **up-heapify**.
* `poll()` uses **down-heapify**.
* PriorityQueue `offer()` and `poll()` are **O(log n)**.
* Array storage is **contiguous and cache-friendly**, which is an important practical performance consideration.

---

# 36. Minimal Self-Test

Try answering these without looking back:

1. What does FIFO mean?
2. What is the difference between Queue and Stack?
3. What are `front` and `rear` used for?
4. Why can an array-based queue need resizing?
5. What problem does a circular queue solve?
6. How does a linked-list queue represent its elements?
7. What does `Deque` mean?
8. Why can `ArrayDeque` be used as a Stack?
9. Why is `Stack` considered a legacy choice in these notes?
10. What is the difference between `add()` and `offer()`?
11. What is the difference between `remove()` and `poll()`?
12. What is the difference between `element()` and `peek()`?
13. What does `PriorityQueue` prioritize?
14. Why is `PriorityQueue` not simply FIFO?
15. What is a Min-Heap?
16. What is a Max-Heap?
17. What is a complete binary tree?
18. Why can a heap be stored in an array?
19. What is the left-child formula for index `i`?
20. What is the right-child formula?
21. What is the parent formula?
22. What happens during up-heapify?
23. What happens during down-heapify?
24. What is the complexity of PriorityQueue `offer()`?
25. What is the complexity of PriorityQueue `poll()`?
26. Why can `ArrayDeque` have practical cache advantages over a linked structure?
27. Can `ArrayDeque` contain `null`?
28. Can `LinkedList` be used as a `Deque`?
29. Can `ArrayDeque` behave like a Queue?
30. Can `ArrayDeque` behave like a Stack?

---

# 37. What to Learn Next

The natural next step is to study **PriorityQueue and Binary Heap implementation in more depth**, especially:

```text
Binary Heap
   ↓
Min-Heap / Max-Heap
   ↓
Insert
   ↓
Up-Heapify
   ↓
Remove Root
   ↓
Down-Heapify
   ↓
PriorityQueue
```

After that, practice implementing a **Queue, Deque, Stack, and Min-Heap from scratch** using arrays. That will make the Java Collection implementations much easier to understand.
