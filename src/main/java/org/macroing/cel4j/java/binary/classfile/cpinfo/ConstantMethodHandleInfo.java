/**
 * Copyright 2009 - 2024 J&#246;rgen Lundgren
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
package org.macroing.cel4j.java.binary.classfile.cpinfo;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.java.binary.classfile.CPInfo;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code ConstantMethodHandleInfo} represents a {@code CONSTANT_MethodHandle_info} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code CONSTANT_MethodHandle_info} structure was added to Java in version 7 and can be found in the {@code constant_pool} table item of a {@code ClassFile} structure.
 * <p>
 * The {@code CONSTANT_MethodHandle_info} structure has the following format:
 * <pre>
 * <code>
 * CONSTANT_MethodHandle_info {
 *     u1 tag;
 *     u1 reference_kind;
 *     u2 reference_index;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ConstantMethodHandleInfo extends CPInfo {
	/**
	 * The name of the {@code CONSTANT_MethodHandle_info} structure.
	 */
	public static final String NAME = "CONSTANT_MethodHandle";
	
	/**
	 * A value that represents {@code REF_getField}.
	 */
	public static final int REF_GET_FIELD = 1;
	
	/**
	 * A value that represents {@code REF_getStatic}.
	 */
	public static final int REF_GET_STATIC = 2;
	
	/**
	 * A value that represents {@code REF_invokeInterface}.
	 */
	public static final int REF_INVOKE_INTERFACE = 9;
	
	/**
	 * A value that represents {@code REF_invokeSpecial}.
	 */
	public static final int REF_INVOKE_SPECIAL = 7;
	
	/**
	 * A value that represents {@code REF_invokeStatic}.
	 */
	public static final int REF_INVOKE_STATIC = 6;
	
	/**
	 * A value that represents {@code REF_invokeVirtual}.
	 */
	public static final int REF_INVOKE_VIRTUAL = 5;
	
	/**
	 * A value that represents {@code REF_newInvokeSpecial}.
	 */
	public static final int REF_NEW_INVOKE_SPECIAL = 8;
	
	/**
	 * A value that represents {@code REF_putField}.
	 */
	public static final int REF_PUT_FIELD = 3;
	
	/**
	 * A value that represents {@code REF_putStatic}.
	 */
	public static final int REF_PUT_STATIC = 4;
	
	/**
	 * The tag for the {@code CONSTANT_MethodHandle_info} structure.
	 */
	public static final int TAG = 15;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int referenceIndex;
	private int referenceKind;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ConstantMethodHandleInfo} instance that is a copy of {@code constantMethodHandleInfo}.
	 * <p>
	 * If {@code constantMethodHandleInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param constantMethodHandleInfo the {@code ConstantMethodHandleInfo} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code constantMethodHandleInfo} is {@code null}
	 */
	public ConstantMethodHandleInfo(final ConstantMethodHandleInfo constantMethodHandleInfo) {
		super(NAME, TAG, 1);
		
		this.referenceIndex = constantMethodHandleInfo.referenceIndex;
		this.referenceKind = constantMethodHandleInfo.referenceKind;
	}
	
	/**
	 * Constructs a new {@code ConstantMethodHandleInfo} instance.
	 * <p>
	 * If {@code referenceKind} is less than {@code 0}, or {@code referenceIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param referenceKind the value for the {@code reference_kind} item associated with this {@code ConstantMethodHandleInfo} instance
	 * @param referenceIndex the value for the {@code reference_index} item associated with this {@code ConstantMethodHandleInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code referenceKind} is less than {@code 0}, or {@code referenceIndex} is less than {@code 1}
	 */
	public ConstantMethodHandleInfo(final int referenceKind, final int referenceIndex) {
		super(NAME, TAG, 1);
		
		this.referenceIndex = ParameterArguments.requireRange(referenceIndex, 1, Integer.MAX_VALUE, "referenceIndex");
		this.referenceKind = ParameterArguments.requireRange(referenceKind, 0, Integer.MAX_VALUE, "referenceKind");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ConstantMethodHandleInfo} instance.
	 * 
	 * @return a copy of this {@code ConstantMethodHandleInfo} instance
	 */
	@Override
	public ConstantMethodHandleInfo copy() {
		return new ConstantMethodHandleInfo(this);
	}
	
	/**
	 * Writes this {@code ConstantMethodHandleInfo} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * constantMethodHandleInfo.write(new Document());
	 * }
	 * </pre>
	 * 
	 * @return the {@code Document}
	 */
	@Override
	public Document write() {
		return write(new Document());
	}
	
	/**
	 * Writes this {@code ConstantMethodHandleInfo} to {@code document}.
	 * <p>
	 * Returns {@code document}.
	 * <p>
	 * If {@code document} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param document the {@link Document} to write to
	 * @return {@code document}
	 * @throws NullPointerException thrown if, and only if, {@code document} is {@code null}
	 */
	@Override
	public Document write(final Document document) {
		document.linef("%s_info = {", getName());
		document.indent();
		document.linef("u1 tag = %s;", Integer.toString(getTag()));
		document.linef("u1 reference_kind = %s;", Integer.toString(getReferenceKind()));
		document.linef("u2 reference_index = %s;", Integer.toString(getReferenceIndex()));
		document.outdent();
		document.linef("};");
		
		return document;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ConstantMethodHandleInfo} instance.
	 * 
	 * @return a {@code String} representation of this {@code ConstantMethodHandleInfo} instance
	 */
	@Override
	public String toString() {
		return String.format("new ConstantMethodHandleInfo(%s, %s)", Integer.toString(getReferenceKind()), Integer.toString(getReferenceIndex()));
	}
	
	/**
	 * Compares {@code object} to this {@code ConstantMethodHandleInfo} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ConstantMethodHandleInfo}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ConstantMethodHandleInfo} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ConstantMethodHandleInfo}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ConstantMethodHandleInfo)) {
			return false;
		} else if(!Objects.equals(getName(), ConstantMethodHandleInfo.class.cast(object).getName())) {
			return false;
		} else if(getTag() != ConstantMethodHandleInfo.class.cast(object).getTag()) {
			return false;
		} else if(getConstantPoolEntryCount() != ConstantMethodHandleInfo.class.cast(object).getConstantPoolEntryCount()) {
			return false;
		} else if(getReferenceKind() != ConstantMethodHandleInfo.class.cast(object).getReferenceKind()) {
			return false;
		} else if(getReferenceIndex() != ConstantMethodHandleInfo.class.cast(object).getReferenceIndex()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code reference_index} item associated with this {@code ConstantMethodHandleInfo} instance.
	 * 
	 * @return the value of the {@code reference_index} item associated with this {@code ConstantMethodHandleInfo} instance
	 */
	public int getReferenceIndex() {
		return this.referenceIndex;
	}
	
	/**
	 * Returns the value of the {@code reference_kind} item associated with this {@code ConstantMethodHandleInfo} instance.
	 * 
	 * @return the value of the {@code reference_kind} item associated with this {@code ConstantMethodHandleInfo} instance
	 */
	public int getReferenceKind() {
		return this.referenceKind;
	}
	
	/**
	 * Returns a hash code for this {@code ConstantMethodHandleInfo} instance.
	 * 
	 * @return a hash code for this {@code ConstantMethodHandleInfo} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getTag()), Integer.valueOf(getConstantPoolEntryCount()), Integer.valueOf(getReferenceKind()), Integer.valueOf(getReferenceIndex()));
	}
	
	/**
	 * Sets {@code referenceIndex} as the value for the {@code reference_index} item associated with this {@code ConstantMethodHandleInfo} instance.
	 * <p>
	 * If {@code referenceIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param referenceIndex the value for the {@code reference_index} item associated with this {@code ConstantMethodHandleInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code referenceIndex} is less than {@code 1}
	 */
	public void setReferenceIndex(final int referenceIndex) {
		this.referenceIndex = ParameterArguments.requireRange(referenceIndex, 1, Integer.MAX_VALUE, "referenceIndex");
	}
	
	/**
	 * Sets {@code referenceKind} as the value for the {@code reference_kind} item associated with this {@code ConstantMethodHandleInfo} instance.
	 * <p>
	 * If {@code referenceKind} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param referenceKind the value for the {@code reference_kind} item associated with this {@code ConstantMethodHandleInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code referenceKind} is less than {@code 0}
	 */
	public void setReferenceKind(final int referenceKind) {
		this.referenceKind = ParameterArguments.requireRange(referenceKind, 0, Integer.MAX_VALUE, "referenceKind");
	}
	
	/**
	 * Writes this {@code ConstantMethodHandleInfo} to {@code dataOutput}.
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
			dataOutput.writeByte(getTag());
			dataOutput.writeByte(getReferenceKind());
			dataOutput.writeShort(getReferenceIndex());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code ConstantMethodHandleInfo} instances in {@code node}.
	 * <p>
	 * All {@code ConstantMethodHandleInfo} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code ConstantMethodHandleInfo} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<ConstantMethodHandleInfo> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), ConstantMethodHandleInfo.class);
	}
}