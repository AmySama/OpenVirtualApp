package com.android.dx.dex.code;

import com.android.dx.dex.DexOptions;
import com.android.dx.dex.code.form.Form10t;
import com.android.dx.dex.code.form.Form10x;
import com.android.dx.dex.code.form.Form11n;
import com.android.dx.dex.code.form.Form11x;
import com.android.dx.dex.code.form.Form12x;
import com.android.dx.dex.code.form.Form20t;
import com.android.dx.dex.code.form.Form21c;
import com.android.dx.dex.code.form.Form21h;
import com.android.dx.dex.code.form.Form21s;
import com.android.dx.dex.code.form.Form21t;
import com.android.dx.dex.code.form.Form22b;
import com.android.dx.dex.code.form.Form22c;
import com.android.dx.dex.code.form.Form22s;
import com.android.dx.dex.code.form.Form22t;
import com.android.dx.dex.code.form.Form22x;
import com.android.dx.dex.code.form.Form23x;
import com.android.dx.dex.code.form.Form30t;
import com.android.dx.dex.code.form.Form31c;
import com.android.dx.dex.code.form.Form31i;
import com.android.dx.dex.code.form.Form31t;
import com.android.dx.dex.code.form.Form32x;
import com.android.dx.dex.code.form.Form35c;
import com.android.dx.dex.code.form.Form3rc;
import com.android.dx.dex.code.form.Form45cc;
import com.android.dx.dex.code.form.Form4rcc;
import com.android.dx.dex.code.form.Form51l;
import com.android.dx.dex.code.form.SpecialFormat;

public final class Dops {
  public static final Dop ADD_DOUBLE;
  
  public static final Dop ADD_DOUBLE_2ADDR;
  
  public static final Dop ADD_FLOAT;
  
  public static final Dop ADD_FLOAT_2ADDR;
  
  public static final Dop ADD_INT;
  
  public static final Dop ADD_INT_2ADDR;
  
  public static final Dop ADD_INT_LIT16;
  
  public static final Dop ADD_INT_LIT8;
  
  public static final Dop ADD_LONG;
  
  public static final Dop ADD_LONG_2ADDR;
  
  public static final Dop AGET;
  
  public static final Dop AGET_BOOLEAN;
  
  public static final Dop AGET_BYTE;
  
  public static final Dop AGET_CHAR;
  
  public static final Dop AGET_OBJECT;
  
  public static final Dop AGET_SHORT;
  
  public static final Dop AGET_WIDE;
  
  public static final Dop AND_INT;
  
  public static final Dop AND_INT_2ADDR;
  
  public static final Dop AND_INT_LIT16;
  
  public static final Dop AND_INT_LIT8;
  
  public static final Dop AND_LONG;
  
  public static final Dop AND_LONG_2ADDR;
  
  public static final Dop APUT;
  
  public static final Dop APUT_BOOLEAN;
  
  public static final Dop APUT_BYTE;
  
  public static final Dop APUT_CHAR;
  
  public static final Dop APUT_OBJECT;
  
  public static final Dop APUT_SHORT;
  
  public static final Dop APUT_WIDE;
  
  public static final Dop ARRAY_LENGTH;
  
  public static final Dop CHECK_CAST;
  
  public static final Dop CMPG_DOUBLE;
  
  public static final Dop CMPG_FLOAT;
  
  public static final Dop CMPL_DOUBLE;
  
  public static final Dop CMPL_FLOAT;
  
  public static final Dop CMP_LONG;
  
  public static final Dop CONST;
  
  public static final Dop CONST_16;
  
  public static final Dop CONST_4;
  
  public static final Dop CONST_CLASS;
  
  public static final Dop CONST_HIGH16;
  
  public static final Dop CONST_METHOD_HANDLE;
  
  public static final Dop CONST_METHOD_TYPE;
  
  public static final Dop CONST_STRING;
  
  public static final Dop CONST_STRING_JUMBO;
  
  public static final Dop CONST_WIDE;
  
  public static final Dop CONST_WIDE_16;
  
  public static final Dop CONST_WIDE_32;
  
  public static final Dop CONST_WIDE_HIGH16;
  
  public static final Dop DIV_DOUBLE;
  
  public static final Dop DIV_DOUBLE_2ADDR;
  
  public static final Dop DIV_FLOAT;
  
  public static final Dop DIV_FLOAT_2ADDR;
  
  public static final Dop DIV_INT;
  
  public static final Dop DIV_INT_2ADDR;
  
  public static final Dop DIV_INT_LIT16;
  
  public static final Dop DIV_INT_LIT8;
  
  public static final Dop DIV_LONG;
  
  public static final Dop DIV_LONG_2ADDR;
  
  private static final Dop[] DOPS;
  
  public static final Dop DOUBLE_TO_FLOAT;
  
  public static final Dop DOUBLE_TO_INT;
  
  public static final Dop DOUBLE_TO_LONG;
  
  public static final Dop FILLED_NEW_ARRAY;
  
  public static final Dop FILLED_NEW_ARRAY_RANGE;
  
  public static final Dop FILL_ARRAY_DATA;
  
  public static final Dop FLOAT_TO_DOUBLE;
  
  public static final Dop FLOAT_TO_INT;
  
  public static final Dop FLOAT_TO_LONG;
  
  public static final Dop GOTO;
  
  public static final Dop GOTO_16;
  
  public static final Dop GOTO_32;
  
  public static final Dop IF_EQ;
  
  public static final Dop IF_EQZ;
  
  public static final Dop IF_GE;
  
  public static final Dop IF_GEZ;
  
  public static final Dop IF_GT;
  
  public static final Dop IF_GTZ;
  
  public static final Dop IF_LE;
  
  public static final Dop IF_LEZ;
  
  public static final Dop IF_LT;
  
  public static final Dop IF_LTZ;
  
  public static final Dop IF_NE;
  
  public static final Dop IF_NEZ;
  
  public static final Dop IGET;
  
  public static final Dop IGET_BOOLEAN;
  
  public static final Dop IGET_BYTE;
  
  public static final Dop IGET_CHAR;
  
  public static final Dop IGET_OBJECT;
  
  public static final Dop IGET_SHORT;
  
  public static final Dop IGET_WIDE;
  
  public static final Dop INSTANCE_OF;
  
  public static final Dop INT_TO_BYTE;
  
  public static final Dop INT_TO_CHAR;
  
  public static final Dop INT_TO_DOUBLE;
  
  public static final Dop INT_TO_FLOAT;
  
  public static final Dop INT_TO_LONG;
  
  public static final Dop INT_TO_SHORT;
  
  public static final Dop INVOKE_CUSTOM;
  
  public static final Dop INVOKE_CUSTOM_RANGE;
  
  public static final Dop INVOKE_DIRECT;
  
  public static final Dop INVOKE_DIRECT_RANGE;
  
  public static final Dop INVOKE_INTERFACE;
  
  public static final Dop INVOKE_INTERFACE_RANGE;
  
  public static final Dop INVOKE_POLYMORPHIC;
  
  public static final Dop INVOKE_POLYMORPHIC_RANGE;
  
  public static final Dop INVOKE_STATIC;
  
  public static final Dop INVOKE_STATIC_RANGE;
  
  public static final Dop INVOKE_SUPER;
  
  public static final Dop INVOKE_SUPER_RANGE;
  
  public static final Dop INVOKE_VIRTUAL;
  
  public static final Dop INVOKE_VIRTUAL_RANGE;
  
  public static final Dop IPUT;
  
  public static final Dop IPUT_BOOLEAN;
  
  public static final Dop IPUT_BYTE;
  
  public static final Dop IPUT_CHAR;
  
  public static final Dop IPUT_OBJECT;
  
  public static final Dop IPUT_SHORT;
  
  public static final Dop IPUT_WIDE;
  
  public static final Dop LONG_TO_DOUBLE;
  
  public static final Dop LONG_TO_FLOAT;
  
  public static final Dop LONG_TO_INT;
  
  public static final Dop MONITOR_ENTER;
  
  public static final Dop MONITOR_EXIT;
  
  public static final Dop MOVE;
  
  public static final Dop MOVE_16;
  
  public static final Dop MOVE_EXCEPTION;
  
  public static final Dop MOVE_FROM16;
  
  public static final Dop MOVE_OBJECT;
  
  public static final Dop MOVE_OBJECT_16;
  
  public static final Dop MOVE_OBJECT_FROM16;
  
  public static final Dop MOVE_RESULT;
  
  public static final Dop MOVE_RESULT_OBJECT;
  
  public static final Dop MOVE_RESULT_WIDE;
  
  public static final Dop MOVE_WIDE;
  
  public static final Dop MOVE_WIDE_16;
  
  public static final Dop MOVE_WIDE_FROM16;
  
  public static final Dop MUL_DOUBLE;
  
  public static final Dop MUL_DOUBLE_2ADDR;
  
  public static final Dop MUL_FLOAT;
  
  public static final Dop MUL_FLOAT_2ADDR;
  
  public static final Dop MUL_INT;
  
  public static final Dop MUL_INT_2ADDR;
  
  public static final Dop MUL_INT_LIT16;
  
  public static final Dop MUL_INT_LIT8;
  
  public static final Dop MUL_LONG;
  
  public static final Dop MUL_LONG_2ADDR;
  
  public static final Dop NEG_DOUBLE;
  
  public static final Dop NEG_FLOAT;
  
  public static final Dop NEG_INT;
  
  public static final Dop NEG_LONG;
  
  public static final Dop NEW_ARRAY;
  
  public static final Dop NEW_INSTANCE;
  
  public static final Dop NOP;
  
  public static final Dop NOT_INT;
  
  public static final Dop NOT_LONG;
  
  public static final Dop OR_INT;
  
  public static final Dop OR_INT_2ADDR;
  
  public static final Dop OR_INT_LIT16;
  
  public static final Dop OR_INT_LIT8;
  
  public static final Dop OR_LONG;
  
  public static final Dop OR_LONG_2ADDR;
  
  public static final Dop PACKED_SWITCH;
  
  public static final Dop REM_DOUBLE;
  
  public static final Dop REM_DOUBLE_2ADDR;
  
  public static final Dop REM_FLOAT;
  
  public static final Dop REM_FLOAT_2ADDR;
  
  public static final Dop REM_INT;
  
  public static final Dop REM_INT_2ADDR;
  
  public static final Dop REM_INT_LIT16;
  
  public static final Dop REM_INT_LIT8;
  
  public static final Dop REM_LONG;
  
  public static final Dop REM_LONG_2ADDR;
  
  public static final Dop RETURN;
  
  public static final Dop RETURN_OBJECT;
  
  public static final Dop RETURN_VOID;
  
  public static final Dop RETURN_WIDE;
  
  public static final Dop RSUB_INT;
  
  public static final Dop RSUB_INT_LIT8;
  
  public static final Dop SGET;
  
  public static final Dop SGET_BOOLEAN;
  
  public static final Dop SGET_BYTE;
  
  public static final Dop SGET_CHAR;
  
  public static final Dop SGET_OBJECT;
  
  public static final Dop SGET_SHORT;
  
  public static final Dop SGET_WIDE;
  
  public static final Dop SHL_INT;
  
  public static final Dop SHL_INT_2ADDR;
  
  public static final Dop SHL_INT_LIT8;
  
  public static final Dop SHL_LONG;
  
  public static final Dop SHL_LONG_2ADDR;
  
  public static final Dop SHR_INT;
  
  public static final Dop SHR_INT_2ADDR;
  
  public static final Dop SHR_INT_LIT8;
  
  public static final Dop SHR_LONG;
  
  public static final Dop SHR_LONG_2ADDR;
  
  public static final Dop SPARSE_SWITCH;
  
  public static final Dop SPECIAL_FORMAT = new Dop(-1, -1, -1, SpecialFormat.THE_ONE, false);
  
  public static final Dop SPUT;
  
  public static final Dop SPUT_BOOLEAN;
  
  public static final Dop SPUT_BYTE;
  
  public static final Dop SPUT_CHAR;
  
  public static final Dop SPUT_OBJECT;
  
  public static final Dop SPUT_SHORT;
  
  public static final Dop SPUT_WIDE;
  
  public static final Dop SUB_DOUBLE;
  
  public static final Dop SUB_DOUBLE_2ADDR;
  
  public static final Dop SUB_FLOAT;
  
  public static final Dop SUB_FLOAT_2ADDR;
  
  public static final Dop SUB_INT;
  
  public static final Dop SUB_INT_2ADDR;
  
  public static final Dop SUB_LONG;
  
  public static final Dop SUB_LONG_2ADDR;
  
  public static final Dop THROW;
  
  public static final Dop USHR_INT;
  
  public static final Dop USHR_INT_2ADDR;
  
  public static final Dop USHR_INT_LIT8;
  
  public static final Dop USHR_LONG;
  
  public static final Dop USHR_LONG_2ADDR;
  
  public static final Dop XOR_INT;
  
  public static final Dop XOR_INT_2ADDR;
  
  public static final Dop XOR_INT_LIT16;
  
  public static final Dop XOR_INT_LIT8;
  
  public static final Dop XOR_LONG;
  
  public static final Dop XOR_LONG_2ADDR;
  
  static {
    NOP = new Dop(0, 0, -1, Form10x.THE_ONE, false);
    MOVE = new Dop(1, 1, 2, Form12x.THE_ONE, true);
    MOVE_FROM16 = new Dop(2, 1, 3, Form22x.THE_ONE, true);
    MOVE_16 = new Dop(3, 1, -1, Form32x.THE_ONE, true);
    MOVE_WIDE = new Dop(4, 4, 5, Form12x.THE_ONE, true);
    MOVE_WIDE_FROM16 = new Dop(5, 4, 6, Form22x.THE_ONE, true);
    MOVE_WIDE_16 = new Dop(6, 4, -1, Form32x.THE_ONE, true);
    MOVE_OBJECT = new Dop(7, 7, 8, Form12x.THE_ONE, true);
    MOVE_OBJECT_FROM16 = new Dop(8, 7, 9, Form22x.THE_ONE, true);
    MOVE_OBJECT_16 = new Dop(9, 7, -1, Form32x.THE_ONE, true);
    MOVE_RESULT = new Dop(10, 10, -1, Form11x.THE_ONE, true);
    MOVE_RESULT_WIDE = new Dop(11, 11, -1, Form11x.THE_ONE, true);
    MOVE_RESULT_OBJECT = new Dop(12, 12, -1, Form11x.THE_ONE, true);
    MOVE_EXCEPTION = new Dop(13, 13, -1, Form11x.THE_ONE, true);
    RETURN_VOID = new Dop(14, 14, -1, Form10x.THE_ONE, false);
    RETURN = new Dop(15, 15, -1, Form11x.THE_ONE, false);
    RETURN_WIDE = new Dop(16, 16, -1, Form11x.THE_ONE, false);
    RETURN_OBJECT = new Dop(17, 17, -1, Form11x.THE_ONE, false);
    CONST_4 = new Dop(18, 20, 19, Form11n.THE_ONE, true);
    CONST_16 = new Dop(19, 20, 21, Form21s.THE_ONE, true);
    CONST = new Dop(20, 20, -1, Form31i.THE_ONE, true);
    CONST_HIGH16 = new Dop(21, 20, 20, Form21h.THE_ONE, true);
    CONST_WIDE_16 = new Dop(22, 24, 25, Form21s.THE_ONE, true);
    CONST_WIDE_32 = new Dop(23, 24, 24, Form31i.THE_ONE, true);
    CONST_WIDE = new Dop(24, 24, -1, Form51l.THE_ONE, true);
    CONST_WIDE_HIGH16 = new Dop(25, 24, 23, Form21h.THE_ONE, true);
    CONST_STRING = new Dop(26, 26, 27, Form21c.THE_ONE, true);
    CONST_STRING_JUMBO = new Dop(27, 26, -1, Form31c.THE_ONE, true);
    CONST_CLASS = new Dop(28, 28, -1, Form21c.THE_ONE, true);
    MONITOR_ENTER = new Dop(29, 29, -1, Form11x.THE_ONE, false);
    MONITOR_EXIT = new Dop(30, 30, -1, Form11x.THE_ONE, false);
    CHECK_CAST = new Dop(31, 31, -1, Form21c.THE_ONE, true);
    INSTANCE_OF = new Dop(32, 32, -1, Form22c.THE_ONE, true);
    ARRAY_LENGTH = new Dop(33, 33, -1, Form12x.THE_ONE, true);
    NEW_INSTANCE = new Dop(34, 34, -1, Form21c.THE_ONE, true);
    NEW_ARRAY = new Dop(35, 35, -1, Form22c.THE_ONE, true);
    FILLED_NEW_ARRAY = new Dop(36, 36, 37, Form35c.THE_ONE, false);
    FILLED_NEW_ARRAY_RANGE = new Dop(37, 36, -1, Form3rc.THE_ONE, false);
    FILL_ARRAY_DATA = new Dop(38, 38, -1, Form31t.THE_ONE, false);
    THROW = new Dop(39, 39, -1, Form11x.THE_ONE, false);
    GOTO = new Dop(40, 40, 41, Form10t.THE_ONE, false);
    GOTO_16 = new Dop(41, 40, 42, Form20t.THE_ONE, false);
    GOTO_32 = new Dop(42, 40, -1, Form30t.THE_ONE, false);
    PACKED_SWITCH = new Dop(43, 43, -1, Form31t.THE_ONE, false);
    SPARSE_SWITCH = new Dop(44, 44, -1, Form31t.THE_ONE, false);
    CMPL_FLOAT = new Dop(45, 45, -1, Form23x.THE_ONE, true);
    CMPG_FLOAT = new Dop(46, 46, -1, Form23x.THE_ONE, true);
    CMPL_DOUBLE = new Dop(47, 47, -1, Form23x.THE_ONE, true);
    CMPG_DOUBLE = new Dop(48, 48, -1, Form23x.THE_ONE, true);
    CMP_LONG = new Dop(49, 49, -1, Form23x.THE_ONE, true);
    IF_EQ = new Dop(50, 50, -1, Form22t.THE_ONE, false);
    IF_NE = new Dop(51, 51, -1, Form22t.THE_ONE, false);
    IF_LT = new Dop(52, 52, -1, Form22t.THE_ONE, false);
    IF_GE = new Dop(53, 53, -1, Form22t.THE_ONE, false);
    IF_GT = new Dop(54, 54, -1, Form22t.THE_ONE, false);
    IF_LE = new Dop(55, 55, -1, Form22t.THE_ONE, false);
    IF_EQZ = new Dop(56, 56, -1, Form21t.THE_ONE, false);
    IF_NEZ = new Dop(57, 57, -1, Form21t.THE_ONE, false);
    IF_LTZ = new Dop(58, 58, -1, Form21t.THE_ONE, false);
    IF_GEZ = new Dop(59, 59, -1, Form21t.THE_ONE, false);
    IF_GTZ = new Dop(60, 60, -1, Form21t.THE_ONE, false);
    IF_LEZ = new Dop(61, 61, -1, Form21t.THE_ONE, false);
    AGET = new Dop(68, 68, -1, Form23x.THE_ONE, true);
    AGET_WIDE = new Dop(69, 69, -1, Form23x.THE_ONE, true);
    AGET_OBJECT = new Dop(70, 70, -1, Form23x.THE_ONE, true);
    AGET_BOOLEAN = new Dop(71, 71, -1, Form23x.THE_ONE, true);
    AGET_BYTE = new Dop(72, 72, -1, Form23x.THE_ONE, true);
    AGET_CHAR = new Dop(73, 73, -1, Form23x.THE_ONE, true);
    AGET_SHORT = new Dop(74, 74, -1, Form23x.THE_ONE, true);
    APUT = new Dop(75, 75, -1, Form23x.THE_ONE, false);
    APUT_WIDE = new Dop(76, 76, -1, Form23x.THE_ONE, false);
    APUT_OBJECT = new Dop(77, 77, -1, Form23x.THE_ONE, false);
    APUT_BOOLEAN = new Dop(78, 78, -1, Form23x.THE_ONE, false);
    APUT_BYTE = new Dop(79, 79, -1, Form23x.THE_ONE, false);
    APUT_CHAR = new Dop(80, 80, -1, Form23x.THE_ONE, false);
    APUT_SHORT = new Dop(81, 81, -1, Form23x.THE_ONE, false);
    IGET = new Dop(82, 82, -1, Form22c.THE_ONE, true);
    IGET_WIDE = new Dop(83, 83, -1, Form22c.THE_ONE, true);
    IGET_OBJECT = new Dop(84, 84, -1, Form22c.THE_ONE, true);
    IGET_BOOLEAN = new Dop(85, 85, -1, Form22c.THE_ONE, true);
    IGET_BYTE = new Dop(86, 86, -1, Form22c.THE_ONE, true);
    IGET_CHAR = new Dop(87, 87, -1, Form22c.THE_ONE, true);
    IGET_SHORT = new Dop(88, 88, -1, Form22c.THE_ONE, true);
    IPUT = new Dop(89, 89, -1, Form22c.THE_ONE, false);
    IPUT_WIDE = new Dop(90, 90, -1, Form22c.THE_ONE, false);
    IPUT_OBJECT = new Dop(91, 91, -1, Form22c.THE_ONE, false);
    IPUT_BOOLEAN = new Dop(92, 92, -1, Form22c.THE_ONE, false);
    IPUT_BYTE = new Dop(93, 93, -1, Form22c.THE_ONE, false);
    IPUT_CHAR = new Dop(94, 94, -1, Form22c.THE_ONE, false);
    IPUT_SHORT = new Dop(95, 95, -1, Form22c.THE_ONE, false);
    SGET = new Dop(96, 96, -1, Form21c.THE_ONE, true);
    SGET_WIDE = new Dop(97, 97, -1, Form21c.THE_ONE, true);
    SGET_OBJECT = new Dop(98, 98, -1, Form21c.THE_ONE, true);
    SGET_BOOLEAN = new Dop(99, 99, -1, Form21c.THE_ONE, true);
    SGET_BYTE = new Dop(100, 100, -1, Form21c.THE_ONE, true);
    SGET_CHAR = new Dop(101, 101, -1, Form21c.THE_ONE, true);
    SGET_SHORT = new Dop(102, 102, -1, Form21c.THE_ONE, true);
    SPUT = new Dop(103, 103, -1, Form21c.THE_ONE, false);
    SPUT_WIDE = new Dop(104, 104, -1, Form21c.THE_ONE, false);
    SPUT_OBJECT = new Dop(105, 105, -1, Form21c.THE_ONE, false);
    SPUT_BOOLEAN = new Dop(106, 106, -1, Form21c.THE_ONE, false);
    SPUT_BYTE = new Dop(107, 107, -1, Form21c.THE_ONE, false);
    SPUT_CHAR = new Dop(108, 108, -1, Form21c.THE_ONE, false);
    SPUT_SHORT = new Dop(109, 109, -1, Form21c.THE_ONE, false);
    INVOKE_VIRTUAL = new Dop(110, 110, 116, Form35c.THE_ONE, false);
    INVOKE_SUPER = new Dop(111, 111, 117, Form35c.THE_ONE, false);
    INVOKE_DIRECT = new Dop(112, 112, 118, Form35c.THE_ONE, false);
    INVOKE_STATIC = new Dop(113, 113, 119, Form35c.THE_ONE, false);
    INVOKE_INTERFACE = new Dop(114, 114, 120, Form35c.THE_ONE, false);
    INVOKE_VIRTUAL_RANGE = new Dop(116, 110, -1, Form3rc.THE_ONE, false);
    INVOKE_SUPER_RANGE = new Dop(117, 111, -1, Form3rc.THE_ONE, false);
    INVOKE_DIRECT_RANGE = new Dop(118, 112, -1, Form3rc.THE_ONE, false);
    INVOKE_STATIC_RANGE = new Dop(119, 113, -1, Form3rc.THE_ONE, false);
    INVOKE_INTERFACE_RANGE = new Dop(120, 114, -1, Form3rc.THE_ONE, false);
    NEG_INT = new Dop(123, 123, -1, Form12x.THE_ONE, true);
    NOT_INT = new Dop(124, 124, -1, Form12x.THE_ONE, true);
    NEG_LONG = new Dop(125, 125, -1, Form12x.THE_ONE, true);
    NOT_LONG = new Dop(126, 126, -1, Form12x.THE_ONE, true);
    NEG_FLOAT = new Dop(127, 127, -1, Form12x.THE_ONE, true);
    NEG_DOUBLE = new Dop(128, 128, -1, Form12x.THE_ONE, true);
    INT_TO_LONG = new Dop(129, 129, -1, Form12x.THE_ONE, true);
    INT_TO_FLOAT = new Dop(130, 130, -1, Form12x.THE_ONE, true);
    INT_TO_DOUBLE = new Dop(131, 131, -1, Form12x.THE_ONE, true);
    LONG_TO_INT = new Dop(132, 132, -1, Form12x.THE_ONE, true);
    LONG_TO_FLOAT = new Dop(133, 133, -1, Form12x.THE_ONE, true);
    LONG_TO_DOUBLE = new Dop(134, 134, -1, Form12x.THE_ONE, true);
    FLOAT_TO_INT = new Dop(135, 135, -1, Form12x.THE_ONE, true);
    FLOAT_TO_LONG = new Dop(136, 136, -1, Form12x.THE_ONE, true);
    FLOAT_TO_DOUBLE = new Dop(137, 137, -1, Form12x.THE_ONE, true);
    DOUBLE_TO_INT = new Dop(138, 138, -1, Form12x.THE_ONE, true);
    DOUBLE_TO_LONG = new Dop(139, 139, -1, Form12x.THE_ONE, true);
    DOUBLE_TO_FLOAT = new Dop(140, 140, -1, Form12x.THE_ONE, true);
    INT_TO_BYTE = new Dop(141, 141, -1, Form12x.THE_ONE, true);
    INT_TO_CHAR = new Dop(142, 142, -1, Form12x.THE_ONE, true);
    INT_TO_SHORT = new Dop(143, 143, -1, Form12x.THE_ONE, true);
    ADD_INT = new Dop(144, 144, -1, Form23x.THE_ONE, true);
    SUB_INT = new Dop(145, 145, -1, Form23x.THE_ONE, true);
    MUL_INT = new Dop(146, 146, -1, Form23x.THE_ONE, true);
    DIV_INT = new Dop(147, 147, -1, Form23x.THE_ONE, true);
    REM_INT = new Dop(148, 148, -1, Form23x.THE_ONE, true);
    AND_INT = new Dop(149, 149, -1, Form23x.THE_ONE, true);
    OR_INT = new Dop(150, 150, -1, Form23x.THE_ONE, true);
    XOR_INT = new Dop(151, 151, -1, Form23x.THE_ONE, true);
    SHL_INT = new Dop(152, 152, -1, Form23x.THE_ONE, true);
    SHR_INT = new Dop(153, 153, -1, Form23x.THE_ONE, true);
    USHR_INT = new Dop(154, 154, -1, Form23x.THE_ONE, true);
    ADD_LONG = new Dop(155, 155, -1, Form23x.THE_ONE, true);
    SUB_LONG = new Dop(156, 156, -1, Form23x.THE_ONE, true);
    MUL_LONG = new Dop(157, 157, -1, Form23x.THE_ONE, true);
    DIV_LONG = new Dop(158, 158, -1, Form23x.THE_ONE, true);
    REM_LONG = new Dop(159, 159, -1, Form23x.THE_ONE, true);
    AND_LONG = new Dop(160, 160, -1, Form23x.THE_ONE, true);
    OR_LONG = new Dop(161, 161, -1, Form23x.THE_ONE, true);
    XOR_LONG = new Dop(162, 162, -1, Form23x.THE_ONE, true);
    SHL_LONG = new Dop(163, 163, -1, Form23x.THE_ONE, true);
    SHR_LONG = new Dop(164, 164, -1, Form23x.THE_ONE, true);
    USHR_LONG = new Dop(165, 165, -1, Form23x.THE_ONE, true);
    ADD_FLOAT = new Dop(166, 166, -1, Form23x.THE_ONE, true);
    SUB_FLOAT = new Dop(167, 167, -1, Form23x.THE_ONE, true);
    MUL_FLOAT = new Dop(168, 168, -1, Form23x.THE_ONE, true);
    DIV_FLOAT = new Dop(169, 169, -1, Form23x.THE_ONE, true);
    REM_FLOAT = new Dop(170, 170, -1, Form23x.THE_ONE, true);
    ADD_DOUBLE = new Dop(171, 171, -1, Form23x.THE_ONE, true);
    SUB_DOUBLE = new Dop(172, 172, -1, Form23x.THE_ONE, true);
    MUL_DOUBLE = new Dop(173, 173, -1, Form23x.THE_ONE, true);
    DIV_DOUBLE = new Dop(174, 174, -1, Form23x.THE_ONE, true);
    REM_DOUBLE = new Dop(175, 175, -1, Form23x.THE_ONE, true);
    ADD_INT_2ADDR = new Dop(176, 144, 144, Form12x.THE_ONE, true);
    SUB_INT_2ADDR = new Dop(177, 145, 145, Form12x.THE_ONE, true);
    MUL_INT_2ADDR = new Dop(178, 146, 146, Form12x.THE_ONE, true);
    DIV_INT_2ADDR = new Dop(179, 147, 147, Form12x.THE_ONE, true);
    REM_INT_2ADDR = new Dop(180, 148, 148, Form12x.THE_ONE, true);
    AND_INT_2ADDR = new Dop(181, 149, 149, Form12x.THE_ONE, true);
    OR_INT_2ADDR = new Dop(182, 150, 150, Form12x.THE_ONE, true);
    XOR_INT_2ADDR = new Dop(183, 151, 151, Form12x.THE_ONE, true);
    SHL_INT_2ADDR = new Dop(184, 152, 152, Form12x.THE_ONE, true);
    SHR_INT_2ADDR = new Dop(185, 153, 153, Form12x.THE_ONE, true);
    USHR_INT_2ADDR = new Dop(186, 154, 154, Form12x.THE_ONE, true);
    ADD_LONG_2ADDR = new Dop(187, 155, 155, Form12x.THE_ONE, true);
    SUB_LONG_2ADDR = new Dop(188, 156, 156, Form12x.THE_ONE, true);
    MUL_LONG_2ADDR = new Dop(189, 157, 157, Form12x.THE_ONE, true);
    DIV_LONG_2ADDR = new Dop(190, 158, 158, Form12x.THE_ONE, true);
    REM_LONG_2ADDR = new Dop(191, 159, 159, Form12x.THE_ONE, true);
    AND_LONG_2ADDR = new Dop(192, 160, 160, Form12x.THE_ONE, true);
    OR_LONG_2ADDR = new Dop(193, 161, 161, Form12x.THE_ONE, true);
    XOR_LONG_2ADDR = new Dop(194, 162, 162, Form12x.THE_ONE, true);
    SHL_LONG_2ADDR = new Dop(195, 163, 163, Form12x.THE_ONE, true);
    SHR_LONG_2ADDR = new Dop(196, 164, 164, Form12x.THE_ONE, true);
    USHR_LONG_2ADDR = new Dop(197, 165, 165, Form12x.THE_ONE, true);
    ADD_FLOAT_2ADDR = new Dop(198, 166, 166, Form12x.THE_ONE, true);
    SUB_FLOAT_2ADDR = new Dop(199, 167, 167, Form12x.THE_ONE, true);
    MUL_FLOAT_2ADDR = new Dop(200, 168, 168, Form12x.THE_ONE, true);
    DIV_FLOAT_2ADDR = new Dop(201, 169, 169, Form12x.THE_ONE, true);
    REM_FLOAT_2ADDR = new Dop(202, 170, 170, Form12x.THE_ONE, true);
    ADD_DOUBLE_2ADDR = new Dop(203, 171, 171, Form12x.THE_ONE, true);
    SUB_DOUBLE_2ADDR = new Dop(204, 172, 172, Form12x.THE_ONE, true);
    MUL_DOUBLE_2ADDR = new Dop(205, 173, 173, Form12x.THE_ONE, true);
    DIV_DOUBLE_2ADDR = new Dop(206, 174, 174, Form12x.THE_ONE, true);
    REM_DOUBLE_2ADDR = new Dop(207, 175, 175, Form12x.THE_ONE, true);
    ADD_INT_LIT16 = new Dop(208, 144, -1, Form22s.THE_ONE, true);
    RSUB_INT = new Dop(209, 209, -1, Form22s.THE_ONE, true);
    MUL_INT_LIT16 = new Dop(210, 146, -1, Form22s.THE_ONE, true);
    DIV_INT_LIT16 = new Dop(211, 147, -1, Form22s.THE_ONE, true);
    REM_INT_LIT16 = new Dop(212, 148, -1, Form22s.THE_ONE, true);
    AND_INT_LIT16 = new Dop(213, 149, -1, Form22s.THE_ONE, true);
    OR_INT_LIT16 = new Dop(214, 150, -1, Form22s.THE_ONE, true);
    XOR_INT_LIT16 = new Dop(215, 151, -1, Form22s.THE_ONE, true);
    ADD_INT_LIT8 = new Dop(216, 144, 208, Form22b.THE_ONE, true);
    RSUB_INT_LIT8 = new Dop(217, 209, 209, Form22b.THE_ONE, true);
    MUL_INT_LIT8 = new Dop(218, 146, 210, Form22b.THE_ONE, true);
    DIV_INT_LIT8 = new Dop(219, 147, 211, Form22b.THE_ONE, true);
    REM_INT_LIT8 = new Dop(220, 148, 212, Form22b.THE_ONE, true);
    AND_INT_LIT8 = new Dop(221, 149, 213, Form22b.THE_ONE, true);
    OR_INT_LIT8 = new Dop(222, 150, 214, Form22b.THE_ONE, true);
    XOR_INT_LIT8 = new Dop(223, 151, 215, Form22b.THE_ONE, true);
    SHL_INT_LIT8 = new Dop(224, 152, -1, Form22b.THE_ONE, true);
    SHR_INT_LIT8 = new Dop(225, 153, -1, Form22b.THE_ONE, true);
    USHR_INT_LIT8 = new Dop(226, 154, -1, Form22b.THE_ONE, true);
    INVOKE_POLYMORPHIC = new Dop(250, 250, 251, Form45cc.THE_ONE, false);
    INVOKE_POLYMORPHIC_RANGE = new Dop(251, 250, -1, Form4rcc.THE_ONE, false);
    INVOKE_CUSTOM = new Dop(252, 252, 253, Form35c.THE_ONE, false);
    INVOKE_CUSTOM_RANGE = new Dop(253, 252, -1, Form3rc.THE_ONE, false);
    CONST_METHOD_HANDLE = new Dop(254, 254, -1, Form21c.THE_ONE, true);
    CONST_METHOD_TYPE = new Dop(255, 255, -1, Form21c.THE_ONE, true);
    DOPS = new Dop[65537];
    set(SPECIAL_FORMAT);
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
  
  public static Dop get(int paramInt) {
    try {
      Dop dop = DOPS[paramInt + 1];
      if (dop != null)
        return dop; 
    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {}
    throw new IllegalArgumentException("bogus opcode");
  }
  
  public static Dop getNextOrNull(Dop paramDop, DexOptions paramDexOptions) {
    int i = paramDop.getNextOpcode();
    return (i == -1) ? null : get(i);
  }
  
  private static void set(Dop paramDop) {
    int i = paramDop.getOpcode();
    DOPS[i + 1] = paramDop;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\dex\code\Dops.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */