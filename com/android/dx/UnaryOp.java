package com.android.dx;

import com.android.dx.rop.code.Rop;
import com.android.dx.rop.code.Rops;
import com.android.dx.rop.type.TypeBearer;

public enum UnaryOp {
  NEGATE,
  NOT {
    Rop rop(TypeId<?> param1TypeId) {
      return Rops.opNot((TypeBearer)param1TypeId.ropType);
    }
  };
  
  static {
    null  = new null("NEGATE", 1);
    NEGATE = ;
    $VALUES = new UnaryOp[] { NOT,  };
  }
  
  abstract Rop rop(TypeId<?> paramTypeId);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\UnaryOp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */