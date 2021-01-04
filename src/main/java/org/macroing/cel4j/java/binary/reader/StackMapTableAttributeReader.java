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
package org.macroing.cel4j.java.binary.reader;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.macroing.cel4j.java.binary.classfile.AttributeInfo;
import org.macroing.cel4j.java.binary.classfile.CPInfo;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.AppendFrame;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.ChopFrame;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.DoubleVariableInfo;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.FloatVariableInfo;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.FullFrame;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.IntegerVariableInfo;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.LongVariableInfo;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.NullVariableInfo;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.ObjectVariableInfo;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.SameFrame;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.SameFrameExtended;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.SameLocals1StackItemFrame;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.SameLocals1StackItemFrameExtended;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.StackMapFrame;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.StackMapTableAttribute;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.TopVariableInfo;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.UninitializedThisVariableInfo;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.UninitializedVariableInfo;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.VerificationTypeInfo;

final class StackMapTableAttributeReader implements AttributeInfoReader {
	public StackMapTableAttributeReader() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public AttributeInfo read(final DataInput dataInput, final int attributeNameIndex, final List<CPInfo> constantPool) {
		try {
			final StackMapTableAttribute stackMapTableAttribute = new StackMapTableAttribute(attributeNameIndex);
			
			final int numberOfEntries = dataInput.readUnsignedShort();
			
			for(int i = 0; i < numberOfEntries; i++) {
				stackMapTableAttribute.addEntry(doReadStackMapFrame(dataInput));
			}
			
			return stackMapTableAttribute;
		} catch(final IOException | IllegalArgumentException e) {
			throw new AttributeInfoReaderException("Unable to read StackMapTable_attribute", e);
		}
	}
	
	@Override
	public boolean isSupported(final String name) {
		return name.equals(StackMapTableAttribute.NAME);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static StackMapFrame doReadStackMapFrame(final DataInput dataInput) throws IOException {
		final int frameType = dataInput.readByte() & 0xFF;
		
		if(frameType >= 0 && frameType <= 63) {
			return new SameFrame(frameType);
		} else if(frameType >= 64 && frameType <= 127) {
			return new SameLocals1StackItemFrame(frameType, doReadVerificationTypeInfo(dataInput));
		} else if(frameType == 247) {
			return new SameLocals1StackItemFrameExtended(frameType, dataInput.readShort(), doReadVerificationTypeInfo(dataInput));
		} else if(frameType >= 248 && frameType <= 250) {
			return new ChopFrame(frameType, dataInput.readShort());
		} else if(frameType == 251) {
			return new SameFrameExtended(frameType, dataInput.readShort());
		} else if(frameType >= 252 && frameType <= 254) {
			final int offsetDelta = dataInput.readShort();
			final int numberOfLocals = frameType - 251;
			
			final List<VerificationTypeInfo> locals = new ArrayList<>();
			
			for(int i = 0; i < numberOfLocals; i++) {
				locals.add(doReadVerificationTypeInfo(dataInput));
			}
			
			return new AppendFrame(frameType, offsetDelta, locals);
		} else if(frameType == 255) {
			final int offsetDelta = dataInput.readShort();
			final int numberOfLocals = dataInput.readShort();
			
			final List<VerificationTypeInfo> locals = new ArrayList<>();
			
			for(int i = 0; i < numberOfLocals; i++) {
				locals.add(doReadVerificationTypeInfo(dataInput));
			}
			
			final int numberOfStackItems = dataInput.readShort();
			
			final List<VerificationTypeInfo> stack = new ArrayList<>();
			
			for(int i = 0; i < numberOfStackItems; i++) {
				stack.add(doReadVerificationTypeInfo(dataInput));
			}
			
			return new FullFrame(frameType, offsetDelta, locals, stack);
		}
		
		throw new AttributeInfoReaderException(String.format("Illegal frame_type: %s", Integer.toString(frameType)));
	}
	
	private static VerificationTypeInfo doReadVerificationTypeInfo(final DataInput dataInput) throws IOException {
		final int tag = dataInput.readByte() & 0xFF;
		
		switch(tag) {
			case VerificationTypeInfo.ITEM_DOUBLE:
				return DoubleVariableInfo.getInstance();
			case VerificationTypeInfo.ITEM_FLOAT:
				return FloatVariableInfo.getInstance();
			case VerificationTypeInfo.ITEM_INTEGER:
				return IntegerVariableInfo.getInstance();
			case VerificationTypeInfo.ITEM_LONG:
				return LongVariableInfo.getInstance();
			case VerificationTypeInfo.ITEM_NULL:
				return NullVariableInfo.getInstance();
			case VerificationTypeInfo.ITEM_OBJECT:
				return new ObjectVariableInfo(dataInput.readShort());
			case VerificationTypeInfo.ITEM_TOP:
				return TopVariableInfo.getInstance();
			case VerificationTypeInfo.ITEM_UNINITIALIZED:
				return new UninitializedVariableInfo(dataInput.readShort());
			case VerificationTypeInfo.ITEM_UNINITIALIZED_THIS:
				return UninitializedThisVariableInfo.getInstance();
			default:
				throw new AttributeInfoReaderException(String.format("Illegal tag: %s", Integer.toString(tag)));
		}
	}
}