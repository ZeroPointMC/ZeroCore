package zeropoint.core.struct;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import zeropoint.core.math.MathUtil;


/**
 * Store a sequential collection of objects, retrieve them in order, and wrap around to the start when reaching the end
 * 
 * @author Zero Point
 * @param <C>
 *            The type of data stored in the wheel
 */
public class Wheel<C> implements List<C> {
	/**
	 * The actual content of the <code>Wheel</code>
	 */
	protected List<C> content;
	/**
	 * The current index of the content array
	 */
	protected int pointer = 0;
	/**
	 * Construct an empty <code>Wheel</code> with an initial size of 10
	 */
	public Wheel() {
		this(10);
	}
	/**
	 * Construct an empty <code>Wheel</code> with the given initial size
	 * 
	 * @param size
	 *            - the initial size of the <code>Wheel</code>
	 */
	public Wheel(int size) {
		this.content = new ArrayList<C>();
	}
	/**
	 * Ensure that the pointer does not go beyond the end of the content, limiting it
	 */
	public void limitPointer() {
		if (this.content.size() == 0) {
			this.pointer = -1;
		}
		else if (this.pointer >= this.content.size()) {
			this.pointer = this.content.size();
		}
		else if (this.pointer < 0) {
			this.pointer = 0;
		}
	}
	/**
	 * Ensure that the pointer does not go beyond the end of the content, wrapping around
	 */
	public void wrapPointer() {
		if (this.content.size() == 0) {
			this.pointer = -1;
		}
		else if (this.pointer >= this.content.size()) {
			this.pointer = 0;
		}
		else if (this.pointer < 0) {
			this.pointer = this.content.size();
		}
	}
	/**
	 * Set the internal pointer to a random position without changing the content
	 * 
	 * @return this
	 */
	public Wheel<C> spin() {
		this.pointer = MathUtil.randInt(new Random(), 0, this.content.size());
		return this;
	}
	@Override
	public boolean add(C item) {
		return this.content.add(item);
	}
	@Override
	public void add(int index, C item) {
		this.content.add(index, item);
	}
	@Override
	public boolean addAll(Collection<? extends C> items) {
		return this.content.addAll(items);
	}
	@Override
	public boolean addAll(int index, Collection<? extends C> items) {
		return this.content.addAll(index, items);
	}
	/**
	 * Add all items in the array
	 * 
	 * @param items
	 *            - the array of objects to add
	 * @return <code>true</code> on success, <code>false</code> on failure
	 */
	public boolean addAll(C[] items) {
		List<C> list = new ArrayList<C>();
		for (int i = 0; i < items.length; i++ ) {
			list.add(items[i]);
		}
		return this.content.addAll(list);
	}
	/**
	 * Add all items in the array, starting insertion at the specified index
	 * 
	 * @param index
	 *            - the index to start inserting elements at
	 * @param items
	 *            - the array of objects to add
	 * @return <code>true</code> on success, <code>false</code> on failure
	 */
	public boolean addAll(int index, C[] items) {
		List<C> list = new ArrayList<C>();
		for (int i = 0; i < items.length; i++ ) {
			list.add(items[i]);
		}
		return this.content.addAll(index, list);
	}
	/**
	 * @return the next element in the wheel
	 */
	public C read() {
		C ret = this.content.get(this.pointer);
		this.pointer += 1;
		if (this.pointer >= this.content.size()) {
			this.pointer = 0;
		}
		return ret;
	}
	/**
	 * Reset the pointer position to 0
	 * 
	 * @return this
	 */
	public Wheel<C> reset() {
		this.pointer = 0;
		return this;
	}
	@Override
	public void clear() {
		this.content.clear();
		this.limitPointer();
	}
	@Override
	public boolean contains(Object test) {
		return this.content.contains(test);
	}
	@Override
	public boolean containsAll(Collection<?> tests) {
		return this.content.containsAll(tests);
	}
	@Override
	public C get(int index) {
		this.limitPointer();
		return this.content.get(index);
	}
	@Override
	public int indexOf(Object search) {
		return this.content.indexOf(search);
	}
	@Override
	public boolean isEmpty() {
		return this.content.isEmpty();
	}
	@Override
	public Iterator<C> iterator() {
		return new WheelIterator<C>(this);
	}
	@Override
	public int lastIndexOf(Object search) {
		return this.content.lastIndexOf(search);
	}
	@Override
	public ListIterator<C> listIterator() {
		return this.listIterator(0);
	}
	@Override
	public ListIterator<C> listIterator(int start) {
		return new WheelIterator<C>(this).setPointer(start);
	}
	@Override
	public boolean remove(Object item) {
		boolean ret = this.content.remove(item);
		this.limitPointer();
		return ret;
	}
	@Override
	public C remove(int index) {
		C ret = this.content.remove(index);
		this.limitPointer();
		return ret;
	}
	@Override
	public boolean removeAll(Collection<?> items) {
		boolean ret = this.content.removeAll(items);
		this.limitPointer();
		return ret;
	}
	@Override
	public boolean retainAll(Collection<?> items) {
		boolean ret = this.content.retainAll(items);
		this.limitPointer();
		return ret;
	}
	@Override
	public C set(int index, C item) {
		C ret = this.content.set(index, item);
		this.limitPointer();
		return ret;
	}
	@Override
	public int size() {
		return this.content.size();
	}
	@Override
	public List<C> subList(int from, int to) {
		return this.content.subList(from, to);
	}
	@Override
	public C[] toArray() {
		return (C[]) this.content.toArray();
	}
	@Override
	public <T> T[] toArray(T[] type) {
		return this.content.toArray(type);
	}
}
