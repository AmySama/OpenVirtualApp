package com.android.dx.rop.cst;

import com.android.dx.cf.code.BootstrapMethodArgumentsList;
import com.android.dx.rop.type.Prototype;

public final class CstCallSite extends CstArray {
  private CstCallSite(CstArray.List paramList) {
    super(paramList);
  }
  
  public static CstCallSite make(CstMethodHandle paramCstMethodHandle, CstNat paramCstNat, BootstrapMethodArgumentsList paramBootstrapMethodArgumentsList) {
    if (paramCstMethodHandle != null) {
      if (paramCstNat != null) {
        CstArray.List list = new CstArray.List(paramBootstrapMethodArgumentsList.size() + 3);
        byte b = 0;
        list.set(0, paramCstMethodHandle);
        list.set(1, paramCstNat.getName());
        list.set(2, new CstProtoRef(Prototype.fromDescriptor(paramCstNat.getDescriptor().getString())));
        if (paramBootstrapMethodArgumentsList != null)
          while (b < paramBootstrapMethodArgumentsList.size()) {
            list.set(b + 3, paramBootstrapMethodArgumentsList.get(b));
            b++;
          }  
        list.setImmutable();
        return new CstCallSite(list);
      } 
      throw new NullPointerException("nat == null");
    } 
    throw new NullPointerException("bootstrapMethodHandle == null");
  }
  
  protected int compareTo0(Constant paramConstant) {
    return getList().compareTo(((CstCallSite)paramConstant).getList());
  }
  
  public boolean equals(Object paramObject) {
    return (paramObject instanceof CstCallSite) ? getList().equals(((CstCallSite)paramObject).getList()) : false;
  }
  
  public int hashCode() {
    return getList().hashCode();
  }
  
  public boolean isCategory2() {
    return false;
  }
  
  public String toHuman() {
    return getList().toHuman("{", ", ", "}");
  }
  
  public String toString() {
    return getList().toString("call site{", ", ", "}");
  }
  
  public String typeName() {
    return "call site";
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\CstCallSite.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */