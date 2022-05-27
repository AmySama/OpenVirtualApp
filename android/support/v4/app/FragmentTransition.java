package android.support.v4.app;

import android.graphics.Rect;
import android.os.Build;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

class FragmentTransition {
  private static final int[] INVERSE_OPS = new int[] { 0, 3, 0, 1, 5, 4, 7, 6, 9, 8 };
  
  private static final FragmentTransitionImpl PLATFORM_IMPL;
  
  private static final FragmentTransitionImpl SUPPORT_IMPL = resolveSupportImpl();
  
  private static void addSharedElementsWithMatchingNames(ArrayList<View> paramArrayList, ArrayMap<String, View> paramArrayMap, Collection<String> paramCollection) {
    for (int i = paramArrayMap.size() - 1; i >= 0; i--) {
      View view = (View)paramArrayMap.valueAt(i);
      if (paramCollection.contains(ViewCompat.getTransitionName(view)))
        paramArrayList.add(view); 
    } 
  }
  
  private static void addToFirstInLastOut(BackStackRecord paramBackStackRecord, BackStackRecord.Op paramOp, SparseArray<FragmentContainerTransition> paramSparseArray, boolean paramBoolean1, boolean paramBoolean2) {
    // Byte code:
    //   0: aload_1
    //   1: getfield fragment : Landroid/support/v4/app/Fragment;
    //   4: astore #5
    //   6: aload #5
    //   8: ifnonnull -> 12
    //   11: return
    //   12: aload #5
    //   14: getfield mContainerId : I
    //   17: istore #6
    //   19: iload #6
    //   21: ifne -> 25
    //   24: return
    //   25: iload_3
    //   26: ifeq -> 42
    //   29: getstatic android/support/v4/app/FragmentTransition.INVERSE_OPS : [I
    //   32: aload_1
    //   33: getfield cmd : I
    //   36: iaload
    //   37: istore #7
    //   39: goto -> 48
    //   42: aload_1
    //   43: getfield cmd : I
    //   46: istore #7
    //   48: iconst_0
    //   49: istore #8
    //   51: iconst_0
    //   52: istore #9
    //   54: iload #7
    //   56: iconst_1
    //   57: if_icmpeq -> 287
    //   60: iload #7
    //   62: iconst_3
    //   63: if_icmpeq -> 197
    //   66: iload #7
    //   68: iconst_4
    //   69: if_icmpeq -> 146
    //   72: iload #7
    //   74: iconst_5
    //   75: if_icmpeq -> 104
    //   78: iload #7
    //   80: bipush #6
    //   82: if_icmpeq -> 197
    //   85: iload #7
    //   87: bipush #7
    //   89: if_icmpeq -> 287
    //   92: iconst_0
    //   93: istore #7
    //   95: iconst_0
    //   96: istore #10
    //   98: iconst_0
    //   99: istore #11
    //   101: goto -> 333
    //   104: iload #4
    //   106: ifeq -> 136
    //   109: aload #5
    //   111: getfield mHiddenChanged : Z
    //   114: ifeq -> 324
    //   117: aload #5
    //   119: getfield mHidden : Z
    //   122: ifne -> 324
    //   125: aload #5
    //   127: getfield mAdded : Z
    //   130: ifeq -> 324
    //   133: goto -> 318
    //   136: aload #5
    //   138: getfield mHidden : Z
    //   141: istore #9
    //   143: goto -> 327
    //   146: iload #4
    //   148: ifeq -> 178
    //   151: aload #5
    //   153: getfield mHiddenChanged : Z
    //   156: ifeq -> 245
    //   159: aload #5
    //   161: getfield mAdded : Z
    //   164: ifeq -> 245
    //   167: aload #5
    //   169: getfield mHidden : Z
    //   172: ifeq -> 245
    //   175: goto -> 239
    //   178: aload #5
    //   180: getfield mAdded : Z
    //   183: ifeq -> 245
    //   186: aload #5
    //   188: getfield mHidden : Z
    //   191: ifne -> 245
    //   194: goto -> 175
    //   197: iload #4
    //   199: ifeq -> 251
    //   202: aload #5
    //   204: getfield mAdded : Z
    //   207: ifne -> 245
    //   210: aload #5
    //   212: getfield mView : Landroid/view/View;
    //   215: ifnull -> 245
    //   218: aload #5
    //   220: getfield mView : Landroid/view/View;
    //   223: invokevirtual getVisibility : ()I
    //   226: ifne -> 245
    //   229: aload #5
    //   231: getfield mPostponedAlpha : F
    //   234: fconst_0
    //   235: fcmpl
    //   236: iflt -> 245
    //   239: iconst_1
    //   240: istore #7
    //   242: goto -> 270
    //   245: iconst_0
    //   246: istore #7
    //   248: goto -> 270
    //   251: aload #5
    //   253: getfield mAdded : Z
    //   256: ifeq -> 245
    //   259: aload #5
    //   261: getfield mHidden : Z
    //   264: ifne -> 245
    //   267: goto -> 239
    //   270: iload #7
    //   272: istore #11
    //   274: iconst_0
    //   275: istore #7
    //   277: iconst_1
    //   278: istore #10
    //   280: iload #8
    //   282: istore #9
    //   284: goto -> 333
    //   287: iload #4
    //   289: ifeq -> 302
    //   292: aload #5
    //   294: getfield mIsNewlyAdded : Z
    //   297: istore #9
    //   299: goto -> 327
    //   302: aload #5
    //   304: getfield mAdded : Z
    //   307: ifne -> 324
    //   310: aload #5
    //   312: getfield mHidden : Z
    //   315: ifne -> 324
    //   318: iconst_1
    //   319: istore #9
    //   321: goto -> 327
    //   324: iconst_0
    //   325: istore #9
    //   327: iconst_1
    //   328: istore #7
    //   330: goto -> 95
    //   333: aload_2
    //   334: iload #6
    //   336: invokevirtual get : (I)Ljava/lang/Object;
    //   339: checkcast android/support/v4/app/FragmentTransition$FragmentContainerTransition
    //   342: astore #12
    //   344: aload #12
    //   346: astore_1
    //   347: iload #9
    //   349: ifeq -> 377
    //   352: aload #12
    //   354: aload_2
    //   355: iload #6
    //   357: invokestatic ensureContainer : (Landroid/support/v4/app/FragmentTransition$FragmentContainerTransition;Landroid/util/SparseArray;I)Landroid/support/v4/app/FragmentTransition$FragmentContainerTransition;
    //   360: astore_1
    //   361: aload_1
    //   362: aload #5
    //   364: putfield lastIn : Landroid/support/v4/app/Fragment;
    //   367: aload_1
    //   368: iload_3
    //   369: putfield lastInIsPop : Z
    //   372: aload_1
    //   373: aload_0
    //   374: putfield lastInTransaction : Landroid/support/v4/app/BackStackRecord;
    //   377: iload #4
    //   379: ifne -> 454
    //   382: iload #7
    //   384: ifeq -> 454
    //   387: aload_1
    //   388: ifnull -> 405
    //   391: aload_1
    //   392: getfield firstOut : Landroid/support/v4/app/Fragment;
    //   395: aload #5
    //   397: if_acmpne -> 405
    //   400: aload_1
    //   401: aconst_null
    //   402: putfield firstOut : Landroid/support/v4/app/Fragment;
    //   405: aload_0
    //   406: getfield mManager : Landroid/support/v4/app/FragmentManagerImpl;
    //   409: astore #12
    //   411: aload #5
    //   413: getfield mState : I
    //   416: iconst_1
    //   417: if_icmpge -> 454
    //   420: aload #12
    //   422: getfield mCurState : I
    //   425: iconst_1
    //   426: if_icmplt -> 454
    //   429: aload_0
    //   430: getfield mReorderingAllowed : Z
    //   433: ifne -> 454
    //   436: aload #12
    //   438: aload #5
    //   440: invokevirtual makeActive : (Landroid/support/v4/app/Fragment;)V
    //   443: aload #12
    //   445: aload #5
    //   447: iconst_1
    //   448: iconst_0
    //   449: iconst_0
    //   450: iconst_0
    //   451: invokevirtual moveToState : (Landroid/support/v4/app/Fragment;IIIZ)V
    //   454: aload_1
    //   455: astore #12
    //   457: iload #11
    //   459: ifeq -> 504
    //   462: aload_1
    //   463: ifnull -> 476
    //   466: aload_1
    //   467: astore #12
    //   469: aload_1
    //   470: getfield firstOut : Landroid/support/v4/app/Fragment;
    //   473: ifnonnull -> 504
    //   476: aload_1
    //   477: aload_2
    //   478: iload #6
    //   480: invokestatic ensureContainer : (Landroid/support/v4/app/FragmentTransition$FragmentContainerTransition;Landroid/util/SparseArray;I)Landroid/support/v4/app/FragmentTransition$FragmentContainerTransition;
    //   483: astore #12
    //   485: aload #12
    //   487: aload #5
    //   489: putfield firstOut : Landroid/support/v4/app/Fragment;
    //   492: aload #12
    //   494: iload_3
    //   495: putfield firstOutIsPop : Z
    //   498: aload #12
    //   500: aload_0
    //   501: putfield firstOutTransaction : Landroid/support/v4/app/BackStackRecord;
    //   504: iload #4
    //   506: ifne -> 535
    //   509: iload #10
    //   511: ifeq -> 535
    //   514: aload #12
    //   516: ifnull -> 535
    //   519: aload #12
    //   521: getfield lastIn : Landroid/support/v4/app/Fragment;
    //   524: aload #5
    //   526: if_acmpne -> 535
    //   529: aload #12
    //   531: aconst_null
    //   532: putfield lastIn : Landroid/support/v4/app/Fragment;
    //   535: return
  }
  
  public static void calculateFragments(BackStackRecord paramBackStackRecord, SparseArray<FragmentContainerTransition> paramSparseArray, boolean paramBoolean) {
    int i = paramBackStackRecord.mOps.size();
    for (byte b = 0; b < i; b++)
      addToFirstInLastOut(paramBackStackRecord, paramBackStackRecord.mOps.get(b), paramSparseArray, false, paramBoolean); 
  }
  
  private static ArrayMap<String, String> calculateNameOverrides(int paramInt1, ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, int paramInt2, int paramInt3) {
    ArrayMap<String, String> arrayMap = new ArrayMap();
    while (--paramInt3 >= paramInt2) {
      BackStackRecord backStackRecord = paramArrayList.get(paramInt3);
      if (backStackRecord.interactsWith(paramInt1)) {
        boolean bool = ((Boolean)paramArrayList1.get(paramInt3)).booleanValue();
        if (backStackRecord.mSharedElementSourceNames != null) {
          ArrayList<String> arrayList1;
          ArrayList<String> arrayList2;
          int i = backStackRecord.mSharedElementSourceNames.size();
          if (bool) {
            arrayList1 = backStackRecord.mSharedElementSourceNames;
            arrayList2 = backStackRecord.mSharedElementTargetNames;
          } else {
            arrayList2 = backStackRecord.mSharedElementSourceNames;
            arrayList1 = backStackRecord.mSharedElementTargetNames;
          } 
          for (byte b = 0; b < i; b++) {
            String str2 = arrayList2.get(b);
            String str3 = arrayList1.get(b);
            String str1 = (String)arrayMap.remove(str3);
            if (str1 != null) {
              arrayMap.put(str2, str1);
            } else {
              arrayMap.put(str2, str3);
            } 
          } 
        } 
      } 
      paramInt3--;
    } 
    return arrayMap;
  }
  
  public static void calculatePopFragments(BackStackRecord paramBackStackRecord, SparseArray<FragmentContainerTransition> paramSparseArray, boolean paramBoolean) {
    if (!paramBackStackRecord.mManager.mContainer.onHasView())
      return; 
    for (int i = paramBackStackRecord.mOps.size() - 1; i >= 0; i--)
      addToFirstInLastOut(paramBackStackRecord, paramBackStackRecord.mOps.get(i), paramSparseArray, true, paramBoolean); 
  }
  
  private static void callSharedElementStartEnd(Fragment paramFragment1, Fragment paramFragment2, boolean paramBoolean1, ArrayMap<String, View> paramArrayMap, boolean paramBoolean2) {
    SharedElementCallback sharedElementCallback;
    if (paramBoolean1) {
      sharedElementCallback = paramFragment2.getEnterTransitionCallback();
    } else {
      sharedElementCallback = sharedElementCallback.getEnterTransitionCallback();
    } 
    if (sharedElementCallback != null) {
      int i;
      ArrayList<Object> arrayList1 = new ArrayList();
      ArrayList<Object> arrayList2 = new ArrayList();
      byte b = 0;
      if (paramArrayMap == null) {
        i = 0;
      } else {
        i = paramArrayMap.size();
      } 
      while (b < i) {
        arrayList2.add(paramArrayMap.keyAt(b));
        arrayList1.add(paramArrayMap.valueAt(b));
        b++;
      } 
      if (paramBoolean2) {
        sharedElementCallback.onSharedElementStart(arrayList2, arrayList1, null);
      } else {
        sharedElementCallback.onSharedElementEnd(arrayList2, arrayList1, null);
      } 
    } 
  }
  
  private static boolean canHandleAll(FragmentTransitionImpl paramFragmentTransitionImpl, List<Object> paramList) {
    int i = paramList.size();
    for (byte b = 0; b < i; b++) {
      if (!paramFragmentTransitionImpl.canHandle(paramList.get(b)))
        return false; 
    } 
    return true;
  }
  
  private static ArrayMap<String, View> captureInSharedElements(FragmentTransitionImpl paramFragmentTransitionImpl, ArrayMap<String, String> paramArrayMap, Object paramObject, FragmentContainerTransition paramFragmentContainerTransition) {
    ArrayList<String> arrayList;
    Fragment fragment = paramFragmentContainerTransition.lastIn;
    View view = fragment.getView();
    if (paramArrayMap.isEmpty() || paramObject == null || view == null) {
      paramArrayMap.clear();
      return null;
    } 
    ArrayMap<String, View> arrayMap = new ArrayMap();
    paramFragmentTransitionImpl.findNamedViews((Map<String, View>)arrayMap, view);
    BackStackRecord backStackRecord = paramFragmentContainerTransition.lastInTransaction;
    if (paramFragmentContainerTransition.lastInIsPop) {
      paramObject = fragment.getExitTransitionCallback();
      arrayList = backStackRecord.mSharedElementSourceNames;
    } else {
      paramObject = fragment.getEnterTransitionCallback();
      arrayList = ((BackStackRecord)arrayList).mSharedElementTargetNames;
    } 
    if (arrayList != null) {
      arrayMap.retainAll(arrayList);
      arrayMap.retainAll(paramArrayMap.values());
    } 
    if (paramObject != null) {
      paramObject.onMapSharedElements(arrayList, (Map<String, View>)arrayMap);
      for (int i = arrayList.size() - 1; i >= 0; i--) {
        String str = arrayList.get(i);
        paramObject = arrayMap.get(str);
        if (paramObject == null) {
          paramObject = findKeyForValue(paramArrayMap, str);
          if (paramObject != null)
            paramArrayMap.remove(paramObject); 
        } else if (!str.equals(ViewCompat.getTransitionName((View)paramObject))) {
          str = findKeyForValue(paramArrayMap, str);
          if (str != null)
            paramArrayMap.put(str, ViewCompat.getTransitionName((View)paramObject)); 
        } 
      } 
    } else {
      retainValues(paramArrayMap, arrayMap);
    } 
    return arrayMap;
  }
  
  private static ArrayMap<String, View> captureOutSharedElements(FragmentTransitionImpl paramFragmentTransitionImpl, ArrayMap<String, String> paramArrayMap, Object paramObject, FragmentContainerTransition paramFragmentContainerTransition) {
    ArrayList<String> arrayList;
    if (paramArrayMap.isEmpty() || paramObject == null) {
      paramArrayMap.clear();
      return null;
    } 
    paramObject = paramFragmentContainerTransition.firstOut;
    ArrayMap<String, View> arrayMap = new ArrayMap();
    paramFragmentTransitionImpl.findNamedViews((Map<String, View>)arrayMap, paramObject.getView());
    BackStackRecord backStackRecord = paramFragmentContainerTransition.firstOutTransaction;
    if (paramFragmentContainerTransition.firstOutIsPop) {
      paramObject = paramObject.getEnterTransitionCallback();
      arrayList = backStackRecord.mSharedElementTargetNames;
    } else {
      paramObject = paramObject.getExitTransitionCallback();
      arrayList = ((BackStackRecord)arrayList).mSharedElementSourceNames;
    } 
    arrayMap.retainAll(arrayList);
    if (paramObject != null) {
      paramObject.onMapSharedElements(arrayList, (Map<String, View>)arrayMap);
      for (int i = arrayList.size() - 1; i >= 0; i--) {
        String str = arrayList.get(i);
        paramObject = arrayMap.get(str);
        if (paramObject == null) {
          paramArrayMap.remove(str);
        } else if (!str.equals(ViewCompat.getTransitionName((View)paramObject))) {
          str = (String)paramArrayMap.remove(str);
          paramArrayMap.put(ViewCompat.getTransitionName((View)paramObject), str);
        } 
      } 
    } else {
      paramArrayMap.retainAll(arrayMap.keySet());
    } 
    return arrayMap;
  }
  
  private static FragmentTransitionImpl chooseImpl(Fragment paramFragment1, Fragment paramFragment2) {
    ArrayList<Object> arrayList = new ArrayList();
    if (paramFragment1 != null) {
      Object object2 = paramFragment1.getExitTransition();
      if (object2 != null)
        arrayList.add(object2); 
      object2 = paramFragment1.getReturnTransition();
      if (object2 != null)
        arrayList.add(object2); 
      Object object1 = paramFragment1.getSharedElementReturnTransition();
      if (object1 != null)
        arrayList.add(object1); 
    } 
    if (paramFragment2 != null) {
      Object object = paramFragment2.getEnterTransition();
      if (object != null)
        arrayList.add(object); 
      object = paramFragment2.getReenterTransition();
      if (object != null)
        arrayList.add(object); 
      object = paramFragment2.getSharedElementEnterTransition();
      if (object != null)
        arrayList.add(object); 
    } 
    if (arrayList.isEmpty())
      return null; 
    FragmentTransitionImpl fragmentTransitionImpl = PLATFORM_IMPL;
    if (fragmentTransitionImpl != null && canHandleAll(fragmentTransitionImpl, arrayList))
      return PLATFORM_IMPL; 
    fragmentTransitionImpl = SUPPORT_IMPL;
    if (fragmentTransitionImpl != null && canHandleAll(fragmentTransitionImpl, arrayList))
      return SUPPORT_IMPL; 
    if (PLATFORM_IMPL == null && SUPPORT_IMPL == null)
      return null; 
    throw new IllegalArgumentException("Invalid Transition types");
  }
  
  private static ArrayList<View> configureEnteringExitingViews(FragmentTransitionImpl paramFragmentTransitionImpl, Object paramObject, Fragment paramFragment, ArrayList<View> paramArrayList, View paramView) {
    if (paramObject != null) {
      ArrayList<View> arrayList2 = new ArrayList();
      View view = paramFragment.getView();
      if (view != null)
        paramFragmentTransitionImpl.captureTransitioningViews(arrayList2, view); 
      if (paramArrayList != null)
        arrayList2.removeAll(paramArrayList); 
      ArrayList<View> arrayList1 = arrayList2;
      if (!arrayList2.isEmpty()) {
        arrayList2.add(paramView);
        paramFragmentTransitionImpl.addTargets(paramObject, arrayList2);
        arrayList1 = arrayList2;
      } 
    } else {
      paramFragment = null;
    } 
    return (ArrayList<View>)paramFragment;
  }
  
  private static Object configureSharedElementsOrdered(final FragmentTransitionImpl impl, ViewGroup paramViewGroup, final View nonExistentView, final ArrayMap<String, String> nameOverrides, final FragmentContainerTransition fragments, final ArrayList<View> sharedElementsOut, final ArrayList<View> sharedElementsIn, final Object enterTransition, final Object inEpicenter) {
    final Object finalSharedElementTransition;
    final Fragment inFragment = fragments.lastIn;
    final Fragment outFragment = fragments.firstOut;
    if (fragment1 == null || fragment2 == null)
      return null; 
    final boolean inIsPop = fragments.lastInIsPop;
    if (nameOverrides.isEmpty()) {
      object = null;
    } else {
      object = getSharedElementTransition(impl, fragment1, fragment2, bool);
    } 
    ArrayMap<String, View> arrayMap = captureOutSharedElements(impl, nameOverrides, object, fragments);
    if (nameOverrides.isEmpty()) {
      object = null;
    } else {
      sharedElementsOut.addAll(arrayMap.values());
    } 
    if (enterTransition == null && inEpicenter == null && object == null)
      return null; 
    callSharedElementStartEnd(fragment1, fragment2, bool, arrayMap, true);
    if (object != null) {
      Rect rect = new Rect();
      impl.setSharedElementTargets(object, nonExistentView, sharedElementsOut);
      setOutEpicenter(impl, object, inEpicenter, arrayMap, fragments.firstOutIsPop, fragments.firstOutTransaction);
      inEpicenter = rect;
      if (enterTransition != null) {
        impl.setEpicenter(enterTransition, rect);
        inEpicenter = rect;
      } 
    } else {
      inEpicenter = null;
    } 
    OneShotPreDrawListener.add((View)paramViewGroup, new Runnable() {
          public void run() {
            ArrayMap arrayMap = FragmentTransition.captureInSharedElements(impl, nameOverrides, finalSharedElementTransition, fragments);
            if (arrayMap != null) {
              sharedElementsIn.addAll(arrayMap.values());
              sharedElementsIn.add(nonExistentView);
            } 
            FragmentTransition.callSharedElementStartEnd(inFragment, outFragment, inIsPop, arrayMap, false);
            Object object = finalSharedElementTransition;
            if (object != null) {
              impl.swapSharedElementTargets(object, sharedElementsOut, sharedElementsIn);
              View view = FragmentTransition.getInEpicenterView(arrayMap, fragments, enterTransition, inIsPop);
              if (view != null)
                impl.getBoundsOnScreen(view, inEpicenter); 
            } 
          }
        });
    return object;
  }
  
  private static Object configureSharedElementsReordered(final FragmentTransitionImpl impl, ViewGroup paramViewGroup, View paramView, ArrayMap<String, String> paramArrayMap, FragmentContainerTransition paramFragmentContainerTransition, ArrayList<View> paramArrayList1, ArrayList<View> paramArrayList2, Object paramObject1, Object paramObject2) {
    Object object1;
    final View epicenter;
    final View epicenterView;
    Object object2;
    final Fragment inFragment = paramFragmentContainerTransition.lastIn;
    final Fragment outFragment = paramFragmentContainerTransition.firstOut;
    if (fragment1 != null)
      fragment1.getView().setVisibility(0); 
    if (fragment1 == null || fragment2 == null)
      return null; 
    final boolean inIsPop = paramFragmentContainerTransition.lastInIsPop;
    if (paramArrayMap.isEmpty()) {
      object2 = null;
    } else {
      object2 = getSharedElementTransition(impl, fragment1, fragment2, bool);
    } 
    ArrayMap<String, View> arrayMap1 = captureOutSharedElements(impl, paramArrayMap, object2, paramFragmentContainerTransition);
    final ArrayMap<String, View> inSharedElements = captureInSharedElements(impl, paramArrayMap, object2, paramFragmentContainerTransition);
    if (paramArrayMap.isEmpty()) {
      if (arrayMap1 != null)
        arrayMap1.clear(); 
      if (arrayMap2 != null)
        arrayMap2.clear(); 
      paramArrayMap = null;
    } else {
      addSharedElementsWithMatchingNames(paramArrayList1, arrayMap1, paramArrayMap.keySet());
      addSharedElementsWithMatchingNames(paramArrayList2, arrayMap2, paramArrayMap.values());
      object1 = object2;
    } 
    if (paramObject1 == null && paramObject2 == null && object1 == null)
      return null; 
    callSharedElementStartEnd(fragment1, fragment2, bool, arrayMap1, true);
    if (object1 != null) {
      paramArrayList2.add(paramView);
      impl.setSharedElementTargets(object1, paramView, paramArrayList1);
      setOutEpicenter(impl, object1, paramObject2, arrayMap1, paramFragmentContainerTransition.firstOutIsPop, paramFragmentContainerTransition.firstOutTransaction);
      Rect rect1 = new Rect();
      view2 = getInEpicenterView(arrayMap2, paramFragmentContainerTransition, paramObject1, bool);
      if (view2 != null)
        impl.setEpicenter(paramObject1, rect1); 
      Rect rect2 = rect1;
    } else {
      paramView = null;
      view1 = paramView;
      view2 = paramView;
    } 
    OneShotPreDrawListener.add((View)paramViewGroup, new Runnable() {
          public void run() {
            FragmentTransition.callSharedElementStartEnd(inFragment, outFragment, inIsPop, inSharedElements, false);
            View view = epicenterView;
            if (view != null)
              impl.getBoundsOnScreen(view, epicenter); 
          }
        });
    return object1;
  }
  
  private static void configureTransitionsOrdered(FragmentManagerImpl paramFragmentManagerImpl, int paramInt, FragmentContainerTransition paramFragmentContainerTransition, View paramView, ArrayMap<String, String> paramArrayMap) {
    if (paramFragmentManagerImpl.mContainer.onHasView()) {
      ViewGroup viewGroup = (ViewGroup)paramFragmentManagerImpl.mContainer.onFindViewById(paramInt);
    } else {
      paramFragmentManagerImpl = null;
    } 
    if (paramFragmentManagerImpl == null)
      return; 
    Fragment fragment1 = paramFragmentContainerTransition.lastIn;
    Fragment fragment2 = paramFragmentContainerTransition.firstOut;
    FragmentTransitionImpl fragmentTransitionImpl = chooseImpl(fragment2, fragment1);
    if (fragmentTransitionImpl == null)
      return; 
    boolean bool1 = paramFragmentContainerTransition.lastInIsPop;
    boolean bool2 = paramFragmentContainerTransition.firstOutIsPop;
    Object object2 = getEnterTransition(fragmentTransitionImpl, fragment1, bool1);
    Object object3 = getExitTransition(fragmentTransitionImpl, fragment2, bool2);
    ArrayList<View> arrayList2 = new ArrayList();
    ArrayList<View> arrayList3 = new ArrayList();
    Object object4 = configureSharedElementsOrdered(fragmentTransitionImpl, (ViewGroup)paramFragmentManagerImpl, paramView, paramArrayMap, paramFragmentContainerTransition, arrayList2, arrayList3, object2, object3);
    if (object2 == null && object4 == null && object3 == null)
      return; 
    ArrayList<View> arrayList1 = configureEnteringExitingViews(fragmentTransitionImpl, object3, fragment2, arrayList2, paramView);
    if (arrayList1 == null || arrayList1.isEmpty())
      object3 = null; 
    fragmentTransitionImpl.addTarget(object2, paramView);
    Object object1 = mergeTransitions(fragmentTransitionImpl, object2, object3, object4, fragment1, paramFragmentContainerTransition.lastInIsPop);
    if (object1 != null) {
      arrayList2 = new ArrayList<View>();
      fragmentTransitionImpl.scheduleRemoveTargets(object1, object2, arrayList2, object3, arrayList1, object4, arrayList3);
      scheduleTargetChange(fragmentTransitionImpl, (ViewGroup)paramFragmentManagerImpl, fragment1, paramView, arrayList3, object2, arrayList2, object3, arrayList1);
      fragmentTransitionImpl.setNameOverridesOrdered((View)paramFragmentManagerImpl, arrayList3, (Map<String, String>)paramArrayMap);
      fragmentTransitionImpl.beginDelayedTransition((ViewGroup)paramFragmentManagerImpl, object1);
      fragmentTransitionImpl.scheduleNameReset((ViewGroup)paramFragmentManagerImpl, arrayList3, (Map<String, String>)paramArrayMap);
    } 
  }
  
  private static void configureTransitionsReordered(FragmentManagerImpl paramFragmentManagerImpl, int paramInt, FragmentContainerTransition paramFragmentContainerTransition, View paramView, ArrayMap<String, String> paramArrayMap) {
    if (paramFragmentManagerImpl.mContainer.onHasView()) {
      ViewGroup viewGroup = (ViewGroup)paramFragmentManagerImpl.mContainer.onFindViewById(paramInt);
    } else {
      paramFragmentManagerImpl = null;
    } 
    if (paramFragmentManagerImpl == null)
      return; 
    Fragment fragment1 = paramFragmentContainerTransition.lastIn;
    Fragment fragment2 = paramFragmentContainerTransition.firstOut;
    FragmentTransitionImpl fragmentTransitionImpl = chooseImpl(fragment2, fragment1);
    if (fragmentTransitionImpl == null)
      return; 
    boolean bool1 = paramFragmentContainerTransition.lastInIsPop;
    boolean bool2 = paramFragmentContainerTransition.firstOutIsPop;
    ArrayList<View> arrayList2 = new ArrayList();
    ArrayList<View> arrayList3 = new ArrayList();
    Object object3 = getEnterTransition(fragmentTransitionImpl, fragment1, bool1);
    Object<View> object4 = (Object<View>)getExitTransition(fragmentTransitionImpl, fragment2, bool2);
    Object object5 = configureSharedElementsReordered(fragmentTransitionImpl, (ViewGroup)paramFragmentManagerImpl, paramView, paramArrayMap, paramFragmentContainerTransition, arrayList3, arrayList2, object3, object4);
    if (object3 == null && object5 == null && object4 == null)
      return; 
    Object<View> object1 = object4;
    object4 = (Object<View>)configureEnteringExitingViews(fragmentTransitionImpl, object1, fragment2, arrayList3, paramView);
    ArrayList<View> arrayList1 = configureEnteringExitingViews(fragmentTransitionImpl, object3, fragment1, arrayList2, paramView);
    setViewVisibility(arrayList1, 4);
    Object object2 = mergeTransitions(fragmentTransitionImpl, object3, object1, object5, fragment1, bool1);
    if (object2 != null) {
      replaceHide(fragmentTransitionImpl, object1, fragment2, (ArrayList<View>)object4);
      ArrayList<String> arrayList = fragmentTransitionImpl.prepareSetNameOverridesReordered(arrayList2);
      fragmentTransitionImpl.scheduleRemoveTargets(object2, object3, arrayList1, object1, (ArrayList<View>)object4, object5, arrayList2);
      fragmentTransitionImpl.beginDelayedTransition((ViewGroup)paramFragmentManagerImpl, object2);
      fragmentTransitionImpl.setNameOverridesReordered((View)paramFragmentManagerImpl, arrayList3, arrayList2, arrayList, (Map<String, String>)paramArrayMap);
      setViewVisibility(arrayList1, 0);
      fragmentTransitionImpl.swapSharedElementTargets(object5, arrayList3, arrayList2);
    } 
  }
  
  private static FragmentContainerTransition ensureContainer(FragmentContainerTransition paramFragmentContainerTransition, SparseArray<FragmentContainerTransition> paramSparseArray, int paramInt) {
    FragmentContainerTransition fragmentContainerTransition = paramFragmentContainerTransition;
    if (paramFragmentContainerTransition == null) {
      fragmentContainerTransition = new FragmentContainerTransition();
      paramSparseArray.put(paramInt, fragmentContainerTransition);
    } 
    return fragmentContainerTransition;
  }
  
  private static String findKeyForValue(ArrayMap<String, String> paramArrayMap, String paramString) {
    int i = paramArrayMap.size();
    for (byte b = 0; b < i; b++) {
      if (paramString.equals(paramArrayMap.valueAt(b)))
        return (String)paramArrayMap.keyAt(b); 
    } 
    return null;
  }
  
  private static Object getEnterTransition(FragmentTransitionImpl paramFragmentTransitionImpl, Fragment paramFragment, boolean paramBoolean) {
    Object object;
    if (paramFragment == null)
      return null; 
    if (paramBoolean) {
      object = paramFragment.getReenterTransition();
    } else {
      object = object.getEnterTransition();
    } 
    return paramFragmentTransitionImpl.cloneTransition(object);
  }
  
  private static Object getExitTransition(FragmentTransitionImpl paramFragmentTransitionImpl, Fragment paramFragment, boolean paramBoolean) {
    Object object;
    if (paramFragment == null)
      return null; 
    if (paramBoolean) {
      object = paramFragment.getReturnTransition();
    } else {
      object = object.getExitTransition();
    } 
    return paramFragmentTransitionImpl.cloneTransition(object);
  }
  
  private static View getInEpicenterView(ArrayMap<String, View> paramArrayMap, FragmentContainerTransition paramFragmentContainerTransition, Object paramObject, boolean paramBoolean) {
    BackStackRecord backStackRecord = paramFragmentContainerTransition.lastInTransaction;
    if (paramObject != null && paramArrayMap != null && backStackRecord.mSharedElementSourceNames != null && !backStackRecord.mSharedElementSourceNames.isEmpty()) {
      String str;
      if (paramBoolean) {
        str = backStackRecord.mSharedElementSourceNames.get(0);
      } else {
        str = ((BackStackRecord)str).mSharedElementTargetNames.get(0);
      } 
      return (View)paramArrayMap.get(str);
    } 
    return null;
  }
  
  private static Object getSharedElementTransition(FragmentTransitionImpl paramFragmentTransitionImpl, Fragment paramFragment1, Fragment paramFragment2, boolean paramBoolean) {
    Object object;
    if (paramFragment1 == null || paramFragment2 == null)
      return null; 
    if (paramBoolean) {
      object = paramFragment2.getSharedElementReturnTransition();
    } else {
      object = object.getSharedElementEnterTransition();
    } 
    return paramFragmentTransitionImpl.wrapTransitionInSet(paramFragmentTransitionImpl.cloneTransition(object));
  }
  
  private static Object mergeTransitions(FragmentTransitionImpl paramFragmentTransitionImpl, Object paramObject1, Object paramObject2, Object paramObject3, Fragment paramFragment, boolean paramBoolean) {
    Object object;
    if (paramObject1 != null && paramObject2 != null && paramFragment != null) {
      if (paramBoolean) {
        paramBoolean = paramFragment.getAllowReturnTransitionOverlap();
      } else {
        paramBoolean = paramFragment.getAllowEnterTransitionOverlap();
      } 
    } else {
      paramBoolean = true;
    } 
    if (paramBoolean) {
      object = paramFragmentTransitionImpl.mergeTransitionsTogether(paramObject2, paramObject1, paramObject3);
    } else {
      object = object.mergeTransitionsInSequence(paramObject2, paramObject1, paramObject3);
    } 
    return object;
  }
  
  private static void replaceHide(FragmentTransitionImpl paramFragmentTransitionImpl, Object paramObject, Fragment paramFragment, final ArrayList<View> exitingViews) {
    if (paramFragment != null && paramObject != null && paramFragment.mAdded && paramFragment.mHidden && paramFragment.mHiddenChanged) {
      paramFragment.setHideReplaced(true);
      paramFragmentTransitionImpl.scheduleHideFragmentView(paramObject, paramFragment.getView(), exitingViews);
      OneShotPreDrawListener.add((View)paramFragment.mContainer, new Runnable() {
            public void run() {
              FragmentTransition.setViewVisibility(exitingViews, 4);
            }
          });
    } 
  }
  
  private static FragmentTransitionImpl resolveSupportImpl() {
    try {
      return Class.forName("android.support.transition.FragmentTransitionSupport").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
    } catch (Exception exception) {
      return null;
    } 
  }
  
  private static void retainValues(ArrayMap<String, String> paramArrayMap, ArrayMap<String, View> paramArrayMap1) {
    for (int i = paramArrayMap.size() - 1; i >= 0; i--) {
      if (!paramArrayMap1.containsKey(paramArrayMap.valueAt(i)))
        paramArrayMap.removeAt(i); 
    } 
  }
  
  private static void scheduleTargetChange(final FragmentTransitionImpl impl, ViewGroup paramViewGroup, final Fragment inFragment, final View nonExistentView, final ArrayList<View> sharedElementsIn, final Object enterTransition, final ArrayList<View> enteringViews, final Object exitTransition, final ArrayList<View> exitingViews) {
    OneShotPreDrawListener.add((View)paramViewGroup, new Runnable() {
          public void run() {
            Object object = enterTransition;
            if (object != null) {
              impl.removeTarget(object, nonExistentView);
              object = FragmentTransition.configureEnteringExitingViews(impl, enterTransition, inFragment, sharedElementsIn, nonExistentView);
              enteringViews.addAll((Collection)object);
            } 
            if (exitingViews != null) {
              if (exitTransition != null) {
                object = new ArrayList();
                object.add(nonExistentView);
                impl.replaceTargets(exitTransition, exitingViews, (ArrayList<View>)object);
              } 
              exitingViews.clear();
              exitingViews.add(nonExistentView);
            } 
          }
        });
  }
  
  private static void setOutEpicenter(FragmentTransitionImpl paramFragmentTransitionImpl, Object paramObject1, Object paramObject2, ArrayMap<String, View> paramArrayMap, boolean paramBoolean, BackStackRecord paramBackStackRecord) {
    if (paramBackStackRecord.mSharedElementSourceNames != null && !paramBackStackRecord.mSharedElementSourceNames.isEmpty()) {
      String str;
      if (paramBoolean) {
        str = paramBackStackRecord.mSharedElementTargetNames.get(0);
      } else {
        str = ((BackStackRecord)str).mSharedElementSourceNames.get(0);
      } 
      View view = (View)paramArrayMap.get(str);
      paramFragmentTransitionImpl.setEpicenter(paramObject1, view);
      if (paramObject2 != null)
        paramFragmentTransitionImpl.setEpicenter(paramObject2, view); 
    } 
  }
  
  private static void setViewVisibility(ArrayList<View> paramArrayList, int paramInt) {
    if (paramArrayList == null)
      return; 
    for (int i = paramArrayList.size() - 1; i >= 0; i--)
      ((View)paramArrayList.get(i)).setVisibility(paramInt); 
  }
  
  static void startTransitions(FragmentManagerImpl paramFragmentManagerImpl, ArrayList<BackStackRecord> paramArrayList, ArrayList<Boolean> paramArrayList1, int paramInt1, int paramInt2, boolean paramBoolean) {
    if (paramFragmentManagerImpl.mCurState < 1)
      return; 
    SparseArray<FragmentContainerTransition> sparseArray = new SparseArray();
    int i;
    for (i = paramInt1; i < paramInt2; i++) {
      BackStackRecord backStackRecord = paramArrayList.get(i);
      if (((Boolean)paramArrayList1.get(i)).booleanValue()) {
        calculatePopFragments(backStackRecord, sparseArray, paramBoolean);
      } else {
        calculateFragments(backStackRecord, sparseArray, paramBoolean);
      } 
    } 
    if (sparseArray.size() != 0) {
      View view = new View(paramFragmentManagerImpl.mHost.getContext());
      int j = sparseArray.size();
      for (i = 0; i < j; i++) {
        int k = sparseArray.keyAt(i);
        ArrayMap<String, String> arrayMap = calculateNameOverrides(k, paramArrayList, paramArrayList1, paramInt1, paramInt2);
        FragmentContainerTransition fragmentContainerTransition = (FragmentContainerTransition)sparseArray.valueAt(i);
        if (paramBoolean) {
          configureTransitionsReordered(paramFragmentManagerImpl, k, fragmentContainerTransition, view, arrayMap);
        } else {
          configureTransitionsOrdered(paramFragmentManagerImpl, k, fragmentContainerTransition, view, arrayMap);
        } 
      } 
    } 
  }
  
  static boolean supportsTransition() {
    return (PLATFORM_IMPL != null || SUPPORT_IMPL != null);
  }
  
  static {
    FragmentTransitionImpl fragmentTransitionImpl;
  }
  
  static {
    if (Build.VERSION.SDK_INT >= 21) {
      fragmentTransitionImpl = new FragmentTransitionCompat21();
    } else {
      fragmentTransitionImpl = null;
    } 
    PLATFORM_IMPL = fragmentTransitionImpl;
  }
  
  static class FragmentContainerTransition {
    public Fragment firstOut;
    
    public boolean firstOutIsPop;
    
    public BackStackRecord firstOutTransaction;
    
    public Fragment lastIn;
    
    public boolean lastInIsPop;
    
    public BackStackRecord lastInTransaction;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\app\FragmentTransition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */