package android.support.v7.view.menu;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;

public class MenuPopupHelper implements MenuHelper {
  private static final int TOUCH_EPICENTER_SIZE_DP = 48;
  
  private View mAnchorView;
  
  private final Context mContext;
  
  private int mDropDownGravity = 8388611;
  
  private boolean mForceShowIcon;
  
  private final PopupWindow.OnDismissListener mInternalOnDismissListener = new PopupWindow.OnDismissListener() {
      public void onDismiss() {
        MenuPopupHelper.this.onDismiss();
      }
    };
  
  private final MenuBuilder mMenu;
  
  private PopupWindow.OnDismissListener mOnDismissListener;
  
  private final boolean mOverflowOnly;
  
  private MenuPopup mPopup;
  
  private final int mPopupStyleAttr;
  
  private final int mPopupStyleRes;
  
  private MenuPresenter.Callback mPresenterCallback;
  
  public MenuPopupHelper(Context paramContext, MenuBuilder paramMenuBuilder) {
    this(paramContext, paramMenuBuilder, null, false, R.attr.popupMenuStyle, 0);
  }
  
  public MenuPopupHelper(Context paramContext, MenuBuilder paramMenuBuilder, View paramView) {
    this(paramContext, paramMenuBuilder, paramView, false, R.attr.popupMenuStyle, 0);
  }
  
  public MenuPopupHelper(Context paramContext, MenuBuilder paramMenuBuilder, View paramView, boolean paramBoolean, int paramInt) {
    this(paramContext, paramMenuBuilder, paramView, paramBoolean, paramInt, 0);
  }
  
  public MenuPopupHelper(Context paramContext, MenuBuilder paramMenuBuilder, View paramView, boolean paramBoolean, int paramInt1, int paramInt2) {
    this.mContext = paramContext;
    this.mMenu = paramMenuBuilder;
    this.mAnchorView = paramView;
    this.mOverflowOnly = paramBoolean;
    this.mPopupStyleAttr = paramInt1;
    this.mPopupStyleRes = paramInt2;
  }
  
  private MenuPopup createPopup() {
    StandardMenuPopup standardMenuPopup;
    boolean bool;
    Display display = ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay();
    Point point = new Point();
    if (Build.VERSION.SDK_INT >= 17) {
      display.getRealSize(point);
    } else {
      display.getSize(point);
    } 
    if (Math.min(point.x, point.y) >= this.mContext.getResources().getDimensionPixelSize(R.dimen.abc_cascading_menus_min_smallest_width)) {
      bool = true;
    } else {
      bool = false;
    } 
    if (bool) {
      CascadingMenuPopup cascadingMenuPopup = new CascadingMenuPopup(this.mContext, this.mAnchorView, this.mPopupStyleAttr, this.mPopupStyleRes, this.mOverflowOnly);
    } else {
      standardMenuPopup = new StandardMenuPopup(this.mContext, this.mMenu, this.mAnchorView, this.mPopupStyleAttr, this.mPopupStyleRes, this.mOverflowOnly);
    } 
    standardMenuPopup.addMenu(this.mMenu);
    standardMenuPopup.setOnDismissListener(this.mInternalOnDismissListener);
    standardMenuPopup.setAnchorView(this.mAnchorView);
    standardMenuPopup.setCallback(this.mPresenterCallback);
    standardMenuPopup.setForceShowIcon(this.mForceShowIcon);
    standardMenuPopup.setGravity(this.mDropDownGravity);
    return standardMenuPopup;
  }
  
  private void showPopup(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2) {
    MenuPopup menuPopup = getPopup();
    menuPopup.setShowTitle(paramBoolean2);
    if (paramBoolean1) {
      int i = paramInt1;
      if ((GravityCompat.getAbsoluteGravity(this.mDropDownGravity, ViewCompat.getLayoutDirection(this.mAnchorView)) & 0x7) == 5)
        i = paramInt1 + this.mAnchorView.getWidth(); 
      menuPopup.setHorizontalOffset(i);
      menuPopup.setVerticalOffset(paramInt2);
      paramInt1 = (int)((this.mContext.getResources().getDisplayMetrics()).density * 48.0F / 2.0F);
      menuPopup.setEpicenterBounds(new Rect(i - paramInt1, paramInt2 - paramInt1, i + paramInt1, paramInt2 + paramInt1));
    } 
    menuPopup.show();
  }
  
  public void dismiss() {
    if (isShowing())
      this.mPopup.dismiss(); 
  }
  
  public int getGravity() {
    return this.mDropDownGravity;
  }
  
  public ListView getListView() {
    return getPopup().getListView();
  }
  
  public MenuPopup getPopup() {
    if (this.mPopup == null)
      this.mPopup = createPopup(); 
    return this.mPopup;
  }
  
  public boolean isShowing() {
    boolean bool;
    MenuPopup menuPopup = this.mPopup;
    if (menuPopup != null && menuPopup.isShowing()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  protected void onDismiss() {
    this.mPopup = null;
    PopupWindow.OnDismissListener onDismissListener = this.mOnDismissListener;
    if (onDismissListener != null)
      onDismissListener.onDismiss(); 
  }
  
  public void setAnchorView(View paramView) {
    this.mAnchorView = paramView;
  }
  
  public void setForceShowIcon(boolean paramBoolean) {
    this.mForceShowIcon = paramBoolean;
    MenuPopup menuPopup = this.mPopup;
    if (menuPopup != null)
      menuPopup.setForceShowIcon(paramBoolean); 
  }
  
  public void setGravity(int paramInt) {
    this.mDropDownGravity = paramInt;
  }
  
  public void setOnDismissListener(PopupWindow.OnDismissListener paramOnDismissListener) {
    this.mOnDismissListener = paramOnDismissListener;
  }
  
  public void setPresenterCallback(MenuPresenter.Callback paramCallback) {
    this.mPresenterCallback = paramCallback;
    MenuPopup menuPopup = this.mPopup;
    if (menuPopup != null)
      menuPopup.setCallback(paramCallback); 
  }
  
  public void show() {
    if (tryShow())
      return; 
    throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
  }
  
  public void show(int paramInt1, int paramInt2) {
    if (tryShow(paramInt1, paramInt2))
      return; 
    throw new IllegalStateException("MenuPopupHelper cannot be used without an anchor");
  }
  
  public boolean tryShow() {
    if (isShowing())
      return true; 
    if (this.mAnchorView == null)
      return false; 
    showPopup(0, 0, false, false);
    return true;
  }
  
  public boolean tryShow(int paramInt1, int paramInt2) {
    if (isShowing())
      return true; 
    if (this.mAnchorView == null)
      return false; 
    showPopup(paramInt1, paramInt2, true, true);
    return true;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\view\menu\MenuPopupHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */