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
import java.lang.reflect.Field;//TODO: Update Javadocs!
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
 * A {@code SignatureAttribute} denotes a Signature_attribute structure somewhere in a ClassFile structure.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class SignatureAttribute extends AttributeInfo {
	/**
	 * The name of the Signature_attribute structure.
	 */
	public static final String NAME = "Signature";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int signatureIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code SignatureAttribute} instance.
	 * <p>
	 * If either {@code attributeNameIndex} or {@code signatureIndex} are less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the attribute_name_index of the new {@code SignatureAttribute} instance
	 * @param signatureIndex the signature_index of the new {@code SignatureAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, either {@code attributeNameIndex} or {@code signatureIndex} are less than or equal to {@code 0}
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
		return new SignatureAttribute(getAttributeNameIndex(), this.signatureIndex);
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
		
		final String toString = stringBuilder.toString();
		
		return toString;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code SignatureAttribute}, and that {@code SignatureAttribute} instance is equal to this {@code SignatureAttribute} instance, {@code false}
	 * otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code SignatureAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code SignatureAttribute}, and that {@code SignatureAttribute} instance is equal to this {@code SignatureAttribute} instance, {@code false}
	 * otherwise
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
	 * Returns the attribute_length of this {@code SignatureAttribute} instance.
	 * 
	 * @return the attribute_length of this {@code SignatureAttribute} instance
	 */
	@Override
	public int getAttributeLength() {
		return 2;
	}
	
	/**
	 * Returns the signature_index of this {@code SignatureAttribute} instance.
	 * 
	 * @return the signature_index of this {@code SignatureAttribute} instance
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
	 * Sets a new signature_index for this {@code SignatureAttribute} instance.
	 * <p>
	 * If {@code signatureIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param signatureIndex the new signature_index for this {@code SignatureAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code signatureIndex} is less than or equal to {@code 0}
	 */
	public void setSignatureIndex(final int signatureIndex) {
		this.signatureIndex = ParameterArguments.requireRange(signatureIndex, 1, Integer.MAX_VALUE);
	}
	
	/**
	 * Writes this {@code SignatureAttribute} to {@code dataOutput}.
	 * <p>
	 * If {@code dataOutput} is an {@code OutputStream} (or any other type of stream), this method will not close it.
	 * <p>
	 * If {@code dataOutput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O-error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param dataOutput the {@code DataOutput} to write to
	 * @throws NullPointerException thrown if, and only if, {@code dataOutput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O-error occurs
	 */
	@Override
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeShort(getAttributeNameIndex());
			dataOutput.writeInt(getAttributeLength());
			dataOutput.writeShort(this.signatureIndex);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code SignatureAttribute}s.
	 * <p>
	 * All {@code SignatureAttribute}s are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code SignatureAttribute}s
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