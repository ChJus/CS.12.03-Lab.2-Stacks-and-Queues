package test;

import common.AbstractFactoryClient;
import common.QueueEmptyException;
import common.QueueFullException;
import interfaces.IQueue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests double stack queue implementation.
 */
public class TestDoubleStackQueue extends AbstractFactoryClient {
  // Default maximum capacity of DoubleStack
  private static final int DEFAULT_MAX_SIZE = 11;

  /**
   * Tests that the factory constructs a non-null double stack.
   */
  @Test
  public void factoryReturnsNonNullDoubleStackQueue() {
    // Ensure that queue can be initialized correctly/successfully.
    IQueue queue = getFactory().makeDoubleStackQueue(DEFAULT_MAX_SIZE);
    assertNotNull(queue, "Failure: IFactory.makeDoubleStackQueue returns null, expected non-null object");
  }

  /**
   * Tests that DoubleStackQueue rejects negative capacity sizes.
   */
  @Test
  public void rejectInvalidCapacity() {
    // Ensure DoubleStackQueue reject negative capacity sizes in constructor
    assertThrows(IllegalArgumentException.class, () -> getFactory().makeDoubleStackQueue(-1));
  }

  /**
   * Tests DoubleStackQueue methods in a general use case scenario.
   * Calls methods <code>enqueue</code>, <code>dequeue</code>, <code>size</code>,
   * <code>capacity</code>, <code>isEmpty</code>, <code>isFull</code>, <code>clear</code>.
   */
  @Test
  public void fullUseTest() throws QueueFullException, QueueEmptyException {
    // Initialize DoubleStackQueue with capacity 11
    IQueue queue = getFactory().makeDoubleStackQueue(DEFAULT_MAX_SIZE);

    // Ensure dequeue operation is rejected on empty queues
    assertThrows(QueueEmptyException.class, queue::dequeue);

    // Ensure size is 0
    assertEquals(0, queue.size());

    // Ensure maximum capacity is equivalent to given size
    assertEquals(DEFAULT_MAX_SIZE, queue.capacity());

    // Queue [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]
    for (int i = 1; i <= 11; i++) {
      queue.enqueue(i);
    }

    // Ensure dequeue returns values 1-11 in original order
    for (int i = 1; i <= 11; i++) {
      assertEquals(i, queue.dequeue());
    }

    // Add 1-5 to queue.
    for (int i = 1; i <= 5; i++) {
      queue.enqueue(i);
    }

    // Ensure dequeue returns 1-3.
    assertEquals(1, queue.dequeue());
    assertEquals(2, queue.dequeue());
    assertEquals(3, queue.dequeue());

    // Add 6-10 to queue
    for (int i = 6; i <= 10; i++) {
      queue.enqueue(i);
    }

    // Ensure size is 7 (added 1-10 and removed 1-3)
    assertEquals(7, queue.size());

    // Enqueue 11-14
    queue.enqueue(11);
    queue.enqueue(12);
    queue.enqueue(13);
    queue.enqueue(14);

    // Now, size should be full (contains 4-14, which has 11 elements)
    assertTrue(queue.isFull());

    // Dequeue all elements and ensure it is in order
    for (int i = 4; i <= 14; i++) {
      assertEquals(i, queue.dequeue());
    }

    // Add 1-11 and perform clear operation, ensure queue is empty
    for (int i = 1; i <= 11; i++) queue.enqueue(i);
    queue.clear();
    assertTrue(queue.isEmpty());

    // Ensure internal stack representation also cleared, so that
    // Queue can add 11 elements and reach full capacity.
    for (int i = 1; i <= 11; i++) queue.enqueue(i);
    assertTrue(queue.isFull());
    
    // Ensure dequeue returns values 1-11 in original order
    for (int i = 1; i <= 11; i++) {
      assertEquals(i, queue.dequeue());
    }
  }
}
