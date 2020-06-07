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
import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.ParameterArguments;

//TODO: Add Javadocs!
public final class AppendFrame implements StackMapFrame {
	private final List<VerificationTypeInfo> locals;
	private final int frameType;
	private final int offsetDelta;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private AppendFrame(final int frameType, final int offsetDelta, final List<VerificationTypeInfo> locals) {
		this.frameType = frameType;
		this.offsetDelta = offsetDelta;
		this.locals = locals;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	@Override
	public AppendFrame copy() {
		final List<VerificationTypeInfo> locals = this.locals.stream().map(local -> local.copy()).collect(Collectors.toCollection(ArrayList::new));
		
		return new AppendFrame(this.frameType, this.offsetDelta, locals);
	}
	
//	TODO: Add Javadocs!
	public List<VerificationTypeInfo> getLocals() {
		return new ArrayList<>(this.locals);
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				for(final VerificationTypeInfo local : this.locals) {
					if(!local.accept(nodeHierarchicalVisitor)) {
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
		} else if(!(object instanceof AppendFrame)) {
			return false;
		} else if(!Objects.equals(Integer.valueOf(this.frameType), Integer.valueOf(AppendFrame.class.cast(object).frameType))) {
			return false;
		} else if(!Objects.equals(Integer.valueOf(this.offsetDelta), Integer.valueOf(AppendFrame.class.cast(object).offsetDelta))) {
			return false;
		} else if(!Objects.equals(this.locals, AppendFrame.class.cast(object).locals)) {
			return false;
		} else {
			return true;
		}
	}
	
//	TODO: Add Javadocs!
	@Override
	public int getFrameType() {
		return this.frameType;
	}
	
//	TODO: Add Javadocs!
	@Override
	public int getLength() {
		int length = 3;
		
		for(final VerificationTypeInfo local : this.locals) {
			length += local.getLength();
		}
		
		return length;
	}
	
//	TODO: Add Javadocs!
	public int getOffsetDelta() {
		return this.offsetDelta;
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(this.frameType), Integer.valueOf(this.offsetDelta), this.locals);
	}
	
//	TODO: Add Javadocs!
	@Override
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeByte(this.frameType);
			dataOutput.writeShort(this.offsetDelta);
			
			for(final VerificationTypeInfo local : this.locals) {
				local.write(dataOutput);
			}
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static AppendFrame newInstance(final int frameType, final int offsetDelta, final List<VerificationTypeInfo> locals) {
		final int frameType0 = ParameterArguments.requireRange(frameType, 252, 254, "frameType");
		final int offsetDelta0 = ParameterArguments.requireRange(offsetDelta, 0, Integer.MAX_VALUE, "offsetDelta");
		
		final List<VerificationTypeInfo> locals0 = new ArrayList<>(ParameterArguments.requireNonNullList(locals, "locals"));
		
		if(locals0.size() != frameType0 - 251) {
			throw new IllegalArgumentException(String.format("locals.size() != frameType - 251: Expected %s but found %s", Integer.valueOf(frameType0 - 251), Integer.valueOf(locals0.size())));
		}
		
		return new AppendFrame(frameType0, offsetDelta0, locals0);
	}
}