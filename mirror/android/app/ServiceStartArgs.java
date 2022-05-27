package mirror.android.app;

import android.content.Intent;
import mirror.MethodParams;
import mirror.RefBoolean;
import mirror.RefClass;
import mirror.RefConstructor;
import mirror.RefInt;
import mirror.RefObject;

public class ServiceStartArgs {
  public static Class<?> TYPE = RefClass.load(ServiceStartArgs.class, "android.app.ServiceStartArgs");
  
  public static RefObject<Intent> args;
  
  @MethodParams({boolean.class, int.class, int.class, Intent.class})
  public static RefConstructor<Object> ctor;
  
  public static RefInt flags;
  
  public static RefInt startId;
  
  public static RefBoolean taskRemoved;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\app\ServiceStartArgs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */