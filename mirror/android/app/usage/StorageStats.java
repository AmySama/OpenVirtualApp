package mirror.android.app.usage;

import mirror.RefClass;
import mirror.RefConstructor;
import mirror.RefLong;

public class StorageStats {
  public static Class<?> TYPE = RefClass.load(StorageStats.class, "android.app.usage.StorageStats");
  
  public static RefLong cacheBytes;
  
  public static RefLong codeBytes;
  
  public static RefConstructor<android.app.usage.StorageStats> ctor;
  
  public static RefLong dataBytes;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\ap\\usage\StorageStats.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */