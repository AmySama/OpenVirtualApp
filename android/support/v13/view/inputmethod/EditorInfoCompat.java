package android.support.v13.view.inputmethod;

import android.os.Build;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;

public final class EditorInfoCompat {
  private static final String CONTENT_MIME_TYPES_KEY = "android.support.v13.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES";
  
  private static final String[] EMPTY_STRING_ARRAY = new String[0];
  
  public static final int IME_FLAG_FORCE_ASCII = -2147483648;
  
  public static final int IME_FLAG_NO_PERSONALIZED_LEARNING = 16777216;
  
  public static String[] getContentMimeTypes(EditorInfo paramEditorInfo) {
    if (Build.VERSION.SDK_INT >= 25) {
      arrayOfString = paramEditorInfo.contentMimeTypes;
      if (arrayOfString == null)
        arrayOfString = EMPTY_STRING_ARRAY; 
      return arrayOfString;
    } 
    if (((EditorInfo)arrayOfString).extras == null)
      return EMPTY_STRING_ARRAY; 
    String[] arrayOfString = ((EditorInfo)arrayOfString).extras.getStringArray("android.support.v13.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES");
    if (arrayOfString == null)
      arrayOfString = EMPTY_STRING_ARRAY; 
    return arrayOfString;
  }
  
  public static void setContentMimeTypes(EditorInfo paramEditorInfo, String[] paramArrayOfString) {
    if (Build.VERSION.SDK_INT >= 25) {
      paramEditorInfo.contentMimeTypes = paramArrayOfString;
    } else {
      if (paramEditorInfo.extras == null)
        paramEditorInfo.extras = new Bundle(); 
      paramEditorInfo.extras.putStringArray("android.support.v13.view.inputmethod.EditorInfoCompat.CONTENT_MIME_TYPES", paramArrayOfString);
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v13\view\inputmethod\EditorInfoCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */