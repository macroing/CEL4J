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
package org.macroing.cel4j.java.decompiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.java.binary.classfile.ClassFile;
import org.macroing.cel4j.java.binary.classfile.attributeinfo.InnerClass;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantClassInfo;
import org.macroing.cel4j.java.binary.classfile.cpinfo.ConstantUTF8Info;
import org.macroing.cel4j.java.binary.classfile.descriptor.ClassName;

final class InnerType {
	private final ClassFile classFile;
	private final InnerClass innerClass;
	private final Type type;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	InnerType(final ClassFile classFile, final InnerClass innerClass) {
		this.classFile = Objects.requireNonNull(classFile, "classFile == null");
		this.innerClass = Objects.requireNonNull(innerClass, "innerClass == null");
		this.type = Type.valueOf(ClassName.parseClassName(classFile.getCPInfo(classFile.getCPInfo(innerClass.getInnerClassInfoIndex(), ConstantClassInfo.class).getNameIndex(), ConstantUTF8Info.class).getStringValue()).toExternalForm());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public List<Modifier> getModifiers() {
		final List<Modifier> modifiers = new ArrayList<>();
		
		if(isPrivate()) {
			modifiers.add(Modifier.PRIVATE);
		} else if(isProtected()) {
			modifiers.add(Modifier.PROTECTED);
		} else if(isPublic()) {
			modifiers.add(Modifier.PUBLIC);
		}
		
		if(isStatic()) {
			modifiers.add(Modifier.STATIC);
		}
		
		if(isAbstract()) {
			modifiers.add(Modifier.ABSTRACT);
		} else if(isFinal()) {
			modifiers.add(Modifier.FINAL);
		}
		
		return modifiers;
	}
	
	public String getSimpleName() {
		return this.innerClass.getInnerNameIndex() != 0 ? this.classFile.getCPInfo(this.innerClass.getInnerNameIndex(), ConstantUTF8Info.class).getStringValue() : "";
	}
	
	public Type getType() {
		return this.type;
	}
	
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof InnerType)) {
			return false;
		} else if(!Objects.equals(this.classFile, InnerType.class.cast(object).classFile)) {
			return false;
		} else if(!Objects.equals(this.innerClass, InnerType.class.cast(object).innerClass)) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isAbstract() {
		return this.innerClass.isAbstract();
	}
	
	public boolean isFinal() {
		return this.innerClass.isFinal();
	}
	
	public boolean isPackageProtected() {
		return this.innerClass.isPackageProtected();
	}
	
	public boolean isPrivate() {
		return this.innerClass.isPrivate();
	}
	
	public boolean isProtected() {
		return this.innerClass.isProtected();
	}
	
	public boolean isPublic() {
		return this.innerClass.isPublic();
	}
	
	public boolean isStatic() {
		return this.innerClass.isStatic();
	}
	
	public boolean isSynthetic() {
		return this.innerClass.isSynthetic();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.classFile, this.innerClass);
	}
}