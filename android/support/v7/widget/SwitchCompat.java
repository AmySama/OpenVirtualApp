package android.support.v7.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.text.AllCapsTransformationMethod;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.Property;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;

public class SwitchCompat extends CompoundButton {
  private static final String ACCESSIBILITY_EVENT_CLASS_NAME = "android.widget.Switch";
  
  private static final int[] CHECKED_STATE_SET;
  
  private static final int MONOSPACE = 3;
  
  private static final int SANS = 1;
  
  private static final int SERIF = 2;
  
  private static final int THUMB_ANIMATION_DURATION = 250;
  
  private static final Property<SwitchCompat, Float> THUMB_POS = new Property<SwitchCompat, Float>(Float.class, "thumbPos") {
      public Float get(SwitchCompat param1SwitchCompat) {
        return Float.valueOf(param1SwitchCompat.mThumbPosition);
      }
      
      public void set(SwitchCompat param1SwitchCompat, Float param1Float) {
        param1SwitchCompat.setThumbPosition(param1Float.floatValue());
      }
    };
  
  private static final int TOUCH_MODE_DOWN = 1;
  
  private static final int TOUCH_MODE_DRAGGING = 2;
  
  private static final int TOUCH_MODE_IDLE = 0;
  
  private boolean mHasThumbTint = false;
  
  private boolean mHasThumbTintMode = false;
  
  private boolean mHasTrackTint = false;
  
  private boolean mHasTrackTintMode = false;
  
  private int mMinFlingVelocity;
  
  private Layout mOffLayout;
  
  private Layout mOnLayout;
  
  ObjectAnimator mPositionAnimator;
  
  private boolean mShowText;
  
  private boolean mSplitTrack;
  
  private int mSwitchBottom;
  
  private int mSwitchHeight;
  
  private int mSwitchLeft;
  
  private int mSwitchMinWidth;
  
  private int mSwitchPadding;
  
  private int mSwitchRight;
  
  private int mSwitchTop;
  
  private TransformationMethod mSwitchTransformationMethod;
  
  private int mSwitchWidth;
  
  private final Rect mTempRect = new Rect();
  
  private ColorStateList mTextColors;
  
  private CharSequence mTextOff;
  
  private CharSequence mTextOn;
  
  private final TextPaint mTextPaint = new TextPaint(1);
  
  private Drawable mThumbDrawable;
  
  private float mThumbPosition;
  
  private int mThumbTextPadding;
  
  private ColorStateList mThumbTintList = null;
  
  private PorterDuff.Mode mThumbTintMode = null;
  
  private int mThumbWidth;
  
  private int mTouchMode;
  
  private int mTouchSlop;
  
  private float mTouchX;
  
  private float mTouchY;
  
  private Drawable mTrackDrawable;
  
  private ColorStateList mTrackTintList = null;
  
  private PorterDuff.Mode mTrackTintMode = null;
  
  private VelocityTracker mVelocityTracker = VelocityTracker.obtain();
  
  static {
    CHECKED_STATE_SET = new int[] { 16842912 };
  }
  
  public SwitchCompat(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public SwitchCompat(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, R.attr.switchStyle);
  }
  
  public SwitchCompat(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    Resources resources = getResources();
    this.mTextPaint.density = (resources.getDisplayMetrics()).density;
    TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.SwitchCompat, paramInt, 0);
    Drawable drawable = tintTypedArray.getDrawable(R.styleable.SwitchCompat_android_thumb);
    this.mThumbDrawable = drawable;
    if (drawable != null)
      drawable.setCallback((Drawable.Callback)this); 
    drawable = tintTypedArray.getDrawable(R.styleable.SwitchCompat_track);
    this.mTrackDrawable = drawable;
    if (drawable != null)
      drawable.setCallback((Drawable.Callback)this); 
    this.mTextOn = tintTypedArray.getText(R.styleable.SwitchCompat_android_textOn);
    this.mTextOff = tintTypedArray.getText(R.styleable.SwitchCompat_android_textOff);
    this.mShowText = tintTypedArray.getBoolean(R.styleable.SwitchCompat_showText, true);
    this.mThumbTextPadding = tintTypedArray.getDimensionPixelSize(R.styleable.SwitchCompat_thumbTextPadding, 0);
    this.mSwitchMinWidth = tintTypedArray.getDimensionPixelSize(R.styleable.SwitchCompat_switchMinWidth, 0);
    this.mSwitchPadding = tintTypedArray.getDimensionPixelSize(R.styleable.SwitchCompat_switchPadding, 0);
    this.mSplitTrack = tintTypedArray.getBoolean(R.styleable.SwitchCompat_splitTrack, false);
    ColorStateList colorStateList2 = tintTypedArray.getColorStateList(R.styleable.SwitchCompat_thumbTint);
    if (colorStateList2 != null) {
      this.mThumbTintList = colorStateList2;
      this.mHasThumbTint = true;
    } 
    PorterDuff.Mode mode2 = DrawableUtils.parseTintMode(tintTypedArray.getInt(R.styleable.SwitchCompat_thumbTintMode, -1), null);
    if (this.mThumbTintMode != mode2) {
      this.mThumbTintMode = mode2;
      this.mHasThumbTintMode = true;
    } 
    if (this.mHasThumbTint || this.mHasThumbTintMode)
      applyThumbTint(); 
    ColorStateList colorStateList1 = tintTypedArray.getColorStateList(R.styleable.SwitchCompat_trackTint);
    if (colorStateList1 != null) {
      this.mTrackTintList = colorStateList1;
      this.mHasTrackTint = true;
    } 
    PorterDuff.Mode mode1 = DrawableUtils.parseTintMode(tintTypedArray.getInt(R.styleable.SwitchCompat_trackTintMode, -1), null);
    if (this.mTrackTintMode != mode1) {
      this.mTrackTintMode = mode1;
      this.mHasTrackTintMode = true;
    } 
    if (this.mHasTrackTint || this.mHasTrackTintMode)
      applyTrackTint(); 
    paramInt = tintTypedArray.getResourceId(R.styleable.SwitchCompat_switchTextAppearance, 0);
    if (paramInt != 0)
      setSwitchTextAppearance(paramContext, paramInt); 
    tintTypedArray.recycle();
    ViewConfiguration viewConfiguration = ViewConfiguration.get(paramContext);
    this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
    this.mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
    refreshDrawableState();
    setChecked(isChecked());
  }
  
  private void animateThumbToCheckedState(boolean paramBoolean) {
    float f;
    if (paramBoolean) {
      f = 1.0F;
    } else {
      f = 0.0F;
    } 
    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, THUMB_POS, new float[] { f });
    this.mPositionAnimator = objectAnimator;
    objectAnimator.setDuration(250L);
    if (Build.VERSION.SDK_INT >= 18)
      this.mPositionAnimator.setAutoCancel(true); 
    this.mPositionAnimator.start();
  }
  
  private void applyThumbTint() {
    if (this.mThumbDrawable != null && (this.mHasThumbTint || this.mHasThumbTintMode)) {
      Drawable drawable = this.mThumbDrawable.mutate();
      this.mThumbDrawable = drawable;
      if (this.mHasThumbTint)
        DrawableCompat.setTintList(drawable, this.mThumbTintList); 
      if (this.mHasThumbTintMode)
        DrawableCompat.setTintMode(this.mThumbDrawable, this.mThumbTintMode); 
      if (this.mThumbDrawable.isStateful())
        this.mThumbDrawable.setState(getDrawableState()); 
    } 
  }
  
  private void applyTrackTint() {
    if (this.mTrackDrawable != null && (this.mHasTrackTint || this.mHasTrackTintMode)) {
      Drawable drawable = this.mTrackDrawable.mutate();
      this.mTrackDrawable = drawable;
      if (this.mHasTrackTint)
        DrawableCompat.setTintList(drawable, this.mTrackTintList); 
      if (this.mHasTrackTintMode)
        DrawableCompat.setTintMode(this.mTrackDrawable, this.mTrackTintMode); 
      if (this.mTrackDrawable.isStateful())
        this.mTrackDrawable.setState(getDrawableState()); 
    } 
  }
  
  private void cancelPositionAnimator() {
    ObjectAnimator objectAnimator = this.mPositionAnimator;
    if (objectAnimator != null)
      objectAnimator.cancel(); 
  }
  
  private void cancelSuperTouch(MotionEvent paramMotionEvent) {
    paramMotionEvent = MotionEvent.obtain(paramMotionEvent);
    paramMotionEvent.setAction(3);
    super.onTouchEvent(paramMotionEvent);
    paramMotionEvent.recycle();
  }
  
  private static float constrain(float paramFloat1, float paramFloat2, float paramFloat3) {
    if (paramFloat1 >= paramFloat2) {
      paramFloat2 = paramFloat1;
      if (paramFloat1 > paramFloat3)
        paramFloat2 = paramFloat3; 
    } 
    return paramFloat2;
  }
  
  private boolean getTargetCheckedState() {
    boolean bool;
    if (this.mThumbPosition > 0.5F) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private int getThumbOffset() {
    float f;
    if (ViewUtils.isLayoutRtl((View)this)) {
      f = 1.0F - this.mThumbPosition;
    } else {
      f = this.mThumbPosition;
    } 
    return (int)(f * getThumbScrollRange() + 0.5F);
  }
  
  private int getThumbScrollRange() {
    Drawable drawable = this.mTrackDrawable;
    if (drawable != null) {
      Rect rect1;
      Rect rect2 = this.mTempRect;
      drawable.getPadding(rect2);
      drawable = this.mThumbDrawable;
      if (drawable != null) {
        rect1 = DrawableUtils.getOpticalBounds(drawable);
      } else {
        rect1 = DrawableUtils.INSETS_NONE;
      } 
      return this.mSwitchWidth - this.mThumbWidth - rect2.left - rect2.right - rect1.left - rect1.right;
    } 
    return 0;
  }
  
  private boolean hitThumb(float paramFloat1, float paramFloat2) {
    Drawable drawable = this.mThumbDrawable;
    boolean bool1 = false;
    if (drawable == null)
      return false; 
    int i = getThumbOffset();
    this.mThumbDrawable.getPadding(this.mTempRect);
    int j = this.mSwitchTop;
    int k = this.mTouchSlop;
    int m = this.mSwitchLeft + i - k;
    int n = this.mThumbWidth;
    int i1 = this.mTempRect.left;
    i = this.mTempRect.right;
    int i2 = this.mTouchSlop;
    int i3 = this.mSwitchBottom;
    boolean bool2 = bool1;
    if (paramFloat1 > m) {
      bool2 = bool1;
      if (paramFloat1 < (n + m + i1 + i + i2)) {
        bool2 = bool1;
        if (paramFloat2 > (j - k)) {
          bool2 = bool1;
          if (paramFloat2 < (i3 + i2))
            bool2 = true; 
        } 
      } 
    } 
    return bool2;
  }
  
  private Layout makeLayout(CharSequence paramCharSequence) {
    boolean bool;
    TransformationMethod transformationMethod = this.mSwitchTransformationMethod;
    CharSequence charSequence = paramCharSequence;
    if (transformationMethod != null)
      charSequence = transformationMethod.getTransformation(paramCharSequence, (View)this); 
    TextPaint textPaint = this.mTextPaint;
    if (charSequence != null) {
      bool = (int)Math.ceil(Layout.getDesiredWidth(charSequence, textPaint));
    } else {
      bool = false;
    } 
    return (Layout)new StaticLayout(charSequence, textPaint, bool, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
  }
  
  private void setSwitchTypefaceByIndex(int paramInt1, int paramInt2) {
    Typeface typeface;
    if (paramInt1 != 1) {
      if (paramInt1 != 2) {
        if (paramInt1 != 3) {
          typeface = null;
        } else {
          typeface = Typeface.MONOSPACE;
        } 
      } else {
        typeface = Typeface.SERIF;
      } 
    } else {
      typeface = Typeface.SANS_SERIF;
    } 
    setSwitchTypeface(typeface, paramInt2);
  }
  
  private void stopDrag(MotionEvent paramMotionEvent) {
    this.mTouchMode = 0;
    int i = paramMotionEvent.getAction();
    boolean bool1 = true;
    if (i == 1 && isEnabled()) {
      i = 1;
    } else {
      i = 0;
    } 
    boolean bool2 = isChecked();
    if (i != 0) {
      this.mVelocityTracker.computeCurrentVelocity(1000);
      float f = this.mVelocityTracker.getXVelocity();
      if (Math.abs(f) > this.mMinFlingVelocity) {
        if (ViewUtils.isLayoutRtl((View)this) ? (f < 0.0F) : (f > 0.0F))
          bool1 = false; 
      } else {
        bool1 = getTargetCheckedState();
      } 
    } else {
      bool1 = bool2;
    } 
    if (bool1 != bool2)
      playSoundEffect(0); 
    setChecked(bool1);
    cancelSuperTouch(paramMotionEvent);
  }
  
  public void draw(Canvas paramCanvas) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mTempRect : Landroid/graphics/Rect;
    //   4: astore_2
    //   5: aload_0
    //   6: getfield mSwitchLeft : I
    //   9: istore_3
    //   10: aload_0
    //   11: getfield mSwitchTop : I
    //   14: istore #4
    //   16: aload_0
    //   17: getfield mSwitchRight : I
    //   20: istore #5
    //   22: aload_0
    //   23: getfield mSwitchBottom : I
    //   26: istore #6
    //   28: aload_0
    //   29: invokespecial getThumbOffset : ()I
    //   32: iload_3
    //   33: iadd
    //   34: istore #7
    //   36: aload_0
    //   37: getfield mThumbDrawable : Landroid/graphics/drawable/Drawable;
    //   40: astore #8
    //   42: aload #8
    //   44: ifnull -> 57
    //   47: aload #8
    //   49: invokestatic getOpticalBounds : (Landroid/graphics/drawable/Drawable;)Landroid/graphics/Rect;
    //   52: astore #8
    //   54: goto -> 62
    //   57: getstatic android/support/v7/widget/DrawableUtils.INSETS_NONE : Landroid/graphics/Rect;
    //   60: astore #8
    //   62: aload_0
    //   63: getfield mTrackDrawable : Landroid/graphics/drawable/Drawable;
    //   66: astore #9
    //   68: iload #7
    //   70: istore #10
    //   72: aload #9
    //   74: ifnull -> 274
    //   77: aload #9
    //   79: aload_2
    //   80: invokevirtual getPadding : (Landroid/graphics/Rect;)Z
    //   83: pop
    //   84: iload #7
    //   86: aload_2
    //   87: getfield left : I
    //   90: iadd
    //   91: istore #11
    //   93: aload #8
    //   95: ifnull -> 240
    //   98: iload_3
    //   99: istore #10
    //   101: aload #8
    //   103: getfield left : I
    //   106: aload_2
    //   107: getfield left : I
    //   110: if_icmple -> 127
    //   113: iload_3
    //   114: aload #8
    //   116: getfield left : I
    //   119: aload_2
    //   120: getfield left : I
    //   123: isub
    //   124: iadd
    //   125: istore #10
    //   127: aload #8
    //   129: getfield top : I
    //   132: aload_2
    //   133: getfield top : I
    //   136: if_icmple -> 157
    //   139: aload #8
    //   141: getfield top : I
    //   144: aload_2
    //   145: getfield top : I
    //   148: isub
    //   149: iload #4
    //   151: iadd
    //   152: istore #7
    //   154: goto -> 161
    //   157: iload #4
    //   159: istore #7
    //   161: iload #5
    //   163: istore #12
    //   165: aload #8
    //   167: getfield right : I
    //   170: aload_2
    //   171: getfield right : I
    //   174: if_icmple -> 192
    //   177: iload #5
    //   179: aload #8
    //   181: getfield right : I
    //   184: aload_2
    //   185: getfield right : I
    //   188: isub
    //   189: isub
    //   190: istore #12
    //   192: iload #10
    //   194: istore_3
    //   195: iload #12
    //   197: istore #5
    //   199: iload #7
    //   201: istore #13
    //   203: aload #8
    //   205: getfield bottom : I
    //   208: aload_2
    //   209: getfield bottom : I
    //   212: if_icmple -> 244
    //   215: iload #6
    //   217: aload #8
    //   219: getfield bottom : I
    //   222: aload_2
    //   223: getfield bottom : I
    //   226: isub
    //   227: isub
    //   228: istore #5
    //   230: iload #10
    //   232: istore_3
    //   233: iload #5
    //   235: istore #10
    //   237: goto -> 256
    //   240: iload #4
    //   242: istore #13
    //   244: iload #6
    //   246: istore #10
    //   248: iload #13
    //   250: istore #7
    //   252: iload #5
    //   254: istore #12
    //   256: aload_0
    //   257: getfield mTrackDrawable : Landroid/graphics/drawable/Drawable;
    //   260: iload_3
    //   261: iload #7
    //   263: iload #12
    //   265: iload #10
    //   267: invokevirtual setBounds : (IIII)V
    //   270: iload #11
    //   272: istore #10
    //   274: aload_0
    //   275: getfield mThumbDrawable : Landroid/graphics/drawable/Drawable;
    //   278: astore #8
    //   280: aload #8
    //   282: ifnull -> 354
    //   285: aload #8
    //   287: aload_2
    //   288: invokevirtual getPadding : (Landroid/graphics/Rect;)Z
    //   291: pop
    //   292: iload #10
    //   294: aload_2
    //   295: getfield left : I
    //   298: isub
    //   299: istore #7
    //   301: iload #10
    //   303: aload_0
    //   304: getfield mThumbWidth : I
    //   307: iadd
    //   308: aload_2
    //   309: getfield right : I
    //   312: iadd
    //   313: istore #10
    //   315: aload_0
    //   316: getfield mThumbDrawable : Landroid/graphics/drawable/Drawable;
    //   319: iload #7
    //   321: iload #4
    //   323: iload #10
    //   325: iload #6
    //   327: invokevirtual setBounds : (IIII)V
    //   330: aload_0
    //   331: invokevirtual getBackground : ()Landroid/graphics/drawable/Drawable;
    //   334: astore #8
    //   336: aload #8
    //   338: ifnull -> 354
    //   341: aload #8
    //   343: iload #7
    //   345: iload #4
    //   347: iload #10
    //   349: iload #6
    //   351: invokestatic setHotspotBounds : (Landroid/graphics/drawable/Drawable;IIII)V
    //   354: aload_0
    //   355: aload_1
    //   356: invokespecial draw : (Landroid/graphics/Canvas;)V
    //   359: return
  }
  
  public void drawableHotspotChanged(float paramFloat1, float paramFloat2) {
    if (Build.VERSION.SDK_INT >= 21)
      super.drawableHotspotChanged(paramFloat1, paramFloat2); 
    Drawable drawable = this.mThumbDrawable;
    if (drawable != null)
      DrawableCompat.setHotspot(drawable, paramFloat1, paramFloat2); 
    drawable = this.mTrackDrawable;
    if (drawable != null)
      DrawableCompat.setHotspot(drawable, paramFloat1, paramFloat2); 
  }
  
  protected void drawableStateChanged() {
    boolean bool;
    super.drawableStateChanged();
    int[] arrayOfInt = getDrawableState();
    Drawable drawable = this.mThumbDrawable;
    int i = 0;
    int j = i;
    if (drawable != null) {
      j = i;
      if (drawable.isStateful())
        j = false | drawable.setState(arrayOfInt); 
    } 
    drawable = this.mTrackDrawable;
    i = j;
    if (drawable != null) {
      i = j;
      if (drawable.isStateful())
        bool = j | drawable.setState(arrayOfInt); 
    } 
    if (bool)
      invalidate(); 
  }
  
  public int getCompoundPaddingLeft() {
    if (!ViewUtils.isLayoutRtl((View)this))
      return super.getCompoundPaddingLeft(); 
    int i = super.getCompoundPaddingLeft() + this.mSwitchWidth;
    int j = i;
    if (!TextUtils.isEmpty(getText()))
      j = i + this.mSwitchPadding; 
    return j;
  }
  
  public int getCompoundPaddingRight() {
    if (ViewUtils.isLayoutRtl((View)this))
      return super.getCompoundPaddingRight(); 
    int i = super.getCompoundPaddingRight() + this.mSwitchWidth;
    int j = i;
    if (!TextUtils.isEmpty(getText()))
      j = i + this.mSwitchPadding; 
    return j;
  }
  
  public boolean getShowText() {
    return this.mShowText;
  }
  
  public boolean getSplitTrack() {
    return this.mSplitTrack;
  }
  
  public int getSwitchMinWidth() {
    return this.mSwitchMinWidth;
  }
  
  public int getSwitchPadding() {
    return this.mSwitchPadding;
  }
  
  public CharSequence getTextOff() {
    return this.mTextOff;
  }
  
  public CharSequence getTextOn() {
    return this.mTextOn;
  }
  
  public Drawable getThumbDrawable() {
    return this.mThumbDrawable;
  }
  
  public int getThumbTextPadding() {
    return this.mThumbTextPadding;
  }
  
  public ColorStateList getThumbTintList() {
    return this.mThumbTintList;
  }
  
  public PorterDuff.Mode getThumbTintMode() {
    return this.mThumbTintMode;
  }
  
  public Drawable getTrackDrawable() {
    return this.mTrackDrawable;
  }
  
  public ColorStateList getTrackTintList() {
    return this.mTrackTintList;
  }
  
  public PorterDuff.Mode getTrackTintMode() {
    return this.mTrackTintMode;
  }
  
  public void jumpDrawablesToCurrentState() {
    super.jumpDrawablesToCurrentState();
    Drawable drawable = this.mThumbDrawable;
    if (drawable != null)
      drawable.jumpToCurrentState(); 
    drawable = this.mTrackDrawable;
    if (drawable != null)
      drawable.jumpToCurrentState(); 
    ObjectAnimator objectAnimator = this.mPositionAnimator;
    if (objectAnimator != null && objectAnimator.isStarted()) {
      this.mPositionAnimator.end();
      this.mPositionAnimator = null;
    } 
  }
  
  protected int[] onCreateDrawableState(int paramInt) {
    int[] arrayOfInt = super.onCreateDrawableState(paramInt + 1);
    if (isChecked())
      mergeDrawableStates(arrayOfInt, CHECKED_STATE_SET); 
    return arrayOfInt;
  }
  
  protected void onDraw(Canvas paramCanvas) {
    Layout layout;
    super.onDraw(paramCanvas);
    Rect rect = this.mTempRect;
    Drawable drawable1 = this.mTrackDrawable;
    if (drawable1 != null) {
      drawable1.getPadding(rect);
    } else {
      rect.setEmpty();
    } 
    int i = this.mSwitchTop;
    int j = this.mSwitchBottom;
    int k = rect.top;
    int m = rect.bottom;
    Drawable drawable2 = this.mThumbDrawable;
    if (drawable1 != null)
      if (this.mSplitTrack && drawable2 != null) {
        Rect rect1 = DrawableUtils.getOpticalBounds(drawable2);
        drawable2.copyBounds(rect);
        rect.left += rect1.left;
        rect.right -= rect1.right;
        int i1 = paramCanvas.save();
        paramCanvas.clipRect(rect, Region.Op.DIFFERENCE);
        drawable1.draw(paramCanvas);
        paramCanvas.restoreToCount(i1);
      } else {
        drawable1.draw(paramCanvas);
      }  
    int n = paramCanvas.save();
    if (drawable2 != null)
      drawable2.draw(paramCanvas); 
    if (getTargetCheckedState()) {
      layout = this.mOnLayout;
    } else {
      layout = this.mOffLayout;
    } 
    if (layout != null) {
      int i1;
      int[] arrayOfInt = getDrawableState();
      ColorStateList colorStateList = this.mTextColors;
      if (colorStateList != null)
        this.mTextPaint.setColor(colorStateList.getColorForState(arrayOfInt, 0)); 
      this.mTextPaint.drawableState = arrayOfInt;
      if (drawable2 != null) {
        Rect rect1 = drawable2.getBounds();
        i1 = rect1.left + rect1.right;
      } else {
        i1 = getWidth();
      } 
      i1 /= 2;
      int i2 = layout.getWidth() / 2;
      k = (i + k + j - m) / 2;
      i = layout.getHeight() / 2;
      paramCanvas.translate((i1 - i2), (k - i));
      layout.draw(paramCanvas);
    } 
    paramCanvas.restoreToCount(n);
  }
  
  public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent) {
    super.onInitializeAccessibilityEvent(paramAccessibilityEvent);
    paramAccessibilityEvent.setClassName("android.widget.Switch");
  }
  
  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo) {
    CharSequence charSequence;
    super.onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
    paramAccessibilityNodeInfo.setClassName("android.widget.Switch");
    if (isChecked()) {
      charSequence = this.mTextOn;
    } else {
      charSequence = this.mTextOff;
    } 
    if (!TextUtils.isEmpty(charSequence)) {
      CharSequence charSequence1 = paramAccessibilityNodeInfo.getText();
      if (TextUtils.isEmpty(charSequence1)) {
        paramAccessibilityNodeInfo.setText(charSequence);
      } else {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(charSequence1);
        stringBuilder.append(' ');
        stringBuilder.append(charSequence);
        paramAccessibilityNodeInfo.setText(stringBuilder);
      } 
    } 
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    Drawable drawable = this.mThumbDrawable;
    paramInt1 = 0;
    if (drawable != null) {
      Rect rect1 = this.mTempRect;
      Drawable drawable1 = this.mTrackDrawable;
      if (drawable1 != null) {
        drawable1.getPadding(rect1);
      } else {
        rect1.setEmpty();
      } 
      Rect rect2 = DrawableUtils.getOpticalBounds(this.mThumbDrawable);
      paramInt2 = Math.max(0, rect2.left - rect1.left);
      paramInt1 = Math.max(0, rect2.right - rect1.right);
    } else {
      paramInt2 = 0;
    } 
    if (ViewUtils.isLayoutRtl((View)this)) {
      paramInt3 = getPaddingLeft() + paramInt2;
      paramInt2 = this.mSwitchWidth + paramInt3 - paramInt2 - paramInt1;
    } else {
      paramInt3 = getWidth() - getPaddingRight() - paramInt1;
      paramInt1 = paramInt3 - this.mSwitchWidth + paramInt2 + paramInt1;
      paramInt2 = paramInt3;
      paramInt3 = paramInt1;
    } 
    paramInt1 = getGravity() & 0x70;
    if (paramInt1 != 16) {
      if (paramInt1 != 80) {
        paramInt1 = getPaddingTop();
        paramInt4 = this.mSwitchHeight;
      } else {
        paramInt4 = getHeight() - getPaddingBottom();
        paramInt1 = paramInt4 - this.mSwitchHeight;
        this.mSwitchLeft = paramInt3;
        this.mSwitchTop = paramInt1;
        this.mSwitchBottom = paramInt4;
        this.mSwitchRight = paramInt2;
      } 
    } else {
      paramInt1 = (getPaddingTop() + getHeight() - getPaddingBottom()) / 2;
      paramInt4 = this.mSwitchHeight;
      paramInt1 -= paramInt4 / 2;
    } 
    paramInt4 += paramInt1;
    this.mSwitchLeft = paramInt3;
    this.mSwitchTop = paramInt1;
    this.mSwitchBottom = paramInt4;
    this.mSwitchRight = paramInt2;
  }
  
  public void onMeasure(int paramInt1, int paramInt2) {
    int j;
    if (this.mShowText) {
      if (this.mOnLayout == null)
        this.mOnLayout = makeLayout(this.mTextOn); 
      if (this.mOffLayout == null)
        this.mOffLayout = makeLayout(this.mTextOff); 
    } 
    Rect rect = this.mTempRect;
    Drawable drawable2 = this.mThumbDrawable;
    int i = 0;
    if (drawable2 != null) {
      drawable2.getPadding(rect);
      j = this.mThumbDrawable.getIntrinsicWidth() - rect.left - rect.right;
      k = this.mThumbDrawable.getIntrinsicHeight();
    } else {
      j = 0;
      k = 0;
    } 
    if (this.mShowText) {
      m = Math.max(this.mOnLayout.getWidth(), this.mOffLayout.getWidth()) + this.mThumbTextPadding * 2;
    } else {
      m = 0;
    } 
    this.mThumbWidth = Math.max(m, j);
    drawable2 = this.mTrackDrawable;
    if (drawable2 != null) {
      drawable2.getPadding(rect);
      j = this.mTrackDrawable.getIntrinsicHeight();
    } else {
      rect.setEmpty();
      j = i;
    } 
    int n = rect.left;
    int i1 = rect.right;
    Drawable drawable1 = this.mThumbDrawable;
    i = i1;
    int m = n;
    if (drawable1 != null) {
      Rect rect1 = DrawableUtils.getOpticalBounds(drawable1);
      m = Math.max(n, rect1.left);
      i = Math.max(i1, rect1.right);
    } 
    m = Math.max(this.mSwitchMinWidth, this.mThumbWidth * 2 + m + i);
    int k = Math.max(j, k);
    this.mSwitchWidth = m;
    this.mSwitchHeight = k;
    super.onMeasure(paramInt1, paramInt2);
    if (getMeasuredHeight() < k)
      setMeasuredDimension(getMeasuredWidthAndState(), k); 
  }
  
  public void onPopulateAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent) {
    CharSequence charSequence;
    super.onPopulateAccessibilityEvent(paramAccessibilityEvent);
    if (isChecked()) {
      charSequence = this.mTextOn;
    } else {
      charSequence = this.mTextOff;
    } 
    if (charSequence != null)
      paramAccessibilityEvent.getText().add(charSequence); 
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    this.mVelocityTracker.addMovement(paramMotionEvent);
    int i = paramMotionEvent.getActionMasked();
    if (i != 0) {
      if (i != 1)
        if (i != 2) {
          if (i != 3)
            return super.onTouchEvent(paramMotionEvent); 
        } else {
          i = this.mTouchMode;
          if (i != 1) {
            if (i == 2) {
              float f1 = paramMotionEvent.getX();
              i = getThumbScrollRange();
              float f2 = f1 - this.mTouchX;
              if (i != 0) {
                f2 /= i;
              } else if (f2 > 0.0F) {
                f2 = 1.0F;
              } else {
                f2 = -1.0F;
              } 
              float f3 = f2;
              if (ViewUtils.isLayoutRtl((View)this))
                f3 = -f2; 
              f2 = constrain(this.mThumbPosition + f3, 0.0F, 1.0F);
              if (f2 != this.mThumbPosition) {
                this.mTouchX = f1;
                setThumbPosition(f2);
              } 
              return true;
            } 
          } else {
            float f2 = paramMotionEvent.getX();
            float f1 = paramMotionEvent.getY();
            if (Math.abs(f2 - this.mTouchX) > this.mTouchSlop || Math.abs(f1 - this.mTouchY) > this.mTouchSlop) {
              this.mTouchMode = 2;
              getParent().requestDisallowInterceptTouchEvent(true);
              this.mTouchX = f2;
              this.mTouchY = f1;
              return true;
            } 
          } 
          return super.onTouchEvent(paramMotionEvent);
        }  
      if (this.mTouchMode == 2) {
        stopDrag(paramMotionEvent);
        super.onTouchEvent(paramMotionEvent);
        return true;
      } 
      this.mTouchMode = 0;
      this.mVelocityTracker.clear();
    } else {
      float f1 = paramMotionEvent.getX();
      float f2 = paramMotionEvent.getY();
      if (isEnabled() && hitThumb(f1, f2)) {
        this.mTouchMode = 1;
        this.mTouchX = f1;
        this.mTouchY = f2;
      } 
    } 
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public void setChecked(boolean paramBoolean) {
    super.setChecked(paramBoolean);
    paramBoolean = isChecked();
    if (getWindowToken() != null && ViewCompat.isLaidOut((View)this)) {
      animateThumbToCheckedState(paramBoolean);
    } else {
      float f;
      cancelPositionAnimator();
      if (paramBoolean) {
        f = 1.0F;
      } else {
        f = 0.0F;
      } 
      setThumbPosition(f);
    } 
  }
  
  public void setShowText(boolean paramBoolean) {
    if (this.mShowText != paramBoolean) {
      this.mShowText = paramBoolean;
      requestLayout();
    } 
  }
  
  public void setSplitTrack(boolean paramBoolean) {
    this.mSplitTrack = paramBoolean;
    invalidate();
  }
  
  public void setSwitchMinWidth(int paramInt) {
    this.mSwitchMinWidth = paramInt;
    requestLayout();
  }
  
  public void setSwitchPadding(int paramInt) {
    this.mSwitchPadding = paramInt;
    requestLayout();
  }
  
  public void setSwitchTextAppearance(Context paramContext, int paramInt) {
    TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(paramContext, paramInt, R.styleable.TextAppearance);
    ColorStateList colorStateList = tintTypedArray.getColorStateList(R.styleable.TextAppearance_android_textColor);
    if (colorStateList != null) {
      this.mTextColors = colorStateList;
    } else {
      this.mTextColors = getTextColors();
    } 
    paramInt = tintTypedArray.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, 0);
    if (paramInt != 0) {
      float f = paramInt;
      if (f != this.mTextPaint.getTextSize()) {
        this.mTextPaint.setTextSize(f);
        requestLayout();
      } 
    } 
    setSwitchTypefaceByIndex(tintTypedArray.getInt(R.styleable.TextAppearance_android_typeface, -1), tintTypedArray.getInt(R.styleable.TextAppearance_android_textStyle, -1));
    if (tintTypedArray.getBoolean(R.styleable.TextAppearance_textAllCaps, false)) {
      this.mSwitchTransformationMethod = (TransformationMethod)new AllCapsTransformationMethod(getContext());
    } else {
      this.mSwitchTransformationMethod = null;
    } 
    tintTypedArray.recycle();
  }
  
  public void setSwitchTypeface(Typeface paramTypeface) {
    if ((this.mTextPaint.getTypeface() != null && !this.mTextPaint.getTypeface().equals(paramTypeface)) || (this.mTextPaint.getTypeface() == null && paramTypeface != null)) {
      this.mTextPaint.setTypeface(paramTypeface);
      requestLayout();
      invalidate();
    } 
  }
  
  public void setSwitchTypeface(Typeface paramTypeface, int paramInt) {
    TextPaint textPaint;
    float f = 0.0F;
    boolean bool = false;
    if (paramInt > 0) {
      int i;
      if (paramTypeface == null) {
        paramTypeface = Typeface.defaultFromStyle(paramInt);
      } else {
        paramTypeface = Typeface.create(paramTypeface, paramInt);
      } 
      setSwitchTypeface(paramTypeface);
      if (paramTypeface != null) {
        i = paramTypeface.getStyle();
      } else {
        i = 0;
      } 
      paramInt = i & paramInt;
      textPaint = this.mTextPaint;
      if ((paramInt & 0x1) != 0)
        bool = true; 
      textPaint.setFakeBoldText(bool);
      textPaint = this.mTextPaint;
      if ((paramInt & 0x2) != 0)
        f = -0.25F; 
      textPaint.setTextSkewX(f);
    } else {
      this.mTextPaint.setFakeBoldText(false);
      this.mTextPaint.setTextSkewX(0.0F);
      setSwitchTypeface((Typeface)textPaint);
    } 
  }
  
  public void setTextOff(CharSequence paramCharSequence) {
    this.mTextOff = paramCharSequence;
    requestLayout();
  }
  
  public void setTextOn(CharSequence paramCharSequence) {
    this.mTextOn = paramCharSequence;
    requestLayout();
  }
  
  public void setThumbDrawable(Drawable paramDrawable) {
    Drawable drawable = this.mThumbDrawable;
    if (drawable != null)
      drawable.setCallback(null); 
    this.mThumbDrawable = paramDrawable;
    if (paramDrawable != null)
      paramDrawable.setCallback((Drawable.Callback)this); 
    requestLayout();
  }
  
  void setThumbPosition(float paramFloat) {
    this.mThumbPosition = paramFloat;
    invalidate();
  }
  
  public void setThumbResource(int paramInt) {
    setThumbDrawable(AppCompatResources.getDrawable(getContext(), paramInt));
  }
  
  public void setThumbTextPadding(int paramInt) {
    this.mThumbTextPadding = paramInt;
    requestLayout();
  }
  
  public void setThumbTintList(ColorStateList paramColorStateList) {
    this.mThumbTintList = paramColorStateList;
    this.mHasThumbTint = true;
    applyThumbTint();
  }
  
  public void setThumbTintMode(PorterDuff.Mode paramMode) {
    this.mThumbTintMode = paramMode;
    this.mHasThumbTintMode = true;
    applyThumbTint();
  }
  
  public void setTrackDrawable(Drawable paramDrawable) {
    Drawable drawable = this.mTrackDrawable;
    if (drawable != null)
      drawable.setCallback(null); 
    this.mTrackDrawable = paramDrawable;
    if (paramDrawable != null)
      paramDrawable.setCallback((Drawable.Callback)this); 
    requestLayout();
  }
  
  public void setTrackResource(int paramInt) {
    setTrackDrawable(AppCompatResources.getDrawable(getContext(), paramInt));
  }
  
  public void setTrackTintList(ColorStateList paramColorStateList) {
    this.mTrackTintList = paramColorStateList;
    this.mHasTrackTint = true;
    applyTrackTint();
  }
  
  public void setTrackTintMode(PorterDuff.Mode paramMode) {
    this.mTrackTintMode = paramMode;
    this.mHasTrackTintMode = true;
    applyTrackTint();
  }
  
  public void toggle() {
    setChecked(isChecked() ^ true);
  }
  
  protected boolean verifyDrawable(Drawable paramDrawable) {
    return (super.verifyDrawable(paramDrawable) || paramDrawable == this.mThumbDrawable || paramDrawable == this.mTrackDrawable);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\SwitchCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */