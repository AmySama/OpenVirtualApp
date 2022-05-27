package mirror.android.content.pm;

import android.content.pm.ApplicationInfo;
import java.util.List;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefObject;

public class ApplicationInfoP {
  public static Class<?> TYPE = RefClass.load(ApplicationInfoP.class, ApplicationInfo.class);
  
  @MethodParams({int.class})
  public static RefObject<List<Object>> sharedLibraryInfos;
  
  public static List sharedLibraryInfos(ApplicationInfo paramApplicationInfo) {
    RefObject<List<Object>> refObject = sharedLibraryInfos;
    return (refObject != null) ? (List)refObject.get(paramApplicationInfo) : null;
  }
  
  public static void sharedLibraryInfos(ApplicationInfo paramApplicationInfo, List paramList) {
    RefObject<List<Object>> refObject = sharedLibraryInfos;
    if (refObject != null)
      refObject.set(paramApplicationInfo, paramList); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\content\pm\ApplicationInfoP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */