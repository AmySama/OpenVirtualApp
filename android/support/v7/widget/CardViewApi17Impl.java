package android.support.v7.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

class CardViewApi17Impl extends CardViewBaseImpl {
  public void initStatic() {
    RoundRectDrawableWithShadow.sRoundRectHelper = new RoundRectDrawableWithShadow.RoundRectHelper() {
        public void drawRoundRect(Canvas param1Canvas, RectF param1RectF, float param1Float, Paint param1Paint) {
          param1Canvas.drawRoundRect(param1RectF, param1Float, param1Float, param1Paint);
        }
      };
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\CardViewApi17Impl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */