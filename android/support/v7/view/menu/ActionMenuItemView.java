package android.support.v7.view.menu;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.v7.appcompat.R;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.ForwardingListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ActionMenuItemView extends AppCompatTextView implements MenuView.ItemView, View.OnClickListener, ActionMenuView.ActionMenuChildView {
  private static final int MAX_ICON_SIZE = 32;
  
  private static final String TAG = "ActionMenuItemView";
  
  private boolean mAllowTextWithIcon;
  
  private boolean mExpandedFormat;
  
  private ForwardingListener mForwardingListener;
  
  private Drawable mIcon;
  
  MenuItemImpl mItemData;
  
  MenuBuilder.ItemInvoker mItemInvoker;
  
  private int mMaxIconSize;
  
  private int mMinWidth;
  
  PopupCallback mPopupCallback;
  
  private int mSavedPaddingLeft;
  
  private CharSequence mTitle;
  
  public ActionMenuItemView(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public ActionMenuItemView(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public ActionMenuItemView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    Resources resources = paramContext.getResources();
    this.mAllowTextWithIcon = shouldAllowTextWithIcon();
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ActionMenuItemView, paramInt, 0);
    this.mMinWidth = typedArray.getDimensionPixelSize(R.styleable.ActionMenuItemView_android_minWidth, 0);
    typedArray.recycle();
    this.mMaxIconSize = (int)((resources.getDisplayMetrics()).density * 32.0F + 0.5F);
    setOnClickListener(this);
    this.mSavedPaddingLeft = -1;
    setSaveEnabled(false);
  }
  
  private boolean shouldAllowTextWithIcon() {
    Configuration configuration = getContext().getResources().getConfiguration();
    int i = configuration.screenWidthDp;
    int j = configuration.screenHeightDp;
    return (i >= 480 || (i >= 640 && j >= 480) || configuration.orientation == 2);
  }
  
  private void updateTextButtonVisibility() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mTitle : Ljava/lang/CharSequence;
    //   4: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   7: istore_1
    //   8: iconst_1
    //   9: istore_2
    //   10: iload_2
    //   11: istore_3
    //   12: aload_0
    //   13: getfield mIcon : Landroid/graphics/drawable/Drawable;
    //   16: ifnull -> 52
    //   19: aload_0
    //   20: getfield mItemData : Landroid/support/v7/view/menu/MenuItemImpl;
    //   23: invokevirtual showsTextAsAction : ()Z
    //   26: ifeq -> 50
    //   29: iload_2
    //   30: istore_3
    //   31: aload_0
    //   32: getfield mAllowTextWithIcon : Z
    //   35: ifne -> 52
    //   38: aload_0
    //   39: getfield mExpandedFormat : Z
    //   42: ifeq -> 50
    //   45: iload_2
    //   46: istore_3
    //   47: goto -> 52
    //   50: iconst_0
    //   51: istore_3
    //   52: iload_1
    //   53: iconst_1
    //   54: ixor
    //   55: iload_3
    //   56: iand
    //   57: istore_3
    //   58: aconst_null
    //   59: astore #4
    //   61: iload_3
    //   62: ifeq -> 74
    //   65: aload_0
    //   66: getfield mTitle : Ljava/lang/CharSequence;
    //   69: astore #5
    //   71: goto -> 77
    //   74: aconst_null
    //   75: astore #5
    //   77: aload_0
    //   78: aload #5
    //   80: invokevirtual setText : (Ljava/lang/CharSequence;)V
    //   83: aload_0
    //   84: getfield mItemData : Landroid/support/v7/view/menu/MenuItemImpl;
    //   87: invokevirtual getContentDescription : ()Ljava/lang/CharSequence;
    //   90: astore #5
    //   92: aload #5
    //   94: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   97: ifeq -> 128
    //   100: iload_3
    //   101: ifeq -> 110
    //   104: aconst_null
    //   105: astore #5
    //   107: goto -> 119
    //   110: aload_0
    //   111: getfield mItemData : Landroid/support/v7/view/menu/MenuItemImpl;
    //   114: invokevirtual getTitle : ()Ljava/lang/CharSequence;
    //   117: astore #5
    //   119: aload_0
    //   120: aload #5
    //   122: invokevirtual setContentDescription : (Ljava/lang/CharSequence;)V
    //   125: goto -> 134
    //   128: aload_0
    //   129: aload #5
    //   131: invokevirtual setContentDescription : (Ljava/lang/CharSequence;)V
    //   134: aload_0
    //   135: getfield mItemData : Landroid/support/v7/view/menu/MenuItemImpl;
    //   138: invokevirtual getTooltipText : ()Ljava/lang/CharSequence;
    //   141: astore #5
    //   143: aload #5
    //   145: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   148: ifeq -> 180
    //   151: iload_3
    //   152: ifeq -> 162
    //   155: aload #4
    //   157: astore #5
    //   159: goto -> 171
    //   162: aload_0
    //   163: getfield mItemData : Landroid/support/v7/view/menu/MenuItemImpl;
    //   166: invokevirtual getTitle : ()Ljava/lang/CharSequence;
    //   169: astore #5
    //   171: aload_0
    //   172: aload #5
    //   174: invokestatic setTooltipText : (Landroid/view/View;Ljava/lang/CharSequence;)V
    //   177: goto -> 186
    //   180: aload_0
    //   181: aload #5
    //   183: invokestatic setTooltipText : (Landroid/view/View;Ljava/lang/CharSequence;)V
    //   186: return
  }
  
  public MenuItemImpl getItemData() {
    return this.mItemData;
  }
  
  public boolean hasText() {
    return TextUtils.isEmpty(getText()) ^ true;
  }
  
  public void initialize(MenuItemImpl paramMenuItemImpl, int paramInt) {
    this.mItemData = paramMenuItemImpl;
    setIcon(paramMenuItemImpl.getIcon());
    setTitle(paramMenuItemImpl.getTitleForItemView(this));
    setId(paramMenuItemImpl.getItemId());
    if (paramMenuItemImpl.isVisible()) {
      paramInt = 0;
    } else {
      paramInt = 8;
    } 
    setVisibility(paramInt);
    setEnabled(paramMenuItemImpl.isEnabled());
    if (paramMenuItemImpl.hasSubMenu() && this.mForwardingListener == null)
      this.mForwardingListener = new ActionMenuItemForwardingListener(); 
  }
  
  public boolean needsDividerAfter() {
    return hasText();
  }
  
  public boolean needsDividerBefore() {
    boolean bool;
    if (hasText() && this.mItemData.getIcon() == null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void onClick(View paramView) {
    MenuBuilder.ItemInvoker itemInvoker = this.mItemInvoker;
    if (itemInvoker != null)
      itemInvoker.invokeItem(this.mItemData); 
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration) {
    super.onConfigurationChanged(paramConfiguration);
    this.mAllowTextWithIcon = shouldAllowTextWithIcon();
    updateTextButtonVisibility();
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    boolean bool = hasText();
    if (bool) {
      int k = this.mSavedPaddingLeft;
      if (k >= 0)
        super.setPadding(k, getPaddingTop(), getPaddingRight(), getPaddingBottom()); 
    } 
    super.onMeasure(paramInt1, paramInt2);
    int i = View.MeasureSpec.getMode(paramInt1);
    paramInt1 = View.MeasureSpec.getSize(paramInt1);
    int j = getMeasuredWidth();
    if (i == Integer.MIN_VALUE) {
      paramInt1 = Math.min(paramInt1, this.mMinWidth);
    } else {
      paramInt1 = this.mMinWidth;
    } 
    if (i != 1073741824 && this.mMinWidth > 0 && j < paramInt1)
      super.onMeasure(View.MeasureSpec.makeMeasureSpec(paramInt1, 1073741824), paramInt2); 
    if (!bool && this.mIcon != null)
      super.setPadding((getMeasuredWidth() - this.mIcon.getBounds().width()) / 2, getPaddingTop(), getPaddingRight(), getPaddingBottom()); 
  }
  
  public void onRestoreInstanceState(Parcelable paramParcelable) {
    super.onRestoreInstanceState(null);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    if (this.mItemData.hasSubMenu()) {
      ForwardingListener forwardingListener = this.mForwardingListener;
      if (forwardingListener != null && forwardingListener.onTouch((View)this, paramMotionEvent))
        return true; 
    } 
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public boolean prefersCondensedTitle() {
    return true;
  }
  
  public void setCheckable(boolean paramBoolean) {}
  
  public void setChecked(boolean paramBoolean) {}
  
  public void setExpandedFormat(boolean paramBoolean) {
    if (this.mExpandedFormat != paramBoolean) {
      this.mExpandedFormat = paramBoolean;
      MenuItemImpl menuItemImpl = this.mItemData;
      if (menuItemImpl != null)
        menuItemImpl.actionFormatChanged(); 
    } 
  }
  
  public void setIcon(Drawable paramDrawable) {
    this.mIcon = paramDrawable;
    if (paramDrawable != null) {
      int i = paramDrawable.getIntrinsicWidth();
      int j = paramDrawable.getIntrinsicHeight();
      int k = this.mMaxIconSize;
      int m = i;
      int n = j;
      if (i > k) {
        float f = k / i;
        n = (int)(j * f);
        m = k;
      } 
      i = this.mMaxIconSize;
      j = m;
      k = n;
      if (n > i) {
        float f = i / n;
        j = (int)(m * f);
        k = i;
      } 
      paramDrawable.setBounds(0, 0, j, k);
    } 
    setCompoundDrawables(paramDrawable, null, null, null);
    updateTextButtonVisibility();
  }
  
  public void setItemInvoker(MenuBuilder.ItemInvoker paramItemInvoker) {
    this.mItemInvoker = paramItemInvoker;
  }
  
  public void setPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.mSavedPaddingLeft = paramInt1;
    super.setPadding(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void setPopupCallback(PopupCallback paramPopupCallback) {
    this.mPopupCallback = paramPopupCallback;
  }
  
  public void setShortcut(boolean paramBoolean, char paramChar) {}
  
  public void setTitle(CharSequence paramCharSequence) {
    this.mTitle = paramCharSequence;
    updateTextButtonVisibility();
  }
  
  public boolean showsIcon() {
    return true;
  }
  
  private class ActionMenuItemForwardingListener extends ForwardingListener {
    public ActionMenuItemForwardingListener() {
      super((View)ActionMenuItemView.this);
    }
    
    public ShowableListMenu getPopup() {
      return (ActionMenuItemView.this.mPopupCallback != null) ? ActionMenuItemView.this.mPopupCallback.getPopup() : null;
    }
    
    protected boolean onForwardingStarted() {
      MenuBuilder.ItemInvoker itemInvoker = ActionMenuItemView.this.mItemInvoker;
      boolean bool1 = false;
      boolean bool2 = bool1;
      if (itemInvoker != null) {
        bool2 = bool1;
        if (ActionMenuItemView.this.mItemInvoker.invokeItem(ActionMenuItemView.this.mItemData)) {
          ShowableListMenu showableListMenu = getPopup();
          bool2 = bool1;
          if (showableListMenu != null) {
            bool2 = bool1;
            if (showableListMenu.isShowing())
              bool2 = true; 
          } 
        } 
      } 
      return bool2;
    }
  }
  
  public static abstract class PopupCallback {
    public abstract ShowableListMenu getPopup();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\view\menu\ActionMenuItemView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */