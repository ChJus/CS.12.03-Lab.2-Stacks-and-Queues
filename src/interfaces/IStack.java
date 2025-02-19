package interfaces;

import common.StackEmptyException;
import common.StackOverflowException;

/**
 * Simple stack interface.
 */
public interface IStack {
  /**
   * Pushes an element onto the stack.
   *
   * @param element the element to be pushed
   * @throws StackOverflowException if there is no room on the stack for the new element
   */
  void push(Object element) throws StackOverflowException;

  /**
   * Pops an element from the stack.
   *
   * @return the popped element
   * @throws StackEmptyException if the stack is empty
   */
  Object pop() throws StackEmptyException;

  /**
   * Accesses the top element on the stack without removing it.
   *
   * @return the top element
   * @throws StackEmptyException if the stack is empty
   */
  Object top() throws StackEmptyException;

  /**
   * @return the number of elements on the stack
   */
  int size();

  /**
   * @return the maximum capacity of the stack
   */
  int capacity();

  /**
   * Checks whether the stack is empty.
   *
   * @return true if the stack is empty
   */
  boolean isEmpty();

  /**
   * Checks whether stack is full.
   *
   * @return true if stack is full.
   */
  boolean isFull();

  /**
   * Removes all elements from the stack.
   */
  void clear();
}
