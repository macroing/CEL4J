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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A {@code JArray} is a {@link JType} implementation that represents an array type.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
final class JArray extends JType {
	private static final Map<String, JArray> J_ARRAYS = new HashMap<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final JType componentType;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code JArray} instance.
	 * <p>
	 * If {@code componentType} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param componentType the {@link JType} instance that represents the component type
	 * @throws NullPointerException thrown if, and only if, {@code componentType} is {@code null}
	 */
	public JArray(final JType componentType) {
		this.componentType = Objects.requireNonNull(componentType, "componentType == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link JType} instance that represents the component type of this {@code JArray} instance.
	 * 
	 * @return the {@code JType} instance that represents the component type of this {@code JArray} instance
	 */
	public JType getComponentType() {
		return this.componentType;
	}
	
	/**
	 * Returns the name of this {@code JArray} instance.
	 * 
	 * @return the name of this {@code JArray} instance
	 */
	@Override
	public String getName() {
		return getComponentType().getName() + "[]";
	}
	
	/**
	 * Returns a {@code String} representation of this {@code JArray} instance.
	 * 
	 * @return a {@code String} representation of this {@code JArray} instance
	 */
	@Override
	public String toString() {
		return String.format("new JArray(%s)", getComponentType());
	}
	
	/**
	 * Compares {@code object} to this {@code JArray} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code JArray}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code JArray} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code JArray}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof JArray)) {
			return false;
		} else if(!Objects.equals(getComponentType(), JArray.class.cast(object).getComponentType())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code false}.
	 * 
	 * @return {@code false}
	 */
	@Override
	public boolean isInnerType() {
		return false;
	}
	
	/**
	 * Returns a hash code for this {@code JArray} instance.
	 * 
	 * @return a hash code for this {@code JArray} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getComponentType());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code JArray} instance that represents {@code clazz}.
	 * <p>
	 * If {@code clazz} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code clazz.isArray() == false}, a {@code JTypeException} will be thrown.
	 * <p>
	 * This method will cache all {@code JArray} instances.
	 * 
	 * @param clazz a {@code Class} instance
	 * @return a {@code JArray} instance that represents {@code clazz}
	 * @throws JTypeException thrown if, and only if, {@code clazz.isArray() == false}
	 * @throws NullPointerException thrown if, and only if, {@code clazz} is {@code null}
	 */
	public static JArray valueOf(final Class<?> clazz) {
		Objects.requireNonNull(clazz, "clazz == null");
		
		if(!clazz.isArray()) {
			throw new JTypeException(String.format("A JArray must refer to an array: %s", clazz));
		}
		
		synchronized(J_ARRAYS) {
			return J_ARRAYS.computeIfAbsent(clazz.getName(), name -> new JArray(JType.valueOf(clazz.getComponentType())));
		}
	}
	
	/**
	 * Returns a {@code JArray} instance that represents {@code Class.forName(className)}.
	 * <p>
	 * If {@code className} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code Class.forName(className)} fails or {@code Class.forName(className).isArray() == false}, a {@code JTypeException} will be thrown.
	 * <p>
	 * This method will cache all {@code JArray} instances.
	 * 
	 * @param className the fully qualified name of the desired class
	 * @return a {@code JArray} instance that represents {@code Class.forName(className)}
	 * @throws JTypeException thrown if, and only if, {@code Class.forName(className)} fails or {@code Class.forName(className).isArray() == false}
	 * @throws NullPointerException thrown if, and only if, {@code className} is {@code null}
	 */
	public static JArray valueOf(final String className) {
		try {
			return valueOf(Class.forName(Objects.requireNonNull(className, "className == null")));
		} catch(final ClassNotFoundException | LinkageError e) {
			throw new JTypeException(e);
		}
	}
	
	/**
	 * Clears the cache.
	 */
	public static void clearCache() {
		synchronized(J_ARRAYS) {
			J_ARRAYS.clear();
		}
	}
}