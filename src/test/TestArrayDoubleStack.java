package test;

import common.AbstractFactoryClient;
import common.StackEmptyException;
import common.StackOverflowException;
import interfaces.IDoubleStack;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests array collection implementation.
 */
public class TestArrayDoubleStack extends AbstractFactoryClient {
  // Default maximum capacity of DoubleStack
  private static final int DEFAULT_MAX_SIZE = 10;

  /**
   * Tests that the factory constructs a non-null double stack.
   */
  @Test
  public void factoryReturnsNonNullDoubleStackObject() {
    // Ensure that DoubleStack can be initialized correctly/successfully.
    IDoubleStack doubleStack1 = getFactory().makeDoubleStack(DEFAULT_MAX_SIZE);
    assertNotNull(doubleStack1, "Failure: IFactory.makeDoubleStack returns null, expected non-null object");
  }

  /**
   * Tests that DoubleStack rejects negative capacity sizes.
   */
  @Test
  public void rejectInvalidCapacity() {
    // Ensure DoubleStack reject negative capacity sizes in constructor
    assertThrows(IllegalArgumentException.class, () -> getFactory().makeDoubleStack(-1));
  }

  /**
   * Tests DoubleStack methods in a general use case scenario.
   * Calls methods <code>size</code>, <code>capacity</code>, <code>push</code>,
   * <code>pop</code>, <code>clear</code>, <code>isEmpty</code>, <code>top</code>.
   */
  @Test
  public void fullUseTest() throws StackOverflowException, StackEmptyException {
    // Initialize DoubleStack with capacity 10
    IDoubleStack stack = getFactory().makeDoubleStack(DEFAULT_MAX_SIZE);

    // Get first and second stack objects
    var firstStack = stack.getFirstStack();
    var secondStack = stack.getSecondStack();

    // Ensure pop operation is rejected on empty stacks
    assertThrows(StackEmptyException.class, firstStack::pop);
    assertThrows(StackEmptyException.class, secondStack::pop);

    // Ensure size is 0
    assertEquals(0, firstStack.size());
    assertEquals(0, secondStack.size());

    // Ensure maximum capacity is 10/2 = 5
    assertEquals(5, firstStack.capacity());
    assertEquals(5, secondStack.capacity());

    // Push [5, 4, 3, 2, 1] onto first stack
    for (int i = 5; i >= 1; i--) {
      firstStack.push(i);

      // Ensure top returns most recent item pushed onto stack
      assertEquals(i, firstStack.top());
    }

    // Push [5, 4, 3, 2, 1] onto first stack
    for (int i = 5; i >= 1; i--) {
      secondStack.push(i);

      // Ensure top returns most recent item pushed onto stack
      assertEquals(i, secondStack.top());
    }

    // Stack now contains elements, so isEmpty should be false
    assertFalse(firstStack.isEmpty());
    assertFalse(secondStack.isEmpty());

    // Ensure both stacks cannot add new elements (as both reached max capacity of 5)
    assertThrows(StackOverflowException.class, () -> firstStack.push(6));
    assertThrows(StackOverflowException.class, () -> secondStack.push(6));

    // Ensure the values are retrieved in LIFO order (should be [1, 2, 3, 4, 5])
    for (int i = 1; i <= 5; i++) {
      assertEquals(i, firstStack.pop());
      assertEquals(i, secondStack.pop());
    }

    // Test clearing method by adding elements, calling clear() and checking stacks are empty
    firstStack.push(-1);
    secondStack.push(-2);
    firstStack.clear();
    secondStack.clear();
    assertTrue(firstStack.isEmpty());
    assertTrue(secondStack.isEmpty());
  }

  // Checks smaller edge cases that may occur
  @Test
  public void edgeCases() {
    // Initialize a DoubleStack with an odd capacity.
    IDoubleStack stack = getFactory().makeDoubleStack(11);

    var firstStack = stack.getFirstStack();
    var secondStack = stack.getSecondStack();

    // First, second stacks should now have a max capacity of floor(11/2) = 5
    assertEquals(5, firstStack.capacity());
    assertEquals(5, secondStack.capacity());
  }
}
