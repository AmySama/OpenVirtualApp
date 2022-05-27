package android.support.v7.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.NavUtils;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.view.menu.ListMenuPresenter;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.ActionBarContextView;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.DecorContentParent;
import android.support.v7.widget.FitWindowsViewGroup;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.VectorEnabledTintResources;
import android.support.v7.widget.ViewUtils;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;

class AppCompatDelegateImplV9 extends AppCompatDelegateImplBase implements MenuBuilder.Callback, LayoutInflater.Factory2 {
  private static final boolean IS_PRE_LOLLIPOP;
  
  private ActionMenuPresenterCallback mActionMenuPresenterCallback;
  
  ActionMode mActionMode;
  
  PopupWindow mActionModePopup;
  
  ActionBarContextView mActionModeView;
  
  private AppCompatViewInflater mAppCompatViewInflater;
  
  private boolean mClosingActionMenu;
  
  private DecorContentParent mDecorContentParent;
  
  private boolean mEnableDefaultActionBarUp;
  
  ViewPropertyAnimatorCompat mFadeAnim = null;
  
  private boolean mFeatureIndeterminateProgress;
  
  private boolean mFeatureProgress;
  
  int mInvalidatePanelMenuFeatures;
  
  boolean mInvalidatePanelMenuPosted;
  
  private final Runnable mInvalidatePanelMenuRunnable = new Runnable() {
      public void run() {
        if ((AppCompatDelegateImplV9.this.mInvalidatePanelMenuFeatures & 0x1) != 0)
          AppCompatDelegateImplV9.this.doInvalidatePanelMenu(0); 
        if ((AppCompatDelegateImplV9.this.mInvalidatePanelMenuFeatures & 0x1000) != 0)
          AppCompatDelegateImplV9.this.doInvalidatePanelMenu(108); 
        AppCompatDelegateImplV9.this.mInvalidatePanelMenuPosted = false;
        AppCompatDelegateImplV9.this.mInvalidatePanelMenuFeatures = 0;
      }
    };
  
  private boolean mLongPressBackDown;
  
  private PanelMenuPresenterCallback mPanelMenuPresenterCallback;
  
  private PanelFeatureState[] mPanels;
  
  private PanelFeatureState mPreparedPanel;
  
  Runnable mShowActionModePopup;
  
  private View mStatusGuard;
  
  private ViewGroup mSubDecor;
  
  private boolean mSubDecorInstalled;
  
  private Rect mTempRect1;
  
  private Rect mTempRect2;
  
  private TextView mTitleView;
  
  static {
    boolean bool;
    if (Build.VERSION.SDK_INT < 21) {
      bool = true;
    } else {
      bool = false;
    } 
    IS_PRE_LOLLIPOP = bool;
  }
  
  AppCompatDelegateImplV9(Context paramContext, Window paramWindow, AppCompatCallback paramAppCompatCallback) {
    super(paramContext, paramWindow, paramAppCompatCallback);
  }
  
  private void applyFixedSizeWindow() {
    ContentFrameLayout contentFrameLayout = (ContentFrameLayout)this.mSubDecor.findViewById(16908290);
    View view = this.mWindow.getDecorView();
    contentFrameLayout.setDecorPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());
    TypedArray typedArray = this.mContext.obtainStyledAttributes(R.styleable.AppCompatTheme);
    typedArray.getValue(R.styleable.AppCompatTheme_windowMinWidthMajor, contentFrameLayout.getMinWidthMajor());
    typedArray.getValue(R.styleable.AppCompatTheme_windowMinWidthMinor, contentFrameLayout.getMinWidthMinor());
    if (typedArray.hasValue(R.styleable.AppCompatTheme_windowFixedWidthMajor))
      typedArray.getValue(R.styleable.AppCompatTheme_windowFixedWidthMajor, contentFrameLayout.getFixedWidthMajor()); 
    if (typedArray.hasValue(R.styleable.AppCompatTheme_windowFixedWidthMinor))
      typedArray.getValue(R.styleable.AppCompatTheme_windowFixedWidthMinor, contentFrameLayout.getFixedWidthMinor()); 
    if (typedArray.hasValue(R.styleable.AppCompatTheme_windowFixedHeightMajor))
      typedArray.getValue(R.styleable.AppCompatTheme_windowFixedHeightMajor, contentFrameLayout.getFixedHeightMajor()); 
    if (typedArray.hasValue(R.styleable.AppCompatTheme_windowFixedHeightMinor))
      typedArray.getValue(R.styleable.AppCompatTheme_windowFixedHeightMinor, contentFrameLayout.getFixedHeightMinor()); 
    typedArray.recycle();
    contentFrameLayout.requestLayout();
  }
  
  private ViewGroup createSubDecor() {
    StringBuilder stringBuilder;
    TypedArray typedArray = this.mContext.obtainStyledAttributes(R.styleable.AppCompatTheme);
    if (typedArray.hasValue(R.styleable.AppCompatTheme_windowActionBar)) {
      ViewGroup viewGroup;
      if (typedArray.getBoolean(R.styleable.AppCompatTheme_windowNoTitle, false)) {
        requestWindowFeature(1);
      } else if (typedArray.getBoolean(R.styleable.AppCompatTheme_windowActionBar, false)) {
        requestWindowFeature(108);
      } 
      if (typedArray.getBoolean(R.styleable.AppCompatTheme_windowActionBarOverlay, false))
        requestWindowFeature(109); 
      if (typedArray.getBoolean(R.styleable.AppCompatTheme_windowActionModeOverlay, false))
        requestWindowFeature(10); 
      this.mIsFloating = typedArray.getBoolean(R.styleable.AppCompatTheme_android_windowIsFloating, false);
      typedArray.recycle();
      this.mWindow.getDecorView();
      LayoutInflater layoutInflater = LayoutInflater.from(this.mContext);
      if (!this.mWindowNoTitle) {
        if (this.mIsFloating) {
          viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.abc_dialog_title_material, null);
          this.mOverlayActionBar = false;
          this.mHasActionBar = false;
        } else if (this.mHasActionBar) {
          Context context;
          TypedValue typedValue = new TypedValue();
          this.mContext.getTheme().resolveAttribute(R.attr.actionBarTheme, typedValue, true);
          if (typedValue.resourceId != 0) {
            ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(this.mContext, typedValue.resourceId);
          } else {
            context = this.mContext;
          } 
          ViewGroup viewGroup1 = (ViewGroup)LayoutInflater.from(context).inflate(R.layout.abc_screen_toolbar, null);
          DecorContentParent decorContentParent = (DecorContentParent)viewGroup1.findViewById(R.id.decor_content_parent);
          this.mDecorContentParent = decorContentParent;
          decorContentParent.setWindowCallback(getWindowCallback());
          if (this.mOverlayActionBar)
            this.mDecorContentParent.initFeature(109); 
          if (this.mFeatureProgress)
            this.mDecorContentParent.initFeature(2); 
          viewGroup = viewGroup1;
          if (this.mFeatureIndeterminateProgress) {
            this.mDecorContentParent.initFeature(5);
            viewGroup = viewGroup1;
          } 
        } else {
          layoutInflater = null;
        } 
      } else {
        if (this.mOverlayActionMode) {
          viewGroup = (ViewGroup)layoutInflater.inflate(R.layout.abc_screen_simple_overlay_action_mode, null);
        } else {
          viewGroup = (ViewGroup)viewGroup.inflate(R.layout.abc_screen_simple, null);
        } 
        if (Build.VERSION.SDK_INT >= 21) {
          ViewCompat.setOnApplyWindowInsetsListener((View)viewGroup, new OnApplyWindowInsetsListener() {
                public WindowInsetsCompat onApplyWindowInsets(View param1View, WindowInsetsCompat param1WindowInsetsCompat) {
                  int i = param1WindowInsetsCompat.getSystemWindowInsetTop();
                  int j = AppCompatDelegateImplV9.this.updateStatusGuard(i);
                  WindowInsetsCompat windowInsetsCompat = param1WindowInsetsCompat;
                  if (i != j)
                    windowInsetsCompat = param1WindowInsetsCompat.replaceSystemWindowInsets(param1WindowInsetsCompat.getSystemWindowInsetLeft(), j, param1WindowInsetsCompat.getSystemWindowInsetRight(), param1WindowInsetsCompat.getSystemWindowInsetBottom()); 
                  return ViewCompat.onApplyWindowInsets(param1View, windowInsetsCompat);
                }
              });
        } else {
          ((FitWindowsViewGroup)viewGroup).setOnFitSystemWindowsListener(new FitWindowsViewGroup.OnFitSystemWindowsListener() {
                public void onFitSystemWindows(Rect param1Rect) {
                  param1Rect.top = AppCompatDelegateImplV9.this.updateStatusGuard(param1Rect.top);
                }
              });
        } 
      } 
      if (viewGroup != null) {
        if (this.mDecorContentParent == null)
          this.mTitleView = (TextView)viewGroup.findViewById(R.id.title); 
        ViewUtils.makeOptionalFitsSystemWindows((View)viewGroup);
        ContentFrameLayout contentFrameLayout = (ContentFrameLayout)viewGroup.findViewById(R.id.action_bar_activity_content);
        ViewGroup viewGroup1 = (ViewGroup)this.mWindow.findViewById(16908290);
        if (viewGroup1 != null) {
          while (viewGroup1.getChildCount() > 0) {
            View view = viewGroup1.getChildAt(0);
            viewGroup1.removeViewAt(0);
            contentFrameLayout.addView(view);
          } 
          viewGroup1.setId(-1);
          contentFrameLayout.setId(16908290);
          if (viewGroup1 instanceof FrameLayout)
            ((FrameLayout)viewGroup1).setForeground(null); 
        } 
        this.mWindow.setContentView((View)viewGroup);
        contentFrameLayout.setAttachListener(new ContentFrameLayout.OnAttachListener() {
              public void onAttachedFromWindow() {}
              
              public void onDetachedFromWindow() {
                AppCompatDelegateImplV9.this.dismissPopups();
              }
            });
        return viewGroup;
      } 
      stringBuilder = new StringBuilder();
      stringBuilder.append("AppCompat does not support the current theme features: { windowActionBar: ");
      stringBuilder.append(this.mHasActionBar);
      stringBuilder.append(", windowActionBarOverlay: ");
      stringBuilder.append(this.mOverlayActionBar);
      stringBuilder.append(", android:windowIsFloating: ");
      stringBuilder.append(this.mIsFloating);
      stringBuilder.append(", windowActionModeOverlay: ");
      stringBuilder.append(this.mOverlayActionMode);
      stringBuilder.append(", windowNoTitle: ");
      stringBuilder.append(this.mWindowNoTitle);
      stringBuilder.append(" }");
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    stringBuilder.recycle();
    throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
  }
  
  private void ensureSubDecor() {
    if (!this.mSubDecorInstalled) {
      this.mSubDecor = createSubDecor();
      CharSequence charSequence = getTitle();
      if (!TextUtils.isEmpty(charSequence))
        onTitleChanged(charSequence); 
      applyFixedSizeWindow();
      onSubDecorInstalled(this.mSubDecor);
      this.mSubDecorInstalled = true;
      PanelFeatureState panelFeatureState = getPanelState(0, false);
      if (!isDestroyed() && (panelFeatureState == null || panelFeatureState.menu == null))
        invalidatePanelMenu(108); 
    } 
  }
  
  private boolean initializePanelContent(PanelFeatureState paramPanelFeatureState) {
    View view = paramPanelFeatureState.createdPanelView;
    boolean bool = true;
    if (view != null) {
      paramPanelFeatureState.shownPanelView = paramPanelFeatureState.createdPanelView;
      return true;
    } 
    if (paramPanelFeatureState.menu == null)
      return false; 
    if (this.mPanelMenuPresenterCallback == null)
      this.mPanelMenuPresenterCallback = new PanelMenuPresenterCallback(); 
    paramPanelFeatureState.shownPanelView = (View)paramPanelFeatureState.getListMenuView(this.mPanelMenuPresenterCallback);
    if (paramPanelFeatureState.shownPanelView == null)
      bool = false; 
    return bool;
  }
  
  private boolean initializePanelDecor(PanelFeatureState paramPanelFeatureState) {
    paramPanelFeatureState.setStyle(getActionBarThemedContext());
    paramPanelFeatureState.decorView = (ViewGroup)new ListMenuDecorView(paramPanelFeatureState.listPresenterContext);
    paramPanelFeatureState.gravity = 81;
    return true;
  }
  
  private boolean initializePanelMenu(PanelFeatureState paramPanelFeatureState) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mContext : Landroid/content/Context;
    //   4: astore_2
    //   5: aload_1
    //   6: getfield featureId : I
    //   9: ifeq -> 23
    //   12: aload_2
    //   13: astore_3
    //   14: aload_1
    //   15: getfield featureId : I
    //   18: bipush #108
    //   20: if_icmpne -> 190
    //   23: aload_2
    //   24: astore_3
    //   25: aload_0
    //   26: getfield mDecorContentParent : Landroid/support/v7/widget/DecorContentParent;
    //   29: ifnull -> 190
    //   32: new android/util/TypedValue
    //   35: dup
    //   36: invokespecial <init> : ()V
    //   39: astore #4
    //   41: aload_2
    //   42: invokevirtual getTheme : ()Landroid/content/res/Resources$Theme;
    //   45: astore #5
    //   47: aload #5
    //   49: getstatic android/support/v7/appcompat/R$attr.actionBarTheme : I
    //   52: aload #4
    //   54: iconst_1
    //   55: invokevirtual resolveAttribute : (ILandroid/util/TypedValue;Z)Z
    //   58: pop
    //   59: aconst_null
    //   60: astore_3
    //   61: aload #4
    //   63: getfield resourceId : I
    //   66: ifeq -> 107
    //   69: aload_2
    //   70: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   73: invokevirtual newTheme : ()Landroid/content/res/Resources$Theme;
    //   76: astore_3
    //   77: aload_3
    //   78: aload #5
    //   80: invokevirtual setTo : (Landroid/content/res/Resources$Theme;)V
    //   83: aload_3
    //   84: aload #4
    //   86: getfield resourceId : I
    //   89: iconst_1
    //   90: invokevirtual applyStyle : (IZ)V
    //   93: aload_3
    //   94: getstatic android/support/v7/appcompat/R$attr.actionBarWidgetTheme : I
    //   97: aload #4
    //   99: iconst_1
    //   100: invokevirtual resolveAttribute : (ILandroid/util/TypedValue;Z)Z
    //   103: pop
    //   104: goto -> 119
    //   107: aload #5
    //   109: getstatic android/support/v7/appcompat/R$attr.actionBarWidgetTheme : I
    //   112: aload #4
    //   114: iconst_1
    //   115: invokevirtual resolveAttribute : (ILandroid/util/TypedValue;Z)Z
    //   118: pop
    //   119: aload_3
    //   120: astore #6
    //   122: aload #4
    //   124: getfield resourceId : I
    //   127: ifeq -> 164
    //   130: aload_3
    //   131: astore #6
    //   133: aload_3
    //   134: ifnonnull -> 153
    //   137: aload_2
    //   138: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   141: invokevirtual newTheme : ()Landroid/content/res/Resources$Theme;
    //   144: astore #6
    //   146: aload #6
    //   148: aload #5
    //   150: invokevirtual setTo : (Landroid/content/res/Resources$Theme;)V
    //   153: aload #6
    //   155: aload #4
    //   157: getfield resourceId : I
    //   160: iconst_1
    //   161: invokevirtual applyStyle : (IZ)V
    //   164: aload_2
    //   165: astore_3
    //   166: aload #6
    //   168: ifnull -> 190
    //   171: new android/support/v7/view/ContextThemeWrapper
    //   174: dup
    //   175: aload_2
    //   176: iconst_0
    //   177: invokespecial <init> : (Landroid/content/Context;I)V
    //   180: astore_3
    //   181: aload_3
    //   182: invokevirtual getTheme : ()Landroid/content/res/Resources$Theme;
    //   185: aload #6
    //   187: invokevirtual setTo : (Landroid/content/res/Resources$Theme;)V
    //   190: new android/support/v7/view/menu/MenuBuilder
    //   193: dup
    //   194: aload_3
    //   195: invokespecial <init> : (Landroid/content/Context;)V
    //   198: astore_3
    //   199: aload_3
    //   200: aload_0
    //   201: invokevirtual setCallback : (Landroid/support/v7/view/menu/MenuBuilder$Callback;)V
    //   204: aload_1
    //   205: aload_3
    //   206: invokevirtual setMenu : (Landroid/support/v7/view/menu/MenuBuilder;)V
    //   209: iconst_1
    //   210: ireturn
  }
  
  private void invalidatePanelMenu(int paramInt) {
    this.mInvalidatePanelMenuFeatures = 1 << paramInt | this.mInvalidatePanelMenuFeatures;
    if (!this.mInvalidatePanelMenuPosted) {
      ViewCompat.postOnAnimation(this.mWindow.getDecorView(), this.mInvalidatePanelMenuRunnable);
      this.mInvalidatePanelMenuPosted = true;
    } 
  }
  
  private boolean onKeyDownPanel(int paramInt, KeyEvent paramKeyEvent) {
    if (paramKeyEvent.getRepeatCount() == 0) {
      PanelFeatureState panelFeatureState = getPanelState(paramInt, true);
      if (!panelFeatureState.isOpen)
        return preparePanel(panelFeatureState, paramKeyEvent); 
    } 
    return false;
  }
  
  private boolean onKeyUpPanel(int paramInt, KeyEvent paramKeyEvent) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mActionMode : Landroid/support/v7/view/ActionMode;
    //   4: ifnull -> 9
    //   7: iconst_0
    //   8: ireturn
    //   9: iconst_1
    //   10: istore_3
    //   11: aload_0
    //   12: iload_1
    //   13: iconst_1
    //   14: invokevirtual getPanelState : (IZ)Landroid/support/v7/app/AppCompatDelegateImplV9$PanelFeatureState;
    //   17: astore #4
    //   19: iload_1
    //   20: ifne -> 114
    //   23: aload_0
    //   24: getfield mDecorContentParent : Landroid/support/v7/widget/DecorContentParent;
    //   27: astore #5
    //   29: aload #5
    //   31: ifnull -> 114
    //   34: aload #5
    //   36: invokeinterface canShowOverflowMenu : ()Z
    //   41: ifeq -> 114
    //   44: aload_0
    //   45: getfield mContext : Landroid/content/Context;
    //   48: invokestatic get : (Landroid/content/Context;)Landroid/view/ViewConfiguration;
    //   51: invokevirtual hasPermanentMenuKey : ()Z
    //   54: ifne -> 114
    //   57: aload_0
    //   58: getfield mDecorContentParent : Landroid/support/v7/widget/DecorContentParent;
    //   61: invokeinterface isOverflowMenuShowing : ()Z
    //   66: ifne -> 100
    //   69: aload_0
    //   70: invokevirtual isDestroyed : ()Z
    //   73: ifne -> 188
    //   76: aload_0
    //   77: aload #4
    //   79: aload_2
    //   80: invokespecial preparePanel : (Landroid/support/v7/app/AppCompatDelegateImplV9$PanelFeatureState;Landroid/view/KeyEvent;)Z
    //   83: ifeq -> 188
    //   86: aload_0
    //   87: getfield mDecorContentParent : Landroid/support/v7/widget/DecorContentParent;
    //   90: invokeinterface showOverflowMenu : ()Z
    //   95: istore #6
    //   97: goto -> 208
    //   100: aload_0
    //   101: getfield mDecorContentParent : Landroid/support/v7/widget/DecorContentParent;
    //   104: invokeinterface hideOverflowMenu : ()Z
    //   109: istore #6
    //   111: goto -> 208
    //   114: aload #4
    //   116: getfield isOpen : Z
    //   119: ifne -> 194
    //   122: aload #4
    //   124: getfield isHandled : Z
    //   127: ifeq -> 133
    //   130: goto -> 194
    //   133: aload #4
    //   135: getfield isPrepared : Z
    //   138: ifeq -> 188
    //   141: aload #4
    //   143: getfield refreshMenuContent : Z
    //   146: ifeq -> 167
    //   149: aload #4
    //   151: iconst_0
    //   152: putfield isPrepared : Z
    //   155: aload_0
    //   156: aload #4
    //   158: aload_2
    //   159: invokespecial preparePanel : (Landroid/support/v7/app/AppCompatDelegateImplV9$PanelFeatureState;Landroid/view/KeyEvent;)Z
    //   162: istore #6
    //   164: goto -> 170
    //   167: iconst_1
    //   168: istore #6
    //   170: iload #6
    //   172: ifeq -> 188
    //   175: aload_0
    //   176: aload #4
    //   178: aload_2
    //   179: invokespecial openPanel : (Landroid/support/v7/app/AppCompatDelegateImplV9$PanelFeatureState;Landroid/view/KeyEvent;)V
    //   182: iload_3
    //   183: istore #6
    //   185: goto -> 208
    //   188: iconst_0
    //   189: istore #6
    //   191: goto -> 208
    //   194: aload #4
    //   196: getfield isOpen : Z
    //   199: istore #6
    //   201: aload_0
    //   202: aload #4
    //   204: iconst_1
    //   205: invokevirtual closePanel : (Landroid/support/v7/app/AppCompatDelegateImplV9$PanelFeatureState;Z)V
    //   208: iload #6
    //   210: ifeq -> 249
    //   213: aload_0
    //   214: getfield mContext : Landroid/content/Context;
    //   217: ldc_w 'audio'
    //   220: invokevirtual getSystemService : (Ljava/lang/String;)Ljava/lang/Object;
    //   223: checkcast android/media/AudioManager
    //   226: astore_2
    //   227: aload_2
    //   228: ifnull -> 239
    //   231: aload_2
    //   232: iconst_0
    //   233: invokevirtual playSoundEffect : (I)V
    //   236: goto -> 249
    //   239: ldc_w 'AppCompatDelegate'
    //   242: ldc_w 'Couldn't get audio manager'
    //   245: invokestatic w : (Ljava/lang/String;Ljava/lang/String;)I
    //   248: pop
    //   249: iload #6
    //   251: ireturn
  }
  
  private void openPanel(PanelFeatureState paramPanelFeatureState, KeyEvent paramKeyEvent) {
    // Byte code:
    //   0: aload_1
    //   1: getfield isOpen : Z
    //   4: ifne -> 411
    //   7: aload_0
    //   8: invokevirtual isDestroyed : ()Z
    //   11: ifeq -> 17
    //   14: goto -> 411
    //   17: aload_1
    //   18: getfield featureId : I
    //   21: ifne -> 56
    //   24: aload_0
    //   25: getfield mContext : Landroid/content/Context;
    //   28: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   31: invokevirtual getConfiguration : ()Landroid/content/res/Configuration;
    //   34: getfield screenLayout : I
    //   37: bipush #15
    //   39: iand
    //   40: iconst_4
    //   41: if_icmpne -> 49
    //   44: iconst_1
    //   45: istore_3
    //   46: goto -> 51
    //   49: iconst_0
    //   50: istore_3
    //   51: iload_3
    //   52: ifeq -> 56
    //   55: return
    //   56: aload_0
    //   57: invokevirtual getWindowCallback : ()Landroid/view/Window$Callback;
    //   60: astore #4
    //   62: aload #4
    //   64: ifnull -> 92
    //   67: aload #4
    //   69: aload_1
    //   70: getfield featureId : I
    //   73: aload_1
    //   74: getfield menu : Landroid/support/v7/view/menu/MenuBuilder;
    //   77: invokeinterface onMenuOpened : (ILandroid/view/Menu;)Z
    //   82: ifne -> 92
    //   85: aload_0
    //   86: aload_1
    //   87: iconst_1
    //   88: invokevirtual closePanel : (Landroid/support/v7/app/AppCompatDelegateImplV9$PanelFeatureState;Z)V
    //   91: return
    //   92: aload_0
    //   93: getfield mContext : Landroid/content/Context;
    //   96: ldc_w 'window'
    //   99: invokevirtual getSystemService : (Ljava/lang/String;)Ljava/lang/Object;
    //   102: checkcast android/view/WindowManager
    //   105: astore #5
    //   107: aload #5
    //   109: ifnonnull -> 113
    //   112: return
    //   113: aload_0
    //   114: aload_1
    //   115: aload_2
    //   116: invokespecial preparePanel : (Landroid/support/v7/app/AppCompatDelegateImplV9$PanelFeatureState;Landroid/view/KeyEvent;)Z
    //   119: ifne -> 123
    //   122: return
    //   123: aload_1
    //   124: getfield decorView : Landroid/view/ViewGroup;
    //   127: ifnull -> 172
    //   130: aload_1
    //   131: getfield refreshDecorView : Z
    //   134: ifeq -> 140
    //   137: goto -> 172
    //   140: aload_1
    //   141: getfield createdPanelView : Landroid/view/View;
    //   144: ifnull -> 343
    //   147: aload_1
    //   148: getfield createdPanelView : Landroid/view/View;
    //   151: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   154: astore_2
    //   155: aload_2
    //   156: ifnull -> 343
    //   159: aload_2
    //   160: getfield width : I
    //   163: iconst_m1
    //   164: if_icmpne -> 343
    //   167: iconst_m1
    //   168: istore_3
    //   169: goto -> 346
    //   172: aload_1
    //   173: getfield decorView : Landroid/view/ViewGroup;
    //   176: ifnonnull -> 195
    //   179: aload_0
    //   180: aload_1
    //   181: invokespecial initializePanelDecor : (Landroid/support/v7/app/AppCompatDelegateImplV9$PanelFeatureState;)Z
    //   184: ifeq -> 194
    //   187: aload_1
    //   188: getfield decorView : Landroid/view/ViewGroup;
    //   191: ifnonnull -> 219
    //   194: return
    //   195: aload_1
    //   196: getfield refreshDecorView : Z
    //   199: ifeq -> 219
    //   202: aload_1
    //   203: getfield decorView : Landroid/view/ViewGroup;
    //   206: invokevirtual getChildCount : ()I
    //   209: ifle -> 219
    //   212: aload_1
    //   213: getfield decorView : Landroid/view/ViewGroup;
    //   216: invokevirtual removeAllViews : ()V
    //   219: aload_0
    //   220: aload_1
    //   221: invokespecial initializePanelContent : (Landroid/support/v7/app/AppCompatDelegateImplV9$PanelFeatureState;)Z
    //   224: ifeq -> 411
    //   227: aload_1
    //   228: invokevirtual hasPanelItems : ()Z
    //   231: ifne -> 237
    //   234: goto -> 411
    //   237: aload_1
    //   238: getfield shownPanelView : Landroid/view/View;
    //   241: invokevirtual getLayoutParams : ()Landroid/view/ViewGroup$LayoutParams;
    //   244: astore #4
    //   246: aload #4
    //   248: astore_2
    //   249: aload #4
    //   251: ifnonnull -> 266
    //   254: new android/view/ViewGroup$LayoutParams
    //   257: dup
    //   258: bipush #-2
    //   260: bipush #-2
    //   262: invokespecial <init> : (II)V
    //   265: astore_2
    //   266: aload_1
    //   267: getfield background : I
    //   270: istore_3
    //   271: aload_1
    //   272: getfield decorView : Landroid/view/ViewGroup;
    //   275: iload_3
    //   276: invokevirtual setBackgroundResource : (I)V
    //   279: aload_1
    //   280: getfield shownPanelView : Landroid/view/View;
    //   283: invokevirtual getParent : ()Landroid/view/ViewParent;
    //   286: astore #4
    //   288: aload #4
    //   290: ifnull -> 313
    //   293: aload #4
    //   295: instanceof android/view/ViewGroup
    //   298: ifeq -> 313
    //   301: aload #4
    //   303: checkcast android/view/ViewGroup
    //   306: aload_1
    //   307: getfield shownPanelView : Landroid/view/View;
    //   310: invokevirtual removeView : (Landroid/view/View;)V
    //   313: aload_1
    //   314: getfield decorView : Landroid/view/ViewGroup;
    //   317: aload_1
    //   318: getfield shownPanelView : Landroid/view/View;
    //   321: aload_2
    //   322: invokevirtual addView : (Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V
    //   325: aload_1
    //   326: getfield shownPanelView : Landroid/view/View;
    //   329: invokevirtual hasFocus : ()Z
    //   332: ifne -> 343
    //   335: aload_1
    //   336: getfield shownPanelView : Landroid/view/View;
    //   339: invokevirtual requestFocus : ()Z
    //   342: pop
    //   343: bipush #-2
    //   345: istore_3
    //   346: aload_1
    //   347: iconst_0
    //   348: putfield isHandled : Z
    //   351: new android/view/WindowManager$LayoutParams
    //   354: dup
    //   355: iload_3
    //   356: bipush #-2
    //   358: aload_1
    //   359: getfield x : I
    //   362: aload_1
    //   363: getfield y : I
    //   366: sipush #1002
    //   369: ldc_w 8519680
    //   372: bipush #-3
    //   374: invokespecial <init> : (IIIIIII)V
    //   377: astore_2
    //   378: aload_2
    //   379: aload_1
    //   380: getfield gravity : I
    //   383: putfield gravity : I
    //   386: aload_2
    //   387: aload_1
    //   388: getfield windowAnimations : I
    //   391: putfield windowAnimations : I
    //   394: aload #5
    //   396: aload_1
    //   397: getfield decorView : Landroid/view/ViewGroup;
    //   400: aload_2
    //   401: invokeinterface addView : (Landroid/view/View;Landroid/view/ViewGroup$LayoutParams;)V
    //   406: aload_1
    //   407: iconst_1
    //   408: putfield isOpen : Z
    //   411: return
  }
  
  private boolean performPanelShortcut(PanelFeatureState paramPanelFeatureState, int paramInt1, KeyEvent paramKeyEvent, int paramInt2) {
    // Byte code:
    //   0: aload_3
    //   1: invokevirtual isSystem : ()Z
    //   4: istore #5
    //   6: iconst_0
    //   7: istore #6
    //   9: iload #5
    //   11: ifeq -> 16
    //   14: iconst_0
    //   15: ireturn
    //   16: aload_1
    //   17: getfield isPrepared : Z
    //   20: ifne -> 36
    //   23: iload #6
    //   25: istore #5
    //   27: aload_0
    //   28: aload_1
    //   29: aload_3
    //   30: invokespecial preparePanel : (Landroid/support/v7/app/AppCompatDelegateImplV9$PanelFeatureState;Landroid/view/KeyEvent;)Z
    //   33: ifeq -> 60
    //   36: iload #6
    //   38: istore #5
    //   40: aload_1
    //   41: getfield menu : Landroid/support/v7/view/menu/MenuBuilder;
    //   44: ifnull -> 60
    //   47: aload_1
    //   48: getfield menu : Landroid/support/v7/view/menu/MenuBuilder;
    //   51: iload_2
    //   52: aload_3
    //   53: iload #4
    //   55: invokevirtual performShortcut : (ILandroid/view/KeyEvent;I)Z
    //   58: istore #5
    //   60: iload #5
    //   62: ifeq -> 85
    //   65: iload #4
    //   67: iconst_1
    //   68: iand
    //   69: ifne -> 85
    //   72: aload_0
    //   73: getfield mDecorContentParent : Landroid/support/v7/widget/DecorContentParent;
    //   76: ifnonnull -> 85
    //   79: aload_0
    //   80: aload_1
    //   81: iconst_1
    //   82: invokevirtual closePanel : (Landroid/support/v7/app/AppCompatDelegateImplV9$PanelFeatureState;Z)V
    //   85: iload #5
    //   87: ireturn
  }
  
  private boolean preparePanel(PanelFeatureState paramPanelFeatureState, KeyEvent paramKeyEvent) {
    DecorContentParent decorContentParent;
    int i;
    if (isDestroyed())
      return false; 
    if (paramPanelFeatureState.isPrepared)
      return true; 
    PanelFeatureState panelFeatureState = this.mPreparedPanel;
    if (panelFeatureState != null && panelFeatureState != paramPanelFeatureState)
      closePanel(panelFeatureState, false); 
    Window.Callback callback = getWindowCallback();
    if (callback != null)
      paramPanelFeatureState.createdPanelView = callback.onCreatePanelView(paramPanelFeatureState.featureId); 
    if (paramPanelFeatureState.featureId == 0 || paramPanelFeatureState.featureId == 108) {
      i = 1;
    } else {
      i = 0;
    } 
    if (i) {
      DecorContentParent decorContentParent1 = this.mDecorContentParent;
      if (decorContentParent1 != null)
        decorContentParent1.setMenuPrepared(); 
    } 
    if (paramPanelFeatureState.createdPanelView == null && (!i || !(peekSupportActionBar() instanceof ToolbarActionBar))) {
      DecorContentParent decorContentParent1;
      boolean bool;
      if (paramPanelFeatureState.menu == null || paramPanelFeatureState.refreshMenuContent) {
        if (paramPanelFeatureState.menu == null && (!initializePanelMenu(paramPanelFeatureState) || paramPanelFeatureState.menu == null))
          return false; 
        if (i && this.mDecorContentParent != null) {
          if (this.mActionMenuPresenterCallback == null)
            this.mActionMenuPresenterCallback = new ActionMenuPresenterCallback(); 
          this.mDecorContentParent.setMenu((Menu)paramPanelFeatureState.menu, this.mActionMenuPresenterCallback);
        } 
        paramPanelFeatureState.menu.stopDispatchingItemsChanged();
        if (!callback.onCreatePanelMenu(paramPanelFeatureState.featureId, (Menu)paramPanelFeatureState.menu)) {
          paramPanelFeatureState.setMenu(null);
          if (i) {
            decorContentParent = this.mDecorContentParent;
            if (decorContentParent != null)
              decorContentParent.setMenu(null, this.mActionMenuPresenterCallback); 
          } 
          return false;
        } 
        ((PanelFeatureState)decorContentParent).refreshMenuContent = false;
      } 
      ((PanelFeatureState)decorContentParent).menu.stopDispatchingItemsChanged();
      if (((PanelFeatureState)decorContentParent).frozenActionViewState != null) {
        ((PanelFeatureState)decorContentParent).menu.restoreActionViewStates(((PanelFeatureState)decorContentParent).frozenActionViewState);
        ((PanelFeatureState)decorContentParent).frozenActionViewState = null;
      } 
      if (!callback.onPreparePanel(0, ((PanelFeatureState)decorContentParent).createdPanelView, (Menu)((PanelFeatureState)decorContentParent).menu)) {
        if (i) {
          decorContentParent1 = this.mDecorContentParent;
          if (decorContentParent1 != null)
            decorContentParent1.setMenu(null, this.mActionMenuPresenterCallback); 
        } 
        ((PanelFeatureState)decorContentParent).menu.startDispatchingItemsChanged();
        return false;
      } 
      if (decorContentParent1 != null) {
        i = decorContentParent1.getDeviceId();
      } else {
        i = -1;
      } 
      if (KeyCharacterMap.load(i).getKeyboardType() != 1) {
        bool = true;
      } else {
        bool = false;
      } 
      ((PanelFeatureState)decorContentParent).qwertyMode = bool;
      ((PanelFeatureState)decorContentParent).menu.setQwertyMode(((PanelFeatureState)decorContentParent).qwertyMode);
      ((PanelFeatureState)decorContentParent).menu.startDispatchingItemsChanged();
    } 
    ((PanelFeatureState)decorContentParent).isPrepared = true;
    ((PanelFeatureState)decorContentParent).isHandled = false;
    this.mPreparedPanel = (PanelFeatureState)decorContentParent;
    return true;
  }
  
  private void reopenMenu(MenuBuilder paramMenuBuilder, boolean paramBoolean) {
    DecorContentParent decorContentParent = this.mDecorContentParent;
    if (decorContentParent != null && decorContentParent.canShowOverflowMenu() && (!ViewConfiguration.get(this.mContext).hasPermanentMenuKey() || this.mDecorContentParent.isOverflowMenuShowPending())) {
      Window.Callback callback = getWindowCallback();
      if (!this.mDecorContentParent.isOverflowMenuShowing() || !paramBoolean) {
        if (callback != null && !isDestroyed()) {
          if (this.mInvalidatePanelMenuPosted && (this.mInvalidatePanelMenuFeatures & 0x1) != 0) {
            this.mWindow.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable);
            this.mInvalidatePanelMenuRunnable.run();
          } 
          PanelFeatureState panelFeatureState1 = getPanelState(0, true);
          if (panelFeatureState1.menu != null && !panelFeatureState1.refreshMenuContent && callback.onPreparePanel(0, panelFeatureState1.createdPanelView, (Menu)panelFeatureState1.menu)) {
            callback.onMenuOpened(108, (Menu)panelFeatureState1.menu);
            this.mDecorContentParent.showOverflowMenu();
          } 
        } 
        return;
      } 
      this.mDecorContentParent.hideOverflowMenu();
      if (!isDestroyed())
        callback.onPanelClosed(108, (Menu)(getPanelState(0, true)).menu); 
      return;
    } 
    PanelFeatureState panelFeatureState = getPanelState(0, true);
    panelFeatureState.refreshDecorView = true;
    closePanel(panelFeatureState, false);
    openPanel(panelFeatureState, (KeyEvent)null);
  }
  
  private int sanitizeWindowFeatureId(int paramInt) {
    if (paramInt == 8) {
      Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.");
      return 108;
    } 
    int i = paramInt;
    if (paramInt == 9) {
      Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.");
      i = 109;
    } 
    return i;
  }
  
  private boolean shouldInheritContext(ViewParent paramViewParent) {
    if (paramViewParent == null)
      return false; 
    View view = this.mWindow.getDecorView();
    while (true) {
      if (paramViewParent == null)
        return true; 
      if (paramViewParent == view || !(paramViewParent instanceof View) || ViewCompat.isAttachedToWindow((View)paramViewParent))
        break; 
      paramViewParent = paramViewParent.getParent();
    } 
    return false;
  }
  
  private void throwFeatureRequestIfSubDecorInstalled() {
    if (!this.mSubDecorInstalled)
      return; 
    throw new AndroidRuntimeException("Window feature must be requested before adding content");
  }
  
  public void addContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams) {
    ensureSubDecor();
    ((ViewGroup)this.mSubDecor.findViewById(16908290)).addView(paramView, paramLayoutParams);
    this.mOriginalWindowCallback.onContentChanged();
  }
  
  View callActivityOnCreateView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet) {
    if (this.mOriginalWindowCallback instanceof LayoutInflater.Factory) {
      paramView = ((LayoutInflater.Factory)this.mOriginalWindowCallback).onCreateView(paramString, paramContext, paramAttributeSet);
      if (paramView != null)
        return paramView; 
    } 
    return null;
  }
  
  void callOnPanelClosed(int paramInt, PanelFeatureState paramPanelFeatureState, Menu paramMenu) {
    MenuBuilder menuBuilder;
    PanelFeatureState panelFeatureState = paramPanelFeatureState;
    Menu menu = paramMenu;
    if (paramMenu == null) {
      PanelFeatureState panelFeatureState1 = paramPanelFeatureState;
      if (paramPanelFeatureState == null) {
        panelFeatureState1 = paramPanelFeatureState;
        if (paramInt >= 0) {
          PanelFeatureState[] arrayOfPanelFeatureState = this.mPanels;
          panelFeatureState1 = paramPanelFeatureState;
          if (paramInt < arrayOfPanelFeatureState.length)
            panelFeatureState1 = arrayOfPanelFeatureState[paramInt]; 
        } 
      } 
      panelFeatureState = panelFeatureState1;
      menu = paramMenu;
      if (panelFeatureState1 != null) {
        menuBuilder = panelFeatureState1.menu;
        panelFeatureState = panelFeatureState1;
      } 
    } 
    if (panelFeatureState != null && !panelFeatureState.isOpen)
      return; 
    if (!isDestroyed())
      this.mOriginalWindowCallback.onPanelClosed(paramInt, (Menu)menuBuilder); 
  }
  
  void checkCloseActionMenu(MenuBuilder paramMenuBuilder) {
    if (this.mClosingActionMenu)
      return; 
    this.mClosingActionMenu = true;
    this.mDecorContentParent.dismissPopups();
    Window.Callback callback = getWindowCallback();
    if (callback != null && !isDestroyed())
      callback.onPanelClosed(108, (Menu)paramMenuBuilder); 
    this.mClosingActionMenu = false;
  }
  
  void closePanel(int paramInt) {
    closePanel(getPanelState(paramInt, true), true);
  }
  
  void closePanel(PanelFeatureState paramPanelFeatureState, boolean paramBoolean) {
    if (paramBoolean && paramPanelFeatureState.featureId == 0) {
      DecorContentParent decorContentParent = this.mDecorContentParent;
      if (decorContentParent != null && decorContentParent.isOverflowMenuShowing()) {
        checkCloseActionMenu(paramPanelFeatureState.menu);
        return;
      } 
    } 
    WindowManager windowManager = (WindowManager)this.mContext.getSystemService("window");
    if (windowManager != null && paramPanelFeatureState.isOpen && paramPanelFeatureState.decorView != null) {
      windowManager.removeView((View)paramPanelFeatureState.decorView);
      if (paramBoolean)
        callOnPanelClosed(paramPanelFeatureState.featureId, paramPanelFeatureState, (Menu)null); 
    } 
    paramPanelFeatureState.isPrepared = false;
    paramPanelFeatureState.isHandled = false;
    paramPanelFeatureState.isOpen = false;
    paramPanelFeatureState.shownPanelView = null;
    paramPanelFeatureState.refreshDecorView = true;
    if (this.mPreparedPanel == paramPanelFeatureState)
      this.mPreparedPanel = null; 
  }
  
  public View createView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet) {
    AppCompatViewInflater appCompatViewInflater = this.mAppCompatViewInflater;
    boolean bool = false;
    if (appCompatViewInflater == null) {
      String str = this.mContext.obtainStyledAttributes(R.styleable.AppCompatTheme).getString(R.styleable.AppCompatTheme_viewInflaterClass);
      if (str == null || AppCompatViewInflater.class.getName().equals(str)) {
        this.mAppCompatViewInflater = new AppCompatViewInflater();
      } else {
        try {
          this.mAppCompatViewInflater = Class.forName(str).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } finally {
          appCompatViewInflater = null;
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Failed to instantiate custom view inflater ");
          stringBuilder.append(str);
          stringBuilder.append(". Falling back to default.");
          Log.i("AppCompatDelegate", stringBuilder.toString(), (Throwable)appCompatViewInflater);
        } 
      } 
    } 
    if (IS_PRE_LOLLIPOP) {
      if (paramAttributeSet instanceof XmlPullParser) {
        if (((XmlPullParser)paramAttributeSet).getDepth() > 1)
          bool = true; 
      } else {
        bool = shouldInheritContext((ViewParent)paramView);
      } 
    } else {
      bool = false;
    } 
    return this.mAppCompatViewInflater.createView(paramView, paramString, paramContext, paramAttributeSet, bool, IS_PRE_LOLLIPOP, true, VectorEnabledTintResources.shouldBeUsed());
  }
  
  void dismissPopups() {
    DecorContentParent decorContentParent = this.mDecorContentParent;
    if (decorContentParent != null)
      decorContentParent.dismissPopups(); 
    if (this.mActionModePopup != null) {
      this.mWindow.getDecorView().removeCallbacks(this.mShowActionModePopup);
      if (this.mActionModePopup.isShowing())
        try {
          this.mActionModePopup.dismiss();
        } catch (IllegalArgumentException illegalArgumentException) {} 
      this.mActionModePopup = null;
    } 
    endOnGoingFadeAnimation();
    PanelFeatureState panelFeatureState = getPanelState(0, false);
    if (panelFeatureState != null && panelFeatureState.menu != null)
      panelFeatureState.menu.close(); 
  }
  
  boolean dispatchKeyEvent(KeyEvent paramKeyEvent) {
    boolean bool1;
    int i = paramKeyEvent.getKeyCode();
    boolean bool = true;
    if (i == 82 && this.mOriginalWindowCallback.dispatchKeyEvent(paramKeyEvent))
      return true; 
    i = paramKeyEvent.getKeyCode();
    if (paramKeyEvent.getAction() != 0)
      bool = false; 
    if (bool) {
      bool1 = onKeyDown(i, paramKeyEvent);
    } else {
      bool1 = onKeyUp(i, paramKeyEvent);
    } 
    return bool1;
  }
  
  void doInvalidatePanelMenu(int paramInt) {
    PanelFeatureState panelFeatureState = getPanelState(paramInt, true);
    if (panelFeatureState.menu != null) {
      Bundle bundle = new Bundle();
      panelFeatureState.menu.saveActionViewStates(bundle);
      if (bundle.size() > 0)
        panelFeatureState.frozenActionViewState = bundle; 
      panelFeatureState.menu.stopDispatchingItemsChanged();
      panelFeatureState.menu.clear();
    } 
    panelFeatureState.refreshMenuContent = true;
    panelFeatureState.refreshDecorView = true;
    if ((paramInt == 108 || paramInt == 0) && this.mDecorContentParent != null) {
      PanelFeatureState panelFeatureState1 = getPanelState(0, false);
      if (panelFeatureState1 != null) {
        panelFeatureState1.isPrepared = false;
        preparePanel(panelFeatureState1, (KeyEvent)null);
      } 
    } 
  }
  
  void endOnGoingFadeAnimation() {
    ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = this.mFadeAnim;
    if (viewPropertyAnimatorCompat != null)
      viewPropertyAnimatorCompat.cancel(); 
  }
  
  PanelFeatureState findMenuPanel(Menu paramMenu) {
    byte b2;
    PanelFeatureState[] arrayOfPanelFeatureState = this.mPanels;
    byte b1 = 0;
    if (arrayOfPanelFeatureState != null) {
      b2 = arrayOfPanelFeatureState.length;
    } else {
      b2 = 0;
    } 
    while (b1 < b2) {
      PanelFeatureState panelFeatureState = arrayOfPanelFeatureState[b1];
      if (panelFeatureState != null && panelFeatureState.menu == paramMenu)
        return panelFeatureState; 
      b1++;
    } 
    return null;
  }
  
  public <T extends View> T findViewById(int paramInt) {
    ensureSubDecor();
    return (T)this.mWindow.findViewById(paramInt);
  }
  
  protected PanelFeatureState getPanelState(int paramInt, boolean paramBoolean) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mPanels : [Landroid/support/v7/app/AppCompatDelegateImplV9$PanelFeatureState;
    //   4: astore_3
    //   5: aload_3
    //   6: ifnull -> 18
    //   9: aload_3
    //   10: astore #4
    //   12: aload_3
    //   13: arraylength
    //   14: iload_1
    //   15: if_icmpgt -> 46
    //   18: iload_1
    //   19: iconst_1
    //   20: iadd
    //   21: anewarray android/support/v7/app/AppCompatDelegateImplV9$PanelFeatureState
    //   24: astore #4
    //   26: aload_3
    //   27: ifnull -> 40
    //   30: aload_3
    //   31: iconst_0
    //   32: aload #4
    //   34: iconst_0
    //   35: aload_3
    //   36: arraylength
    //   37: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
    //   40: aload_0
    //   41: aload #4
    //   43: putfield mPanels : [Landroid/support/v7/app/AppCompatDelegateImplV9$PanelFeatureState;
    //   46: aload #4
    //   48: iload_1
    //   49: aaload
    //   50: astore #5
    //   52: aload #5
    //   54: astore_3
    //   55: aload #5
    //   57: ifnonnull -> 74
    //   60: new android/support/v7/app/AppCompatDelegateImplV9$PanelFeatureState
    //   63: dup
    //   64: iload_1
    //   65: invokespecial <init> : (I)V
    //   68: astore_3
    //   69: aload #4
    //   71: iload_1
    //   72: aload_3
    //   73: aastore
    //   74: aload_3
    //   75: areturn
  }
  
  ViewGroup getSubDecor() {
    return this.mSubDecor;
  }
  
  public boolean hasWindowFeature(int paramInt) {
    paramInt = sanitizeWindowFeatureId(paramInt);
    return (paramInt != 1) ? ((paramInt != 2) ? ((paramInt != 5) ? ((paramInt != 10) ? ((paramInt != 108) ? ((paramInt != 109) ? false : this.mOverlayActionBar) : this.mHasActionBar) : this.mOverlayActionMode) : this.mFeatureIndeterminateProgress) : this.mFeatureProgress) : this.mWindowNoTitle;
  }
  
  public void initWindowDecorActionBar() {
    ensureSubDecor();
    if (this.mHasActionBar && this.mActionBar == null) {
      if (this.mOriginalWindowCallback instanceof Activity) {
        this.mActionBar = new WindowDecorActionBar((Activity)this.mOriginalWindowCallback, this.mOverlayActionBar);
      } else if (this.mOriginalWindowCallback instanceof Dialog) {
        this.mActionBar = new WindowDecorActionBar((Dialog)this.mOriginalWindowCallback);
      } 
      if (this.mActionBar != null)
        this.mActionBar.setDefaultDisplayHomeAsUpEnabled(this.mEnableDefaultActionBarUp); 
    } 
  }
  
  public void installViewFactory() {
    LayoutInflater layoutInflater = LayoutInflater.from(this.mContext);
    if (layoutInflater.getFactory() == null) {
      LayoutInflaterCompat.setFactory2(layoutInflater, this);
    } else if (!(layoutInflater.getFactory2() instanceof AppCompatDelegateImplV9)) {
      Log.i("AppCompatDelegate", "The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
    } 
  }
  
  public void invalidateOptionsMenu() {
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null && actionBar.invalidateOptionsMenu())
      return; 
    invalidatePanelMenu(0);
  }
  
  boolean onBackPressed() {
    ActionMode actionMode = this.mActionMode;
    if (actionMode != null) {
      actionMode.finish();
      return true;
    } 
    ActionBar actionBar = getSupportActionBar();
    return (actionBar != null && actionBar.collapseActionView());
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration) {
    if (this.mHasActionBar && this.mSubDecorInstalled) {
      ActionBar actionBar = getSupportActionBar();
      if (actionBar != null)
        actionBar.onConfigurationChanged(paramConfiguration); 
    } 
    AppCompatDrawableManager.get().onConfigurationChanged(this.mContext);
    applyDayNight();
  }
  
  public void onCreate(Bundle paramBundle) {
    if (this.mOriginalWindowCallback instanceof Activity && NavUtils.getParentActivityName((Activity)this.mOriginalWindowCallback) != null) {
      ActionBar actionBar = peekSupportActionBar();
      if (actionBar == null) {
        this.mEnableDefaultActionBarUp = true;
      } else {
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
      } 
    } 
  }
  
  public final View onCreateView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet) {
    View view = callActivityOnCreateView(paramView, paramString, paramContext, paramAttributeSet);
    return (view != null) ? view : createView(paramView, paramString, paramContext, paramAttributeSet);
  }
  
  public View onCreateView(String paramString, Context paramContext, AttributeSet paramAttributeSet) {
    return onCreateView((View)null, paramString, paramContext, paramAttributeSet);
  }
  
  public void onDestroy() {
    if (this.mInvalidatePanelMenuPosted)
      this.mWindow.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable); 
    super.onDestroy();
    if (this.mActionBar != null)
      this.mActionBar.onDestroy(); 
  }
  
  boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
    boolean bool = true;
    if (paramInt != 4) {
      if (paramInt == 82) {
        onKeyDownPanel(0, paramKeyEvent);
        return true;
      } 
    } else {
      if ((paramKeyEvent.getFlags() & 0x80) == 0)
        bool = false; 
      this.mLongPressBackDown = bool;
    } 
    return false;
  }
  
  boolean onKeyShortcut(int paramInt, KeyEvent paramKeyEvent) {
    PanelFeatureState panelFeatureState1;
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null && actionBar.onKeyShortcut(paramInt, paramKeyEvent))
      return true; 
    PanelFeatureState panelFeatureState2 = this.mPreparedPanel;
    if (panelFeatureState2 != null && performPanelShortcut(panelFeatureState2, paramKeyEvent.getKeyCode(), paramKeyEvent, 1)) {
      panelFeatureState1 = this.mPreparedPanel;
      if (panelFeatureState1 != null)
        panelFeatureState1.isHandled = true; 
      return true;
    } 
    if (this.mPreparedPanel == null) {
      panelFeatureState2 = getPanelState(0, true);
      preparePanel(panelFeatureState2, (KeyEvent)panelFeatureState1);
      boolean bool = performPanelShortcut(panelFeatureState2, panelFeatureState1.getKeyCode(), (KeyEvent)panelFeatureState1, 1);
      panelFeatureState2.isPrepared = false;
      if (bool)
        return true; 
    } 
    return false;
  }
  
  boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent) {
    if (paramInt != 4) {
      if (paramInt == 82) {
        onKeyUpPanel(0, paramKeyEvent);
        return true;
      } 
    } else {
      boolean bool = this.mLongPressBackDown;
      this.mLongPressBackDown = false;
      PanelFeatureState panelFeatureState = getPanelState(0, false);
      if (panelFeatureState != null && panelFeatureState.isOpen) {
        if (!bool)
          closePanel(panelFeatureState, true); 
        return true;
      } 
      if (onBackPressed())
        return true; 
    } 
    return false;
  }
  
  public boolean onMenuItemSelected(MenuBuilder paramMenuBuilder, MenuItem paramMenuItem) {
    Window.Callback callback = getWindowCallback();
    if (callback != null && !isDestroyed()) {
      PanelFeatureState panelFeatureState = findMenuPanel((Menu)paramMenuBuilder.getRootMenu());
      if (panelFeatureState != null)
        return callback.onMenuItemSelected(panelFeatureState.featureId, paramMenuItem); 
    } 
    return false;
  }
  
  public void onMenuModeChange(MenuBuilder paramMenuBuilder) {
    reopenMenu(paramMenuBuilder, true);
  }
  
  boolean onMenuOpened(int paramInt, Menu paramMenu) {
    if (paramInt == 108) {
      ActionBar actionBar = getSupportActionBar();
      if (actionBar != null)
        actionBar.dispatchMenuVisibilityChanged(true); 
      return true;
    } 
    return false;
  }
  
  void onPanelClosed(int paramInt, Menu paramMenu) {
    if (paramInt == 108) {
      ActionBar actionBar = getSupportActionBar();
      if (actionBar != null)
        actionBar.dispatchMenuVisibilityChanged(false); 
    } else if (paramInt == 0) {
      PanelFeatureState panelFeatureState = getPanelState(paramInt, true);
      if (panelFeatureState.isOpen)
        closePanel(panelFeatureState, false); 
    } 
  }
  
  public void onPostCreate(Bundle paramBundle) {
    ensureSubDecor();
  }
  
  public void onPostResume() {
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null)
      actionBar.setShowHideAnimationEnabled(true); 
  }
  
  public void onStop() {
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null)
      actionBar.setShowHideAnimationEnabled(false); 
  }
  
  void onSubDecorInstalled(ViewGroup paramViewGroup) {}
  
  void onTitleChanged(CharSequence paramCharSequence) {
    DecorContentParent decorContentParent = this.mDecorContentParent;
    if (decorContentParent != null) {
      decorContentParent.setWindowTitle(paramCharSequence);
    } else if (peekSupportActionBar() != null) {
      peekSupportActionBar().setWindowTitle(paramCharSequence);
    } else {
      TextView textView = this.mTitleView;
      if (textView != null)
        textView.setText(paramCharSequence); 
    } 
  }
  
  public boolean requestWindowFeature(int paramInt) {
    paramInt = sanitizeWindowFeatureId(paramInt);
    if (this.mWindowNoTitle && paramInt == 108)
      return false; 
    if (this.mHasActionBar && paramInt == 1)
      this.mHasActionBar = false; 
    if (paramInt != 1) {
      if (paramInt != 2) {
        if (paramInt != 5) {
          if (paramInt != 10) {
            if (paramInt != 108) {
              if (paramInt != 109)
                return this.mWindow.requestFeature(paramInt); 
              throwFeatureRequestIfSubDecorInstalled();
              this.mOverlayActionBar = true;
              return true;
            } 
            throwFeatureRequestIfSubDecorInstalled();
            this.mHasActionBar = true;
            return true;
          } 
          throwFeatureRequestIfSubDecorInstalled();
          this.mOverlayActionMode = true;
          return true;
        } 
        throwFeatureRequestIfSubDecorInstalled();
        this.mFeatureIndeterminateProgress = true;
        return true;
      } 
      throwFeatureRequestIfSubDecorInstalled();
      this.mFeatureProgress = true;
      return true;
    } 
    throwFeatureRequestIfSubDecorInstalled();
    this.mWindowNoTitle = true;
    return true;
  }
  
  public void setContentView(int paramInt) {
    ensureSubDecor();
    ViewGroup viewGroup = (ViewGroup)this.mSubDecor.findViewById(16908290);
    viewGroup.removeAllViews();
    LayoutInflater.from(this.mContext).inflate(paramInt, viewGroup);
    this.mOriginalWindowCallback.onContentChanged();
  }
  
  public void setContentView(View paramView) {
    ensureSubDecor();
    ViewGroup viewGroup = (ViewGroup)this.mSubDecor.findViewById(16908290);
    viewGroup.removeAllViews();
    viewGroup.addView(paramView);
    this.mOriginalWindowCallback.onContentChanged();
  }
  
  public void setContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams) {
    ensureSubDecor();
    ViewGroup viewGroup = (ViewGroup)this.mSubDecor.findViewById(16908290);
    viewGroup.removeAllViews();
    viewGroup.addView(paramView, paramLayoutParams);
    this.mOriginalWindowCallback.onContentChanged();
  }
  
  public void setSupportActionBar(Toolbar paramToolbar) {
    if (!(this.mOriginalWindowCallback instanceof Activity))
      return; 
    ActionBar actionBar = getSupportActionBar();
    if (!(actionBar instanceof WindowDecorActionBar)) {
      this.mMenuInflater = null;
      if (actionBar != null)
        actionBar.onDestroy(); 
      if (paramToolbar != null) {
        ToolbarActionBar toolbarActionBar = new ToolbarActionBar(paramToolbar, ((Activity)this.mOriginalWindowCallback).getTitle(), this.mAppCompatWindowCallback);
        this.mActionBar = toolbarActionBar;
        this.mWindow.setCallback(toolbarActionBar.getWrappedWindowCallback());
      } else {
        this.mActionBar = null;
        this.mWindow.setCallback(this.mAppCompatWindowCallback);
      } 
      invalidateOptionsMenu();
      return;
    } 
    throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.");
  }
  
  final boolean shouldAnimateActionModeView() {
    if (this.mSubDecorInstalled) {
      ViewGroup viewGroup = this.mSubDecor;
      if (viewGroup != null && ViewCompat.isLaidOut((View)viewGroup))
        return true; 
    } 
    return false;
  }
  
  public ActionMode startSupportActionMode(ActionMode.Callback paramCallback) {
    if (paramCallback != null) {
      ActionMode actionMode = this.mActionMode;
      if (actionMode != null)
        actionMode.finish(); 
      paramCallback = new ActionModeCallbackWrapperV9(paramCallback);
      ActionBar actionBar = getSupportActionBar();
      if (actionBar != null) {
        ActionMode actionMode1 = actionBar.startActionMode(paramCallback);
        this.mActionMode = actionMode1;
        if (actionMode1 != null && this.mAppCompatCallback != null)
          this.mAppCompatCallback.onSupportActionModeStarted(this.mActionMode); 
      } 
      if (this.mActionMode == null)
        this.mActionMode = startSupportActionModeFromWindow(paramCallback); 
      return this.mActionMode;
    } 
    throw new IllegalArgumentException("ActionMode callback can not be null.");
  }
  
  ActionMode startSupportActionModeFromWindow(ActionMode.Callback paramCallback) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual endOnGoingFadeAnimation : ()V
    //   4: aload_0
    //   5: getfield mActionMode : Landroid/support/v7/view/ActionMode;
    //   8: astore_2
    //   9: aload_2
    //   10: ifnull -> 17
    //   13: aload_2
    //   14: invokevirtual finish : ()V
    //   17: aload_1
    //   18: astore_2
    //   19: aload_1
    //   20: instanceof android/support/v7/app/AppCompatDelegateImplV9$ActionModeCallbackWrapperV9
    //   23: ifne -> 36
    //   26: new android/support/v7/app/AppCompatDelegateImplV9$ActionModeCallbackWrapperV9
    //   29: dup
    //   30: aload_0
    //   31: aload_1
    //   32: invokespecial <init> : (Landroid/support/v7/app/AppCompatDelegateImplV9;Landroid/support/v7/view/ActionMode$Callback;)V
    //   35: astore_2
    //   36: aload_0
    //   37: getfield mAppCompatCallback : Landroid/support/v7/app/AppCompatCallback;
    //   40: ifnull -> 64
    //   43: aload_0
    //   44: invokevirtual isDestroyed : ()Z
    //   47: ifne -> 64
    //   50: aload_0
    //   51: getfield mAppCompatCallback : Landroid/support/v7/app/AppCompatCallback;
    //   54: aload_2
    //   55: invokeinterface onWindowStartingSupportActionMode : (Landroid/support/v7/view/ActionMode$Callback;)Landroid/support/v7/view/ActionMode;
    //   60: astore_1
    //   61: goto -> 66
    //   64: aconst_null
    //   65: astore_1
    //   66: aload_1
    //   67: ifnull -> 78
    //   70: aload_0
    //   71: aload_1
    //   72: putfield mActionMode : Landroid/support/v7/view/ActionMode;
    //   75: goto -> 569
    //   78: aload_0
    //   79: getfield mActionModeView : Landroid/support/v7/widget/ActionBarContextView;
    //   82: astore_1
    //   83: iconst_1
    //   84: istore_3
    //   85: aload_1
    //   86: ifnonnull -> 352
    //   89: aload_0
    //   90: getfield mIsFloating : Z
    //   93: ifeq -> 312
    //   96: new android/util/TypedValue
    //   99: dup
    //   100: invokespecial <init> : ()V
    //   103: astore #4
    //   105: aload_0
    //   106: getfield mContext : Landroid/content/Context;
    //   109: invokevirtual getTheme : ()Landroid/content/res/Resources$Theme;
    //   112: astore_1
    //   113: aload_1
    //   114: getstatic android/support/v7/appcompat/R$attr.actionBarTheme : I
    //   117: aload #4
    //   119: iconst_1
    //   120: invokevirtual resolveAttribute : (ILandroid/util/TypedValue;Z)Z
    //   123: pop
    //   124: aload #4
    //   126: getfield resourceId : I
    //   129: ifeq -> 186
    //   132: aload_0
    //   133: getfield mContext : Landroid/content/Context;
    //   136: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   139: invokevirtual newTheme : ()Landroid/content/res/Resources$Theme;
    //   142: astore #5
    //   144: aload #5
    //   146: aload_1
    //   147: invokevirtual setTo : (Landroid/content/res/Resources$Theme;)V
    //   150: aload #5
    //   152: aload #4
    //   154: getfield resourceId : I
    //   157: iconst_1
    //   158: invokevirtual applyStyle : (IZ)V
    //   161: new android/support/v7/view/ContextThemeWrapper
    //   164: dup
    //   165: aload_0
    //   166: getfield mContext : Landroid/content/Context;
    //   169: iconst_0
    //   170: invokespecial <init> : (Landroid/content/Context;I)V
    //   173: astore_1
    //   174: aload_1
    //   175: invokevirtual getTheme : ()Landroid/content/res/Resources$Theme;
    //   178: aload #5
    //   180: invokevirtual setTo : (Landroid/content/res/Resources$Theme;)V
    //   183: goto -> 191
    //   186: aload_0
    //   187: getfield mContext : Landroid/content/Context;
    //   190: astore_1
    //   191: aload_0
    //   192: new android/support/v7/widget/ActionBarContextView
    //   195: dup
    //   196: aload_1
    //   197: invokespecial <init> : (Landroid/content/Context;)V
    //   200: putfield mActionModeView : Landroid/support/v7/widget/ActionBarContextView;
    //   203: new android/widget/PopupWindow
    //   206: dup
    //   207: aload_1
    //   208: aconst_null
    //   209: getstatic android/support/v7/appcompat/R$attr.actionModePopupWindowStyle : I
    //   212: invokespecial <init> : (Landroid/content/Context;Landroid/util/AttributeSet;I)V
    //   215: astore #5
    //   217: aload_0
    //   218: aload #5
    //   220: putfield mActionModePopup : Landroid/widget/PopupWindow;
    //   223: aload #5
    //   225: iconst_2
    //   226: invokestatic setWindowLayoutType : (Landroid/widget/PopupWindow;I)V
    //   229: aload_0
    //   230: getfield mActionModePopup : Landroid/widget/PopupWindow;
    //   233: aload_0
    //   234: getfield mActionModeView : Landroid/support/v7/widget/ActionBarContextView;
    //   237: invokevirtual setContentView : (Landroid/view/View;)V
    //   240: aload_0
    //   241: getfield mActionModePopup : Landroid/widget/PopupWindow;
    //   244: iconst_m1
    //   245: invokevirtual setWidth : (I)V
    //   248: aload_1
    //   249: invokevirtual getTheme : ()Landroid/content/res/Resources$Theme;
    //   252: getstatic android/support/v7/appcompat/R$attr.actionBarSize : I
    //   255: aload #4
    //   257: iconst_1
    //   258: invokevirtual resolveAttribute : (ILandroid/util/TypedValue;Z)Z
    //   261: pop
    //   262: aload #4
    //   264: getfield data : I
    //   267: aload_1
    //   268: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   271: invokevirtual getDisplayMetrics : ()Landroid/util/DisplayMetrics;
    //   274: invokestatic complexToDimensionPixelSize : (ILandroid/util/DisplayMetrics;)I
    //   277: istore #6
    //   279: aload_0
    //   280: getfield mActionModeView : Landroid/support/v7/widget/ActionBarContextView;
    //   283: iload #6
    //   285: invokevirtual setContentHeight : (I)V
    //   288: aload_0
    //   289: getfield mActionModePopup : Landroid/widget/PopupWindow;
    //   292: bipush #-2
    //   294: invokevirtual setHeight : (I)V
    //   297: aload_0
    //   298: new android/support/v7/app/AppCompatDelegateImplV9$5
    //   301: dup
    //   302: aload_0
    //   303: invokespecial <init> : (Landroid/support/v7/app/AppCompatDelegateImplV9;)V
    //   306: putfield mShowActionModePopup : Ljava/lang/Runnable;
    //   309: goto -> 352
    //   312: aload_0
    //   313: getfield mSubDecor : Landroid/view/ViewGroup;
    //   316: getstatic android/support/v7/appcompat/R$id.action_mode_bar_stub : I
    //   319: invokevirtual findViewById : (I)Landroid/view/View;
    //   322: checkcast android/support/v7/widget/ViewStubCompat
    //   325: astore_1
    //   326: aload_1
    //   327: ifnull -> 352
    //   330: aload_1
    //   331: aload_0
    //   332: invokevirtual getActionBarThemedContext : ()Landroid/content/Context;
    //   335: invokestatic from : (Landroid/content/Context;)Landroid/view/LayoutInflater;
    //   338: invokevirtual setLayoutInflater : (Landroid/view/LayoutInflater;)V
    //   341: aload_0
    //   342: aload_1
    //   343: invokevirtual inflate : ()Landroid/view/View;
    //   346: checkcast android/support/v7/widget/ActionBarContextView
    //   349: putfield mActionModeView : Landroid/support/v7/widget/ActionBarContextView;
    //   352: aload_0
    //   353: getfield mActionModeView : Landroid/support/v7/widget/ActionBarContextView;
    //   356: ifnull -> 569
    //   359: aload_0
    //   360: invokevirtual endOnGoingFadeAnimation : ()V
    //   363: aload_0
    //   364: getfield mActionModeView : Landroid/support/v7/widget/ActionBarContextView;
    //   367: invokevirtual killMode : ()V
    //   370: aload_0
    //   371: getfield mActionModeView : Landroid/support/v7/widget/ActionBarContextView;
    //   374: invokevirtual getContext : ()Landroid/content/Context;
    //   377: astore_1
    //   378: aload_0
    //   379: getfield mActionModeView : Landroid/support/v7/widget/ActionBarContextView;
    //   382: astore #4
    //   384: aload_0
    //   385: getfield mActionModePopup : Landroid/widget/PopupWindow;
    //   388: ifnonnull -> 394
    //   391: goto -> 396
    //   394: iconst_0
    //   395: istore_3
    //   396: new android/support/v7/view/StandaloneActionMode
    //   399: dup
    //   400: aload_1
    //   401: aload #4
    //   403: aload_2
    //   404: iload_3
    //   405: invokespecial <init> : (Landroid/content/Context;Landroid/support/v7/widget/ActionBarContextView;Landroid/support/v7/view/ActionMode$Callback;Z)V
    //   408: astore_1
    //   409: aload_2
    //   410: aload_1
    //   411: aload_1
    //   412: invokevirtual getMenu : ()Landroid/view/Menu;
    //   415: invokeinterface onCreateActionMode : (Landroid/support/v7/view/ActionMode;Landroid/view/Menu;)Z
    //   420: ifeq -> 564
    //   423: aload_1
    //   424: invokevirtual invalidate : ()V
    //   427: aload_0
    //   428: getfield mActionModeView : Landroid/support/v7/widget/ActionBarContextView;
    //   431: aload_1
    //   432: invokevirtual initForMode : (Landroid/support/v7/view/ActionMode;)V
    //   435: aload_0
    //   436: aload_1
    //   437: putfield mActionMode : Landroid/support/v7/view/ActionMode;
    //   440: aload_0
    //   441: invokevirtual shouldAnimateActionModeView : ()Z
    //   444: ifeq -> 488
    //   447: aload_0
    //   448: getfield mActionModeView : Landroid/support/v7/widget/ActionBarContextView;
    //   451: fconst_0
    //   452: invokevirtual setAlpha : (F)V
    //   455: aload_0
    //   456: getfield mActionModeView : Landroid/support/v7/widget/ActionBarContextView;
    //   459: invokestatic animate : (Landroid/view/View;)Landroid/support/v4/view/ViewPropertyAnimatorCompat;
    //   462: fconst_1
    //   463: invokevirtual alpha : (F)Landroid/support/v4/view/ViewPropertyAnimatorCompat;
    //   466: astore_1
    //   467: aload_0
    //   468: aload_1
    //   469: putfield mFadeAnim : Landroid/support/v4/view/ViewPropertyAnimatorCompat;
    //   472: aload_1
    //   473: new android/support/v7/app/AppCompatDelegateImplV9$6
    //   476: dup
    //   477: aload_0
    //   478: invokespecial <init> : (Landroid/support/v7/app/AppCompatDelegateImplV9;)V
    //   481: invokevirtual setListener : (Landroid/support/v4/view/ViewPropertyAnimatorListener;)Landroid/support/v4/view/ViewPropertyAnimatorCompat;
    //   484: pop
    //   485: goto -> 539
    //   488: aload_0
    //   489: getfield mActionModeView : Landroid/support/v7/widget/ActionBarContextView;
    //   492: fconst_1
    //   493: invokevirtual setAlpha : (F)V
    //   496: aload_0
    //   497: getfield mActionModeView : Landroid/support/v7/widget/ActionBarContextView;
    //   500: iconst_0
    //   501: invokevirtual setVisibility : (I)V
    //   504: aload_0
    //   505: getfield mActionModeView : Landroid/support/v7/widget/ActionBarContextView;
    //   508: bipush #32
    //   510: invokevirtual sendAccessibilityEvent : (I)V
    //   513: aload_0
    //   514: getfield mActionModeView : Landroid/support/v7/widget/ActionBarContextView;
    //   517: invokevirtual getParent : ()Landroid/view/ViewParent;
    //   520: instanceof android/view/View
    //   523: ifeq -> 539
    //   526: aload_0
    //   527: getfield mActionModeView : Landroid/support/v7/widget/ActionBarContextView;
    //   530: invokevirtual getParent : ()Landroid/view/ViewParent;
    //   533: checkcast android/view/View
    //   536: invokestatic requestApplyInsets : (Landroid/view/View;)V
    //   539: aload_0
    //   540: getfield mActionModePopup : Landroid/widget/PopupWindow;
    //   543: ifnull -> 569
    //   546: aload_0
    //   547: getfield mWindow : Landroid/view/Window;
    //   550: invokevirtual getDecorView : ()Landroid/view/View;
    //   553: aload_0
    //   554: getfield mShowActionModePopup : Ljava/lang/Runnable;
    //   557: invokevirtual post : (Ljava/lang/Runnable;)Z
    //   560: pop
    //   561: goto -> 569
    //   564: aload_0
    //   565: aconst_null
    //   566: putfield mActionMode : Landroid/support/v7/view/ActionMode;
    //   569: aload_0
    //   570: getfield mActionMode : Landroid/support/v7/view/ActionMode;
    //   573: ifnull -> 596
    //   576: aload_0
    //   577: getfield mAppCompatCallback : Landroid/support/v7/app/AppCompatCallback;
    //   580: ifnull -> 596
    //   583: aload_0
    //   584: getfield mAppCompatCallback : Landroid/support/v7/app/AppCompatCallback;
    //   587: aload_0
    //   588: getfield mActionMode : Landroid/support/v7/view/ActionMode;
    //   591: invokeinterface onSupportActionModeStarted : (Landroid/support/v7/view/ActionMode;)V
    //   596: aload_0
    //   597: getfield mActionMode : Landroid/support/v7/view/ActionMode;
    //   600: areturn
    //   601: astore_1
    //   602: goto -> 64
    // Exception table:
    //   from	to	target	type
    //   50	61	601	java/lang/AbstractMethodError
  }
  
  int updateStatusGuard(int paramInt) {
    int i;
    boolean bool2;
    ActionBarContextView actionBarContextView = this.mActionModeView;
    boolean bool1 = false;
    if (actionBarContextView != null && actionBarContextView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
      boolean bool3;
      ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)this.mActionModeView.getLayoutParams();
      boolean bool = this.mActionModeView.isShown();
      i = 1;
      int j = 1;
      if (bool) {
        if (this.mTempRect1 == null) {
          this.mTempRect1 = new Rect();
          this.mTempRect2 = new Rect();
        } 
        Rect rect1 = this.mTempRect1;
        Rect rect2 = this.mTempRect2;
        rect1.set(0, paramInt, 0, 0);
        ViewUtils.computeFitSystemWindows((View)this.mSubDecor, rect1, rect2);
        if (rect2.top == 0) {
          bool3 = paramInt;
        } else {
          bool3 = false;
        } 
        if (marginLayoutParams.topMargin != bool3) {
          marginLayoutParams.topMargin = paramInt;
          View view1 = this.mStatusGuard;
          if (view1 == null) {
            view1 = new View(this.mContext);
            this.mStatusGuard = view1;
            view1.setBackgroundColor(this.mContext.getResources().getColor(R.color.abc_input_method_navigation_guard));
            this.mSubDecor.addView(this.mStatusGuard, -1, new ViewGroup.LayoutParams(-1, paramInt));
          } else {
            ViewGroup.LayoutParams layoutParams = view1.getLayoutParams();
            if (layoutParams.height != paramInt) {
              layoutParams.height = paramInt;
              this.mStatusGuard.setLayoutParams(layoutParams);
            } 
          } 
          bool3 = true;
        } else {
          bool3 = false;
        } 
        if (this.mStatusGuard == null)
          j = 0; 
        i = paramInt;
        if (!this.mOverlayActionMode) {
          i = paramInt;
          if (j)
            i = 0; 
        } 
        paramInt = bool3;
        bool3 = j;
        j = paramInt;
        paramInt = i;
      } else if (marginLayoutParams.topMargin != 0) {
        marginLayoutParams.topMargin = 0;
        bool3 = false;
        j = i;
      } else {
        bool3 = false;
        j = 0;
      } 
      bool2 = bool3;
      i = paramInt;
      if (j != 0) {
        this.mActionModeView.setLayoutParams((ViewGroup.LayoutParams)marginLayoutParams);
        bool2 = bool3;
        i = paramInt;
      } 
    } else {
      bool2 = false;
      i = paramInt;
    } 
    View view = this.mStatusGuard;
    if (view != null) {
      if (bool2) {
        paramInt = bool1;
      } else {
        paramInt = 8;
      } 
      view.setVisibility(paramInt);
    } 
    return i;
  }
  
  private final class ActionMenuPresenterCallback implements MenuPresenter.Callback {
    public void onCloseMenu(MenuBuilder param1MenuBuilder, boolean param1Boolean) {
      AppCompatDelegateImplV9.this.checkCloseActionMenu(param1MenuBuilder);
    }
    
    public boolean onOpenSubMenu(MenuBuilder param1MenuBuilder) {
      Window.Callback callback = AppCompatDelegateImplV9.this.getWindowCallback();
      if (callback != null)
        callback.onMenuOpened(108, (Menu)param1MenuBuilder); 
      return true;
    }
  }
  
  class ActionModeCallbackWrapperV9 implements ActionMode.Callback {
    private ActionMode.Callback mWrapped;
    
    public ActionModeCallbackWrapperV9(ActionMode.Callback param1Callback) {
      this.mWrapped = param1Callback;
    }
    
    public boolean onActionItemClicked(ActionMode param1ActionMode, MenuItem param1MenuItem) {
      return this.mWrapped.onActionItemClicked(param1ActionMode, param1MenuItem);
    }
    
    public boolean onCreateActionMode(ActionMode param1ActionMode, Menu param1Menu) {
      return this.mWrapped.onCreateActionMode(param1ActionMode, param1Menu);
    }
    
    public void onDestroyActionMode(ActionMode param1ActionMode) {
      this.mWrapped.onDestroyActionMode(param1ActionMode);
      if (AppCompatDelegateImplV9.this.mActionModePopup != null)
        AppCompatDelegateImplV9.this.mWindow.getDecorView().removeCallbacks(AppCompatDelegateImplV9.this.mShowActionModePopup); 
      if (AppCompatDelegateImplV9.this.mActionModeView != null) {
        AppCompatDelegateImplV9.this.endOnGoingFadeAnimation();
        AppCompatDelegateImplV9 appCompatDelegateImplV9 = AppCompatDelegateImplV9.this;
        appCompatDelegateImplV9.mFadeAnim = ViewCompat.animate((View)appCompatDelegateImplV9.mActionModeView).alpha(0.0F);
        AppCompatDelegateImplV9.this.mFadeAnim.setListener((ViewPropertyAnimatorListener)new ViewPropertyAnimatorListenerAdapter() {
              public void onAnimationEnd(View param2View) {
                AppCompatDelegateImplV9.this.mActionModeView.setVisibility(8);
                if (AppCompatDelegateImplV9.this.mActionModePopup != null) {
                  AppCompatDelegateImplV9.this.mActionModePopup.dismiss();
                } else if (AppCompatDelegateImplV9.this.mActionModeView.getParent() instanceof View) {
                  ViewCompat.requestApplyInsets((View)AppCompatDelegateImplV9.this.mActionModeView.getParent());
                } 
                AppCompatDelegateImplV9.this.mActionModeView.removeAllViews();
                AppCompatDelegateImplV9.this.mFadeAnim.setListener(null);
                AppCompatDelegateImplV9.this.mFadeAnim = null;
              }
            });
      } 
      if (AppCompatDelegateImplV9.this.mAppCompatCallback != null)
        AppCompatDelegateImplV9.this.mAppCompatCallback.onSupportActionModeFinished(AppCompatDelegateImplV9.this.mActionMode); 
      AppCompatDelegateImplV9.this.mActionMode = null;
    }
    
    public boolean onPrepareActionMode(ActionMode param1ActionMode, Menu param1Menu) {
      return this.mWrapped.onPrepareActionMode(param1ActionMode, param1Menu);
    }
  }
  
  class null extends ViewPropertyAnimatorListenerAdapter {
    public void onAnimationEnd(View param1View) {
      AppCompatDelegateImplV9.this.mActionModeView.setVisibility(8);
      if (AppCompatDelegateImplV9.this.mActionModePopup != null) {
        AppCompatDelegateImplV9.this.mActionModePopup.dismiss();
      } else if (AppCompatDelegateImplV9.this.mActionModeView.getParent() instanceof View) {
        ViewCompat.requestApplyInsets((View)AppCompatDelegateImplV9.this.mActionModeView.getParent());
      } 
      AppCompatDelegateImplV9.this.mActionModeView.removeAllViews();
      AppCompatDelegateImplV9.this.mFadeAnim.setListener(null);
      AppCompatDelegateImplV9.this.mFadeAnim = null;
    }
  }
  
  private class ListMenuDecorView extends ContentFrameLayout {
    public ListMenuDecorView(Context param1Context) {
      super(param1Context);
    }
    
    private boolean isOutOfBounds(int param1Int1, int param1Int2) {
      return (param1Int1 < -5 || param1Int2 < -5 || param1Int1 > getWidth() + 5 || param1Int2 > getHeight() + 5);
    }
    
    public boolean dispatchKeyEvent(KeyEvent param1KeyEvent) {
      return (AppCompatDelegateImplV9.this.dispatchKeyEvent(param1KeyEvent) || super.dispatchKeyEvent(param1KeyEvent));
    }
    
    public boolean onInterceptTouchEvent(MotionEvent param1MotionEvent) {
      if (param1MotionEvent.getAction() == 0 && isOutOfBounds((int)param1MotionEvent.getX(), (int)param1MotionEvent.getY())) {
        AppCompatDelegateImplV9.this.closePanel(0);
        return true;
      } 
      return super.onInterceptTouchEvent(param1MotionEvent);
    }
    
    public void setBackgroundResource(int param1Int) {
      setBackgroundDrawable(AppCompatResources.getDrawable(getContext(), param1Int));
    }
  }
  
  protected static final class PanelFeatureState {
    int background;
    
    View createdPanelView;
    
    ViewGroup decorView;
    
    int featureId;
    
    Bundle frozenActionViewState;
    
    Bundle frozenMenuState;
    
    int gravity;
    
    boolean isHandled;
    
    boolean isOpen;
    
    boolean isPrepared;
    
    ListMenuPresenter listMenuPresenter;
    
    Context listPresenterContext;
    
    MenuBuilder menu;
    
    public boolean qwertyMode;
    
    boolean refreshDecorView;
    
    boolean refreshMenuContent;
    
    View shownPanelView;
    
    boolean wasLastOpen;
    
    int windowAnimations;
    
    int x;
    
    int y;
    
    PanelFeatureState(int param1Int) {
      this.featureId = param1Int;
      this.refreshDecorView = false;
    }
    
    void applyFrozenState() {
      MenuBuilder menuBuilder = this.menu;
      if (menuBuilder != null) {
        Bundle bundle = this.frozenMenuState;
        if (bundle != null) {
          menuBuilder.restorePresenterStates(bundle);
          this.frozenMenuState = null;
        } 
      } 
    }
    
    public void clearMenuPresenters() {
      MenuBuilder menuBuilder = this.menu;
      if (menuBuilder != null)
        menuBuilder.removeMenuPresenter((MenuPresenter)this.listMenuPresenter); 
      this.listMenuPresenter = null;
    }
    
    MenuView getListMenuView(MenuPresenter.Callback param1Callback) {
      if (this.menu == null)
        return null; 
      if (this.listMenuPresenter == null) {
        ListMenuPresenter listMenuPresenter = new ListMenuPresenter(this.listPresenterContext, R.layout.abc_list_menu_item_layout);
        this.listMenuPresenter = listMenuPresenter;
        listMenuPresenter.setCallback(param1Callback);
        this.menu.addMenuPresenter((MenuPresenter)this.listMenuPresenter);
      } 
      return this.listMenuPresenter.getMenuView(this.decorView);
    }
    
    public boolean hasPanelItems() {
      View view = this.shownPanelView;
      boolean bool = false;
      if (view == null)
        return false; 
      if (this.createdPanelView != null)
        return true; 
      if (this.listMenuPresenter.getAdapter().getCount() > 0)
        bool = true; 
      return bool;
    }
    
    void onRestoreInstanceState(Parcelable param1Parcelable) {
      param1Parcelable = param1Parcelable;
      this.featureId = ((SavedState)param1Parcelable).featureId;
      this.wasLastOpen = ((SavedState)param1Parcelable).isOpen;
      this.frozenMenuState = ((SavedState)param1Parcelable).menuState;
      this.shownPanelView = null;
      this.decorView = null;
    }
    
    Parcelable onSaveInstanceState() {
      SavedState savedState = new SavedState();
      savedState.featureId = this.featureId;
      savedState.isOpen = this.isOpen;
      if (this.menu != null) {
        savedState.menuState = new Bundle();
        this.menu.savePresenterStates(savedState.menuState);
      } 
      return savedState;
    }
    
    void setMenu(MenuBuilder param1MenuBuilder) {
      MenuBuilder menuBuilder = this.menu;
      if (param1MenuBuilder == menuBuilder)
        return; 
      if (menuBuilder != null)
        menuBuilder.removeMenuPresenter((MenuPresenter)this.listMenuPresenter); 
      this.menu = param1MenuBuilder;
      if (param1MenuBuilder != null) {
        ListMenuPresenter listMenuPresenter = this.listMenuPresenter;
        if (listMenuPresenter != null)
          param1MenuBuilder.addMenuPresenter((MenuPresenter)listMenuPresenter); 
      } 
    }
    
    void setStyle(Context param1Context) {
      TypedValue typedValue = new TypedValue();
      Resources.Theme theme = param1Context.getResources().newTheme();
      theme.setTo(param1Context.getTheme());
      theme.resolveAttribute(R.attr.actionBarPopupTheme, typedValue, true);
      if (typedValue.resourceId != 0)
        theme.applyStyle(typedValue.resourceId, true); 
      theme.resolveAttribute(R.attr.panelMenuListTheme, typedValue, true);
      if (typedValue.resourceId != 0) {
        theme.applyStyle(typedValue.resourceId, true);
      } else {
        theme.applyStyle(R.style.Theme_AppCompat_CompactMenu, true);
      } 
      ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(param1Context, 0);
      contextThemeWrapper.getTheme().setTo(theme);
      this.listPresenterContext = (Context)contextThemeWrapper;
      TypedArray typedArray = contextThemeWrapper.obtainStyledAttributes(R.styleable.AppCompatTheme);
      this.background = typedArray.getResourceId(R.styleable.AppCompatTheme_panelBackground, 0);
      this.windowAnimations = typedArray.getResourceId(R.styleable.AppCompatTheme_android_windowAnimationStyle, 0);
      typedArray.recycle();
    }
    
    private static class SavedState implements Parcelable {
      public static final Parcelable.Creator<SavedState> CREATOR = (Parcelable.Creator<SavedState>)new Parcelable.ClassLoaderCreator<SavedState>() {
          public AppCompatDelegateImplV9.PanelFeatureState.SavedState createFromParcel(Parcel param3Parcel) {
            return AppCompatDelegateImplV9.PanelFeatureState.SavedState.readFromParcel(param3Parcel, null);
          }
          
          public AppCompatDelegateImplV9.PanelFeatureState.SavedState createFromParcel(Parcel param3Parcel, ClassLoader param3ClassLoader) {
            return AppCompatDelegateImplV9.PanelFeatureState.SavedState.readFromParcel(param3Parcel, param3ClassLoader);
          }
          
          public AppCompatDelegateImplV9.PanelFeatureState.SavedState[] newArray(int param3Int) {
            return new AppCompatDelegateImplV9.PanelFeatureState.SavedState[param3Int];
          }
        };
      
      int featureId;
      
      boolean isOpen;
      
      Bundle menuState;
      
      static SavedState readFromParcel(Parcel param2Parcel, ClassLoader param2ClassLoader) {
        SavedState savedState = new SavedState();
        savedState.featureId = param2Parcel.readInt();
        int i = param2Parcel.readInt();
        boolean bool = true;
        if (i != 1)
          bool = false; 
        savedState.isOpen = bool;
        if (bool)
          savedState.menuState = param2Parcel.readBundle(param2ClassLoader); 
        return savedState;
      }
      
      public int describeContents() {
        return 0;
      }
      
      public void writeToParcel(Parcel param2Parcel, int param2Int) {
        param2Parcel.writeInt(this.featureId);
        param2Parcel.writeInt(this.isOpen);
        if (this.isOpen)
          param2Parcel.writeBundle(this.menuState); 
      }
    }
    
    static final class null implements Parcelable.ClassLoaderCreator<SavedState> {
      public AppCompatDelegateImplV9.PanelFeatureState.SavedState createFromParcel(Parcel param2Parcel) {
        return AppCompatDelegateImplV9.PanelFeatureState.SavedState.readFromParcel(param2Parcel, null);
      }
      
      public AppCompatDelegateImplV9.PanelFeatureState.SavedState createFromParcel(Parcel param2Parcel, ClassLoader param2ClassLoader) {
        return AppCompatDelegateImplV9.PanelFeatureState.SavedState.readFromParcel(param2Parcel, param2ClassLoader);
      }
      
      public AppCompatDelegateImplV9.PanelFeatureState.SavedState[] newArray(int param2Int) {
        return new AppCompatDelegateImplV9.PanelFeatureState.SavedState[param2Int];
      }
    }
  }
  
  private static class SavedState implements Parcelable {
    public static final Parcelable.Creator<SavedState> CREATOR = (Parcelable.Creator<SavedState>)new Parcelable.ClassLoaderCreator<SavedState>() {
        public AppCompatDelegateImplV9.PanelFeatureState.SavedState createFromParcel(Parcel param3Parcel) {
          return AppCompatDelegateImplV9.PanelFeatureState.SavedState.readFromParcel(param3Parcel, null);
        }
        
        public AppCompatDelegateImplV9.PanelFeatureState.SavedState createFromParcel(Parcel param3Parcel, ClassLoader param3ClassLoader) {
          return AppCompatDelegateImplV9.PanelFeatureState.SavedState.readFromParcel(param3Parcel, param3ClassLoader);
        }
        
        public AppCompatDelegateImplV9.PanelFeatureState.SavedState[] newArray(int param3Int) {
          return new AppCompatDelegateImplV9.PanelFeatureState.SavedState[param3Int];
        }
      };
    
    int featureId;
    
    boolean isOpen;
    
    Bundle menuState;
    
    static SavedState readFromParcel(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      SavedState savedState = new SavedState();
      savedState.featureId = param1Parcel.readInt();
      int i = param1Parcel.readInt();
      boolean bool = true;
      if (i != 1)
        bool = false; 
      savedState.isOpen = bool;
      if (bool)
        savedState.menuState = param1Parcel.readBundle(param1ClassLoader); 
      return savedState;
    }
    
    public int describeContents() {
      return 0;
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      param1Parcel.writeInt(this.featureId);
      param1Parcel.writeInt(this.isOpen);
      if (this.isOpen)
        param1Parcel.writeBundle(this.menuState); 
    }
  }
  
  static final class null implements Parcelable.ClassLoaderCreator<PanelFeatureState.SavedState> {
    public AppCompatDelegateImplV9.PanelFeatureState.SavedState createFromParcel(Parcel param1Parcel) {
      return AppCompatDelegateImplV9.PanelFeatureState.SavedState.readFromParcel(param1Parcel, null);
    }
    
    public AppCompatDelegateImplV9.PanelFeatureState.SavedState createFromParcel(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      return AppCompatDelegateImplV9.PanelFeatureState.SavedState.readFromParcel(param1Parcel, param1ClassLoader);
    }
    
    public AppCompatDelegateImplV9.PanelFeatureState.SavedState[] newArray(int param1Int) {
      return new AppCompatDelegateImplV9.PanelFeatureState.SavedState[param1Int];
    }
  }
  
  private final class PanelMenuPresenterCallback implements MenuPresenter.Callback {
    public void onCloseMenu(MenuBuilder param1MenuBuilder, boolean param1Boolean) {
      boolean bool;
      MenuBuilder menuBuilder = param1MenuBuilder.getRootMenu();
      if (menuBuilder != param1MenuBuilder) {
        bool = true;
      } else {
        bool = false;
      } 
      AppCompatDelegateImplV9 appCompatDelegateImplV9 = AppCompatDelegateImplV9.this;
      if (bool)
        param1MenuBuilder = menuBuilder; 
      AppCompatDelegateImplV9.PanelFeatureState panelFeatureState = appCompatDelegateImplV9.findMenuPanel((Menu)param1MenuBuilder);
      if (panelFeatureState != null)
        if (bool) {
          AppCompatDelegateImplV9.this.callOnPanelClosed(panelFeatureState.featureId, panelFeatureState, (Menu)menuBuilder);
          AppCompatDelegateImplV9.this.closePanel(panelFeatureState, true);
        } else {
          AppCompatDelegateImplV9.this.closePanel(panelFeatureState, param1Boolean);
        }  
    }
    
    public boolean onOpenSubMenu(MenuBuilder param1MenuBuilder) {
      if (param1MenuBuilder == null && AppCompatDelegateImplV9.this.mHasActionBar) {
        Window.Callback callback = AppCompatDelegateImplV9.this.getWindowCallback();
        if (callback != null && !AppCompatDelegateImplV9.this.isDestroyed())
          callback.onMenuOpened(108, (Menu)param1MenuBuilder); 
      } 
      return true;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\app\AppCompatDelegateImplV9.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */