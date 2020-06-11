/**
 * Provides a {@code ScriptEngine} implementation called Artifact that evaluates a super-set of Java source code.
 * <p>
 * The {@code ScriptEngine} provided compiles the source code into {@code CompiledScript}s and loads them, using the context {@code ClassLoader}. It caches the {@code CompiledScript}s using a normalized version of the source code provided for that
 * {@code CompiledScript}. By doing so, no re-compilation will be performed when you add whitespace in other places than {@code String} literals.
 * <p>
 * To demonstrate its use, here is an example:
 * <pre>
 * {@code
 * ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
 * 
 * ScriptEngine scriptEngine = scriptEngineManager.getEngineByExtension("java");
 * scriptEngine.eval("System.out.println(\"Hello, World!\"); return true;");
 * }
 * </pre>
 * As you can see in the example above, you can return a result. Although you can, it's not necessary. If nothing is returned by you, {@code null} will be returned by default.
 * <p>
 * If a variable starts with a dollar sign ({@code $}), followed by a variable name (a Java identifier), that variable will be substituted for a variable in the {@code ScriptContext} and cast to its type. Lets say you have a variable {@code $string}
 * that refers to a variable called {@code "string"} in the {@code ScriptContext}, and that variable is of type {@code String}. A call such as {@code $string.length()} would return the length of the {@code String} variable. Note, however, that this
 * assumes the variable already exists in the {@code ScriptContext} prior to the evaluation of the current script. Adding a variable to the {@code ScriptContext} and then using this variable substitution mechanism to get that variable in the same
 * script won't work. The reason for this, is that the variable substitution is performed prior to the evaluation of the script itself.
 */
package org.macroing.cel4j.artifact;