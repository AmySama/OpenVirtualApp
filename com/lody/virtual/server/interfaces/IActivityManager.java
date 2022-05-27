package com.lody.virtual.server.interfaces;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ProviderInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.lody.virtual.remote.AppTaskInfo;
import com.lody.virtual.remote.BadgerInfo;
import com.lody.virtual.remote.ClientConfig;
import com.lody.virtual.remote.IntentSenderData;
import com.lody.virtual.remote.VParceledListSlice;
import java.util.List;

public interface IActivityManager extends IInterface {
  IBinder acquireProviderClient(int paramInt, ProviderInfo paramProviderInfo) throws RemoteException;
  
  void addOrUpdateIntentSender(IntentSenderData paramIntentSenderData, int paramInt) throws RemoteException;
  
  void appDoneExecuting(String paramString, int paramInt) throws RemoteException;
  
  boolean broadcastFinish(IBinder paramIBinder) throws RemoteException;
  
  int checkPermission(boolean paramBoolean, String paramString, int paramInt1, int paramInt2) throws RemoteException;
  
  void dump() throws RemoteException;
  
  boolean finishActivityAffinity(int paramInt, IBinder paramIBinder) throws RemoteException;
  
  ComponentName getActivityClassForToken(int paramInt, IBinder paramIBinder) throws RemoteException;
  
  int getAppPid(String paramString1, int paramInt, String paramString2) throws RemoteException;
  
  String getAppProcessName(int paramInt) throws RemoteException;
  
  ComponentName getCallingActivity(int paramInt, IBinder paramIBinder) throws RemoteException;
  
  String getCallingPackage(int paramInt, IBinder paramIBinder) throws RemoteException;
  
  int getFreeStubCount() throws RemoteException;
  
  String getInitialPackage(int paramInt) throws RemoteException;
  
  IntentSenderData getIntentSender(IBinder paramIBinder) throws RemoteException;
  
  String getPackageForToken(int paramInt, IBinder paramIBinder) throws RemoteException;
  
  List<String> getProcessPkgList(int paramInt) throws RemoteException;
  
  VParceledListSlice getServices(String paramString, int paramInt1, int paramInt2, int paramInt3) throws RemoteException;
  
  int getSystemPid() throws RemoteException;
  
  int getSystemUid() throws RemoteException;
  
  AppTaskInfo getTaskInfo(int paramInt) throws RemoteException;
  
  int getUidByPid(int paramInt) throws RemoteException;
  
  void handleDownloadCompleteIntent(Intent paramIntent) throws RemoteException;
  
  ClientConfig initProcess(String paramString1, String paramString2, int paramInt) throws RemoteException;
  
  boolean isAppInactive(String paramString, int paramInt) throws RemoteException;
  
  boolean isAppPid(int paramInt) throws RemoteException;
  
  boolean isAppProcess(String paramString) throws RemoteException;
  
  boolean isAppRunning(String paramString, int paramInt, boolean paramBoolean) throws RemoteException;
  
  void killAllApps() throws RemoteException;
  
  void killAppByPkg(String paramString, int paramInt) throws RemoteException;
  
  void killApplicationProcess(String paramString, int paramInt) throws RemoteException;
  
  void notifyBadgerChange(BadgerInfo paramBadgerInfo) throws RemoteException;
  
  void onActivityCreated(IBinder paramIBinder1, IBinder paramIBinder2, int paramInt) throws RemoteException;
  
  boolean onActivityDestroyed(int paramInt, IBinder paramIBinder) throws RemoteException;
  
  void onActivityFinish(int paramInt, IBinder paramIBinder) throws RemoteException;
  
  void onActivityResumed(int paramInt, IBinder paramIBinder) throws RemoteException;
  
  void processRestarted(String paramString1, String paramString2, int paramInt) throws RemoteException;
  
  void removeIntentSender(IBinder paramIBinder) throws RemoteException;
  
  void setAppInactive(String paramString, boolean paramBoolean, int paramInt) throws RemoteException;
  
  int startActivities(Intent[] paramArrayOfIntent, String[] paramArrayOfString, IBinder paramIBinder, Bundle paramBundle, String paramString, int paramInt) throws RemoteException;
  
  int startActivity(Intent paramIntent, ActivityInfo paramActivityInfo, IBinder paramIBinder, Bundle paramBundle, String paramString1, int paramInt1, String paramString2, int paramInt2) throws RemoteException;
  
  int startActivityFromHistory(Intent paramIntent) throws RemoteException;
  
  public static class Default implements IActivityManager {
    public IBinder acquireProviderClient(int param1Int, ProviderInfo param1ProviderInfo) throws RemoteException {
      return null;
    }
    
    public void addOrUpdateIntentSender(IntentSenderData param1IntentSenderData, int param1Int) throws RemoteException {}
    
    public void appDoneExecuting(String param1String, int param1Int) throws RemoteException {}
    
    public IBinder asBinder() {
      return null;
    }
    
    public boolean broadcastFinish(IBinder param1IBinder) throws RemoteException {
      return false;
    }
    
    public int checkPermission(boolean param1Boolean, String param1String, int param1Int1, int param1Int2) throws RemoteException {
      return 0;
    }
    
    public void dump() throws RemoteException {}
    
    public boolean finishActivityAffinity(int param1Int, IBinder param1IBinder) throws RemoteException {
      return false;
    }
    
    public ComponentName getActivityClassForToken(int param1Int, IBinder param1IBinder) throws RemoteException {
      return null;
    }
    
    public int getAppPid(String param1String1, int param1Int, String param1String2) throws RemoteException {
      return 0;
    }
    
    public String getAppProcessName(int param1Int) throws RemoteException {
      return null;
    }
    
    public ComponentName getCallingActivity(int param1Int, IBinder param1IBinder) throws RemoteException {
      return null;
    }
    
    public String getCallingPackage(int param1Int, IBinder param1IBinder) throws RemoteException {
      return null;
    }
    
    public int getFreeStubCount() throws RemoteException {
      return 0;
    }
    
    public String getInitialPackage(int param1Int) throws RemoteException {
      return null;
    }
    
    public IntentSenderData getIntentSender(IBinder param1IBinder) throws RemoteException {
      return null;
    }
    
    public String getPackageForToken(int param1Int, IBinder param1IBinder) throws RemoteException {
      return null;
    }
    
    public List<String> getProcessPkgList(int param1Int) throws RemoteException {
      return null;
    }
    
    public VParceledListSlice getServices(String param1String, int param1Int1, int param1Int2, int param1Int3) throws RemoteException {
      return null;
    }
    
    public int getSystemPid() throws RemoteException {
      return 0;
    }
    
    public int getSystemUid() throws RemoteException {
      return 0;
    }
    
    public AppTaskInfo getTaskInfo(int param1Int) throws RemoteException {
      return null;
    }
    
    public int getUidByPid(int param1Int) throws RemoteException {
      return 0;
    }
    
    public void handleDownloadCompleteIntent(Intent param1Intent) throws RemoteException {}
    
    public ClientConfig initProcess(String param1String1, String param1String2, int param1Int) throws RemoteException {
      return null;
    }
    
    public boolean isAppInactive(String param1String, int param1Int) throws RemoteException {
      return false;
    }
    
    public boolean isAppPid(int param1Int) throws RemoteException {
      return false;
    }
    
    public boolean isAppProcess(String param1String) throws RemoteException {
      return false;
    }
    
    public boolean isAppRunning(String param1String, int param1Int, boolean param1Boolean) throws RemoteException {
      return false;
    }
    
    public void killAllApps() throws RemoteException {}
    
    public void killAppByPkg(String param1String, int param1Int) throws RemoteException {}
    
    public void killApplicationProcess(String param1String, int param1Int) throws RemoteException {}
    
    public void notifyBadgerChange(BadgerInfo param1BadgerInfo) throws RemoteException {}
    
    public void onActivityCreated(IBinder param1IBinder1, IBinder param1IBinder2, int param1Int) throws RemoteException {}
    
    public boolean onActivityDestroyed(int param1Int, IBinder param1IBinder) throws RemoteException {
      return false;
    }
    
    public void onActivityFinish(int param1Int, IBinder param1IBinder) throws RemoteException {}
    
    public void onActivityResumed(int param1Int, IBinder param1IBinder) throws RemoteException {}
    
    public void processRestarted(String param1String1, String param1String2, int param1Int) throws RemoteException {}
    
    public void removeIntentSender(IBinder param1IBinder) throws RemoteException {}
    
    public void setAppInactive(String param1String, boolean param1Boolean, int param1Int) throws RemoteException {}
    
    public int startActivities(Intent[] param1ArrayOfIntent, String[] param1ArrayOfString, IBinder param1IBinder, Bundle param1Bundle, String param1String, int param1Int) throws RemoteException {
      return 0;
    }
    
    public int startActivity(Intent param1Intent, ActivityInfo param1ActivityInfo, IBinder param1IBinder, Bundle param1Bundle, String param1String1, int param1Int1, String param1String2, int param1Int2) throws RemoteException {
      return 0;
    }
    
    public int startActivityFromHistory(Intent param1Intent) throws RemoteException {
      return 0;
    }
  }
  
  public static abstract class Stub extends Binder implements IActivityManager {
    private static final String DESCRIPTOR = "com.lody.virtual.server.interfaces.IActivityManager";
    
    static final int TRANSACTION_acquireProviderClient = 31;
    
    static final int TRANSACTION_addOrUpdateIntentSender = 33;
    
    static final int TRANSACTION_appDoneExecuting = 2;
    
    static final int TRANSACTION_broadcastFinish = 32;
    
    static final int TRANSACTION_checkPermission = 4;
    
    static final int TRANSACTION_dump = 16;
    
    static final int TRANSACTION_finishActivityAffinity = 21;
    
    static final int TRANSACTION_getActivityClassForToken = 26;
    
    static final int TRANSACTION_getAppPid = 42;
    
    static final int TRANSACTION_getAppProcessName = 11;
    
    static final int TRANSACTION_getCallingActivity = 28;
    
    static final int TRANSACTION_getCallingPackage = 27;
    
    static final int TRANSACTION_getFreeStubCount = 3;
    
    static final int TRANSACTION_getInitialPackage = 17;
    
    static final int TRANSACTION_getIntentSender = 35;
    
    static final int TRANSACTION_getPackageForToken = 30;
    
    static final int TRANSACTION_getProcessPkgList = 12;
    
    static final int TRANSACTION_getServices = 40;
    
    static final int TRANSACTION_getSystemPid = 5;
    
    static final int TRANSACTION_getSystemUid = 6;
    
    static final int TRANSACTION_getTaskInfo = 29;
    
    static final int TRANSACTION_getUidByPid = 7;
    
    static final int TRANSACTION_handleDownloadCompleteIntent = 41;
    
    static final int TRANSACTION_initProcess = 1;
    
    static final int TRANSACTION_isAppInactive = 39;
    
    static final int TRANSACTION_isAppPid = 10;
    
    static final int TRANSACTION_isAppProcess = 8;
    
    static final int TRANSACTION_isAppRunning = 9;
    
    static final int TRANSACTION_killAllApps = 13;
    
    static final int TRANSACTION_killAppByPkg = 14;
    
    static final int TRANSACTION_killApplicationProcess = 15;
    
    static final int TRANSACTION_notifyBadgerChange = 37;
    
    static final int TRANSACTION_onActivityCreated = 22;
    
    static final int TRANSACTION_onActivityDestroyed = 24;
    
    static final int TRANSACTION_onActivityFinish = 25;
    
    static final int TRANSACTION_onActivityResumed = 23;
    
    static final int TRANSACTION_processRestarted = 36;
    
    static final int TRANSACTION_removeIntentSender = 34;
    
    static final int TRANSACTION_setAppInactive = 38;
    
    static final int TRANSACTION_startActivities = 18;
    
    static final int TRANSACTION_startActivity = 19;
    
    static final int TRANSACTION_startActivityFromHistory = 20;
    
    public Stub() {
      attachInterface(this, "com.lody.virtual.server.interfaces.IActivityManager");
    }
    
    public static IActivityManager asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("com.lody.virtual.server.interfaces.IActivityManager");
      return (iInterface != null && iInterface instanceof IActivityManager) ? (IActivityManager)iInterface : new Proxy(param1IBinder);
    }
    
    public static IActivityManager getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IActivityManager param1IActivityManager) {
      if (Proxy.sDefaultImpl == null && param1IActivityManager != null) {
        Proxy.sDefaultImpl = param1IActivityManager;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      if (param1Int1 != 1598968902) {
        boolean bool4;
        int m;
        boolean bool3;
        int k;
        boolean bool2;
        int j;
        boolean bool1;
        int i;
        VParceledListSlice vParceledListSlice;
        IntentSenderData intentSenderData1;
        IBinder iBinder1;
        String str4;
        AppTaskInfo appTaskInfo;
        ComponentName componentName2;
        String str3;
        ComponentName componentName1;
        String str2;
        List<String> list;
        String str1;
        IBinder iBinder2;
        String[] arrayOfString;
        Intent[] arrayOfIntent;
        String str7;
        BadgerInfo badgerInfo2;
        IntentSenderData intentSenderData3;
        ProviderInfo providerInfo2;
        Intent intent1;
        Bundle bundle2;
        String str6;
        IBinder iBinder3;
        String str5 = null;
        BadgerInfo badgerInfo1 = null;
        IntentSenderData intentSenderData2 = null;
        ProviderInfo providerInfo1 = null;
        Bundle bundle1 = null;
        Intent intent2 = null;
        Intent intent3 = null;
        boolean bool5 = false;
        boolean bool6 = false;
        boolean bool7 = false;
        switch (param1Int1) {
          default:
            return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2);
          case 42:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            param1Int1 = getAppPid(param1Parcel1.readString(), param1Parcel1.readInt(), param1Parcel1.readString());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(param1Int1);
            return true;
          case 41:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            intent2 = intent3;
            if (param1Parcel1.readInt() != 0)
              intent2 = (Intent)Intent.CREATOR.createFromParcel(param1Parcel1); 
            handleDownloadCompleteIntent(intent2);
            param1Parcel2.writeNoException();
            return true;
          case 40:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            vParceledListSlice = getServices(param1Parcel1.readString(), param1Parcel1.readInt(), param1Parcel1.readInt(), param1Parcel1.readInt());
            param1Parcel2.writeNoException();
            if (vParceledListSlice != null) {
              param1Parcel2.writeInt(1);
              vParceledListSlice.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 39:
            vParceledListSlice.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            bool4 = isAppInactive(vParceledListSlice.readString(), vParceledListSlice.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool4);
            return true;
          case 38:
            vParceledListSlice.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            str7 = vParceledListSlice.readString();
            if (vParceledListSlice.readInt() != 0)
              bool7 = true; 
            setAppInactive(str7, bool7, vParceledListSlice.readInt());
            param1Parcel2.writeNoException();
            return true;
          case 37:
            vParceledListSlice.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            str7 = str5;
            if (vParceledListSlice.readInt() != 0)
              badgerInfo2 = (BadgerInfo)BadgerInfo.CREATOR.createFromParcel((Parcel)vParceledListSlice); 
            notifyBadgerChange(badgerInfo2);
            param1Parcel2.writeNoException();
            return true;
          case 36:
            vParceledListSlice.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            processRestarted(vParceledListSlice.readString(), vParceledListSlice.readString(), vParceledListSlice.readInt());
            param1Parcel2.writeNoException();
            return true;
          case 35:
            vParceledListSlice.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            intentSenderData1 = getIntentSender(vParceledListSlice.readStrongBinder());
            param1Parcel2.writeNoException();
            if (intentSenderData1 != null) {
              param1Parcel2.writeInt(1);
              intentSenderData1.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 34:
            intentSenderData1.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            removeIntentSender(intentSenderData1.readStrongBinder());
            param1Parcel2.writeNoException();
            return true;
          case 33:
            intentSenderData1.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            badgerInfo2 = badgerInfo1;
            if (intentSenderData1.readInt() != 0)
              intentSenderData3 = (IntentSenderData)IntentSenderData.CREATOR.createFromParcel((Parcel)intentSenderData1); 
            addOrUpdateIntentSender(intentSenderData3, intentSenderData1.readInt());
            param1Parcel2.writeNoException();
            return true;
          case 32:
            intentSenderData1.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            bool4 = broadcastFinish(intentSenderData1.readStrongBinder());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool4);
            return true;
          case 31:
            intentSenderData1.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            m = intentSenderData1.readInt();
            intentSenderData3 = intentSenderData2;
            if (intentSenderData1.readInt() != 0)
              providerInfo2 = (ProviderInfo)ProviderInfo.CREATOR.createFromParcel((Parcel)intentSenderData1); 
            iBinder1 = acquireProviderClient(m, providerInfo2);
            param1Parcel2.writeNoException();
            param1Parcel2.writeStrongBinder(iBinder1);
            return true;
          case 30:
            iBinder1.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            str4 = getPackageForToken(iBinder1.readInt(), iBinder1.readStrongBinder());
            param1Parcel2.writeNoException();
            param1Parcel2.writeString(str4);
            return true;
          case 29:
            str4.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            appTaskInfo = getTaskInfo(str4.readInt());
            param1Parcel2.writeNoException();
            if (appTaskInfo != null) {
              param1Parcel2.writeInt(1);
              appTaskInfo.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 28:
            appTaskInfo.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            componentName2 = getCallingActivity(appTaskInfo.readInt(), appTaskInfo.readStrongBinder());
            param1Parcel2.writeNoException();
            if (componentName2 != null) {
              param1Parcel2.writeInt(1);
              componentName2.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 27:
            componentName2.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            str3 = getCallingPackage(componentName2.readInt(), componentName2.readStrongBinder());
            param1Parcel2.writeNoException();
            param1Parcel2.writeString(str3);
            return true;
          case 26:
            str3.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            componentName1 = getActivityClassForToken(str3.readInt(), str3.readStrongBinder());
            param1Parcel2.writeNoException();
            if (componentName1 != null) {
              param1Parcel2.writeInt(1);
              componentName1.writeToParcel(param1Parcel2, 1);
            } else {
              param1Parcel2.writeInt(0);
            } 
            return true;
          case 25:
            componentName1.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            onActivityFinish(componentName1.readInt(), componentName1.readStrongBinder());
            param1Parcel2.writeNoException();
            return true;
          case 24:
            componentName1.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            bool3 = onActivityDestroyed(componentName1.readInt(), componentName1.readStrongBinder());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool3);
            return true;
          case 23:
            componentName1.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            onActivityResumed(componentName1.readInt(), componentName1.readStrongBinder());
            param1Parcel2.writeNoException();
            return true;
          case 22:
            componentName1.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            onActivityCreated(componentName1.readStrongBinder(), componentName1.readStrongBinder(), componentName1.readInt());
            param1Parcel2.writeNoException();
            return true;
          case 21:
            componentName1.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            bool3 = finishActivityAffinity(componentName1.readInt(), componentName1.readStrongBinder());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool3);
            return true;
          case 20:
            componentName1.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            providerInfo2 = providerInfo1;
            if (componentName1.readInt() != 0)
              intent1 = (Intent)Intent.CREATOR.createFromParcel((Parcel)componentName1); 
            k = startActivityFromHistory(intent1);
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(k);
            return true;
          case 19:
            componentName1.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            if (componentName1.readInt() != 0) {
              intent1 = (Intent)Intent.CREATOR.createFromParcel((Parcel)componentName1);
            } else {
              intent1 = null;
            } 
            if (componentName1.readInt() != 0) {
              ActivityInfo activityInfo = (ActivityInfo)ActivityInfo.CREATOR.createFromParcel((Parcel)componentName1);
            } else {
              intent3 = null;
            } 
            iBinder2 = componentName1.readStrongBinder();
            if (componentName1.readInt() != 0)
              bundle1 = (Bundle)Bundle.CREATOR.createFromParcel((Parcel)componentName1); 
            k = startActivity(intent1, (ActivityInfo)intent3, iBinder2, bundle1, componentName1.readString(), componentName1.readInt(), componentName1.readString(), componentName1.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(k);
            return true;
          case 18:
            componentName1.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            arrayOfIntent = (Intent[])componentName1.createTypedArray(Intent.CREATOR);
            arrayOfString = componentName1.createStringArray();
            iBinder3 = componentName1.readStrongBinder();
            if (componentName1.readInt() != 0)
              bundle2 = (Bundle)Bundle.CREATOR.createFromParcel((Parcel)componentName1); 
            k = startActivities(arrayOfIntent, arrayOfString, iBinder3, bundle2, componentName1.readString(), componentName1.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(k);
            return true;
          case 17:
            componentName1.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            str2 = getInitialPackage(componentName1.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeString(str2);
            return true;
          case 16:
            str2.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            dump();
            param1Parcel2.writeNoException();
            return true;
          case 15:
            str2.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            killApplicationProcess(str2.readString(), str2.readInt());
            param1Parcel2.writeNoException();
            return true;
          case 14:
            str2.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            killAppByPkg(str2.readString(), str2.readInt());
            param1Parcel2.writeNoException();
            return true;
          case 13:
            str2.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            killAllApps();
            param1Parcel2.writeNoException();
            return true;
          case 12:
            str2.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            list = getProcessPkgList(str2.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeStringList(list);
            return true;
          case 11:
            list.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            str1 = getAppProcessName(list.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeString(str1);
            return true;
          case 10:
            str1.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            bool2 = isAppPid(str1.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool2);
            return true;
          case 9:
            str1.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            str6 = str1.readString();
            j = str1.readInt();
            bool7 = bool5;
            if (str1.readInt() != 0)
              bool7 = true; 
            bool1 = isAppRunning(str6, j, bool7);
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool1);
            return true;
          case 8:
            str1.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            bool1 = isAppProcess(str1.readString());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool1);
            return true;
          case 7:
            str1.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            i = getUidByPid(str1.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(i);
            return true;
          case 6:
            str1.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            i = getSystemUid();
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(i);
            return true;
          case 5:
            str1.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            i = getSystemPid();
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(i);
            return true;
          case 4:
            str1.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            bool7 = bool6;
            if (str1.readInt() != 0)
              bool7 = true; 
            i = checkPermission(bool7, str1.readString(), str1.readInt(), str1.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(i);
            return true;
          case 3:
            str1.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            i = getFreeStubCount();
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(i);
            return true;
          case 2:
            str1.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
            appDoneExecuting(str1.readString(), str1.readInt());
            param1Parcel2.writeNoException();
            return true;
          case 1:
            break;
        } 
        str1.enforceInterface("com.lody.virtual.server.interfaces.IActivityManager");
        ClientConfig clientConfig = initProcess(str1.readString(), str1.readString(), str1.readInt());
        param1Parcel2.writeNoException();
        if (clientConfig != null) {
          param1Parcel2.writeInt(1);
          clientConfig.writeToParcel(param1Parcel2, 1);
        } else {
          param1Parcel2.writeInt(0);
        } 
        return true;
      } 
      param1Parcel2.writeString("com.lody.virtual.server.interfaces.IActivityManager");
      return true;
    }
    
    private static class Proxy implements IActivityManager {
      public static IActivityManager sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public IBinder acquireProviderClient(int param2Int, ProviderInfo param2ProviderInfo) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeInt(param2Int);
          if (param2ProviderInfo != null) {
            parcel1.writeInt(1);
            param2ProviderInfo.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(31, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
            return IActivityManager.Stub.getDefaultImpl().acquireProviderClient(param2Int, param2ProviderInfo); 
          parcel2.readException();
          return parcel2.readStrongBinder();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void addOrUpdateIntentSender(IntentSenderData param2IntentSenderData, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          if (param2IntentSenderData != null) {
            parcel1.writeInt(1);
            param2IntentSenderData.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(33, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
            IActivityManager.Stub.getDefaultImpl().addOrUpdateIntentSender(param2IntentSenderData, param2Int);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void appDoneExecuting(String param2String, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
            IActivityManager.Stub.getDefaultImpl().appDoneExecuting(param2String, param2Int);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public boolean broadcastFinish(IBinder param2IBinder) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeStrongBinder(param2IBinder);
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          if (!iBinder.transact(32, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
            bool = IActivityManager.Stub.getDefaultImpl().broadcastFinish(param2IBinder);
            return bool;
          } 
          parcel2.readException();
          int i = parcel2.readInt();
          if (i != 0)
            bool = true; 
          return bool;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public int checkPermission(boolean param2Boolean, String param2String, int param2Int1, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          boolean bool;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          if (param2Boolean) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int1);
          parcel1.writeInt(param2Int2);
          if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
            param2Int1 = IActivityManager.Stub.getDefaultImpl().checkPermission(param2Boolean, param2String, param2Int1, param2Int2);
            return param2Int1;
          } 
          parcel2.readException();
          param2Int1 = parcel2.readInt();
          return param2Int1;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void dump() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          if (!this.mRemote.transact(16, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
            IActivityManager.Stub.getDefaultImpl().dump();
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public boolean finishActivityAffinity(int param2Int, IBinder param2IBinder) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeInt(param2Int);
          parcel1.writeStrongBinder(param2IBinder);
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          if (!iBinder.transact(21, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
            bool = IActivityManager.Stub.getDefaultImpl().finishActivityAffinity(param2Int, param2IBinder);
            return bool;
          } 
          parcel2.readException();
          param2Int = parcel2.readInt();
          if (param2Int != 0)
            bool = true; 
          return bool;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public ComponentName getActivityClassForToken(int param2Int, IBinder param2IBinder) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeInt(param2Int);
          parcel1.writeStrongBinder(param2IBinder);
          if (!this.mRemote.transact(26, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
            return IActivityManager.Stub.getDefaultImpl().getActivityClassForToken(param2Int, param2IBinder); 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            ComponentName componentName = (ComponentName)ComponentName.CREATOR.createFromParcel(parcel2);
          } else {
            param2IBinder = null;
          } 
          return (ComponentName)param2IBinder;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public int getAppPid(String param2String1, int param2Int, String param2String2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeString(param2String1);
          parcel1.writeInt(param2Int);
          parcel1.writeString(param2String2);
          if (!this.mRemote.transact(42, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
            param2Int = IActivityManager.Stub.getDefaultImpl().getAppPid(param2String1, param2Int, param2String2);
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
      
      public String getAppProcessName(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(11, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
            return IActivityManager.Stub.getDefaultImpl().getAppProcessName(param2Int); 
          parcel2.readException();
          return parcel2.readString();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public ComponentName getCallingActivity(int param2Int, IBinder param2IBinder) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeInt(param2Int);
          parcel1.writeStrongBinder(param2IBinder);
          if (!this.mRemote.transact(28, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
            return IActivityManager.Stub.getDefaultImpl().getCallingActivity(param2Int, param2IBinder); 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            ComponentName componentName = (ComponentName)ComponentName.CREATOR.createFromParcel(parcel2);
          } else {
            param2IBinder = null;
          } 
          return (ComponentName)param2IBinder;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String getCallingPackage(int param2Int, IBinder param2IBinder) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeInt(param2Int);
          parcel1.writeStrongBinder(param2IBinder);
          if (!this.mRemote.transact(27, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
            return IActivityManager.Stub.getDefaultImpl().getCallingPackage(param2Int, param2IBinder); 
          parcel2.readException();
          return parcel2.readString();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public int getFreeStubCount() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
            return IActivityManager.Stub.getDefaultImpl().getFreeStubCount(); 
          parcel2.readException();
          return parcel2.readInt();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String getInitialPackage(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(17, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
            return IActivityManager.Stub.getDefaultImpl().getInitialPackage(param2Int); 
          parcel2.readException();
          return parcel2.readString();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public IntentSenderData getIntentSender(IBinder param2IBinder) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeStrongBinder(param2IBinder);
          if (!this.mRemote.transact(35, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
            return IActivityManager.Stub.getDefaultImpl().getIntentSender(param2IBinder); 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            IntentSenderData intentSenderData = (IntentSenderData)IntentSenderData.CREATOR.createFromParcel(parcel2);
          } else {
            param2IBinder = null;
          } 
          return (IntentSenderData)param2IBinder;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String getInterfaceDescriptor() {
        return "com.lody.virtual.server.interfaces.IActivityManager";
      }
      
      public String getPackageForToken(int param2Int, IBinder param2IBinder) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeInt(param2Int);
          parcel1.writeStrongBinder(param2IBinder);
          if (!this.mRemote.transact(30, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
            return IActivityManager.Stub.getDefaultImpl().getPackageForToken(param2Int, param2IBinder); 
          parcel2.readException();
          return parcel2.readString();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public List<String> getProcessPkgList(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(12, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
            return IActivityManager.Stub.getDefaultImpl().getProcessPkgList(param2Int); 
          parcel2.readException();
          return parcel2.createStringArrayList();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public VParceledListSlice getServices(String param2String, int param2Int1, int param2Int2, int param2Int3) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int1);
          parcel1.writeInt(param2Int2);
          parcel1.writeInt(param2Int3);
          if (!this.mRemote.transact(40, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
            return IActivityManager.Stub.getDefaultImpl().getServices(param2String, param2Int1, param2Int2, param2Int3); 
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
      
      public int getSystemPid() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
            return IActivityManager.Stub.getDefaultImpl().getSystemPid(); 
          parcel2.readException();
          return parcel2.readInt();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public int getSystemUid() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          if (!this.mRemote.transact(6, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
            return IActivityManager.Stub.getDefaultImpl().getSystemUid(); 
          parcel2.readException();
          return parcel2.readInt();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public AppTaskInfo getTaskInfo(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          AppTaskInfo appTaskInfo;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(29, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
            appTaskInfo = IActivityManager.Stub.getDefaultImpl().getTaskInfo(param2Int);
            return appTaskInfo;
          } 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            appTaskInfo = (AppTaskInfo)AppTaskInfo.CREATOR.createFromParcel(parcel2);
          } else {
            appTaskInfo = null;
          } 
          return appTaskInfo;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public int getUidByPid(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(7, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
            param2Int = IActivityManager.Stub.getDefaultImpl().getUidByPid(param2Int);
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
      
      public void handleDownloadCompleteIntent(Intent param2Intent) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          if (param2Intent != null) {
            parcel1.writeInt(1);
            param2Intent.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(41, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
            IActivityManager.Stub.getDefaultImpl().handleDownloadCompleteIntent(param2Intent);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public ClientConfig initProcess(String param2String1, String param2String2, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeString(param2String1);
          parcel1.writeString(param2String2);
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
            return IActivityManager.Stub.getDefaultImpl().initProcess(param2String1, param2String2, param2Int); 
          parcel2.readException();
          if (parcel2.readInt() != 0) {
            ClientConfig clientConfig = (ClientConfig)ClientConfig.CREATOR.createFromParcel(parcel2);
          } else {
            param2String1 = null;
          } 
          return (ClientConfig)param2String1;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public boolean isAppInactive(String param2String, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int);
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          if (!iBinder.transact(39, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
            bool = IActivityManager.Stub.getDefaultImpl().isAppInactive(param2String, param2Int);
            return bool;
          } 
          parcel2.readException();
          param2Int = parcel2.readInt();
          if (param2Int != 0)
            bool = true; 
          return bool;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public boolean isAppPid(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeInt(param2Int);
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          if (!iBinder.transact(10, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
            bool = IActivityManager.Stub.getDefaultImpl().isAppPid(param2Int);
            return bool;
          } 
          parcel2.readException();
          param2Int = parcel2.readInt();
          if (param2Int != 0)
            bool = true; 
          return bool;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public boolean isAppProcess(String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeString(param2String);
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          if (!iBinder.transact(8, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
            bool = IActivityManager.Stub.getDefaultImpl().isAppProcess(param2String);
            return bool;
          } 
          parcel2.readException();
          int i = parcel2.readInt();
          if (i != 0)
            bool = true; 
          return bool;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public boolean isAppRunning(String param2String, int param2Int, boolean param2Boolean) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          boolean bool2;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int);
          boolean bool1 = true;
          if (param2Boolean) {
            bool2 = true;
          } else {
            bool2 = false;
          } 
          parcel1.writeInt(bool2);
          if (!this.mRemote.transact(9, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
            param2Boolean = IActivityManager.Stub.getDefaultImpl().isAppRunning(param2String, param2Int, param2Boolean);
            return param2Boolean;
          } 
          parcel2.readException();
          param2Int = parcel2.readInt();
          if (param2Int != 0) {
            param2Boolean = bool1;
          } else {
            param2Boolean = false;
          } 
          return param2Boolean;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void killAllApps() throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          if (!this.mRemote.transact(13, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
            IActivityManager.Stub.getDefaultImpl().killAllApps();
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void killAppByPkg(String param2String, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(14, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
            IActivityManager.Stub.getDefaultImpl().killAppByPkg(param2String, param2Int);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void killApplicationProcess(String param2String, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(15, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
            IActivityManager.Stub.getDefaultImpl().killApplicationProcess(param2String, param2Int);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void notifyBadgerChange(BadgerInfo param2BadgerInfo) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          if (param2BadgerInfo != null) {
            parcel1.writeInt(1);
            param2BadgerInfo.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(37, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
            IActivityManager.Stub.getDefaultImpl().notifyBadgerChange(param2BadgerInfo);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void onActivityCreated(IBinder param2IBinder1, IBinder param2IBinder2, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeStrongBinder(param2IBinder1);
          parcel1.writeStrongBinder(param2IBinder2);
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(22, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
            IActivityManager.Stub.getDefaultImpl().onActivityCreated(param2IBinder1, param2IBinder2, param2Int);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public boolean onActivityDestroyed(int param2Int, IBinder param2IBinder) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeInt(param2Int);
          parcel1.writeStrongBinder(param2IBinder);
          IBinder iBinder = this.mRemote;
          boolean bool = false;
          if (!iBinder.transact(24, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
            bool = IActivityManager.Stub.getDefaultImpl().onActivityDestroyed(param2Int, param2IBinder);
            return bool;
          } 
          parcel2.readException();
          param2Int = parcel2.readInt();
          if (param2Int != 0)
            bool = true; 
          return bool;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void onActivityFinish(int param2Int, IBinder param2IBinder) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeInt(param2Int);
          parcel1.writeStrongBinder(param2IBinder);
          if (!this.mRemote.transact(25, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
            IActivityManager.Stub.getDefaultImpl().onActivityFinish(param2Int, param2IBinder);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void onActivityResumed(int param2Int, IBinder param2IBinder) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeInt(param2Int);
          parcel1.writeStrongBinder(param2IBinder);
          if (!this.mRemote.transact(23, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
            IActivityManager.Stub.getDefaultImpl().onActivityResumed(param2Int, param2IBinder);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void processRestarted(String param2String1, String param2String2, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeString(param2String1);
          parcel1.writeString(param2String2);
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(36, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
            IActivityManager.Stub.getDefaultImpl().processRestarted(param2String1, param2String2, param2Int);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void removeIntentSender(IBinder param2IBinder) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeStrongBinder(param2IBinder);
          if (!this.mRemote.transact(34, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
            IActivityManager.Stub.getDefaultImpl().removeIntentSender(param2IBinder);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setAppInactive(String param2String, boolean param2Boolean, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          boolean bool;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeString(param2String);
          if (param2Boolean) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(38, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
            IActivityManager.Stub.getDefaultImpl().setAppInactive(param2String, param2Boolean, param2Int);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public int startActivities(Intent[] param2ArrayOfIntent, String[] param2ArrayOfString, IBinder param2IBinder, Bundle param2Bundle, String param2String, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          parcel1.writeTypedArray((Parcelable[])param2ArrayOfIntent, 0);
          parcel1.writeStringArray(param2ArrayOfString);
          parcel1.writeStrongBinder(param2IBinder);
          if (param2Bundle != null) {
            parcel1.writeInt(1);
            param2Bundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int);
          try {
            if (!this.mRemote.transact(18, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
              param2Int = IActivityManager.Stub.getDefaultImpl().startActivities(param2ArrayOfIntent, param2ArrayOfString, param2IBinder, param2Bundle, param2String, param2Int);
              parcel2.recycle();
              parcel1.recycle();
              return param2Int;
            } 
            parcel2.readException();
            param2Int = parcel2.readInt();
            parcel2.recycle();
            parcel1.recycle();
            return param2Int;
          } finally {}
        } finally {}
        parcel2.recycle();
        parcel1.recycle();
        throw param2ArrayOfIntent;
      }
      
      public int startActivity(Intent param2Intent, ActivityInfo param2ActivityInfo, IBinder param2IBinder, Bundle param2Bundle, String param2String1, int param2Int1, String param2String2, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          if (param2Intent != null) {
            parcel1.writeInt(1);
            param2Intent.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (param2ActivityInfo != null) {
            parcel1.writeInt(1);
            param2ActivityInfo.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeStrongBinder(param2IBinder);
          if (param2Bundle != null) {
            parcel1.writeInt(1);
            param2Bundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String1);
          parcel1.writeInt(param2Int1);
          parcel1.writeString(param2String2);
          parcel1.writeInt(param2Int2);
          try {
            if (!this.mRemote.transact(19, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
              param2Int1 = IActivityManager.Stub.getDefaultImpl().startActivity(param2Intent, param2ActivityInfo, param2IBinder, param2Bundle, param2String1, param2Int1, param2String2, param2Int2);
              parcel2.recycle();
              parcel1.recycle();
              return param2Int1;
            } 
            parcel2.readException();
            param2Int1 = parcel2.readInt();
            parcel2.recycle();
            parcel1.recycle();
            return param2Int1;
          } finally {}
        } finally {}
        parcel2.recycle();
        parcel1.recycle();
        throw param2Intent;
      }
      
      public int startActivityFromHistory(Intent param2Intent) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
          if (param2Intent != null) {
            parcel1.writeInt(1);
            param2Intent.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(20, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
            return IActivityManager.Stub.getDefaultImpl().startActivityFromHistory(param2Intent); 
          parcel2.readException();
          return parcel2.readInt();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
    }
  }
  
  private static class Proxy implements IActivityManager {
    public static IActivityManager sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public IBinder acquireProviderClient(int param1Int, ProviderInfo param1ProviderInfo) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeInt(param1Int);
        if (param1ProviderInfo != null) {
          parcel1.writeInt(1);
          param1ProviderInfo.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(31, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
          return IActivityManager.Stub.getDefaultImpl().acquireProviderClient(param1Int, param1ProviderInfo); 
        parcel2.readException();
        return parcel2.readStrongBinder();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void addOrUpdateIntentSender(IntentSenderData param1IntentSenderData, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        if (param1IntentSenderData != null) {
          parcel1.writeInt(1);
          param1IntentSenderData.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(33, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
          IActivityManager.Stub.getDefaultImpl().addOrUpdateIntentSender(param1IntentSenderData, param1Int);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void appDoneExecuting(String param1String, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
          IActivityManager.Stub.getDefaultImpl().appDoneExecuting(param1String, param1Int);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public boolean broadcastFinish(IBinder param1IBinder) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeStrongBinder(param1IBinder);
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        if (!iBinder.transact(32, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
          bool = IActivityManager.Stub.getDefaultImpl().broadcastFinish(param1IBinder);
          return bool;
        } 
        parcel2.readException();
        int i = parcel2.readInt();
        if (i != 0)
          bool = true; 
        return bool;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public int checkPermission(boolean param1Boolean, String param1String, int param1Int1, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        boolean bool;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        if (param1Boolean) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int1);
        parcel1.writeInt(param1Int2);
        if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
          param1Int1 = IActivityManager.Stub.getDefaultImpl().checkPermission(param1Boolean, param1String, param1Int1, param1Int2);
          return param1Int1;
        } 
        parcel2.readException();
        param1Int1 = parcel2.readInt();
        return param1Int1;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void dump() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        if (!this.mRemote.transact(16, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
          IActivityManager.Stub.getDefaultImpl().dump();
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public boolean finishActivityAffinity(int param1Int, IBinder param1IBinder) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeInt(param1Int);
        parcel1.writeStrongBinder(param1IBinder);
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        if (!iBinder.transact(21, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
          bool = IActivityManager.Stub.getDefaultImpl().finishActivityAffinity(param1Int, param1IBinder);
          return bool;
        } 
        parcel2.readException();
        param1Int = parcel2.readInt();
        if (param1Int != 0)
          bool = true; 
        return bool;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public ComponentName getActivityClassForToken(int param1Int, IBinder param1IBinder) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeInt(param1Int);
        parcel1.writeStrongBinder(param1IBinder);
        if (!this.mRemote.transact(26, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
          return IActivityManager.Stub.getDefaultImpl().getActivityClassForToken(param1Int, param1IBinder); 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          ComponentName componentName = (ComponentName)ComponentName.CREATOR.createFromParcel(parcel2);
        } else {
          param1IBinder = null;
        } 
        return (ComponentName)param1IBinder;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public int getAppPid(String param1String1, int param1Int, String param1String2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeString(param1String1);
        parcel1.writeInt(param1Int);
        parcel1.writeString(param1String2);
        if (!this.mRemote.transact(42, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
          param1Int = IActivityManager.Stub.getDefaultImpl().getAppPid(param1String1, param1Int, param1String2);
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
    
    public String getAppProcessName(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(11, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
          return IActivityManager.Stub.getDefaultImpl().getAppProcessName(param1Int); 
        parcel2.readException();
        return parcel2.readString();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public ComponentName getCallingActivity(int param1Int, IBinder param1IBinder) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeInt(param1Int);
        parcel1.writeStrongBinder(param1IBinder);
        if (!this.mRemote.transact(28, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
          return IActivityManager.Stub.getDefaultImpl().getCallingActivity(param1Int, param1IBinder); 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          ComponentName componentName = (ComponentName)ComponentName.CREATOR.createFromParcel(parcel2);
        } else {
          param1IBinder = null;
        } 
        return (ComponentName)param1IBinder;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String getCallingPackage(int param1Int, IBinder param1IBinder) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeInt(param1Int);
        parcel1.writeStrongBinder(param1IBinder);
        if (!this.mRemote.transact(27, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
          return IActivityManager.Stub.getDefaultImpl().getCallingPackage(param1Int, param1IBinder); 
        parcel2.readException();
        return parcel2.readString();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public int getFreeStubCount() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
          return IActivityManager.Stub.getDefaultImpl().getFreeStubCount(); 
        parcel2.readException();
        return parcel2.readInt();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String getInitialPackage(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(17, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
          return IActivityManager.Stub.getDefaultImpl().getInitialPackage(param1Int); 
        parcel2.readException();
        return parcel2.readString();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public IntentSenderData getIntentSender(IBinder param1IBinder) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeStrongBinder(param1IBinder);
        if (!this.mRemote.transact(35, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
          return IActivityManager.Stub.getDefaultImpl().getIntentSender(param1IBinder); 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          IntentSenderData intentSenderData = (IntentSenderData)IntentSenderData.CREATOR.createFromParcel(parcel2);
        } else {
          param1IBinder = null;
        } 
        return (IntentSenderData)param1IBinder;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String getInterfaceDescriptor() {
      return "com.lody.virtual.server.interfaces.IActivityManager";
    }
    
    public String getPackageForToken(int param1Int, IBinder param1IBinder) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeInt(param1Int);
        parcel1.writeStrongBinder(param1IBinder);
        if (!this.mRemote.transact(30, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
          return IActivityManager.Stub.getDefaultImpl().getPackageForToken(param1Int, param1IBinder); 
        parcel2.readException();
        return parcel2.readString();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public List<String> getProcessPkgList(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(12, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
          return IActivityManager.Stub.getDefaultImpl().getProcessPkgList(param1Int); 
        parcel2.readException();
        return parcel2.createStringArrayList();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public VParceledListSlice getServices(String param1String, int param1Int1, int param1Int2, int param1Int3) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int1);
        parcel1.writeInt(param1Int2);
        parcel1.writeInt(param1Int3);
        if (!this.mRemote.transact(40, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
          return IActivityManager.Stub.getDefaultImpl().getServices(param1String, param1Int1, param1Int2, param1Int3); 
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
    
    public int getSystemPid() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
          return IActivityManager.Stub.getDefaultImpl().getSystemPid(); 
        parcel2.readException();
        return parcel2.readInt();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public int getSystemUid() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        if (!this.mRemote.transact(6, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
          return IActivityManager.Stub.getDefaultImpl().getSystemUid(); 
        parcel2.readException();
        return parcel2.readInt();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public AppTaskInfo getTaskInfo(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        AppTaskInfo appTaskInfo;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(29, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
          appTaskInfo = IActivityManager.Stub.getDefaultImpl().getTaskInfo(param1Int);
          return appTaskInfo;
        } 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          appTaskInfo = (AppTaskInfo)AppTaskInfo.CREATOR.createFromParcel(parcel2);
        } else {
          appTaskInfo = null;
        } 
        return appTaskInfo;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public int getUidByPid(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(7, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
          param1Int = IActivityManager.Stub.getDefaultImpl().getUidByPid(param1Int);
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
    
    public void handleDownloadCompleteIntent(Intent param1Intent) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        if (param1Intent != null) {
          parcel1.writeInt(1);
          param1Intent.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(41, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
          IActivityManager.Stub.getDefaultImpl().handleDownloadCompleteIntent(param1Intent);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public ClientConfig initProcess(String param1String1, String param1String2, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeString(param1String1);
        parcel1.writeString(param1String2);
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
          return IActivityManager.Stub.getDefaultImpl().initProcess(param1String1, param1String2, param1Int); 
        parcel2.readException();
        if (parcel2.readInt() != 0) {
          ClientConfig clientConfig = (ClientConfig)ClientConfig.CREATOR.createFromParcel(parcel2);
        } else {
          param1String1 = null;
        } 
        return (ClientConfig)param1String1;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public boolean isAppInactive(String param1String, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int);
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        if (!iBinder.transact(39, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
          bool = IActivityManager.Stub.getDefaultImpl().isAppInactive(param1String, param1Int);
          return bool;
        } 
        parcel2.readException();
        param1Int = parcel2.readInt();
        if (param1Int != 0)
          bool = true; 
        return bool;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public boolean isAppPid(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeInt(param1Int);
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        if (!iBinder.transact(10, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
          bool = IActivityManager.Stub.getDefaultImpl().isAppPid(param1Int);
          return bool;
        } 
        parcel2.readException();
        param1Int = parcel2.readInt();
        if (param1Int != 0)
          bool = true; 
        return bool;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public boolean isAppProcess(String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeString(param1String);
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        if (!iBinder.transact(8, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
          bool = IActivityManager.Stub.getDefaultImpl().isAppProcess(param1String);
          return bool;
        } 
        parcel2.readException();
        int i = parcel2.readInt();
        if (i != 0)
          bool = true; 
        return bool;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public boolean isAppRunning(String param1String, int param1Int, boolean param1Boolean) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        boolean bool2;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int);
        boolean bool1 = true;
        if (param1Boolean) {
          bool2 = true;
        } else {
          bool2 = false;
        } 
        parcel1.writeInt(bool2);
        if (!this.mRemote.transact(9, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
          param1Boolean = IActivityManager.Stub.getDefaultImpl().isAppRunning(param1String, param1Int, param1Boolean);
          return param1Boolean;
        } 
        parcel2.readException();
        param1Int = parcel2.readInt();
        if (param1Int != 0) {
          param1Boolean = bool1;
        } else {
          param1Boolean = false;
        } 
        return param1Boolean;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void killAllApps() throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        if (!this.mRemote.transact(13, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
          IActivityManager.Stub.getDefaultImpl().killAllApps();
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void killAppByPkg(String param1String, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(14, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
          IActivityManager.Stub.getDefaultImpl().killAppByPkg(param1String, param1Int);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void killApplicationProcess(String param1String, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(15, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
          IActivityManager.Stub.getDefaultImpl().killApplicationProcess(param1String, param1Int);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void notifyBadgerChange(BadgerInfo param1BadgerInfo) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        if (param1BadgerInfo != null) {
          parcel1.writeInt(1);
          param1BadgerInfo.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(37, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
          IActivityManager.Stub.getDefaultImpl().notifyBadgerChange(param1BadgerInfo);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void onActivityCreated(IBinder param1IBinder1, IBinder param1IBinder2, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeStrongBinder(param1IBinder1);
        parcel1.writeStrongBinder(param1IBinder2);
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(22, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
          IActivityManager.Stub.getDefaultImpl().onActivityCreated(param1IBinder1, param1IBinder2, param1Int);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public boolean onActivityDestroyed(int param1Int, IBinder param1IBinder) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeInt(param1Int);
        parcel1.writeStrongBinder(param1IBinder);
        IBinder iBinder = this.mRemote;
        boolean bool = false;
        if (!iBinder.transact(24, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
          bool = IActivityManager.Stub.getDefaultImpl().onActivityDestroyed(param1Int, param1IBinder);
          return bool;
        } 
        parcel2.readException();
        param1Int = parcel2.readInt();
        if (param1Int != 0)
          bool = true; 
        return bool;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void onActivityFinish(int param1Int, IBinder param1IBinder) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeInt(param1Int);
        parcel1.writeStrongBinder(param1IBinder);
        if (!this.mRemote.transact(25, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
          IActivityManager.Stub.getDefaultImpl().onActivityFinish(param1Int, param1IBinder);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void onActivityResumed(int param1Int, IBinder param1IBinder) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeInt(param1Int);
        parcel1.writeStrongBinder(param1IBinder);
        if (!this.mRemote.transact(23, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
          IActivityManager.Stub.getDefaultImpl().onActivityResumed(param1Int, param1IBinder);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void processRestarted(String param1String1, String param1String2, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeString(param1String1);
        parcel1.writeString(param1String2);
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(36, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
          IActivityManager.Stub.getDefaultImpl().processRestarted(param1String1, param1String2, param1Int);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void removeIntentSender(IBinder param1IBinder) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeStrongBinder(param1IBinder);
        if (!this.mRemote.transact(34, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
          IActivityManager.Stub.getDefaultImpl().removeIntentSender(param1IBinder);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setAppInactive(String param1String, boolean param1Boolean, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        boolean bool;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeString(param1String);
        if (param1Boolean) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(38, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
          IActivityManager.Stub.getDefaultImpl().setAppInactive(param1String, param1Boolean, param1Int);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public int startActivities(Intent[] param1ArrayOfIntent, String[] param1ArrayOfString, IBinder param1IBinder, Bundle param1Bundle, String param1String, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        parcel1.writeTypedArray((Parcelable[])param1ArrayOfIntent, 0);
        parcel1.writeStringArray(param1ArrayOfString);
        parcel1.writeStrongBinder(param1IBinder);
        if (param1Bundle != null) {
          parcel1.writeInt(1);
          param1Bundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int);
        try {
          if (!this.mRemote.transact(18, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
            param1Int = IActivityManager.Stub.getDefaultImpl().startActivities(param1ArrayOfIntent, param1ArrayOfString, param1IBinder, param1Bundle, param1String, param1Int);
            parcel2.recycle();
            parcel1.recycle();
            return param1Int;
          } 
          parcel2.readException();
          param1Int = parcel2.readInt();
          parcel2.recycle();
          parcel1.recycle();
          return param1Int;
        } finally {}
      } finally {}
      parcel2.recycle();
      parcel1.recycle();
      throw param1ArrayOfIntent;
    }
    
    public int startActivity(Intent param1Intent, ActivityInfo param1ActivityInfo, IBinder param1IBinder, Bundle param1Bundle, String param1String1, int param1Int1, String param1String2, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        if (param1Intent != null) {
          parcel1.writeInt(1);
          param1Intent.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (param1ActivityInfo != null) {
          parcel1.writeInt(1);
          param1ActivityInfo.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeStrongBinder(param1IBinder);
        if (param1Bundle != null) {
          parcel1.writeInt(1);
          param1Bundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String1);
        parcel1.writeInt(param1Int1);
        parcel1.writeString(param1String2);
        parcel1.writeInt(param1Int2);
        try {
          if (!this.mRemote.transact(19, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null) {
            param1Int1 = IActivityManager.Stub.getDefaultImpl().startActivity(param1Intent, param1ActivityInfo, param1IBinder, param1Bundle, param1String1, param1Int1, param1String2, param1Int2);
            parcel2.recycle();
            parcel1.recycle();
            return param1Int1;
          } 
          parcel2.readException();
          param1Int1 = parcel2.readInt();
          parcel2.recycle();
          parcel1.recycle();
          return param1Int1;
        } finally {}
      } finally {}
      parcel2.recycle();
      parcel1.recycle();
      throw param1Intent;
    }
    
    public int startActivityFromHistory(Intent param1Intent) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IActivityManager");
        if (param1Intent != null) {
          parcel1.writeInt(1);
          param1Intent.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(20, parcel1, parcel2, 0) && IActivityManager.Stub.getDefaultImpl() != null)
          return IActivityManager.Stub.getDefaultImpl().startActivityFromHistory(param1Intent); 
        parcel2.readException();
        return parcel2.readInt();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\interfaces\IActivityManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */