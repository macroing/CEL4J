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

final class JArray extends JType {
	private static final Map<String, JArray> J_ARRAYS = new HashMap<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final JType componentType;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private JArray(final JType componentType) {
		this.componentType = componentType;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public JType getComponentType() {
		return this.componentType;
	}
	
	@Override
	public String getName() {
		return getComponentType().getName() + "[]";
	}
	
	@Override
	public String toString() {
		return String.format("JArray: [Name=%s], [ComponentType=%s]", getName(), getComponentType());
	}
	
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
	
	@Override
	public boolean isInnerType() {
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getComponentType());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static JArray valueOf(final Class<?> clazz) {
		if(!clazz.isArray()) {
			throw new JTypeException(String.format("A JArray must refer to an array: %s", clazz));
		}
		
		synchronized(J_ARRAYS) {
			return J_ARRAYS.computeIfAbsent(clazz.getName(), name -> new JArray(JType.valueOf(clazz.getComponentType())));
		}
	}
	
	public static JArray valueOf(final String name) {
		if(!name.startsWith("[")) {
			throw new JTypeException(String.format("A JArray must refer to an array: %s", name));
		}
		
		try {
			return valueOf(Class.forName(name));
		} catch(final ClassNotFoundException | LinkageError e) {
			throw new JTypeException(e);
		}
	}
	
	public static void clearCache() {
		synchronized(J_ARRAYS) {
			J_ARRAYS.clear();
		}
	}
}