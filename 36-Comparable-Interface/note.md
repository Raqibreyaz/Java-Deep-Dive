# Comparable Interface and Collections Utility Class in Java



## One-sentence summary

**`Comparable` defines the natural ordering of objects of a custom class, while the `Collections` utility class provides ready-made static methods for sorting, searching, reversing, shuffling, and safely handling collections.**

---

# 1. Why Do We Need `Comparable`?

Java already knows how to compare many built-in types.

For example:

```java
int a = 10;
int b = 20;
```

Java knows that:

```text
10 < 20
```

Similarly, `String` has a predefined **lexicographical order**.

For example:

```java
"Apple"
"Banana"
"Cat"
```

Strings can be compared because `String` already provides comparison behavior.

---

## The problem with custom classes

Suppose we create a `Student` class:

```java
class Student {
    int marks;
    String name;
}
```

Now suppose we have:

```java
Student s1 = new Student(95, "Raj");
Student s2 = new Student(85, "Amit");
```

Which student is smaller?

```text
s1 < s2 ?
s1 > s2 ?
s1 == s2 ?
```

Java cannot automatically know what **"smaller"** means for a `Student`.

Should students be compared by:

* marks?
* name?
* age?
* roll number?

There is no obvious natural ordering unless we define one.

The first page of the uploaded notes uses exactly this `Student` example and connects the problem to `Collections.sort()`, `TreeSet`, and `TreeMap`. 

---

# 2. Where Does This Problem Appear?

The problem becomes visible when we use operations that need objects to be ordered.

For example:

```java
Collections.sort(students);
```

or structures such as:

```java
TreeSet<Student>
TreeMap<Student, ...>
```

These operations need to know:

> How should two `Student` objects be compared?

Without that information, Java cannot establish the required ordering.

---

# 3. `Comparable` Interface

Java provides the `Comparable` interface for this purpose.

Conceptually:

```text
Custom class
     ↓
implements Comparable
     ↓
defines natural ordering
     ↓
Java knows how to compare objects
```

The interface contains:

```java
interface Comparable<T> {
    int compareTo(T o);
}
```

So a class implementing `Comparable` is making a **contract**:

> "I know how to compare my objects with another object of the same type."

The notes describe `Comparable` as a contract and show `compareTo(T o)` as its core method. 

---

# 4. Implementing `Comparable`

Suppose we want to sort students according to their marks.

We can write:

```java
class Student implements Comparable<Student> {

    int marks;
    String name;

    @Override
    public int compareTo(Student other) {
        return this.marks - other.marks;
    }
}
```

The important part is:

```java
implements Comparable<Student>
```

and:

```java
public int compareTo(Student other)
```

Now Java knows how one `Student` compares to another `Student`.

---

# 5. Understanding `compareTo()`

The return value of `compareTo()` is extremely important.

```java
int result = s1.compareTo(s2);
```

There are three possibilities:

```text
result < 0
    ↓
s1 is smaller than s2


result > 0
    ↓
s1 is greater than s2


result == 0
    ↓
s1 and s2 are considered equal in ordering
```

The notes on pages 2 and 3 illustrate these three cases explicitly. 

---

# 6. Example with Marks

Suppose:

```text
s1 → marks = 10
s2 → marks = 20
```

And:

```java
@Override
public int compareTo(Student other) {
    return this.marks - other.marks;
}
```

Then:

```java
s1.compareTo(s2)
```

becomes:

```text
10 - 20
= -10
```

So the result is negative.

Therefore:

```text
s1 < s2
```

---

## Reverse comparison

Now:

```java
s2.compareTo(s1)
```

gives:

```text
20 - 10
= 10
```

Positive.

Therefore:

```text
s2 > s1
```

The uploaded notes demonstrate both directions with concrete student values. 

---

# 7. The Three `compareTo()` Results

Always remember:

```text
                 compareTo()
                     │
          ┌──────────┼──────────┐
          ↓          ↓          ↓
        -ve          0         +ve
          ↓          ↓          ↓
       smaller     equal      larger
```

For example:

```java
a.compareTo(b)
```

If:

```text
a = 10
b = 20
```

then:

```text
-10 → a comes before b
```

If:

```text
a = 20
b = 10
```

then:

```text
+10 → a comes after b
```

If:

```text
a = 10
b = 10
```

then:

```text
0 → same ordering position
```

---

# 8. Natural Ordering

The ordering defined by `Comparable` is called the **natural ordering** of the class.

For example, if we define:

```java
class Student implements Comparable<Student>
```

and choose marks as the comparison field:

```java
return this.marks - other.marks;
```

then we are saying:

> "The natural ordering of `Student` is based on marks."

The notes on page 3 explicitly connect:

```text
Comparable
    ↓
Natural Ordering
    ↓
compareTo()
    ↓
sort()
```



---

# 9. `Collections.sort()` Uses Natural Ordering

Once `Student` implements `Comparable`, we can use:

```java
Collections.sort(studentList);
```

Java can now determine how to order the students because `Student` has defined its natural ordering through `compareTo()`.

Conceptually:

```text
List<Student>
      ↓
Collections.sort()
      ↓
compareTo()
      ↓
Natural ordering
      ↓
Sorted students
```

---

# 10. Multiple-Level Sorting

Sometimes one field is not enough.

Suppose we have:

```java
class Student implements Comparable<Student> {

    int marks;
    String name;
}
```

We want:

1. Sort by marks.
2. If marks are equal, sort by name.

This is called **multi-level sorting**.

---

## Step 1: Compare marks

```java
if (this.marks != other.marks) {
    return this.marks - other.marks;
}
```

If marks are different, we are done.

---

## Step 2: Compare names

If marks are equal:

```java
return this.name.compareTo(other.name);
```

Complete logic:

```java
@Override
public int compareTo(Student other) {

    if (this.marks != other.marks) {
        return this.marks - other.marks;
    }

    return this.name.compareTo(other.name);
}
```

The video notes specifically show this pattern: compare marks first, and when marks are equal, use `String.compareTo()` for the names. 

---

# 11. Why Does `String.compareTo()` Work?

`String` already has its own comparison behavior.

For example:

```java
"Ajay".compareTo("Rohit")
```

uses lexicographical ordering.

So our custom class can reuse it:

```java
this.name.compareTo(other.name)
```

This gives us another level of comparison without writing the string comparison logic ourselves.

---

# 12. Example: Multi-Level Sorting

Suppose we have:

```text
Student("Ajay", 20)
Student("Rohit", 20)
Student("Amit", 10)
```

First compare marks:

```text
Amit  → 10
Ajay  → 20
Rohit → 20
```

Now `Ajay` and `Rohit` have equal marks.

So compare names:

```text
Ajay < Rohit
```

Final ordering:

```text
Amit
Ajay
Rohit
```

The important idea is:

```text
marks different?
    ↓ yes
compare marks

marks same?
    ↓ yes
compare names
```

---

# 13. The Dangerous Meaning of Returning `0`

One of the most important points in the notes is the **danger of returning `0`**.

Suppose we write:

```java
@Override
public int compareTo(Student other) {
    return this.marks - other.marks;
}
```

Now consider:

```text
Student 1 → marks = 90, name = "Raj"
Student 2 → marks = 90, name = "Amit"
```

The students are clearly different objects.

But:

```text
90 - 90 = 0
```

Therefore:

```java
student1.compareTo(student2)
```

returns:

```text
0
```

---

# 14. Why Is This Dangerous?

For ordered data structures such as:

```java
TreeSet
TreeMap
```

a comparison result of `0` means the objects are considered equivalent for ordering purposes.

So the structure can treat:

```text
Student("Raj", 90)
Student("Amit", 90)
```

as duplicates.

This can cause one of them to be ignored.

The notes on page 3 specifically highlight this danger and connect `compareTo() == 0` with duplicate handling in `TreeSet` and `TreeMap`. 

---

# 15. Fixing the `0` Problem

Use a second comparison field.

Instead of:

```java
return this.marks - other.marks;
```

use:

```java
if (this.marks != other.marks) {
    return this.marks - other.marks;
}

return this.name.compareTo(other.name);
```

Now:

```text
Raj, 90
Amit, 90
```

will not compare as `0` because:

```text
"Raj".compareTo("Amit")
```

is non-zero.

So the objects can both exist in an ordered set.

---

# 16. Important Rule for `compareTo()`

The notes give a useful rule:

> If `a.compareTo(b) == 0`, make sure `a.equals(b)` is also true.

Conceptually:

```text
a.compareTo(b) == 0
          ↓
   treated as equal
          ↓
   a.equals(b) should
      also be true
```

This is especially important when using:

```text
TreeSet
TreeMap
```

because their duplicate behavior depends on ordering.

The rule is highlighted on page 3 of the uploaded notes. 

---

# 17. `Comparable` and One Natural Ordering

A useful point from the notes is:

```text
One class
    ↓
One natural ordering
```

For example:

```java
Student implements Comparable<Student>
```

could define the natural ordering as:

```text
marks ascending
```

But what if sometimes we want:

```text
sort by marks
```

and other times:

```text
sort by name
```

A class can have only one `compareTo()` definition representing its natural ordering.

This is where `Comparator` becomes useful.

The page 4 notes briefly introduce `Comparator` alongside `Student` and fields such as marks and name. 

---

# 18. `Comparable` vs `Comparator`

The key idea from the notes is:

| `Comparable`              | `Comparator`                        |
| ------------------------- | ----------------------------------- |
| Defines natural ordering  | Defines alternative/custom ordering |
| Implemented by the class  | Separate comparison strategy        |
| Uses `compareTo()`        | Uses `compare()`                    |
| One main natural ordering | Can define multiple orderings       |

Example:

```text
Student
 ├── natural ordering → marks
 ├── alternative      → name
 └── alternative      → marks descending
```

For this topic, focus first on `Comparable` and `compareTo()`.

---

# 19. `Comparable` Is a Functional Interface

The notes describe `Comparable` as a **functional interface** because it has one abstract method:

```java
int compareTo(T o);
```

So the core contract is very small:

```text
Comparable<T>
      ↓
compareTo(T)
```

The important thing is that implementing the interface means your class provides this comparison behavior.

---

# 20. `Collections` Utility Class

Now we move to the second major topic.

Do not confuse:

```text
Collection
```

with:

```text
Collections
```

They are different.

---

## `Collection`

`Collection` is an interface.

It represents a group of objects and is part of the Java Collections Framework.

Examples include:

```text
List
Set
Queue
```

---

## `Collections`

`Collections` is a **utility class**.

It provides many **static methods** for working with collections.

The notes on page 4 explicitly describe it as a utility class containing static methods. 

Think:

```text
Collection
    ↓
interface

Collections
    ↓
utility class
    ↓
static helper methods
```

---

# 21. `Collections.sort()`

`sort()` sorts elements.

Example:

```java
List<Integer> list =
    new ArrayList<>(List.of(40, 10, 30, 20));

Collections.sort(list);
```

Result:

```text
[10, 20, 30, 40]
```

For objects, sorting can use their natural ordering through `Comparable`.

```java
Collections.sort(studentList);
```

This eventually relies on the comparison behavior defined by `compareTo()`.

---

# 22. `Collections.max()`

`max()` finds the maximum element.

```java
Collections.max(list);
```

For:

```text
[10, 20, 30, 40]
```

result:

```text
40
```

The notes on page 4 include both `max()` and `min()` as utility methods. 

---

# 23. `Collections.min()`

`min()` finds the minimum element.

```java
Collections.min(list);
```

For:

```text
[10, 20, 30, 40]
```

result:

```text
10
```

---

# 24. `Collections.fill()`

`fill()` replaces every element in a list with the specified value.

Example:

```java
List<Integer> list =
    new ArrayList<>(List.of(10, 20, 30, 40));

Collections.fill(list, 0);
```

Result:

```text
[0, 0, 0, 0]
```

The notes show `fill(list, 0)` and illustrate the entire collection being replaced with `0`. 

### Important

`fill()` does not change the size of the list.

It changes the values already present.

---

# 25. `Collections.reverse()`

`reverse()` reverses the order of elements.

Example:

```java
List<Integer> list =
    new ArrayList<>(List.of(10, 20, 30, 40));

Collections.reverse(list);
```

Result:

```text
[40, 30, 20, 10]
```

---

# 26. `Collections.shuffle()`

`shuffle()` randomly rearranges elements.

Example:

```java
Collections.shuffle(list);
```

A list such as:

```text
[10, 20, 30, 40]
```

might become:

```text
[30, 10, 40, 20]
```

The exact result is not fixed because the purpose is to randomize the order.

The uploaded notes list `shuffle()` among the main `Collections` utility methods. 

---

# 27. `Collections.swap()`

The page 4 notes also include:

```java
Collections.swap(list, a, b);
```

It swaps the elements at two positions.

Example:

```java
List<Integer> list =
    new ArrayList<>(List.of(10, 20, 30));

Collections.swap(list, 0, 2);
```

Result:

```text
[30, 20, 10]
```

So:

```text
index 0 ↔ index 2
```

---

# 28. `Collections.binarySearch()`

`binarySearch()` searches for an element efficiently in a **sorted list**.

The important requirement is:

> The list should already be sorted according to the same ordering being used for the search.

Example:

```java
List<Integer> list =
    new ArrayList<>(List.of(10, 20, 30, 40, 50));

int index = Collections.binarySearch(list, 30);
```

The result is the position of `30`.

Conceptually, binary search repeatedly divides the search space:

```text
[10, 20, 30, 40, 50]
         ↑
       middle
```

This gives logarithmic search behavior:

```text
O(log n)
```

The notes specifically identify `binarySearch()` as a logarithmic-time operation. 

---

# 29. Why Must the List Be Sorted?

Binary search relies on ordering.

For:

```text
[10, 20, 30, 40, 50]
```

we can decide:

```text
target < middle
```

or:

```text
target > middle
```

and eliminate half of the remaining elements.

But with:

```text
[40, 10, 50, 20, 30]
```

we cannot make those decisions reliably.

So remember:

```text
binarySearch()
      ↓
sorted list required
      ↓
O(log n)
```

---

# 30. `Collections.frequency()`

`frequency()` counts how many times an element occurs.

Example:

```java
List<Integer> list =
    List.of(10, 20, 10, 30, 10);

int count = Collections.frequency(list, 10);
```

Result:

```text
3
```

Because `10` appears three times.

The notes include `frequency(list, e)` as one of the utility methods. 

---

# 31. `Collections.unmodifiableList()`

Sometimes you want to return a list but do not want the caller to modify it.

For this, the `Collections` utility class provides:

```java
Collections.unmodifiableList(list);
```

Conceptually:

```text
Original List
     ↓
unmodifiableList()
     ↓
Read-only view
```

The notes specifically describe this as a way to create a list that is **read-only**.

---

## Example

```java
List<Integer> list =
    new ArrayList<>(List.of(10, 20, 30));

List<Integer> readOnly =
    Collections.unmodifiableList(list);
```

The caller can read the values, but cannot use the returned reference to perform modifications.

This is useful when designing APIs where you want to prevent callers from modifying an exposed collection.

---

# 32. Unmodifiable Set and Map

The notes also show corresponding utility methods for other collection types:

```java
Collections.unmodifiableSet(set);
Collections.unmodifiableMap(map);
```

So the idea applies beyond lists:

```text
List → unmodifiableList()
Set  → unmodifiableSet()
Map  → unmodifiableMap()
```

The purpose is the same:

> Provide a non-modifiable view of the collection.

---

# 33. `Collections.emptyList()`

The notes also highlight:

```java
Collections.emptyList();
```

It provides an empty list.

Instead of returning:

```java
null
```

from an API, we can return an empty collection.

For example:

```java
return Collections.emptyList();
```

This is useful because the caller can safely work with the returned collection without first checking whether the reference itself is `null`.

---

# 34. Why Empty Collections Are Useful

Suppose an API returns:

```java
List<Student> findStudents() {
    // ...
}
```

If no students are found, returning:

```java
return null;
```

can force callers to write:

```java
if (students != null) {
    // use students
}
```

Otherwise they may encounter a `NullPointerException`.

Returning:

```java
return Collections.emptyList();
```

instead means:

```text
No students
    ↓
empty list
    ↓
safe to iterate
```

Example:

```java
for (Student student : findStudents()) {
    System.out.println(student);
}
```

If the result is empty, the loop simply runs zero times.

The notes specifically connect empty collections with avoiding `NullPointerException` problems when returning results from APIs. 

---

# 35. Other Empty Collection Utilities

The same idea applies to other collection types:

```java
Collections.emptyList();
Collections.emptySet();
Collections.emptyMap();
```

So:

```text
emptyList() → empty List
emptySet()  → empty Set
emptyMap()  → empty Map
```

The page 4 notes show these empty collection utilities together with the unmodifiable variants. 

---

# 36. `Collections` Utility Methods — Cheat Sheet

| Method                         | Purpose                    |
| ------------------------------ | -------------------------- |
| `sort(list)`                   | Sort elements              |
| `max(collection)`              | Find maximum               |
| `min(collection)`              | Find minimum               |
| `fill(list, value)`            | Replace every list element |
| `reverse(list)`                | Reverse order              |
| `shuffle(list)`                | Randomly rearrange         |
| `swap(list, a, b)`             | Swap two positions         |
| `binarySearch(list, key)`      | Search sorted list         |
| `frequency(collection, value)` | Count occurrences          |
| `unmodifiableList(list)`       | Read-only list view        |
| `unmodifiableSet(set)`         | Read-only set view         |
| `unmodifiableMap(map)`         | Read-only map view         |
| `emptyList()`                  | Empty list                 |
| `emptySet()`                   | Empty set                  |
| `emptyMap()`                   | Empty map                  |

The page 4 handwritten list contains these utility categories, including `max`, `min`, `swap`, and the empty/unmodifiable collection helpers. 

---

# 37. `Comparable` + `Collections.sort()`

These two concepts work together.

Suppose:

```java
class Student implements Comparable<Student> {

    int marks;

    @Override
    public int compareTo(Student other) {
        return this.marks - other.marks;
    }
}
```

Then:

```java
List<Student> students = ...;

Collections.sort(students);
```

works because:

```text
Collections.sort()
       ↓
needs ordering
       ↓
Student implements Comparable
       ↓
compareTo()
       ↓
natural ordering
       ↓
students sorted
```

This is the main connection between the two topics.

---

# 38. Complete Example

```java
import java.util.*;

class Student implements Comparable<Student> {

    int marks;
    String name;

    Student(int marks, String name) {
        this.marks = marks;
        this.name = name;
    }

    @Override
    public int compareTo(Student other) {

        // First compare marks
        if (this.marks != other.marks) {
            return this.marks - other.marks;
        }

        // If marks are equal, compare names
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return name + " - " + marks;
    }
}
```

Now:

```java
List<Student> students = new ArrayList<>();

students.add(new Student(90, "Raj"));
students.add(new Student(80, "Amit"));
students.add(new Student(90, "Ajay"));

Collections.sort(students);

System.out.println(students);
```

The natural ordering is:

```text
marks ascending
      ↓
if marks equal
      ↓
name ascending
```

So the result is conceptually:

```text
Amit - 80
Ajay - 90
Raj  - 90
```

The second-level comparison prevents the two `90`-mark students from becoming equivalent according to `compareTo()`.

---

# 39. The Big Picture

```text
                  Custom Class
                      │
                      ↓
             implements Comparable
                      │
                      ↓
                 compareTo()
                      │
              ┌───────┼───────┐
              ↓       ↓       ↓
             -ve      0      +ve
              ↓       ↓       ↓
           smaller   equal   larger
                      │
                      ↓
              Natural Ordering
                      │
             ┌────────┴────────┐
             ↓                 ↓
     Collections.sort()    TreeSet/TreeMap
```

And separately:

```text
Collections
     │
     ├── sort()
     ├── max()
     ├── min()
     ├── fill()
     ├── reverse()
     ├── shuffle()
     ├── swap()
     ├── binarySearch()
     ├── frequency()
     │
     ├── unmodifiableList()
     ├── unmodifiableSet()
     ├── unmodifiableMap()
     │
     ├── emptyList()
     ├── emptySet()
     └── emptyMap()
```

---

# 40. Common Mistakes / Gotchas

## 1. Forgetting `Comparable`

This will cause problems when Java needs a natural ordering for a custom class.

```java
class Student {
    int marks;
}
```

is not enough for:

```java
Collections.sort(students);
```

You need to define the comparison behavior.

---

## 2. Returning the wrong sign

The important contract is:

```text
negative → current object comes before other
zero     → same ordering
positive → current object comes after other
```

Do not focus on the exact value.

For example, both:

```text
-1
-10
-100
```

represent the negative case.

---

## 3. Returning `0` too easily

This is the biggest trap.

Bad:

```java
return this.marks - other.marks;
```

if two different students are allowed to have the same marks and both must coexist in a `TreeSet`.

Better:

```java
if (this.marks != other.marks) {
    return this.marks - other.marks;
}

return this.name.compareTo(other.name);
```

---

## 4. Thinking `compareTo() == 0` means the same object

It means the objects are considered **equal in ordering**.

They do not have to be the exact same object in memory.

This distinction matters especially for `TreeSet` and `TreeMap`.

---

## 5. Using `binarySearch()` on an unsorted list

Wrong idea:

```java
Collections.binarySearch(unsortedList, value);
```

Binary search relies on ordering.

Remember:

```text
sort first
   ↓
binarySearch
```

---

## 6. Confusing `Collection` and `Collections`

```text
Collection  → interface
Collections → utility class
```

This is a common interview question.

---

## 7. Returning `null` unnecessarily

For APIs returning collections, consider:

```java
Collections.emptyList()
```

instead of:

```java
return null;
```

when an empty result is the natural meaning.

---

# 41. Interview Revision Sheet

### What is `Comparable`?

An interface used by a class to define its **natural ordering**.

### What method does it provide?

```java
compareTo(T o)
```

### What does a negative result mean?

Current object comes before the other object.

### What does zero mean?

Both objects are considered equal in ordering.

### What does a positive result mean?

Current object comes after the other object.

### Why do custom classes need it?

Java does not automatically know how custom objects should be ordered.

### Where is it useful?

Especially with:

```text
Collections.sort()
TreeSet
TreeMap
```

### What is the danger of returning `0`?

Different objects may be treated as equivalent/duplicates by ordered structures such as `TreeSet` and `TreeMap`.

### What is natural ordering?

The default ordering defined by the class through `Comparable`.

### What is `Collections`?

A utility class containing static methods for manipulating collections.

### Name important `Collections` methods.

```text
sort
max
min
fill
reverse
shuffle
swap
binarySearch
frequency
unmodifiableList
unmodifiableSet
unmodifiableMap
emptyList
emptySet
emptyMap
```

---

# 42. Key Takeaways

* Java knows how to compare many built-in types, but **custom objects need comparison rules**.
* `Comparable<T>` allows a class to define its **natural ordering**.
* The main method is:

```java
int compareTo(T o)
```

* `compareTo()` returns:

  * negative → smaller/before
  * zero → equal in ordering
  * positive → larger/after
* A `Student` can implement:

```java
Comparable<Student>
```

* A simple comparison can be:

```java
return this.marks - other.marks;
```

* For multiple sorting criteria, compare another field when the first field is equal.
* Example:

```java
if (this.marks != other.marks)
    return this.marks - other.marks;

return this.name.compareTo(other.name);
```

* Be careful with returning `0`; `TreeSet` and `TreeMap` can treat such objects as duplicates/equivalent.
* A useful consistency rule is:

```text
a.compareTo(b) == 0
        ⇒
a.equals(b) should be true
```

* `Comparable` gives a class its **natural ordering**.
* `Comparator` is useful when you need **alternative orderings**.
* `Collections` is a **utility class**, not the `Collection` interface.
* `Collections.sort()` can use the natural ordering defined by `Comparable`.
* `binarySearch()` requires a sorted list and provides logarithmic-time searching.
* `unmodifiableList()`, `unmodifiableSet()`, and `unmodifiableMap()` provide non-modifiable views.
* `emptyList()`, `emptySet()`, and `emptyMap()` are useful when an API should return an empty result instead of `null`.

---

# 43. Minimal Self-Test

1. Why can't Java automatically sort two `Student` objects?
2. What is the purpose of `Comparable`?
3. What method does `Comparable` define?
4. What does a negative `compareTo()` result mean?
5. What does a positive result mean?
6. What does `0` mean?
7. What is natural ordering?
8. How would you sort `Student` objects by marks?
9. How would you sort by marks first and name second?
10. Why can returning `0` for students with the same marks be dangerous?
11. Why is `compareTo()` important for `TreeSet`?
12. Why is it important for `TreeMap`?
13. What is the difference between `Comparable` and `Comparator`?
14. What is the difference between `Collection` and `Collections`?
15. What does `Collections.sort()` do?
16. What does `Collections.fill()` do?
17. What does `Collections.reverse()` do?
18. What does `Collections.shuffle()` do?
19. What does `Collections.swap()` do?
20. Why must a list be sorted before using `binarySearch()`?
21. What does `Collections.frequency()` return?
22. Why would you use `Collections.emptyList()` instead of returning `null`?
23. What is the purpose of `unmodifiableList()`?
24. Name three empty collection utilities.
25. Name five commonly used methods from the `Collections` utility class.
