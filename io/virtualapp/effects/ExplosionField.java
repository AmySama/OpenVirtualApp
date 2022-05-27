package io.virtualapp.effects;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import io.virtualapp.App;
import io.virtualapp.abs.ui.VUiKit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ExplosionField extends View {
  private static final Canvas sCanvas = new Canvas();
  
  private int[] mExpandInset = new int[2];
  
  private List<ExplosionAnimator> mExplosions = new ArrayList<ExplosionAnimator>();
  
  public ExplosionField(Context paramContext) {
    super(paramContext);
    init();
  }
  
  public ExplosionField(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    init();
  }
  
  public ExplosionField(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    init();
  }
  
  public static ExplosionField attachToWindow(Activity paramActivity) {
    ViewGroup viewGroup = (ViewGroup)paramActivity.findViewById(16908290);
    ExplosionField explosionField = new ExplosionField((Context)paramActivity);
    viewGroup.addView(explosionField, new ViewGroup.LayoutParams(-1, -1));
    return explosionField;
  }
  
  public static ExplosionField attachToWindow(ViewGroup paramViewGroup, Activity paramActivity) {
    ExplosionField explosionField = new ExplosionField((Context)paramActivity);
    paramViewGroup.addView(explosionField, new ViewGroup.LayoutParams(-1, -1));
    return explosionField;
  }
  
  public static Bitmap createBitmapFromView(View paramView) {
    if (paramView instanceof ImageView) {
      Drawable drawable = ((ImageView)paramView).getDrawable();
      if (drawable != null && drawable instanceof BitmapDrawable)
        return ((BitmapDrawable)drawable).getBitmap(); 
    } 
    paramView.clearFocus();
    Bitmap bitmap = createBitmapSafely(paramView.getWidth(), paramView.getHeight(), Bitmap.Config.ARGB_8888, 1);
    if (bitmap != null)
      synchronized (sCanvas) {
        Canvas canvas = sCanvas;
        canvas.setBitmap(bitmap);
        paramView.draw(canvas);
        canvas.setBitmap(null);
      }  
    return bitmap;
  }
  
  public static Bitmap createBitmapSafely(int paramInt1, int paramInt2, Bitmap.Config paramConfig, int paramInt3) {
    try {
      return Bitmap.createBitmap(paramInt1, paramInt2, paramConfig);
    } catch (OutOfMemoryError outOfMemoryError) {
      outOfMemoryError.printStackTrace();
      if (paramInt3 > 0) {
        System.gc();
        return createBitmapSafely(paramInt1, paramInt2, paramConfig, paramInt3 - 1);
      } 
      return null;
    } 
  }
  
  private void init() {
    Arrays.fill(this.mExpandInset, VUiKit.dpToPx((Context)App.getApp(), 32));
  }
  
  public void clear() {
    this.mExplosions.clear();
    invalidate();
  }
  
  public void expandExplosionBound(int paramInt1, int paramInt2) {
    int[] arrayOfInt = this.mExpandInset;
    arrayOfInt[0] = paramInt1;
    arrayOfInt[1] = paramInt2;
  }
  
  public void explode(Bitmap paramBitmap, Rect paramRect, long paramLong1, long paramLong2) {
    ExplosionAnimator explosionAnimator = new ExplosionAnimator(this, paramBitmap, paramRect);
    explosionAnimator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
          public void onAnimationEnd(Animator param1Animator) {
            ExplosionField.this.mExplosions.remove(param1Animator);
          }
        });
    explosionAnimator.setStartDelay(paramLong1);
    explosionAnimator.setDuration(paramLong2);
    this.mExplosions.add(explosionAnimator);
    explosionAnimator.start();
  }
  
  public void explode(View paramView) {
    explode(paramView, (OnExplodeFinishListener)null);
  }
  
  public void explode(final View view, final OnExplodeFinishListener listener) {
    Rect rect = new Rect();
    view.getGlobalVisibleRect(rect);
    int[] arrayOfInt = new int[2];
    getLocationOnScreen(arrayOfInt);
    rect.offset(-arrayOfInt[0], -arrayOfInt[1]);
    arrayOfInt = this.mExpandInset;
    rect.inset(-arrayOfInt[0], -arrayOfInt[1]);
    ValueAnimator valueAnimator = ValueAnimator.ofFloat(new float[] { 0.0F, 1.0F }).setDuration(150L);
    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
          Random random = new Random();
          
          public void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
            view.setTranslationX((this.random.nextFloat() - 0.5F) * view.getWidth() * 0.05F);
            view.setTranslationY((this.random.nextFloat() - 0.5F) * view.getHeight() * 0.05F);
          }
        });
    valueAnimator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
          public void onAnimationEnd(Animator param1Animator) {
            ExplosionField.OnExplodeFinishListener onExplodeFinishListener = listener;
            if (onExplodeFinishListener != null)
              onExplodeFinishListener.onExplodeFinish(view); 
          }
        });
    valueAnimator.start();
    ViewPropertyAnimator viewPropertyAnimator = view.animate().setDuration(150L);
    long l = 100L;
    viewPropertyAnimator.setStartDelay(l).scaleX(0.0F).scaleY(0.0F).alpha(0.0F).start();
    explode(createBitmapFromView(view), rect, l, ExplosionAnimator.DEFAULT_DURATION);
  }
  
  protected void onDraw(Canvas paramCanvas) {
    super.onDraw(paramCanvas);
    Iterator<ExplosionAnimator> iterator = this.mExplosions.iterator();
    while (iterator.hasNext())
      ((ExplosionAnimator)iterator.next()).draw(paramCanvas); 
  }
  
  public static interface OnExplodeFinishListener {
    void onExplodeFinish(View param1View);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\effects\ExplosionField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */