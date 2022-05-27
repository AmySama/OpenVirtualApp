package android.support.transition;

import android.graphics.Matrix;
import android.graphics.RectF;

class MatrixUtils {
  static final Matrix IDENTITY_MATRIX = new Matrix() {
      void oops() {
        throw new IllegalStateException("Matrix can not be modified");
      }
      
      public boolean postConcat(Matrix param1Matrix) {
        oops();
        return false;
      }
      
      public boolean postRotate(float param1Float) {
        oops();
        return false;
      }
      
      public boolean postRotate(float param1Float1, float param1Float2, float param1Float3) {
        oops();
        return false;
      }
      
      public boolean postScale(float param1Float1, float param1Float2) {
        oops();
        return false;
      }
      
      public boolean postScale(float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
        oops();
        return false;
      }
      
      public boolean postSkew(float param1Float1, float param1Float2) {
        oops();
        return false;
      }
      
      public boolean postSkew(float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
        oops();
        return false;
      }
      
      public boolean postTranslate(float param1Float1, float param1Float2) {
        oops();
        return false;
      }
      
      public boolean preConcat(Matrix param1Matrix) {
        oops();
        return false;
      }
      
      public boolean preRotate(float param1Float) {
        oops();
        return false;
      }
      
      public boolean preRotate(float param1Float1, float param1Float2, float param1Float3) {
        oops();
        return false;
      }
      
      public boolean preScale(float param1Float1, float param1Float2) {
        oops();
        return false;
      }
      
      public boolean preScale(float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
        oops();
        return false;
      }
      
      public boolean preSkew(float param1Float1, float param1Float2) {
        oops();
        return false;
      }
      
      public boolean preSkew(float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
        oops();
        return false;
      }
      
      public boolean preTranslate(float param1Float1, float param1Float2) {
        oops();
        return false;
      }
      
      public void reset() {
        oops();
      }
      
      public void set(Matrix param1Matrix) {
        oops();
      }
      
      public boolean setConcat(Matrix param1Matrix1, Matrix param1Matrix2) {
        oops();
        return false;
      }
      
      public boolean setPolyToPoly(float[] param1ArrayOffloat1, int param1Int1, float[] param1ArrayOffloat2, int param1Int2, int param1Int3) {
        oops();
        return false;
      }
      
      public boolean setRectToRect(RectF param1RectF1, RectF param1RectF2, Matrix.ScaleToFit param1ScaleToFit) {
        oops();
        return false;
      }
      
      public void setRotate(float param1Float) {
        oops();
      }
      
      public void setRotate(float param1Float1, float param1Float2, float param1Float3) {
        oops();
      }
      
      public void setScale(float param1Float1, float param1Float2) {
        oops();
      }
      
      public void setScale(float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
        oops();
      }
      
      public void setSinCos(float param1Float1, float param1Float2) {
        oops();
      }
      
      public void setSinCos(float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
        oops();
      }
      
      public void setSkew(float param1Float1, float param1Float2) {
        oops();
      }
      
      public void setSkew(float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
        oops();
      }
      
      public void setTranslate(float param1Float1, float param1Float2) {
        oops();
      }
      
      public void setValues(float[] param1ArrayOffloat) {
        oops();
      }
    };
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\MatrixUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */