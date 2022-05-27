package com.lody.virtual.os;

import android.os.Binder;
import com.lody.virtual.client.ipc.VActivityManager;

public class VBinder {
  public static int getBaseCallingUid() {
    return VUserHandle.getAppId(getCallingUid());
  }
  
  public static int getCallingPid() {
    return Binder.getCallingPid();
  }
  
  public static int getCallingUid() {
    return VActivityManager.get().getUidByPid(Binder.getCallingPid());
  }
  
  public static VUserHandle getCallingUserHandle() {
    return VUserHandle.getCallingUserHandle();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\os\VBinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */