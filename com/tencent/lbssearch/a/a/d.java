package com.tencent.lbssearch.a.a;

import java.lang.reflect.Field;

public enum d implements e {
  a {
    public String a(Field param1Field) {
      return param1Field.getName();
    }
  },
  b {
    public String a(Field param1Field) {
      return a(param1Field.getName());
    }
  },
  c {
    public String a(Field param1Field) {
      return a(a(param1Field.getName(), " "));
    }
  },
  d {
    public String a(Field param1Field) {
      return a(param1Field.getName(), "_").toLowerCase();
    }
  },
  e;
  
  static {
    null  = new null("LOWER_CASE_WITH_DASHES", 4);
    e = ;
    f = new d[] { a, b, c, d,  };
  }
  
  private static String a(char paramChar, String paramString, int paramInt) {
    if (paramInt < paramString.length()) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramChar);
      stringBuilder.append(paramString.substring(paramInt));
      paramString = stringBuilder.toString();
    } else {
      paramString = String.valueOf(paramChar);
    } 
    return paramString;
  }
  
  private static String b(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    byte b = 0;
    char c = paramString.charAt(0);
    char c1;
    for (c1 = c; b < paramString.length() - 1 && !Character.isLetter(c1); c1 = c) {
      stringBuilder.append(c1);
      c = paramString.charAt(++b);
    } 
    if (b == paramString.length())
      return stringBuilder.toString(); 
    String str = paramString;
    if (!Character.isUpperCase(c1)) {
      stringBuilder.append(a(Character.toUpperCase(c1), paramString, b + 1));
      str = stringBuilder.toString();
    } 
    return str;
  }
  
  private static String b(String paramString1, String paramString2) {
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b = 0; b < paramString1.length(); b++) {
      char c = paramString1.charAt(b);
      if (Character.isUpperCase(c) && stringBuilder.length() != 0)
        stringBuilder.append(paramString2); 
      stringBuilder.append(c);
    } 
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */