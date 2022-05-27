package android.support.v4.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.widget.EdgeEffect;

public final class EdgeEffectCompat {
  private static final EdgeEffectBaseImpl IMPL;
  
  private EdgeEffect mEdgeEffect;
  
  static {
    if (Build.VERSION.SDK_INT >= 21) {
      IMPL = new EdgeEffectApi21Impl();
    } else {
      IMPL = new EdgeEffectBaseImpl();
    } 
  }
  
  @Deprecated
  public EdgeEffectCompat(Context paramContext) {
    this.mEdgeEffect = new EdgeEffect(paramContext);
  }
  
  public static void onPull(EdgeEffect paramEdgeEffect, float paramFloat1, float paramFloat2) {
    IMPL.onPull(paramEdgeEffect, paramFloat1, paramFloat2);
  }
  
  @Deprecated
  public boolean draw(Canvas paramCanvas) {
    return this.mEdgeEffect.draw(paramCanvas);
  }
  
  @Deprecated
  public void finish() {
    this.mEdgeEffect.finish();
  }
  
  @Deprecated
  public boolean isFinished() {
    return this.mEdgeEffect.isFinished();
  }
  
  @Deprecated
  public boolean onAbsorb(int paramInt) {
    this.mEdgeEffect.onAbsorb(paramInt);
    return true;
  }
  
  @Deprecated
  public boolean onPull(float paramFloat) {
    this.mEdgeEffect.onPull(paramFloat);
    return true;
  }
  
  @Deprecated
  public boolean onPull(float paramFloat1, float paramFloat2) {
    IMPL.onPull(this.mEdgeEffect, paramFloat1, paramFloat2);
    return true;
  }
  
  @Deprecated
  public boolean onRelease() {
    this.mEdgeEffect.onRelease();
    return this.mEdgeEffect.isFinished();
  }
  
  @Deprecated
  public void setSize(int paramInt1, int paramInt2) {
    this.mEdgeEffect.setSize(paramInt1, paramInt2);
  }
  
  static class EdgeEffectApi21Impl extends EdgeEffectBaseImpl {
    public void onPull(EdgeEffect param1EdgeEffect, float param1Float1, float param1Float2) {
      param1EdgeEffect.onPull(param1Float1, param1Float2);
    }
  }
  
  static class EdgeEffectBaseImpl {
    public void onPull(EdgeEffect param1EdgeEffect, float param1Float1, float param1Float2) {
      param1EdgeEffect.onPull(param1Float1);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\widget\EdgeEffectCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */