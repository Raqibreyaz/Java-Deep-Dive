# Mastering Java Strings: The Silent Optimization Engine

## What a String really is

A Java `String` is a high-level object in `java.lang.String`, and the PDF starts from the basic idea that a string is a **sequence of characters**.
It also shows why Java does not leave strings as raw `char[]`: arrays are awkward for comparison, concatenation, and substring-style operations, so `String` exists as a richer abstraction over character data.

Your note’s immutability point is strongly supported by the PDF, which explicitly labels `String` as an **immutable class** and even hints at why that matters through examples like passwords, URLs, and hashes.
That gives the right mental model: strings are meant to be safe, shareable, and easy to use, not manually managed byte-by-byte.

### Example

```java
char[] arr = {'j', 'a', 'v', 'a'};
String s = "java";
```

The array holds raw characters, but the string gives you higher-level operations like comparison, concatenation, and substring handling more naturally.

## Two worlds of String creation

The PDF splits string creation into two paths: **literal** and **`new`**. A literal like `"Hello"` participates in the **String Pool**, while `new String("Hello")` creates a separate heap object.

This is one of the most important rules in the whole topic:

- Only **compile-time constants** go automatically to the string pool.
- Strings created at runtime go to the normal heap.

### Example: pooled literals

```java
String s1 = "Hello";
String s2 = "Hello";
String s3 = "Hello";
```

The PDF’s diagram shows `s1`, `s2`, and `s3` all pointing to the same pooled `"Hello"` object.

### Example: separate heap objects

```java
String s1 = new String("Hello");
String s2 = new String("Hello");
```

The PDF contrasts this with `new`, where each call creates a separate heap-side string object rather than reusing the pooled one directly.

### Example: compile-time constant folding

```java
String s1 = "Ja" + "va";
String s2 = "Java";
System.out.println(s1 == s2); // true
```

The PDF explicitly says this becomes `true` because `"Ja" + "va"` is a **compile-time** constant and is pooled like a normal literal.

### Example: runtime concatenation

```java
String s1 = "ja";
String s2 = s1 + "va";
String s3 = "java";
System.out.println(s2 == s3); // not pooled automatically
```

The PDF uses this kind of example to show the runtime/compile-time split: once concatenation depends on a variable like `s1`, the result is treated differently from a pure compile-time literal expression.

## Immutability and what “change” really means

The PDF’s reassignment example is small but extremely important:

```java
String s = "Hello";
s = "world";
```

This does **not** modify the original `"Hello"` object; it only changes which string object the variable `s` points to.
That is the real meaning of immutability: the object never changes, only references do.

Your note’s warning about operations like `concat()` is exactly the right intuition. Because strings are immutable, any “modifying” operation must produce a **new** string object instead of editing the old one.

### Example

```java
String s = "hello";
s.concat(" world");
System.out.println(s); // hello
```

Nothing visible changes here because the new string result was never assigned anywhere.

### Better version

```java
String s = "hello";
s = s.concat(" world");
System.out.println(s); // hello world
```

That works because the variable is updated to point at the newly created string.

## Internal layout and memory optimization

The PDF gives a very concrete internal view of modern `String`:

```java
public final class String {
    private final byte[] value;
    private final byte coder;
    private int hash;
}
```

So the optimization story is not just “string pool.” The PDF explicitly lists three internal ideas: **pooling**, **`char[]` to `byte[]` compact storage**, and **cached hash values**.

### `byte[] value`

The PDF shows `"java"` being stored internally as bytes like `106, 97, 118, 97`, and contrasts that with the older mental model of storing every character as a 2-byte `char`.
That is the key compact-string optimization: when the content fits a 1-byte representation, Java can save memory significantly.

### `coder`

The PDF says the `coder` field is a flag:

- `coder = 0` means **Latin1**, using 1 byte per character.
- `coder = 1` means **UTF16**, using 2 bytes per character.

### Examples

```java
String s = "java";
```

The PDF shows this kind of ASCII/Latin text using `coder = 0`, so the internal bytes are compact.

```java
String s = "कग";
```

The PDF shows non-Latin content like this using `coder = 1`, which means a wider internal representation.

That is why your note’s “silent optimization engine” title is actually accurate: the JVM quietly chooses a more compact internal layout when it can.

### `hash`

The PDF also shows an `int hash` field and labels hash-value caching as one of the core string optimizations.
That means once a string’s hash code is computed, Java can reuse it instead of recalculating it repeatedly for hash-based operations.

## Performance, gotchas, and examples

One of the most useful examples in the PDF is string concatenation inside a loop:

```java
String s = "";
for (int i = 0; i < 5; i++) {
    s = s + i;
    System.out.println(s);
}
```

The output grows as:

```text
0
01
012
0123
01234
```

The PDF pairs this with pool/heap sketches to show that repeated concatenation creates multiple intermediate strings rather than mutating one existing string in place.
That is why concatenation in loops is performance-heavy: immutability forces new string creation again and again.

### `==` vs `.equals()`

Your note includes this and it belongs here. The pool examples in the PDF make it clear why `==` can sometimes appear to work for literals: two references may point to the same pooled object.
But content comparison should still use `.equals()` because `==` is identity comparison, not value comparison.

### Example

```java
String a = "test";
String b = "test";
System.out.println(a == b);      // true, pooled
System.out.println(a.equals(b)); // true
```

### Example

```java
String a = new String("test");
String b = new String("test");
System.out.println(a == b);      // false, different objects
System.out.println(a.equals(b)); // true
```

## Key ideas to remember

- `String` is a `java.lang.String` object representing a sequence of characters.
- Strings are immutable, so operations create new objects instead of changing old ones.
- Literal strings use the **String Pool**, while runtime-created strings go to the heap.
- Only compile-time constants are pooled automatically.
- Modern strings use `byte[] value`, `byte coder`, and cached `hash` for optimization.
- `coder = 0` uses Latin1-style compact storage, and `coder = 1` uses UTF16-style wider storage.
- Repeated concatenation creates intermediate objects, which is why loops with `s = s + ...` are expensive.

### Minimal self-test

1. Why does `String s1 = "Ja" + "va"; String s2 = "Java";` make `s1 == s2` true?
2. Why does `String s = "Hello"; s = "world";` not violate immutability?
3. What does the `coder` field represent inside `String`?
4. Why is `hash` cached?
5. Why is concatenation inside a loop expensive for strings?

## What to learn next

The best next topics after this note are **`StringBuilder`**, because it solves the repeated-concatenation problem; **`equals()` vs `==`** across objects in general; and **interning / pooling behavior**, because string pooling is your first real example of JVM-level object reuse.
