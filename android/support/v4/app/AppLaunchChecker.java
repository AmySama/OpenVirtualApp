package android.support.v4.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class AppLaunchChecker {
  private static final String KEY_STARTED_FROM_LAUNCHER = "startedFromLauncher";
  
  private static final String SHARED_PREFS_NAME = "android.support.AppLaunchChecker";
  
  public static boolean hasStartedFromLauncher(Context paramContext) {
    return paramContext.getSharedPreferences("android.support.AppLaunchChecker", 0).getBoolean("startedFromLauncher", false);
  }
  
  public static void onActivityCreate(Activity paramActivity) {
    SharedPreferences sharedPreferences = paramActivity.getSharedPreferences("android.support.AppLaunchChecker", 0);
    if (sharedPreferences.getBoolean("startedFromLauncher", false))
      return; 
    Intent intent = paramActivity.getIntent();
    if (intent == null)
      return; 
    if ("android.intent.action.MAIN".equals(intent.getAction()) && (intent.hasCategory("android.intent.category.LAUNCHER") || intent.hasCategory("android.intent.category.LEANBACK_LAUNCHER")))
      sharedPreferences.edit().putBoolean("startedFromLauncher", true).apply(); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\app\AppLaunchChecker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */