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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.FieldInfo;
import org.macroing.cel4j.java.binary.classfile.MethodInfo;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code SignatureAttribute} represents a {@code Signature_attribute} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code Signature_attribute} structure has the following format:
 * <pre>
 * <code>
 * Signature_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 signature_index;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class SignatureAttribute extends AttributeInfo {
	/**
	 * The name of the {@code Signature_attribute} structure.
	 */
	public static final String NAME = "Signature";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int signatureIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code SignatureAttribute} instance.
	 * <p>
	 * If either {@code attributeNameIndex} or {@code signatureIndex} are less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the value for the {@code attribute_name_index} item associated with this {@code SignatureAttribute} instance
	 * @param signatureIndex the value for the {@code signature_index} item associated with this {@code SignatureAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, either {@code attributeNameIndex} or {@code signatureIndex} are less than {@code 1}
	 */
	public SignatureAttribute(final int attributeNameIndex, final int signatureIndex) {
		super(NAME, attributeNameIndex);
		
		this.signatureIndex = ParameterArguments.requireRange(signatureIndex, 1, Integer.MAX_VALUE);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code SignatureAttribute} instance.
	 * 
	 * @return a copy of this {@code SignatureAttribute} instance
	 */
	@Override
	public SignatureAttribute copy() {
		return new SignatureAttribute(getAttributeNameIndex(), getSignatureIndex());
	}
	
	/**
	 * Returns a {@code String} representation of this {@code SignatureAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code SignatureAttribute} instance
	 */
	@Override
	public String toString() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Signature_attribute");
		stringBuilder.append(":");
		stringBuilder.append(" ");
		stringBuilder.append("name=" + getName());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_name_index=" + getAttributeNameIndex());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_length=" + getAttributeLength());
		stringBuilder.append(" ");
		stringBuilder.append("signature_index=" + this.signatureIndex);
		
		return stringBuilder.toString();
	}
	
	/**
	 * Compares {@code object} to this {@code SignatureAttribute} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code SignatureAttribute}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code SignatureAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code SignatureAttribute}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof SignatureAttribute)) {
			return false;
		} else if(!Objects.equals(SignatureAttribute.class.cast(object).getName(), getName())) {
			return false;
		} else if(SignatureAttribute.class.cast(object).getAttributeNameIndex() != getAttributeNameIndex()) {
			return false;
		} else if(SignatureAttribute.class.cast(object).getAttributeLength() != getAttributeLength()) {
			return false;
		} else if(SignatureAttribute.class.cast(object).getSignatureIndex() != getSignatureIndex()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code attribute_length} item associated with this {@code SignatureAttribute} instance.
	 * 
	 * @return the value of the {@code attribute_length} item associated with this {@code SignatureAttribute} instance
	 */
	@Override
	public int getAttributeLength() {
		return 2;
	}
	
	/**
	 * Returns the value of the {@code signature_index} item associated with this {@code SignatureAttribute} instance.
	 * 
	 * @return the value of the {@code signature_index} item associated with this {@code SignatureAttribute} instance
	 */
	public int getSignatureIndex() {
		return this.signatureIndex;
	}
	
	/**
	 * Returns a hash code for this {@code SignatureAttribute} instance.
	 * 
	 * @return a hash code for this {@code SignatureAttribute} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getAttributeNameIndex()), Integer.valueOf(getAttributeLength()), Integer.valueOf(getSignatureIndex()));
	}
	
	/**
	 * Sets {@code signatureIndex} as the value for the {@code signature_index} item associated with this {@code SignatureAttribute} instance.
	 * <p>
	 * If {@code signatureIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param signatureIndex the value for the {@code signature_index} item associated with this {@code SignatureAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code signatureIndex} is less than {@code 1}
	 */
	public void setSignatureIndex(final int signatureIndex) {
		this.signatureIndex = ParameterArguments.requireRange(signatureIndex, 1, Integer.MAX_VALUE);
	}
	
	/**
	 * Writes this {@code SignatureAttribute} to {@code dataOutput}.
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
			dataOutput.writeShort(getAttributeNameIndex());
			dataOutput.writeInt(getAttributeLength());
			dataOutput.writeShort(getSignatureIndex());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code SignatureAttribute} instances in {@code node}.
	 * <p>
	 * All {@code SignatureAttribute} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code SignatureAttribute} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<SignatureAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), SignatureAttribute.class);
	}
	
	/**
	 * Attempts to find a {@code SignatureAttribute} instance in {@code classFile}.
	 * <p>
	 * Returns an {@code Optional} with the optional {@code SignatureAttribute} instance.
	 * <p>
	 * If {@code classFile} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param classFile the {@link ClassFile} to check in
	 * @return an {@code Optional} with the optional {@code SignatureAttribute} instance
	 * @throws NullPointerException thrown if, and only if, {@code classFile} is {@code null}
	 */
	public static Optional<SignatureAttribute> find(final ClassFile classFile) {
		return classFile.getAttributeInfos().stream().filter(attributeInfo -> attributeInfo instanceof SignatureAttribute).map(attributeInfo -> SignatureAttribute.class.cast(attributeInfo)).findFirst();
	}
	
	/**
	 * Attempts to find a {@code SignatureAttribute} instance in {@code fieldInfo}.
	 * <p>
	 * Returns an {@code Optional} with the optional {@code SignatureAttribute} instance.
	 * <p>
	 * If {@code fieldInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param fieldInfo the {@link FieldInfo} to check in
	 * @return an {@code Optional} with the optional {@code SignatureAttribute} instance
	 * @throws NullPointerException thrown if, and only if, {@code fieldInfo} is {@code null}
	 */
	public static Optional<SignatureAttribute> find(final FieldInfo fieldInfo) {
		return fieldInfo.getAttributeInfos().stream().filter(attributeInfo -> attributeInfo instanceof SignatureAttribute).map(attributeInfo -> SignatureAttribute.class.cast(attributeInfo)).findFirst();
	}
	
	/**
	 * Attempts to find a {@code SignatureAttribute} instance in {@code methodInfo}.
	 * <p>
	 * Returns an {@code Optional} with the optional {@code SignatureAttribute} instance.
	 * <p>
	 * If {@code methodInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param methodInfo the {@link MethodInfo} to check in
	 * @return an {@code Optional} with the optional {@code SignatureAttribute} instance
	 * @throws NullPointerException thrown if, and only if, {@code methodInfo} is {@code null}
	 */
	public static Optional<SignatureAttribute> find(final MethodInfo methodInfo) {
		return methodInfo.getAttributeInfos().stream().filter(attributeInfo -> attributeInfo instanceof SignatureAttribute).map(attributeInfo -> SignatureAttribute.class.cast(attributeInfo)).findFirst();
	}
}