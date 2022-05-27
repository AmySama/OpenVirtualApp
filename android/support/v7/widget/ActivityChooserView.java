package android.support.v7.widget;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.ShowableListMenu;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;

public class ActivityChooserView extends ViewGroup implements ActivityChooserModel.ActivityChooserModelClient {
  private static final String LOG_TAG = "ActivityChooserView";
  
  private final View mActivityChooserContent;
  
  private final Drawable mActivityChooserContentBackground;
  
  final ActivityChooserViewAdapter mAdapter;
  
  private final Callbacks mCallbacks;
  
  private int mDefaultActionButtonContentDescription;
  
  final FrameLayout mDefaultActivityButton;
  
  private final ImageView mDefaultActivityButtonImage;
  
  final FrameLayout mExpandActivityOverflowButton;
  
  private final ImageView mExpandActivityOverflowButtonImage;
  
  int mInitialActivityCount = 4;
  
  private boolean mIsAttachedToWindow;
  
  boolean mIsSelectingDefaultActivity;
  
  private final int mListPopupMaxWidth;
  
  private ListPopupWindow mListPopupWindow;
  
  final DataSetObserver mModelDataSetObserver = new DataSetObserver() {
      public void onChanged() {
        super.onChanged();
        ActivityChooserView.this.mAdapter.notifyDataSetChanged();
      }
      
      public void onInvalidated() {
        super.onInvalidated();
        ActivityChooserView.this.mAdapter.notifyDataSetInvalidated();
      }
    };
  
  PopupWindow.OnDismissListener mOnDismissListener;
  
  private final ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
      public void onGlobalLayout() {
        if (ActivityChooserView.this.isShowingPopup())
          if (!ActivityChooserView.this.isShown()) {
            ActivityChooserView.this.getListPopupWindow().dismiss();
          } else {
            ActivityChooserView.this.getListPopupWindow().show();
            if (ActivityChooserView.this.mProvider != null)
              ActivityChooserView.this.mProvider.subUiVisibilityChanged(true); 
          }  
      }
    };
  
  ActionProvider mProvider;
  
  public ActivityChooserView(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public ActivityChooserView(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public ActivityChooserView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ActivityChooserView, paramInt, 0);
    this.mInitialActivityCount = typedArray.getInt(R.styleable.ActivityChooserView_initialActivityCount, 4);
    Drawable drawable = typedArray.getDrawable(R.styleable.ActivityChooserView_expandActivityOverflowButtonDrawable);
    typedArray.recycle();
    LayoutInflater.from(getContext()).inflate(R.layout.abc_activity_chooser_view, this, true);
    this.mCallbacks = new Callbacks();
    View view = findViewById(R.id.activity_chooser_view_content);
    this.mActivityChooserContent = view;
    this.mActivityChooserContentBackground = view.getBackground();
    FrameLayout frameLayout = (FrameLayout)findViewById(R.id.default_activity_button);
    this.mDefaultActivityButton = frameLayout;
    frameLayout.setOnClickListener(this.mCallbacks);
    this.mDefaultActivityButton.setOnLongClickListener(this.mCallbacks);
    this.mDefaultActivityButtonImage = (ImageView)this.mDefaultActivityButton.findViewById(R.id.image);
    frameLayout = (FrameLayout)findViewById(R.id.expand_activities_button);
    frameLayout.setOnClickListener(this.mCallbacks);
    frameLayout.setAccessibilityDelegate(new View.AccessibilityDelegate() {
          public void onInitializeAccessibilityNodeInfo(View param1View, AccessibilityNodeInfo param1AccessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(param1View, param1AccessibilityNodeInfo);
            AccessibilityNodeInfoCompat.wrap(param1AccessibilityNodeInfo).setCanOpenPopup(true);
          }
        });
    frameLayout.setOnTouchListener(new ForwardingListener((View)frameLayout) {
          public ShowableListMenu getPopup() {
            return ActivityChooserView.this.getListPopupWindow();
          }
          
          protected boolean onForwardingStarted() {
            ActivityChooserView.this.showPopup();
            return true;
          }
          
          protected boolean onForwardingStopped() {
            ActivityChooserView.this.dismissPopup();
            return true;
          }
        });
    this.mExpandActivityOverflowButton = frameLayout;
    ImageView imageView = (ImageView)frameLayout.findViewById(R.id.image);
    this.mExpandActivityOverflowButtonImage = imageView;
    imageView.setImageDrawable(drawable);
    ActivityChooserViewAdapter activityChooserViewAdapter = new ActivityChooserViewAdapter();
    this.mAdapter = activityChooserViewAdapter;
    activityChooserViewAdapter.registerDataSetObserver(new DataSetObserver() {
          public void onChanged() {
            super.onChanged();
            ActivityChooserView.this.updateAppearance();
          }
        });
    Resources resources = paramContext.getResources();
    this.mListPopupMaxWidth = Math.max((resources.getDisplayMetrics()).widthPixels / 2, resources.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
  }
  
  public boolean dismissPopup() {
    if (isShowingPopup()) {
      getListPopupWindow().dismiss();
      ViewTreeObserver viewTreeObserver = getViewTreeObserver();
      if (viewTreeObserver.isAlive())
        viewTreeObserver.removeGlobalOnLayoutListener(this.mOnGlobalLayoutListener); 
    } 
    return true;
  }
  
  public ActivityChooserModel getDataModel() {
    return this.mAdapter.getDataModel();
  }
  
  ListPopupWindow getListPopupWindow() {
    if (this.mListPopupWindow == null) {
      ListPopupWindow listPopupWindow = new ListPopupWindow(getContext());
      this.mListPopupWindow = listPopupWindow;
      listPopupWindow.setAdapter((ListAdapter)this.mAdapter);
      this.mListPopupWindow.setAnchorView((View)this);
      this.mListPopupWindow.setModal(true);
      this.mListPopupWindow.setOnItemClickListener(this.mCallbacks);
      this.mListPopupWindow.setOnDismissListener(this.mCallbacks);
    } 
    return this.mListPopupWindow;
  }
  
  public boolean isShowingPopup() {
    return getListPopupWindow().isShowing();
  }
  
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    ActivityChooserModel activityChooserModel = this.mAdapter.getDataModel();
    if (activityChooserModel != null)
      activityChooserModel.registerObserver(this.mModelDataSetObserver); 
    this.mIsAttachedToWindow = true;
  }
  
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    ActivityChooserModel activityChooserModel = this.mAdapter.getDataModel();
    if (activityChooserModel != null)
      activityChooserModel.unregisterObserver(this.mModelDataSetObserver); 
    ViewTreeObserver viewTreeObserver = getViewTreeObserver();
    if (viewTreeObserver.isAlive())
      viewTreeObserver.removeGlobalOnLayoutListener(this.mOnGlobalLayoutListener); 
    if (isShowingPopup())
      dismissPopup(); 
    this.mIsAttachedToWindow = false;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.mActivityChooserContent.layout(0, 0, paramInt3 - paramInt1, paramInt4 - paramInt2);
    if (!isShowingPopup())
      dismissPopup(); 
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    View view = this.mActivityChooserContent;
    int i = paramInt2;
    if (this.mDefaultActivityButton.getVisibility() != 0)
      i = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(paramInt2), 1073741824); 
    measureChild(view, paramInt1, i);
    setMeasuredDimension(view.getMeasuredWidth(), view.getMeasuredHeight());
  }
  
  public void setActivityChooserModel(ActivityChooserModel paramActivityChooserModel) {
    this.mAdapter.setDataModel(paramActivityChooserModel);
    if (isShowingPopup()) {
      dismissPopup();
      showPopup();
    } 
  }
  
  public void setDefaultActionButtonContentDescription(int paramInt) {
    this.mDefaultActionButtonContentDescription = paramInt;
  }
  
  public void setExpandActivityOverflowButtonContentDescription(int paramInt) {
    String str = getContext().getString(paramInt);
    this.mExpandActivityOverflowButtonImage.setContentDescription(str);
  }
  
  public void setExpandActivityOverflowButtonDrawable(Drawable paramDrawable) {
    this.mExpandActivityOverflowButtonImage.setImageDrawable(paramDrawable);
  }
  
  public void setInitialActivityCount(int paramInt) {
    this.mInitialActivityCount = paramInt;
  }
  
  public void setOnDismissListener(PopupWindow.OnDismissListener paramOnDismissListener) {
    this.mOnDismissListener = paramOnDismissListener;
  }
  
  public void setProvider(ActionProvider paramActionProvider) {
    this.mProvider = paramActionProvider;
  }
  
  public boolean showPopup() {
    if (isShowingPopup() || !this.mIsAttachedToWindow)
      return false; 
    this.mIsSelectingDefaultActivity = false;
    showPopupUnchecked(this.mInitialActivityCount);
    return true;
  }
  
  void showPopupUnchecked(int paramInt) {
    if (this.mAdapter.getDataModel() != null) {
      byte b;
      getViewTreeObserver().addOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
      if (this.mDefaultActivityButton.getVisibility() == 0) {
        b = 1;
      } else {
        b = 0;
      } 
      int i = this.mAdapter.getActivityCount();
      if (paramInt != Integer.MAX_VALUE && i > paramInt + b) {
        this.mAdapter.setShowFooterView(true);
        this.mAdapter.setMaxActivityCount(paramInt - 1);
      } else {
        this.mAdapter.setShowFooterView(false);
        this.mAdapter.setMaxActivityCount(paramInt);
      } 
      ListPopupWindow listPopupWindow = getListPopupWindow();
      if (!listPopupWindow.isShowing()) {
        if (this.mIsSelectingDefaultActivity || b == 0) {
          this.mAdapter.setShowDefaultActivity(true, b);
        } else {
          this.mAdapter.setShowDefaultActivity(false, false);
        } 
        listPopupWindow.setContentWidth(Math.min(this.mAdapter.measureContentWidth(), this.mListPopupMaxWidth));
        listPopupWindow.show();
        ActionProvider actionProvider = this.mProvider;
        if (actionProvider != null)
          actionProvider.subUiVisibilityChanged(true); 
        listPopupWindow.getListView().setContentDescription(getContext().getString(R.string.abc_activitychooserview_choose_application));
        listPopupWindow.getListView().setSelector((Drawable)new ColorDrawable(0));
      } 
      return;
    } 
    throw new IllegalStateException("No data model. Did you call #setDataModel?");
  }
  
  void updateAppearance() {
    if (this.mAdapter.getCount() > 0) {
      this.mExpandActivityOverflowButton.setEnabled(true);
    } else {
      this.mExpandActivityOverflowButton.setEnabled(false);
    } 
    int i = this.mAdapter.getActivityCount();
    int j = this.mAdapter.getHistorySize();
    if (i == 1 || (i > 1 && j > 0)) {
      this.mDefaultActivityButton.setVisibility(0);
      ResolveInfo resolveInfo = this.mAdapter.getDefaultActivity();
      PackageManager packageManager = getContext().getPackageManager();
      this.mDefaultActivityButtonImage.setImageDrawable(resolveInfo.loadIcon(packageManager));
      if (this.mDefaultActionButtonContentDescription != 0) {
        CharSequence charSequence = resolveInfo.loadLabel(packageManager);
        charSequence = getContext().getString(this.mDefaultActionButtonContentDescription, new Object[] { charSequence });
        this.mDefaultActivityButton.setContentDescription(charSequence);
      } 
    } else {
      this.mDefaultActivityButton.setVisibility(8);
    } 
    if (this.mDefaultActivityButton.getVisibility() == 0) {
      this.mActivityChooserContent.setBackgroundDrawable(this.mActivityChooserContentBackground);
    } else {
      this.mActivityChooserContent.setBackgroundDrawable(null);
    } 
  }
  
  private class ActivityChooserViewAdapter extends BaseAdapter {
    private static final int ITEM_VIEW_TYPE_ACTIVITY = 0;
    
    private static final int ITEM_VIEW_TYPE_COUNT = 3;
    
    private static final int ITEM_VIEW_TYPE_FOOTER = 1;
    
    public static final int MAX_ACTIVITY_COUNT_DEFAULT = 4;
    
    public static final int MAX_ACTIVITY_COUNT_UNLIMITED = 2147483647;
    
    private ActivityChooserModel mDataModel;
    
    private boolean mHighlightDefaultActivity;
    
    private int mMaxActivityCount = 4;
    
    private boolean mShowDefaultActivity;
    
    private boolean mShowFooterView;
    
    public int getActivityCount() {
      return this.mDataModel.getActivityCount();
    }
    
    public int getCount() {
      int i = this.mDataModel.getActivityCount();
      int j = i;
      if (!this.mShowDefaultActivity) {
        j = i;
        if (this.mDataModel.getDefaultActivity() != null)
          j = i - 1; 
      } 
      i = Math.min(j, this.mMaxActivityCount);
      j = i;
      if (this.mShowFooterView)
        j = i + 1; 
      return j;
    }
    
    public ActivityChooserModel getDataModel() {
      return this.mDataModel;
    }
    
    public ResolveInfo getDefaultActivity() {
      return this.mDataModel.getDefaultActivity();
    }
    
    public int getHistorySize() {
      return this.mDataModel.getHistorySize();
    }
    
    public Object getItem(int param1Int) {
      int i = getItemViewType(param1Int);
      if (i != 0) {
        if (i == 1)
          return null; 
        throw new IllegalArgumentException();
      } 
      i = param1Int;
      if (!this.mShowDefaultActivity) {
        i = param1Int;
        if (this.mDataModel.getDefaultActivity() != null)
          i = param1Int + 1; 
      } 
      return this.mDataModel.getActivity(i);
    }
    
    public long getItemId(int param1Int) {
      return param1Int;
    }
    
    public int getItemViewType(int param1Int) {
      return (this.mShowFooterView && param1Int == getCount() - 1) ? 1 : 0;
    }
    
    public boolean getShowDefaultActivity() {
      return this.mShowDefaultActivity;
    }
    
    public View getView(int param1Int, View param1View, ViewGroup param1ViewGroup) {
      // Byte code:
      //   0: aload_0
      //   1: iload_1
      //   2: invokevirtual getItemViewType : (I)I
      //   5: istore #4
      //   7: iload #4
      //   9: ifeq -> 97
      //   12: iload #4
      //   14: iconst_1
      //   15: if_icmpne -> 89
      //   18: aload_2
      //   19: ifnull -> 33
      //   22: aload_2
      //   23: astore #5
      //   25: aload_2
      //   26: invokevirtual getId : ()I
      //   29: iconst_1
      //   30: if_icmpeq -> 86
      //   33: aload_0
      //   34: getfield this$0 : Landroid/support/v7/widget/ActivityChooserView;
      //   37: invokevirtual getContext : ()Landroid/content/Context;
      //   40: invokestatic from : (Landroid/content/Context;)Landroid/view/LayoutInflater;
      //   43: getstatic android/support/v7/appcompat/R$layout.abc_activity_chooser_view_list_item : I
      //   46: aload_3
      //   47: iconst_0
      //   48: invokevirtual inflate : (ILandroid/view/ViewGroup;Z)Landroid/view/View;
      //   51: astore #5
      //   53: aload #5
      //   55: iconst_1
      //   56: invokevirtual setId : (I)V
      //   59: aload #5
      //   61: getstatic android/support/v7/appcompat/R$id.title : I
      //   64: invokevirtual findViewById : (I)Landroid/view/View;
      //   67: checkcast android/widget/TextView
      //   70: aload_0
      //   71: getfield this$0 : Landroid/support/v7/widget/ActivityChooserView;
      //   74: invokevirtual getContext : ()Landroid/content/Context;
      //   77: getstatic android/support/v7/appcompat/R$string.abc_activity_chooser_view_see_all : I
      //   80: invokevirtual getString : (I)Ljava/lang/String;
      //   83: invokevirtual setText : (Ljava/lang/CharSequence;)V
      //   86: aload #5
      //   88: areturn
      //   89: new java/lang/IllegalArgumentException
      //   92: dup
      //   93: invokespecial <init> : ()V
      //   96: athrow
      //   97: aload_2
      //   98: ifnull -> 114
      //   101: aload_2
      //   102: astore #5
      //   104: aload_2
      //   105: invokevirtual getId : ()I
      //   108: getstatic android/support/v7/appcompat/R$id.list_item : I
      //   111: if_icmpeq -> 134
      //   114: aload_0
      //   115: getfield this$0 : Landroid/support/v7/widget/ActivityChooserView;
      //   118: invokevirtual getContext : ()Landroid/content/Context;
      //   121: invokestatic from : (Landroid/content/Context;)Landroid/view/LayoutInflater;
      //   124: getstatic android/support/v7/appcompat/R$layout.abc_activity_chooser_view_list_item : I
      //   127: aload_3
      //   128: iconst_0
      //   129: invokevirtual inflate : (ILandroid/view/ViewGroup;Z)Landroid/view/View;
      //   132: astore #5
      //   134: aload_0
      //   135: getfield this$0 : Landroid/support/v7/widget/ActivityChooserView;
      //   138: invokevirtual getContext : ()Landroid/content/Context;
      //   141: invokevirtual getPackageManager : ()Landroid/content/pm/PackageManager;
      //   144: astore_3
      //   145: aload #5
      //   147: getstatic android/support/v7/appcompat/R$id.icon : I
      //   150: invokevirtual findViewById : (I)Landroid/view/View;
      //   153: checkcast android/widget/ImageView
      //   156: astore_2
      //   157: aload_0
      //   158: iload_1
      //   159: invokevirtual getItem : (I)Ljava/lang/Object;
      //   162: checkcast android/content/pm/ResolveInfo
      //   165: astore #6
      //   167: aload_2
      //   168: aload #6
      //   170: aload_3
      //   171: invokevirtual loadIcon : (Landroid/content/pm/PackageManager;)Landroid/graphics/drawable/Drawable;
      //   174: invokevirtual setImageDrawable : (Landroid/graphics/drawable/Drawable;)V
      //   177: aload #5
      //   179: getstatic android/support/v7/appcompat/R$id.title : I
      //   182: invokevirtual findViewById : (I)Landroid/view/View;
      //   185: checkcast android/widget/TextView
      //   188: aload #6
      //   190: aload_3
      //   191: invokevirtual loadLabel : (Landroid/content/pm/PackageManager;)Ljava/lang/CharSequence;
      //   194: invokevirtual setText : (Ljava/lang/CharSequence;)V
      //   197: aload_0
      //   198: getfield mShowDefaultActivity : Z
      //   201: ifeq -> 224
      //   204: iload_1
      //   205: ifne -> 224
      //   208: aload_0
      //   209: getfield mHighlightDefaultActivity : Z
      //   212: ifeq -> 224
      //   215: aload #5
      //   217: iconst_1
      //   218: invokevirtual setActivated : (Z)V
      //   221: goto -> 230
      //   224: aload #5
      //   226: iconst_0
      //   227: invokevirtual setActivated : (Z)V
      //   230: aload #5
      //   232: areturn
    }
    
    public int getViewTypeCount() {
      return 3;
    }
    
    public int measureContentWidth() {
      int i = this.mMaxActivityCount;
      this.mMaxActivityCount = Integer.MAX_VALUE;
      byte b = 0;
      int j = View.MeasureSpec.makeMeasureSpec(0, 0);
      int k = View.MeasureSpec.makeMeasureSpec(0, 0);
      int m = getCount();
      View view = null;
      int n = 0;
      while (b < m) {
        view = getView(b, view, (ViewGroup)null);
        view.measure(j, k);
        n = Math.max(n, view.getMeasuredWidth());
        b++;
      } 
      this.mMaxActivityCount = i;
      return n;
    }
    
    public void setDataModel(ActivityChooserModel param1ActivityChooserModel) {
      ActivityChooserModel activityChooserModel = ActivityChooserView.this.mAdapter.getDataModel();
      if (activityChooserModel != null && ActivityChooserView.this.isShown())
        activityChooserModel.unregisterObserver(ActivityChooserView.this.mModelDataSetObserver); 
      this.mDataModel = param1ActivityChooserModel;
      if (param1ActivityChooserModel != null && ActivityChooserView.this.isShown())
        param1ActivityChooserModel.registerObserver(ActivityChooserView.this.mModelDataSetObserver); 
      notifyDataSetChanged();
    }
    
    public void setMaxActivityCount(int param1Int) {
      if (this.mMaxActivityCount != param1Int) {
        this.mMaxActivityCount = param1Int;
        notifyDataSetChanged();
      } 
    }
    
    public void setShowDefaultActivity(boolean param1Boolean1, boolean param1Boolean2) {
      if (this.mShowDefaultActivity != param1Boolean1 || this.mHighlightDefaultActivity != param1Boolean2) {
        this.mShowDefaultActivity = param1Boolean1;
        this.mHighlightDefaultActivity = param1Boolean2;
        notifyDataSetChanged();
      } 
    }
    
    public void setShowFooterView(boolean param1Boolean) {
      if (this.mShowFooterView != param1Boolean) {
        this.mShowFooterView = param1Boolean;
        notifyDataSetChanged();
      } 
    }
  }
  
  private class Callbacks implements AdapterView.OnItemClickListener, View.OnClickListener, View.OnLongClickListener, PopupWindow.OnDismissListener {
    private void notifyOnDismissListener() {
      if (ActivityChooserView.this.mOnDismissListener != null)
        ActivityChooserView.this.mOnDismissListener.onDismiss(); 
    }
    
    public void onClick(View param1View) {
      Intent intent;
      if (param1View == ActivityChooserView.this.mDefaultActivityButton) {
        ActivityChooserView.this.dismissPopup();
        ResolveInfo resolveInfo = ActivityChooserView.this.mAdapter.getDefaultActivity();
        int i = ActivityChooserView.this.mAdapter.getDataModel().getActivityIndex(resolveInfo);
        intent = ActivityChooserView.this.mAdapter.getDataModel().chooseActivity(i);
        if (intent != null) {
          intent.addFlags(524288);
          ActivityChooserView.this.getContext().startActivity(intent);
        } 
      } else {
        if (intent == ActivityChooserView.this.mExpandActivityOverflowButton) {
          ActivityChooserView.this.mIsSelectingDefaultActivity = false;
          ActivityChooserView activityChooserView = ActivityChooserView.this;
          activityChooserView.showPopupUnchecked(activityChooserView.mInitialActivityCount);
          return;
        } 
        throw new IllegalArgumentException();
      } 
    }
    
    public void onDismiss() {
      notifyOnDismissListener();
      if (ActivityChooserView.this.mProvider != null)
        ActivityChooserView.this.mProvider.subUiVisibilityChanged(false); 
    }
    
    public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
      int i = ((ActivityChooserView.ActivityChooserViewAdapter)param1AdapterView.getAdapter()).getItemViewType(param1Int);
      if (i != 0) {
        if (i == 1) {
          ActivityChooserView.this.showPopupUnchecked(2147483647);
        } else {
          throw new IllegalArgumentException();
        } 
      } else {
        ActivityChooserView.this.dismissPopup();
        if (ActivityChooserView.this.mIsSelectingDefaultActivity) {
          if (param1Int > 0)
            ActivityChooserView.this.mAdapter.getDataModel().setDefaultActivity(param1Int); 
        } else {
          if (!ActivityChooserView.this.mAdapter.getShowDefaultActivity())
            param1Int++; 
          Intent intent = ActivityChooserView.this.mAdapter.getDataModel().chooseActivity(param1Int);
          if (intent != null) {
            intent.addFlags(524288);
            ActivityChooserView.this.getContext().startActivity(intent);
          } 
        } 
      } 
    }
    
    public boolean onLongClick(View param1View) {
      if (param1View == ActivityChooserView.this.mDefaultActivityButton) {
        if (ActivityChooserView.this.mAdapter.getCount() > 0) {
          ActivityChooserView.this.mIsSelectingDefaultActivity = true;
          ActivityChooserView activityChooserView = ActivityChooserView.this;
          activityChooserView.showPopupUnchecked(activityChooserView.mInitialActivityCount);
        } 
        return true;
      } 
      throw new IllegalArgumentException();
    }
  }
  
  public static class InnerLayout extends LinearLayout {
    private static final int[] TINT_ATTRS = new int[] { 16842964 };
    
    public InnerLayout(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
      TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(param1Context, param1AttributeSet, TINT_ATTRS);
      setBackgroundDrawable(tintTypedArray.getDrawable(0));
      tintTypedArray.recycle();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\ActivityChooserView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */