package io.virtualapp.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import io.virtualapp.R;

public class DragSelectRecyclerView extends RecyclerView {
  private static final int AUTO_SCROLL_DELAY = 25;
  
  private static final boolean LOGGING = false;
  
  private DragSelectRecyclerViewAdapter<?> mAdapter;
  
  private Handler mAutoScrollHandler;
  
  private Runnable mAutoScrollRunnable = new Runnable() {
      public void run() {
        if (DragSelectRecyclerView.this.mAutoScrollHandler == null)
          return; 
        if (DragSelectRecyclerView.this.mInTopHotspot) {
          DragSelectRecyclerView dragSelectRecyclerView = DragSelectRecyclerView.this;
          dragSelectRecyclerView.scrollBy(0, -dragSelectRecyclerView.mAutoScrollVelocity);
          DragSelectRecyclerView.this.mAutoScrollHandler.postDelayed(this, 25L);
        } else if (DragSelectRecyclerView.this.mInBottomHotspot) {
          DragSelectRecyclerView dragSelectRecyclerView = DragSelectRecyclerView.this;
          dragSelectRecyclerView.scrollBy(0, dragSelectRecyclerView.mAutoScrollVelocity);
          DragSelectRecyclerView.this.mAutoScrollHandler.postDelayed(this, 25L);
        } 
      }
    };
  
  private int mAutoScrollVelocity;
  
  private RectF mBottomBoundRect;
  
  private boolean mDebugEnabled = false;
  
  private Paint mDebugPaint;
  
  private boolean mDragSelectActive;
  
  private FingerListener mFingerListener;
  
  private int mHotspotBottomBoundEnd;
  
  private int mHotspotBottomBoundStart;
  
  private int mHotspotHeight;
  
  private int mHotspotOffsetBottom;
  
  private int mHotspotOffsetTop;
  
  private int mHotspotTopBoundEnd;
  
  private int mHotspotTopBoundStart;
  
  private boolean mInBottomHotspot;
  
  private boolean mInTopHotspot;
  
  private int mInitialSelection;
  
  private int mLastDraggedIndex = -1;
  
  private int mMaxReached;
  
  private int mMinReached;
  
  private RectF mTopBoundRect;
  
  public DragSelectRecyclerView(Context paramContext) {
    super(paramContext);
    init(paramContext, (AttributeSet)null);
  }
  
  public DragSelectRecyclerView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    init(paramContext, paramAttributeSet);
  }
  
  public DragSelectRecyclerView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet);
  }
  
  private static void LOG(String paramString, Object... paramVarArgs) {}
  
  private int getItemPosition(MotionEvent paramMotionEvent) {
    View view = findChildViewUnder(paramMotionEvent.getX(), paramMotionEvent.getY());
    if (view == null)
      return -1; 
    if (view.getTag() != null && view.getTag() instanceof RecyclerView.ViewHolder)
      return ((RecyclerView.ViewHolder)view.getTag()).getAdapterPosition(); 
    throw new IllegalStateException("Make sure your adapter makes a call to super.onBindViewHolder(), and doesn't override itemView tags.");
  }
  
  private void init(Context paramContext, AttributeSet paramAttributeSet) {
    this.mAutoScrollHandler = new Handler();
    int i = paramContext.getResources().getDimensionPixelSize(2131165377);
    if (paramAttributeSet != null) {
      TypedArray typedArray = paramContext.getTheme().obtainStyledAttributes(paramAttributeSet, R.styleable.DragSelectRecyclerView, 0, 0);
      try {
        if (!typedArray.getBoolean(0, true)) {
          this.mHotspotHeight = -1;
          this.mHotspotOffsetTop = -1;
          this.mHotspotOffsetBottom = -1;
          LOG("Auto-scroll disabled", new Object[0]);
        } else {
          this.mHotspotHeight = typedArray.getDimensionPixelSize(1, i);
          this.mHotspotOffsetTop = typedArray.getDimensionPixelSize(3, 0);
          this.mHotspotOffsetBottom = typedArray.getDimensionPixelSize(2, 0);
          LOG("Hotspot height = %d", new Object[] { Integer.valueOf(this.mHotspotHeight) });
        } 
      } finally {
        typedArray.recycle();
      } 
    } else {
      this.mHotspotHeight = i;
      LOG("Hotspot height = %d", new Object[] { Integer.valueOf(i) });
    } 
  }
  
  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent) {
    FingerListener fingerListener;
    if (this.mAdapter.getItemCount() == 0)
      return super.dispatchTouchEvent(paramMotionEvent); 
    if (this.mDragSelectActive) {
      if (paramMotionEvent.getAction() == 1) {
        this.mDragSelectActive = false;
        this.mInTopHotspot = false;
        this.mInBottomHotspot = false;
        this.mAutoScrollHandler.removeCallbacks(this.mAutoScrollRunnable);
        fingerListener = this.mFingerListener;
        if (fingerListener != null)
          fingerListener.onDragSelectFingerAction(false); 
        return true;
      } 
      if (fingerListener.getAction() == 2) {
        if (this.mHotspotHeight > -1)
          if (fingerListener.getY() >= this.mHotspotTopBoundStart && fingerListener.getY() <= this.mHotspotTopBoundEnd) {
            this.mInBottomHotspot = false;
            if (!this.mInTopHotspot) {
              this.mInTopHotspot = true;
              LOG("Now in TOP hotspot", new Object[0]);
              this.mAutoScrollHandler.removeCallbacks(this.mAutoScrollRunnable);
              this.mAutoScrollHandler.postDelayed(this.mAutoScrollRunnable, 25L);
            } 
            int i = (int)((this.mHotspotTopBoundEnd - this.mHotspotTopBoundStart) - fingerListener.getY() - this.mHotspotTopBoundStart) / 2;
            this.mAutoScrollVelocity = i;
            LOG("Auto scroll velocity = %d", new Object[] { Integer.valueOf(i) });
          } else if (fingerListener.getY() >= this.mHotspotBottomBoundStart && fingerListener.getY() <= this.mHotspotBottomBoundEnd) {
            this.mInTopHotspot = false;
            if (!this.mInBottomHotspot) {
              this.mInBottomHotspot = true;
              LOG("Now in BOTTOM hotspot", new Object[0]);
              this.mAutoScrollHandler.removeCallbacks(this.mAutoScrollRunnable);
              this.mAutoScrollHandler.postDelayed(this.mAutoScrollRunnable, 25L);
            } 
            float f = fingerListener.getY();
            int i = this.mHotspotBottomBoundEnd;
            i = (int)(f + i - (this.mHotspotBottomBoundStart + i)) / 2;
            this.mAutoScrollVelocity = i;
            LOG("Auto scroll velocity = %d", new Object[] { Integer.valueOf(i) });
          } else if (this.mInTopHotspot || this.mInBottomHotspot) {
            LOG("Left the hotspot", new Object[0]);
            this.mAutoScrollHandler.removeCallbacks(this.mAutoScrollRunnable);
            this.mInTopHotspot = false;
            this.mInBottomHotspot = false;
          }  
        return true;
      } 
    } 
    return super.dispatchTouchEvent((MotionEvent)fingerListener);
  }
  
  public final void enableDebug() {
    this.mDebugEnabled = true;
    invalidate();
  }
  
  public void onDraw(Canvas paramCanvas) {
    super.onDraw(paramCanvas);
    if (this.mDebugEnabled) {
      if (this.mDebugPaint == null) {
        Paint paint = new Paint();
        this.mDebugPaint = paint;
        paint.setColor(-16777216);
        this.mDebugPaint.setAntiAlias(true);
        this.mDebugPaint.setStyle(Paint.Style.FILL);
        this.mTopBoundRect = new RectF(0.0F, this.mHotspotTopBoundStart, getMeasuredWidth(), this.mHotspotTopBoundEnd);
        this.mBottomBoundRect = new RectF(0.0F, this.mHotspotBottomBoundStart, getMeasuredWidth(), this.mHotspotBottomBoundEnd);
      } 
      paramCanvas.drawRect(this.mTopBoundRect, this.mDebugPaint);
      paramCanvas.drawRect(this.mBottomBoundRect, this.mDebugPaint);
    } 
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    super.onMeasure(paramInt1, paramInt2);
    paramInt1 = this.mHotspotHeight;
    if (paramInt1 > -1) {
      paramInt2 = this.mHotspotOffsetTop;
      this.mHotspotTopBoundStart = paramInt2;
      this.mHotspotTopBoundEnd = paramInt2 + paramInt1;
      this.mHotspotBottomBoundStart = getMeasuredHeight() - this.mHotspotHeight - this.mHotspotOffsetBottom;
      this.mHotspotBottomBoundEnd = getMeasuredHeight() - this.mHotspotOffsetBottom;
      LOG("RecyclerView height = %d", new Object[] { Integer.valueOf(getMeasuredHeight()) });
      LOG("Hotspot top bound = %d to %d", new Object[] { Integer.valueOf(this.mHotspotTopBoundStart), Integer.valueOf(this.mHotspotTopBoundStart) });
      LOG("Hotspot bottom bound = %d to %d", new Object[] { Integer.valueOf(this.mHotspotBottomBoundStart), Integer.valueOf(this.mHotspotBottomBoundEnd) });
    } 
  }
  
  @Deprecated
  public void setAdapter(RecyclerView.Adapter paramAdapter) {
    if (paramAdapter instanceof DragSelectRecyclerViewAdapter) {
      setAdapter((DragSelectRecyclerViewAdapter)paramAdapter);
      return;
    } 
    throw new IllegalArgumentException("Adapter must be a DragSelectRecyclerViewAdapter.");
  }
  
  public void setAdapter(DragSelectRecyclerViewAdapter<?> paramDragSelectRecyclerViewAdapter) {
    super.setAdapter(paramDragSelectRecyclerViewAdapter);
    this.mAdapter = paramDragSelectRecyclerViewAdapter;
  }
  
  public boolean setDragSelectActive(boolean paramBoolean, int paramInt) {
    if (paramBoolean && this.mDragSelectActive) {
      LOG("Drag selection is already active.", new Object[0]);
      return false;
    } 
    this.mLastDraggedIndex = -1;
    this.mMinReached = -1;
    this.mMaxReached = -1;
    if (!this.mAdapter.isIndexSelectable(paramInt)) {
      this.mDragSelectActive = false;
      this.mInitialSelection = -1;
      this.mLastDraggedIndex = -1;
      LOG("Index %d is not selectable.", new Object[] { Integer.valueOf(paramInt) });
      return false;
    } 
    this.mAdapter.setSelected(paramInt, true);
    this.mDragSelectActive = paramBoolean;
    this.mInitialSelection = paramInt;
    this.mLastDraggedIndex = paramInt;
    FingerListener fingerListener = this.mFingerListener;
    if (fingerListener != null)
      fingerListener.onDragSelectFingerAction(true); 
    LOG("Drag selection initialized, starting at index %d.", new Object[] { Integer.valueOf(paramInt) });
    return true;
  }
  
  public void setFingerListener(FingerListener paramFingerListener) {
    this.mFingerListener = paramFingerListener;
  }
  
  public static interface FingerListener {
    void onDragSelectFingerAction(boolean param1Boolean);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\DragSelectRecyclerView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */