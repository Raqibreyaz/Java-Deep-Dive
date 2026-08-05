### 1. The Core Problem: Generics and Invariance (0:25 - 13:12)
* **Generics vs. Inheritance:** While *Animal* is a parent of *Dog*, a `List<Dog>` is **not** a subtype of `List<Animal>`. This is known as **Invariance**.
* **Type Safety:** If Java allowed `List<Animal> = new ArrayList<Dog>()`, one could add a *Cat* to a *Dog* list at runtime, breaking type safety (11:36 - 13:10).
* **Array Exception:** Unlike *Lists*, arrays in Java are **covariant**, which allows `Animal[] arr = new Dog[10]`, but this leads to potential *ArrayStoreException* at runtime (14:19 - 20:58).

### 2. Wildcards: The Solution (27:54 - 38:00)
* **Unbounded Wildcard (`<?>`):** Used when you want to handle any type of list. However, it restricts operations: you cannot add elements (except *null*) because the compiler does not know the exact type (28:30 - 36:00).

### 3. Bounded Wildcards (38:06 - 59:22)
* **Upper Bounded (`? extends T`):** 
    * Makes the list **read-only** (or Producer). You can retrieve items as type *T* (or its parent), but you cannot write to it.
    * Use this when you want to extract data from a structure (47:00 - 58:50).
* **Lower Bounded (`? super T`):** 
    * Makes the list **write-only** (or Consumer). You can add objects of type *T* or its children, but reading is restricted to *Object* type (58:54 - 59:22).

### 4. PECS Rule (1:00:30 - 1:01:30)
* **P**roducer **E**xtends, **C**onsumer **S**uper.
* If you are retrieving data (Producer), use `? extends T`.
* If you are adding data (Consumer), use `? super T`.

### 5. Type Erasure and Internals (1:04:22 - 1:21:55)
* **JVM and Generics:** The JVM has **no knowledge** of generics at runtime. The compiler performs **Type Erasure**.
    * All generic types are replaced by their bounds (or *Object* if unbounded) (1:07:30 - 1:09:40).
    * The compiler automatically inserts **casts** to maintain type safety (1:09:43 - 1:12:40).
* **Consequences of Erasure:**
    * Cannot use `instanceof` with specific generic types (1:13:05 - 1:13:50).
    * Cannot overload methods based solely on generic type parameters (1:14:08 - 1:15:00).
    * **Bridge Methods:** The compiler creates these to preserve polymorphism when overriding methods with erased types (1:15:10 - 1:18:50).
* **Why no primitives in Generics?** Primitives (like `int`) cannot be replaced by `Object` during type erasure because `Object` is only a parent to reference types, not primitives (1:20:20 - 1:21:50).