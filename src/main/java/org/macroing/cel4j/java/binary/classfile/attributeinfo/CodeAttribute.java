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
import java.util.Optional;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.java.binary.classfile.MethodInfo;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeFilter;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * A {@code CodeAttribute} represents a {@code Code_attribute} structure as defined by the Java Virtual Machine Specifications.
 * <p>
 * This class is mutable and not thread-safe.
 * <p>
 * The {@code Code_attribute} structure has the following format:
 * <pre>
 * <code>
 * Code_attribute {
 *     u2 attribute_name_index;
 *     u4 attribute_length;
 *     u2 max_stack;
 *     u2 max_locals;
 *     u4 code_length;
 *     u1[code_length] code;
 *     u2 exception_table_length;
 *     exception_handler[exception_table_length] exception_table;
 *     u2 attributes_count;
 *     attribute_info[attributes_count] attributes;
 * }
 * </code>
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class CodeAttribute extends AttributeInfo {
	/**
	 * The name of the {@code Code_attribute} structure.
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
	 * Constructs a new {@code CodeAttribute} instance that is a copy of {@code codeAttribute}.
	 * <p>
	 * If {@code codeAttribute} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param codeAttribute the {@code CodeAttribute} instance to copy
	 * @throws NullPointerException thrown if, and only if, {@code codeAttribute} is {@code null}
	 */
	public CodeAttribute(final CodeAttribute codeAttribute) {
		super(NAME, codeAttribute.getAttributeNameIndex());
		
		this.attributeInfos = codeAttribute.attributeInfos.stream().map(attributeInfo -> attributeInfo.copy()).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
		this.exceptionHandlers = codeAttribute.exceptionHandlers.stream().map(exceptionHandler -> exceptionHandler.copy()).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
		this.instructions = codeAttribute.instructions.stream().collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
		this.maxLocals = codeAttribute.maxLocals;
		this.maxStack = codeAttribute.maxStack;
	}
	
	/**
	 * Constructs a new {@code CodeAttribute} instance.
	 * <p>
	 * If {@code attributeNameIndex} is less than {@code 1}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeNameIndex the value for the {@code attribute_name_index} item associated with this {@code CodeAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code attributeNameIndex} is less than {@code 1}
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
	 * Returns the first {@link AttributeInfo} instance of this {@code CodeAttribute} instance that is equal to {@code attributeInfo}.
	 * <p>
	 * If {@code attributeInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If this {@code CodeAttribute} instance does not contain an {@code AttributeInfo} instance that is equal to {@code attributeInfo}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param attributeInfo the {@code AttributeInfo} instance to test for equality against
	 * @return the first {@code AttributeInfo} instance of this {@code CodeAttribute} instance that is equal to {@code attributeInfo}
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
		return new CodeAttribute(this);
	}
	
	/**
	 * Returns a {@code List} with all currently added {@link AttributeInfo} instances.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code CodeAttribute} instance.
	 * 
	 * @return a {@code List} with all currently added {@code AttributeInfo} instances
	 */
	public List<AttributeInfo> getAttributeInfos() {
		return new ArrayList<>(this.attributeInfos);
	}
	
	/**
	 * Returns a {@code List} with all currently added {@link ExceptionHandler} instances.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code CodeAttribute} instance.
	 * 
	 * @return a {@code List} with all currently added {@code ExceptionHandler} instances
	 */
	public List<ExceptionHandler> getExceptionHandlers() {
		return new ArrayList<>(this.exceptionHandlers);
	}
	
	/**
	 * Returns a {@code List} with all currently added {@link Instruction} instances.
	 * <p>
	 * Modifying the returned {@code List} will not affect this {@code CodeAttribute} instance.
	 * 
	 * @return a {@code List} with all currently added {@code Instruction} instances
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
		return String.format("new CodeAttribute(%s)", Integer.toString(getAttributeNameIndex()));
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
	 * Compares {@code object} to this {@code CodeAttribute} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code CodeAttribute}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code CodeAttribute} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code CodeAttribute}, and their respective values are equal, {@code false} otherwise
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
	 * Returns the value of the {@code attributes_count} item associated with this {@code CodeAttribute} instance.
	 * 
	 * @return the value of the {@code attributes_count} item associated with this {@code CodeAttribute} instance
	 */
	public int getAttributeInfoCount() {
		return this.attributeInfos.size();
	}
	
	/**
	 * Returns the value of the {@code attribute_length} item associated with this {@code CodeAttribute} instance.
	 * 
	 * @return the value of the {@code attribute_length} item associated with this {@code CodeAttribute} instance
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
	 * Returns the value of the {@code code_length} item associated with this {@code CodeAttribute} instance.
	 * 
	 * @return the value of the {@code code_length} item associated with this {@code CodeAttribute} instance
	 */
	public int getCodeLength() {
		int codeLength = 0;
		
		for(final Instruction instruction : this.instructions) {
			codeLength += instruction.getLength();
		}
		
		return codeLength;
	}
	
	/**
	 * Returns the value of the {@code exception_table_length} item associated with this {@code CodeAttribute} instance.
	 * 
	 * @return the value of the {@code exception_table_length} item associated with this {@code CodeAttribute} instance
	 */
	public int getExceptionTableLength() {
		return this.exceptionHandlers.size();
	}
	
	/**
	 * Returns the value of the {@code max_locals} item associated with this {@code CodeAttribute} instance.
	 * 
	 * @return the value of the {@code max_locals} item associated with this {@code CodeAttribute} instance
	 */
	public int getMaxLocals() {
		return this.maxLocals;
	}
	
	/**
	 * Returns the value of the {@code max_stack} item associated with this {@code CodeAttribute} instance.
	 * 
	 * @return the value of the {@code max_stack} item associated with this {@code CodeAttribute} instance
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
	 * Adds {@code attributeInfo} to this {@code CodeAttribute} instance, if absent.
	 * <p>
	 * If {@code attributeInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param attributeInfo the {@link AttributeInfo} to add
	 * @throws NullPointerException thrown if, and only if, {@code attributeInfo} is {@code null}
	 */
	public void addAttributeInfo(final AttributeInfo attributeInfo) {
		if(!this.attributeInfos.contains(Objects.requireNonNull(attributeInfo, "attributeInfo == null"))) {
			this.attributeInfos.add(attributeInfo);
		}
	}
	
	/**
	 * Adds {@code exceptionHandler} to this {@code CodeAttribute} instance, if absent.
	 * <p>
	 * If {@code exceptionHandler} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param exceptionHandler the {@link ExceptionHandler} to add
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
	 * @param instruction the {@link Instruction} to add
	 * @throws NullPointerException thrown if, and only if, {@code instruction} is {@code null}
	 */
	public void addInstruction(final Instruction instruction) {
		this.instructions.add(Objects.requireNonNull(instruction, "instruction == null"));
	}
	
	/**
	 * Removes {@code attributeInfo} from this {@code CodeAttribute} instance, if present.
	 * <p>
	 * If {@code attributeInfo} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param attributeInfo the {@link AttributeInfo} to remove
	 * @throws NullPointerException thrown if, and only if, {@code attributeInfo} is {@code null}
	 */
	public void removeAttributeInfo(final AttributeInfo attributeInfo) {
		this.attributeInfos.remove(Objects.requireNonNull(attributeInfo, "attributeInfo == null"));
	}
	
	/**
	 * Removes {@code exceptionHandler} from this {@code CodeAttribute} instance, if present.
	 * <p>
	 * If {@code exceptionHandler} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param exceptionHandler the {@link ExceptionHandler} to remove
	 * @throws NullPointerException thrown if, and only if, {@code exceptionHandler} is {@code null}
	 */
	public void removeExceptionHandler(final ExceptionHandler exceptionHandler) {
		this.exceptionHandlers.remove(Objects.requireNonNull(exceptionHandler, "exceptionHandler == null"));
	}
	
	/**
	 * Removes {@code instruction} from this {@code CodeAttribute} instance, if present.
	 * <p>
	 * If {@code instruction} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param instruction the {@link Instruction} to remove
	 * @throws NullPointerException thrown if, and only if, {@code instruction} is {@code null}
	 */
	public void removeInstruction(final Instruction instruction) {
		this.instructions.remove(Objects.requireNonNull(instruction, "instruction == null"));
	}
	
	/**
	 * Sets {@code maxLocals} as the value for the {@code max_locals} item associated with this {@code CodeAttribute} instance.
	 * <p>
	 * If {@code maxLocals} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param maxLocals the value for the {@code max_locals} item associated with this {@code CodeAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code maxLocals} is less than {@code 0}
	 */
	public void setMaxLocals(final int maxLocals) {
		this.maxLocals = ParameterArguments.requireRange(maxLocals, 0, Integer.MAX_VALUE);
	}
	
	/**
	 * Sets {@code maxStack} as the value for the {@code max_stack} item associated with this {@code CodeAttribute} instance.
	 * <p>
	 * If {@code maxStack} is less than {@code 0}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param maxStack the value for the {@code max_stack} item associated with this {@code CodeAttribute} instance
	 * @throws IllegalArgumentException thrown if, and only if, {@code maxStack} is less than {@code 0}
	 */
	public void setMaxStack(final int maxStack) {
		this.maxStack = ParameterArguments.requireRange(maxStack, 0, Integer.MAX_VALUE);
	}
	
	/**
	 * Writes this {@code CodeAttribute} to {@code dataOutput}.
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
			final List<AttributeInfo> attributeInfos = this.attributeInfos;
			final List<ExceptionHandler> exceptionHandlers = this.exceptionHandlers;
			final List<Instruction> instructions = this.instructions;
			
			dataOutput.writeShort(getAttributeNameIndex());
			dataOutput.writeInt(getAttributeLength());
			dataOutput.writeShort(getMaxStack());
			dataOutput.writeShort(getMaxLocals());
			dataOutput.writeInt(getCodeLength());
			
			for(final Instruction instruction : instructions) {
				instruction.write(dataOutput);
			}
			
			dataOutput.writeShort(getExceptionTableLength());
			
			for(final ExceptionHandler exceptionHandler : exceptionHandlers) {
				exceptionHandler.write(dataOutput);
			}
			
			dataOutput.writeShort(attributeInfos.size());
			
			for(final AttributeInfo attributeInfo : attributeInfos) {
				attributeInfo.write(dataOutput);
			}
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all {@code CodeAttribute} instances in {@code node}.
	 * <p>
	 * All {@code CodeAttribute} instances are found by traversing {@code node} using a simple {@link NodeHierarchicalVisitor} implementation.
	 * <p>
	 * If {@code node} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param node the {@link Node} to start traversal from
	 * @return a {@code List} with all {@code CodeAttribute} instances in {@code node}
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