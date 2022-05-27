package mirror.android.util;

import mirror.RefClass;
import mirror.RefMethod;
import mirror.RefObject;

public class Singleton {
  public static Class<?> TYPE = RefClass.load(Singleton.class, "android.util.Singleton");
  
  public static RefMethod<Object> get;
  
  public static RefObject<Object> mInstance;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\androi\\util\Singleton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */