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
package org.macroing.cel4j.java.binary.classfile.attributeinfo;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;

/**
 * An {@code ElementValue} represents an {@code element_value} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is indirectly mutable and not thread-safe.
 * <p>
 * The {@code element_value} structure has the following format:
 * <pre>
 * <code>
 * element_value {
 *     u1 tag;
 *     union value;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ElementValue implements Node {
	/**
	 * A tag that defines an annotation.
	 */
	public static final char ANNOTATION_TAG = '@';
	
	/**
	 * A tag that defines an array.
	 */
	public static final char ARRAY_TAG = '[';
	
	/**
	 * A tag that defines a {@code boolean}.
	 */
	public static final char BOOLEAN_TAG = 'Z';
	
	/**
	 * A tag that defines a {@code byte}.
	 */
	public static final char BYTE_TAG = 'B';
	
	/**
	 * A tag that defines a {@code char}.
	 */
	public static final char CHAR_TAG = 'C';
	
	/**
	 * A tag that defines a class.
	 */
	public static final char CLASS_TAG = 'c';
	
	/**
	 * A tag that defines a {@code double}.
	 */
	public static final char DOUBLE_TAG = 'D';
	
	/**
	 * A tag that defines an enum.
	 */
	public static final char ENUM_TAG = 'e';
	
	/**
	 * A tag that defines a {@code float}.
	 */
	public static final char FLOAT_TAG = 'F';
	
	/**
	 * A tag that defines an {@code int}.
	 */
	public static final char INT_TAG = 'I';
	
	/**
	 * A tag that defines a {@code long}.
	 */
	public static final char LONG_TAG = 'J';
	
	/**
	 * A tag that defines a {@code short}.
	 */
	public static final char SHORT_TAG = 'S';
	
	/**
	 * A tag that defines a {@code String}.
	 */
	public static final char STRING_TAG = 's';
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Union value;
	private final int tag;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ElementValue} instance given {@code tag} and {@code value}.
	 * <p>
	 * If {@code value} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code tag} is invalid, or the combination of {@code tag} and {@code value} is, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param tag the value for the {@code tag} item associated with this {@code ElementValue} instance
	 * @param value the value for the {@code value} item associated with this {@code ElementValue} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code tag} is invalid, or the combination of {@code tag} and {@code value} is
	 * @throws NullPointerException thrown if, and only if, {@code value} is {@code null}
	 */
	public ElementValue(final int tag, final Union value) {
		this.tag = doRequireValidTag(tag);
		this.value = doRequireValidValue(tag, Objects.requireNonNull(value, "value == null"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ElementValue} instance.
	 * 
	 * @return a copy of this {@code ElementValue} instance
	 */
	public ElementValue copy() {
		return new ElementValue(getTag(), getValue().copy());
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ElementValue} instance.
	 * 
	 * @return a {@code String} representation of this {@code ElementValue} instance
	 */
	@Override
	public String toString() {
		return String.format("new ElementValue(%s, %s)", Integer.toString(getTag()), getValue());
	}
	
	/**
	 * Returns the value of the {@code value} item associated with this {@code ElementValue} instance.
	 * 
	 * @return the value of the {@code value} item associated with this {@code ElementValue} instance
	 */
	public Union getValue() {
		return this.value;
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
	 * <li>traverse its child {@code Node}, a {@link Union}.</li>
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
				if(!getValue().accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
				}
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Compares {@code object} to this {@code ElementValue} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ElementValue}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ElementValue} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ElementValue}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ElementValue)) {
			return false;
		} else if(getTag() != ElementValue.class.cast(object).getTag()) {
			return false;
		} else if(!Objects.equals(getValue(), ElementValue.class.cast(object).getValue())) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the length of this {@code ElementValue} instance.
	 * 
	 * @return the length of this {@code ElementValue} instance
	 */
	public int getLength() {
		return 1 + getValue().getLength();
	}
	
	/**
	 * Returns the value of the {@code tag} item associated with this {@code ElementValue} instance.
	 * 
	 * @return the value of the {@code tag} item associated with this {@code ElementValue} instance
	 */
	public int getTag() {
		return this.tag;
	}
	
	/**
	 * Returns a hash code for this {@code ElementValue} instance.
	 * 
	 * @return a hash code for this {@code ElementValue} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(getTag()), getValue());
	}
	
	/**
	 * Writes this {@code ElementValue} to {@code dataOutput}.
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
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeByte(getTag());
			
			final
			Union union = getValue();
			union.write(dataOutput);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static Union doRequireValidValue(final int tag, final Union value) {
		switch(tag) {
			case ANNOTATION_TAG:
				return doRequireValidType(value, AnnotationValueUnion.class);
			case ARRAY_TAG:
				return doRequireValidType(value, ArrayValueUnion.class);
			case BOOLEAN_TAG:
			case BYTE_TAG:
			case CHAR_TAG:
				return doRequireValidType(value, ConstValueIndexUnion.class);
			case CLASS_TAG:
				return doRequireValidType(value, ClassInfoIndexUnion.class);
			case DOUBLE_TAG:
				return doRequireValidType(value, ConstValueIndexUnion.class);
			case ENUM_TAG:
				return doRequireValidType(value, EnumConstValueUnion.class);
			case FLOAT_TAG:
			case INT_TAG:
			case LONG_TAG:
			case SHORT_TAG:
			case STRING_TAG:
				return doRequireValidType(value, ConstValueIndexUnion.class);
			default:
				throw new IllegalArgumentException(String.format("The value is invalid: %s", value));
		}
	}
	
	private static <T> T doRequireValidType(final T value, final Class<?> clazz) {
		if(clazz.isInstance(value)) {
			return value;
		}
		
		throw new IllegalArgumentException(String.format("The value is invalid: %s", value));
	}
	
	private static int doRequireValidTag(final int tag) {
		switch(tag) {
			case ANNOTATION_TAG:
			case ARRAY_TAG:
			case BOOLEAN_TAG:
			case BYTE_TAG:
			case CHAR_TAG:
			case CLASS_TAG:
			case DOUBLE_TAG:
			case ENUM_TAG:
			case FLOAT_TAG:
			case INT_TAG:
			case LONG_TAG:
			case SHORT_TAG:
			case STRING_TAG:
				return tag;
			default:
				throw new IllegalArgumentException(String.format("The tag is invalid: %s", Integer.toString(tag)));
		}
	}
}