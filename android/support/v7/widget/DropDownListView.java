package android.support.v7.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v7.appcompat.R;
import android.support.v7.graphics.drawable.DrawableWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.lang.reflect.Field;

class DropDownListView extends ListView {
  public static final int INVALID_POSITION = -1;
  
  public static final int NO_POSITION = -1;
  
  private ViewPropertyAnimatorCompat mClickAnimation;
  
  private boolean mDrawsInPressedState;
  
  private boolean mHijackFocus;
  
  private Field mIsChildViewEnabled;
  
  private boolean mListSelectionHidden;
  
  private int mMotionPosition;
  
  private ResolveHoverRunnable mResolveHoverRunnable;
  
  private ListViewAutoScrollHelper mScrollHelper;
  
  private int mSelectionBottomPadding = 0;
  
  private int mSelectionLeftPadding = 0;
  
  private int mSelectionRightPadding = 0;
  
  private int mSelectionTopPadding = 0;
  
  private GateKeeperDrawable mSelector;
  
  private final Rect mSelectorRect = new Rect();
  
  DropDownListView(Context paramContext, boolean paramBoolean) {
    super(paramContext, null, R.attr.dropDownListViewStyle);
    this.mHijackFocus = paramBoolean;
    setCacheColorHint(0);
    try {
      Field field = AbsListView.class.getDeclaredField("mIsChildViewEnabled");
      this.mIsChildViewEnabled = field;
      field.setAccessible(true);
    } catch (NoSuchFieldException noSuchFieldException) {
      noSuchFieldException.printStackTrace();
    } 
  }
  
  private void clearPressedItem() {
    this.mDrawsInPressedState = false;
    setPressed(false);
    drawableStateChanged();
    View view = getChildAt(this.mMotionPosition - getFirstVisiblePosition());
    if (view != null)
      view.setPressed(false); 
    ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = this.mClickAnimation;
    if (viewPropertyAnimatorCompat != null) {
      viewPropertyAnimatorCompat.cancel();
      this.mClickAnimation = null;
    } 
  }
  
  private void clickPressedItem(View paramView, int paramInt) {
    performItemClick(paramView, paramInt, getItemIdAtPosition(paramInt));
  }
  
  private void drawSelectorCompat(Canvas paramCanvas) {
    if (!this.mSelectorRect.isEmpty()) {
      Drawable drawable = getSelector();
      if (drawable != null) {
        drawable.setBounds(this.mSelectorRect);
        drawable.draw(paramCanvas);
      } 
    } 
  }
  
  private void positionSelectorCompat(int paramInt, View paramView) {
    Rect rect = this.mSelectorRect;
    rect.set(paramView.getLeft(), paramView.getTop(), paramView.getRight(), paramView.getBottom());
    rect.left -= this.mSelectionLeftPadding;
    rect.top -= this.mSelectionTopPadding;
    rect.right += this.mSelectionRightPadding;
    rect.bottom += this.mSelectionBottomPadding;
    try {
      boolean bool = this.mIsChildViewEnabled.getBoolean(this);
      if (paramView.isEnabled() != bool) {
        Field field = this.mIsChildViewEnabled;
        if (!bool) {
          bool = true;
        } else {
          bool = false;
        } 
        field.set(this, Boolean.valueOf(bool));
        if (paramInt != -1)
          refreshDrawableState(); 
      } 
    } catch (IllegalAccessException illegalAccessException) {
      illegalAccessException.printStackTrace();
    } 
  }
  
  private void positionSelectorLikeFocusCompat(int paramInt, View paramView) {
    boolean bool2;
    Drawable drawable = getSelector();
    boolean bool1 = true;
    if (drawable != null && paramInt != -1) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    if (bool2)
      drawable.setVisible(false, false); 
    positionSelectorCompat(paramInt, paramView);
    if (bool2) {
      Rect rect = this.mSelectorRect;
      float f1 = rect.exactCenterX();
      float f2 = rect.exactCenterY();
      if (getVisibility() != 0)
        bool1 = false; 
      drawable.setVisible(bool1, false);
      DrawableCompat.setHotspot(drawable, f1, f2);
    } 
  }
  
  private void positionSelectorLikeTouchCompat(int paramInt, View paramView, float paramFloat1, float paramFloat2) {
    positionSelectorLikeFocusCompat(paramInt, paramView);
    Drawable drawable = getSelector();
    if (drawable != null && paramInt != -1)
      DrawableCompat.setHotspot(drawable, paramFloat1, paramFloat2); 
  }
  
  private void setPressedItem(View paramView, int paramInt, float paramFloat1, float paramFloat2) {
    this.mDrawsInPressedState = true;
    if (Build.VERSION.SDK_INT >= 21)
      drawableHotspotChanged(paramFloat1, paramFloat2); 
    if (!isPressed())
      setPressed(true); 
    layoutChildren();
    int i = this.mMotionPosition;
    if (i != -1) {
      View view = getChildAt(i - getFirstVisiblePosition());
      if (view != null && view != paramView && view.isPressed())
        view.setPressed(false); 
    } 
    this.mMotionPosition = paramInt;
    float f1 = paramView.getLeft();
    float f2 = paramView.getTop();
    if (Build.VERSION.SDK_INT >= 21)
      paramView.drawableHotspotChanged(paramFloat1 - f1, paramFloat2 - f2); 
    if (!paramView.isPressed())
      paramView.setPressed(true); 
    positionSelectorLikeTouchCompat(paramInt, paramView, paramFloat1, paramFloat2);
    setSelectorEnabled(false);
    refreshDrawableState();
  }
  
  private void setSelectorEnabled(boolean paramBoolean) {
    GateKeeperDrawable gateKeeperDrawable = this.mSelector;
    if (gateKeeperDrawable != null)
      gateKeeperDrawable.setEnabled(paramBoolean); 
  }
  
  private boolean touchModeDrawsInPressedStateCompat() {
    return this.mDrawsInPressedState;
  }
  
  private void updateSelectorStateCompat() {
    Drawable drawable = getSelector();
    if (drawable != null && touchModeDrawsInPressedStateCompat() && isPressed())
      drawable.setState(getDrawableState()); 
  }
  
  protected void dispatchDraw(Canvas paramCanvas) {
    drawSelectorCompat(paramCanvas);
    super.dispatchDraw(paramCanvas);
  }
  
  protected void drawableStateChanged() {
    if (this.mResolveHoverRunnable != null)
      return; 
    super.drawableStateChanged();
    setSelectorEnabled(true);
    updateSelectorStateCompat();
  }
  
  public boolean hasFocus() {
    return (this.mHijackFocus || super.hasFocus());
  }
  
  public boolean hasWindowFocus() {
    return (this.mHijackFocus || super.hasWindowFocus());
  }
  
  public boolean isFocused() {
    return (this.mHijackFocus || super.isFocused());
  }
  
  public boolean isInTouchMode() {
    boolean bool;
    if ((this.mHijackFocus && this.mListSelectionHidden) || super.isInTouchMode()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public int lookForSelectablePosition(int paramInt, boolean paramBoolean) {
    ListAdapter listAdapter = getAdapter();
    if (listAdapter != null && !isInTouchMode()) {
      int i = listAdapter.getCount();
      if (!getAdapter().areAllItemsEnabled()) {
        int j;
        if (paramBoolean) {
          paramInt = Math.max(0, paramInt);
          while (true) {
            j = paramInt;
            if (paramInt < i) {
              j = paramInt;
              if (!listAdapter.isEnabled(paramInt)) {
                paramInt++;
                continue;
              } 
            } 
            break;
          } 
        } else {
          paramInt = Math.min(paramInt, i - 1);
          while (true) {
            j = paramInt;
            if (paramInt >= 0) {
              j = paramInt;
              if (!listAdapter.isEnabled(paramInt)) {
                paramInt--;
                continue;
              } 
            } 
            break;
          } 
        } 
        return (j < 0 || j >= i) ? -1 : j;
      } 
      if (paramInt >= 0 && paramInt < i)
        return paramInt; 
    } 
    return -1;
  }
  
  public int measureHeightOfChildrenCompat(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    paramInt3 = getListPaddingTop();
    paramInt2 = getListPaddingBottom();
    getListPaddingLeft();
    getListPaddingRight();
    int i = getDividerHeight();
    Drawable drawable = getDivider();
    ListAdapter listAdapter = getAdapter();
    if (listAdapter == null)
      return paramInt3 + paramInt2; 
    paramInt3 += paramInt2;
    if (i <= 0 || drawable == null)
      i = 0; 
    int j = listAdapter.getCount();
    drawable = null;
    byte b = 0;
    int k = 0;
    for (paramInt2 = 0; b < j; paramInt2 = m) {
      int m = listAdapter.getItemViewType(b);
      int n = k;
      if (m != k) {
        drawable = null;
        n = m;
      } 
      View view2 = listAdapter.getView(b, (View)drawable, (ViewGroup)this);
      ViewGroup.LayoutParams layoutParams2 = view2.getLayoutParams();
      ViewGroup.LayoutParams layoutParams1 = layoutParams2;
      if (layoutParams2 == null) {
        layoutParams1 = generateDefaultLayoutParams();
        view2.setLayoutParams(layoutParams1);
      } 
      if (layoutParams1.height > 0) {
        k = View.MeasureSpec.makeMeasureSpec(layoutParams1.height, 1073741824);
      } else {
        k = View.MeasureSpec.makeMeasureSpec(0, 0);
      } 
      view2.measure(paramInt1, k);
      view2.forceLayout();
      k = paramInt3;
      if (b > 0)
        k = paramInt3 + i; 
      paramInt3 = k + view2.getMeasuredHeight();
      if (paramInt3 >= paramInt4) {
        paramInt1 = paramInt4;
        if (paramInt5 >= 0) {
          paramInt1 = paramInt4;
          if (b > paramInt5) {
            paramInt1 = paramInt4;
            if (paramInt2 > 0) {
              paramInt1 = paramInt4;
              if (paramInt3 != paramInt4)
                paramInt1 = paramInt2; 
            } 
          } 
        } 
        return paramInt1;
      } 
      m = paramInt2;
      if (paramInt5 >= 0) {
        m = paramInt2;
        if (b >= paramInt5)
          m = paramInt3; 
      } 
      b++;
      k = n;
      View view1 = view2;
    } 
    return paramInt3;
  }
  
  protected void onDetachedFromWindow() {
    this.mResolveHoverRunnable = null;
    super.onDetachedFromWindow();
  }
  
  public boolean onForwardedEvent(MotionEvent paramMotionEvent, int paramInt) {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual getActionMasked : ()I
    //   4: istore_3
    //   5: iload_3
    //   6: iconst_1
    //   7: if_icmpeq -> 42
    //   10: iload_3
    //   11: iconst_2
    //   12: if_icmpeq -> 36
    //   15: iload_3
    //   16: iconst_3
    //   17: if_icmpeq -> 28
    //   20: iconst_0
    //   21: istore_2
    //   22: iconst_1
    //   23: istore #4
    //   25: goto -> 139
    //   28: iconst_0
    //   29: istore_2
    //   30: iconst_0
    //   31: istore #4
    //   33: goto -> 139
    //   36: iconst_1
    //   37: istore #4
    //   39: goto -> 45
    //   42: iconst_0
    //   43: istore #4
    //   45: aload_1
    //   46: iload_2
    //   47: invokevirtual findPointerIndex : (I)I
    //   50: istore #5
    //   52: iload #5
    //   54: ifge -> 60
    //   57: goto -> 28
    //   60: aload_1
    //   61: iload #5
    //   63: invokevirtual getX : (I)F
    //   66: f2i
    //   67: istore_2
    //   68: aload_1
    //   69: iload #5
    //   71: invokevirtual getY : (I)F
    //   74: f2i
    //   75: istore #6
    //   77: aload_0
    //   78: iload_2
    //   79: iload #6
    //   81: invokevirtual pointToPosition : (II)I
    //   84: istore #5
    //   86: iload #5
    //   88: iconst_m1
    //   89: if_icmpne -> 97
    //   92: iconst_1
    //   93: istore_2
    //   94: goto -> 139
    //   97: aload_0
    //   98: iload #5
    //   100: aload_0
    //   101: invokevirtual getFirstVisiblePosition : ()I
    //   104: isub
    //   105: invokevirtual getChildAt : (I)Landroid/view/View;
    //   108: astore #7
    //   110: aload_0
    //   111: aload #7
    //   113: iload #5
    //   115: iload_2
    //   116: i2f
    //   117: iload #6
    //   119: i2f
    //   120: invokespecial setPressedItem : (Landroid/view/View;IFF)V
    //   123: iload_3
    //   124: iconst_1
    //   125: if_icmpne -> 20
    //   128: aload_0
    //   129: aload #7
    //   131: iload #5
    //   133: invokespecial clickPressedItem : (Landroid/view/View;I)V
    //   136: goto -> 20
    //   139: iload #4
    //   141: ifeq -> 148
    //   144: iload_2
    //   145: ifeq -> 152
    //   148: aload_0
    //   149: invokespecial clearPressedItem : ()V
    //   152: iload #4
    //   154: ifeq -> 198
    //   157: aload_0
    //   158: getfield mScrollHelper : Landroid/support/v4/widget/ListViewAutoScrollHelper;
    //   161: ifnonnull -> 176
    //   164: aload_0
    //   165: new android/support/v4/widget/ListViewAutoScrollHelper
    //   168: dup
    //   169: aload_0
    //   170: invokespecial <init> : (Landroid/widget/ListView;)V
    //   173: putfield mScrollHelper : Landroid/support/v4/widget/ListViewAutoScrollHelper;
    //   176: aload_0
    //   177: getfield mScrollHelper : Landroid/support/v4/widget/ListViewAutoScrollHelper;
    //   180: iconst_1
    //   181: invokevirtual setEnabled : (Z)Landroid/support/v4/widget/AutoScrollHelper;
    //   184: pop
    //   185: aload_0
    //   186: getfield mScrollHelper : Landroid/support/v4/widget/ListViewAutoScrollHelper;
    //   189: aload_0
    //   190: aload_1
    //   191: invokevirtual onTouch : (Landroid/view/View;Landroid/view/MotionEvent;)Z
    //   194: pop
    //   195: goto -> 213
    //   198: aload_0
    //   199: getfield mScrollHelper : Landroid/support/v4/widget/ListViewAutoScrollHelper;
    //   202: astore_1
    //   203: aload_1
    //   204: ifnull -> 213
    //   207: aload_1
    //   208: iconst_0
    //   209: invokevirtual setEnabled : (Z)Landroid/support/v4/widget/AutoScrollHelper;
    //   212: pop
    //   213: iload #4
    //   215: ireturn
  }
  
  public boolean onHoverEvent(MotionEvent paramMotionEvent) {
    if (Build.VERSION.SDK_INT < 26)
      return super.onHoverEvent(paramMotionEvent); 
    int i = paramMotionEvent.getActionMasked();
    if (i == 10 && this.mResolveHoverRunnable == null) {
      ResolveHoverRunnable resolveHoverRunnable = new ResolveHoverRunnable();
      this.mResolveHoverRunnable = resolveHoverRunnable;
      resolveHoverRunnable.post();
    } 
    boolean bool = super.onHoverEvent(paramMotionEvent);
    if (i == 9 || i == 7) {
      i = pointToPosition((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY());
      if (i != -1 && i != getSelectedItemPosition()) {
        View view = getChildAt(i - getFirstVisiblePosition());
        if (view.isEnabled())
          setSelectionFromTop(i, view.getTop() - getTop()); 
        updateSelectorStateCompat();
      } 
      return bool;
    } 
    setSelection(-1);
    return bool;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    if (paramMotionEvent.getAction() == 0)
      this.mMotionPosition = pointToPosition((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY()); 
    ResolveHoverRunnable resolveHoverRunnable = this.mResolveHoverRunnable;
    if (resolveHoverRunnable != null)
      resolveHoverRunnable.cancel(); 
    return super.onTouchEvent(paramMotionEvent);
  }
  
  void setListSelectionHidden(boolean paramBoolean) {
    this.mListSelectionHidden = paramBoolean;
  }
  
  public void setSelector(Drawable paramDrawable) {
    GateKeeperDrawable gateKeeperDrawable;
    if (paramDrawable != null) {
      gateKeeperDrawable = new GateKeeperDrawable(paramDrawable);
    } else {
      gateKeeperDrawable = null;
    } 
    this.mSelector = gateKeeperDrawable;
    super.setSelector((Drawable)gateKeeperDrawable);
    Rect rect = new Rect();
    if (paramDrawable != null)
      paramDrawable.getPadding(rect); 
    this.mSelectionLeftPadding = rect.left;
    this.mSelectionTopPadding = rect.top;
    this.mSelectionRightPadding = rect.right;
    this.mSelectionBottomPadding = rect.bottom;
  }
  
  private static class GateKeeperDrawable extends DrawableWrapper {
    private boolean mEnabled = true;
    
    GateKeeperDrawable(Drawable param1Drawable) {
      super(param1Drawable);
    }
    
    public void draw(Canvas param1Canvas) {
      if (this.mEnabled)
        super.draw(param1Canvas); 
    }
    
    void setEnabled(boolean param1Boolean) {
      this.mEnabled = param1Boolean;
    }
    
    public void setHotspot(float param1Float1, float param1Float2) {
      if (this.mEnabled)
        super.setHotspot(param1Float1, param1Float2); 
    }
    
    public void setHotspotBounds(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      if (this.mEnabled)
        super.setHotspotBounds(param1Int1, param1Int2, param1Int3, param1Int4); 
    }
    
    public boolean setState(int[] param1ArrayOfint) {
      return this.mEnabled ? super.setState(param1ArrayOfint) : false;
    }
    
    public boolean setVisible(boolean param1Boolean1, boolean param1Boolean2) {
      return this.mEnabled ? super.setVisible(param1Boolean1, param1Boolean2) : false;
    }
  }
  
  private class ResolveHoverRunnable implements Runnable {
    private ResolveHoverRunnable() {}
    
    public void cancel() {
      DropDownListView.access$102(DropDownListView.this, (ResolveHoverRunnable)null);
      DropDownListView.this.removeCallbacks(this);
    }
    
    public void post() {
      DropDownListView.this.post(this);
    }
    
    public void run() {
      DropDownListView.access$102(DropDownListView.this, (ResolveHoverRunnable)null);
      DropDownListView.this.drawableStateChanged();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\DropDownListView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */