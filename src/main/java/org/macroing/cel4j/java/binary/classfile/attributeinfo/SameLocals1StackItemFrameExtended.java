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
import java.util.Objects;

import org.macroing.cel4j.node.NodeHierarchicalVisitor;
import org.macroing.cel4j.node.NodeTraversalException;
import org.macroing.cel4j.util.ParameterArguments;

//TODO: Add Javadocs!
public final class SameLocals1StackItemFrameExtended implements StackMapFrame {
	private final VerificationTypeInfo verificationTypeInfo;
	private final int frameType;
	private final int offsetDelta;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private SameLocals1StackItemFrameExtended(final int frameType, final int offsetDelta, final VerificationTypeInfo verificationTypeInfo) {
		this.frameType = frameType;
		this.offsetDelta = offsetDelta;
		this.verificationTypeInfo = verificationTypeInfo;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	@Override
	public SameLocals1StackItemFrameExtended copy() {
		return new SameLocals1StackItemFrameExtended(this.frameType, this.offsetDelta, this.verificationTypeInfo.copy());
	}
	
//	TODO: Add Javadocs!
	public VerificationTypeInfo getVerificationTypeInfo() {
		return this.verificationTypeInfo;
	}
	
//	TODO: Add Javadocs!
	@Override
	public boolean accept(final NodeHierarchicalVisitor nodeHierarchicalVisitor) {
		Objects.requireNonNull(nodeHierarchicalVisitor, "nodeHierarchicalVisitor == null");
		
		try {
			if(nodeHierarchicalVisitor.visitEnter(this)) {
				if(!this.verificationTypeInfo.accept(nodeHierarchicalVisitor)) {
					return nodeHierarchicalVisitor.visitLeave(this);
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
		} else if(!(object instanceof SameLocals1StackItemFrameExtended)) {
			return false;
		} else if(!Objects.equals(Integer.valueOf(this.frameType), Integer.valueOf(SameLocals1StackItemFrameExtended.class.cast(object).frameType))) {
			return false;
		} else if(!Objects.equals(Integer.valueOf(this.offsetDelta), Integer.valueOf(SameLocals1StackItemFrameExtended.class.cast(object).offsetDelta))) {
			return false;
		} else if(!Objects.equals(this.verificationTypeInfo, SameLocals1StackItemFrameExtended.class.cast(object).verificationTypeInfo)) {
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
		return 3 + this.verificationTypeInfo.getLength();
	}
	
//	TODO: Add Javadocs!
	public int getOffsetDelta() {
		return this.offsetDelta;
	}
	
//	TODO: Add Javadocs!
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(this.frameType), Integer.valueOf(this.offsetDelta), this.verificationTypeInfo);
	}
	
//	TODO: Add Javadocs!
	@Override
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeByte(this.frameType);
			dataOutput.writeShort(this.offsetDelta);
			
			this.verificationTypeInfo.write(dataOutput);
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static SameLocals1StackItemFrameExtended newInstance(final int frameType, final int offsetDelta, final VerificationTypeInfo verificationTypeInfo) {
		return new SameLocals1StackItemFrameExtended(ParameterArguments.requireRange(frameType, 247, 247, "frameType"), ParameterArguments.requireRange(offsetDelta, 0, Integer.MAX_VALUE, "offsetDelta"), Objects.requireNonNull(verificationTypeInfo, "verificationTypeInfo == null"));
	}
}