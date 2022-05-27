package com.android.dx.cf.iface;

import com.android.dx.rop.cst.CstNat;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.cst.CstType;

public abstract class StdMember implements Member {
  private final int accessFlags;
  
  private final AttributeList attributes;
  
  private final CstType definingClass;
  
  private final CstNat nat;
  
  public StdMember(CstType paramCstType, int paramInt, CstNat paramCstNat, AttributeList paramAttributeList) {
    if (paramCstType != null) {
      if (paramCstNat != null) {
        if (paramAttributeList != null) {
          this.definingClass = paramCstType;
          this.accessFlags = paramInt;
          this.nat = paramCstNat;
          this.attributes = paramAttributeList;
          return;
        } 
        throw new NullPointerException("attributes == null");
      } 
      throw new NullPointerException("nat == null");
    } 
    throw new NullPointerException("definingClass == null");
  }
  
  public final int getAccessFlags() {
    return this.accessFlags;
  }
  
  public final AttributeList getAttributes() {
    return this.attributes;
  }
  
  public final CstType getDefiningClass() {
    return this.definingClass;
  }
  
  public final CstString getDescriptor() {
    return this.nat.getDescriptor();
  }
  
  public final CstString getName() {
    return this.nat.getName();
  }
  
  public final CstNat getNat() {
    return this.nat;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(100);
    stringBuilder.append(getClass().getName());
    stringBuilder.append('{');
    stringBuilder.append(this.nat.toHuman());
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\cf\iface\StdMember.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */