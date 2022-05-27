package android.support.v4.text;

import android.os.Build;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

public final class ICUCompat {
  private static final String TAG = "ICUCompat";
  
  private static Method sAddLikelySubtagsMethod;
  
  private static Method sGetScriptMethod;
  
  static {
    if (Build.VERSION.SDK_INT >= 21) {
      try {
        sAddLikelySubtagsMethod = Class.forName("libcore.icu.ICU").getMethod("addLikelySubtags", new Class[] { Locale.class });
      } catch (Exception exception) {
        throw new IllegalStateException(exception);
      } 
    } else {
      try {
        Class<?> clazz = Class.forName("libcore.icu.ICU");
        if (clazz != null) {
          sGetScriptMethod = clazz.getMethod("getScript", new Class[] { String.class });
          sAddLikelySubtagsMethod = clazz.getMethod("addLikelySubtags", new Class[] { String.class });
        } 
      } catch (Exception exception) {
        sGetScriptMethod = null;
        sAddLikelySubtagsMethod = null;
        Log.w("ICUCompat", exception);
      } 
    } 
  }
  
  private static String addLikelySubtags(Locale paramLocale) {
    String str = paramLocale.toString();
    try {
      if (sAddLikelySubtagsMethod != null)
        return (String)sAddLikelySubtagsMethod.invoke(null, new Object[] { str }); 
    } catch (IllegalAccessException illegalAccessException) {
      Log.w("ICUCompat", illegalAccessException);
    } catch (InvocationTargetException invocationTargetException) {
      Log.w("ICUCompat", invocationTargetException);
    } 
    return str;
  }
  
  private static String getScript(String paramString) {
    try {
      if (sGetScriptMethod != null)
        return (String)sGetScriptMethod.invoke(null, new Object[] { paramString }); 
    } catch (IllegalAccessException illegalAccessException) {
      Log.w("ICUCompat", illegalAccessException);
    } catch (InvocationTargetException invocationTargetException) {
      Log.w("ICUCompat", invocationTargetException);
    } 
    return null;
  }
  
  public static String maximizeAndGetScript(Locale paramLocale) {
    if (Build.VERSION.SDK_INT >= 21) {
      try {
        return ((Locale)sAddLikelySubtagsMethod.invoke(null, new Object[] { paramLocale })).getScript();
      } catch (InvocationTargetException invocationTargetException) {
        Log.w("ICUCompat", invocationTargetException);
      } catch (IllegalAccessException illegalAccessException) {
        Log.w("ICUCompat", illegalAccessException);
      } 
      return paramLocale.getScript();
    } 
    String str = addLikelySubtags(paramLocale);
    return (str != null) ? getScript(str) : null;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\text\ICUCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */