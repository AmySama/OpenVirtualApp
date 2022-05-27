package com.android.dx.rop.cst;

import com.android.dx.rop.type.Prototype;
import com.android.dx.rop.type.Type;

public class CstCallSiteRef extends Constant {
  private final int id;
  
  private final CstInvokeDynamic invokeDynamic;
  
  CstCallSiteRef(CstInvokeDynamic paramCstInvokeDynamic, int paramInt) {
    if (paramCstInvokeDynamic != null) {
      this.invokeDynamic = paramCstInvokeDynamic;
      this.id = paramInt;
      return;
    } 
    throw new NullPointerException("invokeDynamic == null");
  }
  
  protected int compareTo0(Constant paramConstant) {
    paramConstant = paramConstant;
    int i = this.invokeDynamic.compareTo(((CstCallSiteRef)paramConstant).invokeDynamic);
    return (i != 0) ? i : Integer.compare(this.id, ((CstCallSiteRef)paramConstant).id);
  }
  
  public CstCallSite getCallSite() {
    return this.invokeDynamic.getCallSite();
  }
  
  public Prototype getPrototype() {
    return this.invokeDynamic.getPrototype();
  }
  
  public Type getReturnType() {
    return this.invokeDynamic.getReturnType();
  }
  
  public boolean isCategory2() {
    return false;
  }
  
  public String toHuman() {
    return getCallSite().toHuman();
  }
  
  public String toString() {
    return getCallSite().toString();
  }
  
  public String typeName() {
    return "CallSiteRef";
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\CstCallSiteRef.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */