/**
 * Copyright 2009 - 2022 J&#246;rgen Lundgren
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
package org.macroing.cel4j.java.binary.classfile.signature;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import org.macroing.cel4j.java.binary.classfile.signature.MethodSignature.Builder;

final class Filters {
	private static final Pattern PACKAGE_NAME_PATTERN = Pattern.compile("\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*((/|\\.)\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*)*");
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Filters() {
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static ArrayTypeSignature excludePackageName(final String packageName, final ArrayTypeSignature arrayTypeSignature) {
		final JavaTypeSignature oldJavaTypeSignature = arrayTypeSignature.getJavaTypeSignature();
		final JavaTypeSignature newJavaTypeSignature = excludePackageName(packageName, oldJavaTypeSignature);
		
		if(oldJavaTypeSignature.equals(newJavaTypeSignature)) {
			return arrayTypeSignature;
		}
		
		return ArrayTypeSignature.valueOf(newJavaTypeSignature);
	}
	
	public static ClassBound excludePackageName(final String packageName, final ClassBound classBound) {
		if(classBound.isEmpty()) {
			return classBound;
		}
		
		final ReferenceTypeSignature oldReferenceTypeSignature = classBound.getReferenceTypeSignature().get();
		final ReferenceTypeSignature newReferenceTypeSignature = excludePackageName(packageName, oldReferenceTypeSignature);
		
		if(oldReferenceTypeSignature.equals(newReferenceTypeSignature)) {
			return classBound;
		}
		
		return ClassBound.valueOf(newReferenceTypeSignature);
	}
	
	public static ClassSignature excludePackageName(final String packageName, final ClassSignature classSignature) {
		final List<SuperInterfaceSignature> oldSuperInterfaceSignatures = classSignature.getSuperInterfaceSignatures();
		final List<SuperInterfaceSignature> newSuperInterfaceSignatures = oldSuperInterfaceSignatures.stream().map(superInterfaceSignature -> excludePackageName(packageName, superInterfaceSignature)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
		
		final SuperClassSignature oldSuperClassSignature = classSignature.getSuperClassSignature();
		final SuperClassSignature newSuperClassSignature = excludePackageName(packageName, oldSuperClassSignature);
		
		final TypeParameters oldTypeParameters = classSignature.getTypeParameters().orElse(null);
		final TypeParameters newTypeParameters = oldTypeParameters != null ? excludePackageName(packageName, oldTypeParameters) : null;
		
		if(Objects.equals(oldSuperInterfaceSignatures, newSuperInterfaceSignatures) && Objects.equals(oldSuperClassSignature, newSuperClassSignature) && Objects.equals(oldTypeParameters, newTypeParameters)) {
			return classSignature;
		}
		
		if(newTypeParameters != null) {
			return ClassSignature.valueOf(newSuperClassSignature, newSuperInterfaceSignatures, newTypeParameters);
		}
		
		return ClassSignature.valueOf(newSuperClassSignature, newSuperInterfaceSignatures);
	}
	
	public static ClassTypeSignature excludePackageName(final String packageName, final ClassTypeSignature classTypeSignature) {
		final List<ClassTypeSignatureSuffix> oldClassTypeSignatureSuffixes = classTypeSignature.getClassTypeSignatureSuffixes();
		final List<ClassTypeSignatureSuffix> newClassTypeSignatureSuffixes = oldClassTypeSignatureSuffixes.stream().map(classTypeSignatureSuffix -> excludePackageName(packageName, classTypeSignatureSuffix)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
		
		final PackageSpecifier oldPackageSpecifier = classTypeSignature.getPackageSpecifier().orElse(null);
		final PackageSpecifier newPackageSpecifier = oldPackageSpecifier != null ? excludePackageName(packageName, oldPackageSpecifier) : oldPackageSpecifier;
		
		final SimpleClassTypeSignature oldSimpleClassTypeSignature = classTypeSignature.getSimpleClassTypeSignature();
		final SimpleClassTypeSignature newSimpleClassTypeSignature = excludePackageName(packageName, oldSimpleClassTypeSignature);
		
		if(Objects.equals(oldClassTypeSignatureSuffixes, newClassTypeSignatureSuffixes) && Objects.equals(oldPackageSpecifier, newPackageSpecifier) && Objects.equals(oldSimpleClassTypeSignature, newSimpleClassTypeSignature)) {
			return classTypeSignature;
		}
		
		if(newPackageSpecifier != null) {
			return ClassTypeSignature.valueOf(newSimpleClassTypeSignature, newClassTypeSignatureSuffixes, newPackageSpecifier);
		}
		
		return ClassTypeSignature.valueOf(newSimpleClassTypeSignature, newClassTypeSignatureSuffixes);
	}
	
	public static ClassTypeSignatureSuffix excludePackageName(final String packageName, final ClassTypeSignatureSuffix classTypeSignatureSuffix) {
		final SimpleClassTypeSignature oldSimpleClassTypeSignature = classTypeSignatureSuffix.getSimpleClassTypeSignature();
		final SimpleClassTypeSignature newSimpleClassTypeSignature = excludePackageName(packageName, oldSimpleClassTypeSignature);
		
		if(oldSimpleClassTypeSignature.equals(newSimpleClassTypeSignature)) {
			return classTypeSignatureSuffix;
		}
		
		return ClassTypeSignatureSuffix.valueOf(newSimpleClassTypeSignature);
	}
	
	public static FieldSignature excludePackageName(final String packageName, final FieldSignature fieldSignature) {
		return fieldSignature instanceof ReferenceTypeSignature ? excludePackageName(packageName, ReferenceTypeSignature.class.cast(fieldSignature)) : fieldSignature;
	}
	
	public static InterfaceBound excludePackageName(final String packageName, final InterfaceBound interfaceBound) {
		final ReferenceTypeSignature oldReferenceTypeSignature = interfaceBound.getReferenceTypeSignature();
		final ReferenceTypeSignature newReferenceTypeSignature = excludePackageName(packageName, oldReferenceTypeSignature);
		
		if(oldReferenceTypeSignature.equals(newReferenceTypeSignature)) {
			return interfaceBound;
		}
		
		return InterfaceBound.valueOf(newReferenceTypeSignature);
	}
	
	public static JavaTypeSignature excludePackageName(final String packageName, final JavaTypeSignature javaTypeSignature) {
		return javaTypeSignature instanceof ReferenceTypeSignature ? excludePackageName(packageName, ReferenceTypeSignature.class.cast(javaTypeSignature)) : javaTypeSignature;
	}
	
	public static MethodSignature excludePackageName(final String packageName, final MethodSignature methodSignature) {
		final List<JavaTypeSignature> oldJavaTypeSignatures = methodSignature.getJavaTypeSignatures();
		final List<JavaTypeSignature> newJavaTypeSignatures = oldJavaTypeSignatures.stream().map(javaTypeSignature -> excludePackageName(packageName, javaTypeSignature)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
		
		final List<ThrowsSignature> oldThrowsSignatures = methodSignature.getThrowsSignatures();
		final List<ThrowsSignature> newThrowsSignatures = oldThrowsSignatures.stream().map(throwsSignature -> excludePackageName(packageName, throwsSignature)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
		
		final Result oldResult = methodSignature.getResult();
		final Result newResult = excludePackageName(packageName, oldResult);
		
		final TypeParameters oldTypeParameters = methodSignature.getTypeParameters().orElse(null);
		final TypeParameters newTypeParameters = oldTypeParameters != null ? excludePackageName(packageName, oldTypeParameters) : null;
		
		if(Objects.equals(oldJavaTypeSignatures, newJavaTypeSignatures) && Objects.equals(oldThrowsSignatures, newThrowsSignatures) && Objects.equals(oldResult, newResult) && Objects.equals(oldTypeParameters, newTypeParameters)) {
			return methodSignature;
		}
		
		final Builder builder = newTypeParameters != null ? Builder.newInstance(newResult, newTypeParameters) : MethodSignature.Builder.newInstance(newResult);
		
		for(final JavaTypeSignature javaTypeSignature : newJavaTypeSignatures) {
			builder.addJavaTypeSignature(javaTypeSignature);
		}
		
		for(final ThrowsSignature throwsSignature : newThrowsSignatures) {
			builder.addThrowsSignature(throwsSignature);
		}
		
		return builder.build();
	}
	
	public static PackageSpecifier excludePackageName(final String packageName, final PackageSpecifier packageSpecifier) {
		if(PACKAGE_NAME_PATTERN.matcher(packageName).matches()) {
			final String[] packageNameParts = packageName.split("\\.|/");
			
			final List<Identifier> identifiers = packageSpecifier.getIdentifiers();
			
			if(packageNameParts.length == identifiers.size()) {
				for(int i = 0; i < packageNameParts.length; i++) {
					if(!packageNameParts[i].equals(identifiers.get(i).toExternalForm())) {
						return packageSpecifier;
					}
				}
				
				return null;
			}
		}
		
		return packageSpecifier;
	}
	
	public static ReferenceTypeSignature excludePackageName(final String packageName, final ReferenceTypeSignature referenceTypeSignature) {
		if(referenceTypeSignature instanceof ArrayTypeSignature) {
			return excludePackageName(packageName, ArrayTypeSignature.class.cast(referenceTypeSignature));
		} else if(referenceTypeSignature instanceof ClassTypeSignature) {
			return excludePackageName(packageName, ClassTypeSignature.class.cast(referenceTypeSignature));
		} else {
			return referenceTypeSignature;
		}
	}
	
	public static Result excludePackageName(final String packageName, final Result result) {
		return result instanceof JavaTypeSignature ? excludePackageName(packageName, JavaTypeSignature.class.cast(result)) : result;
	}
	
	public static Signature excludePackageName(final String packageName, final Signature signature) {
		if(signature instanceof ClassSignature) {
			return excludePackageName(packageName, ClassSignature.class.cast(signature));
		} else if(signature instanceof FieldSignature) {
			return excludePackageName(packageName, FieldSignature.class.cast(signature));
		} else if(signature instanceof MethodSignature) {
			return excludePackageName(packageName, MethodSignature.class.cast(signature));
		} else {
			return signature;
		}
	}
	
	public static SimpleClassTypeSignature excludePackageName(final String packageName, final SimpleClassTypeSignature simpleClassTypeSignature) {
		final Identifier oldIdentifier = simpleClassTypeSignature.getIdentifier();
		final Identifier newIdentifier = oldIdentifier;
		
		final TypeArguments oldTypeArguments = simpleClassTypeSignature.getTypeArguments().orElse(null);
		final TypeArguments newTypeArguments = oldTypeArguments != null ? excludePackageName(packageName, oldTypeArguments) : null;
		
		if(Objects.equals(oldTypeArguments, newTypeArguments)) {
			return simpleClassTypeSignature;
		}
		
		return SimpleClassTypeSignature.valueOf(newIdentifier, newTypeArguments);
	}
	
	public static SuperClassSignature excludePackageName(final String packageName, final SuperClassSignature superClassSignature) {
		return superClassSignature instanceof ClassTypeSignature ? excludePackageName(packageName, ClassTypeSignature.class.cast(superClassSignature)) : superClassSignature;
	}
	
	public static SuperInterfaceSignature excludePackageName(final String packageName, final SuperInterfaceSignature superInterfaceSignature) {
		return superInterfaceSignature instanceof ClassTypeSignature ? excludePackageName(packageName, ClassTypeSignature.class.cast(superInterfaceSignature)) : superInterfaceSignature;
	}
	
	public static ThrowsSignature excludePackageName(final String packageName, final ThrowsSignature throwsSignature) {
		final ReferenceTypeSignature oldReferenceTypeSignature = throwsSignature.getReferenceTypeSignature();
		final ReferenceTypeSignature newReferenceTypeSignature = excludePackageName(packageName, oldReferenceTypeSignature);
		
		if(oldReferenceTypeSignature.equals(newReferenceTypeSignature)) {
			return throwsSignature;
		}
		
		if(newReferenceTypeSignature instanceof ClassTypeSignature) {
			return ThrowsSignature.valueOf(ClassTypeSignature.class.cast(newReferenceTypeSignature));
		}
		
		return throwsSignature;
	}
	
	public static TypeArgument excludePackageName(final String packageName, final TypeArgument typeArgument) {
		final ReferenceTypeSignature oldReferenceTypeSignature = typeArgument.getReferenceTypeSignature().orElse(null);
		final ReferenceTypeSignature newReferenceTypeSignature = oldReferenceTypeSignature != null ? excludePackageName(packageName, oldReferenceTypeSignature) : null;
		
		final WildcardIndicator oldWildcardIndicator = typeArgument.getWildcardIndicator().orElse(null);
		final WildcardIndicator newWildcardIndicator = oldWildcardIndicator;
		
		if(Objects.equals(oldReferenceTypeSignature, newReferenceTypeSignature)) {
			return typeArgument;
		}
		
		if(newWildcardIndicator != null) {
			return TypeArgument.valueOf(newReferenceTypeSignature, newWildcardIndicator);
		}
		
		return TypeArgument.valueOf(newReferenceTypeSignature);
	}
	
	public static TypeArguments excludePackageName(final String packageName, final TypeArguments typeArguments) {
		final List<TypeArgument> oldTypeArguments = typeArguments.getTypeArguments();
		final List<TypeArgument> newTypeArguments = oldTypeArguments.stream().map(typeArgument -> excludePackageName(packageName, typeArgument)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
		
		if(oldTypeArguments.equals(newTypeArguments)) {
			return typeArguments;
		}
		
		if(newTypeArguments.size() > 1) {
			return TypeArguments.valueOf(newTypeArguments.get(0), newTypeArguments.subList(1, newTypeArguments.size()).toArray(new TypeArgument[newTypeArguments.size() - 1]));
		}
		
		return TypeArguments.valueOf(newTypeArguments.get(0));
	}
	
	public static TypeParameter excludePackageName(final String packageName, final TypeParameter typeParameter) {
		final ClassBound oldClassBound = typeParameter.getClassBound();
		final ClassBound newClassBound = excludePackageName(packageName, oldClassBound);
		
		final Identifier oldIdentifier = typeParameter.getIdentifier();
		final Identifier newIdentifier = oldIdentifier;
		
		final List<InterfaceBound> oldInterfaceBounds = typeParameter.getInterfaceBounds();
		final List<InterfaceBound> newInterfaceBounds = oldInterfaceBounds.stream().map(interfaceBound -> excludePackageName(packageName, interfaceBound)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
		
		if(Objects.equals(oldClassBound, newClassBound) && Objects.equals(oldInterfaceBounds, newInterfaceBounds)) {
			return typeParameter;
		}
		
		return TypeParameter.valueOf(newIdentifier, newClassBound, newInterfaceBounds.toArray(new InterfaceBound[newInterfaceBounds.size()]));
	}
	
	public static TypeParameters excludePackageName(final String packageName, final TypeParameters typeParameters) {
		final List<TypeParameter> oldTypeParameters = typeParameters.getTypeParameters();
		final List<TypeParameter> newTypeParameters = oldTypeParameters.stream().map(typeParameter -> excludePackageName(packageName, typeParameter)).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
		
		if(oldTypeParameters.equals(newTypeParameters)) {
			return typeParameters;
		}
		
		if(newTypeParameters.size() > 1) {
			return TypeParameters.valueOf(newTypeParameters.get(0), newTypeParameters.subList(1, newTypeParameters.size()).toArray(new TypeParameter[newTypeParameters.size() - 1]));
		}
		
		return TypeParameters.valueOf(newTypeParameters.get(0));
	}
}