package android.support.v4.math;

public class MathUtils {
  public static double clamp(double paramDouble1, double paramDouble2, double paramDouble3) {
    return (paramDouble1 < paramDouble2) ? paramDouble2 : ((paramDouble1 > paramDouble3) ? paramDouble3 : paramDouble1);
  }
  
  public static float clamp(float paramFloat1, float paramFloat2, float paramFloat3) {
    return (paramFloat1 < paramFloat2) ? paramFloat2 : ((paramFloat1 > paramFloat3) ? paramFloat3 : paramFloat1);
  }
  
  public static int clamp(int paramInt1, int paramInt2, int paramInt3) {
    return (paramInt1 < paramInt2) ? paramInt2 : ((paramInt1 > paramInt3) ? paramInt3 : paramInt1);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\math\MathUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */