# String vs StringBuilder vs StringBuffer (Java)

---

# What it is

Java provides three main classes to work with text:

* **String** → Immutable sequence of characters.
* **StringBuilder** → Mutable sequence of characters (fast).
* **StringBuffer** → Mutable and thread-safe version of `StringBuilder`.

Although all three store characters, they are designed for different purposes.

---

# One-Sentence Summary

* **String** → Use when text should never change.
* **StringBuilder** → Use when modifying strings frequently in a single thread.
* **StringBuffer** → Use when multiple threads modify the same string safely.

---

# The Big Problem (Why StringBuilder Exists)

Strings in Java are **immutable**.

Once a String object is created, its content **cannot be modified**.

Whenever you perform operations like

```java
str = str + "World";
```

or

```java
str = str.trim();
```

Java **does not modify** the existing object.

Instead,

1. Creates a new String object.
2. Copies old content.
3. Applies changes.
4. Old object becomes garbage.

Example:

```java
String s = "Java";

s = s + " Programming";
```

Memory:

```
Old Object

"Java"

↓

New Object

"Java Programming"
```

The old `"Java"` object cannot be reused.

---

# Why is this a Problem?

Imagine this loop:

```java
String s = "";

for(int i=0;i<10000;i++)
{
    s += i;
}
```

Every iteration creates

* new String
* copy previous characters
* garbage collection

Thousands of temporary objects are created.

This becomes very slow.

---

# Solution: StringBuilder

Instead of creating new objects repeatedly,

`StringBuilder`

uses **one resizable internal buffer**.

It simply changes that buffer.

```
append()

↓

same object changes

↓

No new String every time
```

This makes it much faster.

---

# Intuition

## String

Think of a **printed book**.

If you want to change one sentence,

you print an entirely new book.

---

## StringBuilder

Think of a **whiteboard**.

You erase,

write,

append,

delete,

without buying a new board.

---

# Creating Strings

The lecture shows two ways to create Strings. 

## 1. Using String Literal

```java
String s1 = "Hello";
```

Stored inside the **String Pool** if possible.

---

## 2. Using new

```java
String s2 = new String("Hello");
```

Always creates a new object in Heap memory.

Even if `"Hello"` already exists in the String Pool.

---

# Common String Constructors

The notes list several constructors. 

```java
new String()

new String(String)

new String(char[])

new String(byte[])

new String(StringBuilder)

new String(StringBuffer)
```

---

# Important String Methods

The lecture groups methods into categories. 

---

## Length / Emptiness

```java
length()

isEmpty()

isBlank()
```

Example

```java
String s = "Hello";

System.out.println(s.length());   //5
```

---

## Character Access

```java
charAt()

toCharArray()
```

Example

```java
String s = "Java";

System.out.println(s.charAt(2));
```

Output

```
v
```

---

## Searching

```
contains()

indexOf()

lastIndexOf()

startsWith()

endsWith()
```

Example

```java
String s = "Programming";

System.out.println(s.contains("gram"));
```

Output

```
true
```

---

## Comparison

```
equals()

equalsIgnoreCase()

compareTo()
```

Example

```java
String a = "java";

String b = "JAVA";

System.out.println(a.equals(b));
```

Output

```
false
```

---

## Transformation

```
substring()

toUpperCase()

toLowerCase()

trim()

strip()

repeat()

replace()

replaceAll()

split()

join()
```

Example

```java
String s = " hello ";

System.out.println(s.trim());
```

Output

```
hello
```

---

## Conversion

```
valueOf()

getBytes()
```

---

## Advanced

```
intern()

format()
```

---

# String Immutability

The lecture diagram shows this concept visually. 

Example

```java
String s = "Hello";

s.toUpperCase();
```

What happens?

NOT

```
HELLO
```

Actually

```
Hello

↓

HELLO (new object)
```

Original object remains unchanged.

Correct usage:

```java
s = s.toUpperCase();
```

---

# StringBuilder

`StringBuilder` belongs to

```java
java.lang
```

It extends

```
AbstractStringBuilder
```

The notes also show that `StringBuffer` extends the same parent class. 

---

# Internal Structure

Internally,

`StringBuilder` stores

```
byte[] value
```

(or `char[]` conceptually in older explanations)

along with

```
count
```

which tracks how many characters are actually used. 

Example

```java
StringBuilder sb = new StringBuilder("Java");
```

Internally

```
Capacity = 20

Count = 4

+----+----+----+----+----+----+----+
| J  | a  | v  | a  |    |    | ... |
+----+----+----+----+----+----+----+
```

Why capacity 20?

Constructor with a String allocates:

```
16 + string length

16 + 4 = 20
```

This is illustrated in the lecture notes. 

---

# Default Capacity

```java
StringBuilder sb = new StringBuilder();
```

Default capacity

```
16
```

---

Custom capacity

```java
StringBuilder sb = new StringBuilder(50);
```

Capacity

```
50
```

---

# Dynamic Growth

Suppose

```
Capacity =16
```

You append more characters.

When capacity is full,

Java creates a bigger array.

Formula

```
newCapacity = oldCapacity × 2 + 2
```

Example

Old

```
16
```

New

```
16 × 2 + 2

=34
```

Again full

```
34 ×2 +2

=70
```

This resizing sequence is shown in the notes. 

---

# Why Resize?

Suppose

```
Capacity

16
```

You append

```
20 characters
```

Old array

```
16 cells
```

↓

Create

```
34 cells
```

↓

Copy old data

↓

Continue appending

This happens automatically.

---

# StringBuilder Methods

The lecture lists these common methods. 

```java
append()

insert()

delete()

replace()

reverse()

charAt()

setCharAt()

length()

capacity()

ensureCapacity()

trimToSize()

toString()
```

---

## append()

```java
StringBuilder sb = new StringBuilder();

sb.append("Java");

sb.append(" Programming");
```

Output

```
Java Programming
```

---

## insert()

```java
sb.insert(4," Language");
```

Result

```
Java Language Programming
```

---

## delete()

```java
sb.delete(4,13);
```

Removes characters between indices.

---

## reverse()

```java
StringBuilder sb = new StringBuilder("Java");

sb.reverse();
```

Output

```
avaJ
```

---

## replace()

```java
sb.replace(0,4,"Python");
```

---

## length()

Returns

```
Current characters
```

---

## capacity()

Returns

```
Internal array size
```

---

## ensureCapacity()

Ensures the internal buffer has at least the requested capacity.

```java
sb.ensureCapacity(100);
```

---

## trimToSize()

Reduces unused capacity.

Useful when a large builder is no longer expected to grow.

---

## toString()

Converts

```
StringBuilder

↓

String
```

Example

```java
String str = sb.toString();
```

---

# Comparing StringBuilders

A common mistake highlighted by the notes is comparing two `StringBuilder` objects with `equals()`. 

```java
StringBuilder sb1 = new StringBuilder("Aditya");
StringBuilder sb2 = new StringBuilder("Aditya");

System.out.println(sb1.equals(sb2));
```

Output

```
false
```

Why?

`StringBuilder` does **not** override `Object.equals()`.

So `equals()` checks **reference equality**, not character content.

To compare content:

```java
sb1.toString().equals(sb2.toString());
```

---

# StringBuffer

`StringBuffer` is

* Mutable
* Thread-safe

It extends the same `AbstractStringBuilder` class as `StringBuilder`. 

---

# Why Thread Safety?

Imagine

```
Thread 1

append("A")
```

and

```
Thread 2

append("B")
```

Both operate on the same object.

Without synchronization,

operations may overlap.

Possible outputs

```
HelloA

HelloAB
```

or inconsistent results due to a **race condition**, as illustrated in the lecture. 

`StringBuffer` synchronizes its methods to prevent this.

---

# High-Level Comparison

| Feature     | String                          | StringBuilder             | StringBuffer                       |
| ----------- | ------------------------------- | ------------------------- | ---------------------------------- |
| Mutable     | ❌ No                            | ✅ Yes                     | ✅ Yes                              |
| Thread-safe | ✅ Yes (immutable)               | ❌ No                      | ✅ Yes                              |
| Performance | Slow for repeated modifications | Fast                      | Slower than StringBuilder          |
| Best Use    | Constant text                   | Heavy string manipulation | Multi-threaded shared modification |

---

# Common Mistakes / Gotchas

### 1. String Pool Confusion

```java
String s = "Java";
```

Uses the String Pool.

```java
new String("Java");
```

Always creates a Heap object.

`StringBuilder` objects are **never** stored in the String Pool.

---

### 2. Using String in Loops

Bad

```java
String s="";

for(...)
    s += i;
```

Good

```java
StringBuilder sb = new StringBuilder();

for(...)
    sb.append(i);
```

---

### 3. Overusing StringBuilder

For simple concatenation:

```java
String s = "A" + "B";
```

The compiler optimizes this efficiently.

No need to manually use `StringBuilder`.

---

### 4. Using StringBuffer Unnecessarily

Modern web applications usually handle each request on its own thread.

Most string manipulation happens within that single thread.

`StringBuilder` is usually sufficient.

---

### 5. Confusing `length()` and `capacity()`

```java
StringBuilder sb = new StringBuilder("Java");
```

```
length()   → 4

capacity() → 20
```

---

# Key Takeaways

* `String` is immutable, so every modification creates a new object.
* Repeated String concatenation creates many temporary objects and increases garbage collection.
* `StringBuilder` modifies the same internal buffer, making it much faster for repeated updates.
* Default `StringBuilder` capacity is **16**.
* `new StringBuilder("Java")` creates a capacity of **20** (`16 + 4`).
* When the buffer fills, capacity grows using `oldCapacity × 2 + 2`.
* `StringBuilder` and `StringBuffer` extend `AbstractStringBuilder`.
* `StringBuilder` is not thread-safe.
* `StringBuffer` is synchronized and thread-safe but slower due to synchronization overhead.
* `StringBuilder.equals()` compares object references, not content.

---

# Minimal Self-Test

1. Why is `String` called immutable?
2. Why is `StringBuilder` faster inside loops?
3. What is the default capacity of `StringBuilder()`?
4. What is the capacity of `new StringBuilder("Java")`?
5. If capacity is **16**, what will it become after resizing?
6. What is the difference between `length()` and `capacity()`?
7. Why does `sb1.equals(sb2)` return `false` for two `StringBuilder` objects with the same text?
8. When should you choose `StringBuffer` over `StringBuilder`?
9. Why are `String` literals stored differently from `new String("...")`?
10. What does `trimToSize()` do?

---

# What to Learn Next

* String Pool and `intern()` in depth
* Wrapper Classes and Autoboxing
* `StringTokenizer` vs `split()`
* `StringJoiner`
* `String.format()` and `Formatter`
* Regular Expressions (`Pattern` and `Matcher`)
