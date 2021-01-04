/**
 * Copyright 2009 - 2021 J&#246;rgen Lundgren
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code FullFrame} represents a {@code full_frame} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is indirectly mutable and not thread-safe.
 * <p>
 * The {@code full_frame} structure has the following format:
 * <pre>
 * <code>
 * full_frame {
 *     u1 frame_type;
 *     u2 offset_delta;
 *     u2 number_of_locals;
 *     verification_type_info[number_of_locals] locals;
 *     u2 number_of_stack_items;
 *     verification_type_info[number_of_stack_items] stack;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class FullFrame implements StackMapFrame {
	private final List<VerificationTypeInfo> locals;
	private final List<VerificationTypeInfo> stack;
	private final int frameType;
	private final int offsetDelta;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code FullFrame} instance.
	 * <p>
	 * If either {@code frameType} is not equal to {@code 255}, or {@code offsetDelta} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If either {@code locals}, {@code stack} or any of their elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param frameType the value of the {@code frame_type} item associated with this {@code FullFrame} instance
	 * @param offsetDelta the value of the {@code offset_delta} item associated with this {@code FullFrame} instance
	 * @param locals the values of the {@code locals} item associated with this {@code FullFrame} instance
	 * @param stack the values of the {@code stack} item associated with this {@code FullFrame} instance
	 * @throws IllegalArgumentException thrown if, and only if, either {@code frameType} is not equal to {@code 255}, or {@code offsetDelta} is less than {@code 0}
	 * @throws NullPointerException thrown if, and only if, either {@code locals}, {@code stack} or any of their elements are {@code null}
	 */
	public FullFrame(final int frameType, final int offsetDelta, final List<VerificationTypeInfo> locals, final List<VerificationTypeInfo> stack) {
		this.frameType = ParameterArguments.requireRange(frameType, 255, 255, "frameType");
		this.offsetDelta = ParameterArguments.requireRange(offsetDelta, 0, Integer.MAX_VALUE, "offsetDelta");
		this.locals = new ArrayList<>(ParameterArguments.requireNonNullList(locals, "locals"));
		this.stack = new ArrayList<>(ParameterArguments.requireNonNullList(stack, "stack"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code FullFrame} instance.
	 * 
	 * @return a copy of this {@code FullFrame} instance
	 */
	@Override
	public FullFrame copy() {
		final List<VerificationTypeInfo> locals = this.locals.stream().map(local -> local.copy()).collect(Collectors.toCollection(ArrayList::new));
		final List<VerificationTypeInfo> stack = this.stack.stream().map(stackItem -> stackItem.copy()).collect(Collectors.toCollection(ArrayList::new));
		
		return new FullFrame(getFrameType(), getOffsetDelta(), locals, stack);
	}
	
	/**
	 * Returns a {@code List} with the values of the {@code locals} item associated with this {@code FullFrame} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code FullFrame} instance.
	 * 
	 * @return a {@code List} with the values of the {@code locals} item associated with this {@code FullFrame} instance
	 */
	public List<VerificationTypeInfo> getLocals() {
		return new ArrayList<>(this.locals);
	}
	
	/**
	 * Returns a {@code List} with the values of the {@code stack} item associated with this {@code FullFrame} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code FullFrame} instance.
	 * 
	 * @return a {@code List} with the values of the {@code stack} item associated with this {@code FullFrame} instance
	 */
	public List<VerificationTypeInfo> getStack() {
		return new ArrayList<>(this.stack);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code FullFrame} instance.
	 * 
	 * @return a {@code String} representation of this {@code FullFrame} instance
	 */
	@Override
	public String toString() {
		return String.format("new FullFrame(%s, %s, new ArrayList<>(), new ArrayList<>())", Integer.toString(getFrameType()), Integer.toString(getOffsetDelta()));
	}
	
	/**
	 * Accepts a {@link NodeHierarchicalVisitor}.
	 * <p>
	 * Returns the result of {@code nodeHierarchicalVisitor.visitLeave(this)}.
	 * <p>
	 * If {@code nodeHierarchicalVisitor} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If a {@code RuntimeException} is thrown by the current {@code NodeHierarchicalVisitor}, a {@code NodeTraversalException} will be thrown with the {@code RuntimeException} wrapped.
	 * <p>
	 * This implementation will:
	 * <ul>
	 * <li>throw a {@code NullPointerException} if {@code nodeHierarchicalVisitor} is {@code null}.</li>
	 * <li>throw a {@code NodeTraversalException} if {@code nodeHierarchicalVisitor} throws a {@code RuntimeException}.</li>
	 * <li>traverse its child {@code Node} instances.</li>
	 * </ul>
	 * 
	 * @param nodeHierarchicalVisitor the {@code NodeHierarchicalVisitor} to accept
	 * @return the result of {@code nodeHierarchicalVisitor.visitLeave(this)}
	 * @throws NodeTraversalException thrown if, and only if, a {@code RuntimeException} is thrown by the current {@code NodeHierarchicalVisitor}
	 * @throws NullPointerException thrown if, and only if, {@code nodeHierarchicalVisitor} is {@code null}
	 */
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
	
	/**
	 * Compares {@code object} to this {@code FullFrame} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code FullFrame}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code FullFrame} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code FullFrame}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof FullFrame)) {
			return false;
		} else if(getFrameType() != FullFrame.class.cast(object).getFrameType()) {
			return false;
		} else if(getOffsetDelta() != FullFrame.class.cast(object).getOffsetDelta()) {
			return false;
		} else if(!Objects.equals(this.locals, FullFrame.class.cast(object).locals)) {
			return false;
		} else if(!Objects.equals(this.stack, FullFrame.class.cast(object).stack)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code frame_type} item associated with this {@code FullFrame} instance.
	 * 
	 * @return the value of the {@code frame_type} item associated with this {@code FullFrame} instance
	 */
	@Override
	public int getFrameType() {
		return this.frameType;
	}
	
	/**
	 * Returns the length of this {@code FullFrame} instance.
	 * 
	 * @return the length of this {@code FullFrame} instance
	 */
	@Override
	public int getLength() {
		int length = 7;
		
		for(final VerificationTypeInfo local : this.locals) {
			length += local.getLength();
		}
		
		for(final VerificationTypeInfo stackItem : this.stack) {
			length += stackItem.getLength();
		}
		
		return length;
	}
	
	/**
	 * Returns the value of the {@code number_of_locals} item associated with this {@code FullFrame} instance.
	 * 
	 * @return the value of the {@code number_of_locals} item associated with this {@code FullFrame} instance
	 */
	public int getNumberOfLocals() {
		return this.locals.size();
	}
	
	/**
	 * Returns the value of the {@code number_of_stack_items} item associated with this {@code FullFrame} instance.
	 * 
	 * @return the value of the {@code number_of_stack_items} item associated with this {@code FullFrame} instance
	 */
	public int getNumberOfStackItems() {
		return this.stack.size();
	}
	
	/**
	 * Returns the value of the {@code offset_delta} item associated with this {@code FullFrame} instance.
	 * 
	 * @return the value of the {@code offset_delta} item associated with this {@code FullFrame} instance
	 */
	public int getOffsetDelta() {
		return this.offsetDelta;
	}
	
	/**
	 * Returns a hash code for this {@code FullFrame} instance.
	 * 
	 * @return a hash code for this {@code FullFrame} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(getFrameType()), Integer.valueOf(getOffsetDelta()), this.locals, this.stack);
	}
	
	/**
	 * Writes this {@code FullFrame} to {@code dataOutput}.
	 * <p>
	 * If {@code dataOutput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an {@code IOException} is caught, an {@code UncheckedIOException} will be thrown.
	 * <p>
	 * This method does not close {@code dataOutput}.
	 * 
	 * @param dataOutput the {@code DataOutput} to write to
	 * @throws NullPointerException thrown if, and only if, {@code dataOutput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an {@code IOException} is caught
	 */
	@Override
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeByte(getFrameType());
			dataOutput.writeShort(getOffsetDelta());
			dataOutput.writeShort(getNumberOfLocals());
			
			for(final VerificationTypeInfo local : this.locals) {
				local.write(dataOutput);
			}
			
			dataOutput.writeShort(getNumberOfStackItems());
			
			for(final VerificationTypeInfo stackItem : this.stack) {
				stackItem.write(dataOutput);
			}
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}