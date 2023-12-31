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
package org.macroing.cel4j.java.decompiler;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A {@code DecompilerConfiguration} is used to configure hints to a {@link Decompiler} instance.
 * <p>
 * This class is thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class DecompilerConfiguration {
	private final AtomicBoolean isAnnotatingDeprecatedMethods;
	private final AtomicBoolean isAnnotatingOverriddenMethods;
	private final AtomicBoolean isDiscardingAbstractInterfaceMethodModifier;
	private final AtomicBoolean isDiscardingExtendsObject;
	private final AtomicBoolean isDiscardingPublicInterfaceMethodModifier;
	private final AtomicBoolean isDiscardingUnnecessaryPackageNames;
	private final AtomicBoolean isDisplayingAttributeInfos;
	private final AtomicBoolean isDisplayingConfigurationParameters;
	private final AtomicBoolean isDisplayingInstructions;
	private final AtomicBoolean isImportingTypes;
	private final AtomicBoolean isSeparatingGroups;
	private final AtomicBoolean isSortingGroups;
	private final AtomicReference<LocalVariableNameGenerator> localVariableNameGenerator;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Constructs a new {@code DecompilerConfiguration} instance.
	 */
	public DecompilerConfiguration() {
		this.isAnnotatingDeprecatedMethods = new AtomicBoolean(true);
		this.isAnnotatingOverriddenMethods = new AtomicBoolean(true);
		this.isDiscardingAbstractInterfaceMethodModifier = new AtomicBoolean(true);
		this.isDiscardingExtendsObject = new AtomicBoolean(true);
		this.isDiscardingPublicInterfaceMethodModifier = new AtomicBoolean(true);
		this.isDiscardingUnnecessaryPackageNames = new AtomicBoolean(true);
		this.isDisplayingAttributeInfos = new AtomicBoolean(false);
		this.isDisplayingConfigurationParameters = new AtomicBoolean(false);
		this.isDisplayingInstructions = new AtomicBoolean(false);
		this.isImportingTypes = new AtomicBoolean(true);
		this.isSeparatingGroups = new AtomicBoolean(false);
		this.isSortingGroups = new AtomicBoolean(false);
		this.localVariableNameGenerator = new AtomicReference<>(LocalVariableNameGenerator.newSimpleName());
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns the {@link LocalVariableNameGenerator} that will generate names for local variables.
	 * <p>
	 * The default {@code LocalVariableNameGenerator} is equivalent to {@code LocalVariableNameGenerator.newSimpleName()}.
	 * 
	 * @return the {@code LocalVariableNameGenerator} that will generate names for local variables
	 */
	public LocalVariableNameGenerator getLocalVariableNameGenerator() {
		return this.localVariableNameGenerator.get();
	}
	
	/**
	 * Returns {@code true} if, and only if, deprecated methods should be annotated with {@code @Deprecated}, {@code false} otherwise.
	 * <p>
	 * By default this method returns {@code true}.
	 * 
	 * @return {@code true} if, and only if, deprecated methods should be annotated with {@code @Deprecated}, {@code false} otherwise
	 */
	public boolean isAnnotatingDeprecatedMethods() {
		return this.isAnnotatingDeprecatedMethods.get();
	}
	
	/**
	 * Returns {@code true} if, and only if, overridden methods should be annotated with {@code @Override}, {@code false} otherwise.
	 * <p>
	 * By default this method returns {@code true}.
	 * 
	 * @return {@code true} if, and only if, overridden methods should be annotated with {@code @Override}, {@code false} otherwise
	 */
	public boolean isAnnotatingOverriddenMethods() {
		return this.isAnnotatingOverriddenMethods.get();
	}
	
	/**
	 * Returns {@code true} if, and only if, the abstract interface method modifier should be discarded, {@code false} otherwise.
	 * <p>
	 * By default this method returns {@code true}.
	 * 
	 * @return {@code true} if, and only if, the abstract interface method modifier should be discarded, {@code false} otherwise
	 */
	public boolean isDiscardingAbstractInterfaceMethodModifier() {
		return this.isDiscardingAbstractInterfaceMethodModifier.get();
	}
	
	/**
	 * Returns {@code true} if, and only if, the extends clause should be discarded if the super class is {@code java.lang.Object}, {@code false} otherwise.
	 * <p>
	 * By default this method returns {@code true}.
	 * 
	 * @return {@code true} if, and only if, the extends clause should be discarded if the super class is {@code java.lang.Object}, {@code false} otherwise
	 */
	public boolean isDiscardingExtendsObject() {
		return this.isDiscardingExtendsObject.get();
	}
	
	/**
	 * Returns {@code true} if, and only if, the public interface method modifier should be discarded, {@code false} otherwise.
	 * <p>
	 * By default this method returns {@code true}.
	 * 
	 * @return {@code true} if, and only if, the public interface method modifier should be discarded, {@code false} otherwise
	 */
	public boolean isDiscardingPublicInterfaceMethodModifier() {
		return this.isDiscardingPublicInterfaceMethodModifier.get();
	}
	
	/**
	 * Returns {@code true} if, and only if, package names should be discarded if unnecessary, {@code false} otherwise.
	 * <p>
	 * By default this method returns {@code true}.
	 * <p>
	 * There are two scenarios where package names are unnecessary, they are:
	 * <ul>
	 * <li>The decompiled type is in the same package as another type used by it.</li>
	 * <li>The decompiled type is using a type from the package {@code java.lang}.</li>
	 * </ul>
	 * 
	 * @return {@code true} if, and only if, package names should be discarded if unnecessary, {@code false} otherwise
	 */
	public boolean isDiscardingUnnecessaryPackageNames() {
		return this.isDiscardingUnnecessaryPackageNames.get();
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code attribute_info} structures should be displayed in a comment in all methods where applicable, {@code false} otherwise.
	 * <p>
	 * By default this method returns {@code false}.
	 * 
	 * @return {@code true} if, and only if, {@code attribute_info} structures should be displayed in a comment in all methods where applicable, {@code false} otherwise
	 */
	public boolean isDisplayingAttributeInfos() {
		return this.isDisplayingAttributeInfos.get();
	}
	
	/**
	 * Returns {@code true} if, and only if, configuration parameters should be displayed in a comment at the top of the compilation unit, {@code false} otherwise.
	 * <p>
	 * By default this method returns {@code false}.
	 * 
	 * @return {@code true} if, and only if, configuration parameters should be displayed in a comment at the top of the compilation unit, {@code false} otherwise
	 */
	public boolean isDisplayingConfigurationParameters() {
		return this.isDisplayingConfigurationParameters.get();
	}
	
	/**
	 * Returns {@code true} if, and only if, instructions should be displayed in a comment in all methods where applicable, {@code false} otherwise.
	 * <p>
	 * By default this method returns {@code false}.
	 * 
	 * @return {@code true} if, and only if, instructions should be displayed in a comment in all methods where applicable, {@code false} otherwise
	 */
	public boolean isDisplayingInstructions() {
		return this.isDisplayingInstructions.get();
	}
	
	/**
	 * Returns {@code true} if, and only if, types should be imported with import statements, {@code false} otherwise.
	 * <p>
	 * By default this method returns {@code true}.
	 * 
	 * @return {@code true} if, and only if, types should be imported with import statements, {@code false} otherwise
	 */
	public boolean isImportingTypes() {
		return this.isImportingTypes.get();
	}
	
	/**
	 * Returns {@code true} if, and only if, different groups of constructs should be separated with comments, {@code false} otherwise.
	 * <p>
	 * By default this method returns {@code false}.
	 * 
	 * @return {@code true} if, and only if, different groups of constructs should be separated with comments, {@code false} otherwise
	 */
	public boolean isSeparatingGroups() {
		return this.isSeparatingGroups.get();
	}
	
	/**
	 * Returns {@code true} if, and only if, different groups of constructs should be sorted, {@code false} otherwise.
	 * <p>
	 * By default this method returns {@code false}.
	 * 
	 * @return {@code true} if, and only if, different groups of constructs should be sorted, {@code false} otherwise
	 */
	public boolean isSortingGroups() {
		return this.isSortingGroups.get();
	}
	
	/**
	 * Sets whether deprecated methods should be annotated with {@code @Deprecated}.
	 * <p>
	 * The default value is {@code true}.
	 * 
	 * @param isAnnotatingDeprecatedMethods {@code true} if, and only if, deprecated methods should be annotated with {@code @Deprecated}, {@code false} otherwise
	 */
	public void setAnnotatingDeprecatedMethods(final boolean isAnnotatingDeprecatedMethods) {
		this.isAnnotatingDeprecatedMethods.set(isAnnotatingDeprecatedMethods);
	}
	
	/**
	 * Sets whether overridden methods should be annotated with {@code @Override}.
	 * <p>
	 * The default value is {@code true}.
	 * 
	 * @param isAnnotatingOverriddenMethods {@code true} if, and only if, overridden methods should be annotated with {@code @Override}, {@code false} otherwise
	 */
	public void setAnnotatingOverriddenMethods(final boolean isAnnotatingOverriddenMethods) {
		this.isAnnotatingOverriddenMethods.set(isAnnotatingOverriddenMethods);
	}
	
	/**
	 * Sets whether the abstract interface method modifier should be discarded.
	 * <p>
	 * The default value is {@code true}.
	 * 
	 * @param isDiscardingAbstractInterfaceMethodModifier {@code true} if, and only if, the abstract interface method modifier should be discarded, {@code false} otherwise
	 */
	public void setDiscardingAbstractInterfaceMethodModifier(final boolean isDiscardingAbstractInterfaceMethodModifier) {
		this.isDiscardingAbstractInterfaceMethodModifier.set(isDiscardingAbstractInterfaceMethodModifier);
	}
	
	/**
	 * Sets whether the extends clause should be discarded if the super class is {@code java.lang.Object}.
	 * <p>
	 * The default value is {@code true}.
	 * 
	 * @param isDiscardingExtendsObject {@code true} if, and only if, the extends clause should be discarded if the super class is {@code java.lang.Object}, {@code false} otherwise
	 */
	public void setDiscardingExtendsObject(final boolean isDiscardingExtendsObject) {
		this.isDiscardingExtendsObject.set(isDiscardingExtendsObject);
	}
	
	/**
	 * Sets whether the public interface method modifier should be discarded.
	 * <p>
	 * The default value is {@code true}.
	 * 
	 * @param isDiscardingPublicInterfaceMethodModifier {@code true} if, and only if, the public interface method modifier should be discarded, {@code false} otherwise
	 */
	public void setDiscardingPublicInterfaceMethodModifier(final boolean isDiscardingPublicInterfaceMethodModifier) {
		this.isDiscardingPublicInterfaceMethodModifier.set(isDiscardingPublicInterfaceMethodModifier);
	}
	
	/**
	 * Sets whether package names should be discarded if unnecessary.
	 * <p>
	 * The default value is {@code true}.
	 * <p>
	 * There are two scenarios where package names are unnecessary, they are:
	 * <ul>
	 * <li>The decompiled type is in the same package as another type used by it.</li>
	 * <li>The decompiled type is using a type from the package {@code java.lang}.</li>
	 * </ul>
	 * 
	 * @param isDiscardingUnnecessaryPackageNames {@code true} if, and only if, package names should be discarded if unnecessary, {@code false} otherwise
	 */
	public void setDiscardingUnnecessaryPackageNames(final boolean isDiscardingUnnecessaryPackageNames) {
		this.isDiscardingUnnecessaryPackageNames.set(isDiscardingUnnecessaryPackageNames);
	}
	
	/**
	 * Sets whether {@code attribute_info} structures should be displayed in a comment in all methods where applicable.
	 * <p>
	 * The default value is {@code false}.
	 * 
	 * @param isDisplayingAttributeInfos {@code true} if, and only if, {@code attribute_info} structures should be displayed in a comment in all methods where applicable, {@code false} otherwise
	 */
	public void setDisplayingAttributeInfos(final boolean isDisplayingAttributeInfos) {
		this.isDisplayingAttributeInfos.set(isDisplayingAttributeInfos);
	}
	
	/**
	 * Sets whether configuration parameters should be displayed in a comment at the top of the compilation unit.
	 * <p>
	 * The default value is {@code false}.
	 * 
	 * @param isDisplayingConfigurationParameters {@code true} if, and only if, configuration parameters should be displayed in a comment at the top of the compilation unit, {@code false} otherwise
	 */
	public void setDisplayingConfigurationParameters(final boolean isDisplayingConfigurationParameters) {
		this.isDisplayingConfigurationParameters.set(isDisplayingConfigurationParameters);
	}
	
	/**
	 * Sets whether instructions should be displayed in a comment in all methods where applicable.
	 * <p>
	 * The default value is {@code false}.
	 * 
	 * @param isDisplayingInstructions {@code true} if, and only if, instructions should be displayed in a comment in all methods where applicable, {@code false} otherwise
	 */
	public void setDisplayingInstructions(final boolean isDisplayingInstructions) {
		this.isDisplayingInstructions.set(isDisplayingInstructions);
	}
	
	/**
	 * Sets whether types should be imported with import statements.
	 * <p>
	 * The default value is {@code true}.
	 * 
	 * @param isImportingTypes {@code true} if, and only if, types should be imported with import statements, {@code false} otherwise
	 */
	public void setImportingTypes(final boolean isImportingTypes) {
		this.isImportingTypes.set(isImportingTypes);
	}
	
	/**
	 * Sets the {@link LocalVariableNameGenerator} that will generate names for local variables.
	 * <p>
	 * The default {@code LocalVariableNameGenerator} is equivalent to {@code LocalVariableNameGenerator.newSimpleName()}.
	 * <p>
	 * If {@code localVariableNameGenerator} is {@code null}, a {@code NullPointerException} will be thrown.
	 * 
	 * @param localVariableNameGenerator the new {@code LocalVariableNameGenerator}
	 * @throws NullPointerException thrown if, and only if, {@code localVariableNameGenerator} is {@code null}
	 */
	public void setLocalVariableNameGenerator(final LocalVariableNameGenerator localVariableNameGenerator) {
		this.localVariableNameGenerator.set(Objects.requireNonNull(localVariableNameGenerator, "localVariableNameGenerator == null"));
	}
	
	/**
	 * Sets whether different groups of constructs should be separated with comments.
	 * <p>
	 * The default value is {@code false}.
	 * 
	 * @param isSeparatingGroups {@code true} if, and only if, different groups of constructs should be separated with comments, {@code false} otherwise
	 */
	public void setSeparatingGroups(final boolean isSeparatingGroups) {
		this.isSeparatingGroups.set(isSeparatingGroups);
	}
	
	/**
	 * Sets whether different groups of constructs should be sorted.
	 * <p>
	 * The default value is {@code false}.
	 * 
	 * @param isSortingGroups {@code true} if, and only if, different groups of constructs should be sorted, {@code false} otherwise
	 */
	public void setSortingGroups(final boolean isSortingGroups) {
		this.isSortingGroups.set(isSortingGroups);
	}
}