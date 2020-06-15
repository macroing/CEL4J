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
import org.macroing.cel4j.java.binary.classfile.MethodInfo;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code CodeAttribute} denotes a Code_attribute structure somewhere in a ClassFile structure.
 * <p>
 * This class is not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class CodeAttribute extends AttributeInfo {
	/**
	 * The name of the Code_attribute structure.
	 */
	public static final String NAME = "Code";
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<AttributeInfo> attributeInfos;
	private final List<ExceptionHandler> exceptionHandlers;
	private final List<Instruction> instructions;
	private int maxLocals;
	private int maxStack;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code CodeAttribute} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than or equal to {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the attribute_name_index of the new {@code CodeAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than or equal to {@code 0}
	 */
	public CodeAttribute(final int attributeNameIndex) {
		super(NAME, attributeNameIndex);
		
		this.attributeInfos = new ArrayList<>();
		this.exceptionHandlers = new ArrayList<>();
		this.instructions = new ArrayList<>();
		this.maxLocals = 0;
		this.maxStack = 0;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link AttributeInfo} instance of this {@code CodeAttribute} instance that is equal to {@code attributeInfo}.
	 * <p>
	 * If {@code attributeInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If this {@code CodeAttribute} instance does not contain an {@code AttributeInfo} instance that is equal to {@code attributeInfo}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeInfo the {@code AttributeInfo} instance to test for equality against
	 * @return the {@code AttributeInfo} instance of this {@code CodeAttribute} instance that is equal to {@code attributeInfo}
	 * @throws IllegalArgumentException thrown if, and only if, this {@code CodeAttribute} instance does not contain an {@code AttributeInfo} instance that is equal to {@code attributeInfo}
	 * @throws NullPointerException thrown if, and only if, {@code attributeInfo} is {@code null}
	 */
	public AttributeInfo getAttributeInfo(final AttributeInfo attributeInfo) {
		Objects.requireNonNull(attributeInfo, "attributeInfo == null");
		
		for(final AttributeInfo currentAttributeInfo : getAttributeInfos()) {
			if(currentAttributeInfo.equals(attributeInfo)) {
				return currentAttributeInfo;
			}
		}
		
		throw new IllegalArgumentException("This Code_attribute does not contain the provided attribute_info.");
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
	 * Returns a copy of this {@code CodeAttribute} instance.
	 * 
	 * @return a copy of this {@code CodeAttribute} instance
	 */
	@Override
	public CodeAttribute copy() {
		final
		CodeAttribute codeAttribute = new CodeAttribute(getAttributeNameIndex());
		codeAttribute.setMaxLocals(getMaxLocals());
		codeAttribute.setMaxStack(getMaxStack());
		
		this.attributeInfos.forEach(attributeInfo -> codeAttribute.addAttributeInfo(attributeInfo.copy()));
		
		this.exceptionHandlers.forEach(exceptionHandler -> codeAttribute.addExceptionHandler(exceptionHandler.copy()));
		
		this.instructions.forEach(instruction -> codeAttribute.addInstruction(instruction.copy()));
		
		return codeAttribute;
	}
	
	/**
	 * Returns a {@code List} with all currently added {@code AttributeInfo}s.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code CodeAttribute} instance.
	 * 
	 * @return a {@code List} with all currently added {@code AttributeInfo}s
	 */
	public List<AttributeInfo> getAttributeInfos() {
		return new ArrayList<>(this.attributeInfos);
	}
	
	/**
	 * Returns a {@code List} with all currently added {@code ExceptionHandler}s.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code CodeAttribute} instance.
	 * 
	 * @return a {@code List} with all currently added {@code ExceptionHandler}s
	 */
	public List<ExceptionHandler> getExceptionHandlers() {
		return new ArrayList<>(this.exceptionHandlers);
	}
	
	/**
	 * Returns a {@code List} with all currently added {@code Instruction}s.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code CodeAttribute} instance.
	 * 
	 * @return a {@code List} with all currently added {@code Instruction}s
	 */
	public List<Instruction> getInstructions() {
		return new ArrayList<>(this.instructions);
	}
	
	/**
	 * Returns a {@code String} representation of this {@code CodeAttribute} instance.
	 * 
	 * @return a {@code String} representation of this {@code CodeAttribute} instance
	 */
	@Override
	public String toString() {
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Code_attribute");
		stringBuilder.append(":");
		stringBuilder.append(" ");
		stringBuilder.append("name=" + getName());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_name_index=" + getAttributeNameIndex());
		stringBuilder.append(" ");
		stringBuilder.append("attribute_length=" + getAttributeLength());
		stringBuilder.append(" ");
		stringBuilder.append("max_stack=" + getMaxStack());
		stringBuilder.append(" ");
		stringBuilder.append("max_locals=" + getMaxLocals());
		stringBuilder.append(" ");
		stringBuilder.append("code_length=" + getCodeLength());
		
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
				for(final AttributeInfo attributeInfo : this.attributeInfos) {
					if(!attributeInfo.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
				}
				
				for(final ExceptionHandler exceptionHandler : this.exceptionHandlers) {
					if(!exceptionHandler.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
				}
				
				for(final Instruction instruction : this.instructions) {
					if(!instruction.accept(nodeHierarchicalVisitor)) {
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
	 * Returns {@code true} if, and only if, this {@code CodeAttribute} instance contains {@code attributeInfo}, {@code false} otherwise.
	 * <p>
	 * If {@code attributeInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param attributeInfo the {@link AttributeInfo} to check
	 * @return {@code true} if, and only if, this {@code CodeAttribute} instance contains {@code attributeInfo}, {@code false} otherwise
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
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code CodeAttribute}, and that {@code CodeAttribute} instance is equal to this {@code CodeAttribute} instance, {@code false} otherwise.
	 * 
	 * @param object an {@code Object} to compare to this {@code CodeAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code CodeAttribute}, and that {@code CodeAttribute} instance is equal to this {@code CodeAttribute} instance, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof CodeAttribute)) {
			return false;
		} else if(!Objects.equals(CodeAttribute.class.cast(object).getName(), getName())) {
			return false;
		} else if(CodeAttribute.class.cast(object).getAttributeNameIndex() != getAttributeNameIndex()) {
			return false;
		} else if(CodeAttribute.class.cast(object).getAttributeLength() != getAttributeLength()) {
			return false;
		} else if(CodeAttribute.class.cast(object).getMaxStack() != getMaxStack()) {
			return false;
		} else if(CodeAttribute.class.cast(object).getMaxLocals() != getMaxLocals()) {
			return false;
		} else if(CodeAttribute.class.cast(object).getCodeLength() != getCodeLength()) {
			return false;
		} else if(!Objects.equals(CodeAttribute.class.cast(object).instructions, this.instructions)) {
			return false;
		} else if(CodeAttribute.class.cast(object).getExceptionTableLength() != getExceptionTableLength()) {
			return false;
		} else if(!Objects.equals(CodeAttribute.class.cast(object).exceptionHandlers, this.exceptionHandlers)) {
			return false;
		} else if(CodeAttribute.class.cast(object).getAttributeInfoCount() != getAttributeInfoCount()) {
			return false;
		} else if(!Objects.equals(CodeAttribute.class.cast(object).attributeInfos, this.attributeInfos)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns the attributes_count of this {@code CodeAttribute} instance.
	 * 
	 * @return the attributes_count of this {@code CodeAttribute} instance
	 */
	public int getAttributeInfoCount() {
		return this.attributeInfos.size();
	}
	
	/**
	 * Returns the attribute_length of this {@code CodeAttribute} instance.
	 * 
	 * @return the attribute_length of this {@code CodeAttribute} instance
	 */
	@Override
	public int getAttributeLength() {
		int attributeLength = 0;
		
		attributeLength += 2;
		attributeLength += 2;
		attributeLength += 4;
		attributeLength += getCodeLength();
		attributeLength += 2;
		attributeLength += this.exceptionHandlers.size() * 8;
		attributeLength += 2;
		
		for(final AttributeInfo attributeInfo : this.attributeInfos) {
			attributeLength += attributeInfo.getAttributeLength() + 6;
		}
		
		return attributeLength;
	}
	
	/**
	 * Returns the code_length of this {@code CodeAttribute} instance.
	 * 
	 * @return the code_length of this {@code CodeAttribute} instance
	 */
	public int getCodeLength() {
		int codeLength = 0;
		
		for(final Instruction instruction : this.instructions) {
			codeLength += instruction.getLength();
		}
		
		return codeLength;
	}
	
	/**
	 * Returns the exception_table_length of this {@code CodeAttribute} instance.
	 * 
	 * @return the exception_table_length of this {@code CodeAttribute} instance
	 */
	public int getExceptionTableLength() {
		return this.exceptionHandlers.size();
	}
	
	/**
	 * Returns the max_locals of this {@code CodeAttribute} instance.
	 * 
	 * @return the max_locals of this {@code CodeAttribute} instance
	 */
	public int getMaxLocals() {
		return this.maxLocals;
	}
	
	/**
	 * Returns the max_stack of this {@code CodeAttribute} instance.
	 * 
	 * @return the max_stack of this {@code CodeAttribute} instance
	 */
	public int getMaxStack() {
		return this.maxStack;
	}
	
	/**
	 * Returns a hash code for this {@code CodeAttribute} instance.
	 * 
	 * @return a hash code for this {@code CodeAttribute} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), Integer.valueOf(getAttributeNameIndex()), Integer.valueOf(getAttributeLength()), Integer.valueOf(getMaxStack()), Integer.valueOf(getMaxLocals()), Integer.valueOf(getCodeLength()), this.instructions, Integer.valueOf(getExceptionTableLength()), this.exceptionHandlers, Integer.valueOf(getAttributeInfoCount()), this.attributeInfos);
	}
	
	/**
	 * Attempts to add {@code attributeInfo} to this {@code CodeAttribute} instance.
	 * <p>
	 * If {@code attributeInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code attributeInfo} (or an {@code AttributeInfo} instance that equals {@code attributeInfo}) has already been added prior to this method call, nothing will happen.
	 * 
	 * @param attributeInfo the {@code AttributeInfo} to add
	 * @throws NullPointerException thrown if, and only if, {@code attributeInfo} is {@code null}
	 */
	public void addAttributeInfo(final AttributeInfo attributeInfo) {
		if(!this.attributeInfos.contains(Objects.requireNonNull(attributeInfo, "attributeInfo == null"))) {
			this.attributeInfos.add(attributeInfo);
		}
	}
	
	/**
	 * Attempts to add {@code exceptionHandler} to this {@code CodeAttribute} instance.
	 * <p>
	 * If {@code exceptionHandler} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If {@code exceptionHandler} (or an {@code ExceptionHandler} instance that equals {@code exceptionHandler}) has already been added prior to this method call, nothing will happen.
	 * 
	 * @param exceptionHandler the {@code ExceptionHandler} to add
	 * @throws NullPointerException thrown if, and only if, {@code exceptionHandler} is {@code null}
	 */
	public void addExceptionHandler(final ExceptionHandler exceptionHandler) {
		if(!this.exceptionHandlers.contains(Objects.requireNonNull(exceptionHandler, "exceptionHandler == null"))) {
			this.exceptionHandlers.add(exceptionHandler);
		}
	}
	
	/**
	 * Adds {@code instruction} to this {@code CodeAttribute} instance.
	 * <p>
	 * If {@code instruction} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param instruction the {@code Instruction} to add
	 * @throws NullPointerException thrown if, and only if, {@code instruction} is {@code null}
	 */
	public void addInstruction(final Instruction instruction) {
		this.instructions.add(Objects.requireNonNull(instruction, "instruction == null"));
	}
	
	/**
	 * Attempts to remove {@code attributeInfo} from this {@code CodeAttribute} instance.
	 * <p>
	 * If {@code attributeInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If no {@code AttributeInfo} equal to {@code attributeInfo} can be found, nothing will happen.
	 * 
	 * @param attributeInfo the {@code AttributeInfo} to remove
	 * @throws NullPointerException thrown if, and only if, {@code attributeInfo} is {@code null}
	 */
	public void removeAttributeInfo(final AttributeInfo attributeInfo) {
		this.attributeInfos.remove(Objects.requireNonNull(attributeInfo, "attributeInfo == null"));
	}
	
	/**
	 * Attempts to remove {@code exceptionHandler} from this {@code CodeAttribute} instance.
	 * <p>
	 * If {@code exceptionHandler} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If no {@code ExceptionHandler} equal to {@code exceptionHandler} can be found, nothing will happen.
	 * 
	 * @param exceptionHandler the {@code ExceptionHandler} to remove
	 * @throws NullPointerException thrown if, and only if, {@code exceptionHandler} is {@code null}
	 */
	public void removeExceptionHandler(final ExceptionHandler exceptionHandler) {
		this.exceptionHandlers.remove(Objects.requireNonNull(exceptionHandler, "exceptionHandler == null"));
	}
	
	/**
	 * Attempts to remove {@code instruction} from this {@code CodeAttribute} instance.
	 * <p>
	 * If {@code instruction} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If no {@code Instruction} equal to {@code instruction} can be found, nothing will happen.
	 * 
	 * @param instruction the {@code Instruction} to remove
	 * @throws NullPointerException thrown if, and only if, {@code instruction} is {@code null}
	 */
	public void removeInstruction(final Instruction instruction) {
		this.instructions.remove(Objects.requireNonNull(instruction, "instruction == null"));
	}
	
	/**
	 * Sets a new max_locals for this {@code CodeAttribute} instance.
	 * <p>
	 * If {@code maxLocals} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param maxLocals the new max_locals for this {@code CodeAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code maxLocals} is less than {@code 0}
	 */
	public void setMaxLocals(final int maxLocals) {
		this.maxLocals = ParameterArguments.requireRange(maxLocals, 0, Integer.MAX_VALUE);
	}
	
	/**
	 * Sets a new max_stack for this {@code CodeAttribute} instance.
	 * <p>
	 * If {@code maxStack} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param maxStack the new max_stack for this {@code CodeAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code maxStack} is less than {@code 0}
	 */
	public void setMaxStack(final int maxStack) {
		this.maxStack = ParameterArguments.requireRange(maxStack, 0, Integer.MAX_VALUE);
	}
	
	/**
	 * Writes this {@code CodeAttribute} to {@code dataOutput}.
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
			dataOutput.writeShort(getMaxStack());
			dataOutput.writeShort(getMaxLocals());
			dataOutput.writeInt(getCodeLength());
			
			this.instructions.forEach(instruction -> instruction.write(dataOutput));
			
			dataOutput.writeShort(getExceptionTableLength());
			
			this.exceptionHandlers.forEach(exceptionHandler -> exceptionHandler.write(dataOutput));
			
			dataOutput.writeShort(this.attributeInfos.size());
			
			this.attributeInfos.forEach(attributeInfo -> attributeInfo.write(dataOutput));
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code CodeAttribute}s.
	 * <p>
	 * All {@code CodeAttribute}s are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code CodeAttribute}s
	 * @throws NullPointerException thrown if, and only if, {@code node} is {@code null}
	 */
	public static List<CodeAttribute> filter(final Node node) {
		return NodeFilter.filter(node, NodeFilter.any(), CodeAttribute.class);
	}
	
	/**
	 * Attempts to find a {@code CodeAttribute} instance in {@code methodInfo}.
	 * <p>
	 * Returns an {@code Optional} with the optional {@code CodeAttribute} instance.
	 * <p>
	 * If {@code methodInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param methodInfo the {@link MethodInfo} to check in
	 * @return an {@code Optional} with the optional {@code CodeAttribute} instance
	 * @throws NullPointerException thrown if, and only if, {@code methodInfo} is {@code null}
	 */
	public static Optional<CodeAttribute> find(final MethodInfo methodInfo) {
		return methodInfo.getAttributeInfos().stream().filter(attributeInfo -> attributeInfo instanceof CodeAttribute).map(attributeInfo -> CodeAttribute.class.cast(attributeInfo)).findFirst();
	}
}