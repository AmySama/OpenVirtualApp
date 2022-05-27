package android.support.design.internal;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.support.design.R;
import android.support.transition.AutoTransition;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v4.util.Pools;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuView;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class BottomNavigationMenuView extends ViewGroup implements MenuView {
  private static final long ACTIVE_ANIMATION_DURATION_MS = 115L;
  
  private final int mActiveItemMaxWidth;
  
  private BottomNavigationItemView[] mButtons;
  
  private final int mInactiveItemMaxWidth;
  
  private final int mInactiveItemMinWidth;
  
  private int mItemBackgroundRes;
  
  private final int mItemHeight;
  
  private ColorStateList mItemIconTint;
  
  private final Pools.Pool<BottomNavigationItemView> mItemPool = (Pools.Pool<BottomNavigationItemView>)new Pools.SynchronizedPool(5);
  
  private ColorStateList mItemTextColor;
  
  private MenuBuilder mMenu;
  
  private final View.OnClickListener mOnClickListener;
  
  private BottomNavigationPresenter mPresenter;
  
  private int mSelectedItemId = 0;
  
  private int mSelectedItemPosition = 0;
  
  private final TransitionSet mSet;
  
  private boolean mShiftingMode = true;
  
  private int[] mTempChildWidths;
  
  public BottomNavigationMenuView(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public BottomNavigationMenuView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    Resources resources = getResources();
    this.mInactiveItemMaxWidth = resources.getDimensionPixelSize(R.dimen.design_bottom_navigation_item_max_width);
    this.mInactiveItemMinWidth = resources.getDimensionPixelSize(R.dimen.design_bottom_navigation_item_min_width);
    this.mActiveItemMaxWidth = resources.getDimensionPixelSize(R.dimen.design_bottom_navigation_active_item_max_width);
    this.mItemHeight = resources.getDimensionPixelSize(R.dimen.design_bottom_navigation_height);
    AutoTransition autoTransition = new AutoTransition();
    this.mSet = (TransitionSet)autoTransition;
    autoTransition.setOrdering(0);
    this.mSet.setDuration(115L);
    this.mSet.setInterpolator((TimeInterpolator)new FastOutSlowInInterpolator());
    this.mSet.addTransition(new TextScale());
    this.mOnClickListener = new View.OnClickListener() {
        public void onClick(View param1View) {
          MenuItemImpl menuItemImpl = ((BottomNavigationItemView)param1View).getItemData();
          if (!BottomNavigationMenuView.this.mMenu.performItemAction((MenuItem)menuItemImpl, BottomNavigationMenuView.this.mPresenter, 0))
            menuItemImpl.setChecked(true); 
        }
      };
    this.mTempChildWidths = new int[5];
  }
  
  private BottomNavigationItemView getNewItem() {
    BottomNavigationItemView bottomNavigationItemView1 = (BottomNavigationItemView)this.mItemPool.acquire();
    BottomNavigationItemView bottomNavigationItemView2 = bottomNavigationItemView1;
    if (bottomNavigationItemView1 == null)
      bottomNavigationItemView2 = new BottomNavigationItemView(getContext()); 
    return bottomNavigationItemView2;
  }
  
  public void buildMenuView() {
    boolean bool;
    removeAllViews();
    BottomNavigationItemView[] arrayOfBottomNavigationItemView = this.mButtons;
    if (arrayOfBottomNavigationItemView != null) {
      int j = arrayOfBottomNavigationItemView.length;
      for (byte b = 0; b < j; b++) {
        BottomNavigationItemView bottomNavigationItemView = arrayOfBottomNavigationItemView[b];
        this.mItemPool.release(bottomNavigationItemView);
      } 
    } 
    if (this.mMenu.size() == 0) {
      this.mSelectedItemId = 0;
      this.mSelectedItemPosition = 0;
      this.mButtons = null;
      return;
    } 
    this.mButtons = new BottomNavigationItemView[this.mMenu.size()];
    if (this.mMenu.size() > 3) {
      bool = true;
    } else {
      bool = false;
    } 
    this.mShiftingMode = bool;
    int i;
    for (i = 0; i < this.mMenu.size(); i++) {
      this.mPresenter.setUpdateSuspended(true);
      this.mMenu.getItem(i).setCheckable(true);
      this.mPresenter.setUpdateSuspended(false);
      BottomNavigationItemView bottomNavigationItemView = getNewItem();
      this.mButtons[i] = bottomNavigationItemView;
      bottomNavigationItemView.setIconTintList(this.mItemIconTint);
      bottomNavigationItemView.setTextColor(this.mItemTextColor);
      bottomNavigationItemView.setItemBackground(this.mItemBackgroundRes);
      bottomNavigationItemView.setShiftingMode(this.mShiftingMode);
      bottomNavigationItemView.initialize((MenuItemImpl)this.mMenu.getItem(i), 0);
      bottomNavigationItemView.setItemPosition(i);
      bottomNavigationItemView.setOnClickListener(this.mOnClickListener);
      addView((View)bottomNavigationItemView);
    } 
    i = Math.min(this.mMenu.size() - 1, this.mSelectedItemPosition);
    this.mSelectedItemPosition = i;
    this.mMenu.getItem(i).setChecked(true);
  }
  
  public ColorStateList getIconTintList() {
    return this.mItemIconTint;
  }
  
  public int getItemBackgroundRes() {
    return this.mItemBackgroundRes;
  }
  
  public ColorStateList getItemTextColor() {
    return this.mItemTextColor;
  }
  
  public int getSelectedItemId() {
    return this.mSelectedItemId;
  }
  
  public int getWindowAnimations() {
    return 0;
  }
  
  public void initialize(MenuBuilder paramMenuBuilder) {
    this.mMenu = paramMenuBuilder;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int i = getChildCount();
    int j = paramInt4 - paramInt2;
    paramInt2 = 0;
    paramInt4 = 0;
    while (paramInt2 < i) {
      View view = getChildAt(paramInt2);
      if (view.getVisibility() != 8) {
        if (ViewCompat.getLayoutDirection((View)this) == 1) {
          int k = paramInt3 - paramInt1 - paramInt4;
          view.layout(k - view.getMeasuredWidth(), 0, k, j);
        } else {
          view.layout(paramInt4, 0, view.getMeasuredWidth() + paramInt4, j);
        } 
        paramInt4 += view.getMeasuredWidth();
      } 
      paramInt2++;
    } 
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    paramInt2 = View.MeasureSpec.getSize(paramInt1);
    int i = getChildCount();
    int j = View.MeasureSpec.makeMeasureSpec(this.mItemHeight, 1073741824);
    if (this.mShiftingMode) {
      paramInt1 = i - 1;
      int k = Math.min(paramInt2 - this.mInactiveItemMinWidth * paramInt1, this.mActiveItemMaxWidth);
      paramInt2 -= k;
      int m = Math.min(paramInt2 / paramInt1, this.mInactiveItemMaxWidth);
      paramInt1 = paramInt2 - paramInt1 * m;
      paramInt2 = 0;
      while (paramInt2 < i) {
        int[] arrayOfInt = this.mTempChildWidths;
        if (paramInt2 == this.mSelectedItemPosition) {
          n = k;
        } else {
          n = m;
        } 
        arrayOfInt[paramInt2] = n;
        int n = paramInt1;
        if (paramInt1 > 0) {
          arrayOfInt = this.mTempChildWidths;
          arrayOfInt[paramInt2] = arrayOfInt[paramInt2] + 1;
          n = paramInt1 - 1;
        } 
        paramInt2++;
        paramInt1 = n;
      } 
    } else {
      if (i == 0) {
        paramInt1 = 1;
      } else {
        paramInt1 = i;
      } 
      int k = Math.min(paramInt2 / paramInt1, this.mActiveItemMaxWidth);
      paramInt1 = paramInt2 - k * i;
      byte b = 0;
      while (b < i) {
        int[] arrayOfInt = this.mTempChildWidths;
        arrayOfInt[b] = k;
        paramInt2 = paramInt1;
        if (paramInt1 > 0) {
          arrayOfInt[b] = arrayOfInt[b] + 1;
          paramInt2 = paramInt1 - 1;
        } 
        b++;
        paramInt1 = paramInt2;
      } 
    } 
    paramInt1 = 0;
    paramInt2 = 0;
    while (paramInt1 < i) {
      View view = getChildAt(paramInt1);
      if (view.getVisibility() != 8) {
        view.measure(View.MeasureSpec.makeMeasureSpec(this.mTempChildWidths[paramInt1], 1073741824), j);
        (view.getLayoutParams()).width = view.getMeasuredWidth();
        paramInt2 += view.getMeasuredWidth();
      } 
      paramInt1++;
    } 
    setMeasuredDimension(View.resolveSizeAndState(paramInt2, View.MeasureSpec.makeMeasureSpec(paramInt2, 1073741824), 0), View.resolveSizeAndState(this.mItemHeight, j, 0));
  }
  
  public void setIconTintList(ColorStateList paramColorStateList) {
    this.mItemIconTint = paramColorStateList;
    BottomNavigationItemView[] arrayOfBottomNavigationItemView = this.mButtons;
    if (arrayOfBottomNavigationItemView == null)
      return; 
    int i = arrayOfBottomNavigationItemView.length;
    for (byte b = 0; b < i; b++)
      arrayOfBottomNavigationItemView[b].setIconTintList(paramColorStateList); 
  }
  
  public void setItemBackgroundRes(int paramInt) {
    this.mItemBackgroundRes = paramInt;
    BottomNavigationItemView[] arrayOfBottomNavigationItemView = this.mButtons;
    if (arrayOfBottomNavigationItemView == null)
      return; 
    int i = arrayOfBottomNavigationItemView.length;
    for (byte b = 0; b < i; b++)
      arrayOfBottomNavigationItemView[b].setItemBackground(paramInt); 
  }
  
  public void setItemTextColor(ColorStateList paramColorStateList) {
    this.mItemTextColor = paramColorStateList;
    BottomNavigationItemView[] arrayOfBottomNavigationItemView = this.mButtons;
    if (arrayOfBottomNavigationItemView == null)
      return; 
    int i = arrayOfBottomNavigationItemView.length;
    for (byte b = 0; b < i; b++)
      arrayOfBottomNavigationItemView[b].setTextColor(paramColorStateList); 
  }
  
  public void setPresenter(BottomNavigationPresenter paramBottomNavigationPresenter) {
    this.mPresenter = paramBottomNavigationPresenter;
  }
  
  void tryRestoreSelectedItemId(int paramInt) {
    int i = this.mMenu.size();
    for (byte b = 0; b < i; b++) {
      MenuItem menuItem = this.mMenu.getItem(b);
      if (paramInt == menuItem.getItemId()) {
        this.mSelectedItemId = paramInt;
        this.mSelectedItemPosition = b;
        menuItem.setChecked(true);
        break;
      } 
    } 
  }
  
  public void updateMenuView() {
    int i = this.mMenu.size();
    if (i != this.mButtons.length) {
      buildMenuView();
      return;
    } 
    int j = this.mSelectedItemId;
    byte b;
    for (b = 0; b < i; b++) {
      MenuItem menuItem = this.mMenu.getItem(b);
      if (menuItem.isChecked()) {
        this.mSelectedItemId = menuItem.getItemId();
        this.mSelectedItemPosition = b;
      } 
    } 
    if (j != this.mSelectedItemId)
      TransitionManager.beginDelayedTransition(this, (Transition)this.mSet); 
    for (b = 0; b < i; b++) {
      this.mPresenter.setUpdateSuspended(true);
      this.mButtons[b].initialize((MenuItemImpl)this.mMenu.getItem(b), 0);
      this.mPresenter.setUpdateSuspended(false);
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\internal\BottomNavigationMenuView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */