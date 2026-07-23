# Java Functions: The Building Blocks of Modular Code

## Hook

Why do functions matter so much? Because without them, code turns into repeated logic, tangled control flow, and hard-to-test behavior. Functions let you package behavior into named units that you can call whenever needed.

That is the difference between writing a script and building a system.

## What is it?

A function is a reusable block of code that performs a specific task. Instead of repeating the same logic, you place it inside a function and call it by name whenever you need the result. The PDF shows this idea with a simple `sum(a, b)` example that is called from `main`.

## One-sentence summary

Functions turn inputs into outputs, which makes code reusable, easier to read, easier to test, and easier to reason about.

## Intuition

Think of a function like a machine:

- You feed it inputs.
- It runs internal steps.
- It gives back an output.

You do not need to know every internal detail to use it. You only need to know what it takes in and what it returns. That abstraction is what makes large programs manageable.

## Anatomy of a function

The PDF’s example is:

```java
static int sum(int a, int b) {
    int result = a + b;
    return result;
}
```

Each part has a role:

- `static`: the function belongs to the class, not an object instance.
- `int`: the return type, meaning the function gives back an integer.
- `sum`: the function name.
- `int a, int b`: parameters, the inputs the function expects.
- `{ ... }`: the body, where the work happens.
- `return result;`: sends the answer back to the caller.

That structure is the basic signature of a Java function.

## Parameters and arguments

These two terms are easy to mix up:

- **Parameters** are the variables in the function definition, like `a` and `b`.
- **Arguments** are the actual values passed when calling the function, like `10` and `9`.

Example:

```java
sum(10, 9);
```

Here, `10` and `9` are arguments, and inside the function they get bound to the parameters `a` and `b`.

## Return type

The return type tells you what kind of value the function gives back. In the PDF, `sum` returns `int`, so the caller can store the result in an integer variable.

If a function does not return anything, its return type is `void`. That means it performs an action but does not produce a value for the caller.

Example:

```java
void printHello() {
    System.out.println("Hello");
}
```

This function does something, but there is no return value.

## The main method

The PDF uses `main` as the entry point:

```java
public static void main(String[] args) {
    int i = 4;
    int j = 5;
    int result = sum(i, j);
    System.out.println(result);
}
```

The `main` method is the place the JVM starts execution. From there, it can call other functions. That is why `main` is often called the program’s entry point.

## Types of functions

The PDF groups functions into four useful categories:

- No input, no output.
- Input, no output.
- No input, output.
- Input, output.

This is a good mental model because it forces you to ask two questions:

1. Does the function need data from the caller?
2. Does the caller need a result back?

Example patterns:

```java
void greet() { ... }                  // no input, no output
void printSum(int a, int b) { ... }   // input, no output
int getFive() { ... }                 // no input, output
int sum(int a, int b) { ... }         // input, output
```

## Function chaining

The PDF shows chaining as one function calling another, which calls another. This creates a controlled execution path through multiple layers of logic.

Example:

```java
void main() {
    A();
}

void A() {
    B();
}

void B() {
    System.out.println("Hello");
}
```

Execution flow:

```text
main → A → B → return to A → return to main
```

This matters because it is how larger programs are structured. Each function can do one job and delegate the rest.

## Recursion

Recursion is when a function calls itself. The PDF introduces it directly and then shows it with printing numbers and Fibonacci.

Recursion is useful when a problem can be broken into smaller versions of the same problem. But recursion only works if there is a **base case** that stops the repeated calls.

### Why the base case matters

Without a base case, the function keeps calling itself until the call stack runs out and Java throws `StackOverflowError`. That is not just a bug; it is the runtime warning that the recursion never stopped.

## Example: print numbers

The PDF’s recursive printing example is:

```java
void printNum(int n) {
    if (n == 0) {
        return;
    }
    printNum(n - 1);
    System.out.println(n);
}
```

### What happens

If `printNum(5)` is called:

- It calls `printNum(4)`.
- That calls `printNum(3)`.
- That calls `printNum(2)`.
- That calls `printNum(1)`.
- That calls `printNum(0)`.
- The base case stops recursion.
- Then the stack unwinds and prints `1, 2, 3, 4, 5`.

That is why recursion often feels like “go down first, then come back up.”

## Example: Fibonacci

The PDF also shows Fibonacci recursion:

```java
int fib(int n) {
    if (n == 0 || n == 1) {
        return n;
    }
    int x = fib(n - 1);
    int y = fib(n - 2);
    return x + y;
}
```

This works because each Fibonacci number is defined in terms of the two previous ones.

### Why this is a good recursion example

Fibonacci is a classic recursion example because the definition is naturally self-referential. But it is also a good warning example, because naive recursion does a lot of repeated work. That makes it elegant to read, but not always efficient to run.

## Function calls and the stack

Every function call creates a new stack frame with its own local variables and return information. That is why function chaining and recursion both depend on the call stack.

Think of the stack as a stack of plates:

- Each function call adds a plate.
- When the function finishes, that plate is removed.
- If too many recursive calls happen, the stack grows until it overflows.

This is the hidden memory mechanism behind function execution.

## Scope

The PDF correctly highlights that local variables inside a function are not accessible outside it. That is called **scope**.

Example:

```java
void demo() {
    int x = 10;
}
```

`x` exists only inside `demo()`. Outside that function, `x` is out of scope and cannot be used. This helps prevent accidental interference between parts of a program.

## Overloading

Your note mentions overloading, and this is worth keeping because it is a foundational Java function concept. Function overloading means multiple functions can share the same name as long as their parameter lists differ.

Example:

```java
int sum(int a, int b) { ... }
int sum(int a, int b, int c) { ... }
```

Java decides which one to call at compile time based on the arguments.

Important rule: changing only the return type is **not** enough to overload a method.

## Common gotchas

- Missing base case in recursion causes infinite recursion and stack overflow.
- Local variables do not exist outside the function body.
- Return type must match what the function actually returns.
- Overloading depends on parameter list differences, not just the function name.
- Recursive solutions are often elegant but may repeat work unless optimized.

## Step-by-step example

Consider:

```java
public static void main(String[] args) {
    int result = sum(4, 5);
    System.out.println(result);
}

static int sum(int a, int b) {
    int result = a + b;
    return result;
}
```

### Execution

1. `main` starts.
2. `sum(4, 5)` is called.
3. Inside `sum`, `a = 4` and `b = 5`.
4. `result = 9`.
5. `return result;` sends `9` back to `main`.
6. `main` stores `9` in its local variable `result`.
7. `System.out.println(result);` prints `9`.

That is the cleanest mental model for function calls: input flows in, output flows back out.

## Key takeaways

- Functions package reusable logic into named blocks.
- Parameters are the function definition inputs; arguments are the actual passed values.
- `void` means the function returns nothing.
- `main` is the JVM entry point.
- Overloading requires different parameter lists.
- Recursion needs a base case.
- Function calls use the call stack.

## Minimal self-test

1. What happens if a recursive function has no base case?
2. What is the difference between a parameter and an argument?
3. Can two functions have the same name if their return types differ but parameters are identical?
4. What does `void` mean in a function declaration?
5. Why is `main` special in Java?

## What to learn next

1. **Stack vs Heap memory** — to understand local variables and function calls more deeply.
2. **Methods and classes** — because in Java, functions live inside classes.
3. **Recursion optimization** — memoization and dynamic programming.
4. **Call stack internals** — how frames are created and destroyed during execution.
