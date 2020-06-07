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
package org.macroing.cel4j.php.model;

import java.lang.reflect.Field;//TODO: Add Javadocs!
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.Strings;

//TODO: Add Javadocs!
public final class PMethod implements Comparable<PMethod> {
	private static final int ACCESS_FLAG_ABSTRACT = 0x0400;
	private static final int ACCESS_FLAG_FINAL = 0x0010;
	private static final int ACCESS_FLAG_PRIVATE = 0x0002;
	private static final int ACCESS_FLAG_PROTECTED = 0x0004;
	private static final int ACCESS_FLAG_PUBLIC = 0x0001;
	private static final int ACCESS_FLAG_STATIC = 0x0008;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<PParameterArgument> pParameterArguments;
	private final PBlock pBlock;
	private PReturnType pReturnType;
	private String name;
	private boolean isEnclosedByClass;
	private boolean isEnclosedByInterface;
	private int accessFlags;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public PMethod() {
		this.pParameterArguments = new ArrayList<>();
		this.pBlock = new PBlock();
		this.pReturnType = null;
		this.name = "name";
		this.isEnclosedByClass = true;
		this.isEnclosedByInterface = false;
		this.accessFlags = ACCESS_FLAG_PUBLIC;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public Document write() {
		return write(new Document());
	}
	
//	TODO: Add Javadocs!
	public Document write(final Document document) {
		final PBlock pBlock = this.pBlock;
		
		final String accessModifiers = getAccessModifiersAsString("", " ");
		final String name = getName();
		final String parameterArguments = this.pParameterArguments.stream().map(pParameterArgument -> pParameterArgument.getSourceCode()).collect(Collectors.joining(", "));
		final String returnType = hasPReturnType() ? ": " + this.pReturnType.getSourceCode() : "";
		
		if(isEnclosedByClass()) {
			if(isAbstract()) {
				document.linef("%sfunction %s(%s)%s;", accessModifiers, name, parameterArguments, returnType);
			} else {
				document.linef("%sfunction %s(%s)%s {", accessModifiers, name, parameterArguments, returnType);
				document.indent();
				
				pBlock.write(document);
				
				document.outdent();
				document.line("}");
			}
		} else if(isEnclosedByInterface()) {
			document.linef("%sfunction %s(%s)%s;", accessModifiers, name, parameterArguments, returnType);
		}
		
		return document;
	}
	
//	TODO: Add Javadocs!
	public List<PParameterArgument> getPParameterArguments() {
		return new ArrayList<>(this.pParameterArguments);
	}
	
//	TODO: Add Javadocs!
	public List<String> getAccessModifiers() {
		final List<String> accessModifiers = new ArrayList<>();
		
		if(isPrivate()) {
			accessModifiers.add("private");
		}
		
		if(isProtected()) {
			accessModifiers.add("protected");
		}
		
		if(isPublic()) {
			accessModifiers.add("public");
		}
		
		if(isStatic()) {
			accessModifiers.add("static");
		}
		
		if(isAbstract()) {
			accessModifiers.add("abstract");
		}
		
		if(isFinal()) {
			accessModifiers.add("final");
		}
		
		return accessModifiers;
	}
	
//	TODO: Add Javadocs!
	public Optional<PReturnType> getPReturnType() {
		return Optional.ofNullable(this.pReturnType);
	}
	
//	TODO: Add Javadocs!
	public PBlock getPBlock() {
		return this.pBlock;
	}
	
//	TODO: Add Javadocs!
	public String getAccessModifiersAsString() {
		return getAccessModifiersAsString("", "");
	}
	
//	TODO: Add Javadocs!
	public String getAccessModifiersAsString(final String prefix, final String suffix) {
		Objects.requireNonNull(prefix, "prefix == null");
		Objects.requireNonNull(suffix, "suffix == null");
		
		final List<String> accessModifiers = getAccessModifiers();
		
		if(accessModifiers.isEmpty()) {
			return "";
		}
		
		return accessModifiers.stream().collect(Collectors.joining(" ", prefix, suffix));
	}
	
//	TODO: Add Javadocs!
	public String getName() {
		return this.name;
	}
	
//	TODO: Add Javadocs!
	public boolean hasPReturnType() {
		return this.pReturnType != null;
	}
	
//	TODO: Add Javadocs!
	public boolean isAbstract() {
		return (this.accessFlags & ACCESS_FLAG_ABSTRACT) != 0;
	}
	
//	TODO: Add Javadocs!
	public boolean isDefaultCallable() {
		boolean isDefaultCallable = true;
		
		for(final PParameterArgument pParameterArgument : this.pParameterArguments) {
			if(!pParameterArgument.hasPValue()) {
				isDefaultCallable = false;
			}
		}
		
		return isDefaultCallable;
	}
	
//	TODO: Add Javadocs!
	public boolean isEnclosedByClass() {
		return this.isEnclosedByClass;
	}
	
//	TODO: Add Javadocs!
	public boolean isEnclosedByInterface() {
		return this.isEnclosedByInterface;
	}
	
//	TODO: Add Javadocs!
	public boolean isFinal() {
		return (this.accessFlags & ACCESS_FLAG_FINAL) != 0;
	}
	
//	TODO: Add Javadocs!
	public boolean isPrivate() {
		return (this.accessFlags & ACCESS_FLAG_PRIVATE) != 0;
	}
	
//	TODO: Add Javadocs!
	public boolean isProtected() {
		return (this.accessFlags & ACCESS_FLAG_PROTECTED) != 0;
	}
	
//	TODO: Add Javadocs!
	public boolean isPublic() {
		return (this.accessFlags & ACCESS_FLAG_PUBLIC) != 0;
	}
	
//	TODO: Add Javadocs!
	public boolean isStatic() {
		return (this.accessFlags & ACCESS_FLAG_STATIC) != 0;
	}
	
//	TODO: Add Javadocs!
	@Override
	public int compareTo(final PMethod pMethod) {
		final PMethod pMethodThis = this;
		final PMethod pMethodThat = pMethod;
		
		final boolean isStaticThis = pMethodThis.isStatic();
		final boolean isStaticThat = pMethodThat.isStatic();
		
		if(isStaticThis != isStaticThat) {
			return isStaticThis ? 1 : -1;
		}
		
		final boolean isPublicThis = pMethodThis.isPublic();
		final boolean isPublicThat = pMethodThat.isPublic();
		
		if(isPublicThis != isPublicThat) {
			return isPublicThis ? -1 : 1;
		}
		
		final boolean isProtectedThis = pMethodThis.isProtected();
		final boolean isProtectedThat = pMethodThat.isProtected();
		
		if(isProtectedThis != isProtectedThat) {
			return isProtectedThis ? -1 : 1;
		}
		
		final boolean isPrivateThis = pMethodThis.isPrivate();
		final boolean isPrivateThat = pMethodThat.isPrivate();
		
		if(isPrivateThis != isPrivateThat) {
			return isPrivateThis ? -1 : 1;
		}
		
		final boolean isAbstractThis = pMethodThis.isAbstract();
		final boolean isAbstractThat = pMethodThat.isAbstract();
		
		if(isAbstractThis != isAbstractThat) {
			return isAbstractThis ? -1 : 1;
		}
		
		final boolean isFinalThis = pMethodThis.isFinal();
		final boolean isFinalThat = pMethodThat.isFinal();
		
		if(isFinalThis != isFinalThat) {
			return isFinalThis ? -1 : 1;
		}
		
		return this.name.compareTo(pMethod.name);
	}
	
//	TODO: Add Javadocs!
	public void addPParameterArgument(final PParameterArgument pParameterArgument) {
		this.pParameterArguments.add(Objects.requireNonNull(pParameterArgument, "pParameterArgument == null"));
	}
	
//	TODO: Add Javadocs!
	public void removePParameterArgument(final PParameterArgument pParameterArgument) {
		this.pParameterArguments.remove(Objects.requireNonNull(pParameterArgument, "pParameterArgument == null"));
	}
	
//	TODO: Add Javadocs!
	public void setAbstract(final boolean isAbstract) {
		if(isAbstract) {
			this.accessFlags |= ACCESS_FLAG_ABSTRACT;
			this.accessFlags &= ~ACCESS_FLAG_FINAL;
			this.accessFlags &= ~ACCESS_FLAG_PRIVATE;
			this.accessFlags &= ~ACCESS_FLAG_STATIC;
		} else {
			this.accessFlags &= ~ACCESS_FLAG_ABSTRACT;
		}
	}
	
//	TODO: Add Javadocs!
	public void setEnclosedByClass(final boolean isEnclosedByClass) {
		this.isEnclosedByClass = isEnclosedByClass;
		this.isEnclosedByInterface = isEnclosedByClass ? false : this.isEnclosedByInterface;
	}
	
//	TODO: Add Javadocs!
	public void setEnclosedByInterface(final boolean isEnclosedByInterface) {
		this.isEnclosedByClass = isEnclosedByInterface ? false : this.isEnclosedByClass;
		this.isEnclosedByInterface = isEnclosedByInterface;
	}
	
//	TODO: Add Javadocs!
	public void setFinal(final boolean isFinal) {
		if(isFinal) {
			this.accessFlags &= ~ACCESS_FLAG_ABSTRACT;
			this.accessFlags |= ACCESS_FLAG_FINAL;
		} else {
			this.accessFlags &= ~ACCESS_FLAG_FINAL;
		}
	}
	
//	TODO: Add Javadocs!
	public void setName(final String name) {
		this.name = Objects.requireNonNull(name, "name == null");
	}
	
//	TODO: Add Javadocs!
	public void setPReturnType(final PReturnType pReturnType) {
		this.pReturnType = pReturnType;
	}
	
//	TODO: Add Javadocs!
	public void setPrivate(final boolean isPrivate) {
		if(isPrivate) {
			this.accessFlags |= ACCESS_FLAG_PRIVATE;
			this.accessFlags &= ~ACCESS_FLAG_PROTECTED;
			this.accessFlags &= ~ACCESS_FLAG_PUBLIC;
		} else {
			this.accessFlags &= ~ACCESS_FLAG_PRIVATE;
		}
	}
	
//	TODO: Add Javadocs!
	public void setProtected(final boolean isProtected) {
		if(isProtected) {
			this.accessFlags &= ~ACCESS_FLAG_PRIVATE;
			this.accessFlags |= ACCESS_FLAG_PROTECTED;
			this.accessFlags &= ~ACCESS_FLAG_PUBLIC;
		} else {
			this.accessFlags &= ~ACCESS_FLAG_PROTECTED;
		}
	}
	
//	TODO: Add Javadocs!
	public void setPublic(final boolean isPublic) {
		if(isPublic) {
			this.accessFlags &= ~ACCESS_FLAG_PRIVATE;
			this.accessFlags &= ~ACCESS_FLAG_PROTECTED;
			this.accessFlags |= ACCESS_FLAG_PUBLIC;
		} else {
			this.accessFlags &= ~ACCESS_FLAG_PUBLIC;
		}
	}
	
//	TODO: Add Javadocs!
	public void setStatic(final boolean isStatic) {
		if(isStatic) {
			this.accessFlags |= ACCESS_FLAG_STATIC;
		} else {
			this.accessFlags &= ~ACCESS_FLAG_STATIC;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
//	TODO: Add Javadocs!
	public static PMethod newClassMethodGet(final String name, final PType pType, final boolean isNullable) {
		final String nameCamelCaseModified = Strings.formatCamelCaseModified(name);
		final String nameCamelCase = Strings.formatCamelCase(name);
		
		final
		PMethod pMethod = new PMethod();
		pMethod.getPBlock().addLinef("return $this->%s;", nameCamelCaseModified);
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("get" + nameCamelCase);
		pMethod.setPReturnType(new PReturnType(pType, isNullable));
		
		return pMethod;
	}
	
//	TODO: Add Javadocs!
	public static PMethod newClassMethodHas(final String name) {
		final String nameCamelCaseModified = Strings.formatCamelCaseModified(name);
		final String nameCamelCase = Strings.formatCamelCase(name);
		
		final
		PMethod pMethod = new PMethod();
		pMethod.getPBlock().addLinef("return $this->%s !== null;", nameCamelCaseModified);
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("has" + nameCamelCase);
		pMethod.setPReturnType(new PReturnType(PType.BOOL));
		
		return pMethod;
	}
	
//	TODO: Add Javadocs!
	public static PMethod newClassMethodSet(final String name, final PType pType, final PValue pValue, final boolean isNullable) {
		final String nameCamelCaseModified = Strings.formatCamelCaseModified(name);
		final String nameCamelCase = Strings.formatCamelCase(name);
		
		final
		PMethod pMethod = new PMethod();
		pMethod.addPParameterArgument(new PParameterArgument(nameCamelCaseModified, pType, pValue, isNullable));
		pMethod.getPBlock().addLinef("$this->%s = $%s;", nameCamelCaseModified, nameCamelCaseModified);
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("set" + nameCamelCase);
		pMethod.setPReturnType(new PReturnType(PType.VOID, false));
		
		return pMethod;
	}
	
//	TODO: Add Javadocs!
	public static PMethod newInterfaceMethod(final String name, final PParameterArgument[] pParameterArguments, final PReturnType pReturnType) {
		final
		PMethod pMethod = new PMethod();
		pMethod.setEnclosedByInterface(true);
		pMethod.setName(name);
		pMethod.setPReturnType(pReturnType);
		
		for(final PParameterArgument pParameterArgument : pParameterArguments) {
			pMethod.addPParameterArgument(pParameterArgument);
		}
		
		return pMethod;
	}
	
//	TODO: Add Javadocs!
	public static PMethod newInterfaceMethodGet(final String name, final PType pType, final boolean isNullable) {
		final String nameCamelCase = Strings.formatCamelCase(name);
		
		final
		PMethod pMethod = new PMethod();
		pMethod.setEnclosedByInterface(true);
		pMethod.setName("get" + nameCamelCase);
		pMethod.setPReturnType(pType != null ? new PReturnType(pType, isNullable) : null);
		
		return pMethod;
	}
	
//	TODO: Add Javadocs!
	public static PMethod newInterfaceMethodHas(final String name) {
		final String nameCamelCase = Strings.formatCamelCase(name);
		
		final
		PMethod pMethod = new PMethod();
		pMethod.setEnclosedByInterface(true);
		pMethod.setName("has" + nameCamelCase);
		pMethod.setPReturnType(new PReturnType(PType.BOOL));
		
		return pMethod;
	}
	
//	TODO: Add Javadocs!
	public static PMethod newInterfaceMethodSet(final String name, final PType pType, final PValue pValue, final boolean isNullable) {
		final String nameCamelCaseModified = Strings.formatCamelCaseModified(name);
		final String nameCamelCase = Strings.formatCamelCase(name);
		
		final
		PMethod pMethod = new PMethod();
		pMethod.addPParameterArgument(new PParameterArgument(nameCamelCaseModified, pType, pValue, isNullable));
		pMethod.setEnclosedByInterface(true);
		pMethod.setName("set" + nameCamelCase);
		pMethod.setPReturnType(new PReturnType(PType.VOID, false));
		
		return pMethod;
	}
	
//	TODO: Add Javadocs!
	public static boolean isInDifferentGroups(final PMethod pMethodA, final PMethod pMethodB) {
		if(pMethodA.isStatic() != pMethodB.isStatic()) {
			return true;
		}
		
		if(pMethodA.isPublic() != pMethodB.isPublic()) {
			return true;
		}
		
		if(pMethodA.isProtected() != pMethodB.isProtected()) {
			return true;
		}
		
		if(pMethodA.isPrivate() != pMethodB.isPrivate()) {
			return true;
		}
		
		if(pMethodA.isAbstract() != pMethodB.isAbstract()) {
			return true;
		}
		
		if(pMethodA.isFinal() != pMethodB.isFinal()) {
			return true;
		}
		
		return false;
	}
}