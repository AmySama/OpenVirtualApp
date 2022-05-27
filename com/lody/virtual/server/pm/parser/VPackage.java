package com.lody.virtual.server.pm.parser;

import android.content.ComponentName;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ConfigurationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageParser;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Iterator;
import mirror.android.content.pm.ApplicationInfoP;

public class VPackage implements Parcelable {
  public static final Parcelable.Creator<VPackage> CREATOR = new Parcelable.Creator<VPackage>() {
      public VPackage createFromParcel(Parcel param1Parcel) {
        return new VPackage(param1Parcel);
      }
      
      public VPackage[] newArray(int param1Int) {
        return new VPackage[param1Int];
      }
    };
  
  public ArrayList<ActivityComponent> activities;
  
  public ApplicationInfo applicationInfo;
  
  public ArrayList<ConfigurationInfo> configPreferences = null;
  
  public ArrayList<InstrumentationComponent> instrumentation;
  
  public Bundle mAppMetaData;
  
  public Object mExtras;
  
  public int mPreferredOrder;
  
  public String mSharedUserId;
  
  public int mSharedUserLabel;
  
  public Signature[] mSignatures;
  
  public PackageParser.SigningDetails mSigningDetails;
  
  public int mVersionCode;
  
  public String mVersionName;
  
  public String packageName;
  
  public ArrayList<PermissionGroupComponent> permissionGroups;
  
  public ArrayList<PermissionComponent> permissions;
  
  public ArrayList<String> protectedBroadcasts;
  
  public ArrayList<ProviderComponent> providers;
  
  public ArrayList<ActivityComponent> receivers;
  
  public ArrayList<FeatureInfo> reqFeatures = null;
  
  public ArrayList<String> requestedPermissions;
  
  public ArrayList<ServiceComponent> services;
  
  public ArrayList<SharedLibraryInfo> sharedLibraryInfos = null;
  
  public ArrayList<String> splitNames = null;
  
  public ArrayList<String> usesLibraries;
  
  public ArrayList<String> usesOptionalLibraries;
  
  public VPackage() {}
  
  protected VPackage(Parcel paramParcel) {
    int i = paramParcel.readInt();
    this.activities = new ArrayList<ActivityComponent>(i);
    while (i > 0) {
      this.activities.add(new ActivityComponent(paramParcel));
      i--;
    } 
    i = paramParcel.readInt();
    this.receivers = new ArrayList<ActivityComponent>(i);
    while (i > 0) {
      this.receivers.add(new ActivityComponent(paramParcel));
      i--;
    } 
    i = paramParcel.readInt();
    this.providers = new ArrayList<ProviderComponent>(i);
    while (i > 0) {
      this.providers.add(new ProviderComponent(paramParcel));
      i--;
    } 
    i = paramParcel.readInt();
    this.services = new ArrayList<ServiceComponent>(i);
    while (i > 0) {
      this.services.add(new ServiceComponent(paramParcel));
      i--;
    } 
    i = paramParcel.readInt();
    this.instrumentation = new ArrayList<InstrumentationComponent>(i);
    while (i > 0) {
      this.instrumentation.add(new InstrumentationComponent(paramParcel));
      i--;
    } 
    i = paramParcel.readInt();
    this.permissions = new ArrayList<PermissionComponent>(i);
    while (i > 0) {
      this.permissions.add(new PermissionComponent(paramParcel));
      i--;
    } 
    i = paramParcel.readInt();
    this.permissionGroups = new ArrayList<PermissionGroupComponent>(i);
    while (i > 0) {
      this.permissionGroups.add(new PermissionGroupComponent(paramParcel));
      i--;
    } 
    this.requestedPermissions = paramParcel.createStringArrayList();
    this.protectedBroadcasts = paramParcel.createStringArrayList();
    this.applicationInfo = (ApplicationInfo)paramParcel.readParcelable(ApplicationInfo.class.getClassLoader());
    this.mAppMetaData = paramParcel.readBundle(Bundle.class.getClassLoader());
    this.packageName = paramParcel.readString();
    this.mPreferredOrder = paramParcel.readInt();
    this.mVersionName = paramParcel.readString();
    this.mSharedUserId = paramParcel.readString();
    this.usesLibraries = paramParcel.createStringArrayList();
    this.usesOptionalLibraries = paramParcel.createStringArrayList();
    this.mVersionCode = paramParcel.readInt();
    this.mSharedUserLabel = paramParcel.readInt();
    this.configPreferences = paramParcel.createTypedArrayList(ConfigurationInfo.CREATOR);
    this.reqFeatures = paramParcel.createTypedArrayList(FeatureInfo.CREATOR);
    this.splitNames = paramParcel.createStringArrayList();
    if (ApplicationInfoP.sharedLibraryInfos != null)
      this.sharedLibraryInfos = paramParcel.createTypedArrayList(SharedLibraryInfo.CREATOR); 
  }
  
  public int describeContents() {
    return 0;
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeInt(this.activities.size());
    Iterator<ActivityComponent> iterator = this.activities.iterator();
    while (true) {
      boolean bool = iterator.hasNext();
      int i = 0;
      if (bool) {
        ActivityComponent activityComponent = iterator.next();
        paramParcel.writeParcelable((Parcelable)activityComponent.info, 0);
        paramParcel.writeString(activityComponent.className);
        paramParcel.writeBundle(activityComponent.metaData);
        if (activityComponent.intents != null)
          i = activityComponent.intents.size(); 
        paramParcel.writeInt(i);
        if (activityComponent.intents != null) {
          Iterator<ActivityIntentInfo> iterator1 = activityComponent.intents.iterator();
          while (iterator1.hasNext())
            ((ActivityIntentInfo)iterator1.next()).writeToParcel(paramParcel, paramInt); 
        } 
        continue;
      } 
      paramParcel.writeInt(this.receivers.size());
      for (ActivityComponent activityComponent : this.receivers) {
        paramParcel.writeParcelable((Parcelable)activityComponent.info, 0);
        paramParcel.writeString(activityComponent.className);
        paramParcel.writeBundle(activityComponent.metaData);
        if (activityComponent.intents != null) {
          i = activityComponent.intents.size();
        } else {
          i = 0;
        } 
        paramParcel.writeInt(i);
        if (activityComponent.intents != null) {
          Iterator<ActivityIntentInfo> iterator1 = activityComponent.intents.iterator();
          while (iterator1.hasNext())
            ((ActivityIntentInfo)iterator1.next()).writeToParcel(paramParcel, paramInt); 
        } 
      } 
      paramParcel.writeInt(this.providers.size());
      for (ProviderComponent providerComponent : this.providers) {
        paramParcel.writeParcelable((Parcelable)providerComponent.info, 0);
        paramParcel.writeString(providerComponent.className);
        paramParcel.writeBundle(providerComponent.metaData);
        if (providerComponent.intents != null) {
          i = providerComponent.intents.size();
        } else {
          i = 0;
        } 
        paramParcel.writeInt(i);
        if (providerComponent.intents != null) {
          Iterator<ProviderIntentInfo> iterator1 = providerComponent.intents.iterator();
          while (iterator1.hasNext())
            ((ProviderIntentInfo)iterator1.next()).writeToParcel(paramParcel, paramInt); 
        } 
      } 
      paramParcel.writeInt(this.services.size());
      for (ServiceComponent serviceComponent : this.services) {
        paramParcel.writeParcelable((Parcelable)serviceComponent.info, 0);
        paramParcel.writeString(serviceComponent.className);
        paramParcel.writeBundle(serviceComponent.metaData);
        if (serviceComponent.intents != null) {
          i = serviceComponent.intents.size();
        } else {
          i = 0;
        } 
        paramParcel.writeInt(i);
        if (serviceComponent.intents != null) {
          Iterator<ServiceIntentInfo> iterator1 = serviceComponent.intents.iterator();
          while (iterator1.hasNext())
            ((ServiceIntentInfo)iterator1.next()).writeToParcel(paramParcel, paramInt); 
        } 
      } 
      paramParcel.writeInt(this.instrumentation.size());
      for (InstrumentationComponent instrumentationComponent : this.instrumentation) {
        paramParcel.writeParcelable((Parcelable)instrumentationComponent.info, 0);
        paramParcel.writeString(instrumentationComponent.className);
        paramParcel.writeBundle(instrumentationComponent.metaData);
        if (instrumentationComponent.intents != null) {
          i = instrumentationComponent.intents.size();
        } else {
          i = 0;
        } 
        paramParcel.writeInt(i);
        if (instrumentationComponent.intents != null) {
          Iterator<IntentInfo> iterator1 = instrumentationComponent.intents.iterator();
          while (iterator1.hasNext())
            ((IntentInfo)iterator1.next()).writeToParcel(paramParcel, paramInt); 
        } 
      } 
      paramParcel.writeInt(this.permissions.size());
      for (PermissionComponent permissionComponent : this.permissions) {
        paramParcel.writeParcelable((Parcelable)permissionComponent.info, 0);
        paramParcel.writeString(permissionComponent.className);
        paramParcel.writeBundle(permissionComponent.metaData);
        if (permissionComponent.intents != null) {
          i = permissionComponent.intents.size();
        } else {
          i = 0;
        } 
        paramParcel.writeInt(i);
        if (permissionComponent.intents != null) {
          Iterator<IntentInfo> iterator1 = permissionComponent.intents.iterator();
          while (iterator1.hasNext())
            ((IntentInfo)iterator1.next()).writeToParcel(paramParcel, paramInt); 
        } 
      } 
      paramParcel.writeInt(this.permissionGroups.size());
      for (PermissionGroupComponent permissionGroupComponent : this.permissionGroups) {
        paramParcel.writeParcelable((Parcelable)permissionGroupComponent.info, 0);
        paramParcel.writeString(permissionGroupComponent.className);
        paramParcel.writeBundle(permissionGroupComponent.metaData);
        if (permissionGroupComponent.intents != null) {
          i = permissionGroupComponent.intents.size();
        } else {
          i = 0;
        } 
        paramParcel.writeInt(i);
        if (permissionGroupComponent.intents != null) {
          Iterator<IntentInfo> iterator1 = permissionGroupComponent.intents.iterator();
          while (iterator1.hasNext())
            ((IntentInfo)iterator1.next()).writeToParcel(paramParcel, paramInt); 
        } 
      } 
      paramParcel.writeStringList(this.requestedPermissions);
      paramParcel.writeStringList(this.protectedBroadcasts);
      paramParcel.writeParcelable((Parcelable)this.applicationInfo, paramInt);
      paramParcel.writeBundle(this.mAppMetaData);
      paramParcel.writeString(this.packageName);
      paramParcel.writeInt(this.mPreferredOrder);
      paramParcel.writeString(this.mVersionName);
      paramParcel.writeString(this.mSharedUserId);
      paramParcel.writeStringList(this.usesLibraries);
      paramParcel.writeStringList(this.usesOptionalLibraries);
      paramParcel.writeInt(this.mVersionCode);
      paramParcel.writeInt(this.mSharedUserLabel);
      paramParcel.writeTypedList(this.configPreferences);
      paramParcel.writeTypedList(this.reqFeatures);
      paramParcel.writeStringList(this.splitNames);
      if (ApplicationInfoP.sharedLibraryInfos != null)
        paramParcel.writeTypedList(this.sharedLibraryInfos); 
      return;
    } 
  }
  
  public static class ActivityComponent extends Component<ActivityIntentInfo> {
    public ActivityInfo info;
    
    public ActivityComponent(PackageParser.Activity param1Activity) {
      super((PackageParser.Component)param1Activity);
      if (param1Activity.intents != null) {
        this.intents = new ArrayList<VPackage.ActivityIntentInfo>(param1Activity.intents.size());
        for (PackageParser.IntentInfo intentInfo : param1Activity.intents)
          this.intents.add(new VPackage.ActivityIntentInfo(intentInfo)); 
      } 
      this.info = param1Activity.info;
    }
    
    protected ActivityComponent(Parcel param1Parcel) {
      this.info = (ActivityInfo)param1Parcel.readParcelable(ActivityInfo.class.getClassLoader());
      this.className = param1Parcel.readString();
      this.metaData = param1Parcel.readBundle(Bundle.class.getClassLoader());
      int i = param1Parcel.readInt();
      this.intents = new ArrayList<VPackage.ActivityIntentInfo>(i);
      while (i > 0) {
        this.intents.add(new VPackage.ActivityIntentInfo(param1Parcel));
        i--;
      } 
    }
  }
  
  public static class ActivityIntentInfo extends IntentInfo {
    public VPackage.ActivityComponent activity;
    
    public ActivityIntentInfo(PackageParser.IntentInfo param1IntentInfo) {
      super(param1IntentInfo);
    }
    
    protected ActivityIntentInfo(Parcel param1Parcel) {
      super(param1Parcel);
    }
  }
  
  public static class Component<II extends IntentInfo> {
    public String className;
    
    private ComponentName componentName;
    
    public ArrayList<II> intents;
    
    public Bundle metaData;
    
    public VPackage owner;
    
    protected Component() {}
    
    public Component(PackageParser.Component param1Component) {
      this.className = param1Component.className;
      this.metaData = param1Component.metaData;
    }
    
    public ComponentName getComponentName() {
      ComponentName componentName = this.componentName;
      if (componentName != null)
        return componentName; 
      if (this.className != null)
        this.componentName = new ComponentName(this.owner.packageName, this.className); 
      return this.componentName;
    }
  }
  
  public static class InstrumentationComponent extends Component<IntentInfo> {
    public InstrumentationInfo info;
    
    public InstrumentationComponent(PackageParser.Instrumentation param1Instrumentation) {
      super((PackageParser.Component)param1Instrumentation);
      this.info = param1Instrumentation.info;
    }
    
    protected InstrumentationComponent(Parcel param1Parcel) {
      this.info = (InstrumentationInfo)param1Parcel.readParcelable(ActivityInfo.class.getClassLoader());
      this.className = param1Parcel.readString();
      this.metaData = param1Parcel.readBundle(Bundle.class.getClassLoader());
      int i = param1Parcel.readInt();
      this.intents = new ArrayList<VPackage.IntentInfo>(i);
      while (i > 0) {
        this.intents.add(new VPackage.IntentInfo(param1Parcel));
        i--;
      } 
    }
  }
  
  public static class IntentInfo implements Parcelable {
    public static final Parcelable.Creator<IntentInfo> CREATOR = new Parcelable.Creator<IntentInfo>() {
        public VPackage.IntentInfo createFromParcel(Parcel param2Parcel) {
          return new VPackage.IntentInfo(param2Parcel);
        }
        
        public VPackage.IntentInfo[] newArray(int param2Int) {
          return new VPackage.IntentInfo[param2Int];
        }
      };
    
    public int banner;
    
    public IntentFilter filter;
    
    public boolean hasDefault;
    
    public int icon;
    
    public int labelRes;
    
    public int logo;
    
    public String nonLocalizedLabel;
    
    public IntentInfo(PackageParser.IntentInfo param1IntentInfo) {
      this.filter = (IntentFilter)param1IntentInfo;
      this.hasDefault = param1IntentInfo.hasDefault;
      this.labelRes = param1IntentInfo.labelRes;
      if (param1IntentInfo.nonLocalizedLabel != null)
        this.nonLocalizedLabel = param1IntentInfo.nonLocalizedLabel.toString(); 
      this.icon = param1IntentInfo.icon;
      this.logo = param1IntentInfo.logo;
      this.banner = param1IntentInfo.banner;
    }
    
    protected IntentInfo(Parcel param1Parcel) {
      boolean bool;
      this.filter = (IntentFilter)param1Parcel.readParcelable(VPackage.class.getClassLoader());
      if (param1Parcel.readByte() != 0) {
        bool = true;
      } else {
        bool = false;
      } 
      this.hasDefault = bool;
      this.labelRes = param1Parcel.readInt();
      this.nonLocalizedLabel = param1Parcel.readString();
      this.icon = param1Parcel.readInt();
      this.logo = param1Parcel.readInt();
      this.banner = param1Parcel.readInt();
    }
    
    public int describeContents() {
      return 0;
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      param1Parcel.writeParcelable((Parcelable)this.filter, param1Int);
      param1Parcel.writeByte(this.hasDefault);
      param1Parcel.writeInt(this.labelRes);
      param1Parcel.writeString(this.nonLocalizedLabel);
      param1Parcel.writeInt(this.icon);
      param1Parcel.writeInt(this.logo);
      param1Parcel.writeInt(this.banner);
    }
  }
  
  class null implements Parcelable.Creator<IntentInfo> {
    public VPackage.IntentInfo createFromParcel(Parcel param1Parcel) {
      return new VPackage.IntentInfo(param1Parcel);
    }
    
    public VPackage.IntentInfo[] newArray(int param1Int) {
      return new VPackage.IntentInfo[param1Int];
    }
  }
  
  public static class PermissionComponent extends Component<IntentInfo> {
    public PermissionInfo info;
    
    public PermissionComponent(PackageParser.Permission param1Permission) {
      super((PackageParser.Component)param1Permission);
      this.info = param1Permission.info;
    }
    
    protected PermissionComponent(Parcel param1Parcel) {
      this.info = (PermissionInfo)param1Parcel.readParcelable(ActivityInfo.class.getClassLoader());
      this.className = param1Parcel.readString();
      this.metaData = param1Parcel.readBundle(Bundle.class.getClassLoader());
      int i = param1Parcel.readInt();
      this.intents = new ArrayList<VPackage.IntentInfo>(i);
      while (i > 0) {
        this.intents.add(new VPackage.IntentInfo(param1Parcel));
        i--;
      } 
    }
  }
  
  public static class PermissionGroupComponent extends Component<IntentInfo> {
    public PermissionGroupInfo info;
    
    public PermissionGroupComponent(PackageParser.PermissionGroup param1PermissionGroup) {
      super((PackageParser.Component)param1PermissionGroup);
      this.info = param1PermissionGroup.info;
    }
    
    protected PermissionGroupComponent(Parcel param1Parcel) {
      this.info = (PermissionGroupInfo)param1Parcel.readParcelable(ActivityInfo.class.getClassLoader());
      this.className = param1Parcel.readString();
      this.metaData = param1Parcel.readBundle(Bundle.class.getClassLoader());
      int i = param1Parcel.readInt();
      this.intents = new ArrayList<VPackage.IntentInfo>(i);
      while (i > 0) {
        this.intents.add(new VPackage.IntentInfo(param1Parcel));
        i--;
      } 
    }
  }
  
  public static class ProviderComponent extends Component<ProviderIntentInfo> {
    public ProviderInfo info;
    
    public ProviderComponent(PackageParser.Provider param1Provider) {
      super((PackageParser.Component)param1Provider);
      if (param1Provider.intents != null) {
        this.intents = new ArrayList<VPackage.ProviderIntentInfo>(param1Provider.intents.size());
        for (PackageParser.IntentInfo intentInfo : param1Provider.intents)
          this.intents.add(new VPackage.ProviderIntentInfo(intentInfo)); 
      } 
      this.info = param1Provider.info;
    }
    
    protected ProviderComponent(Parcel param1Parcel) {
      this.info = (ProviderInfo)param1Parcel.readParcelable(ActivityInfo.class.getClassLoader());
      this.className = param1Parcel.readString();
      this.metaData = param1Parcel.readBundle(Bundle.class.getClassLoader());
      int i = param1Parcel.readInt();
      this.intents = new ArrayList<VPackage.ProviderIntentInfo>(i);
      while (i > 0) {
        this.intents.add(new VPackage.ProviderIntentInfo(param1Parcel));
        i--;
      } 
    }
  }
  
  public static class ProviderIntentInfo extends IntentInfo {
    public VPackage.ProviderComponent provider;
    
    public ProviderIntentInfo(PackageParser.IntentInfo param1IntentInfo) {
      super(param1IntentInfo);
    }
    
    protected ProviderIntentInfo(Parcel param1Parcel) {
      super(param1Parcel);
    }
  }
  
  public static class ServiceComponent extends Component<ServiceIntentInfo> {
    public ServiceInfo info;
    
    public ServiceComponent(PackageParser.Service param1Service) {
      super((PackageParser.Component)param1Service);
      if (param1Service.intents != null) {
        this.intents = new ArrayList<VPackage.ServiceIntentInfo>(param1Service.intents.size());
        for (PackageParser.IntentInfo intentInfo : param1Service.intents)
          this.intents.add(new VPackage.ServiceIntentInfo(intentInfo)); 
      } 
      this.info = param1Service.info;
    }
    
    protected ServiceComponent(Parcel param1Parcel) {
      this.info = (ServiceInfo)param1Parcel.readParcelable(ActivityInfo.class.getClassLoader());
      this.className = param1Parcel.readString();
      this.metaData = param1Parcel.readBundle(Bundle.class.getClassLoader());
      int i = param1Parcel.readInt();
      this.intents = new ArrayList<VPackage.ServiceIntentInfo>(i);
      while (i > 0) {
        this.intents.add(new VPackage.ServiceIntentInfo(param1Parcel));
        i--;
      } 
    }
  }
  
  public static class ServiceIntentInfo extends IntentInfo {
    public VPackage.ServiceComponent service;
    
    public ServiceIntentInfo(PackageParser.IntentInfo param1IntentInfo) {
      super(param1IntentInfo);
    }
    
    protected ServiceIntentInfo(Parcel param1Parcel) {
      super(param1Parcel);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\pm\parser\VPackage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */