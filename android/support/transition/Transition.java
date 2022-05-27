package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.xmlpull.v1.XmlPullParser;

public abstract class Transition implements Cloneable {
  static final boolean DBG = false;
  
  private static final int[] DEFAULT_MATCH_ORDER = new int[] { 2, 1, 3, 4 };
  
  private static final String LOG_TAG = "Transition";
  
  private static final int MATCH_FIRST = 1;
  
  public static final int MATCH_ID = 3;
  
  private static final String MATCH_ID_STR = "id";
  
  public static final int MATCH_INSTANCE = 1;
  
  private static final String MATCH_INSTANCE_STR = "instance";
  
  public static final int MATCH_ITEM_ID = 4;
  
  private static final String MATCH_ITEM_ID_STR = "itemId";
  
  private static final int MATCH_LAST = 4;
  
  public static final int MATCH_NAME = 2;
  
  private static final String MATCH_NAME_STR = "name";
  
  private static final PathMotion STRAIGHT_PATH_MOTION = new PathMotion() {
      public Path getPath(float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
        Path path = new Path();
        path.moveTo(param1Float1, param1Float2);
        path.lineTo(param1Float3, param1Float4);
        return path;
      }
    };
  
  private static ThreadLocal<ArrayMap<Animator, AnimationInfo>> sRunningAnimators = new ThreadLocal<ArrayMap<Animator, AnimationInfo>>();
  
  private ArrayList<Animator> mAnimators = new ArrayList<Animator>();
  
  boolean mCanRemoveViews = false;
  
  private ArrayList<Animator> mCurrentAnimators = new ArrayList<Animator>();
  
  long mDuration = -1L;
  
  private TransitionValuesMaps mEndValues = new TransitionValuesMaps();
  
  private ArrayList<TransitionValues> mEndValuesList;
  
  private boolean mEnded = false;
  
  private EpicenterCallback mEpicenterCallback;
  
  private TimeInterpolator mInterpolator = null;
  
  private ArrayList<TransitionListener> mListeners = null;
  
  private int[] mMatchOrder = DEFAULT_MATCH_ORDER;
  
  private String mName = getClass().getName();
  
  private ArrayMap<String, String> mNameOverrides;
  
  private int mNumInstances = 0;
  
  TransitionSet mParent = null;
  
  private PathMotion mPathMotion = STRAIGHT_PATH_MOTION;
  
  private boolean mPaused = false;
  
  TransitionPropagation mPropagation;
  
  private ViewGroup mSceneRoot = null;
  
  private long mStartDelay = -1L;
  
  private TransitionValuesMaps mStartValues = new TransitionValuesMaps();
  
  private ArrayList<TransitionValues> mStartValuesList;
  
  private ArrayList<View> mTargetChildExcludes = null;
  
  private ArrayList<View> mTargetExcludes = null;
  
  private ArrayList<Integer> mTargetIdChildExcludes = null;
  
  private ArrayList<Integer> mTargetIdExcludes = null;
  
  ArrayList<Integer> mTargetIds = new ArrayList<Integer>();
  
  private ArrayList<String> mTargetNameExcludes = null;
  
  private ArrayList<String> mTargetNames = null;
  
  private ArrayList<Class> mTargetTypeChildExcludes = null;
  
  private ArrayList<Class> mTargetTypeExcludes = null;
  
  private ArrayList<Class> mTargetTypes = null;
  
  ArrayList<View> mTargets = new ArrayList<View>();
  
  public Transition() {}
  
  public Transition(Context paramContext, AttributeSet paramAttributeSet) {
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, Styleable.TRANSITION);
    XmlResourceParser xmlResourceParser = (XmlResourceParser)paramAttributeSet;
    long l = TypedArrayUtils.getNamedInt(typedArray, (XmlPullParser)xmlResourceParser, "duration", 1, -1);
    if (l >= 0L)
      setDuration(l); 
    l = TypedArrayUtils.getNamedInt(typedArray, (XmlPullParser)xmlResourceParser, "startDelay", 2, -1);
    if (l > 0L)
      setStartDelay(l); 
    int i = TypedArrayUtils.getNamedResourceId(typedArray, (XmlPullParser)xmlResourceParser, "interpolator", 0, 0);
    if (i > 0)
      setInterpolator((TimeInterpolator)AnimationUtils.loadInterpolator(paramContext, i)); 
    String str = TypedArrayUtils.getNamedString(typedArray, (XmlPullParser)xmlResourceParser, "matchOrder", 3);
    if (str != null)
      setMatchOrder(parseMatchOrder(str)); 
    typedArray.recycle();
  }
  
  private void addUnmatched(ArrayMap<View, TransitionValues> paramArrayMap1, ArrayMap<View, TransitionValues> paramArrayMap2) {
    byte b3;
    byte b1 = 0;
    byte b2 = 0;
    while (true) {
      b3 = b1;
      if (b2 < paramArrayMap1.size()) {
        TransitionValues transitionValues = (TransitionValues)paramArrayMap1.valueAt(b2);
        if (isValidTarget(transitionValues.view)) {
          this.mStartValuesList.add(transitionValues);
          this.mEndValuesList.add(null);
        } 
        b2++;
        continue;
      } 
      break;
    } 
    while (b3 < paramArrayMap2.size()) {
      TransitionValues transitionValues = (TransitionValues)paramArrayMap2.valueAt(b3);
      if (isValidTarget(transitionValues.view)) {
        this.mEndValuesList.add(transitionValues);
        this.mStartValuesList.add(null);
      } 
      b3++;
    } 
  }
  
  private static void addViewValues(TransitionValuesMaps paramTransitionValuesMaps, View paramView, TransitionValues paramTransitionValues) {
    paramTransitionValuesMaps.mViewValues.put(paramView, paramTransitionValues);
    int i = paramView.getId();
    if (i >= 0)
      if (paramTransitionValuesMaps.mIdValues.indexOfKey(i) >= 0) {
        paramTransitionValuesMaps.mIdValues.put(i, null);
      } else {
        paramTransitionValuesMaps.mIdValues.put(i, paramView);
      }  
    String str = ViewCompat.getTransitionName(paramView);
    if (str != null)
      if (paramTransitionValuesMaps.mNameValues.containsKey(str)) {
        paramTransitionValuesMaps.mNameValues.put(str, null);
      } else {
        paramTransitionValuesMaps.mNameValues.put(str, paramView);
      }  
    if (paramView.getParent() instanceof ListView) {
      ListView listView = (ListView)paramView.getParent();
      if (listView.getAdapter().hasStableIds()) {
        long l = listView.getItemIdAtPosition(listView.getPositionForView(paramView));
        if (paramTransitionValuesMaps.mItemIdValues.indexOfKey(l) >= 0) {
          paramView = (View)paramTransitionValuesMaps.mItemIdValues.get(l);
          if (paramView != null) {
            ViewCompat.setHasTransientState(paramView, false);
            paramTransitionValuesMaps.mItemIdValues.put(l, null);
          } 
        } else {
          ViewCompat.setHasTransientState(paramView, true);
          paramTransitionValuesMaps.mItemIdValues.put(l, paramView);
        } 
      } 
    } 
  }
  
  private static boolean alreadyContains(int[] paramArrayOfint, int paramInt) {
    int i = paramArrayOfint[paramInt];
    for (byte b = 0; b < paramInt; b++) {
      if (paramArrayOfint[b] == i)
        return true; 
    } 
    return false;
  }
  
  private void captureHierarchy(View paramView, boolean paramBoolean) {
    if (paramView == null)
      return; 
    int i = paramView.getId();
    ArrayList<Integer> arrayList2 = this.mTargetIdExcludes;
    if (arrayList2 != null && arrayList2.contains(Integer.valueOf(i)))
      return; 
    ArrayList<View> arrayList1 = this.mTargetExcludes;
    if (arrayList1 != null && arrayList1.contains(paramView))
      return; 
    ArrayList<Class> arrayList = this.mTargetTypeExcludes;
    byte b = 0;
    if (arrayList != null) {
      int j = arrayList.size();
      for (byte b1 = 0; b1 < j; b1++) {
        if (((Class)this.mTargetTypeExcludes.get(b1)).isInstance(paramView))
          return; 
      } 
    } 
    if (paramView.getParent() instanceof ViewGroup) {
      TransitionValues transitionValues = new TransitionValues();
      transitionValues.view = paramView;
      if (paramBoolean) {
        captureStartValues(transitionValues);
      } else {
        captureEndValues(transitionValues);
      } 
      transitionValues.mTargetedTransitions.add(this);
      capturePropagationValues(transitionValues);
      if (paramBoolean) {
        addViewValues(this.mStartValues, paramView, transitionValues);
      } else {
        addViewValues(this.mEndValues, paramView, transitionValues);
      } 
    } 
    if (paramView instanceof ViewGroup) {
      ArrayList<Integer> arrayList5 = this.mTargetIdChildExcludes;
      if (arrayList5 != null && arrayList5.contains(Integer.valueOf(i)))
        return; 
      ArrayList<View> arrayList4 = this.mTargetChildExcludes;
      if (arrayList4 != null && arrayList4.contains(paramView))
        return; 
      ArrayList<Class> arrayList3 = this.mTargetTypeChildExcludes;
      if (arrayList3 != null) {
        i = arrayList3.size();
        for (byte b2 = 0; b2 < i; b2++) {
          if (((Class)this.mTargetTypeChildExcludes.get(b2)).isInstance(paramView))
            return; 
        } 
      } 
      ViewGroup viewGroup = (ViewGroup)paramView;
      for (byte b1 = b; b1 < viewGroup.getChildCount(); b1++)
        captureHierarchy(viewGroup.getChildAt(b1), paramBoolean); 
    } 
  }
  
  private ArrayList<Integer> excludeId(ArrayList<Integer> paramArrayList, int paramInt, boolean paramBoolean) {
    ArrayList<Integer> arrayList = paramArrayList;
    if (paramInt > 0)
      if (paramBoolean) {
        arrayList = ArrayListManager.add(paramArrayList, Integer.valueOf(paramInt));
      } else {
        arrayList = ArrayListManager.remove(paramArrayList, Integer.valueOf(paramInt));
      }  
    return arrayList;
  }
  
  private static <T> ArrayList<T> excludeObject(ArrayList<T> paramArrayList, T paramT, boolean paramBoolean) {
    ArrayList<T> arrayList = paramArrayList;
    if (paramT != null)
      if (paramBoolean) {
        arrayList = ArrayListManager.add(paramArrayList, paramT);
      } else {
        arrayList = ArrayListManager.remove(paramArrayList, paramT);
      }  
    return arrayList;
  }
  
  private ArrayList<Class> excludeType(ArrayList<Class> paramArrayList, Class<?> paramClass, boolean paramBoolean) {
    ArrayList<Class> arrayList = paramArrayList;
    if (paramClass != null)
      if (paramBoolean) {
        arrayList = ArrayListManager.add(paramArrayList, paramClass);
      } else {
        arrayList = ArrayListManager.remove(paramArrayList, paramClass);
      }  
    return arrayList;
  }
  
  private ArrayList<View> excludeView(ArrayList<View> paramArrayList, View paramView, boolean paramBoolean) {
    ArrayList<View> arrayList = paramArrayList;
    if (paramView != null)
      if (paramBoolean) {
        arrayList = ArrayListManager.add(paramArrayList, paramView);
      } else {
        arrayList = ArrayListManager.remove(paramArrayList, paramView);
      }  
    return arrayList;
  }
  
  private static ArrayMap<Animator, AnimationInfo> getRunningAnimators() {
    ArrayMap<Animator, AnimationInfo> arrayMap1 = sRunningAnimators.get();
    ArrayMap<Animator, AnimationInfo> arrayMap2 = arrayMap1;
    if (arrayMap1 == null) {
      arrayMap2 = new ArrayMap();
      sRunningAnimators.set(arrayMap2);
    } 
    return arrayMap2;
  }
  
  private static boolean isValidMatch(int paramInt) {
    boolean bool = true;
    if (paramInt < 1 || paramInt > 4)
      bool = false; 
    return bool;
  }
  
  private static boolean isValueChanged(TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2, String paramString) {
    int i;
    paramTransitionValues1 = (TransitionValues)paramTransitionValues1.values.get(paramString);
    paramTransitionValues2 = (TransitionValues)paramTransitionValues2.values.get(paramString);
    byte b = 1;
    if (paramTransitionValues1 == null && paramTransitionValues2 == null) {
      i = 0;
    } else {
      i = b;
      if (paramTransitionValues1 != null)
        if (paramTransitionValues2 == null) {
          i = b;
        } else {
          i = true ^ paramTransitionValues1.equals(paramTransitionValues2);
        }  
    } 
    return i;
  }
  
  private void matchIds(ArrayMap<View, TransitionValues> paramArrayMap1, ArrayMap<View, TransitionValues> paramArrayMap2, SparseArray<View> paramSparseArray1, SparseArray<View> paramSparseArray2) {
    int i = paramSparseArray1.size();
    for (byte b = 0; b < i; b++) {
      View view = (View)paramSparseArray1.valueAt(b);
      if (view != null && isValidTarget(view)) {
        View view1 = (View)paramSparseArray2.get(paramSparseArray1.keyAt(b));
        if (view1 != null && isValidTarget(view1)) {
          TransitionValues transitionValues1 = (TransitionValues)paramArrayMap1.get(view);
          TransitionValues transitionValues2 = (TransitionValues)paramArrayMap2.get(view1);
          if (transitionValues1 != null && transitionValues2 != null) {
            this.mStartValuesList.add(transitionValues1);
            this.mEndValuesList.add(transitionValues2);
            paramArrayMap1.remove(view);
            paramArrayMap2.remove(view1);
          } 
        } 
      } 
    } 
  }
  
  private void matchInstances(ArrayMap<View, TransitionValues> paramArrayMap1, ArrayMap<View, TransitionValues> paramArrayMap2) {
    for (int i = paramArrayMap1.size() - 1; i >= 0; i--) {
      View view = (View)paramArrayMap1.keyAt(i);
      if (view != null && isValidTarget(view)) {
        TransitionValues transitionValues = (TransitionValues)paramArrayMap2.remove(view);
        if (transitionValues != null && transitionValues.view != null && isValidTarget(transitionValues.view)) {
          TransitionValues transitionValues1 = (TransitionValues)paramArrayMap1.removeAt(i);
          this.mStartValuesList.add(transitionValues1);
          this.mEndValuesList.add(transitionValues);
        } 
      } 
    } 
  }
  
  private void matchItemIds(ArrayMap<View, TransitionValues> paramArrayMap1, ArrayMap<View, TransitionValues> paramArrayMap2, LongSparseArray<View> paramLongSparseArray1, LongSparseArray<View> paramLongSparseArray2) {
    int i = paramLongSparseArray1.size();
    for (byte b = 0; b < i; b++) {
      View view = (View)paramLongSparseArray1.valueAt(b);
      if (view != null && isValidTarget(view)) {
        View view1 = (View)paramLongSparseArray2.get(paramLongSparseArray1.keyAt(b));
        if (view1 != null && isValidTarget(view1)) {
          TransitionValues transitionValues1 = (TransitionValues)paramArrayMap1.get(view);
          TransitionValues transitionValues2 = (TransitionValues)paramArrayMap2.get(view1);
          if (transitionValues1 != null && transitionValues2 != null) {
            this.mStartValuesList.add(transitionValues1);
            this.mEndValuesList.add(transitionValues2);
            paramArrayMap1.remove(view);
            paramArrayMap2.remove(view1);
          } 
        } 
      } 
    } 
  }
  
  private void matchNames(ArrayMap<View, TransitionValues> paramArrayMap1, ArrayMap<View, TransitionValues> paramArrayMap2, ArrayMap<String, View> paramArrayMap3, ArrayMap<String, View> paramArrayMap4) {
    int i = paramArrayMap3.size();
    for (byte b = 0; b < i; b++) {
      View view = (View)paramArrayMap3.valueAt(b);
      if (view != null && isValidTarget(view)) {
        View view1 = (View)paramArrayMap4.get(paramArrayMap3.keyAt(b));
        if (view1 != null && isValidTarget(view1)) {
          TransitionValues transitionValues1 = (TransitionValues)paramArrayMap1.get(view);
          TransitionValues transitionValues2 = (TransitionValues)paramArrayMap2.get(view1);
          if (transitionValues1 != null && transitionValues2 != null) {
            this.mStartValuesList.add(transitionValues1);
            this.mEndValuesList.add(transitionValues2);
            paramArrayMap1.remove(view);
            paramArrayMap2.remove(view1);
          } 
        } 
      } 
    } 
  }
  
  private void matchStartAndEnd(TransitionValuesMaps paramTransitionValuesMaps1, TransitionValuesMaps paramTransitionValuesMaps2) {
    ArrayMap<View, TransitionValues> arrayMap1 = new ArrayMap((SimpleArrayMap)paramTransitionValuesMaps1.mViewValues);
    ArrayMap<View, TransitionValues> arrayMap2 = new ArrayMap((SimpleArrayMap)paramTransitionValuesMaps2.mViewValues);
    byte b = 0;
    while (true) {
      int[] arrayOfInt = this.mMatchOrder;
      if (b < arrayOfInt.length) {
        int i = arrayOfInt[b];
        if (i != 1) {
          if (i != 2) {
            if (i != 3) {
              if (i == 4)
                matchItemIds(arrayMap1, arrayMap2, paramTransitionValuesMaps1.mItemIdValues, paramTransitionValuesMaps2.mItemIdValues); 
            } else {
              matchIds(arrayMap1, arrayMap2, paramTransitionValuesMaps1.mIdValues, paramTransitionValuesMaps2.mIdValues);
            } 
          } else {
            matchNames(arrayMap1, arrayMap2, paramTransitionValuesMaps1.mNameValues, paramTransitionValuesMaps2.mNameValues);
          } 
        } else {
          matchInstances(arrayMap1, arrayMap2);
        } 
        b++;
        continue;
      } 
      addUnmatched(arrayMap1, arrayMap2);
      return;
    } 
  }
  
  private static int[] parseMatchOrder(String paramString) {
    StringBuilder stringBuilder;
    StringTokenizer stringTokenizer = new StringTokenizer(paramString, ",");
    int[] arrayOfInt = new int[stringTokenizer.countTokens()];
    for (byte b = 0; stringTokenizer.hasMoreTokens(); b++) {
      String str = stringTokenizer.nextToken().trim();
      if ("id".equalsIgnoreCase(str)) {
        arrayOfInt[b] = 3;
      } else if ("instance".equalsIgnoreCase(str)) {
        arrayOfInt[b] = 1;
      } else if ("name".equalsIgnoreCase(str)) {
        arrayOfInt[b] = 2;
      } else if ("itemId".equalsIgnoreCase(str)) {
        arrayOfInt[b] = 4;
      } else {
        int[] arrayOfInt1;
        if (str.isEmpty()) {
          arrayOfInt1 = new int[arrayOfInt.length - 1];
          System.arraycopy(arrayOfInt, 0, arrayOfInt1, 0, b);
          b--;
          arrayOfInt = arrayOfInt1;
        } else {
          stringBuilder = new StringBuilder();
          stringBuilder.append("Unknown match type in matchOrder: '");
          stringBuilder.append((String)arrayOfInt1);
          stringBuilder.append("'");
          throw new InflateException(stringBuilder.toString());
        } 
      } 
    } 
    return (int[])stringBuilder;
  }
  
  private void runAnimator(Animator paramAnimator, final ArrayMap<Animator, AnimationInfo> runningAnimators) {
    if (paramAnimator != null) {
      paramAnimator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator param1Animator) {
              runningAnimators.remove(param1Animator);
              Transition.this.mCurrentAnimators.remove(param1Animator);
            }
            
            public void onAnimationStart(Animator param1Animator) {
              Transition.this.mCurrentAnimators.add(param1Animator);
            }
          });
      animate(paramAnimator);
    } 
  }
  
  public Transition addListener(TransitionListener paramTransitionListener) {
    if (this.mListeners == null)
      this.mListeners = new ArrayList<TransitionListener>(); 
    this.mListeners.add(paramTransitionListener);
    return this;
  }
  
  public Transition addTarget(int paramInt) {
    if (paramInt != 0)
      this.mTargetIds.add(Integer.valueOf(paramInt)); 
    return this;
  }
  
  public Transition addTarget(View paramView) {
    this.mTargets.add(paramView);
    return this;
  }
  
  public Transition addTarget(Class paramClass) {
    if (this.mTargetTypes == null)
      this.mTargetTypes = new ArrayList<Class<?>>(); 
    this.mTargetTypes.add(paramClass);
    return this;
  }
  
  public Transition addTarget(String paramString) {
    if (this.mTargetNames == null)
      this.mTargetNames = new ArrayList<String>(); 
    this.mTargetNames.add(paramString);
    return this;
  }
  
  protected void animate(Animator paramAnimator) {
    if (paramAnimator == null) {
      end();
    } else {
      if (getDuration() >= 0L)
        paramAnimator.setDuration(getDuration()); 
      if (getStartDelay() >= 0L)
        paramAnimator.setStartDelay(getStartDelay()); 
      if (getInterpolator() != null)
        paramAnimator.setInterpolator(getInterpolator()); 
      paramAnimator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator param1Animator) {
              Transition.this.end();
              param1Animator.removeListener((Animator.AnimatorListener)this);
            }
          });
      paramAnimator.start();
    } 
  }
  
  protected void cancel() {
    int i;
    for (i = this.mCurrentAnimators.size() - 1; i >= 0; i--)
      ((Animator)this.mCurrentAnimators.get(i)).cancel(); 
    ArrayList<TransitionListener> arrayList = this.mListeners;
    if (arrayList != null && arrayList.size() > 0) {
      arrayList = (ArrayList<TransitionListener>)this.mListeners.clone();
      int j = arrayList.size();
      for (i = 0; i < j; i++)
        ((TransitionListener)arrayList.get(i)).onTransitionCancel(this); 
    } 
  }
  
  public abstract void captureEndValues(TransitionValues paramTransitionValues);
  
  void capturePropagationValues(TransitionValues paramTransitionValues) {
    if (this.mPropagation != null && !paramTransitionValues.values.isEmpty()) {
      String[] arrayOfString = this.mPropagation.getPropagationProperties();
      if (arrayOfString == null)
        return; 
      boolean bool = false;
      byte b = 0;
      while (true) {
        if (b < arrayOfString.length) {
          if (!paramTransitionValues.values.containsKey(arrayOfString[b])) {
            b = bool;
            break;
          } 
          b++;
          continue;
        } 
        b = 1;
        break;
      } 
      if (b == 0)
        this.mPropagation.captureValues(paramTransitionValues); 
    } 
  }
  
  public abstract void captureStartValues(TransitionValues paramTransitionValues);
  
  void captureValues(ViewGroup paramViewGroup, boolean paramBoolean) {
    // Byte code:
    //   0: aload_0
    //   1: iload_2
    //   2: invokevirtual clearValues : (Z)V
    //   5: aload_0
    //   6: getfield mTargetIds : Ljava/util/ArrayList;
    //   9: invokevirtual size : ()I
    //   12: istore_3
    //   13: iconst_0
    //   14: istore #4
    //   16: iload_3
    //   17: ifgt -> 30
    //   20: aload_0
    //   21: getfield mTargets : Ljava/util/ArrayList;
    //   24: invokevirtual size : ()I
    //   27: ifle -> 71
    //   30: aload_0
    //   31: getfield mTargetNames : Ljava/util/ArrayList;
    //   34: astore #5
    //   36: aload #5
    //   38: ifnull -> 49
    //   41: aload #5
    //   43: invokevirtual isEmpty : ()Z
    //   46: ifeq -> 71
    //   49: aload_0
    //   50: getfield mTargetTypes : Ljava/util/ArrayList;
    //   53: astore #5
    //   55: aload #5
    //   57: ifnull -> 80
    //   60: aload #5
    //   62: invokevirtual isEmpty : ()Z
    //   65: ifeq -> 71
    //   68: goto -> 80
    //   71: aload_0
    //   72: aload_1
    //   73: iload_2
    //   74: invokespecial captureHierarchy : (Landroid/view/View;Z)V
    //   77: goto -> 312
    //   80: iconst_0
    //   81: istore_3
    //   82: iload_3
    //   83: aload_0
    //   84: getfield mTargetIds : Ljava/util/ArrayList;
    //   87: invokevirtual size : ()I
    //   90: if_icmpge -> 204
    //   93: aload_1
    //   94: aload_0
    //   95: getfield mTargetIds : Ljava/util/ArrayList;
    //   98: iload_3
    //   99: invokevirtual get : (I)Ljava/lang/Object;
    //   102: checkcast java/lang/Integer
    //   105: invokevirtual intValue : ()I
    //   108: invokevirtual findViewById : (I)Landroid/view/View;
    //   111: astore #5
    //   113: aload #5
    //   115: ifnull -> 198
    //   118: new android/support/transition/TransitionValues
    //   121: dup
    //   122: invokespecial <init> : ()V
    //   125: astore #6
    //   127: aload #6
    //   129: aload #5
    //   131: putfield view : Landroid/view/View;
    //   134: iload_2
    //   135: ifeq -> 147
    //   138: aload_0
    //   139: aload #6
    //   141: invokevirtual captureStartValues : (Landroid/support/transition/TransitionValues;)V
    //   144: goto -> 153
    //   147: aload_0
    //   148: aload #6
    //   150: invokevirtual captureEndValues : (Landroid/support/transition/TransitionValues;)V
    //   153: aload #6
    //   155: getfield mTargetedTransitions : Ljava/util/ArrayList;
    //   158: aload_0
    //   159: invokevirtual add : (Ljava/lang/Object;)Z
    //   162: pop
    //   163: aload_0
    //   164: aload #6
    //   166: invokevirtual capturePropagationValues : (Landroid/support/transition/TransitionValues;)V
    //   169: iload_2
    //   170: ifeq -> 187
    //   173: aload_0
    //   174: getfield mStartValues : Landroid/support/transition/TransitionValuesMaps;
    //   177: aload #5
    //   179: aload #6
    //   181: invokestatic addViewValues : (Landroid/support/transition/TransitionValuesMaps;Landroid/view/View;Landroid/support/transition/TransitionValues;)V
    //   184: goto -> 198
    //   187: aload_0
    //   188: getfield mEndValues : Landroid/support/transition/TransitionValuesMaps;
    //   191: aload #5
    //   193: aload #6
    //   195: invokestatic addViewValues : (Landroid/support/transition/TransitionValuesMaps;Landroid/view/View;Landroid/support/transition/TransitionValues;)V
    //   198: iinc #3, 1
    //   201: goto -> 82
    //   204: iconst_0
    //   205: istore_3
    //   206: iload_3
    //   207: aload_0
    //   208: getfield mTargets : Ljava/util/ArrayList;
    //   211: invokevirtual size : ()I
    //   214: if_icmpge -> 312
    //   217: aload_0
    //   218: getfield mTargets : Ljava/util/ArrayList;
    //   221: iload_3
    //   222: invokevirtual get : (I)Ljava/lang/Object;
    //   225: checkcast android/view/View
    //   228: astore_1
    //   229: new android/support/transition/TransitionValues
    //   232: dup
    //   233: invokespecial <init> : ()V
    //   236: astore #5
    //   238: aload #5
    //   240: aload_1
    //   241: putfield view : Landroid/view/View;
    //   244: iload_2
    //   245: ifeq -> 257
    //   248: aload_0
    //   249: aload #5
    //   251: invokevirtual captureStartValues : (Landroid/support/transition/TransitionValues;)V
    //   254: goto -> 263
    //   257: aload_0
    //   258: aload #5
    //   260: invokevirtual captureEndValues : (Landroid/support/transition/TransitionValues;)V
    //   263: aload #5
    //   265: getfield mTargetedTransitions : Ljava/util/ArrayList;
    //   268: aload_0
    //   269: invokevirtual add : (Ljava/lang/Object;)Z
    //   272: pop
    //   273: aload_0
    //   274: aload #5
    //   276: invokevirtual capturePropagationValues : (Landroid/support/transition/TransitionValues;)V
    //   279: iload_2
    //   280: ifeq -> 296
    //   283: aload_0
    //   284: getfield mStartValues : Landroid/support/transition/TransitionValuesMaps;
    //   287: aload_1
    //   288: aload #5
    //   290: invokestatic addViewValues : (Landroid/support/transition/TransitionValuesMaps;Landroid/view/View;Landroid/support/transition/TransitionValues;)V
    //   293: goto -> 306
    //   296: aload_0
    //   297: getfield mEndValues : Landroid/support/transition/TransitionValuesMaps;
    //   300: aload_1
    //   301: aload #5
    //   303: invokestatic addViewValues : (Landroid/support/transition/TransitionValuesMaps;Landroid/view/View;Landroid/support/transition/TransitionValues;)V
    //   306: iinc #3, 1
    //   309: goto -> 206
    //   312: iload_2
    //   313: ifne -> 446
    //   316: aload_0
    //   317: getfield mNameOverrides : Landroid/support/v4/util/ArrayMap;
    //   320: astore_1
    //   321: aload_1
    //   322: ifnull -> 446
    //   325: aload_1
    //   326: invokevirtual size : ()I
    //   329: istore #7
    //   331: new java/util/ArrayList
    //   334: dup
    //   335: iload #7
    //   337: invokespecial <init> : (I)V
    //   340: astore_1
    //   341: iconst_0
    //   342: istore #8
    //   344: iload #4
    //   346: istore_3
    //   347: iload #8
    //   349: iload #7
    //   351: if_icmpge -> 391
    //   354: aload_0
    //   355: getfield mNameOverrides : Landroid/support/v4/util/ArrayMap;
    //   358: iload #8
    //   360: invokevirtual keyAt : (I)Ljava/lang/Object;
    //   363: checkcast java/lang/String
    //   366: astore #5
    //   368: aload_1
    //   369: aload_0
    //   370: getfield mStartValues : Landroid/support/transition/TransitionValuesMaps;
    //   373: getfield mNameValues : Landroid/support/v4/util/ArrayMap;
    //   376: aload #5
    //   378: invokevirtual remove : (Ljava/lang/Object;)Ljava/lang/Object;
    //   381: invokevirtual add : (Ljava/lang/Object;)Z
    //   384: pop
    //   385: iinc #8, 1
    //   388: goto -> 344
    //   391: iload_3
    //   392: iload #7
    //   394: if_icmpge -> 446
    //   397: aload_1
    //   398: iload_3
    //   399: invokevirtual get : (I)Ljava/lang/Object;
    //   402: checkcast android/view/View
    //   405: astore #5
    //   407: aload #5
    //   409: ifnull -> 440
    //   412: aload_0
    //   413: getfield mNameOverrides : Landroid/support/v4/util/ArrayMap;
    //   416: iload_3
    //   417: invokevirtual valueAt : (I)Ljava/lang/Object;
    //   420: checkcast java/lang/String
    //   423: astore #6
    //   425: aload_0
    //   426: getfield mStartValues : Landroid/support/transition/TransitionValuesMaps;
    //   429: getfield mNameValues : Landroid/support/v4/util/ArrayMap;
    //   432: aload #6
    //   434: aload #5
    //   436: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   439: pop
    //   440: iinc #3, 1
    //   443: goto -> 391
    //   446: return
  }
  
  void clearValues(boolean paramBoolean) {
    if (paramBoolean) {
      this.mStartValues.mViewValues.clear();
      this.mStartValues.mIdValues.clear();
      this.mStartValues.mItemIdValues.clear();
    } else {
      this.mEndValues.mViewValues.clear();
      this.mEndValues.mIdValues.clear();
      this.mEndValues.mItemIdValues.clear();
    } 
  }
  
  public Transition clone() {
    try {
      Transition transition = (Transition)super.clone();
      ArrayList<Animator> arrayList = new ArrayList();
      this();
      transition.mAnimators = arrayList;
      TransitionValuesMaps transitionValuesMaps = new TransitionValuesMaps();
      this();
      transition.mStartValues = transitionValuesMaps;
      transitionValuesMaps = new TransitionValuesMaps();
      this();
      transition.mEndValues = transitionValuesMaps;
      transition.mStartValuesList = null;
      transition.mEndValuesList = null;
      return transition;
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      return null;
    } 
  }
  
  public Animator createAnimator(ViewGroup paramViewGroup, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2) {
    return null;
  }
  
  protected void createAnimators(ViewGroup paramViewGroup, TransitionValuesMaps paramTransitionValuesMaps1, TransitionValuesMaps paramTransitionValuesMaps2, ArrayList<TransitionValues> paramArrayList1, ArrayList<TransitionValues> paramArrayList2) {
    Object object;
    ArrayMap<Animator, AnimationInfo> arrayMap = getRunningAnimators();
    SparseIntArray sparseIntArray = new SparseIntArray();
    int i = paramArrayList1.size();
    long l = Long.MAX_VALUE;
    int j = 0;
    while (j < i) {
      TransitionValues transitionValues2 = paramArrayList1.get(j);
      TransitionValues transitionValues1 = paramArrayList2.get(j);
      TransitionValues transitionValues3 = transitionValues2;
      if (transitionValues2 != null) {
        transitionValues3 = transitionValues2;
        if (!transitionValues2.mTargetedTransitions.contains(this))
          transitionValues3 = null; 
      } 
      TransitionValues transitionValues4 = transitionValues1;
      if (transitionValues1 != null) {
        transitionValues4 = transitionValues1;
        if (!transitionValues1.mTargetedTransitions.contains(this))
          transitionValues4 = null; 
      } 
      if (transitionValues3 != null || transitionValues4 != null) {
        int m;
        if (transitionValues3 == null || transitionValues4 == null || isTransitionRequired(transitionValues3, transitionValues4)) {
          m = 1;
        } else {
          m = 0;
        } 
        if (m) {
          Animator animator = createAnimator(paramViewGroup, transitionValues3, transitionValues4);
          if (animator != null) {
            View view;
            if (transitionValues4 != null) {
              view = transitionValues4.view;
              String[] arrayOfString = getTransitionProperties();
              if (view != null && arrayOfString != null && arrayOfString.length > 0) {
                TransitionValues transitionValues = new TransitionValues();
                transitionValues.view = view;
                transitionValues2 = (TransitionValues)paramTransitionValuesMaps2.mViewValues.get(view);
                m = j;
                if (transitionValues2 != null) {
                  byte b = 0;
                  while (true) {
                    m = j;
                    if (b < arrayOfString.length) {
                      transitionValues.values.put(arrayOfString[b], transitionValues2.values.get(arrayOfString[b]));
                      b++;
                      continue;
                    } 
                    break;
                  } 
                } 
                j = m;
                int n = arrayMap.size();
                m = 0;
                while (true) {
                  if (m < n) {
                    AnimationInfo animationInfo = (AnimationInfo)arrayMap.get(arrayMap.keyAt(m));
                    if (animationInfo.mValues != null && animationInfo.mView == view && animationInfo.mName.equals(getName()) && animationInfo.mValues.equals(transitionValues)) {
                      animator = null;
                      TransitionValues transitionValues5 = transitionValues;
                      break;
                    } 
                    m++;
                    continue;
                  } 
                  transitionValues2 = transitionValues;
                  break;
                } 
              } else {
                transitionValues2 = null;
              } 
            } else {
              view = transitionValues3.view;
              transitionValues2 = null;
            } 
            Object object2 = object;
            m = j;
            if (animator != null) {
              TransitionPropagation transitionPropagation = this.mPropagation;
              object2 = object;
              if (transitionPropagation != null) {
                long l1 = transitionPropagation.getStartDelay(paramViewGroup, this, transitionValues3, transitionValues4);
                sparseIntArray.put(this.mAnimators.size(), (int)l1);
                l1 = Math.min(l1, object);
              } 
              arrayMap.put(animator, new AnimationInfo(view, getName(), this, ViewUtils.getWindowId((View)paramViewGroup), transitionValues2));
              this.mAnimators.add(animator);
              m = j;
            } 
            continue;
          } 
        } 
      } 
      Object object1 = object;
      int k = j;
      continue;
      j = SYNTHETIC_LOCAL_VARIABLE_17 + 1;
      object = SYNTHETIC_LOCAL_VARIABLE_15;
    } 
    if (object != 0L)
      for (j = 0; j < sparseIntArray.size(); j++) {
        int k = sparseIntArray.keyAt(j);
        Animator animator = this.mAnimators.get(k);
        animator.setStartDelay(sparseIntArray.valueAt(j) - object + animator.getStartDelay());
      }  
  }
  
  protected void end() {
    int i = this.mNumInstances - 1;
    this.mNumInstances = i;
    if (i == 0) {
      ArrayList<TransitionListener> arrayList = this.mListeners;
      if (arrayList != null && arrayList.size() > 0) {
        arrayList = (ArrayList<TransitionListener>)this.mListeners.clone();
        int j = arrayList.size();
        for (i = 0; i < j; i++)
          ((TransitionListener)arrayList.get(i)).onTransitionEnd(this); 
      } 
      for (i = 0; i < this.mStartValues.mItemIdValues.size(); i++) {
        View view = (View)this.mStartValues.mItemIdValues.valueAt(i);
        if (view != null)
          ViewCompat.setHasTransientState(view, false); 
      } 
      for (i = 0; i < this.mEndValues.mItemIdValues.size(); i++) {
        View view = (View)this.mEndValues.mItemIdValues.valueAt(i);
        if (view != null)
          ViewCompat.setHasTransientState(view, false); 
      } 
      this.mEnded = true;
    } 
  }
  
  public Transition excludeChildren(int paramInt, boolean paramBoolean) {
    this.mTargetIdChildExcludes = excludeId(this.mTargetIdChildExcludes, paramInt, paramBoolean);
    return this;
  }
  
  public Transition excludeChildren(View paramView, boolean paramBoolean) {
    this.mTargetChildExcludes = excludeView(this.mTargetChildExcludes, paramView, paramBoolean);
    return this;
  }
  
  public Transition excludeChildren(Class paramClass, boolean paramBoolean) {
    this.mTargetTypeChildExcludes = excludeType(this.mTargetTypeChildExcludes, paramClass, paramBoolean);
    return this;
  }
  
  public Transition excludeTarget(int paramInt, boolean paramBoolean) {
    this.mTargetIdExcludes = excludeId(this.mTargetIdExcludes, paramInt, paramBoolean);
    return this;
  }
  
  public Transition excludeTarget(View paramView, boolean paramBoolean) {
    this.mTargetExcludes = excludeView(this.mTargetExcludes, paramView, paramBoolean);
    return this;
  }
  
  public Transition excludeTarget(Class paramClass, boolean paramBoolean) {
    this.mTargetTypeExcludes = excludeType(this.mTargetTypeExcludes, paramClass, paramBoolean);
    return this;
  }
  
  public Transition excludeTarget(String paramString, boolean paramBoolean) {
    this.mTargetNameExcludes = excludeObject(this.mTargetNameExcludes, paramString, paramBoolean);
    return this;
  }
  
  void forceToEnd(ViewGroup paramViewGroup) {
    ArrayMap<Animator, AnimationInfo> arrayMap = getRunningAnimators();
    int i = arrayMap.size();
    if (paramViewGroup != null) {
      WindowIdImpl windowIdImpl = ViewUtils.getWindowId((View)paramViewGroup);
      while (--i >= 0) {
        AnimationInfo animationInfo = (AnimationInfo)arrayMap.valueAt(i);
        if (animationInfo.mView != null && windowIdImpl != null && windowIdImpl.equals(animationInfo.mWindowId))
          ((Animator)arrayMap.keyAt(i)).end(); 
        i--;
      } 
    } 
  }
  
  public long getDuration() {
    return this.mDuration;
  }
  
  public Rect getEpicenter() {
    EpicenterCallback epicenterCallback = this.mEpicenterCallback;
    return (epicenterCallback == null) ? null : epicenterCallback.onGetEpicenter(this);
  }
  
  public EpicenterCallback getEpicenterCallback() {
    return this.mEpicenterCallback;
  }
  
  public TimeInterpolator getInterpolator() {
    return this.mInterpolator;
  }
  
  TransitionValues getMatchedTransitionValues(View paramView, boolean paramBoolean) {
    TransitionValues transitionValues;
    ArrayList<TransitionValues> arrayList;
    byte b2;
    TransitionSet transitionSet = this.mParent;
    if (transitionSet != null)
      return transitionSet.getMatchedTransitionValues(paramView, paramBoolean); 
    if (paramBoolean) {
      arrayList = this.mStartValuesList;
    } else {
      arrayList = this.mEndValuesList;
    } 
    View view = null;
    if (arrayList == null)
      return null; 
    int i = arrayList.size();
    byte b1 = -1;
    byte b = 0;
    while (true) {
      b2 = b1;
      if (b < i) {
        TransitionValues transitionValues1 = arrayList.get(b);
        if (transitionValues1 == null)
          return null; 
        if (transitionValues1.view == paramView) {
          b2 = b;
          break;
        } 
        b++;
        continue;
      } 
      break;
    } 
    paramView = view;
    if (b2 >= 0) {
      ArrayList<TransitionValues> arrayList1;
      if (paramBoolean) {
        arrayList1 = this.mEndValuesList;
      } else {
        arrayList1 = this.mStartValuesList;
      } 
      transitionValues = arrayList1.get(b2);
    } 
    return transitionValues;
  }
  
  public String getName() {
    return this.mName;
  }
  
  public PathMotion getPathMotion() {
    return this.mPathMotion;
  }
  
  public TransitionPropagation getPropagation() {
    return this.mPropagation;
  }
  
  public long getStartDelay() {
    return this.mStartDelay;
  }
  
  public List<Integer> getTargetIds() {
    return this.mTargetIds;
  }
  
  public List<String> getTargetNames() {
    return this.mTargetNames;
  }
  
  public List<Class> getTargetTypes() {
    return this.mTargetTypes;
  }
  
  public List<View> getTargets() {
    return this.mTargets;
  }
  
  public String[] getTransitionProperties() {
    return null;
  }
  
  public TransitionValues getTransitionValues(View paramView, boolean paramBoolean) {
    TransitionValuesMaps transitionValuesMaps;
    TransitionSet transitionSet = this.mParent;
    if (transitionSet != null)
      return transitionSet.getTransitionValues(paramView, paramBoolean); 
    if (paramBoolean) {
      transitionValuesMaps = this.mStartValues;
    } else {
      transitionValuesMaps = this.mEndValues;
    } 
    return (TransitionValues)transitionValuesMaps.mViewValues.get(paramView);
  }
  
  public boolean isTransitionRequired(TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2) {
    // Byte code:
    //   0: iconst_0
    //   1: istore_3
    //   2: iload_3
    //   3: istore #4
    //   5: aload_1
    //   6: ifnull -> 117
    //   9: iload_3
    //   10: istore #4
    //   12: aload_2
    //   13: ifnull -> 117
    //   16: aload_0
    //   17: invokevirtual getTransitionProperties : ()[Ljava/lang/String;
    //   20: astore #5
    //   22: aload #5
    //   24: ifnull -> 67
    //   27: aload #5
    //   29: arraylength
    //   30: istore #6
    //   32: iconst_0
    //   33: istore #7
    //   35: iload_3
    //   36: istore #4
    //   38: iload #7
    //   40: iload #6
    //   42: if_icmpge -> 117
    //   45: aload_1
    //   46: aload_2
    //   47: aload #5
    //   49: iload #7
    //   51: aaload
    //   52: invokestatic isValueChanged : (Landroid/support/transition/TransitionValues;Landroid/support/transition/TransitionValues;Ljava/lang/String;)Z
    //   55: ifeq -> 61
    //   58: goto -> 114
    //   61: iinc #7, 1
    //   64: goto -> 35
    //   67: aload_1
    //   68: getfield values : Ljava/util/Map;
    //   71: invokeinterface keySet : ()Ljava/util/Set;
    //   76: invokeinterface iterator : ()Ljava/util/Iterator;
    //   81: astore #5
    //   83: iload_3
    //   84: istore #4
    //   86: aload #5
    //   88: invokeinterface hasNext : ()Z
    //   93: ifeq -> 117
    //   96: aload_1
    //   97: aload_2
    //   98: aload #5
    //   100: invokeinterface next : ()Ljava/lang/Object;
    //   105: checkcast java/lang/String
    //   108: invokestatic isValueChanged : (Landroid/support/transition/TransitionValues;Landroid/support/transition/TransitionValues;Ljava/lang/String;)Z
    //   111: ifeq -> 83
    //   114: iconst_1
    //   115: istore #4
    //   117: iload #4
    //   119: ireturn
  }
  
  boolean isValidTarget(View paramView) {
    int i = paramView.getId();
    ArrayList<Integer> arrayList3 = this.mTargetIdExcludes;
    if (arrayList3 != null && arrayList3.contains(Integer.valueOf(i)))
      return false; 
    ArrayList<View> arrayList2 = this.mTargetExcludes;
    if (arrayList2 != null && arrayList2.contains(paramView))
      return false; 
    ArrayList<Class> arrayList1 = this.mTargetTypeExcludes;
    if (arrayList1 != null) {
      int j = arrayList1.size();
      for (byte b = 0; b < j; b++) {
        if (((Class)this.mTargetTypeExcludes.get(b)).isInstance(paramView))
          return false; 
      } 
    } 
    if (this.mTargetNameExcludes != null && ViewCompat.getTransitionName(paramView) != null && this.mTargetNameExcludes.contains(ViewCompat.getTransitionName(paramView)))
      return false; 
    if (this.mTargetIds.size() == 0 && this.mTargets.size() == 0) {
      arrayList1 = this.mTargetTypes;
      if (arrayList1 == null || arrayList1.isEmpty()) {
        ArrayList<String> arrayList4 = this.mTargetNames;
        if (arrayList4 == null || arrayList4.isEmpty())
          return true; 
      } 
    } 
    if (this.mTargetIds.contains(Integer.valueOf(i)) || this.mTargets.contains(paramView))
      return true; 
    ArrayList<String> arrayList = this.mTargetNames;
    if (arrayList != null && arrayList.contains(ViewCompat.getTransitionName(paramView)))
      return true; 
    if (this.mTargetTypes != null)
      for (byte b = 0; b < this.mTargetTypes.size(); b++) {
        if (((Class)this.mTargetTypes.get(b)).isInstance(paramView))
          return true; 
      }  
    return false;
  }
  
  public void pause(View paramView) {
    if (!this.mEnded) {
      ArrayMap<Animator, AnimationInfo> arrayMap = getRunningAnimators();
      int i = arrayMap.size();
      WindowIdImpl windowIdImpl = ViewUtils.getWindowId(paramView);
      while (--i >= 0) {
        AnimationInfo animationInfo = (AnimationInfo)arrayMap.valueAt(i);
        if (animationInfo.mView != null && windowIdImpl.equals(animationInfo.mWindowId))
          AnimatorUtils.pause((Animator)arrayMap.keyAt(i)); 
        i--;
      } 
      ArrayList<TransitionListener> arrayList = this.mListeners;
      if (arrayList != null && arrayList.size() > 0) {
        arrayList = (ArrayList<TransitionListener>)this.mListeners.clone();
        int j = arrayList.size();
        for (i = 0; i < j; i++)
          ((TransitionListener)arrayList.get(i)).onTransitionPause(this); 
      } 
      this.mPaused = true;
    } 
  }
  
  void playTransition(ViewGroup paramViewGroup) {
    this.mStartValuesList = new ArrayList<TransitionValues>();
    this.mEndValuesList = new ArrayList<TransitionValues>();
    matchStartAndEnd(this.mStartValues, this.mEndValues);
    ArrayMap<Animator, AnimationInfo> arrayMap = getRunningAnimators();
    int i = arrayMap.size();
    WindowIdImpl windowIdImpl = ViewUtils.getWindowId((View)paramViewGroup);
    while (--i >= 0) {
      Animator animator = (Animator)arrayMap.keyAt(i);
      if (animator != null) {
        AnimationInfo animationInfo = (AnimationInfo)arrayMap.get(animator);
        if (animationInfo != null && animationInfo.mView != null && windowIdImpl.equals(animationInfo.mWindowId)) {
          boolean bool;
          TransitionValues transitionValues1 = animationInfo.mValues;
          View view = animationInfo.mView;
          TransitionValues transitionValues3 = getTransitionValues(view, true);
          TransitionValues transitionValues2 = getMatchedTransitionValues(view, true);
          if ((transitionValues3 != null || transitionValues2 != null) && animationInfo.mTransition.isTransitionRequired(transitionValues1, transitionValues2)) {
            bool = true;
          } else {
            bool = false;
          } 
          if (bool)
            if (animator.isRunning() || animator.isStarted()) {
              animator.cancel();
            } else {
              arrayMap.remove(animator);
            }  
        } 
      } 
      i--;
    } 
    createAnimators(paramViewGroup, this.mStartValues, this.mEndValues, this.mStartValuesList, this.mEndValuesList);
    runAnimators();
  }
  
  public Transition removeListener(TransitionListener paramTransitionListener) {
    ArrayList<TransitionListener> arrayList = this.mListeners;
    if (arrayList == null)
      return this; 
    arrayList.remove(paramTransitionListener);
    if (this.mListeners.size() == 0)
      this.mListeners = null; 
    return this;
  }
  
  public Transition removeTarget(int paramInt) {
    if (paramInt != 0)
      this.mTargetIds.remove(Integer.valueOf(paramInt)); 
    return this;
  }
  
  public Transition removeTarget(View paramView) {
    this.mTargets.remove(paramView);
    return this;
  }
  
  public Transition removeTarget(Class paramClass) {
    ArrayList<Class> arrayList = this.mTargetTypes;
    if (arrayList != null)
      arrayList.remove(paramClass); 
    return this;
  }
  
  public Transition removeTarget(String paramString) {
    ArrayList<String> arrayList = this.mTargetNames;
    if (arrayList != null)
      arrayList.remove(paramString); 
    return this;
  }
  
  public void resume(View paramView) {
    if (this.mPaused) {
      if (!this.mEnded) {
        ArrayMap<Animator, AnimationInfo> arrayMap = getRunningAnimators();
        int i = arrayMap.size();
        WindowIdImpl windowIdImpl = ViewUtils.getWindowId(paramView);
        while (--i >= 0) {
          AnimationInfo animationInfo = (AnimationInfo)arrayMap.valueAt(i);
          if (animationInfo.mView != null && windowIdImpl.equals(animationInfo.mWindowId))
            AnimatorUtils.resume((Animator)arrayMap.keyAt(i)); 
          i--;
        } 
        ArrayList<TransitionListener> arrayList = this.mListeners;
        if (arrayList != null && arrayList.size() > 0) {
          arrayList = (ArrayList<TransitionListener>)this.mListeners.clone();
          int j = arrayList.size();
          for (i = 0; i < j; i++)
            ((TransitionListener)arrayList.get(i)).onTransitionResume(this); 
        } 
      } 
      this.mPaused = false;
    } 
  }
  
  protected void runAnimators() {
    start();
    ArrayMap<Animator, AnimationInfo> arrayMap = getRunningAnimators();
    for (Animator animator : this.mAnimators) {
      if (arrayMap.containsKey(animator)) {
        start();
        runAnimator(animator, arrayMap);
      } 
    } 
    this.mAnimators.clear();
    end();
  }
  
  void setCanRemoveViews(boolean paramBoolean) {
    this.mCanRemoveViews = paramBoolean;
  }
  
  public Transition setDuration(long paramLong) {
    this.mDuration = paramLong;
    return this;
  }
  
  public void setEpicenterCallback(EpicenterCallback paramEpicenterCallback) {
    this.mEpicenterCallback = paramEpicenterCallback;
  }
  
  public Transition setInterpolator(TimeInterpolator paramTimeInterpolator) {
    this.mInterpolator = paramTimeInterpolator;
    return this;
  }
  
  public void setMatchOrder(int... paramVarArgs) {
    if (paramVarArgs == null || paramVarArgs.length == 0) {
      this.mMatchOrder = DEFAULT_MATCH_ORDER;
      return;
    } 
    byte b = 0;
    while (b < paramVarArgs.length) {
      if (isValidMatch(paramVarArgs[b])) {
        if (!alreadyContains(paramVarArgs, b)) {
          b++;
          continue;
        } 
        throw new IllegalArgumentException("matches contains a duplicate value");
      } 
      throw new IllegalArgumentException("matches contains invalid value");
    } 
    this.mMatchOrder = (int[])paramVarArgs.clone();
  }
  
  public void setPathMotion(PathMotion paramPathMotion) {
    if (paramPathMotion == null) {
      this.mPathMotion = STRAIGHT_PATH_MOTION;
    } else {
      this.mPathMotion = paramPathMotion;
    } 
  }
  
  public void setPropagation(TransitionPropagation paramTransitionPropagation) {
    this.mPropagation = paramTransitionPropagation;
  }
  
  Transition setSceneRoot(ViewGroup paramViewGroup) {
    this.mSceneRoot = paramViewGroup;
    return this;
  }
  
  public Transition setStartDelay(long paramLong) {
    this.mStartDelay = paramLong;
    return this;
  }
  
  protected void start() {
    if (this.mNumInstances == 0) {
      ArrayList<TransitionListener> arrayList = this.mListeners;
      if (arrayList != null && arrayList.size() > 0) {
        arrayList = (ArrayList<TransitionListener>)this.mListeners.clone();
        int i = arrayList.size();
        for (byte b = 0; b < i; b++)
          ((TransitionListener)arrayList.get(b)).onTransitionStart(this); 
      } 
      this.mEnded = false;
    } 
    this.mNumInstances++;
  }
  
  public String toString() {
    return toString("");
  }
  
  String toString(String paramString) {
    // Byte code:
    //   0: new java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial <init> : ()V
    //   7: astore_2
    //   8: aload_2
    //   9: aload_1
    //   10: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   13: pop
    //   14: aload_2
    //   15: aload_0
    //   16: invokevirtual getClass : ()Ljava/lang/Class;
    //   19: invokevirtual getSimpleName : ()Ljava/lang/String;
    //   22: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   25: pop
    //   26: aload_2
    //   27: ldc_w '@'
    //   30: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   33: pop
    //   34: aload_2
    //   35: aload_0
    //   36: invokevirtual hashCode : ()I
    //   39: invokestatic toHexString : (I)Ljava/lang/String;
    //   42: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   45: pop
    //   46: aload_2
    //   47: ldc_w ': '
    //   50: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   53: pop
    //   54: aload_2
    //   55: invokevirtual toString : ()Ljava/lang/String;
    //   58: astore_2
    //   59: aload_2
    //   60: astore_1
    //   61: aload_0
    //   62: getfield mDuration : J
    //   65: ldc2_w -1
    //   68: lcmp
    //   69: ifeq -> 116
    //   72: new java/lang/StringBuilder
    //   75: dup
    //   76: invokespecial <init> : ()V
    //   79: astore_1
    //   80: aload_1
    //   81: aload_2
    //   82: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   85: pop
    //   86: aload_1
    //   87: ldc_w 'dur('
    //   90: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   93: pop
    //   94: aload_1
    //   95: aload_0
    //   96: getfield mDuration : J
    //   99: invokevirtual append : (J)Ljava/lang/StringBuilder;
    //   102: pop
    //   103: aload_1
    //   104: ldc_w ') '
    //   107: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   110: pop
    //   111: aload_1
    //   112: invokevirtual toString : ()Ljava/lang/String;
    //   115: astore_1
    //   116: aload_1
    //   117: astore_2
    //   118: aload_0
    //   119: getfield mStartDelay : J
    //   122: ldc2_w -1
    //   125: lcmp
    //   126: ifeq -> 173
    //   129: new java/lang/StringBuilder
    //   132: dup
    //   133: invokespecial <init> : ()V
    //   136: astore_2
    //   137: aload_2
    //   138: aload_1
    //   139: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   142: pop
    //   143: aload_2
    //   144: ldc_w 'dly('
    //   147: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   150: pop
    //   151: aload_2
    //   152: aload_0
    //   153: getfield mStartDelay : J
    //   156: invokevirtual append : (J)Ljava/lang/StringBuilder;
    //   159: pop
    //   160: aload_2
    //   161: ldc_w ') '
    //   164: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   167: pop
    //   168: aload_2
    //   169: invokevirtual toString : ()Ljava/lang/String;
    //   172: astore_2
    //   173: aload_2
    //   174: astore_1
    //   175: aload_0
    //   176: getfield mInterpolator : Landroid/animation/TimeInterpolator;
    //   179: ifnull -> 226
    //   182: new java/lang/StringBuilder
    //   185: dup
    //   186: invokespecial <init> : ()V
    //   189: astore_1
    //   190: aload_1
    //   191: aload_2
    //   192: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   195: pop
    //   196: aload_1
    //   197: ldc_w 'interp('
    //   200: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   203: pop
    //   204: aload_1
    //   205: aload_0
    //   206: getfield mInterpolator : Landroid/animation/TimeInterpolator;
    //   209: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   212: pop
    //   213: aload_1
    //   214: ldc_w ') '
    //   217: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   220: pop
    //   221: aload_1
    //   222: invokevirtual toString : ()Ljava/lang/String;
    //   225: astore_1
    //   226: aload_0
    //   227: getfield mTargetIds : Ljava/util/ArrayList;
    //   230: invokevirtual size : ()I
    //   233: ifgt -> 248
    //   236: aload_1
    //   237: astore_2
    //   238: aload_0
    //   239: getfield mTargets : Ljava/util/ArrayList;
    //   242: invokevirtual size : ()I
    //   245: ifle -> 509
    //   248: new java/lang/StringBuilder
    //   251: dup
    //   252: invokespecial <init> : ()V
    //   255: astore_2
    //   256: aload_2
    //   257: aload_1
    //   258: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   261: pop
    //   262: aload_2
    //   263: ldc_w 'tgts('
    //   266: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   269: pop
    //   270: aload_2
    //   271: invokevirtual toString : ()Ljava/lang/String;
    //   274: astore_1
    //   275: aload_0
    //   276: getfield mTargetIds : Ljava/util/ArrayList;
    //   279: invokevirtual size : ()I
    //   282: istore_3
    //   283: iconst_0
    //   284: istore #4
    //   286: aload_1
    //   287: astore_2
    //   288: iload_3
    //   289: ifle -> 378
    //   292: iconst_0
    //   293: istore_3
    //   294: aload_1
    //   295: astore_2
    //   296: iload_3
    //   297: aload_0
    //   298: getfield mTargetIds : Ljava/util/ArrayList;
    //   301: invokevirtual size : ()I
    //   304: if_icmpge -> 378
    //   307: aload_1
    //   308: astore_2
    //   309: iload_3
    //   310: ifle -> 340
    //   313: new java/lang/StringBuilder
    //   316: dup
    //   317: invokespecial <init> : ()V
    //   320: astore_2
    //   321: aload_2
    //   322: aload_1
    //   323: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   326: pop
    //   327: aload_2
    //   328: ldc_w ', '
    //   331: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   334: pop
    //   335: aload_2
    //   336: invokevirtual toString : ()Ljava/lang/String;
    //   339: astore_2
    //   340: new java/lang/StringBuilder
    //   343: dup
    //   344: invokespecial <init> : ()V
    //   347: astore_1
    //   348: aload_1
    //   349: aload_2
    //   350: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   353: pop
    //   354: aload_1
    //   355: aload_0
    //   356: getfield mTargetIds : Ljava/util/ArrayList;
    //   359: iload_3
    //   360: invokevirtual get : (I)Ljava/lang/Object;
    //   363: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   366: pop
    //   367: aload_1
    //   368: invokevirtual toString : ()Ljava/lang/String;
    //   371: astore_1
    //   372: iinc #3, 1
    //   375: goto -> 294
    //   378: aload_2
    //   379: astore #5
    //   381: aload_0
    //   382: getfield mTargets : Ljava/util/ArrayList;
    //   385: invokevirtual size : ()I
    //   388: ifle -> 481
    //   391: aload_2
    //   392: astore_1
    //   393: iload #4
    //   395: istore_3
    //   396: aload_1
    //   397: astore #5
    //   399: iload_3
    //   400: aload_0
    //   401: getfield mTargets : Ljava/util/ArrayList;
    //   404: invokevirtual size : ()I
    //   407: if_icmpge -> 481
    //   410: aload_1
    //   411: astore_2
    //   412: iload_3
    //   413: ifle -> 443
    //   416: new java/lang/StringBuilder
    //   419: dup
    //   420: invokespecial <init> : ()V
    //   423: astore_2
    //   424: aload_2
    //   425: aload_1
    //   426: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   429: pop
    //   430: aload_2
    //   431: ldc_w ', '
    //   434: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   437: pop
    //   438: aload_2
    //   439: invokevirtual toString : ()Ljava/lang/String;
    //   442: astore_2
    //   443: new java/lang/StringBuilder
    //   446: dup
    //   447: invokespecial <init> : ()V
    //   450: astore_1
    //   451: aload_1
    //   452: aload_2
    //   453: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   456: pop
    //   457: aload_1
    //   458: aload_0
    //   459: getfield mTargets : Ljava/util/ArrayList;
    //   462: iload_3
    //   463: invokevirtual get : (I)Ljava/lang/Object;
    //   466: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   469: pop
    //   470: aload_1
    //   471: invokevirtual toString : ()Ljava/lang/String;
    //   474: astore_1
    //   475: iinc #3, 1
    //   478: goto -> 396
    //   481: new java/lang/StringBuilder
    //   484: dup
    //   485: invokespecial <init> : ()V
    //   488: astore_1
    //   489: aload_1
    //   490: aload #5
    //   492: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   495: pop
    //   496: aload_1
    //   497: ldc_w ')'
    //   500: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   503: pop
    //   504: aload_1
    //   505: invokevirtual toString : ()Ljava/lang/String;
    //   508: astore_2
    //   509: aload_2
    //   510: areturn
  }
  
  private static class AnimationInfo {
    String mName;
    
    Transition mTransition;
    
    TransitionValues mValues;
    
    View mView;
    
    WindowIdImpl mWindowId;
    
    AnimationInfo(View param1View, String param1String, Transition param1Transition, WindowIdImpl param1WindowIdImpl, TransitionValues param1TransitionValues) {
      this.mView = param1View;
      this.mName = param1String;
      this.mValues = param1TransitionValues;
      this.mWindowId = param1WindowIdImpl;
      this.mTransition = param1Transition;
    }
  }
  
  private static class ArrayListManager {
    static <T> ArrayList<T> add(ArrayList<T> param1ArrayList, T param1T) {
      ArrayList<T> arrayList = param1ArrayList;
      if (param1ArrayList == null)
        arrayList = new ArrayList<T>(); 
      if (!arrayList.contains(param1T))
        arrayList.add(param1T); 
      return arrayList;
    }
    
    static <T> ArrayList<T> remove(ArrayList<T> param1ArrayList, T param1T) {
      ArrayList<T> arrayList = param1ArrayList;
      if (param1ArrayList != null) {
        param1ArrayList.remove(param1T);
        arrayList = param1ArrayList;
        if (param1ArrayList.isEmpty())
          arrayList = null; 
      } 
      return arrayList;
    }
  }
  
  public static abstract class EpicenterCallback {
    public abstract Rect onGetEpicenter(Transition param1Transition);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface MatchOrder {}
  
  public static interface TransitionListener {
    void onTransitionCancel(Transition param1Transition);
    
    void onTransitionEnd(Transition param1Transition);
    
    void onTransitionPause(Transition param1Transition);
    
    void onTransitionResume(Transition param1Transition);
    
    void onTransitionStart(Transition param1Transition);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\Transition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */