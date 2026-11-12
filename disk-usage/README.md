# Week 5 Coding Exercise: Disk Usage Analyzer

### 1. Sort Race Results and Big-O Analysis

| n | selection ms | merge ms |
|---|---|---|
| 1000 | 6.6 | 0.4 |
| 2000 | 18.7 | 0.5 |
| 4000 | 39.9 | 0.6 |
| 8000 | 115.1 | 1.1 |
| 16000 | 458.6 | 2.5 |

* **Selection sort:** Each time `n` doubles in the larger runs, selection sort takes about **3 to 4 times longer** (going from 39.9 ms to 115.1 ms, and then 115.1 ms to 458.6 ms). This matches **O(n²)** because doubling the input size multiplies the work by $2^2 = 4$.
* **Merge sort:** Each time `n` doubles in the larger runs, merge sort takes roughly **2 times longer** (going from 0.6 ms to 1.1 ms, and then 1.1 ms to 2.5 ms). This matches **O(n log n)** because doubling `n` doubles the linear merge work while only adding one extra split level ($\log_2 n$).

### 2. What is the Big-O of `totalSize` in terms of *f*, the number of files and folders? Why?
The Big-O of `totalSize` is **O(f)** (linear time). Every single file and folder in the directory tree is visited and checked exactly once, so the total amount of work grows directly in proportion to the total number of items *f*.

### 3. Binary search needs a sorted list. If you only need to search once, is it worth sorting first? What if you search 1,000 times?
* **Searching once:** No, it is not worth sorting first. Sorting takes **O(n log n)** time, whereas a simple linear scan through an unsorted list only takes **O(n)** time, which is much faster for a single lookup.
* **Searching 1,000 times:** Yes, it is definitely worth sorting first. You only pay the **O(n log n)** sorting cost once upfront, and then all 1,000 searches run in super fast **O(log n)** time instead of scanning the entire list 1,000 separate times.

### 4. What would happen if `maxDepth` had no base case? What error would Java throw, and why?
Without a base case to stop the recursion when it hits a file or empty folder, `maxDepth` would keep calling itself endlessly. Java would quickly run out of call stack memory and crash with a **`StackOverflowError`** because every method call pushes a new frame onto the call stack until the stack limit is exceeded.

### 5. Name one more real-world problem (not folders) that has a recursive structure.
A **family tree** (or a company's organizational chart) has a recursive structure. Every person has parents who are also people with their own parents, or every manager leads a team of employees who can each be managers leading their own teams.
