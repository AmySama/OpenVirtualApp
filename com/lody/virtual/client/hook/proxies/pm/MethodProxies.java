package com.lody.virtual.client.hook.proxies.pm;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageDeleteObserver2;
import android.content.pm.IPackageInstallerCallback;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.RemoteException;
import android.text.TextUtils;
import com.lody.virtual.client.VClient;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.fixer.ComponentFixer;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.utils.MethodParameterUtils;
import com.lody.virtual.client.ipc.VPackageManager;
import com.lody.virtual.helper.compat.ParceledListSliceCompat;
import com.lody.virtual.helper.utils.ArrayUtils;
import com.lody.virtual.helper.utils.FileUtils;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.os.VUserHandle;
import com.lody.virtual.server.IPackageInstaller;
import com.lody.virtual.server.pm.installer.SessionInfo;
import com.lody.virtual.server.pm.installer.SessionParams;
import java.io.File;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import mirror.android.content.pm.ParceledListSlice;

class MethodProxies {
  private static final int MATCH_ANY_USER = 4194304;
  
  private static final int MATCH_FACTORY_ONLY = 2097152;
  
  static class ActivitySupportsIntent extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      ComponentName componentName = (ComponentName)param1VarArgs[0];
      param1Object = param1VarArgs[1];
      String str = (String)param1VarArgs[2];
      return Boolean.valueOf(VPackageManager.get().activitySupportsIntent(componentName, (Intent)param1Object, str));
    }
    
    public String getMethodName() {
      return "activitySupportsIntent";
    }
  }
  
  static class AddPackageToPreferred extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "addPackageToPreferred";
    }
  }
  
  static class CanForwardTo extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      boolean bool;
      if (((Integer)param1VarArgs[2]).intValue() == ((Integer)param1VarArgs[3]).intValue()) {
        bool = true;
      } else {
        bool = false;
      } 
      return Boolean.valueOf(bool);
    }
    
    public String getMethodName() {
      return "canForwardTo";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class CanRequestPackageInstalls extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      if (VirtualCore.get().getAppCallback() != null)
        return Boolean.valueOf(true); 
      MethodParameterUtils.replaceFirstAppPkg(param1VarArgs);
      replaceLastUserId(param1VarArgs);
      return super.call(param1Object, param1Method, param1VarArgs);
    }
    
    public String getMethodName() {
      return "canRequestPackageInstalls";
    }
  }
  
  static class CheckPackageStartable extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      if (isAppPkg((String)param1VarArgs[0]))
        return Integer.valueOf(0); 
      replaceLastUserId(param1VarArgs);
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "checkPackageStartable";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class CheckPermission extends MethodProxy {
    public Object afterCall(Object param1Object1, Method param1Method, Object[] param1ArrayOfObject, Object param1Object2) throws Throwable {
      return super.afterCall(param1Object1, param1Method, param1ArrayOfObject, param1Object2);
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1VarArgs[0];
      String str = (String)param1VarArgs[1];
      int i = VUserHandle.myUserId();
      return Integer.valueOf(VPackageManager.get().checkPermission((String)param1Object, str, i));
    }
    
    public String getMethodName() {
      return "checkPermission";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class CheckSignatures extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      String str;
      if (param1VarArgs.length == 2 && param1VarArgs[0] instanceof String && param1VarArgs[1] instanceof String) {
        param1Object = param1VarArgs[0];
        str = (String)param1VarArgs[1];
        return TextUtils.equals((CharSequence)param1Object, str) ? Integer.valueOf(0) : Integer.valueOf(VPackageManager.get().checkSignatures((String)param1Object, str));
      } 
      return str.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "checkSignatures";
    }
  }
  
  static class ClearPackagePersistentPreferredActivities extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      MethodParameterUtils.replaceFirstAppPkg(param1VarArgs);
      replaceLastUserId(param1VarArgs);
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "clearPackagePersistentPreferredActivities";
    }
  }
  
  static class ClearPackagePreferredActivities extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      MethodParameterUtils.replaceFirstAppPkg(param1VarArgs);
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "clearPackagePreferredActivities";
    }
  }
  
  static class DeleteApplicationCacheFiles extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      String str = (String)param1VarArgs[0];
      IPackageDataObserver iPackageDataObserver = (IPackageDataObserver)param1VarArgs[1];
      if (str.equals(getAppPkg())) {
        ApplicationInfo applicationInfo = VPackageManager.get().getApplicationInfo(str, 0, getAppUserId());
        if (applicationInfo != null) {
          param1Object = new File(applicationInfo.dataDir);
          FileUtils.deleteDir((File)param1Object);
          param1Object.mkdirs();
          if (Build.VERSION.SDK_INT >= 24) {
            param1Object = new File(applicationInfo.deviceProtectedDataDir);
            FileUtils.deleteDir((File)param1Object);
            param1Object.mkdirs();
          } 
          if (iPackageDataObserver != null)
            iPackageDataObserver.onRemoveCompleted(str, true); 
          return Integer.valueOf(0);
        } 
      } 
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "deleteApplicationCacheFiles";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class DeletePackage extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1VarArgs[0];
      try {
        VirtualCore.get().uninstallPackage((String)param1Object);
        IPackageDeleteObserver2 iPackageDeleteObserver2 = (IPackageDeleteObserver2)param1VarArgs[1];
        if (iPackageDeleteObserver2 != null)
          iPackageDeleteObserver2.onPackageDeleted((String)param1Object, 0, "done."); 
      } finally {}
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "deletePackage";
    }
  }
  
  static class FreeStorage extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = ArrayUtils.getFirst(param1VarArgs, IntentSender.class);
      if (param1Object != null)
        param1Object.sendIntent(getHostContext(), 0, null, null, null); 
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "freeStorage";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class FreeStorageAndNotify extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1VarArgs[param1VarArgs.length - 1];
      if (param1Object != null)
        param1Object.onRemoveCompleted(getAppPkg(), true); 
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "freeStorageAndNotify";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class GetActivityInfo extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      ComponentName componentName = (ComponentName)param1VarArgs[0];
      if (getHostPkg().equals(componentName.getPackageName()))
        return param1Method.invoke(param1Object, param1VarArgs); 
      int i = VUserHandle.myUserId();
      int j = ((Integer)param1VarArgs[1]).intValue();
      ActivityInfo activityInfo2 = VPackageManager.get().getActivityInfo(componentName, j, i);
      ActivityInfo activityInfo1 = activityInfo2;
      if (activityInfo2 == null) {
        replaceLastUserId(param1VarArgs);
        activityInfo1 = (ActivityInfo)param1Method.invoke(param1Object, param1VarArgs);
        if (activityInfo1 == null || !isOutsidePackage(activityInfo1.packageName))
          return null; 
        ComponentFixer.fixOutsideComponentInfo((ComponentInfo)activityInfo1);
      } 
      return activityInfo1;
    }
    
    public String getMethodName() {
      return "getActivityInfo";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class GetApplicationBlockedSettingAsUser extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      MethodParameterUtils.replaceFirstAppPkg(param1VarArgs);
      replaceLastUserId(param1VarArgs);
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "getApplicationBlockedSettingAsUser";
    }
  }
  
  static class GetApplicationEnabledSetting extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      String str = (String)param1VarArgs[0];
      if (isAppPkg(str))
        return Integer.valueOf(1); 
      if (isOutsidePackage(str)) {
        param1VarArgs[1] = Integer.valueOf(0);
        return param1Method.invoke(param1Object, param1VarArgs);
      } 
      return Integer.valueOf(2);
    }
    
    public String getMethodName() {
      return "getApplicationEnabledSetting";
    }
  }
  
  static class GetApplicationInfo extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      String str = (String)param1VarArgs[0];
      int i = ((Integer)param1VarArgs[1]).intValue();
      int j = VUserHandle.myUserId();
      if (str.equals("com.android.defcontainer"))
        return VPackageManager.get().getApplicationInfo("com.android.providers.downloads", i, j); 
      if (getHostPkg().equals(str)) {
        replaceLastUserId(param1VarArgs);
        return param1Method.invoke(param1Object, param1VarArgs);
      } 
      ApplicationInfo applicationInfo = VPackageManager.get().getApplicationInfo(str, i, j);
      if (applicationInfo != null)
        return applicationInfo; 
      replaceLastUserId(param1VarArgs);
      param1Object = param1Method.invoke(param1Object, param1VarArgs);
      if (param1Object == null || !isOutsidePackage(((ApplicationInfo)param1Object).packageName))
        return null; 
      ComponentFixer.fixOutsideApplicationInfo((ApplicationInfo)param1Object);
      return param1Object;
    }
    
    public String getMethodName() {
      return "getApplicationInfo";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class GetComponentEnabledSetting extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1VarArgs[0];
      return Integer.valueOf(VPackageManager.get().getComponentEnabledSetting((ComponentName)param1Object, getAppUserId()));
    }
    
    public String getMethodName() {
      return "getComponentEnabledSetting";
    }
  }
  
  static class GetInstalledApplications extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      boolean bool = ParceledListSliceCompat.isReturnParceledListSlice(param1Method);
      int i = ((Integer)param1VarArgs[0]).intValue();
      int j = VUserHandle.myUserId();
      List list2 = VPackageManager.get().getInstalledApplications(i, j);
      Object object = param1Method.invoke(param1Object, param1VarArgs);
      param1Object = object;
      if (bool)
        param1Object = ParceledListSlice.getList.call(object, new Object[0]); 
      List list1 = (List)param1Object;
      param1Object = list1.iterator();
      while (param1Object.hasNext()) {
        object = param1Object.next();
        if (VirtualCore.get().isAppInstalled(((ApplicationInfo)object).packageName) || !isOutsidePackage(((ApplicationInfo)object).packageName))
          param1Object.remove(); 
        ComponentFixer.fixOutsideApplicationInfo((ApplicationInfo)object);
      } 
      list2.addAll(list1);
      return bool ? ParceledListSliceCompat.create(list2) : list2;
    }
    
    public String getMethodName() {
      return "getInstalledApplications";
    }
  }
  
  static class GetInstalledPackages extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      boolean bool = ParceledListSliceCompat.isReturnParceledListSlice(param1Method);
      int i = ((Integer)param1VarArgs[0]).intValue();
      int j = VUserHandle.myUserId();
      List list = VPackageManager.get().getInstalledPackages(i, j);
      replaceLastUserId(param1VarArgs);
      Object object = param1Method.invoke(param1Object, param1VarArgs);
      param1Object = object;
      if (bool)
        param1Object = ParceledListSlice.getList.call(object, new Object[0]); 
      object = param1Object;
      param1Object = object.iterator();
      while (param1Object.hasNext()) {
        PackageInfo packageInfo = param1Object.next();
        if (VirtualCore.get().isAppInstalled(packageInfo.packageName) || !isOutsidePackage(packageInfo.packageName))
          param1Object.remove(); 
        ComponentFixer.fixOutsideApplicationInfo(packageInfo.applicationInfo);
      } 
      list.addAll((Collection)object);
      return ParceledListSliceCompat.isReturnParceledListSlice(param1Method) ? ParceledListSliceCompat.create(list) : list;
    }
    
    public String getMethodName() {
      return "getInstalledPackages";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class GetInstallerPackageName extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return "com.android.vending";
    }
    
    public String getMethodName() {
      return "getInstallerPackageName";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class GetPackageGids extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      MethodParameterUtils.replaceFirstAppPkg(param1VarArgs);
      replaceLastUserId(param1VarArgs);
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "getPackageGids";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class GetPackageGidsEtc extends GetPackageGids {
    public String getMethodName() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(super.getMethodName());
      stringBuilder.append("Etc");
      return stringBuilder.toString();
    }
  }
  
  static final class GetPackageInfo extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      String str = (String)param1VarArgs[0];
      int i = ((Integer)param1VarArgs[1]).intValue();
      int j = VUserHandle.myUserId();
      int k = i;
      if ((0x400000 & i) != 0) {
        k = i & 0xFFBFFFFF;
        param1VarArgs[1] = Integer.valueOf(k);
      } 
      if ((0x200000 & k) != 0) {
        replaceLastUserId(param1VarArgs);
        return param1Method.invoke(param1Object, param1VarArgs);
      } 
      PackageInfo packageInfo = VPackageManager.get().getPackageInfo(str, k, j);
      if (packageInfo != null)
        return packageInfo; 
      replaceLastUserId(param1VarArgs);
      param1Object = param1Method.invoke(param1Object, param1VarArgs);
      if (param1Object != null && isOutsidePackage(((PackageInfo)param1Object).packageName)) {
        ComponentFixer.fixOutsideApplicationInfo(((PackageInfo)param1Object).applicationInfo);
        return param1Object;
      } 
      return null;
    }
    
    public String getMethodName() {
      return "getPackageInfo";
    }
  }
  
  static class GetPackageInstaller extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1Method.invoke(param1Object, param1VarArgs);
      final IPackageInstaller vInstaller = VPackageManager.get().getPackageInstaller();
      return Proxy.newProxyInstance(param1Object.getClass().getClassLoader(), param1Object.getClass().getInterfaces(), new InvocationHandler() {
            private Object createSession(Object param2Object, Method param2Method, Object[] param2ArrayOfObject) throws RemoteException {
              param2Object = SessionParams.create((PackageInstaller.SessionParams)param2ArrayOfObject[0]);
              String str = (String)param2ArrayOfObject[1];
              return Integer.valueOf(vInstaller.createSession((SessionParams)param2Object, str, VUserHandle.myUserId()));
            }
            
            public Object invoke(Object param2Object, Method param2Method, Object[] param2ArrayOfObject) throws Throwable {
              List list2;
              Iterator<SessionInfo> iterator2;
              List list1;
              Iterator<SessionInfo> iterator1;
              boolean bool;
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("call ");
              stringBuilder.append(param2Method.getName());
              stringBuilder.append(" -> ");
              stringBuilder.append(Arrays.toString(param2ArrayOfObject));
              VLog.e("PackageInstaller", stringBuilder.toString());
              String str = param2Method.getName();
              int i = str.hashCode();
              Integer integer = Integer.valueOf(0);
              switch (i) {
                default:
                  i = -1;
                  break;
                case 1788161260:
                  if (str.equals("openSession")) {
                    i = 5;
                    break;
                  } 
                case 1738611873:
                  if (str.equals("unregisterCallback")) {
                    i = 10;
                    break;
                  } 
                case 1568181855:
                  if (str.equals("getMySessions")) {
                    i = 8;
                    break;
                  } 
                case 1238099456:
                  if (str.equals("updateSessionAppLabel")) {
                    i = 3;
                    break;
                  } 
                case 1170196863:
                  if (str.equals("setPermissionsResult")) {
                    i = 11;
                    break;
                  } 
                case 938656808:
                  if (str.equals("getAllSessions")) {
                    i = 7;
                    break;
                  } 
                case -63461894:
                  if (str.equals("createSession")) {
                    i = 1;
                    break;
                  } 
                case -93516191:
                  if (str.equals("abandonSession")) {
                    i = 4;
                    break;
                  } 
                case -298116903:
                  if (str.equals("getStagedSessions")) {
                    i = 0;
                    break;
                  } 
                case -403218424:
                  if (str.equals("registerCallback")) {
                    i = 9;
                    break;
                  } 
                case -652885011:
                  if (str.equals("updateSessionAppIcon")) {
                    i = 2;
                    break;
                  } 
                case -663066834:
                  if (str.equals("getSessionInfo")) {
                    i = 6;
                    break;
                  } 
                case -1776922004:
                  if (str.equals("toString")) {
                    i = 12;
                    break;
                  } 
              } 
              switch (i) {
                default:
                  VLog.printStackTrace("PackageInstaller");
                  param2Object = new StringBuilder();
                  param2Object.append("Not support PackageInstaller method : ");
                  param2Object.append(param2Method.getName());
                  throw new RuntimeException(param2Object.toString());
                case 12:
                  return "PackageInstaller";
                case 11:
                  i = ((Integer)param2ArrayOfObject[0]).intValue();
                  bool = ((Boolean)param2ArrayOfObject[1]).booleanValue();
                  vInstaller.setPermissionsResult(i, bool);
                  return integer;
                case 10:
                  param2Object = param2ArrayOfObject[0];
                  vInstaller.unregisterCallback((IPackageInstallerCallback)param2Object);
                  return integer;
                case 9:
                  param2Object = param2ArrayOfObject[0];
                  vInstaller.registerCallback((IPackageInstallerCallback)param2Object, VUserHandle.myUserId());
                  return integer;
                case 8:
                  param2Object = param2ArrayOfObject[0];
                  i = ((Integer)param2ArrayOfObject[1]).intValue();
                  list2 = vInstaller.getMySessions((String)param2Object, i).getList();
                  param2Object = new ArrayList(list2.size());
                  iterator2 = list2.iterator();
                  while (iterator2.hasNext())
                    param2Object.add(((SessionInfo)iterator2.next()).alloc()); 
                  return ParceledListSliceCompat.create((List)param2Object);
                case 7:
                  i = ((Integer)param2ArrayOfObject[0]).intValue();
                  list1 = vInstaller.getAllSessions(i).getList();
                  param2Object = new ArrayList(list1.size());
                  iterator1 = list1.iterator();
                  while (iterator1.hasNext())
                    param2Object.add(((SessionInfo)iterator1.next()).alloc()); 
                  return ParceledListSliceCompat.create((List)param2Object);
                case 6:
                  param2Object = vInstaller.getSessionInfo(((Integer)param2ArrayOfObject[0]).intValue());
                  return (param2Object != null) ? param2Object.alloc() : null;
                case 5:
                  return vInstaller.openSession(((Integer)param2ArrayOfObject[0]).intValue());
                case 4:
                  vInstaller.abandonSession(((Integer)param2ArrayOfObject[0]).intValue());
                  return integer;
                case 3:
                  i = ((Integer)param2ArrayOfObject[0]).intValue();
                  param2Object = param2ArrayOfObject[1];
                  vInstaller.updateSessionAppLabel(i, (String)param2Object);
                  return integer;
                case 2:
                  i = ((Integer)param2ArrayOfObject[0]).intValue();
                  param2Object = param2ArrayOfObject[1];
                  vInstaller.updateSessionAppIcon(i, (Bitmap)param2Object);
                  return integer;
                case 1:
                  return createSession(param2Object, (Method)iterator1, param2ArrayOfObject);
                case 0:
                  break;
              } 
              return ParceledListSliceCompat.create(Collections.EMPTY_LIST);
            }
          });
    }
    
    public String getMethodName() {
      return "getPackageInstaller";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  class null implements InvocationHandler {
    private Object createSession(Object param1Object, Method param1Method, Object[] param1ArrayOfObject) throws RemoteException {
      param1Object = SessionParams.create((PackageInstaller.SessionParams)param1ArrayOfObject[0]);
      String str = (String)param1ArrayOfObject[1];
      return Integer.valueOf(vInstaller.createSession((SessionParams)param1Object, str, VUserHandle.myUserId()));
    }
    
    public Object invoke(Object param1Object, Method param1Method, Object[] param1ArrayOfObject) throws Throwable {
      List list2;
      Iterator<SessionInfo> iterator2;
      List list1;
      Iterator<SessionInfo> iterator1;
      boolean bool;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("call ");
      stringBuilder.append(param1Method.getName());
      stringBuilder.append(" -> ");
      stringBuilder.append(Arrays.toString(param1ArrayOfObject));
      VLog.e("PackageInstaller", stringBuilder.toString());
      String str = param1Method.getName();
      int i = str.hashCode();
      Integer integer = Integer.valueOf(0);
      switch (i) {
        default:
          i = -1;
          break;
        case 1788161260:
          if (str.equals("openSession")) {
            i = 5;
            break;
          } 
        case 1738611873:
          if (str.equals("unregisterCallback")) {
            i = 10;
            break;
          } 
        case 1568181855:
          if (str.equals("getMySessions")) {
            i = 8;
            break;
          } 
        case 1238099456:
          if (str.equals("updateSessionAppLabel")) {
            i = 3;
            break;
          } 
        case 1170196863:
          if (str.equals("setPermissionsResult")) {
            i = 11;
            break;
          } 
        case 938656808:
          if (str.equals("getAllSessions")) {
            i = 7;
            break;
          } 
        case -63461894:
          if (str.equals("createSession")) {
            i = 1;
            break;
          } 
        case -93516191:
          if (str.equals("abandonSession")) {
            i = 4;
            break;
          } 
        case -298116903:
          if (str.equals("getStagedSessions")) {
            i = 0;
            break;
          } 
        case -403218424:
          if (str.equals("registerCallback")) {
            i = 9;
            break;
          } 
        case -652885011:
          if (str.equals("updateSessionAppIcon")) {
            i = 2;
            break;
          } 
        case -663066834:
          if (str.equals("getSessionInfo")) {
            i = 6;
            break;
          } 
        case -1776922004:
          if (str.equals("toString")) {
            i = 12;
            break;
          } 
      } 
      switch (i) {
        default:
          VLog.printStackTrace("PackageInstaller");
          param1Object = new StringBuilder();
          param1Object.append("Not support PackageInstaller method : ");
          param1Object.append(param1Method.getName());
          throw new RuntimeException(param1Object.toString());
        case 12:
          return "PackageInstaller";
        case 11:
          i = ((Integer)param1ArrayOfObject[0]).intValue();
          bool = ((Boolean)param1ArrayOfObject[1]).booleanValue();
          vInstaller.setPermissionsResult(i, bool);
          return integer;
        case 10:
          param1Object = param1ArrayOfObject[0];
          vInstaller.unregisterCallback((IPackageInstallerCallback)param1Object);
          return integer;
        case 9:
          param1Object = param1ArrayOfObject[0];
          vInstaller.registerCallback((IPackageInstallerCallback)param1Object, VUserHandle.myUserId());
          return integer;
        case 8:
          param1Object = param1ArrayOfObject[0];
          i = ((Integer)param1ArrayOfObject[1]).intValue();
          list2 = vInstaller.getMySessions((String)param1Object, i).getList();
          param1Object = new ArrayList(list2.size());
          iterator2 = list2.iterator();
          while (iterator2.hasNext())
            param1Object.add(((SessionInfo)iterator2.next()).alloc()); 
          return ParceledListSliceCompat.create((List)param1Object);
        case 7:
          i = ((Integer)param1ArrayOfObject[0]).intValue();
          list1 = vInstaller.getAllSessions(i).getList();
          param1Object = new ArrayList(list1.size());
          iterator1 = list1.iterator();
          while (iterator1.hasNext())
            param1Object.add(((SessionInfo)iterator1.next()).alloc()); 
          return ParceledListSliceCompat.create((List)param1Object);
        case 6:
          param1Object = vInstaller.getSessionInfo(((Integer)param1ArrayOfObject[0]).intValue());
          return (param1Object != null) ? param1Object.alloc() : null;
        case 5:
          return vInstaller.openSession(((Integer)param1ArrayOfObject[0]).intValue());
        case 4:
          vInstaller.abandonSession(((Integer)param1ArrayOfObject[0]).intValue());
          return integer;
        case 3:
          i = ((Integer)param1ArrayOfObject[0]).intValue();
          param1Object = param1ArrayOfObject[1];
          vInstaller.updateSessionAppLabel(i, (String)param1Object);
          return integer;
        case 2:
          i = ((Integer)param1ArrayOfObject[0]).intValue();
          param1Object = param1ArrayOfObject[1];
          vInstaller.updateSessionAppIcon(i, (Bitmap)param1Object);
          return integer;
        case 1:
          return createSession(param1Object, (Method)iterator1, param1ArrayOfObject);
        case 0:
          break;
      } 
      return ParceledListSliceCompat.create(Collections.EMPTY_LIST);
    }
  }
  
  static class GetPackageUid extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      String str = (String)param1VarArgs[0];
      MethodParameterUtils.replaceFirstAppPkg(param1VarArgs);
      replaceLastUserId(param1VarArgs);
      return (isAppPkg(str) || isOutsidePackage(str)) ? param1Method.invoke(param1Object, param1VarArgs) : Integer.valueOf(-1);
    }
    
    public String getMethodName() {
      return "getPackageUid";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class GetPackageUidEtc extends GetPackageUid {
    public String getMethodName() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(super.getMethodName());
      stringBuilder.append("Etc");
      return stringBuilder.toString();
    }
  }
  
  static class GetPackagesForUid extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      if (VClient.get().getClientConfig() == null)
        return param1Method.invoke(param1Object, param1VarArgs); 
      int i = ((Integer)param1VarArgs[0]).intValue();
      if (i == 1000)
        return param1Method.invoke(param1Object, param1VarArgs); 
      int j = i;
      if (i == getRealUid())
        j = VClient.get().getVUid(); 
      String[] arrayOfString = VPackageManager.get().getPackagesForUid(j);
      param1Object = arrayOfString;
      if (arrayOfString == null)
        param1Object = null; 
      return param1Object;
    }
    
    public String getMethodName() {
      return "getPackagesForUid";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class GetPermissionFlags extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      String str1 = (String)param1VarArgs[0];
      String str2 = (String)param1VarArgs[1];
      ((Integer)param1VarArgs[2]).intValue();
      if (VPackageManager.get().getPermissionInfo(str1, 0) != null)
        return Integer.valueOf(0); 
      param1VarArgs[2] = Integer.valueOf(getRealUserId());
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "getPermissionFlags";
    }
  }
  
  static class GetPermissionGroupInfo extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      String str = (String)param1VarArgs[0];
      int i = ((Integer)param1VarArgs[1]).intValue();
      PermissionGroupInfo permissionGroupInfo = VPackageManager.get().getPermissionGroupInfo(str, i);
      return (permissionGroupInfo != null) ? permissionGroupInfo : super.call(param1Object, param1Method, param1VarArgs);
    }
    
    public String getMethodName() {
      return "getPermissionGroupInfo";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class GetPermissionInfo extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      String str = (String)param1VarArgs[0];
      int i = ((Integer)param1VarArgs[param1VarArgs.length - 1]).intValue();
      PermissionInfo permissionInfo = VPackageManager.get().getPermissionInfo(str, i);
      return (permissionInfo != null) ? permissionInfo : super.call(param1Object, param1Method, param1VarArgs);
    }
    
    public String getMethodName() {
      return "getPermissionInfo";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class GetPermissions extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      replaceLastUserId(param1VarArgs);
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "getPermissions";
    }
  }
  
  static class GetPersistentApplications extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return ParceledListSliceCompat.isReturnParceledListSlice(param1Method) ? ParceledListSliceCompat.create(new ArrayList(0)) : new ArrayList(0);
    }
    
    public String getMethodName() {
      return "getPersistentApplications";
    }
  }
  
  static class GetPreferredActivities extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      MethodParameterUtils.replaceLastAppPkg(param1VarArgs);
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "getPreferredActivities";
    }
  }
  
  static class GetProviderInfo extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      ComponentName componentName = (ComponentName)param1VarArgs[0];
      int i = ((Integer)param1VarArgs[1]).intValue();
      if (getHostPkg().equals(componentName.getPackageName())) {
        replaceLastUserId(param1VarArgs);
        return param1Method.invoke(param1Object, param1VarArgs);
      } 
      int j = VUserHandle.myUserId();
      ProviderInfo providerInfo2 = VPackageManager.get().getProviderInfo(componentName, i, j);
      ProviderInfo providerInfo1 = providerInfo2;
      if (providerInfo2 == null) {
        replaceLastUserId(param1VarArgs);
        providerInfo1 = (ProviderInfo)param1Method.invoke(param1Object, param1VarArgs);
        if (providerInfo1 == null || !isOutsidePackage(providerInfo1.packageName))
          return null; 
        ComponentFixer.fixOutsideComponentInfo((ComponentInfo)providerInfo1);
      } 
      return providerInfo1;
    }
    
    public String getMethodName() {
      return "getProviderInfo";
    }
  }
  
  static class GetReceiverInfo extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      ComponentName componentName = (ComponentName)param1VarArgs[0];
      if (getHostPkg().equals(componentName.getPackageName()))
        return param1Method.invoke(param1Object, param1VarArgs); 
      int i = ((Integer)param1VarArgs[1]).intValue();
      ActivityInfo activityInfo2 = VPackageManager.get().getReceiverInfo(componentName, i, 0);
      ActivityInfo activityInfo1 = activityInfo2;
      if (activityInfo2 == null) {
        replaceLastUserId(param1VarArgs);
        activityInfo1 = (ActivityInfo)param1Method.invoke(param1Object, param1VarArgs);
        if (activityInfo1 == null || !isOutsidePackage(activityInfo1.packageName))
          return null; 
        ComponentFixer.fixOutsideComponentInfo((ComponentInfo)activityInfo1);
      } 
      return activityInfo1;
    }
    
    public String getMethodName() {
      return "getReceiverInfo";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class GetServiceInfo extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      ComponentName componentName = (ComponentName)param1VarArgs[0];
      int i = ((Integer)param1VarArgs[1]).intValue();
      int j = VUserHandle.myUserId();
      ServiceInfo serviceInfo = VPackageManager.get().getServiceInfo(componentName, i, j);
      if (serviceInfo != null)
        return serviceInfo; 
      replaceLastUserId(param1VarArgs);
      param1Object = param1Method.invoke(param1Object, param1VarArgs);
      if (param1Object == null || !isOutsidePackage(((ServiceInfo)param1Object).packageName))
        return null; 
      ComponentFixer.fixOutsideComponentInfo((ComponentInfo)param1Object);
      return param1Object;
    }
    
    public String getMethodName() {
      return "getServiceInfo";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class GetSharedLibraries extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      int i = ((Integer)param1VarArgs[1]).intValue();
      if ((0x400000 & i) != 0)
        param1VarArgs[1] = Integer.valueOf(i & 0xFFBFFFFF); 
      param1VarArgs[0] = getHostPkg();
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "getSharedLibraries";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class GetUidForSharedUser extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1VarArgs[0];
      return Integer.valueOf(VirtualCore.get().getUidForSharedUser((String)param1Object));
    }
    
    public String getMethodName() {
      return "getUidForSharedUser";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class IsPackageAvailable extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      if (isAppPkg((String)param1VarArgs[0]))
        return Boolean.valueOf(true); 
      replaceLastUserId(param1VarArgs);
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "isPackageAvailable";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class IsPackageForzen extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return Boolean.valueOf(false);
    }
    
    public String getMethodName() {
      return "isPackageForzen";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class QueryContentProviders extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      boolean bool = ParceledListSliceCompat.isReturnParceledListSlice(param1Method);
      String str = (String)param1VarArgs[0];
      int i = ((Integer)param1VarArgs[1]).intValue();
      ((Integer)param1VarArgs[2]).intValue();
      List list = VPackageManager.get().queryContentProviders(str, i, 0);
      Object object = param1Method.invoke(param1Object, param1VarArgs);
      if (object != null) {
        param1Object = object;
        if (bool)
          param1Object = ParceledListSlice.getList.call(object, new Object[0]); 
        param1Object = param1Object;
        Iterator<ProviderInfo> iterator = param1Object.iterator();
        while (iterator.hasNext()) {
          object = iterator.next();
          if (isAppPkg(((ProviderInfo)object).packageName) || !isOutsidePackage(((ProviderInfo)object).packageName))
            iterator.remove(); 
          ComponentFixer.fixOutsideComponentInfo((ComponentInfo)object);
        } 
        list.addAll((Collection)param1Object);
      } 
      return bool ? ParceledListSliceCompat.create(list) : list;
    }
    
    public String getMethodName() {
      return "queryContentProviders";
    }
  }
  
  static class QueryIntentActivities extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      boolean bool = ParceledListSliceCompat.isReturnParceledListSlice(param1Method);
      int i = VUserHandle.myUserId();
      List list = VPackageManager.get().queryIntentActivities((Intent)param1VarArgs[0], (String)param1VarArgs[1], ((Integer)param1VarArgs[2]).intValue(), i);
      replaceLastUserId(param1VarArgs);
      Object object = param1Method.invoke(param1Object, param1VarArgs);
      if (object != null) {
        param1Object = object;
        if (bool)
          param1Object = ParceledListSlice.getList.call(object, new Object[0]); 
        param1Object = param1Object;
        if (param1Object != null) {
          object = param1Object.iterator();
          while (object.hasNext()) {
            ResolveInfo resolveInfo = object.next();
            if (resolveInfo == null || resolveInfo.activityInfo == null || !isOutsidePackage(resolveInfo.activityInfo.packageName)) {
              object.remove();
              continue;
            } 
            ComponentFixer.fixOutsideComponentInfo((ComponentInfo)resolveInfo.activityInfo);
          } 
          list.addAll((Collection)param1Object);
        } 
      } 
      return bool ? ParceledListSliceCompat.create(list) : list;
    }
    
    public String getMethodName() {
      return "queryIntentActivities";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class QueryIntentContentProviders extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      boolean bool = ParceledListSliceCompat.isReturnParceledListSlice(param1Method);
      int i = VUserHandle.myUserId();
      List list1 = VPackageManager.get().queryIntentContentProviders((Intent)param1VarArgs[0], (String)param1VarArgs[1], ((Integer)param1VarArgs[2]).intValue(), i);
      replaceLastUserId(param1VarArgs);
      Object object = param1Method.invoke(param1Object, param1VarArgs);
      param1Object = object;
      if (bool)
        param1Object = ParceledListSlice.getList.call(object, new Object[0]); 
      List list2 = (List)param1Object;
      if (list2 != null) {
        object = list2.iterator();
        while (object.hasNext()) {
          param1Object = object.next();
          if (param1Object == null || ((ResolveInfo)param1Object).providerInfo == null || !isOutsidePackage(((ResolveInfo)param1Object).providerInfo.packageName)) {
            object.remove();
            continue;
          } 
          ComponentFixer.fixOutsideComponentInfo((ComponentInfo)((ResolveInfo)param1Object).providerInfo);
        } 
        list1.addAll(list2);
      } 
      return ParceledListSliceCompat.isReturnParceledListSlice(param1Method) ? ParceledListSliceCompat.create(list1) : list1;
    }
    
    public String getMethodName() {
      return "queryIntentContentProviders";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class QueryIntentReceivers extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      boolean bool = ParceledListSliceCompat.isReturnParceledListSlice(param1Method);
      int i = VUserHandle.myUserId();
      List list = VPackageManager.get().queryIntentReceivers((Intent)param1VarArgs[0], (String)param1VarArgs[1], ((Integer)param1VarArgs[2]).intValue(), i);
      Object object = param1Method.invoke(param1Object, param1VarArgs);
      param1Object = object;
      if (bool)
        param1Object = ParceledListSlice.getList.call(object, new Object[0]); 
      param1Object = param1Object;
      if (param1Object != null) {
        Iterator<ResolveInfo> iterator = param1Object.iterator();
        while (iterator.hasNext()) {
          object = iterator.next();
          if (object == null || ((ResolveInfo)object).activityInfo == null || isAppPkg(((ResolveInfo)object).activityInfo.packageName) || !isOutsidePackage(((ResolveInfo)object).activityInfo.packageName)) {
            iterator.remove();
            continue;
          } 
          ComponentFixer.fixOutsideComponentInfo((ComponentInfo)((ResolveInfo)object).activityInfo);
        } 
        list.addAll((Collection)param1Object);
      } 
      return bool ? ParceledListSliceCompat.create(list) : list;
    }
    
    public String getMethodName() {
      return "queryIntentReceivers";
    }
  }
  
  static class QueryIntentServices extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      boolean bool = ParceledListSliceCompat.isReturnParceledListSlice(param1Method);
      int i = VUserHandle.myUserId();
      List list = VPackageManager.get().queryIntentServices((Intent)param1VarArgs[0], (String)param1VarArgs[1], ((Integer)param1VarArgs[2]).intValue(), i);
      replaceLastUserId(param1VarArgs);
      Object object = param1Method.invoke(param1Object, param1VarArgs);
      if (object != null) {
        param1Object = object;
        if (bool)
          param1Object = ParceledListSlice.getList.call(object, new Object[0]); 
        param1Object = param1Object;
        if (param1Object != null) {
          Iterator<ResolveInfo> iterator = param1Object.iterator();
          while (iterator.hasNext()) {
            object = iterator.next();
            if (object == null || ((ResolveInfo)object).serviceInfo == null || !isOutsidePackage(((ResolveInfo)object).serviceInfo.packageName))
              iterator.remove(); 
          } 
          list.addAll((Collection)param1Object);
        } 
      } 
      return bool ? ParceledListSliceCompat.create(list) : list;
    }
    
    public String getMethodName() {
      return "queryIntentServices";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class QuerySliceContentProviders extends QueryContentProviders {
    public String getMethodName() {
      return "querySliceContentProviders";
    }
  }
  
  static class RemovePackageFromPreferred extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      MethodParameterUtils.replaceFirstAppPkg(param1VarArgs);
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "removePackageFromPreferred";
    }
  }
  
  static class ResolveContentProvider extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      String str = (String)param1VarArgs[0];
      int i = ((Integer)param1VarArgs[1]).intValue();
      int j = VUserHandle.myUserId();
      ProviderInfo providerInfo = VPackageManager.get().resolveContentProvider(str, i, j);
      Object object = providerInfo;
      if (providerInfo == null) {
        replaceLastUserId(param1VarArgs);
        param1Object = param1Method.invoke(param1Object, param1VarArgs);
        object = param1Object;
        if (param1Object != null) {
          object = param1Object;
          if (isOutsidePackage(((ProviderInfo)param1Object).packageName));
        } 
      } 
      return object;
    }
    
    public String getMethodName() {
      return "resolveContentProvider";
    }
  }
  
  static class ResolveIntent extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      Intent intent = (Intent)param1VarArgs[0];
      String str = (String)param1VarArgs[1];
      int i = ((Integer)param1VarArgs[2]).intValue();
      int j = VUserHandle.myUserId();
      ResolveInfo resolveInfo = VPackageManager.get().resolveIntent(intent, str, i, j);
      if (resolveInfo == null) {
        replaceLastUserId(param1VarArgs);
        param1Object = param1Method.invoke(param1Object, param1VarArgs);
        if (param1Object != null && isOutsidePackage(((ResolveInfo)param1Object).activityInfo.packageName)) {
          ComponentFixer.fixOutsideComponentInfo((ComponentInfo)((ResolveInfo)param1Object).activityInfo);
          return param1Object;
        } 
      } 
      return resolveInfo;
    }
    
    public String getMethodName() {
      return "resolveIntent";
    }
  }
  
  static class ResolveService extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      Intent intent = (Intent)param1VarArgs[0];
      String str = (String)param1VarArgs[1];
      int i = ((Integer)param1VarArgs[2]).intValue();
      int j = VUserHandle.myUserId();
      ResolveInfo resolveInfo = VPackageManager.get().resolveService(intent, str, i, j);
      if (resolveInfo != null)
        return resolveInfo; 
      replaceLastUserId(param1VarArgs);
      param1Object = param1Method.invoke(param1Object, param1VarArgs);
      if (param1Object != null && isOutsidePackage(((ResolveInfo)param1Object).serviceInfo.packageName)) {
        ComponentFixer.fixOutsideComponentInfo((ComponentInfo)((ResolveInfo)param1Object).serviceInfo);
        return param1Object;
      } 
      return null;
    }
    
    public String getMethodName() {
      return "resolveService";
    }
  }
  
  static class RevokeRuntimePermission extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      MethodParameterUtils.replaceFirstAppPkg(param1VarArgs);
      replaceLastUserId(param1VarArgs);
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "revokeRuntimePermission";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class SetApplicationBlockedSettingAsUser extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      MethodParameterUtils.replaceFirstAppPkg(param1VarArgs);
      replaceLastUserId(param1VarArgs);
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "setApplicationBlockedSettingAsUser";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class SetApplicationEnabledSetting extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      replaceLastUserId(param1VarArgs);
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "setApplicationEnabledSetting";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class SetComponentEnabledSetting extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1VarArgs[0];
      int i = ((Integer)param1VarArgs[1]).intValue();
      int j = ((Integer)param1VarArgs[2]).intValue();
      VPackageManager.get().setComponentEnabledSetting((ComponentName)param1Object, i, j, getAppUserId());
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "setComponentEnabledSetting";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class SetPackageStoppedState extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      MethodParameterUtils.replaceFirstAppPkg(param1VarArgs);
      replaceLastUserId(param1VarArgs);
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "setPackageStoppedState";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class checkUidSignatures extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = Integer.valueOf(0);
      int i = ((Integer)param1VarArgs[0]).intValue();
      int j = ((Integer)param1VarArgs[1]).intValue();
      if (i == j)
        return param1Object; 
      if (i == 9000 || j == 9000)
        return param1Object; 
      String[] arrayOfString = VirtualCore.getPM().getPackagesForUid(i);
      param1Object = VirtualCore.getPM().getPackagesForUid(j);
      return (arrayOfString == null || arrayOfString.length == 0) ? Integer.valueOf(-4) : ((param1Object == null || param1Object.length == 0) ? Integer.valueOf(-4) : Integer.valueOf(VPackageManager.get().checkSignatures(arrayOfString[0], (String)param1Object[0])));
    }
    
    public String getMethodName() {
      return "checkUidSignatures";
    }
  }
  
  static class getNameForUid extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      int i = ((Integer)param1VarArgs[0]).intValue();
      int j = i;
      if (i == 9000)
        j = getVUid(); 
      return VPackageManager.get().getNameForUid(j);
    }
    
    public String getMethodName() {
      return "getNameForUid";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\pm\MethodProxies.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */