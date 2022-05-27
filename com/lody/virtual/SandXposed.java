package com.lody.virtual;

import android.content.Context;
import android.os.Build;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.helper.compat.BuildCompat;
import com.swift.sandhook.HookLog;
import com.swift.sandhook.SandHook;
import com.swift.sandhook.SandHookConfig;
import com.swift.sandhook.xposedcompat.XposedCompat;
import com.swift.sandhook.xposedcompat.utils.DexMakerUtils;
import java.io.File;
import java.lang.reflect.Member;
import mirror.dalvik.system.VMRuntime;

public class SandXposed {
  public static void init() {
    boolean bool;
    int i;
    try {
      if (VMRuntime.isJavaDebuggable == null) {
        bool = false;
      } else {
        bool = ((Boolean)VMRuntime.isJavaDebuggable.call(VMRuntime.getRuntime.call(new Object[0]), new Object[0])).booleanValue();
      } 
    } finally {
      Exception exception = null;
    } 
    HookLog.DEBUG = false;
    if (BuildCompat.isR()) {
      i = 30;
    } else {
      i = Build.VERSION.SDK_INT;
    } 
    SandHookConfig.SDK_INT = i;
    if (SandHookConfig.SDK_INT < 26) {
      bool = true;
    } else {
      bool = false;
    } 
    SandHookConfig.compiler = bool;
    SandHookConfig.delayHook = false;
    SandHook.setHookModeCallBack(new SandHook.HookModeCallBack() {
          public int hookMode(Member param1Member) {
            return (Build.VERSION.SDK_INT >= 30) ? 1 : 0;
          }
        });
    SandHook.disableVMInline();
    XposedCompat.cacheDir = new File(VirtualCore.get().getContext().getCacheDir(), "sandhook_cache_general");
  }
  
  public static void initForXposed(Context paramContext, String paramString) {
    XposedCompat.cacheDir = new File(paramContext.getCacheDir(), DexMakerUtils.MD5(paramString));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\SandXposed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */