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
package org.macroing.cel4j.java.binary.classfile.string;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.macroing.cel4j.util.ParameterArguments;

abstract class Scanner {
	private final DataBuffer dataBuffer;
	private final Map<Integer, Integer> indicesAtBeginningOfConsumption;
	private final Map<Integer, Integer> indicesAtEndOfConsumption;
	private int indexAtBeginningOfConsumption;
	private int indexAtEndOfConsumption;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	protected Scanner(final DataBuffer dataBuffer) {
		this.dataBuffer = Objects.requireNonNull(dataBuffer, "dataBuffer == null");
		this.indicesAtBeginningOfConsumption = new LinkedHashMap<>();
		this.indicesAtEndOfConsumption = new LinkedHashMap<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public final String consumption() {
		final StringBuilder stringBuilder = new StringBuilder();
		
		if(this.indexAtEndOfConsumption > this.indexAtBeginningOfConsumption) {
			for(int i = this.indexAtBeginningOfConsumption; i < this.indexAtEndOfConsumption; i++) {
				stringBuilder.append(toString(i));
			}
		}
		
		return stringBuilder.toString();
	}
	
	public abstract String toString(final int absoluteIndex);
	
	public final boolean consume() {
		return doConsume(null);
	}
	
	public final boolean consume(final StringBuilder stringBuilder) {
		return doConsume(Objects.requireNonNull(stringBuilder, "stringBuilder == null"));
	}
	
	public abstract boolean next();
	
	public final boolean rewind(final int key) {
		boolean isSuccessful = false;
		
		final Integer key0 = Integer.valueOf(key);
		
		if(this.indicesAtBeginningOfConsumption.containsKey(key0) && this.indicesAtEndOfConsumption.containsKey(key0)) {
			final int indexAtBeginningOfConsumption = this.indicesAtBeginningOfConsumption.remove(key0).intValue();
			final int indexAtEndOfConsumption = this.indicesAtEndOfConsumption.remove(key0).intValue();
			
			this.indexAtBeginningOfConsumption = indexAtBeginningOfConsumption;
			this.indexAtEndOfConsumption = indexAtEndOfConsumption;
			
			isSuccessful = true;
		}
		
		return isSuccessful;
	}
	
	public final boolean skip() {
		boolean hasSkipped = false;
		
		final int indexAtBeginningOfConsumption = this.indexAtBeginningOfConsumption + 1;
		final int indexAtEndOfConsumption = Math.max(indexAtBeginningOfConsumption, this.indexAtEndOfConsumption);
		
		if(indexAtBeginningOfConsumption < this.dataBuffer.size()) {
			this.indexAtBeginningOfConsumption = indexAtBeginningOfConsumption;
			this.indexAtEndOfConsumption = indexAtEndOfConsumption;
			
			hasSkipped = true;
		}
		
		return hasSkipped;
	}
	
	public final boolean testNext() {
		final int key = mark();
		
		try {
			return next();
		} finally {
			rewind(key);
		}
	}
	
	public final boolean unmark(final int key) {
		boolean isSuccessful = false;
		
		final Integer key0 = Integer.valueOf(key);
		
		if(this.indicesAtBeginningOfConsumption.containsKey(key0) && this.indicesAtEndOfConsumption.containsKey(key0)) {
			this.indicesAtBeginningOfConsumption.remove(key0);
			this.indicesAtEndOfConsumption.remove(key0);
			
			isSuccessful = true;
		}
		
		return isSuccessful;
	}
	
	public final int getIndexAtBeginningOfConsumption() {
		return this.indexAtBeginningOfConsumption;
	}
	
	public final int getIndexAtEndOfConsumption() {
		return this.indexAtEndOfConsumption;
	}
	
	public final int mark() {
		final Random random = ThreadLocalRandom.current();
		
		final int indexAtBeginningOfConsumption = this.indexAtBeginningOfConsumption;
		final int indexAtEndOfConsumption = this.indexAtEndOfConsumption;
		
		Integer key = Integer.valueOf(random.nextInt());
		
		while(this.indicesAtBeginningOfConsumption.containsKey(key)) {
			key = Integer.valueOf(random.nextInt());
		}
		
		this.indicesAtBeginningOfConsumption.put(key, Integer.valueOf(indexAtBeginningOfConsumption));
		this.indicesAtEndOfConsumption.put(key, Integer.valueOf(indexAtEndOfConsumption));
		
		return key.intValue();
	}
	
	public abstract void error();
	
	public abstract void error(final String message);
	
	public final void setIndexAtBeginningOfConsumption(final int indexAtBeginningOfConsumption) {
		this.indexAtBeginningOfConsumption = ParameterArguments.requireRange(indexAtBeginningOfConsumption, 0, Integer.MAX_VALUE);
	}
	
	public final void setIndexAtEndOfConsumption(final int indexAtEndOfConsumption) {
		this.indexAtEndOfConsumption = ParameterArguments.requireRange(indexAtEndOfConsumption, this.indexAtBeginningOfConsumption, Integer.MAX_VALUE);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private boolean doConsume(final StringBuilder stringBuilder) {
		boolean hasConsumed = false;
		
		if(this.indexAtEndOfConsumption > this.indexAtBeginningOfConsumption) {
			if(stringBuilder != null) {
				for(int i = this.indexAtBeginningOfConsumption; i < this.indexAtEndOfConsumption; i++) {
					stringBuilder.append(toString(i));
				}
			}
			
			this.indexAtBeginningOfConsumption = this.indexAtEndOfConsumption;
			
			hasConsumed = true;
		}
		
		return hasConsumed;
	}
}