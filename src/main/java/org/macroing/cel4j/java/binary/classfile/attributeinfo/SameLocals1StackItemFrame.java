/**
 * Copyright 2009 - 2022 J&#246;rgen Lundgren
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
import java.util.Objects;

import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code SameLocals1StackItemFrame} represents a {@code same_locals_1_stack_item_frame} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is indirectly mutable and not thread-safe.
 * <p>
 * The {@code same_locals_1_stack_item_frame} structure has the following format:
 * <pre>
 * <code>
 * same_locals_1_stack_item_frame {
 *     u1 frame_type;
 *     verification_type_info[1] stack;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class SameLocals1StackItemFrame implements StackMapFrame {
	private final VerificationTypeInfo stackItem;
	private final int frameType;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code SameLocals1StackItemFrame} instance.
	 * <p>
	 * If {@code frameType} is less than {@code 64} or greater than {@code 127}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code stackItem} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param frameType the value of the {@code frame_type} item associated with this {@code SameLocals1StackItemFrame} instance
	 * @param stackItem the value of the {@code stack[0]} item associated with this {@code SameLocals1StackItemFrame} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code frameType} is less than {@code 64} or greater than {@code 127}
	 * @throws NullPointerException thrown if, and only if, {@code stackItem} is {@code null}
	 */
	public SameLocals1StackItemFrame(final int frameType, final VerificationTypeInfo stackItem) {
		this.frameType = ParameterArguments.requireRange(frameType, 64, 127, "frameType");
		this.stackItem = Objects.requireNonNull(stackItem, "stackItem == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code SameLocals1StackItemFrame} instance.
	 * 
	 * @return a copy of this {@code SameLocals1StackItemFrame} instance
	 */
	@Override
	public SameLocals1StackItemFrame copy() {
		return new SameLocals1StackItemFrame(getFrameType(), getStackItem().copy());
	}
	
	/**
	 * Returns a {@code String} representation of this {@code SameLocals1StackItemFrame} instance.
	 * 
	 * @return a {@code String} representation of this {@code SameLocals1StackItemFrame} instance
	 */
	@Override
	public String toString() {
		return String.format("new SameLocals1StackItemFrame(%s, %s)", Integer.toString(getFrameType()), getStackItem());
	}
	
	/**
	 * Returns the value of the {@code stack[0]} item associated with this {@code SameLocals1StackItemFrame} instance.
	 * 
	 * @return the value of the {@code stack[0]} item associated with this {@code SameLocals1StackItemFrame} instance
	 */
	public VerificationTypeInfo getStackItem() {
		return this.stackItem;
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
	 * <li>traverse its child {@code Node} instance.</li>
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
				if(!getStackItem().accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
				}
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Compares {@code object} to this {@code SameLocals1StackItemFrame} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code SameLocals1StackItemFrame}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code SameLocals1StackItemFrame} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code SameLocals1StackItemFrame}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof SameLocals1StackItemFrame)) {
			return false;
		} else if(getFrameType() != SameLocals1StackItemFrame.class.cast(object).getFrameType()) {
			return false;
		} else if(!Objects.equals(getStackItem(), SameLocals1StackItemFrame.class.cast(object).getStackItem())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code frame_type} item associated with this {@code SameLocals1StackItemFrame} instance.
	 * 
	 * @return the value of the {@code frame_type} item associated with this {@code SameLocals1StackItemFrame} instance
	 */
	@Override
	public int getFrameType() {
		return this.frameType;
	}
	
	/**
	 * Returns the length of this {@code SameLocals1StackItemFrame} instance.
	 * 
	 * @return the length of this {@code SameLocals1StackItemFrame} instance
	 */
	@Override
	public int getLength() {
		return 1 + getStackItem().getLength();
	}
	
	/**
	 * Returns a hash code for this {@code SameLocals1StackItemFrame} instance.
	 * 
	 * @return a hash code for this {@code SameLocals1StackItemFrame} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(getFrameType()), getStackItem());
	}
	
	/**
	 * Writes this {@code SameLocals1StackItemFrame} to {@code dataOutput}.
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
			
			final
			VerificationTypeInfo verificationTypeInfo = getStackItem();
			verificationTypeInfo.write(dataOutput);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}