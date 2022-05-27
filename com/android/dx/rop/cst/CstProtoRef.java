package com.android.dx.rop.cst;

import com.android.dx.rop.type.Prototype;
import com.android.dx.rop.type.Type;

public final class CstProtoRef extends TypedConstant {
  private final Prototype prototype;
  
  public CstProtoRef(Prototype paramPrototype) {
    this.prototype = paramPrototype;
  }
  
  public static CstProtoRef make(CstString paramCstString) {
    return new CstProtoRef(Prototype.fromDescriptor(paramCstString.getString()));
  }
  
  protected int compareTo0(Constant paramConstant) {
    paramConstant = paramConstant;
    return this.prototype.compareTo(paramConstant.getPrototype());
  }
  
  public boolean equals(Object paramObject) {
    if (!(paramObject instanceof CstProtoRef))
      return false; 
    paramObject = paramObject;
    return getPrototype().equals(paramObject.getPrototype());
  }
  
  public Prototype getPrototype() {
    return this.prototype;
  }
  
  public Type getType() {
    return Type.METHOD_TYPE;
  }
  
  public int hashCode() {
    return this.prototype.hashCode();
  }
  
  public boolean isCategory2() {
    return false;
  }
  
  public String toHuman() {
    return this.prototype.getDescriptor();
  }
  
  public final String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(typeName());
    stringBuilder.append("{");
    stringBuilder.append(toHuman());
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  public String typeName() {
    return "proto";
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\CstProtoRef.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */