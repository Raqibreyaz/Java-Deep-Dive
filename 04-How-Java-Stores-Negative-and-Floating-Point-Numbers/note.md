Here is a revised note **directly based on your PDF**, but cleaned up, better structured, and explained more deeply. The content below follows the PDF’s sequence: negative integers via two’s complement, then floating-point via IEEE 754, then the precision issue with `0.7`, then the float-vs-double-vs-BigDecimal comparison.

# Java Number Representation: Integers, Floats, and Precision

## Hook

Why does `0.1 + 0.2` sometimes fail to equal `0.3` exactly?  
Because computers store numbers in binary, and many decimal fractions cannot be represented perfectly in a finite number of bits.

That single fact explains a lot of “weird” bugs in memory, arithmetic, and finance.

## What it is

This note is about how Java represents numbers at the bit level. Integers use **two’s complement** to represent signed values efficiently, while floating-point numbers use **IEEE 754**, which gives a large range of values but not exact decimal precision for every number.

## One-sentence summary

Java stores signed integers using two’s complement and stores floating-point numbers using IEEE 754, which means integers are exact within range but floats and doubles can lose precision for some decimal values.

## Intuition

Think of integers like an odometer: when the display reaches its limit, it wraps around. That wraparound behavior is what makes two’s complement so useful for signed arithmetic.

Think of floating-point numbers like scientific notation in binary: a number is split into a sign, a power of two, and a fractional part. That gives huge range, but the fractional part is limited, so some decimals become approximations.

## Integers and two’s complement

The PDF starts with an 8-bit `byte`, which can hold values from `-128` to `+127`.
To represent a negative number like `-42`, the process is:

1. Write `42` in binary: `00101010`.
2. Invert the bits: `11010101`.
3. Add 1: `11010110`.

That final bit pattern is how `-42` is stored. The PDF also shows that the most significant bit, or MSB, acts as the sign indicator in this representation: `1` means negative, `0` means positive.

### Example

For `byte b = -42;`, the stored binary is:

```text
11010110
```

If you interpret that using two’s complement rules, it gives `-42`.

## Why two’s complement is used

Two’s complement avoids the problem of having both `+0` and `-0`, which older signed-number systems could produce. The PDF highlights this by showing that `-0` collapses back to `0`, which makes arithmetic simpler and cleaner for hardware.

It also makes addition and subtraction easier because the same binary adder can handle both positive and negative values. That is one of the biggest reasons it became the standard.

## Floating-point numbers and IEEE 754

The PDF shows that a 32-bit `float` is split into:

- 1 sign bit.
- 8 exponent bits.
- 23 mantissa bits.

A 64-bit `double` uses:

- 1 sign bit.
- 11 exponent bits.
- 52 mantissa bits.

This is why `double` has more precision than `float`: it simply has more bits available for the fractional part.

## How floating-point storage works

A floating-point number is stored conceptually as:

\[
(-1)^{sign} \times (1 + Mantissa) \times 2^{Exponent - Bias}
\]

The PDF uses this form to explain both `8.125f` and `0.7f`.

### Example: `8.125f`

The PDF converts `8.125` into binary as `1000.001`. Then it normalizes it to the form `1.000001 × 2^3`. After that, it adds the float bias of `127` to get the exponent field `130`, which becomes `10000010` in binary.

So the stored value is built from:

- sign bit = `0`
- exponent = `10000010`
- mantissa = the fractional bits after the leading `1`

This is a great example because it shows the full pipeline from decimal to stored bits.

## Why `0.7f` is not exact

The PDF’s most important floating-point example is `0.7f`. It shows that `0.7` becomes a repeating binary fraction, just like `1/3` becomes repeating decimal in base 10.

That means the computer cannot store `0.7` exactly with a finite number of mantissa bits. Instead, it stores the closest representable binary value, which is slightly off.

So when you print a `float` approximation of `0.7`, you may see something like:

```text
0.699999988...
```

The PDF uses this to show that the error is not a Java bug. It is a base-conversion limitation.

## Bias and exponent

The PDF explains the bias idea very clearly:

- Float exponent bias = `127`.
- Double exponent bias = `1023`.

Why bias at all?  
Because exponent bits need to represent both positive and negative powers of two, and bias lets the stored exponent stay non-negative while still encoding negative real exponents.

That is why the exponent field for `8.125f` stores `130`, which later becomes `130 - 127 = 3`.

## Precision spectrum

The PDF’s comparison can be cleaned up like this:

| Type         |     Size |   Approximate precision | Use case                                       |
| ------------ | -------: | ----------------------: | ---------------------------------------------- |
| `float`      |  32 bits |  about 7 decimal digits | graphics, memory-sensitive numeric work        |
| `double`     |  64 bits | about 15 decimal digits | general-purpose real-number calculations       |
| `BigDecimal` | variable | exact decimal precision | finance, currency, high-precision decimal math |

The key trade-off is simple: `float` and `double` are fast because hardware supports them directly, but they are inexact for many decimal fractions. `BigDecimal` is exact for decimal math, but slower and heavier because it is object-based.

## Gotchas

- Do not use `==` for floating-point equality if exactness matters. Use a tolerance check instead.
- Do not use `float` for money unless you fully understand the error model.
- Do not assume every bit pattern with MSB `1` is negative in every context; that rule applies to signed integer interpretation, not all binary data.

### Better equality pattern

```java
Math.abs(a - b) < 0.00001
```

This checks “close enough” rather than “bitwise identical.”

## Step-by-step example

### Two’s complement: `-42`

1. `42` in binary = `00101010`
2. Flip bits = `11010101`
3. Add 1 = `11010110`

That stored value represents `-42`.

### Floating point: `8.125f`

1. Decimal `8.125`
2. Binary `1000.001`
3. Normalize to `1.000001 × 2^3`
4. Bias the exponent: `3 + 127 = 130`
5. Store sign, exponent, mantissa

This is why the PDF is useful: it does not just say “floats are approximate,” it shows why.

## Why this matters in practice

If you are writing code for graphics, simulations, or machine learning, `float` can be a good speed/memory trade-off. If you are writing general numeric code, `double` is usually safer. If you are writing banking or billing logic, `BigDecimal` is the right tool because the exact decimal value matters more than raw speed.

## Questions to think about

1. Why does two’s complement make addition easier than sign-bit systems?
2. Why does `0.7` repeat in binary even though it looks simple in decimal?
3. Why does `double` reduce error compared to `float` but not eliminate it?
4. When is `BigDecimal` worth the extra cost?

## Key takeaways

- Java integers use **two’s complement** for signed values.
- Java floating-point numbers follow **IEEE 754**.
- The exponent uses a **bias** so negative powers of two can be stored cleanly.
- Decimal fractions like `0.7` may not have an exact binary form.
- `BigDecimal` is the right choice when exact decimal precision matters.

## Minimal self-test

1. What is the 8-bit two’s complement form of `-1`?
2. Why does `0.1 + 0.2` sometimes not equal `0.3` exactly?
3. What are the three fields in IEEE 754 `float`?
4. Why is `BigDecimal` preferred for money?

## What to learn next

1. **Bitwise operators**: `&`, `|`, `^`, `<<`, `>>`.
2. **`BigDecimal` internals**: how exact decimal arithmetic is implemented.
3. **Java memory model**: how primitives and objects are stored in stack vs heap.

If you want, I can now turn this into the same style as your arrays note: more “teaching note” polish, more diagrams, and a slightly more interview-friendly final version.
