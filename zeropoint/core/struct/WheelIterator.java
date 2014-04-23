package zeropoint.core.struct;


import java.util.ListIterator;


/**
 * Iterator for <code>Wheel</code> objects
 * 
 * @param <T>
 *            - the type of object being iterated over by this object
 * @author Zero Point
 */
public final class WheelIterator<T> implements ListIterator<T> {
	private Wheel<T> internal;
	private boolean removed = false;
	private WheelIterator() {
		super();
	}
	/**
	 * @param wheel
	 *            - the wheel to get elements from
	 */
	protected WheelIterator(Wheel<T> wheel) {
		this();
		this.internal = wheel;
	}
	/**
	 * Set the internal pointer to a random position without changing the content
	 * 
	 * @return this
	 */
	public WheelIterator<T> spin() {
		this.internal.spin();
		return this;
	}
	/**
	 * Set the pointer to the given position. Will not allow the pointer to move beyond the end of the content.
	 * 
	 * @param pos
	 *            - the new pointer position
	 * @return this
	 */
	public WheelIterator<T> setPointer(int pos) {
		this.internal.pointer = pos;
		this.internal.limitPointer();
		return this;
	}
	@Override
	public void add(T item) {
		this.internal.limitPointer();
		this.internal.pointer++ ;
		this.internal.add(this.internal.pointer, item);
	}
	@Override
	public void remove() {
		if (this.removed) {
			throw new IllegalStateException("remove() can only be called once per call to next()");
		}
		this.removed = true;
		this.internal.remove(this.internal.pointer);
		this.internal.limitPointer();
	}
	@Override
	public boolean hasNext() {
		this.internal.limitPointer();
		return this.internal.pointer < this.internal.size();
	}
	@Override
	public T next() {
		this.internal.wrapPointer();
		T ret = this.internal.get(this.internal.pointer);
		this.internal.pointer++ ;
		return ret;
	}
	@Override
	public int nextIndex() {
		this.internal.limitPointer();
		if ((this.internal.pointer + 1) >= this.internal.size()) {
			return 0;
		}
		return this.internal.pointer + 1;
	}
	@Override
	public boolean hasPrevious() {
		this.internal.limitPointer();
		return this.internal.pointer > 0;
	}
	@Override
	public T previous() {
		this.internal.wrapPointer();
		T ret = this.internal.get(this.internal.pointer);
		this.internal.pointer-- ;
		return ret;
	}
	@Override
	public int previousIndex() {
		this.internal.limitPointer();
		if ((this.internal.pointer - 1) <= 0) {
			return this.internal.size();
		}
		return this.internal.pointer - 1;
	}
	@Override
	public void set(T item) {
		this.internal.limitPointer();
		this.internal.set(this.internal.pointer, item);
	}
}
