package com.lody.virtual.helper.utils;

import android.content.ClipData;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.text.TextUtils;
import com.lody.virtual.GmsSupport;
import com.lody.virtual.client.NativeEngine;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.env.SpecialComponentList;
import com.lody.virtual.client.ipc.VPackageManager;
import com.lody.virtual.client.stub.ContentProviderProxy;
import com.lody.virtual.client.stub.ShadowPendingActivity;
import com.lody.virtual.client.stub.ShadowPendingReceiver;
import com.lody.virtual.client.stub.ShadowPendingService;
import com.lody.virtual.helper.compat.ObjectsCompat;
import com.lody.virtual.os.VUserHandle;
import com.lody.virtual.remote.BroadcastIntentData;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import mirror.android.content.Intent;

public class ComponentUtils {
  public static String getComponentAction(ComponentName paramComponentName) {
    return getComponentAction(paramComponentName.getPackageName(), paramComponentName.getClassName());
  }
  
  public static String getComponentAction(ActivityInfo paramActivityInfo) {
    return getComponentAction(paramActivityInfo.packageName, paramActivityInfo.name);
  }
  
  public static String getComponentAction(String paramString1, String paramString2) {
    return String.format("_VA_%s_%s_%s", new Object[] { VirtualCore.get().getHostPkg(), paramString1, paramString2 });
  }
  
  public static String getProcessName(ComponentInfo paramComponentInfo) {
    String str1 = paramComponentInfo.processName;
    String str2 = str1;
    if (str1 == null) {
      str2 = paramComponentInfo.packageName;
      paramComponentInfo.processName = str2;
    } 
    return str2;
  }
  
  public static Intent getProxyIntentSenderIntent(int paramInt1, int paramInt2, String paramString, Intent paramIntent) {
    StringBuilder stringBuilder;
    String str2;
    if (paramInt2 == 3) {
      stringBuilder = new StringBuilder();
      stringBuilder.append("Unsupported IntentSender type: ");
      stringBuilder.append(paramInt2);
      VLog.printStackTrace(stringBuilder.toString());
      return null;
    } 
    Intent intent1 = paramIntent.cloneFilter();
    intent1.setSourceBounds(paramIntent.getSourceBounds());
    if (Build.VERSION.SDK_INT >= 16)
      intent1.setClipData(paramIntent.getClipData()); 
    intent1.addFlags(0x3 & paramIntent.getFlags());
    if (Build.VERSION.SDK_INT >= 19)
      intent1.addFlags(paramIntent.getFlags() & 0x40); 
    if (Build.VERSION.SDK_INT >= 21)
      intent1.addFlags(paramIntent.getFlags() & 0x80); 
    String str1 = intent1.getType();
    ComponentName componentName = intent1.getComponent();
    if (str1 == null) {
      StringBuilder stringBuilder1 = stringBuilder;
    } else {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(str1);
      stringBuilder1.append(":");
      stringBuilder1.append((String)stringBuilder);
      str2 = stringBuilder1.toString();
    } 
    String str3 = str2;
    if (componentName != null) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(str2);
      stringBuilder1.append(":");
      stringBuilder1.append(componentName.flattenToString());
      str3 = stringBuilder1.toString();
    } 
    intent1.setDataAndType(intent1.getData(), str3);
    if (paramInt2 == 2) {
      intent1.setComponent(new ComponentName(VirtualCore.getConfig().getMainPackageName(), ShadowPendingActivity.class.getName()));
    } else if (paramInt2 == 4) {
      intent1.setComponent(new ComponentName(VirtualCore.getConfig().getMainPackageName(), ShadowPendingService.class.getName()));
    } else {
      intent1.setComponent(new ComponentName(VirtualCore.getConfig().getMainPackageName(), ShadowPendingReceiver.class.getName()));
    } 
    Intent intent2 = new Intent();
    intent2.putExtra("_VA_|_user_id_", paramInt1);
    intent2.putExtra("_VA_|_intent_", (Parcelable)paramIntent);
    intent2.putExtra("_VA_|_target_pkg_", (String)stringBuilder);
    intent2.putExtra("_VA_|_original_type_", str1);
    intent2.putExtra("_VA_|_type_", str3);
    intent1.setPackage(null);
    intent1.setSelector(intent2);
    return intent1;
  }
  
  public static String getTaskAffinity(ActivityInfo paramActivityInfo) {
    if (paramActivityInfo.launchMode == 3) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("-SingleInstance-");
      stringBuilder.append(paramActivityInfo.packageName);
      stringBuilder.append("/");
      stringBuilder.append(paramActivityInfo.name);
      return stringBuilder.toString();
    } 
    return (paramActivityInfo.taskAffinity == null && paramActivityInfo.applicationInfo.taskAffinity == null) ? paramActivityInfo.packageName : ((paramActivityInfo.taskAffinity != null) ? paramActivityInfo.taskAffinity : paramActivityInfo.applicationInfo.taskAffinity);
  }
  
  public static boolean intentFilterEquals(Intent paramIntent1, Intent paramIntent2) {
    if (paramIntent1 != null && paramIntent2 != null) {
      if (!ObjectsCompat.equals(paramIntent1.getAction(), paramIntent2.getAction()))
        return false; 
      if (!ObjectsCompat.equals(paramIntent1.getData(), paramIntent2.getData()))
        return false; 
      if (!ObjectsCompat.equals(paramIntent1.getType(), paramIntent2.getType()))
        return false; 
      String str1 = paramIntent1.getPackage();
      String str2 = str1;
      if (str1 == null) {
        str2 = str1;
        if (paramIntent1.getComponent() != null)
          str2 = paramIntent1.getComponent().getPackageName(); 
      } 
      String str3 = paramIntent2.getPackage();
      str1 = str3;
      if (str3 == null) {
        str1 = str3;
        if (paramIntent2.getComponent() != null)
          str1 = paramIntent2.getComponent().getPackageName(); 
      } 
      if (!ObjectsCompat.equals(str2, str1))
        return false; 
      if (!ObjectsCompat.equals(paramIntent1.getComponent(), paramIntent2.getComponent()))
        return false; 
      if (!ObjectsCompat.equals(paramIntent1.getCategories(), paramIntent2.getCategories()))
        return false; 
    } 
    return true;
  }
  
  public static boolean isSameComponent(ComponentInfo paramComponentInfo1, ComponentInfo paramComponentInfo2) {
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (paramComponentInfo1 != null) {
      bool2 = bool1;
      if (paramComponentInfo2 != null) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(paramComponentInfo1.packageName);
        stringBuilder1.append("");
        String str3 = stringBuilder1.toString();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(paramComponentInfo2.packageName);
        stringBuilder2.append("");
        String str4 = stringBuilder2.toString();
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append(paramComponentInfo1.name);
        stringBuilder3.append("");
        String str1 = stringBuilder3.toString();
        stringBuilder3 = new StringBuilder();
        stringBuilder3.append(paramComponentInfo2.name);
        stringBuilder3.append("");
        String str2 = stringBuilder3.toString();
        bool2 = bool1;
        if (str3.equals(str4)) {
          bool2 = bool1;
          if (str1.equals(str2))
            bool2 = true; 
        } 
      } 
    } 
    return bool2;
  }
  
  public static boolean isSystemApp(ApplicationInfo paramApplicationInfo) {
    boolean bool = false;
    if (paramApplicationInfo == null)
      return false; 
    if (GmsSupport.isGoogleAppOrService(paramApplicationInfo.packageName))
      return false; 
    if (SpecialComponentList.isSpecSystemPackage(paramApplicationInfo.packageName))
      return true; 
    if (paramApplicationInfo.uid >= 10000) {
      if ((paramApplicationInfo.flags & 0x1) != 0 || (paramApplicationInfo.flags & 0x80) != 0)
        bool = true; 
      return bool;
    } 
    return true;
  }
  
  public static void parcelActivityIntentSender(Intent paramIntent, IBinder paramIBinder, Bundle paramBundle) {
    Bundle bundle = paramIntent.getExtras();
    if (bundle != null) {
      paramIntent.getExtras().clear();
      paramIntent.putExtra("_VA_|_fill_in_", bundle);
    } 
    if (paramBundle != null)
      paramIntent.putExtra("_VA_|_options_", paramBundle); 
    Intent.putExtra.call(paramIntent, new Object[] { "_VA_|_caller_activity_", paramIBinder });
  }
  
  public static IntentSenderInfo parseIntentSenderInfo(Intent paramIntent, boolean paramBoolean) {
    Intent intent1 = paramIntent.getSelector();
    if (intent1 == null)
      return null; 
    IntentSenderInfo intentSenderInfo = new IntentSenderInfo();
    intentSenderInfo.userId = intent1.getIntExtra("_VA_|_user_id_", -1);
    intentSenderInfo.targetPkg = intent1.getStringExtra("_VA_|_target_pkg_");
    intentSenderInfo.originalType = intent1.getStringExtra("_VA_|_original_type_");
    intentSenderInfo.type = intent1.getStringExtra("_VA_|_type_");
    intentSenderInfo.fillIn = paramIntent.getExtras();
    intent1 = (Intent)intent1.getParcelableExtra("_VA_|_intent_");
    intentSenderInfo.base = intent1.getExtras();
    Intent intent2 = paramIntent.cloneFilter();
    intent2.setComponent(intent1.getComponent());
    intent2.setPackage(intent1.getPackage());
    intent2.setSelector(intent1.getSelector());
    intent2.setFlags(intent1.getFlags());
    if (TextUtils.equals(intentSenderInfo.type, paramIntent.getType()))
      intent2.setDataAndType(intent1.getData(), intentSenderInfo.originalType); 
    if (Build.VERSION.SDK_INT > 15 && intent1.getClipData() == null && paramIntent.getClipData() != null) {
      intent2.setClipData(paramIntent.getClipData());
      if ((paramIntent.getFlags() & 0x10000000) != 0)
        intent2.addFlags(268435456); 
    } 
    if (paramBoolean) {
      intentSenderInfo.callerActivity = (IBinder)Intent.getIBinderExtra.call(paramIntent, new Object[] { "_VA_|_caller_activity_" });
      intentSenderInfo.options = paramIntent.getBundleExtra("_VA_|_options_");
      intentSenderInfo.fillIn = paramIntent.getBundleExtra("_VA_|_fill_in_");
    } 
    intent2.putExtra("_VA_|_fill_in_", intentSenderInfo.fillIn);
    intent2.putExtra("_VA_|_base_", intentSenderInfo.base);
    intentSenderInfo.intent = intent2;
    return intentSenderInfo;
  }
  
  public static Intent processOutsideIntent(int paramInt, boolean paramBoolean, Intent paramIntent) {
    Uri uri = paramIntent.getData();
    if (uri != null)
      paramIntent.setDataAndType(processOutsideUri(paramInt, paramBoolean, uri), paramIntent.getType()); 
    if (Build.VERSION.SDK_INT >= 16 && paramIntent.getClipData() != null) {
      ClipData clipData = paramIntent.getClipData();
      if (clipData.getItemCount() >= 0) {
        ClipData.Item item = clipData.getItemAt(0);
        uri = item.getUri();
        if (uri != null) {
          Uri uri1 = processOutsideUri(paramInt, paramBoolean, uri);
          if (uri1 != uri) {
            ClipData clipData1 = new ClipData(clipData.getDescription(), new ClipData.Item(item.getText(), item.getHtmlText(), item.getIntent(), uri1));
            for (byte b = 1; b < clipData.getItemCount(); b++) {
              ClipData.Item item1 = clipData.getItemAt(b);
              Uri uri2 = item1.getUri();
              uri = uri2;
              if (uri2 != null)
                uri = processOutsideUri(paramInt, paramBoolean, uri2); 
              clipData1.addItem(new ClipData.Item(item1.getText(), item1.getHtmlText(), item1.getIntent(), uri));
            } 
            paramIntent.setClipData(clipData1);
          } 
        } 
      } 
    } 
    if (paramIntent.hasExtra("output")) {
      Parcelable parcelable = paramIntent.getParcelableExtra("output");
      if (parcelable instanceof Uri) {
        paramIntent.putExtra("output", (Parcelable)processOutsideUri(paramInt, paramBoolean, (Uri)parcelable));
      } else if (parcelable instanceof ArrayList) {
        arrayList1 = (ArrayList)parcelable;
        ArrayList<Uri> arrayList = new ArrayList();
        for (ArrayList arrayList1 : arrayList1) {
          if (!(arrayList1 instanceof Uri))
            break; 
          arrayList.add(processOutsideUri(paramInt, paramBoolean, (Uri)arrayList1));
        } 
        if (!arrayList.isEmpty())
          paramIntent.putExtra("output", arrayList); 
      } 
    } 
    if (paramIntent.hasExtra("android.intent.extra.STREAM")) {
      Parcelable parcelable = paramIntent.getParcelableExtra("android.intent.extra.STREAM");
      if (parcelable instanceof Uri) {
        paramIntent.putExtra("android.intent.extra.STREAM", (Parcelable)processOutsideUri(paramInt, paramBoolean, (Uri)parcelable));
      } else if (parcelable instanceof ArrayList) {
        ArrayList arrayList1 = (ArrayList)parcelable;
        ArrayList<Uri> arrayList = new ArrayList();
        for (Uri uri1 : arrayList1) {
          if (!(uri1 instanceof Uri))
            break; 
          arrayList.add(processOutsideUri(paramInt, paramBoolean, uri1));
        } 
        if (!arrayList.isEmpty())
          paramIntent.putExtra("android.intent.extra.STREAM", arrayList); 
      } 
    } 
    return paramIntent;
  }
  
  public static Uri processOutsideUri(int paramInt, boolean paramBoolean, Uri paramUri) {
    if (TextUtils.equals(paramUri.getScheme(), "file"))
      return Uri.fromFile(new File(NativeEngine.reverseRedirectedPath(paramUri.getPath()))); 
    if (!TextUtils.equals(paramUri.getScheme(), "content"))
      return paramUri; 
    String str = paramUri.getAuthority();
    if (str == null)
      return paramUri; 
    ProviderInfo providerInfo1 = VirtualCore.get().getHostPackageManager().resolveContentProvider(str, 0);
    ProviderInfo providerInfo2 = providerInfo1;
    if (providerInfo1 == null)
      providerInfo2 = VPackageManager.get().resolveContentProvider(str, 0, VUserHandle.myUserId()); 
    return (providerInfo2 == null) ? paramUri : ContentProviderProxy.buildProxyUri(paramInt, paramBoolean, str, paramUri);
  }
  
  public static Intent proxyBroadcastIntent(Intent paramIntent, int paramInt) {
    String str1;
    if (paramIntent.getAction() != null && VirtualCore.getConfig().isUnProtectAction(paramIntent.getAction()))
      return paramIntent; 
    Intent intent = new Intent();
    intent.setDataAndType(paramIntent.getData(), paramIntent.getType());
    Set set = paramIntent.getCategories();
    if (set != null) {
      Iterator<String> iterator = set.iterator();
      while (iterator.hasNext())
        intent.addCategory(iterator.next()); 
    } 
    ComponentName componentName = paramIntent.getComponent();
    String str2 = paramIntent.getPackage();
    if (componentName != null) {
      intent.setAction(getComponentAction(componentName));
      str1 = str2;
      if (str2 == null)
        str1 = componentName.getPackageName(); 
    } else {
      intent.setAction(SpecialComponentList.protectAction(paramIntent.getAction()));
      str1 = str2;
    } 
    (new BroadcastIntentData(paramInt, paramIntent, str1)).saveIntent(intent);
    return intent;
  }
  
  public static ComponentName toComponentName(ComponentInfo paramComponentInfo) {
    return new ComponentName(paramComponentInfo.packageName, paramComponentInfo.name);
  }
  
  public static void unpackFillIn(Intent paramIntent, ClassLoader paramClassLoader) {
    paramIntent.setExtrasClassLoader(paramClassLoader);
    try {
      Bundle bundle = paramIntent.getExtras();
    } finally {
      paramIntent = null;
    } 
  }
  
  public static class IntentSenderInfo {
    public Bundle base;
    
    public IBinder callerActivity;
    
    public Bundle fillIn;
    
    public Intent intent;
    
    public Bundle options;
    
    public String originalType;
    
    public String targetPkg;
    
    public String type;
    
    public int userId;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helpe\\utils\ComponentUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */