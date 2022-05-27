package com.android.dx.rop.cst;

import com.android.dx.rop.type.Prototype;
import com.android.dx.rop.type.Type;
import java.util.ArrayList;
import java.util.List;

public final class CstInvokeDynamic extends Constant {
  private final int bootstrapMethodIndex;
  
  private CstCallSite callSite;
  
  private CstType declaringClass;
  
  private final CstNat nat;
  
  private final Prototype prototype;
  
  private final List<CstCallSiteRef> references;
  
  private CstInvokeDynamic(int paramInt, CstNat paramCstNat) {
    this.bootstrapMethodIndex = paramInt;
    this.nat = paramCstNat;
    this.prototype = Prototype.fromDescriptor(paramCstNat.getDescriptor().toHuman());
    this.references = new ArrayList<CstCallSiteRef>();
  }
  
  public static CstInvokeDynamic make(int paramInt, CstNat paramCstNat) {
    return new CstInvokeDynamic(paramInt, paramCstNat);
  }
  
  public CstCallSiteRef addReference() {
    CstCallSiteRef cstCallSiteRef = new CstCallSiteRef(this, this.references.size());
    this.references.add(cstCallSiteRef);
    return cstCallSiteRef;
  }
  
  protected int compareTo0(Constant paramConstant) {
    paramConstant = paramConstant;
    int i = Integer.compare(this.bootstrapMethodIndex, paramConstant.getBootstrapMethodIndex());
    if (i != 0)
      return i; 
    i = this.nat.compareTo(paramConstant.getNat());
    if (i != 0)
      return i; 
    i = this.declaringClass.compareTo(paramConstant.getDeclaringClass());
    return (i != 0) ? i : this.callSite.compareTo(paramConstant.getCallSite());
  }
  
  public int getBootstrapMethodIndex() {
    return this.bootstrapMethodIndex;
  }
  
  public CstCallSite getCallSite() {
    return this.callSite;
  }
  
  public CstType getDeclaringClass() {
    return this.declaringClass;
  }
  
  public CstNat getNat() {
    return this.nat;
  }
  
  public Prototype getPrototype() {
    return this.prototype;
  }
  
  public List<CstCallSiteRef> getReferences() {
    return this.references;
  }
  
  public Type getReturnType() {
    return this.prototype.getReturnType();
  }
  
  public boolean isCategory2() {
    return false;
  }
  
  public void setCallSite(CstCallSite paramCstCallSite) {
    if (this.callSite == null) {
      if (paramCstCallSite != null) {
        this.callSite = paramCstCallSite;
        return;
      } 
      throw new NullPointerException("callSite == null");
    } 
    throw new IllegalArgumentException("already added call site");
  }
  
  public void setDeclaringClass(CstType paramCstType) {
    if (this.declaringClass == null) {
      if (paramCstType != null) {
        this.declaringClass = paramCstType;
        return;
      } 
      throw new NullPointerException("declaringClass == null");
    } 
    throw new IllegalArgumentException("already added declaring class");
  }
  
  public String toHuman() {
    String str;
    CstType cstType = this.declaringClass;
    if (cstType != null) {
      str = cstType.toHuman();
    } else {
      str = "Unknown";
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("InvokeDynamic(");
    stringBuilder.append(str);
    stringBuilder.append(":");
    stringBuilder.append(this.bootstrapMethodIndex);
    stringBuilder.append(", ");
    stringBuilder.append(this.nat.toHuman());
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
  
  public String toString() {
    return toHuman();
  }
  
  public String typeName() {
    return "InvokeDynamic";
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\CstInvokeDynamic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */