# Java File Rules, Wrapper Classes, Abstract Classes, and POJOs

## Hook

Java looks like a pure object-oriented language, but under the hood it balances two worlds: fast primitive values and rich object-based design. That tension is exactly why concepts like file naming rules, wrapper classes, autoboxing, abstract classes, and POJOs exist — they help Java stay both efficient and structured.

## What is it?

This topic combines several Java ideas that often appear together in early language design discussions:

- Why Java ties public class names to file names.
- Why wrapper classes exist beside primitive types.
- How autoboxing and unboxing bridge primitives and objects.
- Why `Integer` caching can make `==` misleading.
- What abstract classes really are.
- What a POJO is and why frameworks like them.

## One-sentence summary

Java uses strict naming rules for public classes, wrapper classes to objectify primitive values, automatic boxing/unboxing to bridge the two worlds, abstract classes to define incomplete blueprints, and POJOs to model clean reusable data structures.

## Why one public class per file?

Java restricts a source file to one `public` top-level class, and the file name must match that class name. Your note connects this to discoverability: when Java compiles and later loads classes, the naming convention removes ambiguity and makes class lookup predictable.

Example:

```java
public class Demo {
    public static void main(String[] args) {
        System.out.println("Hello");
    }
}
```

This must live in:

```text
Demo.java
```

### Why experienced engineers care

This rule is not just style. It creates a stable mapping between:

- source file,
- compiled class,
- public entry point.

That makes codebases easier to navigate, compile, and load mentally.

## Primitives vs wrapper classes

The PDF explicitly maps primitive types to wrapper types:

- `int -> Integer`
- `long -> Long`
- `short -> Short`
- `float -> Float`
- `double -> Double`
- `char -> Character`
- `boolean -> Boolean`

That mapping exists because Java keeps primitives for speed and memory efficiency, but many object-oriented features require actual objects. The PDF frames `Integer` as a class that stores a value and provides methods around it, which is the core idea behind all wrappers.

### Mental model

A primitive is just the raw number.

```java
int x = 10;
```

A wrapper is an object that contains that number plus object behavior.

```java
Integer y = Integer.valueOf(10);
```

The PDF even contrasts a plain `int x = 10;` with an `Integer` object pointing to heap storage for its internal value.

## Why wrappers were needed

Your note adds the important motivation: Java collections and generics work with objects, not primitives. That means something like `ArrayList<int>` is invalid, but `ArrayList<Integer>` works.

That is the real engineering reason wrapper classes matter:

- primitives are efficient,
- wrappers are interoperable with object APIs.

This is also why wrappers show up constantly in enterprise Java, collections, reflection-heavy systems, and serialization code.

## Autoboxing and unboxing

The PDF directly covers both:

- primitive to wrapper: **autoboxing**
- wrapper to primitive: **unboxing**

Examples from the PDF’s model:

```java
int x = 10;
Integer y = x;   // Autoboxing
```

and

```java
Integer x = 10;
int y = x;       // Unboxing
```

The PDF also explains these in explicit method form:

```java
Integer y = Integer.valueOf(x);
int z = y.intValue();
```

So autoboxing and unboxing are really compiler conveniences over regular wrapper method calls.

### Why this matters

This feels magical at first, but it is not free. Boxing creates or reuses wrapper objects, and unboxing extracts the primitive value from the wrapper object model. That extra layer matters in performance-sensitive loops and in null-safety bugs.

## The null unboxing trap

Your note includes a very important gotcha: if a wrapper is `null`, unboxing it throws `NullPointerException`. That follows directly from the fact that unboxing behaves like calling something such as `intValue()` on an object.

Example:

```java
Integer x = null;
int y = x;   // NullPointerException
```

Mental model:

```java
int y = x.intValue(); // x is null
```

This is one of the most common wrapper bugs in real Java code, especially when reading from maps, databases, JSON, or framework-generated objects.

## `new Integer(x)` vs `Integer.valueOf(x)`

The PDF explicitly shows the older style:

```java
Integer y = new Integer(x);
```

and the modern preferred style:

```java
Integer y = Integer.valueOf(x);
```

alongside autoboxing.

Why `valueOf()` is preferred:

- it matches modern Java style,
- it can reuse cached objects,
- it avoids unnecessary object creation for common values.

So your self-test question about `Integer.valueOf(x)` being preferred is exactly the right one.

## Integer caching

The PDF clearly shows that `Integer.valueOf()` caches values from `-128` to `127`.

That means:

```java
Integer x = 100;
Integer y = 100;
System.out.println(x == y); // true
```

can be true because both references may point to the same cached object.

But:

```java
Integer a = 200;
Integer b = 200;
System.out.println(a == b); // false
```

can be false because values outside the cache range may be represented by distinct objects. The PDF demonstrates exactly this contrast between `100` and `200`.

### The equality trap

The PDF directly explains the real rule:

- `==` compares references for wrapper objects.
- `.equals()` compares values.

So this is the safe pattern:

```java
Integer a = 200;
Integer b = 200;

System.out.println(a.equals(b)); // true
```

This is one of those bugs that survives testing because `==` seems to “work” for small integers and then fails later for larger ones.

## Abstract classes

The PDF defines abstract classes as classes that cannot be instantiated directly.

That is the first thing to remember:

```java
abstract class Animal {
}
```

This is legal, but:

```java
new Animal(); // not allowed
```

is not.

### What abstraction is doing here

An abstract class is a half-finished blueprint. It is useful when:

- subclasses are part of the same family,
- some shared structure or behavior should live in one base class,
- but the base class is not complete enough to be used directly.

### Extra info from your note

Your typed note adds several important facts that should not be skipped:

