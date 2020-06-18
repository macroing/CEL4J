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
package org.macroing.cel4j.java.binary.classfile.attributeinfo;

import java.io.DataOutput;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;//TODO: Update Javadocs!
import java.util.Arrays;
import java.util.Objects;

import org.macroing.cel4j.node.Node;
import org.macroing.cel4j.util.ParameterArguments;

/**
 * An {@code Instruction} that can be found as a part of any {@code CodeAttribute} instances.
 * <p>
 * This class is immutable and therefore also thread-safe.
 * 
 * @since 1.0.0
 * @author J&#246;rgen Lundgren
 */
public final class Instruction implements Node {
	/**
	 * The mnemonic for the instruction arraylength.
	 */
	public static final String MNEMONIC_ARRAY_LENGTH = "arraylength";
	
	/**
	 * The mnemonic for the instruction aaload.
	 */
	public static final String MNEMONIC_A_A_LOAD = "aaload";
	
	/**
	 * The mnemonic for the instruction aastore.
	 */
	public static final String MNEMONIC_A_A_STORE = "aastore";
	
	/**
	 * The mnemonic for the instruction aconst_null.
	 */
	public static final String MNEMONIC_A_CONST_NULL = "aconst_null";
	
	/**
	 * The mnemonic for the instruction aload.
	 */
	public static final String MNEMONIC_A_LOAD = "aload";
	
	/**
	 * The mnemonic for the instruction aload_0.
	 */
	public static final String MNEMONIC_A_LOAD_0 = "aload_0";
	
	/**
	 * The mnemonic for the instruction aload_1.
	 */
	public static final String MNEMONIC_A_LOAD_1 = "aload_1";
	
	/**
	 * The mnemonic for the instruction aload_2.
	 */
	public static final String MNEMONIC_A_LOAD_2 = "aload_2";
	
	/**
	 * The mnemonic for the instruction aload_3.
	 */
	public static final String MNEMONIC_A_LOAD_3 = "aload_3";
	
	/**
	 * The mnemonic for the instruction anewarray.
	 */
	public static final String MNEMONIC_A_NEW_ARRAY = "anewarray";
	
	/**
	 * The mnemonic for the instruction areturn.
	 */
	public static final String MNEMONIC_A_RETURN = "areturn";
	
	/**
	 * The mnemonic for the instruction astore.
	 */
	public static final String MNEMONIC_A_STORE = "astore";
	
	/**
	 * The mnemonic for the instruction astore_0.
	 */
	public static final String MNEMONIC_A_STORE_0 = "astore_0";
	
	/**
	 * The mnemonic for the instruction astore_1.
	 */
	public static final String MNEMONIC_A_STORE_1 = "astore_1";
	
	/**
	 * The mnemonic for the instruction astore_2.
	 */
	public static final String MNEMONIC_A_STORE_2 = "astore_2";
	
	/**
	 * The mnemonic for the instruction astore_3.
	 */
	public static final String MNEMONIC_A_STORE_3 = "astore_3";
	
	/**
	 * The mnemonic for the instruction athrow.
	 */
	public static final String MNEMONIC_A_THROW = "athrow";
	
	/**
	 * The mnemonic for the instruction breakpoint.
	 */
	public static final String MNEMONIC_BREAK_POINT = "breakpoint";
	
	/**
	 * The mnemonic for the instruction baload.
	 */
	public static final String MNEMONIC_B_A_LOAD = "baload";
	
	/**
	 * The mnemonic for the instruction bastore.
	 */
	public static final String MNEMONIC_B_A_STORE = "bastore";
	
	/**
	 * The mnemonic for the instruction bipush.
	 */
	public static final String MNEMONIC_B_I_PUSH = "bipush";
	
	/**
	 * The mnemonic for the instruction checkcast.
	 */
	public static final String MNEMONIC_CHECK_CAST = "checkcast";
	
	/**
	 * The mnemonic for the instruction caload.
	 */
	public static final String MNEMONIC_C_A_LOAD = "caload";
	
	/**
	 * The mnemonic for the instruction castore.
	 */
	public static final String MNEMONIC_C_A_STORE = "castore";
	
	/**
	 * The mnemonic for the instruction dup.
	 */
	public static final String MNEMONIC_DUP = "dup";
	
	/**
	 * The mnemonic for the instruction dup2.
	 */
	public static final String MNEMONIC_DUP_2 = "dup2";
	
	/**
	 * The mnemonic for the instruction dup2_x1.
	 */
	public static final String MNEMONIC_DUP_2_X_1 = "dup2_x1";
	
	/**
	 * The mnemonic for the instruction dup2_x2.
	 */
	public static final String MNEMONIC_DUP_2_X_2 = "dup2_x2";
	
	/**
	 * The mnemonic for the instruction dup_x1.
	 */
	public static final String MNEMONIC_DUP_X_1 = "dup_x1";
	
	/**
	 * The mnemonic for the instruction dup_x2.
	 */
	public static final String MNEMONIC_DUP_X_2 = "dup_x2";
	
	/**
	 * The mnemonic for the instruction d2f.
	 */
	public static final String MNEMONIC_D_2_F = "d2f";
	
	/**
	 * The mnemonic for the instruction d2i.
	 */
	public static final String MNEMONIC_D_2_I = "d2i";
	
	/**
	 * The mnemonic for the instruction d2l.
	 */
	public static final String MNEMONIC_D_2_L = "d2l";
	
	/**
	 * The mnemonic for the instruction dadd.
	 */
	public static final String MNEMONIC_D_ADD = "dadd";
	
	/**
	 * The mnemonic for the instruction daload.
	 */
	public static final String MNEMONIC_D_A_LOAD = "daload";
	
	/**
	 * The mnemonic for the instruction dastore.
	 */
	public static final String MNEMONIC_D_A_STORE = "dastore";
	
	/**
	 * The mnemonic for the instruction dcmpg.
	 */
	public static final String MNEMONIC_D_CMP_G = "dcmpg";
	
	/**
	 * The mnemonic for the instruction dcmpl.
	 */
	public static final String MNEMONIC_D_CMP_L = "dcmpl";
	
	/**
	 * The mnemonic for the instruction dconst_0.
	 */
	public static final String MNEMONIC_D_CONST_0 = "dconst_0";
	
	/**
	 * The mnemonic for the instruction dconst_1.
	 */
	public static final String MNEMONIC_D_CONST_1 = "dconst_1";
	
	/**
	 * The mnemonic for the instruction ddiv.
	 */
	public static final String MNEMONIC_D_DIV = "ddiv";
	
	/**
	 * The mnemonic for the instruction dload.
	 */
	public static final String MNEMONIC_D_LOAD = "dload";
	
	/**
	 * The mnemonic for the instruction dload_0.
	 */
	public static final String MNEMONIC_D_LOAD_0 = "dload_0";
	
	/**
	 * The mnemonic for the instruction dload_1.
	 */
	public static final String MNEMONIC_D_LOAD_1 = "dload_1";
	
	/**
	 * The mnemonic for the instruction dload_2.
	 */
	public static final String MNEMONIC_D_LOAD_2 = "dload_2";
	
	/**
	 * The mnemonic for the instruction dload_3.
	 */
	public static final String MNEMONIC_D_LOAD_3 = "dload_3";
	
	/**
	 * The mnemonic for the instruction dmul.
	 */
	public static final String MNEMONIC_D_MUL = "dmul";
	
	/**
	 * The mnemonic for the instruction dneg.
	 */
	public static final String MNEMONIC_D_NEG = "dneg";
	
	/**
	 * The mnemonic for the instruction drem.
	 */
	public static final String MNEMONIC_D_REM = "drem";
	
	/**
	 * The mnemonic for the instruction dreturn.
	 */
	public static final String MNEMONIC_D_RETURN = "dreturn";
	
	/**
	 * The mnemonic for the instruction dstore.
	 */
	public static final String MNEMONIC_D_STORE = "dstore";
	
	/**
	 * The mnemonic for the instruction dstore_0.
	 */
	public static final String MNEMONIC_D_STORE_0 = "dstore_0";
	
	/**
	 * The mnemonic for the instruction dstore_1.
	 */
	public static final String MNEMONIC_D_STORE_1 = "dstore_1";
	
	/**
	 * The mnemonic for the instruction dstore_2.
	 */
	public static final String MNEMONIC_D_STORE_2 = "dstore_2";
	
	/**
	 * The mnemonic for the instruction dstore_3.
	 */
	public static final String MNEMONIC_D_STORE_3 = "dstore_3";
	
	/**
	 * The mnemonic for the instruction dsub.
	 */
	public static final String MNEMONIC_D_SUB = "dsub";
	
	/**
	 * The mnemonic for the instruction f2d.
	 */
	public static final String MNEMONIC_F_2_D = "f2d";
	
	/**
	 * The mnemonic for the instruction f2i.
	 */
	public static final String MNEMONIC_F_2_I = "f2i";
	
	/**
	 * The mnemonic for the instruction f2l.
	 */
	public static final String MNEMONIC_F_2_L = "f2l";
	
	/**
	 * The mnemonic for the instruction fadd.
	 */
	public static final String MNEMONIC_F_ADD = "fadd";
	
	/**
	 * The mnemonic for the instruction faload.
	 */
	public static final String MNEMONIC_F_A_LOAD = "faload";
	
	/**
	 * The mnemonic for the instruction fastore.
	 */
	public static final String MNEMONIC_F_A_STORE = "fastore";
	
	/**
	 * The mnemonic for the instruction fcmpg.
	 */
	public static final String MNEMONIC_F_CMP_G = "fcmpg";
	
	/**
	 * The mnemonic for the instruction fcmpl.
	 */
	public static final String MNEMONIC_F_CMP_L = "fcmpl";
	
	/**
	 * The mnemonic for the instruction fconst_0.
	 */
	public static final String MNEMONIC_F_CONST_0 = "fconst_0";
	
	/**
	 * The mnemonic for the instruction fconst_1.
	 */
	public static final String MNEMONIC_F_CONST_1 = "fconst_1";
	
	/**
	 * The mnemonic for the instruction fconst_2.
	 */
	public static final String MNEMONIC_F_CONST_2 = "fconst_2";
	
	/**
	 * The mnemonic for the instruction fdiv.
	 */
	public static final String MNEMONIC_F_DIV = "fdiv";
	
	/**
	 * The mnemonic for the instruction fload.
	 */
	public static final String MNEMONIC_F_LOAD = "fload";
	
	/**
	 * The mnemonic for the instruction fload_0.
	 */
	public static final String MNEMONIC_F_LOAD_0 = "fload_0";
	
	/**
	 * The mnemonic for the instruction fload_1.
	 */
	public static final String MNEMONIC_F_LOAD_1 = "fload_1";
	
	/**
	 * The mnemonic for the instruction fload_2.
	 */
	public static final String MNEMONIC_F_LOAD_2 = "fload_2";
	
	/**
	 * The mnemonic for the instruction fload_3.
	 */
	public static final String MNEMONIC_F_LOAD_3 = "fload_3";
	
	/**
	 * The mnemonic for the instruction fmul.
	 */
	public static final String MNEMONIC_F_MUL = "fmul";
	
	/**
	 * The mnemonic for the instruction fneg.
	 */
	public static final String MNEMONIC_F_NEG = "fneg";
	
	/**
	 * The mnemonic for the instruction frem.
	 */
	public static final String MNEMONIC_F_REM = "frem";
	
	/**
	 * The mnemonic for the instruction freturn.
	 */
	public static final String MNEMONIC_F_RETURN = "freturn";
	
	/**
	 * The mnemonic for the instruction fstore.
	 */
	public static final String MNEMONIC_F_STORE = "fstore";
	
	/**
	 * The mnemonic for the instruction fstore_0.
	 */
	public static final String MNEMONIC_F_STORE_0 = "fstore_0";
	
	/**
	 * The mnemonic for the instruction fstore_1.
	 */
	public static final String MNEMONIC_F_STORE_1 = "fstore_1";
	
	/**
	 * The mnemonic for the instruction fstore_2.
	 */
	public static final String MNEMONIC_F_STORE_2 = "fstore_2";
	
	/**
	 * The mnemonic for the instruction fstore_3.
	 */
	public static final String MNEMONIC_F_STORE_3 = "fstore_3";
	
	/**
	 * The mnemonic for the instruction fsub.
	 */
	public static final String MNEMONIC_F_SUB = "fsub";
	
	/**
	 * The mnemonic for the instruction getfield.
	 */
	public static final String MNEMONIC_GET_FIELD = "getfield";
	
	/**
	 * The mnemonic for the instruction getstatic.
	 */
	public static final String MNEMONIC_GET_STATIC = "getstatic";
	
	/**
	 * The mnemonic for the instruction goto.
	 */
	public static final String MNEMONIC_GO_TO = "goto";
	
	/**
	 * The mnemonic for the instruction goto_w.
	 */
	public static final String MNEMONIC_GO_TO_W = "goto_w";
	
	/**
	 * The mnemonic for the instruction if_acmpeg.
	 */
	public static final String MNEMONIC_IF_A_CMP_EQ = "if_acmpeq";
	
	/**
	 * The mnemonic for the instruction if_acmpne.
	 */
	public static final String MNEMONIC_IF_A_CMP_N_E = "if_acmpne";
	
	/**
	 * The mnemonic for the instruction ifeq.
	 */
	public static final String MNEMONIC_IF_EQ = "ifeq";
	
	/**
	 * The mnemonic for the instruction ifge.
	 */
	public static final String MNEMONIC_IF_G_E = "ifge";
	
	/**
	 * The mnemonic for the instruction ifgt.
	 */
	public static final String MNEMONIC_IF_G_T = "ifgt";
	
	/**
	 * The mnemonic for the instruction if_icmpeq.
	 */
	public static final String MNEMONIC_IF_I_CMP_EQ = "if_icmpeq";
	
	/**
	 * The mnemonic for the instruction if_icmpge.
	 */
	public static final String MNEMONIC_IF_I_CMP_G_E = "if_icmpge";
	
	/**
	 * The mnemonic for the instruction if_icmpgt.
	 */
	public static final String MNEMONIC_IF_I_CMP_G_T = "if_icmpgt";
	
	/**
	 * The mnemonic for the instruction if_icmple.
	 */
	public static final String MNEMONIC_IF_I_CMP_L_E = "if_icmple";
	
	/**
	 * The mnemonic for the instruction if_icmplt.
	 */
	public static final String MNEMONIC_IF_I_CMP_L_T = "if_icmplt";
	
	/**
	 * The mnemonic for the instruction if_icmpne.
	 */
	public static final String MNEMONIC_IF_I_CMP_N_E = "if_icmpne";
	
	/**
	 * The mnemonic for the instruction ifle.
	 */
	public static final String MNEMONIC_IF_L_E = "ifle";
	
	/**
	 * The mnemonic for the instruction iflt.
	 */
	public static final String MNEMONIC_IF_L_T = "iflt";
	
	/**
	 * The mnemonic for the instruction ifnonnull.
	 */
	public static final String MNEMONIC_IF_NON_NULL = "ifnonnull";
	
	/**
	 * The mnemonic for the instruction ifnull.
	 */
	public static final String MNEMONIC_IF_NULL = "ifnull";
	
	/**
	 * The mnemonic for the instruction ifne.
	 */
	public static final String MNEMONIC_IF_N_E = "ifne";
	
	/**
	 * The mnemonic for the instruction impdep1.
	 */
	public static final String MNEMONIC_IMP_DEP_1 = "impdep1";
	
	/**
	 * The mnemonic for the instruction impdep2.
	 */
	public static final String MNEMONIC_IMP_DEP_2 = "impdep2";
	
	/**
	 * The mnemonic for the instruction instanceof.
	 */
	public static final String MNEMONIC_INSTANCE_OF = "instanceof";
	
	/**
	 * The mnemonic for the instruction invokedynamic.
	 */
	public static final String MNEMONIC_INVOKE_DYNAMIC = "invokedynamic";
	
	/**
	 * The mnemonic for the instruction invokeinterface.
	 */
	public static final String MNEMONIC_INVOKE_INTERFACE = "invokeinterface";
	
	/**
	 * The mnemonic for the instruction invokespecial.
	 */
	public static final String MNEMONIC_INVOKE_SPECIAL = "invokespecial";
	
	/**
	 * The mnemonic for the instruction invokestatic.
	 */
	public static final String MNEMONIC_INVOKE_STATIC = "invokestatic";
	
	/**
	 * The mnemonic for the instruction invokevirtual.
	 */
	public static final String MNEMONIC_INVOKE_VIRTUAL = "invokevirtual";
	
	/**
	 * The mnemonic for the instruction i2b.
	 */
	public static final String MNEMONIC_I_2_B = "i2b";
	
	/**
	 * The mnemonic for the instruction i2c.
	 */
	public static final String MNEMONIC_I_2_C = "i2c";
	
	/**
	 * The mnemonic for the instruction i2d.
	 */
	public static final String MNEMONIC_I_2_D = "i2d";
	
	/**
	 * The mnemonic for the instruction i2f.
	 */
	public static final String MNEMONIC_I_2_F = "i2f";
	
	/**
	 * The mnemonic for the instruction i2l.
	 */
	public static final String MNEMONIC_I_2_L = "i2l";
	
	/**
	 * The mnemonic for the instruction i2s.
	 */
	public static final String MNEMONIC_I_2_S = "i2s";
	
	/**
	 * The mnemonic for the instruction iadd.
	 */
	public static final String MNEMONIC_I_ADD = "iadd";
	
	/**
	 * The mnemonic for the instruction iand.
	 */
	public static final String MNEMONIC_I_AND = "iand";
	
	/**
	 * The mnemonic for the instruction iaload.
	 */
	public static final String MNEMONIC_I_A_LOAD = "iaload";
	
	/**
	 * The mnemonic for the instruction iastore.
	 */
	public static final String MNEMONIC_I_A_STORE = "iastore";
	
	/**
	 * The mnemonic for the instruction iconst_0.
	 */
	public static final String MNEMONIC_I_CONST_0 = "iconst_0";
	
	/**
	 * The mnemonic for the instruction iconst_1.
	 */
	public static final String MNEMONIC_I_CONST_1 = "iconst_1";
	
	/**
	 * The mnemonic for the instruction iconst_2.
	 */
	public static final String MNEMONIC_I_CONST_2 = "iconst_2";
	
	/**
	 * The mnemonic for the instruction iconst_3.
	 */
	public static final String MNEMONIC_I_CONST_3 = "iconst_3";
	
	/**
	 * The mnemonic for the instruction iconst_4.
	 */
	public static final String MNEMONIC_I_CONST_4 = "iconst_4";
	
	/**
	 * The mnemonic for the instruction iconst_5.
	 */
	public static final String MNEMONIC_I_CONST_5 = "iconst_5";
	
	/**
	 * The mnemonic for the instruction iconst_m1.
	 */
	public static final String MNEMONIC_I_CONST_M1 = "iconst_m1";
	
	/**
	 * The mnemonic for the instruction idiv.
	 */
	public static final String MNEMONIC_I_DIV = "idiv";
	
	/**
	 * The mnemonic for the instruction iinc.
	 */
	public static final String MNEMONIC_I_INC = "iinc";
	
	/**
	 * The mnemonic for the instruction iload.
	 */
	public static final String MNEMONIC_I_LOAD = "iload";
	
	/**
	 * The mnemonic for the instruction iload_0.
	 */
	public static final String MNEMONIC_I_LOAD_0 = "iload_0";
	
	/**
	 * The mnemonic for the instruction iload_1.
	 */
	public static final String MNEMONIC_I_LOAD_1 = "iload_1";
	
	/**
	 * The mnemonic for the instruction iload_2.
	 */
	public static final String MNEMONIC_I_LOAD_2 = "iload_2";
	
	/**
	 * The mnemonic for the instruction iload_3.
	 */
	public static final String MNEMONIC_I_LOAD_3 = "iload_3";
	
	/**
	 * The mnemonic for the instruction imul.
	 */
	public static final String MNEMONIC_I_MUL = "imul";
	
	/**
	 * The mnemonic for the instruction ineg.
	 */
	public static final String MNEMONIC_I_NEG = "ineg";
	
	/**
	 * The mnemonic for the instruction ior.
	 */
	public static final String MNEMONIC_I_OR = "ior";
	
	/**
	 * The mnemonic for the instruction irem.
	 */
	public static final String MNEMONIC_I_REM = "irem";
	
	/**
	 * The mnemonic for the instruction ireturn.
	 */
	public static final String MNEMONIC_I_RETURN = "ireturn";
	
	/**
	 * The mnemonic for the instruction ishl.
	 */
	public static final String MNEMONIC_I_SH_L = "ishl";
	
	/**
	 * The mnemonic for the instruction ishr.
	 */
	public static final String MNEMONIC_I_SH_R = "ishr";
	
	/**
	 * The mnemonic for the instruction istore.
	 */
	public static final String MNEMONIC_I_STORE = "istore";
	
	/**
	 * The mnemonic for the instruction istore_0.
	 */
	public static final String MNEMONIC_I_STORE_0 = "istore_0";
	
	/**
	 * The mnemonic for the instruction istore_1.
	 */
	public static final String MNEMONIC_I_STORE_1 = "istore_1";
	
	/**
	 * The mnemonic for the instruction istore_2.
	 */
	public static final String MNEMONIC_I_STORE_2 = "istore_2";
	
	/**
	 * The mnemonic for the instruction istore_3.
	 */
	public static final String MNEMONIC_I_STORE_3 = "istore_3";
	
	/**
	 * The mnemonic for the instruction isub.
	 */
	public static final String MNEMONIC_I_SUB = "isub";
	
	/**
	 * The mnemonic for the instruction iushr.
	 */
	public static final String MNEMONIC_I_U_SH_R = "iushr";
	
	/**
	 * The mnemonic for the instruction ixor.
	 */
	public static final String MNEMONIC_I_XOR = "ixor";
	
	/**
	 * The mnemonic for the instruction jsr.
	 */
	public static final String MNEMONIC_J_S_R = "jsr";
	
	/**
	 * The mnemonic for the instruction jsr_w.
	 */
	public static final String MNEMONIC_J_S_R_W = "jsr_w";
	
	/**
	 * The mnemonic for the instruction lookupswitch.
	 */
	public static final String MNEMONIC_LOOKUP_SWITCH = "lookupswitch";
	
	/**
	 * The mnemonic for the instruction l2d.
	 */
	public static final String MNEMONIC_L_2_D = "l2d";
	
	/**
	 * The mnemonic for the instruction l2f.
	 */
	public static final String MNEMONIC_L_2_F = "l2f";
	
	/**
	 * The mnemonic for the instruction l2i.
	 */
	public static final String MNEMONIC_L_2_I = "l2i";
	
	/**
	 * The mnemonic for the instruction ladd.
	 */
	public static final String MNEMONIC_L_ADD = "ladd";
	
	/**
	 * The mnemonic for the instruction land.
	 */
	public static final String MNEMONIC_L_AND = "land";
	
	/**
	 * The mnemonic for the instruction laload.
	 */
	public static final String MNEMONIC_L_A_LOAD = "laload";
	
	/**
	 * The mnemonic for the instruction lastore.
	 */
	public static final String MNEMONIC_L_A_STORE = "lastore";
	
	/**
	 * The mnemonic for the instruction lcmp.
	 */
	public static final String MNEMONIC_L_CMP = "lcmp";
	
	/**
	 * The mnemonic for the instruction lconst_0.
	 */
	public static final String MNEMONIC_L_CONST_0 = "lconst_0";
	
	/**
	 * The mnemonic for the instruction lconst_1.
	 */
	public static final String MNEMONIC_L_CONST_1 = "lconst_1";
	
	/**
	 * The mnemonic for the instruction ldiv.
	 */
	public static final String MNEMONIC_L_DIV = "ldiv";
	
	/**
	 * The mnemonic for the instruction ldc.
	 */
	public static final String MNEMONIC_L_D_C = "ldc";
	
	/**
	 * The mnemonic for the instruction ldc2_w.
	 */
	public static final String MNEMONIC_L_D_C_2_W = "ldc2_w";
	
	/**
	 * The mnemonic for the instruction ldc_w.
	 */
	public static final String MNEMONIC_L_D_C_W = "ldc_w";
	
	/**
	 * The mnemonic for the instruction lload.
	 */
	public static final String MNEMONIC_L_LOAD = "lload";
	
	/**
	 * The mnemonic for the instruction lload_0.
	 */
	public static final String MNEMONIC_L_LOAD_0 = "lload_0";
	
	/**
	 * The mnemonic for the instruction lload_1.
	 */
	public static final String MNEMONIC_L_LOAD_1 = "lload_1";
	
	/**
	 * The mnemonic for the instruction lload_2.
	 */
	public static final String MNEMONIC_L_LOAD_2 = "lload_2";
	
	/**
	 * The mnemonic for the instruction lload_3.
	 */
	public static final String MNEMONIC_L_LOAD_3 = "lload_3";
	
	/**
	 * The mnemonic for the instruction lmul.
	 */
	public static final String MNEMONIC_L_MUL = "lmul";
	
	/**
	 * The mnemonic for the instruction lneg.
	 */
	public static final String MNEMONIC_L_NEG = "lneg";
	
	/**
	 * The mnemonic for the instruction lor.
	 */
	public static final String MNEMONIC_L_OR = "lor";
	
	/**
	 * The mnemonic for the instruction lrem.
	 */
	public static final String MNEMONIC_L_REM = "lrem";
	
	/**
	 * The mnemonic for the instruction lreturn.
	 */
	public static final String MNEMONIC_L_RETURN = "lreturn";
	
	/**
	 * The mnemonic for the instruction lshl.
	 */
	public static final String MNEMONIC_L_SH_L = "lshl";
	
	/**
	 * The mnemonic for the instruction lshr.
	 */
	public static final String MNEMONIC_L_SH_R = "lshr";
	
	/**
	 * The mnemonic for the instruction lstore.
	 */
	public static final String MNEMONIC_L_STORE = "lstore";
	
	/**
	 * The mnemonic for the instruction lstore_0.
	 */
	public static final String MNEMONIC_L_STORE_0 = "lstore_0";
	
	/**
	 * The mnemonic for the instruction lstore_1.
	 */
	public static final String MNEMONIC_L_STORE_1 = "lstore_1";
	
	/**
	 * The mnemonic for the instruction lstore_2.
	 */
	public static final String MNEMONIC_L_STORE_2 = "lstore_2";
	
	/**
	 * The mnemonic for the instruction lstore_3.
	 */
	public static final String MNEMONIC_L_STORE_3 = "lstore_3";
	
	/**
	 * The mnemonic for the instruction lsub.
	 */
	public static final String MNEMONIC_L_SUB = "lsub";
	
	/**
	 * The mnemonic for the instruction lushr.
	 */
	public static final String MNEMONIC_L_U_SH_R = "lushr";
	
	/**
	 * The mnemonic for the instruction lxor.
	 */
	public static final String MNEMONIC_L_XOR = "lxor";
	
	/**
	 * The mnemonic for the instruction monitorenter.
	 */
	public static final String MNEMONIC_MONITOR_ENTER = "monitorenter";
	
	/**
	 * The mnemonic for the instruction monitorexit.
	 */
	public static final String MNEMONIC_MONITOR_EXIT = "monitorexit";
	
	/**
	 * The mnemonic for the instruction multianewarray.
	 */
	public static final String MNEMONIC_MULTI_A_NEW_ARRAY = "multianewarray";
	
	/**
	 * The mnemonic for the instruction new.
	 */
	public static final String MNEMONIC_NEW = "new";
	
	/**
	 * The mnemonic for the instruction newarray.
	 */
	public static final String MNEMONIC_NEW_ARRAY = "newarray";
	
	/**
	 * The mnemonic for the instruction nop.
	 */
	public static final String MNEMONIC_NOP = "nop";
	
	/**
	 * The mnemonic for the instruction pop.
	 */
	public static final String MNEMONIC_POP = "pop";
	
	/**
	 * The mnemonic for the instruction pop2.
	 */
	public static final String MNEMONIC_POP_2 = "pop2";
	
	/**
	 * The mnemonic for the instruction putfield.
	 */
	public static final String MNEMONIC_PUT_FIELD = "putfield";
	
	/**
	 * The mnemonic for the instruction putstatic.
	 */
	public static final String MNEMONIC_PUT_STATIC = "putstatic";
	
	/**
	 * The mnemonic for the instruction ret.
	 */
	public static final String MNEMONIC_RET = "ret";
	
	/**
	 * The mnemonic for the instruction return.
	 */
	public static final String MNEMONIC_RETURN = "return";
	
	/**
	 * The mnemonic for the instruction swap.
	 */
	public static final String MNEMONIC_SWAP = "swap";
	
	/**
	 * The mnemonic for the instruction saload.
	 */
	public static final String MNEMONIC_S_A_LOAD = "saload";
	
	/**
	 * The mnemonic for the instruction sastore.
	 */
	public static final String MNEMONIC_S_A_STORE = "sastore";
	
	/**
	 * The mnemonic for the instruction sipush.
	 */
	public static final String MNEMONIC_S_I_PUSH = "sipush";
	
	/**
	 * The mnemonic for the instruction tableswitch.
	 */
	public static final String MNEMONIC_TABLE_SWITCH = "tableswitch";
	
	/**
	 * The mnemonic for the instruction wide.
	 */
	public static final String MNEMONIC_WIDE = "wide";
	
	/**
	 * The opcode for the instruction arraylength.
	 */
	public static final int OPCODE_ARRAY_LENGTH = 0xBE;
	
	/**
	 * The opcode for the instruction aaload.
	 */
	public static final int OPCODE_A_A_LOAD = 0x32;
	
	/**
	 * The opcode for the instruction aastore.
	 */
	public static final int OPCODE_A_A_STORE = 0x53;
	
	/**
	 * The opcode for the instruction aconst_null.
	 */
	public static final int OPCODE_A_CONST_NULL = 0x01;
	
	/**
	 * The opcode for the instruction aload.
	 */
	public static final int OPCODE_A_LOAD = 0x19;
	
	/**
	 * The opcode for the instruction aload_0.
	 */
	public static final int OPCODE_A_LOAD_0 = 0x2A;
	
	/**
	 * The opcode for the instruction aload_1.
	 */
	public static final int OPCODE_A_LOAD_1 = 0x2B;
	
	/**
	 * The opcode for the instruction aload_2.
	 */
	public static final int OPCODE_A_LOAD_2 = 0x2C;
	
	/**
	 * The opcode for the instruction aload_3.
	 */
	public static final int OPCODE_A_LOAD_3 = 0x2D;
	
	/**
	 * The opcode for the instruction anewarray.
	 */
	public static final int OPCODE_A_NEW_ARRAY = 0xBD;
	
	/**
	 * The opcode for the instruction areturn.
	 */
	public static final int OPCODE_A_RETURN = 0xB0;
	
	/**
	 * The opcode for the instruction astore.
	 */
	public static final int OPCODE_A_STORE = 0x3A;
	
	/**
	 * The opcode for the instruction astore_0.
	 */
	public static final int OPCODE_A_STORE_0 = 0x4B;
	
	/**
	 * The opcode for the instruction astore_1.
	 */
	public static final int OPCODE_A_STORE_1 = 0x4C;
	
	/**
	 * The opcode for the instruction astore_2.
	 */
	public static final int OPCODE_A_STORE_2 = 0x4D;
	
	/**
	 * The opcode for the instruction astore_3.
	 */
	public static final int OPCODE_A_STORE_3 = 0x4E;
	
	/**
	 * The opcode for the instruction athrow.
	 */
	public static final int OPCODE_A_THROW = 0xBF;
	
	/**
	 * The opcode for the instruction breakpoint.
	 */
	public static final int OPCODE_BREAK_POINT = 0xCA;
	
	/**
	 * The opcode for the instruction baload.
	 */
	public static final int OPCODE_B_A_LOAD = 0x33;
	
	/**
	 * The opcode for the instruction bastore.
	 */
	public static final int OPCODE_B_A_STORE = 0x54;
	
	/**
	 * The opcode for the instruction bipush.
	 */
	public static final int OPCODE_B_I_PUSH = 0x10;
	
	/**
	 * The opcode for the instruction checkcast.
	 */
	public static final int OPCODE_CHECK_CAST = 0xC0;
	
	/**
	 * The opcode for the instruction caload.
	 */
	public static final int OPCODE_C_A_LOAD = 0x34;
	
	/**
	 * The opcode for the instruction castore.
	 */
	public static final int OPCODE_C_A_STORE = 0x55;
	
	/**
	 * The opcode for the instruction dup.
	 */
	public static final int OPCODE_DUP = 0x59;
	
	/**
	 * The opcode for the instruction dup2.
	 */
	public static final int OPCODE_DUP_2 = 0x5C;
	
	/**
	 * The opcode for the instruction dup2_x1.
	 */
	public static final int OPCODE_DUP_2_X_1 = 0x5D;
	
	/**
	 * The opcode for the instruction dup2_x2.
	 */
	public static final int OPCODE_DUP_2_X_2 = 0x5E;
	
	/**
	 * The opcode for the instruction dup_x1.
	 */
	public static final int OPCODE_DUP_X_1 = 0x5A;
	
	/**
	 * The opcode for the instruction dup_x2.
	 */
	public static final int OPCODE_DUP_X_2 = 0x5B;
	
	/**
	 * The opcode for the instruction d2f.
	 */
	public static final int OPCODE_D_2_F = 0x90;
	
	/**
	 * The opcode for the instruction d2i.
	 */
	public static final int OPCODE_D_2_I = 0x8E;
	
	/**
	 * The opcode for the instruction d2l.
	 */
	public static final int OPCODE_D_2_L = 0x8F;
	
	/**
	 * The opcode for the instruction dadd.
	 */
	public static final int OPCODE_D_ADD = 0x63;
	
	/**
	 * The opcode for the instruction daload.
	 */
	public static final int OPCODE_D_A_LOAD = 0x31;
	
	/**
	 * The opcode for the instruction dastore.
	 */
	public static final int OPCODE_D_A_STORE = 0x52;
	
	/**
	 * The opcode for the instruction dcmpg.
	 */
	public static final int OPCODE_D_CMP_G = 0x98;
	
	/**
	 * The opcode for the instruction dcmpl.
	 */
	public static final int OPCODE_D_CMP_L = 0x97;
	
	/**
	 * The opcode for the instruction dconst_0.
	 */
	public static final int OPCODE_D_CONST_0 = 0x0E;
	
	/**
	 * The opcode for the instruction dconst_1.
	 */
	public static final int OPCODE_D_CONST_1 = 0x0F;
	
	/**
	 * The opcode for the instruction ddiv.
	 */
	public static final int OPCODE_D_DIV = 0x6F;
	
	/**
	 * The opcode for the instruction dload.
	 */
	public static final int OPCODE_D_LOAD = 0x18;
	
	/**
	 * The opcode for the instruction dload_0.
	 */
	public static final int OPCODE_D_LOAD_0 = 0x26;
	
	/**
	 * The opcode for the instruction dload_1.
	 */
	public static final int OPCODE_D_LOAD_1 = 0x27;
	
	/**
	 * The opcode for the instruction dload_2.
	 */
	public static final int OPCODE_D_LOAD_2 = 0x28;
	
	/**
	 * The opcode for the instruction dload_3.
	 */
	public static final int OPCODE_D_LOAD_3 = 0x29;
	
	/**
	 * The opcode for the instruction dmul.
	 */
	public static final int OPCODE_D_MUL = 0x6B;
	
	/**
	 * The opcode for the instruction dneg.
	 */
	public static final int OPCODE_D_NEG = 0x77;
	
	/**
	 * The opcode for the instruction drem.
	 */
	public static final int OPCODE_D_REM = 0x73;
	
	/**
	 * The opcode for the instruction dreturn.
	 */
	public static final int OPCODE_D_RETURN = 0xAF;
	
	/**
	 * The opcode for the instruction dstore.
	 */
	public static final int OPCODE_D_STORE = 0x39;
	
	/**
	 * The opcode for the instruction dstore_0.
	 */
	public static final int OPCODE_D_STORE_0 = 0x47;
	
	/**
	 * The opcode for the instruction dstore_1.
	 */
	public static final int OPCODE_D_STORE_1 = 0x48;
	
	/**
	 * The opcode for the instruction dstore_2.
	 */
	public static final int OPCODE_D_STORE_2 = 0x49;
	
	/**
	 * The opcode for the instruction dstore_3.
	 */
	public static final int OPCODE_D_STORE_3 = 0x4A;
	
	/**
	 * The opcode for the instruction dsub.
	 */
	public static final int OPCODE_D_SUB = 0x67;
	
	/**
	 * The opcode for the instruction f2d.
	 */
	public static final int OPCODE_F_2_D = 0x8D;
	
	/**
	 * The opcode for the instruction f2i.
	 */
	public static final int OPCODE_F_2_I = 0x8B;
	
	/**
	 * The opcode for the instruction f2l.
	 */
	public static final int OPCODE_F_2_L = 0x8C;
	
	/**
	 * The opcode for the instruction dadd.
	 */
	public static final int OPCODE_F_ADD = 0x62;
	
	/**
	 * The opcode for the instruction faload.
	 */
	public static final int OPCODE_F_A_LOAD = 0x30;
	
	/**
	 * The opcode for the instruction fastore.
	 */
	public static final int OPCODE_F_A_STORE = 0x51;
	
	/**
	 * The opcode for the instruction fcmpg.
	 */
	public static final int OPCODE_F_CMP_G = 0x96;
	
	/**
	 * The opcode for the instruction fcmpl.
	 */
	public static final int OPCODE_F_CMP_L = 0x95;
	
	/**
	 * The opcode for the instruction fconst_0.
	 */
	public static final int OPCODE_F_CONST_0 = 0x0B;
	
	/**
	 * The opcode for the instruction fconst_1.
	 */
	public static final int OPCODE_F_CONST_1 = 0x0C;
	
	/**
	 * The opcode for the instruction fconst_2.
	 */
	public static final int OPCODE_F_CONST_2 = 0x0D;
	
	/**
	 * The opcode for the instruction fdiv.
	 */
	public static final int OPCODE_F_DIV = 0x6E;
	
	/**
	 * The opcode for the instruction fload.
	 */
	public static final int OPCODE_F_LOAD = 0x17;
	
	/**
	 * The opcode for the instruction fload_0.
	 */
	public static final int OPCODE_F_LOAD_0 = 0x22;
	
	/**
	 * The opcode for the instruction fload_1.
	 */
	public static final int OPCODE_F_LOAD_1 = 0x23;
	
	/**
	 * The opcode for the instruction fload_2.
	 */
	public static final int OPCODE_F_LOAD_2 = 0x24;
	
	/**
	 * The opcode for the instruction fload_3.
	 */
	public static final int OPCODE_F_LOAD_3 = 0x25;
	
	/**
	 * The opcode for the instruction fmul.
	 */
	public static final int OPCODE_F_MUL = 0x6A;
	
	/**
	 * The opcode for the instruction fneg.
	 */
	public static final int OPCODE_F_NEG = 0x76;
	
	/**
	 * The opcode for the instruction frem.
	 */
	public static final int OPCODE_F_REM = 0x72;
	
	/**
	 * The opcode for the instruction freturn.
	 */
	public static final int OPCODE_F_RETURN = 0xAE;
	
	/**
	 * The opcode for the instruction fstore.
	 */
	public static final int OPCODE_F_STORE = 0x38;
	
	/**
	 * The opcode for the instruction fstore_0.
	 */
	public static final int OPCODE_F_STORE_0 = 0x43;
	
	/**
	 * The opcode for the instruction fstore_1.
	 */
	public static final int OPCODE_F_STORE_1 = 0x44;
	
	/**
	 * The opcode for the instruction fstore_2.
	 */
	public static final int OPCODE_F_STORE_2 = 0x45;
	
	/**
	 * The opcode for the instruction fstore_3.
	 */
	public static final int OPCODE_F_STORE_3 = 0x46;
	
	/**
	 * The opcode for the instruction fsub.
	 */
	public static final int OPCODE_F_SUB = 0x66;
	
	/**
	 * The opcode for the instruction getfield.
	 */
	public static final int OPCODE_GET_FIELD = 0xB4;
	
	/**
	 * The opcode for the instruction getstatic.
	 */
	public static final int OPCODE_GET_STATIC = 0xB2;
	
	/**
	 * The opcode for the instruction goto.
	 */
	public static final int OPCODE_GO_TO = 0xA7;
	
	/**
	 * The opcode for the instruction goto_w.
	 */
	public static final int OPCODE_GO_TO_W = 0xC8;
	
	/**
	 * The opcode for the instruction if_acmpeq.
	 */
	public static final int OPCODE_IF_A_CMP_EQ = 0xA5;
	
	/**
	 * The opcode for the instruction if_acmpne.
	 */
	public static final int OPCODE_IF_A_CMP_N_E = 0xA6;
	
	/**
	 * The opcode for the instruction ifeq.
	 */
	public static final int OPCODE_IF_EQ = 0x99;
	
	/**
	 * The opcode for the instruction ifge.
	 */
	public static final int OPCODE_IF_G_E = 0x9C;
	
	/**
	 * The opcode for the instruction ifgt.
	 */
	public static final int OPCODE_IF_G_T = 0x9D;
	
	/**
	 * The opcode for the instruction if_icmpeq.
	 */
	public static final int OPCODE_IF_I_CMP_EQ = 0x9F;
	
	/**
	 * The opcode for the instruction if_icmpge.
	 */
	public static final int OPCODE_IF_I_CMP_G_E = 0xA2;
	
	/**
	 * The opcode for the instruction if_icmpgt.
	 */
	public static final int OPCODE_IF_I_CMP_G_T = 0xA3;
	
	/**
	 * The opcode for the instruction if_icmple.
	 */
	public static final int OPCODE_IF_I_CMP_L_E = 0xA4;
	
	/**
	 * The opcode for the instruction if_icmplt.
	 */
	public static final int OPCODE_IF_I_CMP_L_T = 0xA1;
	
	/**
	 * The opcode for the instruction if_icmpne.
	 */
	public static final int OPCODE_IF_I_CMP_N_E = 0xA0;
	
	/**
	 * The opcode for the instruction ifle.
	 */
	public static final int OPCODE_IF_L_E = 0x9E;
	
	/**
	 * The opcode for the instruction iflt.
	 */
	public static final int OPCODE_IF_L_T = 0x9B;
	
	/**
	 * The opcode for the instruction ifnonnull.
	 */
	public static final int OPCODE_IF_NON_NULL = 0xC7;
	
	/**
	 * The opcode for the instruction ifnull.
	 */
	public static final int OPCODE_IF_NULL = 0xC6;
	
	/**
	 * The opcode for the instruction ifne.
	 */
	public static final int OPCODE_IF_N_E = 0x9A;
	
	/**
	 * The opcode for the instruction impdep1.
	 */
	public static final int OPCODE_IMP_DEP_1 = 0xFE;
	
	/**
	 * The opcode for the instruction impdep2.
	 */
	public static final int OPCODE_IMP_DEP_2 = 0xFF;
	
	/**
	 * The opcode for the instruction instanceof.
	 */
	public static final int OPCODE_INSTANCE_OF = 0xC1;
	
	/**
	 * The opcode for the instruction invokedynamic.
	 */
	public static final int OPCODE_INVOKE_DYNAMIC = 0xBA;
	
	/**
	 * The opcode for the instruction invokeinterface.
	 */
	public static final int OPCODE_INVOKE_INTERFACE = 0xB9;
	
	/**
	 * The opcode for the instruction invokespecial.
	 */
	public static final int OPCODE_INVOKE_SPECIAL = 0xB7;
	
	/**
	 * The opcode for the instruction invokestatic.
	 */
	public static final int OPCODE_INVOKE_STATIC = 0xB8;
	
	/**
	 * The opcode for the instruction invokevirtual.
	 */
	public static final int OPCODE_INVOKE_VIRTUAL = 0xB6;
	
	/**
	 * The opcode for the instruction i2b.
	 */
	public static final int OPCODE_I_2_B = 0x91;
	
	/**
	 * The opcode for the instruction i2c.
	 */
	public static final int OPCODE_I_2_C = 0x92;
	
	/**
	 * The opcode for the instruction i2d.
	 */
	public static final int OPCODE_I_2_D = 0x87;
	
	/**
	 * The opcode for the instruction i2f.
	 */
	public static final int OPCODE_I_2_F = 0x86;
	
	/**
	 * The opcode for the instruction i2l.
	 */
	public static final int OPCODE_I_2_L = 0x85;
	
	/**
	 * The opcode for the instruction i2s.
	 */
	public static final int OPCODE_I_2_S = 0x93;
	
	/**
	 * The opcode for the instruction iadd.
	 */
	public static final int OPCODE_I_ADD = 0x60;
	
	/**
	 * The opcode for the instruction iand.
	 */
	public static final int OPCODE_I_AND = 0x7E;
	
	/**
	 * The opcode for the instruction iaload.
	 */
	public static final int OPCODE_I_A_LOAD = 0x2E;
	
	/**
	 * The opcode for the instruction iastore.
	 */
	public static final int OPCODE_I_A_STORE = 0x4F;
	
	/**
	 * The opcode for the instruction iconst_0.
	 */
	public static final int OPCODE_I_CONST_0 = 0x03;
	
	/**
	 * The opcode for the instruction iconst_1.
	 */
	public static final int OPCODE_I_CONST_1 = 0x04;
	
	/**
	 * The opcode for the instruction iconst_2.
	 */
	public static final int OPCODE_I_CONST_2 = 0x05;
	
	/**
	 * The opcode for the instruction iconst_3.
	 */
	public static final int OPCODE_I_CONST_3 = 0x06;
	
	/**
	 * The opcode for the instruction iconst_4.
	 */
	public static final int OPCODE_I_CONST_4 = 0x07;
	
	/**
	 * The opcode for the instruction iconst_5.
	 */
	public static final int OPCODE_I_CONST_5 = 0x08;
	
	/**
	 * The opcode for the instruction iconst_m1.
	 */
	public static final int OPCODE_I_CONST_M1 = 0x02;
	
	/**
	 * The opcode for the instruction idiv.
	 */
	public static final int OPCODE_I_DIV = 0x6C;
	
	/**
	 * The opcode for the instruction iinc.
	 */
	public static final int OPCODE_I_INC = 0x84;
	
	/**
	 * The opcode for the instruction iload.
	 */
	public static final int OPCODE_I_LOAD = 0x15;
	
	/**
	 * The opcode for the instruction iload_0.
	 */
	public static final int OPCODE_I_LOAD_0 = 0x1A;
	
	/**
	 * The opcode for the instruction iload_1.
	 */
	public static final int OPCODE_I_LOAD_1 = 0x1B;
	
	/**
	 * The opcode for the instruction iload_2.
	 */
	public static final int OPCODE_I_LOAD_2 = 0x1C;
	
	/**
	 * The opcode for the instruction iload_3.
	 */
	public static final int OPCODE_I_LOAD_3 = 0x1D;
	
	/**
	 * The opcode for the instruction imul.
	 */
	public static final int OPCODE_I_MUL = 0x68;
	
	/**
	 * The opcode for the instruction ineg.
	 */
	public static final int OPCODE_I_NEG = 0x74;
	
	/**
	 * The opcode for the instruction ior.
	 */
	public static final int OPCODE_I_OR = 0x80;
	
	/**
	 * The opcode for the instruction irem.
	 */
	public static final int OPCODE_I_REM = 0x70;
	
	/**
	 * The opcode for the instruction ireturn.
	 */
	public static final int OPCODE_I_RETURN = 0xAC;
	
	/**
	 * The opcode for the instruction ishl.
	 */
	public static final int OPCODE_I_SH_L = 0x78;
	
	/**
	 * The opcode for the instruction ishr.
	 */
	public static final int OPCODE_I_SH_R = 0x7A;
	
	/**
	 * The opcode for the instruction istore.
	 */
	public static final int OPCODE_I_STORE = 0x36;
	
	/**
	 * The opcode for the instruction istore_0.
	 */
	public static final int OPCODE_I_STORE_0 = 0x3B;
	
	/**
	 * The opcode for the instruction istore_1.
	 */
	public static final int OPCODE_I_STORE_1 = 0x3C;
	
	/**
	 * The opcode for the instruction istore_2.
	 */
	public static final int OPCODE_I_STORE_2 = 0x3D;
	
	/**
	 * The opcode for the instruction istore_3.
	 */
	public static final int OPCODE_I_STORE_3 = 0x3E;
	
	/**
	 * The opcode for the instruction isub.
	 */
	public static final int OPCODE_I_SUB = 0x64;
	
	/**
	 * The opcode for the instruction iushr.
	 */
	public static final int OPCODE_I_U_SH_R = 0x7C;
	
	/**
	 * The opcode for the instruction ixor.
	 */
	public static final int OPCODE_I_XOR = 0x82;
	
	/**
	 * The opcode for the instruction jsr.
	 */
	public static final int OPCODE_J_S_R = 0xA8;
	
	/**
	 * The opcode for the instruction jsr_w.
	 */
	public static final int OPCODE_J_S_R_W = 0xC9;
	
	/**
	 * The opcode for the instruction lookupswitch.
	 */
	public static final int OPCODE_LOOKUP_SWITCH = 0xAB;
	
	/**
	 * The opcode for the instruction l2d.
	 */
	public static final int OPCODE_L_2_D = 0x8A;
	
	/**
	 * The opcode for the instruction l2f.
	 */
	public static final int OPCODE_L_2_F = 0x89;
	
	/**
	 * The opcode for the instruction l2i.
	 */
	public static final int OPCODE_L_2_I = 0x88;
	
	/**
	 * The opcode for the instruction ladd.
	 */
	public static final int OPCODE_L_ADD = 0x61;
	
	/**
	 * The opcode for the instruction land.
	 */
	public static final int OPCODE_L_AND = 0x7F;
	
	/**
	 * The opcode for the instruction laload.
	 */
	public static final int OPCODE_L_A_LOAD = 0x2F;
	
	/**
	 * The opcode for the instruction lastore.
	 */
	public static final int OPCODE_L_A_STORE = 0x50;
	
	/**
	 * The opcode for the instruction lcmp.
	 */
	public static final int OPCODE_L_CMP = 0x94;
	
	/**
	 * The opcode for the instruction lconst_0.
	 */
	public static final int OPCODE_L_CONST_0 = 0x09;
	
	/**
	 * The opcode for the instruction lconst_1.
	 */
	public static final int OPCODE_L_CONST_1 = 0x0A;
	
	/**
	 * The opcode for the instruction ldiv.
	 */
	public static final int OPCODE_L_DIV = 0x6D;
	
	/**
	 * The opcode for the instruction ldc.
	 */
	public static final int OPCODE_L_D_C = 0x12;
	
	/**
	 * The opcode for the instruction ldc2_w.
	 */
	public static final int OPCODE_L_D_C_2_W = 0x14;
	
	/**
	 * The opcode for the instruction ldc_w.
	 */
	public static final int OPCODE_L_D_C_W = 0x13;
	
	/**
	 * The opcode for the instruction lload.
	 */
	public static final int OPCODE_L_LOAD = 0x16;
	
	/**
	 * The opcode for the instruction lload_0.
	 */
	public static final int OPCODE_L_LOAD_0 = 0x1E;
	
	/**
	 * The opcode for the instruction lload_1.
	 */
	public static final int OPCODE_L_LOAD_1 = 0x1F;
	
	/**
	 * The opcode for the instruction lload_2.
	 */
	public static final int OPCODE_L_LOAD_2 = 0x20;
	
	/**
	 * The opcode for the instruction lload_3.
	 */
	public static final int OPCODE_L_LOAD_3 = 0x21;
	
	/**
	 * The opcode for the instruction lmul.
	 */
	public static final int OPCODE_L_MUL = 0x69;
	
	/**
	 * The opcode for the instruction lneg.
	 */
	public static final int OPCODE_L_NEG = 0x75;
	
	/**
	 * The opcode for the instruction lor.
	 */
	public static final int OPCODE_L_OR = 0x81;
	
	/**
	 * The opcode for the instruction lrem.
	 */
	public static final int OPCODE_L_REM = 0x71;
	
	/**
	 * The opcode for the instruction lreturn.
	 */
	public static final int OPCODE_L_RETURN = 0xAD;
	
	/**
	 * The opcode for the instruction lshl.
	 */
	public static final int OPCODE_L_SH_L = 0x79;
	
	/**
	 * The opcode for the instruction lshr.
	 */
	public static final int OPCODE_L_SH_R = 0x7B;
	
	/**
	 * The opcode for the instruction lstore.
	 */
	public static final int OPCODE_L_STORE = 0x37;
	
	/**
	 * The opcode for the instruction lstore_0.
	 */
	public static final int OPCODE_L_STORE_0 = 0x3F;
	
	/**
	 * The opcode for the instruction lstore_1.
	 */
	public static final int OPCODE_L_STORE_1 = 0x40;
	
	/**
	 * The opcode for the instruction lstore_2.
	 */
	public static final int OPCODE_L_STORE_2 = 0x41;
	
	/**
	 * The opcode for the instruction lstore_3.
	 */
	public static final int OPCODE_L_STORE_3 = 0x42;
	
	/**
	 * The opcode for the instruction lsub.
	 */
	public static final int OPCODE_L_SUB = 0x65;
	
	/**
	 * The opcode for the instruction lushr.
	 */
	public static final int OPCODE_L_U_SH_R = 0x7D;
	
	/**
	 * The opcode for the instruction lxor.
	 */
	public static final int OPCODE_L_XOR = 0x83;
	
	/**
	 * The opcode for the instruction monitorenter.
	 */
	public static final int OPCODE_MONITOR_ENTER = 0xC2;
	
	/**
	 * The opcode for the instruction monitorexit.
	 */
	public static final int OPCODE_MONITOR_EXIT = 0xC3;
	
	/**
	 * The opcode for the instruction multianewarray.
	 */
	public static final int OPCODE_MULTI_A_NEW_ARRAY = 0xC5;
	
	/**
	 * The opcode for the instruction new.
	 */
	public static final int OPCODE_NEW = 0xBB;
	
	/**
	 * The opcode for the instruction newarray.
	 */
	public static final int OPCODE_NEW_ARRAY = 0xBC;
	
	/**
	 * The opcode for the instruction nop.
	 */
	public static final int OPCODE_NOP = 0x00;
	
	/**
	 * The opcode for the instruction pop.
	 */
	public static final int OPCODE_POP = 0x57;
	
	/**
	 * The opcode for the instruction pop2.
	 */
	public static final int OPCODE_POP_2 = 0x58;
	
	/**
	 * The opcode for the instruction putfield.
	 */
	public static final int OPCODE_PUT_FIELD = 0xB5;
	
	/**
	 * The opcode for the instruction putstatic.
	 */
	public static final int OPCODE_PUT_STATIC = 0xB3;
	
	/**
	 * The opcode for the instruction ret.
	 */
	public static final int OPCODE_RET = 0xA9;
	
	/**
	 * The opcode for the instruction return.
	 */
	public static final int OPCODE_RETURN = 0xB1;
	
	/**
	 * The opcode for the instruction swap.
	 */
	public static final int OPCODE_SWAP = 0x5F;
	
	/**
	 * The opcode for the instruction saload.
	 */
	public static final int OPCODE_S_A_LOAD = 0x35;
	
	/**
	 * The opcode for the instruction sastore.
	 */
	public static final int OPCODE_S_A_STORE = 0x56;
	
	/**
	 * The opcode for the instruction sipush.
	 */
	public static final int OPCODE_S_I_PUSH = 0x11;
	
	/**
	 * The opcode for the instruction tableswitch.
	 */
	public static final int OPCODE_TABLE_SWITCH = 0xAA;
	
	/**
	 * The opcode for the instruction wide.
	 */
	public static final int OPCODE_WIDE = 0xC4;
	
	/**
	 * Represents the instruction arraylength.
	 */
	public static final Instruction ARRAY_LENGTH = valueOf(OPCODE_ARRAY_LENGTH, MNEMONIC_ARRAY_LENGTH);
	
	/**
	 * Represents the instruction aaload.
	 */
	public static final Instruction A_A_LOAD = valueOf(OPCODE_A_A_LOAD, MNEMONIC_A_A_LOAD);
	
	/**
	 * Represents the instruction aastore.
	 */
	public static final Instruction A_A_STORE = valueOf(OPCODE_A_A_STORE, MNEMONIC_A_A_STORE);
	
	/**
	 * Represents the instruction aconst_null.
	 */
	public static final Instruction A_CONST_NULL = valueOf(OPCODE_A_CONST_NULL, MNEMONIC_A_CONST_NULL);
	
	/**
	 * Represents the instruction aload_0.
	 */
	public static final Instruction A_LOAD_0 = valueOf(OPCODE_A_LOAD_0, MNEMONIC_A_LOAD_0);
	
	/**
	 * Represents the instruction aload_1.
	 */
	public static final Instruction A_LOAD_1 = valueOf(OPCODE_A_LOAD_1, MNEMONIC_A_LOAD_1);
	
	/**
	 * Represents the instruction aload_2.
	 */
	public static final Instruction A_LOAD_2 = valueOf(OPCODE_A_LOAD_2, MNEMONIC_A_LOAD_2);
	
	/**
	 * Represents the instruction aload_3.
	 */
	public static final Instruction A_LOAD_3 = valueOf(OPCODE_A_LOAD_3, MNEMONIC_A_LOAD_3);
	
	/**
	 * Represents the instruction areturn.
	 */
	public static final Instruction A_RETURN = valueOf(OPCODE_A_RETURN, MNEMONIC_A_RETURN);
	
	/**
	 * Represents the instruction astore_0.
	 */
	public static final Instruction A_STORE_0 = valueOf(OPCODE_A_STORE_0, MNEMONIC_A_STORE_0);
	
	/**
	 * Represents the instruction astore_1.
	 */
	public static final Instruction A_STORE_1 = valueOf(OPCODE_A_STORE_1, MNEMONIC_A_STORE_1);
	
	/**
	 * Represents the instruction astore_2.
	 */
	public static final Instruction A_STORE_2 = valueOf(OPCODE_A_STORE_2, MNEMONIC_A_STORE_2);
	
	/**
	 * Represents the instruction astore_3.
	 */
	public static final Instruction A_STORE_3 = valueOf(OPCODE_A_STORE_3, MNEMONIC_A_STORE_3);
	
	/**
	 * Represents the instruction athrow.
	 */
	public static final Instruction A_THROW = valueOf(OPCODE_A_THROW, MNEMONIC_A_THROW);
	
	/**
	 * Represents the instruction breakpoint.
	 */
	public static final Instruction BREAK_POINT = valueOf(OPCODE_BREAK_POINT, MNEMONIC_BREAK_POINT);
	
	/**
	 * Represents the instruction baload.
	 */
	public static final Instruction B_A_LOAD = valueOf(OPCODE_B_A_LOAD, MNEMONIC_B_A_LOAD);
	
	/**
	 * Represents the instruction bastore.
	 */
	public static final Instruction B_A_STORE = valueOf(OPCODE_B_A_STORE, MNEMONIC_B_A_STORE);
	
	/**
	 * Represents the instruction caload.
	 */
	public static final Instruction C_A_LOAD = valueOf(OPCODE_C_A_LOAD, MNEMONIC_C_A_LOAD);
	
	/**
	 * Represents the instruction castore.
	 */
	public static final Instruction C_A_STORE = valueOf(OPCODE_C_A_STORE, MNEMONIC_C_A_STORE);
	
	/**
	 * Represents the instruction dup.
	 */
	public static final Instruction DUP = valueOf(OPCODE_DUP, MNEMONIC_DUP);
	
	/**
	 * Represents the instruction dup2.
	 */
	public static final Instruction DUP_2 = valueOf(OPCODE_DUP_2, MNEMONIC_DUP_2);
	
	/**
	 * Represents the instruction dup2_x1.
	 */
	public static final Instruction DUP_2_X_1 = valueOf(OPCODE_DUP_2_X_1, MNEMONIC_DUP_2_X_1);
	
	/**
	 * Represents the instruction dup2_x2.
	 */
	public static final Instruction DUP_2_X_2 = valueOf(OPCODE_DUP_2_X_2, MNEMONIC_DUP_2_X_2);
	
	/**
	 * Represents the instruction dup_x1.
	 */
	public static final Instruction DUP_X_1 = valueOf(OPCODE_DUP_X_1, MNEMONIC_DUP_X_1);
	
	/**
	 * Represents the instruction dup_x2.
	 */
	public static final Instruction DUP_X_2 = valueOf(OPCODE_DUP_X_2, MNEMONIC_DUP_X_2);
	
	/**
	 * Represents the instruction d2f.
	 */
	public static final Instruction D_2_F = valueOf(OPCODE_D_2_F, MNEMONIC_D_2_F);
	
	/**
	 * Represents the instruction d2i.
	 */
	public static final Instruction D_2_I = valueOf(OPCODE_D_2_I, MNEMONIC_D_2_I);
	
	/**
	 * Represents the instruction d2l.
	 */
	public static final Instruction D_2_L = valueOf(OPCODE_D_2_L, MNEMONIC_D_2_L);
	
	/**
	 * Represents the instruction dadd.
	 */
	public static final Instruction D_ADD = valueOf(OPCODE_D_ADD, MNEMONIC_D_ADD);
	
	/**
	 * Represents the instruction daload.
	 */
	public static final Instruction D_A_LOAD = valueOf(OPCODE_D_A_LOAD, MNEMONIC_D_A_LOAD);
	
	/**
	 * Represents the instruction dastore.
	 */
	public static final Instruction D_A_STORE = valueOf(OPCODE_D_A_STORE, MNEMONIC_D_A_STORE);
	
	/**
	 * Represents the instruction dcmpg.
	 */
	public static final Instruction D_CMP_G = valueOf(OPCODE_D_CMP_G, MNEMONIC_D_CMP_G);
	
	/**
	 * Represents the instruction dcmpl.
	 */
	public static final Instruction D_CMP_L = valueOf(OPCODE_D_CMP_L, MNEMONIC_D_CMP_L);
	
	/**
	 * Represents the instruction dconst_0.
	 */
	public static final Instruction D_CONST_0 = valueOf(OPCODE_D_CONST_0, MNEMONIC_D_CONST_0);
	
	/**
	 * Represents the instruction dconst_1.
	 */
	public static final Instruction D_CONST_1 = valueOf(OPCODE_D_CONST_1, MNEMONIC_D_CONST_1);
	
	/**
	 * Represents the instruction ddiv.
	 */
	public static final Instruction D_DIV = valueOf(OPCODE_D_DIV, MNEMONIC_D_DIV);
	
	/**
	 * Represents the instruction dload_0.
	 */
	public static final Instruction D_LOAD_0 = valueOf(OPCODE_D_LOAD_0, MNEMONIC_D_LOAD_0);
	
	/**
	 * Represents the instruction dload_1.
	 */
	public static final Instruction D_LOAD_1 = valueOf(OPCODE_D_LOAD_1, MNEMONIC_D_LOAD_1);
	
	/**
	 * Represents the instruction dload_2.
	 */
	public static final Instruction D_LOAD_2 = valueOf(OPCODE_D_LOAD_2, MNEMONIC_D_LOAD_2);
	
	/**
	 * Represents the instruction dload_3.
	 */
	public static final Instruction D_LOAD_3 = valueOf(OPCODE_D_LOAD_3, MNEMONIC_D_LOAD_3);
	
	/**
	 * Represents the instruction dmul.
	 */
	public static final Instruction D_MUL = valueOf(OPCODE_D_MUL, MNEMONIC_D_MUL);
	
	/**
	 * Represents the instruction dneg.
	 */
	public static final Instruction D_NEG = valueOf(OPCODE_D_NEG, MNEMONIC_D_NEG);
	
	/**
	 * Represents the instruction drem.
	 */
	public static final Instruction D_REM = valueOf(OPCODE_D_REM, MNEMONIC_D_REM);
	
	/**
	 * Represents the instruction dreturn.
	 */
	public static final Instruction D_RETURN = valueOf(OPCODE_D_RETURN, MNEMONIC_D_RETURN);
	
	/**
	 * Represents the instruction dstore_0.
	 */
	public static final Instruction D_STORE_0 = valueOf(OPCODE_D_STORE_0, MNEMONIC_D_STORE_0);
	
	/**
	 * Represents the instruction dstore_1.
	 */
	public static final Instruction D_STORE_1 = valueOf(OPCODE_D_STORE_1, MNEMONIC_D_STORE_1);
	
	/**
	 * Represents the instruction dstore_2.
	 */
	public static final Instruction D_STORE_2 = valueOf(OPCODE_D_STORE_2, MNEMONIC_D_STORE_2);
	
	/**
	 * Represents the instruction dstore_3.
	 */
	public static final Instruction D_STORE_3 = valueOf(OPCODE_D_STORE_3, MNEMONIC_D_STORE_3);
	
	/**
	 * Represents the instruction dsub.
	 */
	public static final Instruction D_SUB = valueOf(OPCODE_D_SUB, MNEMONIC_D_SUB);
	
	/**
	 * Represents the instruction f2d.
	 */
	public static final Instruction F_2_D = valueOf(OPCODE_F_2_D, MNEMONIC_F_2_D);
	
	/**
	 * Represents the instruction f2i.
	 */
	public static final Instruction F_2_I = valueOf(OPCODE_F_2_I, MNEMONIC_F_2_I);
	
	/**
	 * Represents the instruction f2l.
	 */
	public static final Instruction F_2_L = valueOf(OPCODE_F_2_L, MNEMONIC_F_2_L);
	
	/**
	 * Represents the instruction dadd.
	 */
	public static final Instruction F_ADD = valueOf(OPCODE_F_ADD, MNEMONIC_F_ADD);
	
	/**
	 * Represents the instruction faload.
	 */
	public static final Instruction F_A_LOAD = valueOf(OPCODE_F_A_LOAD, MNEMONIC_F_A_LOAD);
	
	/**
	 * Represents the instruction fastore.
	 */
	public static final Instruction F_A_STORE = valueOf(OPCODE_F_A_STORE, MNEMONIC_F_A_STORE);
	
	/**
	 * Represents the instruction fcmpg.
	 */
	public static final Instruction F_CMP_G = valueOf(OPCODE_F_CMP_G, MNEMONIC_F_CMP_G);
	
	/**
	 * Represents the instruction fcmpl.
	 */
	public static final Instruction F_CMP_L = valueOf(OPCODE_F_CMP_L, MNEMONIC_F_CMP_L);
	
	/**
	 * Represents the instruction fconst_0.
	 */
	public static final Instruction F_CONST_0 = valueOf(OPCODE_F_CONST_0, MNEMONIC_F_CONST_0);
	
	/**
	 * Represents the instruction fconst_1.
	 */
	public static final Instruction F_CONST_1 = valueOf(OPCODE_F_CONST_1, MNEMONIC_F_CONST_1);
	
	/**
	 * Represents the instruction fconst_2.
	 */
	public static final Instruction F_CONST_2 = valueOf(OPCODE_F_CONST_2, MNEMONIC_F_CONST_2);
	
	/**
	 * Represents the instruction fdiv.
	 */
	public static final Instruction F_DIV = valueOf(OPCODE_F_DIV, MNEMONIC_F_DIV);
	
	/**
	 * Represents the instruction fload_0.
	 */
	public static final Instruction F_LOAD_0 = valueOf(OPCODE_F_LOAD_0, MNEMONIC_F_LOAD_0);
	
	/**
	 * Represents the instruction fload_1.
	 */
	public static final Instruction F_LOAD_1 = valueOf(OPCODE_F_LOAD_1, MNEMONIC_F_LOAD_1);
	
	/**
	 * Represents the instruction fload_2.
	 */
	public static final Instruction F_LOAD_2 = valueOf(OPCODE_F_LOAD_2, MNEMONIC_F_LOAD_2);
	
	/**
	 * Represents the instruction fload_3.
	 */
	public static final Instruction F_LOAD_3 = valueOf(OPCODE_F_LOAD_3, MNEMONIC_F_LOAD_3);
	
	/**
	 * Represents the instruction fmul.
	 */
	public static final Instruction F_MUL = valueOf(OPCODE_F_MUL, MNEMONIC_F_MUL);
	
	/**
	 * Represents the instruction fneg.
	 */
	public static final Instruction F_NEG = valueOf(OPCODE_F_NEG, MNEMONIC_F_NEG);
	
	/**
	 * Represents the instruction frem.
	 */
	public static final Instruction F_REM = valueOf(OPCODE_F_REM, MNEMONIC_F_REM);
	
	/**
	 * Represents the instruction freturn.
	 */
	public static final Instruction F_RETURN = valueOf(OPCODE_F_RETURN, MNEMONIC_F_RETURN);
	
	/**
	 * Represents the instruction fstore_0.
	 */
	public static final Instruction F_STORE_0 = valueOf(OPCODE_F_STORE_0, MNEMONIC_F_STORE_0);
	
	/**
	 * Represents the instruction fstore_1.
	 */
	public static final Instruction F_STORE_1 = valueOf(OPCODE_F_STORE_1, MNEMONIC_F_STORE_1);
	
	/**
	 * Represents the instruction fstore_2.
	 */
	public static final Instruction F_STORE_2 = valueOf(OPCODE_F_STORE_2, MNEMONIC_F_STORE_2);
	
