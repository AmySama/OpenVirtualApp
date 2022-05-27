package mirror.android.renderscript;

import java.io.File;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class RenderScriptCacheDir {
  public static Class<?> TYPE = RefClass.load(RenderScriptCacheDir.class, "android.renderscript.RenderScriptCacheDir");
  
  @MethodParams({File.class})
  public static RefStaticMethod<Void> setupDiskCache;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\renderscript\RenderScriptCacheDir.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */