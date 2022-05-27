package android.support.percent;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

@Deprecated
public class PercentLayoutHelper {
  private static final boolean DEBUG = false;
  
  private static final String TAG = "PercentLayout";
  
  private static final boolean VERBOSE = false;
  
  private final ViewGroup mHost;
  
  public PercentLayoutHelper(ViewGroup paramViewGroup) {
    if (paramViewGroup != null) {
      this.mHost = paramViewGroup;
      return;
    } 
    throw new IllegalArgumentException("host must be non-null");
  }
  
  public static void fetchWidthAndHeight(ViewGroup.LayoutParams paramLayoutParams, TypedArray paramTypedArray, int paramInt1, int paramInt2) {
    paramLayoutParams.width = paramTypedArray.getLayoutDimension(paramInt1, 0);
    paramLayoutParams.height = paramTypedArray.getLayoutDimension(paramInt2, 0);
  }
  
  public static PercentLayoutInfo getPercentLayoutInfo(Context paramContext, AttributeSet paramAttributeSet) {
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.PercentLayout_Layout);
    float f = typedArray.getFraction(R.styleable.PercentLayout_Layout_layout_widthPercent, 1, 1, -1.0F);
    if (f != -1.0F) {
      PercentLayoutInfo percentLayoutInfo = new PercentLayoutInfo();
      percentLayoutInfo.widthPercent = f;
    } else {
      paramAttributeSet = null;
    } 
    f = typedArray.getFraction(R.styleable.PercentLayout_Layout_layout_heightPercent, 1, 1, -1.0F);
    AttributeSet attributeSet = paramAttributeSet;
    if (f != -1.0F) {
      if (paramAttributeSet != null) {
        attributeSet = paramAttributeSet;
      } else {
        percentLayoutInfo1 = new PercentLayoutInfo();
      } 
      percentLayoutInfo1.heightPercent = f;
    } 
    f = typedArray.getFraction(R.styleable.PercentLayout_Layout_layout_marginPercent, 1, 1, -1.0F);
    PercentLayoutInfo percentLayoutInfo2 = percentLayoutInfo1;
    if (f != -1.0F) {
      if (percentLayoutInfo1 == null)
        percentLayoutInfo1 = new PercentLayoutInfo(); 
      percentLayoutInfo1.leftMarginPercent = f;
      percentLayoutInfo1.topMarginPercent = f;
      percentLayoutInfo1.rightMarginPercent = f;
      percentLayoutInfo1.bottomMarginPercent = f;
      percentLayoutInfo2 = percentLayoutInfo1;
    } 
    f = typedArray.getFraction(R.styleable.PercentLayout_Layout_layout_marginLeftPercent, 1, 1, -1.0F);
    PercentLayoutInfo percentLayoutInfo1 = percentLayoutInfo2;
    if (f != -1.0F) {
      if (percentLayoutInfo2 != null) {
        percentLayoutInfo1 = percentLayoutInfo2;
      } else {
        percentLayoutInfo1 = new PercentLayoutInfo();
      } 
      percentLayoutInfo1.leftMarginPercent = f;
    } 
    f = typedArray.getFraction(R.styleable.PercentLayout_Layout_layout_marginTopPercent, 1, 1, -1.0F);
    percentLayoutInfo2 = percentLayoutInfo1;
    if (f != -1.0F) {
      if (percentLayoutInfo1 == null)
        percentLayoutInfo1 = new PercentLayoutInfo(); 
      percentLayoutInfo1.topMarginPercent = f;
      percentLayoutInfo2 = percentLayoutInfo1;
    } 
    f = typedArray.getFraction(R.styleable.PercentLayout_Layout_layout_marginRightPercent, 1, 1, -1.0F);
    percentLayoutInfo1 = percentLayoutInfo2;
    if (f != -1.0F) {
      if (percentLayoutInfo2 != null) {
        percentLayoutInfo1 = percentLayoutInfo2;
      } else {
        percentLayoutInfo1 = new PercentLayoutInfo();
      } 
      percentLayoutInfo1.rightMarginPercent = f;
    } 
    f = typedArray.getFraction(R.styleable.PercentLayout_Layout_layout_marginBottomPercent, 1, 1, -1.0F);
    percentLayoutInfo2 = percentLayoutInfo1;
    if (f != -1.0F) {
      if (percentLayoutInfo1 == null)
        percentLayoutInfo1 = new PercentLayoutInfo(); 
      percentLayoutInfo1.bottomMarginPercent = f;
      percentLayoutInfo2 = percentLayoutInfo1;
    } 
    f = typedArray.getFraction(R.styleable.PercentLayout_Layout_layout_marginStartPercent, 1, 1, -1.0F);
    percentLayoutInfo1 = percentLayoutInfo2;
    if (f != -1.0F) {
      if (percentLayoutInfo2 != null) {
        percentLayoutInfo1 = percentLayoutInfo2;
      } else {
        percentLayoutInfo1 = new PercentLayoutInfo();
      } 
      percentLayoutInfo1.startMarginPercent = f;
    } 
    f = typedArray.getFraction(R.styleable.PercentLayout_Layout_layout_marginEndPercent, 1, 1, -1.0F);
    percentLayoutInfo2 = percentLayoutInfo1;
    if (f != -1.0F) {
      if (percentLayoutInfo1 == null)
        percentLayoutInfo1 = new PercentLayoutInfo(); 
      percentLayoutInfo1.endMarginPercent = f;
      percentLayoutInfo2 = percentLayoutInfo1;
    } 
    f = typedArray.getFraction(R.styleable.PercentLayout_Layout_layout_aspectRatio, 1, 1, -1.0F);
    percentLayoutInfo1 = percentLayoutInfo2;
    if (f != -1.0F) {
      if (percentLayoutInfo2 == null)
        percentLayoutInfo2 = new PercentLayoutInfo(); 
      percentLayoutInfo2.aspectRatio = f;
      percentLayoutInfo1 = percentLayoutInfo2;
    } 
    typedArray.recycle();
    return percentLayoutInfo1;
  }
  
  private static boolean shouldHandleMeasuredHeightTooSmall(View paramView, PercentLayoutInfo paramPercentLayoutInfo) {
    boolean bool;
    if ((paramView.getMeasuredHeightAndState() & 0xFF000000) == 16777216 && paramPercentLayoutInfo.heightPercent >= 0.0F && paramPercentLayoutInfo.mPreservedParams.height == -2) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private static boolean shouldHandleMeasuredWidthTooSmall(View paramView, PercentLayoutInfo paramPercentLayoutInfo) {
    boolean bool;
    if ((paramView.getMeasuredWidthAndState() & 0xFF000000) == 16777216 && paramPercentLayoutInfo.widthPercent >= 0.0F && paramPercentLayoutInfo.mPreservedParams.width == -2) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void adjustChildren(int paramInt1, int paramInt2) {
    int i = View.MeasureSpec.getSize(paramInt1) - this.mHost.getPaddingLeft() - this.mHost.getPaddingRight();
    int j = View.MeasureSpec.getSize(paramInt2) - this.mHost.getPaddingTop() - this.mHost.getPaddingBottom();
    paramInt2 = this.mHost.getChildCount();
    for (paramInt1 = 0; paramInt1 < paramInt2; paramInt1++) {
      View view = this.mHost.getChildAt(paramInt1);
      ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
      if (layoutParams instanceof PercentLayoutParams) {
        PercentLayoutInfo percentLayoutInfo = ((PercentLayoutParams)layoutParams).getPercentLayoutInfo();
        if (percentLayoutInfo != null)
          if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            percentLayoutInfo.fillMarginLayoutParams(view, (ViewGroup.MarginLayoutParams)layoutParams, i, j);
          } else {
            percentLayoutInfo.fillLayoutParams(layoutParams, i, j);
          }  
      } 
    } 
  }
  
  public boolean handleMeasuredStateTooSmall() {
    int i = this.mHost.getChildCount();
    byte b = 0;
    boolean bool;
    for (bool = false; b < i; bool = bool1) {
      View view = this.mHost.getChildAt(b);
      ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
      boolean bool1 = bool;
      if (layoutParams instanceof PercentLayoutParams) {
        PercentLayoutInfo percentLayoutInfo = ((PercentLayoutParams)layoutParams).getPercentLayoutInfo();
        bool1 = bool;
        if (percentLayoutInfo != null) {
          if (shouldHandleMeasuredWidthTooSmall(view, percentLayoutInfo)) {
            layoutParams.width = -2;
            bool = true;
          } 
          bool1 = bool;
          if (shouldHandleMeasuredHeightTooSmall(view, percentLayoutInfo)) {
            layoutParams.height = -2;
            bool1 = true;
          } 
        } 
      } 
      b++;
    } 
    return bool;
  }
  
  public void restoreOriginalParams() {
    int i = this.mHost.getChildCount();
    for (byte b = 0; b < i; b++) {
      ViewGroup.LayoutParams layoutParams = this.mHost.getChildAt(b).getLayoutParams();
      if (layoutParams instanceof PercentLayoutParams) {
        PercentLayoutInfo percentLayoutInfo = ((PercentLayoutParams)layoutParams).getPercentLayoutInfo();
        if (percentLayoutInfo != null)
          if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            percentLayoutInfo.restoreMarginLayoutParams((ViewGroup.MarginLayoutParams)layoutParams);
          } else {
            percentLayoutInfo.restoreLayoutParams(layoutParams);
          }  
      } 
    } 
  }
  
  @Deprecated
  public static class PercentLayoutInfo {
    public float aspectRatio;
    
    public float bottomMarginPercent = -1.0F;
    
    public float endMarginPercent = -1.0F;
    
    public float heightPercent = -1.0F;
    
    public float leftMarginPercent = -1.0F;
    
    final PercentLayoutHelper.PercentMarginLayoutParams mPreservedParams = new PercentLayoutHelper.PercentMarginLayoutParams(0, 0);
    
    public float rightMarginPercent = -1.0F;
    
    public float startMarginPercent = -1.0F;
    
    public float topMarginPercent = -1.0F;
    
    public float widthPercent = -1.0F;
    
    public void fillLayoutParams(ViewGroup.LayoutParams param1LayoutParams, int param1Int1, int param1Int2) {
      // Byte code:
      //   0: aload_0
      //   1: getfield mPreservedParams : Landroid/support/percent/PercentLayoutHelper$PercentMarginLayoutParams;
      //   4: aload_1
      //   5: getfield width : I
      //   8: putfield width : I
      //   11: aload_0
      //   12: getfield mPreservedParams : Landroid/support/percent/PercentLayoutHelper$PercentMarginLayoutParams;
      //   15: aload_1
      //   16: getfield height : I
      //   19: putfield height : I
      //   22: aload_0
      //   23: getfield mPreservedParams : Landroid/support/percent/PercentLayoutHelper$PercentMarginLayoutParams;
      //   26: invokestatic access$000 : (Landroid/support/percent/PercentLayoutHelper$PercentMarginLayoutParams;)Z
      //   29: istore #4
      //   31: iconst_0
      //   32: istore #5
      //   34: iload #4
      //   36: ifne -> 49
      //   39: aload_0
      //   40: getfield mPreservedParams : Landroid/support/percent/PercentLayoutHelper$PercentMarginLayoutParams;
      //   43: getfield width : I
      //   46: ifne -> 64
      //   49: aload_0
      //   50: getfield widthPercent : F
      //   53: fconst_0
      //   54: fcmpg
      //   55: ifge -> 64
      //   58: iconst_1
      //   59: istore #6
      //   61: goto -> 67
      //   64: iconst_0
      //   65: istore #6
      //   67: aload_0
      //   68: getfield mPreservedParams : Landroid/support/percent/PercentLayoutHelper$PercentMarginLayoutParams;
      //   71: invokestatic access$100 : (Landroid/support/percent/PercentLayoutHelper$PercentMarginLayoutParams;)Z
      //   74: ifne -> 91
      //   77: iload #5
      //   79: istore #7
      //   81: aload_0
      //   82: getfield mPreservedParams : Landroid/support/percent/PercentLayoutHelper$PercentMarginLayoutParams;
      //   85: getfield height : I
      //   88: ifne -> 107
      //   91: iload #5
      //   93: istore #7
      //   95: aload_0
      //   96: getfield heightPercent : F
      //   99: fconst_0
      //   100: fcmpg
      //   101: ifge -> 107
      //   104: iconst_1
      //   105: istore #7
      //   107: aload_0
      //   108: getfield widthPercent : F
      //   111: fstore #8
      //   113: fload #8
      //   115: fconst_0
      //   116: fcmpl
      //   117: iflt -> 132
      //   120: aload_1
      //   121: iload_2
      //   122: i2f
      //   123: fload #8
      //   125: fmul
      //   126: invokestatic round : (F)I
      //   129: putfield width : I
      //   132: aload_0
      //   133: getfield heightPercent : F
      //   136: fstore #8
      //   138: fload #8
      //   140: fconst_0
      //   141: fcmpl
      //   142: iflt -> 157
      //   145: aload_1
      //   146: iload_3
      //   147: i2f
      //   148: fload #8
      //   150: fmul
      //   151: invokestatic round : (F)I
      //   154: putfield height : I
      //   157: aload_0
      //   158: getfield aspectRatio : F
      //   161: fconst_0
      //   162: fcmpl
      //   163: iflt -> 228
      //   166: iload #6
      //   168: ifeq -> 197
      //   171: aload_1
      //   172: aload_1
      //   173: getfield height : I
      //   176: i2f
      //   177: aload_0
      //   178: getfield aspectRatio : F
      //   181: fmul
      //   182: invokestatic round : (F)I
      //   185: putfield width : I
      //   188: aload_0
      //   189: getfield mPreservedParams : Landroid/support/percent/PercentLayoutHelper$PercentMarginLayoutParams;
      //   192: iconst_1
      //   193: invokestatic access$002 : (Landroid/support/percent/PercentLayoutHelper$PercentMarginLayoutParams;Z)Z
      //   196: pop
      //   197: iload #7
      //   199: ifeq -> 228
      //   202: aload_1
      //   203: aload_1
      //   204: getfield width : I
      //   207: i2f
      //   208: aload_0
      //   209: getfield aspectRatio : F
      //   212: fdiv
      //   213: invokestatic round : (F)I
      //   216: putfield height : I
      //   219: aload_0
      //   220: getfield mPreservedParams : Landroid/support/percent/PercentLayoutHelper$PercentMarginLayoutParams;
      //   223: iconst_1
      //   224: invokestatic access$102 : (Landroid/support/percent/PercentLayoutHelper$PercentMarginLayoutParams;Z)Z
      //   227: pop
      //   228: return
    }
    
    public void fillMarginLayoutParams(View param1View, ViewGroup.MarginLayoutParams param1MarginLayoutParams, int param1Int1, int param1Int2) {
      fillLayoutParams((ViewGroup.LayoutParams)param1MarginLayoutParams, param1Int1, param1Int2);
      this.mPreservedParams.leftMargin = param1MarginLayoutParams.leftMargin;
      this.mPreservedParams.topMargin = param1MarginLayoutParams.topMargin;
      this.mPreservedParams.rightMargin = param1MarginLayoutParams.rightMargin;
      this.mPreservedParams.bottomMargin = param1MarginLayoutParams.bottomMargin;
      MarginLayoutParamsCompat.setMarginStart(this.mPreservedParams, MarginLayoutParamsCompat.getMarginStart(param1MarginLayoutParams));
      MarginLayoutParamsCompat.setMarginEnd(this.mPreservedParams, MarginLayoutParamsCompat.getMarginEnd(param1MarginLayoutParams));
      float f = this.leftMarginPercent;
      if (f >= 0.0F)
        param1MarginLayoutParams.leftMargin = Math.round(param1Int1 * f); 
      f = this.topMarginPercent;
      if (f >= 0.0F)
        param1MarginLayoutParams.topMargin = Math.round(param1Int2 * f); 
      f = this.rightMarginPercent;
      if (f >= 0.0F)
        param1MarginLayoutParams.rightMargin = Math.round(param1Int1 * f); 
      f = this.bottomMarginPercent;
      if (f >= 0.0F)
        param1MarginLayoutParams.bottomMargin = Math.round(param1Int2 * f); 
      param1Int2 = 0;
      f = this.startMarginPercent;
      boolean bool = true;
      if (f >= 0.0F) {
        MarginLayoutParamsCompat.setMarginStart(param1MarginLayoutParams, Math.round(param1Int1 * f));
        param1Int2 = 1;
      } 
      f = this.endMarginPercent;
      if (f >= 0.0F) {
        MarginLayoutParamsCompat.setMarginEnd(param1MarginLayoutParams, Math.round(param1Int1 * f));
        param1Int2 = bool;
      } 
      if (param1Int2 != 0 && param1View != null)
        MarginLayoutParamsCompat.resolveLayoutDirection(param1MarginLayoutParams, ViewCompat.getLayoutDirection(param1View)); 
    }
    
    @Deprecated
    public void fillMarginLayoutParams(ViewGroup.MarginLayoutParams param1MarginLayoutParams, int param1Int1, int param1Int2) {
      fillMarginLayoutParams(null, param1MarginLayoutParams, param1Int1, param1Int2);
    }
    
    public void restoreLayoutParams(ViewGroup.LayoutParams param1LayoutParams) {
      if (!this.mPreservedParams.mIsWidthComputedFromAspectRatio)
        param1LayoutParams.width = this.mPreservedParams.width; 
      if (!this.mPreservedParams.mIsHeightComputedFromAspectRatio)
        param1LayoutParams.height = this.mPreservedParams.height; 
      PercentLayoutHelper.PercentMarginLayoutParams.access$002(this.mPreservedParams, false);
      PercentLayoutHelper.PercentMarginLayoutParams.access$102(this.mPreservedParams, false);
    }
    
    public void restoreMarginLayoutParams(ViewGroup.MarginLayoutParams param1MarginLayoutParams) {
      restoreLayoutParams((ViewGroup.LayoutParams)param1MarginLayoutParams);
      param1MarginLayoutParams.leftMargin = this.mPreservedParams.leftMargin;
      param1MarginLayoutParams.topMargin = this.mPreservedParams.topMargin;
      param1MarginLayoutParams.rightMargin = this.mPreservedParams.rightMargin;
      param1MarginLayoutParams.bottomMargin = this.mPreservedParams.bottomMargin;
      MarginLayoutParamsCompat.setMarginStart(param1MarginLayoutParams, MarginLayoutParamsCompat.getMarginStart(this.mPreservedParams));
      MarginLayoutParamsCompat.setMarginEnd(param1MarginLayoutParams, MarginLayoutParamsCompat.getMarginEnd(this.mPreservedParams));
    }
    
    public String toString() {
      return String.format("PercentLayoutInformation width: %f height %f, margins (%f, %f,  %f, %f, %f, %f)", new Object[] { Float.valueOf(this.widthPercent), Float.valueOf(this.heightPercent), Float.valueOf(this.leftMarginPercent), Float.valueOf(this.topMarginPercent), Float.valueOf(this.rightMarginPercent), Float.valueOf(this.bottomMarginPercent), Float.valueOf(this.startMarginPercent), Float.valueOf(this.endMarginPercent) });
    }
  }
  
  @Deprecated
  public static interface PercentLayoutParams {
    PercentLayoutHelper.PercentLayoutInfo getPercentLayoutInfo();
  }
  
  static class PercentMarginLayoutParams extends ViewGroup.MarginLayoutParams {
    private boolean mIsHeightComputedFromAspectRatio;
    
    private boolean mIsWidthComputedFromAspectRatio;
    
    public PercentMarginLayoutParams(int param1Int1, int param1Int2) {
      super(param1Int1, param1Int2);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\percent\PercentLayoutHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */