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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code NestMembersAttribute} represents a {@code NestMembers_attribute} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code NestMembers_attribute} structure has the following format:
 * <pre>
 * <code>
 * NestMembers_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 number_of_classes;
 *     u2[number_of_classes] classes;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class NestMembersAttribute extends AttributeInfo {
	/**
	 * The name of the {@code NestMembers_attribute} structure.
	 */
	public static final String NAME = "NestMembers";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<Integer> classes;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code NestMembersAttribute} instance that is a copy of {@code nestMembersAttribute}.
	 * <p>
	 * If {@code nestMembersAttribute} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param nestMembersAttribute the {@code NestMembersAttribute} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code nestMembersAttribute} is {@code null}
	 */
	public NestMembersAttribute(final NestMembersAttribute nestMembersAttribute) {
		super(NAME, nestMembersAttribute.getAttributeNameIndex());
		
		this.classes = nestMembersAttribute.classes.stream().collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	/**
	 * Constructs a new {@code NestMembersAttribute} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the value for the {@code attribute_name_index} item associated with this {@code NestMembersAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than {@code 1}
	 */
	public NestMembersAttribute(final int attributeNameIndex) {
		super(NAME, attributeNameIndex);
		
		this.classes = new ArrayList<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code NestMembersAttribute} instance.
	 * 
	 * @return a copy of this {@code NestMembersAttribute} instance
	 */
	@Override
	public NestMembersAttribute copy() {
		return new NestMembersAttribute(this);
	}
	
	/**
	 * Returns a {@code List} that represents the {@code classes} table item associated with this {@code NestMembersAttribute} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code ExceptionsAttribute} instance.
	 * 
	 * @return a {@code List} that represents the {@code classes} table item associated with this {@code NestMembersAttribute} instance
	 */
	public List<Integer> getClasses() {
		return new ArrayList<>(this.classes);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code NestMembersAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code NestMembersAttribute} instance
	 */
	@Override
	public String toString() {
		return String.format("new NestMembersAttribute(%s)", Integer.toString(getAttributeNameIndex()));
	}
	
	/**
	 * Compares {@code object} to this {@code NestMembersAttribute} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code NestMembersAttribute}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code NestMembersAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code NestMembersAttribute}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof NestMembersAttribute)) {
			return false;
		} else if(!Objects.equals(getName(), NestMembersAttribute.class.cast(object).getName())) {
			return false;
		} else if(getAttributeNameIndex() != NestMembersAttribute.class.cast(object).getAttributeNameIndex()) {
			return false;
		} else if(getAttributeLength() != NestMembersAttribute.class.cast(object).getAttributeLength()) {
			return false;
		} else if(getNumberOfClasses() != NestMembersAttribute.class.cast(object).getNumberOfClasses()) {
			return false;
		} else if(!Objects.equals(this.classes, NestMembersAttribute.class.cast(object).classes)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code attribute_length} item associated with this {@code NestMembersAttribute} instance.
	 * 
	 * @return the value of the {@code attribute_length} item associated with this {@code NestMembersAttribute} instance
	 */
	@Override
	public int getAttributeLength() {
		return 2 + getNumberOfClasses() * 2;
	}
	
	/**
	 * Returns the value of the {@code number_of_classes} item associated with this {@code NestMembersAttribute} instance.
	 * 
	 * @return the value of the {@code number_of_classes} item associated with this {@code NestMembersAttribute} instance
	 */
	public int getNumberOfClasses() {
		return this.classes.size();
	}
	
	/**
	 * Returns a hash code for this {@code NestMembersAttribute} instance.
	 * 
	 * @return a hash code for this {@code NestMembersAttribute} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getAttributeNameIndex()), Integer.valueOf(getAttributeLength()), Integer.valueOf(getNumberOfClasses()), this.classes);
	}
	
	/**
	 * Adds {@code index} to the {@code classes} table item associated with this {@code NestMembersAttribute} instance.
	 * <p>
	 * If {@code index} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param index the index to add
	 * @throws IllegalArgumentException thrown if, and only if, {@code index} is less than {@code 1}
	 */
	public void addClass(final int index) {
		this.classes.add(Integer.valueOf(ParameterArguments.requireRange(index, 1, Integer.MAX_VALUE, "index")));
	}
	
	/**
	 * Removes {@code index} from the {@code classes} table item associated with this {@code NestMembersAttribute} instance.
	 * <p>
	 * If {@code index} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param index the index to remove
	 * @throws IllegalArgumentException thrown if, and only if, {@code index} is less than {@code 1}
	 */
	public void removeClass(final int index) {
		this.classes.remove(Integer.valueOf(ParameterArguments.requireRange(index, 1, Integer.MAX_VALUE, "index")));
	}
	
	/**
	 * Writes this {@code NestMembersAttribute} to {@code dataOutput}.
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
			dataOutput.writeShort(getAttributeNameIndex());
			dataOutput.writeInt(getAttributeLength());
			dataOutput.writeShort(getNumberOfClasses());
			
			for(final int index : this.classes) {
				dataOutput.writeShort(index);
			}
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code NestMembersAttribute} instances in {@code node}.
	 * <p>
	 * All {@code NestMembersAttribute} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code NestMembersAttribute} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<NestMembersAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), NestMembersAttribute.class);
	}
}