	/**
	 * Represents the instruction fstore_3.
	 */
	public static final Instruction F_STORE_3 = valueOf(OPCODE_F_STORE_3, MNEMONIC_F_STORE_3);
	
	/**
	 * Represents the instruction fsub.
	 */
	public static final Instruction F_SUB = valueOf(OPCODE_F_SUB, MNEMONIC_F_SUB);
	
	/**
	 * Represents the instruction impdep1.
	 */
	public static final Instruction IMP_DEP_1 = valueOf(OPCODE_IMP_DEP_1, MNEMONIC_IMP_DEP_1);
	
	/**
	 * Represents the instruction impdep2.
	 */
	public static final Instruction IMP_DEP_2 = valueOf(OPCODE_IMP_DEP_2, MNEMONIC_IMP_DEP_2);
	
	/**
	 * Represents the instruction i2b.
	 */
	public static final Instruction I_2_B = valueOf(OPCODE_I_2_B, MNEMONIC_I_2_B);
	
	/**
	 * Represents the instruction i2c.
	 */
	public static final Instruction I_2_C = valueOf(OPCODE_I_2_C, MNEMONIC_I_2_C);
	
	/**
	 * Represents the instruction i2d.
	 */
	public static final Instruction I_2_D = valueOf(OPCODE_I_2_D, MNEMONIC_I_2_D);
	
	/**
	 * Represents the instruction i2f.
	 */
	public static final Instruction I_2_F = valueOf(OPCODE_I_2_F, MNEMONIC_I_2_F);
	
	/**
	 * Represents the instruction i2l.
	 */
	public static final Instruction I_2_L = valueOf(OPCODE_I_2_L, MNEMONIC_I_2_L);
	
	/**
	 * Represents the instruction i2s.
	 */
	public static final Instruction I_2_S = valueOf(OPCODE_I_2_S, MNEMONIC_I_2_S);
	
	/**
	 * Represents the instruction iadd.
	 */
	public static final Instruction I_ADD = valueOf(OPCODE_I_ADD, MNEMONIC_I_ADD);
	
	/**
	 * Represents the instruction iand.
	 */
	public static final Instruction I_AND = valueOf(OPCODE_I_AND, MNEMONIC_I_AND);
	
	/**
	 * Represents the instruction iaload.
	 */
	public static final Instruction I_A_LOAD = valueOf(OPCODE_I_A_LOAD, MNEMONIC_I_A_LOAD);
	
	/**
	 * Represents the instruction iastore.
	 */
	public static final Instruction I_A_STORE = valueOf(OPCODE_I_A_STORE, MNEMONIC_I_A_STORE);
	
	/**
	 * Represents the instruction iconst_0.
	 */
	public static final Instruction I_CONST_0 = valueOf(OPCODE_I_CONST_0, MNEMONIC_I_CONST_0);
	
	/**
	 * Represents the instruction iconst_1.
	 */
	public static final Instruction I_CONST_1 = valueOf(OPCODE_I_CONST_1, MNEMONIC_I_CONST_1);
	
	/**
	 * Represents the instruction iconst_2.
	 */
	public static final Instruction I_CONST_2 = valueOf(OPCODE_I_CONST_2, MNEMONIC_I_CONST_2);
	
	/**
	 * Represents the instruction iconst_3.
	 */
	public static final Instruction I_CONST_3 = valueOf(OPCODE_I_CONST_3, MNEMONIC_I_CONST_3);
	
	/**
	 * Represents the instruction iconst_4.
	 */
	public static final Instruction I_CONST_4 = valueOf(OPCODE_I_CONST_4, MNEMONIC_I_CONST_4);
	
	/**
	 * Represents the instruction iconst_5.
	 */
	public static final Instruction I_CONST_5 = valueOf(OPCODE_I_CONST_5, MNEMONIC_I_CONST_5);
	
	/**
	 * Represents the instruction iconst_m1.
	 */
	public static final Instruction I_CONST_M1 = valueOf(OPCODE_I_CONST_M1, MNEMONIC_I_CONST_M1);
	
	/**
	 * Represents the instruction idiv.
	 */
	public static final Instruction I_DIV = valueOf(OPCODE_I_DIV, MNEMONIC_I_DIV);
	
	/**
	 * Represents the instruction iload_0.
	 */
	public static final Instruction I_LOAD_0 = valueOf(OPCODE_I_LOAD_0, MNEMONIC_I_LOAD_0);
	
	/**
	 * Represents the instruction iload_1.
	 */
	public static final Instruction I_LOAD_1 = valueOf(OPCODE_I_LOAD_1, MNEMONIC_I_LOAD_1);
	
	/**
	 * Represents the instruction iload_2.
	 */
	public static final Instruction I_LOAD_2 = valueOf(OPCODE_I_LOAD_2, MNEMONIC_I_LOAD_2);
	
	/**
	 * Represents the instruction iload_3.
	 */
	public static final Instruction I_LOAD_3 = valueOf(OPCODE_I_LOAD_3, MNEMONIC_I_LOAD_3);
	
	/**
	 * Represents the instruction imul.
	 */
	public static final Instruction I_MUL = valueOf(OPCODE_I_MUL, MNEMONIC_I_MUL);
	
	/**
	 * Represents the instruction ineg.
	 */
	public static final Instruction I_NEG = valueOf(OPCODE_I_NEG, MNEMONIC_I_NEG);
	
	/**
	 * Represents the instruction ior.
	 */
	public static final Instruction I_OR = valueOf(OPCODE_I_OR, MNEMONIC_I_OR);
	
	/**
	 * Represents the instruction irem.
	 */
	public static final Instruction I_REM = valueOf(OPCODE_I_REM, MNEMONIC_I_REM);
	
	/**
	 * Represents the instruction ireturn.
	 */
	public static final Instruction I_RETURN = valueOf(OPCODE_I_RETURN, MNEMONIC_I_RETURN);
	
	/**
	 * Represents the instruction ishl.
	 */
	public static final Instruction I_SH_L = valueOf(OPCODE_I_SH_L, MNEMONIC_I_SH_L);
	
	/**
	 * Represents the instruction ishr.
	 */
	public static final Instruction I_SH_R = valueOf(OPCODE_I_SH_R, MNEMONIC_I_SH_R);
	
	/**
	 * Represents the instruction istore_0.
	 */
	public static final Instruction I_STORE_0 = valueOf(OPCODE_I_STORE_0, MNEMONIC_I_STORE_0);
	
	/**
	 * Represents the instruction istore_1.
	 */
	public static final Instruction I_STORE_1 = valueOf(OPCODE_I_STORE_1, MNEMONIC_I_STORE_1);
	
	/**
	 * Represents the instruction istore_2.
	 */
	public static final Instruction I_STORE_2 = valueOf(OPCODE_I_STORE_2, MNEMONIC_I_STORE_2);
	
	/**
	 * Represents the instruction istore_3.
	 */
	public static final Instruction I_STORE_3 = valueOf(OPCODE_I_STORE_3, MNEMONIC_I_STORE_3);
	
	/**
	 * Represents the instruction isub.
	 */
	public static final Instruction I_SUB = valueOf(OPCODE_I_SUB, MNEMONIC_I_SUB);
	
	/**
	 * Represents the instruction iushr.
	 */
	public static final Instruction I_U_SH_R = valueOf(OPCODE_I_U_SH_R, MNEMONIC_I_U_SH_R);
	
	/**
	 * Represents the instruction ixor.
	 */
	public static final Instruction I_XOR = valueOf(OPCODE_I_XOR, MNEMONIC_I_XOR);
	
	/**
	 * Represents the instruction l2d.
	 */
	public static final Instruction L_2_D = valueOf(OPCODE_L_2_D, MNEMONIC_L_2_D);
	
	/**
	 * Represents the instruction l2f.
	 */
	public static final Instruction L_2_F = valueOf(OPCODE_L_2_F, MNEMONIC_L_2_F);
	
	/**
	 * Represents the instruction l2i.
	 */
	public static final Instruction L_2_I = valueOf(OPCODE_L_2_I, MNEMONIC_L_2_I);
	
	/**
	 * Represents the instruction ladd.
	 */
	public static final Instruction L_ADD = valueOf(OPCODE_L_ADD, MNEMONIC_L_ADD);
	
	/**
	 * Represents the instruction land.
	 */
	public static final Instruction L_AND = valueOf(OPCODE_L_AND, MNEMONIC_L_AND);
	
	/**
	 * Represents the instruction laload.
	 */
	public static final Instruction L_A_LOAD = valueOf(OPCODE_L_A_LOAD, MNEMONIC_L_A_LOAD);
	
	/**
	 * Represents the instruction lastore.
	 */
	public static final Instruction L_A_STORE = valueOf(OPCODE_L_A_STORE, MNEMONIC_L_A_STORE);
	
	/**
	 * Represents the instruction lcmp.
	 */
	public static final Instruction L_CMP = valueOf(OPCODE_L_CMP, MNEMONIC_L_CMP);
	
	/**
	 * Represents the instruction lconst_0.
	 */
	public static final Instruction L_CONST_0 = valueOf(OPCODE_L_CONST_0, MNEMONIC_L_CONST_0);
	
	/**
	 * Represents the instruction lconst_1.
	 */
	public static final Instruction L_CONST_1 = valueOf(OPCODE_L_CONST_1, MNEMONIC_L_CONST_1);
	
	/**
	 * Represents the instruction ldiv.
	 */
	public static final Instruction L_DIV = valueOf(OPCODE_L_DIV, MNEMONIC_L_DIV);
	
	/**
	 * Represents the instruction lload_0.
	 */
	public static final Instruction L_LOAD_0 = valueOf(OPCODE_L_LOAD_0, MNEMONIC_L_LOAD_0);
	
	/**
	 * Represents the instruction lload_1.
	 */
	public static final Instruction L_LOAD_1 = valueOf(OPCODE_L_LOAD_1, MNEMONIC_L_LOAD_1);
	
	/**
	 * Represents the instruction lload_2.
	 */
	public static final Instruction L_LOAD_2 = valueOf(OPCODE_L_LOAD_2, MNEMONIC_L_LOAD_2);
	
	/**
	 * Represents the instruction lload_3.
	 */
	public static final Instruction L_LOAD_3 = valueOf(OPCODE_L_LOAD_3, MNEMONIC_L_LOAD_3);
	
	/**
	 * Represents the instruction lmul.
	 */
	public static final Instruction L_MUL = valueOf(OPCODE_L_MUL, MNEMONIC_L_MUL);
	
	/**
	 * Represents the instruction lneg.
	 */
	public static final Instruction L_NEG = valueOf(OPCODE_L_NEG, MNEMONIC_L_NEG);
	
	/**
	 * Represents the instruction lor.
	 */
	public static final Instruction L_OR = valueOf(OPCODE_L_OR, MNEMONIC_L_OR);
	
	/**
	 * Represents the instruction lrem.
	 */
	public static final Instruction L_REM = valueOf(OPCODE_L_REM, MNEMONIC_L_REM);
	
	/**
	 * Represents the instruction lreturn.
	 */
	public static final Instruction L_RETURN = valueOf(OPCODE_L_RETURN, MNEMONIC_L_RETURN);
	
	/**
	 * Represents the instruction lshl.
	 */
	public static final Instruction L_SH_L = valueOf(OPCODE_L_SH_L, MNEMONIC_L_SH_L);
	
	/**
	 * Represents the instruction lshr.
	 */
	public static final Instruction L_SH_R = valueOf(OPCODE_L_SH_R, MNEMONIC_L_SH_R);
	
	/**
	 * Represents the instruction lstore_0.
	 */
	public static final Instruction L_STORE_0 = valueOf(OPCODE_L_STORE_0, MNEMONIC_L_STORE_0);
	
	/**
	 * Represents the instruction lstore_1.
	 */
	public static final Instruction L_STORE_1 = valueOf(OPCODE_L_STORE_1, MNEMONIC_L_STORE_1);
	
	/**
	 * Represents the instruction lstore_2.
	 */
	public static final Instruction L_STORE_2 = valueOf(OPCODE_L_STORE_2, MNEMONIC_L_STORE_2);
	
	/**
	 * Represents the instruction lstore_3.
	 */
	public static final Instruction L_STORE_3 = valueOf(OPCODE_L_STORE_3, MNEMONIC_L_STORE_3);
	
	/**
	 * Represents the instruction lsub.
	 */
	public static final Instruction L_SUB = valueOf(OPCODE_L_SUB, MNEMONIC_L_SUB);
	
	/**
	 * Represents the instruction lushr.
	 */
	public static final Instruction L_U_SH_R = valueOf(OPCODE_L_U_SH_R, MNEMONIC_L_U_SH_R);
	
	/**
	 * Represents the instruction lxor.
	 */
	public static final Instruction L_XOR = valueOf(OPCODE_L_XOR, MNEMONIC_L_XOR);
	
	/**
	 * Represents the instruction monitorenter.
	 */
	public static final Instruction MONITOR_ENTER = valueOf(OPCODE_MONITOR_ENTER, MNEMONIC_MONITOR_ENTER);
	
	/**
	 * Represents the instruction monitorexit.
	 */
	public static final Instruction MONITOR_EXIT = valueOf(OPCODE_MONITOR_EXIT, MNEMONIC_MONITOR_EXIT);
	
	/**
	 * Represents the instruction nop.
	 */
	public static final Instruction NOP = valueOf(OPCODE_NOP, MNEMONIC_NOP);
	
	/**
	 * Represents the instruction pop.
	 */
	public static final Instruction POP = valueOf(OPCODE_POP, MNEMONIC_POP);
	
	/**
	 * Represents the instruction pop2.
	 */
	public static final Instruction POP_2 = valueOf(OPCODE_POP_2, MNEMONIC_POP_2);
	
	/**
	 * Represents the instruction return.
	 */
	public static final Instruction RETURN = valueOf(OPCODE_RETURN, MNEMONIC_RETURN);
	
	/**
	 * Represents the instruction swap.
	 */
	public static final Instruction SWAP = valueOf(OPCODE_SWAP, MNEMONIC_SWAP);
	
	/**
	 * Represents the instruction saload.
	 */
	public static final Instruction S_A_LOAD = valueOf(OPCODE_S_A_LOAD, MNEMONIC_S_A_LOAD);
	
	/**
	 * Represents the instruction sastore.
	 */
	public static final Instruction S_A_STORE = valueOf(OPCODE_S_A_STORE, MNEMONIC_S_A_STORE);
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private final String mnemonic;
	private final int opcode;
	private final int padding;
	private final int[] operands;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Instruction(final int opcode, final String mnemonic, final int... operands) {
		this.opcode = opcode;
		this.mnemonic = mnemonic;
		this.operands = operands;
		this.padding = 0;
	}
	
	private Instruction(final int padding, final int opcode, final String mnemonic, final int... operands) {
		this.padding = padding;
		this.opcode = opcode;
		this.mnemonic = mnemonic;
		this.operands = operands;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns a copy of this {@code Instruction} instance.
	 * 
	 * @return a copy of this {@code Instruction} instance
	 */
	public Instruction copy() {
		return new Instruction(this.padding, this.opcode, this.mnemonic, this.operands.clone());
	}
	
	/**
	 * Returns a {@code String} with the mnemonic for this {@code Instruction} instance.
	 * 
	 * @return a {@code String} with the mnemonic for this {@code Instruction} instance
	 */
	public String getMnemonic() {
		return this.mnemonic;
	}
	
	/**
	 * Returns a {@code String} representation of this {@code Instruction} instance.
	 * 
	 * @return a {@code String} representation of this {@code Instruction} instance
	 */
	@Override
	public String toString() {
		return String.format("Instruction: [Mnemonic=%s], [Opcode=%s], [Operands=%s]", getMnemonic(), Integer.toString(this.opcode), Arrays.toString(this.operands));
	}
	
	/**
	 * Returns {@code true} if, and only if, {@code object} is an instance of {@code Instruction}, and that {@code Instruction} instance is equal to this {@code Instruction} instance, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, {@code object} is an instance of {@code Instruction}, and that {@code Instruction} instance is equal to this {@code Instruction} instance, {@code false} otherwise
	 */
	@Override
	public boolean equals(final Object object) {
		if(object == this) {
			return true;
		} else if(!(object instanceof Instruction)) {
			return false;
		} else if(Instruction.class.cast(object).opcode != this.opcode) {
			return false;
		} else if(!Objects.equals(Instruction.class.cast(object).mnemonic, this.mnemonic)) {
			return false;
		} else if(!Arrays.equals(Instruction.class.cast(object).operands, this.operands)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Instruction} is an {@code aload} instruction, {@code false} otherwise.
	 * <p>
	 * The {@code aload} instructions that are available are the following:
	 * <ul>
	 * <li>{@code aload}</li>
	 * <li>{@code aload_0}</li>
	 * <li>{@code aload_1}</li>
	 * <li>{@code aload_2}</li>
	 * <li>{@code aload_3}</li>
	 * <li>{@code wide aload}</li>
	 * </ul>
	 * 
	 * @return {@code true} if, and only if, this {@code Instruction} is an {@code aload} instruction, {@code false} otherwise
	 */
	public boolean isALoad() {
		switch(getOpcode()) {
			case OPCODE_A_LOAD:
			case OPCODE_A_LOAD_0:
			case OPCODE_A_LOAD_1:
			case OPCODE_A_LOAD_2:
			case OPCODE_A_LOAD_3:
				return true;
			default:
				return isWide() && getWideOpcode() == OPCODE_A_LOAD;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Instruction} is a branch instruction, {@code false} otherwise.
	 * <p>
	 * The branch instructions that are available are the following:
	 * <ul>
	 * <li>{@code goto}</li>
	 * <li>{@code goto_w}</li>
	 * <li>{@code if_acmpeq}</li>
	 * <li>{@code if_acmpne}</li>
	 * <li>{@code if_icmpeq}</li>
	 * <li>{@code if_icmpge}</li>
	 * <li>{@code if_icmpgt}</li>
	 * <li>{@code if_icmple}</li>
	 * <li>{@code if_icmplt}</li>
	 * <li>{@code if_icmpne}</li>
	 * <li>{@code ifeq}</li>
	 * <li>{@code ifge}</li>
	 * <li>{@code ifgt}</li>
	 * <li>{@code ifle}</li>
	 * <li>{@code iflt}</li>
	 * <li>{@code ifne}</li>
	 * <li>{@code ifnonnull}</li>
	 * <li>{@code ifnull}</li>
	 * <li>{@code jsr}</li>
	 * <li>{@code jsr_w}</li>
	 * <li>{@code lookupswitch}</li>
	 * <li>{@code tableswitch}</li>
	 * </ul>
	 * 
	 * @return {@code true} if, and only if, this {@code Instruction} is a branch instruction, {@code false} otherwise
	 */
	public boolean isBranch() {
		switch(getOpcode()) {
			case OPCODE_GO_TO:
			case OPCODE_GO_TO_W:
			case OPCODE_IF_A_CMP_EQ:
			case OPCODE_IF_A_CMP_N_E:
			case OPCODE_IF_EQ:
			case OPCODE_IF_G_E:
			case OPCODE_IF_G_T:
			case OPCODE_IF_I_CMP_EQ:
			case OPCODE_IF_I_CMP_G_E:
			case OPCODE_IF_I_CMP_G_T:
			case OPCODE_IF_I_CMP_L_E:
			case OPCODE_IF_I_CMP_L_T:
			case OPCODE_IF_I_CMP_N_E:
			case OPCODE_IF_L_E:
			case OPCODE_IF_L_T:
			case OPCODE_IF_N_E:
			case OPCODE_IF_NON_NULL:
			case OPCODE_IF_NULL:
			case OPCODE_J_S_R:
			case OPCODE_J_S_R_W:
			case OPCODE_LOOKUP_SWITCH:
			case OPCODE_TABLE_SWITCH:
				return true;
			default:
				return false;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Instruction} is a {@code dload} instruction, {@code false} otherwise.
	 * <p>
	 * The {@code dload} instructions that are available are the following:
	 * <ul>
	 * <li>{@code dload}</li>
	 * <li>{@code dload_0}</li>
	 * <li>{@code dload_1}</li>
	 * <li>{@code dload_2}</li>
	 * <li>{@code dload_3}</li>
	 * <li>{@code wide dload}</li>
	 * </ul>
	 * 
	 * @return {@code true} if, and only if, this {@code Instruction} is a {@code dload} instruction, {@code false} otherwise
	 */
	public boolean isDLoad() {
		switch(getOpcode()) {
			case OPCODE_D_LOAD:
			case OPCODE_D_LOAD_0:
			case OPCODE_D_LOAD_1:
			case OPCODE_D_LOAD_2:
			case OPCODE_D_LOAD_3:
				return true;
			default:
				return isWide() && getWideOpcode() == OPCODE_D_LOAD;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Instruction} is an {@code fload} instruction, {@code false} otherwise.
	 * <p>
	 * The {@code fload} instructions that are available are the following:
	 * <ul>
	 * <li>{@code fload}</li>
	 * <li>{@code fload_0}</li>
	 * <li>{@code fload_1}</li>
	 * <li>{@code fload_2}</li>
	 * <li>{@code fload_3}</li>
	 * <li>{@code wide fload}</li>
	 * </ul>
	 * 
	 * @return {@code true} if, and only if, this {@code Instruction} is an {@code fload} instruction, {@code false} otherwise
	 */
	public boolean isFLoad() {
		switch(getOpcode()) {
			case OPCODE_F_LOAD:
			case OPCODE_F_LOAD_0:
			case OPCODE_F_LOAD_1:
			case OPCODE_F_LOAD_2:
			case OPCODE_F_LOAD_3:
				return true;
			default:
				return isWide() && getWideOpcode() == OPCODE_F_LOAD;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Instruction} is an {@code iload} instruction, {@code false} otherwise.
	 * <p>
	 * The {@code iload} instructions that are available are the following:
	 * <ul>
	 * <li>{@code iload}</li>
	 * <li>{@code iload_0}</li>
	 * <li>{@code iload_1}</li>
	 * <li>{@code iload_2}</li>
	 * <li>{@code iload_3}</li>
	 * <li>{@code wide iload}</li>
	 * </ul>
	 * 
	 * @return {@code true} if, and only if, this {@code Instruction} is an {@code iload} instruction, {@code false} otherwise
	 */
	public boolean isILoad() {
		switch(getOpcode()) {
			case OPCODE_I_LOAD:
			case OPCODE_I_LOAD_0:
			case OPCODE_I_LOAD_1:
			case OPCODE_I_LOAD_2:
			case OPCODE_I_LOAD_3:
				return true;
			default:
				return isWide() && getWideOpcode() == OPCODE_I_LOAD;
		}
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Instruction} is a load instruction, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Instruction} is a load instruction, {@code false} otherwise
	 */
	public boolean isLoad() {
		return isALoad() || isDLoad() || isFLoad() || isILoad();
	}
	
	/**
	 * Returns {@code true} if, and only if, this {@code Instruction} is a wide instruction, {@code false} otherwise.
	 * 
	 * @return {@code true} if, and only if, this {@code Instruction} is a wide instruction, {@code false} otherwise
	 */
	public boolean isWide() {
		return getOpcode() == OPCODE_WIDE;
	}
	
	/**
	 * Returns the index of this {@code Instruction} if it is an {@code aload} instruction.
	 * <p>
	 * If this {@code Instruction} is not an {@code aload} instruction, an {@code IllegalStateException} will be thrown.
	 * <p>
	 * The {@code aload} instructions that are available are the following:
	 * <ul>
	 * <li>{@code aload}</li>
	 * <li>{@code aload_0}</li>
	 * <li>{@code aload_1}</li>
	 * <li>{@code aload_2}</li>
	 * <li>{@code aload_3}</li>
	 * <li>{@code wide aload}</li>
	 * </ul>
	 * 
	 * @return the index of this {@code Instruction} if it is an {@code aload} instruction
	 * @throws IllegalStateException thrown if, and only if, this {@code Instruction} is not an {@code aload} instruction
	 */
	public int getALoadIndex() {
		switch(getOpcode()) {
			case OPCODE_A_LOAD:
				return this.operands[0];
			case OPCODE_A_LOAD_0:
				return 0;
			case OPCODE_A_LOAD_1:
				return 1;
			case OPCODE_A_LOAD_2:
				return 2;
			case OPCODE_A_LOAD_3:
				return 3;
			default:
				if(isWide() && getWideOpcode() == OPCODE_A_LOAD) {
					return (this.operands[1] << 8) | this.operands[2];
				}
				
				throw new IllegalStateException();
		}
	}
	
	/**
	 * Returns the index of this {@code Instruction} if it is a {@code dload} instruction.
	 * <p>
	 * If this {@code Instruction} is not a {@code dload} instruction, an {@code IllegalStateException} will be thrown.
	 * <p>
	 * The {@code dload} instructions that are available are the following:
	 * <ul>
	 * <li>{@code dload}</li>
	 * <li>{@code dload_0}</li>
	 * <li>{@code dload_1}</li>
	 * <li>{@code dload_2}</li>
	 * <li>{@code dload_3}</li>
	 * <li>{@code wide dload}</li>
	 * </ul>
	 * 
	 * @return the index of this {@code Instruction} if it is a {@code dload} instruction
	 * @throws IllegalStateException thrown if, and only if, this {@code Instruction} is not a {@code dload} instruction
	 */
	public int getDLoadIndex() {
		switch(getOpcode()) {
			case OPCODE_D_LOAD:
				return this.operands[0];
			case OPCODE_D_LOAD_0:
				return 0;
			case OPCODE_D_LOAD_1:
				return 1;
			case OPCODE_D_LOAD_2:
				return 2;
			case OPCODE_D_LOAD_3:
				return 3;
			default:
				if(isWide() && getWideOpcode() == OPCODE_D_LOAD) {
					return (this.operands[1] << 8) | this.operands[2];
				}
				
				throw new IllegalStateException();
		}
	}
	
	/**
	 * Returns the index of this {@code Instruction} if it is an {@code fload} instruction.
	 * <p>
	 * If this {@code Instruction} is not an {@code fload} instruction, an {@code IllegalStateException} will be thrown.
	 * <p>
	 * The {@code fload} instructions that are available are the following:
	 * <ul>
	 * <li>{@code fload}</li>
	 * <li>{@code fload_0}</li>
	 * <li>{@code fload_1}</li>
	 * <li>{@code fload_2}</li>
	 * <li>{@code fload_3}</li>
	 * <li>{@code wide fload}</li>
	 * </ul>
	 * 
	 * @return the index of this {@code Instruction} if it is an {@code fload} instruction
	 * @throws IllegalStateException thrown if, and only if, this {@code Instruction} is not an {@code fload} instruction
	 */
	public int getFLoadIndex() {
		switch(getOpcode()) {
			case OPCODE_F_LOAD:
				return this.operands[0];
			case OPCODE_F_LOAD_0:
				return 0;
			case OPCODE_F_LOAD_1:
				return 1;
			case OPCODE_F_LOAD_2:
				return 2;
			case OPCODE_F_LOAD_3:
				return 3;
			default:
				if(isWide() && getWideOpcode() == OPCODE_F_LOAD) {
					return (this.operands[1] << 8) | this.operands[2];
				}
				
				throw new IllegalStateException();
		}
	}
	
	/**
	 * Returns the index of this {@code Instruction} if it is a {@code getfield} instruction.
	 * <p>
	 * If this {@code Instruction} is not a {@code getfield} instruction, an {@code IllegalStateException} will be thrown.
	 * 
	 * @return the index of this {@code Instruction} if it is a {@code getfield} instruction
	 */
	public int getGetFieldIndex() {
		switch(getOpcode()) {
			case OPCODE_GET_FIELD:
				return (this.operands[0] << 8) | this.operands[1];
			default:
				throw new IllegalStateException();
		}
	}
	
	/**
	 * Returns the index of this {@code Instruction} if it is a {@code getstatic} instruction.
	 * <p>
	 * If this {@code Instruction} is not a {@code getstatic} instruction, an {@code IllegalStateException} will be thrown.
	 * 
	 * @return the index of this {@code Instruction} if it is a {@code getstatic} instruction
	 */
	public int getGetStaticIndex() {
		switch(getOpcode()) {
			case OPCODE_GET_STATIC:
				return (this.operands[0] << 8) | this.operands[1];
			default:
				throw new IllegalStateException();
		}
	}
	
	/**
	 * Returns the index of this {@code Instruction} if it is an {@code iload} instruction.
	 * <p>
	 * If this {@code Instruction} is not an {@code iload} instruction, an {@code IllegalStateException} will be thrown.
	 * <p>
	 * The {@code iload} instructions that are available are the following:
	 * <ul>
	 * <li>{@code iload}</li>
	 * <li>{@code iload_0}</li>
	 * <li>{@code iload_1}</li>
	 * <li>{@code iload_2}</li>
	 * <li>{@code iload_3}</li>
	 * <li>{@code wide iload}</li>
	 * </ul>
	 * 
	 * @return the index of this {@code Instruction} if it is an {@code iload} instruction
	 * @throws IllegalStateException thrown if, and only if, this {@code Instruction} is not an {@code iload} instruction
	 */
	public int getILoadIndex() {
		switch(getOpcode()) {
			case OPCODE_I_LOAD:
				return this.operands[0];
			case OPCODE_I_LOAD_0:
				return 0;
			case OPCODE_I_LOAD_1:
				return 1;
			case OPCODE_I_LOAD_2:
				return 2;
			case OPCODE_I_LOAD_3:
				return 3;
			default:
				if(isWide() && getWideOpcode() == OPCODE_I_LOAD) {
					return (this.operands[1] << 8) | this.operands[2];
				}
				
				throw new IllegalStateException();
		}
	}
	
	/**
	 * Returns the index of this {@code Instruction} if it is a load instruction.
	 * <p>
	 * If this {@code Instruction} is not a load instruction, an {@code IllegalStateException} will be thrown.
	 * 
	 * @return the index of this {@code Instruction} if it is a load instruction
	 * @throws IllegalStateException thrown if, and only if, this {@code Instruction} is not a load instruction
	 */
	public int getLoadIndex() {
		if(isALoad()) {
			return getALoadIndex();
		} else if(isDLoad()) {
			return getDLoadIndex();
		} else if(isFLoad()) {
			return getFLoadIndex();
		} else if(isILoad()) {
			return getILoadIndex();
		} else {
			throw new IllegalStateException();
		}
	}
	
	/**
	 * Returns the length of this {@code Instruction} instance.
	 * 
	 * @return the length of this {@code Instruction} instance
	 */
	public int getLength() {
		return 1 + this.operands.length;
	}
	
	/**
	 * Returns the opcode of this {@code Instruction} instance.
	 * 
	 * @return the opcode of this {@code Instruction} instance
	 */
	public int getOpcode() {
		return this.opcode;
	}
	
	/**
	 * Returns the operand at index {@code index} of this {@code Instruction} instance.
	 * <p>
	 * If {@code index} is less than {@code 0}, or greater than or equal to {@code instruction.getOperandCount()}, an {@code IndexOutOfBoundsException} will be thrown.
	 * 
	 * @param index the index of the operand to get
	 * @return the operand at index {@code index} of this {@code Instruction} instance
	 * @throws IndexOutOfBoundsException thrown if, and only if, {@code index} is less than {@code 0}, or greater than or equal to {@code instruction.getOperandCount()}
	 */
	public int getOperand(final int index) {
		return this.operands[index];
	}
	
	/**
	 * Returns the operand count for this {@code Instruction} instance.
	 * 
	 * @return the operand count for this {@code Instruction} instance
	 */
	public int getOperandCount() {
		return this.operands.length;
	}
	
	/**
	 * Returns the opcode that was assigned as an operand to this {@code Instruction} if it is a wide instruction.
	 * <p>
	 * If this {@code Instruction} is not a wide instruction, an {@code IllegalStateException} will be thrown.
	 * 
	 * @return the opcode that was assigned as an operand to this {@code Instruction} if it is a wide instruction
	 * @throws IllegalStateException thrown if, and only if, this {@code Instruction} is not a wide instruction
	 */
	public int getWideOpcode() {
		if(isWide()) {
			return this.operands[0];
		}
		
		throw new IllegalStateException();
	}
	
	/**
	 * Returns a hash-code for this {@code Instruction} instance.
	 * 
	 * @return a hash-code for this {@code Instruction} instance
	 */
	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(this.opcode), this.mnemonic, Integer.valueOf(Arrays.hashCode(this.operands)));
	}
	
	/**
	 * Returns an {@code int} array with the branch offsets for this {@code Instruction}.
	 * <p>
	 * If this {@code Instruction} is not a branch instruction, an empty array will be returned.
	 * 
	 * @param offset the offset of this {@code Instruction}
	 * @return an {@code int} array with the branch offsets for this {@code Instruction}
	 */
	public int[] getBranchOffsets(final int offset) {
		switch(getOpcode()) {
			case OPCODE_GO_TO:
				return new int[] {(short)(offset + ((this.operands[0] << 8) | this.operands[1]))};
			case OPCODE_GO_TO_W:
				return new int[] {(short)(offset + ((this.operands[0] << 24) | (this.operands[1] << 16) | (this.operands[2] << 8) | this.operands[3]))};
			case OPCODE_IF_A_CMP_EQ:
			case OPCODE_IF_A_CMP_N_E:
			case OPCODE_IF_EQ:
			case OPCODE_IF_G_E:
			case OPCODE_IF_G_T:
			case OPCODE_IF_I_CMP_EQ:
			case OPCODE_IF_I_CMP_G_E:
			case OPCODE_IF_I_CMP_G_T:
			case OPCODE_IF_I_CMP_L_E:
			case OPCODE_IF_I_CMP_L_T:
			case OPCODE_IF_I_CMP_N_E:
			case OPCODE_IF_L_E:
			case OPCODE_IF_L_T:
			case OPCODE_IF_N_E:
			case OPCODE_IF_NON_NULL:
			case OPCODE_IF_NULL:
			case OPCODE_J_S_R:
				return new int[] {(short)(offset + ((this.operands[0] << 8) | this.operands[1]))};
			case OPCODE_J_S_R_W:
				return new int[] {(short)(offset + ((this.operands[0] << 24) | (this.operands[1] << 16) | (this.operands[2] << 8) | this.operands[3]))};
			case OPCODE_LOOKUP_SWITCH: {
				final int offset0 = this.padding;
				final int defaultOffset = (short)(offset + ((this.operands[offset0 + 0] << 24) | (this.operands[offset0 + 1] << 16) | (this.operands[offset0 + 2] << 8) | this.operands[offset0 + 3]));
				final int nPairs = (this.operands[offset0 + 4] << 24) | (this.operands[offset0 + 5] << 16) | (this.operands[offset0 + 6] << 8) | this.operands[offset0 + 7];
				
				final int[] offsets = new int[1 + nPairs];
				
				offsets[0] = defaultOffset;
				
				for(int i = 0, j = 0; i < nPairs; i++, j += 8) {
					offsets[i + 1] = (short)(offset + ((this.operands[offset0 + j + 4] << 24) | (this.operands[offset0 + j + 5] << 16) | (this.operands[offset0 + j + 6] << 8) | this.operands[offset0 + j + 7]));
				}
				
				return offsets;
			}
			case OPCODE_TABLE_SWITCH: {
				final int offset0 = this.padding;
				final int defaultOffset = (short)(offset + ((this.operands[offset0 + 0] << 24) | (this.operands[offset0 + 1] << 16) | (this.operands[offset0 + 2] << 8) | this.operands[offset0 + 3]));
				final int low = (this.operands[offset0 + 4] << 24) | (this.operands[offset0 + 5] << 16) | (this.operands[offset0 + 6] << 8) | this.operands[offset0 + 7];
				final int high = (this.operands[offset0 + 8] << 24) | (this.operands[offset0 + 9] << 16) | (this.operands[offset0 + 10] << 8) | this.operands[offset0 + 11];
				final int jumpOffsets = high - low + 1;
				
				final int[] offsets = new int[1 + jumpOffsets];
				
				offsets[0] = defaultOffset;
				
				for(int i = 0, j = 0; i < jumpOffsets; i++, j += 4) {
					offsets[i + 1] = (short)(offset + ((this.operands[offset0 + j + 0] << 24) | (this.operands[offset0 + j + 1] << 16) | (this.operands[offset0 + j + 2] << 8) | this.operands[offset0 + j + 3]));
				}
				
				return offsets;
			}
			default:
				return new int[0];
		}
	}
	
	/**
	 * Returns the operands of this {@code Instruction} instance.
	 * <p>
	 * If this {@code Instruction} instance does not have any operands, an empty array will be returned, not {@code null}.
	 * 
	 * @return the operands of this {@code Instruction} instance
	 */
	public int[] getOperands() {
		return this.operands.clone();
	}
	
	/**
	 * Writes this {@code Instruction} to {@code dataOutput}.
	 * <p>
	 * If {@code dataOutput} is an {@code OutputStream} (or any other type of stream), this method will not close it.
	 * <p>
	 * If {@code dataOutput} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If an I/O-error occurs, an {@code UncheckedIOException} will be thrown.
	 * 
	 * @param dataOutput the {@code DataOutput} to write to
	 * @throws NullPointerException thrown if, and only if, {@code dataOutput} is {@code null}
	 * @throws UncheckedIOException thrown if, and only if, an I/O-error occurs
	 */
	public void write(final DataOutput dataOutput) {
		try {
			dataOutput.writeByte(this.opcode);
			
			for(final int operand : this.operands) {
				dataOutput.writeByte(operand);
			}
		} catch(final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Returns an {@code Instruction} denoting aload.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param index an index between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting aload
	 * @throws IllegalArgumentException thrown if, and only if, {@code index} is less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getALoad(final int index) {
		return valueOf(OPCODE_A_LOAD, MNEMONIC_A_LOAD, ParameterArguments.requireRange(index, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting anewarray.
	 * <p>
	 * If either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param indexByte1 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param indexByte2 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting anewarray
	 * @throws IllegalArgumentException thrown if, and only if, either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getANewArray(final int indexByte1, final int indexByte2) {
		return valueOf(OPCODE_A_NEW_ARRAY, MNEMONIC_A_NEW_ARRAY, ParameterArguments.requireRange(indexByte1, 0, 255), ParameterArguments.requireRange(indexByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting astore.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param index an index between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting astore
	 * @throws IllegalArgumentException thrown if, and only if, {@code index} is less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getAStore(final int index) {
		return valueOf(OPCODE_A_STORE, MNEMONIC_A_STORE, ParameterArguments.requireRange(index, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting bipush.
	 * <p>
	 * If {@code value} is less than {@code Byte.MIN_VALUE} or greater than {@code Byte.MAX_VALUE}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param value a value between {@code Byte.MIN_VALUE} (inclusive) and {@code Byte.MAX_VALUE} (inclusive)
	 * @return an {@code Instruction} denoting bipush
	 * @throws IllegalArgumentException thrown if, and only if, {@code value} is less than {@code Byte.MIN_VALUE} or greater than {@code Byte.MAX_VALUE}
	 */
	public static Instruction getBIPush(final int value) {
		return valueOf(OPCODE_B_I_PUSH, MNEMONIC_B_I_PUSH, ParameterArguments.requireRange(value, Byte.MIN_VALUE, Byte.MAX_VALUE));
	}
	
	/**
	 * Returns an {@code Instruction} denoting checkcast.
	 * <p>
	 * If either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param indexByte1 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param indexByte2 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting checkcast
	 * @throws IllegalArgumentException thrown if, and only if, either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getCheckCast(final int indexByte1, final int indexByte2) {
		return valueOf(OPCODE_CHECK_CAST, MNEMONIC_CHECK_CAST, ParameterArguments.requireRange(indexByte1, 0, 255), ParameterArguments.requireRange(indexByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting dload.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param index an index between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting dload
	 * @throws IllegalArgumentException thrown if, and only if, {@code index} is less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getDLoad(final int index) {
		return valueOf(OPCODE_D_LOAD, MNEMONIC_D_LOAD, ParameterArguments.requireRange(index, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting dstore.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param index an index between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting dstore
	 * @throws IllegalArgumentException thrown if, and only if, {@code index} is less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getDStore(final int index) {
		return valueOf(OPCODE_D_STORE, MNEMONIC_D_STORE, ParameterArguments.requireRange(index, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting fload.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param index an index between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting fload
	 * @throws IllegalArgumentException thrown if, and only if, {@code index} is less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getFLoad(final int index) {
		return valueOf(OPCODE_F_LOAD, MNEMONIC_F_LOAD, ParameterArguments.requireRange(index, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting fstore.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param index an index between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting fstore
	 * @throws IllegalArgumentException thrown if, and only if, {@code index} is less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getFStore(final int index) {
		return valueOf(OPCODE_F_STORE, MNEMONIC_F_STORE, ParameterArguments.requireRange(index, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting getfield.
	 * <p>
	 * If either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param indexByte1 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param indexByte2 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting getfield
	 * @throws IllegalArgumentException thrown if, and only if, either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getGetField(final int indexByte1, final int indexByte2) {
		return valueOf(OPCODE_GET_FIELD, MNEMONIC_GET_FIELD, ParameterArguments.requireRange(indexByte1, 0, 255), ParameterArguments.requireRange(indexByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting getstatic.
	 * <p>
	 * If either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param indexByte1 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param indexByte2 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting getstatic
	 * @throws IllegalArgumentException thrown if, and only if, either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getGetStatic(final int indexByte1, final int indexByte2) {
		return valueOf(OPCODE_GET_STATIC, MNEMONIC_GET_STATIC, ParameterArguments.requireRange(indexByte1, 0, 255), ParameterArguments.requireRange(indexByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting goto.
	 * <p>
	 * If either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param branchByte1 a part of the branch index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param branchByte2 a part of the branch index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting goto
	 * @throws IllegalArgumentException thrown if, and only if, either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getGoTo(final int branchByte1, final int branchByte2) {
		return valueOf(OPCODE_GO_TO, MNEMONIC_GO_TO, ParameterArguments.requireRange(branchByte1, 0, 255), ParameterArguments.requireRange(branchByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting goto_w.
	 * <p>
	 * If either {@code branchByte1}, {@code branchByte2}, {@code branchByte3} or {@code branchByte4} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param branchByte1 a part of the branch offset, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param branchByte2 a part of the branch offset, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param branchByte3 a part of the branch offset, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param branchByte4 a part of the branch offset, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting goto_w
	 * @throws IllegalArgumentException thrown if, and only if, either {@code branchByte1}, {@code branchByte2}, {@code branchByte3} or {@code branchByte4} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getGoToW(final int branchByte1, final int branchByte2, final int branchByte3, final int branchByte4) {
		return valueOf(OPCODE_GO_TO_W, MNEMONIC_GO_TO_W, ParameterArguments.requireRange(branchByte1, 0, 255), ParameterArguments.requireRange(branchByte2, 0, 255), ParameterArguments.requireRange(branchByte3, 0, 255), ParameterArguments.requireRange(branchByte4, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting iinc.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than {@code 255}, or {@code constant} is less than {@code Byte.MIN_VALUE} or greater than {@code Byte.MAX_VALUE}, an {@code IllegalArgumentException} will be
	 * thrown.
	 * 
	 * @param index an index between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param constant a constant between {@code Byte.MIN_VALUE} (inclusive) and {@code Byte.MAX_VALUE} (inclusive)
	 * @return an {@code Instruction} denoting iinc
	 * @throws IllegalArgumentException thrown if, and only if, {@code index} is less than {@code 0} or greater than {@code 255}, or {@code constant} is less than {@code Byte.MIN_VALUE} or greater than
	 * {@code Byte.MAX_VALUE}
	 */
	public static Instruction getIInc(final int index, final int constant) {
		return valueOf(OPCODE_I_INC, MNEMONIC_I_INC, ParameterArguments.requireRange(index, 0, 255), ParameterArguments.requireRange(constant, Byte.MIN_VALUE, Byte.MAX_VALUE));
	}
	
	/**
	 * Returns an {@code Instruction} denoting iload.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param index an index between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting iload
	 * @throws IllegalArgumentException thrown if, and only if, {@code index} is less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getILoad(final int index) {
		return valueOf(OPCODE_I_LOAD, MNEMONIC_I_LOAD, ParameterArguments.requireRange(index, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting istore.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param index an index between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting istore
	 * @throws IllegalArgumentException thrown if, and only if, {@code index} is less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getIStore(final int index) {
		return valueOf(OPCODE_I_STORE, MNEMONIC_I_STORE, ParameterArguments.requireRange(index, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting if_acmpeq.
	 * <p>
	 * If either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param branchByte1 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param branchByte2 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting if_acmpeq
	 * @throws IllegalArgumentException thrown if, and only if, either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getIfACmpEq(final int branchByte1, final int branchByte2) {
		return valueOf(OPCODE_IF_A_CMP_EQ, MNEMONIC_IF_A_CMP_EQ, ParameterArguments.requireRange(branchByte1, 0, 255), ParameterArguments.requireRange(branchByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting if_acmpne.
	 * <p>
	 * If either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param branchByte1 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param branchByte2 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting if_acmpne
	 * @throws IllegalArgumentException thrown if, and only if, either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getIfACmpNE(final int branchByte1, final int branchByte2) {
		return valueOf(OPCODE_IF_A_CMP_N_E, MNEMONIC_IF_A_CMP_N_E, ParameterArguments.requireRange(branchByte1, 0, 255), ParameterArguments.requireRange(branchByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting ifeq.
	 * <p>
	 * If either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param branchByte1 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param branchByte2 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting ifeq
	 * @throws IllegalArgumentException thrown if, and only if, either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getIfEq(final int branchByte1, final int branchByte2) {
		return valueOf(OPCODE_IF_EQ, MNEMONIC_IF_EQ, ParameterArguments.requireRange(branchByte1, 0, 255), ParameterArguments.requireRange(branchByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting ifge.
	 * <p>
	 * If either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param branchByte1 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param branchByte2 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting ifge
	 * @throws IllegalArgumentException thrown if, and only if, either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getIfGE(final int branchByte1, final int branchByte2) {
		return valueOf(OPCODE_IF_G_E, MNEMONIC_IF_G_E, ParameterArguments.requireRange(branchByte1, 0, 255), ParameterArguments.requireRange(branchByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting ifgt.
	 * <p>
	 * If either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param branchByte1 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param branchByte2 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting ifgt
	 * @throws IllegalArgumentException thrown if, and only if, either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getIfGT(final int branchByte1, final int branchByte2) {
		return valueOf(OPCODE_IF_G_T, MNEMONIC_IF_G_T, ParameterArguments.requireRange(branchByte1, 0, 255), ParameterArguments.requireRange(branchByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting if_icmpeq.
	 * <p>
	 * If either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param branchByte1 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param branchByte2 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting if_icmpeq
	 * @throws IllegalArgumentException thrown if, and only if, either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getIfICmpEq(final int branchByte1, final int branchByte2) {
		return valueOf(OPCODE_IF_I_CMP_EQ, MNEMONIC_IF_I_CMP_EQ, ParameterArguments.requireRange(branchByte1, 0, 255), ParameterArguments.requireRange(branchByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting if_icmpge.
	 * <p>
	 * If either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param branchByte1 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param branchByte2 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting if_icmpge
	 * @throws IllegalArgumentException thrown if, and only if, either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getIfICmpGE(final int branchByte1, final int branchByte2) {
		return valueOf(OPCODE_IF_I_CMP_G_E, MNEMONIC_IF_I_CMP_G_E, ParameterArguments.requireRange(branchByte1, 0, 255), ParameterArguments.requireRange(branchByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting if_icmpgt.
	 * <p>
	 * If either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param branchByte1 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param branchByte2 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting if_icmpgt
	 * @throws IllegalArgumentException thrown if, and only if, either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getIfICmpGT(final int branchByte1, final int branchByte2) {
		return valueOf(OPCODE_IF_I_CMP_G_T, MNEMONIC_IF_I_CMP_G_T, ParameterArguments.requireRange(branchByte1, 0, 255), ParameterArguments.requireRange(branchByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting if_icmple.
	 * <p>
	 * If either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param branchByte1 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param branchByte2 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting if_imple
	 * @throws IllegalArgumentException thrown if, and only if, either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getIfICmpLE(final int branchByte1, final int branchByte2) {
		return valueOf(OPCODE_IF_I_CMP_L_E, MNEMONIC_IF_I_CMP_L_E, ParameterArguments.requireRange(branchByte1, 0, 255), ParameterArguments.requireRange(branchByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting if_icmplt.
	 * <p>
	 * If either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param branchByte1 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param branchByte2 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting if_icmplt
	 * @throws IllegalArgumentException thrown if, and only if, either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getIfICmpLT(final int branchByte1, final int branchByte2) {
		return valueOf(OPCODE_IF_I_CMP_L_T, MNEMONIC_IF_I_CMP_L_T, ParameterArguments.requireRange(branchByte1, 0, 255), ParameterArguments.requireRange(branchByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting if_icmpne.
	 * <p>
	 * If either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param branchByte1 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param branchByte2 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting if_icmpne
	 * @throws IllegalArgumentException thrown if, and only if, either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getIfICmpNE(final int branchByte1, final int branchByte2) {
		return valueOf(OPCODE_IF_I_CMP_N_E, MNEMONIC_IF_I_CMP_N_E, ParameterArguments.requireRange(branchByte1, 0, 255), ParameterArguments.requireRange(branchByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting ifle.
	 * <p>
	 * If either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param branchByte1 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param branchByte2 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting ifle
	 * @throws IllegalArgumentException thrown if, and only if, either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getIfLE(final int branchByte1, final int branchByte2) {
		return valueOf(OPCODE_IF_L_E, MNEMONIC_IF_L_E, ParameterArguments.requireRange(branchByte1, 0, 255), ParameterArguments.requireRange(branchByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting iflt.
	 * <p>
	 * If either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param branchByte1 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param branchByte2 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting iflt
	 * @throws IllegalArgumentException thrown if, and only if, either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getIfLT(final int branchByte1, final int branchByte2) {
		return valueOf(OPCODE_IF_L_T, MNEMONIC_IF_L_T, ParameterArguments.requireRange(branchByte1, 0, 255), ParameterArguments.requireRange(branchByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting ifne.
	 * <p>
	 * If either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param branchByte1 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param branchByte2 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting ifne
	 * @throws IllegalArgumentException thrown if, and only if, either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getIfNE(final int branchByte1, final int branchByte2) {
		return valueOf(OPCODE_IF_N_E, MNEMONIC_IF_N_E, ParameterArguments.requireRange(branchByte1, 0, 255), ParameterArguments.requireRange(branchByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting ifnonnull.
	 * <p>
	 * If either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param branchByte1 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param branchByte2 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting ifnonnull
	 * @throws IllegalArgumentException thrown if, and only if, either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getIfNonNull(final int branchByte1, final int branchByte2) {
		return valueOf(OPCODE_IF_NON_NULL, MNEMONIC_IF_NON_NULL, ParameterArguments.requireRange(branchByte1, 0, 255), ParameterArguments.requireRange(branchByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting ifnull.
	 * <p>
	 * If either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param branchByte1 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param branchByte2 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting ifnull
	 * @throws IllegalArgumentException thrown if, and only if, either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getIfNull(final int branchByte1, final int branchByte2) {
		return valueOf(OPCODE_IF_NULL, MNEMONIC_IF_NULL, ParameterArguments.requireRange(branchByte1, 0, 255), ParameterArguments.requireRange(branchByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting instanceof.
	 * <p>
	 * If either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param indexByte1 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param indexByte2 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting instanceof
	 * @throws IllegalArgumentException thrown if, and only if, either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getInstanceOf(final int indexByte1, final int indexByte2) {
		return valueOf(OPCODE_INSTANCE_OF, MNEMONIC_INSTANCE_OF, ParameterArguments.requireRange(indexByte1, 0, 255), ParameterArguments.requireRange(indexByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting invokedynamic.
	 * <p>
	 * If either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}, or {@code constant1} or {@code constant2} are anything but {@code 0}, an {@code IllegalArgumentException}
	 * will be thrown.
	 * 
	 * @param indexByte1 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param indexByte2 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param constant1 a value of {@code 0}
	 * @param constant2 a value of {@code 0}
	 * @return an {@code Instruction} denoting invokedynamic
	 * @throws IllegalArgumentException thrown if, and only if, either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}, or {@code constant1} or {@code constant2} are
	 * anything but {@code 0}
	 */
	public static Instruction getInvokeDynamic(final int indexByte1, final int indexByte2, final int constant1, final int constant2) {
		return valueOf(OPCODE_INVOKE_DYNAMIC, MNEMONIC_INVOKE_DYNAMIC, ParameterArguments.requireRange(indexByte1, 0, 255), ParameterArguments.requireRange(indexByte2, 0, 255), ParameterArguments.requireRange(constant1, 0, 0), ParameterArguments.requireRange(constant2, 0, 0));
	}
	
	/**
	 * Returns an {@code Instruction} denoting invokeinterface.
	 * <p>
	 * If either {@code indexByte1}, {@code indexByte2} or {@code count} are less than {@code 0} or greater than {@code 255}, or {@code constant} is anything but {@code 0}, an {@code IllegalArgumentException} will be
	 * thrown.
	 * 
	 * @param indexByte1 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param indexByte2 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param count a value between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param constant a value of {@code 0}
	 * @return an {@code Instruction} denoting invokeinterface
	 * @throws IllegalArgumentException thrown if, and only if, either {@code indexByte1}, {@code indexByte2} or {@code count} are less than {@code 0} or greater than {@code 255}, or {@code constant} is anything but
	 * {@code 0}
	 */
	public static Instruction getInvokeInterface(final int indexByte1, final int indexByte2, final int count, final int constant) {
		return valueOf(OPCODE_INVOKE_INTERFACE, MNEMONIC_INVOKE_INTERFACE, ParameterArguments.requireRange(indexByte1, 0, 255), ParameterArguments.requireRange(indexByte2, 0, 255), ParameterArguments.requireRange(count, 0, 255), ParameterArguments.requireRange(constant, 0, 0));
	}
	
	/**
	 * Returns an {@code Instruction} denoting invokespecial.
	 * <p>
	 * If either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param indexByte1 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param indexByte2 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting invokespecial
	 * @throws IllegalArgumentException thrown if, and only if, either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getInvokeSpecial(final int indexByte1, final int indexByte2) {
		return valueOf(OPCODE_INVOKE_SPECIAL, MNEMONIC_INVOKE_SPECIAL, ParameterArguments.requireRange(indexByte1, 0, 255), ParameterArguments.requireRange(indexByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting invokestatic.
	 * <p>
	 * If either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param indexByte1 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param indexByte2 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting invokestatic
	 * @throws IllegalArgumentException thrown if, and only if, either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getInvokeStatic(final int indexByte1, final int indexByte2) {
		return valueOf(OPCODE_INVOKE_STATIC, MNEMONIC_INVOKE_STATIC, ParameterArguments.requireRange(indexByte1, 0, 255), ParameterArguments.requireRange(indexByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting invokevirtual.
	 * <p>
	 * If either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param indexByte1 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param indexByte2 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting invokevirtual
	 * @throws IllegalArgumentException thrown if, and only if, either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getInvokeVirtual(final int indexByte1, final int indexByte2) {
		return valueOf(OPCODE_INVOKE_VIRTUAL, MNEMONIC_INVOKE_VIRTUAL, ParameterArguments.requireRange(indexByte1, 0, 255), ParameterArguments.requireRange(indexByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting jsr.
	 * <p>
	 * If either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param branchByte1 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param branchByte2 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting jsr
	 * @throws IllegalArgumentException thrown if, and only if, either {@code branchByte1} or {@code branchByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getJSR(final int branchByte1, final int branchByte2) {
		return valueOf(OPCODE_J_S_R, MNEMONIC_J_S_R, ParameterArguments.requireRange(branchByte1, 0, 255), ParameterArguments.requireRange(branchByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting jsr_w.
	 * <p>
	 * If either {@code branchByte1}, {@code branchByte2}, {@code branchByte3} or {@code branchByte4} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param branchByte1 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param branchByte2 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param branchByte3 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param branchByte4 an unsigned byte between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting jsr_w
	 * @throws IllegalArgumentException thrown if, and only if, either {@code branchByte1}, {@code branchByte2}, {@code branchByte3} or {@code branchByte4} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getJSRW(final int branchByte1, final int branchByte2, final int branchByte3, final int branchByte4) {
		return valueOf(OPCODE_J_S_R_W, MNEMONIC_J_S_R_W, ParameterArguments.requireRange(branchByte1, 0, 255), ParameterArguments.requireRange(branchByte2, 0, 255), ParameterArguments.requireRange(branchByte3, 0, 255), ParameterArguments.requireRange(branchByte4, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting ldc.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param index an index between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting ldc
	 * @throws IllegalArgumentException thrown if, and only if, {@code index} is less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getLDC(final int index) {
		return valueOf(OPCODE_L_D_C, MNEMONIC_L_D_C, ParameterArguments.requireRange(index, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting ldc2_w.
	 * <p>
	 * If either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param indexByte1 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param indexByte2 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting ldc2_w
	 * @throws IllegalArgumentException thrown if, and only if, either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getLDC2W(final int indexByte1, final int indexByte2) {
		return valueOf(OPCODE_L_D_C_2_W, MNEMONIC_L_D_C_2_W, ParameterArguments.requireRange(indexByte1, 0, 255), ParameterArguments.requireRange(indexByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting ldc_w.
	 * <p>
	 * If either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param indexByte1 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param indexByte2 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting ldc_w
	 * @throws IllegalArgumentException thrown if, and only if, either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getLDCW(final int indexByte1, final int indexByte2) {
		return valueOf(OPCODE_L_D_C_W, MNEMONIC_L_D_C_W, ParameterArguments.requireRange(indexByte1, 0, 255), ParameterArguments.requireRange(indexByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting lload.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param index an index between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting lload
	 * @throws IllegalArgumentException thrown if, and only if, {@code index} is less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getLLoad(final int index) {
		return valueOf(OPCODE_L_LOAD, MNEMONIC_L_LOAD, ParameterArguments.requireRange(index, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting lstore.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param index an index between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting lstore
	 * @throws IllegalArgumentException thrown if, and only if, {@code index} is less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getLStore(final int index) {
		return valueOf(OPCODE_L_STORE, MNEMONIC_L_STORE, ParameterArguments.requireRange(index, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting lookupswitch.
	 * <p>
	 * If {@code operands} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If either {@code padding} is less than {@code 0} or greater than {@code 3}, or any of the values in {@code operands} are less than {@code Byte.MIN_VALUE} or greater than {@code Byte.MAX_VALUE}, an
	 * {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param padding the padding between {@code 0} (inclusive) and {@code 3} (inclusive)
	 * @param operands an array with values, each one between {@code Byte.MIN_VALUE} (inclusive) and {@code Byte.MAX_VALUE} (inclusive)
	 * @return an {@code Instruction} denoting lookupswitch
	 * @throws IllegalArgumentException thrown if, and only if, either {@code padding} is less than {@code 0} or greater than {@code 3}, or any of the values in {@code operands} are less than {@code Byte.MIN_VALUE} or
	 * greater than {@code Byte.MAX_VALUE}
	 * @throws NullPointerException thrown if, and only if, {@code operands} is {@code null}
	 */
	public static Instruction getLookupSwitch(final int padding, final int... operands) {
		return new Instruction(ParameterArguments.requireRange(padding, 0, 3), OPCODE_LOOKUP_SWITCH, MNEMONIC_LOOKUP_SWITCH, ParameterArguments.requireRange(operands, Byte.MIN_VALUE, Byte.MAX_VALUE));
	}
	
	/**
	 * Returns an {@code Instruction} denoting multianewarray.
	 * <p>
	 * If either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}, or {@code dimensions} are less than {@code 1} or greater than {@code 255}, an
	 * {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param indexByte1 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param indexByte2 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param dimensions the dimensions, between {@code 1} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting multianewarray
	 * @throws IllegalArgumentException thrown if, and only if, either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}, or {@code dimensions} are less than {@code 1} or
	 * greater than {@code 255}
	 */
	public static Instruction getMultiANewArray(final int indexByte1, final int indexByte2, final int dimensions) {
		return valueOf(OPCODE_MULTI_A_NEW_ARRAY, MNEMONIC_MULTI_A_NEW_ARRAY, ParameterArguments.requireRange(indexByte1, 0, 255), ParameterArguments.requireRange(indexByte2, 0, 255), ParameterArguments.requireRange(dimensions, 1, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting new.
	 * <p>
	 * If either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param indexByte1 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param indexByte2 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting new
	 * @throws IllegalArgumentException thrown if, and only if, either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getNew(final int indexByte1, final int indexByte2) {
		return valueOf(OPCODE_NEW, MNEMONIC_NEW, ParameterArguments.requireRange(indexByte1, 0, 255), ParameterArguments.requireRange(indexByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting newarray.
	 * <p>
	 * If {@code aType} is less than {@code 4} or greater than {@code 11}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param aType the type, between {@code 4} (inclusive) and {@code 11} (inclusive)
	 * @return an {@code Instruction} denoting newarray
	 * @throws IllegalArgumentException thrown if, and only if, {@code aType} is less than {@code 4} or greater than {@code 11}
	 */
	public static Instruction getNewArray(final int aType) {
		return valueOf(OPCODE_NEW_ARRAY, MNEMONIC_NEW_ARRAY, ParameterArguments.requireRange(aType, 4, 11));
	}
	
	/**
	 * Returns an {@code Instruction} denoting putfield.
	 * <p>
	 * If either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param indexByte1 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param indexByte2 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting putfield
	 * @throws IllegalArgumentException thrown if, and only if, either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getPutField(final int indexByte1, final int indexByte2) {
		return valueOf(OPCODE_PUT_FIELD, MNEMONIC_PUT_FIELD, ParameterArguments.requireRange(indexByte1, 0, 255), ParameterArguments.requireRange(indexByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting putstatic.
	 * <p>
	 * If either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param indexByte1 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param indexByte2 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting putstatic
	 * @throws IllegalArgumentException thrown if, and only if, either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getPutStatic(final int indexByte1, final int indexByte2) {
		return valueOf(OPCODE_PUT_STATIC, MNEMONIC_PUT_STATIC, ParameterArguments.requireRange(indexByte1, 0, 255), ParameterArguments.requireRange(indexByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting ret.
	 * <p>
	 * If {@code index} is less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param index an index between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting ret
	 * @throws IllegalArgumentException thrown if, and only if, {@code index} is less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getRet(final int index) {
		return valueOf(OPCODE_RET, MNEMONIC_RET, ParameterArguments.requireRange(index, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting sipush.
	 * <p>
	 * If either {@code byte1} or {@code byte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param byte1 a part of the value, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param byte2 a part of the value, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting sipush
	 * @throws IllegalArgumentException thrown if, and only if, either {@code byte1} or {@code byte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getSIPush(final int byte1, final int byte2) {
		return valueOf(OPCODE_S_I_PUSH, MNEMONIC_S_I_PUSH, ParameterArguments.requireRange(byte1, 0, 255), ParameterArguments.requireRange(byte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} denoting tableswitch.
	 * <p>
	 * If {@code operands} is {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * If either {@code padding} is less than {@code 0} or greater than {@code 3}, or any of the values in {@code operands} are less than {@code Byte.MIN_VALUE} or greater than {@code Byte.MAX_VALUE}, an
	 * {@code IllegalArgumentException} will be thrown.
	 * 
	 * @param padding the padding between {@code 0} (inclusive) and {@code 3} (inclusive)
	 * @param operands an array with values, each one between {@code Byte.MIN_VALUE} (inclusive) and {@code Byte.MAX_VALUE} (inclusive)
	 * @return an {@code Instruction} denoting tableswitch
	 * @throws IllegalArgumentException thrown if, and only if, either {@code padding} is less than {@code 0} or greater than {@code 3}, or any of the values in {@code operands} are less than {@code Byte.MIN_VALUE} or
	 * greater than {@code Byte.MAX_VALUE}
	 * @throws NullPointerException thrown if, and only if, {@code operands} is {@code null}
	 */
	public static Instruction getTableSwitch(final int padding, final int... operands) {
		return new Instruction(ParameterArguments.requireRange(padding, 0, 3), OPCODE_TABLE_SWITCH, MNEMONIC_TABLE_SWITCH, ParameterArguments.requireRange(operands, Byte.MIN_VALUE, Byte.MAX_VALUE));
	}
	
	/**
	 * Returns an {@code Instruction} denoting wide.
	 * <p>
	 * If {@code opcode} is invalid, or either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * The valid opcodes for the wide instruction provided by this method is the following:
	 * <ul>
	 * <li>{@link #OPCODE_A_LOAD}</li>
	 * <li>{@link #OPCODE_A_STORE}</li>
	 * <li>{@link #OPCODE_D_LOAD}</li>
	 * <li>{@link #OPCODE_D_STORE}</li>
	 * <li>{@link #OPCODE_F_LOAD}</li>
	 * <li>{@link #OPCODE_F_STORE}</li>
	 * <li>{@link #OPCODE_I_LOAD}</li>
	 * <li>{@link #OPCODE_I_STORE}</li>
	 * <li>{@link #OPCODE_L_LOAD}</li>
	 * <li>{@link #OPCODE_L_STORE}</li>
	 * <li>{@link #OPCODE_RET}</li>
	 * </ul>
	 * 
	 * @param opcode an opcode that the wide instruction handles
	 * @param indexByte1 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param indexByte2 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting wide
	 * @throws IllegalArgumentException thrown if, and only if, {@code opcode} is invalid, or either {@code indexByte1} or {@code indexByte2} are less than {@code 0} or greater than {@code 255}
	 */
	public static Instruction getWide1(final int opcode, final int indexByte1, final int indexByte2) {
		switch(opcode) {
			case OPCODE_A_LOAD:
			case OPCODE_A_STORE:
			case OPCODE_D_LOAD:
			case OPCODE_D_STORE:
			case OPCODE_F_LOAD:
			case OPCODE_F_STORE:
			case OPCODE_I_LOAD:
			case OPCODE_I_STORE:
			case OPCODE_L_LOAD:
			case OPCODE_L_STORE:
			case OPCODE_RET:
				return valueOf(OPCODE_WIDE, MNEMONIC_WIDE, opcode, ParameterArguments.requireRange(indexByte1, 0, 255), ParameterArguments.requireRange(indexByte2, 0, 255));
			default:
				throw new IllegalArgumentException(String.format("Illegal opcode for wide: %s", Integer.toString(opcode)));
		}
	}
	
	/**
	 * Returns an {@code Instruction} denoting wide.
	 * <p>
	 * If {@code opcode} is invalid, or either {@code indexByte1}, {@code indexByte2}, {@code constByte1} or {@code constByte2} are less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException}
	 * will be thrown.
	 * <p>
	 * The valid opcodes for the wide instruction provided by this method is the following:
	 * <ul>
	 * <li>{@link #OPCODE_I_INC}</li>
	 * </ul>
	 * 
	 * @param opcode an opcode that the wide instruction handles
	 * @param indexByte1 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param indexByte2 a part of the index, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param constByte1 a part of the constant, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @param constByte2 a part of the constant, between {@code 0} (inclusive) and {@code 255} (inclusive)
	 * @return an {@code Instruction} denoting wide
	 * @throws IllegalArgumentException thrown if, and only if, {@code opcode} is invalid, or either {@code indexByte1}, {@code indexByte2}, {@code constByte1} or {@code constByte2} are less than {@code 0} or greater
	 * than {@code 255}
	 */
	public static Instruction getWide2(final int opcode, final int indexByte1, final int indexByte2, final int constByte1, final int constByte2) {
		return valueOf(OPCODE_WIDE, MNEMONIC_WIDE, ParameterArguments.requireRange(opcode, OPCODE_I_INC, OPCODE_I_INC), ParameterArguments.requireRange(indexByte1, 0, 255), ParameterArguments.requireRange(indexByte2, 0, 255), ParameterArguments.requireRange(constByte1, 0, 255), ParameterArguments.requireRange(constByte2, 0, 255));
	}
	
	/**
	 * Returns an {@code Instruction} based on an opcode and its optional operands.
	 * <p>
	 * If {@code opcode} is less than {@code 0} or greater than {@code 255}, an {@code IllegalArgumentException} will be thrown.
	 * <p>
	 * If either {@code mnemonic} or {@code operands} are {@code null}, a {@code NullPointerException} will be thrown.
	 * <p>
	 * It's recommended to use the constants provided by this class, or any of the many methods that create {@code Instruction}s with operands.
	 * 
	 * @param opcode the opcode of the {@code Instruction}
	 * @param mnemonic the mnemonic of the {@code Instruction}
	 * @param operands the optional operands of the {@code Instruction}
	 * @return an {@code Instruction} based on an opcode and its optional operands
	 * @throws IllegalArgumentException thrown if, and only if, {@code opcode} is less than {@code 0} or greater than {@code 255}
	 * @throws NullPointerException thrown if, and only if, either {@code mnemonic} or {@code operands} are {@code null}
	 */
	public static Instruction valueOf(final int opcode, final String mnemonic, final int... operands) {
		return new Instruction(ParameterArguments.requireRange(opcode, 0, 255), Objects.requireNonNull(mnemonic, "mnemonic == null"), operands.clone());
	}
}