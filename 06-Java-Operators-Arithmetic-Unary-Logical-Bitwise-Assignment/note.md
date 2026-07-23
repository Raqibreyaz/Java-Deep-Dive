# Java Operators and Bitwise/Shift Semantics

## Hook

Programming is not just math; it is math plus rules about types, memory, and evaluation order. Operators are the tiny symbols that let you control all three.

Once these rules click, bit tricks, boolean logic, and precedence stop feeling random and start feeling mechanical.

## What it is

Operators are symbols that tell Java to perform arithmetic, relational, logical, bitwise, assignment, or conditional work on values. The PDF covers arithmetic, relational, bitwise, logical, assignment, and operator precedence as the main building blocks.

## One-sentence summary

Java operators are the language’s execution vocabulary: they define how values are combined, compared, shifted, and assigned, and they follow strict rules for precedence, promotion, and evaluation order.

## Intuition

Think of operators as verbs and operands as nouns. The expression `a + b` is just shorthand for “add these two values,” while `a < b` asks a question and returns a boolean result.

The important mental model is that Java does not treat all operators equally. It first decides **what kind of operation** is being performed, then applies precedence, then applies type conversion rules, and only then produces the final result.

## Operator groups

The PDF organizes operators into these categories:

- Arithmetic: `+`, `-`, `*`, `/`, `%`, and compound forms like `+=`, `-=`, `*=`, `/=`, `%=`.
- Unary: `++`, `--`, `!`, unary `+`, unary `-`, and type cast.
- Relational: `==`, `!=`, `<`, `>`, `<=`, `>=`.
- Bitwise: `&`, `|`, `^`, `~`, `<<`, `>>`, `>>>`, and compound forms like `&=`, `|=`, `^=`, `<<=`, `>>=`, `>>>=`.
- Logical: `&&`, `||`, `!`.
- Assignment: `=`, plus compound assignment forms.
- Ternary: `condition ? trueValue : falseValue`.

That classification is useful because each group has different rules, especially around type checking and short-circuiting.

## Arithmetic operators

Arithmetic operators behave the way you would expect:

- `+` adds.
- `-` subtracts.
- `*` multiplies.
- `/` divides.
- `%` returns the remainder.

Example:

```java
int a = 10;
int b = 3;

System.out.println(a + b); // 13
System.out.println(a / b); // 3
System.out.println(a % b); // 1
```

The main thing to remember is that arithmetic in Java still respects operand types, so integer division truncates toward zero.

## Pre vs post increment

This is one of the most common operator mistakes. The PDF highlights the difference between postfix and prefix increment, and this distinction matters because the expression result changes depending on when the increment happens.

- `i++` uses the current value, then increments.
- `++i` increments first, then uses the new value.

Example:

```java
int i = 5;
int a = i++; // a = 5, i = 6
int b = ++i; // i = 7, b = 7
```

A good rule: postfix is “use then change,” prefix is “change then use.”

## Relational operators

Relational operators compare values and return a boolean. The PDF shows the core set clearly:

- `==`
- `!=`
- `<`
- `>`
- `<=`
- `>=`

Example:

```java
int a = 5;
int b = 10;

boolean c = (a == b); // false
boolean d = (a < b);  // true
```

Important: `=` is assignment, while `==` is comparison. Mixing them up is a classic beginner bug.

## Logical operators

Logical operators work on booleans:

- `&&` is logical AND.
- `||` is logical OR.
- `!` is logical NOT.

The PDF’s truth tables show that `&&` returns true only when both sides are true, and `||` returns true when at least one side is true.

Example:

```java
boolean ok = (a < b) && (a < c);
boolean any = (a < b) || (a < c);
```

The key feature here is **short-circuiting**: Java may stop early if the answer is already determined.

## Short-circuiting

For `A && B`, if `A` is false, Java does not evaluate `B`. For `A || B`, if `A` is true, Java does not evaluate `B`.

This matters in real code:

```java
if (obj != null && obj.isValid()) {
    ...
}
```

If `obj` is null, the second part is skipped, which prevents `NullPointerException`.

That is why `&&` and `||` are more than logic operators; they are also control-flow tools.

## Bitwise operators

The PDF introduces bitwise operators as direct bit manipulation tools:

- `&` AND
- `|` OR
- `^` XOR
- `~` NOT
- `<<` left shift
- `>>` arithmetic right shift
- `>>>` logical right shift

These operators work on the binary representation of values, which is why they are useful for masks, flags, compression tricks, and low-level performance code.

### Example: `&`

```java
byte a = 2; // 00000010
byte b = 3; // 00000011

int c = a & b; // 2
```

Bitwise AND keeps a bit only when both inputs have `1` in that position.

### Truth table for `&`

- `0 & 0 = 0`
- `0 & 1 = 0`
- `1 & 0 = 0`
- `1 & 1 = 1`

That is exactly the pattern shown in the PDF’s table.

## Type promotion

One of the most important ideas in the PDF is that small integer types are promoted before many bitwise and shift operations. In practice, `byte`, `short`, and `char` are promoted to `int` for the computation.

That means:

```java
byte a = 5;
byte b = 3;

int x = a | b;      // fine
byte y = (byte)(a | b); // needs cast
```

Why? Because `a | b` is computed as an `int`, not as a `byte`.

This is a critical Java rule because it explains many “why does this not compile?” moments.

## Compound assignment

Compound operators like `|=`, `&=`, `^=`, `<<=`, and `>>=` include an implicit cast back to the left-hand side type. The PDF points out this shortcut correctly.

Example:

```java
byte b = 5;
b |= 1;
```

This compiles even though `b | 1` would normally be an `int` expression. The compound assignment does the cast for you.

## Sign extension

The PDF also shows that widening a signed `byte` to `int` preserves the value using **sign extension**, not zero fill.

Example:

```java
byte b = (byte)0x80; // -128
int i = b;
```

The `int` becomes `11111111 11111111 11111111 10000000`, which still represents `-128`.

This matters because Java treats signed bytes as signed values when promoting them.

### Unsigned interpretation

If you want the raw 0–255 interpretation of a byte, use masking:

```java
int x = b & 0xFF;
```

That clears the upper bits after promotion and gives the unsigned value you probably meant.

## Shift operators

Shift operators are where the PDF gets especially interesting. Java’s shift rules are not arbitrary; they are designed around fixed-width machine words.

### Left shift: `<<`

Left shift moves bits to the left and inserts zeros on the right. The PDF shows examples like:

- `5 << 1 = 10`
- `5 << 2 = 20`

This makes left shift useful as a fast multiplication-by-powers-of-two trick.

Example:

```java
int x = 5;
System.out.println(x << 1); // 10
System.out.println(x << 2); // 20
```

### Arithmetic right shift: `>>`

The PDF shows that `>>` preserves the sign bit for negative values. This is called arithmetic right shift.

Example:

```java
byte b = 32;
System.out.println(b >> 1); // 16
```

For negative numbers, the leftmost bit is copied inward so the sign stays negative. That is why `-128 >> 1` becomes `-64`, then `-32`, and so on.

### Logical right shift: `>>>`

`>>>` shifts right and fills the left side with zeros. The PDF identifies this as an unsigned-style right shift.

Example:

```java
int x = -1;
System.out.println(x >> 1);   // -1
System.out.println(x >>> 1);  // 2147483647
```

The key difference is that `>>` preserves sign, while `>>>` does not.

## Shift count masking

A subtle rule from the PDF is that shift counts are masked rather than rejected. For `int`, Java uses only the low 5 bits of the shift amount, which is equivalent to shifting by `shift % 32`. For `long`, it uses the low 6 bits, equivalent to `shift % 64`.

That means:

```java
int x = 1;
System.out.println(x << -1);  // same as x << 31
```

So negative shift counts do not throw an exception in Java; they wrap through masking.

This is one of the most useful “Java is precise but surprising” rules to remember.

## Logical vs bitwise on booleans

Java allows both logical and bitwise-style operators on booleans, but they are not the same:

- `&&` and `||` short-circuit.
- `&` and `|` evaluate both sides.

This matters when the right-hand side has side effects or could fail.

Example:

```java
if (a < b & expensiveCheck()) { ... }
```

`expensiveCheck()` is still called even if `a < b` is false, because `&` does not short-circuit.

## Operator precedence

The PDF includes a precedence table, and the main lesson is that parentheses should be used when clarity matters.

A simplified mental model:

1. Unary operators
2. Multiplicative operators
3. Additive operators
4. Shifts
5. Relational operators
6. Equality operators
7. Bitwise operators
8. Logical operators
9. Ternary
10. Assignment

Example:

```java
int a = b + c * d;
```

Here `c * d` happens before addition.

If you want `b + c` first, write:

```java
int a = (b + c) * d;
```

## Comparison table

| Concept    | Example           | Behavior                |
| ---------- | ----------------- | ----------------------- | ---------------------- | ----------------------------------- |
| Arithmetic | `+`, `*`          | Numeric computation     |
| Relational | `a < b`           | Returns boolean         |
| Logical    | `&&`, `           |                         | `                      | Boolean logic with short-circuiting |
| Bitwise    | `&`, `            | `, `^`                  | Bit-level manipulation |
| Shift      | `<<`, `>>`, `>>>` | Move bits left or right |
| Assignment | `=`               | Stores a value          |
| Ternary    | `?:`              | Compact if-else         |

## Common gotchas

- `=` and `==` are not the same. Assignment vs comparison.
- `i++` and `++i` produce different results in expressions.
- `byte` and `short` expressions are often promoted to `int`.
- `&&` and `||` short-circuit, but `&` and `|` do not.
- `>>` preserves sign, while `>>>` fills with zeros.
- Shift counts are masked, so negative shifts do not throw errors.

## Step-by-step examples

### Example 1: `byte a = 5; a << 1`

- `5` in binary: `00000101`
- Shift left by 1: `00001010`
- Result: `10`

### Example 2: `byte b = -128; b >> 1`

- `-128` is `10000000` in 8-bit two’s complement.
- Shift right arithmetic keeps the sign bit.
- Result becomes `-64`, then `-32`, and so on.

### Example 3: `int x = 1; x << -1`

- Java masks `-1` to `31`.
- So this becomes `1 << 31`.
- Result is `0x80000000`, which is `-2147483648`.

## Minimal self-test

1. What is the difference between `a = b` and `a == b`?
2. Why does `i++` differ from `++i`?
3. Why does `byte r = a | b;` often fail to compile?
4. What is the difference between `>>` and `>>>`?
5. Why does `1 << -1` not throw an error in Java?

## What to learn next

1. **Type casting** — widening vs narrowing conversions.
2. **Binary representation** — two’s complement and bit masks.
3. **Control flow** — how logical operators influence `if` conditions.
4. **Low-level data structures** — bitsets, flags, and packed encodings.
