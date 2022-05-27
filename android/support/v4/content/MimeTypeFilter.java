package android.support.v4.content;

import java.util.ArrayList;

public final class MimeTypeFilter {
  public static String matches(String paramString, String[] paramArrayOfString) {
    if (paramString == null)
      return null; 
    String[] arrayOfString = paramString.split("/");
    int i = paramArrayOfString.length;
    for (byte b = 0; b < i; b++) {
      paramString = paramArrayOfString[b];
      if (mimeTypeAgainstFilter(arrayOfString, paramString.split("/")))
        return paramString; 
    } 
    return null;
  }
  
  public static String matches(String[] paramArrayOfString, String paramString) {
    if (paramArrayOfString == null)
      return null; 
    String[] arrayOfString = paramString.split("/");
    int i = paramArrayOfString.length;
    for (byte b = 0; b < i; b++) {
      paramString = paramArrayOfString[b];
      if (mimeTypeAgainstFilter(paramString.split("/"), arrayOfString))
        return paramString; 
    } 
    return null;
  }
  
  public static boolean matches(String paramString1, String paramString2) {
    return (paramString1 == null) ? false : mimeTypeAgainstFilter(paramString1.split("/"), paramString2.split("/"));
  }
  
  public static String[] matchesMany(String[] paramArrayOfString, String paramString) {
    byte b = 0;
    if (paramArrayOfString == null)
      return new String[0]; 
    ArrayList<String> arrayList = new ArrayList();
    String[] arrayOfString = paramString.split("/");
    int i = paramArrayOfString.length;
    while (b < i) {
      String str = paramArrayOfString[b];
      if (mimeTypeAgainstFilter(str.split("/"), arrayOfString))
        arrayList.add(str); 
      b++;
    } 
    return arrayList.<String>toArray(new String[arrayList.size()]);
  }
  
  private static boolean mimeTypeAgainstFilter(String[] paramArrayOfString1, String[] paramArrayOfString2) {
    if (paramArrayOfString2.length == 2) {
      if (!paramArrayOfString2[0].isEmpty() && !paramArrayOfString2[1].isEmpty())
        return (paramArrayOfString1.length != 2) ? false : ((!"*".equals(paramArrayOfString2[0]) && !paramArrayOfString2[0].equals(paramArrayOfString1[0])) ? false : (!(!"*".equals(paramArrayOfString2[1]) && !paramArrayOfString2[1].equals(paramArrayOfString1[1])))); 
      throw new IllegalArgumentException("Ill-formatted MIME type filter. Type or subtype empty.");
    } 
    throw new IllegalArgumentException("Ill-formatted MIME type filter. Must be type/subtype.");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\content\MimeTypeFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */