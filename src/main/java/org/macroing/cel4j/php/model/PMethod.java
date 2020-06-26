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
	
	private final List<PParameterArgument> parameterArguments;
	private final PBlock block;
	private PReturnType returnType;
	private String name;
	private boolean isEnclosedByClass;
	private boolean isEnclosedByInterface;
	private int accessFlags;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code PMethod} instance.
	 */
	public PMethod() {
		this.parameterArguments = new ArrayList<>();
		this.block = new PBlock();
		this.returnType = null;
		this.name = "name";
		this.isEnclosedByClass = true;
		this.isEnclosedByInterface = false;
		this.accessFlags = ACCESS_FLAG_PUBLIC;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Writes this {@code PMethod} instance to a {@link Document}.
	 * <p>
	 * Returns the {@code Document} that was written to.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * pMethod.write(new Document());
	 * }
	 * </pre>
	 * 
	 * @return the {@code Document} that was written to
	 */
	public Document write() {
		return write(new Document());
	}
	
	/**
	 * Writes this {@code PMethod} instance to a {@link Document}.
	 * <p>
	 * Returns the {@code Document} that was written to.
	 * <p>
	 * If {@code document} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param document the {@code Document} to write to
	 * @return the {@code Document} that was written to
	 * @throws NullPointerException thrown if, and only if, {@code document} is {@code null}
	 */
	public Document write(final Document document) {
		final PBlock block = this.block;
		
		final String accessModifiers = getAccessModifiersAsString("", " ");
		final String name = getName();
		final String parameterArguments = this.parameterArguments.stream().map(pParameterArgument -> pParameterArgument.getSourceCode()).collect(Collectors.joining(", "));
		final String returnType = hasReturnType() ? ": " + this.returnType.getSourceCode() : "";
		
		if(isEnclosedByClass()) {
			if(isAbstract()) {
				document.linef("%sfunction %s(%s)%s;", accessModifiers, name, parameterArguments, returnType);
			} else {
				document.linef("%sfunction %s(%s)%s {", accessModifiers, name, parameterArguments, returnType);
				document.indent();
				
				block.write(document);
				
				document.outdent();
				document.line("}");
			}
		} else if(isEnclosedByInterface()) {
			document.linef("%sfunction %s(%s)%s;", accessModifiers, name, parameterArguments, returnType);
		}
		
		return document;
	}
	
	/**
	 * Returns a {@code List} with all {@link PParameterArgument} instances that are currently added to this {@code PMethod} instance.
	 * 
	 * @return a {@code List} with all {@code PParameterArgument} instances that are currently added to this {@code PMethod} instance
	 */
	public List<PParameterArgument> getParameterArguments() {
		return new ArrayList<>(this.parameterArguments);
	}
	
	/**
	 * Returns a {@code List} with all access modifiers that are currently associated with this {@code PMethod} instance.
	 * 
	 * @return a {@code List} with all access modifiers that are currently associated with this {@code PMethod} instance
	 */
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
	
	/**
	 * Returns an {@code Optional} with the optional {@link PReturnType} instance that is associated with this {@code PMethod} instance.
	 * 
	 * @return an {@code Optional} with the optional {@code PReturnType} instance that is associated with this {@code PMethod} instance
	 */
	public Optional<PReturnType> getReturnType() {
		return Optional.ofNullable(this.returnType);
	}
	
	/**
	 * Returns the {@link PBlock} instance that is associated with this {@code PMethod} instance.
	 * 
	 * @return the {@code PBlock} instance that is associated with this {@code PMethod} instance
	 */
	public PBlock getBlock() {
		return this.block;
	}
	
	/**
	 * Returns a {@code String} representation of the access modifiers that are currently associated with this {@code PMethod} instance.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * pMethod.getAccessModifiersAsString("", "");
	 * }
	 * </pre>
	 * 
	 * @return a {@code String} representation of the access modifiers that are currently associated with this {@code PMethod} instance
	 */
	public String getAccessModifiersAsString() {
		return getAccessModifiersAsString("", "");
	}
	
	/**
	 * Returns a {@code String} representation of the access modifiers that are currently associated with this {@code PMethod} instance.
	 * <p>
	 * If either {@code prefix} or {@code suffix} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param prefix a {@code String} to be used at the beginning of the access modifiers, if there are any
	 * @param suffix a {@code String} to be used at the end of the access modifiers, if there are any
	 * @return a {@code String} representation of the access modifiers that are currently associated with this {@code PMethod} instance
	 * @throws NullPointerException thrown if, and only if, either {@code prefix} or {@code suffix} are {@code null}
	 */
	public String getAccessModifiersAsString(final String prefix, final String suffix) {
		Objects.requireNonNull(prefix, "prefix == null");
		Objects.requireNonNull(suffix, "suffix == null");
		
		final List<String> accessModifiers = getAccessModifiers();
		
		if(accessModifiers.isEmpty()) {
			return "";
		}
		
		return accessModifiers.stream().collect(Collectors.joining(" ", prefix, suffix));
	}
	
	/**
	 * Returns the name associated with this {@code PMethod} instance.
	 * 
	 * @return the name associated with this {@code PMethod} instance
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code PMethod} instance has a {@link PReturnType}, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code PMethod} instance has a {@code PReturnType}, {@code false} otherwise
	 */
	public boolean hasReturnType() {
		return this.returnType != null;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code PMethod} instance represents an abstract method, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code PMethod} instance represents an abstract method, {@code false} otherwise
	 */
	public boolean isAbstract() {
		return (this.accessFlags & ACCESS_FLAG_ABSTRACT) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code PMethod} instance can be called without any parameter arguments specified, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code PMethod} instance can be called without any parameter arguments specified, {@code false} otherwise
	 */
	public boolean isDefaultCallable() {
		boolean isDefaultCallable = true;
		
		for(final PParameterArgument parameterArgument : this.parameterArguments) {
			if(!parameterArgument.hasValue()) {
				isDefaultCallable = false;
			}
		}
		
		return isDefaultCallable;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code PMethod} instance is enclosed by a class, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code PMethod} instance is enclosed by a class, {@code false} otherwise
	 */
	public boolean isEnclosedByClass() {
		return this.isEnclosedByClass;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code PMethod} instance is enclosed by an interface, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code PMethod} instance is enclosed by an interface, {@code false} otherwise
	 */
	public boolean isEnclosedByInterface() {
		return this.isEnclosedByInterface;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code PMethod} instance represents a final method, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code PMethod} instance represents a final method, {@code false} otherwise
	 */
	public boolean isFinal() {
		return (this.accessFlags & ACCESS_FLAG_FINAL) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code PMethod} instance represents a private method, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code PMethod} instance represents a private method, {@code false} otherwise
	 */
	public boolean isPrivate() {
		return (this.accessFlags & ACCESS_FLAG_PRIVATE) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code PMethod} instance represents a protected method, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code PMethod} instance represents a protected method, {@code false} otherwise
	 */
	public boolean isProtected() {
		return (this.accessFlags & ACCESS_FLAG_PROTECTED) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code PMethod} instance represents a public method, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code PMethod} instance represents a public method, {@code false} otherwise
	 */
	public boolean isPublic() {
		return (this.accessFlags & ACCESS_FLAG_PUBLIC) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code PMethod} instance represents a static method, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code PMethod} instance represents a static method, {@code false} otherwise
	 */
	public boolean isStatic() {
		return (this.accessFlags & ACCESS_FLAG_STATIC) != 0;
	}
	
	/**
	 * Compares this {@code PMethod} instance to {@code method}.
	 * <p>
	 * Returns a comparison value.
	 * <p>
	 * If {@code method} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param method the {@code PMethod} to compare this {@code PMethod} instance to
	 * @return a comparison value
	 * @throws NullPointerException thrown if, and only if, {@code method} is {@code null}
	 */
	@Override
	public int compareTo(final PMethod method) {
		final PMethod methodThis = this;
		final PMethod methodThat = method;
		
		final boolean isStaticThis = methodThis.isStatic();
		final boolean isStaticThat = methodThat.isStatic();
		
		if(isStaticThis != isStaticThat) {
			return isStaticThis ? 1 : -1;
		}
		
		final boolean isPublicThis = methodThis.isPublic();
		final boolean isPublicThat = methodThat.isPublic();
		
		if(isPublicThis != isPublicThat) {
			return isPublicThis ? -1 : 1;
		}
		
		final boolean isProtectedThis = methodThis.isProtected();
		final boolean isProtectedThat = methodThat.isProtected();
		
		if(isProtectedThis != isProtectedThat) {
			return isProtectedThis ? -1 : 1;
		}
		
		final boolean isPrivateThis = methodThis.isPrivate();
		final boolean isPrivateThat = methodThat.isPrivate();
		
		if(isPrivateThis != isPrivateThat) {
			return isPrivateThis ? -1 : 1;
		}
		
		final boolean isAbstractThis = methodThis.isAbstract();
		final boolean isAbstractThat = methodThat.isAbstract();
		
		if(isAbstractThis != isAbstractThat) {
			return isAbstractThis ? -1 : 1;
		}
		
		final boolean isFinalThis = methodThis.isFinal();
		final boolean isFinalThat = methodThat.isFinal();
		
		if(isFinalThis != isFinalThat) {
			return isFinalThis ? -1 : 1;
		}
		
		return this.name.compareTo(method.name);
	}
	
