package android.support.design.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.design.R;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.TooltipCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.TextView;

public class NavigationMenuItemView extends ForegroundLinearLayout implements MenuView.ItemView {
  private static final int[] CHECKED_STATE_SET = new int[] { 16842912 };
  
  private final AccessibilityDelegateCompat mAccessibilityDelegate = new AccessibilityDelegateCompat() {
      public void onInitializeAccessibilityNodeInfo(View param1View, AccessibilityNodeInfoCompat param1AccessibilityNodeInfoCompat) {
        super.onInitializeAccessibilityNodeInfo(param1View, param1AccessibilityNodeInfoCompat);
        param1AccessibilityNodeInfoCompat.setCheckable(NavigationMenuItemView.this.mCheckable);
      }
    };
  
  private FrameLayout mActionArea;
  
  boolean mCheckable;
  
  private Drawable mEmptyDrawable;
  
  private boolean mHasIconTintList;
  
  private final int mIconSize;
  
  private ColorStateList mIconTintList;
  
  private MenuItemImpl mItemData;
  
  private boolean mNeedsEmptyIcon;
  
  private final CheckedTextView mTextView;
  
  public NavigationMenuItemView(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public NavigationMenuItemView(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public NavigationMenuItemView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    setOrientation(0);
    LayoutInflater.from(paramContext).inflate(R.layout.design_navigation_menu_item, (ViewGroup)this, true);
    this.mIconSize = paramContext.getResources().getDimensionPixelSize(R.dimen.design_navigation_icon_size);
    CheckedTextView checkedTextView = (CheckedTextView)findViewById(R.id.design_menu_item_text);
    this.mTextView = checkedTextView;
    checkedTextView.setDuplicateParentStateEnabled(true);
    ViewCompat.setAccessibilityDelegate((View)this.mTextView, this.mAccessibilityDelegate);
  }
  
  private void adjustAppearance() {
    if (shouldExpandActionArea()) {
      this.mTextView.setVisibility(8);
      FrameLayout frameLayout = this.mActionArea;
      if (frameLayout != null) {
        LinearLayoutCompat.LayoutParams layoutParams = (LinearLayoutCompat.LayoutParams)frameLayout.getLayoutParams();
        layoutParams.width = -1;
        this.mActionArea.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
      } 
    } else {
      this.mTextView.setVisibility(0);
      FrameLayout frameLayout = this.mActionArea;
      if (frameLayout != null) {
        LinearLayoutCompat.LayoutParams layoutParams = (LinearLayoutCompat.LayoutParams)frameLayout.getLayoutParams();
        layoutParams.width = -2;
        this.mActionArea.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
      } 
    } 
  }
  
  private StateListDrawable createDefaultBackground() {
    TypedValue typedValue = new TypedValue();
    if (getContext().getTheme().resolveAttribute(R.attr.colorControlHighlight, typedValue, true)) {
      StateListDrawable stateListDrawable = new StateListDrawable();
      stateListDrawable.addState(CHECKED_STATE_SET, (Drawable)new ColorDrawable(typedValue.data));
      stateListDrawable.addState(EMPTY_STATE_SET, (Drawable)new ColorDrawable(0));
      return stateListDrawable;
    } 
    return null;
  }
  
  private void setActionView(View paramView) {
    if (paramView != null) {
      if (this.mActionArea == null)
        this.mActionArea = (FrameLayout)((ViewStub)findViewById(R.id.design_menu_item_action_area_stub)).inflate(); 
      this.mActionArea.removeAllViews();
      this.mActionArea.addView(paramView);
    } 
  }
  
  private boolean shouldExpandActionArea() {
    boolean bool;
    if (this.mItemData.getTitle() == null && this.mItemData.getIcon() == null && this.mItemData.getActionView() != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public MenuItemImpl getItemData() {
    return this.mItemData;
  }
  
  public void initialize(MenuItemImpl paramMenuItemImpl, int paramInt) {
    this.mItemData = paramMenuItemImpl;
    if (paramMenuItemImpl.isVisible()) {
      paramInt = 0;
    } else {
      paramInt = 8;
    } 
    setVisibility(paramInt);
    if (getBackground() == null)
      ViewCompat.setBackground((View)this, (Drawable)createDefaultBackground()); 
    setCheckable(paramMenuItemImpl.isCheckable());
    setChecked(paramMenuItemImpl.isChecked());
    setEnabled(paramMenuItemImpl.isEnabled());
    setTitle(paramMenuItemImpl.getTitle());
    setIcon(paramMenuItemImpl.getIcon());
    setActionView(paramMenuItemImpl.getActionView());
    setContentDescription(paramMenuItemImpl.getContentDescription());
    TooltipCompat.setTooltipText((View)this, paramMenuItemImpl.getTooltipText());
    adjustAppearance();
  }
  
  protected int[] onCreateDrawableState(int paramInt) {
    int[] arrayOfInt = super.onCreateDrawableState(paramInt + 1);
    MenuItemImpl menuItemImpl = this.mItemData;
    if (menuItemImpl != null && menuItemImpl.isCheckable() && this.mItemData.isChecked())
      mergeDrawableStates(arrayOfInt, CHECKED_STATE_SET); 
    return arrayOfInt;
  }
  
  public boolean prefersCondensedTitle() {
    return false;
  }
  
  public void recycle() {
    FrameLayout frameLayout = this.mActionArea;
    if (frameLayout != null)
      frameLayout.removeAllViews(); 
    this.mTextView.setCompoundDrawables(null, null, null, null);
  }
  
  public void setCheckable(boolean paramBoolean) {
    refreshDrawableState();
    if (this.mCheckable != paramBoolean) {
      this.mCheckable = paramBoolean;
      this.mAccessibilityDelegate.sendAccessibilityEvent((View)this.mTextView, 2048);
    } 
  }
  
  public void setChecked(boolean paramBoolean) {
    refreshDrawableState();
    this.mTextView.setChecked(paramBoolean);
  }
  
  public void setIcon(Drawable paramDrawable) {
    if (paramDrawable != null) {
      Drawable drawable = paramDrawable;
      if (this.mHasIconTintList) {
        Drawable.ConstantState constantState = paramDrawable.getConstantState();
        if (constantState != null)
          paramDrawable = constantState.newDrawable(); 
        drawable = DrawableCompat.wrap(paramDrawable).mutate();
        DrawableCompat.setTintList(drawable, this.mIconTintList);
      } 
      int i = this.mIconSize;
      drawable.setBounds(0, 0, i, i);
      paramDrawable = drawable;
    } else if (this.mNeedsEmptyIcon) {
      if (this.mEmptyDrawable == null) {
        paramDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.navigation_empty_icon, getContext().getTheme());
        this.mEmptyDrawable = paramDrawable;
        if (paramDrawable != null) {
          int i = this.mIconSize;
          paramDrawable.setBounds(0, 0, i, i);
        } 
      } 
      paramDrawable = this.mEmptyDrawable;
    } 
    TextViewCompat.setCompoundDrawablesRelative((TextView)this.mTextView, paramDrawable, null, null, null);
  }
  
  void setIconTintList(ColorStateList paramColorStateList) {
    boolean bool;
    this.mIconTintList = paramColorStateList;
    if (paramColorStateList != null) {
      bool = true;
    } else {
      bool = false;
    } 
    this.mHasIconTintList = bool;
    MenuItemImpl menuItemImpl = this.mItemData;
    if (menuItemImpl != null)
      setIcon(menuItemImpl.getIcon()); 
  }
  
  public void setNeedsEmptyIcon(boolean paramBoolean) {
    this.mNeedsEmptyIcon = paramBoolean;
  }
  
  public void setShortcut(boolean paramBoolean, char paramChar) {}
  
  public void setTextAppearance(int paramInt) {
    TextViewCompat.setTextAppearance((TextView)this.mTextView, paramInt);
  }
  
  public void setTextColor(ColorStateList paramColorStateList) {
    this.mTextView.setTextColor(paramColorStateList);
  }
  
  public void setTitle(CharSequence paramCharSequence) {
    this.mTextView.setText(paramCharSequence);
  }
  
  public boolean showsIcon() {
    return true;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\internal\NavigationMenuItemView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */