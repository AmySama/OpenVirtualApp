package android.support.v7.widget.helper;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.recyclerview.R;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import java.util.ArrayList;
import java.util.List;

public class ItemTouchHelper extends RecyclerView.ItemDecoration implements RecyclerView.OnChildAttachStateChangeListener {
  static final int ACTION_MODE_DRAG_MASK = 16711680;
  
  private static final int ACTION_MODE_IDLE_MASK = 255;
  
  static final int ACTION_MODE_SWIPE_MASK = 65280;
  
  public static final int ACTION_STATE_DRAG = 2;
  
  public static final int ACTION_STATE_IDLE = 0;
  
  public static final int ACTION_STATE_SWIPE = 1;
  
  static final int ACTIVE_POINTER_ID_NONE = -1;
  
  public static final int ANIMATION_TYPE_DRAG = 8;
  
  public static final int ANIMATION_TYPE_SWIPE_CANCEL = 4;
  
  public static final int ANIMATION_TYPE_SWIPE_SUCCESS = 2;
  
  static final boolean DEBUG = false;
  
  static final int DIRECTION_FLAG_COUNT = 8;
  
  public static final int DOWN = 2;
  
  public static final int END = 32;
  
  public static final int LEFT = 4;
  
  private static final int PIXELS_PER_SECOND = 1000;
  
  public static final int RIGHT = 8;
  
  public static final int START = 16;
  
  static final String TAG = "ItemTouchHelper";
  
  public static final int UP = 1;
  
  int mActionState = 0;
  
  int mActivePointerId = -1;
  
  Callback mCallback;
  
  private RecyclerView.ChildDrawingOrderCallback mChildDrawingOrderCallback = null;
  
  private List<Integer> mDistances;
  
  private long mDragScrollStartTimeInMs;
  
  float mDx;
  
  float mDy;
  
  GestureDetectorCompat mGestureDetector;
  
  float mInitialTouchX;
  
  float mInitialTouchY;
  
  private ItemTouchHelperGestureListener mItemTouchHelperGestureListener;
  
  float mMaxSwipeVelocity;
  
  private final RecyclerView.OnItemTouchListener mOnItemTouchListener = new RecyclerView.OnItemTouchListener() {
      public boolean onInterceptTouchEvent(RecyclerView param1RecyclerView, MotionEvent param1MotionEvent) {
        ItemTouchHelper.this.mGestureDetector.onTouchEvent(param1MotionEvent);
        int i = param1MotionEvent.getActionMasked();
        boolean bool = true;
        if (i == 0) {
          ItemTouchHelper.this.mActivePointerId = param1MotionEvent.getPointerId(0);
          ItemTouchHelper.this.mInitialTouchX = param1MotionEvent.getX();
          ItemTouchHelper.this.mInitialTouchY = param1MotionEvent.getY();
          ItemTouchHelper.this.obtainVelocityTracker();
          if (ItemTouchHelper.this.mSelected == null) {
            ItemTouchHelper.RecoverAnimation recoverAnimation = ItemTouchHelper.this.findAnimation(param1MotionEvent);
            if (recoverAnimation != null) {
              ItemTouchHelper itemTouchHelper2 = ItemTouchHelper.this;
              itemTouchHelper2.mInitialTouchX -= recoverAnimation.mX;
              itemTouchHelper2 = ItemTouchHelper.this;
              itemTouchHelper2.mInitialTouchY -= recoverAnimation.mY;
              ItemTouchHelper.this.endRecoverAnimation(recoverAnimation.mViewHolder, true);
              if (ItemTouchHelper.this.mPendingCleanup.remove(recoverAnimation.mViewHolder.itemView))
                ItemTouchHelper.this.mCallback.clearView(ItemTouchHelper.this.mRecyclerView, recoverAnimation.mViewHolder); 
              ItemTouchHelper.this.select(recoverAnimation.mViewHolder, recoverAnimation.mActionState);
              ItemTouchHelper itemTouchHelper1 = ItemTouchHelper.this;
              itemTouchHelper1.updateDxDy(param1MotionEvent, itemTouchHelper1.mSelectedFlags, 0);
            } 
          } 
        } else if (i == 3 || i == 1) {
          ItemTouchHelper.this.mActivePointerId = -1;
          ItemTouchHelper.this.select(null, 0);
        } else if (ItemTouchHelper.this.mActivePointerId != -1) {
          int j = param1MotionEvent.findPointerIndex(ItemTouchHelper.this.mActivePointerId);
          if (j >= 0)
            ItemTouchHelper.this.checkSelectForSwipe(i, param1MotionEvent, j); 
        } 
        if (ItemTouchHelper.this.mVelocityTracker != null)
          ItemTouchHelper.this.mVelocityTracker.addMovement(param1MotionEvent); 
        if (ItemTouchHelper.this.mSelected == null)
          bool = false; 
        return bool;
      }
      
      public void onRequestDisallowInterceptTouchEvent(boolean param1Boolean) {
        if (!param1Boolean)
          return; 
        ItemTouchHelper.this.select(null, 0);
      }
      
      public void onTouchEvent(RecyclerView param1RecyclerView, MotionEvent param1MotionEvent) {
        ItemTouchHelper.this.mGestureDetector.onTouchEvent(param1MotionEvent);
        if (ItemTouchHelper.this.mVelocityTracker != null)
          ItemTouchHelper.this.mVelocityTracker.addMovement(param1MotionEvent); 
        if (ItemTouchHelper.this.mActivePointerId == -1)
          return; 
        int i = param1MotionEvent.getActionMasked();
        int j = param1MotionEvent.findPointerIndex(ItemTouchHelper.this.mActivePointerId);
        if (j >= 0)
          ItemTouchHelper.this.checkSelectForSwipe(i, param1MotionEvent, j); 
        RecyclerView.ViewHolder viewHolder = ItemTouchHelper.this.mSelected;
        if (viewHolder == null)
          return; 
        boolean bool = false;
        if (i != 1) {
          ItemTouchHelper itemTouchHelper;
          if (i != 2) {
            if (i != 3) {
              if (i == 6) {
                i = param1MotionEvent.getActionIndex();
                if (param1MotionEvent.getPointerId(i) == ItemTouchHelper.this.mActivePointerId) {
                  if (i == 0)
                    bool = true; 
                  ItemTouchHelper.this.mActivePointerId = param1MotionEvent.getPointerId(bool);
                  itemTouchHelper = ItemTouchHelper.this;
                  itemTouchHelper.updateDxDy(param1MotionEvent, itemTouchHelper.mSelectedFlags, i);
                } 
              } 
            } else {
              if (ItemTouchHelper.this.mVelocityTracker != null)
                ItemTouchHelper.this.mVelocityTracker.clear(); 
              ItemTouchHelper.this.select(null, 0);
              ItemTouchHelper.this.mActivePointerId = -1;
            } 
          } else if (j >= 0) {
            ItemTouchHelper itemTouchHelper1 = ItemTouchHelper.this;
            itemTouchHelper1.updateDxDy(param1MotionEvent, itemTouchHelper1.mSelectedFlags, j);
            ItemTouchHelper.this.moveIfNecessary((RecyclerView.ViewHolder)itemTouchHelper);
            ItemTouchHelper.this.mRecyclerView.removeCallbacks(ItemTouchHelper.this.mScrollRunnable);
            ItemTouchHelper.this.mScrollRunnable.run();
            ItemTouchHelper.this.mRecyclerView.invalidate();
          } 
          return;
        } 
        ItemTouchHelper.this.select(null, 0);
        ItemTouchHelper.this.mActivePointerId = -1;
      }
    };
  
  View mOverdrawChild = null;
  
  int mOverdrawChildPosition = -1;
  
  final List<View> mPendingCleanup = new ArrayList<View>();
  
  List<RecoverAnimation> mRecoverAnimations = new ArrayList<RecoverAnimation>();
  
  RecyclerView mRecyclerView;
  
  final Runnable mScrollRunnable = new Runnable() {
      public void run() {
        if (ItemTouchHelper.this.mSelected != null && ItemTouchHelper.this.scrollIfNecessary()) {
          if (ItemTouchHelper.this.mSelected != null) {
            ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
            itemTouchHelper.moveIfNecessary(itemTouchHelper.mSelected);
          } 
          ItemTouchHelper.this.mRecyclerView.removeCallbacks(ItemTouchHelper.this.mScrollRunnable);
          ViewCompat.postOnAnimation((View)ItemTouchHelper.this.mRecyclerView, this);
        } 
      }
    };
  
  RecyclerView.ViewHolder mSelected = null;
  
  int mSelectedFlags;
  
  float mSelectedStartX;
  
  float mSelectedStartY;
  
  private int mSlop;
  
  private List<RecyclerView.ViewHolder> mSwapTargets;
  
  float mSwipeEscapeVelocity;
  
  private final float[] mTmpPosition = new float[2];
  
  private Rect mTmpRect;
  
  VelocityTracker mVelocityTracker;
  
  public ItemTouchHelper(Callback paramCallback) {
    this.mCallback = paramCallback;
  }
  
  private void addChildDrawingOrderCallback() {
    if (Build.VERSION.SDK_INT >= 21)
      return; 
    if (this.mChildDrawingOrderCallback == null)
      this.mChildDrawingOrderCallback = new RecyclerView.ChildDrawingOrderCallback() {
          public int onGetChildDrawingOrder(int param1Int1, int param1Int2) {
            if (ItemTouchHelper.this.mOverdrawChild == null)
              return param1Int2; 
            int i = ItemTouchHelper.this.mOverdrawChildPosition;
            int j = i;
            if (i == -1) {
              j = ItemTouchHelper.this.mRecyclerView.indexOfChild(ItemTouchHelper.this.mOverdrawChild);
              ItemTouchHelper.this.mOverdrawChildPosition = j;
            } 
            if (param1Int2 == param1Int1 - 1)
              return j; 
            if (param1Int2 >= j)
              param1Int2++; 
            return param1Int2;
          }
        }; 
    this.mRecyclerView.setChildDrawingOrderCallback(this.mChildDrawingOrderCallback);
  }
  
  private int checkHorizontalSwipe(RecyclerView.ViewHolder paramViewHolder, int paramInt) {
    if ((paramInt & 0xC) != 0) {
      byte b2;
      float f1 = this.mDx;
      byte b1 = 8;
      if (f1 > 0.0F) {
        b2 = 8;
      } else {
        b2 = 4;
      } 
      VelocityTracker velocityTracker = this.mVelocityTracker;
      if (velocityTracker != null && this.mActivePointerId > -1) {
        velocityTracker.computeCurrentVelocity(1000, this.mCallback.getSwipeVelocityThreshold(this.mMaxSwipeVelocity));
        float f = this.mVelocityTracker.getXVelocity(this.mActivePointerId);
        f1 = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
        if (f <= 0.0F)
          b1 = 4; 
        f = Math.abs(f);
        if ((b1 & paramInt) != 0 && b2 == b1 && f >= this.mCallback.getSwipeEscapeVelocity(this.mSwipeEscapeVelocity) && f > Math.abs(f1))
          return b1; 
      } 
      f1 = this.mRecyclerView.getWidth();
      float f2 = this.mCallback.getSwipeThreshold(paramViewHolder);
      if ((paramInt & b2) != 0 && Math.abs(this.mDx) > f1 * f2)
        return b2; 
    } 
    return 0;
  }
  
  private int checkVerticalSwipe(RecyclerView.ViewHolder paramViewHolder, int paramInt) {
    if ((paramInt & 0x3) != 0) {
      byte b2;
      float f1 = this.mDy;
      byte b1 = 2;
      if (f1 > 0.0F) {
        b2 = 2;
      } else {
        b2 = 1;
      } 
      VelocityTracker velocityTracker = this.mVelocityTracker;
      if (velocityTracker != null && this.mActivePointerId > -1) {
        velocityTracker.computeCurrentVelocity(1000, this.mCallback.getSwipeVelocityThreshold(this.mMaxSwipeVelocity));
        f1 = this.mVelocityTracker.getXVelocity(this.mActivePointerId);
        float f = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
        if (f <= 0.0F)
          b1 = 1; 
        f = Math.abs(f);
        if ((b1 & paramInt) != 0 && b1 == b2 && f >= this.mCallback.getSwipeEscapeVelocity(this.mSwipeEscapeVelocity) && f > Math.abs(f1))
          return b1; 
      } 
      f1 = this.mRecyclerView.getHeight();
      float f2 = this.mCallback.getSwipeThreshold(paramViewHolder);
      if ((paramInt & b2) != 0 && Math.abs(this.mDy) > f1 * f2)
        return b2; 
    } 
    return 0;
  }
  
  private void destroyCallbacks() {
    this.mRecyclerView.removeItemDecoration(this);
    this.mRecyclerView.removeOnItemTouchListener(this.mOnItemTouchListener);
    this.mRecyclerView.removeOnChildAttachStateChangeListener(this);
    for (int i = this.mRecoverAnimations.size() - 1; i >= 0; i--) {
      RecoverAnimation recoverAnimation = this.mRecoverAnimations.get(0);
      this.mCallback.clearView(this.mRecyclerView, recoverAnimation.mViewHolder);
    } 
    this.mRecoverAnimations.clear();
    this.mOverdrawChild = null;
    this.mOverdrawChildPosition = -1;
    releaseVelocityTracker();
    stopGestureDetection();
  }
  
  private List<RecyclerView.ViewHolder> findSwapTargets(RecyclerView.ViewHolder paramViewHolder) {
    RecyclerView.ViewHolder viewHolder = paramViewHolder;
    List<RecyclerView.ViewHolder> list = this.mSwapTargets;
    if (list == null) {
      this.mSwapTargets = new ArrayList<RecyclerView.ViewHolder>();
      this.mDistances = new ArrayList<Integer>();
    } else {
      list.clear();
      this.mDistances.clear();
    } 
    int i = this.mCallback.getBoundingBoxMargin();
    int j = Math.round(this.mSelectedStartX + this.mDx) - i;
    int k = Math.round(this.mSelectedStartY + this.mDy) - i;
    int m = viewHolder.itemView.getWidth();
    i *= 2;
    int n = m + j + i;
    int i1 = viewHolder.itemView.getHeight() + k + i;
    int i2 = (j + n) / 2;
    int i3 = (k + i1) / 2;
    RecyclerView.LayoutManager layoutManager = this.mRecyclerView.getLayoutManager();
    int i4 = layoutManager.getChildCount();
    for (m = 0; m < i4; m++) {
      View view = layoutManager.getChildAt(m);
      if (view != paramViewHolder.itemView && view.getBottom() >= k && view.getTop() <= i1 && view.getRight() >= j && view.getLeft() <= n) {
        RecyclerView.ViewHolder viewHolder1 = this.mRecyclerView.getChildViewHolder(view);
        if (this.mCallback.canDropOver(this.mRecyclerView, this.mSelected, viewHolder1)) {
          i = Math.abs(i2 - (view.getLeft() + view.getRight()) / 2);
          int i5 = Math.abs(i3 - (view.getTop() + view.getBottom()) / 2);
          int i6 = i * i + i5 * i5;
          int i7 = this.mSwapTargets.size();
          i5 = 0;
          i = 0;
          while (i5 < i7 && i6 > ((Integer)this.mDistances.get(i5)).intValue()) {
            i++;
            i5++;
          } 
          this.mSwapTargets.add(i, viewHolder1);
          this.mDistances.add(i, Integer.valueOf(i6));
        } 
      } 
    } 
    return this.mSwapTargets;
  }
  
  private RecyclerView.ViewHolder findSwipedView(MotionEvent paramMotionEvent) {
    RecyclerView.LayoutManager layoutManager = this.mRecyclerView.getLayoutManager();
    int i = this.mActivePointerId;
    if (i == -1)
      return null; 
    i = paramMotionEvent.findPointerIndex(i);
    float f1 = paramMotionEvent.getX(i);
    float f2 = this.mInitialTouchX;
    float f3 = paramMotionEvent.getY(i);
    float f4 = this.mInitialTouchY;
    f2 = Math.abs(f1 - f2);
    f4 = Math.abs(f3 - f4);
    i = this.mSlop;
    if (f2 < i && f4 < i)
      return null; 
    if (f2 > f4 && layoutManager.canScrollHorizontally())
      return null; 
    if (f4 > f2 && layoutManager.canScrollVertically())
      return null; 
    View view = findChildView(paramMotionEvent);
    return (view == null) ? null : this.mRecyclerView.getChildViewHolder(view);
  }
  
  private void getSelectedDxDy(float[] paramArrayOffloat) {
    if ((this.mSelectedFlags & 0xC) != 0) {
      paramArrayOffloat[0] = this.mSelectedStartX + this.mDx - this.mSelected.itemView.getLeft();
    } else {
      paramArrayOffloat[0] = this.mSelected.itemView.getTranslationX();
    } 
    if ((this.mSelectedFlags & 0x3) != 0) {
      paramArrayOffloat[1] = this.mSelectedStartY + this.mDy - this.mSelected.itemView.getTop();
    } else {
      paramArrayOffloat[1] = this.mSelected.itemView.getTranslationY();
    } 
  }
  
  private static boolean hitTest(View paramView, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    boolean bool;
    if (paramFloat1 >= paramFloat3 && paramFloat1 <= paramFloat3 + paramView.getWidth() && paramFloat2 >= paramFloat4 && paramFloat2 <= paramFloat4 + paramView.getHeight()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private void releaseVelocityTracker() {
    VelocityTracker velocityTracker = this.mVelocityTracker;
    if (velocityTracker != null) {
      velocityTracker.recycle();
      this.mVelocityTracker = null;
    } 
  }
  
  private void setupCallbacks() {
    this.mSlop = ViewConfiguration.get(this.mRecyclerView.getContext()).getScaledTouchSlop();
    this.mRecyclerView.addItemDecoration(this);
    this.mRecyclerView.addOnItemTouchListener(this.mOnItemTouchListener);
    this.mRecyclerView.addOnChildAttachStateChangeListener(this);
    startGestureDetection();
  }
  
  private void startGestureDetection() {
    this.mItemTouchHelperGestureListener = new ItemTouchHelperGestureListener();
    this.mGestureDetector = new GestureDetectorCompat(this.mRecyclerView.getContext(), (GestureDetector.OnGestureListener)this.mItemTouchHelperGestureListener);
  }
  
  private void stopGestureDetection() {
    ItemTouchHelperGestureListener itemTouchHelperGestureListener = this.mItemTouchHelperGestureListener;
    if (itemTouchHelperGestureListener != null) {
      itemTouchHelperGestureListener.doNotReactToLongPress();
      this.mItemTouchHelperGestureListener = null;
    } 
    if (this.mGestureDetector != null)
      this.mGestureDetector = null; 
  }
  
  private int swipeIfNecessary(RecyclerView.ViewHolder paramViewHolder) {
    if (this.mActionState == 2)
      return 0; 
    int i = this.mCallback.getMovementFlags(this.mRecyclerView, paramViewHolder);
    int j = (this.mCallback.convertToAbsoluteDirection(i, ViewCompat.getLayoutDirection((View)this.mRecyclerView)) & 0xFF00) >> 8;
    if (j == 0)
      return 0; 
    int k = (i & 0xFF00) >> 8;
    if (Math.abs(this.mDx) > Math.abs(this.mDy)) {
      i = checkHorizontalSwipe(paramViewHolder, j);
      if (i > 0)
        return ((k & i) == 0) ? Callback.convertToRelativeDirection(i, ViewCompat.getLayoutDirection((View)this.mRecyclerView)) : i; 
      j = checkVerticalSwipe(paramViewHolder, j);
      if (j > 0)
        return j; 
    } else {
      i = checkVerticalSwipe(paramViewHolder, j);
      if (i > 0)
        return i; 
      i = checkHorizontalSwipe(paramViewHolder, j);
      if (i > 0) {
        j = i;
        if ((k & i) == 0)
          j = Callback.convertToRelativeDirection(i, ViewCompat.getLayoutDirection((View)this.mRecyclerView)); 
        return j;
      } 
    } 
    return 0;
  }
  
  public void attachToRecyclerView(RecyclerView paramRecyclerView) {
    RecyclerView recyclerView = this.mRecyclerView;
    if (recyclerView == paramRecyclerView)
      return; 
    if (recyclerView != null)
      destroyCallbacks(); 
    this.mRecyclerView = paramRecyclerView;
    if (paramRecyclerView != null) {
      Resources resources = paramRecyclerView.getResources();
      this.mSwipeEscapeVelocity = resources.getDimension(R.dimen.item_touch_helper_swipe_escape_velocity);
      this.mMaxSwipeVelocity = resources.getDimension(R.dimen.item_touch_helper_swipe_escape_max_velocity);
      setupCallbacks();
    } 
  }
  
  boolean checkSelectForSwipe(int paramInt1, MotionEvent paramMotionEvent, int paramInt2) {
    if (this.mSelected != null || paramInt1 != 2 || this.mActionState == 2 || !this.mCallback.isItemViewSwipeEnabled())
      return false; 
    if (this.mRecyclerView.getScrollState() == 1)
      return false; 
    RecyclerView.ViewHolder viewHolder = findSwipedView(paramMotionEvent);
    if (viewHolder == null)
      return false; 
    paramInt1 = (this.mCallback.getAbsoluteMovementFlags(this.mRecyclerView, viewHolder) & 0xFF00) >> 8;
    if (paramInt1 == 0)
      return false; 
    float f1 = paramMotionEvent.getX(paramInt2);
    float f2 = paramMotionEvent.getY(paramInt2);
    f1 -= this.mInitialTouchX;
    f2 -= this.mInitialTouchY;
    float f3 = Math.abs(f1);
    float f4 = Math.abs(f2);
    paramInt2 = this.mSlop;
    if (f3 < paramInt2 && f4 < paramInt2)
      return false; 
    if (f3 > f4) {
      if (f1 < 0.0F && (paramInt1 & 0x4) == 0)
        return false; 
      if (f1 > 0.0F && (paramInt1 & 0x8) == 0)
        return false; 
    } else {
      if (f2 < 0.0F && (paramInt1 & 0x1) == 0)
        return false; 
      if (f2 > 0.0F && (paramInt1 & 0x2) == 0)
        return false; 
    } 
    this.mDy = 0.0F;
    this.mDx = 0.0F;
    this.mActivePointerId = paramMotionEvent.getPointerId(0);
    select(viewHolder, 1);
    return true;
  }
  
  int endRecoverAnimation(RecyclerView.ViewHolder paramViewHolder, boolean paramBoolean) {
    for (int i = this.mRecoverAnimations.size() - 1; i >= 0; i--) {
      RecoverAnimation recoverAnimation = this.mRecoverAnimations.get(i);
      if (recoverAnimation.mViewHolder == paramViewHolder) {
        recoverAnimation.mOverridden |= paramBoolean;
        if (!recoverAnimation.mEnded)
          recoverAnimation.cancel(); 
        this.mRecoverAnimations.remove(i);
        return recoverAnimation.mAnimationType;
      } 
    } 
    return 0;
  }
  
  RecoverAnimation findAnimation(MotionEvent paramMotionEvent) {
    if (this.mRecoverAnimations.isEmpty())
      return null; 
    View view = findChildView(paramMotionEvent);
    for (int i = this.mRecoverAnimations.size() - 1; i >= 0; i--) {
      RecoverAnimation recoverAnimation = this.mRecoverAnimations.get(i);
      if (recoverAnimation.mViewHolder.itemView == view)
        return recoverAnimation; 
    } 
    return null;
  }
  
  View findChildView(MotionEvent paramMotionEvent) {
    float f1 = paramMotionEvent.getX();
    float f2 = paramMotionEvent.getY();
    RecyclerView.ViewHolder viewHolder = this.mSelected;
    if (viewHolder != null) {
      View view = viewHolder.itemView;
      if (hitTest(view, f1, f2, this.mSelectedStartX + this.mDx, this.mSelectedStartY + this.mDy))
        return view; 
    } 
    for (int i = this.mRecoverAnimations.size() - 1; i >= 0; i--) {
      RecoverAnimation recoverAnimation = this.mRecoverAnimations.get(i);
      View view = recoverAnimation.mViewHolder.itemView;
      if (hitTest(view, f1, f2, recoverAnimation.mX, recoverAnimation.mY))
        return view; 
    } 
    return this.mRecyclerView.findChildViewUnder(f1, f2);
  }
  
  public void getItemOffsets(Rect paramRect, View paramView, RecyclerView paramRecyclerView, RecyclerView.State paramState) {
    paramRect.setEmpty();
  }
  
  boolean hasRunningRecoverAnim() {
    int i = this.mRecoverAnimations.size();
    for (byte b = 0; b < i; b++) {
      if (!((RecoverAnimation)this.mRecoverAnimations.get(b)).mEnded)
        return true; 
    } 
    return false;
  }
  
  void moveIfNecessary(RecyclerView.ViewHolder paramViewHolder) {
    if (this.mRecyclerView.isLayoutRequested())
      return; 
    if (this.mActionState != 2)
      return; 
    float f = this.mCallback.getMoveThreshold(paramViewHolder);
    int i = (int)(this.mSelectedStartX + this.mDx);
    int j = (int)(this.mSelectedStartY + this.mDy);
    if (Math.abs(j - paramViewHolder.itemView.getTop()) < paramViewHolder.itemView.getHeight() * f && Math.abs(i - paramViewHolder.itemView.getLeft()) < paramViewHolder.itemView.getWidth() * f)
      return; 
    List<RecyclerView.ViewHolder> list = findSwapTargets(paramViewHolder);
    if (list.size() == 0)
      return; 
    RecyclerView.ViewHolder viewHolder = this.mCallback.chooseDropTarget(paramViewHolder, list, i, j);
    if (viewHolder == null) {
      this.mSwapTargets.clear();
      this.mDistances.clear();
      return;
    } 
    int k = viewHolder.getAdapterPosition();
    int m = paramViewHolder.getAdapterPosition();
    if (this.mCallback.onMove(this.mRecyclerView, paramViewHolder, viewHolder))
      this.mCallback.onMoved(this.mRecyclerView, paramViewHolder, m, viewHolder, k, i, j); 
  }
  
  void obtainVelocityTracker() {
    VelocityTracker velocityTracker = this.mVelocityTracker;
    if (velocityTracker != null)
      velocityTracker.recycle(); 
    this.mVelocityTracker = VelocityTracker.obtain();
  }
  
  public void onChildViewAttachedToWindow(View paramView) {}
  
  public void onChildViewDetachedFromWindow(View paramView) {
    removeChildDrawingOrderCallbackIfNecessary(paramView);
    RecyclerView.ViewHolder viewHolder1 = this.mRecyclerView.getChildViewHolder(paramView);
    if (viewHolder1 == null)
      return; 
    RecyclerView.ViewHolder viewHolder2 = this.mSelected;
    if (viewHolder2 != null && viewHolder1 == viewHolder2) {
      select(null, 0);
    } else {
      endRecoverAnimation(viewHolder1, false);
      if (this.mPendingCleanup.remove(viewHolder1.itemView))
        this.mCallback.clearView(this.mRecyclerView, viewHolder1); 
    } 
  }
  
  public void onDraw(Canvas paramCanvas, RecyclerView paramRecyclerView, RecyclerView.State paramState) {
    float f1;
    float f2;
    this.mOverdrawChildPosition = -1;
    if (this.mSelected != null) {
      getSelectedDxDy(this.mTmpPosition);
      float[] arrayOfFloat = this.mTmpPosition;
      f1 = arrayOfFloat[0];
      f2 = arrayOfFloat[1];
    } else {
      f1 = 0.0F;
      f2 = 0.0F;
    } 
    this.mCallback.onDraw(paramCanvas, paramRecyclerView, this.mSelected, this.mRecoverAnimations, this.mActionState, f1, f2);
  }
  
  public void onDrawOver(Canvas paramCanvas, RecyclerView paramRecyclerView, RecyclerView.State paramState) {
    float f1;
    float f2;
    if (this.mSelected != null) {
      getSelectedDxDy(this.mTmpPosition);
      float[] arrayOfFloat = this.mTmpPosition;
      f1 = arrayOfFloat[0];
      f2 = arrayOfFloat[1];
    } else {
      f1 = 0.0F;
      f2 = 0.0F;
    } 
    this.mCallback.onDrawOver(paramCanvas, paramRecyclerView, this.mSelected, this.mRecoverAnimations, this.mActionState, f1, f2);
  }
  
  void postDispatchSwipe(final RecoverAnimation anim, final int swipeDir) {
    this.mRecyclerView.post(new Runnable() {
          public void run() {
            if (ItemTouchHelper.this.mRecyclerView != null && ItemTouchHelper.this.mRecyclerView.isAttachedToWindow() && !anim.mOverridden && anim.mViewHolder.getAdapterPosition() != -1) {
              RecyclerView.ItemAnimator itemAnimator = ItemTouchHelper.this.mRecyclerView.getItemAnimator();
              if ((itemAnimator == null || !itemAnimator.isRunning(null)) && !ItemTouchHelper.this.hasRunningRecoverAnim()) {
                ItemTouchHelper.this.mCallback.onSwiped(anim.mViewHolder, swipeDir);
              } else {
                ItemTouchHelper.this.mRecyclerView.post(this);
              } 
            } 
          }
        });
  }
  
  void removeChildDrawingOrderCallbackIfNecessary(View paramView) {
    if (paramView == this.mOverdrawChild) {
      this.mOverdrawChild = null;
      if (this.mChildDrawingOrderCallback != null)
        this.mRecyclerView.setChildDrawingOrderCallback(null); 
    } 
  }
  
  boolean scrollIfNecessary() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mSelected : Landroid/support/v7/widget/RecyclerView$ViewHolder;
    //   4: ifnonnull -> 16
    //   7: aload_0
    //   8: ldc2_w -9223372036854775808
    //   11: putfield mDragScrollStartTimeInMs : J
    //   14: iconst_0
    //   15: ireturn
    //   16: invokestatic currentTimeMillis : ()J
    //   19: lstore_1
    //   20: aload_0
    //   21: getfield mDragScrollStartTimeInMs : J
    //   24: lstore_3
    //   25: lload_3
    //   26: ldc2_w -9223372036854775808
    //   29: lcmp
    //   30: ifne -> 38
    //   33: lconst_0
    //   34: lstore_3
    //   35: goto -> 42
    //   38: lload_1
    //   39: lload_3
    //   40: lsub
    //   41: lstore_3
    //   42: aload_0
    //   43: getfield mRecyclerView : Landroid/support/v7/widget/RecyclerView;
    //   46: invokevirtual getLayoutManager : ()Landroid/support/v7/widget/RecyclerView$LayoutManager;
    //   49: astore #5
    //   51: aload_0
    //   52: getfield mTmpRect : Landroid/graphics/Rect;
    //   55: ifnonnull -> 69
    //   58: aload_0
    //   59: new android/graphics/Rect
    //   62: dup
    //   63: invokespecial <init> : ()V
    //   66: putfield mTmpRect : Landroid/graphics/Rect;
    //   69: aload #5
    //   71: aload_0
    //   72: getfield mSelected : Landroid/support/v7/widget/RecyclerView$ViewHolder;
    //   75: getfield itemView : Landroid/view/View;
    //   78: aload_0
    //   79: getfield mTmpRect : Landroid/graphics/Rect;
    //   82: invokevirtual calculateItemDecorationsForChild : (Landroid/view/View;Landroid/graphics/Rect;)V
    //   85: aload #5
    //   87: invokevirtual canScrollHorizontally : ()Z
    //   90: ifeq -> 198
    //   93: aload_0
    //   94: getfield mSelectedStartX : F
    //   97: aload_0
    //   98: getfield mDx : F
    //   101: fadd
    //   102: f2i
    //   103: istore #6
    //   105: iload #6
    //   107: aload_0
    //   108: getfield mTmpRect : Landroid/graphics/Rect;
    //   111: getfield left : I
    //   114: isub
    //   115: aload_0
    //   116: getfield mRecyclerView : Landroid/support/v7/widget/RecyclerView;
    //   119: invokevirtual getPaddingLeft : ()I
    //   122: isub
    //   123: istore #7
    //   125: aload_0
    //   126: getfield mDx : F
    //   129: fconst_0
    //   130: fcmpg
    //   131: ifge -> 142
    //   134: iload #7
    //   136: ifge -> 142
    //   139: goto -> 201
    //   142: aload_0
    //   143: getfield mDx : F
    //   146: fconst_0
    //   147: fcmpl
    //   148: ifle -> 198
    //   151: iload #6
    //   153: aload_0
    //   154: getfield mSelected : Landroid/support/v7/widget/RecyclerView$ViewHolder;
    //   157: getfield itemView : Landroid/view/View;
    //   160: invokevirtual getWidth : ()I
    //   163: iadd
    //   164: aload_0
    //   165: getfield mTmpRect : Landroid/graphics/Rect;
    //   168: getfield right : I
    //   171: iadd
    //   172: aload_0
    //   173: getfield mRecyclerView : Landroid/support/v7/widget/RecyclerView;
    //   176: invokevirtual getWidth : ()I
    //   179: aload_0
    //   180: getfield mRecyclerView : Landroid/support/v7/widget/RecyclerView;
    //   183: invokevirtual getPaddingRight : ()I
    //   186: isub
    //   187: isub
    //   188: istore #7
    //   190: iload #7
    //   192: ifle -> 198
    //   195: goto -> 201
    //   198: iconst_0
    //   199: istore #7
    //   201: aload #5
    //   203: invokevirtual canScrollVertically : ()Z
    //   206: ifeq -> 314
    //   209: aload_0
    //   210: getfield mSelectedStartY : F
    //   213: aload_0
    //   214: getfield mDy : F
    //   217: fadd
    //   218: f2i
    //   219: istore #8
    //   221: iload #8
    //   223: aload_0
    //   224: getfield mTmpRect : Landroid/graphics/Rect;
    //   227: getfield top : I
    //   230: isub
    //   231: aload_0
    //   232: getfield mRecyclerView : Landroid/support/v7/widget/RecyclerView;
    //   235: invokevirtual getPaddingTop : ()I
    //   238: isub
    //   239: istore #6
    //   241: aload_0
    //   242: getfield mDy : F
    //   245: fconst_0
    //   246: fcmpg
    //   247: ifge -> 258
    //   250: iload #6
    //   252: ifge -> 258
    //   255: goto -> 317
    //   258: aload_0
    //   259: getfield mDy : F
    //   262: fconst_0
    //   263: fcmpl
    //   264: ifle -> 314
    //   267: iload #8
    //   269: aload_0
    //   270: getfield mSelected : Landroid/support/v7/widget/RecyclerView$ViewHolder;
    //   273: getfield itemView : Landroid/view/View;
    //   276: invokevirtual getHeight : ()I
    //   279: iadd
    //   280: aload_0
    //   281: getfield mTmpRect : Landroid/graphics/Rect;
    //   284: getfield bottom : I
    //   287: iadd
    //   288: aload_0
    //   289: getfield mRecyclerView : Landroid/support/v7/widget/RecyclerView;
    //   292: invokevirtual getHeight : ()I
    //   295: aload_0
    //   296: getfield mRecyclerView : Landroid/support/v7/widget/RecyclerView;
    //   299: invokevirtual getPaddingBottom : ()I
    //   302: isub
    //   303: isub
    //   304: istore #6
    //   306: iload #6
    //   308: ifle -> 314
    //   311: goto -> 317
    //   314: iconst_0
    //   315: istore #6
    //   317: iload #7
    //   319: istore #8
    //   321: iload #7
    //   323: ifeq -> 359
    //   326: aload_0
    //   327: getfield mCallback : Landroid/support/v7/widget/helper/ItemTouchHelper$Callback;
    //   330: aload_0
    //   331: getfield mRecyclerView : Landroid/support/v7/widget/RecyclerView;
    //   334: aload_0
    //   335: getfield mSelected : Landroid/support/v7/widget/RecyclerView$ViewHolder;
    //   338: getfield itemView : Landroid/view/View;
    //   341: invokevirtual getWidth : ()I
    //   344: iload #7
    //   346: aload_0
    //   347: getfield mRecyclerView : Landroid/support/v7/widget/RecyclerView;
    //   350: invokevirtual getWidth : ()I
    //   353: lload_3
    //   354: invokevirtual interpolateOutOfBoundsScroll : (Landroid/support/v7/widget/RecyclerView;IIIJ)I
    //   357: istore #8
    //   359: iload #6
    //   361: ifeq -> 400
    //   364: aload_0
    //   365: getfield mCallback : Landroid/support/v7/widget/helper/ItemTouchHelper$Callback;
    //   368: aload_0
    //   369: getfield mRecyclerView : Landroid/support/v7/widget/RecyclerView;
    //   372: aload_0
    //   373: getfield mSelected : Landroid/support/v7/widget/RecyclerView$ViewHolder;
    //   376: getfield itemView : Landroid/view/View;
    //   379: invokevirtual getHeight : ()I
    //   382: iload #6
    //   384: aload_0
    //   385: getfield mRecyclerView : Landroid/support/v7/widget/RecyclerView;
    //   388: invokevirtual getHeight : ()I
    //   391: lload_3
    //   392: invokevirtual interpolateOutOfBoundsScroll : (Landroid/support/v7/widget/RecyclerView;IIIJ)I
    //   395: istore #6
    //   397: goto -> 400
    //   400: iload #8
    //   402: ifne -> 422
    //   405: iload #6
    //   407: ifeq -> 413
    //   410: goto -> 422
    //   413: aload_0
    //   414: ldc2_w -9223372036854775808
    //   417: putfield mDragScrollStartTimeInMs : J
    //   420: iconst_0
    //   421: ireturn
    //   422: aload_0
    //   423: getfield mDragScrollStartTimeInMs : J
    //   426: ldc2_w -9223372036854775808
    //   429: lcmp
    //   430: ifne -> 438
    //   433: aload_0
    //   434: lload_1
    //   435: putfield mDragScrollStartTimeInMs : J
    //   438: aload_0
    //   439: getfield mRecyclerView : Landroid/support/v7/widget/RecyclerView;
    //   442: iload #8
    //   444: iload #6
    //   446: invokevirtual scrollBy : (II)V
    //   449: iconst_1
    //   450: ireturn
  }
  
  void select(RecyclerView.ViewHolder paramViewHolder, int paramInt) {
    boolean bool;
    if (paramViewHolder == this.mSelected && paramInt == this.mActionState)
      return; 
    this.mDragScrollStartTimeInMs = Long.MIN_VALUE;
    int i = this.mActionState;
    endRecoverAnimation(paramViewHolder, true);
    this.mActionState = paramInt;
    if (paramInt == 2) {
      this.mOverdrawChild = paramViewHolder.itemView;
      addChildDrawingOrderCallback();
    } 
    final RecyclerView.ViewHolder prevSelected = this.mSelected;
    if (viewHolder != null) {
      if (viewHolder.itemView.getParent() != null) {
        final int swipeDir;
        float f1;
        float f2;
        if (i == 2) {
          j = 0;
        } else {
          j = swipeIfNecessary(viewHolder);
        } 
        releaseVelocityTracker();
        if (j != 1 && j != 2) {
          if (j != 4 && j != 8 && j != 16 && j != 32) {
            f1 = 0.0F;
          } else {
            f1 = Math.signum(this.mDx) * this.mRecyclerView.getWidth();
          } 
          f2 = 0.0F;
        } else {
          f2 = Math.signum(this.mDy) * this.mRecyclerView.getHeight();
          f1 = 0.0F;
        } 
        if (i == 2) {
          bool = true;
        } else if (j > 0) {
          bool = true;
        } else {
          bool = true;
        } 
        getSelectedDxDy(this.mTmpPosition);
        float[] arrayOfFloat = this.mTmpPosition;
        float f3 = arrayOfFloat[0];
        float f4 = arrayOfFloat[1];
        RecoverAnimation recoverAnimation = new RecoverAnimation(viewHolder, bool, i, f3, f4, f1, f2) {
            public void onAnimationEnd(Animator param1Animator) {
              super.onAnimationEnd(param1Animator);
              if (this.mOverridden)
                return; 
              if (swipeDir <= 0) {
                ItemTouchHelper.this.mCallback.clearView(ItemTouchHelper.this.mRecyclerView, prevSelected);
              } else {
                ItemTouchHelper.this.mPendingCleanup.add(prevSelected.itemView);
                this.mIsPendingCleanup = true;
                int i = swipeDir;
                if (i > 0)
                  ItemTouchHelper.this.postDispatchSwipe(this, i); 
              } 
              if (ItemTouchHelper.this.mOverdrawChild == prevSelected.itemView)
                ItemTouchHelper.this.removeChildDrawingOrderCallbackIfNecessary(prevSelected.itemView); 
            }
          };
        recoverAnimation.setDuration(this.mCallback.getAnimationDuration(this.mRecyclerView, bool, f1 - f3, f2 - f4));
        this.mRecoverAnimations.add(recoverAnimation);
        recoverAnimation.start();
        bool = true;
      } else {
        removeChildDrawingOrderCallbackIfNecessary(viewHolder.itemView);
        this.mCallback.clearView(this.mRecyclerView, viewHolder);
        bool = false;
      } 
      this.mSelected = null;
    } else {
      bool = false;
    } 
    if (paramViewHolder != null) {
      this.mSelectedFlags = (this.mCallback.getAbsoluteMovementFlags(this.mRecyclerView, paramViewHolder) & (1 << paramInt * 8 + 8) - 1) >> this.mActionState * 8;
      this.mSelectedStartX = paramViewHolder.itemView.getLeft();
      this.mSelectedStartY = paramViewHolder.itemView.getTop();
      this.mSelected = paramViewHolder;
      if (paramInt == 2)
        paramViewHolder.itemView.performHapticFeedback(0); 
    } 
    ViewParent viewParent = this.mRecyclerView.getParent();
    if (viewParent != null) {
      boolean bool1;
      if (this.mSelected != null) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      viewParent.requestDisallowInterceptTouchEvent(bool1);
    } 
    if (!bool)
      this.mRecyclerView.getLayoutManager().requestSimpleAnimationsInNextLayout(); 
    this.mCallback.onSelectedChanged(this.mSelected, this.mActionState);
    this.mRecyclerView.invalidate();
  }
  
  public void startDrag(RecyclerView.ViewHolder paramViewHolder) {
    if (!this.mCallback.hasDragFlag(this.mRecyclerView, paramViewHolder)) {
      Log.e("ItemTouchHelper", "Start drag has been called but dragging is not enabled");
      return;
    } 
    if (paramViewHolder.itemView.getParent() != this.mRecyclerView) {
      Log.e("ItemTouchHelper", "Start drag has been called with a view holder which is not a child of the RecyclerView which is controlled by this ItemTouchHelper.");
      return;
    } 
    obtainVelocityTracker();
    this.mDy = 0.0F;
    this.mDx = 0.0F;
    select(paramViewHolder, 2);
  }
  
  public void startSwipe(RecyclerView.ViewHolder paramViewHolder) {
    if (!this.mCallback.hasSwipeFlag(this.mRecyclerView, paramViewHolder)) {
      Log.e("ItemTouchHelper", "Start swipe has been called but swiping is not enabled");
      return;
    } 
    if (paramViewHolder.itemView.getParent() != this.mRecyclerView) {
      Log.e("ItemTouchHelper", "Start swipe has been called with a view holder which is not a child of the RecyclerView controlled by this ItemTouchHelper.");
      return;
    } 
    obtainVelocityTracker();
    this.mDy = 0.0F;
    this.mDx = 0.0F;
    select(paramViewHolder, 1);
  }
  
  void updateDxDy(MotionEvent paramMotionEvent, int paramInt1, int paramInt2) {
    float f1 = paramMotionEvent.getX(paramInt2);
    float f2 = paramMotionEvent.getY(paramInt2);
    f1 -= this.mInitialTouchX;
    this.mDx = f1;
    this.mDy = f2 - this.mInitialTouchY;
    if ((paramInt1 & 0x4) == 0)
      this.mDx = Math.max(0.0F, f1); 
    if ((paramInt1 & 0x8) == 0)
      this.mDx = Math.min(0.0F, this.mDx); 
    if ((paramInt1 & 0x1) == 0)
      this.mDy = Math.max(0.0F, this.mDy); 
    if ((paramInt1 & 0x2) == 0)
      this.mDy = Math.min(0.0F, this.mDy); 
  }
  
  public static abstract class Callback {
    private static final int ABS_HORIZONTAL_DIR_FLAGS = 789516;
    
    public static final int DEFAULT_DRAG_ANIMATION_DURATION = 200;
    
    public static final int DEFAULT_SWIPE_ANIMATION_DURATION = 250;
    
    private static final long DRAG_SCROLL_ACCELERATION_LIMIT_TIME_MS = 2000L;
    
    static final int RELATIVE_DIR_FLAGS = 3158064;
    
    private static final Interpolator sDragScrollInterpolator = new Interpolator() {
        public float getInterpolation(float param2Float) {
          return param2Float * param2Float * param2Float * param2Float * param2Float;
        }
      };
    
    private static final Interpolator sDragViewScrollCapInterpolator = new Interpolator() {
        public float getInterpolation(float param2Float) {
          param2Float--;
          return param2Float * param2Float * param2Float * param2Float * param2Float + 1.0F;
        }
      };
    
    private static final ItemTouchUIUtil sUICallback;
    
    private int mCachedMaxScrollSpeed = -1;
    
    static {
      if (Build.VERSION.SDK_INT >= 21) {
        sUICallback = new ItemTouchUIUtilImpl.Api21Impl();
      } else {
        sUICallback = new ItemTouchUIUtilImpl.BaseImpl();
      } 
    }
    
    public static int convertToRelativeDirection(int param1Int1, int param1Int2) {
      int i = param1Int1 & 0xC0C0C;
      if (i == 0)
        return param1Int1; 
      param1Int1 &= i;
      if (param1Int2 == 0) {
        param1Int2 = i << 2;
        return param1Int1 | param1Int2;
      } 
      param1Int2 = i << 1;
      param1Int1 |= 0xFFF3F3F3 & param1Int2;
      param1Int2 = (param1Int2 & 0xC0C0C) << 2;
      return param1Int1 | param1Int2;
    }
    
    public static ItemTouchUIUtil getDefaultUIUtil() {
      return sUICallback;
    }
    
    private int getMaxDragScroll(RecyclerView param1RecyclerView) {
      if (this.mCachedMaxScrollSpeed == -1)
        this.mCachedMaxScrollSpeed = param1RecyclerView.getResources().getDimensionPixelSize(R.dimen.item_touch_helper_max_drag_scroll_per_frame); 
      return this.mCachedMaxScrollSpeed;
    }
    
    public static int makeFlag(int param1Int1, int param1Int2) {
      return param1Int2 << param1Int1 * 8;
    }
    
    public static int makeMovementFlags(int param1Int1, int param1Int2) {
      int i = makeFlag(0, param1Int2 | param1Int1);
      param1Int2 = makeFlag(1, param1Int2);
      return makeFlag(2, param1Int1) | param1Int2 | i;
    }
    
    public boolean canDropOver(RecyclerView param1RecyclerView, RecyclerView.ViewHolder param1ViewHolder1, RecyclerView.ViewHolder param1ViewHolder2) {
      return true;
    }
    
    public RecyclerView.ViewHolder chooseDropTarget(RecyclerView.ViewHolder param1ViewHolder, List<RecyclerView.ViewHolder> param1List, int param1Int1, int param1Int2) {
      int i = param1ViewHolder.itemView.getWidth();
      int j = param1ViewHolder.itemView.getHeight();
      int k = param1Int1 - param1ViewHolder.itemView.getLeft();
      int m = param1Int2 - param1ViewHolder.itemView.getTop();
      int n = param1List.size();
      RecyclerView.ViewHolder viewHolder = null;
      int i1 = -1;
      for (byte b = 0; b < n; b++) {
        RecyclerView.ViewHolder viewHolder1 = param1List.get(b);
        RecyclerView.ViewHolder viewHolder2 = viewHolder;
        int i2 = i1;
        if (k > 0) {
          int i3 = viewHolder1.itemView.getRight() - param1Int1 + i;
          viewHolder2 = viewHolder;
          i2 = i1;
          if (i3 < 0) {
            viewHolder2 = viewHolder;
            i2 = i1;
            if (viewHolder1.itemView.getRight() > param1ViewHolder.itemView.getRight()) {
              i3 = Math.abs(i3);
              viewHolder2 = viewHolder;
              i2 = i1;
              if (i3 > i1) {
                viewHolder2 = viewHolder1;
                i2 = i3;
              } 
            } 
          } 
        } 
        viewHolder = viewHolder2;
        i1 = i2;
        if (k < 0) {
          int i3 = viewHolder1.itemView.getLeft() - param1Int1;
          viewHolder = viewHolder2;
          i1 = i2;
          if (i3 > 0) {
            viewHolder = viewHolder2;
            i1 = i2;
            if (viewHolder1.itemView.getLeft() < param1ViewHolder.itemView.getLeft()) {
              i3 = Math.abs(i3);
              viewHolder = viewHolder2;
              i1 = i2;
              if (i3 > i2) {
                viewHolder = viewHolder1;
                i1 = i3;
              } 
            } 
          } 
        } 
        viewHolder2 = viewHolder;
        i2 = i1;
        if (m < 0) {
          int i3 = viewHolder1.itemView.getTop() - param1Int2;
          viewHolder2 = viewHolder;
          i2 = i1;
          if (i3 > 0) {
            viewHolder2 = viewHolder;
            i2 = i1;
            if (viewHolder1.itemView.getTop() < param1ViewHolder.itemView.getTop()) {
              i3 = Math.abs(i3);
              viewHolder2 = viewHolder;
              i2 = i1;
              if (i3 > i1) {
                viewHolder2 = viewHolder1;
                i2 = i3;
              } 
            } 
          } 
        } 
        viewHolder = viewHolder2;
        i1 = i2;
        if (m > 0) {
          int i3 = viewHolder1.itemView.getBottom() - param1Int2 + j;
          viewHolder = viewHolder2;
          i1 = i2;
          if (i3 < 0) {
            viewHolder = viewHolder2;
            i1 = i2;
            if (viewHolder1.itemView.getBottom() > param1ViewHolder.itemView.getBottom()) {
              i3 = Math.abs(i3);
              viewHolder = viewHolder2;
              i1 = i2;
              if (i3 > i2) {
                i1 = i3;
                viewHolder = viewHolder1;
              } 
            } 
          } 
        } 
      } 
      return viewHolder;
    }
    
    public void clearView(RecyclerView param1RecyclerView, RecyclerView.ViewHolder param1ViewHolder) {
      sUICallback.clearView(param1ViewHolder.itemView);
    }
    
    public int convertToAbsoluteDirection(int param1Int1, int param1Int2) {
      int i = param1Int1 & 0x303030;
      if (i == 0)
        return param1Int1; 
      int j = param1Int1 & i;
      if (param1Int2 == 0) {
        param1Int1 = i >> 2;
        param1Int2 = j;
        return param1Int2 | param1Int1;
      } 
      param1Int1 = i >> 1;
      param1Int2 = j | 0xFFCFCFCF & param1Int1;
      param1Int1 = (param1Int1 & 0x303030) >> 2;
      return param1Int2 | param1Int1;
    }
    
    final int getAbsoluteMovementFlags(RecyclerView param1RecyclerView, RecyclerView.ViewHolder param1ViewHolder) {
      return convertToAbsoluteDirection(getMovementFlags(param1RecyclerView, param1ViewHolder), ViewCompat.getLayoutDirection((View)param1RecyclerView));
    }
    
    public long getAnimationDuration(RecyclerView param1RecyclerView, int param1Int, float param1Float1, float param1Float2) {
      long l;
      RecyclerView.ItemAnimator itemAnimator = param1RecyclerView.getItemAnimator();
      if (itemAnimator == null) {
        if (param1Int == 8) {
          l = 200L;
        } else {
          l = 250L;
        } 
        return l;
      } 
      if (param1Int == 8) {
        l = itemAnimator.getMoveDuration();
      } else {
        l = itemAnimator.getRemoveDuration();
      } 
      return l;
    }
    
    public int getBoundingBoxMargin() {
      return 0;
    }
    
    public float getMoveThreshold(RecyclerView.ViewHolder param1ViewHolder) {
      return 0.5F;
    }
    
    public abstract int getMovementFlags(RecyclerView param1RecyclerView, RecyclerView.ViewHolder param1ViewHolder);
    
    public float getSwipeEscapeVelocity(float param1Float) {
      return param1Float;
    }
    
    public float getSwipeThreshold(RecyclerView.ViewHolder param1ViewHolder) {
      return 0.5F;
    }
    
    public float getSwipeVelocityThreshold(float param1Float) {
      return param1Float;
    }
    
    boolean hasDragFlag(RecyclerView param1RecyclerView, RecyclerView.ViewHolder param1ViewHolder) {
      boolean bool;
      if ((getAbsoluteMovementFlags(param1RecyclerView, param1ViewHolder) & 0xFF0000) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    boolean hasSwipeFlag(RecyclerView param1RecyclerView, RecyclerView.ViewHolder param1ViewHolder) {
      boolean bool;
      if ((getAbsoluteMovementFlags(param1RecyclerView, param1ViewHolder) & 0xFF00) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public int interpolateOutOfBoundsScroll(RecyclerView param1RecyclerView, int param1Int1, int param1Int2, int param1Int3, long param1Long) {
      int i = getMaxDragScroll(param1RecyclerView);
      int j = Math.abs(param1Int2);
      param1Int3 = (int)Math.signum(param1Int2);
      float f1 = j;
      float f2 = 1.0F;
      f1 = Math.min(1.0F, f1 * 1.0F / param1Int1);
      param1Int1 = (int)((param1Int3 * i) * sDragViewScrollCapInterpolator.getInterpolation(f1));
      if (param1Long <= 2000L)
        f2 = (float)param1Long / 2000.0F; 
      param1Int3 = (int)(param1Int1 * sDragScrollInterpolator.getInterpolation(f2));
      param1Int1 = param1Int3;
      if (param1Int3 == 0)
        if (param1Int2 > 0) {
          param1Int1 = 1;
        } else {
          param1Int1 = -1;
        }  
      return param1Int1;
    }
    
    public boolean isItemViewSwipeEnabled() {
      return true;
    }
    
    public boolean isLongPressDragEnabled() {
      return true;
    }
    
    public void onChildDraw(Canvas param1Canvas, RecyclerView param1RecyclerView, RecyclerView.ViewHolder param1ViewHolder, float param1Float1, float param1Float2, int param1Int, boolean param1Boolean) {
      sUICallback.onDraw(param1Canvas, param1RecyclerView, param1ViewHolder.itemView, param1Float1, param1Float2, param1Int, param1Boolean);
    }
    
    public void onChildDrawOver(Canvas param1Canvas, RecyclerView param1RecyclerView, RecyclerView.ViewHolder param1ViewHolder, float param1Float1, float param1Float2, int param1Int, boolean param1Boolean) {
      sUICallback.onDrawOver(param1Canvas, param1RecyclerView, param1ViewHolder.itemView, param1Float1, param1Float2, param1Int, param1Boolean);
    }
    
    void onDraw(Canvas param1Canvas, RecyclerView param1RecyclerView, RecyclerView.ViewHolder param1ViewHolder, List<ItemTouchHelper.RecoverAnimation> param1List, int param1Int, float param1Float1, float param1Float2) {
      int i = param1List.size();
      int j;
      for (j = 0; j < i; j++) {
        ItemTouchHelper.RecoverAnimation recoverAnimation = param1List.get(j);
        recoverAnimation.update();
        int k = param1Canvas.save();
        onChildDraw(param1Canvas, param1RecyclerView, recoverAnimation.mViewHolder, recoverAnimation.mX, recoverAnimation.mY, recoverAnimation.mActionState, false);
        param1Canvas.restoreToCount(k);
      } 
      if (param1ViewHolder != null) {
        j = param1Canvas.save();
        onChildDraw(param1Canvas, param1RecyclerView, param1ViewHolder, param1Float1, param1Float2, param1Int, true);
        param1Canvas.restoreToCount(j);
      } 
    }
    
    void onDrawOver(Canvas param1Canvas, RecyclerView param1RecyclerView, RecyclerView.ViewHolder param1ViewHolder, List<ItemTouchHelper.RecoverAnimation> param1List, int param1Int, float param1Float1, float param1Float2) {
      int i = param1List.size();
      boolean bool = false;
      int j;
      for (j = 0; j < i; j++) {
        ItemTouchHelper.RecoverAnimation recoverAnimation = param1List.get(j);
        int k = param1Canvas.save();
        onChildDrawOver(param1Canvas, param1RecyclerView, recoverAnimation.mViewHolder, recoverAnimation.mX, recoverAnimation.mY, recoverAnimation.mActionState, false);
        param1Canvas.restoreToCount(k);
      } 
      if (param1ViewHolder != null) {
        j = param1Canvas.save();
        onChildDrawOver(param1Canvas, param1RecyclerView, param1ViewHolder, param1Float1, param1Float2, param1Int, true);
        param1Canvas.restoreToCount(j);
      } 
      param1Int = i - 1;
      j = bool;
      while (param1Int >= 0) {
        ItemTouchHelper.RecoverAnimation recoverAnimation = param1List.get(param1Int);
        if (recoverAnimation.mEnded && !recoverAnimation.mIsPendingCleanup) {
          param1List.remove(param1Int);
        } else if (!recoverAnimation.mEnded) {
          j = 1;
        } 
        param1Int--;
      } 
      if (j != 0)
        param1RecyclerView.invalidate(); 
    }
    
    public abstract boolean onMove(RecyclerView param1RecyclerView, RecyclerView.ViewHolder param1ViewHolder1, RecyclerView.ViewHolder param1ViewHolder2);
    
    public void onMoved(RecyclerView param1RecyclerView, RecyclerView.ViewHolder param1ViewHolder1, int param1Int1, RecyclerView.ViewHolder param1ViewHolder2, int param1Int2, int param1Int3, int param1Int4) {
      RecyclerView.LayoutManager layoutManager = param1RecyclerView.getLayoutManager();
      if (layoutManager instanceof ItemTouchHelper.ViewDropHandler) {
        ((ItemTouchHelper.ViewDropHandler)layoutManager).prepareForDrop(param1ViewHolder1.itemView, param1ViewHolder2.itemView, param1Int3, param1Int4);
        return;
      } 
      if (layoutManager.canScrollHorizontally()) {
        if (layoutManager.getDecoratedLeft(param1ViewHolder2.itemView) <= param1RecyclerView.getPaddingLeft())
          param1RecyclerView.scrollToPosition(param1Int2); 
        if (layoutManager.getDecoratedRight(param1ViewHolder2.itemView) >= param1RecyclerView.getWidth() - param1RecyclerView.getPaddingRight())
          param1RecyclerView.scrollToPosition(param1Int2); 
      } 
      if (layoutManager.canScrollVertically()) {
        if (layoutManager.getDecoratedTop(param1ViewHolder2.itemView) <= param1RecyclerView.getPaddingTop())
          param1RecyclerView.scrollToPosition(param1Int2); 
        if (layoutManager.getDecoratedBottom(param1ViewHolder2.itemView) >= param1RecyclerView.getHeight() - param1RecyclerView.getPaddingBottom())
          param1RecyclerView.scrollToPosition(param1Int2); 
      } 
    }
    
    public void onSelectedChanged(RecyclerView.ViewHolder param1ViewHolder, int param1Int) {
      if (param1ViewHolder != null)
        sUICallback.onSelected(param1ViewHolder.itemView); 
    }
    
    public abstract void onSwiped(RecyclerView.ViewHolder param1ViewHolder, int param1Int);
  }
  
  static final class null implements Interpolator {
    public float getInterpolation(float param1Float) {
      return param1Float * param1Float * param1Float * param1Float * param1Float;
    }
  }
  
  static final class null implements Interpolator {
    public float getInterpolation(float param1Float) {
      param1Float--;
      return param1Float * param1Float * param1Float * param1Float * param1Float + 1.0F;
    }
  }
  
  private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
    private boolean mShouldReactToLongPress = true;
    
    void doNotReactToLongPress() {
      this.mShouldReactToLongPress = false;
    }
    
    public boolean onDown(MotionEvent param1MotionEvent) {
      return true;
    }
    
    public void onLongPress(MotionEvent param1MotionEvent) {
      if (!this.mShouldReactToLongPress)
        return; 
      View view = ItemTouchHelper.this.findChildView(param1MotionEvent);
      if (view != null) {
        RecyclerView.ViewHolder viewHolder = ItemTouchHelper.this.mRecyclerView.getChildViewHolder(view);
        if (viewHolder != null) {
          if (!ItemTouchHelper.this.mCallback.hasDragFlag(ItemTouchHelper.this.mRecyclerView, viewHolder))
            return; 
          if (param1MotionEvent.getPointerId(0) == ItemTouchHelper.this.mActivePointerId) {
            int i = param1MotionEvent.findPointerIndex(ItemTouchHelper.this.mActivePointerId);
            float f1 = param1MotionEvent.getX(i);
            float f2 = param1MotionEvent.getY(i);
            ItemTouchHelper.this.mInitialTouchX = f1;
            ItemTouchHelper.this.mInitialTouchY = f2;
            ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
            itemTouchHelper.mDy = 0.0F;
            itemTouchHelper.mDx = 0.0F;
            if (ItemTouchHelper.this.mCallback.isLongPressDragEnabled())
              ItemTouchHelper.this.select(viewHolder, 2); 
          } 
        } 
      } 
    }
  }
  
  private static class RecoverAnimation implements Animator.AnimatorListener {
    final int mActionState;
    
    final int mAnimationType;
    
    boolean mEnded = false;
    
    private float mFraction;
    
    public boolean mIsPendingCleanup;
    
    boolean mOverridden = false;
    
    final float mStartDx;
    
    final float mStartDy;
    
    final float mTargetX;
    
    final float mTargetY;
    
    private final ValueAnimator mValueAnimator;
    
    final RecyclerView.ViewHolder mViewHolder;
    
    float mX;
    
    float mY;
    
    RecoverAnimation(RecyclerView.ViewHolder param1ViewHolder, int param1Int1, int param1Int2, float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
      this.mActionState = param1Int2;
      this.mAnimationType = param1Int1;
      this.mViewHolder = param1ViewHolder;
      this.mStartDx = param1Float1;
      this.mStartDy = param1Float2;
      this.mTargetX = param1Float3;
      this.mTargetY = param1Float4;
      ValueAnimator valueAnimator = ValueAnimator.ofFloat(new float[] { 0.0F, 1.0F });
      this.mValueAnimator = valueAnimator;
      valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator param2ValueAnimator) {
              ItemTouchHelper.RecoverAnimation.this.setFraction(param2ValueAnimator.getAnimatedFraction());
            }
          });
      this.mValueAnimator.setTarget(param1ViewHolder.itemView);
      this.mValueAnimator.addListener(this);
      setFraction(0.0F);
    }
    
    public void cancel() {
      this.mValueAnimator.cancel();
    }
    
    public void onAnimationCancel(Animator param1Animator) {
      setFraction(1.0F);
    }
    
    public void onAnimationEnd(Animator param1Animator) {
      if (!this.mEnded)
        this.mViewHolder.setIsRecyclable(true); 
      this.mEnded = true;
    }
    
    public void onAnimationRepeat(Animator param1Animator) {}
    
    public void onAnimationStart(Animator param1Animator) {}
    
    public void setDuration(long param1Long) {
      this.mValueAnimator.setDuration(param1Long);
    }
    
    public void setFraction(float param1Float) {
      this.mFraction = param1Float;
    }
    
    public void start() {
      this.mViewHolder.setIsRecyclable(false);
      this.mValueAnimator.start();
    }
    
    public void update() {
      float f1 = this.mStartDx;
      float f2 = this.mTargetX;
      if (f1 == f2) {
        this.mX = this.mViewHolder.itemView.getTranslationX();
      } else {
        this.mX = f1 + this.mFraction * (f2 - f1);
      } 
      f2 = this.mStartDy;
      f1 = this.mTargetY;
      if (f2 == f1) {
        this.mY = this.mViewHolder.itemView.getTranslationY();
      } else {
        this.mY = f2 + this.mFraction * (f1 - f2);
      } 
    }
  }
  
  class null implements ValueAnimator.AnimatorUpdateListener {
    public void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
      this.this$0.setFraction(param1ValueAnimator.getAnimatedFraction());
    }
  }
  
  public static abstract class SimpleCallback extends Callback {
    private int mDefaultDragDirs;
    
    private int mDefaultSwipeDirs;
    
    public SimpleCallback(int param1Int1, int param1Int2) {
      this.mDefaultSwipeDirs = param1Int2;
      this.mDefaultDragDirs = param1Int1;
    }
    
    public int getDragDirs(RecyclerView param1RecyclerView, RecyclerView.ViewHolder param1ViewHolder) {
      return this.mDefaultDragDirs;
    }
    
    public int getMovementFlags(RecyclerView param1RecyclerView, RecyclerView.ViewHolder param1ViewHolder) {
      return makeMovementFlags(getDragDirs(param1RecyclerView, param1ViewHolder), getSwipeDirs(param1RecyclerView, param1ViewHolder));
    }
    
    public int getSwipeDirs(RecyclerView param1RecyclerView, RecyclerView.ViewHolder param1ViewHolder) {
      return this.mDefaultSwipeDirs;
    }
    
    public void setDefaultDragDirs(int param1Int) {
      this.mDefaultDragDirs = param1Int;
    }
    
    public void setDefaultSwipeDirs(int param1Int) {
      this.mDefaultSwipeDirs = param1Int;
    }
  }
  
  public static interface ViewDropHandler {
    void prepareForDrop(View param1View1, View param1View2, int param1Int1, int param1Int2);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\helper\ItemTouchHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */