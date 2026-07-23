# Java Objects: Memory, References, and Copying

## Hook

An object looks simple from the outside, but inside it is not just fields and values. It also carries metadata, alignment rules, and reference behavior that decide how Java stores, passes, and copies it.

## What is an object internally?

When Java creates an object with `new`, the heap allocation contains more than just your class fields. The PDF breaks object memory into three parts:

- **Object header**.
- **Instance data**.
- **Padding**.

For a `Student` object with fields like `String name`, `int age`, `int rollNo`, and `String college`, the PDF shows the object as a combination of metadata plus field storage, with padding added to keep the final size aligned.

## One-sentence summary

Java objects are heap-allocated structures with headers, field data, and padding, and Java passes object references by value rather than passing the object itself.

## Intuition

Think of a reference variable as a paper card with an address written on it. The actual house is the object on the heap. When you copy the reference, you copy the address, not the house. When you pass the reference into a method, you give the method a copy of that address.

That is why Java feels like call-by-reference sometimes, but is still strictly **pass-by-value**.

## Object layout

The PDF describes object memory like this:

- **Header**: metadata about the object.
- **Fields**: the actual data defined in the class.
- **Padding**: extra bytes added for alignment.

### Header

The header includes:

- **Mark word**: used for locking/synchronization, garbage collection info, and hash-related metadata.
- **Class pointer**: points back to the object’s class metadata.

The PDF treats the header as about 12 bytes in the common case.

### Instance data

This is the actual field content. For example:

```java
class Student {
    String name;
    int age;
    int rollNo;
    String college;
}
```

The PDF shows each field contributing to the object’s data size, and then padding may be added at the end to align the object size.

### Padding

Padding is added so the total object size lands on an alignment boundary, often 8 bytes. The PDF shows examples where 28 bytes becomes 32 bytes after adding 4 bytes of padding.

## Why padding exists

Padding is a performance and hardware-alignment concern. CPUs like memory aligned in chunks, so the JVM may add a few bytes of unused space to make object access simpler and faster.

This means object size is not always just “sum of field sizes.” It is often:

\[
\text{Object size} = \text{Header} + \text{Fields} + \text{Padding}
\]

## Example: object size

The PDF gives a `Student` example:

- Header: 12 bytes
- Fields: 16 bytes
- Total: 28 bytes
- Padding: 4 bytes
- Final size: 32 bytes

This is a very useful mental model for estimating memory cost.

## Pass-by-value

The PDF’s most important conceptual point is that Java is **strictly pass-by-value**. Even when you pass an object, Java passes the value of the reference, not the object itself.

That means the method receives a copy of the address, not the original reference variable.

### Why this matters

If a method changes the object’s fields, the change is visible outside the method because both references point to the same heap object.
But if the method changes the local reference variable itself, the caller’s reference does not change, because the method only got a copy of the reference value.

## Reference example

Suppose:

```java
Student s1 = new Student();
Student s2 = s1;
```

Now both variables point to the same object. If you modify `s2.name`, you also observe that change through `s1`, because there is still only one object.

This is the heart of reference sharing.

## Method passing example

The PDF illustrates the difference between changing fields and changing the local reference inside a method.

If you pass an object into a method and do this:

```java
obj = null;
```

inside the method, the original caller’s reference is unchanged. That proves the method received a copy of the reference value, not the reference variable itself.

## Shallow copy

A shallow copy copies the reference, not the full object. The PDF shows this with assignments like `R3 = R1`.

So after:

```java
Random r3 = r1;
```

both `r3` and `r1` point to the same heap object. Changing one changes what the other sees.

### When shallow copy is useful

Shallow copy is cheap and fast when shared ownership is acceptable.

## Deep copy

A deep copy creates a new object and manually copies the data into it. The PDF shows this with a copy constructor like:

```java
Random(Random r) {
    this.x = r.x;
    this.y = r.y;
}
```

That creates a separate object with the same initial values.

Now changes to one object do not affect the other.

### When deep copy is useful

Deep copy is valuable when independent state is important and shared mutation would be dangerous.

## Copying comparison

| Copy type    | What gets copied               | Result                                 |
| ------------ | ------------------------------ | -------------------------------------- |
| Shallow copy | Reference value                | Two variables point to the same object |
| Deep copy    | Field values into a new object | Two independent objects exist          |

## What about strings?

The PDF asks about immutable `String` behavior, and that is a good question. A shallow copy of a `String` reference still means both variables point to the same `String` object, but because `String` is immutable, the object cannot be changed in place.

So sharing a `String` reference is usually safe.

## Common gotchas

- A reference variable is not the object itself.
- Java is not call-by-reference; it is pass-by-value of the reference.
- Modifying an object’s fields inside a method affects the shared object.
- Reassigning the local parameter inside a method does not change the caller’s variable.
- Object size includes header and padding, not just field sizes.

## Step-by-step example

Consider:

```java
Student s1 = new Student();
Student s2 = s1;
s2.age = 28;
```

What happens:

1. `new Student()` creates one heap object.
2. `s1` points to that object.
3. `s2 = s1` copies the reference value.
4. `s2.age = 28` changes the shared object.
5. Reading `s1.age` also gives `28`.

There is still only **one** object on the heap.

## Key takeaways

- Objects live on the heap and include headers, fields, and padding.
- Java passes references by value, not by reference.
- Shallow copy copies the address; deep copy creates a new object.
- Modifying object fields through any shared reference affects the same heap object.
- Immutable classes like `String` behave safely even when references are shared.

## Minimal self-test

1. If you pass an object into a method and change one of its fields, why does the caller see the change?
2. If `obj1 = obj2`, how many objects are on the heap?
3. Why does setting the local method parameter to `null` not affect the caller’s variable?
4. Why does the JVM add padding to objects?
5. Why is `String` safer than a mutable object in shallow copy situations?

## What to learn next

1. **Garbage collection** — how unreachable objects are cleaned up.
2. **Object identity vs equality** — `==` versus `.equals()`.
3. **Copy constructors and cloning** — practical ways to implement deep copy.
4. **JVM object layout details** — how headers and alignment vary by runtime.
