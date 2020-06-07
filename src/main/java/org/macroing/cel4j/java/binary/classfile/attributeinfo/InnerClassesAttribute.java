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
import java.util.Optional;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * An {@code InnerClassesAttribute} denotes an InnerClasses_attribute structure somewhere in a ClassFile structure.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class InnerClassesAttribute extends AttributeInfo {
	/**
	 * The name of the InnerClasses_attribute structure.
	 */
	public static final String NAME = "InnerClasses";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<InnerClass> innerClasses = new ArrayList<>();
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private InnerClassesAttribute(final int attributeNameIndex) {
		super(NAME, attributeNameIndex);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code InnerClassesAttribute} instance.
	 * 
	 * @return a copy of this {@code InnerClassesAttribute} instance
	 */
	@Override
	public InnerClassesAttribute copy() {
		final InnerClassesAttribute innerClassesAttribute = new InnerClassesAttribute(getAttributeNameIndex());
		
		this.innerClasses.forEach(innerClass -> innerClassesAttribute.addInnerClass(innerClass.copy()));
		
		return innerClassesAttribute;
	}
	
	/**
	 * Returns a {@code List} with all currently added {@code InnerClass}s.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code InnerClassesAttribute} instance.
	 * 
	 * @return a {@code List} with all currently added {@code InnerClass}s
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
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("InnerClasses_attribute");
		stringBuilder.append(":");
		stringBuilder.append(" ");
		stringBuilder.append("name=" + getName());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_name_index=" + getAttributeNameIndex());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_length=" + getAttributeLength());
		stringBuilder.append(" ");
		stringBuilder.append("number_of_classes=" + getNumberOfClasses());
		
		final String toString = stringBuilder.toString();
		
		return toString;
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
	 * <li>traverse its child {@code Node}s, if it has any.</li>
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
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code InnerClassesAttribute}, and that {@code InnerClassesAttribute} instance is equal to this {@code InnerClassesAttribute} instance,
	 * {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code InnerClassesAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code InnerClassesAttribute}, and that {@code InnerClassesAttribute} instance is equal to this {@code InnerClassesAttribute} instance,
	 * {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof InnerClassesAttribute)) {
			return false;
		} else if(!Objects.equals(InnerClassesAttribute.class.cast(object).getName(), getName())) {
			return false;
		} else if(InnerClassesAttribute.class.cast(object).getAttributeNameIndex() != getAttributeNameIndex()) {
			return false;
		} else if(InnerClassesAttribute.class.cast(object).getAttributeLength() != getAttributeLength()) {
			return false;
		} else if(!Objects.equals(InnerClassesAttribute.class.cast(object).innerClasses, this.innerClasses)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the attribute_length of this {@code InnerClassesAttribute} instance.
	 * 
	 * @return the attribute_length of this {@code InnerClassesAttribute} instance.
	 */
	@Override
	public int getAttributeLength() {
		int attributeLength = 0;
		
		attributeLength += 2;
		attributeLength += getNumberOfClasses() * 8;
		
		return attributeLength;
	}
	
	/**
	 * Returns the number_of_classes of this {@code InnerClassesAttribute} instance.
	 * 
	 * @return the number_of_classes of this {@code InnerClassesAttribute} instance.
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
	 * Attempts to add {@code innerClass} to this {@code InnerClassesAttribute} instance.
	 * <p>
	 * If {@code innerClass} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code innerClass} (or an {@code InnerClass} instance that equals {@code innerClass}) has already been added prior to this method call, nothing will happen.
	 * 
	 * @param innerClass the {@code InnerClass} to add
	 * @throws NullPointerException thrown if, and only if, {@code innerClass} is {@code null}
	 */
	public void addInnerClass(final InnerClass innerClass) {
		if(!this.innerClasses.contains(Objects.requireNonNull(innerClass, "innerClass == null"))) {
			this.innerClasses.add(innerClass);
		}
	}
	
	/**
	 * Attempts to remove {@code innerClass} from this {@code InnerClassesAttribute} instance.
	 * <p>
	 * If {@code innerClass} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If no {@code InnerClass} equal to {@code innerClass} can be found, nothing will happen.
	 * 
	 * @param innerClass the {@code InnerClass} to remove
	 * @throws NullPointerException thrown if, and only if, {@code innerClass} is {@code null}
	 */
	public void removeInnerClass(final InnerClass innerClass) {
		this.innerClasses.remove(Objects.requireNonNull(innerClass, "innerClass == null"));
	}
	
	/**
	 * Writes this {@code InnerClassesAttribute} to {@code dataOutput}.
	 * <p>
	 * If {@code dataOutput} is an {@code OutputStream} (or any other type of stream), this method will not close it.
	 * <p>
	 * If {@code dataOutput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O-error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param dataOutput the {@code DataOutput} to write to
	 * @throws NullPointerException thrown if, and only if, {@code dataOutput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O-error occurs
	 */
	@Override
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeShort(getAttributeNameIndex());
			dataOutput.writeInt(getAttributeLength());
			dataOutput.writeShort(getNumberOfClasses());
			
			this.innerClasses.forEach(innerClass -> innerClass.write(dataOutput));
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code InnerClassesAttribute} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the attribute_name_index of the new {@code InnerClassesAttribute} instance
	 * @return a new {@code InnerClassesAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than or equal to {@code 0}
	 */
	public static InnerClassesAttribute newInstance(final int attributeNameIndex) {
		return new InnerClassesAttribute(ParameterArguments.requireRange(attributeNameIndex, 1, Integer.MAX_VALUE));
	}
	
	/**
	 * Returns a {@code List} with all {@code InnerClassesAttribute}s.
	 * <p>
	 * All {@code InnerClassesAttribute}s are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code InnerClassesAttribute}s
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