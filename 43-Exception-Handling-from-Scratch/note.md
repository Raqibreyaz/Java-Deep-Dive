# Java Exception Handling



## One-sentence summary

**Exception handling in Java allows a program to detect abnormal situations, handle them safely, clean up resources, and continue execution instead of crashing unexpectedly.**

---

# 1. Why Do We Need Exception Handling?

Real-world programs do not always receive perfect input or operate under perfect conditions.

For example:

* A user enters invalid input.
* A file that the program expects is missing.
* A program tries to divide by zero.
* A required resource is unavailable.

The uploaded notes on page 1 give three simple examples:

```text
Wrong user input
File is missing
Division by zero
```



Without exception handling, these situations can interrupt the normal flow of the program.

---

# 2. What Is an Exception?

An **exception** is an abnormal or unexpected condition that occurs while a program is executing.

Examples:

```java
int a = 10;
int b = 0;

System.out.println(a / b);
```

Division by zero causes:

```text
ArithmeticException
```

Another example is trying to access something that does not exist or providing invalid input.

The important idea is:

```text
Normal execution
      ↓
Unexpected condition
      ↓
Exception
      ↓
Handle it
      ↓
Continue / recover
```

The notes describe an exception as a **recoverable/handleable abnormal condition that occurs during program execution**. 

---

# 3. Exceptions Are Objects

Java follows an object-oriented approach to exception handling.

An exception is represented by an **object**.

For example:

```java
catch (ArithmeticException e) {
    // handle exception
}
```

Here:

```text
ArithmeticException
       ↓
    exception
       ↓
     object
       ↓
       e
```

The variable `e` refers to the exception object.

This object contains information about what went wrong.

---

# 4. Exception vs Error

Java problems are broadly divided into two important categories:

```text
Problems
   ├── Exception
   └── Error
```

This distinction is shown on page 1 of the uploaded notes. 

---

## Exception

An **Exception** represents a condition that the application can generally handle or recover from.

Examples:

```text
ArithmeticException
invalid input
missing file
```

The application can often decide what to do when such a situation occurs.

For example:

```text
Invalid input
     ↓
show an error message
     ↓
ask the user again
```

---

## Error

An **Error** represents a much more serious problem.

Examples from the notes:

```text
OutOfMemoryError
StackOverflowError
```

These are generally not situations that normal application code is expected to recover from.

Think:

```text
Exception → application may handle it
Error     → serious JVM/system-level problem
```

The uploaded notes explicitly list `OutOfMemoryError` and `StackOverflowError` under Errors. 

---

# 5. Simple Comparison

| Exception                      | Error                                                |
| ------------------------------ | ---------------------------------------------------- |
| Abnormal runtime condition     | Serious failure                                      |
| Often recoverable/handleable   | Generally not recoverable by normal application code |
| Programmer can often handle it | Usually indicates a serious JVM/runtime problem      |
| Example: `ArithmeticException` | Example: `OutOfMemoryError`                          |
| Example: invalid input         | Example: `StackOverflowError`                        |

### Mental model

```text
Exception
   ↓
"Something went wrong,
but I may be able to handle it."


Error
   ↓
"Something seriously went wrong;
normal application recovery is generally
not expected."
```

---

# 6. What Happens Without Exception Handling?

Suppose we have:

```java
public static void main(String[] args) {

    int a = 5;
    int b = 0;

    System.out.println(a / b);

    System.out.println("Hello");
}
```

The division:

```java
a / b
```

causes an `ArithmeticException`.

The `Hello` statement will not execute because normal control flow is interrupted.

---

# 7. JVM's Default Exception Handling

What happens if we do not handle the exception ourselves?

The **JVM performs default exception handling**.

The basic flow is:

```text
Program starts
      ↓
method calls another method
      ↓
exception occurs
      ↓
no matching handler
      ↓
JVM default exception handling
      ↓
print stack trace
      ↓
program terminates
```

The notes on page 2 demonstrate this using:

```java
int a = 5;
int b = 0;

System.out.println(a / b);
```

The JVM detects the arithmetic problem and performs default exception handling. 

---

# 8. What Is a Stack Trace?

A **stack trace** is a report showing the path through the program's method calls leading to the exception.

For example, imagine:

```text
main()
  ↓
methodA()
  ↓
methodB()
  ↓
a / b
  ↓
ArithmeticException
```

The stack trace helps us understand:

```text
Where did the exception happen?
Which method was executing?
How did execution reach that point?
```

This makes stack traces very useful for **debugging**.

The notes explicitly connect exception handling with debugging and show the method-call path on page 2. 

---

# 9. Exception Propagation

An exception does not necessarily have to be handled in the exact method where it occurs.

It can **propagate up the call stack**.

Consider:

```text
main()
  ↓
methodA()
  ↓
methodB()
  ↓
exception occurs
```

If `methodB()` does not handle the exception:

```text
methodB()
   ↓
methodA()
   ↓
main()
```

The exception moves upward looking for a suitable handler.

This is called **exception propagation**.

The source notes describe this as the choice between handling an exception directly where it occurs or passing it upward to a parent method. 

---

# 10. Visualizing Propagation

```text
main()
  │
  └── methodA()
          │
          └── methodB()
                  │
                  └── risky code
                        ↓
                    Exception
                        ↓
                 methodB handles?
                   /       \
                 yes        no
                  ↓          ↓
              handle     propagate
                             ↓
                         methodA
                             ↓
                      handles or
                       propagates
                             ↓
                           main
```

This is why the stack trace is useful: it records this path.

---

# 11. The Goal of Exception Handling

The handwritten notes on page 3 summarize the goal very clearly:

> When an exception occurs, don't crash. Handle it and continue.

Conceptually:

```text
Without handling:

Exception
   ↓
Crash
   ↓
Program stops


With handling:

Exception
   ↓
Catch
   ↓
Handle
   ↓
Continue
```



---

# 12. `try-catch`

The main mechanism for handling exceptions is the **`try-catch` block**.

Basic structure:

```java
try {
    // risky code
}
catch (Exception e) {
    // recovery/handling code
}
```

The page 3 notes identify the code inside `try` as **risky code** and the `catch` block as the place for handling the exception. 

---

# 13. The `try` Block

The `try` block contains code that **might throw an exception**.

Example:

```java
try {
    int a = 5;
    int b = 0;

    System.out.println(a / b);
}
```

The risky operation is:

```java
a / b
```

because `b` is zero.

Mental model:

```text
try {
    risky code
}
```

It means:

> "Try to execute this code. If an exception occurs, look for an appropriate handler."

---

# 14. The `catch` Block

The `catch` block handles an exception thrown from the corresponding `try`.

Example:

```java
try {
    int a = 5;
    int b = 0;

    System.out.println(a / b);
}
catch (ArithmeticException e) {
    System.out.println("Cannot divide by zero");
}
```

The flow becomes:

```text
try
 ↓
5 / 0
 ↓
ArithmeticException
 ↓
catch
 ↓
print message
 ↓
continue after try-catch
```

Instead of the program terminating through default handling, we provide our own response.

---

# 15. Why Specify `ArithmeticException`?

Exceptions are represented by classes.

So:

```java
catch (ArithmeticException e)
```

means:

> "If an `ArithmeticException` occurs in this try block, give me that exception object as `e`."

The type of the exception determines which catch block can handle it.

---

# 16. The Exception Object

Consider:

```java
catch (ArithmeticException e) {
    System.out.println(e.getMessage());
}
```

Here:

```text
e
↓
exception object
```

Because `e` is an object, we can call methods on it.

Two useful methods are:

```java
e.getMessage();
e.printStackTrace();
```

---

# 17. `getMessage()`

`getMessage()` gives the exception's message.

Example:

```java
catch (ArithmeticException e) {
    System.out.println(e.getMessage());
}
```

This is useful when we want to log or display a concise description of the problem.

Conceptually:

```text
Exception object
      ↓
getMessage()
      ↓
description of problem
```

---

# 18. `printStackTrace()`

Another useful method is:

```java
e.printStackTrace();
```

It prints the stack trace.

This is particularly useful during debugging because it shows the path through the program that led to the exception.

Example:

```java
catch (ArithmeticException e) {
    e.printStackTrace();
}
```

---

# 19. Complete `try-catch` Example

```java
public static void main(String[] args) {

    try {
        int a = 5;
        int b = 0;

        System.out.println(a / b);
    }
    catch (ArithmeticException e) {
        System.out.println("Cannot divide by zero");
    }

    System.out.println("Program continues...");
}
```

Conceptual output:

```text
Cannot divide by zero
Program continues...
```

The important point is that after the exception is handled, execution can continue after the `try-catch`.

---

# 20. `finally`

`finally` is another part of Java's exception-handling mechanism.

Basic structure:

```java
try {
    // risky code
}
catch (Exception e) {
    // handle exception
}
finally {
    // cleanup
}
```

Unlike `catch`, `finally` is not primarily about handling the exception.

Its main purpose is **cleanup**.

---

# 21. Why Use `finally`?

Some resources must be cleaned up after use.

Examples include:

* closing files,
* releasing database connections,
* releasing other resources.

The idea is:

```text
Use resource
    ↓
Something happens
    ↓
Cleanup still needs to happen
```

That is where `finally` is useful.

The source specifically identifies closing files and releasing database connections as cleanup use cases. 

---

# 22. `finally` Executes Regardless of Exception Handling

The important idea is:

```text
try
 ↓
exception?
 ├── no ──→ finally
 └── yes → catch → finally
```

So `finally` is used for code that should execute regardless of whether the risky code succeeded or an exception was handled.

---

# 23. Example with `finally`

```java
try {
    int a = 10;
    int b = 0;

    System.out.println(a / b);
}
catch (ArithmeticException e) {
    System.out.println("Division by zero");
}
finally {
    System.out.println("Cleanup code");
}
```

Flow:

```text
try
 ↓
exception
 ↓
catch
 ↓
finally
 ↓
continue
```

The cleanup code gets a guaranteed place in the normal exception-handling structure.

---

# 24. `try-finally` Without `catch`

A useful pattern is:

```java
try {
    // risky code
}
finally {
    // cleanup
}
```

A `catch` block is not mandatory if immediate handling is not required at that point.

Why use this?

Because you may want the exception to propagate to a parent method while still guaranteeing cleanup.

Conceptually:

```text
methodB()
   ↓
try
   ↓
exception
   ↓
finally → cleanup
   ↓
propagate exception
   ↓
methodA()
```

The source explicitly mentions that a `try-finally` structure can be used without a `catch` when immediate handling is not required but cleanup is mandatory. 

---

# 25. Multiple `catch` Blocks

A program may encounter different types of exceptions.

Java allows multiple `catch` blocks for different exception types.

Conceptually:

```java
try {
    // risky code
}
catch (ArithmeticException e) {
    // handle arithmetic problem
}
catch (Exception e) {
    // handle other exceptions
}
```

The idea is:

```text
                    Exception
                       ↓
              ┌────────┴────────┐
              ↓                 ↓
     ArithmeticException      other Exception
              ↓                 ↓
          catch #1          catch #2
```

This allows different exception types to receive different handling logic.

---

# 26. Why Multiple Catch Blocks?

Different problems may require different responses.

For example:

```text
ArithmeticException
       ↓
"Invalid mathematical operation"


Input-related exception
       ↓
"Please enter valid input"


Other exception
       ↓
"Unexpected problem"
```

So exception handling can be more precise than having one generic handler for everything.

---

# 27. Exception Handling and Control Flow

Exception handling changes the normal flow of control.

Without handling:

```text
main
 ↓
methodA
 ↓
methodB
 ↓
exception
 ↓
JVM default handling
 ↓
stack trace
 ↓
termination
```

With handling:

```text
main
 ↓
methodA
 ↓
methodB
 ↓
exception
 ↓
catch
 ↓
recovery
 ↓
continue
```

The uploaded notes explicitly connect exception handling with **flow of control**, **debugging**, and **user experience**. 

---

# 28. Exception Handling and User Experience

Exception handling is not only about preventing technical crashes.

It also improves the user experience.

Bad experience:

```text
User enters invalid input
        ↓
Exception
        ↓
program crashes
        ↓
user sees technical error
```

Better experience:

```text
User enters invalid input
        ↓
Exception handled
        ↓
meaningful message
        ↓
user can correct input
        ↓
program continues
```

So exception handling can turn an unexpected technical failure into a controlled user-facing response.

---

# 29. Exception Handling and Debugging

Exception objects provide information that helps developers understand problems.

For example:

```java
catch (Exception e) {
    System.out.println(e.getMessage());
    e.printStackTrace();
}
```

Here:

```text
getMessage()
    ↓
what happened?


printStackTrace()
    ↓
where/how did it happen?
```

This makes exception information useful during debugging.

---

# 30. A Complete Mental Model

```text
                  Exception occurs
                         │
                         ↓
              Is it handled here?
                  /            \
                Yes             No
                 ↓               ↓
               catch        propagate upward
                 │               │
                 ↓               ↓
             handle it       parent method
                 │               │
                 └───────┬───────┘
                         ↓
                      finally
                         ↓
                      continue
```

If nobody handles the exception:

```text
Exception
    ↓
propagates through call stack
    ↓
no handler found
    ↓
JVM default exception handling
    ↓
stack trace
    ↓
program terminates
```

---

# 31. Important Exception Handling Keywords

| Keyword   | Purpose                                                  |
| --------- | -------------------------------------------------------- |
| `try`     | Contains risky code                                      |
| `catch`   | Handles an exception                                     |
| `finally` | Performs cleanup                                         |
| `throw`   | Explicitly throws an exception                           |
| `throws`  | Declares that a method may pass exceptions to its caller |

The uploaded source focuses primarily on `try`, `catch`, and `finally`; `throw` and `throws` are not developed in the provided material, so they should be studied separately rather than inferred from this source. 

---

# 32. Common Mistakes / Gotchas

## 1. Putting all code inside `try`

Do not automatically put the entire program inside one giant `try`.

The purpose of `try` is to identify **risky code** that needs exception handling.

Prefer:

```java
try {
    // operation that may fail
}
catch (...) {
    // appropriate response
}
```

---

## 2. Catching everything without understanding it

This:

```java
catch (Exception e) {
    // ...
}
```

can be useful in some situations, but specific exceptions often allow better handling.

For example:

```java
catch (ArithmeticException e)
```

communicates exactly what problem is being handled.

---

## 3. Ignoring the exception object

This:

```java
catch (Exception e) {
}
```

throws away useful debugging information.

The exception object can provide:

```java
e.getMessage();
e.printStackTrace();
```

---

## 4. Confusing Exception and Error

Remember:

```text
Exception → generally handleable
Error     → serious, generally not recoverable
```

Do not treat every `Error` like an ordinary application exception.

---

## 5. Forgetting cleanup

If a resource needs cleanup, make sure there is a reliable cleanup strategy.

The source highlights `finally` for this purpose.

---

## 6. Assuming the exception must be handled where it occurs

It does not.

An exception can propagate:

```text
methodB()
   ↓
methodA()
   ↓
main()
```

until an appropriate handler is found.

---

## 7. Forgetting that `finally` can exist without `catch`

This is valid:

```java
try {
    // risky code
}
finally {
    // cleanup
}
```

It allows cleanup while the exception can continue propagating upward.

---

# 33. Exception Handling Example from Start to Finish

Consider:

```java
public static void divide() {

    try {
        int a = 10;
        int b = 0;

        System.out.println(a / b);
    }
    catch (ArithmeticException e) {
        System.out.println("Cannot divide by zero");
        System.out.println(e.getMessage());
    }
    finally {
        System.out.println("Division operation finished");
    }
}
```

The flow is:

```text
divide()
   ↓
try
   ↓
10 / 0
   ↓
ArithmeticException
   ↓
catch
   ├── display message
   └── inspect exception
   ↓
finally
   ↓
cleanup/final action
   ↓
method continues/returns
```

---

# 34. Exception Propagation Example

Suppose:

```java
static void methodB() {
    int a = 10;
    int b = 0;

    System.out.println(a / b);
}

static void methodA() {
    methodB();
}

public static void main(String[] args) {
    methodA();
}
```

The call chain is:

```text
main()
  ↓
methodA()
  ↓
methodB()
  ↓
a / b
  ↓
exception
```

If `methodB()` does not handle it, the exception can propagate:

```text
methodB()
   ↓
methodA()
   ↓
main()
```

The stack trace reflects this path.

---

# 35. Default Handling vs Manual Handling

| Default handling                    | Manual handling                      |
| ----------------------------------- | ------------------------------------ |
| JVM handles the unhandled exception | Programmer provides handler          |
| Stack trace is printed              | Programmer decides what to do        |
| Program terminates                  | Program may recover and continue     |
| Limited control                     | Better control over flow             |
| Mostly useful as a fallback         | Useful for real application behavior |

The page 2 diagram specifically contrasts the exception occurring in a nested method call with the JVM's default handling and stack trace. 

---

# 36. The Big Picture

```text
                         Java Program
                              │
                              ↓
                       Normal execution
                              │
                     unexpected situation
                              ↓
                         Exception
                              │
                ┌─────────────┴─────────────┐
                │                           │
          Handle locally              Propagate upward
                │                           │
             catch                    parent method
                │                           │
                └─────────────┬─────────────┘
                              ↓
                           finally
                              │
                           cleanup
                              │
                              ↓
                          Continue


If nobody handles it:
                              ↓
                     JVM default handling
                              ↓
                        Stack trace
                              ↓
                       Program ends
```

---

# 37. Key Takeaways

* Real-world programs encounter unexpected situations.
* Examples include:

  * invalid user input,
  * missing files,
  * division by zero.
* An **Exception** is an abnormal runtime condition that is generally recoverable/handleable.
* An **Error** represents a serious problem that normal application code generally cannot recover from.
* Examples of Errors:

  * `OutOfMemoryError`
  * `StackOverflowError`
* Exceptions are **objects** in Java.
* If an exception is not handled manually, the **JVM performs default exception handling**.
* Default handling:

  1. stops normal control flow,
  2. prints a stack trace,
  3. terminates the program.
* `try` contains **risky code**.
* `catch` contains the **handling/recovery code**.
* An exception object can be accessed through a variable such as:

```java
catch (ArithmeticException e)
```

* Useful methods include:

```java
e.getMessage();
e.printStackTrace();
```

* `finally` is used mainly for **cleanup**.
* Typical cleanup includes:

  * closing files,
  * releasing database connections,
  * releasing other resources.
* Exceptions can **propagate up the call stack**.
* Multiple `catch` blocks can handle different exception types.
* `try-finally` can be used without `catch` when cleanup is required but immediate handling is not.
* The overall goal is:

```text
Exception occurs
      ↓
Handle it
      ↓
Avoid uncontrolled crash
      ↓
Continue safely
```

---

# 38. Minimal Self-Test

1. Why is exception handling needed in real-world programs?
2. What is an exception?
3. What is an error?
4. Give two examples of Java Errors.
5. What is the difference between an Exception and an Error?
6. What happens when an exception is not handled?
7. What is default exception handling?
8. What is a stack trace?
9. Why is a stack trace useful for debugging?
10. What is the purpose of the `try` block?
11. What is the purpose of the `catch` block?
12. Why are exceptions called objects in Java?
13. What does `ArithmeticException e` mean?
14. What does `e.getMessage()` provide?
15. What does `e.printStackTrace()` do?
16. What is the purpose of `finally`?
17. Why is `finally` useful for resource cleanup?
18. Can `finally` exist without `catch`?
19. What is exception propagation?
20. Can an exception be handled by a parent method?
21. Why might a program use multiple `catch` blocks?
22. What happens to control flow when an exception occurs?
23. What is the main goal of exception handling?
24. How can exception handling improve user experience?
25. What is the difference between handling an exception locally and propagating it?
