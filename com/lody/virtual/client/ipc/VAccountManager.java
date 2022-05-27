package com.lody.virtual.client.ipc;

import android.accounts.Account;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorDescription;
import android.accounts.IAccountManagerResponse;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import com.lody.virtual.client.env.VirtualRuntime;
import com.lody.virtual.client.stub.AmsTask;
import com.lody.virtual.os.VUserHandle;
import com.lody.virtual.server.interfaces.IAccountManager;
import java.util.Map;

public class VAccountManager {
  private static VAccountManager sMgr = new VAccountManager();
  
  private IAccountManager mService;
  
  public static VAccountManager get() {
    return sMgr;
  }
  
  private Object getStubInterface() {
    return IAccountManager.Stub.asInterface(ServiceManagerNative.getService("account"));
  }
  
  public boolean accountAuthenticated(Account paramAccount) {
    try {
      return getService().accountAuthenticated(VUserHandle.myUserId(), paramAccount);
    } catch (RemoteException remoteException) {
      return ((Boolean)VirtualRuntime.crash((Throwable)remoteException)).booleanValue();
    } 
  }
  
  public AccountManagerFuture<Bundle> addAccount(final int userId, final String accountType, final String authTokenType, final String[] requiredFeatures, Bundle paramBundle, final Activity activity, AccountManagerCallback<Bundle> paramAccountManagerCallback, Handler paramHandler) {
    if (accountType != null) {
      final Bundle optionsIn = new Bundle();
      if (paramBundle != null)
        bundle.putAll(paramBundle); 
      bundle.putString("androidPackageName", "android");
      return (new AmsTask(activity, paramHandler, paramAccountManagerCallback) {
          public void doWork() throws RemoteException {
            boolean bool;
            VAccountManager vAccountManager = VAccountManager.this;
            int i = userId;
            IAccountManagerResponse iAccountManagerResponse = this.mResponse;
            String str1 = accountType;
            String str2 = authTokenType;
            String[] arrayOfString = requiredFeatures;
            if (activity != null) {
              bool = true;
            } else {
              bool = false;
            } 
            vAccountManager.addAccount(i, iAccountManagerResponse, str1, str2, arrayOfString, bool, optionsIn);
          }
        }).start();
    } 
    throw new IllegalArgumentException("accountType is null");
  }
  
  public void addAccount(int paramInt, IAccountManagerResponse paramIAccountManagerResponse, String paramString1, String paramString2, String[] paramArrayOfString, boolean paramBoolean, Bundle paramBundle) {
    try {
      getService().addAccount(paramInt, paramIAccountManagerResponse, paramString1, paramString2, paramArrayOfString, paramBoolean, paramBundle);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public void addAccount(IAccountManagerResponse paramIAccountManagerResponse, String paramString1, String paramString2, String[] paramArrayOfString, boolean paramBoolean, Bundle paramBundle) {
    try {
      getService().addAccount(VUserHandle.myUserId(), paramIAccountManagerResponse, paramString1, paramString2, paramArrayOfString, paramBoolean, paramBundle);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public boolean addAccountExplicitly(Account paramAccount, String paramString, Bundle paramBundle) {
    try {
      return getService().addAccountExplicitly(VUserHandle.myUserId(), paramAccount, paramString, paramBundle);
    } catch (RemoteException remoteException) {
      return ((Boolean)VirtualRuntime.crash((Throwable)remoteException)).booleanValue();
    } 
  }
  
  public boolean addAccountExplicitlyWithVisibility(Account paramAccount, String paramString, Bundle paramBundle, Map paramMap) {
    try {
      return getService().addAccountExplicitlyWithVisibility(VUserHandle.myUserId(), paramAccount, paramString, paramBundle, paramMap);
    } catch (RemoteException remoteException) {
      return ((Boolean)VirtualRuntime.crash((Throwable)remoteException)).booleanValue();
    } 
  }
  
  public void clearPassword(Account paramAccount) {
    try {
      getService().clearPassword(VUserHandle.myUserId(), paramAccount);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public void confirmCredentials(IAccountManagerResponse paramIAccountManagerResponse, Account paramAccount, Bundle paramBundle, boolean paramBoolean) {
    try {
      getService().confirmCredentials(VUserHandle.myUserId(), paramIAccountManagerResponse, paramAccount, paramBundle, paramBoolean);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public void editProperties(IAccountManagerResponse paramIAccountManagerResponse, String paramString, boolean paramBoolean) {
    try {
      getService().editProperties(VUserHandle.myUserId(), paramIAccountManagerResponse, paramString, paramBoolean);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public void finishSessionAsUser(IAccountManagerResponse paramIAccountManagerResponse, Bundle paramBundle1, boolean paramBoolean, Bundle paramBundle2, int paramInt) {
    try {
      getService().finishSessionAsUser(paramIAccountManagerResponse, paramBundle1, paramBoolean, paramBundle2, paramInt);
    } catch (RemoteException remoteException) {
      VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public int getAccountVisibility(Account paramAccount, String paramString) {
    try {
      return getService().getAccountVisibility(VUserHandle.myUserId(), paramAccount, paramString);
    } catch (RemoteException remoteException) {
      return ((Integer)VirtualRuntime.crash((Throwable)remoteException)).intValue();
    } 
  }
  
  public Account[] getAccounts(int paramInt, String paramString) {
    try {
      return getService().getAccounts(paramInt, paramString);
    } catch (RemoteException remoteException) {
      return (Account[])VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public Account[] getAccounts(String paramString) {
    try {
      return getService().getAccounts(VUserHandle.myUserId(), paramString);
    } catch (RemoteException remoteException) {
      return (Account[])VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public Map getAccountsAndVisibilityForPackage(String paramString1, String paramString2) {
    try {
      return getService().getAccountsAndVisibilityForPackage(VUserHandle.myUserId(), paramString1, paramString2);
    } catch (RemoteException remoteException) {
      return (Map)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public void getAccountsByFeatures(IAccountManagerResponse paramIAccountManagerResponse, String paramString, String[] paramArrayOfString) {
    try {
      getService().getAccountsByFeatures(VUserHandle.myUserId(), paramIAccountManagerResponse, paramString, paramArrayOfString);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public void getAuthToken(IAccountManagerResponse paramIAccountManagerResponse, Account paramAccount, String paramString, boolean paramBoolean1, boolean paramBoolean2, Bundle paramBundle) {
    try {
      getService().getAuthToken(VUserHandle.myUserId(), paramIAccountManagerResponse, paramAccount, paramString, paramBoolean1, paramBoolean2, paramBundle);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public void getAuthTokenLabel(IAccountManagerResponse paramIAccountManagerResponse, String paramString1, String paramString2) {
    try {
      getService().getAuthTokenLabel(VUserHandle.myUserId(), paramIAccountManagerResponse, paramString1, paramString2);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public AuthenticatorDescription[] getAuthenticatorTypes(int paramInt) {
    try {
      return getService().getAuthenticatorTypes(paramInt);
    } catch (RemoteException remoteException) {
      return (AuthenticatorDescription[])VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public Map getPackagesAndVisibilityForAccount(Account paramAccount) {
    try {
      return getService().getPackagesAndVisibilityForAccount(VUserHandle.myUserId(), paramAccount);
    } catch (RemoteException remoteException) {
      return (Map)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public Object getPassword(Account paramAccount) {
    try {
      return getService().getPassword(VUserHandle.myUserId(), paramAccount);
    } catch (RemoteException remoteException) {
      return VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public String getPreviousName(Account paramAccount) {
    try {
      return getService().getPreviousName(VUserHandle.myUserId(), paramAccount);
    } catch (RemoteException remoteException) {
      return (String)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public IAccountManager getService() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mService : Lcom/lody/virtual/server/interfaces/IAccountManager;
    //   4: invokestatic isAlive : (Landroid/os/IInterface;)Z
    //   7: ifne -> 41
    //   10: ldc com/lody/virtual/client/ipc/VAccountManager
    //   12: monitorenter
    //   13: aload_0
    //   14: ldc com/lody/virtual/server/interfaces/IAccountManager
    //   16: aload_0
    //   17: invokespecial getStubInterface : ()Ljava/lang/Object;
    //   20: invokestatic genProxy : (Ljava/lang/Class;Ljava/lang/Object;)Ljava/lang/Object;
    //   23: checkcast com/lody/virtual/server/interfaces/IAccountManager
    //   26: putfield mService : Lcom/lody/virtual/server/interfaces/IAccountManager;
    //   29: ldc com/lody/virtual/client/ipc/VAccountManager
    //   31: monitorexit
    //   32: goto -> 41
    //   35: astore_1
    //   36: ldc com/lody/virtual/client/ipc/VAccountManager
    //   38: monitorexit
    //   39: aload_1
    //   40: athrow
    //   41: aload_0
    //   42: getfield mService : Lcom/lody/virtual/server/interfaces/IAccountManager;
    //   45: areturn
    // Exception table:
    //   from	to	target	type
    //   13	32	35	finally
    //   36	39	35	finally
  }
  
  public String getUserData(Account paramAccount, String paramString) {
    try {
      return getService().getUserData(VUserHandle.myUserId(), paramAccount, paramString);
    } catch (RemoteException remoteException) {
      return (String)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public void hasFeatures(IAccountManagerResponse paramIAccountManagerResponse, Account paramAccount, String[] paramArrayOfString) {
    try {
      getService().hasFeatures(VUserHandle.myUserId(), paramIAccountManagerResponse, paramAccount, paramArrayOfString);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public void invalidateAuthToken(String paramString1, String paramString2) {
    try {
      getService().invalidateAuthToken(VUserHandle.myUserId(), paramString1, paramString2);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public void isCredentialsUpdateSuggested(IAccountManagerResponse paramIAccountManagerResponse, Account paramAccount, String paramString) {
    try {
      getService().isCredentialsUpdateSuggested(paramIAccountManagerResponse, paramAccount, paramString);
    } catch (RemoteException remoteException) {
      VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public String peekAuthToken(Account paramAccount, String paramString) {
    try {
      return getService().peekAuthToken(VUserHandle.myUserId(), paramAccount, paramString);
    } catch (RemoteException remoteException) {
      return (String)VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public void registerAccountListener(String[] paramArrayOfString) {
    try {
      getService().registerAccountListener(paramArrayOfString);
    } catch (RemoteException remoteException) {
      VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public void removeAccount(IAccountManagerResponse paramIAccountManagerResponse, Account paramAccount, boolean paramBoolean) {
    try {
      getService().removeAccount(VUserHandle.myUserId(), paramIAccountManagerResponse, paramAccount, paramBoolean);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public boolean removeAccountExplicitly(Account paramAccount) {
    try {
      return getService().removeAccountExplicitly(VUserHandle.myUserId(), paramAccount);
    } catch (RemoteException remoteException) {
      return ((Boolean)VirtualRuntime.crash((Throwable)remoteException)).booleanValue();
    } 
  }
  
  public void renameAccount(IAccountManagerResponse paramIAccountManagerResponse, Account paramAccount, String paramString) {
    try {
      getService().renameAccount(VUserHandle.myUserId(), paramIAccountManagerResponse, paramAccount, paramString);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public boolean setAccountVisibility(Account paramAccount, String paramString, int paramInt) {
    try {
      return getService().setAccountVisibility(VUserHandle.myUserId(), paramAccount, paramString, paramInt);
    } catch (RemoteException remoteException) {
      return ((Boolean)VirtualRuntime.crash((Throwable)remoteException)).booleanValue();
    } 
  }
  
  public void setAuthToken(Account paramAccount, String paramString1, String paramString2) {
    try {
      getService().setAuthToken(VUserHandle.myUserId(), paramAccount, paramString1, paramString2);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public void setPassword(Account paramAccount, String paramString) {
    try {
      getService().setPassword(VUserHandle.myUserId(), paramAccount, paramString);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public void setUserData(Account paramAccount, String paramString1, String paramString2) {
    try {
      getService().setUserData(VUserHandle.myUserId(), paramAccount, paramString1, paramString2);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  public void startAddAccountSession(IAccountManagerResponse paramIAccountManagerResponse, String paramString1, String paramString2, String[] paramArrayOfString, boolean paramBoolean, Bundle paramBundle) {
    try {
      getService().startAddAccountSession(paramIAccountManagerResponse, paramString1, paramString2, paramArrayOfString, paramBoolean, paramBundle);
    } catch (RemoteException remoteException) {
      VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public void startUpdateCredentialsSession(IAccountManagerResponse paramIAccountManagerResponse, Account paramAccount, String paramString, boolean paramBoolean, Bundle paramBundle) {
    try {
      getService().startUpdateCredentialsSession(paramIAccountManagerResponse, paramAccount, paramString, paramBoolean, paramBundle);
    } catch (RemoteException remoteException) {
      VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public void unregisterAccountListener(String[] paramArrayOfString) {
    try {
      getService().unregisterAccountListener(paramArrayOfString);
    } catch (RemoteException remoteException) {
      VirtualRuntime.crash((Throwable)remoteException);
    } 
  }
  
  public void updateCredentials(IAccountManagerResponse paramIAccountManagerResponse, Account paramAccount, String paramString, boolean paramBoolean, Bundle paramBundle) {
    try {
      getService().updateCredentials(VUserHandle.myUserId(), paramIAccountManagerResponse, paramAccount, paramString, paramBoolean, paramBundle);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\ipc\VAccountManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */