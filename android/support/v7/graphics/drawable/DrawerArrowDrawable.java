package android.support.v7.graphics.drawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.support.v7.appcompat.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DrawerArrowDrawable extends Drawable {
  public static final int ARROW_DIRECTION_END = 3;
  
  public static final int ARROW_DIRECTION_LEFT = 0;
  
  public static final int ARROW_DIRECTION_RIGHT = 1;
  
  public static final int ARROW_DIRECTION_START = 2;
  
  private static final float ARROW_HEAD_ANGLE = (float)Math.toRadians(45.0D);
  
  private float mArrowHeadLength;
  
  private float mArrowShaftLength;
  
  private float mBarGap;
  
  private float mBarLength;
  
  private int mDirection = 2;
  
  private float mMaxCutForBarSize;
  
  private final Paint mPaint = new Paint();
  
  private final Path mPath = new Path();
  
  private float mProgress;
  
  private final int mSize;
  
  private boolean mSpin;
  
  private boolean mVerticalMirror = false;
  
  public DrawerArrowDrawable(Context paramContext) {
    this.mPaint.setStyle(Paint.Style.STROKE);
    this.mPaint.setStrokeJoin(Paint.Join.MITER);
    this.mPaint.setStrokeCap(Paint.Cap.BUTT);
    this.mPaint.setAntiAlias(true);
    TypedArray typedArray = paramContext.getTheme().obtainStyledAttributes(null, R.styleable.DrawerArrowToggle, R.attr.drawerArrowStyle, R.style.Base_Widget_AppCompat_DrawerArrowToggle);
    setColor(typedArray.getColor(R.styleable.DrawerArrowToggle_color, 0));
    setBarThickness(typedArray.getDimension(R.styleable.DrawerArrowToggle_thickness, 0.0F));
    setSpinEnabled(typedArray.getBoolean(R.styleable.DrawerArrowToggle_spinBars, true));
    setGapSize(Math.round(typedArray.getDimension(R.styleable.DrawerArrowToggle_gapBetweenBars, 0.0F)));
    this.mSize = typedArray.getDimensionPixelSize(R.styleable.DrawerArrowToggle_drawableSize, 0);
    this.mBarLength = Math.round(typedArray.getDimension(R.styleable.DrawerArrowToggle_barLength, 0.0F));
    this.mArrowHeadLength = Math.round(typedArray.getDimension(R.styleable.DrawerArrowToggle_arrowHeadLength, 0.0F));
    this.mArrowShaftLength = typedArray.getDimension(R.styleable.DrawerArrowToggle_arrowShaftLength, 0.0F);
    typedArray.recycle();
  }
  
  private static float lerp(float paramFloat1, float paramFloat2, float paramFloat3) {
    return paramFloat1 + (paramFloat2 - paramFloat1) * paramFloat3;
  }
  
  public void draw(Canvas paramCanvas) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual getBounds : ()Landroid/graphics/Rect;
    //   4: astore_2
    //   5: aload_0
    //   6: getfield mDirection : I
    //   9: istore_3
    //   10: iconst_0
    //   11: istore #4
    //   13: iload #4
    //   15: istore #5
    //   17: iload_3
    //   18: ifeq -> 60
    //   21: iload_3
    //   22: iconst_1
    //   23: if_icmpeq -> 57
    //   26: iload_3
    //   27: iconst_3
    //   28: if_icmpeq -> 46
    //   31: iload #4
    //   33: istore #5
    //   35: aload_0
    //   36: invokestatic getLayoutDirection : (Landroid/graphics/drawable/Drawable;)I
    //   39: iconst_1
    //   40: if_icmpne -> 60
    //   43: goto -> 57
    //   46: iload #4
    //   48: istore #5
    //   50: aload_0
    //   51: invokestatic getLayoutDirection : (Landroid/graphics/drawable/Drawable;)I
    //   54: ifne -> 60
    //   57: iconst_1
    //   58: istore #5
    //   60: aload_0
    //   61: getfield mArrowHeadLength : F
    //   64: fstore #6
    //   66: fload #6
    //   68: fload #6
    //   70: fmul
    //   71: fconst_2
    //   72: fmul
    //   73: f2d
    //   74: invokestatic sqrt : (D)D
    //   77: d2f
    //   78: fstore #6
    //   80: aload_0
    //   81: getfield mBarLength : F
    //   84: fload #6
    //   86: aload_0
    //   87: getfield mProgress : F
    //   90: invokestatic lerp : (FFF)F
    //   93: fstore #7
    //   95: aload_0
    //   96: getfield mBarLength : F
    //   99: aload_0
    //   100: getfield mArrowShaftLength : F
    //   103: aload_0
    //   104: getfield mProgress : F
    //   107: invokestatic lerp : (FFF)F
    //   110: fstore #8
    //   112: fconst_0
    //   113: aload_0
    //   114: getfield mMaxCutForBarSize : F
    //   117: aload_0
    //   118: getfield mProgress : F
    //   121: invokestatic lerp : (FFF)F
    //   124: invokestatic round : (F)I
    //   127: i2f
    //   128: fstore #9
    //   130: fconst_0
    //   131: getstatic android/support/v7/graphics/drawable/DrawerArrowDrawable.ARROW_HEAD_ANGLE : F
    //   134: aload_0
    //   135: getfield mProgress : F
    //   138: invokestatic lerp : (FFF)F
    //   141: fstore #10
    //   143: iload #5
    //   145: ifeq -> 154
    //   148: fconst_0
    //   149: fstore #6
    //   151: goto -> 158
    //   154: ldc -180.0
    //   156: fstore #6
    //   158: iload #5
    //   160: ifeq -> 170
    //   163: ldc 180.0
    //   165: fstore #11
    //   167: goto -> 173
    //   170: fconst_0
    //   171: fstore #11
    //   173: fload #6
    //   175: fload #11
    //   177: aload_0
    //   178: getfield mProgress : F
    //   181: invokestatic lerp : (FFF)F
    //   184: fstore #6
    //   186: fload #7
    //   188: f2d
    //   189: dstore #12
    //   191: fload #10
    //   193: f2d
    //   194: dstore #14
    //   196: dload #14
    //   198: invokestatic cos : (D)D
    //   201: dload #12
    //   203: dmul
    //   204: invokestatic round : (D)J
    //   207: l2f
    //   208: fstore #10
    //   210: dload #12
    //   212: dload #14
    //   214: invokestatic sin : (D)D
    //   217: dmul
    //   218: invokestatic round : (D)J
    //   221: l2f
    //   222: fstore #11
    //   224: aload_0
    //   225: getfield mPath : Landroid/graphics/Path;
    //   228: invokevirtual rewind : ()V
    //   231: aload_0
    //   232: getfield mBarGap : F
    //   235: aload_0
    //   236: getfield mPaint : Landroid/graphics/Paint;
    //   239: invokevirtual getStrokeWidth : ()F
    //   242: fadd
    //   243: aload_0
    //   244: getfield mMaxCutForBarSize : F
    //   247: fneg
    //   248: aload_0
    //   249: getfield mProgress : F
    //   252: invokestatic lerp : (FFF)F
    //   255: fstore #7
    //   257: fload #8
    //   259: fneg
    //   260: fconst_2
    //   261: fdiv
    //   262: fstore #16
    //   264: aload_0
    //   265: getfield mPath : Landroid/graphics/Path;
    //   268: fload #16
    //   270: fload #9
    //   272: fadd
    //   273: fconst_0
    //   274: invokevirtual moveTo : (FF)V
    //   277: aload_0
    //   278: getfield mPath : Landroid/graphics/Path;
    //   281: fload #8
    //   283: fload #9
    //   285: fconst_2
    //   286: fmul
    //   287: fsub
    //   288: fconst_0
    //   289: invokevirtual rLineTo : (FF)V
    //   292: aload_0
    //   293: getfield mPath : Landroid/graphics/Path;
    //   296: fload #16
    //   298: fload #7
    //   300: invokevirtual moveTo : (FF)V
    //   303: aload_0
    //   304: getfield mPath : Landroid/graphics/Path;
    //   307: fload #10
    //   309: fload #11
    //   311: invokevirtual rLineTo : (FF)V
    //   314: aload_0
    //   315: getfield mPath : Landroid/graphics/Path;
    //   318: fload #16
    //   320: fload #7
    //   322: fneg
    //   323: invokevirtual moveTo : (FF)V
    //   326: aload_0
    //   327: getfield mPath : Landroid/graphics/Path;
    //   330: fload #10
    //   332: fload #11
    //   334: fneg
    //   335: invokevirtual rLineTo : (FF)V
    //   338: aload_0
    //   339: getfield mPath : Landroid/graphics/Path;
    //   342: invokevirtual close : ()V
    //   345: aload_1
    //   346: invokevirtual save : ()I
    //   349: pop
    //   350: aload_0
    //   351: getfield mPaint : Landroid/graphics/Paint;
    //   354: invokevirtual getStrokeWidth : ()F
    //   357: fstore #11
    //   359: aload_2
    //   360: invokevirtual height : ()I
    //   363: i2f
    //   364: fstore #9
    //   366: aload_0
    //   367: getfield mBarGap : F
    //   370: fstore #8
    //   372: fload #9
    //   374: ldc_w 3.0
    //   377: fload #11
    //   379: fmul
    //   380: fsub
    //   381: fconst_2
    //   382: fload #8
    //   384: fmul
    //   385: fsub
    //   386: f2i
    //   387: iconst_4
    //   388: idiv
    //   389: iconst_2
    //   390: imul
    //   391: i2f
    //   392: fstore #9
    //   394: aload_1
    //   395: aload_2
    //   396: invokevirtual centerX : ()I
    //   399: i2f
    //   400: fload #9
    //   402: fload #11
    //   404: ldc_w 1.5
    //   407: fmul
    //   408: fload #8
    //   410: fadd
    //   411: fadd
    //   412: invokevirtual translate : (FF)V
    //   415: aload_0
    //   416: getfield mSpin : Z
    //   419: ifeq -> 454
    //   422: aload_0
    //   423: getfield mVerticalMirror : Z
    //   426: iload #5
    //   428: ixor
    //   429: ifeq -> 438
    //   432: iconst_m1
    //   433: istore #5
    //   435: goto -> 441
    //   438: iconst_1
    //   439: istore #5
    //   441: aload_1
    //   442: fload #6
    //   444: iload #5
    //   446: i2f
    //   447: fmul
    //   448: invokevirtual rotate : (F)V
    //   451: goto -> 465
    //   454: iload #5
    //   456: ifeq -> 465
    //   459: aload_1
    //   460: ldc 180.0
    //   462: invokevirtual rotate : (F)V
    //   465: aload_1
    //   466: aload_0
    //   467: getfield mPath : Landroid/graphics/Path;
    //   470: aload_0
    //   471: getfield mPaint : Landroid/graphics/Paint;
    //   474: invokevirtual drawPath : (Landroid/graphics/Path;Landroid/graphics/Paint;)V
    //   477: aload_1
    //   478: invokevirtual restore : ()V
    //   481: return
  }
  
  public float getArrowHeadLength() {
    return this.mArrowHeadLength;
  }
  
  public float getArrowShaftLength() {
    return this.mArrowShaftLength;
  }
  
  public float getBarLength() {
    return this.mBarLength;
  }
  
  public float getBarThickness() {
    return this.mPaint.getStrokeWidth();
  }
  
  public int getColor() {
    return this.mPaint.getColor();
  }
  
  public int getDirection() {
    return this.mDirection;
  }
  
  public float getGapSize() {
    return this.mBarGap;
  }
  
  public int getIntrinsicHeight() {
    return this.mSize;
  }
  
  public int getIntrinsicWidth() {
    return this.mSize;
  }
  
  public int getOpacity() {
    return -3;
  }
  
  public final Paint getPaint() {
    return this.mPaint;
  }
  
  public float getProgress() {
    return this.mProgress;
  }
  
  public boolean isSpinEnabled() {
    return this.mSpin;
  }
  
  public void setAlpha(int paramInt) {
    if (paramInt != this.mPaint.getAlpha()) {
      this.mPaint.setAlpha(paramInt);
      invalidateSelf();
    } 
  }
  
  public void setArrowHeadLength(float paramFloat) {
    if (this.mArrowHeadLength != paramFloat) {
      this.mArrowHeadLength = paramFloat;
      invalidateSelf();
    } 
  }
  
  public void setArrowShaftLength(float paramFloat) {
    if (this.mArrowShaftLength != paramFloat) {
      this.mArrowShaftLength = paramFloat;
      invalidateSelf();
    } 
  }
  
  public void setBarLength(float paramFloat) {
    if (this.mBarLength != paramFloat) {
      this.mBarLength = paramFloat;
      invalidateSelf();
    } 
  }
  
  public void setBarThickness(float paramFloat) {
    if (this.mPaint.getStrokeWidth() != paramFloat) {
      this.mPaint.setStrokeWidth(paramFloat);
      this.mMaxCutForBarSize = (float)((paramFloat / 2.0F) * Math.cos(ARROW_HEAD_ANGLE));
      invalidateSelf();
    } 
  }
  
  public void setColor(int paramInt) {
    if (paramInt != this.mPaint.getColor()) {
      this.mPaint.setColor(paramInt);
      invalidateSelf();
    } 
  }
  
  public void setColorFilter(ColorFilter paramColorFilter) {
    this.mPaint.setColorFilter(paramColorFilter);
    invalidateSelf();
  }
  
  public void setDirection(int paramInt) {
    if (paramInt != this.mDirection) {
      this.mDirection = paramInt;
      invalidateSelf();
    } 
  }
  
  public void setGapSize(float paramFloat) {
    if (paramFloat != this.mBarGap) {
      this.mBarGap = paramFloat;
      invalidateSelf();
    } 
  }
  
  public void setProgress(float paramFloat) {
    if (this.mProgress != paramFloat) {
      this.mProgress = paramFloat;
      invalidateSelf();
    } 
  }
  
  public void setSpinEnabled(boolean paramBoolean) {
    if (this.mSpin != paramBoolean) {
      this.mSpin = paramBoolean;
      invalidateSelf();
    } 
  }
  
  public void setVerticalMirror(boolean paramBoolean) {
    if (this.mVerticalMirror != paramBoolean) {
      this.mVerticalMirror = paramBoolean;
      invalidateSelf();
    } 
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface ArrowDirection {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\graphics\drawable\DrawerArrowDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */