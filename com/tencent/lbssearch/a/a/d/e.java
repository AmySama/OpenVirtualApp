package com.tencent.lbssearch.a.a.d;

final class e {
  private final String[] a = new String[1024];
  
  public String a(char[] paramArrayOfchar, int paramInt1, int paramInt2, int paramInt3) {
    String str1;
    if (paramInt2 > 20)
      return new String(paramArrayOfchar, paramInt1, paramInt2); 
    paramInt3 ^= paramInt3 >>> 20 ^ paramInt3 >>> 12;
    String[] arrayOfString = this.a;
    int i = (paramInt3 ^ paramInt3 >>> 7 ^ paramInt3 >>> 4) & arrayOfString.length - 1;
    String str2 = arrayOfString[i];
    if (str2 == null || str2.length() != paramInt2) {
      str1 = new String(paramArrayOfchar, paramInt1, paramInt2);
      this.a[i] = str1;
      return str1;
    } 
    for (paramInt3 = 0; paramInt3 < paramInt2; paramInt3++) {
      if (str2.charAt(paramInt3) != str1[paramInt1 + paramInt3]) {
        str1 = new String((char[])str1, paramInt1, paramInt2);
        this.a[i] = str1;
        return str1;
      } 
    } 
    return str2;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\d\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */