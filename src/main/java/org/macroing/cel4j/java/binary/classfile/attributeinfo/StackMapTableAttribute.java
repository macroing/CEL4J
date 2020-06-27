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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;

/**
 * A {@code StackMapTableAttribute} represents a {@code StackMapTable_attribute} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code StackMapTable_attribute} structure has the following format:
 * <pre>
 * <code>
 * StackMapTable_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 number_of_entries;
 *     stack_map_frame[number_of_entries] entries;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class StackMapTableAttribute extends AttributeInfo {
	/**
	 * The name of the {@code StackMapTable_attribute} structure.
	 */
	public static final String NAME = "StackMapTable";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<StackMapFrame> entries;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code StackMapTableAttribute} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the value for the {@code attribute_name_index} item associated with this {@code StackMapTableAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than {@code 1}
	 */
	public StackMapTableAttribute(final int attributeNameIndex) {
		super(NAME, attributeNameIndex);
		
		this.entries = new ArrayList<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all currently added {@link StackMapFrame} instances.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code StackMapTableAttribute} instance.
	 * 
	 * @return a {@code List} with all currently added {@code StackMapFrame} instances
	 */
	public List<StackMapFrame> getEntries() {
		return new ArrayList<>(this.entries);
	}
	
	/**
	 * Returns a copy of this {@code StackMapTableAttribute} instance.
	 * 
	 * @return a copy of this {@code StackMapTableAttribute} instance
	 */
	@Override
	public StackMapTableAttribute copy() {
		final StackMapTableAttribute stackMapTableAttribute = new StackMapTableAttribute(getAttributeNameIndex());
		
		for(final StackMapFrame entry : this.entries) {
			stackMapTableAttribute.addEntry(entry.copy());
		}
		
		return stackMapTableAttribute;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code StackMapTableAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code StackMapTableAttribute} instance
	 */
	@Override
	public String toString() {
		return String.format("new StackMapTableAttribute(%s)", Integer.toString(getAttributeNameIndex()));
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
	 * <li>traverse its child {@code Node} instances, if it has any.</li>
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
				for(final StackMapFrame entry : this.entries) {
					if(!entry.accept(nodeHierarchicalVisitor)) {
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
	 * Compares {@code object} to this {@code StackMapTableAttribute} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code StackMapTableAttribute}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code StackMapTableAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code StackMapTableAttribute}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof StackMapTableAttribute)) {
			return false;
		} else if(!Objects.equals(getName(), StackMapTableAttribute.class.cast(object).getName())) {
			return false;
		} else if(getAttributeNameIndex() != StackMapTableAttribute.class.cast(object).getAttributeNameIndex()) {
			return false;
		} else if(getAttributeLength() != StackMapTableAttribute.class.cast(object).getAttributeLength()) {
			return false;
		} else if(!Objects.equals(this.entries, StackMapTableAttribute.class.cast(object).entries)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code attribute_length} item associated with this {@code StackMapTableAttribute} instance.
	 * 
	 * @return the value of the {@code attribute_length} item associated with this {@code StackMapTableAttribute} instance
	 */
	@Override
	public int getAttributeLength() {
		int attributeLength = 2;
		
		for(final StackMapFrame entry : this.entries) {
			attributeLength += entry.getLength();
		}
		
		return attributeLength;
	}
	
	/**
	 * Returns the value of the {@code number_of_entries} item associated with this {@code StackMapTableAttribute} instance.
	 * 
	 * @return the value of the {@code number_of_entries} item associated with this {@code StackMapTableAttribute} instance
	 */
	public int getNumberOfEntries() {
		return this.entries.size();
	}
	
	/**
	 * Returns a hash code for this {@code StackMapTableAttribute} instance.
	 * 
	 * @return a hash code for this {@code StackMapTableAttribute} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getAttributeNameIndex()), Integer.valueOf(getAttributeLength()), this.entries);
	}
	
	/**
	 * Adds {@code entry} to this {@code StackMapTableAttribute} instance.
	 * <p>
	 * If {@code entry} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param entry the {@link StackMapFrame} to add
	 * @throws NullPointerException thrown if, and only if, {@code entry} is {@code null}
	 */
	public void addEntry(final StackMapFrame entry) {
		this.entries.add(Objects.requireNonNull(entry, "entry == null"));
	}
	
	/**
	 * Removes {@code entry} from this {@code StackMapTableAttribute} instance, if present.
	 * <p>
	 * If {@code entry} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param entry the {@link StackMapFrame} to remove
	 * @throws NullPointerException thrown if, and only if, {@code entry} is {@code null}
	 */
	public void removeEntry(final StackMapFrame entry) {
		this.entries.remove(Objects.requireNonNull(entry, "entry == null"));
	}
	
	/**
	 * Writes this {@code StackMapTableAttribute} to {@code dataOutput}.
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
			dataOutput.writeShort(getNumberOfEntries());
			
			for(final StackMapFrame entry : this.entries) {
				entry.write(dataOutput);
			}
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code StackMapTableAttribute} instances in {@code node}.
	 * <p>
	 * All {@code StackMapTableAttribute} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code StackMapTableAttribute} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<StackMapTableAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), StackMapTableAttribute.class);
	}
}