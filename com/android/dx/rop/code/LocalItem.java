package com.android.dx.rop.code;

import com.android.dx.rop.cst.Constant;
import com.android.dx.rop.cst.CstString;

public class LocalItem implements Comparable<LocalItem> {
  private final CstString name;
  
  private final CstString signature;
  
  private LocalItem(CstString paramCstString1, CstString paramCstString2) {
    this.name = paramCstString1;
    this.signature = paramCstString2;
  }
  
  private static int compareHandlesNulls(CstString paramCstString1, CstString paramCstString2) {
    return (paramCstString1 == paramCstString2) ? 0 : ((paramCstString1 == null) ? -1 : ((paramCstString2 == null) ? 1 : paramCstString1.compareTo((Constant)paramCstString2)));
  }
  
  public static LocalItem make(CstString paramCstString1, CstString paramCstString2) {
    return (paramCstString1 == null && paramCstString2 == null) ? null : new LocalItem(paramCstString1, paramCstString2);
  }
  
  public int compareTo(LocalItem paramLocalItem) {
    int i = compareHandlesNulls(this.name, paramLocalItem.name);
    return (i != 0) ? i : compareHandlesNulls(this.signature, paramLocalItem.signature);
  }
  
  public boolean equals(Object paramObject) {
    boolean bool = paramObject instanceof LocalItem;
    boolean bool1 = false;
    if (!bool)
      return false; 
    if (compareTo((LocalItem)paramObject) == 0)
      bool1 = true; 
    return bool1;
  }
  
  public CstString getName() {
    return this.name;
  }
  
  public CstString getSignature() {
    return this.signature;
  }
  
  public int hashCode() {
    int j;
    CstString cstString = this.name;
    int i = 0;
    if (cstString == null) {
      j = 0;
    } else {
      j = cstString.hashCode();
    } 
    cstString = this.signature;
    if (cstString != null)
      i = cstString.hashCode(); 
    return j * 31 + i;
  }
  
  public String toString() {
    String str2;
    String str1;
    CstString cstString2 = this.name;
    if (cstString2 != null && this.signature == null)
      return cstString2.toQuoted(); 
    cstString2 = this.name;
    String str3 = "";
    if (cstString2 == null && this.signature == null)
      return ""; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("[");
    cstString2 = this.name;
    if (cstString2 == null) {
      str2 = "";
    } else {
      str2 = str2.toQuoted();
    } 
    stringBuilder.append(str2);
    stringBuilder.append("|");
    CstString cstString1 = this.signature;
    if (cstString1 == null) {
      str1 = str3;
    } else {
      str1 = str1.toQuoted();
    } 
    stringBuilder.append(str1);
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\code\LocalItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */