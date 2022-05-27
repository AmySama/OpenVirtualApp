package android.support.v7.widget;

import android.content.Context;
import android.os.Build;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;

class AppCompatPopupWindow extends PopupWindow {
  private static final boolean COMPAT_OVERLAP_ANCHOR;
  
  private boolean mOverlapAnchor;
  
  static {
    boolean bool;
    if (Build.VERSION.SDK_INT < 21) {
      bool = true;
    } else {
      bool = false;
    } 
    COMPAT_OVERLAP_ANCHOR = bool;
  }
  
  public AppCompatPopupWindow(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    init(paramContext, paramAttributeSet, paramInt, 0);
  }
  
  public AppCompatPopupWindow(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    init(paramContext, paramAttributeSet, paramInt1, paramInt2);
  }
  
  private void init(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
    TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.PopupWindow, paramInt1, paramInt2);
    if (tintTypedArray.hasValue(R.styleable.PopupWindow_overlapAnchor))
      setSupportOverlapAnchor(tintTypedArray.getBoolean(R.styleable.PopupWindow_overlapAnchor, false)); 
    setBackgroundDrawable(tintTypedArray.getDrawable(R.styleable.PopupWindow_android_popupBackground));
    tintTypedArray.recycle();
  }
  
  private void setSupportOverlapAnchor(boolean paramBoolean) {
    if (COMPAT_OVERLAP_ANCHOR) {
      this.mOverlapAnchor = paramBoolean;
    } else {
      PopupWindowCompat.setOverlapAnchor(this, paramBoolean);
    } 
  }
  
  public void showAsDropDown(View paramView, int paramInt1, int paramInt2) {
    int i = paramInt2;
    if (COMPAT_OVERLAP_ANCHOR) {
      i = paramInt2;
      if (this.mOverlapAnchor)
        i = paramInt2 - paramView.getHeight(); 
    } 
    super.showAsDropDown(paramView, paramInt1, i);
  }
  
  public void showAsDropDown(View paramView, int paramInt1, int paramInt2, int paramInt3) {
    int i = paramInt2;
    if (COMPAT_OVERLAP_ANCHOR) {
      i = paramInt2;
      if (this.mOverlapAnchor)
        i = paramInt2 - paramView.getHeight(); 
    } 
    super.showAsDropDown(paramView, paramInt1, i, paramInt3);
  }
  
  public void update(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    int i = paramInt2;
    if (COMPAT_OVERLAP_ANCHOR) {
      i = paramInt2;
      if (this.mOverlapAnchor)
        i = paramInt2 - paramView.getHeight(); 
    } 
    super.update(paramView, paramInt1, i, paramInt3, paramInt4);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\AppCompatPopupWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */