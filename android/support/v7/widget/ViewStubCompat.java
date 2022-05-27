package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import java.lang.ref.WeakReference;

public final class ViewStubCompat extends View {
  private OnInflateListener mInflateListener;
  
  private int mInflatedId;
  
  private WeakReference<View> mInflatedViewRef;
  
  private LayoutInflater mInflater;
  
  private int mLayoutResource = 0;
  
  public ViewStubCompat(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public ViewStubCompat(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.ViewStubCompat, paramInt, 0);
    this.mInflatedId = typedArray.getResourceId(R.styleable.ViewStubCompat_android_inflatedId, -1);
    this.mLayoutResource = typedArray.getResourceId(R.styleable.ViewStubCompat_android_layout, 0);
    setId(typedArray.getResourceId(R.styleable.ViewStubCompat_android_id, -1));
    typedArray.recycle();
    setVisibility(8);
    setWillNotDraw(true);
  }
  
  protected void dispatchDraw(Canvas paramCanvas) {}
  
  public void draw(Canvas paramCanvas) {}
  
  public int getInflatedId() {
    return this.mInflatedId;
  }
  
  public LayoutInflater getLayoutInflater() {
    return this.mInflater;
  }
  
  public int getLayoutResource() {
    return this.mLayoutResource;
  }
  
  public View inflate() {
    ViewParent viewParent = getParent();
    if (viewParent != null && viewParent instanceof ViewGroup) {
      if (this.mLayoutResource != 0) {
        ViewGroup viewGroup = (ViewGroup)viewParent;
        LayoutInflater layoutInflater = this.mInflater;
        if (layoutInflater == null)
          layoutInflater = LayoutInflater.from(getContext()); 
        View view = layoutInflater.inflate(this.mLayoutResource, viewGroup, false);
        int i = this.mInflatedId;
        if (i != -1)
          view.setId(i); 
        i = viewGroup.indexOfChild(this);
        viewGroup.removeViewInLayout(this);
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams != null) {
          viewGroup.addView(view, i, layoutParams);
        } else {
          viewGroup.addView(view, i);
        } 
        this.mInflatedViewRef = new WeakReference<View>(view);
        OnInflateListener onInflateListener = this.mInflateListener;
        if (onInflateListener != null)
          onInflateListener.onInflate(this, view); 
        return view;
      } 
      throw new IllegalArgumentException("ViewStub must have a valid layoutResource");
    } 
    throw new IllegalStateException("ViewStub must have a non-null ViewGroup viewParent");
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    setMeasuredDimension(0, 0);
  }
  
  public void setInflatedId(int paramInt) {
    this.mInflatedId = paramInt;
  }
  
  public void setLayoutInflater(LayoutInflater paramLayoutInflater) {
    this.mInflater = paramLayoutInflater;
  }
  
  public void setLayoutResource(int paramInt) {
    this.mLayoutResource = paramInt;
  }
  
  public void setOnInflateListener(OnInflateListener paramOnInflateListener) {
    this.mInflateListener = paramOnInflateListener;
  }
  
  public void setVisibility(int paramInt) {
    WeakReference<View> weakReference = this.mInflatedViewRef;
    if (weakReference != null) {
      View view = weakReference.get();
      if (view != null) {
        view.setVisibility(paramInt);
      } else {
        throw new IllegalStateException("setVisibility called on un-referenced view");
      } 
    } else {
      super.setVisibility(paramInt);
      if (paramInt == 0 || paramInt == 4)
        inflate(); 
    } 
  }
  
  public static interface OnInflateListener {
    void onInflate(ViewStubCompat param1ViewStubCompat, View param1View);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\ViewStubCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */