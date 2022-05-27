package external.org.apache.commons.lang3;

public class CharSequenceUtils {
  static int indexOf(CharSequence paramCharSequence, int paramInt1, int paramInt2) {
    if (paramCharSequence instanceof String)
      return ((String)paramCharSequence).indexOf(paramInt1, paramInt2); 
    int i = paramCharSequence.length();
    int j = paramInt2;
    if (paramInt2 < 0)
      j = 0; 
    while (j < i) {
      if (paramCharSequence.charAt(j) == paramInt1)
        return j; 
      j++;
    } 
    return -1;
  }
  
  static int indexOf(CharSequence paramCharSequence1, CharSequence paramCharSequence2, int paramInt) {
    return paramCharSequence1.toString().indexOf(paramCharSequence2.toString(), paramInt);
  }
  
  static int lastIndexOf(CharSequence paramCharSequence, int paramInt1, int paramInt2) {
    if (paramCharSequence instanceof String)
      return ((String)paramCharSequence).lastIndexOf(paramInt1, paramInt2); 
    int i = paramCharSequence.length();
    if (paramInt2 < 0)
      return -1; 
    int j = paramInt2;
    if (paramInt2 >= i)
      j = i - 1; 
    while (j >= 0) {
      if (paramCharSequence.charAt(j) == paramInt1)
        return j; 
      j--;
    } 
    return -1;
  }
  
  static int lastIndexOf(CharSequence paramCharSequence1, CharSequence paramCharSequence2, int paramInt) {
    return paramCharSequence1.toString().lastIndexOf(paramCharSequence2.toString(), paramInt);
  }
  
  static boolean regionMatches(CharSequence paramCharSequence1, boolean paramBoolean, int paramInt1, CharSequence paramCharSequence2, int paramInt2, int paramInt3) {
    return (paramCharSequence1 instanceof String && paramCharSequence2 instanceof String) ? ((String)paramCharSequence1).regionMatches(paramBoolean, paramInt1, (String)paramCharSequence2, paramInt2, paramInt3) : paramCharSequence1.toString().regionMatches(paramBoolean, paramInt1, paramCharSequence2.toString(), paramInt2, paramInt3);
  }
  
  public static CharSequence subSequence(CharSequence paramCharSequence, int paramInt) {
    if (paramCharSequence == null) {
      paramCharSequence = null;
    } else {
      paramCharSequence = paramCharSequence.subSequence(paramInt, paramCharSequence.length());
    } 
    return paramCharSequence;
  }
  
  static char[] toCharArray(CharSequence paramCharSequence) {
    if (paramCharSequence instanceof String)
      return ((String)paramCharSequence).toCharArray(); 
    int i = paramCharSequence.length();
    char[] arrayOfChar = new char[paramCharSequence.length()];
    for (byte b = 0; b < i; b++)
      arrayOfChar[b] = paramCharSequence.charAt(b); 
    return arrayOfChar;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\external\org\apache\commons\lang3\CharSequenceUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */