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
package org.macroing.cel4j.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;

/**
 * A {@code JSONObject} denotes a JSON object.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class JSONObject implements JSONType {
	private final List<Property> properties;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new empty {@code JSONObject} instance.
	 */
	public JSONObject() {
		this.properties = new ArrayList<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@link Property} instances added to this {@code JSONObject} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code JSONObject} instance.
	 * 
	 * @return a {@code List} with all {@code Property} instances added to this {@code JSONObject} instance
	 */
	public List<Property> getProperties() {
		return new ArrayList<>(this.properties);
	}
	
	/**
	 * Returns a {@code String} with the source code of this {@code JSONObject} instance.
	 * 
	 * @return a {@code String} with the source code of this {@code JSONObject} instance
	 */
	@Override
	public String toSourceCode() {
		return getProperties().stream().map(property -> property.toSourceCode()).collect(Collectors.joining(",", "{", "}"));
	}
	
	/**
	 * Returns a {@code String} representation of this {@code JSONObject} instance.
	 * 
	 * @return a {@code String} representation of this {@code JSONObject} instance
	 */
	@Override
	public String toString() {
		return "new JSONObject()";
	}
	
	/**
	 * Accepts a {@link NodeHierarchicalVisitor}.
	 * <p>
	 * Returns the result of {@code nodeHierarchicalVisitor.visitLeave(this)}.
	 * <p>
	 * If {@code nodeHierarchicalVisitor} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If a {@code RuntimeException} is thrown by the current {@code NodeHierarchicalVisitor}, a {@code NodeTraversalException} will be thrown with the {@code RuntimeException} wrapped.
	 * <p>
	 * This implementation will:
	 * <ul>
	 * <li>throw a {@code NullPointerException} if {@code nodeHierarchicalVisitor} is {@code null}.</li>
	 * <li>throw a {@code NodeTraversalException} if {@code nodeHierarchicalVisitor} throws a {@code RuntimeException}.</li>
	 * <li>traverse child {@code Node}s, if it has any.</li>
	 * </ul>
	 * 
	 * @param nodeHierarchicalVisitor the {@code NodeHierarchicalVisitor} to accept
	 * @return the result of {@code nodeHierarchicalVisitor.visitLeave(this)}
	 * @throws NodeTraversalException thrown if, and only if, a {@code RuntimeException} is thrown by the current {@code NodeHierarchicalVisitor}
	 * @throws NullPointerException thrown if, and only if, {@code nodeHierarchicalVisitor} is {@code null}
	 */
	@Override
	public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				for(final Property property : this.properties) {
					if(!property.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
				}
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Compares {@code object} to this {@code JSONObject} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code JSONObject}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code JSONObject} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code JSONObject}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof JSONObject)) {
			return false;
		} else if(!Objects.equals(getProperties(), JSONObject.class.cast(object).getProperties())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code JSONObject} instance.
	 * 
	 * @return a hash code for this {@code JSONObject} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getProperties());
	}
	
	/**
	 * Adds {@code property} to this {@code JSONObject} instance.
	 * <p>
	 * If {@code property} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param property the {@link Property} to add
	 * @throws NullPointerException thrown if, and only if, {@code property} is {@code null}
	 */
	public void addProperty(final Property property) {
		this.properties.add(Objects.requireNonNull(property, "property == null"));
	}
	
	/**
	 * Removes {@code property} from this {@code JSONObject} instance.
	 * <p>
	 * If {@code property} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param property the {@link Property} to remove
	 * @throws NullPointerException thrown if, and only if, {@code property} is {@code null}
	 */
	public void removeProperty(final Property property) {
		this.properties.remove(Objects.requireNonNull(property, "property == null"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * A {@code Property} denotes a property of a {@link JSONObject}.
	 * <p>
	 * This class is immutable and therefore thread-safe.
	 * 
	 * @since 1.0.0
	 * @author J&#246;rgen Lundgren
	 */
	public static final class Property implements Node {
		private final JSONType value;
		private final String key;
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		/**
		 * Constructs a new {@code Property} instance.
		 * <p>
		 * If either {@code key} or {@code value} are {@code null}, a {@code NullPointerException} will be thrown.
		 * 
		 * @param key the key to use
		 * @param value the value to use
		 * @throws NullPointerException thrown if, and only if, either {@code key} or {@code value} are {@code null}
		 */
		public Property(final String key, final JSONType value) {
			this.key = Objects.requireNonNull(key, "key == null");
			this.value = Objects.requireNonNull(value, "value == null");
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		/**
		 * Returns the {@link JSONType} value assigned to this {@code Property} instance.
		 * 
		 * @return the {@code JSONType} value assigned to this {@code Property} instance
		 */
		public JSONType getValue() {
			return this.value;
		}
		
		/**
		 * Returns the key assigned to this {@code Property} instance.
		 * 
		 * @return the key assigned to this {@code Property} instance
		 */
		public String getKey() {
			return this.key;
		}
		
		/**
		 * Returns a {@code String} with the source code of this {@code Property} instance.
		 * 
		 * @return a {@code String} with the source code of this {@code Property} instance
		 */
		public String toSourceCode() {
			return String.format("\"%s\":%s", getKey(), getValue().toSourceCode());
		}
		
		/**
		 * Returns a {@code String} representation of this {@code Property} instance.
		 * 
		 * @return a {@code String} representation of this {@code Property} instance
		 */
		@Override
		public String toString() {
			return String.format("new Property(\"%s\", %s)", getKey(), getValue());
		}
		
		/**
		 * Accepts a {@link NodeHierarchicalVisitor}.
		 * <p>
		 * Returns the result of {@code nodeHierarchicalVisitor.visitLeave(this)}.
		 * <p>
		 * If {@code nodeHierarchicalVisitor} is {@code null}, a {@code NullPointerException} will be thrown.
		 * <p>
		 * If a {@code RuntimeException} is thrown by the current {@code NodeHierarchicalVisitor}, a {@code NodeTraversalException} will be thrown with the {@code RuntimeException} wrapped.
		 * <p>
		 * This implementation will:
		 * <ul>
		 * <li>throw a {@code NullPointerException} if {@code nodeHierarchicalVisitor} is {@code null}.</li>
		 * <li>throw a {@code NodeTraversalException} if {@code nodeHierarchicalVisitor} throws a {@code RuntimeException}.</li>
		 * <li>traverse its child {@code Node}.</li>
		 * </ul>
		 * 
		 * @param nodeHierarchicalVisitor the {@code NodeHierarchicalVisitor} to accept
		 * @return the result of {@code nodeHierarchicalVisitor.visitLeave(this)}
		 * @throws NodeTraversalException thrown if, and only if, a {@code RuntimeException} is thrown by the current {@code NodeHierarchicalVisitor}
		 * @throws NullPointerException thrown if, and only if, {@code nodeHierarchicalVisitor} is {@code null}
		 */
		@Override
		public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
			Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
			
			try {
				if(nodeHierarchicalVisitor.visitEnter(this)) {
					if(!this.value.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
				}
				
				return nodeHierarchicalVisitor.visitLeave(this);
			} catch(final RuntimeException e) {
				throw new NodeTraversalException(e);
			}
		}
		
		/**
		 * Compares {@code object} to this {@code Property} instance for equality.
		 * <p>
		 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Property}, and their respective values are equal, {@code false} otherwise.
		 * 
		 * @param object the {@code Object} to compare to this {@code Property} instance for equality
		 * @return {@code true} if, and only if, {@code object} is an instance of {@code Property}, and their respective values are equal, {@code false} otherwise
		 */
		@Override
		public boolean equals(final Object object) {
			if(object == this) {
				return true;
			} else if(!(object instanceof Property)) {
				return false;
			} else if(!Objects.equals(getKey(), Property.class.cast(object).getKey())) {
				return false;
			} else if(!Objects.equals(getValue(), Property.class.cast(object).getValue())) {
				return false;
			} else {
				return true;
			}
		}
		
		/**
		 * Returns a hash code for this {@code Property} instance.
		 * 
		 * @return a hash code for this {@code Property} instance
		 */
		@Override
		public int hashCode() {
			return Objects.hash(getKey(), getValue());
		}
	}
}