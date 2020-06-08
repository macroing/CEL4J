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
package org.macroing.cel4j.php.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.macroing.cel4j.php.model.PClass;
import org.macroing.cel4j.php.model.PConst;
import org.macroing.cel4j.php.model.PConstructor;
import org.macroing.cel4j.php.model.PDocument;
import org.macroing.cel4j.php.model.PField;
import org.macroing.cel4j.php.model.PMethod;
import org.macroing.cel4j.php.model.PParameterArgument;
import org.macroing.cel4j.php.model.PReturnType;
import org.macroing.cel4j.php.model.PType;
import org.macroing.cel4j.php.model.PValue;
import org.macroing.cel4j.util.Strings;

/**
 * A {@code Model} is used for generating object-oriented PHP class models using simple definitions.
 * <p>
 * The definitions used by this class are its code, name and properties.
 * <p>
 * The code is a {@code String} used in a const of the PHP class. This const is used in the {@code parseArray(array)} and {@code parseJSON(string)} methods that are generated.
 * <p>
 * The name is a {@code String} used to generate the name of the PHP class itself.
 * <p>
 * It is likely that the code and the name are equal, but that is not always the case. If the {@code Model} is built from scratch, where no JSON markup text is used to guide its implementation, the code and the name should preferably be equal. If, on
 * the other hand, the {@code Model} is built as a PHP class model for existing JSON markup text, they may be distinct.
 * <p>
 * The properties defines the content of the PHP class itself. Each property, which is an instance of the class {@link Property}, builds all consts, fields, methods and more via its associated {@link PropertyBuilder} instance. A wide variety of
 * {@code PropertyBuilder} implementations exists, for various purposes. It is also easy to create a new implementation, should one be needed.
 * <p>
 * To use this class, consider the following example:
 * <pre>
 * {@code
 * Model model = new Model("Person");
 * model.addProperty(new Property(PType.INT, new DefaultPropertyBuilder(), "Age"));
 * model.addProperty(new Property(PType.STRING, new DefaultPropertyBuilder(), "FirstName"));
 * model.addProperty(new Property(PType.STRING, new DefaultPropertyBuilder(), "LastName"));
 * 
 * PDocument pDocument = model.toDocument(PDocument.toNamespace("com", "example", "world"));
 * 
 * System.out.println(pDocument.write());
 * }
 * </pre>
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Model {
	private final List<Property> properties;
	private final String code;
	private final String name;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code Model} instance.
	 * <p>
	 * If {@code code} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this constructor is equivalent to the following:
	 * <pre>
	 * {@code
	 * new Model(code, code);
	 * }
	 * </pre>
	 * 
	 * @param code the code associated with the {@code Model}
	 * @throws NullPointerException thrown if, and only if, {@code code} is {@code null}
	 */
	public Model(final String code) {
		this(code, code);
	}
	
	/**
	 * Constructs a new {@code Model} instance.
	 * <p>
	 * If either {@code code} or {@code name} are {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param code the code associated with the {@code Model}
	 * @param name the name associated with the {@code Model}
	 * @throws NullPointerException thrown if, and only if, either {@code code} or {@code name} are {@code null}
	 */
	public Model(final String code, final String name) {
		this.properties = new ArrayList<>();
		this.code = Objects.requireNonNull(code, "code == null");
		this.name = Objects.requireNonNull(name, "name == null");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a {@code List} with all currently added {@link Property} instances.
	 * <p>
	 * Modifying the {@code List} itself will not affect this {@code Model} instance.
	 * 
	 * @return a {@code List} with all currently added {@code Property} instances
	 */
	public List<Property> getProperties() {
		return new ArrayList<>(this.properties);
	}
	
	/**
	 * Generates a {@link PClass} using all currently added {@link Property} instances.
	 * <p>
	 * Returns the generated {@code PClass}.
	 * 
	 * @return the generated {@code PClass}
	 */
	public PClass toClass() {
		final String name = getName();
		final String nameCamelCase = Strings.formatCamelCase(name);
		final String nameUnderscoreSeparatedUpperCase = Strings.formatUnderscoreSeparatedUpperCase(name);
		
		final List<Property> properties = getProperties();
		
		doSortPropertiesByName(properties);
		
		final
		PConstructor pConstructor = new PConstructor();
		pConstructor.setFinal(true);
		pConstructor.setPublic(true);
		
		final
		PClass pClass = new PClass();
		pClass.setFinal(true);
		pClass.setName(nameCamelCase);
		pClass.setConstructor(pConstructor);
		pClass.addConst(new PConst(nameUnderscoreSeparatedUpperCase, PValue.valueOf(getCode())));
		pClass.addMethod(doToMethodCopy(properties, name));
		pClass.addMethod(doToMethodParseArray(properties, name));
		pClass.addMethod(doToMethodParseJSON(name));
		pClass.addMethod(doToMethodSet(properties, name));
		pClass.addMethod(doToMethodToArray(properties));
		pClass.addMethod(doToMethodToJSON());
		
		for(final Property property : properties) {
			final PropertyBuilder propertyBuilder = property.getPropertyBuilder();
			
			final List<String> constructorLines = propertyBuilder.toConstructorLines(property);
			
			for(final PConst pConst : propertyBuilder.toConsts(property)) {
				pClass.addConst(pConst);
			}
			
			for(final PField pField : propertyBuilder.toFields(property)) {
				pClass.addField(pField);
			}
			
			for(final PMethod pMethod : propertyBuilder.toMethods(property)) {
				pClass.addMethod(pMethod);
			}
			
			for(final String constructorLine : constructorLines) {
				pConstructor.getBlock().addLine(constructorLine);
			}
		}
		
		pClass.sort();
		
		return pClass;
	}
	
	/**
	 * Generates a {@link PDocument} using all currently added {@link Property} instances.
	 * <p>
	 * Returns the generated {@code PDocument}.
	 * <p>
	 * If {@code namespace} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * Calling this method is equivalent to the following:
	 * <pre>
	 * {@code
	 * PDocument pDocument = new PDocument();
	 * pDocument.addClass(model.toClass());
	 * pDocument.setNamespace(namespace);
	 * }
	 * </pre>
	 * 
	 * @param namespace the namespace to use
	 * @return the generated {@code PDocument}
	 * @throws NullPointerException thrown if, and only if, {@code namespace} is {@code null}
	 */
	public PDocument toDocument(final String namespace) {
		final
		PDocument pDocument = new PDocument();
		pDocument.addClass(toClass());
		pDocument.setNamespace(namespace);
		
		return pDocument;
	}
	
	/**
	 * Returns the associated code.
	 * 
	 * @return the associated code
	 */
	public String getCode() {
		return this.code;
	}
	
	/**
	 * Returns the associated name.
	 * 
	 * @return the associated name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Compares {@code object} to this {@code Model} instance for equality.
	 * <p>
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Model}, and their respective values are equal, {@code false} otherwise.
	 * 
	 * @param object the {@code Object} to compare to this {@code Model} instance for equality
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Model}, and their respective values are equal, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Model)) {
			return false;
		} else if(!Objects.equals(this.properties, Model.class.cast(object).properties)) {
			return false;
		} else if(!Objects.equals(this.code, Model.class.cast(object).code)) {
			return false;
		} else if(!Objects.equals(this.name, Model.class.cast(object).name)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns a hash code for this {@code Model} instance.
	 * 
	 * @return a hash code for this {@code Model} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.properties, this.code, this.name);
	}
	
	/**
	 * Adds {@code property} to this {@code Model} instance.
	 * <p>
	 * If {@code property} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param property the {@link Property} to add
	 * @throws NullPointerException thrown if, and only if, {@code property} is {@code null}
	 */
	public void addProperty(final Property property) {
		this.properties.add(Objects.requireNonNull(property, "property == null"));
	}
	
	/**
	 * Removes {@code property} from this {@code Model} instance.
	 * <p>
	 * If {@code property} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param property the {@link Property} to remove
	 * @throws NullPointerException thrown if, and only if, {@code property} is {@code null}
	 */
	public void removeProperty(final Property property) {
		this.properties.remove(Objects.requireNonNull(property, "property == null"));
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static PMethod doToMethodCopy(final List<Property> properties, final String name) {
		final String nameCamelCase = Strings.formatCamelCase(name);
		final String nameCamelCaseModified = Strings.formatCamelCaseModified(name);
		
		final
		PMethod pMethod = new PMethod();
		pMethod.getBlock().addLinef("$%s = new %s();", nameCamelCaseModified, nameCamelCase);
		
		for(final Property property : properties) {
			final List<String> methodCopyLines = property.getPropertyBuilder().toMethodCopyLines(property, nameCamelCaseModified);
			
			for(final String methodCopyLine : methodCopyLines) {
				pMethod.getBlock().addLine(methodCopyLine);
			}
		}
		
		pMethod.getBlock().addLinef("");
		pMethod.getBlock().addLinef("return $%s;", nameCamelCaseModified);
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("copy");
		pMethod.setPublic(true);
		pMethod.setReturnType(new PReturnType(PType.valueOf(nameCamelCase), false));
		
		return pMethod;
	}
	
	private static PMethod doToMethodParseArray(final List<Property> properties, final String name) {
		final String nameArray = "array";
		final String nameCamelCase = Strings.formatCamelCase(name);
		final String nameCamelCaseModified = Strings.formatCamelCaseModified(name);
		final String nameUnderscoreSeparatedUpperCase = Strings.formatUnderscoreSeparatedUpperCase(name);
		
		final
		PMethod pMethod = new PMethod();
		pMethod.addParameterArgument(new PParameterArgument(nameArray, PType.ARRAY, null, false));
		pMethod.getBlock().addLinef("if(array_key_exists(self::%s, $%s)) {", nameUnderscoreSeparatedUpperCase, nameArray);
		pMethod.getBlock().addLinef("	$%s = $%s[self::%s];", nameArray, nameArray, nameUnderscoreSeparatedUpperCase);
		pMethod.getBlock().addLinef("	$%s = is_array($%s) ? $%s : [];", nameArray, nameArray, nameArray);
		pMethod.getBlock().addLinef("}");
		pMethod.getBlock().addLinef("");
		pMethod.getBlock().addLinef("$%s = new %s();", nameCamelCaseModified, nameCamelCase);
		
		for(final Property property : properties) {
			final List<String> methodParseArrayLines = property.getPropertyBuilder().toMethodParseArrayLines(property, nameArray, nameCamelCaseModified);
			
			for(final String methodParseArrayLine : methodParseArrayLines) {
				pMethod.getBlock().addLine(methodParseArrayLine);
			}
		}
		
		pMethod.getBlock().addLinef("");
		pMethod.getBlock().addLinef("return $%s;", nameCamelCaseModified);
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("parseArray");
		pMethod.setPublic(true);
		pMethod.setReturnType(new PReturnType(PType.valueOf(nameCamelCase), false));
		pMethod.setStatic(true);
		
		return pMethod;
	}
	
	private static PMethod doToMethodParseJSON(final String name) {
		final String nameCamelCase = Strings.formatCamelCase(name);
		
		final
		PMethod pMethod = new PMethod();
		pMethod.addParameterArgument(new PParameterArgument("stringJSON", PType.STRING, null, false));
		pMethod.getBlock().addLine("return self::parseArray(json_decode($stringJSON, true));");
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("parseJSON");
		pMethod.setPublic(true);
		pMethod.setReturnType(new PReturnType(PType.valueOf(nameCamelCase), false));
		pMethod.setStatic(true);
		
		return pMethod;
	}
	
	private static PMethod doToMethodSet(final List<Property> properties, final String name) {
		final String nameCamelCase = Strings.formatCamelCase(name);
		final String nameCamelCaseModified = Strings.formatCamelCaseModified(name);
		
		final
		PMethod pMethod = new PMethod();
		pMethod.addParameterArgument(new PParameterArgument(nameCamelCaseModified, PType.valueOf(nameCamelCase), null, false));
		
		for(final Property property : properties) {
			final List<String> methodSetLines = property.getPropertyBuilder().toMethodSetLines(property, nameCamelCaseModified);
			
			for(final String methodSetLine : methodSetLines) {
				pMethod.getBlock().addLine(methodSetLine);
			}
		}
		
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("set");
		pMethod.setPublic(true);
		pMethod.setReturnType(new PReturnType(PType.VOID, false));
		
		return pMethod;
	}
	
	private static PMethod doToMethodToArray(final List<Property> properties) {
		final
		PMethod pMethod = new PMethod();
		pMethod.addParameterArgument(new PParameterArgument("isIncludingNull", PType.BOOL, PValue.valueOf(false), false));
		pMethod.getBlock().addLine("$array = [];");
		
		for(final Property property : properties) {
			final List<String> methodToArrayLines = property.getPropertyBuilder().toMethodToArrayLines(property);
			
			if(methodToArrayLines.size() > 0) {
				pMethod.getBlock().addLine("");
				
				for(final String methodToArrayLine : methodToArrayLines) {
					pMethod.getBlock().addLine(methodToArrayLine);
				}
			}
		}
		
		pMethod.getBlock().addLine("");
		pMethod.getBlock().addLine("return $array;");
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("toArray");
		pMethod.setPublic(true);
		pMethod.setReturnType(new PReturnType(PType.ARRAY, false));
		
		return pMethod;
	}
	
	private static PMethod doToMethodToJSON() {
		final
		PMethod pMethod = new PMethod();
		pMethod.addParameterArgument(new PParameterArgument("isIncludingNull", PType.BOOL, PValue.valueOf(false), false));
		pMethod.addParameterArgument(new PParameterArgument("isPrettyPrinting", PType.BOOL, PValue.valueOf(false), false));
		pMethod.getBlock().addLine("return $isPrettyPrinting ? json_encode($this->toArray($isIncludingNull), JSON_PRETTY_PRINT) : json_encode($this->toArray($isIncludingNull));");
		pMethod.setEnclosedByClass(true);
		pMethod.setFinal(true);
		pMethod.setName("toJSON");
		pMethod.setPublic(true);
		pMethod.setReturnType(new PReturnType(PType.STRING, false));
		
		return pMethod;
	}
	
	private static void doSortPropertiesByName(final List<Property> properties) {
		Collections.sort(properties, (a, b) -> a.getName().compareTo(b.getName()));
	}
}