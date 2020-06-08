/**
 * Provides the Java Decompiler API.
 * <p>
 * To use this API, consider the following example. It demonstrates the simplest use case, where the default configuration parameters are used, the decompiled source code is displayed in standard output and no decompilation progress is shown.
 * <pre>
 * {@code
 * Decompiler decompiler = Decompiler.newInstance();
 * decompiler.addClass(Integer.class);
 * decompiler.decompile();
 * }
 * </pre>
 * The example below decompiles the source code to files, shows decompilation progress in standard output and changes some of the configuration parameters.
 * <pre>
 * {@code
 * Decompiler decompiler = Decompiler.newInstance();
 * decompiler.addClass(Integer.class, Consumers.file("generated", Integer.class));
 * decompiler.addClass(String.class, Consumers.file("generated", String.class));
 * decompiler.addDecompilerObserver(DecompilerObserver.print());
 * decompiler.getDecompilerConfiguration().setDisplayingAttributeInfos(true);
 * decompiler.getDecompilerConfiguration().setDisplayingConfigurationParameters(true);
 * decompiler.getDecompilerConfiguration().setDisplayingInstructions(true);
 * decompiler.getDecompilerConfiguration().setSeparatingGroups(true);
 * decompiler.getDecompilerConfiguration().setSortingGroups(true);
 * decompiler.decompile();
 * }
 * </pre>
 */
package org.macroing.cel4j.java.decompiler;