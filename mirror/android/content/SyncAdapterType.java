package mirror.android.content;

import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefConstructor;

public class SyncAdapterType {
  public static Class<?> TYPE = RefClass.load(SyncAdapterType.class, android.content.SyncAdapterType.class);
  
  @MethodParams({String.class, String.class, boolean.class, boolean.class, boolean.class, boolean.class, String.class})
  public static RefConstructor<android.content.SyncAdapterType> ctor;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\content\SyncAdapterType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */