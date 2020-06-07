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
package org.macroing.cel4j.java.binary.reader;

import java.io.DataInput;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.macroing.cel4j.java.binary.classfile.CPInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantClassInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantDoubleInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantDynamicInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantFieldRefInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantFloatInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantIntegerInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantInterfaceMethodRefInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantInvokeDynamicInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantLongInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantMethodHandleInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantMethodRefInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantMethodTypeInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantModuleInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantNameAndTypeInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantPackageInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantStringInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;
import org.macroing.cel4j.node.NodeFormatException;
import org.macroing.cel4j.util.ParameterArguments;

final class CPInfoReaderImpl implements CPInfoReader {
	private final Map<Integer, CPInfoReader> cPInfoReaders;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public CPInfoReaderImpl() {
		this.cPInfoReaders = new HashMap<>();
		this.cPInfoReaders.put(Integer.valueOf(ConstantClassInfo.TAG), new ConstantClassInfoReader());
		this.cPInfoReaders.put(Integer.valueOf(ConstantDoubleInfo.TAG), new ConstantDoubleInfoReader());
		this.cPInfoReaders.put(Integer.valueOf(ConstantDynamicInfo.TAG), new ConstantDynamicInfoReader());
		this.cPInfoReaders.put(Integer.valueOf(ConstantFieldRefInfo.TAG), new ConstantFieldRefInfoReader());
		this.cPInfoReaders.put(Integer.valueOf(ConstantFloatInfo.TAG), new ConstantFloatInfoReader());
		this.cPInfoReaders.put(Integer.valueOf(ConstantIntegerInfo.TAG), new ConstantIntegerInfoReader());
		this.cPInfoReaders.put(Integer.valueOf(ConstantInterfaceMethodRefInfo.TAG), new ConstantInterfaceMethodRefInfoReader());
		this.cPInfoReaders.put(Integer.valueOf(ConstantInvokeDynamicInfo.TAG), new ConstantInvokeDynamicInfoReader());
		this.cPInfoReaders.put(Integer.valueOf(ConstantLongInfo.TAG), new ConstantLongInfoReader());
		this.cPInfoReaders.put(Integer.valueOf(ConstantMethodHandleInfo.TAG), new ConstantMethodHandleInfoReader());
		this.cPInfoReaders.put(Integer.valueOf(ConstantMethodRefInfo.TAG), new ConstantMethodRefInfoReader());
		this.cPInfoReaders.put(Integer.valueOf(ConstantMethodTypeInfo.TAG), new ConstantMethodTypeInfoReader());
		this.cPInfoReaders.put(Integer.valueOf(ConstantModuleInfo.TAG), new ConstantModuleInfoReader());
		this.cPInfoReaders.put(Integer.valueOf(ConstantNameAndTypeInfo.TAG), new ConstantNameAndTypeInfoReader());
		this.cPInfoReaders.put(Integer.valueOf(ConstantPackageInfo.TAG), new ConstantPackageInfoReader());
		this.cPInfoReaders.put(Integer.valueOf(ConstantStringInfo.TAG), new ConstantStringInfoReader());
		this.cPInfoReaders.put(Integer.valueOf(ConstantUTF8Info.TAG), new ConstantUTF8InfoReader());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public CPInfo readCPInfo(final DataInput dataInput, final int tag) {
		final Integer tag0 = Integer.valueOf(ParameterArguments.requireRange(tag, 0, Integer.MAX_VALUE));
		
		final CPInfoReader cPInfoReader = this.cPInfoReaders.get(tag0);
		
		if(cPInfoReader == null) {
			throw new NodeFormatException(String.format("Unable to read cp_info: tag=%s", tag0));
		}
		
		return cPInfoReader.readCPInfo(Objects.requireNonNull(dataInput, "dataInput == null"), tag);
	}
	
	@Override
	public boolean isCPInfoReadingSupportedFor(final int tag) {
		return this.cPInfoReaders.get(Integer.valueOf(ParameterArguments.requireRange(tag, 0, Integer.MAX_VALUE))) != null;
	}
}