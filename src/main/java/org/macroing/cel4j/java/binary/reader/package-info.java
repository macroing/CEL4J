/**
 * Provides the Java Binary Reader API.
 * <p>
 * The Java Binary Reader API is used for reading sequences of bytes, that matches the ClassFile format of the Java Virtual Machine Specifications, into instances of the {@link org.macroing.cel4j.java.binary.classfile.ClassFile ClassFile} class.
 * <p>
 * The classes and interfaces provided by this API are specified below.
 * <h3>ClassFileReader</h3>
 * The {@link org.macroing.cel4j.java.binary.reader.ClassFileReader ClassFileReader} class is used for reading sequences of bytes into instances of the {@code ClassFile} class.
 * <p>
 * This class is the core component of this API. Most users of this API will only need this class.
 * <p>
 * A {@link org.macroing.cel4j.java.binary.reader.ClassFileReaderException ClassFileReaderException} can be thrown by this class.
 * <h3>AttributeInfoReader</h3>
 * The {@link org.macroing.cel4j.java.binary.reader.AttributeInfoReader AttributeInfoReader} interface is used for reading sequences of bytes into instances of the {@link org.macroing.cel4j.java.binary.classfile.AttributeInfo AttributeInfo} interface.
 * <p>
 * This interface is implemented and used internally by the API. But it is exposed to the public in order to act as a Service Provider Interface (SPI). This allows the user of this API to implement their own {@code AttributeInfo} and
 * {@code AttributeInfoReader} implementations. It is even possible to override existing implementations, but it is not recommended.
 * <p>
 * An {@link org.macroing.cel4j.java.binary.reader.AttributeInfoReaderException AttributeInfoReaderException} can be thrown by implementations of this interface.
 * <h3>CPInfoReader</h3>
 * The {@link org.macroing.cel4j.java.binary.reader.CPInfoReader CPInfoReader} interface is used for reading sequences of bytes into instances of the {@link org.macroing.cel4j.java.binary.classfile.CPInfo CPInfo} interface.
 * <p>
 * This interface is implemented and used internally by the API. But it is exposed to the public in order to act as a Service Provider Interface (SPI). This allows the user of this API to implement their own {@code CPInfo} and {@code CPInfoReader}
 * implementations. It is even possible to override existing implementations, but it is not recommended.
 * <p>
 * A {@link org.macroing.cel4j.java.binary.reader.CPInfoReaderException CPInfoReaderException} can be thrown by implementations of this interface.
 */
package org.macroing.cel4j.java.binary.reader;