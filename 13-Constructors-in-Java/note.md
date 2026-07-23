# Java Constructors: Object Initialization

## Hook

Creating an object without initializing it is like building a house with empty rooms and no wiring. The memory exists, but the object is not yet ready to be used safely or meaningfully. Constructors solve that by turning raw allocation into a valid starting state.

## What is it?

A constructor is a special block of code that runs automatically when an object is created. It has the same name as the class, has no return type, and its job is to initialize the object’s fields. The PDF states these rules directly and shows a `Student()` constructor assigning initial values.

## One-sentence summary

A constructor is the object’s startup routine: it runs at creation time and sets the object into a usable initial state.

## Intuition

Think of a constructor like a factory setup sequence. The raw object memory is the chassis; the constructor installs the engine, dashboard, and other essentials so the object can actually function. Without that setup, you only have allocated memory, not a meaningful object.

## Why constructors matter

The PDF makes the key point that constructors are mandatory in the lifecycle of an object: they define how the object begins its life.
This is important because uninitialized objects are hard to trust. If fields are left at defaults, the object may technically exist but still represent an incomplete or invalid state.

## High-level workflow

The creation path is:

1. `new` allocates memory in the heap.
2. The constructor runs automatically.
3. Fields are assigned initial values.
4. The reference variable on the stack points to the new object.

This is the clean mental model behind object creation in Java.

## Constructor rules

The PDF lists the basic rules:

- Same name as the class.
- No return type.
- Automatically called during object creation.
- Used to initialize the object.
- Can be overloaded.

These are the defining properties that distinguish constructors from ordinary methods.

## Default constructor

If you define no constructor, Java provides a default no-argument constructor. The PDF demonstrates this with an empty `Student()` constructor.

Example:

```java
class Student {
    String name;
    int age;
    int rollNo;
    String college;
}
```

If no constructor is written, Java can still allow:

```java
Student s1 = new Student();
```

The object is created, and fields get default values like `0`, `false`, or `null` depending on type.

## Parameterized constructor

The PDF also shows a parameterized constructor:

```java
Student(String n, int a, int rn, int c) {
    name = n;
    age = a;
    rollNo = rn;
    college = c;
}
```

This lets the caller supply initial values at creation time.

Example use:

```java
Student s1 = new Student("Aditya", 28, 101, "IIT G");
```

That is much better when object state must be known immediately.

## Constructor overloading

The PDF emphasizes that constructors can be overloaded.
That means a class can have multiple constructors with different parameter lists.

Example:

```java
class Student {
    String name;
    int age;
    int rollNo;
    String college;

    Student() {
        name = "Unknown";
        age = 0;
        rollNo = 0;
        college = "Not assigned";
    }

    Student(String name, int age, int rollNo, String college) {
        this.name = name;
        this.age = age;
        this.rollNo = rollNo;
        this.college = college;
    }
}
```

This supports both empty creation and fully initialized creation.

## The `this` keyword

The PDF uses `this` to resolve name ambiguity between fields and parameters.

Example:

```java
Student(String name, int age, int rollNo, String college) {
    this.name = name;
    this.age = age;
    this.rollNo = rollNo;
    this.college = college;
}
```

Here:

- `this.name` means the field inside the current object.
- `name` means the constructor parameter.

That distinction is crucial because otherwise the parameter would shadow the field.

## Constructor chaining

The PDF introduces chaining with `this(...)`. This means one constructor can call another constructor in the same class.

Example:

```java
class Student {
    String name;
    int age;
    int rollNo;
    String college;

    Student() {
        this("Unknown", 0, 0, "Not assigned");
    }

    Student(String name, int age, int rollNo, String college) {
        this.name = name;
        this.age = age;
        this.rollNo = rollNo;
        this.college = college;
    }
}
```

This avoids duplication and keeps initialization logic in one place.

### First-line rule

The PDF correctly notes that `this()` must be the first statement in the constructor.
That is a hard rule in Java, because constructor chaining must happen before any other code in that constructor executes.

## Default values

The PDF’s constructor note also points at default initialization behavior. If a constructor does not assign a field, Java gives primitives and references their default values.

Typical defaults:

- `int` → `0`
- `boolean` → `false`
- object references → `null`

That means an object can exist in a partially initialized state unless the constructor assigns meaningful values.

## Manual constructor calling

The PDF warns that constructors cannot be called like normal methods. You do not write `s1.Student()`.
Constructors are invoked only by `new` or by another constructor via `this()`.

That is because constructors are part of object creation, not regular behavior on an already-created object.

## Heap exhaustion

The PDF also mentions that object creation can fail at runtime if the heap does not have enough space.

Example outcome:

- `new` tries to allocate memory.
- Heap memory is exhausted.
- Java throws `OutOfMemoryError`.

So constructors are not just syntax; they are part of runtime memory allocation.

## Common gotchas

- Adding `void` before a constructor name turns it into a method, not a constructor.
- If you define any constructor, the compiler does not automatically keep giving you the no-arg one in the same way you might expect; if you want both forms, define both explicitly.
- `this()` must be the first line in a constructor.
- Constructors cannot be called with dot syntax like normal methods.
- Heap exhaustion can stop object creation even if the code is valid.

## Step-by-step example

Consider:

```java
Student s1 = new Student("Aditya", 28, 101, "IIT G");
```

What happens:

1. `new Student(...)` requests heap memory.
2. Java allocates the object.
3. The matching constructor is selected.
4. The constructor receives the argument values.
5. `this.name`, `this.age`, `this.rollNo`, and `this.college` are initialized.
6. `s1` stores the reference to that object.

That is the full object birth sequence.

## Key takeaways

- Constructors initialize objects at creation time.
- They have the same name as the class and no return type.
- Java can provide a default constructor if you do not define one.
- Constructors can be overloaded.
- `this` distinguishes fields from parameters.
- `this()` enables constructor chaining.
- Object creation can fail if the heap runs out of memory.

## Minimal self-test

1. What happens if you add `void` to a constructor declaration?
2. Can a constructor call itself directly with `this()`?
3. If a class has only `Student(String name)`, can `new Student()` still work without changes?
4. Where is the object stored, and where is the reference stored?
5. Why must `this()` be the first statement in a constructor?

## What to learn next

1. **Encapsulation** — how to protect initialized fields.
2. **Inheritance and `super()`** — how parent constructors run before child constructors.
3. **Garbage collection** — how Java cleans up objects after they are no longer reachable.
