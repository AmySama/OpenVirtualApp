package io.virtualapp.home.repo;

import android.content.Context;
import com.lody.virtual.remote.VAppInstallerResult;
import io.virtualapp.home.models.AppData;
import io.virtualapp.home.models.AppInfo;
import io.virtualapp.home.models.AppInfoLite;
import java.io.File;
import java.util.List;
import org.jdeferred.Promise;

public interface AppDataSource {
  VAppInstallerResult addVirtualApp(AppInfoLite paramAppInfoLite);
  
  Promise<List<AppInfo>, Throwable, Void> getInstalledApps(Context paramContext);
  
  List<AppInfo> getInstalledAppsDirect(Context paramContext);
  
  String getLabel(String paramString);
  
  Promise<List<AppInfo>, Throwable, Void> getStorageApps(Context paramContext, File paramFile);
  
  Promise<List<AppData>, Throwable, Void> getVirtualApps();
  
  List<AppData> getVirtualAppsDirect();
  
  boolean removeVirtualApp(String paramString, int paramInt);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\repo\AppDataSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */