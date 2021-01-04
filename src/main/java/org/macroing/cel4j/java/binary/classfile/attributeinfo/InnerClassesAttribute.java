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
package org.macroing.cel4j.java.binary.classfile.attributeinfo;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;

/**
 * An {@code InnerClassesAttribute} represents an {@code InnerClasses_attribute} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code InnerClasses_attribute} structure has the following format:
 * <pre>
 * <code>
 * InnerClasses_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 number_of_classes;
 *     class[number_of_classes] classes;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class InnerClassesAttribute extends AttributeInfo {
	/**
	 * The name of the {@code InnerClasses_attribute} structure.
	 */
	public static final String NAME = "InnerClasses";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<InnerClass> innerClasses;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code InnerClassesAttribute} instance that is a copy of {@code innerClassesAttribute}.
	 * <p>
	 * If {@code innerClassesAttribute} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param innerClassesAttribute the {@code InnerClassesAttribute} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code innerClassesAttribute} is {@code null}
	 */
	public InnerClassesAttribute(final InnerClassesAttribute innerClassesAttribute) {
		super(NAME, innerClassesAttribute.getAttributeNameIndex());
		
		this.innerClasses = innerClassesAttribute.innerClasses.stream().map(innerClass -> innerClass.copy()).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	/**
	 * Constructs a new {@code InnerClassesAttribute} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the value for the {@code attribute_name_index} item associated with this {@code InnerClassesAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than {@code 1}
	 */
	public InnerClassesAttribute(final int attributeNameIndex) {
		super(NAME, attributeNameIndex);
		
		this.innerClasses = new ArrayList<>();
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code InnerClassesAttribute} instance.
	 * 
	 * @return a copy of this {@code InnerClassesAttribute} instance
	 */
	@Override
	public InnerClassesAttribute copy() {
		return new InnerClassesAttribute(this);
	}
	
	/**
	 * Returns a {@code List} with all currently added {@code InnerClass} instances.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code InnerClassesAttribute} instance.
	 * 
	 * @return a {@code List} with all currently added {@code InnerClass} instances
	 */
	public List<InnerClass> getInnerClasses() {
		return new ArrayList<>(this.innerClasses);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code InnerClassesAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code InnerClassesAttribute} instance
	 */
	@Override
	public String toString() {
		return String.format("new InnerClassesAttribute(%s)", Integer.toString(getAttributeNameIndex()));
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
				for(final InnerClass innerClass : this.innerClasses) {
					if(!innerClass.accept(nodeHierarchicalVisitor)) {
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
	 * Compares {@code object} to this {@code InnerClassesAttribute} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code InnerClassesAttribute}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code InnerClassesAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code InnerClassesAttribute}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof InnerClassesAttribute)) {
			return false;
		} else if(!Objects.equals(getName(), InnerClassesAttribute.class.cast(object).getName())) {
			return false;
		} else if(getAttributeNameIndex() != InnerClassesAttribute.class.cast(object).getAttributeNameIndex()) {
			return false;
		} else if(getAttributeLength() != InnerClassesAttribute.class.cast(object).getAttributeLength()) {
			return false;
		} else if(!Objects.equals(this.innerClasses, InnerClassesAttribute.class.cast(object).innerClasses)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the value of the {@code attribute_length} item associated with this {@code InnerClassesAttribute} instance.
	 * 
	 * @return the value of the {@code attribute_length} item associated with this {@code InnerClassesAttribute} instance
	 */
	@Override
	public int getAttributeLength() {
		int attributeLength = 0;
		
		attributeLength += 2;
		attributeLength += getNumberOfClasses() * 8;
		
		return attributeLength;
	}
	
	/**
	 * Returns the value of the {@code number_of_classes} item associated with this {@code InnerClassesAttribute} instance.
	 * 
	 * @return the value of the {@code number_of_classes} item associated with this {@code InnerClassesAttribute} instance
	 */
	public int getNumberOfClasses() {
		return this.innerClasses.size();
	}
	
	/**
	 * Returns a hash code for this {@code InnerClassesAttribute} instance.
	 * 
	 * @return a hash code for this {@code InnerClassesAttribute} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getAttributeNameIndex()), Integer.valueOf(getAttributeLength()), this.innerClasses);
	}
	
	/**
	 * Adds {@code innerClass} to this {@code InnerClassesAttribute} instance, if absent.
	 * <p>
	 * If {@code innerClass} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param innerClass the {@link InnerClass} to add
	 * @throws NullPointerException thrown if, and only if, {@code innerClass} is {@code null}
	 */
	public void addInnerClass(final InnerClass innerClass) {
		if(!this.innerClasses.contains(Objects.requireNonNull(innerClass, "innerClass == null"))) {
			this.innerClasses.add(innerClass);
		}
	}
	
	/**
	 * Removes {@code innerClass} from this {@code InnerClassesAttribute} instance, if present.
	 * <p>
	 * If {@code innerClass} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param innerClass the {@link InnerClass} to remove
	 * @throws NullPointerException thrown if, and only if, {@code innerClass} is {@code null}
	 */
	public void removeInnerClass(final InnerClass innerClass) {
		this.innerClasses.remove(Objects.requireNonNull(innerClass, "innerClass == null"));
	}
	
	/**
	 * Writes this {@code InnerClassesAttribute} to {@code dataOutput}.
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
			
			for(final InnerClass innerClass : this.innerClasses) {
				innerClass.write(dataOutput);
			}
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code InnerClassesAttribute} instances in {@code node}.
	 * <p>
	 * All {@code InnerClassesAttribute} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code InnerClassesAttribute} instances in {@code node}
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<InnerClassesAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), InnerClassesAttribute.class);
	}
	
	/**
	 * Attempts to find an {@code InnerClassesAttribute} instance in {@code classFile}.
	 * <p>
	 * Returns an {@code Optional} with the optional {@code InnerClassesAttribute} instance.
	 * <p>
	 * If {@code classFile} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param classFile the {@link ClassFile} to check in
	 * @return an {@code Optional} with the optional {@code InnerClassesAttribute} instance
	 * @throws NullPointerException thrown if, and only if, {@code classFile} is {@code null}
	 */
	public static Optional<InnerClassesAttribute> find(final ClassFile classFile) {
		return classFile.getAttributeInfos().stream().filter(attributeInfo -> attributeInfo instanceof InnerClassesAttribute).map(attributeInfo -> InnerClassesAttribute.class.cast(attributeInfo)).findFirst();
	}
}