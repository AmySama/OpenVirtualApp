package com.android.dx.io;

import com.android.dx.io.instructions.InstructionCodec;
import com.android.dx.util.Hex;

public final class OpcodeInfo {
  public static final Info ADD_DOUBLE;
  
  public static final Info ADD_DOUBLE_2ADDR;
  
  public static final Info ADD_FLOAT;
  
  public static final Info ADD_FLOAT_2ADDR;
  
  public static final Info ADD_INT;
  
  public static final Info ADD_INT_2ADDR;
  
  public static final Info ADD_INT_LIT16;
  
  public static final Info ADD_INT_LIT8;
  
  public static final Info ADD_LONG;
  
  public static final Info ADD_LONG_2ADDR;
  
  public static final Info AGET;
  
  public static final Info AGET_BOOLEAN;
  
  public static final Info AGET_BYTE;
  
  public static final Info AGET_CHAR;
  
  public static final Info AGET_OBJECT;
  
  public static final Info AGET_SHORT;
  
  public static final Info AGET_WIDE;
  
  public static final Info AND_INT;
  
  public static final Info AND_INT_2ADDR;
  
  public static final Info AND_INT_LIT16;
  
  public static final Info AND_INT_LIT8;
  
  public static final Info AND_LONG;
  
  public static final Info AND_LONG_2ADDR;
  
  public static final Info APUT;
  
  public static final Info APUT_BOOLEAN;
  
  public static final Info APUT_BYTE;
  
  public static final Info APUT_CHAR;
  
  public static final Info APUT_OBJECT;
  
  public static final Info APUT_SHORT;
  
  public static final Info APUT_WIDE;
  
  public static final Info ARRAY_LENGTH;
  
  public static final Info CHECK_CAST;
  
  public static final Info CMPG_DOUBLE;
  
  public static final Info CMPG_FLOAT;
  
  public static final Info CMPL_DOUBLE;
  
  public static final Info CMPL_FLOAT;
  
  public static final Info CMP_LONG;
  
  public static final Info CONST;
  
  public static final Info CONST_16;
  
  public static final Info CONST_4;
  
  public static final Info CONST_CLASS;
  
  public static final Info CONST_HIGH16;
  
  public static final Info CONST_METHOD_HANDLE;
  
  public static final Info CONST_METHOD_TYPE;
  
  public static final Info CONST_STRING;
  
  public static final Info CONST_STRING_JUMBO;
  
  public static final Info CONST_WIDE;
  
  public static final Info CONST_WIDE_16;
  
  public static final Info CONST_WIDE_32;
  
  public static final Info CONST_WIDE_HIGH16;
  
  public static final Info DIV_DOUBLE;
  
  public static final Info DIV_DOUBLE_2ADDR;
  
  public static final Info DIV_FLOAT;
  
  public static final Info DIV_FLOAT_2ADDR;
  
  public static final Info DIV_INT;
  
  public static final Info DIV_INT_2ADDR;
  
  public static final Info DIV_INT_LIT16;
  
  public static final Info DIV_INT_LIT8;
  
  public static final Info DIV_LONG;
  
  public static final Info DIV_LONG_2ADDR;
  
  public static final Info DOUBLE_TO_FLOAT;
  
  public static final Info DOUBLE_TO_INT;
  
  public static final Info DOUBLE_TO_LONG;
  
  public static final Info FILLED_NEW_ARRAY;
  
  public static final Info FILLED_NEW_ARRAY_RANGE;
  
  public static final Info FILL_ARRAY_DATA;
  
  public static final Info FILL_ARRAY_DATA_PAYLOAD;
  
  public static final Info FLOAT_TO_DOUBLE;
  
  public static final Info FLOAT_TO_INT;
  
  public static final Info FLOAT_TO_LONG;
  
  public static final Info GOTO;
  
  public static final Info GOTO_16;
  
  public static final Info GOTO_32;
  
  public static final Info IF_EQ;
  
  public static final Info IF_EQZ;
  
  public static final Info IF_GE;
  
  public static final Info IF_GEZ;
  
  public static final Info IF_GT;
  
  public static final Info IF_GTZ;
  
  public static final Info IF_LE;
  
  public static final Info IF_LEZ;
  
  public static final Info IF_LT;
  
  public static final Info IF_LTZ;
  
  public static final Info IF_NE;
  
  public static final Info IF_NEZ;
  
  public static final Info IGET;
  
  public static final Info IGET_BOOLEAN;
  
  public static final Info IGET_BYTE;
  
  public static final Info IGET_CHAR;
  
  public static final Info IGET_OBJECT;
  
  public static final Info IGET_SHORT;
  
  public static final Info IGET_WIDE;
  
  private static final Info[] INFO;
  
  public static final Info INSTANCE_OF;
  
  public static final Info INT_TO_BYTE;
  
  public static final Info INT_TO_CHAR;
  
  public static final Info INT_TO_DOUBLE;
  
  public static final Info INT_TO_FLOAT;
  
  public static final Info INT_TO_LONG;
  
  public static final Info INT_TO_SHORT;
  
  public static final Info INVOKE_CUSTOM;
  
  public static final Info INVOKE_CUSTOM_RANGE;
  
  public static final Info INVOKE_DIRECT;
  
  public static final Info INVOKE_DIRECT_RANGE;
  
  public static final Info INVOKE_INTERFACE;
  
  public static final Info INVOKE_INTERFACE_RANGE;
  
  public static final Info INVOKE_POLYMORPHIC;
  
  public static final Info INVOKE_POLYMORPHIC_RANGE;
  
  public static final Info INVOKE_STATIC;
  
  public static final Info INVOKE_STATIC_RANGE;
  
  public static final Info INVOKE_SUPER;
  
  public static final Info INVOKE_SUPER_RANGE;
  
  public static final Info INVOKE_VIRTUAL;
  
  public static final Info INVOKE_VIRTUAL_RANGE;
  
  public static final Info IPUT;
  
  public static final Info IPUT_BOOLEAN;
  
  public static final Info IPUT_BYTE;
  
  public static final Info IPUT_CHAR;
  
  public static final Info IPUT_OBJECT;
  
  public static final Info IPUT_SHORT;
  
  public static final Info IPUT_WIDE;
  
  public static final Info LONG_TO_DOUBLE;
  
  public static final Info LONG_TO_FLOAT;
  
  public static final Info LONG_TO_INT;
  
  public static final Info MONITOR_ENTER;
  
  public static final Info MONITOR_EXIT;
  
  public static final Info MOVE;
  
  public static final Info MOVE_16;
  
  public static final Info MOVE_EXCEPTION;
  
  public static final Info MOVE_FROM16;
  
  public static final Info MOVE_OBJECT;
  
  public static final Info MOVE_OBJECT_16;
  
  public static final Info MOVE_OBJECT_FROM16;
  
  public static final Info MOVE_RESULT;
  
  public static final Info MOVE_RESULT_OBJECT;
  
  public static final Info MOVE_RESULT_WIDE;
  
  public static final Info MOVE_WIDE;
  
  public static final Info MOVE_WIDE_16;
  
  public static final Info MOVE_WIDE_FROM16;
  
  public static final Info MUL_DOUBLE;
  
  public static final Info MUL_DOUBLE_2ADDR;
  
  public static final Info MUL_FLOAT;
  
  public static final Info MUL_FLOAT_2ADDR;
  
  public static final Info MUL_INT;
  
  public static final Info MUL_INT_2ADDR;
  
  public static final Info MUL_INT_LIT16;
  
  public static final Info MUL_INT_LIT8;
  
  public static final Info MUL_LONG;
  
  public static final Info MUL_LONG_2ADDR;
  
  public static final Info NEG_DOUBLE;
  
  public static final Info NEG_FLOAT;
  
  public static final Info NEG_INT;
  
  public static final Info NEG_LONG;
  
  public static final Info NEW_ARRAY;
  
  public static final Info NEW_INSTANCE;
  
  public static final Info NOP;
  
  public static final Info NOT_INT;
  
  public static final Info NOT_LONG;
  
  public static final Info OR_INT;
  
  public static final Info OR_INT_2ADDR;
  
  public static final Info OR_INT_LIT16;
  
  public static final Info OR_INT_LIT8;
  
  public static final Info OR_LONG;
  
  public static final Info OR_LONG_2ADDR;
  
  public static final Info PACKED_SWITCH;
  
  public static final Info PACKED_SWITCH_PAYLOAD;
  
  public static final Info REM_DOUBLE;
  
  public static final Info REM_DOUBLE_2ADDR;
  
  public static final Info REM_FLOAT;
  
  public static final Info REM_FLOAT_2ADDR;
  
  public static final Info REM_INT;
  
  public static final Info REM_INT_2ADDR;
  
  public static final Info REM_INT_LIT16;
  
  public static final Info REM_INT_LIT8;
  
  public static final Info REM_LONG;
  
  public static final Info REM_LONG_2ADDR;
  
  public static final Info RETURN;
  
  public static final Info RETURN_OBJECT;
  
  public static final Info RETURN_VOID;
  
  public static final Info RETURN_WIDE;
  
  public static final Info RSUB_INT;
  
  public static final Info RSUB_INT_LIT8;
  
  public static final Info SGET;
  
  public static final Info SGET_BOOLEAN;
  
  public static final Info SGET_BYTE;
  
  public static final Info SGET_CHAR;
  
  public static final Info SGET_OBJECT;
  
  public static final Info SGET_SHORT;
  
  public static final Info SGET_WIDE;
  
  public static final Info SHL_INT;
  
  public static final Info SHL_INT_2ADDR;
  
  public static final Info SHL_INT_LIT8;
  
  public static final Info SHL_LONG;
  
  public static final Info SHL_LONG_2ADDR;
  
  public static final Info SHR_INT;
  
  public static final Info SHR_INT_2ADDR;
  
  public static final Info SHR_INT_LIT8;
  
  public static final Info SHR_LONG;
  
  public static final Info SHR_LONG_2ADDR;
  
  public static final Info SPARSE_SWITCH;
  
  public static final Info SPARSE_SWITCH_PAYLOAD;
  
  public static final Info SPECIAL_FORMAT = new Info(-1, "<special>", InstructionCodec.FORMAT_00X, IndexType.NONE);
  
  public static final Info SPUT;
  
  public static final Info SPUT_BOOLEAN;
  
  public static final Info SPUT_BYTE;
  
  public static final Info SPUT_CHAR;
  
  public static final Info SPUT_OBJECT;
  
  public static final Info SPUT_SHORT;
  
  public static final Info SPUT_WIDE;
  
  public static final Info SUB_DOUBLE;
  
  public static final Info SUB_DOUBLE_2ADDR;
  
  public static final Info SUB_FLOAT;
  
  public static final Info SUB_FLOAT_2ADDR;
  
  public static final Info SUB_INT;
  
  public static final Info SUB_INT_2ADDR;
  
  public static final Info SUB_LONG;
  
  public static final Info SUB_LONG_2ADDR;
  
  public static final Info THROW;
  
  public static final Info USHR_INT;
  
  public static final Info USHR_INT_2ADDR;
  
  public static final Info USHR_INT_LIT8;
  
  public static final Info USHR_LONG;
  
  public static final Info USHR_LONG_2ADDR;
  
  public static final Info XOR_INT;
  
  public static final Info XOR_INT_2ADDR;
  
  public static final Info XOR_INT_LIT16;
  
  public static final Info XOR_INT_LIT8;
  
  public static final Info XOR_LONG;
  
  public static final Info XOR_LONG_2ADDR;
  
  static {
    PACKED_SWITCH_PAYLOAD = new Info(256, "packed-switch-payload", InstructionCodec.FORMAT_PACKED_SWITCH_PAYLOAD, IndexType.NONE);
    SPARSE_SWITCH_PAYLOAD = new Info(512, "sparse-switch-payload", InstructionCodec.FORMAT_SPARSE_SWITCH_PAYLOAD, IndexType.NONE);
    FILL_ARRAY_DATA_PAYLOAD = new Info(768, "fill-array-data-payload", InstructionCodec.FORMAT_FILL_ARRAY_DATA_PAYLOAD, IndexType.NONE);
    NOP = new Info(0, "nop", InstructionCodec.FORMAT_10X, IndexType.NONE);
    MOVE = new Info(1, "move", InstructionCodec.FORMAT_12X, IndexType.NONE);
    MOVE_FROM16 = new Info(2, "move/from16", InstructionCodec.FORMAT_22X, IndexType.NONE);
    MOVE_16 = new Info(3, "move/16", InstructionCodec.FORMAT_32X, IndexType.NONE);
    MOVE_WIDE = new Info(4, "move-wide", InstructionCodec.FORMAT_12X, IndexType.NONE);
    MOVE_WIDE_FROM16 = new Info(5, "move-wide/from16", InstructionCodec.FORMAT_22X, IndexType.NONE);
    MOVE_WIDE_16 = new Info(6, "move-wide/16", InstructionCodec.FORMAT_32X, IndexType.NONE);
    MOVE_OBJECT = new Info(7, "move-object", InstructionCodec.FORMAT_12X, IndexType.NONE);
    MOVE_OBJECT_FROM16 = new Info(8, "move-object/from16", InstructionCodec.FORMAT_22X, IndexType.NONE);
    MOVE_OBJECT_16 = new Info(9, "move-object/16", InstructionCodec.FORMAT_32X, IndexType.NONE);
    MOVE_RESULT = new Info(10, "move-result", InstructionCodec.FORMAT_11X, IndexType.NONE);
    MOVE_RESULT_WIDE = new Info(11, "move-result-wide", InstructionCodec.FORMAT_11X, IndexType.NONE);
    MOVE_RESULT_OBJECT = new Info(12, "move-result-object", InstructionCodec.FORMAT_11X, IndexType.NONE);
    MOVE_EXCEPTION = new Info(13, "move-exception", InstructionCodec.FORMAT_11X, IndexType.NONE);
    RETURN_VOID = new Info(14, "return-void", InstructionCodec.FORMAT_10X, IndexType.NONE);
    RETURN = new Info(15, "return", InstructionCodec.FORMAT_11X, IndexType.NONE);
    RETURN_WIDE = new Info(16, "return-wide", InstructionCodec.FORMAT_11X, IndexType.NONE);
    RETURN_OBJECT = new Info(17, "return-object", InstructionCodec.FORMAT_11X, IndexType.NONE);
    CONST_4 = new Info(18, "const/4", InstructionCodec.FORMAT_11N, IndexType.NONE);
    CONST_16 = new Info(19, "const/16", InstructionCodec.FORMAT_21S, IndexType.NONE);
    CONST = new Info(20, "const", InstructionCodec.FORMAT_31I, IndexType.NONE);
    CONST_HIGH16 = new Info(21, "const/high16", InstructionCodec.FORMAT_21H, IndexType.NONE);
    CONST_WIDE_16 = new Info(22, "const-wide/16", InstructionCodec.FORMAT_21S, IndexType.NONE);
    CONST_WIDE_32 = new Info(23, "const-wide/32", InstructionCodec.FORMAT_31I, IndexType.NONE);
    CONST_WIDE = new Info(24, "const-wide", InstructionCodec.FORMAT_51L, IndexType.NONE);
    CONST_WIDE_HIGH16 = new Info(25, "const-wide/high16", InstructionCodec.FORMAT_21H, IndexType.NONE);
    CONST_STRING = new Info(26, "const-string", InstructionCodec.FORMAT_21C, IndexType.STRING_REF);
    CONST_STRING_JUMBO = new Info(27, "const-string/jumbo", InstructionCodec.FORMAT_31C, IndexType.STRING_REF);
    CONST_CLASS = new Info(28, "const-class", InstructionCodec.FORMAT_21C, IndexType.TYPE_REF);
    MONITOR_ENTER = new Info(29, "monitor-enter", InstructionCodec.FORMAT_11X, IndexType.NONE);
    MONITOR_EXIT = new Info(30, "monitor-exit", InstructionCodec.FORMAT_11X, IndexType.NONE);
    CHECK_CAST = new Info(31, "check-cast", InstructionCodec.FORMAT_21C, IndexType.TYPE_REF);
    INSTANCE_OF = new Info(32, "instance-of", InstructionCodec.FORMAT_22C, IndexType.TYPE_REF);
    ARRAY_LENGTH = new Info(33, "array-length", InstructionCodec.FORMAT_12X, IndexType.NONE);
    NEW_INSTANCE = new Info(34, "new-instance", InstructionCodec.FORMAT_21C, IndexType.TYPE_REF);
    NEW_ARRAY = new Info(35, "new-array", InstructionCodec.FORMAT_22C, IndexType.TYPE_REF);
    FILLED_NEW_ARRAY = new Info(36, "filled-new-array", InstructionCodec.FORMAT_35C, IndexType.TYPE_REF);
    FILLED_NEW_ARRAY_RANGE = new Info(37, "filled-new-array/range", InstructionCodec.FORMAT_3RC, IndexType.TYPE_REF);
    FILL_ARRAY_DATA = new Info(38, "fill-array-data", InstructionCodec.FORMAT_31T, IndexType.NONE);
    THROW = new Info(39, "throw", InstructionCodec.FORMAT_11X, IndexType.NONE);
    GOTO = new Info(40, "goto", InstructionCodec.FORMAT_10T, IndexType.NONE);
    GOTO_16 = new Info(41, "goto/16", InstructionCodec.FORMAT_20T, IndexType.NONE);
    GOTO_32 = new Info(42, "goto/32", InstructionCodec.FORMAT_30T, IndexType.NONE);
    PACKED_SWITCH = new Info(43, "packed-switch", InstructionCodec.FORMAT_31T, IndexType.NONE);
    SPARSE_SWITCH = new Info(44, "sparse-switch", InstructionCodec.FORMAT_31T, IndexType.NONE);
    CMPL_FLOAT = new Info(45, "cmpl-float", InstructionCodec.FORMAT_23X, IndexType.NONE);
    CMPG_FLOAT = new Info(46, "cmpg-float", InstructionCodec.FORMAT_23X, IndexType.NONE);
    CMPL_DOUBLE = new Info(47, "cmpl-double", InstructionCodec.FORMAT_23X, IndexType.NONE);
    CMPG_DOUBLE = new Info(48, "cmpg-double", InstructionCodec.FORMAT_23X, IndexType.NONE);
    CMP_LONG = new Info(49, "cmp-long", InstructionCodec.FORMAT_23X, IndexType.NONE);
    IF_EQ = new Info(50, "if-eq", InstructionCodec.FORMAT_22T, IndexType.NONE);
    IF_NE = new Info(51, "if-ne", InstructionCodec.FORMAT_22T, IndexType.NONE);
    IF_LT = new Info(52, "if-lt", InstructionCodec.FORMAT_22T, IndexType.NONE);
    IF_GE = new Info(53, "if-ge", InstructionCodec.FORMAT_22T, IndexType.NONE);
    IF_GT = new Info(54, "if-gt", InstructionCodec.FORMAT_22T, IndexType.NONE);
    IF_LE = new Info(55, "if-le", InstructionCodec.FORMAT_22T, IndexType.NONE);
    IF_EQZ = new Info(56, "if-eqz", InstructionCodec.FORMAT_21T, IndexType.NONE);
    IF_NEZ = new Info(57, "if-nez", InstructionCodec.FORMAT_21T, IndexType.NONE);
    IF_LTZ = new Info(58, "if-ltz", InstructionCodec.FORMAT_21T, IndexType.NONE);
    IF_GEZ = new Info(59, "if-gez", InstructionCodec.FORMAT_21T, IndexType.NONE);
    IF_GTZ = new Info(60, "if-gtz", InstructionCodec.FORMAT_21T, IndexType.NONE);
    IF_LEZ = new Info(61, "if-lez", InstructionCodec.FORMAT_21T, IndexType.NONE);
    AGET = new Info(68, "aget", InstructionCodec.FORMAT_23X, IndexType.NONE);
    AGET_WIDE = new Info(69, "aget-wide", InstructionCodec.FORMAT_23X, IndexType.NONE);
    AGET_OBJECT = new Info(70, "aget-object", InstructionCodec.FORMAT_23X, IndexType.NONE);
    AGET_BOOLEAN = new Info(71, "aget-boolean", InstructionCodec.FORMAT_23X, IndexType.NONE);
    AGET_BYTE = new Info(72, "aget-byte", InstructionCodec.FORMAT_23X, IndexType.NONE);
    AGET_CHAR = new Info(73, "aget-char", InstructionCodec.FORMAT_23X, IndexType.NONE);
    AGET_SHORT = new Info(74, "aget-short", InstructionCodec.FORMAT_23X, IndexType.NONE);
    APUT = new Info(75, "aput", InstructionCodec.FORMAT_23X, IndexType.NONE);
    APUT_WIDE = new Info(76, "aput-wide", InstructionCodec.FORMAT_23X, IndexType.NONE);
    APUT_OBJECT = new Info(77, "aput-object", InstructionCodec.FORMAT_23X, IndexType.NONE);
    APUT_BOOLEAN = new Info(78, "aput-boolean", InstructionCodec.FORMAT_23X, IndexType.NONE);
    APUT_BYTE = new Info(79, "aput-byte", InstructionCodec.FORMAT_23X, IndexType.NONE);
    APUT_CHAR = new Info(80, "aput-char", InstructionCodec.FORMAT_23X, IndexType.NONE);
    APUT_SHORT = new Info(81, "aput-short", InstructionCodec.FORMAT_23X, IndexType.NONE);
    IGET = new Info(82, "iget", InstructionCodec.FORMAT_22C, IndexType.FIELD_REF);
    IGET_WIDE = new Info(83, "iget-wide", InstructionCodec.FORMAT_22C, IndexType.FIELD_REF);
    IGET_OBJECT = new Info(84, "iget-object", InstructionCodec.FORMAT_22C, IndexType.FIELD_REF);
    IGET_BOOLEAN = new Info(85, "iget-boolean", InstructionCodec.FORMAT_22C, IndexType.FIELD_REF);
    IGET_BYTE = new Info(86, "iget-byte", InstructionCodec.FORMAT_22C, IndexType.FIELD_REF);
    IGET_CHAR = new Info(87, "iget-char", InstructionCodec.FORMAT_22C, IndexType.FIELD_REF);
    IGET_SHORT = new Info(88, "iget-short", InstructionCodec.FORMAT_22C, IndexType.FIELD_REF);
    IPUT = new Info(89, "iput", InstructionCodec.FORMAT_22C, IndexType.FIELD_REF);
    IPUT_WIDE = new Info(90, "iput-wide", InstructionCodec.FORMAT_22C, IndexType.FIELD_REF);
    IPUT_OBJECT = new Info(91, "iput-object", InstructionCodec.FORMAT_22C, IndexType.FIELD_REF);
    IPUT_BOOLEAN = new Info(92, "iput-boolean", InstructionCodec.FORMAT_22C, IndexType.FIELD_REF);
    IPUT_BYTE = new Info(93, "iput-byte", InstructionCodec.FORMAT_22C, IndexType.FIELD_REF);
    IPUT_CHAR = new Info(94, "iput-char", InstructionCodec.FORMAT_22C, IndexType.FIELD_REF);
    IPUT_SHORT = new Info(95, "iput-short", InstructionCodec.FORMAT_22C, IndexType.FIELD_REF);
    SGET = new Info(96, "sget", InstructionCodec.FORMAT_21C, IndexType.FIELD_REF);
    SGET_WIDE = new Info(97, "sget-wide", InstructionCodec.FORMAT_21C, IndexType.FIELD_REF);
    SGET_OBJECT = new Info(98, "sget-object", InstructionCodec.FORMAT_21C, IndexType.FIELD_REF);
    SGET_BOOLEAN = new Info(99, "sget-boolean", InstructionCodec.FORMAT_21C, IndexType.FIELD_REF);
    SGET_BYTE = new Info(100, "sget-byte", InstructionCodec.FORMAT_21C, IndexType.FIELD_REF);
    SGET_CHAR = new Info(101, "sget-char", InstructionCodec.FORMAT_21C, IndexType.FIELD_REF);
    SGET_SHORT = new Info(102, "sget-short", InstructionCodec.FORMAT_21C, IndexType.FIELD_REF);
    SPUT = new Info(103, "sput", InstructionCodec.FORMAT_21C, IndexType.FIELD_REF);
    SPUT_WIDE = new Info(104, "sput-wide", InstructionCodec.FORMAT_21C, IndexType.FIELD_REF);
    SPUT_OBJECT = new Info(105, "sput-object", InstructionCodec.FORMAT_21C, IndexType.FIELD_REF);
    SPUT_BOOLEAN = new Info(106, "sput-boolean", InstructionCodec.FORMAT_21C, IndexType.FIELD_REF);
    SPUT_BYTE = new Info(107, "sput-byte", InstructionCodec.FORMAT_21C, IndexType.FIELD_REF);
    SPUT_CHAR = new Info(108, "sput-char", InstructionCodec.FORMAT_21C, IndexType.FIELD_REF);
    SPUT_SHORT = new Info(109, "sput-short", InstructionCodec.FORMAT_21C, IndexType.FIELD_REF);
    INVOKE_VIRTUAL = new Info(110, "invoke-virtual", InstructionCodec.FORMAT_35C, IndexType.METHOD_REF);
    INVOKE_SUPER = new Info(111, "invoke-super", InstructionCodec.FORMAT_35C, IndexType.METHOD_REF);
    INVOKE_DIRECT = new Info(112, "invoke-direct", InstructionCodec.FORMAT_35C, IndexType.METHOD_REF);
    INVOKE_STATIC = new Info(113, "invoke-static", InstructionCodec.FORMAT_35C, IndexType.METHOD_REF);
    INVOKE_INTERFACE = new Info(114, "invoke-interface", InstructionCodec.FORMAT_35C, IndexType.METHOD_REF);
    INVOKE_VIRTUAL_RANGE = new Info(116, "invoke-virtual/range", InstructionCodec.FORMAT_3RC, IndexType.METHOD_REF);
    INVOKE_SUPER_RANGE = new Info(117, "invoke-super/range", InstructionCodec.FORMAT_3RC, IndexType.METHOD_REF);
    INVOKE_DIRECT_RANGE = new Info(118, "invoke-direct/range", InstructionCodec.FORMAT_3RC, IndexType.METHOD_REF);
    INVOKE_STATIC_RANGE = new Info(119, "invoke-static/range", InstructionCodec.FORMAT_3RC, IndexType.METHOD_REF);
    INVOKE_INTERFACE_RANGE = new Info(120, "invoke-interface/range", InstructionCodec.FORMAT_3RC, IndexType.METHOD_REF);
    NEG_INT = new Info(123, "neg-int", InstructionCodec.FORMAT_12X, IndexType.NONE);
    NOT_INT = new Info(124, "not-int", InstructionCodec.FORMAT_12X, IndexType.NONE);
    NEG_LONG = new Info(125, "neg-long", InstructionCodec.FORMAT_12X, IndexType.NONE);
    NOT_LONG = new Info(126, "not-long", InstructionCodec.FORMAT_12X, IndexType.NONE);
    NEG_FLOAT = new Info(127, "neg-float", InstructionCodec.FORMAT_12X, IndexType.NONE);
    NEG_DOUBLE = new Info(128, "neg-double", InstructionCodec.FORMAT_12X, IndexType.NONE);
    INT_TO_LONG = new Info(129, "int-to-long", InstructionCodec.FORMAT_12X, IndexType.NONE);
    INT_TO_FLOAT = new Info(130, "int-to-float", InstructionCodec.FORMAT_12X, IndexType.NONE);
    INT_TO_DOUBLE = new Info(131, "int-to-double", InstructionCodec.FORMAT_12X, IndexType.NONE);
    LONG_TO_INT = new Info(132, "long-to-int", InstructionCodec.FORMAT_12X, IndexType.NONE);
    LONG_TO_FLOAT = new Info(133, "long-to-float", InstructionCodec.FORMAT_12X, IndexType.NONE);
    LONG_TO_DOUBLE = new Info(134, "long-to-double", InstructionCodec.FORMAT_12X, IndexType.NONE);
    FLOAT_TO_INT = new Info(135, "float-to-int", InstructionCodec.FORMAT_12X, IndexType.NONE);
    FLOAT_TO_LONG = new Info(136, "float-to-long", InstructionCodec.FORMAT_12X, IndexType.NONE);
    FLOAT_TO_DOUBLE = new Info(137, "float-to-double", InstructionCodec.FORMAT_12X, IndexType.NONE);
    DOUBLE_TO_INT = new Info(138, "double-to-int", InstructionCodec.FORMAT_12X, IndexType.NONE);
    DOUBLE_TO_LONG = new Info(139, "double-to-long", InstructionCodec.FORMAT_12X, IndexType.NONE);
    DOUBLE_TO_FLOAT = new Info(140, "double-to-float", InstructionCodec.FORMAT_12X, IndexType.NONE);
    INT_TO_BYTE = new Info(141, "int-to-byte", InstructionCodec.FORMAT_12X, IndexType.NONE);
    INT_TO_CHAR = new Info(142, "int-to-char", InstructionCodec.FORMAT_12X, IndexType.NONE);
    INT_TO_SHORT = new Info(143, "int-to-short", InstructionCodec.FORMAT_12X, IndexType.NONE);
    ADD_INT = new Info(144, "add-int", InstructionCodec.FORMAT_23X, IndexType.NONE);
    SUB_INT = new Info(145, "sub-int", InstructionCodec.FORMAT_23X, IndexType.NONE);
    MUL_INT = new Info(146, "mul-int", InstructionCodec.FORMAT_23X, IndexType.NONE);
    DIV_INT = new Info(147, "div-int", InstructionCodec.FORMAT_23X, IndexType.NONE);
    REM_INT = new Info(148, "rem-int", InstructionCodec.FORMAT_23X, IndexType.NONE);
    AND_INT = new Info(149, "and-int", InstructionCodec.FORMAT_23X, IndexType.NONE);
    OR_INT = new Info(150, "or-int", InstructionCodec.FORMAT_23X, IndexType.NONE);
    XOR_INT = new Info(151, "xor-int", InstructionCodec.FORMAT_23X, IndexType.NONE);
    SHL_INT = new Info(152, "shl-int", InstructionCodec.FORMAT_23X, IndexType.NONE);
    SHR_INT = new Info(153, "shr-int", InstructionCodec.FORMAT_23X, IndexType.NONE);
    USHR_INT = new Info(154, "ushr-int", InstructionCodec.FORMAT_23X, IndexType.NONE);
    ADD_LONG = new Info(155, "add-long", InstructionCodec.FORMAT_23X, IndexType.NONE);
    SUB_LONG = new Info(156, "sub-long", InstructionCodec.FORMAT_23X, IndexType.NONE);
    MUL_LONG = new Info(157, "mul-long", InstructionCodec.FORMAT_23X, IndexType.NONE);
    DIV_LONG = new Info(158, "div-long", InstructionCodec.FORMAT_23X, IndexType.NONE);
    REM_LONG = new Info(159, "rem-long", InstructionCodec.FORMAT_23X, IndexType.NONE);
    AND_LONG = new Info(160, "and-long", InstructionCodec.FORMAT_23X, IndexType.NONE);
    OR_LONG = new Info(161, "or-long", InstructionCodec.FORMAT_23X, IndexType.NONE);
    XOR_LONG = new Info(162, "xor-long", InstructionCodec.FORMAT_23X, IndexType.NONE);
    SHL_LONG = new Info(163, "shl-long", InstructionCodec.FORMAT_23X, IndexType.NONE);
    SHR_LONG = new Info(164, "shr-long", InstructionCodec.FORMAT_23X, IndexType.NONE);
    USHR_LONG = new Info(165, "ushr-long", InstructionCodec.FORMAT_23X, IndexType.NONE);
    ADD_FLOAT = new Info(166, "add-float", InstructionCodec.FORMAT_23X, IndexType.NONE);
    SUB_FLOAT = new Info(167, "sub-float", InstructionCodec.FORMAT_23X, IndexType.NONE);
    MUL_FLOAT = new Info(168, "mul-float", InstructionCodec.FORMAT_23X, IndexType.NONE);
    DIV_FLOAT = new Info(169, "div-float", InstructionCodec.FORMAT_23X, IndexType.NONE);
    REM_FLOAT = new Info(170, "rem-float", InstructionCodec.FORMAT_23X, IndexType.NONE);
    ADD_DOUBLE = new Info(171, "add-double", InstructionCodec.FORMAT_23X, IndexType.NONE);
    SUB_DOUBLE = new Info(172, "sub-double", InstructionCodec.FORMAT_23X, IndexType.NONE);
    MUL_DOUBLE = new Info(173, "mul-double", InstructionCodec.FORMAT_23X, IndexType.NONE);
    DIV_DOUBLE = new Info(174, "div-double", InstructionCodec.FORMAT_23X, IndexType.NONE);
    REM_DOUBLE = new Info(175, "rem-double", InstructionCodec.FORMAT_23X, IndexType.NONE);
    ADD_INT_2ADDR = new Info(176, "add-int/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    SUB_INT_2ADDR = new Info(177, "sub-int/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    MUL_INT_2ADDR = new Info(178, "mul-int/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    DIV_INT_2ADDR = new Info(179, "div-int/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    REM_INT_2ADDR = new Info(180, "rem-int/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    AND_INT_2ADDR = new Info(181, "and-int/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    OR_INT_2ADDR = new Info(182, "or-int/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    XOR_INT_2ADDR = new Info(183, "xor-int/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    SHL_INT_2ADDR = new Info(184, "shl-int/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    SHR_INT_2ADDR = new Info(185, "shr-int/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    USHR_INT_2ADDR = new Info(186, "ushr-int/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    ADD_LONG_2ADDR = new Info(187, "add-long/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    SUB_LONG_2ADDR = new Info(188, "sub-long/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    MUL_LONG_2ADDR = new Info(189, "mul-long/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    DIV_LONG_2ADDR = new Info(190, "div-long/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    REM_LONG_2ADDR = new Info(191, "rem-long/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    AND_LONG_2ADDR = new Info(192, "and-long/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    OR_LONG_2ADDR = new Info(193, "or-long/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    XOR_LONG_2ADDR = new Info(194, "xor-long/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    SHL_LONG_2ADDR = new Info(195, "shl-long/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    SHR_LONG_2ADDR = new Info(196, "shr-long/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    USHR_LONG_2ADDR = new Info(197, "ushr-long/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    ADD_FLOAT_2ADDR = new Info(198, "add-float/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    SUB_FLOAT_2ADDR = new Info(199, "sub-float/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    MUL_FLOAT_2ADDR = new Info(200, "mul-float/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    DIV_FLOAT_2ADDR = new Info(201, "div-float/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    REM_FLOAT_2ADDR = new Info(202, "rem-float/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    ADD_DOUBLE_2ADDR = new Info(203, "add-double/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    SUB_DOUBLE_2ADDR = new Info(204, "sub-double/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    MUL_DOUBLE_2ADDR = new Info(205, "mul-double/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    DIV_DOUBLE_2ADDR = new Info(206, "div-double/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    REM_DOUBLE_2ADDR = new Info(207, "rem-double/2addr", InstructionCodec.FORMAT_12X, IndexType.NONE);
    ADD_INT_LIT16 = new Info(208, "add-int/lit16", InstructionCodec.FORMAT_22S, IndexType.NONE);
    RSUB_INT = new Info(209, "rsub-int", InstructionCodec.FORMAT_22S, IndexType.NONE);
    MUL_INT_LIT16 = new Info(210, "mul-int/lit16", InstructionCodec.FORMAT_22S, IndexType.NONE);
    DIV_INT_LIT16 = new Info(211, "div-int/lit16", InstructionCodec.FORMAT_22S, IndexType.NONE);
    REM_INT_LIT16 = new Info(212, "rem-int/lit16", InstructionCodec.FORMAT_22S, IndexType.NONE);
    AND_INT_LIT16 = new Info(213, "and-int/lit16", InstructionCodec.FORMAT_22S, IndexType.NONE);
    OR_INT_LIT16 = new Info(214, "or-int/lit16", InstructionCodec.FORMAT_22S, IndexType.NONE);
    XOR_INT_LIT16 = new Info(215, "xor-int/lit16", InstructionCodec.FORMAT_22S, IndexType.NONE);
    ADD_INT_LIT8 = new Info(216, "add-int/lit8", InstructionCodec.FORMAT_22B, IndexType.NONE);
    RSUB_INT_LIT8 = new Info(217, "rsub-int/lit8", InstructionCodec.FORMAT_22B, IndexType.NONE);
    MUL_INT_LIT8 = new Info(218, "mul-int/lit8", InstructionCodec.FORMAT_22B, IndexType.NONE);
    DIV_INT_LIT8 = new Info(219, "div-int/lit8", InstructionCodec.FORMAT_22B, IndexType.NONE);
    REM_INT_LIT8 = new Info(220, "rem-int/lit8", InstructionCodec.FORMAT_22B, IndexType.NONE);
    AND_INT_LIT8 = new Info(221, "and-int/lit8", InstructionCodec.FORMAT_22B, IndexType.NONE);
    OR_INT_LIT8 = new Info(222, "or-int/lit8", InstructionCodec.FORMAT_22B, IndexType.NONE);
    XOR_INT_LIT8 = new Info(223, "xor-int/lit8", InstructionCodec.FORMAT_22B, IndexType.NONE);
    SHL_INT_LIT8 = new Info(224, "shl-int/lit8", InstructionCodec.FORMAT_22B, IndexType.NONE);
    SHR_INT_LIT8 = new Info(225, "shr-int/lit8", InstructionCodec.FORMAT_22B, IndexType.NONE);
    USHR_INT_LIT8 = new Info(226, "ushr-int/lit8", InstructionCodec.FORMAT_22B, IndexType.NONE);
    INVOKE_POLYMORPHIC = new Info(250, "invoke-polymorphic", InstructionCodec.FORMAT_45CC, IndexType.METHOD_AND_PROTO_REF);
    INVOKE_POLYMORPHIC_RANGE = new Info(251, "invoke-polymorphic/range", InstructionCodec.FORMAT_4RCC, IndexType.METHOD_AND_PROTO_REF);
    INVOKE_CUSTOM = new Info(252, "invoke-custom", InstructionCodec.FORMAT_35C, IndexType.CALL_SITE_REF);
    INVOKE_CUSTOM_RANGE = new Info(253, "invoke-custom/range", InstructionCodec.FORMAT_3RC, IndexType.CALL_SITE_REF);
    CONST_METHOD_HANDLE = new Info(254, "const-method-handle", InstructionCodec.FORMAT_21C, IndexType.METHOD_HANDLE_REF);
    CONST_METHOD_TYPE = new Info(255, "const-method-type", InstructionCodec.FORMAT_21C, IndexType.PROTO_REF);
    INFO = new Info[65537];
    set(SPECIAL_FORMAT);
    set(PACKED_SWITCH_PAYLOAD);
    set(SPARSE_SWITCH_PAYLOAD);
    set(FILL_ARRAY_DATA_PAYLOAD);
    set(NOP);
    set(MOVE);
    set(MOVE_FROM16);
    set(MOVE_16);
    set(MOVE_WIDE);
    set(MOVE_WIDE_FROM16);
    set(MOVE_WIDE_16);
    set(MOVE_OBJECT);
    set(MOVE_OBJECT_FROM16);
    set(MOVE_OBJECT_16);
    set(MOVE_RESULT);
    set(MOVE_RESULT_WIDE);
    set(MOVE_RESULT_OBJECT);
    set(MOVE_EXCEPTION);
    set(RETURN_VOID);
    set(RETURN);
    set(RETURN_WIDE);
    set(RETURN_OBJECT);
    set(CONST_4);
    set(CONST_16);
    set(CONST);
    set(CONST_HIGH16);
    set(CONST_WIDE_16);
    set(CONST_WIDE_32);
    set(CONST_WIDE);
    set(CONST_WIDE_HIGH16);
    set(CONST_STRING);
    set(CONST_STRING_JUMBO);
    set(CONST_CLASS);
    set(MONITOR_ENTER);
    set(MONITOR_EXIT);
    set(CHECK_CAST);
    set(INSTANCE_OF);
    set(ARRAY_LENGTH);
    set(NEW_INSTANCE);
    set(NEW_ARRAY);
    set(FILLED_NEW_ARRAY);
    set(FILLED_NEW_ARRAY_RANGE);
    set(FILL_ARRAY_DATA);
    set(THROW);
    set(GOTO);
    set(GOTO_16);
    set(GOTO_32);
    set(PACKED_SWITCH);
    set(SPARSE_SWITCH);
    set(CMPL_FLOAT);
    set(CMPG_FLOAT);
    set(CMPL_DOUBLE);
    set(CMPG_DOUBLE);
    set(CMP_LONG);
    set(IF_EQ);
    set(IF_NE);
    set(IF_LT);
    set(IF_GE);
    set(IF_GT);
    set(IF_LE);
    set(IF_EQZ);
    set(IF_NEZ);
    set(IF_LTZ);
    set(IF_GEZ);
    set(IF_GTZ);
    set(IF_LEZ);
    set(AGET);
    set(AGET_WIDE);
    set(AGET_OBJECT);
    set(AGET_BOOLEAN);
    set(AGET_BYTE);
    set(AGET_CHAR);
    set(AGET_SHORT);
    set(APUT);
    set(APUT_WIDE);
    set(APUT_OBJECT);
    set(APUT_BOOLEAN);
    set(APUT_BYTE);
    set(APUT_CHAR);
    set(APUT_SHORT);
    set(IGET);
    set(IGET_WIDE);
    set(IGET_OBJECT);
    set(IGET_BOOLEAN);
    set(IGET_BYTE);
    set(IGET_CHAR);
    set(IGET_SHORT);
    set(IPUT);
    set(IPUT_WIDE);
    set(IPUT_OBJECT);
    set(IPUT_BOOLEAN);
    set(IPUT_BYTE);
    set(IPUT_CHAR);
    set(IPUT_SHORT);
    set(SGET);
    set(SGET_WIDE);
    set(SGET_OBJECT);
    set(SGET_BOOLEAN);
    set(SGET_BYTE);
    set(SGET_CHAR);
    set(SGET_SHORT);
    set(SPUT);
    set(SPUT_WIDE);
    set(SPUT_OBJECT);
    set(SPUT_BOOLEAN);
    set(SPUT_BYTE);
    set(SPUT_CHAR);
    set(SPUT_SHORT);
    set(INVOKE_VIRTUAL);
    set(INVOKE_SUPER);
    set(INVOKE_DIRECT);
    set(INVOKE_STATIC);
    set(INVOKE_INTERFACE);
    set(INVOKE_VIRTUAL_RANGE);
    set(INVOKE_SUPER_RANGE);
    set(INVOKE_DIRECT_RANGE);
    set(INVOKE_STATIC_RANGE);
    set(INVOKE_INTERFACE_RANGE);
    set(NEG_INT);
    set(NOT_INT);
    set(NEG_LONG);
    set(NOT_LONG);
    set(NEG_FLOAT);
    set(NEG_DOUBLE);
    set(INT_TO_LONG);
    set(INT_TO_FLOAT);
    set(INT_TO_DOUBLE);
    set(LONG_TO_INT);
    set(LONG_TO_FLOAT);
    set(LONG_TO_DOUBLE);
    set(FLOAT_TO_INT);
    set(FLOAT_TO_LONG);
    set(FLOAT_TO_DOUBLE);
    set(DOUBLE_TO_INT);
    set(DOUBLE_TO_LONG);
    set(DOUBLE_TO_FLOAT);
    set(INT_TO_BYTE);
    set(INT_TO_CHAR);
    set(INT_TO_SHORT);
    set(ADD_INT);
    set(SUB_INT);
    set(MUL_INT);
    set(DIV_INT);
    set(REM_INT);
    set(AND_INT);
    set(OR_INT);
    set(XOR_INT);
    set(SHL_INT);
    set(SHR_INT);
    set(USHR_INT);
    set(ADD_LONG);
    set(SUB_LONG);
    set(MUL_LONG);
    set(DIV_LONG);
    set(REM_LONG);
    set(AND_LONG);
    set(OR_LONG);
    set(XOR_LONG);
    set(SHL_LONG);
    set(SHR_LONG);
    set(USHR_LONG);
    set(ADD_FLOAT);
    set(SUB_FLOAT);
    set(MUL_FLOAT);
    set(DIV_FLOAT);
    set(REM_FLOAT);
    set(ADD_DOUBLE);
    set(SUB_DOUBLE);
    set(MUL_DOUBLE);
    set(DIV_DOUBLE);
    set(REM_DOUBLE);
    set(ADD_INT_2ADDR);
    set(SUB_INT_2ADDR);
    set(MUL_INT_2ADDR);
    set(DIV_INT_2ADDR);
    set(REM_INT_2ADDR);
    set(AND_INT_2ADDR);
    set(OR_INT_2ADDR);
    set(XOR_INT_2ADDR);
    set(SHL_INT_2ADDR);
    set(SHR_INT_2ADDR);
    set(USHR_INT_2ADDR);
    set(ADD_LONG_2ADDR);
    set(SUB_LONG_2ADDR);
    set(MUL_LONG_2ADDR);
    set(DIV_LONG_2ADDR);
    set(REM_LONG_2ADDR);
    set(AND_LONG_2ADDR);
    set(OR_LONG_2ADDR);
    set(XOR_LONG_2ADDR);
    set(SHL_LONG_2ADDR);
    set(SHR_LONG_2ADDR);
    set(USHR_LONG_2ADDR);
    set(ADD_FLOAT_2ADDR);
    set(SUB_FLOAT_2ADDR);
    set(MUL_FLOAT_2ADDR);
    set(DIV_FLOAT_2ADDR);
    set(REM_FLOAT_2ADDR);
    set(ADD_DOUBLE_2ADDR);
    set(SUB_DOUBLE_2ADDR);
    set(MUL_DOUBLE_2ADDR);
    set(DIV_DOUBLE_2ADDR);
    set(REM_DOUBLE_2ADDR);
    set(ADD_INT_LIT16);
    set(RSUB_INT);
    set(MUL_INT_LIT16);
    set(DIV_INT_LIT16);
    set(REM_INT_LIT16);
    set(AND_INT_LIT16);
    set(OR_INT_LIT16);
    set(XOR_INT_LIT16);
    set(ADD_INT_LIT8);
    set(RSUB_INT_LIT8);
    set(MUL_INT_LIT8);
    set(DIV_INT_LIT8);
    set(REM_INT_LIT8);
    set(AND_INT_LIT8);
    set(OR_INT_LIT8);
    set(XOR_INT_LIT8);
    set(SHL_INT_LIT8);
    set(SHR_INT_LIT8);
    set(USHR_INT_LIT8);
    set(INVOKE_POLYMORPHIC);
    set(INVOKE_POLYMORPHIC_RANGE);
    set(INVOKE_CUSTOM);
    set(INVOKE_CUSTOM_RANGE);
    set(CONST_METHOD_HANDLE);
    set(CONST_METHOD_TYPE);
  }
  
  public static Info get(int paramInt) {
    try {
      Info info = INFO[paramInt + 1];
      if (info != null)
        return info; 
    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {}
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("bogus opcode: ");
    stringBuilder.append(Hex.u2or4(paramInt));
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public static InstructionCodec getFormat(int paramInt) {
    return get(paramInt).getFormat();
  }
  
  public static IndexType getIndexType(int paramInt) {
    return get(paramInt).getIndexType();
  }
  
  public static String getName(int paramInt) {
    return get(paramInt).getName();
  }
  
  private static void set(Info paramInfo) {
    int i = paramInfo.getOpcode();
    INFO[i + 1] = paramInfo;
  }
  
  public static class Info {
    private final InstructionCodec format;
    
    private final IndexType indexType;
    
    private final String name;
    
    private final int opcode;
    
    public Info(int param1Int, String param1String, InstructionCodec param1InstructionCodec, IndexType param1IndexType) {
      this.opcode = param1Int;
      this.name = param1String;
      this.format = param1InstructionCodec;
      this.indexType = param1IndexType;
    }
    
    public InstructionCodec getFormat() {
      return this.format;
    }
    
    public IndexType getIndexType() {
      return this.indexType;
    }
    
    public String getName() {
      return this.name;
    }
    
    public int getOpcode() {
      return this.opcode;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\io\OpcodeInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */