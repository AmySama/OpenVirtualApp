package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ActionProvider;
import android.support.v7.appcompat.R;
import android.support.v7.view.ActionBarPolicy;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.view.menu.BaseMenuPresenter;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.support.v7.view.menu.ShowableListMenu;
import android.support.v7.view.menu.SubMenuBuilder;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

class ActionMenuPresenter extends BaseMenuPresenter implements ActionProvider.SubUiVisibilityListener {
  private static final String TAG = "ActionMenuPresenter";
  
  private final SparseBooleanArray mActionButtonGroups = new SparseBooleanArray();
  
  ActionButtonSubmenu mActionButtonPopup;
  
  private int mActionItemWidthLimit;
  
  private boolean mExpandedActionViewsExclusive;
  
  private int mMaxItems;
  
  private boolean mMaxItemsSet;
  
  private int mMinCellSize;
  
  int mOpenSubMenuId;
  
  OverflowMenuButton mOverflowButton;
  
  OverflowPopup mOverflowPopup;
  
  private Drawable mPendingOverflowIcon;
  
  private boolean mPendingOverflowIconSet;
  
  private ActionMenuPopupCallback mPopupCallback;
  
  final PopupPresenterCallback mPopupPresenterCallback = new PopupPresenterCallback();
  
  OpenOverflowRunnable mPostedOpenRunnable;
  
  private boolean mReserveOverflow;
  
  private boolean mReserveOverflowSet;
  
  private View mScrapActionButtonView;
  
  private boolean mStrictWidthLimit;
  
  private int mWidthLimit;
  
  private boolean mWidthLimitSet;
  
  public ActionMenuPresenter(Context paramContext) {
    super(paramContext, R.layout.abc_action_menu_layout, R.layout.abc_action_menu_item_layout);
  }
  
  private View findViewForItem(MenuItem paramMenuItem) {
    ViewGroup viewGroup = (ViewGroup)this.mMenuView;
    if (viewGroup == null)
      return null; 
    int i = viewGroup.getChildCount();
    for (byte b = 0; b < i; b++) {
      View view = viewGroup.getChildAt(b);
      if (view instanceof MenuView.ItemView && ((MenuView.ItemView)view).getItemData() == paramMenuItem)
        return view; 
    } 
    return null;
  }
  
  public void bindItemView(MenuItemImpl paramMenuItemImpl, MenuView.ItemView paramItemView) {
    paramItemView.initialize(paramMenuItemImpl, 0);
    ActionMenuView actionMenuView = (ActionMenuView)this.mMenuView;
    ActionMenuItemView actionMenuItemView = (ActionMenuItemView)paramItemView;
    actionMenuItemView.setItemInvoker(actionMenuView);
    if (this.mPopupCallback == null)
      this.mPopupCallback = new ActionMenuPopupCallback(); 
    actionMenuItemView.setPopupCallback(this.mPopupCallback);
  }
  
  public boolean dismissPopupMenus() {
    return hideOverflowMenu() | hideSubMenus();
  }
  
  public boolean filterLeftoverView(ViewGroup paramViewGroup, int paramInt) {
    return (paramViewGroup.getChildAt(paramInt) == this.mOverflowButton) ? false : super.filterLeftoverView(paramViewGroup, paramInt);
  }
  
  public boolean flagActionItems() {
    // Byte code:
    //   0: aload_0
    //   1: astore_1
    //   2: aload_1
    //   3: getfield mMenu : Landroid/support/v7/view/menu/MenuBuilder;
    //   6: ifnull -> 25
    //   9: aload_1
    //   10: getfield mMenu : Landroid/support/v7/view/menu/MenuBuilder;
    //   13: invokevirtual getVisibleItems : ()Ljava/util/ArrayList;
    //   16: astore_2
    //   17: aload_2
    //   18: invokevirtual size : ()I
    //   21: istore_3
    //   22: goto -> 29
    //   25: aconst_null
    //   26: astore_2
    //   27: iconst_0
    //   28: istore_3
    //   29: aload_1
    //   30: getfield mMaxItems : I
    //   33: istore #4
    //   35: aload_1
    //   36: getfield mActionItemWidthLimit : I
    //   39: istore #5
    //   41: iconst_0
    //   42: iconst_0
    //   43: invokestatic makeMeasureSpec : (II)I
    //   46: istore #6
    //   48: aload_1
    //   49: getfield mMenuView : Landroid/support/v7/view/menu/MenuView;
    //   52: checkcast android/view/ViewGroup
    //   55: astore #7
    //   57: iconst_0
    //   58: istore #8
    //   60: iconst_0
    //   61: istore #9
    //   63: iconst_0
    //   64: istore #10
    //   66: iconst_0
    //   67: istore #11
    //   69: iload #8
    //   71: iload_3
    //   72: if_icmpge -> 153
    //   75: aload_2
    //   76: iload #8
    //   78: invokevirtual get : (I)Ljava/lang/Object;
    //   81: checkcast android/support/v7/view/menu/MenuItemImpl
    //   84: astore #12
    //   86: aload #12
    //   88: invokevirtual requiresActionButton : ()Z
    //   91: ifeq -> 100
    //   94: iinc #10, 1
    //   97: goto -> 117
    //   100: aload #12
    //   102: invokevirtual requestsActionButton : ()Z
    //   105: ifeq -> 114
    //   108: iinc #11, 1
    //   111: goto -> 117
    //   114: iconst_1
    //   115: istore #9
    //   117: iload #4
    //   119: istore #13
    //   121: aload_1
    //   122: getfield mExpandedActionViewsExclusive : Z
    //   125: ifeq -> 143
    //   128: iload #4
    //   130: istore #13
    //   132: aload #12
    //   134: invokevirtual isActionViewExpanded : ()Z
    //   137: ifeq -> 143
    //   140: iconst_0
    //   141: istore #13
    //   143: iinc #8, 1
    //   146: iload #13
    //   148: istore #4
    //   150: goto -> 69
    //   153: iload #4
    //   155: istore #8
    //   157: aload_1
    //   158: getfield mReserveOverflow : Z
    //   161: ifeq -> 189
    //   164: iload #9
    //   166: ifne -> 183
    //   169: iload #4
    //   171: istore #8
    //   173: iload #11
    //   175: iload #10
    //   177: iadd
    //   178: iload #4
    //   180: if_icmple -> 189
    //   183: iload #4
    //   185: iconst_1
    //   186: isub
    //   187: istore #8
    //   189: iload #8
    //   191: iload #10
    //   193: isub
    //   194: istore #4
    //   196: aload_1
    //   197: getfield mActionButtonGroups : Landroid/util/SparseBooleanArray;
    //   200: astore #12
    //   202: aload #12
    //   204: invokevirtual clear : ()V
    //   207: aload_1
    //   208: getfield mStrictWidthLimit : Z
    //   211: ifeq -> 243
    //   214: aload_1
    //   215: getfield mMinCellSize : I
    //   218: istore #11
    //   220: iload #5
    //   222: iload #11
    //   224: idiv
    //   225: istore #10
    //   227: iload #11
    //   229: iload #5
    //   231: iload #11
    //   233: irem
    //   234: iload #10
    //   236: idiv
    //   237: iadd
    //   238: istore #9
    //   240: goto -> 249
    //   243: iconst_0
    //   244: istore #9
    //   246: iconst_0
    //   247: istore #10
    //   249: iconst_0
    //   250: istore #13
    //   252: iconst_0
    //   253: istore #11
    //   255: iload #5
    //   257: istore #8
    //   259: iload_3
    //   260: istore #5
    //   262: aload_0
    //   263: astore_1
    //   264: iload #13
    //   266: iload #5
    //   268: if_icmpge -> 767
    //   271: aload_2
    //   272: iload #13
    //   274: invokevirtual get : (I)Ljava/lang/Object;
    //   277: checkcast android/support/v7/view/menu/MenuItemImpl
    //   280: astore #14
    //   282: aload #14
    //   284: invokevirtual requiresActionButton : ()Z
    //   287: ifeq -> 410
    //   290: aload_1
    //   291: aload #14
    //   293: aload_1
    //   294: getfield mScrapActionButtonView : Landroid/view/View;
    //   297: aload #7
    //   299: invokevirtual getItemView : (Landroid/support/v7/view/menu/MenuItemImpl;Landroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;
    //   302: astore #15
    //   304: aload_1
    //   305: getfield mScrapActionButtonView : Landroid/view/View;
    //   308: ifnonnull -> 317
    //   311: aload_1
    //   312: aload #15
    //   314: putfield mScrapActionButtonView : Landroid/view/View;
    //   317: aload_1
    //   318: getfield mStrictWidthLimit : Z
    //   321: ifeq -> 344
    //   324: iload #10
    //   326: aload #15
    //   328: iload #9
    //   330: iload #10
    //   332: iload #6
    //   334: iconst_0
    //   335: invokestatic measureChildForCells : (Landroid/view/View;IIII)I
    //   338: isub
    //   339: istore #10
    //   341: goto -> 353
    //   344: aload #15
    //   346: iload #6
    //   348: iload #6
    //   350: invokevirtual measure : (II)V
    //   353: aload #15
    //   355: invokevirtual getMeasuredWidth : ()I
    //   358: istore #16
    //   360: iload #8
    //   362: iload #16
    //   364: isub
    //   365: istore #8
    //   367: iload #11
    //   369: istore_3
    //   370: iload #11
    //   372: ifne -> 378
    //   375: iload #16
    //   377: istore_3
    //   378: aload #14
    //   380: invokevirtual getGroupId : ()I
    //   383: istore #11
    //   385: iload #11
    //   387: ifeq -> 398
    //   390: aload #12
    //   392: iload #11
    //   394: iconst_1
    //   395: invokevirtual put : (IZ)V
    //   398: aload #14
    //   400: iconst_1
    //   401: invokevirtual setIsActionButton : (Z)V
    //   404: iload_3
    //   405: istore #11
    //   407: goto -> 761
    //   410: aload #14
    //   412: invokevirtual requestsActionButton : ()Z
    //   415: ifeq -> 755
    //   418: aload #14
    //   420: invokevirtual getGroupId : ()I
    //   423: istore #17
    //   425: aload #12
    //   427: iload #17
    //   429: invokevirtual get : (I)Z
    //   432: istore #18
    //   434: iload #4
    //   436: ifgt -> 444
    //   439: iload #18
    //   441: ifeq -> 467
    //   444: iload #8
    //   446: ifle -> 467
    //   449: aload_1
    //   450: getfield mStrictWidthLimit : Z
    //   453: ifeq -> 461
    //   456: iload #10
    //   458: ifle -> 467
    //   461: iconst_1
    //   462: istore #19
    //   464: goto -> 470
    //   467: iconst_0
    //   468: istore #19
    //   470: iload #19
    //   472: istore #20
    //   474: iload #19
    //   476: ifeq -> 628
    //   479: aload_1
    //   480: aload #14
    //   482: aload_1
    //   483: getfield mScrapActionButtonView : Landroid/view/View;
    //   486: aload #7
    //   488: invokevirtual getItemView : (Landroid/support/v7/view/menu/MenuItemImpl;Landroid/view/View;Landroid/view/ViewGroup;)Landroid/view/View;
    //   491: astore #15
    //   493: aload_1
    //   494: getfield mScrapActionButtonView : Landroid/view/View;
    //   497: ifnonnull -> 506
    //   500: aload_1
    //   501: aload #15
    //   503: putfield mScrapActionButtonView : Landroid/view/View;
    //   506: aload_1
    //   507: getfield mStrictWidthLimit : Z
    //   510: ifeq -> 550
    //   513: aload #15
    //   515: iload #9
    //   517: iload #10
    //   519: iload #6
    //   521: iconst_0
    //   522: invokestatic measureChildForCells : (Landroid/view/View;IIII)I
    //   525: istore #16
    //   527: iload #10
    //   529: iload #16
    //   531: isub
    //   532: istore_3
    //   533: iload_3
    //   534: istore #10
    //   536: iload #16
    //   538: ifne -> 559
    //   541: iconst_0
    //   542: istore #20
    //   544: iload_3
    //   545: istore #10
    //   547: goto -> 559
    //   550: aload #15
    //   552: iload #6
    //   554: iload #6
    //   556: invokevirtual measure : (II)V
    //   559: aload #15
    //   561: invokevirtual getMeasuredWidth : ()I
    //   564: istore #16
    //   566: iload #8
    //   568: iload #16
    //   570: isub
    //   571: istore #8
    //   573: iload #11
    //   575: istore_3
    //   576: iload #11
    //   578: ifne -> 584
    //   581: iload #16
    //   583: istore_3
    //   584: aload_1
    //   585: getfield mStrictWidthLimit : Z
    //   588: ifeq -> 599
    //   591: iload #8
    //   593: iflt -> 612
    //   596: goto -> 606
    //   599: iload #8
    //   601: iload_3
    //   602: iadd
    //   603: ifle -> 612
    //   606: iconst_1
    //   607: istore #11
    //   609: goto -> 615
    //   612: iconst_0
    //   613: istore #11
    //   615: iload #20
    //   617: iload #11
    //   619: iand
    //   620: istore #19
    //   622: iload_3
    //   623: istore #11
    //   625: goto -> 628
    //   628: iload #19
    //   630: ifeq -> 652
    //   633: iload #17
    //   635: ifeq -> 652
    //   638: aload #12
    //   640: iload #17
    //   642: iconst_1
    //   643: invokevirtual put : (IZ)V
    //   646: iload #4
    //   648: istore_3
    //   649: goto -> 732
    //   652: iload #4
    //   654: istore_3
    //   655: iload #18
    //   657: ifeq -> 732
    //   660: aload #12
    //   662: iload #17
    //   664: iconst_0
    //   665: invokevirtual put : (IZ)V
    //   668: iconst_0
    //   669: istore #16
    //   671: iload #4
    //   673: istore_3
    //   674: iload #16
    //   676: iload #13
    //   678: if_icmpge -> 732
    //   681: aload_2
    //   682: iload #16
    //   684: invokevirtual get : (I)Ljava/lang/Object;
    //   687: checkcast android/support/v7/view/menu/MenuItemImpl
    //   690: astore_1
    //   691: iload #4
    //   693: istore_3
    //   694: aload_1
    //   695: invokevirtual getGroupId : ()I
    //   698: iload #17
    //   700: if_icmpne -> 723
    //   703: iload #4
    //   705: istore_3
    //   706: aload_1
    //   707: invokevirtual isActionButton : ()Z
    //   710: ifeq -> 718
    //   713: iload #4
    //   715: iconst_1
    //   716: iadd
    //   717: istore_3
    //   718: aload_1
    //   719: iconst_0
    //   720: invokevirtual setIsActionButton : (Z)V
    //   723: iinc #16, 1
    //   726: iload_3
    //   727: istore #4
    //   729: goto -> 671
    //   732: iload_3
    //   733: istore #4
    //   735: iload #19
    //   737: ifeq -> 745
    //   740: iload_3
    //   741: iconst_1
    //   742: isub
    //   743: istore #4
    //   745: aload #14
    //   747: iload #19
    //   749: invokevirtual setIsActionButton : (Z)V
    //   752: goto -> 407
    //   755: aload #14
    //   757: iconst_0
    //   758: invokevirtual setIsActionButton : (Z)V
    //   761: iinc #13, 1
    //   764: goto -> 262
    //   767: iconst_1
    //   768: ireturn
  }
  
  public View getItemView(MenuItemImpl paramMenuItemImpl, View paramView, ViewGroup paramViewGroup) {
    boolean bool;
    View view = paramMenuItemImpl.getActionView();
    if (view == null || paramMenuItemImpl.hasCollapsibleActionView())
      view = super.getItemView(paramMenuItemImpl, paramView, paramViewGroup); 
    if (paramMenuItemImpl.isActionViewExpanded()) {
      bool = true;
    } else {
      bool = false;
    } 
    view.setVisibility(bool);
    ActionMenuView actionMenuView = (ActionMenuView)paramViewGroup;
    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
    if (!actionMenuView.checkLayoutParams(layoutParams))
      view.setLayoutParams((ViewGroup.LayoutParams)actionMenuView.generateLayoutParams(layoutParams)); 
    return view;
  }
  
  public MenuView getMenuView(ViewGroup paramViewGroup) {
    MenuView menuView2 = this.mMenuView;
    MenuView menuView1 = super.getMenuView(paramViewGroup);
    if (menuView2 != menuView1)
      ((ActionMenuView)menuView1).setPresenter(this); 
    return menuView1;
  }
  
  public Drawable getOverflowIcon() {
    OverflowMenuButton overflowMenuButton = this.mOverflowButton;
    return (overflowMenuButton != null) ? overflowMenuButton.getDrawable() : (this.mPendingOverflowIconSet ? this.mPendingOverflowIcon : null);
  }
  
  public boolean hideOverflowMenu() {
    if (this.mPostedOpenRunnable != null && this.mMenuView != null) {
      ((View)this.mMenuView).removeCallbacks(this.mPostedOpenRunnable);
      this.mPostedOpenRunnable = null;
      return true;
    } 
    OverflowPopup overflowPopup = this.mOverflowPopup;
    if (overflowPopup != null) {
      overflowPopup.dismiss();
      return true;
    } 
    return false;
  }
  
  public boolean hideSubMenus() {
    ActionButtonSubmenu actionButtonSubmenu = this.mActionButtonPopup;
    if (actionButtonSubmenu != null) {
      actionButtonSubmenu.dismiss();
      return true;
    } 
    return false;
  }
  
  public void initForMenu(Context paramContext, MenuBuilder paramMenuBuilder) {
    super.initForMenu(paramContext, paramMenuBuilder);
    Resources resources = paramContext.getResources();
    ActionBarPolicy actionBarPolicy = ActionBarPolicy.get(paramContext);
    if (!this.mReserveOverflowSet)
      this.mReserveOverflow = actionBarPolicy.showsOverflowMenuButton(); 
    if (!this.mWidthLimitSet)
      this.mWidthLimit = actionBarPolicy.getEmbeddedMenuWidthLimit(); 
    if (!this.mMaxItemsSet)
      this.mMaxItems = actionBarPolicy.getMaxActionButtons(); 
    int i = this.mWidthLimit;
    if (this.mReserveOverflow) {
      if (this.mOverflowButton == null) {
        OverflowMenuButton overflowMenuButton = new OverflowMenuButton(this.mSystemContext);
        this.mOverflowButton = overflowMenuButton;
        if (this.mPendingOverflowIconSet) {
          overflowMenuButton.setImageDrawable(this.mPendingOverflowIcon);
          this.mPendingOverflowIcon = null;
          this.mPendingOverflowIconSet = false;
        } 
        int j = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mOverflowButton.measure(j, j);
      } 
      i -= this.mOverflowButton.getMeasuredWidth();
    } else {
      this.mOverflowButton = null;
    } 
    this.mActionItemWidthLimit = i;
    this.mMinCellSize = (int)((resources.getDisplayMetrics()).density * 56.0F);
    this.mScrapActionButtonView = null;
  }
  
  public boolean isOverflowMenuShowPending() {
    return (this.mPostedOpenRunnable != null || isOverflowMenuShowing());
  }
  
  public boolean isOverflowMenuShowing() {
    boolean bool;
    OverflowPopup overflowPopup = this.mOverflowPopup;
    if (overflowPopup != null && overflowPopup.isShowing()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isOverflowReserved() {
    return this.mReserveOverflow;
  }
  
  public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean) {
    dismissPopupMenus();
    super.onCloseMenu(paramMenuBuilder, paramBoolean);
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration) {
    if (!this.mMaxItemsSet)
      this.mMaxItems = ActionBarPolicy.get(this.mContext).getMaxActionButtons(); 
    if (this.mMenu != null)
      this.mMenu.onItemsChanged(true); 
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable) {
    if (!(paramParcelable instanceof SavedState))
      return; 
    paramParcelable = paramParcelable;
    if (((SavedState)paramParcelable).openSubMenuId > 0) {
      MenuItem menuItem = this.mMenu.findItem(((SavedState)paramParcelable).openSubMenuId);
      if (menuItem != null)
        onSubMenuSelected((SubMenuBuilder)menuItem.getSubMenu()); 
    } 
  }
  
  public Parcelable onSaveInstanceState() {
    SavedState savedState = new SavedState();
    savedState.openSubMenuId = this.mOpenSubMenuId;
    return savedState;
  }
  
  public boolean onSubMenuSelected(SubMenuBuilder paramSubMenuBuilder) {
    boolean bool = paramSubMenuBuilder.hasVisibleItems();
    boolean bool1 = false;
    if (!bool)
      return false; 
    SubMenuBuilder subMenuBuilder;
    for (subMenuBuilder = paramSubMenuBuilder; subMenuBuilder.getParentMenu() != this.mMenu; subMenuBuilder = (SubMenuBuilder)subMenuBuilder.getParentMenu());
    View view = findViewForItem(subMenuBuilder.getItem());
    if (view == null)
      return false; 
    this.mOpenSubMenuId = paramSubMenuBuilder.getItem().getItemId();
    int i = paramSubMenuBuilder.size();
    byte b = 0;
    while (true) {
      bool = bool1;
      if (b < i) {
        MenuItem menuItem = paramSubMenuBuilder.getItem(b);
        if (menuItem.isVisible() && menuItem.getIcon() != null) {
          bool = true;
          break;
        } 
        b++;
        continue;
      } 
      break;
    } 
    ActionButtonSubmenu actionButtonSubmenu = new ActionButtonSubmenu(this.mContext, paramSubMenuBuilder, view);
    this.mActionButtonPopup = actionButtonSubmenu;
    actionButtonSubmenu.setForceShowIcon(bool);
    this.mActionButtonPopup.show();
    super.onSubMenuSelected(paramSubMenuBuilder);
    return true;
  }
  
  public void onSubUiVisibilityChanged(boolean paramBoolean) {
    if (paramBoolean) {
      super.onSubMenuSelected(null);
    } else if (this.mMenu != null) {
      this.mMenu.close(false);
    } 
  }
  
  public void setExpandedActionViewsExclusive(boolean paramBoolean) {
    this.mExpandedActionViewsExclusive = paramBoolean;
  }
  
  public void setItemLimit(int paramInt) {
    this.mMaxItems = paramInt;
    this.mMaxItemsSet = true;
  }
  
  public void setMenuView(ActionMenuView paramActionMenuView) {
    this.mMenuView = paramActionMenuView;
    paramActionMenuView.initialize(this.mMenu);
  }
  
  public void setOverflowIcon(Drawable paramDrawable) {
    OverflowMenuButton overflowMenuButton = this.mOverflowButton;
    if (overflowMenuButton != null) {
      overflowMenuButton.setImageDrawable(paramDrawable);
    } else {
      this.mPendingOverflowIconSet = true;
      this.mPendingOverflowIcon = paramDrawable;
    } 
  }
  
  public void setReserveOverflow(boolean paramBoolean) {
    this.mReserveOverflow = paramBoolean;
    this.mReserveOverflowSet = true;
  }
  
  public void setWidthLimit(int paramInt, boolean paramBoolean) {
    this.mWidthLimit = paramInt;
    this.mStrictWidthLimit = paramBoolean;
    this.mWidthLimitSet = true;
  }
  
  public boolean shouldIncludeItem(int paramInt, MenuItemImpl paramMenuItemImpl) {
    return paramMenuItemImpl.isActionButton();
  }
  
  public boolean showOverflowMenu() {
    if (this.mReserveOverflow && !isOverflowMenuShowing() && this.mMenu != null && this.mMenuView != null && this.mPostedOpenRunnable == null && !this.mMenu.getNonActionItems().isEmpty()) {
      this.mPostedOpenRunnable = new OpenOverflowRunnable(new OverflowPopup(this.mContext, this.mMenu, (View)this.mOverflowButton, true));
      ((View)this.mMenuView).post(this.mPostedOpenRunnable);
      super.onSubMenuSelected(null);
      return true;
    } 
    return false;
  }
  
  public void updateMenuView(boolean paramBoolean) {
    super.updateMenuView(paramBoolean);
    ((View)this.mMenuView).requestLayout();
    MenuBuilder<MenuItemImpl> menuBuilder = this.mMenu;
    byte b = 0;
    if (menuBuilder != null) {
      ArrayList<MenuItemImpl> arrayList = this.mMenu.getActionItems();
      int j = arrayList.size();
      for (byte b1 = 0; b1 < j; b1++) {
        ActionProvider actionProvider = ((MenuItemImpl)arrayList.get(b1)).getSupportActionProvider();
        if (actionProvider != null)
          actionProvider.setSubUiVisibilityListener(this); 
      } 
    } 
    if (this.mMenu != null) {
      ArrayList arrayList = this.mMenu.getNonActionItems();
    } else {
      menuBuilder = null;
    } 
    int i = b;
    if (this.mReserveOverflow) {
      i = b;
      if (menuBuilder != null) {
        int j = menuBuilder.size();
        if (j == 1) {
          i = ((MenuItemImpl)menuBuilder.get(0)).isActionViewExpanded() ^ true;
        } else {
          i = b;
          if (j > 0)
            i = 1; 
        } 
      } 
    } 
    if (i != 0) {
      if (this.mOverflowButton == null)
        this.mOverflowButton = new OverflowMenuButton(this.mSystemContext); 
      ViewGroup viewGroup = (ViewGroup)this.mOverflowButton.getParent();
      if (viewGroup != this.mMenuView) {
        if (viewGroup != null)
          viewGroup.removeView((View)this.mOverflowButton); 
        viewGroup = (ActionMenuView)this.mMenuView;
        viewGroup.addView((View)this.mOverflowButton, (ViewGroup.LayoutParams)viewGroup.generateOverflowButtonLayoutParams());
      } 
    } else {
      OverflowMenuButton overflowMenuButton = this.mOverflowButton;
      if (overflowMenuButton != null && overflowMenuButton.getParent() == this.mMenuView)
        ((ViewGroup)this.mMenuView).removeView((View)this.mOverflowButton); 
    } 
    ((ActionMenuView)this.mMenuView).setOverflowReserved(this.mReserveOverflow);
  }
  
  private class ActionButtonSubmenu extends MenuPopupHelper {
    public ActionButtonSubmenu(Context param1Context, SubMenuBuilder param1SubMenuBuilder, View param1View) {
      super(param1Context, (MenuBuilder)param1SubMenuBuilder, param1View, false, R.attr.actionOverflowMenuStyle);
      if (!((MenuItemImpl)param1SubMenuBuilder.getItem()).isActionButton()) {
        ActionMenuPresenter.OverflowMenuButton overflowMenuButton;
        if (ActionMenuPresenter.this.mOverflowButton == null) {
          View view = (View)ActionMenuPresenter.this.mMenuView;
        } else {
          overflowMenuButton = ActionMenuPresenter.this.mOverflowButton;
        } 
        setAnchorView((View)overflowMenuButton);
      } 
      setPresenterCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
    }
    
    protected void onDismiss() {
      ActionMenuPresenter.this.mActionButtonPopup = null;
      ActionMenuPresenter.this.mOpenSubMenuId = 0;
      super.onDismiss();
    }
  }
  
  private class ActionMenuPopupCallback extends ActionMenuItemView.PopupCallback {
    public ShowableListMenu getPopup() {
      ShowableListMenu showableListMenu;
      if (ActionMenuPresenter.this.mActionButtonPopup != null) {
        showableListMenu = (ShowableListMenu)ActionMenuPresenter.this.mActionButtonPopup.getPopup();
      } else {
        showableListMenu = null;
      } 
      return showableListMenu;
    }
  }
  
  private class OpenOverflowRunnable implements Runnable {
    private ActionMenuPresenter.OverflowPopup mPopup;
    
    public OpenOverflowRunnable(ActionMenuPresenter.OverflowPopup param1OverflowPopup) {
      this.mPopup = param1OverflowPopup;
    }
    
    public void run() {
      if (ActionMenuPresenter.this.mMenu != null)
        ActionMenuPresenter.this.mMenu.changeMenuMode(); 
      View view = (View)ActionMenuPresenter.this.mMenuView;
      if (view != null && view.getWindowToken() != null && this.mPopup.tryShow())
        ActionMenuPresenter.this.mOverflowPopup = this.mPopup; 
      ActionMenuPresenter.this.mPostedOpenRunnable = null;
    }
  }
  
  private class OverflowMenuButton extends AppCompatImageView implements ActionMenuView.ActionMenuChildView {
    private final float[] mTempPts = new float[2];
    
    public OverflowMenuButton(Context param1Context) {
      super(param1Context, (AttributeSet)null, R.attr.actionOverflowButtonStyle);
      setClickable(true);
      setFocusable(true);
      setVisibility(0);
      setEnabled(true);
      TooltipCompat.setTooltipText((View)this, getContentDescription());
      setOnTouchListener(new ForwardingListener((View)this) {
            public ShowableListMenu getPopup() {
              return (ShowableListMenu)((ActionMenuPresenter.this.mOverflowPopup == null) ? null : ActionMenuPresenter.this.mOverflowPopup.getPopup());
            }
            
            public boolean onForwardingStarted() {
              ActionMenuPresenter.this.showOverflowMenu();
              return true;
            }
            
            public boolean onForwardingStopped() {
              if (ActionMenuPresenter.this.mPostedOpenRunnable != null)
                return false; 
              ActionMenuPresenter.this.hideOverflowMenu();
              return true;
            }
          });
    }
    
    public boolean needsDividerAfter() {
      return false;
    }
    
    public boolean needsDividerBefore() {
      return false;
    }
    
    public boolean performClick() {
      if (super.performClick())
        return true; 
      playSoundEffect(0);
      ActionMenuPresenter.this.showOverflowMenu();
      return true;
    }
    
    protected boolean setFrame(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      boolean bool = super.setFrame(param1Int1, param1Int2, param1Int3, param1Int4);
      Drawable drawable1 = getDrawable();
      Drawable drawable2 = getBackground();
      if (drawable1 != null && drawable2 != null) {
        int i = getWidth();
        param1Int4 = getHeight();
        param1Int1 = Math.max(i, param1Int4) / 2;
        int j = getPaddingLeft();
        int k = getPaddingRight();
        param1Int3 = getPaddingTop();
        param1Int2 = getPaddingBottom();
        k = (i + j - k) / 2;
        param1Int2 = (param1Int4 + param1Int3 - param1Int2) / 2;
        DrawableCompat.setHotspotBounds(drawable2, k - param1Int1, param1Int2 - param1Int1, k + param1Int1, param1Int2 + param1Int1);
      } 
      return bool;
    }
  }
  
  class null extends ForwardingListener {
    null(View param1View) {
      super(param1View);
    }
    
    public ShowableListMenu getPopup() {
      return (ShowableListMenu)((ActionMenuPresenter.this.mOverflowPopup == null) ? null : ActionMenuPresenter.this.mOverflowPopup.getPopup());
    }
    
    public boolean onForwardingStarted() {
      ActionMenuPresenter.this.showOverflowMenu();
      return true;
    }
    
    public boolean onForwardingStopped() {
      if (ActionMenuPresenter.this.mPostedOpenRunnable != null)
        return false; 
      ActionMenuPresenter.this.hideOverflowMenu();
      return true;
    }
  }
  
  private class OverflowPopup extends MenuPopupHelper {
    public OverflowPopup(Context param1Context, MenuBuilder param1MenuBuilder, View param1View, boolean param1Boolean) {
      super(param1Context, param1MenuBuilder, param1View, param1Boolean, R.attr.actionOverflowMenuStyle);
      setGravity(8388613);
      setPresenterCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
    }
    
    protected void onDismiss() {
      if (ActionMenuPresenter.this.mMenu != null)
        ActionMenuPresenter.this.mMenu.close(); 
      ActionMenuPresenter.this.mOverflowPopup = null;
      super.onDismiss();
    }
  }
  
  private class PopupPresenterCallback implements MenuPresenter.Callback {
    public void onCloseMenu(MenuBuilder param1MenuBuilder, boolean param1Boolean) {
      if (param1MenuBuilder instanceof SubMenuBuilder)
        param1MenuBuilder.getRootMenu().close(false); 
      MenuPresenter.Callback callback = ActionMenuPresenter.this.getCallback();
      if (callback != null)
        callback.onCloseMenu(param1MenuBuilder, param1Boolean); 
    }
    
    public boolean onOpenSubMenu(MenuBuilder param1MenuBuilder) {
      boolean bool = false;
      if (param1MenuBuilder == null)
        return false; 
      ActionMenuPresenter.this.mOpenSubMenuId = ((SubMenuBuilder)param1MenuBuilder).getItem().getItemId();
      MenuPresenter.Callback callback = ActionMenuPresenter.this.getCallback();
      if (callback != null)
        bool = callback.onOpenSubMenu(param1MenuBuilder); 
      return bool;
    }
  }
  
  private static class SavedState implements Parcelable {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
        public ActionMenuPresenter.SavedState createFromParcel(Parcel param2Parcel) {
          return new ActionMenuPresenter.SavedState(param2Parcel);
        }
        
        public ActionMenuPresenter.SavedState[] newArray(int param2Int) {
          return new ActionMenuPresenter.SavedState[param2Int];
        }
      };
    
    public int openSubMenuId;
    
    SavedState() {}
    
    SavedState(Parcel param1Parcel) {
      this.openSubMenuId = param1Parcel.readInt();
    }
    
    public int describeContents() {
      return 0;
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      param1Parcel.writeInt(this.openSubMenuId);
    }
  }
  
  static final class null implements Parcelable.Creator<SavedState> {
    public ActionMenuPresenter.SavedState createFromParcel(Parcel param1Parcel) {
      return new ActionMenuPresenter.SavedState(param1Parcel);
    }
    
    public ActionMenuPresenter.SavedState[] newArray(int param1Int) {
      return new ActionMenuPresenter.SavedState[param1Int];
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\ActionMenuPresenter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */