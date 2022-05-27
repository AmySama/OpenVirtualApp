package mirror.android.app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.IBinder;
import mirror.RefBoolean;
import mirror.RefClass;
import mirror.RefInt;
import mirror.RefObject;

public class Activity {
  public static Class<?> TYPE = RefClass.load(Activity.class, "android.app.Activity");
  
  public static RefObject<ActivityInfo> mActivityInfo;
  
  public static RefObject<String> mEmbeddedID;
  
  public static RefBoolean mFinished;
  
  public static RefObject<android.app.Activity> mParent;
  
  public static RefInt mResultCode;
  
  public static RefObject<Intent> mResultData;
  
  public static RefObject<IBinder> mToken;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\app\Activity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */