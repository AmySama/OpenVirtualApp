package com.swift.sandhook.xposedcompat.methodgen;

import de.robv.android.xposed.XposedBridge;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public interface HookMaker {
  Method getBackupMethod();
  
  Method getCallBackupMethod();
  
  Method getHookMethod();
  
  void start(Member paramMember, XposedBridge.AdditionalHookInfo paramAdditionalHookInfo, ClassLoader paramClassLoader, String paramString) throws Exception;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\swift\sandhook\xposedcompat\methodgen\HookMaker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */