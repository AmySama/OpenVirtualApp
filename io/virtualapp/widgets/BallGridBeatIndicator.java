package io.virtualapp.widgets;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.ArrayList;

public class BallGridBeatIndicator extends Indicator {
  private static final int ALPHA = 255;
  
  private static final int[] ALPHAS = new int[] { 255, 255, 255, 255, 255, 255, 255, 255, 255 };
  
  public void draw(Canvas paramCanvas, Paint paramPaint) {
    float f1 = (getWidth() - 16.0F) / 6.0F;
    float f2 = (getWidth() / 2);
    float f3 = 2.0F * f1;
    float f4 = f3 + 4.0F;
    float f5 = (getWidth() / 2);
    for (byte b = 0; b < 3; b++) {
      for (byte b1 = 0; b1 < 3; b1++) {
        paramCanvas.save();
        float f6 = b1;
        float f7 = b;
        paramCanvas.translate(f3 * f6 + f2 - f4 + f6 * 4.0F, f3 * f7 + f5 - f4 + f7 * 4.0F);
        paramPaint.setAlpha(ALPHAS[b * 3 + b1]);
        paramCanvas.drawCircle(0.0F, 0.0F, f1, paramPaint);
        paramCanvas.restore();
      } 
    } 
  }
  
  public ArrayList<ValueAnimator> onCreateAnimators() {
    ArrayList<ValueAnimator> arrayList = new ArrayList();
    for (byte b = 0; b < 9; b++) {
      ValueAnimator valueAnimator = ValueAnimator.ofInt(new int[] { 255, 168, 255 });
      (new int[9])[0] = 960;
      (new int[9])[1] = 930;
      (new int[9])[2] = 1190;
      (new int[9])[3] = 1130;
      (new int[9])[4] = 1340;
      (new int[9])[5] = 940;
      (new int[9])[6] = 1200;
      (new int[9])[7] = 820;
      (new int[9])[8] = 1190;
      valueAnimator.setDuration((new int[9])[b]);
      valueAnimator.setRepeatCount(-1);
      (new int[9])[0] = 360;
      (new int[9])[1] = 400;
      (new int[9])[2] = 680;
      (new int[9])[3] = 410;
      (new int[9])[4] = 710;
      (new int[9])[5] = -150;
      (new int[9])[6] = -120;
      (new int[9])[7] = 10;
      (new int[9])[8] = 320;
      valueAnimator.setStartDelay((new int[9])[b]);
      addUpdateListener(valueAnimator, new _$$Lambda$BallGridBeatIndicator$azegqExm4KtHlgp6wVU7VFs6G6c(this, b));
      arrayList.add(valueAnimator);
    } 
    return arrayList;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\BallGridBeatIndicator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */