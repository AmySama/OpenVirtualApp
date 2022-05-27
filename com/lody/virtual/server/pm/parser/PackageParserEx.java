package com.lody.virtual.server.pm.parser;

import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageParser;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.lody.virtual.GmsSupport;
import com.lody.virtual.client.core.SettingConfig;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.env.VirtualRuntime;
import com.lody.virtual.client.fixer.ComponentFixer;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.compat.PackageParserCompat;
import com.lody.virtual.helper.utils.ArrayUtils;
import com.lody.virtual.helper.utils.ComponentUtils;
import com.lody.virtual.helper.utils.FileUtils;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.os.VEnvironment;
import com.lody.virtual.server.pm.ComponentStateManager;
import com.lody.virtual.server.pm.PackageCacheManager;
import com.lody.virtual.server.pm.PackageSetting;
import com.lody.virtual.server.pm.PackageUserState;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import mirror.android.content.pm.ApplicationInfoL;
import mirror.android.content.pm.ApplicationInfoN;
import mirror.android.content.pm.PackageInfoPie;
import mirror.android.content.pm.PackageParser;
import mirror.android.content.pm.SigningInfo;

public class PackageParserEx {
  public static final int GET_SIGNING_CERTIFICATES = 134217728;
  
  public static final String ORG_APACHE_HTTP_LEGACY = "org.apache.http.legacy";
  
  private static final String TAG = PackageParserEx.class.getSimpleName();
  
  private static void addOwner(VPackage paramVPackage) {
    for (VPackage.ActivityComponent activityComponent : paramVPackage.activities) {
      activityComponent.owner = paramVPackage;
      Iterator<VPackage.ActivityIntentInfo> iterator1 = activityComponent.intents.iterator();
      while (iterator1.hasNext())
        ((VPackage.ActivityIntentInfo)iterator1.next()).activity = activityComponent; 
    } 
    for (VPackage.ServiceComponent serviceComponent : paramVPackage.services) {
      serviceComponent.owner = paramVPackage;
      Iterator<VPackage.ServiceIntentInfo> iterator1 = serviceComponent.intents.iterator();
      while (iterator1.hasNext())
        ((VPackage.ServiceIntentInfo)iterator1.next()).service = serviceComponent; 
    } 
    for (VPackage.ActivityComponent activityComponent : paramVPackage.receivers) {
      activityComponent.owner = paramVPackage;
      Iterator<VPackage.ActivityIntentInfo> iterator1 = activityComponent.intents.iterator();
      while (iterator1.hasNext())
        ((VPackage.ActivityIntentInfo)iterator1.next()).activity = activityComponent; 
    } 
    for (VPackage.ProviderComponent providerComponent : paramVPackage.providers) {
      providerComponent.owner = paramVPackage;
      Iterator<VPackage.ProviderIntentInfo> iterator1 = providerComponent.intents.iterator();
      while (iterator1.hasNext())
        ((VPackage.ProviderIntentInfo)iterator1.next()).provider = providerComponent; 
    } 
    Iterator<VPackage.InstrumentationComponent> iterator = paramVPackage.instrumentation.iterator();
    while (iterator.hasNext())
      ((VPackage.InstrumentationComponent)iterator.next()).owner = paramVPackage; 
    iterator = (Iterator)paramVPackage.permissions.iterator();
    while (iterator.hasNext())
      ((VPackage.PermissionComponent)iterator.next()).owner = paramVPackage; 
    iterator = (Iterator)paramVPackage.permissionGroups.iterator();
    while (iterator.hasNext())
      ((VPackage.PermissionGroupComponent)iterator.next()).owner = paramVPackage; 
    byte b = 4;
    if (GmsSupport.isGoogleService(paramVPackage.packageName))
      b = 12; 
    ApplicationInfo applicationInfo = paramVPackage.applicationInfo;
    applicationInfo.flags = b | applicationInfo.flags;
  }
  
  private static VPackage buildPackageCache(PackageParser.Package paramPackage) {
    VPackage vPackage = new VPackage();
    vPackage.activities = new ArrayList<VPackage.ActivityComponent>(paramPackage.activities.size());
    vPackage.services = new ArrayList<VPackage.ServiceComponent>(paramPackage.services.size());
    vPackage.receivers = new ArrayList<VPackage.ActivityComponent>(paramPackage.receivers.size());
    vPackage.providers = new ArrayList<VPackage.ProviderComponent>(paramPackage.providers.size());
    vPackage.instrumentation = new ArrayList<VPackage.InstrumentationComponent>(paramPackage.instrumentation.size());
    vPackage.permissions = new ArrayList<VPackage.PermissionComponent>(paramPackage.permissions.size());
    vPackage.permissionGroups = new ArrayList<VPackage.PermissionGroupComponent>(paramPackage.permissionGroups.size());
    for (PackageParser.Activity activity : paramPackage.activities)
      vPackage.activities.add(new VPackage.ActivityComponent(activity)); 
    for (PackageParser.Service service : paramPackage.services)
      vPackage.services.add(new VPackage.ServiceComponent(service)); 
    for (PackageParser.Activity activity : paramPackage.receivers)
      vPackage.receivers.add(new VPackage.ActivityComponent(activity)); 
    for (PackageParser.Provider provider : paramPackage.providers)
      vPackage.providers.add(new VPackage.ProviderComponent(provider)); 
    for (PackageParser.Instrumentation instrumentation : paramPackage.instrumentation)
      vPackage.instrumentation.add(new VPackage.InstrumentationComponent(instrumentation)); 
    for (PackageParser.Permission permission : paramPackage.permissions)
      vPackage.permissions.add(new VPackage.PermissionComponent(permission)); 
    for (PackageParser.PermissionGroup permissionGroup : paramPackage.permissionGroups)
      vPackage.permissionGroups.add(new VPackage.PermissionGroupComponent(permissionGroup)); 
    vPackage.requestedPermissions = new ArrayList<String>(paramPackage.requestedPermissions.size());
    vPackage.requestedPermissions.addAll(paramPackage.requestedPermissions);
    if (PackageParser.Package.protectedBroadcasts != null) {
      List<? extends String> list = (List)PackageParser.Package.protectedBroadcasts.get(paramPackage);
      if (list != null) {
        vPackage.protectedBroadcasts = new ArrayList<String>(list);
        vPackage.protectedBroadcasts.addAll(list);
      } 
    } 
    vPackage.applicationInfo = paramPackage.applicationInfo;
    if (BuildCompat.isPie()) {
      vPackage.mSigningDetails = paramPackage.mSigningDetails;
      vPackage.mSignatures = paramPackage.mSigningDetails.signatures;
    } else {
      vPackage.mSignatures = paramPackage.mSignatures;
    } 
    vPackage.mAppMetaData = paramPackage.mAppMetaData;
    vPackage.packageName = paramPackage.packageName;
    vPackage.mPreferredOrder = paramPackage.mPreferredOrder;
    vPackage.mVersionName = paramPackage.mVersionName;
    vPackage.mSharedUserId = paramPackage.mSharedUserId;
    vPackage.mSharedUserLabel = paramPackage.mSharedUserLabel;
    vPackage.usesLibraries = paramPackage.usesLibraries;
    vPackage.usesOptionalLibraries = paramPackage.usesOptionalLibraries;
    vPackage.mVersionCode = paramPackage.mVersionCode;
    vPackage.configPreferences = paramPackage.configPreferences;
    vPackage.reqFeatures = paramPackage.reqFeatures;
    addOwner(vPackage);
    updatePackageApache(vPackage);
    return vPackage;
  }
  
  private static void buildSignature(PackageParser.Package paramPackage, Signature[] paramArrayOfSignature) {
    Object object;
    if (BuildCompat.isPie()) {
      object = PackageParser.Package.mSigningDetails.get(paramPackage);
      PackageParser.SigningDetails.pastSigningCertificates.set(object, paramArrayOfSignature);
      PackageParser.SigningDetails.signatures.set(object, paramArrayOfSignature);
    } else {
      ((PackageParser.Package)object).mSignatures = paramArrayOfSignature;
    } 
  }
  
  private static boolean checkUseInstalledOrHidden(PackageUserState paramPackageUserState, int paramInt) {
    boolean bool;
    if ((paramPackageUserState.installed && !paramPackageUserState.hidden) || (paramInt & 0x2000) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static ActivityInfo generateActivityInfo(VPackage.ActivityComponent paramActivityComponent, int paramInt1, PackageUserState paramPackageUserState, int paramInt2, boolean paramBoolean) {
    if (paramActivityComponent == null)
      return null; 
    if (!checkUseInstalledOrHidden(paramPackageUserState, paramInt1))
      return null; 
    ActivityInfo activityInfo = new ActivityInfo(paramActivityComponent.info);
    if ((paramInt1 & 0x80) != 0 && paramActivityComponent.metaData != null)
      activityInfo.metaData = paramActivityComponent.metaData; 
    activityInfo.enabled = isEnabledLPr((ComponentInfo)paramActivityComponent.info, 0, paramInt2);
    activityInfo.applicationInfo = generateApplicationInfo(paramActivityComponent.owner, paramInt1, paramPackageUserState, paramInt2, paramBoolean);
    return activityInfo;
  }
  
  public static ApplicationInfo generateApplicationInfo(VPackage paramVPackage, int paramInt1, PackageUserState paramPackageUserState, int paramInt2, boolean paramBoolean) {
    if (paramVPackage == null)
      return null; 
    if (!checkUseInstalledOrHidden(paramPackageUserState, paramInt1))
      return null; 
    ApplicationInfo applicationInfo = new ApplicationInfo(paramVPackage.applicationInfo);
    if ((paramInt1 & 0x80) != 0)
      applicationInfo.metaData = paramVPackage.mAppMetaData; 
    initApplicationAsUser(applicationInfo, paramInt2, paramBoolean);
    return applicationInfo;
  }
  
  public static InstrumentationInfo generateInstrumentationInfo(VPackage.InstrumentationComponent paramInstrumentationComponent, int paramInt) {
    if (paramInstrumentationComponent == null)
      return null; 
    if ((paramInt & 0x80) == 0)
      return paramInstrumentationComponent.info; 
    InstrumentationInfo instrumentationInfo = new InstrumentationInfo(paramInstrumentationComponent.info);
    instrumentationInfo.metaData = paramInstrumentationComponent.metaData;
    return instrumentationInfo;
  }
  
  public static PackageInfo generatePackageInfo(VPackage paramVPackage, PackageSetting paramPackageSetting, int paramInt1, long paramLong1, long paramLong2, PackageUserState paramPackageUserState, int paramInt2, boolean paramBoolean) {
    if (!checkUseInstalledOrHidden(paramPackageUserState, paramInt1))
      return null; 
    if (paramVPackage.mSignatures == null && paramVPackage.mSigningDetails == null)
      readSignature(paramVPackage); 
    PackageInfo packageInfo = new PackageInfo();
    packageInfo.packageName = paramVPackage.packageName;
    packageInfo.versionCode = paramVPackage.mVersionCode;
    packageInfo.sharedUserLabel = paramVPackage.mSharedUserLabel;
    packageInfo.versionName = paramVPackage.mVersionName;
    packageInfo.sharedUserId = paramVPackage.mSharedUserId;
    packageInfo.applicationInfo = generateApplicationInfo(paramVPackage, paramInt1, paramPackageUserState, paramInt2, paramBoolean);
    packageInfo.firstInstallTime = paramLong1;
    packageInfo.lastUpdateTime = paramLong2;
    if (paramVPackage.requestedPermissions != null && !paramVPackage.requestedPermissions.isEmpty()) {
      String[] arrayOfString = new String[paramVPackage.requestedPermissions.size()];
      paramVPackage.requestedPermissions.toArray(arrayOfString);
      packageInfo.requestedPermissions = arrayOfString;
    } 
    if ((paramInt1 & 0x100) != 0)
      packageInfo.gids = PackageParserCompat.GIDS; 
    if ((paramInt1 & 0x4000) != 0) {
      int i;
      if (paramVPackage.configPreferences != null) {
        i = paramVPackage.configPreferences.size();
      } else {
        i = 0;
      } 
      if (i) {
        packageInfo.configPreferences = new android.content.pm.ConfigurationInfo[i];
        paramVPackage.configPreferences.toArray(packageInfo.configPreferences);
      } 
      if (paramVPackage.reqFeatures != null) {
        i = paramVPackage.reqFeatures.size();
      } else {
        i = 0;
      } 
      if (i > 0) {
        packageInfo.reqFeatures = new android.content.pm.FeatureInfo[i];
        paramVPackage.reqFeatures.toArray(packageInfo.reqFeatures);
      } 
    } 
    if ((paramInt1 & 0x1) != 0) {
      int i = paramVPackage.activities.size();
      if (i > 0) {
        ActivityInfo[] arrayOfActivityInfo = new ActivityInfo[i];
        byte b = 0;
        int j;
        for (j = 0; b < i; j = k) {
          VPackage.ActivityComponent activityComponent = paramVPackage.activities.get(b);
          int k = j;
          if (paramPackageSetting.isEnabledAndMatchLPr((ComponentInfo)activityComponent.info, paramInt1, paramInt2)) {
            arrayOfActivityInfo[j] = generateActivityInfo(activityComponent, paramInt1, paramPackageUserState, paramInt2, paramBoolean);
            k = j + 1;
          } 
          b++;
        } 
        packageInfo.activities = (ActivityInfo[])ArrayUtils.trimToSize((Object[])arrayOfActivityInfo, j);
      } 
    } 
    if ((paramInt1 & 0x2) != 0) {
      int i = paramVPackage.receivers.size();
      if (i > 0) {
        ActivityInfo[] arrayOfActivityInfo = new ActivityInfo[i];
        byte b = 0;
        int j;
        for (j = 0; b < i; j = k) {
          VPackage.ActivityComponent activityComponent = paramVPackage.receivers.get(b);
          int k = j;
          if (paramPackageSetting.isEnabledAndMatchLPr((ComponentInfo)activityComponent.info, paramInt1, paramInt2)) {
            arrayOfActivityInfo[j] = generateActivityInfo(activityComponent, paramInt1, paramPackageUserState, paramInt2, paramBoolean);
            k = j + 1;
          } 
          b++;
        } 
        packageInfo.receivers = (ActivityInfo[])ArrayUtils.trimToSize((Object[])arrayOfActivityInfo, j);
      } 
    } 
    if ((paramInt1 & 0x4) != 0) {
      int i = paramVPackage.services.size();
      if (i > 0) {
        ServiceInfo[] arrayOfServiceInfo = new ServiceInfo[i];
        byte b = 0;
        int j;
        for (j = 0; b < i; j = k) {
          VPackage.ServiceComponent serviceComponent = paramVPackage.services.get(b);
          int k = j;
          if (paramPackageSetting.isEnabledAndMatchLPr((ComponentInfo)serviceComponent.info, paramInt1, paramInt2)) {
            arrayOfServiceInfo[j] = generateServiceInfo(serviceComponent, paramInt1, paramPackageUserState, paramInt2, paramBoolean);
            k = j + 1;
          } 
          b++;
        } 
        packageInfo.services = (ServiceInfo[])ArrayUtils.trimToSize((Object[])arrayOfServiceInfo, j);
      } 
    } 
    if ((paramInt1 & 0x8) != 0) {
      int i = paramVPackage.providers.size();
      if (i > 0) {
        ProviderInfo[] arrayOfProviderInfo = new ProviderInfo[i];
        byte b = 0;
        int j;
        for (j = 0; b < i; j = k) {
          VPackage.ProviderComponent providerComponent = paramVPackage.providers.get(b);
          int k = j;
          if (paramPackageSetting.isEnabledAndMatchLPr((ComponentInfo)providerComponent.info, paramInt1, paramInt2)) {
            arrayOfProviderInfo[j] = generateProviderInfo(providerComponent, paramInt1, paramPackageUserState, paramInt2, paramBoolean);
            k = j + 1;
          } 
          b++;
        } 
        packageInfo.providers = (ProviderInfo[])ArrayUtils.trimToSize((Object[])arrayOfProviderInfo, j);
      } 
    } 
    if ((paramInt1 & 0x10) != 0) {
      int i = paramVPackage.instrumentation.size();
      if (i > 0) {
        packageInfo.instrumentation = new InstrumentationInfo[i];
        for (paramInt2 = 0; paramInt2 < i; paramInt2++)
          packageInfo.instrumentation[paramInt2] = generateInstrumentationInfo(paramVPackage.instrumentation.get(paramInt2), paramInt1); 
      } 
    } 
    if ((paramInt1 & 0x1000) != 0) {
      int i = paramVPackage.permissions.size();
      if (i > 0) {
        packageInfo.permissions = new PermissionInfo[i];
        for (paramInt2 = 0; paramInt2 < i; paramInt2++)
          packageInfo.permissions[paramInt2] = generatePermissionInfo(paramVPackage.permissions.get(paramInt2), paramInt1); 
      } 
      if (paramVPackage.requestedPermissions == null) {
        paramInt2 = 0;
      } else {
        paramInt2 = paramVPackage.requestedPermissions.size();
      } 
      if (paramInt2 > 0) {
        packageInfo.requestedPermissions = new String[paramInt2];
        for (i = 0; i < paramInt2; i++) {
          String str = paramVPackage.requestedPermissions.get(i);
          packageInfo.requestedPermissions[i] = str;
        } 
      } 
    } 
    if ((paramInt1 & 0x40) != 0) {
      if (paramVPackage.mSignatures != null) {
        paramInt2 = paramVPackage.mSignatures.length;
      } else {
        paramInt2 = 0;
      } 
      if (paramInt2 > 0) {
        packageInfo.signatures = new Signature[paramInt2];
        System.arraycopy(paramVPackage.mSignatures, 0, packageInfo.signatures, 0, paramInt2);
      } else {
        try {
          packageInfo.signatures = (VirtualCore.get().getHostPackageManager().getPackageInfo(paramVPackage.packageName, 64)).signatures;
        } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
          nameNotFoundException.printStackTrace();
        } 
      } 
    } 
    if (BuildCompat.isPie() && (0x8000000 & paramInt1) != 0)
      if (paramVPackage.mSigningDetails != null) {
        PackageInfoPie.signingInfo.set(packageInfo, SigningInfo.ctor.newInstance(new Object[] { paramVPackage.mSigningDetails }));
        packageInfo.signatures = paramVPackage.mSigningDetails.signatures;
      } else if (paramVPackage.mSignatures != null) {
        PackageParser.SigningDetails signingDetails = new PackageParser.SigningDetails();
        PackageParser.SigningDetails.pastSigningCertificates.set(signingDetails, paramVPackage.mSignatures);
        PackageParser.SigningDetails.signatures.set(signingDetails, paramVPackage.mSignatures);
        PackageInfoPie.signingInfo.set(packageInfo, SigningInfo.ctor.newInstance(new Object[] { signingDetails }));
        packageInfo.signatures = paramVPackage.mSigningDetails.signatures;
      }  
    return packageInfo;
  }
  
  public static PermissionGroupInfo generatePermissionGroupInfo(VPackage.PermissionGroupComponent paramPermissionGroupComponent, int paramInt) {
    if (paramPermissionGroupComponent == null)
      return null; 
    if ((paramInt & 0x80) == 0)
      return paramPermissionGroupComponent.info; 
    PermissionGroupInfo permissionGroupInfo = new PermissionGroupInfo(paramPermissionGroupComponent.info);
    permissionGroupInfo.metaData = paramPermissionGroupComponent.metaData;
    return permissionGroupInfo;
  }
  
  public static PermissionInfo generatePermissionInfo(VPackage.PermissionComponent paramPermissionComponent, int paramInt) {
    if (paramPermissionComponent == null)
      return null; 
    if ((paramInt & 0x80) == 0)
      return paramPermissionComponent.info; 
    PermissionInfo permissionInfo = new PermissionInfo(paramPermissionComponent.info);
    permissionInfo.metaData = paramPermissionComponent.metaData;
    return permissionInfo;
  }
  
  public static ProviderInfo generateProviderInfo(VPackage.ProviderComponent paramProviderComponent, int paramInt1, PackageUserState paramPackageUserState, int paramInt2, boolean paramBoolean) {
    if (paramProviderComponent == null)
      return null; 
    if (!checkUseInstalledOrHidden(paramPackageUserState, paramInt1))
      return null; 
    ProviderInfo providerInfo = new ProviderInfo(paramProviderComponent.info);
    if ((paramInt1 & 0x80) != 0 && paramProviderComponent.metaData != null)
      providerInfo.metaData = paramProviderComponent.metaData; 
    if ((paramInt1 & 0x800) == 0)
      providerInfo.uriPermissionPatterns = null; 
    providerInfo.enabled = isEnabledLPr((ComponentInfo)paramProviderComponent.info, 0, paramInt2);
    providerInfo.applicationInfo = generateApplicationInfo(paramProviderComponent.owner, paramInt1, paramPackageUserState, paramInt2, paramBoolean);
    return providerInfo;
  }
  
  public static ServiceInfo generateServiceInfo(VPackage.ServiceComponent paramServiceComponent, int paramInt1, PackageUserState paramPackageUserState, int paramInt2, boolean paramBoolean) {
    if (paramServiceComponent == null)
      return null; 
    if (!checkUseInstalledOrHidden(paramPackageUserState, paramInt1))
      return null; 
    ServiceInfo serviceInfo = new ServiceInfo(paramServiceComponent.info);
    if ((paramInt1 & 0x80) != 0 && paramServiceComponent.metaData != null)
      serviceInfo.metaData = paramServiceComponent.metaData; 
    serviceInfo.enabled = isEnabledLPr((ComponentInfo)paramServiceComponent.info, 0, paramInt2);
    serviceInfo.applicationInfo = generateApplicationInfo(paramServiceComponent.owner, paramInt1, paramPackageUserState, paramInt2, paramBoolean);
    return serviceInfo;
  }
  
  private static void initApplicationAsUser(ApplicationInfo paramApplicationInfo, int paramInt, boolean paramBoolean) {
    PackageSetting packageSetting = PackageCacheManager.getSetting(paramApplicationInfo.packageName);
    VPackage vPackage = PackageCacheManager.get(paramApplicationInfo.packageName);
    if (packageSetting != null) {
      String str;
      SettingConfig settingConfig = VirtualCore.getConfig();
      if (paramBoolean && !packageSetting.dynamic) {
        paramApplicationInfo.sourceDir = VEnvironment.getPackageFileExt(paramApplicationInfo.packageName).getPath();
        paramApplicationInfo.publicSourceDir = paramApplicationInfo.sourceDir;
        File file = VEnvironment.getDataAppLibDirectoryExt(paramApplicationInfo.packageName);
        paramApplicationInfo.nativeLibraryDir = (new File(file, VirtualRuntime.getInstructionSet(packageSetting.primaryCpuAbi))).getPath();
        if (packageSetting.secondaryCpuAbi != null) {
          file = new File(file, VirtualRuntime.getInstructionSet(packageSetting.secondaryCpuAbi));
          ApplicationInfoL.secondaryNativeLibraryDir.set(paramApplicationInfo, file.getPath());
        } 
        String str1 = VEnvironment.getDataAppPackageDirectoryExt(paramApplicationInfo.packageName).getAbsolutePath();
        ApplicationInfoL.scanSourceDir.set(paramApplicationInfo, str1);
        ApplicationInfoL.scanPublicSourceDir.set(paramApplicationInfo, str1);
      } 
      if (!packageSetting.dynamic && vPackage.splitNames != null && !vPackage.splitNames.isEmpty()) {
        if (Build.VERSION.SDK_INT >= 26)
          paramApplicationInfo.splitNames = vPackage.splitNames.<String>toArray(new String[0]); 
        ArrayList<String> arrayList = new ArrayList();
        for (String str1 : vPackage.splitNames) {
          File file;
          if (paramBoolean) {
            file = VEnvironment.getSplitPackageFileExt(paramApplicationInfo.packageName, str1);
          } else {
            file = VEnvironment.getSplitPackageFile(paramApplicationInfo.packageName, (String)file);
          } 
          arrayList.add(file.getPath());
        } 
        vPackage.applicationInfo.splitSourceDirs = arrayList.<String>toArray(new String[0]);
        vPackage.applicationInfo.splitPublicSourceDirs = arrayList.<String>toArray(new String[0]);
      } 
      if (paramBoolean) {
        str = VEnvironment.getDataUserPackageDirectoryExt(paramInt, paramApplicationInfo.packageName).getPath();
      } else {
        str = VEnvironment.getDataUserPackageDirectory(paramInt, paramApplicationInfo.packageName).getPath();
      } 
      paramApplicationInfo.dataDir = str;
      if (Build.VERSION.SDK_INT >= 24) {
        if (paramBoolean) {
          str = VEnvironment.getDeDataUserPackageDirectoryExt(paramInt, paramApplicationInfo.packageName).getPath();
        } else {
          str = VEnvironment.getDeDataUserPackageDirectory(paramInt, paramApplicationInfo.packageName).getPath();
        } 
        if (ApplicationInfoN.deviceEncryptedDataDir != null)
          ApplicationInfoN.deviceEncryptedDataDir.set(paramApplicationInfo, str); 
        if (ApplicationInfoN.credentialEncryptedDataDir != null)
          ApplicationInfoN.credentialEncryptedDataDir.set(paramApplicationInfo, paramApplicationInfo.dataDir); 
        if (ApplicationInfoN.deviceProtectedDataDir != null)
          ApplicationInfoN.deviceProtectedDataDir.set(paramApplicationInfo, str); 
        if (ApplicationInfoN.credentialProtectedDataDir != null)
          ApplicationInfoN.credentialProtectedDataDir.set(paramApplicationInfo, paramApplicationInfo.dataDir); 
      } 
      if (settingConfig.isEnableIORedirect() && settingConfig.isUseRealDataDir(paramApplicationInfo.packageName)) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("/data/data/");
        stringBuilder1.append(paramApplicationInfo.packageName);
        stringBuilder1.append("/");
        paramApplicationInfo.dataDir = stringBuilder1.toString();
      } 
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("failed to getSetting for:");
    stringBuilder.append(paramApplicationInfo.packageName);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public static void initApplicationInfoBase(PackageSetting paramPackageSetting, VPackage paramVPackage) {
    ApplicationInfo applicationInfo = paramVPackage.applicationInfo;
    if (TextUtils.isEmpty(applicationInfo.processName))
      applicationInfo.processName = applicationInfo.packageName; 
    applicationInfo.enabled = true;
    applicationInfo.uid = paramPackageSetting.appId;
    applicationInfo.name = ComponentFixer.fixComponentClassName(paramPackageSetting.packageName, applicationInfo.name);
  }
  
  public static boolean isEnabledLPr(ComponentInfo paramComponentInfo, int paramInt1, int paramInt2) {
    ComponentName componentName = ComponentUtils.toComponentName(paramComponentInfo);
    paramInt1 = ComponentStateManager.user(paramInt2).get(componentName);
    if (paramInt1 == 0)
      return paramComponentInfo.enabled; 
    if (paramInt1 == 2)
      return false; 
    if (paramInt1 == 4)
      return false; 
    if (paramInt1 == 3)
      return false; 
    if (paramInt1 == 1);
    return true;
  }
  
  private static boolean isLibraryPresent(List<String> paramList1, List<String> paramList2, String paramString) {
    if (paramList1 != null) {
      Iterator<String> iterator = paramList1.iterator();
      while (iterator.hasNext()) {
        if (((String)iterator.next()).equals(paramString))
          return true; 
      } 
    } 
    if (paramList2 != null) {
      Iterator<String> iterator = paramList2.iterator();
      while (iterator.hasNext()) {
        if (((String)iterator.next()).equals(paramString))
          return true; 
      } 
    } 
    return false;
  }
  
  public static VPackage parsePackage(File paramFile) throws Throwable {
    StringBuilder stringBuilder;
    PackageParser packageParser = PackageParserCompat.createParser(paramFile);
    if (BuildCompat.isQ())
      packageParser.setCallback((PackageParser.Callback)new PackageParser.CallbackImpl(VirtualCore.getPM())); 
    PackageParser.Package package_ = PackageParserCompat.parsePackage(packageParser, paramFile, 0);
    boolean bool = package_.requestedPermissions.contains("android.permission.FAKE_PACKAGE_SIGNATURE");
    byte b = 1;
    if (bool && package_.mAppMetaData != null && package_.mAppMetaData.containsKey("fake-signature")) {
      buildSignature(package_, new Signature[] { new Signature(package_.mAppMetaData.getString("fake-signature")) });
      String str = TAG;
      stringBuilder = new StringBuilder();
      stringBuilder.append("Using fake-signature feature on : ");
      stringBuilder.append(package_.packageName);
      VLog.d(str, stringBuilder.toString(), new Object[0]);
    } else {
      try {
        if (BuildCompat.isPie())
          b = 16; 
      } finally {
        stringBuilder = null;
      } 
    } 
    return buildPackageCache(package_);
  }
  
  public static VPackage readPackageCache(String paramString) {
    Parcel parcel = Parcel.obtain();
    try {
      File file = VEnvironment.getPackageCacheFile(paramString);
      FileInputStream fileInputStream = new FileInputStream();
      this(file);
      byte[] arrayOfByte = FileUtils.toByteArray(fileInputStream);
      fileInputStream.close();
      parcel.unmarshall(arrayOfByte, 0, arrayOfByte.length);
      parcel.setDataPosition(0);
      if (parcel.readInt() == 4) {
        VPackage vPackage = new VPackage();
        this(parcel);
        addOwner(vPackage);
        parcel.recycle();
        return vPackage;
      } 
      IllegalStateException illegalStateException = new IllegalStateException();
      this("Invalid version.");
      throw illegalStateException;
    } catch (Exception exception) {
      exception.printStackTrace();
      parcel.recycle();
      return null;
    } finally {}
    parcel.recycle();
    throw paramString;
  }
  
  public static void readSignature(VPackage paramVPackage) {
    File file = VEnvironment.getSignatureFile(paramVPackage.packageName);
    if (!file.exists())
      return; 
    Parcel parcel = Parcel.obtain();
    try {
      FileInputStream fileInputStream = new FileInputStream();
      this(file);
      byte[] arrayOfByte = FileUtils.toByteArray(fileInputStream);
      fileInputStream.close();
      parcel.unmarshall(arrayOfByte, 0, arrayOfByte.length);
      parcel.setDataPosition(0);
      boolean bool = BuildCompat.isPie();
      if (bool)
        try {
          PackageParser.SigningDetails signingDetails = (PackageParser.SigningDetails)((Parcelable.Creator)PackageParser.SigningDetails.CREATOR.get()).createFromParcel(parcel);
          paramVPackage.mSigningDetails = signingDetails;
          paramVPackage.mSignatures = signingDetails.signatures;
        } catch (Exception exception) {
          exception.printStackTrace();
        }  
      if (paramVPackage.mSigningDetails == null || paramVPackage.mSignatures == null) {
        parcel.setDataPosition(0);
        paramVPackage.mSignatures = (Signature[])parcel.createTypedArray(Signature.CREATOR);
        paramVPackage.mSigningDetails = null;
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {}
    parcel.recycle();
  }
  
  public static void savePackageCache(VPackage paramVPackage) {
    PackageParser.SigningDetails signingDetails1;
    String str = paramVPackage.packageName;
    File file1 = VEnvironment.getPackageCacheFile(str);
    if (file1.exists())
      file1.delete(); 
    File file2 = VEnvironment.getSignatureFile(str);
    if (file2.exists())
      file2.delete(); 
    Parcel parcel = Parcel.obtain();
    try {
      parcel.writeInt(4);
      paramVPackage.writeToParcel(parcel, 0);
      FileOutputStream fileOutputStream = new FileOutputStream();
      this(file1);
      fileOutputStream.write(parcel.marshall());
      fileOutputStream.close();
    } catch (Exception exception) {
      exception.printStackTrace();
    } finally {}
    parcel.recycle();
    Signature[] arrayOfSignature = paramVPackage.mSignatures;
    PackageParser.SigningDetails signingDetails2 = paramVPackage.mSigningDetails;
    if (signingDetails2 == null) {
      Signature[] arrayOfSignature1 = arrayOfSignature;
    } else {
      signingDetails1 = signingDetails2;
    } 
    if (signingDetails1 != null) {
      if (file2.exists() && !file2.delete()) {
        String str1 = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unable to delete the signatures of ");
        stringBuilder.append(str);
        VLog.w(str1, stringBuilder.toString(), new Object[0]);
      } 
      Parcel parcel1 = Parcel.obtain();
      try {
        if (signingDetails1 instanceof PackageParser.SigningDetails) {
          PackageParser.SigningDetails.writeToParcel.call(signingDetails2, new Object[] { parcel1, Integer.valueOf(0) });
        } else {
          parcel1.writeTypedArray((Parcelable[])arrayOfSignature, 0);
        } 
        FileUtils.writeParcelToFile(parcel1, file2);
      } catch (IOException iOException) {
        iOException.printStackTrace();
      } finally {}
      parcel1.recycle();
    } 
  }
  
  private static void updatePackageApache(VPackage paramVPackage) {
    if (paramVPackage.usesLibraries == null)
      paramVPackage.usesLibraries = new ArrayList<String>(); 
    if (paramVPackage.usesOptionalLibraries == null)
      paramVPackage.usesOptionalLibraries = new ArrayList<String>(); 
    if (paramVPackage.applicationInfo != null && paramVPackage.applicationInfo.targetSdkVersion < 28 && !isLibraryPresent(paramVPackage.usesLibraries, paramVPackage.usesOptionalLibraries, "org.apache.http.legacy"))
      paramVPackage.usesLibraries.add(0, "org.apache.http.legacy"); 
    if (paramVPackage.applicationInfo != null && !isLibraryPresent(paramVPackage.usesLibraries, paramVPackage.usesOptionalLibraries, "android.test.base"))
      paramVPackage.usesLibraries.add(0, "android.test.base"); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\pm\parser\PackageParserEx.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */