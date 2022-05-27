package mirror.android.ddm;

import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class DdmHandleAppNameJBMR1 {
  public static Class Class = RefClass.load(DdmHandleAppNameJBMR1.class, "android.ddm.DdmHandleAppName");
  
  @MethodParams({String.class, int.class})
  public static RefStaticMethod<Void> setAppName;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\ddm\DdmHandleAppNameJBMR1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */