package hw4;

import exceptions.EmptyException;

/**
 * Stack ADT implemented using linked nodes.
 *
 * @param <T> base type.
 */
public class LinkedStack<T> implements Stack<T> {
  private Node<T> front;

  /**
   * Construct an ListStack.
   */
  public LinkedStack() {
    front = null;
  }

  @Override
  public boolean empty() {
    return front == null;
  }

  @Override
  public T top() throws EmptyException {
    if (empty()) {
      throw new EmptyException();
    }
    return front.data;
  }

  @Override
  public void pop() throws EmptyException {
    if (empty()) {
      throw new EmptyException();
    }

    front = front.next;
  }

  @Override
  public void push(T t) {
    Node<T> node = new Node<>();
    node.data = t;
    node.next = front;
    front = node;
  }

  private static class Node<T> {
    T data;
    Node<T> next;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append("[");
    toString(str, front);
    str.append("]");
    return str.toString();
  }

  // recursively traverse the stack!
  private void toString(StringBuilder str, Node<T> node) {
    if (node == null) {
      return;
    }
    if (node.next != null) {
      toString(str, node.next);
    }
    str.append(node.data.toString());
    if (node != front) {
      str.append(", ");
    }
  }
}
