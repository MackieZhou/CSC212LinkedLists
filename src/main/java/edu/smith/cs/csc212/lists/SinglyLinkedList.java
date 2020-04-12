package edu.smith.cs.csc212.lists;

import me.jjfoley.adt.ListADT;
import me.jjfoley.adt.errors.BadIndexError;
import me.jjfoley.adt.errors.TODOErr;

/**
 * A Singly-Linked List is a list that has only knowledge of its very first
 * element. Elements after that are chained, ending with a null node.
 * 
 * @author jfoley
 *
 * @param <T> - the type of the item stored in this list.
 */
public class SinglyLinkedList<T> extends ListADT<T> {
	/**
	 * The start of this list. Node is defined at the bottom of this file.
	 */
	Node<T> start;

	@Override
	public T removeFront() {
		checkNotEmpty();
		T returnValue = this.start.value;
		this.start = this.start.next;
		return returnValue;
	}

	@Override
	public T removeBack() {
		checkNotEmpty();

		if (this.start.next == null) {
			return this.removeFront();
		}

		Node<T> secondToLast = null;
		for (Node<T> current = this.start; current.next != null; current = current.next) {
			secondToLast = current;
		}

		T delete = secondToLast.next.value;
		assert (secondToLast.next.next == null);
		secondToLast.next = null;
		return delete;
	}

	@Override
	public T removeIndex(int index) {
		checkNotEmpty();

		T delete;

		// if delete the first element
		if (index == 0) {
			delete = this.start.value;
			this.removeFront();
			return delete;
		}

		// if delete the second and after
		int at = 0;
		for (Node<T> current = this.start; current != null; current = current.next) {
			if (at++ == index - 1) {
				delete = current.next.value;
				current.next = current.next.next;
				return delete;
			}
		}
		throw new BadIndexError(index);
	}

	@Override
	public void addFront(T item) {
		this.start = new Node<T>(item, start);
	}

	@Override
	public void addBack(T item) {
		// if this is an empty list
		if (this.start == null) {
			this.addFront(item);
			return;
		}

		// if this is not an empty list
		Node<T> lastThing = null;

		// find the last node
		for (Node<T> current = this.start; current != null; current = current.next) {
			lastThing = current;
		}

		// add item to the last node
		assert (lastThing.next == null);
		lastThing.next = new Node<T>(item, null);
	}

	@Override
	public void addIndex(int index, T item) {
		// if add to the first position
		if (index == 0) {
			this.addFront(item);
			return;
		}

		// if add to the second and after
		int at = 0;
		for (Node<T> current = this.start; current != null; current = current.next) {
			if (at++ == index - 1) {
				current.next = new Node<T>(item, current.next);
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
		// if this is an empty list
		checkNotEmpty();

		// if this is not an empty list
		Node<T> lastThing = null;

		// find the last node
		for (Node<T> current = this.start; current != null; current = current.next) {
			lastThing = current;
		}
		assert (lastThing.next == null);
		return lastThing.value;
	}

	@Override
	public T getIndex(int index) {
		checkNotEmpty();
		int at = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			if (at++ == index) {
				return n.value;
			}
		}
		throw new BadIndexError(index);
	}

	@Override
	public void setIndex(int index, T value) {
		checkNotEmpty();

		if (index == 0) {
			this.start = new Node<T>(value, this.start.next);
			return;
		}

		int at = 0;
		for (Node<T> current = this.start; current.next != null; current = current.next) {
			if (at++ == index - 1) {
				current.next = new Node<T>(value, current.next.next);
				return;
			}
		}

		throw new BadIndexError(index);
	}

	@Override
	public int size() {
		int count = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			count++;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		return this.start == null;
	}

	/**
	 * The node on any linked list should not be exposed. Static means we don't need
	 * a "this" of SinglyLinkedList to make a node.
	 * 
	 * @param <T> the type of the values stored.
	 */
	static class Node<T> {
		/**
		 * What node comes after me?
		 */
		public Node<T> next;
		/**
		 * What value is stored in this node?
		 */
		public T value;

		/**
		 * Create a node with a friend.
		 * 
		 * @param value - the value to put in it.
		 * @param next  - the friend of this node.
		 */
		public Node(T value, Node<T> next) {
			this.value = value;
			this.next = next;
		}

		/**
		 * Alternate constructor; create a node with no friends.
		 * 
		 * @param value - the value to put in it.
		 */
		public Node(T value) {
			this.value = value;
			this.next = null;
		}
	}

}
