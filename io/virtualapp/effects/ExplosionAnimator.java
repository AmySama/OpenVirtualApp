package io.virtualapp.effects;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import io.virtualapp.App;
import io.virtualapp.abs.ui.VUiKit;
import java.util.Random;

public class ExplosionAnimator extends ValueAnimator {
  static long DEFAULT_DURATION = 0L;
  
  private static final Interpolator DEFAULT_INTERPOLATOR = (Interpolator)new AccelerateInterpolator(0.6F);
  
  private static final float END_VALUE = 1.4F;
  
  private static final float V;
  
  private static final float W;
  
  private static final float X = VUiKit.dpToPx((Context)App.getApp(), 5);
  
  private static final float Y = VUiKit.dpToPx((Context)App.getApp(), 20);
  
  private Rect mBound;
  
  private View mContainer;
  
  private Paint mPaint = new Paint();
  
  private Particle[] mParticles;
  
  static {
    V = VUiKit.dpToPx((Context)App.getApp(), 2);
    W = VUiKit.dpToPx((Context)App.getApp(), 1);
    DEFAULT_DURATION = 1104L;
  }
  
  public ExplosionAnimator(View paramView, Bitmap paramBitmap, Rect paramRect) {
    this.mBound = new Rect(paramRect);
    this.mParticles = new Particle[225];
    Random random = new Random(System.currentTimeMillis());
    int i = paramBitmap.getWidth() / 17;
    int j = paramBitmap.getHeight() / 17;
    for (byte b = 0; b < 15; b++) {
      int k;
      for (k = 0; k < 15; k = m) {
        Particle[] arrayOfParticle = this.mParticles;
        int m = k + 1;
        arrayOfParticle[b * 15 + k] = generateParticle(paramBitmap.getPixel(m * i, (b + 1) * j), random);
      } 
    } 
    this.mContainer = paramView;
    setFloatValues(new float[] { 0.0F, 1.4F });
    setInterpolator((TimeInterpolator)DEFAULT_INTERPOLATOR);
    setDuration(DEFAULT_DURATION);
  }
  
  private Particle generateParticle(int paramInt, Random paramRandom) {
    Particle particle = new Particle();
    particle.color = paramInt;
    particle.radius = V;
    if (paramRandom.nextFloat() < 0.2F) {
      f1 = V;
      particle.baseRadius = f1 + (X - f1) * paramRandom.nextFloat();
    } else {
      f1 = W;
      particle.baseRadius = f1 + (V - f1) * paramRandom.nextFloat();
    } 
    float f2 = paramRandom.nextFloat();
    particle.top = this.mBound.height() * (paramRandom.nextFloat() * 0.18F + 0.2F);
    paramInt = f2 cmp 0.2F;
    if (paramInt < 0) {
      f1 = particle.top;
    } else {
      f1 = particle.top + particle.top * 0.2F * paramRandom.nextFloat();
    } 
    particle.top = f1;
    particle.bottom = this.mBound.height() * (paramRandom.nextFloat() - 0.5F) * 1.8F;
    if (paramInt < 0) {
      f1 = particle.bottom;
    } else {
      if (f2 < 0.8F) {
        f1 = particle.bottom;
        f2 = 0.6F;
      } else {
        f1 = particle.bottom;
        f2 = 0.3F;
      } 
      f1 *= f2;
    } 
    particle.bottom = f1;
    particle.mag = particle.top * 4.0F / particle.bottom;
    particle.neg = -particle.mag / particle.bottom;
    float f1 = this.mBound.centerX() + Y * (paramRandom.nextFloat() - 0.5F);
    particle.baseCx = f1;
    particle.cx = f1;
    f1 = this.mBound.centerY() + Y * (paramRandom.nextFloat() - 0.5F);
    particle.baseCy = f1;
    particle.cy = f1;
    particle.life = paramRandom.nextFloat() * 0.14F;
    particle.overflow = paramRandom.nextFloat() * 0.4F;
    particle.alpha = 1.0F;
    return particle;
  }
  
  public boolean draw(Canvas paramCanvas) {
    boolean bool = isStarted();
    byte b = 0;
    if (!bool)
      return false; 
    Particle[] arrayOfParticle = this.mParticles;
    int i = arrayOfParticle.length;
    while (b < i) {
      Particle particle = arrayOfParticle[b];
      particle.advance(((Float)getAnimatedValue()).floatValue());
      if (particle.alpha > 0.0F) {
        this.mPaint.setColor(particle.color);
        this.mPaint.setAlpha((int)(Color.alpha(particle.color) * particle.alpha));
        paramCanvas.drawCircle(particle.cx, particle.cy, particle.radius, this.mPaint);
      } 
      b++;
    } 
    this.mContainer.invalidate();
    return true;
  }
  
  public void start() {
    super.start();
    this.mContainer.invalidate(this.mBound);
  }
  
  private class Particle {
    float alpha;
    
    float baseCx;
    
    float baseCy;
    
    float baseRadius;
    
    float bottom;
    
    int color;
    
    float cx;
    
    float cy;
    
    float life;
    
    float mag;
    
    float neg;
    
    float overflow;
    
    float radius;
    
    float top;
    
    private Particle() {}
    
    public void advance(float param1Float) {
      float f1 = param1Float / 1.4F;
      float f2 = this.life;
      param1Float = 0.0F;
      if (f1 >= f2) {
        float f = this.overflow;
        if (f1 <= 1.0F - f) {
          f = (f1 - f2) / (1.0F - f2 - f);
          f1 = 1.4F * f;
          if (f >= 0.7F)
            param1Float = (f - 0.7F) / 0.3F; 
          this.alpha = 1.0F - param1Float;
          param1Float = this.bottom * f1;
          this.cx = this.baseCx + param1Float;
          this.cy = (float)(this.baseCy - this.neg * Math.pow(param1Float, 2.0D)) - param1Float * this.mag;
          this.radius = ExplosionAnimator.V + (this.baseRadius - ExplosionAnimator.V) * f1;
          return;
        } 
      } 
      this.alpha = 0.0F;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\effects\ExplosionAnimator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */