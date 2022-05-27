package android.support.v4.app;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.arch.lifecycle.ViewModelStore;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.support.v4.util.ArraySet;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.LogWriter;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

final class FragmentManagerImpl extends FragmentManager implements LayoutInflater.Factory2 {
  static final Interpolator ACCELERATE_CUBIC;
  
  static final Interpolator ACCELERATE_QUINT;
  
  static final int ANIM_DUR = 220;
  
  public static final int ANIM_STYLE_CLOSE_ENTER = 3;
  
  public static final int ANIM_STYLE_CLOSE_EXIT = 4;
  
  public static final int ANIM_STYLE_FADE_ENTER = 5;
  
  public static final int ANIM_STYLE_FADE_EXIT = 6;
  
  public static final int ANIM_STYLE_OPEN_ENTER = 1;
  
  public static final int ANIM_STYLE_OPEN_EXIT = 2;
  
  static boolean DEBUG = false;
  
  static final Interpolator DECELERATE_CUBIC;
  
  static final Interpolator DECELERATE_QUINT = (Interpolator)new DecelerateInterpolator(2.5F);
  
  static final String TAG = "FragmentManager";
  
  static final String TARGET_REQUEST_CODE_STATE_TAG = "android:target_req_state";
  
  static final String TARGET_STATE_TAG = "android:target_state";
  
  static final String USER_VISIBLE_HINT_TAG = "android:user_visible_hint";
  
  static final String VIEW_STATE_TAG = "android:view_state";
  
  static Field sAnimationListenerField;
  
  SparseArray<Fragment> mActive;
  
  final ArrayList<Fragment> mAdded = new ArrayList<Fragment>();
  
  ArrayList<Integer> mAvailBackStackIndices;
  
  ArrayList<BackStackRecord> mBackStack;
  
  ArrayList<FragmentManager.OnBackStackChangedListener> mBackStackChangeListeners;
  
  ArrayList<BackStackRecord> mBackStackIndices;
  
  FragmentContainer mContainer;
  
  ArrayList<Fragment> mCreatedMenus;
  
  int mCurState = 0;
  
  boolean mDestroyed;
  
  Runnable mExecCommit = new Runnable() {
      public void run() {
        FragmentManagerImpl.this.execPendingActions();
      }
    };
  
  boolean mExecutingActions;
  
  boolean mHavePendingDeferredStart;
  
  FragmentHostCallback mHost;
  
  private final CopyOnWriteArrayList<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>> mLifecycleCallbacks = new CopyOnWriteArrayList<Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean>>();
  
  boolean mNeedMenuInvalidate;
  
  int mNextFragmentIndex = 0;
  
  String mNoTransactionsBecause;
  
  Fragment mParent;
  
  ArrayList<OpGenerator> mPendingActions;
  
  ArrayList<StartEnterTransitionListener> mPostponedTransactions;
  
  Fragment mPrimaryNav;
  
  FragmentManagerNonConfig mSavedNonConfig;
  
  SparseArray<Parcelable> mStateArray = null;
  
  Bundle mStateBundle = null;
  
  boolean mStateSaved;
  
  boolean mStopped;
  
  ArrayList<Fragment> mTmpAddedFragments;
  
  ArrayList<Boolean> mTmpIsPop;
  
  ArrayList<BackStackRecord> mTmpRecords;
  
  static {
    DECELERATE_CUBIC = (Interpolator)new DecelerateInterpolator(1.5F);
    ACCELERATE_QUINT = (Interpolator)new AccelerateInterpolator(2.5F);
    ACCELERATE_CUBIC = (Interpolator)new AccelerateInterpolator(1.5F);
  }
  
  private void addAddedFragments(ArraySet<Fragment> paramArraySet) {
    int i = this.mCurState;
    if (i < 1)
      return; 
    int j = Math.min(i, 4);
    int k = this.mAdded.size();
    for (i = 0; i < k; i++) {
      Fragment fragment = this.mAdded.get(i);
      if (fragment.mState < j) {
        moveToState(fragment, j, fragment.getNextAnim(), fragment.getNextTransition(), false);
        if (fragment.mView != null && !fragment.mHidden && fragment.mIsNewlyAdded)
          paramArraySet.add(fragment); 
      } 
    } 
  }
  
  private void animateRemoveFragment(final Fragment fragment, AnimationOrAnimator paramAnimationOrAnimator, int paramInt) {
    final View viewToAnimate = fragment.mView;
    final ViewGroup container = fragment.mContainer;
    viewGroup.startViewTransition(view);
    fragment.setStateAfterAnimating(paramInt);
    if (paramAnimationOrAnimator.animation != null) {
      EndViewTransitionAnimator endViewTransitionAnimator = new EndViewTransitionAnimator(paramAnimationOrAnimator.animation, viewGroup, view);
      fragment.setAnimatingAway(fragment.mView);
      endViewTransitionAnimator.setAnimationListener(new AnimationListenerWrapper(getAnimationListener((Animation)endViewTransitionAnimator)) {
            public void onAnimationEnd(Animation param1Animation) {
              super.onAnimationEnd(param1Animation);
              container.post(new Runnable() {
                    public void run() {
                      if (fragment.getAnimatingAway() != null) {
                        fragment.setAnimatingAway(null);
                        FragmentManagerImpl.this.moveToState(fragment, fragment.getStateAfterAnimating(), 0, 0, false);
                      } 
                    }
                  });
            }
          });
      setHWLayerAnimListenerIfAlpha(view, paramAnimationOrAnimator);
      fragment.mView.startAnimation((Animation)endViewTransitionAnimator);
    } else {
      Animator animator = paramAnimationOrAnimator.animator;
      fragment.setAnimator(paramAnimationOrAnimator.animator);
      animator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator param1Animator) {
              container.endViewTransition(viewToAnimate);
              param1Animator = fragment.getAnimator();
              fragment.setAnimator(null);
              if (param1Animator != null && container.indexOfChild(viewToAnimate) < 0) {
                FragmentManagerImpl fragmentManagerImpl = FragmentManagerImpl.this;
                Fragment fragment = fragment;
                fragmentManagerImpl.moveToState(fragment, fragment.getStateAfterAnimating(), 0, 0, false);
              } 
            }
          });
      animator.setTarget(fragment.mView);
      setHWLayerAnimListenerIfAlpha(fragment.mView, paramAnimationOrAnimator);
      animator.start();
    } 
  }
  
  private void burpActive() {
    SparseArray<Fragment> sparseArray = this.mActive;
    if (sparseArray != null)
      for (int i = sparseArray.size() - 1; i >= 0; i--) {
        if (this.mActive.valueAt(i) == null) {
          sparseArray = this.mActive;
          sparseArray.delete(sparseArray.keyAt(i));
        } 
      }  
  }
  
  private void checkStateLoss() {
    if (!isStateSaved()) {
      if (this.mNoTransactionsBecause == null)
        return; 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Can not perform this action inside of ");
      stringBuilder.append(this.mNoTransactionsBecause);
      throw new IllegalStateException(stringBuilder.toString());
    } 
    throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
  }
  
  private void cleanupExec() {
    this.mExecutingActions = false;
    this.mTmpIsPop.clear();
    this.mTmpRecords.clear();
  }
  
  private void completeExecute(BackStackRecord paramBackStackRecord, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
    if (paramBoolean1) {
      paramBackStackRecord.executePopOps(paramBoolean3);
    } else {
      paramBackStackRecord.executeOps();
    } 
    ArrayList<BackStackRecord> arrayList = new ArrayList(1);
    ArrayList<Boolean> arrayList1 = new ArrayList(1);
    arrayList.add(paramBackStackRecord);
    arrayList1.add(Boolean.valueOf(paramBoolean1));
    if (paramBoolean2)
      FragmentTransition.startTransitions(this, arrayList, arrayList1, 0, 1, true); 
    if (paramBoolean3)
      moveToState(this.mCurState, true); 
    SparseArray<Fragment> sparseArray = this.mActive;
    if (sparseArray != null) {
      int i = sparseArray.size();
      for (byte b = 0; b < i; b++) {
        Fragment fragment = (Fragment)this.mActive.valueAt(b);
        if (fragment != null && fragment.mView != null && fragment.mIsNewlyAdded && paramBackStackRecord.interactsWith(fragment.mContainerId)) {
          if (fragment.mPostponedAlpha > 0.0F)
            fragment.mView.setAlpha(fragment.mPostponedAlpha); 
          if (paramBoolean3) {
            fragment.mPostponedAlpha = 0.0F;
          } else {
            fragment.mPostponedAlpha = -1.0F;
            fragment.mIsNewlyAdded = false;
          } 
        } 
      } 
    } 
  }
  
  private void dispatchStateChange(int paramInt) {
    try {
      this.mExecutingActions = true;
      moveToState(paramInt, false);
      this.mExecutingActions = false;
      return;
    } finally {
      this.mExecutingActions = false;
    } 
  }
  
  private void endAnimatingAwayFragments() {
    int i;
    SparseArray<Fragment> sparseArray = this.mActive;
    byte b = 0;
    if (sparseArray == null) {
      i = 0;
    } else {
      i = sparseArray.size();
    } 
    while (b < i) {
      Fragment fragment = (Fragment)this.mActive.valueAt(b);
      if (fragment != null)
        if (fragment.getAnimatingAway() != null) {
          int j = fragment.getStateAfterAnimating();
          View view = fragment.getAnimatingAway();
          Animation animation = view.getAnimation();
          if (animation != null) {
            animation.cancel();
            view.clearAnimation();
          } 
          fragment.setAnimatingAway(null);
          moveToState(fragment, j, 0, 0, false);
        } else if (fragment.getAnimator() != null) {
          fragment.getAnimator().end();
        }  
      b++;
    } 
  }
  
  private void ensureExecReady(boolean paramBoolean) {
    if (!this.mExecutingActions) {
      if (this.mHost != null) {
        if (Looper.myLooper() == this.mHost.getHandler().getLooper()) {
          if (!paramBoolean)
            checkStateLoss(); 
          if (this.mTmpRecords == null) {
            this.mTmpRecords = new ArrayList<BackStackRecord>();
            this.mTmpIsPop = new ArrayList<Boolean>();
          } 
          this.mExecutingActions = true;
          try {
            executePostponedTransaction(null, null);
            return;
          } finally {
            this.mExecutingActions = false;
          } 
        } 
        throw new IllegalStateException("Must be called from main thread of fragment host");
      } 
      throw new IllegalStateException("Fragment host has been destroyed");
    } 
    throw new IllegalStateException("FragmentManager is already executing transactions");
  }
  
  private static void executeOps(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, int paramInt1, int paramInt2) {
    while (paramInt1 < paramInt2) {
      BackStackRecord backStackRecord = paramArrayList.get(paramInt1);
      boolean bool = ((Boolean)paramArrayList1.get(paramInt1)).booleanValue();
      boolean bool1 = true;
      if (bool) {
        backStackRecord.bumpBackStackNesting(-1);
        if (paramInt1 != paramInt2 - 1)
          bool1 = false; 
        backStackRecord.executePopOps(bool1);
      } else {
        backStackRecord.bumpBackStackNesting(1);
        backStackRecord.executeOps();
      } 
      paramInt1++;
    } 
  }
  
  private void executeOpsTogether(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, int paramInt1, int paramInt2) {
    int k;
    int i = paramInt1;
    boolean bool = ((BackStackRecord)paramArrayList.get(i)).mReorderingAllowed;
    ArrayList<Fragment> arrayList = this.mTmpAddedFragments;
    if (arrayList == null) {
      this.mTmpAddedFragments = new ArrayList<Fragment>();
    } else {
      arrayList.clear();
    } 
    this.mTmpAddedFragments.addAll(this.mAdded);
    Fragment fragment = getPrimaryNavigationFragment();
    int j = i;
    boolean bool1 = false;
    while (j < paramInt2) {
      BackStackRecord backStackRecord = paramArrayList.get(j);
      if (!((Boolean)paramArrayList1.get(j)).booleanValue()) {
        fragment = backStackRecord.expandOps(this.mTmpAddedFragments, fragment);
      } else {
        fragment = backStackRecord.trackAddedFragmentsInPop(this.mTmpAddedFragments, fragment);
      } 
      if (bool1 || backStackRecord.mAddToBackStack) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      j++;
    } 
    this.mTmpAddedFragments.clear();
    if (!bool)
      FragmentTransition.startTransitions(this, paramArrayList, paramArrayList1, paramInt1, paramInt2, false); 
    executeOps(paramArrayList, paramArrayList1, paramInt1, paramInt2);
    if (bool) {
      ArraySet<Fragment> arraySet = new ArraySet();
      addAddedFragments(arraySet);
      k = postponePostponableTransactions(paramArrayList, paramArrayList1, paramInt1, paramInt2, arraySet);
      makeRemovedFragmentsInvisible(arraySet);
    } else {
      k = paramInt2;
    } 
    j = i;
    if (k != i) {
      j = i;
      if (bool) {
        FragmentTransition.startTransitions(this, paramArrayList, paramArrayList1, paramInt1, k, true);
        moveToState(this.mCurState, true);
        j = i;
      } 
    } 
    while (j < paramInt2) {
      BackStackRecord backStackRecord = paramArrayList.get(j);
      if (((Boolean)paramArrayList1.get(j)).booleanValue() && backStackRecord.mIndex >= 0) {
        freeBackStackIndex(backStackRecord.mIndex);
        backStackRecord.mIndex = -1;
      } 
      backStackRecord.runOnCommitRunnables();
      j++;
    } 
    if (bool1)
      reportBackStackChanged(); 
  }
  
  private void executePostponedTransaction(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mPostponedTransactions : Ljava/util/ArrayList;
    //   4: astore_3
    //   5: aload_3
    //   6: ifnonnull -> 15
    //   9: iconst_0
    //   10: istore #4
    //   12: goto -> 21
    //   15: aload_3
    //   16: invokevirtual size : ()I
    //   19: istore #4
    //   21: iconst_0
    //   22: istore #5
    //   24: iload #5
    //   26: iload #4
    //   28: if_icmpge -> 232
    //   31: aload_0
    //   32: getfield mPostponedTransactions : Ljava/util/ArrayList;
    //   35: iload #5
    //   37: invokevirtual get : (I)Ljava/lang/Object;
    //   40: checkcast android/support/v4/app/FragmentManagerImpl$StartEnterTransitionListener
    //   43: astore_3
    //   44: aload_1
    //   45: ifnull -> 101
    //   48: aload_3
    //   49: invokestatic access$300 : (Landroid/support/v4/app/FragmentManagerImpl$StartEnterTransitionListener;)Z
    //   52: ifne -> 101
    //   55: aload_1
    //   56: aload_3
    //   57: invokestatic access$400 : (Landroid/support/v4/app/FragmentManagerImpl$StartEnterTransitionListener;)Landroid/support/v4/app/BackStackRecord;
    //   60: invokevirtual indexOf : (Ljava/lang/Object;)I
    //   63: istore #6
    //   65: iload #6
    //   67: iconst_m1
    //   68: if_icmpeq -> 101
    //   71: aload_2
    //   72: iload #6
    //   74: invokevirtual get : (I)Ljava/lang/Object;
    //   77: checkcast java/lang/Boolean
    //   80: invokevirtual booleanValue : ()Z
    //   83: ifeq -> 101
    //   86: aload_3
    //   87: invokevirtual cancelTransaction : ()V
    //   90: iload #4
    //   92: istore #6
    //   94: iload #5
    //   96: istore #7
    //   98: goto -> 219
    //   101: aload_3
    //   102: invokevirtual isReady : ()Z
    //   105: ifne -> 144
    //   108: iload #4
    //   110: istore #6
    //   112: iload #5
    //   114: istore #7
    //   116: aload_1
    //   117: ifnull -> 219
    //   120: iload #4
    //   122: istore #6
    //   124: iload #5
    //   126: istore #7
    //   128: aload_3
    //   129: invokestatic access$400 : (Landroid/support/v4/app/FragmentManagerImpl$StartEnterTransitionListener;)Landroid/support/v4/app/BackStackRecord;
    //   132: aload_1
    //   133: iconst_0
    //   134: aload_1
    //   135: invokevirtual size : ()I
    //   138: invokevirtual interactsWith : (Ljava/util/ArrayList;II)Z
    //   141: ifeq -> 219
    //   144: aload_0
    //   145: getfield mPostponedTransactions : Ljava/util/ArrayList;
    //   148: iload #5
    //   150: invokevirtual remove : (I)Ljava/lang/Object;
    //   153: pop
    //   154: iload #5
    //   156: iconst_1
    //   157: isub
    //   158: istore #7
    //   160: iload #4
    //   162: iconst_1
    //   163: isub
    //   164: istore #6
    //   166: aload_1
    //   167: ifnull -> 215
    //   170: aload_3
    //   171: invokestatic access$300 : (Landroid/support/v4/app/FragmentManagerImpl$StartEnterTransitionListener;)Z
    //   174: ifne -> 215
    //   177: aload_1
    //   178: aload_3
    //   179: invokestatic access$400 : (Landroid/support/v4/app/FragmentManagerImpl$StartEnterTransitionListener;)Landroid/support/v4/app/BackStackRecord;
    //   182: invokevirtual indexOf : (Ljava/lang/Object;)I
    //   185: istore #5
    //   187: iload #5
    //   189: iconst_m1
    //   190: if_icmpeq -> 215
    //   193: aload_2
    //   194: iload #5
    //   196: invokevirtual get : (I)Ljava/lang/Object;
    //   199: checkcast java/lang/Boolean
    //   202: invokevirtual booleanValue : ()Z
    //   205: ifeq -> 215
    //   208: aload_3
    //   209: invokevirtual cancelTransaction : ()V
    //   212: goto -> 219
    //   215: aload_3
    //   216: invokevirtual completeTransaction : ()V
    //   219: iload #7
    //   221: iconst_1
    //   222: iadd
    //   223: istore #5
    //   225: iload #6
    //   227: istore #4
    //   229: goto -> 24
    //   232: return
  }
  
  private Fragment findFragmentUnder(Fragment paramFragment) {
    ViewGroup viewGroup = paramFragment.mContainer;
    View view = paramFragment.mView;
    if (viewGroup != null && view != null)
      for (int i = this.mAdded.indexOf(paramFragment) - 1; i >= 0; i--) {
        paramFragment = this.mAdded.get(i);
        if (paramFragment.mContainer == viewGroup && paramFragment.mView != null)
          return paramFragment; 
      }  
    return null;
  }
  
  private void forcePostponedTransactions() {
    if (this.mPostponedTransactions != null)
      while (!this.mPostponedTransactions.isEmpty())
        ((StartEnterTransitionListener)this.mPostponedTransactions.remove(0)).completeTransaction();  
  }
  
  private boolean generateOpsForPendingActions(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mPendingActions : Ljava/util/ArrayList;
    //   6: astore_3
    //   7: iconst_0
    //   8: istore #4
    //   10: aload_3
    //   11: ifnull -> 102
    //   14: aload_0
    //   15: getfield mPendingActions : Ljava/util/ArrayList;
    //   18: invokevirtual size : ()I
    //   21: ifne -> 27
    //   24: goto -> 102
    //   27: aload_0
    //   28: getfield mPendingActions : Ljava/util/ArrayList;
    //   31: invokevirtual size : ()I
    //   34: istore #5
    //   36: iconst_0
    //   37: istore #6
    //   39: iload #4
    //   41: iload #5
    //   43: if_icmpge -> 76
    //   46: iload #6
    //   48: aload_0
    //   49: getfield mPendingActions : Ljava/util/ArrayList;
    //   52: iload #4
    //   54: invokevirtual get : (I)Ljava/lang/Object;
    //   57: checkcast android/support/v4/app/FragmentManagerImpl$OpGenerator
    //   60: aload_1
    //   61: aload_2
    //   62: invokeinterface generateOps : (Ljava/util/ArrayList;Ljava/util/ArrayList;)Z
    //   67: ior
    //   68: istore #6
    //   70: iinc #4, 1
    //   73: goto -> 39
    //   76: aload_0
    //   77: getfield mPendingActions : Ljava/util/ArrayList;
    //   80: invokevirtual clear : ()V
    //   83: aload_0
    //   84: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   87: invokevirtual getHandler : ()Landroid/os/Handler;
    //   90: aload_0
    //   91: getfield mExecCommit : Ljava/lang/Runnable;
    //   94: invokevirtual removeCallbacks : (Ljava/lang/Runnable;)V
    //   97: aload_0
    //   98: monitorexit
    //   99: iload #6
    //   101: ireturn
    //   102: aload_0
    //   103: monitorexit
    //   104: iconst_0
    //   105: ireturn
    //   106: astore_1
    //   107: aload_0
    //   108: monitorexit
    //   109: aload_1
    //   110: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	106	finally
    //   14	24	106	finally
    //   27	36	106	finally
    //   46	70	106	finally
    //   76	99	106	finally
    //   102	104	106	finally
    //   107	109	106	finally
  }
  
  private static Animation.AnimationListener getAnimationListener(Animation paramAnimation) {
    try {
      if (sAnimationListenerField == null) {
        Field field = Animation.class.getDeclaredField("mListener");
        sAnimationListenerField = field;
        field.setAccessible(true);
      } 
      Animation.AnimationListener animationListener = (Animation.AnimationListener)sAnimationListenerField.get(paramAnimation);
    } catch (NoSuchFieldException noSuchFieldException) {
      Log.e("FragmentManager", "No field with the name mListener is found in Animation class", noSuchFieldException);
      noSuchFieldException = null;
    } catch (IllegalAccessException illegalAccessException) {
      Log.e("FragmentManager", "Cannot access Animation's mListener field", illegalAccessException);
    } 
    return (Animation.AnimationListener)illegalAccessException;
  }
  
  static AnimationOrAnimator makeFadeAnimation(Context paramContext, float paramFloat1, float paramFloat2) {
    AlphaAnimation alphaAnimation = new AlphaAnimation(paramFloat1, paramFloat2);
    alphaAnimation.setInterpolator(DECELERATE_CUBIC);
    alphaAnimation.setDuration(220L);
    return new AnimationOrAnimator((Animation)alphaAnimation);
  }
  
  static AnimationOrAnimator makeOpenCloseAnimation(Context paramContext, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    AnimationSet animationSet = new AnimationSet(false);
    ScaleAnimation scaleAnimation = new ScaleAnimation(paramFloat1, paramFloat2, paramFloat1, paramFloat2, 1, 0.5F, 1, 0.5F);
    scaleAnimation.setInterpolator(DECELERATE_QUINT);
    scaleAnimation.setDuration(220L);
    animationSet.addAnimation((Animation)scaleAnimation);
    AlphaAnimation alphaAnimation = new AlphaAnimation(paramFloat3, paramFloat4);
    alphaAnimation.setInterpolator(DECELERATE_CUBIC);
    alphaAnimation.setDuration(220L);
    animationSet.addAnimation((Animation)alphaAnimation);
    return new AnimationOrAnimator((Animation)animationSet);
  }
  
  private void makeRemovedFragmentsInvisible(ArraySet<Fragment> paramArraySet) {
    int i = paramArraySet.size();
    for (byte b = 0; b < i; b++) {
      Fragment fragment = (Fragment)paramArraySet.valueAt(b);
      if (!fragment.mAdded) {
        View view = fragment.getView();
        fragment.mPostponedAlpha = view.getAlpha();
        view.setAlpha(0.0F);
      } 
    } 
  }
  
  static boolean modifiesAlpha(Animator paramAnimator) {
    PropertyValuesHolder[] arrayOfPropertyValuesHolder;
    if (paramAnimator == null)
      return false; 
    if (paramAnimator instanceof ValueAnimator) {
      arrayOfPropertyValuesHolder = ((ValueAnimator)paramAnimator).getValues();
      for (byte b = 0; b < arrayOfPropertyValuesHolder.length; b++) {
        if ("alpha".equals(arrayOfPropertyValuesHolder[b].getPropertyName()))
          return true; 
      } 
    } else if (arrayOfPropertyValuesHolder instanceof AnimatorSet) {
      ArrayList<Animator> arrayList = ((AnimatorSet)arrayOfPropertyValuesHolder).getChildAnimations();
      for (byte b = 0; b < arrayList.size(); b++) {
        if (modifiesAlpha(arrayList.get(b)))
          return true; 
      } 
    } 
    return false;
  }
  
  static boolean modifiesAlpha(AnimationOrAnimator paramAnimationOrAnimator) {
    List list;
    if (paramAnimationOrAnimator.animation instanceof AlphaAnimation)
      return true; 
    if (paramAnimationOrAnimator.animation instanceof AnimationSet) {
      list = ((AnimationSet)paramAnimationOrAnimator.animation).getAnimations();
      for (byte b = 0; b < list.size(); b++) {
        if (list.get(b) instanceof AlphaAnimation)
          return true; 
      } 
      return false;
    } 
    return modifiesAlpha(((AnimationOrAnimator)list).animator);
  }
  
  private boolean popBackStackImmediate(String paramString, int paramInt1, int paramInt2) {
    execPendingActions();
    ensureExecReady(true);
    Fragment fragment = this.mPrimaryNav;
    if (fragment != null && paramInt1 < 0 && paramString == null) {
      FragmentManager fragmentManager = fragment.peekChildFragmentManager();
      if (fragmentManager != null && fragmentManager.popBackStackImmediate())
        return true; 
    } 
    boolean bool = popBackStackState(this.mTmpRecords, this.mTmpIsPop, paramString, paramInt1, paramInt2);
    if (bool) {
      this.mExecutingActions = true;
      try {
        removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
      } finally {
        cleanupExec();
      } 
    } 
    doPendingDeferredStart();
    burpActive();
    return bool;
  }
  
  private int postponePostponableTransactions(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, int paramInt1, int paramInt2, ArraySet<Fragment> paramArraySet) {
    int i = paramInt2 - 1;
    int j;
    for (j = paramInt2; i >= paramInt1; j = k) {
      boolean bool1;
      BackStackRecord backStackRecord = paramArrayList.get(i);
      boolean bool = ((Boolean)paramArrayList1.get(i)).booleanValue();
      if (backStackRecord.isPostponed() && !backStackRecord.interactsWith(paramArrayList, i + 1, paramInt2)) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      int k = j;
      if (bool1) {
        if (this.mPostponedTransactions == null)
          this.mPostponedTransactions = new ArrayList<StartEnterTransitionListener>(); 
        StartEnterTransitionListener startEnterTransitionListener = new StartEnterTransitionListener(backStackRecord, bool);
        this.mPostponedTransactions.add(startEnterTransitionListener);
        backStackRecord.setOnStartPostponedListener(startEnterTransitionListener);
        if (bool) {
          backStackRecord.executeOps();
        } else {
          backStackRecord.executePopOps(false);
        } 
        k = j - 1;
        if (i != k) {
          paramArrayList.remove(i);
          paramArrayList.add(k, backStackRecord);
        } 
        addAddedFragments(paramArraySet);
      } 
      i--;
    } 
    return j;
  }
  
  private void removeRedundantOperationsAndExecute(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1) {
    if (paramArrayList == null || paramArrayList.isEmpty())
      return; 
    if (paramArrayList1 != null && paramArrayList.size() == paramArrayList1.size()) {
      executePostponedTransaction(paramArrayList, paramArrayList1);
      int i = paramArrayList.size();
      int j = 0;
      int k;
      for (k = 0; j < i; k = n) {
        int m = j;
        int n = k;
        if (!((BackStackRecord)paramArrayList.get(j)).mReorderingAllowed) {
          if (k != j)
            executeOpsTogether(paramArrayList, paramArrayList1, k, j); 
          k = j + 1;
          n = k;
          if (((Boolean)paramArrayList1.get(j)).booleanValue())
            while (true) {
              n = k;
              if (k < i) {
                n = k;
                if (((Boolean)paramArrayList1.get(k)).booleanValue()) {
                  n = k;
                  if (!((BackStackRecord)paramArrayList.get(k)).mReorderingAllowed) {
                    k++;
                    continue;
                  } 
                } 
              } 
              break;
            }  
          executeOpsTogether(paramArrayList, paramArrayList1, j, n);
          m = n - 1;
        } 
        j = m + 1;
      } 
      if (k != i)
        executeOpsTogether(paramArrayList, paramArrayList1, k, i); 
      return;
    } 
    throw new IllegalStateException("Internal error with the back stack records");
  }
  
  public static int reverseTransit(int paramInt) {
    char c = ' ';
    if (paramInt != 4097)
      if (paramInt != 4099) {
        if (paramInt != 8194) {
          c = Character.MIN_VALUE;
        } else {
          c = 'ခ';
        } 
      } else {
        c = 'ဃ';
      }  
    return c;
  }
  
  private void scheduleCommit() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mPostponedTransactions : Ljava/util/ArrayList;
    //   6: astore_1
    //   7: iconst_0
    //   8: istore_2
    //   9: aload_1
    //   10: ifnull -> 28
    //   13: aload_0
    //   14: getfield mPostponedTransactions : Ljava/util/ArrayList;
    //   17: invokevirtual isEmpty : ()Z
    //   20: ifne -> 28
    //   23: iconst_1
    //   24: istore_3
    //   25: goto -> 30
    //   28: iconst_0
    //   29: istore_3
    //   30: iload_2
    //   31: istore #4
    //   33: aload_0
    //   34: getfield mPendingActions : Ljava/util/ArrayList;
    //   37: ifnull -> 57
    //   40: iload_2
    //   41: istore #4
    //   43: aload_0
    //   44: getfield mPendingActions : Ljava/util/ArrayList;
    //   47: invokevirtual size : ()I
    //   50: iconst_1
    //   51: if_icmpne -> 57
    //   54: iconst_1
    //   55: istore #4
    //   57: iload_3
    //   58: ifne -> 66
    //   61: iload #4
    //   63: ifeq -> 95
    //   66: aload_0
    //   67: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   70: invokevirtual getHandler : ()Landroid/os/Handler;
    //   73: aload_0
    //   74: getfield mExecCommit : Ljava/lang/Runnable;
    //   77: invokevirtual removeCallbacks : (Ljava/lang/Runnable;)V
    //   80: aload_0
    //   81: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   84: invokevirtual getHandler : ()Landroid/os/Handler;
    //   87: aload_0
    //   88: getfield mExecCommit : Ljava/lang/Runnable;
    //   91: invokevirtual post : (Ljava/lang/Runnable;)Z
    //   94: pop
    //   95: aload_0
    //   96: monitorexit
    //   97: return
    //   98: astore_1
    //   99: aload_0
    //   100: monitorexit
    //   101: aload_1
    //   102: athrow
    // Exception table:
    //   from	to	target	type
    //   2	7	98	finally
    //   13	23	98	finally
    //   33	40	98	finally
    //   43	54	98	finally
    //   66	95	98	finally
    //   95	97	98	finally
    //   99	101	98	finally
  }
  
  private static void setHWLayerAnimListenerIfAlpha(View paramView, AnimationOrAnimator paramAnimationOrAnimator) {
    if (paramView != null && paramAnimationOrAnimator != null && shouldRunOnHWLayer(paramView, paramAnimationOrAnimator))
      if (paramAnimationOrAnimator.animator != null) {
        paramAnimationOrAnimator.animator.addListener((Animator.AnimatorListener)new AnimatorOnHWLayerIfNeededListener(paramView));
      } else {
        Animation.AnimationListener animationListener = getAnimationListener(paramAnimationOrAnimator.animation);
        paramView.setLayerType(2, null);
        paramAnimationOrAnimator.animation.setAnimationListener(new AnimateOnHWLayerIfNeededListener(paramView, animationListener));
      }  
  }
  
  private static void setRetaining(FragmentManagerNonConfig paramFragmentManagerNonConfig) {
    if (paramFragmentManagerNonConfig == null)
      return; 
    List<Fragment> list1 = paramFragmentManagerNonConfig.getFragments();
    if (list1 != null) {
      Iterator<Fragment> iterator = list1.iterator();
      while (iterator.hasNext())
        ((Fragment)iterator.next()).mRetaining = true; 
    } 
    List<FragmentManagerNonConfig> list = paramFragmentManagerNonConfig.getChildNonConfigs();
    if (list != null) {
      Iterator<FragmentManagerNonConfig> iterator = list.iterator();
      while (iterator.hasNext())
        setRetaining(iterator.next()); 
    } 
  }
  
  static boolean shouldRunOnHWLayer(View paramView, AnimationOrAnimator paramAnimationOrAnimator) {
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (paramView != null)
      if (paramAnimationOrAnimator == null) {
        bool2 = bool1;
      } else {
        bool2 = bool1;
        if (Build.VERSION.SDK_INT >= 19) {
          bool2 = bool1;
          if (paramView.getLayerType() == 0) {
            bool2 = bool1;
            if (ViewCompat.hasOverlappingRendering(paramView)) {
              bool2 = bool1;
              if (modifiesAlpha(paramAnimationOrAnimator))
                bool2 = true; 
            } 
          } 
        } 
      }  
    return bool2;
  }
  
  private void throwException(RuntimeException paramRuntimeException) {
    Log.e("FragmentManager", paramRuntimeException.getMessage());
    Log.e("FragmentManager", "Activity state:");
    PrintWriter printWriter = new PrintWriter((Writer)new LogWriter("FragmentManager"));
    FragmentHostCallback fragmentHostCallback = this.mHost;
    if (fragmentHostCallback != null) {
      try {
        fragmentHostCallback.onDump("  ", null, printWriter, new String[0]);
      } catch (Exception exception) {
        Log.e("FragmentManager", "Failed dumping state", exception);
      } 
    } else {
      try {
        dump("  ", null, printWriter, new String[0]);
      } catch (Exception exception) {
        Log.e("FragmentManager", "Failed dumping state", exception);
      } 
    } 
    throw paramRuntimeException;
  }
  
  public static int transitToStyleIndex(int paramInt, boolean paramBoolean) {
    if (paramInt != 4097) {
      if (paramInt != 4099) {
        if (paramInt != 8194) {
          paramInt = -1;
        } else if (paramBoolean) {
          paramInt = 3;
        } else {
          paramInt = 4;
        } 
      } else if (paramBoolean) {
        paramInt = 5;
      } else {
        paramInt = 6;
      } 
    } else if (paramBoolean) {
      paramInt = 1;
    } else {
      paramInt = 2;
    } 
    return paramInt;
  }
  
  void addBackStackState(BackStackRecord paramBackStackRecord) {
    if (this.mBackStack == null)
      this.mBackStack = new ArrayList<BackStackRecord>(); 
    this.mBackStack.add(paramBackStackRecord);
  }
  
  public void addFragment(Fragment paramFragment, boolean paramBoolean) {
    if (DEBUG) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("add: ");
      stringBuilder.append(paramFragment);
      Log.v("FragmentManager", stringBuilder.toString());
    } 
    makeActive(paramFragment);
    if (!paramFragment.mDetached)
      if (!this.mAdded.contains(paramFragment)) {
        synchronized (this.mAdded) {
          this.mAdded.add(paramFragment);
          paramFragment.mAdded = true;
          paramFragment.mRemoving = false;
          if (paramFragment.mView == null)
            paramFragment.mHiddenChanged = false; 
          if (paramFragment.mHasMenu && paramFragment.mMenuVisible)
            this.mNeedMenuInvalidate = true; 
          if (paramBoolean)
            moveToState(paramFragment); 
        } 
      } else {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Fragment already added: ");
        stringBuilder.append(paramFragment);
        throw new IllegalStateException(stringBuilder.toString());
      }  
  }
  
  public void addOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener paramOnBackStackChangedListener) {
    if (this.mBackStackChangeListeners == null)
      this.mBackStackChangeListeners = new ArrayList<FragmentManager.OnBackStackChangedListener>(); 
    this.mBackStackChangeListeners.add(paramOnBackStackChangedListener);
  }
  
  public int allocBackStackIndex(BackStackRecord paramBackStackRecord) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   6: ifnull -> 111
    //   9: aload_0
    //   10: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   13: invokevirtual size : ()I
    //   16: ifgt -> 22
    //   19: goto -> 111
    //   22: aload_0
    //   23: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   26: aload_0
    //   27: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   30: invokevirtual size : ()I
    //   33: iconst_1
    //   34: isub
    //   35: invokevirtual remove : (I)Ljava/lang/Object;
    //   38: checkcast java/lang/Integer
    //   41: invokevirtual intValue : ()I
    //   44: istore_2
    //   45: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   48: ifeq -> 97
    //   51: new java/lang/StringBuilder
    //   54: astore_3
    //   55: aload_3
    //   56: invokespecial <init> : ()V
    //   59: aload_3
    //   60: ldc_w 'Adding back stack index '
    //   63: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   66: pop
    //   67: aload_3
    //   68: iload_2
    //   69: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   72: pop
    //   73: aload_3
    //   74: ldc_w ' with '
    //   77: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   80: pop
    //   81: aload_3
    //   82: aload_1
    //   83: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   86: pop
    //   87: ldc 'FragmentManager'
    //   89: aload_3
    //   90: invokevirtual toString : ()Ljava/lang/String;
    //   93: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   96: pop
    //   97: aload_0
    //   98: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   101: iload_2
    //   102: aload_1
    //   103: invokevirtual set : (ILjava/lang/Object;)Ljava/lang/Object;
    //   106: pop
    //   107: aload_0
    //   108: monitorexit
    //   109: iload_2
    //   110: ireturn
    //   111: aload_0
    //   112: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   115: ifnonnull -> 131
    //   118: new java/util/ArrayList
    //   121: astore_3
    //   122: aload_3
    //   123: invokespecial <init> : ()V
    //   126: aload_0
    //   127: aload_3
    //   128: putfield mBackStackIndices : Ljava/util/ArrayList;
    //   131: aload_0
    //   132: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   135: invokevirtual size : ()I
    //   138: istore_2
    //   139: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   142: ifeq -> 191
    //   145: new java/lang/StringBuilder
    //   148: astore_3
    //   149: aload_3
    //   150: invokespecial <init> : ()V
    //   153: aload_3
    //   154: ldc_w 'Setting back stack index '
    //   157: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   160: pop
    //   161: aload_3
    //   162: iload_2
    //   163: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   166: pop
    //   167: aload_3
    //   168: ldc_w ' to '
    //   171: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   174: pop
    //   175: aload_3
    //   176: aload_1
    //   177: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   180: pop
    //   181: ldc 'FragmentManager'
    //   183: aload_3
    //   184: invokevirtual toString : ()Ljava/lang/String;
    //   187: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   190: pop
    //   191: aload_0
    //   192: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   195: aload_1
    //   196: invokevirtual add : (Ljava/lang/Object;)Z
    //   199: pop
    //   200: aload_0
    //   201: monitorexit
    //   202: iload_2
    //   203: ireturn
    //   204: astore_1
    //   205: aload_0
    //   206: monitorexit
    //   207: aload_1
    //   208: athrow
    // Exception table:
    //   from	to	target	type
    //   2	19	204	finally
    //   22	97	204	finally
    //   97	109	204	finally
    //   111	131	204	finally
    //   131	191	204	finally
    //   191	202	204	finally
    //   205	207	204	finally
  }
  
  public void attachController(FragmentHostCallback paramFragmentHostCallback, FragmentContainer paramFragmentContainer, Fragment paramFragment) {
    if (this.mHost == null) {
      this.mHost = paramFragmentHostCallback;
      this.mContainer = paramFragmentContainer;
      this.mParent = paramFragment;
      return;
    } 
    throw new IllegalStateException("Already attached");
  }
  
  public void attachFragment(Fragment paramFragment) {
    if (DEBUG) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("attach: ");
      stringBuilder.append(paramFragment);
      Log.v("FragmentManager", stringBuilder.toString());
    } 
    if (paramFragment.mDetached) {
      paramFragment.mDetached = false;
      if (!paramFragment.mAdded)
        if (!this.mAdded.contains(paramFragment)) {
          if (DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("add from attach: ");
            stringBuilder.append(paramFragment);
            Log.v("FragmentManager", stringBuilder.toString());
          } 
          synchronized (this.mAdded) {
            this.mAdded.add(paramFragment);
            paramFragment.mAdded = true;
            if (paramFragment.mHasMenu && paramFragment.mMenuVisible)
              this.mNeedMenuInvalidate = true; 
          } 
        } else {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Fragment already added: ");
          stringBuilder.append(paramFragment);
          throw new IllegalStateException(stringBuilder.toString());
        }  
    } 
  }
  
  public FragmentTransaction beginTransaction() {
    return new BackStackRecord(this);
  }
  
  void completeShowHideFragment(final Fragment fragment) {
    if (fragment.mView != null) {
      AnimationOrAnimator animationOrAnimator = loadAnimation(fragment, fragment.getNextTransition(), fragment.mHidden ^ true, fragment.getNextTransitionStyle());
      if (animationOrAnimator != null && animationOrAnimator.animator != null) {
        animationOrAnimator.animator.setTarget(fragment.mView);
        if (fragment.mHidden) {
          if (fragment.isHideReplaced()) {
            fragment.setHideReplaced(false);
          } else {
            final ViewGroup container = fragment.mContainer;
            final View animatingView = fragment.mView;
            viewGroup.startViewTransition(view);
            animationOrAnimator.animator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
                  public void onAnimationEnd(Animator param1Animator) {
                    container.endViewTransition(animatingView);
                    param1Animator.removeListener((Animator.AnimatorListener)this);
                    if (fragment.mView != null)
                      fragment.mView.setVisibility(8); 
                  }
                });
          } 
        } else {
          fragment.mView.setVisibility(0);
        } 
        setHWLayerAnimListenerIfAlpha(fragment.mView, animationOrAnimator);
        animationOrAnimator.animator.start();
      } else {
        boolean bool;
        if (animationOrAnimator != null) {
          setHWLayerAnimListenerIfAlpha(fragment.mView, animationOrAnimator);
          fragment.mView.startAnimation(animationOrAnimator.animation);
          animationOrAnimator.animation.start();
        } 
        if (fragment.mHidden && !fragment.isHideReplaced()) {
          bool = true;
        } else {
          bool = false;
        } 
        fragment.mView.setVisibility(bool);
        if (fragment.isHideReplaced())
          fragment.setHideReplaced(false); 
      } 
    } 
    if (fragment.mAdded && fragment.mHasMenu && fragment.mMenuVisible)
      this.mNeedMenuInvalidate = true; 
    fragment.mHiddenChanged = false;
    fragment.onHiddenChanged(fragment.mHidden);
  }
  
  public void detachFragment(Fragment paramFragment) {
    if (DEBUG) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("detach: ");
      stringBuilder.append(paramFragment);
      Log.v("FragmentManager", stringBuilder.toString());
    } 
    if (!paramFragment.mDetached) {
      paramFragment.mDetached = true;
      if (paramFragment.mAdded) {
        if (DEBUG) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("remove from detach: ");
          stringBuilder.append(paramFragment);
          Log.v("FragmentManager", stringBuilder.toString());
        } 
        synchronized (this.mAdded) {
          this.mAdded.remove(paramFragment);
          if (paramFragment.mHasMenu && paramFragment.mMenuVisible)
            this.mNeedMenuInvalidate = true; 
          paramFragment.mAdded = false;
        } 
      } 
    } 
  }
  
  public void dispatchActivityCreated() {
    this.mStateSaved = false;
    this.mStopped = false;
    dispatchStateChange(2);
  }
  
  public void dispatchConfigurationChanged(Configuration paramConfiguration) {
    for (byte b = 0; b < this.mAdded.size(); b++) {
      Fragment fragment = this.mAdded.get(b);
      if (fragment != null)
        fragment.performConfigurationChanged(paramConfiguration); 
    } 
  }
  
  public boolean dispatchContextItemSelected(MenuItem paramMenuItem) {
    if (this.mCurState < 1)
      return false; 
    for (byte b = 0; b < this.mAdded.size(); b++) {
      Fragment fragment = this.mAdded.get(b);
      if (fragment != null && fragment.performContextItemSelected(paramMenuItem))
        return true; 
    } 
    return false;
  }
  
  public void dispatchCreate() {
    this.mStateSaved = false;
    this.mStopped = false;
    dispatchStateChange(1);
  }
  
  public boolean dispatchCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater) {
    int i = this.mCurState;
    boolean bool1 = false;
    if (i < 1)
      return false; 
    ArrayList<Fragment> arrayList = null;
    i = 0;
    boolean bool2;
    for (bool2 = false; i < this.mAdded.size(); bool2 = bool) {
      Fragment fragment = this.mAdded.get(i);
      ArrayList<Fragment> arrayList1 = arrayList;
      boolean bool = bool2;
      if (fragment != null) {
        arrayList1 = arrayList;
        bool = bool2;
        if (fragment.performCreateOptionsMenu(paramMenu, paramMenuInflater)) {
          arrayList1 = arrayList;
          if (arrayList == null)
            arrayList1 = new ArrayList(); 
          arrayList1.add(fragment);
          bool = true;
        } 
      } 
      i++;
      arrayList = arrayList1;
    } 
    if (this.mCreatedMenus != null)
      for (i = bool1; i < this.mCreatedMenus.size(); i++) {
        Fragment fragment = this.mCreatedMenus.get(i);
        if (arrayList == null || !arrayList.contains(fragment))
          fragment.onDestroyOptionsMenu(); 
      }  
    this.mCreatedMenus = arrayList;
    return bool2;
  }
  
  public void dispatchDestroy() {
    this.mDestroyed = true;
    execPendingActions();
    dispatchStateChange(0);
    this.mHost = null;
    this.mContainer = null;
    this.mParent = null;
  }
  
  public void dispatchDestroyView() {
    dispatchStateChange(1);
  }
  
  public void dispatchLowMemory() {
    for (byte b = 0; b < this.mAdded.size(); b++) {
      Fragment fragment = this.mAdded.get(b);
      if (fragment != null)
        fragment.performLowMemory(); 
    } 
  }
  
  public void dispatchMultiWindowModeChanged(boolean paramBoolean) {
    for (int i = this.mAdded.size() - 1; i >= 0; i--) {
      Fragment fragment = this.mAdded.get(i);
      if (fragment != null)
        fragment.performMultiWindowModeChanged(paramBoolean); 
    } 
  }
  
  void dispatchOnFragmentActivityCreated(Fragment paramFragment, Bundle paramBundle, boolean paramBoolean) {
    Fragment fragment = this.mParent;
    if (fragment != null) {
      FragmentManager fragmentManager = fragment.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentActivityCreated(paramFragment, paramBundle, true); 
    } 
    for (Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> pair : this.mLifecycleCallbacks) {
      if (!paramBoolean || ((Boolean)pair.second).booleanValue())
        ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentActivityCreated(this, paramFragment, paramBundle); 
    } 
  }
  
  void dispatchOnFragmentAttached(Fragment paramFragment, Context paramContext, boolean paramBoolean) {
    Fragment fragment = this.mParent;
    if (fragment != null) {
      FragmentManager fragmentManager = fragment.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentAttached(paramFragment, paramContext, true); 
    } 
    for (Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> pair : this.mLifecycleCallbacks) {
      if (!paramBoolean || ((Boolean)pair.second).booleanValue())
        ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentAttached(this, paramFragment, paramContext); 
    } 
  }
  
  void dispatchOnFragmentCreated(Fragment paramFragment, Bundle paramBundle, boolean paramBoolean) {
    Fragment fragment = this.mParent;
    if (fragment != null) {
      FragmentManager fragmentManager = fragment.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentCreated(paramFragment, paramBundle, true); 
    } 
    for (Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> pair : this.mLifecycleCallbacks) {
      if (!paramBoolean || ((Boolean)pair.second).booleanValue())
        ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentCreated(this, paramFragment, paramBundle); 
    } 
  }
  
  void dispatchOnFragmentDestroyed(Fragment paramFragment, boolean paramBoolean) {
    Fragment fragment = this.mParent;
    if (fragment != null) {
      FragmentManager fragmentManager = fragment.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentDestroyed(paramFragment, true); 
    } 
    for (Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> pair : this.mLifecycleCallbacks) {
      if (!paramBoolean || ((Boolean)pair.second).booleanValue())
        ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentDestroyed(this, paramFragment); 
    } 
  }
  
  void dispatchOnFragmentDetached(Fragment paramFragment, boolean paramBoolean) {
    Fragment fragment = this.mParent;
    if (fragment != null) {
      FragmentManager fragmentManager = fragment.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentDetached(paramFragment, true); 
    } 
    for (Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> pair : this.mLifecycleCallbacks) {
      if (!paramBoolean || ((Boolean)pair.second).booleanValue())
        ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentDetached(this, paramFragment); 
    } 
  }
  
  void dispatchOnFragmentPaused(Fragment paramFragment, boolean paramBoolean) {
    Fragment fragment = this.mParent;
    if (fragment != null) {
      FragmentManager fragmentManager = fragment.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentPaused(paramFragment, true); 
    } 
    for (Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> pair : this.mLifecycleCallbacks) {
      if (!paramBoolean || ((Boolean)pair.second).booleanValue())
        ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentPaused(this, paramFragment); 
    } 
  }
  
  void dispatchOnFragmentPreAttached(Fragment paramFragment, Context paramContext, boolean paramBoolean) {
    Fragment fragment = this.mParent;
    if (fragment != null) {
      FragmentManager fragmentManager = fragment.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentPreAttached(paramFragment, paramContext, true); 
    } 
    for (Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> pair : this.mLifecycleCallbacks) {
      if (!paramBoolean || ((Boolean)pair.second).booleanValue())
        ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentPreAttached(this, paramFragment, paramContext); 
    } 
  }
  
  void dispatchOnFragmentPreCreated(Fragment paramFragment, Bundle paramBundle, boolean paramBoolean) {
    Fragment fragment = this.mParent;
    if (fragment != null) {
      FragmentManager fragmentManager = fragment.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentPreCreated(paramFragment, paramBundle, true); 
    } 
    for (Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> pair : this.mLifecycleCallbacks) {
      if (!paramBoolean || ((Boolean)pair.second).booleanValue())
        ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentPreCreated(this, paramFragment, paramBundle); 
    } 
  }
  
  void dispatchOnFragmentResumed(Fragment paramFragment, boolean paramBoolean) {
    Fragment fragment = this.mParent;
    if (fragment != null) {
      FragmentManager fragmentManager = fragment.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentResumed(paramFragment, true); 
    } 
    for (Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> pair : this.mLifecycleCallbacks) {
      if (!paramBoolean || ((Boolean)pair.second).booleanValue())
        ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentResumed(this, paramFragment); 
    } 
  }
  
  void dispatchOnFragmentSaveInstanceState(Fragment paramFragment, Bundle paramBundle, boolean paramBoolean) {
    Fragment fragment = this.mParent;
    if (fragment != null) {
      FragmentManager fragmentManager = fragment.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentSaveInstanceState(paramFragment, paramBundle, true); 
    } 
    for (Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> pair : this.mLifecycleCallbacks) {
      if (!paramBoolean || ((Boolean)pair.second).booleanValue())
        ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentSaveInstanceState(this, paramFragment, paramBundle); 
    } 
  }
  
  void dispatchOnFragmentStarted(Fragment paramFragment, boolean paramBoolean) {
    Fragment fragment = this.mParent;
    if (fragment != null) {
      FragmentManager fragmentManager = fragment.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentStarted(paramFragment, true); 
    } 
    for (Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> pair : this.mLifecycleCallbacks) {
      if (!paramBoolean || ((Boolean)pair.second).booleanValue())
        ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentStarted(this, paramFragment); 
    } 
  }
  
  void dispatchOnFragmentStopped(Fragment paramFragment, boolean paramBoolean) {
    Fragment fragment = this.mParent;
    if (fragment != null) {
      FragmentManager fragmentManager = fragment.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentStopped(paramFragment, true); 
    } 
    for (Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> pair : this.mLifecycleCallbacks) {
      if (!paramBoolean || ((Boolean)pair.second).booleanValue())
        ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentStopped(this, paramFragment); 
    } 
  }
  
  void dispatchOnFragmentViewCreated(Fragment paramFragment, View paramView, Bundle paramBundle, boolean paramBoolean) {
    Fragment fragment = this.mParent;
    if (fragment != null) {
      FragmentManager fragmentManager = fragment.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentViewCreated(paramFragment, paramView, paramBundle, true); 
    } 
    for (Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> pair : this.mLifecycleCallbacks) {
      if (!paramBoolean || ((Boolean)pair.second).booleanValue())
        ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentViewCreated(this, paramFragment, paramView, paramBundle); 
    } 
  }
  
  void dispatchOnFragmentViewDestroyed(Fragment paramFragment, boolean paramBoolean) {
    Fragment fragment = this.mParent;
    if (fragment != null) {
      FragmentManager fragmentManager = fragment.getFragmentManager();
      if (fragmentManager instanceof FragmentManagerImpl)
        ((FragmentManagerImpl)fragmentManager).dispatchOnFragmentViewDestroyed(paramFragment, true); 
    } 
    for (Pair<FragmentManager.FragmentLifecycleCallbacks, Boolean> pair : this.mLifecycleCallbacks) {
      if (!paramBoolean || ((Boolean)pair.second).booleanValue())
        ((FragmentManager.FragmentLifecycleCallbacks)pair.first).onFragmentViewDestroyed(this, paramFragment); 
    } 
  }
  
  public boolean dispatchOptionsItemSelected(MenuItem paramMenuItem) {
    if (this.mCurState < 1)
      return false; 
    for (byte b = 0; b < this.mAdded.size(); b++) {
      Fragment fragment = this.mAdded.get(b);
      if (fragment != null && fragment.performOptionsItemSelected(paramMenuItem))
        return true; 
    } 
    return false;
  }
  
  public void dispatchOptionsMenuClosed(Menu paramMenu) {
    if (this.mCurState < 1)
      return; 
    for (byte b = 0; b < this.mAdded.size(); b++) {
      Fragment fragment = this.mAdded.get(b);
      if (fragment != null)
        fragment.performOptionsMenuClosed(paramMenu); 
    } 
  }
  
  public void dispatchPause() {
    dispatchStateChange(4);
  }
  
  public void dispatchPictureInPictureModeChanged(boolean paramBoolean) {
    for (int i = this.mAdded.size() - 1; i >= 0; i--) {
      Fragment fragment = this.mAdded.get(i);
      if (fragment != null)
        fragment.performPictureInPictureModeChanged(paramBoolean); 
    } 
  }
  
  public boolean dispatchPrepareOptionsMenu(Menu paramMenu) {
    int i = this.mCurState;
    byte b = 0;
    if (i < 1)
      return false; 
    boolean bool;
    for (bool = false; b < this.mAdded.size(); bool = bool1) {
      Fragment fragment = this.mAdded.get(b);
      boolean bool1 = bool;
      if (fragment != null) {
        bool1 = bool;
        if (fragment.performPrepareOptionsMenu(paramMenu))
          bool1 = true; 
      } 
      b++;
    } 
    return bool;
  }
  
  public void dispatchReallyStop() {
    dispatchStateChange(2);
  }
  
  public void dispatchResume() {
    this.mStateSaved = false;
    this.mStopped = false;
    dispatchStateChange(5);
  }
  
  public void dispatchStart() {
    this.mStateSaved = false;
    this.mStopped = false;
    dispatchStateChange(4);
  }
  
  public void dispatchStop() {
    this.mStopped = true;
    dispatchStateChange(3);
  }
  
  void doPendingDeferredStart() {
    if (this.mHavePendingDeferredStart) {
      this.mHavePendingDeferredStart = false;
      startPendingDeferredFragments();
    } 
  }
  
  public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString) {
    // Byte code:
    //   0: new java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore #5
    //   9: aload #5
    //   11: aload_1
    //   12: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   15: pop
    //   16: aload #5
    //   18: ldc_w '    '
    //   21: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   24: pop
    //   25: aload #5
    //   27: invokevirtual toString : ()Ljava/lang/String;
    //   30: astore #5
    //   32: aload_0
    //   33: getfield mActive : Landroid/util/SparseArray;
    //   36: astore #6
    //   38: iconst_0
    //   39: istore #7
    //   41: aload #6
    //   43: ifnull -> 165
    //   46: aload #6
    //   48: invokevirtual size : ()I
    //   51: istore #8
    //   53: iload #8
    //   55: ifle -> 165
    //   58: aload_3
    //   59: aload_1
    //   60: invokevirtual print : (Ljava/lang/String;)V
    //   63: aload_3
    //   64: ldc_w 'Active Fragments in '
    //   67: invokevirtual print : (Ljava/lang/String;)V
    //   70: aload_3
    //   71: aload_0
    //   72: invokestatic identityHashCode : (Ljava/lang/Object;)I
    //   75: invokestatic toHexString : (I)Ljava/lang/String;
    //   78: invokevirtual print : (Ljava/lang/String;)V
    //   81: aload_3
    //   82: ldc_w ':'
    //   85: invokevirtual println : (Ljava/lang/String;)V
    //   88: iconst_0
    //   89: istore #9
    //   91: iload #9
    //   93: iload #8
    //   95: if_icmpge -> 165
    //   98: aload_0
    //   99: getfield mActive : Landroid/util/SparseArray;
    //   102: iload #9
    //   104: invokevirtual valueAt : (I)Ljava/lang/Object;
    //   107: checkcast android/support/v4/app/Fragment
    //   110: astore #6
    //   112: aload_3
    //   113: aload_1
    //   114: invokevirtual print : (Ljava/lang/String;)V
    //   117: aload_3
    //   118: ldc_w '  #'
    //   121: invokevirtual print : (Ljava/lang/String;)V
    //   124: aload_3
    //   125: iload #9
    //   127: invokevirtual print : (I)V
    //   130: aload_3
    //   131: ldc_w ': '
    //   134: invokevirtual print : (Ljava/lang/String;)V
    //   137: aload_3
    //   138: aload #6
    //   140: invokevirtual println : (Ljava/lang/Object;)V
    //   143: aload #6
    //   145: ifnull -> 159
    //   148: aload #6
    //   150: aload #5
    //   152: aload_2
    //   153: aload_3
    //   154: aload #4
    //   156: invokevirtual dump : (Ljava/lang/String;Ljava/io/FileDescriptor;Ljava/io/PrintWriter;[Ljava/lang/String;)V
    //   159: iinc #9, 1
    //   162: goto -> 91
    //   165: aload_0
    //   166: getfield mAdded : Ljava/util/ArrayList;
    //   169: invokevirtual size : ()I
    //   172: istore #8
    //   174: iload #8
    //   176: ifle -> 255
    //   179: aload_3
    //   180: aload_1
    //   181: invokevirtual print : (Ljava/lang/String;)V
    //   184: aload_3
    //   185: ldc_w 'Added Fragments:'
    //   188: invokevirtual println : (Ljava/lang/String;)V
    //   191: iconst_0
    //   192: istore #9
    //   194: iload #9
    //   196: iload #8
    //   198: if_icmpge -> 255
    //   201: aload_0
    //   202: getfield mAdded : Ljava/util/ArrayList;
    //   205: iload #9
    //   207: invokevirtual get : (I)Ljava/lang/Object;
    //   210: checkcast android/support/v4/app/Fragment
    //   213: astore #6
    //   215: aload_3
    //   216: aload_1
    //   217: invokevirtual print : (Ljava/lang/String;)V
    //   220: aload_3
    //   221: ldc_w '  #'
    //   224: invokevirtual print : (Ljava/lang/String;)V
    //   227: aload_3
    //   228: iload #9
    //   230: invokevirtual print : (I)V
    //   233: aload_3
    //   234: ldc_w ': '
    //   237: invokevirtual print : (Ljava/lang/String;)V
    //   240: aload_3
    //   241: aload #6
    //   243: invokevirtual toString : ()Ljava/lang/String;
    //   246: invokevirtual println : (Ljava/lang/String;)V
    //   249: iinc #9, 1
    //   252: goto -> 194
    //   255: aload_0
    //   256: getfield mCreatedMenus : Ljava/util/ArrayList;
    //   259: astore #6
    //   261: aload #6
    //   263: ifnull -> 354
    //   266: aload #6
    //   268: invokevirtual size : ()I
    //   271: istore #8
    //   273: iload #8
    //   275: ifle -> 354
    //   278: aload_3
    //   279: aload_1
    //   280: invokevirtual print : (Ljava/lang/String;)V
    //   283: aload_3
    //   284: ldc_w 'Fragments Created Menus:'
    //   287: invokevirtual println : (Ljava/lang/String;)V
    //   290: iconst_0
    //   291: istore #9
    //   293: iload #9
    //   295: iload #8
    //   297: if_icmpge -> 354
    //   300: aload_0
    //   301: getfield mCreatedMenus : Ljava/util/ArrayList;
    //   304: iload #9
    //   306: invokevirtual get : (I)Ljava/lang/Object;
    //   309: checkcast android/support/v4/app/Fragment
    //   312: astore #6
    //   314: aload_3
    //   315: aload_1
    //   316: invokevirtual print : (Ljava/lang/String;)V
    //   319: aload_3
    //   320: ldc_w '  #'
    //   323: invokevirtual print : (Ljava/lang/String;)V
    //   326: aload_3
    //   327: iload #9
    //   329: invokevirtual print : (I)V
    //   332: aload_3
    //   333: ldc_w ': '
    //   336: invokevirtual print : (Ljava/lang/String;)V
    //   339: aload_3
    //   340: aload #6
    //   342: invokevirtual toString : ()Ljava/lang/String;
    //   345: invokevirtual println : (Ljava/lang/String;)V
    //   348: iinc #9, 1
    //   351: goto -> 293
    //   354: aload_0
    //   355: getfield mBackStack : Ljava/util/ArrayList;
    //   358: astore #6
    //   360: aload #6
    //   362: ifnull -> 464
    //   365: aload #6
    //   367: invokevirtual size : ()I
    //   370: istore #8
    //   372: iload #8
    //   374: ifle -> 464
    //   377: aload_3
    //   378: aload_1
    //   379: invokevirtual print : (Ljava/lang/String;)V
    //   382: aload_3
    //   383: ldc_w 'Back Stack:'
    //   386: invokevirtual println : (Ljava/lang/String;)V
    //   389: iconst_0
    //   390: istore #9
    //   392: iload #9
    //   394: iload #8
    //   396: if_icmpge -> 464
    //   399: aload_0
    //   400: getfield mBackStack : Ljava/util/ArrayList;
    //   403: iload #9
    //   405: invokevirtual get : (I)Ljava/lang/Object;
    //   408: checkcast android/support/v4/app/BackStackRecord
    //   411: astore #6
    //   413: aload_3
    //   414: aload_1
    //   415: invokevirtual print : (Ljava/lang/String;)V
    //   418: aload_3
    //   419: ldc_w '  #'
    //   422: invokevirtual print : (Ljava/lang/String;)V
    //   425: aload_3
    //   426: iload #9
    //   428: invokevirtual print : (I)V
    //   431: aload_3
    //   432: ldc_w ': '
    //   435: invokevirtual print : (Ljava/lang/String;)V
    //   438: aload_3
    //   439: aload #6
    //   441: invokevirtual toString : ()Ljava/lang/String;
    //   444: invokevirtual println : (Ljava/lang/String;)V
    //   447: aload #6
    //   449: aload #5
    //   451: aload_2
    //   452: aload_3
    //   453: aload #4
    //   455: invokevirtual dump : (Ljava/lang/String;Ljava/io/FileDescriptor;Ljava/io/PrintWriter;[Ljava/lang/String;)V
    //   458: iinc #9, 1
    //   461: goto -> 392
    //   464: aload_0
    //   465: monitorenter
    //   466: aload_0
    //   467: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   470: ifnull -> 558
    //   473: aload_0
    //   474: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   477: invokevirtual size : ()I
    //   480: istore #8
    //   482: iload #8
    //   484: ifle -> 558
    //   487: aload_3
    //   488: aload_1
    //   489: invokevirtual print : (Ljava/lang/String;)V
    //   492: aload_3
    //   493: ldc_w 'Back Stack Indices:'
    //   496: invokevirtual println : (Ljava/lang/String;)V
    //   499: iconst_0
    //   500: istore #9
    //   502: iload #9
    //   504: iload #8
    //   506: if_icmpge -> 558
    //   509: aload_0
    //   510: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   513: iload #9
    //   515: invokevirtual get : (I)Ljava/lang/Object;
    //   518: checkcast android/support/v4/app/BackStackRecord
    //   521: astore_2
    //   522: aload_3
    //   523: aload_1
    //   524: invokevirtual print : (Ljava/lang/String;)V
    //   527: aload_3
    //   528: ldc_w '  #'
    //   531: invokevirtual print : (Ljava/lang/String;)V
    //   534: aload_3
    //   535: iload #9
    //   537: invokevirtual print : (I)V
    //   540: aload_3
    //   541: ldc_w ': '
    //   544: invokevirtual print : (Ljava/lang/String;)V
    //   547: aload_3
    //   548: aload_2
    //   549: invokevirtual println : (Ljava/lang/Object;)V
    //   552: iinc #9, 1
    //   555: goto -> 502
    //   558: aload_0
    //   559: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   562: ifnull -> 601
    //   565: aload_0
    //   566: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   569: invokevirtual size : ()I
    //   572: ifle -> 601
    //   575: aload_3
    //   576: aload_1
    //   577: invokevirtual print : (Ljava/lang/String;)V
    //   580: aload_3
    //   581: ldc_w 'mAvailBackStackIndices: '
    //   584: invokevirtual print : (Ljava/lang/String;)V
    //   587: aload_3
    //   588: aload_0
    //   589: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   592: invokevirtual toArray : ()[Ljava/lang/Object;
    //   595: invokestatic toString : ([Ljava/lang/Object;)Ljava/lang/String;
    //   598: invokevirtual println : (Ljava/lang/String;)V
    //   601: aload_0
    //   602: monitorexit
    //   603: aload_0
    //   604: getfield mPendingActions : Ljava/util/ArrayList;
    //   607: astore_2
    //   608: aload_2
    //   609: ifnull -> 695
    //   612: aload_2
    //   613: invokevirtual size : ()I
    //   616: istore #8
    //   618: iload #8
    //   620: ifle -> 695
    //   623: aload_3
    //   624: aload_1
    //   625: invokevirtual print : (Ljava/lang/String;)V
    //   628: aload_3
    //   629: ldc_w 'Pending Actions:'
    //   632: invokevirtual println : (Ljava/lang/String;)V
    //   635: iload #7
    //   637: istore #9
    //   639: iload #9
    //   641: iload #8
    //   643: if_icmpge -> 695
    //   646: aload_0
    //   647: getfield mPendingActions : Ljava/util/ArrayList;
    //   650: iload #9
    //   652: invokevirtual get : (I)Ljava/lang/Object;
    //   655: checkcast android/support/v4/app/FragmentManagerImpl$OpGenerator
    //   658: astore_2
    //   659: aload_3
    //   660: aload_1
    //   661: invokevirtual print : (Ljava/lang/String;)V
    //   664: aload_3
    //   665: ldc_w '  #'
    //   668: invokevirtual print : (Ljava/lang/String;)V
    //   671: aload_3
    //   672: iload #9
    //   674: invokevirtual print : (I)V
    //   677: aload_3
    //   678: ldc_w ': '
    //   681: invokevirtual print : (Ljava/lang/String;)V
    //   684: aload_3
    //   685: aload_2
    //   686: invokevirtual println : (Ljava/lang/Object;)V
    //   689: iinc #9, 1
    //   692: goto -> 639
    //   695: aload_3
    //   696: aload_1
    //   697: invokevirtual print : (Ljava/lang/String;)V
    //   700: aload_3
    //   701: ldc_w 'FragmentManager misc state:'
    //   704: invokevirtual println : (Ljava/lang/String;)V
    //   707: aload_3
    //   708: aload_1
    //   709: invokevirtual print : (Ljava/lang/String;)V
    //   712: aload_3
    //   713: ldc_w '  mHost='
    //   716: invokevirtual print : (Ljava/lang/String;)V
    //   719: aload_3
    //   720: aload_0
    //   721: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   724: invokevirtual println : (Ljava/lang/Object;)V
    //   727: aload_3
    //   728: aload_1
    //   729: invokevirtual print : (Ljava/lang/String;)V
    //   732: aload_3
    //   733: ldc_w '  mContainer='
    //   736: invokevirtual print : (Ljava/lang/String;)V
    //   739: aload_3
    //   740: aload_0
    //   741: getfield mContainer : Landroid/support/v4/app/FragmentContainer;
    //   744: invokevirtual println : (Ljava/lang/Object;)V
    //   747: aload_0
    //   748: getfield mParent : Landroid/support/v4/app/Fragment;
    //   751: ifnull -> 774
    //   754: aload_3
    //   755: aload_1
    //   756: invokevirtual print : (Ljava/lang/String;)V
    //   759: aload_3
    //   760: ldc_w '  mParent='
    //   763: invokevirtual print : (Ljava/lang/String;)V
    //   766: aload_3
    //   767: aload_0
    //   768: getfield mParent : Landroid/support/v4/app/Fragment;
    //   771: invokevirtual println : (Ljava/lang/Object;)V
    //   774: aload_3
    //   775: aload_1
    //   776: invokevirtual print : (Ljava/lang/String;)V
    //   779: aload_3
    //   780: ldc_w '  mCurState='
    //   783: invokevirtual print : (Ljava/lang/String;)V
    //   786: aload_3
    //   787: aload_0
    //   788: getfield mCurState : I
    //   791: invokevirtual print : (I)V
    //   794: aload_3
    //   795: ldc_w ' mStateSaved='
    //   798: invokevirtual print : (Ljava/lang/String;)V
    //   801: aload_3
    //   802: aload_0
    //   803: getfield mStateSaved : Z
    //   806: invokevirtual print : (Z)V
    //   809: aload_3
    //   810: ldc_w ' mStopped='
    //   813: invokevirtual print : (Ljava/lang/String;)V
    //   816: aload_3
    //   817: aload_0
    //   818: getfield mStopped : Z
    //   821: invokevirtual print : (Z)V
    //   824: aload_3
    //   825: ldc_w ' mDestroyed='
    //   828: invokevirtual print : (Ljava/lang/String;)V
    //   831: aload_3
    //   832: aload_0
    //   833: getfield mDestroyed : Z
    //   836: invokevirtual println : (Z)V
    //   839: aload_0
    //   840: getfield mNeedMenuInvalidate : Z
    //   843: ifeq -> 866
    //   846: aload_3
    //   847: aload_1
    //   848: invokevirtual print : (Ljava/lang/String;)V
    //   851: aload_3
    //   852: ldc_w '  mNeedMenuInvalidate='
    //   855: invokevirtual print : (Ljava/lang/String;)V
    //   858: aload_3
    //   859: aload_0
    //   860: getfield mNeedMenuInvalidate : Z
    //   863: invokevirtual println : (Z)V
    //   866: aload_0
    //   867: getfield mNoTransactionsBecause : Ljava/lang/String;
    //   870: ifnull -> 893
    //   873: aload_3
    //   874: aload_1
    //   875: invokevirtual print : (Ljava/lang/String;)V
    //   878: aload_3
    //   879: ldc_w '  mNoTransactionsBecause='
    //   882: invokevirtual print : (Ljava/lang/String;)V
    //   885: aload_3
    //   886: aload_0
    //   887: getfield mNoTransactionsBecause : Ljava/lang/String;
    //   890: invokevirtual println : (Ljava/lang/String;)V
    //   893: return
    //   894: astore_1
    //   895: aload_0
    //   896: monitorexit
    //   897: aload_1
    //   898: athrow
    // Exception table:
    //   from	to	target	type
    //   466	482	894	finally
    //   487	499	894	finally
    //   509	552	894	finally
    //   558	601	894	finally
    //   601	603	894	finally
    //   895	897	894	finally
  }
  
  public void enqueueAction(OpGenerator paramOpGenerator, boolean paramBoolean) {
    // Byte code:
    //   0: iload_2
    //   1: ifne -> 8
    //   4: aload_0
    //   5: invokespecial checkStateLoss : ()V
    //   8: aload_0
    //   9: monitorenter
    //   10: aload_0
    //   11: getfield mDestroyed : Z
    //   14: ifne -> 63
    //   17: aload_0
    //   18: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   21: ifnonnull -> 27
    //   24: goto -> 63
    //   27: aload_0
    //   28: getfield mPendingActions : Ljava/util/ArrayList;
    //   31: ifnonnull -> 47
    //   34: new java/util/ArrayList
    //   37: astore_3
    //   38: aload_3
    //   39: invokespecial <init> : ()V
    //   42: aload_0
    //   43: aload_3
    //   44: putfield mPendingActions : Ljava/util/ArrayList;
    //   47: aload_0
    //   48: getfield mPendingActions : Ljava/util/ArrayList;
    //   51: aload_1
    //   52: invokevirtual add : (Ljava/lang/Object;)Z
    //   55: pop
    //   56: aload_0
    //   57: invokespecial scheduleCommit : ()V
    //   60: aload_0
    //   61: monitorexit
    //   62: return
    //   63: iload_2
    //   64: ifeq -> 70
    //   67: aload_0
    //   68: monitorexit
    //   69: return
    //   70: new java/lang/IllegalStateException
    //   73: astore_1
    //   74: aload_1
    //   75: ldc_w 'Activity has been destroyed'
    //   78: invokespecial <init> : (Ljava/lang/String;)V
    //   81: aload_1
    //   82: athrow
    //   83: astore_1
    //   84: aload_0
    //   85: monitorexit
    //   86: aload_1
    //   87: athrow
    // Exception table:
    //   from	to	target	type
    //   10	24	83	finally
    //   27	47	83	finally
    //   47	62	83	finally
    //   67	69	83	finally
    //   70	83	83	finally
    //   84	86	83	finally
  }
  
  void ensureInflatedFragmentView(Fragment paramFragment) {
    if (paramFragment.mFromLayout && !paramFragment.mPerformedCreateView) {
      paramFragment.mView = paramFragment.performCreateView(paramFragment.performGetLayoutInflater(paramFragment.mSavedFragmentState), null, paramFragment.mSavedFragmentState);
      if (paramFragment.mView != null) {
        paramFragment.mInnerView = paramFragment.mView;
        paramFragment.mView.setSaveFromParentEnabled(false);
        if (paramFragment.mHidden)
          paramFragment.mView.setVisibility(8); 
        paramFragment.onViewCreated(paramFragment.mView, paramFragment.mSavedFragmentState);
        dispatchOnFragmentViewCreated(paramFragment, paramFragment.mView, paramFragment.mSavedFragmentState, false);
      } else {
        paramFragment.mInnerView = null;
      } 
    } 
  }
  
  public boolean execPendingActions() {
    ensureExecReady(true);
    boolean bool = false;
    while (generateOpsForPendingActions(this.mTmpRecords, this.mTmpIsPop)) {
      this.mExecutingActions = true;
      try {
        removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
        cleanupExec();
      } finally {
        cleanupExec();
      } 
    } 
    doPendingDeferredStart();
    burpActive();
    return bool;
  }
  
  public void execSingleAction(OpGenerator paramOpGenerator, boolean paramBoolean) {
    if (paramBoolean && (this.mHost == null || this.mDestroyed))
      return; 
    ensureExecReady(paramBoolean);
    if (paramOpGenerator.generateOps(this.mTmpRecords, this.mTmpIsPop)) {
      this.mExecutingActions = true;
      try {
        removeRedundantOperationsAndExecute(this.mTmpRecords, this.mTmpIsPop);
      } finally {
        cleanupExec();
      } 
    } 
    doPendingDeferredStart();
    burpActive();
  }
  
  public boolean executePendingTransactions() {
    boolean bool = execPendingActions();
    forcePostponedTransactions();
    return bool;
  }
  
  public Fragment findFragmentById(int paramInt) {
    int i;
    for (i = this.mAdded.size() - 1; i >= 0; i--) {
      Fragment fragment = this.mAdded.get(i);
      if (fragment != null && fragment.mFragmentId == paramInt)
        return fragment; 
    } 
    SparseArray<Fragment> sparseArray = this.mActive;
    if (sparseArray != null)
      for (i = sparseArray.size() - 1; i >= 0; i--) {
        Fragment fragment = (Fragment)this.mActive.valueAt(i);
        if (fragment != null && fragment.mFragmentId == paramInt)
          return fragment; 
      }  
    return null;
  }
  
  public Fragment findFragmentByTag(String paramString) {
    if (paramString != null)
      for (int i = this.mAdded.size() - 1; i >= 0; i--) {
        Fragment fragment = this.mAdded.get(i);
        if (fragment != null && paramString.equals(fragment.mTag))
          return fragment; 
      }  
    SparseArray<Fragment> sparseArray = this.mActive;
    if (sparseArray != null && paramString != null)
      for (int i = sparseArray.size() - 1; i >= 0; i--) {
        Fragment fragment = (Fragment)this.mActive.valueAt(i);
        if (fragment != null && paramString.equals(fragment.mTag))
          return fragment; 
      }  
    return null;
  }
  
  public Fragment findFragmentByWho(String paramString) {
    SparseArray<Fragment> sparseArray = this.mActive;
    if (sparseArray != null && paramString != null)
      for (int i = sparseArray.size() - 1; i >= 0; i--) {
        Fragment fragment = (Fragment)this.mActive.valueAt(i);
        if (fragment != null) {
          fragment = fragment.findFragmentByWho(paramString);
          if (fragment != null)
            return fragment; 
        } 
      }  
    return null;
  }
  
  public void freeBackStackIndex(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   6: iload_1
    //   7: aconst_null
    //   8: invokevirtual set : (ILjava/lang/Object;)Ljava/lang/Object;
    //   11: pop
    //   12: aload_0
    //   13: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   16: ifnonnull -> 32
    //   19: new java/util/ArrayList
    //   22: astore_2
    //   23: aload_2
    //   24: invokespecial <init> : ()V
    //   27: aload_0
    //   28: aload_2
    //   29: putfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   32: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   35: ifeq -> 70
    //   38: new java/lang/StringBuilder
    //   41: astore_2
    //   42: aload_2
    //   43: invokespecial <init> : ()V
    //   46: aload_2
    //   47: ldc_w 'Freeing back stack index '
    //   50: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   53: pop
    //   54: aload_2
    //   55: iload_1
    //   56: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   59: pop
    //   60: ldc 'FragmentManager'
    //   62: aload_2
    //   63: invokevirtual toString : ()Ljava/lang/String;
    //   66: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   69: pop
    //   70: aload_0
    //   71: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   74: iload_1
    //   75: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   78: invokevirtual add : (Ljava/lang/Object;)Z
    //   81: pop
    //   82: aload_0
    //   83: monitorexit
    //   84: return
    //   85: astore_2
    //   86: aload_0
    //   87: monitorexit
    //   88: aload_2
    //   89: athrow
    // Exception table:
    //   from	to	target	type
    //   2	32	85	finally
    //   32	70	85	finally
    //   70	84	85	finally
    //   86	88	85	finally
  }
  
  int getActiveFragmentCount() {
    SparseArray<Fragment> sparseArray = this.mActive;
    return (sparseArray == null) ? 0 : sparseArray.size();
  }
  
  List<Fragment> getActiveFragments() {
    SparseArray<Fragment> sparseArray = this.mActive;
    if (sparseArray == null)
      return null; 
    int i = sparseArray.size();
    ArrayList<Object> arrayList = new ArrayList(i);
    for (byte b = 0; b < i; b++)
      arrayList.add(this.mActive.valueAt(b)); 
    return arrayList;
  }
  
  public FragmentManager.BackStackEntry getBackStackEntryAt(int paramInt) {
    return this.mBackStack.get(paramInt);
  }
  
  public int getBackStackEntryCount() {
    boolean bool;
    ArrayList<BackStackRecord> arrayList = this.mBackStack;
    if (arrayList != null) {
      bool = arrayList.size();
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public Fragment getFragment(Bundle paramBundle, String paramString) {
    int i = paramBundle.getInt(paramString, -1);
    if (i == -1)
      return null; 
    Fragment fragment = (Fragment)this.mActive.get(i);
    if (fragment == null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Fragment no longer exists for key ");
      stringBuilder.append(paramString);
      stringBuilder.append(": index ");
      stringBuilder.append(i);
      throwException(new IllegalStateException(stringBuilder.toString()));
    } 
    return fragment;
  }
  
  public List<Fragment> getFragments() {
    if (this.mAdded.isEmpty())
      return Collections.EMPTY_LIST; 
    synchronized (this.mAdded) {
      return (List)this.mAdded.clone();
    } 
  }
  
  LayoutInflater.Factory2 getLayoutInflaterFactory() {
    return this;
  }
  
  public Fragment getPrimaryNavigationFragment() {
    return this.mPrimaryNav;
  }
  
  public void hideFragment(Fragment paramFragment) {
    if (DEBUG) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("hide: ");
      stringBuilder.append(paramFragment);
      Log.v("FragmentManager", stringBuilder.toString());
    } 
    if (!paramFragment.mHidden) {
      paramFragment.mHidden = true;
      paramFragment.mHiddenChanged = true ^ paramFragment.mHiddenChanged;
    } 
  }
  
  public boolean isDestroyed() {
    return this.mDestroyed;
  }
  
  boolean isStateAtLeast(int paramInt) {
    boolean bool;
    if (this.mCurState >= paramInt) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isStateSaved() {
    return (this.mStateSaved || this.mStopped);
  }
  
  AnimationOrAnimator loadAnimation(Fragment paramFragment, int paramInt1, boolean paramBoolean, int paramInt2) {
    int i = paramFragment.getNextAnim();
    Animation animation = paramFragment.onCreateAnimation(paramInt1, paramBoolean, i);
    if (animation != null)
      return new AnimationOrAnimator(animation); 
    Animator animator = paramFragment.onCreateAnimator(paramInt1, paramBoolean, i);
    if (animator != null)
      return new AnimationOrAnimator(animator); 
    if (i != 0) {
      boolean bool = "anim".equals(this.mHost.getContext().getResources().getResourceTypeName(i));
      boolean bool1 = false;
      boolean bool2 = bool1;
      if (bool)
        try {
          Animation animation1 = AnimationUtils.loadAnimation(this.mHost.getContext(), i);
          if (animation1 != null)
            return new AnimationOrAnimator(animation1); 
          bool2 = true;
        } catch (android.content.res.Resources.NotFoundException notFoundException) {
          throw notFoundException;
        } catch (RuntimeException runtimeException) {
          bool2 = bool1;
        }  
      if (!bool2)
        try {
          animator = AnimatorInflater.loadAnimator(this.mHost.getContext(), i);
          if (animator != null)
            return new AnimationOrAnimator(animator); 
        } catch (RuntimeException runtimeException) {
          Animation animation1;
          if (!bool) {
            animation1 = AnimationUtils.loadAnimation(this.mHost.getContext(), i);
            if (animation1 != null)
              return new AnimationOrAnimator(animation1); 
          } else {
            throw animation1;
          } 
        }  
    } 
    if (paramInt1 == 0)
      return null; 
    paramInt1 = transitToStyleIndex(paramInt1, paramBoolean);
    if (paramInt1 < 0)
      return null; 
    switch (paramInt1) {
      default:
        paramInt1 = paramInt2;
        if (paramInt2 == 0) {
          paramInt1 = paramInt2;
          if (this.mHost.onHasWindowAnimations())
            paramInt1 = this.mHost.onGetWindowAnimations(); 
        } 
        break;
      case 6:
        return makeFadeAnimation(this.mHost.getContext(), 1.0F, 0.0F);
      case 5:
        return makeFadeAnimation(this.mHost.getContext(), 0.0F, 1.0F);
      case 4:
        return makeOpenCloseAnimation(this.mHost.getContext(), 1.0F, 1.075F, 1.0F, 0.0F);
      case 3:
        return makeOpenCloseAnimation(this.mHost.getContext(), 0.975F, 1.0F, 0.0F, 1.0F);
      case 2:
        return makeOpenCloseAnimation(this.mHost.getContext(), 1.0F, 0.975F, 1.0F, 0.0F);
      case 1:
        return makeOpenCloseAnimation(this.mHost.getContext(), 1.125F, 1.0F, 0.0F, 1.0F);
    } 
    if (paramInt1 == 0);
    return null;
  }
  
  void makeActive(Fragment paramFragment) {
    if (paramFragment.mIndex >= 0)
      return; 
    int i = this.mNextFragmentIndex;
    this.mNextFragmentIndex = i + 1;
    paramFragment.setIndex(i, this.mParent);
    if (this.mActive == null)
      this.mActive = new SparseArray(); 
    this.mActive.put(paramFragment.mIndex, paramFragment);
    if (DEBUG) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Allocated fragment index ");
      stringBuilder.append(paramFragment);
      Log.v("FragmentManager", stringBuilder.toString());
    } 
  }
  
  void makeInactive(Fragment paramFragment) {
    if (paramFragment.mIndex < 0)
      return; 
    if (DEBUG) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Freeing fragment index ");
      stringBuilder.append(paramFragment);
      Log.v("FragmentManager", stringBuilder.toString());
    } 
    this.mActive.put(paramFragment.mIndex, null);
    paramFragment.initState();
  }
  
  void moveFragmentToExpectedState(Fragment paramFragment) {
    if (paramFragment == null)
      return; 
    int i = this.mCurState;
    int j = i;
    if (paramFragment.mRemoving)
      if (paramFragment.isInBackStack()) {
        j = Math.min(i, 1);
      } else {
        j = Math.min(i, 0);
      }  
    moveToState(paramFragment, j, paramFragment.getNextTransition(), paramFragment.getNextTransitionStyle(), false);
    if (paramFragment.mView != null) {
      Fragment fragment = findFragmentUnder(paramFragment);
      if (fragment != null) {
        View view = fragment.mView;
        ViewGroup viewGroup = paramFragment.mContainer;
        j = viewGroup.indexOfChild(view);
        i = viewGroup.indexOfChild(paramFragment.mView);
        if (i < j) {
          viewGroup.removeViewAt(i);
          viewGroup.addView(paramFragment.mView, j);
        } 
      } 
      if (paramFragment.mIsNewlyAdded && paramFragment.mContainer != null) {
        if (paramFragment.mPostponedAlpha > 0.0F)
          paramFragment.mView.setAlpha(paramFragment.mPostponedAlpha); 
        paramFragment.mPostponedAlpha = 0.0F;
        paramFragment.mIsNewlyAdded = false;
        AnimationOrAnimator animationOrAnimator = loadAnimation(paramFragment, paramFragment.getNextTransition(), true, paramFragment.getNextTransitionStyle());
        if (animationOrAnimator != null) {
          setHWLayerAnimListenerIfAlpha(paramFragment.mView, animationOrAnimator);
          if (animationOrAnimator.animation != null) {
            paramFragment.mView.startAnimation(animationOrAnimator.animation);
          } else {
            animationOrAnimator.animator.setTarget(paramFragment.mView);
            animationOrAnimator.animator.start();
          } 
        } 
      } 
    } 
    if (paramFragment.mHiddenChanged)
      completeShowHideFragment(paramFragment); 
  }
  
  void moveToState(int paramInt, boolean paramBoolean) {
    if (this.mHost != null || paramInt == 0) {
      if (!paramBoolean && paramInt == this.mCurState)
        return; 
      this.mCurState = paramInt;
      if (this.mActive != null) {
        int i = this.mAdded.size();
        for (paramInt = 0; paramInt < i; paramInt++)
          moveFragmentToExpectedState(this.mAdded.get(paramInt)); 
        i = this.mActive.size();
        for (paramInt = 0; paramInt < i; paramInt++) {
          Fragment fragment = (Fragment)this.mActive.valueAt(paramInt);
          if (fragment != null && (fragment.mRemoving || fragment.mDetached) && !fragment.mIsNewlyAdded)
            moveFragmentToExpectedState(fragment); 
        } 
        startPendingDeferredFragments();
        if (this.mNeedMenuInvalidate) {
          FragmentHostCallback fragmentHostCallback = this.mHost;
          if (fragmentHostCallback != null && this.mCurState == 5) {
            fragmentHostCallback.onSupportInvalidateOptionsMenu();
            this.mNeedMenuInvalidate = false;
          } 
        } 
      } 
      return;
    } 
    throw new IllegalStateException("No activity");
  }
  
  void moveToState(Fragment paramFragment) {
    moveToState(paramFragment, this.mCurState, 0, 0, false);
  }
  
  void moveToState(Fragment paramFragment, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
    // Byte code:
    //   0: aload_1
    //   1: getfield mAdded : Z
    //   4: istore #6
    //   6: iconst_1
    //   7: istore #7
    //   9: iconst_1
    //   10: istore #8
    //   12: iload #6
    //   14: ifeq -> 30
    //   17: aload_1
    //   18: getfield mDetached : Z
    //   21: ifeq -> 27
    //   24: goto -> 30
    //   27: goto -> 44
    //   30: iload_2
    //   31: istore #9
    //   33: iload #9
    //   35: istore_2
    //   36: iload #9
    //   38: iconst_1
    //   39: if_icmple -> 44
    //   42: iconst_1
    //   43: istore_2
    //   44: iload_2
    //   45: istore #9
    //   47: aload_1
    //   48: getfield mRemoving : Z
    //   51: ifeq -> 91
    //   54: iload_2
    //   55: istore #9
    //   57: iload_2
    //   58: aload_1
    //   59: getfield mState : I
    //   62: if_icmple -> 91
    //   65: aload_1
    //   66: getfield mState : I
    //   69: ifne -> 85
    //   72: aload_1
    //   73: invokevirtual isInBackStack : ()Z
    //   76: ifeq -> 85
    //   79: iconst_1
    //   80: istore #9
    //   82: goto -> 91
    //   85: aload_1
    //   86: getfield mState : I
    //   89: istore #9
    //   91: aload_1
    //   92: getfield mDeferStart : Z
    //   95: ifeq -> 117
    //   98: aload_1
    //   99: getfield mState : I
    //   102: iconst_4
    //   103: if_icmpge -> 117
    //   106: iload #9
    //   108: iconst_3
    //   109: if_icmple -> 117
    //   112: iconst_3
    //   113: istore_2
    //   114: goto -> 120
    //   117: iload #9
    //   119: istore_2
    //   120: aload_1
    //   121: getfield mState : I
    //   124: iload_2
    //   125: if_icmpgt -> 1401
    //   128: aload_1
    //   129: getfield mFromLayout : Z
    //   132: ifeq -> 143
    //   135: aload_1
    //   136: getfield mInLayout : Z
    //   139: ifne -> 143
    //   142: return
    //   143: aload_1
    //   144: invokevirtual getAnimatingAway : ()Landroid/view/View;
    //   147: ifnonnull -> 157
    //   150: aload_1
    //   151: invokevirtual getAnimator : ()Landroid/animation/Animator;
    //   154: ifnull -> 179
    //   157: aload_1
    //   158: aconst_null
    //   159: invokevirtual setAnimatingAway : (Landroid/view/View;)V
    //   162: aload_1
    //   163: aconst_null
    //   164: invokevirtual setAnimator : (Landroid/animation/Animator;)V
    //   167: aload_0
    //   168: aload_1
    //   169: aload_1
    //   170: invokevirtual getStateAfterAnimating : ()I
    //   173: iconst_0
    //   174: iconst_0
    //   175: iconst_1
    //   176: invokevirtual moveToState : (Landroid/support/v4/app/Fragment;IIIZ)V
    //   179: aload_1
    //   180: getfield mState : I
    //   183: istore #9
    //   185: iload #9
    //   187: ifeq -> 233
    //   190: iload_2
    //   191: istore #4
    //   193: iload #9
    //   195: iconst_1
    //   196: if_icmpeq -> 776
    //   199: iload_2
    //   200: istore_3
    //   201: iload #9
    //   203: iconst_2
    //   204: if_icmpeq -> 1242
    //   207: iload_2
    //   208: istore #4
    //   210: iload #9
    //   212: iconst_3
    //   213: if_icmpeq -> 1258
    //   216: iload_2
    //   217: istore_3
    //   218: iload #9
    //   220: iconst_4
    //   221: if_icmpeq -> 230
    //   224: iload_2
    //   225: istore #9
    //   227: goto -> 2054
    //   230: goto -> 1325
    //   233: iload_2
    //   234: istore #4
    //   236: iload_2
    //   237: ifle -> 776
    //   240: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   243: ifeq -> 282
    //   246: new java/lang/StringBuilder
    //   249: dup
    //   250: invokespecial <init> : ()V
    //   253: astore #10
    //   255: aload #10
    //   257: ldc_w 'moveto CREATED: '
    //   260: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   263: pop
    //   264: aload #10
    //   266: aload_1
    //   267: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   270: pop
    //   271: ldc 'FragmentManager'
    //   273: aload #10
    //   275: invokevirtual toString : ()Ljava/lang/String;
    //   278: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   281: pop
    //   282: iload_2
    //   283: istore #4
    //   285: aload_1
    //   286: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   289: ifnull -> 423
    //   292: aload_1
    //   293: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   296: aload_0
    //   297: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   300: invokevirtual getContext : ()Landroid/content/Context;
    //   303: invokevirtual getClassLoader : ()Ljava/lang/ClassLoader;
    //   306: invokevirtual setClassLoader : (Ljava/lang/ClassLoader;)V
    //   309: aload_1
    //   310: aload_1
    //   311: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   314: ldc 'android:view_state'
    //   316: invokevirtual getSparseParcelableArray : (Ljava/lang/String;)Landroid/util/SparseArray;
    //   319: putfield mSavedViewState : Landroid/util/SparseArray;
    //   322: aload_1
    //   323: aload_0
    //   324: aload_1
    //   325: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   328: ldc 'android:target_state'
    //   330: invokevirtual getFragment : (Landroid/os/Bundle;Ljava/lang/String;)Landroid/support/v4/app/Fragment;
    //   333: putfield mTarget : Landroid/support/v4/app/Fragment;
    //   336: aload_1
    //   337: getfield mTarget : Landroid/support/v4/app/Fragment;
    //   340: ifnull -> 357
    //   343: aload_1
    //   344: aload_1
    //   345: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   348: ldc 'android:target_req_state'
    //   350: iconst_0
    //   351: invokevirtual getInt : (Ljava/lang/String;I)I
    //   354: putfield mTargetRequestCode : I
    //   357: aload_1
    //   358: getfield mSavedUserVisibleHint : Ljava/lang/Boolean;
    //   361: ifnull -> 383
    //   364: aload_1
    //   365: aload_1
    //   366: getfield mSavedUserVisibleHint : Ljava/lang/Boolean;
    //   369: invokevirtual booleanValue : ()Z
    //   372: putfield mUserVisibleHint : Z
    //   375: aload_1
    //   376: aconst_null
    //   377: putfield mSavedUserVisibleHint : Ljava/lang/Boolean;
    //   380: goto -> 397
    //   383: aload_1
    //   384: aload_1
    //   385: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   388: ldc 'android:user_visible_hint'
    //   390: iconst_1
    //   391: invokevirtual getBoolean : (Ljava/lang/String;Z)Z
    //   394: putfield mUserVisibleHint : Z
    //   397: iload_2
    //   398: istore #4
    //   400: aload_1
    //   401: getfield mUserVisibleHint : Z
    //   404: ifne -> 423
    //   407: aload_1
    //   408: iconst_1
    //   409: putfield mDeferStart : Z
    //   412: iload_2
    //   413: istore #4
    //   415: iload_2
    //   416: iconst_3
    //   417: if_icmple -> 423
    //   420: iconst_3
    //   421: istore #4
    //   423: aload_1
    //   424: aload_0
    //   425: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   428: putfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   431: aload_1
    //   432: aload_0
    //   433: getfield mParent : Landroid/support/v4/app/Fragment;
    //   436: putfield mParentFragment : Landroid/support/v4/app/Fragment;
    //   439: aload_0
    //   440: getfield mParent : Landroid/support/v4/app/Fragment;
    //   443: astore #10
    //   445: aload #10
    //   447: ifnull -> 460
    //   450: aload #10
    //   452: getfield mChildFragmentManager : Landroid/support/v4/app/FragmentManagerImpl;
    //   455: astore #10
    //   457: goto -> 469
    //   460: aload_0
    //   461: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   464: invokevirtual getFragmentManagerImpl : ()Landroid/support/v4/app/FragmentManagerImpl;
    //   467: astore #10
    //   469: aload_1
    //   470: aload #10
    //   472: putfield mFragmentManager : Landroid/support/v4/app/FragmentManagerImpl;
    //   475: aload_1
    //   476: getfield mTarget : Landroid/support/v4/app/Fragment;
    //   479: ifnull -> 595
    //   482: aload_0
    //   483: getfield mActive : Landroid/util/SparseArray;
    //   486: aload_1
    //   487: getfield mTarget : Landroid/support/v4/app/Fragment;
    //   490: getfield mIndex : I
    //   493: invokevirtual get : (I)Ljava/lang/Object;
    //   496: aload_1
    //   497: getfield mTarget : Landroid/support/v4/app/Fragment;
    //   500: if_acmpne -> 529
    //   503: aload_1
    //   504: getfield mTarget : Landroid/support/v4/app/Fragment;
    //   507: getfield mState : I
    //   510: iconst_1
    //   511: if_icmpge -> 595
    //   514: aload_0
    //   515: aload_1
    //   516: getfield mTarget : Landroid/support/v4/app/Fragment;
    //   519: iconst_1
    //   520: iconst_0
    //   521: iconst_0
    //   522: iconst_1
    //   523: invokevirtual moveToState : (Landroid/support/v4/app/Fragment;IIIZ)V
    //   526: goto -> 595
    //   529: new java/lang/StringBuilder
    //   532: dup
    //   533: invokespecial <init> : ()V
    //   536: astore #10
    //   538: aload #10
    //   540: ldc_w 'Fragment '
    //   543: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   546: pop
    //   547: aload #10
    //   549: aload_1
    //   550: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   553: pop
    //   554: aload #10
    //   556: ldc_w ' declared target fragment '
    //   559: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   562: pop
    //   563: aload #10
    //   565: aload_1
    //   566: getfield mTarget : Landroid/support/v4/app/Fragment;
    //   569: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   572: pop
    //   573: aload #10
    //   575: ldc_w ' that does not belong to this FragmentManager!'
    //   578: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   581: pop
    //   582: new java/lang/IllegalStateException
    //   585: dup
    //   586: aload #10
    //   588: invokevirtual toString : ()Ljava/lang/String;
    //   591: invokespecial <init> : (Ljava/lang/String;)V
    //   594: athrow
    //   595: aload_0
    //   596: aload_1
    //   597: aload_0
    //   598: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   601: invokevirtual getContext : ()Landroid/content/Context;
    //   604: iconst_0
    //   605: invokevirtual dispatchOnFragmentPreAttached : (Landroid/support/v4/app/Fragment;Landroid/content/Context;Z)V
    //   608: aload_1
    //   609: iconst_0
    //   610: putfield mCalled : Z
    //   613: aload_1
    //   614: aload_0
    //   615: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   618: invokevirtual getContext : ()Landroid/content/Context;
    //   621: invokevirtual onAttach : (Landroid/content/Context;)V
    //   624: aload_1
    //   625: getfield mCalled : Z
    //   628: ifeq -> 729
    //   631: aload_1
    //   632: getfield mParentFragment : Landroid/support/v4/app/Fragment;
    //   635: ifnonnull -> 649
    //   638: aload_0
    //   639: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   642: aload_1
    //   643: invokevirtual onAttachFragment : (Landroid/support/v4/app/Fragment;)V
    //   646: goto -> 657
    //   649: aload_1
    //   650: getfield mParentFragment : Landroid/support/v4/app/Fragment;
    //   653: aload_1
    //   654: invokevirtual onAttachFragment : (Landroid/support/v4/app/Fragment;)V
    //   657: aload_0
    //   658: aload_1
    //   659: aload_0
    //   660: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   663: invokevirtual getContext : ()Landroid/content/Context;
    //   666: iconst_0
    //   667: invokevirtual dispatchOnFragmentAttached : (Landroid/support/v4/app/Fragment;Landroid/content/Context;Z)V
    //   670: aload_1
    //   671: getfield mIsCreated : Z
    //   674: ifne -> 708
    //   677: aload_0
    //   678: aload_1
    //   679: aload_1
    //   680: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   683: iconst_0
    //   684: invokevirtual dispatchOnFragmentPreCreated : (Landroid/support/v4/app/Fragment;Landroid/os/Bundle;Z)V
    //   687: aload_1
    //   688: aload_1
    //   689: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   692: invokevirtual performCreate : (Landroid/os/Bundle;)V
    //   695: aload_0
    //   696: aload_1
    //   697: aload_1
    //   698: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   701: iconst_0
    //   702: invokevirtual dispatchOnFragmentCreated : (Landroid/support/v4/app/Fragment;Landroid/os/Bundle;Z)V
    //   705: goto -> 721
    //   708: aload_1
    //   709: aload_1
    //   710: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   713: invokevirtual restoreChildFragmentState : (Landroid/os/Bundle;)V
    //   716: aload_1
    //   717: iconst_1
    //   718: putfield mState : I
    //   721: aload_1
    //   722: iconst_0
    //   723: putfield mRetaining : Z
    //   726: goto -> 776
    //   729: new java/lang/StringBuilder
    //   732: dup
    //   733: invokespecial <init> : ()V
    //   736: astore #10
    //   738: aload #10
    //   740: ldc_w 'Fragment '
    //   743: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   746: pop
    //   747: aload #10
    //   749: aload_1
    //   750: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   753: pop
    //   754: aload #10
    //   756: ldc_w ' did not call through to super.onAttach()'
    //   759: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   762: pop
    //   763: new android/support/v4/app/SuperNotCalledException
    //   766: dup
    //   767: aload #10
    //   769: invokevirtual toString : ()Ljava/lang/String;
    //   772: invokespecial <init> : (Ljava/lang/String;)V
    //   775: athrow
    //   776: aload_0
    //   777: aload_1
    //   778: invokevirtual ensureInflatedFragmentView : (Landroid/support/v4/app/Fragment;)V
    //   781: iload #4
    //   783: istore_3
    //   784: iload #4
    //   786: iconst_1
    //   787: if_icmple -> 1242
    //   790: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   793: ifeq -> 832
    //   796: new java/lang/StringBuilder
    //   799: dup
    //   800: invokespecial <init> : ()V
    //   803: astore #10
    //   805: aload #10
    //   807: ldc_w 'moveto ACTIVITY_CREATED: '
    //   810: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   813: pop
    //   814: aload #10
    //   816: aload_1
    //   817: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   820: pop
    //   821: ldc 'FragmentManager'
    //   823: aload #10
    //   825: invokevirtual toString : ()Ljava/lang/String;
    //   828: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   831: pop
    //   832: aload_1
    //   833: getfield mFromLayout : Z
    //   836: ifne -> 1201
    //   839: aload_1
    //   840: getfield mContainerId : I
    //   843: ifeq -> 1050
    //   846: aload_1
    //   847: getfield mContainerId : I
    //   850: iconst_m1
    //   851: if_icmpne -> 904
    //   854: new java/lang/StringBuilder
    //   857: dup
    //   858: invokespecial <init> : ()V
    //   861: astore #10
    //   863: aload #10
    //   865: ldc_w 'Cannot create fragment '
    //   868: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   871: pop
    //   872: aload #10
    //   874: aload_1
    //   875: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   878: pop
    //   879: aload #10
    //   881: ldc_w ' for a container view with no id'
    //   884: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   887: pop
    //   888: aload_0
    //   889: new java/lang/IllegalArgumentException
    //   892: dup
    //   893: aload #10
    //   895: invokevirtual toString : ()Ljava/lang/String;
    //   898: invokespecial <init> : (Ljava/lang/String;)V
    //   901: invokespecial throwException : (Ljava/lang/RuntimeException;)V
    //   904: aload_0
    //   905: getfield mContainer : Landroid/support/v4/app/FragmentContainer;
    //   908: aload_1
    //   909: getfield mContainerId : I
    //   912: invokevirtual onFindViewById : (I)Landroid/view/View;
    //   915: checkcast android/view/ViewGroup
    //   918: astore #11
    //   920: aload #11
    //   922: astore #10
    //   924: aload #11
    //   926: ifnonnull -> 1053
    //   929: aload #11
    //   931: astore #10
    //   933: aload_1
    //   934: getfield mRestored : Z
    //   937: ifne -> 1053
    //   940: aload_1
    //   941: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   944: aload_1
    //   945: getfield mContainerId : I
    //   948: invokevirtual getResourceName : (I)Ljava/lang/String;
    //   951: astore #10
    //   953: goto -> 963
    //   956: astore #10
    //   958: ldc_w 'unknown'
    //   961: astore #10
    //   963: new java/lang/StringBuilder
    //   966: dup
    //   967: invokespecial <init> : ()V
    //   970: astore #12
    //   972: aload #12
    //   974: ldc_w 'No view found for id 0x'
    //   977: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   980: pop
    //   981: aload #12
    //   983: aload_1
    //   984: getfield mContainerId : I
    //   987: invokestatic toHexString : (I)Ljava/lang/String;
    //   990: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   993: pop
    //   994: aload #12
    //   996: ldc_w ' ('
    //   999: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1002: pop
    //   1003: aload #12
    //   1005: aload #10
    //   1007: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1010: pop
    //   1011: aload #12
    //   1013: ldc_w ') for fragment '
    //   1016: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1019: pop
    //   1020: aload #12
    //   1022: aload_1
    //   1023: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1026: pop
    //   1027: aload_0
    //   1028: new java/lang/IllegalArgumentException
    //   1031: dup
    //   1032: aload #12
    //   1034: invokevirtual toString : ()Ljava/lang/String;
    //   1037: invokespecial <init> : (Ljava/lang/String;)V
    //   1040: invokespecial throwException : (Ljava/lang/RuntimeException;)V
    //   1043: aload #11
    //   1045: astore #10
    //   1047: goto -> 1053
    //   1050: aconst_null
    //   1051: astore #10
    //   1053: aload_1
    //   1054: aload #10
    //   1056: putfield mContainer : Landroid/view/ViewGroup;
    //   1059: aload_1
    //   1060: aload_1
    //   1061: aload_1
    //   1062: aload_1
    //   1063: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   1066: invokevirtual performGetLayoutInflater : (Landroid/os/Bundle;)Landroid/view/LayoutInflater;
    //   1069: aload #10
    //   1071: aload_1
    //   1072: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   1075: invokevirtual performCreateView : (Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)Landroid/view/View;
    //   1078: putfield mView : Landroid/view/View;
    //   1081: aload_1
    //   1082: getfield mView : Landroid/view/View;
    //   1085: ifnull -> 1196
    //   1088: aload_1
    //   1089: aload_1
    //   1090: getfield mView : Landroid/view/View;
    //   1093: putfield mInnerView : Landroid/view/View;
    //   1096: aload_1
    //   1097: getfield mView : Landroid/view/View;
    //   1100: iconst_0
    //   1101: invokevirtual setSaveFromParentEnabled : (Z)V
    //   1104: aload #10
    //   1106: ifnull -> 1118
    //   1109: aload #10
    //   1111: aload_1
    //   1112: getfield mView : Landroid/view/View;
    //   1115: invokevirtual addView : (Landroid/view/View;)V
    //   1118: aload_1
    //   1119: getfield mHidden : Z
    //   1122: ifeq -> 1134
    //   1125: aload_1
    //   1126: getfield mView : Landroid/view/View;
    //   1129: bipush #8
    //   1131: invokevirtual setVisibility : (I)V
    //   1134: aload_1
    //   1135: aload_1
    //   1136: getfield mView : Landroid/view/View;
    //   1139: aload_1
    //   1140: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   1143: invokevirtual onViewCreated : (Landroid/view/View;Landroid/os/Bundle;)V
    //   1146: aload_0
    //   1147: aload_1
    //   1148: aload_1
    //   1149: getfield mView : Landroid/view/View;
    //   1152: aload_1
    //   1153: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   1156: iconst_0
    //   1157: invokevirtual dispatchOnFragmentViewCreated : (Landroid/support/v4/app/Fragment;Landroid/view/View;Landroid/os/Bundle;Z)V
    //   1160: aload_1
    //   1161: getfield mView : Landroid/view/View;
    //   1164: invokevirtual getVisibility : ()I
    //   1167: ifne -> 1184
    //   1170: aload_1
    //   1171: getfield mContainer : Landroid/view/ViewGroup;
    //   1174: ifnull -> 1184
    //   1177: iload #8
    //   1179: istore #5
    //   1181: goto -> 1187
    //   1184: iconst_0
    //   1185: istore #5
    //   1187: aload_1
    //   1188: iload #5
    //   1190: putfield mIsNewlyAdded : Z
    //   1193: goto -> 1201
    //   1196: aload_1
    //   1197: aconst_null
    //   1198: putfield mInnerView : Landroid/view/View;
    //   1201: aload_1
    //   1202: aload_1
    //   1203: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   1206: invokevirtual performActivityCreated : (Landroid/os/Bundle;)V
    //   1209: aload_0
    //   1210: aload_1
    //   1211: aload_1
    //   1212: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   1215: iconst_0
    //   1216: invokevirtual dispatchOnFragmentActivityCreated : (Landroid/support/v4/app/Fragment;Landroid/os/Bundle;Z)V
    //   1219: aload_1
    //   1220: getfield mView : Landroid/view/View;
    //   1223: ifnull -> 1234
    //   1226: aload_1
    //   1227: aload_1
    //   1228: getfield mSavedFragmentState : Landroid/os/Bundle;
    //   1231: invokevirtual restoreViewState : (Landroid/os/Bundle;)V
    //   1234: aload_1
    //   1235: aconst_null
    //   1236: putfield mSavedFragmentState : Landroid/os/Bundle;
    //   1239: iload #4
    //   1241: istore_3
    //   1242: iload_3
    //   1243: istore #4
    //   1245: iload_3
    //   1246: iconst_2
    //   1247: if_icmple -> 1258
    //   1250: aload_1
    //   1251: iconst_3
    //   1252: putfield mState : I
    //   1255: iload_3
    //   1256: istore #4
    //   1258: iload #4
    //   1260: istore_3
    //   1261: iload #4
    //   1263: iconst_3
    //   1264: if_icmple -> 230
    //   1267: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   1270: ifeq -> 1309
    //   1273: new java/lang/StringBuilder
    //   1276: dup
    //   1277: invokespecial <init> : ()V
    //   1280: astore #10
    //   1282: aload #10
    //   1284: ldc_w 'moveto STARTED: '
    //   1287: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1290: pop
    //   1291: aload #10
    //   1293: aload_1
    //   1294: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1297: pop
    //   1298: ldc 'FragmentManager'
    //   1300: aload #10
    //   1302: invokevirtual toString : ()Ljava/lang/String;
    //   1305: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   1308: pop
    //   1309: aload_1
    //   1310: invokevirtual performStart : ()V
    //   1313: aload_0
    //   1314: aload_1
    //   1315: iconst_0
    //   1316: invokevirtual dispatchOnFragmentStarted : (Landroid/support/v4/app/Fragment;Z)V
    //   1319: iload #4
    //   1321: istore_3
    //   1322: goto -> 230
    //   1325: iload_3
    //   1326: istore #9
    //   1328: iload_3
    //   1329: iconst_4
    //   1330: if_icmple -> 2054
    //   1333: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   1336: ifeq -> 1375
    //   1339: new java/lang/StringBuilder
    //   1342: dup
    //   1343: invokespecial <init> : ()V
    //   1346: astore #10
    //   1348: aload #10
    //   1350: ldc_w 'moveto RESUMED: '
    //   1353: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1356: pop
    //   1357: aload #10
    //   1359: aload_1
    //   1360: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1363: pop
    //   1364: ldc 'FragmentManager'
    //   1366: aload #10
    //   1368: invokevirtual toString : ()Ljava/lang/String;
    //   1371: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   1374: pop
    //   1375: aload_1
    //   1376: invokevirtual performResume : ()V
    //   1379: aload_0
    //   1380: aload_1
    //   1381: iconst_0
    //   1382: invokevirtual dispatchOnFragmentResumed : (Landroid/support/v4/app/Fragment;Z)V
    //   1385: aload_1
    //   1386: aconst_null
    //   1387: putfield mSavedFragmentState : Landroid/os/Bundle;
    //   1390: aload_1
    //   1391: aconst_null
    //   1392: putfield mSavedViewState : Landroid/util/SparseArray;
    //   1395: iload_3
    //   1396: istore #9
    //   1398: goto -> 2054
    //   1401: iload_2
    //   1402: istore #9
    //   1404: aload_1
    //   1405: getfield mState : I
    //   1408: iload_2
    //   1409: if_icmple -> 2054
    //   1412: aload_1
    //   1413: getfield mState : I
    //   1416: istore #9
    //   1418: iload #9
    //   1420: iconst_1
    //   1421: if_icmpeq -> 1837
    //   1424: iload #9
    //   1426: iconst_2
    //   1427: if_icmpeq -> 1619
    //   1430: iload #9
    //   1432: iconst_3
    //   1433: if_icmpeq -> 1568
    //   1436: iload #9
    //   1438: iconst_4
    //   1439: if_icmpeq -> 1511
    //   1442: iload #9
    //   1444: iconst_5
    //   1445: if_icmpeq -> 1454
    //   1448: iload_2
    //   1449: istore #9
    //   1451: goto -> 2054
    //   1454: iload_2
    //   1455: iconst_5
    //   1456: if_icmpge -> 1511
    //   1459: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   1462: ifeq -> 1501
    //   1465: new java/lang/StringBuilder
    //   1468: dup
    //   1469: invokespecial <init> : ()V
    //   1472: astore #10
    //   1474: aload #10
    //   1476: ldc_w 'movefrom RESUMED: '
    //   1479: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1482: pop
    //   1483: aload #10
    //   1485: aload_1
    //   1486: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1489: pop
    //   1490: ldc 'FragmentManager'
    //   1492: aload #10
    //   1494: invokevirtual toString : ()Ljava/lang/String;
    //   1497: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   1500: pop
    //   1501: aload_1
    //   1502: invokevirtual performPause : ()V
    //   1505: aload_0
    //   1506: aload_1
    //   1507: iconst_0
    //   1508: invokevirtual dispatchOnFragmentPaused : (Landroid/support/v4/app/Fragment;Z)V
    //   1511: iload_2
    //   1512: iconst_4
    //   1513: if_icmpge -> 1568
    //   1516: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   1519: ifeq -> 1558
    //   1522: new java/lang/StringBuilder
    //   1525: dup
    //   1526: invokespecial <init> : ()V
    //   1529: astore #10
    //   1531: aload #10
    //   1533: ldc_w 'movefrom STARTED: '
    //   1536: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1539: pop
    //   1540: aload #10
    //   1542: aload_1
    //   1543: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1546: pop
    //   1547: ldc 'FragmentManager'
    //   1549: aload #10
    //   1551: invokevirtual toString : ()Ljava/lang/String;
    //   1554: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   1557: pop
    //   1558: aload_1
    //   1559: invokevirtual performStop : ()V
    //   1562: aload_0
    //   1563: aload_1
    //   1564: iconst_0
    //   1565: invokevirtual dispatchOnFragmentStopped : (Landroid/support/v4/app/Fragment;Z)V
    //   1568: iload_2
    //   1569: iconst_3
    //   1570: if_icmpge -> 1619
    //   1573: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   1576: ifeq -> 1615
    //   1579: new java/lang/StringBuilder
    //   1582: dup
    //   1583: invokespecial <init> : ()V
    //   1586: astore #10
    //   1588: aload #10
    //   1590: ldc_w 'movefrom STOPPED: '
    //   1593: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1596: pop
    //   1597: aload #10
    //   1599: aload_1
    //   1600: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1603: pop
    //   1604: ldc 'FragmentManager'
    //   1606: aload #10
    //   1608: invokevirtual toString : ()Ljava/lang/String;
    //   1611: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   1614: pop
    //   1615: aload_1
    //   1616: invokevirtual performReallyStop : ()V
    //   1619: iload_2
    //   1620: iconst_2
    //   1621: if_icmpge -> 1837
    //   1624: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   1627: ifeq -> 1666
    //   1630: new java/lang/StringBuilder
    //   1633: dup
    //   1634: invokespecial <init> : ()V
    //   1637: astore #10
    //   1639: aload #10
    //   1641: ldc_w 'movefrom ACTIVITY_CREATED: '
    //   1644: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1647: pop
    //   1648: aload #10
    //   1650: aload_1
    //   1651: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1654: pop
    //   1655: ldc 'FragmentManager'
    //   1657: aload #10
    //   1659: invokevirtual toString : ()Ljava/lang/String;
    //   1662: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   1665: pop
    //   1666: aload_1
    //   1667: getfield mView : Landroid/view/View;
    //   1670: ifnull -> 1696
    //   1673: aload_0
    //   1674: getfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   1677: aload_1
    //   1678: invokevirtual onShouldSaveFragmentState : (Landroid/support/v4/app/Fragment;)Z
    //   1681: ifeq -> 1696
    //   1684: aload_1
    //   1685: getfield mSavedViewState : Landroid/util/SparseArray;
    //   1688: ifnonnull -> 1696
    //   1691: aload_0
    //   1692: aload_1
    //   1693: invokevirtual saveFragmentViewState : (Landroid/support/v4/app/Fragment;)V
    //   1696: aload_1
    //   1697: invokevirtual performDestroyView : ()V
    //   1700: aload_0
    //   1701: aload_1
    //   1702: iconst_0
    //   1703: invokevirtual dispatchOnFragmentViewDestroyed : (Landroid/support/v4/app/Fragment;Z)V
    //   1706: aload_1
    //   1707: getfield mView : Landroid/view/View;
    //   1710: ifnull -> 1817
    //   1713: aload_1
    //   1714: getfield mContainer : Landroid/view/ViewGroup;
    //   1717: ifnull -> 1817
    //   1720: aload_1
    //   1721: getfield mContainer : Landroid/view/ViewGroup;
    //   1724: aload_1
    //   1725: getfield mView : Landroid/view/View;
    //   1728: invokevirtual endViewTransition : (Landroid/view/View;)V
    //   1731: aload_1
    //   1732: getfield mView : Landroid/view/View;
    //   1735: invokevirtual clearAnimation : ()V
    //   1738: aload_0
    //   1739: getfield mCurState : I
    //   1742: ifle -> 1785
    //   1745: aload_0
    //   1746: getfield mDestroyed : Z
    //   1749: ifne -> 1785
    //   1752: aload_1
    //   1753: getfield mView : Landroid/view/View;
    //   1756: invokevirtual getVisibility : ()I
    //   1759: ifne -> 1785
    //   1762: aload_1
    //   1763: getfield mPostponedAlpha : F
    //   1766: fconst_0
    //   1767: fcmpl
    //   1768: iflt -> 1785
    //   1771: aload_0
    //   1772: aload_1
    //   1773: iload_3
    //   1774: iconst_0
    //   1775: iload #4
    //   1777: invokevirtual loadAnimation : (Landroid/support/v4/app/Fragment;IZI)Landroid/support/v4/app/FragmentManagerImpl$AnimationOrAnimator;
    //   1780: astore #10
    //   1782: goto -> 1788
    //   1785: aconst_null
    //   1786: astore #10
    //   1788: aload_1
    //   1789: fconst_0
    //   1790: putfield mPostponedAlpha : F
    //   1793: aload #10
    //   1795: ifnull -> 1806
    //   1798: aload_0
    //   1799: aload_1
    //   1800: aload #10
    //   1802: iload_2
    //   1803: invokespecial animateRemoveFragment : (Landroid/support/v4/app/Fragment;Landroid/support/v4/app/FragmentManagerImpl$AnimationOrAnimator;I)V
    //   1806: aload_1
    //   1807: getfield mContainer : Landroid/view/ViewGroup;
    //   1810: aload_1
    //   1811: getfield mView : Landroid/view/View;
    //   1814: invokevirtual removeView : (Landroid/view/View;)V
    //   1817: aload_1
    //   1818: aconst_null
    //   1819: putfield mContainer : Landroid/view/ViewGroup;
    //   1822: aload_1
    //   1823: aconst_null
    //   1824: putfield mView : Landroid/view/View;
    //   1827: aload_1
    //   1828: aconst_null
    //   1829: putfield mInnerView : Landroid/view/View;
    //   1832: aload_1
    //   1833: iconst_0
    //   1834: putfield mInLayout : Z
    //   1837: iload_2
    //   1838: istore #9
    //   1840: iload_2
    //   1841: iconst_1
    //   1842: if_icmpge -> 2054
    //   1845: aload_0
    //   1846: getfield mDestroyed : Z
    //   1849: ifeq -> 1901
    //   1852: aload_1
    //   1853: invokevirtual getAnimatingAway : ()Landroid/view/View;
    //   1856: ifnull -> 1878
    //   1859: aload_1
    //   1860: invokevirtual getAnimatingAway : ()Landroid/view/View;
    //   1863: astore #10
    //   1865: aload_1
    //   1866: aconst_null
    //   1867: invokevirtual setAnimatingAway : (Landroid/view/View;)V
    //   1870: aload #10
    //   1872: invokevirtual clearAnimation : ()V
    //   1875: goto -> 1901
    //   1878: aload_1
    //   1879: invokevirtual getAnimator : ()Landroid/animation/Animator;
    //   1882: ifnull -> 1901
    //   1885: aload_1
    //   1886: invokevirtual getAnimator : ()Landroid/animation/Animator;
    //   1889: astore #10
    //   1891: aload_1
    //   1892: aconst_null
    //   1893: invokevirtual setAnimator : (Landroid/animation/Animator;)V
    //   1896: aload #10
    //   1898: invokevirtual cancel : ()V
    //   1901: aload_1
    //   1902: invokevirtual getAnimatingAway : ()Landroid/view/View;
    //   1905: ifnonnull -> 2042
    //   1908: aload_1
    //   1909: invokevirtual getAnimator : ()Landroid/animation/Animator;
    //   1912: ifnull -> 1918
    //   1915: goto -> 2042
    //   1918: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   1921: ifeq -> 1960
    //   1924: new java/lang/StringBuilder
    //   1927: dup
    //   1928: invokespecial <init> : ()V
    //   1931: astore #10
    //   1933: aload #10
    //   1935: ldc_w 'movefrom CREATED: '
    //   1938: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1941: pop
    //   1942: aload #10
    //   1944: aload_1
    //   1945: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1948: pop
    //   1949: ldc 'FragmentManager'
    //   1951: aload #10
    //   1953: invokevirtual toString : ()Ljava/lang/String;
    //   1956: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   1959: pop
    //   1960: aload_1
    //   1961: getfield mRetaining : Z
    //   1964: ifne -> 1980
    //   1967: aload_1
    //   1968: invokevirtual performDestroy : ()V
    //   1971: aload_0
    //   1972: aload_1
    //   1973: iconst_0
    //   1974: invokevirtual dispatchOnFragmentDestroyed : (Landroid/support/v4/app/Fragment;Z)V
    //   1977: goto -> 1985
    //   1980: aload_1
    //   1981: iconst_0
    //   1982: putfield mState : I
    //   1985: aload_1
    //   1986: invokevirtual performDetach : ()V
    //   1989: aload_0
    //   1990: aload_1
    //   1991: iconst_0
    //   1992: invokevirtual dispatchOnFragmentDetached : (Landroid/support/v4/app/Fragment;Z)V
    //   1995: iload_2
    //   1996: istore #9
    //   1998: iload #5
    //   2000: ifne -> 2054
    //   2003: aload_1
    //   2004: getfield mRetaining : Z
    //   2007: ifne -> 2021
    //   2010: aload_0
    //   2011: aload_1
    //   2012: invokevirtual makeInactive : (Landroid/support/v4/app/Fragment;)V
    //   2015: iload_2
    //   2016: istore #9
    //   2018: goto -> 2054
    //   2021: aload_1
    //   2022: aconst_null
    //   2023: putfield mHost : Landroid/support/v4/app/FragmentHostCallback;
    //   2026: aload_1
    //   2027: aconst_null
    //   2028: putfield mParentFragment : Landroid/support/v4/app/Fragment;
    //   2031: aload_1
    //   2032: aconst_null
    //   2033: putfield mFragmentManager : Landroid/support/v4/app/FragmentManagerImpl;
    //   2036: iload_2
    //   2037: istore #9
    //   2039: goto -> 2054
    //   2042: aload_1
    //   2043: iload_2
    //   2044: invokevirtual setStateAfterAnimating : (I)V
    //   2047: iload #7
    //   2049: istore #9
    //   2051: goto -> 2054
    //   2054: aload_1
    //   2055: getfield mState : I
    //   2058: iload #9
    //   2060: if_icmpeq -> 2150
    //   2063: new java/lang/StringBuilder
    //   2066: dup
    //   2067: invokespecial <init> : ()V
    //   2070: astore #10
    //   2072: aload #10
    //   2074: ldc_w 'moveToState: Fragment state for '
    //   2077: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2080: pop
    //   2081: aload #10
    //   2083: aload_1
    //   2084: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   2087: pop
    //   2088: aload #10
    //   2090: ldc_w ' not updated inline; '
    //   2093: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2096: pop
    //   2097: aload #10
    //   2099: ldc_w 'expected state '
    //   2102: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2105: pop
    //   2106: aload #10
    //   2108: iload #9
    //   2110: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   2113: pop
    //   2114: aload #10
    //   2116: ldc_w ' found '
    //   2119: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2122: pop
    //   2123: aload #10
    //   2125: aload_1
    //   2126: getfield mState : I
    //   2129: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   2132: pop
    //   2133: ldc 'FragmentManager'
    //   2135: aload #10
    //   2137: invokevirtual toString : ()Ljava/lang/String;
    //   2140: invokestatic w : (Ljava/lang/String;Ljava/lang/String;)I
    //   2143: pop
    //   2144: aload_1
    //   2145: iload #9
    //   2147: putfield mState : I
    //   2150: return
    // Exception table:
    //   from	to	target	type
    //   940	953	956	android/content/res/Resources$NotFoundException
  }
  
  public void noteStateNotSaved() {
    this.mSavedNonConfig = null;
    byte b = 0;
    this.mStateSaved = false;
    this.mStopped = false;
    int i = this.mAdded.size();
    while (b < i) {
      Fragment fragment = this.mAdded.get(b);
      if (fragment != null)
        fragment.noteStateNotSaved(); 
      b++;
    } 
  }
  
  public View onCreateView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet) {
    if (!"fragment".equals(paramString))
      return null; 
    paramString = paramAttributeSet.getAttributeValue(null, "class");
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, FragmentTag.Fragment);
    int i = 0;
    String str1 = paramString;
    if (paramString == null)
      str1 = typedArray.getString(0); 
    int j = typedArray.getResourceId(1, -1);
    String str2 = typedArray.getString(2);
    typedArray.recycle();
    if (!Fragment.isSupportFragmentClass(this.mHost.getContext(), str1))
      return null; 
    if (paramView != null)
      i = paramView.getId(); 
    if (i != -1 || j != -1 || str2 != null) {
      Fragment fragment2;
      if (j != -1) {
        Fragment fragment = findFragmentById(j);
      } else {
        paramView = null;
      } 
      View view = paramView;
      if (paramView == null) {
        view = paramView;
        if (str2 != null)
          fragment2 = findFragmentByTag(str2); 
      } 
      Fragment fragment1 = fragment2;
      if (fragment2 == null) {
        fragment1 = fragment2;
        if (i != -1)
          fragment1 = findFragmentById(i); 
      } 
      if (DEBUG) {
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("onCreateView: id=0x");
        stringBuilder2.append(Integer.toHexString(j));
        stringBuilder2.append(" fname=");
        stringBuilder2.append(str1);
        stringBuilder2.append(" existing=");
        stringBuilder2.append(fragment1);
        Log.v("FragmentManager", stringBuilder2.toString());
      } 
      if (fragment1 == null) {
        int k;
        fragment2 = this.mContainer.instantiate(paramContext, str1, null);
        fragment2.mFromLayout = true;
        if (j != 0) {
          k = j;
        } else {
          k = i;
        } 
        fragment2.mFragmentId = k;
        fragment2.mContainerId = i;
        fragment2.mTag = str2;
        fragment2.mInLayout = true;
        fragment2.mFragmentManager = this;
        fragment2.mHost = this.mHost;
        fragment2.onInflate(this.mHost.getContext(), paramAttributeSet, fragment2.mSavedFragmentState);
        addFragment(fragment2, true);
      } else if (!fragment1.mInLayout) {
        fragment1.mInLayout = true;
        fragment1.mHost = this.mHost;
        fragment2 = fragment1;
        if (!fragment1.mRetaining) {
          fragment1.onInflate(this.mHost.getContext(), paramAttributeSet, fragment1.mSavedFragmentState);
          fragment2 = fragment1;
        } 
      } else {
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(paramAttributeSet.getPositionDescription());
        stringBuilder2.append(": Duplicate id 0x");
        stringBuilder2.append(Integer.toHexString(j));
        stringBuilder2.append(", tag ");
        stringBuilder2.append(str2);
        stringBuilder2.append(", or parent id 0x");
        stringBuilder2.append(Integer.toHexString(i));
        stringBuilder2.append(" with another fragment for ");
        stringBuilder2.append(str1);
        throw new IllegalArgumentException(stringBuilder2.toString());
      } 
      if (this.mCurState < 1 && fragment2.mFromLayout) {
        moveToState(fragment2, 1, 0, 0, false);
      } else {
        moveToState(fragment2);
      } 
      if (fragment2.mView != null) {
        if (j != 0)
          fragment2.mView.setId(j); 
        if (fragment2.mView.getTag() == null)
          fragment2.mView.setTag(str2); 
        return fragment2.mView;
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Fragment ");
      stringBuilder1.append(str1);
      stringBuilder1.append(" did not create a view.");
      throw new IllegalStateException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramAttributeSet.getPositionDescription());
    stringBuilder.append(": Must specify unique android:id, android:tag, or have a parent with an id for ");
    stringBuilder.append(str1);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public View onCreateView(String paramString, Context paramContext, AttributeSet paramAttributeSet) {
    return onCreateView(null, paramString, paramContext, paramAttributeSet);
  }
  
  public void performPendingDeferredStart(Fragment paramFragment) {
    if (paramFragment.mDeferStart) {
      if (this.mExecutingActions) {
        this.mHavePendingDeferredStart = true;
        return;
      } 
      paramFragment.mDeferStart = false;
      moveToState(paramFragment, this.mCurState, 0, 0, false);
    } 
  }
  
  public void popBackStack() {
    enqueueAction(new PopBackStackState(null, -1, 0), false);
  }
  
  public void popBackStack(int paramInt1, int paramInt2) {
    if (paramInt1 >= 0) {
      enqueueAction(new PopBackStackState(null, paramInt1, paramInt2), false);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Bad id: ");
    stringBuilder.append(paramInt1);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void popBackStack(String paramString, int paramInt) {
    enqueueAction(new PopBackStackState(paramString, -1, paramInt), false);
  }
  
  public boolean popBackStackImmediate() {
    checkStateLoss();
    return popBackStackImmediate(null, -1, 0);
  }
  
  public boolean popBackStackImmediate(int paramInt1, int paramInt2) {
    checkStateLoss();
    execPendingActions();
    if (paramInt1 >= 0)
      return popBackStackImmediate(null, paramInt1, paramInt2); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Bad id: ");
    stringBuilder.append(paramInt1);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public boolean popBackStackImmediate(String paramString, int paramInt) {
    checkStateLoss();
    return popBackStackImmediate(paramString, -1, paramInt);
  }
  
  boolean popBackStackState(ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, String paramString, int paramInt1, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mBackStack : Ljava/util/ArrayList;
    //   4: astore #6
    //   6: aload #6
    //   8: ifnonnull -> 13
    //   11: iconst_0
    //   12: ireturn
    //   13: aload_3
    //   14: ifnonnull -> 71
    //   17: iload #4
    //   19: ifge -> 71
    //   22: iload #5
    //   24: iconst_1
    //   25: iand
    //   26: ifne -> 71
    //   29: aload #6
    //   31: invokevirtual size : ()I
    //   34: iconst_1
    //   35: isub
    //   36: istore #4
    //   38: iload #4
    //   40: ifge -> 45
    //   43: iconst_0
    //   44: ireturn
    //   45: aload_1
    //   46: aload_0
    //   47: getfield mBackStack : Ljava/util/ArrayList;
    //   50: iload #4
    //   52: invokevirtual remove : (I)Ljava/lang/Object;
    //   55: invokevirtual add : (Ljava/lang/Object;)Z
    //   58: pop
    //   59: aload_2
    //   60: iconst_1
    //   61: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   64: invokevirtual add : (Ljava/lang/Object;)Z
    //   67: pop
    //   68: goto -> 326
    //   71: aload_3
    //   72: ifnonnull -> 89
    //   75: iload #4
    //   77: iflt -> 83
    //   80: goto -> 89
    //   83: iconst_m1
    //   84: istore #4
    //   86: goto -> 263
    //   89: aload_0
    //   90: getfield mBackStack : Ljava/util/ArrayList;
    //   93: invokevirtual size : ()I
    //   96: iconst_1
    //   97: isub
    //   98: istore #7
    //   100: iload #7
    //   102: iflt -> 162
    //   105: aload_0
    //   106: getfield mBackStack : Ljava/util/ArrayList;
    //   109: iload #7
    //   111: invokevirtual get : (I)Ljava/lang/Object;
    //   114: checkcast android/support/v4/app/BackStackRecord
    //   117: astore #6
    //   119: aload_3
    //   120: ifnull -> 138
    //   123: aload_3
    //   124: aload #6
    //   126: invokevirtual getName : ()Ljava/lang/String;
    //   129: invokevirtual equals : (Ljava/lang/Object;)Z
    //   132: ifeq -> 138
    //   135: goto -> 162
    //   138: iload #4
    //   140: iflt -> 156
    //   143: iload #4
    //   145: aload #6
    //   147: getfield mIndex : I
    //   150: if_icmpne -> 156
    //   153: goto -> 162
    //   156: iinc #7, -1
    //   159: goto -> 100
    //   162: iload #7
    //   164: ifge -> 169
    //   167: iconst_0
    //   168: ireturn
    //   169: iload #7
    //   171: istore #8
    //   173: iload #5
    //   175: iconst_1
    //   176: iand
    //   177: ifeq -> 259
    //   180: iload #7
    //   182: iconst_1
    //   183: isub
    //   184: istore #5
    //   186: iload #5
    //   188: istore #8
    //   190: iload #5
    //   192: iflt -> 259
    //   195: aload_0
    //   196: getfield mBackStack : Ljava/util/ArrayList;
    //   199: iload #5
    //   201: invokevirtual get : (I)Ljava/lang/Object;
    //   204: checkcast android/support/v4/app/BackStackRecord
    //   207: astore #6
    //   209: aload_3
    //   210: ifnull -> 229
    //   213: iload #5
    //   215: istore #7
    //   217: aload_3
    //   218: aload #6
    //   220: invokevirtual getName : ()Ljava/lang/String;
    //   223: invokevirtual equals : (Ljava/lang/Object;)Z
    //   226: ifne -> 180
    //   229: iload #5
    //   231: istore #8
    //   233: iload #4
    //   235: iflt -> 259
    //   238: iload #5
    //   240: istore #8
    //   242: iload #4
    //   244: aload #6
    //   246: getfield mIndex : I
    //   249: if_icmpne -> 259
    //   252: iload #5
    //   254: istore #7
    //   256: goto -> 180
    //   259: iload #8
    //   261: istore #4
    //   263: iload #4
    //   265: aload_0
    //   266: getfield mBackStack : Ljava/util/ArrayList;
    //   269: invokevirtual size : ()I
    //   272: iconst_1
    //   273: isub
    //   274: if_icmpne -> 279
    //   277: iconst_0
    //   278: ireturn
    //   279: aload_0
    //   280: getfield mBackStack : Ljava/util/ArrayList;
    //   283: invokevirtual size : ()I
    //   286: iconst_1
    //   287: isub
    //   288: istore #5
    //   290: iload #5
    //   292: iload #4
    //   294: if_icmple -> 326
    //   297: aload_1
    //   298: aload_0
    //   299: getfield mBackStack : Ljava/util/ArrayList;
    //   302: iload #5
    //   304: invokevirtual remove : (I)Ljava/lang/Object;
    //   307: invokevirtual add : (Ljava/lang/Object;)Z
    //   310: pop
    //   311: aload_2
    //   312: iconst_1
    //   313: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   316: invokevirtual add : (Ljava/lang/Object;)Z
    //   319: pop
    //   320: iinc #5, -1
    //   323: goto -> 290
    //   326: iconst_1
    //   327: ireturn
  }
  
  public void putFragment(Bundle paramBundle, String paramString, Fragment paramFragment) {
    if (paramFragment.mIndex < 0) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Fragment ");
      stringBuilder.append(paramFragment);
      stringBuilder.append(" is not currently in the FragmentManager");
      throwException(new IllegalStateException(stringBuilder.toString()));
    } 
    paramBundle.putInt(paramString, paramFragment.mIndex);
  }
  
  public void registerFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks paramFragmentLifecycleCallbacks, boolean paramBoolean) {
    this.mLifecycleCallbacks.add(new Pair(paramFragmentLifecycleCallbacks, Boolean.valueOf(paramBoolean)));
  }
  
  public void removeFragment(Fragment paramFragment) {
    if (DEBUG) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("remove: ");
      stringBuilder.append(paramFragment);
      stringBuilder.append(" nesting=");
      stringBuilder.append(paramFragment.mBackStackNesting);
      Log.v("FragmentManager", stringBuilder.toString());
    } 
    boolean bool = paramFragment.isInBackStack();
    if (!paramFragment.mDetached || (bool ^ true) != 0)
      synchronized (this.mAdded) {
        this.mAdded.remove(paramFragment);
        if (paramFragment.mHasMenu && paramFragment.mMenuVisible)
          this.mNeedMenuInvalidate = true; 
        paramFragment.mAdded = false;
        paramFragment.mRemoving = true;
        return;
      }  
  }
  
  public void removeOnBackStackChangedListener(FragmentManager.OnBackStackChangedListener paramOnBackStackChangedListener) {
    ArrayList<FragmentManager.OnBackStackChangedListener> arrayList = this.mBackStackChangeListeners;
    if (arrayList != null)
      arrayList.remove(paramOnBackStackChangedListener); 
  }
  
  void reportBackStackChanged() {
    if (this.mBackStackChangeListeners != null)
      for (byte b = 0; b < this.mBackStackChangeListeners.size(); b++)
        ((FragmentManager.OnBackStackChangedListener)this.mBackStackChangeListeners.get(b)).onBackStackChanged();  
  }
  
  void restoreAllState(Parcelable<FragmentManagerNonConfig> paramParcelable, FragmentManagerNonConfig paramFragmentManagerNonConfig) {
    Parcelable<FragmentManagerNonConfig> parcelable;
    if (paramParcelable == null)
      return; 
    FragmentManagerState fragmentManagerState = (FragmentManagerState)paramParcelable;
    if (fragmentManagerState.mActive == null)
      return; 
    if (paramFragmentManagerNonConfig != null) {
      byte b1;
      List<Fragment> list = paramFragmentManagerNonConfig.getFragments();
      List<FragmentManagerNonConfig> list1 = paramFragmentManagerNonConfig.getChildNonConfigs();
      List<ViewModelStore> list2 = paramFragmentManagerNonConfig.getViewModelStores();
      if (list != null) {
        b1 = list.size();
      } else {
        b1 = 0;
      } 
      byte b2 = 0;
      while (true) {
        List<FragmentManagerNonConfig> list4 = list1;
        List<ViewModelStore> list3 = list2;
        if (b2 < b1) {
          Fragment fragment = list.get(b2);
          if (DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("restoreAllState: re-attaching retained ");
            stringBuilder.append(fragment);
            Log.v("FragmentManager", stringBuilder.toString());
          } 
          byte b;
          for (b = 0; b < fragmentManagerState.mActive.length && (fragmentManagerState.mActive[b]).mIndex != fragment.mIndex; b++);
          if (b == fragmentManagerState.mActive.length) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not find active fragment with index ");
            stringBuilder.append(fragment.mIndex);
            throwException(new IllegalStateException(stringBuilder.toString()));
          } 
          parcelable = fragmentManagerState.mActive[b];
          ((FragmentState)parcelable).mInstance = fragment;
          fragment.mSavedViewState = null;
          fragment.mBackStackNesting = 0;
          fragment.mInLayout = false;
          fragment.mAdded = false;
          fragment.mTarget = null;
          if (((FragmentState)parcelable).mSavedFragmentState != null) {
            ((FragmentState)parcelable).mSavedFragmentState.setClassLoader(this.mHost.getContext().getClassLoader());
            fragment.mSavedViewState = ((FragmentState)parcelable).mSavedFragmentState.getSparseParcelableArray("android:view_state");
            fragment.mSavedFragmentState = ((FragmentState)parcelable).mSavedFragmentState;
          } 
          b2++;
          continue;
        } 
        break;
      } 
    } else {
      parcelable = null;
      paramParcelable = parcelable;
    } 
    this.mActive = new SparseArray(fragmentManagerState.mActive.length);
    int i;
    for (i = 0; i < fragmentManagerState.mActive.length; i++) {
      FragmentState fragmentState = fragmentManagerState.mActive[i];
      if (fragmentState != null) {
        FragmentManagerNonConfig fragmentManagerNonConfig;
        if (parcelable != null && i < parcelable.size()) {
          fragmentManagerNonConfig = parcelable.get(i);
        } else {
          fragmentManagerNonConfig = null;
        } 
        if (paramParcelable != null && i < paramParcelable.size()) {
          fragment = (Fragment)paramParcelable.get(i);
        } else {
          fragment = null;
        } 
        Fragment fragment = fragmentState.instantiate(this.mHost, this.mContainer, this.mParent, fragmentManagerNonConfig, (ViewModelStore)fragment);
        if (DEBUG) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("restoreAllState: active #");
          stringBuilder.append(i);
          stringBuilder.append(": ");
          stringBuilder.append(fragment);
          Log.v("FragmentManager", stringBuilder.toString());
        } 
        this.mActive.put(fragment.mIndex, fragment);
        fragmentState.mInstance = null;
      } 
    } 
    if (paramFragmentManagerNonConfig != null) {
      List<Fragment> list = paramFragmentManagerNonConfig.getFragments();
      if (list != null) {
        i = list.size();
      } else {
        i = 0;
      } 
      for (byte b = 0; b < i; b++) {
        Fragment fragment = list.get(b);
        if (fragment.mTargetIndex >= 0) {
          fragment.mTarget = (Fragment)this.mActive.get(fragment.mTargetIndex);
          if (fragment.mTarget == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Re-attaching retained fragment ");
            stringBuilder.append(fragment);
            stringBuilder.append(" target no longer exists: ");
            stringBuilder.append(fragment.mTargetIndex);
            Log.w("FragmentManager", stringBuilder.toString());
          } 
        } 
      } 
    } 
    this.mAdded.clear();
    if (fragmentManagerState.mAdded != null) {
      i = 0;
      while (i < fragmentManagerState.mAdded.length) {
        Fragment fragment = (Fragment)this.mActive.get(fragmentManagerState.mAdded[i]);
        if (fragment == null) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("No instantiated fragment for index #");
          stringBuilder.append(fragmentManagerState.mAdded[i]);
          throwException(new IllegalStateException(stringBuilder.toString()));
        } 
        fragment.mAdded = true;
        if (DEBUG) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("restoreAllState: added #");
          stringBuilder.append(i);
          stringBuilder.append(": ");
          stringBuilder.append(fragment);
          Log.v("FragmentManager", stringBuilder.toString());
        } 
        if (!this.mAdded.contains(fragment)) {
          synchronized (this.mAdded) {
            this.mAdded.add(fragment);
            i++;
          } 
          continue;
        } 
        throw new IllegalStateException("Already added!");
      } 
    } 
    if (fragmentManagerState.mBackStack != null) {
      this.mBackStack = new ArrayList<BackStackRecord>(fragmentManagerState.mBackStack.length);
      for (i = 0; i < fragmentManagerState.mBackStack.length; i++) {
        BackStackRecord backStackRecord = fragmentManagerState.mBackStack[i].instantiate(this);
        if (DEBUG) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("restoreAllState: back stack #");
          stringBuilder.append(i);
          stringBuilder.append(" (index ");
          stringBuilder.append(backStackRecord.mIndex);
          stringBuilder.append("): ");
          stringBuilder.append(backStackRecord);
          Log.v("FragmentManager", stringBuilder.toString());
          PrintWriter printWriter = new PrintWriter((Writer)new LogWriter("FragmentManager"));
          backStackRecord.dump("  ", printWriter, false);
          printWriter.close();
        } 
        this.mBackStack.add(backStackRecord);
        if (backStackRecord.mIndex >= 0)
          setBackStackIndex(backStackRecord.mIndex, backStackRecord); 
      } 
    } else {
      this.mBackStack = null;
    } 
    if (fragmentManagerState.mPrimaryNavActiveIndex >= 0)
      this.mPrimaryNav = (Fragment)this.mActive.get(fragmentManagerState.mPrimaryNavActiveIndex); 
    this.mNextFragmentIndex = fragmentManagerState.mNextFragmentIndex;
  }
  
  FragmentManagerNonConfig retainNonConfig() {
    setRetaining(this.mSavedNonConfig);
    return this.mSavedNonConfig;
  }
  
  Parcelable saveAllState() {
    StringBuilder stringBuilder;
    forcePostponedTransactions();
    endAnimatingAwayFragments();
    execPendingActions();
    this.mStateSaved = true;
    BackStackState[] arrayOfBackStackState1 = null;
    this.mSavedNonConfig = null;
    SparseArray<Fragment> sparseArray = this.mActive;
    if (sparseArray == null || sparseArray.size() <= 0)
      return null; 
    int i = this.mActive.size();
    FragmentState[] arrayOfFragmentState = new FragmentState[i];
    boolean bool = false;
    byte b = 0;
    int j = 0;
    while (b < i) {
      Fragment fragment1 = (Fragment)this.mActive.valueAt(b);
      if (fragment1 != null) {
        if (fragment1.mIndex < 0) {
          stringBuilder = new StringBuilder();
          stringBuilder.append("Failure saving state: active ");
          stringBuilder.append(fragment1);
          stringBuilder.append(" has cleared index: ");
          stringBuilder.append(fragment1.mIndex);
          throwException(new IllegalStateException(stringBuilder.toString()));
        } 
        FragmentState fragmentState = new FragmentState(fragment1);
        arrayOfFragmentState[b] = fragmentState;
        if (fragment1.mState > 0 && fragmentState.mSavedFragmentState == null) {
          fragmentState.mSavedFragmentState = saveFragmentBasicState(fragment1);
          if (fragment1.mTarget != null) {
            if (fragment1.mTarget.mIndex < 0) {
              StringBuilder stringBuilder1 = new StringBuilder();
              stringBuilder1.append("Failure saving state: ");
              stringBuilder1.append(fragment1);
              stringBuilder1.append(" has target not in fragment manager: ");
              stringBuilder1.append(fragment1.mTarget);
              throwException(new IllegalStateException(stringBuilder1.toString()));
            } 
            if (fragmentState.mSavedFragmentState == null)
              fragmentState.mSavedFragmentState = new Bundle(); 
            putFragment(fragmentState.mSavedFragmentState, "android:target_state", fragment1.mTarget);
            if (fragment1.mTargetRequestCode != 0)
              fragmentState.mSavedFragmentState.putInt("android:target_req_state", fragment1.mTargetRequestCode); 
          } 
        } else {
          fragmentState.mSavedFragmentState = fragment1.mSavedFragmentState;
        } 
        if (DEBUG) {
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("Saved state of ");
          stringBuilder1.append(fragment1);
          stringBuilder1.append(": ");
          stringBuilder1.append(fragmentState.mSavedFragmentState);
          Log.v("FragmentManager", stringBuilder1.toString());
        } 
        j = 1;
      } 
      b++;
    } 
    if (!j) {
      if (DEBUG)
        Log.v("FragmentManager", "saveAllState: no fragments!"); 
      return null;
    } 
    j = this.mAdded.size();
    if (j > 0) {
      int[] arrayOfInt = new int[j];
      b = 0;
      while (true) {
        int[] arrayOfInt1 = arrayOfInt;
        if (b < j) {
          arrayOfInt[b] = ((Fragment)this.mAdded.get(b)).mIndex;
          if (arrayOfInt[b] < 0) {
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append("Failure saving state: active ");
            stringBuilder1.append(this.mAdded.get(b));
            stringBuilder1.append(" has cleared index: ");
            stringBuilder1.append(arrayOfInt[b]);
            throwException(new IllegalStateException(stringBuilder1.toString()));
          } 
          if (DEBUG) {
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append("saveAllState: adding fragment #");
            stringBuilder1.append(b);
            stringBuilder1.append(": ");
            stringBuilder1.append(this.mAdded.get(b));
            Log.v("FragmentManager", stringBuilder1.toString());
          } 
          b++;
          continue;
        } 
        break;
      } 
    } else {
      sparseArray = null;
    } 
    ArrayList<BackStackRecord> arrayList = this.mBackStack;
    BackStackState[] arrayOfBackStackState2 = arrayOfBackStackState1;
    if (arrayList != null) {
      j = arrayList.size();
      arrayOfBackStackState2 = arrayOfBackStackState1;
      if (j > 0) {
        arrayOfBackStackState1 = new BackStackState[j];
        b = bool;
        while (true) {
          arrayOfBackStackState2 = arrayOfBackStackState1;
          if (b < j) {
            arrayOfBackStackState1[b] = new BackStackState(this.mBackStack.get(b));
            if (DEBUG) {
              stringBuilder = new StringBuilder();
              stringBuilder.append("saveAllState: adding back stack #");
              stringBuilder.append(b);
              stringBuilder.append(": ");
              stringBuilder.append(this.mBackStack.get(b));
              Log.v("FragmentManager", stringBuilder.toString());
            } 
            b++;
            continue;
          } 
          break;
        } 
      } 
    } 
    FragmentManagerState fragmentManagerState = new FragmentManagerState();
    fragmentManagerState.mActive = arrayOfFragmentState;
    fragmentManagerState.mAdded = (int[])sparseArray;
    fragmentManagerState.mBackStack = (BackStackState[])stringBuilder;
    Fragment fragment = this.mPrimaryNav;
    if (fragment != null)
      fragmentManagerState.mPrimaryNavActiveIndex = fragment.mIndex; 
    fragmentManagerState.mNextFragmentIndex = this.mNextFragmentIndex;
    saveNonConfig();
    return fragmentManagerState;
  }
  
  Bundle saveFragmentBasicState(Fragment paramFragment) {
    if (this.mStateBundle == null)
      this.mStateBundle = new Bundle(); 
    paramFragment.performSaveInstanceState(this.mStateBundle);
    dispatchOnFragmentSaveInstanceState(paramFragment, this.mStateBundle, false);
    boolean bool = this.mStateBundle.isEmpty();
    Bundle bundle1 = null;
    if (!bool) {
      bundle1 = this.mStateBundle;
      this.mStateBundle = null;
    } 
    if (paramFragment.mView != null)
      saveFragmentViewState(paramFragment); 
    Bundle bundle2 = bundle1;
    if (paramFragment.mSavedViewState != null) {
      bundle2 = bundle1;
      if (bundle1 == null)
        bundle2 = new Bundle(); 
      bundle2.putSparseParcelableArray("android:view_state", paramFragment.mSavedViewState);
    } 
    bundle1 = bundle2;
    if (!paramFragment.mUserVisibleHint) {
      bundle1 = bundle2;
      if (bundle2 == null)
        bundle1 = new Bundle(); 
      bundle1.putBoolean("android:user_visible_hint", paramFragment.mUserVisibleHint);
    } 
    return bundle1;
  }
  
  public Fragment.SavedState saveFragmentInstanceState(Fragment paramFragment) {
    if (paramFragment.mIndex < 0) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Fragment ");
      stringBuilder.append(paramFragment);
      stringBuilder.append(" is not currently in the FragmentManager");
      throwException(new IllegalStateException(stringBuilder.toString()));
    } 
    int i = paramFragment.mState;
    Fragment.SavedState savedState2 = null;
    Fragment.SavedState savedState1 = savedState2;
    if (i > 0) {
      Bundle bundle = saveFragmentBasicState(paramFragment);
      savedState1 = savedState2;
      if (bundle != null)
        savedState1 = new Fragment.SavedState(bundle); 
    } 
    return savedState1;
  }
  
  void saveFragmentViewState(Fragment paramFragment) {
    if (paramFragment.mInnerView == null)
      return; 
    SparseArray<Parcelable> sparseArray = this.mStateArray;
    if (sparseArray == null) {
      this.mStateArray = new SparseArray();
    } else {
      sparseArray.clear();
    } 
    paramFragment.mInnerView.saveHierarchyState(this.mStateArray);
    if (this.mStateArray.size() > 0) {
      paramFragment.mSavedViewState = this.mStateArray;
      this.mStateArray = null;
    } 
  }
  
  void saveNonConfig() {
    List<FragmentManagerNonConfig> list1;
    List<FragmentManagerNonConfig> list2;
    List<FragmentManagerNonConfig> list3;
    if (this.mActive != null) {
      ArrayList<Fragment> arrayList1 = null;
      ArrayList<Fragment> arrayList2 = arrayList1;
      ArrayList<Fragment> arrayList3 = arrayList2;
      byte b = 0;
      while (true) {
        list1 = (List)arrayList1;
        list2 = (List)arrayList2;
        list3 = (List)arrayList3;
        if (b < this.mActive.size()) {
          ArrayList<Fragment> arrayList4;
          Fragment fragment = (Fragment)this.mActive.valueAt(b);
          list1 = (List)arrayList1;
          ArrayList<Fragment> arrayList5 = arrayList2;
          list2 = (List)arrayList3;
          if (fragment != null) {
            FragmentManagerNonConfig fragmentManagerNonConfig;
            list3 = (List)arrayList1;
            if (fragment.mRetainInstance) {
              byte b1;
              list2 = (List)arrayList1;
              if (arrayList1 == null)
                list2 = new ArrayList(); 
              list2.add(fragment);
              if (fragment.mTarget != null) {
                b1 = fragment.mTarget.mIndex;
              } else {
                b1 = -1;
              } 
              fragment.mTargetIndex = b1;
              list3 = list2;
              if (DEBUG) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("retainNonConfig: keeping retained ");
                stringBuilder.append(fragment);
                Log.v("FragmentManager", stringBuilder.toString());
                list3 = list2;
              } 
            } 
            if (fragment.mChildFragmentManager != null) {
              fragment.mChildFragmentManager.saveNonConfig();
              fragmentManagerNonConfig = fragment.mChildFragmentManager.mSavedNonConfig;
            } else {
              fragmentManagerNonConfig = fragment.mChildNonConfig;
            } 
            arrayList1 = arrayList2;
            if (arrayList2 == null) {
              arrayList1 = arrayList2;
              if (fragmentManagerNonConfig != null) {
                arrayList2 = new ArrayList<Fragment>(this.mActive.size());
                byte b1 = 0;
                while (true) {
                  arrayList1 = arrayList2;
                  if (b1 < b) {
                    arrayList2.add(null);
                    b1++;
                    continue;
                  } 
                  break;
                } 
              } 
            } 
            if (arrayList1 != null)
              arrayList1.add(fragmentManagerNonConfig); 
            arrayList2 = arrayList3;
            if (arrayList3 == null) {
              arrayList2 = arrayList3;
              if (fragment.mViewModelStore != null) {
                arrayList3 = new ArrayList<Fragment>(this.mActive.size());
                byte b1 = 0;
                while (true) {
                  arrayList2 = arrayList3;
                  if (b1 < b) {
                    arrayList3.add(null);
                    b1++;
                    continue;
                  } 
                  break;
                } 
              } 
            } 
            list1 = list3;
            arrayList5 = arrayList1;
            arrayList4 = arrayList2;
            if (arrayList2 != null) {
              arrayList2.add(fragment.mViewModelStore);
              arrayList4 = arrayList2;
              arrayList5 = arrayList1;
              list1 = list3;
            } 
          } 
          b++;
          arrayList1 = (ArrayList)list1;
          arrayList2 = arrayList5;
          arrayList3 = arrayList4;
          continue;
        } 
        break;
      } 
    } else {
      list1 = null;
      List<FragmentManagerNonConfig> list = list1;
      list3 = list;
      list2 = list;
    } 
    if (list1 == null && list2 == null && list3 == null) {
      this.mSavedNonConfig = null;
    } else {
      this.mSavedNonConfig = new FragmentManagerNonConfig((List)list1, list2, (List)list3);
    } 
  }
  
  public void setBackStackIndex(int paramInt, BackStackRecord paramBackStackRecord) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   6: ifnonnull -> 22
    //   9: new java/util/ArrayList
    //   12: astore_3
    //   13: aload_3
    //   14: invokespecial <init> : ()V
    //   17: aload_0
    //   18: aload_3
    //   19: putfield mBackStackIndices : Ljava/util/ArrayList;
    //   22: aload_0
    //   23: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   26: invokevirtual size : ()I
    //   29: istore #4
    //   31: iload #4
    //   33: istore #5
    //   35: iload_1
    //   36: iload #4
    //   38: if_icmpge -> 106
    //   41: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   44: ifeq -> 93
    //   47: new java/lang/StringBuilder
    //   50: astore_3
    //   51: aload_3
    //   52: invokespecial <init> : ()V
    //   55: aload_3
    //   56: ldc_w 'Setting back stack index '
    //   59: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   62: pop
    //   63: aload_3
    //   64: iload_1
    //   65: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   68: pop
    //   69: aload_3
    //   70: ldc_w ' to '
    //   73: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   76: pop
    //   77: aload_3
    //   78: aload_2
    //   79: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   82: pop
    //   83: ldc 'FragmentManager'
    //   85: aload_3
    //   86: invokevirtual toString : ()Ljava/lang/String;
    //   89: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   92: pop
    //   93: aload_0
    //   94: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   97: iload_1
    //   98: aload_2
    //   99: invokevirtual set : (ILjava/lang/Object;)Ljava/lang/Object;
    //   102: pop
    //   103: goto -> 260
    //   106: iload #5
    //   108: iload_1
    //   109: if_icmpge -> 199
    //   112: aload_0
    //   113: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   116: aconst_null
    //   117: invokevirtual add : (Ljava/lang/Object;)Z
    //   120: pop
    //   121: aload_0
    //   122: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   125: ifnonnull -> 141
    //   128: new java/util/ArrayList
    //   131: astore_3
    //   132: aload_3
    //   133: invokespecial <init> : ()V
    //   136: aload_0
    //   137: aload_3
    //   138: putfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   141: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   144: ifeq -> 180
    //   147: new java/lang/StringBuilder
    //   150: astore_3
    //   151: aload_3
    //   152: invokespecial <init> : ()V
    //   155: aload_3
    //   156: ldc_w 'Adding available back stack index '
    //   159: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   162: pop
    //   163: aload_3
    //   164: iload #5
    //   166: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   169: pop
    //   170: ldc 'FragmentManager'
    //   172: aload_3
    //   173: invokevirtual toString : ()Ljava/lang/String;
    //   176: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   179: pop
    //   180: aload_0
    //   181: getfield mAvailBackStackIndices : Ljava/util/ArrayList;
    //   184: iload #5
    //   186: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   189: invokevirtual add : (Ljava/lang/Object;)Z
    //   192: pop
    //   193: iinc #5, 1
    //   196: goto -> 106
    //   199: getstatic android/support/v4/app/FragmentManagerImpl.DEBUG : Z
    //   202: ifeq -> 251
    //   205: new java/lang/StringBuilder
    //   208: astore_3
    //   209: aload_3
    //   210: invokespecial <init> : ()V
    //   213: aload_3
    //   214: ldc_w 'Adding back stack index '
    //   217: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   220: pop
    //   221: aload_3
    //   222: iload_1
    //   223: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   226: pop
    //   227: aload_3
    //   228: ldc_w ' with '
    //   231: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   234: pop
    //   235: aload_3
    //   236: aload_2
    //   237: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   240: pop
    //   241: ldc 'FragmentManager'
    //   243: aload_3
    //   244: invokevirtual toString : ()Ljava/lang/String;
    //   247: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
    //   250: pop
    //   251: aload_0
    //   252: getfield mBackStackIndices : Ljava/util/ArrayList;
    //   255: aload_2
    //   256: invokevirtual add : (Ljava/lang/Object;)Z
    //   259: pop
    //   260: aload_0
    //   261: monitorexit
    //   262: return
    //   263: astore_2
    //   264: aload_0
    //   265: monitorexit
    //   266: aload_2
    //   267: athrow
    // Exception table:
    //   from	to	target	type
    //   2	22	263	finally
    //   22	31	263	finally
    //   41	93	263	finally
    //   93	103	263	finally
    //   112	141	263	finally
    //   141	180	263	finally
    //   180	193	263	finally
    //   199	251	263	finally
    //   251	260	263	finally
    //   260	262	263	finally
    //   264	266	263	finally
  }
  
  public void setPrimaryNavigationFragment(Fragment paramFragment) {
    if (paramFragment == null || (this.mActive.get(paramFragment.mIndex) == paramFragment && (paramFragment.mHost == null || paramFragment.getFragmentManager() == this))) {
      this.mPrimaryNav = paramFragment;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(paramFragment);
    stringBuilder.append(" is not an active fragment of FragmentManager ");
    stringBuilder.append(this);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void showFragment(Fragment paramFragment) {
    if (DEBUG) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("show: ");
      stringBuilder.append(paramFragment);
      Log.v("FragmentManager", stringBuilder.toString());
    } 
    if (paramFragment.mHidden) {
      paramFragment.mHidden = false;
      paramFragment.mHiddenChanged ^= 0x1;
    } 
  }
  
  void startPendingDeferredFragments() {
    if (this.mActive == null)
      return; 
    for (byte b = 0; b < this.mActive.size(); b++) {
      Fragment fragment = (Fragment)this.mActive.valueAt(b);
      if (fragment != null)
        performPendingDeferredStart(fragment); 
    } 
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(128);
    stringBuilder.append("FragmentManager{");
    stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
    stringBuilder.append(" in ");
    Fragment fragment = this.mParent;
    if (fragment != null) {
      DebugUtils.buildShortClassTag(fragment, stringBuilder);
    } else {
      DebugUtils.buildShortClassTag(this.mHost, stringBuilder);
    } 
    stringBuilder.append("}}");
    return stringBuilder.toString();
  }
  
  public void unregisterFragmentLifecycleCallbacks(FragmentManager.FragmentLifecycleCallbacks paramFragmentLifecycleCallbacks) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mLifecycleCallbacks : Ljava/util/concurrent/CopyOnWriteArrayList;
    //   4: astore_2
    //   5: aload_2
    //   6: monitorenter
    //   7: iconst_0
    //   8: istore_3
    //   9: aload_0
    //   10: getfield mLifecycleCallbacks : Ljava/util/concurrent/CopyOnWriteArrayList;
    //   13: invokevirtual size : ()I
    //   16: istore #4
    //   18: iload_3
    //   19: iload #4
    //   21: if_icmpge -> 60
    //   24: aload_0
    //   25: getfield mLifecycleCallbacks : Ljava/util/concurrent/CopyOnWriteArrayList;
    //   28: iload_3
    //   29: invokevirtual get : (I)Ljava/lang/Object;
    //   32: checkcast android/support/v4/util/Pair
    //   35: getfield first : Ljava/lang/Object;
    //   38: aload_1
    //   39: if_acmpne -> 54
    //   42: aload_0
    //   43: getfield mLifecycleCallbacks : Ljava/util/concurrent/CopyOnWriteArrayList;
    //   46: iload_3
    //   47: invokevirtual remove : (I)Ljava/lang/Object;
    //   50: pop
    //   51: goto -> 60
    //   54: iinc #3, 1
    //   57: goto -> 18
    //   60: aload_2
    //   61: monitorexit
    //   62: return
    //   63: astore_1
    //   64: aload_2
    //   65: monitorexit
    //   66: aload_1
    //   67: athrow
    // Exception table:
    //   from	to	target	type
    //   9	18	63	finally
    //   24	51	63	finally
    //   60	62	63	finally
    //   64	66	63	finally
  }
  
  private static class AnimateOnHWLayerIfNeededListener extends AnimationListenerWrapper {
    View mView;
    
    AnimateOnHWLayerIfNeededListener(View param1View, Animation.AnimationListener param1AnimationListener) {
      super(param1AnimationListener);
      this.mView = param1View;
    }
    
    public void onAnimationEnd(Animation param1Animation) {
      if (ViewCompat.isAttachedToWindow(this.mView) || Build.VERSION.SDK_INT >= 24) {
        this.mView.post(new Runnable() {
              public void run() {
                FragmentManagerImpl.AnimateOnHWLayerIfNeededListener.this.mView.setLayerType(0, null);
              }
            });
      } else {
        this.mView.setLayerType(0, null);
      } 
      super.onAnimationEnd(param1Animation);
    }
  }
  
  class null implements Runnable {
    public void run() {
      this.this$0.mView.setLayerType(0, null);
    }
  }
  
  private static class AnimationListenerWrapper implements Animation.AnimationListener {
    private final Animation.AnimationListener mWrapped;
    
    private AnimationListenerWrapper(Animation.AnimationListener param1AnimationListener) {
      this.mWrapped = param1AnimationListener;
    }
    
    public void onAnimationEnd(Animation param1Animation) {
      Animation.AnimationListener animationListener = this.mWrapped;
      if (animationListener != null)
        animationListener.onAnimationEnd(param1Animation); 
    }
    
    public void onAnimationRepeat(Animation param1Animation) {
      Animation.AnimationListener animationListener = this.mWrapped;
      if (animationListener != null)
        animationListener.onAnimationRepeat(param1Animation); 
    }
    
    public void onAnimationStart(Animation param1Animation) {
      Animation.AnimationListener animationListener = this.mWrapped;
      if (animationListener != null)
        animationListener.onAnimationStart(param1Animation); 
    }
  }
  
  private static class AnimationOrAnimator {
    public final Animation animation = null;
    
    public final Animator animator;
    
    private AnimationOrAnimator(Animator param1Animator) {
      this.animator = param1Animator;
      if (param1Animator != null)
        return; 
      throw new IllegalStateException("Animator cannot be null");
    }
    
    private AnimationOrAnimator(Animation param1Animation) {
      this.animator = null;
      if (param1Animation != null)
        return; 
      throw new IllegalStateException("Animation cannot be null");
    }
  }
  
  private static class AnimatorOnHWLayerIfNeededListener extends AnimatorListenerAdapter {
    View mView;
    
    AnimatorOnHWLayerIfNeededListener(View param1View) {
      this.mView = param1View;
    }
    
    public void onAnimationEnd(Animator param1Animator) {
      this.mView.setLayerType(0, null);
      param1Animator.removeListener((Animator.AnimatorListener)this);
    }
    
    public void onAnimationStart(Animator param1Animator) {
      this.mView.setLayerType(2, null);
    }
  }
  
  private static class EndViewTransitionAnimator extends AnimationSet implements Runnable {
    private final View mChild;
    
    private boolean mEnded;
    
    private final ViewGroup mParent;
    
    private boolean mTransitionEnded;
    
    EndViewTransitionAnimator(Animation param1Animation, ViewGroup param1ViewGroup, View param1View) {
      super(false);
      this.mParent = param1ViewGroup;
      this.mChild = param1View;
      addAnimation(param1Animation);
    }
    
    public boolean getTransformation(long param1Long, Transformation param1Transformation) {
      if (this.mEnded)
        return this.mTransitionEnded ^ true; 
      if (!super.getTransformation(param1Long, param1Transformation)) {
        this.mEnded = true;
        OneShotPreDrawListener.add((View)this.mParent, this);
      } 
      return true;
    }
    
    public boolean getTransformation(long param1Long, Transformation param1Transformation, float param1Float) {
      if (this.mEnded)
        return this.mTransitionEnded ^ true; 
      if (!super.getTransformation(param1Long, param1Transformation, param1Float)) {
        this.mEnded = true;
        OneShotPreDrawListener.add((View)this.mParent, this);
      } 
      return true;
    }
    
    public void run() {
      this.mParent.endViewTransition(this.mChild);
      this.mTransitionEnded = true;
    }
  }
  
  static class FragmentTag {
    public static final int[] Fragment = new int[] { 16842755, 16842960, 16842961 };
    
    public static final int Fragment_id = 1;
    
    public static final int Fragment_name = 0;
    
    public static final int Fragment_tag = 2;
  }
  
  static interface OpGenerator {
    boolean generateOps(ArrayList<BackStackRecord> param1ArrayList, ArrayList<Boolean> param1ArrayList1);
  }
  
  private class PopBackStackState implements OpGenerator {
    final int mFlags;
    
    final int mId;
    
    final String mName;
    
    PopBackStackState(String param1String, int param1Int1, int param1Int2) {
      this.mName = param1String;
      this.mId = param1Int1;
      this.mFlags = param1Int2;
    }
    
    public boolean generateOps(ArrayList<BackStackRecord> param1ArrayList, ArrayList<Boolean> param1ArrayList1) {
      if (FragmentManagerImpl.this.mPrimaryNav != null && this.mId < 0 && this.mName == null) {
        FragmentManager fragmentManager = FragmentManagerImpl.this.mPrimaryNav.peekChildFragmentManager();
        if (fragmentManager != null && fragmentManager.popBackStackImmediate())
          return false; 
      } 
      return FragmentManagerImpl.this.popBackStackState(param1ArrayList, param1ArrayList1, this.mName, this.mId, this.mFlags);
    }
  }
  
  static class StartEnterTransitionListener implements Fragment.OnStartEnterTransitionListener {
    private final boolean mIsBack;
    
    private int mNumPostponed;
    
    private final BackStackRecord mRecord;
    
    StartEnterTransitionListener(BackStackRecord param1BackStackRecord, boolean param1Boolean) {
      this.mIsBack = param1Boolean;
      this.mRecord = param1BackStackRecord;
    }
    
    public void cancelTransaction() {
      this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, false, false);
    }
    
    public void completeTransaction() {
      int i = this.mNumPostponed;
      byte b = 0;
      if (i > 0) {
        i = 1;
      } else {
        i = 0;
      } 
      FragmentManagerImpl fragmentManagerImpl = this.mRecord.mManager;
      int j = fragmentManagerImpl.mAdded.size();
      while (b < j) {
        Fragment fragment = fragmentManagerImpl.mAdded.get(b);
        fragment.setOnStartEnterTransitionListener(null);
        if (i != 0 && fragment.isPostponed())
          fragment.startPostponedEnterTransition(); 
        b++;
      } 
      this.mRecord.mManager.completeExecute(this.mRecord, this.mIsBack, i ^ 0x1, true);
    }
    
    public boolean isReady() {
      boolean bool;
      if (this.mNumPostponed == 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public void onStartEnterTransition() {
      int i = this.mNumPostponed - 1;
      this.mNumPostponed = i;
      if (i != 0)
        return; 
      this.mRecord.mManager.scheduleCommit();
    }
    
    public void startListening() {
      this.mNumPostponed++;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\app\FragmentManagerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */