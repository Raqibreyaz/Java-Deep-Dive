# Java `Optional`: Safe Handling of Missing Values

## The problem with `null`

Suppose a method returns a user name:

```java
String name = getName();
int length = name.length();
```

If `getName()` returns `null`, this line fails:

```java
name.length();
```

Java throws a `NullPointerException`.

The usual solution is to keep adding null checks:

```java
String name = getName();

if (name != null) {
    int length = name.length();

    if (length > 0) {
        System.out.println(name);
    }
}
```

This style becomes much worse when objects are nested.

```java
User user = getUser();

if (user != null) {
    if (user.getAddress() != null) {
        if (user.getAddress().getCity() != null) {
            System.out.println(
                user.getAddress().getCity()
            );
        }
    }
}
```

This is commonly called **null-check hell**. The PDF shows this exact problem with a `User`, `Address`, and `City` chain.

## One-sentence summary

`Optional<T>` is a wrapper that contains either one non-null value or no value, making the possibility of missing data explicit and safer to handle.

## What is `Optional`?

`Optional<T>` is a container object.

It can be in one of two states:

```text
Present:
Optional → value

Empty:
Optional → no value
```

Example:

```java
Optional<String> name =
    Optional.of("Aditya");
```

Conceptually:

```text
Optional<String>
       ↓
    "Aditya"
```

An empty optional looks like:

```java
Optional<String> name =
    Optional.empty();
```

Conceptually:

```text
Optional<String>
       ↓
      empty
```

The PDF models `Optional<T>` as a class that internally holds a value:

```java
class Optional<T> {
    private final T value;
}
```

If the value is `null`, the optional is empty. If the value is non-null, the optional is present.

## Why `Optional` exists

An `Optional` return type communicates a contract:

```java
Optional<String> findUserName();
```

This tells the caller:

```text
A name may exist,
but it may also be missing.
```

A raw return type hides that possibility:

```java
String findUserName();
```

The caller might assume the result is always available and accidentally call a method on `null`.

Optional does not make all nulls disappear. It makes missing values visible and encourages the caller to handle both cases.

## Creating Optional objects

### `Optional.empty()`

Use `empty()` when there is no value.

```java
Optional<String> name =
    Optional.empty();
```

This is the safe Optional equivalent of “no value.”

Do not write:

```java
Optional<String> name = null;
```

That defeats the purpose of Optional and can itself cause a `NullPointerException`.

### `Optional.of(value)`

Use `of()` when you know the value is not null.

```java
Optional<String> name =
    Optional.of("Aditya");
```

This works because `"Aditya"` is non-null.

But this fails:

```java
String name = null;

Optional<String> result =
    Optional.of(name);
```

`Optional.of(null)` throws a `NullPointerException`.

Use `of()` when null would indicate a programming error.

### `Optional.ofNullable(value)`

Use `ofNullable()` when the value might be null.

```java
String name = getName();

Optional<String> result =
    Optional.ofNullable(name);
```

If `name` is non-null:

```text
Optional → value
```

If `name` is null:

```text
Optional → empty
```

Example:

```java
Optional<String> a =
    Optional.ofNullable("Aditya");

Optional<String> b =
    Optional.ofNullable(null);
```

Conceptually:

```text
a → Optional containing "Aditya"
b → Optional.empty()
```

## The Optional state model

```text
Optional<T>
   ├── present → contains a non-null T
   └── empty   → contains no T
```

Optional should never contain `null`.

That is why these are different:

```java
String name = null;
```

```java
Optional<String> name =
    Optional.empty();
```

The first is a null reference. The second is a valid Optional object that clearly represents absence.

## Getting values from Optional

### `get()`

`get()` returns the contained value.

```java
Optional<String> name =
    Optional.of("Aditya");

String value = name.get();

System.out.println(value); // Aditya
```

But this is unsafe:

```java
Optional<String> name =
    Optional.empty();

String value = name.get();
```

It throws `NoSuchElementException`.

Use `get()` only when you already know the Optional is present, or when the surrounding logic guarantees it.

### `isPresent()`

`isPresent()` returns `true` when a value exists.

```java
Optional<String> name =
    Optional.ofNullable(getName());

if (name.isPresent()) {
    String value = name.get();
    System.out.println(value);
}
```

This is safer than directly calling a method on a possibly null reference.

However, repeatedly using `isPresent()` followed by `get()` can still look like old-style null checking. Prefer the functional methods when possible.

### `ifPresent(Consumer)`

`ifPresent()` runs an action only when a value exists.

```java
Optional<String> name =
    Optional.ofNullable(getName());

name.ifPresent(
    value -> System.out.println(value)
);
```

Using a method reference:

```java
name.ifPresent(System.out::println);
```

If the Optional is empty, nothing happens.

Conceptually:

```text
Present → run the Consumer
Empty   → do nothing
```

### `ifPresentOrElse()`

`ifPresentOrElse()` handles both cases.

```java
Optional<String> name =
    Optional.ofNullable(getName());

name.ifPresentOrElse(
    value -> System.out.println("Name: " + value),
    () -> System.out.println("Name is missing")
);
```

The first action runs when present. The second action runs when empty.

## Default values

### `orElse()`

`orElse()` returns the value if present. Otherwise, it returns a default value.

```java
Optional<String> name =
    Optional.empty();

String result =
    name.orElse("Unknown");

System.out.println(result); // Unknown
```

When the value is present:

```java
Optional<String> name =
    Optional.of("Aditya");

String result =
    name.orElse("Unknown");

System.out.println(result); // Aditya
```

### `orElseGet()`

`orElseGet()` receives a `Supplier` that creates the default value only when needed.

```java
String result =
    name.orElseGet(() -> createDefaultName());
```

This is useful when creating the default is expensive.

```java
String result =
    name.orElseGet(() -> {
        System.out.println("Creating default");
        return "Unknown";
    });
```

The supplier is called only when `name` is empty.

### `orElse()` vs `orElseGet()`

```java
name.orElse(createDefaultName());
```

The argument to `orElse()` is evaluated immediately, even if the Optional already contains a value.

```java
name.orElseGet(() -> createDefaultName());
```

The supplier runs only if the Optional is empty.

Use:

- `orElse()` for a simple cheap default.
- `orElseGet()` for a computed or expensive default.

### Example

```java
String result =
    Optional.of("Aditya")
            .orElse(getDefaultName());
```

`getDefaultName()` may still run even though `"Aditya"` is present.

```java
String result =
    Optional.of("Aditya")
            .orElseGet(() -> getDefaultName());
```

Here, `getDefaultName()` is not called because the value exists.

### `orElseThrow()`

`orElseThrow()` throws an exception when the Optional is empty.

```java
String name =
    Optional.ofNullable(getName())
            .orElseThrow();
```

If the value is absent, Java throws `NoSuchElementException`.

### Custom exception

```java
String name =
    Optional.ofNullable(getName())
            .orElseThrow(
                () -> new IllegalStateException(
                    "Name is required"
                )
            );
```

The exception supplier is evaluated only when the Optional is empty.

This is useful when absence is invalid and the method should fail clearly.

## Transformation methods

Optional has methods that work like a very small stream.

A stream can contain:

```text
zero to many values
```

An Optional can contain:

```text
zero or one value
```

The PDF calls Optional a **mini-stream** for this reason.

## `map()`

`map()` transforms the value inside the Optional if it is present.

```java
Optional<String> name =
    Optional.of("Aditya");

Optional<Integer> length =
    name.map(String::length);

System.out.println(length.get()); // 6
```

The type changes:

```text
Optional<String>
      ↓ map()
Optional<Integer>
```

If the original Optional is empty, the result is also empty:

```java
Optional<String> name =
    Optional.empty();

Optional<Integer> length =
    name.map(String::length);

System.out.println(length.isEmpty()); // true
```

The function is not called when the Optional is empty.

### Without Optional

```java
String name = getName();

if (name != null) {
    int length = name.length();
}
```

### With Optional

```java
Optional<Integer> length =
    Optional.ofNullable(getName())
            .map(String::length);
```

This expresses the same logic more cleanly.

## `filter()`

`filter()` keeps the value only when it satisfies a condition.

```java
Optional<String> name =
    Optional.of("Aditya");

Optional<String> result =
    name.filter(value -> value.length() > 3);

System.out.println(result); // Optional[Aditya]
```

If the condition fails:

```java
Optional<String> name =
    Optional.of("Tom");

Optional<String> result =
    name.filter(value -> value.length() > 3);

System.out.println(result); // Optional.empty
```

The flow is:

```text
Present + condition true  → remains present
Present + condition false → becomes empty
Empty                     → remains empty
```

## `flatMap()`

`flatMap()` is useful when the mapping function already returns an Optional.

Suppose:

```java
Optional<User> user =
    Optional.of(new User());
```

And:

```java
Optional<Address> getAddress();
```

This is wrong:

```java
Optional<Optional<Address>> address =
    user.map(User::getAddress);
```

Why? `map()` wraps the returned Optional again.

Use `flatMap()`:

```java
Optional<Address> address =
    user.flatMap(User::getAddress);
```

The difference is:

```text
map:
Optional<User> → Optional<Optional<Address>>

flatMap:
Optional<User> → Optional<Address>
```

### `map()` vs `flatMap()`

Use `map()` when the function returns a normal value:

```java
Optional<String> name =
    user.map(User::getName);
```

Use `flatMap()` when the function already returns an Optional:

```java
Optional<Address> address =
    user.flatMap(User::getAddress);
```

## Nested object example

Suppose the model is:

```java
class User {
    private Address address;

    Address getAddress() {
        return address;
    }
}

class Address {
    private String city;

    String getCity() {
        return city;
    }
}
```

Traditional null-check code:

```java
User user = getUser();

if (user != null) {
    Address address = user.getAddress();

    if (address != null) {
        String city = address.getCity();

        if (city != null) {
            System.out.println(city);
        }
    }
}
```

Optional version:

```java
Optional.ofNullable(getUser())
        .map(User::getAddress)
        .map(Address::getCity)
        .ifPresent(System.out::println);
```

The pipeline is:

```text
User
  ↓ map getAddress()
Address
  ↓ map getCity()
String
  ↓ ifPresent()
Print
```

If any step is missing, the pipeline becomes empty and printing is skipped.

## Optional and streams

A stream can contain many users:

```java
List<User> users;
```

Suppose each user may or may not have an email:

```java
Optional<String> getEmail();
```

The stream then has this conceptual type:

```text
Stream<Optional<String>>
```

Example:

```java
List<String> emails =
    users.stream()
         .map(User::getEmail)
         .filter(Optional::isPresent)
         .map(Optional::get)
         .toList();
```

The flow is:

```text
List<User>
   ↓
Stream<User>
   ↓ map(User::getEmail)
Stream<Optional<String>>
   ↓ filter present values
Stream<Optional<String>>
   ↓ map(Optional::get)
Stream<String>
   ↓ toList()
List<String>
```

A cleaner modern form is:

```java
List<String> emails =
    users.stream()
         .map(User::getEmail)
         .flatMap(Optional::stream)
         .toList();
```

`Optional::stream` converts:

```text
present Optional → one-element stream
empty Optional   → empty stream
```

So it removes missing values naturally.

## Optional as a mini-stream

The comparison is:

| Type          | Number of values |
| ------------- | ---------------: |
| `Stream<T>`   |     Zero to many |
| `Optional<T>` |      Zero or one |

This explains why Optional has methods similar to streams:

```text
Optional:
map()
filter()
flatMap()

Stream:
map()
filter()
flatMap()
```

But Optional is designed for one possible result, not a collection of results.

## Best practices

### Use Optional mainly as a return type

The video recommends using Optional mainly when a method may not find a value.

Good:

```java
Optional<User> findUserById(int id) {
    // return user or Optional.empty()
}
```

This return type tells callers that the user may be missing.

### Avoid Optional fields

Usually avoid:

```java
class User {
    Optional<String> email;
}
```

Prefer:

```java
class User {
    String email;
}
```

Then expose absence through a method if needed:

```java
Optional<String> getEmail() {
    return Optional.ofNullable(email);
}
```

### Avoid Optional parameters

Usually avoid:

```java
void sendEmail(Optional<String> email) {
}
```

Prefer:

```java
void sendEmail(String email) {
}
```

If the parameter is optional, use method overloading or clearly define what `null` means at the boundary.

### Do not return `null` instead of Optional

This is wrong:

```java
Optional<String> getName() {
    return null;
}
```

Return:

```java
return Optional.empty();
```

An Optional-returning method should always return an Optional object.

## Common mistakes

### Calling `get()` without checking

```java
Optional<String> name =
    Optional.empty();

name.get(); // NoSuchElementException
```

Prefer:

```java
name.ifPresent(System.out::println);
```

or:

```java
String result =
    name.orElse("Unknown");
```

### Using `Optional.of()` with nullable data

```java
String name = getName();

Optional<String> result =
    Optional.of(name); // may throw
```

Use:

```java
Optional<String> result =
    Optional.ofNullable(name);
```

### Creating an Optional that is itself null

```java
Optional<String> name = null;
```

This is not an empty Optional. It is a null reference to an Optional object.

Correct:

```java
Optional<String> name =
    Optional.empty();
```

### Using Optional as a replacement for every null

Optional is useful, but it is not necessary for every local variable or every field. It is mainly useful where a missing return value is part of the method’s contract.

## Complete example

```java
import java.util.List;
import java.util.Optional;

class Address {
    private final String city;

    Address(String city) {
        this.city = city;
    }

    String getCity() {
        return city;
    }
}

class User {
    private final String name;
    private final Address address;
    private final String email;

    User(String name, Address address, String email) {
        this.name = name;
        this.address = address;
        this.email = email;
    }

    String getName() {
        return name;
    }

    Optional<Address> getAddress() {
        return Optional.ofNullable(address);
    }

    Optional<String> getEmail() {
        return Optional.ofNullable(email);
    }
}

public class Main {
    public static void main(String[] args) {
        User user =
            new User(
                "Aditya",
                new Address("Pune"),
                "aditya@example.com"
            );

        Optional.of(user)
                .map(User::getAddress)
                .flatMap(value -> value)
                .map(Address::getCity)
                .ifPresent(System.out::println);

        String email =
            user.getEmail()
                .orElse("No email available");

        System.out.println(email);

        List<User> users = List.of(user);

        List<String> emails =
            users.stream()
                 .map(User::getEmail)
                 .flatMap(Optional::stream)
                 .toList();

        System.out.println(emails);
    }
}
```

A simpler address chain, when `getAddress()` returns an ordinary nullable `Address`, would be:

```java
Optional.ofNullable(user)
        .map(User::getAddress)
        .map(Address::getCity)
        .ifPresent(System.out::println);
```

If `getAddress()` already returns `Optional<Address>`, use `flatMap()` for that step instead.

## Key takeaways

- `null` can cause `NullPointerException`.
- Nested null checks create null-check hell.
- `Optional<T>` represents one present value or no value.
- `Optional.empty()` creates an empty Optional.
- `Optional.of(value)` requires a non-null value.
- `Optional.ofNullable(value)` safely handles nullable values.
- `isPresent()` checks whether a value exists.
- `ifPresent()` runs an action only when a value exists.
- `get()` is unsafe when the Optional may be empty.
- `orElse()` gives a direct default.
- `orElseGet()` creates a default lazily.
- `orElseThrow()` fails clearly when a value is required.
- `ifPresentOrElse()` handles both present and empty cases.
- `map()` transforms a present value.
- `filter()` keeps a value only when a condition passes.
- `flatMap()` prevents nested Optionals.
- Optional behaves like a mini-stream of zero or one element.
- Optional is usually best used as a method return type.
- Optional integrates well with streams through `Optional::stream`.

## Minimal self-test

1. What problem does `Optional` try to solve?
2. What is the difference between `Optional.empty()`, `Optional.of()`, and `Optional.ofNullable()`?
3. What happens when you call `get()` on an empty Optional?
4. Why is `orElseGet()` lazy but `orElse()` eager?
5. Rewrite nested null checks using `map()`.
6. When should you use `flatMap()` instead of `map()`?
7. What is the difference between `isPresent()` and `ifPresent()`?
8. Why should an Optional-returning method never return `null`?
9. How does `Optional::stream` help with stream processing?
10. Why is `Optional` usually avoided for fields and method parameters?

## What to learn next

The next useful topics are:

1. `Optional` with `Stream` pipelines.
2. `flatMap()` in both streams and Optional.
3. `orElse()` versus `orElseGet()` in performance-sensitive code.
4. Designing repository methods such as `findById()`.
5. Null-safe API design.
6. Exceptions versus Optional for failure handling.
