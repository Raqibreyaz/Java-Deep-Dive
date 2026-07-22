### Hook: The Variable Explosion

Imagine managing 100 students' roll numbers. Declaring `rollNo1`, `rollNo2`, ... `rollNo100` is not just tedious; it is unmanageable. You need a way to group data logically and store it efficiently in memory. This is the origin of the **Array**.

### What is an Array?

An array is a fixed-size data structure that stores a collection of elements of the same data type in **contiguous memory locations**. It allows you to access any element instantly using an index.

### One-Sentence Summary

An array is a contiguous block of memory acting as a container for multiple values of the same type, reachable via numeric offsets.

### Intuition: The Lockers Analogy

Think of an array as a row of lockers in a school hallway.

- Each locker holds the same type of item (e.g., student books).
- Each locker has a specific number (index), starting from **0**.
- Because they are in a row, if you know the location of locker #0, you can calculate the exact position of any other locker by jumping forward by a fixed size.

### Motivation: Why Contiguous Memory?

1. **Locality of Reference:** Modern CPUs are faster when they fetch data that is stored close together. Contiguous memory minimizes cache misses.
2. **O(1) Access:** Since we know the starting address and the size of each element, calculating the memory address of index `i` is a simple mathematical formula: `Base_Address + (i * element_size)`.

### Internals

When you declare an array in Java:

1. **Allocation:** The JVM reserves a block of memory on the **Heap**.
2. **Initialization:** All elements are automatically initialized to their default values (e.g., `0` for integers, `null` for objects).
3. **Binding:** The variable name (on the stack) holds a **reference** (pointer) to the memory address on the heap.

### Visual Representation

`int[] arr = new int[3];`

| **Stack** (Reference) | **Heap** (Actual Data) |
| :-------------------- | :--------------------- |
| `arr` ------>         | `[0] [0] [0]`          |

### Coding Basics

```java
// Declaration & Allocation
int[] rollNumbers = new int[3];

// Assignment
rollNumbers[0] = 101;
rollNumbers[1] = 102;
rollNumbers[2] = 103;

// Traversal
for(int i = 0; i < rollNumbers.length; i++) {
    System.out.println(rollNumbers[i]);
}
```

### Important Concepts

- **Index:** The offset from the start of the array. Always starts at `0`.
- **Length Property:** A built-in attribute (`array.length`) that returns the number of slots available.
- **IndexOutOfBoundsException:** A common runtime error occurring when you attempt to access an index `< 0` or `>= length`.

### Multi-Dimensional Arrays

Think of a 2D array as an **"Array of Arrays"**.

- Logically: A table (matrix) with rows and columns.
- Internally: A main array where each element is a reference to another 1D array.
- Usage: Storing grids, matrices, or grouped records (e.g., marks for multiple students across multiple subjects).

### Performance & Trade-offs

- **Pros:** Extremely fast random access (O(1)).
- **Cons:**
  - Fixed size (cannot grow or shrink dynamically).
  - Costly insertion/deletion (elements must be shifted to maintain contiguity).

### Gotchas

- **Off-by-one errors:** Looping until `i <= length` instead of `i < length` triggers an exception.
- **Declaration vs. Instantiation:** `int[] arr;` does not create an array; `arr = new int[5];` actually allocates the memory.

### Key Takeaways

- Arrays store data of the same type contiguously.
- Memory is allocated on the Heap.
- Accessing an element is an O(1) constant-time operation.
- Always use `.length` to avoid hardcoding sizes.
- 2D arrays are simply arrays of arrays, not necessarily a grid in physical memory.

### Minimal Self-Test

1. If an array has a size of 10, what is the valid range of indices?
2. What is the value of `int[] arr = new int[5]` at index `2` immediately after declaration?
3. Why do we say array access is O(1)?
4. What happens if you try to access `arr[10]` in an array of size 10?

### What to Learn Next

1. **Strings in Java:** How they differ from primitive arrays.
2. **ArrayList:** How to bypass the "fixed size" constraint of arrays.
3. **Complexity Analysis (Big O):** To understand why arrays are chosen for specific algorithms.
