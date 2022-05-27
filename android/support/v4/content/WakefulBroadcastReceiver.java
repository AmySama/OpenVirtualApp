package android.support.v4.content;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.util.SparseArray;

@Deprecated
public abstract class WakefulBroadcastReceiver extends BroadcastReceiver {
  private static final String EXTRA_WAKE_LOCK_ID = "android.support.content.wakelockid";
  
  private static int mNextId;
  
  private static final SparseArray<PowerManager.WakeLock> sActiveWakeLocks = new SparseArray();
  
  static {
    mNextId = 1;
  }
  
  public static boolean completeWakefulIntent(Intent paramIntent) {
    int i = paramIntent.getIntExtra("android.support.content.wakelockid", 0);
    if (i == 0)
      return false; 
    synchronized (sActiveWakeLocks) {
      PowerManager.WakeLock wakeLock = (PowerManager.WakeLock)sActiveWakeLocks.get(i);
      if (wakeLock != null) {
        wakeLock.release();
        sActiveWakeLocks.remove(i);
        return true;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append("No active wake lock id #");
      stringBuilder.append(i);
      Log.w("WakefulBroadcastReceiv.", stringBuilder.toString());
      return true;
    } 
  }
  
  public static ComponentName startWakefulService(Context paramContext, Intent paramIntent) {
    synchronized (sActiveWakeLocks) {
      int i = mNextId;
      int j = mNextId + 1;
      mNextId = j;
      if (j <= 0)
        mNextId = 1; 
      paramIntent.putExtra("android.support.content.wakelockid", i);
      ComponentName componentName = paramContext.startService(paramIntent);
      if (componentName == null)
        return null; 
      PowerManager powerManager = (PowerManager)paramContext.getSystemService("power");
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append("wake:");
      stringBuilder.append(componentName.flattenToShortString());
      PowerManager.WakeLock wakeLock = powerManager.newWakeLock(1, stringBuilder.toString());
      wakeLock.setReferenceCounted(false);
      wakeLock.acquire(60000L);
      sActiveWakeLocks.put(i, wakeLock);
      return componentName;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\content\WakefulBroadcastReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */