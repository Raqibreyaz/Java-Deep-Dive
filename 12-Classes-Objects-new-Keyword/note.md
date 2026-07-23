# Java OOP: Classes, Objects, and Memory

## Hook

Imagine building a banking system with only loose variables like `name1`, `age1`, `salary1`, `account1`, then repeating that pattern for millions of customers. The code would become a mess of disconnected values, and every function would need to juggle too many unrelated arguments. OOP exists to stop that chaos by bundling related data and behavior into one unit.

## What is it?

Object-Oriented Programming is a way of designing software around **objects**: bundles of state (data) and behavior (methods) that model real things or conceptual entities. The PDF shows this by contrasting scattered student variables with a `Student` class that groups `name`, `age`, `rollNo`, and `college` together.

## One-sentence summary

OOP organizes software as interacting objects so that data and the operations on that data stay together instead of being scattered across unrelated functions and variables.

## Intuition

A **class** is like a blueprint, and an **object** is the actual house built from that blueprint. The blueprint defines what every house has, while each real house can hold different furniture, occupants, and details.

That is the core idea of OOP: define structure once, then create many instances from it.

## Why OOP exists

The PDF starts with the exact pain point: if a student is represented using separate variables, you quickly need many independent values just to describe one entity.
That makes function calls awkward too, because you have to pass every field separately.
As systems scale, this becomes brittle: one bug in argument order or missing data can break the logic. OOP solves this by keeping related data and behavior together.

## Class vs object

A **class** is a user-defined data type, a template that describes what an object should contain and what it can do.
An **object** is a runtime instance created from that class.

Example from the PDF:

```java
class Student {
    String name;
    int age;
    int rollNo;
    String college;
}
```

This class says, “A student has these fields.” It does not create a student by itself.

## Memory view

The PDF shows the most important runtime distinction:

- The **reference variable** lives on the stack.
- The **actual object** lives on the heap.

Example:

```java
Student s1 = new Student();
```

What happens conceptually:

1. `s1` is created as a reference variable.
2. `new Student()` allocates object memory on the heap.
3. `s1` points to that heap object.

ASCII view:

```text
Stack                    Heap
+------+                 +--------------------+
| s1 --|---------------> | Student object     |
+------+                 | name               |
                         | age                |
                         | rollNo             |
                         | college            |
                         +--------------------+
```

This is the key to understanding object behavior in Java. The variable is not the object; it is a reference to the object.

## The `new` keyword

The PDF correctly highlights that `new` is what actually creates the object at runtime.
This is dynamic memory allocation in the heap.

So:

```java
Student s1;
```

does **not** create an object yet. It only declares a reference variable.

But:

```java
s1 = new Student();
```

creates the object and connects `s1` to it.

That difference matters a lot in real Java code.

## Fields and methods

The PDF divides a class into two kinds of members:

- **Characteristics / data** → fields like `name`, `age`, `rollNo`, `college`.
- **Behaviors** → methods like `markAttendance()` or `printDetails()`.

That is one of the strongest mental models in OOP: a class contains both what something **is** and what it **does**.

Example:

```java
class Student {
    String name;
    int age;
    int rollNo;
    String college;

    void printDetails() {
        System.out.println(name + " is " + age);
    }
}
```

## Using the dot operator

The dot operator is the bridge between a reference and its object members. The PDF shows examples like `s1.name` and `s1.markAttendance()`.

Example:

```java
Student s1 = new Student();
s1.name = "Aditya";
s1.age = 28;
s1.rollNo = 101;
s1.college = "IIT Guwahati";
s1.printDetails();
```

Here, `.` means “go inside this object and access its field or method.”

## Multiple objects

The PDF shows `s1` and `s2` as separate objects.
That is the point of OOP: many objects can be created from one class, and each object has its own state.

Example:

```java
Student s1 = new Student();
Student s2 = new Student();

s1.name = "Aditya";
s2.name = "Rohit";
```

Even though both objects are of the same class, they hold different data.

## Why objects help

The PDF’s first page lists the pain of raw variables:

- too many independent variables,
- too many arguments to pass,
- repeated sets of variables for each new student,
- no authority over data.

OOP fixes this by grouping state and behavior into a single logical unit. That reduces parameter clutter and makes code easier to maintain.

## Naming conventions

The PDF gives a good style rule:

- Classes start with a capital letter: `Student`, `Human`, `BankAccount`.
- Variables and methods use camelCase: `firstName`, `printDetails()`.

This is not just style. It helps readers immediately distinguish types from instances.

## Real-world modeling

The PDF lists examples like:

- Student
- Animal
- BankAccount
- Location

That is the power of OOP: it lets you model concepts that already exist in the real world or in your system design. A `BankAccount` can have balance and deposit/withdraw behavior. An `Animal` can have type and behaviors like `eat()` or `make_sound()`.

## Example: Student

A clearer version of the PDF example is:

```java
class Student {
    String name;
    int age;
    int rollNo;
    String college;

    void markAttendance() {
        System.out.println(name + " marked present");
    }
}
```

And then:

```java
public class Main {
    public static void main(String[] args) {
        Student s1 = new Student();
        s1.name = "Aditya";
        s1.age = 28;
        s1.rollNo = 101;
        s1.college = "IIT Guwahati";
        s1.markAttendance();
    }
}
```

This is exactly the “data + behavior together” idea that the PDF is teaching.

## Common gotchas

- `Student s1;` does not create an object. It only declares a reference.
- `new Student()` creates the actual heap object.
- Two objects from the same class do not share the same state unless you explicitly make state shared.
- A class is a blueprint; the object is the actual instance.
- Class names should use capitalized style; variables and methods should use camelCase.

## Step-by-step example

Suppose you write:

```java
Student s1 = new Student();
s1.name = "Aditya";
s1.age = 28;
```

What happens:

1. Java allocates a `Student` object on the heap.
2. `s1` stores the reference to that object.
3. The `name` field inside that object is set to `"Aditya"`.
4. The `age` field is set to `28`.

That is the simplest accurate mental model for object creation.

## Key takeaways

- OOP solves the problem of scattered data by bundling fields and methods together.
- A class is a blueprint; an object is a runtime instance.
- `new` creates the object in heap memory.
- The reference variable lives separately from the object.
- The dot operator accesses object members.
- Naming conventions help distinguish classes from variables and methods.

## Minimal self-test

1. What is the difference between `Student s1;` and `s1 = new Student();`?
2. Why does OOP reduce the “too many variables” problem?
3. What is the difference between a class and an object?
4. Why does `new` matter so much in Java object creation?
5. If two `Student` objects exist, do they share the same fields automatically?

## What to learn next

1. **Constructors** — to initialize objects automatically when they are created.
2. **Encapsulation** — to control access with `private` and `public`.
3. **Inheritance** — to reuse and specialize class behavior.
4. **Polymorphism** — to understand why the same interface can produce different behavior.
