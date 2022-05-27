package android.support.v4.content.pm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import java.util.Iterator;

public class ShortcutManagerCompat {
  static final String ACTION_INSTALL_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
  
  static final String INSTALL_SHORTCUT_PERMISSION = "com.android.launcher.permission.INSTALL_SHORTCUT";
  
  public static Intent createShortcutResultIntent(Context paramContext, ShortcutInfoCompat paramShortcutInfoCompat) {
    Intent intent;
    if (Build.VERSION.SDK_INT >= 26) {
      Intent intent1 = ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).createShortcutResultIntent(paramShortcutInfoCompat.toShortcutInfo());
    } else {
      paramContext = null;
    } 
    Context context = paramContext;
    if (paramContext == null)
      intent = new Intent(); 
    return paramShortcutInfoCompat.addToIntent(intent);
  }
  
  public static boolean isRequestPinShortcutSupported(Context paramContext) {
    if (Build.VERSION.SDK_INT >= 26)
      return ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).isRequestPinShortcutSupported(); 
    if (ContextCompat.checkSelfPermission(paramContext, "com.android.launcher.permission.INSTALL_SHORTCUT") != 0)
      return false; 
    Iterator iterator = paramContext.getPackageManager().queryBroadcastReceivers(new Intent("com.android.launcher.action.INSTALL_SHORTCUT"), 0).iterator();
    while (iterator.hasNext()) {
      String str = ((ResolveInfo)iterator.next()).activityInfo.permission;
      if (TextUtils.isEmpty(str) || "com.android.launcher.permission.INSTALL_SHORTCUT".equals(str))
        return true; 
    } 
    return false;
  }
  
  public static boolean requestPinShortcut(Context paramContext, ShortcutInfoCompat paramShortcutInfoCompat, final IntentSender callback) {
    if (Build.VERSION.SDK_INT >= 26)
      return ((ShortcutManager)paramContext.getSystemService(ShortcutManager.class)).requestPinShortcut(paramShortcutInfoCompat.toShortcutInfo(), callback); 
    if (!isRequestPinShortcutSupported(paramContext))
      return false; 
    Intent intent = paramShortcutInfoCompat.addToIntent(new Intent("com.android.launcher.action.INSTALL_SHORTCUT"));
    if (callback == null) {
      paramContext.sendBroadcast(intent);
      return true;
    } 
    paramContext.sendOrderedBroadcast(intent, null, new BroadcastReceiver() {
          public void onReceive(Context param1Context, Intent param1Intent) {
            try {
              callback.sendIntent(param1Context, 0, null, null, null);
            } catch (android.content.IntentSender.SendIntentException sendIntentException) {}
          }
        }null, -1, null, null);
    return true;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\content\pm\ShortcutManagerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */