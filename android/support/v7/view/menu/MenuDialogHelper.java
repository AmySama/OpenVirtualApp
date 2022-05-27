package android.support.v7.view.menu;

import android.content.DialogInterface;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.appcompat.R;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

class MenuDialogHelper implements DialogInterface.OnKeyListener, DialogInterface.OnClickListener, DialogInterface.OnDismissListener, MenuPresenter.Callback {
  private AlertDialog mDialog;
  
  private MenuBuilder mMenu;
  
  ListMenuPresenter mPresenter;
  
  private MenuPresenter.Callback mPresenterCallback;
  
  public MenuDialogHelper(MenuBuilder paramMenuBuilder) {
    this.mMenu = paramMenuBuilder;
  }
  
  public void dismiss() {
    AlertDialog alertDialog = this.mDialog;
    if (alertDialog != null)
      alertDialog.dismiss(); 
  }
  
  public void onClick(DialogInterface paramDialogInterface, int paramInt) {
    this.mMenu.performItemAction((MenuItem)this.mPresenter.getAdapter().getItem(paramInt), 0);
  }
  
  public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean) {
    if (paramBoolean || paramMenuBuilder == this.mMenu)
      dismiss(); 
    MenuPresenter.Callback callback = this.mPresenterCallback;
    if (callback != null)
      callback.onCloseMenu(paramMenuBuilder, paramBoolean); 
  }
  
  public void onDismiss(DialogInterface paramDialogInterface) {
    this.mPresenter.onCloseMenu(this.mMenu, true);
  }
  
  public boolean onKey(DialogInterface paramDialogInterface, int paramInt, KeyEvent paramKeyEvent) {
    if (paramInt == 82 || paramInt == 4) {
      KeyEvent.DispatcherState dispatcherState;
      if (paramKeyEvent.getAction() == 0 && paramKeyEvent.getRepeatCount() == 0) {
        Window window = this.mDialog.getWindow();
        if (window != null) {
          View view = window.getDecorView();
          if (view != null) {
            dispatcherState = view.getKeyDispatcherState();
            if (dispatcherState != null) {
              dispatcherState.startTracking(paramKeyEvent, this);
              return true;
            } 
          } 
        } 
      } else if (paramKeyEvent.getAction() == 1 && !paramKeyEvent.isCanceled()) {
        Window window = this.mDialog.getWindow();
        if (window != null) {
          View view = window.getDecorView();
          if (view != null) {
            KeyEvent.DispatcherState dispatcherState1 = view.getKeyDispatcherState();
            if (dispatcherState1 != null && dispatcherState1.isTracking(paramKeyEvent)) {
              this.mMenu.close(true);
              dispatcherState.dismiss();
              return true;
            } 
          } 
        } 
      } 
    } 
    return this.mMenu.performShortcut(paramInt, paramKeyEvent, 0);
  }
  
  public boolean onOpenSubMenu(MenuBuilder paramMenuBuilder) {
    MenuPresenter.Callback callback = this.mPresenterCallback;
    return (callback != null) ? callback.onOpenSubMenu(paramMenuBuilder) : false;
  }
  
  public void setPresenterCallback(MenuPresenter.Callback paramCallback) {
    this.mPresenterCallback = paramCallback;
  }
  
  public void show(IBinder paramIBinder) {
    MenuBuilder menuBuilder = this.mMenu;
    AlertDialog.Builder builder = new AlertDialog.Builder(menuBuilder.getContext());
    ListMenuPresenter listMenuPresenter = new ListMenuPresenter(builder.getContext(), R.layout.abc_list_menu_item_layout);
    this.mPresenter = listMenuPresenter;
    listMenuPresenter.setCallback(this);
    this.mMenu.addMenuPresenter(this.mPresenter);
    builder.setAdapter(this.mPresenter.getAdapter(), this);
    View view = menuBuilder.getHeaderView();
    if (view != null) {
      builder.setCustomTitle(view);
    } else {
      builder.setIcon(menuBuilder.getHeaderIcon()).setTitle(menuBuilder.getHeaderTitle());
    } 
    builder.setOnKeyListener(this);
    AlertDialog alertDialog = builder.create();
    this.mDialog = alertDialog;
    alertDialog.setOnDismissListener(this);
    WindowManager.LayoutParams layoutParams = this.mDialog.getWindow().getAttributes();
    layoutParams.type = 1003;
    if (paramIBinder != null)
      layoutParams.token = paramIBinder; 
    layoutParams.flags |= 0x20000;
    this.mDialog.show();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\view\menu\MenuDialogHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */