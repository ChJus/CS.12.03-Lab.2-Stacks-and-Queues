package impl;

import interfaces.IDoubleStack;
import interfaces.IStack;

public class DoubleStack implements IDoubleStack {
  private IStack firstStack;  // Stores stack that starts at start of internal array
  private IStack secondStack; // Stores stack that starts at end of internal array
  private Object[] array;     // Stores internal array

  // Construct DoubleStack with maximum capacity
  public DoubleStack(int capacity) {
    // Ensure capacity is non-negative
    if (capacity < 0) throw new IllegalArgumentException("Cannot have negative capacity");

    // Create new internal array with maximum capacity
    array = new Object[capacity];

    // Initialize first and second stack.
    // Note the use of integer truncation —— this means that if
    // the array has an odd size of n, each stack will use (n-1)/2 indices,
    // leaving the median index empty at all times.
    firstStack = new Stack(capacity / 2, 0, array);
    secondStack = new Stack(capacity / 2, array.length - 1, array);
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
