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
package org.macroing.cel4j.java.decompiler;

/**
 * A {@code LocalVariableNameGenerator} is used to generate the name of a local variable.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public interface LocalVariableNameGenerator {
	/**
	 * Returns the name of a local variable.
	 * 
	 * @param typeName a {@code String} with the name of the type of the local variable
	 * @param index the position of the local variable in the method or constructor
	 * @return the name of a local variable
	 */
	String generateLocalVariableName(final String typeName, final int index);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code LocalVariableNameGenerator} that generates the local variable name based on the simple name of the type.
	 * <p>
	 * A method with the signature {@code format(String, Object[])} would become {@code format(String string0, Object[] objectArray1)}.
	 * 
	 * @return a {@code LocalVariableNameGenerator} that generates the local variable name based on the simple name of the type
	 */
	static LocalVariableNameGenerator newSimpleName() {
		return (typeName, index) -> {
			final String fullyQualifiedName = typeName;
			final String simpleName = fullyQualifiedName.lastIndexOf(".") >= 0 ? fullyQualifiedName.substring(fullyQualifiedName.lastIndexOf(".") + 1) : fullyQualifiedName;
			final String localVariableName = (Character.toLowerCase(simpleName.charAt(0)) + (simpleName.length() > 1 ? simpleName.substring(1) : "") + index).replace("[]", "Array");
			
			return localVariableName;
		};
	}
}