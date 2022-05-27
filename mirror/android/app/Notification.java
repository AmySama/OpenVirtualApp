package mirror.android.app;

import android.app.PendingIntent;
import android.content.Context;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefMethod;

public class Notification {
  public static Class<?> TYPE = RefClass.load(Notification.class, android.app.Notification.class);
  
  @MethodParams({Context.class, CharSequence.class, CharSequence.class, PendingIntent.class})
  public static RefMethod<Void> setLatestEventInfo;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\app\Notification.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */