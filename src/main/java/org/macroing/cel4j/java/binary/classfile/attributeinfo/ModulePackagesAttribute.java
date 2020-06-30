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
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code ModulePackagesAttribute} represents a {@code ModulePackages_attribute} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code ModulePackages_attribute} structure has the following format:
 * <pre>
 * <code>
 * ModulePackages_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 package_count;
 *     u2[package_count] package_index;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class ModulePackagesAttribute extends AttributeInfo {
	/**
	 * The name of the {@code ModulePackages_attribute} structure.
	 */
	public static final String NAME = "ModulePackages";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<Integer> packageIndexTable;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code ModulePackagesAttribute} instance that is a copy of {@code modulePackagesAttribute}.
	 * <p>
	 * If {@code modulePackagesAttribute} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param modulePackagesAttribute the {@code ModulePackagesAttribute} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code modulePackagesAttribute} is {@code null}
	 */
	public ModulePackagesAttribute(final ModulePackagesAttribute modulePackagesAttribute) {
		super(NAME, modulePackagesAttribute.getAttributeNameIndex());
		
		this.packageIndexTable = modulePackagesAttribute.packageIndexTable.stream().collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	/**
	 * Constructs a new {@code ModulePackagesAttribute} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the value for the {@code attribute_name_index} item associated with this {@code ModulePackagesAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than {@code 1}
	 */
	public ModulePackagesAttribute(final int attributeNameIndex) {
		super(NAME, attributeNameIndex);
		
		this.packageIndexTable = new ArrayList<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code ModulePackagesAttribute} instance.
	 * 
	 * @return a copy of this {@code ModulePackagesAttribute} instance
	 */
	@Override
	public ModulePackagesAttribute copy() {
		return new ModulePackagesAttribute(this);
	}
	
	/**
	 * Returns a {@code List} that represents the {@code package_index} table item associated with this {@code ModulePackagesAttribute} instance.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code ModulePackagesAttribute} instance.
	 * 
	 * @return a {@code List} that represents the {@code package_index} table item associated with this {@code ModulePackagesAttribute} instance
	 */
	public List<Integer> getPackageIndexTable() {
		return new ArrayList<>(this.packageIndexTable);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code ModulePackagesAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code ModulePackagesAttribute} instance
	 */
	@Override
	public String toString() {
		return String.format("new ModulePackagesAttribute(%s)", Integer.toString(getAttributeNameIndex()));
	}
	
	/**
	 * Compares {@code object} to this {@code ModulePackagesAttribute} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code ModulePackagesAttribute}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code ModulePackagesAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code ModulePackagesAttribute}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof ModulePackagesAttribute)) {
			return false;
		} else if(!Objects.equals(getName(), ModulePackagesAttribute.class.cast(object).getName())) {
			return false;
		} else if(getAttributeNameIndex() != ModulePackagesAttribute.class.cast(object).getAttributeNameIndex()) {
			return false;
		} else if(getAttributeLength() != ModulePackagesAttribute.class.cast(object).getAttributeLength()) {
			return false;
		} else if(getPackageCount() != ModulePackagesAttribute.class.cast(object).getPackageCount()) {
			return false;
		} else if(!Objects.equals(this.packageIndexTable, ModulePackagesAttribute.class.cast(object).packageIndexTable)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code attribute_length} item associated with this {@code ModulePackagesAttribute} instance.
	 * 
	 * @return the value of the {@code attribute_length} item associated with this {@code ModulePackagesAttribute} instance
	 */
	@Override
	public int getAttributeLength() {
		return 2 + getPackageCount() * 2;
	}
	
	/**
	 * Returns the value of the {@code package_count} item associated with this {@code ModulePackagesAttribute} instance.
	 * 
	 * @return the value of the {@code package_count} item associated with this {@code ModulePackagesAttribute} instance
	 */
	public int getPackageCount() {
		return this.packageIndexTable.size();
	}
	
	/**
	 * Returns a hash code for this {@code ModulePackagesAttribute} instance.
	 * 
	 * @return a hash code for this {@code ModulePackagesAttribute} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getAttributeNameIndex()), Integer.valueOf(getAttributeLength()), Integer.valueOf(getPackageCount()), this.packageIndexTable);
	}
	
	/**
	 * Adds {@code packageIndex} to the {@code package_index} table item associated with this {@code ModulePackagesAttribute} instance.
	 * <p>
	 * If {@code packageIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param packageIndex the package index to add
	 * @throws IllegalArgumentException thrown if, and only if, {@code packageIndex} is less than {@code 1}
	 */
	public void addPackageIndex(final int packageIndex) {
		this.packageIndexTable.add(Integer.valueOf(ParameterArguments.requireRange(packageIndex, 1, Integer.MAX_VALUE, "packageIndex")));
	}
	
	/**
	 * Removes {@code packageIndex} from the {@code package_index} table item associated with this {@code ModulePackagesAttribute} instance.
	 * <p>
	 * If {@code packageIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param packageIndex the package index to remove
	 * @throws IllegalArgumentException thrown if, and only if, {@code packageIndex} is less than {@code 1}
	 */
	public void removePackageIndex(final int packageIndex) {
		this.packageIndexTable.remove(Integer.valueOf(ParameterArguments.requireRange(packageIndex, 1, Integer.MAX_VALUE, "packageIndex")));
	}
	
	/**
	 * Writes this {@code ModulePackagesAttribute} to {@code dataOutput}.
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
			dataOutput.writeShort(getPackageCount());
			
			for(final int packageIndex : this.packageIndexTable) {
				dataOutput.writeShort(packageIndex);
			}
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code ModulePackagesAttribute} instances in {@code node}.
	 * <p>
	 * All {@code ModulePackagesAttribute} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code ModulePackagesAttribute} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<ModulePackagesAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), ModulePackagesAttribute.class);
	}
}