package com.lody.virtual.server.pm;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import com.lody.virtual.helper.compat.ObjectsCompat;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.server.pm.parser.PackageParserEx;
import com.lody.virtual.server.pm.parser.VPackage;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

final class ProviderIntentResolver extends IntentResolver<VPackage.ProviderIntentInfo, ResolveInfo> {
  private int mFlags;
  
  private final HashMap<ComponentName, VPackage.ProviderComponent> mProviders = new HashMap<ComponentName, VPackage.ProviderComponent>();
  
  public final void addProvider(VPackage.ProviderComponent paramProviderComponent) {
    boolean bool = this.mProviders.containsKey(paramProviderComponent.getComponentName());
    byte b = 0;
    if (bool) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Provider ");
      stringBuilder.append(paramProviderComponent.getComponentName());
      stringBuilder.append(" already defined; ignoring");
      VLog.w("PackageManager", stringBuilder.toString(), new Object[0]);
      return;
    } 
    this.mProviders.put(paramProviderComponent.getComponentName(), paramProviderComponent);
    int i = paramProviderComponent.intents.size();
    while (b < i) {
      addFilter(paramProviderComponent.intents.get(b));
      b++;
    } 
  }
  
  protected boolean allowFilterResult(VPackage.ProviderIntentInfo paramProviderIntentInfo, List<ResolveInfo> paramList) {
    ProviderInfo providerInfo = paramProviderIntentInfo.provider.info;
    for (int i = paramList.size() - 1; i >= 0; i--) {
      ProviderInfo providerInfo1 = ((ResolveInfo)paramList.get(i)).providerInfo;
      if (ObjectsCompat.equals(providerInfo1.name, providerInfo.name) && ObjectsCompat.equals(providerInfo1.packageName, providerInfo.packageName))
        return false; 
    } 
    return true;
  }
  
  protected void dumpFilter(PrintWriter paramPrintWriter, String paramString, VPackage.ProviderIntentInfo paramProviderIntentInfo) {}
  
  protected void dumpFilterLabel(PrintWriter paramPrintWriter, String paramString, Object paramObject, int paramInt) {}
  
  protected Object filterToLabel(VPackage.ProviderIntentInfo paramProviderIntentInfo) {
    return paramProviderIntentInfo.provider;
  }
  
  protected boolean isFilterStopped(VPackage.ProviderIntentInfo paramProviderIntentInfo) {
    return false;
  }
  
  protected boolean isPackageForFilter(String paramString, VPackage.ProviderIntentInfo paramProviderIntentInfo) {
    return paramString.equals(paramProviderIntentInfo.provider.owner.packageName);
  }
  
  protected VPackage.ProviderIntentInfo[] newArray(int paramInt) {
    return new VPackage.ProviderIntentInfo[paramInt];
  }
  
  protected ResolveInfo newResult(VPackage.ProviderIntentInfo paramProviderIntentInfo, int paramInt1, int paramInt2) {
    VPackage.ProviderComponent providerComponent = paramProviderIntentInfo.provider;
    if (!PackageParserEx.isEnabledLPr((ComponentInfo)providerComponent.info, this.mFlags, paramInt2))
      return null; 
    PackageSetting packageSetting = (PackageSetting)providerComponent.owner.mExtras;
    ProviderInfo providerInfo = PackageParserEx.generateProviderInfo(providerComponent, this.mFlags, packageSetting.readUserState(paramInt2), paramInt2, packageSetting.isRunInExtProcess());
    if (providerInfo == null)
      return null; 
    ResolveInfo resolveInfo = new ResolveInfo();
    resolveInfo.providerInfo = providerInfo;
    if ((this.mFlags & 0x40) != 0)
      resolveInfo.filter = paramProviderIntentInfo.filter; 
    resolveInfo.priority = paramProviderIntentInfo.filter.getPriority();
    resolveInfo.preferredOrder = providerComponent.owner.mPreferredOrder;
    resolveInfo.match = paramInt1;
    resolveInfo.isDefault = paramProviderIntentInfo.hasDefault;
    resolveInfo.labelRes = paramProviderIntentInfo.labelRes;
    resolveInfo.nonLocalizedLabel = paramProviderIntentInfo.nonLocalizedLabel;
    resolveInfo.icon = paramProviderIntentInfo.icon;
    return resolveInfo;
  }
  
  public List<ResolveInfo> queryIntent(Intent paramIntent, String paramString, int paramInt1, int paramInt2) {
    boolean bool;
    this.mFlags = paramInt1;
    if ((paramInt1 & 0x10000) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return super.queryIntent(paramIntent, paramString, bool, paramInt2);
  }
  
  public List<ResolveInfo> queryIntent(Intent paramIntent, String paramString, boolean paramBoolean, int paramInt) {
    boolean bool;
    if (paramBoolean) {
      bool = true;
    } else {
      bool = false;
    } 
    this.mFlags = bool;
    return super.queryIntent(paramIntent, paramString, paramBoolean, paramInt);
  }
  
  public List<ResolveInfo> queryIntentForPackage(Intent paramIntent, String paramString, int paramInt1, ArrayList<VPackage.ProviderComponent> paramArrayList, int paramInt2) {
    boolean bool2;
    if (paramArrayList == null)
      return null; 
    this.mFlags = paramInt1;
    boolean bool1 = false;
    if ((paramInt1 & 0x10000) != 0) {
      bool2 = true;
    } else {
      bool2 = false;
    } 
    int i = paramArrayList.size();
    ArrayList<VPackage.ProviderIntentInfo[]> arrayList = new ArrayList(i);
    for (paramInt1 = bool1; paramInt1 < i; paramInt1++) {
      ArrayList arrayList1 = ((VPackage.ProviderComponent)paramArrayList.get(paramInt1)).intents;
      if (arrayList1 != null && arrayList1.size() > 0) {
        VPackage.ProviderIntentInfo[] arrayOfProviderIntentInfo = new VPackage.ProviderIntentInfo[arrayList1.size()];
        arrayList1.toArray((Object[])arrayOfProviderIntentInfo);
        arrayList.add(arrayOfProviderIntentInfo);
      } 
    } 
    return (List)queryIntentFromList(paramIntent, paramString, bool2, (ArrayList)arrayList, paramInt2);
  }
  
  public final void removeProvider(VPackage.ProviderComponent paramProviderComponent) {
    this.mProviders.remove(paramProviderComponent.getComponentName());
    int i = paramProviderComponent.intents.size();
    for (byte b = 0; b < i; b++)
      removeFilter(paramProviderComponent.intents.get(b)); 
  }
  
  protected void sortResults(List<ResolveInfo> paramList) {
    Collections.sort(paramList, VPackageManagerService.sResolvePrioritySorter);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\pm\ProviderIntentResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */