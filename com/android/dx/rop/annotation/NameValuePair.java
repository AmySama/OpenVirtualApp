package com.android.dx.rop.annotation;

import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstString;

public final class NameValuePair implements Comparable<NameValuePair> {
  private final CstString name;
  
  private final Constant value;
  
  public NameValuePair(CstString paramCstString, Constant paramConstant) {
    if (paramCstString != null) {
      if (paramConstant != null) {
        this.name = paramCstString;
        this.value = paramConstant;
        return;
      } 
      throw new NullPointerException("value == null");
    } 
    throw new NullPointerException("name == null");
  }
  
  public int compareTo(NameValuePair paramNameValuePair) {
    int i = this.name.compareTo((Constant)paramNameValuePair.name);
    return (i != 0) ? i : this.value.compareTo(paramNameValuePair.value);
  }
  
  public boolean equals(Object paramObject) {
    boolean bool = paramObject instanceof NameValuePair;
    boolean bool1 = false;
    if (!bool)
      return false; 
    paramObject = paramObject;
    bool = bool1;
    if (this.name.equals(((NameValuePair)paramObject).name)) {
      bool = bool1;
      if (this.value.equals(((NameValuePair)paramObject).value))
        bool = true; 
    } 
    return bool;
  }
  
  public CstString getName() {
    return this.name;
  }
  
  public Constant getValue() {
    return this.value;
  }
  
  public int hashCode() {
    return this.name.hashCode() * 31 + this.value.hashCode();
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.name.toHuman());
    stringBuilder.append(":");
    stringBuilder.append(this.value);
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\annotation\NameValuePair.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */