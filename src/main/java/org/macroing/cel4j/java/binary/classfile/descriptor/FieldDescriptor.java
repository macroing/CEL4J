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
package org.macroing.cel4j.java.binary.classfile.descriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.java.binary.classfile.CPInfo;
import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.FieldInfo;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.LocalVariable;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.scanner.TextScanner;

/**
 * A {@code FieldDescriptor} denotes a FieldDescriptor as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface FieldDescriptor extends Node {
	/**
	 * Returns a {@code Class} representation of this {@code FieldDescriptor} instance.
	 * <p>
	 * If the {@code Class} cannot be found, a {@code ClassNotFoundException} will be thrown.
	 * 
	 * @return a {@code Class} representation of this {@code FieldDescriptor} instance
	 * @throws ClassNotFoundException thrown if, and only if, the {@code Class} cannot be found
	 */
	Class<?> toClass() throws ClassNotFoundException;
	
	/**
	 * Returns a {@code String} representation of this {@code FieldDescriptor} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code FieldDescriptor} instance in external form
	 */
	String toExternalForm();
	
	/**
	 * Returns a {@code String} representation of this {@code FieldDescriptor} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code FieldDescriptor} instance in internal form
	 */
	String toInternalForm();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code FieldDescriptor} instance that excludes all package names that are equal to {@code "java.lang"} from {@code fieldDescriptor}.
	 * <p>
	 * If {@code fieldDescriptor} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * FieldDescriptor.excludePackageName(fieldDescriptor, "java.lang");
	 * }
	 * </pre>
	 * 
	 * @param fieldDescriptor a {@code FieldDescriptor} instance
	 * @return a {@code FieldDescriptor} instance that excludes all package names that are equal to {@code "java.lang"} from {@code fieldDescriptor}
	 * @throws NullPointerException thrown if, and only if, {@code fieldDescriptor} is {@code null}
	 */
	static FieldDescriptor excludePackageName(final FieldDescriptor fieldDescriptor) {
		return excludePackageName(fieldDescriptor, "java.lang");
	}
	
	/**
	 * Returns a {@code FieldDescriptor} instance that excludes all package names that are equal to {@code packageName} from {@code fieldDescriptor}.
	 * <p>
	 * If either {@code fieldDescriptor} or {@code packageName} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param fieldDescriptor a {@code FieldDescriptor} instance
	 * @param packageName the package name to exclude
	 * @return a {@code FieldDescriptor} instance that excludes all package names that are equal to {@code packageName} from {@code fieldDescriptor}
	 * @throws NullPointerException thrown if, and only if, either {@code fieldDescriptor} or {@code packageName} are {@code null}
	 */
	static FieldDescriptor excludePackageName(final FieldDescriptor fieldDescriptor, final String packageName) {
		Objects.requireNonNull(fieldDescriptor, "fieldDescriptor == null");
		Objects.requireNonNull(packageName, "packageName == null");
		
		return Filters.excludePackageName(packageName, fieldDescriptor);
	}
	
	/**
	 * Parses the {@code FieldDescriptor} of {@code fieldInfo} in {@code classFile}.
	 * <p>
	 * Returns a {@code FieldDescriptor} instance.
	 * <p>
	 * If either {@code classFile} or {@code fieldInfo} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code classFile} does not contain a {@link FieldInfo} instance that is equal to {@code fieldInfo}, the {@link CPInfo} on the index {@code fieldInfo.getDescriptorIndex()} is not a {@link ConstantUTF8Info} instance, or the {@code getStringValue()} method of the {@code ConstantUTF8Info} instance returns a {@code String} that is malformed, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code fieldInfo.getDescriptorIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param classFile a {@link ClassFile} instance
	 * @param fieldInfo a {@code FieldInfo} instance
	 * @return a {@code FieldDescriptor} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code classFile} does not contain a {@code FieldInfo} instance that is equal to {@code fieldInfo}, the {@code CPInfo} on the index {@code fieldInfo.getDescriptorIndex()} is not a {@code ConstantUTF8Info} instance, or the {@code getStringValue()} method of the {@code ConstantUTF8Info} instance returns a {@code String} that is malformed
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code fieldInfo.getDescriptorIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code fieldInfo} are {@code null}
	 */
	static FieldDescriptor parseFieldDescriptor(final ClassFile classFile, final FieldInfo fieldInfo) {
		return parseFieldDescriptor(ConstantUTF8Info.findByDescriptorIndex(classFile, fieldInfo).getStringValue());
	}
	
	/**
	 * Parses the {@code FieldDescriptor} of {@code localVariable}.
	 * <p>
	 * Returns a {@code FieldDescriptor} instance.
	 * <p>
	 * If either {@code classFile} or {@code localVariable} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the {@link CPInfo} on the index {@code localVariable.getDescriptorIndex()} is not a {@link ConstantUTF8Info} instance, or the {@code getStringValue()} method of the {@code ConstantUTF8Info} instance returns a {@code String} that is malformed, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If {@code localVariable.getDescriptorIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param classFile a {@link ClassFile} instance
	 * @param localVariable a {@link LocalVariable} instance
	 * @return a {@code FieldDescriptor} instance
	 * @throws IllegalArgumentException thrown if, and only if, the {@code CPInfo} on the index {@code localVariable.getDescriptorIndex()} is not a {@code ConstantUTF8Info} instance, or the {@code getStringValue()} method of the {@code ConstantUTF8Info} instance returns a {@code String} that is malformed
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code localVariable.getDescriptorIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, either {@code classFile} or {@code localVariable} are {@code null}
	 */
	static FieldDescriptor parseFieldDescriptor(final ClassFile classFile, final LocalVariable localVariable) {
		return parseFieldDescriptor(classFile.getCPInfo(localVariable.getDescriptorIndex(), ConstantUTF8Info.class).getStringValue());
	}
	
	/**
	 * Parses {@code string} into a {@code FieldDescriptor} instance.
	 * <p>
	 * Returns a {@code FieldDescriptor} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return a {@code FieldDescriptor} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	static FieldDescriptor parseFieldDescriptor(final String string) {
		return Parsers.parseFieldDescriptor(new TextScanner(string));
	}
	
	/**
	 * Parses all {@code FieldDescriptor} instances from all {@link FieldInfo} instances in {@code classFile}.
	 * <p>
	 * Returns a {@code List} with all {@code FieldDescriptor} instances that were parsed from all {@code FieldInfo} instances in {@code classFile}.
	 * <p>
	 * If {@code classFile} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If, for any {@code FieldInfo} {@code fieldInfo} in {@code classFile}, the {@link CPInfo} on the index {@code fieldInfo.getDescriptorIndex()} is not a {@link ConstantUTF8Info} instance, or the {@code getStringValue()} method of the {@code ConstantUTF8Info} instance returns a {@code String} that is malformed, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If, for any {@code FieldInfo} {@code fieldInfo} in {@code classFile}, {@code fieldInfo.getDescriptorIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param classFile a {@link ClassFile} instance
	 * @return a {@code List} with all {@code FieldDescriptor} instances that were parsed from all {@code FieldInfo} instances in {@code classFile}
	 * @throws IllegalArgumentException thrown if, and only if, for any {@code FieldInfo} {@code fieldInfo} in {@code classFile}, the {@code CPInfo} on the index {@code fieldInfo.getDescriptorIndex()} is not a {@code ConstantUTF8Info} instance, or the {@code getStringValue()} method of the {@code ConstantUTF8Info} instance returns a {@code String} that is malformed
	 * @throws IndexOutOfBoundsException thrown if, and only if, for any {@code FieldInfo} {@code fieldInfo} in {@code classFile}, {@code fieldInfo.getDescriptorIndex()} is less than {@code 0}, or greater than or equal to {@code classFile.getCPInfoCount()}
	 * @throws NullPointerException thrown if, and only if, {@code classFile} is {@code null}
	 */
	static List<FieldDescriptor> parseFieldDescriptors(final ClassFile classFile) {
		return classFile.getFieldInfos().stream().map(fieldInfo -> parseFieldDescriptor(classFile, fieldInfo)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
}