- abstract classes **can have constructors**,
- they **can have static members**,
- they **can have private members**,
- they **can contain final methods**.

That combination is important because many beginners incorrectly think “abstract” means “only abstract methods.” It does not. An abstract class can mix:

- normal methods,
- abstract methods,
- fields,
- constructors,
- static helpers,
- private internal methods.

### Can an abstract class have a constructor?

Yes. Even though the abstract class itself cannot be instantiated directly, its constructor still runs as part of child object creation.

Example:

```java
abstract class Animal {
    Animal() {
        System.out.println("Animal constructor");
    }
}

class Dog extends Animal {
    Dog() {
        System.out.println("Dog constructor");
    }
}
```

When `new Dog()` runs, the parent constructor executes before the child constructor.

### Can an abstract class have `main()`?

Yes, it can have a static `main()` method. “Abstract” prevents direct object instantiation, not class loading or static method existence. So an abstract class can still act as a program entry container if it defines a proper static `main`.

### Can an abstract method be private?

No. A `private` method is not inherited/visible for overriding, while an abstract method is specifically meant to be implemented by subclasses. So `private abstract` is contradictory.

That makes your self-test question a very good one.

### Final methods inside abstract classes

Your note also correctly includes that methods in an abstract class can be marked `final`. That means the base class can force some behavior to remain unchanged while still leaving other behavior abstract or overridable.

This is a strong design tool:

- abstract class gives extension,
- `final` method protects invariants.

## POJOs

The PDF defines POJO as **Plain Old Java Object** and links it to fields, constructors, getters, and setters.

It also distinguishes between simpler getter/setter-driven models and richer domain objects with business logic.

### Core structure

A typical POJO contains:

1. Private fields.
2. Constructors.
3. Getters.
4. Setters.

Example:

```java
class Student {
    private String name;
    private int age;

    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
```

### Why POJOs matter

Your note adds the important practical angle: POJOs are widely used in frameworks because they act as clean data carriers. Frameworks often inspect them reflectively and map them to:

- JSON objects,
- database rows,
- request/response models.

That is why POJOs are everywhere in Spring, Hibernate, REST APIs, and enterprise code.

### POJO nuance: anemic vs rich model

The PDF includes an excellent distinction:

- **Anemic model** → mostly fields, constructor, getters, setters.
- **Rich domain model** → also contains meaningful business logic.

That is a deeper design insight. A POJO does not have to be “dumb,” but many POJOs in practice are mostly data containers.

## Comparison table

| Concept        | Main purpose                    | Typical form                           |
| -------------- | ------------------------------- | -------------------------------------- |
| Primitive      | Raw fast value                  | `int`, `double`, `char`                |
| Wrapper        | Object form of primitive        | `Integer`, `Double`, `Character`       |
| Autoboxing     | Primitive to wrapper conversion | `Integer x = 10;`                      |
| Unboxing       | Wrapper to primitive conversion | `int y = x;`                           |
| Abstract class | Incomplete base blueprint       | cannot instantiate directly            |
| POJO           | Simple reusable Java object     | fields + constructor + getters/setters |

## Gotchas

- `==` is safe for primitives, but dangerous for wrapper value comparison because it checks object identity.
- Cached `Integer` values can make broken code appear correct for small numbers.
- Unboxing a `null` wrapper throws `NullPointerException`.
- `new Integer(x)` is legacy-style compared to `Integer.valueOf(x)` or autoboxing.
- Abstract classes cannot be instantiated directly, but they can still have constructors, static methods, private methods, and final methods.
- A POJO is usually meant to stay simple; stuffing random framework or unrelated business complexity into it often defeats the point.

## Step-by-step examples

### 1. Boxing and unboxing

```java
int x = 10;
Integer y = x;  // boxing
int z = y;      // unboxing
```

Conceptually this behaves like:

```java
Integer y = Integer.valueOf(10);
int z = y.intValue();
```

The PDF shows exactly this relationship.

### 2. Why `==` fails for wrappers

```java
Integer a = 200;
Integer b = 200;

System.out.println(a == b);      // false
System.out.println(a.equals(b)); // true
```

Because `==` compares whether both references point to the same object, while `.equals()` compares the stored numeric value.

### 3. Abstract class constructor

```java
abstract class A {
    A() {
        System.out.println("A created");
    }
}

class B extends A {
    B() {
        System.out.println("B created");
    }
}
```

`new B()` triggers both constructor levels even though `A` itself cannot be instantiated directly.

## Key takeaways

- Public class naming rules create a predictable link between class identity and file identity.
- Wrapper classes exist because Java needs object forms of primitive values.
- Autoboxing and unboxing are automatic conversions built on wrapper methods like `valueOf()` and `intValue()`.
- `Integer` caching explains why `==` is unreliable for wrapper value comparison.
- Use `.equals()` for wrapper value comparison.
- Abstract classes are incomplete blueprints, but they can still contain constructors, static members, private helpers, and final methods.
- POJOs are simple Java objects commonly used as clean data carriers, especially in framework-heavy applications.

## Minimal self-test

1. Why can `Integer x = 200; Integer y = 200; x == y` return `false` while `x.equals(y)` returns `true`?
2. What is the difference between autoboxing and unboxing?
3. Why does unboxing `null` fail?
4. Can an abstract class have a constructor? Can it have `main()`?
5. Why is `private abstract` invalid?
6. What is the difference between an anemic POJO and a rich domain model?

## What to learn next

The best next topics from here are:

1. **Collections and Generics**, because wrappers matter most there.
2. **Interfaces vs abstract classes**, because abstraction design choices start getting real there.
3. **Null-safety and Optional**, because wrapper/reference behavior creates many runtime bugs.
4. **Reflection and frameworks**, because that is where POJOs become central in real Java systems.
