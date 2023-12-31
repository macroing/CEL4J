/**
 * Copyright 2009 - 2024 J&#246;rgen Lundgren
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

/**
 * A {@code BootstrapMethodsAttribute} represents a {@code BootstrapMethods_attribute} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code BootstrapMethods_attribute} structure has the following format:
 * <pre>
 * <code>
 * BootstrapMethods_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 num_bootstrap_methods;
 *     bootstrap_method[num_bootstrap_methods] bootstrap_methods;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class BootstrapMethodsAttribute extends AttributeInfo {
	/**
	 * The name of the {@code BootstrapMethods_attribute} structure.
	 */
	public static final String NAME = "BootstrapMethods";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<BootstrapMethod> bootstrapMethods;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code BootstrapMethodsAttribute} instance that is a copy of {@code bootstrapMethodsAttribute}.
	 * <p>
	 * If {@code bootstrapMethodsAttribute} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param bootstrapMethodsAttribute the {@code BootstrapMethodsAttribute} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code bootstrapMethodsAttribute} is {@code null}
	 */
	public BootstrapMethodsAttribute(final BootstrapMethodsAttribute bootstrapMethodsAttribute) {
		super(NAME, bootstrapMethodsAttribute.getAttributeNameIndex());
		
		this.bootstrapMethods = bootstrapMethodsAttribute.bootstrapMethods.stream().collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	/**
	 * Constructs a new {@code BootstrapMethodsAttribute} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the value for the {@code attribute_name_index} item associated with this {@code BootstrapMethodsAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than {@code 1}
	 */
	public BootstrapMethodsAttribute(final int attributeNameIndex) {
		super(NAME, attributeNameIndex);
		
		this.bootstrapMethods = new ArrayList<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code BootstrapMethodsAttribute} instance.
	 * 
	 * @return a copy of this {@code BootstrapMethodsAttribute} instance
	 */
	@Override
	public BootstrapMethodsAttribute copy() {
		return new BootstrapMethodsAttribute(this);
	}
	
	/**
	 * Returns a {@code List} that represents the {@code bootstrap_methods} table item associated with this {@code BootstrapMethodsAttribute} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code BootstrapMethodsAttribute} instance.
	 * 
	 * @return a {@code List} that represents the {@code bootstrap_methods} table item associated with this {@code BootstrapMethodsAttribute} instance
	 */
	public List<BootstrapMethod> getBootstrapMethods() {
		return new ArrayList<>(this.bootstrapMethods);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code BootstrapMethodsAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code BootstrapMethodsAttribute} instance
	 */
	@Override
	public String toString() {
		return String.format("new BootstrapMethodsAttribute(%s)", Integer.toString(getAttributeNameIndex()));
	}
	
	/**
	 * Compares {@code object} to this {@code BootstrapMethodsAttribute} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code BootstrapMethodsAttribute}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code BootstrapMethodsAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code BootstrapMethodsAttribute}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof BootstrapMethodsAttribute)) {
			return false;
		} else if(!Objects.equals(getName(), BootstrapMethodsAttribute.class.cast(object).getName())) {
			return false;
		} else if(getAttributeNameIndex() != BootstrapMethodsAttribute.class.cast(object).getAttributeNameIndex()) {
			return false;
		} else if(getAttributeLength() != BootstrapMethodsAttribute.class.cast(object).getAttributeLength()) {
			return false;
		} else if(getNumBootstrapMethods() != BootstrapMethodsAttribute.class.cast(object).getNumBootstrapMethods()) {
			return false;
		} else if(!Objects.equals(this.bootstrapMethods, BootstrapMethodsAttribute.class.cast(object).bootstrapMethods)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code attribute_length} item associated with this {@code BootstrapMethodsAttribute} instance.
	 * 
	 * @return the value of the {@code attribute_length} item associated with this {@code BootstrapMethodsAttribute} instance
	 */
	@Override
	public int getAttributeLength() {
		int attributeLength = 2;
		
		for(final BootstrapMethod bootstrapMethod : this.bootstrapMethods) {
			attributeLength += bootstrapMethod.getLength();
		}
		
		return attributeLength;
	}
	
	/**
	 * Returns the value of the {@code num_bootstrap_methods} item associated with this {@code BootstrapMethodsAttribute} instance.
	 * 
	 * @return the value of the {@code num_bootstrap_methods} item associated with this {@code BootstrapMethodsAttribute} instance
	 */
	public int getNumBootstrapMethods() {
		return this.bootstrapMethods.size();
	}
	
	/**
	 * Returns a hash code for this {@code BootstrapMethodsAttribute} instance.
	 * 
	 * @return a hash code for this {@code BootstrapMethodsAttribute} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getAttributeNameIndex()), Integer.valueOf(getAttributeLength()), Integer.valueOf(getNumBootstrapMethods()), this.bootstrapMethods);
	}
	
	/**
	 * Adds {@code bootstrapMethod} to the {@code bootstrap_methods} table item associated with this {@code BootstrapMethodsAttribute} instance.
	 * <p>
	 * If {@code bootstrapMethod} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param bootstrapMethod the {@link BootstrapMethod} to add
	 * @throws NullPointerException thrown if, and only if, {@code bootstrapMethod} is {@code null}
	 */
	public void addBootstrapMethod(final BootstrapMethod bootstrapMethod) {
		this.bootstrapMethods.add(Objects.requireNonNull(bootstrapMethod, "bootstrapMethod == null"));
	}
	
	/**
	 * Removes {@code bootstrapMethod} from the {@code bootstrap_methods} table item associated with this {@code BootstrapMethodsAttribute} instance.
	 * <p>
	 * If {@code bootstrapMethod} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param bootstrapMethod the {@link BootstrapMethod} to remove
	 * @throws NullPointerException thrown if, and only if, {@code bootstrapMethod} is {@code null}
	 */
	public void removeBootstrapMethod(final BootstrapMethod bootstrapMethod) {
		this.bootstrapMethods.remove(Objects.requireNonNull(bootstrapMethod, "bootstrapMethod == null"));
	}
	
	/**
	 * Writes this {@code BootstrapMethodsAttribute} to {@code dataOutput}.
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
			dataOutput.writeShort(getNumBootstrapMethods());
			
			for(final BootstrapMethod bootstrapMethod : this.bootstrapMethods) {
				bootstrapMethod.write(dataOutput);
			}
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code BootstrapMethodsAttribute} instances in {@code node}.
	 * <p>
	 * All {@code BootstrapMethodsAttribute} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code BootstrapMethodsAttribute} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<BootstrapMethodsAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), BootstrapMethodsAttribute.class);
	}
}