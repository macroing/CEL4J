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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * An {@code AppendFrame} represents an {@code append_frame} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is indirectly mutable and not thread-safe.
 * <p>
 * The {@code append_frame} structure has the following format:
 * <pre>
 * <code>
 * append_frame {
 *     u1 frame_type;
 *     u2 offset_delta;
 *     verification_type_info[frame_type - 251] locals;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class AppendFrame implements StackMapFrame {
	private final List<VerificationTypeInfo> locals;
	private final int frameType;
	private final int offsetDelta;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code AppendFrame} instance.
	 * <p>
	 * If either {@code frameType} is less than {@code 252} or greater than {@code 254}, or {@code offsetDelta} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If either {@code locals} or any of its elements are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param frameType the value of the {@code frame_type} item associated with this {@code AppendFrame} instance
	 * @param offsetDelta the value of the {@code offset_delta} item associated with this {@code AppendFrame} instance
	 * @param locals the {@link VerificationTypeInfo} instances associated with this {@code AppendFrame} instance
	 * @throws IllegalArgumentException thrown if, and only if, either {@code frameType} is less than {@code 252} or greater than {@code 254}, or {@code offsetDelta} is less than {@code 0}
	 * @throws NullPointerException thrown if, and only if, either {@code locals} or any of its elements are {@code null}
	 */
	public AppendFrame(final int frameType, final int offsetDelta, final List<VerificationTypeInfo> locals) {
		this.frameType = ParameterArguments.requireRange(frameType, 252, 254, "frameType");
		this.offsetDelta = ParameterArguments.requireRange(offsetDelta, 0, Integer.MAX_VALUE, "offsetDelta");
		this.locals = new ArrayList<>(ParameterArguments.requireNonNullList(locals, "locals"));
		
		if(this.locals.size() != this.frameType - 251) {
			throw new IllegalArgumentException(String.format("locals.size() != frameType - 251: Expected %s but found %s", Integer.valueOf(this.frameType - 251), Integer.valueOf(this.locals.size())));
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code AppendFrame} instance.
	 * 
	 * @return a copy of this {@code AppendFrame} instance
	 */
	@Override
	public AppendFrame copy() {
		return new AppendFrame(getFrameType(), getOffsetDelta(), this.locals.stream().map(local -> local.copy()).collect(Collectors.toCollection(ArrayList::new)));
	}
	
	/**
	 * Returns a {@code List} with all {@link VerificationTypeInfo} instances associated with this {@code AppendFrame} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code AppendFrame} instance.
	 * 
	 * @return a {@code List} with all {@code VerificationTypeInfo} instances associated with this {@code AppendFrame} instance
	 */
	public List<VerificationTypeInfo> getLocals() {
		return new ArrayList<>(this.locals);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code AppendFrame} instance.
	 * 
	 * @return a {@code String} representation of this {@code AppendFrame} instance
	 */
	@Override
	public String toString() {
		return String.format("new AppendFrame(%s, %s, new ArrayList<>())", Integer.toString(getFrameType()), Integer.toString(getOffsetDelta()));
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
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Compares {@code object} to this {@code AppendFrame} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code AppendFrame}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code AppendFrame} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code AppendFrame}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof AppendFrame)) {
			return false;
		} else if(getFrameType() != AppendFrame.class.cast(object).getFrameType()) {
			return false;
		} else if(getOffsetDelta() != AppendFrame.class.cast(object).getOffsetDelta()) {
			return false;
		} else if(!Objects.equals(this.locals, AppendFrame.class.cast(object).locals)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code frame_type} item associated with this {@code AppendFrame} instance.
	 * 
	 * @return the value of the {@code frame_type} item associated with this {@code AppendFrame} instance
	 */
	@Override
	public int getFrameType() {
		return this.frameType;
	}
	
	/**
	 * Returns the length of this {@code AppendFrame} instance.
	 * 
	 * @return the length of this {@code AppendFrame} instance
	 */
	@Override
	public int getLength() {
		int length = 3;
		
		for(final VerificationTypeInfo local : this.locals) {
			length += local.getLength();
		}
		
		return length;
	}
	
	/**
	 * Returns the value of the {@code offset_delta} item associated with this {@code AppendFrame} instance.
	 * 
	 * @return the value of the {@code offset_delta} item associated with this {@code AppendFrame} instance
	 */
	public int getOffsetDelta() {
		return this.offsetDelta;
	}
	
	/**
	 * Returns a hash code for this {@code AppendFrame} instance.
	 * 
	 * @return a hash code for this {@code AppendFrame} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(getFrameType()), Integer.valueOf(getOffsetDelta()), this.locals);
	}
	
	/**
	 * Writes this {@code AppendFrame} to {@code dataOutput}.
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
			
			for(final VerificationTypeInfo local : this.locals) {
				local.write(dataOutput);
			}
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}