package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LinearLayoutCompat extends ViewGroup {
  public static final int HORIZONTAL = 0;
  
  private static final int INDEX_BOTTOM = 2;
  
  private static final int INDEX_CENTER_VERTICAL = 0;
  
  private static final int INDEX_FILL = 3;
  
  private static final int INDEX_TOP = 1;
  
  public static final int SHOW_DIVIDER_BEGINNING = 1;
  
  public static final int SHOW_DIVIDER_END = 4;
  
  public static final int SHOW_DIVIDER_MIDDLE = 2;
  
  public static final int SHOW_DIVIDER_NONE = 0;
  
  public static final int VERTICAL = 1;
  
  private static final int VERTICAL_GRAVITY_COUNT = 4;
  
  private boolean mBaselineAligned = true;
  
  private int mBaselineAlignedChildIndex = -1;
  
  private int mBaselineChildTop = 0;
  
  private Drawable mDivider;
  
  private int mDividerHeight;
  
  private int mDividerPadding;
  
  private int mDividerWidth;
  
  private int mGravity = 8388659;
  
  private int[] mMaxAscent;
  
  private int[] mMaxDescent;
  
  private int mOrientation;
  
  private int mShowDividers;
  
  private int mTotalLength;
  
  private boolean mUseLargestChild;
  
  private float mWeightSum;
  
  public LinearLayoutCompat(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public LinearLayoutCompat(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public LinearLayoutCompat(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.LinearLayoutCompat, paramInt, 0);
    paramInt = tintTypedArray.getInt(R.styleable.LinearLayoutCompat_android_orientation, -1);
    if (paramInt >= 0)
      setOrientation(paramInt); 
    paramInt = tintTypedArray.getInt(R.styleable.LinearLayoutCompat_android_gravity, -1);
    if (paramInt >= 0)
      setGravity(paramInt); 
    boolean bool = tintTypedArray.getBoolean(R.styleable.LinearLayoutCompat_android_baselineAligned, true);
    if (!bool)
      setBaselineAligned(bool); 
    this.mWeightSum = tintTypedArray.getFloat(R.styleable.LinearLayoutCompat_android_weightSum, -1.0F);
    this.mBaselineAlignedChildIndex = tintTypedArray.getInt(R.styleable.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
    this.mUseLargestChild = tintTypedArray.getBoolean(R.styleable.LinearLayoutCompat_measureWithLargestChild, false);
    setDividerDrawable(tintTypedArray.getDrawable(R.styleable.LinearLayoutCompat_divider));
    this.mShowDividers = tintTypedArray.getInt(R.styleable.LinearLayoutCompat_showDividers, 0);
    this.mDividerPadding = tintTypedArray.getDimensionPixelSize(R.styleable.LinearLayoutCompat_dividerPadding, 0);
    tintTypedArray.recycle();
  }
  
  private void forceUniformHeight(int paramInt1, int paramInt2) {
    int i = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
    for (byte b = 0; b < paramInt1; b++) {
      View view = getVirtualChildAt(b);
      if (view.getVisibility() != 8) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.height == -1) {
          int j = layoutParams.width;
          layoutParams.width = view.getMeasuredWidth();
          measureChildWithMargins(view, paramInt2, 0, i, 0);
          layoutParams.width = j;
        } 
      } 
    } 
  }
  
  private void forceUniformWidth(int paramInt1, int paramInt2) {
    int i = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
    for (byte b = 0; b < paramInt1; b++) {
      View view = getVirtualChildAt(b);
      if (view.getVisibility() != 8) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (layoutParams.width == -1) {
          int j = layoutParams.height;
          layoutParams.height = view.getMeasuredHeight();
          measureChildWithMargins(view, i, 0, paramInt2, 0);
          layoutParams.height = j;
        } 
      } 
    } 
  }
  
  private void setChildFrame(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    paramView.layout(paramInt1, paramInt2, paramInt3 + paramInt1, paramInt4 + paramInt2);
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    return paramLayoutParams instanceof LayoutParams;
  }
  
  void drawDividersHorizontal(Canvas paramCanvas) {
    int i = getVirtualChildCount();
    boolean bool = ViewUtils.isLayoutRtl((View)this);
    int j;
    for (j = 0; j < i; j++) {
      View view = getVirtualChildAt(j);
      if (view != null && view.getVisibility() != 8 && hasDividerBeforeChildAt(j)) {
        int k;
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (bool) {
          k = view.getRight() + layoutParams.rightMargin;
        } else {
          k = view.getLeft() - layoutParams.leftMargin - this.mDividerWidth;
        } 
        drawVerticalDivider(paramCanvas, k);
      } 
    } 
    if (hasDividerBeforeChildAt(i)) {
      View view = getVirtualChildAt(i - 1);
      if (view == null) {
        if (bool) {
          j = getPaddingLeft();
        } else {
          j = getWidth() - getPaddingRight();
          int k = this.mDividerWidth;
          j -= k;
        } 
      } else {
        int k;
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (bool) {
          j = view.getLeft() - layoutParams.leftMargin;
          k = this.mDividerWidth;
        } else {
          j = view.getRight() + layoutParams.rightMargin;
          drawVerticalDivider(paramCanvas, j);
        } 
        j -= k;
      } 
    } else {
      return;
    } 
    drawVerticalDivider(paramCanvas, j);
  }
  
  void drawDividersVertical(Canvas paramCanvas) {
    int i = getVirtualChildCount();
    int j;
    for (j = 0; j < i; j++) {
      View view = getVirtualChildAt(j);
      if (view != null && view.getVisibility() != 8 && hasDividerBeforeChildAt(j)) {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        drawHorizontalDivider(paramCanvas, view.getTop() - layoutParams.topMargin - this.mDividerHeight);
      } 
    } 
    if (hasDividerBeforeChildAt(i)) {
      View view = getVirtualChildAt(i - 1);
      if (view == null) {
        j = getHeight() - getPaddingBottom() - this.mDividerHeight;
      } else {
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        j = view.getBottom() + layoutParams.bottomMargin;
      } 
      drawHorizontalDivider(paramCanvas, j);
    } 
  }
  
  void drawHorizontalDivider(Canvas paramCanvas, int paramInt) {
    this.mDivider.setBounds(getPaddingLeft() + this.mDividerPadding, paramInt, getWidth() - getPaddingRight() - this.mDividerPadding, this.mDividerHeight + paramInt);
    this.mDivider.draw(paramCanvas);
  }
  
  void drawVerticalDivider(Canvas paramCanvas, int paramInt) {
    this.mDivider.setBounds(paramInt, getPaddingTop() + this.mDividerPadding, this.mDividerWidth + paramInt, getHeight() - getPaddingBottom() - this.mDividerPadding);
    this.mDivider.draw(paramCanvas);
  }
  
  protected LayoutParams generateDefaultLayoutParams() {
    int i = this.mOrientation;
    return (i == 0) ? new LayoutParams(-2, -2) : ((i == 1) ? new LayoutParams(-1, -2) : null);
  }
  
  public LayoutParams generateLayoutParams(AttributeSet paramAttributeSet) {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    return new LayoutParams(paramLayoutParams);
  }
  
  public int getBaseline() {
    if (this.mBaselineAlignedChildIndex < 0)
      return super.getBaseline(); 
    int i = getChildCount();
    int j = this.mBaselineAlignedChildIndex;
    if (i > j) {
      View view = getChildAt(j);
      int k = view.getBaseline();
      if (k == -1) {
        if (this.mBaselineAlignedChildIndex == 0)
          return -1; 
        throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
      } 
      i = this.mBaselineChildTop;
      j = i;
      if (this.mOrientation == 1) {
        int m = this.mGravity & 0x70;
        j = i;
        if (m != 48)
          if (m != 16) {
            if (m != 80) {
              j = i;
            } else {
              j = getBottom() - getTop() - getPaddingBottom() - this.mTotalLength;
            } 
          } else {
            j = i + (getBottom() - getTop() - getPaddingTop() - getPaddingBottom() - this.mTotalLength) / 2;
          }  
      } 
      return j + ((LayoutParams)view.getLayoutParams()).topMargin + k;
    } 
    throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
  }
  
  public int getBaselineAlignedChildIndex() {
    return this.mBaselineAlignedChildIndex;
  }
  
  int getChildrenSkipCount(View paramView, int paramInt) {
    return 0;
  }
  
  public Drawable getDividerDrawable() {
    return this.mDivider;
  }
  
  public int getDividerPadding() {
    return this.mDividerPadding;
  }
  
  public int getDividerWidth() {
    return this.mDividerWidth;
  }
  
  public int getGravity() {
    return this.mGravity;
  }
  
  int getLocationOffset(View paramView) {
    return 0;
  }
  
  int getNextLocationOffset(View paramView) {
    return 0;
  }
  
  public int getOrientation() {
    return this.mOrientation;
  }
  
  public int getShowDividers() {
    return this.mShowDividers;
  }
  
  View getVirtualChildAt(int paramInt) {
    return getChildAt(paramInt);
  }
  
  int getVirtualChildCount() {
    return getChildCount();
  }
  
  public float getWeightSum() {
    return this.mWeightSum;
  }
  
  protected boolean hasDividerBeforeChildAt(int paramInt) {
    boolean bool1 = false;
    boolean bool2 = false;
    boolean bool3 = false;
    if (paramInt == 0) {
      if ((this.mShowDividers & 0x1) != 0)
        bool3 = true; 
      return bool3;
    } 
    if (paramInt == getChildCount()) {
      bool3 = bool1;
      if ((this.mShowDividers & 0x4) != 0)
        bool3 = true; 
      return bool3;
    } 
    bool3 = bool2;
    if ((this.mShowDividers & 0x2) != 0) {
      paramInt--;
      while (true) {
        bool3 = bool2;
        if (paramInt >= 0) {
          if (getChildAt(paramInt).getVisibility() != 8) {
            bool3 = true;
            break;
          } 
          paramInt--;
          continue;
        } 
        break;
      } 
    } 
    return bool3;
  }
  
  public boolean isBaselineAligned() {
    return this.mBaselineAligned;
  }
  
  public boolean isMeasureWithLargestChildEnabled() {
    return this.mUseLargestChild;
  }
  
  void layoutHorizontal(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    byte b1;
    byte b2;
    boolean bool1 = ViewUtils.isLayoutRtl((View)this);
    int i = getPaddingTop();
    int j = paramInt4 - paramInt2;
    int k = getPaddingBottom();
    int m = getPaddingBottom();
    int n = getVirtualChildCount();
    paramInt2 = this.mGravity;
    paramInt4 = paramInt2 & 0x70;
    boolean bool2 = this.mBaselineAligned;
    int[] arrayOfInt1 = this.mMaxAscent;
    int[] arrayOfInt2 = this.mMaxDescent;
    paramInt2 = GravityCompat.getAbsoluteGravity(0x800007 & paramInt2, ViewCompat.getLayoutDirection((View)this));
    if (paramInt2 != 1) {
      if (paramInt2 != 5) {
        paramInt2 = getPaddingLeft();
      } else {
        paramInt2 = getPaddingLeft() + paramInt3 - paramInt1 - this.mTotalLength;
      } 
    } else {
      paramInt2 = getPaddingLeft() + (paramInt3 - paramInt1 - this.mTotalLength) / 2;
    } 
    if (bool1) {
      b1 = n - 1;
      b2 = -1;
    } else {
      b1 = 0;
      b2 = 1;
    } 
    int i1 = 0;
    paramInt3 = paramInt4;
    paramInt4 = i;
    while (i1 < n) {
      int i2 = b1 + b2 * i1;
      View view = getVirtualChildAt(i2);
      if (view == null) {
        paramInt2 += measureNullChild(i2);
      } else if (view.getVisibility() != 8) {
        int i3 = view.getMeasuredWidth();
        int i4 = view.getMeasuredHeight();
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        if (bool2 && layoutParams.height != -1) {
          i5 = view.getBaseline();
        } else {
          i5 = -1;
        } 
        int i6 = layoutParams.gravity;
        paramInt1 = i6;
        if (i6 < 0)
          paramInt1 = paramInt3; 
        paramInt1 &= 0x70;
        if (paramInt1 != 16) {
          if (paramInt1 != 48) {
            if (paramInt1 != 80) {
              paramInt1 = paramInt4;
            } else {
              i6 = j - k - i4 - layoutParams.bottomMargin;
              paramInt1 = i6;
              if (i5 != -1) {
                paramInt1 = view.getMeasuredHeight();
                paramInt1 = i6 - arrayOfInt2[2] - paramInt1 - i5;
              } 
            } 
          } else {
            i6 = layoutParams.topMargin + paramInt4;
            paramInt1 = i6;
            if (i5 != -1)
              paramInt1 = i6 + arrayOfInt1[1] - i5; 
          } 
        } else {
          paramInt1 = (j - i - m - i4) / 2 + paramInt4 + layoutParams.topMargin - layoutParams.bottomMargin;
        } 
        int i5 = paramInt2;
        if (hasDividerBeforeChildAt(i2))
          i5 = paramInt2 + this.mDividerWidth; 
        paramInt2 = layoutParams.leftMargin + i5;
        setChildFrame(view, paramInt2 + getLocationOffset(view), paramInt1, i3, i4);
        paramInt1 = layoutParams.rightMargin;
        i5 = getNextLocationOffset(view);
        i1 += getChildrenSkipCount(view, i2);
        paramInt2 += i3 + paramInt1 + i5;
      } 
      i1++;
    } 
  }
  
  void layoutVertical(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int i = getPaddingLeft();
    int j = paramInt3 - paramInt1;
    int k = getPaddingRight();
    int m = getPaddingRight();
    int n = getVirtualChildCount();
    int i1 = this.mGravity;
    paramInt1 = i1 & 0x70;
    if (paramInt1 != 16) {
      if (paramInt1 != 80) {
        paramInt1 = getPaddingTop();
      } else {
        paramInt1 = getPaddingTop() + paramInt4 - paramInt2 - this.mTotalLength;
      } 
    } else {
      paramInt1 = getPaddingTop() + (paramInt4 - paramInt2 - this.mTotalLength) / 2;
    } 
    paramInt2 = 0;
    while (paramInt2 < n) {
      View view = getVirtualChildAt(paramInt2);
      if (view == null) {
        paramInt3 = paramInt1 + measureNullChild(paramInt2);
        paramInt4 = paramInt2;
      } else {
        paramInt3 = paramInt1;
        paramInt4 = paramInt2;
        if (view.getVisibility() != 8) {
          int i2 = view.getMeasuredWidth();
          int i3 = view.getMeasuredHeight();
          LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
          paramInt4 = layoutParams.gravity;
          paramInt3 = paramInt4;
          if (paramInt4 < 0)
            paramInt3 = i1 & 0x800007; 
          paramInt3 = GravityCompat.getAbsoluteGravity(paramInt3, ViewCompat.getLayoutDirection((View)this)) & 0x7;
          if (paramInt3 != 1) {
            if (paramInt3 != 5) {
              paramInt3 = layoutParams.leftMargin + i;
            } else {
              paramInt4 = j - k - i2;
              paramInt3 = layoutParams.rightMargin;
              paramInt3 = paramInt4 - paramInt3;
            } 
          } else {
            paramInt4 = (j - i - m - i2) / 2 + i + layoutParams.leftMargin;
            paramInt3 = layoutParams.rightMargin;
            paramInt3 = paramInt4 - paramInt3;
          } 
          paramInt4 = paramInt1;
          if (hasDividerBeforeChildAt(paramInt2))
            paramInt4 = paramInt1 + this.mDividerHeight; 
          paramInt1 = paramInt4 + layoutParams.topMargin;
          setChildFrame(view, paramInt3, paramInt1 + getLocationOffset(view), i2, i3);
          paramInt3 = layoutParams.bottomMargin;
          i2 = getNextLocationOffset(view);
          paramInt4 = paramInt2 + getChildrenSkipCount(view, paramInt2);
          paramInt3 = paramInt1 + i3 + paramInt3 + i2;
        } 
      } 
      paramInt2 = paramInt4 + 1;
      paramInt1 = paramInt3;
    } 
  }
  
  void measureChildBeforeLayout(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    measureChildWithMargins(paramView, paramInt2, paramInt3, paramInt4, paramInt5);
  }
  
  void measureHorizontal(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: iconst_0
    //   2: putfield mTotalLength : I
    //   5: aload_0
    //   6: invokevirtual getVirtualChildCount : ()I
    //   9: istore_3
    //   10: iload_1
    //   11: invokestatic getMode : (I)I
    //   14: istore #4
    //   16: iload_2
    //   17: invokestatic getMode : (I)I
    //   20: istore #5
    //   22: aload_0
    //   23: getfield mMaxAscent : [I
    //   26: ifnull -> 36
    //   29: aload_0
    //   30: getfield mMaxDescent : [I
    //   33: ifnonnull -> 50
    //   36: aload_0
    //   37: iconst_4
    //   38: newarray int
    //   40: putfield mMaxAscent : [I
    //   43: aload_0
    //   44: iconst_4
    //   45: newarray int
    //   47: putfield mMaxDescent : [I
    //   50: aload_0
    //   51: getfield mMaxAscent : [I
    //   54: astore #6
    //   56: aload_0
    //   57: getfield mMaxDescent : [I
    //   60: astore #7
    //   62: aload #6
    //   64: iconst_3
    //   65: iconst_m1
    //   66: iastore
    //   67: aload #6
    //   69: iconst_2
    //   70: iconst_m1
    //   71: iastore
    //   72: aload #6
    //   74: iconst_1
    //   75: iconst_m1
    //   76: iastore
    //   77: aload #6
    //   79: iconst_0
    //   80: iconst_m1
    //   81: iastore
    //   82: aload #7
    //   84: iconst_3
    //   85: iconst_m1
    //   86: iastore
    //   87: aload #7
    //   89: iconst_2
    //   90: iconst_m1
    //   91: iastore
    //   92: aload #7
    //   94: iconst_1
    //   95: iconst_m1
    //   96: iastore
    //   97: aload #7
    //   99: iconst_0
    //   100: iconst_m1
    //   101: iastore
    //   102: aload_0
    //   103: getfield mBaselineAligned : Z
    //   106: istore #8
    //   108: aload_0
    //   109: getfield mUseLargestChild : Z
    //   112: istore #9
    //   114: iload #4
    //   116: ldc 1073741824
    //   118: if_icmpne -> 127
    //   121: iconst_1
    //   122: istore #10
    //   124: goto -> 130
    //   127: iconst_0
    //   128: istore #10
    //   130: fconst_0
    //   131: fstore #11
    //   133: iconst_0
    //   134: istore #12
    //   136: iconst_0
    //   137: istore #13
    //   139: iconst_0
    //   140: istore #14
    //   142: iconst_0
    //   143: istore #15
    //   145: iconst_0
    //   146: istore #16
    //   148: iconst_0
    //   149: istore #17
    //   151: iconst_0
    //   152: istore #18
    //   154: iconst_1
    //   155: istore #19
    //   157: iconst_0
    //   158: istore #20
    //   160: iload #12
    //   162: iload_3
    //   163: if_icmpge -> 848
    //   166: aload_0
    //   167: iload #12
    //   169: invokevirtual getVirtualChildAt : (I)Landroid/view/View;
    //   172: astore #21
    //   174: aload #21
    //   176: ifnonnull -> 197
    //   179: aload_0
    //   180: aload_0
    //   181: getfield mTotalLength : I
    //   184: aload_0
    //   185: iload #12
    //   187: invokevirtual measureNullChild : (I)I
    //   190: iadd
    //   191: putfield mTotalLength : I
    //   194: goto -> 842
    //   197: aload #21
    //   199: invokevirtual getVisibility : ()I
    //   202: bipush #8
    //   204: if_icmpne -> 223
    //   207: iload #12
    //   209: aload_0
    //   210: aload #21
    //   212: iload #12
    //   214: invokevirtual getChildrenSkipCount : (Landroid/view/View;I)I
    //   217: iadd
    //   218: istore #12
    //   220: goto -> 194
    //   223: aload_0
    //   224: iload #12
    //   226: invokevirtual hasDividerBeforeChildAt : (I)Z
    //   229: ifeq -> 245
    //   232: aload_0
    //   233: aload_0
    //   234: getfield mTotalLength : I
    //   237: aload_0
    //   238: getfield mDividerWidth : I
    //   241: iadd
    //   242: putfield mTotalLength : I
    //   245: aload #21
    //   247: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   250: checkcast android/support/v7/widget/LinearLayoutCompat$LayoutParams
    //   253: astore #22
    //   255: fload #11
    //   257: aload #22
    //   259: getfield weight : F
    //   262: fadd
    //   263: fstore #11
    //   265: iload #4
    //   267: ldc 1073741824
    //   269: if_icmpne -> 381
    //   272: aload #22
    //   274: getfield width : I
    //   277: ifne -> 381
    //   280: aload #22
    //   282: getfield weight : F
    //   285: fconst_0
    //   286: fcmpl
    //   287: ifle -> 381
    //   290: iload #10
    //   292: ifeq -> 318
    //   295: aload_0
    //   296: aload_0
    //   297: getfield mTotalLength : I
    //   300: aload #22
    //   302: getfield leftMargin : I
    //   305: aload #22
    //   307: getfield rightMargin : I
    //   310: iadd
    //   311: iadd
    //   312: putfield mTotalLength : I
    //   315: goto -> 347
    //   318: aload_0
    //   319: getfield mTotalLength : I
    //   322: istore #23
    //   324: aload_0
    //   325: iload #23
    //   327: aload #22
    //   329: getfield leftMargin : I
    //   332: iload #23
    //   334: iadd
    //   335: aload #22
    //   337: getfield rightMargin : I
    //   340: iadd
    //   341: invokestatic max : (II)I
    //   344: putfield mTotalLength : I
    //   347: iload #8
    //   349: ifeq -> 375
    //   352: iconst_0
    //   353: iconst_0
    //   354: invokestatic makeMeasureSpec : (II)I
    //   357: istore #23
    //   359: aload #21
    //   361: iload #23
    //   363: iload #23
    //   365: invokevirtual measure : (II)V
    //   368: iload #13
    //   370: istore #23
    //   372: goto -> 566
    //   375: iconst_1
    //   376: istore #17
    //   378: goto -> 570
    //   381: aload #22
    //   383: getfield width : I
    //   386: ifne -> 412
    //   389: aload #22
    //   391: getfield weight : F
    //   394: fconst_0
    //   395: fcmpl
    //   396: ifle -> 412
    //   399: aload #22
    //   401: bipush #-2
    //   403: putfield width : I
    //   406: iconst_0
    //   407: istore #23
    //   409: goto -> 417
    //   412: ldc_w -2147483648
    //   415: istore #23
    //   417: fload #11
    //   419: fconst_0
    //   420: fcmpl
    //   421: ifne -> 433
    //   424: aload_0
    //   425: getfield mTotalLength : I
    //   428: istore #24
    //   430: goto -> 436
    //   433: iconst_0
    //   434: istore #24
    //   436: aload_0
    //   437: aload #21
    //   439: iload #12
    //   441: iload_1
    //   442: iload #24
    //   444: iload_2
    //   445: iconst_0
    //   446: invokevirtual measureChildBeforeLayout : (Landroid/view/View;IIIII)V
    //   449: iload #23
    //   451: ldc_w -2147483648
    //   454: if_icmpeq -> 464
    //   457: aload #22
    //   459: iload #23
    //   461: putfield width : I
    //   464: aload #21
    //   466: invokevirtual getMeasuredWidth : ()I
    //   469: istore #24
    //   471: iload #10
    //   473: ifeq -> 509
    //   476: aload_0
    //   477: aload_0
    //   478: getfield mTotalLength : I
    //   481: aload #22
    //   483: getfield leftMargin : I
    //   486: iload #24
    //   488: iadd
    //   489: aload #22
    //   491: getfield rightMargin : I
    //   494: iadd
    //   495: aload_0
    //   496: aload #21
    //   498: invokevirtual getNextLocationOffset : (Landroid/view/View;)I
    //   501: iadd
    //   502: iadd
    //   503: putfield mTotalLength : I
    //   506: goto -> 548
    //   509: aload_0
    //   510: getfield mTotalLength : I
    //   513: istore #23
    //   515: aload_0
    //   516: iload #23
    //   518: iload #23
    //   520: iload #24
    //   522: iadd
    //   523: aload #22
    //   525: getfield leftMargin : I
    //   528: iadd
    //   529: aload #22
    //   531: getfield rightMargin : I
    //   534: iadd
    //   535: aload_0
    //   536: aload #21
    //   538: invokevirtual getNextLocationOffset : (Landroid/view/View;)I
    //   541: iadd
    //   542: invokestatic max : (II)I
    //   545: putfield mTotalLength : I
    //   548: iload #13
    //   550: istore #23
    //   552: iload #9
    //   554: ifeq -> 566
    //   557: iload #24
    //   559: iload #13
    //   561: invokestatic max : (II)I
    //   564: istore #23
    //   566: iload #23
    //   568: istore #13
    //   570: iload #12
    //   572: istore #25
    //   574: iload #5
    //   576: ldc 1073741824
    //   578: if_icmpeq -> 599
    //   581: aload #22
    //   583: getfield height : I
    //   586: iconst_m1
    //   587: if_icmpne -> 599
    //   590: iconst_1
    //   591: istore #12
    //   593: iconst_1
    //   594: istore #20
    //   596: goto -> 602
    //   599: iconst_0
    //   600: istore #12
    //   602: aload #22
    //   604: getfield topMargin : I
    //   607: aload #22
    //   609: getfield bottomMargin : I
    //   612: iadd
    //   613: istore #24
    //   615: aload #21
    //   617: invokevirtual getMeasuredHeight : ()I
    //   620: iload #24
    //   622: iadd
    //   623: istore #23
    //   625: iload #18
    //   627: aload #21
    //   629: invokevirtual getMeasuredState : ()I
    //   632: invokestatic combineMeasuredStates : (II)I
    //   635: istore #26
    //   637: iload #8
    //   639: ifeq -> 726
    //   642: aload #21
    //   644: invokevirtual getBaseline : ()I
    //   647: istore #27
    //   649: iload #27
    //   651: iconst_m1
    //   652: if_icmpeq -> 726
    //   655: aload #22
    //   657: getfield gravity : I
    //   660: ifge -> 672
    //   663: aload_0
    //   664: getfield mGravity : I
    //   667: istore #18
    //   669: goto -> 679
    //   672: aload #22
    //   674: getfield gravity : I
    //   677: istore #18
    //   679: iload #18
    //   681: bipush #112
    //   683: iand
    //   684: iconst_4
    //   685: ishr
    //   686: bipush #-2
    //   688: iand
    //   689: iconst_1
    //   690: ishr
    //   691: istore #18
    //   693: aload #6
    //   695: iload #18
    //   697: aload #6
    //   699: iload #18
    //   701: iaload
    //   702: iload #27
    //   704: invokestatic max : (II)I
    //   707: iastore
    //   708: aload #7
    //   710: iload #18
    //   712: aload #7
    //   714: iload #18
    //   716: iaload
    //   717: iload #23
    //   719: iload #27
    //   721: isub
    //   722: invokestatic max : (II)I
    //   725: iastore
    //   726: iload #14
    //   728: iload #23
    //   730: invokestatic max : (II)I
    //   733: istore #14
    //   735: iload #19
    //   737: ifeq -> 755
    //   740: aload #22
    //   742: getfield height : I
    //   745: iconst_m1
    //   746: if_icmpne -> 755
    //   749: iconst_1
    //   750: istore #19
    //   752: goto -> 758
    //   755: iconst_0
    //   756: istore #19
    //   758: aload #22
    //   760: getfield weight : F
    //   763: fconst_0
    //   764: fcmpl
    //   765: ifle -> 792
    //   768: iload #12
    //   770: ifeq -> 780
    //   773: iload #24
    //   775: istore #23
    //   777: goto -> 780
    //   780: iload #16
    //   782: iload #23
    //   784: invokestatic max : (II)I
    //   787: istore #12
    //   789: goto -> 817
    //   792: iload #12
    //   794: ifeq -> 800
    //   797: goto -> 804
    //   800: iload #23
    //   802: istore #24
    //   804: iload #15
    //   806: iload #24
    //   808: invokestatic max : (II)I
    //   811: istore #15
    //   813: iload #16
    //   815: istore #12
    //   817: aload_0
    //   818: aload #21
    //   820: iload #25
    //   822: invokevirtual getChildrenSkipCount : (Landroid/view/View;I)I
    //   825: iload #25
    //   827: iadd
    //   828: istore #23
    //   830: iload #26
    //   832: istore #18
    //   834: iload #12
    //   836: istore #16
    //   838: iload #23
    //   840: istore #12
    //   842: iinc #12, 1
    //   845: goto -> 160
    //   848: aload_0
    //   849: getfield mTotalLength : I
    //   852: ifle -> 876
    //   855: aload_0
    //   856: iload_3
    //   857: invokevirtual hasDividerBeforeChildAt : (I)Z
    //   860: ifeq -> 876
    //   863: aload_0
    //   864: aload_0
    //   865: getfield mTotalLength : I
    //   868: aload_0
    //   869: getfield mDividerWidth : I
    //   872: iadd
    //   873: putfield mTotalLength : I
    //   876: aload #6
    //   878: iconst_1
    //   879: iaload
    //   880: iconst_m1
    //   881: if_icmpne -> 914
    //   884: aload #6
    //   886: iconst_0
    //   887: iaload
    //   888: iconst_m1
    //   889: if_icmpne -> 914
    //   892: aload #6
    //   894: iconst_2
    //   895: iaload
    //   896: iconst_m1
    //   897: if_icmpne -> 914
    //   900: aload #6
    //   902: iconst_3
    //   903: iaload
    //   904: iconst_m1
    //   905: if_icmpeq -> 911
    //   908: goto -> 914
    //   911: goto -> 972
    //   914: iload #14
    //   916: aload #6
    //   918: iconst_3
    //   919: iaload
    //   920: aload #6
    //   922: iconst_0
    //   923: iaload
    //   924: aload #6
    //   926: iconst_1
    //   927: iaload
    //   928: aload #6
    //   930: iconst_2
    //   931: iaload
    //   932: invokestatic max : (II)I
    //   935: invokestatic max : (II)I
    //   938: invokestatic max : (II)I
    //   941: aload #7
    //   943: iconst_3
    //   944: iaload
    //   945: aload #7
    //   947: iconst_0
    //   948: iaload
    //   949: aload #7
    //   951: iconst_1
    //   952: iaload
    //   953: aload #7
    //   955: iconst_2
    //   956: iaload
    //   957: invokestatic max : (II)I
    //   960: invokestatic max : (II)I
    //   963: invokestatic max : (II)I
    //   966: iadd
    //   967: invokestatic max : (II)I
    //   970: istore #14
    //   972: iload #14
    //   974: istore #23
    //   976: iload #9
    //   978: ifeq -> 1166
    //   981: iload #4
    //   983: ldc_w -2147483648
    //   986: if_icmpeq -> 998
    //   989: iload #14
    //   991: istore #23
    //   993: iload #4
    //   995: ifne -> 1166
    //   998: aload_0
    //   999: iconst_0
    //   1000: putfield mTotalLength : I
    //   1003: iconst_0
    //   1004: istore #12
    //   1006: iload #14
    //   1008: istore #23
    //   1010: iload #12
    //   1012: iload_3
    //   1013: if_icmpge -> 1166
    //   1016: aload_0
    //   1017: iload #12
    //   1019: invokevirtual getVirtualChildAt : (I)Landroid/view/View;
    //   1022: astore #22
    //   1024: aload #22
    //   1026: ifnonnull -> 1047
    //   1029: aload_0
    //   1030: aload_0
    //   1031: getfield mTotalLength : I
    //   1034: aload_0
    //   1035: iload #12
    //   1037: invokevirtual measureNullChild : (I)I
    //   1040: iadd
    //   1041: putfield mTotalLength : I
    //   1044: goto -> 1070
    //   1047: aload #22
    //   1049: invokevirtual getVisibility : ()I
    //   1052: bipush #8
    //   1054: if_icmpne -> 1073
    //   1057: iload #12
    //   1059: aload_0
    //   1060: aload #22
    //   1062: iload #12
    //   1064: invokevirtual getChildrenSkipCount : (Landroid/view/View;I)I
    //   1067: iadd
    //   1068: istore #12
    //   1070: goto -> 1160
    //   1073: aload #22
    //   1075: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   1078: checkcast android/support/v7/widget/LinearLayoutCompat$LayoutParams
    //   1081: astore #21
    //   1083: iload #10
    //   1085: ifeq -> 1121
    //   1088: aload_0
    //   1089: aload_0
    //   1090: getfield mTotalLength : I
    //   1093: aload #21
    //   1095: getfield leftMargin : I
    //   1098: iload #13
    //   1100: iadd
    //   1101: aload #21
    //   1103: getfield rightMargin : I
    //   1106: iadd
    //   1107: aload_0
    //   1108: aload #22
    //   1110: invokevirtual getNextLocationOffset : (Landroid/view/View;)I
    //   1113: iadd
    //   1114: iadd
    //   1115: putfield mTotalLength : I
    //   1118: goto -> 1070
    //   1121: aload_0
    //   1122: getfield mTotalLength : I
    //   1125: istore #23
    //   1127: aload_0
    //   1128: iload #23
    //   1130: iload #23
    //   1132: iload #13
    //   1134: iadd
    //   1135: aload #21
    //   1137: getfield leftMargin : I
    //   1140: iadd
    //   1141: aload #21
    //   1143: getfield rightMargin : I
    //   1146: iadd
    //   1147: aload_0
    //   1148: aload #22
    //   1150: invokevirtual getNextLocationOffset : (Landroid/view/View;)I
    //   1153: iadd
    //   1154: invokestatic max : (II)I
    //   1157: putfield mTotalLength : I
    //   1160: iinc #12, 1
    //   1163: goto -> 1006
    //   1166: aload_0
    //   1167: getfield mTotalLength : I
    //   1170: aload_0
    //   1171: invokevirtual getPaddingLeft : ()I
    //   1174: aload_0
    //   1175: invokevirtual getPaddingRight : ()I
    //   1178: iadd
    //   1179: iadd
    //   1180: istore #12
    //   1182: aload_0
    //   1183: iload #12
    //   1185: putfield mTotalLength : I
    //   1188: iload #12
    //   1190: aload_0
    //   1191: invokevirtual getSuggestedMinimumWidth : ()I
    //   1194: invokestatic max : (II)I
    //   1197: iload_1
    //   1198: iconst_0
    //   1199: invokestatic resolveSizeAndState : (III)I
    //   1202: istore #25
    //   1204: ldc_w 16777215
    //   1207: iload #25
    //   1209: iand
    //   1210: aload_0
    //   1211: getfield mTotalLength : I
    //   1214: isub
    //   1215: istore #24
    //   1217: iload #17
    //   1219: ifne -> 1351
    //   1222: iload #24
    //   1224: ifeq -> 1237
    //   1227: fload #11
    //   1229: fconst_0
    //   1230: fcmpl
    //   1231: ifle -> 1237
    //   1234: goto -> 1351
    //   1237: iload #15
    //   1239: iload #16
    //   1241: invokestatic max : (II)I
    //   1244: istore #14
    //   1246: iload #9
    //   1248: ifeq -> 1337
    //   1251: iload #4
    //   1253: ldc 1073741824
    //   1255: if_icmpeq -> 1337
    //   1258: iconst_0
    //   1259: istore #15
    //   1261: iload #15
    //   1263: iload_3
    //   1264: if_icmpge -> 1337
    //   1267: aload_0
    //   1268: iload #15
    //   1270: invokevirtual getVirtualChildAt : (I)Landroid/view/View;
    //   1273: astore #7
    //   1275: aload #7
    //   1277: ifnull -> 1331
    //   1280: aload #7
    //   1282: invokevirtual getVisibility : ()I
    //   1285: bipush #8
    //   1287: if_icmpne -> 1293
    //   1290: goto -> 1331
    //   1293: aload #7
    //   1295: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   1298: checkcast android/support/v7/widget/LinearLayoutCompat$LayoutParams
    //   1301: getfield weight : F
    //   1304: fconst_0
    //   1305: fcmpl
    //   1306: ifle -> 1331
    //   1309: aload #7
    //   1311: iload #13
    //   1313: ldc 1073741824
    //   1315: invokestatic makeMeasureSpec : (II)I
    //   1318: aload #7
    //   1320: invokevirtual getMeasuredHeight : ()I
    //   1323: ldc 1073741824
    //   1325: invokestatic makeMeasureSpec : (II)I
    //   1328: invokevirtual measure : (II)V
    //   1331: iinc #15, 1
    //   1334: goto -> 1261
    //   1337: iload_3
    //   1338: istore #12
    //   1340: iload #23
    //   1342: istore #15
    //   1344: iload #14
    //   1346: istore #13
    //   1348: goto -> 2083
    //   1351: aload_0
    //   1352: getfield mWeightSum : F
    //   1355: fstore #28
    //   1357: fload #28
    //   1359: fconst_0
    //   1360: fcmpl
    //   1361: ifle -> 1368
    //   1364: fload #28
    //   1366: fstore #11
    //   1368: aload #6
    //   1370: iconst_3
    //   1371: iconst_m1
    //   1372: iastore
    //   1373: aload #6
    //   1375: iconst_2
    //   1376: iconst_m1
    //   1377: iastore
    //   1378: aload #6
    //   1380: iconst_1
    //   1381: iconst_m1
    //   1382: iastore
    //   1383: aload #6
    //   1385: iconst_0
    //   1386: iconst_m1
    //   1387: iastore
    //   1388: aload #7
    //   1390: iconst_3
    //   1391: iconst_m1
    //   1392: iastore
    //   1393: aload #7
    //   1395: iconst_2
    //   1396: iconst_m1
    //   1397: iastore
    //   1398: aload #7
    //   1400: iconst_1
    //   1401: iconst_m1
    //   1402: iastore
    //   1403: aload #7
    //   1405: iconst_0
    //   1406: iconst_m1
    //   1407: iastore
    //   1408: aload_0
    //   1409: iconst_0
    //   1410: putfield mTotalLength : I
    //   1413: iload #18
    //   1415: istore #14
    //   1417: iconst_m1
    //   1418: istore #16
    //   1420: iconst_0
    //   1421: istore #18
    //   1423: iload #19
    //   1425: istore #12
    //   1427: iload_3
    //   1428: istore #13
    //   1430: iload #14
    //   1432: istore #19
    //   1434: iload #15
    //   1436: istore #14
    //   1438: iload #24
    //   1440: istore #15
    //   1442: iload #18
    //   1444: iload #13
    //   1446: if_icmpge -> 1949
    //   1449: aload_0
    //   1450: iload #18
    //   1452: invokevirtual getVirtualChildAt : (I)Landroid/view/View;
    //   1455: astore #22
    //   1457: aload #22
    //   1459: ifnull -> 1943
    //   1462: aload #22
    //   1464: invokevirtual getVisibility : ()I
    //   1467: bipush #8
    //   1469: if_icmpne -> 1475
    //   1472: goto -> 1943
    //   1475: aload #22
    //   1477: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   1480: checkcast android/support/v7/widget/LinearLayoutCompat$LayoutParams
    //   1483: astore #21
    //   1485: aload #21
    //   1487: getfield weight : F
    //   1490: fstore #28
    //   1492: fload #28
    //   1494: fconst_0
    //   1495: fcmpl
    //   1496: ifle -> 1659
    //   1499: iload #15
    //   1501: i2f
    //   1502: fload #28
    //   1504: fmul
    //   1505: fload #11
    //   1507: fdiv
    //   1508: f2i
    //   1509: istore #23
    //   1511: iload_2
    //   1512: aload_0
    //   1513: invokevirtual getPaddingTop : ()I
    //   1516: aload_0
    //   1517: invokevirtual getPaddingBottom : ()I
    //   1520: iadd
    //   1521: aload #21
    //   1523: getfield topMargin : I
    //   1526: iadd
    //   1527: aload #21
    //   1529: getfield bottomMargin : I
    //   1532: iadd
    //   1533: aload #21
    //   1535: getfield height : I
    //   1538: invokestatic getChildMeasureSpec : (III)I
    //   1541: istore #24
    //   1543: aload #21
    //   1545: getfield width : I
    //   1548: ifne -> 1593
    //   1551: iload #4
    //   1553: ldc 1073741824
    //   1555: if_icmpeq -> 1561
    //   1558: goto -> 1593
    //   1561: iload #23
    //   1563: ifle -> 1573
    //   1566: iload #23
    //   1568: istore #17
    //   1570: goto -> 1576
    //   1573: iconst_0
    //   1574: istore #17
    //   1576: aload #22
    //   1578: iload #17
    //   1580: ldc 1073741824
    //   1582: invokestatic makeMeasureSpec : (II)I
    //   1585: iload #24
    //   1587: invokevirtual measure : (II)V
    //   1590: goto -> 1626
    //   1593: aload #22
    //   1595: invokevirtual getMeasuredWidth : ()I
    //   1598: iload #23
    //   1600: iadd
    //   1601: istore_3
    //   1602: iload_3
    //   1603: istore #17
    //   1605: iload_3
    //   1606: ifge -> 1612
    //   1609: iconst_0
    //   1610: istore #17
    //   1612: aload #22
    //   1614: iload #17
    //   1616: ldc 1073741824
    //   1618: invokestatic makeMeasureSpec : (II)I
    //   1621: iload #24
    //   1623: invokevirtual measure : (II)V
    //   1626: iload #19
    //   1628: aload #22
    //   1630: invokevirtual getMeasuredState : ()I
    //   1633: ldc_w -16777216
    //   1636: iand
    //   1637: invokestatic combineMeasuredStates : (II)I
    //   1640: istore #19
    //   1642: fload #11
    //   1644: fload #28
    //   1646: fsub
    //   1647: fstore #11
    //   1649: iload #15
    //   1651: iload #23
    //   1653: isub
    //   1654: istore #15
    //   1656: goto -> 1659
    //   1659: iload #10
    //   1661: ifeq -> 1700
    //   1664: aload_0
    //   1665: aload_0
    //   1666: getfield mTotalLength : I
    //   1669: aload #22
    //   1671: invokevirtual getMeasuredWidth : ()I
    //   1674: aload #21
    //   1676: getfield leftMargin : I
    //   1679: iadd
    //   1680: aload #21
    //   1682: getfield rightMargin : I
    //   1685: iadd
    //   1686: aload_0
    //   1687: aload #22
    //   1689: invokevirtual getNextLocationOffset : (Landroid/view/View;)I
    //   1692: iadd
    //   1693: iadd
    //   1694: putfield mTotalLength : I
    //   1697: goto -> 1742
    //   1700: aload_0
    //   1701: getfield mTotalLength : I
    //   1704: istore #17
    //   1706: aload_0
    //   1707: iload #17
    //   1709: aload #22
    //   1711: invokevirtual getMeasuredWidth : ()I
    //   1714: iload #17
    //   1716: iadd
    //   1717: aload #21
    //   1719: getfield leftMargin : I
    //   1722: iadd
    //   1723: aload #21
    //   1725: getfield rightMargin : I
    //   1728: iadd
    //   1729: aload_0
    //   1730: aload #22
    //   1732: invokevirtual getNextLocationOffset : (Landroid/view/View;)I
    //   1735: iadd
    //   1736: invokestatic max : (II)I
    //   1739: putfield mTotalLength : I
    //   1742: iload #5
    //   1744: ldc 1073741824
    //   1746: if_icmpeq -> 1764
    //   1749: aload #21
    //   1751: getfield height : I
    //   1754: iconst_m1
    //   1755: if_icmpne -> 1764
    //   1758: iconst_1
    //   1759: istore #17
    //   1761: goto -> 1767
    //   1764: iconst_0
    //   1765: istore #17
    //   1767: aload #21
    //   1769: getfield topMargin : I
    //   1772: aload #21
    //   1774: getfield bottomMargin : I
    //   1777: iadd
    //   1778: istore #24
    //   1780: aload #22
    //   1782: invokevirtual getMeasuredHeight : ()I
    //   1785: iload #24
    //   1787: iadd
    //   1788: istore_3
    //   1789: iload #16
    //   1791: iload_3
    //   1792: invokestatic max : (II)I
    //   1795: istore #23
    //   1797: iload #17
    //   1799: ifeq -> 1809
    //   1802: iload #24
    //   1804: istore #16
    //   1806: goto -> 1812
    //   1809: iload_3
    //   1810: istore #16
    //   1812: iload #14
    //   1814: iload #16
    //   1816: invokestatic max : (II)I
    //   1819: istore #16
    //   1821: iload #12
    //   1823: ifeq -> 1841
    //   1826: aload #21
    //   1828: getfield height : I
    //   1831: iconst_m1
    //   1832: if_icmpne -> 1841
    //   1835: iconst_1
    //   1836: istore #12
    //   1838: goto -> 1844
    //   1841: iconst_0
    //   1842: istore #12
    //   1844: iload #8
    //   1846: ifeq -> 1932
    //   1849: aload #22
    //   1851: invokevirtual getBaseline : ()I
    //   1854: istore #17
    //   1856: iload #17
    //   1858: iconst_m1
    //   1859: if_icmpeq -> 1932
    //   1862: aload #21
    //   1864: getfield gravity : I
    //   1867: ifge -> 1879
    //   1870: aload_0
    //   1871: getfield mGravity : I
    //   1874: istore #14
    //   1876: goto -> 1886
    //   1879: aload #21
    //   1881: getfield gravity : I
    //   1884: istore #14
    //   1886: iload #14
    //   1888: bipush #112
    //   1890: iand
    //   1891: iconst_4
    //   1892: ishr
    //   1893: bipush #-2
    //   1895: iand
    //   1896: iconst_1
    //   1897: ishr
    //   1898: istore #14
    //   1900: aload #6
    //   1902: iload #14
    //   1904: aload #6
    //   1906: iload #14
    //   1908: iaload
    //   1909: iload #17
    //   1911: invokestatic max : (II)I
    //   1914: iastore
    //   1915: aload #7
    //   1917: iload #14
    //   1919: aload #7
    //   1921: iload #14
    //   1923: iaload
    //   1924: iload_3
    //   1925: iload #17
    //   1927: isub
    //   1928: invokestatic max : (II)I
    //   1931: iastore
    //   1932: iload #16
    //   1934: istore #14
    //   1936: iload #23
    //   1938: istore #16
    //   1940: goto -> 1943
    //   1943: iinc #18, 1
    //   1946: goto -> 1442
    //   1949: aload_0
    //   1950: aload_0
    //   1951: getfield mTotalLength : I
    //   1954: aload_0
    //   1955: invokevirtual getPaddingLeft : ()I
    //   1958: aload_0
    //   1959: invokevirtual getPaddingRight : ()I
    //   1962: iadd
    //   1963: iadd
    //   1964: putfield mTotalLength : I
    //   1967: aload #6
    //   1969: iconst_1
    //   1970: iaload
    //   1971: iconst_m1
    //   1972: if_icmpne -> 2009
    //   1975: aload #6
    //   1977: iconst_0
    //   1978: iaload
    //   1979: iconst_m1
    //   1980: if_icmpne -> 2009
    //   1983: aload #6
    //   1985: iconst_2
    //   1986: iaload
    //   1987: iconst_m1
    //   1988: if_icmpne -> 2009
    //   1991: aload #6
    //   1993: iconst_3
    //   1994: iaload
    //   1995: iconst_m1
    //   1996: if_icmpeq -> 2002
    //   1999: goto -> 2009
    //   2002: iload #16
    //   2004: istore #15
    //   2006: goto -> 2067
    //   2009: iload #16
    //   2011: aload #6
    //   2013: iconst_3
    //   2014: iaload
    //   2015: aload #6
    //   2017: iconst_0
    //   2018: iaload
    //   2019: aload #6
    //   2021: iconst_1
    //   2022: iaload
    //   2023: aload #6
    //   2025: iconst_2
    //   2026: iaload
    //   2027: invokestatic max : (II)I
    //   2030: invokestatic max : (II)I
    //   2033: invokestatic max : (II)I
    //   2036: aload #7
    //   2038: iconst_3
    //   2039: iaload
    //   2040: aload #7
    //   2042: iconst_0
    //   2043: iaload
    //   2044: aload #7
    //   2046: iconst_1
    //   2047: iaload
    //   2048: aload #7
    //   2050: iconst_2
    //   2051: iaload
    //   2052: invokestatic max : (II)I
    //   2055: invokestatic max : (II)I
    //   2058: invokestatic max : (II)I
    //   2061: iadd
    //   2062: invokestatic max : (II)I
    //   2065: istore #15
    //   2067: iload #19
    //   2069: istore #18
    //   2071: iload #12
    //   2073: istore #19
    //   2075: iload #13
    //   2077: istore #12
    //   2079: iload #14
    //   2081: istore #13
    //   2083: iload #19
    //   2085: ifne -> 2098
    //   2088: iload #5
    //   2090: ldc 1073741824
    //   2092: if_icmpeq -> 2098
    //   2095: goto -> 2102
    //   2098: iload #15
    //   2100: istore #13
    //   2102: aload_0
    //   2103: iload #25
    //   2105: iload #18
    //   2107: ldc_w -16777216
    //   2110: iand
    //   2111: ior
    //   2112: iload #13
    //   2114: aload_0
    //   2115: invokevirtual getPaddingTop : ()I
    //   2118: aload_0
    //   2119: invokevirtual getPaddingBottom : ()I
    //   2122: iadd
    //   2123: iadd
    //   2124: aload_0
    //   2125: invokevirtual getSuggestedMinimumHeight : ()I
    //   2128: invokestatic max : (II)I
    //   2131: iload_2
    //   2132: iload #18
    //   2134: bipush #16
    //   2136: ishl
    //   2137: invokestatic resolveSizeAndState : (III)I
    //   2140: invokevirtual setMeasuredDimension : (II)V
    //   2143: iload #20
    //   2145: ifeq -> 2155
    //   2148: aload_0
    //   2149: iload #12
    //   2151: iload_1
    //   2152: invokespecial forceUniformHeight : (II)V
    //   2155: return
  }
  
  int measureNullChild(int paramInt) {
    return 0;
  }
  
  void measureVertical(int paramInt1, int paramInt2) {
    this.mTotalLength = 0;
    int i = getVirtualChildCount();
    int j = View.MeasureSpec.getMode(paramInt1);
    int k = View.MeasureSpec.getMode(paramInt2);
    int m = this.mBaselineAlignedChildIndex;
    boolean bool = this.mUseLargestChild;
    float f = 0.0F;
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    int i4 = 0;
    int i5 = 0;
    int i6 = 0;
    int i7 = 1;
    boolean bool1 = false;
    while (i5 < i) {
      View view = getVirtualChildAt(i5);
      if (view == null) {
        this.mTotalLength += measureNullChild(i5);
      } else if (view.getVisibility() == 8) {
        i5 += getChildrenSkipCount(view, i5);
      } else {
        if (hasDividerBeforeChildAt(i5))
          this.mTotalLength += this.mDividerHeight; 
        LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
        f += layoutParams.weight;
        if (k == 1073741824 && layoutParams.height == 0 && layoutParams.weight > 0.0F) {
          i6 = this.mTotalLength;
          this.mTotalLength = Math.max(i6, layoutParams.topMargin + i6 + layoutParams.bottomMargin);
          i6 = 1;
        } else {
          if (layoutParams.height == 0 && layoutParams.weight > 0.0F) {
            layoutParams.height = -2;
            i10 = 0;
          } else {
            i10 = Integer.MIN_VALUE;
          } 
          if (f == 0.0F) {
            i11 = this.mTotalLength;
          } else {
            i11 = 0;
          } 
          measureChildBeforeLayout(view, i5, paramInt1, 0, paramInt2, i11);
          if (i10 != Integer.MIN_VALUE)
            layoutParams.height = i10; 
          int i11 = view.getMeasuredHeight();
          int i10 = this.mTotalLength;
          this.mTotalLength = Math.max(i10, i10 + i11 + layoutParams.topMargin + layoutParams.bottomMargin + getNextLocationOffset(view));
          if (bool)
            i2 = Math.max(i11, i2); 
        } 
        int i9 = i5;
        if (m >= 0 && m == i9 + 1)
          this.mBaselineChildTop = this.mTotalLength; 
        if (i9 >= m || layoutParams.weight <= 0.0F) {
          if (j != 1073741824 && layoutParams.width == -1) {
            i5 = 1;
            bool1 = true;
          } else {
            i5 = 0;
          } 
          int i10 = layoutParams.leftMargin + layoutParams.rightMargin;
          int i11 = view.getMeasuredWidth() + i10;
          i1 = Math.max(i1, i11);
          int i12 = View.combineMeasuredStates(n, view.getMeasuredState());
          if (i7 && layoutParams.width == -1) {
            n = 1;
          } else {
            n = 0;
          } 
          if (layoutParams.weight > 0.0F) {
            if (i5 == 0)
              i10 = i11; 
            i3 = Math.max(i3, i10);
            i7 = i4;
          } else {
            if (i5 == 0)
              i10 = i11; 
            i7 = Math.max(i4, i10);
          } 
          i5 = getChildrenSkipCount(view, i9);
          i4 = i7;
          i10 = i12;
          i5 += i9;
          i7 = n;
          n = i10;
        } else {
          throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
        } 
      } 
      i5++;
    } 
    if (this.mTotalLength > 0 && hasDividerBeforeChildAt(i))
      this.mTotalLength += this.mDividerHeight; 
    if (bool && (k == Integer.MIN_VALUE || k == 0)) {
      this.mTotalLength = 0;
      for (i5 = 0; i5 < i; i5++) {
        View view = getVirtualChildAt(i5);
        if (view == null) {
          this.mTotalLength += measureNullChild(i5);
        } else if (view.getVisibility() == 8) {
          i5 += getChildrenSkipCount(view, i5);
        } else {
          LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
          int i9 = this.mTotalLength;
          this.mTotalLength = Math.max(i9, i9 + i2 + layoutParams.topMargin + layoutParams.bottomMargin + getNextLocationOffset(view));
        } 
      } 
    } 
    i5 = this.mTotalLength + getPaddingTop() + getPaddingBottom();
    this.mTotalLength = i5;
    int i8 = View.resolveSizeAndState(Math.max(i5, getSuggestedMinimumHeight()), paramInt2, 0);
    i5 = (0xFFFFFF & i8) - this.mTotalLength;
    if (i6 != 0 || (i5 != 0 && f > 0.0F)) {
      float f1 = this.mWeightSum;
      if (f1 > 0.0F)
        f = f1; 
      this.mTotalLength = 0;
      i3 = i5;
      i5 = 0;
      i2 = i1;
      while (i5 < i) {
        View view = getVirtualChildAt(i5);
        if (view.getVisibility() != 8) {
          LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
          f1 = layoutParams.weight;
          if (f1 > 0.0F) {
            i1 = (int)(i3 * f1 / f);
            int i10 = getPaddingLeft();
            m = getPaddingRight();
            int i11 = layoutParams.leftMargin;
            int i12 = layoutParams.rightMargin;
            int i13 = layoutParams.width;
            i6 = i3 - i1;
            i10 = getChildMeasureSpec(paramInt1, i10 + m + i11 + i12, i13);
            if (layoutParams.height != 0 || k != 1073741824) {
              i1 = view.getMeasuredHeight() + i1;
              i3 = i1;
              if (i1 < 0)
                i3 = 0; 
              view.measure(i10, View.MeasureSpec.makeMeasureSpec(i3, 1073741824));
            } else {
              if (i1 > 0) {
                i3 = i1;
              } else {
                i3 = 0;
              } 
              view.measure(i10, View.MeasureSpec.makeMeasureSpec(i3, 1073741824));
            } 
            n = View.combineMeasuredStates(n, view.getMeasuredState() & 0xFFFFFF00);
            f -= f1;
            i3 = i6;
          } 
          int i9 = layoutParams.leftMargin + layoutParams.rightMargin;
          i1 = view.getMeasuredWidth() + i9;
          i6 = Math.max(i2, i1);
          if (j != 1073741824 && layoutParams.width == -1) {
            i2 = 1;
          } else {
            i2 = 0;
          } 
          if (i2 != 0) {
            i2 = i9;
          } else {
            i2 = i1;
          } 
          i4 = Math.max(i4, i2);
          if (i7 != 0 && layoutParams.width == -1) {
            i7 = 1;
          } else {
            i7 = 0;
          } 
          i2 = this.mTotalLength;
          this.mTotalLength = Math.max(i2, view.getMeasuredHeight() + i2 + layoutParams.topMargin + layoutParams.bottomMargin + getNextLocationOffset(view));
          i2 = i6;
        } 
        i5++;
      } 
      this.mTotalLength += getPaddingTop() + getPaddingBottom();
      i3 = n;
      n = i4;
    } else {
      i4 = Math.max(i4, i3);
      if (bool && k != 1073741824)
        for (i3 = 0; i3 < i; i3++) {
          View view = getVirtualChildAt(i3);
          if (view != null && view.getVisibility() != 8 && ((LayoutParams)view.getLayoutParams()).weight > 0.0F)
            view.measure(View.MeasureSpec.makeMeasureSpec(view.getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(i2, 1073741824)); 
        }  
      i3 = n;
      n = i4;
      i2 = i1;
    } 
    if (i7 != 0 || j == 1073741824)
      n = i2; 
    setMeasuredDimension(View.resolveSizeAndState(Math.max(n + getPaddingLeft() + getPaddingRight(), getSuggestedMinimumWidth()), paramInt1, i3), i8);
    if (bool1)
      forceUniformWidth(i, paramInt2); 
  }
  
  protected void onDraw(Canvas paramCanvas) {
    if (this.mDivider == null)
      return; 
    if (this.mOrientation == 1) {
      drawDividersVertical(paramCanvas);
    } else {
      drawDividersHorizontal(paramCanvas);
    } 
  }
  
  public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent) {
    super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
    paramAccessibilityEvent.setClassName(LinearLayoutCompat.class.getName());
  }
  
  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo) {
    super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
    paramAccessibilityNodeInfo.setClassName(LinearLayoutCompat.class.getName());
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    if (this.mOrientation == 1) {
      layoutVertical(paramInt1, paramInt2, paramInt3, paramInt4);
    } else {
      layoutHorizontal(paramInt1, paramInt2, paramInt3, paramInt4);
    } 
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    if (this.mOrientation == 1) {
      measureVertical(paramInt1, paramInt2);
    } else {
      measureHorizontal(paramInt1, paramInt2);
    } 
  }
  
  public void setBaselineAligned(boolean paramBoolean) {
    this.mBaselineAligned = paramBoolean;
  }
  
  public void setBaselineAlignedChildIndex(int paramInt) {
    if (paramInt >= 0 && paramInt < getChildCount()) {
      this.mBaselineAlignedChildIndex = paramInt;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("base aligned child index out of range (0, ");
    stringBuilder.append(getChildCount());
    stringBuilder.append(")");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void setDividerDrawable(Drawable paramDrawable) {
    if (paramDrawable == this.mDivider)
      return; 
    this.mDivider = paramDrawable;
    boolean bool = false;
    if (paramDrawable != null) {
      this.mDividerWidth = paramDrawable.getIntrinsicWidth();
      this.mDividerHeight = paramDrawable.getIntrinsicHeight();
    } else {
      this.mDividerWidth = 0;
      this.mDividerHeight = 0;
    } 
    if (paramDrawable == null)
      bool = true; 
    setWillNotDraw(bool);
    requestLayout();
  }
  
  public void setDividerPadding(int paramInt) {
    this.mDividerPadding = paramInt;
  }
  
  public void setGravity(int paramInt) {
    if (this.mGravity != paramInt) {
      int i = paramInt;
      if ((0x800007 & paramInt) == 0)
        i = paramInt | 0x800003; 
      paramInt = i;
      if ((i & 0x70) == 0)
        paramInt = i | 0x30; 
      this.mGravity = paramInt;
      requestLayout();
    } 
  }
  
  public void setHorizontalGravity(int paramInt) {
    int i = paramInt & 0x800007;
    paramInt = this.mGravity;
    if ((0x800007 & paramInt) != i) {
      this.mGravity = i | 0xFF7FFFF8 & paramInt;
      requestLayout();
    } 
  }
  
  public void setMeasureWithLargestChildEnabled(boolean paramBoolean) {
    this.mUseLargestChild = paramBoolean;
  }
  
  public void setOrientation(int paramInt) {
    if (this.mOrientation != paramInt) {
      this.mOrientation = paramInt;
      requestLayout();
    } 
  }
  
  public void setShowDividers(int paramInt) {
    if (paramInt != this.mShowDividers)
      requestLayout(); 
    this.mShowDividers = paramInt;
  }
  
  public void setVerticalGravity(int paramInt) {
    paramInt &= 0x70;
    int i = this.mGravity;
    if ((i & 0x70) != paramInt) {
      this.mGravity = paramInt | i & 0xFFFFFF8F;
      requestLayout();
    } 
  }
  
  public void setWeightSum(float paramFloat) {
    this.mWeightSum = Math.max(0.0F, paramFloat);
  }
  
  public boolean shouldDelayChildPressedState() {
    return false;
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface DividerMode {}
  
  public static class LayoutParams extends ViewGroup.MarginLayoutParams {
    public int gravity = -1;
    
    public float weight;
    
    public LayoutParams(int param1Int1, int param1Int2) {
      super(param1Int1, param1Int2);
      this.weight = 0.0F;
    }
    
    public LayoutParams(int param1Int1, int param1Int2, float param1Float) {
      super(param1Int1, param1Int2);
      this.weight = param1Float;
    }
    
    public LayoutParams(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
      TypedArray typedArray = param1Context.obtainStyledAttributes(param1AttributeSet, R.styleable.LinearLayoutCompat_Layout);
      this.weight = typedArray.getFloat(R.styleable.LinearLayoutCompat_Layout_android_layout_weight, 0.0F);
      this.gravity = typedArray.getInt(R.styleable.LinearLayoutCompat_Layout_android_layout_gravity, -1);
      typedArray.recycle();
    }
    
    public LayoutParams(LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
      this.weight = param1LayoutParams.weight;
      this.gravity = param1LayoutParams.gravity;
    }
    
    public LayoutParams(ViewGroup.LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams param1MarginLayoutParams) {
      super(param1MarginLayoutParams);
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface OrientationMode {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\LinearLayoutCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */