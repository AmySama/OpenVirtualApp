package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.xmlpull.v1.XmlPullParser;

public abstract class Visibility extends Transition {
  public static final int MODE_IN = 1;
  
  public static final int MODE_OUT = 2;
  
  private static final String PROPNAME_PARENT = "android:visibility:parent";
  
  private static final String PROPNAME_SCREEN_LOCATION = "android:visibility:screenLocation";
  
  static final String PROPNAME_VISIBILITY = "android:visibility:visibility";
  
  private static final String[] sTransitionProperties = new String[] { "android:visibility:visibility", "android:visibility:parent" };
  
  private int mMode = 3;
  
  public Visibility() {}
  
  public Visibility(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, Styleable.VISIBILITY_TRANSITION);
    int i = TypedArrayUtils.getNamedInt(typedArray, (XmlPullParser)paramAttributeSet, "transitionVisibilityMode", 0, 0);
    typedArray.recycle();
    if (i != 0)
      setMode(i); 
  }
  
  private void captureValues(TransitionValues paramTransitionValues) {
    int i = paramTransitionValues.view.getVisibility();
    paramTransitionValues.values.put("android:visibility:visibility", Integer.valueOf(i));
    paramTransitionValues.values.put("android:visibility:parent", paramTransitionValues.view.getParent());
    int[] arrayOfInt = new int[2];
    paramTransitionValues.view.getLocationOnScreen(arrayOfInt);
    paramTransitionValues.values.put("android:visibility:screenLocation", arrayOfInt);
  }
  
  private VisibilityInfo getVisibilityChangeInfo(TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2) {
    VisibilityInfo visibilityInfo = new VisibilityInfo();
    visibilityInfo.mVisibilityChange = false;
    visibilityInfo.mFadeIn = false;
    if (paramTransitionValues1 != null && paramTransitionValues1.values.containsKey("android:visibility:visibility")) {
      visibilityInfo.mStartVisibility = ((Integer)paramTransitionValues1.values.get("android:visibility:visibility")).intValue();
      visibilityInfo.mStartParent = (ViewGroup)paramTransitionValues1.values.get("android:visibility:parent");
    } else {
      visibilityInfo.mStartVisibility = -1;
      visibilityInfo.mStartParent = null;
    } 
    if (paramTransitionValues2 != null && paramTransitionValues2.values.containsKey("android:visibility:visibility")) {
      visibilityInfo.mEndVisibility = ((Integer)paramTransitionValues2.values.get("android:visibility:visibility")).intValue();
      visibilityInfo.mEndParent = (ViewGroup)paramTransitionValues2.values.get("android:visibility:parent");
    } else {
      visibilityInfo.mEndVisibility = -1;
      visibilityInfo.mEndParent = null;
    } 
    if (paramTransitionValues1 != null && paramTransitionValues2 != null) {
      if (visibilityInfo.mStartVisibility == visibilityInfo.mEndVisibility && visibilityInfo.mStartParent == visibilityInfo.mEndParent)
        return visibilityInfo; 
      if (visibilityInfo.mStartVisibility != visibilityInfo.mEndVisibility) {
        if (visibilityInfo.mStartVisibility == 0) {
          visibilityInfo.mFadeIn = false;
          visibilityInfo.mVisibilityChange = true;
        } else if (visibilityInfo.mEndVisibility == 0) {
          visibilityInfo.mFadeIn = true;
          visibilityInfo.mVisibilityChange = true;
        } 
      } else if (visibilityInfo.mEndParent == null) {
        visibilityInfo.mFadeIn = false;
        visibilityInfo.mVisibilityChange = true;
      } else if (visibilityInfo.mStartParent == null) {
        visibilityInfo.mFadeIn = true;
        visibilityInfo.mVisibilityChange = true;
      } 
    } else if (paramTransitionValues1 == null && visibilityInfo.mEndVisibility == 0) {
      visibilityInfo.mFadeIn = true;
      visibilityInfo.mVisibilityChange = true;
    } else if (paramTransitionValues2 == null && visibilityInfo.mStartVisibility == 0) {
      visibilityInfo.mFadeIn = false;
      visibilityInfo.mVisibilityChange = true;
    } 
    return visibilityInfo;
  }
  
  public void captureEndValues(TransitionValues paramTransitionValues) {
    captureValues(paramTransitionValues);
  }
  
  public void captureStartValues(TransitionValues paramTransitionValues) {
    captureValues(paramTransitionValues);
  }
  
  public Animator createAnimator(ViewGroup paramViewGroup, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2) {
    VisibilityInfo visibilityInfo = getVisibilityChangeInfo(paramTransitionValues1, paramTransitionValues2);
    return (visibilityInfo.mVisibilityChange && (visibilityInfo.mStartParent != null || visibilityInfo.mEndParent != null)) ? (visibilityInfo.mFadeIn ? onAppear(paramViewGroup, paramTransitionValues1, visibilityInfo.mStartVisibility, paramTransitionValues2, visibilityInfo.mEndVisibility) : onDisappear(paramViewGroup, paramTransitionValues1, visibilityInfo.mStartVisibility, paramTransitionValues2, visibilityInfo.mEndVisibility)) : null;
  }
  
  public int getMode() {
    return this.mMode;
  }
  
  public String[] getTransitionProperties() {
    return sTransitionProperties;
  }
  
  public boolean isTransitionRequired(TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2) {
    boolean bool = false;
    if (paramTransitionValues1 == null && paramTransitionValues2 == null)
      return false; 
    if (paramTransitionValues1 != null && paramTransitionValues2 != null && paramTransitionValues2.values.containsKey("android:visibility:visibility") != paramTransitionValues1.values.containsKey("android:visibility:visibility"))
      return false; 
    VisibilityInfo visibilityInfo = getVisibilityChangeInfo(paramTransitionValues1, paramTransitionValues2);
    null = bool;
    if (visibilityInfo.mVisibilityChange) {
      if (visibilityInfo.mStartVisibility != 0) {
        null = bool;
        return (visibilityInfo.mEndVisibility == 0) ? true : null;
      } 
    } else {
      return null;
    } 
    return true;
  }
  
  public boolean isVisible(TransitionValues paramTransitionValues) {
    boolean bool1 = false;
    if (paramTransitionValues == null)
      return false; 
    int i = ((Integer)paramTransitionValues.values.get("android:visibility:visibility")).intValue();
    View view = (View)paramTransitionValues.values.get("android:visibility:parent");
    boolean bool2 = bool1;
    if (i == 0) {
      bool2 = bool1;
      if (view != null)
        bool2 = true; 
    } 
    return bool2;
  }
  
  public Animator onAppear(ViewGroup paramViewGroup, TransitionValues paramTransitionValues1, int paramInt1, TransitionValues paramTransitionValues2, int paramInt2) {
    if ((this.mMode & 0x1) != 1 || paramTransitionValues2 == null)
      return null; 
    if (paramTransitionValues1 == null) {
      View view = (View)paramTransitionValues2.view.getParent();
      if ((getVisibilityChangeInfo(getMatchedTransitionValues(view, false), getTransitionValues(view, false))).mVisibilityChange)
        return null; 
    } 
    return onAppear(paramViewGroup, paramTransitionValues2.view, paramTransitionValues1, paramTransitionValues2);
  }
  
  public Animator onAppear(ViewGroup paramViewGroup, View paramView, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2) {
    return null;
  }
  
  public Animator onDisappear(ViewGroup paramViewGroup, TransitionValues paramTransitionValues1, int paramInt1, TransitionValues paramTransitionValues2, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mMode : I
    //   4: iconst_2
    //   5: iand
    //   6: iconst_2
    //   7: if_icmpeq -> 12
    //   10: aconst_null
    //   11: areturn
    //   12: aload_2
    //   13: ifnull -> 25
    //   16: aload_2
    //   17: getfield view : Landroid/view/View;
    //   20: astore #6
    //   22: goto -> 28
    //   25: aconst_null
    //   26: astore #6
    //   28: aload #4
    //   30: ifnull -> 43
    //   33: aload #4
    //   35: getfield view : Landroid/view/View;
    //   38: astore #7
    //   40: goto -> 46
    //   43: aconst_null
    //   44: astore #7
    //   46: aload #7
    //   48: ifnull -> 88
    //   51: aload #7
    //   53: invokevirtual getParent : ()Landroid/view/ViewParent;
    //   56: ifnonnull -> 62
    //   59: goto -> 88
    //   62: iload #5
    //   64: iconst_4
    //   65: if_icmpne -> 71
    //   68: goto -> 82
    //   71: aload #6
    //   73: astore #8
    //   75: aload #6
    //   77: aload #7
    //   79: if_acmpne -> 97
    //   82: aconst_null
    //   83: astore #8
    //   85: goto -> 235
    //   88: aload #7
    //   90: ifnull -> 103
    //   93: aload #7
    //   95: astore #8
    //   97: aconst_null
    //   98: astore #7
    //   100: goto -> 235
    //   103: aload #6
    //   105: ifnull -> 228
    //   108: aload #6
    //   110: invokevirtual getParent : ()Landroid/view/ViewParent;
    //   113: ifnonnull -> 123
    //   116: aload #6
    //   118: astore #8
    //   120: goto -> 97
    //   123: aload #6
    //   125: invokevirtual getParent : ()Landroid/view/ViewParent;
    //   128: instanceof android/view/View
    //   131: ifeq -> 228
    //   134: aload #6
    //   136: invokevirtual getParent : ()Landroid/view/ViewParent;
    //   139: checkcast android/view/View
    //   142: astore #8
    //   144: aload_0
    //   145: aload_0
    //   146: aload #8
    //   148: iconst_1
    //   149: invokevirtual getTransitionValues : (Landroid/view/View;Z)Landroid/support/transition/TransitionValues;
    //   152: aload_0
    //   153: aload #8
    //   155: iconst_1
    //   156: invokevirtual getMatchedTransitionValues : (Landroid/view/View;Z)Landroid/support/transition/TransitionValues;
    //   159: invokespecial getVisibilityChangeInfo : (Landroid/support/transition/TransitionValues;Landroid/support/transition/TransitionValues;)Landroid/support/transition/Visibility$VisibilityInfo;
    //   162: getfield mVisibilityChange : Z
    //   165: ifne -> 181
    //   168: aload_1
    //   169: aload #6
    //   171: aload #8
    //   173: invokestatic copyViewImage : (Landroid/view/ViewGroup;Landroid/view/View;Landroid/view/View;)Landroid/view/View;
    //   176: astore #8
    //   178: goto -> 97
    //   181: aload #8
    //   183: invokevirtual getParent : ()Landroid/view/ViewParent;
    //   186: ifnonnull -> 222
    //   189: aload #8
    //   191: invokevirtual getId : ()I
    //   194: istore_3
    //   195: iload_3
    //   196: iconst_m1
    //   197: if_icmpeq -> 222
    //   200: aload_1
    //   201: iload_3
    //   202: invokevirtual findViewById : (I)Landroid/view/View;
    //   205: ifnull -> 222
    //   208: aload_0
    //   209: getfield mCanRemoveViews : Z
    //   212: ifeq -> 222
    //   215: aload #6
    //   217: astore #8
    //   219: goto -> 97
    //   222: aconst_null
    //   223: astore #8
    //   225: goto -> 97
    //   228: aconst_null
    //   229: astore #8
    //   231: aload #8
    //   233: astore #7
    //   235: aload #8
    //   237: ifnull -> 377
    //   240: aload_2
    //   241: ifnull -> 377
    //   244: aload_2
    //   245: getfield values : Ljava/util/Map;
    //   248: ldc 'android:visibility:screenLocation'
    //   250: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   255: checkcast [I
    //   258: astore #7
    //   260: aload #7
    //   262: iconst_0
    //   263: iaload
    //   264: istore #5
    //   266: aload #7
    //   268: iconst_1
    //   269: iaload
    //   270: istore_3
    //   271: iconst_2
    //   272: newarray int
    //   274: astore #7
    //   276: aload_1
    //   277: aload #7
    //   279: invokevirtual getLocationOnScreen : ([I)V
    //   282: aload #8
    //   284: iload #5
    //   286: aload #7
    //   288: iconst_0
    //   289: iaload
    //   290: isub
    //   291: aload #8
    //   293: invokevirtual getLeft : ()I
    //   296: isub
    //   297: invokevirtual offsetLeftAndRight : (I)V
    //   300: aload #8
    //   302: iload_3
    //   303: aload #7
    //   305: iconst_1
    //   306: iaload
    //   307: isub
    //   308: aload #8
    //   310: invokevirtual getTop : ()I
    //   313: isub
    //   314: invokevirtual offsetTopAndBottom : (I)V
    //   317: aload_1
    //   318: invokestatic getOverlay : (Landroid/view/ViewGroup;)Landroid/support/transition/ViewGroupOverlayImpl;
    //   321: astore #7
    //   323: aload #7
    //   325: aload #8
    //   327: invokeinterface add : (Landroid/view/View;)V
    //   332: aload_0
    //   333: aload_1
    //   334: aload #8
    //   336: aload_2
    //   337: aload #4
    //   339: invokevirtual onDisappear : (Landroid/view/ViewGroup;Landroid/view/View;Landroid/support/transition/TransitionValues;Landroid/support/transition/TransitionValues;)Landroid/animation/Animator;
    //   342: astore_1
    //   343: aload_1
    //   344: ifnonnull -> 359
    //   347: aload #7
    //   349: aload #8
    //   351: invokeinterface remove : (Landroid/view/View;)V
    //   356: goto -> 375
    //   359: aload_1
    //   360: new android/support/transition/Visibility$1
    //   363: dup
    //   364: aload_0
    //   365: aload #7
    //   367: aload #8
    //   369: invokespecial <init> : (Landroid/support/transition/Visibility;Landroid/support/transition/ViewGroupOverlayImpl;Landroid/view/View;)V
    //   372: invokevirtual addListener : (Landroid/animation/Animator$AnimatorListener;)V
    //   375: aload_1
    //   376: areturn
    //   377: aload #7
    //   379: ifnull -> 449
    //   382: aload #7
    //   384: invokevirtual getVisibility : ()I
    //   387: istore_3
    //   388: aload #7
    //   390: iconst_0
    //   391: invokestatic setTransitionVisibility : (Landroid/view/View;I)V
    //   394: aload_0
    //   395: aload_1
    //   396: aload #7
    //   398: aload_2
    //   399: aload #4
    //   401: invokevirtual onDisappear : (Landroid/view/ViewGroup;Landroid/view/View;Landroid/support/transition/TransitionValues;Landroid/support/transition/TransitionValues;)Landroid/animation/Animator;
    //   404: astore_1
    //   405: aload_1
    //   406: ifnull -> 441
    //   409: new android/support/transition/Visibility$DisappearListener
    //   412: dup
    //   413: aload #7
    //   415: iload #5
    //   417: iconst_1
    //   418: invokespecial <init> : (Landroid/view/View;IZ)V
    //   421: astore_2
    //   422: aload_1
    //   423: aload_2
    //   424: invokevirtual addListener : (Landroid/animation/Animator$AnimatorListener;)V
    //   427: aload_1
    //   428: aload_2
    //   429: invokestatic addPauseListener : (Landroid/animation/Animator;Landroid/animation/AnimatorListenerAdapter;)V
    //   432: aload_0
    //   433: aload_2
    //   434: invokevirtual addListener : (Landroid/support/transition/Transition$TransitionListener;)Landroid/support/transition/Transition;
    //   437: pop
    //   438: goto -> 447
    //   441: aload #7
    //   443: iload_3
    //   444: invokestatic setTransitionVisibility : (Landroid/view/View;I)V
    //   447: aload_1
    //   448: areturn
    //   449: aconst_null
    //   450: areturn
  }
  
  public Animator onDisappear(ViewGroup paramViewGroup, View paramView, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2) {
    return null;
  }
  
  public void setMode(int paramInt) {
    if ((paramInt & 0xFFFFFFFC) == 0) {
      this.mMode = paramInt;
      return;
    } 
    throw new IllegalArgumentException("Only MODE_IN and MODE_OUT flags are allowed");
  }
  
  private static class DisappearListener extends AnimatorListenerAdapter implements Transition.TransitionListener, AnimatorUtilsApi14.AnimatorPauseListenerCompat {
    boolean mCanceled = false;
    
    private final int mFinalVisibility;
    
    private boolean mLayoutSuppressed;
    
    private final ViewGroup mParent;
    
    private final boolean mSuppressLayout;
    
    private final View mView;
    
    DisappearListener(View param1View, int param1Int, boolean param1Boolean) {
      this.mView = param1View;
      this.mFinalVisibility = param1Int;
      this.mParent = (ViewGroup)param1View.getParent();
      this.mSuppressLayout = param1Boolean;
      suppressLayout(true);
    }
    
    private void hideViewWhenNotCanceled() {
      if (!this.mCanceled) {
        ViewUtils.setTransitionVisibility(this.mView, this.mFinalVisibility);
        ViewGroup viewGroup = this.mParent;
        if (viewGroup != null)
          viewGroup.invalidate(); 
      } 
      suppressLayout(false);
    }
    
    private void suppressLayout(boolean param1Boolean) {
      if (this.mSuppressLayout && this.mLayoutSuppressed != param1Boolean) {
        ViewGroup viewGroup = this.mParent;
        if (viewGroup != null) {
          this.mLayoutSuppressed = param1Boolean;
          ViewGroupUtils.suppressLayout(viewGroup, param1Boolean);
        } 
      } 
    }
    
    public void onAnimationCancel(Animator param1Animator) {
      this.mCanceled = true;
    }
    
    public void onAnimationEnd(Animator param1Animator) {
      hideViewWhenNotCanceled();
    }
    
    public void onAnimationPause(Animator param1Animator) {
      if (!this.mCanceled)
        ViewUtils.setTransitionVisibility(this.mView, this.mFinalVisibility); 
    }
    
    public void onAnimationRepeat(Animator param1Animator) {}
    
    public void onAnimationResume(Animator param1Animator) {
      if (!this.mCanceled)
        ViewUtils.setTransitionVisibility(this.mView, 0); 
    }
    
    public void onAnimationStart(Animator param1Animator) {}
    
    public void onTransitionCancel(Transition param1Transition) {}
    
    public void onTransitionEnd(Transition param1Transition) {
      hideViewWhenNotCanceled();
      param1Transition.removeListener(this);
    }
    
    public void onTransitionPause(Transition param1Transition) {
      suppressLayout(false);
    }
    
    public void onTransitionResume(Transition param1Transition) {
      suppressLayout(true);
    }
    
    public void onTransitionStart(Transition param1Transition) {}
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface Mode {}
  
  private static class VisibilityInfo {
    ViewGroup mEndParent;
    
    int mEndVisibility;
    
    boolean mFadeIn;
    
    ViewGroup mStartParent;
    
    int mStartVisibility;
    
    boolean mVisibilityChange;
    
    private VisibilityInfo() {}
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\Visibility.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */