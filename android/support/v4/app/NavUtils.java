package android.support.v4.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

public final class NavUtils {
  public static final String PARENT_ACTIVITY = "android.support.PARENT_ACTIVITY";
  
  private static final String TAG = "NavUtils";
  
  public static Intent getParentActivityIntent(Activity paramActivity) {
    if (Build.VERSION.SDK_INT >= 16) {
      Intent intent = paramActivity.getParentActivityIntent();
      if (intent != null)
        return intent; 
    } 
    String str = getParentActivityName(paramActivity);
    if (str == null)
      return null; 
    ComponentName componentName = new ComponentName((Context)paramActivity, str);
    try {
      Intent intent;
      if (getParentActivityName((Context)paramActivity, componentName) == null) {
        intent = Intent.makeMainActivity(componentName);
      } else {
        intent = new Intent();
        this();
        intent = intent.setComponent(componentName);
      } 
      return intent;
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("getParentActivityIntent: bad parentActivityName '");
      stringBuilder.append(str);
      stringBuilder.append("' in manifest");
      Log.e("NavUtils", stringBuilder.toString());
      return null;
    } 
  }
  
  public static Intent getParentActivityIntent(Context paramContext, ComponentName paramComponentName) throws PackageManager.NameNotFoundException {
    Intent intent;
    String str = getParentActivityName(paramContext, paramComponentName);
    if (str == null)
      return null; 
    paramComponentName = new ComponentName(paramComponentName.getPackageName(), str);
    if (getParentActivityName(paramContext, paramComponentName) == null) {
      intent = Intent.makeMainActivity(paramComponentName);
    } else {
      intent = (new Intent()).setComponent(paramComponentName);
    } 
    return intent;
  }
  
  public static Intent getParentActivityIntent(Context paramContext, Class<?> paramClass) throws PackageManager.NameNotFoundException {
    Intent intent;
    String str = getParentActivityName(paramContext, new ComponentName(paramContext, paramClass));
    if (str == null)
      return null; 
    ComponentName componentName = new ComponentName(paramContext, str);
    if (getParentActivityName(paramContext, componentName) == null) {
      intent = Intent.makeMainActivity(componentName);
    } else {
      intent = (new Intent()).setComponent(componentName);
    } 
    return intent;
  }
  
  public static String getParentActivityName(Activity paramActivity) {
    try {
      return getParentActivityName((Context)paramActivity, paramActivity.getComponentName());
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      throw new IllegalArgumentException(nameNotFoundException);
    } 
  }
  
  public static String getParentActivityName(Context paramContext, ComponentName paramComponentName) throws PackageManager.NameNotFoundException {
    ActivityInfo activityInfo = paramContext.getPackageManager().getActivityInfo(paramComponentName, 128);
    if (Build.VERSION.SDK_INT >= 16) {
      String str = activityInfo.parentActivityName;
      if (str != null)
        return str; 
    } 
    if (activityInfo.metaData == null)
      return null; 
    String str2 = activityInfo.metaData.getString("android.support.PARENT_ACTIVITY");
    if (str2 == null)
      return null; 
    String str1 = str2;
    if (str2.charAt(0) == '.') {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramContext.getPackageName());
      stringBuilder.append(str2);
      str1 = stringBuilder.toString();
    } 
    return str1;
  }
  
  public static void navigateUpFromSameTask(Activity paramActivity) {
    Intent intent = getParentActivityIntent(paramActivity);
    if (intent != null) {
      navigateUpTo(paramActivity, intent);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Activity ");
    stringBuilder.append(paramActivity.getClass().getSimpleName());
    stringBuilder.append(" does not have a parent activity name specified.");
    stringBuilder.append(" (Did you forget to add the android.support.PARENT_ACTIVITY <meta-data> ");
    stringBuilder.append(" element in your manifest?)");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public static void navigateUpTo(Activity paramActivity, Intent paramIntent) {
    if (Build.VERSION.SDK_INT >= 16) {
      paramActivity.navigateUpTo(paramIntent);
    } else {
      paramIntent.addFlags(67108864);
      paramActivity.startActivity(paramIntent);
      paramActivity.finish();
    } 
  }
  
  public static boolean shouldUpRecreateTask(Activity paramActivity, Intent paramIntent) {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 16)
      return paramActivity.shouldUpRecreateTask(paramIntent); 
    String str = paramActivity.getIntent().getAction();
    if (str != null && !str.equals("android.intent.action.MAIN")) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\app\NavUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */