## Rewritten note

# Abstraction and Polymorphism in Java

### Hook

Abstraction exists because real systems are too complex to expose all at once. The PDF explains this with examples like a car or an ATM: you use `start()`, `accelerate()`, `deposit()`, or `withdraw()` without needing to see every internal detail behind those actions.

### What is it?

Abstraction is the process of focusing on **what** something does while ignoring **how** it does it. The PDF splits this into two useful ideas: hiding implementation details and separating the public behavior from the internal mechanism.

Polymorphism means **many forms**. The PDF shows that the same operation, such as `run()` or `drive()`, can behave differently depending on the method signature or the actual runtime object.

### One-sentence summary

Abstraction hides unnecessary detail by exposing only the essential interface, while polymorphism allows one common operation to behave differently across different contexts or object types.

## Abstraction

The PDF’s car example is the right mental model: a user interacts with `start()`, `accelerate()`, and `brake()` without caring about the engine internals. That is the essence of abstraction: represent only what is necessary and hide the rest.

In Java, the PDF presents two main tools for high-level abstraction: **abstract classes** and **interfaces**. Abstract classes provide a mix of implemented behavior and abstract behavior, while interfaces represent a pure contract of responsibilities or capabilities.

### Abstract classes

An abstract class cannot be used as a normal concrete blueprint for direct object creation, and it can contain both complete methods and incomplete methods. The PDF’s `Car` example shows this clearly: `start()` may be implemented once, while `accelerate()` and `brake()` are left abstract for subclasses like `EC` and `FuelCar` to define differently.

```java
abstract class Car {
    void start() {
        System.out.println("Car started");
    }

    abstract void accelerate();
    abstract void brake();
}

class ElectricCar extends Car {
    void accelerate() {
        System.out.println("Electric acceleration");
    }

    void brake() {
        System.out.println("Electric braking");
    }
}
```

The important idea is that an abstract class captures a **family** of related objects. The PDF explicitly frames abstract classes as suitable when classes are closely related, such as different kinds of cars.

### Interfaces

The PDF describes an interface as a **contract** and as “pure WHAT.” That means an interface says what capabilities must exist, but not the concrete mechanism used to provide them.

```java
interface Car {
    void start();
    void accelerate();
    void brake();
}
```

This is why interfaces are powerful for modeling roles such as `Flyable`, `Comparable`, or `Serializable`. The PDF emphasizes that interfaces are about responsibilities, capabilities, and roles rather than object family structure.

## Polymorphism

The PDF divides polymorphism into **compile-time polymorphism** and **runtime polymorphism**. That split is the cleanest way to understand why overloading and overriding are related but not the same thing.

### Compile-time polymorphism

Compile-time polymorphism is shown through **method overloading**. The PDF’s `run()` example shows one method name responding differently when the parameter list changes, such as `run()` versus `run(boolean isDogBehind)`.

```java
class Human {
    void run() {
        System.out.println("Normal speed");
    }

    void run(boolean isDogBehind) {
        if (isDogBehind) {
            System.out.println("Faster speed");
        }
    }
}
```

Here, the compiler decides which method to call based on the method signature. So the variation comes from the arguments, not from runtime object dispatch.

### Runtime polymorphism

Runtime polymorphism is shown through **method overriding**. The PDF uses the classic pattern `Animal a = new Dog(); a.run();`, where the reference type is the parent but the executed method comes from the actual child object.

```java
abstract class Animal {
    abstract void run();
}

class Dog extends Animal {
    void run() {
        System.out.println("Dog runs");
    }
}

class Duck extends Animal {
    void run() {
        System.out.println("Duck runs differently");
    }
}
```

This is the core power of OOP design: code can depend on the parent abstraction while the JVM chooses the child-specific implementation at runtime. The PDF presents this as the main runtime form of polymorphism.

## Comparison and gotchas

The PDF makes a useful distinction between **abstraction** and **encapsulation**. Abstraction is about hiding implementation details and separating what from how, while encapsulation is about data security and access control using modifiers.

| Concept       | Main focus                                     | Main mechanism                                         |
| ------------- | ---------------------------------------------- | ------------------------------------------------------ |
| Abstraction   | Hiding complexity, exposing essential behavior | Abstract classes and interfaces                        |
| Encapsulation | Protecting internal data                       | Access modifiers like `private`, `public`, `protected` |

The PDF also highlights an important inheritance rule: Java does **not** support multiple inheritance of classes, but it does support multiple inheritance through interfaces. That is why interfaces are often preferred when a class must combine multiple capabilities without inheriting conflicting concrete behavior.

Another key gotcha from the PDF is that **static methods do not participate in runtime overriding**. If a parent and child both define a static method with the same name, that is method hiding, not true runtime polymorphism.

## Key takeaways

- Abstraction means focusing on what an object does, not how it does it.
- Abstract classes model related families and can contain both implemented and abstract methods.
- Interfaces model contracts, capabilities, and roles.
- Compile-time polymorphism comes from overloading; runtime polymorphism comes from overriding.
- Java allows multiple interfaces but not multiple class inheritance.
- Static methods are not runtime-polymorphic in the same way overridden instance methods are.

### Minimal self-test

1. Why can an interface be called a contract?
2. What is the difference between an abstract class and an interface?
3. How is method overloading different from method overriding?
4. In `Animal a = new Dog();`, why does `a.run()` execute the dog version?
5. Why is `static` method hiding different from runtime overriding?

### What to learn next

The next best topics after this PDF are **method overriding rules**, **interfaces vs abstract classes in real design**, **upcasting and dynamic dispatch**, and **encapsulation vs abstraction with concrete class design examples**.
