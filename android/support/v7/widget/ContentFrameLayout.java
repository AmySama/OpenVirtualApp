package android.support.v7.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;

public class ContentFrameLayout extends FrameLayout {
  private OnAttachListener mAttachListener;
  
  private final Rect mDecorPadding = new Rect();
  
  private TypedValue mFixedHeightMajor;
  
  private TypedValue mFixedHeightMinor;
  
  private TypedValue mFixedWidthMajor;
  
  private TypedValue mFixedWidthMinor;
  
  private TypedValue mMinWidthMajor;
  
  private TypedValue mMinWidthMinor;
  
  public ContentFrameLayout(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public ContentFrameLayout(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public ContentFrameLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void dispatchFitSystemWindows(Rect paramRect) {
    fitSystemWindows(paramRect);
  }
  
  public TypedValue getFixedHeightMajor() {
    if (this.mFixedHeightMajor == null)
      this.mFixedHeightMajor = new TypedValue(); 
    return this.mFixedHeightMajor;
  }
  
  public TypedValue getFixedHeightMinor() {
    if (this.mFixedHeightMinor == null)
      this.mFixedHeightMinor = new TypedValue(); 
    return this.mFixedHeightMinor;
  }
  
  public TypedValue getFixedWidthMajor() {
    if (this.mFixedWidthMajor == null)
      this.mFixedWidthMajor = new TypedValue(); 
    return this.mFixedWidthMajor;
  }
  
  public TypedValue getFixedWidthMinor() {
    if (this.mFixedWidthMinor == null)
      this.mFixedWidthMinor = new TypedValue(); 
    return this.mFixedWidthMinor;
  }
  
  public TypedValue getMinWidthMajor() {
    if (this.mMinWidthMajor == null)
      this.mMinWidthMajor = new TypedValue(); 
    return this.mMinWidthMajor;
  }
  
  public TypedValue getMinWidthMinor() {
    if (this.mMinWidthMinor == null)
      this.mMinWidthMinor = new TypedValue(); 
    return this.mMinWidthMinor;
  }
  
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    OnAttachListener onAttachListener = this.mAttachListener;
    if (onAttachListener != null)
      onAttachListener.onAttachedFromWindow(); 
  }
  
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    OnAttachListener onAttachListener = this.mAttachListener;
    if (onAttachListener != null)
      onAttachListener.onDetachedFromWindow(); 
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual getContext : ()Landroid/content/Context;
    //   4: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   7: invokevirtual getDisplayMetrics : ()Landroid/util/DisplayMetrics;
    //   10: astore_3
    //   11: aload_3
    //   12: getfield widthPixels : I
    //   15: istore #4
    //   17: aload_3
    //   18: getfield heightPixels : I
    //   21: istore #5
    //   23: iconst_1
    //   24: istore #6
    //   26: iload #4
    //   28: iload #5
    //   30: if_icmpge -> 39
    //   33: iconst_1
    //   34: istore #5
    //   36: goto -> 42
    //   39: iconst_0
    //   40: istore #5
    //   42: iload_1
    //   43: invokestatic getMode : (I)I
    //   46: istore #7
    //   48: iload_2
    //   49: invokestatic getMode : (I)I
    //   52: istore #8
    //   54: iload #7
    //   56: ldc -2147483648
    //   58: if_icmpne -> 194
    //   61: iload #5
    //   63: ifeq -> 75
    //   66: aload_0
    //   67: getfield mFixedWidthMinor : Landroid/util/TypedValue;
    //   70: astore #9
    //   72: goto -> 81
    //   75: aload_0
    //   76: getfield mFixedWidthMajor : Landroid/util/TypedValue;
    //   79: astore #9
    //   81: aload #9
    //   83: ifnull -> 194
    //   86: aload #9
    //   88: getfield type : I
    //   91: ifeq -> 194
    //   94: aload #9
    //   96: getfield type : I
    //   99: iconst_5
    //   100: if_icmpne -> 119
    //   103: aload #9
    //   105: aload_3
    //   106: invokevirtual getDimension : (Landroid/util/DisplayMetrics;)F
    //   109: fstore #10
    //   111: fload #10
    //   113: f2i
    //   114: istore #4
    //   116: goto -> 152
    //   119: aload #9
    //   121: getfield type : I
    //   124: bipush #6
    //   126: if_icmpne -> 149
    //   129: aload #9
    //   131: aload_3
    //   132: getfield widthPixels : I
    //   135: i2f
    //   136: aload_3
    //   137: getfield widthPixels : I
    //   140: i2f
    //   141: invokevirtual getFraction : (FF)F
    //   144: fstore #10
    //   146: goto -> 111
    //   149: iconst_0
    //   150: istore #4
    //   152: iload #4
    //   154: ifle -> 194
    //   157: iload #4
    //   159: aload_0
    //   160: getfield mDecorPadding : Landroid/graphics/Rect;
    //   163: getfield left : I
    //   166: aload_0
    //   167: getfield mDecorPadding : Landroid/graphics/Rect;
    //   170: getfield right : I
    //   173: iadd
    //   174: isub
    //   175: iload_1
    //   176: invokestatic getSize : (I)I
    //   179: invokestatic min : (II)I
    //   182: ldc 1073741824
    //   184: invokestatic makeMeasureSpec : (II)I
    //   187: istore #11
    //   189: iconst_1
    //   190: istore_1
    //   191: goto -> 203
    //   194: iconst_0
    //   195: istore #4
    //   197: iload_1
    //   198: istore #11
    //   200: iload #4
    //   202: istore_1
    //   203: iload_2
    //   204: istore #4
    //   206: iload #8
    //   208: ldc -2147483648
    //   210: if_icmpne -> 350
    //   213: iload #5
    //   215: ifeq -> 227
    //   218: aload_0
    //   219: getfield mFixedHeightMajor : Landroid/util/TypedValue;
    //   222: astore #9
    //   224: goto -> 233
    //   227: aload_0
    //   228: getfield mFixedHeightMinor : Landroid/util/TypedValue;
    //   231: astore #9
    //   233: iload_2
    //   234: istore #4
    //   236: aload #9
    //   238: ifnull -> 350
    //   241: iload_2
    //   242: istore #4
    //   244: aload #9
    //   246: getfield type : I
    //   249: ifeq -> 350
    //   252: aload #9
    //   254: getfield type : I
    //   257: iconst_5
    //   258: if_icmpne -> 277
    //   261: aload #9
    //   263: aload_3
    //   264: invokevirtual getDimension : (Landroid/util/DisplayMetrics;)F
    //   267: fstore #10
    //   269: fload #10
    //   271: f2i
    //   272: istore #8
    //   274: goto -> 310
    //   277: aload #9
    //   279: getfield type : I
    //   282: bipush #6
    //   284: if_icmpne -> 307
    //   287: aload #9
    //   289: aload_3
    //   290: getfield heightPixels : I
    //   293: i2f
    //   294: aload_3
    //   295: getfield heightPixels : I
    //   298: i2f
    //   299: invokevirtual getFraction : (FF)F
    //   302: fstore #10
    //   304: goto -> 269
    //   307: iconst_0
    //   308: istore #8
    //   310: iload_2
    //   311: istore #4
    //   313: iload #8
    //   315: ifle -> 350
    //   318: iload #8
    //   320: aload_0
    //   321: getfield mDecorPadding : Landroid/graphics/Rect;
    //   324: getfield top : I
    //   327: aload_0
    //   328: getfield mDecorPadding : Landroid/graphics/Rect;
    //   331: getfield bottom : I
    //   334: iadd
    //   335: isub
    //   336: iload_2
    //   337: invokestatic getSize : (I)I
    //   340: invokestatic min : (II)I
    //   343: ldc 1073741824
    //   345: invokestatic makeMeasureSpec : (II)I
    //   348: istore #4
    //   350: aload_0
    //   351: iload #11
    //   353: iload #4
    //   355: invokespecial onMeasure : (II)V
    //   358: aload_0
    //   359: invokevirtual getMeasuredWidth : ()I
    //   362: istore #8
    //   364: iload #8
    //   366: ldc 1073741824
    //   368: invokestatic makeMeasureSpec : (II)I
    //   371: istore #11
    //   373: iload_1
    //   374: ifne -> 516
    //   377: iload #7
    //   379: ldc -2147483648
    //   381: if_icmpne -> 516
    //   384: iload #5
    //   386: ifeq -> 398
    //   389: aload_0
    //   390: getfield mMinWidthMinor : Landroid/util/TypedValue;
    //   393: astore #9
    //   395: goto -> 404
    //   398: aload_0
    //   399: getfield mMinWidthMajor : Landroid/util/TypedValue;
    //   402: astore #9
    //   404: aload #9
    //   406: ifnull -> 516
    //   409: aload #9
    //   411: getfield type : I
    //   414: ifeq -> 516
    //   417: aload #9
    //   419: getfield type : I
    //   422: iconst_5
    //   423: if_icmpne -> 441
    //   426: aload #9
    //   428: aload_3
    //   429: invokevirtual getDimension : (Landroid/util/DisplayMetrics;)F
    //   432: fstore #10
    //   434: fload #10
    //   436: f2i
    //   437: istore_1
    //   438: goto -> 473
    //   441: aload #9
    //   443: getfield type : I
    //   446: bipush #6
    //   448: if_icmpne -> 471
    //   451: aload #9
    //   453: aload_3
    //   454: getfield widthPixels : I
    //   457: i2f
    //   458: aload_3
    //   459: getfield widthPixels : I
    //   462: i2f
    //   463: invokevirtual getFraction : (FF)F
    //   466: fstore #10
    //   468: goto -> 434
    //   471: iconst_0
    //   472: istore_1
    //   473: iload_1
    //   474: istore_2
    //   475: iload_1
    //   476: ifle -> 497
    //   479: iload_1
    //   480: aload_0
    //   481: getfield mDecorPadding : Landroid/graphics/Rect;
    //   484: getfield left : I
    //   487: aload_0
    //   488: getfield mDecorPadding : Landroid/graphics/Rect;
    //   491: getfield right : I
    //   494: iadd
    //   495: isub
    //   496: istore_2
    //   497: iload #8
    //   499: iload_2
    //   500: if_icmpge -> 516
    //   503: iload_2
    //   504: ldc 1073741824
    //   506: invokestatic makeMeasureSpec : (II)I
    //   509: istore_2
    //   510: iload #6
    //   512: istore_1
    //   513: goto -> 521
    //   516: iconst_0
    //   517: istore_1
    //   518: iload #11
    //   520: istore_2
    //   521: iload_1
    //   522: ifeq -> 532
    //   525: aload_0
    //   526: iload_2
    //   527: iload #4
    //   529: invokespecial onMeasure : (II)V
    //   532: return
  }
  
  public void setAttachListener(OnAttachListener paramOnAttachListener) {
    this.mAttachListener = paramOnAttachListener;
  }
  
  public void setDecorPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.mDecorPadding.set(paramInt1, paramInt2, paramInt3, paramInt4);
    if (ViewCompat.isLaidOut((View)this))
      requestLayout(); 
  }
  
  public static interface OnAttachListener {
    void onAttachedFromWindow();
    
    void onDetachedFromWindow();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\ContentFrameLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */