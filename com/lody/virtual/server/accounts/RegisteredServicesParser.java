package com.lody.virtual.server.accounts;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.ServiceInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import com.lody.virtual.server.pm.PackageCacheManager;
import com.lody.virtual.server.pm.PackageSetting;
import mirror.android.content.res.AssetManager;

public class RegisteredServicesParser {
  public XmlResourceParser getParser(Context paramContext, ServiceInfo paramServiceInfo, String paramString) {
    Bundle bundle = paramServiceInfo.metaData;
    if (bundle != null) {
      int i = bundle.getInt(paramString);
      if (i != 0)
        try {
          return getResources(paramContext, paramServiceInfo.applicationInfo).getXml(i);
        } catch (Exception exception) {
          exception.printStackTrace();
        }  
    } 
    return null;
  }
  
  public Resources getResources(Context paramContext, ApplicationInfo paramApplicationInfo) {
    PackageSetting packageSetting = PackageCacheManager.getSetting(paramApplicationInfo.packageName);
    if (packageSetting != null) {
      AssetManager assetManager = (AssetManager)AssetManager.ctor.newInstance();
      AssetManager.addAssetPath.call(assetManager, new Object[] { packageSetting.getPackagePath() });
      Resources resources = paramContext.getResources();
      return new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
    } 
    return null;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\accounts\RegisteredServicesParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */