/**
 * Provides the Java Decompiler API.
 * <p>
 * To use this API, consider the following examples.
 * <p>
 * The example below demonstrates the simplest use case, where the default configuration parameters are used, the decompiled source code is displayed in standard output and no decompilation progress is shown.
 * <pre>
 * <code>
 * try {
 *     Decompiler decompiler = Decompiler.newInstance();
 *     decompiler.addClass(Integer.class);
 *     decompiler.decompile();
 * } catch(DecompilationException e) {
 *     e.printStackTrace();
 * }
 * </code>
 * </pre>
 * The example below demonstrates more features, where some of the default configuration parameters are changed, the source code is decompiled to files and the decompilation progress is shown in standard output.
 * <pre>
 * <code>
 * try {
 *     Decompiler decompiler = Decompiler.newInstance();
 *     decompiler.addClass(Integer.class, Consumers.file("generated", Integer.class));
 *     decompiler.addClass(String.class, Consumers.file("generated", String.class));
 *     decompiler.addDecompilerObserver(DecompilerObserver.print());
 *     decompiler.getDecompilerConfiguration().setDisplayingAttributeInfos(true);
 *     decompiler.getDecompilerConfiguration().setDisplayingConfigurationParameters(true);
 *     decompiler.getDecompilerConfiguration().setDisplayingInstructions(true);
 *     decompiler.getDecompilerConfiguration().setSeparatingGroups(true);
 *     decompiler.getDecompilerConfiguration().setSortingGroups(true);
 *     decompiler.decompile();
 * } catch(DecompilationException e) {
 *     e.printStackTrace();
 * }
 * </code>
 * </pre>
 */
package org.macroing.cel4j.java.decompiler;