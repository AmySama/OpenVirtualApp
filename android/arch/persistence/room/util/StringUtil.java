package android.arch.persistence.room.util;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StringUtil {
  public static final String[] EMPTY_STRING_ARRAY = new String[0];
  
  public static void appendPlaceholders(StringBuilder paramStringBuilder, int paramInt) {
    for (byte b = 0; b < paramInt; b++) {
      paramStringBuilder.append("?");
      if (b < paramInt - 1)
        paramStringBuilder.append(","); 
    } 
  }
  
  public static String joinIntoString(List<Integer> paramList) {
    if (paramList == null)
      return null; 
    int i = paramList.size();
    if (i == 0)
      return ""; 
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b = 0; b < i; b++) {
      stringBuilder.append(Integer.toString(((Integer)paramList.get(b)).intValue()));
      if (b < i - 1)
        stringBuilder.append(","); 
    } 
    return stringBuilder.toString();
  }
  
  public static StringBuilder newStringBuilder() {
    return new StringBuilder();
  }
  
  public static List<Integer> splitToIntList(String paramString) {
    if (paramString == null)
      return null; 
    ArrayList<Integer> arrayList = new ArrayList();
    StringTokenizer stringTokenizer = new StringTokenizer(paramString, ",");
    while (stringTokenizer.hasMoreElements()) {
      String str = stringTokenizer.nextToken();
      try {
        arrayList.add(Integer.valueOf(Integer.parseInt(str)));
      } catch (NumberFormatException numberFormatException) {
        Log.e("ROOM", "Malformed integer list", numberFormatException);
      } 
    } 
    return arrayList;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\persistence\roo\\util\StringUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */