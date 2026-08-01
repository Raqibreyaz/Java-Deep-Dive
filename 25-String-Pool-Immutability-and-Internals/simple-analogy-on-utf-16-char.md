## Java `char`

`char` in Java is **not a byte**. It is a 16-bit Unicode code unit, so it can directly store characters in the Basic Multilingual Plane, like `A`, `ॠ`, `क`, or `Ω`.

```java
char a = 'A';
char b = 'ॠ';
char c = 'क';
char d = 'Ω';
```

All of these work.

### What `char` cannot do

A single `char` cannot hold every Unicode character. Characters outside the BMP, such as many emoji, need **two `char` values**.

```java
char ch = '🙂';   // not valid as a single char
String s = "🙂";  // valid
```

So the correct mental model is:

- `char` = one UTF-16 code unit.
- `char` can store many Unicode letters.
- `char` cannot store every Unicode character alone.

## `char` vs UTF-8 bytes

This is the confusing part.

If a character like `ॠ` is said to be “3 bytes,” that usually means **UTF-8 encoding**, not Java `char`.

Example:

- In UTF-8 file/network form, `ॠ` may take 3 bytes.
- In Java memory as a `char`, it fits in one 16-bit unit.

So “3 bytes” does **not** mean Java `char` cannot store it.

## Java `String`

A `String` is for **text**, not for raw bytes.

```java
String s1 = "A";
String s2 = "ॠ";
String s3 = "🙂";
```

All three are valid.

### How `String` handles characters

A `String` can hold:

- simple ASCII text,
- Unicode letters like `ॠ`,
- supplementary characters like `🙂`.

Internally, modern Java stores strings in a compact form using bytes plus encoding information. That is an implementation detail. As a programmer, you should think of `String` as a sequence of characters, not as a raw byte array.

## Why `ॠ` works in both `char` and `String`

```java
char ch = 'ॠ';
String s = "ॠ";
System.out.println(ch);
System.out.println(s);
```

This works because `ॠ` is within the range that one Java `char` can represent directly.

## Why `🙂` works only in `String`

```java
String s = "🙂";
System.out.println(s);
```

This works because `String` can represent text that needs more than one `char`.

But this does not:

```java
char ch = '🙂';   // error
```

because one `char` is not enough.

## `char` and `String` together

Think of them like this:

- `char` is for a **single UTF-16 unit**.
- `String` is for **text made of one or more units**.

So:

```java
char x = 'A';     // yes
char y = 'ॠ';     // yes
char z = '🙂';    // no

String s1 = "A";   // yes
String s2 = "ॠ";   // yes
String s3 = "🙂";  // yes
```

## Practical takeaway

Use `char` when:

- you are working with one simple Unicode unit,
- you are processing characters one by one,
- you know the character is in the BMP.

Use `String` when:

- you are handling text,
- you may have emoji or other supplementary characters,
- you want safe general Unicode support.

## One-line summary

`char` can store many Unicode letters, but not every Unicode character; `String` is the safer general text type because it can represent both BMP and non-BMP characters.