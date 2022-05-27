package com.lody.virtual.helper.utils;

import android.os.IInterface;
import com.lody.virtual.client.core.VirtualCore;

public class IInterfaceUtils {
  public static boolean isAlive(IInterface paramIInterface) {
    return (paramIInterface == null) ? false : (VirtualCore.get().isMainProcess() ? paramIInterface.asBinder().pingBinder() : paramIInterface.asBinder().isBinderAlive());
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helpe\\utils\IInterfaceUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */