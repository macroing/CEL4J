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
package org.macroing.cel4j.java.binary.classfile.attributeinfo;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.util.Objects;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;

//TODO: Add Javadocs!
public final class ElementValue implements Node {
//	TODO: Add Javadocs!
	public static final char ANNOTATION_TYPE_TAG = '@';
	
//	TODO: Add Javadocs!
	public static final char ARRAY_TAG = '[';
	
//	TODO: Add Javadocs!
	public static final char BOOLEAN_TAG = 'Z';
	
//	TODO: Add Javadocs!
	public static final char BYTE_TAG = 'B';
	
//	TODO: Add Javadocs!
	public static final char CHAR_TAG = 'C';
	
//	TODO: Add Javadocs!
	public static final char CLASS_TAG = 'c';
	
//	TODO: Add Javadocs!
	public static final char DOUBLE_TAG = 'D';
	
//	TODO: Add Javadocs!
	public static final char ENUM_CONSTANT_TAG = 'e';
	
//	TODO: Add Javadocs!
	public static final char FLOAT_TAG = 'F';
	
//	TODO: Add Javadocs!
	public static final char INT_TAG = 'I';
	
//	TODO: Add Javadocs!
	public static final char LONG_TAG = 'J';
	
//	TODO: Add Javadocs!
	public static final char SHORT_TAG = 'S';
	
//	TODO: Add Javadocs!
	public static final char STRING_TAG = 's';
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final Union value;
	private final int tag;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private ElementValue(final int tag, final Union value) {
		this.tag = tag;
		this.value = value;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public ElementValue copy() {
		return new ElementValue(this.tag, this.value.copy());
	}
	
//	TODO: Add Javadocs!
	public Union getValue() {
		return this.value;
	}
	
//	TODO: Add Javadocs!
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
	
//	TODO: Add Javadocs!
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ElementValue)) {
			return false;
		} else if(!Objects.equals(Integer.valueOf(this.tag), Integer.valueOf(ElementValue.class.cast(object).tag))) {
			return false;
		} else if(!Objects.equals(this.value, ElementValue.class.cast(object).value)) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	public int getLength() {
		return 1 + this.value.getLength();
	}
	
//	TODO: Add Javadocs!
	public int getTag() {
		return this.tag;
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(this.tag), this.value);
	}
	
//	TODO: Add Javadocs!
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeByte(this.tag);
			
			this.value.write(dataOutput);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static ElementValue newInstance(final int tag, final Union value) {
		return new ElementValue(doRequireValidTag(tag), doRequireValidValue(tag, value));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static Union doRequireValidValue(final int tag, final Union value) {
		switch(tag) {
			case ANNOTATION_TYPE_TAG:
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
			case ENUM_CONSTANT_TAG:
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
			case ANNOTATION_TYPE_TAG:
			case ARRAY_TAG:
			case BOOLEAN_TAG:
			case BYTE_TAG:
			case CHAR_TAG:
			case CLASS_TAG:
			case DOUBLE_TAG:
			case ENUM_CONSTANT_TAG:
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