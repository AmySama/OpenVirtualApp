package android.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.design.R;
import android.support.design.internal.SnackbarContentLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;

public final class Snackbar extends BaseTransientBottomBar<Snackbar> {
  public static final int LENGTH_INDEFINITE = -2;
  
  public static final int LENGTH_LONG = 0;
  
  public static final int LENGTH_SHORT = -1;
  
  private BaseTransientBottomBar.BaseCallback<Snackbar> mCallback;
  
  private Snackbar(ViewGroup paramViewGroup, View paramView, BaseTransientBottomBar.ContentViewCallback paramContentViewCallback) {
    super(paramViewGroup, paramView, paramContentViewCallback);
  }
  
  private static ViewGroup findSuitableParent(View paramView) {
    ViewGroup viewGroup = null;
    View view = paramView;
    while (true) {
      ViewParent viewParent1;
      if (view instanceof CoordinatorLayout)
        return (ViewGroup)view; 
      ViewGroup viewGroup1 = viewGroup;
      if (view instanceof android.widget.FrameLayout) {
        if (view.getId() == 16908290)
          return (ViewGroup)view; 
        viewGroup1 = (ViewGroup)view;
      } 
      paramView = view;
      if (view != null) {
        viewParent1 = view.getParent();
        if (viewParent1 instanceof View) {
          View view1 = (View)viewParent1;
        } else {
          viewParent1 = null;
        } 
      } 
      viewGroup = viewGroup1;
      ViewParent viewParent2 = viewParent1;
      if (viewParent1 == null)
        return viewGroup1; 
    } 
  }
  
  public static Snackbar make(View paramView, int paramInt1, int paramInt2) {
    return make(paramView, paramView.getResources().getText(paramInt1), paramInt2);
  }
  
  public static Snackbar make(View paramView, CharSequence paramCharSequence, int paramInt) {
    ViewGroup viewGroup = findSuitableParent(paramView);
    if (viewGroup != null) {
      SnackbarContentLayout snackbarContentLayout = (SnackbarContentLayout)LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.design_layout_snackbar_include, viewGroup, false);
      Snackbar snackbar = new Snackbar(viewGroup, (View)snackbarContentLayout, (BaseTransientBottomBar.ContentViewCallback)snackbarContentLayout);
      snackbar.setText(paramCharSequence);
      snackbar.setDuration(paramInt);
      return snackbar;
    } 
    throw new IllegalArgumentException("No suitable parent found from the given view. Please provide a valid view.");
  }
  
  public Snackbar setAction(int paramInt, View.OnClickListener paramOnClickListener) {
    return setAction(getContext().getText(paramInt), paramOnClickListener);
  }
  
  public Snackbar setAction(CharSequence paramCharSequence, final View.OnClickListener listener) {
    Button button = ((SnackbarContentLayout)this.mView.getChildAt(0)).getActionView();
    if (TextUtils.isEmpty(paramCharSequence) || listener == null) {
      button.setVisibility(8);
      button.setOnClickListener(null);
      return this;
    } 
    button.setVisibility(0);
    button.setText(paramCharSequence);
    button.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            listener.onClick(param1View);
            Snackbar.this.dispatchDismiss(1);
          }
        });
    return this;
  }
  
  public Snackbar setActionTextColor(int paramInt) {
    ((SnackbarContentLayout)this.mView.getChildAt(0)).getActionView().setTextColor(paramInt);
    return this;
  }
  
  public Snackbar setActionTextColor(ColorStateList paramColorStateList) {
    ((SnackbarContentLayout)this.mView.getChildAt(0)).getActionView().setTextColor(paramColorStateList);
    return this;
  }
  
  @Deprecated
  public Snackbar setCallback(Callback paramCallback) {
    BaseTransientBottomBar.BaseCallback<Snackbar> baseCallback = this.mCallback;
    if (baseCallback != null)
      removeCallback(baseCallback); 
    if (paramCallback != null)
      addCallback(paramCallback); 
    this.mCallback = paramCallback;
    return this;
  }
  
  public Snackbar setText(int paramInt) {
    return setText(getContext().getText(paramInt));
  }
  
  public Snackbar setText(CharSequence paramCharSequence) {
    ((SnackbarContentLayout)this.mView.getChildAt(0)).getMessageView().setText(paramCharSequence);
    return this;
  }
  
  public static class Callback extends BaseTransientBottomBar.BaseCallback<Snackbar> {
    public static final int DISMISS_EVENT_ACTION = 1;
    
    public static final int DISMISS_EVENT_CONSECUTIVE = 4;
    
    public static final int DISMISS_EVENT_MANUAL = 3;
    
    public static final int DISMISS_EVENT_SWIPE = 0;
    
    public static final int DISMISS_EVENT_TIMEOUT = 2;
    
    public void onDismissed(Snackbar param1Snackbar, int param1Int) {}
    
    public void onShown(Snackbar param1Snackbar) {}
  }
  
  public static final class SnackbarLayout extends BaseTransientBottomBar.SnackbarBaseLayout {
    public SnackbarLayout(Context param1Context) {
      super(param1Context);
    }
    
    public SnackbarLayout(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
    }
    
    protected void onMeasure(int param1Int1, int param1Int2) {
      super.onMeasure(param1Int1, param1Int2);
      param1Int2 = getChildCount();
      int i = getMeasuredWidth();
      int j = getPaddingLeft();
      int k = getPaddingRight();
      for (param1Int1 = 0; param1Int1 < param1Int2; param1Int1++) {
        View view = getChildAt(param1Int1);
        if ((view.getLayoutParams()).width == -1)
          view.measure(View.MeasureSpec.makeMeasureSpec(i - j - k, 1073741824), View.MeasureSpec.makeMeasureSpec(view.getMeasuredHeight(), 1073741824)); 
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\widget\Snackbar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */