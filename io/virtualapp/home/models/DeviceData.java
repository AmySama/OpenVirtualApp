package io.virtualapp.home.models;

import android.content.Context;
import com.lody.virtual.client.ipc.VDeviceManager;
import com.lody.virtual.remote.InstalledAppInfo;

public class DeviceData extends SettingsData {
  public DeviceData(Context paramContext, InstalledAppInfo paramInstalledAppInfo, int paramInt) {
    super(paramContext, paramInstalledAppInfo, paramInt);
  }
  
  public boolean isMocking() {
    return VDeviceManager.get().isEnable(this.userId);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\models\DeviceData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */