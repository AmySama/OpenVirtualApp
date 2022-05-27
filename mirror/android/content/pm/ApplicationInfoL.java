package mirror.android.content.pm;

import android.content.pm.ApplicationInfo;
import mirror.RefBoolean;
import mirror.RefClass;
import mirror.RefObject;

public class ApplicationInfoL {
  public static Class<?> TYPE = RefClass.load(ApplicationInfoL.class, ApplicationInfo.class);
  
  public static RefObject<String> nativeLibraryRootDir;
  
  public static RefBoolean nativeLibraryRootRequiresIsa;
  
  public static RefObject<String> primaryCpuAbi;
  
  public static RefObject<String> scanPublicSourceDir;
  
  public static RefObject<String> scanSourceDir;
  
  public static RefObject<String> secondaryCpuAbi;
  
  public static RefObject<String> secondaryNativeLibraryDir;
  
  public static RefObject<String[]> splitPublicSourceDirs;
  
  public static RefObject<String[]> splitSourceDirs;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\content\pm\ApplicationInfoL.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */