package com.android.dx.cf.iface;

import com.android.dx.rop.cst.CstNat;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.cst.CstType;

public interface Member extends HasAttribute {
  int getAccessFlags();
  
  AttributeList getAttributes();
  
  CstType getDefiningClass();
  
  CstString getDescriptor();
  
  CstString getName();
  
  CstNat getNat();
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\iface\Member.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */