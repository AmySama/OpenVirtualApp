package android.support.v4.view;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

public final class GestureDetectorCompat {
  private final GestureDetectorCompatImpl mImpl;
  
  public GestureDetectorCompat(Context paramContext, GestureDetector.OnGestureListener paramOnGestureListener) {
    this(paramContext, paramOnGestureListener, null);
  }
  
  public GestureDetectorCompat(Context paramContext, GestureDetector.OnGestureListener paramOnGestureListener, Handler paramHandler) {
    if (Build.VERSION.SDK_INT > 17) {
      this.mImpl = new GestureDetectorCompatImplJellybeanMr2(paramContext, paramOnGestureListener, paramHandler);
    } else {
      this.mImpl = new GestureDetectorCompatImplBase(paramContext, paramOnGestureListener, paramHandler);
    } 
  }
  
  public boolean isLongpressEnabled() {
    return this.mImpl.isLongpressEnabled();
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    return this.mImpl.onTouchEvent(paramMotionEvent);
  }
  
  public void setIsLongpressEnabled(boolean paramBoolean) {
    this.mImpl.setIsLongpressEnabled(paramBoolean);
  }
  
  public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener paramOnDoubleTapListener) {
    this.mImpl.setOnDoubleTapListener(paramOnDoubleTapListener);
  }
  
  static interface GestureDetectorCompatImpl {
    boolean isLongpressEnabled();
    
    boolean onTouchEvent(MotionEvent param1MotionEvent);
    
    void setIsLongpressEnabled(boolean param1Boolean);
    
    void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener param1OnDoubleTapListener);
  }
  
  static class GestureDetectorCompatImplBase implements GestureDetectorCompatImpl {
    private static final int DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();
    
    private static final int LONGPRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout();
    
    private static final int LONG_PRESS = 2;
    
    private static final int SHOW_PRESS = 1;
    
    private static final int TAP = 3;
    
    private static final int TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
    
    private boolean mAlwaysInBiggerTapRegion;
    
    private boolean mAlwaysInTapRegion;
    
    MotionEvent mCurrentDownEvent;
    
    boolean mDeferConfirmSingleTap;
    
    GestureDetector.OnDoubleTapListener mDoubleTapListener;
    
    private int mDoubleTapSlopSquare;
    
    private float mDownFocusX;
    
    private float mDownFocusY;
    
    private final Handler mHandler;
    
    private boolean mInLongPress;
    
    private boolean mIsDoubleTapping;
    
    private boolean mIsLongpressEnabled;
    
    private float mLastFocusX;
    
    private float mLastFocusY;
    
    final GestureDetector.OnGestureListener mListener;
    
    private int mMaximumFlingVelocity;
    
    private int mMinimumFlingVelocity;
    
    private MotionEvent mPreviousUpEvent;
    
    boolean mStillDown;
    
    private int mTouchSlopSquare;
    
    private VelocityTracker mVelocityTracker;
    
    static {
    
    }
    
    GestureDetectorCompatImplBase(Context param1Context, GestureDetector.OnGestureListener param1OnGestureListener, Handler param1Handler) {
      if (param1Handler != null) {
        this.mHandler = new GestureHandler(param1Handler);
      } else {
        this.mHandler = new GestureHandler();
      } 
      this.mListener = param1OnGestureListener;
      if (param1OnGestureListener instanceof GestureDetector.OnDoubleTapListener)
        setOnDoubleTapListener((GestureDetector.OnDoubleTapListener)param1OnGestureListener); 
      init(param1Context);
    }
    
    private void cancel() {
      this.mHandler.removeMessages(1);
      this.mHandler.removeMessages(2);
      this.mHandler.removeMessages(3);
      this.mVelocityTracker.recycle();
      this.mVelocityTracker = null;
      this.mIsDoubleTapping = false;
      this.mStillDown = false;
      this.mAlwaysInTapRegion = false;
      this.mAlwaysInBiggerTapRegion = false;
      this.mDeferConfirmSingleTap = false;
      if (this.mInLongPress)
        this.mInLongPress = false; 
    }
    
    private void cancelTaps() {
      this.mHandler.removeMessages(1);
      this.mHandler.removeMessages(2);
      this.mHandler.removeMessages(3);
      this.mIsDoubleTapping = false;
      this.mAlwaysInTapRegion = false;
      this.mAlwaysInBiggerTapRegion = false;
      this.mDeferConfirmSingleTap = false;
      if (this.mInLongPress)
        this.mInLongPress = false; 
    }
    
    private void init(Context param1Context) {
      if (param1Context != null) {
        if (this.mListener != null) {
          this.mIsLongpressEnabled = true;
          ViewConfiguration viewConfiguration = ViewConfiguration.get(param1Context);
          int i = viewConfiguration.getScaledTouchSlop();
          int j = viewConfiguration.getScaledDoubleTapSlop();
          this.mMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
          this.mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
          this.mTouchSlopSquare = i * i;
          this.mDoubleTapSlopSquare = j * j;
          return;
        } 
        throw new IllegalArgumentException("OnGestureListener must not be null");
      } 
      throw new IllegalArgumentException("Context must not be null");
    }
    
    private boolean isConsideredDoubleTap(MotionEvent param1MotionEvent1, MotionEvent param1MotionEvent2, MotionEvent param1MotionEvent3) {
      boolean bool = this.mAlwaysInBiggerTapRegion;
      boolean bool1 = false;
      if (!bool)
        return false; 
      if (param1MotionEvent3.getEventTime() - param1MotionEvent2.getEventTime() > DOUBLE_TAP_TIMEOUT)
        return false; 
      int i = (int)param1MotionEvent1.getX() - (int)param1MotionEvent3.getX();
      int j = (int)param1MotionEvent1.getY() - (int)param1MotionEvent3.getY();
      if (i * i + j * j < this.mDoubleTapSlopSquare)
        bool1 = true; 
      return bool1;
    }
    
    void dispatchLongPress() {
      this.mHandler.removeMessages(3);
      this.mDeferConfirmSingleTap = false;
      this.mInLongPress = true;
      this.mListener.onLongPress(this.mCurrentDownEvent);
    }
    
    public boolean isLongpressEnabled() {
      return this.mIsLongpressEnabled;
    }
    
    public boolean onTouchEvent(MotionEvent param1MotionEvent) {
      // Byte code:
      //   0: aload_1
      //   1: invokevirtual getAction : ()I
      //   4: istore_2
      //   5: aload_0
      //   6: getfield mVelocityTracker : Landroid/view/VelocityTracker;
      //   9: ifnonnull -> 19
      //   12: aload_0
      //   13: invokestatic obtain : ()Landroid/view/VelocityTracker;
      //   16: putfield mVelocityTracker : Landroid/view/VelocityTracker;
      //   19: aload_0
      //   20: getfield mVelocityTracker : Landroid/view/VelocityTracker;
      //   23: aload_1
      //   24: invokevirtual addMovement : (Landroid/view/MotionEvent;)V
      //   27: iload_2
      //   28: sipush #255
      //   31: iand
      //   32: istore_3
      //   33: iconst_0
      //   34: istore #4
      //   36: iload_3
      //   37: bipush #6
      //   39: if_icmpne -> 47
      //   42: iconst_1
      //   43: istore_2
      //   44: goto -> 49
      //   47: iconst_0
      //   48: istore_2
      //   49: iload_2
      //   50: ifeq -> 62
      //   53: aload_1
      //   54: invokevirtual getActionIndex : ()I
      //   57: istore #5
      //   59: goto -> 65
      //   62: iconst_m1
      //   63: istore #5
      //   65: aload_1
      //   66: invokevirtual getPointerCount : ()I
      //   69: istore #6
      //   71: iconst_0
      //   72: istore #7
      //   74: fconst_0
      //   75: fstore #8
      //   77: fconst_0
      //   78: fstore #9
      //   80: iload #7
      //   82: iload #6
      //   84: if_icmpge -> 125
      //   87: iload #5
      //   89: iload #7
      //   91: if_icmpne -> 97
      //   94: goto -> 119
      //   97: fload #8
      //   99: aload_1
      //   100: iload #7
      //   102: invokevirtual getX : (I)F
      //   105: fadd
      //   106: fstore #8
      //   108: fload #9
      //   110: aload_1
      //   111: iload #7
      //   113: invokevirtual getY : (I)F
      //   116: fadd
      //   117: fstore #9
      //   119: iinc #7, 1
      //   122: goto -> 80
      //   125: iload_2
      //   126: ifeq -> 137
      //   129: iload #6
      //   131: iconst_1
      //   132: isub
      //   133: istore_2
      //   134: goto -> 140
      //   137: iload #6
      //   139: istore_2
      //   140: iload_2
      //   141: i2f
      //   142: fstore #10
      //   144: fload #8
      //   146: fload #10
      //   148: fdiv
      //   149: fstore #8
      //   151: fload #9
      //   153: fload #10
      //   155: fdiv
      //   156: fstore #10
      //   158: iload_3
      //   159: ifeq -> 920
      //   162: iload_3
      //   163: iconst_1
      //   164: if_icmpeq -> 641
      //   167: iload_3
      //   168: iconst_2
      //   169: if_icmpeq -> 391
      //   172: iload_3
      //   173: iconst_3
      //   174: if_icmpeq -> 380
      //   177: iload_3
      //   178: iconst_5
      //   179: if_icmpeq -> 345
      //   182: iload_3
      //   183: bipush #6
      //   185: if_icmpeq -> 195
      //   188: iload #4
      //   190: istore #11
      //   192: goto -> 1188
      //   195: aload_0
      //   196: fload #8
      //   198: putfield mLastFocusX : F
      //   201: aload_0
      //   202: fload #8
      //   204: putfield mDownFocusX : F
      //   207: aload_0
      //   208: fload #10
      //   210: putfield mLastFocusY : F
      //   213: aload_0
      //   214: fload #10
      //   216: putfield mDownFocusY : F
      //   219: aload_0
      //   220: getfield mVelocityTracker : Landroid/view/VelocityTracker;
      //   223: sipush #1000
      //   226: aload_0
      //   227: getfield mMaximumFlingVelocity : I
      //   230: i2f
      //   231: invokevirtual computeCurrentVelocity : (IF)V
      //   234: aload_1
      //   235: invokevirtual getActionIndex : ()I
      //   238: istore #5
      //   240: aload_1
      //   241: iload #5
      //   243: invokevirtual getPointerId : (I)I
      //   246: istore_2
      //   247: aload_0
      //   248: getfield mVelocityTracker : Landroid/view/VelocityTracker;
      //   251: iload_2
      //   252: invokevirtual getXVelocity : (I)F
      //   255: fstore #9
      //   257: aload_0
      //   258: getfield mVelocityTracker : Landroid/view/VelocityTracker;
      //   261: iload_2
      //   262: invokevirtual getYVelocity : (I)F
      //   265: fstore #8
      //   267: iconst_0
      //   268: istore_2
      //   269: iload #4
      //   271: istore #11
      //   273: iload_2
      //   274: iload #6
      //   276: if_icmpge -> 1188
      //   279: iload_2
      //   280: iload #5
      //   282: if_icmpne -> 288
      //   285: goto -> 339
      //   288: aload_1
      //   289: iload_2
      //   290: invokevirtual getPointerId : (I)I
      //   293: istore #7
      //   295: aload_0
      //   296: getfield mVelocityTracker : Landroid/view/VelocityTracker;
      //   299: iload #7
      //   301: invokevirtual getXVelocity : (I)F
      //   304: fload #9
      //   306: fmul
      //   307: aload_0
      //   308: getfield mVelocityTracker : Landroid/view/VelocityTracker;
      //   311: iload #7
      //   313: invokevirtual getYVelocity : (I)F
      //   316: fload #8
      //   318: fmul
      //   319: fadd
      //   320: fconst_0
      //   321: fcmpg
      //   322: ifge -> 339
      //   325: aload_0
      //   326: getfield mVelocityTracker : Landroid/view/VelocityTracker;
      //   329: invokevirtual clear : ()V
      //   332: iload #4
      //   334: istore #11
      //   336: goto -> 1188
      //   339: iinc #2, 1
      //   342: goto -> 269
      //   345: aload_0
      //   346: fload #8
      //   348: putfield mLastFocusX : F
      //   351: aload_0
      //   352: fload #8
      //   354: putfield mDownFocusX : F
      //   357: aload_0
      //   358: fload #10
      //   360: putfield mLastFocusY : F
      //   363: aload_0
      //   364: fload #10
      //   366: putfield mDownFocusY : F
      //   369: aload_0
      //   370: invokespecial cancelTaps : ()V
      //   373: iload #4
      //   375: istore #11
      //   377: goto -> 1188
      //   380: aload_0
      //   381: invokespecial cancel : ()V
      //   384: iload #4
      //   386: istore #11
      //   388: goto -> 1188
      //   391: aload_0
      //   392: getfield mInLongPress : Z
      //   395: ifeq -> 405
      //   398: iload #4
      //   400: istore #11
      //   402: goto -> 1188
      //   405: aload_0
      //   406: getfield mLastFocusX : F
      //   409: fload #8
      //   411: fsub
      //   412: fstore #9
      //   414: aload_0
      //   415: getfield mLastFocusY : F
      //   418: fload #10
      //   420: fsub
      //   421: fstore #12
      //   423: aload_0
      //   424: getfield mIsDoubleTapping : Z
      //   427: ifeq -> 447
      //   430: iconst_0
      //   431: aload_0
      //   432: getfield mDoubleTapListener : Landroid/view/GestureDetector$OnDoubleTapListener;
      //   435: aload_1
      //   436: invokeinterface onDoubleTapEvent : (Landroid/view/MotionEvent;)Z
      //   441: ior
      //   442: istore #11
      //   444: goto -> 1188
      //   447: aload_0
      //   448: getfield mAlwaysInTapRegion : Z
      //   451: ifeq -> 582
      //   454: fload #8
      //   456: aload_0
      //   457: getfield mDownFocusX : F
      //   460: fsub
      //   461: f2i
      //   462: istore_2
      //   463: fload #10
      //   465: aload_0
      //   466: getfield mDownFocusY : F
      //   469: fsub
      //   470: f2i
      //   471: istore #5
      //   473: iload_2
      //   474: iload_2
      //   475: imul
      //   476: iload #5
      //   478: iload #5
      //   480: imul
      //   481: iadd
      //   482: istore_2
      //   483: iload_2
      //   484: aload_0
      //   485: getfield mTouchSlopSquare : I
      //   488: if_icmple -> 555
      //   491: aload_0
      //   492: getfield mListener : Landroid/view/GestureDetector$OnGestureListener;
      //   495: aload_0
      //   496: getfield mCurrentDownEvent : Landroid/view/MotionEvent;
      //   499: aload_1
      //   500: fload #9
      //   502: fload #12
      //   504: invokeinterface onScroll : (Landroid/view/MotionEvent;Landroid/view/MotionEvent;FF)Z
      //   509: istore #11
      //   511: aload_0
      //   512: fload #8
      //   514: putfield mLastFocusX : F
      //   517: aload_0
      //   518: fload #10
      //   520: putfield mLastFocusY : F
      //   523: aload_0
      //   524: iconst_0
      //   525: putfield mAlwaysInTapRegion : Z
      //   528: aload_0
      //   529: getfield mHandler : Landroid/os/Handler;
      //   532: iconst_3
      //   533: invokevirtual removeMessages : (I)V
      //   536: aload_0
      //   537: getfield mHandler : Landroid/os/Handler;
      //   540: iconst_1
      //   541: invokevirtual removeMessages : (I)V
      //   544: aload_0
      //   545: getfield mHandler : Landroid/os/Handler;
      //   548: iconst_2
      //   549: invokevirtual removeMessages : (I)V
      //   552: goto -> 558
      //   555: iconst_0
      //   556: istore #11
      //   558: iload #11
      //   560: istore #4
      //   562: iload_2
      //   563: aload_0
      //   564: getfield mTouchSlopSquare : I
      //   567: if_icmple -> 913
      //   570: aload_0
      //   571: iconst_0
      //   572: putfield mAlwaysInBiggerTapRegion : Z
      //   575: iload #11
      //   577: istore #4
      //   579: goto -> 913
      //   582: fload #9
      //   584: invokestatic abs : (F)F
      //   587: fconst_1
      //   588: fcmpl
      //   589: ifge -> 606
      //   592: iload #4
      //   594: istore #11
      //   596: fload #12
      //   598: invokestatic abs : (F)F
      //   601: fconst_1
      //   602: fcmpl
      //   603: iflt -> 1188
      //   606: aload_0
      //   607: getfield mListener : Landroid/view/GestureDetector$OnGestureListener;
      //   610: aload_0
      //   611: getfield mCurrentDownEvent : Landroid/view/MotionEvent;
      //   614: aload_1
      //   615: fload #9
      //   617: fload #12
      //   619: invokeinterface onScroll : (Landroid/view/MotionEvent;Landroid/view/MotionEvent;FF)Z
      //   624: istore #11
      //   626: aload_0
      //   627: fload #8
      //   629: putfield mLastFocusX : F
      //   632: aload_0
      //   633: fload #10
      //   635: putfield mLastFocusY : F
      //   638: goto -> 1188
      //   641: aload_0
      //   642: iconst_0
      //   643: putfield mStillDown : Z
      //   646: aload_1
      //   647: invokestatic obtain : (Landroid/view/MotionEvent;)Landroid/view/MotionEvent;
      //   650: astore #13
      //   652: aload_0
      //   653: getfield mIsDoubleTapping : Z
      //   656: ifeq -> 676
      //   659: aload_0
      //   660: getfield mDoubleTapListener : Landroid/view/GestureDetector$OnDoubleTapListener;
      //   663: aload_1
      //   664: invokeinterface onDoubleTapEvent : (Landroid/view/MotionEvent;)Z
      //   669: iconst_0
      //   670: ior
      //   671: istore #11
      //   673: goto -> 846
      //   676: aload_0
      //   677: getfield mInLongPress : Z
      //   680: ifeq -> 699
      //   683: aload_0
      //   684: getfield mHandler : Landroid/os/Handler;
      //   687: iconst_3
      //   688: invokevirtual removeMessages : (I)V
      //   691: aload_0
      //   692: iconst_0
      //   693: putfield mInLongPress : Z
      //   696: goto -> 820
      //   699: aload_0
      //   700: getfield mAlwaysInTapRegion : Z
      //   703: ifeq -> 748
      //   706: aload_0
      //   707: getfield mListener : Landroid/view/GestureDetector$OnGestureListener;
      //   710: aload_1
      //   711: invokeinterface onSingleTapUp : (Landroid/view/MotionEvent;)Z
      //   716: istore #11
      //   718: aload_0
      //   719: getfield mDeferConfirmSingleTap : Z
      //   722: ifeq -> 745
      //   725: aload_0
      //   726: getfield mDoubleTapListener : Landroid/view/GestureDetector$OnDoubleTapListener;
      //   729: astore #14
      //   731: aload #14
      //   733: ifnull -> 745
      //   736: aload #14
      //   738: aload_1
      //   739: invokeinterface onSingleTapConfirmed : (Landroid/view/MotionEvent;)Z
      //   744: pop
      //   745: goto -> 846
      //   748: aload_0
      //   749: getfield mVelocityTracker : Landroid/view/VelocityTracker;
      //   752: astore #14
      //   754: aload_1
      //   755: iconst_0
      //   756: invokevirtual getPointerId : (I)I
      //   759: istore_2
      //   760: aload #14
      //   762: sipush #1000
      //   765: aload_0
      //   766: getfield mMaximumFlingVelocity : I
      //   769: i2f
      //   770: invokevirtual computeCurrentVelocity : (IF)V
      //   773: aload #14
      //   775: iload_2
      //   776: invokevirtual getYVelocity : (I)F
      //   779: fstore #9
      //   781: aload #14
      //   783: iload_2
      //   784: invokevirtual getXVelocity : (I)F
      //   787: fstore #8
      //   789: fload #9
      //   791: invokestatic abs : (F)F
      //   794: aload_0
      //   795: getfield mMinimumFlingVelocity : I
      //   798: i2f
      //   799: fcmpl
      //   800: ifgt -> 826
      //   803: fload #8
      //   805: invokestatic abs : (F)F
      //   808: aload_0
      //   809: getfield mMinimumFlingVelocity : I
      //   812: i2f
      //   813: fcmpl
      //   814: ifle -> 820
      //   817: goto -> 826
      //   820: iconst_0
      //   821: istore #11
      //   823: goto -> 846
      //   826: aload_0
      //   827: getfield mListener : Landroid/view/GestureDetector$OnGestureListener;
      //   830: aload_0
      //   831: getfield mCurrentDownEvent : Landroid/view/MotionEvent;
      //   834: aload_1
      //   835: fload #8
      //   837: fload #9
      //   839: invokeinterface onFling : (Landroid/view/MotionEvent;Landroid/view/MotionEvent;FF)Z
      //   844: istore #11
      //   846: aload_0
      //   847: getfield mPreviousUpEvent : Landroid/view/MotionEvent;
      //   850: astore_1
      //   851: aload_1
      //   852: ifnull -> 859
      //   855: aload_1
      //   856: invokevirtual recycle : ()V
      //   859: aload_0
      //   860: aload #13
      //   862: putfield mPreviousUpEvent : Landroid/view/MotionEvent;
      //   865: aload_0
      //   866: getfield mVelocityTracker : Landroid/view/VelocityTracker;
      //   869: astore_1
      //   870: aload_1
      //   871: ifnull -> 883
      //   874: aload_1
      //   875: invokevirtual recycle : ()V
      //   878: aload_0
      //   879: aconst_null
      //   880: putfield mVelocityTracker : Landroid/view/VelocityTracker;
      //   883: aload_0
      //   884: iconst_0
      //   885: putfield mIsDoubleTapping : Z
      //   888: aload_0
      //   889: iconst_0
      //   890: putfield mDeferConfirmSingleTap : Z
      //   893: aload_0
      //   894: getfield mHandler : Landroid/os/Handler;
      //   897: iconst_1
      //   898: invokevirtual removeMessages : (I)V
      //   901: aload_0
      //   902: getfield mHandler : Landroid/os/Handler;
      //   905: iconst_2
      //   906: invokevirtual removeMessages : (I)V
      //   909: iload #11
      //   911: istore #4
      //   913: iload #4
      //   915: istore #11
      //   917: goto -> 1188
      //   920: aload_0
      //   921: getfield mDoubleTapListener : Landroid/view/GestureDetector$OnDoubleTapListener;
      //   924: ifnull -> 1037
      //   927: aload_0
      //   928: getfield mHandler : Landroid/os/Handler;
      //   931: iconst_3
      //   932: invokevirtual hasMessages : (I)Z
      //   935: istore #11
      //   937: iload #11
      //   939: ifeq -> 950
      //   942: aload_0
      //   943: getfield mHandler : Landroid/os/Handler;
      //   946: iconst_3
      //   947: invokevirtual removeMessages : (I)V
      //   950: aload_0
      //   951: getfield mCurrentDownEvent : Landroid/view/MotionEvent;
      //   954: astore #13
      //   956: aload #13
      //   958: ifnull -> 1024
      //   961: aload_0
      //   962: getfield mPreviousUpEvent : Landroid/view/MotionEvent;
      //   965: astore #14
      //   967: aload #14
      //   969: ifnull -> 1024
      //   972: iload #11
      //   974: ifeq -> 1024
      //   977: aload_0
      //   978: aload #13
      //   980: aload #14
      //   982: aload_1
      //   983: invokespecial isConsideredDoubleTap : (Landroid/view/MotionEvent;Landroid/view/MotionEvent;Landroid/view/MotionEvent;)Z
      //   986: ifeq -> 1024
      //   989: aload_0
      //   990: iconst_1
      //   991: putfield mIsDoubleTapping : Z
      //   994: aload_0
      //   995: getfield mDoubleTapListener : Landroid/view/GestureDetector$OnDoubleTapListener;
      //   998: aload_0
      //   999: getfield mCurrentDownEvent : Landroid/view/MotionEvent;
      //   1002: invokeinterface onDoubleTap : (Landroid/view/MotionEvent;)Z
      //   1007: iconst_0
      //   1008: ior
      //   1009: aload_0
      //   1010: getfield mDoubleTapListener : Landroid/view/GestureDetector$OnDoubleTapListener;
      //   1013: aload_1
      //   1014: invokeinterface onDoubleTapEvent : (Landroid/view/MotionEvent;)Z
      //   1019: ior
      //   1020: istore_2
      //   1021: goto -> 1039
      //   1024: aload_0
      //   1025: getfield mHandler : Landroid/os/Handler;
      //   1028: iconst_3
      //   1029: getstatic android/support/v4/view/GestureDetectorCompat$GestureDetectorCompatImplBase.DOUBLE_TAP_TIMEOUT : I
      //   1032: i2l
      //   1033: invokevirtual sendEmptyMessageDelayed : (IJ)Z
      //   1036: pop
      //   1037: iconst_0
      //   1038: istore_2
      //   1039: aload_0
      //   1040: fload #8
      //   1042: putfield mLastFocusX : F
      //   1045: aload_0
      //   1046: fload #8
      //   1048: putfield mDownFocusX : F
      //   1051: aload_0
      //   1052: fload #10
      //   1054: putfield mLastFocusY : F
      //   1057: aload_0
      //   1058: fload #10
      //   1060: putfield mDownFocusY : F
      //   1063: aload_0
      //   1064: getfield mCurrentDownEvent : Landroid/view/MotionEvent;
      //   1067: astore #13
      //   1069: aload #13
      //   1071: ifnull -> 1079
      //   1074: aload #13
      //   1076: invokevirtual recycle : ()V
      //   1079: aload_0
      //   1080: aload_1
      //   1081: invokestatic obtain : (Landroid/view/MotionEvent;)Landroid/view/MotionEvent;
      //   1084: putfield mCurrentDownEvent : Landroid/view/MotionEvent;
      //   1087: aload_0
      //   1088: iconst_1
      //   1089: putfield mAlwaysInTapRegion : Z
      //   1092: aload_0
      //   1093: iconst_1
      //   1094: putfield mAlwaysInBiggerTapRegion : Z
      //   1097: aload_0
      //   1098: iconst_1
      //   1099: putfield mStillDown : Z
      //   1102: aload_0
      //   1103: iconst_0
      //   1104: putfield mInLongPress : Z
      //   1107: aload_0
      //   1108: iconst_0
      //   1109: putfield mDeferConfirmSingleTap : Z
      //   1112: aload_0
      //   1113: getfield mIsLongpressEnabled : Z
      //   1116: ifeq -> 1153
      //   1119: aload_0
      //   1120: getfield mHandler : Landroid/os/Handler;
      //   1123: iconst_2
      //   1124: invokevirtual removeMessages : (I)V
      //   1127: aload_0
      //   1128: getfield mHandler : Landroid/os/Handler;
      //   1131: iconst_2
      //   1132: aload_0
      //   1133: getfield mCurrentDownEvent : Landroid/view/MotionEvent;
      //   1136: invokevirtual getDownTime : ()J
      //   1139: getstatic android/support/v4/view/GestureDetectorCompat$GestureDetectorCompatImplBase.TAP_TIMEOUT : I
      //   1142: i2l
      //   1143: ladd
      //   1144: getstatic android/support/v4/view/GestureDetectorCompat$GestureDetectorCompatImplBase.LONGPRESS_TIMEOUT : I
      //   1147: i2l
      //   1148: ladd
      //   1149: invokevirtual sendEmptyMessageAtTime : (IJ)Z
      //   1152: pop
      //   1153: aload_0
      //   1154: getfield mHandler : Landroid/os/Handler;
      //   1157: iconst_1
      //   1158: aload_0
      //   1159: getfield mCurrentDownEvent : Landroid/view/MotionEvent;
      //   1162: invokevirtual getDownTime : ()J
      //   1165: getstatic android/support/v4/view/GestureDetectorCompat$GestureDetectorCompatImplBase.TAP_TIMEOUT : I
      //   1168: i2l
      //   1169: ladd
      //   1170: invokevirtual sendEmptyMessageAtTime : (IJ)Z
      //   1173: pop
      //   1174: iload_2
      //   1175: aload_0
      //   1176: getfield mListener : Landroid/view/GestureDetector$OnGestureListener;
      //   1179: aload_1
      //   1180: invokeinterface onDown : (Landroid/view/MotionEvent;)Z
      //   1185: ior
      //   1186: istore #11
      //   1188: iload #11
      //   1190: ireturn
    }
    
    public void setIsLongpressEnabled(boolean param1Boolean) {
      this.mIsLongpressEnabled = param1Boolean;
    }
    
    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener param1OnDoubleTapListener) {
      this.mDoubleTapListener = param1OnDoubleTapListener;
    }
    
    private class GestureHandler extends Handler {
      GestureHandler() {}
      
      GestureHandler(Handler param2Handler) {
        super(param2Handler.getLooper());
      }
      
      public void handleMessage(Message param2Message) {
        int i = param2Message.what;
        if (i != 1) {
          if (i != 2) {
            if (i == 3) {
              if (GestureDetectorCompat.GestureDetectorCompatImplBase.this.mDoubleTapListener != null)
                if (!GestureDetectorCompat.GestureDetectorCompatImplBase.this.mStillDown) {
                  GestureDetectorCompat.GestureDetectorCompatImplBase.this.mDoubleTapListener.onSingleTapConfirmed(GestureDetectorCompat.GestureDetectorCompatImplBase.this.mCurrentDownEvent);
                } else {
                  GestureDetectorCompat.GestureDetectorCompatImplBase.this.mDeferConfirmSingleTap = true;
                }  
            } else {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("Unknown message ");
              stringBuilder.append(param2Message);
              throw new RuntimeException(stringBuilder.toString());
            } 
          } else {
            GestureDetectorCompat.GestureDetectorCompatImplBase.this.dispatchLongPress();
          } 
        } else {
          GestureDetectorCompat.GestureDetectorCompatImplBase.this.mListener.onShowPress(GestureDetectorCompat.GestureDetectorCompatImplBase.this.mCurrentDownEvent);
        } 
      }
    }
  }
  
  private class GestureHandler extends Handler {
    GestureHandler() {}
    
    GestureHandler(Handler param1Handler) {
      super(param1Handler.getLooper());
    }
    
    public void handleMessage(Message param1Message) {
      int i = param1Message.what;
      if (i != 1) {
        if (i != 2) {
          if (i == 3) {
            if (this.this$0.mDoubleTapListener != null)
              if (!this.this$0.mStillDown) {
                this.this$0.mDoubleTapListener.onSingleTapConfirmed(this.this$0.mCurrentDownEvent);
              } else {
                this.this$0.mDeferConfirmSingleTap = true;
              }  
          } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown message ");
            stringBuilder.append(param1Message);
            throw new RuntimeException(stringBuilder.toString());
          } 
        } else {
          this.this$0.dispatchLongPress();
        } 
      } else {
        this.this$0.mListener.onShowPress(this.this$0.mCurrentDownEvent);
      } 
    }
  }
  
  static class GestureDetectorCompatImplJellybeanMr2 implements GestureDetectorCompatImpl {
    private final GestureDetector mDetector;
    
    GestureDetectorCompatImplJellybeanMr2(Context param1Context, GestureDetector.OnGestureListener param1OnGestureListener, Handler param1Handler) {
      this.mDetector = new GestureDetector(param1Context, param1OnGestureListener, param1Handler);
    }
    
    public boolean isLongpressEnabled() {
      return this.mDetector.isLongpressEnabled();
    }
    
    public boolean onTouchEvent(MotionEvent param1MotionEvent) {
      return this.mDetector.onTouchEvent(param1MotionEvent);
    }
    
    public void setIsLongpressEnabled(boolean param1Boolean) {
      this.mDetector.setIsLongpressEnabled(param1Boolean);
    }
    
    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener param1OnDoubleTapListener) {
      this.mDetector.setOnDoubleTapListener(param1OnDoubleTapListener);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\view\GestureDetectorCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */