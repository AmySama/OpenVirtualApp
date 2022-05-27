package com.android.dx.rop.cst;

import com.android.dx.rop.type.Type;

public final class CstNat extends Constant {
  public static final CstNat PRIMITIVE_TYPE_NAT = new CstNat(new CstString("TYPE"), new CstString("Ljava/lang/Class;"));
  
  private final CstString descriptor;
  
  private final CstString name;
  
  public CstNat(CstString paramCstString1, CstString paramCstString2) {
    if (paramCstString1 != null) {
      if (paramCstString2 != null) {
        this.name = paramCstString1;
        this.descriptor = paramCstString2;
        return;
      } 
      throw new NullPointerException("descriptor == null");
    } 
    throw new NullPointerException("name == null");
  }
  
  protected int compareTo0(Constant paramConstant) {
    paramConstant = paramConstant;
    int i = this.name.compareTo(((CstNat)paramConstant).name);
    return (i != 0) ? i : this.descriptor.compareTo(((CstNat)paramConstant).descriptor);
  }
  
  public boolean equals(Object paramObject) {
    boolean bool = paramObject instanceof CstNat;
    boolean bool1 = false;
    if (!bool)
      return false; 
    paramObject = paramObject;
    bool = bool1;
    if (this.name.equals(((CstNat)paramObject).name)) {
      bool = bool1;
      if (this.descriptor.equals(((CstNat)paramObject).descriptor))
        bool = true; 
    } 
    return bool;
  }
  
  public CstString getDescriptor() {
    return this.descriptor;
  }
  
  public Type getFieldType() {
    return Type.intern(this.descriptor.getString());
  }
  
  public CstString getName() {
    return this.name;
  }
  
  public int hashCode() {
    return this.name.hashCode() * 31 ^ this.descriptor.hashCode();
  }
  
  public boolean isCategory2() {
    return false;
  }
  
  public final boolean isClassInit() {
    return this.name.getString().equals("<clinit>");
  }
  
  public final boolean isInstanceInit() {
    return this.name.getString().equals("<init>");
  }
  
  public String toHuman() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.name.toHuman());
    stringBuilder.append(':');
    stringBuilder.append(this.descriptor.toHuman());
    return stringBuilder.toString();
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("nat{");
    stringBuilder.append(toHuman());
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  public String typeName() {
    return "nat";
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\CstNat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */