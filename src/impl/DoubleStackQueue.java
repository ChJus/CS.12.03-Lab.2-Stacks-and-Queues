package impl;

import common.QueueEmptyException;
import common.QueueFullException;
import common.StackEmptyException;
import common.StackOverflowException;
import interfaces.IQueue;

public class DoubleStackQueue implements IQueue {
  private int capacity;         // Stores Queue capacity
  private int size;             // Stores number of elements in Queue
  private DoubleStack internal; // Internal DoubleStack representation of Queue

  public DoubleStackQueue(int capacity) {
    this.capacity = capacity;
    internal = new DoubleStack(capacity, capacity);
    // Initializes a DoubleStack representation with given capacity; each stack is also assigned
    // the entire capacity of the internal representation.
    // Note the checking handlers in DoubleStack constructor will reject negative capacity sizes
  }

  // Adds element to queue
  @Override
  public void enqueue(Object element) throws QueueFullException {
    // Do not allow enqueuing if queue is full
    if (isFull()) throw new QueueFullException();

    // Push element onto first stack and increment size counter.
    try {
      internal.getFirstStack().push(element);
      size++;
    } catch (StackOverflowException e) {
      throw new QueueFullException();
    }
  }

  // Removes element from queue in FIFO order
  @Override
  public Object dequeue() throws QueueEmptyException {
    // Do not allow dequeue if queue is empty
    if (isEmpty()) throw new QueueEmptyException();

    // If the second stack does not have elements, push all elements from the
    // first stack onto the second stack. This essentially applies LIFO twice to the
    // original items (first enqueuing pushes onto first stack, dequeuing pushes onto second stack),
    // which equivalently results in a FIFO order.
    try {
      if (internal.getSecondStack().isEmpty()) {
        while (!internal.getFirstStack().isEmpty()) {
          internal.getSecondStack().push(internal.getFirstStack().pop());
        }
      }

      size--; // Decrement size counter

      // Return topmost element of second stack
      return internal.getSecondStack().pop();
    } catch (StackEmptyException e) {
      throw new QueueEmptyException();
    } catch (StackOverflowException e) {
      throw new RuntimeException();
    }
  }

  // Returns number of elements in queue.
  @Override
  public int size() {
    return size;
  }

  // Returns maximum capacity of queue.
  @Override
  public int capacity() {
    return capacity;
  }

  // Returns whether the queue is empty
  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  // Returns whether the queue is full.
  @Override
  public boolean isFull() {
    return size() == capacity();
  }

  // Clears the queue. Note the contents of the internal representation
  // are not cleared, only the size pointers are reset.
  // This means there must be a careful implementation when looping through the stack
  // to ensure invalid/supposedly erased data is not accessible.
  @Override
  public void clear() {
    size = 0;
    internal.getFirstStack().clear();
    internal.getSecondStack().clear();
  }
}
