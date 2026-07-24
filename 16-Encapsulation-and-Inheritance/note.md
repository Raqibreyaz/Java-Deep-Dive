# Java OOP: Encapsulation and Inheritance

## Hook

Imagine a banking system where anyone can directly set `balance` to any number. That would be chaos. OOP solves this by hiding sensitive data behind controlled methods and by reusing behavior through class hierarchies.

## What is it?

**Encapsulation** is the practice of keeping data and the methods that operate on it together inside a class while restricting direct access to the internal state. **Inheritance** is the mechanism by which one class acquires properties and behavior from another class, creating a parent-child relationship.

## One-sentence summary

Encapsulation protects internal state through controlled access, while inheritance lets child classes reuse and extend the behavior of parent classes.

## Intuition

Encapsulation is like a medicine capsule: the useful contents are protected and only exposed in a controlled way. In code, that means fields stay hidden and public methods provide safe interaction.

Inheritance is like family resemblance or DNA: a child class starts with the parent’s common features and then adds its own specifics. That is how you avoid rewriting shared logic again and again.

## Encapsulation

The PDF shows encapsulation with a `BankAccount` example where `balance` should not be directly exposed.

Example:

```java
class BankAccount {
    private double balance;

    public void deposit(double amount) {
        if (amount > 0) balance += amount;
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) balance -= amount;
    }

    public double getBalance() {
        return balance;
    }
}
```

The key idea is simple: the data stays private, and the class provides public methods to interact with it safely.

## Why encapsulation matters

The PDF makes the central point clearly: data and behavior should be together, and data should not be exposed without control.
If callers can change fields directly, you lose the ability to validate values.  
If callers must use methods, you can enforce rules like “deposit must be positive” or “withdrawal cannot exceed balance.”

That is the real power of encapsulation: protection plus validation.

## Access modifiers

The PDF lists the four Java access levels:

- `private`
- default
- `protected`
- `public`

### Private

Visible only inside the same class.

### Default

Visible only inside the same package.

### Protected

Visible inside the same package and in child classes.

### Public

Visible everywhere.

These modifiers are the mechanism that makes encapsulation possible.

## Best practice

The PDF recommends making fields `private` and exposing `public` getters and setters.

That pattern is useful because it lets the class control how its data changes:

- validate input,
- reject bad values,
- keep invariants intact.

Example:

```java
private double balance;

public void setBalance(double balance) {
    if (balance >= 0) {
        this.balance = balance;
    }
}
```

## Encapsulation example

The PDF’s `BankAccount` example is exactly this pattern:

```java
class BankAccount {
    private double balance;

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }
}
```

You do not write `ba.balance = ...` directly. Instead, you call methods like `deposit()` and `withdraw()`.

## Inheritance

The PDF shows inheritance using the `extends` keyword and frames it as an **IS-A** relationship.

Example:

```java
class Student {
    String name;
}

class EngStudent extends Student {
    String labName;
}
```

An `EngStudent` is a `Student`, so it inherits the student fields and can also define new fields of its own.

## Why inheritance matters

Inheritance reduces repetition. If `Student`, `EngineeringStudent`, and `MedicalStudent` all share common data like `name` or `age`, you define those once in the parent class.
That keeps the code DRY and makes shared behavior easier to update.

The PDF also notes that inheritance supports polymorphism, which is one reason it matters beyond code reuse.

## The `super` keyword

The PDF uses `super` for three related purposes:

1. Access parent class variables.
2. Call parent class methods.
3. Call parent class constructors.

### Parent variable

```java
class A {
    int x = 4;
}

class B extends A {
    int x = 5;
    void show() {
        System.out.println(super.x);
    }
}
```

`super.x` refers to the parent version of `x`.

### Parent method

```java
class A {
    void greet() {
        System.out.println("Hello");
    }
}

class B extends A {
    void sayHello() {
        super.greet();
    }
}
```

This is how a child class can reuse or extend parent behavior.

### Parent constructor

The PDF also shows `super()` in a child constructor.

```java
class A {
    A() {}
}

class B extends A {
    B() {
        super();
    }
}
```

This ensures the parent part of the object is initialized before the child part.

## Packages

The PDF says a package is a group of similar classes and interfaces.

Packages help with:

- logical organization,
- naming conflict reduction,
- access control.

That is why `default` access works within the same package and `protected` can extend into subclasses.

## Why Java avoids multiple inheritance

The PDF explains that Java does not support multiple inheritance of classes because of the **diamond problem**.

If two parents provide the same method and a child inherits from both, the compiler may not know which implementation to use. That ambiguity is exactly what Java avoids by not allowing multiple class inheritance.

Java uses interfaces instead when it needs this style of structure without the conflict.

## Comparison table

| Concept      | Encapsulation                   | Inheritance                           |
| ------------ | ------------------------------- | ------------------------------------- |
| Goal         | Protect data and control access | Reuse and extend behavior             |
| Core idea    | Bundle data with methods        | Child class derives from parent class |
| Main keyword | `private`, `public`             | `extends`, `super`                    |
| Benefit      | Safety and validation           | Reuse and hierarchy                   |

## Common gotchas

- Making fields public breaks encapsulation.
- Making everything private without methods makes the class unusable.
- A private field is not directly accessible in a child class.
- Inheritance is not just code reuse; it also creates a type relationship.
- Java avoids multiple inheritance of classes because of ambiguity in method resolution.

## Step-by-step example

```java
class BankAccount {
    private double balance;

    public void deposit(double amount) {
        if (amount > 0) balance += amount;
    }

    public double getBalance() {
        return balance;
    }
}
```

### What happens

1. `balance` cannot be accessed directly from outside the class.
2. `deposit()` becomes the controlled entry point.
3. `getBalance()` provides read-only access.
4. The class preserves its own rules.

That is encapsulation in action.

## Key takeaways

- Encapsulation keeps data and behavior together while hiding internal state.
- Use `private` for fields and `public` methods for safe access.
- Inheritance uses `extends` to create IS-A relationships.
- `super` accesses parent members and constructors.
- Java avoids multiple inheritance of classes to prevent the diamond problem.

## Minimal self-test

1. Why does `private` help protect data?
2. What does a child class gain from inheritance?
3. Can a private field be accessed directly in a subclass? Why or why not?
4. Why does Java avoid multiple inheritance of classes?
5. What is the difference between `this` and `super`?

## What to learn next

1. **Polymorphism** — how one interface can produce different behavior.
2. **Abstraction** — how interfaces and abstract classes describe “what” without exposing “how.”
3. **Method overriding** — how child classes customize inherited behavior.
