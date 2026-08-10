# Set and Map Interfaces in Java



## One-sentence summary

**`Set` stores unique elements, while `Map` stores unique keys mapped to values; hash-based implementations use buckets and hashing for average `O(1)` lookup, while tree-based implementations use Red-Black Trees for `O(log n)` lookup and sorted data.**

---

# 1. What are Set and Map?

Java provides different collection types for different problems.

Two important ones are:

* **`Set`** → stores unique elements.
* **`Map`** → stores data in **key-value pairs**.

A simple way to remember:

```text
Set
 ↓
{ 10, 20, 30, 40 }

No duplicate elements
```

```text
Map
 ↓
{ 101 → "Aditya"
  102 → "Rohit"
  103 → "Rohan" }

Unique keys → values
```

The lecture starts with two important properties of `Set`:

1. **Duplicates are not allowed.**
2. Search/contains is generally **constant time `O(1)`** for hash-based sets.

For `Map`:

1. Data is stored as **`(key, value)`**.
2. Duplicate keys are not allowed.
3. Operations such as `put()`, `get()`, and `containsKey()` are generally **`O(1)` on average** for a `HashMap`. 

---

# 2. Why do we need Set and Map?

Suppose we have a list:

```java
List<Integer> list = ...
```

and we want to check whether `50` exists.

With a list, we may have to inspect elements one by one:

```text
10 → 20 → 30 → 40 → 50
```

In the worst case:

```text
O(n)
```

If the collection is large, this can become expensive.

A hash-based `Set` is designed for fast membership checking:

```java
set.contains(50);
```

Average:

```text
O(1)
```

This makes `Set` useful for things such as:

* checking whether a username already exists,
* checking whether an email has already been registered,
* tracking unique users,
* checking whether an item has already been processed.

The notes use the idea of a website/email system as an example: if emails must be unique, a `Set` is a natural choice. 

---

# 3. Set

## What is a Set?

A `Set` is a collection that **does not allow duplicate elements**.

Example:

```java
Set<Integer> set = new HashSet<>();

set.add(2);
set.add(3);
set.add(2);
```

The final logical contents are:

```text
{2, 3}
```

The second `2` is not added as a new element.

The lecture represents this idea visually on page 1 with a collection such as:

```text
7   3   2
```

and emphasizes that duplicate values are not allowed. 

---

## `Set.add()`

```java
set.add(2);
```

Internally, for a `HashSet`, this is closely related to putting the element into an underlying `HashMap`.

Conceptually:

```java
set.add(2);
```

becomes something like:

```java
map.put(2, PRESENT);
```

where `PRESENT` is a dummy object.

This is one of the most important internal details of `HashSet`.

---

# 4. HashSet internally uses HashMap

This is a common interview question.

### Question

> How does `HashSet` work internally?

### Answer

**`HashSet` internally uses a `HashMap`.**

The Set element becomes the **key** of the internal map.

The value is a dummy object called:

```text
PRESENT
```

The lecture illustrates this on pages 4–5. 

Conceptually:

```text
HashSet

     add("Aditya")
          |
          ↓
   internal HashMap
          |
          ↓
"Aditya" → PRESENT
```

So:

```java
set.add("Aditya");
```

is conceptually similar to:

```java
map.put("Aditya", PRESENT);
```

where:

```java
private static final Object PRESENT = new Object();
```

The exact implementation details belong to Java's `HashSet` implementation, but the important concept is:

```text
HashSet
   ↓
HashMap
   ↓
key = Set element
value = PRESENT
```

---

# 5. Why does HashSet use HashMap?

Because a `HashMap` already provides the machinery required for:

* hashing,
* buckets,
* collision handling,
* duplicate-key detection,
* fast lookup.

A `Set` only needs to answer:

```text
"Does this element exist?"
```

It does not need an actual meaningful value.

So the implementation can simply use:

```text
element → PRESENT
```

For example:

```text
"Aditya" → PRESENT
"Rohit"  → PRESENT
"Rohan"  → PRESENT
```

The actual values do not matter.

---

# 6. Map

## What is a Map?

A `Map` stores data as:

```text
key → value
```

Example:

```java
Map<Integer, String> map = new HashMap<>();

map.put(101, "Aditya");
map.put(102, "Rohit");
map.put(103, "Rohan");
```

Conceptually:

```text
101 → Aditya
102 → Rohit
103 → Rohan
```

The lecture uses a roll-number/name example on pages 1 and 3. 

Here:

```text
Key   = Roll number
Value = Name
```

---

# 7. Duplicate keys are not allowed

A `Map` does not allow two entries with the same key.

For example:

```java
map.put(101, "Aditya");
map.put(101, "Rohit");
```

The key `101` already exists.

So the second `put()` does not create another independent key.

Instead, the value associated with `101` is replaced.

Conceptually:

```text
Before:

101 → Aditya


After:

101 → Rohit
```

So remember:

> **Map keys are unique. Values do not have to be unique.**

Example:

```text
101 → Aditya
102 → Aditya
103 → Rohit
```

This is completely valid.

---

# 8. Important Map methods

## `put(key, value)`

Adds or updates a mapping.

```java
map.put(101, "Aditya");
```

Meaning:

```text
101 → Aditya
```

---

## `get(key)`

Retrieves the value associated with a key.

```java
map.get(101);
```

Result:

```text
Aditya
```

The lecture illustrates this on pages 1 and 8. 

---

## `containsKey(key)`

Checks whether a key exists.

```java
map.containsKey(101);
```

Result:

```text
true
```

If the key doesn't exist:

```text
false
```

---

# 9. Set and Map: Important Difference

| Feature                        | Set            | Map                       |
| ------------------------------ | -------------- | ------------------------- |
| Stores                         | Elements       | Key-value pairs           |
| Duplicate elements             | No             | Keys cannot duplicate     |
| Main lookup                    | `contains()`   | `containsKey()` / `get()` |
| Example                        | `{10, 20, 30}` | `{101 → "Aditya"}`        |
| Hash-based average lookup      | `O(1)`         | `O(1)`                    |
| Part of `Collection` hierarchy | Yes            | No                        |

One particularly important point:

> **`Map` is not a subtype of `Collection`.**

The hierarchy shown on page 11 separates them:

```text
Collection
    |
   Set
    |
 HashSet
    |
LinkedHashSet


Map
 |
HashMap
 |
LinkedHashMap
```

and separately:

```text
Map
 |
HashMap
 |
LinkedHashMap

Map
 |
TreeMap
```

while:

```text
Set
 |
TreeSet
```

is used for the tree-based set. 

---

# 10. How does HashMap work internally?

This is the core of the lecture.

The basic idea is:

```text
Key
 ↓
hashCode()
 ↓
hash
 ↓
bucket index
 ↓
bucket
 ↓
stored node
```

The lecture explains this flow on pages 3, 5, 6 and 7. 

---

# 11. Buckets

Internally, a `HashMap` maintains an array-like structure.

Think of it as:

```text
Index

  0     1     2     3     4
┌─────┬─────┬─────┬─────┬─────┐
│     │     │     │     │     │
└─────┴─────┴─────┴─────┴─────┘
```

Each position is called a **bucket**.

A key is assigned to one of these buckets.

For example:

```text
HashMap
    ↓
┌─────┬─────┬─────┬─────┬─────┐
│  0  │  1  │  2  │  3  │  4  │
└─────┴─────┴─────┴─────┴─────┘
```

The bucket index is determined using the key's hash.

The lecture simplifies this as:

```text
index = hash % n
```

where:

* `hash` = hash value
* `n` = number of buckets

The page 5 diagram shows a five-bucket example. 

---

# 12. What is `hashCode()`?

Every Java object can provide a hash code through:

```java
hashCode()
```

For example:

```java
key.hashCode();
```

The hash value helps Java decide where the key should be stored.

Conceptually:

```text
"Aditya"
   ↓
hashCode()
   ↓
some hash value
   ↓
bucket index
```

The lecture specifically demonstrates this with:

```text
Aditya, Rohit
      ↓
  hashCode()
```

on page 3. 

---

# 13. Example of bucket calculation

Suppose:

```text
number of buckets = 5
```

and suppose a key produces:

```text
hash = 17
```

Using the simplified calculation from the lecture:

```text
index = hash % n
      = 17 % 5
      = 2
```

Therefore:

```text
key → bucket 2
```

Visualized:

```text
0      1      2      3      4
┌──────┬──────┬──────┬──────┬──────┐
│      │      │ key  │      │      │
└──────┴──────┴──────┴──────┴──────┘
              ↑
           bucket 2
```

The lecture uses the same bucket idea throughout pages 2–7. 

---

# 14. What happens when two keys go to the same bucket?

This is called a **collision**.

Example:

```text
Key A → bucket 2
Key B → bucket 2
```

Both keys want the same bucket.

That is a collision.

The lecture shows this situation on page 2:

```text
bucket 0
bucket 1
bucket 2
bucket 3

             ↓
          Node
             ↓
          Node
             ↓
          Node
```

---

# 15. Collision handling: Chaining

The lecture explains **chaining** using a linked list.

Instead of replacing the old element, Java keeps multiple nodes associated with the same bucket.

Conceptually:

```text
Bucket 2
   |
   ↓
┌──────────┐
│ Key A    │
└────┬─────┘
     ↓
┌──────────┐
│ Key B    │
└────┬─────┘
     ↓
┌──────────┐
│ Key C    │
└──────────┘
```

This is called:

> **Separate chaining**

The lecture contrasts this with **open addressing**, but the Java `HashMap` design discussed here uses chaining rather than open addressing. 

---

# 16. Internal Node of HashMap

The notes show a simplified node structure:

```java
class Node<K, V> {
    K key;
    V value;
    int hash;
    Node<K, V> next;
}
```

This gives us a very useful mental model.

Each node contains:

```text
key
value
hash
next
```

For example:

```text
┌────────────────────┐
│ hash               │
│ key   = 101        │
│ value = "Aditya"   │
│ next  ─────────────┼────→ next node
└────────────────────┘
```

The `next` pointer allows multiple entries to exist in the same bucket.

This node structure is illustrated on page 5. 

---

# 17. What happens during `put()`?

Suppose we execute:

```java
map.put(101, "Aditya");
```

Conceptually, the process is:

```text
map.put(101, "Aditya")
          |
          ↓
     calculate hash
          |
          ↓
   find bucket index
          |
          ↓
   check bucket
          |
          ↓
 ┌────────┴─────────┐
 │                  │
Empty            Not empty
 │                  │
 ↓                  ↓
Create node      Search nodes
```

The lecture breaks this into cases on pages 5–7. 

---

# 18. Case 1: Bucket is empty

Suppose the calculated bucket is `0`.

```text
0     1     2     3     4
┌─────┬─────┬─────┬─────┬─────┐
│     │     │     │     │     │
└─────┴─────┴─────┴─────┴─────┘
  ↑
empty bucket
```

Then Java creates a new node:

```text
bucket[0] = new Node(key, value, hash, null)
```

Conceptually:

```text
bucket[0]
   |
   ↓
┌──────────────────┐
│ key = Aditya     │
│ value = PRESENT  │
│ hash             │
│ next = null      │
└──────────────────┘
```

The lecture shows this as **Case 1: Bucket Empty** on page 6. 

---

# 19. Case 2: Bucket is not empty

Suppose another key maps to the same bucket.

Now:

```text
bucket[0]
   |
   ↓
Node 1
   |
   ↓
Node 2
   |
   ↓
Node 3
```

Java traverses the nodes.

Conceptually:

```java
Node temp = head;

while (temp != null) {
    ...
    temp = temp.next;
}
```

The purpose is to determine:

1. Does the key already exist?
2. If not, where should the new node be added?

---

# 20. How does Java detect duplicate keys?

This is a very important interview concept.

Java does not simply check:

```text
hash == hash
```

It also needs to compare the actual keys.

The lecture shows the condition conceptually as:

```text
node.hash == hash
AND
node.key.equals(key)
```

on pages 6–7. 

So duplicate detection is based on:

```text
hashCode()
      +
equals()
```

---

# 21. Why are both `hashCode()` and `equals()` important?

Suppose:

```java
map.put(new Student(101), "Aditya");
```

Later:

```java
map.get(new Student(101));
```

These may be two different Java objects.

Java needs a way to determine whether they represent the same logical key.

The normal contract is:

> If two objects are equal according to `equals()`, they must return the same `hashCode()`.

The lecture demonstrates this idea with keys such as:

```text
Aditya → 2023
Rohit  → 2023
```

and shows the relationship between `equals()` and `hashCode()` on page 7. 

---

# 22. Collision does NOT necessarily mean duplicate

This distinction is extremely important.

Two different keys can have the same hash.

Example:

```text
Key A
hash = 100

Key B
hash = 100
```

They collide.

But they may still be different:

```java
A.equals(B) == false
```

Therefore both can exist in the map.

Conceptually:

```text
same hash
   ↓
same bucket
   ↓
check equals()
   ↓
false
   ↓
different keys → store both
```

If:

```java
A.equals(B) == true
```

then they represent the same key and the existing mapping is updated.

---

# 23. `HashSet.contains()` internally

Consider:

```java
set.contains("Aditya");
```

Because `HashSet` uses a `HashMap`, this is conceptually:

```java
map.containsKey("Aditya");
```

The lecture explicitly shows:

```text
set.contains(Aditya)
       ↓
contains(key)
       ↓
map.containsKey(key)
```

on page 7. 

So:

```text
HashSet.contains(x)
        ↓
HashMap.containsKey(x)
```

This explains why `HashSet` can provide fast lookup.

---

# 24. Why is HashMap usually `O(1)`?

The goal of hashing is to directly identify the bucket where a key should be.

Instead of searching every element:

```text
Node 1
Node 2
Node 3
...
Node n
```

we first calculate:

```text
hash → bucket
```

So the search is narrowed down immediately.

Ideal case:

```text
Key
 ↓
hash
 ↓
bucket
 ↓
node
```

This is approximately:

```text
O(1)
```

on average.

However, collisions can create multiple nodes in the same bucket.

Therefore, the performance is not literally guaranteed to be `O(1)` for every situation.

---

# 25. Load Factor

Another major concept is the **load factor**.

Load factor tells us how full the hash table is allowed to become before resizing.

The lecture represents it as:

```text
load factor = elements / capacity
```

For example:

```text
elements = 6
capacity = 5
```

gives:

```text
6 / 5 = 1.2
```

The lecture also gives:

```text
4 / 5 = 0.8
```

as an example. 

---

# 26. Java's default load factor

The lecture notes the common Java `HashMap` configuration:

```text
capacity = 16
load factor = 0.75
```

Therefore the threshold is:

```text
16 × 0.75 = 12
```

So the lecture summarizes this as:

```text
elements < 12
```

before the resize threshold is reached. 

A useful mental model:

```text
Capacity = 16

0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15
|  |  |  |  |  |  |  |  |  |  |  |  |  |  |  |

                 threshold
                     ↓
                    12
```

---

# 27. Why does HashMap resize?

If too many elements are stored relative to the number of buckets, collisions become more likely.

That means buckets can become longer:

```text
bucket
  ↓
Node
  ↓
Node
  ↓
Node
  ↓
Node
```

Longer chains mean slower lookup.

Therefore, Java increases the table capacity.

The lecture describes this as:

```text
resize
   ↓
double the array size
   ↓
rehash elements
```

The resize mechanism is discussed on pages 8–9. 

---

# 28. Example of resizing

Suppose:

```text
capacity = 16
load factor = 0.75
```

Threshold:

```text
16 × 0.75 = 12
```

Once the relevant threshold is crossed, the table grows.

Conceptually:

```text
Before:

capacity = 16


After:

capacity = 32
```

The entries then need to be redistributed according to the new table size.

This is why resizing has a cost.

---

# 29. Important performance idea: resizing is expensive

Normal insertion is usually:

```text
O(1) average
```

But a resize requires moving/reorganizing entries.

Therefore, a resize operation is more expensive.

This is why load factor and capacity matter.

The overall design is a trade-off:

```text
More buckets
     ↓
fewer collisions
     ↓
better lookup
     ↓
more memory
```

while:

```text
Fewer buckets
     ↓
more collisions
     ↓
potentially slower lookup
     ↓
less memory
```

---

# 30. Treeification: Java 8+

A major optimization was introduced in Java 8.

If a bucket's linked-list chain becomes sufficiently large, Java can convert that bucket's structure into a **Red-Black Tree**.

The lecture uses:

```text
bucket size > 8
       ↓
Red-Black Tree
```

as the key threshold discussed in the lecture. 

---

# 31. Why convert the linked list into a tree?

A linked list search is:

```text
O(n)
```

in the worst case.

For example:

```text
Bucket
  ↓
A → B → C → D → E → F → G → H
```

To find `H`, we may need to traverse many nodes.

But a balanced search tree can search in:

```text
O(log n)
```

So:

```text
Long linked list
       ↓
Red-Black Tree
       ↓
better worst-case lookup
```

The lecture compares:

```text
Linked List → O(n)

Red-Black Tree → O(log n)
```

on page 9. 

---

# 32. Red-Black Tree

A **Red-Black Tree** is a self-balancing Binary Search Tree.

"Self-balancing" means it prevents the tree from becoming excessively skewed.

A simplified tree:

```text
          50
        /    \
      30      70
     /  \    /  \
   20   40  60   80
```

The lecture uses a similar example on page 10. 

---

# 33. Why balance matters

Consider a normal BST.

If values are inserted in sorted order:

```text
10
  \
   20
     \
      30
        \
         40
           \
            50
```

This starts looking like a linked list.

Searching can become:

```text
O(n)
```

A self-balancing tree keeps the height controlled.

Therefore:

```text
Balanced tree
     ↓
height ≈ log n
     ↓
search ≈ O(log n)
```

The lecture specifically identifies the Red-Black Tree as the self-balancing structure used for treeification. 

---

# 34. HashMap internal flow — complete picture

Put everything together:

```text
                 map.put(key, value)
                          |
                          ↓
                     hashCode()
                          |
                          ↓
                    calculate hash
                          |
                          ↓
                     bucket index
                          |
                          ↓
                  ┌──── bucket ────┐
                  │                │
                  ↓                ↓
               empty            non-empty
                  │                │
                  ↓                ↓
             create node      compare nodes
                                   |
                              hash + equals
                                   |
                         ┌─────────┴─────────┐
                         ↓                   ↓
                      same key          different key
                         ↓                   ↓
                    update value       collision chain
                                             |
                                             ↓
                                     linked list / tree
```

This combines the internal diagrams shown across pages 5–9. 

---

# 35. LinkedHashMap and LinkedHashSet

Hash-based collections do not primarily exist to maintain insertion order.

When we need to preserve insertion order, Java provides:

* `LinkedHashMap`
* `LinkedHashSet`

The lecture explains that these maintain an additional **doubly linked list**. 

---

# 36. LinkedHashMap

A `LinkedHashMap` is based on `HashMap`, but adds links between entries.

Conceptually:

```text
Hash table:

bucket → Node
          |
          ↓
        Node
```

And additionally:

```text
Node A ⇄ Node B ⇄ Node C
```

So each node has extra pointers such as:

```text
before
after
```

The page 9 diagram shows a node containing:

```text
key
value
hash
next
before
after
```



---

# 37. Why does LinkedHashMap need before/after?

Suppose we insert:

```java
map.put(1, "Aditya");
map.put(2, "Rohit");
map.put(3, "Rohan");
```

The insertion order is:

```text
Aditya → Rohit → Rohan
```

The doubly linked list helps maintain this order.

Conceptually:

```text
head
 ↓
Aditya ⇄ Rohit ⇄ Rohan
                         ↑
                        tail
```

So when iterating, the insertion order can be preserved.

---

# 38. LinkedHashSet

`LinkedHashSet` provides Set behavior while preserving insertion order.

Example:

```java
Set<Integer> set = new LinkedHashSet<>();

set.add(30);
set.add(10);
set.add(20);
```

Iteration preserves:

```text
30
10
20
```

instead of treating the set as an unordered hash structure.

The lecture groups `LinkedHashSet` and `LinkedHashMap` together because both use additional links to maintain ordering. 

---

# 39. HashMap vs LinkedHashMap

| Feature                   | HashMap      | LinkedHashMap |
| ------------------------- | ------------ | ------------- |
| Hashing                   | Yes          | Yes           |
| Key-value pairs           | Yes          | Yes           |
| Average lookup            | `O(1)`       | `O(1)`        |
| Maintains insertion order | No guarantee | Yes           |
| Additional links          | No           | Yes           |
| Memory                    | Lower        | Higher        |

The extra linked-list structure requires additional memory.

So this is another trade-off:

```text
HashMap
→ simpler
→ less overhead

LinkedHashMap
→ maintains order
→ extra memory/pointers
```

---

# 40. TreeMap and TreeSet

Hashing is not always the right solution.

Sometimes we want:

* sorted data,
* minimum/maximum values,
* range queries,
* ordered traversal.

For this, Java provides:

* `TreeMap`
* `TreeSet`

The lecture explains these using a **self-balancing BST / Red-Black Tree**. 

---

# 41. TreeSet

A `TreeSet` stores unique elements in sorted order.

Example:

```java
Set<Integer> set = new TreeSet<>();

set.add(50);
set.add(20);
set.add(70);
set.add(10);
set.add(40);
```

The values are maintained in sorted order:

```text
10
20
40
50
70
```

The lecture's page 10 example shows a tree containing:

```text
50
├── 30
│   ├── 20
│   └── 40
└── 70
    ├── 60
    └── 80
```

and then shows the sorted order:

```text
50, 60, 70, 80, 90
```

as part of its TreeSet discussion. 

---

# 42. TreeMap

`TreeMap` is the Map equivalent.

It stores:

```text
key → value
```

but maintains keys in sorted order.

Conceptually:

```text
        50
       /  \
     30    70
```

The tree is organized according to the keys.

So:

```java
TreeMap<Integer, String>
```

keeps its integer keys sorted.

---

# 43. BST rule

A Binary Search Tree follows:

```text
smaller values → left

larger values → right
```

For example:

```text
             50
           /    \
         30      70
        /  \    /  \
      20   40  60   80
```

Here:

```text
20 < 30 < 50
40 < 50
60 > 50
80 > 50
```

The lecture explicitly writes:

```text
smaller value → left
higher value  → right
```

on page 10. 

---

# 44. Why use TreeSet / TreeMap?

The major advantage is **ordering**.

With a hash-based collection:

```text
HashMap / HashSet
```

the main goal is fast lookup.

With tree-based collections:

```text
TreeMap / TreeSet
```

the main goal includes maintaining sorted order.

This makes tree-based collections useful for:

* finding smallest value,
* finding largest value,
* range queries,
* ordered iteration,
* sorted keys/elements.

The lecture specifically calls out **key sorting, largest/smallest values, and range queries**. 

---

# 45. Performance: Hash vs Tree

| Operation / property |             Hash-based |     Tree-based |
| -------------------- | ---------------------: | -------------: |
| Average lookup       |                 `O(1)` |     `O(log n)` |
| Ordering             |         Not guaranteed |         Sorted |
| Main structure       |             Hash table | Red-Black Tree |
| Collision handling   | Chaining/treeification | Not applicable |
| Range queries        |  Not the main strength |         Strong |
| Min/max              |  Not the main strength |      Efficient |

The key trade-off:

```text
HashMap / HashSet
→ faster average lookup
→ no sorted-order guarantee


TreeMap / TreeSet
→ O(log n)
→ sorted structure
→ useful for range/order operations
```

---

# 46. TreeMap/TreeSet and `null`

This is an important difference.

The lecture explains that tree-based collections need to compare keys.

For example:

```text
key1 < key2
```

or:

```text
key1 > key2
```

But `null` cannot participate in normal key comparison using `compareTo()`.

Therefore, the lecture notes that `TreeMap` and `TreeSet` do not allow `null` keys in this context, because comparison can result in a `NullPointerException`. 

---

# 47. HashMap and null

The lecture notes that `HashMap` can contain:

* **one `null` key**
* **multiple `null` values**

Example:

```java
Map<Integer, String> map = new HashMap<>();

map.put(null, "Aditya");
map.put(101, null);
map.put(102, null);
```

Conceptually:

```text
null → Aditya
101  → null
102  → null
```

The lecture explains that the `null` key is handled specially and is associated with the `0` bucket/index. 

---

# 48. HashSet and null

Because `HashSet` internally uses `HashMap`, its null behavior follows the underlying map's ability to store a null key.

Therefore, a `HashSet` can contain a single `null` element.

Example:

```java
Set<String> set = new HashSet<>();

set.add(null);
set.add(null);
```

Only one logical `null` element exists because a Set cannot contain duplicates.

---

# 49. Null comparison: the mental model

Remember:

```text
HashMap / HashSet
        ↓
hash-based
        ↓
null can be handled specially
```

while:

```text
TreeMap / TreeSet
        ↓
comparison-based
        ↓
null key cannot normally be compared
```

This difference comes directly from the internal data structures.

---

# 50. Complete collection hierarchy

The lecture's final page provides a useful overall picture.

## Set side

```text
Collection
    |
   Set
    |
    +----------------+
    |                |
HashSet           TreeSet
    |
LinkedHashSet
```

## Map side

```text
Map
 |
 +-------------------+
 |                   |
HashMap            TreeMap
 |
LinkedHashMap
```

The important relationship is:

```text
Set → Collection hierarchy

Map → separate hierarchy
```

`Map` does **not** extend `Collection`. 

---

# 51. HashSet → HashMap relationship

Keep this diagram in your head:

```text
HashSet
   |
   ↓
HashMap
   |
   ↓
key = Set element
value = PRESENT
```

Example:

```java
set.add("Aditya");
```

internally conceptually:

```java
map.put("Aditya", PRESENT);
```

This is one of the most useful facts for Java interviews.

---

# 52. HashMap complete internal structure

Think of a `HashMap` as:

```text
                 HashMap
                    |
                    ↓
              bucket array
                    |
       ┌────────────┼────────────┐
       ↓            ↓            ↓
    bucket 0      bucket 1     bucket 2
       |                         |
       ↓                         ↓
     Node                       Node
       |                         |
       ↓                         ↓
     Node                       Node
       |
       ↓
     Node
```

Each node conceptually contains:

```text
┌─────────────────────┐
│ hash                │
│ key                 │
│ value               │
│ next                │
└─────────────────────┘
```

If the chain becomes sufficiently large:

```text
Linked List
     ↓
Treeification
     ↓
Red-Black Tree
```



---

# 53. Full `put()` mental model

For:

```java
map.put(key, value);
```

think:

### Step 1 — Hash

```text
key.hashCode()
```

### Step 2 — Find bucket

```text
hash → bucket index
```

### Step 3 — Check bucket

```text
Is bucket empty?
```

### Step 4A — Empty

Create a new node.

```text
bucket
  ↓
new Node
```

### Step 4B — Not empty

Traverse the existing nodes.

### Step 5 — Compare

Check the hash and key equality.

```text
hash matches?
     +
key.equals()?
```

### Step 6A — Same key

Update the value.

```text
existing key → new value
```

### Step 6B — Different key

Add another node to the collision chain.

### Step 7 — Large bucket

If the chain becomes large enough, treeification can occur.

### Step 8 — High load

If the map reaches its resize threshold:

```text
resize
   ↓
larger table
   ↓
redistribute entries
```

This is the complete conceptual flow represented across the lecture's internal diagrams. 

---

# 54. HashCode and equals contract

For objects used as keys, remember:

```text
equals()
+
hashCode()
```

They must work consistently.

Important rule:

> If `a.equals(b)` is `true`, then `a.hashCode()` and `b.hashCode()` must be the same.

Why?

Because HashMap first uses hashing to find the appropriate bucket.

Then it uses equality to determine whether the key is actually the same.

Conceptually:

```text
        key
         |
         ↓
    hashCode()
         |
         ↓
      bucket
         |
         ↓
 compare candidate keys
         |
         ↓
     equals()
```

This is why overriding `equals()` without correctly overriding `hashCode()` can cause incorrect behavior in hash-based collections.

---

# 55. Example: custom object as a key

Suppose:

```java
class Student {
    int rollNo;
    String name;
}
```

and we use:

```java
Map<Student, String> map = new HashMap<>();
```

The `Student` object becomes a key.

HashMap needs a consistent definition of:

```text
Are these two Student objects the same key?
```

That is why `equals()` and `hashCode()` are important.

For example:

```text
Student(101, "Aditya")
Student(101, "Aditya")
```

could be considered logically equal if the class defines equality that way.

Then their hash codes must also match.

---

# 56. Hash collision vs duplicate key

These are different concepts.

### Collision

Two different keys produce the same bucket.

```text
Key A → bucket 2
Key B → bucket 2

A != B
```

Both can exist.

### Duplicate key

Two objects are considered the same key.

```text
A.equals(B) == true
```

The new value replaces/updates the existing mapping.

Remember:

```text
Collision ≠ duplicate
```

A collision only means:

> "These keys ended up in the same bucket."

A duplicate means:

> "These keys represent the same logical key."

---

# 57. Which collection should you choose?

A useful decision guide:

### Need unique elements + fast average lookup?

Use:

```text
HashSet
```

Example:

```text
unique email IDs
unique user IDs
visited IDs
```

---

### Need key-value data + fast average lookup?

Use:

```text
HashMap
```

Example:

```text
roll number → student name
user ID → user object
product ID → product
```

---

### Need insertion order?

Use:

```text
LinkedHashSet
LinkedHashMap
```

Example:

```text
process items in the same order they were inserted
```

---

### Need sorted unique elements?

Use:

```text
TreeSet
```

Example:

```text
sorted scores
sorted IDs
```

---

### Need sorted keys with values?

Use:

```text
TreeMap
```

Example:

```text
student roll number → student
```

where the roll numbers should remain sorted.

---

# 58. Comparison table

| Collection      | Stores          | Ordering            | Internal idea            | Average lookup |
| --------------- | --------------- | ------------------- | ------------------------ | -------------: |
| `HashSet`       | Unique elements | No guaranteed order | `HashMap`                |         `O(1)` |
| `LinkedHashSet` | Unique elements | Insertion order     | Hashing + linked list    |         `O(1)` |
| `TreeSet`       | Unique elements | Sorted order        | Red-Black Tree           |     `O(log n)` |
| `HashMap`       | Key-value pairs | No guaranteed order | Hash table               |         `O(1)` |
| `LinkedHashMap` | Key-value pairs | Insertion order     | Hash table + linked list |         `O(1)` |
| `TreeMap`       | Key-value pairs | Sorted by key       | Red-Black Tree           |     `O(log n)` |

---

# 59. Important trade-offs

## HashMap / HashSet

### Advantages

* Very fast average lookup.
* Good for membership checking.
* Good for key-value lookup.
* Efficient for large datasets.

### Costs

* No sorted-order behavior.
* Hashing is required.
* Collisions must be handled.
* Resizing requires additional work.
* Memory is needed for the hash table and nodes.

---

## LinkedHashMap / LinkedHashSet

### Advantages

* Hash-based performance.
* Maintains insertion order.

### Costs

* Extra memory.
* Extra `before` / `after` links.

---

## TreeMap / TreeSet

### Advantages

* Sorted data.
* Predictable ordered traversal.
* Useful for min/max.
* Useful for range-based operations.
* `O(log n)` operations.

### Costs

* Slower than average hash lookup.
* Requires comparison between keys/elements.
* Tree structure is more complex.

---

# 60. The most important diagrams to remember

## Hashing

```text
Key
 ↓
hashCode()
 ↓
hash
 ↓
bucket index
 ↓
bucket
```

---

## Collision

```text
Bucket
  |
  ↓
Node → Node → Node
```

---

## Treeification

```text
Long chain
    ↓
bucket becomes large
    ↓
Red-Black Tree
    ↓
O(log n)
```

---

## HashSet

```text
HashSet
   ↓
HashMap
   ↓
element → PRESENT
```

---

## LinkedHashMap

```text
Hash buckets
     +
doubly linked list
     ↓
insertion order
```

---

## TreeMap / TreeSet

```text
Red-Black Tree
      ↓
sorted order
      ↓
O(log n)
```

---

# 61. Common mistakes / gotchas

### 1. Thinking Set allows duplicates

Wrong:

```text
Set = List but faster
```

Correct:

```text
Set = unique elements
```

---

### 2. Thinking Map is a Collection

It is not.

```text
Collection
   |
  Set

Map
```

They are separate interfaces.

---

### 3. Thinking Map prevents duplicate values

It prevents duplicate **keys**, not values.

Valid:

```text
101 → Aditya
102 → Aditya
```

---

### 4. Thinking collision means duplicate

Not necessarily.

```text
same bucket ≠ same key
```

Java still checks key equality.

---

### 5. Forgetting `equals()` and `hashCode()`

Custom objects used as hash keys need a correct equality/hash-code contract.

---

### 6. Thinking HashMap is always exactly `O(1)`

`O(1)` is the **average expected lookup complexity**.

Collisions and other internal behavior can affect performance.

The lecture specifically explains how large collision chains are optimized using treeification. 

---

### 7. Using HashMap when sorted data is required

If you need:

```text
sorted keys
range queries
smallest/largest
```

consider:

```text
TreeMap
```

rather than `HashMap`.

---

### 8. Forgetting the cost of ordering

`LinkedHashMap` and `LinkedHashSet` preserve order by maintaining extra links.

That means additional memory.

---

### 9. Forgetting null behavior

From the lecture:

```text
HashMap
→ one null key
→ multiple null values


TreeMap
→ null key cannot normally be compared
→ may cause NullPointerException
```



---

# 62. Interview-ready questions

### Q1. What is a Set?

A collection that does not allow duplicate elements.

### Q2. What is a Map?

A collection-like data structure that stores key-value mappings where keys are unique.

### Q3. Is Map part of Collection?

No. `Map` is a separate interface hierarchy.

### Q4. How does HashSet work internally?

`HashSet` internally uses a `HashMap`. Set elements become map keys and a dummy `PRESENT` object is used as the value.

### Q5. What is a collision?

When multiple keys map to the same bucket.

### Q6. How does HashMap handle collisions?

The lecture describes chaining using linked nodes, with treeification for sufficiently large buckets.

### Q7. Why are `hashCode()` and `equals()` both required?

Hashing identifies the bucket, while equality determines whether the key is actually the same key.

### Q8. What is the average complexity of HashMap lookup?

Approximately:

```text
O(1)
```

### Q9. What happens when a bucket becomes too large?

From Java 8+, the lecture describes conversion from a linked-list structure to a Red-Black Tree, improving the search behavior toward `O(log n)`.

### Q10. What is the load factor?

It controls when a hash table should resize.

```text
load factor ≈ elements / capacity
```

### Q11. What is the common default HashMap load factor?

```text
0.75
```

### Q12. What happens when HashMap resizes?

The table grows, commonly by doubling its capacity, and entries are redistributed.

### Q13. HashMap vs TreeMap?

```text
HashMap
→ hashing
→ average O(1)
→ no sorted-order guarantee

TreeMap
→ Red-Black Tree
→ O(log n)
→ sorted keys
```

### Q14. HashSet vs TreeSet?

```text
HashSet
→ hashing
→ average O(1)
→ no sorted order

TreeSet
→ Red-Black Tree
→ O(log n)
→ sorted elements
```

### Q15. Why use LinkedHashMap?

To combine hash-based lookup with predictable insertion-order iteration.

---

# 63. Quick revision sheet

```text
SET
│
├── Unique elements
├── contains()
├── HashSet → average O(1)
├── LinkedHashSet → insertion order
└── TreeSet → sorted order, O(log n)
```

```text
MAP
│
├── key → value
├── keys are unique
├── put()
├── get()
├── containsKey()
├── HashMap → average O(1)
├── LinkedHashMap → insertion order
└── TreeMap → sorted keys, O(log n)
```

```text
HASHMAP INTERNALS

key
 ↓
hashCode()
 ↓
bucket index
 ↓
bucket
 ↓
Node
 ↓
next → next → next
```

```text
COLLISION

different keys
      ↓
same bucket
      ↓
chaining
      ↓
large chain
      ↓
Red-Black Tree
```

```text
HASHSET

HashSet
   ↓
HashMap
   ↓
element → PRESENT
```

```text
LOAD FACTOR

elements / capacity
        ↓
threshold reached
        ↓
resize
        ↓
larger table
```

---

# 64. Final mental model

If you remember only one picture, remember this:

```text
                       Java Collections
                              |
             ┌────────────────┴────────────────┐
             |                                 |
         Collection                           Map
             |                                 |
            Set                           ┌─────┴─────┐
             |                            |           |
      ┌──────┼──────┐                 HashMap     TreeMap
      |      |      |                    |
  HashSet  Linked   TreeSet          LinkedHashMap
             |
       LinkedHashSet
```

And for the internals:

```text
                 HashMap
                    |
                    ↓
               Bucket Array
                    |
             ┌──────┴──────┐
             ↓             ↓
        empty bucket    collision
             |             |
          new Node      linked nodes
                           |
                    chain becomes large
                           |
                           ↓
                     Red-Black Tree
```

The core decision is therefore:

```text
Need unique values?
        ↓
       Set

Need key → value?
        ↓
       Map


Need fast average lookup?
        ↓
    HashSet / HashMap


Need insertion order?
        ↓
LinkedHashSet / LinkedHashMap


Need sorted data / range operations?
        ↓
 TreeSet / TreeMap
```

The lecture's 11-page notes build exactly around these ideas: Set/Map fundamentals, bucket-based hashing, collision handling, `HashSet`'s internal `HashMap`, load factor and resizing, treeification, linked ordering, tree-based collections, null handling, and the final collection hierarchy. 

---

# Key Takeaways

* **Set = unique elements.**

* **Map = unique keys mapped to values.**

* `Map` is separate from the `Collection` hierarchy.

* `HashSet` internally uses a `HashMap`.

* `HashSet` conceptually stores:

  ```text
  element → PRESENT
  ```

* `HashMap` uses a bucket-based hashing structure.

* `hashCode()` helps locate the bucket.

* Collisions occur when multiple keys map to the same bucket.

* The lecture describes collision handling through **chaining**.

* `equals()` is used along with the hash to identify an existing key.

* Hash-based lookup is **`O(1)` on average**.

* Load factor determines when resizing occurs.

* The lecture uses the common `0.75` load factor and capacity `16`, giving a threshold of `12`.

* Java 8+ can treeify sufficiently large collision chains into **Red-Black Trees**.

* Tree-based lookup is **`O(log n)`**.

* `LinkedHashMap` and `LinkedHashSet` maintain insertion order using extra linked-list connections.

* `TreeMap` and `TreeSet` maintain sorted order using a self-balancing tree.

* Hash-based collections prioritize fast average lookup.

* Tree-based collections prioritize sorted data and ordered/range operations.

* `HashMap` can have one `null` key and multiple `null` values.

* Tree-based keys need to be comparable, so `null` keys are problematic.

* Know these three families:

```text
Hash*       → fast average lookup
LinkedHash* → fast lookup + insertion order
Tree*       → sorted order + O(log n)
```

---

# Minimal Self-Test

1. Why does `HashSet` internally use `HashMap`?
2. What exactly is stored as the value inside the internal `HashMap` of a `HashSet`?
3. What is a hash collision?
4. How does Java handle collisions in the lecture's model?
5. Why are `hashCode()` and `equals()` both important?
6. Can two different keys have the same hash?
7. What happens when the same key is inserted into a `HashMap` again?
8. What is the purpose of the load factor?
9. Why does a `HashMap` resize?
10. What happens to a large collision chain from Java 8 onward?
11. Why is a Red-Black Tree better than a long linked list?
12. Why does `LinkedHashMap` need `before` and `after` references?
13. When would you choose `TreeMap` over `HashMap`?
14. Why is `TreeSet` slower than `HashSet` in typical lookup complexity?
15. Is `Map` a child interface of `Collection`?
16. What is the difference between a collision and a duplicate key?
17. How many `null` keys can a `HashMap` contain?
18. Why is `null` problematic for a `TreeMap` key?
19. What is the difference between `HashSet`, `LinkedHashSet`, and `TreeSet`?
20. What is the difference between `HashMap`, `LinkedHashMap`, and `TreeMap`?
