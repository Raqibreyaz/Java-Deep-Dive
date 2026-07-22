# Java Operators & Bitwise/Shift Semantics

## Hook: Beyond Basic Math
Programming is just math with a memory system. If you only know `+` and `-`, you are missing the levers that allow you to manipulate memory at the bit level and control the flow of complex logic. Operators are the vocabulary of computer science.

## What Is It?
Operators are symbols that tell the Java compiler to perform specific mathematical, logical, or bitwise manipulations on data. They are the essential building blocks for evaluating expressions, managing state, and performing conditional checks.

**One-sentence summary:** Operators are the fundamental building blocks of expression evaluation in Java, enabling everything from simple arithmetic to complex bit-level memory manipulation and conditional control flow.

**Intuition:** Think of operators as functions with shorthand syntax. Instead of calling a function like `add(a, b)`, we use `a + b`. They are the "verbs" of your code — they define what action is happening to your data.

## Taxonomy of Operators
1. **Arithmetic:** Basic math (`+`, `-`, `*`, `/`, `%`).
2. **Unary:** Operations on a single operand (`++`, `--`, `!`, `-` as a sign).
3. **Relational:** Comparing values (`==`, `!=`, `<`, `>`, `<=`, `>=`), always returning a `boolean`.
4. **Logical:** Combining conditions (`&&`, `||`, `!`).
5. **Bitwise:** Manipulating bits directly (`&`, `|`, `^`, `~`, `<<`, `>>`, `>>>`).
6. **Assignment:** Updating variables (`=`, `+=`, `*=`, etc.).
7. **Ternary:** A shorthand `if-else` (`condition ? trueVal : falseVal`).

## The "Gotchas": Pre vs. Post Increment
The most common pitfall for beginners is the difference between `i++` and `++i`:

- **Post-increment (`i++`):** Use the current value, then increment.
- **Pre-increment (`++i`):** Increment the value, then use it.

```java
int i = 5;
int a = i++; // a is 5, i is 6
int b = ++i; // b is 7, i is 7
```

## Type Promotion in Bitwise Operations
In Java, type promotion makes bitwise results come out as `int` in many cases, even when both inputs are smaller types like `byte` or `short`. The operation is performed after widening the operands, and you often need an explicit cast to store the result back into a smaller type.

### What Gets Promoted
For bitwise and shift operators, Java applies numeric promotion before evaluating the expression. In practice, `byte`, `short`, and `char` are promoted to `int`, so expressions like `b1 | b2`, `b1 ^ b2`, `~b`, `b << 1`, and `b >> 1` typically produce an `int` result.

### Why This Matters
Because the result is `int`, assigning it back to a `byte` or `short` can fail or require a cast. For example, `byte r = (byte) (b1 | b2);` is valid, while `byte r = b1 | b2;` usually is not, because the expression type is `int`.

### Example
```java
byte a = 5;      // 00000101
byte b = 3;      // 00000011

int x = a | b;   // 7, result type is int
byte y = (byte) (a | b); // 7, cast back to byte
```
Here, `a | b` is computed as an `int`, so `x` is fine, but `y` needs a cast.

### Compound Assignment Shortcut
Compound operators like `|=`, `^=`, `&=`, `<<=`, and `>>=` include an implicit cast back to the left-hand type, which is why `b |= 1;` compiles even though `b | 1` is an `int` expression.

### Subtle Signed-Byte Behavior
A common surprise is that shifting a negative byte can keep sign bits because the byte is first promoted to a sign-extended `int`. If you want to treat a byte as unsigned before bit work, masking with `0xFF` is often necessary.

## Widening Conversion: Sign Extension, Not Zero Fill
Java does not just copy the 8 bits of a byte into a 32-bit int and fill the front with zeros. For signed byte values, it uses sign extension: the top bit is copied into all the new high bits so the numeric value stays the same.

### Why -128 Stays Negative
`byte` in Java is signed two's-complement, so -128 is `10000000` in 8 bits. When it is widened to `int`, Java fills the upper 24 bits with 1s, producing `11111111 11111111 11111111 10000000`, which is still -128 as an `int`. That is the key idea: widening preserves the *value*, not the raw bit pattern interpretation you might expect from unsigned data.

### Example
```java
byte b = (byte) 0x80;   // -128
int i = b;
System.out.println(i);  // -128
System.out.println(Integer.toBinaryString(i));
```
The binary string looks "full of 1s" on the left because the sign bit was extended, not zero-filled.

### Compare With Zero Extension
If Java instead filled the upper bits with 0s, the same pattern would become `00000000 00000000 00000000 10000000`, which is 128 — changing the value. Java avoids this for signed widening conversions.

### If You Want Unsigned Interpretation
If your intent is "treat these 8 bits as 0..255", mask it:
```java
byte b = (byte) 0x80;
int i = b & 0xFF;   // 128
```
`& 0xFF` clears the upper 24 bits after promotion (or removes the sign-extended top bits), keeping only the low 8 bits — the unsigned interpretation you probably expected.

## Shift Count Masking: x << y When y < 0
In Java, the shift distance is masked, not rejected:

- For `int` shifts: only the low 5 bits of `y` are used, so `x << y` behaves like `x << (y & 0x1F)`.
- For `long` shifts: only the low 6 bits are used, effectively `y & 0x3F`.

So:
- `1 << -1` becomes `1 << 31` for `int`, because `-1 & 31 = 31`.
- `1 << -33` becomes `1 << 31` too, for the same reason.

That means negative shift counts do not throw an exception in Java; they wrap through masking.

### Why This Feels Surprising
Two separate rules are interacting:
- `byte` is signed, so widening to `int` uses sign extension.
- Shift counts are masked, so negative `y` values are converted into a valid shift distance.

A useful mental model: Java first converts operands to a working `int`/`long` form, then applies the operator, then stores or casts the result if needed.

## Bit-by-Bit Walkthroughs

### 1) byte -128 → int
```java
byte b = -128;
int i = b;
```
- `b` is `10000000`.
- Java widens to 32 bits.
- Because `b` is signed and negative, the new bits become 1s.
- Result: `11111111 11111111 11111111 10000000` = -128.

### 2) int x = 1; x << -1
```java
int x = 1;
int y = -1;
int z = x << y;
```
- Java uses `y & 31`.
- `-1 & 31 = 31`.
- So `z = 1 << 31`.
- Result: `0x80000000`, which is -2147483648.

### Practical Takeaway
- `byte -> int` preserves sign by sign extension.
- Use `& 0xFF` when you want an unsigned byte value.
- Negative shift counts do not error in Java; they are masked into a legal shift distance.

## Arithmetic (>>) vs Logical (>>>) Right Shift
Java's `>>` is an arithmetic right shift, so it preserves signedness by copying the original sign bit into the new leftmost bits. For a negative `int`, the sign bit is 1, so every shifted-in bit is also 1, keeping the result negative.

### What Actually Happens
Take -8 as a 32-bit two's-complement value:
```
11111111 11111111 11111111 11111000
```
Now do `-8 >> 1`:
```
11111111 11111111 11111111 11111100
```
The rightmost bit is discarded, and a 1 is inserted on the left because the number was negative. That gives -4, so the sign is preserved.

### Why This Works
In two's complement, the top bit is the sign bit. Arithmetic right shift keeps that top bit unchanged by replicating it, which is why negative numbers stay negative and positive numbers stay positive.

### Difference From >>>
Java also has `>>>`, the logical right shift, which always inserts 0 on the left. So for a negative number, `>>>` changes the sign and usually produces a large positive value instead.

```java
int x = -1;
System.out.println(x >> 1);   // -1
System.out.println(x >>> 1);  // 2147483647
```
-1 is all 1s in binary, so `>> 1` keeps filling with 1s, while `>>> 1` fills with 0s.

### Important Nuance
`>>` does not "preserve the exact original number" — it preserves the *sign*. The numeric value still changes because bits are shifted out on the right; only the sign behavior is maintained.

A useful intuition: `>>` behaves like dividing by 2 while rounding toward negative infinity for signed integers, whereas `>>>` treats the bits as unsigned and shifts in zeros.

## Internals: The Truth About Bitwise Shift Operators
Java's shift operators (`<<`, `>>`, `>>>`) behave differently than expected due to type promotion:

- When you perform arithmetic or bitwise operations on `byte` or `short`, Java internally promotes them to `int` (32-bit).
- **Modulo 32 rule:** For `int` types, the shift amount is effectively `shift_amount % 32` (via masking with the low 5 bits). If you shift an integer by 33, it's the same as shifting it by 1.
- **Sign extension:** `>>` (signed shift) preserves the sign bit (MSB), whereas `>>>` (unsigned shift) fills with zeros.

## Logic & Short-Circuiting
The logical `&&` and `||` operators perform short-circuit evaluation:

- In `A && B`, if `A` is `false`, `B` is never evaluated.
- This is critical for performance and preventing `NullPointerException` (e.g., `if (obj != null && obj.isValid())`).

## Comparison: Logical vs. Bitwise Operators

| Operator | Type | Behavior |
| --- | --- | --- |
| `&&` | Logical | Short-circuits; operates on `boolean` |
| `&` | Bitwise | Evaluates both sides; operates on `int`/`boolean` |

## Key Takeaways
- Operators are symbols for functions: they translate directly to CPU instructions.
- Precedence matters: always use parentheses `()` to enforce order rather than memorizing the massive Java precedence table.
- Type promotion: operations on small types (`byte`, `char`) often result in `int` values.
- Assignment is an expression: `a = b = c = 10` is valid because the assignment operator returns the value assigned.

## Questions to Think About
1. Why would you ever use a bitwise `&` instead of a logical `&&`?
2. What happens to the sign bit when shifting a negative number using `>>` versus `>>>`?
3. Why does Java force type promotion to `int` for operations on `byte` and `short`?

## Minimal Self-Test
1. Predict: `int x = 10; x = x++ + ++x;` What is the final value of `x`?
2. Which operator has the lowest precedence in Java?
3. What is the difference between `a == b` and `a.equals(b)` in the context of object references?

## What to Learn Next
- **Control flow:** `if-else`, `switch`, and loops (which rely heavily on relational and logical operators).
- **Type casting:** Understanding widening vs. narrowing conversions.
- **Data structures:** Specifically how bit manipulation helps in designing efficient algorithms.