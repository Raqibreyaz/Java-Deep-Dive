# Java Enums: Beyond Constants

## Problem and idea

Before enums, fixed choices were often modeled with raw constants like `int` or `String`, which made invalid values easy to pass around because the type system could not strongly distinguish “any integer” from “one valid status.”  
An enum solves that by creating a **real type** with a fixed set of valid values, and the PDF shows this through examples like `Direction` and `DayOfWeek`, where only predefined constants such as `NORTH` or `MONDAY` are allowed.

The key mindset shift is this: an enum is not “just a nicer constant list.” The PDF explicitly shows that each enum is actually a class, extends `java.lang.Enum`, and each constant is a `static final` object of that enum type.

## What the compiler makes

The PDF’s most important idea is the compiler transformation. A source enum like `enum Direction { NORTH, SOUTH, EAST, WEST }` is conceptually turned into a final class `Direction extends Enum<Direction>` with `public static final` objects such as `NORTH`, `SOUTH`, `EAST`, and `WEST`, plus a private constructor so no outside code can create more instances.

That explains several rules all at once: you cannot do `new Direction()`, an enum cannot extend another class because it already extends `Enum`, and enum constants are singleton-like predefined objects rather than arbitrary values.

Example:

```java
enum Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST
}
```

Conceptual compiler view:

```java
final class Direction extends Enum<Direction> {
    public static final Direction NORTH = new Direction();
    public static final Direction SOUTH = new Direction();
    public static final Direction EAST  = new Direction();
    public static final Direction WEST  = new Direction();

    private Direction() {}
}
```

## State and behavior

The PDF does not stop at plain constants; it explicitly shows that enums can have **state** through fields and constructors, and **behavior** through methods. The `Direction` example stores degrees like `NORTH(0)`, `EAST(90)`, `SOUTH(180)`, and `WEST(270)`, then exposes that data through `getDegrees()`.

Example:

```java
enum Direction {
    NORTH(0),
    EAST(90),
    SOUTH(180),
    WEST(270);

    private final int degrees;

    Direction(int degrees) {
        this.degrees = degrees;
    }

    public int getDegrees() {
        return degrees;
    }
}
```

Usage:

```java
Direction d = Direction.NORTH;
System.out.println(d.getDegrees()); // 0
```

The PDF also shows an even more advanced pattern: each enum constant can override behavior individually. In its `move()` example, `NORTH` prints “Moving Up,” `SOUTH` prints “Moving Down,” `EAST` prints “Moving Right,” and `WEST` prints “Moving Left,” which is a powerful way to attach behavior directly to each constant instead of scattering `switch` logic elsewhere.

Example:

```java
enum Direction {
    NORTH {
        @Override
        public void move() {
            System.out.println("Moving Up");
        }
    },
    SOUTH {
        @Override
        public void move() {
            System.out.println("Moving Down");
        }
    },
    EAST {
        @Override
        public void move() {
            System.out.println("Moving Right");
        }
    },
    WEST {
        @Override
        public void move() {
            System.out.println("Moving Left");
        }
    };

    public abstract void move();
}
```

Usage:

```java
Direction.NORTH.move(); // Moving Up
Direction.WEST.move();  // Moving Left
```

## Built-in functionality

The PDF explicitly lists the existing enum functionality as `values()`, `valueOf(String)`, `name()`, and `ordinal()`. It also shows that `values()` is compiler-generated using an internal array like `$VALUES`, and that the method returns a clone rather than the original array.

That clone detail matters because it protects the enum’s internal constant list from external modification. The PDF also shows `Direction.valueOf("EAST")`, which converts a string into the matching enum constant, while `name()` returns the constant name and `ordinal()` returns its position.

Example:

```java
for (Direction d : Direction.values()) {
    System.out.println(d.name() + " -> " + d.ordinal());
}
```

Example:

```java
Direction d = Direction.valueOf("EAST");
System.out.println(d); // EAST
```

Your typed note added one very important warning that fits perfectly here: `ordinal()` should not drive business logic. If you insert or reorder constants, ordinals shift silently, so code that depended on positions breaks without obvious compiler errors.

## Use cases and gotchas

The PDF names practical enum use cases such as **day of the week**, **payment status**, **order status**, and log levels like **INFO, ERROR, DEBUG**. Those are exactly the kinds of domains where the set of valid values is fixed and type safety matters.

Examples:

```java
enum PaymentStatus {
    PENDING,
    SUCCESS,
    FAILED
}
```

```java
enum LogLevel {
    INFO,
    ERROR,
    DEBUG
}
```

A few gotchas matter a lot:

- Use `==` for enum comparison because enum constants are unique predefined instances, so identity comparison is the natural and safe choice here.
- Do not use `ordinal()` as business meaning; position is an implementation detail, not stable domain data.
- You cannot instantiate an enum with `new` because the constructor is private and the compiler controls the only valid instances.
- An enum cannot extend another class because it already extends `java.lang.Enum`, though it can still carry fields, methods, and rich behavior.

Example:

```java
Direction a = Direction.NORTH;
Direction b = Direction.NORTH;

System.out.println(a == b); // true
```

## Key takeaways

Enums are real classes with a fixed, compiler-controlled set of instances, not glorified integers. The PDF shows that each constant is a `static final` object, enums extend `java.lang.Enum`, and they come with built-in functionality like `values()`, `valueOf()`, `name()`, and `ordinal()`.

The deeper lesson is that enums give you **type safety plus object power**: they can hold state, expose methods, and even implement constant-specific behavior like `move()`. The best way to think about them is “a tiny closed object system,” not “named numbers.”

### Minimal self-test

1. Why is `Direction.NORTH` safer than using `int NORTH = 0`?
2. Why can’t you write `new Direction()`?
3. Why is `==` the preferred comparison for enums?
4. Why is `ordinal()` dangerous for business logic?
5. How does constant-specific overriding reduce the need for external `switch` statements?
