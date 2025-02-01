package impl;

import interfaces.IDoubleStack;
import interfaces.IStack;

public class DoubleStack implements IDoubleStack {
  private IStack firstStack;  // Stores stack that starts at start of internal array
  private IStack secondStack; // Stores stack that starts at end of internal array
  private Object[] array;     // Stores internal array

  // Construct DoubleStack with maximum capacity and size allocation to each stack.
  // Notable uses of sizePerStack would be as capacity / 2 for a normal double stack,
  // and capacity, for the implementation of a DoubleStackQueue.
  public DoubleStack(int capacity, int sizePerStack) {
    // Ensure capacity is non-negative
    if (capacity < 0) throw new IllegalArgumentException("Cannot have negative capacity");

    // Throw exception if size per stack is larger than capacity or negative.
    if (sizePerStack < 0 || sizePerStack > capacity)
      throw new IllegalArgumentException("Cannot allocate more space for each stack than maximum capacity.");

    // Create new internal array with maximum capacity
    array = new Object[capacity];

    // Initialize first and second stack.
    // Note the use of integer truncation —— this means that if
    // the array has an odd size of n, each stack will use (n-1)/2 indices,
    // leaving the median index empty at all times.
    firstStack = new Stack(sizePerStack, 0, array);
    secondStack = new Stack(sizePerStack, array.length - 1, array);
  }

  // Returns the first stack
  @Override
  public IStack getFirstStack() {
    return firstStack;
  }

  // Returns the second stack
  @Override
  public IStack getSecondStack() {
    return secondStack;
  }
}
