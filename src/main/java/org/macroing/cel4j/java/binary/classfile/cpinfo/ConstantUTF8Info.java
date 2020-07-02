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
package org.macroing.cel4j.java.binary.classfile.cpinfo;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.java.binary.classfile.CPInfo;
import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.FieldInfo;
import org.macroing.cel4j.java.binary.classfile.MethodInfo;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.util.Document;

/**
 * A {@code ConstantUTF8Info} denotes a CONSTANT_Utf8_info structure in the constant_pool of a ClassFile structure.
 * <p>
 * The CONSTANT_Utf8_info structure was added to Java in version 1.0.2.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
/**
 * A {@code ConstantUTF8Info} represents a {@code CONSTANT_Utf8_info} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code CONSTANT_Utf8_info} structure was added to Java in version 1.0.2 and can be found in the {@code constant_pool} table item of a {@code ClassFile} structure.
 * <p>
 * The {@code CONSTANT_Utf8_info} structure has the following format:
 * <pre>
 * <code>
 * CONSTANT_Utf8_info {
 *     u1 tag;
 *     u2 length;
 *     u1[length] bytes;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ConstantUTF8Info extends CPInfo {
	/**
	 * The name of the {@code CONSTANT_Utf8_info} structure.
	 */
	public static final String NAME = "CONSTANT_Utf8";
	
	/**
	 * The tag for the {@code CONSTANT_Utf8_info} structure.
	 */
	public static final int TAG = 1;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private String stringValue;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ConstantUTF8Info} instance that is a copy of {@code constantUTF8Info}.
	 * <p>
	 * If {@code constantUTF8Info} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param constantUTF8Info the {@code ConstantUTF8Info} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code constantUTF8Info} is {@code null}
	 */
	public ConstantUTF8Info(final ConstantUTF8Info constantUTF8Info) {
		super(NAME, TAG, 2);
		
		this.stringValue = constantUTF8Info.stringValue;
	}
	
	/**
	 * Constructs a new {@code ConstantUTF8Info} instance.
	 * <p>
	 * If {@code stringValue} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param stringValue the {@code String} value for the {@code bytes} item associated with this {@code ConstantUTF8Info} instance
	 * @throws NullPointerException thrown if, and only if, {@code stringValue} is {@code null}
	 */
	public ConstantUTF8Info(final String stringValue) {
		super(NAME, TAG, 1);
		
		this.stringValue = Objects.requireNonNull(stringValue, "stringValue == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ConstantUTF8Info} instance.
	 * 
	 * @return a copy of this {@code ConstantUTF8Info} instance
	 */
	@Override
	public ConstantUTF8Info copy() {
		return new ConstantUTF8Info(this);
	}
	
	/**
	 * Returns the {@code String} value for the {@code bytes} item associated with this {@code ConstantUTF8Info} instance.
	 * 
	 * @return the {@code String} value for the {@code bytes} item associated with this {@code ConstantUTF8Info} instance
	 */
	public String getStringValue() {
		return this.stringValue;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ConstantUTF8Info} instance.
	 * 
	 * @return a {@code String} representation of this {@code ConstantUTF8Info} instance
	 */
	@Override
	public String toString() {
		return String.format("new ConstantUTF8Info(\"%s\")", getStringValue());
	}
	
	/**
	 * Compares {@code object} to this {@code ConstantUTF8Info} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ConstantUTF8Info}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ConstantUTF8Info} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ConstantUTF8Info}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ConstantUTF8Info)) {
			return false;
		} else if(!Objects.equals(getName(), ConstantUTF8Info.class.cast(object).getName())) {
			return false;
		} else if(getTag() != ConstantUTF8Info.class.cast(object).getTag()) {
			return false;
		} else if(getConstantPoolEntryCount() != ConstantUTF8Info.class.cast(object).getConstantPoolEntryCount()) {
			return false;
		} else if(!Objects.equals(getStringValue(), ConstantUTF8Info.class.cast(object).getStringValue())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code ConstantUTF8Info} instance.
	 * 
	 * @return a hash code for this {@code ConstantUTF8Info} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getTag()), Integer.valueOf(getConstantPoolEntryCount()), getStringValue());
	}
	
	/**
	 * Sets {@code stringValue} as the {@code String} value for the {@code bytes} item associated with this {@code ConstantUTF8Info} instance.
	 * <p>
	 * If {@code stringValue} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param stringValue the {@code String} value for the {@code bytes} item associated with this {@code ConstantUTF8Info} instance
	 * @throws NullPointerException thrown if, and only if, {@code stringValue} is {@code null}
	 */
	public void setStringValue(final String stringValue) {
		this.stringValue = Objects.requireNonNull(stringValue, "stringValue == null");
	}
	
	/**
	 * Writes this {@code ConstantUTF8Info} to {@code dataOutput}.
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
			dataOutput.writeUTF(getStringValue());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Writes this {@code ConstantUTF8Info} to {@code document}.
	 * <p>
	 * If {@code document} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param document the {@link Document} to write to
	 * @throws NullPointerException thrown if, and only if, {@code document} is {@code null}
	 */
	@Override
	public void write(final Document document) {
		document.linef("%s_info = {", getName());
		document.indent();
		document.linef("u1 tag = %s;", Integer.toString(getTag()));
		document.linef("String string = \"%s\";", getStringValue());
		document.outdent();
		document.linef("};");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@code ConstantUTF8Info} that is located on the index {@code attributeInfo.getAttributeNameIndex()} in the {@code constant_pool} table item of {@code classFile}.
	 * <p>
	 * If either {@code attributeInfo} or {@code classFile} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code classFile} does not contain an {@link AttributeInfo} instance that is equal to {@code attributeInfo}, or the {@link CPInfo} on the index {@code attributeInfo.getAttributeNameIndex()} is not a {@code ConstantUTF8Info} instance, an
	 * {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code attributeInfo.getAttributeNameIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param attributeInfo the {@code AttributeInfo} instance that contains the {@code attribute_name_index}
	 * @param classFile the {@code ClassFile} instance that contains an {@code AttributeInfo} instance that is equal to {@code attributeInfo}
	 * @return the {@code ConstantUTF8Info} that is located on the index {@code attributeInfo.getAttributeNameIndex()} in the {@code constant_pool} table item of {@code classFile}
	 * @throws IllegalArgumentException thrown if, and only if, {@code classFile} does not contain an {@code AttributeInfo} instance that is equal to {@code attributeInfo}, or the {@code CPInfo} on the index
	 *                                  {@code attributeInfo.getAttributeNameIndex()} is not a {@code ConstantUTF8Info} instance
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code attributeInfo.getAttributeNameIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, either {@code attributeInfo} or {@code classFile} are {@code null}
	 */
	public static ConstantUTF8Info findByAttributeNameIndex(final AttributeInfo attributeInfo, final ClassFile classFile) {
		return classFile.getCPInfo(classFile.getAttributeInfo(attributeInfo).getAttributeNameIndex(), ConstantUTF8Info.class);
	}
	
	/**
	 * Returns the {@code ConstantUTF8Info} that is located on the index {@code attributeInfo.getAttributeNameIndex()} in the c{@code constant_pool} table item of {@code classFile}.
	 * <p>
	 * If either {@code attributeInfo}, {@code classFile} or {@code fieldInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code classFile} does not contain a {@link FieldInfo} instance that is equal to {@code fieldInfo}, {@code fieldInfo} does not contain an {@link AttributeInfo} instance that is equal to {@code attributeInfo}, or the {@link CPInfo} on the
	 * index {@code attributeInfo.getAttributeNameIndex()} is not a {@code ConstantUTF8Info} instance, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code attributeInfo.getAttributeNameIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param attributeInfo the {@code AttributeInfo} instance that contains the {@code attribute_name_index}
	 * @param classFile the {@code ClassFile} instance that contains a {@code FieldInfo} instance that is equal to {@code fieldInfo}
	 * @param fieldInfo the {@code FieldInfo} instance that contains an {@code AttributeInfo} instance that is equal to {@code attributeInfo}
	 * @return the {@code ConstantUTF8Info} that is located on the index {@code attributeInfo.getAttributeNameIndex()} in the {@code constant_pool} table item of {@code classFile}
	 * @throws IllegalArgumentException thrown if, and only if, {@code classFile} does not contain a {@code FieldInfo} instance that is equal to {@code fieldInfo}, {@code fieldInfo} does not contain an {@code AttributeInfo} instance that is equal to
	 *                                  {@code attributeInfo}, or the {@code CPInfo} on the index {@code attributeInfo.getAttributeNameIndex()} is not a {@code ConstantUTF8Info} instance
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code attributeInfo.getAttributeNameIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, either {@code attributeInfo}, {@code classFile} or {@code fieldInfo} are {@code null}
	 */
	public static ConstantUTF8Info findByAttributeNameIndex(final AttributeInfo attributeInfo, final ClassFile classFile, final FieldInfo fieldInfo) {
		return classFile.getCPInfo(classFile.getFieldInfo(fieldInfo).getAttributeInfo(attributeInfo).getAttributeNameIndex(), ConstantUTF8Info.class);
	}
	
	/**
	 * Returns the {@code ConstantUTF8Info} that is located on the index {@code attributeInfo.getAttributeNameIndex()} in the {@code constant_pool} table item of {@code classFile}.
	 * <p>
	 * If either {@code attributeInfo}, {@code classFile} or {@code methodInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code classFile} does not contain a {@link MethodInfo} instance that is equal to {@code methodInfo}, {@code methodInfo} does not contain an {@link AttributeInfo} instance that is equal to {@code attributeInfo}, or the {@link CPInfo} on the
	 * index {@code attributeInfo.getAttributeNameIndex()} is not a {@code ConstantUTF8Info} instance, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code attributeInfo.getAttributeNameIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param attributeInfo the {@code AttributeInfo} instance that contains the {@code attribute_name_index}
	 * @param classFile the {@code ClassFile} instance that contains a {@code MethodInfo} instance that is equal to {@code methodInfo}
	 * @param methodInfo the {@code MethodInfo} instance that contains an {@code AttributeInfo} instance that is equal to {@code attributeInfo}
	 * @return the {@code ConstantUTF8Info} that is located on the index {@code attributeInfo.getAttributeNameIndex()} in the {@code constant_pool} table item of {@code classFile}
	 * @throws IllegalArgumentException thrown if, and only if, {@code classFile} does not contain a {@code MethodInfo} instance that is equal to {@code methodInfo}, {@code methodInfo} does not contain an {@code AttributeInfo} instance that is equal
	 *                                  to {@code attributeInfo}, or the {@code CPInfo} on the index {@code attributeInfo.getAttributeNameIndex()} is not a {@code ConstantUTF8Info} instance
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code attributeInfo.getAttributeNameIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, either {@code attributeInfo}, {@code classFile} or {@code methodInfo} are {@code null}
	 */
	public static ConstantUTF8Info findByAttributeNameIndex(final AttributeInfo attributeInfo, final ClassFile classFile, final MethodInfo methodInfo) {
		return classFile.getCPInfo(classFile.getMethodInfo(methodInfo).getAttributeInfo(attributeInfo).getAttributeNameIndex(), ConstantUTF8Info.class);
	}
	
	/**
	 * Returns the {@code ConstantUTF8Info} that is located on the index {@code constantNameAndTypeInfo.getDescriptorIndex()} in the {@code constant_pool} table item of {@code classFile}.
	 * <p>
	 * If either {@code classFile} or {@code constantNameAndTypeInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code classFile} does not contain a {@link ConstantNameAndTypeInfo} instance that is equal to {@code constantNameAndTypeInfo}, or the {@link CPInfo} on the index {@code constantNameAndTypeInfo.getDescriptorIndex()} is not a
	 * {@code ConstantUTF8Info} instance, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code constantNameAndTypeInfo.getDescriptorIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param classFile the {@code ClassFile} instance that contains a {@code ConstantNameAndTypeInfo} instance that is equal to {@code constantNameAndTypeInfo}
	 * @param constantNameAndTypeInfo the {@code ConstantNameAndTypeInfo} instance that contains the {@code descriptor_index}
	 * @return the {@code ConstantUTF8Info} that is located on the index {@code constantNameAndTypeInfo.getDescriptorIndex()} in the {@code constant_pool} table item of {@code classFile}
	 * @throws IllegalArgumentException thrown if, and only if, {@code classFile} does not contain a {@code ConstantNameAndTypeInfo} instance that is equal to {@code constantNameAndTypeInfo}, or the {@code CPInfo} on the index
	 *                                  {@code constantNameAndTypeInfo.getDescriptorIndex()} is not a {@code ConstantUTF8Info} instance
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code constantNameAndTypeInfo.getDescriptorIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code constantNameAndTypeInfo} are {@code null}
	 */
	public static ConstantUTF8Info findByDescriptorIndex(final ClassFile classFile, final ConstantNameAndTypeInfo constantNameAndTypeInfo) {
		return classFile.getCPInfo(classFile.getCPInfo(constantNameAndTypeInfo, ConstantNameAndTypeInfo.class).getDescriptorIndex(), ConstantUTF8Info.class);
	}
	
	/**
	 * Returns the {@code ConstantUTF8Info} that is located on the index {@code fieldInfo.getDescriptorIndex()} in the {@code constant_pool} table item of {@code classFile}.
	 * <p>
	 * If either {@code classFile} or {@code fieldInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code classFile} does not contain a {@link FieldInfo} instance that is equal to {@code fieldInfo}, or the {@link CPInfo} on the index {@code fieldInfo.getDescriptorIndex()} is not a {@code ConstantUTF8Info} instance, an
	 * {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code fieldInfo.getDescriptorIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param classFile the {@code ClassFile} instance that contains a {@code FieldInfo} instance that is equal to {@code fieldInfo}
	 * @param fieldInfo the {@code FieldInfo} instance that contains the {@code descriptor_index}
	 * @return the {@code ConstantUTF8Info} that is located on the index {@code fieldInfo.getDescriptorIndex()} in the {@code constant_pool} table item of {@code classFile}
	 * @throws IllegalArgumentException thrown if, and only if, {@code classFile} does not contain a {@code FieldInfo} instance that is equal to {@code fieldInfo}, or the {@code CPInfo} on the index {@code fieldInfo.getDescriptorIndex()} is not a
	 *                                  {@code ConstantUTF8Info} instance
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code fieldInfo.getDescriptorIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code fieldInfo} are {@code null}
	 */
	public static ConstantUTF8Info findByDescriptorIndex(final ClassFile classFile, final FieldInfo fieldInfo) {
		return classFile.getCPInfo(classFile.getFieldInfo(fieldInfo).getDescriptorIndex(), ConstantUTF8Info.class);
	}
	
	/**
	 * Returns the {@code ConstantUTF8Info} that is located on the index {@code methodInfo.getDescriptorIndex()} in the {@code constant_pool} table item of {@code classFile}.
	 * <p>
	 * If either {@code classFile} or {@code methodInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code classFile} does not contain a {@link MethodInfo} instance that is equal to {@code methodInfo}, or the {@link CPInfo} on the index {@code methodInfo.getDescriptorIndex()} is not a {@code ConstantUTF8Info} instance, an
	 * {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code methodInfo.getDescriptorIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param classFile the {@code ClassFile} instance that contains a {@code MethodInfo} instance that is equal to {@code methodInfo}
	 * @param methodInfo the {@code MethodInfo} instance that contains the {@code descriptor_index}
	 * @return the {@code ConstantUTF8Info} that is located on the index {@code methodInfo.getDescriptorIndex()} in the {@code constant_pool} table item of {@code classFile}
	 * @throws IllegalArgumentException thrown if, and only if, {@code classFile} does not contain a {@code MethodInfo} instance that is equal to {@code methodInfo}, or the {@code CPInfo} on the index {@code methodInfo.getDescriptorIndex()} is not a
	 *                                  {@code ConstantUTF8Info} instance
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code methodInfo.getDescriptorIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code methodInfo} are {@code null}
	 */
	public static ConstantUTF8Info findByDescriptorIndex(final ClassFile classFile, final MethodInfo methodInfo) {
		return classFile.getCPInfo(classFile.getMethodInfo(methodInfo).getDescriptorIndex(), ConstantUTF8Info.class);
	}
	
	/**
	 * Returns {@code ConstantUTF8Info.findByDescriptorIndex(classFile, ConstantNameAndTypeInfo.findByNameAndTypeIndex(classFile, constantFieldRefInfo))}.
	 * <p>
	 * If either {@code classFile} or {@code constantFieldRefInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If either {@link ConstantNameAndTypeInfo#findByNameAndTypeIndex(ClassFile, ConstantFieldRefInfo)} or {@link ConstantUTF8Info#findByDescriptorIndex(ClassFile, ConstantNameAndTypeInfo)} throws an {@code IllegalArgumentException}, it will be
	 * thrown.
	 * <p>
	 * If either {@code ConstantNameAndTypeInfo.findByNameAndTypeIndex(ClassFile, ConstantFieldRefInfo)} or {@code ConstantUTF8Info.findByDescriptorIndex(ClassFile, ConstantNameAndTypeInfo)} throws an {@code IndexOutOfBoundsException}, it will be
	 * thrown.
	 * 
	 * @param classFile the {@code ClassFile} instance that contains a {@code ConstantFieldRefInfo} instance that is equal to {@code constantFieldRefInfo}
	 * @param constantFieldRefInfo the {@code ConstantFieldRefInfo} instance that contains the {@code name_and_type_index}
	 * @return {@code ConstantUTF8Info.findByDescriptorIndex(classFile, ConstantNameAndTypeInfo.findByNameAndTypeIndex(classFile, constantFieldRefInfo))}
	 * @throws IllegalArgumentException thrown if, and only if, either {@code ConstantNameAndTypeInfo.findByNameAndTypeIndex(ClassFile, ConstantFieldRefInfo)} or {@code ConstantUTF8Info.findByDescriptorIndex(ClassFile, ConstantNameAndTypeInfo)} throws
	 *                                  an {@code IllegalArgumentException}
	 * @throws IndexOutOfBoundsException thrown if, and only if, either {@code ConstantNameAndTypeInfo.findByNameAndTypeIndex(ClassFile, ConstantFieldRefInfo)} or {@code ConstantUTF8Info.findByDescriptorIndex(ClassFile, ConstantNameAndTypeInfo)} throws
	 *                                   an {@code IndexOutOfBoundsException}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code constantFieldRefInfo} are {@code null}
	 */
	public static ConstantUTF8Info findByDescriptorIndexByNameAndTypeIndex(final ClassFile classFile, final ConstantFieldRefInfo constantFieldRefInfo) {
		return findByDescriptorIndex(classFile, ConstantNameAndTypeInfo.findByNameAndTypeIndex(classFile, constantFieldRefInfo));
	}
	
	/**
	 * Returns {@code ConstantUTF8Info.findByDescriptorIndex(classFile, ConstantNameAndTypeInfo.findByNameAndTypeIndex(classFile, constantInterfaceMethodRefInfo))}.
	 * <p>
	 * If either {@code classFile} or {@code constantInterfaceMethodRefInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If either {@link ConstantNameAndTypeInfo#findByNameAndTypeIndex(ClassFile, ConstantInterfaceMethodRefInfo)} or {@link ConstantUTF8Info#findByDescriptorIndex(ClassFile, ConstantNameAndTypeInfo)} throws an {@code IllegalArgumentException}, it will
	 * be thrown.
	 * <p>
	 * If either {@code ConstantNameAndTypeInfo.findByNameAndTypeIndex(ClassFile, ConstantInterfaceMethodRefInfo)} or {@code ConstantUTF8Info.findByDescriptorIndex(ClassFile, ConstantNameAndTypeInfo)} throws an {@code IndexOutOfBoundsException}, it
	 * will be thrown.
	 * 
	 * @param classFile the {@code ClassFile} instance that contains a {@code ConstantInterfaceMethodRefInfo} instance that is equal to {@code constantInterfaceMethodRefInfo}
	 * @param constantInterfaceMethodRefInfo the {@code ConstantInterfaceMethodRefInfo} instance that contains the {@code name_and_type_index}
	 * @return {@code ConstantUTF8Info.findByDescriptorIndex(classFile, ConstantNameAndTypeInfo.findByNameAndTypeIndex(classFile, constantInterfaceMethodRefInfo))}
	 * @throws IllegalArgumentException thrown if, and only if, either {@code ConstantNameAndTypeInfo.findByNameAndTypeIndex(ClassFile, ConstantInterfaceMethodRefInfo)} or
	 *                                  {@code ConstantUTF8Info.findByDescriptorIndex(ClassFile, ConstantNameAndTypeInfo)} throws an {@code IllegalArgumentException}
	 * @throws IndexOutOfBoundsException thrown if, and only if, either {@code ConstantNameAndTypeInfo.findByNameAndTypeIndex(ClassFile, ConstantInterfaceMethodRefInfo)} or
	 *                                   {@code ConstantUTF8Info.findByDescriptorIndex(ClassFile, ConstantNameAndTypeInfo)} throws an {@code IndexOutOfBoundsException}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code constantInterfaceMethodRefInfo} are {@code null}
	 */
	public static ConstantUTF8Info findByDescriptorIndexByNameAndTypeIndex(final ClassFile classFile, final ConstantInterfaceMethodRefInfo constantInterfaceMethodRefInfo) {
		return findByDescriptorIndex(classFile, ConstantNameAndTypeInfo.findByNameAndTypeIndex(classFile, constantInterfaceMethodRefInfo));
	}
	
	/**
	 * Returns {@code ConstantUTF8Info.findByDescriptorIndex(classFile, ConstantNameAndTypeInfo.findByNameAndTypeIndex(classFile, constantMethodRefInfo))}.
	 * <p>
	 * If either {@code classFile} or {@code constantMethodRefInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If either {@link ConstantNameAndTypeInfo#findByNameAndTypeIndex(ClassFile, ConstantMethodRefInfo)} or {@link ConstantUTF8Info#findByDescriptorIndex(ClassFile, ConstantNameAndTypeInfo)} throws an {@code IllegalArgumentException}, it will be
	 * thrown.
	 * <p>
	 * If either {@code ConstantNameAndTypeInfo.findByNameAndTypeIndex(ClassFile, ConstantMethodRefInfo)} or {@code ConstantUTF8Info.findByDescriptorIndex(ClassFile, ConstantNameAndTypeInfo)} throws an {@code IndexOutOfBoundsException}, it will be
	 * thrown.
	 * 
	 * @param classFile the {@code ClassFile} instance that contains a {@code ConstantMethodRefInfo} instance that is equal to {@code constantMethodRefInfo}
	 * @param constantMethodRefInfo the {@code ConstantMethodRefInfo} instance that contains the {@code name_and_type_index}
	 * @return {@code ConstantUTF8Info.findByDescriptorIndex(classFile, ConstantNameAndTypeInfo.findByNameAndTypeIndex(classFile, constantMethodRefInfo))}
	 * @throws IllegalArgumentException thrown if, and only if, either {@code ConstantNameAndTypeInfo.findByNameAndTypeIndex(ClassFile, ConstantMethodRefInfo)} or {@code ConstantUTF8Info.findByDescriptorIndex(ClassFile, ConstantNameAndTypeInfo)} throws
	 *                                  an {@code IllegalArgumentException}
	 * @throws IndexOutOfBoundsException thrown if, and only if, either {@code ConstantNameAndTypeInfo.findByNameAndTypeIndex(ClassFile, ConstantMethodRefInfo)} or {@code ConstantUTF8Info.findByDescriptorIndex(ClassFile, ConstantNameAndTypeInfo)}
	 *                                   throws an {@code IndexOutOfBoundsException}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code constantMethodRefInfo} are {@code null}
	 */
	public static ConstantUTF8Info findByDescriptorIndexByNameAndTypeIndex(final ClassFile classFile, final ConstantMethodRefInfo constantMethodRefInfo) {
		return findByDescriptorIndex(classFile, ConstantNameAndTypeInfo.findByNameAndTypeIndex(classFile, constantMethodRefInfo));
	}
	
	/**
	 * Returns the {@code ConstantUTF8Info} that is located on the index {@code constantClassInfo.getNameIndex()} in the {@code constant_pool} table item of {@code classFile}.
	 * <p>
	 * If either {@code classFile} or {@code constantClassInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code classFile} does not contain a {@code ConstantClassInfo} instance that is equal to {@code constantClassInfo}, or the {@link CPInfo} on the index {@code constantClassInfo.getNameIndex()} is not a {@code ConstantUTF8Info} instance, an
	 * {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code constantClassInfo.getNameIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param classFile the {@code ClassFile} instance that contains a {@code ConstantClassInfo} instance that is equal to {@code constantClassInfo}
	 * @param constantClassInfo the {@code ConstantClassInfo} instance that contains the {@code name_index}
	 * @return the {@code ConstantUTF8Info} that is located on the index {@code constantClassInfo.getNameIndex()} in the {@code constant_pool} table item of {@code classFile}
	 * @throws IllegalArgumentException thrown if, and only if, {@code classFile} does not contain a {@code ConstantClassInfo} instance that is equal to {@code constantClassInfo}, or the {@code CPInfo} on the index
	 *                                  {@code constantClassInfo.getNameIndex()} is not a {@code ConstantUTF8Info} instance
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code constantClassInfo.getNameIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code constantClassInfo} are {@code null}
	 */
	public static ConstantUTF8Info findByNameIndex(final ClassFile classFile, final ConstantClassInfo constantClassInfo) {
		return classFile.getCPInfo(classFile.getCPInfo(constantClassInfo, ConstantClassInfo.class).getNameIndex(), ConstantUTF8Info.class);
	}
	
	/**
	 * Returns the {@code ConstantUTF8Info} that is located on the index {@code constantNameAndTypeInfo.getNameIndex()} in the {@code constant_pool} table item of {@code classFile}.
	 * <p>
	 * If either {@code classFile} or {@code constantNameAndTypeInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code classFile} does not contain a {@code ConstantNameAndTypeInfo} instance that is equal to {@code constantNameAndTypeInfo}, or the {@link CPInfo} on the index {@code constantNameAndTypeInfo.getNameIndex()} is not a
	 * {@code ConstantUTF8Info} instance, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code constantNameAndTypeInfo.getNameIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param classFile the {@code ClassFile} instance that contains a {@code ConstantNameAndTypeInfo} instance that is equal to {@code constantNameAndTypeInfo}
	 * @param constantNameAndTypeInfo the {@code ConstantNameAndTypeInfo} instance that contains the {@code name_index}
	 * @return the {@code ConstantUTF8Info} that is located on the index {@code ConstantNameAndTypeInfo.getNameIndex()} in the {@code constant_pool} table item of {@code classFile}
	 * @throws IllegalArgumentException thrown if, and only if, {@code classFile} does not contain a {@code ConstantNameAndTypeInfo} instance that is equal to {@code constantNameAndTypeInfo}, or the {@code CPInfo} on the index
	 *                                  {@code constantNameAndTypeInfo.getNameIndex()} is not a {@code ConstantUTF8Info} instance
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code constantNameAndTypeInfo.getNameIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code constantNameAndTypeInfo} are {@code null}
	 */
	public static ConstantUTF8Info findByNameIndex(final ClassFile classFile, final ConstantNameAndTypeInfo constantNameAndTypeInfo) {
		return classFile.getCPInfo(classFile.getCPInfo(constantNameAndTypeInfo, ConstantNameAndTypeInfo.class).getNameIndex(), ConstantUTF8Info.class);
	}
	
	/**
	 * Returns the {@code ConstantUTF8Info} that is located on the index {@code fieldInfo.getNameIndex()} in the {@code constant_pool} table item of {@code classFile}.
	 * <p>
	 * If either {@code classFile} or {@code fieldInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code classFile} does not contain a {@link FieldInfo} instance that is equal to {@code fieldInfo}, or the {@link CPInfo} on the index {@code fieldInfo.getNameIndex()} is not a {@code ConstantUTF8Info} instance, an
	 * {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code fieldInfo.getNameIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param classFile the {@code ClassFile} instance that contains a {@code FieldInfo} instance that is equal to {@code fieldInfo}
	 * @param fieldInfo the {@code FieldInfo} instance that contains the {@code name_index}
	 * @return the {@code ConstantUTF8Info} that is located on the index {@code fieldInfo.getNameIndex()} in the {@code constant_pool} table item of {@code classFile}
	 * @throws IllegalArgumentException thrown if, and only if, {@code classFile} does not contain a {@code FieldInfo} instance that is equal to {@code fieldInfo}, or the {@code CPInfo} on the index {@code fieldInfo.getNameIndex()} is not a
	 *                                  {@code ConstantUTF8Info} instance
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code fieldInfo.getNameIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code fieldInfo} are {@code null}
	 */
	public static ConstantUTF8Info findByNameIndex(final ClassFile classFile, final FieldInfo fieldInfo) {
		return classFile.getCPInfo(classFile.getFieldInfo(fieldInfo).getNameIndex(), ConstantUTF8Info.class);
	}
	
	/**
	 * Returns the {@code ConstantUTF8Info} that is located on the index {@code methodInfo.getNameIndex()} in the {@code constant_pool} table item of {@code classFile}.
	 * <p>
	 * If either {@code classFile} or {@code methodInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code classFile} does not contain a {@link MethodInfo} instance that is equal to {@code methodInfo}, or the {@link CPInfo} on the index {@code methodInfo.getNameIndex()} is not a {@code ConstantUTF8Info} instance, an
	 * {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code methodInfo.getNameIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param classFile the {@code ClassFile} instance that contains a {@code MethodInfo} instance that is equal to {@code methodInfo}
	 * @param methodInfo the {@code MethodInfo} instance that contains the {@code name_index}
	 * @return the {@code ConstantUTF8Info} that is located on the index {@code methodInfo.getNameIndex()} in the {@code constant_pool} table item of {@code classFile}
	 * @throws IllegalArgumentException thrown if, and only if, {@code classFile} does not contain a {@code MethodInfo} instance that is equal to {@code methodInfo}, or the {@code CPInfo} on the index {@code methodInfo.getNameIndex()} is not a
	 *                                  {@code ConstantUTF8Info} instance
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code methodInfo.getNameIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code methodInfo} are {@code null}
	 */
	public static ConstantUTF8Info findByNameIndex(final ClassFile classFile, final MethodInfo methodInfo) {
		return classFile.getCPInfo(classFile.getMethodInfo(methodInfo).getNameIndex(), ConstantUTF8Info.class);
	}
	
	/**
	 * Returns {@code ConstantUTF8Info.findByNameIndex(classFile, ConstantClassInfo.findByClassIndex(classFile, constantFieldRefInfo))}.
	 * <p>
	 * If either {@code classFile} or {@code constantFieldRefInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If either {@link ConstantClassInfo#findByClassIndex(ClassFile, ConstantFieldRefInfo)} or {@link ConstantUTF8Info#findByNameIndex(ClassFile, ConstantClassInfo)} throws an {@code IllegalArgumentException}, it will be thrown.
	 * <p>
	 * If either {@code ConstantClassInfo.findByClassIndex(ClassFile, ConstantFieldRefInfo)} or {@code ConstantUTF8Info.findByNameIndex(ClassFile, ConstantClassInfo)} throws an {@code IndexOutOfBoundsException}, it will be thrown.
	 * 
	 * @param classFile the {@code ClassFile} instance that contains a {@code ConstantFieldRefInfo} instance that is equal to {@code constantFieldRefInfo}
	 * @param constantFieldRefInfo the {@code ConstantFieldRefInfo} instance that contains the {@code class_index}
	 * @return {@code ConstantUTF8Info.findByNameIndex(classFile, ConstantClassInfo.findByClassIndex(classFile, constantFieldRefInfo))}
	 * @throws IllegalArgumentException thrown if, and only if, either {@code ConstantClassInfo.findByClassIndex(ClassFile, ConstantFieldRefInfo)} or {@code ConstantUTF8Info.findByNameIndex(ClassFile, ConstantClassInfo)} throws an
	 *                                  {@code IllegalArgumentException}
	 * @throws IndexOutOfBoundsException thrown if, and only if, either {@code ConstantClassInfo.findByClassIndex(ClassFile, ConstantFieldRefInfo)} or {@code ConstantUTF8Info.findByNameIndex(ClassFile, ConstantClassInfo)} throws an
	 *                                   {@code IndexOutOfBoundsException}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code constantFieldRefInfo} are {@code null}
	 */
	public static ConstantUTF8Info findByNameIndexByClassIndex(final ClassFile classFile, final ConstantFieldRefInfo constantFieldRefInfo) {
		return findByNameIndex(classFile, ConstantClassInfo.findByClassIndex(classFile, constantFieldRefInfo));
	}
	
	/**
	 * Returns {@code ConstantUTF8Info.findByNameIndex(classFile, ConstantClassInfo.findByClassIndex(classFile, constantInterfaceMethodRefInfo))}.
	 * <p>
	 * If either {@code classFile} or {@code constantInterfaceMethodRefInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If either {@link ConstantClassInfo#findByClassIndex(ClassFile, ConstantInterfaceMethodRefInfo)} or {@link ConstantUTF8Info#findByNameIndex(ClassFile, ConstantClassInfo)} throws an {@code IllegalArgumentException}, it will be thrown.
	 * <p>
	 * If either {@code ConstantClassInfo.findByClassIndex(ClassFile, ConstantInterfaceMethodRefInfo)} or {@code ConstantUTF8Info.findByNameIndex(ClassFile, ConstantClassInfo)} throws an {@code IndexOutOfBoundsException}, it will be thrown.
	 * 
	 * @param classFile the {@code ClassFile} instance that contains a {@code ConstantInterfaceMethodRefInfo} instance that is equal to {@code constantInterfaceMethodRefInfo}
	 * @param constantInterfaceMethodRefInfo the {@code ConstantInterfaceMethodRefInfo} instance that contains the {@code class_index}
	 * @return {@code ConstantUTF8Info.findByNameIndex(classFile, ConstantClassInfo.findByClassIndex(classFile, constantInterfaceMethodRefInfo))}
	 * @throws IllegalArgumentException thrown if, and only if, either {@code ConstantClassInfo.findByClassIndex(ClassFile, ConstantInterfaceMethodRefInfo)} or {@code ConstantUTF8Info.findByNameIndex(ClassFile, ConstantClassInfo)} throws an
	 *                                  {@code IllegalArgumentException}
	 * @throws IndexOutOfBoundsException thrown if, and only if, either {@code ConstantClassInfo.findByClassIndex(ClassFile, ConstantInterfaceMethodRefInfo)} or {@code ConstantUTF8Info.findByNameIndex(ClassFile, ConstantClassInfo)} throws an
	 *                                   {@code IndexOutOfBoundsException}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code constantInterfaceMethodRefInfo} are {@code null}
	 */
	public static ConstantUTF8Info findByNameIndexByClassIndex(final ClassFile classFile, final ConstantInterfaceMethodRefInfo constantInterfaceMethodRefInfo) {
		return findByNameIndex(classFile, ConstantClassInfo.findByClassIndex(classFile, constantInterfaceMethodRefInfo));
	}
	
	/**
	 * Returns {@code ConstantUTF8Info.findByNameIndex(classFile, ConstantClassInfo.findByClassIndex(classFile, constantMethodRefInfo))}.
	 * <p>
	 * If either {@code classFile} or {@code constantMethodRefInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If either {@link ConstantClassInfo#findByClassIndex(ClassFile, ConstantMethodRefInfo)} or {@link ConstantUTF8Info#findByNameIndex(ClassFile, ConstantClassInfo)} throws an {@code IllegalArgumentException}, it will be thrown.
	 * <p>
	 * If either {@code ConstantClassInfo.findByClassIndex(ClassFile, ConstantMethodRefInfo)} or {@code ConstantUTF8Info.findByNameIndex(ClassFile, ConstantClassInfo)} throws an {@code IndexOutOfBoundsException}, it will be thrown.
	 * 
	 * @param classFile the {@code ClassFile} instance that contains a {@code ConstantMethodRefInfo} instance that is equal to {@code constantMethodRefInfo}
	 * @param constantMethodRefInfo the {@code ConstantMethodRefInfo} instance that contains the {@code class_index}
	 * @return {@code ConstantUTF8Info.findByNameIndex(classFile, ConstantClassInfo.findByClassIndex(classFile, constantMethodRefInfo))}
	 * @throws IllegalArgumentException thrown if, and only if, either {@code ConstantClassInfo.findByClassIndex(ClassFile, ConstantMethodRefInfo)} or {@code ConstantUTF8Info.findByNameIndex(ClassFile, ConstantClassInfo)} throws an
	 *                                  {@code IllegalArgumentException}
	 * @throws IndexOutOfBoundsException thrown if, and only if, either {@code ConstantClassInfo.findByClassIndex(ClassFile, ConstantMethodRefInfo)} or {@code ConstantUTF8Info.findByNameIndex(ClassFile, ConstantClassInfo)} throws an
	 *                                   {@code IndexOutOfBoundsException}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code constantMethodRefInfo} are {@code null}
	 */
	public static ConstantUTF8Info findByNameIndexByClassIndex(final ClassFile classFile, final ConstantMethodRefInfo constantMethodRefInfo) {
		return findByNameIndex(classFile, ConstantClassInfo.findByClassIndex(classFile, constantMethodRefInfo));
	}
	
	/**
	 * Returns {@code ConstantUTF8Info.findByNameIndex(classFile, ConstantNameAndTypeInfo.findByNameAndTypeIndex(classFile, constantFieldRefInfo))}.
	 * <p>
	 * If either {@code classFile} or {@code constantFieldRefInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If either {@link ConstantNameAndTypeInfo#findByNameAndTypeIndex(ClassFile, ConstantFieldRefInfo)} or {@link ConstantUTF8Info#findByNameIndex(ClassFile, ConstantNameAndTypeInfo)} throws an {@code IllegalArgumentException}, it will be thrown.
	 * <p>
	 * If either {@code ConstantNameAndTypeInfo.findByNameAndTypeIndex(ClassFile, ConstantFieldRefInfo)} or {@code ConstantUTF8Info.findByNameIndex(ClassFile, ConstantNameAndTypeInfo)} throws an {@code IndexOutOfBoundsException}, it will be thrown.
	 * 
	 * @param classFile the {@code ClassFile} instance that contains a {@code ConstantFieldRefInfo} instance that is equal to {@code constantFieldRefInfo}
	 * @param constantFieldRefInfo the {@code ConstantFieldRefInfo} instance that contains the {@code name_and_type_index}
	 * @return {@code ConstantUTF8Info.findByNameIndex(classFile, ConstantNameAndTypeInfo.findByNameAndTypeIndex(classFile, constantFieldRefInfo))}
	 * @throws IllegalArgumentException thrown if, and only if, either {@code ConstantNameAndTypeInfo.findByNameAndTypeIndex(ClassFile, ConstantFieldRefInfo)} or {@code ConstantUTF8Info.findByNameIndex(ClassFile, ConstantNameAndTypeInfo)} throws an
	 *                                  {@code IllegalArgumentException}
	 * @throws IndexOutOfBoundsException thrown if, and only if, either {@code ConstantNameAndTypeInfo.findByNameAndTypeIndex(ClassFile, ConstantFieldRefInfo)} or {@code ConstantUTF8Info.findByNameIndex(ClassFile, ConstantNameAndTypeInfo)} throws an
	 *                                   {@code IndexOutOfBoundsException}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code constantFieldRefInfo} are {@code null}
	 */
	public static ConstantUTF8Info findByNameIndexByNameAndTypeIndex(final ClassFile classFile, final ConstantFieldRefInfo constantFieldRefInfo) {
		return findByNameIndex(classFile, ConstantNameAndTypeInfo.findByNameAndTypeIndex(classFile, constantFieldRefInfo));
	}
	
	/**
	 * Returns {@code ConstantUTF8Info.findByNameIndex(classFile, ConstantNameAndTypeInfo.findByNameAndTypeIndex(classFile, constantInterfaceMethodRefInfo))}.
	 * <p>
	 * If either {@code classFile} or {@code constantInterfaceMethodRefInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If either {@link ConstantNameAndTypeInfo#findByNameAndTypeIndex(ClassFile, ConstantInterfaceMethodRefInfo)} or {@link ConstantUTF8Info#findByNameIndex(ClassFile, ConstantNameAndTypeInfo)} throws an {@code IllegalArgumentException}, it will be
	 * thrown.
	 * <p>
	 * If either {@code ConstantNameAndTypeInfo.findByNameAndTypeIndex(ClassFile, ConstantInterfaceMethodRefInfo)} or {@code ConstantUTF8Info.findByNameIndex(ClassFile, ConstantNameAndTypeInfo)} throws an {@code IndexOutOfBoundsException}, it will be
	 * thrown.
	 * 
	 * @param classFile the {@code ClassFile} instance that contains a {@code ConstantInterfaceMethodRefInfo} instance that is equal to {@code constantInterfaceMethodRefInfo}
	 * @param constantInterfaceMethodRefInfo the {@code ConstantInterfaceMethodRefInfo} instance that contains the {@code name_and_type_index}
	 * @return {@code ConstantUTF8Info.findByNameIndex(classFile, ConstantNameAndTypeInfo.findByNameAndTypeIndex(classFile, constantInterfaceMethodRefInfo))}
	 * @throws IllegalArgumentException thrown if, and only if, either {@code ConstantNameAndTypeInfo.findByNameAndTypeIndex(ClassFile, ConstantInterfaceMethodRefInfo)} or {@code ConstantUTF8Info.findByNameIndex(ClassFile, ConstantNameAndTypeInfo)}
	 *                                  throws an {@code IllegalArgumentException}
	 * @throws IndexOutOfBoundsException thrown if, and only if, either {@code ConstantNameAndTypeInfo.findByNameAndTypeIndex(ClassFile, ConstantInterfaceMethodRefInfo)} or {@code ConstantUTF8Info.findByNameIndex(ClassFile, ConstantNameAndTypeInfo)}
	 *                                   throws an {@code IndexOutOfBoundsException}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code constantInterfaceMethodRefInfo} are {@code null}
	 */
	public static ConstantUTF8Info findByNameIndexByNameAndTypeIndex(final ClassFile classFile, final ConstantInterfaceMethodRefInfo constantInterfaceMethodRefInfo) {
		return findByNameIndex(classFile, ConstantNameAndTypeInfo.findByNameAndTypeIndex(classFile, constantInterfaceMethodRefInfo));
	}
	
	/**
	 * Returns {@code ConstantUTF8Info.findByNameIndex(classFile, ConstantNameAndTypeInfo.findByNameAndTypeIndex(classFile, constantMethodRefInfo))}.
	 * <p>
	 * If either {@code classFile} or {@code constantMethodRefInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If either {@link ConstantNameAndTypeInfo#findByNameAndTypeIndex(ClassFile, ConstantMethodRefInfo)} or {@link ConstantUTF8Info#findByNameIndex(ClassFile, ConstantNameAndTypeInfo)} throws an {@code IllegalArgumentException}, it will be thrown.
	 * <p>
	 * If either {@code ConstantNameAndTypeInfo.findByNameAndTypeIndex(ClassFile, ConstantMethodRefInfo)} or {@code ConstantUTF8Info.findByNameIndex(ClassFile, ConstantNameAndTypeInfo)} throws an {@code IndexOutOfBoundsException}, it will be thrown.
	 * 
	 * @param classFile the {@code ClassFile} instance that contains a {@code ConstantMethodRefInfo} instance that is equal to {@code constantMethodRefInfo}
	 * @param constantMethodRefInfo the {@code ConstantMethodRefInfo} instance that contains the {@code name_and_type_index}
	 * @return {@code ConstantUTF8Info.findByNameIndex(classFile, ConstantNameAndTypeInfo.findByNameAndTypeIndex(classFile, constantMethodRefInfo))}
	 * @throws IllegalArgumentException thrown if, and only if, either {@code ConstantNameAndTypeInfo.findByNameAndTypeIndex(ClassFile, ConstantMethodRefInfo)} or {@code ConstantUTF8Info.findByNameIndex(ClassFile, ConstantNameAndTypeInfo)} throws an
	 *                                  {@code IllegalArgumentException}
	 * @throws IndexOutOfBoundsException thrown if, and only if, either {@code ConstantNameAndTypeInfo.findByNameAndTypeIndex(ClassFile, ConstantMethodRefInfo)} or {@code ConstantUTF8Info.findByNameIndex(ClassFile, ConstantNameAndTypeInfo)} throws an
	 *                                   {@code IndexOutOfBoundsException}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code constantMethodRefInfo} are {@code null}
	 */
	public static ConstantUTF8Info findByNameIndexByNameAndTypeIndex(final ClassFile classFile, final ConstantMethodRefInfo constantMethodRefInfo) {
		return findByNameIndex(classFile, ConstantNameAndTypeInfo.findByNameAndTypeIndex(classFile, constantMethodRefInfo));
	}
	
	/**
	 * Returns the {@code ConstantUTF8Info} that is located on the index {@code constantStringInfo.getStringIndex()} in the {@code constant_pool} table item of {@code classFile}.
	 * <p>
	 * If either {@code classFile} or {@code constantStringInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code classFile} does not contain a {@link ConstantStringInfo} instance that is equal to {@code constantStringInfo}, or the {@link CPInfo} on the index {@code constantStringInfo.getStringIndex()} is not a {@code ConstantUTF8Info} instance,
	 * an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code constantStringInfo.getStringIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param classFile the {@code ClassFile} instance that contains a {@code ConstantStringInfo} instance that is equal to {@code constantStringInfo}
	 * @param constantStringInfo the {@code ConstantStringInfo} instance that contains the {@code string_index}
	 * @return the {@code ConstantUTF8Info} that is located on the index {@code constantStringInfo.getStringIndex()} in the {@code constant_pool} table item of {@code classFile}
	 * @throws IllegalArgumentException thrown if, and only if, {@code classFile} does not contain a {@code ConstantStringInfo} instance that is equal to {@code constantStringInfo}, or the {@code CPInfo} on the index
	 *                                  {@code constantStringInfo.getStringIndex()} is not a {@code ConstantUTF8Info} instance
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code constantStringInfo.getStringIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code constantStringInfo} are {@code null}
	 */
	public static ConstantUTF8Info findByStringIndex(final ClassFile classFile, final ConstantStringInfo constantStringInfo) {
		return classFile.getCPInfo(classFile.getCPInfo(constantStringInfo, ConstantStringInfo.class).getStringIndex(), ConstantUTF8Info.class);
	}
	
	/**
	 * Returns a {@code List} with all {@code ConstantUTF8Info} instances in {@code node}.
	 * <p>
	 * All {@code ConstantUTF8Info} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code ConstantUTF8Info} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<ConstantUTF8Info> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), ConstantUTF8Info.class);
	}
	
	/**
	 * Returns a {@link NodeFilter} that accepts {@link Node} instances that are instances of {@code ConstantUTF8Info}.
	 * <p>
	 * The {@code NodeFilter} returned by this method will throw a {@code NullPointerException} if, and only if, the {@code Node} to accept or reject is {@code null}. It is also stateless and therefore considered thread-safe.
	 * 
	 * @return a {@code NodeFilter} that accepts {@code Node} instances that are instances of {@code ConstantUTF8Info}
	 */
	public static NodeFilter newAnyNodeFilter() {
		return node -> {
			Objects.requireNonNull(node, "node == null");
			
			if(node instanceof ConstantUTF8Info) {
				return true;
			}
			
			return false;
		};
	}
	
	/**
	 * Returns a {@link NodeFilter} that accepts {@link Node} instances that are instances of {@code ConstantUTF8Info} and have a {@code getStringValue()} method that matches {@code regex}.
	 * <p>
	 * If {@code regex} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * The {@code NodeFilter} returned by this method will throw a {@code NullPointerException} if, and only if, the {@code Node} to accept or reject is {@code null}. It is also stateless and therefore considered thread-safe.
	 * 
	 * @param regex a {@code String} representing a Regex pattern
	 * @return a {@code NodeFilter} that accepts {@code Node} instances that are instances of {@code ConstantUTF8Info} and have a {@code getStringValue()} method that matches {@code regex}
	 * @throws NullPointerException thrown if, and only if, {@code regex} is {@code null}
	 */
	public static NodeFilter newRegexNodeFilter(final String regex) {
		Objects.requireNonNull(regex, "regex == null");
		
		return node -> {
			Objects.requireNonNull(node, "node == null");
			
			if(node instanceof ConstantUTF8Info) {
				final ConstantUTF8Info constantUTF8Info = ConstantUTF8Info.class.cast(node);
				
				if(constantUTF8Info.getStringValue().matches(regex)) {
					return true;
				}
			}
			
			return false;
		};
	}
}