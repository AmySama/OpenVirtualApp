package android.support.v7.view.menu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.widget.MenuItemHoverListener;
import android.support.v7.widget.MenuPopupWindow;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ListView;
import android.widget.PopupWindow;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

final class CascadingMenuPopup extends MenuPopup implements MenuPresenter, View.OnKeyListener, PopupWindow.OnDismissListener {
  static final int HORIZ_POSITION_LEFT = 0;
  
  static final int HORIZ_POSITION_RIGHT = 1;
  
  static final int SUBMENU_TIMEOUT_MS = 200;
  
  private View mAnchorView;
  
  private final View.OnAttachStateChangeListener mAttachStateChangeListener = new View.OnAttachStateChangeListener() {
      public void onViewAttachedToWindow(View param1View) {}
      
      public void onViewDetachedFromWindow(View param1View) {
        if (CascadingMenuPopup.this.mTreeObserver != null) {
          if (!CascadingMenuPopup.this.mTreeObserver.isAlive())
            CascadingMenuPopup.access$002(CascadingMenuPopup.this, param1View.getViewTreeObserver()); 
          CascadingMenuPopup.this.mTreeObserver.removeGlobalOnLayoutListener(CascadingMenuPopup.this.mGlobalLayoutListener);
        } 
        param1View.removeOnAttachStateChangeListener(this);
      }
    };
  
  private final Context mContext;
  
  private int mDropDownGravity = 0;
  
  private boolean mForceShowIcon;
  
  private final ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
      public void onGlobalLayout() {
        if (CascadingMenuPopup.this.isShowing() && CascadingMenuPopup.this.mShowingMenus.size() > 0 && !((CascadingMenuPopup.CascadingMenuInfo)CascadingMenuPopup.this.mShowingMenus.get(0)).window.isModal()) {
          View view = CascadingMenuPopup.this.mShownAnchorView;
          if (view == null || !view.isShown()) {
            CascadingMenuPopup.this.dismiss();
            return;
          } 
          Iterator<CascadingMenuPopup.CascadingMenuInfo> iterator = CascadingMenuPopup.this.mShowingMenus.iterator();
          while (iterator.hasNext())
            ((CascadingMenuPopup.CascadingMenuInfo)iterator.next()).window.show(); 
        } 
      }
    };
  
  private boolean mHasXOffset;
  
  private boolean mHasYOffset;
  
  private int mLastPosition;
  
  private final MenuItemHoverListener mMenuItemHoverListener = new MenuItemHoverListener() {
      public void onItemHoverEnter(MenuBuilder param1MenuBuilder, MenuItem param1MenuItem) {
        // Byte code:
        //   0: aload_0
        //   1: getfield this$0 : Landroid/support/v7/view/menu/CascadingMenuPopup;
        //   4: getfield mSubMenuHoverHandler : Landroid/os/Handler;
        //   7: astore_3
        //   8: aconst_null
        //   9: astore #4
        //   11: aload_3
        //   12: aconst_null
        //   13: invokevirtual removeCallbacksAndMessages : (Ljava/lang/Object;)V
        //   16: aload_0
        //   17: getfield this$0 : Landroid/support/v7/view/menu/CascadingMenuPopup;
        //   20: getfield mShowingMenus : Ljava/util/List;
        //   23: invokeinterface size : ()I
        //   28: istore #5
        //   30: iconst_0
        //   31: istore #6
        //   33: iload #6
        //   35: iload #5
        //   37: if_icmpge -> 73
        //   40: aload_1
        //   41: aload_0
        //   42: getfield this$0 : Landroid/support/v7/view/menu/CascadingMenuPopup;
        //   45: getfield mShowingMenus : Ljava/util/List;
        //   48: iload #6
        //   50: invokeinterface get : (I)Ljava/lang/Object;
        //   55: checkcast android/support/v7/view/menu/CascadingMenuPopup$CascadingMenuInfo
        //   58: getfield menu : Landroid/support/v7/view/menu/MenuBuilder;
        //   61: if_acmpne -> 67
        //   64: goto -> 76
        //   67: iinc #6, 1
        //   70: goto -> 33
        //   73: iconst_m1
        //   74: istore #6
        //   76: iload #6
        //   78: iconst_m1
        //   79: if_icmpne -> 83
        //   82: return
        //   83: iinc #6, 1
        //   86: iload #6
        //   88: aload_0
        //   89: getfield this$0 : Landroid/support/v7/view/menu/CascadingMenuPopup;
        //   92: getfield mShowingMenus : Ljava/util/List;
        //   95: invokeinterface size : ()I
        //   100: if_icmpge -> 122
        //   103: aload_0
        //   104: getfield this$0 : Landroid/support/v7/view/menu/CascadingMenuPopup;
        //   107: getfield mShowingMenus : Ljava/util/List;
        //   110: iload #6
        //   112: invokeinterface get : (I)Ljava/lang/Object;
        //   117: checkcast android/support/v7/view/menu/CascadingMenuPopup$CascadingMenuInfo
        //   120: astore #4
        //   122: new android/support/v7/view/menu/CascadingMenuPopup$3$1
        //   125: dup
        //   126: aload_0
        //   127: aload #4
        //   129: aload_2
        //   130: aload_1
        //   131: invokespecial <init> : (Landroid/support/v7/view/menu/CascadingMenuPopup$3;Landroid/support/v7/view/menu/CascadingMenuPopup$CascadingMenuInfo;Landroid/view/MenuItem;Landroid/support/v7/view/menu/MenuBuilder;)V
        //   134: astore_2
        //   135: invokestatic uptimeMillis : ()J
        //   138: lstore #7
        //   140: aload_0
        //   141: getfield this$0 : Landroid/support/v7/view/menu/CascadingMenuPopup;
        //   144: getfield mSubMenuHoverHandler : Landroid/os/Handler;
        //   147: aload_2
        //   148: aload_1
        //   149: lload #7
        //   151: ldc2_w 200
        //   154: ladd
        //   155: invokevirtual postAtTime : (Ljava/lang/Runnable;Ljava/lang/Object;J)Z
        //   158: pop
        //   159: return
      }
      
      public void onItemHoverExit(MenuBuilder param1MenuBuilder, MenuItem param1MenuItem) {
        CascadingMenuPopup.this.mSubMenuHoverHandler.removeCallbacksAndMessages(param1MenuBuilder);
      }
    };
  
  private final int mMenuMaxWidth;
  
  private PopupWindow.OnDismissListener mOnDismissListener;
  
  private final boolean mOverflowOnly;
  
  private final List<MenuBuilder> mPendingMenus = new ArrayList<MenuBuilder>();
  
  private final int mPopupStyleAttr;
  
  private final int mPopupStyleRes;
  
  private MenuPresenter.Callback mPresenterCallback;
  
  private int mRawDropDownGravity = 0;
  
  boolean mShouldCloseImmediately;
  
  private boolean mShowTitle;
  
  final List<CascadingMenuInfo> mShowingMenus = new ArrayList<CascadingMenuInfo>();
  
  View mShownAnchorView;
  
  final Handler mSubMenuHoverHandler;
  
  private ViewTreeObserver mTreeObserver;
  
  private int mXOffset;
  
  private int mYOffset;
  
  public CascadingMenuPopup(Context paramContext, View paramView, int paramInt1, int paramInt2, boolean paramBoolean) {
    this.mContext = paramContext;
    this.mAnchorView = paramView;
    this.mPopupStyleAttr = paramInt1;
    this.mPopupStyleRes = paramInt2;
    this.mOverflowOnly = paramBoolean;
    this.mForceShowIcon = false;
    this.mLastPosition = getInitialMenuPosition();
    Resources resources = paramContext.getResources();
    this.mMenuMaxWidth = Math.max((resources.getDisplayMetrics()).widthPixels / 2, resources.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
    this.mSubMenuHoverHandler = new Handler();
  }
  
  private MenuPopupWindow createPopupWindow() {
    MenuPopupWindow menuPopupWindow = new MenuPopupWindow(this.mContext, null, this.mPopupStyleAttr, this.mPopupStyleRes);
    menuPopupWindow.setHoverListener(this.mMenuItemHoverListener);
    menuPopupWindow.setOnItemClickListener(this);
    menuPopupWindow.setOnDismissListener(this);
    menuPopupWindow.setAnchorView(this.mAnchorView);
    menuPopupWindow.setDropDownGravity(this.mDropDownGravity);
    menuPopupWindow.setModal(true);
    menuPopupWindow.setInputMethodMode(2);
    return menuPopupWindow;
  }
  
  private int findIndexOfAddedMenu(MenuBuilder paramMenuBuilder) {
    int i = this.mShowingMenus.size();
    for (byte b = 0; b < i; b++) {
      if (paramMenuBuilder == ((CascadingMenuInfo)this.mShowingMenus.get(b)).menu)
        return b; 
    } 
    return -1;
  }
  
  private MenuItem findMenuItemForSubmenu(MenuBuilder paramMenuBuilder1, MenuBuilder paramMenuBuilder2) {
    int i = paramMenuBuilder1.size();
    for (byte b = 0; b < i; b++) {
      MenuItem menuItem = paramMenuBuilder1.getItem(b);
      if (menuItem.hasSubMenu() && paramMenuBuilder2 == menuItem.getSubMenu())
        return menuItem; 
    } 
    return null;
  }
  
  private View findParentViewForSubmenu(CascadingMenuInfo paramCascadingMenuInfo, MenuBuilder paramMenuBuilder) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: getfield menu : Landroid/support/v7/view/menu/MenuBuilder;
    //   5: aload_2
    //   6: invokespecial findMenuItemForSubmenu : (Landroid/support/v7/view/menu/MenuBuilder;Landroid/support/v7/view/menu/MenuBuilder;)Landroid/view/MenuItem;
    //   9: astore_2
    //   10: aload_2
    //   11: ifnonnull -> 16
    //   14: aconst_null
    //   15: areturn
    //   16: aload_1
    //   17: invokevirtual getListView : ()Landroid/widget/ListView;
    //   20: astore_3
    //   21: aload_3
    //   22: invokevirtual getAdapter : ()Landroid/widget/ListAdapter;
    //   25: astore_1
    //   26: aload_1
    //   27: instanceof android/widget/HeaderViewListAdapter
    //   30: istore #4
    //   32: iconst_0
    //   33: istore #5
    //   35: iload #4
    //   37: ifeq -> 62
    //   40: aload_1
    //   41: checkcast android/widget/HeaderViewListAdapter
    //   44: astore_1
    //   45: aload_1
    //   46: invokevirtual getHeadersCount : ()I
    //   49: istore #6
    //   51: aload_1
    //   52: invokevirtual getWrappedAdapter : ()Landroid/widget/ListAdapter;
    //   55: checkcast android/support/v7/view/menu/MenuAdapter
    //   58: astore_1
    //   59: goto -> 70
    //   62: aload_1
    //   63: checkcast android/support/v7/view/menu/MenuAdapter
    //   66: astore_1
    //   67: iconst_0
    //   68: istore #6
    //   70: aload_1
    //   71: invokevirtual getCount : ()I
    //   74: istore #7
    //   76: iload #5
    //   78: iload #7
    //   80: if_icmpge -> 102
    //   83: aload_2
    //   84: aload_1
    //   85: iload #5
    //   87: invokevirtual getItem : (I)Landroid/support/v7/view/menu/MenuItemImpl;
    //   90: if_acmpne -> 96
    //   93: goto -> 105
    //   96: iinc #5, 1
    //   99: goto -> 76
    //   102: iconst_m1
    //   103: istore #5
    //   105: iload #5
    //   107: iconst_m1
    //   108: if_icmpne -> 113
    //   111: aconst_null
    //   112: areturn
    //   113: iload #5
    //   115: iload #6
    //   117: iadd
    //   118: aload_3
    //   119: invokevirtual getFirstVisiblePosition : ()I
    //   122: isub
    //   123: istore #5
    //   125: iload #5
    //   127: iflt -> 149
    //   130: iload #5
    //   132: aload_3
    //   133: invokevirtual getChildCount : ()I
    //   136: if_icmplt -> 142
    //   139: goto -> 149
    //   142: aload_3
    //   143: iload #5
    //   145: invokevirtual getChildAt : (I)Landroid/view/View;
    //   148: areturn
    //   149: aconst_null
    //   150: areturn
  }
  
  private int getInitialMenuPosition() {
    int i = ViewCompat.getLayoutDirection(this.mAnchorView);
    boolean bool = true;
    if (i == 1)
      bool = false; 
    return bool;
  }
  
  private int getNextMenuPosition(int paramInt) {
    List<CascadingMenuInfo> list = this.mShowingMenus;
    ListView listView = ((CascadingMenuInfo)list.get(list.size() - 1)).getListView();
    int[] arrayOfInt = new int[2];
    listView.getLocationOnScreen(arrayOfInt);
    Rect rect = new Rect();
    this.mShownAnchorView.getWindowVisibleDisplayFrame(rect);
    return (this.mLastPosition == 1) ? ((arrayOfInt[0] + listView.getWidth() + paramInt > rect.right) ? 0 : 1) : ((arrayOfInt[0] - paramInt < 0) ? 1 : 0);
  }
  
  private void showMenu(MenuBuilder paramMenuBuilder) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mContext : Landroid/content/Context;
    //   4: invokestatic from : (Landroid/content/Context;)Landroid/view/LayoutInflater;
    //   7: astore_2
    //   8: new android/support/v7/view/menu/MenuAdapter
    //   11: dup
    //   12: aload_1
    //   13: aload_2
    //   14: aload_0
    //   15: getfield mOverflowOnly : Z
    //   18: invokespecial <init> : (Landroid/support/v7/view/menu/MenuBuilder;Landroid/view/LayoutInflater;Z)V
    //   21: astore_3
    //   22: aload_0
    //   23: invokevirtual isShowing : ()Z
    //   26: ifne -> 44
    //   29: aload_0
    //   30: getfield mForceShowIcon : Z
    //   33: ifeq -> 44
    //   36: aload_3
    //   37: iconst_1
    //   38: invokevirtual setForceShowIcon : (Z)V
    //   41: goto -> 59
    //   44: aload_0
    //   45: invokevirtual isShowing : ()Z
    //   48: ifeq -> 59
    //   51: aload_3
    //   52: aload_1
    //   53: invokestatic shouldPreserveIconSpacing : (Landroid/support/v7/view/menu/MenuBuilder;)Z
    //   56: invokevirtual setForceShowIcon : (Z)V
    //   59: aload_3
    //   60: aconst_null
    //   61: aload_0
    //   62: getfield mContext : Landroid/content/Context;
    //   65: aload_0
    //   66: getfield mMenuMaxWidth : I
    //   69: invokestatic measureIndividualMenuWidth : (Landroid/widget/ListAdapter;Landroid/view/ViewGroup;Landroid/content/Context;I)I
    //   72: istore #4
    //   74: aload_0
    //   75: invokespecial createPopupWindow : ()Landroid/support/v7/widget/MenuPopupWindow;
    //   78: astore #5
    //   80: aload #5
    //   82: aload_3
    //   83: invokevirtual setAdapter : (Landroid/widget/ListAdapter;)V
    //   86: aload #5
    //   88: iload #4
    //   90: invokevirtual setContentWidth : (I)V
    //   93: aload #5
    //   95: aload_0
    //   96: getfield mDropDownGravity : I
    //   99: invokevirtual setDropDownGravity : (I)V
    //   102: aload_0
    //   103: getfield mShowingMenus : Ljava/util/List;
    //   106: invokeinterface size : ()I
    //   111: ifle -> 148
    //   114: aload_0
    //   115: getfield mShowingMenus : Ljava/util/List;
    //   118: astore_3
    //   119: aload_3
    //   120: aload_3
    //   121: invokeinterface size : ()I
    //   126: iconst_1
    //   127: isub
    //   128: invokeinterface get : (I)Ljava/lang/Object;
    //   133: checkcast android/support/v7/view/menu/CascadingMenuPopup$CascadingMenuInfo
    //   136: astore_3
    //   137: aload_0
    //   138: aload_3
    //   139: aload_1
    //   140: invokespecial findParentViewForSubmenu : (Landroid/support/v7/view/menu/CascadingMenuPopup$CascadingMenuInfo;Landroid/support/v7/view/menu/MenuBuilder;)Landroid/view/View;
    //   143: astore #6
    //   145: goto -> 153
    //   148: aconst_null
    //   149: astore_3
    //   150: aload_3
    //   151: astore #6
    //   153: aload #6
    //   155: ifnull -> 392
    //   158: aload #5
    //   160: iconst_0
    //   161: invokevirtual setTouchModal : (Z)V
    //   164: aload #5
    //   166: aconst_null
    //   167: invokevirtual setEnterTransition : (Ljava/lang/Object;)V
    //   170: aload_0
    //   171: iload #4
    //   173: invokespecial getNextMenuPosition : (I)I
    //   176: istore #7
    //   178: iload #7
    //   180: iconst_1
    //   181: if_icmpne -> 190
    //   184: iconst_1
    //   185: istore #8
    //   187: goto -> 193
    //   190: iconst_0
    //   191: istore #8
    //   193: aload_0
    //   194: iload #7
    //   196: putfield mLastPosition : I
    //   199: getstatic android/os/Build$VERSION.SDK_INT : I
    //   202: bipush #26
    //   204: if_icmplt -> 223
    //   207: aload #5
    //   209: aload #6
    //   211: invokevirtual setAnchorView : (Landroid/view/View;)V
    //   214: iconst_0
    //   215: istore #7
    //   217: iconst_0
    //   218: istore #9
    //   220: goto -> 312
    //   223: iconst_2
    //   224: newarray int
    //   226: astore #10
    //   228: aload_0
    //   229: getfield mAnchorView : Landroid/view/View;
    //   232: aload #10
    //   234: invokevirtual getLocationOnScreen : ([I)V
    //   237: iconst_2
    //   238: newarray int
    //   240: astore #11
    //   242: aload #6
    //   244: aload #11
    //   246: invokevirtual getLocationOnScreen : ([I)V
    //   249: aload_0
    //   250: getfield mDropDownGravity : I
    //   253: bipush #7
    //   255: iand
    //   256: iconst_5
    //   257: if_icmpne -> 290
    //   260: aload #10
    //   262: iconst_0
    //   263: aload #10
    //   265: iconst_0
    //   266: iaload
    //   267: aload_0
    //   268: getfield mAnchorView : Landroid/view/View;
    //   271: invokevirtual getWidth : ()I
    //   274: iadd
    //   275: iastore
    //   276: aload #11
    //   278: iconst_0
    //   279: aload #11
    //   281: iconst_0
    //   282: iaload
    //   283: aload #6
    //   285: invokevirtual getWidth : ()I
    //   288: iadd
    //   289: iastore
    //   290: aload #11
    //   292: iconst_0
    //   293: iaload
    //   294: aload #10
    //   296: iconst_0
    //   297: iaload
    //   298: isub
    //   299: istore #9
    //   301: aload #11
    //   303: iconst_1
    //   304: iaload
    //   305: aload #10
    //   307: iconst_1
    //   308: iaload
    //   309: isub
    //   310: istore #7
    //   312: aload_0
    //   313: getfield mDropDownGravity : I
    //   316: iconst_5
    //   317: iand
    //   318: iconst_5
    //   319: if_icmpne -> 340
    //   322: iload #8
    //   324: ifeq -> 330
    //   327: goto -> 352
    //   330: aload #6
    //   332: invokevirtual getWidth : ()I
    //   335: istore #4
    //   337: goto -> 362
    //   340: iload #8
    //   342: ifeq -> 362
    //   345: aload #6
    //   347: invokevirtual getWidth : ()I
    //   350: istore #4
    //   352: iload #9
    //   354: iload #4
    //   356: iadd
    //   357: istore #4
    //   359: goto -> 369
    //   362: iload #9
    //   364: iload #4
    //   366: isub
    //   367: istore #4
    //   369: aload #5
    //   371: iload #4
    //   373: invokevirtual setHorizontalOffset : (I)V
    //   376: aload #5
    //   378: iconst_1
    //   379: invokevirtual setOverlapAnchor : (Z)V
    //   382: aload #5
    //   384: iload #7
    //   386: invokevirtual setVerticalOffset : (I)V
    //   389: goto -> 433
    //   392: aload_0
    //   393: getfield mHasXOffset : Z
    //   396: ifeq -> 408
    //   399: aload #5
    //   401: aload_0
    //   402: getfield mXOffset : I
    //   405: invokevirtual setHorizontalOffset : (I)V
    //   408: aload_0
    //   409: getfield mHasYOffset : Z
    //   412: ifeq -> 424
    //   415: aload #5
    //   417: aload_0
    //   418: getfield mYOffset : I
    //   421: invokevirtual setVerticalOffset : (I)V
    //   424: aload #5
    //   426: aload_0
    //   427: invokevirtual getEpicenterBounds : ()Landroid/graphics/Rect;
    //   430: invokevirtual setEpicenterBounds : (Landroid/graphics/Rect;)V
    //   433: new android/support/v7/view/menu/CascadingMenuPopup$CascadingMenuInfo
    //   436: dup
    //   437: aload #5
    //   439: aload_1
    //   440: aload_0
    //   441: getfield mLastPosition : I
    //   444: invokespecial <init> : (Landroid/support/v7/widget/MenuPopupWindow;Landroid/support/v7/view/menu/MenuBuilder;I)V
    //   447: astore #6
    //   449: aload_0
    //   450: getfield mShowingMenus : Ljava/util/List;
    //   453: aload #6
    //   455: invokeinterface add : (Ljava/lang/Object;)Z
    //   460: pop
    //   461: aload #5
    //   463: invokevirtual show : ()V
    //   466: aload #5
    //   468: invokevirtual getListView : ()Landroid/widget/ListView;
    //   471: astore #6
    //   473: aload #6
    //   475: aload_0
    //   476: invokevirtual setOnKeyListener : (Landroid/view/View$OnKeyListener;)V
    //   479: aload_3
    //   480: ifnonnull -> 548
    //   483: aload_0
    //   484: getfield mShowTitle : Z
    //   487: ifeq -> 548
    //   490: aload_1
    //   491: invokevirtual getHeaderTitle : ()Ljava/lang/CharSequence;
    //   494: ifnull -> 548
    //   497: aload_2
    //   498: getstatic android/support/v7/appcompat/R$layout.abc_popup_menu_header_item_layout : I
    //   501: aload #6
    //   503: iconst_0
    //   504: invokevirtual inflate : (ILandroid/view/ViewGroup;Z)Landroid/view/View;
    //   507: checkcast android/widget/FrameLayout
    //   510: astore_2
    //   511: aload_2
    //   512: ldc_w 16908310
    //   515: invokevirtual findViewById : (I)Landroid/view/View;
    //   518: checkcast android/widget/TextView
    //   521: astore_3
    //   522: aload_2
    //   523: iconst_0
    //   524: invokevirtual setEnabled : (Z)V
    //   527: aload_3
    //   528: aload_1
    //   529: invokevirtual getHeaderTitle : ()Ljava/lang/CharSequence;
    //   532: invokevirtual setText : (Ljava/lang/CharSequence;)V
    //   535: aload #6
    //   537: aload_2
    //   538: aconst_null
    //   539: iconst_0
    //   540: invokevirtual addHeaderView : (Landroid/view/View;Ljava/lang/Object;Z)V
    //   543: aload #5
    //   545: invokevirtual show : ()V
    //   548: return
  }
  
  public void addMenu(MenuBuilder paramMenuBuilder) {
    paramMenuBuilder.addMenuPresenter(this, this.mContext);
    if (isShowing()) {
      showMenu(paramMenuBuilder);
    } else {
      this.mPendingMenus.add(paramMenuBuilder);
    } 
  }
  
  protected boolean closeMenuOnSubMenuOpened() {
    return false;
  }
  
  public void dismiss() {
    int i = this.mShowingMenus.size();
    if (i > 0) {
      CascadingMenuInfo[] arrayOfCascadingMenuInfo = this.mShowingMenus.<CascadingMenuInfo>toArray(new CascadingMenuInfo[i]);
      while (--i >= 0) {
        CascadingMenuInfo cascadingMenuInfo = arrayOfCascadingMenuInfo[i];
        if (cascadingMenuInfo.window.isShowing())
          cascadingMenuInfo.window.dismiss(); 
        i--;
      } 
    } 
  }
  
  public boolean flagActionItems() {
    return false;
  }
  
  public ListView getListView() {
    ListView listView;
    if (this.mShowingMenus.isEmpty()) {
      listView = null;
    } else {
      List<CascadingMenuInfo> list = this.mShowingMenus;
      listView = ((CascadingMenuInfo)list.get(list.size() - 1)).getListView();
    } 
    return listView;
  }
  
  public boolean isShowing() {
    int i = this.mShowingMenus.size();
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (i > 0) {
      bool2 = bool1;
      if (((CascadingMenuInfo)this.mShowingMenus.get(0)).window.isShowing())
        bool2 = true; 
    } 
    return bool2;
  }
  
  public void onCloseMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean) {
    int i = findIndexOfAddedMenu(paramMenuBuilder);
    if (i < 0)
      return; 
    int j = i + 1;
    if (j < this.mShowingMenus.size())
      ((CascadingMenuInfo)this.mShowingMenus.get(j)).menu.close(false); 
    CascadingMenuInfo cascadingMenuInfo = this.mShowingMenus.remove(i);
    cascadingMenuInfo.menu.removeMenuPresenter(this);
    if (this.mShouldCloseImmediately) {
      cascadingMenuInfo.window.setExitTransition(null);
      cascadingMenuInfo.window.setAnimationStyle(0);
    } 
    cascadingMenuInfo.window.dismiss();
    j = this.mShowingMenus.size();
    if (j > 0) {
      this.mLastPosition = ((CascadingMenuInfo)this.mShowingMenus.get(j - 1)).position;
    } else {
      this.mLastPosition = getInitialMenuPosition();
    } 
    if (j == 0) {
      dismiss();
      MenuPresenter.Callback callback = this.mPresenterCallback;
      if (callback != null)
        callback.onCloseMenu(paramMenuBuilder, true); 
      ViewTreeObserver viewTreeObserver = this.mTreeObserver;
      if (viewTreeObserver != null) {
        if (viewTreeObserver.isAlive())
          this.mTreeObserver.removeGlobalOnLayoutListener(this.mGlobalLayoutListener); 
        this.mTreeObserver = null;
      } 
      this.mShownAnchorView.removeOnAttachStateChangeListener(this.mAttachStateChangeListener);
      this.mOnDismissListener.onDismiss();
    } else if (paramBoolean) {
      ((CascadingMenuInfo)this.mShowingMenus.get(0)).menu.close(false);
    } 
  }
  
  public void onDismiss() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mShowingMenus : Ljava/util/List;
    //   4: invokeinterface size : ()I
    //   9: istore_1
    //   10: iconst_0
    //   11: istore_2
    //   12: iload_2
    //   13: iload_1
    //   14: if_icmpge -> 50
    //   17: aload_0
    //   18: getfield mShowingMenus : Ljava/util/List;
    //   21: iload_2
    //   22: invokeinterface get : (I)Ljava/lang/Object;
    //   27: checkcast android/support/v7/view/menu/CascadingMenuPopup$CascadingMenuInfo
    //   30: astore_3
    //   31: aload_3
    //   32: getfield window : Landroid/support/v7/widget/MenuPopupWindow;
    //   35: invokevirtual isShowing : ()Z
    //   38: ifne -> 44
    //   41: goto -> 52
    //   44: iinc #2, 1
    //   47: goto -> 12
    //   50: aconst_null
    //   51: astore_3
    //   52: aload_3
    //   53: ifnull -> 64
    //   56: aload_3
    //   57: getfield menu : Landroid/support/v7/view/menu/MenuBuilder;
    //   60: iconst_0
    //   61: invokevirtual close : (Z)V
    //   64: return
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
    for (CascadingMenuInfo cascadingMenuInfo : this.mShowingMenus) {
      if (paramSubMenuBuilder == cascadingMenuInfo.menu) {
        cascadingMenuInfo.getListView().requestFocus();
        return true;
      } 
    } 
    if (paramSubMenuBuilder.hasVisibleItems()) {
      addMenu(paramSubMenuBuilder);
      MenuPresenter.Callback callback = this.mPresenterCallback;
      if (callback != null)
        callback.onOpenSubMenu(paramSubMenuBuilder); 
      return true;
    } 
    return false;
  }
  
  public void setAnchorView(View paramView) {
    if (this.mAnchorView != paramView) {
      this.mAnchorView = paramView;
      this.mDropDownGravity = GravityCompat.getAbsoluteGravity(this.mRawDropDownGravity, ViewCompat.getLayoutDirection(paramView));
    } 
  }
  
  public void setCallback(MenuPresenter.Callback paramCallback) {
    this.mPresenterCallback = paramCallback;
  }
  
  public void setForceShowIcon(boolean paramBoolean) {
    this.mForceShowIcon = paramBoolean;
  }
  
  public void setGravity(int paramInt) {
    if (this.mRawDropDownGravity != paramInt) {
      this.mRawDropDownGravity = paramInt;
      this.mDropDownGravity = GravityCompat.getAbsoluteGravity(paramInt, ViewCompat.getLayoutDirection(this.mAnchorView));
    } 
  }
  
  public void setHorizontalOffset(int paramInt) {
    this.mHasXOffset = true;
    this.mXOffset = paramInt;
  }
  
  public void setOnDismissListener(PopupWindow.OnDismissListener paramOnDismissListener) {
    this.mOnDismissListener = paramOnDismissListener;
  }
  
  public void setShowTitle(boolean paramBoolean) {
    this.mShowTitle = paramBoolean;
  }
  
  public void setVerticalOffset(int paramInt) {
    this.mHasYOffset = true;
    this.mYOffset = paramInt;
  }
  
  public void show() {
    if (isShowing())
      return; 
    Iterator<MenuBuilder> iterator = this.mPendingMenus.iterator();
    while (iterator.hasNext())
      showMenu(iterator.next()); 
    this.mPendingMenus.clear();
    View view = this.mAnchorView;
    this.mShownAnchorView = view;
    if (view != null) {
      boolean bool;
      if (this.mTreeObserver == null) {
        bool = true;
      } else {
        bool = false;
      } 
      ViewTreeObserver viewTreeObserver = this.mShownAnchorView.getViewTreeObserver();
      this.mTreeObserver = viewTreeObserver;
      if (bool)
        viewTreeObserver.addOnGlobalLayoutListener(this.mGlobalLayoutListener); 
      this.mShownAnchorView.addOnAttachStateChangeListener(this.mAttachStateChangeListener);
    } 
  }
  
  public void updateMenuView(boolean paramBoolean) {
    Iterator<CascadingMenuInfo> iterator = this.mShowingMenus.iterator();
    while (iterator.hasNext())
      toMenuAdapter(((CascadingMenuInfo)iterator.next()).getListView().getAdapter()).notifyDataSetChanged(); 
  }
  
  private static class CascadingMenuInfo {
    public final MenuBuilder menu;
    
    public final int position;
    
    public final MenuPopupWindow window;
    
    public CascadingMenuInfo(MenuPopupWindow param1MenuPopupWindow, MenuBuilder param1MenuBuilder, int param1Int) {
      this.window = param1MenuPopupWindow;
      this.menu = param1MenuBuilder;
      this.position = param1Int;
    }
    
    public ListView getListView() {
      return this.window.getListView();
    }
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface HorizPosition {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\view\menu\CascadingMenuPopup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */