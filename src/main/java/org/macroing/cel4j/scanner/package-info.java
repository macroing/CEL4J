/**
 * Provides the Scanner API.
 * <p>
 * The Scanner API allows for scanning of data in various forms. A brief introduction to the classes provided by this API are specified below.
 * <h3>Scanner</h3>
 * The {@link org.macroing.cel4j.scanner.Scanner Scanner} class is an abstract base class with common functionality provided by all {@code Scanner} implementations.
 * <h3>Key</h3>
 * The {@link org.macroing.cel4j.scanner.Key Key} class is associated with the state of a {@code Scanner}. It is possible to save, load and delete the state.
 * <h3>TextScanner</h3>
 * The {@link org.macroing.cel4j.scanner.TextScanner TextScanner} class is an implementation of the {@code Scanner} class that scans text. Text in this context refers to the data types {@code char} and {@code String}.
 */
package org.macroing.cel4j.scanner;