package com.lody.virtual.server.interfaces;

import android.accounts.Account;
import android.accounts.AuthenticatorDescription;
import android.accounts.IAccountManagerResponse;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.Map;

public interface IAccountManager extends IInterface {
  boolean accountAuthenticated(int paramInt, Account paramAccount) throws RemoteException;
  
  void addAccount(int paramInt, IAccountManagerResponse paramIAccountManagerResponse, String paramString1, String paramString2, String[] paramArrayOfString, boolean paramBoolean, Bundle paramBundle) throws RemoteException;
  
  boolean addAccountExplicitly(int paramInt, Account paramAccount, String paramString, Bundle paramBundle) throws RemoteException;
  
  boolean addAccountExplicitlyWithVisibility(int paramInt, Account paramAccount, String paramString, Bundle paramBundle, Map paramMap) throws RemoteException;
  
  void clearPassword(int paramInt, Account paramAccount) throws RemoteException;
  
  void confirmCredentials(int paramInt, IAccountManagerResponse paramIAccountManagerResponse, Account paramAccount, Bundle paramBundle, boolean paramBoolean) throws RemoteException;
  
  void editProperties(int paramInt, IAccountManagerResponse paramIAccountManagerResponse, String paramString, boolean paramBoolean) throws RemoteException;
  
  void finishSessionAsUser(IAccountManagerResponse paramIAccountManagerResponse, Bundle paramBundle1, boolean paramBoolean, Bundle paramBundle2, int paramInt) throws RemoteException;
  
  int getAccountVisibility(int paramInt, Account paramAccount, String paramString) throws RemoteException;
  
  Account[] getAccounts(int paramInt, String paramString) throws RemoteException;
  
  Map getAccountsAndVisibilityForPackage(int paramInt, String paramString1, String paramString2) throws RemoteException;
  
  void getAccountsByFeatures(int paramInt, IAccountManagerResponse paramIAccountManagerResponse, String paramString, String[] paramArrayOfString) throws RemoteException;
  
  void getAuthToken(int paramInt, IAccountManagerResponse paramIAccountManagerResponse, Account paramAccount, String paramString, boolean paramBoolean1, boolean paramBoolean2, Bundle paramBundle) throws RemoteException;
  
  void getAuthTokenLabel(int paramInt, IAccountManagerResponse paramIAccountManagerResponse, String paramString1, String paramString2) throws RemoteException;
  
  AuthenticatorDescription[] getAuthenticatorTypes(int paramInt) throws RemoteException;
  
  Map getPackagesAndVisibilityForAccount(int paramInt, Account paramAccount) throws RemoteException;
  
  String getPassword(int paramInt, Account paramAccount) throws RemoteException;
  
  String getPreviousName(int paramInt, Account paramAccount) throws RemoteException;
  
  String getUserData(int paramInt, Account paramAccount, String paramString) throws RemoteException;
  
  void hasFeatures(int paramInt, IAccountManagerResponse paramIAccountManagerResponse, Account paramAccount, String[] paramArrayOfString) throws RemoteException;
  
  void invalidateAuthToken(int paramInt, String paramString1, String paramString2) throws RemoteException;
  
  void isCredentialsUpdateSuggested(IAccountManagerResponse paramIAccountManagerResponse, Account paramAccount, String paramString) throws RemoteException;
  
  String peekAuthToken(int paramInt, Account paramAccount, String paramString) throws RemoteException;
  
  void registerAccountListener(String[] paramArrayOfString) throws RemoteException;
  
  void removeAccount(int paramInt, IAccountManagerResponse paramIAccountManagerResponse, Account paramAccount, boolean paramBoolean) throws RemoteException;
  
  boolean removeAccountExplicitly(int paramInt, Account paramAccount) throws RemoteException;
  
  void renameAccount(int paramInt, IAccountManagerResponse paramIAccountManagerResponse, Account paramAccount, String paramString) throws RemoteException;
  
  boolean setAccountVisibility(int paramInt1, Account paramAccount, String paramString, int paramInt2) throws RemoteException;
  
  void setAuthToken(int paramInt, Account paramAccount, String paramString1, String paramString2) throws RemoteException;
  
  void setPassword(int paramInt, Account paramAccount, String paramString) throws RemoteException;
  
  void setUserData(int paramInt, Account paramAccount, String paramString1, String paramString2) throws RemoteException;
  
  void startAddAccountSession(IAccountManagerResponse paramIAccountManagerResponse, String paramString1, String paramString2, String[] paramArrayOfString, boolean paramBoolean, Bundle paramBundle) throws RemoteException;
  
  void startUpdateCredentialsSession(IAccountManagerResponse paramIAccountManagerResponse, Account paramAccount, String paramString, boolean paramBoolean, Bundle paramBundle) throws RemoteException;
  
  void unregisterAccountListener(String[] paramArrayOfString) throws RemoteException;
  
  void updateCredentials(int paramInt, IAccountManagerResponse paramIAccountManagerResponse, Account paramAccount, String paramString, boolean paramBoolean, Bundle paramBundle) throws RemoteException;
  
  public static class Default implements IAccountManager {
    public boolean accountAuthenticated(int param1Int, Account param1Account) throws RemoteException {
      return false;
    }
    
    public void addAccount(int param1Int, IAccountManagerResponse param1IAccountManagerResponse, String param1String1, String param1String2, String[] param1ArrayOfString, boolean param1Boolean, Bundle param1Bundle) throws RemoteException {}
    
    public boolean addAccountExplicitly(int param1Int, Account param1Account, String param1String, Bundle param1Bundle) throws RemoteException {
      return false;
    }
    
    public boolean addAccountExplicitlyWithVisibility(int param1Int, Account param1Account, String param1String, Bundle param1Bundle, Map param1Map) throws RemoteException {
      return false;
    }
    
    public IBinder asBinder() {
      return null;
    }
    
    public void clearPassword(int param1Int, Account param1Account) throws RemoteException {}
    
    public void confirmCredentials(int param1Int, IAccountManagerResponse param1IAccountManagerResponse, Account param1Account, Bundle param1Bundle, boolean param1Boolean) throws RemoteException {}
    
    public void editProperties(int param1Int, IAccountManagerResponse param1IAccountManagerResponse, String param1String, boolean param1Boolean) throws RemoteException {}
    
    public void finishSessionAsUser(IAccountManagerResponse param1IAccountManagerResponse, Bundle param1Bundle1, boolean param1Boolean, Bundle param1Bundle2, int param1Int) throws RemoteException {}
    
    public int getAccountVisibility(int param1Int, Account param1Account, String param1String) throws RemoteException {
      return 0;
    }
    
    public Account[] getAccounts(int param1Int, String param1String) throws RemoteException {
      return null;
    }
    
    public Map getAccountsAndVisibilityForPackage(int param1Int, String param1String1, String param1String2) throws RemoteException {
      return null;
    }
    
    public void getAccountsByFeatures(int param1Int, IAccountManagerResponse param1IAccountManagerResponse, String param1String, String[] param1ArrayOfString) throws RemoteException {}
    
    public void getAuthToken(int param1Int, IAccountManagerResponse param1IAccountManagerResponse, Account param1Account, String param1String, boolean param1Boolean1, boolean param1Boolean2, Bundle param1Bundle) throws RemoteException {}
    
    public void getAuthTokenLabel(int param1Int, IAccountManagerResponse param1IAccountManagerResponse, String param1String1, String param1String2) throws RemoteException {}
    
    public AuthenticatorDescription[] getAuthenticatorTypes(int param1Int) throws RemoteException {
      return null;
    }
    
    public Map getPackagesAndVisibilityForAccount(int param1Int, Account param1Account) throws RemoteException {
      return null;
    }
    
    public String getPassword(int param1Int, Account param1Account) throws RemoteException {
      return null;
    }
    
    public String getPreviousName(int param1Int, Account param1Account) throws RemoteException {
      return null;
    }
    
    public String getUserData(int param1Int, Account param1Account, String param1String) throws RemoteException {
      return null;
    }
    
    public void hasFeatures(int param1Int, IAccountManagerResponse param1IAccountManagerResponse, Account param1Account, String[] param1ArrayOfString) throws RemoteException {}
    
    public void invalidateAuthToken(int param1Int, String param1String1, String param1String2) throws RemoteException {}
    
    public void isCredentialsUpdateSuggested(IAccountManagerResponse param1IAccountManagerResponse, Account param1Account, String param1String) throws RemoteException {}
    
    public String peekAuthToken(int param1Int, Account param1Account, String param1String) throws RemoteException {
      return null;
    }
    
    public void registerAccountListener(String[] param1ArrayOfString) throws RemoteException {}
    
    public void removeAccount(int param1Int, IAccountManagerResponse param1IAccountManagerResponse, Account param1Account, boolean param1Boolean) throws RemoteException {}
    
    public boolean removeAccountExplicitly(int param1Int, Account param1Account) throws RemoteException {
      return false;
    }
    
    public void renameAccount(int param1Int, IAccountManagerResponse param1IAccountManagerResponse, Account param1Account, String param1String) throws RemoteException {}
    
    public boolean setAccountVisibility(int param1Int1, Account param1Account, String param1String, int param1Int2) throws RemoteException {
      return false;
    }
    
    public void setAuthToken(int param1Int, Account param1Account, String param1String1, String param1String2) throws RemoteException {}
    
    public void setPassword(int param1Int, Account param1Account, String param1String) throws RemoteException {}
    
    public void setUserData(int param1Int, Account param1Account, String param1String1, String param1String2) throws RemoteException {}
    
    public void startAddAccountSession(IAccountManagerResponse param1IAccountManagerResponse, String param1String1, String param1String2, String[] param1ArrayOfString, boolean param1Boolean, Bundle param1Bundle) throws RemoteException {}
    
    public void startUpdateCredentialsSession(IAccountManagerResponse param1IAccountManagerResponse, Account param1Account, String param1String, boolean param1Boolean, Bundle param1Bundle) throws RemoteException {}
    
    public void unregisterAccountListener(String[] param1ArrayOfString) throws RemoteException {}
    
    public void updateCredentials(int param1Int, IAccountManagerResponse param1IAccountManagerResponse, Account param1Account, String param1String, boolean param1Boolean, Bundle param1Bundle) throws RemoteException {}
  }
  
  public static abstract class Stub extends Binder implements IAccountManager {
    private static final String DESCRIPTOR = "com.lody.virtual.server.interfaces.IAccountManager";
    
    static final int TRANSACTION_accountAuthenticated = 22;
    
    static final int TRANSACTION_addAccount = 16;
    
    static final int TRANSACTION_addAccountExplicitly = 17;
    
    static final int TRANSACTION_addAccountExplicitlyWithVisibility = 35;
    
    static final int TRANSACTION_clearPassword = 21;
    
    static final int TRANSACTION_confirmCredentials = 15;
    
    static final int TRANSACTION_editProperties = 11;
    
    static final int TRANSACTION_finishSessionAsUser = 33;
    
    static final int TRANSACTION_getAccountVisibility = 26;
    
    static final int TRANSACTION_getAccounts = 4;
    
    static final int TRANSACTION_getAccountsAndVisibilityForPackage = 32;
    
    static final int TRANSACTION_getAccountsByFeatures = 2;
    
    static final int TRANSACTION_getAuthToken = 5;
    
    static final int TRANSACTION_getAuthTokenLabel = 12;
    
    static final int TRANSACTION_getAuthenticatorTypes = 1;
    
    static final int TRANSACTION_getPackagesAndVisibilityForAccount = 31;
    
    static final int TRANSACTION_getPassword = 14;
    
    static final int TRANSACTION_getPreviousName = 3;
    
    static final int TRANSACTION_getUserData = 13;
    
    static final int TRANSACTION_hasFeatures = 9;
    
    static final int TRANSACTION_invalidateAuthToken = 23;
    
    static final int TRANSACTION_isCredentialsUpdateSuggested = 34;
    
    static final int TRANSACTION_peekAuthToken = 24;
    
    static final int TRANSACTION_registerAccountListener = 29;
    
    static final int TRANSACTION_removeAccount = 20;
    
    static final int TRANSACTION_removeAccountExplicitly = 18;
    
    static final int TRANSACTION_renameAccount = 19;
    
    static final int TRANSACTION_setAccountVisibility = 25;
    
    static final int TRANSACTION_setAuthToken = 7;
    
    static final int TRANSACTION_setPassword = 6;
    
    static final int TRANSACTION_setUserData = 8;
    
    static final int TRANSACTION_startAddAccountSession = 27;
    
    static final int TRANSACTION_startUpdateCredentialsSession = 28;
    
    static final int TRANSACTION_unregisterAccountListener = 30;
    
    static final int TRANSACTION_updateCredentials = 10;
    
    public Stub() {
      attachInterface(this, "com.lody.virtual.server.interfaces.IAccountManager");
    }
    
    public static IAccountManager asInterface(IBinder param1IBinder) {
      if (param1IBinder == null)
        return null; 
      IInterface iInterface = param1IBinder.queryLocalInterface("com.lody.virtual.server.interfaces.IAccountManager");
      return (iInterface != null && iInterface instanceof IAccountManager) ? (IAccountManager)iInterface : new Proxy(param1IBinder);
    }
    
    public static IAccountManager getDefaultImpl() {
      return Proxy.sDefaultImpl;
    }
    
    public static boolean setDefaultImpl(IAccountManager param1IAccountManager) {
      if (Proxy.sDefaultImpl == null && param1IAccountManager != null) {
        Proxy.sDefaultImpl = param1IAccountManager;
        return true;
      } 
      return false;
    }
    
    public IBinder asBinder() {
      return (IBinder)this;
    }
    
    public boolean onTransact(int param1Int1, Parcel param1Parcel1, Parcel param1Parcel2, int param1Int2) throws RemoteException {
      if (param1Int1 != 1598968902) {
        boolean bool5;
        int n;
        boolean bool4;
        int m;
        boolean bool3;
        int k;
        boolean bool2;
        int j;
        boolean bool1;
        int i;
        Map map;
        String str2;
        Account[] arrayOfAccount;
        String str1;
        String str3;
        Account account3;
        IAccountManagerResponse iAccountManagerResponse2;
        Account account2;
        IAccountManagerResponse iAccountManagerResponse1;
        Account account1;
        String[] arrayOfString1;
        String str5;
        IAccountManagerResponse iAccountManagerResponse3;
        String str4;
        String str8;
        IAccountManagerResponse iAccountManagerResponse10;
        String str11;
        Bundle bundle3;
        IAccountManagerResponse iAccountManagerResponse9;
        String str10;
        IAccountManagerResponse iAccountManagerResponse8;
        Bundle bundle2;
        String[] arrayOfString3;
        Bundle bundle1;
        String str9;
        IAccountManagerResponse iAccountManagerResponse7;
        boolean bool6 = false;
        boolean bool7 = false;
        Account account4 = null;
        String str6 = null;
        Account account5 = null;
        String str7 = null;
        Account account6 = null;
        Account account7 = null;
        Account account8 = null;
        Account account9 = null;
        Account account10 = null;
        Account account11 = null;
        Account account12 = null;
        IAccountManagerResponse iAccountManagerResponse4 = null;
        String[] arrayOfString2 = null;
        IAccountManagerResponse iAccountManagerResponse5 = null;
        Account account13 = null;
        IAccountManagerResponse iAccountManagerResponse6 = null;
        Account account14 = null;
        Account account15 = null;
        Account account16 = null;
        Account account17 = null;
        Bundle bundle4 = null;
        switch (param1Int1) {
          default:
            return super.onTransact(param1Int1, param1Parcel1, param1Parcel2, param1Int2);
          case 35:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            param1Int1 = param1Parcel1.readInt();
            if (param1Parcel1.readInt() != 0) {
              account4 = (Account)Account.CREATOR.createFromParcel(param1Parcel1);
            } else {
              account4 = null;
            } 
            str6 = param1Parcel1.readString();
            if (param1Parcel1.readInt() != 0)
              bundle4 = (Bundle)Bundle.CREATOR.createFromParcel(param1Parcel1); 
            bool5 = addAccountExplicitlyWithVisibility(param1Int1, account4, str6, bundle4, param1Parcel1.readHashMap(getClass().getClassLoader()));
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool5);
            return true;
          case 34:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            iAccountManagerResponse10 = IAccountManagerResponse.Stub.asInterface(param1Parcel1.readStrongBinder());
            if (param1Parcel1.readInt() != 0)
              account4 = (Account)Account.CREATOR.createFromParcel(param1Parcel1); 
            isCredentialsUpdateSuggested(iAccountManagerResponse10, account4, param1Parcel1.readString());
            param1Parcel2.writeNoException();
            return true;
          case 33:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            iAccountManagerResponse4 = IAccountManagerResponse.Stub.asInterface(param1Parcel1.readStrongBinder());
            if (param1Parcel1.readInt() != 0) {
              Bundle bundle = (Bundle)Bundle.CREATOR.createFromParcel(param1Parcel1);
            } else {
              account4 = null;
            } 
            if (param1Parcel1.readInt() != 0) {
              bool7 = true;
            } else {
              bool7 = false;
            } 
            str11 = str6;
            if (param1Parcel1.readInt() != 0)
              bundle3 = (Bundle)Bundle.CREATOR.createFromParcel(param1Parcel1); 
            finishSessionAsUser(iAccountManagerResponse4, (Bundle)account4, bool7, bundle3, param1Parcel1.readInt());
            param1Parcel2.writeNoException();
            return true;
          case 32:
            param1Parcel1.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            map = getAccountsAndVisibilityForPackage(param1Parcel1.readInt(), param1Parcel1.readString(), param1Parcel1.readString());
            param1Parcel2.writeNoException();
            param1Parcel2.writeMap(map);
            return true;
          case 31:
            map.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            n = map.readInt();
            account4 = account5;
            if (map.readInt() != 0)
              account4 = (Account)Account.CREATOR.createFromParcel((Parcel)map); 
            map = getPackagesAndVisibilityForAccount(n, account4);
            param1Parcel2.writeNoException();
            param1Parcel2.writeMap(map);
            return true;
          case 30:
            map.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            unregisterAccountListener(map.createStringArray());
            param1Parcel2.writeNoException();
            return true;
          case 29:
            map.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            registerAccountListener(map.createStringArray());
            param1Parcel2.writeNoException();
            return true;
          case 28:
            map.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            iAccountManagerResponse9 = IAccountManagerResponse.Stub.asInterface(map.readStrongBinder());
            if (map.readInt() != 0) {
              account4 = (Account)Account.CREATOR.createFromParcel((Parcel)map);
            } else {
              account4 = null;
            } 
            str6 = map.readString();
            if (map.readInt() != 0) {
              bool7 = true;
            } else {
              bool7 = false;
            } 
            if (map.readInt() != 0) {
              Bundle bundle = (Bundle)Bundle.CREATOR.createFromParcel((Parcel)map);
            } else {
              map = null;
            } 
            startUpdateCredentialsSession(iAccountManagerResponse9, account4, str6, bool7, (Bundle)map);
            param1Parcel2.writeNoException();
            return true;
          case 27:
            map.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            iAccountManagerResponse4 = IAccountManagerResponse.Stub.asInterface(map.readStrongBinder());
            str3 = map.readString();
            str10 = map.readString();
            arrayOfString1 = map.createStringArray();
            if (map.readInt() != 0) {
              bool7 = true;
            } else {
              bool7 = false;
            } 
            if (map.readInt() != 0) {
              Bundle bundle = (Bundle)Bundle.CREATOR.createFromParcel((Parcel)map);
            } else {
              map = null;
            } 
            startAddAccountSession(iAccountManagerResponse4, str3, str10, arrayOfString1, bool7, (Bundle)map);
            param1Parcel2.writeNoException();
            return true;
          case 26:
            map.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            n = map.readInt();
            str3 = str7;
            if (map.readInt() != 0)
              account3 = (Account)Account.CREATOR.createFromParcel((Parcel)map); 
            n = getAccountVisibility(n, account3, map.readString());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(n);
            return true;
          case 25:
            map.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            n = map.readInt();
            account3 = account6;
            if (map.readInt() != 0)
              account3 = (Account)Account.CREATOR.createFromParcel((Parcel)map); 
            bool4 = setAccountVisibility(n, account3, map.readString(), map.readInt());
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool4);
            return true;
          case 24:
            map.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            m = map.readInt();
            account3 = account7;
            if (map.readInt() != 0)
              account3 = (Account)Account.CREATOR.createFromParcel((Parcel)map); 
            str2 = peekAuthToken(m, account3, map.readString());
            param1Parcel2.writeNoException();
            param1Parcel2.writeString(str2);
            return true;
          case 23:
            str2.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            invalidateAuthToken(str2.readInt(), str2.readString(), str2.readString());
            param1Parcel2.writeNoException();
            return true;
          case 22:
            str2.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            m = str2.readInt();
            account3 = account8;
            if (str2.readInt() != 0)
              account3 = (Account)Account.CREATOR.createFromParcel((Parcel)str2); 
            bool3 = accountAuthenticated(m, account3);
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool3);
            return true;
          case 21:
            str2.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            k = str2.readInt();
            account3 = account9;
            if (str2.readInt() != 0)
              account3 = (Account)Account.CREATOR.createFromParcel((Parcel)str2); 
            clearPassword(k, account3);
            param1Parcel2.writeNoException();
            return true;
          case 20:
            str2.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            k = str2.readInt();
            iAccountManagerResponse8 = IAccountManagerResponse.Stub.asInterface(str2.readStrongBinder());
            account3 = account10;
            if (str2.readInt() != 0)
              account3 = (Account)Account.CREATOR.createFromParcel((Parcel)str2); 
            if (str2.readInt() != 0)
              bool7 = true; 
            removeAccount(k, iAccountManagerResponse8, account3, bool7);
            param1Parcel2.writeNoException();
            return true;
          case 19:
            str2.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            k = str2.readInt();
            iAccountManagerResponse8 = IAccountManagerResponse.Stub.asInterface(str2.readStrongBinder());
            account3 = account11;
            if (str2.readInt() != 0)
              account3 = (Account)Account.CREATOR.createFromParcel((Parcel)str2); 
            renameAccount(k, iAccountManagerResponse8, account3, str2.readString());
            param1Parcel2.writeNoException();
            return true;
          case 18:
            str2.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            k = str2.readInt();
            account3 = account12;
            if (str2.readInt() != 0)
              account3 = (Account)Account.CREATOR.createFromParcel((Parcel)str2); 
            bool2 = removeAccountExplicitly(k, account3);
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool2);
            return true;
          case 17:
            str2.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            j = str2.readInt();
            if (str2.readInt() != 0) {
              account3 = (Account)Account.CREATOR.createFromParcel((Parcel)str2);
            } else {
              account3 = null;
            } 
            str5 = str2.readString();
            iAccountManagerResponse8 = iAccountManagerResponse4;
            if (str2.readInt() != 0)
              bundle2 = (Bundle)Bundle.CREATOR.createFromParcel((Parcel)str2); 
            bool1 = addAccountExplicitly(j, account3, str5, bundle2);
            param1Parcel2.writeNoException();
            param1Parcel2.writeInt(bool1);
            return true;
          case 16:
            str2.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            i = str2.readInt();
            iAccountManagerResponse2 = IAccountManagerResponse.Stub.asInterface(str2.readStrongBinder());
            str5 = str2.readString();
            str8 = str2.readString();
            arrayOfString3 = str2.createStringArray();
            if (str2.readInt() != 0) {
              bool7 = true;
            } else {
              bool7 = false;
            } 
            if (str2.readInt() != 0) {
              Bundle bundle = (Bundle)Bundle.CREATOR.createFromParcel((Parcel)str2);
            } else {
              str2 = null;
            } 
            addAccount(i, iAccountManagerResponse2, str5, str8, arrayOfString3, bool7, (Bundle)str2);
            param1Parcel2.writeNoException();
            return true;
          case 15:
            str2.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            i = str2.readInt();
            iAccountManagerResponse3 = IAccountManagerResponse.Stub.asInterface(str2.readStrongBinder());
            if (str2.readInt() != 0) {
              account2 = (Account)Account.CREATOR.createFromParcel((Parcel)str2);
            } else {
              iAccountManagerResponse2 = null;
            } 
            arrayOfString3 = arrayOfString2;
            if (str2.readInt() != 0)
              bundle1 = (Bundle)Bundle.CREATOR.createFromParcel((Parcel)str2); 
            if (str2.readInt() != 0) {
              bool7 = true;
            } else {
              bool7 = false;
            } 
            confirmCredentials(i, iAccountManagerResponse3, (Account)iAccountManagerResponse2, bundle1, bool7);
            param1Parcel2.writeNoException();
            return true;
          case 14:
            str2.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            i = str2.readInt();
            iAccountManagerResponse2 = iAccountManagerResponse5;
            if (str2.readInt() != 0)
              account2 = (Account)Account.CREATOR.createFromParcel((Parcel)str2); 
            str2 = getPassword(i, account2);
            param1Parcel2.writeNoException();
            param1Parcel2.writeString(str2);
            return true;
          case 13:
            str2.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            i = str2.readInt();
            account2 = account13;
            if (str2.readInt() != 0)
              account2 = (Account)Account.CREATOR.createFromParcel((Parcel)str2); 
            str2 = getUserData(i, account2, str2.readString());
            param1Parcel2.writeNoException();
            param1Parcel2.writeString(str2);
            return true;
          case 12:
            str2.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            getAuthTokenLabel(str2.readInt(), IAccountManagerResponse.Stub.asInterface(str2.readStrongBinder()), str2.readString(), str2.readString());
            param1Parcel2.writeNoException();
            return true;
          case 11:
            str2.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            i = str2.readInt();
            iAccountManagerResponse1 = IAccountManagerResponse.Stub.asInterface(str2.readStrongBinder());
            str9 = str2.readString();
            bool7 = bool6;
            if (str2.readInt() != 0)
              bool7 = true; 
            editProperties(i, iAccountManagerResponse1, str9, bool7);
            param1Parcel2.writeNoException();
            return true;
          case 10:
            str2.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            i = str2.readInt();
            iAccountManagerResponse7 = IAccountManagerResponse.Stub.asInterface(str2.readStrongBinder());
            if (str2.readInt() != 0) {
              account1 = (Account)Account.CREATOR.createFromParcel((Parcel)str2);
            } else {
              iAccountManagerResponse1 = null;
            } 
            str4 = str2.readString();
            if (str2.readInt() != 0) {
              bool7 = true;
            } else {
              bool7 = false;
            } 
            if (str2.readInt() != 0) {
              Bundle bundle = (Bundle)Bundle.CREATOR.createFromParcel((Parcel)str2);
            } else {
              str2 = null;
            } 
            updateCredentials(i, iAccountManagerResponse7, (Account)iAccountManagerResponse1, str4, bool7, (Bundle)str2);
            param1Parcel2.writeNoException();
            return true;
          case 9:
            str2.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            i = str2.readInt();
            iAccountManagerResponse7 = IAccountManagerResponse.Stub.asInterface(str2.readStrongBinder());
            iAccountManagerResponse1 = iAccountManagerResponse6;
            if (str2.readInt() != 0)
              account1 = (Account)Account.CREATOR.createFromParcel((Parcel)str2); 
            hasFeatures(i, iAccountManagerResponse7, account1, str2.createStringArray());
            param1Parcel2.writeNoException();
            return true;
          case 8:
            str2.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            i = str2.readInt();
            account1 = account14;
            if (str2.readInt() != 0)
              account1 = (Account)Account.CREATOR.createFromParcel((Parcel)str2); 
            setUserData(i, account1, str2.readString(), str2.readString());
            param1Parcel2.writeNoException();
            return true;
          case 7:
            str2.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            i = str2.readInt();
            account1 = account15;
            if (str2.readInt() != 0)
              account1 = (Account)Account.CREATOR.createFromParcel((Parcel)str2); 
            setAuthToken(i, account1, str2.readString(), str2.readString());
            param1Parcel2.writeNoException();
            return true;
          case 6:
            str2.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            i = str2.readInt();
            account1 = account16;
            if (str2.readInt() != 0)
              account1 = (Account)Account.CREATOR.createFromParcel((Parcel)str2); 
            setPassword(i, account1, str2.readString());
            param1Parcel2.writeNoException();
            return true;
          case 5:
            str2.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            i = str2.readInt();
            iAccountManagerResponse7 = IAccountManagerResponse.Stub.asInterface(str2.readStrongBinder());
            if (str2.readInt() != 0) {
              account1 = (Account)Account.CREATOR.createFromParcel((Parcel)str2);
            } else {
              account1 = null;
            } 
            str4 = str2.readString();
            if (str2.readInt() != 0) {
              bool7 = true;
            } else {
              bool7 = false;
            } 
            if (str2.readInt() != 0) {
              bool6 = true;
            } else {
              bool6 = false;
            } 
            if (str2.readInt() != 0) {
              Bundle bundle = (Bundle)Bundle.CREATOR.createFromParcel((Parcel)str2);
            } else {
              str2 = null;
            } 
            getAuthToken(i, iAccountManagerResponse7, account1, str4, bool7, bool6, (Bundle)str2);
            param1Parcel2.writeNoException();
            return true;
          case 4:
            str2.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            arrayOfAccount = getAccounts(str2.readInt(), str2.readString());
            param1Parcel2.writeNoException();
            param1Parcel2.writeTypedArray((Parcelable[])arrayOfAccount, 1);
            return true;
          case 3:
            arrayOfAccount.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            i = arrayOfAccount.readInt();
            account1 = account17;
            if (arrayOfAccount.readInt() != 0)
              account1 = (Account)Account.CREATOR.createFromParcel((Parcel)arrayOfAccount); 
            str1 = getPreviousName(i, account1);
            param1Parcel2.writeNoException();
            param1Parcel2.writeString(str1);
            return true;
          case 2:
            str1.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
            getAccountsByFeatures(str1.readInt(), IAccountManagerResponse.Stub.asInterface(str1.readStrongBinder()), str1.readString(), str1.createStringArray());
            param1Parcel2.writeNoException();
            return true;
          case 1:
            break;
        } 
        str1.enforceInterface("com.lody.virtual.server.interfaces.IAccountManager");
        AuthenticatorDescription[] arrayOfAuthenticatorDescription = getAuthenticatorTypes(str1.readInt());
        param1Parcel2.writeNoException();
        param1Parcel2.writeTypedArray((Parcelable[])arrayOfAuthenticatorDescription, 1);
        return true;
      } 
      param1Parcel2.writeString("com.lody.virtual.server.interfaces.IAccountManager");
      return true;
    }
    
    private static class Proxy implements IAccountManager {
      public static IAccountManager sDefaultImpl;
      
      private IBinder mRemote;
      
      Proxy(IBinder param2IBinder) {
        this.mRemote = param2IBinder;
      }
      
      public boolean accountAuthenticated(int param2Int, Account param2Account) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeInt(param2Int);
          boolean bool = true;
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(22, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
            bool = IAccountManager.Stub.getDefaultImpl().accountAuthenticated(param2Int, param2Account);
            return bool;
          } 
          parcel2.readException();
          param2Int = parcel2.readInt();
          if (param2Int == 0)
            bool = false; 
          return bool;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void addAccount(int param2Int, IAccountManagerResponse param2IAccountManagerResponse, String param2String1, String param2String2, String[] param2ArrayOfString, boolean param2Boolean, Bundle param2Bundle) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          boolean bool;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeInt(param2Int);
          if (param2IAccountManagerResponse != null) {
            iBinder = param2IAccountManagerResponse.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          parcel1.writeString(param2String1);
          parcel1.writeString(param2String2);
          parcel1.writeStringArray(param2ArrayOfString);
          if (param2Boolean) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          if (param2Bundle != null) {
            parcel1.writeInt(1);
            param2Bundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          try {
            if (!this.mRemote.transact(16, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
              IAccountManager.Stub.getDefaultImpl().addAccount(param2Int, param2IAccountManagerResponse, param2String1, param2String2, param2ArrayOfString, param2Boolean, param2Bundle);
              parcel2.recycle();
              parcel1.recycle();
              return;
            } 
            parcel2.readException();
            parcel2.recycle();
            parcel1.recycle();
            return;
          } finally {}
        } finally {}
        parcel2.recycle();
        parcel1.recycle();
        throw param2IAccountManagerResponse;
      }
      
      public boolean addAccountExplicitly(int param2Int, Account param2Account, String param2String, Bundle param2Bundle) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeInt(param2Int);
          boolean bool = true;
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          if (param2Bundle != null) {
            parcel1.writeInt(1);
            param2Bundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(17, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
            bool = IAccountManager.Stub.getDefaultImpl().addAccountExplicitly(param2Int, param2Account, param2String, param2Bundle);
            return bool;
          } 
          parcel2.readException();
          param2Int = parcel2.readInt();
          if (param2Int == 0)
            bool = false; 
          return bool;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public boolean addAccountExplicitlyWithVisibility(int param2Int, Account param2Account, String param2String, Bundle param2Bundle, Map param2Map) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeInt(param2Int);
          boolean bool = true;
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          if (param2Bundle != null) {
            parcel1.writeInt(1);
            param2Bundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeMap(param2Map);
          try {
            if (!this.mRemote.transact(35, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
              bool = IAccountManager.Stub.getDefaultImpl().addAccountExplicitlyWithVisibility(param2Int, param2Account, param2String, param2Bundle, param2Map);
              parcel2.recycle();
              parcel1.recycle();
              return bool;
            } 
            parcel2.readException();
            param2Int = parcel2.readInt();
            if (param2Int == 0)
              bool = false; 
            parcel2.recycle();
            parcel1.recycle();
            return bool;
          } finally {}
        } finally {}
        parcel2.recycle();
        parcel1.recycle();
        throw param2Account;
      }
      
      public IBinder asBinder() {
        return this.mRemote;
      }
      
      public void clearPassword(int param2Int, Account param2Account) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeInt(param2Int);
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(21, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
            IAccountManager.Stub.getDefaultImpl().clearPassword(param2Int, param2Account);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void confirmCredentials(int param2Int, IAccountManagerResponse param2IAccountManagerResponse, Account param2Account, Bundle param2Bundle, boolean param2Boolean) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeInt(param2Int);
          if (param2IAccountManagerResponse != null) {
            iBinder = param2IAccountManagerResponse.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          boolean bool = true;
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (param2Bundle != null) {
            parcel1.writeInt(1);
            param2Bundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!param2Boolean)
            bool = false; 
          parcel1.writeInt(bool);
          if (!this.mRemote.transact(15, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
            IAccountManager.Stub.getDefaultImpl().confirmCredentials(param2Int, param2IAccountManagerResponse, param2Account, param2Bundle, param2Boolean);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void editProperties(int param2Int, IAccountManagerResponse param2IAccountManagerResponse, String param2String, boolean param2Boolean) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          boolean bool;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeInt(param2Int);
          if (param2IAccountManagerResponse != null) {
            iBinder = param2IAccountManagerResponse.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          parcel1.writeString(param2String);
          if (param2Boolean) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          if (!this.mRemote.transact(11, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
            IAccountManager.Stub.getDefaultImpl().editProperties(param2Int, param2IAccountManagerResponse, param2String, param2Boolean);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void finishSessionAsUser(IAccountManagerResponse param2IAccountManagerResponse, Bundle param2Bundle1, boolean param2Boolean, Bundle param2Bundle2, int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          boolean bool;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          if (param2IAccountManagerResponse != null) {
            iBinder = param2IAccountManagerResponse.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          if (param2Bundle1 != null) {
            parcel1.writeInt(1);
            param2Bundle1.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (param2Boolean) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          if (param2Bundle2 != null) {
            parcel1.writeInt(1);
            param2Bundle2.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(33, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
            IAccountManager.Stub.getDefaultImpl().finishSessionAsUser(param2IAccountManagerResponse, param2Bundle1, param2Boolean, param2Bundle2, param2Int);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public int getAccountVisibility(int param2Int, Account param2Account, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeInt(param2Int);
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(26, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
            param2Int = IAccountManager.Stub.getDefaultImpl().getAccountVisibility(param2Int, param2Account, param2String);
            return param2Int;
          } 
          parcel2.readException();
          param2Int = parcel2.readInt();
          return param2Int;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public Account[] getAccounts(int param2Int, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeInt(param2Int);
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null)
            return IAccountManager.Stub.getDefaultImpl().getAccounts(param2Int, param2String); 
          parcel2.readException();
          return (Account[])parcel2.createTypedArray(Account.CREATOR);
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public Map getAccountsAndVisibilityForPackage(int param2Int, String param2String1, String param2String2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeInt(param2Int);
          parcel1.writeString(param2String1);
          parcel1.writeString(param2String2);
          if (!this.mRemote.transact(32, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null)
            return IAccountManager.Stub.getDefaultImpl().getAccountsAndVisibilityForPackage(param2Int, param2String1, param2String2); 
          parcel2.readException();
          return parcel2.readHashMap(getClass().getClassLoader());
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void getAccountsByFeatures(int param2Int, IAccountManagerResponse param2IAccountManagerResponse, String param2String, String[] param2ArrayOfString) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeInt(param2Int);
          if (param2IAccountManagerResponse != null) {
            iBinder = param2IAccountManagerResponse.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          parcel1.writeString(param2String);
          parcel1.writeStringArray(param2ArrayOfString);
          if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
            IAccountManager.Stub.getDefaultImpl().getAccountsByFeatures(param2Int, param2IAccountManagerResponse, param2String, param2ArrayOfString);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void getAuthToken(int param2Int, IAccountManagerResponse param2IAccountManagerResponse, Account param2Account, String param2String, boolean param2Boolean1, boolean param2Boolean2, Bundle param2Bundle) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          boolean bool;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeInt(param2Int);
          if (param2IAccountManagerResponse != null) {
            iBinder = param2IAccountManagerResponse.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          if (param2Boolean1) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          if (param2Boolean2) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          if (param2Bundle != null) {
            parcel1.writeInt(1);
            param2Bundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          try {
            if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
              IAccountManager.Stub.getDefaultImpl().getAuthToken(param2Int, param2IAccountManagerResponse, param2Account, param2String, param2Boolean1, param2Boolean2, param2Bundle);
              parcel2.recycle();
              parcel1.recycle();
              return;
            } 
            parcel2.readException();
            parcel2.recycle();
            parcel1.recycle();
            return;
          } finally {}
        } finally {}
        parcel2.recycle();
        parcel1.recycle();
        throw param2IAccountManagerResponse;
      }
      
      public void getAuthTokenLabel(int param2Int, IAccountManagerResponse param2IAccountManagerResponse, String param2String1, String param2String2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeInt(param2Int);
          if (param2IAccountManagerResponse != null) {
            iBinder = param2IAccountManagerResponse.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          parcel1.writeString(param2String1);
          parcel1.writeString(param2String2);
          if (!this.mRemote.transact(12, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
            IAccountManager.Stub.getDefaultImpl().getAuthTokenLabel(param2Int, param2IAccountManagerResponse, param2String1, param2String2);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public AuthenticatorDescription[] getAuthenticatorTypes(int param2Int) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeInt(param2Int);
          if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null)
            return IAccountManager.Stub.getDefaultImpl().getAuthenticatorTypes(param2Int); 
          parcel2.readException();
          return (AuthenticatorDescription[])parcel2.createTypedArray(AuthenticatorDescription.CREATOR);
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String getInterfaceDescriptor() {
        return "com.lody.virtual.server.interfaces.IAccountManager";
      }
      
      public Map getPackagesAndVisibilityForAccount(int param2Int, Account param2Account) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeInt(param2Int);
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(31, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null)
            return IAccountManager.Stub.getDefaultImpl().getPackagesAndVisibilityForAccount(param2Int, param2Account); 
          parcel2.readException();
          return parcel2.readHashMap(getClass().getClassLoader());
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String getPassword(int param2Int, Account param2Account) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeInt(param2Int);
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(14, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null)
            return IAccountManager.Stub.getDefaultImpl().getPassword(param2Int, param2Account); 
          parcel2.readException();
          return parcel2.readString();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String getPreviousName(int param2Int, Account param2Account) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeInt(param2Int);
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null)
            return IAccountManager.Stub.getDefaultImpl().getPreviousName(param2Int, param2Account); 
          parcel2.readException();
          return parcel2.readString();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String getUserData(int param2Int, Account param2Account, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeInt(param2Int);
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(13, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null)
            return IAccountManager.Stub.getDefaultImpl().getUserData(param2Int, param2Account, param2String); 
          parcel2.readException();
          return parcel2.readString();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void hasFeatures(int param2Int, IAccountManagerResponse param2IAccountManagerResponse, Account param2Account, String[] param2ArrayOfString) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeInt(param2Int);
          if (param2IAccountManagerResponse != null) {
            iBinder = param2IAccountManagerResponse.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeStringArray(param2ArrayOfString);
          if (!this.mRemote.transact(9, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
            IAccountManager.Stub.getDefaultImpl().hasFeatures(param2Int, param2IAccountManagerResponse, param2Account, param2ArrayOfString);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void invalidateAuthToken(int param2Int, String param2String1, String param2String2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeInt(param2Int);
          parcel1.writeString(param2String1);
          parcel1.writeString(param2String2);
          if (!this.mRemote.transact(23, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
            IAccountManager.Stub.getDefaultImpl().invalidateAuthToken(param2Int, param2String1, param2String2);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void isCredentialsUpdateSuggested(IAccountManagerResponse param2IAccountManagerResponse, Account param2Account, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          if (param2IAccountManagerResponse != null) {
            iBinder = param2IAccountManagerResponse.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(34, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
            IAccountManager.Stub.getDefaultImpl().isCredentialsUpdateSuggested(param2IAccountManagerResponse, param2Account, param2String);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public String peekAuthToken(int param2Int, Account param2Account, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeInt(param2Int);
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(24, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null)
            return IAccountManager.Stub.getDefaultImpl().peekAuthToken(param2Int, param2Account, param2String); 
          parcel2.readException();
          return parcel2.readString();
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void registerAccountListener(String[] param2ArrayOfString) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeStringArray(param2ArrayOfString);
          if (!this.mRemote.transact(29, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
            IAccountManager.Stub.getDefaultImpl().registerAccountListener(param2ArrayOfString);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void removeAccount(int param2Int, IAccountManagerResponse param2IAccountManagerResponse, Account param2Account, boolean param2Boolean) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeInt(param2Int);
          if (param2IAccountManagerResponse != null) {
            iBinder = param2IAccountManagerResponse.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          boolean bool = true;
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!param2Boolean)
            bool = false; 
          parcel1.writeInt(bool);
          if (!this.mRemote.transact(20, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
            IAccountManager.Stub.getDefaultImpl().removeAccount(param2Int, param2IAccountManagerResponse, param2Account, param2Boolean);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public boolean removeAccountExplicitly(int param2Int, Account param2Account) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeInt(param2Int);
          boolean bool = true;
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(18, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
            bool = IAccountManager.Stub.getDefaultImpl().removeAccountExplicitly(param2Int, param2Account);
            return bool;
          } 
          parcel2.readException();
          param2Int = parcel2.readInt();
          if (param2Int == 0)
            bool = false; 
          return bool;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void renameAccount(int param2Int, IAccountManagerResponse param2IAccountManagerResponse, Account param2Account, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeInt(param2Int);
          if (param2IAccountManagerResponse != null) {
            iBinder = param2IAccountManagerResponse.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(19, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
            IAccountManager.Stub.getDefaultImpl().renameAccount(param2Int, param2IAccountManagerResponse, param2Account, param2String);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public boolean setAccountVisibility(int param2Int1, Account param2Account, String param2String, int param2Int2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeInt(param2Int1);
          boolean bool = true;
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          parcel1.writeInt(param2Int2);
          if (!this.mRemote.transact(25, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
            bool = IAccountManager.Stub.getDefaultImpl().setAccountVisibility(param2Int1, param2Account, param2String, param2Int2);
            return bool;
          } 
          parcel2.readException();
          param2Int1 = parcel2.readInt();
          if (param2Int1 == 0)
            bool = false; 
          return bool;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setAuthToken(int param2Int, Account param2Account, String param2String1, String param2String2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeInt(param2Int);
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String1);
          parcel1.writeString(param2String2);
          if (!this.mRemote.transact(7, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
            IAccountManager.Stub.getDefaultImpl().setAuthToken(param2Int, param2Account, param2String1, param2String2);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setPassword(int param2Int, Account param2Account, String param2String) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeInt(param2Int);
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          if (!this.mRemote.transact(6, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
            IAccountManager.Stub.getDefaultImpl().setPassword(param2Int, param2Account, param2String);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void setUserData(int param2Int, Account param2Account, String param2String1, String param2String2) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeInt(param2Int);
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String1);
          parcel1.writeString(param2String2);
          if (!this.mRemote.transact(8, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
            IAccountManager.Stub.getDefaultImpl().setUserData(param2Int, param2Account, param2String1, param2String2);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void startAddAccountSession(IAccountManagerResponse param2IAccountManagerResponse, String param2String1, String param2String2, String[] param2ArrayOfString, boolean param2Boolean, Bundle param2Bundle) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          boolean bool;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          if (param2IAccountManagerResponse != null) {
            iBinder = param2IAccountManagerResponse.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          parcel1.writeString(param2String1);
          parcel1.writeString(param2String2);
          parcel1.writeStringArray(param2ArrayOfString);
          if (param2Boolean) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          if (param2Bundle != null) {
            parcel1.writeInt(1);
            param2Bundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          try {
            if (!this.mRemote.transact(27, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
              IAccountManager.Stub.getDefaultImpl().startAddAccountSession(param2IAccountManagerResponse, param2String1, param2String2, param2ArrayOfString, param2Boolean, param2Bundle);
              parcel2.recycle();
              parcel1.recycle();
              return;
            } 
            parcel2.readException();
            parcel2.recycle();
            parcel1.recycle();
            return;
          } finally {}
        } finally {}
        parcel2.recycle();
        parcel1.recycle();
        throw param2IAccountManagerResponse;
      }
      
      public void startUpdateCredentialsSession(IAccountManagerResponse param2IAccountManagerResponse, Account param2Account, String param2String, boolean param2Boolean, Bundle param2Bundle) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          boolean bool;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          if (param2IAccountManagerResponse != null) {
            iBinder = param2IAccountManagerResponse.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          if (param2Boolean) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          if (param2Bundle != null) {
            parcel1.writeInt(1);
            param2Bundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          if (!this.mRemote.transact(28, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
            IAccountManager.Stub.getDefaultImpl().startUpdateCredentialsSession(param2IAccountManagerResponse, param2Account, param2String, param2Boolean, param2Bundle);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void unregisterAccountListener(String[] param2ArrayOfString) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeStringArray(param2ArrayOfString);
          if (!this.mRemote.transact(30, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
            IAccountManager.Stub.getDefaultImpl().unregisterAccountListener(param2ArrayOfString);
            return;
          } 
          parcel2.readException();
          return;
        } finally {
          parcel2.recycle();
          parcel1.recycle();
        } 
      }
      
      public void updateCredentials(int param2Int, IAccountManagerResponse param2IAccountManagerResponse, Account param2Account, String param2String, boolean param2Boolean, Bundle param2Bundle) throws RemoteException {
        Parcel parcel1 = Parcel.obtain();
        Parcel parcel2 = Parcel.obtain();
        try {
          IBinder iBinder;
          boolean bool;
          parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
          parcel1.writeInt(param2Int);
          if (param2IAccountManagerResponse != null) {
            iBinder = param2IAccountManagerResponse.asBinder();
          } else {
            iBinder = null;
          } 
          parcel1.writeStrongBinder(iBinder);
          if (param2Account != null) {
            parcel1.writeInt(1);
            param2Account.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          parcel1.writeString(param2String);
          if (param2Boolean) {
            bool = true;
          } else {
            bool = false;
          } 
          parcel1.writeInt(bool);
          if (param2Bundle != null) {
            parcel1.writeInt(1);
            param2Bundle.writeToParcel(parcel1, 0);
          } else {
            parcel1.writeInt(0);
          } 
          try {
            if (!this.mRemote.transact(10, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
              IAccountManager.Stub.getDefaultImpl().updateCredentials(param2Int, param2IAccountManagerResponse, param2Account, param2String, param2Boolean, param2Bundle);
              parcel2.recycle();
              parcel1.recycle();
              return;
            } 
            parcel2.readException();
            parcel2.recycle();
            parcel1.recycle();
            return;
          } finally {}
        } finally {}
        parcel2.recycle();
        parcel1.recycle();
        throw param2IAccountManagerResponse;
      }
    }
  }
  
  private static class Proxy implements IAccountManager {
    public static IAccountManager sDefaultImpl;
    
    private IBinder mRemote;
    
    Proxy(IBinder param1IBinder) {
      this.mRemote = param1IBinder;
    }
    
    public boolean accountAuthenticated(int param1Int, Account param1Account) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeInt(param1Int);
        boolean bool = true;
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(22, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
          bool = IAccountManager.Stub.getDefaultImpl().accountAuthenticated(param1Int, param1Account);
          return bool;
        } 
        parcel2.readException();
        param1Int = parcel2.readInt();
        if (param1Int == 0)
          bool = false; 
        return bool;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void addAccount(int param1Int, IAccountManagerResponse param1IAccountManagerResponse, String param1String1, String param1String2, String[] param1ArrayOfString, boolean param1Boolean, Bundle param1Bundle) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        boolean bool;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeInt(param1Int);
        if (param1IAccountManagerResponse != null) {
          iBinder = param1IAccountManagerResponse.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        parcel1.writeString(param1String1);
        parcel1.writeString(param1String2);
        parcel1.writeStringArray(param1ArrayOfString);
        if (param1Boolean) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        if (param1Bundle != null) {
          parcel1.writeInt(1);
          param1Bundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        try {
          if (!this.mRemote.transact(16, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
            IAccountManager.Stub.getDefaultImpl().addAccount(param1Int, param1IAccountManagerResponse, param1String1, param1String2, param1ArrayOfString, param1Boolean, param1Bundle);
            parcel2.recycle();
            parcel1.recycle();
            return;
          } 
          parcel2.readException();
          parcel2.recycle();
          parcel1.recycle();
          return;
        } finally {}
      } finally {}
      parcel2.recycle();
      parcel1.recycle();
      throw param1IAccountManagerResponse;
    }
    
    public boolean addAccountExplicitly(int param1Int, Account param1Account, String param1String, Bundle param1Bundle) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeInt(param1Int);
        boolean bool = true;
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        if (param1Bundle != null) {
          parcel1.writeInt(1);
          param1Bundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(17, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
          bool = IAccountManager.Stub.getDefaultImpl().addAccountExplicitly(param1Int, param1Account, param1String, param1Bundle);
          return bool;
        } 
        parcel2.readException();
        param1Int = parcel2.readInt();
        if (param1Int == 0)
          bool = false; 
        return bool;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public boolean addAccountExplicitlyWithVisibility(int param1Int, Account param1Account, String param1String, Bundle param1Bundle, Map param1Map) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeInt(param1Int);
        boolean bool = true;
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        if (param1Bundle != null) {
          parcel1.writeInt(1);
          param1Bundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeMap(param1Map);
        try {
          if (!this.mRemote.transact(35, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
            bool = IAccountManager.Stub.getDefaultImpl().addAccountExplicitlyWithVisibility(param1Int, param1Account, param1String, param1Bundle, param1Map);
            parcel2.recycle();
            parcel1.recycle();
            return bool;
          } 
          parcel2.readException();
          param1Int = parcel2.readInt();
          if (param1Int == 0)
            bool = false; 
          parcel2.recycle();
          parcel1.recycle();
          return bool;
        } finally {}
      } finally {}
      parcel2.recycle();
      parcel1.recycle();
      throw param1Account;
    }
    
    public IBinder asBinder() {
      return this.mRemote;
    }
    
    public void clearPassword(int param1Int, Account param1Account) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeInt(param1Int);
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(21, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
          IAccountManager.Stub.getDefaultImpl().clearPassword(param1Int, param1Account);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void confirmCredentials(int param1Int, IAccountManagerResponse param1IAccountManagerResponse, Account param1Account, Bundle param1Bundle, boolean param1Boolean) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeInt(param1Int);
        if (param1IAccountManagerResponse != null) {
          iBinder = param1IAccountManagerResponse.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        boolean bool = true;
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (param1Bundle != null) {
          parcel1.writeInt(1);
          param1Bundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!param1Boolean)
          bool = false; 
        parcel1.writeInt(bool);
        if (!this.mRemote.transact(15, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
          IAccountManager.Stub.getDefaultImpl().confirmCredentials(param1Int, param1IAccountManagerResponse, param1Account, param1Bundle, param1Boolean);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void editProperties(int param1Int, IAccountManagerResponse param1IAccountManagerResponse, String param1String, boolean param1Boolean) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        boolean bool;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeInt(param1Int);
        if (param1IAccountManagerResponse != null) {
          iBinder = param1IAccountManagerResponse.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        parcel1.writeString(param1String);
        if (param1Boolean) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        if (!this.mRemote.transact(11, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
          IAccountManager.Stub.getDefaultImpl().editProperties(param1Int, param1IAccountManagerResponse, param1String, param1Boolean);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void finishSessionAsUser(IAccountManagerResponse param1IAccountManagerResponse, Bundle param1Bundle1, boolean param1Boolean, Bundle param1Bundle2, int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        boolean bool;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        if (param1IAccountManagerResponse != null) {
          iBinder = param1IAccountManagerResponse.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        if (param1Bundle1 != null) {
          parcel1.writeInt(1);
          param1Bundle1.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (param1Boolean) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        if (param1Bundle2 != null) {
          parcel1.writeInt(1);
          param1Bundle2.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(33, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
          IAccountManager.Stub.getDefaultImpl().finishSessionAsUser(param1IAccountManagerResponse, param1Bundle1, param1Boolean, param1Bundle2, param1Int);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public int getAccountVisibility(int param1Int, Account param1Account, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeInt(param1Int);
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(26, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
          param1Int = IAccountManager.Stub.getDefaultImpl().getAccountVisibility(param1Int, param1Account, param1String);
          return param1Int;
        } 
        parcel2.readException();
        param1Int = parcel2.readInt();
        return param1Int;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public Account[] getAccounts(int param1Int, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeInt(param1Int);
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(4, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null)
          return IAccountManager.Stub.getDefaultImpl().getAccounts(param1Int, param1String); 
        parcel2.readException();
        return (Account[])parcel2.createTypedArray(Account.CREATOR);
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public Map getAccountsAndVisibilityForPackage(int param1Int, String param1String1, String param1String2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeInt(param1Int);
        parcel1.writeString(param1String1);
        parcel1.writeString(param1String2);
        if (!this.mRemote.transact(32, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null)
          return IAccountManager.Stub.getDefaultImpl().getAccountsAndVisibilityForPackage(param1Int, param1String1, param1String2); 
        parcel2.readException();
        return parcel2.readHashMap(getClass().getClassLoader());
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void getAccountsByFeatures(int param1Int, IAccountManagerResponse param1IAccountManagerResponse, String param1String, String[] param1ArrayOfString) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeInt(param1Int);
        if (param1IAccountManagerResponse != null) {
          iBinder = param1IAccountManagerResponse.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        parcel1.writeString(param1String);
        parcel1.writeStringArray(param1ArrayOfString);
        if (!this.mRemote.transact(2, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
          IAccountManager.Stub.getDefaultImpl().getAccountsByFeatures(param1Int, param1IAccountManagerResponse, param1String, param1ArrayOfString);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void getAuthToken(int param1Int, IAccountManagerResponse param1IAccountManagerResponse, Account param1Account, String param1String, boolean param1Boolean1, boolean param1Boolean2, Bundle param1Bundle) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        boolean bool;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeInt(param1Int);
        if (param1IAccountManagerResponse != null) {
          iBinder = param1IAccountManagerResponse.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        if (param1Boolean1) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        if (param1Boolean2) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        if (param1Bundle != null) {
          parcel1.writeInt(1);
          param1Bundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        try {
          if (!this.mRemote.transact(5, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
            IAccountManager.Stub.getDefaultImpl().getAuthToken(param1Int, param1IAccountManagerResponse, param1Account, param1String, param1Boolean1, param1Boolean2, param1Bundle);
            parcel2.recycle();
            parcel1.recycle();
            return;
          } 
          parcel2.readException();
          parcel2.recycle();
          parcel1.recycle();
          return;
        } finally {}
      } finally {}
      parcel2.recycle();
      parcel1.recycle();
      throw param1IAccountManagerResponse;
    }
    
    public void getAuthTokenLabel(int param1Int, IAccountManagerResponse param1IAccountManagerResponse, String param1String1, String param1String2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeInt(param1Int);
        if (param1IAccountManagerResponse != null) {
          iBinder = param1IAccountManagerResponse.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        parcel1.writeString(param1String1);
        parcel1.writeString(param1String2);
        if (!this.mRemote.transact(12, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
          IAccountManager.Stub.getDefaultImpl().getAuthTokenLabel(param1Int, param1IAccountManagerResponse, param1String1, param1String2);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public AuthenticatorDescription[] getAuthenticatorTypes(int param1Int) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeInt(param1Int);
        if (!this.mRemote.transact(1, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null)
          return IAccountManager.Stub.getDefaultImpl().getAuthenticatorTypes(param1Int); 
        parcel2.readException();
        return (AuthenticatorDescription[])parcel2.createTypedArray(AuthenticatorDescription.CREATOR);
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String getInterfaceDescriptor() {
      return "com.lody.virtual.server.interfaces.IAccountManager";
    }
    
    public Map getPackagesAndVisibilityForAccount(int param1Int, Account param1Account) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeInt(param1Int);
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(31, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null)
          return IAccountManager.Stub.getDefaultImpl().getPackagesAndVisibilityForAccount(param1Int, param1Account); 
        parcel2.readException();
        return parcel2.readHashMap(getClass().getClassLoader());
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String getPassword(int param1Int, Account param1Account) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeInt(param1Int);
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(14, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null)
          return IAccountManager.Stub.getDefaultImpl().getPassword(param1Int, param1Account); 
        parcel2.readException();
        return parcel2.readString();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String getPreviousName(int param1Int, Account param1Account) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeInt(param1Int);
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(3, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null)
          return IAccountManager.Stub.getDefaultImpl().getPreviousName(param1Int, param1Account); 
        parcel2.readException();
        return parcel2.readString();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String getUserData(int param1Int, Account param1Account, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeInt(param1Int);
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(13, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null)
          return IAccountManager.Stub.getDefaultImpl().getUserData(param1Int, param1Account, param1String); 
        parcel2.readException();
        return parcel2.readString();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void hasFeatures(int param1Int, IAccountManagerResponse param1IAccountManagerResponse, Account param1Account, String[] param1ArrayOfString) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeInt(param1Int);
        if (param1IAccountManagerResponse != null) {
          iBinder = param1IAccountManagerResponse.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeStringArray(param1ArrayOfString);
        if (!this.mRemote.transact(9, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
          IAccountManager.Stub.getDefaultImpl().hasFeatures(param1Int, param1IAccountManagerResponse, param1Account, param1ArrayOfString);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void invalidateAuthToken(int param1Int, String param1String1, String param1String2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeInt(param1Int);
        parcel1.writeString(param1String1);
        parcel1.writeString(param1String2);
        if (!this.mRemote.transact(23, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
          IAccountManager.Stub.getDefaultImpl().invalidateAuthToken(param1Int, param1String1, param1String2);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void isCredentialsUpdateSuggested(IAccountManagerResponse param1IAccountManagerResponse, Account param1Account, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        if (param1IAccountManagerResponse != null) {
          iBinder = param1IAccountManagerResponse.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(34, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
          IAccountManager.Stub.getDefaultImpl().isCredentialsUpdateSuggested(param1IAccountManagerResponse, param1Account, param1String);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public String peekAuthToken(int param1Int, Account param1Account, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeInt(param1Int);
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(24, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null)
          return IAccountManager.Stub.getDefaultImpl().peekAuthToken(param1Int, param1Account, param1String); 
        parcel2.readException();
        return parcel2.readString();
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void registerAccountListener(String[] param1ArrayOfString) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeStringArray(param1ArrayOfString);
        if (!this.mRemote.transact(29, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
          IAccountManager.Stub.getDefaultImpl().registerAccountListener(param1ArrayOfString);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void removeAccount(int param1Int, IAccountManagerResponse param1IAccountManagerResponse, Account param1Account, boolean param1Boolean) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeInt(param1Int);
        if (param1IAccountManagerResponse != null) {
          iBinder = param1IAccountManagerResponse.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        boolean bool = true;
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!param1Boolean)
          bool = false; 
        parcel1.writeInt(bool);
        if (!this.mRemote.transact(20, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
          IAccountManager.Stub.getDefaultImpl().removeAccount(param1Int, param1IAccountManagerResponse, param1Account, param1Boolean);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public boolean removeAccountExplicitly(int param1Int, Account param1Account) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeInt(param1Int);
        boolean bool = true;
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(18, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
          bool = IAccountManager.Stub.getDefaultImpl().removeAccountExplicitly(param1Int, param1Account);
          return bool;
        } 
        parcel2.readException();
        param1Int = parcel2.readInt();
        if (param1Int == 0)
          bool = false; 
        return bool;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void renameAccount(int param1Int, IAccountManagerResponse param1IAccountManagerResponse, Account param1Account, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeInt(param1Int);
        if (param1IAccountManagerResponse != null) {
          iBinder = param1IAccountManagerResponse.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(19, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
          IAccountManager.Stub.getDefaultImpl().renameAccount(param1Int, param1IAccountManagerResponse, param1Account, param1String);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public boolean setAccountVisibility(int param1Int1, Account param1Account, String param1String, int param1Int2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeInt(param1Int1);
        boolean bool = true;
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        parcel1.writeInt(param1Int2);
        if (!this.mRemote.transact(25, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
          bool = IAccountManager.Stub.getDefaultImpl().setAccountVisibility(param1Int1, param1Account, param1String, param1Int2);
          return bool;
        } 
        parcel2.readException();
        param1Int1 = parcel2.readInt();
        if (param1Int1 == 0)
          bool = false; 
        return bool;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setAuthToken(int param1Int, Account param1Account, String param1String1, String param1String2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeInt(param1Int);
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String1);
        parcel1.writeString(param1String2);
        if (!this.mRemote.transact(7, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
          IAccountManager.Stub.getDefaultImpl().setAuthToken(param1Int, param1Account, param1String1, param1String2);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setPassword(int param1Int, Account param1Account, String param1String) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeInt(param1Int);
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        if (!this.mRemote.transact(6, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
          IAccountManager.Stub.getDefaultImpl().setPassword(param1Int, param1Account, param1String);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void setUserData(int param1Int, Account param1Account, String param1String1, String param1String2) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeInt(param1Int);
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String1);
        parcel1.writeString(param1String2);
        if (!this.mRemote.transact(8, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
          IAccountManager.Stub.getDefaultImpl().setUserData(param1Int, param1Account, param1String1, param1String2);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void startAddAccountSession(IAccountManagerResponse param1IAccountManagerResponse, String param1String1, String param1String2, String[] param1ArrayOfString, boolean param1Boolean, Bundle param1Bundle) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        boolean bool;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        if (param1IAccountManagerResponse != null) {
          iBinder = param1IAccountManagerResponse.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        parcel1.writeString(param1String1);
        parcel1.writeString(param1String2);
        parcel1.writeStringArray(param1ArrayOfString);
        if (param1Boolean) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        if (param1Bundle != null) {
          parcel1.writeInt(1);
          param1Bundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        try {
          if (!this.mRemote.transact(27, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
            IAccountManager.Stub.getDefaultImpl().startAddAccountSession(param1IAccountManagerResponse, param1String1, param1String2, param1ArrayOfString, param1Boolean, param1Bundle);
            parcel2.recycle();
            parcel1.recycle();
            return;
          } 
          parcel2.readException();
          parcel2.recycle();
          parcel1.recycle();
          return;
        } finally {}
      } finally {}
      parcel2.recycle();
      parcel1.recycle();
      throw param1IAccountManagerResponse;
    }
    
    public void startUpdateCredentialsSession(IAccountManagerResponse param1IAccountManagerResponse, Account param1Account, String param1String, boolean param1Boolean, Bundle param1Bundle) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        boolean bool;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        if (param1IAccountManagerResponse != null) {
          iBinder = param1IAccountManagerResponse.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        if (param1Boolean) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        if (param1Bundle != null) {
          parcel1.writeInt(1);
          param1Bundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        if (!this.mRemote.transact(28, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
          IAccountManager.Stub.getDefaultImpl().startUpdateCredentialsSession(param1IAccountManagerResponse, param1Account, param1String, param1Boolean, param1Bundle);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void unregisterAccountListener(String[] param1ArrayOfString) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeStringArray(param1ArrayOfString);
        if (!this.mRemote.transact(30, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
          IAccountManager.Stub.getDefaultImpl().unregisterAccountListener(param1ArrayOfString);
          return;
        } 
        parcel2.readException();
        return;
      } finally {
        parcel2.recycle();
        parcel1.recycle();
      } 
    }
    
    public void updateCredentials(int param1Int, IAccountManagerResponse param1IAccountManagerResponse, Account param1Account, String param1String, boolean param1Boolean, Bundle param1Bundle) throws RemoteException {
      Parcel parcel1 = Parcel.obtain();
      Parcel parcel2 = Parcel.obtain();
      try {
        IBinder iBinder;
        boolean bool;
        parcel1.writeInterfaceToken("com.lody.virtual.server.interfaces.IAccountManager");
        parcel1.writeInt(param1Int);
        if (param1IAccountManagerResponse != null) {
          iBinder = param1IAccountManagerResponse.asBinder();
        } else {
          iBinder = null;
        } 
        parcel1.writeStrongBinder(iBinder);
        if (param1Account != null) {
          parcel1.writeInt(1);
          param1Account.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        parcel1.writeString(param1String);
        if (param1Boolean) {
          bool = true;
        } else {
          bool = false;
        } 
        parcel1.writeInt(bool);
        if (param1Bundle != null) {
          parcel1.writeInt(1);
          param1Bundle.writeToParcel(parcel1, 0);
        } else {
          parcel1.writeInt(0);
        } 
        try {
          if (!this.mRemote.transact(10, parcel1, parcel2, 0) && IAccountManager.Stub.getDefaultImpl() != null) {
            IAccountManager.Stub.getDefaultImpl().updateCredentials(param1Int, param1IAccountManagerResponse, param1Account, param1String, param1Boolean, param1Bundle);
            parcel2.recycle();
            parcel1.recycle();
            return;
          } 
          parcel2.readException();
          parcel2.recycle();
          parcel1.recycle();
          return;
        } finally {}
      } finally {}
      parcel2.recycle();
      parcel1.recycle();
      throw param1IAccountManagerResponse;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\interfaces\IAccountManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */