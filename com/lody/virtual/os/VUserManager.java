package com.lody.virtual.os;

import android.graphics.Bitmap;
import android.os.RemoteException;
import android.util.Log;
import com.lody.virtual.client.ipc.ServiceManagerNative;
import com.lody.virtual.server.interfaces.IUserManager;
import java.util.List;

public class VUserManager {
  private static String TAG = "VUserManager";
  
  private static final VUserManager sInstance = new VUserManager();
  
  private IUserManager mService;
  
  public static VUserManager get() {
    // Byte code:
    //   0: ldc com/lody/virtual/os/VUserManager
    //   2: monitorenter
    //   3: getstatic com/lody/virtual/os/VUserManager.sInstance : Lcom/lody/virtual/os/VUserManager;
    //   6: astore_0
    //   7: ldc com/lody/virtual/os/VUserManager
    //   9: monitorexit
    //   10: aload_0
    //   11: areturn
    //   12: astore_0
    //   13: ldc com/lody/virtual/os/VUserManager
    //   15: monitorexit
    //   16: aload_0
    //   17: athrow
    // Exception table:
    //   from	to	target	type
    //   3	7	12	finally
  }
  
  public static int getMaxSupportedUsers() {
    return Integer.MAX_VALUE;
  }
  
  private Object getRemoteInterface() {
    return IUserManager.Stub.asInterface(ServiceManagerNative.getService("user"));
  }
  
  public static boolean supportsMultipleUsers() {
    int i = getMaxSupportedUsers();
    boolean bool = true;
    if (i <= 1)
      bool = false; 
    return bool;
  }
  
  public VUserInfo createUser(String paramString, int paramInt) {
    try {
      return getService().createUser(paramString, paramInt);
    } catch (RemoteException remoteException) {
      Log.w(TAG, "Could not create a user", (Throwable)remoteException);
      return null;
    } 
  }
  
  public long getSerialNumberForUser(VUserHandle paramVUserHandle) {
    return getUserSerialNumber(paramVUserHandle.getIdentifier());
  }
  
  public IUserManager getService() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mService : Lcom/lody/virtual/server/interfaces/IUserManager;
    //   4: invokestatic isAlive : (Landroid/os/IInterface;)Z
    //   7: ifeq -> 19
    //   10: invokestatic get : ()Lcom/lody/virtual/client/core/VirtualCore;
    //   13: invokevirtual isExtHelperProcess : ()Z
    //   16: ifeq -> 41
    //   19: ldc com/lody/virtual/os/VUserManager
    //   21: monitorenter
    //   22: aload_0
    //   23: ldc com/lody/virtual/server/interfaces/IUserManager
    //   25: aload_0
    //   26: invokespecial getRemoteInterface : ()Ljava/lang/Object;
    //   29: invokestatic genProxy : (Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;
    //   32: checkcast com/lody/virtual/server/interfaces/IUserManager
    //   35: putfield mService : Lcom/lody/virtual/server/interfaces/IUserManager;
    //   38: ldc com/lody/virtual/os/VUserManager
    //   40: monitorexit
    //   41: aload_0
    //   42: getfield mService : Lcom/lody/virtual/server/interfaces/IUserManager;
    //   45: areturn
    //   46: astore_1
    //   47: ldc com/lody/virtual/os/VUserManager
    //   49: monitorexit
    //   50: aload_1
    //   51: athrow
    // Exception table:
    //   from	to	target	type
    //   22	41	46	finally
    //   47	50	46	finally
  }
  
  public int getUserCount() {
    boolean bool;
    List<VUserInfo> list = getUsers();
    if (list != null) {
      bool = list.size();
    } else {
      bool = true;
    } 
    return bool;
  }
  
  public VUserHandle getUserForSerialNumber(long paramLong) {
    VUserHandle vUserHandle;
    int i = getUserHandle((int)paramLong);
    if (i >= 0) {
      vUserHandle = new VUserHandle(i);
    } else {
      vUserHandle = null;
    } 
    return vUserHandle;
  }
  
  public int getUserHandle() {
    return VUserHandle.myUserId();
  }
  
  public int getUserHandle(int paramInt) {
    try {
      return getService().getUserHandle(paramInt);
    } catch (RemoteException remoteException) {
      String str = TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Could not get VUserHandle for user ");
      stringBuilder.append(paramInt);
      Log.w(str, stringBuilder.toString());
      return -1;
    } 
  }
  
  public Bitmap getUserIcon(int paramInt) {
    try {
      return getService().getUserIcon(paramInt);
    } catch (RemoteException remoteException) {
      Log.w(TAG, "Could not get the user icon ", (Throwable)remoteException);
      return null;
    } 
  }
  
  public VUserInfo getUserInfo(int paramInt) {
    try {
      return getService().getUserInfo(paramInt);
    } catch (RemoteException remoteException) {
      Log.w(TAG, "Could not get user info", (Throwable)remoteException);
      return null;
    } 
  }
  
  public String getUserName() {
    try {
      return (getService().getUserInfo(getUserHandle())).name;
    } catch (RemoteException remoteException) {
      Log.w(TAG, "Could not get user name", (Throwable)remoteException);
      return "";
    } 
  }
  
  public int getUserSerialNumber(int paramInt) {
    try {
      return getService().getUserSerialNumber(paramInt);
    } catch (RemoteException remoteException) {
      String str = TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Could not get serial number for user ");
      stringBuilder.append(paramInt);
      Log.w(str, stringBuilder.toString());
      return -1;
    } 
  }
  
  public List<VUserInfo> getUsers() {
    try {
      return getService().getUsers(false);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
      Log.w(TAG, "Could not get user list", (Throwable)remoteException);
      return null;
    } 
  }
  
  public List<VUserInfo> getUsers(boolean paramBoolean) {
    try {
      return getService().getUsers(paramBoolean);
    } catch (RemoteException remoteException) {
      Log.w(TAG, "Could not get user list", (Throwable)remoteException);
      return null;
    } 
  }
  
  public boolean isGuestEnabled() {
    try {
      return getService().isGuestEnabled();
    } catch (RemoteException remoteException) {
      Log.w(TAG, "Could not retrieve guest enabled state");
      return false;
    } 
  }
  
  public boolean isUserAGoat() {
    return false;
  }
  
  public boolean removeUser(int paramInt) {
    try {
      return getService().removeUser(paramInt);
    } catch (RemoteException remoteException) {
      Log.w(TAG, "Could not remove user ", (Throwable)remoteException);
      return false;
    } 
  }
  
  public void setGuestEnabled(boolean paramBoolean) {
    try {
      getService().setGuestEnabled(paramBoolean);
    } catch (RemoteException remoteException) {
      String str = TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Could not change guest account availability to ");
      stringBuilder.append(paramBoolean);
      Log.w(str, stringBuilder.toString());
    } 
  }
  
  public void setUserIcon(int paramInt, Bitmap paramBitmap) {
    try {
      getService().setUserIcon(paramInt, paramBitmap);
    } catch (RemoteException remoteException) {
      Log.w(TAG, "Could not set the user icon ", (Throwable)remoteException);
    } 
  }
  
  public void setUserName(int paramInt, String paramString) {
    try {
      getService().setUserName(paramInt, paramString);
    } catch (RemoteException remoteException) {
      Log.w(TAG, "Could not set the user name ", (Throwable)remoteException);
    } 
  }
  
  public void wipeUser(int paramInt) {
    try {
      getService().wipeUser(paramInt);
    } catch (RemoteException remoteException) {
      String str = TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Could not wipe user ");
      stringBuilder.append(paramInt);
      Log.w(str, stringBuilder.toString());
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\os\VUserManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */