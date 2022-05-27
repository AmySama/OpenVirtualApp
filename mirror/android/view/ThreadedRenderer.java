package mirror.android.view;

import java.io.File;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class ThreadedRenderer {
  public static Class<?> TYPE = RefClass.load(ThreadedRenderer.class, "android.view.ThreadedRenderer");
  
  @MethodParams({File.class})
  public static RefStaticMethod<Void> setupDiskCache;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\view\ThreadedRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */