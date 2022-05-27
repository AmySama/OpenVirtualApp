package android.support.v7.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcelable;
import android.support.v7.appcompat.R;
import android.support.v7.widget.MenuPopupWindow;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

final class StandardMenuPopup extends MenuPopup implements PopupWindow.OnDismissListener, AdapterView.OnItemClickListener, MenuPresenter, View.OnKeyListener {
  private final MenuAdapter mAdapter;
  
  private View mAnchorView;
  
  private final View.OnAttachStateChangeListener mAttachStateChangeListener = new View.OnAttachStateChangeListener() {
      public void onViewAttachedToWindow(View param1View) {}
      
      public void onViewDetachedFromWindow(View param1View) {
        if (StandardMenuPopup.this.mTreeObserver != null) {
          if (!StandardMenuPopup.this.mTreeObserver.isAlive())
            StandardMenuPopup.access$002(StandardMenuPopup.this, param1View.getViewTreeObserver()); 
          StandardMenuPopup.this.mTreeObserver.removeGlobalOnLayoutListener(StandardMenuPopup.this.mGlobalLayoutListener);
        } 
        param1View.removeOnAttachStateChangeListener(this);
      }
    };
  
  private int mContentWidth;
  
  private final Context mContext;
  
  private int mDropDownGravity = 0;
  
  private final ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
      public void onGlobalLayout() {
        if (StandardMenuPopup.this.isShowing() && !StandardMenuPopup.this.mPopup.isModal()) {
          View view = StandardMenuPopup.this.mShownAnchorView;
          if (view == null || !view.isShown()) {
            StandardMenuPopup.this.dismiss();
            return;
          } 
          StandardMenuPopup.this.mPopup.show();
        } 
      }
    };
  
  private boolean mHasContentWidth;
  
  private final MenuBuilder mMenu;
  
  private PopupWindow.OnDismissListener mOnDismissListener;
  
  private final boolean mOverflowOnly;
  
  final MenuPopupWindow mPopup;
  
  private final int mPopupMaxWidth;
  
  private final int mPopupStyleAttr;
  
  private final int mPopupStyleRes;
  
  private MenuPresenter.Callback mPresenterCallback;
  
  private boolean mShowTitle;
  
  View mShownAnchorView;
  
  private ViewTreeObserver mTreeObserver;
  
  private boolean mWasDismissed;
  
  public StandardMenuPopup(Context paramContext, MenuBuilder paramMenuBuilder, View paramView, int paramInt1, int paramInt2, boolean paramBoolean) {
    this.mContext = paramContext;
    this.mMenu = paramMenuBuilder;
    this.mOverflowOnly = paramBoolean;
    this.mAdapter = new MenuAdapter(paramMenuBuilder, LayoutInflater.from(paramContext), this.mOverflowOnly);
    this.mPopupStyleAttr = paramInt1;
    this.mPopupStyleRes = paramInt2;
    Resources resources = paramContext.getResources();
    this.mPopupMaxWidth = Math.max((resources.getDisplayMetrics()).widthPixels / 2, resources.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
    this.mAnchorView = paramView;
    this.mPopup = new MenuPopupWindow(this.mContext, null, this.mPopupStyleAttr, this.mPopupStyleRes);
    paramMenuBuilder.addMenuPresenter(this, paramContext);
  }
  
  private boolean tryShow() {
    if (isShowing())
      return true; 
    if (!this.mWasDismissed) {
      View view = this.mAnchorView;
      if (view != null) {
        boolean bool;
        this.mShownAnchorView = view;
        this.mPopup.setOnDismissListener(this);
        this.mPopup.setOnItemClickListener(this);
        this.mPopup.setModal(true);
        view = this.mShownAnchorView;
        if (this.mTreeObserver == null) {
          bool = true;
        } else {
          bool = false;
        } 
        ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        this.mTreeObserver = viewTreeObserver;
        if (bool)
          viewTreeObserver.addOnGlobalLayoutListener(this.mGlobalLayoutListener); 
        view.addOnAttachStateChangeListener(this.mAttachStateChangeListener);
        this.mPopup.setAnchorView(view);
        this.mPopup.setDropDownGravity(this.mDropDownGravity);
        if (!this.mHasContentWidth) {
          this.mContentWidth = measureIndividualMenuWidth((ListAdapter)this.mAdapter, null, this.mContext, this.mPopupMaxWidth);
          this.mHasContentWidth = true;
        } 
        this.mPopup.setContentWidth(this.mContentWidth);
        this.mPopup.setInputMethodMode(2);
        this.mPopup.setEpicenterBounds(getEpicenterBounds());
        this.mPopup.show();
        ListView listView = this.mPopup.getListView();
        listView.setOnKeyListener(this);
        if (this.mShowTitle && this.mMenu.getHeaderTitle() != null) {
          FrameLayout frameLayout = (FrameLayout)LayoutInflater.from(this.mContext).inflate(R.layout.abc_popup_menu_header_item_layout, (ViewGroup)listView, false);
          TextView textView = (TextView)frameLayout.findViewById(16908310);
          if (textView != null)
            textView.setText(this.mMenu.getHeaderTitle()); 
          frameLayout.setEnabled(false);
          listView.addHeaderView((View)frameLayout, null, false);
        } 
        this.mPopup.setAdapter((ListAdapter)this.mAdapter);
        this.mPopup.show();
        return true;
      } 
    } 
    return false;
  }
  
  public void addMenu(MenuBuilder paramMenuBuilder) {}
  
  public void dismiss() {
    if (isShowing())
      this.mPopup.dismiss(); 
  }
  
  public boolean flagActionItems() {
    return false;
  }
  
  public ListView getListView() {
    return this.mPopup.getListView();
  }
  
  public boolean isShowing() {
    boolean bool;
    if (!this.mWasDismissed && this.mPopup.isShowing()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean) {
    if (paramMenuBuilder != this.mMenu)
      return; 
    dismiss();
    MenuPresenter.Callback callback = this.mPresenterCallback;
    if (callback != null)
      callback.onCloseMenu(paramMenuBuilder, paramBoolean); 
  }
  
  public void onDismiss() {
    this.mWasDismissed = true;
    this.mMenu.close();
    ViewTreeObserver viewTreeObserver = this.mTreeObserver;
    if (viewTreeObserver != null) {
      if (!viewTreeObserver.isAlive())
        this.mTreeObserver = this.mShownAnchorView.getViewTreeObserver(); 
      this.mTreeObserver.removeGlobalOnLayoutListener(this.mGlobalLayoutListener);
      this.mTreeObserver = null;
    } 
    this.mShownAnchorView.removeOnAttachStateChangeListener(this.mAttachStateChangeListener);
    PopupWindow.OnDismissListener onDismissListener = this.mOnDismissListener;
    if (onDismissListener != null)
      onDismissListener.onDismiss(); 
  }
  
  public boolean onKey(View paramView, int paramInt, KeyEvent paramKeyEvent) {
    if (paramKeyEvent.getAction() == 1 && paramInt == 82) {
      dismiss();
      return true;
    } 
    return false;
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable) {}
  
  public Parcelable onSaveInstanceState() {
    return null;
  }
  
  public boolean onSubMenuSelected(SubMenuBuilder paramSubMenuBuilder) {
    if (paramSubMenuBuilder.hasVisibleItems()) {
      MenuPopupHelper menuPopupHelper = new MenuPopupHelper(this.mContext, paramSubMenuBuilder, this.mShownAnchorView, this.mOverflowOnly, this.mPopupStyleAttr, this.mPopupStyleRes);
      menuPopupHelper.setPresenterCallback(this.mPresenterCallback);
      menuPopupHelper.setForceShowIcon(MenuPopup.shouldPreserveIconSpacing(paramSubMenuBuilder));
      menuPopupHelper.setGravity(this.mDropDownGravity);
      menuPopupHelper.setOnDismissListener(this.mOnDismissListener);
      this.mOnDismissListener = null;
      this.mMenu.close(false);
      if (menuPopupHelper.tryShow(this.mPopup.getHorizontalOffset(), this.mPopup.getVerticalOffset())) {
        MenuPresenter.Callback callback = this.mPresenterCallback;
        if (callback != null)
          callback.onOpenSubMenu(paramSubMenuBuilder); 
        return true;
      } 
    } 
    return false;
  }
  
  public void setAnchorView(View paramView) {
    this.mAnchorView = paramView;
  }
  
  public void setCallback(MenuPresenter.Callback paramCallback) {
    this.mPresenterCallback = paramCallback;
  }
  
  public void setForceShowIcon(boolean paramBoolean) {
    this.mAdapter.setForceShowIcon(paramBoolean);
  }
  
  public void setGravity(int paramInt) {
    this.mDropDownGravity = paramInt;
  }
  
  public void setHorizontalOffset(int paramInt) {
    this.mPopup.setHorizontalOffset(paramInt);
  }
  
  public void setOnDismissListener(PopupWindow.OnDismissListener paramOnDismissListener) {
    this.mOnDismissListener = paramOnDismissListener;
  }
  
  public void setShowTitle(boolean paramBoolean) {
    this.mShowTitle = paramBoolean;
  }
  
  public void setVerticalOffset(int paramInt) {
    this.mPopup.setVerticalOffset(paramInt);
  }
  
  public void show() {
    if (tryShow())
      return; 
    throw new IllegalStateException("StandardMenuPopup cannot be used without an anchor");
  }
  
  public void updateMenuView(boolean paramBoolean) {
    this.mHasContentWidth = false;
    MenuAdapter menuAdapter = this.mAdapter;
    if (menuAdapter != null)
      menuAdapter.notifyDataSetChanged(); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\view\menu\StandardMenuPopup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */