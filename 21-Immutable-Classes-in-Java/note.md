# Immutable Classes in Java

## What it is

An immutable object is an object whose state cannot change after construction. The PDF builds this idea by contrasting a normal `Student` class that has setters and can be modified after creation with an immutable design where the class is locked down and mutation paths are removed.

In practical Java terms, immutability means: once the constructor finishes, the object should remain in the same logical state for the rest of its lifetime. That is why immutability is really a **design discipline**, not just a single keyword.

### One-sentence summary

An immutable class is a class designed so that its objects cannot be changed after they are created, usually by making the class `final`, the fields `private final`, and by preventing mutation leaks through setters or exposed mutable references.

## Intuition

The PDF’s examples imply a very useful contrast: a normal object is something you can keep modifying through setters, while an immutable object is something you must replace rather than edit. For intuition, your “PDF vs Word document” analogy is excellent: a Word file is mutable, while a finalized PDF is effectively read-only.

That mental model matters because many bugs come from not knowing **who changed shared state** and **when**. Immutability removes that entire class of uncertainty by making “change” mean “construct a new object,” not “mutate the old one.”

## Why it matters

The PDF explicitly links immutability to **threads** and **race conditions**, which is one of the strongest reasons engineers care about it. If an object never changes, multiple threads can read it without stepping on each other’s updates.

Your typed note adds the broader engineering benefits, and those should absolutely be kept:

- **Thread safety:** immutable objects avoid many synchronization problems because readers do not compete with writers.
- **Security:** if callers cannot mutate the object, passing it around is safer.
- **Predictability:** debugging gets easier because the object’s state does not drift silently over time.
- **Stable hashing:** immutable objects are ideal as keys in structures like `HashMap`, because their logical value does not change after insertion.

So the core value of immutability is not “style.” It is **control over state over time**.

## Design rules

The PDF gives three explicit rules for immutable objects:

1. Mark the class as `final`.
2. Mark fields as `private` and `final`.
3. Provide **no setters**.

These three rules are the skeleton of immutable-class design.

### 1. Mark the class `final`

The PDF shows that if a normal `Student` class is extendable, a subclass like `CompSciStudent` can override behavior. Marking the class `final` prevents subclassing, which closes one major escape hatch for breaking immutability.

Why this matters: if subclasses were allowed, they could add mutable state or override methods in ways that violate the “unchangeable after construction” guarantee. So `final` is not cosmetic — it seals the contract.

### 2. Mark fields `private final`

The PDF explicitly says immutable-object variables should be `private` and `final`.

- `private` prevents uncontrolled direct access from outside code.
- `final` ensures the field reference or value is assigned once and not reassigned later.

Example:

```java
public final class Student {
    private final String name;
    private final int age;
}
```

This is the minimum shape of an immutable field design.

### 3. Remove setters

The PDF contrasts a mutable object with something like `s1.setName("Rohit")`, which is exactly the kind of mutation immutable design forbids.

If a class has setters, its state can change after construction. That directly breaks immutability. So the rule is simple: **no setter methods**.

### 4. Prefer getters that do not leak mutability

The PDF goes further than the basic three rules by showing that getters can still break immutability when fields point to mutable objects like `College`.

That is the deeper lesson: immutability is not only about whether your own fields are `final`; it is also about whether the objects reachable through those fields can still be mutated from outside.

## Mutable references

This is the most important “gotcha” in the PDF. A `final` reference to a mutable object is **not enough** to make your class immutable. The PDF demonstrates this with a `Student` containing a `College` reference and a mutation path like `s1.getCollege().name = "IITB"`.

That example is crucial because it exposes the difference between:

- “the reference cannot be reassigned,” and
- “the referenced object cannot be changed.”

Those are not the same thing.

### Shallow copy problem

The PDF explicitly labels the broken version as **shallow copy**. In that design, the `Student` simply stores the same `College` reference that came from outside, so both external code and the `Student` object end up pointing to the same mutable heap object.

That means if outside code mutates `College`, the supposedly immutable `Student` appears to change too. So the `Student` was never truly immutable; it just looked immutable on the surface.

## Defensive copying

The PDF’s fix is **defensive copy in constructor and getter**. That is the real heart of immutable-class design when mutable members are involved.

### Constructor copy

Instead of storing the incoming `College` reference directly, the PDF shows constructing a **new** `College` object from its values inside the constructor.

Conceptually:

```java
Student(String name, int age, College college) {
    this.college = new College(college.name, college.addr);
}
```

This means the immutable object owns its own copy rather than trusting an external mutable object.

### Getter copy

The PDF also shows that getters must return a **new copy**, not the original internal reference.

Conceptually:

```java
public College getCollege() {
    return new College(this.college.name, this.college.addr);
}
```

That prevents callers from reaching inside and mutating the object’s internal state indirectly.

### Why both are needed

If you copy only in the constructor but return the real internal object in the getter, immutability still breaks. If you copy only in the getter but store the external reference directly in the constructor, outside code can still mutate the object after construction. The PDF’s design correctly shows that you need defensive copying in **both** places.

## Shallow vs deep copy

Your typed note includes the shallow-vs-deep distinction, and the PDF strongly supports it with its diagrams.

- **Shallow copy** means copying only the reference, so two owners point to the same mutable object.
- **Deep copy** means constructing a new object with equivalent values, so the copy is independent.

That is why the PDF marks the constructor/getter copy approach as the correct **deep copy** direction for preserving immutability.

### Mental model

Shallow copy says: “Here is the same house key.”

Deep copy says: “Here is a newly built house with the same layout.”

For immutability, the second one is what you want whenever mutable nested objects are involved.

## Complete example

Here is the polished version of the pattern shown in the PDF:

```java
final class College {
    private final String name;
    private final String address;

    College(String name, String address) {
        this.name = name;
        this.address = address;
    }

    String getName() {
        return name;
    }

    String getAddress() {
        return address;
    }
}

public final class Student {
    private final String name;
    private final int age;
    private final College college;

    public Student(String name, int age, College college) {
        this.name = name;
        this.age = age;
        this.college = new College(
            college.getName(),
            college.getAddress()
        );
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public College getCollege() {
        return new College(
            this.college.getName(),
            this.college.getAddress()
        );
    }
}
```

This keeps the PDF’s core rule set while making the code cleaner and safer.

## Performance trade-off

Your typed note also correctly says immutability is not free. Defensive copying and creating new objects instead of mutating old ones can increase allocation pressure and garbage collection work.

That said, the trade-off is often worth it in real systems because immutability reduces entire categories of correctness bugs. In engineering, fewer state bugs often matter more than saving a few object allocations.

## Common misconceptions

- “`final` class means immutable.”  
  No. A `final` class can still contain mutable fields or leak internal mutable references.

- “`final` reference means the referenced object cannot change.”  
  No. It only means the reference cannot point somewhere else; the object itself may still mutate.

- “No setters is enough.”  
  No. If getters leak mutable internal objects, the class is still mutable from the outside.

- “Immutability only matters for multithreading.”  
  No. Threads are a major reason, and the PDF mentions race conditions explicitly, but immutability also improves reasoning, testing, and API safety.

## Key takeaways

- Immutable means the object’s logical state never changes after construction.
- Mark the class `final` to prevent subclass-based mutation paths.
- Mark fields `private final` to restrict access and reassignment.
- Do not provide setters.
- If a field refers to a mutable object, use **defensive copies** in both constructor and getter.
- Shallow copy breaks immutability; deep copy preserves it.
- Immutability helps with thread safety and race-condition avoidance.

## Minimal self-test

1. If a class is `final` but exposes a public mutable field, is it immutable? Why not?
2. Why does `private final College college;` alone not guarantee immutability?
3. Why must defensive copying happen in both the constructor and the getter?
4. What is the difference between shallow copy and deep copy?
5. Why are immutable objects easier to share across threads?

## What to learn next

The best next topics after this are:

1. **Thread safety and synchronization**, because the PDF already hints at race conditions.
2. **`String` and wrapper classes**, as standard-library examples of immutability.
3. **Collections and immutable views**, because mutable containers create the same defensive-copy problems at larger scale.
4. **Builder pattern**, because it is a common way to construct complex immutable objects cleanly.
