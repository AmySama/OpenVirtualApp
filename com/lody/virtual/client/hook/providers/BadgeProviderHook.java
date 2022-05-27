package com.lody.virtual.client.hook.providers;

import android.os.Bundle;
import android.os.IInterface;
import com.lody.virtual.client.VClient;
import com.lody.virtual.client.hook.base.MethodBox;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.os.VUserHandle;
import com.lody.virtual.remote.BadgerInfo;
import java.lang.reflect.InvocationTargetException;

public class BadgeProviderHook extends ExternalProviderHook {
  public BadgeProviderHook(IInterface paramIInterface) {
    super(paramIInterface);
  }
  
  public Bundle call(MethodBox paramMethodBox, String paramString1, String paramString2, Bundle paramBundle) throws InvocationTargetException {
    Bundle bundle;
    if ("change_badge".equals(paramString1)) {
      BadgerInfo badgerInfo = new BadgerInfo();
      badgerInfo.userId = VUserHandle.myUserId();
      badgerInfo.packageName = paramBundle.getString("package");
      badgerInfo.className = paramBundle.getString("class");
      badgerInfo.badgerCount = paramBundle.getInt("badgenumber");
      VActivityManager.get().notifyBadgerChange(badgerInfo);
      bundle = new Bundle();
      bundle.putBoolean("success", true);
      return bundle;
    } 
    if ("setAppBadgeCount".equals(paramString1)) {
      BadgerInfo badgerInfo = new BadgerInfo();
      badgerInfo.userId = VUserHandle.myUserId();
      badgerInfo.packageName = VClient.get().getCurrentPackage();
      badgerInfo.badgerCount = paramBundle.getInt("app_badge_count");
      VActivityManager.get().notifyBadgerChange(badgerInfo);
      (new Bundle()).putBoolean("success", true);
    } 
    return super.call((MethodBox)bundle, paramString1, paramString2, paramBundle);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\providers\BadgeProviderHook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */