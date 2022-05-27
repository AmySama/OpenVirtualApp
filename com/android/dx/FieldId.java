package com.android.dx;

import com.android.dx.rop.cst.CstFieldRef;
import com.android.dx.rop.cst.CstNat;
import com.android.dx.rop.cst.CstString;

public final class FieldId<D, V> {
  final CstFieldRef constant;
  
  final TypeId<D> declaringType;
  
  final String name;
  
  final CstNat nat;
  
  final TypeId<V> type;
  
  FieldId(TypeId<D> paramTypeId, TypeId<V> paramTypeId1, String paramString) {
    if (paramTypeId != null && paramTypeId1 != null && paramString != null) {
      this.declaringType = paramTypeId;
      this.type = paramTypeId1;
      this.name = paramString;
      this.nat = new CstNat(new CstString(paramString), new CstString(paramTypeId1.name));
      this.constant = new CstFieldRef(paramTypeId.constant, this.nat);
      return;
    } 
    throw null;
  }
  
  public boolean equals(Object paramObject) {
    if (paramObject instanceof FieldId) {
      paramObject = paramObject;
      if (((FieldId)paramObject).declaringType.equals(this.declaringType) && ((FieldId)paramObject).name.equals(this.name))
        return true; 
    } 
    return false;
  }
  
  public TypeId<D> getDeclaringType() {
    return this.declaringType;
  }
  
  public String getName() {
    return this.name;
  }
  
  public TypeId<V> getType() {
    return this.type;
  }
  
  public int hashCode() {
    return this.declaringType.hashCode() + this.name.hashCode() * 37;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.declaringType);
    stringBuilder.append(".");
    stringBuilder.append(this.name);
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\FieldId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */