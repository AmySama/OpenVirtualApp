package android.support.v4.content;

import android.content.Intent;
import android.os.Build;

public final class IntentCompat {
  public static final String CATEGORY_LEANBACK_LAUNCHER = "android.intent.category.LEANBACK_LAUNCHER";
  
  public static final String EXTRA_HTML_TEXT = "android.intent.extra.HTML_TEXT";
  
  public static final String EXTRA_START_PLAYBACK = "android.intent.extra.START_PLAYBACK";
  
  public static Intent makeMainSelectorActivity(String paramString1, String paramString2) {
    if (Build.VERSION.SDK_INT >= 15)
      return Intent.makeMainSelectorActivity(paramString1, paramString2); 
    Intent intent = new Intent(paramString1);
    intent.addCategory(paramString2);
    return intent;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\content\IntentCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */