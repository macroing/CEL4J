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
package org.macroing.cel4j.java.binary.classfile.descriptor;

import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.MethodInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;
import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.scanner.TextScanner;
import org.macroing.cel4j.util.ParameterArguments;

//TODO: Add Javadocs!
public final class MethodDescriptor implements Node {
	private final List<ParameterDescriptor> parameterDescriptors;
	private final ReturnDescriptor returnDescriptor;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	MethodDescriptor(final ReturnDescriptor returnDescriptor, final ParameterDescriptor... parameterDescriptors) {
		this(returnDescriptor, ParameterArguments.requireNonNullList(Arrays.asList(parameterDescriptors.clone()), "parameterDescriptors"));
	}
	
	MethodDescriptor(final ReturnDescriptor returnDescriptor, final List<ParameterDescriptor> parameterDescriptors) {
		this.parameterDescriptors = parameterDescriptors;
		this.returnDescriptor = returnDescriptor;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public List<ParameterDescriptor> getParameterDescriptors() {
		return new ArrayList<>(this.parameterDescriptors);
	}
	
//	TODO: Add Javadocs!
	public ReturnDescriptor getReturnDescriptor() {
		return this.returnDescriptor;
	}
	
//	TODO: Add Javadocs!
	public String toExternalForm() {
		return String.format("%s(%s)", this.returnDescriptor.toExternalForm(), this.parameterDescriptors.stream().map(parameterDescriptor -> parameterDescriptor.toExternalForm()).collect(Collectors.joining(",")));
	}
	
//	TODO: Add Javadocs!
	public String toInternalForm() {
		return String.format("(%s)%s", this.parameterDescriptors.stream().map(parameterDescriptor -> parameterDescriptor.toInternalForm()).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append), this.returnDescriptor.toInternalForm());
	}
	
//	TODO: Add Javadocs!
	@Override
	public String toString() {
		return String.format("MethodDescriptor: [ReturnDescriptor=%s], [ParameterDescriptors=%s], [InternalForm=%s]", getReturnDescriptor(), getParameterDescriptors(), toInternalForm());
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				if(!this.returnDescriptor.accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
				}
				
				for(final ParameterDescriptor parameterDescriptor : this.parameterDescriptors) {
					if(!parameterDescriptor.accept(nodeHierarchicalVisitor)) {
						return nodeHierarchicalVisitor.visitLeave(this);
					}
				}
			}
			
			return nodeHierarchicalVisitor.visitLeave(this);
		} catch(final RuntimeException e) {
			throw new NodeTraversalException(e);
		}
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof MethodDescriptor)) {
			return false;
		} else if(!Objects.equals(MethodDescriptor.class.cast(object).returnDescriptor, this.returnDescriptor)) {
			return false;
		} else if(!Objects.equals(MethodDescriptor.class.cast(object).parameterDescriptors, this.parameterDescriptors)) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(this.returnDescriptor, this.parameterDescriptors);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static List<MethodDescriptor> parseMethodDescriptors(final ClassFile classFile) {
		return classFile.getMethodInfos().stream().map(methodInfo -> parseMethodDescriptor(classFile, methodInfo)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
//	TODO: Add Javadocs!
	public static MethodDescriptor parseMethodDescriptor(final ClassFile classFile, final MethodInfo methodInfo) {
		return parseMethodDescriptor(classFile.getCPInfo(classFile.getMethodInfo(methodInfo).getDescriptorIndex(), ConstantUTF8Info.class).getString());
	}
	
//	TODO: Add Javadocs!
	public static MethodDescriptor parseMethodDescriptor(final String string) {
		return Parsers.parseMethodDescriptor(new TextScanner(string));
	}
	
//	TODO: Add Javadocs!
	public static MethodDescriptor valueOf(final ReturnDescriptor returnDescriptor, final ParameterDescriptor... parameterDescriptors) {
		return new MethodDescriptor(Objects.requireNonNull(returnDescriptor, "returnDescriptor == null"), Objects.requireNonNull(parameterDescriptors, "parameterDescriptors == null"));
	}
}