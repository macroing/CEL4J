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
package org.macroing.cel4j.java.binary.classfile;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code FieldInfo} represents a {@code field_info} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code field_info} structure has the following format:
 * <pre>
 * <code>
 * field_info {
 *     u2 access_flags;
 *     u2 name_index;
 *     u2 descriptor_index;
 *     u2 attributes_count;
 *     attribute_info[attributes_count] attributes;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class FieldInfo implements Node {
	/**
	 * The value for the access flag {@code ACC_ENUM} in the {@code access_flags} item of the {@code field_info} structure.
	 */
	public static final int ACC_ENUM = 0x4000;
	
	/**
	 * The value for the access flag {@code ACC_FINAL} in the {@code access_flags} item of the {@code field_info} structure.
	 */
	public static final int ACC_FINAL = 0x0010;
	
	/**
	 * The value for the access flag {@code ACC_PRIVATE} in the {@code access_flags} item of the {@code field_info} structure.
	 */
	public static final int ACC_PRIVATE = 0x0002;
	
	/**
	 * The value for the access flag {@code ACC_PROTECTED} in the {@code access_flags} item of the {@code field_info} structure.
	 */
	public static final int ACC_PROTECTED = 0x0004;
	
	/**
	 * The value for the access flag {@code ACC_PUBLIC} in the {@code access_flags} item of the {@code field_info} structure.
	 */
	public static final int ACC_PUBLIC = 0x0001;
	
	/**
	 * The value for the access flag {@code ACC_STATIC} in the {@code access_flags} item of the {@code field_info} structure.
	 */
	public static final int ACC_STATIC = 0x0008;
	
	/**
	 * The value for the access flag {@code ACC_SYNTHETIC} in the {@code access_flags} item of the {@code field_info} structure.
	 */
	public static final int ACC_SYNTHETIC = 0x1000;
	
	/**
	 * The value for the access flag {@code ACC_TRANSIENT} in the {@code access_flags} item of the {@code field_info} structure.
	 */
	public static final int ACC_TRANSIENT = 0x0080;
	
	/**
	 * The value for the access flag {@code ACC_VOLATILE} in the {@code access_flags} item of the {@code field_info} structure.
	 */
	public static final int ACC_VOLATILE = 0x0040;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final CopyOnWriteArrayList<AttributeInfo> attributeInfos;
	private int accessFlags;
	private int descriptorIndex;
	private int nameIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code FieldInfo} instance.
	 */
	public FieldInfo() {
		this.attributeInfos = new CopyOnWriteArrayList<>();
		this.accessFlags = 0;
		this.descriptorIndex = 2;
		this.nameIndex = 1;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link AttributeInfo} instance of this {@code FieldInfo} instance that is equal to {@code attributeInfo}.
	 * <p>
	 * If {@code attributeInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If this {@code FieldInfo} instance does not contain an {@code AttributeInfo} instance that is equal to {@code attributeInfo}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeInfo the {@code AttributeInfo} instance to test for equality against
	 * @return the {@code AttributeInfo} instance of this {@code FieldInfo} instance that is equal to {@code attributeInfo}
	 * @throws IllegalArgumentException thrown if, and only if, this {@code FieldInfo} instance does not contain an {@code AttributeInfo} instance that is equal to {@code attributeInfo}
	 * @throws NullPointerException thrown if, and only if, {@code attributeInfo} is {@code null}
	 */
	public AttributeInfo getAttributeInfo(final AttributeInfo attributeInfo) {
		Objects.requireNonNull(attributeInfo, "attributeInfo == null");
		
		for(final AttributeInfo currentAttributeInfo : getAttributeInfos()) {
			if(currentAttributeInfo.equals(attributeInfo)) {
				return currentAttributeInfo;
			}
		}
		
		throw new IllegalArgumentException("This field_info does not contain the provided attribute_info.");
	}
	
	/**
	 * Returns the {@link AttributeInfo} instance on the index {@code index}.
	 * <p>
	 * If {@code index} is less than {@code 0}, or greater than or equal to {@link #getAttributeInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param index the index of the {@code AttributeInfo}
	 * @return the {@code AttributeInfo} instance on the index {@code index}
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code index} is less than {@code 0}, or greater than or equal to {@code getAttributeInfoCount()}
	 */
	public AttributeInfo getAttributeInfo(final int index) {
		return this.attributeInfos.get(index);
	}
	
	/**
	 * Writes this {@code FieldInfo} to {@code dataOutput}.
	 * <p>
	 * Returns {@code dataOutput}.
	 * <p>
	 * If {@code dataOutput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an {@code IOException} is caught, an {@code UncheckedIOException} will be thrown.
	 * <p>
	 * This method does not close {@code dataOutput}.
	 * 
	 * @param dataOutput the {@code DataOutput} to write to
	 * @return {@code dataOutput}
	 * @throws NullPointerException thrown if, and only if, {@code dataOutput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an {@code IOException} is caught
	 */
	public DataOutput write(final DataOutput dataOutput) {
		try {
			dataOutput.writeShort(this.accessFlags);
			dataOutput.writeShort(this.nameIndex);
			dataOutput.writeShort(this.descriptorIndex);
			dataOutput.writeShort(this.attributeInfos.size());
			
			this.attributeInfos.forEach(attributeInfo -> attributeInfo.write(dataOutput));
			
			return dataOutput;
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	/**
	 * Writes this {@code FieldInfo} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * fieldInfo.write(new Document());
	 * }
	 * </pre>
	 * 
	 * @return the {@code Document}
	 */
	public Document write() {
		return write(new Document());
	}
	
	/**
	 * Writes this {@code FieldInfo} to {@code document}.
	 * <p>
	 * Returns {@code document}.
	 * <p>
	 * If {@code document} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param document the {@link Document} to write to
	 * @return {@code document}
	 * @throws NullPointerException thrown if, and only if, {@code document} is {@code null}
	 */
	public Document write(final Document document) {
		document.linef("field_info = {");
		document.indent();
		document.linef("u2 access_flags = %s;", Integer.toString(getAccessFlags()));
		document.linef("u2 name_index = %s;", Integer.toString(getNameIndex()));
		document.linef("u2 descriptor_index = %s;", Integer.toString(getDescriptorIndex()));
		document.linef("u2 attributes_count = %s;", Integer.toString(getAttributeInfoCount()));
		document.linef("attribute_info[%s] attributes = {", Integer.toString(getAttributeInfoCount()));
		document.indent();
		document.outdent();
		document.linef("};");
		document.outdent();
		document.linef("};");
		
		return document;
	}
	
	/**
	 * Returns a copy of this {@code FieldInfo} instance.
	 * 
	 * @return a copy of this {@code FieldInfo} instance
	 */
	public FieldInfo copy() {
		final
		FieldInfo fieldInfo = new FieldInfo();
		fieldInfo.accessFlags = this.accessFlags;
		fieldInfo.descriptorIndex = this.descriptorIndex;
		fieldInfo.nameIndex = this.nameIndex;
		
		this.attributeInfos.forEach(attributeInfo -> fieldInfo.addAttributeInfo(attributeInfo.copy()));
		
		return fieldInfo;
	}
	
	/**
	 * Returns a {@code List} with all currently added {@link AttributeInfo} instances.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code FieldInfo} instance.
	 * 
	 * @return a {@code List} with all currently added {@code AttributeInfo} instances
	 */
	public List<AttributeInfo> getAttributeInfos() {
		return new ArrayList<>(this.attributeInfos);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code FieldInfo} instance.
	 * 
	 * @return a {@code String} representation of this {@code FieldInfo} instance
	 */
	@Override
	public String toString() {
		return write().toString();
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
				for(final AttributeInfo attributeInfo : this.attributeInfos) {
					if(!attributeInfo.accept(nodeHierarchicalVisitor)) {
						break;
					}
				}
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
	/**
	 * Adds {@code attributeInfo} to this {@code FieldInfo} instance, if absent.
	 * <p>
	 * Returns {@code true} if, and only if, {@code attributeInfo} was added, {@code false} otherwise.
	 * <p>
	 * If {@code attributeInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param attributeInfo the {@link AttributeInfo} to add
	 * @return {@code true} if, and only if, {@code attributeInfo} was added, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code attributeInfo} is {@code null}
	 */
	public boolean addAttributeInfo(final AttributeInfo attributeInfo) {
		return this.attributeInfos.addIfAbsent(Objects.requireNonNull(attributeInfo, "attributeInfo == null"));
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code FieldInfo} instance contains {@code attributeInfo}, {@code false} otherwise.
	 * <p>
	 * If {@code attributeInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param attributeInfo the {@link AttributeInfo} to check
	 * @return {@code true} if, and only if, this {@code FieldInfo} instance contains {@code attributeInfo}, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code attributeInfo} is {@code null}
	 */
	public boolean containsAttributeInfo(final AttributeInfo attributeInfo) {
		Objects.requireNonNull(attributeInfo, "attributeInfo == null");
		
		for(final AttributeInfo currentAttributeInfo : getAttributeInfos()) {
			if(currentAttributeInfo.equals(attributeInfo)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Compares {@code object} to this {@code FieldInfo} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code FieldInfo}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code FieldInfo} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code FieldInfo}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof FieldInfo)) {
			return false;
		} else if(FieldInfo.class.cast(object).accessFlags != this.accessFlags) {
			return false;
		} else if(FieldInfo.class.cast(object).nameIndex != this.nameIndex) {
			return false;
		} else if(FieldInfo.class.cast(object).descriptorIndex != this.descriptorIndex) {
			return false;
		} else if(!Objects.equals(FieldInfo.class.cast(object).attributeInfos, this.attributeInfos)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code FieldInfo} instance may be used in a class, {@code false} otherwise.
	 * <p>
	 * This method should always return {@code true}. This is because, any and all of the access flag mutator methods will take care of their own constraints, such that an instance of this class will always be in a valid state. A class may have any
	 * access flag, but not all combinations thereof.
	 * 
	 * @return {@code true} if, and only if, this {@code FieldInfo} instance may be used in a class, {@code false} otherwise
	 */
	@SuppressWarnings("static-method")
	public boolean isClassCompatible() {
		return true;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code ACC_ENUM} is set in the {@code access_flags} item associated with this {@code FieldInfo} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, {@code ACC_ENUM} is set in the {@code access_flags} item associated with this {@code FieldInfo} instance, {@code false} otherwise
	 */
	public boolean isEnum() {
		return (this.accessFlags & ACC_ENUM) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code ACC_FINAL} is set in the {@code access_flags} item associated with this {@code FieldInfo} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, {@code ACC_FINAL} is set in the {@code access_flags} item associated with this {@code FieldInfo} instance, {@code false} otherwise
	 */
	public boolean isFinal() {
		return (this.accessFlags & ACC_FINAL) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code FieldInfo} instance may be used in an interface, {@code false} otherwise.
	 * <p>
	 * In order for this method to return {@code true}, all of the methods {@code isFinal()}, {@code isPublic()} and {@code isStatic()} must return {@code true}. The method {@code isSynthetic()} may return {@code true}, but this is not a requirement.
	 * All the other methods, namely {@code isEnum()}, {@code isPrivate()}, {@code isProtected()}, {@code isTransient()} and {@code isVolatile()}, must return {@code false}.
	 * 
	 * @return {@code true} if, and only if, this {@code FieldInfo} instance may be used in an interface, {@code false} otherwise
	 */
	public boolean isInterfaceCompatible() {
		if(isEnum()) {
			return false;
		} else if(isPrivate()) {
			return false;
		} else if(isProtected()) {
			return false;
		} else if(isTransient()) {
			return false;
		} else if(isVolatile()) {
			return false;
		} else {
			return isFinal() && isPublic() && isStatic();
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code ACC_PRIVATE} is set in the {@code access_flags} item associated with this {@code FieldInfo} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, {@code ACC_PRIVATE} is set in the {@code access_flags} item associated with this {@code FieldInfo} instance, {@code false} otherwise
	 */
	public boolean isPrivate() {
		return (this.accessFlags & ACC_PRIVATE) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code ACC_PROTECTED} is set in the {@code access_flags} item associated with this {@code FieldInfo} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, {@code ACC_PROTECTED} is set in the {@code access_flags} item associated with this {@code FieldInfo} instance, {@code false} otherwise
	 */
	public boolean isProtected() {
		return (this.accessFlags & ACC_PROTECTED) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code ACC_PUBLIC} is set in the {@code access_flags} item associated with this {@code FieldInfo} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, {@code ACC_PUBLIC} is set in the {@code access_flags} item associated with this {@code FieldInfo} instance, {@code false} otherwise
	 */
	public boolean isPublic() {
		return (this.accessFlags & ACC_PUBLIC) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code ACC_STATIC} is set in the {@code access_flags} item associated with this {@code FieldInfo} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, {@code ACC_STATIC} is set in the {@code access_flags} item associated with this {@code FieldInfo} instance, {@code false} otherwise
	 */
	public boolean isStatic() {
		return (this.accessFlags & ACC_STATIC) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code ACC_SYNTHETIC} is set in the {@code access_flags} item associated with this {@code FieldInfo} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, {@code ACC_SYNTHETIC} is set in the {@code access_flags} item associated with this {@code FieldInfo} instance, {@code false} otherwise
	 */
	public boolean isSynthetic() {
		return (this.accessFlags & ACC_SYNTHETIC) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code ACC_TRANSIENT} is set in the {@code access_flags} item associated with this {@code FieldInfo} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, {@code ACC_TRANSIENT} is set in the {@code access_flags} item associated with this {@code FieldInfo} instance, {@code false} otherwise
	 */
	public boolean isTransient() {
		return (this.accessFlags & ACC_TRANSIENT) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code ACC_VOLATILE} is set in the {@code access_flags} item associated with this {@code FieldInfo} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, {@code ACC_VOLATILE} is set in the {@code access_flags} item associated with this {@code FieldInfo} instance, {@code false} otherwise
	 */
	public boolean isVolatile() {
		return (this.accessFlags & ACC_VOLATILE) != 0;
	}
	
	/**
	 * Removes {@code attributeInfo} from this {@code FieldInfo} instance, if present.
	 * <p>
	 * Returns {@code true} if, and only if, {@code attributeInfo} was removed, {@code false} otherwise.
	 * <p>
	 * If {@code attributeInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param attributeInfo the {@link AttributeInfo} to remove
	 * @return {@code true} if, and only if, {@code attributeInfo} was removed, {@code false} otherwise
	 * @throws NullPointerException thrown if, and only if, {@code attributeInfo} is {@code null}
	 */
	public boolean removeAttributeInfo(final AttributeInfo attributeInfo) {
		return this.attributeInfos.remove(Objects.requireNonNull(attributeInfo, "attributeInfo == null"));
	}
	
	/**
	 * Returns the value of the {@code access_flags} item associated with this {@code FieldInfo} instance.
	 * 
	 * @return the value of the {@code access_flags} item associated with this {@code FieldInfo} instance
	 */
	public int getAccessFlags() {
		return this.accessFlags;
	}
	
	/**
	 * Returns the value of the {@code attributes_count} item associated with this {@code FieldInfo} instance.
	 * 
	 * @return the value of the {@code attributes_count} item associated with this {@code FieldInfo} instance
	 */
	public int getAttributeInfoCount() {
		return this.attributeInfos.size();
	}
	
	/**
	 * Returns the value of the {@code descriptor_index} item associated with this {@code FieldInfo} instance.
	 * 
	 * @return the value of the {@code descriptor_index} item associated with this {@code FieldInfo} instance
	 */
	public int getDescriptorIndex() {
		return this.descriptorIndex;
	}
	
	/**
	 * Returns the value of the {@code name_index} item associated with this {@code FieldInfo} instance.
	 * 
	 * @return the value of the {@code name_index} item associated with this {@code FieldInfo} instance
	 */
	public int getNameIndex() {
		return this.nameIndex;
	}
	
	/**
	 * Returns a hash code for this {@code FieldInfo} instance.
	 * 
	 * @return a hash code for this {@code FieldInfo} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(this.accessFlags), Integer.valueOf(this.nameIndex), Integer.valueOf(this.descriptorIndex), this.attributeInfos);
	}
	
	/**
	 * Sets {@code descriptorIndex} as the value for the {@code descriptor_index} item associated with this {@code FieldInfo} instance.
	 * <p>
	 * If {@code descriptorIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param descriptorIndex the value for the {@code descriptor_index} item associated with this {@code FieldInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code descriptorIndex} is less than {@code 1}
	 */
	public void setDescriptorIndex(final int descriptorIndex) {
		this.descriptorIndex = ParameterArguments.requireRange(descriptorIndex, 1, Integer.MAX_VALUE);
	}
	
	/**
	 * Adds or removes the access flag {@code ACC_ENUM}.
	 * 
	 * @param isEnum {@code true} if, and only if, the access flag {@code ACC_ENUM} should be added, {@code false} otherwise
	 */
	public void setEnum(final boolean isEnum) {
		if(isEnum) {
			this.accessFlags |= ACC_ENUM;
		} else {
			this.accessFlags &= ~ACC_ENUM;
		}
	}
	
	/**
	 * Adds or removes the access flag {@code ACC_FINAL}.
	 * <p>
	 * If {@code isFinal} is {@code true}, the following access flags will be removed:
	 * <ul>
	 * <li>{@code ACC_VOLATILE}</li>
	 * </ul>
	 * 
	 * @param isFinal {@code true} if, and only if, the access flag {@code ACC_FINAL} should be added, {@code false} otherwise
	 */
	public void setFinal(final boolean isFinal) {
		if(isFinal) {
			this.accessFlags |= ACC_FINAL;
			this.accessFlags &= ~ACC_VOLATILE;
		} else {
			this.accessFlags &= ~ACC_FINAL;
		}
	}
	
	/**
	 * Sets {@code nameIndex} as the value for the {@code name_index} item associated with this {@code FieldInfo} instance.
	 * <p>
	 * If {@code nameIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param nameIndex the value for the {@code name_index} item associated with this {@code FieldInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code nameIndex} is less than {@code 1}
	 */
	public void setNameIndex(final int nameIndex) {
		this.nameIndex = ParameterArguments.requireRange(nameIndex, 1, Integer.MAX_VALUE);
	}
	
	/**
	 * Adds or removes the access flag {@code ACC_PRIVATE}.
	 * <p>
	 * If {@code isPrivate} is {@code true}, the following access flags will be removed:
	 * <ul>
	 * <li>{@code ACC_PROTECTED}</li>
	 * <li>{@code ACC_PUBLIC}</li>
	 * </ul>
	 * 
	 * @param isPrivate {@code true} if, and only if, the access flag {@code ACC_PRIVATE} should be added, {@code false} otherwise
	 */
	public void setPrivate(final boolean isPrivate) {
		if(isPrivate) {
			this.accessFlags |= ACC_PRIVATE;
			this.accessFlags &= ~ACC_PROTECTED;
			this.accessFlags &= ~ACC_PUBLIC;
		} else {
			this.accessFlags &= ~ACC_PRIVATE;
		}
	}
	
	/**
	 * Adds or removes the access flag {@code ACC_PROTECTED}.
	 * <p>
	 * If {@code isProtected} is {@code true}, the following access flags will be removed:
	 * <ul>
	 * <li>{@code ACC_PRIVATE}</li>
	 * <li>{@code ACC_PUBLIC}</li>
	 * </ul>
	 * 
	 * @param isProtected {@code true} if, and only if, the access flag {@code ACC_PROTECTED} should be added, {@code false} otherwise
	 */
	public void setProtected(final boolean isProtected) {
		if(isProtected) {
			this.accessFlags &= ~ACC_PRIVATE;
			this.accessFlags |= ACC_PROTECTED;
			this.accessFlags &= ~ACC_PUBLIC;
		} else {
			this.accessFlags &= ~ACC_PROTECTED;
		}
	}
	
	/**
	 * Adds or removes the access flag {@code ACC_PUBLIC}.
	 * <p>
	 * If {@code isPublic} is {@code true}, the following access flags will be removed:
	 * <ul>
	 * <li>{@code ACC_PRIVATE}</li>
	 * <li>{@code ACC_PROTECTED}</li>
	 * </ul>
	 * 
	 * @param isPublic {@code true} if, and only if, the access flag {@code ACC_PUBLIC} should be added, {@code false} otherwise
	 */
	public void setPublic(final boolean isPublic) {
		if(isPublic) {
			this.accessFlags &= ~ACC_PRIVATE;
			this.accessFlags &= ~ACC_PROTECTED;
			this.accessFlags |= ACC_PUBLIC;
		} else {
			this.accessFlags &= ~ACC_PUBLIC;
		}
	}
	
	/**
	 * Adds or removes the access flag {@code ACC_STATIC}.
	 * 
	 * @param isStatic {@code true} if, and only if, the access flag {@code ACC_STATIC} should be added, {@code false} otherwise
	 */
	public void setStatic(final boolean isStatic) {
		if(isStatic) {
			this.accessFlags |= ACC_STATIC;
		} else {
			this.accessFlags &= ~ACC_STATIC;
		}
	}
	
	/**
	 * Adds or removes the access flag {@code ACC_SYNTHETIC}.
	 * 
	 * @param isSynthetic {@code true} if, and only if, the access flag {@code ACC_SYNTHETIC} should be added, {@code false} otherwise
	 */
	public void setSynthetic(final boolean isSynthetic) {
		if(isSynthetic) {
			this.accessFlags |= ACC_SYNTHETIC;
		} else {
			this.accessFlags &= ~ACC_SYNTHETIC;
		}
	}
	
	/**
	 * Adds or removes the access flag {@code ACC_TRANSIENT}.
	 * 
	 * @param isTransient {@code true} if, and only if, the access flag {@code ACC_TRANSIENT} should be added, {@code false} otherwise
	 */
	public void setTransient(final boolean isTransient) {
		if(isTransient) {
			this.accessFlags |= ACC_TRANSIENT;
		} else {
			this.accessFlags &= ~ACC_TRANSIENT;
		}
	}
	
	/**
	 * Adds or removes the access flag {@code ACC_VOLATILE}.
	 * <p>
	 * If {@code isVolatile} is {@code true}, the following access flags will be removed:
	 * <ul>
	 * <li>{@code ACC_FINAL}</li>
	 * </ul>
	 * 
	 * @param isVolatile {@code true} if, and only if, the access flag {@code ACC_VOLATILE} should be added, {@code false} otherwise
	 */
	public void setVolatile(final boolean isVolatile) {
		if(isVolatile) {
			this.accessFlags  &= ~ACC_FINAL;
			this.accessFlags |= ACC_VOLATILE;
		} else {
			this.accessFlags &= ~ACC_VOLATILE;
		}
	}
}