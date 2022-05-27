package com.android.dx;

import com.android.dx.rop.code.Rop;
import com.android.dx.rop.code.Rops;
import com.android.dx.rop.type.TypeList;

public enum BinaryOp {
  ADD {
    Rop rop(TypeList param1TypeList) {
      return Rops.opAdd(param1TypeList);
    }
  },
  AND,
  DIVIDE,
  MULTIPLY,
  OR,
  REMAINDER,
  SHIFT_LEFT,
  SHIFT_RIGHT,
  SUBTRACT {
    Rop rop(TypeList param1TypeList) {
      return Rops.opSub(param1TypeList);
    }
  },
  UNSIGNED_SHIFT_RIGHT,
  XOR;
  
  static {
    MULTIPLY = new null("MULTIPLY", 2);
    DIVIDE = new null("DIVIDE", 3);
    REMAINDER = new null("REMAINDER", 4);
    AND = new null("AND", 5);
    OR = new null("OR", 6);
    XOR = new null("XOR", 7);
    SHIFT_LEFT = new null("SHIFT_LEFT", 8);
    SHIFT_RIGHT = new null("SHIFT_RIGHT", 9);
    null  = new null("UNSIGNED_SHIFT_RIGHT", 10);
    UNSIGNED_SHIFT_RIGHT = ;
    $VALUES = new BinaryOp[] { 
        ADD, SUBTRACT, MULTIPLY, DIVIDE, REMAINDER, AND, OR, XOR, SHIFT_LEFT, SHIFT_RIGHT, 
         };
  }
  
  abstract Rop rop(TypeList paramTypeList);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\BinaryOp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */