package mirror.android.content.res;

import android.content.pm.ApplicationInfo;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefConstructor;
import mirror.RefStaticObject;

public class CompatibilityInfo {
  public static RefStaticObject<Object> DEFAULT_COMPATIBILITY_INFO;
  
  public static Class<?> TYPE = RefClass.load(CompatibilityInfo.class, "android.content.res.CompatibilityInfo");
  
  @MethodParams({ApplicationInfo.class, int.class, int.class, boolean.class})
  public static RefConstructor ctor;
  
  @MethodParams({ApplicationInfo.class, int.class, int.class, boolean.class, int.class})
  public static RefConstructor ctorLG;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\content\res\CompatibilityInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */