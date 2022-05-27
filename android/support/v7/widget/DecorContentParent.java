package android.support.v7.widget;

import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v7.view.menu.MenuPresenter;
import android.util.SparseArray;
import android.view.Menu;
import android.view.Window;

public interface DecorContentParent {
  boolean canShowOverflowMenu();
  
  void dismissPopups();
  
  CharSequence getTitle();
  
  boolean hasIcon();
  
  boolean hasLogo();
  
  boolean hideOverflowMenu();
  
  void initFeature(int paramInt);
  
  boolean isOverflowMenuShowPending();
  
  boolean isOverflowMenuShowing();
  
  void restoreToolbarHierarchyState(SparseArray<Parcelable> paramSparseArray);
  
  void saveToolbarHierarchyState(SparseArray<Parcelable> paramSparseArray);
  
  void setIcon(int paramInt);
  
  void setIcon(Drawable paramDrawable);
  
  void setLogo(int paramInt);
  
  void setMenu(Menu paramMenu, MenuPresenter.Callback paramCallback);
  
  void setMenuPrepared();
  
  void setUiOptions(int paramInt);
  
  void setWindowCallback(Window.Callback paramCallback);
  
  void setWindowTitle(CharSequence paramCharSequence);
  
  boolean showOverflowMenu();
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\DecorContentParent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */