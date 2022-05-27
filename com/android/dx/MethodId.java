package com.android.dx;

import com.android.dx.rop.cst.CstMethodRef;
import com.android.dx.rop.cst.CstNat;
import com.android.dx.rop.cst.CstString;
import com.android.dx.rop.type.Prototype;
import java.util.List;

public final class MethodId<D, R> {
  final CstMethodRef constant;
  
  final TypeId<D> declaringType;
  
  final String name;
  
  final CstNat nat;
  
  final TypeList parameters;
  
  final TypeId<R> returnType;
  
  MethodId(TypeId<D> paramTypeId, TypeId<R> paramTypeId1, String paramString, TypeList paramTypeList) {
    if (paramTypeId != null && paramTypeId1 != null && paramString != null && paramTypeList != null) {
      this.declaringType = paramTypeId;
      this.returnType = paramTypeId1;
      this.name = paramString;
      this.parameters = paramTypeList;
      this.nat = new CstNat(new CstString(paramString), new CstString(descriptor(false)));
      this.constant = new CstMethodRef(paramTypeId.constant, this.nat);
      return;
    } 
    throw null;
  }
  
  String descriptor(boolean paramBoolean) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("(");
    if (paramBoolean)
      stringBuilder.append(this.declaringType.name); 
    TypeId<?>[] arrayOfTypeId = this.parameters.types;
    int i = arrayOfTypeId.length;
    for (byte b = 0; b < i; b++)
      stringBuilder.append((arrayOfTypeId[b]).name); 
    stringBuilder.append(")");
    stringBuilder.append(this.returnType.name);
    return stringBuilder.toString();
  }
  
  public boolean equals(Object paramObject) {
    if (paramObject instanceof MethodId) {
      paramObject = paramObject;
      if (((MethodId)paramObject).declaringType.equals(this.declaringType) && ((MethodId)paramObject).name.equals(this.name) && ((MethodId)paramObject).parameters.equals(this.parameters) && ((MethodId)paramObject).returnType.equals(this.returnType))
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
  
  public List<TypeId<?>> getParameters() {
    return this.parameters.asList();
  }
  
  public TypeId<R> getReturnType() {
    return this.returnType;
  }
  
  public int hashCode() {
    return (((527 + this.declaringType.hashCode()) * 31 + this.name.hashCode()) * 31 + this.parameters.hashCode()) * 31 + this.returnType.hashCode();
  }
  
  public boolean isConstructor() {
    return this.name.equals("<init>");
  }
  
  public boolean isStaticInitializer() {
    return this.name.equals("<clinit>");
  }
  
  Prototype prototype(boolean paramBoolean) {
    return Prototype.intern(descriptor(paramBoolean));
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.declaringType);
    stringBuilder.append(".");
    stringBuilder.append(this.name);
    stringBuilder.append("(");
    stringBuilder.append(this.parameters);
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\MethodId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */