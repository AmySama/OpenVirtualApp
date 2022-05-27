package com.android.dx;

import com.android.dx.rop.code.Rop;
import com.android.dx.rop.code.Rops;
import com.android.dx.rop.type.TypeList;

public enum Comparison {
  EQ,
  GE,
  GT,
  LE,
  LT {
    Rop rop(TypeList param1TypeList) {
      return Rops.opIfLt(param1TypeList);
    }
  },
  NE;
  
  static {
    LE = new null("LE", 1);
    EQ = new null("EQ", 2);
    GE = new null("GE", 3);
    GT = new null("GT", 4);
    null  = new null("NE", 5);
    NE = ;
    $VALUES = new Comparison[] { LT, LE, EQ, GE, GT,  };
  }
  
  abstract Rop rop(TypeList paramTypeList);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\Comparison.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */