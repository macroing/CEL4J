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
import java.lang.reflect.Field;//TODO: Update Javadocs!
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
 * A {@code ConstantClassInfo} denotes a CONSTANT_Class_info structure in the constant_pool of a ClassFile structure.
 * <p>
 * The CONSTANT_Class_info structure was added to Java in version 1.0.2.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ConstantClassInfo extends CPInfo {
	/**
	 * The name of the CONSTANT_Class_info structure.
	 */
	public static final String NAME = "CONSTANT_Class";
	
	/**
	 * The tag for CONSTANT_Class.
	 */
	public static final int TAG = 7;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private int nameIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ConstantClassInfo}.
	 * <p>
	 * If {@code nameIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param nameIndex the name_index of the new {@code ConstantClassInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code nameIndex} is less than or equal to {@code 0}
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
		return new ConstantClassInfo(this.nameIndex);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ConstantClassInfo} instance.
	 * 
	 * @return a {@code String} representation of this {@code ConstantClassInfo} instance
	 */
	@Override
	public String toString() {
		return String.format("CONSTANT_Class_info: name_index=%s", Integer.toString(this.nameIndex));
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ConstantClassInfo}, and that {@code ConstantClassInfo} instance is equal to this {@code ConstantClassInfo} instance, {@code false}
	 * otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code ConstantClassInfo} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ConstantClassInfo}, and that {@code ConstantClassInfo} instance is equal to this {@code ConstantClassInfo} instance, {@code false}
	 * otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ConstantClassInfo)) {
			return false;
		} else if(!Objects.equals(ConstantClassInfo.class.cast(object).getName(), getName())) {
			return false;
		} else if(ConstantClassInfo.class.cast(object).getTag() != getTag()) {
			return false;
		} else if(ConstantClassInfo.class.cast(object).getConstantPoolEntryCount() != getConstantPoolEntryCount()) {
			return false;
		} else if(ConstantClassInfo.class.cast(object).nameIndex != this.nameIndex) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the name_index of this {@code ConstantClassInfo} instance.
	 * 
	 * @return the name_index of this {@code ConstantClassInfo} instance
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
	 * Sets a new name_index for this {@code ConstantClassInfo} instance.
	 * <p>
	 * If {@code nameIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param nameIndex the new name_index for this {@code ConstantClassInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code nameIndex} is less than or equal to {@code 0}
	 */
	public void setNameIndex(final int nameIndex) {
		this.nameIndex = ParameterArguments.requireRange(nameIndex, 1, Integer.MAX_VALUE);
	}
	
	/**
	 * Writes this {@code ConstantClassInfo} to {@code dataOutput}.
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
			dataOutput.writeByte(getTag());
			dataOutput.writeShort(this.nameIndex);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Writes this {@code ConstantClassInfo} to {@code document}.
	 * <p>
	 * If {@code document} is {@code null}, a {@code NullPointerException} may be thrown. But no guarantees can be made.
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
	 * Returns the {@code ConstantClassInfo} that is located on the index {@code constantFieldRefInfo.getClassIndex()} in the constant_pool table of {@code classFile}.
	 * <p>
	 * If either {@code classFile} or {@code constantFieldRefInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code classFile} does not contain a {@code ConstantFieldRefInfo} instance that is equal to {@code constantFieldRefInfo}, or the {@link CPInfo} on the index {@code constantFieldRefInfo.getClassIndex()} is not a {@code ConstantClassInfo}
	 * instance, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code constantFieldRefInfo.getClassIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param classFile the {@code ClassFile} instance that contains a {@code ConstantFieldRefInfo} instance that is equal to {@code constantFieldRefInfo}
	 * @param constantFieldRefInfo the {@code ConstantFieldRefInfo} instance that contains the class_index
	 * @return the {@code ConstantClassInfo} that is located on the index {@code constantFieldRefInfo.getClassIndex()} in the constant_pool table of {@code classFile}
	 * @throws IllegalArgumentException thrown if, and only if, {@code classFile} does not contain a {@code ConstantFieldRefInfo} instance that is equal to {@code constantFieldRefInfo}, or the {@code CPInfo} on the index
	 *                                  {@code constantFieldRefInfo.getClassIndex()} is not a {@code ConstantClassInfo} instance
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code constantFieldRefInfo.getClassIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code constantFieldRefInfo} are {@code null}
	 */
	public static ConstantClassInfo findByClassIndex(final ClassFile classFile, final ConstantFieldRefInfo constantFieldRefInfo) {
		return classFile.getCPInfo(classFile.getCPInfo(constantFieldRefInfo, ConstantFieldRefInfo.class).getClassIndex(), ConstantClassInfo.class);
	}
	
	/**
	 * Returns the {@code ConstantClassInfo} that is located on the index {@code constantInterfaceMethodRefInfo.getClassIndex()} in the constant_pool table of {@code classFile}.
	 * <p>
	 * If either {@code classFile} or {@code constantInterfaceMethodRefInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code classFile} does not contain a {@code ConstantInterfaceMethodRefInfo} instance that is equal to {@code constantInterfaceMethodRefInfo}, or the {@link CPInfo} on the index {@code constantInterfaceMethodRefInfo.getClassIndex()} is not a
	 * {@code ConstantClassInfo} instance, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code constantInterfaceMethodRefInfo.getClassIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param classFile the {@code ClassFile} instance that contains a {@code ConstantInterfaceMethodRefInfo} instance that is equal to {@code constantInterfaceMethodRefInfo}
	 * @param constantInterfaceMethodRefInfo the {@code ConstantInterfaceMethodRefInfo} instance that contains the class_index
	 * @return the {@code ConstantClassInfo} that is located on the index {@code constantInterfaceMethodRefInfo.getClassIndex()} in the constant_pool table of {@code classFile}
	 * @throws IllegalArgumentException thrown if, and only if, {@code classFile} does not contain a {@code ConstantInterfaceMethodRefInfo} instance that is equal to {@code constantInterfaceMethodRefInfo}, or the {@code CPInfo} on the index
	 *                                  {@code constantInterfaceMethodRefInfo.getClassIndex()} is not a {@code ConstantClassInfo} instance
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code constantInterfaceMethodRefInfo.getClassIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code constantInterfaceMethodRefInfo} are {@code null}
	 */
	public static ConstantClassInfo findByClassIndex(final ClassFile classFile, final ConstantInterfaceMethodRefInfo constantInterfaceMethodRefInfo) {
		return classFile.getCPInfo(classFile.getCPInfo(constantInterfaceMethodRefInfo, ConstantInterfaceMethodRefInfo.class).getClassIndex(), ConstantClassInfo.class);
	}
	
	/**
	 * Returns the {@code ConstantClassInfo} that is located on the index {@code constantMethodRefInfo.getClassIndex()} in the constant_pool table of {@code classFile}.
	 * <p>
	 * If either {@code classFile} or {@code constantMethodRefInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code classFile} does not contain a {@code ConstantMethodRefInfo} instance that is equal to {@code constantMethodRefInfo}, or the {@link CPInfo} on the index {@code constantInterfaceMethodRefInfo.getClassIndex()} is not a
	 * {@code ConstantClassInfo} instance, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code constantMethodRefInfo.getClassIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param classFile the {@code ClassFile} instance that contains a {@code ConstantMethodRefInfo} instance that is equal to {@code constantMethodRefInfo}
	 * @param constantMethodRefInfo the {@code ConstantMethodRefInfo} instance that contains the class_index
	 * @return the {@code ConstantClassInfo} that is located on the index {@code constantMethodRefInfo.getClassIndex()} in the constant_pool table of {@code classFile}
	 * @throws IllegalArgumentException thrown if, and only if, {@code classFile} does not contain a {@code ConstantMethodRefInfo} instance that is equal to {@code constantMethodRefInfo}, or the {@code CPInfo} on the index
	 *                                  {@code constantMethodRefInfo.getClassIndex()} is not a {@code ConstantClassInfo} instance
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code constantMethodRefInfo.getClassIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code constantMethodRefInfo} are {@code null}
	 */
	public static ConstantClassInfo findByClassIndex(final ClassFile classFile, final ConstantMethodRefInfo constantMethodRefInfo) {
		return classFile.getCPInfo(classFile.getCPInfo(constantMethodRefInfo, ConstantMethodRefInfo.class).getClassIndex(), ConstantClassInfo.class);
	}
	
	/**
	 * Returns a {@code List} with all {@code ConstantClassInfo}s.
	 * <p>
	 * All {@code ConstantClassInfo}s are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code ConstantClassInfo}s
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<ConstantClassInfo> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), ConstantClassInfo.class);
	}
}