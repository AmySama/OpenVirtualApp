package com.lody.virtual.client.hook.proxies.shortcut;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArraySet;
import com.lody.virtual.client.VClient;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import com.lody.virtual.helper.compat.ParceledListSliceCompat;
import com.lody.virtual.helper.utils.BitmapUtils;
import com.lody.virtual.helper.utils.Reflect;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import mirror.android.content.pm.IShortcutService;
import mirror.android.content.pm.ParceledListSlice;

public class ShortcutServiceStub extends BinderInvocationProxy {
  public ShortcutServiceStub() {
    super(IShortcutService.Stub.TYPE, "shortcut");
  }
  
  private static <T> String setToString(Set<T> paramSet) {
    if (paramSet == null)
      return null; 
    StringBuilder stringBuilder = new StringBuilder();
    Iterator<T> iterator = paramSet.iterator();
    boolean bool = true;
    while (iterator.hasNext()) {
      if (bool) {
        bool = false;
      } else {
        stringBuilder.append(",");
      } 
      stringBuilder.append(iterator.next());
    } 
    return stringBuilder.toString();
  }
  
  private static Set<String> toSet(String paramString) {
    if (paramString == null)
      return null; 
    String[] arrayOfString = paramString.split(",");
    ArraySet<String> arraySet = new ArraySet();
    int i = arrayOfString.length;
    for (byte b = 0; b < i; b++)
      arraySet.add(arrayOfString[b]); 
    return (Set<String>)arraySet;
  }
  
  static ShortcutInfo unWrapper(Context paramContext, ShortcutInfo paramShortcutInfo, String paramString, int paramInt) throws URISyntaxException {
    Intent intent = paramShortcutInfo.getIntent();
    String str1 = null;
    if (intent == null)
      return null; 
    String str2 = intent.getStringExtra("_VA_|_pkg_");
    int i = intent.getIntExtra("_VA_|_user_id_", 0);
    if (TextUtils.equals(str2, paramString) && i == paramInt) {
      Intent intent1;
      paramString = paramShortcutInfo.getId();
      String str4 = paramString.substring(paramString.indexOf("/") + 1);
      Icon icon = (Icon)Reflect.on(paramShortcutInfo).opt("mIcon");
      String str5 = intent.getStringExtra("_VA_|_uri_");
      paramString = str1;
      if (!TextUtils.isEmpty(str5))
        intent1 = Intent.parseUri(str5, 0); 
      ComponentName componentName = (ComponentName)intent.getParcelableExtra("_VA_|activity");
      String str3 = intent.getStringExtra("_VA_|categories");
      ShortcutInfo.Builder builder = new ShortcutInfo.Builder(paramContext, str4);
      if (icon != null)
        builder.setIcon(icon); 
      if (paramShortcutInfo.getLongLabel() != null)
        builder.setLongLabel(paramShortcutInfo.getLongLabel()); 
      if (paramShortcutInfo.getShortLabel() != null)
        builder.setShortLabel(paramShortcutInfo.getShortLabel()); 
      if (componentName != null)
        builder.setActivity(componentName); 
      if (intent1 != null)
        builder.setIntent(intent1); 
      Set<String> set = toSet(str3);
      if (set != null)
        builder.setCategories(set); 
      return builder.build();
    } 
    return null;
  }
  
  static ShortcutInfo wrapper(Context paramContext, ShortcutInfo paramShortcutInfo, String paramString, int paramInt) {
    Bitmap bitmap;
    Icon icon = (Icon)Reflect.on(paramShortcutInfo).opt("mIcon");
    if (icon != null) {
      bitmap = BitmapUtils.drawableToBitmap(icon.loadDrawable(paramContext));
    } else {
      PackageManager packageManager = VirtualCore.get().getPackageManager();
      bitmap = BitmapUtils.drawableToBitmap(bitmap.getApplicationInfo().loadIcon(packageManager));
    } 
    Intent intent = VirtualCore.get().wrapperShortcutIntent(paramShortcutInfo.getIntent(), null, paramString, paramInt);
    intent.putExtra("_VA_|categories", setToString(paramShortcutInfo.getCategories()));
    intent.putExtra("_VA_|activity", (Parcelable)paramShortcutInfo.getActivity());
    Context context = VirtualCore.get().getContext();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append("@");
    stringBuilder.append(paramInt);
    stringBuilder.append("/");
    stringBuilder.append(paramShortcutInfo.getId());
    ShortcutInfo.Builder builder = new ShortcutInfo.Builder(context, stringBuilder.toString());
    if (paramShortcutInfo.getLongLabel() != null)
      builder.setLongLabel(paramShortcutInfo.getLongLabel()); 
    if (paramShortcutInfo.getShortLabel() != null)
      builder.setShortLabel(paramShortcutInfo.getShortLabel()); 
    builder.setIcon(Icon.createWithBitmap(bitmap));
    builder.setIntent(intent);
    return builder.build();
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("disableShortcuts"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("enableShortcuts"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getRemainingCallCount"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getRateLimitResetTime"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getIconMaxDimensions"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getMaxShortcutCountPerActivity"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("reportShortcutUsed"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("onApplicationActive"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("hasShortcutHostPermission"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("removeAllDynamicShortcuts"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("removeDynamicShortcuts"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getShortcuts"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("removeLongLivedShortcuts"));
    Boolean bool = Boolean.valueOf(false);
    addMethodProxy((MethodProxy)new WrapperShortcutInfo("requestPinShortcut", 1, bool));
    addMethodProxy((MethodProxy)new UnWrapperShortcutInfo("getPinnedShortcuts"));
    addMethodProxy((MethodProxy)new WrapperShortcutInfo("addDynamicShortcuts", 1, bool));
    addMethodProxy((MethodProxy)new WrapperShortcutInfo("setDynamicShortcuts", 1, bool));
    addMethodProxy((MethodProxy)new UnWrapperShortcutInfo("getDynamicShortcuts"));
    addMethodProxy((MethodProxy)new WrapperShortcutInfo("createShortcutResultIntent", 1, null));
    addMethodProxy((MethodProxy)new WrapperShortcutInfo("updateShortcuts", 1, bool));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getManifestShortcuts") {
          public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) {
            return ParceledListSliceCompat.create(new ArrayList());
          }
        });
  }
  
  static class UnWrapperShortcutInfo extends ReplaceCallingPkgMethodProxy {
    public UnWrapperShortcutInfo(String param1String) {
      super(param1String);
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      Object object = super.call(param1Object, param1Method, param1VarArgs);
      if (object != null) {
        param1Object = new ArrayList();
        if (!getConfig().isAllowCreateShortcut())
          return ParceledListSliceCompat.create((List)param1Object); 
        object = ParceledListSlice.getList.call(object, new Object[0]);
        if (object != null)
          for (int i = object.size() - 1; i >= 0; i--) {
            param1VarArgs = object.get(i);
            if (param1VarArgs instanceof ShortcutInfo) {
              ShortcutInfo shortcutInfo = (ShortcutInfo)param1VarArgs;
              shortcutInfo = ShortcutServiceStub.unWrapper((Context)VClient.get().getCurrentApplication(), shortcutInfo, getAppPkg(), getAppUserId());
              if (shortcutInfo != null)
                param1Object.add(shortcutInfo); 
            } 
          }  
        return ParceledListSliceCompat.create((List)param1Object);
      } 
      return null;
    }
  }
  
  static class WrapperShortcutInfo extends ReplaceCallingPkgMethodProxy {
    private Object defValue;
    
    private int infoIndex;
    
    public WrapperShortcutInfo(String param1String, int param1Int, Object param1Object) {
      super(param1String);
      this.infoIndex = param1Int;
      this.defValue = param1Object;
    }
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      if (!getConfig().isAllowCreateShortcut())
        return this.defValue; 
      int i = this.infoIndex;
      Object object = param1VarArgs[i];
      if (object != null) {
        if (object instanceof ShortcutInfo) {
          ShortcutInfo shortcutInfo = (ShortcutInfo)object;
          param1VarArgs[i] = ShortcutServiceStub.wrapper((Context)VClient.get().getCurrentApplication(), shortcutInfo, getAppPkg(), getAppUserId());
        } else {
          ArrayList<ShortcutInfo> arrayList = new ArrayList();
          try {
            object = ParceledListSlice.getList.call(object, new Object[0]);
            if (object != null)
              for (i = object.size() - 1; i >= 0; i--) {
                ShortcutInfo shortcutInfo = (ShortcutInfo)object.get(i);
                if (shortcutInfo instanceof ShortcutInfo) {
                  shortcutInfo = shortcutInfo;
                  shortcutInfo = ShortcutServiceStub.unWrapper((Context)VClient.get().getCurrentApplication(), shortcutInfo, getAppPkg(), getAppUserId());
                  if (shortcutInfo != null)
                    arrayList.add(shortcutInfo); 
                } 
              }  
            return param1Method.invoke(param1Object, param1VarArgs);
          } finally {
            param1Object = null;
          } 
        } 
        return param1Method.invoke(param1Object, param1VarArgs);
      } 
      return this.defValue;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\shortcut\ShortcutServiceStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */