# Java Collections Framework — Set & Map Interfaces, Methods, and Specialized Implementations

## One-sentence summary

**`Set` is used for unique elements, `Map` is used for key-value mappings, and Java provides specialized implementations such as `TreeSet`, `LinkedHashMap`, `WeakHashMap`, `IdentityHashMap`, `EnumMap`, and `ConcurrentHashMap` for different requirements.**

---

# 1. Set Interface

## What is a Set?

A `Set` is a collection that stores **unique elements**.

```java
Set<Integer> set = new HashSet<>();

set.add(10);
set.add(20);
set.add(10);
```

The result contains:

```text
10, 20
```

The second `10` is ignored because duplicates are not allowed.

---

## Set hierarchy

The lecture's diagram on page 1 shows the hierarchy:

```text
Iterable
   |
Collection
   |
Set
   |
   +------------------+
   |                  |
HashSet           SortedSet
   |                  |
LinkedHashSet    NavigableSet
                      |
                   TreeSet
```

So:

* `Set` extends `Collection`.
* `Collection` extends `Iterable`.
* `HashSet` directly implements `Set`.
* `LinkedHashSet` extends `HashSet`.
* `TreeSet` implements `NavigableSet`.
* `NavigableSet` extends `SortedSet`.
* `SortedSet` extends `Set`. 

### Important hierarchy

```text
Iterable
   ↓
Collection
   ↓
Set
   ↓
SortedSet
   ↓
NavigableSet
   ↓
TreeSet
```

`HashSet` and `LinkedHashSet` are on the other branch.

---

# 2. Does Set introduce new methods?

An important point from the lecture:

> **The `Set` interface itself does not introduce a separate group of basic collection methods.**

It inherits the methods from `Collection`.

Common methods include:

```java
add()
remove()
contains()
size()
isEmpty()
```

Example:

```java
Set<String> names = new HashSet<>();

names.add("Aditya");

names.contains("Aditya");  // true

names.remove("Aditya");

names.size();              // 0

names.isEmpty();           // true
```

So when learning `Set`, you should already know the important `Collection` methods.

---

# 3. HashSet Constructors

The lecture discusses four common constructor forms for `HashSet`.

## 3.1 Default constructor

```java
HashSet<Integer> set = new HashSet<>();
```

Creates an empty `HashSet`.

---

## 3.2 Initial capacity

```java
HashSet<Integer> set = new HashSet<>(20);
```

The number specifies the initial capacity.

Think:

```text
HashSet
   ↓
initial capacity = 20
```

Capacity and actual number of elements are different things.

---

## 3.3 Initial capacity + load factor

```java
HashSet<Integer> set =
    new HashSet<>(20, 0.75f);
```

Here:

```text
20   → initial capacity
0.75 → load factor
```

The lecture identifies `0.75` as the common/default load factor.

The load factor controls when the underlying hash table should resize.

---

## 3.4 Constructor using another Collection

You can create a `HashSet` from an existing collection:

```java
List<Integer> list = List.of(10, 20, 10, 30);

HashSet<Integer> set = new HashSet<>(list);
```

The resulting set contains only unique values:

```text
10, 20, 30
```

This is useful for removing duplicates from another collection.

---

# 4. LinkedHashSet

`LinkedHashSet` is a specialized form of `HashSet`.

Its main benefit is:

> **It maintains insertion order.**

Example:

```java
Set<Integer> set = new LinkedHashSet<>();

set.add(30);
set.add(10);
set.add(20);
```

Iteration gives:

```text
30
10
20
```

The order in which elements were inserted is preserved.

Conceptually:

```text
Hashing
   +
Linked structure
   ↓
Fast lookup + insertion order
```

---

# 5. TreeSet

`TreeSet` is different from `HashSet`.

It maintains elements in **sorted order**.

The lecture describes it as being based on a **self-balancing Binary Search Tree (BST)**.

Conceptually:

```text
          50
        /    \
      30      70
     /  \    /  \
   20   40  60   80
```

The values are maintained according to their natural ordering or a supplied comparator.

For example:

```java
TreeSet<Integer> set = new TreeSet<>();

set.add(50);
set.add(20);
set.add(70);
set.add(10);
set.add(40);
```

Iteration produces:

```text
10, 20, 40, 50, 70
```

---

# 6. Why TreeSet?

Use `TreeSet` when you need:

* unique elements,
* sorted order,
* finding smallest/largest elements,
* navigation around a particular value,
* range queries.

The important difference is:

```text
HashSet
→ uniqueness + hash-based lookup

TreeSet
→ uniqueness + sorted structure
```

---

# 7. TreeSet Navigation Methods

`TreeSet` provides methods that are especially useful for finding elements relative to another element.

The lecture's page 1 specifically lists:

```text
first()
last()

lower()
higher()
floor()
ceiling()

pollFirst()
pollLast()

descendingSet()
descendingIterator()

headSet()
tailSet()
subSet()
```

These methods come from the sorted/navigable interfaces.

---

# 8. `first()` and `last()`

## `first()`

Returns the smallest element.

```java
TreeSet<Integer> set = new TreeSet<>();

set.add(10);
set.add(30);
set.add(20);

set.first();
```

Result:

```text
10
```

---

## `last()`

Returns the largest element.

```java
set.last();
```

Result:

```text
30
```

Mental model:

```text
10   20   30
↑         ↑
first     last
```

---

# 9. `lower()` and `higher()`

Suppose:

```java
TreeSet<Integer> set =
    new TreeSet<>(List.of(10, 20, 30, 40, 50));
```

### `lower(30)`

Returns the greatest element **strictly smaller** than `30`.

```java
set.lower(30);
```

Result:

```text
20
```

### `higher(30)`

Returns the smallest element **strictly greater** than `30`.

```java
set.higher(30);
```

Result:

```text
40
```

Remember:

```text
lower  → strictly <
higher → strictly >
```

---

# 10. `floor()` and `ceiling()`

These are similar to `lower()` and `higher()`, but they can include the requested value itself.

For:

```text
10, 20, 30, 40, 50
```

### `floor(30)`

Returns the greatest element **less than or equal to** `30`.

```java
set.floor(30);
```

Result:

```text
30
```

### `ceiling(30)`

Returns the smallest element **greater than or equal to** `30`.

```java
set.ceiling(30);
```

Result:

```text
30
```

If `30` did not exist:

```text
10, 20, 40, 50
```

then:

```text
floor(30)   → 20
ceiling(30) → 40
```

### Easy memory trick

```text
lower   → <
floor   → <=

higher  → >
ceiling → >=
```

---

# 11. `pollFirst()` and `pollLast()`

These methods retrieve and remove the first/last element.

```java
set.pollFirst();
```

means:

```text
get smallest element
+
remove it
```

Similarly:

```java
set.pollLast();
```

means:

```text
get largest element
+
remove it
```

Example:

```text
Before:

10, 20, 30, 40

pollFirst()
    ↓
returns 10

After:

20, 30, 40
```

---

# 12. Descending Views

`NavigableSet` also provides:

```java
descendingSet()
descendingIterator()
```

These allow you to work with the set in descending order.

Example:

```java
TreeSet<Integer> set =
    new TreeSet<>(List.of(10, 20, 30, 40));
```

Normal order:

```text
10, 20, 30, 40
```

Descending view:

```java
set.descendingSet();
```

gives:

```text
40, 30, 20, 10
```

The lecture includes both `descendingSet()` and `descendingIterator()` in the NavigableSet section. 

---

# 13. Range Operations in TreeSet

`TreeSet` is especially powerful when you need a portion of the sorted data.

Important methods include:

```java
headSet()
tailSet()
subSet()
```

---

## `headSet()`

Gets elements before a particular value.

Conceptually:

```text
Set:

10 20 30 40 50

headSet(40)

10 20 30
```

---

## `tailSet()`

Gets elements from a particular point onward.

Conceptually:

```text
tailSet(30)

30 40 50
```

---

## `subSet()`

Gets a range.

Conceptually:

```text
subSet(20, 50)

20 30 40
```

The lecture also shows the more detailed `NavigableSet.subSet()` form, which allows boundary inclusiveness to be specified.

The idea is:

```java
subSet(from, fromInclusive, to, toInclusive)
```

For example:

```java
set.subSet(20, true, 50, false);
```

means:

```text
20 <= x < 50
```

---

# 14. Map Interface

Now we move from `Set` to `Map`.

A `Map` stores:

```text
key → value
```

Example:

```java
Map<Integer, String> students = new HashMap<>();

students.put(101, "Aditya");
students.put(102, "Rohit");
students.put(103, "Rohan");
```

Conceptually:

```text
101 → Aditya
102 → Rohit
103 → Rohan
```

The key is unique.

Values can be duplicated.

---

# 15. Map does not extend Collection

This is an important hierarchy point.

```text
Iterable
   ↓
Collection
   ↓
Set
```

is one hierarchy.

`Map` is separate:

```text
Map
├── HashMap
│    └── LinkedHashMap
│
└── TreeMap
```

The lecture's pages 1–2 show these separate hierarchies. 

---

# 16. Basic Map Methods

Important methods discussed in the lecture include:

```java
put()
get()
containsKey()
containsValue()
remove()
clear()
```

Let's understand each one.

---

# 17. `put()`

```java
map.put(key, value);
```

Adds a new mapping or updates an existing one.

Example:

```java
Map<Integer, String> map = new HashMap<>();

map.put(101, "Aditya");
```

Now:

```text
101 → Aditya
```

---

## `put()` has an important return value

This is an interview favorite.

If the key is **new**:

```java
map.put(101, "Aditya");
```

returns:

```text
null
```

If the key already exists:

```java
map.put(101, "Rohit");
```

the old value:

```text
"Aditya"
```

is returned.

So:

```java
String oldValue = map.put(101, "Rohit");
```

gives:

```text
oldValue = "Aditya"
```

Conceptually:

```text
New key
   ↓
put()
   ↓
null


Existing key
   ↓
put()
   ↓
previous value
```

---

# 18. `get()`

Used to retrieve the value associated with a key.

```java
map.get(101);
```

If:

```text
101 → Aditya
```

then:

```text
get(101) → Aditya
```

If the key doesn't exist, `get()` returns `null` unless a value of `null` is actually associated with the key, which means `get()` alone cannot always distinguish those two situations.

For checking existence, use:

```java
containsKey()
```

---

# 19. `containsKey()`

Checks whether a key exists.

```java
map.containsKey(101);
```

Result:

```text
true
```

This is useful when you need to distinguish:

```text
key doesn't exist
```

from:

```text
key exists and its value is null
```

---

# 20. `containsValue()`

Checks whether a particular value exists.

```java
map.containsValue("Aditya");
```

Result:

```text
true
```

Important distinction:

```text
containsKey()
→ searches keys

containsValue()
→ searches values
```

---

# 21. `remove()`

Removes a mapping.

```java
map.remove(101);
```

If:

```text
101 → Aditya
```

exists, the mapping is removed.

---

# 22. `clear()`

Removes all mappings.

```java
map.clear();
```

Before:

```text
101 → Aditya
102 → Rohit
103 → Rohan
```

After:

```text
empty map
```

---

# 23. `getOrDefault()`

Sometimes you want a fallback value when a key does not exist.

Instead of:

```java
String value = map.get(101);

if (value == null) {
    value = "Unknown";
}
```

you can use:

```java
String value =
    map.getOrDefault(101, "Unknown");
```

Meaning:

```text
key exists
    ↓
return its value

key missing
    ↓
return default value
```

Example:

```java
Map<Integer, String> map = new HashMap<>();

map.put(101, "Aditya");

map.getOrDefault(101, "Unknown");
```

returns:

```text
Aditya
```

while:

```java
map.getOrDefault(999, "Unknown");
```

returns:

```text
Unknown
```

---

# 24. `putIfAbsent()`

`putIfAbsent()` adds a mapping **only if the key is not already present**.

Example:

```java
Map<Integer, String> map = new HashMap<>();

map.put(101, "Aditya");

map.putIfAbsent(101, "Rohit");
```

The value remains:

```text
101 → Aditya
```

because key `101` already exists.

But:

```java
map.putIfAbsent(102, "Rohit");
```

adds:

```text
102 → Rohit
```

### Mental model

```text
put()
→ add OR update

putIfAbsent()
→ add only if missing
```

---

# 25. `replace()`

`replace()` updates an existing mapping.

Conceptually:

```java
map.replace(101, "Rohit");
```

If `101` exists:

```text
101 → Aditya
```

becomes:

```text
101 → Rohit
```

This is useful when you specifically want replacement behavior rather than blindly inserting.

---

# 26. `keySet()`

One of the most useful Map views is:

```java
map.keySet();
```

It returns a **Set containing the keys**.

Example:

```java
Map<Integer, String> map = new HashMap<>();

map.put(101, "Aditya");
map.put(102, "Rohit");
map.put(103, "Rohan");
```

Then:

```java
map.keySet();
```

conceptually gives:

```text
{101, 102, 103}
```

Why is it a `Set`?

Because Map keys are unique.

Therefore:

```text
Map keys
   ↓
unique
   ↓
Set
```

---

# 27. `entrySet()`

If you need both the key and value together, use:

```java
map.entrySet();
```

Each element is a:

```java
Map.Entry<K, V>
```

Example:

```java
for (Map.Entry<Integer, String> entry
        : map.entrySet()) {

    System.out.println(
        entry.getKey() + " " +
        entry.getValue()
    );
}
```

For:

```text
101 → Aditya
102 → Rohit
```

the iteration gives entries such as:

```text
101 Aditya
102 Rohit
```

---

# 28. `Map.Entry`

`Map.Entry` represents one key-value pair.

Think:

```text
Entry
┌──────────────┐
│ key          │
│ value        │
└──────────────┘
```

You can retrieve them using:

```java
entry.getKey();
entry.getValue();
```

This is often the cleanest way to iterate over both keys and values.

---

# 29. `keySet()` vs `entrySet()`

| Method       | Gives                    |
| ------------ | ------------------------ |
| `keySet()`   | Set of keys              |
| `entrySet()` | Set of key-value entries |

Example:

```text
Map:

101 → Aditya
102 → Rohit
```

### `keySet()`

```text
101
102
```

### `entrySet()`

```text
101 → Aditya
102 → Rohit
```

Use `entrySet()` when you need both pieces of information.

---

# 30. Creating small immutable Maps with `Map.of()`

For small maps, Java provides:

```java
Map.of()
```

Example:

```java
Map<Integer, String> map =
    Map.of(
        101, "Aditya",
        102, "Rohit",
        103, "Rohan"
    );
```

This is convenient for creating a small map directly.

The lecture specifically mentions `Map.of()` for **small immutable maps**. 

The important word is:

> **Immutable**

You should not expect to modify this map with operations such as:

```java
map.put(...);
```

---

# 31. Map hierarchy

The lecture's page 2 gives the main Map hierarchy:

```text
Map
 |
 +----------------+
 |                |
HashMap         TreeMap
 |
LinkedHashMap
```

Then the tree branch is:

```text
Map
 ↓
SortedMap
 ↓
NavigableMap
 ↓
TreeMap
```

So the full conceptual hierarchy is:

```text
Map
 |
 +-------------------------+
 |                         |
HashMap                  SortedMap
 |                         |
LinkedHashMap          NavigableMap
                           |
                        TreeMap
```



---

# 32. Specialized Map Implementations

The lecture covers several specialized map classes:

```text
HashTable
Properties
WeakHashMap
IdentityHashMap
EnumMap
ConcurrentHashMap
```

Each exists for a different problem.

The key is not to memorize the names blindly.

Instead, remember **why each one exists**.

---

# 33. Hashtable

`Hashtable` is a **legacy Map implementation**.

The lecture notes:

```text
Hashtable
→ legacy class
→ thread safe
→ overhead
```

Historically, `Hashtable` provides synchronization/thread safety, but this comes with additional overhead.

The lecture contrasts it with `HashMap`:

```text
Hashtable
→ thread-safe
→ more overhead

HashMap
→ not synchronized
→ generally lower overhead
```

For modern concurrent applications, `ConcurrentHashMap` is generally the more specialized choice discussed in the lecture.

---

# 34. Properties

`Properties` is a specialized legacy class built on top of `Hashtable`.

The lecture shows the relationship:

```text
Hashtable
   ↑
Properties
```

It is primarily used for **text-based configuration data**.

For example, configuration may look like:

```text
username = admin
password = 123
```

The lecture's page 3 gives exactly this kind of configuration example and labels `Properties` as a specialized map for configuration data. 

A typical use case is application configuration:

```text
database username
database password
application settings
server configuration
```

---

# 35. Why use Properties?

Instead of treating configuration as arbitrary application data:

```text
Map<Object, Object>
```

`Properties` is designed around configuration-style key-value data, traditionally represented as strings.

Mental model:

```text
Properties
    ↓
configuration
    ↓
key = value
```

Example:

```text
username = admin
password = 123
```

---

# 36. WeakHashMap

`WeakHashMap` is useful for **cache-like behavior**.

The lecture describes it using:

```text
WeakHashMap
→ rarely used
→ cache-like behavior
→ weak references
```



The important idea is that keys are held through **weak references**.

This means an entry can become eligible for removal when the key is no longer strongly referenced elsewhere.

---

# 37. Why is WeakHashMap useful for caches?

Imagine:

```text
Application
    |
    ↓
Object A
    |
    ↓
WeakHashMap
```

If the application stops strongly referencing `Object A`, the key can become eligible for garbage collection.

Eventually, the corresponding entry can disappear from the `WeakHashMap`.

So the map does not necessarily keep the object alive forever.

This makes it useful for certain cache-like or metadata scenarios where you do not want the map itself to prevent garbage collection.

### Mental model

```text
Normal HashMap

Map → key
      ↑
   keeps key strongly reachable


WeakHashMap

Map → weak key
      ↑
   does not prevent GC
```

---

# 38. IdentityHashMap

This is one of the most interesting specialized maps.

Normal `HashMap` uses equality-based key comparison.

Conceptually:

```text
HashMap
→ equals()
```

`IdentityHashMap` works differently.

It compares keys using:

```java
==
```

rather than:

```java
.equals()
```

The lecture explicitly contrasts:

```text
IdentityHashMap
→ reference-based

HashMap
→ equality-based
```

on pages 3–4. 

---

# 39. `==` vs `equals()`

This distinction is critical.

Suppose:

```java
String a = new String("Aditya");
String b = new String("Aditya");
```

These are two different objects.

Conceptually:

```text
a ──→ [Aditya]
b ──→ [Aditya]
```

Their contents may be equal:

```java
a.equals(b)
```

returns:

```text
true
```

But they are different object references:

```java
a == b
```

returns:

```text
false
```

So:

```text
equals()
→ "Do these objects represent equal values?"

==
→ "Are these the exact same object?"
```

---

# 40. HashMap vs IdentityHashMap

Consider:

```java
String a = new String("Aditya");
String b = new String("Aditya");
```

With a normal `HashMap`, the keys are compared using the equality-based mechanism.

So two logically equal objects can be treated as the same key.

With `IdentityHashMap`, the references matter.

```text
a != b
```

Therefore they can be treated as different keys even though:

```text
a.equals(b) == true
```

The lecture illustrates this exact idea with two `String` objects containing `"Aditya"` on page 4. 

---

# 41. When is IdentityHashMap useful?

It is **not a normal replacement for HashMap**.

It is useful in specialized algorithms where object identity matters.

The lecture specifically mentions:

> **specific graph algorithms**

A useful mental model is:

```text
HashMap
→ "Are these logically equal?"

IdentityHashMap
→ "Is this literally the same object?"
```

This can matter when tracking objects by identity rather than by their logical value.

---

# 42. EnumMap

`EnumMap` is a highly specialized Map designed for:

> **Enum keys**

Example:

```java
enum Day {
    MON, TUE, WED, THU, FRI, SAT, SUN
}
```

Then:

```java
Map<Day, Integer> map =
    new EnumMap<>(Day.class);
```

The key type is the enum:

```text
Day
 ↓
MON
TUE
WED
...
```

---

# 43. Why is EnumMap efficient?

The lecture highlights:

* optimized for enum keys,
* uses internal ordinal indexing,
* memory efficient,
* preserves iteration order,
* does not allow `null` keys.

Enums have a fixed set of constants.

For example:

```text
MON
TUE
WED
THU
FRI
SAT
SUN
```

Each enum constant has an ordinal position.

Conceptually:

```text
MON → 0
TUE → 1
WED → 2
THU → 3
...
```

So an `EnumMap` can use this known structure very efficiently.

---

# 44. EnumMap example

```java
enum Day {
    MON, TUE, WED
}

Map<Day, Integer> map =
    new EnumMap<>(Day.class);

map.put(Day.MON, 1);
map.put(Day.TUE, 2);
map.put(Day.WED, 3);
```

Conceptually:

```text
MON → 1
TUE → 2
WED → 3
```

The lecture's page 4 shows this kind of `Day` enum example and notes the memory-efficiency advantage. 

---

# 45. EnumMap preserves iteration order

The lecture specifically states:

> **Iteration order is preserved.**

For enum constants, this means iteration follows their natural enum declaration order.

If:

```java
enum Day {
    MON, TUE, WED
}
```

then the map's iteration follows:

```text
MON
TUE
WED
```

rather than an arbitrary order.

---

# 46. EnumMap and null keys

The lecture notes:

```text
null key → not allowed
null value → allowed
```

So:

```java
map.put(null, 10);
```

is not allowed for the key.

But a null value can be stored:

```java
map.put(Day.MON, null);
```

The key restriction exists because the map is specialized around enum constants.

---

# 47. ConcurrentHashMap

The lecture describes `ConcurrentHashMap` as:

> **A thread-safe Map designed for multi-threaded environments.**

The key idea is:

```text
ConcurrentHashMap
        ↓
thread-safe
        ↓
high performance
```

It is presented as the modern alternative to using `Hashtable` when you need concurrent access.

---

# 48. Why not simply use Hashtable?

The lecture makes this comparison:

```text
Hashtable
→ thread-safe
→ synchronization overhead


ConcurrentHashMap
→ thread-safe
→ designed for better concurrent performance
```

The important idea is that `ConcurrentHashMap` avoids the need to lock the **entire map** for every operation in the simplistic way associated with legacy synchronization.

This allows multiple threads to work with the map efficiently.

---

# 49. ConcurrentHashMap use case

Imagine multiple threads processing users:

```text
Thread 1 ──┐
Thread 2 ──┤
Thread 3 ──┼──→ ConcurrentHashMap
Thread 4 ──┘
```

If multiple threads need to access/update shared map data, a normal `HashMap` is not the correct thread-safe choice.

A concurrent collection is designed for this situation.

Mental model:

```text
Single-threaded
     ↓
HashMap


Multi-threaded shared map
     ↓
ConcurrentHashMap
```

---

# 50. HashMap vs Hashtable vs ConcurrentHashMap

| Feature           | HashMap                 | Hashtable                  | ConcurrentHashMap           |
| ----------------- | ----------------------- | -------------------------- | --------------------------- |
| Thread-safe       | No                      | Yes                        | Yes                         |
| Legacy            | No                      | Yes                        | No                          |
| Concurrent use    | Not directly            | Supported but older design | Designed for it             |
| Performance focus | General use             | Synchronization            | High-concurrency access     |
| Typical choice    | Normal application code | Legacy code                | Multi-threaded applications |

---

# 51. Choosing the correct Map

A useful decision tree:

```text
Need a normal key-value map?
        ↓
     HashMap
```

```text
Need insertion order?
        ↓
  LinkedHashMap
```

```text
Need sorted keys?
        ↓
    TreeMap
```

```text
Keys are enum constants?
        ↓
    EnumMap
```

```text
Need weak-reference / cache-like behavior?
        ↓
   WeakHashMap
```

```text
Need identity (==) comparison?
        ↓
 IdentityHashMap
```

```text
Need concurrent access?
        ↓
ConcurrentHashMap
```

```text
Working with configuration properties?
        ↓
    Properties
```

---

# 52. Set decision guide

Similarly:

```text
Need unique elements?
        ↓
       Set
        |
        +----------------------+
        |          |           |
        ↓          ↓           ↓
    HashSet   LinkedHashSet  TreeSet
        |          |           |
      Fast      Insertion     Sorted
      lookup      order        order
```

So:

### `HashSet`

Use when:

```text
Uniqueness + fast average lookup
```

### `LinkedHashSet`

Use when:

```text
Uniqueness + insertion order
```

### `TreeSet`

Use when:

```text
Uniqueness + sorted/navigation operations
```

---

# 53. TreeSet navigation cheat sheet

This is worth memorizing.

Suppose:

```text
10  20  30  40  50
```

For `30`:

| Method        | Meaning          | Result |
| ------------- | ---------------- | -----: |
| `lower(30)`   | greatest `< 30`  |   `20` |
| `floor(30)`   | greatest `<= 30` |   `30` |
| `higher(30)`  | smallest `> 30`  |   `40` |
| `ceiling(30)` | smallest `>= 30` |   `30` |

Easy memory:

```text
lower   = <
floor   = <=
higher  = >
ceiling = >=
```

And:

```text
first() → smallest
last()  → largest
```

---

# 54. Set vs Map

| Feature              | Set          | Map                 |
| -------------------- | ------------ | ------------------- |
| Stores               | Elements     | Key-value pairs     |
| Duplicate elements   | Not allowed  | Keys not duplicated |
| Main lookup          | `contains()` | `containsKey()`     |
| Basic addition       | `add()`      | `put()`             |
| Remove               | `remove()`   | `remove()`          |
| Size                 | `size()`     | `size()`            |
| Empty check          | `isEmpty()`  | `isEmpty()`         |
| Collection hierarchy | Yes          | No                  |

---

# 55. HashSet vs TreeSet vs LinkedHashSet

| Feature            | `HashSet`                     | `LinkedHashSet`              | `TreeSet`                    |
| ------------------ | ----------------------------- | ---------------------------- | ---------------------------- |
| Duplicates         | No                            | No                           | No                           |
| Main structure     | Hash-based                    | Hash + linked structure      | Self-balancing BST           |
| Order              | No guaranteed insertion order | Insertion order              | Sorted order                 |
| Navigation methods | Limited                       | Limited                      | Rich                         |
| Typical lookup     | Average `O(1)`                | Average `O(1)`               | `O(log n)`                   |
| Best for           | Fast membership               | Membership + insertion order | Sorted/navigation operations |

---

# 56. HashMap vs LinkedHashMap vs TreeMap

| Feature        | `HashMap`            | `LinkedHashMap`    | `TreeMap`               |
| -------------- | -------------------- | ------------------ | ----------------------- |
| Key-value      | Yes                  | Yes                | Yes                     |
| Ordering       | No guaranteed order  | Insertion order    | Sorted by key           |
| Internal idea  | Hash table           | Hash table + links | Red-Black Tree          |
| Typical lookup | Average `O(1)`       | Average `O(1)`     | `O(log n)`              |
| Best for       | General-purpose maps | Ordered iteration  | Sorted/range operations |

---

# 57. Important interview traps

## Trap 1: Set defines lots of new methods

Not really.

The lecture emphasizes that basic `Set` operations come from `Collection`.

```text
Set
 ↓
inherits Collection methods
```

---

## Trap 2: TreeSet is just a sorted HashSet

No.

`TreeSet` uses a **tree-based sorted structure**, not hashing.

```text
HashSet → hash-based

TreeSet → self-balancing BST
```

---

## Trap 3: `lower()` and `floor()` are the same

They are not.

```text
lower(x) → < x
floor(x) → <= x
```

---

## Trap 4: `higher()` and `ceiling()` are the same

Again:

```text
higher(x)  → > x
ceiling(x) → >= x
```

---

## Trap 5: `put()` always returns the inserted value

No.

Conceptually:

```text
new key
→ null

existing key
→ previous value
```

---

## Trap 6: `putIfAbsent()` always overwrites

No.

```text
putIfAbsent()
→ changes only when key is absent
```

---

## Trap 7: `keySet()` returns a List

No.

It returns a:

```java
Set<K>
```

because Map keys are unique.

---

## Trap 8: IdentityHashMap uses `equals()`

No.

Its defining behavior is identity/reference comparison:

```java
==
```

rather than the normal equality-based comparison.

---

## Trap 9: WeakHashMap is just a faster HashMap

No.

Its purpose is different.

It uses weak references and is useful for particular cache-like situations.

---

## Trap 10: EnumMap is just HashMap with enum keys

Conceptually it is much more specialized.

It can exploit enum characteristics such as ordinal positions, making it memory efficient and optimized for enum keys.

---

## Trap 11: Hashtable is the modern answer for thread safety

The lecture presents `Hashtable` as a **legacy**, synchronized implementation.

For modern concurrent map use cases, the specialized choice discussed is:

```text
ConcurrentHashMap
```

---

# 58. Complete mental model

## Set

```text
                    Set
                     |
       ┌─────────────┴─────────────┐
       |                           |
   HashSet                    SortedSet
       |                           |
LinkedHashSet                NavigableSet
                                   |
                                TreeSet
```

---

## Map

```text
                       Map
                        |
          ┌─────────────┴──────────────┐
          |                            |
       HashMap                      SortedMap
          |                            |
   LinkedHashMap                 NavigableMap
                                       |
                                    TreeMap
```

Specialized maps:

```text
Map
├── HashMap
├── LinkedHashMap
├── TreeMap
├── Properties
├── WeakHashMap
├── IdentityHashMap
├── EnumMap
└── ConcurrentHashMap
```

The lecture's pages 2–5 organize these specialized classes around their specific use cases. 

---

# 59. Practical examples

## Example 1 — Unique emails

```java
Set<String> emails = new HashSet<>();

emails.add("a@gmail.com");
emails.add("b@gmail.com");
emails.add("a@gmail.com");
```

Use:

```text
HashSet
```

because uniqueness is the main requirement.

---

## Example 2 — Preserve registration order

```java
Set<String> users = new LinkedHashSet<>();

users.add("Aditya");
users.add("Rohit");
users.add("Rohan");
```

Use:

```text
LinkedHashSet
```

because insertion order matters.

---

## Example 3 — Sorted scores

```java
TreeSet<Integer> scores = new TreeSet<>();

scores.add(80);
scores.add(50);
scores.add(90);
scores.add(70);
```

Result:

```text
50, 70, 80, 90
```

Use `TreeSet` because sorted order matters.

---

## Example 4 — Student lookup

```java
Map<Integer, String> students =
    new HashMap<>();

students.put(101, "Aditya");
students.put(102, "Rohit");
```

Now:

```java
students.get(101);
```

returns:

```text
Aditya
```

Use `HashMap` because you want fast key-based lookup.

---

## Example 5 — Sorted student IDs

```java
Map<Integer, String> students =
    new TreeMap<>();
```

Now keys remain sorted.

Use `TreeMap` when sorted keys are important.

---

## Example 6 — Enum configuration

```java
enum Day {
    MON, TUE, WED, THU, FRI
}

Map<Day, Integer> schedule =
    new EnumMap<>(Day.class);
```

Use `EnumMap` because the keys are enum constants.

---

## Example 7 — Multi-threaded shared map

```java
Map<String, Integer> counts =
    new ConcurrentHashMap<>();
```

Use it when multiple threads need to work with shared map data.

---

# 60. Quick revision sheet

```text
SET
│
├── Set → unique elements
│
├── HashSet
│   └── fast average lookup
│
├── LinkedHashSet
│   └── insertion order
│
└── TreeSet
    ├── sorted order
    ├── self-balancing BST
    └── navigation methods
```

```text
TREESET METHODS

first()    → smallest
last()     → largest

lower(x)   → < x
floor(x)   → <= x

higher(x)  → > x
ceiling(x) → >= x

pollFirst() → remove first
pollLast()  → remove last

headSet() / tailSet() / subSet()
→ range operations
```

```text
MAP
│
├── HashMap
├── LinkedHashMap
├── TreeMap
├── Properties
├── WeakHashMap
├── IdentityHashMap
├── EnumMap
└── ConcurrentHashMap
```

```text
MAP METHODS

put()
get()
containsKey()
containsValue()
remove()
clear()

getOrDefault()
putIfAbsent()
replace()

keySet()
entrySet()
```

---

# 61. Specialized Map cheat sheet

| Class               | Remember it as                        |
| ------------------- | ------------------------------------- |
| `HashMap`           | General-purpose hash map              |
| `LinkedHashMap`     | HashMap + insertion order             |
| `TreeMap`           | Sorted keys                           |
| `Properties`        | Configuration data / legacy           |
| `WeakHashMap`       | Weak references / cache-like behavior |
| `IdentityHashMap`   | Compare keys using `==`               |
| `EnumMap`           | Optimized for enum keys               |
| `ConcurrentHashMap` | Thread-safe high-performance map      |

---

# 62. Key Takeaways

* `Set` stores **unique elements**.
* `Set` extends `Collection`.
* `Set` mainly uses methods inherited from `Collection`.
* `HashSet` provides hash-based uniqueness and fast average lookup.
* `LinkedHashSet` additionally preserves insertion order.
* `TreeSet` maintains sorted order using a self-balancing BST.
* `TreeSet` provides powerful navigation methods:

  * `first()`
  * `last()`
  * `lower()`
  * `higher()`
  * `floor()`
  * `ceiling()`
  * `pollFirst()`
  * `pollLast()`
* `headSet()`, `tailSet()`, and `subSet()` are useful for range operations.
* `Map` stores **key-value pairs**.
* Map keys are unique.
* `put()` returns `null` for a new key and the previous value when updating an existing key.
* `getOrDefault()` provides a fallback.
* `putIfAbsent()` avoids overwriting an existing key.
* `replace()` safely updates an existing mapping.
* `keySet()` gives the keys as a `Set`.
* `entrySet()` gives key-value entries through `Map.Entry`.
* `Map.of()` is useful for small immutable maps.
* `Properties` is mainly for configuration-style data.
* `WeakHashMap` uses weak references and can support cache-like behavior.
* `IdentityHashMap` compares keys using `==` instead of normal equality.
* `EnumMap` is optimized specifically for enum keys.
* `EnumMap` preserves iteration order and does not allow null keys.
* `ConcurrentHashMap` is designed for thread-safe, high-performance concurrent access.
* `Hashtable` is a legacy synchronized Map implementation.
* The biggest selection rule is:

```text
Hash*       → fast lookup
LinkedHash* → insertion order
Tree*       → sorted/navigation operations
```

---

# 63. Minimal Self-Test

1. What interfaces does `TreeSet` implement indirectly?
2. Does `Set` introduce its own basic collection methods?
3. What are the four common `HashSet` constructor forms?
4. What is the difference between `HashSet` and `LinkedHashSet`?
5. Why does `TreeSet` provide `lower()`, `floor()`, `higher()`, and `ceiling()`?
6. What is the difference between `lower(x)` and `floor(x)`?
7. What is the difference between `higher(x)` and `ceiling(x)`?
8. What does `pollFirst()` do?
9. What does `subSet()` provide?
10. Why is `Map` not part of the `Collection` hierarchy?
11. What does `put()` return when the key already exists?
12. What is the difference between `put()` and `putIfAbsent()`?
13. What is the purpose of `getOrDefault()`?
14. What is the difference between `keySet()` and `entrySet()`?
15. What is `Map.Entry`?
16. Why would you use `Map.of()`?
17. What is `Properties` mainly used for?
18. How does `WeakHashMap` differ from `HashMap`?
19. What is the difference between `HashMap` and `IdentityHashMap`?
20. Why does `IdentityHashMap` use `==`?
21. When should you use `EnumMap`?
22. Why is `EnumMap` memory efficient?
23. Does `EnumMap` allow null keys?
24. When should you use `ConcurrentHashMap`?
25. Why is `Hashtable` considered a legacy class?
26. What is the difference between `HashMap`, `LinkedHashMap`, and `TreeMap`?
27. What is the difference between `HashSet`, `LinkedHashSet`, and `TreeSet`?
