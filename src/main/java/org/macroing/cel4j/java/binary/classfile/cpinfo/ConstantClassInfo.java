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

import org.macroing.cel4j.java.binary.classfile.CPInfo;
import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code ConstantClassInfo} represents a {@code CONSTANT_Class_info} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code CONSTANT_Class_info} structure was added to Java in version 1.0.2 and can be found in the {@code constant_pool} table item of a {@code ClassFile} structure.
 * <p>
 * The {@code CONSTANT_Class_info} structure has the following format:
 * <pre>
 * <code>
 * CONSTANT_Class_info {
 *     u1 tag;
 *     u2 name_index;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ConstantClassInfo extends CPInfo {
	/**
	 * The name of the {@code CONSTANT_Class_info} structure.
	 */
	public static final String NAME = "CONSTANT_Class";
	
	/**
	 * The tag for the {@code CONSTANT_Class_info} structure.
	 */
	public static final int TAG = 7;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int nameIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ConstantClassInfo} instance that is a copy of {@code constantClassInfo}.
	 * <p>
	 * If {@code constantClassInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param constantClassInfo the {@code ConstantClassInfo} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code constantClassInfo} is {@code null}
	 */
	public ConstantClassInfo(final ConstantClassInfo constantClassInfo) {
		super(NAME, TAG, 1);
		
		this.nameIndex = constantClassInfo.nameIndex;
	}
	
	/**
	 * Constructs a new {@code ConstantClassInfo} instance.
	 * <p>
	 * If {@code nameIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param nameIndex the value for the {@code name_index} item associated with this {@code ConstantClassInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code nameIndex} is less than {@code 1}
	 */
	public ConstantClassInfo(final int nameIndex) {
		super(NAME, TAG, 1);
		
		this.nameIndex = ParameterArguments.requireRange(nameIndex, 1, Integer.MAX_VALUE);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ConstantClassInfo} instance.
	 * 
	 * @return a copy of this {@code ConstantClassInfo} instance
	 */
	@Override
	public ConstantClassInfo copy() {
		return new ConstantClassInfo(this);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ConstantClassInfo} instance.
	 * 
	 * @return a {@code String} representation of this {@code ConstantClassInfo} instance
	 */
	@Override
	public String toString() {
		return String.format("new ConstantClassInfo(%s)", Integer.toString(getNameIndex()));
	}
	
	/**
	 * Compares {@code object} to this {@code ConstantClassInfo} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ConstantClassInfo}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ConstantClassInfo} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ConstantClassInfo}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ConstantClassInfo)) {
			return false;
		} else if(!Objects.equals(getName(), ConstantClassInfo.class.cast(object).getName())) {
			return false;
		} else if(getTag() != ConstantClassInfo.class.cast(object).getTag()) {
			return false;
		} else if(getConstantPoolEntryCount() != ConstantClassInfo.class.cast(object).getConstantPoolEntryCount()) {
			return false;
		} else if(getNameIndex() != ConstantClassInfo.class.cast(object).getNameIndex()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code name_index} item associated with this {@code ConstantClassInfo} instance.
	 * 
	 * @return the value of the {@code name_index} item associated with this {@code ConstantClassInfo} instance
	 */
	public int getNameIndex() {
		return this.nameIndex;
	}
	
	/**
	 * Returns a hash code for this {@code ConstantClassInfo} instance.
	 * 
	 * @return a hash code for this {@code ConstantClassInfo} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getTag()), Integer.valueOf(getConstantPoolEntryCount()), Integer.valueOf(getNameIndex()));
	}
	
	/**
	 * Sets {@code nameIndex} as the value for the {@code name_index} item associated with this {@code ConstantClassInfo} instance.
	 * <p>
	 * If {@code nameIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param nameIndex the value for the {@code name_index} item associated with this {@code ConstantClassInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code nameIndex} is less than {@code 1}
	 */
	public void setNameIndex(final int nameIndex) {
		this.nameIndex = ParameterArguments.requireRange(nameIndex, 1, Integer.MAX_VALUE, "nameIndex");
	}
	
	/**
	 * Writes this {@code ConstantClassInfo} to {@code dataOutput}.
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
			dataOutput.writeShort(getNameIndex());
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Writes this {@code ConstantClassInfo} to {@code document}.
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
		document.linef("u2 name_index = %s;", Integer.toString(getNameIndex()));
		document.outdent();
		document.linef("};");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@code ConstantClassInfo} that is located on the index {@code constantFieldRefInfo.getClassIndex()} in the {@code constant_pool} table item of {@code classFile}.
	 * <p>
	 * If either {@code classFile} or {@code constantFieldRefInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code classFile} does not contain a {@code ConstantFieldRefInfo} instance that is equal to {@code constantFieldRefInfo}, or the {@link CPInfo} on the index {@code constantFieldRefInfo.getClassIndex()} is not a {@code ConstantClassInfo}
	 * instance, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code constantFieldRefInfo.getClassIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param classFile the {@code ClassFile} instance that contains a {@code ConstantFieldRefInfo} instance that is equal to {@code constantFieldRefInfo}
	 * @param constantFieldRefInfo the {@code ConstantFieldRefInfo} instance that contains the {@code class_index}
	 * @return the {@code ConstantClassInfo} that is located on the index {@code constantFieldRefInfo.getClassIndex()} in the {@code constant_pool} table item of {@code classFile}
	 * @throws IllegalArgumentException thrown if, and only if, {@code classFile} does not contain a {@code ConstantFieldRefInfo} instance that is equal to {@code constantFieldRefInfo}, or the {@code CPInfo} on the index
	 *                                  {@code constantFieldRefInfo.getClassIndex()} is not a {@code ConstantClassInfo} instance
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code constantFieldRefInfo.getClassIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code constantFieldRefInfo} are {@code null}
	 */
	public static ConstantClassInfo findByClassIndex(final ClassFile classFile, final ConstantFieldRefInfo constantFieldRefInfo) {
		return classFile.getCPInfo(classFile.getCPInfo(constantFieldRefInfo, ConstantFieldRefInfo.class).getClassIndex(), ConstantClassInfo.class);
	}
	
	/**
	 * Returns the {@code ConstantClassInfo} that is located on the index {@code constantInterfaceMethodRefInfo.getClassIndex()} in the {@code constant_pool} table item of {@code classFile}.
	 * <p>
	 * If either {@code classFile} or {@code constantInterfaceMethodRefInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code classFile} does not contain a {@code ConstantInterfaceMethodRefInfo} instance that is equal to {@code constantInterfaceMethodRefInfo}, or the {@link CPInfo} on the index {@code constantInterfaceMethodRefInfo.getClassIndex()} is not a
	 * {@code ConstantClassInfo} instance, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code constantInterfaceMethodRefInfo.getClassIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param classFile the {@code ClassFile} instance that contains a {@code ConstantInterfaceMethodRefInfo} instance that is equal to {@code constantInterfaceMethodRefInfo}
	 * @param constantInterfaceMethodRefInfo the {@code ConstantInterfaceMethodRefInfo} instance that contains the {@code class_index}
	 * @return the {@code ConstantClassInfo} that is located on the index {@code constantInterfaceMethodRefInfo.getClassIndex()} in the {@code constant_pool} table item of {@code classFile}
	 * @throws IllegalArgumentException thrown if, and only if, {@code classFile} does not contain a {@code ConstantInterfaceMethodRefInfo} instance that is equal to {@code constantInterfaceMethodRefInfo}, or the {@code CPInfo} on the index
	 *                                  {@code constantInterfaceMethodRefInfo.getClassIndex()} is not a {@code ConstantClassInfo} instance
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code constantInterfaceMethodRefInfo.getClassIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code constantInterfaceMethodRefInfo} are {@code null}
	 */
	public static ConstantClassInfo findByClassIndex(final ClassFile classFile, final ConstantInterfaceMethodRefInfo constantInterfaceMethodRefInfo) {
		return classFile.getCPInfo(classFile.getCPInfo(constantInterfaceMethodRefInfo, ConstantInterfaceMethodRefInfo.class).getClassIndex(), ConstantClassInfo.class);
	}
	
	/**
	 * Returns the {@code ConstantClassInfo} that is located on the index {@code constantMethodRefInfo.getClassIndex()} in the {@code constant_pool} table item of {@code classFile}.
	 * <p>
	 * If either {@code classFile} or {@code constantMethodRefInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code classFile} does not contain a {@code ConstantMethodRefInfo} instance that is equal to {@code constantMethodRefInfo}, or the {@link CPInfo} on the index {@code constantInterfaceMethodRefInfo.getClassIndex()} is not a
	 * {@code ConstantClassInfo} instance, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code constantMethodRefInfo.getClassIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param classFile the {@code ClassFile} instance that contains a {@code ConstantMethodRefInfo} instance that is equal to {@code constantMethodRefInfo}
	 * @param constantMethodRefInfo the {@code ConstantMethodRefInfo} instance that contains the {@code class_index}
	 * @return the {@code ConstantClassInfo} that is located on the index {@code constantMethodRefInfo.getClassIndex()} in the {@code constant_pool} table item of {@code classFile}
	 * @throws IllegalArgumentException thrown if, and only if, {@code classFile} does not contain a {@code ConstantMethodRefInfo} instance that is equal to {@code constantMethodRefInfo}, or the {@code CPInfo} on the index
	 *                                  {@code constantMethodRefInfo.getClassIndex()} is not a {@code ConstantClassInfo} instance
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code constantMethodRefInfo.getClassIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code constantMethodRefInfo} are {@code null}
	 */
	public static ConstantClassInfo findByClassIndex(final ClassFile classFile, final ConstantMethodRefInfo constantMethodRefInfo) {
		return classFile.getCPInfo(classFile.getCPInfo(constantMethodRefInfo, ConstantMethodRefInfo.class).getClassIndex(), ConstantClassInfo.class);
	}
	
	/**
	 * Returns a {@code List} with all {@code ConstantClassInfo} instances in {@code node}.
	 * <p>
	 * All {@code ConstantClassInfo} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code ConstantClassInfo} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<ConstantClassInfo> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), ConstantClassInfo.class);
	}
}