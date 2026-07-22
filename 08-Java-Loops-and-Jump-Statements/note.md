# Java Loops & Jump Statements: Controlling Execution Flow

### Hook
Imagine you need to print a message 1,000 times. Writing it manually is absurd. Controlling execution flow allows you to dictate how and how many times your logic runs, transforming static scripts into dynamic engines.

### What is it?
Loops (Iteration Statements) are language constructs that allow a block of code to execute repeatedly based on a condition. Jump statements allow you to deviate from the normal sequential flow by exiting or skipping parts of the logic.

### One-Sentence Summary
Loops automate repetitive tasks by re-executing logic until a condition is met, while jump statements provide fine-grained control over the flow of that repetition.

### Intuition
Think of a loop as a physical treadmill. You set the speed (logic) and the duration/distance (condition). You keep walking (executing) until the treadmill turns off (condition becomes false). A jump statement is like hitting the "Emergency Stop" (break) or "Skip this stretch" (continue) button while running.

### The Three Core Loops
1. **While Loop:** Best when you don't know the exact number of iterations beforehand (e.g., "Keep reading file input until EOF").
2. **Do-While Loop:** Guarantees at least one execution because the condition check happens *after* the logic.
3. **For Loop:** Ideal for known ranges (e.g., "Do this 10 times"). It bundles initialization, condition, and increment in one line.

### Visual Flow
```text
[Initialization] 
      ↓
[Condition Check] ──→ (True) ──→ [Logic] ──→ [Update] ─╮
      │                                                  │
   (False)                                               │
      └──────────────────────────────────────────────────╯
      ↓
[Post-Loop Execution]
```

### Internals: The For-Loop Anatomy
`for(initialization; condition; increment) { ... }`
1. **Init:** Executed exactly once at the start.
2. **Check:** Evaluated before every iteration. If false, terminate.
3. **Body:** The code block that executes.
4. **Update:** Executed *after* the body finishes, right before the next condition check.

### Jump Statements
*   **Break:** Completely terminates the current loop and moves control to the first line after the loop block.
*   **Continue:** Stops the current iteration and jumps immediately to the next iteration (re-evaluating the loop condition).
*   **Labels:** Allow you to break or continue a specific outer loop when nested inside multiple loops.

### Important Gotchas
*   **Infinite Loops:** Occur if the loop condition never becomes `false`. Example: `for(int i = 0; i < 10; ) { }` (forgetting the increment).
*   **Variable Scope:** Variables defined inside the `for` loop header are not accessible outside the loop block.
*   **Type Promotion:** If you use a `byte` or `short` as a loop counter, Java automatically promotes it to `int` during arithmetic operations, which is why `int` is the standard convention.

### Performance & Trade-offs
*   **Modern Compilers:** For, while, and do-while loops are usually optimized by the JVM to produce identical machine code.
*   **Readability:** Use `for` loops for counting, `while` for state-driven loops. Using the wrong one obscures intent.

### Key Takeaways
*   Loops reduce code redundancy.
*   `while` checks condition first; `do-while` checks it last.
*   `break` exits the loop; `continue` skips the current iteration.
*   Always ensure your loop condition eventually reaches a terminating state to avoid infinite hang-ups.

### Minimal Self-Test
1. How many times does a `do-while` loop execute if the condition is false on the first check?
2. If you have a nested loop, what does `break` inside the inner loop do?
3. Can you declare the loop variable outside the `for` statement? What happens to the scope?
4. What happens if you forget to include an increment statement in a `while` loop?

### What to learn next
*   **Arrays:** Handling collections of data using loops.
*   **Methods:** Refactoring loop logic into reusable blocks.
*   **Complexity Analysis (Big O):** Understanding the cost of nested loops.