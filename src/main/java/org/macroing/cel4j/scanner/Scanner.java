/**
 * Copyright 2009 - 2020 J&#246;rgen Lundgren
 * 
 * This file is part of org.macroing.cel4j.
 * 
 * org.macroing.cel4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * org.macroing.cel4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with org.macroing.cel4j. If not, see <http://www.gnu.org/licenses/>.
 */
package org.macroing.cel4j.scanner;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code Scanner} is an abstract base class for a scanner.
 * <p>
 * This class makes no assumptions about the data to scan. That is decided by any given implementation.
 * <p>
 * All {@code Scanner} implementations are built around two indices. These are the index at the beginning of the current consumption and the index at the end of the current consumption, which are inclusive and exclusive, respectively. A consumption,
 * which means that data can be consumed, exists as long as the index at the beginning of the current consumption is less than the index at the end of the current consumption.
 * <p>
 * In order to consume data, the data has to be available for consumption. The {@link #next()} method attempts to make data available for consumption. It essentially increments the index at the end of the current consumption. Subclasses may contain
 * other methods than {@code next()}, for more specific use-cases. When data is available for consumption, the methods {@link #consume()} and {@link #consume(Object)} can be used to consume the data and the method {@link #skip()} can be used to skip
 * the data. The methods {@link #consumption()} and {@link #consumption(int, int)} can be used to look at the data available for consumption.
 * <p>
 * Sometimes it is necessary to perform tests for certain data elements. This is where the {@link #testNext()} method comes into play. Subclasses may contain other methods than {@code testNext()}, for more specific use-cases. The {@code testNext()}
 * method requires another useful feature to work. This is to save and load the current state of the {@code Scanner}. The {@link #stateSave()} method saves the current state and returns a {@link Key} that is associated with that saved state. To load
 * state that was previously saved into the {@code Scanner}, use the {@link #stateLoad(Key)} method. To delete previously saved state, use the {@link #stateDelete(Key)} method.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public abstract class Scanner<T, U> {
	private final AtomicInteger indexAtBeginningInclusive;
	private final AtomicInteger indexAtEndExclusive;
	private final Map<Key, State> states;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Scanner} instance.
	 */
	protected Scanner() {
		this.indexAtBeginningInclusive = new AtomicInteger();
		this.indexAtEndExclusive = new AtomicInteger();
		this.states = new LinkedHashMap<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Saves the state of this {@code Scanner} instance for later use.
	 * <p>
	 * Returns a {@link Key} that can be used to load the state at a later time.
	 * <p>
	 * To load the state associated with the returned {@code Key} into this {@code Scanner} instance, call {@link #stateLoad(Key)}.
	 * <p>
	 * To delete the state associated with the returned {@code Key}, call {@link #stateDelete(Key)}.
	 * 
	 * @return a {@code Key} that can be used to load the state at a later time
	 */
	public final Key stateSave() {
		final Map<Key, State> states = this.states;
		
		final int indexAtBeginningInclusive = getIndexAtBeginningInclusive();
		final int indexAtEndExclusive = getIndexAtEndExclusive();
		
		final State state = new State(indexAtBeginningInclusive, indexAtEndExclusive);
		
		Key key = new Key();
		
		while(states.containsKey(key)) {
			key = new Key();
		}
		
		states.put(key, state);
		
		return key;
	}
	
	/**
	 * Returns an {@code Object} that contains the current consumption without consuming it.
	 * 
	 * @return an {@code Object} that contains the current consumption without consuming it
	 */
	public final U consumption() {
		return consumption(getIndexAtBeginningInclusive(), getIndexAtEndExclusive());
	}
	
	/**
	 * Returns an {@code Object} that contains the consumption between {@code indexAtBeginningInclusive} (inclusive) and {@code indexAtEndExclusive} (exclusive).
	 * <p>
	 * If {@code indexAtBeginningInclusive} is less than {@code 0} or greater than {@code indexAtEndExclusive}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param indexAtBeginningInclusive the index at the beginning of the consumption, which is inclusive
	 * @param indexAtEndExclusive the index at the end of the consumption, which is exclusive
	 * @return an {@code Object} that contains the consumption between {@code indexAtBeginningInclusive} (inclusive) and {@code indexAtEndExclusive} (exclusive)
	 * @throws IllegalArgumentException thrown if, and only if, {@code indexAtBeginningInclusive} is less than {@code 0} or greater than {@code indexAtEndExclusive}
	 */
	public abstract U consumption(final int indexAtBeginningInclusive, final int indexAtEndExclusive);
	
	/**
	 * Consumes the data between the indices representing the beginning and the end of the current consumption.
	 * <p>
	 * Returns {@code true} if, and only if, data was consumed, {@code false} otherwise.
	 * <p>
	 * If data can be consumed, {@link #consume(int, int)} will be called. Then it will set the index at the beginning of the current consumption to the index at the end of the current consumption.
	 * 
	 * @return {@code true} if, and only if, data was consumed, {@code false} otherwise
	 */
	public final boolean consume() {
		final int indexAtBeginningInclusive = getIndexAtBeginningInclusive();
		final int indexAtEndExclusive = getIndexAtEndExclusive();
		
		if(indexAtBeginningInclusive < indexAtEndExclusive) {
			consume(indexAtBeginningInclusive, indexAtEndExclusive);
			
			setIndexAtBeginningInclusive(indexAtEndExclusive);
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Consumes the data between the indices representing the beginning and the end of the current consumption.
	 * <p>
	 * Returns {@code true} if, and only if, data was consumed, {@code false} otherwise.
	 * <p>
	 * If {@code object} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If data can be consumed, {@link #consume(int, int, Object)} will be called. Then it will set the index at the beginning of the current consumption to the index at the end of the current consumption.
	 * 
	 * @param object the {@code Object} that will consume the data
	 * @return {@code true} if, and only if, data was consumed, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code object} is {@code null}
	 */
	public final boolean consume(final T object) {
		Objects.requireNonNull(object, "object == null");
		
		final int indexAtBeginningInclusive = getIndexAtBeginningInclusive();
		final int indexAtEndExclusive = getIndexAtEndExclusive();
		
		if(indexAtBeginningInclusive < indexAtEndExclusive) {
			consume(indexAtBeginningInclusive, indexAtEndExclusive, object);
			
			setIndexAtBeginningInclusive(indexAtEndExclusive);
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Reads the next data element.
	 * <p>
	 * Returns {@code true} if, and only if, the next data element was read, {@code false} otherwise.
	 * <p>
	 * This method essentially increments the index at the end of the current consumption.
	 * <p>
	 * If this method returns {@code true}, the {@link #consume()} and {@link #consume(Object)} methods can be successfully called.
	 * 
	 * @return {@code true} if, and only if, the next data element was read, {@code false} otherwise
	 */
	public abstract boolean next();
	
	/**
	 * Loads the state associated with {@code key} into this {@code Scanner} instance.
	 * <p>
	 * Returns {@code true} if, and only if, the state was loaded into this {@code Scanner} instance, {@code false} otherwise.
	 * <p>
	 * If {@code key} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * This method will not delete the state associated with {@code key}. That requires a call to {@link #stateDelete(Key)}.
	 * 
	 * @param key the {@link Key} associated with the state to load
	 * @return {@code true} if, and only if, the state was loaded into this {@code Scanner} instance, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code key} is {@code null}
	 */
	public final boolean stateLoad(final Key key) {
		final Map<Key, State> states = this.states;
		
		final State state = states.get(Objects.requireNonNull(key, "key == null"));
		
		if(state != null) {
			int indexAtBeginningInclusive = state.getIndexAtBeginningInclusive();
			int indexAtEndExclusive = state.getIndexAtEndExclusive();
			
			setIndexAtBeginningInclusiveAndIndexAtEndExclusive(indexAtBeginningInclusive, indexAtEndExclusive);
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Skips the data between the indices representing the beginning and the end of the current consumption.
	 * <p>
	 * Returns {@code true} if, and only if, data was skipped, {@code false} otherwise.
	 * <p>
	 * If data can be skipped, it will set the index at the beginning of the current consumption to the index at the end of the current consumption.
	 * 
	 * @return {@code true} if, and only if, data was skipped, {@code false} otherwise
	 */
	public final boolean skip() {
		int indexAtBeginningInclusive = getIndexAtBeginningInclusive();
		int indexAtEndExclusive = getIndexAtEndExclusive();
		
		if(indexAtBeginningInclusive < indexAtEndExclusive) {
			setIndexAtBeginningInclusive(indexAtEndExclusive);
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * Deletes the state associated with {@code key} in this {@code Scanner} instance.
	 * <p>
	 * Returns {@code true} if, and only if, the state associated with {@code key} was deleted, {@code false} otherwise.
	 * <p>
	 * If {@code key} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param key the {@link Key} associated with the state to delete
	 * @return {@code true} if, and only if, the state associated with {@code key} was deleted, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code key} is {@code null}
	 */
	public final boolean stateDelete(final Key key) {
		return this.states.remove(Objects.requireNonNull(key, "key == null")) != null;
	}
	
	/**
	 * Tests whether {@link #next()} can be called successfully.
	 * <p>
	 * Returns {@code true} if, and only if, {@code next()} returns {@code true}, {@code false} otherwise.
	 * <p>
	 * This method calls {@link #stateSave()} prior to {@code next()} and {@link #stateLoad(Key)} as well as {@link #stateDelete(Key)} afterwards, which means that the result of {@code next()} cannot be consumed.
	 * 
	 * @return {@code true} if, and only if, {@code next()} returns {@code true}, {@code false} otherwise
	 */
	public final boolean testNext() {
		final Key key = stateSave();
		
		try {
			return next();
		} finally {
			stateLoad(key);
			stateDelete(key);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the index at the beginning of the current consumption.
	 * <p>
	 * This index is inclusive.
	 * 
	 * @return the index at the beginning of the current consumption
	 */
	protected final int getIndexAtBeginningInclusive() {
		return this.indexAtBeginningInclusive.get();
	}
	
	/**
	 * Returns the index at the end of the current consumption.
	 * <p>
	 * This index is exclusive.
	 * 
	 * @return the index at the end of the current consumption
	 */
	protected final int getIndexAtEndExclusive() {
		return this.indexAtEndExclusive.get();
	}
	
	/**
	 * Called by {@link #consume()} when data can be consumed.
	 * <p>
	 * All parameter arguments passed to this method should be valid.
	 * 
	 * @param indexAtBeginningInclusive the index at the beginning of the consumption, which is inclusive
	 * @param indexAtEndExclusive the index at the end of the consumption, which is exclusive
	 */
	protected abstract void consume(final int indexAtBeginningInclusive, final int indexAtEndExclusive);
	
	/**
	 * Called by {@link #consume(Object)} when data can be consumed.
	 * <p>
	 * All parameter arguments passed to this method should be valid.
	 * 
	 * @param indexAtBeginningInclusive the index at the beginning of the consumption, which is inclusive
	 * @param indexAtEndExclusive the index at the end of the consumption, which is exclusive
	 * @param object the {@code Object} that consumes the data
	 */
	protected abstract void consume(final int indexAtBeginningInclusive, final int indexAtEndExclusive, final T object);
	
	/**
	 * Sets the index at the beginning of the consumption.
	 * <p>
	 * If {@code indexAtBeginningInclusive} is less than {@code 0} or greater than {@code scanner.getIndexAtEndExclusive()}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param indexAtBeginningInclusive the index at the beginning of the consumption, which is inclusive
	 * @throws IllegalArgumentException thrown if, and only if, {@code indexAtBeginningInclusive} is less than {@code 0} or greater than {@code scanner.getIndexAtEndExclusive()}
	 */
	protected final void setIndexAtBeginningInclusive(final int indexAtBeginningInclusive) {
		this.indexAtBeginningInclusive.set(ParameterArguments.requireRange(indexAtBeginningInclusive, 0, getIndexAtEndExclusive(), "indexAtBeginningInclusive"));
	}
	
	/**
	 * Sets the indices at the beginning and the end of the consumption.
	 * <p>
	 * If {@code indexAtBeginningInclusive} is less than {@code 0} or greater than {@code indexAtEndExclusive}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param indexAtBeginningInclusive the index at the beginning of the consumption, which is inclusive
	 * @param indexAtEndExclusive the index at the end of the consumption, which is exclusive
	 * @throws IllegalArgumentException thrown if, and only if, {@code indexAtBeginningInclusive} is less than {@code 0} or greater than {@code indexAtEndExclusive}
	 */
	protected final void setIndexAtBeginningInclusiveAndIndexAtEndExclusive(final int indexAtBeginningInclusive, final int indexAtEndExclusive) {
		ParameterArguments.requireRange(indexAtBeginningInclusive, 0, indexAtEndExclusive, "indexAtBeginningInclusive");
		ParameterArguments.requireRange(indexAtEndExclusive, indexAtBeginningInclusive, Integer.MAX_VALUE, "indexAtEndExclusive");//Might not be necessary?
		
		this.indexAtBeginningInclusive.set(indexAtBeginningInclusive);
		this.indexAtEndExclusive.set(indexAtEndExclusive);
	}
	
	/**
	 * Sets the index at the end of the consumption.
	 * <p>
	 * If {@code indexAtEndExclusive} is less than {@code scanner.getIndexAtBeginningInclusive()}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param indexAtEndExclusive the index at the end of the consumption, which is exclusive
	 * @throws IllegalArgumentException thrown if, and only if, {@code indexAtEndExclusive} is less than {@code scanner.getIndexAtBeginningInclusive()}
	 */
	protected final void setIndexAtEndExclusive(final int indexAtEndExclusive) {
		this.indexAtEndExclusive.set(ParameterArguments.requireRange(indexAtEndExclusive, getIndexAtBeginningInclusive(), Integer.MAX_VALUE, "indexAtEndExclusive"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final class State {
		private final int indexAtBeginningInclusive;
		private final int indexAtEndExclusive;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		public State(final int indexAtBeginningInclusive, final int indexAtEndExclusive) {
			this.indexAtBeginningInclusive = indexAtBeginningInclusive;
			this.indexAtEndExclusive = indexAtEndExclusive;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		@Override
		public String toString() {
			return String.format("new State(%s, %s)", Integer.toString(this.indexAtBeginningInclusive), Integer.toString(this.indexAtEndExclusive));
		}
		
		@Override
		public boolean equals(final Object object) {
			if(object == this) {
				return true;
			} else if(!(object instanceof State)) {
				return false;
			} else if(this.indexAtBeginningInclusive != State.class.cast(object).indexAtBeginningInclusive) {
				return false;
			} else if(this.indexAtEndExclusive != State.class.cast(object).indexAtEndExclusive) {
				return false;
			} else {
				return true;
			}
		}
		
		public int getIndexAtBeginningInclusive() {
			return this.indexAtBeginningInclusive;
		}
		
		public int getIndexAtEndExclusive() {
			return this.indexAtEndExclusive;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(Integer.valueOf(this.indexAtBeginningInclusive), Integer.valueOf(this.indexAtEndExclusive));
		}
	}
}