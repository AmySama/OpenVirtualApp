package com.lody.virtual.client.ipc;

import android.os.Binder;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LocalProxyUtils {
  public static <T> T genProxy(Class<T> paramClass, Object paramObject) {
    return (T)paramObject;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\ipc\LocalProxyUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */