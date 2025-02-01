package impl;

import common.StackEmptyException;
import common.StackOverflowException;
import interfaces.IStack;

public class Stack implements IStack {
  private int capacity;   // Stores the maximum capacity of the stack
  private int count = 0;  // Stores the number of elements currently in the stack
  private int startIndex; // Stores the index of bottom element of the stack
  private Object[] array; // Stores reference to internal array used to store values in stack

  public Stack(int capacity, int startIndex, Object[] array) {
    if (capacity < 0 || startIndex < 0) // Do not allow index or capacity to be negative
      throw new IllegalArgumentException("Cannot have negative capacity or start index.");

    // If the stack does not start at index 0, then we assume the stack uses indices in the reverse manner,
    // i.e., decrementing as new elements are added to the stack.
    // As such, there needs to be enough valid indices to store the given maximum capacity of elements.
    if (startIndex != 0 && (startIndex - capacity + 1) < 0)
      throw new IllegalArgumentException("Starting at non-zero index reverses index traversal direction of stack, but index is not large enough to support capacity given.");

    // Ensure that the starting index is within array index bounds (negative case previously checked).
    if (startIndex >= array.length)
      throw new IllegalArgumentException("Start index outside internal array index bounds.");

    this.capacity = capacity;
    this.startIndex = startIndex;
    this.array = array;
  }

  // Pushes element onto stack
  @Override
  public void push(Object element) throws StackOverflowException {
    // Do not allow push operation if stack is full.
    if (capacity == count) throw new StackOverflowException();

    // Add element to array depending on direction of index traversal.
    // If we start at index 0, then add element to array[count] and increment count.
    // Otherwise, add element to array[startIndex - count] and increment count.
    if (startIndex == 0) {
      array[count++] = element;
    } else {
      array[startIndex - count++] = element;
    }
  }

  // Removes element from stack.
  // To avoid unnecessary complication, we do not remove past elements
  // from the array. Note this means there must be careful tracking of
  // indices to ensure invalid/supposedly erased data is not accessible.
  @Override
  public Object pop() throws StackEmptyException {
    // Throw exception if stack is empty and trying to pop().
    if (isEmpty()) throw new StackEmptyException();

    // Get top value
    Object val = top();

    // Simply decrement our count index, which stores next available index to store value
    count--;

    // Return top value
    return val;
  }

  // Gets top value without popping.
  @Override
  public Object top() throws StackEmptyException {
    // Ensure stack has elements.
    if (count == 0) throw new StackEmptyException();

    // The count index stores next available index, so return value
    // from index of (count - 1).
    // We split cases for when start index is 0 or at end of array.
    if (startIndex == 0) {
      return array[count - 1];
    } else {
      return array[startIndex - (count - 1)];
    }
  }

  // Returns the number of elements in the stack.
  @Override
  public int size() {
    return count;
  }

  // Returns the maximum capacity of the stack.
  @Override
  public int capacity() {
    return capacity;
  }

  // Returns whether the stack is empty
  @Override
  public boolean isEmpty() {
    return count == 0;
  }

  // Clears the stack.
  // Notice like the implementation of pop, we do not erase data,
  // instead resetting the count index.
  @Override
  public void clear() {
    count = 0;
  }
}
