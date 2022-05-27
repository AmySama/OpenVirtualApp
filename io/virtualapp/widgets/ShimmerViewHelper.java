package io.virtualapp.widgets;

import android.content.res.TypedArray;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import io.virtualapp.R;

public class ShimmerViewHelper {
  private static final int DEFAULT_REFLECTION_COLOR = -1;
  
  private AnimationSetupCallback callback;
  
  private float gradientX;
  
  private boolean isSetUp;
  
  private boolean isShimmering;
  
  private LinearGradient linearGradient;
  
  private Matrix linearGradientMatrix;
  
  private Paint paint;
  
  private int primaryColor;
  
  private int reflectionColor;
  
  private View view;
  
  public ShimmerViewHelper(View paramView, Paint paramPaint, AttributeSet paramAttributeSet) {
    this.view = paramView;
    this.paint = paramPaint;
    init(paramAttributeSet);
  }
  
  private void init(AttributeSet paramAttributeSet) {
    this.reflectionColor = -1;
    if (paramAttributeSet != null) {
      TypedArray typedArray = this.view.getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.ShimmerView, 0, 0);
      if (typedArray != null)
        try {
          this.reflectionColor = typedArray.getColor(0, -1);
          typedArray.recycle();
        } catch (Exception exception) {
          Log.e("ShimmerTextView", "Error while creating the view:", exception);
          typedArray.recycle();
        } finally {
          Exception exception;
        }  
    } 
    this.linearGradientMatrix = new Matrix();
  }
  
  private void resetLinearGradient() {
    float f = -this.view.getWidth();
    int i = this.primaryColor;
    int j = this.reflectionColor;
    Shader.TileMode tileMode = Shader.TileMode.CLAMP;
    LinearGradient linearGradient = new LinearGradient(f, 0.0F, 0.0F, 0.0F, new int[] { i, j, i }, new float[] { 0.0F, 0.5F, 1.0F }, tileMode);
    this.linearGradient = linearGradient;
    this.paint.setShader((Shader)linearGradient);
  }
  
  public float getGradientX() {
    return this.gradientX;
  }
  
  public int getPrimaryColor() {
    return this.primaryColor;
  }
  
  public int getReflectionColor() {
    return this.reflectionColor;
  }
  
  public boolean isSetUp() {
    return this.isSetUp;
  }
  
  public boolean isShimmering() {
    return this.isShimmering;
  }
  
  public void onDraw() {
    if (this.isShimmering) {
      if (this.paint.getShader() == null)
        this.paint.setShader((Shader)this.linearGradient); 
      this.linearGradientMatrix.setTranslate(this.gradientX * 2.0F, 0.0F);
      this.linearGradient.setLocalMatrix(this.linearGradientMatrix);
    } else {
      this.paint.setShader(null);
    } 
  }
  
  protected void onSizeChanged() {
    resetLinearGradient();
    if (!this.isSetUp) {
      this.isSetUp = true;
      AnimationSetupCallback animationSetupCallback = this.callback;
      if (animationSetupCallback != null)
        animationSetupCallback.onSetupAnimation(this.view); 
    } 
  }
  
  public void setAnimationSetupCallback(AnimationSetupCallback paramAnimationSetupCallback) {
    this.callback = paramAnimationSetupCallback;
  }
  
  public void setGradientX(float paramFloat) {
    this.gradientX = paramFloat;
    this.view.invalidate();
  }
  
  public void setPrimaryColor(int paramInt) {
    this.primaryColor = paramInt;
    if (this.isSetUp)
      resetLinearGradient(); 
  }
  
  public void setReflectionColor(int paramInt) {
    this.reflectionColor = paramInt;
    if (this.isSetUp)
      resetLinearGradient(); 
  }
  
  public void setShimmering(boolean paramBoolean) {
    this.isShimmering = paramBoolean;
  }
  
  public static interface AnimationSetupCallback {
    void onSetupAnimation(View param1View);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\ShimmerViewHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */