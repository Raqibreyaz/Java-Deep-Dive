> ## Hook: Why Do We Need the Collection Framework?
>
> Every software application works with **data**. The real challenge is not just storing data—it is storing it in a way that makes operations like searching, inserting, deleting, and updating efficient. No single data structure is best for every situation. Some are optimized for fast searching, while others are optimized for fast insertion. The Java Collection Framework provides a standard set of data structures, each designed for a different trade-off, so developers can choose the most appropriate one without implementing everything from scratch. 
>
> ---
>
> # Java Collection Framework – Introduction
>
> ## What is the Collection Framework?
>
> The **Java Collection Framework (JCF)** is a set of interfaces, classes, and algorithms that provide efficient ways to store and manipulate groups of objects.
>
> Instead of creating your own dynamic arrays, linked lists, hash tables, or trees, Java already provides optimized implementations such as:
>
> * `ArrayList`
> * `LinkedList`
> * `HashSet`
> * `HashMap`
> * `TreeSet`
> * `TreeMap`
> * `ArrayDeque`
>
> These classes follow a common hierarchy, making them easy to learn and use through polymorphism. 
>
> ---
>
> # Why Do We Need Data Structures?
>
> Suppose we store numbers inside an array:
>
> ```java
> int[] arr = {3, 7, 10, 1, 4, 12, 24, 0};
> ```
>
> Accessing an element by index is extremely fast.
>
> ```java
> arr[3];
> ```
>
> Time Complexity:
>
> ```text
> O(1)
> ```
>
> because Java directly calculates the memory address.
>
> ---
>
> ## Finding the Largest Element
>
> To find the maximum value:
>
> ```java
> int max = arr[0];
>
> for (int num : arr) {
>     if (num > max)
>         max = num;
> }
> ```
>
> Time Complexity:
>
> ```text
> O(n)
> ```
>
> Every element must be examined.
>
> ---
>
> ## What If We Keep the Array Sorted?
>
> ```text
> [0,1,3,4,7,10,12,24]
> ```
>
> Largest element:
>
> ```java
> arr[arr.length - 1];
> ```
>
> Time Complexity:
>
> ```text
> O(1)
> ```
>
> Much faster.
>
> ---
>
> ## But There Is a Trade-off
>
> Suppose we insert:
>
> ```text
> 6
> ```
>
> Every larger element must shift one position.
>
> ```text
> Before:
>
> 0 1 3 4 7 10 12 24
>
> After:
>
> 0 1 3 4 6 7 10 12 24
> ```
>
> Time Complexity:
>
> ```text
> O(n)
> ```
>
> because shifting elements takes linear time.
>
> **Important Principle**
>
> > Every data structure is a compromise. Improving one operation often makes another operation slower. This trade-off is one of the central ideas emphasized in the lecture. 
>
> ---
>
> # Arrays
>
> Java arrays are:
>
> * Continuous in memory.
> * Fixed in size.
> * Fast random access.
>
> Example:
>
> ```java
> int[] arr = new int[10];
> ```
>
> Trying to access beyond the last index:
>
> ```java
> arr[10];
> ```
>
> results in:
>
> ```text
> ArrayIndexOutOfBoundsException
> ```
>
> because arrays cannot automatically grow. 
>
> ---
>
> # Dynamic Arrays
>
> A dynamic array behaves like a normal array but automatically grows when it becomes full.
>
> Internally:
>
> 1. Create a larger array.
> 2. Copy existing elements.
> 3. Insert the new element.
> 4. Replace the old array.
>
> ```text
> Old Array
>
> [4][10][15][18][17]
>
> ↓
>
> Create Bigger Array
>
> [4][10][15][18][17][ ][ ][ ]
> ```
>
> This process is handled automatically by Java.
>
> ---
>
> # ArrayList
>
> Java provides **`ArrayList`** as an implementation of a dynamic array.
>
> ```java
> ArrayList<Integer> list = new ArrayList<>();
>
> list.add(10);
> list.add(20);
> list.add(30);
> ```
>
> Developers do not need to manually resize arrays because `ArrayList` handles memory management internally. The lecture uses this as the motivation for `ArrayList`. 
>
> ---
>
> # Linked List
>
> Unlike arrays, linked lists do not require contiguous memory.
>
> They consist of **nodes**.
>
> Each node contains:
>
> ```java
> class Node {
>     int data;
>     Node next;
> }
> ```
>
> Example:
>
> ```text
> Head
>  ↓
> [2|•] → [3|•] → [4|•] → [5|null]
> ```
>
> Each node stores:
>
> * Data
> * Reference to the next node
>
> The diagrams in the notes illustrate this node-based structure and how references connect nodes. 
>
> ---
>
> ## Insertion in Linked Lists
>
> Suppose we insert:
>
> ```text
> 5
> ```
>
> between:
>
> ```text
> 4 → 6
> ```
>
> Only references change.
>
> ```text
> Before
>
> 4 → 6
>
> After
>
> 4 → 5 → 6
> ```
>
> No shifting of existing elements is required.
>
> If the insertion position is already known, the pointer update itself is **O(1)**.
>
> ---
>
> # Set
>
> A **Set** stores **unique values**.
>
> Example:
>
> ```text
> Input
>
> 2 3 5 2 3
>
> Stored
>
> 2 3 5
> ```
>
> Duplicate elements are automatically ignored.
>
> This property is highlighted in the lecture as the defining characteristic of a `Set`. 
>
> ---
>
> # Hashing
>
> Hash-based collections use an internal array.
>
> Each element's position is calculated using a **hash function**.
>
> Example:
>
> ```text
> index = key % arrayLength
> ```
>
> The computed index determines where the element should be stored.
>
> This allows average constant-time lookup.
>
> ```text
> O(1)
> ```
>
> ---
>
> ## Collision
>
> Different values may produce the same index.
>
> Example:
>
> ```text
> 10
> 100
> ```
>
> Both may map to:
>
> ```text
> index = 0
> ```
>
> This situation is called a **collision**.
>
> ---
>
> ## Chaining
>
> Java commonly handles collisions using **chaining**.
>
> Each array slot acts as the head of a linked list.
>
> ```text
> Index 0
>
> ↓
>
> 10 → 100 → 190
> ```
>
> Multiple elements sharing the same hash index are linked together rather than overwriting each other. This chaining approach is illustrated in the notes. 
>
> ---
>
> ## Hash-Based Collections
>
> Java provides:
>
> * `HashSet`
> * `LinkedHashSet`
>
> Both are based on hashing.
>
> The hash value is obtained using:
>
> ```java
> hashCode()
> ```
>
> ---
>
> # Stack
>
> A **Stack** follows:
>
> ```text
> LIFO
>
> Last In
> First Out
> ```
>
> Example:
>
> ```text
> Push
>
> 2
> 3
> 4
> 5
>
> Top
> ```
>
> Removal order:
>
> ```text
> 5
> 4
> 3
> 2
> ```
>
> Similar to a stack of plates.
>
> The last plate placed on top is removed first.
>
> ---
>
> # Queue
>
> A **Queue** follows:
>
> ```text
> FIFO
>
> First In
> First Out
> ```
>
> Example:
>
> ```text
> Add
>
> 4
> 5
> 6
>
> Remove
>
> 4
> 5
> 6
> ```
>
> The first inserted element is the first removed.
>
> Java commonly implements queues using:
>
> ```text
> ArrayDeque
> ```
>
> as shown in the lecture notes. 
>
> ---
>
> # Map
>
> A **Map** stores **key-value pairs**.
>
> Example:
>
> ```text
> Aditya → 101
>
> Rohit → 102
>
> Rohan → 103
> ```
>
> Unlike a `Set`, each element has:
>
> * Key
> * Value
>
> Keys must be unique.
>
> Duplicate keys are not allowed.
>
> ---
>
> ## Internal Working of Map
>
> Similar to a `HashSet`,
>
> Java computes:
>
> ```java
> hashCode(key)
> ```
>
> Then calculates the array index.
>
> ```text
> hashCode(key) % arraySize
> ```
>
> This determines where the key-value pair is stored.
>
> Java implementations include:
>
> * `HashMap`
> * `LinkedHashMap`
>
> The lecture diagrams show key-value nodes being placed into buckets based on the key's hash code. 
>
> ---
>
> # Trees
>
> Trees store hierarchical data.
>
> Example:
>
> ```text
> Folder
> ├── File
> ├── Folder
> │   └── File
> └── File
> ```
>
> This structure naturally represents relationships such as file systems.
>
> ---
>
> # Binary Search Tree (BST)
>
> In a BST:
>
> ```text
> Left < Root < Right
> ```
>
> Example:
>
> ```text
>        10
>       /  \
>      6    12
> ```
>
> Rules:
>
> * Left subtree contains smaller values.
> * Right subtree contains larger values.
>
> Java provides:
>
> * `TreeSet`
> * `TreeMap`
>
> which use self-balancing binary search trees internally. 
>
> ---
>
> # Collection Framework Hierarchy
>
> One of the main goals of the Collection Framework is **code reuse through inheritance and polymorphism**.
>
> Example:
>
> ```java
> List<Integer> list = new ArrayList<>();
> ```
>
> can later become:
>
> ```java
> List<Integer> list = new LinkedList<>();
> ```
>
> without changing the rest of the code because both implement the same `List` interface.
>
> Common methods such as:
>
> ```java
> add()
> remove()
> ```
>
> work regardless of the underlying implementation.
>
> ---
>
> ## Collection Hierarchy
>
> ```text
>             Collection
>            /     |      \
>          List   Set    Queue
>         /   \
> ArrayList  LinkedList
> ```
>
> The lecture notes present `Collection` as the common parent interface for these collection types. 
>
> ---
>
> ## Why Isn't `Map` Inside Collection?
>
> `Collection` stores:
>
> ```text
> Values
> ```
>
> while `Map` stores:
>
> ```text
> Key → Value
> ```
>
> Since a map represents associations rather than a simple collection of values, Java keeps it outside the main `Collection` hierarchy.
>
> ---
>
> # Key Takeaways
>
> * Data structures exist to optimize different operations on data.
> * Arrays provide **O(1)** indexed access but have a fixed size.
> * Dynamic arrays grow automatically by allocating a larger array and copying existing elements.
> * `ArrayList` is Java's built-in dynamic array implementation.
> * Linked lists store elements in nodes connected by references, enabling efficient insertions without shifting elements.
> * Sets store only unique elements.
> * Hashing uses `hashCode()` to compute an array index, enabling fast average lookup.
> * Hash collisions are commonly handled using **chaining** (linked lists).
> * Stacks follow **LIFO**; queues follow **FIFO**.
> * Maps store unique keys associated with values and use hashing internally.
> * Trees organize hierarchical data, and BSTs maintain the ordering rule: **Left < Root < Right**.
> * The Collection Framework uses inheritance and polymorphism to provide a consistent API across different data structures, while `Map` remains outside the `Collection` hierarchy because it stores key-value pairs instead of only values. 
