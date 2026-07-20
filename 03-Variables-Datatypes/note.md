# Java Fundamentals: Variables, Data Types, and Memory

## Hook
Ever wonder how your code actually "remembers" a number or a piece of text while it's running? It isn't magic; it's a precise orchestration of hardware memory, binary representation, and strict language rules that prevent your program from overwriting its own brain.

## What is it?
In Java, a **variable** is a named location in the computer's memory (RAM) used to store data. Java is a **statically-typed** language, meaning you must tell the compiler what *kind* of data (data type) a variable will hold before you use it.

## One-Sentence Summary
Variables are labeled containers for data, and data types are the blueprints that define how much memory that container needs and how the bits inside it should be interpreted.

## Intuition: The Container Model
Imagine a wall of labeled cubbyholes in a post office:
* **The Data Type** is the size of the box (e.g., a tiny box for a single stamp, or a massive crate for a package).
* **The Identifier** is the name written on the label (e.g., `firstNumber`).
* **The Literal** is the actual object you put inside (e.g., the number `5`).

## Internals: Memory and Bits
Java organizes primitive data types by their size in bits, which directly dictates their range (how large or small a number can be):

| Type | Size | Range/Purpose |
| :--- | :--- | :--- |
| `byte` | 8 bits | -128 to 127 |
| `short` | 16 bits | -32,768 to 32,767 |
| `int` | 32 bits | ~ ± 2 billion |
| `long` | 64 bits | Massive values |
| `float` | 32 bits | Precision decimal numbers |
| `double` | 64 bits | High-precision decimal numbers |
| `char` | 16 bits | Unicode characters |
| `boolean`| N/A | `true` or `false` only |

## Important Concepts
* **Identifiers:** The names you give to variables. Must start with a letter, `_`, or `$`. Cannot start with a number.
* **Literals:** The raw values you type in code (e.g., `10` is an integer literal, `10.5f` is a float literal).
* **Keywords:** Reserved words in Java (like `class`, `int`, `if`) that you cannot use as names because they already have a special meaning to the compiler.
* **Unicode vs ASCII:** Unlike older languages using 8-bit ASCII, Java’s `char` uses 16-bit Unicode, allowing it to represent symbols from virtually every language on Earth.

## High-Level Workflow
1. **Declaration:** `int x;` (The compiler reserves 32 bits for `x`).
2. **Definition:** `x = 10;` (The literal `10` is converted to binary and stored in those 32 bits).
3. **Usage:** The program retrieves the bits from the address labeled `x` whenever it encounters that name.

## Gotchas & Common Mistakes
* **The Float Trap:** In Java, all decimal literals are treated as `double` by default. To store one in a `float` variable, you *must* append an `f` (e.g., `float f = 10.5f;`).
* **Boolean Logic:** Unlike C++, you cannot use `0` for `false` or `1` for `true`. Java `boolean` is strictly `true` or `false`.
* **Integer Underflow/Overflow:** If you try to store a number larger than the range of the data type, the value "wraps around" (e.g., adding to the max value of a `byte` makes it negative).

## Performance Note
Choosing the right data type is a balance. While `long` can hold any `int`, using it everywhere consumes more memory. However, on modern 64-bit systems, the performance difference is often negligible compared to the clarity gained by using the semantically correct type.

## Key Takeaways
* **Statically Typed:** Know your type before you compile.
* **Memory Efficiency:** Use the smallest type that fits your data.
* **Binary Awareness:** Everything is stored as 0s and 1s; types tell Java how to read those bits.
* **Reserved Words:** Don't name your variables after language keywords.

## Minimal Self-Test
1. What happens if you declare `float x = 5.5;`? Why?
2. If you need to store the world population, which integer type should you use?
3. What is the difference between an identifier and a literal?
4. Why does Java prefer `double` over `float` for most operations?

## What to learn next
* **Operators:** How to manipulate these variables.
* **Control Flow:** How to make decisions using boolean values.
* **Type Casting:** How to convert between different data types (e.g., `int` to `double`).