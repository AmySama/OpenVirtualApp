package android.support.v4.text;

import android.os.Build;
import android.text.TextUtils;
import java.util.Locale;

public final class TextUtilsCompat {
  private static final String ARAB_SCRIPT_SUBTAG = "Arab";
  
  private static final String HEBR_SCRIPT_SUBTAG = "Hebr";
  
  private static final Locale ROOT = new Locale("", "");
  
  private static int getLayoutDirectionFromFirstChar(Locale paramLocale) {
    byte b = Character.getDirectionality(paramLocale.getDisplayName(paramLocale).charAt(0));
    return (b != 1 && b != 2) ? 0 : 1;
  }
  
  public static int getLayoutDirectionFromLocale(Locale paramLocale) {
    if (Build.VERSION.SDK_INT >= 17)
      return TextUtils.getLayoutDirectionFromLocale(paramLocale); 
    if (paramLocale != null && !paramLocale.equals(ROOT)) {
      String str = ICUCompat.maximizeAndGetScript(paramLocale);
      if (str == null)
        return getLayoutDirectionFromFirstChar(paramLocale); 
      if (str.equalsIgnoreCase("Arab") || str.equalsIgnoreCase("Hebr"))
        return 1; 
    } 
    return 0;
  }
  
  public static String htmlEncode(String paramString) {
    if (Build.VERSION.SDK_INT >= 17)
      return TextUtils.htmlEncode(paramString); 
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b = 0; b < paramString.length(); b++) {
      char c = paramString.charAt(b);
      if (c != '"') {
        if (c != '<') {
          if (c != '>') {
            if (c != '&') {
              if (c != '\'') {
                stringBuilder.append(c);
              } else {
                stringBuilder.append("&#39;");
              } 
            } else {
              stringBuilder.append("&amp;");
            } 
          } else {
            stringBuilder.append("&gt;");
          } 
        } else {
          stringBuilder.append("&lt;");
        } 
      } else {
        stringBuilder.append("&quot;");
      } 
    } 
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\text\TextUtilsCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */