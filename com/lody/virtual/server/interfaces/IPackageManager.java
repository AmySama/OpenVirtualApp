package com.lody.virtual.server.interfaces;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.lody.virtual.remote.ReceiverInfo;
import com.lody.virtual.remote.VParceledListSlice;
import java.util.List;

public interface IPackageManager extends IInterface {
  boolean activitySupportsIntent(ComponentName paramComponentName, Intent paramIntent, String paramString) throws RemoteException;
  
  int checkPermission(boolean paramBoolean, String paramString1, String paramString2, int paramInt) throws RemoteException;
  
  int checkSignatures(String paramString1, String paramString2) throws RemoteException;
  
  ActivityInfo getActivityInfo(ComponentName paramComponentName, int paramInt1, int paramInt2) throws RemoteException;
  
  List<PermissionGroupInfo> getAllPermissionGroups(int paramInt) throws RemoteException;
  
  ApplicationInfo getApplicationInfo(String paramString, int paramInt1, int paramInt2) throws RemoteException;
  
  int getComponentEnabledSetting(ComponentName paramComponentName, int paramInt) throws RemoteException;
  
  String[] getDangerousPermissions(String paramString) throws RemoteException;
  
  VParceledListSlice getInstalledApplications(int paramInt1, int paramInt2) throws RemoteException;
  
  VParceledListSlice getInstalledPackages(int paramInt1, int paramInt2) throws RemoteException;
  
  String getNameForUid(int paramInt) throws RemoteException;
  
  PackageInfo getPackageInfo(String paramString, int paramInt1, int paramInt2) throws RemoteException;
  
  IBinder getPackageInstaller() throws RemoteException;
  
  int getPackageUid(String paramString, int paramInt) throws RemoteException;
  
  String[] getPackagesForUid(int paramInt) throws RemoteException;
  
  PermissionGroupInfo getPermissionGroupInfo(String paramString, int paramInt) throws RemoteException;
  
  PermissionInfo getPermissionInfo(String paramString, int paramInt) throws RemoteException;
  
  ProviderInfo getProviderInfo(ComponentName paramComponentName, int paramInt1, int paramInt2) throws RemoteException;
  
  ActivityInfo getReceiverInfo(ComponentName paramComponentName, int paramInt1, int paramInt2) throws RemoteException;
  
  List<ReceiverInfo> getReceiverInfos(String paramString1, String paramString2, int paramInt) throws RemoteException;
  
  ServiceInfo getServiceInfo(ComponentName paramComponentName, int paramInt1, int paramInt2) throws RemoteException;
  
  List<String> getSharedLibraries(String paramString) throws RemoteException;
  
  VParceledListSlice queryContentProviders(String paramString, int paramInt1, int paramInt2) throws RemoteException;
  
  List<ResolveInfo> queryIntentActivities(Intent paramIntent, String paramString, int paramInt1, int paramInt2) throws RemoteException;
  
  List<ResolveInfo> queryIntentContentProviders(Intent paramIntent, String paramString, int paramInt1, int paramInt2) throws RemoteException;
  
  List<ResolveInfo> queryIntentReceivers(Intent paramIntent, String paramString, int paramInt1, int paramInt2) throws RemoteException;
  
  List<ResolveInfo> queryIntentServices(Intent paramIntent, String paramString, int paramInt1, int paramInt2) throws RemoteException;
  
  List<PermissionInfo> queryPermissionsByGroup(String paramString, int paramInt) throws RemoteException;
  
  List<String> querySharedPackages(String paramString) throws RemoteException;
  
  ProviderInfo resolveContentProvider(String paramString, int paramInt1, int paramInt2) throws RemoteException;
  
  ResolveInfo resolveIntent(Intent paramIntent, String paramString, int paramInt1, int paramInt2) throws RemoteException;
  
  ResolveInfo resolveService(Intent paramIntent, String paramString, int paramInt1, int paramInt2) throws RemoteException;
  
  void setComponentEnabledSetting(ComponentName paramComponentName, int paramInt1, int paramInt2, int paramInt3) throws RemoteException;
  
  public static class Default implements IPackageManager {
    public boolean activitySupportsIntent(ComponentName param1ComponentName, Intent param1Intent, String param1String) throws RemoteException {
      return false;
    }
    
    public IBinder asBinder() {
      return null;
    }
    
    public int checkPermission(boolean param1Boolean, String param1String1, String param1String2, int param1Int) throws RemoteException {
      return 0;
    }
    
    public int checkSignatures(String param1String1, String param1String2) throws RemoteException {
      return 0;
    }
    
    public ActivityInfo getActivityInfo(ComponentName param1ComponentName, int param1Int1, int param1Int2) throws RemoteException {
      return null;
    }
    
    public List<PermissionGroupInfo> getAllPermissionGroups(int param1Int) throws RemoteException {
      return null;
    }
    
    public ApplicationInfo getApplicationInfo(String param1String, int param1Int1, int param1Int2) throws RemoteException {
      return null;
    }
    
    public int getComponentEnabledSetting(ComponentName param1ComponentName, int param1Int) throws RemoteException {
      return 0;
    }
    
    public String[] getDangerousPermissions(String param1String) throws RemoteException {
      return null;
    }
    
    public VParceledListSlice getInstalledApplications(int param1Int1, int param1Int2) throws RemoteException {
      return null;
    }
    
    public VParceledListSlice getInstalledPackages(int param1Int1, int param1Int2) throws RemoteException {
      return null;
    }
    
    public String getNameForUid(int param1Int) throws RemoteException {
      return null;
    }
    
    public PackageInfo getPackageInfo(String param1String, int param1Int1, int param1Int2) throws RemoteException {
      return null;
    }
    
    public IBinder getPackageInstaller() throws RemoteException {
      return null;
    }
    
    public int getPackageUid(String param1String, int param1Int) throws RemoteException {
      return 0;
    }
    
    public String[] getPackagesForUid(int param1Int) throws RemoteException {
      return null;
    }
    
    public PermissionGroupInfo getPermissionGroupInfo(String param1String, int param1Int) throws RemoteException {
      return null;
    }
    
    public PermissionInfo getPermissionInfo(String param1String, int param1Int) throws RemoteException {
      return null;
    }
    
    public ProviderInfo getProviderInfo(ComponentName param1ComponentName, int param1Int1, int param1Int2) throws RemoteException {
      return null;
    }
    
    public ActivityInfo getReceiverInfo(ComponentName param1ComponentName, int param1Int1, int param1Int2) throws RemoteException {
      return null;
    }
    
    public List<ReceiverInfo> getReceiverInfos(String param1String1, String param1String2, int param1Int) throws RemoteException {
      return null;
    }
    
    public ServiceInfo getServiceInfo(ComponentName param1ComponentName, int param1Int1, int param1Int2) throws RemoteException {
      return null;
    }
    
    public List<String> getSharedLibraries(String param1String) throws RemoteException {
      return null;
    }
    
    public VParceledListSlice queryContentProviders(String param1String, int param1Int1, int param1Int2) throws RemoteException {
      return null;
    }
    
    public List<ResolveInfo> queryIntentActivities(Intent param1Intent, String param1String, int param1Int1, int param1Int2) throws RemoteException {
      return null;
    }
    
    public List<ResolveInfo> queryIntentContentProviders(Intent param1Intent, String param1String, int param1Int1, int param1Int2) throws RemoteException {
      return null;
    }
    
    public List<ResolveInfo> queryIntentReceivers(Intent param1Intent, String param1String, int param1Int1, int param1Int2) throws RemoteException {
      return null;
    }
    
    public List<ResolveInfo> queryIntentServices(Intent param1Intent, String param1String, int param1Int1, int param1Int2) throws RemoteException {
      return null;
    }
    
    public List<PermissionInfo> queryPermissionsByGroup(String param1String, int param1Int) throws RemoteException {
      return null;
    }
    
    public List<String> querySharedPackages(String param1String) throws RemoteException {
      return null;
    }
    
    public ProviderInfo resolveContentProvider(String param1String, int param1Int1, int param1Int2) throws RemoteException {
      return null;
    }
    
    public ResolveInfo resolveIntent(Intent param1Intent, String param1String, int param1Int1, int param1Int2) throws RemoteException {
      return null;
    }
    
    public ResolveInfo resolveService(Intent param1Intent, String param1String, int param1Int1, int param1Int2) throws RemoteException {
      return null;
    }
    
    public void setComponentEnabledSetting(ComponentName param1ComponentName, int param1Int1, int param1Int2, int param1Int3) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements IPackageManager {
    private static final String DESCRIPTOR = "com.lody.virtual.server.interfaces.IPackageManager";
    
    static final int TRANSACTION_activitySupportsIntent = 7;
    
    static final int TRANSACTION_checkPermission = 4;
    
    static final int TRANSACTION_checkSignatures = 30;
    
    static final int TRANSACTION_getActivityInfo = 6;
    
    static final int TRANSACTION_getAllPermissionGroups = 23;
    
    static final int TRANSACTION_getApplicationInfo = 25;
    
    static final int TRANSACTION_getComponentEnabledSetting = 33;
    
    static final int TRANSACTION_getDangerousPermissions = 31;
    
    static final int TRANSACTION_getInstalledApplications = 18;
    
    static final int TRANSACTION_getInstalledPackages = 17;
    
    static final int TRANSACTION_getNameForUid = 28;
    
    static final int TRANSACTION_getPackageInfo = 5;
    
    static final int TRANSACTION_getPackageInstaller = 29;
    
    static final int TRANSACTION_getPackageUid = 1;
    
    static final int TRANSACTION_getPackagesForUid = 2;
    
    static final int TRANSACTION_getPermissionGroupInfo = 22;
    
    static final int TRANSACTION_getPermissionInfo = 20;
    
    static final int TRANSACTION_getProviderInfo = 10;
    
    static final int TRANSACTION_getReceiverInfo = 8;
    
    static final int TRANSACTION_getReceiverInfos = 19;
    
    static final int TRANSACTION_getServiceInfo = 9;
    
    static final int TRANSACTION_getSharedLibraries = 3;
    
    static final int TRANSACTION_queryContentProviders = 26;
    
    static final int TRANSACTION_queryIntentActivities = 12;
    
    static final int TRANSACTION_queryIntentContentProviders = 16;
    
    static final int TRANSACTION_queryIntentReceivers = 13;
    
    static final int TRANSACTION_queryIntentServices = 15;
    
    static final int TRANSACTION_queryPermissionsByGroup = 21;
    
    static final int TRANSACTION_querySharedPackages = 27;
    
    static final int TRANSACTION_resolveContentProvider = 24;
    
    static final int TRANSACTION_resolveIntent = 11;
    
    static final int TRANSACTION_resolveService = 14;
    
    static final int TRANSACTION_setComponentEnabledSetting = 32;
    
    public Stub() {
      attachInterface(this, "com.lody.virtual.server.interfaces.IPackageManager");
    }
    
    public static IPackageManager asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("com.lody.virtual.server.interfaces.IPackageManager");
      return (iInterface != null && iInterface instanceof IPackageManager) ? (IPackageManager)iInterface : new Proxy(param1IBinder);
    }
    
    public static IPackageManager getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IPackageManager param1IPackageManager) {
      if (Proxy.sDefaultImpl == null && param1IPackageManager != null) {
        Proxy.sDefaultImpl = param1IPackageManager;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      if (param1Int1 != 1598968902) {
        boolean bool;
        String[] arrayOfString2;
        IBinder iBinder;
        String str;
        List<String> list6;
        VParceledListSlice vParceledListSlice2;
        ApplicationInfo applicationInfo;
        ProviderInfo providerInfo2;
        List<PermissionGroupInfo> list5;
        PermissionGroupInfo permissionGroupInfo;
        List<PermissionInfo> list4;
        PermissionInfo permissionInfo;
        List<ReceiverInfo> list;
        VParceledListSlice vParceledListSlice1;
        List<ResolveInfo> list3;
        ResolveInfo resolveInfo2;
        List<ResolveInfo> list2;
        ResolveInfo resolveInfo1;
        ProviderInfo providerInfo1;
        ServiceInfo serviceInfo;
        ActivityInfo activityInfo;
        PackageInfo packageInfo;
        List<String> list1;
        String[] arrayOfString1;
        Intent intent8;
        ComponentName componentName6;
        ComponentName componentName1 = null;
        ComponentName componentName2 = null;
        Intent intent1 = null;
        Intent intent2 = null;
        Intent intent3 = null;
        Intent intent4 = null;
        Intent intent5 = null;
        Intent intent6 = null;
        ComponentName componentName3 = null;
        ComponentName componentName4 = null;
        Intent intent7 = null;
        ComponentName componentName5 = null;
        ComponentName componentName7 = null;
        boolean bool1 = false;
        switch (param1Int1) {
          default:
            return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2);
          case 33:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            if (param1Parcel1.readInt() != 0)
              componentName7 = (ComponentName)ComponentName.CREATOR.createFromParcel(param1Parcel1); 
            param1Int1 = getComponentEnabledSetting(componentName7, param1Parcel1.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(param1Int1);
            return true;
          case 32:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            componentName7 = componentName1;
            if (param1Parcel1.readInt() != 0)
              componentName7 = (ComponentName)ComponentName.CREATOR.createFromParcel(param1Parcel1); 
            setComponentEnabledSetting(componentName7, param1Parcel1.readInt(), param1Parcel1.readInt(), param1Parcel1.readInt());
            param1Parcel2.writeNoException();
            return true;
          case 31:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            arrayOfString2 = getDangerousPermissions(param1Parcel1.readString());
            param1Parcel2.writeNoException();
            param1Parcel2.writeStringArray(arrayOfString2);
            return true;
          case 30:
            arrayOfString2.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            param1Int1 = checkSignatures(arrayOfString2.readString(), arrayOfString2.readString());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(param1Int1);
            return true;
          case 29:
            arrayOfString2.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            iBinder = getPackageInstaller();
            param1Parcel2.writeNoException();
            param1Parcel2.writeStrongBinder(iBinder);
            return true;
          case 28:
            iBinder.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            str = getNameForUid(iBinder.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeString(str);
            return true;
          case 27:
            str.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            list6 = querySharedPackages(str.readString());
            param1Parcel2.writeNoException();
            param1Parcel2.writeStringList(list6);
            return true;
          case 26:
            list6.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            vParceledListSlice2 = queryContentProviders(list6.readString(), list6.readInt(), list6.readInt());
            param1Parcel2.writeNoException();
            if (vParceledListSlice2 != null) {
              param1Parcel2.writeInt(1);
              vParceledListSlice2.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 25:
            vParceledListSlice2.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            applicationInfo = getApplicationInfo(vParceledListSlice2.readString(), vParceledListSlice2.readInt(), vParceledListSlice2.readInt());
            param1Parcel2.writeNoException();
            if (applicationInfo != null) {
              param1Parcel2.writeInt(1);
              applicationInfo.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 24:
            applicationInfo.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            providerInfo2 = resolveContentProvider(applicationInfo.readString(), applicationInfo.readInt(), applicationInfo.readInt());
            param1Parcel2.writeNoException();
            if (providerInfo2 != null) {
              param1Parcel2.writeInt(1);
              providerInfo2.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 23:
            providerInfo2.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            list5 = getAllPermissionGroups(providerInfo2.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeTypedList(list5);
            return true;
          case 22:
            list5.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            permissionGroupInfo = getPermissionGroupInfo(list5.readString(), list5.readInt());
            param1Parcel2.writeNoException();
            if (permissionGroupInfo != null) {
              param1Parcel2.writeInt(1);
              permissionGroupInfo.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 21:
            permissionGroupInfo.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            list4 = queryPermissionsByGroup(permissionGroupInfo.readString(), permissionGroupInfo.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeTypedList(list4);
            return true;
          case 20:
            list4.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            permissionInfo = getPermissionInfo(list4.readString(), list4.readInt());
            param1Parcel2.writeNoException();
            if (permissionInfo != null) {
              param1Parcel2.writeInt(1);
              permissionInfo.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 19:
            permissionInfo.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            list = getReceiverInfos(permissionInfo.readString(), permissionInfo.readString(), permissionInfo.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeTypedList(list);
            return true;
          case 18:
            list.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            vParceledListSlice1 = getInstalledApplications(list.readInt(), list.readInt());
            param1Parcel2.writeNoException();
            if (vParceledListSlice1 != null) {
              param1Parcel2.writeInt(1);
              vParceledListSlice1.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 17:
            vParceledListSlice1.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            vParceledListSlice1 = getInstalledPackages(vParceledListSlice1.readInt(), vParceledListSlice1.readInt());
            param1Parcel2.writeNoException();
            if (vParceledListSlice1 != null) {
              param1Parcel2.writeInt(1);
              vParceledListSlice1.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 16:
            vParceledListSlice1.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            componentName7 = componentName2;
            if (vParceledListSlice1.readInt() != 0)
              intent8 = (Intent)Intent.CREATOR.createFromParcel((Parcel)vParceledListSlice1); 
            list3 = queryIntentContentProviders(intent8, vParceledListSlice1.readString(), vParceledListSlice1.readInt(), vParceledListSlice1.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeTypedList(list3);
            return true;
          case 15:
            list3.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            intent8 = intent1;
            if (list3.readInt() != 0)
              intent8 = (Intent)Intent.CREATOR.createFromParcel((Parcel)list3); 
            list3 = queryIntentServices(intent8, list3.readString(), list3.readInt(), list3.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeTypedList(list3);
            return true;
          case 14:
            list3.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            intent8 = intent2;
            if (list3.readInt() != 0)
              intent8 = (Intent)Intent.CREATOR.createFromParcel((Parcel)list3); 
            resolveInfo2 = resolveService(intent8, list3.readString(), list3.readInt(), list3.readInt());
            param1Parcel2.writeNoException();
            if (resolveInfo2 != null) {
              param1Parcel2.writeInt(1);
              resolveInfo2.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 13:
            resolveInfo2.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            intent8 = intent3;
            if (resolveInfo2.readInt() != 0)
              intent8 = (Intent)Intent.CREATOR.createFromParcel((Parcel)resolveInfo2); 
            list2 = queryIntentReceivers(intent8, resolveInfo2.readString(), resolveInfo2.readInt(), resolveInfo2.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeTypedList(list2);
            return true;
          case 12:
            list2.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            intent8 = intent4;
            if (list2.readInt() != 0)
              intent8 = (Intent)Intent.CREATOR.createFromParcel((Parcel)list2); 
            list2 = queryIntentActivities(intent8, list2.readString(), list2.readInt(), list2.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeTypedList(list2);
            return true;
          case 11:
            list2.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            intent8 = intent5;
            if (list2.readInt() != 0)
              intent8 = (Intent)Intent.CREATOR.createFromParcel((Parcel)list2); 
            resolveInfo1 = resolveIntent(intent8, list2.readString(), list2.readInt(), list2.readInt());
            param1Parcel2.writeNoException();
            if (resolveInfo1 != null) {
              param1Parcel2.writeInt(1);
              resolveInfo1.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 10:
            resolveInfo1.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            intent8 = intent6;
            if (resolveInfo1.readInt() != 0)
              componentName6 = (ComponentName)ComponentName.CREATOR.createFromParcel((Parcel)resolveInfo1); 
            providerInfo1 = getProviderInfo(componentName6, resolveInfo1.readInt(), resolveInfo1.readInt());
            param1Parcel2.writeNoException();
            if (providerInfo1 != null) {
              param1Parcel2.writeInt(1);
              providerInfo1.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 9:
            providerInfo1.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            componentName6 = componentName3;
            if (providerInfo1.readInt() != 0)
              componentName6 = (ComponentName)ComponentName.CREATOR.createFromParcel((Parcel)providerInfo1); 
            serviceInfo = getServiceInfo(componentName6, providerInfo1.readInt(), providerInfo1.readInt());
            param1Parcel2.writeNoException();
            if (serviceInfo != null) {
              param1Parcel2.writeInt(1);
              serviceInfo.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 8:
            serviceInfo.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            componentName6 = componentName4;
            if (serviceInfo.readInt() != 0)
              componentName6 = (ComponentName)ComponentName.CREATOR.createFromParcel((Parcel)serviceInfo); 
            activityInfo = getReceiverInfo(componentName6, serviceInfo.readInt(), serviceInfo.readInt());
            param1Parcel2.writeNoException();
            if (activityInfo != null) {
              param1Parcel2.writeInt(1);
              activityInfo.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 7:
            activityInfo.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            if (activityInfo.readInt() != 0) {
              componentName6 = (ComponentName)ComponentName.CREATOR.createFromParcel((Parcel)activityInfo);
            } else {
              componentName6 = null;
            } 
            if (activityInfo.readInt() != 0)
              intent7 = (Intent)Intent.CREATOR.createFromParcel((Parcel)activityInfo); 
            bool = activitySupportsIntent(componentName6, intent7, activityInfo.readString());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool);
            return true;
          case 6:
            activityInfo.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            componentName6 = componentName5;
            if (activityInfo.readInt() != 0)
              componentName6 = (ComponentName)ComponentName.CREATOR.createFromParcel((Parcel)activityInfo); 
            activityInfo = getActivityInfo(componentName6, activityInfo.readInt(), activityInfo.readInt());
            param1Parcel2.writeNoException();
            if (activityInfo != null) {
              param1Parcel2.writeInt(1);
              activityInfo.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 5:
            activityInfo.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            packageInfo = getPackageInfo(activityInfo.readString(), activityInfo.readInt(), activityInfo.readInt());
            param1Parcel2.writeNoException();
            if (packageInfo != null) {
              param1Parcel2.writeInt(1);
              packageInfo.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 4:
            packageInfo.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            if (packageInfo.readInt() != 0)
              bool1 = true; 
            i = checkPermission(bool1, packageInfo.readString(), packageInfo.readString(), packageInfo.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(i);
            return true;
          case 3:
            packageInfo.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            list1 = getSharedLibraries(packageInfo.readString());
            param1Parcel2.writeNoException();
            param1Parcel2.writeStringList(list1);
            return true;
          case 2:
            list1.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
            arrayOfString1 = getPackagesForUid(list1.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeStringArray(arrayOfString1);
            return true;
          case 1:
            break;
        } 
        arrayOfString1.enforceInterface("com.lody.virtual.server.interfaces.IPackageManager");
        int i = getPackageUid(arrayOfString1.readString(), arrayOfString1.readInt());
        param1Parcel2.writeNoException();
        param1Parcel2.writeInt(i);
        return true;
      } 
      param1Parcel2.writeString("com.lody.virtual.server.interfaces.IPackageManager");
      return true;
    }
    
    private static class Proxy implements IPackageManager {
      public static IPackageManager sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public boolean activitySupportsIntent(ComponentName param2ComponentName, Intent param2Intent, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          boolean bool = true;
          if (param2ComponentName != null) {
            parcel1.writeInt(1);
            param2ComponentName.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (param2Intent != null) {
            parcel1.writeInt(1);
            param2Intent.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(7, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null) {
            bool = IPackageManager.Stub.getDefaultImpl().activitySupportsIntent(param2ComponentName, param2Intent, param2String);
            return bool;
          } 
          parcel2.readException();
          int i = parcel2.readInt();
          if (i == 0)
            bool = false; 
          return bool;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public int checkPermission(boolean param2Boolean, String param2String1, String param2String2, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          boolean bool;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          if (param2Boolean) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          parcel1.writeString(param2String1);
          parcel1.writeString(param2String2);
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null) {
            param2Int = IPackageManager.Stub.getDefaultImpl().checkPermission(param2Boolean, param2String1, param2String2, param2Int);
            return param2Int;
          } 
          parcel2.readException();
          param2Int = parcel2.readInt();
          return param2Int;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public int checkSignatures(String param2String1, String param2String2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          parcel1.writeString(param2String1);
          parcel1.writeString(param2String2);
          if (!this.mRemote.transact(30, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
            return IPackageManager.Stub.getDefaultImpl().checkSignatures(param2String1, param2String2); 
          parcel2.readException();
          return parcel2.readInt();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public ActivityInfo getActivityInfo(ComponentName param2ComponentName, int param2Int1, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          if (param2ComponentName != null) {
            parcel1.writeInt(1);
            param2ComponentName.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeInt(param2Int1);
          parcel1.writeInt(param2Int2);
          if (!this.mRemote.transact(6, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
            return IPackageManager.Stub.getDefaultImpl().getActivityInfo(param2ComponentName, param2Int1, param2Int2); 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            ActivityInfo activityInfo = (ActivityInfo)ActivityInfo.CREATOR.createFromParcel(parcel2);
          } else {
            param2ComponentName = null;
          } 
          return (ActivityInfo)param2ComponentName;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public List<PermissionGroupInfo> getAllPermissionGroups(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(23, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
            return IPackageManager.Stub.getDefaultImpl().getAllPermissionGroups(param2Int); 
          parcel2.readException();
          return parcel2.createTypedArrayList(PermissionGroupInfo.CREATOR);
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public ApplicationInfo getApplicationInfo(String param2String, int param2Int1, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int1);
          parcel1.writeInt(param2Int2);
          if (!this.mRemote.transact(25, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
            return IPackageManager.Stub.getDefaultImpl().getApplicationInfo(param2String, param2Int1, param2Int2); 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            ApplicationInfo applicationInfo = (ApplicationInfo)ApplicationInfo.CREATOR.createFromParcel(parcel2);
          } else {
            param2String = null;
          } 
          return (ApplicationInfo)param2String;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public int getComponentEnabledSetting(ComponentName param2ComponentName, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          if (param2ComponentName != null) {
            parcel1.writeInt(1);
            param2ComponentName.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(33, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null) {
            param2Int = IPackageManager.Stub.getDefaultImpl().getComponentEnabledSetting(param2ComponentName, param2Int);
            return param2Int;
          } 
          parcel2.readException();
          param2Int = parcel2.readInt();
          return param2Int;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String[] getDangerousPermissions(String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(31, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
            return IPackageManager.Stub.getDefaultImpl().getDangerousPermissions(param2String); 
          parcel2.readException();
          return parcel2.createStringArray();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public VParceledListSlice getInstalledApplications(int param2Int1, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          VParceledListSlice vParceledListSlice;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          parcel1.writeInt(param2Int1);
          parcel1.writeInt(param2Int2);
          if (!this.mRemote.transact(18, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null) {
            vParceledListSlice = IPackageManager.Stub.getDefaultImpl().getInstalledApplications(param2Int1, param2Int2);
            return vParceledListSlice;
          } 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            vParceledListSlice = (VParceledListSlice)VParceledListSlice.CREATOR.createFromParcel(parcel2);
          } else {
            vParceledListSlice = null;
          } 
          return vParceledListSlice;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public VParceledListSlice getInstalledPackages(int param2Int1, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          VParceledListSlice vParceledListSlice;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          parcel1.writeInt(param2Int1);
          parcel1.writeInt(param2Int2);
          if (!this.mRemote.transact(17, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null) {
            vParceledListSlice = IPackageManager.Stub.getDefaultImpl().getInstalledPackages(param2Int1, param2Int2);
            return vParceledListSlice;
          } 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            vParceledListSlice = (VParceledListSlice)VParceledListSlice.CREATOR.createFromParcel(parcel2);
          } else {
            vParceledListSlice = null;
          } 
          return vParceledListSlice;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String getInterfaceDescriptor() {
        return "com.lody.virtual.server.interfaces.IPackageManager";
      }
      
      public String getNameForUid(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(28, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
            return IPackageManager.Stub.getDefaultImpl().getNameForUid(param2Int); 
          parcel2.readException();
          return parcel2.readString();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public PackageInfo getPackageInfo(String param2String, int param2Int1, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int1);
          parcel1.writeInt(param2Int2);
          if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
            return IPackageManager.Stub.getDefaultImpl().getPackageInfo(param2String, param2Int1, param2Int2); 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            PackageInfo packageInfo = (PackageInfo)PackageInfo.CREATOR.createFromParcel(parcel2);
          } else {
            param2String = null;
          } 
          return (PackageInfo)param2String;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public IBinder getPackageInstaller() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          if (!this.mRemote.transact(29, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
            return IPackageManager.Stub.getDefaultImpl().getPackageInstaller(); 
          parcel2.readException();
          return parcel2.readStrongBinder();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public int getPackageUid(String param2String, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null) {
            param2Int = IPackageManager.Stub.getDefaultImpl().getPackageUid(param2String, param2Int);
            return param2Int;
          } 
          parcel2.readException();
          param2Int = parcel2.readInt();
          return param2Int;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String[] getPackagesForUid(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
            return IPackageManager.Stub.getDefaultImpl().getPackagesForUid(param2Int); 
          parcel2.readException();
          return parcel2.createStringArray();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public PermissionGroupInfo getPermissionGroupInfo(String param2String, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(22, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
            return IPackageManager.Stub.getDefaultImpl().getPermissionGroupInfo(param2String, param2Int); 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            PermissionGroupInfo permissionGroupInfo = (PermissionGroupInfo)PermissionGroupInfo.CREATOR.createFromParcel(parcel2);
          } else {
            param2String = null;
          } 
          return (PermissionGroupInfo)param2String;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public PermissionInfo getPermissionInfo(String param2String, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(20, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
            return IPackageManager.Stub.getDefaultImpl().getPermissionInfo(param2String, param2Int); 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            PermissionInfo permissionInfo = (PermissionInfo)PermissionInfo.CREATOR.createFromParcel(parcel2);
          } else {
            param2String = null;
          } 
          return (PermissionInfo)param2String;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public ProviderInfo getProviderInfo(ComponentName param2ComponentName, int param2Int1, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          if (param2ComponentName != null) {
            parcel1.writeInt(1);
            param2ComponentName.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeInt(param2Int1);
          parcel1.writeInt(param2Int2);
          if (!this.mRemote.transact(10, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
            return IPackageManager.Stub.getDefaultImpl().getProviderInfo(param2ComponentName, param2Int1, param2Int2); 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            ProviderInfo providerInfo = (ProviderInfo)ProviderInfo.CREATOR.createFromParcel(parcel2);
          } else {
            param2ComponentName = null;
          } 
          return (ProviderInfo)param2ComponentName;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public ActivityInfo getReceiverInfo(ComponentName param2ComponentName, int param2Int1, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          if (param2ComponentName != null) {
            parcel1.writeInt(1);
            param2ComponentName.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeInt(param2Int1);
          parcel1.writeInt(param2Int2);
          if (!this.mRemote.transact(8, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
            return IPackageManager.Stub.getDefaultImpl().getReceiverInfo(param2ComponentName, param2Int1, param2Int2); 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            ActivityInfo activityInfo = (ActivityInfo)ActivityInfo.CREATOR.createFromParcel(parcel2);
          } else {
            param2ComponentName = null;
          } 
          return (ActivityInfo)param2ComponentName;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public List<ReceiverInfo> getReceiverInfos(String param2String1, String param2String2, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          parcel1.writeString(param2String1);
          parcel1.writeString(param2String2);
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(19, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
            return IPackageManager.Stub.getDefaultImpl().getReceiverInfos(param2String1, param2String2, param2Int); 
          parcel2.readException();
          return parcel2.createTypedArrayList(ReceiverInfo.CREATOR);
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public ServiceInfo getServiceInfo(ComponentName param2ComponentName, int param2Int1, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          if (param2ComponentName != null) {
            parcel1.writeInt(1);
            param2ComponentName.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeInt(param2Int1);
          parcel1.writeInt(param2Int2);
          if (!this.mRemote.transact(9, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
            return IPackageManager.Stub.getDefaultImpl().getServiceInfo(param2ComponentName, param2Int1, param2Int2); 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            ServiceInfo serviceInfo = (ServiceInfo)ServiceInfo.CREATOR.createFromParcel(parcel2);
          } else {
            param2ComponentName = null;
          } 
          return (ServiceInfo)param2ComponentName;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public List<String> getSharedLibraries(String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
            return IPackageManager.Stub.getDefaultImpl().getSharedLibraries(param2String); 
          parcel2.readException();
          return parcel2.createStringArrayList();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public VParceledListSlice queryContentProviders(String param2String, int param2Int1, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int1);
          parcel1.writeInt(param2Int2);
          if (!this.mRemote.transact(26, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
            return IPackageManager.Stub.getDefaultImpl().queryContentProviders(param2String, param2Int1, param2Int2); 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            VParceledListSlice vParceledListSlice = (VParceledListSlice)VParceledListSlice.CREATOR.createFromParcel(parcel2);
          } else {
            param2String = null;
          } 
          return (VParceledListSlice)param2String;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public List<ResolveInfo> queryIntentActivities(Intent param2Intent, String param2String, int param2Int1, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          if (param2Intent != null) {
            parcel1.writeInt(1);
            param2Intent.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int1);
          parcel1.writeInt(param2Int2);
          if (!this.mRemote.transact(12, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
            return IPackageManager.Stub.getDefaultImpl().queryIntentActivities(param2Intent, param2String, param2Int1, param2Int2); 
          parcel2.readException();
          return parcel2.createTypedArrayList(ResolveInfo.CREATOR);
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public List<ResolveInfo> queryIntentContentProviders(Intent param2Intent, String param2String, int param2Int1, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          if (param2Intent != null) {
            parcel1.writeInt(1);
            param2Intent.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int1);
          parcel1.writeInt(param2Int2);
          if (!this.mRemote.transact(16, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
            return IPackageManager.Stub.getDefaultImpl().queryIntentContentProviders(param2Intent, param2String, param2Int1, param2Int2); 
          parcel2.readException();
          return parcel2.createTypedArrayList(ResolveInfo.CREATOR);
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public List<ResolveInfo> queryIntentReceivers(Intent param2Intent, String param2String, int param2Int1, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          if (param2Intent != null) {
            parcel1.writeInt(1);
            param2Intent.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int1);
          parcel1.writeInt(param2Int2);
          if (!this.mRemote.transact(13, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
            return IPackageManager.Stub.getDefaultImpl().queryIntentReceivers(param2Intent, param2String, param2Int1, param2Int2); 
          parcel2.readException();
          return parcel2.createTypedArrayList(ResolveInfo.CREATOR);
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public List<ResolveInfo> queryIntentServices(Intent param2Intent, String param2String, int param2Int1, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          if (param2Intent != null) {
            parcel1.writeInt(1);
            param2Intent.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int1);
          parcel1.writeInt(param2Int2);
          if (!this.mRemote.transact(15, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
            return IPackageManager.Stub.getDefaultImpl().queryIntentServices(param2Intent, param2String, param2Int1, param2Int2); 
          parcel2.readException();
          return parcel2.createTypedArrayList(ResolveInfo.CREATOR);
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public List<PermissionInfo> queryPermissionsByGroup(String param2String, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(21, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
            return IPackageManager.Stub.getDefaultImpl().queryPermissionsByGroup(param2String, param2Int); 
          parcel2.readException();
          return parcel2.createTypedArrayList(PermissionInfo.CREATOR);
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public List<String> querySharedPackages(String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(27, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
            return IPackageManager.Stub.getDefaultImpl().querySharedPackages(param2String); 
          parcel2.readException();
          return parcel2.createStringArrayList();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public ProviderInfo resolveContentProvider(String param2String, int param2Int1, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int1);
          parcel1.writeInt(param2Int2);
          if (!this.mRemote.transact(24, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
            return IPackageManager.Stub.getDefaultImpl().resolveContentProvider(param2String, param2Int1, param2Int2); 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            ProviderInfo providerInfo = (ProviderInfo)ProviderInfo.CREATOR.createFromParcel(parcel2);
          } else {
            param2String = null;
          } 
          return (ProviderInfo)param2String;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public ResolveInfo resolveIntent(Intent param2Intent, String param2String, int param2Int1, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          if (param2Intent != null) {
            parcel1.writeInt(1);
            param2Intent.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int1);
          parcel1.writeInt(param2Int2);
          if (!this.mRemote.transact(11, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
            return IPackageManager.Stub.getDefaultImpl().resolveIntent(param2Intent, param2String, param2Int1, param2Int2); 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            ResolveInfo resolveInfo = (ResolveInfo)ResolveInfo.CREATOR.createFromParcel(parcel2);
          } else {
            param2Intent = null;
          } 
          return (ResolveInfo)param2Intent;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public ResolveInfo resolveService(Intent param2Intent, String param2String, int param2Int1, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          if (param2Intent != null) {
            parcel1.writeInt(1);
            param2Intent.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int1);
          parcel1.writeInt(param2Int2);
          if (!this.mRemote.transact(14, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
            return IPackageManager.Stub.getDefaultImpl().resolveService(param2Intent, param2String, param2Int1, param2Int2); 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            ResolveInfo resolveInfo = (ResolveInfo)ResolveInfo.CREATOR.createFromParcel(parcel2);
          } else {
            param2Intent = null;
          } 
          return (ResolveInfo)param2Intent;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setComponentEnabledSetting(ComponentName param2ComponentName, int param2Int1, int param2Int2, int param2Int3) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
          if (param2ComponentName != null) {
            parcel1.writeInt(1);
            param2ComponentName.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeInt(param2Int1);
          parcel1.writeInt(param2Int2);
          parcel1.writeInt(param2Int3);
          if (!this.mRemote.transact(32, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null) {
            IPackageManager.Stub.getDefaultImpl().setComponentEnabledSetting(param2ComponentName, param2Int1, param2Int2, param2Int3);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
    }
  }
  
  private static class Proxy implements IPackageManager {
    public static IPackageManager sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public boolean activitySupportsIntent(ComponentName param1ComponentName, Intent param1Intent, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        boolean bool = true;
        if (param1ComponentName != null) {
          parcel1.writeInt(1);
          param1ComponentName.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (param1Intent != null) {
          parcel1.writeInt(1);
          param1Intent.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(7, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null) {
          bool = IPackageManager.Stub.getDefaultImpl().activitySupportsIntent(param1ComponentName, param1Intent, param1String);
          return bool;
        } 
        parcel2.readException();
        int i = parcel2.readInt();
        if (i == 0)
          bool = false; 
        return bool;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public int checkPermission(boolean param1Boolean, String param1String1, String param1String2, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        boolean bool;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        if (param1Boolean) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        parcel1.writeString(param1String1);
        parcel1.writeString(param1String2);
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null) {
          param1Int = IPackageManager.Stub.getDefaultImpl().checkPermission(param1Boolean, param1String1, param1String2, param1Int);
          return param1Int;
        } 
        parcel2.readException();
        param1Int = parcel2.readInt();
        return param1Int;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public int checkSignatures(String param1String1, String param1String2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        parcel1.writeString(param1String1);
        parcel1.writeString(param1String2);
        if (!this.mRemote.transact(30, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
          return IPackageManager.Stub.getDefaultImpl().checkSignatures(param1String1, param1String2); 
        parcel2.readException();
        return parcel2.readInt();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public ActivityInfo getActivityInfo(ComponentName param1ComponentName, int param1Int1, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        if (param1ComponentName != null) {
          parcel1.writeInt(1);
          param1ComponentName.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeInt(param1Int1);
        parcel1.writeInt(param1Int2);
        if (!this.mRemote.transact(6, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
          return IPackageManager.Stub.getDefaultImpl().getActivityInfo(param1ComponentName, param1Int1, param1Int2); 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          ActivityInfo activityInfo = (ActivityInfo)ActivityInfo.CREATOR.createFromParcel(parcel2);
        } else {
          param1ComponentName = null;
        } 
        return (ActivityInfo)param1ComponentName;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public List<PermissionGroupInfo> getAllPermissionGroups(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(23, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
          return IPackageManager.Stub.getDefaultImpl().getAllPermissionGroups(param1Int); 
        parcel2.readException();
        return parcel2.createTypedArrayList(PermissionGroupInfo.CREATOR);
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public ApplicationInfo getApplicationInfo(String param1String, int param1Int1, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int1);
        parcel1.writeInt(param1Int2);
        if (!this.mRemote.transact(25, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
          return IPackageManager.Stub.getDefaultImpl().getApplicationInfo(param1String, param1Int1, param1Int2); 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          ApplicationInfo applicationInfo = (ApplicationInfo)ApplicationInfo.CREATOR.createFromParcel(parcel2);
        } else {
          param1String = null;
        } 
        return (ApplicationInfo)param1String;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public int getComponentEnabledSetting(ComponentName param1ComponentName, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        if (param1ComponentName != null) {
          parcel1.writeInt(1);
          param1ComponentName.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(33, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null) {
          param1Int = IPackageManager.Stub.getDefaultImpl().getComponentEnabledSetting(param1ComponentName, param1Int);
          return param1Int;
        } 
        parcel2.readException();
        param1Int = parcel2.readInt();
        return param1Int;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String[] getDangerousPermissions(String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(31, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
          return IPackageManager.Stub.getDefaultImpl().getDangerousPermissions(param1String); 
        parcel2.readException();
        return parcel2.createStringArray();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public VParceledListSlice getInstalledApplications(int param1Int1, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        VParceledListSlice vParceledListSlice;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        parcel1.writeInt(param1Int1);
        parcel1.writeInt(param1Int2);
        if (!this.mRemote.transact(18, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null) {
          vParceledListSlice = IPackageManager.Stub.getDefaultImpl().getInstalledApplications(param1Int1, param1Int2);
          return vParceledListSlice;
        } 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          vParceledListSlice = (VParceledListSlice)VParceledListSlice.CREATOR.createFromParcel(parcel2);
        } else {
          vParceledListSlice = null;
        } 
        return vParceledListSlice;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public VParceledListSlice getInstalledPackages(int param1Int1, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        VParceledListSlice vParceledListSlice;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        parcel1.writeInt(param1Int1);
        parcel1.writeInt(param1Int2);
        if (!this.mRemote.transact(17, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null) {
          vParceledListSlice = IPackageManager.Stub.getDefaultImpl().getInstalledPackages(param1Int1, param1Int2);
          return vParceledListSlice;
        } 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          vParceledListSlice = (VParceledListSlice)VParceledListSlice.CREATOR.createFromParcel(parcel2);
        } else {
          vParceledListSlice = null;
        } 
        return vParceledListSlice;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String getInterfaceDescriptor() {
      return "com.lody.virtual.server.interfaces.IPackageManager";
    }
    
    public String getNameForUid(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(28, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
          return IPackageManager.Stub.getDefaultImpl().getNameForUid(param1Int); 
        parcel2.readException();
        return parcel2.readString();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public PackageInfo getPackageInfo(String param1String, int param1Int1, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int1);
        parcel1.writeInt(param1Int2);
        if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
          return IPackageManager.Stub.getDefaultImpl().getPackageInfo(param1String, param1Int1, param1Int2); 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          PackageInfo packageInfo = (PackageInfo)PackageInfo.CREATOR.createFromParcel(parcel2);
        } else {
          param1String = null;
        } 
        return (PackageInfo)param1String;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public IBinder getPackageInstaller() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        if (!this.mRemote.transact(29, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
          return IPackageManager.Stub.getDefaultImpl().getPackageInstaller(); 
        parcel2.readException();
        return parcel2.readStrongBinder();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public int getPackageUid(String param1String, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null) {
          param1Int = IPackageManager.Stub.getDefaultImpl().getPackageUid(param1String, param1Int);
          return param1Int;
        } 
        parcel2.readException();
        param1Int = parcel2.readInt();
        return param1Int;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String[] getPackagesForUid(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
          return IPackageManager.Stub.getDefaultImpl().getPackagesForUid(param1Int); 
        parcel2.readException();
        return parcel2.createStringArray();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public PermissionGroupInfo getPermissionGroupInfo(String param1String, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(22, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
          return IPackageManager.Stub.getDefaultImpl().getPermissionGroupInfo(param1String, param1Int); 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          PermissionGroupInfo permissionGroupInfo = (PermissionGroupInfo)PermissionGroupInfo.CREATOR.createFromParcel(parcel2);
        } else {
          param1String = null;
        } 
        return (PermissionGroupInfo)param1String;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public PermissionInfo getPermissionInfo(String param1String, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(20, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
          return IPackageManager.Stub.getDefaultImpl().getPermissionInfo(param1String, param1Int); 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          PermissionInfo permissionInfo = (PermissionInfo)PermissionInfo.CREATOR.createFromParcel(parcel2);
        } else {
          param1String = null;
        } 
        return (PermissionInfo)param1String;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public ProviderInfo getProviderInfo(ComponentName param1ComponentName, int param1Int1, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        if (param1ComponentName != null) {
          parcel1.writeInt(1);
          param1ComponentName.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeInt(param1Int1);
        parcel1.writeInt(param1Int2);
        if (!this.mRemote.transact(10, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
          return IPackageManager.Stub.getDefaultImpl().getProviderInfo(param1ComponentName, param1Int1, param1Int2); 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          ProviderInfo providerInfo = (ProviderInfo)ProviderInfo.CREATOR.createFromParcel(parcel2);
        } else {
          param1ComponentName = null;
        } 
        return (ProviderInfo)param1ComponentName;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public ActivityInfo getReceiverInfo(ComponentName param1ComponentName, int param1Int1, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        if (param1ComponentName != null) {
          parcel1.writeInt(1);
          param1ComponentName.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeInt(param1Int1);
        parcel1.writeInt(param1Int2);
        if (!this.mRemote.transact(8, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
          return IPackageManager.Stub.getDefaultImpl().getReceiverInfo(param1ComponentName, param1Int1, param1Int2); 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          ActivityInfo activityInfo = (ActivityInfo)ActivityInfo.CREATOR.createFromParcel(parcel2);
        } else {
          param1ComponentName = null;
        } 
        return (ActivityInfo)param1ComponentName;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public List<ReceiverInfo> getReceiverInfos(String param1String1, String param1String2, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        parcel1.writeString(param1String1);
        parcel1.writeString(param1String2);
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(19, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
          return IPackageManager.Stub.getDefaultImpl().getReceiverInfos(param1String1, param1String2, param1Int); 
        parcel2.readException();
        return parcel2.createTypedArrayList(ReceiverInfo.CREATOR);
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public ServiceInfo getServiceInfo(ComponentName param1ComponentName, int param1Int1, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        if (param1ComponentName != null) {
          parcel1.writeInt(1);
          param1ComponentName.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeInt(param1Int1);
        parcel1.writeInt(param1Int2);
        if (!this.mRemote.transact(9, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
          return IPackageManager.Stub.getDefaultImpl().getServiceInfo(param1ComponentName, param1Int1, param1Int2); 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          ServiceInfo serviceInfo = (ServiceInfo)ServiceInfo.CREATOR.createFromParcel(parcel2);
        } else {
          param1ComponentName = null;
        } 
        return (ServiceInfo)param1ComponentName;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public List<String> getSharedLibraries(String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
          return IPackageManager.Stub.getDefaultImpl().getSharedLibraries(param1String); 
        parcel2.readException();
        return parcel2.createStringArrayList();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public VParceledListSlice queryContentProviders(String param1String, int param1Int1, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int1);
        parcel1.writeInt(param1Int2);
        if (!this.mRemote.transact(26, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
          return IPackageManager.Stub.getDefaultImpl().queryContentProviders(param1String, param1Int1, param1Int2); 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          VParceledListSlice vParceledListSlice = (VParceledListSlice)VParceledListSlice.CREATOR.createFromParcel(parcel2);
        } else {
          param1String = null;
        } 
        return (VParceledListSlice)param1String;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public List<ResolveInfo> queryIntentActivities(Intent param1Intent, String param1String, int param1Int1, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        if (param1Intent != null) {
          parcel1.writeInt(1);
          param1Intent.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int1);
        parcel1.writeInt(param1Int2);
        if (!this.mRemote.transact(12, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
          return IPackageManager.Stub.getDefaultImpl().queryIntentActivities(param1Intent, param1String, param1Int1, param1Int2); 
        parcel2.readException();
        return parcel2.createTypedArrayList(ResolveInfo.CREATOR);
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public List<ResolveInfo> queryIntentContentProviders(Intent param1Intent, String param1String, int param1Int1, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        if (param1Intent != null) {
          parcel1.writeInt(1);
          param1Intent.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int1);
        parcel1.writeInt(param1Int2);
        if (!this.mRemote.transact(16, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
          return IPackageManager.Stub.getDefaultImpl().queryIntentContentProviders(param1Intent, param1String, param1Int1, param1Int2); 
        parcel2.readException();
        return parcel2.createTypedArrayList(ResolveInfo.CREATOR);
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public List<ResolveInfo> queryIntentReceivers(Intent param1Intent, String param1String, int param1Int1, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        if (param1Intent != null) {
          parcel1.writeInt(1);
          param1Intent.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int1);
        parcel1.writeInt(param1Int2);
        if (!this.mRemote.transact(13, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
          return IPackageManager.Stub.getDefaultImpl().queryIntentReceivers(param1Intent, param1String, param1Int1, param1Int2); 
        parcel2.readException();
        return parcel2.createTypedArrayList(ResolveInfo.CREATOR);
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public List<ResolveInfo> queryIntentServices(Intent param1Intent, String param1String, int param1Int1, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        if (param1Intent != null) {
          parcel1.writeInt(1);
          param1Intent.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int1);
        parcel1.writeInt(param1Int2);
        if (!this.mRemote.transact(15, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
          return IPackageManager.Stub.getDefaultImpl().queryIntentServices(param1Intent, param1String, param1Int1, param1Int2); 
        parcel2.readException();
        return parcel2.createTypedArrayList(ResolveInfo.CREATOR);
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public List<PermissionInfo> queryPermissionsByGroup(String param1String, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(21, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
          return IPackageManager.Stub.getDefaultImpl().queryPermissionsByGroup(param1String, param1Int); 
        parcel2.readException();
        return parcel2.createTypedArrayList(PermissionInfo.CREATOR);
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public List<String> querySharedPackages(String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(27, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
          return IPackageManager.Stub.getDefaultImpl().querySharedPackages(param1String); 
        parcel2.readException();
        return parcel2.createStringArrayList();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public ProviderInfo resolveContentProvider(String param1String, int param1Int1, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int1);
        parcel1.writeInt(param1Int2);
        if (!this.mRemote.transact(24, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
          return IPackageManager.Stub.getDefaultImpl().resolveContentProvider(param1String, param1Int1, param1Int2); 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          ProviderInfo providerInfo = (ProviderInfo)ProviderInfo.CREATOR.createFromParcel(parcel2);
        } else {
          param1String = null;
        } 
        return (ProviderInfo)param1String;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public ResolveInfo resolveIntent(Intent param1Intent, String param1String, int param1Int1, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        if (param1Intent != null) {
          parcel1.writeInt(1);
          param1Intent.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int1);
        parcel1.writeInt(param1Int2);
        if (!this.mRemote.transact(11, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
          return IPackageManager.Stub.getDefaultImpl().resolveIntent(param1Intent, param1String, param1Int1, param1Int2); 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          ResolveInfo resolveInfo = (ResolveInfo)ResolveInfo.CREATOR.createFromParcel(parcel2);
        } else {
          param1Intent = null;
        } 
        return (ResolveInfo)param1Intent;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public ResolveInfo resolveService(Intent param1Intent, String param1String, int param1Int1, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        if (param1Intent != null) {
          parcel1.writeInt(1);
          param1Intent.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int1);
        parcel1.writeInt(param1Int2);
        if (!this.mRemote.transact(14, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null)
          return IPackageManager.Stub.getDefaultImpl().resolveService(param1Intent, param1String, param1Int1, param1Int2); 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          ResolveInfo resolveInfo = (ResolveInfo)ResolveInfo.CREATOR.createFromParcel(parcel2);
        } else {
          param1Intent = null;
        } 
        return (ResolveInfo)param1Intent;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setComponentEnabledSetting(ComponentName param1ComponentName, int param1Int1, int param1Int2, int param1Int3) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IPackageManager");
        if (param1ComponentName != null) {
          parcel1.writeInt(1);
          param1ComponentName.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeInt(param1Int1);
        parcel1.writeInt(param1Int2);
        parcel1.writeInt(param1Int3);
        if (!this.mRemote.transact(32, parcel1, parcel2, 0) && IPackageManager.Stub.getDefaultImpl() != null) {
          IPackageManager.Stub.getDefaultImpl().setComponentEnabledSetting(param1ComponentName, param1Int1, param1Int2, param1Int3);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\interfaces\IPackageManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */