# Advanced Exception Handling in Java



## One-sentence summary

**Advanced exception handling in Java covers how exceptions move through nested blocks and method calls, how the exception hierarchy determines what can be caught, how to throw or declare exceptions, how to create domain-specific exceptions, and how try-with-resources simplifies resource cleanup.**

---

# 1. Nested `try-catch`

Java allows one `try-catch` block to be placed inside another `try-catch` block.

This is called **nested try-catch**.

Basic structure:

```java
try {

    // outer code

    try {
        // inner risky code
    }
    catch (Exception e) {
        // inner handling
    }

}
catch (Exception e) {
    // outer handling
}
```

The diagram on **page 1** of the uploaded notes shows exactly this structure: an outer `try`, an inner `try-catch`, and an outer `catch`. 

---

# 2. Why Would We Need Nested `try-catch`?

Suppose a method contains multiple operations:

```text
Outer operation
     ↓
Inner operation
     ↓
Some risky code
```

We might want the inner operation to handle its own exception locally.

For example:

```java
try {

    System.out.println("Outer code");

    try {
        int x = 10 / 0;
    }
    catch (ArithmeticException e) {
        System.out.println("Handled inside");
    }

    System.out.println("More outer code");

}
catch (Exception e) {
    System.out.println("Handled outside");
}
```

Here the inner `catch` handles the exception.

---

# 3. Case 1 — Inner `catch` Handles the Exception

Consider:

```java
try {

    System.out.println("Outer try");

    try {
        int result = 10 / 0;
    }
    catch (ArithmeticException e) {
        System.out.println("Inner catch");
    }

    System.out.println("Outer code continues");

}
catch (Exception e) {
    System.out.println("Outer catch");
}
```

The flow is:

```text
Outer try
   ↓
Inner try
   ↓
Exception occurs
   ↓
Inner catch matches
   ↓
Exception handled
   ↓
Continue after inner try-catch
   ↓
Outer code continues
```

The outer `catch` is **not** used because the inner block successfully handled the exception.

The first case in the video describes exactly this behavior. 

---

# 4. Case 2 — Inner `catch` Cannot Handle the Exception

Now suppose the inner `catch` expects the wrong exception type.

```java
try {

    try {
        int result = 10 / 0;
    }
    catch (NullPointerException e) {
        System.out.println("Inner catch");
    }

}
catch (ArithmeticException e) {
    System.out.println("Outer catch");
}
```

The exception is:

```text
ArithmeticException
```

but the inner `catch` handles:

```text
NullPointerException
```

So there is no match.

The exception propagates outward:

```text
Inner try
   ↓
ArithmeticException
   ↓
Inner catch doesn't match
   ↓
Propagate outward
   ↓
Outer catch matches
   ↓
Handle exception
```

This is the **external handling** case described in the video. 

---

# 5. Nested `try-catch` Mental Model

Think of exception handling as searching outward:

```text
                Outer try
        ┌─────────────────────┐
        │                     │
        │   Inner try         │
        │   ┌─────────────┐   │
        │   │ risky code  │   │
        │   └──────┬──────┘   │
        │          ↓          │
        │     Inner catch?    │
        │          │          │
        │       no match      │
        │          ↓          │
        │     Outer catch?    │
        │                     │
        └─────────────────────┘
```

The exception looks for a matching handler.

If the inner handler matches, it handles the exception.

If not, the exception can propagate to an outer handler.

---

# 6. Best Practice: Avoid Unnecessary Nested `try-catch`

Although Java allows nested `try-catch`, the video recommends **avoiding unnecessary nesting**.

Why?

Because nested exception handling can make control flow difficult to understand.

For example:

```java
try {
    try {
        try {
            // risky code
        }
        catch (...) {
        }
    }
    catch (...) {
    }
}
catch (...) {
}
```

This quickly becomes difficult to read.

The page 1 notes end with the explicit reminder:

```text
Avoid Nested Try
```



### Better approach

Use nested blocks only when the inner operation genuinely needs separate handling.

Otherwise, prefer a simpler structure:

```java
try {
    // risky operations
}
catch (Exception e) {
    // handling
}
```

---

# 7. Exception Hierarchy

Java's exception system is based on a class hierarchy.

The top-level class is:

```java
Throwable
```

The diagram on **page 2** shows:

```text
                  Throwable
                 /         \
              Error       Exception
                            │
                 ┌──────────┴──────────┐
                 ↓                     ↓
         Checked Exceptions     RuntimeException
                                  (Unchecked)
```



---

# 8. `Throwable`

`Throwable` is the common superclass for:

* `Error`
* `Exception`

Conceptually:

```text
Throwable
   ├── Error
   └── Exception
```

Therefore, both errors and exceptions are throwable objects.

This is why Java's exception-handling mechanisms can work with them.

---

# 9. `Error`

`Error` represents serious problems that are generally not expected to be recovered from by normal application code.

Examples include:

```text
OutOfMemoryError
StackOverflowError
```

So:

```text
Throwable
   │
   └── Error
          ├── OutOfMemoryError
          └── StackOverflowError
```

The page 2 hierarchy explicitly places `Error` directly under `Throwable`. 

---

# 10. `Exception`

The other major branch is:

```text
Throwable
    │
    └── Exception
```

`Exception` represents conditions that application code can generally handle.

It has two important categories in the source:

```text
Exception
   ├── Checked Exceptions
   └── RuntimeException
          ↓
      Unchecked
```

---

# 11. Checked Exceptions

A **checked exception** is an exception that the Java compiler requires the program to account for.

The source notes show examples such as:

```text
IOException
SQLException
ClassNotFoundException
```

The hierarchy from page 2 represents them under `Exception`, separate from `RuntimeException`. 

Example:

```java
void readFile() throws IOException {
    // file operation
}
```

The method must either:

* handle the checked exception, or
* declare it with `throws`.

---

# 12. Why Are They Called "Checked"?

The compiler checks whether your code has accounted for the exception.

For example:

```java
void readFile() throws IOException {
    // ...
}
```

or:

```java
try {
    // file operation
}
catch (IOException e) {
    // handle it
}
```

So the responsibility cannot simply be ignored.

---

# 13. Runtime Exceptions — Unchecked

`RuntimeException` is a subclass of `Exception`.

The source calls these **unchecked exceptions**.

```text
Exception
    │
    └── RuntimeException
            │
            ├── NullPointerException
            ├── ArithmeticException
            ├── ArrayIndexOutOfBoundsException
            ├── IllegalArgumentException
            ├── IllegalStateException
            └── ...
```

These examples are explicitly shown in the page 2 hierarchy. 

---

# 14. Why Are Runtime Exceptions Called Unchecked?

They are called **unchecked** because the compiler does not force you to catch or declare them.

For example:

```java
int a = 10;
int b = 0;

int result = a / b;
```

This compiles.

At runtime, however:

```text
ArithmeticException
```

can occur.

Similarly:

```java
String name = null;

System.out.println(name.length());
```

can produce:

```text
NullPointerException
```

The compiler does not require:

```java
try-catch
```

around these operations.

---

# 15. Checked vs Unchecked

| Checked Exception                             | Unchecked Exception                          |
| --------------------------------------------- | -------------------------------------------- |
| Under `Exception`, but not `RuntimeException` | Under `RuntimeException`                     |
| Compiler requires handling or declaration     | Compiler does not force handling/declaration |
| Often external/resource-related conditions    | Often programming/runtime problems           |
| Example: `IOException`                        | Example: `NullPointerException`              |
| Example: `SQLException`                       | Example: `ArithmeticException`               |
| Example: `ClassNotFoundException`             | Example: `IllegalArgumentException`          |

### Mental model

```text
Checked
   ↓
Compiler says:
"Deal with this."

Unchecked
   ↓
Compiler says:
"You don't have to explicitly deal with this."
```

---

# 16. Important Exception Classes

The page 2 diagram gives several useful examples.

## `NullPointerException`

Usually occurs when code tries to use a `null` reference as though it refers to an object.

```java
String name = null;

name.length();
```

---

## `ArithmeticException`

Occurs when an invalid arithmetic operation occurs.

Example:

```java
int result = 10 / 0;
```

---

## `ArrayIndexOutOfBoundsException`

Occurs when accessing an invalid array index.

```java
int[] arr = {10, 20, 30};

System.out.println(arr[5]);
```

Valid indices are:

```text
0
1
2
```

Index `5` is invalid.

---

## `IllegalArgumentException`

Indicates that a method received an inappropriate argument.

For example:

```java
Thread.sleep(-100);
```

can lead to an `IllegalArgumentException`.

---

## `IllegalStateException`

Indicates that an operation is being performed when the object is not in an appropriate state.

---

## `ClassNotFoundException`

The source lists this under checked exceptions.

It can occur when Java is asked to load a class but cannot find it.

---

# 17. Exception Object Methods

Every exception object provides useful methods for understanding the problem.

The source specifically identifies:

```java
getMessage()
printStackTrace()
toString()
```

The page 2 diagram shows these three methods branching from `Exception`. 

---

# 18. `getMessage()`

Returns the message associated with the exception.

```java
catch (Exception e) {
    System.out.println(e.getMessage());
}
```

Think:

```text
Exception
   ↓
getMessage()
   ↓
"What went wrong?"
```

---

# 19. `printStackTrace()`

Prints the stack trace.

```java
catch (Exception e) {
    e.printStackTrace();
}
```

It is particularly useful during debugging because it shows the path of method calls leading to the exception.

---

# 20. `toString()`

`toString()` provides a string representation of the exception.

For example:

```java
catch (Exception e) {
    System.out.println(e.toString());
}
```

Conceptually, it provides information such as:

```text
Exception type + message
```

So the three methods can be remembered as:

```text
getMessage()
     ↓
message


toString()
     ↓
exception description


printStackTrace()
     ↓
exception + execution path
```

---

# 21. Multiple `catch` Blocks

Java allows multiple `catch` blocks after one `try`.

Example:

```java
try {
    // risky code
}
catch (ArithmeticException e) {
    // arithmetic problem
}
catch (NullPointerException e) {
    // null problem
}
catch (Exception e) {
    // other exception
}
```

This allows different exception types to receive different handling logic.

---

# 22. Catch Matching Happens Top to Bottom

This is a very important rule.

The JVM checks the `catch` blocks from **top to bottom**.

Conceptually:

```text
try
 ↓
exception occurs
 ↓
catch #1?
 ↓ no
catch #2?
 ↓ no
catch #3?
 ↓ yes
handle
```

Therefore, the order of catch blocks matters.

---

# 23. Specific Exception Before Generic Exception

Consider:

```java
try {
    int result = 10 / 0;
}
catch (Exception e) {
    System.out.println("General");
}
catch (ArithmeticException e) {
    System.out.println("Arithmetic");
}
```

This is wrong.

Why?

Because:

```text
ArithmeticException
       ↓
Exception
```

`ArithmeticException` is already an `Exception`.

So the first catch:

```java
catch (Exception e)
```

would catch it.

The later `ArithmeticException` block would never be reached.

---

# 24. Correct Catch Ordering

Write the specific exception first:

```java
try {
    int result = 10 / 0;
}
catch (ArithmeticException e) {
    System.out.println("Arithmetic problem");
}
catch (Exception e) {
    System.out.println("Some other problem");
}
```

Mental rule:

```text
Specific
   ↓
More general
   ↓
Most general
```

The video explicitly emphasizes **specific to generic** ordering. 

---

# 25. Exception Hierarchy Explains Catch Ordering

The reason is the inheritance relationship:

```text
Exception
   │
   └── RuntimeException
          │
          └── ArithmeticException
```

Therefore:

```text
ArithmeticException IS-A Exception
```

So:

```java
catch (Exception e)
```

can catch an `ArithmeticException`.

That is why the general handler must come after the specific handler.

---

# 26. Multi-Catch

Java 7 introduced **multi-catch**.

It allows multiple sibling exception types to be handled by one `catch` block.

Syntax:

```java
try {
    // risky code
}
catch (ArithmeticException | NullPointerException e) {
    // common handling
}
```

The `|` operator means:

```text
ArithmeticException
       OR
NullPointerException
       ↓
same catch block
```

The video specifically identifies multi-catch as a Java 7 feature. 

---

# 27. Why Use Multi-Catch?

Suppose two unrelated exception types require exactly the same response.

Without multi-catch:

```java
catch (ArithmeticException e) {
    logError(e);
}

catch (NullPointerException e) {
    logError(e);
}
```

With multi-catch:

```java
catch (ArithmeticException | NullPointerException e) {
    logError(e);
}
```

This avoids duplicate handling code.

---

# 28. Multi-Catch and Sibling Exceptions

Multi-catch is intended for separate exception types.

For example:

```java
catch (ArithmeticException | NullPointerException e)
```

works because these are different exception classes that share a common ancestor.

Do not use a parent and its child together in the same multi-catch.

The important idea is:

```text
Different exception types
        ↓
Same handling
        ↓
Multi-catch
```

---

# 29. `throw` vs `throws`

These two keywords are easy to confuse.

Remember:

```text
throw
   ↓
actually throw an exception


throws
   ↓
declare that a method may throw an exception
```

---

# 30. `throw`

`throw` is used **inside a method** to explicitly trigger an exception.

Example:

```java
if (age < 18) {
    throw new IllegalArgumentException("Age must be 18 or above");
}
```

The program is explicitly saying:

> "This condition is invalid, so I want to throw an exception."

Conceptually:

```text
Condition detected
      ↓
throw
      ↓
Exception object
      ↓
exception handling / propagation
```

The video describes `throw` as manually triggering an exception from inside a method. 

---

# 31. `throws`

`throws` is used in the **method declaration**.

Example:

```java
void readFile() throws IOException {
    // file operation
}
```

This means:

> "This method may produce an `IOException`. The caller is responsible for dealing with it."

So the responsibility can move upward:

```text
readFile()
   ↓
throws IOException
   ↓
caller
   ↓
handles or further declares it
```

The video describes `throws` as passing the responsibility of handling an exception to the caller. 

---

# 32. `throw` vs `throws` Comparison

| `throw`                             | `throws`                               |
| ----------------------------------- | -------------------------------------- |
| Used to actually throw an exception | Used to declare possible exceptions    |
| Used inside method body             | Used in method signature               |
| Throws an exception object          | Announces possible exception types     |
| Example: `throw new ...`            | Example: `method() throws IOException` |

### Easy memory trick

```text
throw
  ↓
DO it

throws
  ↓
DECLARE it
```

---

# 33. Example Combining `throw` and `throws`

```java
static void checkAge(int age)
        throws InvalidAgeException {

    if (age < 18) {
        throw new InvalidAgeException("Age must be 18 or above");
    }
}
```

Here:

```java
throws InvalidAgeException
```

declares that the method may throw the exception.

And:

```java
throw new InvalidAgeException(...)
```

actually creates and throws it.

---

# 34. Custom Exceptions

Java allows developers to create their own exception classes.

These are called **custom exceptions**.

Why?

Because generic exceptions do not always clearly describe domain-specific problems.

For example:

```text
Invalid age
Invalid account state
Insufficient balance
Invalid employee ID
Invalid order state
```

A custom exception can communicate the exact business problem.

The page 3 notes specifically demonstrate an `InvalidAgeException`. 

---

# 35. Creating a Custom Exception

A common approach is:

```java
class InvalidAgeException extends Exception {

    public InvalidAgeException(String msg) {
        super(msg);
    }
}
```

The important parts are:

```text
InvalidAgeException
       ↓
extends Exception
```

and:

```java
super(msg);
```

---

# 36. Why Call `super(message)`?

The parent `Exception` class already supports storing an exception message.

So:

```java
public InvalidAgeException(String msg) {
    super(msg);
}
```

passes the message to the parent class.

Conceptually:

```text
"Age must be 18+"
       ↓
InvalidAgeException
       ↓
super(message)
       ↓
Exception
       ↓
getMessage()
```

Then:

```java
catch (InvalidAgeException e) {
    System.out.println(e.getMessage());
}
```

can retrieve that message.

The page 3 handwritten implementation explicitly shows the custom exception constructor calling `super(msg)`. 

---

# 37. Complete Custom Exception Example

```java
class InvalidAgeException extends Exception {

    public InvalidAgeException(String message) {
        super(message);
    }
}
```

Use it:

```java
static void checkAge(int age)
        throws InvalidAgeException {

    if (age < 18) {
        throw new InvalidAgeException(
            "Age must be at least 18"
        );
    }
}
```

Handle it:

```java
public static void main(String[] args) {

    try {
        checkAge(15);
    }
    catch (InvalidAgeException e) {
        System.out.println(e.getMessage());
    }
}
```

Flow:

```text
age = 15
   ↓
checkAge()
   ↓
age < 18
   ↓
throw InvalidAgeException
   ↓
caller
   ↓
catch
   ↓
getMessage()
```

---

# 38. Custom Exceptions Can Store Extra Data

A custom exception does not have to contain only a message.

You can add additional fields.

For example:

```java
class InvalidAgeException extends Exception {

    private final int age;

    public InvalidAgeException(int age, String message) {
        super(message);
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}
```

Now the exception carries both:

```text
message
+
invalid age
```

This is useful when the code handling the exception needs more context.

---

# 39. Why Add Extra Information?

Imagine an API receives:

```text
age = 15
```

Instead of only:

```text
"Invalid age"
```

the exception can carry:

```text
message = "Age must be at least 18"
age = 15
```

Then:

```java
catch (InvalidAgeException e) {
    System.out.println(e.getMessage());
    System.out.println("Received age: " + e.getAge());
}
```

The video specifically points out that custom exceptions can contain **extra fields and getter methods** to carry more context. 

---

# 40. Why Custom Exceptions Are Useful

They make the code communicate domain-specific meaning.

Compare:

```java
throw new Exception("Invalid input");
```

with:

```java
throw new InvalidAgeException("Age must be at least 18");
```

The second version is much clearer.

The exception type itself communicates the problem:

```text
InvalidAgeException
      ↓
This is specifically an age validation problem.
```

---

# 41. Try-with-Resources

The final major topic is **try-with-resources**.

It is designed to make resource management easier.

Typical resources include:

* files,
* database connections,
* network resources,
* other objects that need to be closed after use.

The page 3 notes list:

```text
Resource
 ├── File
 ├── DB Connection
 └── ...
```

and then introduce `try-with-resources`. 

---

# 42. The Problem with Manual Resource Cleanup

Traditionally, we might write:

```java
Resource resource = ...;

try {
    // use resource
}
catch (Exception e) {
    // handle exception
}
finally {
    resource.close();
}
```

The `finally` block is used to make sure cleanup happens.

But this can become verbose and error-prone, especially when multiple resources are involved.

---

# 43. Try-with-Resources Syntax

With try-with-resources, put the resource inside the parentheses after `try`:

```java
try (Resource resource = ...) {
    // use resource
}
catch (Exception e) {
    // handle exception
}
```

Java automatically handles closing the resource.

The video specifically says to define the resource inside the `try` parentheses and let Java handle the closing automatically. 

---

# 44. File Example

The handwritten page 3 example shows a `FileReader` being created in the try-with-resources section:

```java
try (FileReader fr = new FileReader("abc.txt")) {
    // use reader
}
catch (FileNotFoundException e) {
    // handle exception
}
```

The important part is:

```java
try (FileReader fr = new FileReader("abc.txt"))
```

The resource is declared inside `try`.



---

# 45. What Happens Automatically?

Conceptually:

```text
try-with-resources
        ↓
open resource
        ↓
use resource
        ↓
try finishes
        ↓
Java closes resource automatically
```

Even when an exception occurs, Java's try-with-resources mechanism handles the resource closing.

This removes the need for the programmer to manually write the normal `close()` cleanup in `finally`.

---

# 46. Traditional `finally` vs Try-with-Resources

### Traditional approach

```java
FileReader fr = new FileReader("abc.txt");

try {
    // use fr
}
finally {
    fr.close();
}
```

### Try-with-resources

```java
try (FileReader fr = new FileReader("abc.txt")) {
    // use fr
}
```

The second version is cleaner.

---

# 47. When Should You Use Try-with-Resources?

Use it when working with resources that need to be closed.

Common examples:

```text
File
Database connection
Input/output stream
Reader/Writer
Network resource
```

Mental model:

```text
Resource needs closing?
        ↓
Use try-with-resources
```

---

# 48. Full Advanced Exception Flow

All the concepts can be connected:

```text
                         Throwable
                        /         \
                     Error      Exception
                                  │
                    ┌─────────────┴─────────────┐
                    │                           │
                Checked                  RuntimeException
                    │                       (Unchecked)
                    │                           │
               IOException              NullPointerException
               SQLException             ArithmeticException
               ClassNotFoundException   ArrayIndexOutOfBoundsException
```

Then exception handling operates around this hierarchy:

```text
                  risky code
                      ↓
                    try
                      ↓
                 exception
                      ↓
             matching catch?
                /          \
              yes           no
               ↓             ↓
            handle       propagate
                             ↓
                       outer method/block
                             ↓
                         matching catch
```

And cleanup:

```text
try
 ↓
catch
 ↓
finally
```

or, preferably for closeable resources:

```text
try-with-resources
        ↓
automatic cleanup
```

---

# 49. Nested Try-Catch vs Exception Propagation

These concepts are related but different.

### Nested `try-catch`

The exception moves between **nested blocks**:

```text
Outer try
   ↓
Inner try
   ↓
Inner catch
   ↓
Outer catch
```

### Method propagation

The exception moves between **methods**:

```text
main()
  ↓
methodA()
  ↓
methodB()
  ↓
exception
  ↓
methodA()
  ↓
main()
```

In both cases, Java searches for an appropriate handler.

---

# 50. Exception Handling Decision Tree

When an exception occurs, think like this:

```text
Exception occurs
      ↓
Can current catch handle it?
   /          \
 Yes           No
  ↓             ↓
Handle       Propagate
               ↓
        Outer/caller handler
               ↓
         Can it handle it?
          /          \
        Yes           No
         ↓             ↓
      Handle       Continue
                    propagating
                        ↓
                  no handler found
                        ↓
                 JVM default handling
                        ↓
                  stack trace
                        ↓
                   termination
```

---

# 51. Common Mistakes / Gotchas

## 1. Putting generic `catch` before specific `catch`

Bad:

```java
catch (Exception e) {
}

catch (ArithmeticException e) {
}
```

Correct:

```java
catch (ArithmeticException e) {
}

catch (Exception e) {
}
```

Remember:

```text
specific → generic
```

---

## 2. Overusing nested `try-catch`

Nested blocks are legal, but too many levels make control flow difficult to understand.

Use them only when the inner operation genuinely needs separate handling.

---

## 3. Confusing `throw` and `throws`

Remember:

```text
throw  → actually throw
throws → declare possible exception
```

---

## 4. Using generic exceptions everywhere

Prefer a meaningful exception type when possible:

```java
throw new InvalidAgeException(...);
```

instead of:

```java
throw new Exception(...);
```

when the problem has a specific domain meaning.

---

## 5. Forgetting `super(message)` in a custom exception

A common custom exception pattern is:

```java
public InvalidAgeException(String message) {
    super(message);
}
```

This makes the message available through:

```java
e.getMessage()
```

---

## 6. Manually closing resources when try-with-resources is appropriate

Instead of:

```java
try {
    // use resource
}
finally {
    resource.close();
}
```

prefer:

```java
try (Resource resource = ...) {
    // use resource
}
```

when the resource supports try-with-resources.

---

## 7. Thinking `RuntimeException` must always be caught

Runtime exceptions are **unchecked**.

The compiler does not force you to catch or declare them.

That does not mean they should be ignored. It means the compiler does not impose the same checked-exception requirement.

---

# 52. Interview Revision Sheet

### What is nested try-catch?

A `try-catch` block placed inside another `try`, `catch`, or related control structure.

### What happens if the inner catch matches?

The inner catch handles the exception locally.

### What happens if it does not match?

The exception can propagate to an outer handler.

### Should nested try-catch always be used?

No. Avoid unnecessary nesting because it makes control flow harder to understand.

### What is the root class of Java's exception hierarchy?

```java
Throwable
```

### What are its two major branches?

```text
Error
Exception
```

### What is a checked exception?

An exception for which the compiler requires the program to handle or declare it.

### What is an unchecked exception?

A `RuntimeException` or its subclass that the compiler does not require you to catch or declare.

### Give examples of checked exceptions.

```text
IOException
SQLException
ClassNotFoundException
```

### Give examples of unchecked exceptions.

```text
NullPointerException
ArithmeticException
ArrayIndexOutOfBoundsException
IllegalArgumentException
IllegalStateException
```

### Name three useful exception methods.

```java
getMessage()
printStackTrace()
toString()
```

### What is the order of multiple catch blocks?

```text
Specific → Generic
```

### What is multi-catch?

Handling multiple exception types in one `catch`:

```java
catch (ArithmeticException | NullPointerException e)
```

### What is `throw`?

Used to explicitly throw an exception.

### What is `throws`?

Used in a method declaration to declare that the method may throw an exception.

### What is a custom exception?

An exception class created by the developer for a domain-specific problem.

### How do you commonly create one?

```java
class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}
```

### Why use `super(message)`?

To pass the message to the parent `Exception` class.

### Can custom exceptions contain extra information?

Yes. Add fields and getter methods.

### What is try-with-resources?

A Java construct that automatically closes resources declared inside the `try` parentheses.

---

# 53. Key Takeaways

* Java supports **nested `try-catch` blocks**.
* If the inner `catch` matches, the exception is handled locally.
* If it does not match, the exception can propagate outward.
* Avoid unnecessary nested `try-catch` because it makes control flow harder to read.
* The exception hierarchy starts with:

```text
Throwable
├── Error
└── Exception
```

* `Error` represents serious problems such as:

  * `OutOfMemoryError`
  * `StackOverflowError`
* `Exception` represents conditions that application code can generally handle.
* `RuntimeException` represents **unchecked exceptions**.
* Examples include:

  * `NullPointerException`
  * `ArithmeticException`
  * `ArrayIndexOutOfBoundsException`
  * `IllegalArgumentException`
  * `IllegalStateException`
* Checked exceptions include examples such as:

  * `IOException`
  * `SQLException`
  * `ClassNotFoundException`
* Useful exception methods are:

```java
getMessage()
printStackTrace()
toString()
```

* Multiple `catch` blocks are checked **top to bottom**.
* Always place **specific exceptions before generic exceptions**.
* Java 7 introduced **multi-catch**:

```java
catch (A | B e)
```

* `throw` actually throws an exception.
* `throws` declares that a method may throw an exception.
* Custom exceptions allow domain-specific error handling.
* Custom exceptions commonly extend `Exception`.
* `super(message)` passes the message to the parent exception class.
* Custom exceptions can contain additional fields and getters for richer error context.
* **Try-with-resources** automatically closes resources.
* It is especially useful for files, database connections, and I/O resources.
* The overall goal is not simply "catch every exception." The goal is to create **clear, controlled, and maintainable failure handling**.

---

# 54. Minimal Self-Test

1. What is a nested `try-catch`?
2. What happens when an inner `catch` successfully handles an exception?
3. What happens when no inner `catch` matches?
4. Why should unnecessary nested `try-catch` blocks be avoided?
5. What is the root of Java's exception hierarchy?
6. What are the two major subclasses of `Throwable`?
7. What is the difference between `Error` and `Exception`?
8. What is a checked exception?
9. What is an unchecked exception?
10. Why is `RuntimeException` unchecked?
11. Give three examples of checked exceptions.
12. Give three examples of unchecked exceptions.
13. What does `getMessage()` do?
14. What does `printStackTrace()` do?
15. What does `toString()` do?
16. Why must specific `catch` blocks come before generic ones?
17. What would happen if `catch (Exception e)` came before `catch (ArithmeticException e)`?
18. What is multi-catch?
19. When was multi-catch introduced?
20. What is the difference between `throw` and `throws`?
21. Where is `throw` used?
22. Where is `throws` used?
23. Why would you create a custom exception?
24. How do you create `InvalidAgeException`?
25. Why is `super(message)` used in a custom exception constructor?
26. How can a custom exception carry additional context?
27. What problem does try-with-resources solve?
28. Where is the resource declared in try-with-resources?
29. What happens to the resource after the `try` finishes?
30. When would you prefer try-with-resources over manual cleanup in `finally`?
