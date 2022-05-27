package android.support.transition;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

class GhostViewApi14 extends View implements GhostViewImpl {
  Matrix mCurrentMatrix;
  
  private int mDeltaX;
  
  private int mDeltaY;
  
  private final Matrix mMatrix = new Matrix();
  
  private final ViewTreeObserver.OnPreDrawListener mOnPreDrawListener = new ViewTreeObserver.OnPreDrawListener() {
      public boolean onPreDraw() {
        GhostViewApi14 ghostViewApi14 = GhostViewApi14.this;
        ghostViewApi14.mCurrentMatrix = ghostViewApi14.mView.getMatrix();
        ViewCompat.postInvalidateOnAnimation(GhostViewApi14.this);
        if (GhostViewApi14.this.mStartParent != null && GhostViewApi14.this.mStartView != null) {
          GhostViewApi14.this.mStartParent.endViewTransition(GhostViewApi14.this.mStartView);
          ViewCompat.postInvalidateOnAnimation((View)GhostViewApi14.this.mStartParent);
          GhostViewApi14.this.mStartParent = null;
          GhostViewApi14.this.mStartView = null;
        } 
        return true;
      }
    };
  
  int mReferences;
  
  ViewGroup mStartParent;
  
  View mStartView;
  
  final View mView;
  
  GhostViewApi14(View paramView) {
    super(paramView.getContext());
    this.mView = paramView;
    setLayerType(2, null);
  }
  
  static GhostViewApi14 getGhostView(View paramView) {
    return (GhostViewApi14)paramView.getTag(R.id.ghost_view);
  }
  
  private static void setGhostView(View paramView, GhostViewApi14 paramGhostViewApi14) {
    paramView.setTag(R.id.ghost_view, paramGhostViewApi14);
  }
  
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    setGhostView(this.mView, this);
    int[] arrayOfInt1 = new int[2];
    int[] arrayOfInt2 = new int[2];
    getLocationOnScreen(arrayOfInt1);
    this.mView.getLocationOnScreen(arrayOfInt2);
    arrayOfInt2[0] = (int)(arrayOfInt2[0] - this.mView.getTranslationX());
    arrayOfInt2[1] = (int)(arrayOfInt2[1] - this.mView.getTranslationY());
    this.mDeltaX = arrayOfInt2[0] - arrayOfInt1[0];
    this.mDeltaY = arrayOfInt2[1] - arrayOfInt1[1];
    this.mView.getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener);
    this.mView.setVisibility(4);
  }
  
  protected void onDetachedFromWindow() {
    this.mView.getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener);
    this.mView.setVisibility(0);
    setGhostView(this.mView, (GhostViewApi14)null);
    super.onDetachedFromWindow();
  }
  
  protected void onDraw(Canvas paramCanvas) {
    this.mMatrix.set(this.mCurrentMatrix);
    this.mMatrix.postTranslate(this.mDeltaX, this.mDeltaY);
    paramCanvas.setMatrix(this.mMatrix);
    this.mView.draw(paramCanvas);
  }
  
  public void reserveEndViewTransition(ViewGroup paramViewGroup, View paramView) {
    this.mStartParent = paramViewGroup;
    this.mStartView = paramView;
  }
  
  public void setVisibility(int paramInt) {
    super.setVisibility(paramInt);
    View view = this.mView;
    if (paramInt == 0) {
      paramInt = 4;
    } else {
      paramInt = 0;
    } 
    view.setVisibility(paramInt);
  }
  
  static class Creator implements GhostViewImpl.Creator {
    private static FrameLayout findFrameLayout(ViewGroup param1ViewGroup) {
      ViewGroup viewGroup;
      while (!(param1ViewGroup instanceof FrameLayout)) {
        ViewParent viewParent = param1ViewGroup.getParent();
        if (!(viewParent instanceof ViewGroup))
          return null; 
        viewGroup = (ViewGroup)viewParent;
      } 
      return (FrameLayout)viewGroup;
    }
    
    public GhostViewImpl addGhost(View param1View, ViewGroup param1ViewGroup, Matrix param1Matrix) {
      GhostViewApi14 ghostViewApi142 = GhostViewApi14.getGhostView(param1View);
      GhostViewApi14 ghostViewApi141 = ghostViewApi142;
      if (ghostViewApi142 == null) {
        FrameLayout frameLayout = findFrameLayout(param1ViewGroup);
        if (frameLayout == null)
          return null; 
        ghostViewApi141 = new GhostViewApi14(param1View);
        frameLayout.addView(ghostViewApi141);
      } 
      ghostViewApi141.mReferences++;
      return ghostViewApi141;
    }
    
    public void removeGhost(View param1View) {
      param1View = GhostViewApi14.getGhostView(param1View);
      if (param1View != null) {
        ((GhostViewApi14)param1View).mReferences--;
        if (((GhostViewApi14)param1View).mReferences <= 0) {
          ViewParent viewParent = param1View.getParent();
          if (viewParent instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)viewParent;
            viewGroup.endViewTransition(param1View);
            viewGroup.removeView(param1View);
          } 
        } 
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\GhostViewApi14.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */