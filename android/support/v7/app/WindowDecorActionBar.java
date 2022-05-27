package android.support.v7.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.ViewPropertyAnimatorUpdateListener;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.view.ActionBarPolicy;
import android.support.v7.view.ActionMode;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.ViewPropertyAnimatorCompatSet;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.ActionBarContainer;
import android.support.v7.widget.ActionBarContextView;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.DecorToolbar;
import android.support.v7.widget.ScrollingTabContainerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.SpinnerAdapter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class WindowDecorActionBar extends ActionBar implements ActionBarOverlayLayout.ActionBarVisibilityCallback {
  private static final long FADE_IN_DURATION_MS = 200L;
  
  private static final long FADE_OUT_DURATION_MS = 100L;
  
  private static final int INVALID_POSITION = -1;
  
  private static final String TAG = "WindowDecorActionBar";
  
  private static final Interpolator sHideInterpolator = (Interpolator)new AccelerateInterpolator();
  
  private static final Interpolator sShowInterpolator = (Interpolator)new DecelerateInterpolator();
  
  ActionModeImpl mActionMode;
  
  private Activity mActivity;
  
  ActionBarContainer mContainerView;
  
  boolean mContentAnimations = true;
  
  View mContentView;
  
  Context mContext;
  
  ActionBarContextView mContextView;
  
  private int mCurWindowVisibility = 0;
  
  ViewPropertyAnimatorCompatSet mCurrentShowAnim;
  
  DecorToolbar mDecorToolbar;
  
  ActionMode mDeferredDestroyActionMode;
  
  ActionMode.Callback mDeferredModeDestroyCallback;
  
  private Dialog mDialog;
  
  private boolean mDisplayHomeAsUpSet;
  
  private boolean mHasEmbeddedTabs;
  
  boolean mHiddenByApp;
  
  boolean mHiddenBySystem;
  
  final ViewPropertyAnimatorListener mHideListener = (ViewPropertyAnimatorListener)new ViewPropertyAnimatorListenerAdapter() {
      public void onAnimationEnd(View param1View) {
        if (WindowDecorActionBar.this.mContentAnimations && WindowDecorActionBar.this.mContentView != null) {
          WindowDecorActionBar.this.mContentView.setTranslationY(0.0F);
          WindowDecorActionBar.this.mContainerView.setTranslationY(0.0F);
        } 
        WindowDecorActionBar.this.mContainerView.setVisibility(8);
        WindowDecorActionBar.this.mContainerView.setTransitioning(false);
        WindowDecorActionBar.this.mCurrentShowAnim = null;
        WindowDecorActionBar.this.completeDeferredDestroyActionMode();
        if (WindowDecorActionBar.this.mOverlayLayout != null)
          ViewCompat.requestApplyInsets((View)WindowDecorActionBar.this.mOverlayLayout); 
      }
    };
  
  boolean mHideOnContentScroll;
  
  private boolean mLastMenuVisibility;
  
  private ArrayList<ActionBar.OnMenuVisibilityListener> mMenuVisibilityListeners = new ArrayList<ActionBar.OnMenuVisibilityListener>();
  
  private boolean mNowShowing = true;
  
  ActionBarOverlayLayout mOverlayLayout;
  
  private int mSavedTabPosition = -1;
  
  private TabImpl mSelectedTab;
  
  private boolean mShowHideAnimationEnabled;
  
  final ViewPropertyAnimatorListener mShowListener = (ViewPropertyAnimatorListener)new ViewPropertyAnimatorListenerAdapter() {
      public void onAnimationEnd(View param1View) {
        WindowDecorActionBar.this.mCurrentShowAnim = null;
        WindowDecorActionBar.this.mContainerView.requestLayout();
      }
    };
  
  private boolean mShowingForMode;
  
  ScrollingTabContainerView mTabScrollView;
  
  private ArrayList<TabImpl> mTabs = new ArrayList<TabImpl>();
  
  private Context mThemedContext;
  
  final ViewPropertyAnimatorUpdateListener mUpdateListener = new ViewPropertyAnimatorUpdateListener() {
      public void onAnimationUpdate(View param1View) {
        ((View)WindowDecorActionBar.this.mContainerView.getParent()).invalidate();
      }
    };
  
  public WindowDecorActionBar(Activity paramActivity, boolean paramBoolean) {
    this.mActivity = paramActivity;
    View view = paramActivity.getWindow().getDecorView();
    init(view);
    if (!paramBoolean)
      this.mContentView = view.findViewById(16908290); 
  }
  
  public WindowDecorActionBar(Dialog paramDialog) {
    this.mDialog = paramDialog;
    init(paramDialog.getWindow().getDecorView());
  }
  
  public WindowDecorActionBar(View paramView) {
    init(paramView);
  }
  
  static boolean checkShowingFlags(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
    return paramBoolean3 ? true : (!(paramBoolean1 || paramBoolean2));
  }
  
  private void cleanupTabs() {
    if (this.mSelectedTab != null)
      selectTab(null); 
    this.mTabs.clear();
    ScrollingTabContainerView scrollingTabContainerView = this.mTabScrollView;
    if (scrollingTabContainerView != null)
      scrollingTabContainerView.removeAllTabs(); 
    this.mSavedTabPosition = -1;
  }
  
  private void configureTab(ActionBar.Tab paramTab, int paramInt) {
    paramTab = paramTab;
    if (paramTab.getCallback() != null) {
      paramTab.setPosition(paramInt);
      this.mTabs.add(paramInt, paramTab);
      int i = this.mTabs.size();
      while (++paramInt < i)
        ((TabImpl)this.mTabs.get(paramInt)).setPosition(paramInt); 
      return;
    } 
    throw new IllegalStateException("Action Bar Tab must have a Callback");
  }
  
  private void ensureTabsExist() {
    if (this.mTabScrollView != null)
      return; 
    ScrollingTabContainerView scrollingTabContainerView = new ScrollingTabContainerView(this.mContext);
    if (this.mHasEmbeddedTabs) {
      scrollingTabContainerView.setVisibility(0);
      this.mDecorToolbar.setEmbeddedTabView(scrollingTabContainerView);
    } else {
      if (getNavigationMode() == 2) {
        scrollingTabContainerView.setVisibility(0);
        ActionBarOverlayLayout actionBarOverlayLayout = this.mOverlayLayout;
        if (actionBarOverlayLayout != null)
          ViewCompat.requestApplyInsets((View)actionBarOverlayLayout); 
      } else {
        scrollingTabContainerView.setVisibility(8);
      } 
      this.mContainerView.setTabContainer(scrollingTabContainerView);
    } 
    this.mTabScrollView = scrollingTabContainerView;
  }
  
  private DecorToolbar getDecorToolbar(View paramView) {
    String str;
    if (paramView instanceof DecorToolbar)
      return (DecorToolbar)paramView; 
    if (paramView instanceof Toolbar)
      return ((Toolbar)paramView).getWrapper(); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Can't make a decor toolbar out of ");
    stringBuilder.append(paramView);
    if (stringBuilder.toString() != null) {
      str = paramView.getClass().getSimpleName();
    } else {
      str = "null";
    } 
    throw new IllegalStateException(str);
  }
  
  private void hideForActionMode() {
    if (this.mShowingForMode) {
      this.mShowingForMode = false;
      ActionBarOverlayLayout actionBarOverlayLayout = this.mOverlayLayout;
      if (actionBarOverlayLayout != null)
        actionBarOverlayLayout.setShowingForActionMode(false); 
      updateVisibility(false);
    } 
  }
  
  private void init(View paramView) {
    ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout)paramView.findViewById(R.id.decor_content_parent);
    this.mOverlayLayout = actionBarOverlayLayout;
    if (actionBarOverlayLayout != null)
      actionBarOverlayLayout.setActionBarVisibilityCallback(this); 
    this.mDecorToolbar = getDecorToolbar(paramView.findViewById(R.id.action_bar));
    this.mContextView = (ActionBarContextView)paramView.findViewById(R.id.action_context_bar);
    ActionBarContainer actionBarContainer = (ActionBarContainer)paramView.findViewById(R.id.action_bar_container);
    this.mContainerView = actionBarContainer;
    DecorToolbar decorToolbar = this.mDecorToolbar;
    if (decorToolbar != null && this.mContextView != null && actionBarContainer != null) {
      boolean bool;
      this.mContext = decorToolbar.getContext();
      if ((this.mDecorToolbar.getDisplayOptions() & 0x4) != 0) {
        i = 1;
      } else {
        i = 0;
      } 
      if (i)
        this.mDisplayHomeAsUpSet = true; 
      ActionBarPolicy actionBarPolicy = ActionBarPolicy.get(this.mContext);
      if (actionBarPolicy.enableHomeButtonByDefault() || i) {
        bool = true;
      } else {
        bool = false;
      } 
      setHomeButtonEnabled(bool);
      setHasEmbeddedTabs(actionBarPolicy.hasEmbeddedTabs());
      TypedArray typedArray = this.mContext.obtainStyledAttributes(null, R.styleable.ActionBar, R.attr.actionBarStyle, 0);
      if (typedArray.getBoolean(R.styleable.ActionBar_hideOnContentScroll, false))
        setHideOnContentScrollEnabled(true); 
      int i = typedArray.getDimensionPixelSize(R.styleable.ActionBar_elevation, 0);
      if (i != 0)
        setElevation(i); 
      typedArray.recycle();
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(getClass().getSimpleName());
    stringBuilder.append(" can only be used ");
    stringBuilder.append("with a compatible window decor layout");
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  private void setHasEmbeddedTabs(boolean paramBoolean) {
    this.mHasEmbeddedTabs = paramBoolean;
    if (!paramBoolean) {
      this.mDecorToolbar.setEmbeddedTabView(null);
      this.mContainerView.setTabContainer(this.mTabScrollView);
    } else {
      this.mContainerView.setTabContainer(null);
      this.mDecorToolbar.setEmbeddedTabView(this.mTabScrollView);
    } 
    int i = getNavigationMode();
    boolean bool = true;
    if (i == 2) {
      i = 1;
    } else {
      i = 0;
    } 
    ScrollingTabContainerView scrollingTabContainerView = this.mTabScrollView;
    if (scrollingTabContainerView != null) {
      ActionBarOverlayLayout actionBarOverlayLayout1;
      if (i != 0) {
        scrollingTabContainerView.setVisibility(0);
        actionBarOverlayLayout1 = this.mOverlayLayout;
        if (actionBarOverlayLayout1 != null)
          ViewCompat.requestApplyInsets((View)actionBarOverlayLayout1); 
      } else {
        actionBarOverlayLayout1.setVisibility(8);
      } 
    } 
    DecorToolbar decorToolbar = this.mDecorToolbar;
    if (!this.mHasEmbeddedTabs && i != 0) {
      paramBoolean = true;
    } else {
      paramBoolean = false;
    } 
    decorToolbar.setCollapsible(paramBoolean);
    ActionBarOverlayLayout actionBarOverlayLayout = this.mOverlayLayout;
    if (!this.mHasEmbeddedTabs && i != 0) {
      paramBoolean = bool;
    } else {
      paramBoolean = false;
    } 
    actionBarOverlayLayout.setHasNonEmbeddedTabs(paramBoolean);
  }
  
  private boolean shouldAnimateContextView() {
    return ViewCompat.isLaidOut((View)this.mContainerView);
  }
  
  private void showForActionMode() {
    if (!this.mShowingForMode) {
      this.mShowingForMode = true;
      ActionBarOverlayLayout actionBarOverlayLayout = this.mOverlayLayout;
      if (actionBarOverlayLayout != null)
        actionBarOverlayLayout.setShowingForActionMode(true); 
      updateVisibility(false);
    } 
  }
  
  private void updateVisibility(boolean paramBoolean) {
    if (checkShowingFlags(this.mHiddenByApp, this.mHiddenBySystem, this.mShowingForMode)) {
      if (!this.mNowShowing) {
        this.mNowShowing = true;
        doShow(paramBoolean);
      } 
    } else if (this.mNowShowing) {
      this.mNowShowing = false;
      doHide(paramBoolean);
    } 
  }
  
  public void addOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener paramOnMenuVisibilityListener) {
    this.mMenuVisibilityListeners.add(paramOnMenuVisibilityListener);
  }
  
  public void addTab(ActionBar.Tab paramTab) {
    addTab(paramTab, this.mTabs.isEmpty());
  }
  
  public void addTab(ActionBar.Tab paramTab, int paramInt) {
    addTab(paramTab, paramInt, this.mTabs.isEmpty());
  }
  
  public void addTab(ActionBar.Tab paramTab, int paramInt, boolean paramBoolean) {
    ensureTabsExist();
    this.mTabScrollView.addTab(paramTab, paramInt, paramBoolean);
    configureTab(paramTab, paramInt);
    if (paramBoolean)
      selectTab(paramTab); 
  }
  
  public void addTab(ActionBar.Tab paramTab, boolean paramBoolean) {
    ensureTabsExist();
    this.mTabScrollView.addTab(paramTab, paramBoolean);
    configureTab(paramTab, this.mTabs.size());
    if (paramBoolean)
      selectTab(paramTab); 
  }
  
  public void animateToMode(boolean paramBoolean) {
    if (paramBoolean) {
      showForActionMode();
    } else {
      hideForActionMode();
    } 
    if (shouldAnimateContextView()) {
      ViewPropertyAnimatorCompat viewPropertyAnimatorCompat1;
      ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2;
      if (paramBoolean) {
        viewPropertyAnimatorCompat1 = this.mDecorToolbar.setupAnimatorToVisibility(4, 100L);
        viewPropertyAnimatorCompat2 = this.mContextView.setupAnimatorToVisibility(0, 200L);
      } else {
        viewPropertyAnimatorCompat2 = this.mDecorToolbar.setupAnimatorToVisibility(0, 200L);
        viewPropertyAnimatorCompat1 = this.mContextView.setupAnimatorToVisibility(8, 100L);
      } 
      ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = new ViewPropertyAnimatorCompatSet();
      viewPropertyAnimatorCompatSet.playSequentially(viewPropertyAnimatorCompat1, viewPropertyAnimatorCompat2);
      viewPropertyAnimatorCompatSet.start();
    } else if (paramBoolean) {
      this.mDecorToolbar.setVisibility(4);
      this.mContextView.setVisibility(0);
    } else {
      this.mDecorToolbar.setVisibility(0);
      this.mContextView.setVisibility(8);
    } 
  }
  
  public boolean collapseActionView() {
    DecorToolbar decorToolbar = this.mDecorToolbar;
    if (decorToolbar != null && decorToolbar.hasExpandedActionView()) {
      this.mDecorToolbar.collapseActionView();
      return true;
    } 
    return false;
  }
  
  void completeDeferredDestroyActionMode() {
    ActionMode.Callback callback = this.mDeferredModeDestroyCallback;
    if (callback != null) {
      callback.onDestroyActionMode(this.mDeferredDestroyActionMode);
      this.mDeferredDestroyActionMode = null;
      this.mDeferredModeDestroyCallback = null;
    } 
  }
  
  public void dispatchMenuVisibilityChanged(boolean paramBoolean) {
    if (paramBoolean == this.mLastMenuVisibility)
      return; 
    this.mLastMenuVisibility = paramBoolean;
    int i = this.mMenuVisibilityListeners.size();
    for (byte b = 0; b < i; b++)
      ((ActionBar.OnMenuVisibilityListener)this.mMenuVisibilityListeners.get(b)).onMenuVisibilityChanged(paramBoolean); 
  }
  
  public void doHide(boolean paramBoolean) {
    ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = this.mCurrentShowAnim;
    if (viewPropertyAnimatorCompatSet != null)
      viewPropertyAnimatorCompatSet.cancel(); 
    if (this.mCurWindowVisibility == 0 && (this.mShowHideAnimationEnabled || paramBoolean)) {
      this.mContainerView.setAlpha(1.0F);
      this.mContainerView.setTransitioning(true);
      viewPropertyAnimatorCompatSet = new ViewPropertyAnimatorCompatSet();
      float f1 = -this.mContainerView.getHeight();
      float f2 = f1;
      if (paramBoolean) {
        int[] arrayOfInt = new int[2];
        arrayOfInt[0] = 0;
        arrayOfInt[1] = 0;
        this.mContainerView.getLocationInWindow(arrayOfInt);
        f2 = f1 - arrayOfInt[1];
      } 
      ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = ViewCompat.animate((View)this.mContainerView).translationY(f2);
      viewPropertyAnimatorCompat.setUpdateListener(this.mUpdateListener);
      viewPropertyAnimatorCompatSet.play(viewPropertyAnimatorCompat);
      if (this.mContentAnimations) {
        View view = this.mContentView;
        if (view != null)
          viewPropertyAnimatorCompatSet.play(ViewCompat.animate(view).translationY(f2)); 
      } 
      viewPropertyAnimatorCompatSet.setInterpolator(sHideInterpolator);
      viewPropertyAnimatorCompatSet.setDuration(250L);
      viewPropertyAnimatorCompatSet.setListener(this.mHideListener);
      this.mCurrentShowAnim = viewPropertyAnimatorCompatSet;
      viewPropertyAnimatorCompatSet.start();
    } else {
      this.mHideListener.onAnimationEnd(null);
    } 
  }
  
  public void doShow(boolean paramBoolean) {
    ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = this.mCurrentShowAnim;
    if (viewPropertyAnimatorCompatSet != null)
      viewPropertyAnimatorCompatSet.cancel(); 
    this.mContainerView.setVisibility(0);
    if (this.mCurWindowVisibility == 0 && (this.mShowHideAnimationEnabled || paramBoolean)) {
      this.mContainerView.setTranslationY(0.0F);
      float f1 = -this.mContainerView.getHeight();
      float f2 = f1;
      if (paramBoolean) {
        int[] arrayOfInt = new int[2];
        arrayOfInt[0] = 0;
        arrayOfInt[1] = 0;
        this.mContainerView.getLocationInWindow(arrayOfInt);
        f2 = f1 - arrayOfInt[1];
      } 
      this.mContainerView.setTranslationY(f2);
      viewPropertyAnimatorCompatSet = new ViewPropertyAnimatorCompatSet();
      ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = ViewCompat.animate((View)this.mContainerView).translationY(0.0F);
      viewPropertyAnimatorCompat.setUpdateListener(this.mUpdateListener);
      viewPropertyAnimatorCompatSet.play(viewPropertyAnimatorCompat);
      if (this.mContentAnimations) {
        View view = this.mContentView;
        if (view != null) {
          view.setTranslationY(f2);
          viewPropertyAnimatorCompatSet.play(ViewCompat.animate(this.mContentView).translationY(0.0F));
        } 
      } 
      viewPropertyAnimatorCompatSet.setInterpolator(sShowInterpolator);
      viewPropertyAnimatorCompatSet.setDuration(250L);
      viewPropertyAnimatorCompatSet.setListener(this.mShowListener);
      this.mCurrentShowAnim = viewPropertyAnimatorCompatSet;
      viewPropertyAnimatorCompatSet.start();
    } else {
      this.mContainerView.setAlpha(1.0F);
      this.mContainerView.setTranslationY(0.0F);
      if (this.mContentAnimations) {
        View view = this.mContentView;
        if (view != null)
          view.setTranslationY(0.0F); 
      } 
      this.mShowListener.onAnimationEnd(null);
    } 
    ActionBarOverlayLayout actionBarOverlayLayout = this.mOverlayLayout;
    if (actionBarOverlayLayout != null)
      ViewCompat.requestApplyInsets((View)actionBarOverlayLayout); 
  }
  
  public void enableContentAnimations(boolean paramBoolean) {
    this.mContentAnimations = paramBoolean;
  }
  
  public View getCustomView() {
    return this.mDecorToolbar.getCustomView();
  }
  
  public int getDisplayOptions() {
    return this.mDecorToolbar.getDisplayOptions();
  }
  
  public float getElevation() {
    return ViewCompat.getElevation((View)this.mContainerView);
  }
  
  public int getHeight() {
    return this.mContainerView.getHeight();
  }
  
  public int getHideOffset() {
    return this.mOverlayLayout.getActionBarHideOffset();
  }
  
  public int getNavigationItemCount() {
    int i = this.mDecorToolbar.getNavigationMode();
    return (i != 1) ? ((i != 2) ? 0 : this.mTabs.size()) : this.mDecorToolbar.getDropdownItemCount();
  }
  
  public int getNavigationMode() {
    return this.mDecorToolbar.getNavigationMode();
  }
  
  public int getSelectedNavigationIndex() {
    int i = this.mDecorToolbar.getNavigationMode();
    if (i != 1) {
      int j = -1;
      if (i != 2)
        return -1; 
      TabImpl tabImpl = this.mSelectedTab;
      if (tabImpl != null)
        j = tabImpl.getPosition(); 
      return j;
    } 
    return this.mDecorToolbar.getDropdownSelectedPosition();
  }
  
  public ActionBar.Tab getSelectedTab() {
    return this.mSelectedTab;
  }
  
  public CharSequence getSubtitle() {
    return this.mDecorToolbar.getSubtitle();
  }
  
  public ActionBar.Tab getTabAt(int paramInt) {
    return this.mTabs.get(paramInt);
  }
  
  public int getTabCount() {
    return this.mTabs.size();
  }
  
  public Context getThemedContext() {
    if (this.mThemedContext == null) {
      TypedValue typedValue = new TypedValue();
      this.mContext.getTheme().resolveAttribute(R.attr.actionBarWidgetTheme, typedValue, true);
      int i = typedValue.resourceId;
      if (i != 0) {
        this.mThemedContext = (Context)new ContextThemeWrapper(this.mContext, i);
      } else {
        this.mThemedContext = this.mContext;
      } 
    } 
    return this.mThemedContext;
  }
  
  public CharSequence getTitle() {
    return this.mDecorToolbar.getTitle();
  }
  
  public boolean hasIcon() {
    return this.mDecorToolbar.hasIcon();
  }
  
  public boolean hasLogo() {
    return this.mDecorToolbar.hasLogo();
  }
  
  public void hide() {
    if (!this.mHiddenByApp) {
      this.mHiddenByApp = true;
      updateVisibility(false);
    } 
  }
  
  public void hideForSystem() {
    if (!this.mHiddenBySystem) {
      this.mHiddenBySystem = true;
      updateVisibility(true);
    } 
  }
  
  public boolean isHideOnContentScrollEnabled() {
    return this.mOverlayLayout.isHideOnContentScrollEnabled();
  }
  
  public boolean isShowing() {
    boolean bool;
    int i = getHeight();
    if (this.mNowShowing && (i == 0 || getHideOffset() < i)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isTitleTruncated() {
    boolean bool;
    DecorToolbar decorToolbar = this.mDecorToolbar;
    if (decorToolbar != null && decorToolbar.isTitleTruncated()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public ActionBar.Tab newTab() {
    return new TabImpl();
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration) {
    setHasEmbeddedTabs(ActionBarPolicy.get(this.mContext).hasEmbeddedTabs());
  }
  
  public void onContentScrollStarted() {
    ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = this.mCurrentShowAnim;
    if (viewPropertyAnimatorCompatSet != null) {
      viewPropertyAnimatorCompatSet.cancel();
      this.mCurrentShowAnim = null;
    } 
  }
  
  public void onContentScrollStopped() {}
  
  public boolean onKeyShortcut(int paramInt, KeyEvent paramKeyEvent) {
    ActionModeImpl actionModeImpl = this.mActionMode;
    if (actionModeImpl == null)
      return false; 
    Menu menu = actionModeImpl.getMenu();
    if (menu != null) {
      if (paramKeyEvent != null) {
        i = paramKeyEvent.getDeviceId();
      } else {
        i = -1;
      } 
      int i = KeyCharacterMap.load(i).getKeyboardType();
      boolean bool = true;
      if (i == 1)
        bool = false; 
      menu.setQwertyMode(bool);
      return menu.performShortcut(paramInt, paramKeyEvent, 0);
    } 
    return false;
  }
  
  public void onWindowVisibilityChanged(int paramInt) {
    this.mCurWindowVisibility = paramInt;
  }
  
  public void removeAllTabs() {
    cleanupTabs();
  }
  
  public void removeOnMenuVisibilityListener(ActionBar.OnMenuVisibilityListener paramOnMenuVisibilityListener) {
    this.mMenuVisibilityListeners.remove(paramOnMenuVisibilityListener);
  }
  
  public void removeTab(ActionBar.Tab paramTab) {
    removeTabAt(paramTab.getPosition());
  }
  
  public void removeTabAt(int paramInt) {
    int i;
    if (this.mTabScrollView == null)
      return; 
    TabImpl tabImpl = this.mSelectedTab;
    if (tabImpl != null) {
      i = tabImpl.getPosition();
    } else {
      i = this.mSavedTabPosition;
    } 
    this.mTabScrollView.removeTabAt(paramInt);
    tabImpl = this.mTabs.remove(paramInt);
    if (tabImpl != null)
      tabImpl.setPosition(-1); 
    int j = this.mTabs.size();
    for (int k = paramInt; k < j; k++)
      ((TabImpl)this.mTabs.get(k)).setPosition(k); 
    if (i == paramInt) {
      if (this.mTabs.isEmpty()) {
        tabImpl = null;
      } else {
        tabImpl = this.mTabs.get(Math.max(0, paramInt - 1));
      } 
      selectTab(tabImpl);
    } 
  }
  
  public boolean requestFocus() {
    ViewGroup viewGroup = this.mDecorToolbar.getViewGroup();
    if (viewGroup != null && !viewGroup.hasFocus()) {
      viewGroup.requestFocus();
      return true;
    } 
    return false;
  }
  
  public void selectTab(ActionBar.Tab paramTab) {
    FragmentTransaction fragmentTransaction;
    int i = getNavigationMode();
    int j = -1;
    if (i != 2) {
      if (paramTab != null)
        j = paramTab.getPosition(); 
      this.mSavedTabPosition = j;
      return;
    } 
    if (this.mActivity instanceof FragmentActivity && !this.mDecorToolbar.getViewGroup().isInEditMode()) {
      fragmentTransaction = ((FragmentActivity)this.mActivity).getSupportFragmentManager().beginTransaction().disallowAddToBackStack();
    } else {
      fragmentTransaction = null;
    } 
    TabImpl tabImpl = this.mSelectedTab;
    if (tabImpl == paramTab) {
      if (tabImpl != null) {
        tabImpl.getCallback().onTabReselected(this.mSelectedTab, fragmentTransaction);
        this.mTabScrollView.animateToTab(paramTab.getPosition());
      } 
    } else {
      ScrollingTabContainerView scrollingTabContainerView = this.mTabScrollView;
      if (paramTab != null)
        j = paramTab.getPosition(); 
      scrollingTabContainerView.setTabSelected(j);
      TabImpl tabImpl1 = this.mSelectedTab;
      if (tabImpl1 != null)
        tabImpl1.getCallback().onTabUnselected(this.mSelectedTab, fragmentTransaction); 
      paramTab = paramTab;
      this.mSelectedTab = (TabImpl)paramTab;
      if (paramTab != null)
        paramTab.getCallback().onTabSelected(this.mSelectedTab, fragmentTransaction); 
    } 
    if (fragmentTransaction != null && !fragmentTransaction.isEmpty())
      fragmentTransaction.commit(); 
  }
  
  public void setBackgroundDrawable(Drawable paramDrawable) {
    this.mContainerView.setPrimaryBackground(paramDrawable);
  }
  
  public void setCustomView(int paramInt) {
    setCustomView(LayoutInflater.from(getThemedContext()).inflate(paramInt, this.mDecorToolbar.getViewGroup(), false));
  }
  
  public void setCustomView(View paramView) {
    this.mDecorToolbar.setCustomView(paramView);
  }
  
  public void setCustomView(View paramView, ActionBar.LayoutParams paramLayoutParams) {
    paramView.setLayoutParams((ViewGroup.LayoutParams)paramLayoutParams);
    this.mDecorToolbar.setCustomView(paramView);
  }
  
  public void setDefaultDisplayHomeAsUpEnabled(boolean paramBoolean) {
    if (!this.mDisplayHomeAsUpSet)
      setDisplayHomeAsUpEnabled(paramBoolean); 
  }
  
  public void setDisplayHomeAsUpEnabled(boolean paramBoolean) {
    boolean bool;
    if (paramBoolean) {
      bool = true;
    } else {
      bool = false;
    } 
    setDisplayOptions(bool, 4);
  }
  
  public void setDisplayOptions(int paramInt) {
    if ((paramInt & 0x4) != 0)
      this.mDisplayHomeAsUpSet = true; 
    this.mDecorToolbar.setDisplayOptions(paramInt);
  }
  
  public void setDisplayOptions(int paramInt1, int paramInt2) {
    int i = this.mDecorToolbar.getDisplayOptions();
    if ((paramInt2 & 0x4) != 0)
      this.mDisplayHomeAsUpSet = true; 
    this.mDecorToolbar.setDisplayOptions(paramInt1 & paramInt2 | paramInt2 & i);
  }
  
  public void setDisplayShowCustomEnabled(boolean paramBoolean) {
    boolean bool;
    if (paramBoolean) {
      bool = true;
    } else {
      bool = false;
    } 
    setDisplayOptions(bool, 16);
  }
  
  public void setDisplayShowHomeEnabled(boolean paramBoolean) {
    boolean bool;
    if (paramBoolean) {
      bool = true;
    } else {
      bool = false;
    } 
    setDisplayOptions(bool, 2);
  }
  
  public void setDisplayShowTitleEnabled(boolean paramBoolean) {
    boolean bool;
    if (paramBoolean) {
      bool = true;
    } else {
      bool = false;
    } 
    setDisplayOptions(bool, 8);
  }
  
  public void setDisplayUseLogoEnabled(boolean paramBoolean) {
    setDisplayOptions(paramBoolean, 1);
  }
  
  public void setElevation(float paramFloat) {
    ViewCompat.setElevation((View)this.mContainerView, paramFloat);
  }
  
  public void setHideOffset(int paramInt) {
    if (paramInt == 0 || this.mOverlayLayout.isInOverlayMode()) {
      this.mOverlayLayout.setActionBarHideOffset(paramInt);
      return;
    } 
    throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to set a non-zero hide offset");
  }
  
  public void setHideOnContentScrollEnabled(boolean paramBoolean) {
    if (!paramBoolean || this.mOverlayLayout.isInOverlayMode()) {
      this.mHideOnContentScroll = paramBoolean;
      this.mOverlayLayout.setHideOnContentScrollEnabled(paramBoolean);
      return;
    } 
    throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to enable hide on content scroll");
  }
  
  public void setHomeActionContentDescription(int paramInt) {
    this.mDecorToolbar.setNavigationContentDescription(paramInt);
  }
  
  public void setHomeActionContentDescription(CharSequence paramCharSequence) {
    this.mDecorToolbar.setNavigationContentDescription(paramCharSequence);
  }
  
  public void setHomeAsUpIndicator(int paramInt) {
    this.mDecorToolbar.setNavigationIcon(paramInt);
  }
  
  public void setHomeAsUpIndicator(Drawable paramDrawable) {
    this.mDecorToolbar.setNavigationIcon(paramDrawable);
  }
  
  public void setHomeButtonEnabled(boolean paramBoolean) {
    this.mDecorToolbar.setHomeButtonEnabled(paramBoolean);
  }
  
  public void setIcon(int paramInt) {
    this.mDecorToolbar.setIcon(paramInt);
  }
  
  public void setIcon(Drawable paramDrawable) {
    this.mDecorToolbar.setIcon(paramDrawable);
  }
  
  public void setListNavigationCallbacks(SpinnerAdapter paramSpinnerAdapter, ActionBar.OnNavigationListener paramOnNavigationListener) {
    this.mDecorToolbar.setDropdownParams(paramSpinnerAdapter, new NavItemSelectedListener(paramOnNavigationListener));
  }
  
  public void setLogo(int paramInt) {
    this.mDecorToolbar.setLogo(paramInt);
  }
  
  public void setLogo(Drawable paramDrawable) {
    this.mDecorToolbar.setLogo(paramDrawable);
  }
  
  public void setNavigationMode(int paramInt) {
    int i = this.mDecorToolbar.getNavigationMode();
    if (i == 2) {
      this.mSavedTabPosition = getSelectedNavigationIndex();
      selectTab(null);
      this.mTabScrollView.setVisibility(8);
    } 
    if (i != paramInt && !this.mHasEmbeddedTabs) {
      ActionBarOverlayLayout actionBarOverlayLayout1 = this.mOverlayLayout;
      if (actionBarOverlayLayout1 != null)
        ViewCompat.requestApplyInsets((View)actionBarOverlayLayout1); 
    } 
    this.mDecorToolbar.setNavigationMode(paramInt);
    boolean bool1 = false;
    if (paramInt == 2) {
      ensureTabsExist();
      this.mTabScrollView.setVisibility(0);
      i = this.mSavedTabPosition;
      if (i != -1) {
        setSelectedNavigationItem(i);
        this.mSavedTabPosition = -1;
      } 
    } 
    DecorToolbar decorToolbar = this.mDecorToolbar;
    if (paramInt == 2 && !this.mHasEmbeddedTabs) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    decorToolbar.setCollapsible(bool2);
    ActionBarOverlayLayout actionBarOverlayLayout = this.mOverlayLayout;
    boolean bool2 = bool1;
    if (paramInt == 2) {
      bool2 = bool1;
      if (!this.mHasEmbeddedTabs)
        bool2 = true; 
    } 
    actionBarOverlayLayout.setHasNonEmbeddedTabs(bool2);
  }
  
  public void setSelectedNavigationItem(int paramInt) {
    int i = this.mDecorToolbar.getNavigationMode();
    if (i != 1) {
      if (i == 2) {
        selectTab(this.mTabs.get(paramInt));
      } else {
        throw new IllegalStateException("setSelectedNavigationIndex not valid for current navigation mode");
      } 
    } else {
      this.mDecorToolbar.setDropdownSelectedPosition(paramInt);
    } 
  }
  
  public void setShowHideAnimationEnabled(boolean paramBoolean) {
    this.mShowHideAnimationEnabled = paramBoolean;
    if (!paramBoolean) {
      ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = this.mCurrentShowAnim;
      if (viewPropertyAnimatorCompatSet != null)
        viewPropertyAnimatorCompatSet.cancel(); 
    } 
  }
  
  public void setSplitBackgroundDrawable(Drawable paramDrawable) {}
  
  public void setStackedBackgroundDrawable(Drawable paramDrawable) {
    this.mContainerView.setStackedBackground(paramDrawable);
  }
  
  public void setSubtitle(int paramInt) {
    setSubtitle(this.mContext.getString(paramInt));
  }
  
  public void setSubtitle(CharSequence paramCharSequence) {
    this.mDecorToolbar.setSubtitle(paramCharSequence);
  }
  
  public void setTitle(int paramInt) {
    setTitle(this.mContext.getString(paramInt));
  }
  
  public void setTitle(CharSequence paramCharSequence) {
    this.mDecorToolbar.setTitle(paramCharSequence);
  }
  
  public void setWindowTitle(CharSequence paramCharSequence) {
    this.mDecorToolbar.setWindowTitle(paramCharSequence);
  }
  
  public void show() {
    if (this.mHiddenByApp) {
      this.mHiddenByApp = false;
      updateVisibility(false);
    } 
  }
  
  public void showForSystem() {
    if (this.mHiddenBySystem) {
      this.mHiddenBySystem = false;
      updateVisibility(true);
    } 
  }
  
  public ActionMode startActionMode(ActionMode.Callback paramCallback) {
    ActionModeImpl actionModeImpl2 = this.mActionMode;
    if (actionModeImpl2 != null)
      actionModeImpl2.finish(); 
    this.mOverlayLayout.setHideOnContentScrollEnabled(false);
    this.mContextView.killMode();
    ActionModeImpl actionModeImpl1 = new ActionModeImpl(this.mContextView.getContext(), paramCallback);
    if (actionModeImpl1.dispatchOnCreate()) {
      this.mActionMode = actionModeImpl1;
      actionModeImpl1.invalidate();
      this.mContextView.initForMode(actionModeImpl1);
      animateToMode(true);
      this.mContextView.sendAccessibilityEvent(32);
      return actionModeImpl1;
    } 
    return null;
  }
  
  public class ActionModeImpl extends ActionMode implements MenuBuilder.Callback {
    private final Context mActionModeContext;
    
    private ActionMode.Callback mCallback;
    
    private WeakReference<View> mCustomView;
    
    private final MenuBuilder mMenu;
    
    public ActionModeImpl(Context param1Context, ActionMode.Callback param1Callback) {
      this.mActionModeContext = param1Context;
      this.mCallback = param1Callback;
      MenuBuilder menuBuilder = (new MenuBuilder(param1Context)).setDefaultShowAsAction(1);
      this.mMenu = menuBuilder;
      menuBuilder.setCallback(this);
    }
    
    public boolean dispatchOnCreate() {
      this.mMenu.stopDispatchingItemsChanged();
      try {
        return this.mCallback.onCreateActionMode(this, (Menu)this.mMenu);
      } finally {
        this.mMenu.startDispatchingItemsChanged();
      } 
    }
    
    public void finish() {
      if (WindowDecorActionBar.this.mActionMode != this)
        return; 
      if (!WindowDecorActionBar.checkShowingFlags(WindowDecorActionBar.this.mHiddenByApp, WindowDecorActionBar.this.mHiddenBySystem, false)) {
        WindowDecorActionBar.this.mDeferredDestroyActionMode = this;
        WindowDecorActionBar.this.mDeferredModeDestroyCallback = this.mCallback;
      } else {
        this.mCallback.onDestroyActionMode(this);
      } 
      this.mCallback = null;
      WindowDecorActionBar.this.animateToMode(false);
      WindowDecorActionBar.this.mContextView.closeMode();
      WindowDecorActionBar.this.mDecorToolbar.getViewGroup().sendAccessibilityEvent(32);
      WindowDecorActionBar.this.mOverlayLayout.setHideOnContentScrollEnabled(WindowDecorActionBar.this.mHideOnContentScroll);
      WindowDecorActionBar.this.mActionMode = null;
    }
    
    public View getCustomView() {
      WeakReference<View> weakReference = this.mCustomView;
      if (weakReference != null) {
        View view = weakReference.get();
      } else {
        weakReference = null;
      } 
      return (View)weakReference;
    }
    
    public Menu getMenu() {
      return (Menu)this.mMenu;
    }
    
    public MenuInflater getMenuInflater() {
      return (MenuInflater)new SupportMenuInflater(this.mActionModeContext);
    }
    
    public CharSequence getSubtitle() {
      return WindowDecorActionBar.this.mContextView.getSubtitle();
    }
    
    public CharSequence getTitle() {
      return WindowDecorActionBar.this.mContextView.getTitle();
    }
    
    public void invalidate() {
      if (WindowDecorActionBar.this.mActionMode != this)
        return; 
      this.mMenu.stopDispatchingItemsChanged();
      try {
        this.mCallback.onPrepareActionMode(this, (Menu)this.mMenu);
        return;
      } finally {
        this.mMenu.startDispatchingItemsChanged();
      } 
    }
    
    public boolean isTitleOptional() {
      return WindowDecorActionBar.this.mContextView.isTitleOptional();
    }
    
    public void onCloseMenu(MenuBuilder param1MenuBuilder, boolean param1Boolean) {}
    
    public void onCloseSubMenu(SubMenuBuilder param1SubMenuBuilder) {}
    
    public boolean onMenuItemSelected(MenuBuilder param1MenuBuilder, MenuItem param1MenuItem) {
      ActionMode.Callback callback = this.mCallback;
      return (callback != null) ? callback.onActionItemClicked(this, param1MenuItem) : false;
    }
    
    public void onMenuModeChange(MenuBuilder param1MenuBuilder) {
      if (this.mCallback == null)
        return; 
      invalidate();
      WindowDecorActionBar.this.mContextView.showOverflowMenu();
    }
    
    public boolean onSubMenuSelected(SubMenuBuilder param1SubMenuBuilder) {
      if (this.mCallback == null)
        return false; 
      if (!param1SubMenuBuilder.hasVisibleItems())
        return true; 
      (new MenuPopupHelper(WindowDecorActionBar.this.getThemedContext(), (MenuBuilder)param1SubMenuBuilder)).show();
      return true;
    }
    
    public void setCustomView(View param1View) {
      WindowDecorActionBar.this.mContextView.setCustomView(param1View);
      this.mCustomView = new WeakReference<View>(param1View);
    }
    
    public void setSubtitle(int param1Int) {
      setSubtitle(WindowDecorActionBar.this.mContext.getResources().getString(param1Int));
    }
    
    public void setSubtitle(CharSequence param1CharSequence) {
      WindowDecorActionBar.this.mContextView.setSubtitle(param1CharSequence);
    }
    
    public void setTitle(int param1Int) {
      setTitle(WindowDecorActionBar.this.mContext.getResources().getString(param1Int));
    }
    
    public void setTitle(CharSequence param1CharSequence) {
      WindowDecorActionBar.this.mContextView.setTitle(param1CharSequence);
    }
    
    public void setTitleOptionalHint(boolean param1Boolean) {
      super.setTitleOptionalHint(param1Boolean);
      WindowDecorActionBar.this.mContextView.setTitleOptional(param1Boolean);
    }
  }
  
  public class TabImpl extends ActionBar.Tab {
    private ActionBar.TabListener mCallback;
    
    private CharSequence mContentDesc;
    
    private View mCustomView;
    
    private Drawable mIcon;
    
    private int mPosition = -1;
    
    private Object mTag;
    
    private CharSequence mText;
    
    public ActionBar.TabListener getCallback() {
      return this.mCallback;
    }
    
    public CharSequence getContentDescription() {
      return this.mContentDesc;
    }
    
    public View getCustomView() {
      return this.mCustomView;
    }
    
    public Drawable getIcon() {
      return this.mIcon;
    }
    
    public int getPosition() {
      return this.mPosition;
    }
    
    public Object getTag() {
      return this.mTag;
    }
    
    public CharSequence getText() {
      return this.mText;
    }
    
    public void select() {
      WindowDecorActionBar.this.selectTab(this);
    }
    
    public ActionBar.Tab setContentDescription(int param1Int) {
      return setContentDescription(WindowDecorActionBar.this.mContext.getResources().getText(param1Int));
    }
    
    public ActionBar.Tab setContentDescription(CharSequence param1CharSequence) {
      this.mContentDesc = param1CharSequence;
      if (this.mPosition >= 0)
        WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition); 
      return this;
    }
    
    public ActionBar.Tab setCustomView(int param1Int) {
      return setCustomView(LayoutInflater.from(WindowDecorActionBar.this.getThemedContext()).inflate(param1Int, null));
    }
    
    public ActionBar.Tab setCustomView(View param1View) {
      this.mCustomView = param1View;
      if (this.mPosition >= 0)
        WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition); 
      return this;
    }
    
    public ActionBar.Tab setIcon(int param1Int) {
      return setIcon(AppCompatResources.getDrawable(WindowDecorActionBar.this.mContext, param1Int));
    }
    
    public ActionBar.Tab setIcon(Drawable param1Drawable) {
      this.mIcon = param1Drawable;
      if (this.mPosition >= 0)
        WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition); 
      return this;
    }
    
    public void setPosition(int param1Int) {
      this.mPosition = param1Int;
    }
    
    public ActionBar.Tab setTabListener(ActionBar.TabListener param1TabListener) {
      this.mCallback = param1TabListener;
      return this;
    }
    
    public ActionBar.Tab setTag(Object param1Object) {
      this.mTag = param1Object;
      return this;
    }
    
    public ActionBar.Tab setText(int param1Int) {
      return setText(WindowDecorActionBar.this.mContext.getResources().getText(param1Int));
    }
    
    public ActionBar.Tab setText(CharSequence param1CharSequence) {
      this.mText = param1CharSequence;
      if (this.mPosition >= 0)
        WindowDecorActionBar.this.mTabScrollView.updateTab(this.mPosition); 
      return this;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\app\WindowDecorActionBar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */