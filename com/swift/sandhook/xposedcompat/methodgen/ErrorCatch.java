package com.swift.sandhook.xposedcompat.methodgen;

import android.util.Log;
import com.swift.sandhook.SandHook;
import com.swift.sandhook.xposedcompat.XposedCompat;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public class ErrorCatch {
  public static Object callOriginError(Member paramMember, Method paramMethod, Object paramObject, Object[] paramArrayOfObject) throws Throwable {
    if (XposedCompat.retryWhenCallOriginError) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("method <");
      stringBuilder.append(paramMember.toString());
      stringBuilder.append("> use invoke to call origin!");
      Log.w("SandHook", stringBuilder.toString());
      return SandHook.callOriginMethod(paramMember, paramMethod, paramObject, paramArrayOfObject);
    } 
    return null;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\swift\sandhook\xposedcompat\methodgen\ErrorCatch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */