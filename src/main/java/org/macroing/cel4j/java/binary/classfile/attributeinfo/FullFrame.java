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
package org.macroing.cel4j.java.binary.classfile.attributeinfo;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.ParameterArguments;

//TODO: Add Javadocs!
public final class FullFrame implements StackMapFrame {
	private final List<VerificationTypeInfo> locals;
	private final List<VerificationTypeInfo> stack;
	private final int frameType;
	private final int offsetDelta;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private FullFrame(final int frameType, final int offsetDelta, final List<VerificationTypeInfo> locals, final List<VerificationTypeInfo> stack) {
		this.frameType = frameType;
		this.offsetDelta = offsetDelta;
		this.locals = locals;
		this.stack = stack;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	@Override
	public FullFrame copy() {
		final List<VerificationTypeInfo> locals = this.locals.stream().map(local -> local.copy()).collect(Collectors.toCollection(ArrayList::new));
		final List<VerificationTypeInfo> stack = this.stack.stream().map(stackItem -> stackItem.copy()).collect(Collectors.toCollection(ArrayList::new));
		
		return new FullFrame(this.frameType, this.offsetDelta, locals, stack);
	}
	
//	TODO: Add Javadocs!
	public List<VerificationTypeInfo> getLocals() {
		return new ArrayList<>(this.locals);
	}
	
//	TODO: Add Javadocs!
	public List<VerificationTypeInfo> getStack() {
		return new ArrayList<>(this.stack);
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				for(final VerificationTypeInfo local : this.locals) {
					if(!local.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
				}
				
				for(final VerificationTypeInfo stackItem : this.stack) {
					if(!stackItem.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
				}
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof FullFrame)) {
			return false;
		} else if(!Objects.equals(Integer.valueOf(this.frameType), Integer.valueOf(FullFrame.class.cast(object).frameType))) {
			return false;
		} else if(!Objects.equals(Integer.valueOf(this.offsetDelta), Integer.valueOf(FullFrame.class.cast(object).offsetDelta))) {
			return false;
		} else if(!Objects.equals(this.locals, FullFrame.class.cast(object).locals)) {
			return false;
		} else if(!Objects.equals(this.stack, FullFrame.class.cast(object).stack)) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	@Override
	public int getFrameType() {
		return this.frameType;
	}
	
//	TODO: Add Javadocs!
	@Override
	public int getLength() {
		int length = 3;
		
		for(final VerificationTypeInfo local : this.locals) {
			length += local.getLength();
		}
		
		return length;
	}
	
//	TODO: Add Javadocs!
	public int getNumberOfLocals() {
		return this.locals.size();
	}
	
//	TODO: Add Javadocs!
	public int getNumberOfStackItems() {
		return this.stack.size();
	}
	
//	TODO: Add Javadocs!
	public int getOffsetDelta() {
		return this.offsetDelta;
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(this.frameType), Integer.valueOf(this.offsetDelta), this.locals, this.stack);
	}
	
//	TODO: Add Javadocs!
	@Override
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeByte(this.frameType);
			dataOutput.writeShort(this.offsetDelta);
			dataOutput.writeShort(getNumberOfLocals());
			
			this.locals.forEach(local -> local.write(dataOutput));
			
			dataOutput.writeShort(getNumberOfStackItems());
			
			this.stack.forEach(stackItem -> stackItem.write(dataOutput));
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static FullFrame newInstance(final int frameType, final int offsetDelta, final List<VerificationTypeInfo> locals, final List<VerificationTypeInfo> stack) {
		final int frameType0 = ParameterArguments.requireRange(frameType, 255, 255, "frameType");
		final int offsetDelta0 = ParameterArguments.requireRange(offsetDelta, 0, Integer.MAX_VALUE, "offsetDelta");
		
		final List<VerificationTypeInfo> locals0 = new ArrayList<>(ParameterArguments.requireNonNullList(locals, "locals"));
		final List<VerificationTypeInfo> stack0 = new ArrayList<>(ParameterArguments.requireNonNullList(stack, "stack"));
		
		return new FullFrame(frameType0, offsetDelta0, locals0, stack0);
	}
}