//	TODO: Add Javadocs!
	public void addParameterArgument(final PParameterArgument parameterArgument) {
		this.parameterArguments.add(Objects.requireNonNull(parameterArgument, "parameterArgument == null"));
	}
	
//	TODO: Add Javadocs!
	public void removeParameterArgument(final PParameterArgument parameterArgument) {
		this.parameterArguments.remove(Objects.requireNonNull(parameterArgument, "parameterArgument == null"));
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
	public void setReturnType(final PReturnType returnType) {
		this.returnType = returnType;
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
	public static PMethod newClassMethodGet(final String name, final PType type, final boolean isNullable) {
		final String nameCamelCaseModified = Strings.formatCamelCaseModified(name);
		final String nameCamelCase = Strings.formatCamelCase(name);
		
		final
		PMethod pMethod = new PMethod();
		pMethod.getBlock().addLinef("return $this->%s;", nameCamelCaseModified);
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("get" + nameCamelCase);
		pMethod.setReturnType(new PReturnType(type, isNullable));
		
		return pMethod;
	}
	
//	TODO: Add Javadocs!
	public static PMethod newClassMethodHas(final String name) {
		final String nameCamelCaseModified = Strings.formatCamelCaseModified(name);
		final String nameCamelCase = Strings.formatCamelCase(name);
		
		final
		PMethod pMethod = new PMethod();
		pMethod.getBlock().addLinef("return $this->%s !== null;", nameCamelCaseModified);
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("has" + nameCamelCase);
		pMethod.setReturnType(new PReturnType(PType.BOOL));
		
		return pMethod;
	}
	
//	TODO: Add Javadocs!
	public static PMethod newClassMethodSet(final String name, final PType type, final PValue value, final boolean isNullable) {
		final String nameCamelCaseModified = Strings.formatCamelCaseModified(name);
		final String nameCamelCase = Strings.formatCamelCase(name);
		
		final
		PMethod pMethod = new PMethod();
		pMethod.addParameterArgument(new PParameterArgument(nameCamelCaseModified, type, value, isNullable));
		pMethod.getBlock().addLinef("$this->%s = $%s;", nameCamelCaseModified, nameCamelCaseModified);
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("set" + nameCamelCase);
		pMethod.setReturnType(new PReturnType(PType.VOID, false));
		
		return pMethod;
	}
	
//	TODO: Add Javadocs!
	public static PMethod newInterfaceMethod(final String name, final PParameterArgument[] parameterArguments, final PReturnType returnType) {
		final
		PMethod pMethod = new PMethod();
		pMethod.setEnclosedByInterface(true);
		pMethod.setName(name);
		pMethod.setReturnType(returnType);
		
		for(final PParameterArgument parameterArgument : parameterArguments) {
			pMethod.addParameterArgument(parameterArgument);
		}
		
		return pMethod;
	}
	
//	TODO: Add Javadocs!
	public static PMethod newInterfaceMethodGet(final String name, final PType type, final boolean isNullable) {
		final String nameCamelCase = Strings.formatCamelCase(name);
		
		final
		PMethod pMethod = new PMethod();
		pMethod.setEnclosedByInterface(true);
		pMethod.setName("get" + nameCamelCase);
		pMethod.setReturnType(type != null ? new PReturnType(type, isNullable) : null);
		
		return pMethod;
	}
	
//	TODO: Add Javadocs!
	public static PMethod newInterfaceMethodHas(final String name) {
		final String nameCamelCase = Strings.formatCamelCase(name);
		
		final
		PMethod pMethod = new PMethod();
		pMethod.setEnclosedByInterface(true);
		pMethod.setName("has" + nameCamelCase);
		pMethod.setReturnType(new PReturnType(PType.BOOL));
		
		return pMethod;
	}
	
//	TODO: Add Javadocs!
	public static PMethod newInterfaceMethodSet(final String name, final PType type, final PValue value, final boolean isNullable) {
		final String nameCamelCaseModified = Strings.formatCamelCaseModified(name);
		final String nameCamelCase = Strings.formatCamelCase(name);
		
		final
		PMethod pMethod = new PMethod();
		pMethod.addParameterArgument(new PParameterArgument(nameCamelCaseModified, type, value, isNullable));
		pMethod.setEnclosedByInterface(true);
		pMethod.setName("set" + nameCamelCase);
		pMethod.setReturnType(new PReturnType(PType.VOID, false));
		
		return pMethod;
	}
	
//	TODO: Add Javadocs!
	public static boolean isInDifferentGroups(final PMethod methodA, final PMethod methodB) {
		if(methodA.isStatic() != methodB.isStatic()) {
			return true;
		}
		
		if(methodA.isPublic() != methodB.isPublic()) {
			return true;
		}
		
		if(methodA.isProtected() != methodB.isProtected()) {
			return true;
		}
		
		if(methodA.isPrivate() != methodB.isPrivate()) {
			return true;
		}
		
		if(methodA.isAbstract() != methodB.isAbstract()) {
			return true;
		}
		
		if(methodA.isFinal() != methodB.isFinal()) {
			return true;
		}
		
		return false;
	}
}