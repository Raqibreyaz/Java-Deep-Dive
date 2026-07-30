# The Root of Everything: Java `Object` Class

## What it is

The `Object` class, located in `java.lang`, is the ultimate parent of every Java class. The PDF explicitly says every class in Java inherits from `Object`, whether directly or through another parent class.

That is why classes that look unrelated, such as `Student`, `Integer`, and many standard-library types, still share a common minimum set of behaviors. The PDF also connects this to both **inheritance** and **polymorphism**, showing examples like `Object obj = new Student();`.

### One-sentence summary

`Object` is the root of the Java class hierarchy and provides the common methods that let all objects participate in string conversion, comparison, hashing, cloning, runtime type inspection, garbage-collection hooks, and thread coordination.

## Why `Object` matters

Without `Object`, Java would not have one universal base type for treating different classes uniformly. The PDF’s `Object obj = new Student();` example is small, but it represents a huge idea: a common ancestor makes general-purpose APIs and polymorphic behavior possible.

Think of `Object` as the minimum passport every Java object carries. If something is a proper Java object, it inherits the baseline methods defined by `Object`, and that is why the rest of the language and libraries can assume those behaviors exist.

## Core methods

The PDF groups the major `Object` methods into a few clusters: core methods, cloning, garbage collection, and thread coordination. It explicitly lists `toString()`, `equals()`, `hashCode()`, `getClass()`, `clone()`, `finalize()`, `wait()`, `notify()`, and `notifyAll()`.

### `toString()`

The PDF describes `toString()` as the method used to convert an object into its string representation. It also shows the default style as `ClassName@HexCode`, which is why printing an object without overriding `toString()` often produces that format.

That is why overriding `toString()` is such a common practice in real Java code: the default is mechanically useful, but not very human-friendly. If a `Student` object prints as `Student@1a2b3c`, that is legal, but it is not very informative for debugging.

### `equals()`

The PDF says `equals()` compares two objects and returns `true` or `false`, and it explicitly notes that the default implementation compares references. In other words, the inherited default behavior is effectively identity-style comparison rather than logical value comparison.

That is why two separate `Student` objects with the same field values are not automatically considered equal unless you override `equals()`. The PDF’s `Student s1` and `Student s2` example makes exactly this point.

### `==` vs `.equals()`

Your typed note includes this comparison, and the PDF strongly supports it through the default-reference-comparison explanation. `==` checks whether two references point to the same object, while `.equals()` is the method that can be overridden to express logical equality.

That distinction is one of the most important early Java lessons. A surprising amount of collection behavior, testing behavior, and domain-logic correctness depends on understanding it.

### `hashCode()`

The PDF describes `hashCode()` as returning an integer for an object and then states the critical rule: if two objects are equal, their hash codes must also be equal. It also points directly to hash-based collections such as `HashSet`, `HashMap`, and `Hashtable` as places where this rule matters.

The PDF also makes the reverse-rule nuance explicit: two objects having the same hash code does **not** necessarily mean they are equal. That is why hash collisions are allowed, but broken `equals()`/`hashCode()` consistency is not.

### The `equals`-`hashCode` contract

This is the engineering heart of the note. If you override `equals()` to say two objects are logically equal, you must also override `hashCode()` so they produce the same hash code.

If you do not, hash-based collections can misbehave in subtle ways. The object may go into one bucket and later be searched for using another rule, which breaks the collection’s internal assumptions.

### `getClass()`

The PDF says `getClass()` returns the runtime class of an object and shows `s1.getClass().getName()` producing something like `Student`. It also explicitly shows `getClass()` as `final`, meaning subclasses cannot change how runtime class retrieval works.

This is the bridge from ordinary object usage into reflection-style thinking. Once you have the runtime `Class` object, Java can begin describing metadata about the object’s actual type.

## Lifecycle, copying, and threads

The PDF goes beyond the “big three” and includes some of the most foundational but misunderstood methods in `Object`. These are the methods that make `Object` feel less like a simple base class and more like the runtime root of Java’s object model.

### `clone()`

The PDF describes `clone()` as a way to create a copy of an object and connects the default behavior with **shallow copy**. It also states that cloning is only allowed if the object implements `Cloneable`; otherwise, `clone()` throws an exception.

That is an important restriction because Java does not assume every object should be clonable. The PDF even gives examples like database-related objects and threads as cases where blind cloning would be inappropriate.

It also notes that `Cloneable` is an empty interface, which makes it a **marker interface**. That means the interface exists not to define methods, but to signal permission or capability to the runtime.

### Deep copy vs shallow copy

Your typed note adds a useful systems-level clarification here. The default cloning story is shallow-copy oriented, but classes that own mutable nested state often need a custom override if they want a safer deep-copy style behavior. The PDF explicitly hints at this by mentioning overriding for deep copy.

That matters because a copied object that still shares internal mutable references is not really independent in the way many programmers expect.

### `finalize()`

The PDF includes `finalize()` under garbage collection and labels it **deprecated**, **unpredictable**, **unsafe**, and **unreliable**. That is a strong warning, and it reflects how modern Java treats finalization.

So while `finalize()` is historically part of `Object`, it is not a modern resource-management technique you should build design habits around. The presence of a method in `Object` does not mean it is equally recommended today.

### `wait()`, `notify()`, `notifyAll()`

The PDF explicitly groups `wait()`, `notify()`, and `notifyAll()` under threads. These methods are the low-level monitor communication primitives that let threads coordinate through object monitors.

Your typed note is right to call them the backbone of inter-thread communication at the primitive level. Higher-level concurrency utilities exist today, but these methods are still the conceptual foundation beneath many synchronization patterns.

## Arrays and `Object`

The PDF also adds a subtle but important observation: arrays are tied into the object model too. It explicitly shows an array example like `int[] arr = new int[5];` and associates arrays with `Object`.

That is a useful reminder that Java arrays are not just raw language syntax; they participate in the object system as well. This is one of those facts that feels small until you start using reflection, generics boundaries, or polymorphic APIs.

## Example: implementing equality safely

Your typed note includes a good `equals()`/`hashCode()` implementation pattern, and it fits the PDF’s contract discussion well. A safe version looks like this:

```java
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Student s = (Student) o;
    return age == s.age && java.util.Objects.equals(name, s.name);
}

@Override
public int hashCode() {
    return java.util.Objects.hash(name, age);
}
```

This pattern respects the PDF’s core rule that equal objects must have equal hash codes.

## Common gotchas

- Every class in Java inherits from `Object`, even if you do not explicitly write `extends Object`.
- The default `toString()` is not domain-friendly; it is just a basic representation like `ClassName@HexCode`.
- The default `equals()` compares references, not business meaning.
- If `equals()` says two objects are equal, `hashCode()` must agree.
- `getClass()` is final, so subclasses cannot redefine runtime type reporting.
- `clone()` is not universally available as a safe copy tool; it depends on `Cloneable`, and the PDF explicitly warns that not every object should be cloned.
- `finalize()` exists historically but is deprecated and unreliable.
- `wait()`, `notify()`, and `notifyAll()` belong to `Object`, which is why every object can act as a monitor.

## Key takeaways

- `Object` is the root parent of all Java classes.
- It provides shared behavior that makes inheritance and polymorphism possible across the language.
- The most important practical methods are `toString()`, `equals()`, `hashCode()`, and `getClass()`.
- `clone()`, `finalize()`, and the thread methods are also part of the base object contract, though they have very different modern relevance.
- The `equals()`/`hashCode()` contract is essential for hash-based collections.
- Arrays participate in the object world too.

## Minimal self-test

1. Why can `Object obj = new Student();` compile, and what does that reveal about Java’s type hierarchy?
2. Why is the default `equals()` often not enough for domain objects like `Student`?
3. Why must equal objects return equal hash codes?
4. Why is `getClass()` final?
5. Why is `finalize()` considered unsafe and unreliable?
6. Why does `clone()` rely on the `Cloneable` marker interface?

## What to learn next

The best next topics after this note are **HashMap/HashSet internals**, because they make the `equals()`/`hashCode()` contract feel real; **reflection**, because `getClass()` is the entry point into runtime type inspection; and **multithreading**, because `wait()`/`notify()` become much easier once you understand monitors and synchronization.
