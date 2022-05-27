package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Observable;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v4.os.TraceCompat;
import android.support.v4.util.Preconditions;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild2;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ScrollingView;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.recyclerview.R;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.FocusFinder;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.OverScroller;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerView extends ViewGroup implements ScrollingView, NestedScrollingChild2 {
  static {
    CLIP_TO_PADDING_ATTR = new int[] { 16842987 };
    if (Build.VERSION.SDK_INT == 18 || Build.VERSION.SDK_INT == 19 || Build.VERSION.SDK_INT == 20) {
      bool = true;
    } else {
      bool = false;
    } 
    FORCE_INVALIDATE_DISPLAY_LIST = bool;
    if (Build.VERSION.SDK_INT >= 23) {
      bool = true;
    } else {
      bool = false;
    } 
    ALLOW_SIZE_IN_UNSPECIFIED_SPEC = bool;
    if (Build.VERSION.SDK_INT >= 16) {
      bool = true;
    } else {
      bool = false;
    } 
    POST_UPDATES_ON_ANIMATION = bool;
    if (Build.VERSION.SDK_INT >= 21) {
      bool = true;
    } else {
      bool = false;
    } 
    ALLOW_THREAD_GAP_WORK = bool;
    if (Build.VERSION.SDK_INT <= 15) {
      bool = true;
    } else {
      bool = false;
    } 
    FORCE_ABS_FOCUS_SEARCH_DIRECTION = bool;
    if (Build.VERSION.SDK_INT <= 15) {
      bool = true;
    } else {
      bool = false;
    } 
    IGNORE_DETACHED_FOCUSED_CHILD = bool;
    LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE = new Class[] { Context.class, AttributeSet.class, int.class, int.class };
    sQuinticInterpolator = new Interpolator() {
        public float getInterpolation(float param1Float) {
          param1Float--;
          return param1Float * param1Float * param1Float * param1Float * param1Float + 1.0F;
        }
      };
  }
  
  public RecyclerView(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public RecyclerView(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public RecyclerView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    GapWorker.LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl;
    boolean bool1;
    this.mObserver = new RecyclerViewDataObserver();
    this.mRecycler = new Recycler();
    this.mViewInfoStore = new ViewInfoStore();
    this.mUpdateChildViewsRunnable = new Runnable() {
        public void run() {
          if (RecyclerView.this.mFirstLayoutComplete && !RecyclerView.this.isLayoutRequested()) {
            if (!RecyclerView.this.mIsAttached) {
              RecyclerView.this.requestLayout();
              return;
            } 
            if (RecyclerView.this.mLayoutFrozen) {
              RecyclerView.this.mLayoutWasDefered = true;
              return;
            } 
            RecyclerView.this.consumePendingUpdateOperations();
          } 
        }
      };
    this.mTempRect = new Rect();
    this.mTempRect2 = new Rect();
    this.mTempRectF = new RectF();
    this.mItemDecorations = new ArrayList<ItemDecoration>();
    this.mOnItemTouchListeners = new ArrayList<OnItemTouchListener>();
    this.mInterceptRequestLayoutDepth = 0;
    this.mDataSetHasChangedAfterLayout = false;
    this.mDispatchItemsChangedEvent = false;
    this.mLayoutOrScrollCounter = 0;
    this.mDispatchScrollCounter = 0;
    this.mEdgeEffectFactory = new EdgeEffectFactory();
    this.mItemAnimator = new DefaultItemAnimator();
    this.mScrollState = 0;
    this.mScrollPointerId = -1;
    this.mScaledHorizontalScrollFactor = Float.MIN_VALUE;
    this.mScaledVerticalScrollFactor = Float.MIN_VALUE;
    boolean bool = true;
    this.mPreserveFocusAfterLayout = true;
    this.mViewFlinger = new ViewFlinger();
    if (ALLOW_THREAD_GAP_WORK) {
      layoutPrefetchRegistryImpl = new GapWorker.LayoutPrefetchRegistryImpl();
    } else {
      layoutPrefetchRegistryImpl = null;
    } 
    this.mPrefetchRegistry = layoutPrefetchRegistryImpl;
    this.mState = new State();
    this.mItemsAddedOrRemoved = false;
    this.mItemsChanged = false;
    this.mItemAnimatorListener = new ItemAnimatorRestoreListener();
    this.mPostedAnimatorRunner = false;
    this.mMinMaxLayoutPositions = new int[2];
    this.mScrollOffset = new int[2];
    this.mScrollConsumed = new int[2];
    this.mNestedOffsets = new int[2];
    this.mPendingAccessibilityImportanceChange = new ArrayList<ViewHolder>();
    this.mItemAnimatorRunner = new Runnable() {
        public void run() {
          if (RecyclerView.this.mItemAnimator != null)
            RecyclerView.this.mItemAnimator.runPendingAnimations(); 
          RecyclerView.this.mPostedAnimatorRunner = false;
        }
      };
    this.mViewInfoProcessCallback = new ViewInfoStore.ProcessCallback() {
        public void processAppeared(RecyclerView.ViewHolder param1ViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo param1ItemHolderInfo1, RecyclerView.ItemAnimator.ItemHolderInfo param1ItemHolderInfo2) {
          RecyclerView.this.animateAppearance(param1ViewHolder, param1ItemHolderInfo1, param1ItemHolderInfo2);
        }
        
        public void processDisappeared(RecyclerView.ViewHolder param1ViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo param1ItemHolderInfo1, RecyclerView.ItemAnimator.ItemHolderInfo param1ItemHolderInfo2) {
          RecyclerView.this.mRecycler.unscrapView(param1ViewHolder);
          RecyclerView.this.animateDisappearance(param1ViewHolder, param1ItemHolderInfo1, param1ItemHolderInfo2);
        }
        
        public void processPersistent(RecyclerView.ViewHolder param1ViewHolder, RecyclerView.ItemAnimator.ItemHolderInfo param1ItemHolderInfo1, RecyclerView.ItemAnimator.ItemHolderInfo param1ItemHolderInfo2) {
          param1ViewHolder.setIsRecyclable(false);
          if (RecyclerView.this.mDataSetHasChangedAfterLayout) {
            if (RecyclerView.this.mItemAnimator.animateChange(param1ViewHolder, param1ViewHolder, param1ItemHolderInfo1, param1ItemHolderInfo2))
              RecyclerView.this.postAnimationRunner(); 
          } else if (RecyclerView.this.mItemAnimator.animatePersistence(param1ViewHolder, param1ItemHolderInfo1, param1ItemHolderInfo2)) {
            RecyclerView.this.postAnimationRunner();
          } 
        }
        
        public void unused(RecyclerView.ViewHolder param1ViewHolder) {
          RecyclerView.this.mLayout.removeAndRecycleView(param1ViewHolder.itemView, RecyclerView.this.mRecycler);
        }
      };
    if (paramAttributeSet != null) {
      TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, CLIP_TO_PADDING_ATTR, paramInt, 0);
      this.mClipToPadding = typedArray.getBoolean(0, true);
      typedArray.recycle();
    } else {
      this.mClipToPadding = true;
    } 
    setScrollContainer(true);
    setFocusableInTouchMode(true);
    ViewConfiguration viewConfiguration = ViewConfiguration.get(paramContext);
    this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
    this.mScaledHorizontalScrollFactor = ViewConfigurationCompat.getScaledHorizontalScrollFactor(viewConfiguration, paramContext);
    this.mScaledVerticalScrollFactor = ViewConfigurationCompat.getScaledVerticalScrollFactor(viewConfiguration, paramContext);
    this.mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
    this.mMaxFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
    if (getOverScrollMode() == 2) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    setWillNotDraw(bool1);
    this.mItemAnimator.setListener(this.mItemAnimatorListener);
    initAdapterManager();
    initChildrenHelper();
    if (ViewCompat.getImportantForAccessibility((View)this) == 0)
      ViewCompat.setImportantForAccessibility((View)this, 1); 
    this.mAccessibilityManager = (AccessibilityManager)getContext().getSystemService("accessibility");
    setAccessibilityDelegateCompat(new RecyclerViewAccessibilityDelegate(this));
    if (paramAttributeSet != null) {
      TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.RecyclerView, paramInt, 0);
      String str = typedArray.getString(R.styleable.RecyclerView_layoutManager);
      if (typedArray.getInt(R.styleable.RecyclerView_android_descendantFocusability, -1) == -1)
        setDescendantFocusability(262144); 
      bool1 = typedArray.getBoolean(R.styleable.RecyclerView_fastScrollEnabled, false);
      this.mEnableFastScroller = bool1;
      if (bool1)
        initFastScroller((StateListDrawable)typedArray.getDrawable(R.styleable.RecyclerView_fastScrollVerticalThumbDrawable), typedArray.getDrawable(R.styleable.RecyclerView_fastScrollVerticalTrackDrawable), (StateListDrawable)typedArray.getDrawable(R.styleable.RecyclerView_fastScrollHorizontalThumbDrawable), typedArray.getDrawable(R.styleable.RecyclerView_fastScrollHorizontalTrackDrawable)); 
      typedArray.recycle();
      createLayoutManager(paramContext, str, paramAttributeSet, paramInt, 0);
      bool1 = bool;
      if (Build.VERSION.SDK_INT >= 21) {
        TypedArray typedArray1 = paramContext.obtainStyledAttributes(paramAttributeSet, NESTED_SCROLLING_ATTRS, paramInt, 0);
        bool1 = typedArray1.getBoolean(0, true);
        typedArray1.recycle();
      } 
    } else {
      setDescendantFocusability(262144);
      bool1 = bool;
    } 
    setNestedScrollingEnabled(bool1);
  }
  
  private void addAnimatingView(ViewHolder paramViewHolder) {
    boolean bool;
    View view = paramViewHolder.itemView;
    if (view.getParent() == this) {
      bool = true;
    } else {
      bool = false;
    } 
    this.mRecycler.unscrapView(getChildViewHolder(view));
    if (paramViewHolder.isTmpDetached()) {
      this.mChildHelper.attachViewToParent(view, -1, view.getLayoutParams(), true);
    } else if (!bool) {
      this.mChildHelper.addView(view, true);
    } else {
      this.mChildHelper.hide(view);
    } 
  }
  
  private void animateChange(ViewHolder paramViewHolder1, ViewHolder paramViewHolder2, ItemAnimator.ItemHolderInfo paramItemHolderInfo1, ItemAnimator.ItemHolderInfo paramItemHolderInfo2, boolean paramBoolean1, boolean paramBoolean2) {
    paramViewHolder1.setIsRecyclable(false);
    if (paramBoolean1)
      addAnimatingView(paramViewHolder1); 
    if (paramViewHolder1 != paramViewHolder2) {
      if (paramBoolean2)
        addAnimatingView(paramViewHolder2); 
      paramViewHolder1.mShadowedHolder = paramViewHolder2;
      addAnimatingView(paramViewHolder1);
      this.mRecycler.unscrapView(paramViewHolder1);
      paramViewHolder2.setIsRecyclable(false);
      paramViewHolder2.mShadowingHolder = paramViewHolder1;
    } 
    if (this.mItemAnimator.animateChange(paramViewHolder1, paramViewHolder2, paramItemHolderInfo1, paramItemHolderInfo2))
      postAnimationRunner(); 
  }
  
  private void cancelTouch() {
    resetTouch();
    setScrollState(0);
  }
  
  static void clearNestedRecyclerViewIfNotNested(ViewHolder paramViewHolder) {
    if (paramViewHolder.mNestedRecyclerView != null) {
      View view = (View)paramViewHolder.mNestedRecyclerView.get();
      while (view != null) {
        if (view == paramViewHolder.itemView)
          return; 
        ViewParent viewParent = view.getParent();
        if (viewParent instanceof View) {
          View view1 = (View)viewParent;
          continue;
        } 
        viewParent = null;
      } 
      paramViewHolder.mNestedRecyclerView = null;
    } 
  }
  
  private void createLayoutManager(Context paramContext, String paramString, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
    if (paramString != null) {
      paramString = paramString.trim();
      if (!paramString.isEmpty()) {
        String str = getFullClassName(paramContext, paramString);
        try {
          Object[] arrayOfObject;
          IllegalStateException illegalStateException;
          if (isInEditMode()) {
            classLoader = getClass().getClassLoader();
          } else {
            classLoader = paramContext.getClassLoader();
          } 
          Class<? extends LayoutManager> clazz = classLoader.loadClass(str).asSubclass(LayoutManager.class);
          ClassLoader classLoader = null;
          try {
            Constructor<? extends LayoutManager> constructor2 = clazz.getConstructor(LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE);
            arrayOfObject = new Object[] { paramContext, paramAttributeSet, Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) };
            Constructor<? extends LayoutManager> constructor1 = constructor2;
          } catch (NoSuchMethodException noSuchMethodException1) {
            try {
              Constructor<? extends LayoutManager> constructor = clazz.getConstructor(new Class[0]);
              constructor.setAccessible(true);
              setLayoutManager(constructor.newInstance(arrayOfObject));
            } catch (NoSuchMethodException noSuchMethodException) {
              noSuchMethodException.initCause(noSuchMethodException1);
              illegalStateException = new IllegalStateException();
              StringBuilder stringBuilder = new StringBuilder();
              this();
              stringBuilder.append(paramAttributeSet.getPositionDescription());
              stringBuilder.append(": Error creating LayoutManager ");
              stringBuilder.append(str);
              this(stringBuilder.toString(), noSuchMethodException);
              throw illegalStateException;
            } 
          } 
          noSuchMethodException.setAccessible(true);
          setLayoutManager(noSuchMethodException.newInstance((Object[])illegalStateException));
        } catch (ClassNotFoundException classNotFoundException) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(paramAttributeSet.getPositionDescription());
          stringBuilder.append(": Unable to find LayoutManager ");
          stringBuilder.append(str);
          throw new IllegalStateException(stringBuilder.toString(), classNotFoundException);
        } catch (InvocationTargetException invocationTargetException) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(paramAttributeSet.getPositionDescription());
          stringBuilder.append(": Could not instantiate the LayoutManager: ");
          stringBuilder.append(str);
          throw new IllegalStateException(stringBuilder.toString(), invocationTargetException);
        } catch (InstantiationException instantiationException) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(paramAttributeSet.getPositionDescription());
          stringBuilder.append(": Could not instantiate the LayoutManager: ");
          stringBuilder.append(str);
          throw new IllegalStateException(stringBuilder.toString(), instantiationException);
        } catch (IllegalAccessException illegalAccessException) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(paramAttributeSet.getPositionDescription());
          stringBuilder.append(": Cannot access non-public constructor ");
          stringBuilder.append(str);
          throw new IllegalStateException(stringBuilder.toString(), illegalAccessException);
        } catch (ClassCastException classCastException) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(paramAttributeSet.getPositionDescription());
          stringBuilder.append(": Class is not a LayoutManager ");
          stringBuilder.append(str);
          throw new IllegalStateException(stringBuilder.toString(), classCastException);
        } 
      } 
    } 
  }
  
  private boolean didChildRangeChange(int paramInt1, int paramInt2) {
    findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
    int[] arrayOfInt = this.mMinMaxLayoutPositions;
    boolean bool = false;
    if (arrayOfInt[0] != paramInt1 || arrayOfInt[1] != paramInt2)
      bool = true; 
    return bool;
  }
  
  private void dispatchContentChangedIfNecessary() {
    int i = this.mEatenAccessibilityChangeFlags;
    this.mEatenAccessibilityChangeFlags = 0;
    if (i != 0 && isAccessibilityEnabled()) {
      AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain();
      accessibilityEvent.setEventType(2048);
      AccessibilityEventCompat.setContentChangeTypes(accessibilityEvent, i);
      sendAccessibilityEventUnchecked(accessibilityEvent);
    } 
  }
  
  private void dispatchLayoutStep1() {
    State state = this.mState;
    boolean bool = true;
    state.assertLayoutStep(1);
    fillRemainingScrollValues(this.mState);
    this.mState.mIsMeasuring = false;
    startInterceptRequestLayout();
    this.mViewInfoStore.clear();
    onEnterLayoutOrScroll();
    processAdapterUpdatesAndSetAnimationFlags();
    saveFocusInfo();
    state = this.mState;
    if (!state.mRunSimpleAnimations || !this.mItemsChanged)
      bool = false; 
    state.mTrackOldChangeHolders = bool;
    this.mItemsChanged = false;
    this.mItemsAddedOrRemoved = false;
    state = this.mState;
    state.mInPreLayout = state.mRunPredictiveAnimations;
    this.mState.mItemCount = this.mAdapter.getItemCount();
    findMinMaxChildLayoutPositions(this.mMinMaxLayoutPositions);
    if (this.mState.mRunSimpleAnimations) {
      int i = this.mChildHelper.getChildCount();
      for (byte b = 0; b < i; b++) {
        ViewHolder viewHolder = getChildViewHolderInt(this.mChildHelper.getChildAt(b));
        if (!viewHolder.shouldIgnore() && (!viewHolder.isInvalid() || this.mAdapter.hasStableIds())) {
          ItemAnimator.ItemHolderInfo itemHolderInfo = this.mItemAnimator.recordPreLayoutInformation(this.mState, viewHolder, ItemAnimator.buildAdapterChangeFlagsForAnimations(viewHolder), viewHolder.getUnmodifiedPayloads());
          this.mViewInfoStore.addToPreLayout(viewHolder, itemHolderInfo);
          if (this.mState.mTrackOldChangeHolders && viewHolder.isUpdated() && !viewHolder.isRemoved() && !viewHolder.shouldIgnore() && !viewHolder.isInvalid()) {
            long l = getChangedHolderKey(viewHolder);
            this.mViewInfoStore.addToOldChangeHolders(l, viewHolder);
          } 
        } 
      } 
    } 
    if (this.mState.mRunPredictiveAnimations) {
      saveOldPositions();
      bool = this.mState.mStructureChanged;
      this.mState.mStructureChanged = false;
      this.mLayout.onLayoutChildren(this.mRecycler, this.mState);
      this.mState.mStructureChanged = bool;
      for (byte b = 0; b < this.mChildHelper.getChildCount(); b++) {
        ViewHolder viewHolder = getChildViewHolderInt(this.mChildHelper.getChildAt(b));
        if (!viewHolder.shouldIgnore() && !this.mViewInfoStore.isInPreLayout(viewHolder)) {
          int j = ItemAnimator.buildAdapterChangeFlagsForAnimations(viewHolder);
          bool = viewHolder.hasAnyOfTheFlags(8192);
          int i = j;
          if (!bool)
            i = j | 0x1000; 
          ItemAnimator.ItemHolderInfo itemHolderInfo = this.mItemAnimator.recordPreLayoutInformation(this.mState, viewHolder, i, viewHolder.getUnmodifiedPayloads());
          if (bool) {
            recordAnimationInfoIfBouncedHiddenView(viewHolder, itemHolderInfo);
          } else {
            this.mViewInfoStore.addToAppearedInPreLayoutHolders(viewHolder, itemHolderInfo);
          } 
        } 
      } 
      clearOldPositions();
    } else {
      clearOldPositions();
    } 
    onExitLayoutOrScroll();
    stopInterceptRequestLayout(false);
    this.mState.mLayoutStep = 2;
  }
  
  private void dispatchLayoutStep2() {
    boolean bool;
    startInterceptRequestLayout();
    onEnterLayoutOrScroll();
    this.mState.assertLayoutStep(6);
    this.mAdapterHelper.consumeUpdatesInOnePass();
    this.mState.mItemCount = this.mAdapter.getItemCount();
    this.mState.mDeletedInvisibleItemCountSincePreviousLayout = 0;
    this.mState.mInPreLayout = false;
    this.mLayout.onLayoutChildren(this.mRecycler, this.mState);
    this.mState.mStructureChanged = false;
    this.mPendingSavedState = null;
    State state = this.mState;
    if (state.mRunSimpleAnimations && this.mItemAnimator != null) {
      bool = true;
    } else {
      bool = false;
    } 
    state.mRunSimpleAnimations = bool;
    this.mState.mLayoutStep = 4;
    onExitLayoutOrScroll();
    stopInterceptRequestLayout(false);
  }
  
  private void dispatchLayoutStep3() {
    this.mState.assertLayoutStep(4);
    startInterceptRequestLayout();
    onEnterLayoutOrScroll();
    this.mState.mLayoutStep = 1;
    if (this.mState.mRunSimpleAnimations) {
      for (int i = this.mChildHelper.getChildCount() - 1; i >= 0; i--) {
        ViewHolder viewHolder = getChildViewHolderInt(this.mChildHelper.getChildAt(i));
        if (!viewHolder.shouldIgnore()) {
          long l = getChangedHolderKey(viewHolder);
          ItemAnimator.ItemHolderInfo itemHolderInfo = this.mItemAnimator.recordPostLayoutInformation(this.mState, viewHolder);
          ViewHolder viewHolder1 = this.mViewInfoStore.getFromOldChangeHolders(l);
          if (viewHolder1 != null && !viewHolder1.shouldIgnore()) {
            boolean bool1 = this.mViewInfoStore.isDisappearing(viewHolder1);
            boolean bool2 = this.mViewInfoStore.isDisappearing(viewHolder);
            if (bool1 && viewHolder1 == viewHolder) {
              this.mViewInfoStore.addToPostLayout(viewHolder, itemHolderInfo);
            } else {
              ItemAnimator.ItemHolderInfo itemHolderInfo1 = this.mViewInfoStore.popFromPreLayout(viewHolder1);
              this.mViewInfoStore.addToPostLayout(viewHolder, itemHolderInfo);
              itemHolderInfo = this.mViewInfoStore.popFromPostLayout(viewHolder);
              if (itemHolderInfo1 == null) {
                handleMissingPreInfoForChangeError(l, viewHolder, viewHolder1);
              } else {
                animateChange(viewHolder1, viewHolder, itemHolderInfo1, itemHolderInfo, bool1, bool2);
              } 
            } 
          } else {
            this.mViewInfoStore.addToPostLayout(viewHolder, itemHolderInfo);
          } 
        } 
      } 
      this.mViewInfoStore.process(this.mViewInfoProcessCallback);
    } 
    this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
    State state = this.mState;
    state.mPreviousLayoutItemCount = state.mItemCount;
    this.mDataSetHasChangedAfterLayout = false;
    this.mDispatchItemsChangedEvent = false;
    this.mState.mRunSimpleAnimations = false;
    this.mState.mRunPredictiveAnimations = false;
    this.mLayout.mRequestedSimpleAnimations = false;
    if (this.mRecycler.mChangedScrap != null)
      this.mRecycler.mChangedScrap.clear(); 
    if (this.mLayout.mPrefetchMaxObservedInInitialPrefetch) {
      this.mLayout.mPrefetchMaxCountObserved = 0;
      this.mLayout.mPrefetchMaxObservedInInitialPrefetch = false;
      this.mRecycler.updateViewCacheSize();
    } 
    this.mLayout.onLayoutCompleted(this.mState);
    onExitLayoutOrScroll();
    stopInterceptRequestLayout(false);
    this.mViewInfoStore.clear();
    int[] arrayOfInt = this.mMinMaxLayoutPositions;
    if (didChildRangeChange(arrayOfInt[0], arrayOfInt[1]))
      dispatchOnScrolled(0, 0); 
    recoverFocusFromState();
    resetFocusInfo();
  }
  
  private boolean dispatchOnItemTouch(MotionEvent paramMotionEvent) {
    int i = paramMotionEvent.getAction();
    OnItemTouchListener onItemTouchListener = this.mActiveOnItemTouchListener;
    if (onItemTouchListener != null)
      if (i == 0) {
        this.mActiveOnItemTouchListener = null;
      } else {
        onItemTouchListener.onTouchEvent(this, paramMotionEvent);
        if (i == 3 || i == 1)
          this.mActiveOnItemTouchListener = null; 
        return true;
      }  
    if (i != 0) {
      int j = this.mOnItemTouchListeners.size();
      for (i = 0; i < j; i++) {
        onItemTouchListener = this.mOnItemTouchListeners.get(i);
        if (onItemTouchListener.onInterceptTouchEvent(this, paramMotionEvent)) {
          this.mActiveOnItemTouchListener = onItemTouchListener;
          return true;
        } 
      } 
    } 
    return false;
  }
  
  private boolean dispatchOnItemTouchIntercept(MotionEvent paramMotionEvent) {
    int i = paramMotionEvent.getAction();
    if (i == 3 || i == 0)
      this.mActiveOnItemTouchListener = null; 
    int j = this.mOnItemTouchListeners.size();
    for (byte b = 0; b < j; b++) {
      OnItemTouchListener onItemTouchListener = this.mOnItemTouchListeners.get(b);
      if (onItemTouchListener.onInterceptTouchEvent(this, paramMotionEvent) && i != 3) {
        this.mActiveOnItemTouchListener = onItemTouchListener;
        return true;
      } 
    } 
    return false;
  }
  
  private void findMinMaxChildLayoutPositions(int[] paramArrayOfint) {
    int i = this.mChildHelper.getChildCount();
    if (i == 0) {
      paramArrayOfint[0] = -1;
      paramArrayOfint[1] = -1;
      return;
    } 
    int j = Integer.MAX_VALUE;
    int k = Integer.MIN_VALUE;
    byte b = 0;
    while (b < i) {
      int m;
      ViewHolder viewHolder = getChildViewHolderInt(this.mChildHelper.getChildAt(b));
      if (viewHolder.shouldIgnore()) {
        m = k;
      } else {
        int n = viewHolder.getLayoutPosition();
        int i1 = j;
        if (n < j)
          i1 = n; 
        j = i1;
        m = k;
        if (n > k) {
          m = n;
          j = i1;
        } 
      } 
      b++;
      k = m;
    } 
    paramArrayOfint[0] = j;
    paramArrayOfint[1] = k;
  }
  
  static RecyclerView findNestedRecyclerView(View paramView) {
    if (!(paramView instanceof ViewGroup))
      return null; 
    if (paramView instanceof RecyclerView)
      return (RecyclerView)paramView; 
    ViewGroup viewGroup = (ViewGroup)paramView;
    int i = viewGroup.getChildCount();
    for (byte b = 0; b < i; b++) {
      RecyclerView recyclerView = findNestedRecyclerView(viewGroup.getChildAt(b));
      if (recyclerView != null)
        return recyclerView; 
    } 
    return null;
  }
  
  private View findNextViewToFocus() {
    if (this.mState.mFocusedItemPosition != -1) {
      i = this.mState.mFocusedItemPosition;
    } else {
      i = 0;
    } 
    int j = this.mState.getItemCount();
    for (int k = i; k < j; k++) {
      ViewHolder viewHolder = findViewHolderForAdapterPosition(k);
      if (viewHolder == null)
        break; 
      if (viewHolder.itemView.hasFocusable())
        return viewHolder.itemView; 
    } 
    for (int i = Math.min(j, i) - 1; i >= 0; i--) {
      ViewHolder viewHolder = findViewHolderForAdapterPosition(i);
      if (viewHolder == null)
        return null; 
      if (viewHolder.itemView.hasFocusable())
        return viewHolder.itemView; 
    } 
    return null;
  }
  
  static ViewHolder getChildViewHolderInt(View paramView) {
    return (paramView == null) ? null : ((LayoutParams)paramView.getLayoutParams()).mViewHolder;
  }
  
  static void getDecoratedBoundsWithMarginsInt(View paramView, Rect paramRect) {
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    Rect rect = layoutParams.mDecorInsets;
    paramRect.set(paramView.getLeft() - rect.left - layoutParams.leftMargin, paramView.getTop() - rect.top - layoutParams.topMargin, paramView.getRight() + rect.right + layoutParams.rightMargin, paramView.getBottom() + rect.bottom + layoutParams.bottomMargin);
  }
  
  private int getDeepestFocusedViewWithId(View paramView) {
    int i = paramView.getId();
    while (!paramView.isFocused() && paramView instanceof ViewGroup && paramView.hasFocus()) {
      View view = ((ViewGroup)paramView).getFocusedChild();
      paramView = view;
      if (view.getId() != -1) {
        i = view.getId();
        paramView = view;
      } 
    } 
    return i;
  }
  
  private String getFullClassName(Context paramContext, String paramString) {
    if (paramString.charAt(0) == '.') {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(paramContext.getPackageName());
      stringBuilder1.append(paramString);
      return stringBuilder1.toString();
    } 
    if (paramString.contains("."))
      return paramString; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(RecyclerView.class.getPackage().getName());
    stringBuilder.append('.');
    stringBuilder.append(paramString);
    return stringBuilder.toString();
  }
  
  private NestedScrollingChildHelper getScrollingChildHelper() {
    if (this.mScrollingChildHelper == null)
      this.mScrollingChildHelper = new NestedScrollingChildHelper((View)this); 
    return this.mScrollingChildHelper;
  }
  
  private void handleMissingPreInfoForChangeError(long paramLong, ViewHolder paramViewHolder1, ViewHolder paramViewHolder2) {
    StringBuilder stringBuilder1;
    int i = this.mChildHelper.getChildCount();
    for (byte b = 0; b < i; b++) {
      ViewHolder viewHolder = getChildViewHolderInt(this.mChildHelper.getChildAt(b));
      if (viewHolder != paramViewHolder1 && getChangedHolderKey(viewHolder) == paramLong) {
        Adapter adapter = this.mAdapter;
        if (adapter != null && adapter.hasStableIds()) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Two different ViewHolders have the same stable ID. Stable IDs in your adapter MUST BE unique and SHOULD NOT change.\n ViewHolder 1:");
          stringBuilder.append(viewHolder);
          stringBuilder.append(" \n View Holder 2:");
          stringBuilder.append(paramViewHolder1);
          stringBuilder.append(exceptionLabel());
          throw new IllegalStateException(stringBuilder.toString());
        } 
        stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Two different ViewHolders have the same change ID. This might happen due to inconsistent Adapter update events or if the LayoutManager lays out the same View multiple times.\n ViewHolder 1:");
        stringBuilder1.append(viewHolder);
        stringBuilder1.append(" \n View Holder 2:");
        stringBuilder1.append(paramViewHolder1);
        stringBuilder1.append(exceptionLabel());
        throw new IllegalStateException(stringBuilder1.toString());
      } 
    } 
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("Problem while matching changed view holders with the newones. The pre-layout information for the change holder ");
    stringBuilder2.append(stringBuilder1);
    stringBuilder2.append(" cannot be found but it is necessary for ");
    stringBuilder2.append(paramViewHolder1);
    stringBuilder2.append(exceptionLabel());
    Log.e("RecyclerView", stringBuilder2.toString());
  }
  
  private boolean hasUpdatedView() {
    int i = this.mChildHelper.getChildCount();
    for (byte b = 0; b < i; b++) {
      ViewHolder viewHolder = getChildViewHolderInt(this.mChildHelper.getChildAt(b));
      if (viewHolder != null && !viewHolder.shouldIgnore() && viewHolder.isUpdated())
        return true; 
    } 
    return false;
  }
  
  private void initChildrenHelper() {
    this.mChildHelper = new ChildHelper(new ChildHelper.Callback() {
          public void addView(View param1View, int param1Int) {
            RecyclerView.this.addView(param1View, param1Int);
            RecyclerView.this.dispatchChildAttached(param1View);
          }
          
          public void attachViewToParent(View param1View, int param1Int, ViewGroup.LayoutParams param1LayoutParams) {
            StringBuilder stringBuilder;
            RecyclerView.ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(param1View);
            if (viewHolder != null)
              if (viewHolder.isTmpDetached() || viewHolder.shouldIgnore()) {
                viewHolder.clearTmpDetachFlag();
              } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Called attach on a child which is not detached: ");
                stringBuilder.append(viewHolder);
                stringBuilder.append(RecyclerView.this.exceptionLabel());
                throw new IllegalArgumentException(stringBuilder.toString());
              }  
            RecyclerView.this.attachViewToParent((View)stringBuilder, param1Int, param1LayoutParams);
          }
          
          public void detachViewFromParent(int param1Int) {
            View view = getChildAt(param1Int);
            if (view != null) {
              RecyclerView.ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
              if (viewHolder != null)
                if (!viewHolder.isTmpDetached() || viewHolder.shouldIgnore()) {
                  viewHolder.addFlags(256);
                } else {
                  StringBuilder stringBuilder = new StringBuilder();
                  stringBuilder.append("called detach on an already detached child ");
                  stringBuilder.append(viewHolder);
                  stringBuilder.append(RecyclerView.this.exceptionLabel());
                  throw new IllegalArgumentException(stringBuilder.toString());
                }  
            } 
            RecyclerView.this.detachViewFromParent(param1Int);
          }
          
          public View getChildAt(int param1Int) {
            return RecyclerView.this.getChildAt(param1Int);
          }
          
          public int getChildCount() {
            return RecyclerView.this.getChildCount();
          }
          
          public RecyclerView.ViewHolder getChildViewHolder(View param1View) {
            return RecyclerView.getChildViewHolderInt(param1View);
          }
          
          public int indexOfChild(View param1View) {
            return RecyclerView.this.indexOfChild(param1View);
          }
          
          public void onEnteredHiddenState(View param1View) {
            RecyclerView.ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(param1View);
            if (viewHolder != null)
              viewHolder.onEnteredHiddenState(RecyclerView.this); 
          }
          
          public void onLeftHiddenState(View param1View) {
            RecyclerView.ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(param1View);
            if (viewHolder != null)
              viewHolder.onLeftHiddenState(RecyclerView.this); 
          }
          
          public void removeAllViews() {
            int i = getChildCount();
            for (byte b = 0; b < i; b++) {
              View view = getChildAt(b);
              RecyclerView.this.dispatchChildDetached(view);
              view.clearAnimation();
            } 
            RecyclerView.this.removeAllViews();
          }
          
          public void removeViewAt(int param1Int) {
            View view = RecyclerView.this.getChildAt(param1Int);
            if (view != null) {
              RecyclerView.this.dispatchChildDetached(view);
              view.clearAnimation();
            } 
            RecyclerView.this.removeViewAt(param1Int);
          }
        });
  }
  
  private boolean isPreferredNextFocus(View paramView1, View paramView2, int paramInt) {
    boolean bool1 = false;
    boolean bool2 = false;
    boolean bool3 = false;
    boolean bool4 = false;
    boolean bool5 = false;
    boolean bool6 = false;
    boolean bool7 = bool5;
    if (paramView2 != null)
      if (paramView2 == this) {
        bool7 = bool5;
      } else {
        byte b1;
        if (findContainingItemView(paramView2) == null)
          return false; 
        if (paramView1 == null)
          return true; 
        if (findContainingItemView(paramView1) == null)
          return true; 
        this.mTempRect.set(0, 0, paramView1.getWidth(), paramView1.getHeight());
        this.mTempRect2.set(0, 0, paramView2.getWidth(), paramView2.getHeight());
        offsetDescendantRectToMyCoords(paramView1, this.mTempRect);
        offsetDescendantRectToMyCoords(paramView2, this.mTempRect2);
        int i = this.mLayout.getLayoutDirection();
        byte b = -1;
        if (i == 1) {
          b1 = -1;
        } else {
          b1 = 1;
        } 
        if ((this.mTempRect.left < this.mTempRect2.left || this.mTempRect.right <= this.mTempRect2.left) && this.mTempRect.right < this.mTempRect2.right) {
          i = 1;
        } else if ((this.mTempRect.right > this.mTempRect2.right || this.mTempRect.left >= this.mTempRect2.right) && this.mTempRect.left > this.mTempRect2.left) {
          i = -1;
        } else {
          i = 0;
        } 
        if ((this.mTempRect.top < this.mTempRect2.top || this.mTempRect.bottom <= this.mTempRect2.top) && this.mTempRect.bottom < this.mTempRect2.bottom) {
          b = 1;
        } else if ((this.mTempRect.bottom <= this.mTempRect2.bottom && this.mTempRect.top < this.mTempRect2.bottom) || this.mTempRect.top <= this.mTempRect2.top) {
          b = 0;
        } 
        if (paramInt != 1) {
          if (paramInt != 2) {
            if (paramInt != 17) {
              if (paramInt != 33) {
                if (paramInt != 66) {
                  if (paramInt == 130) {
                    bool7 = bool6;
                    if (b > 0)
                      bool7 = true; 
                    return bool7;
                  } 
                  StringBuilder stringBuilder = new StringBuilder();
                  stringBuilder.append("Invalid direction: ");
                  stringBuilder.append(paramInt);
                  stringBuilder.append(exceptionLabel());
                  throw new IllegalArgumentException(stringBuilder.toString());
                } 
                bool7 = bool1;
                if (i > 0)
                  bool7 = true; 
                return bool7;
              } 
              bool7 = bool2;
              if (b < 0)
                bool7 = true; 
              return bool7;
            } 
            bool7 = bool3;
            if (i < 0)
              bool7 = true; 
            return bool7;
          } 
          if (b <= 0) {
            bool7 = bool4;
            if (b == 0) {
              bool7 = bool4;
              if (i * b1 >= 0)
                bool7 = true; 
            } 
            return bool7;
          } 
        } else {
          if (b >= 0) {
            bool7 = bool5;
            if (b == 0) {
              bool7 = bool5;
              if (i * b1 <= 0)
                bool7 = true; 
            } 
            return bool7;
          } 
          bool7 = true;
        } 
        bool7 = true;
      }  
    return bool7;
  }
  
  private void onPointerUp(MotionEvent paramMotionEvent) {
    int i = paramMotionEvent.getActionIndex();
    if (paramMotionEvent.getPointerId(i) == this.mScrollPointerId) {
      if (i == 0) {
        i = 1;
      } else {
        i = 0;
      } 
      this.mScrollPointerId = paramMotionEvent.getPointerId(i);
      int j = (int)(paramMotionEvent.getX(i) + 0.5F);
      this.mLastTouchX = j;
      this.mInitialTouchX = j;
      i = (int)(paramMotionEvent.getY(i) + 0.5F);
      this.mLastTouchY = i;
      this.mInitialTouchY = i;
    } 
  }
  
  private boolean predictiveItemAnimationsEnabled() {
    boolean bool;
    if (this.mItemAnimator != null && this.mLayout.supportsPredictiveItemAnimations()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private void processAdapterUpdatesAndSetAnimationFlags() {
    boolean bool2;
    if (this.mDataSetHasChangedAfterLayout) {
      this.mAdapterHelper.reset();
      if (this.mDispatchItemsChangedEvent)
        this.mLayout.onItemsChanged(this); 
    } 
    if (predictiveItemAnimationsEnabled()) {
      this.mAdapterHelper.preProcess();
    } else {
      this.mAdapterHelper.consumeUpdatesInOnePass();
    } 
    boolean bool = this.mItemsAddedOrRemoved;
    boolean bool1 = false;
    if (bool || this.mItemsChanged) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    State state = this.mState;
    if (this.mFirstLayoutComplete && this.mItemAnimator != null && (this.mDataSetHasChangedAfterLayout || bool2 || this.mLayout.mRequestedSimpleAnimations) && (!this.mDataSetHasChangedAfterLayout || this.mAdapter.hasStableIds())) {
      bool = true;
    } else {
      bool = false;
    } 
    state.mRunSimpleAnimations = bool;
    state = this.mState;
    bool = bool1;
    if (state.mRunSimpleAnimations) {
      bool = bool1;
      if (bool2) {
        bool = bool1;
        if (!this.mDataSetHasChangedAfterLayout) {
          bool = bool1;
          if (predictiveItemAnimationsEnabled())
            bool = true; 
        } 
      } 
    } 
    state.mRunPredictiveAnimations = bool;
  }
  
  private void pullGlows(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    // Byte code:
    //   0: iconst_1
    //   1: istore #5
    //   3: fload_2
    //   4: fconst_0
    //   5: fcmpg
    //   6: ifge -> 43
    //   9: aload_0
    //   10: invokevirtual ensureLeftGlow : ()V
    //   13: aload_0
    //   14: getfield mLeftGlow : Landroid/widget/EdgeEffect;
    //   17: fload_2
    //   18: fneg
    //   19: aload_0
    //   20: invokevirtual getWidth : ()I
    //   23: i2f
    //   24: fdiv
    //   25: fconst_1
    //   26: fload_3
    //   27: aload_0
    //   28: invokevirtual getHeight : ()I
    //   31: i2f
    //   32: fdiv
    //   33: fsub
    //   34: invokestatic onPull : (Landroid/widget/EdgeEffect;FF)V
    //   37: iconst_1
    //   38: istore #6
    //   40: goto -> 80
    //   43: fload_2
    //   44: fconst_0
    //   45: fcmpl
    //   46: ifle -> 77
    //   49: aload_0
    //   50: invokevirtual ensureRightGlow : ()V
    //   53: aload_0
    //   54: getfield mRightGlow : Landroid/widget/EdgeEffect;
    //   57: fload_2
    //   58: aload_0
    //   59: invokevirtual getWidth : ()I
    //   62: i2f
    //   63: fdiv
    //   64: fload_3
    //   65: aload_0
    //   66: invokevirtual getHeight : ()I
    //   69: i2f
    //   70: fdiv
    //   71: invokestatic onPull : (Landroid/widget/EdgeEffect;FF)V
    //   74: goto -> 37
    //   77: iconst_0
    //   78: istore #6
    //   80: fload #4
    //   82: fconst_0
    //   83: fcmpg
    //   84: ifge -> 121
    //   87: aload_0
    //   88: invokevirtual ensureTopGlow : ()V
    //   91: aload_0
    //   92: getfield mTopGlow : Landroid/widget/EdgeEffect;
    //   95: fload #4
    //   97: fneg
    //   98: aload_0
    //   99: invokevirtual getHeight : ()I
    //   102: i2f
    //   103: fdiv
    //   104: fload_1
    //   105: aload_0
    //   106: invokevirtual getWidth : ()I
    //   109: i2f
    //   110: fdiv
    //   111: invokestatic onPull : (Landroid/widget/EdgeEffect;FF)V
    //   114: iload #5
    //   116: istore #6
    //   118: goto -> 163
    //   121: fload #4
    //   123: fconst_0
    //   124: fcmpl
    //   125: ifle -> 163
    //   128: aload_0
    //   129: invokevirtual ensureBottomGlow : ()V
    //   132: aload_0
    //   133: getfield mBottomGlow : Landroid/widget/EdgeEffect;
    //   136: fload #4
    //   138: aload_0
    //   139: invokevirtual getHeight : ()I
    //   142: i2f
    //   143: fdiv
    //   144: fconst_1
    //   145: fload_1
    //   146: aload_0
    //   147: invokevirtual getWidth : ()I
    //   150: i2f
    //   151: fdiv
    //   152: fsub
    //   153: invokestatic onPull : (Landroid/widget/EdgeEffect;FF)V
    //   156: iload #5
    //   158: istore #6
    //   160: goto -> 163
    //   163: iload #6
    //   165: ifne -> 181
    //   168: fload_2
    //   169: fconst_0
    //   170: fcmpl
    //   171: ifne -> 181
    //   174: fload #4
    //   176: fconst_0
    //   177: fcmpl
    //   178: ifeq -> 185
    //   181: aload_0
    //   182: invokestatic postInvalidateOnAnimation : (Landroid/view/View;)V
    //   185: return
  }
  
  private void recoverFocusFromState() {
    if (this.mPreserveFocusAfterLayout && this.mAdapter != null && hasFocus() && getDescendantFocusability() != 393216 && (getDescendantFocusability() != 131072 || !isFocused())) {
      View view1;
      if (!isFocused()) {
        view1 = getFocusedChild();
        if (IGNORE_DETACHED_FOCUSED_CHILD && (view1.getParent() == null || !view1.hasFocus())) {
          if (this.mChildHelper.getChildCount() == 0) {
            requestFocus();
            return;
          } 
        } else if (!this.mChildHelper.isHidden(view1)) {
          return;
        } 
      } 
      long l = this.mState.mFocusedItemId;
      View view2 = null;
      if (l != -1L && this.mAdapter.hasStableIds()) {
        view1 = (View)findViewHolderForItemId(this.mState.mFocusedItemId);
      } else {
        view1 = null;
      } 
      if (view1 == null || this.mChildHelper.isHidden(((ViewHolder)view1).itemView) || !((ViewHolder)view1).itemView.hasFocusable()) {
        view1 = view2;
        if (this.mChildHelper.getChildCount() > 0)
          view1 = findNextViewToFocus(); 
      } else {
        view1 = ((ViewHolder)view1).itemView;
      } 
      if (view1 != null) {
        view2 = view1;
        if (this.mState.mFocusedSubChildId != -1L) {
          View view = view1.findViewById(this.mState.mFocusedSubChildId);
          view2 = view1;
          if (view != null) {
            view2 = view1;
            if (view.isFocusable())
              view2 = view; 
          } 
        } 
        view2.requestFocus();
      } 
    } 
  }
  
  private void releaseGlows() {
    EdgeEffect edgeEffect = this.mLeftGlow;
    if (edgeEffect != null) {
      edgeEffect.onRelease();
      bool1 = this.mLeftGlow.isFinished();
    } else {
      bool1 = false;
    } 
    edgeEffect = this.mTopGlow;
    boolean bool2 = bool1;
    if (edgeEffect != null) {
      edgeEffect.onRelease();
      bool2 = bool1 | this.mTopGlow.isFinished();
    } 
    edgeEffect = this.mRightGlow;
    boolean bool1 = bool2;
    if (edgeEffect != null) {
      edgeEffect.onRelease();
      bool1 = bool2 | this.mRightGlow.isFinished();
    } 
    edgeEffect = this.mBottomGlow;
    bool2 = bool1;
    if (edgeEffect != null) {
      edgeEffect.onRelease();
      bool2 = bool1 | this.mBottomGlow.isFinished();
    } 
    if (bool2)
      ViewCompat.postInvalidateOnAnimation((View)this); 
  }
  
  private void requestChildOnScreen(View paramView1, View paramView2) {
    View view;
    boolean bool1;
    if (paramView2 != null) {
      view = paramView2;
    } else {
      view = paramView1;
    } 
    this.mTempRect.set(0, 0, view.getWidth(), view.getHeight());
    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
    if (layoutParams instanceof LayoutParams) {
      LayoutParams layoutParams1 = (LayoutParams)layoutParams;
      if (!layoutParams1.mInsetsDirty) {
        Rect rect1 = layoutParams1.mDecorInsets;
        Rect rect2 = this.mTempRect;
        rect2.left -= rect1.left;
        rect2 = this.mTempRect;
        rect2.right += rect1.right;
        rect2 = this.mTempRect;
        rect2.top -= rect1.top;
        rect2 = this.mTempRect;
        rect2.bottom += rect1.bottom;
      } 
    } 
    if (paramView2 != null) {
      offsetDescendantRectToMyCoords(paramView2, this.mTempRect);
      offsetRectIntoDescendantCoords(paramView1, this.mTempRect);
    } 
    LayoutManager layoutManager = this.mLayout;
    Rect rect = this.mTempRect;
    boolean bool = this.mFirstLayoutComplete;
    if (paramView2 == null) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    layoutManager.requestChildRectangleOnScreen(this, paramView1, rect, bool ^ true, bool1);
  }
  
  private void resetFocusInfo() {
    this.mState.mFocusedItemId = -1L;
    this.mState.mFocusedItemPosition = -1;
    this.mState.mFocusedSubChildId = -1;
  }
  
  private void resetTouch() {
    VelocityTracker velocityTracker = this.mVelocityTracker;
    if (velocityTracker != null)
      velocityTracker.clear(); 
    stopNestedScroll(0);
    releaseGlows();
  }
  
  private void saveFocusInfo() {
    ViewHolder viewHolder2;
    boolean bool = this.mPreserveFocusAfterLayout;
    ViewHolder viewHolder1 = null;
    if (bool && hasFocus() && this.mAdapter != null) {
      viewHolder2 = (ViewHolder)getFocusedChild();
    } else {
      viewHolder2 = null;
    } 
    if (viewHolder2 == null) {
      viewHolder2 = viewHolder1;
    } else {
      viewHolder2 = findContainingViewHolder((View)viewHolder2);
    } 
    if (viewHolder2 == null) {
      resetFocusInfo();
    } else {
      long l;
      int i;
      State state = this.mState;
      if (this.mAdapter.hasStableIds()) {
        l = viewHolder2.getItemId();
      } else {
        l = -1L;
      } 
      state.mFocusedItemId = l;
      state = this.mState;
      if (this.mDataSetHasChangedAfterLayout) {
        i = -1;
      } else if (viewHolder2.isRemoved()) {
        i = viewHolder2.mOldPosition;
      } else {
        i = viewHolder2.getAdapterPosition();
      } 
      state.mFocusedItemPosition = i;
      this.mState.mFocusedSubChildId = getDeepestFocusedViewWithId(viewHolder2.itemView);
    } 
  }
  
  private void setAdapterInternal(Adapter paramAdapter, boolean paramBoolean1, boolean paramBoolean2) {
    Adapter adapter = this.mAdapter;
    if (adapter != null) {
      adapter.unregisterAdapterDataObserver(this.mObserver);
      this.mAdapter.onDetachedFromRecyclerView(this);
    } 
    if (!paramBoolean1 || paramBoolean2)
      removeAndRecycleViews(); 
    this.mAdapterHelper.reset();
    adapter = this.mAdapter;
    this.mAdapter = paramAdapter;
    if (paramAdapter != null) {
      paramAdapter.registerAdapterDataObserver(this.mObserver);
      paramAdapter.onAttachedToRecyclerView(this);
    } 
    LayoutManager layoutManager = this.mLayout;
    if (layoutManager != null)
      layoutManager.onAdapterChanged(adapter, this.mAdapter); 
    this.mRecycler.onAdapterChanged(adapter, this.mAdapter, paramBoolean1);
    this.mState.mStructureChanged = true;
  }
  
  private void stopScrollersInternal() {
    this.mViewFlinger.stop();
    LayoutManager layoutManager = this.mLayout;
    if (layoutManager != null)
      layoutManager.stopSmoothScroller(); 
  }
  
  void absorbGlows(int paramInt1, int paramInt2) {
    if (paramInt1 < 0) {
      ensureLeftGlow();
      this.mLeftGlow.onAbsorb(-paramInt1);
    } else if (paramInt1 > 0) {
      ensureRightGlow();
      this.mRightGlow.onAbsorb(paramInt1);
    } 
    if (paramInt2 < 0) {
      ensureTopGlow();
      this.mTopGlow.onAbsorb(-paramInt2);
    } else if (paramInt2 > 0) {
      ensureBottomGlow();
      this.mBottomGlow.onAbsorb(paramInt2);
    } 
    if (paramInt1 != 0 || paramInt2 != 0)
      ViewCompat.postInvalidateOnAnimation((View)this); 
  }
  
  public void addFocusables(ArrayList<View> paramArrayList, int paramInt1, int paramInt2) {
    LayoutManager layoutManager = this.mLayout;
    if (layoutManager == null || !layoutManager.onAddFocusables(this, paramArrayList, paramInt1, paramInt2))
      super.addFocusables(paramArrayList, paramInt1, paramInt2); 
  }
  
  public void addItemDecoration(ItemDecoration paramItemDecoration) {
    addItemDecoration(paramItemDecoration, -1);
  }
  
  public void addItemDecoration(ItemDecoration paramItemDecoration, int paramInt) {
    LayoutManager layoutManager = this.mLayout;
    if (layoutManager != null)
      layoutManager.assertNotInLayoutOrScroll("Cannot add item decoration during a scroll  or layout"); 
    if (this.mItemDecorations.isEmpty())
      setWillNotDraw(false); 
    if (paramInt < 0) {
      this.mItemDecorations.add(paramItemDecoration);
    } else {
      this.mItemDecorations.add(paramInt, paramItemDecoration);
    } 
    markItemDecorInsetsDirty();
    requestLayout();
  }
  
  public void addOnChildAttachStateChangeListener(OnChildAttachStateChangeListener paramOnChildAttachStateChangeListener) {
    if (this.mOnChildAttachStateListeners == null)
      this.mOnChildAttachStateListeners = new ArrayList<OnChildAttachStateChangeListener>(); 
    this.mOnChildAttachStateListeners.add(paramOnChildAttachStateChangeListener);
  }
  
  public void addOnItemTouchListener(OnItemTouchListener paramOnItemTouchListener) {
    this.mOnItemTouchListeners.add(paramOnItemTouchListener);
  }
  
  public void addOnScrollListener(OnScrollListener paramOnScrollListener) {
    if (this.mScrollListeners == null)
      this.mScrollListeners = new ArrayList<OnScrollListener>(); 
    this.mScrollListeners.add(paramOnScrollListener);
  }
  
  void animateAppearance(ViewHolder paramViewHolder, ItemAnimator.ItemHolderInfo paramItemHolderInfo1, ItemAnimator.ItemHolderInfo paramItemHolderInfo2) {
    paramViewHolder.setIsRecyclable(false);
    if (this.mItemAnimator.animateAppearance(paramViewHolder, paramItemHolderInfo1, paramItemHolderInfo2))
      postAnimationRunner(); 
  }
  
  void animateDisappearance(ViewHolder paramViewHolder, ItemAnimator.ItemHolderInfo paramItemHolderInfo1, ItemAnimator.ItemHolderInfo paramItemHolderInfo2) {
    addAnimatingView(paramViewHolder);
    paramViewHolder.setIsRecyclable(false);
    if (this.mItemAnimator.animateDisappearance(paramViewHolder, paramItemHolderInfo1, paramItemHolderInfo2))
      postAnimationRunner(); 
  }
  
  void assertInLayoutOrScroll(String paramString) {
    if (!isComputingLayout()) {
      StringBuilder stringBuilder1;
      if (paramString == null) {
        stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Cannot call this method unless RecyclerView is computing a layout or scrolling");
        stringBuilder1.append(exceptionLabel());
        throw new IllegalStateException(stringBuilder1.toString());
      } 
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append((String)stringBuilder1);
      stringBuilder2.append(exceptionLabel());
      throw new IllegalStateException(stringBuilder2.toString());
    } 
  }
  
  void assertNotInLayoutOrScroll(String paramString) {
    if (isComputingLayout()) {
      StringBuilder stringBuilder;
      if (paramString == null) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot call this method while RecyclerView is computing a layout or scrolling");
        stringBuilder.append(exceptionLabel());
        throw new IllegalStateException(stringBuilder.toString());
      } 
      throw new IllegalStateException(stringBuilder);
    } 
    if (this.mDispatchScrollCounter > 0) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("");
      stringBuilder.append(exceptionLabel());
      Log.w("RecyclerView", "Cannot call this method in a scroll callback. Scroll callbacks mightbe run during a measure & layout pass where you cannot change theRecyclerView data. Any method call that might change the structureof the RecyclerView or the adapter contents should be postponed tothe next frame.", new IllegalStateException(stringBuilder.toString()));
    } 
  }
  
  boolean canReuseUpdatedViewHolder(ViewHolder paramViewHolder) {
    ItemAnimator itemAnimator = this.mItemAnimator;
    return (itemAnimator == null || itemAnimator.canReuseUpdatedViewHolder(paramViewHolder, paramViewHolder.getUnmodifiedPayloads()));
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    boolean bool;
    if (paramLayoutParams instanceof LayoutParams && this.mLayout.checkLayoutParams((LayoutParams)paramLayoutParams)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  void clearOldPositions() {
    int i = this.mChildHelper.getUnfilteredChildCount();
    for (byte b = 0; b < i; b++) {
      ViewHolder viewHolder = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(b));
      if (!viewHolder.shouldIgnore())
        viewHolder.clearOldPosition(); 
    } 
    this.mRecycler.clearOldPositions();
  }
  
  public void clearOnChildAttachStateChangeListeners() {
    List<OnChildAttachStateChangeListener> list = this.mOnChildAttachStateListeners;
    if (list != null)
      list.clear(); 
  }
  
  public void clearOnScrollListeners() {
    List<OnScrollListener> list = this.mScrollListeners;
    if (list != null)
      list.clear(); 
  }
  
  public int computeHorizontalScrollExtent() {
    LayoutManager layoutManager = this.mLayout;
    int i = 0;
    if (layoutManager == null)
      return 0; 
    if (layoutManager.canScrollHorizontally())
      i = this.mLayout.computeHorizontalScrollExtent(this.mState); 
    return i;
  }
  
  public int computeHorizontalScrollOffset() {
    LayoutManager layoutManager = this.mLayout;
    int i = 0;
    if (layoutManager == null)
      return 0; 
    if (layoutManager.canScrollHorizontally())
      i = this.mLayout.computeHorizontalScrollOffset(this.mState); 
    return i;
  }
  
  public int computeHorizontalScrollRange() {
    LayoutManager layoutManager = this.mLayout;
    int i = 0;
    if (layoutManager == null)
      return 0; 
    if (layoutManager.canScrollHorizontally())
      i = this.mLayout.computeHorizontalScrollRange(this.mState); 
    return i;
  }
  
  public int computeVerticalScrollExtent() {
    LayoutManager layoutManager = this.mLayout;
    int i = 0;
    if (layoutManager == null)
      return 0; 
    if (layoutManager.canScrollVertically())
      i = this.mLayout.computeVerticalScrollExtent(this.mState); 
    return i;
  }
  
  public int computeVerticalScrollOffset() {
    LayoutManager layoutManager = this.mLayout;
    int i = 0;
    if (layoutManager == null)
      return 0; 
    if (layoutManager.canScrollVertically())
      i = this.mLayout.computeVerticalScrollOffset(this.mState); 
    return i;
  }
  
  public int computeVerticalScrollRange() {
    LayoutManager layoutManager = this.mLayout;
    int i = 0;
    if (layoutManager == null)
      return 0; 
    if (layoutManager.canScrollVertically())
      i = this.mLayout.computeVerticalScrollRange(this.mState); 
    return i;
  }
  
  void considerReleasingGlowsOnScroll(int paramInt1, int paramInt2) {
    EdgeEffect edgeEffect = this.mLeftGlow;
    if (edgeEffect != null && !edgeEffect.isFinished() && paramInt1 > 0) {
      this.mLeftGlow.onRelease();
      bool1 = this.mLeftGlow.isFinished();
    } else {
      bool1 = false;
    } 
    edgeEffect = this.mRightGlow;
    boolean bool2 = bool1;
    if (edgeEffect != null) {
      bool2 = bool1;
      if (!edgeEffect.isFinished()) {
        bool2 = bool1;
        if (paramInt1 < 0) {
          this.mRightGlow.onRelease();
          bool2 = bool1 | this.mRightGlow.isFinished();
        } 
      } 
    } 
    edgeEffect = this.mTopGlow;
    boolean bool1 = bool2;
    if (edgeEffect != null) {
      bool1 = bool2;
      if (!edgeEffect.isFinished()) {
        bool1 = bool2;
        if (paramInt2 > 0) {
          this.mTopGlow.onRelease();
          bool1 = bool2 | this.mTopGlow.isFinished();
        } 
      } 
    } 
    edgeEffect = this.mBottomGlow;
    bool2 = bool1;
    if (edgeEffect != null) {
      bool2 = bool1;
      if (!edgeEffect.isFinished()) {
        bool2 = bool1;
        if (paramInt2 < 0) {
          this.mBottomGlow.onRelease();
          bool2 = bool1 | this.mBottomGlow.isFinished();
        } 
      } 
    } 
    if (bool2)
      ViewCompat.postInvalidateOnAnimation((View)this); 
  }
  
  void consumePendingUpdateOperations() {
    if (!this.mFirstLayoutComplete || this.mDataSetHasChangedAfterLayout) {
      TraceCompat.beginSection("RV FullInvalidate");
      dispatchLayout();
      TraceCompat.endSection();
      return;
    } 
    if (!this.mAdapterHelper.hasPendingUpdates())
      return; 
    if (this.mAdapterHelper.hasAnyUpdateTypes(4) && !this.mAdapterHelper.hasAnyUpdateTypes(11)) {
      TraceCompat.beginSection("RV PartialInvalidate");
      startInterceptRequestLayout();
      onEnterLayoutOrScroll();
      this.mAdapterHelper.preProcess();
      if (!this.mLayoutWasDefered)
        if (hasUpdatedView()) {
          dispatchLayout();
        } else {
          this.mAdapterHelper.consumePostponedUpdates();
        }  
      stopInterceptRequestLayout(true);
      onExitLayoutOrScroll();
      TraceCompat.endSection();
    } else if (this.mAdapterHelper.hasPendingUpdates()) {
      TraceCompat.beginSection("RV FullInvalidate");
      dispatchLayout();
      TraceCompat.endSection();
    } 
  }
  
  void defaultOnMeasure(int paramInt1, int paramInt2) {
    setMeasuredDimension(LayoutManager.chooseSize(paramInt1, getPaddingLeft() + getPaddingRight(), ViewCompat.getMinimumWidth((View)this)), LayoutManager.chooseSize(paramInt2, getPaddingTop() + getPaddingBottom(), ViewCompat.getMinimumHeight((View)this)));
  }
  
  void dispatchChildAttached(View paramView) {
    ViewHolder viewHolder = getChildViewHolderInt(paramView);
    onChildAttachedToWindow(paramView);
    Adapter<ViewHolder> adapter = this.mAdapter;
    if (adapter != null && viewHolder != null)
      adapter.onViewAttachedToWindow(viewHolder); 
    List<OnChildAttachStateChangeListener> list = this.mOnChildAttachStateListeners;
    if (list != null)
      for (int i = list.size() - 1; i >= 0; i--)
        ((OnChildAttachStateChangeListener)this.mOnChildAttachStateListeners.get(i)).onChildViewAttachedToWindow(paramView);  
  }
  
  void dispatchChildDetached(View paramView) {
    ViewHolder viewHolder = getChildViewHolderInt(paramView);
    onChildDetachedFromWindow(paramView);
    Adapter<ViewHolder> adapter = this.mAdapter;
    if (adapter != null && viewHolder != null)
      adapter.onViewDetachedFromWindow(viewHolder); 
    List<OnChildAttachStateChangeListener> list = this.mOnChildAttachStateListeners;
    if (list != null)
      for (int i = list.size() - 1; i >= 0; i--)
        ((OnChildAttachStateChangeListener)this.mOnChildAttachStateListeners.get(i)).onChildViewDetachedFromWindow(paramView);  
  }
  
  void dispatchLayout() {
    if (this.mAdapter == null) {
      Log.e("RecyclerView", "No adapter attached; skipping layout");
      return;
    } 
    if (this.mLayout == null) {
      Log.e("RecyclerView", "No layout manager attached; skipping layout");
      return;
    } 
    this.mState.mIsMeasuring = false;
    if (this.mState.mLayoutStep == 1) {
      dispatchLayoutStep1();
      this.mLayout.setExactMeasureSpecsFrom(this);
      dispatchLayoutStep2();
    } else if (this.mAdapterHelper.hasUpdates() || this.mLayout.getWidth() != getWidth() || this.mLayout.getHeight() != getHeight()) {
      this.mLayout.setExactMeasureSpecsFrom(this);
      dispatchLayoutStep2();
    } else {
      this.mLayout.setExactMeasureSpecsFrom(this);
    } 
    dispatchLayoutStep3();
  }
  
  public boolean dispatchNestedFling(float paramFloat1, float paramFloat2, boolean paramBoolean) {
    return getScrollingChildHelper().dispatchNestedFling(paramFloat1, paramFloat2, paramBoolean);
  }
  
  public boolean dispatchNestedPreFling(float paramFloat1, float paramFloat2) {
    return getScrollingChildHelper().dispatchNestedPreFling(paramFloat1, paramFloat2);
  }
  
  public boolean dispatchNestedPreScroll(int paramInt1, int paramInt2, int[] paramArrayOfint1, int[] paramArrayOfint2) {
    return getScrollingChildHelper().dispatchNestedPreScroll(paramInt1, paramInt2, paramArrayOfint1, paramArrayOfint2);
  }
  
  public boolean dispatchNestedPreScroll(int paramInt1, int paramInt2, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt3) {
    return getScrollingChildHelper().dispatchNestedPreScroll(paramInt1, paramInt2, paramArrayOfint1, paramArrayOfint2, paramInt3);
  }
  
  public boolean dispatchNestedScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint) {
    return getScrollingChildHelper().dispatchNestedScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfint);
  }
  
  public boolean dispatchNestedScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfint, int paramInt5) {
    return getScrollingChildHelper().dispatchNestedScroll(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfint, paramInt5);
  }
  
  void dispatchOnScrollStateChanged(int paramInt) {
    LayoutManager layoutManager = this.mLayout;
    if (layoutManager != null)
      layoutManager.onScrollStateChanged(paramInt); 
    onScrollStateChanged(paramInt);
    OnScrollListener onScrollListener = this.mScrollListener;
    if (onScrollListener != null)
      onScrollListener.onScrollStateChanged(this, paramInt); 
    List<OnScrollListener> list = this.mScrollListeners;
    if (list != null)
      for (int i = list.size() - 1; i >= 0; i--)
        ((OnScrollListener)this.mScrollListeners.get(i)).onScrollStateChanged(this, paramInt);  
  }
  
  void dispatchOnScrolled(int paramInt1, int paramInt2) {
    this.mDispatchScrollCounter++;
    int i = getScrollX();
    int j = getScrollY();
    onScrollChanged(i, j, i, j);
    onScrolled(paramInt1, paramInt2);
    OnScrollListener onScrollListener = this.mScrollListener;
    if (onScrollListener != null)
      onScrollListener.onScrolled(this, paramInt1, paramInt2); 
    List<OnScrollListener> list = this.mScrollListeners;
    if (list != null)
      for (i = list.size() - 1; i >= 0; i--)
        ((OnScrollListener)this.mScrollListeners.get(i)).onScrolled(this, paramInt1, paramInt2);  
    this.mDispatchScrollCounter--;
  }
  
  void dispatchPendingImportantForAccessibilityChanges() {
    for (int i = this.mPendingAccessibilityImportanceChange.size() - 1; i >= 0; i--) {
      ViewHolder viewHolder = this.mPendingAccessibilityImportanceChange.get(i);
      if (viewHolder.itemView.getParent() == this && !viewHolder.shouldIgnore()) {
        int j = viewHolder.mPendingAccessibilityState;
        if (j != -1) {
          ViewCompat.setImportantForAccessibility(viewHolder.itemView, j);
          viewHolder.mPendingAccessibilityState = -1;
        } 
      } 
    } 
    this.mPendingAccessibilityImportanceChange.clear();
  }
  
  protected void dispatchRestoreInstanceState(SparseArray<Parcelable> paramSparseArray) {
    dispatchThawSelfOnly(paramSparseArray);
  }
  
  protected void dispatchSaveInstanceState(SparseArray<Parcelable> paramSparseArray) {
    dispatchFreezeSelfOnly(paramSparseArray);
  }
  
  public void draw(Canvas paramCanvas) {
    super.draw(paramCanvas);
    int i = this.mItemDecorations.size();
    boolean bool1 = false;
    int j;
    for (j = 0; j < i; j++)
      ((ItemDecoration)this.mItemDecorations.get(j)).onDrawOver(paramCanvas, this, this.mState); 
    EdgeEffect edgeEffect = this.mLeftGlow;
    boolean bool2 = true;
    if (edgeEffect != null && !edgeEffect.isFinished()) {
      int k = paramCanvas.save();
      if (this.mClipToPadding) {
        j = getPaddingBottom();
      } else {
        j = 0;
      } 
      paramCanvas.rotate(270.0F);
      paramCanvas.translate((-getHeight() + j), 0.0F);
      edgeEffect = this.mLeftGlow;
      if (edgeEffect != null && edgeEffect.draw(paramCanvas)) {
        i = 1;
      } else {
        i = 0;
      } 
      paramCanvas.restoreToCount(k);
    } else {
      i = 0;
    } 
    edgeEffect = this.mTopGlow;
    j = i;
    if (edgeEffect != null) {
      j = i;
      if (!edgeEffect.isFinished()) {
        int k = paramCanvas.save();
        if (this.mClipToPadding)
          paramCanvas.translate(getPaddingLeft(), getPaddingTop()); 
        edgeEffect = this.mTopGlow;
        if (edgeEffect != null && edgeEffect.draw(paramCanvas)) {
          j = 1;
        } else {
          j = 0;
        } 
        j = i | j;
        paramCanvas.restoreToCount(k);
      } 
    } 
    edgeEffect = this.mRightGlow;
    i = j;
    if (edgeEffect != null) {
      i = j;
      if (!edgeEffect.isFinished()) {
        int k = paramCanvas.save();
        int m = getWidth();
        if (this.mClipToPadding) {
          i = getPaddingTop();
        } else {
          i = 0;
        } 
        paramCanvas.rotate(90.0F);
        paramCanvas.translate(-i, -m);
        edgeEffect = this.mRightGlow;
        if (edgeEffect != null && edgeEffect.draw(paramCanvas)) {
          i = 1;
        } else {
          i = 0;
        } 
        i = j | i;
        paramCanvas.restoreToCount(k);
      } 
    } 
    edgeEffect = this.mBottomGlow;
    j = i;
    if (edgeEffect != null) {
      j = i;
      if (!edgeEffect.isFinished()) {
        int k = paramCanvas.save();
        paramCanvas.rotate(180.0F);
        if (this.mClipToPadding) {
          paramCanvas.translate((-getWidth() + getPaddingRight()), (-getHeight() + getPaddingBottom()));
        } else {
          paramCanvas.translate(-getWidth(), -getHeight());
        } 
        edgeEffect = this.mBottomGlow;
        j = bool1;
        if (edgeEffect != null) {
          j = bool1;
          if (edgeEffect.draw(paramCanvas))
            j = 1; 
        } 
        j = i | j;
        paramCanvas.restoreToCount(k);
      } 
    } 
    if (j == 0 && this.mItemAnimator != null && this.mItemDecorations.size() > 0 && this.mItemAnimator.isRunning())
      j = bool2; 
    if (j != 0)
      ViewCompat.postInvalidateOnAnimation((View)this); 
  }
  
  public boolean drawChild(Canvas paramCanvas, View paramView, long paramLong) {
    return super.drawChild(paramCanvas, paramView, paramLong);
  }
  
  void ensureBottomGlow() {
    if (this.mBottomGlow != null)
      return; 
    EdgeEffect edgeEffect = this.mEdgeEffectFactory.createEdgeEffect(this, 3);
    this.mBottomGlow = edgeEffect;
    if (this.mClipToPadding) {
      edgeEffect.setSize(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), getMeasuredHeight() - getPaddingTop() - getPaddingBottom());
    } else {
      edgeEffect.setSize(getMeasuredWidth(), getMeasuredHeight());
    } 
  }
  
  void ensureLeftGlow() {
    if (this.mLeftGlow != null)
      return; 
    EdgeEffect edgeEffect = this.mEdgeEffectFactory.createEdgeEffect(this, 0);
    this.mLeftGlow = edgeEffect;
    if (this.mClipToPadding) {
      edgeEffect.setSize(getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), getMeasuredWidth() - getPaddingLeft() - getPaddingRight());
    } else {
      edgeEffect.setSize(getMeasuredHeight(), getMeasuredWidth());
    } 
  }
  
  void ensureRightGlow() {
    if (this.mRightGlow != null)
      return; 
    EdgeEffect edgeEffect = this.mEdgeEffectFactory.createEdgeEffect(this, 2);
    this.mRightGlow = edgeEffect;
    if (this.mClipToPadding) {
      edgeEffect.setSize(getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), getMeasuredWidth() - getPaddingLeft() - getPaddingRight());
    } else {
      edgeEffect.setSize(getMeasuredHeight(), getMeasuredWidth());
    } 
  }
  
  void ensureTopGlow() {
    if (this.mTopGlow != null)
      return; 
    EdgeEffect edgeEffect = this.mEdgeEffectFactory.createEdgeEffect(this, 1);
    this.mTopGlow = edgeEffect;
    if (this.mClipToPadding) {
      edgeEffect.setSize(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), getMeasuredHeight() - getPaddingTop() - getPaddingBottom());
    } else {
      edgeEffect.setSize(getMeasuredWidth(), getMeasuredHeight());
    } 
  }
  
  String exceptionLabel() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(" ");
    stringBuilder.append(toString());
    stringBuilder.append(", adapter:");
    stringBuilder.append(this.mAdapter);
    stringBuilder.append(", layout:");
    stringBuilder.append(this.mLayout);
    stringBuilder.append(", context:");
    stringBuilder.append(getContext());
    return stringBuilder.toString();
  }
  
  final void fillRemainingScrollValues(State paramState) {
    if (getScrollState() == 2) {
      OverScroller overScroller = this.mViewFlinger.mScroller;
      paramState.mRemainingScrollHorizontal = overScroller.getFinalX() - overScroller.getCurrX();
      paramState.mRemainingScrollVertical = overScroller.getFinalY() - overScroller.getCurrY();
    } else {
      paramState.mRemainingScrollHorizontal = 0;
      paramState.mRemainingScrollVertical = 0;
    } 
  }
  
  public View findChildViewUnder(float paramFloat1, float paramFloat2) {
    for (int i = this.mChildHelper.getChildCount() - 1; i >= 0; i--) {
      View view = this.mChildHelper.getChildAt(i);
      float f1 = view.getTranslationX();
      float f2 = view.getTranslationY();
      if (paramFloat1 >= view.getLeft() + f1 && paramFloat1 <= view.getRight() + f1 && paramFloat2 >= view.getTop() + f2 && paramFloat2 <= view.getBottom() + f2)
        return view; 
    } 
    return null;
  }
  
  public View findContainingItemView(View paramView) {
    ViewParent viewParent;
    for (viewParent = paramView.getParent(); viewParent != null && viewParent != this && viewParent instanceof View; viewParent = paramView.getParent())
      paramView = (View)viewParent; 
    if (viewParent != this)
      paramView = null; 
    return paramView;
  }
  
  public ViewHolder findContainingViewHolder(View paramView) {
    ViewHolder viewHolder;
    paramView = findContainingItemView(paramView);
    if (paramView == null) {
      paramView = null;
    } else {
      viewHolder = getChildViewHolder(paramView);
    } 
    return viewHolder;
  }
  
  public ViewHolder findViewHolderForAdapterPosition(int paramInt) {
    boolean bool = this.mDataSetHasChangedAfterLayout;
    ViewHolder viewHolder = null;
    if (bool)
      return null; 
    int i = this.mChildHelper.getUnfilteredChildCount();
    byte b = 0;
    while (b < i) {
      ViewHolder viewHolder1 = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(b));
      ViewHolder viewHolder2 = viewHolder;
      if (viewHolder1 != null) {
        viewHolder2 = viewHolder;
        if (!viewHolder1.isRemoved()) {
          viewHolder2 = viewHolder;
          if (getAdapterPositionFor(viewHolder1) == paramInt)
            if (this.mChildHelper.isHidden(viewHolder1.itemView)) {
              viewHolder2 = viewHolder1;
            } else {
              return viewHolder1;
            }  
        } 
      } 
      b++;
      viewHolder = viewHolder2;
    } 
    return viewHolder;
  }
  
  public ViewHolder findViewHolderForItemId(long paramLong) {
    Adapter adapter = this.mAdapter;
    ViewHolder viewHolder1 = null;
    ViewHolder viewHolder2 = null;
    ViewHolder viewHolder3 = viewHolder1;
    if (adapter != null)
      if (!adapter.hasStableIds()) {
        viewHolder3 = viewHolder1;
      } else {
        int i = this.mChildHelper.getUnfilteredChildCount();
        byte b = 0;
        while (true) {
          viewHolder3 = viewHolder2;
          if (b < i) {
            viewHolder1 = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(b));
            viewHolder3 = viewHolder2;
            if (viewHolder1 != null) {
              viewHolder3 = viewHolder2;
              if (!viewHolder1.isRemoved()) {
                viewHolder3 = viewHolder2;
                if (viewHolder1.getItemId() == paramLong)
                  if (this.mChildHelper.isHidden(viewHolder1.itemView)) {
                    viewHolder3 = viewHolder1;
                  } else {
                    return viewHolder1;
                  }  
              } 
            } 
            b++;
            viewHolder2 = viewHolder3;
            continue;
          } 
          break;
        } 
      }  
    return viewHolder3;
  }
  
  public ViewHolder findViewHolderForLayoutPosition(int paramInt) {
    return findViewHolderForPosition(paramInt, false);
  }
  
  @Deprecated
  public ViewHolder findViewHolderForPosition(int paramInt) {
    return findViewHolderForPosition(paramInt, false);
  }
  
  ViewHolder findViewHolderForPosition(int paramInt, boolean paramBoolean) {
    int i = this.mChildHelper.getUnfilteredChildCount();
    Object object = null;
    byte b = 0;
    while (b < i) {
      ViewHolder viewHolder = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(b));
      Object object1 = object;
      if (viewHolder != null) {
        object1 = object;
        if (!viewHolder.isRemoved()) {
          if (paramBoolean) {
            if (viewHolder.mPosition != paramInt) {
              object1 = object;
              continue;
            } 
          } else if (viewHolder.getLayoutPosition() != paramInt) {
            object1 = object;
            continue;
          } 
          if (this.mChildHelper.isHidden(viewHolder.itemView)) {
            object1 = viewHolder;
          } else {
            return viewHolder;
          } 
        } 
      } 
      continue;
      b++;
      object = SYNTHETIC_LOCAL_VARIABLE_7;
    } 
    return (ViewHolder)object;
  }
  
  public boolean fling(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mLayout : Landroid/support/v7/widget/RecyclerView$LayoutManager;
    //   4: astore_3
    //   5: iconst_0
    //   6: istore #4
    //   8: aload_3
    //   9: ifnonnull -> 23
    //   12: ldc 'RecyclerView'
    //   14: ldc_w 'Cannot fling without a LayoutManager set. Call setLayoutManager with a non-null argument.'
    //   17: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)I
    //   20: pop
    //   21: iconst_0
    //   22: ireturn
    //   23: aload_0
    //   24: getfield mLayoutFrozen : Z
    //   27: ifeq -> 32
    //   30: iconst_0
    //   31: ireturn
    //   32: aload_3
    //   33: invokevirtual canScrollHorizontally : ()Z
    //   36: istore #5
    //   38: aload_0
    //   39: getfield mLayout : Landroid/support/v7/widget/RecyclerView$LayoutManager;
    //   42: invokevirtual canScrollVertically : ()Z
    //   45: istore #6
    //   47: iload #5
    //   49: ifeq -> 66
    //   52: iload_1
    //   53: istore #7
    //   55: iload_1
    //   56: invokestatic abs : (I)I
    //   59: aload_0
    //   60: getfield mMinFlingVelocity : I
    //   63: if_icmpge -> 69
    //   66: iconst_0
    //   67: istore #7
    //   69: iload #6
    //   71: ifeq -> 88
    //   74: iload_2
    //   75: istore #8
    //   77: iload_2
    //   78: invokestatic abs : (I)I
    //   81: aload_0
    //   82: getfield mMinFlingVelocity : I
    //   85: if_icmpge -> 91
    //   88: iconst_0
    //   89: istore #8
    //   91: iload #7
    //   93: ifne -> 103
    //   96: iload #8
    //   98: ifne -> 103
    //   101: iconst_0
    //   102: ireturn
    //   103: iload #7
    //   105: i2f
    //   106: fstore #9
    //   108: iload #8
    //   110: i2f
    //   111: fstore #10
    //   113: aload_0
    //   114: fload #9
    //   116: fload #10
    //   118: invokevirtual dispatchNestedPreFling : (FF)Z
    //   121: ifne -> 257
    //   124: iload #5
    //   126: ifne -> 143
    //   129: iload #6
    //   131: ifeq -> 137
    //   134: goto -> 143
    //   137: iconst_0
    //   138: istore #11
    //   140: goto -> 146
    //   143: iconst_1
    //   144: istore #11
    //   146: aload_0
    //   147: fload #9
    //   149: fload #10
    //   151: iload #11
    //   153: invokevirtual dispatchNestedFling : (FFZ)Z
    //   156: pop
    //   157: aload_0
    //   158: getfield mOnFlingListener : Landroid/support/v7/widget/RecyclerView$OnFlingListener;
    //   161: astore_3
    //   162: aload_3
    //   163: ifnull -> 179
    //   166: aload_3
    //   167: iload #7
    //   169: iload #8
    //   171: invokevirtual onFling : (II)Z
    //   174: ifeq -> 179
    //   177: iconst_1
    //   178: ireturn
    //   179: iload #11
    //   181: ifeq -> 257
    //   184: iload #4
    //   186: istore_1
    //   187: iload #5
    //   189: ifeq -> 194
    //   192: iconst_1
    //   193: istore_1
    //   194: iload_1
    //   195: istore_2
    //   196: iload #6
    //   198: ifeq -> 205
    //   201: iload_1
    //   202: iconst_2
    //   203: ior
    //   204: istore_2
    //   205: aload_0
    //   206: iload_2
    //   207: iconst_1
    //   208: invokevirtual startNestedScroll : (II)Z
    //   211: pop
    //   212: aload_0
    //   213: getfield mMaxFlingVelocity : I
    //   216: istore_1
    //   217: iload_1
    //   218: ineg
    //   219: iload #7
    //   221: iload_1
    //   222: invokestatic min : (II)I
    //   225: invokestatic max : (II)I
    //   228: istore_1
    //   229: aload_0
    //   230: getfield mMaxFlingVelocity : I
    //   233: istore_2
    //   234: iload_2
    //   235: ineg
    //   236: iload #8
    //   238: iload_2
    //   239: invokestatic min : (II)I
    //   242: invokestatic max : (II)I
    //   245: istore_2
    //   246: aload_0
    //   247: getfield mViewFlinger : Landroid/support/v7/widget/RecyclerView$ViewFlinger;
    //   250: iload_1
    //   251: iload_2
    //   252: invokevirtual fling : (II)V
    //   255: iconst_1
    //   256: ireturn
    //   257: iconst_0
    //   258: ireturn
  }
  
  public View focusSearch(View paramView, int paramInt) {
    View view1;
    int i;
    View view2 = this.mLayout.onInterceptFocusSearch(paramView, paramInt);
    if (view2 != null)
      return view2; 
    Adapter adapter = this.mAdapter;
    boolean bool = true;
    if (adapter != null && this.mLayout != null && !isComputingLayout() && !this.mLayoutFrozen) {
      i = 1;
    } else {
      i = 0;
    } 
    FocusFinder focusFinder = FocusFinder.getInstance();
    if (i && (paramInt == 2 || paramInt == 1)) {
      if (this.mLayout.canScrollVertically()) {
        byte b1;
        byte b2;
        if (paramInt == 2) {
          b1 = 130;
        } else {
          b1 = 33;
        } 
        if (focusFinder.findNextFocus(this, paramView, b1) == null) {
          b2 = 1;
        } else {
          b2 = 0;
        } 
        i = b2;
        if (FORCE_ABS_FOCUS_SEARCH_DIRECTION) {
          paramInt = b1;
          i = b2;
        } 
      } else {
        i = 0;
      } 
      int k = i;
      int j = paramInt;
      if (!i) {
        k = i;
        j = paramInt;
        if (this.mLayout.canScrollHorizontally()) {
          if (this.mLayout.getLayoutDirection() == 1) {
            i = 1;
          } else {
            i = 0;
          } 
          if (paramInt == 2) {
            j = 1;
          } else {
            j = 0;
          } 
          if ((i ^ j) != 0) {
            i = 66;
          } else {
            i = 17;
          } 
          if (focusFinder.findNextFocus(this, paramView, i) == null) {
            j = bool;
          } else {
            j = 0;
          } 
          if (FORCE_ABS_FOCUS_SEARCH_DIRECTION)
            paramInt = i; 
          k = j;
          j = paramInt;
        } 
      } 
      if (k != 0) {
        consumePendingUpdateOperations();
        if (findContainingItemView(paramView) == null)
          return null; 
        startInterceptRequestLayout();
        this.mLayout.onFocusSearchFailed(paramView, j, this.mRecycler, this.mState);
        stopInterceptRequestLayout(false);
      } 
      view1 = focusFinder.findNextFocus(this, paramView, j);
      paramInt = j;
    } else {
      view1 = view1.findNextFocus(this, paramView, paramInt);
      if (view1 == null && i != 0) {
        consumePendingUpdateOperations();
        if (findContainingItemView(paramView) == null)
          return null; 
        startInterceptRequestLayout();
        view1 = this.mLayout.onFocusSearchFailed(paramView, paramInt, this.mRecycler, this.mState);
        stopInterceptRequestLayout(false);
      } 
    } 
    if (view1 != null && !view1.hasFocusable()) {
      if (getFocusedChild() == null)
        return super.focusSearch(paramView, paramInt); 
      requestChildOnScreen(view1, (View)null);
      return paramView;
    } 
    if (!isPreferredNextFocus(paramView, view1, paramInt))
      view1 = super.focusSearch(paramView, paramInt); 
    return view1;
  }
  
  protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
    LayoutManager layoutManager = this.mLayout;
    if (layoutManager != null)
      return (ViewGroup.LayoutParams)layoutManager.generateDefaultLayoutParams(); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("RecyclerView has no LayoutManager");
    stringBuilder.append(exceptionLabel());
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet) {
    LayoutManager layoutManager = this.mLayout;
    if (layoutManager != null)
      return (ViewGroup.LayoutParams)layoutManager.generateLayoutParams(getContext(), paramAttributeSet); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("RecyclerView has no LayoutManager");
    stringBuilder.append(exceptionLabel());
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams) {
    LayoutManager layoutManager = this.mLayout;
    if (layoutManager != null)
      return (ViewGroup.LayoutParams)layoutManager.generateLayoutParams(paramLayoutParams); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("RecyclerView has no LayoutManager");
    stringBuilder.append(exceptionLabel());
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public Adapter getAdapter() {
    return this.mAdapter;
  }
  
  int getAdapterPositionFor(ViewHolder paramViewHolder) {
    return (paramViewHolder.hasAnyOfTheFlags(524) || !paramViewHolder.isBound()) ? -1 : this.mAdapterHelper.applyPendingUpdatesToPosition(paramViewHolder.mPosition);
  }
  
  public int getBaseline() {
    LayoutManager layoutManager = this.mLayout;
    return (layoutManager != null) ? layoutManager.getBaseline() : super.getBaseline();
  }
  
  long getChangedHolderKey(ViewHolder paramViewHolder) {
    long l;
    if (this.mAdapter.hasStableIds()) {
      l = paramViewHolder.getItemId();
    } else {
      l = paramViewHolder.mPosition;
    } 
    return l;
  }
  
  public int getChildAdapterPosition(View paramView) {
    byte b;
    ViewHolder viewHolder = getChildViewHolderInt(paramView);
    if (viewHolder != null) {
      b = viewHolder.getAdapterPosition();
    } else {
      b = -1;
    } 
    return b;
  }
  
  protected int getChildDrawingOrder(int paramInt1, int paramInt2) {
    ChildDrawingOrderCallback childDrawingOrderCallback = this.mChildDrawingOrderCallback;
    return (childDrawingOrderCallback == null) ? super.getChildDrawingOrder(paramInt1, paramInt2) : childDrawingOrderCallback.onGetChildDrawingOrder(paramInt1, paramInt2);
  }
  
  public long getChildItemId(View paramView) {
    Adapter adapter = this.mAdapter;
    long l1 = -1L;
    long l2 = l1;
    if (adapter != null)
      if (!adapter.hasStableIds()) {
        l2 = l1;
      } else {
        ViewHolder viewHolder = getChildViewHolderInt(paramView);
        l2 = l1;
        if (viewHolder != null)
          l2 = viewHolder.getItemId(); 
      }  
    return l2;
  }
  
  public int getChildLayoutPosition(View paramView) {
    byte b;
    ViewHolder viewHolder = getChildViewHolderInt(paramView);
    if (viewHolder != null) {
      b = viewHolder.getLayoutPosition();
    } else {
      b = -1;
    } 
    return b;
  }
  
  @Deprecated
  public int getChildPosition(View paramView) {
    return getChildAdapterPosition(paramView);
  }
  
  public ViewHolder getChildViewHolder(View paramView) {
    ViewParent viewParent = paramView.getParent();
    if (viewParent == null || viewParent == this)
      return getChildViewHolderInt(paramView); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("View ");
    stringBuilder.append(paramView);
    stringBuilder.append(" is not a direct child of ");
    stringBuilder.append(this);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public boolean getClipToPadding() {
    return this.mClipToPadding;
  }
  
  public RecyclerViewAccessibilityDelegate getCompatAccessibilityDelegate() {
    return this.mAccessibilityDelegate;
  }
  
  public void getDecoratedBoundsWithMargins(View paramView, Rect paramRect) {
    getDecoratedBoundsWithMarginsInt(paramView, paramRect);
  }
  
  public EdgeEffectFactory getEdgeEffectFactory() {
    return this.mEdgeEffectFactory;
  }
  
  public ItemAnimator getItemAnimator() {
    return this.mItemAnimator;
  }
  
  Rect getItemDecorInsetsForChild(View paramView) {
    LayoutParams layoutParams = (LayoutParams)paramView.getLayoutParams();
    if (!layoutParams.mInsetsDirty)
      return layoutParams.mDecorInsets; 
    if (this.mState.isPreLayout() && (layoutParams.isItemChanged() || layoutParams.isViewInvalid()))
      return layoutParams.mDecorInsets; 
    Rect rect = layoutParams.mDecorInsets;
    rect.set(0, 0, 0, 0);
    int i = this.mItemDecorations.size();
    for (byte b = 0; b < i; b++) {
      this.mTempRect.set(0, 0, 0, 0);
      ((ItemDecoration)this.mItemDecorations.get(b)).getItemOffsets(this.mTempRect, paramView, this, this.mState);
      rect.left += this.mTempRect.left;
      rect.top += this.mTempRect.top;
      rect.right += this.mTempRect.right;
      rect.bottom += this.mTempRect.bottom;
    } 
    layoutParams.mInsetsDirty = false;
    return rect;
  }
  
  public ItemDecoration getItemDecorationAt(int paramInt) {
    int i = getItemDecorationCount();
    if (paramInt >= 0 && paramInt < i)
      return this.mItemDecorations.get(paramInt); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramInt);
    stringBuilder.append(" is an invalid index for size ");
    stringBuilder.append(i);
    throw new IndexOutOfBoundsException(stringBuilder.toString());
  }
  
  public int getItemDecorationCount() {
    return this.mItemDecorations.size();
  }
  
  public LayoutManager getLayoutManager() {
    return this.mLayout;
  }
  
  public int getMaxFlingVelocity() {
    return this.mMaxFlingVelocity;
  }
  
  public int getMinFlingVelocity() {
    return this.mMinFlingVelocity;
  }
  
  long getNanoTime() {
    return ALLOW_THREAD_GAP_WORK ? System.nanoTime() : 0L;
  }
  
  public OnFlingListener getOnFlingListener() {
    return this.mOnFlingListener;
  }
  
  public boolean getPreserveFocusAfterLayout() {
    return this.mPreserveFocusAfterLayout;
  }
  
  public RecycledViewPool getRecycledViewPool() {
    return this.mRecycler.getRecycledViewPool();
  }
  
  public int getScrollState() {
    return this.mScrollState;
  }
  
  public boolean hasFixedSize() {
    return this.mHasFixedSize;
  }
  
  public boolean hasNestedScrollingParent() {
    return getScrollingChildHelper().hasNestedScrollingParent();
  }
  
  public boolean hasNestedScrollingParent(int paramInt) {
    return getScrollingChildHelper().hasNestedScrollingParent(paramInt);
  }
  
  public boolean hasPendingAdapterUpdates() {
    return (!this.mFirstLayoutComplete || this.mDataSetHasChangedAfterLayout || this.mAdapterHelper.hasPendingUpdates());
  }
  
  void initAdapterManager() {
    this.mAdapterHelper = new AdapterHelper(new AdapterHelper.Callback() {
          void dispatchUpdate(AdapterHelper.UpdateOp param1UpdateOp) {
            int i = param1UpdateOp.cmd;
            if (i != 1) {
              if (i != 2) {
                if (i != 4) {
                  if (i == 8)
                    RecyclerView.this.mLayout.onItemsMoved(RecyclerView.this, param1UpdateOp.positionStart, param1UpdateOp.itemCount, 1); 
                } else {
                  RecyclerView.this.mLayout.onItemsUpdated(RecyclerView.this, param1UpdateOp.positionStart, param1UpdateOp.itemCount, param1UpdateOp.payload);
                } 
              } else {
                RecyclerView.this.mLayout.onItemsRemoved(RecyclerView.this, param1UpdateOp.positionStart, param1UpdateOp.itemCount);
              } 
            } else {
              RecyclerView.this.mLayout.onItemsAdded(RecyclerView.this, param1UpdateOp.positionStart, param1UpdateOp.itemCount);
            } 
          }
          
          public RecyclerView.ViewHolder findViewHolder(int param1Int) {
            RecyclerView.ViewHolder viewHolder = RecyclerView.this.findViewHolderForPosition(param1Int, true);
            return (viewHolder == null) ? null : (RecyclerView.this.mChildHelper.isHidden(viewHolder.itemView) ? null : viewHolder);
          }
          
          public void markViewHoldersUpdated(int param1Int1, int param1Int2, Object param1Object) {
            RecyclerView.this.viewRangeUpdate(param1Int1, param1Int2, param1Object);
            RecyclerView.this.mItemsChanged = true;
          }
          
          public void offsetPositionsForAdd(int param1Int1, int param1Int2) {
            RecyclerView.this.offsetPositionRecordsForInsert(param1Int1, param1Int2);
            RecyclerView.this.mItemsAddedOrRemoved = true;
          }
          
          public void offsetPositionsForMove(int param1Int1, int param1Int2) {
            RecyclerView.this.offsetPositionRecordsForMove(param1Int1, param1Int2);
            RecyclerView.this.mItemsAddedOrRemoved = true;
          }
          
          public void offsetPositionsForRemovingInvisible(int param1Int1, int param1Int2) {
            RecyclerView.this.offsetPositionRecordsForRemove(param1Int1, param1Int2, true);
            RecyclerView.this.mItemsAddedOrRemoved = true;
            RecyclerView.State state = RecyclerView.this.mState;
            state.mDeletedInvisibleItemCountSincePreviousLayout += param1Int2;
          }
          
          public void offsetPositionsForRemovingLaidOutOrNewView(int param1Int1, int param1Int2) {
            RecyclerView.this.offsetPositionRecordsForRemove(param1Int1, param1Int2, false);
            RecyclerView.this.mItemsAddedOrRemoved = true;
          }
          
          public void onDispatchFirstPass(AdapterHelper.UpdateOp param1UpdateOp) {
            dispatchUpdate(param1UpdateOp);
          }
          
          public void onDispatchSecondPass(AdapterHelper.UpdateOp param1UpdateOp) {
            dispatchUpdate(param1UpdateOp);
          }
        });
  }
  
  void initFastScroller(StateListDrawable paramStateListDrawable1, Drawable paramDrawable1, StateListDrawable paramStateListDrawable2, Drawable paramDrawable2) {
    if (paramStateListDrawable1 != null && paramDrawable1 != null && paramStateListDrawable2 != null && paramDrawable2 != null) {
      Resources resources = getContext().getResources();
      new FastScroller(this, paramStateListDrawable1, paramDrawable1, paramStateListDrawable2, paramDrawable2, resources.getDimensionPixelSize(R.dimen.fastscroll_default_thickness), resources.getDimensionPixelSize(R.dimen.fastscroll_minimum_range), resources.getDimensionPixelOffset(R.dimen.fastscroll_margin));
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Trying to set fast scroller without both required drawables.");
    stringBuilder.append(exceptionLabel());
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  void invalidateGlows() {
    this.mBottomGlow = null;
    this.mTopGlow = null;
    this.mRightGlow = null;
    this.mLeftGlow = null;
  }
  
  public void invalidateItemDecorations() {
    if (this.mItemDecorations.size() == 0)
      return; 
    LayoutManager layoutManager = this.mLayout;
    if (layoutManager != null)
      layoutManager.assertNotInLayoutOrScroll("Cannot invalidate item decorations during a scroll or layout"); 
    markItemDecorInsetsDirty();
    requestLayout();
  }
  
  boolean isAccessibilityEnabled() {
    boolean bool;
    AccessibilityManager accessibilityManager = this.mAccessibilityManager;
    if (accessibilityManager != null && accessibilityManager.isEnabled()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isAnimating() {
    boolean bool;
    ItemAnimator itemAnimator = this.mItemAnimator;
    if (itemAnimator != null && itemAnimator.isRunning()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isAttachedToWindow() {
    return this.mIsAttached;
  }
  
  public boolean isComputingLayout() {
    boolean bool;
    if (this.mLayoutOrScrollCounter > 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isLayoutFrozen() {
    return this.mLayoutFrozen;
  }
  
  public boolean isNestedScrollingEnabled() {
    return getScrollingChildHelper().isNestedScrollingEnabled();
  }
  
  void jumpToPositionForSmoothScroller(int paramInt) {
    LayoutManager layoutManager = this.mLayout;
    if (layoutManager == null)
      return; 
    layoutManager.scrollToPosition(paramInt);
    awakenScrollBars();
  }
  
  void markItemDecorInsetsDirty() {
    int i = this.mChildHelper.getUnfilteredChildCount();
    for (byte b = 0; b < i; b++)
      ((LayoutParams)this.mChildHelper.getUnfilteredChildAt(b).getLayoutParams()).mInsetsDirty = true; 
    this.mRecycler.markItemDecorInsetsDirty();
  }
  
  void markKnownViewsInvalid() {
    int i = this.mChildHelper.getUnfilteredChildCount();
    for (byte b = 0; b < i; b++) {
      ViewHolder viewHolder = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(b));
      if (viewHolder != null && !viewHolder.shouldIgnore())
        viewHolder.addFlags(6); 
    } 
    markItemDecorInsetsDirty();
    this.mRecycler.markKnownViewsInvalid();
  }
  
  public void offsetChildrenHorizontal(int paramInt) {
    int i = this.mChildHelper.getChildCount();
    for (byte b = 0; b < i; b++)
      this.mChildHelper.getChildAt(b).offsetLeftAndRight(paramInt); 
  }
  
  public void offsetChildrenVertical(int paramInt) {
    int i = this.mChildHelper.getChildCount();
    for (byte b = 0; b < i; b++)
      this.mChildHelper.getChildAt(b).offsetTopAndBottom(paramInt); 
  }
  
  void offsetPositionRecordsForInsert(int paramInt1, int paramInt2) {
    int i = this.mChildHelper.getUnfilteredChildCount();
    for (byte b = 0; b < i; b++) {
      ViewHolder viewHolder = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(b));
      if (viewHolder != null && !viewHolder.shouldIgnore() && viewHolder.mPosition >= paramInt1) {
        viewHolder.offsetPosition(paramInt2, false);
        this.mState.mStructureChanged = true;
      } 
    } 
    this.mRecycler.offsetPositionRecordsForInsert(paramInt1, paramInt2);
    requestLayout();
  }
  
  void offsetPositionRecordsForMove(int paramInt1, int paramInt2) {
    boolean bool;
    int j;
    int k;
    int i = this.mChildHelper.getUnfilteredChildCount();
    if (paramInt1 < paramInt2) {
      bool = true;
      j = paramInt1;
      k = paramInt2;
    } else {
      k = paramInt1;
      j = paramInt2;
      bool = true;
    } 
    for (byte b = 0; b < i; b++) {
      ViewHolder viewHolder = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(b));
      if (viewHolder != null && viewHolder.mPosition >= j && viewHolder.mPosition <= k) {
        if (viewHolder.mPosition == paramInt1) {
          viewHolder.offsetPosition(paramInt2 - paramInt1, false);
        } else {
          viewHolder.offsetPosition(bool, false);
        } 
        this.mState.mStructureChanged = true;
      } 
    } 
    this.mRecycler.offsetPositionRecordsForMove(paramInt1, paramInt2);
    requestLayout();
  }
  
  void offsetPositionRecordsForRemove(int paramInt1, int paramInt2, boolean paramBoolean) {
    int i = this.mChildHelper.getUnfilteredChildCount();
    for (byte b = 0; b < i; b++) {
      ViewHolder viewHolder = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(b));
      if (viewHolder != null && !viewHolder.shouldIgnore())
        if (viewHolder.mPosition >= paramInt1 + paramInt2) {
          viewHolder.offsetPosition(-paramInt2, paramBoolean);
          this.mState.mStructureChanged = true;
        } else if (viewHolder.mPosition >= paramInt1) {
          viewHolder.flagRemovedAndOffsetPosition(paramInt1 - 1, -paramInt2, paramBoolean);
          this.mState.mStructureChanged = true;
        }  
    } 
    this.mRecycler.offsetPositionRecordsForRemove(paramInt1, paramInt2, paramBoolean);
    requestLayout();
  }
  
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    this.mLayoutOrScrollCounter = 0;
    boolean bool = true;
    this.mIsAttached = true;
    if (!this.mFirstLayoutComplete || isLayoutRequested())
      bool = false; 
    this.mFirstLayoutComplete = bool;
    LayoutManager layoutManager = this.mLayout;
    if (layoutManager != null)
      layoutManager.dispatchAttachedToWindow(this); 
    this.mPostedAnimatorRunner = false;
    if (ALLOW_THREAD_GAP_WORK) {
      GapWorker gapWorker = GapWorker.sGapWorker.get();
      this.mGapWorker = gapWorker;
      if (gapWorker == null) {
        this.mGapWorker = new GapWorker();
        Display display = ViewCompat.getDisplay((View)this);
        float f1 = 60.0F;
        float f2 = f1;
        if (!isInEditMode()) {
          f2 = f1;
          if (display != null) {
            float f = display.getRefreshRate();
            f2 = f1;
            if (f >= 30.0F)
              f2 = f; 
          } 
        } 
        this.mGapWorker.mFrameIntervalNs = (long)(1.0E9F / f2);
        GapWorker.sGapWorker.set(this.mGapWorker);
      } 
      this.mGapWorker.add(this);
    } 
  }
  
  public void onChildAttachedToWindow(View paramView) {}
  
  public void onChildDetachedFromWindow(View paramView) {}
  
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    ItemAnimator itemAnimator = this.mItemAnimator;
    if (itemAnimator != null)
      itemAnimator.endAnimations(); 
    stopScroll();
    this.mIsAttached = false;
    LayoutManager layoutManager = this.mLayout;
    if (layoutManager != null)
      layoutManager.dispatchDetachedFromWindow(this, this.mRecycler); 
    this.mPendingAccessibilityImportanceChange.clear();
    removeCallbacks(this.mItemAnimatorRunner);
    this.mViewInfoStore.onDetach();
    if (ALLOW_THREAD_GAP_WORK) {
      GapWorker gapWorker = this.mGapWorker;
      if (gapWorker != null) {
        gapWorker.remove(this);
        this.mGapWorker = null;
      } 
    } 
  }
  
  public void onDraw(Canvas paramCanvas) {
    super.onDraw(paramCanvas);
    int i = this.mItemDecorations.size();
    for (byte b = 0; b < i; b++)
      ((ItemDecoration)this.mItemDecorations.get(b)).onDraw(paramCanvas, this, this.mState); 
  }
  
  void onEnterLayoutOrScroll() {
    this.mLayoutOrScrollCounter++;
  }
  
  void onExitLayoutOrScroll() {
    onExitLayoutOrScroll(true);
  }
  
  void onExitLayoutOrScroll(boolean paramBoolean) {
    int i = this.mLayoutOrScrollCounter - 1;
    this.mLayoutOrScrollCounter = i;
    if (i < 1) {
      this.mLayoutOrScrollCounter = 0;
      if (paramBoolean) {
        dispatchContentChangedIfNecessary();
        dispatchPendingImportantForAccessibilityChanges();
      } 
    } 
  }
  
  public boolean onGenericMotionEvent(MotionEvent paramMotionEvent) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mLayout : Landroid/support/v7/widget/RecyclerView$LayoutManager;
    //   4: ifnonnull -> 9
    //   7: iconst_0
    //   8: ireturn
    //   9: aload_0
    //   10: getfield mLayoutFrozen : Z
    //   13: ifeq -> 18
    //   16: iconst_0
    //   17: ireturn
    //   18: aload_1
    //   19: invokevirtual getAction : ()I
    //   22: bipush #8
    //   24: if_icmpne -> 172
    //   27: aload_1
    //   28: invokevirtual getSource : ()I
    //   31: iconst_2
    //   32: iand
    //   33: ifeq -> 87
    //   36: aload_0
    //   37: getfield mLayout : Landroid/support/v7/widget/RecyclerView$LayoutManager;
    //   40: invokevirtual canScrollVertically : ()Z
    //   43: ifeq -> 57
    //   46: aload_1
    //   47: bipush #9
    //   49: invokevirtual getAxisValue : (I)F
    //   52: fneg
    //   53: fstore_2
    //   54: goto -> 59
    //   57: fconst_0
    //   58: fstore_2
    //   59: fload_2
    //   60: fstore_3
    //   61: aload_0
    //   62: getfield mLayout : Landroid/support/v7/widget/RecyclerView$LayoutManager;
    //   65: invokevirtual canScrollHorizontally : ()Z
    //   68: ifeq -> 138
    //   71: aload_1
    //   72: bipush #10
    //   74: invokevirtual getAxisValue : (I)F
    //   77: fstore #4
    //   79: fload_2
    //   80: fstore_3
    //   81: fload #4
    //   83: fstore_2
    //   84: goto -> 140
    //   87: aload_1
    //   88: invokevirtual getSource : ()I
    //   91: ldc_w 4194304
    //   94: iand
    //   95: ifeq -> 136
    //   98: aload_1
    //   99: bipush #26
    //   101: invokevirtual getAxisValue : (I)F
    //   104: fstore_2
    //   105: aload_0
    //   106: getfield mLayout : Landroid/support/v7/widget/RecyclerView$LayoutManager;
    //   109: invokevirtual canScrollVertically : ()Z
    //   112: ifeq -> 121
    //   115: fload_2
    //   116: fneg
    //   117: fstore_3
    //   118: goto -> 138
    //   121: aload_0
    //   122: getfield mLayout : Landroid/support/v7/widget/RecyclerView$LayoutManager;
    //   125: invokevirtual canScrollHorizontally : ()Z
    //   128: ifeq -> 136
    //   131: fconst_0
    //   132: fstore_3
    //   133: goto -> 140
    //   136: fconst_0
    //   137: fstore_3
    //   138: fconst_0
    //   139: fstore_2
    //   140: fload_3
    //   141: fconst_0
    //   142: fcmpl
    //   143: ifne -> 152
    //   146: fload_2
    //   147: fconst_0
    //   148: fcmpl
    //   149: ifeq -> 172
    //   152: aload_0
    //   153: fload_2
    //   154: aload_0
    //   155: getfield mScaledHorizontalScrollFactor : F
    //   158: fmul
    //   159: f2i
    //   160: fload_3
    //   161: aload_0
    //   162: getfield mScaledVerticalScrollFactor : F
    //   165: fmul
    //   166: f2i
    //   167: aload_1
    //   168: invokevirtual scrollByInternal : (IILandroid/view/MotionEvent;)Z
    //   171: pop
    //   172: iconst_0
    //   173: ireturn
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
    StringBuilder stringBuilder;
    boolean bool1 = this.mLayoutFrozen;
    boolean bool = false;
    if (bool1)
      return false; 
    if (dispatchOnItemTouchIntercept(paramMotionEvent)) {
      cancelTouch();
      return true;
    } 
    LayoutManager layoutManager = this.mLayout;
    if (layoutManager == null)
      return false; 
    boolean bool2 = layoutManager.canScrollHorizontally();
    bool1 = this.mLayout.canScrollVertically();
    if (this.mVelocityTracker == null)
      this.mVelocityTracker = VelocityTracker.obtain(); 
    this.mVelocityTracker.addMovement(paramMotionEvent);
    int i = paramMotionEvent.getActionMasked();
    int j = paramMotionEvent.getActionIndex();
    if (i != 0) {
      if (i != 1) {
        if (i != 2) {
          if (i != 3) {
            if (i != 5) {
              if (i == 6)
                onPointerUp(paramMotionEvent); 
            } else {
              this.mScrollPointerId = paramMotionEvent.getPointerId(j);
              i = (int)(paramMotionEvent.getX(j) + 0.5F);
              this.mLastTouchX = i;
              this.mInitialTouchX = i;
              j = (int)(paramMotionEvent.getY(j) + 0.5F);
              this.mLastTouchY = j;
              this.mInitialTouchY = j;
            } 
          } else {
            cancelTouch();
          } 
        } else {
          i = paramMotionEvent.findPointerIndex(this.mScrollPointerId);
          if (i < 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Error processing scroll; pointer index for id ");
            stringBuilder.append(this.mScrollPointerId);
            stringBuilder.append(" not found. Did any MotionEvents get skipped?");
            Log.e("RecyclerView", stringBuilder.toString());
            return false;
          } 
          j = (int)(stringBuilder.getX(i) + 0.5F);
          int k = (int)(stringBuilder.getY(i) + 0.5F);
          if (this.mScrollState != 1) {
            i = this.mInitialTouchX;
            int m = this.mInitialTouchY;
            if (bool2 && Math.abs(j - i) > this.mTouchSlop) {
              this.mLastTouchX = j;
              j = 1;
            } else {
              j = 0;
            } 
            i = j;
            if (bool1) {
              i = j;
              if (Math.abs(k - m) > this.mTouchSlop) {
                this.mLastTouchY = k;
                i = 1;
              } 
            } 
            if (i != 0)
              setScrollState(1); 
          } 
        } 
      } else {
        this.mVelocityTracker.clear();
        stopNestedScroll(0);
      } 
    } else {
      if (this.mIgnoreMotionEventTillDown)
        this.mIgnoreMotionEventTillDown = false; 
      this.mScrollPointerId = stringBuilder.getPointerId(0);
      j = (int)(stringBuilder.getX() + 0.5F);
      this.mLastTouchX = j;
      this.mInitialTouchX = j;
      j = (int)(stringBuilder.getY() + 0.5F);
      this.mLastTouchY = j;
      this.mInitialTouchY = j;
      if (this.mScrollState == 2) {
        getParent().requestDisallowInterceptTouchEvent(true);
        setScrollState(1);
      } 
      int[] arrayOfInt = this.mNestedOffsets;
      arrayOfInt[1] = 0;
      arrayOfInt[0] = 0;
      if (bool2) {
        j = 1;
      } else {
        j = 0;
      } 
      i = j;
      if (bool1)
        i = j | 0x2; 
      startNestedScroll(i, 0);
    } 
    if (this.mScrollState == 1)
      bool = true; 
    return bool;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    TraceCompat.beginSection("RV OnLayout");
    dispatchLayout();
    TraceCompat.endSection();
    this.mFirstLayoutComplete = true;
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    LayoutManager layoutManager = this.mLayout;
    if (layoutManager == null) {
      defaultOnMeasure(paramInt1, paramInt2);
      return;
    } 
    boolean bool = layoutManager.isAutoMeasureEnabled();
    boolean bool1 = false;
    if (bool) {
      int i = View.MeasureSpec.getMode(paramInt1);
      int j = View.MeasureSpec.getMode(paramInt2);
      this.mLayout.onMeasure(this.mRecycler, this.mState, paramInt1, paramInt2);
      boolean bool2 = bool1;
      if (i == 1073741824) {
        bool2 = bool1;
        if (j == 1073741824)
          bool2 = true; 
      } 
      if (bool2 || this.mAdapter == null)
        return; 
      if (this.mState.mLayoutStep == 1)
        dispatchLayoutStep1(); 
      this.mLayout.setMeasureSpecs(paramInt1, paramInt2);
      this.mState.mIsMeasuring = true;
      dispatchLayoutStep2();
      this.mLayout.setMeasuredDimensionFromChildren(paramInt1, paramInt2);
      if (this.mLayout.shouldMeasureTwice()) {
        this.mLayout.setMeasureSpecs(View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
        this.mState.mIsMeasuring = true;
        dispatchLayoutStep2();
        this.mLayout.setMeasuredDimensionFromChildren(paramInt1, paramInt2);
      } 
    } else {
      if (this.mHasFixedSize) {
        this.mLayout.onMeasure(this.mRecycler, this.mState, paramInt1, paramInt2);
        return;
      } 
      if (this.mAdapterUpdateDuringMeasure) {
        startInterceptRequestLayout();
        onEnterLayoutOrScroll();
        processAdapterUpdatesAndSetAnimationFlags();
        onExitLayoutOrScroll();
        if (this.mState.mRunPredictiveAnimations) {
          this.mState.mInPreLayout = true;
        } else {
          this.mAdapterHelper.consumeUpdatesInOnePass();
          this.mState.mInPreLayout = false;
        } 
        this.mAdapterUpdateDuringMeasure = false;
        stopInterceptRequestLayout(false);
      } else if (this.mState.mRunPredictiveAnimations) {
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
        return;
      } 
      Adapter adapter = this.mAdapter;
      if (adapter != null) {
        this.mState.mItemCount = adapter.getItemCount();
      } else {
        this.mState.mItemCount = 0;
      } 
      startInterceptRequestLayout();
      this.mLayout.onMeasure(this.mRecycler, this.mState, paramInt1, paramInt2);
      stopInterceptRequestLayout(false);
      this.mState.mInPreLayout = false;
    } 
  }
  
  protected boolean onRequestFocusInDescendants(int paramInt, Rect paramRect) {
    return isComputingLayout() ? false : super.onRequestFocusInDescendants(paramInt, paramRect);
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable) {
    if (!(paramParcelable instanceof SavedState)) {
      super.onRestoreInstanceState(paramParcelable);
      return;
    } 
    SavedState savedState = (SavedState)paramParcelable;
    this.mPendingSavedState = savedState;
    super.onRestoreInstanceState(savedState.getSuperState());
    if (this.mLayout != null && this.mPendingSavedState.mLayoutState != null)
      this.mLayout.onRestoreInstanceState(this.mPendingSavedState.mLayoutState); 
  }
  
  protected Parcelable onSaveInstanceState() {
    SavedState savedState1 = new SavedState(super.onSaveInstanceState());
    SavedState savedState2 = this.mPendingSavedState;
    if (savedState2 != null) {
      savedState1.copyFrom(savedState2);
    } else {
      LayoutManager layoutManager = this.mLayout;
      if (layoutManager != null) {
        savedState1.mLayoutState = layoutManager.onSaveInstanceState();
      } else {
        savedState1.mLayoutState = null;
      } 
    } 
    return (Parcelable)savedState1;
  }
  
  public void onScrollStateChanged(int paramInt) {}
  
  public void onScrolled(int paramInt1, int paramInt2) {}
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (paramInt1 != paramInt3 || paramInt2 != paramInt4)
      invalidateGlows(); 
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mLayoutFrozen : Z
    //   4: istore_2
    //   5: iconst_0
    //   6: istore_3
    //   7: iload_2
    //   8: ifne -> 998
    //   11: aload_0
    //   12: getfield mIgnoreMotionEventTillDown : Z
    //   15: ifeq -> 21
    //   18: goto -> 998
    //   21: aload_0
    //   22: aload_1
    //   23: invokespecial dispatchOnItemTouch : (Landroid/view/MotionEvent;)Z
    //   26: ifeq -> 35
    //   29: aload_0
    //   30: invokespecial cancelTouch : ()V
    //   33: iconst_1
    //   34: ireturn
    //   35: aload_0
    //   36: getfield mLayout : Landroid/support/v7/widget/RecyclerView$LayoutManager;
    //   39: astore #4
    //   41: aload #4
    //   43: ifnonnull -> 48
    //   46: iconst_0
    //   47: ireturn
    //   48: aload #4
    //   50: invokevirtual canScrollHorizontally : ()Z
    //   53: istore #5
    //   55: aload_0
    //   56: getfield mLayout : Landroid/support/v7/widget/RecyclerView$LayoutManager;
    //   59: invokevirtual canScrollVertically : ()Z
    //   62: istore_2
    //   63: aload_0
    //   64: getfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   67: ifnonnull -> 77
    //   70: aload_0
    //   71: invokestatic obtain : ()Landroid/view/VelocityTracker;
    //   74: putfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   77: aload_1
    //   78: invokestatic obtain : (Landroid/view/MotionEvent;)Landroid/view/MotionEvent;
    //   81: astore #4
    //   83: aload_1
    //   84: invokevirtual getActionMasked : ()I
    //   87: istore #6
    //   89: aload_1
    //   90: invokevirtual getActionIndex : ()I
    //   93: istore #7
    //   95: iload #6
    //   97: ifne -> 116
    //   100: aload_0
    //   101: getfield mNestedOffsets : [I
    //   104: astore #8
    //   106: aload #8
    //   108: iconst_1
    //   109: iconst_0
    //   110: iastore
    //   111: aload #8
    //   113: iconst_0
    //   114: iconst_0
    //   115: iastore
    //   116: aload_0
    //   117: getfield mNestedOffsets : [I
    //   120: astore #8
    //   122: aload #4
    //   124: aload #8
    //   126: iconst_0
    //   127: iaload
    //   128: i2f
    //   129: aload #8
    //   131: iconst_1
    //   132: iaload
    //   133: i2f
    //   134: invokevirtual offsetLocation : (FF)V
    //   137: iload #6
    //   139: ifeq -> 883
    //   142: iload #6
    //   144: iconst_1
    //   145: if_icmpeq -> 768
    //   148: iload #6
    //   150: iconst_2
    //   151: if_icmpeq -> 266
    //   154: iload #6
    //   156: iconst_3
    //   157: if_icmpeq -> 256
    //   160: iload #6
    //   162: iconst_5
    //   163: if_icmpeq -> 190
    //   166: iload #6
    //   168: bipush #6
    //   170: if_icmpeq -> 179
    //   173: iload_3
    //   174: istore #7
    //   176: goto -> 977
    //   179: aload_0
    //   180: aload_1
    //   181: invokespecial onPointerUp : (Landroid/view/MotionEvent;)V
    //   184: iload_3
    //   185: istore #7
    //   187: goto -> 977
    //   190: aload_0
    //   191: aload_1
    //   192: iload #7
    //   194: invokevirtual getPointerId : (I)I
    //   197: putfield mScrollPointerId : I
    //   200: aload_1
    //   201: iload #7
    //   203: invokevirtual getX : (I)F
    //   206: ldc_w 0.5
    //   209: fadd
    //   210: f2i
    //   211: istore #6
    //   213: aload_0
    //   214: iload #6
    //   216: putfield mLastTouchX : I
    //   219: aload_0
    //   220: iload #6
    //   222: putfield mInitialTouchX : I
    //   225: aload_1
    //   226: iload #7
    //   228: invokevirtual getY : (I)F
    //   231: ldc_w 0.5
    //   234: fadd
    //   235: f2i
    //   236: istore #7
    //   238: aload_0
    //   239: iload #7
    //   241: putfield mLastTouchY : I
    //   244: aload_0
    //   245: iload #7
    //   247: putfield mInitialTouchY : I
    //   250: iload_3
    //   251: istore #7
    //   253: goto -> 977
    //   256: aload_0
    //   257: invokespecial cancelTouch : ()V
    //   260: iload_3
    //   261: istore #7
    //   263: goto -> 977
    //   266: aload_1
    //   267: aload_0
    //   268: getfield mScrollPointerId : I
    //   271: invokevirtual findPointerIndex : (I)I
    //   274: istore #7
    //   276: iload #7
    //   278: ifge -> 326
    //   281: new java/lang/StringBuilder
    //   284: dup
    //   285: invokespecial <init> : ()V
    //   288: astore_1
    //   289: aload_1
    //   290: ldc_w 'Error processing scroll; pointer index for id '
    //   293: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   296: pop
    //   297: aload_1
    //   298: aload_0
    //   299: getfield mScrollPointerId : I
    //   302: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   305: pop
    //   306: aload_1
    //   307: ldc_w ' not found. Did any MotionEvents get skipped?'
    //   310: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   313: pop
    //   314: ldc 'RecyclerView'
    //   316: aload_1
    //   317: invokevirtual toString : ()Ljava/lang/String;
    //   320: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)I
    //   323: pop
    //   324: iconst_0
    //   325: ireturn
    //   326: aload_1
    //   327: iload #7
    //   329: invokevirtual getX : (I)F
    //   332: ldc_w 0.5
    //   335: fadd
    //   336: f2i
    //   337: istore #9
    //   339: aload_1
    //   340: iload #7
    //   342: invokevirtual getY : (I)F
    //   345: ldc_w 0.5
    //   348: fadd
    //   349: f2i
    //   350: istore #10
    //   352: aload_0
    //   353: getfield mLastTouchX : I
    //   356: iload #9
    //   358: isub
    //   359: istore #11
    //   361: aload_0
    //   362: getfield mLastTouchY : I
    //   365: iload #10
    //   367: isub
    //   368: istore #12
    //   370: iload #11
    //   372: istore #6
    //   374: iload #12
    //   376: istore #7
    //   378: aload_0
    //   379: iload #11
    //   381: iload #12
    //   383: aload_0
    //   384: getfield mScrollConsumed : [I
    //   387: aload_0
    //   388: getfield mScrollOffset : [I
    //   391: iconst_0
    //   392: invokevirtual dispatchNestedPreScroll : (II[I[II)Z
    //   395: ifeq -> 476
    //   398: aload_0
    //   399: getfield mScrollConsumed : [I
    //   402: astore_1
    //   403: iload #11
    //   405: aload_1
    //   406: iconst_0
    //   407: iaload
    //   408: isub
    //   409: istore #6
    //   411: iload #12
    //   413: aload_1
    //   414: iconst_1
    //   415: iaload
    //   416: isub
    //   417: istore #7
    //   419: aload_0
    //   420: getfield mScrollOffset : [I
    //   423: astore_1
    //   424: aload #4
    //   426: aload_1
    //   427: iconst_0
    //   428: iaload
    //   429: i2f
    //   430: aload_1
    //   431: iconst_1
    //   432: iaload
    //   433: i2f
    //   434: invokevirtual offsetLocation : (FF)V
    //   437: aload_0
    //   438: getfield mNestedOffsets : [I
    //   441: astore #8
    //   443: aload #8
    //   445: iconst_0
    //   446: iaload
    //   447: istore #12
    //   449: aload_0
    //   450: getfield mScrollOffset : [I
    //   453: astore_1
    //   454: aload #8
    //   456: iconst_0
    //   457: iload #12
    //   459: aload_1
    //   460: iconst_0
    //   461: iaload
    //   462: iadd
    //   463: iastore
    //   464: aload #8
    //   466: iconst_1
    //   467: aload #8
    //   469: iconst_1
    //   470: iaload
    //   471: aload_1
    //   472: iconst_1
    //   473: iaload
    //   474: iadd
    //   475: iastore
    //   476: iload #6
    //   478: istore #11
    //   480: iload #7
    //   482: istore #12
    //   484: aload_0
    //   485: getfield mScrollState : I
    //   488: iconst_1
    //   489: if_icmpeq -> 639
    //   492: iload #5
    //   494: ifeq -> 545
    //   497: iload #6
    //   499: invokestatic abs : (I)I
    //   502: istore #11
    //   504: aload_0
    //   505: getfield mTouchSlop : I
    //   508: istore #12
    //   510: iload #11
    //   512: iload #12
    //   514: if_icmple -> 545
    //   517: iload #6
    //   519: ifle -> 532
    //   522: iload #6
    //   524: iload #12
    //   526: isub
    //   527: istore #6
    //   529: goto -> 539
    //   532: iload #6
    //   534: iload #12
    //   536: iadd
    //   537: istore #6
    //   539: iconst_1
    //   540: istore #12
    //   542: goto -> 548
    //   545: iconst_0
    //   546: istore #12
    //   548: iload #12
    //   550: istore #13
    //   552: iload #7
    //   554: istore #14
    //   556: iload_2
    //   557: ifeq -> 613
    //   560: iload #7
    //   562: invokestatic abs : (I)I
    //   565: istore #11
    //   567: aload_0
    //   568: getfield mTouchSlop : I
    //   571: istore #15
    //   573: iload #12
    //   575: istore #13
    //   577: iload #7
    //   579: istore #14
    //   581: iload #11
    //   583: iload #15
    //   585: if_icmple -> 613
    //   588: iload #7
    //   590: ifle -> 603
    //   593: iload #7
    //   595: iload #15
    //   597: isub
    //   598: istore #14
    //   600: goto -> 610
    //   603: iload #7
    //   605: iload #15
    //   607: iadd
    //   608: istore #14
    //   610: iconst_1
    //   611: istore #13
    //   613: iload #6
    //   615: istore #11
    //   617: iload #14
    //   619: istore #12
    //   621: iload #13
    //   623: ifeq -> 639
    //   626: aload_0
    //   627: iconst_1
    //   628: invokevirtual setScrollState : (I)V
    //   631: iload #14
    //   633: istore #12
    //   635: iload #6
    //   637: istore #11
    //   639: iload_3
    //   640: istore #7
    //   642: aload_0
    //   643: getfield mScrollState : I
    //   646: iconst_1
    //   647: if_icmpne -> 977
    //   650: aload_0
    //   651: getfield mScrollOffset : [I
    //   654: astore_1
    //   655: aload_0
    //   656: iload #9
    //   658: aload_1
    //   659: iconst_0
    //   660: iaload
    //   661: isub
    //   662: putfield mLastTouchX : I
    //   665: aload_0
    //   666: iload #10
    //   668: aload_1
    //   669: iconst_1
    //   670: iaload
    //   671: isub
    //   672: putfield mLastTouchY : I
    //   675: iload #5
    //   677: ifeq -> 687
    //   680: iload #11
    //   682: istore #7
    //   684: goto -> 690
    //   687: iconst_0
    //   688: istore #7
    //   690: iload_2
    //   691: ifeq -> 701
    //   694: iload #12
    //   696: istore #6
    //   698: goto -> 704
    //   701: iconst_0
    //   702: istore #6
    //   704: aload_0
    //   705: iload #7
    //   707: iload #6
    //   709: aload #4
    //   711: invokevirtual scrollByInternal : (IILandroid/view/MotionEvent;)Z
    //   714: ifeq -> 727
    //   717: aload_0
    //   718: invokevirtual getParent : ()Landroid/view/ViewParent;
    //   721: iconst_1
    //   722: invokeinterface requestDisallowInterceptTouchEvent : (Z)V
    //   727: iload_3
    //   728: istore #7
    //   730: aload_0
    //   731: getfield mGapWorker : Landroid/support/v7/widget/GapWorker;
    //   734: ifnull -> 977
    //   737: iload #11
    //   739: ifne -> 750
    //   742: iload_3
    //   743: istore #7
    //   745: iload #12
    //   747: ifeq -> 977
    //   750: aload_0
    //   751: getfield mGapWorker : Landroid/support/v7/widget/GapWorker;
    //   754: aload_0
    //   755: iload #11
    //   757: iload #12
    //   759: invokevirtual postFromTraversal : (Landroid/support/v7/widget/RecyclerView;II)V
    //   762: iload_3
    //   763: istore #7
    //   765: goto -> 977
    //   768: aload_0
    //   769: getfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   772: aload #4
    //   774: invokevirtual addMovement : (Landroid/view/MotionEvent;)V
    //   777: aload_0
    //   778: getfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   781: sipush #1000
    //   784: aload_0
    //   785: getfield mMaxFlingVelocity : I
    //   788: i2f
    //   789: invokevirtual computeCurrentVelocity : (IF)V
    //   792: iload #5
    //   794: ifeq -> 814
    //   797: aload_0
    //   798: getfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   801: aload_0
    //   802: getfield mScrollPointerId : I
    //   805: invokevirtual getXVelocity : (I)F
    //   808: fneg
    //   809: fstore #16
    //   811: goto -> 817
    //   814: fconst_0
    //   815: fstore #16
    //   817: iload_2
    //   818: ifeq -> 838
    //   821: aload_0
    //   822: getfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   825: aload_0
    //   826: getfield mScrollPointerId : I
    //   829: invokevirtual getYVelocity : (I)F
    //   832: fneg
    //   833: fstore #17
    //   835: goto -> 841
    //   838: fconst_0
    //   839: fstore #17
    //   841: fload #16
    //   843: fconst_0
    //   844: fcmpl
    //   845: ifne -> 855
    //   848: fload #17
    //   850: fconst_0
    //   851: fcmpl
    //   852: ifeq -> 868
    //   855: aload_0
    //   856: fload #16
    //   858: f2i
    //   859: fload #17
    //   861: f2i
    //   862: invokevirtual fling : (II)Z
    //   865: ifne -> 873
    //   868: aload_0
    //   869: iconst_0
    //   870: invokevirtual setScrollState : (I)V
    //   873: aload_0
    //   874: invokespecial resetTouch : ()V
    //   877: iconst_1
    //   878: istore #7
    //   880: goto -> 977
    //   883: aload_0
    //   884: aload_1
    //   885: iconst_0
    //   886: invokevirtual getPointerId : (I)I
    //   889: putfield mScrollPointerId : I
    //   892: aload_1
    //   893: invokevirtual getX : ()F
    //   896: ldc_w 0.5
    //   899: fadd
    //   900: f2i
    //   901: istore #7
    //   903: aload_0
    //   904: iload #7
    //   906: putfield mLastTouchX : I
    //   909: aload_0
    //   910: iload #7
    //   912: putfield mInitialTouchX : I
    //   915: aload_1
    //   916: invokevirtual getY : ()F
    //   919: ldc_w 0.5
    //   922: fadd
    //   923: f2i
    //   924: istore #7
    //   926: aload_0
    //   927: iload #7
    //   929: putfield mLastTouchY : I
    //   932: aload_0
    //   933: iload #7
    //   935: putfield mInitialTouchY : I
    //   938: iload #5
    //   940: ifeq -> 949
    //   943: iconst_1
    //   944: istore #7
    //   946: goto -> 952
    //   949: iconst_0
    //   950: istore #7
    //   952: iload #7
    //   954: istore #6
    //   956: iload_2
    //   957: ifeq -> 966
    //   960: iload #7
    //   962: iconst_2
    //   963: ior
    //   964: istore #6
    //   966: aload_0
    //   967: iload #6
    //   969: iconst_0
    //   970: invokevirtual startNestedScroll : (II)Z
    //   973: pop
    //   974: iload_3
    //   975: istore #7
    //   977: iload #7
    //   979: ifne -> 991
    //   982: aload_0
    //   983: getfield mVelocityTracker : Landroid/view/VelocityTracker;
    //   986: aload #4
    //   988: invokevirtual addMovement : (Landroid/view/MotionEvent;)V
    //   991: aload #4
    //   993: invokevirtual recycle : ()V
    //   996: iconst_1
    //   997: ireturn
    //   998: iconst_0
    //   999: ireturn
  }
  
  void postAnimationRunner() {
    if (!this.mPostedAnimatorRunner && this.mIsAttached) {
      ViewCompat.postOnAnimation((View)this, this.mItemAnimatorRunner);
      this.mPostedAnimatorRunner = true;
    } 
  }
  
  void processDataSetCompletelyChanged(boolean paramBoolean) {
    this.mDispatchItemsChangedEvent = paramBoolean | this.mDispatchItemsChangedEvent;
    this.mDataSetHasChangedAfterLayout = true;
    markKnownViewsInvalid();
  }
  
  void recordAnimationInfoIfBouncedHiddenView(ViewHolder paramViewHolder, ItemAnimator.ItemHolderInfo paramItemHolderInfo) {
    paramViewHolder.setFlags(0, 8192);
    if (this.mState.mTrackOldChangeHolders && paramViewHolder.isUpdated() && !paramViewHolder.isRemoved() && !paramViewHolder.shouldIgnore()) {
      long l = getChangedHolderKey(paramViewHolder);
      this.mViewInfoStore.addToOldChangeHolders(l, paramViewHolder);
    } 
    this.mViewInfoStore.addToPreLayout(paramViewHolder, paramItemHolderInfo);
  }
  
  void removeAndRecycleViews() {
    ItemAnimator itemAnimator = this.mItemAnimator;
    if (itemAnimator != null)
      itemAnimator.endAnimations(); 
    LayoutManager layoutManager = this.mLayout;
    if (layoutManager != null) {
      layoutManager.removeAndRecycleAllViews(this.mRecycler);
      this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
    } 
    this.mRecycler.clear();
  }
  
  boolean removeAnimatingView(View paramView) {
    startInterceptRequestLayout();
    boolean bool = this.mChildHelper.removeViewIfHidden(paramView);
    if (bool) {
      ViewHolder viewHolder = getChildViewHolderInt(paramView);
      this.mRecycler.unscrapView(viewHolder);
      this.mRecycler.recycleViewHolderInternal(viewHolder);
    } 
    stopInterceptRequestLayout(bool ^ true);
    return bool;
  }
  
  protected void removeDetachedView(View paramView, boolean paramBoolean) {
    StringBuilder stringBuilder;
    ViewHolder viewHolder = getChildViewHolderInt(paramView);
    if (viewHolder != null)
      if (viewHolder.isTmpDetached()) {
        viewHolder.clearTmpDetachFlag();
      } else if (!viewHolder.shouldIgnore()) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("Called removeDetachedView with a view which is not flagged as tmp detached.");
        stringBuilder.append(viewHolder);
        stringBuilder.append(exceptionLabel());
        throw new IllegalArgumentException(stringBuilder.toString());
      }  
    stringBuilder.clearAnimation();
    dispatchChildDetached((View)stringBuilder);
    super.removeDetachedView((View)stringBuilder, paramBoolean);
  }
  
  public void removeItemDecoration(ItemDecoration paramItemDecoration) {
    LayoutManager layoutManager = this.mLayout;
    if (layoutManager != null)
      layoutManager.assertNotInLayoutOrScroll("Cannot remove item decoration during a scroll  or layout"); 
    this.mItemDecorations.remove(paramItemDecoration);
    if (this.mItemDecorations.isEmpty()) {
      boolean bool;
      if (getOverScrollMode() == 2) {
        bool = true;
      } else {
        bool = false;
      } 
      setWillNotDraw(bool);
    } 
    markItemDecorInsetsDirty();
    requestLayout();
  }
  
  public void removeItemDecorationAt(int paramInt) {
    int i = getItemDecorationCount();
    if (paramInt >= 0 && paramInt < i) {
      removeItemDecoration(getItemDecorationAt(paramInt));
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramInt);
    stringBuilder.append(" is an invalid index for size ");
    stringBuilder.append(i);
    throw new IndexOutOfBoundsException(stringBuilder.toString());
  }
  
  public void removeOnChildAttachStateChangeListener(OnChildAttachStateChangeListener paramOnChildAttachStateChangeListener) {
    List<OnChildAttachStateChangeListener> list = this.mOnChildAttachStateListeners;
    if (list == null)
      return; 
    list.remove(paramOnChildAttachStateChangeListener);
  }
  
  public void removeOnItemTouchListener(OnItemTouchListener paramOnItemTouchListener) {
    this.mOnItemTouchListeners.remove(paramOnItemTouchListener);
    if (this.mActiveOnItemTouchListener == paramOnItemTouchListener)
      this.mActiveOnItemTouchListener = null; 
  }
  
  public void removeOnScrollListener(OnScrollListener paramOnScrollListener) {
    List<OnScrollListener> list = this.mScrollListeners;
    if (list != null)
      list.remove(paramOnScrollListener); 
  }
  
  void repositionShadowingViews() {
    int i = this.mChildHelper.getChildCount();
    for (byte b = 0; b < i; b++) {
      View view = this.mChildHelper.getChildAt(b);
      ViewHolder viewHolder = getChildViewHolder(view);
      if (viewHolder != null && viewHolder.mShadowingHolder != null) {
        View view1 = viewHolder.mShadowingHolder.itemView;
        int j = view.getLeft();
        int k = view.getTop();
        if (j != view1.getLeft() || k != view1.getTop())
          view1.layout(j, k, view1.getWidth() + j, view1.getHeight() + k); 
      } 
    } 
  }
  
  public void requestChildFocus(View paramView1, View paramView2) {
    if (!this.mLayout.onRequestChildFocus(this, this.mState, paramView1, paramView2) && paramView2 != null)
      requestChildOnScreen(paramView1, paramView2); 
    super.requestChildFocus(paramView1, paramView2);
  }
  
  public boolean requestChildRectangleOnScreen(View paramView, Rect paramRect, boolean paramBoolean) {
    return this.mLayout.requestChildRectangleOnScreen(this, paramView, paramRect, paramBoolean);
  }
  
  public void requestDisallowInterceptTouchEvent(boolean paramBoolean) {
    int i = this.mOnItemTouchListeners.size();
    for (byte b = 0; b < i; b++)
      ((OnItemTouchListener)this.mOnItemTouchListeners.get(b)).onRequestDisallowInterceptTouchEvent(paramBoolean); 
    super.requestDisallowInterceptTouchEvent(paramBoolean);
  }
  
  public void requestLayout() {
    if (this.mInterceptRequestLayoutDepth == 0 && !this.mLayoutFrozen) {
      super.requestLayout();
    } else {
      this.mLayoutWasDefered = true;
    } 
  }
  
  void saveOldPositions() {
    int i = this.mChildHelper.getUnfilteredChildCount();
    for (byte b = 0; b < i; b++) {
      ViewHolder viewHolder = getChildViewHolderInt(this.mChildHelper.getUnfilteredChildAt(b));
      if (!viewHolder.shouldIgnore())
        viewHolder.saveOldPosition(); 
    } 
  }
  
  public void scrollBy(int paramInt1, int paramInt2) {
    LayoutManager layoutManager = this.mLayout;
    if (layoutManager == null) {
      Log.e("RecyclerView", "Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
      return;
    } 
    if (this.mLayoutFrozen)
      return; 
    boolean bool1 = layoutManager.canScrollHorizontally();
    boolean bool2 = this.mLayout.canScrollVertically();
    if (bool1 || bool2) {
      if (!bool1)
        paramInt1 = 0; 
      if (!bool2)
        paramInt2 = 0; 
      scrollByInternal(paramInt1, paramInt2, (MotionEvent)null);
    } 
  }
  
  boolean scrollByInternal(int paramInt1, int paramInt2, MotionEvent paramMotionEvent) {
    int[] arrayOfInt;
    boolean bool2;
    boolean bool3;
    boolean bool4;
    boolean bool5;
    consumePendingUpdateOperations();
    Adapter adapter = this.mAdapter;
    boolean bool1 = false;
    if (adapter != null) {
      startInterceptRequestLayout();
      onEnterLayoutOrScroll();
      TraceCompat.beginSection("RV Scroll");
      fillRemainingScrollValues(this.mState);
      if (paramInt1 != 0) {
        bool2 = this.mLayout.scrollHorizontallyBy(paramInt1, this.mRecycler, this.mState);
        bool3 = paramInt1 - bool2;
      } else {
        bool2 = false;
        bool3 = false;
      } 
      if (paramInt2 != 0) {
        bool4 = this.mLayout.scrollVerticallyBy(paramInt2, this.mRecycler, this.mState);
        bool5 = paramInt2 - bool4;
      } else {
        bool4 = false;
        bool5 = false;
      } 
      TraceCompat.endSection();
      repositionShadowingViews();
      onExitLayoutOrScroll();
      stopInterceptRequestLayout(false);
    } else {
      bool2 = false;
      bool3 = false;
      bool4 = false;
      bool5 = false;
    } 
    if (!this.mItemDecorations.isEmpty())
      invalidate(); 
    if (dispatchNestedScroll(bool2, bool4, bool3, bool5, this.mScrollOffset, 0)) {
      paramInt1 = this.mLastTouchX;
      int[] arrayOfInt1 = this.mScrollOffset;
      this.mLastTouchX = paramInt1 - arrayOfInt1[0];
      this.mLastTouchY -= arrayOfInt1[1];
      if (paramMotionEvent != null)
        paramMotionEvent.offsetLocation(arrayOfInt1[0], arrayOfInt1[1]); 
      arrayOfInt1 = this.mNestedOffsets;
      paramInt1 = arrayOfInt1[0];
      arrayOfInt = this.mScrollOffset;
      arrayOfInt1[0] = paramInt1 + arrayOfInt[0];
      arrayOfInt1[1] = arrayOfInt1[1] + arrayOfInt[1];
    } else if (getOverScrollMode() != 2) {
      if (arrayOfInt != null && !MotionEventCompat.isFromSource((MotionEvent)arrayOfInt, 8194))
        pullGlows(arrayOfInt.getX(), bool3, arrayOfInt.getY(), bool5); 
      considerReleasingGlowsOnScroll(paramInt1, paramInt2);
    } 
    if (bool2 || bool4)
      dispatchOnScrolled(bool2, bool4); 
    if (!awakenScrollBars())
      invalidate(); 
    if (bool2 || bool4)
      bool1 = true; 
    return bool1;
  }
  
  public void scrollTo(int paramInt1, int paramInt2) {
    Log.w("RecyclerView", "RecyclerView does not support scrolling to an absolute position. Use scrollToPosition instead");
  }
  
  public void scrollToPosition(int paramInt) {
    if (this.mLayoutFrozen)
      return; 
    stopScroll();
    LayoutManager layoutManager = this.mLayout;
    if (layoutManager == null) {
      Log.e("RecyclerView", "Cannot scroll to position a LayoutManager set. Call setLayoutManager with a non-null argument.");
      return;
    } 
    layoutManager.scrollToPosition(paramInt);
    awakenScrollBars();
  }
  
  public void sendAccessibilityEventUnchecked(AccessibilityEvent paramAccessibilityEvent) {
    if (shouldDeferAccessibilityEvent(paramAccessibilityEvent))
      return; 
    super.sendAccessibilityEventUnchecked(paramAccessibilityEvent);
  }
  
  public void setAccessibilityDelegateCompat(RecyclerViewAccessibilityDelegate paramRecyclerViewAccessibilityDelegate) {
    this.mAccessibilityDelegate = paramRecyclerViewAccessibilityDelegate;
    ViewCompat.setAccessibilityDelegate((View)this, paramRecyclerViewAccessibilityDelegate);
  }
  
  public void setAdapter(Adapter paramAdapter) {
    setLayoutFrozen(false);
    setAdapterInternal(paramAdapter, false, true);
    processDataSetCompletelyChanged(false);
    requestLayout();
  }
  
  public void setChildDrawingOrderCallback(ChildDrawingOrderCallback paramChildDrawingOrderCallback) {
    boolean bool;
    if (paramChildDrawingOrderCallback == this.mChildDrawingOrderCallback)
      return; 
    this.mChildDrawingOrderCallback = paramChildDrawingOrderCallback;
    if (paramChildDrawingOrderCallback != null) {
      bool = true;
    } else {
      bool = false;
    } 
    setChildrenDrawingOrderEnabled(bool);
  }
  
  boolean setChildImportantForAccessibilityInternal(ViewHolder paramViewHolder, int paramInt) {
    if (isComputingLayout()) {
      paramViewHolder.mPendingAccessibilityState = paramInt;
      this.mPendingAccessibilityImportanceChange.add(paramViewHolder);
      return false;
    } 
    ViewCompat.setImportantForAccessibility(paramViewHolder.itemView, paramInt);
    return true;
  }
  
  public void setClipToPadding(boolean paramBoolean) {
    if (paramBoolean != this.mClipToPadding)
      invalidateGlows(); 
    this.mClipToPadding = paramBoolean;
    super.setClipToPadding(paramBoolean);
    if (this.mFirstLayoutComplete)
      requestLayout(); 
  }
  
  public void setEdgeEffectFactory(EdgeEffectFactory paramEdgeEffectFactory) {
    Preconditions.checkNotNull(paramEdgeEffectFactory);
    this.mEdgeEffectFactory = paramEdgeEffectFactory;
    invalidateGlows();
  }
  
  public void setHasFixedSize(boolean paramBoolean) {
    this.mHasFixedSize = paramBoolean;
  }
  
  public void setItemAnimator(ItemAnimator paramItemAnimator) {
    ItemAnimator itemAnimator = this.mItemAnimator;
    if (itemAnimator != null) {
      itemAnimator.endAnimations();
      this.mItemAnimator.setListener(null);
    } 
    this.mItemAnimator = paramItemAnimator;
    if (paramItemAnimator != null)
      paramItemAnimator.setListener(this.mItemAnimatorListener); 
  }
  
  public void setItemViewCacheSize(int paramInt) {
    this.mRecycler.setViewCacheSize(paramInt);
  }
  
  public void setLayoutFrozen(boolean paramBoolean) {
    if (paramBoolean != this.mLayoutFrozen) {
      assertNotInLayoutOrScroll("Do not setLayoutFrozen in layout or scroll");
      if (!paramBoolean) {
        this.mLayoutFrozen = false;
        if (this.mLayoutWasDefered && this.mLayout != null && this.mAdapter != null)
          requestLayout(); 
        this.mLayoutWasDefered = false;
      } else {
        long l = SystemClock.uptimeMillis();
        onTouchEvent(MotionEvent.obtain(l, l, 3, 0.0F, 0.0F, 0));
        this.mLayoutFrozen = true;
        this.mIgnoreMotionEventTillDown = true;
        stopScroll();
      } 
    } 
  }
  
  public void setLayoutManager(LayoutManager paramLayoutManager) {
    if (paramLayoutManager == this.mLayout)
      return; 
    stopScroll();
    if (this.mLayout != null) {
      ItemAnimator itemAnimator = this.mItemAnimator;
      if (itemAnimator != null)
        itemAnimator.endAnimations(); 
      this.mLayout.removeAndRecycleAllViews(this.mRecycler);
      this.mLayout.removeAndRecycleScrapInt(this.mRecycler);
      this.mRecycler.clear();
      if (this.mIsAttached)
        this.mLayout.dispatchDetachedFromWindow(this, this.mRecycler); 
      this.mLayout.setRecyclerView(null);
      this.mLayout = null;
    } else {
      this.mRecycler.clear();
    } 
    this.mChildHelper.removeAllViewsUnfiltered();
    this.mLayout = paramLayoutManager;
    if (paramLayoutManager != null)
      if (paramLayoutManager.mRecyclerView == null) {
        this.mLayout.setRecyclerView(this);
        if (this.mIsAttached)
          this.mLayout.dispatchAttachedToWindow(this); 
      } else {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("LayoutManager ");
        stringBuilder.append(paramLayoutManager);
        stringBuilder.append(" is already attached to a RecyclerView:");
        stringBuilder.append(paramLayoutManager.mRecyclerView.exceptionLabel());
        throw new IllegalArgumentException(stringBuilder.toString());
      }  
    this.mRecycler.updateViewCacheSize();
    requestLayout();
  }
  
  public void setNestedScrollingEnabled(boolean paramBoolean) {
    getScrollingChildHelper().setNestedScrollingEnabled(paramBoolean);
  }
  
  public void setOnFlingListener(OnFlingListener paramOnFlingListener) {
    this.mOnFlingListener = paramOnFlingListener;
  }
  
  @Deprecated
  public void setOnScrollListener(OnScrollListener paramOnScrollListener) {
    this.mScrollListener = paramOnScrollListener;
  }
  
  public void setPreserveFocusAfterLayout(boolean paramBoolean) {
    this.mPreserveFocusAfterLayout = paramBoolean;
  }
  
  public void setRecycledViewPool(RecycledViewPool paramRecycledViewPool) {
    this.mRecycler.setRecycledViewPool(paramRecycledViewPool);
  }
  
  public void setRecyclerListener(RecyclerListener paramRecyclerListener) {
    this.mRecyclerListener = paramRecyclerListener;
  }
  
  void setScrollState(int paramInt) {
    if (paramInt == this.mScrollState)
      return; 
    this.mScrollState = paramInt;
    if (paramInt != 2)
      stopScrollersInternal(); 
    dispatchOnScrollStateChanged(paramInt);
  }
  
  public void setScrollingTouchSlop(int paramInt) {
    ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
    if (paramInt != 0)
      if (paramInt != 1) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setScrollingTouchSlop(): bad argument constant ");
        stringBuilder.append(paramInt);
        stringBuilder.append("; using default value");
        Log.w("RecyclerView", stringBuilder.toString());
      } else {
        this.mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
        return;
      }  
    this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
  }
  
  public void setViewCacheExtension(ViewCacheExtension paramViewCacheExtension) {
    this.mRecycler.setViewCacheExtension(paramViewCacheExtension);
  }
  
  boolean shouldDeferAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent) {
    boolean bool = isComputingLayout();
    boolean bool1 = false;
    if (bool) {
      boolean bool2;
      if (paramAccessibilityEvent != null) {
        bool2 = AccessibilityEventCompat.getContentChangeTypes(paramAccessibilityEvent);
      } else {
        bool2 = false;
      } 
      if (!bool2)
        bool2 = bool1; 
      this.mEatenAccessibilityChangeFlags |= bool2;
      return true;
    } 
    return false;
  }
  
  public void smoothScrollBy(int paramInt1, int paramInt2) {
    smoothScrollBy(paramInt1, paramInt2, (Interpolator)null);
  }
  
  public void smoothScrollBy(int paramInt1, int paramInt2, Interpolator paramInterpolator) {
    LayoutManager layoutManager = this.mLayout;
    if (layoutManager == null) {
      Log.e("RecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
      return;
    } 
    if (this.mLayoutFrozen)
      return; 
    if (!layoutManager.canScrollHorizontally())
      paramInt1 = 0; 
    if (!this.mLayout.canScrollVertically())
      paramInt2 = 0; 
    if (paramInt1 != 0 || paramInt2 != 0)
      this.mViewFlinger.smoothScrollBy(paramInt1, paramInt2, paramInterpolator); 
  }
  
  public void smoothScrollToPosition(int paramInt) {
    if (this.mLayoutFrozen)
      return; 
    LayoutManager layoutManager = this.mLayout;
    if (layoutManager == null) {
      Log.e("RecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
      return;
    } 
    layoutManager.smoothScrollToPosition(this, this.mState, paramInt);
  }
  
  void startInterceptRequestLayout() {
    int i = this.mInterceptRequestLayoutDepth + 1;
    this.mInterceptRequestLayoutDepth = i;
    if (i == 1 && !this.mLayoutFrozen)
      this.mLayoutWasDefered = false; 
  }
  
  public boolean startNestedScroll(int paramInt) {
    return getScrollingChildHelper().startNestedScroll(paramInt);
  }
  
  public boolean startNestedScroll(int paramInt1, int paramInt2) {
    return getScrollingChildHelper().startNestedScroll(paramInt1, paramInt2);
  }
  
  void stopInterceptRequestLayout(boolean paramBoolean) {
    if (this.mInterceptRequestLayoutDepth < 1)
      this.mInterceptRequestLayoutDepth = 1; 
    if (!paramBoolean && !this.mLayoutFrozen)
      this.mLayoutWasDefered = false; 
    if (this.mInterceptRequestLayoutDepth == 1) {
      if (paramBoolean && this.mLayoutWasDefered && !this.mLayoutFrozen && this.mLayout != null && this.mAdapter != null)
        dispatchLayout(); 
      if (!this.mLayoutFrozen)
        this.mLayoutWasDefered = false; 
    } 
    this.mInterceptRequestLayoutDepth--;
  }
  
  public void stopNestedScroll() {
    getScrollingChildHelper().stopNestedScroll();
  }
  
  public void stopNestedScroll(int paramInt) {
    getScrollingChildHelper().stopNestedScroll(paramInt);
  }
  
  public void stopScroll() {
    setScrollState(0);
    stopScrollersInternal();
  }
  
  public void swapAdapter(Adapter paramAdapter, boolean paramBoolean) {
    setLayoutFrozen(false);
    setAdapterInternal(paramAdapter, true, paramBoolean);
    processDataSetCompletelyChanged(true);
    requestLayout();
  }
  
  void viewRangeUpdate(int paramInt1, int paramInt2, Object paramObject) {
    int i = this.mChildHelper.getUnfilteredChildCount();
    for (byte b = 0; b < i; b++) {
      View view = this.mChildHelper.getUnfilteredChildAt(b);
      ViewHolder viewHolder = getChildViewHolderInt(view);
      if (viewHolder != null && !viewHolder.shouldIgnore() && viewHolder.mPosition >= paramInt1 && viewHolder.mPosition < paramInt1 + paramInt2) {
        viewHolder.addFlags(2);
        viewHolder.addChangePayload(paramObject);
        ((LayoutParams)view.getLayoutParams()).mInsetsDirty = true;
      } 
    } 
    this.mRecycler.viewRangeUpdate(paramInt1, paramInt2);
  }
  
  static {
    boolean bool;
  }
  
  static final boolean ALLOW_SIZE_IN_UNSPECIFIED_SPEC;
  
  private static final boolean ALLOW_THREAD_GAP_WORK;
  
  private static final int[] CLIP_TO_PADDING_ATTR;
  
  static final boolean DEBUG = false;
  
  static final int DEFAULT_ORIENTATION = 1;
  
  static final boolean DISPATCH_TEMP_DETACH = false;
  
  private static final boolean FORCE_ABS_FOCUS_SEARCH_DIRECTION;
  
  static final boolean FORCE_INVALIDATE_DISPLAY_LIST;
  
  static final long FOREVER_NS = 9223372036854775807L;
  
  public static final int HORIZONTAL = 0;
  
  private static final boolean IGNORE_DETACHED_FOCUSED_CHILD;
  
  private static final int INVALID_POINTER = -1;
  
  public static final int INVALID_TYPE = -1;
  
  private static final Class<?>[] LAYOUT_MANAGER_CONSTRUCTOR_SIGNATURE;
  
  static final int MAX_SCROLL_DURATION = 2000;
  
  private static final int[] NESTED_SCROLLING_ATTRS = new int[] { 16843830 };
  
  public static final long NO_ID = -1L;
  
  public static final int NO_POSITION = -1;
  
  static final boolean POST_UPDATES_ON_ANIMATION;
  
  public static final int SCROLL_STATE_DRAGGING = 1;
  
  public static final int SCROLL_STATE_IDLE = 0;
  
  public static final int SCROLL_STATE_SETTLING = 2;
  
  static final String TAG = "RecyclerView";
  
  public static final int TOUCH_SLOP_DEFAULT = 0;
  
  public static final int TOUCH_SLOP_PAGING = 1;
  
  static final String TRACE_BIND_VIEW_TAG = "RV OnBindView";
  
  static final String TRACE_CREATE_VIEW_TAG = "RV CreateView";
  
  private static final String TRACE_HANDLE_ADAPTER_UPDATES_TAG = "RV PartialInvalidate";
  
  static final String TRACE_NESTED_PREFETCH_TAG = "RV Nested Prefetch";
  
  private static final String TRACE_ON_DATA_SET_CHANGE_LAYOUT_TAG = "RV FullInvalidate";
  
  private static final String TRACE_ON_LAYOUT_TAG = "RV OnLayout";
  
  static final String TRACE_PREFETCH_TAG = "RV Prefetch";
  
  static final String TRACE_SCROLL_TAG = "RV Scroll";
  
  static final boolean VERBOSE_TRACING = false;
  
  public static final int VERTICAL = 1;
  
  static final Interpolator sQuinticInterpolator;
  
  RecyclerViewAccessibilityDelegate mAccessibilityDelegate;
  
  private final AccessibilityManager mAccessibilityManager;
  
  private OnItemTouchListener mActiveOnItemTouchListener;
  
  Adapter mAdapter;
  
  AdapterHelper mAdapterHelper;
  
  boolean mAdapterUpdateDuringMeasure;
  
  private EdgeEffect mBottomGlow;
  
  private ChildDrawingOrderCallback mChildDrawingOrderCallback;
  
  ChildHelper mChildHelper;
  
  boolean mClipToPadding;
  
  boolean mDataSetHasChangedAfterLayout;
  
  boolean mDispatchItemsChangedEvent;
  
  private int mDispatchScrollCounter;
  
  private int mEatenAccessibilityChangeFlags;
  
  private EdgeEffectFactory mEdgeEffectFactory;
  
  boolean mEnableFastScroller;
  
  boolean mFirstLayoutComplete;
  
  GapWorker mGapWorker;
  
  boolean mHasFixedSize;
  
  private boolean mIgnoreMotionEventTillDown;
  
  private int mInitialTouchX;
  
  private int mInitialTouchY;
  
  private int mInterceptRequestLayoutDepth;
  
  boolean mIsAttached;
  
  ItemAnimator mItemAnimator;
  
  private ItemAnimator.ItemAnimatorListener mItemAnimatorListener;
  
  private Runnable mItemAnimatorRunner;
  
  final ArrayList<ItemDecoration> mItemDecorations;
  
  boolean mItemsAddedOrRemoved;
  
  boolean mItemsChanged;
  
  private int mLastTouchX;
  
  private int mLastTouchY;
  
  LayoutManager mLayout;
  
  boolean mLayoutFrozen;
  
  private int mLayoutOrScrollCounter;
  
  boolean mLayoutWasDefered;
  
  private EdgeEffect mLeftGlow;
  
  private final int mMaxFlingVelocity;
  
  private final int mMinFlingVelocity;
  
  private final int[] mMinMaxLayoutPositions;
  
  private final int[] mNestedOffsets;
  
  private final RecyclerViewDataObserver mObserver;
  
  private List<OnChildAttachStateChangeListener> mOnChildAttachStateListeners;
  
  private OnFlingListener mOnFlingListener;
  
  private final ArrayList<OnItemTouchListener> mOnItemTouchListeners;
  
  final List<ViewHolder> mPendingAccessibilityImportanceChange;
  
  private SavedState mPendingSavedState;
  
  boolean mPostedAnimatorRunner;
  
  GapWorker.LayoutPrefetchRegistryImpl mPrefetchRegistry;
  
  private boolean mPreserveFocusAfterLayout;
  
  final Recycler mRecycler;
  
  RecyclerListener mRecyclerListener;
  
  private EdgeEffect mRightGlow;
  
  private float mScaledHorizontalScrollFactor;
  
  private float mScaledVerticalScrollFactor;
  
  private final int[] mScrollConsumed;
  
  private OnScrollListener mScrollListener;
  
  private List<OnScrollListener> mScrollListeners;
  
  private final int[] mScrollOffset;
  
  private int mScrollPointerId;
  
  private int mScrollState;
  
  private NestedScrollingChildHelper mScrollingChildHelper;
  
  final State mState;
  
  final Rect mTempRect;
  
  private final Rect mTempRect2;
  
  final RectF mTempRectF;
  
  private EdgeEffect mTopGlow;
  
  private int mTouchSlop;
  
  final Runnable mUpdateChildViewsRunnable;
  
  private VelocityTracker mVelocityTracker;
  
  final ViewFlinger mViewFlinger;
  
  private final ViewInfoStore.ProcessCallback mViewInfoProcessCallback;
  
  final ViewInfoStore mViewInfoStore;
  
  public static abstract class Adapter<VH extends ViewHolder> {
    private boolean mHasStableIds = false;
    
    private final RecyclerView.AdapterDataObservable mObservable = new RecyclerView.AdapterDataObservable();
    
    public final void bindViewHolder(VH param1VH, int param1Int) {
      ((RecyclerView.ViewHolder)param1VH).mPosition = param1Int;
      if (hasStableIds())
        ((RecyclerView.ViewHolder)param1VH).mItemId = getItemId(param1Int); 
      param1VH.setFlags(1, 519);
      TraceCompat.beginSection("RV OnBindView");
      onBindViewHolder(param1VH, param1Int, param1VH.getUnmodifiedPayloads());
      param1VH.clearPayload();
      ViewGroup.LayoutParams layoutParams = ((RecyclerView.ViewHolder)param1VH).itemView.getLayoutParams();
      if (layoutParams instanceof RecyclerView.LayoutParams)
        ((RecyclerView.LayoutParams)layoutParams).mInsetsDirty = true; 
      TraceCompat.endSection();
    }
    
    public final VH createViewHolder(ViewGroup param1ViewGroup, int param1Int) {
      try {
        TraceCompat.beginSection("RV CreateView");
        param1ViewGroup = (ViewGroup)onCreateViewHolder(param1ViewGroup, param1Int);
        if (((RecyclerView.ViewHolder)param1ViewGroup).itemView.getParent() == null) {
          ((RecyclerView.ViewHolder)param1ViewGroup).mItemViewType = param1Int;
          return (VH)param1ViewGroup;
        } 
        IllegalStateException illegalStateException = new IllegalStateException();
        this("ViewHolder views must not be attached when created. Ensure that you are not passing 'true' to the attachToRoot parameter of LayoutInflater.inflate(..., boolean attachToRoot)");
        throw illegalStateException;
      } finally {
        TraceCompat.endSection();
      } 
    }
    
    public abstract int getItemCount();
    
    public long getItemId(int param1Int) {
      return -1L;
    }
    
    public int getItemViewType(int param1Int) {
      return 0;
    }
    
    public final boolean hasObservers() {
      return this.mObservable.hasObservers();
    }
    
    public final boolean hasStableIds() {
      return this.mHasStableIds;
    }
    
    public final void notifyDataSetChanged() {
      this.mObservable.notifyChanged();
    }
    
    public final void notifyItemChanged(int param1Int) {
      this.mObservable.notifyItemRangeChanged(param1Int, 1);
    }
    
    public final void notifyItemChanged(int param1Int, Object param1Object) {
      this.mObservable.notifyItemRangeChanged(param1Int, 1, param1Object);
    }
    
    public final void notifyItemInserted(int param1Int) {
      this.mObservable.notifyItemRangeInserted(param1Int, 1);
    }
    
    public final void notifyItemMoved(int param1Int1, int param1Int2) {
      this.mObservable.notifyItemMoved(param1Int1, param1Int2);
    }
    
    public final void notifyItemRangeChanged(int param1Int1, int param1Int2) {
      this.mObservable.notifyItemRangeChanged(param1Int1, param1Int2);
    }
    
    public final void notifyItemRangeChanged(int param1Int1, int param1Int2, Object param1Object) {
      this.mObservable.notifyItemRangeChanged(param1Int1, param1Int2, param1Object);
    }
    
    public final void notifyItemRangeInserted(int param1Int1, int param1Int2) {
      this.mObservable.notifyItemRangeInserted(param1Int1, param1Int2);
    }
    
    public final void notifyItemRangeRemoved(int param1Int1, int param1Int2) {
      this.mObservable.notifyItemRangeRemoved(param1Int1, param1Int2);
    }
    
    public final void notifyItemRemoved(int param1Int) {
      this.mObservable.notifyItemRangeRemoved(param1Int, 1);
    }
    
    public void onAttachedToRecyclerView(RecyclerView param1RecyclerView) {}
    
    public abstract void onBindViewHolder(VH param1VH, int param1Int);
    
    public void onBindViewHolder(VH param1VH, int param1Int, List<Object> param1List) {
      onBindViewHolder(param1VH, param1Int);
    }
    
    public abstract VH onCreateViewHolder(ViewGroup param1ViewGroup, int param1Int);
    
    public void onDetachedFromRecyclerView(RecyclerView param1RecyclerView) {}
    
    public boolean onFailedToRecycleView(VH param1VH) {
      return false;
    }
    
    public void onViewAttachedToWindow(VH param1VH) {}
    
    public void onViewDetachedFromWindow(VH param1VH) {}
    
    public void onViewRecycled(VH param1VH) {}
    
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver param1AdapterDataObserver) {
      this.mObservable.registerObserver(param1AdapterDataObserver);
    }
    
    public void setHasStableIds(boolean param1Boolean) {
      if (!hasObservers()) {
        this.mHasStableIds = param1Boolean;
        return;
      } 
      throw new IllegalStateException("Cannot change whether this adapter has stable IDs while the adapter has registered observers.");
    }
    
    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver param1AdapterDataObserver) {
      this.mObservable.unregisterObserver(param1AdapterDataObserver);
    }
  }
  
  static class AdapterDataObservable extends Observable<AdapterDataObserver> {
    public boolean hasObservers() {
      return this.mObservers.isEmpty() ^ true;
    }
    
    public void notifyChanged() {
      for (int i = this.mObservers.size() - 1; i >= 0; i--)
        ((RecyclerView.AdapterDataObserver)this.mObservers.get(i)).onChanged(); 
    }
    
    public void notifyItemMoved(int param1Int1, int param1Int2) {
      for (int i = this.mObservers.size() - 1; i >= 0; i--)
        ((RecyclerView.AdapterDataObserver)this.mObservers.get(i)).onItemRangeMoved(param1Int1, param1Int2, 1); 
    }
    
    public void notifyItemRangeChanged(int param1Int1, int param1Int2) {
      notifyItemRangeChanged(param1Int1, param1Int2, (Object)null);
    }
    
    public void notifyItemRangeChanged(int param1Int1, int param1Int2, Object param1Object) {
      for (int i = this.mObservers.size() - 1; i >= 0; i--)
        ((RecyclerView.AdapterDataObserver)this.mObservers.get(i)).onItemRangeChanged(param1Int1, param1Int2, param1Object); 
    }
    
    public void notifyItemRangeInserted(int param1Int1, int param1Int2) {
      for (int i = this.mObservers.size() - 1; i >= 0; i--)
        ((RecyclerView.AdapterDataObserver)this.mObservers.get(i)).onItemRangeInserted(param1Int1, param1Int2); 
    }
    
    public void notifyItemRangeRemoved(int param1Int1, int param1Int2) {
      for (int i = this.mObservers.size() - 1; i >= 0; i--)
        ((RecyclerView.AdapterDataObserver)this.mObservers.get(i)).onItemRangeRemoved(param1Int1, param1Int2); 
    }
  }
  
  public static abstract class AdapterDataObserver {
    public void onChanged() {}
    
    public void onItemRangeChanged(int param1Int1, int param1Int2) {}
    
    public void onItemRangeChanged(int param1Int1, int param1Int2, Object param1Object) {
      onItemRangeChanged(param1Int1, param1Int2);
    }
    
    public void onItemRangeInserted(int param1Int1, int param1Int2) {}
    
    public void onItemRangeMoved(int param1Int1, int param1Int2, int param1Int3) {}
    
    public void onItemRangeRemoved(int param1Int1, int param1Int2) {}
  }
  
  public static interface ChildDrawingOrderCallback {
    int onGetChildDrawingOrder(int param1Int1, int param1Int2);
  }
  
  public static class EdgeEffectFactory {
    public static final int DIRECTION_BOTTOM = 3;
    
    public static final int DIRECTION_LEFT = 0;
    
    public static final int DIRECTION_RIGHT = 2;
    
    public static final int DIRECTION_TOP = 1;
    
    protected EdgeEffect createEdgeEffect(RecyclerView param1RecyclerView, int param1Int) {
      return new EdgeEffect(param1RecyclerView.getContext());
    }
    
    @Retention(RetentionPolicy.SOURCE)
    public static @interface EdgeDirection {}
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface EdgeDirection {}
  
  public static abstract class ItemAnimator {
    public static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
    
    public static final int FLAG_CHANGED = 2;
    
    public static final int FLAG_INVALIDATED = 4;
    
    public static final int FLAG_MOVED = 2048;
    
    public static final int FLAG_REMOVED = 8;
    
    private long mAddDuration = 120L;
    
    private long mChangeDuration = 250L;
    
    private ArrayList<ItemAnimatorFinishedListener> mFinishedListeners = new ArrayList<ItemAnimatorFinishedListener>();
    
    private ItemAnimatorListener mListener = null;
    
    private long mMoveDuration = 250L;
    
    private long mRemoveDuration = 120L;
    
    static int buildAdapterChangeFlagsForAnimations(RecyclerView.ViewHolder param1ViewHolder) {
      int i = param1ViewHolder.mFlags & 0xE;
      if (param1ViewHolder.isInvalid())
        return 4; 
      int j = i;
      if ((i & 0x4) == 0) {
        int k = param1ViewHolder.getOldPosition();
        int m = param1ViewHolder.getAdapterPosition();
        j = i;
        if (k != -1) {
          j = i;
          if (m != -1) {
            j = i;
            if (k != m)
              j = i | 0x800; 
          } 
        } 
      } 
      return j;
    }
    
    public abstract boolean animateAppearance(RecyclerView.ViewHolder param1ViewHolder, ItemHolderInfo param1ItemHolderInfo1, ItemHolderInfo param1ItemHolderInfo2);
    
    public abstract boolean animateChange(RecyclerView.ViewHolder param1ViewHolder1, RecyclerView.ViewHolder param1ViewHolder2, ItemHolderInfo param1ItemHolderInfo1, ItemHolderInfo param1ItemHolderInfo2);
    
    public abstract boolean animateDisappearance(RecyclerView.ViewHolder param1ViewHolder, ItemHolderInfo param1ItemHolderInfo1, ItemHolderInfo param1ItemHolderInfo2);
    
    public abstract boolean animatePersistence(RecyclerView.ViewHolder param1ViewHolder, ItemHolderInfo param1ItemHolderInfo1, ItemHolderInfo param1ItemHolderInfo2);
    
    public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder param1ViewHolder) {
      return true;
    }
    
    public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder param1ViewHolder, List<Object> param1List) {
      return canReuseUpdatedViewHolder(param1ViewHolder);
    }
    
    public final void dispatchAnimationFinished(RecyclerView.ViewHolder param1ViewHolder) {
      onAnimationFinished(param1ViewHolder);
      ItemAnimatorListener itemAnimatorListener = this.mListener;
      if (itemAnimatorListener != null)
        itemAnimatorListener.onAnimationFinished(param1ViewHolder); 
    }
    
    public final void dispatchAnimationStarted(RecyclerView.ViewHolder param1ViewHolder) {
      onAnimationStarted(param1ViewHolder);
    }
    
    public final void dispatchAnimationsFinished() {
      int i = this.mFinishedListeners.size();
      for (byte b = 0; b < i; b++)
        ((ItemAnimatorFinishedListener)this.mFinishedListeners.get(b)).onAnimationsFinished(); 
      this.mFinishedListeners.clear();
    }
    
    public abstract void endAnimation(RecyclerView.ViewHolder param1ViewHolder);
    
    public abstract void endAnimations();
    
    public long getAddDuration() {
      return this.mAddDuration;
    }
    
    public long getChangeDuration() {
      return this.mChangeDuration;
    }
    
    public long getMoveDuration() {
      return this.mMoveDuration;
    }
    
    public long getRemoveDuration() {
      return this.mRemoveDuration;
    }
    
    public abstract boolean isRunning();
    
    public final boolean isRunning(ItemAnimatorFinishedListener param1ItemAnimatorFinishedListener) {
      boolean bool = isRunning();
      if (param1ItemAnimatorFinishedListener != null)
        if (!bool) {
          param1ItemAnimatorFinishedListener.onAnimationsFinished();
        } else {
          this.mFinishedListeners.add(param1ItemAnimatorFinishedListener);
        }  
      return bool;
    }
    
    public ItemHolderInfo obtainHolderInfo() {
      return new ItemHolderInfo();
    }
    
    public void onAnimationFinished(RecyclerView.ViewHolder param1ViewHolder) {}
    
    public void onAnimationStarted(RecyclerView.ViewHolder param1ViewHolder) {}
    
    public ItemHolderInfo recordPostLayoutInformation(RecyclerView.State param1State, RecyclerView.ViewHolder param1ViewHolder) {
      return obtainHolderInfo().setFrom(param1ViewHolder);
    }
    
    public ItemHolderInfo recordPreLayoutInformation(RecyclerView.State param1State, RecyclerView.ViewHolder param1ViewHolder, int param1Int, List<Object> param1List) {
      return obtainHolderInfo().setFrom(param1ViewHolder);
    }
    
    public abstract void runPendingAnimations();
    
    public void setAddDuration(long param1Long) {
      this.mAddDuration = param1Long;
    }
    
    public void setChangeDuration(long param1Long) {
      this.mChangeDuration = param1Long;
    }
    
    void setListener(ItemAnimatorListener param1ItemAnimatorListener) {
      this.mListener = param1ItemAnimatorListener;
    }
    
    public void setMoveDuration(long param1Long) {
      this.mMoveDuration = param1Long;
    }
    
    public void setRemoveDuration(long param1Long) {
      this.mRemoveDuration = param1Long;
    }
    
    @Retention(RetentionPolicy.SOURCE)
    public static @interface AdapterChanges {}
    
    public static interface ItemAnimatorFinishedListener {
      void onAnimationsFinished();
    }
    
    static interface ItemAnimatorListener {
      void onAnimationFinished(RecyclerView.ViewHolder param2ViewHolder);
    }
    
    public static class ItemHolderInfo {
      public int bottom;
      
      public int changeFlags;
      
      public int left;
      
      public int right;
      
      public int top;
      
      public ItemHolderInfo setFrom(RecyclerView.ViewHolder param2ViewHolder) {
        return setFrom(param2ViewHolder, 0);
      }
      
      public ItemHolderInfo setFrom(RecyclerView.ViewHolder param2ViewHolder, int param2Int) {
        View view = param2ViewHolder.itemView;
        this.left = view.getLeft();
        this.top = view.getTop();
        this.right = view.getRight();
        this.bottom = view.getBottom();
        return this;
      }
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface AdapterChanges {}
  
  public static interface ItemAnimatorFinishedListener {
    void onAnimationsFinished();
  }
  
  static interface ItemAnimatorListener {
    void onAnimationFinished(RecyclerView.ViewHolder param1ViewHolder);
  }
  
  public static class ItemHolderInfo {
    public int bottom;
    
    public int changeFlags;
    
    public int left;
    
    public int right;
    
    public int top;
    
    public ItemHolderInfo setFrom(RecyclerView.ViewHolder param1ViewHolder) {
      return setFrom(param1ViewHolder, 0);
    }
    
    public ItemHolderInfo setFrom(RecyclerView.ViewHolder param1ViewHolder, int param1Int) {
      View view = param1ViewHolder.itemView;
      this.left = view.getLeft();
      this.top = view.getTop();
      this.right = view.getRight();
      this.bottom = view.getBottom();
      return this;
    }
  }
  
  private class ItemAnimatorRestoreListener implements ItemAnimator.ItemAnimatorListener {
    public void onAnimationFinished(RecyclerView.ViewHolder param1ViewHolder) {
      param1ViewHolder.setIsRecyclable(true);
      if (param1ViewHolder.mShadowedHolder != null && param1ViewHolder.mShadowingHolder == null)
        param1ViewHolder.mShadowedHolder = null; 
      param1ViewHolder.mShadowingHolder = null;
      if (!param1ViewHolder.shouldBeKeptAsChild() && !RecyclerView.this.removeAnimatingView(param1ViewHolder.itemView) && param1ViewHolder.isTmpDetached())
        RecyclerView.this.removeDetachedView(param1ViewHolder.itemView, false); 
    }
  }
  
  public static abstract class ItemDecoration {
    @Deprecated
    public void getItemOffsets(Rect param1Rect, int param1Int, RecyclerView param1RecyclerView) {
      param1Rect.set(0, 0, 0, 0);
    }
    
    public void getItemOffsets(Rect param1Rect, View param1View, RecyclerView param1RecyclerView, RecyclerView.State param1State) {
      getItemOffsets(param1Rect, ((RecyclerView.LayoutParams)param1View.getLayoutParams()).getViewLayoutPosition(), param1RecyclerView);
    }
    
    @Deprecated
    public void onDraw(Canvas param1Canvas, RecyclerView param1RecyclerView) {}
    
    public void onDraw(Canvas param1Canvas, RecyclerView param1RecyclerView, RecyclerView.State param1State) {
      onDraw(param1Canvas, param1RecyclerView);
    }
    
    @Deprecated
    public void onDrawOver(Canvas param1Canvas, RecyclerView param1RecyclerView) {}
    
    public void onDrawOver(Canvas param1Canvas, RecyclerView param1RecyclerView, RecyclerView.State param1State) {
      onDrawOver(param1Canvas, param1RecyclerView);
    }
  }
  
  public static abstract class LayoutManager {
    boolean mAutoMeasure = false;
    
    ChildHelper mChildHelper;
    
    private int mHeight;
    
    private int mHeightMode;
    
    ViewBoundsCheck mHorizontalBoundCheck = new ViewBoundsCheck(this.mHorizontalBoundCheckCallback);
    
    private final ViewBoundsCheck.Callback mHorizontalBoundCheckCallback = new ViewBoundsCheck.Callback() {
        public View getChildAt(int param2Int) {
          return RecyclerView.LayoutManager.this.getChildAt(param2Int);
        }
        
        public int getChildCount() {
          return RecyclerView.LayoutManager.this.getChildCount();
        }
        
        public int getChildEnd(View param2View) {
          RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)param2View.getLayoutParams();
          return RecyclerView.LayoutManager.this.getDecoratedRight(param2View) + layoutParams.rightMargin;
        }
        
        public int getChildStart(View param2View) {
          RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)param2View.getLayoutParams();
          return RecyclerView.LayoutManager.this.getDecoratedLeft(param2View) - layoutParams.leftMargin;
        }
        
        public View getParent() {
          return (View)RecyclerView.LayoutManager.this.mRecyclerView;
        }
        
        public int getParentEnd() {
          return RecyclerView.LayoutManager.this.getWidth() - RecyclerView.LayoutManager.this.getPaddingRight();
        }
        
        public int getParentStart() {
          return RecyclerView.LayoutManager.this.getPaddingLeft();
        }
      };
    
    boolean mIsAttachedToWindow = false;
    
    private boolean mItemPrefetchEnabled = true;
    
    private boolean mMeasurementCacheEnabled = true;
    
    int mPrefetchMaxCountObserved;
    
    boolean mPrefetchMaxObservedInInitialPrefetch;
    
    RecyclerView mRecyclerView;
    
    boolean mRequestedSimpleAnimations = false;
    
    RecyclerView.SmoothScroller mSmoothScroller;
    
    ViewBoundsCheck mVerticalBoundCheck = new ViewBoundsCheck(this.mVerticalBoundCheckCallback);
    
    private final ViewBoundsCheck.Callback mVerticalBoundCheckCallback = new ViewBoundsCheck.Callback() {
        public View getChildAt(int param2Int) {
          return RecyclerView.LayoutManager.this.getChildAt(param2Int);
        }
        
        public int getChildCount() {
          return RecyclerView.LayoutManager.this.getChildCount();
        }
        
        public int getChildEnd(View param2View) {
          RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)param2View.getLayoutParams();
          return RecyclerView.LayoutManager.this.getDecoratedBottom(param2View) + layoutParams.bottomMargin;
        }
        
        public int getChildStart(View param2View) {
          RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)param2View.getLayoutParams();
          return RecyclerView.LayoutManager.this.getDecoratedTop(param2View) - layoutParams.topMargin;
        }
        
        public View getParent() {
          return (View)RecyclerView.LayoutManager.this.mRecyclerView;
        }
        
        public int getParentEnd() {
          return RecyclerView.LayoutManager.this.getHeight() - RecyclerView.LayoutManager.this.getPaddingBottom();
        }
        
        public int getParentStart() {
          return RecyclerView.LayoutManager.this.getPaddingTop();
        }
      };
    
    private int mWidth;
    
    private int mWidthMode;
    
    private void addViewInt(View param1View, int param1Int, boolean param1Boolean) {
      StringBuilder stringBuilder;
      RecyclerView.ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(param1View);
      if (param1Boolean || viewHolder.isRemoved()) {
        this.mRecyclerView.mViewInfoStore.addToDisappearedInLayout(viewHolder);
      } else {
        this.mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout(viewHolder);
      } 
      RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)param1View.getLayoutParams();
      if (viewHolder.wasReturnedFromScrap() || viewHolder.isScrap()) {
        if (viewHolder.isScrap()) {
          viewHolder.unScrap();
        } else {
          viewHolder.clearReturnedFromScrapFlag();
        } 
        this.mChildHelper.attachViewToParent(param1View, param1Int, param1View.getLayoutParams(), false);
      } else if (param1View.getParent() == this.mRecyclerView) {
        int i = this.mChildHelper.indexOfChild(param1View);
        int j = param1Int;
        if (param1Int == -1)
          j = this.mChildHelper.getChildCount(); 
        if (i != -1) {
          if (i != j)
            this.mRecyclerView.mLayout.moveView(i, j); 
        } else {
          stringBuilder = new StringBuilder();
          stringBuilder.append("Added View has RecyclerView as parent but view is not a real child. Unfiltered index:");
          stringBuilder.append(this.mRecyclerView.indexOfChild(param1View));
          stringBuilder.append(this.mRecyclerView.exceptionLabel());
          throw new IllegalStateException(stringBuilder.toString());
        } 
      } else {
        this.mChildHelper.addView(param1View, param1Int, false);
        layoutParams.mInsetsDirty = true;
        RecyclerView.SmoothScroller smoothScroller = this.mSmoothScroller;
        if (smoothScroller != null && smoothScroller.isRunning())
          this.mSmoothScroller.onChildAttachedToWindow(param1View); 
      } 
      if (layoutParams.mPendingInvalidate) {
        ((RecyclerView.ViewHolder)stringBuilder).itemView.invalidate();
        layoutParams.mPendingInvalidate = false;
      } 
    }
    
    public static int chooseSize(int param1Int1, int param1Int2, int param1Int3) {
      int i = View.MeasureSpec.getMode(param1Int1);
      param1Int1 = View.MeasureSpec.getSize(param1Int1);
      if (i != Integer.MIN_VALUE) {
        if (i != 1073741824)
          param1Int1 = Math.max(param1Int2, param1Int3); 
        return param1Int1;
      } 
      return Math.min(param1Int1, Math.max(param1Int2, param1Int3));
    }
    
    private void detachViewInternal(int param1Int, View param1View) {
      this.mChildHelper.detachViewFromParent(param1Int);
    }
    
    public static int getChildMeasureSpec(int param1Int1, int param1Int2, int param1Int3, int param1Int4, boolean param1Boolean) {
      // Byte code:
      //   0: iconst_0
      //   1: iload_0
      //   2: iload_2
      //   3: isub
      //   4: invokestatic max : (II)I
      //   7: istore_2
      //   8: iload #4
      //   10: ifeq -> 48
      //   13: iload_3
      //   14: iflt -> 20
      //   17: goto -> 52
      //   20: iload_3
      //   21: iconst_m1
      //   22: if_icmpne -> 102
      //   25: iload_1
      //   26: istore_0
      //   27: iload_1
      //   28: ldc -2147483648
      //   30: if_icmpeq -> 65
      //   33: iload_1
      //   34: ifeq -> 102
      //   37: iload_1
      //   38: istore_0
      //   39: iload_1
      //   40: ldc 1073741824
      //   42: if_icmpeq -> 65
      //   45: goto -> 102
      //   48: iload_3
      //   49: iflt -> 58
      //   52: ldc 1073741824
      //   54: istore_0
      //   55: goto -> 106
      //   58: iload_3
      //   59: iconst_m1
      //   60: if_icmpne -> 70
      //   63: iload_1
      //   64: istore_0
      //   65: iload_2
      //   66: istore_3
      //   67: goto -> 106
      //   70: iload_3
      //   71: bipush #-2
      //   73: if_icmpne -> 102
      //   76: iload_1
      //   77: ldc -2147483648
      //   79: if_icmpeq -> 96
      //   82: iload_1
      //   83: ldc 1073741824
      //   85: if_icmpne -> 91
      //   88: goto -> 96
      //   91: iconst_0
      //   92: istore_0
      //   93: goto -> 65
      //   96: ldc -2147483648
      //   98: istore_0
      //   99: goto -> 65
      //   102: iconst_0
      //   103: istore_0
      //   104: iconst_0
      //   105: istore_3
      //   106: iload_3
      //   107: iload_0
      //   108: invokestatic makeMeasureSpec : (II)I
      //   111: ireturn
    }
    
    @Deprecated
    public static int getChildMeasureSpec(int param1Int1, int param1Int2, int param1Int3, boolean param1Boolean) {
      // Byte code:
      //   0: iconst_0
      //   1: istore #4
      //   3: iconst_0
      //   4: iload_0
      //   5: iload_1
      //   6: isub
      //   7: invokestatic max : (II)I
      //   10: istore_1
      //   11: iload_3
      //   12: ifeq -> 30
      //   15: iload_2
      //   16: iflt -> 22
      //   19: goto -> 34
      //   22: iconst_0
      //   23: istore_2
      //   24: iload #4
      //   26: istore_0
      //   27: goto -> 65
      //   30: iload_2
      //   31: iflt -> 40
      //   34: ldc 1073741824
      //   36: istore_0
      //   37: goto -> 65
      //   40: iload_2
      //   41: iconst_m1
      //   42: if_icmpne -> 53
      //   45: ldc 1073741824
      //   47: istore_0
      //   48: iload_1
      //   49: istore_2
      //   50: goto -> 65
      //   53: iload_2
      //   54: bipush #-2
      //   56: if_icmpne -> 22
      //   59: ldc -2147483648
      //   61: istore_0
      //   62: goto -> 48
      //   65: iload_2
      //   66: iload_0
      //   67: invokestatic makeMeasureSpec : (II)I
      //   70: ireturn
    }
    
    private int[] getChildRectangleOnScreenScrollAmount(RecyclerView param1RecyclerView, View param1View, Rect param1Rect, boolean param1Boolean) {
      int i = getPaddingLeft();
      int j = getPaddingTop();
      int k = getWidth();
      int m = getPaddingRight();
      int n = getHeight();
      int i1 = getPaddingBottom();
      int i2 = param1View.getLeft() + param1Rect.left - param1View.getScrollX();
      int i3 = param1View.getTop() + param1Rect.top - param1View.getScrollY();
      int i4 = param1Rect.width();
      int i5 = param1Rect.height();
      int i6 = i2 - i;
      i = Math.min(0, i6);
      int i7 = i3 - j;
      j = Math.min(0, i7);
      m = i4 + i2 - k - m;
      k = Math.max(0, m);
      n = Math.max(0, i5 + i3 - n - i1);
      if (getLayoutDirection() == 1) {
        if (k != 0) {
          i = k;
        } else {
          i = Math.max(i, m);
        } 
      } else if (i == 0) {
        i = Math.min(i6, k);
      } 
      if (j == 0)
        j = Math.min(i7, n); 
      return new int[] { i, j };
    }
    
    public static Properties getProperties(Context param1Context, AttributeSet param1AttributeSet, int param1Int1, int param1Int2) {
      Properties properties = new Properties();
      TypedArray typedArray = param1Context.obtainStyledAttributes(param1AttributeSet, R.styleable.RecyclerView, param1Int1, param1Int2);
      properties.orientation = typedArray.getInt(R.styleable.RecyclerView_android_orientation, 1);
      properties.spanCount = typedArray.getInt(R.styleable.RecyclerView_spanCount, 1);
      properties.reverseLayout = typedArray.getBoolean(R.styleable.RecyclerView_reverseLayout, false);
      properties.stackFromEnd = typedArray.getBoolean(R.styleable.RecyclerView_stackFromEnd, false);
      typedArray.recycle();
      return properties;
    }
    
    private boolean isFocusedChildVisibleAfterScrolling(RecyclerView param1RecyclerView, int param1Int1, int param1Int2) {
      View view = param1RecyclerView.getFocusedChild();
      if (view == null)
        return false; 
      int i = getPaddingLeft();
      int j = getPaddingTop();
      int k = getWidth();
      int m = getPaddingRight();
      int n = getHeight();
      int i1 = getPaddingBottom();
      Rect rect = this.mRecyclerView.mTempRect;
      getDecoratedBoundsWithMargins(view, rect);
      return !(rect.left - param1Int1 >= k - m || rect.right - param1Int1 <= i || rect.top - param1Int2 >= n - i1 || rect.bottom - param1Int2 <= j);
    }
    
    private static boolean isMeasurementUpToDate(int param1Int1, int param1Int2, int param1Int3) {
      int i = View.MeasureSpec.getMode(param1Int2);
      param1Int2 = View.MeasureSpec.getSize(param1Int2);
      boolean bool1 = false;
      boolean bool2 = false;
      if (param1Int3 > 0 && param1Int1 != param1Int3)
        return false; 
      if (i != Integer.MIN_VALUE) {
        if (i != 0) {
          if (i != 1073741824)
            return false; 
          if (param1Int2 == param1Int1)
            bool2 = true; 
          return bool2;
        } 
        return true;
      } 
      bool2 = bool1;
      if (param1Int2 >= param1Int1)
        bool2 = true; 
      return bool2;
    }
    
    private void onSmoothScrollerStopped(RecyclerView.SmoothScroller param1SmoothScroller) {
      if (this.mSmoothScroller == param1SmoothScroller)
        this.mSmoothScroller = null; 
    }
    
    private void scrapOrRecycleView(RecyclerView.Recycler param1Recycler, int param1Int, View param1View) {
      RecyclerView.ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(param1View);
      if (viewHolder.shouldIgnore())
        return; 
      if (viewHolder.isInvalid() && !viewHolder.isRemoved() && !this.mRecyclerView.mAdapter.hasStableIds()) {
        removeViewAt(param1Int);
        param1Recycler.recycleViewHolderInternal(viewHolder);
      } else {
        detachViewAt(param1Int);
        param1Recycler.scrapView(param1View);
        this.mRecyclerView.mViewInfoStore.onViewDetached(viewHolder);
      } 
    }
    
    public void addDisappearingView(View param1View) {
      addDisappearingView(param1View, -1);
    }
    
    public void addDisappearingView(View param1View, int param1Int) {
      addViewInt(param1View, param1Int, true);
    }
    
    public void addView(View param1View) {
      addView(param1View, -1);
    }
    
    public void addView(View param1View, int param1Int) {
      addViewInt(param1View, param1Int, false);
    }
    
    public void assertInLayoutOrScroll(String param1String) {
      RecyclerView recyclerView = this.mRecyclerView;
      if (recyclerView != null)
        recyclerView.assertInLayoutOrScroll(param1String); 
    }
    
    public void assertNotInLayoutOrScroll(String param1String) {
      RecyclerView recyclerView = this.mRecyclerView;
      if (recyclerView != null)
        recyclerView.assertNotInLayoutOrScroll(param1String); 
    }
    
    public void attachView(View param1View) {
      attachView(param1View, -1);
    }
    
    public void attachView(View param1View, int param1Int) {
      attachView(param1View, param1Int, (RecyclerView.LayoutParams)param1View.getLayoutParams());
    }
    
    public void attachView(View param1View, int param1Int, RecyclerView.LayoutParams param1LayoutParams) {
      RecyclerView.ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(param1View);
      if (viewHolder.isRemoved()) {
        this.mRecyclerView.mViewInfoStore.addToDisappearedInLayout(viewHolder);
      } else {
        this.mRecyclerView.mViewInfoStore.removeFromDisappearedInLayout(viewHolder);
      } 
      this.mChildHelper.attachViewToParent(param1View, param1Int, (ViewGroup.LayoutParams)param1LayoutParams, viewHolder.isRemoved());
    }
    
    public void calculateItemDecorationsForChild(View param1View, Rect param1Rect) {
      RecyclerView recyclerView = this.mRecyclerView;
      if (recyclerView == null) {
        param1Rect.set(0, 0, 0, 0);
        return;
      } 
      param1Rect.set(recyclerView.getItemDecorInsetsForChild(param1View));
    }
    
    public boolean canScrollHorizontally() {
      return false;
    }
    
    public boolean canScrollVertically() {
      return false;
    }
    
    public boolean checkLayoutParams(RecyclerView.LayoutParams param1LayoutParams) {
      boolean bool;
      if (param1LayoutParams != null) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public void collectAdjacentPrefetchPositions(int param1Int1, int param1Int2, RecyclerView.State param1State, LayoutPrefetchRegistry param1LayoutPrefetchRegistry) {}
    
    public void collectInitialPrefetchPositions(int param1Int, LayoutPrefetchRegistry param1LayoutPrefetchRegistry) {}
    
    public int computeHorizontalScrollExtent(RecyclerView.State param1State) {
      return 0;
    }
    
    public int computeHorizontalScrollOffset(RecyclerView.State param1State) {
      return 0;
    }
    
    public int computeHorizontalScrollRange(RecyclerView.State param1State) {
      return 0;
    }
    
    public int computeVerticalScrollExtent(RecyclerView.State param1State) {
      return 0;
    }
    
    public int computeVerticalScrollOffset(RecyclerView.State param1State) {
      return 0;
    }
    
    public int computeVerticalScrollRange(RecyclerView.State param1State) {
      return 0;
    }
    
    public void detachAndScrapAttachedViews(RecyclerView.Recycler param1Recycler) {
      for (int i = getChildCount() - 1; i >= 0; i--)
        scrapOrRecycleView(param1Recycler, i, getChildAt(i)); 
    }
    
    public void detachAndScrapView(View param1View, RecyclerView.Recycler param1Recycler) {
      scrapOrRecycleView(param1Recycler, this.mChildHelper.indexOfChild(param1View), param1View);
    }
    
    public void detachAndScrapViewAt(int param1Int, RecyclerView.Recycler param1Recycler) {
      scrapOrRecycleView(param1Recycler, param1Int, getChildAt(param1Int));
    }
    
    public void detachView(View param1View) {
      int i = this.mChildHelper.indexOfChild(param1View);
      if (i >= 0)
        detachViewInternal(i, param1View); 
    }
    
    public void detachViewAt(int param1Int) {
      detachViewInternal(param1Int, getChildAt(param1Int));
    }
    
    void dispatchAttachedToWindow(RecyclerView param1RecyclerView) {
      this.mIsAttachedToWindow = true;
      onAttachedToWindow(param1RecyclerView);
    }
    
    void dispatchDetachedFromWindow(RecyclerView param1RecyclerView, RecyclerView.Recycler param1Recycler) {
      this.mIsAttachedToWindow = false;
      onDetachedFromWindow(param1RecyclerView, param1Recycler);
    }
    
    public void endAnimation(View param1View) {
      if (this.mRecyclerView.mItemAnimator != null)
        this.mRecyclerView.mItemAnimator.endAnimation(RecyclerView.getChildViewHolderInt(param1View)); 
    }
    
    public View findContainingItemView(View param1View) {
      RecyclerView recyclerView = this.mRecyclerView;
      if (recyclerView == null)
        return null; 
      param1View = recyclerView.findContainingItemView(param1View);
      return (param1View == null) ? null : (this.mChildHelper.isHidden(param1View) ? null : param1View);
    }
    
    public View findViewByPosition(int param1Int) {
      int i = getChildCount();
      for (byte b = 0; b < i; b++) {
        View view = getChildAt(b);
        RecyclerView.ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
        if (viewHolder != null && viewHolder.getLayoutPosition() == param1Int && !viewHolder.shouldIgnore() && (this.mRecyclerView.mState.isPreLayout() || !viewHolder.isRemoved()))
          return view; 
      } 
      return null;
    }
    
    public abstract RecyclerView.LayoutParams generateDefaultLayoutParams();
    
    public RecyclerView.LayoutParams generateLayoutParams(Context param1Context, AttributeSet param1AttributeSet) {
      return new RecyclerView.LayoutParams(param1Context, param1AttributeSet);
    }
    
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams param1LayoutParams) {
      return (param1LayoutParams instanceof RecyclerView.LayoutParams) ? new RecyclerView.LayoutParams((RecyclerView.LayoutParams)param1LayoutParams) : ((param1LayoutParams instanceof ViewGroup.MarginLayoutParams) ? new RecyclerView.LayoutParams((ViewGroup.MarginLayoutParams)param1LayoutParams) : new RecyclerView.LayoutParams(param1LayoutParams));
    }
    
    public int getBaseline() {
      return -1;
    }
    
    public int getBottomDecorationHeight(View param1View) {
      return ((RecyclerView.LayoutParams)param1View.getLayoutParams()).mDecorInsets.bottom;
    }
    
    public View getChildAt(int param1Int) {
      ChildHelper childHelper = this.mChildHelper;
      if (childHelper != null) {
        View view = childHelper.getChildAt(param1Int);
      } else {
        childHelper = null;
      } 
      return (View)childHelper;
    }
    
    public int getChildCount() {
      boolean bool;
      ChildHelper childHelper = this.mChildHelper;
      if (childHelper != null) {
        bool = childHelper.getChildCount();
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public boolean getClipToPadding() {
      boolean bool;
      RecyclerView recyclerView = this.mRecyclerView;
      if (recyclerView != null && recyclerView.mClipToPadding) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public int getColumnCountForAccessibility(RecyclerView.Recycler param1Recycler, RecyclerView.State param1State) {
      RecyclerView recyclerView = this.mRecyclerView;
      byte b = 1;
      int i = b;
      if (recyclerView != null)
        if (recyclerView.mAdapter == null) {
          i = b;
        } else {
          i = b;
          if (canScrollHorizontally())
            i = this.mRecyclerView.mAdapter.getItemCount(); 
        }  
      return i;
    }
    
    public int getDecoratedBottom(View param1View) {
      return param1View.getBottom() + getBottomDecorationHeight(param1View);
    }
    
    public void getDecoratedBoundsWithMargins(View param1View, Rect param1Rect) {
      RecyclerView.getDecoratedBoundsWithMarginsInt(param1View, param1Rect);
    }
    
    public int getDecoratedLeft(View param1View) {
      return param1View.getLeft() - getLeftDecorationWidth(param1View);
    }
    
    public int getDecoratedMeasuredHeight(View param1View) {
      Rect rect = ((RecyclerView.LayoutParams)param1View.getLayoutParams()).mDecorInsets;
      return param1View.getMeasuredHeight() + rect.top + rect.bottom;
    }
    
    public int getDecoratedMeasuredWidth(View param1View) {
      Rect rect = ((RecyclerView.LayoutParams)param1View.getLayoutParams()).mDecorInsets;
      return param1View.getMeasuredWidth() + rect.left + rect.right;
    }
    
    public int getDecoratedRight(View param1View) {
      return param1View.getRight() + getRightDecorationWidth(param1View);
    }
    
    public int getDecoratedTop(View param1View) {
      return param1View.getTop() - getTopDecorationHeight(param1View);
    }
    
    public View getFocusedChild() {
      RecyclerView recyclerView = this.mRecyclerView;
      if (recyclerView == null)
        return null; 
      View view = recyclerView.getFocusedChild();
      return (view == null || this.mChildHelper.isHidden(view)) ? null : view;
    }
    
    public int getHeight() {
      return this.mHeight;
    }
    
    public int getHeightMode() {
      return this.mHeightMode;
    }
    
    public int getItemCount() {
      boolean bool;
      RecyclerView recyclerView = this.mRecyclerView;
      if (recyclerView != null) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
      } else {
        recyclerView = null;
      } 
      if (recyclerView != null) {
        bool = recyclerView.getItemCount();
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public int getItemViewType(View param1View) {
      return RecyclerView.getChildViewHolderInt(param1View).getItemViewType();
    }
    
    public int getLayoutDirection() {
      return ViewCompat.getLayoutDirection((View)this.mRecyclerView);
    }
    
    public int getLeftDecorationWidth(View param1View) {
      return ((RecyclerView.LayoutParams)param1View.getLayoutParams()).mDecorInsets.left;
    }
    
    public int getMinimumHeight() {
      return ViewCompat.getMinimumHeight((View)this.mRecyclerView);
    }
    
    public int getMinimumWidth() {
      return ViewCompat.getMinimumWidth((View)this.mRecyclerView);
    }
    
    public int getPaddingBottom() {
      boolean bool;
      RecyclerView recyclerView = this.mRecyclerView;
      if (recyclerView != null) {
        bool = recyclerView.getPaddingBottom();
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public int getPaddingEnd() {
      boolean bool;
      RecyclerView recyclerView = this.mRecyclerView;
      if (recyclerView != null) {
        bool = ViewCompat.getPaddingEnd((View)recyclerView);
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public int getPaddingLeft() {
      boolean bool;
      RecyclerView recyclerView = this.mRecyclerView;
      if (recyclerView != null) {
        bool = recyclerView.getPaddingLeft();
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public int getPaddingRight() {
      boolean bool;
      RecyclerView recyclerView = this.mRecyclerView;
      if (recyclerView != null) {
        bool = recyclerView.getPaddingRight();
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public int getPaddingStart() {
      boolean bool;
      RecyclerView recyclerView = this.mRecyclerView;
      if (recyclerView != null) {
        bool = ViewCompat.getPaddingStart((View)recyclerView);
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public int getPaddingTop() {
      boolean bool;
      RecyclerView recyclerView = this.mRecyclerView;
      if (recyclerView != null) {
        bool = recyclerView.getPaddingTop();
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public int getPosition(View param1View) {
      return ((RecyclerView.LayoutParams)param1View.getLayoutParams()).getViewLayoutPosition();
    }
    
    public int getRightDecorationWidth(View param1View) {
      return ((RecyclerView.LayoutParams)param1View.getLayoutParams()).mDecorInsets.right;
    }
    
    public int getRowCountForAccessibility(RecyclerView.Recycler param1Recycler, RecyclerView.State param1State) {
      RecyclerView recyclerView = this.mRecyclerView;
      byte b = 1;
      int i = b;
      if (recyclerView != null)
        if (recyclerView.mAdapter == null) {
          i = b;
        } else {
          i = b;
          if (canScrollVertically())
            i = this.mRecyclerView.mAdapter.getItemCount(); 
        }  
      return i;
    }
    
    public int getSelectionModeForAccessibility(RecyclerView.Recycler param1Recycler, RecyclerView.State param1State) {
      return 0;
    }
    
    public int getTopDecorationHeight(View param1View) {
      return ((RecyclerView.LayoutParams)param1View.getLayoutParams()).mDecorInsets.top;
    }
    
    public void getTransformedBoundingBox(View param1View, boolean param1Boolean, Rect param1Rect) {
      if (param1Boolean) {
        Rect rect = ((RecyclerView.LayoutParams)param1View.getLayoutParams()).mDecorInsets;
        param1Rect.set(-rect.left, -rect.top, param1View.getWidth() + rect.right, param1View.getHeight() + rect.bottom);
      } else {
        param1Rect.set(0, 0, param1View.getWidth(), param1View.getHeight());
      } 
      if (this.mRecyclerView != null) {
        Matrix matrix = param1View.getMatrix();
        if (matrix != null && !matrix.isIdentity()) {
          RectF rectF = this.mRecyclerView.mTempRectF;
          rectF.set(param1Rect);
          matrix.mapRect(rectF);
          param1Rect.set((int)Math.floor(rectF.left), (int)Math.floor(rectF.top), (int)Math.ceil(rectF.right), (int)Math.ceil(rectF.bottom));
        } 
      } 
      param1Rect.offset(param1View.getLeft(), param1View.getTop());
    }
    
    public int getWidth() {
      return this.mWidth;
    }
    
    public int getWidthMode() {
      return this.mWidthMode;
    }
    
    boolean hasFlexibleChildInBothOrientations() {
      int i = getChildCount();
      for (byte b = 0; b < i; b++) {
        ViewGroup.LayoutParams layoutParams = getChildAt(b).getLayoutParams();
        if (layoutParams.width < 0 && layoutParams.height < 0)
          return true; 
      } 
      return false;
    }
    
    public boolean hasFocus() {
      boolean bool;
      RecyclerView recyclerView = this.mRecyclerView;
      if (recyclerView != null && recyclerView.hasFocus()) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public void ignoreView(View param1View) {
      ViewParent viewParent = param1View.getParent();
      RecyclerView recyclerView = this.mRecyclerView;
      if (viewParent == recyclerView && recyclerView.indexOfChild(param1View) != -1) {
        RecyclerView.ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(param1View);
        viewHolder.addFlags(128);
        this.mRecyclerView.mViewInfoStore.removeViewHolder(viewHolder);
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("View should be fully attached to be ignored");
      stringBuilder.append(this.mRecyclerView.exceptionLabel());
      throw new IllegalArgumentException(stringBuilder.toString());
    }
    
    public boolean isAttachedToWindow() {
      return this.mIsAttachedToWindow;
    }
    
    public boolean isAutoMeasureEnabled() {
      return this.mAutoMeasure;
    }
    
    public boolean isFocused() {
      boolean bool;
      RecyclerView recyclerView = this.mRecyclerView;
      if (recyclerView != null && recyclerView.isFocused()) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public final boolean isItemPrefetchEnabled() {
      return this.mItemPrefetchEnabled;
    }
    
    public boolean isLayoutHierarchical(RecyclerView.Recycler param1Recycler, RecyclerView.State param1State) {
      return false;
    }
    
    public boolean isMeasurementCacheEnabled() {
      return this.mMeasurementCacheEnabled;
    }
    
    public boolean isSmoothScrolling() {
      boolean bool;
      RecyclerView.SmoothScroller smoothScroller = this.mSmoothScroller;
      if (smoothScroller != null && smoothScroller.isRunning()) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public boolean isViewPartiallyVisible(View param1View, boolean param1Boolean1, boolean param1Boolean2) {
      if (this.mHorizontalBoundCheck.isViewWithinBoundFlags(param1View, 24579) && this.mVerticalBoundCheck.isViewWithinBoundFlags(param1View, 24579)) {
        param1Boolean2 = true;
      } else {
        param1Boolean2 = false;
      } 
      return param1Boolean1 ? param1Boolean2 : (param1Boolean2 ^ true);
    }
    
    public void layoutDecorated(View param1View, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      Rect rect = ((RecyclerView.LayoutParams)param1View.getLayoutParams()).mDecorInsets;
      param1View.layout(param1Int1 + rect.left, param1Int2 + rect.top, param1Int3 - rect.right, param1Int4 - rect.bottom);
    }
    
    public void layoutDecoratedWithMargins(View param1View, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)param1View.getLayoutParams();
      Rect rect = layoutParams.mDecorInsets;
      param1View.layout(param1Int1 + rect.left + layoutParams.leftMargin, param1Int2 + rect.top + layoutParams.topMargin, param1Int3 - rect.right - layoutParams.rightMargin, param1Int4 - rect.bottom - layoutParams.bottomMargin);
    }
    
    public void measureChild(View param1View, int param1Int1, int param1Int2) {
      RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)param1View.getLayoutParams();
      Rect rect = this.mRecyclerView.getItemDecorInsetsForChild(param1View);
      int i = rect.left;
      int j = rect.right;
      int k = rect.top;
      int m = rect.bottom;
      param1Int1 = getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight() + param1Int1 + i + j, layoutParams.width, canScrollHorizontally());
      param1Int2 = getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom() + param1Int2 + k + m, layoutParams.height, canScrollVertically());
      if (shouldMeasureChild(param1View, param1Int1, param1Int2, layoutParams))
        param1View.measure(param1Int1, param1Int2); 
    }
    
    public void measureChildWithMargins(View param1View, int param1Int1, int param1Int2) {
      RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)param1View.getLayoutParams();
      Rect rect = this.mRecyclerView.getItemDecorInsetsForChild(param1View);
      int i = rect.left;
      int j = rect.right;
      int k = rect.top;
      int m = rect.bottom;
      param1Int1 = getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight() + layoutParams.leftMargin + layoutParams.rightMargin + param1Int1 + i + j, layoutParams.width, canScrollHorizontally());
      param1Int2 = getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom() + layoutParams.topMargin + layoutParams.bottomMargin + param1Int2 + k + m, layoutParams.height, canScrollVertically());
      if (shouldMeasureChild(param1View, param1Int1, param1Int2, layoutParams))
        param1View.measure(param1Int1, param1Int2); 
    }
    
    public void moveView(int param1Int1, int param1Int2) {
      View view = getChildAt(param1Int1);
      if (view != null) {
        detachViewAt(param1Int1);
        attachView(view, param1Int2);
        return;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Cannot move a child from non-existing index:");
      stringBuilder.append(param1Int1);
      stringBuilder.append(this.mRecyclerView.toString());
      throw new IllegalArgumentException(stringBuilder.toString());
    }
    
    public void offsetChildrenHorizontal(int param1Int) {
      RecyclerView recyclerView = this.mRecyclerView;
      if (recyclerView != null)
        recyclerView.offsetChildrenHorizontal(param1Int); 
    }
    
    public void offsetChildrenVertical(int param1Int) {
      RecyclerView recyclerView = this.mRecyclerView;
      if (recyclerView != null)
        recyclerView.offsetChildrenVertical(param1Int); 
    }
    
    public void onAdapterChanged(RecyclerView.Adapter param1Adapter1, RecyclerView.Adapter param1Adapter2) {}
    
    public boolean onAddFocusables(RecyclerView param1RecyclerView, ArrayList<View> param1ArrayList, int param1Int1, int param1Int2) {
      return false;
    }
    
    public void onAttachedToWindow(RecyclerView param1RecyclerView) {}
    
    @Deprecated
    public void onDetachedFromWindow(RecyclerView param1RecyclerView) {}
    
    public void onDetachedFromWindow(RecyclerView param1RecyclerView, RecyclerView.Recycler param1Recycler) {
      onDetachedFromWindow(param1RecyclerView);
    }
    
    public View onFocusSearchFailed(View param1View, int param1Int, RecyclerView.Recycler param1Recycler, RecyclerView.State param1State) {
      return null;
    }
    
    public void onInitializeAccessibilityEvent(RecyclerView.Recycler param1Recycler, RecyclerView.State param1State, AccessibilityEvent param1AccessibilityEvent) {
      RecyclerView recyclerView = this.mRecyclerView;
      if (recyclerView != null && param1AccessibilityEvent != null) {
        boolean bool1 = true;
        boolean bool2 = bool1;
        if (!recyclerView.canScrollVertically(1)) {
          bool2 = bool1;
          if (!this.mRecyclerView.canScrollVertically(-1)) {
            bool2 = bool1;
            if (!this.mRecyclerView.canScrollHorizontally(-1))
              if (this.mRecyclerView.canScrollHorizontally(1)) {
                bool2 = bool1;
              } else {
                bool2 = false;
              }  
          } 
        } 
        param1AccessibilityEvent.setScrollable(bool2);
        if (this.mRecyclerView.mAdapter != null)
          param1AccessibilityEvent.setItemCount(this.mRecyclerView.mAdapter.getItemCount()); 
      } 
    }
    
    public void onInitializeAccessibilityEvent(AccessibilityEvent param1AccessibilityEvent) {
      onInitializeAccessibilityEvent(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, param1AccessibilityEvent);
    }
    
    void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfoCompat param1AccessibilityNodeInfoCompat) {
      onInitializeAccessibilityNodeInfo(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, param1AccessibilityNodeInfoCompat);
    }
    
    public void onInitializeAccessibilityNodeInfo(RecyclerView.Recycler param1Recycler, RecyclerView.State param1State, AccessibilityNodeInfoCompat param1AccessibilityNodeInfoCompat) {
      if (this.mRecyclerView.canScrollVertically(-1) || this.mRecyclerView.canScrollHorizontally(-1)) {
        param1AccessibilityNodeInfoCompat.addAction(8192);
        param1AccessibilityNodeInfoCompat.setScrollable(true);
      } 
      if (this.mRecyclerView.canScrollVertically(1) || this.mRecyclerView.canScrollHorizontally(1)) {
        param1AccessibilityNodeInfoCompat.addAction(4096);
        param1AccessibilityNodeInfoCompat.setScrollable(true);
      } 
      param1AccessibilityNodeInfoCompat.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(getRowCountForAccessibility(param1Recycler, param1State), getColumnCountForAccessibility(param1Recycler, param1State), isLayoutHierarchical(param1Recycler, param1State), getSelectionModeForAccessibility(param1Recycler, param1State)));
    }
    
    public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler param1Recycler, RecyclerView.State param1State, View param1View, AccessibilityNodeInfoCompat param1AccessibilityNodeInfoCompat) {
      boolean bool1;
      boolean bool2;
      if (canScrollVertically()) {
        bool1 = getPosition(param1View);
      } else {
        bool1 = false;
      } 
      if (canScrollHorizontally()) {
        bool2 = getPosition(param1View);
      } else {
        bool2 = false;
      } 
      param1AccessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(bool1, 1, bool2, 1, false, false));
    }
    
    void onInitializeAccessibilityNodeInfoForItem(View param1View, AccessibilityNodeInfoCompat param1AccessibilityNodeInfoCompat) {
      RecyclerView.ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(param1View);
      if (viewHolder != null && !viewHolder.isRemoved() && !this.mChildHelper.isHidden(viewHolder.itemView))
        onInitializeAccessibilityNodeInfoForItem(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, param1View, param1AccessibilityNodeInfoCompat); 
    }
    
    public View onInterceptFocusSearch(View param1View, int param1Int) {
      return null;
    }
    
    public void onItemsAdded(RecyclerView param1RecyclerView, int param1Int1, int param1Int2) {}
    
    public void onItemsChanged(RecyclerView param1RecyclerView) {}
    
    public void onItemsMoved(RecyclerView param1RecyclerView, int param1Int1, int param1Int2, int param1Int3) {}
    
    public void onItemsRemoved(RecyclerView param1RecyclerView, int param1Int1, int param1Int2) {}
    
    public void onItemsUpdated(RecyclerView param1RecyclerView, int param1Int1, int param1Int2) {}
    
    public void onItemsUpdated(RecyclerView param1RecyclerView, int param1Int1, int param1Int2, Object param1Object) {
      onItemsUpdated(param1RecyclerView, param1Int1, param1Int2);
    }
    
    public void onLayoutChildren(RecyclerView.Recycler param1Recycler, RecyclerView.State param1State) {
      Log.e("RecyclerView", "You must override onLayoutChildren(Recycler recycler, State state) ");
    }
    
    public void onLayoutCompleted(RecyclerView.State param1State) {}
    
    public void onMeasure(RecyclerView.Recycler param1Recycler, RecyclerView.State param1State, int param1Int1, int param1Int2) {
      this.mRecyclerView.defaultOnMeasure(param1Int1, param1Int2);
    }
    
    public boolean onRequestChildFocus(RecyclerView param1RecyclerView, RecyclerView.State param1State, View param1View1, View param1View2) {
      return onRequestChildFocus(param1RecyclerView, param1View1, param1View2);
    }
    
    @Deprecated
    public boolean onRequestChildFocus(RecyclerView param1RecyclerView, View param1View1, View param1View2) {
      return (isSmoothScrolling() || param1RecyclerView.isComputingLayout());
    }
    
    public void onRestoreInstanceState(Parcelable param1Parcelable) {}
    
    public Parcelable onSaveInstanceState() {
      return null;
    }
    
    public void onScrollStateChanged(int param1Int) {}
    
    boolean performAccessibilityAction(int param1Int, Bundle param1Bundle) {
      return performAccessibilityAction(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, param1Int, param1Bundle);
    }
    
    public boolean performAccessibilityAction(RecyclerView.Recycler param1Recycler, RecyclerView.State param1State, int param1Int, Bundle param1Bundle) {
      // Byte code:
      //   0: aload_0
      //   1: getfield mRecyclerView : Landroid/support/v7/widget/RecyclerView;
      //   4: astore_1
      //   5: aload_1
      //   6: ifnonnull -> 11
      //   9: iconst_0
      //   10: ireturn
      //   11: iload_3
      //   12: sipush #4096
      //   15: if_icmpeq -> 104
      //   18: iload_3
      //   19: sipush #8192
      //   22: if_icmpeq -> 41
      //   25: iconst_0
      //   26: istore #5
      //   28: iconst_0
      //   29: istore #6
      //   31: iload #5
      //   33: istore_3
      //   34: iload #6
      //   36: istore #5
      //   38: goto -> 162
      //   41: aload_1
      //   42: iconst_m1
      //   43: invokevirtual canScrollVertically : (I)Z
      //   46: ifeq -> 68
      //   49: aload_0
      //   50: invokevirtual getHeight : ()I
      //   53: aload_0
      //   54: invokevirtual getPaddingTop : ()I
      //   57: isub
      //   58: aload_0
      //   59: invokevirtual getPaddingBottom : ()I
      //   62: isub
      //   63: ineg
      //   64: istore_3
      //   65: goto -> 70
      //   68: iconst_0
      //   69: istore_3
      //   70: iload_3
      //   71: istore #5
      //   73: aload_0
      //   74: getfield mRecyclerView : Landroid/support/v7/widget/RecyclerView;
      //   77: iconst_m1
      //   78: invokevirtual canScrollHorizontally : (I)Z
      //   81: ifeq -> 28
      //   84: aload_0
      //   85: invokevirtual getWidth : ()I
      //   88: aload_0
      //   89: invokevirtual getPaddingLeft : ()I
      //   92: isub
      //   93: aload_0
      //   94: invokevirtual getPaddingRight : ()I
      //   97: isub
      //   98: ineg
      //   99: istore #5
      //   101: goto -> 162
      //   104: aload_1
      //   105: iconst_1
      //   106: invokevirtual canScrollVertically : (I)Z
      //   109: ifeq -> 130
      //   112: aload_0
      //   113: invokevirtual getHeight : ()I
      //   116: aload_0
      //   117: invokevirtual getPaddingTop : ()I
      //   120: isub
      //   121: aload_0
      //   122: invokevirtual getPaddingBottom : ()I
      //   125: isub
      //   126: istore_3
      //   127: goto -> 132
      //   130: iconst_0
      //   131: istore_3
      //   132: iload_3
      //   133: istore #5
      //   135: aload_0
      //   136: getfield mRecyclerView : Landroid/support/v7/widget/RecyclerView;
      //   139: iconst_1
      //   140: invokevirtual canScrollHorizontally : (I)Z
      //   143: ifeq -> 28
      //   146: aload_0
      //   147: invokevirtual getWidth : ()I
      //   150: aload_0
      //   151: invokevirtual getPaddingLeft : ()I
      //   154: isub
      //   155: aload_0
      //   156: invokevirtual getPaddingRight : ()I
      //   159: isub
      //   160: istore #5
      //   162: iload_3
      //   163: ifne -> 173
      //   166: iload #5
      //   168: ifne -> 173
      //   171: iconst_0
      //   172: ireturn
      //   173: aload_0
      //   174: getfield mRecyclerView : Landroid/support/v7/widget/RecyclerView;
      //   177: iload #5
      //   179: iload_3
      //   180: invokevirtual scrollBy : (II)V
      //   183: iconst_1
      //   184: ireturn
    }
    
    public boolean performAccessibilityActionForItem(RecyclerView.Recycler param1Recycler, RecyclerView.State param1State, View param1View, int param1Int, Bundle param1Bundle) {
      return false;
    }
    
    boolean performAccessibilityActionForItem(View param1View, int param1Int, Bundle param1Bundle) {
      return performAccessibilityActionForItem(this.mRecyclerView.mRecycler, this.mRecyclerView.mState, param1View, param1Int, param1Bundle);
    }
    
    public void postOnAnimation(Runnable param1Runnable) {
      RecyclerView recyclerView = this.mRecyclerView;
      if (recyclerView != null)
        ViewCompat.postOnAnimation((View)recyclerView, param1Runnable); 
    }
    
    public void removeAllViews() {
      for (int i = getChildCount() - 1; i >= 0; i--)
        this.mChildHelper.removeViewAt(i); 
    }
    
    public void removeAndRecycleAllViews(RecyclerView.Recycler param1Recycler) {
      for (int i = getChildCount() - 1; i >= 0; i--) {
        if (!RecyclerView.getChildViewHolderInt(getChildAt(i)).shouldIgnore())
          removeAndRecycleViewAt(i, param1Recycler); 
      } 
    }
    
    void removeAndRecycleScrapInt(RecyclerView.Recycler param1Recycler) {
      int i = param1Recycler.getScrapCount();
      for (int j = i - 1; j >= 0; j--) {
        View view = param1Recycler.getScrapViewAt(j);
        RecyclerView.ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
        if (!viewHolder.shouldIgnore()) {
          viewHolder.setIsRecyclable(false);
          if (viewHolder.isTmpDetached())
            this.mRecyclerView.removeDetachedView(view, false); 
          if (this.mRecyclerView.mItemAnimator != null)
            this.mRecyclerView.mItemAnimator.endAnimation(viewHolder); 
          viewHolder.setIsRecyclable(true);
          param1Recycler.quickRecycleScrapView(view);
        } 
      } 
      param1Recycler.clearScrap();
      if (i > 0)
        this.mRecyclerView.invalidate(); 
    }
    
    public void removeAndRecycleView(View param1View, RecyclerView.Recycler param1Recycler) {
      removeView(param1View);
      param1Recycler.recycleView(param1View);
    }
    
    public void removeAndRecycleViewAt(int param1Int, RecyclerView.Recycler param1Recycler) {
      View view = getChildAt(param1Int);
      removeViewAt(param1Int);
      param1Recycler.recycleView(view);
    }
    
    public boolean removeCallbacks(Runnable param1Runnable) {
      RecyclerView recyclerView = this.mRecyclerView;
      return (recyclerView != null) ? recyclerView.removeCallbacks(param1Runnable) : false;
    }
    
    public void removeDetachedView(View param1View) {
      this.mRecyclerView.removeDetachedView(param1View, false);
    }
    
    public void removeView(View param1View) {
      this.mChildHelper.removeView(param1View);
    }
    
    public void removeViewAt(int param1Int) {
      if (getChildAt(param1Int) != null)
        this.mChildHelper.removeViewAt(param1Int); 
    }
    
    public boolean requestChildRectangleOnScreen(RecyclerView param1RecyclerView, View param1View, Rect param1Rect, boolean param1Boolean) {
      return requestChildRectangleOnScreen(param1RecyclerView, param1View, param1Rect, param1Boolean, false);
    }
    
    public boolean requestChildRectangleOnScreen(RecyclerView param1RecyclerView, View param1View, Rect param1Rect, boolean param1Boolean1, boolean param1Boolean2) {
      int[] arrayOfInt = getChildRectangleOnScreenScrollAmount(param1RecyclerView, param1View, param1Rect, param1Boolean1);
      int i = arrayOfInt[0];
      int j = arrayOfInt[1];
      if ((!param1Boolean2 || isFocusedChildVisibleAfterScrolling(param1RecyclerView, i, j)) && (i != 0 || j != 0)) {
        if (param1Boolean1) {
          param1RecyclerView.scrollBy(i, j);
        } else {
          param1RecyclerView.smoothScrollBy(i, j);
        } 
        return true;
      } 
      return false;
    }
    
    public void requestLayout() {
      RecyclerView recyclerView = this.mRecyclerView;
      if (recyclerView != null)
        recyclerView.requestLayout(); 
    }
    
    public void requestSimpleAnimationsInNextLayout() {
      this.mRequestedSimpleAnimations = true;
    }
    
    public int scrollHorizontallyBy(int param1Int, RecyclerView.Recycler param1Recycler, RecyclerView.State param1State) {
      return 0;
    }
    
    public void scrollToPosition(int param1Int) {}
    
    public int scrollVerticallyBy(int param1Int, RecyclerView.Recycler param1Recycler, RecyclerView.State param1State) {
      return 0;
    }
    
    @Deprecated
    public void setAutoMeasureEnabled(boolean param1Boolean) {
      this.mAutoMeasure = param1Boolean;
    }
    
    void setExactMeasureSpecsFrom(RecyclerView param1RecyclerView) {
      setMeasureSpecs(View.MeasureSpec.makeMeasureSpec(param1RecyclerView.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(param1RecyclerView.getHeight(), 1073741824));
    }
    
    public final void setItemPrefetchEnabled(boolean param1Boolean) {
      if (param1Boolean != this.mItemPrefetchEnabled) {
        this.mItemPrefetchEnabled = param1Boolean;
        this.mPrefetchMaxCountObserved = 0;
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView != null)
          recyclerView.mRecycler.updateViewCacheSize(); 
      } 
    }
    
    void setMeasureSpecs(int param1Int1, int param1Int2) {
      this.mWidth = View.MeasureSpec.getSize(param1Int1);
      param1Int1 = View.MeasureSpec.getMode(param1Int1);
      this.mWidthMode = param1Int1;
      if (param1Int1 == 0 && !RecyclerView.ALLOW_SIZE_IN_UNSPECIFIED_SPEC)
        this.mWidth = 0; 
      this.mHeight = View.MeasureSpec.getSize(param1Int2);
      param1Int1 = View.MeasureSpec.getMode(param1Int2);
      this.mHeightMode = param1Int1;
      if (param1Int1 == 0 && !RecyclerView.ALLOW_SIZE_IN_UNSPECIFIED_SPEC)
        this.mHeight = 0; 
    }
    
    public void setMeasuredDimension(int param1Int1, int param1Int2) {
      this.mRecyclerView.setMeasuredDimension(param1Int1, param1Int2);
    }
    
    public void setMeasuredDimension(Rect param1Rect, int param1Int1, int param1Int2) {
      int i = param1Rect.width();
      int j = getPaddingLeft();
      int k = getPaddingRight();
      int m = param1Rect.height();
      int n = getPaddingTop();
      int i1 = getPaddingBottom();
      setMeasuredDimension(chooseSize(param1Int1, i + j + k, getMinimumWidth()), chooseSize(param1Int2, m + n + i1, getMinimumHeight()));
    }
    
    void setMeasuredDimensionFromChildren(int param1Int1, int param1Int2) {
      int i = getChildCount();
      if (i == 0) {
        this.mRecyclerView.defaultOnMeasure(param1Int1, param1Int2);
        return;
      } 
      byte b = 0;
      int j = Integer.MIN_VALUE;
      int k = Integer.MIN_VALUE;
      int m = Integer.MAX_VALUE;
      int n;
      for (n = Integer.MAX_VALUE; b < i; n = i2) {
        View view = getChildAt(b);
        Rect rect = this.mRecyclerView.mTempRect;
        getDecoratedBoundsWithMargins(view, rect);
        int i1 = m;
        if (rect.left < m)
          i1 = rect.left; 
        m = j;
        if (rect.right > j)
          m = rect.right; 
        int i2 = n;
        if (rect.top < n)
          i2 = rect.top; 
        n = k;
        if (rect.bottom > k)
          n = rect.bottom; 
        b++;
        j = m;
        k = n;
        m = i1;
      } 
      this.mRecyclerView.mTempRect.set(m, n, j, k);
      setMeasuredDimension(this.mRecyclerView.mTempRect, param1Int1, param1Int2);
    }
    
    public void setMeasurementCacheEnabled(boolean param1Boolean) {
      this.mMeasurementCacheEnabled = param1Boolean;
    }
    
    void setRecyclerView(RecyclerView param1RecyclerView) {
      if (param1RecyclerView == null) {
        this.mRecyclerView = null;
        this.mChildHelper = null;
        this.mWidth = 0;
        this.mHeight = 0;
      } else {
        this.mRecyclerView = param1RecyclerView;
        this.mChildHelper = param1RecyclerView.mChildHelper;
        this.mWidth = param1RecyclerView.getWidth();
        this.mHeight = param1RecyclerView.getHeight();
      } 
      this.mWidthMode = 1073741824;
      this.mHeightMode = 1073741824;
    }
    
    boolean shouldMeasureChild(View param1View, int param1Int1, int param1Int2, RecyclerView.LayoutParams param1LayoutParams) {
      return (param1View.isLayoutRequested() || !this.mMeasurementCacheEnabled || !isMeasurementUpToDate(param1View.getWidth(), param1Int1, param1LayoutParams.width) || !isMeasurementUpToDate(param1View.getHeight(), param1Int2, param1LayoutParams.height));
    }
    
    boolean shouldMeasureTwice() {
      return false;
    }
    
    boolean shouldReMeasureChild(View param1View, int param1Int1, int param1Int2, RecyclerView.LayoutParams param1LayoutParams) {
      return (!this.mMeasurementCacheEnabled || !isMeasurementUpToDate(param1View.getMeasuredWidth(), param1Int1, param1LayoutParams.width) || !isMeasurementUpToDate(param1View.getMeasuredHeight(), param1Int2, param1LayoutParams.height));
    }
    
    public void smoothScrollToPosition(RecyclerView param1RecyclerView, RecyclerView.State param1State, int param1Int) {
      Log.e("RecyclerView", "You must override smoothScrollToPosition to support smooth scrolling");
    }
    
    public void startSmoothScroll(RecyclerView.SmoothScroller param1SmoothScroller) {
      RecyclerView.SmoothScroller smoothScroller = this.mSmoothScroller;
      if (smoothScroller != null && param1SmoothScroller != smoothScroller && smoothScroller.isRunning())
        this.mSmoothScroller.stop(); 
      this.mSmoothScroller = param1SmoothScroller;
      param1SmoothScroller.start(this.mRecyclerView, this);
    }
    
    public void stopIgnoringView(View param1View) {
      RecyclerView.ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(param1View);
      viewHolder.stopIgnoring();
      viewHolder.resetInternal();
      viewHolder.addFlags(4);
    }
    
    void stopSmoothScroller() {
      RecyclerView.SmoothScroller smoothScroller = this.mSmoothScroller;
      if (smoothScroller != null)
        smoothScroller.stop(); 
    }
    
    public boolean supportsPredictiveItemAnimations() {
      return false;
    }
    
    public static interface LayoutPrefetchRegistry {
      void addPosition(int param2Int1, int param2Int2);
    }
    
    public static class Properties {
      public int orientation;
      
      public boolean reverseLayout;
      
      public int spanCount;
      
      public boolean stackFromEnd;
    }
  }
  
  class null implements ViewBoundsCheck.Callback {
    public View getChildAt(int param1Int) {
      return this.this$0.getChildAt(param1Int);
    }
    
    public int getChildCount() {
      return this.this$0.getChildCount();
    }
    
    public int getChildEnd(View param1View) {
      RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)param1View.getLayoutParams();
      return this.this$0.getDecoratedRight(param1View) + layoutParams.rightMargin;
    }
    
    public int getChildStart(View param1View) {
      RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)param1View.getLayoutParams();
      return this.this$0.getDecoratedLeft(param1View) - layoutParams.leftMargin;
    }
    
    public View getParent() {
      return (View)this.this$0.mRecyclerView;
    }
    
    public int getParentEnd() {
      return this.this$0.getWidth() - this.this$0.getPaddingRight();
    }
    
    public int getParentStart() {
      return this.this$0.getPaddingLeft();
    }
  }
  
  class null implements ViewBoundsCheck.Callback {
    public View getChildAt(int param1Int) {
      return this.this$0.getChildAt(param1Int);
    }
    
    public int getChildCount() {
      return this.this$0.getChildCount();
    }
    
    public int getChildEnd(View param1View) {
      RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)param1View.getLayoutParams();
      return this.this$0.getDecoratedBottom(param1View) + layoutParams.bottomMargin;
    }
    
    public int getChildStart(View param1View) {
      RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)param1View.getLayoutParams();
      return this.this$0.getDecoratedTop(param1View) - layoutParams.topMargin;
    }
    
    public View getParent() {
      return (View)this.this$0.mRecyclerView;
    }
    
    public int getParentEnd() {
      return this.this$0.getHeight() - this.this$0.getPaddingBottom();
    }
    
    public int getParentStart() {
      return this.this$0.getPaddingTop();
    }
  }
  
  public static interface LayoutPrefetchRegistry {
    void addPosition(int param1Int1, int param1Int2);
  }
  
  public static class Properties {
    public int orientation;
    
    public boolean reverseLayout;
    
    public int spanCount;
    
    public boolean stackFromEnd;
  }
  
  public static class LayoutParams extends ViewGroup.MarginLayoutParams {
    final Rect mDecorInsets = new Rect();
    
    boolean mInsetsDirty = true;
    
    boolean mPendingInvalidate = false;
    
    RecyclerView.ViewHolder mViewHolder;
    
    public LayoutParams(int param1Int1, int param1Int2) {
      super(param1Int1, param1Int2);
    }
    
    public LayoutParams(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
    }
    
    public LayoutParams(LayoutParams param1LayoutParams) {
      super((ViewGroup.LayoutParams)param1LayoutParams);
    }
    
    public LayoutParams(ViewGroup.LayoutParams param1LayoutParams) {
      super(param1LayoutParams);
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams param1MarginLayoutParams) {
      super(param1MarginLayoutParams);
    }
    
    public int getViewAdapterPosition() {
      return this.mViewHolder.getAdapterPosition();
    }
    
    public int getViewLayoutPosition() {
      return this.mViewHolder.getLayoutPosition();
    }
    
    @Deprecated
    public int getViewPosition() {
      return this.mViewHolder.getPosition();
    }
    
    public boolean isItemChanged() {
      return this.mViewHolder.isUpdated();
    }
    
    public boolean isItemRemoved() {
      return this.mViewHolder.isRemoved();
    }
    
    public boolean isViewInvalid() {
      return this.mViewHolder.isInvalid();
    }
    
    public boolean viewNeedsUpdate() {
      return this.mViewHolder.needsUpdate();
    }
  }
  
  public static interface OnChildAttachStateChangeListener {
    void onChildViewAttachedToWindow(View param1View);
    
    void onChildViewDetachedFromWindow(View param1View);
  }
  
  public static abstract class OnFlingListener {
    public abstract boolean onFling(int param1Int1, int param1Int2);
  }
  
  public static interface OnItemTouchListener {
    boolean onInterceptTouchEvent(RecyclerView param1RecyclerView, MotionEvent param1MotionEvent);
    
    void onRequestDisallowInterceptTouchEvent(boolean param1Boolean);
    
    void onTouchEvent(RecyclerView param1RecyclerView, MotionEvent param1MotionEvent);
  }
  
  public static abstract class OnScrollListener {
    public void onScrollStateChanged(RecyclerView param1RecyclerView, int param1Int) {}
    
    public void onScrolled(RecyclerView param1RecyclerView, int param1Int1, int param1Int2) {}
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface Orientation {}
  
  public static class RecycledViewPool {
    private static final int DEFAULT_MAX_SCRAP = 5;
    
    private int mAttachCount = 0;
    
    SparseArray<ScrapData> mScrap = new SparseArray();
    
    private ScrapData getScrapDataForType(int param1Int) {
      ScrapData scrapData1 = (ScrapData)this.mScrap.get(param1Int);
      ScrapData scrapData2 = scrapData1;
      if (scrapData1 == null) {
        scrapData2 = new ScrapData();
        this.mScrap.put(param1Int, scrapData2);
      } 
      return scrapData2;
    }
    
    void attach(RecyclerView.Adapter param1Adapter) {
      this.mAttachCount++;
    }
    
    public void clear() {
      for (byte b = 0; b < this.mScrap.size(); b++)
        ((ScrapData)this.mScrap.valueAt(b)).mScrapHeap.clear(); 
    }
    
    void detach() {
      this.mAttachCount--;
    }
    
    void factorInBindTime(int param1Int, long param1Long) {
      ScrapData scrapData = getScrapDataForType(param1Int);
      scrapData.mBindRunningAverageNs = runningAverage(scrapData.mBindRunningAverageNs, param1Long);
    }
    
    void factorInCreateTime(int param1Int, long param1Long) {
      ScrapData scrapData = getScrapDataForType(param1Int);
      scrapData.mCreateRunningAverageNs = runningAverage(scrapData.mCreateRunningAverageNs, param1Long);
    }
    
    public RecyclerView.ViewHolder getRecycledView(int param1Int) {
      ScrapData scrapData = (ScrapData)this.mScrap.get(param1Int);
      if (scrapData != null && !scrapData.mScrapHeap.isEmpty()) {
        ArrayList<RecyclerView.ViewHolder> arrayList = scrapData.mScrapHeap;
        return arrayList.remove(arrayList.size() - 1);
      } 
      return null;
    }
    
    public int getRecycledViewCount(int param1Int) {
      return (getScrapDataForType(param1Int)).mScrapHeap.size();
    }
    
    void onAdapterChanged(RecyclerView.Adapter param1Adapter1, RecyclerView.Adapter param1Adapter2, boolean param1Boolean) {
      if (param1Adapter1 != null)
        detach(); 
      if (!param1Boolean && this.mAttachCount == 0)
        clear(); 
      if (param1Adapter2 != null)
        attach(param1Adapter2); 
    }
    
    public void putRecycledView(RecyclerView.ViewHolder param1ViewHolder) {
      int i = param1ViewHolder.getItemViewType();
      ArrayList<RecyclerView.ViewHolder> arrayList = (getScrapDataForType(i)).mScrapHeap;
      if (((ScrapData)this.mScrap.get(i)).mMaxScrap <= arrayList.size())
        return; 
      param1ViewHolder.resetInternal();
      arrayList.add(param1ViewHolder);
    }
    
    long runningAverage(long param1Long1, long param1Long2) {
      return (param1Long1 == 0L) ? param1Long2 : (param1Long1 / 4L * 3L + param1Long2 / 4L);
    }
    
    public void setMaxRecycledViews(int param1Int1, int param1Int2) {
      ScrapData scrapData = getScrapDataForType(param1Int1);
      scrapData.mMaxScrap = param1Int2;
      ArrayList<RecyclerView.ViewHolder> arrayList = scrapData.mScrapHeap;
      while (arrayList.size() > param1Int2)
        arrayList.remove(arrayList.size() - 1); 
    }
    
    int size() {
      byte b = 0;
      int i;
      for (i = 0; b < this.mScrap.size(); i = j) {
        ArrayList<RecyclerView.ViewHolder> arrayList = ((ScrapData)this.mScrap.valueAt(b)).mScrapHeap;
        int j = i;
        if (arrayList != null)
          j = i + arrayList.size(); 
        b++;
      } 
      return i;
    }
    
    boolean willBindInTime(int param1Int, long param1Long1, long param1Long2) {
      long l = (getScrapDataForType(param1Int)).mBindRunningAverageNs;
      return (l == 0L || param1Long1 + l < param1Long2);
    }
    
    boolean willCreateInTime(int param1Int, long param1Long1, long param1Long2) {
      long l = (getScrapDataForType(param1Int)).mCreateRunningAverageNs;
      return (l == 0L || param1Long1 + l < param1Long2);
    }
    
    static class ScrapData {
      long mBindRunningAverageNs = 0L;
      
      long mCreateRunningAverageNs = 0L;
      
      int mMaxScrap = 5;
      
      final ArrayList<RecyclerView.ViewHolder> mScrapHeap = new ArrayList<RecyclerView.ViewHolder>();
    }
  }
  
  static class ScrapData {
    long mBindRunningAverageNs = 0L;
    
    long mCreateRunningAverageNs = 0L;
    
    int mMaxScrap = 5;
    
    final ArrayList<RecyclerView.ViewHolder> mScrapHeap = new ArrayList<RecyclerView.ViewHolder>();
  }
  
  public final class Recycler {
    static final int DEFAULT_CACHE_SIZE = 2;
    
    final ArrayList<RecyclerView.ViewHolder> mAttachedScrap = new ArrayList<RecyclerView.ViewHolder>();
    
    final ArrayList<RecyclerView.ViewHolder> mCachedViews = new ArrayList<RecyclerView.ViewHolder>();
    
    ArrayList<RecyclerView.ViewHolder> mChangedScrap = null;
    
    RecyclerView.RecycledViewPool mRecyclerPool;
    
    private int mRequestedCacheMax = 2;
    
    private final List<RecyclerView.ViewHolder> mUnmodifiableAttachedScrap = Collections.unmodifiableList(this.mAttachedScrap);
    
    private RecyclerView.ViewCacheExtension mViewCacheExtension;
    
    int mViewCacheMax = 2;
    
    private void attachAccessibilityDelegateOnBind(RecyclerView.ViewHolder param1ViewHolder) {
      if (RecyclerView.this.isAccessibilityEnabled()) {
        View view = param1ViewHolder.itemView;
        if (ViewCompat.getImportantForAccessibility(view) == 0)
          ViewCompat.setImportantForAccessibility(view, 1); 
        if (!ViewCompat.hasAccessibilityDelegate(view)) {
          param1ViewHolder.addFlags(16384);
          ViewCompat.setAccessibilityDelegate(view, RecyclerView.this.mAccessibilityDelegate.getItemDelegate());
        } 
      } 
    }
    
    private void invalidateDisplayListInt(RecyclerView.ViewHolder param1ViewHolder) {
      if (param1ViewHolder.itemView instanceof ViewGroup)
        invalidateDisplayListInt((ViewGroup)param1ViewHolder.itemView, false); 
    }
    
    private void invalidateDisplayListInt(ViewGroup param1ViewGroup, boolean param1Boolean) {
      int i;
      for (i = param1ViewGroup.getChildCount() - 1; i >= 0; i--) {
        View view = param1ViewGroup.getChildAt(i);
        if (view instanceof ViewGroup)
          invalidateDisplayListInt((ViewGroup)view, true); 
      } 
      if (!param1Boolean)
        return; 
      if (param1ViewGroup.getVisibility() == 4) {
        param1ViewGroup.setVisibility(0);
        param1ViewGroup.setVisibility(4);
      } else {
        i = param1ViewGroup.getVisibility();
        param1ViewGroup.setVisibility(4);
        param1ViewGroup.setVisibility(i);
      } 
    }
    
    private boolean tryBindViewHolderByDeadline(RecyclerView.ViewHolder param1ViewHolder, int param1Int1, int param1Int2, long param1Long) {
      param1ViewHolder.mOwnerRecyclerView = RecyclerView.this;
      int i = param1ViewHolder.getItemViewType();
      long l = RecyclerView.this.getNanoTime();
      if (param1Long != Long.MAX_VALUE && !this.mRecyclerPool.willBindInTime(i, l, param1Long))
        return false; 
      RecyclerView.this.mAdapter.bindViewHolder(param1ViewHolder, param1Int1);
      param1Long = RecyclerView.this.getNanoTime();
      this.mRecyclerPool.factorInBindTime(param1ViewHolder.getItemViewType(), param1Long - l);
      attachAccessibilityDelegateOnBind(param1ViewHolder);
      if (RecyclerView.this.mState.isPreLayout())
        param1ViewHolder.mPreLayoutPosition = param1Int2; 
      return true;
    }
    
    void addViewHolderToRecycledViewPool(RecyclerView.ViewHolder param1ViewHolder, boolean param1Boolean) {
      RecyclerView.clearNestedRecyclerViewIfNotNested(param1ViewHolder);
      if (param1ViewHolder.hasAnyOfTheFlags(16384)) {
        param1ViewHolder.setFlags(0, 16384);
        ViewCompat.setAccessibilityDelegate(param1ViewHolder.itemView, null);
      } 
      if (param1Boolean)
        dispatchViewRecycled(param1ViewHolder); 
      param1ViewHolder.mOwnerRecyclerView = null;
      getRecycledViewPool().putRecycledView(param1ViewHolder);
    }
    
    public void bindViewToPosition(View param1View, int param1Int) {
      RecyclerView.ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(param1View);
      if (viewHolder != null) {
        int i = RecyclerView.this.mAdapterHelper.findPositionOffset(param1Int);
        if (i >= 0 && i < RecyclerView.this.mAdapter.getItemCount()) {
          RecyclerView.LayoutParams layoutParams;
          tryBindViewHolderByDeadline(viewHolder, i, param1Int, Long.MAX_VALUE);
          ViewGroup.LayoutParams layoutParams1 = viewHolder.itemView.getLayoutParams();
          if (layoutParams1 == null) {
            layoutParams = (RecyclerView.LayoutParams)RecyclerView.this.generateDefaultLayoutParams();
            viewHolder.itemView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
          } else if (!RecyclerView.this.checkLayoutParams((ViewGroup.LayoutParams)layoutParams)) {
            layoutParams = (RecyclerView.LayoutParams)RecyclerView.this.generateLayoutParams((ViewGroup.LayoutParams)layoutParams);
            viewHolder.itemView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
          } else {
            layoutParams = layoutParams;
          } 
          boolean bool = true;
          layoutParams.mInsetsDirty = true;
          layoutParams.mViewHolder = viewHolder;
          if (viewHolder.itemView.getParent() != null)
            bool = false; 
          layoutParams.mPendingInvalidate = bool;
          return;
        } 
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Inconsistency detected. Invalid item position ");
        stringBuilder1.append(param1Int);
        stringBuilder1.append("(offset:");
        stringBuilder1.append(i);
        stringBuilder1.append(").");
        stringBuilder1.append("state:");
        stringBuilder1.append(RecyclerView.this.mState.getItemCount());
        stringBuilder1.append(RecyclerView.this.exceptionLabel());
        throw new IndexOutOfBoundsException(stringBuilder1.toString());
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("The view does not have a ViewHolder. You cannot pass arbitrary views to this method, they should be created by the Adapter");
      stringBuilder.append(RecyclerView.this.exceptionLabel());
      throw new IllegalArgumentException(stringBuilder.toString());
    }
    
    public void clear() {
      this.mAttachedScrap.clear();
      recycleAndClearCachedViews();
    }
    
    void clearOldPositions() {
      int i = this.mCachedViews.size();
      boolean bool = false;
      byte b;
      for (b = 0; b < i; b++)
        ((RecyclerView.ViewHolder)this.mCachedViews.get(b)).clearOldPosition(); 
      i = this.mAttachedScrap.size();
      for (b = 0; b < i; b++)
        ((RecyclerView.ViewHolder)this.mAttachedScrap.get(b)).clearOldPosition(); 
      ArrayList<RecyclerView.ViewHolder> arrayList = this.mChangedScrap;
      if (arrayList != null) {
        i = arrayList.size();
        for (b = bool; b < i; b++)
          ((RecyclerView.ViewHolder)this.mChangedScrap.get(b)).clearOldPosition(); 
      } 
    }
    
    void clearScrap() {
      this.mAttachedScrap.clear();
      ArrayList<RecyclerView.ViewHolder> arrayList = this.mChangedScrap;
      if (arrayList != null)
        arrayList.clear(); 
    }
    
    public int convertPreLayoutPositionToPostLayout(int param1Int) {
      if (param1Int >= 0 && param1Int < RecyclerView.this.mState.getItemCount())
        return !RecyclerView.this.mState.isPreLayout() ? param1Int : RecyclerView.this.mAdapterHelper.findPositionOffset(param1Int); 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("invalid position ");
      stringBuilder.append(param1Int);
      stringBuilder.append(". State ");
      stringBuilder.append("item count is ");
      stringBuilder.append(RecyclerView.this.mState.getItemCount());
      stringBuilder.append(RecyclerView.this.exceptionLabel());
      throw new IndexOutOfBoundsException(stringBuilder.toString());
    }
    
    void dispatchViewRecycled(RecyclerView.ViewHolder param1ViewHolder) {
      if (RecyclerView.this.mRecyclerListener != null)
        RecyclerView.this.mRecyclerListener.onViewRecycled(param1ViewHolder); 
      if (RecyclerView.this.mAdapter != null)
        RecyclerView.this.mAdapter.onViewRecycled(param1ViewHolder); 
      if (RecyclerView.this.mState != null)
        RecyclerView.this.mViewInfoStore.removeViewHolder(param1ViewHolder); 
    }
    
    RecyclerView.ViewHolder getChangedScrapViewForPosition(int param1Int) {
      ArrayList<RecyclerView.ViewHolder> arrayList = this.mChangedScrap;
      if (arrayList != null) {
        int i = arrayList.size();
        if (i != 0) {
          boolean bool = false;
          for (byte b = 0; b < i; b++) {
            RecyclerView.ViewHolder viewHolder = this.mChangedScrap.get(b);
            if (!viewHolder.wasReturnedFromScrap() && viewHolder.getLayoutPosition() == param1Int) {
              viewHolder.addFlags(32);
              return viewHolder;
            } 
          } 
          if (RecyclerView.this.mAdapter.hasStableIds()) {
            param1Int = RecyclerView.this.mAdapterHelper.findPositionOffset(param1Int);
            if (param1Int > 0 && param1Int < RecyclerView.this.mAdapter.getItemCount()) {
              long l = RecyclerView.this.mAdapter.getItemId(param1Int);
              for (param1Int = bool; param1Int < i; param1Int++) {
                RecyclerView.ViewHolder viewHolder = this.mChangedScrap.get(param1Int);
                if (!viewHolder.wasReturnedFromScrap() && viewHolder.getItemId() == l) {
                  viewHolder.addFlags(32);
                  return viewHolder;
                } 
              } 
            } 
          } 
        } 
      } 
      return null;
    }
    
    RecyclerView.RecycledViewPool getRecycledViewPool() {
      if (this.mRecyclerPool == null)
        this.mRecyclerPool = new RecyclerView.RecycledViewPool(); 
      return this.mRecyclerPool;
    }
    
    int getScrapCount() {
      return this.mAttachedScrap.size();
    }
    
    public List<RecyclerView.ViewHolder> getScrapList() {
      return this.mUnmodifiableAttachedScrap;
    }
    
    RecyclerView.ViewHolder getScrapOrCachedViewForId(long param1Long, int param1Int, boolean param1Boolean) {
      int i;
      for (i = this.mAttachedScrap.size() - 1; i >= 0; i--) {
        RecyclerView.ViewHolder viewHolder = this.mAttachedScrap.get(i);
        if (viewHolder.getItemId() == param1Long && !viewHolder.wasReturnedFromScrap()) {
          if (param1Int == viewHolder.getItemViewType()) {
            viewHolder.addFlags(32);
            if (viewHolder.isRemoved() && !RecyclerView.this.mState.isPreLayout())
              viewHolder.setFlags(2, 14); 
            return viewHolder;
          } 
          if (!param1Boolean) {
            this.mAttachedScrap.remove(i);
            RecyclerView.this.removeDetachedView(viewHolder.itemView, false);
            quickRecycleScrapView(viewHolder.itemView);
          } 
        } 
      } 
      for (i = this.mCachedViews.size() - 1; i >= 0; i--) {
        RecyclerView.ViewHolder viewHolder = this.mCachedViews.get(i);
        if (viewHolder.getItemId() == param1Long) {
          if (param1Int == viewHolder.getItemViewType()) {
            if (!param1Boolean)
              this.mCachedViews.remove(i); 
            return viewHolder;
          } 
          if (!param1Boolean) {
            recycleCachedViewAt(i);
            return null;
          } 
        } 
      } 
      return null;
    }
    
    RecyclerView.ViewHolder getScrapOrHiddenOrCachedHolderForPosition(int param1Int, boolean param1Boolean) {
      int i = this.mAttachedScrap.size();
      boolean bool = false;
      byte b;
      for (b = 0; b < i; b++) {
        RecyclerView.ViewHolder viewHolder = this.mAttachedScrap.get(b);
        if (!viewHolder.wasReturnedFromScrap() && viewHolder.getLayoutPosition() == param1Int && !viewHolder.isInvalid() && (RecyclerView.this.mState.mInPreLayout || !viewHolder.isRemoved())) {
          viewHolder.addFlags(32);
          return viewHolder;
        } 
      } 
      if (!param1Boolean) {
        View view = RecyclerView.this.mChildHelper.findHiddenNonRemovedView(param1Int);
        if (view != null) {
          RecyclerView.ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(view);
          RecyclerView.this.mChildHelper.unhide(view);
          param1Int = RecyclerView.this.mChildHelper.indexOfChild(view);
          if (param1Int != -1) {
            RecyclerView.this.mChildHelper.detachViewFromParent(param1Int);
            scrapView(view);
            viewHolder.addFlags(8224);
            return viewHolder;
          } 
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("layout index should not be -1 after unhiding a view:");
          stringBuilder.append(viewHolder);
          stringBuilder.append(RecyclerView.this.exceptionLabel());
          throw new IllegalStateException(stringBuilder.toString());
        } 
      } 
      i = this.mCachedViews.size();
      for (b = bool; b < i; b++) {
        RecyclerView.ViewHolder viewHolder = this.mCachedViews.get(b);
        if (!viewHolder.isInvalid() && viewHolder.getLayoutPosition() == param1Int) {
          if (!param1Boolean)
            this.mCachedViews.remove(b); 
          return viewHolder;
        } 
      } 
      return null;
    }
    
    View getScrapViewAt(int param1Int) {
      return ((RecyclerView.ViewHolder)this.mAttachedScrap.get(param1Int)).itemView;
    }
    
    public View getViewForPosition(int param1Int) {
      return getViewForPosition(param1Int, false);
    }
    
    View getViewForPosition(int param1Int, boolean param1Boolean) {
      return (tryGetViewHolderForPositionByDeadline(param1Int, param1Boolean, Long.MAX_VALUE)).itemView;
    }
    
    void markItemDecorInsetsDirty() {
      int i = this.mCachedViews.size();
      for (byte b = 0; b < i; b++) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)((RecyclerView.ViewHolder)this.mCachedViews.get(b)).itemView.getLayoutParams();
        if (layoutParams != null)
          layoutParams.mInsetsDirty = true; 
      } 
    }
    
    void markKnownViewsInvalid() {
      int i = this.mCachedViews.size();
      for (byte b = 0; b < i; b++) {
        RecyclerView.ViewHolder viewHolder = this.mCachedViews.get(b);
        if (viewHolder != null) {
          viewHolder.addFlags(6);
          viewHolder.addChangePayload(null);
        } 
      } 
      if (RecyclerView.this.mAdapter == null || !RecyclerView.this.mAdapter.hasStableIds())
        recycleAndClearCachedViews(); 
    }
    
    void offsetPositionRecordsForInsert(int param1Int1, int param1Int2) {
      int i = this.mCachedViews.size();
      for (byte b = 0; b < i; b++) {
        RecyclerView.ViewHolder viewHolder = this.mCachedViews.get(b);
        if (viewHolder != null && viewHolder.mPosition >= param1Int1)
          viewHolder.offsetPosition(param1Int2, true); 
      } 
    }
    
    void offsetPositionRecordsForMove(int param1Int1, int param1Int2) {
      boolean bool;
      int i;
      int j;
      if (param1Int1 < param1Int2) {
        bool = true;
        i = param1Int1;
        j = param1Int2;
      } else {
        bool = true;
        j = param1Int1;
        i = param1Int2;
      } 
      int k = this.mCachedViews.size();
      for (byte b = 0; b < k; b++) {
        RecyclerView.ViewHolder viewHolder = this.mCachedViews.get(b);
        if (viewHolder != null && viewHolder.mPosition >= i && viewHolder.mPosition <= j)
          if (viewHolder.mPosition == param1Int1) {
            viewHolder.offsetPosition(param1Int2 - param1Int1, false);
          } else {
            viewHolder.offsetPosition(bool, false);
          }  
      } 
    }
    
    void offsetPositionRecordsForRemove(int param1Int1, int param1Int2, boolean param1Boolean) {
      for (int i = this.mCachedViews.size() - 1; i >= 0; i--) {
        RecyclerView.ViewHolder viewHolder = this.mCachedViews.get(i);
        if (viewHolder != null)
          if (viewHolder.mPosition >= param1Int1 + param1Int2) {
            viewHolder.offsetPosition(-param1Int2, param1Boolean);
          } else if (viewHolder.mPosition >= param1Int1) {
            viewHolder.addFlags(8);
            recycleCachedViewAt(i);
          }  
      } 
    }
    
    void onAdapterChanged(RecyclerView.Adapter param1Adapter1, RecyclerView.Adapter param1Adapter2, boolean param1Boolean) {
      clear();
      getRecycledViewPool().onAdapterChanged(param1Adapter1, param1Adapter2, param1Boolean);
    }
    
    void quickRecycleScrapView(View param1View) {
      RecyclerView.ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(param1View);
      RecyclerView.ViewHolder.access$1002(viewHolder, null);
      RecyclerView.ViewHolder.access$1102(viewHolder, false);
      viewHolder.clearReturnedFromScrapFlag();
      recycleViewHolderInternal(viewHolder);
    }
    
    void recycleAndClearCachedViews() {
      for (int i = this.mCachedViews.size() - 1; i >= 0; i--)
        recycleCachedViewAt(i); 
      this.mCachedViews.clear();
      if (RecyclerView.ALLOW_THREAD_GAP_WORK)
        RecyclerView.this.mPrefetchRegistry.clearPrefetchPositions(); 
    }
    
    void recycleCachedViewAt(int param1Int) {
      addViewHolderToRecycledViewPool(this.mCachedViews.get(param1Int), true);
      this.mCachedViews.remove(param1Int);
    }
    
    public void recycleView(View param1View) {
      RecyclerView.ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(param1View);
      if (viewHolder.isTmpDetached())
        RecyclerView.this.removeDetachedView(param1View, false); 
      if (viewHolder.isScrap()) {
        viewHolder.unScrap();
      } else if (viewHolder.wasReturnedFromScrap()) {
        viewHolder.clearReturnedFromScrapFlag();
      } 
      recycleViewHolderInternal(viewHolder);
    }
    
    void recycleViewHolderInternal(RecyclerView.ViewHolder param1ViewHolder) {
      // Byte code:
      //   0: aload_1
      //   1: invokevirtual isScrap : ()Z
      //   4: istore_2
      //   5: iconst_0
      //   6: istore_3
      //   7: iconst_0
      //   8: istore #4
      //   10: iconst_1
      //   11: istore #5
      //   13: iload_2
      //   14: ifne -> 427
      //   17: aload_1
      //   18: getfield itemView : Landroid/view/View;
      //   21: invokevirtual getParent : ()Landroid/view/ViewParent;
      //   24: ifnull -> 30
      //   27: goto -> 427
      //   30: aload_1
      //   31: invokevirtual isTmpDetached : ()Z
      //   34: ifne -> 376
      //   37: aload_1
      //   38: invokevirtual shouldIgnore : ()Z
      //   41: ifne -> 336
      //   44: aload_1
      //   45: invokestatic access$900 : (Landroid/support/v7/widget/RecyclerView$ViewHolder;)Z
      //   48: istore_3
      //   49: aload_0
      //   50: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   53: getfield mAdapter : Landroid/support/v7/widget/RecyclerView$Adapter;
      //   56: ifnull -> 83
      //   59: iload_3
      //   60: ifeq -> 83
      //   63: aload_0
      //   64: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   67: getfield mAdapter : Landroid/support/v7/widget/RecyclerView$Adapter;
      //   70: aload_1
      //   71: invokevirtual onFailedToRecycleView : (Landroid/support/v7/widget/RecyclerView$ViewHolder;)Z
      //   74: ifeq -> 83
      //   77: iconst_1
      //   78: istore #6
      //   80: goto -> 86
      //   83: iconst_0
      //   84: istore #6
      //   86: iload #6
      //   88: ifne -> 111
      //   91: iload #4
      //   93: istore #6
      //   95: aload_1
      //   96: invokevirtual isRecyclable : ()Z
      //   99: ifeq -> 105
      //   102: goto -> 111
      //   105: iconst_0
      //   106: istore #4
      //   108: goto -> 305
      //   111: aload_0
      //   112: getfield mViewCacheMax : I
      //   115: ifle -> 281
      //   118: aload_1
      //   119: sipush #526
      //   122: invokevirtual hasAnyOfTheFlags : (I)Z
      //   125: ifne -> 281
      //   128: aload_0
      //   129: getfield mCachedViews : Ljava/util/ArrayList;
      //   132: invokevirtual size : ()I
      //   135: istore #4
      //   137: iload #4
      //   139: istore #6
      //   141: iload #4
      //   143: aload_0
      //   144: getfield mViewCacheMax : I
      //   147: if_icmplt -> 170
      //   150: iload #4
      //   152: istore #6
      //   154: iload #4
      //   156: ifle -> 170
      //   159: aload_0
      //   160: iconst_0
      //   161: invokevirtual recycleCachedViewAt : (I)V
      //   164: iload #4
      //   166: iconst_1
      //   167: isub
      //   168: istore #6
      //   170: iload #6
      //   172: istore #4
      //   174: invokestatic access$800 : ()Z
      //   177: ifeq -> 265
      //   180: iload #6
      //   182: istore #4
      //   184: iload #6
      //   186: ifle -> 265
      //   189: iload #6
      //   191: istore #4
      //   193: aload_0
      //   194: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   197: getfield mPrefetchRegistry : Landroid/support/v7/widget/GapWorker$LayoutPrefetchRegistryImpl;
      //   200: aload_1
      //   201: getfield mPosition : I
      //   204: invokevirtual lastPrefetchIncludedPosition : (I)Z
      //   207: ifne -> 265
      //   210: iinc #6, -1
      //   213: iload #6
      //   215: iflt -> 259
      //   218: aload_0
      //   219: getfield mCachedViews : Ljava/util/ArrayList;
      //   222: iload #6
      //   224: invokevirtual get : (I)Ljava/lang/Object;
      //   227: checkcast android/support/v7/widget/RecyclerView$ViewHolder
      //   230: getfield mPosition : I
      //   233: istore #4
      //   235: aload_0
      //   236: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   239: getfield mPrefetchRegistry : Landroid/support/v7/widget/GapWorker$LayoutPrefetchRegistryImpl;
      //   242: iload #4
      //   244: invokevirtual lastPrefetchIncludedPosition : (I)Z
      //   247: ifne -> 253
      //   250: goto -> 259
      //   253: iinc #6, -1
      //   256: goto -> 213
      //   259: iload #6
      //   261: iconst_1
      //   262: iadd
      //   263: istore #4
      //   265: aload_0
      //   266: getfield mCachedViews : Ljava/util/ArrayList;
      //   269: iload #4
      //   271: aload_1
      //   272: invokevirtual add : (ILjava/lang/Object;)V
      //   275: iconst_1
      //   276: istore #6
      //   278: goto -> 284
      //   281: iconst_0
      //   282: istore #6
      //   284: iload #6
      //   286: ifne -> 302
      //   289: aload_0
      //   290: aload_1
      //   291: iconst_1
      //   292: invokevirtual addViewHolderToRecycledViewPool : (Landroid/support/v7/widget/RecyclerView$ViewHolder;Z)V
      //   295: iload #5
      //   297: istore #4
      //   299: goto -> 305
      //   302: goto -> 105
      //   305: aload_0
      //   306: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   309: getfield mViewInfoStore : Landroid/support/v7/widget/ViewInfoStore;
      //   312: aload_1
      //   313: invokevirtual removeViewHolder : (Landroid/support/v7/widget/RecyclerView$ViewHolder;)V
      //   316: iload #6
      //   318: ifne -> 335
      //   321: iload #4
      //   323: ifne -> 335
      //   326: iload_3
      //   327: ifeq -> 335
      //   330: aload_1
      //   331: aconst_null
      //   332: putfield mOwnerRecyclerView : Landroid/support/v7/widget/RecyclerView;
      //   335: return
      //   336: new java/lang/StringBuilder
      //   339: dup
      //   340: invokespecial <init> : ()V
      //   343: astore_1
      //   344: aload_1
      //   345: ldc_w 'Trying to recycle an ignored view holder. You should first call stopIgnoringView(view) before calling recycle.'
      //   348: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   351: pop
      //   352: aload_1
      //   353: aload_0
      //   354: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   357: invokevirtual exceptionLabel : ()Ljava/lang/String;
      //   360: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   363: pop
      //   364: new java/lang/IllegalArgumentException
      //   367: dup
      //   368: aload_1
      //   369: invokevirtual toString : ()Ljava/lang/String;
      //   372: invokespecial <init> : (Ljava/lang/String;)V
      //   375: athrow
      //   376: new java/lang/StringBuilder
      //   379: dup
      //   380: invokespecial <init> : ()V
      //   383: astore #7
      //   385: aload #7
      //   387: ldc_w 'Tmp detached view should be removed from RecyclerView before it can be recycled: '
      //   390: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   393: pop
      //   394: aload #7
      //   396: aload_1
      //   397: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   400: pop
      //   401: aload #7
      //   403: aload_0
      //   404: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   407: invokevirtual exceptionLabel : ()Ljava/lang/String;
      //   410: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   413: pop
      //   414: new java/lang/IllegalArgumentException
      //   417: dup
      //   418: aload #7
      //   420: invokevirtual toString : ()Ljava/lang/String;
      //   423: invokespecial <init> : (Ljava/lang/String;)V
      //   426: athrow
      //   427: new java/lang/StringBuilder
      //   430: dup
      //   431: invokespecial <init> : ()V
      //   434: astore #7
      //   436: aload #7
      //   438: ldc_w 'Scrapped or attached views may not be recycled. isScrap:'
      //   441: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   444: pop
      //   445: aload #7
      //   447: aload_1
      //   448: invokevirtual isScrap : ()Z
      //   451: invokevirtual append : (Z)Ljava/lang/StringBuilder;
      //   454: pop
      //   455: aload #7
      //   457: ldc_w ' isAttached:'
      //   460: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   463: pop
      //   464: aload_1
      //   465: getfield itemView : Landroid/view/View;
      //   468: invokevirtual getParent : ()Landroid/view/ViewParent;
      //   471: ifnull -> 476
      //   474: iconst_1
      //   475: istore_3
      //   476: aload #7
      //   478: iload_3
      //   479: invokevirtual append : (Z)Ljava/lang/StringBuilder;
      //   482: pop
      //   483: aload #7
      //   485: aload_0
      //   486: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   489: invokevirtual exceptionLabel : ()Ljava/lang/String;
      //   492: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   495: pop
      //   496: new java/lang/IllegalArgumentException
      //   499: dup
      //   500: aload #7
      //   502: invokevirtual toString : ()Ljava/lang/String;
      //   505: invokespecial <init> : (Ljava/lang/String;)V
      //   508: athrow
    }
    
    void recycleViewInternal(View param1View) {
      recycleViewHolderInternal(RecyclerView.getChildViewHolderInt(param1View));
    }
    
    void scrapView(View param1View) {
      StringBuilder stringBuilder;
      RecyclerView.ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(param1View);
      if (viewHolder.hasAnyOfTheFlags(12) || !viewHolder.isUpdated() || RecyclerView.this.canReuseUpdatedViewHolder(viewHolder)) {
        if (!viewHolder.isInvalid() || viewHolder.isRemoved() || RecyclerView.this.mAdapter.hasStableIds()) {
          viewHolder.setScrapContainer(this, false);
          this.mAttachedScrap.add(viewHolder);
          return;
        } 
        stringBuilder = new StringBuilder();
        stringBuilder.append("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool.");
        stringBuilder.append(RecyclerView.this.exceptionLabel());
        throw new IllegalArgumentException(stringBuilder.toString());
      } 
      if (this.mChangedScrap == null)
        this.mChangedScrap = new ArrayList<RecyclerView.ViewHolder>(); 
      stringBuilder.setScrapContainer(this, true);
      this.mChangedScrap.add(stringBuilder);
    }
    
    void setRecycledViewPool(RecyclerView.RecycledViewPool param1RecycledViewPool) {
      RecyclerView.RecycledViewPool recycledViewPool = this.mRecyclerPool;
      if (recycledViewPool != null)
        recycledViewPool.detach(); 
      this.mRecyclerPool = param1RecycledViewPool;
      if (param1RecycledViewPool != null)
        param1RecycledViewPool.attach(RecyclerView.this.getAdapter()); 
    }
    
    void setViewCacheExtension(RecyclerView.ViewCacheExtension param1ViewCacheExtension) {
      this.mViewCacheExtension = param1ViewCacheExtension;
    }
    
    public void setViewCacheSize(int param1Int) {
      this.mRequestedCacheMax = param1Int;
      updateViewCacheSize();
    }
    
    RecyclerView.ViewHolder tryGetViewHolderForPositionByDeadline(int param1Int, boolean param1Boolean, long param1Long) {
      // Byte code:
      //   0: iload_1
      //   1: iflt -> 1055
      //   4: iload_1
      //   5: aload_0
      //   6: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   9: getfield mState : Landroid/support/v7/widget/RecyclerView$State;
      //   12: invokevirtual getItemCount : ()I
      //   15: if_icmpge -> 1055
      //   18: aload_0
      //   19: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   22: getfield mState : Landroid/support/v7/widget/RecyclerView$State;
      //   25: invokevirtual isPreLayout : ()Z
      //   28: istore #5
      //   30: iconst_1
      //   31: istore #6
      //   33: iload #5
      //   35: ifeq -> 60
      //   38: aload_0
      //   39: iload_1
      //   40: invokevirtual getChangedScrapViewForPosition : (I)Landroid/support/v7/widget/RecyclerView$ViewHolder;
      //   43: astore #7
      //   45: aload #7
      //   47: astore #8
      //   49: aload #7
      //   51: ifnull -> 63
      //   54: iconst_1
      //   55: istore #9
      //   57: goto -> 70
      //   60: aconst_null
      //   61: astore #8
      //   63: iconst_0
      //   64: istore #9
      //   66: aload #8
      //   68: astore #7
      //   70: aload #7
      //   72: astore #8
      //   74: iload #9
      //   76: istore #10
      //   78: aload #7
      //   80: ifnonnull -> 188
      //   83: aload_0
      //   84: iload_1
      //   85: iload_2
      //   86: invokevirtual getScrapOrHiddenOrCachedHolderForPosition : (IZ)Landroid/support/v7/widget/RecyclerView$ViewHolder;
      //   89: astore #7
      //   91: aload #7
      //   93: astore #8
      //   95: iload #9
      //   97: istore #10
      //   99: aload #7
      //   101: ifnull -> 188
      //   104: aload_0
      //   105: aload #7
      //   107: invokevirtual validateViewHolderForOffsetPosition : (Landroid/support/v7/widget/RecyclerView$ViewHolder;)Z
      //   110: ifne -> 181
      //   113: iload_2
      //   114: ifne -> 171
      //   117: aload #7
      //   119: iconst_4
      //   120: invokevirtual addFlags : (I)V
      //   123: aload #7
      //   125: invokevirtual isScrap : ()Z
      //   128: ifeq -> 152
      //   131: aload_0
      //   132: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   135: aload #7
      //   137: getfield itemView : Landroid/view/View;
      //   140: iconst_0
      //   141: invokevirtual removeDetachedView : (Landroid/view/View;Z)V
      //   144: aload #7
      //   146: invokevirtual unScrap : ()V
      //   149: goto -> 165
      //   152: aload #7
      //   154: invokevirtual wasReturnedFromScrap : ()Z
      //   157: ifeq -> 165
      //   160: aload #7
      //   162: invokevirtual clearReturnedFromScrapFlag : ()V
      //   165: aload_0
      //   166: aload #7
      //   168: invokevirtual recycleViewHolderInternal : (Landroid/support/v7/widget/RecyclerView$ViewHolder;)V
      //   171: aconst_null
      //   172: astore #8
      //   174: iload #9
      //   176: istore #10
      //   178: goto -> 188
      //   181: iconst_1
      //   182: istore #10
      //   184: aload #7
      //   186: astore #8
      //   188: aload #8
      //   190: astore #11
      //   192: iload #10
      //   194: istore #12
      //   196: aload #8
      //   198: ifnonnull -> 745
      //   201: aload_0
      //   202: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   205: getfield mAdapterHelper : Landroid/support/v7/widget/AdapterHelper;
      //   208: iload_1
      //   209: invokevirtual findPositionOffset : (I)I
      //   212: istore #12
      //   214: iload #12
      //   216: iflt -> 644
      //   219: iload #12
      //   221: aload_0
      //   222: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   225: getfield mAdapter : Landroid/support/v7/widget/RecyclerView$Adapter;
      //   228: invokevirtual getItemCount : ()I
      //   231: if_icmpge -> 644
      //   234: aload_0
      //   235: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   238: getfield mAdapter : Landroid/support/v7/widget/RecyclerView$Adapter;
      //   241: iload #12
      //   243: invokevirtual getItemViewType : (I)I
      //   246: istore #13
      //   248: aload #8
      //   250: astore #7
      //   252: iload #10
      //   254: istore #9
      //   256: aload_0
      //   257: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   260: getfield mAdapter : Landroid/support/v7/widget/RecyclerView$Adapter;
      //   263: invokevirtual hasStableIds : ()Z
      //   266: ifeq -> 317
      //   269: aload_0
      //   270: aload_0
      //   271: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   274: getfield mAdapter : Landroid/support/v7/widget/RecyclerView$Adapter;
      //   277: iload #12
      //   279: invokevirtual getItemId : (I)J
      //   282: iload #13
      //   284: iload_2
      //   285: invokevirtual getScrapOrCachedViewForId : (JIZ)Landroid/support/v7/widget/RecyclerView$ViewHolder;
      //   288: astore #8
      //   290: aload #8
      //   292: astore #7
      //   294: iload #10
      //   296: istore #9
      //   298: aload #8
      //   300: ifnull -> 317
      //   303: aload #8
      //   305: iload #12
      //   307: putfield mPosition : I
      //   310: iconst_1
      //   311: istore #9
      //   313: aload #8
      //   315: astore #7
      //   317: aload #7
      //   319: astore #8
      //   321: aload #7
      //   323: ifnonnull -> 476
      //   326: aload_0
      //   327: getfield mViewCacheExtension : Landroid/support/v7/widget/RecyclerView$ViewCacheExtension;
      //   330: astore #11
      //   332: aload #7
      //   334: astore #8
      //   336: aload #11
      //   338: ifnull -> 476
      //   341: aload #11
      //   343: aload_0
      //   344: iload_1
      //   345: iload #13
      //   347: invokevirtual getViewForPositionAndType : (Landroid/support/v7/widget/RecyclerView$Recycler;II)Landroid/view/View;
      //   350: astore #11
      //   352: aload #7
      //   354: astore #8
      //   356: aload #11
      //   358: ifnull -> 476
      //   361: aload_0
      //   362: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   365: aload #11
      //   367: invokevirtual getChildViewHolder : (Landroid/view/View;)Landroid/support/v7/widget/RecyclerView$ViewHolder;
      //   370: astore #8
      //   372: aload #8
      //   374: ifnull -> 432
      //   377: aload #8
      //   379: invokevirtual shouldIgnore : ()Z
      //   382: ifne -> 388
      //   385: goto -> 476
      //   388: new java/lang/StringBuilder
      //   391: dup
      //   392: invokespecial <init> : ()V
      //   395: astore #8
      //   397: aload #8
      //   399: ldc_w 'getViewForPositionAndType returned a view that is ignored. You must call stopIgnoring before returning this view.'
      //   402: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   405: pop
      //   406: aload #8
      //   408: aload_0
      //   409: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   412: invokevirtual exceptionLabel : ()Ljava/lang/String;
      //   415: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   418: pop
      //   419: new java/lang/IllegalArgumentException
      //   422: dup
      //   423: aload #8
      //   425: invokevirtual toString : ()Ljava/lang/String;
      //   428: invokespecial <init> : (Ljava/lang/String;)V
      //   431: athrow
      //   432: new java/lang/StringBuilder
      //   435: dup
      //   436: invokespecial <init> : ()V
      //   439: astore #8
      //   441: aload #8
      //   443: ldc_w 'getViewForPositionAndType returned a view which does not have a ViewHolder'
      //   446: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   449: pop
      //   450: aload #8
      //   452: aload_0
      //   453: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   456: invokevirtual exceptionLabel : ()Ljava/lang/String;
      //   459: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   462: pop
      //   463: new java/lang/IllegalArgumentException
      //   466: dup
      //   467: aload #8
      //   469: invokevirtual toString : ()Ljava/lang/String;
      //   472: invokespecial <init> : (Ljava/lang/String;)V
      //   475: athrow
      //   476: aload #8
      //   478: astore #7
      //   480: aload #8
      //   482: ifnonnull -> 518
      //   485: aload_0
      //   486: invokevirtual getRecycledViewPool : ()Landroid/support/v7/widget/RecyclerView$RecycledViewPool;
      //   489: iload #13
      //   491: invokevirtual getRecycledView : (I)Landroid/support/v7/widget/RecyclerView$ViewHolder;
      //   494: astore #7
      //   496: aload #7
      //   498: ifnull -> 518
      //   501: aload #7
      //   503: invokevirtual resetInternal : ()V
      //   506: getstatic android/support/v7/widget/RecyclerView.FORCE_INVALIDATE_DISPLAY_LIST : Z
      //   509: ifeq -> 518
      //   512: aload_0
      //   513: aload #7
      //   515: invokespecial invalidateDisplayListInt : (Landroid/support/v7/widget/RecyclerView$ViewHolder;)V
      //   518: aload #7
      //   520: astore #11
      //   522: iload #9
      //   524: istore #12
      //   526: aload #7
      //   528: ifnonnull -> 745
      //   531: aload_0
      //   532: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   535: invokevirtual getNanoTime : ()J
      //   538: lstore #14
      //   540: lload_3
      //   541: ldc2_w 9223372036854775807
      //   544: lcmp
      //   545: ifeq -> 565
      //   548: aload_0
      //   549: getfield mRecyclerPool : Landroid/support/v7/widget/RecyclerView$RecycledViewPool;
      //   552: iload #13
      //   554: lload #14
      //   556: lload_3
      //   557: invokevirtual willCreateInTime : (IJJ)Z
      //   560: ifne -> 565
      //   563: aconst_null
      //   564: areturn
      //   565: aload_0
      //   566: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   569: getfield mAdapter : Landroid/support/v7/widget/RecyclerView$Adapter;
      //   572: aload_0
      //   573: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   576: iload #13
      //   578: invokevirtual createViewHolder : (Landroid/view/ViewGroup;I)Landroid/support/v7/widget/RecyclerView$ViewHolder;
      //   581: astore #7
      //   583: invokestatic access$800 : ()Z
      //   586: ifeq -> 618
      //   589: aload #7
      //   591: getfield itemView : Landroid/view/View;
      //   594: invokestatic findNestedRecyclerView : (Landroid/view/View;)Landroid/support/v7/widget/RecyclerView;
      //   597: astore #8
      //   599: aload #8
      //   601: ifnull -> 618
      //   604: aload #7
      //   606: new java/lang/ref/WeakReference
      //   609: dup
      //   610: aload #8
      //   612: invokespecial <init> : (Ljava/lang/Object;)V
      //   615: putfield mNestedRecyclerView : Ljava/lang/ref/WeakReference;
      //   618: aload_0
      //   619: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   622: invokevirtual getNanoTime : ()J
      //   625: lstore #16
      //   627: aload_0
      //   628: getfield mRecyclerPool : Landroid/support/v7/widget/RecyclerView$RecycledViewPool;
      //   631: iload #13
      //   633: lload #16
      //   635: lload #14
      //   637: lsub
      //   638: invokevirtual factorInCreateTime : (IJ)V
      //   641: goto -> 753
      //   644: new java/lang/StringBuilder
      //   647: dup
      //   648: invokespecial <init> : ()V
      //   651: astore #8
      //   653: aload #8
      //   655: ldc 'Inconsistency detected. Invalid item position '
      //   657: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   660: pop
      //   661: aload #8
      //   663: iload_1
      //   664: invokevirtual append : (I)Ljava/lang/StringBuilder;
      //   667: pop
      //   668: aload #8
      //   670: ldc_w '(offset:'
      //   673: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   676: pop
      //   677: aload #8
      //   679: iload #12
      //   681: invokevirtual append : (I)Ljava/lang/StringBuilder;
      //   684: pop
      //   685: aload #8
      //   687: ldc_w ').'
      //   690: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   693: pop
      //   694: aload #8
      //   696: ldc_w 'state:'
      //   699: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   702: pop
      //   703: aload #8
      //   705: aload_0
      //   706: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   709: getfield mState : Landroid/support/v7/widget/RecyclerView$State;
      //   712: invokevirtual getItemCount : ()I
      //   715: invokevirtual append : (I)Ljava/lang/StringBuilder;
      //   718: pop
      //   719: aload #8
      //   721: aload_0
      //   722: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   725: invokevirtual exceptionLabel : ()Ljava/lang/String;
      //   728: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   731: pop
      //   732: new java/lang/IndexOutOfBoundsException
      //   735: dup
      //   736: aload #8
      //   738: invokevirtual toString : ()Ljava/lang/String;
      //   741: invokespecial <init> : (Ljava/lang/String;)V
      //   744: athrow
      //   745: aload #11
      //   747: astore #7
      //   749: iload #12
      //   751: istore #9
      //   753: iload #9
      //   755: ifeq -> 854
      //   758: aload_0
      //   759: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   762: getfield mState : Landroid/support/v7/widget/RecyclerView$State;
      //   765: invokevirtual isPreLayout : ()Z
      //   768: ifne -> 854
      //   771: aload #7
      //   773: sipush #8192
      //   776: invokevirtual hasAnyOfTheFlags : (I)Z
      //   779: ifeq -> 854
      //   782: aload #7
      //   784: iconst_0
      //   785: sipush #8192
      //   788: invokevirtual setFlags : (II)V
      //   791: aload_0
      //   792: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   795: getfield mState : Landroid/support/v7/widget/RecyclerView$State;
      //   798: getfield mRunSimpleAnimations : Z
      //   801: ifeq -> 854
      //   804: aload #7
      //   806: invokestatic buildAdapterChangeFlagsForAnimations : (Landroid/support/v7/widget/RecyclerView$ViewHolder;)I
      //   809: istore #10
      //   811: aload_0
      //   812: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   815: getfield mItemAnimator : Landroid/support/v7/widget/RecyclerView$ItemAnimator;
      //   818: aload_0
      //   819: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   822: getfield mState : Landroid/support/v7/widget/RecyclerView$State;
      //   825: aload #7
      //   827: iload #10
      //   829: sipush #4096
      //   832: ior
      //   833: aload #7
      //   835: invokevirtual getUnmodifiedPayloads : ()Ljava/util/List;
      //   838: invokevirtual recordPreLayoutInformation : (Landroid/support/v7/widget/RecyclerView$State;Landroid/support/v7/widget/RecyclerView$ViewHolder;ILjava/util/List;)Landroid/support/v7/widget/RecyclerView$ItemAnimator$ItemHolderInfo;
      //   841: astore #8
      //   843: aload_0
      //   844: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   847: aload #7
      //   849: aload #8
      //   851: invokevirtual recordAnimationInfoIfBouncedHiddenView : (Landroid/support/v7/widget/RecyclerView$ViewHolder;Landroid/support/v7/widget/RecyclerView$ItemAnimator$ItemHolderInfo;)V
      //   854: aload_0
      //   855: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   858: getfield mState : Landroid/support/v7/widget/RecyclerView$State;
      //   861: invokevirtual isPreLayout : ()Z
      //   864: ifeq -> 884
      //   867: aload #7
      //   869: invokevirtual isBound : ()Z
      //   872: ifeq -> 884
      //   875: aload #7
      //   877: iload_1
      //   878: putfield mPreLayoutPosition : I
      //   881: goto -> 911
      //   884: aload #7
      //   886: invokevirtual isBound : ()Z
      //   889: ifeq -> 916
      //   892: aload #7
      //   894: invokevirtual needsUpdate : ()Z
      //   897: ifne -> 916
      //   900: aload #7
      //   902: invokevirtual isInvalid : ()Z
      //   905: ifeq -> 911
      //   908: goto -> 916
      //   911: iconst_0
      //   912: istore_2
      //   913: goto -> 936
      //   916: aload_0
      //   917: aload #7
      //   919: aload_0
      //   920: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   923: getfield mAdapterHelper : Landroid/support/v7/widget/AdapterHelper;
      //   926: iload_1
      //   927: invokevirtual findPositionOffset : (I)I
      //   930: iload_1
      //   931: lload_3
      //   932: invokespecial tryBindViewHolderByDeadline : (Landroid/support/v7/widget/RecyclerView$ViewHolder;IIJ)Z
      //   935: istore_2
      //   936: aload #7
      //   938: getfield itemView : Landroid/view/View;
      //   941: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
      //   944: astore #8
      //   946: aload #8
      //   948: ifnonnull -> 976
      //   951: aload_0
      //   952: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   955: invokevirtual generateDefaultLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
      //   958: checkcast android/support/v7/widget/RecyclerView$LayoutParams
      //   961: astore #8
      //   963: aload #7
      //   965: getfield itemView : Landroid/view/View;
      //   968: aload #8
      //   970: invokevirtual setLayoutParams : (Landroid/view/ViewGroup$LayoutParams;)V
      //   973: goto -> 1022
      //   976: aload_0
      //   977: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   980: aload #8
      //   982: invokevirtual checkLayoutParams : (Landroid/view/ViewGroup$LayoutParams;)Z
      //   985: ifne -> 1015
      //   988: aload_0
      //   989: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   992: aload #8
      //   994: invokevirtual generateLayoutParams : (Landroid/view/ViewGroup$LayoutParams;)Landroid/view/ViewGroup$LayoutParams;
      //   997: checkcast android/support/v7/widget/RecyclerView$LayoutParams
      //   1000: astore #8
      //   1002: aload #7
      //   1004: getfield itemView : Landroid/view/View;
      //   1007: aload #8
      //   1009: invokevirtual setLayoutParams : (Landroid/view/ViewGroup$LayoutParams;)V
      //   1012: goto -> 1022
      //   1015: aload #8
      //   1017: checkcast android/support/v7/widget/RecyclerView$LayoutParams
      //   1020: astore #8
      //   1022: aload #8
      //   1024: aload #7
      //   1026: putfield mViewHolder : Landroid/support/v7/widget/RecyclerView$ViewHolder;
      //   1029: iload #9
      //   1031: ifeq -> 1044
      //   1034: iload_2
      //   1035: ifeq -> 1044
      //   1038: iload #6
      //   1040: istore_2
      //   1041: goto -> 1046
      //   1044: iconst_0
      //   1045: istore_2
      //   1046: aload #8
      //   1048: iload_2
      //   1049: putfield mPendingInvalidate : Z
      //   1052: aload #7
      //   1054: areturn
      //   1055: new java/lang/StringBuilder
      //   1058: dup
      //   1059: invokespecial <init> : ()V
      //   1062: astore #8
      //   1064: aload #8
      //   1066: ldc_w 'Invalid item position '
      //   1069: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1072: pop
      //   1073: aload #8
      //   1075: iload_1
      //   1076: invokevirtual append : (I)Ljava/lang/StringBuilder;
      //   1079: pop
      //   1080: aload #8
      //   1082: ldc_w '('
      //   1085: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1088: pop
      //   1089: aload #8
      //   1091: iload_1
      //   1092: invokevirtual append : (I)Ljava/lang/StringBuilder;
      //   1095: pop
      //   1096: aload #8
      //   1098: ldc_w '). Item count:'
      //   1101: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1104: pop
      //   1105: aload #8
      //   1107: aload_0
      //   1108: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   1111: getfield mState : Landroid/support/v7/widget/RecyclerView$State;
      //   1114: invokevirtual getItemCount : ()I
      //   1117: invokevirtual append : (I)Ljava/lang/StringBuilder;
      //   1120: pop
      //   1121: aload #8
      //   1123: aload_0
      //   1124: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   1127: invokevirtual exceptionLabel : ()Ljava/lang/String;
      //   1130: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1133: pop
      //   1134: new java/lang/IndexOutOfBoundsException
      //   1137: dup
      //   1138: aload #8
      //   1140: invokevirtual toString : ()Ljava/lang/String;
      //   1143: invokespecial <init> : (Ljava/lang/String;)V
      //   1146: athrow
    }
    
    void unscrapView(RecyclerView.ViewHolder param1ViewHolder) {
      if (param1ViewHolder.mInChangeScrap) {
        this.mChangedScrap.remove(param1ViewHolder);
      } else {
        this.mAttachedScrap.remove(param1ViewHolder);
      } 
      RecyclerView.ViewHolder.access$1002(param1ViewHolder, null);
      RecyclerView.ViewHolder.access$1102(param1ViewHolder, false);
      param1ViewHolder.clearReturnedFromScrapFlag();
    }
    
    void updateViewCacheSize() {
      if (RecyclerView.this.mLayout != null) {
        i = RecyclerView.this.mLayout.mPrefetchMaxCountObserved;
      } else {
        i = 0;
      } 
      this.mViewCacheMax = this.mRequestedCacheMax + i;
      for (int i = this.mCachedViews.size() - 1; i >= 0 && this.mCachedViews.size() > this.mViewCacheMax; i--)
        recycleCachedViewAt(i); 
    }
    
    boolean validateViewHolderForOffsetPosition(RecyclerView.ViewHolder param1ViewHolder) {
      if (param1ViewHolder.isRemoved())
        return RecyclerView.this.mState.isPreLayout(); 
      if (param1ViewHolder.mPosition >= 0 && param1ViewHolder.mPosition < RecyclerView.this.mAdapter.getItemCount()) {
        boolean bool = RecyclerView.this.mState.isPreLayout();
        boolean bool1 = false;
        if (!bool && RecyclerView.this.mAdapter.getItemViewType(param1ViewHolder.mPosition) != param1ViewHolder.getItemViewType())
          return false; 
        if (RecyclerView.this.mAdapter.hasStableIds()) {
          if (param1ViewHolder.getItemId() == RecyclerView.this.mAdapter.getItemId(param1ViewHolder.mPosition))
            bool1 = true; 
          return bool1;
        } 
        return true;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Inconsistency detected. Invalid view holder adapter position");
      stringBuilder.append(param1ViewHolder);
      stringBuilder.append(RecyclerView.this.exceptionLabel());
      throw new IndexOutOfBoundsException(stringBuilder.toString());
    }
    
    void viewRangeUpdate(int param1Int1, int param1Int2) {
      for (int i = this.mCachedViews.size() - 1; i >= 0; i--) {
        RecyclerView.ViewHolder viewHolder = this.mCachedViews.get(i);
        if (viewHolder != null) {
          int j = viewHolder.mPosition;
          if (j >= param1Int1 && j < param1Int2 + param1Int1) {
            viewHolder.addFlags(2);
            recycleCachedViewAt(i);
          } 
        } 
      } 
    }
  }
  
  public static interface RecyclerListener {
    void onViewRecycled(RecyclerView.ViewHolder param1ViewHolder);
  }
  
  private class RecyclerViewDataObserver extends AdapterDataObserver {
    public void onChanged() {
      RecyclerView.this.assertNotInLayoutOrScroll((String)null);
      RecyclerView.this.mState.mStructureChanged = true;
      RecyclerView.this.processDataSetCompletelyChanged(true);
      if (!RecyclerView.this.mAdapterHelper.hasPendingUpdates())
        RecyclerView.this.requestLayout(); 
    }
    
    public void onItemRangeChanged(int param1Int1, int param1Int2, Object param1Object) {
      RecyclerView.this.assertNotInLayoutOrScroll((String)null);
      if (RecyclerView.this.mAdapterHelper.onItemRangeChanged(param1Int1, param1Int2, param1Object))
        triggerUpdateProcessor(); 
    }
    
    public void onItemRangeInserted(int param1Int1, int param1Int2) {
      RecyclerView.this.assertNotInLayoutOrScroll((String)null);
      if (RecyclerView.this.mAdapterHelper.onItemRangeInserted(param1Int1, param1Int2))
        triggerUpdateProcessor(); 
    }
    
    public void onItemRangeMoved(int param1Int1, int param1Int2, int param1Int3) {
      RecyclerView.this.assertNotInLayoutOrScroll((String)null);
      if (RecyclerView.this.mAdapterHelper.onItemRangeMoved(param1Int1, param1Int2, param1Int3))
        triggerUpdateProcessor(); 
    }
    
    public void onItemRangeRemoved(int param1Int1, int param1Int2) {
      RecyclerView.this.assertNotInLayoutOrScroll((String)null);
      if (RecyclerView.this.mAdapterHelper.onItemRangeRemoved(param1Int1, param1Int2))
        triggerUpdateProcessor(); 
    }
    
    void triggerUpdateProcessor() {
      if (RecyclerView.POST_UPDATES_ON_ANIMATION && RecyclerView.this.mHasFixedSize && RecyclerView.this.mIsAttached) {
        RecyclerView recyclerView = RecyclerView.this;
        ViewCompat.postOnAnimation((View)recyclerView, recyclerView.mUpdateChildViewsRunnable);
      } else {
        RecyclerView.this.mAdapterUpdateDuringMeasure = true;
        RecyclerView.this.requestLayout();
      } 
    }
  }
  
  public static class SavedState extends AbsSavedState {
    public static final Parcelable.Creator<SavedState> CREATOR = (Parcelable.Creator<SavedState>)new Parcelable.ClassLoaderCreator<SavedState>() {
        public RecyclerView.SavedState createFromParcel(Parcel param2Parcel) {
          return new RecyclerView.SavedState(param2Parcel, null);
        }
        
        public RecyclerView.SavedState createFromParcel(Parcel param2Parcel, ClassLoader param2ClassLoader) {
          return new RecyclerView.SavedState(param2Parcel, param2ClassLoader);
        }
        
        public RecyclerView.SavedState[] newArray(int param2Int) {
          return new RecyclerView.SavedState[param2Int];
        }
      };
    
    Parcelable mLayoutState;
    
    SavedState(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      super(param1Parcel, param1ClassLoader);
      if (param1ClassLoader == null)
        param1ClassLoader = RecyclerView.LayoutManager.class.getClassLoader(); 
      this.mLayoutState = param1Parcel.readParcelable(param1ClassLoader);
    }
    
    SavedState(Parcelable param1Parcelable) {
      super(param1Parcelable);
    }
    
    void copyFrom(SavedState param1SavedState) {
      this.mLayoutState = param1SavedState.mLayoutState;
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      super.writeToParcel(param1Parcel, param1Int);
      param1Parcel.writeParcelable(this.mLayoutState, 0);
    }
  }
  
  static final class null implements Parcelable.ClassLoaderCreator<SavedState> {
    public RecyclerView.SavedState createFromParcel(Parcel param1Parcel) {
      return new RecyclerView.SavedState(param1Parcel, null);
    }
    
    public RecyclerView.SavedState createFromParcel(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      return new RecyclerView.SavedState(param1Parcel, param1ClassLoader);
    }
    
    public RecyclerView.SavedState[] newArray(int param1Int) {
      return new RecyclerView.SavedState[param1Int];
    }
  }
  
  public static class SimpleOnItemTouchListener implements OnItemTouchListener {
    public boolean onInterceptTouchEvent(RecyclerView param1RecyclerView, MotionEvent param1MotionEvent) {
      return false;
    }
    
    public void onRequestDisallowInterceptTouchEvent(boolean param1Boolean) {}
    
    public void onTouchEvent(RecyclerView param1RecyclerView, MotionEvent param1MotionEvent) {}
  }
  
  public static abstract class SmoothScroller {
    private RecyclerView.LayoutManager mLayoutManager;
    
    private boolean mPendingInitialRun;
    
    private RecyclerView mRecyclerView;
    
    private final Action mRecyclingAction = new Action(0, 0);
    
    private boolean mRunning;
    
    private int mTargetPosition = -1;
    
    private View mTargetView;
    
    private void onAnimation(int param1Int1, int param1Int2) {
      RecyclerView recyclerView = this.mRecyclerView;
      if (!this.mRunning || this.mTargetPosition == -1 || recyclerView == null)
        stop(); 
      this.mPendingInitialRun = false;
      View view = this.mTargetView;
      if (view != null)
        if (getChildPosition(view) == this.mTargetPosition) {
          onTargetFound(this.mTargetView, recyclerView.mState, this.mRecyclingAction);
          this.mRecyclingAction.runIfNecessary(recyclerView);
          stop();
        } else {
          Log.e("RecyclerView", "Passed over target position while smooth scrolling.");
          this.mTargetView = null;
        }  
      if (this.mRunning) {
        onSeekTargetStep(param1Int1, param1Int2, recyclerView.mState, this.mRecyclingAction);
        boolean bool = this.mRecyclingAction.hasJumpTarget();
        this.mRecyclingAction.runIfNecessary(recyclerView);
        if (bool)
          if (this.mRunning) {
            this.mPendingInitialRun = true;
            recyclerView.mViewFlinger.postOnAnimation();
          } else {
            stop();
          }  
      } 
    }
    
    public View findViewByPosition(int param1Int) {
      return this.mRecyclerView.mLayout.findViewByPosition(param1Int);
    }
    
    public int getChildCount() {
      return this.mRecyclerView.mLayout.getChildCount();
    }
    
    public int getChildPosition(View param1View) {
      return this.mRecyclerView.getChildLayoutPosition(param1View);
    }
    
    public RecyclerView.LayoutManager getLayoutManager() {
      return this.mLayoutManager;
    }
    
    public int getTargetPosition() {
      return this.mTargetPosition;
    }
    
    @Deprecated
    public void instantScrollToPosition(int param1Int) {
      this.mRecyclerView.scrollToPosition(param1Int);
    }
    
    public boolean isPendingInitialRun() {
      return this.mPendingInitialRun;
    }
    
    public boolean isRunning() {
      return this.mRunning;
    }
    
    protected void normalize(PointF param1PointF) {
      float f = (float)Math.sqrt((param1PointF.x * param1PointF.x + param1PointF.y * param1PointF.y));
      param1PointF.x /= f;
      param1PointF.y /= f;
    }
    
    protected void onChildAttachedToWindow(View param1View) {
      if (getChildPosition(param1View) == getTargetPosition())
        this.mTargetView = param1View; 
    }
    
    protected abstract void onSeekTargetStep(int param1Int1, int param1Int2, RecyclerView.State param1State, Action param1Action);
    
    protected abstract void onStart();
    
    protected abstract void onStop();
    
    protected abstract void onTargetFound(View param1View, RecyclerView.State param1State, Action param1Action);
    
    public void setTargetPosition(int param1Int) {
      this.mTargetPosition = param1Int;
    }
    
    void start(RecyclerView param1RecyclerView, RecyclerView.LayoutManager param1LayoutManager) {
      this.mRecyclerView = param1RecyclerView;
      this.mLayoutManager = param1LayoutManager;
      if (this.mTargetPosition != -1) {
        RecyclerView.State.access$1302(param1RecyclerView.mState, this.mTargetPosition);
        this.mRunning = true;
        this.mPendingInitialRun = true;
        this.mTargetView = findViewByPosition(getTargetPosition());
        onStart();
        this.mRecyclerView.mViewFlinger.postOnAnimation();
        return;
      } 
      throw new IllegalArgumentException("Invalid target position");
    }
    
    protected final void stop() {
      if (!this.mRunning)
        return; 
      this.mRunning = false;
      onStop();
      RecyclerView.State.access$1302(this.mRecyclerView.mState, -1);
      this.mTargetView = null;
      this.mTargetPosition = -1;
      this.mPendingInitialRun = false;
      this.mLayoutManager.onSmoothScrollerStopped(this);
      this.mLayoutManager = null;
      this.mRecyclerView = null;
    }
    
    public static class Action {
      public static final int UNDEFINED_DURATION = -2147483648;
      
      private boolean mChanged = false;
      
      private int mConsecutiveUpdates = 0;
      
      private int mDuration;
      
      private int mDx;
      
      private int mDy;
      
      private Interpolator mInterpolator;
      
      private int mJumpToPosition = -1;
      
      public Action(int param2Int1, int param2Int2) {
        this(param2Int1, param2Int2, -2147483648, null);
      }
      
      public Action(int param2Int1, int param2Int2, int param2Int3) {
        this(param2Int1, param2Int2, param2Int3, null);
      }
      
      public Action(int param2Int1, int param2Int2, int param2Int3, Interpolator param2Interpolator) {
        this.mDx = param2Int1;
        this.mDy = param2Int2;
        this.mDuration = param2Int3;
        this.mInterpolator = param2Interpolator;
      }
      
      private void validate() {
        if (this.mInterpolator == null || this.mDuration >= 1) {
          if (this.mDuration >= 1)
            return; 
          throw new IllegalStateException("Scroll duration must be a positive number");
        } 
        throw new IllegalStateException("If you provide an interpolator, you must set a positive duration");
      }
      
      public int getDuration() {
        return this.mDuration;
      }
      
      public int getDx() {
        return this.mDx;
      }
      
      public int getDy() {
        return this.mDy;
      }
      
      public Interpolator getInterpolator() {
        return this.mInterpolator;
      }
      
      boolean hasJumpTarget() {
        boolean bool;
        if (this.mJumpToPosition >= 0) {
          bool = true;
        } else {
          bool = false;
        } 
        return bool;
      }
      
      public void jumpTo(int param2Int) {
        this.mJumpToPosition = param2Int;
      }
      
      void runIfNecessary(RecyclerView param2RecyclerView) {
        int i = this.mJumpToPosition;
        if (i >= 0) {
          this.mJumpToPosition = -1;
          param2RecyclerView.jumpToPositionForSmoothScroller(i);
          this.mChanged = false;
          return;
        } 
        if (this.mChanged) {
          validate();
          if (this.mInterpolator == null) {
            if (this.mDuration == Integer.MIN_VALUE) {
              param2RecyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy);
            } else {
              param2RecyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, this.mDuration);
            } 
          } else {
            param2RecyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, this.mDuration, this.mInterpolator);
          } 
          i = this.mConsecutiveUpdates + 1;
          this.mConsecutiveUpdates = i;
          if (i > 10)
            Log.e("RecyclerView", "Smooth Scroll action is being updated too frequently. Make sure you are not changing it unless necessary"); 
          this.mChanged = false;
        } else {
          this.mConsecutiveUpdates = 0;
        } 
      }
      
      public void setDuration(int param2Int) {
        this.mChanged = true;
        this.mDuration = param2Int;
      }
      
      public void setDx(int param2Int) {
        this.mChanged = true;
        this.mDx = param2Int;
      }
      
      public void setDy(int param2Int) {
        this.mChanged = true;
        this.mDy = param2Int;
      }
      
      public void setInterpolator(Interpolator param2Interpolator) {
        this.mChanged = true;
        this.mInterpolator = param2Interpolator;
      }
      
      public void update(int param2Int1, int param2Int2, int param2Int3, Interpolator param2Interpolator) {
        this.mDx = param2Int1;
        this.mDy = param2Int2;
        this.mDuration = param2Int3;
        this.mInterpolator = param2Interpolator;
        this.mChanged = true;
      }
    }
    
    public static interface ScrollVectorProvider {
      PointF computeScrollVectorForPosition(int param2Int);
    }
  }
  
  public static class Action {
    public static final int UNDEFINED_DURATION = -2147483648;
    
    private boolean mChanged = false;
    
    private int mConsecutiveUpdates = 0;
    
    private int mDuration;
    
    private int mDx;
    
    private int mDy;
    
    private Interpolator mInterpolator;
    
    private int mJumpToPosition = -1;
    
    public Action(int param1Int1, int param1Int2) {
      this(param1Int1, param1Int2, -2147483648, null);
    }
    
    public Action(int param1Int1, int param1Int2, int param1Int3) {
      this(param1Int1, param1Int2, param1Int3, null);
    }
    
    public Action(int param1Int1, int param1Int2, int param1Int3, Interpolator param1Interpolator) {
      this.mDx = param1Int1;
      this.mDy = param1Int2;
      this.mDuration = param1Int3;
      this.mInterpolator = param1Interpolator;
    }
    
    private void validate() {
      if (this.mInterpolator == null || this.mDuration >= 1) {
        if (this.mDuration >= 1)
          return; 
        throw new IllegalStateException("Scroll duration must be a positive number");
      } 
      throw new IllegalStateException("If you provide an interpolator, you must set a positive duration");
    }
    
    public int getDuration() {
      return this.mDuration;
    }
    
    public int getDx() {
      return this.mDx;
    }
    
    public int getDy() {
      return this.mDy;
    }
    
    public Interpolator getInterpolator() {
      return this.mInterpolator;
    }
    
    boolean hasJumpTarget() {
      boolean bool;
      if (this.mJumpToPosition >= 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public void jumpTo(int param1Int) {
      this.mJumpToPosition = param1Int;
    }
    
    void runIfNecessary(RecyclerView param1RecyclerView) {
      int i = this.mJumpToPosition;
      if (i >= 0) {
        this.mJumpToPosition = -1;
        param1RecyclerView.jumpToPositionForSmoothScroller(i);
        this.mChanged = false;
        return;
      } 
      if (this.mChanged) {
        validate();
        if (this.mInterpolator == null) {
          if (this.mDuration == Integer.MIN_VALUE) {
            param1RecyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy);
          } else {
            param1RecyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, this.mDuration);
          } 
        } else {
          param1RecyclerView.mViewFlinger.smoothScrollBy(this.mDx, this.mDy, this.mDuration, this.mInterpolator);
        } 
        i = this.mConsecutiveUpdates + 1;
        this.mConsecutiveUpdates = i;
        if (i > 10)
          Log.e("RecyclerView", "Smooth Scroll action is being updated too frequently. Make sure you are not changing it unless necessary"); 
        this.mChanged = false;
      } else {
        this.mConsecutiveUpdates = 0;
      } 
    }
    
    public void setDuration(int param1Int) {
      this.mChanged = true;
      this.mDuration = param1Int;
    }
    
    public void setDx(int param1Int) {
      this.mChanged = true;
      this.mDx = param1Int;
    }
    
    public void setDy(int param1Int) {
      this.mChanged = true;
      this.mDy = param1Int;
    }
    
    public void setInterpolator(Interpolator param1Interpolator) {
      this.mChanged = true;
      this.mInterpolator = param1Interpolator;
    }
    
    public void update(int param1Int1, int param1Int2, int param1Int3, Interpolator param1Interpolator) {
      this.mDx = param1Int1;
      this.mDy = param1Int2;
      this.mDuration = param1Int3;
      this.mInterpolator = param1Interpolator;
      this.mChanged = true;
    }
  }
  
  public static interface ScrollVectorProvider {
    PointF computeScrollVectorForPosition(int param1Int);
  }
  
  public static class State {
    static final int STEP_ANIMATIONS = 4;
    
    static final int STEP_LAYOUT = 2;
    
    static final int STEP_START = 1;
    
    private SparseArray<Object> mData;
    
    int mDeletedInvisibleItemCountSincePreviousLayout = 0;
    
    long mFocusedItemId;
    
    int mFocusedItemPosition;
    
    int mFocusedSubChildId;
    
    boolean mInPreLayout = false;
    
    boolean mIsMeasuring = false;
    
    int mItemCount = 0;
    
    int mLayoutStep = 1;
    
    int mPreviousLayoutItemCount = 0;
    
    int mRemainingScrollHorizontal;
    
    int mRemainingScrollVertical;
    
    boolean mRunPredictiveAnimations = false;
    
    boolean mRunSimpleAnimations = false;
    
    boolean mStructureChanged = false;
    
    private int mTargetPosition = -1;
    
    boolean mTrackOldChangeHolders = false;
    
    void assertLayoutStep(int param1Int) {
      if ((this.mLayoutStep & param1Int) != 0)
        return; 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Layout state should be one of ");
      stringBuilder.append(Integer.toBinaryString(param1Int));
      stringBuilder.append(" but it is ");
      stringBuilder.append(Integer.toBinaryString(this.mLayoutStep));
      throw new IllegalStateException(stringBuilder.toString());
    }
    
    public boolean didStructureChange() {
      return this.mStructureChanged;
    }
    
    public <T> T get(int param1Int) {
      SparseArray<Object> sparseArray = this.mData;
      return (T)((sparseArray == null) ? null : sparseArray.get(param1Int));
    }
    
    public int getItemCount() {
      int i;
      if (this.mInPreLayout) {
        i = this.mPreviousLayoutItemCount - this.mDeletedInvisibleItemCountSincePreviousLayout;
      } else {
        i = this.mItemCount;
      } 
      return i;
    }
    
    public int getRemainingScrollHorizontal() {
      return this.mRemainingScrollHorizontal;
    }
    
    public int getRemainingScrollVertical() {
      return this.mRemainingScrollVertical;
    }
    
    public int getTargetScrollPosition() {
      return this.mTargetPosition;
    }
    
    public boolean hasTargetScrollPosition() {
      boolean bool;
      if (this.mTargetPosition != -1) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public boolean isMeasuring() {
      return this.mIsMeasuring;
    }
    
    public boolean isPreLayout() {
      return this.mInPreLayout;
    }
    
    void prepareForNestedPrefetch(RecyclerView.Adapter param1Adapter) {
      this.mLayoutStep = 1;
      this.mItemCount = param1Adapter.getItemCount();
      this.mInPreLayout = false;
      this.mTrackOldChangeHolders = false;
      this.mIsMeasuring = false;
    }
    
    public void put(int param1Int, Object param1Object) {
      if (this.mData == null)
        this.mData = new SparseArray(); 
      this.mData.put(param1Int, param1Object);
    }
    
    public void remove(int param1Int) {
      SparseArray<Object> sparseArray = this.mData;
      if (sparseArray == null)
        return; 
      sparseArray.remove(param1Int);
    }
    
    State reset() {
      this.mTargetPosition = -1;
      SparseArray<Object> sparseArray = this.mData;
      if (sparseArray != null)
        sparseArray.clear(); 
      this.mItemCount = 0;
      this.mStructureChanged = false;
      this.mIsMeasuring = false;
      return this;
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("State{mTargetPosition=");
      stringBuilder.append(this.mTargetPosition);
      stringBuilder.append(", mData=");
      stringBuilder.append(this.mData);
      stringBuilder.append(", mItemCount=");
      stringBuilder.append(this.mItemCount);
      stringBuilder.append(", mIsMeasuring=");
      stringBuilder.append(this.mIsMeasuring);
      stringBuilder.append(", mPreviousLayoutItemCount=");
      stringBuilder.append(this.mPreviousLayoutItemCount);
      stringBuilder.append(", mDeletedInvisibleItemCountSincePreviousLayout=");
      stringBuilder.append(this.mDeletedInvisibleItemCountSincePreviousLayout);
      stringBuilder.append(", mStructureChanged=");
      stringBuilder.append(this.mStructureChanged);
      stringBuilder.append(", mInPreLayout=");
      stringBuilder.append(this.mInPreLayout);
      stringBuilder.append(", mRunSimpleAnimations=");
      stringBuilder.append(this.mRunSimpleAnimations);
      stringBuilder.append(", mRunPredictiveAnimations=");
      stringBuilder.append(this.mRunPredictiveAnimations);
      stringBuilder.append('}');
      return stringBuilder.toString();
    }
    
    public boolean willRunPredictiveAnimations() {
      return this.mRunPredictiveAnimations;
    }
    
    public boolean willRunSimpleAnimations() {
      return this.mRunSimpleAnimations;
    }
    
    @Retention(RetentionPolicy.SOURCE)
    static @interface LayoutState {}
  }
  
  @Retention(RetentionPolicy.SOURCE)
  static @interface LayoutState {}
  
  public static abstract class ViewCacheExtension {
    public abstract View getViewForPositionAndType(RecyclerView.Recycler param1Recycler, int param1Int1, int param1Int2);
  }
  
  class ViewFlinger implements Runnable {
    private boolean mEatRunOnAnimationRequest = false;
    
    Interpolator mInterpolator = RecyclerView.sQuinticInterpolator;
    
    private int mLastFlingX;
    
    private int mLastFlingY;
    
    private boolean mReSchedulePostAnimationCallback = false;
    
    private OverScroller mScroller = new OverScroller(RecyclerView.this.getContext(), RecyclerView.sQuinticInterpolator);
    
    private int computeScrollDuration(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      boolean bool;
      int i = Math.abs(param1Int1);
      int j = Math.abs(param1Int2);
      if (i > j) {
        bool = true;
      } else {
        bool = false;
      } 
      param1Int3 = (int)Math.sqrt((param1Int3 * param1Int3 + param1Int4 * param1Int4));
      param1Int2 = (int)Math.sqrt((param1Int1 * param1Int1 + param1Int2 * param1Int2));
      RecyclerView recyclerView = RecyclerView.this;
      if (bool) {
        param1Int1 = recyclerView.getWidth();
      } else {
        param1Int1 = recyclerView.getHeight();
      } 
      param1Int4 = param1Int1 / 2;
      float f1 = param1Int2;
      float f2 = param1Int1;
      float f3 = Math.min(1.0F, f1 * 1.0F / f2);
      f1 = param1Int4;
      f3 = distanceInfluenceForSnapDuration(f3);
      if (param1Int3 > 0) {
        param1Int1 = Math.round(Math.abs((f1 + f3 * f1) / param1Int3) * 1000.0F) * 4;
      } else {
        if (bool) {
          param1Int1 = i;
        } else {
          param1Int1 = j;
        } 
        param1Int1 = (int)((param1Int1 / f2 + 1.0F) * 300.0F);
      } 
      return Math.min(param1Int1, 2000);
    }
    
    private void disableRunOnAnimationRequests() {
      this.mReSchedulePostAnimationCallback = false;
      this.mEatRunOnAnimationRequest = true;
    }
    
    private float distanceInfluenceForSnapDuration(float param1Float) {
      return (float)Math.sin(((param1Float - 0.5F) * 0.47123894F));
    }
    
    private void enableRunOnAnimationRequests() {
      this.mEatRunOnAnimationRequest = false;
      if (this.mReSchedulePostAnimationCallback)
        postOnAnimation(); 
    }
    
    public void fling(int param1Int1, int param1Int2) {
      RecyclerView.this.setScrollState(2);
      this.mLastFlingY = 0;
      this.mLastFlingX = 0;
      this.mScroller.fling(0, 0, param1Int1, param1Int2, -2147483648, 2147483647, -2147483648, 2147483647);
      postOnAnimation();
    }
    
    void postOnAnimation() {
      if (this.mEatRunOnAnimationRequest) {
        this.mReSchedulePostAnimationCallback = true;
      } else {
        RecyclerView.this.removeCallbacks(this);
        ViewCompat.postOnAnimation((View)RecyclerView.this, this);
      } 
    }
    
    public void run() {
      // Byte code:
      //   0: aload_0
      //   1: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   4: getfield mLayout : Landroid/support/v7/widget/RecyclerView$LayoutManager;
      //   7: ifnonnull -> 15
      //   10: aload_0
      //   11: invokevirtual stop : ()V
      //   14: return
      //   15: aload_0
      //   16: invokespecial disableRunOnAnimationRequests : ()V
      //   19: aload_0
      //   20: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   23: invokevirtual consumePendingUpdateOperations : ()V
      //   26: aload_0
      //   27: getfield mScroller : Landroid/widget/OverScroller;
      //   30: astore_1
      //   31: aload_0
      //   32: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   35: getfield mLayout : Landroid/support/v7/widget/RecyclerView$LayoutManager;
      //   38: getfield mSmoothScroller : Landroid/support/v7/widget/RecyclerView$SmoothScroller;
      //   41: astore_2
      //   42: aload_1
      //   43: invokevirtual computeScrollOffset : ()Z
      //   46: ifeq -> 946
      //   49: aload_0
      //   50: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   53: invokestatic access$500 : (Landroid/support/v7/widget/RecyclerView;)[I
      //   56: astore_3
      //   57: aload_1
      //   58: invokevirtual getCurrX : ()I
      //   61: istore #4
      //   63: aload_1
      //   64: invokevirtual getCurrY : ()I
      //   67: istore #5
      //   69: iload #4
      //   71: aload_0
      //   72: getfield mLastFlingX : I
      //   75: isub
      //   76: istore #6
      //   78: iload #5
      //   80: aload_0
      //   81: getfield mLastFlingY : I
      //   84: isub
      //   85: istore #7
      //   87: aload_0
      //   88: iload #4
      //   90: putfield mLastFlingX : I
      //   93: aload_0
      //   94: iload #5
      //   96: putfield mLastFlingY : I
      //   99: iload #6
      //   101: istore #8
      //   103: iload #7
      //   105: istore #9
      //   107: aload_0
      //   108: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   111: iload #6
      //   113: iload #7
      //   115: aload_3
      //   116: aconst_null
      //   117: iconst_1
      //   118: invokevirtual dispatchNestedPreScroll : (II[I[II)Z
      //   121: ifeq -> 140
      //   124: iload #6
      //   126: aload_3
      //   127: iconst_0
      //   128: iaload
      //   129: isub
      //   130: istore #8
      //   132: iload #7
      //   134: aload_3
      //   135: iconst_1
      //   136: iaload
      //   137: isub
      //   138: istore #9
      //   140: aload_0
      //   141: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   144: getfield mAdapter : Landroid/support/v7/widget/RecyclerView$Adapter;
      //   147: ifnull -> 494
      //   150: aload_0
      //   151: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   154: invokevirtual startInterceptRequestLayout : ()V
      //   157: aload_0
      //   158: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   161: invokevirtual onEnterLayoutOrScroll : ()V
      //   164: ldc 'RV Scroll'
      //   166: invokestatic beginSection : (Ljava/lang/String;)V
      //   169: aload_0
      //   170: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   173: astore_3
      //   174: aload_3
      //   175: aload_3
      //   176: getfield mState : Landroid/support/v7/widget/RecyclerView$State;
      //   179: invokevirtual fillRemainingScrollValues : (Landroid/support/v7/widget/RecyclerView$State;)V
      //   182: iload #8
      //   184: ifeq -> 225
      //   187: aload_0
      //   188: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   191: getfield mLayout : Landroid/support/v7/widget/RecyclerView$LayoutManager;
      //   194: iload #8
      //   196: aload_0
      //   197: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   200: getfield mRecycler : Landroid/support/v7/widget/RecyclerView$Recycler;
      //   203: aload_0
      //   204: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   207: getfield mState : Landroid/support/v7/widget/RecyclerView$State;
      //   210: invokevirtual scrollHorizontallyBy : (ILandroid/support/v7/widget/RecyclerView$Recycler;Landroid/support/v7/widget/RecyclerView$State;)I
      //   213: istore #6
      //   215: iload #8
      //   217: iload #6
      //   219: isub
      //   220: istore #7
      //   222: goto -> 231
      //   225: iconst_0
      //   226: istore #6
      //   228: iconst_0
      //   229: istore #7
      //   231: iload #9
      //   233: ifeq -> 274
      //   236: aload_0
      //   237: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   240: getfield mLayout : Landroid/support/v7/widget/RecyclerView$LayoutManager;
      //   243: iload #9
      //   245: aload_0
      //   246: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   249: getfield mRecycler : Landroid/support/v7/widget/RecyclerView$Recycler;
      //   252: aload_0
      //   253: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   256: getfield mState : Landroid/support/v7/widget/RecyclerView$State;
      //   259: invokevirtual scrollVerticallyBy : (ILandroid/support/v7/widget/RecyclerView$Recycler;Landroid/support/v7/widget/RecyclerView$State;)I
      //   262: istore #10
      //   264: iload #9
      //   266: iload #10
      //   268: isub
      //   269: istore #11
      //   271: goto -> 280
      //   274: iconst_0
      //   275: istore #10
      //   277: iconst_0
      //   278: istore #11
      //   280: invokestatic endSection : ()V
      //   283: aload_0
      //   284: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   287: invokevirtual repositionShadowingViews : ()V
      //   290: aload_0
      //   291: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   294: invokevirtual onExitLayoutOrScroll : ()V
      //   297: aload_0
      //   298: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   301: iconst_0
      //   302: invokevirtual stopInterceptRequestLayout : (Z)V
      //   305: iload #6
      //   307: istore #12
      //   309: iload #7
      //   311: istore #13
      //   313: iload #10
      //   315: istore #14
      //   317: iload #11
      //   319: istore #15
      //   321: aload_2
      //   322: ifnull -> 506
      //   325: iload #6
      //   327: istore #12
      //   329: iload #7
      //   331: istore #13
      //   333: iload #10
      //   335: istore #14
      //   337: iload #11
      //   339: istore #15
      //   341: aload_2
      //   342: invokevirtual isPendingInitialRun : ()Z
      //   345: ifne -> 506
      //   348: iload #6
      //   350: istore #12
      //   352: iload #7
      //   354: istore #13
      //   356: iload #10
      //   358: istore #14
      //   360: iload #11
      //   362: istore #15
      //   364: aload_2
      //   365: invokevirtual isRunning : ()Z
      //   368: ifeq -> 506
      //   371: aload_0
      //   372: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   375: getfield mState : Landroid/support/v7/widget/RecyclerView$State;
      //   378: invokevirtual getItemCount : ()I
      //   381: istore #15
      //   383: iload #15
      //   385: ifne -> 411
      //   388: aload_2
      //   389: invokevirtual stop : ()V
      //   392: iload #6
      //   394: istore #12
      //   396: iload #7
      //   398: istore #13
      //   400: iload #10
      //   402: istore #14
      //   404: iload #11
      //   406: istore #15
      //   408: goto -> 506
      //   411: aload_2
      //   412: invokevirtual getTargetPosition : ()I
      //   415: iload #15
      //   417: if_icmplt -> 461
      //   420: aload_2
      //   421: iload #15
      //   423: iconst_1
      //   424: isub
      //   425: invokevirtual setTargetPosition : (I)V
      //   428: aload_2
      //   429: iload #8
      //   431: iload #7
      //   433: isub
      //   434: iload #9
      //   436: iload #11
      //   438: isub
      //   439: invokestatic access$600 : (Landroid/support/v7/widget/RecyclerView$SmoothScroller;II)V
      //   442: iload #6
      //   444: istore #12
      //   446: iload #7
      //   448: istore #13
      //   450: iload #10
      //   452: istore #14
      //   454: iload #11
      //   456: istore #15
      //   458: goto -> 506
      //   461: aload_2
      //   462: iload #8
      //   464: iload #7
      //   466: isub
      //   467: iload #9
      //   469: iload #11
      //   471: isub
      //   472: invokestatic access$600 : (Landroid/support/v7/widget/RecyclerView$SmoothScroller;II)V
      //   475: iload #6
      //   477: istore #12
      //   479: iload #7
      //   481: istore #13
      //   483: iload #10
      //   485: istore #14
      //   487: iload #11
      //   489: istore #15
      //   491: goto -> 506
      //   494: iconst_0
      //   495: istore #12
      //   497: iconst_0
      //   498: istore #13
      //   500: iconst_0
      //   501: istore #14
      //   503: iconst_0
      //   504: istore #15
      //   506: aload_0
      //   507: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   510: getfield mItemDecorations : Ljava/util/ArrayList;
      //   513: invokevirtual isEmpty : ()Z
      //   516: ifne -> 526
      //   519: aload_0
      //   520: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   523: invokevirtual invalidate : ()V
      //   526: aload_0
      //   527: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   530: invokevirtual getOverScrollMode : ()I
      //   533: iconst_2
      //   534: if_icmpeq -> 548
      //   537: aload_0
      //   538: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   541: iload #8
      //   543: iload #9
      //   545: invokevirtual considerReleasingGlowsOnScroll : (II)V
      //   548: aload_0
      //   549: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   552: iload #12
      //   554: iload #14
      //   556: iload #13
      //   558: iload #15
      //   560: aconst_null
      //   561: iconst_1
      //   562: invokevirtual dispatchNestedScroll : (IIII[II)Z
      //   565: ifne -> 715
      //   568: iload #13
      //   570: ifne -> 578
      //   573: iload #15
      //   575: ifeq -> 715
      //   578: aload_1
      //   579: invokevirtual getCurrVelocity : ()F
      //   582: f2i
      //   583: istore #6
      //   585: iload #13
      //   587: iload #4
      //   589: if_icmpeq -> 617
      //   592: iload #13
      //   594: ifge -> 605
      //   597: iload #6
      //   599: ineg
      //   600: istore #7
      //   602: goto -> 620
      //   605: iload #13
      //   607: ifle -> 617
      //   610: iload #6
      //   612: istore #7
      //   614: goto -> 620
      //   617: iconst_0
      //   618: istore #7
      //   620: iload #15
      //   622: iload #5
      //   624: if_icmpeq -> 648
      //   627: iload #15
      //   629: ifge -> 640
      //   632: iload #6
      //   634: ineg
      //   635: istore #6
      //   637: goto -> 651
      //   640: iload #15
      //   642: ifle -> 648
      //   645: goto -> 651
      //   648: iconst_0
      //   649: istore #6
      //   651: aload_0
      //   652: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   655: invokevirtual getOverScrollMode : ()I
      //   658: iconst_2
      //   659: if_icmpeq -> 673
      //   662: aload_0
      //   663: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   666: iload #7
      //   668: iload #6
      //   670: invokevirtual absorbGlows : (II)V
      //   673: iload #7
      //   675: ifne -> 692
      //   678: iload #13
      //   680: iload #4
      //   682: if_icmpeq -> 692
      //   685: aload_1
      //   686: invokevirtual getFinalX : ()I
      //   689: ifne -> 715
      //   692: iload #6
      //   694: ifne -> 711
      //   697: iload #15
      //   699: iload #5
      //   701: if_icmpeq -> 711
      //   704: aload_1
      //   705: invokevirtual getFinalY : ()I
      //   708: ifne -> 715
      //   711: aload_1
      //   712: invokevirtual abortAnimation : ()V
      //   715: iload #12
      //   717: ifne -> 725
      //   720: iload #14
      //   722: ifeq -> 736
      //   725: aload_0
      //   726: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   729: iload #12
      //   731: iload #14
      //   733: invokevirtual dispatchOnScrolled : (II)V
      //   736: aload_0
      //   737: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   740: invokestatic access$700 : (Landroid/support/v7/widget/RecyclerView;)Z
      //   743: ifne -> 753
      //   746: aload_0
      //   747: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   750: invokevirtual invalidate : ()V
      //   753: iload #9
      //   755: ifeq -> 784
      //   758: aload_0
      //   759: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   762: getfield mLayout : Landroid/support/v7/widget/RecyclerView$LayoutManager;
      //   765: invokevirtual canScrollVertically : ()Z
      //   768: ifeq -> 784
      //   771: iload #14
      //   773: iload #9
      //   775: if_icmpne -> 784
      //   778: iconst_1
      //   779: istore #7
      //   781: goto -> 787
      //   784: iconst_0
      //   785: istore #7
      //   787: iload #8
      //   789: ifeq -> 818
      //   792: aload_0
      //   793: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   796: getfield mLayout : Landroid/support/v7/widget/RecyclerView$LayoutManager;
      //   799: invokevirtual canScrollHorizontally : ()Z
      //   802: ifeq -> 818
      //   805: iload #12
      //   807: iload #8
      //   809: if_icmpne -> 818
      //   812: iconst_1
      //   813: istore #6
      //   815: goto -> 821
      //   818: iconst_0
      //   819: istore #6
      //   821: iload #8
      //   823: ifne -> 831
      //   826: iload #9
      //   828: ifeq -> 850
      //   831: iload #6
      //   833: ifne -> 850
      //   836: iload #7
      //   838: ifeq -> 844
      //   841: goto -> 850
      //   844: iconst_0
      //   845: istore #7
      //   847: goto -> 853
      //   850: iconst_1
      //   851: istore #7
      //   853: aload_1
      //   854: invokevirtual isFinished : ()Z
      //   857: ifne -> 914
      //   860: iload #7
      //   862: ifne -> 879
      //   865: aload_0
      //   866: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   869: iconst_1
      //   870: invokevirtual hasNestedScrollingParent : (I)Z
      //   873: ifne -> 879
      //   876: goto -> 914
      //   879: aload_0
      //   880: invokevirtual postOnAnimation : ()V
      //   883: aload_0
      //   884: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   887: getfield mGapWorker : Landroid/support/v7/widget/GapWorker;
      //   890: ifnull -> 946
      //   893: aload_0
      //   894: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   897: getfield mGapWorker : Landroid/support/v7/widget/GapWorker;
      //   900: aload_0
      //   901: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   904: iload #8
      //   906: iload #9
      //   908: invokevirtual postFromTraversal : (Landroid/support/v7/widget/RecyclerView;II)V
      //   911: goto -> 946
      //   914: aload_0
      //   915: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   918: iconst_0
      //   919: invokevirtual setScrollState : (I)V
      //   922: invokestatic access$800 : ()Z
      //   925: ifeq -> 938
      //   928: aload_0
      //   929: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   932: getfield mPrefetchRegistry : Landroid/support/v7/widget/GapWorker$LayoutPrefetchRegistryImpl;
      //   935: invokevirtual clearPrefetchPositions : ()V
      //   938: aload_0
      //   939: getfield this$0 : Landroid/support/v7/widget/RecyclerView;
      //   942: iconst_1
      //   943: invokevirtual stopNestedScroll : (I)V
      //   946: aload_2
      //   947: ifnull -> 974
      //   950: aload_2
      //   951: invokevirtual isPendingInitialRun : ()Z
      //   954: ifeq -> 963
      //   957: aload_2
      //   958: iconst_0
      //   959: iconst_0
      //   960: invokestatic access$600 : (Landroid/support/v7/widget/RecyclerView$SmoothScroller;II)V
      //   963: aload_0
      //   964: getfield mReSchedulePostAnimationCallback : Z
      //   967: ifne -> 974
      //   970: aload_2
      //   971: invokevirtual stop : ()V
      //   974: aload_0
      //   975: invokespecial enableRunOnAnimationRequests : ()V
      //   978: return
    }
    
    public void smoothScrollBy(int param1Int1, int param1Int2) {
      smoothScrollBy(param1Int1, param1Int2, 0, 0);
    }
    
    public void smoothScrollBy(int param1Int1, int param1Int2, int param1Int3) {
      smoothScrollBy(param1Int1, param1Int2, param1Int3, RecyclerView.sQuinticInterpolator);
    }
    
    public void smoothScrollBy(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      smoothScrollBy(param1Int1, param1Int2, computeScrollDuration(param1Int1, param1Int2, param1Int3, param1Int4));
    }
    
    public void smoothScrollBy(int param1Int1, int param1Int2, int param1Int3, Interpolator param1Interpolator) {
      if (this.mInterpolator != param1Interpolator) {
        this.mInterpolator = param1Interpolator;
        this.mScroller = new OverScroller(RecyclerView.this.getContext(), param1Interpolator);
      } 
      RecyclerView.this.setScrollState(2);
      this.mLastFlingY = 0;
      this.mLastFlingX = 0;
      this.mScroller.startScroll(0, 0, param1Int1, param1Int2, param1Int3);
      if (Build.VERSION.SDK_INT < 23)
        this.mScroller.computeScrollOffset(); 
      postOnAnimation();
    }
    
    public void smoothScrollBy(int param1Int1, int param1Int2, Interpolator param1Interpolator) {
      int i = computeScrollDuration(param1Int1, param1Int2, 0, 0);
      Interpolator interpolator = param1Interpolator;
      if (param1Interpolator == null)
        interpolator = RecyclerView.sQuinticInterpolator; 
      smoothScrollBy(param1Int1, param1Int2, i, interpolator);
    }
    
    public void stop() {
      RecyclerView.this.removeCallbacks(this);
      this.mScroller.abortAnimation();
    }
  }
  
  public static abstract class ViewHolder {
    static final int FLAG_ADAPTER_FULLUPDATE = 1024;
    
    static final int FLAG_ADAPTER_POSITION_UNKNOWN = 512;
    
    static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
    
    static final int FLAG_BOUNCED_FROM_HIDDEN_LIST = 8192;
    
    static final int FLAG_BOUND = 1;
    
    static final int FLAG_IGNORE = 128;
    
    static final int FLAG_INVALID = 4;
    
    static final int FLAG_MOVED = 2048;
    
    static final int FLAG_NOT_RECYCLABLE = 16;
    
    static final int FLAG_REMOVED = 8;
    
    static final int FLAG_RETURNED_FROM_SCRAP = 32;
    
    static final int FLAG_SET_A11Y_ITEM_DELEGATE = 16384;
    
    static final int FLAG_TMP_DETACHED = 256;
    
    static final int FLAG_UPDATE = 2;
    
    private static final List<Object> FULLUPDATE_PAYLOADS = Collections.EMPTY_LIST;
    
    static final int PENDING_ACCESSIBILITY_STATE_NOT_SET = -1;
    
    public final View itemView;
    
    private int mFlags;
    
    private boolean mInChangeScrap = false;
    
    private int mIsRecyclableCount = 0;
    
    long mItemId = -1L;
    
    int mItemViewType = -1;
    
    WeakReference<RecyclerView> mNestedRecyclerView;
    
    int mOldPosition = -1;
    
    RecyclerView mOwnerRecyclerView;
    
    List<Object> mPayloads = null;
    
    int mPendingAccessibilityState = -1;
    
    int mPosition = -1;
    
    int mPreLayoutPosition = -1;
    
    private RecyclerView.Recycler mScrapContainer = null;
    
    ViewHolder mShadowedHolder = null;
    
    ViewHolder mShadowingHolder = null;
    
    List<Object> mUnmodifiedPayloads = null;
    
    private int mWasImportantForAccessibilityBeforeHidden = 0;
    
    public ViewHolder(View param1View) {
      if (param1View != null) {
        this.itemView = param1View;
        return;
      } 
      throw new IllegalArgumentException("itemView may not be null");
    }
    
    private void createPayloadsIfNeeded() {
      if (this.mPayloads == null) {
        ArrayList<Object> arrayList = new ArrayList();
        this.mPayloads = arrayList;
        this.mUnmodifiedPayloads = Collections.unmodifiableList(arrayList);
      } 
    }
    
    private boolean doesTransientStatePreventRecycling() {
      boolean bool;
      if ((this.mFlags & 0x10) == 0 && ViewCompat.hasTransientState(this.itemView)) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    private void onEnteredHiddenState(RecyclerView param1RecyclerView) {
      int i = this.mPendingAccessibilityState;
      if (i != -1) {
        this.mWasImportantForAccessibilityBeforeHidden = i;
      } else {
        this.mWasImportantForAccessibilityBeforeHidden = ViewCompat.getImportantForAccessibility(this.itemView);
      } 
      param1RecyclerView.setChildImportantForAccessibilityInternal(this, 4);
    }
    
    private void onLeftHiddenState(RecyclerView param1RecyclerView) {
      param1RecyclerView.setChildImportantForAccessibilityInternal(this, this.mWasImportantForAccessibilityBeforeHidden);
      this.mWasImportantForAccessibilityBeforeHidden = 0;
    }
    
    private boolean shouldBeKeptAsChild() {
      boolean bool;
      if ((this.mFlags & 0x10) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    void addChangePayload(Object param1Object) {
      if (param1Object == null) {
        addFlags(1024);
      } else if ((0x400 & this.mFlags) == 0) {
        createPayloadsIfNeeded();
        this.mPayloads.add(param1Object);
      } 
    }
    
    void addFlags(int param1Int) {
      this.mFlags = param1Int | this.mFlags;
    }
    
    void clearOldPosition() {
      this.mOldPosition = -1;
      this.mPreLayoutPosition = -1;
    }
    
    void clearPayload() {
      List<Object> list = this.mPayloads;
      if (list != null)
        list.clear(); 
      this.mFlags &= 0xFFFFFBFF;
    }
    
    void clearReturnedFromScrapFlag() {
      this.mFlags &= 0xFFFFFFDF;
    }
    
    void clearTmpDetachFlag() {
      this.mFlags &= 0xFFFFFEFF;
    }
    
    void flagRemovedAndOffsetPosition(int param1Int1, int param1Int2, boolean param1Boolean) {
      addFlags(8);
      offsetPosition(param1Int2, param1Boolean);
      this.mPosition = param1Int1;
    }
    
    public final int getAdapterPosition() {
      RecyclerView recyclerView = this.mOwnerRecyclerView;
      return (recyclerView == null) ? -1 : recyclerView.getAdapterPositionFor(this);
    }
    
    public final long getItemId() {
      return this.mItemId;
    }
    
    public final int getItemViewType() {
      return this.mItemViewType;
    }
    
    public final int getLayoutPosition() {
      int i = this.mPreLayoutPosition;
      int j = i;
      if (i == -1)
        j = this.mPosition; 
      return j;
    }
    
    public final int getOldPosition() {
      return this.mOldPosition;
    }
    
    @Deprecated
    public final int getPosition() {
      int i = this.mPreLayoutPosition;
      int j = i;
      if (i == -1)
        j = this.mPosition; 
      return j;
    }
    
    List<Object> getUnmodifiedPayloads() {
      if ((this.mFlags & 0x400) == 0) {
        List<Object> list = this.mPayloads;
        return (list == null || list.size() == 0) ? FULLUPDATE_PAYLOADS : this.mUnmodifiedPayloads;
      } 
      return FULLUPDATE_PAYLOADS;
    }
    
    boolean hasAnyOfTheFlags(int param1Int) {
      boolean bool;
      if ((param1Int & this.mFlags) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    boolean isAdapterPositionUnknown() {
      return ((this.mFlags & 0x200) != 0 || isInvalid());
    }
    
    boolean isBound() {
      int i = this.mFlags;
      boolean bool = true;
      if ((i & 0x1) == 0)
        bool = false; 
      return bool;
    }
    
    boolean isInvalid() {
      boolean bool;
      if ((this.mFlags & 0x4) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public final boolean isRecyclable() {
      boolean bool;
      if ((this.mFlags & 0x10) == 0 && !ViewCompat.hasTransientState(this.itemView)) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    boolean isRemoved() {
      boolean bool;
      if ((this.mFlags & 0x8) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    boolean isScrap() {
      boolean bool;
      if (this.mScrapContainer != null) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    boolean isTmpDetached() {
      boolean bool;
      if ((this.mFlags & 0x100) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    boolean isUpdated() {
      boolean bool;
      if ((this.mFlags & 0x2) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    boolean needsUpdate() {
      boolean bool;
      if ((this.mFlags & 0x2) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    void offsetPosition(int param1Int, boolean param1Boolean) {
      if (this.mOldPosition == -1)
        this.mOldPosition = this.mPosition; 
      if (this.mPreLayoutPosition == -1)
        this.mPreLayoutPosition = this.mPosition; 
      if (param1Boolean)
        this.mPreLayoutPosition += param1Int; 
      this.mPosition += param1Int;
      if (this.itemView.getLayoutParams() != null)
        ((RecyclerView.LayoutParams)this.itemView.getLayoutParams()).mInsetsDirty = true; 
    }
    
    void resetInternal() {
      this.mFlags = 0;
      this.mPosition = -1;
      this.mOldPosition = -1;
      this.mItemId = -1L;
      this.mPreLayoutPosition = -1;
      this.mIsRecyclableCount = 0;
      this.mShadowedHolder = null;
      this.mShadowingHolder = null;
      clearPayload();
      this.mWasImportantForAccessibilityBeforeHidden = 0;
      this.mPendingAccessibilityState = -1;
      RecyclerView.clearNestedRecyclerViewIfNotNested(this);
    }
    
    void saveOldPosition() {
      if (this.mOldPosition == -1)
        this.mOldPosition = this.mPosition; 
    }
    
    void setFlags(int param1Int1, int param1Int2) {
      this.mFlags = param1Int1 & param1Int2 | this.mFlags & param1Int2;
    }
    
    public final void setIsRecyclable(boolean param1Boolean) {
      int i = this.mIsRecyclableCount;
      if (param1Boolean) {
        i--;
      } else {
        i++;
      } 
      this.mIsRecyclableCount = i;
      if (i < 0) {
        this.mIsRecyclableCount = 0;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for ");
        stringBuilder.append(this);
        Log.e("View", stringBuilder.toString());
      } else if (!param1Boolean && i == 1) {
        this.mFlags |= 0x10;
      } else if (param1Boolean && this.mIsRecyclableCount == 0) {
        this.mFlags &= 0xFFFFFFEF;
      } 
    }
    
    void setScrapContainer(RecyclerView.Recycler param1Recycler, boolean param1Boolean) {
      this.mScrapContainer = param1Recycler;
      this.mInChangeScrap = param1Boolean;
    }
    
    boolean shouldIgnore() {
      boolean bool;
      if ((this.mFlags & 0x80) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    void stopIgnoring() {
      this.mFlags &= 0xFFFFFF7F;
    }
    
    public String toString() {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("ViewHolder{");
      stringBuilder1.append(Integer.toHexString(hashCode()));
      stringBuilder1.append(" position=");
      stringBuilder1.append(this.mPosition);
      stringBuilder1.append(" id=");
      stringBuilder1.append(this.mItemId);
      stringBuilder1.append(", oldPos=");
      stringBuilder1.append(this.mOldPosition);
      stringBuilder1.append(", pLpos:");
      stringBuilder1.append(this.mPreLayoutPosition);
      StringBuilder stringBuilder2 = new StringBuilder(stringBuilder1.toString());
      if (isScrap()) {
        String str;
        stringBuilder2.append(" scrap ");
        if (this.mInChangeScrap) {
          str = "[changeScrap]";
        } else {
          str = "[attachedScrap]";
        } 
        stringBuilder2.append(str);
      } 
      if (isInvalid())
        stringBuilder2.append(" invalid"); 
      if (!isBound())
        stringBuilder2.append(" unbound"); 
      if (needsUpdate())
        stringBuilder2.append(" update"); 
      if (isRemoved())
        stringBuilder2.append(" removed"); 
      if (shouldIgnore())
        stringBuilder2.append(" ignored"); 
      if (isTmpDetached())
        stringBuilder2.append(" tmpDetached"); 
      if (!isRecyclable()) {
        stringBuilder1 = new StringBuilder();
        stringBuilder1.append(" not recyclable(");
        stringBuilder1.append(this.mIsRecyclableCount);
        stringBuilder1.append(")");
        stringBuilder2.append(stringBuilder1.toString());
      } 
      if (isAdapterPositionUnknown())
        stringBuilder2.append(" undefined adapter position"); 
      if (this.itemView.getParent() == null)
        stringBuilder2.append(" no parent"); 
      stringBuilder2.append("}");
      return stringBuilder2.toString();
    }
    
    void unScrap() {
      this.mScrapContainer.unscrapView(this);
    }
    
    boolean wasReturnedFromScrap() {
      boolean bool;
      if ((this.mFlags & 0x20) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\RecyclerView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */