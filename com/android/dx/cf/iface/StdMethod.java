package com.android.dx.cf.iface;

import com.android.dx.rop.code.AccessFlags;
import com.android.dx.rop.cst.CstNat;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.type.Prototype;

public final class StdMethod extends StdMember implements Method {
  private final Prototype effectiveDescriptor;
  
  public StdMethod(CstType paramCstType, int paramInt, CstNat paramCstNat, AttributeList paramAttributeList) {
    super(paramCstType, paramInt, paramCstNat, paramAttributeList);
    this.effectiveDescriptor = Prototype.intern(getDescriptor().getString(), paramCstType.getClassType(), AccessFlags.isStatic(paramInt), paramCstNat.isInstanceInit());
  }
  
  public Prototype getEffectiveDescriptor() {
    return this.effectiveDescriptor;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\iface\StdMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */