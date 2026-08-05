> ## Hook: Why Doesn't `List<Dog>` Become a `List<Animal>`?
>
> In Java, inheritance works between classes (`Dog` is an `Animal`), but **it does not automatically apply to generic types**. Even though a `Dog` is an `Animal`, a `List<Dog>` is **not** a `List<Animal>`. This rule, called **invariance**, exists to preserve type safety. Java solves the flexibility problem using **wildcards (`?`)**, which allow generic types to participate in controlled inheritance without compromising safety. 
>
> ---
>
> # Wildcards, Variance & Type Erasure in Java Generics
>
> ## The Core Problem: Invariance
>
> Consider the following inheritance hierarchy:
>
> ```java
> class Animal {}
>
> class Dog extends Animal {}
> ```
>
> Upcasting works:
>
> ```java
> Dog dog = new Dog();
> Animal animal = dog;
> ```
>
> However, the same rule **does not apply** to generic types.
>
> ```java
> List<Dog> dogs = new ArrayList<>();
>
> List<Animal> animals = dogs;   // ❌ Compile-time Error
> ```
>
> Although `Dog` is an `Animal`, **`List<Dog>` is not a subtype of `List<Animal>`**.
>
> This property is called **Invariance**.
>
> The lecture notes emphasize:
>
> > If `A` is a child of `B`, it **does not mean** `Generic<A>` is a child of `Generic<B>`. 
>
> ---
>
> ## Why Is Java Invariant?
>
> Suppose Java allowed this:
>
> ```java
> List<Dog> dogs = new ArrayList<>();
>
> List<Animal> animals = dogs;
>
> animals.add(new Cat());
> ```
>
> Now the original `dogs` list contains:
>
> ```text
> Dog
> Dog
> Cat   ❌
> ```
>
> Later,
>
> ```java
> for (Dog d : dogs) {
>     d.bark();
> }
> ```
>
> The `Cat` object cannot be treated as a `Dog`.
>
> Type safety breaks.
>
> Therefore Java prevents this assignment entirely.
>
> ---
>
> ## Arrays Are Different (Covariance)
>
> Arrays behave differently.
>
> ```java
> Dog[] dogs = new Dog[10];
>
> Animal[] animals = dogs;
> ```
>
> This is allowed because arrays are **covariant**.
>
> However,
>
> ```java
> animals[0] = new Cat();
> ```
>
> Runtime:
>
> ```text
> ArrayStoreException
> ```
>
> Unlike generics, arrays check element types at runtime, which is why this unsafe operation results in an exception instead of a compile-time error. The lecture contrasts this behavior with the stricter rules of generics. 
>
> ---
>
> # Wildcards (`?`)
>
> A wildcard represents **an unknown generic type**.
>
> ```java
> List<?> list;
> ```
>
> Read it as:
>
> > A list of **some type**, but we don't know which one.
>
> It could be:
>
> ```java
> List<Integer>
> List<String>
> List<Animal>
> List<Dog>
> ```
>
> All are valid.
>
> ---
>
> ## Unbounded Wildcard (`<?>`)
>
> Example:
>
> ```java
> void print(List<?> list) {
>
> }
> ```
>
> Now any list can be passed.
>
> ```java
> print(new ArrayList<Integer>());
> print(new ArrayList<String>());
> print(new ArrayList<Dog>());
> ```
>
> ---
>
> ### Reading from `<?>`
>
> Since the compiler does not know the exact type,
>
> it only guarantees that every element is an `Object`.
>
> ```java
> Object obj = list.get(0);
> ```
>
> This is allowed.
>
> ---
>
> ### Writing to `<?>`
>
> ```java
> list.add("Hello");
> ```
>
> ❌ Not allowed.
>
> Why?
>
> The compiler doesn't know whether the list actually stores:
>
> * Integer
> * Dog
> * String
> * Animal
>
> Therefore inserting any object could break type safety.
>
> The only value that can be added is:
>
> ```java
> list.add(null);
> ```
>
> because `null` is compatible with every reference type. 
>
> ---
>
> # Upper Bounded Wildcards (`? extends T`)
>
> Syntax:
>
> ```java
> List<? extends Animal>
> ```
>
> Means:
>
> > A list containing **Animal or any subclass of Animal**.
>
> Valid:
>
> ```java
> List<Animal>
> List<Dog>
> List<Cat>
> ```
>
> ---
>
> ## Reading
>
> Since every element is guaranteed to be at least an `Animal`,
>
> reading is safe.
>
> ```java
> Animal a = list.get(0);
> ```
>
> Allowed.
>
> ---
>
> ## Writing
>
> ```java
> list.add(new Dog());
> ```
>
> ❌ Not allowed.
>
> Why?
>
> Imagine the actual list is:
>
> ```java
> List<Cat>
> ```
>
> Adding a `Dog` would violate the list's type.
>
> Since the compiler cannot determine the exact subtype, it prevents all additions except `null`.
>
> Therefore:
>
> `? extends` behaves like a **read-only (Producer)** collection.
>
> The lecture describes this as **covariant** because it allows reading values safely from related subtypes. 
>
> ---
>
> # Lower Bounded Wildcards (`? super T`)
>
> Syntax:
>
> ```java
> List<? super Animal>
> ```
>
> Means:
>
> > A list whose type is `Animal` or one of its superclasses.
>
> Valid:
>
> ```java
> List<Animal>
> List<Object>
> ```
>
> ---
>
> ## Writing
>
> Since every acceptable list can store `Animal`,
>
> writing is safe.
>
> ```java
> list.add(new Animal());
>
> list.add(new Dog());
> ```
>
> Both are allowed because `Dog` is also an `Animal`.
>
> ---
>
> ## Reading
>
> ```java
> Animal a = list.get(0);
> ```
>
> ❌ Not allowed.
>
> Why?
>
> The actual list may be:
>
> ```java
> List<Object>
> ```
>
> The retrieved object may not necessarily be an `Animal`.
>
> Therefore Java only guarantees:
>
> ```java
> Object obj = list.get(0);
> ```
>
> `? super` therefore behaves like a **write-only (Consumer)** collection. It is often described as **contravariant**. 
>
> ---
>
> # Summary of Wildcards
>
> | Type                | Read     | Write | Typical Use            |
> | ------------------- | -------- | ----- | ---------------------- |
> | `List<T>`           | ✅        | ✅     | Exact type (Invariant) |
> | `List<?>`           | `Object` | ❌     | Unknown type           |
> | `List<? extends T>` | `T`      | ❌     | Reading (Producer)     |
> | `List<? super T>`   | `Object` | `T`   | Writing (Consumer)     |
>
> ---
>
> # PECS Rule
>
> Java developers remember wildcard usage using the **PECS** rule:
>
> **P**roducer **E**xtends
>
> **C**onsumer **S**uper
>
> * If the collection **produces** data for you to read, use:
>
> ```java
> ? extends T
> ```
>
> * If the collection **consumes** data that you add, use:
>
> ```java
> ? super T
> ```
>
> This rule is highlighted in the lecture notes as the easiest way to choose the correct wildcard. 
>
> ---
>
> # Type Erasure
>
> One of the most important concepts in Generics is **Type Erasure**.
>
> The JVM **does not understand generic types**.
>
> Generics exist only during compilation.
>
> The compiler removes all generic information before generating bytecode.
>
> The lecture illustrates this compilation process:
>
> ```text
> Java Source
>      ↓
> Compiler
>      ↓
> Bytecode
>      ↓
> JVM
> ```
>
> By the time the program reaches the JVM, generic type parameters no longer exist. 
>
> ---
>
> ## Unbounded Generic After Erasure
>
> Source:
>
> ```java
> class Box<T> {
>     T value;
> }
> ```
>
> After compilation:
>
> ```java
> class Box {
>     Object value;
> }
> ```
>
> The type parameter is replaced with `Object`. 
>
> ---
>
> ## Bounded Generic After Erasure
>
> Source:
>
> ```java
> class Box<T extends Number> {
>     T value;
> }
> ```
>
> After compilation:
>
> ```java
> class Box {
>     Number value;
> }
> ```
>
> Here, the type parameter is replaced with its upper bound (`Number`) instead of `Object`. 
>
> ---
>
> ## Automatic Cast Insertion
>
> Consider:
>
> ```java
> Box<Integer> box = new Box<>();
>
> Integer x = box.get();
> ```
>
> After type erasure:
>
> ```java
> Box box = new Box();
>
> Integer x = (Integer) box.get();
> ```
>
> The compiler automatically inserts the necessary cast, allowing programmers to write generic code without manual casting. 
>
> ---
>
> # Consequences of Type Erasure
>
> ## 1. Cannot Use `instanceof` with Specific Generic Types
>
> ```java
> if (list instanceof List<String>)
> ```
>
> ❌ Not allowed.
>
> After type erasure, both `List<String>` and `List<Integer>` become simply `List`, so the JVM cannot distinguish between them. The notes illustrate this limitation with an `instanceof` example. 
>
> ---
>
> ## 2. Cannot Overload Only by Generic Parameters
>
> The following is illegal:
>
> ```java
> void print(List<String> list) {}
>
> void print(List<Integer> list) {}
> ```
>
> Both methods erase to:
>
> ```java
> void print(List list)
> ```
>
> causing a method signature conflict.
>
> ---
>
> ## 3. Bridge Methods
>
> When generic methods are overridden, the compiler may generate **bridge methods**.
>
> These synthetic methods preserve polymorphism after type erasure by ensuring overridden methods still match the erased method signatures. They are compiler-generated and usually invisible to developers.
>
> ---
>
> ## 4. Why Can't Generics Use Primitive Types?
>
> Invalid:
>
> ```java
> List<int>
> ```
>
> Valid:
>
> ```java
> List<Integer>
> ```
>
> During type erasure, generic type parameters are replaced with `Object` (or their bound).
>
> Primitive types like `int`, `double`, and `char` do not inherit from `Object`, so they cannot participate in generic type substitution.
>
> Java therefore requires wrapper classes such as:
>
> * `Integer`
> * `Double`
> * `Character`
> * `Boolean`
>
> ---
>
> # Key Takeaways
>
> * Generic types are **invariant**—`List<Dog>` is not a `List<Animal>`.
> * Arrays are **covariant**, but this can lead to `ArrayStoreException`.
> * `<?>` accepts any generic type but allows only safe read operations.
> * `? extends T` is used for reading (Producer).
> * `? super T` is used for writing (Consumer).
> * Remember **PECS**: **Producer Extends, Consumer Super**.
> * The JVM has no knowledge of generic types at runtime because of **Type Erasure**.
> * Unbounded generics erase to `Object`; bounded generics erase to their bound.
> * The compiler automatically inserts casts after erasure.
> * Due to type erasure, you cannot use `instanceof` with parameterized types or overload methods based only on generic type arguments.
> * Generic classes cannot use primitive types directly; wrapper classes must be used instead. 
