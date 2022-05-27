package com.android.dx.cf.iface;

import com.android.dx.cf.attrib.AttConstantValue;
import com.android.dx.rop.cst.CstNat;
import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.cst.TypedConstant;

public final class StdField extends StdMember implements Field {
  public StdField(CstType paramCstType, int paramInt, CstNat paramCstNat, AttributeList paramAttributeList) {
    super(paramCstType, paramInt, paramCstNat, paramAttributeList);
  }
  
  public TypedConstant getConstantValue() {
    AttConstantValue attConstantValue = (AttConstantValue)getAttributes().findFirst("ConstantValue");
    return (attConstantValue == null) ? null : attConstantValue.getConstantValue();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\iface\StdField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */