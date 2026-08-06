## Hook: Why Doesn't a Simple `for` Loop Work for Every Collection?

 Different collections store data differently. An `ArrayList` stores elements in contiguous memory and supports fast index-based access, while a `LinkedList` stores elements as connected nodes, and a `HashSet` doesn't even maintain an index. Because of these differences, Java cannot rely on a single index-based traversal mechanism. Instead, it introduces the **Iterable** and **Iterator** interfaces, which provide a common way to traverse every collection without exposing its internal implementation. 

 ---

 # Iterable Interface in Java

 ## What is Iterable?

 `Iterable` is the **root interface** for any object whose elements can be traversed one by one.

 Any class that implements `Iterable` promises that its elements can be accessed sequentially.

 This is why most classes in the Java Collection Framework can be used inside a **for-each loop**.

 Collection hierarchy:

 ```text
 Object
    │
 Iterable
    │
 Collection
 ┌──┴──────────────┐
 │      │         │
 List   Set     Queue
 ```

 `Map` is not part of the `Collection` hierarchy, although it provides its own ways to iterate over keys, values, and entries. The hierarchy is shown in the lecture notes. 

 ---

 # The Problem with Index-Based Traversal

 Consider an `ArrayList`.

 ```java
 List<Integer list = new ArrayList<();

 for (int i = 0; i < list.size(); i++) {
     System.out.println(list.get(i));
 }
 ```

 This works because `ArrayList` provides **random access**.

 ```text
 Index → Element
 ```

 Each call to `get(i)` is approximately **O(1)**.

 ---

 ## What About LinkedList?

 A `LinkedList` stores data as nodes.

 ```text
 Head
  ↓
 2 → 3 → 4 → 5
 ```

 To access the fourth element,

 Java must start from the head and follow each reference.

 ```text
 Head
 ↓
 2 → 3 → 4 → 5
 ```

 So,

 ```java
 list.get(i);
 ```

 requires **O(n)** time.

 If we repeatedly call `get(i)` inside a loop:

 ```java
 for (int i = 0; i < list.size(); i++) {
     list.get(i);
 }
 ```

 Total complexity becomes:

 ```text
 O(n²)
 ```

 which is highly inefficient.

 ---

 ## What About HashSet?

 `HashSet` stores data using hashing.

 It:

 * has no index
 * does not support `get(i)`
 * may not preserve insertion order

 Therefore, an index-based loop cannot even be written.

 This demonstrates why Java needs a traversal mechanism independent of the underlying data structure. 

 ---

 # The Solution: Iterator

 Instead of exposing internal storage,

 every iterable collection provides an **Iterator**.

 The collection only supplies the iterator.

 The iterator performs the traversal.

 This design works for:

 * ArrayList
 * LinkedList
 * HashSet
 * Queue
 * TreeSet
 * and other iterable collections.

 ---

 # Iterable Interface

 The `Iterable` interface defines a single essential method:

 ```java
 interface Iterable<T {

     Iterator<T iterator();

 }
 ```

 Every iterable collection implements this method.

 Example:

 ```java
 Iterator<Integer it = list.iterator();
 ```

 Calling `iterator()` creates a new iterator object responsible for traversing that collection. 

 ---

 # Iterator Interface

 The `Iterator` interface contains the traversal logic.

 Its two primary methods are:

 ```java
 boolean hasNext();

 T next();
 ```

 * `hasNext()` checks whether another element exists.
 * `next()` returns the next element and advances the iterator.

 The lecture notes also show additional methods such as `remove()` and `forEachRemaining()`, though `hasNext()` and `next()` are the core operations. 

 ---

 # Using an Iterator

 ```java
 List<Integer list = new ArrayList<();

 Iterator<Integer it = list.iterator();

 while (it.hasNext()) {
     System.out.println(it.next());
 }
 ```

 The process is:

 ```text
 Create Iterator
       ↓
 hasNext()
       ↓
 next()
       ↓
 Repeat until finished
 ```

 ---

 # How ArrayList Uses an Iterator

 Conceptually,

 ```java
 class ArrayList implements Iterable<Integer {

     private int[] arr;
     private int size;

     @Override
     public Iterator<Integer iterator() {
         return new ArrayListIterator();
     }
 }
 ```

 The iterator maintains its own position:

 ```java
 class ArrayListIterator implements Iterator<Integer {

     int pos = 0;

     public boolean hasNext() {
         return pos < size;
     }

     public Integer next() {
         return arr[pos++];
     }
 }
 ```

 The iterator accesses the collection's internal array and tracks traversal using a private position variable. This implementation is illustrated in the lecture diagrams. 

 ---

 # How LinkedList Uses an Iterator

 A linked list cannot use an array index.

 Instead, its iterator stores a pointer to the current node.

 ```java
 class LinkedListIterator
         implements Iterator<Integer {

     Node current = head;

     public boolean hasNext() {
         return current != null;
     }

     public Integer next() {
         int value = current.data;
         current = current.next;
         return value;
     }
 }
 ```

 Here,

 traversal happens by following references:

 ```text
 Head
  ↓
 2 → 3 → 4 → 5
 ```

 rather than using indexes. 

 ---

 # Separation of Concerns

 Java separates:

 ```text
 Collection
 ```

 from

 ```text
 Traversal Logic
 ```

 The collection only stores data.

 The iterator knows how to move through that data.

 This follows the **Single Responsibility Principle (SRP)**:

 * Collection → manages storage.
 * Iterator → manages traversal.

 This separation makes collections simpler, easier to maintain, and allows different traversal strategies without changing the collection itself. The notes explicitly highlight this design principle. 

 ---

 # Why Use Nested Classes?

 Collection classes often implement iterators as **private nested classes**.

 Example:

 ```java
 class ArrayList {

     private int[] arr;

     private class ArrayListIterator
             implements Iterator<Integer {

     }
 }
 ```

 Benefits:

 * Direct access to private fields.
 * No need for getters.
 * Better encapsulation.
 * Iterator implementation remains hidden from users.

 This design is reflected in the lecture's conceptual implementation of `ArrayList` and `LinkedList`. 

 ---

 # Why Doesn't the Collection Store the Traversal Position?

 Imagine:

 ```java
 class MyArrayList {

     int pos;

     boolean hasNext() { ... }

     int next() { ... }
 }
 ```

 Consider nested loops:

 ```java
 while (list.hasNext()) {

     int a = list.next();

     while (list.hasNext()) {

         int b = list.next();

     }
 }
 ```

 Both loops share the same `pos` variable.

 The inner loop changes the traversal state of the outer loop, causing incorrect behavior.

 The lecture demonstrates this problem using a custom collection with a shared `pos` variable. 

 ---

 # The Real Solution

 Every call to:

 ```java
 list.iterator();
 ```

 creates a **new iterator object**.

 ```java
 Iterator<Integer it1 = list.iterator();

 Iterator<Integer it2 = list.iterator();
 ```

 Each iterator has its own independent position:

 ```text
 it1 → pos = 0

 it2 → pos = 0
 ```

 Therefore,

 nested loops work correctly because each iterator maintains its own traversal state. This independent state management is one of the key reasons Java uses separate iterator objects. 

 ---

 # Enhanced For Loop (`for-each`)

 The following code:

 ```java
 for (Integer value : list) {
     System.out.println(value);
 }
 ```

 is only syntactic sugar.

 Internally,

 Java converts it to something similar to:

 ```java
 Iterator<Integer it = list.iterator();

 while (it.hasNext()) {
     Integer value = it.next();
     System.out.println(value);
 }
 ```

 Therefore, every enhanced `for` loop uses an iterator behind the scenes.

 ---

 # Concurrent Modification

 Suppose:

 ```java
 Iterator<Integer it = list.iterator();
 ```

 While iterating,

 another operation modifies the collection:

 ```java
 list.add(100);
 ```

 or

 ```java
 list.remove(2);
 ```

 The iterator detects that the underlying collection has changed.

 Result:

 ```text
 ConcurrentModificationException
 ```

 ---

 # Fail-Fast Behavior

 Java iterators are **fail-fast**.

 Instead of continuing with inconsistent data,

 they immediately throw:

 ```text
 ConcurrentModificationException
 ```

 This prevents unpredictable traversal results and helps maintain data integrity.

 ---

 # Safe Removal During Iteration

 Incorrect:

 ```java
 while (it.hasNext()) {

     Integer x = it.next();

     list.remove(x);
 }
 ```

 This may throw:

 ```text
 ConcurrentModificationException
 ```

 Correct:

 ```java
 while (it.hasNext()) {

     Integer x = it.next();

     if (x % 2 == 0)
         it.remove();
 }
 ```

 `iterator.remove()` safely updates both the iterator's internal state and the collection, avoiding concurrent modification problems.

 ---

 # Key Takeaways

 * Different collections require different traversal strategies because they store data differently.
 * Index-based loops work efficiently for `ArrayList` but are inefficient for `LinkedList` and impossible for collections such as `HashSet`.
 * `Iterable` is the root interface for objects whose elements can be traversed one by one.
 * `Iterable` provides the `iterator()` method, which returns an `Iterator`.
 * `Iterator` performs traversal using `hasNext()` and `next()`.
 * `ArrayList` iterators track an index, while `LinkedList` iterators follow node references.
 * Java separates storage and traversal responsibilities, following the **Single Responsibility Principle**.
 * Iterators are often implemented as private nested classes to access collection internals while preserving encapsulation.
 * Each call to `iterator()` creates a new iterator with independent traversal state, allowing nested iterations to work correctly.
 * The enhanced `for` loop is implemented internally using an `Iterator`.
 * Modifying a collection during iteration can cause `ConcurrentModificationException` because iterators are fail-fast.
 * Use `iterator.remove()` instead of modifying the collection directly while iterating. 
