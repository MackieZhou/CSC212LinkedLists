package edu.smith.cs.csc212.lists;

import edu.smith.cs.csc212.lists.SinglyLinkedList.Node;
import me.jjfoley.adt.ListADT;
import me.jjfoley.adt.errors.*;

/**
 * A Doubly-Linked List is a list based on nodes that know of their successor
 * and predecessor.
 * 
 * @author jfoley
 *
 * @param <T>
 */
public class DoublyLinkedList<T> extends ListADT<T> {
	/**
	 * This is a reference to the first node in this list.
	 */
	Node<T> start;
	/**
	 * This is a reference to the last node in this list.
	 */
	Node<T> end;

	/**
	 * A doubly-linked list starts empty.
	 */
	public DoublyLinkedList() {
		this.start = null;
		this.end = null;
	}

	/**
	 * The node on any linked list should not be exposed. Static means we don't need
	 * a "this" of DoublyLinkedList to make a node.
	 * 
	 * @param <T> the type of the values stored.
	 */
	static class Node<T> {
		/**
		 * What node comes before me?
		 */
		public Node<T> before;
		/**
		 * What node comes after me?
		 */
		public Node<T> after;
		/**
		 * What value is stored in this node?
		 */
		public T value;

		/**
		 * Create a node with no friends.
		 * 
		 * @param value - the value to put in it.
		 */
		public Node(T value) {
			this.value = value;
			this.before = null;
			this.after = null;
		}
	}

	@Override
	public T removeFront() {
		checkNotEmpty();

		T front = this.start.value;

		if (this.size() == 1) {
			start = end = null;
		} else {
			start = start.after;
			start.before = null;
		}

		return front;
	}

	@Override
	public T removeBack() {
		checkNotEmpty();

		T last = this.end.value;

		if (this.size() == 1) {
			start = end = null;
		} else {
			end = end.before;
			end.after = null;
		}

		return last;
	}

	@Override
	public T removeIndex(int index) {
		checkNotEmpty();

		T delete;

		if (index == 0) {
			delete = this.removeFront();
			return delete;
		} else if (index == this.size() - 1) {
			delete = this.removeBack();
			return delete;
		}

		int at = 0;
		for (Node<T> current = this.start; current != null; current = current.after) {
			if (at++ == index - 1) {
				delete = current.after.value;

				Node<T> tail = current.after.after;
				current.after = tail;
				tail.before = current;
				return delete;
			}
		}
		throw new BadIndexError(index);
	}

	@Override
	public void addFront(T item) {
		if (start == null) {
			end = start = new Node<T>(item);
		} else {
			Node<T> second = start;
			start = new Node<T>(item);
			start.after = second;
			second.before = start;
		}
	}

	@Override
	public void addBack(T item) {
		if (end == null) {
			start = end = new Node<T>(item);
		} else {
			Node<T> secondToLast = end;
			end = new Node<T>(item);
			end.before = secondToLast;
			secondToLast.after = end;
		}
	}

	@Override
	public void addIndex(int index, T item) {
		if (index == 0) {
			this.addFront(item);
			return;
		}

		if (index == this.size()) {
			this.addBack(item);
			return;
		}

		int at = 0;
		for (Node<T> current = this.start; current != null; current = current.after) {
			if (at++ == index - 1) {
				Node<T> tail = current.after;
				Node<T> insert = new Node<T>(item);
				current.after = insert;
				insert.before = current;
				insert.after = tail;
				tail.before = insert;
				return;
			}
		}

		throw new BadIndexError(index);
	}

	@Override
	public T getFront() {
		checkNotEmpty();
		return this.start.value;
	}

	@Override
	public T getBack() {
		checkNotEmpty();
		return this.end.value;
	}

	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		int at = 0;
		for (Node<T> n = this.start; n != null; n = n.after) {
			if (at++ == index) {
				return n.value;
			}
		}
		throw new BadIndexError(index);
	}

	public void setIndex(int index, T value) {
		checkNotEmpty();

		int at = 0;
		for (Node<T> n = this.start; n != null; n = n.after) {
			if (at++ == index) {
				n.value = value;
				return;
			}
		}

		throw new BadIndexError(index);
	}

	@Override
	public int size() {
		int count = 0;
		for (Node<T> n = this.start; n != null; n = n.after) {
			count++;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		return this.start == null && this.end == null;
	}
}
