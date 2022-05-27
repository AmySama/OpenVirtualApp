package mirror.android.widget;

import android.content.pm.ApplicationInfo;
import java.util.ArrayList;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefConstructor;
import mirror.RefObject;

public class RemoteViews {
  public static Class<?> TYPE = RefClass.load(RemoteViews.class, android.widget.RemoteViews.class);
  
  @MethodParams({ApplicationInfo.class, int.class})
  public static RefConstructor<android.widget.RemoteViews> ctor;
  
  public static RefObject<ArrayList<Object>> mActions;
  
  public static RefObject<ApplicationInfo> mApplication;
  
  public static RefObject<String> mPackage;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\widget\RemoteViews.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */