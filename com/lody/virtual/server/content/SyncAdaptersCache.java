package com.lody.virtual.server.content;

import android.accounts.Account;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SyncAdapterType;
import android.content.pm.ComponentInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Xml;
import com.lody.virtual.helper.utils.ComponentUtils;
import com.lody.virtual.server.accounts.RegisteredServicesParser;
import com.lody.virtual.server.pm.VPackageManagerService;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mirror.android.content.SyncAdapterTypeN;
import mirror.com.android.internal.R_Hide;
import org.xmlpull.v1.XmlPullParser;

public class SyncAdaptersCache {
  private Context mContext;
  
  private final Map<String, SyncAdapterInfo> mSyncAdapterInfos = new HashMap<String, SyncAdapterInfo>();
  
  public SyncAdaptersCache(Context paramContext) {
    this.mContext = paramContext;
  }
  
  private void generateServicesMap(List<ResolveInfo> paramList, Map<String, SyncAdapterInfo> paramMap, RegisteredServicesParser paramRegisteredServicesParser) {
    for (ResolveInfo resolveInfo : paramList) {
      XmlResourceParser xmlResourceParser = paramRegisteredServicesParser.getParser(this.mContext, resolveInfo.serviceInfo, "android.content.SyncAdapter");
      if (xmlResourceParser != null)
        try {
          AttributeSet attributeSet = Xml.asAttributeSet((XmlPullParser)xmlResourceParser);
          while (true) {
            int i = xmlResourceParser.next();
            if (i != 1 && i != 2)
              continue; 
            break;
          } 
          if ("sync-adapter".equals(xmlResourceParser.getName())) {
            SyncAdapterType syncAdapterType = parseSyncAdapterType(paramRegisteredServicesParser.getResources(this.mContext, resolveInfo.serviceInfo.applicationInfo), attributeSet);
            if (syncAdapterType != null) {
              StringBuilder stringBuilder = new StringBuilder();
              this();
              stringBuilder.append(syncAdapterType.accountType);
              stringBuilder.append("/");
              stringBuilder.append(syncAdapterType.authority);
              String str = stringBuilder.toString();
              SyncAdapterInfo syncAdapterInfo = new SyncAdapterInfo();
              this(syncAdapterType, resolveInfo.serviceInfo);
              paramMap.put(str, syncAdapterInfo);
            } 
          } 
        } catch (Exception exception) {
          exception.printStackTrace();
        }  
    } 
  }
  
  private SyncAdapterType parseSyncAdapterType(Resources paramResources, AttributeSet paramAttributeSet) {
    TypedArray typedArray = paramResources.obtainAttributes(paramAttributeSet, (int[])R_Hide.styleable.SyncAdapter.get());
    try {
      SyncAdapterType syncAdapterType;
      String str2 = typedArray.getString(R_Hide.styleable.SyncAdapter_contentAuthority.get());
      String str1 = typedArray.getString(R_Hide.styleable.SyncAdapter_accountType.get());
      if (str2 == null || str1 == null)
        return null; 
      boolean bool1 = typedArray.getBoolean(R_Hide.styleable.SyncAdapter_userVisible.get(), true);
      boolean bool2 = typedArray.getBoolean(R_Hide.styleable.SyncAdapter_supportsUploading.get(), true);
      boolean bool3 = typedArray.getBoolean(R_Hide.styleable.SyncAdapter_isAlwaysSyncable.get(), true);
      boolean bool4 = typedArray.getBoolean(R_Hide.styleable.SyncAdapter_allowParallelSyncs.get(), true);
      String str3 = typedArray.getString(R_Hide.styleable.SyncAdapter_settingsActivity.get());
      if (SyncAdapterTypeN.ctor != null)
        return syncAdapterType; 
      return syncAdapterType;
    } finally {
      typedArray = null;
      typedArray.printStackTrace();
    } 
  }
  
  public Collection<SyncAdapterInfo> getAllServices() {
    return this.mSyncAdapterInfos.values();
  }
  
  public SyncAdapterInfo getServiceInfo(Account paramAccount, String paramString) {
    synchronized (this.mSyncAdapterInfos) {
      Map<String, SyncAdapterInfo> map = this.mSyncAdapterInfos;
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append(paramAccount.type);
      stringBuilder.append("/");
      stringBuilder.append(paramString);
      return map.get(stringBuilder.toString());
    } 
  }
  
  public void refreshServiceCache(String paramString) {
    Intent intent = new Intent("android.content.SyncAdapter");
    if (paramString != null)
      intent.setPackage(paramString); 
    generateServicesMap(VPackageManagerService.get().queryIntentServices(intent, null, 128, 0), this.mSyncAdapterInfos, new RegisteredServicesParser());
  }
  
  public static class SyncAdapterInfo {
    public ComponentName componentName;
    
    public ServiceInfo serviceInfo;
    
    public SyncAdapterType type;
    
    SyncAdapterInfo(SyncAdapterType param1SyncAdapterType, ServiceInfo param1ServiceInfo) {
      this.type = param1SyncAdapterType;
      this.serviceInfo = param1ServiceInfo;
      this.componentName = ComponentUtils.toComponentName((ComponentInfo)param1ServiceInfo);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\content\SyncAdaptersCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */