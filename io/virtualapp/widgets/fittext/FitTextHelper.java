package io.virtualapp.widgets.fittext;

import android.graphics.Paint;
import android.os.Build;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.widget.TextView;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

class FitTextHelper {
  protected static final float LIMIT = 0.001F;
  
  private static final boolean LastNoSpace = false;
  
  public static final List<CharSequence> sSpcaeList;
  
  protected volatile boolean mFittingText = false;
  
  protected BaseTextView textView;
  
  static {
    ArrayList<CharSequence> arrayList = new ArrayList();
    sSpcaeList = arrayList;
    arrayList.add(",");
    sSpcaeList.add(".");
    sSpcaeList.add(";");
    sSpcaeList.add("'");
    sSpcaeList.add("\"");
    sSpcaeList.add(":");
    sSpcaeList.add("?");
    sSpcaeList.add("~");
    sSpcaeList.add("!");
    sSpcaeList.add("‘");
    sSpcaeList.add("’");
    sSpcaeList.add("”");
    sSpcaeList.add("“");
    sSpcaeList.add("；");
    sSpcaeList.add("：");
    sSpcaeList.add("，");
    sSpcaeList.add("。");
    sSpcaeList.add("？");
    sSpcaeList.add("！");
    sSpcaeList.add("(");
    sSpcaeList.add(")");
    sSpcaeList.add("[");
    sSpcaeList.add("]");
    sSpcaeList.add("@");
    sSpcaeList.add("/");
    sSpcaeList.add("#");
    sSpcaeList.add("$");
    sSpcaeList.add("%");
    sSpcaeList.add("^");
    sSpcaeList.add("&");
    sSpcaeList.add("*");
    sSpcaeList.add("<");
    sSpcaeList.add(">");
    sSpcaeList.add("+");
    sSpcaeList.add("-");
    sSpcaeList.add("·");
  }
  
  public FitTextHelper(BaseTextView paramBaseTextView) {
    this.textView = paramBaseTextView;
  }
  
  public static Layout.Alignment getLayoutAlignment(TextView paramTextView) {
    Layout.Alignment alignment;
    if (Build.VERSION.SDK_INT < 17)
      return Layout.Alignment.ALIGN_NORMAL; 
    switch (paramTextView.getTextAlignment()) {
      default:
        return Layout.Alignment.ALIGN_NORMAL;
      case 6:
        return Layout.Alignment.ALIGN_OPPOSITE;
      case 5:
        return Layout.Alignment.ALIGN_NORMAL;
      case 4:
        return Layout.Alignment.ALIGN_CENTER;
      case 3:
        return Layout.Alignment.ALIGN_OPPOSITE;
      case 2:
        return Layout.Alignment.ALIGN_NORMAL;
      case 1:
        break;
    } 
    int i = paramTextView.getGravity() & 0x800007;
    if (i != 1) {
      if (i != 3) {
        if (i != 5) {
          if (i != 8388611) {
            if (i != 8388613) {
              alignment = Layout.Alignment.ALIGN_NORMAL;
            } else {
              alignment = Layout.Alignment.ALIGN_OPPOSITE;
            } 
          } else {
            alignment = Layout.Alignment.ALIGN_NORMAL;
          } 
        } else if (alignment.getLayoutDirection() == 1) {
          alignment = Layout.Alignment.ALIGN_NORMAL;
        } else {
          alignment = Layout.Alignment.ALIGN_OPPOSITE;
        } 
      } else if (alignment.getLayoutDirection() == 1) {
        alignment = Layout.Alignment.ALIGN_OPPOSITE;
      } else {
        alignment = Layout.Alignment.ALIGN_NORMAL;
      } 
    } else {
      alignment = Layout.Alignment.ALIGN_CENTER;
    } 
    return alignment;
  }
  
  public static StaticLayout getStaticLayout(TextView paramTextView, CharSequence paramCharSequence, TextPaint paramTextPaint) {
    StaticLayout staticLayout;
    if (paramTextView instanceof FitTextView) {
      FitTextView fitTextView = (FitTextView)paramTextView;
      staticLayout = new StaticLayout(paramCharSequence, paramTextPaint, getTextWidth(paramTextView), getLayoutAlignment(fitTextView), fitTextView.getLineSpacingMultiplierCompat(), fitTextView.getLineSpacingExtraCompat(), fitTextView.getIncludeFontPaddingCompat());
    } else if (Build.VERSION.SDK_INT <= 16) {
      staticLayout = new StaticLayout((CharSequence)staticLayout, paramTextPaint, getTextWidth(paramTextView), getLayoutAlignment(paramTextView), 0.0F, 0.0F, false);
    } else {
      staticLayout = new StaticLayout((CharSequence)staticLayout, paramTextPaint, getTextWidth(paramTextView), getLayoutAlignment(paramTextView), paramTextView.getLineSpacingMultiplier(), paramTextView.getLineSpacingExtra(), paramTextView.getIncludeFontPadding());
    } 
    if (isSingleLine(paramTextView))
      try {
        Field field = StaticLayout.class.getDeclaredField("mMaximumVisibleLineCount");
        if (field != null) {
          field.setAccessible(true);
          field.set(staticLayout, Integer.valueOf(1));
        } 
      } catch (Exception exception) {
        exception.printStackTrace();
      }  
    return staticLayout;
  }
  
  public static int getTextWidth(TextView paramTextView) {
    return paramTextView.getMeasuredWidth() - paramTextView.getCompoundPaddingLeft() - paramTextView.getCompoundPaddingRight();
  }
  
  public static boolean isSingleLine(TextView paramTextView) {
    boolean bool = false;
    if (paramTextView == null)
      return false; 
    if (paramTextView instanceof BaseTextView)
      return ((BaseTextView)paramTextView).isSingleLine(); 
    if (paramTextView == null)
      return false; 
    if ((paramTextView.getInputType() & 0x20000) == 131072)
      bool = true; 
    return bool;
  }
  
  public float fitTextSize(TextPaint paramTextPaint, CharSequence paramCharSequence, float paramFloat1, float paramFloat2) {
    if (TextUtils.isEmpty(paramCharSequence)) {
      if (paramTextPaint != null)
        return paramTextPaint.getTextSize(); 
      BaseTextView baseTextView = this.textView;
      if (baseTextView != null)
        return baseTextView.getTextSize(); 
    } 
    paramTextPaint = new TextPaint((Paint)paramTextPaint);
    while (Math.abs(paramFloat1 - paramFloat2) > 0.001F) {
      paramTextPaint.setTextSize((paramFloat2 + paramFloat1) / 2.0F);
      if (isFit(getLineBreaks(paramCharSequence, paramTextPaint), paramTextPaint)) {
        paramFloat2 = paramTextPaint.getTextSize();
        continue;
      } 
      paramFloat1 = paramTextPaint.getTextSize();
    } 
    return paramFloat2;
  }
  
  public CharSequence getLineBreaks(CharSequence paramCharSequence, TextPaint paramTextPaint) {
    Object object;
    int i = this.textView.getTextWidth();
    boolean bool = this.textView.isKeepWord();
    if (i <= 0 || bool)
      return paramCharSequence; 
    int j = paramCharSequence.length();
    boolean bool1 = false;
    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
    byte b = 1;
    while (b <= j) {
      int k = b - 1;
      if (TextUtils.equals(paramCharSequence.subSequence(k, b), "\n")) {
        spannableStringBuilder.append(paramCharSequence, object, b);
      } else {
        int m = paramTextPaint.measureText(paramCharSequence, object, b) cmp i;
        if (m > 0) {
          spannableStringBuilder.append(paramCharSequence, object, k);
          if (b < j && !TextUtils.equals(paramCharSequence.subSequence(k, b), "\n"))
            spannableStringBuilder.append('\n'); 
          continue;
        } 
        if (m == 0) {
          spannableStringBuilder.append(paramCharSequence, object, b);
          if (b < j && !TextUtils.equals(paramCharSequence.subSequence(b, b + 1), "\n"))
            spannableStringBuilder.append('\n'); 
        } else {
          Object object1 = object;
          if (b == j) {
            spannableStringBuilder.append(paramCharSequence, object, b);
          } else {
            continue;
          } 
        } 
      } 
      k = b;
      continue;
      b++;
      object = SYNTHETIC_LOCAL_VARIABLE_9;
    } 
    return (CharSequence)spannableStringBuilder;
  }
  
  protected int getMaxLineCount() {
    float f = this.textView.getTextLineHeight();
    return (int)(this.textView.getTextHeight() / f);
  }
  
  public StaticLayout getStaticLayout(CharSequence paramCharSequence, TextPaint paramTextPaint) {
    return getStaticLayout(this.textView.getTextView(), paramCharSequence, paramTextPaint);
  }
  
  protected boolean isFit(CharSequence paramCharSequence, TextPaint paramTextPaint) {
    boolean bool = this.textView.isSingleLine();
    int i = this.textView.getMaxLinesCompat();
    float f1 = this.textView.getLineSpacingMultiplierCompat();
    float f2 = this.textView.getLineSpacingExtraCompat();
    int j = this.textView.getTextHeight();
    int k = j;
    if (!bool)
      k = j + Math.round(f2 * f1); 
    boolean bool1 = true;
    if (bool) {
      j = 1;
    } else {
      j = Math.max(1, i);
    } 
    StaticLayout staticLayout = getStaticLayout(paramCharSequence, paramTextPaint);
    if (staticLayout.getLineCount() > j || staticLayout.getHeight() > k)
      bool1 = false; 
    return bool1;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\fittext\FitTextHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */