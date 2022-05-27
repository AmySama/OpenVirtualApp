package android.support.v4.graphics;

import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.util.Pair;

public final class PaintCompat {
  private static final String EM_STRING = "m";
  
  private static final String TOFU_STRING = "󟿽";
  
  private static final ThreadLocal<Pair<Rect, Rect>> sRectThreadLocal = new ThreadLocal<Pair<Rect, Rect>>();
  
  public static boolean hasGlyph(Paint paramPaint, String paramString) {
    if (Build.VERSION.SDK_INT >= 23)
      return paramPaint.hasGlyph(paramString); 
    int i = paramString.length();
    if (i == 1 && Character.isWhitespace(paramString.charAt(0)))
      return true; 
    float f1 = paramPaint.measureText("󟿽");
    float f2 = paramPaint.measureText("m");
    float f3 = paramPaint.measureText(paramString);
    float f4 = 0.0F;
    if (f3 == 0.0F)
      return false; 
    if (paramString.codePointCount(0, paramString.length()) > 1) {
      if (f3 > f2 * 2.0F)
        return false; 
      int j;
      for (j = 0; j < i; j = k) {
        int k = Character.charCount(paramString.codePointAt(j)) + j;
        f4 += paramPaint.measureText(paramString, j, k);
      } 
      if (f3 >= f4)
        return false; 
    } 
    if (f3 != f1)
      return true; 
    Pair<Rect, Rect> pair = obtainEmptyRects();
    paramPaint.getTextBounds("󟿽", 0, 2, (Rect)pair.first);
    paramPaint.getTextBounds(paramString, 0, i, (Rect)pair.second);
    return ((Rect)pair.first).equals(pair.second) ^ true;
  }
  
  private static Pair<Rect, Rect> obtainEmptyRects() {
    Pair<Rect, Rect> pair = sRectThreadLocal.get();
    if (pair == null) {
      pair = new Pair(new Rect(), new Rect());
      sRectThreadLocal.set(pair);
    } else {
      ((Rect)pair.first).setEmpty();
      ((Rect)pair.second).setEmpty();
    } 
    return pair;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\graphics\PaintCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */