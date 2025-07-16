# AMIS Data Structures and Algorithms Analysis

## 1. Overview
This document provides a detailed analysis of the data structures and algorithms used in the Atinka Meds Inventory System (AMIS), including their time and space complexity using Big O and Omega notation.

## 2. Data Structures Analysis

### 2.1 Custom HashMap
**Implementation**: Separate Chaining with Linked Lists
**Key Operations**:
- `put(K key, V value)`: O(1) average, O(n) worst-case
- `get(Object key)`: O(1) average, O(n) worst-case
- `remove(Object key)`: O(1) average, O(n) worst-case
- `containsKey(Object key)`: O(1) average, O(n) worst-case

**Justification**:
- Uses array of linked lists to handle collisions
- Load factor (0.75) ensures good performance by resizing when needed
- Hash function distributes keys uniformly across buckets
- Worst-case occurs when all keys hash to the same bucket (unlikely with good hash function)

### 2.2 MinHeap
**Implementation**: Binary Heap using Array
**Key Operations**:
- `insert(T element)`: O(log n)
- `extractMin()`: O(log n)
- `getMin()`: O(1)
- `isEmpty()`: O(1)

**Justification**:
- Used for efficient retrieval of minimum element (low stock items)
- Complete binary tree property ensures O(log n) operations
- Array-based implementation provides good cache locality
- More efficient than sorting for repeated minimum extractions

### 2.3 LinkedList
**Implementation**: Doubly Linked List
**Key Operations**:
- `addFirst(T element)`: O(1)
- `addLast(T element)`: O(1)
- `removeFirst()`: O(1)
- `removeLast()`: O(1)
- `get(int index)`: O(n)
- `remove(Object o)`: O(n)

**Justification**:
- Provides O(1) insertion/deletion at both ends
- Used as the underlying structure for Queue and Stack
- Doubly-linked allows for efficient traversal in both directions
- More memory overhead than array-based lists but better for frequent insertions/deletions

### 2.4 Queue
**Implementation**: LinkedList-based
**Key Operations**:
- `enqueue(T item)`: O(1)
- `dequeue()`: O(1)
- `peek()`: O(1)
- `isEmpty()`: O(1)

**Justification**:
- Used for managing purchase history (FIFO order)
- Efficient O(1) operations for all queue operations
- Wraps LinkedList to provide queue-specific interface
- Ensures proper ordering of transactions

### 2.5 Stack
**Implementation**: LinkedList-based
**Key Operations**:
- `push(T item)`: O(1)
- `pop()`: O(1)
- `peek()`: O(1)
- `isEmpty()`: O(1)

**Justification**:
- Used for sales log (LIFO order)
- Efficient O(1) operations for all stack operations
- Simple and efficient for tracking recent sales
- Natural choice for undo/redo functionality

## 3. Algorithm Analysis

### 3.1 Hash Function
```java
private int hash(Object key) {
    int h = key.hashCode();
    return (h ^ (h >>> 16)) & (table.length - 1);
}
```
**Complexity**: O(1)
**Analysis**:
- Uses Java's built-in `hashCode()`
- Applies additional hashing to improve distribution
- Bitwise operations ensure index stays within array bounds
- Constant time operation

### 3.2 Heapify Operations
**minHeapify(int i)**:
- Time: O(log n)
- Space: O(1) (iterative implementation)
- Maintains heap property by moving elements down the tree

**insert(T element)**:
- Time: O(log n) worst-case
- Space: O(1) auxiliary
- Adds to end and performs at most log n swaps

**extractMin()**:
- Time: O(log n) worst-case
- Space: O(1) auxiliary
- Replaces root with last element and performs heapify

## 4. Performance Trade-offs

### 4.1 Space vs Time
- **HashMap**: Higher memory usage (array + linked lists) for O(1) average operations
- **MinHeap**: More efficient memory usage than sorted structures but O(log n) operations
- **LinkedList**: Extra memory for node pointers but O(1) insertions/deletions

### 4.2 Choice of Data Structures
1. **Inventory Management (HashMap)**
   - Fast lookups by drug code (O(1) average)
   - Efficient updates and deletions
   - Handles large inventory well

2. **Low Stock Alerts (MinHeap)**
   - Quick access to minimum stock items
   - Efficient updates when stock changes
   - Better than sorting for dynamic data

3. **Transaction Processing (Queue/Stack)**
   - Queue for FIFO processing of purchases
   - Stack for LIFO processing of sales
   - Natural fit for the respective use cases

## 5. Potential Optimizations

1. **HashMap**
   - Consider open addressing for better cache performance
   - Implement automatic resizing based on load factor
   - Add treeification for buckets with many collisions

2. **MinHeap**
   - Add bulk insert operation (O(n) instead of n × O(log n))
   - Implement decreaseKey operation for more efficient updates
   - Consider Fibonacci heap for better amortized times

3. **General**
   - Add custom serialization for persistence
   - Implement iterators for all collections
   - Add thread-safety if needed for concurrent access

## 6. Conclusion
The chosen data structures provide a good balance between time and space complexity for the AMIS requirements. The HashMap offers efficient inventory management, while the MinHeap ensures quick access to low-stock items. The Queue and Stack provide appropriate ordering for transaction processing. The implementations prioritize simplicity and correctness while maintaining good asymptotic complexity.
