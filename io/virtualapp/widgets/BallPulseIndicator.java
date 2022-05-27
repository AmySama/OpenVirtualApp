package io.virtualapp.widgets;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.ArrayList;

public class BallPulseIndicator extends Indicator {
  public static final float SCALE = 1.0F;
  
  private float[] scaleFloats = new float[] { 1.0F, 1.0F, 1.0F };
  
  public void draw(Canvas paramCanvas, Paint paramPaint) {
    float f1 = (Math.min(getWidth(), getHeight()) - 8.0F) / 6.0F;
    float f2 = (getWidth() / 2);
    float f3 = 2.0F * f1;
    float f4 = (getHeight() / 2);
    for (byte b = 0; b < 3; b++) {
      paramCanvas.save();
      float f = b;
      paramCanvas.translate(f3 * f + f2 - f3 + 4.0F + f * 4.0F, f4);
      float[] arrayOfFloat = this.scaleFloats;
      paramCanvas.scale(arrayOfFloat[b], arrayOfFloat[b]);
      paramCanvas.drawCircle(0.0F, 0.0F, f1, paramPaint);
      paramCanvas.restore();
    } 
  }
  
  public ArrayList<ValueAnimator> onCreateAnimators() {
    ArrayList<ValueAnimator> arrayList = new ArrayList();
    for (byte b = 0; b < 3; b++) {
      ValueAnimator valueAnimator = ValueAnimator.ofFloat(new float[] { 1.0F, 0.3F, 1.0F });
      valueAnimator.setDuration(750L);
      valueAnimator.setRepeatCount(-1);
      (new int[3])[0] = 120;
      (new int[3])[1] = 240;
      (new int[3])[2] = 360;
      valueAnimator.setStartDelay((new int[3])[b]);
      addUpdateListener(valueAnimator, new _$$Lambda$BallPulseIndicator$8aJprY3_W6DEw7466nPrIQYI31w(this, b));
      arrayList.add(valueAnimator);
    } 
    return arrayList;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\BallPulseIndicator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */