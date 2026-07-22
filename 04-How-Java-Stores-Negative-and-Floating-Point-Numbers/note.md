### Hook: The Illusion of Precision
Ever wonder why `0.1 + 0.2` in many programming languages doesn't exactly equal `0.3`? Computers don't "think" in decimals; they operate in a binary world of switches that are either ON or OFF. This transition from our human base-10 intuition to the computer’s binary reality is where most bugs involving memory and precision live.

### What is it?
This is the study of **Number Representation** in Java. It explains how high-level abstractions like `byte`, `float`, and `double` are mapped into physical bits (0s and 1s) inside your RAM.

### One-Sentence Summary
Java represents integers using Two's Complement to handle signed values efficiently, while floating-point numbers follow the IEEE 754 standard, which trades absolute precision for a vast range of scale.

### Intuition: The Binary Clockwork
*   **Integers:** Imagine an odometer. When it reaches its maximum and rolls over, it wraps back to zero or a negative number. This "overflow" is the mechanism for negative numbers.
*   **Floating Point:** Imagine scientific notation ($1.23 \times 10^4$). Computers do this with binary: a fraction (significand) multiplied by a power of two (exponent). Because we have limited bits, we can't represent every possible fraction, leading to rounding errors.

### Part 1: Negative Numbers (Two's Complement)
To represent negatives without needing a dedicated "minus sign" bit that complicates hardware, we use **Two's Complement**.

**The Process:**
1.  **Start with the positive binary** (e.g., $42$ as `00101010`).
2.  **Invert all bits** (1s become 0s, 0s become 1s) — this is the *One's Complement*.
3.  **Add 1** to the result. This is your negative number.

**Why this genius?**
It solves the "Negative Zero" problem. In simpler systems, you get a $+0$ and a $-0$, which is wasteful and confusing. Two's Complement ensures there is only one unique representation for zero.

### Part 2: Floating Point (IEEE 754)
Floating point numbers (`float`, `double`) use three specific fields within their allocated memory:

1.  **Sign bit:** 0 for positive, 1 for negative.
2.  **Exponent:** Stores the power of two, shifted by a **Bias** (127 for float) to handle negative exponents without a sign bit.
3.  **Mantissa (Significand):** Stores the precision digits.

**Why the precision loss?**
Some base-10 fractions like $0.1$ have no finite representation in binary (they become infinite repeating sequences like `0.000110011...`). Since we only have 23 bits (for `float`) or 52 bits (for `double`) for the mantissa, we *must* truncate, causing tiny errors.

### Important Terms
*   **MSB (Most Significant Bit):** The leftmost bit; in Java integers, it defines the sign.
*   **Bias:** A constant (127 for 32-bit floats) added to the exponent so the computer can store negative powers of 2 as positive integers.
*   **Normalization:** Shifting the binary point so the number always starts with `1.xxxx`, allowing us to save bits by not storing that leading `1`.

### Comparison: The Precision Spectrum
| Feature | `float` | `double` | `BigDecimal` |
| :--- | :--- | :--- | :--- |
| **Size** | 32 bits | 64 bits | Arbitrary |
| **Precision** | ~7 decimal digits | ~15 decimal digits | Exact |
| **Performance** | Fast (Hardware) | Fast (Hardware) | Slower (Object-based) |
| **Use Case** | Graphics, ML | Standard Math | Financial/Banking |

### Gotchas
*   **Equality Checks:** Never use `==` with `float` or `double`. Use a small tolerance check: `Math.abs(a - b) < 0.00001`.
*   **Truncation:** Expect errors in long-running financial calculations if using primitive floats.

### Questions to Think About
1.  What would happen if we used the same sign-bit approach for integers as we do for floats? Why is Two's Complement superior for addition/subtraction?
2.  Why is the bias for a 32-bit float exactly 127?
3.  If a `float` cannot represent `0.7` exactly, how does the computer decide which number to store instead?

### Key Takeaways
*   **Integers:** Java uses Two's Complement; the MSB is the sign bit.
*   **Floats:** Use IEEE 754; represented as $\pm 1.Mantissa \times 2^{Exponent-Bias}$.
*   **Precision:** Binary cannot represent all decimal fractions exactly; this is a hardware limitation, not a Java flaw.

### Minimal Self-Test
1.  In an 8-bit signed byte, what is the binary representation of $-1$?
2.  True or False: The leftmost bit in an integer being `1` always indicates a negative number.
3.  Why does the IEEE 754 standard use a 'bias' for the exponent instead of a sign bit?
4.  If you need to calculate interest on a bank account, which Java type should you use and why?

### What to learn next
1.  **Bitwise Operators:** `&`, `|`, `^`, `<<`, `>>`. (Now that you know how bits are stored, you can manipulate them directly).
2.  **`BigDecimal` class:** How Java provides exact precision for financial applications.
3.  **Memory Model:** How objects and primitives reside differently in Stack vs. Heap.