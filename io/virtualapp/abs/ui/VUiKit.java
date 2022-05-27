package io.virtualapp.abs.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import org.jdeferred.android.AndroidDeferredManager;

public class VUiKit {
  private static final AndroidDeferredManager gDM = new AndroidDeferredManager();
  
  private static final Handler gUiHandler = new Handler(Looper.getMainLooper());
  
  public static AndroidDeferredManager defer() {
    return gDM;
  }
  
  public static int dpToPx(Context paramContext, int paramInt) {
    return (int)TypedValue.applyDimension(1, paramInt, paramContext.getResources().getDisplayMetrics());
  }
  
  public static void post(Runnable paramRunnable) {
    gUiHandler.post(paramRunnable);
  }
  
  public static void postDelayed(long paramLong, Runnable paramRunnable) {
    gUiHandler.postDelayed(paramRunnable, paramLong);
  }
  
  public static void sleep(long paramLong) {
    try {
      Thread.sleep(paramLong);
    } catch (InterruptedException interruptedException) {
      interruptedException.printStackTrace();
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\ab\\ui\VUiKit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */