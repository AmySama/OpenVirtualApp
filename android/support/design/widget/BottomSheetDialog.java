package android.support.design.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.design.R;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.app.AppCompatDialog;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

public class BottomSheetDialog extends AppCompatDialog {
  private BottomSheetBehavior<FrameLayout> mBehavior;
  
  private BottomSheetBehavior.BottomSheetCallback mBottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
      public void onSlide(View param1View, float param1Float) {}
      
      public void onStateChanged(View param1View, int param1Int) {
        if (param1Int == 5)
          BottomSheetDialog.this.cancel(); 
      }
    };
  
  boolean mCancelable = true;
  
  private boolean mCanceledOnTouchOutside = true;
  
  private boolean mCanceledOnTouchOutsideSet;
  
  public BottomSheetDialog(Context paramContext) {
    this(paramContext, 0);
  }
  
  public BottomSheetDialog(Context paramContext, int paramInt) {
    super(paramContext, getThemeResId(paramContext, paramInt));
    supportRequestWindowFeature(1);
  }
  
  protected BottomSheetDialog(Context paramContext, boolean paramBoolean, DialogInterface.OnCancelListener paramOnCancelListener) {
    super(paramContext, paramBoolean, paramOnCancelListener);
    supportRequestWindowFeature(1);
    this.mCancelable = paramBoolean;
  }
  
  private static int getThemeResId(Context paramContext, int paramInt) {
    int i = paramInt;
    if (paramInt == 0) {
      TypedValue typedValue = new TypedValue();
      if (paramContext.getTheme().resolveAttribute(R.attr.bottomSheetDialogTheme, typedValue, true)) {
        i = typedValue.resourceId;
      } else {
        i = R.style.Theme_Design_Light_BottomSheetDialog;
      } 
    } 
    return i;
  }
  
  private View wrapInBottomSheet(int paramInt, View paramView, ViewGroup.LayoutParams paramLayoutParams) {
    FrameLayout frameLayout2 = (FrameLayout)View.inflate(getContext(), R.layout.design_bottom_sheet_dialog, null);
    CoordinatorLayout coordinatorLayout = (CoordinatorLayout)frameLayout2.findViewById(R.id.coordinator);
    View view = paramView;
    if (paramInt != 0) {
      view = paramView;
      if (paramView == null)
        view = getLayoutInflater().inflate(paramInt, coordinatorLayout, false); 
    } 
    FrameLayout frameLayout1 = (FrameLayout)coordinatorLayout.findViewById(R.id.design_bottom_sheet);
    BottomSheetBehavior<FrameLayout> bottomSheetBehavior = BottomSheetBehavior.from(frameLayout1);
    this.mBehavior = bottomSheetBehavior;
    bottomSheetBehavior.setBottomSheetCallback(this.mBottomSheetCallback);
    this.mBehavior.setHideable(this.mCancelable);
    if (paramLayoutParams == null) {
      frameLayout1.addView(view);
    } else {
      frameLayout1.addView(view, paramLayoutParams);
    } 
    coordinatorLayout.findViewById(R.id.touch_outside).setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            if (BottomSheetDialog.this.mCancelable && BottomSheetDialog.this.isShowing() && BottomSheetDialog.this.shouldWindowCloseOnTouchOutside())
              BottomSheetDialog.this.cancel(); 
          }
        });
    ViewCompat.setAccessibilityDelegate((View)frameLayout1, new AccessibilityDelegateCompat() {
          public void onInitializeAccessibilityNodeInfo(View param1View, AccessibilityNodeInfoCompat param1AccessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(param1View, param1AccessibilityNodeInfoCompat);
            if (BottomSheetDialog.this.mCancelable) {
              param1AccessibilityNodeInfoCompat.addAction(1048576);
              param1AccessibilityNodeInfoCompat.setDismissable(true);
            } else {
              param1AccessibilityNodeInfoCompat.setDismissable(false);
            } 
          }
          
          public boolean performAccessibilityAction(View param1View, int param1Int, Bundle param1Bundle) {
            if (param1Int == 1048576 && BottomSheetDialog.this.mCancelable) {
              BottomSheetDialog.this.cancel();
              return true;
            } 
            return super.performAccessibilityAction(param1View, param1Int, param1Bundle);
          }
        });
    frameLayout1.setOnTouchListener(new View.OnTouchListener() {
          public boolean onTouch(View param1View, MotionEvent param1MotionEvent) {
            return true;
          }
        });
    return (View)frameLayout2;
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    Window window = getWindow();
    if (window != null) {
      if (Build.VERSION.SDK_INT >= 21) {
        window.clearFlags(67108864);
        window.addFlags(-2147483648);
      } 
      window.setLayout(-1, -1);
    } 
  }
  
  protected void onStart() {
    super.onStart();
    BottomSheetBehavior<FrameLayout> bottomSheetBehavior = this.mBehavior;
    if (bottomSheetBehavior != null)
      bottomSheetBehavior.setState(4); 
  }
  
  public void setCancelable(boolean paramBoolean) {
    super.setCancelable(paramBoolean);
    if (this.mCancelable != paramBoolean) {
      this.mCancelable = paramBoolean;
      BottomSheetBehavior<FrameLayout> bottomSheetBehavior = this.mBehavior;
      if (bottomSheetBehavior != null)
        bottomSheetBehavior.setHideable(paramBoolean); 
    } 
  }
  
  public void setCanceledOnTouchOutside(boolean paramBoolean) {
    super.setCanceledOnTouchOutside(paramBoolean);
    if (paramBoolean && !this.mCancelable)
      this.mCancelable = true; 
    this.mCanceledOnTouchOutside = paramBoolean;
    this.mCanceledOnTouchOutsideSet = true;
  }
  
  public void setContentView(int paramInt) {
    super.setContentView(wrapInBottomSheet(paramInt, (View)null, (ViewGroup.LayoutParams)null));
  }
  
  public void setContentView(View paramView) {
    super.setContentView(wrapInBottomSheet(0, paramView, (ViewGroup.LayoutParams)null));
  }
  
  public void setContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams) {
    super.setContentView(wrapInBottomSheet(0, paramView, paramLayoutParams));
  }
  
  boolean shouldWindowCloseOnTouchOutside() {
    if (!this.mCanceledOnTouchOutsideSet) {
      if (Build.VERSION.SDK_INT < 11) {
        this.mCanceledOnTouchOutside = true;
      } else {
        TypedArray typedArray = getContext().obtainStyledAttributes(new int[] { 16843611 });
        this.mCanceledOnTouchOutside = typedArray.getBoolean(0, true);
        typedArray.recycle();
      } 
      this.mCanceledOnTouchOutsideSet = true;
    } 
    return this.mCanceledOnTouchOutside;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\widget\BottomSheetDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */