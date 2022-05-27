package android.support.transition;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

class ViewOverlayApi14 implements ViewOverlayImpl {
  protected OverlayViewGroup mOverlayViewGroup;
  
  ViewOverlayApi14(Context paramContext, ViewGroup paramViewGroup, View paramView) {
    this.mOverlayViewGroup = new OverlayViewGroup(paramContext, paramViewGroup, paramView, this);
  }
  
  static ViewOverlayApi14 createFrom(View paramView) {
    ViewGroup viewGroup = getContentView(paramView);
    if (viewGroup != null) {
      int i = viewGroup.getChildCount();
      for (byte b = 0; b < i; b++) {
        View view = viewGroup.getChildAt(b);
        if (view instanceof OverlayViewGroup)
          return ((OverlayViewGroup)view).mViewOverlay; 
      } 
      return new ViewGroupOverlayApi14(viewGroup.getContext(), viewGroup, paramView);
    } 
    return null;
  }
  
  static ViewGroup getContentView(View paramView) {
    while (paramView != null) {
      if (paramView.getId() == 16908290 && paramView instanceof ViewGroup)
        return (ViewGroup)paramView; 
      if (paramView.getParent() instanceof ViewGroup)
        ViewGroup viewGroup = (ViewGroup)paramView.getParent(); 
    } 
    return null;
  }
  
  public void add(Drawable paramDrawable) {
    this.mOverlayViewGroup.add(paramDrawable);
  }
  
  public void clear() {
    this.mOverlayViewGroup.clear();
  }
  
  ViewGroup getOverlayView() {
    return this.mOverlayViewGroup;
  }
  
  boolean isEmpty() {
    return this.mOverlayViewGroup.isEmpty();
  }
  
  public void remove(Drawable paramDrawable) {
    this.mOverlayViewGroup.remove(paramDrawable);
  }
  
  static class OverlayViewGroup extends ViewGroup {
    static Method sInvalidateChildInParentFastMethod;
    
    ArrayList<Drawable> mDrawables = null;
    
    ViewGroup mHostView;
    
    View mRequestingView;
    
    ViewOverlayApi14 mViewOverlay;
    
    static {
      try {
        sInvalidateChildInParentFastMethod = ViewGroup.class.getDeclaredMethod("invalidateChildInParentFast", new Class[] { int.class, int.class, Rect.class });
      } catch (NoSuchMethodException noSuchMethodException) {}
    }
    
    OverlayViewGroup(Context param1Context, ViewGroup param1ViewGroup, View param1View, ViewOverlayApi14 param1ViewOverlayApi14) {
      super(param1Context);
      this.mHostView = param1ViewGroup;
      this.mRequestingView = param1View;
      setRight(param1ViewGroup.getWidth());
      setBottom(param1ViewGroup.getHeight());
      param1ViewGroup.addView((View)this);
      this.mViewOverlay = param1ViewOverlayApi14;
    }
    
    private void getOffset(int[] param1ArrayOfint) {
      int[] arrayOfInt1 = new int[2];
      int[] arrayOfInt2 = new int[2];
      this.mHostView.getLocationOnScreen(arrayOfInt1);
      this.mRequestingView.getLocationOnScreen(arrayOfInt2);
      param1ArrayOfint[0] = arrayOfInt2[0] - arrayOfInt1[0];
      param1ArrayOfint[1] = arrayOfInt2[1] - arrayOfInt1[1];
    }
    
    public void add(Drawable param1Drawable) {
      if (this.mDrawables == null)
        this.mDrawables = new ArrayList<Drawable>(); 
      if (!this.mDrawables.contains(param1Drawable)) {
        this.mDrawables.add(param1Drawable);
        invalidate(param1Drawable.getBounds());
        param1Drawable.setCallback((Drawable.Callback)this);
      } 
    }
    
    public void add(View param1View) {
      if (param1View.getParent() instanceof ViewGroup) {
        ViewGroup viewGroup = (ViewGroup)param1View.getParent();
        if (viewGroup != this.mHostView && viewGroup.getParent() != null && ViewCompat.isAttachedToWindow((View)viewGroup)) {
          int[] arrayOfInt1 = new int[2];
          int[] arrayOfInt2 = new int[2];
          viewGroup.getLocationOnScreen(arrayOfInt1);
          this.mHostView.getLocationOnScreen(arrayOfInt2);
          ViewCompat.offsetLeftAndRight(param1View, arrayOfInt1[0] - arrayOfInt2[0]);
          ViewCompat.offsetTopAndBottom(param1View, arrayOfInt1[1] - arrayOfInt2[1]);
        } 
        viewGroup.removeView(param1View);
        if (param1View.getParent() != null)
          viewGroup.removeView(param1View); 
      } 
      addView(param1View, getChildCount() - 1);
    }
    
    public void clear() {
      removeAllViews();
      ArrayList<Drawable> arrayList = this.mDrawables;
      if (arrayList != null)
        arrayList.clear(); 
    }
    
    protected void dispatchDraw(Canvas param1Canvas) {
      int i;
      int[] arrayOfInt1 = new int[2];
      int[] arrayOfInt2 = new int[2];
      this.mHostView.getLocationOnScreen(arrayOfInt1);
      this.mRequestingView.getLocationOnScreen(arrayOfInt2);
      byte b = 0;
      param1Canvas.translate((arrayOfInt2[0] - arrayOfInt1[0]), (arrayOfInt2[1] - arrayOfInt1[1]));
      param1Canvas.clipRect(new Rect(0, 0, this.mRequestingView.getWidth(), this.mRequestingView.getHeight()));
      super.dispatchDraw(param1Canvas);
      ArrayList<Drawable> arrayList = this.mDrawables;
      if (arrayList == null) {
        i = 0;
      } else {
        i = arrayList.size();
      } 
      while (b < i) {
        ((Drawable)this.mDrawables.get(b)).draw(param1Canvas);
        b++;
      } 
    }
    
    public boolean dispatchTouchEvent(MotionEvent param1MotionEvent) {
      return false;
    }
    
    public void invalidateChildFast(View param1View, Rect param1Rect) {
      if (this.mHostView != null) {
        int i = param1View.getLeft();
        int j = param1View.getTop();
        int[] arrayOfInt = new int[2];
        getOffset(arrayOfInt);
        param1Rect.offset(i + arrayOfInt[0], j + arrayOfInt[1]);
        this.mHostView.invalidate(param1Rect);
      } 
    }
    
    public ViewParent invalidateChildInParent(int[] param1ArrayOfint, Rect param1Rect) {
      if (this.mHostView != null) {
        param1Rect.offset(param1ArrayOfint[0], param1ArrayOfint[1]);
        if (this.mHostView instanceof ViewGroup) {
          param1ArrayOfint[0] = 0;
          param1ArrayOfint[1] = 0;
          int[] arrayOfInt = new int[2];
          getOffset(arrayOfInt);
          param1Rect.offset(arrayOfInt[0], arrayOfInt[1]);
          return super.invalidateChildInParent(param1ArrayOfint, param1Rect);
        } 
        invalidate(param1Rect);
      } 
      return null;
    }
    
    protected ViewParent invalidateChildInParentFast(int param1Int1, int param1Int2, Rect param1Rect) {
      if (this.mHostView instanceof ViewGroup && sInvalidateChildInParentFastMethod != null)
        try {
          getOffset(new int[2]);
          sInvalidateChildInParentFastMethod.invoke(this.mHostView, new Object[] { Integer.valueOf(param1Int1), Integer.valueOf(param1Int2), param1Rect });
        } catch (IllegalAccessException illegalAccessException) {
          illegalAccessException.printStackTrace();
        } catch (InvocationTargetException invocationTargetException) {
          invocationTargetException.printStackTrace();
        }  
      return null;
    }
    
    public void invalidateDrawable(Drawable param1Drawable) {
      invalidate(param1Drawable.getBounds());
    }
    
    boolean isEmpty() {
      if (getChildCount() == 0) {
        ArrayList<Drawable> arrayList = this.mDrawables;
        if (arrayList == null || arrayList.size() == 0)
          return true; 
      } 
      return false;
    }
    
    protected void onLayout(boolean param1Boolean, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {}
    
    public void remove(Drawable param1Drawable) {
      ArrayList<Drawable> arrayList = this.mDrawables;
      if (arrayList != null) {
        arrayList.remove(param1Drawable);
        invalidate(param1Drawable.getBounds());
        param1Drawable.setCallback(null);
      } 
    }
    
    public void remove(View param1View) {
      removeView(param1View);
      if (isEmpty())
        this.mHostView.removeView((View)this); 
    }
    
    protected boolean verifyDrawable(Drawable param1Drawable) {
      if (!super.verifyDrawable(param1Drawable)) {
        ArrayList<Drawable> arrayList = this.mDrawables;
        return (arrayList != null && arrayList.contains(param1Drawable));
      } 
      return true;
    }
    
    static class TouchInterceptor extends View {
      TouchInterceptor(Context param2Context) {
        super(param2Context);
      }
    }
  }
  
  static class TouchInterceptor extends View {
    TouchInterceptor(Context param1Context) {
      super(param1Context);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\ViewOverlayApi14.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */