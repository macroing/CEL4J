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
package org.macroing.cel4j.java.binary.classfile.descriptor;

import java.util.Objects;

import org.macroing.cel4j.java.binary.classfile.CPInfo;
import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantClassInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.scanner.TextScanner;

/**
 * A {@code ClassName} denotes a ClassName as defined by the Java Virtual Machine Specifications.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ClassName implements Node {
	private final String internalForm;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	ClassName(final String internalForm) {
		this.internalForm = internalForm;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code Class} representation of this {@code ClassName} instance.
	 * <p>
	 * If the {@code Class} cannot be found, a {@code ClassNotFoundException} will be thrown.
	 * 
	 * @return a {@code Class} representation of this {@code ClassName} instance
	 * @throws ClassNotFoundException thrown if, and only if, the {@code Class} cannot be found
	 */
	public Class<?> toClass() throws ClassNotFoundException {
		return Class.forName(toExternalForm());
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ClassName} instance in external form.
	 * 
	 * @return a {@code String} representation of this {@code ClassName} instance in external form
	 */
	public String toExternalForm() {
		return toInternalForm().replace("/", ".");
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ClassName} instance in internal form.
	 * 
	 * @return a {@code String} representation of this {@code ClassName} instance in internal form
	 */
	public String toInternalForm() {
		return this.internalForm;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ClassName} instance.
	 * 
	 * @return a {@code String} representation of this {@code ClassName} instance
	 */
	@Override
	public String toString() {
		return String.format("ClassName: [ExternalForm=%s], [InternalForm=%s]", toExternalForm(), toInternalForm());
	}
	
	/**
	 * Compares {@code object} to this {@code ClassName} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ClassName}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ClassName} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ClassName}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ClassName)) {
			return false;
		} else if(!Objects.equals(ClassName.class.cast(object).internalForm, this.internalForm)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code ClassName} instance.
	 * 
	 * @return a hash code for this {@code ClassName} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.internalForm);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Parses {@code string} into a {@code ClassName} instance.
	 * <p>
	 * Returns a {@code ClassName} instance.
	 * <p>
	 * If {@code string} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code string} is malformed, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param string the {@code String} to parse
	 * @return a {@code ClassName} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code string} is malformed
	 * @throws NullPointerException thrown if, and only if, {@code string} is {@code null}
	 */
	public static ClassName parseClassName(final String string) {
		return Parsers.parseClassName(new TextScanner(string));
	}
	
	/**
	 * Parses the {@code String} indirectly referred to by {@code classFile.getSuperClass()} into a {@code ClassName} instance.
	 * <p>
	 * Returns a {@code ClassName} instance.
	 * <p>
	 * If {@code classFile} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the {@code String} indirectly referred to by {@code classFile.getSuperClass()} cannot be retrieved, or it is malformed, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * In order to retrieve the {@code String} to parse, {@code classFile.getSuperClass()} must point to a valid index in the {@code constant_pool} table item. The {@link CPInfo} at that index must be a {@link ConstantClassInfo} instance. The method
	 * {@code getNameIndex()} of the {@code ConstantClassInfo} instance must also point to a valid index in the {@code constant_pool} table item. The {@code CPInfo} at this index must be a {@link ConstantUTF8Info} instance. The method
	 * {@code getStringValue()} of the {@code ConstantUTF8Info} instance must return a {@code String} that is not malformed.
	 * 
	 * @param classFile a {@link ClassFile}
	 * @return a {@code ClassName} instance
	 * @throws IllegalArgumentException thrown if, and only if, the {@code String} indirectly referred to by {@code classFile.getSuperClass()} cannot be retrieved, or it is malformed
	 * @throws NullPointerException thrown if, and only if, {@code classFile} is {@code null}
	 */
	public static ClassName parseClassNameSuperClass(final ClassFile classFile) {
		return parseClassName(classFile.getCPInfo(classFile.getCPInfo(classFile.getSuperClass(), ConstantClassInfo.class).getNameIndex(), ConstantUTF8Info.class).getStringValue());
	}
	
	/**
	 * Parses the {@code String} indirectly referred to by {@code classFile.getThisClass()} into a {@code ClassName} instance.
	 * <p>
	 * Returns a {@code ClassName} instance.
	 * <p>
	 * If {@code classFile} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If the {@code String} indirectly referred to by {@code classFile.getThisClass()} cannot be retrieved, or it is malformed, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * In order to retrieve the {@code String} to parse, {@code classFile.getThisClass()} must point to a valid index in the {@code constant_pool} table item. The {@link CPInfo} at that index must be a {@link ConstantClassInfo} instance. The method
	 * {@code getNameIndex()} of the {@code ConstantClassInfo} instance must also point to a valid index in the {@code constant_pool} table item. The {@code CPInfo} at this index must be a {@link ConstantUTF8Info} instance. The method
	 * {@code getStringValue()} of the {@code ConstantUTF8Info} instance must return a {@code String} that is not malformed.
	 * 
	 * @param classFile a {@link ClassFile}
	 * @return a {@code ClassName} instance
	 * @throws IllegalArgumentException thrown if, and only if, the {@code String} indirectly referred to by {@code classFile.getThisClass()} cannot be retrieved, or it is malformed
	 * @throws NullPointerException thrown if, and only if, {@code classFile} is {@code null}
	 */
	public static ClassName parseClassNameThisClass(final ClassFile classFile) {
		return parseClassName(classFile.getCPInfo(classFile.getCPInfo(classFile.getThisClass(), ConstantClassInfo.class).getNameIndex(), ConstantUTF8Info.class).getStringValue());
	}
}