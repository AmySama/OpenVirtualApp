package com.android.dx.cf.code;

import com.android.dx.rop.cst.Constant;
import com.android.dx.util.FixedSizeList;

public class BootstrapMethodArgumentsList extends FixedSizeList {
  public BootstrapMethodArgumentsList(int paramInt) {
    super(paramInt);
  }
  
  public Constant get(int paramInt) {
    return (Constant)get0(paramInt);
  }
  
  public void set(int paramInt, Constant paramConstant) {
    if (paramConstant instanceof com.android.dx.rop.cst.CstString || paramConstant instanceof com.android.dx.rop.cst.CstType || paramConstant instanceof com.android.dx.rop.cst.CstInteger || paramConstant instanceof com.android.dx.rop.cst.CstLong || paramConstant instanceof com.android.dx.rop.cst.CstFloat || paramConstant instanceof com.android.dx.rop.cst.CstDouble || paramConstant instanceof com.android.dx.rop.cst.CstMethodHandle || paramConstant instanceof com.android.dx.rop.cst.CstProtoRef) {
      set0(paramInt, paramConstant);
      return;
    } 
    Class<?> clazz = paramConstant.getClass();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("bad type for bootstrap argument: ");
    stringBuilder.append(clazz);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\code\BootstrapMethodArgumentsList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */