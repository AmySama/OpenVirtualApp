package com.lody.virtual.server.pm;

import android.content.Intent;
import com.lody.virtual.os.VUserHandle;
import com.lody.virtual.server.am.VActivityManagerService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PrivilegeAppOptimizer {
  private static final PrivilegeAppOptimizer sInstance = new PrivilegeAppOptimizer();
  
  private final List<String> privilegeApps = new ArrayList<String>();
  
  private final List<String> privilegeProcessNames = new ArrayList<String>();
  
  private PrivilegeAppOptimizer() {
    this.privilegeApps.add("com.google.android.gms");
    this.privilegeApps.add("com.google.android.gsf");
    this.privilegeProcessNames.add("com.google.android.gms.persistent");
    this.privilegeProcessNames.add("com.google.process.gapps");
  }
  
  public static PrivilegeAppOptimizer get() {
    return sInstance;
  }
  
  private Intent specifyApp(Intent paramIntent, String paramString, int paramInt) {
    paramIntent.putExtra("_VA_|_privilege_pkg_", paramString);
    paramIntent.putExtra("_VA_|_user_id_", paramInt);
    return paramIntent;
  }
  
  public void addPrivilegeApp(String paramString) {
    this.privilegeApps.add(paramString);
  }
  
  public List<String> getPrivilegeApps() {
    return Collections.unmodifiableList(this.privilegeApps);
  }
  
  public boolean isPrivilegeApp(String paramString) {
    return this.privilegeApps.contains(paramString);
  }
  
  public boolean isPrivilegeProcess(String paramString) {
    return this.privilegeProcessNames.contains(paramString);
  }
  
  public boolean performOptimize(String paramString, int paramInt) {
    if (!isPrivilegeApp(paramString))
      return false; 
    VActivityManagerService.get().sendBroadcastAsUser(specifyApp(new Intent("android.intent.action.BOOT_COMPLETED", null), paramString, paramInt), new VUserHandle(paramInt));
    return true;
  }
  
  public void performOptimizeAllApps() {
    Iterator<String> iterator = this.privilegeApps.iterator();
    while (iterator.hasNext())
      performOptimize(iterator.next(), -1); 
  }
  
  public void removePrivilegeApp(String paramString) {
    this.privilegeApps.remove(paramString);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\pm\PrivilegeAppOptimizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */