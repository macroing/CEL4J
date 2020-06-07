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
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code MethodInfo} denotes a method_info structure somewhere in a ClassFile structure.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class MethodInfo implements Node {
	/**
	 * This field represents ACC_ABSTRACT in the access_flags element of a method_info structure.
	 */
	public static final int ACC_ABSTRACT = 0x0400;
	
	/**
	 * This field represents ACC_BRIDGE in the access_flags element of a method_info structure.
	 */
	public static final int ACC_BRIDGE = 0x0040;
	
	/**
	 * This field represents ACC_FINAL in the access_flags element of a method_info structure.
	 */
	public static final int ACC_FINAL = 0x0010;
	
	/**
	 * This field represents ACC_NATIVE in the access_flags element of a method_info structure.
	 */
	public static final int ACC_NATIVE = 0x0100;
	
	/**
	 * This field represents ACC_PRIVATE in the access_flags element of a method_info structure.
	 */
	public static final int ACC_PRIVATE = 0x0002;
	
	/**
	 * This field represents ACC_PROTECTED in the access_flags element of a method_info structure.
	 */
	public static final int ACC_PROTECTED = 0x0004;
	
	/**
	 * This field represents ACC_PUBLIC in the access_flags element of a method_info structure.
	 */
	public static final int ACC_PUBLIC = 0x0001;
	
	/**
	 * This field represents ACC_STATIC in the access_flags element of a method_info structure.
	 */
	public static final int ACC_STATIC = 0x0008;
	
	/**
	 * This field represents ACC_STRICT in the access_flags element of a method_info structure.
	 */
	public static final int ACC_STRICT = 0x0800;
	
	/**
	 * This field represents ACC_SYNCHRONIZED in the access_flags element of a method_info structure.
	 */
	public static final int ACC_SYNCHRONIZED = 0x0020;
	
	/**
	 * This field represents ACC_SYNTHETIC in the access_flags element of a method_info structure.
	 */
	public static final int ACC_SYNTHETIC = 0x1000;
	
	/**
	 * This field represents ACC_VARARGS in the access_flags element of a method_info structure.
	 */
	public static final int ACC_VARARGS = 0x0080;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final CopyOnWriteArrayList<AttributeInfo> attributeInfos;
	private int accessFlags;
	private int descriptorIndex;
	private int nameIndex;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code MethodInfo} instance.
	 */
	public MethodInfo() {
		this.attributeInfos = new CopyOnWriteArrayList<>();
		this.accessFlags = 0;
		this.descriptorIndex = 2;
		this.nameIndex = 1;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link AttributeInfo} instance of this {@code MethodInfo} instance that is equal to {@code attributeInfo}.
	 * <p>
	 * If {@code attributeInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If this {@code MethodInfo} instance does not contain an {@code AttributeInfo} instance that is equal to {@code attributeInfo}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeInfo the {@code AttributeInfo} instance to test for equality against
	 * @return the {@code AttributeInfo} instance of this {@code MethodInfo} instance that is equal to {@code attributeInfo}
	 * @throws IllegalArgumentException thrown if, and only if, this {@code MethodInfo} instance does not contain an {@code AttributeInfo} instance that is equal to {@code attributeInfo}
	 * @throws NullPointerException thrown if, and only if, {@code attributeInfo} is {@code null}
	 */
	public AttributeInfo getAttributeInfo(final AttributeInfo attributeInfo) {
		Objects.requireNonNull(attributeInfo, "attributeInfo == null");
		
		for(final AttributeInfo currentAttributeInfo : getAttributeInfos()) {
			if(currentAttributeInfo.equals(attributeInfo)) {
				return currentAttributeInfo;
			}
		}
		
		throw new IllegalArgumentException("This method_info does not contain the provided attribute_info.");
	}
	
	/**
	 * Returns an {@link AttributeInfo} given its index.
	 * <p>
	 * If {@code index} is less than {@code 0}, or greater than or equal to {@link #getAttributeInfoCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param index the index of the {@code AttributeInfo}
	 * @return an {@code AttributeInfo} given its index
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code index} is less than {@code 0}, or greater than or equal to {@code getAttributeInfoCount()}
	 */
	public AttributeInfo getAttributeInfo(final int index) {
		return this.attributeInfos.get(index);
	}
	
	/**
	 * Writes this {@code MethodInfo} to {@code dataOutput}.
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
	 * Writes this {@code MethodInfo} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * methodInfo.write(new Document());
	 * }
	 * </pre>
	 * 
	 * @return the {@code Document}
	 */
	public Document write() {
		return write(new Document());
	}
	
	/**
	 * Writes this {@code MethodInfo} to {@code document}.
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
		document.linef("method_info = {");
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
	 * Returns a {@code List} with all currently added {@link AttributeInfo}s.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code MethodInfo} instance.
	 * 
	 * @return a {@code List} with all currently added {@code AttributeInfo}s
	 */
	public List<AttributeInfo> getAttributeInfos() {
		return new ArrayList<>(this.attributeInfos);
	}
	
	/**
	 * Returns a copy of this {@code MethodInfo} instance.
	 * 
	 * @return a copy of this {@code MethodInfo} instance
	 */
	public MethodInfo copy() {
		final
		MethodInfo methodInfo = new MethodInfo();
		methodInfo.accessFlags = this.accessFlags;
		methodInfo.descriptorIndex = this.descriptorIndex;
		methodInfo.nameIndex = this.nameIndex;
		
		this.attributeInfos.forEach(attributeInfo -> methodInfo.addAttributeInfo(attributeInfo.copy()));
		
		return methodInfo;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code MethodInfo} instance.
	 * 
	 * @return a {@code String} representation of this {@code MethodInfo} instance
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
	 * Adds {@code attributeInfo} to this {@code MethodInfo} instance, if absent.
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
	 * Returns {@code true} if, and only if, this {@code MethodInfo} instance contains {@code attributeInfo}, {@code false} otherwise.
	 * <p>
	 * If {@code attributeInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param attributeInfo the {@link AttributeInfo} to check
	 * @return {@code true} if, and only if, this {@code MethodInfo} instance contains {@code attributeInfo}, {@code false} otherwise
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
	 * Compares {@code object} to this {@code MethodInfo} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code MethodInfo}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code MethodInfo} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code MethodInfo}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof MethodInfo)) {
			return false;
		} else if(MethodInfo.class.cast(object).accessFlags != this.accessFlags) {
			return false;
		} else if(MethodInfo.class.cast(object).nameIndex != this.nameIndex) {
			return false;
		} else if(MethodInfo.class.cast(object).descriptorIndex != this.descriptorIndex) {
			return false;
		} else if(!Objects.equals(MethodInfo.class.cast(object).attributeInfos, this.attributeInfos)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_ABSTRACT is set in the access_flags item of this {@code MethodInfo} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_ABSTRACT is set in the access_flags item of this {@code MethodInfo} instance, {@code false} otherwise
	 */
	public boolean isAbstract() {
		return (this.accessFlags & ACC_ABSTRACT) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_BRIDGE is set in the access_flags item of this {@code MethodInfo} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_BRIDGE is set in the access_flags item of this {@code MethodInfo} instance, {@code false} otherwise
	 */
	public boolean isBridge() {
		return (this.accessFlags & ACC_BRIDGE) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code MethodInfo} instance may be used in a class, {@code false} otherwise.
	 * <p>
	 * This method should always return {@code true}. This is because, any and all of the access flag mutator methods will take care of their own constraints, such that an instance of this class will always be in a valid
	 * state. A class may have any access flag, but not all combinations thereof.
	 * 
	 * @return {@code true} if, and only if, this {@code MethodInfo} instance may be used in a class, {@code false} otherwise
	 */
	@SuppressWarnings("static-method")
	public boolean isClassCompatible() {
		return true;
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_FINAL is set in the access_flags item of this {@code MethodInfo} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_FINAL is set in the access_flags item of this {@code MethodInfo} instance, {@code false} otherwise
	 */
	public boolean isFinal() {
		return (this.accessFlags & ACC_FINAL) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code MethodInfo} instance may be used in an interface, {@code false} otherwise.
	 * <p>
	 * In order for this method to return {@code true}, both of the methods {@code isAbstract()} and {@code isPublic()} must return {@code true}. Any of the methods {@code isBridge()}, {@code isSynthetic()} and
	 * {@code isVarargs()} may return {@code true}, but this is not a requirement. All the other methods, namely {@code isFinal()}, {@code isNative()}, {@code isPrivate()}, {@code isProtected()}, {@code isStatic()},
	 * {@code isStrict()} and {@code isSynchronized()}, must return {@code false}.
	 * 
	 * @return {@code true} if, and only if, this {@code MethodInfo} instance may be used in an interface, {@code false} otherwise
	 */
	public boolean isInterfaceCompatible() {
		if(isFinal()) {
			return false;
		} else if(isNative()) {
			return false;
		} else if(isPrivate()) {
			return false;
		} else if(isProtected()) {
			return false;
		} else if(isStatic()) {
			return false;
		} else if(isStrict()) {
			return false;
		} else if(isSynchronized()) {
			return false;
		} else {
			return isAbstract() && isPublic();
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_NATIVE is set in the access_flags item of this {@code MethodInfo} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_NATIVE is set in the access_flags item of this {@code MethodInfo} instance, {@code false} otherwise
	 */
	public boolean isNative() {
		return (this.accessFlags & ACC_NATIVE) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_PRIVATE is set in the access_flags item of this {@code MethodInfo} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_PRIVATE is set in the access_flags item of this {@code MethodInfo} instance, {@code false} otherwise
	 */
	public boolean isPrivate() {
		return (this.accessFlags & ACC_PRIVATE) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_PROTECTED is set in the access_flags item of this {@code MethodInfo} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_PROTECTED is set in the access_flags item of this {@code MethodInfo} instance, {@code false} otherwise
	 */
	public boolean isProtected() {
		return (this.accessFlags & ACC_PROTECTED) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_PUBLIC is set in the access_flags item of this {@code MethodInfo} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_PUBLIC is set in the access_flags item of this {@code MethodInfo} instance, {@code false} otherwise
	 */
	public boolean isPublic() {
		return (this.accessFlags & ACC_PUBLIC) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_STATIC is set in the access_flags item of this {@code MethodInfo} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_STATIC is set in the access_flags item of this {@code MethodInfo} instance, {@code false} otherwise
	 */
	public boolean isStatic() {
		return (this.accessFlags & ACC_STATIC) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_STRICT is set in the access_flags item of this {@code MethodInfo} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_STRICT is set in the access_flags item of this {@code MethodInfo} instance, {@code false} otherwise
	 */
	public boolean isStrict() {
		return (this.accessFlags & ACC_STRICT) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_SYNCHRONIZED is set in the access_flags item of this {@code MethodInfo} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_SYNCHRONIZED is set in the access_flags item of this {@code MethodInfo} instance, {@code false} otherwise
	 */
	public boolean isSynchronized() {
		return (this.accessFlags & ACC_SYNCHRONIZED) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_SYNTHETIC is set in the access_flags item of this {@code MethodInfo} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_SYNTHETIC is set in the access_flags item of this {@code MethodInfo} instance, {@code false} otherwise
	 */
	public boolean isSynthetic() {
		return (this.accessFlags & ACC_SYNTHETIC) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, ACC_VARARGS is set in the access_flags item of this {@code MethodInfo} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, ACC_VARARGS is set in the access_flags item of this {@code MethodInfo} instance, {@code false} otherwise
	 */
	public boolean isVarargs() {
		return (this.accessFlags & ACC_VARARGS) != 0;
	}
	
	/**
	 * Removes {@code attributeInfo} from this {@code MethodInfo} instance, if present.
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
	 * Returns the access_flags of this {@code MethodInfo} instance.
	 * 
	 * @return the access_flags of this {@code MethodInfo} instance
	 */
	public int getAccessFlags() {
		return this.accessFlags;
	}
	
	/**
	 * Returns the number of {@link AttributeInfo}s currently added.
	 * 
	 * @return the number of {@code AttributeInfo}s currently added
	 */
	public int getAttributeInfoCount() {
		return this.attributeInfos.size();
	}
	
	/**
	 * Returns the descriptor_index of this {@code MethodInfo} instance.
	 * 
	 * @return the descriptor_index of this {@code MethodInfo} instance
	 */
	public int getDescriptorIndex() {
		return this.descriptorIndex;
	}
	
	/**
	 * Returns the name_index of this {@code MethodInfo} instance.
	 * 
	 * @return the name_index of this {@code MethodInfo} instance
	 */
	public int getNameIndex() {
		return this.nameIndex;
	}
	
	/**
	 * Returns a hash code for this {@code MethodInfo} instance.
	 * 
	 * @return a hash code for this {@code MethodInfo} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(this.accessFlags), Integer.valueOf(this.nameIndex), Integer.valueOf(this.descriptorIndex), this.attributeInfos);
	}
	
	/**
	 * Adds or removes the access flag ACC_ABSTRACT.
	 * <p>
	 * This method turns off the following access flags, if given a parameter argument that is set to {@code true}:
	 * <ul>
	 * <li>ACC_FINAL</li>
	 * <li>ACC_NATIVE</li>
	 * <li>ACC_PRIVATE</li>
	 * <li>ACC_STATIC</li>
	 * <li>ACC_STRICT</li>
	 * <li>ACC_SYNCHRONIZED</li>
	 * </ul>
	 * 
	 * @param isAbstract {@code true} to add the access flag ACC_ABSTRACT
	 */
	public void setAbstract(final boolean isAbstract) {
		if(isAbstract) {
			this.accessFlags |= ACC_ABSTRACT;
			this.accessFlags &= ~ACC_FINAL;
			this.accessFlags &= ~ACC_NATIVE;
			this.accessFlags &= ~ACC_PRIVATE;
			this.accessFlags &= ~ACC_STATIC;
			this.accessFlags &= ~ACC_STRICT;
			this.accessFlags &= ~ACC_SYNCHRONIZED;
		} else {
			this.accessFlags &= ~ACC_ABSTRACT;
		}
	}
	
	/**
	 * Adds or removes the access flag ACC_BRIDGE.
	 * 
	 * @param isBridge {@code true} to add the access flag ACC_BRIDGE
	 */
	public void setBridge(final boolean isBridge) {
		if(isBridge) {
			this.accessFlags |= ACC_BRIDGE;
		} else {
			this.accessFlags &= ~ACC_BRIDGE;
		}
	}
	
	/**
	 * Sets a new descriptor_index for this {@code MethodInfo} instance.
	 * <p>
	 * If {@code descriptorIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param descriptorIndex the new descriptor_index for this {@code MethodInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code descriptorIndex} is less than or equal to {@code 0}
	 */
	public void setDescriptorIndex(final int descriptorIndex) {
		this.descriptorIndex = ParameterArguments.requireRange(descriptorIndex, 1, Integer.MAX_VALUE);
	}
	
	/**
	 * Adds or removes the access flag ACC_FINAL.
	 * <p>
	 * This method turns off the following access flags, if given a parameter argument that is set to {@code true}:
	 * <ul>
	 * <li>ACC_ABSTRACT</li>
	 * </ul>
	 * 
	 * @param isFinal {@code true} to add the access flag ACC_FINAL
	 */
	public void setFinal(final boolean isFinal) {
		if(isFinal) {
			this.accessFlags &= ~ACC_ABSTRACT;
			this.accessFlags |= ACC_FINAL;
		} else {
			this.accessFlags &= ~ACC_FINAL;
		}
	}
	
	/**
	 * Sets a new name_index for this {@code MethodInfo} instance.
	 * <p>
	 * If {@code nameIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param nameIndex the new name_index for this {@code MethodInfo} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code nameIndex} is less than or equal to {@code 0}
	 */
	public void setNameIndex(final int nameIndex) {
		this.nameIndex = ParameterArguments.requireRange(nameIndex, 1, Integer.MAX_VALUE);
	}
	
	/**
	 * Adds or removes the access flag ACC_NATIVE.
	 * <p>
	 * This method turns off the following access flags, if given a parameter argument that is set to {@code true}:
	 * <ul>
	 * <li>ACC_ABSTRACT</li>
	 * </ul>
	 * 
	 * @param isNative {@code true} to add the access flag ACC_NATIVE
	 */
	public void setNative(final boolean isNative) {
		if(isNative) {
			this.accessFlags &= ~ACC_ABSTRACT;
			this.accessFlags |= ACC_NATIVE;
		} else {
			this.accessFlags &= ~ACC_NATIVE;
		}
	}
	
	/**
	 * Adds or removes the access flag ACC_PRIVATE.
	 * <p>
	 * This method turns off the following access flags, if given a parameter argument that is set to {@code true}:
	 * <ul>
	 * <li>ACC_ABSTRACT</li>
	 * <li>ACC_PROTECTED</li>
	 * <li>ACC_PUBLIC</li>
	 * </ul>
	 * 
	 * @param isPrivate {@code true} to add the access flag ACC_PRIVATE
	 */
	public void setPrivate(final boolean isPrivate) {
		if(isPrivate) {
			this.accessFlags &= ~ACC_ABSTRACT;
			this.accessFlags |= ACC_PRIVATE;
			this.accessFlags &= ~ACC_PROTECTED;
			this.accessFlags &= ~ACC_PUBLIC;
		} else {
			this.accessFlags &= ~ACC_PRIVATE;
		}
	}
	
	/**
	 * Adds or removes the access flag ACC_PROTECTED.
	 * <p>
	 * This method turns off the following access flags, if given a parameter argument that is set to {@code true}:
	 * <ul>
	 * <li>ACC_PRIVATE</li>
	 * <li>ACC_PUBLIC</li>
	 * </ul>
	 * 
	 * @param isProtected {@code true} to add the access flag ACC_PROTECTED
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
	 * Adds or removes the access flag ACC_PUBLIC.
	 * <p>
	 * This method turns off the following access flags, if given a parameter argument that is set to {@code true}:
	 * <ul>
	 * <li>ACC_PRIVATE</li>
	 * <li>ACC_PROTECTED</li>
	 * </ul>
	 * 
	 * @param isPublic {@code true} to add the access flag ACC_PUBLIC
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
	 * Adds or removes the access flag ACC_STATIC.
	 * <p>
	 * This method turns off the following access flags, if given a parameter argument that is set to {@code true}:
	 * <ul>
	 * <li>ACC_ABSTRACT</li>
	 * </ul>
	 * 
	 * @param isStatic {@code true} to add the access flag ACC_STATIC
	 */
	public void setStatic(final boolean isStatic) {
		if(isStatic) {
			this.accessFlags &= ~ACC_ABSTRACT;
			this.accessFlags |= ACC_STATIC;
		} else {
			this.accessFlags &= ~ACC_STATIC;
		}
	}
	
	/**
	 * Adds or removes the access flag ACC_STRICT.
	 * <p>
	 * This method turns off the following access flags, if given a parameter argument that is set to {@code true}:
	 * <ul>
	 * <li>ACC_ABSTRACT</li>
	 * </ul>
	 * 
	 * @param isStrict {@code true} to add the access flag ACC_STRICT
	 */
	public void setStrict(final boolean isStrict) {
		if(isStrict) {
			this.accessFlags &= ~ACC_ABSTRACT;
			this.accessFlags |= ACC_STRICT;
		} else {
			this.accessFlags &= ~ACC_STRICT;
		}
	}
	
	/**
	 * Adds or removes the access flag ACC_SYNCHRONIZED.
	 * <p>
	 * This method turns off the following access flags, if given a parameter argument that is set to {@code true}:
	 * <ul>
	 * <li>ACC_ABSTRACT</li>
	 * </ul>
	 * 
	 * @param isSynchronized {@code true} to add the access flag ACC_SYNCHRONIZED
	 */
	public void setSynchronized(final boolean isSynchronized) {
		if(isSynchronized) {
			this.accessFlags &= ~ACC_ABSTRACT;
			this.accessFlags |= ACC_SYNCHRONIZED;
		} else {
			this.accessFlags &= ~ACC_SYNCHRONIZED;
		}
	}
	
	/**
	 * Adds or removes the access flag ACC_SYNTHETIC.
	 * 
	 * @param isSynthetic {@code true} to add the access flag ACC_SYNTHETIC
	 */
	public void setSynthetic(final boolean isSynthetic) {
		if(isSynthetic) {
			this.accessFlags |= ACC_SYNTHETIC;
		} else {
			this.accessFlags &= ~ACC_SYNTHETIC;
		}
	}
	
	/**
	 * Adds or removes the access flag ACC_VARARGS.
	 * 
	 * @param isVarargs {@code true} to add the access flag ACC_VARARGS
	 */
	public void setVarargs(final boolean isVarargs) {
		if(isVarargs) {
			this.accessFlags |= ACC_VARARGS;
		} else {
			this.accessFlags &= ~ACC_VARARGS;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a new {@code MethodInfo} instance.
	 * 
	 * @return a new {@code MethodInfo} instance
	 */
	public static MethodInfo newInstance() {
		return new MethodInfo();
	}
	
	/**
	 * Returns a {@link NodeFilter} that accepts {@link Node}s that are instances of {@link MethodInfo} and have an {@code isAbstract()} method that returns {@code true}.
	 * <p>
	 * The {@code NodeFilter} returned by this method will throw a {@code NullPointerException} if, and only if, the {@code Node} to accept or reject is {@code null}. It is also stateless and therefore considered thread-safe.
	 * 
	 * @return a {@code NodeFilter} that accepts {@code Node}s that are instances of {@code MethodInfo} and have an {@code isAbstract()} method that returns {@code true}
	 */
	public static NodeFilter newAbstractNodeFilter() {
		return node -> {
			Objects.requireNonNull(node, "node == null");
			
			if(node instanceof MethodInfo) {
				final MethodInfo methodInfo = MethodInfo.class.cast(node);
				
				if(methodInfo.isAbstract()) {
					return true;
				}
			}
			
			return false;
		};
	}
	
	/**
	 * Returns a {@link NodeFilter} that accepts {@link Node}s that are instances of {@link MethodInfo} and have an {@code isBridge()} method that returns {@code true}.
	 * <p>
	 * The {@code NodeFilter} returned by this method will throw a {@code NullPointerException} if, and only if, the {@code Node} to accept or reject is {@code null}. It is also stateless and therefore considered thread-safe.
	 * 
	 * @return a {@code NodeFilter} that accepts {@code Node}s that are instances of {@code MethodInfo} and have an {@code isBridge()} method that returns {@code true}
	 */
	public static NodeFilter newBridgeNodeFilter() {
		return node -> {
			Objects.requireNonNull(node, "node == null");
			
			if(node instanceof MethodInfo) {
				final MethodInfo methodInfo = MethodInfo.class.cast(node);
				
				if(methodInfo.isBridge()) {
					return true;
				}
			}
			
			return false;
		};
	}
	
	/**
	 * Returns a {@link NodeFilter} that accepts {@link Node}s that are instances of {@link MethodInfo} and have an {@code isClassCompatible()} method that returns {@code true}.
	 * <p>
	 * The {@code NodeFilter} returned by this method will throw a {@code NullPointerException} if, and only if, the {@code Node} to accept or reject is {@code null}. It is also stateless and therefore considered thread-safe.
	 * 
	 * @return a {@code NodeFilter} that accepts {@code Node}s that are instances of {@code MethodInfo} and have an {@code isClassCompatible()} method that returns {@code true}
	 */
	public static NodeFilter newClassCompatibleNodeFilter() {
		return node -> {
			Objects.requireNonNull(node, "node == null");
			
			if(node instanceof MethodInfo) {
				final MethodInfo methodInfo = MethodInfo.class.cast(node);
				
				if(methodInfo.isClassCompatible()) {
					return true;
				}
			}
			
			return false;
		};
	}
	
	/**
	 * Returns a {@link NodeFilter} that accepts {@link Node}s that are instances of {@link MethodInfo} and have an {@code isFinal()} method that returns {@code true}.
	 * <p>
	 * The {@code NodeFilter} returned by this method will throw a {@code NullPointerException} if, and only if, the {@code Node} to accept or reject is {@code null}. It is also stateless and therefore considered thread-safe.
	 * 
	 * @return a {@code NodeFilter} that accepts {@code Node}s that are instances of {@code MethodInfo} and have an {@code isFinal()} method that returns {@code true}
	 */
	public static NodeFilter newFinalNodeFilter() {
		return node -> {
			Objects.requireNonNull(node, "node == null");
			
			if(node instanceof MethodInfo) {
				final MethodInfo methodInfo = MethodInfo.class.cast(node);
				
				if(methodInfo.isFinal()) {
					return true;
				}
			}
			
			return false;
		};
	}
	
	/**
	 * Returns a {@link NodeFilter} that accepts {@link Node}s that are instances of {@link MethodInfo} and have an {@code isInterfaceCompatible()} method that returns {@code true}.
	 * <p>
	 * The {@code NodeFilter} returned by this method will throw a {@code NullPointerException} if, and only if, the {@code Node} to accept or reject is {@code null}. It is also stateless and therefore considered thread-safe.
	 * 
	 * @return a {@code NodeFilter} that accepts {@code Node}s that are instances of {@code MethodInfo} and have an {@code isInterfaceCompatible()} method that returns {@code true}
	 */
	public static NodeFilter newInterfaceCompatibleNodeFilter() {
		return node -> {
			Objects.requireNonNull(node, "node == null");
			
			if(node instanceof MethodInfo) {
				final MethodInfo methodInfo = MethodInfo.class.cast(node);
				
				if(methodInfo.isInterfaceCompatible()) {
					return true;
				}
			}
			
			return false;
		};
	}
	
	/**
	 * Returns a {@link NodeFilter} that accepts {@link Node}s that are instances of {@link MethodInfo} and have an {@code isNative()} method that returns {@code true}.
	 * <p>
	 * The {@code NodeFilter} returned by this method will throw a {@code NullPointerException} if, and only if, the {@code Node} to accept or reject is {@code null}. It is also stateless and therefore considered thread-safe.
	 * 
	 * @return a {@code NodeFilter} that accepts {@code Node}s that are instances of {@code MethodInfo} and have an {@code isNative()} method that returns {@code true}
	 */
	public static NodeFilter newNativeNodeFilter() {
		return node -> {
			Objects.requireNonNull(node, "node == null");
			
			if(node instanceof MethodInfo) {
				final MethodInfo methodInfo = MethodInfo.class.cast(node);
				
				if(methodInfo.isNative()) {
					return true;
				}
			}
			
			return false;
		};
	}
	
	/**
	 * Returns a {@link NodeFilter} that accepts {@link Node}s that are instances of {@link MethodInfo} and have an {@code isStatic()} method that returns {@code false}.
	 * <p>
	 * The {@code NodeFilter} returned by this method will throw a {@code NullPointerException} if, and only if, the {@code Node} to accept or reject is {@code null}. It is also stateless and therefore considered thread-safe.
	 * 
	 * @return a {@code NodeFilter} that accepts {@code Node}s that are instances of {@code MethodInfo} and have an {@code isStatic()} method that returns {@code false}
	 */
	public static NodeFilter newNonStaticNodeFilter() {
		return node -> {
			Objects.requireNonNull(node, "node == null");
			
			if(node instanceof MethodInfo) {
				final MethodInfo methodInfo = MethodInfo.class.cast(node);
				
				if(!methodInfo.isStatic()) {
					return true;
				}
			}
			
			return false;
		};
	}
	
	/**
	 * Returns a {@link NodeFilter} that accepts {@link Node}s that are instances of {@link MethodInfo} and have an {@code isPrivate()} method that returns {@code true}.
	 * <p>
	 * The {@code NodeFilter} returned by this method will throw a {@code NullPointerException} if, and only if, the {@code Node} to accept or reject is {@code null}. It is also stateless and therefore considered thread-safe.
	 * 
	 * @return a {@code NodeFilter} that accepts {@code Node}s that are instances of {@code MethodInfo} and have an {@code isPrivate()} method that returns {@code true}
	 */
	public static NodeFilter newPrivateNodeFilter() {
		return node -> {
			Objects.requireNonNull(node, "node == null");
			
			if(node instanceof MethodInfo) {
				final MethodInfo methodInfo = MethodInfo.class.cast(node);
				
				if(methodInfo.isPrivate()) {
					return true;
				}
			}
			
			return false;
		};
	}
	
	/**
	 * Returns a {@link NodeFilter} that accepts {@link Node}s that are instances of {@link MethodInfo} and have an {@code isProtected()} method that returns {@code true}.
	 * <p>
	 * The {@code NodeFilter} returned by this method will throw a {@code NullPointerException} if, and only if, the {@code Node} to accept or reject is {@code null}. It is also stateless and therefore considered thread-safe.
	 * 
	 * @return a {@code NodeFilter} that accepts {@code Node}s that are instances of {@code MethodInfo} and have an {@code isProtected()} method that returns {@code true}
	 */
	public static NodeFilter newProtectedNodeFilter() {
		return node -> {
			Objects.requireNonNull(node, "node == null");
			
			if(node instanceof MethodInfo) {
				final MethodInfo methodInfo = MethodInfo.class.cast(node);
				
				if(methodInfo.isProtected()) {
					return true;
				}
			}
			
			return false;
		};
	}
	
	/**
	 * Returns a {@link NodeFilter} that accepts {@link Node}s that are instances of {@link MethodInfo} and have an {@code isPublic()} method that returns {@code true}.
	 * <p>
	 * The {@code NodeFilter} returned by this method will throw a {@code NullPointerException} if, and only if, the {@code Node} to accept or reject is {@code null}. It is also stateless and therefore considered thread-safe.
	 * 
	 * @return a {@code NodeFilter} that accepts {@code Node}s that are instances of {@code MethodInfo} and have an {@code isPublic()} method that returns {@code true}
	 */
	public static NodeFilter newPublicNodeFilter() {
		return node -> {
			Objects.requireNonNull(node, "node == null");
			
			if(node instanceof MethodInfo) {
				final MethodInfo methodInfo = MethodInfo.class.cast(node);
				
				if(methodInfo.isPublic()) {
					return true;
				}
			}
			
			return false;
		};
	}
	
	/**
	 * Returns a {@link NodeFilter} that accepts {@link Node}s that are instances of {@link MethodInfo} and have an {@code isStatic()} method that returns {@code true}.
	 * <p>
	 * The {@code NodeFilter} returned by this method will throw a {@code NullPointerException} if, and only if, the {@code Node} to accept or reject is {@code null}. It is also stateless and therefore considered thread-safe.
	 * 
	 * @return a {@code NodeFilter} that accepts {@code Node}s that are instances of {@code MethodInfo} and have an {@code isStatic()} method that returns {@code true}
	 */
	public static NodeFilter newStaticNodeFilter() {
		return node -> {
			Objects.requireNonNull(node, "node == null");
			
			if(node instanceof MethodInfo) {
				final MethodInfo methodInfo = MethodInfo.class.cast(node);
				
				if(methodInfo.isStatic()) {
					return true;
				}
			}
			
			return false;
		};
	}
	
	/**
	 * Returns a {@link NodeFilter} that accepts {@link Node}s that are instances of {@link MethodInfo} and have an {@code isStrict()} method that returns {@code true}.
	 * <p>
	 * The {@code NodeFilter} returned by this method will throw a {@code NullPointerException} if, and only if, the {@code Node} to accept or reject is {@code null}. It is also stateless and therefore considered thread-safe.
	 * 
	 * @return a {@code NodeFilter} that accepts {@code Node}s that are instances of {@code MethodInfo} and have an {@code isStrict()} method that returns {@code true}
	 */
	public static NodeFilter newStrictNodeFilter() {
		return node -> {
			Objects.requireNonNull(node, "node == null");
			
			if(node instanceof MethodInfo) {
				final MethodInfo methodInfo = MethodInfo.class.cast(node);
				
				if(methodInfo.isStrict()) {
					return true;
				}
			}
			
			return false;
		};
	}
	
	/**
	 * Returns a {@link NodeFilter} that accepts {@link Node}s that are instances of {@link MethodInfo} and have an {@code isSynchronized()} method that returns {@code true}.
	 * <p>
	 * The {@code NodeFilter} returned by this method will throw a {@code NullPointerException} if, and only if, the {@code Node} to accept or reject is {@code null}. It is also stateless and therefore considered thread-safe.
	 * 
	 * @return a {@code NodeFilter} that accepts {@code Node}s that are instances of {@code MethodInfo} and have an {@code isSynchronized()} method that returns {@code true}
	 */
	public static NodeFilter newSynchronizedNodeFilter() {
		return node -> {
			Objects.requireNonNull(node, "node == null");
			
			if(node instanceof MethodInfo) {
				final MethodInfo methodInfo = MethodInfo.class.cast(node);
				
				if(methodInfo.isSynchronized()) {
					return true;
				}
			}
			
			return false;
		};
	}
	
	/**
	 * Returns a {@link NodeFilter} that accepts {@link Node}s that are instances of {@link MethodInfo} and have an {@code isSynthetic()} method that returns {@code true}.
	 * <p>
	 * The {@code NodeFilter} returned by this method will throw a {@code NullPointerException} if, and only if, the {@code Node} to accept or reject is {@code null}. It is also stateless and therefore considered thread-safe.
	 * 
	 * @return a {@code NodeFilter} that accepts {@code Node}s that are instances of {@code MethodInfo} and have an {@code isSynthetic()} method that returns {@code true}
	 */
	public static NodeFilter newSyntheticNodeFilter() {
		return node -> {
			Objects.requireNonNull(node, "node == null");
			
			if(node instanceof MethodInfo) {
				final MethodInfo methodInfo = MethodInfo.class.cast(node);
				
				if(methodInfo.isSynthetic()) {
					return true;
				}
			}
			
			return false;
		};
	}
	
	/**
	 * Returns a {@link NodeFilter} that accepts {@link Node}s that are instances of {@link MethodInfo} and have an {@code isVarargs()} method that returns {@code true}.
	 * <p>
	 * The {@code NodeFilter} returned by this method will throw a {@code NullPointerException} if, and only if, the {@code Node} to accept or reject is {@code null}. It is also stateless and therefore considered thread-safe.
	 * 
	 * @return a {@code NodeFilter} that accepts {@code Node}s that are instances of {@code MethodInfo} and have an {@code isVarargs()} method that returns {@code true}
	 */
	public static NodeFilter newVarargsNodeFilter() {
		return node -> {
			Objects.requireNonNull(node, "node == null");
			
			if(node instanceof MethodInfo) {
				final MethodInfo methodInfo = MethodInfo.class.cast(node);
				
				if(methodInfo.isVarargs()) {
					return true;
				}
			}
			
			return false;
		};
	}
}