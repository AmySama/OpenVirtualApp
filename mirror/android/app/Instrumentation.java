package mirror.android.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefMethod;

public class Instrumentation {
  public static Class<?> TYPE = RefClass.load(Instrumentation.class, android.app.Instrumentation.class);
  
  @MethodParams({Context.class, IBinder.class, IBinder.class, Activity.class, Intent.class, int.class, Bundle.class})
  public static RefMethod<android.app.Instrumentation.ActivityResult> execStartActivity;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\app\Instrumentation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */