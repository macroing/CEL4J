/**
 * Copyright 2009 - 2024 J&#246;rgen Lundgren
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.macroing.cel4j.util.Document;
import org.macroing.cel4j.util.Documentable;

/**
 * A {@code PClass} represents a class and can be added to a {@link PDocument}.
 * <p>
 * This class is mutable and not thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class PClass implements Documentable {
	private static final int ACCESS_FLAG_ABSTRACT = 0x0400;
	private static final int ACCESS_FLAG_FINAL = 0x0010;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final List<PConst> consts;
	private final List<PField> fields;
	private final List<PMethod> methods;
	private final List<String> implementedInterfaces;
	private PConstructor constructor;
	private String extendedClass;
	private String name;
	private int accessFlags;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code PClass} instance.
	 */
	public PClass() {
		this.consts = new ArrayList<>();
		this.fields = new ArrayList<>();
		this.methods = new ArrayList<>();
		this.implementedInterfaces = new ArrayList<>();
		this.constructor = null;
		this.extendedClass = null;
		this.name = "MyClass";
		this.accessFlags = 0;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Writes this {@code PClass} to a {@link Document}.
	 * <p>
	 * Returns the {@code Document}.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * pClass.write(new Document());
	 * }
	 * </pre>
	 * 
	 * @return the {@code Document}
	 */
	@Override
	public Document write() {
		return write(new Document());
	}
	
	/**
	 * Writes this {@code PClass} to {@code document}.
	 * <p>
	 * Returns {@code document}.
	 * <p>
	 * If {@code document} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * pClass.write(document, false);
	 * }
	 * </pre>
	 * 
	 * @param document the {@link Document} to write to
	 * @return {@code document}
	 * @throws NullPointerException thrown if, and only if, {@code document} is {@code null}
	 */
	@Override
	public Document write(final Document document) {
		return write(document, false);
	}
	
	/**
	 * Writes this {@code PClass} to {@code document}.
	 * <p>
	 * Returns {@code document}.
	 * <p>
	 * If {@code document} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param document the {@code Document} to write to
	 * @param isAligningConsts {@code true} if, and only if, all {@link PConst} instances should be aligned, {@code false} otherwise
	 * @return {@code document}
	 * @throws NullPointerException thrown if, and only if, {@code document} is {@code null}
	 */
	public Document write(final Document document, final boolean isAligningConsts) {
		final List<PConst> consts = getConsts();
		final List<PField> fields = getFields();
		final List<PMethod> methods = getMethods();
		
		final PConstructor constructor = this.constructor;
		
		final boolean hasConsts = consts.size() > 0;
		final boolean hasConstructor = constructor != null;
		final boolean hasFields = fields.size() > 0;
		final boolean hasMethods = methods.size() > 0;
		
		final String accessModifier = isAbstract() ? "abstract " : isFinal() ? "final " : "";
		final String name = getName();
		final String extendsClause = doCreateExtendsClause();
		final String implementsClause = doCreateImplementsClause();
		
		document.linef("%sclass %s%s%s {", accessModifier, name, extendsClause, implementsClause);
		document.indent();
		
		if(hasConsts) {
			if(isAligningConsts) {
				int maximumLength = 0;
				
				for(final PConst currentConst : consts) {
					maximumLength = Math.max(maximumLength, currentConst.getAccessModifiersAsString("", " ").length() + currentConst.getName().length());
				}
				
				for(int i = 0; i < consts.size(); i++) {
					final PConst constA = consts.get(i);
					final PConst constB = consts.get(i + 1 < consts.size() ? i + 1 : i);
					
					constA.write(document, maximumLength);
					
					if(PConst.isInDifferentGroups(constA, constB)) {
						document.line();
						document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
						document.line();
					}
				}
			} else {
				for(int i = 0; i < consts.size(); i++) {
					final PConst constA = consts.get(i);
					final PConst constB = consts.get(i + 1 < consts.size() ? i + 1 : i);
					
					constA.write(document);
					
					if(PConst.isInDifferentGroups(constA, constB)) {
						document.line();
						document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
						document.line();
					}
				}
			}
			
			if(hasConstructor || hasFields || hasMethods) {
				document.line();
				document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
				document.line();
			}
		}
		
		if(hasFields) {
			for(int i = 0; i < fields.size(); i++) {
				final PField fieldA = fields.get(i);
				final PField fieldB = fields.get(i + 1 < fields.size() ? i + 1 : i);
				
				fieldA.write(document);
				
				if(PField.isInDifferentGroups(fieldA, fieldB)) {
					document.line();
					document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
					document.line();
				}
			}
			
			if(hasConstructor || hasMethods) {
				document.line();
				document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
				document.line();
			}
		}
		
		if(constructor != null) {
			constructor.write(document);
			
			if(hasMethods) {
				document.line();
				document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
				document.line();
			}
		}
		
		for(int i = 0; i < methods.size(); i++) {
			final PMethod methodA = methods.get(i);
			final PMethod methodB = methods.get(i + 1 < methods.size() ? i + 1 : i);
			
			methodA.write(document);
			
			if(PMethod.isInDifferentGroups(methodA, methodB)) {
				document.line();
				document.line("////////////////////////////////////////////////////////////////////////////////////////////////////");
				document.line();
			} else if(methodA != methodB) {
				document.line();
			}
		}
		
		document.outdent();
		document.line("}");
		
		return document;
	}
	
	/**
	 * Returns a {@code List} with all {@link PConst} instances that are currently added to this {@code PClass} instance.
	 * 
	 * @return a {@code List} with all {@code PConst} instances that are currently added to this {@code PClass} instance
	 */
	public List<PConst> getConsts() {
		return new ArrayList<>(this.consts);
	}
	
	/**
	 * Returns a {@code List} with all {@link PField} instances that are currently added to this {@code PClass} instance.
	 * 
	 * @return a {@code List} with all {@code PField} instances that are currently added to this {@code PClass} instance
	 */
	public List<PField> getFields() {
		return new ArrayList<>(this.fields);
	}
	
	/**
	 * Returns a {@code List} with all {@link PMethod} instances that are currently added to this {@code PClass} instance.
	 * 
	 * @return a {@code List} with all {@code PMethod} instances that are currently added to this {@code PClass} instance
	 */
	public List<PMethod> getMethods() {
		return new ArrayList<>(this.methods);
	}
	
	/**
	 * Returns a {@code List} with the names of all implemented interfaces that are currently added to this {@code PClass} instance.
	 * 
	 * @return a {@code List} with the names of all implemented interfaces that are currently added to this {@code PClass} instance
	 */
	public List<String> getImplementedInterfaces() {
		return new ArrayList<>(this.implementedInterfaces);
	}
	
	/**
	 * Returns an {@code Optional} with the optional {@link PConstructor} instance that is associated with this {@code PClass} instance.
	 * 
	 * @return an {@code Optional} with the optional {@code PConstructor} instance that is associated with this {@code PClass} instance
	 */
	public Optional<PConstructor> getConstructor() {
		return Optional.ofNullable(this.constructor);
	}
	
	/**
	 * Returns an {@code Optional} with the optional name of the extended class that is associated with this {@code PClass} instance.
	 * 
	 * @return an {@code Optional} with the optional name of the extended class that is associated with this {@code PClass} instance
	 */
	public Optional<String> getExtendedClass() {
		return Optional.ofNullable(this.extendedClass);
	}
	
	/**
	 * Returns the name associated with this {@code PClass} instance.
	 * 
	 * @return the name associated with this {@code PClass} instance
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code PClass} instance represents an abstract class, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code PClass} instance represents an abstract class, {@code false} otherwise
	 */
	public boolean isAbstract() {
		return (this.accessFlags & ACCESS_FLAG_ABSTRACT) != 0;
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code PClass} instance represents a final class, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code PClass} instance represents a final class, {@code false} otherwise
	 */
	public boolean isFinal() {
		return (this.accessFlags & ACCESS_FLAG_FINAL) != 0;
	}
	
	/**
	 * Adds {@code pConst} to this {@code PClass} instance, if absent.
	 * <p>
	 * If {@code pConst} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param pConst the {@link PConst} to add
	 * @throws NullPointerException thrown if, and only if, {@code pConst} is {@code null}
	 */
	public void addConst(final PConst pConst) {
		if(!doContainsConstByName(Objects.requireNonNull(pConst, "pConst == null"))) {
			this.consts.add(pConst);
		}
	}
	
	/**
	 * Adds {@code field} to this {@code PClass} instance, if absent.
	 * <p>
	 * If {@code field} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param field the {@link PField} to add
	 * @throws NullPointerException thrown if, and only if, {@code field} is {@code null}
	 */
	public void addField(final PField field) {
		if(!doContainsFieldByName(Objects.requireNonNull(field, "field == null"))) {
			this.fields.add(field);
		}
	}
	
	/**
	 * Adds {@code implementedInterface} to this {@code PClass} instance.
	 * <p>
	 * If {@code implementedInterface} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param implementedInterface the name of the implemented interface to add
	 * @throws NullPointerException thrown if, and only if, {@code implementedInterface} is {@code null}
	 */
	public void addImplementedInterface(final String implementedInterface) {
		this.implementedInterfaces.add(Objects.requireNonNull(implementedInterface, "implementedInterface == null"));
	}
	
	/**
	 * Adds {@code method} to this {@code PClass} instance, if absent.
	 * <p>
	 * If {@code method} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param method the {@link PMethod} to add
	 * @throws NullPointerException thrown if, and only if, {@code method} is {@code null}
	 */
	public void addMethod(final PMethod method) {
		if(!doContainsMethodByName(Objects.requireNonNull(method, "method == null"))) {
			this.methods.add(method);
		}
	}
	
	/**
	 * Removes {@code pConst} from this {@code PClass} instance, if present.
	 * <p>
	 * If {@code pConst} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param pConst the {@link PConst} to remove
	 * @throws NullPointerException thrown if, and only if, {@code pConst} is {@code null}
	 */
	public void removeConst(final PConst pConst) {
		this.consts.remove(Objects.requireNonNull(pConst, "pConst == null"));
	}
	
	/**
	 * Removes {@code field} from this {@code PClass} instance, if present.
	 * <p>
	 * If {@code field} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param field the {@link PField} to remove
	 * @throws NullPointerException thrown if, and only if, {@code field} is {@code null}
	 */
	public void removeField(final PField field) {
		this.fields.remove(Objects.requireNonNull(field, "field == null"));
	}
	
	/**
	 * Removes {@code implementedInterface} from this {@code PClass} instance.
	 * <p>
	 * If {@code implementedInterface} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param implementedInterface the name of the implemented interface to remove
	 * @throws NullPointerException thrown if, and only if, {@code implementedInterface} is {@code null}
	 */
	public void removeImplementedInterface(final String implementedInterface) {
		this.implementedInterfaces.remove(Objects.requireNonNull(implementedInterface, "implementedInterface == null"));
	}
	
	/**
	 * Removes {@code method} from this {@code PClass} instance, if present.
	 * <p>
	 * If {@code method} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param method the {@link PMethod} to remove
	 * @throws NullPointerException thrown if, and only if, {@code method} is {@code null}
	 */
	public void removeMethod(final PMethod method) {
		this.methods.remove(Objects.requireNonNull(method, "method == null"));
	}
	
	/**
	 * Adds or removes the abstract access modifier for this {@code PClass} instance.
	 * 
	 * @param isAbstract {@code true} if, and only if, this {@code PClass} is abstract, {@code false} otherwise
	 */
	public void setAbstract(final boolean isAbstract) {
		if(isAbstract) {
			this.accessFlags |= ACCESS_FLAG_ABSTRACT;
			this.accessFlags &= ~ACCESS_FLAG_FINAL;
		} else {
			this.accessFlags &= ~ACCESS_FLAG_ABSTRACT;
		}
	}
	
	/**
	 * Sets the optional constructor for this {@code PClass} instance.
	 * 
	 * @param constructor a {@link PConstructor} that may be {@code null}
	 */
	public void setConstructor(final PConstructor constructor) {
		this.constructor = constructor;
	}
	
	/**
	 * Sets the optional name of the extended class for this {@code PClass} instance.
	 * 
	 * @param extendedClass a {@code String} that may be {@code null}
	 */
	public void setExtendedClass(final String extendedClass) {
		this.extendedClass = extendedClass;
	}
	
	/**
	 * Adds or removes the final access modifier for this {@code PClass} instance.
	 * 
	 * @param isFinal {@code true} if, and only if, this {@code PClass} is final, {@code false} otherwise
	 */
	public void setFinal(final boolean isFinal) {
		if(isFinal) {
			this.accessFlags &= ~ACCESS_FLAG_ABSTRACT;
			this.accessFlags |= ACCESS_FLAG_FINAL;
		} else {
			this.accessFlags &= ~ACCESS_FLAG_FINAL;
		}
	}
	
	/**
	 * Sets {@code name} as the name for this {@code PClass} instance.
	 * <p>
	 * If {@code name} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param name the name for this {@code PClass} instance
	 * @throws NullPointerException thrown if, and only if, {@code name} is {@code null}
	 */
	public void setName(final String name) {
		this.name = Objects.requireNonNull(name, "name == null");
	}
	
	/**
	 * Sorts this {@code PClass} instance.
	 */
	public void sort() {
		Collections.sort(this.consts);
		Collections.sort(this.fields);
		Collections.sort(this.methods);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private String doCreateExtendsClause() {
		final String extendedClass = this.extendedClass;
		
		if(extendedClass == null) {
			return "";
		}
		
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" ");
		stringBuilder.append("extends");
		stringBuilder.append(" ");
		stringBuilder.append(extendedClass);
		
		return stringBuilder.toString();
	}
	
	private String doCreateImplementsClause() {
		final List<String> implementedInterfaces = getImplementedInterfaces();
		
		if(implementedInterfaces.size() == 0) {
			return "";
		}
		
		final
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" ");
		stringBuilder.append("implements");
		stringBuilder.append(" ");
		stringBuilder.append(implementedInterfaces.stream().collect(Collectors.joining(", ")));
		
		return stringBuilder.toString();
	}
	
	private boolean doContainsConstByName(final PConst pConst) {
		return this.consts.stream().anyMatch(currentPConst -> currentPConst.getName().equals(pConst.getName()));
	}
	
	private boolean doContainsFieldByName(final PField field) {
		return this.fields.stream().anyMatch(currentField -> currentField.getName().equals(field.getName()));
	}
	
	private boolean doContainsMethodByName(final PMethod method) {
		return this.methods.stream().anyMatch(currentMethod -> currentMethod.getName().equals(method.getName()));
	}
}