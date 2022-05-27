package android.support.v7.widget;

import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityManager;

class TooltipCompatHandler implements View.OnLongClickListener, View.OnHoverListener, View.OnAttachStateChangeListener {
  private static final long HOVER_HIDE_TIMEOUT_MS = 15000L;
  
  private static final long HOVER_HIDE_TIMEOUT_SHORT_MS = 3000L;
  
  private static final long LONG_CLICK_HIDE_TIMEOUT_MS = 2500L;
  
  private static final String TAG = "TooltipCompatHandler";
  
  private static TooltipCompatHandler sActiveHandler;
  
  private static TooltipCompatHandler sPendingHandler;
  
  private final View mAnchor;
  
  private int mAnchorX;
  
  private int mAnchorY;
  
  private boolean mFromTouch;
  
  private final Runnable mHideRunnable = new Runnable() {
      public void run() {
        TooltipCompatHandler.this.hide();
      }
    };
  
  private TooltipPopup mPopup;
  
  private final Runnable mShowRunnable = new Runnable() {
      public void run() {
        TooltipCompatHandler.this.show(false);
      }
    };
  
  private final CharSequence mTooltipText;
  
  private TooltipCompatHandler(View paramView, CharSequence paramCharSequence) {
    this.mAnchor = paramView;
    this.mTooltipText = paramCharSequence;
    paramView.setOnLongClickListener(this);
    this.mAnchor.setOnHoverListener(this);
  }
  
  private void cancelPendingShow() {
    this.mAnchor.removeCallbacks(this.mShowRunnable);
  }
  
  private void hide() {
    if (sActiveHandler == this) {
      sActiveHandler = null;
      TooltipPopup tooltipPopup = this.mPopup;
      if (tooltipPopup != null) {
        tooltipPopup.hide();
        this.mPopup = null;
        this.mAnchor.removeOnAttachStateChangeListener(this);
      } else {
        Log.e("TooltipCompatHandler", "sActiveHandler.mPopup == null");
      } 
    } 
    if (sPendingHandler == this)
      setPendingHandler(null); 
    this.mAnchor.removeCallbacks(this.mHideRunnable);
  }
  
  private void scheduleShow() {
    this.mAnchor.postDelayed(this.mShowRunnable, ViewConfiguration.getLongPressTimeout());
  }
  
  private static void setPendingHandler(TooltipCompatHandler paramTooltipCompatHandler) {
    TooltipCompatHandler tooltipCompatHandler = sPendingHandler;
    if (tooltipCompatHandler != null)
      tooltipCompatHandler.cancelPendingShow(); 
    sPendingHandler = paramTooltipCompatHandler;
    if (paramTooltipCompatHandler != null)
      paramTooltipCompatHandler.scheduleShow(); 
  }
  
  public static void setTooltipText(View paramView, CharSequence paramCharSequence) {
    TooltipCompatHandler tooltipCompatHandler1;
    TooltipCompatHandler tooltipCompatHandler2 = sPendingHandler;
    if (tooltipCompatHandler2 != null && tooltipCompatHandler2.mAnchor == paramView)
      setPendingHandler(null); 
    if (TextUtils.isEmpty(paramCharSequence)) {
      tooltipCompatHandler1 = sActiveHandler;
      if (tooltipCompatHandler1 != null && tooltipCompatHandler1.mAnchor == paramView)
        tooltipCompatHandler1.hide(); 
      paramView.setOnLongClickListener(null);
      paramView.setLongClickable(false);
      paramView.setOnHoverListener(null);
    } else {
      new TooltipCompatHandler(paramView, (CharSequence)tooltipCompatHandler1);
    } 
  }
  
  private void show(boolean paramBoolean) {
    long l;
    if (!ViewCompat.isAttachedToWindow(this.mAnchor))
      return; 
    setPendingHandler(null);
    TooltipCompatHandler tooltipCompatHandler = sActiveHandler;
    if (tooltipCompatHandler != null)
      tooltipCompatHandler.hide(); 
    sActiveHandler = this;
    this.mFromTouch = paramBoolean;
    TooltipPopup tooltipPopup = new TooltipPopup(this.mAnchor.getContext());
    this.mPopup = tooltipPopup;
    tooltipPopup.show(this.mAnchor, this.mAnchorX, this.mAnchorY, this.mFromTouch, this.mTooltipText);
    this.mAnchor.addOnAttachStateChangeListener(this);
    if (this.mFromTouch) {
      l = 2500L;
    } else {
      int i;
      if ((ViewCompat.getWindowSystemUiVisibility(this.mAnchor) & 0x1) == 1) {
        l = 3000L;
        i = ViewConfiguration.getLongPressTimeout();
      } else {
        l = 15000L;
        i = ViewConfiguration.getLongPressTimeout();
      } 
      l -= i;
    } 
    this.mAnchor.removeCallbacks(this.mHideRunnable);
    this.mAnchor.postDelayed(this.mHideRunnable, l);
  }
  
  public boolean onHover(View paramView, MotionEvent paramMotionEvent) {
    if (this.mPopup != null && this.mFromTouch)
      return false; 
    AccessibilityManager accessibilityManager = (AccessibilityManager)this.mAnchor.getContext().getSystemService("accessibility");
    if (accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled())
      return false; 
    int i = paramMotionEvent.getAction();
    if (i != 7) {
      if (i == 10)
        hide(); 
    } else if (this.mAnchor.isEnabled() && this.mPopup == null) {
      this.mAnchorX = (int)paramMotionEvent.getX();
      this.mAnchorY = (int)paramMotionEvent.getY();
      setPendingHandler(this);
    } 
    return false;
  }
  
  public boolean onLongClick(View paramView) {
    this.mAnchorX = paramView.getWidth() / 2;
    this.mAnchorY = paramView.getHeight() / 2;
    show(true);
    return true;
  }
  
  public void onViewAttachedToWindow(View paramView) {}
  
  public void onViewDetachedFromWindow(View paramView) {
    hide();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\TooltipCompatHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */