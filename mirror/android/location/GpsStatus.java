package mirror.android.location;

import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefMethod;

public class GpsStatus {
  public static Class<?> TYPE = RefClass.load(GpsStatus.class, android.location.GpsStatus.class);
  
  @MethodParams({int.class, int[].class, float[].class, float[].class, float[].class, int.class, int.class, int.class})
  public static RefMethod<Void> setStatus;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\location\GpsStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */