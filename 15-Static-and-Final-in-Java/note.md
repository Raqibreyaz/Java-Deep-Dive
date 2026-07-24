# Java `static` and `final`: Class-Level Sharing and Locking

## Hook

Imagine a university with 10,000 students. If every student object stores the same college name separately, you waste memory repeating the same data again and again. `static` solves that by moving shared data to the class level instead of the object level.

## What is it?

`static` and `final` are keywords that control how members behave in a Java class. `static` means a member belongs to the class itself, while `final` means something cannot be changed, reassigned, or overridden depending on what it is applied to.

## One-sentence summary

`static` makes a member shared across all objects of a class, and `final` locks a variable, method, class, or parameter from further modification in the way Java allows for that kind of member.

## Intuition

Think of `static` as a shared notice board in a classroom. Every student has their own notebook, but the notice board belongs to the class as a whole. If one person changes the board, everyone sees the same updated board.

Think of `final` as a wax seal. Once sealed, the value, behavior, or inheritance relationship cannot be changed in the way that keyword restricts.

## `static` variables

The PDF shows `college` as a `static String` in `Student`.

Example:

```java
class Student {
    String name;
    int age;
    int rollNo;
    static String college;
}
```

This means `college` is shared by all `Student` objects. You can access it using the class name:

```java
Student.college = "IITG";
```

That is the cleanest way to use class-level data.

### Why this matters

If 10,000 `Student` objects all share the same college name, storing one shared copy is more efficient than storing 10,000 duplicate copies. That is the core benefit of `static` fields.

## `static` methods

The PDF also shows a `static` method like `markAttendance()`.

Example:

```java
class Student {
    static void markAttendance() {
        System.out.println("Attendance marked");
    }
}
```

You can call it like this:

```java
Student.markAttendance();
```

or through an object reference, though the class name is the clearer form.

### Important rule

A static method does not belong to any one object, so it cannot use `this`. It also cannot directly access instance variables unless it is given an object reference explicitly.

That is why the PDF says:

- static method can only call other static methods
- static method can only access static variables
- static method has no access to `this`

## Static in memory

The PDF’s diagrams show the shared static field stored separately from individual objects. That means:

- object fields like `name`, `age`, and `rollNo` live inside each object,
- the static field like `college` exists once for the class.

So when you write:

```java
Student s1 = new Student();
Student s2 = new Student();
```

both objects can refer to the same `Student.college` value.

## Why `main()` is static

The `main()` method is static because the JVM needs an entry point it can call before any object exists. If `main()` required an instance, Java would need an object before it could even start the program, which creates a bootstrap problem. `static` avoids that by letting the JVM call the method directly on the class.

## `final` variables

The PDF shows `final double PI = 3.14;` as the constant example.

Example:

```java
class Random {
    final double PI = 3.14;
}
```

A `final` variable can be assigned only once. After that, it cannot be reassigned.

That is why `final` is commonly used for constants.

## `final` and references

A very important nuance: `final` does not always mean the object itself is immutable. It means the reference cannot point to a different object after assignment. If the referenced object is mutable, its internal state can still change.

Example:

```java
final Student s1 = new Student();
s1.name = "Aditya"; // allowed
// s1 = new Student(); // not allowed
```

So `final` locks the reference, not necessarily the object’s internal fields.

## `final` on methods, classes, and parameters

The PDF notes that `final` can be used on:

- variables,
- methods,
- classes,
- parameters.

### Final method

A `final` method cannot be overridden in a subclass.

### Final class

A `final` class cannot be extended.

### Final parameter

A `final` parameter cannot be reassigned inside the method.

These are all different forms of “locking,” depending on the target.

## Comparison table

| Keyword  | What it affects                    | Main idea                                                          |
| -------- | ---------------------------------- | ------------------------------------------------------------------ |
| `static` | Variable or method                 | Belongs to the class, shared across objects                        |
| `final`  | Variable, method, class, parameter | Prevents reassignment, overriding, or extension depending on usage |

## Common gotchas

- A static variable is shared, not duplicated per object.
- A static method cannot use `this`.
- `final` on a reference does not freeze the object itself.
- `static final` is the standard pattern for constants.
- `main()` is static so the JVM can call it without creating an object first.

## Step-by-step example

```java
class Student {
    String name;
    static String college = "IITG";
}
```

Now:

```java
Student s1 = new Student();
Student s2 = new Student();

s1.name = "Aditya";
s2.name = "Rohit";
```

What happens:

1. Two separate `Student` objects are created.
2. Each object gets its own `name`.
3. Both objects share the same `college` field because it is static.

So changing `Student.college` changes what all students see.

## Key takeaways

- `static` makes something class-level instead of object-level.
- `static` variables are shared by all instances.
- `static` methods cannot access `this` directly.
- `final` prevents reassignment or overriding depending on what it is applied to.
- `static final` is the Java pattern for constants.
- `main()` is static because the JVM needs a startup method before any object exists.

## Minimal self-test

1. Can a static method directly call a non-static method? Why or why not?
2. What is the difference between `final int x = 10;` and `static final int X = 10;`?
3. If 1,000 objects exist, how many copies of a static variable exist?
4. Why does `main()` need to be static?
5. What changes are allowed when a reference is declared `final`?

## What to learn next

1. **Inheritance** — how `final` affects method overriding and class extension.
2. **Polymorphism** — why static and instance methods behave differently.
3. **Memory model** — how stack, heap, and class-level storage work together.
