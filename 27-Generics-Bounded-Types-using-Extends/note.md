> ## Hook: Why Did Java Introduce Generics?
>
> Before Java Generics, developers often used `Object` to create reusable classes because every class in Java inherits from `Object`. While this made classes flexible, it also removed **type safety**, forcing programmers to manually cast objects back to their original type. These casts could fail at runtime, leading to `ClassCastException`. Java Generics solved this problem by preserving type information at compile time, eliminating unnecessary casting and making code safer, cleaner, and easier to maintain. 
>
> ---
>
> # Java Generics
>
> ## What is Type Safety?
>
> Java is a **strongly typed language**, meaning every variable has a specific data type. A data type defines the set of valid operations that can be performed on a value. For example:
>
> * An `int` supports arithmetic operations.
> * A `String` supports string methods like `substring()` and `length()`.
> * A custom `Student` object supports only the methods defined in the `Student` class.
>
> Because of these rules, Java prevents invalid operations such as assigning a string to an integer or performing arithmetic on incompatible types. These errors are detected during compilation, making the language safer and reducing runtime bugs. 
>
> ---
>
> ## Upcasting
>
> **Upcasting** means converting an object of a child class into one of its parent classes.
>
> ```java
> class Animal {}
>
> class Dog extends Animal {}
>
> Dog d = new Dog();
> Animal a = d;
> ```
>
> Here, a `Dog` object is stored inside an `Animal` reference.
>
> Upcasting is:
>
> * Automatic
> * Safe
> * Does not require explicit casting
>
> This is possible because every `Dog` is an `Animal`.
>
> Similarly,
>
> ```java
> String s = "Hello";
> Object obj = s;
> ```
>
> Since every class ultimately extends `Object`, any object can be safely upcast to `Object`. The lecture describes this as moving from **specific to general**, and no casting is required. 
>
> ---
>
> ## Downcasting
>
> **Downcasting** is the opposite of upcasting. It converts a parent reference back into a child type.
>
> ```java
> Object obj = "Hello";
>
> String s = (String) obj;
> ```
>
> Unlike upcasting, Java cannot guarantee that the object stored inside the parent reference actually belongs to the requested child class.
>
> Therefore:
>
> * Explicit casting is mandatory.
> * The compiler allows it.
> * The actual type is checked at runtime.
>
> Example:
>
> ```java
> Object obj = 10;
>
> String s = (String) obj;
> ```
>
> This compiles but throws a **ClassCastException** at runtime because the object is actually an `Integer`, not a `String`. The lecture emphasizes that many errors caused by incorrect downcasting shift from compile time to runtime. 
>
> ---
>
> ## Why Using Object Is a Problem
>
> Before Generics, a reusable class was often written using `Object`.
>
> ```java
> class Box {
>     Object value;
> }
> ```
>
> Although this allows storing any type of object, it introduces several problems:
>
> * Type information is lost.
> * Wrong objects can be inserted.
> * Reading data requires explicit casting.
> * Many errors are discovered only at runtime.
>
> These limitations are highlighted in the lecture notes as the primary motivation for Generics. 
>
> ---
>
> ## Java Generics
>
> Generics allow classes and methods to work with different data types while maintaining compile-time type safety.
>
> Instead of storing `Object`, a generic class stores a placeholder type.
>
> ```java
> class Box<T> {
>     T value;
> }
> ```
>
> Here, `T` represents a type that will be supplied later.
>
> Example:
>
> ```java
> Box<String> box = new Box<>();
> ```
>
> Now the compiler knows that this box stores only `String` objects.
>
> Attempting to insert any other type results in a compile-time error instead of a runtime exception. This preserves type information while keeping the class reusable. 
>
> ---
>
> ## Generic Classes
>
> A generic class is defined by adding a type parameter inside angle brackets.
>
> ```java
> class Box<T> {
>
>     private T value;
>
>     public void set(T value) {
>         this.value = value;
>     }
>
>     public T get() {
>         return value;
>     }
> }
> ```
>
> Usage:
>
> ```java
> Box<Integer> b1 = new Box<>();
> b1.set(10);
>
> Box<String> b2 = new Box<>();
> b2.set("Java");
> ```
>
> No casting is required while retrieving values because the compiler already knows the correct type.
>
> ---
>
> ## Generic Methods
>
> Generics can also be applied to methods.
>
> The type parameter is declared before the return type.
>
> ```java
> public static <T> void print(T value) {
>     System.out.println(value);
> }
> ```
>
> Usage:
>
> ```java
> print(10);
> print("Java");
> print(5.5);
> ```
>
> Java automatically determines the type argument based on the method call. This feature is called **Type Inference**, so developers usually do not need to specify the type explicitly.
>
> ---
>
> ## Bounded Types (`extends`)
>
> Sometimes a generic should accept only certain kinds of types.
>
> This is achieved using **upper bounds** with the `extends` keyword.
>
> ```java
> class Calculator<T extends Number> {
>
>     T value;
> }
> ```
>
> Now only subclasses of `Number` are allowed.
>
> Valid:
>
> ```java
> Calculator<Integer>
> Calculator<Double>
> Calculator<Float>
> ```
>
> Invalid:
>
> ```java
> Calculator<String>
> Calculator<Boolean>
> ```
>
> Since every valid type extends `Number`, methods such as `doubleValue()` can safely be called inside the generic class.
>
> ---
>
> ## Multiple Bounds
>
> Java also allows combining multiple constraints.
>
> ```java
> class Example<T extends Animal & Swimmable> {
>
> }
> ```
>
> Here, `T` must:
>
> * Extend the `Animal` class.
> * Implement the `Swimmable` interface.
>
> Both conditions must be satisfied.
>
> ---
>
> ## Advantages of Generics
>
> * Compile-time type checking.
> * Eliminates manual casting.
> * Prevents many runtime errors.
> * Improves code readability.
> * Better IDE support, including auto-completion and debugging.
> * Enables reusable, type-safe classes and methods.
>
> ---
>
> ## Key Takeaways
>
> * Java is a strongly typed language that enforces type safety.
> * Upcasting is automatic because every child object is also a parent object.
> * Downcasting requires explicit casting and may throw `ClassCastException`.
> * Using `Object` as a universal type removes type information.
> * Generics preserve type information while keeping code reusable.
> * Generic classes use type parameters such as `<T>`.
> * Generic methods declare their type parameter before the return type.
> * Type Inference allows Java to determine generic types automatically.
> * `T extends Number` restricts generic types to `Number` and its subclasses.
> * Multiple bounds (`&`) require a type to satisfy more than one constraint simultaneously. 
