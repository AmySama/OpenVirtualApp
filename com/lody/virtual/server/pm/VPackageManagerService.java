package com.lody.virtual.server.pm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.fixer.ComponentFixer;
import com.lody.virtual.client.stub.StubManifest;
import com.lody.virtual.helper.compat.ObjectsCompat;
import com.lody.virtual.helper.compat.PermissionCompat;
import com.lody.virtual.helper.utils.SignaturesUtils;
import com.lody.virtual.helper.utils.Singleton;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.os.VUserHandle;
import com.lody.virtual.remote.ReceiverInfo;
import com.lody.virtual.remote.VParceledListSlice;
import com.lody.virtual.server.interfaces.IPackageManager;
import com.lody.virtual.server.pm.installer.VPackageInstallerService;
import com.lody.virtual.server.pm.parser.PackageParserEx;
import com.lody.virtual.server.pm.parser.VPackage;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class VPackageManagerService extends IPackageManager.Stub {
  static final String TAG = "PackageManager";
  
  private static final Singleton<VPackageManagerService> gService;
  
  private static final Comparator<ProviderInfo> sProviderInitOrderSorter;
  
  static final Comparator<ResolveInfo> sResolvePrioritySorter = new Comparator<ResolveInfo>() {
      public int compare(ResolveInfo param1ResolveInfo1, ResolveInfo param1ResolveInfo2) {
        int i = param1ResolveInfo1.priority;
        int j = param1ResolveInfo2.priority;
        byte b = -1;
        if (i != j) {
          if (i <= j)
            b = 1; 
          return b;
        } 
        i = param1ResolveInfo1.preferredOrder;
        j = param1ResolveInfo2.preferredOrder;
        if (i != j) {
          if (i <= j)
            b = 1; 
          return b;
        } 
        if (param1ResolveInfo1.isDefault != param1ResolveInfo2.isDefault) {
          if (!param1ResolveInfo1.isDefault)
            b = 1; 
          return b;
        } 
        i = param1ResolveInfo1.match;
        j = param1ResolveInfo2.match;
        if (i != j) {
          if (i <= j)
            b = 1; 
          return b;
        } 
        return 0;
      }
    };
  
  private final ActivityIntentResolver mActivities = new ActivityIntentResolver();
  
  private final Map<String, String[]> mDangerousPermissions = (Map)new HashMap<String, String>();
  
  private final Map<String, VPackage> mPackages = (Map<String, VPackage>)PackageCacheManager.PACKAGE_CACHE;
  
  private final HashMap<String, VPackage.PermissionGroupComponent> mPermissionGroups = new HashMap<String, VPackage.PermissionGroupComponent>();
  
  private final HashMap<String, VPackage.PermissionComponent> mPermissions = new HashMap<String, VPackage.PermissionComponent>();
  
  private final ProviderIntentResolver mProviders = new ProviderIntentResolver();
  
  private final HashMap<String, VPackage.ProviderComponent> mProvidersByAuthority = new HashMap<String, VPackage.ProviderComponent>();
  
  private final HashMap<ComponentName, VPackage.ProviderComponent> mProvidersByComponent = new HashMap<ComponentName, VPackage.ProviderComponent>();
  
  private final ActivityIntentResolver mReceivers = new ActivityIntentResolver();
  
  private final ServiceIntentResolver mServices = new ServiceIntentResolver();
  
  static {
    gService = new Singleton<VPackageManagerService>() {
        protected VPackageManagerService create() {
          return new VPackageManagerService();
        }
      };
    sProviderInitOrderSorter = new Comparator<ProviderInfo>() {
        public int compare(ProviderInfo param1ProviderInfo1, ProviderInfo param1ProviderInfo2) {
          int i = param1ProviderInfo1.initOrder;
          return Integer.compare(param1ProviderInfo2.initOrder, i);
        }
      };
  }
  
  private VPackageManagerService() {}
  
  private void checkUserId(int paramInt) {
    if (VUserManagerService.get().exists(paramInt))
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Invalid userId ");
    stringBuilder.append(paramInt);
    throw new SecurityException(stringBuilder.toString());
  }
  
  private ResolveInfo chooseBestActivity(Intent paramIntent, String paramString, int paramInt, List<ResolveInfo> paramList) {
    if (paramList != null) {
      int i = paramList.size();
      if (i == 1)
        return paramList.get(0); 
      if (i > 1) {
        ResolveInfo resolveInfo2 = paramList.get(0);
        ResolveInfo resolveInfo3 = paramList.get(1);
        if (resolveInfo2.priority != resolveInfo3.priority || resolveInfo2.preferredOrder != resolveInfo3.preferredOrder || resolveInfo2.isDefault != resolveInfo3.isDefault)
          return paramList.get(0); 
        ResolveInfo resolveInfo1 = findPreferredActivity(paramIntent, paramString, paramInt, paramList, resolveInfo2.priority);
        return (resolveInfo1 != null) ? resolveInfo1 : paramList.get(0);
      } 
    } 
    return null;
  }
  
  private PermissionInfo findPermission(String paramString) {
    synchronized (this.mPackages) {
      Iterator iterator = this.mPackages.values().iterator();
      while (iterator.hasNext()) {
        ArrayList arrayList = ((VPackage)iterator.next()).permissions;
        if (arrayList != null)
          for (VPackage.PermissionComponent permissionComponent : arrayList) {
            if (permissionComponent.info != null && TextUtils.equals(paramString, permissionComponent.info.name))
              return permissionComponent.info; 
          }  
      } 
      return null;
    } 
  }
  
  private ResolveInfo findPreferredActivity(Intent paramIntent, String paramString, int paramInt1, List<ResolveInfo> paramList, int paramInt2) {
    return null;
  }
  
  private PackageInfo generatePackageInfo(VPackage paramVPackage, PackageSetting paramPackageSetting, int paramInt1, int paramInt2) {
    return PackageParserEx.generatePackageInfo(paramVPackage, paramPackageSetting, updateFlagsNought(paramInt1), paramPackageSetting.firstInstallTime, paramPackageSetting.lastUpdateTime, paramPackageSetting.readUserState(paramInt2), paramInt2, paramPackageSetting.isRunInExtProcess());
  }
  
  public static VPackageManagerService get() {
    return (VPackageManagerService)gService.get();
  }
  
  private boolean hasRequestedPermission(String paramString1, String paramString2) {
    synchronized (this.mPackages) {
      VPackage vPackage = this.mPackages.get(paramString2);
      return (vPackage != null && vPackage.requestedPermissions != null) ? vPackage.requestedPermissions.contains(paramString1) : false;
    } 
  }
  
  public static void systemReady() {
    Context context = VirtualCore.get().getContext();
    VPackageManagerService vPackageManagerService = get();
    Map<String, VPackage> map = (get()).mPackages;
    new VUserManagerService(context, vPackageManagerService, new char[0], map);
  }
  
  private int updateFlagsNought(int paramInt) {
    if (Build.VERSION.SDK_INT < 24)
      return paramInt; 
    if ((paramInt & 0xC0000) == 0)
      paramInt |= 0xC0000; 
    return paramInt;
  }
  
  public boolean activitySupportsIntent(ComponentName paramComponentName, Intent paramIntent, String paramString) {
    synchronized (this.mPackages) {
      VPackage.ActivityComponent activityComponent = (VPackage.ActivityComponent)this.mActivities.mActivities.get(paramComponentName);
      if (activityComponent == null)
        return false; 
      for (byte b = 0; b < activityComponent.intents.size(); b++) {
        if (((VPackage.ActivityIntentInfo)activityComponent.intents.get(b)).filter.match(paramIntent.getAction(), paramString, paramIntent.getScheme(), paramIntent.getData(), paramIntent.getCategories(), "PackageManager") >= 0)
          return true; 
      } 
      return false;
    } 
  }
  
  void analyzePackageLocked(VPackage paramVPackage) {
    int i = paramVPackage.activities.size();
    boolean bool = false;
    byte b;
    for (b = 0; b < i; b++) {
      VPackage.ActivityComponent activityComponent = paramVPackage.activities.get(b);
      if (activityComponent.info.processName == null)
        activityComponent.info.processName = activityComponent.info.packageName; 
      this.mActivities.addActivity(activityComponent, "activity");
    } 
    i = paramVPackage.services.size();
    for (b = 0; b < i; b++) {
      VPackage.ServiceComponent serviceComponent = paramVPackage.services.get(b);
      if (serviceComponent.info.processName == null)
        serviceComponent.info.processName = serviceComponent.info.packageName; 
      this.mServices.addService(serviceComponent);
    } 
    i = paramVPackage.receivers.size();
    for (b = 0; b < i; b++) {
      VPackage.ActivityComponent activityComponent = paramVPackage.receivers.get(b);
      if (activityComponent.info.processName == null)
        activityComponent.info.processName = activityComponent.info.packageName; 
      this.mReceivers.addActivity(activityComponent, "receiver");
    } 
    int j = paramVPackage.providers.size();
    b = 0;
    while (b < j) {
      VPackage.ProviderComponent providerComponent = paramVPackage.providers.get(b);
      if (providerComponent.info.processName == null)
        providerComponent.info.processName = providerComponent.info.packageName; 
      this.mProviders.addProvider(providerComponent);
      String[] arrayOfString = providerComponent.info.authority.split(";");
      synchronized (this.mProvidersByAuthority) {
        int k = arrayOfString.length;
        for (i = 0; i < k; i++) {
          String str = arrayOfString[i];
          if (!this.mProvidersByAuthority.containsKey(str))
            this.mProvidersByAuthority.put(str, providerComponent); 
        } 
        this.mProvidersByComponent.put(providerComponent.getComponentName(), providerComponent);
        b++;
      } 
    } 
    i = paramVPackage.permissions.size();
    for (b = 0; b < i; b++) {
      VPackage.PermissionComponent permissionComponent = paramVPackage.permissions.get(b);
      this.mPermissions.put(permissionComponent.info.name, permissionComponent);
    } 
    i = paramVPackage.permissionGroups.size();
    for (b = bool; b < i; b++) {
      VPackage.PermissionGroupComponent permissionGroupComponent = paramVPackage.permissionGroups.get(b);
      this.mPermissionGroups.put(permissionGroupComponent.className, permissionGroupComponent);
    } 
    synchronized (this.mDangerousPermissions) {
      this.mDangerousPermissions.put(paramVPackage.packageName, PermissionCompat.findDangerousPermissions(paramVPackage.requestedPermissions));
      return;
    } 
  }
  
  public int checkPermission(boolean paramBoolean, String paramString1, String paramString2, int paramInt) {
    return ("android.permission.INTERACT_ACROSS_USERS".equals(paramString1) || "android.permission.INTERACT_ACROSS_USERS_FULL".equals(paramString1)) ? -1 : ((getPermissionInfo(paramString1, 0) != null) ? 0 : VirtualCore.getPM().checkPermission(paramString1, StubManifest.getStubPackageName(paramBoolean)));
  }
  
  public int checkSignatures(String paramString1, String paramString2) {
    if (TextUtils.equals(paramString1, paramString2))
      return 0; 
    PackageInfo packageInfo2 = getPackageInfo(paramString1, 64, 0);
    PackageInfo packageInfo3 = getPackageInfo(paramString2, 64, 0);
    PackageInfo packageInfo4 = packageInfo2;
    if (packageInfo2 == null)
      try {
        packageInfo4 = VirtualCore.get().getHostPackageManager().getPackageInfo(paramString1, 64);
      } catch (android.content.pm.PackageManager.NameNotFoundException null) {
        return -4;
      }  
    PackageInfo packageInfo1 = packageInfo3;
    if (packageInfo3 == null)
      try {
        packageInfo1 = VirtualCore.get().getHostPackageManager().getPackageInfo(paramString2, 64);
      } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
        return -4;
      }  
    return SignaturesUtils.compareSignatures(packageInfo4.signatures, ((PackageInfo)nameNotFoundException).signatures);
  }
  
  public int checkUidPermission(boolean paramBoolean, String paramString, int paramInt) {
    return (getPermissionInfo(paramString, 0) != null) ? 0 : VirtualCore.getPM().checkPermission(paramString, StubManifest.getStubPackageName(paramBoolean));
  }
  
  void cleanUpUser(int paramInt) {
    Iterator iterator = this.mPackages.values().iterator();
    while (iterator.hasNext())
      ((PackageSetting)((VPackage)iterator.next()).mExtras).removeUser(paramInt); 
    ComponentStateManager.user(paramInt).clearAll();
  }
  
  void createNewUser(int paramInt, File paramFile) {
    Iterator iterator = this.mPackages.values().iterator();
    while (iterator.hasNext())
      ((PackageSetting)((VPackage)iterator.next()).mExtras).modifyUserState(paramInt); 
  }
  
  void deletePackageLocked(VPackage paramVPackage) {
    if (paramVPackage == null)
      return; 
    int i = paramVPackage.activities.size();
    boolean bool = false;
    byte b;
    for (b = 0; b < i; b++) {
      VPackage.ActivityComponent activityComponent = paramVPackage.activities.get(b);
      this.mActivities.removeActivity(activityComponent, "activity");
    } 
    i = paramVPackage.services.size();
    for (b = 0; b < i; b++) {
      VPackage.ServiceComponent serviceComponent = paramVPackage.services.get(b);
      this.mServices.removeService(serviceComponent);
    } 
    i = paramVPackage.receivers.size();
    for (b = 0; b < i; b++) {
      VPackage.ActivityComponent activityComponent = paramVPackage.receivers.get(b);
      this.mReceivers.removeActivity(activityComponent, "receiver");
    } 
    int j = paramVPackage.providers.size();
    b = 0;
    while (b < j) {
      VPackage.ProviderComponent providerComponent = paramVPackage.providers.get(b);
      this.mProviders.removeProvider(providerComponent);
      String[] arrayOfString = providerComponent.info.authority.split(";");
      synchronized (this.mProvidersByAuthority) {
        int k = arrayOfString.length;
        for (i = 0; i < k; i++) {
          String str = arrayOfString[i];
          this.mProvidersByAuthority.remove(str);
        } 
        this.mProvidersByComponent.remove(providerComponent.getComponentName());
        b++;
      } 
    } 
    i = paramVPackage.permissions.size();
    for (b = 0; b < i; b++) {
      VPackage.PermissionComponent permissionComponent = paramVPackage.permissions.get(b);
      this.mPermissions.remove(permissionComponent.className);
    } 
    i = paramVPackage.permissionGroups.size();
    for (b = bool; b < i; b++) {
      VPackage.PermissionGroupComponent permissionGroupComponent = paramVPackage.permissionGroups.get(b);
      this.mPermissionGroups.remove(permissionGroupComponent.className);
    } 
  }
  
  public ActivityInfo getActivityInfo(ComponentName paramComponentName, int paramInt1, int paramInt2) {
    checkUserId(paramInt2);
    paramInt1 = updateFlagsNought(paramInt1);
    synchronized (this.mPackages) {
      VPackage vPackage = this.mPackages.get(paramComponentName.getPackageName());
      if (vPackage != null) {
        PackageSetting packageSetting = (PackageSetting)vPackage.mExtras;
        VPackage.ActivityComponent activityComponent = (VPackage.ActivityComponent)this.mActivities.mActivities.get(paramComponentName);
        if (activityComponent != null) {
          ActivityInfo activityInfo = PackageParserEx.generateActivityInfo(activityComponent, paramInt1, packageSetting.readUserState(paramInt2), paramInt2, packageSetting.isRunInExtProcess());
          ComponentFixer.fixComponentInfo((ComponentInfo)activityInfo);
          return activityInfo;
        } 
      } 
      return null;
    } 
  }
  
  public List<PermissionGroupInfo> getAllPermissionGroups(int paramInt) {
    synchronized (this.mPackages) {
      paramInt = this.mPermissionGroups.size();
      ArrayList<PermissionGroupInfo> arrayList = new ArrayList();
      this(paramInt);
      for (VPackage.PermissionGroupComponent permissionGroupComponent : this.mPermissionGroups.values()) {
        PermissionGroupInfo permissionGroupInfo = new PermissionGroupInfo();
        this(permissionGroupComponent.info);
        arrayList.add(permissionGroupInfo);
      } 
      return arrayList;
    } 
  }
  
  public ApplicationInfo getApplicationInfo(String paramString, int paramInt1, int paramInt2) {
    checkUserId(paramInt2);
    paramInt1 = updateFlagsNought(paramInt1);
    synchronized (this.mPackages) {
      VPackage vPackage = this.mPackages.get(paramString);
      if (vPackage != null) {
        PackageSetting packageSetting = (PackageSetting)vPackage.mExtras;
        return PackageParserEx.generateApplicationInfo(vPackage, paramInt1, packageSetting.readUserState(paramInt2), paramInt2, packageSetting.isRunInExtProcess());
      } 
      return null;
    } 
  }
  
  public int getComponentEnabledSetting(ComponentName paramComponentName, int paramInt) {
    if (paramComponentName == null)
      return 0; 
    checkUserId(paramInt);
    synchronized (this.mPackages) {
      paramInt = ComponentStateManager.user(paramInt).get(paramComponentName);
      return paramInt;
    } 
  }
  
  public String[] getDangerousPermissions(String paramString) {
    synchronized (this.mDangerousPermissions) {
      return this.mDangerousPermissions.get(paramString);
    } 
  }
  
  public VParceledListSlice<ApplicationInfo> getInstalledApplications(int paramInt1, int paramInt2) {
    checkUserId(paramInt2);
    paramInt1 = updateFlagsNought(paramInt1);
    null = new ArrayList(this.mPackages.size());
    synchronized (this.mPackages) {
      for (VPackage vPackage : this.mPackages.values()) {
        PackageSetting packageSetting = (PackageSetting)vPackage.mExtras;
        ApplicationInfo applicationInfo = PackageParserEx.generateApplicationInfo(vPackage, paramInt1, packageSetting.readUserState(paramInt2), paramInt2, packageSetting.isRunInExtProcess());
        if (applicationInfo != null)
          null.add(applicationInfo); 
      } 
      return new VParceledListSlice(null);
    } 
  }
  
  public VParceledListSlice<PackageInfo> getInstalledPackages(int paramInt1, int paramInt2) {
    checkUserId(paramInt2);
    null = new ArrayList(this.mPackages.size());
    synchronized (this.mPackages) {
      for (VPackage vPackage : this.mPackages.values()) {
        PackageInfo packageInfo = generatePackageInfo(vPackage, (PackageSetting)vPackage.mExtras, paramInt1, paramInt2);
        if (packageInfo != null)
          null.add(packageInfo); 
      } 
      return new VParceledListSlice(null);
    } 
  }
  
  public String getNameForUid(int paramInt) {
    paramInt = VUserHandle.getAppId(paramInt);
    synchronized (this.mPackages) {
      Iterator iterator = this.mPackages.values().iterator();
      while (iterator.hasNext()) {
        PackageSetting packageSetting = (PackageSetting)((VPackage)iterator.next()).mExtras;
        if (packageSetting.appId == paramInt)
          return packageSetting.packageName; 
      } 
      return null;
    } 
  }
  
  public PackageInfo getPackageInfo(String paramString, int paramInt1, int paramInt2) {
    checkUserId(paramInt2);
    synchronized (this.mPackages) {
      VPackage vPackage = this.mPackages.get(paramString);
      if (vPackage != null)
        return generatePackageInfo(vPackage, (PackageSetting)vPackage.mExtras, paramInt1, paramInt2); 
      return null;
    } 
  }
  
  public IBinder getPackageInstaller() {
    return (IBinder)VPackageInstallerService.get();
  }
  
  public int getPackageUid(String paramString, int paramInt) {
    checkUserId(paramInt);
    synchronized (this.mPackages) {
      VPackage vPackage = this.mPackages.get(paramString);
      if (vPackage != null) {
        paramInt = VUserHandle.getUid(paramInt, ((PackageSetting)vPackage.mExtras).appId);
        return paramInt;
      } 
      return -1;
    } 
  }
  
  public String[] getPackagesForUid(int paramInt) {
    // Byte code:
    //   0: iload_1
    //   1: invokestatic getUserId : (I)I
    //   4: istore_2
    //   5: aload_0
    //   6: iload_2
    //   7: invokespecial checkUserId : (I)V
    //   10: aload_0
    //   11: monitorenter
    //   12: new java/util/ArrayList
    //   15: astore_3
    //   16: aload_3
    //   17: iconst_2
    //   18: invokespecial <init> : (I)V
    //   21: aload_0
    //   22: getfield mPackages : Ljava/util/Map;
    //   25: invokeinterface values : ()Ljava/util/Collection;
    //   30: invokeinterface iterator : ()Ljava/util/Iterator;
    //   35: astore #4
    //   37: aload #4
    //   39: invokeinterface hasNext : ()Z
    //   44: ifeq -> 100
    //   47: aload #4
    //   49: invokeinterface next : ()Ljava/lang/Object;
    //   54: checkcast com/lody/virtual/server/pm/parser/VPackage
    //   57: astore #5
    //   59: iload_2
    //   60: aload #5
    //   62: getfield mExtras : Ljava/lang/Object;
    //   65: checkcast com/lody/virtual/server/pm/PackageSetting
    //   68: getfield appId : I
    //   71: invokestatic getUid : (II)I
    //   74: iload_1
    //   75: if_icmpeq -> 85
    //   78: iload_1
    //   79: sipush #9001
    //   82: if_icmpne -> 37
    //   85: aload_3
    //   86: aload #5
    //   88: getfield packageName : Ljava/lang/String;
    //   91: invokeinterface add : (Ljava/lang/Object;)Z
    //   96: pop
    //   97: goto -> 37
    //   100: aload_3
    //   101: invokeinterface isEmpty : ()Z
    //   106: ifeq -> 121
    //   109: ldc 'PackageManager'
    //   111: ldc_w 'getPackagesForUid return an empty result.'
    //   114: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)V
    //   117: aload_0
    //   118: monitorexit
    //   119: aconst_null
    //   120: areturn
    //   121: aload_3
    //   122: iconst_0
    //   123: anewarray java/lang/String
    //   126: invokeinterface toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
    //   131: checkcast [Ljava/lang/String;
    //   134: astore #4
    //   136: aload_0
    //   137: monitorexit
    //   138: aload #4
    //   140: areturn
    //   141: astore #4
    //   143: aload_0
    //   144: monitorexit
    //   145: aload #4
    //   147: athrow
    // Exception table:
    //   from	to	target	type
    //   12	37	141	finally
    //   37	78	141	finally
    //   85	97	141	finally
    //   100	119	141	finally
    //   121	138	141	finally
    //   143	145	141	finally
  }
  
  public PermissionGroupInfo getPermissionGroupInfo(String paramString, int paramInt) {
    synchronized (this.mPackages) {
      VPackage.PermissionGroupComponent permissionGroupComponent = this.mPermissionGroups.get(paramString);
      if (permissionGroupComponent != null) {
        PermissionGroupInfo permissionGroupInfo = new PermissionGroupInfo();
        this(permissionGroupComponent.info);
        return permissionGroupInfo;
      } 
      return null;
    } 
  }
  
  public PermissionInfo getPermissionInfo(String paramString, int paramInt) {
    synchronized (this.mPackages) {
      VPackage.PermissionComponent permissionComponent = this.mPermissions.get(paramString);
      if (permissionComponent != null) {
        PermissionInfo permissionInfo = new PermissionInfo();
        this(permissionComponent.info);
        return permissionInfo;
      } 
      return null;
    } 
  }
  
  public ProviderInfo getProviderInfo(ComponentName paramComponentName, int paramInt1, int paramInt2) {
    checkUserId(paramInt2);
    paramInt1 = updateFlagsNought(paramInt1);
    synchronized (this.mPackages) {
      VPackage vPackage = this.mPackages.get(paramComponentName.getPackageName());
      if (vPackage != null) {
        PackageSetting packageSetting = (PackageSetting)vPackage.mExtras;
        VPackage.ProviderComponent providerComponent = this.mProvidersByComponent.get(paramComponentName);
        if (providerComponent != null && packageSetting.isEnabledAndMatchLPr((ComponentInfo)providerComponent.info, paramInt1, paramInt2)) {
          ProviderInfo providerInfo = PackageParserEx.generateProviderInfo(providerComponent, paramInt1, packageSetting.readUserState(paramInt2), paramInt2, packageSetting.isRunInExtProcess());
          ComponentFixer.fixComponentInfo((ComponentInfo)providerInfo);
          return providerInfo;
        } 
      } 
      return null;
    } 
  }
  
  public ActivityInfo getReceiverInfo(ComponentName paramComponentName, int paramInt1, int paramInt2) {
    checkUserId(paramInt2);
    paramInt1 = updateFlagsNought(paramInt1);
    synchronized (this.mPackages) {
      VPackage vPackage = this.mPackages.get(paramComponentName.getPackageName());
      if (vPackage != null) {
        PackageSetting packageSetting = (PackageSetting)vPackage.mExtras;
        VPackage.ActivityComponent activityComponent = (VPackage.ActivityComponent)this.mReceivers.mActivities.get(paramComponentName);
        if (activityComponent != null && packageSetting.isEnabledAndMatchLPr((ComponentInfo)activityComponent.info, paramInt1, paramInt2)) {
          ActivityInfo activityInfo = PackageParserEx.generateActivityInfo(activityComponent, paramInt1, packageSetting.readUserState(paramInt2), paramInt2, packageSetting.isRunInExtProcess());
          ComponentFixer.fixComponentInfo((ComponentInfo)activityInfo);
          return activityInfo;
        } 
      } 
      return null;
    } 
  }
  
  public List<ReceiverInfo> getReceiverInfos(String paramString1, String paramString2, int paramInt) {
    ArrayList<ReceiverInfo> arrayList = new ArrayList();
    synchronized (this.mPackages) {
      VPackage vPackage = this.mPackages.get(paramString1);
      if (vPackage == null)
        return (List)Collections.emptyList(); 
      PackageSetting packageSetting = (PackageSetting)vPackage.mExtras;
      for (VPackage.ActivityComponent activityComponent : vPackage.receivers) {
        if (packageSetting.isEnabledAndMatchLPr((ComponentInfo)activityComponent.info, 0, paramInt) && activityComponent.info.processName.equals(paramString2)) {
          ArrayList<IntentFilter> arrayList1 = new ArrayList();
          this();
          Iterator iterator = activityComponent.intents.iterator();
          while (iterator.hasNext())
            arrayList1.add(((VPackage.ActivityIntentInfo)iterator.next()).filter); 
          ReceiverInfo receiverInfo = new ReceiverInfo();
          this(activityComponent.info, arrayList1);
          arrayList.add(receiverInfo);
        } 
      } 
      return arrayList;
    } 
  }
  
  public ServiceInfo getServiceInfo(ComponentName paramComponentName, int paramInt1, int paramInt2) {
    checkUserId(paramInt2);
    paramInt1 = updateFlagsNought(paramInt1);
    synchronized (this.mPackages) {
      VPackage vPackage = this.mPackages.get(paramComponentName.getPackageName());
      if (vPackage != null) {
        PackageSetting packageSetting = (PackageSetting)vPackage.mExtras;
        VPackage.ServiceComponent serviceComponent = (VPackage.ServiceComponent)this.mServices.mServices.get(paramComponentName);
        if (serviceComponent != null) {
          ServiceInfo serviceInfo = PackageParserEx.generateServiceInfo(serviceComponent, paramInt1, packageSetting.readUserState(paramInt2), paramInt2, packageSetting.isRunInExtProcess());
          ComponentFixer.fixComponentInfo((ComponentInfo)serviceInfo);
          return serviceInfo;
        } 
      } 
      return null;
    } 
  }
  
  public List<String> getSharedLibraries(String paramString) {
    synchronized (this.mPackages) {
      VPackage vPackage = this.mPackages.get(paramString);
      if (vPackage != null)
        return vPackage.usesLibraries; 
      return null;
    } 
  }
  
  public VParceledListSlice<ProviderInfo> queryContentProviders(String paramString, int paramInt1, int paramInt2) {
    int i = VUserHandle.getUserId(paramInt1);
    checkUserId(i);
    paramInt2 = updateFlagsNought(paramInt2);
    ArrayList<ProviderInfo> arrayList = new ArrayList(3);
    synchronized (this.mPackages) {
      for (VPackage.ProviderComponent providerComponent : this.mProvidersByAuthority.values()) {
        PackageSetting packageSetting = (PackageSetting)providerComponent.owner.mExtras;
        if (packageSetting.isEnabledAndMatchLPr((ComponentInfo)providerComponent.info, paramInt2, i) && (paramString == null || (packageSetting.appId == VUserHandle.getAppId(paramInt1) && providerComponent.info.processName.equals(paramString))))
          arrayList.add(PackageParserEx.generateProviderInfo(providerComponent, paramInt2, packageSetting.readUserState(i), i, packageSetting.isRunInExtProcess())); 
      } 
      if (!arrayList.isEmpty())
        Collections.sort(arrayList, sProviderInitOrderSorter); 
      return new VParceledListSlice(arrayList);
    } 
  }
  
  public List<ResolveInfo> queryIntentActivities(Intent paramIntent, String paramString, int paramInt1, int paramInt2) {
    ResolveInfo resolveInfo;
    checkUserId(paramInt2);
    paramInt1 = updateFlagsNought(paramInt1);
    ComponentName componentName1 = paramIntent.getComponent();
    Intent intent = paramIntent;
    ComponentName componentName2 = componentName1;
    if (componentName1 == null) {
      intent = paramIntent;
      componentName2 = componentName1;
      if (paramIntent.getSelector() != null) {
        intent = paramIntent.getSelector();
        componentName2 = intent.getComponent();
      } 
    } 
    if (componentName2 != null) {
      ArrayList<ResolveInfo> arrayList = new ArrayList(1);
      null = getActivityInfo(componentName2, paramInt1, paramInt2);
      if (null != null) {
        resolveInfo = new ResolveInfo();
        resolveInfo.activityInfo = null;
        arrayList.add(resolveInfo);
      } 
      return arrayList;
    } 
    synchronized (this.mPackages) {
      String str = resolveInfo.getPackage();
      if (str == null) {
        list = this.mActivities.queryIntent((Intent)resolveInfo, (String)null, paramInt1, paramInt2);
        return list;
      } 
      VPackage vPackage = this.mPackages.get(str);
      if (vPackage != null) {
        list = this.mActivities.queryIntentForPackage((Intent)resolveInfo, (String)list, paramInt1, vPackage.activities, paramInt2);
        return list;
      } 
      List<ResolveInfo> list = Collections.emptyList();
      return list;
    } 
  }
  
  public List<ResolveInfo> queryIntentContentProviders(Intent paramIntent, String paramString, int paramInt1, int paramInt2) {
    ResolveInfo resolveInfo;
    checkUserId(paramInt2);
    paramInt1 = updateFlagsNought(paramInt1);
    ComponentName componentName1 = paramIntent.getComponent();
    Intent intent = paramIntent;
    ComponentName componentName2 = componentName1;
    if (componentName1 == null) {
      intent = paramIntent;
      componentName2 = componentName1;
      if (paramIntent.getSelector() != null) {
        intent = paramIntent.getSelector();
        componentName2 = intent.getComponent();
      } 
    } 
    if (componentName2 != null) {
      ArrayList<ResolveInfo> arrayList = new ArrayList(1);
      null = getProviderInfo(componentName2, paramInt1, paramInt2);
      if (null != null) {
        resolveInfo = new ResolveInfo();
        resolveInfo.providerInfo = null;
        arrayList.add(resolveInfo);
      } 
      return arrayList;
    } 
    synchronized (this.mPackages) {
      String str = resolveInfo.getPackage();
      if (str == null) {
        list = this.mProviders.queryIntent((Intent)resolveInfo, (String)null, paramInt1, paramInt2);
        return list;
      } 
      VPackage vPackage = this.mPackages.get(str);
      if (vPackage != null) {
        list = this.mProviders.queryIntentForPackage((Intent)resolveInfo, (String)list, paramInt1, vPackage.providers, paramInt2);
        return list;
      } 
      List<ResolveInfo> list = Collections.emptyList();
      return list;
    } 
  }
  
  public List<ResolveInfo> queryIntentReceivers(Intent paramIntent, String paramString, int paramInt1, int paramInt2) {
    ResolveInfo resolveInfo;
    checkUserId(paramInt2);
    paramInt1 = updateFlagsNought(paramInt1);
    ComponentName componentName1 = paramIntent.getComponent();
    Intent intent = paramIntent;
    ComponentName componentName2 = componentName1;
    if (componentName1 == null) {
      intent = paramIntent;
      componentName2 = componentName1;
      if (paramIntent.getSelector() != null) {
        intent = paramIntent.getSelector();
        componentName2 = intent.getComponent();
      } 
    } 
    if (componentName2 != null) {
      ArrayList<ResolveInfo> arrayList = new ArrayList(1);
      null = getReceiverInfo(componentName2, paramInt1, paramInt2);
      if (null != null) {
        resolveInfo = new ResolveInfo();
        resolveInfo.activityInfo = null;
        arrayList.add(resolveInfo);
      } 
      return arrayList;
    } 
    synchronized (this.mPackages) {
      String str = resolveInfo.getPackage();
      if (str == null) {
        list = this.mReceivers.queryIntent((Intent)resolveInfo, (String)null, paramInt1, paramInt2);
        return list;
      } 
      VPackage vPackage = this.mPackages.get(str);
      if (vPackage != null) {
        list = this.mReceivers.queryIntentForPackage((Intent)resolveInfo, (String)list, paramInt1, vPackage.receivers, paramInt2);
        return list;
      } 
      List<ResolveInfo> list = Collections.emptyList();
      return list;
    } 
  }
  
  public List<ResolveInfo> queryIntentServices(Intent paramIntent, String paramString, int paramInt1, int paramInt2) {
    ServiceInfo serviceInfo;
    checkUserId(paramInt2);
    paramInt1 = updateFlagsNought(paramInt1);
    ComponentName componentName1 = paramIntent.getComponent();
    Intent intent = paramIntent;
    ComponentName componentName2 = componentName1;
    if (componentName1 == null) {
      intent = paramIntent;
      componentName2 = componentName1;
      if (paramIntent.getSelector() != null) {
        intent = paramIntent.getSelector();
        componentName2 = intent.getComponent();
      } 
    } 
    if (componentName2 != null) {
      ArrayList<ResolveInfo> arrayList = new ArrayList(1);
      serviceInfo = getServiceInfo(componentName2, paramInt1, paramInt2);
      if (serviceInfo != null) {
        null = new ResolveInfo();
        null.serviceInfo = serviceInfo;
        arrayList.add(null);
      } 
      return arrayList;
    } 
    synchronized (this.mPackages) {
      String str = serviceInfo.getPackage();
      if (str == null) {
        list = this.mServices.queryIntent((Intent)serviceInfo, (String)null, paramInt1, paramInt2);
        return list;
      } 
      VPackage vPackage = this.mPackages.get(str);
      if (vPackage != null) {
        list = this.mServices.queryIntentForPackage((Intent)serviceInfo, (String)list, paramInt1, vPackage.services, paramInt2);
        return list;
      } 
      List<ResolveInfo> list = Collections.emptyList();
      return list;
    } 
  }
  
  public List<PermissionInfo> queryPermissionsByGroup(String paramString, int paramInt) {
    ArrayList<PermissionInfo> arrayList = new ArrayList();
    if (paramString != null)
      synchronized (this.mPackages) {
        for (VPackage.PermissionComponent permissionComponent : this.mPermissions.values()) {
          if (permissionComponent.info.group.equals(paramString))
            arrayList.add(permissionComponent.info); 
        } 
      }  
    return arrayList;
  }
  
  public List<String> querySharedPackages(String paramString) {
    synchronized (this.mPackages) {
      VPackage vPackage = this.mPackages.get(paramString);
      if (vPackage == null || vPackage.mSharedUserId == null)
        return Collections.EMPTY_LIST; 
      ArrayList<String> arrayList = new ArrayList();
      this();
      for (VPackage vPackage1 : this.mPackages.values()) {
        if (TextUtils.equals(vPackage1.mSharedUserId, vPackage.mSharedUserId))
          arrayList.add(vPackage1.packageName); 
      } 
      return arrayList;
    } 
  }
  
  public ProviderInfo resolveContentProvider(String paramString, int paramInt1, int paramInt2) {
    HashMap<String, VPackage.ProviderComponent> hashMap;
    ProviderInfo providerInfo;
    checkUserId(paramInt2);
    paramInt1 = updateFlagsNought(paramInt1);
    synchronized (this.mProvidersByAuthority) {
      VPackage.ProviderComponent providerComponent = this.mProvidersByAuthority.get(paramString);
      if (providerComponent != null) {
        PackageSetting packageSetting = (PackageSetting)providerComponent.owner.mExtras;
        providerInfo = PackageParserEx.generateProviderInfo(providerComponent, paramInt1, packageSetting.readUserState(paramInt2), paramInt2, packageSetting.isRunInExtProcess());
        if (providerInfo != null) {
          if (!packageSetting.isEnabledAndMatchLPr((ComponentInfo)providerInfo, paramInt1, paramInt2))
            return null; 
          ComponentFixer.fixComponentInfo((ComponentInfo)providerInfo);
          return providerInfo;
        } 
      } 
      return null;
    } 
  }
  
  public ResolveInfo resolveIntent(Intent paramIntent, String paramString, int paramInt1, int paramInt2) {
    checkUserId(paramInt2);
    paramInt1 = updateFlagsNought(paramInt1);
    return chooseBestActivity(paramIntent, paramString, paramInt1, queryIntentActivities(paramIntent, paramString, paramInt1, paramInt2));
  }
  
  public ResolveInfo resolveService(Intent paramIntent, String paramString, int paramInt1, int paramInt2) {
    checkUserId(paramInt2);
    List<ResolveInfo> list = queryIntentServices(paramIntent, paramString, updateFlagsNought(paramInt1), paramInt2);
    return (list != null && list.size() >= 1) ? list.get(0) : null;
  }
  
  public void setComponentEnabledSetting(ComponentName paramComponentName, int paramInt1, int paramInt2, int paramInt3) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("setComponentEnabledSetting ");
    stringBuilder.append(paramComponentName);
    stringBuilder.append(" newState: ");
    stringBuilder.append(paramInt1);
    stringBuilder.append(" flags: ");
    stringBuilder.append(paramInt2);
    VLog.e("PackageManager", stringBuilder.toString());
    if (paramComponentName == null)
      return; 
    checkUserId(paramInt3);
    synchronized (this.mPackages) {
      ComponentStateManager.user(paramInt3).set(paramComponentName, paramInt1);
      return;
    } 
  }
  
  private static final class ActivityIntentResolver extends IntentResolver<VPackage.ActivityIntentInfo, ResolveInfo> {
    private final HashMap<ComponentName, VPackage.ActivityComponent> mActivities = new HashMap<ComponentName, VPackage.ActivityComponent>();
    
    private int mFlags;
    
    private ActivityIntentResolver() {}
    
    public final void addActivity(VPackage.ActivityComponent param1ActivityComponent, String param1String) {
      this.mActivities.put(param1ActivityComponent.getComponentName(), param1ActivityComponent);
      int i = param1ActivityComponent.intents.size();
      for (byte b = 0; b < i; b++) {
        VPackage.ActivityIntentInfo activityIntentInfo = param1ActivityComponent.intents.get(b);
        if (activityIntentInfo.filter.getPriority() > 0 && "activity".equals(param1String)) {
          activityIntentInfo.filter.setPriority(0);
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("Package ");
          stringBuilder.append(param1ActivityComponent.info.applicationInfo.packageName);
          stringBuilder.append(" has activity ");
          stringBuilder.append(param1ActivityComponent.className);
          stringBuilder.append(" with priority > 0, forcing to 0");
          Log.w("PackageManager", stringBuilder.toString());
        } 
        addFilter(activityIntentInfo);
      } 
    }
    
    protected boolean allowFilterResult(VPackage.ActivityIntentInfo param1ActivityIntentInfo, List<ResolveInfo> param1List) {
      ActivityInfo activityInfo = param1ActivityIntentInfo.activity.info;
      for (int i = param1List.size() - 1; i >= 0; i--) {
        ActivityInfo activityInfo1 = ((ResolveInfo)param1List.get(i)).activityInfo;
        if (ObjectsCompat.equals(activityInfo1.name, activityInfo.name) && ObjectsCompat.equals(activityInfo1.packageName, activityInfo.packageName))
          return false; 
      } 
      return true;
    }
    
    protected void dumpFilter(PrintWriter param1PrintWriter, String param1String, VPackage.ActivityIntentInfo param1ActivityIntentInfo) {}
    
    protected void dumpFilterLabel(PrintWriter param1PrintWriter, String param1String, Object param1Object, int param1Int) {}
    
    protected Object filterToLabel(VPackage.ActivityIntentInfo param1ActivityIntentInfo) {
      return param1ActivityIntentInfo.activity;
    }
    
    protected boolean isFilterStopped(VPackage.ActivityIntentInfo param1ActivityIntentInfo) {
      return false;
    }
    
    protected boolean isPackageForFilter(String param1String, VPackage.ActivityIntentInfo param1ActivityIntentInfo) {
      return param1String.equals(param1ActivityIntentInfo.activity.owner.packageName);
    }
    
    protected VPackage.ActivityIntentInfo[] newArray(int param1Int) {
      return new VPackage.ActivityIntentInfo[param1Int];
    }
    
    protected ResolveInfo newResult(VPackage.ActivityIntentInfo param1ActivityIntentInfo, int param1Int1, int param1Int2) {
      VPackage.ActivityComponent activityComponent = param1ActivityIntentInfo.activity;
      PackageSetting packageSetting = (PackageSetting)activityComponent.owner.mExtras;
      if (!packageSetting.isEnabledAndMatchLPr((ComponentInfo)activityComponent.info, this.mFlags, param1Int2))
        return null; 
      ActivityInfo activityInfo = PackageParserEx.generateActivityInfo(activityComponent, this.mFlags, packageSetting.readUserState(param1Int2), param1Int2, packageSetting.isRunInExtProcess());
      if (activityInfo == null)
        return null; 
      ResolveInfo resolveInfo = new ResolveInfo();
      resolveInfo.activityInfo = activityInfo;
      if ((this.mFlags & 0x40) != 0)
        resolveInfo.filter = param1ActivityIntentInfo.filter; 
      resolveInfo.priority = param1ActivityIntentInfo.filter.getPriority();
      resolveInfo.preferredOrder = activityComponent.owner.mPreferredOrder;
      resolveInfo.match = param1Int1;
      resolveInfo.isDefault = param1ActivityIntentInfo.hasDefault;
      resolveInfo.labelRes = param1ActivityIntentInfo.labelRes;
      resolveInfo.nonLocalizedLabel = param1ActivityIntentInfo.nonLocalizedLabel;
      resolveInfo.icon = param1ActivityIntentInfo.icon;
      return resolveInfo;
    }
    
    List<ResolveInfo> queryIntent(Intent param1Intent, String param1String, int param1Int1, int param1Int2) {
      boolean bool;
      this.mFlags = param1Int1;
      if ((param1Int1 & 0x10000) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return super.queryIntent(param1Intent, param1String, bool, param1Int2);
    }
    
    public List<ResolveInfo> queryIntent(Intent param1Intent, String param1String, boolean param1Boolean, int param1Int) {
      boolean bool;
      if (param1Boolean) {
        bool = true;
      } else {
        bool = false;
      } 
      this.mFlags = bool;
      return super.queryIntent(param1Intent, param1String, param1Boolean, param1Int);
    }
    
    List<ResolveInfo> queryIntentForPackage(Intent param1Intent, String param1String, int param1Int1, ArrayList<VPackage.ActivityComponent> param1ArrayList, int param1Int2) {
      boolean bool2;
      if (param1ArrayList == null)
        return null; 
      this.mFlags = param1Int1;
      boolean bool1 = false;
      if ((param1Int1 & 0x10000) != 0) {
        bool2 = true;
      } else {
        bool2 = false;
      } 
      int i = param1ArrayList.size();
      ArrayList<VPackage.ActivityIntentInfo[]> arrayList = new ArrayList(i);
      for (param1Int1 = bool1; param1Int1 < i; param1Int1++) {
        ArrayList arrayList1 = ((VPackage.ActivityComponent)param1ArrayList.get(param1Int1)).intents;
        if (arrayList1 != null && arrayList1.size() > 0) {
          VPackage.ActivityIntentInfo[] arrayOfActivityIntentInfo = new VPackage.ActivityIntentInfo[arrayList1.size()];
          arrayList1.toArray((Object[])arrayOfActivityIntentInfo);
          arrayList.add(arrayOfActivityIntentInfo);
        } 
      } 
      return (List)queryIntentFromList(param1Intent, param1String, bool2, (ArrayList)arrayList, param1Int2);
    }
    
    public final void removeActivity(VPackage.ActivityComponent param1ActivityComponent, String param1String) {
      this.mActivities.remove(param1ActivityComponent.getComponentName());
      int i = param1ActivityComponent.intents.size();
      for (byte b = 0; b < i; b++)
        removeFilter(param1ActivityComponent.intents.get(b)); 
    }
    
    protected void sortResults(List<ResolveInfo> param1List) {
      Collections.sort(param1List, VPackageManagerService.sResolvePrioritySorter);
    }
  }
  
  private static final class ServiceIntentResolver extends IntentResolver<VPackage.ServiceIntentInfo, ResolveInfo> {
    private int mFlags;
    
    private final HashMap<ComponentName, VPackage.ServiceComponent> mServices = new HashMap<ComponentName, VPackage.ServiceComponent>();
    
    private ServiceIntentResolver() {}
    
    public final void addService(VPackage.ServiceComponent param1ServiceComponent) {
      this.mServices.put(param1ServiceComponent.getComponentName(), param1ServiceComponent);
      int i = param1ServiceComponent.intents.size();
      for (byte b = 0; b < i; b++)
        addFilter(param1ServiceComponent.intents.get(b)); 
    }
    
    protected boolean allowFilterResult(VPackage.ServiceIntentInfo param1ServiceIntentInfo, List<ResolveInfo> param1List) {
      ServiceInfo serviceInfo = param1ServiceIntentInfo.service.info;
      for (int i = param1List.size() - 1; i >= 0; i--) {
        ServiceInfo serviceInfo1 = ((ResolveInfo)param1List.get(i)).serviceInfo;
        if (ObjectsCompat.equals(serviceInfo1.name, serviceInfo.name) && ObjectsCompat.equals(serviceInfo1.packageName, serviceInfo.packageName))
          return false; 
      } 
      return true;
    }
    
    protected void dumpFilter(PrintWriter param1PrintWriter, String param1String, VPackage.ServiceIntentInfo param1ServiceIntentInfo) {}
    
    protected void dumpFilterLabel(PrintWriter param1PrintWriter, String param1String, Object param1Object, int param1Int) {}
    
    protected Object filterToLabel(VPackage.ServiceIntentInfo param1ServiceIntentInfo) {
      return param1ServiceIntentInfo.service;
    }
    
    protected boolean isFilterStopped(VPackage.ServiceIntentInfo param1ServiceIntentInfo) {
      return false;
    }
    
    protected boolean isPackageForFilter(String param1String, VPackage.ServiceIntentInfo param1ServiceIntentInfo) {
      return param1String.equals(param1ServiceIntentInfo.service.owner.packageName);
    }
    
    protected VPackage.ServiceIntentInfo[] newArray(int param1Int) {
      return new VPackage.ServiceIntentInfo[param1Int];
    }
    
    protected ResolveInfo newResult(VPackage.ServiceIntentInfo param1ServiceIntentInfo, int param1Int1, int param1Int2) {
      VPackage.ServiceComponent serviceComponent = param1ServiceIntentInfo.service;
      PackageSetting packageSetting = (PackageSetting)serviceComponent.owner.mExtras;
      if (!packageSetting.isEnabledAndMatchLPr((ComponentInfo)serviceComponent.info, this.mFlags, param1Int2))
        return null; 
      ServiceInfo serviceInfo = PackageParserEx.generateServiceInfo(serviceComponent, this.mFlags, packageSetting.readUserState(param1Int2), param1Int2, packageSetting.isRunInExtProcess());
      if (serviceInfo == null)
        return null; 
      ResolveInfo resolveInfo = new ResolveInfo();
      resolveInfo.serviceInfo = serviceInfo;
      if ((this.mFlags & 0x40) != 0)
        resolveInfo.filter = param1ServiceIntentInfo.filter; 
      resolveInfo.priority = param1ServiceIntentInfo.filter.getPriority();
      resolveInfo.preferredOrder = serviceComponent.owner.mPreferredOrder;
      resolveInfo.match = param1Int1;
      resolveInfo.isDefault = param1ServiceIntentInfo.hasDefault;
      resolveInfo.labelRes = param1ServiceIntentInfo.labelRes;
      resolveInfo.nonLocalizedLabel = param1ServiceIntentInfo.nonLocalizedLabel;
      resolveInfo.icon = param1ServiceIntentInfo.icon;
      return resolveInfo;
    }
    
    public List<ResolveInfo> queryIntent(Intent param1Intent, String param1String, int param1Int1, int param1Int2) {
      boolean bool;
      this.mFlags = param1Int1;
      if ((param1Int1 & 0x10000) != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return super.queryIntent(param1Intent, param1String, bool, param1Int2);
    }
    
    public List<ResolveInfo> queryIntent(Intent param1Intent, String param1String, boolean param1Boolean, int param1Int) {
      boolean bool;
      if (param1Boolean) {
        bool = true;
      } else {
        bool = false;
      } 
      this.mFlags = bool;
      return super.queryIntent(param1Intent, param1String, param1Boolean, param1Int);
    }
    
    public List<ResolveInfo> queryIntentForPackage(Intent param1Intent, String param1String, int param1Int1, ArrayList<VPackage.ServiceComponent> param1ArrayList, int param1Int2) {
      boolean bool2;
      if (param1ArrayList == null)
        return null; 
      this.mFlags = param1Int1;
      boolean bool1 = false;
      if ((param1Int1 & 0x10000) != 0) {
        bool2 = true;
      } else {
        bool2 = false;
      } 
      int i = param1ArrayList.size();
      ArrayList<VPackage.ServiceIntentInfo[]> arrayList = new ArrayList(i);
      for (param1Int1 = bool1; param1Int1 < i; param1Int1++) {
        ArrayList arrayList1 = ((VPackage.ServiceComponent)param1ArrayList.get(param1Int1)).intents;
        if (arrayList1 != null && arrayList1.size() > 0) {
          VPackage.ServiceIntentInfo[] arrayOfServiceIntentInfo = new VPackage.ServiceIntentInfo[arrayList1.size()];
          arrayList1.toArray((Object[])arrayOfServiceIntentInfo);
          arrayList.add(arrayOfServiceIntentInfo);
        } 
      } 
      return (List)queryIntentFromList(param1Intent, param1String, bool2, (ArrayList)arrayList, param1Int2);
    }
    
    public final void removeService(VPackage.ServiceComponent param1ServiceComponent) {
      this.mServices.remove(param1ServiceComponent.getComponentName());
      int i = param1ServiceComponent.intents.size();
      for (byte b = 0; b < i; b++)
        removeFilter(param1ServiceComponent.intents.get(b)); 
    }
    
    protected void sortResults(List<ResolveInfo> param1List) {
      Collections.sort(param1List, VPackageManagerService.sResolvePrioritySorter);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\pm\VPackageManagerService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */