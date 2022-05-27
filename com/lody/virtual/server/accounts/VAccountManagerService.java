package com.lody.virtual.server.accounts;

import android.accounts.Account;
import android.accounts.AuthenticatorDescription;
import android.accounts.IAccountAuthenticator;
import android.accounts.IAccountAuthenticatorResponse;
import android.accounts.IAccountManagerResponse;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.Xml;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.helper.utils.Singleton;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.os.VBinder;
import com.lody.virtual.os.VEnvironment;
import com.lody.virtual.os.VUserHandle;
import com.lody.virtual.server.am.VActivityManagerService;
import com.lody.virtual.server.interfaces.IAccountManager;
import com.lody.virtual.server.pm.VPackageManagerService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import mirror.com.android.internal.R_Hide;
import org.xmlpull.v1.XmlPullParser;

public class VAccountManagerService extends IAccountManager.Stub {
  private static final long CHECK_IN_TIME = 43200000L;
  
  private static final String TAG;
  
  private static final Singleton<VAccountManagerService> sInstance = new Singleton<VAccountManagerService>() {
      protected VAccountManagerService create() {
        return new VAccountManagerService();
      }
    };
  
  private final SparseArray<List<VAccount>> accountsByUserId = new SparseArray();
  
  private final SparseArray<List<VAccountVisibility>> accountsVisibilitiesByUserId = new SparseArray();
  
  private final LinkedList<AuthTokenRecord> authTokenRecords = new LinkedList<AuthTokenRecord>();
  
  private final AuthenticatorCache cache = new AuthenticatorCache();
  
  private long lastAccountChangeTime = 0L;
  
  private Context mContext = VirtualCore.get().getContext();
  
  private final LinkedHashMap<String, Session> mSessions = new LinkedHashMap<String, Session>();
  
  static {
    TAG = VAccountManagerService.class.getSimpleName();
  }
  
  private void broadcastCheckInNowIfNeed(int paramInt) {
    long l = System.currentTimeMillis();
    if (Math.abs(l - this.lastAccountChangeTime) > 43200000L) {
      this.lastAccountChangeTime = l;
      saveAllAccounts();
      Intent intent = new Intent("android.server.checkin.CHECKIN_NOW");
      VActivityManagerService.get().sendBroadcastAsUser(intent, new VUserHandle(paramInt));
    } 
  }
  
  private void generateServicesMap(List<ResolveInfo> paramList, Map<String, AuthenticatorInfo> paramMap, RegisteredServicesParser paramRegisteredServicesParser) {
    for (ResolveInfo resolveInfo : paramList) {
      XmlResourceParser xmlResourceParser = paramRegisteredServicesParser.getParser(this.mContext, resolveInfo.serviceInfo, "android.accounts.AccountAuthenticator");
      if (xmlResourceParser != null)
        try {
          AttributeSet attributeSet = Xml.asAttributeSet((XmlPullParser)xmlResourceParser);
          while (true) {
            int i = xmlResourceParser.next();
            if (i != 1 && i != 2)
              continue; 
            break;
          } 
          if ("account-authenticator".equals(xmlResourceParser.getName())) {
            AuthenticatorDescription authenticatorDescription = parseAuthenticatorDescription(paramRegisteredServicesParser.getResources(this.mContext, resolveInfo.serviceInfo.applicationInfo), resolveInfo.serviceInfo.packageName, attributeSet);
            if (authenticatorDescription != null) {
              String str = authenticatorDescription.type;
              AuthenticatorInfo authenticatorInfo = new AuthenticatorInfo();
              this(this, authenticatorDescription, resolveInfo.serviceInfo);
              paramMap.put(str, authenticatorInfo);
            } 
          } 
        } catch (Exception exception) {
          exception.printStackTrace();
        }  
    } 
  }
  
  public static VAccountManagerService get() {
    return (VAccountManagerService)sInstance.get();
  }
  
  private VAccount getAccount(int paramInt, Account paramAccount) {
    return getAccount(paramInt, paramAccount.name, paramAccount.type);
  }
  
  private VAccount getAccount(int paramInt, String paramString1, String paramString2) {
    List list = (List)this.accountsByUserId.get(paramInt);
    if (list != null)
      for (VAccount vAccount : list) {
        if (TextUtils.equals(vAccount.name, paramString1) && TextUtils.equals(vAccount.type, paramString2))
          return vAccount; 
      }  
    return null;
  }
  
  private List<Account> getAccountList(int paramInt, String paramString) {
    synchronized (this.accountsByUserId) {
      ArrayList<Account> arrayList = new ArrayList();
      this();
      List list = (List)this.accountsByUserId.get(paramInt);
      if (list != null)
        for (VAccount vAccount : list) {
          if (paramString == null || vAccount.type.equals(paramString)) {
            Account account = new Account();
            this(vAccount.name, vAccount.type);
            arrayList.add(account);
          } 
        }  
      return arrayList;
    } 
  }
  
  private VAccountVisibility getAccountVisibility(int paramInt, Account paramAccount) {
    return getAccountVisibility(paramInt, paramAccount.name, paramAccount.type);
  }
  
  private VAccountVisibility getAccountVisibility(int paramInt, String paramString1, String paramString2) {
    List list = (List)this.accountsVisibilitiesByUserId.get(paramInt);
    if (list != null)
      for (VAccountVisibility vAccountVisibility : list) {
        if (TextUtils.equals(vAccountVisibility.name, paramString1) && TextUtils.equals(vAccountVisibility.type, paramString2))
          return vAccountVisibility; 
      }  
    return null;
  }
  
  private AuthenticatorInfo getAuthenticatorInfo(String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: getfield cache : Lcom/lody/virtual/server/accounts/VAccountManagerService$AuthenticatorCache;
    //   4: astore_2
    //   5: aload_2
    //   6: monitorenter
    //   7: aload_1
    //   8: ifnonnull -> 16
    //   11: aconst_null
    //   12: astore_1
    //   13: goto -> 33
    //   16: aload_0
    //   17: getfield cache : Lcom/lody/virtual/server/accounts/VAccountManagerService$AuthenticatorCache;
    //   20: getfield authenticators : Ljava/util/Map;
    //   23: aload_1
    //   24: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   29: checkcast com/lody/virtual/server/accounts/VAccountManagerService$AuthenticatorInfo
    //   32: astore_1
    //   33: aload_2
    //   34: monitorexit
    //   35: aload_1
    //   36: areturn
    //   37: astore_1
    //   38: aload_2
    //   39: monitorexit
    //   40: aload_1
    //   41: athrow
    // Exception table:
    //   from	to	target	type
    //   16	33	37	finally
    //   33	35	37	finally
    //   38	40	37	finally
  }
  
  private String getCustomAuthToken(int paramInt, Account paramAccount, String paramString1, String paramString2) {
    AuthTokenRecord authTokenRecord = new AuthTokenRecord(paramInt, paramAccount, paramString1, paramString2);
    long l = System.currentTimeMillis();
    synchronized (this.authTokenRecords) {
      String str;
      Iterator<AuthTokenRecord> iterator = this.authTokenRecords.iterator();
      paramAccount = null;
      while (iterator.hasNext()) {
        AuthTokenRecord authTokenRecord1 = iterator.next();
        if (authTokenRecord1.expiryEpochMillis > 0L && authTokenRecord1.expiryEpochMillis < l) {
          iterator.remove();
          continue;
        } 
        if (authTokenRecord.equals(authTokenRecord1))
          str = authTokenRecord.authToken; 
      } 
      return str;
    } 
  }
  
  private boolean insertAccountIntoDatabase(int paramInt, Account paramAccount, String paramString, Bundle paramBundle) {
    if (paramAccount == null)
      return false; 
    synchronized (this.accountsByUserId) {
      if (getAccount(paramInt, paramAccount.name, paramAccount.type) != null)
        return false; 
      VAccount vAccount = new VAccount();
      this(paramInt, paramAccount);
      vAccount.password = paramString;
      if (paramBundle != null)
        for (String str : paramBundle.keySet()) {
          Object object = paramBundle.get(str);
          if (object instanceof String)
            vAccount.userDatas.put(str, (String)object); 
        }  
      List<VAccount> list2 = (List)this.accountsByUserId.get(paramInt);
      List<VAccount> list1 = list2;
      if (list2 == null) {
        list1 = new ArrayList();
        super();
        this.accountsByUserId.put(paramInt, list1);
      } 
      list1.add(vAccount);
      saveAllAccounts();
      sendAccountsChangedBroadcast(vAccount.userId);
      return true;
    } 
  }
  
  private boolean insertAccountVisibilityIntoDatabase(int paramInt, Account paramAccount, Map<String, Integer> paramMap) {
    if (paramAccount == null)
      return false; 
    synchronized (this.accountsVisibilitiesByUserId) {
      VAccountVisibility vAccountVisibility = new VAccountVisibility();
      this(paramInt, paramAccount, paramMap);
      List<VAccountVisibility> list2 = (List)this.accountsVisibilitiesByUserId.get(paramInt);
      List<VAccountVisibility> list1 = list2;
      if (list2 == null) {
        list1 = new ArrayList();
        super();
        this.accountsVisibilitiesByUserId.put(paramInt, list1);
      } 
      list1.add(vAccountVisibility);
      saveAllAccountVisibilities();
      sendAccountsChangedBroadcast(vAccountVisibility.userId);
      return true;
    } 
  }
  
  private void onResult(IAccountManagerResponse paramIAccountManagerResponse, Bundle paramBundle) {
    try {
      paramIAccountManagerResponse.onResult(paramBundle);
    } catch (RemoteException remoteException) {
      remoteException.printStackTrace();
    } 
  }
  
  private static AuthenticatorDescription parseAuthenticatorDescription(Resources paramResources, String paramString, AttributeSet paramAttributeSet) {
    TypedArray typedArray = paramResources.obtainAttributes(paramAttributeSet, (int[])R_Hide.styleable.AccountAuthenticator.get());
    try {
      String str = typedArray.getString(R_Hide.styleable.AccountAuthenticator_accountType.get());
      int i = typedArray.getResourceId(R_Hide.styleable.AccountAuthenticator_label.get(), 0);
      int j = typedArray.getResourceId(R_Hide.styleable.AccountAuthenticator_icon.get(), 0);
      int k = typedArray.getResourceId(R_Hide.styleable.AccountAuthenticator_smallIcon.get(), 0);
      int m = typedArray.getResourceId(R_Hide.styleable.AccountAuthenticator_accountPreferences.get(), 0);
      boolean bool1 = typedArray.getBoolean(R_Hide.styleable.AccountAuthenticator_customTokens.get(), false);
      boolean bool2 = TextUtils.isEmpty(str);
      if (bool2)
        return null; 
      return new AuthenticatorDescription(str, paramString, i, j, k, m, bool1);
    } finally {
      typedArray.recycle();
    } 
  }
  
  private void readAllAccountVisibilities() {
    File file = VEnvironment.getAccountVisibilityConfigFile();
    Parcel parcel = Parcel.obtain();
    if (file.exists())
      try {
        FileInputStream fileInputStream = new FileInputStream();
        this(file);
        int i = (int)file.length();
        byte[] arrayOfByte = new byte[i];
        int j = fileInputStream.read(arrayOfByte);
        fileInputStream.close();
      } finally {
        Exception exception = null;
      }  
    parcel.recycle();
  }
  
  private void readAllAccounts() {
    File file = VEnvironment.getAccountConfigFile();
    refreshAuthenticatorCache(null);
    if (file.exists()) {
      this.accountsByUserId.clear();
      Parcel parcel = Parcel.obtain();
      try {
        FileInputStream fileInputStream = new FileInputStream();
        this(file);
        int i = (int)file.length();
        byte[] arrayOfByte = new byte[i];
        int j = fileInputStream.read(arrayOfByte);
        fileInputStream.close();
        if (j == i) {
          parcel.unmarshall(arrayOfByte, 0, i);
          parcel.setDataPosition(0);
          parcel.readInt();
          for (i = parcel.readInt(); i > 0; i--) {
            VAccount vAccount = new VAccount();
            this(parcel);
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            this();
            stringBuilder.append("Reading account : ");
            stringBuilder.append(vAccount.type);
            VLog.d(str, stringBuilder.toString(), new Object[0]);
            List<VAccount> list1 = (List)this.accountsByUserId.get(vAccount.userId);
            List<VAccount> list2 = list1;
            if (list1 == null) {
              list2 = new ArrayList();
              super();
              this.accountsByUserId.put(vAccount.userId, list2);
            } 
            list2.add(vAccount);
          } 
          this.lastAccountChangeTime = parcel.readLong();
        } else {
          IOException iOException = new IOException();
          this(String.format(Locale.ENGLISH, "Expect length %d, but got %d.", new Object[] { Integer.valueOf(i), Integer.valueOf(j) }));
          throw iOException;
        } 
      } catch (Exception exception) {
        exception.printStackTrace();
      } finally {
        Exception exception;
      } 
      parcel.recycle();
    } 
  }
  
  private boolean removeAccountInternal(int paramInt, Account paramAccount) {
    List list = (List)this.accountsByUserId.get(paramInt);
    if (list != null) {
      Iterator<VAccount> iterator = list.iterator();
      while (iterator.hasNext()) {
        VAccount vAccount = iterator.next();
        if (paramInt == vAccount.userId && TextUtils.equals(vAccount.name, paramAccount.name) && TextUtils.equals(paramAccount.type, vAccount.type)) {
          iterator.remove();
          saveAllAccounts();
          sendAccountsChangedBroadcast(paramInt);
          return true;
        } 
      } 
    } 
    return false;
  }
  
  private boolean removeAccountVisibility(int paramInt, Account paramAccount) {
    List list = (List)this.accountsVisibilitiesByUserId.get(paramInt);
    if (list != null) {
      Iterator<VAccountVisibility> iterator = list.iterator();
      while (iterator.hasNext()) {
        VAccountVisibility vAccountVisibility = iterator.next();
        if (paramInt == vAccountVisibility.userId && TextUtils.equals(vAccountVisibility.name, paramAccount.name) && TextUtils.equals(paramAccount.type, vAccountVisibility.type)) {
          iterator.remove();
          saveAllAccountVisibilities();
          sendAccountsChangedBroadcast(paramInt);
          return true;
        } 
      } 
    } 
    return false;
  }
  
  private Account renameAccountInternal(int paramInt, Account paramAccount, String paramString) {
    synchronized (this.accountsByUserId) {
      VAccount vAccount = getAccount(paramInt, paramAccount);
      if (vAccount != null) {
        vAccount.previousName = vAccount.name;
        vAccount.name = paramString;
        saveAllAccounts();
        Account account = new Account();
        this(vAccount.name, vAccount.type);
        synchronized (this.authTokenRecords) {
          for (AuthTokenRecord authTokenRecord : this.authTokenRecords) {
            if (authTokenRecord.userId == paramInt && authTokenRecord.account.equals(paramAccount))
              authTokenRecord.account = account; 
          } 
          sendAccountsChangedBroadcast(paramInt);
          return account;
        } 
      } 
      return paramAccount;
    } 
  }
  
  private boolean renameAccountVisibility(int paramInt, Account paramAccount, String paramString) {
    synchronized (this.accountsVisibilitiesByUserId) {
      VAccountVisibility vAccountVisibility = getAccountVisibility(paramInt, paramAccount);
      if (vAccountVisibility != null) {
        vAccountVisibility.name = paramString;
        saveAllAccountVisibilities();
        sendAccountsChangedBroadcast(paramInt);
        return true;
      } 
      return false;
    } 
  }
  
  private void saveAllAccountVisibilities() {
    File file = VEnvironment.getAccountVisibilityConfigFile();
    Parcel parcel = Parcel.obtain();
    try {
      parcel.writeInt(1);
      parcel.writeInt(this.accountsVisibilitiesByUserId.size());
      for (byte b = 0; b < this.accountsVisibilitiesByUserId.size(); b++) {
        parcel.writeInt(b);
        List list = (List)this.accountsVisibilitiesByUserId.valueAt(b);
        if (list == null) {
          parcel.writeInt(0);
        } else {
          parcel.writeInt(list.size());
          Iterator<VAccountVisibility> iterator = list.iterator();
          while (iterator.hasNext())
            ((VAccountVisibility)iterator.next()).writeToParcel(parcel, 0); 
        } 
      } 
      parcel.writeLong(this.lastAccountChangeTime);
      FileOutputStream fileOutputStream = new FileOutputStream();
      this(file);
      fileOutputStream.write(parcel.marshall());
      fileOutputStream.close();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    parcel.recycle();
  }
  
  private void saveAllAccounts() {
    File file = VEnvironment.getAccountConfigFile();
    Parcel parcel = Parcel.obtain();
    try {
      parcel.writeInt(1);
      ArrayList arrayList = new ArrayList();
      this();
      for (byte b = 0; b < this.accountsByUserId.size(); b++) {
        List list = (List)this.accountsByUserId.valueAt(b);
        if (list != null)
          arrayList.addAll(list); 
      } 
      parcel.writeInt(arrayList.size());
      Iterator<VAccount> iterator = arrayList.iterator();
      while (iterator.hasNext())
        ((VAccount)iterator.next()).writeToParcel(parcel, 0); 
      parcel.writeLong(this.lastAccountChangeTime);
      FileOutputStream fileOutputStream = new FileOutputStream();
      this(file);
      fileOutputStream.write(parcel.marshall());
      fileOutputStream.close();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    parcel.recycle();
  }
  
  private void sendAccountsChangedBroadcast(int paramInt) {
    Intent intent = new Intent("android.accounts.LOGIN_ACCOUNTS_CHANGED");
    VActivityManagerService.get().sendBroadcastAsUser(intent, new VUserHandle(paramInt));
    intent = new Intent("android.accounts.action.VISIBLE_ACCOUNTS_CHANGED");
    VActivityManagerService.get().sendBroadcastAsUser(intent, new VUserHandle(paramInt));
    broadcastCheckInNowIfNeed(paramInt);
  }
  
  private void setPasswordInternal(int paramInt, Account paramAccount, String paramString) {
    synchronized (this.accountsByUserId) {
      VAccount vAccount = getAccount(paramInt, paramAccount);
      if (vAccount != null) {
        vAccount.password = paramString;
        vAccount.authTokens.clear();
        saveAllAccounts();
        synchronized (this.authTokenRecords) {
          Iterator<AuthTokenRecord> iterator = this.authTokenRecords.iterator();
          while (iterator.hasNext()) {
            AuthTokenRecord authTokenRecord = iterator.next();
            if (authTokenRecord.userId == paramInt && authTokenRecord.account.equals(paramAccount))
              iterator.remove(); 
          } 
          sendAccountsChangedBroadcast(paramInt);
        } 
      } 
      return;
    } 
  }
  
  public static void systemReady() {
    get().readAllAccounts();
    get().readAllAccountVisibilities();
  }
  
  public boolean accountAuthenticated(int paramInt, Account paramAccount) {
    if (paramAccount != null)
      synchronized (this.accountsByUserId) {
        VAccount vAccount = getAccount(paramInt, paramAccount);
        if (vAccount != null) {
          vAccount.lastAuthenticatedTime = System.currentTimeMillis();
          saveAllAccounts();
          return true;
        } 
        return false;
      }  
    throw new IllegalArgumentException("account is null");
  }
  
  public void addAccount(int paramInt, IAccountManagerResponse paramIAccountManagerResponse, final String accountType, final String authTokenType, String[] paramArrayOfString, boolean paramBoolean, final Bundle optionsIn) {
    if (paramIAccountManagerResponse != null) {
      if (accountType != null) {
        final Bundle requiredFeatures;
        AuthenticatorInfo authenticatorInfo = getAuthenticatorInfo(accountType);
        if (authenticatorInfo == null) {
          try {
            bundle = new Bundle();
            this();
            bundle.putString("authtoken", authTokenType);
            bundle.putString("accountType", accountType);
            bundle.putBoolean("booleanResult", false);
            paramIAccountManagerResponse.onResult(bundle);
          } catch (RemoteException remoteException) {
            remoteException.printStackTrace();
          } 
          return;
        } 
        (new Session((IAccountManagerResponse)remoteException, paramInt, authenticatorInfo, paramBoolean, true, null, false, true) {
            public void run() throws RemoteException {
              this.mAuthenticator.addAccount((IAccountAuthenticatorResponse)this, this.mAuthenticatorInfo.desc.type, authTokenType, requiredFeatures, optionsIn);
            }
            
            protected String toDebugString(long param1Long) {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append(super.toDebugString(param1Long));
              stringBuilder.append(", addAccount, accountType ");
              stringBuilder.append(accountType);
              stringBuilder.append(", requiredFeatures ");
              String[] arrayOfString = requiredFeatures;
              if (arrayOfString != null) {
                String str = TextUtils.join(",", (Object[])arrayOfString);
              } else {
                arrayOfString = null;
              } 
              stringBuilder.append((String)arrayOfString);
              return stringBuilder.toString();
            }
          }).bind();
        return;
      } 
      throw new IllegalArgumentException("accountType is null");
    } 
    throw new IllegalArgumentException("response is null");
  }
  
  public boolean addAccountExplicitly(int paramInt, Account paramAccount, String paramString, Bundle paramBundle) {
    if (paramAccount != null)
      return insertAccountIntoDatabase(paramInt, paramAccount, paramString, paramBundle); 
    throw new IllegalArgumentException("account is null");
  }
  
  public boolean addAccountExplicitlyWithVisibility(int paramInt, Account paramAccount, String paramString, Bundle paramBundle, Map<String, Integer> paramMap) {
    if (paramAccount != null) {
      boolean bool = insertAccountIntoDatabase(paramInt, paramAccount, paramString, paramBundle);
      insertAccountVisibilityIntoDatabase(paramInt, paramAccount, paramMap);
      return bool;
    } 
    throw new IllegalArgumentException("account is null");
  }
  
  public void clearPassword(int paramInt, Account paramAccount) {
    if (paramAccount != null) {
      setPasswordInternal(paramInt, paramAccount, null);
      return;
    } 
    throw new IllegalArgumentException("account is null");
  }
  
  public void confirmCredentials(int paramInt, IAccountManagerResponse paramIAccountManagerResponse, final Account account, final Bundle options, boolean paramBoolean) {
    if (paramIAccountManagerResponse != null) {
      if (account != null) {
        AuthenticatorInfo authenticatorInfo = getAuthenticatorInfo(account.type);
        if (authenticatorInfo == null) {
          try {
            paramIAccountManagerResponse.onError(7, "account.type does not exist");
          } catch (RemoteException remoteException) {
            remoteException.printStackTrace();
          } 
          return;
        } 
        (new Session((IAccountManagerResponse)remoteException, paramInt, authenticatorInfo, paramBoolean, true, account.name, true, true) {
            public void run() throws RemoteException {
              this.mAuthenticator.confirmCredentials((IAccountAuthenticatorResponse)this, account, options);
            }
          }).bind();
        return;
      } 
      throw new IllegalArgumentException("account is null");
    } 
    throw new IllegalArgumentException("response is null");
  }
  
  public void editProperties(int paramInt, IAccountManagerResponse paramIAccountManagerResponse, final String accountType, boolean paramBoolean) {
    if (paramIAccountManagerResponse != null) {
      if (accountType != null) {
        AuthenticatorInfo authenticatorInfo = getAuthenticatorInfo(accountType);
        if (authenticatorInfo == null) {
          try {
            paramIAccountManagerResponse.onError(7, "account.type does not exist");
          } catch (RemoteException remoteException) {
            remoteException.printStackTrace();
          } 
          return;
        } 
        (new Session((IAccountManagerResponse)remoteException, paramInt, authenticatorInfo, paramBoolean, true, null) {
            public void run() throws RemoteException {
              this.mAuthenticator.editProperties((IAccountAuthenticatorResponse)this, this.mAuthenticatorInfo.desc.type);
            }
            
            protected String toDebugString(long param1Long) {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append(super.toDebugString(param1Long));
              stringBuilder.append(", editProperties, accountType ");
              stringBuilder.append(accountType);
              return stringBuilder.toString();
            }
          }).bind();
        return;
      } 
      throw new IllegalArgumentException("accountType is null");
    } 
    throw new IllegalArgumentException("response is null");
  }
  
  public void finishSessionAsUser(IAccountManagerResponse paramIAccountManagerResponse, Bundle paramBundle1, boolean paramBoolean, Bundle paramBundle2, int paramInt) throws RemoteException {
    throw new RuntimeException("Stub!");
  }
  
  public int getAccountVisibility(int paramInt, Account paramAccount, String paramString) {
    VAccountVisibility vAccountVisibility = getAccountVisibility(paramInt, paramAccount);
    return (vAccountVisibility == null || !vAccountVisibility.visibility.containsKey(paramString)) ? 0 : ((Integer)vAccountVisibility.visibility.get(paramString)).intValue();
  }
  
  public Account[] getAccounts(int paramInt, String paramString) {
    List<Account> list = getAccountList(paramInt, paramString);
    return list.<Account>toArray(new Account[list.size()]);
  }
  
  public Map<Account, Integer> getAccountsAndVisibilityForPackage(int paramInt, String paramString1, String paramString2) {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    for (Account account : getAccountList(paramInt, paramString2)) {
      VAccountVisibility vAccountVisibility = getAccountVisibility(paramInt, account);
      if (vAccountVisibility != null && vAccountVisibility.visibility.containsKey(paramString1))
        hashMap.put(account, vAccountVisibility.visibility.get(paramString1)); 
    } 
    return (Map)hashMap;
  }
  
  public void getAccountsByFeatures(int paramInt, IAccountManagerResponse paramIAccountManagerResponse, String paramString, String[] paramArrayOfString) {
    if (paramIAccountManagerResponse != null) {
      if (paramString != null) {
        Bundle bundle1;
        Bundle bundle2;
        AuthenticatorInfo authenticatorInfo = getAuthenticatorInfo(paramString);
        if (authenticatorInfo == null) {
          bundle1 = new Bundle();
          bundle1.putParcelableArray("accounts", (Parcelable[])new Account[0]);
          try {
            paramIAccountManagerResponse.onResult(bundle1);
          } catch (RemoteException null) {
            remoteException.printStackTrace();
          } 
          return;
        } 
        if (paramArrayOfString == null || paramArrayOfString.length == 0) {
          bundle2 = new Bundle();
          bundle2.putParcelableArray("accounts", (Parcelable[])getAccounts(paramInt, (String)bundle1));
          try {
            remoteException.onResult(bundle2);
          } catch (RemoteException remoteException) {
            remoteException.printStackTrace();
          } 
          return;
        } 
        (new GetAccountsByTypeAndFeatureSession((IAccountManagerResponse)remoteException, paramInt, authenticatorInfo, (String[])bundle2)).bind();
        return;
      } 
      throw new IllegalArgumentException("accountType is null");
    } 
    throw new IllegalArgumentException("response is null");
  }
  
  public AccountAndUser[] getAllAccounts() {
    ArrayList<AccountAndUser> arrayList = new ArrayList();
    for (byte b = 0; b < this.accountsByUserId.size(); b++) {
      for (VAccount vAccount : this.accountsByUserId.valueAt(b))
        arrayList.add(new AccountAndUser(new Account(vAccount.name, vAccount.type), vAccount.userId)); 
    } 
    return arrayList.<AccountAndUser>toArray(new AccountAndUser[0]);
  }
  
  public final void getAuthToken(final int userId, IAccountManagerResponse paramIAccountManagerResponse, final Account account, String paramString, final boolean notifyOnAuthFailure, boolean paramBoolean2, final Bundle loginOptions) {
    if (paramIAccountManagerResponse != null) {
      final Bundle authTokenType;
      if (account == null)
        try {
          VLog.w(TAG, "getAuthToken called with null account", new Object[0]);
          paramIAccountManagerResponse.onError(7, "account is null");
          return;
        } catch (RemoteException null) {
          remoteException.printStackTrace();
          return;
        }  
      if (paramString == null) {
        VLog.w(TAG, "getAuthToken called with null authTokenType", new Object[0]);
        remoteException.onError(7, "authTokenType is null");
        return;
      } 
      AuthenticatorInfo authenticatorInfo = getAuthenticatorInfo(account.type);
      if (authenticatorInfo == null) {
        try {
          remoteException.onError(7, "account.type does not exist");
        } catch (RemoteException remoteException) {
          remoteException.printStackTrace();
        } 
        return;
      } 
      final String callerPkg = loginOptions.getString("androidPackageName");
      final boolean customTokens = authenticatorInfo.desc.customTokens;
      loginOptions.putInt("callerUid", VBinder.getCallingUid());
      loginOptions.putInt("callerPid", VBinder.getCallingPid());
      if (notifyOnAuthFailure)
        loginOptions.putBoolean("notifyOnAuthFailure", true); 
      if (!bool)
        synchronized (this.accountsByUserId) {
          VAccount vAccount = getAccount(userId, account);
          if (vAccount != null) {
            String str1 = vAccount.authTokens.get(paramString);
          } else {
            null = null;
          } 
          if (null != null) {
            bundle = new Bundle();
            bundle.putString("authtoken", (String)null);
            bundle.putString("authAccount", account.name);
            bundle.putString("accountType", account.type);
            onResult((IAccountManagerResponse)remoteException, bundle);
            return;
          } 
        }  
      if (bool) {
        String str1 = getCustomAuthToken(userId, account, (String)bundle, str);
        if (str1 != null) {
          bundle = new Bundle();
          bundle.putString("authtoken", str1);
          bundle.putString("authAccount", account.name);
          bundle.putString("accountType", account.type);
          onResult((IAccountManagerResponse)remoteException, bundle);
          return;
        } 
      } 
      (new Session((IAccountManagerResponse)remoteException, userId, authenticatorInfo, paramBoolean2, false, account.name) {
          public void onResult(Bundle param1Bundle) throws RemoteException {
            if (param1Bundle != null) {
              String str = param1Bundle.getString("authtoken");
              if (str != null) {
                String str1 = param1Bundle.getString("authAccount");
                String str2 = param1Bundle.getString("accountType");
                if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str1)) {
                  onError(5, "the type and name should not be empty");
                  return;
                } 
                if (!customTokens)
                  synchronized (VAccountManagerService.this.accountsByUserId) {
                    if (VAccountManagerService.this.getAccount(userId, str1, str2) == null) {
                      List<VAccount> list1 = (List)VAccountManagerService.this.accountsByUserId.get(userId);
                      List<VAccount> list2 = list1;
                      if (list1 == null) {
                        list2 = new ArrayList();
                        super();
                        VAccountManagerService.this.accountsByUserId.put(userId, list2);
                      } 
                      VAccount vAccount = new VAccount();
                      int i = userId;
                      Account account = new Account();
                      this(str1, str2);
                      this(i, account);
                      list2.add(vAccount);
                      VAccountManagerService.this.saveAllAccounts();
                    } 
                  }  
                long l = param1Bundle.getLong("android.accounts.expiry", 0L);
                if (customTokens && l > System.currentTimeMillis()) {
                  VAccountManagerService.AuthTokenRecord authTokenRecord = new VAccountManagerService.AuthTokenRecord(userId, account, authTokenType, callerPkg, str, l);
                  synchronized (VAccountManagerService.this.authTokenRecords) {
                    VAccountManagerService.this.authTokenRecords.remove(authTokenRecord);
                    VAccountManagerService.this.authTokenRecords.add(authTokenRecord);
                  } 
                } 
              } 
              if ((Intent)param1Bundle.getParcelable("intent") != null)
                boolean bool = notifyOnAuthFailure; 
            } 
            super.onResult(param1Bundle);
          }
          
          public void run() throws RemoteException {
            this.mAuthenticator.getAuthToken((IAccountAuthenticatorResponse)this, account, authTokenType, loginOptions);
          }
          
          protected String toDebugString(long param1Long) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(super.toDebugString(param1Long));
            stringBuilder.append(", getAuthToken, ");
            stringBuilder.append(account);
            stringBuilder.append(", authTokenType ");
            stringBuilder.append(authTokenType);
            stringBuilder.append(", loginOptions ");
            stringBuilder.append(loginOptions);
            stringBuilder.append(", notifyOnAuthFailure ");
            stringBuilder.append(notifyOnAuthFailure);
            return stringBuilder.toString();
          }
        }).bind();
      return;
    } 
    throw new IllegalArgumentException("response is null");
  }
  
  public void getAuthTokenLabel(int paramInt, IAccountManagerResponse paramIAccountManagerResponse, String paramString1, final String authTokenType) {
    if (paramString1 != null) {
      if (authTokenType != null) {
        AuthenticatorInfo authenticatorInfo = getAuthenticatorInfo(paramString1);
        if (authenticatorInfo == null) {
          try {
            paramIAccountManagerResponse.onError(7, "account.type does not exist");
          } catch (RemoteException remoteException) {
            remoteException.printStackTrace();
          } 
          return;
        } 
        (new Session((IAccountManagerResponse)remoteException, paramInt, authenticatorInfo, false, false, null) {
            public void onResult(Bundle param1Bundle) throws RemoteException {
              if (param1Bundle != null) {
                String str = param1Bundle.getString("authTokenLabelKey");
                param1Bundle = new Bundle();
                param1Bundle.putString("authTokenLabelKey", str);
                super.onResult(param1Bundle);
              } else {
                super.onResult((Bundle)null);
              } 
            }
            
            public void run() throws RemoteException {
              this.mAuthenticator.getAuthTokenLabel((IAccountAuthenticatorResponse)this, authTokenType);
            }
          }).bind();
        return;
      } 
      throw new IllegalArgumentException("authTokenType is null");
    } 
    throw new IllegalArgumentException("accountType is null");
  }
  
  public AuthenticatorDescription[] getAuthenticatorTypes(int paramInt) {
    synchronized (this.cache) {
      AuthenticatorDescription[] arrayOfAuthenticatorDescription = new AuthenticatorDescription[this.cache.authenticators.size()];
      paramInt = 0;
      Iterator iterator = this.cache.authenticators.values().iterator();
      while (iterator.hasNext()) {
        arrayOfAuthenticatorDescription[paramInt] = ((AuthenticatorInfo)iterator.next()).desc;
        paramInt++;
      } 
      return arrayOfAuthenticatorDescription;
    } 
  }
  
  public Map<String, Integer> getPackagesAndVisibilityForAccount(int paramInt, Account paramAccount) {
    VAccountVisibility vAccountVisibility = getAccountVisibility(paramInt, paramAccount);
    return (vAccountVisibility != null) ? vAccountVisibility.visibility : null;
  }
  
  public String getPassword(int paramInt, Account paramAccount) {
    if (paramAccount != null)
      synchronized (this.accountsByUserId) {
        VAccount vAccount = getAccount(paramInt, paramAccount);
        if (vAccount != null)
          return vAccount.password; 
        return null;
      }  
    throw new IllegalArgumentException("account is null");
  }
  
  public final String getPreviousName(int paramInt, Account paramAccount) {
    // Byte code:
    //   0: aload_2
    //   1: ifnull -> 45
    //   4: aload_0
    //   5: getfield accountsByUserId : Landroid/util/SparseArray;
    //   8: astore_3
    //   9: aload_3
    //   10: monitorenter
    //   11: aconst_null
    //   12: astore #4
    //   14: aload_0
    //   15: iload_1
    //   16: aload_2
    //   17: invokespecial getAccount : (ILandroid/accounts/Account;)Lcom/lody/virtual/server/accounts/VAccount;
    //   20: astore #5
    //   22: aload #4
    //   24: astore_2
    //   25: aload #5
    //   27: ifnull -> 36
    //   30: aload #5
    //   32: getfield previousName : Ljava/lang/String;
    //   35: astore_2
    //   36: aload_3
    //   37: monitorexit
    //   38: aload_2
    //   39: areturn
    //   40: astore_2
    //   41: aload_3
    //   42: monitorexit
    //   43: aload_2
    //   44: athrow
    //   45: new java/lang/IllegalArgumentException
    //   48: dup
    //   49: ldc_w 'account is null'
    //   52: invokespecial <init> : (Ljava/lang/String;)V
    //   55: athrow
    // Exception table:
    //   from	to	target	type
    //   14	22	40	finally
    //   30	36	40	finally
    //   36	38	40	finally
    //   41	43	40	finally
  }
  
  public String getUserData(int paramInt, Account paramAccount, String paramString) {
    if (paramAccount != null) {
      if (paramString != null)
        synchronized (this.accountsByUserId) {
          VAccount vAccount = getAccount(paramInt, paramAccount);
          if (vAccount != null)
            return vAccount.userDatas.get(paramString); 
          return null;
        }  
      throw new IllegalArgumentException("key is null");
    } 
    throw new IllegalArgumentException("account is null");
  }
  
  public void hasFeatures(int paramInt, IAccountManagerResponse paramIAccountManagerResponse, final Account account, final String[] features) {
    if (paramIAccountManagerResponse != null) {
      if (account != null) {
        if (features != null) {
          AuthenticatorInfo authenticatorInfo = getAuthenticatorInfo(account.type);
          if (authenticatorInfo == null) {
            try {
              paramIAccountManagerResponse.onError(7, "account.type does not exist");
            } catch (RemoteException remoteException) {
              remoteException.printStackTrace();
            } 
            return;
          } 
          (new Session((IAccountManagerResponse)remoteException, paramInt, authenticatorInfo, false, true, account.name) {
              public void onResult(Bundle param1Bundle) throws RemoteException {
                IAccountManagerResponse iAccountManagerResponse = getResponseAndClose();
                if (iAccountManagerResponse != null)
                  if (param1Bundle == null) {
                    try {
                      iAccountManagerResponse.onError(5, "null bundle");
                      return;
                    } catch (RemoteException remoteException) {
                      Log.v(VAccountManagerService.TAG, "failure while notifying response", (Throwable)remoteException);
                    } 
                  } else {
                    String str = VAccountManagerService.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    this();
                    stringBuilder.append(getClass().getSimpleName());
                    stringBuilder.append(" calling onResult() on response ");
                    stringBuilder.append(iAccountManagerResponse);
                    Log.v(str, stringBuilder.toString());
                    Bundle bundle = new Bundle();
                    this();
                    bundle.putBoolean("booleanResult", remoteException.getBoolean("booleanResult", false));
                    iAccountManagerResponse.onResult(bundle);
                  }  
              }
              
              public void run() throws RemoteException {
                try {
                  this.mAuthenticator.hasFeatures((IAccountAuthenticatorResponse)this, account, features);
                } catch (RemoteException remoteException) {
                  onError(1, "remote exception");
                } 
              }
            }).bind();
          return;
        } 
        throw new IllegalArgumentException("features is null");
      } 
      throw new IllegalArgumentException("account is null");
    } 
    throw new IllegalArgumentException("response is null");
  }
  
  public void invalidateAuthToken(int paramInt, String paramString1, String paramString2) {
    if (paramString1 != null) {
      if (paramString2 != null)
        synchronized (this.accountsByUserId) {
          null = (List)this.accountsByUserId.get(paramInt);
          if (null != null) {
            boolean bool = false;
            for (VAccount vAccount : null) {
              if (vAccount.type.equals(paramString1)) {
                vAccount.authTokens.values().remove(paramString2);
                bool = true;
              } 
            } 
            if (bool)
              saveAllAccounts(); 
          } 
          synchronized (this.authTokenRecords) {
            Iterator<AuthTokenRecord> iterator = this.authTokenRecords.iterator();
            while (iterator.hasNext()) {
              AuthTokenRecord authTokenRecord = iterator.next();
              if (authTokenRecord.userId == paramInt && authTokenRecord.authTokenType.equals(paramString1) && authTokenRecord.authToken.equals(paramString2))
                iterator.remove(); 
            } 
            return;
          } 
        }  
      throw new IllegalArgumentException("authToken is null");
    } 
    throw new IllegalArgumentException("accountType is null");
  }
  
  public void isCredentialsUpdateSuggested(IAccountManagerResponse paramIAccountManagerResponse, Account paramAccount, String paramString) throws RemoteException {
    throw new RuntimeException("Stub!");
  }
  
  public String peekAuthToken(int paramInt, Account paramAccount, String paramString) {
    if (paramAccount != null) {
      if (paramString != null)
        synchronized (this.accountsByUserId) {
          VAccount vAccount = getAccount(paramInt, paramAccount);
          if (vAccount != null)
            return vAccount.authTokens.get(paramString); 
          return null;
        }  
      throw new IllegalArgumentException("authTokenType is null");
    } 
    throw new IllegalArgumentException("account is null");
  }
  
  public void refreshAuthenticatorCache(String paramString) {
    this.cache.authenticators.clear();
    Intent intent = new Intent("android.accounts.AccountAuthenticator");
    if (paramString != null)
      intent.setPackage(paramString); 
    generateServicesMap(VPackageManagerService.get().queryIntentServices(intent, null, 128, 0), this.cache.authenticators, new RegisteredServicesParser());
  }
  
  public void registerAccountListener(String[] paramArrayOfString) throws RemoteException {}
  
  public void removeAccount(final int userId, IAccountManagerResponse paramIAccountManagerResponse, final Account account, boolean paramBoolean) {
    if (paramIAccountManagerResponse != null) {
      if (account != null) {
        AuthenticatorInfo authenticatorInfo = getAuthenticatorInfo(account.type);
        if (authenticatorInfo == null) {
          try {
            paramIAccountManagerResponse.onError(7, "account.type does not exist");
          } catch (RemoteException remoteException) {
            remoteException.printStackTrace();
          } 
          return;
        } 
        (new Session((IAccountManagerResponse)remoteException, userId, authenticatorInfo, paramBoolean, true, account.name) {
            public void onResult(Bundle param1Bundle) throws RemoteException {
              if (param1Bundle != null && param1Bundle.containsKey("booleanResult") && !param1Bundle.containsKey("intent")) {
                boolean bool = param1Bundle.getBoolean("booleanResult");
                if (bool)
                  VAccountManagerService.this.removeAccountInternal(userId, account); 
                IAccountManagerResponse iAccountManagerResponse = getResponseAndClose();
                if (iAccountManagerResponse != null) {
                  String str = VAccountManagerService.TAG;
                  StringBuilder stringBuilder = new StringBuilder();
                  stringBuilder.append(getClass().getSimpleName());
                  stringBuilder.append(" calling onResult() on response ");
                  stringBuilder.append(iAccountManagerResponse);
                  Log.v(str, stringBuilder.toString());
                  Bundle bundle = new Bundle();
                  bundle.putBoolean("booleanResult", bool);
                  try {
                    iAccountManagerResponse.onResult(bundle);
                  } catch (RemoteException remoteException) {
                    remoteException.printStackTrace();
                  } 
                } 
              } 
              super.onResult(param1Bundle);
            }
            
            public void run() throws RemoteException {
              this.mAuthenticator.getAccountRemovalAllowed((IAccountAuthenticatorResponse)this, account);
            }
            
            protected String toDebugString(long param1Long) {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append(super.toDebugString(param1Long));
              stringBuilder.append(", removeAccount, account ");
              stringBuilder.append(account);
              return stringBuilder.toString();
            }
          }).bind();
        return;
      } 
      throw new IllegalArgumentException("account is null");
    } 
    throw new IllegalArgumentException("response is null");
  }
  
  public boolean removeAccountExplicitly(int paramInt, Account paramAccount) {
    boolean bool;
    if (paramAccount != null && removeAccountInternal(paramInt, paramAccount)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public void renameAccount(int paramInt, IAccountManagerResponse paramIAccountManagerResponse, Account paramAccount, String paramString) {
    if (paramAccount != null) {
      Account account = renameAccountInternal(paramInt, paramAccount, paramString);
      Bundle bundle = new Bundle();
      bundle.putString("authAccount", account.name);
      bundle.putString("accountType", account.type);
      try {
        paramIAccountManagerResponse.onResult(bundle);
      } catch (RemoteException remoteException) {
        Log.w(TAG, remoteException.getMessage());
      } 
      return;
    } 
    throw new IllegalArgumentException("account is null");
  }
  
  public boolean setAccountVisibility(int paramInt1, Account paramAccount, String paramString, int paramInt2) {
    VAccountVisibility vAccountVisibility = getAccountVisibility(paramInt1, paramAccount);
    if (vAccountVisibility == null)
      return false; 
    vAccountVisibility.visibility.put(paramString, Integer.valueOf(paramInt2));
    saveAllAccountVisibilities();
    sendAccountsChangedBroadcast(paramInt1);
    return true;
  }
  
  public void setAuthToken(int paramInt, Account paramAccount, String paramString1, String paramString2) {
    if (paramAccount != null) {
      if (paramString1 != null)
        synchronized (this.accountsByUserId) {
          VAccount vAccount = getAccount(paramInt, paramAccount);
          if (vAccount != null) {
            vAccount.authTokens.put(paramString1, paramString2);
            saveAllAccounts();
          } 
          return;
        }  
      throw new IllegalArgumentException("authTokenType is null");
    } 
    throw new IllegalArgumentException("account is null");
  }
  
  public void setPassword(int paramInt, Account paramAccount, String paramString) {
    if (paramAccount != null) {
      setPasswordInternal(paramInt, paramAccount, paramString);
      return;
    } 
    throw new IllegalArgumentException("account is null");
  }
  
  public void setUserData(int paramInt, Account paramAccount, String paramString1, String paramString2) {
    if (paramString1 != null) {
      if (paramAccount != null) {
        VAccount vAccount = getAccount(paramInt, paramAccount);
        if (vAccount != null)
          synchronized (this.accountsByUserId) {
            vAccount.userDatas.put(paramString1, paramString2);
            saveAllAccounts();
          }  
        return;
      } 
      throw new IllegalArgumentException("account is null");
    } 
    throw new IllegalArgumentException("key is null");
  }
  
  public void startAddAccountSession(IAccountManagerResponse paramIAccountManagerResponse, String paramString1, String paramString2, String[] paramArrayOfString, boolean paramBoolean, Bundle paramBundle) throws RemoteException {
    throw new RuntimeException("Stub!");
  }
  
  public void startUpdateCredentialsSession(IAccountManagerResponse paramIAccountManagerResponse, Account paramAccount, String paramString, boolean paramBoolean, Bundle paramBundle) throws RemoteException {
    throw new RuntimeException("Stub!");
  }
  
  public void unregisterAccountListener(String[] paramArrayOfString) throws RemoteException {}
  
  public void updateCredentials(int paramInt, IAccountManagerResponse paramIAccountManagerResponse, final Account account, final String authTokenType, boolean paramBoolean, final Bundle loginOptions) {
    if (paramIAccountManagerResponse != null) {
      if (account != null) {
        if (authTokenType != null) {
          AuthenticatorInfo authenticatorInfo = getAuthenticatorInfo(account.type);
          if (authenticatorInfo == null) {
            try {
              paramIAccountManagerResponse.onError(7, "account.type does not exist");
            } catch (RemoteException remoteException) {
              remoteException.printStackTrace();
            } 
            return;
          } 
          (new Session((IAccountManagerResponse)remoteException, paramInt, authenticatorInfo, paramBoolean, false, account.name) {
              public void run() throws RemoteException {
                this.mAuthenticator.updateCredentials((IAccountAuthenticatorResponse)this, account, authTokenType, loginOptions);
              }
              
              protected String toDebugString(long param1Long) {
                Bundle bundle = loginOptions;
                if (bundle != null)
                  bundle.keySet(); 
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(super.toDebugString(param1Long));
                stringBuilder.append(", updateCredentials, ");
                stringBuilder.append(account);
                stringBuilder.append(", authTokenType ");
                stringBuilder.append(authTokenType);
                stringBuilder.append(", loginOptions ");
                stringBuilder.append(loginOptions);
                return stringBuilder.toString();
              }
            }).bind();
          return;
        } 
        throw new IllegalArgumentException("authTokenType is null");
      } 
      throw new IllegalArgumentException("account is null");
    } 
    throw new IllegalArgumentException("response is null");
  }
  
  static final class AuthTokenRecord {
    public Account account;
    
    public String authToken;
    
    private String authTokenType;
    
    public long expiryEpochMillis;
    
    private String packageName;
    
    public int userId;
    
    AuthTokenRecord(int param1Int, Account param1Account, String param1String1, String param1String2) {
      this.userId = param1Int;
      this.account = param1Account;
      this.authTokenType = param1String1;
      this.packageName = param1String2;
    }
    
    AuthTokenRecord(int param1Int, Account param1Account, String param1String1, String param1String2, String param1String3, long param1Long) {
      this.userId = param1Int;
      this.account = param1Account;
      this.authTokenType = param1String1;
      this.packageName = param1String2;
      this.authToken = param1String3;
      this.expiryEpochMillis = param1Long;
    }
    
    public boolean equals(Object param1Object) {
      boolean bool = true;
      if (this == param1Object)
        return true; 
      if (param1Object == null || getClass() != param1Object.getClass())
        return false; 
      param1Object = param1Object;
      if (this.userId != ((AuthTokenRecord)param1Object).userId || !this.account.equals(((AuthTokenRecord)param1Object).account) || !this.authTokenType.equals(((AuthTokenRecord)param1Object).authTokenType) || !this.packageName.equals(((AuthTokenRecord)param1Object).packageName))
        bool = false; 
      return bool;
    }
    
    public int hashCode() {
      return ((this.userId * 31 + this.account.hashCode()) * 31 + this.authTokenType.hashCode()) * 31 + this.packageName.hashCode();
    }
  }
  
  private final class AuthenticatorCache {
    final Map<String, VAccountManagerService.AuthenticatorInfo> authenticators = new HashMap<String, VAccountManagerService.AuthenticatorInfo>();
    
    private AuthenticatorCache() {}
  }
  
  private final class AuthenticatorInfo {
    final AuthenticatorDescription desc;
    
    final ServiceInfo serviceInfo;
    
    AuthenticatorInfo(AuthenticatorDescription param1AuthenticatorDescription, ServiceInfo param1ServiceInfo) {
      this.desc = param1AuthenticatorDescription;
      this.serviceInfo = param1ServiceInfo;
    }
  }
  
  private class GetAccountsByTypeAndFeatureSession extends Session {
    private volatile Account[] mAccountsOfType = null;
    
    private volatile ArrayList<Account> mAccountsWithFeatures = null;
    
    private volatile int mCurrentAccount = 0;
    
    private final String[] mFeatures;
    
    public GetAccountsByTypeAndFeatureSession(IAccountManagerResponse param1IAccountManagerResponse, int param1Int, VAccountManagerService.AuthenticatorInfo param1AuthenticatorInfo, String[] param1ArrayOfString) {
      super(param1IAccountManagerResponse, param1Int, param1AuthenticatorInfo, false, true, null);
      this.mFeatures = param1ArrayOfString;
    }
    
    public void checkAccount() {
      StringBuilder stringBuilder;
      if (this.mCurrentAccount >= this.mAccountsOfType.length) {
        sendResult();
        return;
      } 
      IAccountAuthenticator iAccountAuthenticator = this.mAuthenticator;
      if (iAccountAuthenticator == null) {
        String str = VAccountManagerService.TAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("checkAccount: aborting session since we are no longer connected to the authenticator, ");
        stringBuilder.append(toDebugString());
        Log.v(str, stringBuilder.toString());
        return;
      } 
      try {
        stringBuilder.hasFeatures((IAccountAuthenticatorResponse)this, this.mAccountsOfType[this.mCurrentAccount], this.mFeatures);
      } catch (RemoteException remoteException) {
        onError(1, "remote exception");
      } 
    }
    
    public void onResult(Bundle param1Bundle) {
      this.mNumResults++;
      if (param1Bundle == null) {
        onError(5, "null bundle");
        return;
      } 
      if (param1Bundle.getBoolean("booleanResult", false))
        this.mAccountsWithFeatures.add(this.mAccountsOfType[this.mCurrentAccount]); 
      this.mCurrentAccount++;
      checkAccount();
    }
    
    public void run() throws RemoteException {
      this.mAccountsOfType = VAccountManagerService.this.getAccounts(this.mUserId, this.mAuthenticatorInfo.desc.type);
      this.mAccountsWithFeatures = new ArrayList<Account>(this.mAccountsOfType.length);
      this.mCurrentAccount = 0;
      checkAccount();
    }
    
    public void sendResult() {
      IAccountManagerResponse iAccountManagerResponse = getResponseAndClose();
      if (iAccountManagerResponse != null)
        try {
          int i = this.mAccountsWithFeatures.size();
          Account[] arrayOfAccount = new Account[i];
          for (byte b = 0; b < i; b++)
            arrayOfAccount[b] = this.mAccountsWithFeatures.get(b); 
          if (Log.isLoggable(VAccountManagerService.TAG, 2)) {
            String str = VAccountManagerService.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            this();
            stringBuilder.append(getClass().getSimpleName());
            stringBuilder.append(" calling onResult() on response ");
            stringBuilder.append(iAccountManagerResponse);
            Log.v(str, stringBuilder.toString());
          } 
          Bundle bundle = new Bundle();
          this();
          bundle.putParcelableArray("accounts", (Parcelable[])arrayOfAccount);
          iAccountManagerResponse.onResult(bundle);
        } catch (RemoteException remoteException) {
          Log.v(VAccountManagerService.TAG, "failure while notifying response", (Throwable)remoteException);
        }  
    }
    
    protected String toDebugString(long param1Long) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(super.toDebugString(param1Long));
      stringBuilder.append(", getAccountsByTypeAndFeatures, ");
      String[] arrayOfString = this.mFeatures;
      if (arrayOfString != null) {
        String str = TextUtils.join(",", (Object[])arrayOfString);
      } else {
        arrayOfString = null;
      } 
      stringBuilder.append((String)arrayOfString);
      return stringBuilder.toString();
    }
  }
  
  private abstract class Session extends IAccountAuthenticatorResponse.Stub implements IBinder.DeathRecipient, ServiceConnection {
    private String mAccountName;
    
    private boolean mAuthDetailsRequired;
    
    IAccountAuthenticator mAuthenticator;
    
    final VAccountManagerService.AuthenticatorInfo mAuthenticatorInfo;
    
    private long mCreationTime;
    
    private boolean mExpectActivityLaunch;
    
    private int mNumErrors;
    
    private int mNumRequestContinued;
    
    public int mNumResults;
    
    private IAccountManagerResponse mResponse;
    
    private final boolean mStripAuthTokenFromResult;
    
    private boolean mUpdateLastAuthenticatedTime;
    
    final int mUserId;
    
    Session(IAccountManagerResponse param1IAccountManagerResponse, int param1Int, VAccountManagerService.AuthenticatorInfo param1AuthenticatorInfo, boolean param1Boolean1, boolean param1Boolean2, String param1String) {
      this(param1IAccountManagerResponse, param1Int, param1AuthenticatorInfo, param1Boolean1, param1Boolean2, param1String, false, false);
    }
    
    Session(IAccountManagerResponse param1IAccountManagerResponse, int param1Int, VAccountManagerService.AuthenticatorInfo param1AuthenticatorInfo, boolean param1Boolean1, boolean param1Boolean2, String param1String, boolean param1Boolean3, boolean param1Boolean4) {
      if (param1AuthenticatorInfo != null) {
        this.mStripAuthTokenFromResult = param1Boolean2;
        this.mResponse = param1IAccountManagerResponse;
        this.mUserId = param1Int;
        this.mAuthenticatorInfo = param1AuthenticatorInfo;
        this.mExpectActivityLaunch = param1Boolean1;
        this.mCreationTime = SystemClock.elapsedRealtime();
        this.mAccountName = param1String;
        this.mAuthDetailsRequired = param1Boolean3;
        this.mUpdateLastAuthenticatedTime = param1Boolean4;
        synchronized (VAccountManagerService.this.mSessions) {
          VAccountManagerService.this.mSessions.put(toString(), this);
          if (param1IAccountManagerResponse != null)
            try {
              param1IAccountManagerResponse.asBinder().linkToDeath(this, 0);
            } catch (RemoteException remoteException) {
              this.mResponse = null;
              binderDied();
            }  
          return;
        } 
      } 
      throw new IllegalArgumentException("accountType is null");
    }
    
    private void close() {
      LinkedHashMap linkedHashMap;
      IAccountManagerResponse iAccountManagerResponse;
      synchronized (VAccountManagerService.this.mSessions) {
        if (VAccountManagerService.this.mSessions.remove(toString()) == null)
          return; 
        iAccountManagerResponse = this.mResponse;
        if (iAccountManagerResponse != null) {
          iAccountManagerResponse.asBinder().unlinkToDeath(this, 0);
          this.mResponse = null;
        } 
        unbind();
        return;
      } 
    }
    
    private void unbind() {
      if (this.mAuthenticator != null) {
        this.mAuthenticator = null;
        VActivityManager.get().unbindService(VAccountManagerService.this.mContext, this);
      } 
    }
    
    void bind() {
      String str = VAccountManagerService.TAG;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("initiating bind to authenticator type ");
      stringBuilder.append(this.mAuthenticatorInfo.desc.type);
      Log.v(str, stringBuilder.toString());
      Intent intent = new Intent();
      intent.setAction("android.accounts.AccountAuthenticator");
      intent.setClassName(this.mAuthenticatorInfo.serviceInfo.packageName, this.mAuthenticatorInfo.serviceInfo.name);
      if (!VActivityManager.get().bindService(VAccountManagerService.this.mContext, intent, this, 1, this.mUserId)) {
        String str1 = VAccountManagerService.TAG;
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("bind attempt failed for ");
        stringBuilder1.append(toDebugString());
        Log.d(str1, stringBuilder1.toString());
        onError(1, "bind failure");
      } 
    }
    
    public void binderDied() {
      this.mResponse = null;
      close();
    }
    
    IAccountManagerResponse getResponseAndClose() {
      IAccountManagerResponse iAccountManagerResponse = this.mResponse;
      if (iAccountManagerResponse == null)
        return null; 
      close();
      return iAccountManagerResponse;
    }
    
    public void onError(int param1Int, String param1String) {
      this.mNumErrors++;
      IAccountManagerResponse iAccountManagerResponse = getResponseAndClose();
      if (iAccountManagerResponse != null) {
        String str = VAccountManagerService.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClass().getSimpleName());
        stringBuilder.append(" calling onError() on response ");
        stringBuilder.append(iAccountManagerResponse);
        Log.v(str, stringBuilder.toString());
        try {
          iAccountManagerResponse.onError(param1Int, param1String);
        } catch (RemoteException remoteException) {
          Log.v(VAccountManagerService.TAG, "Session.onError: caught RemoteException while responding", (Throwable)remoteException);
        } 
      } else {
        Log.v(VAccountManagerService.TAG, "Session.onError: already closed");
      } 
    }
    
    public void onRequestContinued() {
      this.mNumRequestContinued++;
    }
    
    public void onResult(Bundle param1Bundle) throws RemoteException {
      // Byte code:
      //   0: aload_0
      //   1: getfield mNumResults : I
      //   4: istore_2
      //   5: iconst_1
      //   6: istore_3
      //   7: aload_0
      //   8: iload_2
      //   9: iconst_1
      //   10: iadd
      //   11: putfield mNumResults : I
      //   14: aload_1
      //   15: ifnull -> 201
      //   18: aload_1
      //   19: ldc 'booleanResult'
      //   21: iconst_0
      //   22: invokevirtual getBoolean : (Ljava/lang/String;Z)Z
      //   25: istore #4
      //   27: aload_1
      //   28: ldc_w 'authAccount'
      //   31: invokevirtual containsKey : (Ljava/lang/String;)Z
      //   34: ifeq -> 53
      //   37: aload_1
      //   38: ldc_w 'accountType'
      //   41: invokevirtual containsKey : (Ljava/lang/String;)Z
      //   44: ifeq -> 53
      //   47: iconst_1
      //   48: istore #5
      //   50: goto -> 56
      //   53: iconst_0
      //   54: istore #5
      //   56: aload_0
      //   57: getfield mUpdateLastAuthenticatedTime : Z
      //   60: ifeq -> 80
      //   63: iload_3
      //   64: istore_2
      //   65: iload #4
      //   67: ifne -> 82
      //   70: iload #5
      //   72: ifeq -> 80
      //   75: iload_3
      //   76: istore_2
      //   77: goto -> 82
      //   80: iconst_0
      //   81: istore_2
      //   82: iload_2
      //   83: ifne -> 93
      //   86: aload_0
      //   87: getfield mAuthDetailsRequired : Z
      //   90: ifeq -> 201
      //   93: aload_0
      //   94: getfield this$0 : Lcom/lody/virtual/server/accounts/VAccountManagerService;
      //   97: invokestatic access$100 : (Lcom/lody/virtual/server/accounts/VAccountManagerService;)Landroid/util/SparseArray;
      //   100: astore #6
      //   102: aload #6
      //   104: monitorenter
      //   105: aload_0
      //   106: getfield this$0 : Lcom/lody/virtual/server/accounts/VAccountManagerService;
      //   109: aload_0
      //   110: getfield mUserId : I
      //   113: aload_0
      //   114: getfield mAccountName : Ljava/lang/String;
      //   117: aload_0
      //   118: getfield mAuthenticatorInfo : Lcom/lody/virtual/server/accounts/VAccountManagerService$AuthenticatorInfo;
      //   121: getfield desc : Landroid/accounts/AuthenticatorDescription;
      //   124: getfield type : Ljava/lang/String;
      //   127: invokestatic access$200 : (Lcom/lody/virtual/server/accounts/VAccountManagerService;ILjava/lang/String;Ljava/lang/String;)Lcom/lody/virtual/server/accounts/VAccount;
      //   130: astore #7
      //   132: iload_2
      //   133: ifeq -> 156
      //   136: aload #7
      //   138: ifnull -> 156
      //   141: aload #7
      //   143: invokestatic currentTimeMillis : ()J
      //   146: putfield lastAuthenticatedTime : J
      //   149: aload_0
      //   150: getfield this$0 : Lcom/lody/virtual/server/accounts/VAccountManagerService;
      //   153: invokestatic access$300 : (Lcom/lody/virtual/server/accounts/VAccountManagerService;)V
      //   156: aload_0
      //   157: getfield mAuthDetailsRequired : Z
      //   160: ifeq -> 189
      //   163: ldc2_w -1
      //   166: lstore #8
      //   168: aload #7
      //   170: ifnull -> 180
      //   173: aload #7
      //   175: getfield lastAuthenticatedTime : J
      //   178: lstore #8
      //   180: aload_1
      //   181: ldc_w 'lastAuthenticatedTime'
      //   184: lload #8
      //   186: invokevirtual putLong : (Ljava/lang/String;J)V
      //   189: aload #6
      //   191: monitorexit
      //   192: goto -> 201
      //   195: astore_1
      //   196: aload #6
      //   198: monitorexit
      //   199: aload_1
      //   200: athrow
      //   201: aload_1
      //   202: ifnull -> 216
      //   205: aload_1
      //   206: ldc_w 'authtoken'
      //   209: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
      //   212: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
      //   215: pop
      //   216: aconst_null
      //   217: astore #6
      //   219: aload_1
      //   220: ifnull -> 235
      //   223: aload_1
      //   224: ldc_w 'intent'
      //   227: invokevirtual getParcelable : (Ljava/lang/String;)Landroid/os/Parcelable;
      //   230: checkcast android/content/Intent
      //   233: astore #6
      //   235: aload_0
      //   236: getfield mExpectActivityLaunch : Z
      //   239: ifeq -> 265
      //   242: aload_1
      //   243: ifnull -> 265
      //   246: aload_1
      //   247: ldc_w 'intent'
      //   250: invokevirtual containsKey : (Ljava/lang/String;)Z
      //   253: ifeq -> 265
      //   256: aload_0
      //   257: getfield mResponse : Landroid/accounts/IAccountManagerResponse;
      //   260: astore #7
      //   262: goto -> 271
      //   265: aload_0
      //   266: invokevirtual getResponseAndClose : ()Landroid/accounts/IAccountManagerResponse;
      //   269: astore #7
      //   271: aload #7
      //   273: ifnull -> 480
      //   276: aload_1
      //   277: ifnonnull -> 347
      //   280: invokestatic access$500 : ()Ljava/lang/String;
      //   283: astore_1
      //   284: new java/lang/StringBuilder
      //   287: astore #6
      //   289: aload #6
      //   291: invokespecial <init> : ()V
      //   294: aload #6
      //   296: aload_0
      //   297: invokevirtual getClass : ()Ljava/lang/Class;
      //   300: invokevirtual getSimpleName : ()Ljava/lang/String;
      //   303: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   306: pop
      //   307: aload #6
      //   309: ldc ' calling onError() on response '
      //   311: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   314: pop
      //   315: aload #6
      //   317: aload #7
      //   319: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   322: pop
      //   323: aload_1
      //   324: aload #6
      //   326: invokevirtual toString : ()Ljava/lang/String;
      //   329: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   332: pop
      //   333: aload #7
      //   335: iconst_5
      //   336: ldc_w 'null bundle returned'
      //   339: invokeinterface onError : (ILjava/lang/String;)V
      //   344: goto -> 480
      //   347: aload_0
      //   348: getfield mStripAuthTokenFromResult : Z
      //   351: ifeq -> 361
      //   354: aload_1
      //   355: ldc_w 'authtoken'
      //   358: invokevirtual remove : (Ljava/lang/String;)V
      //   361: invokestatic access$500 : ()Ljava/lang/String;
      //   364: astore #10
      //   366: new java/lang/StringBuilder
      //   369: astore #11
      //   371: aload #11
      //   373: invokespecial <init> : ()V
      //   376: aload #11
      //   378: aload_0
      //   379: invokevirtual getClass : ()Ljava/lang/Class;
      //   382: invokevirtual getSimpleName : ()Ljava/lang/String;
      //   385: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   388: pop
      //   389: aload #11
      //   391: ldc_w ' calling onResult() on response '
      //   394: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   397: pop
      //   398: aload #11
      //   400: aload #7
      //   402: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   405: pop
      //   406: aload #10
      //   408: aload #11
      //   410: invokevirtual toString : ()Ljava/lang/String;
      //   413: invokestatic v : (Ljava/lang/String;Ljava/lang/String;)I
      //   416: pop
      //   417: aload_1
      //   418: ldc_w 'errorCode'
      //   421: iconst_m1
      //   422: invokevirtual getInt : (Ljava/lang/String;I)I
      //   425: ifle -> 457
      //   428: aload #6
      //   430: ifnonnull -> 457
      //   433: aload #7
      //   435: aload_1
      //   436: ldc_w 'errorCode'
      //   439: invokevirtual getInt : (Ljava/lang/String;)I
      //   442: aload_1
      //   443: ldc_w 'errorMessage'
      //   446: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
      //   449: invokeinterface onError : (ILjava/lang/String;)V
      //   454: goto -> 480
      //   457: aload #7
      //   459: aload_1
      //   460: invokeinterface onResult : (Landroid/os/Bundle;)V
      //   465: goto -> 480
      //   468: astore_1
      //   469: invokestatic access$500 : ()Ljava/lang/String;
      //   472: ldc_w 'failure while notifying response'
      //   475: aload_1
      //   476: invokestatic v : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   479: pop
      //   480: return
      // Exception table:
      //   from	to	target	type
      //   105	132	195	finally
      //   141	156	195	finally
      //   156	163	195	finally
      //   173	180	195	finally
      //   180	189	195	finally
      //   189	192	195	finally
      //   196	199	195	finally
      //   280	344	468	android/os/RemoteException
      //   347	361	468	android/os/RemoteException
      //   361	428	468	android/os/RemoteException
      //   433	454	468	android/os/RemoteException
      //   457	465	468	android/os/RemoteException
    }
    
    public void onServiceConnected(ComponentName param1ComponentName, IBinder param1IBinder) {
      this.mAuthenticator = IAccountAuthenticator.Stub.asInterface(param1IBinder);
      try {
        run();
      } catch (RemoteException remoteException) {
        onError(1, "remote exception");
      } 
    }
    
    public void onServiceDisconnected(ComponentName param1ComponentName) {
      this.mAuthenticator = null;
      IAccountManagerResponse iAccountManagerResponse = getResponseAndClose();
      if (iAccountManagerResponse != null)
        try {
          iAccountManagerResponse.onError(1, "disconnected");
        } catch (RemoteException remoteException) {
          Log.v(VAccountManagerService.TAG, "Session.onServiceDisconnected: caught RemoteException while responding", (Throwable)remoteException);
        }  
    }
    
    public abstract void run() throws RemoteException;
    
    protected String toDebugString() {
      return toDebugString(SystemClock.elapsedRealtime());
    }
    
    protected String toDebugString(long param1Long) {
      boolean bool;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Session: expectLaunch ");
      stringBuilder.append(this.mExpectActivityLaunch);
      stringBuilder.append(", connected ");
      if (this.mAuthenticator != null) {
        bool = true;
      } else {
        bool = false;
      } 
      stringBuilder.append(bool);
      stringBuilder.append(", stats (");
      stringBuilder.append(this.mNumResults);
      stringBuilder.append("/");
      stringBuilder.append(this.mNumRequestContinued);
      stringBuilder.append("/");
      stringBuilder.append(this.mNumErrors);
      stringBuilder.append("), lifetime ");
      stringBuilder.append((param1Long - this.mCreationTime) / 1000.0D);
      return stringBuilder.toString();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\accounts\VAccountManagerService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */