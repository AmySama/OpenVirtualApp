package android.support.v7.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter;
import android.util.SparseArray;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.SpinnerAdapter;

public interface DecorToolbar {
  void animateToVisibility(int paramInt);
  
  boolean canShowOverflowMenu();
  
  void collapseActionView();
  
  void dismissPopupMenus();
  
  Context getContext();
  
  View getCustomView();
  
  int getDisplayOptions();
  
  int getDropdownItemCount();
  
  int getDropdownSelectedPosition();
  
  int getHeight();
  
  Menu getMenu();
  
  int getNavigationMode();
  
  CharSequence getSubtitle();
  
  CharSequence getTitle();
  
  ViewGroup getViewGroup();
  
  int getVisibility();
  
  boolean hasEmbeddedTabs();
  
  boolean hasExpandedActionView();
  
  boolean hasIcon();
  
  boolean hasLogo();
  
  boolean hideOverflowMenu();
  
  void initIndeterminateProgress();
  
  void initProgress();
  
  boolean isOverflowMenuShowPending();
  
  boolean isOverflowMenuShowing();
  
  boolean isTitleTruncated();
  
  void restoreHierarchyState(SparseArray<Parcelable> paramSparseArray);
  
  void saveHierarchyState(SparseArray<Parcelable> paramSparseArray);
  
  void setBackgroundDrawable(Drawable paramDrawable);
  
  void setCollapsible(boolean paramBoolean);
  
  void setCustomView(View paramView);
  
  void setDefaultNavigationContentDescription(int paramInt);
  
  void setDefaultNavigationIcon(Drawable paramDrawable);
  
  void setDisplayOptions(int paramInt);
  
  void setDropdownParams(SpinnerAdapter paramSpinnerAdapter, AdapterView.OnItemSelectedListener paramOnItemSelectedListener);
  
  void setDropdownSelectedPosition(int paramInt);
  
  void setEmbeddedTabView(ScrollingTabContainerView paramScrollingTabContainerView);
  
  void setHomeButtonEnabled(boolean paramBoolean);
  
  void setIcon(int paramInt);
  
  void setIcon(Drawable paramDrawable);
  
  void setLogo(int paramInt);
  
  void setLogo(Drawable paramDrawable);
  
  void setMenu(Menu paramMenu, MenuPresenter.Callback paramCallback);
  
  void setMenuCallbacks(MenuPresenter.Callback paramCallback, MenuBuilder.Callback paramCallback1);
  
  void setMenuPrepared();
  
  void setNavigationContentDescription(int paramInt);
  
  void setNavigationContentDescription(CharSequence paramCharSequence);
  
  void setNavigationIcon(int paramInt);
  
  void setNavigationIcon(Drawable paramDrawable);
  
  void setNavigationMode(int paramInt);
  
  void setSubtitle(CharSequence paramCharSequence);
  
  void setTitle(CharSequence paramCharSequence);
  
  void setVisibility(int paramInt);
  
  void setWindowCallback(Window.Callback paramCallback);
  
  void setWindowTitle(CharSequence paramCharSequence);
  
  ViewPropertyAnimatorCompat setupAnimatorToVisibility(int paramInt, long paramLong);
  
  boolean showOverflowMenu();
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\DecorToolbar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */