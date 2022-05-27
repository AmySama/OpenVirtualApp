package io.virtualapp.home.repo;

import android.content.Context;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.remote.InstalledAppInfo;
import io.virtualapp.App;
import io.virtualapp.abs.Callback;
import io.virtualapp.abs.ui.VUiKit;
import io.virtualapp.home.models.PackageAppData;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.jdeferred.Promise;

public class PackageAppDataStorage {
  private static final PackageAppDataStorage STORAGE = new PackageAppDataStorage();
  
  private final Map<String, PackageAppData> packageDataMap = new HashMap<String, PackageAppData>();
  
  public static PackageAppDataStorage get() {
    return STORAGE;
  }
  
  private PackageAppData loadAppData(String paramString) {
    InstalledAppInfo installedAppInfo = VirtualCore.get().getInstalledAppInfo(paramString, 0);
    if (installedAppInfo != null) {
      PackageAppData packageAppData = new PackageAppData((Context)App.getApp(), installedAppInfo);
      synchronized (this.packageDataMap) {
        this.packageDataMap.put(paramString, packageAppData);
        return packageAppData;
      } 
    } 
    return null;
  }
  
  public PackageAppData acquire(String paramString) {
    synchronized (this.packageDataMap) {
      PackageAppData packageAppData1 = this.packageDataMap.get(paramString);
      PackageAppData packageAppData2 = packageAppData1;
      if (packageAppData1 == null)
        packageAppData2 = loadAppData(paramString); 
      return packageAppData2;
    } 
  }
  
  public void acquire(String paramString, Callback<PackageAppData> paramCallback) {
    Promise promise = VUiKit.defer().when(new _$$Lambda$PackageAppDataStorage$oyV3gbFh046ognBfnddBHxz5Hl0(this, paramString));
    Objects.requireNonNull(paramCallback);
    promise.done(new _$$Lambda$4W_94zs7mEi0EMAuIUQVwaIjmoA(paramCallback));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\repo\PackageAppDataStorage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */