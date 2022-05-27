package android.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.R;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.internal.BottomNavigationPresenter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class BottomNavigationView extends FrameLayout {
  private static final int[] CHECKED_STATE_SET = new int[] { 16842912 };
  
  private static final int[] DISABLED_STATE_SET = new int[] { -16842910 };
  
  private static final int MENU_PRESENTER_ID = 1;
  
  private final MenuBuilder mMenu;
  
  private MenuInflater mMenuInflater;
  
  private final BottomNavigationMenuView mMenuView;
  
  private final BottomNavigationPresenter mPresenter = new BottomNavigationPresenter();
  
  private OnNavigationItemReselectedListener mReselectedListener;
  
  private OnNavigationItemSelectedListener mSelectedListener;
  
  public BottomNavigationView(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public BottomNavigationView(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public BottomNavigationView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    ThemeUtils.checkAppCompatTheme(paramContext);
    this.mMenu = (MenuBuilder)new BottomNavigationMenu(paramContext);
    this.mMenuView = new BottomNavigationMenuView(paramContext);
    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
    layoutParams.gravity = 17;
    this.mMenuView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    this.mPresenter.setBottomNavigationMenuView(this.mMenuView);
    this.mPresenter.setId(1);
    this.mMenuView.setPresenter(this.mPresenter);
    this.mMenu.addMenuPresenter((MenuPresenter)this.mPresenter);
    this.mPresenter.initForMenu(getContext(), this.mMenu);
    TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.BottomNavigationView, paramInt, R.style.Widget_Design_BottomNavigationView);
    if (tintTypedArray.hasValue(R.styleable.BottomNavigationView_itemIconTint)) {
      this.mMenuView.setIconTintList(tintTypedArray.getColorStateList(R.styleable.BottomNavigationView_itemIconTint));
    } else {
      this.mMenuView.setIconTintList(createDefaultColorStateList(16842808));
    } 
    if (tintTypedArray.hasValue(R.styleable.BottomNavigationView_itemTextColor)) {
      this.mMenuView.setItemTextColor(tintTypedArray.getColorStateList(R.styleable.BottomNavigationView_itemTextColor));
    } else {
      this.mMenuView.setItemTextColor(createDefaultColorStateList(16842808));
    } 
    if (tintTypedArray.hasValue(R.styleable.BottomNavigationView_elevation))
      ViewCompat.setElevation((View)this, tintTypedArray.getDimensionPixelSize(R.styleable.BottomNavigationView_elevation, 0)); 
    paramInt = tintTypedArray.getResourceId(R.styleable.BottomNavigationView_itemBackground, 0);
    this.mMenuView.setItemBackgroundRes(paramInt);
    if (tintTypedArray.hasValue(R.styleable.BottomNavigationView_menu))
      inflateMenu(tintTypedArray.getResourceId(R.styleable.BottomNavigationView_menu, 0)); 
    tintTypedArray.recycle();
    addView((View)this.mMenuView, (ViewGroup.LayoutParams)layoutParams);
    if (Build.VERSION.SDK_INT < 21)
      addCompatibilityTopDivider(paramContext); 
    this.mMenu.setCallback(new MenuBuilder.Callback() {
          public boolean onMenuItemSelected(MenuBuilder param1MenuBuilder, MenuItem param1MenuItem) {
            BottomNavigationView.OnNavigationItemReselectedListener onNavigationItemReselectedListener = BottomNavigationView.this.mReselectedListener;
            boolean bool = true;
            if (onNavigationItemReselectedListener != null && param1MenuItem.getItemId() == BottomNavigationView.this.getSelectedItemId()) {
              BottomNavigationView.this.mReselectedListener.onNavigationItemReselected(param1MenuItem);
              return true;
            } 
            if (BottomNavigationView.this.mSelectedListener == null || BottomNavigationView.this.mSelectedListener.onNavigationItemSelected(param1MenuItem))
              bool = false; 
            return bool;
          }
          
          public void onMenuModeChange(MenuBuilder param1MenuBuilder) {}
        });
  }
  
  private void addCompatibilityTopDivider(Context paramContext) {
    View view = new View(paramContext);
    view.setBackgroundColor(ContextCompat.getColor(paramContext, R.color.design_bottom_navigation_shadow_color));
    view.setLayoutParams((ViewGroup.LayoutParams)new FrameLayout.LayoutParams(-1, getResources().getDimensionPixelSize(R.dimen.design_bottom_navigation_shadow_height)));
    addView(view);
  }
  
  private ColorStateList createDefaultColorStateList(int paramInt) {
    TypedValue typedValue = new TypedValue();
    if (!getContext().getTheme().resolveAttribute(paramInt, typedValue, true))
      return null; 
    ColorStateList colorStateList = AppCompatResources.getColorStateList(getContext(), typedValue.resourceId);
    if (!getContext().getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true))
      return null; 
    int i = typedValue.data;
    int j = colorStateList.getDefaultColor();
    int[] arrayOfInt1 = DISABLED_STATE_SET;
    int[] arrayOfInt2 = CHECKED_STATE_SET;
    int[] arrayOfInt3 = EMPTY_STATE_SET;
    paramInt = colorStateList.getColorForState(DISABLED_STATE_SET, j);
    return new ColorStateList(new int[][] { arrayOfInt1, arrayOfInt2, arrayOfInt3 }, new int[] { paramInt, i, j });
  }
  
  private MenuInflater getMenuInflater() {
    if (this.mMenuInflater == null)
      this.mMenuInflater = (MenuInflater)new SupportMenuInflater(getContext()); 
    return this.mMenuInflater;
  }
  
  public int getItemBackgroundResource() {
    return this.mMenuView.getItemBackgroundRes();
  }
  
  public ColorStateList getItemIconTintList() {
    return this.mMenuView.getIconTintList();
  }
  
  public ColorStateList getItemTextColor() {
    return this.mMenuView.getItemTextColor();
  }
  
  public int getMaxItemCount() {
    return 5;
  }
  
  public Menu getMenu() {
    return (Menu)this.mMenu;
  }
  
  public int getSelectedItemId() {
    return this.mMenuView.getSelectedItemId();
  }
  
  public void inflateMenu(int paramInt) {
    this.mPresenter.setUpdateSuspended(true);
    getMenuInflater().inflate(paramInt, (Menu)this.mMenu);
    this.mPresenter.setUpdateSuspended(false);
    this.mPresenter.updateMenuView(true);
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable) {
    if (!(paramParcelable instanceof SavedState)) {
      super.onRestoreInstanceState(paramParcelable);
      return;
    } 
    SavedState savedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(savedState.getSuperState());
    this.mMenu.restorePresenterStates(savedState.menuPresenterState);
  }
  
  protected Parcelable onSaveInstanceState() {
    SavedState savedState = new SavedState(super.onSaveInstanceState());
    savedState.menuPresenterState = new Bundle();
    this.mMenu.savePresenterStates(savedState.menuPresenterState);
    return (Parcelable)savedState;
  }
  
  public void setItemBackgroundResource(int paramInt) {
    this.mMenuView.setItemBackgroundRes(paramInt);
  }
  
  public void setItemIconTintList(ColorStateList paramColorStateList) {
    this.mMenuView.setIconTintList(paramColorStateList);
  }
  
  public void setItemTextColor(ColorStateList paramColorStateList) {
    this.mMenuView.setItemTextColor(paramColorStateList);
  }
  
  public void setOnNavigationItemReselectedListener(OnNavigationItemReselectedListener paramOnNavigationItemReselectedListener) {
    this.mReselectedListener = paramOnNavigationItemReselectedListener;
  }
  
  public void setOnNavigationItemSelectedListener(OnNavigationItemSelectedListener paramOnNavigationItemSelectedListener) {
    this.mSelectedListener = paramOnNavigationItemSelectedListener;
  }
  
  public void setSelectedItemId(int paramInt) {
    MenuItem menuItem = this.mMenu.findItem(paramInt);
    if (menuItem != null && !this.mMenu.performItemAction(menuItem, (MenuPresenter)this.mPresenter, 0))
      menuItem.setChecked(true); 
  }
  
  public static interface OnNavigationItemReselectedListener {
    void onNavigationItemReselected(MenuItem param1MenuItem);
  }
  
  public static interface OnNavigationItemSelectedListener {
    boolean onNavigationItemSelected(MenuItem param1MenuItem);
  }
  
  static class SavedState extends AbsSavedState {
    public static final Parcelable.Creator<SavedState> CREATOR = (Parcelable.Creator<SavedState>)new Parcelable.ClassLoaderCreator<SavedState>() {
        public BottomNavigationView.SavedState createFromParcel(Parcel param2Parcel) {
          return new BottomNavigationView.SavedState(param2Parcel, null);
        }
        
        public BottomNavigationView.SavedState createFromParcel(Parcel param2Parcel, ClassLoader param2ClassLoader) {
          return new BottomNavigationView.SavedState(param2Parcel, param2ClassLoader);
        }
        
        public BottomNavigationView.SavedState[] newArray(int param2Int) {
          return new BottomNavigationView.SavedState[param2Int];
        }
      };
    
    Bundle menuPresenterState;
    
    public SavedState(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      super(param1Parcel, param1ClassLoader);
      readFromParcel(param1Parcel, param1ClassLoader);
    }
    
    public SavedState(Parcelable param1Parcelable) {
      super(param1Parcelable);
    }
    
    private void readFromParcel(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      this.menuPresenterState = param1Parcel.readBundle(param1ClassLoader);
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      super.writeToParcel(param1Parcel, param1Int);
      param1Parcel.writeBundle(this.menuPresenterState);
    }
  }
  
  static final class null implements Parcelable.ClassLoaderCreator<SavedState> {
    public BottomNavigationView.SavedState createFromParcel(Parcel param1Parcel) {
      return new BottomNavigationView.SavedState(param1Parcel, null);
    }
    
    public BottomNavigationView.SavedState createFromParcel(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      return new BottomNavigationView.SavedState(param1Parcel, param1ClassLoader);
    }
    
    public BottomNavigationView.SavedState[] newArray(int param1Int) {
      return new BottomNavigationView.SavedState[param1Int];
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\widget\BottomNavigationView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */