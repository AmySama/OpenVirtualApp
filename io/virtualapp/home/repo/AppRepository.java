package io.virtualapp.home.repo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.lody.virtual.GmsSupport;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.stub.StubManifest;
import com.lody.virtual.remote.InstalledAppInfo;
import com.lody.virtual.remote.VAppInstallerParams;
import com.lody.virtual.remote.VAppInstallerResult;
import io.virtualapp.abs.ui.VUiKit;
import io.virtualapp.home.models.AppData;
import io.virtualapp.home.models.AppInfo;
import io.virtualapp.home.models.AppInfoLite;
import io.virtualapp.home.models.MultiplePackageAppData;
import io.virtualapp.home.models.PackageAppData;
import java.io.File;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.jdeferred.Promise;

public class AppRepository implements AppDataSource {
  private static final Collator COLLATOR = Collator.getInstance(Locale.CHINA);
  
  private static final List<String> SCAN_PATH_LIST = Arrays.asList(new String[] { ".", "backups/apps", "wandoujia/app", "tencent/tassistant/apk", "BaiduAsa9103056", "360Download", "pp/downloader", "pp/downloader/apk", "pp/downloader/silent/apk" });
  
  private Context mContext;
  
  private final Map<String, String> mLabels = new HashMap<String, String>();
  
  public AppRepository(Context paramContext) {
    this.mContext = paramContext;
  }
  
  private List<AppInfo> convertPackageInfoToAppData(Context paramContext, List<PackageInfo> paramList, boolean paramBoolean1, boolean paramBoolean2) {
    PackageManager packageManager = paramContext.getPackageManager();
    ArrayList<AppInfo> arrayList = new ArrayList(paramList.size());
    for (PackageInfo packageInfo : paramList) {
      String str;
      if (StubManifest.isHostPackageName(packageInfo.packageName) || (paramBoolean2 && GmsSupport.isGoogleAppOrService(packageInfo.packageName)) || (paramBoolean1 && isSystemApplication(packageInfo)) || (packageInfo.applicationInfo.flags & 0x4) == 0)
        continue; 
      ApplicationInfo applicationInfo = packageInfo.applicationInfo;
      if (applicationInfo.publicSourceDir != null) {
        str = applicationInfo.publicSourceDir;
      } else {
        str = applicationInfo.sourceDir;
      } 
      if (str == null)
        continue; 
      InstalledAppInfo installedAppInfo = VirtualCore.get().getInstalledAppInfo(packageInfo.packageName, 0);
      AppInfo appInfo = new AppInfo();
      appInfo.packageName = packageInfo.packageName;
      appInfo.cloneMode = paramBoolean1;
      appInfo.path = str;
      appInfo.icon = applicationInfo.loadIcon(packageManager);
      appInfo.name = applicationInfo.loadLabel(packageManager);
      appInfo.targetSdkVersion = packageInfo.applicationInfo.targetSdkVersion;
      appInfo.requestedPermissions = packageInfo.requestedPermissions;
      if (installedAppInfo != null) {
        appInfo.path = installedAppInfo.getApkPath();
        appInfo.cloneCount = (installedAppInfo.getInstalledUsers()).length;
      } 
      arrayList.add(appInfo);
    } 
    Collections.sort(arrayList, (Comparator<? super AppInfo>)_$$Lambda$AppRepository$g2CPB8Z_mZcYaAn8K2LWQ8EA80k.INSTANCE);
    return arrayList;
  }
  
  private List<PackageInfo> findAndParseAPKs(Context paramContext, File paramFile, List<String> paramList) {
    ArrayList<PackageInfo> arrayList = new ArrayList();
    if (paramList == null)
      return arrayList; 
    Iterator<String> iterator = paramList.iterator();
    while (iterator.hasNext()) {
      File[] arrayOfFile = (new File(paramFile, iterator.next())).listFiles();
      if (arrayOfFile == null)
        continue; 
      int i = arrayOfFile.length;
      for (byte b = 0; b < i; b++) {
        File file = arrayOfFile[b];
        if (file.getName().toLowerCase().endsWith(".apk")) {
          PackageInfo packageInfo;
          paramList = null;
          try {
            PackageInfo packageInfo1 = paramContext.getPackageManager().getPackageArchiveInfo(file.getAbsolutePath(), 4096);
            packageInfo = packageInfo1;
            packageInfo1.applicationInfo.sourceDir = file.getAbsolutePath();
            packageInfo = packageInfo1;
            packageInfo1.applicationInfo.publicSourceDir = file.getAbsolutePath();
            packageInfo = packageInfo1;
          } catch (Exception exception) {}
          if (packageInfo != null)
            arrayList.add(packageInfo); 
        } 
      } 
    } 
    return arrayList;
  }
  
  private static boolean isSystemApplication(PackageInfo paramPackageInfo) {
    int i = paramPackageInfo.applicationInfo.uid;
    boolean bool1 = true;
    boolean bool2 = bool1;
    if (i >= 10000) {
      bool2 = bool1;
      if (i <= 19999)
        if ((paramPackageInfo.applicationInfo.flags & 0x1) != 0) {
          bool2 = bool1;
        } else {
          bool2 = false;
        }  
    } 
    return bool2;
  }
  
  public VAppInstallerResult addVirtualApp(AppInfoLite paramAppInfoLite) {
    VAppInstallerParams vAppInstallerParams = new VAppInstallerParams();
    return VirtualCore.get().installPackage(paramAppInfoLite.getUri(), vAppInstallerParams);
  }
  
  public Promise<List<AppInfo>, Throwable, Void> getInstalledApps(Context paramContext) {
    return VUiKit.defer().when(new _$$Lambda$AppRepository$9ougTewtyti8LsqxMaw1O9BH6Uo(this, paramContext));
  }
  
  public List<AppInfo> getInstalledAppsDirect(Context paramContext) {
    return convertPackageInfoToAppData(paramContext, paramContext.getPackageManager().getInstalledPackages(4096), true, true);
  }
  
  public String getLabel(String paramString) {
    String str = this.mLabels.get(paramString);
    return (str == null) ? paramString : str;
  }
  
  public Promise<List<AppInfo>, Throwable, Void> getStorageApps(Context paramContext, File paramFile) {
    return VUiKit.defer().when(new _$$Lambda$AppRepository$QD9JVOXTZjXlcz_xpttv4laugbs(this, paramContext, paramFile));
  }
  
  public Promise<List<AppData>, Throwable, Void> getVirtualApps() {
    return VUiKit.defer().when(new _$$Lambda$AppRepository$UUZf00Dkbvj49p0E2gLIsSg5WOQ(this));
  }
  
  public List<AppData> getVirtualAppsDirect() {
    ArrayList<PackageAppData> arrayList = new ArrayList();
    for (InstalledAppInfo installedAppInfo : VirtualCore.get().getInstalledApps(0)) {
      if (!VirtualCore.get().isPackageLaunchable(installedAppInfo.packageName))
        continue; 
      PackageAppData packageAppData = new PackageAppData(this.mContext, installedAppInfo);
      if (VirtualCore.get().isAppInstalledAsUser(0, installedAppInfo.packageName))
        arrayList.add(packageAppData); 
      this.mLabels.put(installedAppInfo.packageName, packageAppData.name);
      for (int i : installedAppInfo.getInstalledUsers()) {
        if (i != 0)
          arrayList.add(new MultiplePackageAppData(packageAppData, i)); 
      } 
    } 
    return (List)arrayList;
  }
  
  public boolean removeVirtualApp(String paramString, int paramInt) {
    return VirtualCore.get().uninstallPackageAsUser(paramString, paramInt);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\repo\AppRepository.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */