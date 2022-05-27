package mirror.com.android.internal.content;

import java.io.File;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class NativeLibraryHelper {
  public static Class<?> TYPE = RefClass.load(NativeLibraryHelper.class, "com.android.internal.content.NativeLibraryHelper");
  
  @MethodParams({Handle.class, File.class, String.class})
  public static RefStaticMethod<Integer> copyNativeBinaries;
  
  @MethodParams({Handle.class, String[].class})
  public static RefStaticMethod<Integer> findSupportedAbi;
  
  public static class Handle {
    public static Class<?> TYPE = RefClass.load(Handle.class, "com.android.internal.content.NativeLibraryHelper$Handle");
    
    @MethodParams({File.class})
    public static RefStaticMethod<Object> create;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\com\android\internal\content\NativeLibraryHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */