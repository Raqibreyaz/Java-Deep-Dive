# Java Interfaces: Contracts, Roles, and Multiple Inheritance of Behavior

## What is it?

An interface defines **what an object can do without telling how it does that**, and the PDF literally contrasts `interface Car { void drive(); }` with a class implementation to make that distinction clear.
That is why the handwritten note calls an interface a **blueprint of behavior** and a **contract**, while a class is a blueprint of an object.

### One-sentence summary

An interface is a behavior contract that lets unrelated classes share the same capabilities, which is why Java can support multiple inheritance of behavior through interfaces without allowing multiple inheritance of class state.

## Core intuition

The most important mental model in the PDF is this split:

```java
class Car {
    void drive() {
        // implementation
    }
}
```

```java
interface Car {
    void drive();
}
```

The class says, “here is the object and here is how it works,” while the interface says, “anything that claims this role must provide `drive()`.”
So interfaces are not mainly about object identity; they are about **roles, functionalities, and can-do relationships**, which the notes illustrate with examples like `Runnable`, `Walkable`, and `Payable`.

### Example: contract vs implementation

```java
interface Payable {
    void pay();
}

class CreditCardPayment implements Payable {
    public void pay() {
        System.out.println("Paid using credit card");
    }
}

class UpiPayment implements Payable {
    public void pay() {
        System.out.println("Paid using UPI");
    }
}
```

Here, `Payable` is the contract, and the two classes are different implementations of the same behavior role.

## Why interfaces exist

The PDF ties interfaces directly to **abstraction** and to Java’s solution for the **diamond problem**. It shows why multiple inheritance of classes is problematic: if two parent classes both define `fun()`, a child inheriting both creates ambiguity about which implementation should run.

With interfaces, that ambiguity is avoided at the state/inheritance level because interfaces define behavior contracts rather than normal inherited object state. The handwritten diagram explicitly shows the class-based diamond problem and then shows the interface version where `A`, `B`, and `C` are interfaces and `D` is the implementing class.

### Diamond problem example

Problematic class-style idea:

```java
class A {
    void fun() { System.out.println("A"); }
}

class B extends A { }
class C extends A { }

// class D extends B, C { } // not allowed
```

Interface-based version:

```java
interface A {
    void fun();
}

interface B extends A { }
interface C extends A { }

class D implements B, C {
    public void fun() {
        System.out.println("D implementation");
    }
}
```

This is the key reason Java allows one class to implement multiple interfaces but not extend multiple classes.

## High-level workflow

The note’s flow can be understood as:

```text
Declare behavior
   ↓
Implement contract in class
   ↓
Use interface type for polymorphism
```

That is the full point of interfaces: define a shared capability once, let many classes implement it differently, and then write code against the interface instead of a concrete class.

### Example: polymorphism through interface type

```java
interface Animal {
    void run();
}

class Dog implements Animal {
    public void run() {
        System.out.println("Dog runs");
    }
}

class Horse implements Animal {
    public void run() {
        System.out.println("Horse runs");
    }
}

public class Demo {
    public static void main(String[] args) {
        Animal a1 = new Dog();
        Animal a2 = new Horse();

        a1.run();
        a2.run();
    }
}
```

This is exactly the design payoff: one reference type, many implementations.

## Interface vs abstract class

The PDF makes a very important distinction: interfaces represent **roles / functionalities / can-do relationships**, while abstract classes represent **families of similar classes** and an **IS-A relationship**.
It illustrates the abstract-class side with `Animal -> Dog, Duck, Elephant`, which means abstract classes are better when classes are part of the same conceptual family and may share state or common base behavior.

### Mental model

- Interface = adjective-like role, such as `Runnable`, `Comparable`, `Walkable`.
- Abstract class = noun-like family root, such as `Animal`.

### Example

```java
interface Flyable {
    void fly();
}

abstract class Bird {
    String name;

    Bird(String name) {
        this.name = name;
    }

    void eat() {
        System.out.println(name + " is eating");
    }
}

class Eagle extends Bird implements Flyable {
    Eagle(String name) {
        super(name);
    }

    public void fly() {
        System.out.println(name + " is flying");
    }
}
```

Here, `Bird` gives family identity, while `Flyable` gives an additional behavior role.

## What interfaces can and cannot have

The PDF says interfaces do **not** have normal instance fields and do **not** have constructors, while abstract classes can have fields and constructors.
It also notes that interface variables are effectively **static and final**, which means they behave like constants rather than per-object state.

### Example: interface constant

```java
interface Config {
    int TIMEOUT = 30; // implicitly public static final
}
```

Using it:

```java
System.out.println(Config.TIMEOUT);
```

That is why the handwritten page marks fields in interfaces with an “X” in the normal-state sense: interfaces are not meant to hold per-instance object data.

### Why no constructor?

Interfaces cannot be instantiated, so they do not need constructors for object initialization. The PDF marks constructors as absent for interfaces and present for abstract classes, which reflects this exact design difference.

## Methods inside interfaces

The PDF presents two ideas together: the traditional rule that interface methods are public contract methods, and the Java 8/9 evolution that added **default**, **static**, and **private** methods.
The best way to reconcile the note is: interface contract methods are public-facing, while newer Java versions also allow helper logic inside the interface through default, static, and private methods.

### Traditional abstract method

```java
interface Car {
    void drive();
}
```

This is the classic interface style: a pure contract method.

### Default method

```java
interface Vehicle {
    void start();

    default void stop() {
        System.out.println("Vehicle stopped");
    }
}
```

A default method lets the interface carry reusable behavior without forcing every old implementation to break.

### Static method

```java
interface MathUtil {
    static int square(int x) {
        return x * x;
    }
}
```

This keeps utility behavior inside the interface namespace.

### Private method

```java
interface Logger {
    default void logInfo(String msg) {
        print("INFO", msg);
    }

    default void logError(String msg) {
        print("ERROR", msg);
    }

    private void print(String type, String msg) {
        System.out.println(type + ": " + msg);
    }
}
```

The PDF explicitly lists private methods under Java 8/9 interface evolution, and the point is to share helper logic between default methods without exposing that helper as part of the public contract.

## Multiple inheritance with interfaces

The PDF marks **multiple inheritance** as allowed for interfaces.
That means one class can wear many roles at once, which is the real superpower of interfaces.

### Example

```java
interface RunnableTask {
    void run();
}

interface Payable {
    void pay();
}

class Employee implements RunnableTask, Payable {
    public void run() {
        System.out.println("Working...");
    }

    public void pay() {
        System.out.println("Salary credited");
    }
}
```

`Employee` now has two roles without inheriting conflicting class state.

## Default-method conflict and resolution

Your note mentions the default-method diamond case, and it fits naturally with the PDF’s diamond-problem discussion. If two interfaces provide the same default method, the implementing class must override it and resolve the ambiguity explicitly.

### Example

```java
interface A {
    default void fun() {
        System.out.println("A");
    }
}

interface B {
    default void fun() {
        System.out.println("B");
    }
}

class C implements A, B {
    @Override
    public void fun() {
        A.super.fun();
    }
}
```

This is the behavior-level version of diamond resolution: the compiler forces the class to decide.

## Functional interfaces

The PDF explicitly links **functional interfaces** to **functional programming** and **lambda expressions**, and shows a single-method interface example `interface A { void fun(); }`.
It also lists examples like `Comparable` and `Predicate`, which are important because they power modern Java APIs.

### Example: classic form

```java
interface Task {
    void execute();
}

class MyTask implements Task {
    public void execute() {
        System.out.println("Task executed");
    }
}
```

### Example: lambda form

```java
interface Task {
    void execute();
}

public class Demo {
    public static void main(String[] args) {
        Task t = () -> System.out.println("Task executed");
        t.execute();
    }
}
```

This is exactly why functional interfaces matter: they are the bridge from OOP-style contracts to concise lambda-based programming.

## Marker interfaces

The PDF’s third page covers **marker interfaces** and gives examples like `Cloneable`, `Serializable`, and `RandomAccess`.
A marker interface has no methods; its purpose is not to define behavior but to attach meaning or permission to a class.

### Example

```java
class Student implements Cloneable {
    @Override
    public Student clone() throws CloneNotSupportedException {
        return (Student) super.clone();
    }
}
```

The PDF connects this directly to `Object.clone()`, showing that implementing `Cloneable` matters for cloning semantics.

### Why marker interfaces matter

A marker interface answers questions like:

- Can this object be cloned?
- Can this object be serialized?
- Does this collection support fast random access?

That is why `Serializable` is not a normal method-heavy interface; it is metadata in type form.

## Compilation and output

The handwritten page also shows a very basic but useful detail: compiling an interface source like `Animal.java` produces `Animal.class`.
That reinforces a subtle point: interfaces are real types in the Java type system, not just comments or abstract documentation.

### Example

```java
interface Animal {
    void run();
}
```

Compilation:

```text
javac Animal.java
```

Output:

```text
Animal.class
```

## Common misconceptions

- “Interfaces are only for abstraction.”  
  They are for abstraction, but the PDF is more precise: they define **contracts, roles, and functionalities**.

- “Interfaces are just weaker abstract classes.”  
  No; abstract classes model families, while interfaces model capabilities.

- “Interfaces cannot contain any implementation.”  
  That was closer to old Java; the PDF explicitly includes default, static, and private methods in modern interfaces.

- “Interfaces can hold normal object state.”  
  No; interface variables are effectively constant-like, not per-instance mutable fields.

## Key takeaways

- An interface defines what an object can do, not how it does it.
- Interfaces are best understood as **contracts / roles / functionalities**.
- Java allows multiple interface implementation because interfaces avoid the normal multiple-inheritance state conflict that classes would create.
- Abstract classes model related families; interfaces model capabilities.
- Interfaces do not have constructors, but modern Java interfaces can include default, static, and private methods.
- Functional interfaces enable lambda expressions, and marker interfaces attach metadata-like meaning to classes.

## Minimal self-test

1. Why is an interface called a **blueprint of behavior** instead of a blueprint of an object?
2. Why can a class implement multiple interfaces but not extend multiple classes?
3. Why can an interface have constants but not normal instance fields?
4. What is the real difference between `Animal` as an abstract class and `Runnable` as an interface?
5. Why does a marker interface like `Cloneable` exist even though it has no methods?
6. How do default methods change the old idea that interfaces are “pure contracts”?

## What to learn next

The most logical next topics are **default-method conflict resolution**, **functional interfaces with lambdas**, **`Comparable` / `Comparator`**, and **Collections interfaces like `List`, `Set`, and `Map`**, because those are where interface-based design becomes concrete in day-to-day Java.
