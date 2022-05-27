package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.os.Build;
import android.support.v7.appcompat.R;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

class AppCompatTextViewAutoSizeHelper {
  private static final int DEFAULT_AUTO_SIZE_GRANULARITY_IN_PX = 1;
  
  private static final int DEFAULT_AUTO_SIZE_MAX_TEXT_SIZE_IN_SP = 112;
  
  private static final int DEFAULT_AUTO_SIZE_MIN_TEXT_SIZE_IN_SP = 12;
  
  private static final String TAG = "ACTVAutoSizeHelper";
  
  private static final RectF TEMP_RECTF = new RectF();
  
  static final float UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE = -1.0F;
  
  private static final int VERY_WIDE = 1048576;
  
  private static ConcurrentHashMap<String, Method> sTextViewMethodByNameCache = new ConcurrentHashMap<String, Method>();
  
  private float mAutoSizeMaxTextSizeInPx = -1.0F;
  
  private float mAutoSizeMinTextSizeInPx = -1.0F;
  
  private float mAutoSizeStepGranularityInPx = -1.0F;
  
  private int[] mAutoSizeTextSizesInPx = new int[0];
  
  private int mAutoSizeTextType = 0;
  
  private final Context mContext;
  
  private boolean mHasPresetAutoSizeValues = false;
  
  private boolean mNeedsAutoSizeText = false;
  
  private TextPaint mTempTextPaint;
  
  private final TextView mTextView;
  
  AppCompatTextViewAutoSizeHelper(TextView paramTextView) {
    this.mTextView = paramTextView;
    this.mContext = paramTextView.getContext();
  }
  
  private int[] cleanupAutoSizePresetSizes(int[] paramArrayOfint) {
    int i = paramArrayOfint.length;
    if (i == 0)
      return paramArrayOfint; 
    Arrays.sort(paramArrayOfint);
    ArrayList<? extends Comparable<? super Integer>> arrayList = new ArrayList();
    boolean bool = false;
    byte b;
    for (b = 0; b < i; b++) {
      int j = paramArrayOfint[b];
      if (j > 0 && Collections.binarySearch(arrayList, Integer.valueOf(j)) < 0)
        arrayList.add(Integer.valueOf(j)); 
    } 
    if (i == arrayList.size())
      return paramArrayOfint; 
    i = arrayList.size();
    paramArrayOfint = new int[i];
    for (b = bool; b < i; b++)
      paramArrayOfint[b] = ((Integer)arrayList.get(b)).intValue(); 
    return paramArrayOfint;
  }
  
  private void clearAutoSizeConfiguration() {
    this.mAutoSizeTextType = 0;
    this.mAutoSizeMinTextSizeInPx = -1.0F;
    this.mAutoSizeMaxTextSizeInPx = -1.0F;
    this.mAutoSizeStepGranularityInPx = -1.0F;
    this.mAutoSizeTextSizesInPx = new int[0];
    this.mNeedsAutoSizeText = false;
  }
  
  private StaticLayout createStaticLayoutForMeasuring(CharSequence paramCharSequence, Layout.Alignment paramAlignment, int paramInt1, int paramInt2) {
    TextDirectionHeuristic textDirectionHeuristic = invokeAndReturnWithDefault(this.mTextView, "getTextDirectionHeuristic", TextDirectionHeuristics.FIRSTSTRONG_LTR);
    StaticLayout.Builder builder = StaticLayout.Builder.obtain(paramCharSequence, 0, paramCharSequence.length(), this.mTempTextPaint, paramInt1).setAlignment(paramAlignment).setLineSpacing(this.mTextView.getLineSpacingExtra(), this.mTextView.getLineSpacingMultiplier()).setIncludePad(this.mTextView.getIncludeFontPadding()).setBreakStrategy(this.mTextView.getBreakStrategy()).setHyphenationFrequency(this.mTextView.getHyphenationFrequency());
    paramInt1 = paramInt2;
    if (paramInt2 == -1)
      paramInt1 = Integer.MAX_VALUE; 
    return builder.setMaxLines(paramInt1).setTextDirection(textDirectionHeuristic).build();
  }
  
  private StaticLayout createStaticLayoutForMeasuringPre23(CharSequence paramCharSequence, Layout.Alignment paramAlignment, int paramInt) {
    float f1;
    float f2;
    boolean bool;
    if (Build.VERSION.SDK_INT >= 16) {
      f1 = this.mTextView.getLineSpacingMultiplier();
      f2 = this.mTextView.getLineSpacingExtra();
      bool = this.mTextView.getIncludeFontPadding();
    } else {
      f1 = ((Float)invokeAndReturnWithDefault(this.mTextView, "getLineSpacingMultiplier", Float.valueOf(1.0F))).floatValue();
      f2 = ((Float)invokeAndReturnWithDefault(this.mTextView, "getLineSpacingExtra", Float.valueOf(0.0F))).floatValue();
      bool = ((Boolean)invokeAndReturnWithDefault(this.mTextView, "getIncludeFontPadding", Boolean.valueOf(true))).booleanValue();
    } 
    return new StaticLayout(paramCharSequence, this.mTempTextPaint, paramInt, paramAlignment, f1, f2, bool);
  }
  
  private int findLargestTextSizeWhichFits(RectF paramRectF) {
    int i = this.mAutoSizeTextSizesInPx.length;
    if (i != 0) {
      int j = i - 1;
      i = 1;
      int k = 0;
      while (i <= j) {
        int m = (i + j) / 2;
        if (suggestedSizeFitsInSpace(this.mAutoSizeTextSizesInPx[m], paramRectF)) {
          k = i;
          i = m + 1;
          continue;
        } 
        k = m - 1;
        j = k;
      } 
      return this.mAutoSizeTextSizesInPx[k];
    } 
    throw new IllegalStateException("No available text sizes to choose from.");
  }
  
  private Method getTextViewMethod(String paramString) {
    try {
      Method method1 = sTextViewMethodByNameCache.get(paramString);
      Method method2 = method1;
      if (method1 == null) {
        method1 = TextView.class.getDeclaredMethod(paramString, new Class[0]);
        method2 = method1;
        if (method1 != null) {
          method1.setAccessible(true);
          sTextViewMethodByNameCache.put(paramString, method1);
          method2 = method1;
        } 
      } 
      return method2;
    } catch (Exception exception) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Failed to retrieve TextView#");
      stringBuilder.append(paramString);
      stringBuilder.append("() method");
      Log.w("ACTVAutoSizeHelper", stringBuilder.toString(), exception);
      return null;
    } 
  }
  
  private <T> T invokeAndReturnWithDefault(Object paramObject, String paramString, T paramT) {
    try {
      paramObject = getTextViewMethod(paramString).invoke(paramObject, new Object[0]);
      paramT = (T)paramObject;
    } catch (Exception exception) {
      paramObject = new StringBuilder();
      super();
      paramObject.append("Failed to invoke TextView#");
      paramObject.append(paramString);
      paramObject.append("() method");
      Log.w("ACTVAutoSizeHelper", paramObject.toString(), exception);
    } finally {}
    return paramT;
  }
  
  private void setRawTextSize(float paramFloat) {
    if (paramFloat != this.mTextView.getPaint().getTextSize()) {
      boolean bool;
      this.mTextView.getPaint().setTextSize(paramFloat);
      if (Build.VERSION.SDK_INT >= 18) {
        bool = this.mTextView.isInLayout();
      } else {
        bool = false;
      } 
      if (this.mTextView.getLayout() != null) {
        this.mNeedsAutoSizeText = false;
        try {
          Method method = getTextViewMethod("nullLayouts");
          if (method != null)
            method.invoke(this.mTextView, new Object[0]); 
        } catch (Exception exception) {
          Log.w("ACTVAutoSizeHelper", "Failed to invoke TextView#nullLayouts() method", exception);
        } 
        if (!bool) {
          this.mTextView.requestLayout();
        } else {
          this.mTextView.forceLayout();
        } 
        this.mTextView.invalidate();
      } 
    } 
  }
  
  private boolean setupAutoSizeText() {
    boolean bool = supportsAutoSizeText();
    byte b = 0;
    if (bool && this.mAutoSizeTextType == 1) {
      if (!this.mHasPresetAutoSizeValues || this.mAutoSizeTextSizesInPx.length == 0) {
        float f = Math.round(this.mAutoSizeMinTextSizeInPx);
        byte b1 = 1;
        while (Math.round(this.mAutoSizeStepGranularityInPx + f) <= Math.round(this.mAutoSizeMaxTextSizeInPx)) {
          b1++;
          f += this.mAutoSizeStepGranularityInPx;
        } 
        int[] arrayOfInt = new int[b1];
        f = this.mAutoSizeMinTextSizeInPx;
        while (b < b1) {
          arrayOfInt[b] = Math.round(f);
          f += this.mAutoSizeStepGranularityInPx;
          b++;
        } 
        this.mAutoSizeTextSizesInPx = cleanupAutoSizePresetSizes(arrayOfInt);
      } 
      this.mNeedsAutoSizeText = true;
    } else {
      this.mNeedsAutoSizeText = false;
    } 
    return this.mNeedsAutoSizeText;
  }
  
  private void setupAutoSizeUniformPresetSizes(TypedArray paramTypedArray) {
    int i = paramTypedArray.length();
    int[] arrayOfInt = new int[i];
    if (i > 0) {
      for (byte b = 0; b < i; b++)
        arrayOfInt[b] = paramTypedArray.getDimensionPixelSize(b, -1); 
      this.mAutoSizeTextSizesInPx = cleanupAutoSizePresetSizes(arrayOfInt);
      setupAutoSizeUniformPresetSizesConfiguration();
    } 
  }
  
  private boolean setupAutoSizeUniformPresetSizesConfiguration() {
    boolean bool;
    int i = this.mAutoSizeTextSizesInPx.length;
    if (i > 0) {
      bool = true;
    } else {
      bool = false;
    } 
    this.mHasPresetAutoSizeValues = bool;
    if (bool) {
      this.mAutoSizeTextType = 1;
      int[] arrayOfInt = this.mAutoSizeTextSizesInPx;
      this.mAutoSizeMinTextSizeInPx = arrayOfInt[0];
      this.mAutoSizeMaxTextSizeInPx = arrayOfInt[i - 1];
      this.mAutoSizeStepGranularityInPx = -1.0F;
    } 
    return this.mHasPresetAutoSizeValues;
  }
  
  private boolean suggestedSizeFitsInSpace(int paramInt, RectF paramRectF) {
    StaticLayout staticLayout;
    byte b;
    CharSequence charSequence1 = this.mTextView.getText();
    TransformationMethod transformationMethod = this.mTextView.getTransformationMethod();
    CharSequence charSequence2 = charSequence1;
    if (transformationMethod != null) {
      CharSequence charSequence = transformationMethod.getTransformation(charSequence1, (View)this.mTextView);
      charSequence2 = charSequence1;
      if (charSequence != null)
        charSequence2 = charSequence; 
    } 
    if (Build.VERSION.SDK_INT >= 16) {
      b = this.mTextView.getMaxLines();
    } else {
      b = -1;
    } 
    TextPaint textPaint = this.mTempTextPaint;
    if (textPaint == null) {
      this.mTempTextPaint = new TextPaint();
    } else {
      textPaint.reset();
    } 
    this.mTempTextPaint.set(this.mTextView.getPaint());
    this.mTempTextPaint.setTextSize(paramInt);
    Layout.Alignment alignment = invokeAndReturnWithDefault(this.mTextView, "getLayoutAlignment", Layout.Alignment.ALIGN_NORMAL);
    if (Build.VERSION.SDK_INT >= 23) {
      staticLayout = createStaticLayoutForMeasuring(charSequence2, alignment, Math.round(paramRectF.right), b);
    } else {
      staticLayout = createStaticLayoutForMeasuringPre23(charSequence2, (Layout.Alignment)staticLayout, Math.round(paramRectF.right));
    } 
    return (b != -1 && (staticLayout.getLineCount() > b || staticLayout.getLineEnd(staticLayout.getLineCount() - 1) != charSequence2.length())) ? false : (!(staticLayout.getHeight() > paramRectF.bottom));
  }
  
  private boolean supportsAutoSizeText() {
    return this.mTextView instanceof AppCompatEditText ^ true;
  }
  
  private void validateAndSetAutoSizeTextTypeUniformConfiguration(float paramFloat1, float paramFloat2, float paramFloat3) throws IllegalArgumentException {
    if (paramFloat1 > 0.0F) {
      if (paramFloat2 > paramFloat1) {
        if (paramFloat3 > 0.0F) {
          this.mAutoSizeTextType = 1;
          this.mAutoSizeMinTextSizeInPx = paramFloat1;
          this.mAutoSizeMaxTextSizeInPx = paramFloat2;
          this.mAutoSizeStepGranularityInPx = paramFloat3;
          this.mHasPresetAutoSizeValues = false;
          return;
        } 
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("The auto-size step granularity (");
        stringBuilder2.append(paramFloat3);
        stringBuilder2.append("px) is less or equal to (0px)");
        throw new IllegalArgumentException(stringBuilder2.toString());
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Maximum auto-size text size (");
      stringBuilder1.append(paramFloat2);
      stringBuilder1.append("px) is less or equal to minimum auto-size ");
      stringBuilder1.append("text size (");
      stringBuilder1.append(paramFloat1);
      stringBuilder1.append("px)");
      throw new IllegalArgumentException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Minimum auto-size text size (");
    stringBuilder.append(paramFloat1);
    stringBuilder.append("px) is less or equal to (0px)");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  void autoSizeText() {
    if (!isAutoSizeEnabled())
      return; 
    if (this.mNeedsAutoSizeText) {
      int i;
      if (this.mTextView.getMeasuredHeight() <= 0 || this.mTextView.getMeasuredWidth() <= 0)
        return; 
      if (((Boolean)invokeAndReturnWithDefault(this.mTextView, "getHorizontallyScrolling", Boolean.valueOf(false))).booleanValue()) {
        i = 1048576;
      } else {
        i = this.mTextView.getMeasuredWidth() - this.mTextView.getTotalPaddingLeft() - this.mTextView.getTotalPaddingRight();
      } 
      int j = this.mTextView.getHeight() - this.mTextView.getCompoundPaddingBottom() - this.mTextView.getCompoundPaddingTop();
      if (i <= 0 || j <= 0)
        return; 
      synchronized (TEMP_RECTF) {
        TEMP_RECTF.setEmpty();
        TEMP_RECTF.right = i;
        TEMP_RECTF.bottom = j;
        float f = findLargestTextSizeWhichFits(TEMP_RECTF);
        if (f != this.mTextView.getTextSize())
          setTextSizeInternal(0, f); 
      } 
    } 
    this.mNeedsAutoSizeText = true;
  }
  
  int getAutoSizeMaxTextSize() {
    return Math.round(this.mAutoSizeMaxTextSizeInPx);
  }
  
  int getAutoSizeMinTextSize() {
    return Math.round(this.mAutoSizeMinTextSizeInPx);
  }
  
  int getAutoSizeStepGranularity() {
    return Math.round(this.mAutoSizeStepGranularityInPx);
  }
  
  int[] getAutoSizeTextAvailableSizes() {
    return this.mAutoSizeTextSizesInPx;
  }
  
  int getAutoSizeTextType() {
    return this.mAutoSizeTextType;
  }
  
  boolean isAutoSizeEnabled() {
    boolean bool;
    if (supportsAutoSizeText() && this.mAutoSizeTextType != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  void loadFromAttributes(AttributeSet paramAttributeSet, int paramInt) {
    float f1;
    float f2;
    float f3;
    TypedArray typedArray = this.mContext.obtainStyledAttributes(paramAttributeSet, R.styleable.AppCompatTextView, paramInt, 0);
    if (typedArray.hasValue(R.styleable.AppCompatTextView_autoSizeTextType))
      this.mAutoSizeTextType = typedArray.getInt(R.styleable.AppCompatTextView_autoSizeTextType, 0); 
    if (typedArray.hasValue(R.styleable.AppCompatTextView_autoSizeStepGranularity)) {
      f1 = typedArray.getDimension(R.styleable.AppCompatTextView_autoSizeStepGranularity, -1.0F);
    } else {
      f1 = -1.0F;
    } 
    if (typedArray.hasValue(R.styleable.AppCompatTextView_autoSizeMinTextSize)) {
      f2 = typedArray.getDimension(R.styleable.AppCompatTextView_autoSizeMinTextSize, -1.0F);
    } else {
      f2 = -1.0F;
    } 
    if (typedArray.hasValue(R.styleable.AppCompatTextView_autoSizeMaxTextSize)) {
      f3 = typedArray.getDimension(R.styleable.AppCompatTextView_autoSizeMaxTextSize, -1.0F);
    } else {
      f3 = -1.0F;
    } 
    if (typedArray.hasValue(R.styleable.AppCompatTextView_autoSizePresetSizes)) {
      paramInt = typedArray.getResourceId(R.styleable.AppCompatTextView_autoSizePresetSizes, 0);
      if (paramInt > 0) {
        TypedArray typedArray1 = typedArray.getResources().obtainTypedArray(paramInt);
        setupAutoSizeUniformPresetSizes(typedArray1);
        typedArray1.recycle();
      } 
    } 
    typedArray.recycle();
    if (supportsAutoSizeText()) {
      if (this.mAutoSizeTextType == 1) {
        if (!this.mHasPresetAutoSizeValues) {
          DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
          float f = f2;
          if (f2 == -1.0F)
            f = TypedValue.applyDimension(2, 12.0F, displayMetrics); 
          f2 = f3;
          if (f3 == -1.0F)
            f2 = TypedValue.applyDimension(2, 112.0F, displayMetrics); 
          f3 = f1;
          if (f1 == -1.0F)
            f3 = 1.0F; 
          validateAndSetAutoSizeTextTypeUniformConfiguration(f, f2, f3);
        } 
        setupAutoSizeText();
      } 
    } else {
      this.mAutoSizeTextType = 0;
    } 
  }
  
  void setAutoSizeTextTypeUniformWithConfiguration(int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws IllegalArgumentException {
    if (supportsAutoSizeText()) {
      DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
      validateAndSetAutoSizeTextTypeUniformConfiguration(TypedValue.applyDimension(paramInt4, paramInt1, displayMetrics), TypedValue.applyDimension(paramInt4, paramInt2, displayMetrics), TypedValue.applyDimension(paramInt4, paramInt3, displayMetrics));
      if (setupAutoSizeText())
        autoSizeText(); 
    } 
  }
  
  void setAutoSizeTextTypeUniformWithPresetSizes(int[] paramArrayOfint, int paramInt) throws IllegalArgumentException {
    if (supportsAutoSizeText()) {
      int i = paramArrayOfint.length;
      byte b = 0;
      if (i > 0) {
        int[] arrayOfInt2;
        int[] arrayOfInt1 = new int[i];
        if (paramInt == 0) {
          arrayOfInt2 = Arrays.copyOf(paramArrayOfint, i);
        } else {
          DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
          while (true) {
            arrayOfInt2 = arrayOfInt1;
            if (b < i) {
              arrayOfInt1[b] = Math.round(TypedValue.applyDimension(paramInt, paramArrayOfint[b], displayMetrics));
              b++;
              continue;
            } 
            break;
          } 
        } 
        this.mAutoSizeTextSizesInPx = cleanupAutoSizePresetSizes(arrayOfInt2);
        if (!setupAutoSizeUniformPresetSizesConfiguration()) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("None of the preset sizes is valid: ");
          stringBuilder.append(Arrays.toString(paramArrayOfint));
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
      } else {
        this.mHasPresetAutoSizeValues = false;
      } 
      if (setupAutoSizeText())
        autoSizeText(); 
    } 
  }
  
  void setAutoSizeTextTypeWithDefaults(int paramInt) {
    if (supportsAutoSizeText())
      if (paramInt != 0) {
        if (paramInt == 1) {
          DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
          validateAndSetAutoSizeTextTypeUniformConfiguration(TypedValue.applyDimension(2, 12.0F, displayMetrics), TypedValue.applyDimension(2, 112.0F, displayMetrics), 1.0F);
          if (setupAutoSizeText())
            autoSizeText(); 
        } else {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Unknown auto-size text type: ");
          stringBuilder.append(paramInt);
          throw new IllegalArgumentException(stringBuilder.toString());
        } 
      } else {
        clearAutoSizeConfiguration();
      }  
  }
  
  void setTextSizeInternal(int paramInt, float paramFloat) {
    Resources resources;
    Context context = this.mContext;
    if (context == null) {
      resources = Resources.getSystem();
    } else {
      resources = resources.getResources();
    } 
    setRawTextSize(TypedValue.applyDimension(paramInt, paramFloat, resources.getDisplayMetrics()));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\AppCompatTextViewAutoSizeHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */