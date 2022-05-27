package mirror.android.app;

import java.io.File;
import mirror.RefClass;
import mirror.RefObject;

public class ContextImplKitkat {
  public static Class<?> TYPE = RefClass.load(ContextImplKitkat.class, "android.app.ContextImpl");
  
  public static RefObject<Object> mDisplayAdjustments;
  
  public static RefObject<File[]> mExternalCacheDirs;
  
  public static RefObject<File[]> mExternalFilesDirs;
  
  public static RefObject<String> mOpPackageName;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\app\ContextImplKitkat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */