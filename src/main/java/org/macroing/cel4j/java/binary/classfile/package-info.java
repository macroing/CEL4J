/**
 * Provides the Java Binary ClassFile API.
 * <p>
 * The Java Binary ClassFile API models the ClassFile structure, including its substructures, as defined by the ClassFile format of the Java Virtual Machine Specifications.
 * <p>
 * The classes and interfaces provided by this API are specified below.
 * <h3>ClassFile</h3>
 * The {@link org.macroing.cel4j.java.binary.classfile.ClassFile ClassFile} class models the ClassFile structure.
 * <h3>AttributeInfo</h3>
 * The {@link org.macroing.cel4j.java.binary.classfile.AttributeInfo AttributeInfo} class models the general parts of the attribute_info structure. It is an abstract class. All implementations of it can be found in the package
 * {@code org.macroing.cel4j.java.binary.classfile.attributeinfo}.
 * <h3>CPInfo</h3>
 * The {@link org.macroing.cel4j.java.binary.classfile.CPInfo CPInfo} class models the general parts of the cp_info structure. It is an abstract class. All implementations of it can be found in the package
 * {@code org.macroing.cel4j.java.binary.classfile.cpinfo}.
 * <h3>FieldInfo</h3>
 * The {@link org.macroing.cel4j.java.binary.classfile.FieldInfo FieldInfo} class models the field_info structure.
 * <h3>MethodInfo</h3>
 * The {@link org.macroing.cel4j.java.binary.classfile.MethodInfo MethodInfo} class models the method_info structure.
 * <h3>Descriptors and Signatures</h3>
 * The ClassFile structure and its substructures contain references to descriptors and signatures. These can be parsed into models provided in the package {@code org.macroing.cel4j.java.binary.classfile.string}.
 */
package org.macroing.cel4j.java.binary.classfile;