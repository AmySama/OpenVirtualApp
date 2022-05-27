package io.virtualapp.Utils;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.lody.virtual.helper.utils.Reflect;
import mirror.android.os.ServiceManager;

public class IBinderTool {
  public static String tag = "IBinderTool";
  
  public static void printAllService() {
    for (String str1 : (String[])Reflect.on(ServiceManager.TYPE).call("listServices").get()) {
      String str2;
      IBinder iBinder = (IBinder)Reflect.on(ServiceManager.TYPE).call("getService", new Object[] { str1 }).get();
      if (iBinder == null) {
        str2 = tag;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("srv=");
        stringBuilder.append(str1);
        stringBuilder.append(" no find ");
        Log.w(str2, stringBuilder.toString());
      } else {
        try {
          String str = tag;
          StringBuilder stringBuilder = new StringBuilder();
          this();
          stringBuilder.append("srv=");
          stringBuilder.append(str1);
          stringBuilder.append("@");
          stringBuilder.append(str2.getInterfaceDescriptor());
          Log.i(str, stringBuilder.toString());
        } catch (RemoteException remoteException) {
          remoteException.printStackTrace();
        } 
      } 
    } 
  }
  
  public static void printIBinder(String paramString) {
    try {
    
    } finally {
      paramString = null;
    } 
  }
  
  public static void setTag(String paramString) {
    tag = paramString;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\Utils\IBinderTool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */