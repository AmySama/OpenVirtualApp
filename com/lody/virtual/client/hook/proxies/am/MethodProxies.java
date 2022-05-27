package com.lody.virtual.client.hook.proxies.am;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.IServiceConnection;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcelable;
import android.text.TextUtils;
import com.lody.virtual.client.NativeEngine;
import com.lody.virtual.client.VClient;
import com.lody.virtual.client.badger.BadgerManager;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.env.Constants;
import com.lody.virtual.client.env.SpecialComponentList;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.providers.ProviderHook;
import com.lody.virtual.client.hook.secondary.ServiceConnectionProxy;
import com.lody.virtual.client.hook.utils.MethodParameterUtils;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.client.ipc.VPackageManager;
import com.lody.virtual.client.stub.ChooserActivity;
import com.lody.virtual.client.stub.IntentBuilder;
import com.lody.virtual.client.stub.StubManifest;
import com.lody.virtual.helper.compat.ActivityManagerCompat;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.compat.BundleCompat;
import com.lody.virtual.helper.compat.ParceledListSliceCompat;
import com.lody.virtual.helper.compat.PendingIntentCompat;
import com.lody.virtual.helper.utils.ArrayUtils;
import com.lody.virtual.helper.utils.ComponentUtils;
import com.lody.virtual.helper.utils.FileUtils;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.os.VUserHandle;
import com.lody.virtual.os.VUserInfo;
import com.lody.virtual.os.VUserManager;
import com.lody.virtual.remote.AppTaskInfo;
import com.lody.virtual.remote.ClientConfig;
import com.lody.virtual.remote.IntentSenderData;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.List;
import java.util.WeakHashMap;
import mirror.android.app.IActivityManager;
import mirror.android.app.LoadedApk;
import mirror.android.content.ContentProviderHolderOreo;
import mirror.android.content.IIntentReceiverJB;
import mirror.android.content.IntentFilter;
import mirror.android.content.pm.ParceledListSlice;
import mirror.android.content.pm.UserInfo;

public class MethodProxies {
  static class AddPackageDependency extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      MethodParameterUtils.replaceFirstAppPkg(param1VarArgs);
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "addPackageDependency";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class BindIsolatedService extends BindService {
    public boolean beforeCall(Object param1Object, Method param1Method, Object... param1VarArgs) {
      replaceLastUserId(param1VarArgs);
      return super.beforeCall(param1Object, param1Method, param1VarArgs);
    }
    
    public String getMethodName() {
      return "bindIsolatedService";
    }
    
    protected boolean isIsolated() {
      return true;
    }
  }
  
  static class BindService extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      int i;
      Integer integer = Integer.valueOf(0);
      IInterface iInterface = (IInterface)param1VarArgs[0];
      IBinder iBinder = (IBinder)param1VarArgs[1];
      Intent intent = new Intent((Intent)param1VarArgs[2]);
      String str = (String)param1VarArgs[3];
      IServiceConnection iServiceConnection = (IServiceConnection)param1VarArgs[4];
      intent.setDataAndType(intent.getData(), str);
      ComponentName componentName = intent.getComponent();
      if (componentName != null && isHostPkg(componentName.getPackageName()))
        return param1Method.invoke(param1Object, param1VarArgs); 
      if (isIsolated()) {
        i = 7;
      } else {
        i = 6;
      } 
      if (Build.VERSION.SDK_INT >= 23 && param1VarArgs.length >= 8 && param1VarArgs[i] instanceof String)
        param1VarArgs[i] = getHostPkg(); 
      int j = ((Integer)param1VarArgs[5]).intValue();
      if (isServerProcess()) {
        i = intent.getIntExtra("_VA_|_user_id_", -1);
      } else {
        i = VUserHandle.myUserId();
      } 
      if (i != -1) {
        ServiceInfo serviceInfo = VirtualCore.get().resolveServiceInfo(intent, i);
        if (serviceInfo != null) {
          if (isIsolated())
            param1VarArgs[6] = null; 
          if (Build.VERSION.SDK_INT >= 24 && (Integer.MIN_VALUE & j) != 0)
            param1VarArgs[5] = Integer.valueOf(Integer.MAX_VALUE & j); 
          ClientConfig clientConfig = VActivityManager.get().initProcess(serviceInfo.packageName, serviceInfo.processName, i);
          if (clientConfig == null) {
            param1Object = new StringBuilder();
            param1Object.append("failed to initProcess for bindService: ");
            param1Object.append(componentName);
            VLog.e("ActivityManager", param1Object.toString());
            return integer;
          } 
          param1VarArgs[2] = IntentBuilder.createBindProxyServiceIntent(clientConfig.vpid, clientConfig.isExt, serviceInfo, intent, j, i, iServiceConnection);
          param1VarArgs[4] = ServiceConnectionProxy.getOrCreateProxy(iServiceConnection);
          return param1Method.invoke(param1Object, param1VarArgs);
        } 
        if (componentName == null || !isOutsidePackage(componentName.getPackageName())) {
          param1Object = new StringBuilder();
          param1Object.append("Block bindService: ");
          param1Object.append(intent);
          VLog.e("VActivityManager", param1Object.toString());
          return integer;
        } 
        replaceLastUserId(param1VarArgs);
        return param1Method.invoke(param1Object, param1VarArgs);
      } 
      throw new IllegalArgumentException();
    }
    
    public String getMethodName() {
      return "bindService";
    }
    
    public boolean isEnable() {
      return (isAppProcess() || isServerProcess());
    }
    
    protected boolean isIsolated() {
      return false;
    }
  }
  
  static class BroadcastIntent extends MethodProxy {
    private Intent handleInstallShortcutIntent(Intent param1Intent) {
      Intent intent = (Intent)param1Intent.getParcelableExtra("android.intent.extra.shortcut.INTENT");
      if (intent != null) {
        ComponentName componentName = intent.resolveActivity(VirtualCore.getPM());
        if (componentName != null) {
          String str = componentName.getPackageName();
          Intent intent1 = new Intent();
          intent1.addCategory("android.intent.category.DEFAULT");
          intent1.setAction(Constants.ACTION_SHORTCUT);
          intent1.setPackage(getHostPkg());
          intent1.putExtra("_VA_|_intent_", (Parcelable)intent);
          intent1.putExtra("_VA_|_uri_", intent.toUri(0));
          intent1.putExtra("_VA_|_user_id_", VUserHandle.myUserId());
          param1Intent.removeExtra("android.intent.extra.shortcut.INTENT");
          param1Intent.putExtra("android.intent.extra.shortcut.INTENT", (Parcelable)intent1);
          Intent.ShortcutIconResource shortcutIconResource = (Intent.ShortcutIconResource)param1Intent.getParcelableExtra("android.intent.extra.shortcut.ICON_RESOURCE");
          if (shortcutIconResource != null && !TextUtils.equals(shortcutIconResource.packageName, getHostPkg()))
            try {
              Resources resources = VirtualCore.get().getResources(str);
              int i = resources.getIdentifier(shortcutIconResource.resourceName, "drawable", str);
            } finally {
              str = null;
            }  
        } 
      } 
      return param1Intent;
    }
    
    private Intent handleMediaScannerIntent(Intent param1Intent) {
      if (param1Intent == null)
        return null; 
      Uri uri = param1Intent.getData();
      if (uri == null)
        return param1Intent; 
      if (!"file".equalsIgnoreCase(uri.getScheme()))
        return param1Intent; 
      String str = uri.getPath();
      if (str == null)
        return param1Intent; 
      File file = new File(NativeEngine.getRedirectedPath(str));
      if (!file.exists())
        return param1Intent; 
      param1Intent.setData(Uri.fromFile(file));
      return param1Intent;
    }
    
    private void handleUninstallShortcutIntent(Intent param1Intent) {
      Intent intent = (Intent)param1Intent.getParcelableExtra("android.intent.extra.shortcut.INTENT");
      if (intent != null && intent.resolveActivity(getPM()) != null) {
        Intent intent1 = new Intent();
        intent1.putExtra("_VA_|_uri_", intent.toUri(0));
        intent1.setClassName(getHostPkg(), Constants.SHORTCUT_PROXY_ACTIVITY_NAME);
        intent1.removeExtra("android.intent.extra.shortcut.INTENT");
        param1Intent.putExtra("android.intent.extra.shortcut.INTENT", (Parcelable)intent1);
      } 
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      Intent intent1 = new Intent((Intent)param1VarArgs[1]);
      String str = (String)param1VarArgs[2];
      intent1.setDataAndType(intent1.getData(), str);
      Intent intent2 = handleIntent(intent1);
      if (intent2 != null) {
        param1VarArgs[1] = intent2;
        if (param1VarArgs[7] instanceof String || param1VarArgs[7] instanceof String[])
          param1VarArgs[7] = null; 
        param1VarArgs[ArrayUtils.indexOfFirst(param1VarArgs, Boolean.class)] = Boolean.valueOf(false);
        replaceLastUserId(param1VarArgs);
        try {
          return param1Method.invoke(param1Object, param1VarArgs);
        } finally {}
      } 
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "broadcastIntent";
    }
    
    protected Intent handleIntent(Intent param1Intent) {
      String str = param1Intent.getAction();
      boolean bool = "android.intent.action.CREATE_SHORTCUT".equals(str);
      Intent intent = null;
      if (bool || "com.android.launcher.action.INSTALL_SHORTCUT".equals(str) || "com.aliyun.homeshell.action.INSTALL_SHORTCUT".equals(str)) {
        if (getConfig().isAllowCreateShortcut())
          intent = handleInstallShortcutIntent(param1Intent); 
        return intent;
      } 
      if ("com.android.launcher.action.UNINSTALL_SHORTCUT".equals(str) || "com.aliyun.homeshell.action.UNINSTALL_SHORTCUT".equals(str)) {
        handleUninstallShortcutIntent(param1Intent);
        return param1Intent;
      } 
      if ("android.intent.action.MEDIA_SCANNER_SCAN_FILE".equals(str))
        return handleMediaScannerIntent(param1Intent); 
      if (BadgerManager.handleBadger(param1Intent))
        return null; 
      if (param1Intent.getComponent() != null)
        try {
          ActivityInfo activityInfo = VirtualCore.get().getPackageManager().getReceiverInfo(param1Intent.getComponent(), 0);
          if (activityInfo != null && VirtualCore.get().getProccessInfo(activityInfo.processName, VClient.get().getVUid()) == null) {
            ProviderInfo providerInfo = new ProviderInfo();
            this();
            providerInfo.applicationInfo = activityInfo.applicationInfo;
            providerInfo.packageName = activityInfo.packageName;
            providerInfo.processName = activityInfo.processName;
            providerInfo.authority = "";
            VActivityManager.get().acquireProviderClient(VUserHandle.myUserId(), providerInfo);
          } 
        } catch (Exception exception) {} 
      return ComponentUtils.proxyBroadcastIntent(param1Intent, VUserHandle.myUserId());
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class BroadcastIntentWithFeature extends BroadcastIntent {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      Intent intent = new Intent((Intent)param1VarArgs[2]);
      String str = (String)param1VarArgs[3];
      intent.setDataAndType(intent.getData(), str);
      intent = handleIntent(intent);
      if (intent != null) {
        param1VarArgs[2] = intent;
        if (param1VarArgs[8] instanceof String[])
          param1VarArgs[8] = null; 
        param1VarArgs[ArrayUtils.indexOfFirst(param1VarArgs, Boolean.class)] = Boolean.valueOf(false);
        replaceLastUserId(param1VarArgs);
        return param1Method.invoke(param1Object, param1VarArgs);
      } 
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "broadcastIntentWithFeature";
    }
  }
  
  static class CheckGrantUriPermission extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      MethodParameterUtils.replaceFirstAppPkg(param1VarArgs);
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "checkGrantUriPermission";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class CheckPermission extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1VarArgs[0];
      int i = ((Integer)param1VarArgs[1]).intValue();
      int j = ((Integer)param1VarArgs[2]).intValue();
      return Integer.valueOf(VActivityManager.get().checkPermission((String)param1Object, i, j));
    }
    
    public String getMethodName() {
      return "checkPermission";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class CheckPermissionWithToken extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1VarArgs[0];
      int i = ((Integer)param1VarArgs[1]).intValue();
      int j = ((Integer)param1VarArgs[2]).intValue();
      return Integer.valueOf(VActivityManager.get().checkPermission((String)param1Object, i, j));
    }
    
    public String getMethodName() {
      return "checkPermissionWithToken";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class CrashApplication extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "crashApplication";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class FinishReceiver extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      IBinder iBinder = (IBinder)param1VarArgs[0];
      VActivityManager.get().broadcastFinish(iBinder);
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "finishReceiver";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class ForceStopPackage extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1VarArgs[0];
      int i = VUserHandle.myUserId();
      VActivityManager.get().killAppByPkg((String)param1Object, i);
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "forceStopPackage";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class GetActivityClassForToken extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      IBinder iBinder = (IBinder)param1VarArgs[0];
      ComponentName componentName = VActivityManager.get().getActivityForToken(iBinder);
      return (componentName == null) ? param1Method.invoke(param1Object, param1VarArgs) : componentName;
    }
    
    public String getMethodName() {
      return "getActivityClassForToken";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  public static class GetAppTasks extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      MethodParameterUtils.replaceFirstAppPkg(param1VarArgs);
      return super.call(param1Object, param1Method, param1VarArgs);
    }
    
    public String getMethodName() {
      return "getAppTasks";
    }
  }
  
  public static class GetCallingActivity extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1VarArgs[0];
      return VActivityManager.get().getCallingActivity((IBinder)param1Object);
    }
    
    public String getMethodName() {
      return "getCallingActivity";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  public static class GetCallingPackage extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1VarArgs[0];
      return VActivityManager.get().getCallingPackage((IBinder)param1Object);
    }
    
    public String getMethodName() {
      return "getCallingPackage";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class GetContentProvider extends MethodProxy {
    private boolean miuiProviderWaitingTargetProcess(Object param1Object) {
      return (param1Object != null && IActivityManager.ContentProviderHolderMIUI.waitProcessStart != null) ? IActivityManager.ContentProviderHolderMIUI.waitProcessStart.get(param1Object) : false;
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      IInterface iInterface;
      int i = getProviderNameIndex();
      String str = (String)param1VarArgs[i];
      if (str.startsWith(StubManifest.STUB_CP_AUTHORITY) || str.startsWith(StubManifest.EXT_STUB_CP_AUTHORITY) || str.equals(getConfig().getExtPackageHelperAuthority()) || str.equals(getConfig().getBinderProviderAuthority())) {
        replaceLastUserId(param1VarArgs);
        return param1Method.invoke(param1Object, param1VarArgs);
      } 
      boolean bool1 = true;
      boolean bool2 = true;
      VLog.w("VActivityManger", "getContentProvider:%s", new Object[] { str });
      if (BuildCompat.isQ()) {
        int k = i - 1;
        if (param1VarArgs[k] instanceof String)
          param1VarArgs[k] = getHostPkg(); 
      } 
      int j = VUserHandle.myUserId();
      ProviderInfo providerInfo = VPackageManager.get().resolveContentProvider(str, 0, j);
      if (providerInfo != null && !providerInfo.enabled)
        return null; 
      if (providerInfo != null && isAppPkg(providerInfo.packageName)) {
        ClientConfig clientConfig = VActivityManager.get().initProcess(providerInfo.packageName, providerInfo.processName, j);
        if (clientConfig == null) {
          param1Object = new StringBuilder();
          param1Object.append("failed to initProcess for provider: ");
          param1Object.append(str);
          VLog.e("ActivityManager", param1Object.toString());
          return null;
        } 
        param1VarArgs[i] = StubManifest.getStubAuthority(clientConfig.vpid, clientConfig.isExt);
        replaceLastUserId(param1VarArgs);
        object = param1Method.invoke(param1Object, param1VarArgs);
        if (object == null)
          return null; 
        if (BuildCompat.isOreo()) {
          iInterface = (IInterface)ContentProviderHolderOreo.provider.get(object);
          param1Object = iInterface;
          if (iInterface != null) {
            iInterface = VActivityManager.get().acquireProviderClient(j, providerInfo);
            param1Object = iInterface;
            if (BuildCompat.isS()) {
              param1Object = iInterface;
              if (iInterface != null)
                param1Object = ProviderHook.createProxy(false, str, iInterface); 
            } 
            bool2 = false;
          } 
          if (param1Object == null) {
            if (bool2) {
              param1Object = new StringBuilder();
              param1Object.append("Loading provider: ");
              param1Object.append(providerInfo.authority);
              param1Object.append("(");
              param1Object.append(providerInfo.processName);
              param1Object.append(")");
              VLog.w("VActivityManager", param1Object.toString(), new Object[0]);
              ContentProviderHolderOreo.info.set(object, providerInfo);
              return object;
            } 
            param1Object = new StringBuilder();
            param1Object.append("acquireProviderClient fail: ");
            param1Object.append(providerInfo.authority);
            param1Object.append("(");
            param1Object.append(providerInfo.processName);
            param1Object.append(")");
            VLog.e("VActivityManager", param1Object.toString());
            return null;
          } 
          ContentProviderHolderOreo.provider.set(object, param1Object);
          ContentProviderHolderOreo.info.set(object, providerInfo);
        } else {
          iInterface = (IInterface)IActivityManager.ContentProviderHolder.provider.get(object);
          bool2 = bool1;
          param1Object = iInterface;
          if (iInterface != null) {
            param1Object = VActivityManager.get().acquireProviderClient(j, providerInfo);
            bool2 = false;
          } 
          if (param1Object == null) {
            if (bool2) {
              if (BuildCompat.isMIUI() && miuiProviderWaitingTargetProcess(object)) {
                param1Object = new StringBuilder();
                param1Object.append("miui provider waiting process: ");
                param1Object.append(providerInfo.authority);
                param1Object.append("(");
                param1Object.append(providerInfo.processName);
                param1Object.append(")");
                VLog.w("VActivityManager", param1Object.toString(), new Object[0]);
              } 
              return null;
            } 
            param1Object = new StringBuilder();
            param1Object.append("acquireProviderClient fail: ");
            param1Object.append(providerInfo.authority);
            param1Object.append("(");
            param1Object.append(providerInfo.processName);
            param1Object.append(")");
            VLog.e("VActivityManager", param1Object.toString());
            return null;
          } 
          IActivityManager.ContentProviderHolder.provider.set(object, param1Object);
          IActivityManager.ContentProviderHolder.info.set(object, providerInfo);
        } 
        return object;
      } 
      replaceLastUserId((Object[])object);
      Object object = iInterface.invoke(param1Object, (Object[])object);
      if (object != null) {
        if (BuildCompat.isOreo()) {
          iInterface = (IInterface)ContentProviderHolderOreo.provider.get(object);
          providerInfo = (ProviderInfo)ContentProviderHolderOreo.info.get(object);
          param1Object = iInterface;
          if (iInterface != null)
            param1Object = ProviderHook.createProxy(true, providerInfo.authority, iInterface); 
          ContentProviderHolderOreo.provider.set(object, param1Object);
        } else {
          iInterface = (IInterface)IActivityManager.ContentProviderHolder.provider.get(object);
          providerInfo = (ProviderInfo)IActivityManager.ContentProviderHolder.info.get(object);
          param1Object = iInterface;
          if (iInterface != null)
            param1Object = ProviderHook.createProxy(true, providerInfo.authority, iInterface); 
          IActivityManager.ContentProviderHolder.provider.set(object, param1Object);
        } 
        return object;
      } 
      return null;
    }
    
    public String getMethodName() {
      return "getContentProvider";
    }
    
    public int getProviderNameIndex() {
      return BuildCompat.isQ() ? 2 : 1;
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class GetContentProviderExternal extends GetContentProvider {
    public String getMethodName() {
      return "getContentProviderExternal";
    }
    
    public int getProviderNameIndex() {
      return BuildCompat.isQ() ? 1 : 0;
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class GetCurrentUser extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      try {
        return UserInfo.ctor.newInstance(new Object[] { Integer.valueOf(0), "user", Integer.valueOf(1) });
      } finally {
        param1Object = null;
        param1Object.printStackTrace();
      } 
    }
    
    public String getMethodName() {
      return "getCurrentUser";
    }
  }
  
  static class GetIntentForIntentSender extends MethodProxy {
    public Object afterCall(Object param1Object1, Method param1Method, Object[] param1ArrayOfObject, Object param1Object2) {
      param1Object1 = param1Object2;
      if (param1Object1 != null)
        try {
          ComponentUtils.IntentSenderInfo intentSenderInfo = ComponentUtils.parseIntentSenderInfo((Intent)param1Object1, false);
        } finally {
          param1Method = null;
        }  
      return param1Object1;
    }
    
    public String getMethodName() {
      return "getIntentForIntentSender";
    }
  }
  
  static class GetIntentSender extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      IntentSenderData intentSenderData;
      String str = (String)param1VarArgs[1];
      param1VarArgs[1] = getHostPkg();
      if (param1VarArgs[param1VarArgs.length - 1] instanceof Integer)
        param1VarArgs[param1VarArgs.length - 1] = Integer.valueOf(getRealUserId()); 
      int i = ((Integer)param1VarArgs[0]).intValue();
      int j = ArrayUtils.indexOfFirst(param1VarArgs, Intent[].class);
      Intent[] arrayOfIntent = (Intent[])param1VarArgs[j];
      int k = j + 1;
      String[] arrayOfString = (String[])param1VarArgs[k];
      int m = j + 2;
      int n = ((Integer)param1VarArgs[m]).intValue();
      int i1 = VUserHandle.myUserId();
      if (arrayOfIntent.length > 0) {
        Intent intent = arrayOfIntent[arrayOfIntent.length - 1];
        if (arrayOfString != null && arrayOfString.length >= arrayOfIntent.length)
          intent.setDataAndType(intent.getData(), arrayOfString[arrayOfIntent.length - 1]); 
        intent = ComponentUtils.getProxyIntentSenderIntent(i1, i, str, intent);
        if (intent == null)
          return null; 
        int i2 = n & 0xFFFFFFF7;
        n = i2;
        if (Build.VERSION.SDK_INT >= 16)
          n = i2 & 0xFFFFFF7F; 
        i2 = n;
        if ((0x8000000 & n) != 0)
          i2 = n & 0xD7FFFFFF | 0x10000000; 
        (new Intent[1])[0] = intent;
        param1VarArgs[j] = new Intent[1];
        (new String[1])[0] = null;
        param1VarArgs[k] = new String[1];
        if ((i2 & 0x10000000) != 0 && BuildCompat.isSamsung() && Build.VERSION.SDK_INT >= 21) {
          param1VarArgs[m] = Integer.valueOf(536870912);
          IInterface iInterface = (IInterface)param1Method.invoke(param1Object, param1VarArgs);
          if (iInterface != null) {
            PendingIntent pendingIntent = PendingIntentCompat.readPendingIntent(iInterface.asBinder());
            if (pendingIntent != null) {
              AlarmManager alarmManager = (AlarmManager)getHostContext().getSystemService("alarm");
              if (alarmManager != null)
                try {
                  alarmManager.cancel(pendingIntent);
                } finally {
                  alarmManager = null;
                }  
            } 
          } 
        } 
        param1VarArgs[m] = Integer.valueOf(i2);
        param1Object = param1Method.invoke(param1Object, param1VarArgs);
        if (param1Object != null) {
          intentSenderData = new IntentSenderData(str, param1Object.asBinder(), i, i1);
          VActivityManager.get().addOrUpdateIntentSender(intentSenderData);
        } 
        return param1Object;
      } 
      return intentSenderData.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "getIntentSender";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class GetIntentSenderWithFeature extends GetIntentSender {
    public String getMethodName() {
      return "getIntentSenderWithFeature";
    }
  }
  
  static class GetIntentSenderWithSourceToken extends GetIntentSender {
    public String getMethodName() {
      return "getIntentSenderWithSourceToken";
    }
  }
  
  static class GetPackageAskScreenCompat extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      if (Build.VERSION.SDK_INT >= 15 && param1VarArgs.length > 0 && param1VarArgs[0] instanceof String)
        param1VarArgs[0] = getHostPkg(); 
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "getPackageAskScreenCompat";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class GetPackageForIntentSender extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      IInterface iInterface = (IInterface)param1VarArgs[0];
      if (iInterface != null) {
        IntentSenderData intentSenderData = VActivityManager.get().getIntentSender(iInterface.asBinder());
        if (intentSenderData != null)
          return intentSenderData.targetPkg; 
      } 
      return super.call(param1Object, param1Method, param1VarArgs);
    }
    
    public String getMethodName() {
      return "getPackageForIntentSender";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class GetPackageForToken extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      IBinder iBinder = (IBinder)param1VarArgs[0];
      String str = VActivityManager.get().getPackageForToken(iBinder);
      return (str != null) ? str : super.call(param1Object, param1Method, param1VarArgs);
    }
    
    public String getMethodName() {
      return "getPackageForToken";
    }
  }
  
  static class GetPackageProcessState extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return Integer.valueOf(4);
    }
    
    public String getMethodName() {
      return "getPackageProcessState";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class GetPersistedUriPermissions extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      MethodParameterUtils.replaceFirstAppPkg(param1VarArgs);
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "getPersistedUriPermissions";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class GetRecentTasks extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      replaceFirstUserId(param1VarArgs);
      Object object = param1Method.invoke(param1Object, param1VarArgs);
      if (ParceledListSliceCompat.isReturnParceledListSlice(param1Method)) {
        param1Object = ParceledListSlice.getList.call(object, new Object[0]);
      } else {
        param1Object = object;
      } 
      param1Object = ((List)param1Object).iterator();
      while (true) {
        if (param1Object.hasNext()) {
          ActivityManager.RecentTaskInfo recentTaskInfo = param1Object.next();
          AppTaskInfo appTaskInfo = VActivityManager.get().getTaskInfo(recentTaskInfo.id);
          if (appTaskInfo == null)
            continue; 
          if (Build.VERSION.SDK_INT >= 23)
            try {
              recentTaskInfo.topActivity = appTaskInfo.topActivity;
              recentTaskInfo.baseActivity = appTaskInfo.baseActivity;
            } finally {} 
          try {
            recentTaskInfo.origActivity = appTaskInfo.baseActivity;
            recentTaskInfo.baseIntent = appTaskInfo.baseIntent;
          } finally {}
          continue;
        } 
        return object;
      } 
    }
    
    public String getMethodName() {
      return "getRecentTasks";
    }
  }
  
  static class GetRunningAppProcesses extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: invokestatic get : ()Lcom/lody/virtual/client/VClient;
      //   5: invokevirtual getClientConfig : ()Lcom/lody/virtual/remote/ClientConfig;
      //   8: ifnonnull -> 22
      //   11: aload_2
      //   12: aload_1
      //   13: aload_3
      //   14: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
      //   17: astore_1
      //   18: aload_0
      //   19: monitorexit
      //   20: aload_1
      //   21: areturn
      //   22: aload_2
      //   23: aload_1
      //   24: aload_3
      //   25: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
      //   28: checkcast java/util/List
      //   31: astore_2
      //   32: aload_2
      //   33: ifnonnull -> 40
      //   36: aload_0
      //   37: monitorexit
      //   38: aconst_null
      //   39: areturn
      //   40: new java/util/ArrayList
      //   43: astore_1
      //   44: aload_1
      //   45: aload_2
      //   46: invokespecial <init> : (Ljava/util/Collection;)V
      //   49: aload_1
      //   50: invokeinterface iterator : ()Ljava/util/Iterator;
      //   55: astore #4
      //   57: aload #4
      //   59: invokeinterface hasNext : ()Z
      //   64: ifeq -> 250
      //   67: aload #4
      //   69: invokeinterface next : ()Ljava/lang/Object;
      //   74: checkcast android/app/ActivityManager$RunningAppProcessInfo
      //   77: astore_3
      //   78: aload_3
      //   79: getfield uid : I
      //   82: invokestatic getRealUid : ()I
      //   85: if_icmpne -> 57
      //   88: invokestatic get : ()Lcom/lody/virtual/client/ipc/VActivityManager;
      //   91: aload_3
      //   92: getfield pid : I
      //   95: invokevirtual isAppPid : (I)Z
      //   98: ifeq -> 208
      //   101: invokestatic get : ()Lcom/lody/virtual/client/ipc/VActivityManager;
      //   104: aload_3
      //   105: getfield pid : I
      //   108: invokevirtual getUidByPid : (I)I
      //   111: istore #5
      //   113: iload #5
      //   115: invokestatic getUserId : (I)I
      //   118: invokestatic getAppUserId : ()I
      //   121: if_icmpeq -> 134
      //   124: aload #4
      //   126: invokeinterface remove : ()V
      //   131: goto -> 57
      //   134: invokestatic get : ()Lcom/lody/virtual/client/ipc/VActivityManager;
      //   137: aload_3
      //   138: getfield pid : I
      //   141: invokevirtual getProcessPkgList : (I)Ljava/util/List;
      //   144: astore #6
      //   146: invokestatic get : ()Lcom/lody/virtual/client/ipc/VActivityManager;
      //   149: aload_3
      //   150: getfield pid : I
      //   153: invokevirtual getAppProcessName : (I)Ljava/lang/String;
      //   156: astore_2
      //   157: aload_2
      //   158: ifnull -> 181
      //   161: aload_3
      //   162: iconst_0
      //   163: putfield importanceReasonCode : I
      //   166: aload_3
      //   167: iconst_0
      //   168: putfield importanceReasonPid : I
      //   171: aload_3
      //   172: aconst_null
      //   173: putfield importanceReasonComponent : Landroid/content/ComponentName;
      //   176: aload_3
      //   177: aload_2
      //   178: putfield processName : Ljava/lang/String;
      //   181: aload_3
      //   182: aload #6
      //   184: iconst_0
      //   185: anewarray java/lang/String
      //   188: invokeinterface toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
      //   193: checkcast [Ljava/lang/String;
      //   196: putfield pkgList : [Ljava/lang/String;
      //   199: aload_3
      //   200: iload #5
      //   202: putfield uid : I
      //   205: goto -> 57
      //   208: aload_3
      //   209: getfield processName : Ljava/lang/String;
      //   212: invokestatic getConfig : ()Lcom/lody/virtual/client/core/SettingConfig;
      //   215: invokevirtual getMainPackageName : ()Ljava/lang/String;
      //   218: invokevirtual startsWith : (Ljava/lang/String;)Z
      //   221: ifne -> 240
      //   224: aload_3
      //   225: getfield processName : Ljava/lang/String;
      //   228: invokestatic getConfig : ()Lcom/lody/virtual/client/core/SettingConfig;
      //   231: invokevirtual getExtPackageName : ()Ljava/lang/String;
      //   234: invokevirtual startsWith : (Ljava/lang/String;)Z
      //   237: ifeq -> 57
      //   240: aload #4
      //   242: invokeinterface remove : ()V
      //   247: goto -> 57
      //   250: aload_0
      //   251: monitorexit
      //   252: aload_1
      //   253: areturn
      //   254: astore_1
      //   255: aload_0
      //   256: monitorexit
      //   257: aload_1
      //   258: athrow
      // Exception table:
      //   from	to	target	type
      //   2	18	254	finally
      //   22	32	254	finally
      //   40	57	254	finally
      //   57	131	254	finally
      //   134	157	254	finally
      //   161	181	254	finally
      //   181	205	254	finally
      //   208	240	254	finally
      //   240	247	254	finally
    }
    
    public String getMethodName() {
      return "getRunningAppProcesses";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class GetServices extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      int i = ((Integer)param1VarArgs[0]).intValue();
      int j = ((Integer)param1VarArgs[1]).intValue();
      return VActivityManager.get().getServices(VClient.get().getCurrentPackage(), i, j).getList();
    }
    
    public String getMethodName() {
      return "getServices";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  public static class GetTasks extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1Method.invoke(param1Object, param1VarArgs);
      for (ActivityManager.RunningTaskInfo runningTaskInfo : param1Object) {
        AppTaskInfo appTaskInfo = VActivityManager.get().getTaskInfo(runningTaskInfo.id);
        if (appTaskInfo != null) {
          runningTaskInfo.topActivity = appTaskInfo.topActivity;
          runningTaskInfo.baseActivity = appTaskInfo.baseActivity;
        } 
      } 
      return param1Object;
    }
    
    public String getMethodName() {
      return "getTasks";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class GetUidForIntentSender extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1VarArgs[0];
      if (param1Object != null) {
        param1Object = VActivityManager.get().getIntentSender(param1Object.asBinder());
        if (param1Object != null)
          return Integer.valueOf(VPackageManager.get().getPackageUid(((IntentSenderData)param1Object).targetPkg, ((IntentSenderData)param1Object).userId)); 
      } 
      return Integer.valueOf(-1);
    }
    
    public String getMethodName() {
      return "getUidForIntentSender";
    }
  }
  
  static class GrantUriPermission extends MethodProxy {
    public Object call(StackTraceElement param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      MethodParameterUtils.replaceFirstAppPkg(param1VarArgs);
      replaceLastUserId(param1VarArgs);
      if (param1VarArgs[2] != null && param1VarArgs[2] instanceof Uri)
        param1VarArgs[2] = ComponentUtils.processOutsideUri(getAppUserId(), VirtualCore.get().isExtPackage(), (Uri)param1VarArgs[2]); 
      try {
        param1Method.invoke(param1Object, param1VarArgs);
        return null;
      } catch (Exception exception) {
        if (exception.getCause() != null && exception.getCause() instanceof SecurityException)
          for (StackTraceElement param1Object : exception.getStackTrace()) {
            if (TextUtils.equals(param1Object.getClassName(), Intent.class.getName()) && TextUtils.equals(param1Object.getMethodName(), "migrateExtraStreamToClipData"))
              return null; 
          }  
        throw exception;
      } 
    }
    
    public String getMethodName() {
      return "grantUriPermission";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class GrantUriPermissionFromOwner extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      MethodParameterUtils.replaceFirstAppPkg(param1VarArgs);
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "grantUriPermissionFromOwner";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class HandleIncomingUser extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      int i = param1VarArgs.length - 1;
      if (param1VarArgs[i] instanceof String)
        param1VarArgs[i] = getHostPkg(); 
      replaceLastUserId(param1VarArgs);
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "handleIncomingUser";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class IsBackgroundRestricted extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      MethodParameterUtils.replaceFirstAppPkg(param1VarArgs);
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "isBackgroundRestricted";
    }
  }
  
  static class KillApplicationProcess extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "killApplicationProcess";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class KillBackgroundProcesses extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      if (param1VarArgs[0] instanceof String) {
        param1Object = param1VarArgs[0];
        VActivityManager.get().killAppByPkg((String)param1Object, getAppUserId());
        return Integer.valueOf(0);
      } 
      replaceLastUserId(param1VarArgs);
      return super.call(param1Object, param1Method, param1VarArgs);
    }
    
    public String getMethodName() {
      return "killBackgroundProcesses";
    }
  }
  
  public static class OverridePendingTransition extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return !VClient.get().isDynamicApp() ? Integer.valueOf(0) : super.call(param1Object, param1Method, param1VarArgs);
    }
    
    public String getMethodName() {
      return "overridePendingTransition";
    }
  }
  
  static class PeekService extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      int i;
      Intent intent = new Intent((Intent)param1VarArgs[0]);
      String str = (String)param1VarArgs[1];
      ComponentName componentName = intent.getComponent();
      if (componentName != null && isHostPkg(componentName.getPackageName()))
        return param1Method.invoke(param1Object, param1VarArgs); 
      if (isServerProcess()) {
        i = intent.getIntExtra("_VA_|_user_id_", -1);
      } else {
        i = VUserHandle.myUserId();
      } 
      if (i != -1) {
        ClientConfig clientConfig;
        intent.setDataAndType(intent.getData(), str);
        ServiceInfo serviceInfo = VirtualCore.get().resolveServiceInfo(intent, i);
        if (serviceInfo != null) {
          clientConfig = VClient.get().getClientConfig();
          param1VarArgs[0] = IntentBuilder.createBindProxyServiceIntent(clientConfig.vpid, clientConfig.isExt, serviceInfo, intent, 0, i, null);
          return param1Method.invoke(param1Object, param1VarArgs);
        } 
        return (clientConfig != null && isOutsidePackage(clientConfig.getPackageName())) ? param1Method.invoke(param1Object, param1VarArgs) : null;
      } 
      throw new IllegalArgumentException();
    }
    
    public String getMethodName() {
      return "peekService";
    }
    
    public boolean isEnable() {
      return (isAppProcess() || isServerProcess());
    }
  }
  
  static class PublishContentProviders extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "publishContentProviders";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class RegisterReceiver extends MethodProxy {
    int IDX_IIntentReceiver = 2;
    
    int IDX_IntentFilter = 3;
    
    int IDX_RequiredPermission = 4;
    
    private WeakHashMap<IBinder, IIntentReceiver> mProxyIIntentReceivers = new WeakHashMap<IBinder, IIntentReceiver>();
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      MethodParameterUtils.replaceFirstAppPkg(param1VarArgs);
      replaceFirstUserId(param1VarArgs);
      param1VarArgs[this.IDX_RequiredPermission] = null;
      IntentFilter intentFilter1 = (IntentFilter)param1VarArgs[this.IDX_IntentFilter];
      if (intentFilter1 == null)
        return param1Method.invoke(param1Object, param1VarArgs); 
      IntentFilter intentFilter2 = new IntentFilter(intentFilter1);
      if (intentFilter2.hasCategory("__VA__|_static_receiver_")) {
        ((List)IntentFilter.mCategories.get(intentFilter2)).remove("__VA__|_static_receiver_");
        return param1Method.invoke(param1Object, param1VarArgs);
      } 
      SpecialComponentList.protectIntentFilter(intentFilter2);
      param1VarArgs[this.IDX_IntentFilter] = intentFilter2;
      int i = param1VarArgs.length;
      int j = this.IDX_IIntentReceiver;
      if (i > j && param1VarArgs[j] instanceof IIntentReceiver) {
        IInterface iInterface = (IInterface)param1VarArgs[j];
        if (!(iInterface instanceof IIntentReceiverProxy)) {
          final IBinder token = iInterface.asBinder();
          if (iBinder != null) {
            IIntentReceiverProxy iIntentReceiverProxy;
            iBinder.linkToDeath(new IBinder.DeathRecipient() {
                  public void binderDied() {
                    token.unlinkToDeath(this, 0);
                    MethodProxies.RegisterReceiver.this.mProxyIIntentReceivers.remove(token);
                  }
                }0);
            IIntentReceiver iIntentReceiver2 = this.mProxyIIntentReceivers.get(iBinder);
            IIntentReceiver iIntentReceiver1 = iIntentReceiver2;
            if (iIntentReceiver2 == null) {
              iIntentReceiverProxy = new IIntentReceiverProxy(iInterface, intentFilter2);
              this.mProxyIIntentReceivers.put(iBinder, iIntentReceiverProxy);
            } 
            WeakReference weakReference = (WeakReference)LoadedApk.ReceiverDispatcher.InnerReceiver.mDispatcher.get(iInterface);
            if (weakReference != null) {
              LoadedApk.ReceiverDispatcher.mIIntentReceiver.set(weakReference.get(), iIntentReceiverProxy);
              param1VarArgs[this.IDX_IIntentReceiver] = iIntentReceiverProxy;
            } 
          } 
        } 
      } 
      Intent intent = (Intent)param1Method.invoke(param1Object, param1VarArgs);
      param1Object = intent;
      if (intent != null)
        param1Object = SpecialComponentList.unprotectIntent(VUserHandle.myUserId(), intent); 
      return param1Object;
    }
    
    public String getMethodName() {
      return "registerReceiver";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
    
    private static class IIntentReceiverProxy extends IIntentReceiver.Stub {
      IntentFilter mFilter;
      
      IInterface mOld;
      
      IIntentReceiverProxy(IInterface param2IInterface, IntentFilter param2IntentFilter) {
        this.mOld = param2IInterface;
        this.mFilter = param2IntentFilter;
      }
      
      public void performReceive(Intent param2Intent, int param2Int, String param2String, Bundle param2Bundle, boolean param2Boolean1, boolean param2Boolean2) {
        performReceive(param2Intent, param2Int, param2String, param2Bundle, param2Boolean1, param2Boolean2, 0);
      }
      
      public void performReceive(Intent param2Intent, int param2Int1, String param2String, Bundle param2Bundle, boolean param2Boolean1, boolean param2Boolean2, int param2Int2) {
        param2Intent = SpecialComponentList.unprotectIntent(VUserHandle.myUserId(), param2Intent);
        IIntentReceiverJB.performReceive.call(this.mOld, new Object[] { param2Intent, Integer.valueOf(param2Int1), param2String, param2Bundle, Boolean.valueOf(param2Boolean1), Boolean.valueOf(param2Boolean2), Integer.valueOf(param2Int2) });
      }
    }
  }
  
  class null implements IBinder.DeathRecipient {
    public void binderDied() {
      token.unlinkToDeath(this, 0);
      this.this$0.mProxyIIntentReceivers.remove(token);
    }
  }
  
  private static class IIntentReceiverProxy extends IIntentReceiver.Stub {
    IntentFilter mFilter;
    
    IInterface mOld;
    
    IIntentReceiverProxy(IInterface param1IInterface, IntentFilter param1IntentFilter) {
      this.mOld = param1IInterface;
      this.mFilter = param1IntentFilter;
    }
    
    public void performReceive(Intent param1Intent, int param1Int, String param1String, Bundle param1Bundle, boolean param1Boolean1, boolean param1Boolean2) {
      performReceive(param1Intent, param1Int, param1String, param1Bundle, param1Boolean1, param1Boolean2, 0);
    }
    
    public void performReceive(Intent param1Intent, int param1Int1, String param1String, Bundle param1Bundle, boolean param1Boolean1, boolean param1Boolean2, int param1Int2) {
      param1Intent = SpecialComponentList.unprotectIntent(VUserHandle.myUserId(), param1Intent);
      IIntentReceiverJB.performReceive.call(this.mOld, new Object[] { param1Intent, Integer.valueOf(param1Int1), param1String, param1Bundle, Boolean.valueOf(param1Boolean1), Boolean.valueOf(param1Boolean2), Integer.valueOf(param1Int2) });
    }
  }
  
  static class RegisterReceiverWithFeature extends RegisterReceiver {
    public RegisterReceiverWithFeature() {
      if (BuildCompat.isS()) {
        this.IDX_IIntentReceiver = 4;
        this.IDX_IntentFilter = 5;
        this.IDX_RequiredPermission = 6;
      } else {
        this.IDX_IIntentReceiver = 3;
        this.IDX_IntentFilter = 4;
        this.IDX_RequiredPermission = 5;
      } 
    }
    
    public String getMethodName() {
      return "registerReceiverWithFeature";
    }
  }
  
  static class SetPackageAskScreenCompat extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      if (Build.VERSION.SDK_INT >= 15 && param1VarArgs.length > 0 && param1VarArgs[0] instanceof String)
        param1VarArgs[0] = getHostPkg(); 
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "setPackageAskScreenCompat";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class SetServiceForeground extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "setServiceForeground";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  public static class SetTaskDescription extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      // Byte code:
      //   0: aload_3
      //   1: iconst_1
      //   2: aaload
      //   3: checkcast android/app/ActivityManager$TaskDescription
      //   6: astore #4
      //   8: aload #4
      //   10: invokevirtual getLabel : ()Ljava/lang/String;
      //   13: astore #5
      //   15: aload #4
      //   17: invokevirtual getIcon : ()Landroid/graphics/Bitmap;
      //   20: astore #6
      //   22: aload #5
      //   24: ifnull -> 36
      //   27: aload #4
      //   29: astore #7
      //   31: aload #6
      //   33: ifnonnull -> 165
      //   36: invokestatic get : ()Lcom/lody/virtual/client/VClient;
      //   39: invokevirtual getCurrentApplication : ()Landroid/app/Application;
      //   42: astore #8
      //   44: aload #4
      //   46: astore #7
      //   48: aload #8
      //   50: ifnull -> 165
      //   53: aload #5
      //   55: astore #7
      //   57: aload #5
      //   59: ifnonnull -> 90
      //   62: aload #8
      //   64: invokevirtual getApplicationInfo : ()Landroid/content/pm/ApplicationInfo;
      //   67: aload #8
      //   69: invokevirtual getPackageManager : ()Landroid/content/pm/PackageManager;
      //   72: invokevirtual loadLabel : (Landroid/content/pm/PackageManager;)Ljava/lang/CharSequence;
      //   75: invokeinterface toString : ()Ljava/lang/String;
      //   80: astore #7
      //   82: goto -> 90
      //   85: astore #7
      //   87: goto -> 156
      //   90: aload #6
      //   92: astore #5
      //   94: aload #6
      //   96: ifnonnull -> 130
      //   99: aload #8
      //   101: invokevirtual getApplicationInfo : ()Landroid/content/pm/ApplicationInfo;
      //   104: aload #8
      //   106: invokevirtual getPackageManager : ()Landroid/content/pm/PackageManager;
      //   109: invokevirtual loadIcon : (Landroid/content/pm/PackageManager;)Landroid/graphics/drawable/Drawable;
      //   112: astore #8
      //   114: aload #6
      //   116: astore #5
      //   118: aload #8
      //   120: ifnull -> 130
      //   123: aload #8
      //   125: invokestatic drawableToBitMap : (Landroid/graphics/drawable/Drawable;)Landroid/graphics/Bitmap;
      //   128: astore #5
      //   130: new android/app/ActivityManager$TaskDescription
      //   133: astore #6
      //   135: aload #6
      //   137: aload #7
      //   139: aload #5
      //   141: aload #4
      //   143: invokevirtual getPrimaryColor : ()I
      //   146: invokespecial <init> : (Ljava/lang/String;Landroid/graphics/Bitmap;I)V
      //   149: aload #6
      //   151: astore #7
      //   153: goto -> 165
      //   156: aload #7
      //   158: invokevirtual printStackTrace : ()V
      //   161: aload #4
      //   163: astore #7
      //   165: invokestatic get : ()Lcom/lody/virtual/client/core/VirtualCore;
      //   168: invokevirtual getTaskDescriptionDelegate : ()Lcom/lody/virtual/client/hook/delegate/TaskDescriptionDelegate;
      //   171: astore #4
      //   173: aload #7
      //   175: astore #5
      //   177: aload #4
      //   179: ifnull -> 193
      //   182: aload #4
      //   184: aload #7
      //   186: invokeinterface getTaskDescription : (Landroid/app/ActivityManager$TaskDescription;)Landroid/app/ActivityManager$TaskDescription;
      //   191: astore #5
      //   193: aload_3
      //   194: iconst_1
      //   195: aload #5
      //   197: aastore
      //   198: aload_2
      //   199: aload_1
      //   200: aload_3
      //   201: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
      //   204: areturn
      // Exception table:
      //   from	to	target	type
      //   62	82	85	finally
      //   99	114	85	finally
      //   123	130	85	finally
      //   130	149	85	finally
    }
    
    public String getMethodName() {
      return "setTaskDescription";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class ShouldUpRecreateTask extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return Boolean.valueOf(false);
    }
    
    public String getMethodName() {
      return "shouldUpRecreateTask";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  public static class StartActivities extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      (new Exception()).printStackTrace();
      Intent[] arrayOfIntent = (Intent[])ArrayUtils.getFirst(param1VarArgs, Intent[].class);
      String[] arrayOfString = (String[])ArrayUtils.getFirst(param1VarArgs, String[].class);
      int i = ArrayUtils.indexOfObject(param1VarArgs, IBinder.class, 2);
      if (i != -1) {
        param1Object = param1VarArgs[i];
      } else {
        param1Object = null;
      } 
      Bundle bundle = (Bundle)ArrayUtils.getFirst(param1VarArgs, Bundle.class);
      return Integer.valueOf(VActivityManager.get().startActivities(arrayOfIntent, arrayOfString, (IBinder)param1Object, bundle, VClient.get().getCurrentPackage(), VUserHandle.myUserId()));
    }
    
    public String getMethodName() {
      return "startActivities";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  public static class StartActivity extends MethodProxy {
    private static final String SCHEME_CONTENT = "content";
    
    private static final String SCHEME_FILE = "file";
    
    private static final String SCHEME_PACKAGE = "package";
    
    private boolean handleInstallRequest(Intent param1Intent) {
      VirtualCore.AppRequestListener appRequestListener = VirtualCore.get().getAppRequestListener();
      if (appRequestListener != null) {
        Uri uri = param1Intent.getData();
        if ("file".equals(uri.getScheme())) {
          appRequestListener.onRequestInstall(NativeEngine.getRedirectedPath((new File(uri.getPath())).getAbsolutePath()));
          return true;
        } 
        if ("content".equals(uri.getScheme())) {
          Intent intent;
          byte[] arrayOfByte1;
          Exception exception;
          File file = new File(getHostContext().getCacheDir(), uri.getLastPathSegment());
          param1Intent = null;
          byte[] arrayOfByte2 = null;
          try {
            InputStream inputStream1 = getHostContext().getContentResolver().openInputStream(uri);
            try {
              FileOutputStream fileOutputStream = new FileOutputStream();
              this(file);
            } catch (IOException null) {
            
            } finally {
              arrayOfByte2 = null;
            } 
          } catch (IOException iOException) {
          
          } finally {
            arrayOfByte2 = null;
            Intent intent1 = null;
            intent = param1Intent;
          } 
          try {
            intent.printStackTrace();
            FileUtils.closeQuietly((Closeable)arrayOfByte2);
            return true;
          } finally {
            Exception exception1 = null;
            arrayOfByte1 = arrayOfByte2;
          } 
          FileUtils.closeQuietly((Closeable)arrayOfByte1);
          FileUtils.closeQuietly((Closeable)param1Intent);
          throw exception;
        } 
      } 
      return false;
    }
    
    private boolean handleUninstallRequest(Intent param1Intent) {
      VirtualCore.AppRequestListener appRequestListener = VirtualCore.get().getAppRequestListener();
      if (appRequestListener != null) {
        Uri uri = param1Intent.getData();
        if ("package".equals(uri.getScheme())) {
          appRequestListener.onRequestUninstall(uri.getSchemeSpecificPart());
          return true;
        } 
      } 
      return false;
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      Intent intent1;
      ResolveInfo resolveInfo;
      Bundle bundle1;
      String str2;
      int i = ArrayUtils.indexOfObject(param1VarArgs, Intent.class, 1);
      if (i < 0)
        return Integer.valueOf(ActivityManagerCompat.START_INTENT_NOT_RESOLVED); 
      int j = ArrayUtils.indexOfObject(param1VarArgs, IBinder.class, 2);
      String str1 = (String)param1VarArgs[i + 1];
      Intent intent2 = new Intent((Intent)param1VarArgs[i]);
      intent2.setDataAndType(intent2.getData(), str1);
      if (j >= 0) {
        IBinder iBinder = (IBinder)param1VarArgs[j];
      } else {
        str1 = null;
      } 
      Bundle bundle2 = (Bundle)ArrayUtils.getFirst(param1VarArgs, Bundle.class);
      if (str1 != null) {
        str2 = (String)param1VarArgs[j + 1];
        j = ((Integer)param1VarArgs[j + 2]).intValue();
      } else {
        j = 0;
        str2 = null;
      } 
      int k = VUserHandle.myUserId();
      if ("android.intent.action.MAIN".equals(intent2.getAction()) && intent2.hasCategory("android.intent.category.HOME")) {
        intent1 = getConfig().onHandleLauncherIntent(intent2);
        if (intent1 != null)
          param1VarArgs[i] = intent1; 
        return param1Method.invoke(param1Object, param1VarArgs);
      } 
      if (isHostIntent(intent2))
        return param1Method.invoke(param1Object, param1VarArgs); 
      if ("android.intent.action.INSTALL_PACKAGE".equals(intent2.getAction()) || ("android.intent.action.VIEW".equals(intent2.getAction()) && "application/vnd.android.package-archive".equals(intent2.getType()))) {
        if (handleInstallRequest(intent2)) {
          if (intent1 != null && j > 0)
            VActivityManager.get().sendCancelActivityResult((IBinder)intent1, str2, j); 
          return Integer.valueOf(0);
        } 
      } else if (("android.intent.action.UNINSTALL_PACKAGE".equals(intent2.getAction()) || "android.intent.action.DELETE".equals(intent2.getAction())) && "package".equals(intent2.getScheme()) && handleUninstallRequest(intent2)) {
        return Integer.valueOf(0);
      } 
      String str3 = intent2.getPackage();
      if (str3 != null && !isAppPkg(str3)) {
        if (BuildCompat.isR() && "android.content.pm.action.REQUEST_PERMISSIONS".equals(intent2.getAction()))
          param1VarArgs[i - 2] = getHostPkg(); 
        return param1Method.invoke(param1Object, param1VarArgs);
      } 
      if (ChooserActivity.check(intent2)) {
        Intent intent = ComponentUtils.processOutsideIntent(k, VirtualCore.get().isExtPackage(), new Intent(intent2));
        param1VarArgs[i] = intent;
        bundle1 = new Bundle();
        bundle1.putInt("android.intent.extra.user_handle", k);
        bundle1.putBundle("android.intent.extra.virtual.data", bundle2);
        bundle1.putString("android.intent.extra.virtual.who", str2);
        bundle1.putInt("android.intent.extra.virtual.request_code", j);
        BundleCompat.putBinder(bundle1, "_va|ibinder|resultTo", (IBinder)intent1);
        intent.setComponent(new ComponentName(StubManifest.PACKAGE_NAME, ChooserActivity.class.getName()));
        intent.setAction(null);
        intent.putExtras(bundle1);
        return param1Method.invoke(param1Object, param1VarArgs);
      } 
      param1VarArgs[i - 1] = getHostPkg();
      if (bundle1.getScheme() != null && bundle1.getScheme().equals("package") && bundle1.getData() != null && bundle1.getAction() != null && bundle1.getAction().startsWith("android.settings.")) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("package:");
        stringBuilder.append(getHostPkg());
        bundle1.setData(Uri.parse(stringBuilder.toString()));
      } 
      ActivityInfo activityInfo = VirtualCore.get().resolveActivityInfo((Intent)bundle1, k);
      if (activityInfo == null) {
        VLog.e("VActivityManager", "Unable to resolve activityInfo : %s", new Object[] { bundle1 });
        if (bundle1.getPackage() != null && isAppPkg(bundle1.getPackage()))
          return Integer.valueOf(0); 
        param1VarArgs[i] = ComponentUtils.processOutsideIntent(k, VirtualCore.get().isExtPackage(), (Intent)bundle1);
        resolveInfo = VirtualCore.get().getHostPackageManager().resolveActivity((Intent)bundle1, 0);
        return (resolveInfo == null || resolveInfo.activityInfo == null) ? ((bundle1.resolveActivityInfo(VirtualCore.getPM(), 0) != null) ? param1Method.invoke(param1Object, param1VarArgs) : Integer.valueOf(ActivityManagerCompat.START_INTENT_NOT_RESOLVED)) : ((!"android.intent.action.VIEW".equals(bundle1.getAction()) && !getConfig().isOutsideAction(bundle1.getAction()) && !isOutsidePackage(resolveInfo.activityInfo.packageName)) ? Integer.valueOf(ActivityManagerCompat.START_INTENT_NOT_RESOLVED) : param1Method.invoke(param1Object, param1VarArgs));
      } 
      i = VActivityManager.get().startActivity((Intent)bundle1, activityInfo, (IBinder)resolveInfo, bundle2, str2, j, VClient.get().getCurrentPackage(), VUserHandle.myUserId());
      if (i != 0 && resolveInfo != null && j > 0)
        VActivityManager.get().sendCancelActivityResult((IBinder)resolveInfo, str2, j); 
      return Integer.valueOf(i);
    }
    
    public String getMethodName() {
      return "startActivity";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  public static class StartActivityAndWait extends StartActivity {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return super.call(param1Object, param1Method, param1VarArgs);
    }
    
    public String getMethodName() {
      return "startActivityAndWait";
    }
  }
  
  static class StartActivityAsCaller extends StartActivity {
    public String getMethodName() {
      return "startActivityAsCaller";
    }
  }
  
  public static class StartActivityAsUser extends StartActivity {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      replaceLastUserId(param1VarArgs);
      return super.call(param1Object, param1Method, param1VarArgs);
    }
    
    public String getMethodName() {
      return "startActivityAsUser";
    }
  }
  
  public static class StartActivityIntentSender extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      byte b6;
      byte b7;
      boolean bool = BuildCompat.isOreo();
      byte b1 = 9;
      byte b2 = 8;
      byte b3 = 7;
      byte b4 = 6;
      byte b5 = 5;
      if (bool) {
        b6 = 3;
        b1 = 10;
        b2 = 9;
        b3 = 8;
        b4 = 7;
        b5 = 6;
        b7 = 5;
      } else {
        b6 = 2;
        b7 = 4;
      } 
      IInterface iInterface = (IInterface)param1VarArgs[1];
      Intent intent2 = (Intent)param1VarArgs[b6];
      IBinder iBinder = (IBinder)param1VarArgs[b7];
      String str = (String)param1VarArgs[b5];
      ((Integer)param1VarArgs[b4]).intValue();
      Bundle bundle = (Bundle)param1VarArgs[b1];
      ((Integer)param1VarArgs[b3]).intValue();
      ((Integer)param1VarArgs[b2]).intValue();
      Intent intent1 = intent2;
      if (intent2 == null) {
        intent1 = new Intent();
        param1VarArgs[b6] = intent1;
      } 
      ComponentUtils.parcelActivityIntentSender(intent1, iBinder, bundle);
      return super.call(param1Object, param1Method, param1VarArgs);
    }
    
    public String getMethodName() {
      return "startActivityIntentSender";
    }
  }
  
  public static class StartActivityWithConfig extends StartActivity {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return super.call(param1Object, param1Method, param1VarArgs);
    }
    
    public String getMethodName() {
      return "startActivityWithConfig";
    }
  }
  
  static class StartNextMatchingActivity extends StartActivity {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return Boolean.valueOf(false);
    }
    
    public String getMethodName() {
      return "startNextMatchingActivity";
    }
  }
  
  static class StartService extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      int i;
      Intent intent = new Intent((Intent)param1VarArgs[1]);
      String str = (String)param1VarArgs[2];
      ComponentName componentName = intent.getComponent();
      if (componentName != null && isHostPkg(componentName.getPackageName()))
        return param1Method.invoke(param1Object, param1VarArgs); 
      if (isServerProcess()) {
        i = intent.getIntExtra("_VA_|_user_id_", -1);
      } else {
        i = VUserHandle.myUserId();
      } 
      if (i == -1)
        return param1Method.invoke(param1Object, param1VarArgs); 
      intent.setDataAndType(intent.getData(), str);
      ServiceInfo serviceInfo = VirtualCore.get().resolveServiceInfo(intent, i);
      if (serviceInfo != null) {
        if (Build.VERSION.SDK_INT >= 26 && param1VarArgs.length >= 6 && param1VarArgs[3] instanceof Boolean)
          param1VarArgs[3] = Boolean.valueOf(false); 
        ClientConfig clientConfig = VActivityManager.get().initProcess(serviceInfo.packageName, serviceInfo.processName, i);
        if (clientConfig == null) {
          param1Object = new StringBuilder();
          param1Object.append("failed to initProcess for startService: ");
          param1Object.append(componentName);
          VLog.e("ActivityManager", param1Object.toString());
          return null;
        } 
        param1VarArgs[1] = IntentBuilder.createStartProxyServiceIntent(clientConfig.vpid, clientConfig.isExt, serviceInfo, intent, i);
        replaceLastUserId(param1VarArgs);
        return ((ComponentName)param1Method.invoke(param1Object, param1VarArgs) != null) ? new ComponentName(serviceInfo.packageName, serviceInfo.name) : null;
      } 
      if ((componentName == null || !isOutsidePackage(componentName.getPackageName())) && intent.getPackage() != null && !isOutsidePackage(intent.getPackage())) {
        param1Object = new StringBuilder();
        param1Object.append("Block StartService: ");
        param1Object.append(intent);
        VLog.e("VActivityManager", param1Object.toString());
        return null;
      } 
      replaceLastUserId(param1VarArgs);
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "startService";
    }
    
    public boolean isEnable() {
      return (isAppProcess() || isServerProcess());
    }
  }
  
  static class StartVoiceActivity extends StartActivity {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return super.call(param1Object, param1Method, param1VarArgs);
    }
    
    public String getMethodName() {
      return "startVoiceActivity";
    }
  }
  
  static class StopService extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      int i;
      Integer integer = Integer.valueOf(0);
      IInterface iInterface = (IInterface)param1VarArgs[0];
      Intent intent = new Intent((Intent)param1VarArgs[1]);
      String str = (String)param1VarArgs[2];
      intent.setDataAndType(intent.getData(), str);
      ComponentName componentName = intent.getComponent();
      if (componentName != null && isHostPkg(componentName.getPackageName()))
        return param1Method.invoke(param1Object, param1VarArgs); 
      if (isServerProcess()) {
        i = intent.getIntExtra("_VA_|_user_id_", -1);
      } else {
        i = VUserHandle.myUserId();
      } 
      if (i != -1) {
        ClientConfig clientConfig;
        ServiceInfo serviceInfo = VirtualCore.get().resolveServiceInfo(intent, i);
        if (serviceInfo != null && isAppPkg(serviceInfo.packageName)) {
          clientConfig = VActivityManager.get().initProcess(serviceInfo.packageName, serviceInfo.processName, i);
          if (clientConfig == null) {
            param1Object = new StringBuilder();
            param1Object.append("failed to initProcess for stopService: ");
            param1Object.append(componentName);
            VLog.e("ActivityManager", param1Object.toString());
            return integer;
          } 
          param1Object = componentName;
          if (componentName == null)
            param1Object = new ComponentName(serviceInfo.packageName, serviceInfo.name); 
          param1Object = IntentBuilder.createStopProxyServiceIntent(clientConfig.vpid, clientConfig.isExt, (ComponentName)param1Object, i, -1, null);
          getHostContext().startService((Intent)param1Object);
          return Integer.valueOf(1);
        } 
        if (componentName != null && isOutsidePackage(componentName.getPackageName())) {
          replaceLastUserId(param1VarArgs);
          return clientConfig.invoke(param1Object, param1VarArgs);
        } 
        return integer;
      } 
      throw new IllegalArgumentException();
    }
    
    public String getMethodName() {
      return "stopService";
    }
    
    public boolean isEnable() {
      return (isAppProcess() || isServerProcess());
    }
  }
  
  static class StopServiceToken extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      Boolean bool = Boolean.valueOf(false);
      ComponentName componentName = (ComponentName)param1VarArgs[0];
      IBinder iBinder = (IBinder)param1VarArgs[1];
      int i = ((Integer)param1VarArgs[2]).intValue();
      int j = VUserHandle.myUserId();
      ServiceInfo serviceInfo = VPackageManager.get().getServiceInfo(componentName, 0, j);
      if (serviceInfo != null) {
        param1Object = VActivityManager.get().initProcess(serviceInfo.packageName, serviceInfo.processName, j);
        if (param1Object == null) {
          VLog.e("ActivityManager", "failed to initProcess for stopServiceToken");
          return bool;
        } 
        param1Object = IntentBuilder.createStopProxyServiceIntent(((ClientConfig)param1Object).vpid, ((ClientConfig)param1Object).isExt, componentName, j, i, iBinder);
        getHostContext().startService((Intent)param1Object);
        return Boolean.valueOf(true);
      } 
      return isOutsidePackage(componentName.getPackageName()) ? param1Method.invoke(param1Object, param1VarArgs) : bool;
    }
    
    public String getMethodName() {
      return "stopServiceToken";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class UnbindService extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      ServiceConnectionProxy serviceConnectionProxy = ServiceConnectionProxy.removeProxy((IServiceConnection)param1VarArgs[0]);
      if (serviceConnectionProxy != null)
        param1VarArgs[0] = serviceConnectionProxy; 
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "unbindService";
    }
    
    public boolean isEnable() {
      return (isAppProcess() || isServerProcess());
    }
  }
  
  static class UnstableProviderDied extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return (param1VarArgs[0] == null) ? Integer.valueOf(0) : param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "unstableProviderDied";
    }
  }
  
  static class UpdateDeviceOwner extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      MethodParameterUtils.replaceFirstAppPkg(param1VarArgs);
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "updateDeviceOwner";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
  
  static class isUserRunning extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) {
      int i = ((Integer)param1VarArgs[0]).intValue();
      param1Object = VUserManager.get().getUsers().iterator();
      while (param1Object.hasNext()) {
        if (((VUserInfo)param1Object.next()).id == i)
          return Boolean.valueOf(true); 
      } 
      return Boolean.valueOf(false);
    }
    
    public String getMethodName() {
      return "isUserRunning";
    }
    
    public boolean isEnable() {
      return isAppProcess();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\am\MethodProxies.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */