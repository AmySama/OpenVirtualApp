package com.android.dx.rop.cst;

public abstract class CstMemberRef extends TypedConstant {
  private final CstType definingClass;
  
  private final CstNat nat;
  
  CstMemberRef(CstType paramCstType, CstNat paramCstNat) {
    if (paramCstType != null) {
      if (paramCstNat != null) {
        this.definingClass = paramCstType;
        this.nat = paramCstNat;
        return;
      } 
      throw new NullPointerException("nat == null");
    } 
    throw new NullPointerException("definingClass == null");
  }
  
  protected int compareTo0(Constant paramConstant) {
    paramConstant = paramConstant;
    int i = this.definingClass.compareTo(((CstMemberRef)paramConstant).definingClass);
    return (i != 0) ? i : this.nat.getName().compareTo(((CstMemberRef)paramConstant).nat.getName());
  }
  
  public final boolean equals(Object paramObject) {
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (paramObject != null)
      if (getClass() != paramObject.getClass()) {
        bool2 = bool1;
      } else {
        paramObject = paramObject;
        bool2 = bool1;
        if (this.definingClass.equals(((CstMemberRef)paramObject).definingClass)) {
          bool2 = bool1;
          if (this.nat.equals(((CstMemberRef)paramObject).nat))
            bool2 = true; 
        } 
      }  
    return bool2;
  }
  
  public final CstType getDefiningClass() {
    return this.definingClass;
  }
  
  public final CstNat getNat() {
    return this.nat;
  }
  
  public final int hashCode() {
    return this.definingClass.hashCode() * 31 ^ this.nat.hashCode();
  }
  
  public final boolean isCategory2() {
    return false;
  }
  
  public final String toHuman() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.definingClass.toHuman());
    stringBuilder.append('.');
    stringBuilder.append(this.nat.toHuman());
    return stringBuilder.toString();
  }
  
  public final String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(typeName());
    stringBuilder.append('{');
    stringBuilder.append(toHuman());
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\CstMemberRef.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */