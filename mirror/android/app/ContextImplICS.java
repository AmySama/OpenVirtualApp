package mirror.android.app;

import java.io.File;
import mirror.RefClass;
import mirror.RefObject;

public class ContextImplICS {
  public static Class<?> TYPE = RefClass.load(ContextImplICS.class, "android.app.ContextImpl");
  
  public static RefObject<File> mExternalCacheDir;
  
  public static RefObject<File> mExternalFilesDir;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\app\ContextImplICS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */