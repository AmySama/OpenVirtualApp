package com.lody.virtual.client.hook.proxies.account;

import android.accounts.Account;
import android.accounts.IAccountManagerResponse;
import android.os.Bundle;
import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.BinderInvocationStub;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.ipc.VAccountManager;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.utils.Reflect;
import com.lody.virtual.os.VUserHandle;
import java.lang.reflect.Method;
import java.util.Map;
import mirror.android.accounts.IAccountManager;

public class AccountManagerStub extends BinderInvocationProxy {
  private static VAccountManager Mgr = VAccountManager.get();
  
  public AccountManagerStub() {
    super(IAccountManager.Stub.asInterface, "account");
  }
  
  public void inject() throws Throwable {
    super.inject();
    try {
      Reflect.on(getContext().getSystemService("account")).set("mService", ((BinderInvocationStub)getInvocationStub()).getProxyInterface());
    } finally {
      Exception exception = null;
    } 
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy(new getPassword());
    addMethodProxy(new getUserData());
    addMethodProxy(new getAuthenticatorTypes());
    addMethodProxy(new getAccounts());
    addMethodProxy(new getAccountsForPackage());
    addMethodProxy(new getAccountsByTypeForPackage());
    addMethodProxy(new getAccountsAsUser());
    addMethodProxy(new hasFeatures());
    addMethodProxy(new getAccountsByFeatures());
    addMethodProxy(new addAccountExplicitly());
    addMethodProxy(new removeAccount());
    addMethodProxy(new removeAccountAsUser());
    addMethodProxy(new removeAccountExplicitly());
    addMethodProxy(new copyAccountToUser());
    addMethodProxy(new invalidateAuthToken());
    addMethodProxy(new peekAuthToken());
    addMethodProxy(new setAuthToken());
    addMethodProxy(new setPassword());
    addMethodProxy(new clearPassword());
    addMethodProxy(new setUserData());
    addMethodProxy(new updateAppPermission());
    addMethodProxy(new getAuthToken());
    addMethodProxy(new addAccount());
    addMethodProxy(new addAccountAsUser());
    addMethodProxy(new updateCredentials());
    addMethodProxy(new editProperties());
    addMethodProxy(new confirmCredentialsAsUser());
    addMethodProxy(new accountAuthenticated());
    addMethodProxy(new getAuthTokenLabel());
    addMethodProxy(new addSharedAccountAsUser());
    addMethodProxy(new getSharedAccountsAsUser());
    addMethodProxy(new removeSharedAccountAsUser());
    addMethodProxy(new renameAccount());
    addMethodProxy(new getPreviousName());
    addMethodProxy(new renameSharedAccountAsUser());
    if (BuildCompat.isOreo()) {
      addMethodProxy(new finishSessionAsUser());
      addMethodProxy(new getAccountVisibility());
      addMethodProxy(new addAccountExplicitlyWithVisibility());
      addMethodProxy(new getAccountsAndVisibilityForPackage());
      addMethodProxy(new getPackagesAndVisibilityForAccount());
      addMethodProxy(new setAccountVisibility());
      addMethodProxy(new startAddAccountSession());
      addMethodProxy(new startUpdateCredentialsSession());
      addMethodProxy(new registerAccountListener());
      addMethodProxy(new unregisterAccountListener());
    } 
  }
  
  private static class accountAuthenticated extends MethodProxy {
    private accountAuthenticated() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1VarArgs[0];
      return Boolean.valueOf(AccountManagerStub.Mgr.accountAuthenticated((Account)param1Object));
    }
    
    public String getMethodName() {
      return "accountAuthenticated";
    }
  }
  
  private static class addAccount extends MethodProxy {
    private addAccount() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      IAccountManagerResponse iAccountManagerResponse = (IAccountManagerResponse)param1VarArgs[0];
      param1Object = param1VarArgs[1];
      String str = (String)param1VarArgs[2];
      String[] arrayOfString = (String[])param1VarArgs[3];
      boolean bool = ((Boolean)param1VarArgs[4]).booleanValue();
      Bundle bundle = (Bundle)param1VarArgs[5];
      AccountManagerStub.Mgr.addAccount(iAccountManagerResponse, (String)param1Object, str, arrayOfString, bool, bundle);
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "addAccount";
    }
  }
  
  private static class addAccountAsUser extends MethodProxy {
    private addAccountAsUser() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      IAccountManagerResponse iAccountManagerResponse = (IAccountManagerResponse)param1VarArgs[0];
      param1Object = param1VarArgs[1];
      String str = (String)param1VarArgs[2];
      String[] arrayOfString = (String[])param1VarArgs[3];
      boolean bool = ((Boolean)param1VarArgs[4]).booleanValue();
      Bundle bundle = (Bundle)param1VarArgs[5];
      AccountManagerStub.Mgr.addAccount(iAccountManagerResponse, (String)param1Object, str, arrayOfString, bool, bundle);
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "addAccountAsUser";
    }
  }
  
  private static class addAccountExplicitly extends MethodProxy {
    private addAccountExplicitly() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      Account account = (Account)param1VarArgs[0];
      param1Object = param1VarArgs[1];
      Bundle bundle = (Bundle)param1VarArgs[2];
      return Boolean.valueOf(AccountManagerStub.Mgr.addAccountExplicitly(account, (String)param1Object, bundle));
    }
    
    public String getMethodName() {
      return "addAccountExplicitly";
    }
  }
  
  private static class addAccountExplicitlyWithVisibility extends MethodProxy {
    private addAccountExplicitlyWithVisibility() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return Boolean.valueOf(AccountManagerStub.Mgr.addAccountExplicitlyWithVisibility((Account)param1VarArgs[0], (String)param1VarArgs[1], (Bundle)param1VarArgs[2], (Map)param1VarArgs[3]));
    }
    
    public String getMethodName() {
      return "addAccountExplicitlyWithVisibility";
    }
  }
  
  private static class addSharedAccountAsUser extends MethodProxy {
    private addSharedAccountAsUser() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      Account account = (Account)param1VarArgs[0];
      ((Integer)param1VarArgs[1]).intValue();
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "addSharedAccountAsUser";
    }
  }
  
  private static class clearPassword extends MethodProxy {
    private clearPassword() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1VarArgs[0];
      AccountManagerStub.Mgr.clearPassword((Account)param1Object);
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "clearPassword";
    }
  }
  
  private static class confirmCredentialsAsUser extends MethodProxy {
    private confirmCredentialsAsUser() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      IAccountManagerResponse iAccountManagerResponse = (IAccountManagerResponse)param1VarArgs[0];
      param1Object = param1VarArgs[1];
      Bundle bundle = (Bundle)param1VarArgs[2];
      boolean bool = ((Boolean)param1VarArgs[3]).booleanValue();
      AccountManagerStub.Mgr.confirmCredentials(iAccountManagerResponse, (Account)param1Object, bundle, bool);
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "confirmCredentialsAsUser";
    }
  }
  
  private static class copyAccountToUser extends MethodProxy {
    private copyAccountToUser() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      IAccountManagerResponse iAccountManagerResponse = (IAccountManagerResponse)param1VarArgs[0];
      Account account = (Account)param1VarArgs[1];
      ((Integer)param1VarArgs[2]).intValue();
      ((Integer)param1VarArgs[3]).intValue();
      param1Method.invoke(param1Object, param1VarArgs);
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "copyAccountToUser";
    }
  }
  
  private static class editProperties extends MethodProxy {
    private editProperties() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      IAccountManagerResponse iAccountManagerResponse = (IAccountManagerResponse)param1VarArgs[0];
      param1Object = param1VarArgs[1];
      boolean bool = ((Boolean)param1VarArgs[2]).booleanValue();
      AccountManagerStub.Mgr.editProperties(iAccountManagerResponse, (String)param1Object, bool);
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "editProperties";
    }
  }
  
  private static class finishSessionAsUser extends MethodProxy {
    private finishSessionAsUser() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      AccountManagerStub.Mgr.finishSessionAsUser((IAccountManagerResponse)param1VarArgs[0], (Bundle)param1VarArgs[1], ((Boolean)param1VarArgs[2]).booleanValue(), (Bundle)param1VarArgs[3], ((Integer)param1VarArgs[4]).intValue());
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "finishSessionAsUser";
    }
  }
  
  private static class getAccountByTypeAndFeatures extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1VarArgs[0];
      String str = (String)param1VarArgs[1];
      return AccountManagerStub.Mgr.getAccounts((String)param1Object);
    }
    
    public String getMethodName() {
      return "getAccountByTypeAndFeatures";
    }
  }
  
  private static class getAccountVisibility extends MethodProxy {
    private getAccountVisibility() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return Integer.valueOf(AccountManagerStub.Mgr.getAccountVisibility((Account)param1VarArgs[0], (String)param1VarArgs[1]));
    }
    
    public String getMethodName() {
      return "getAccountVisibility";
    }
  }
  
  private static class getAccounts extends MethodProxy {
    private getAccounts() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1VarArgs[0];
      return AccountManagerStub.Mgr.getAccounts((String)param1Object);
    }
    
    public String getMethodName() {
      return "getAccounts";
    }
  }
  
  private static class getAccountsAndVisibilityForPackage extends MethodProxy {
    private getAccountsAndVisibilityForPackage() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return AccountManagerStub.Mgr.getAccountsAndVisibilityForPackage((String)param1VarArgs[0], (String)param1VarArgs[1]);
    }
    
    public String getMethodName() {
      return "getAccountsAndVisibilityForPackage";
    }
  }
  
  private static class getAccountsAsUser extends MethodProxy {
    private getAccountsAsUser() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1VarArgs[0];
      return AccountManagerStub.Mgr.getAccounts((String)param1Object);
    }
    
    public String getMethodName() {
      return "getAccountsAsUser";
    }
  }
  
  private static class getAccountsByFeatures extends MethodProxy {
    private getAccountsByFeatures() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      IAccountManagerResponse iAccountManagerResponse = (IAccountManagerResponse)param1VarArgs[0];
      param1Object = param1VarArgs[1];
      String[] arrayOfString = (String[])param1VarArgs[2];
      AccountManagerStub.Mgr.getAccountsByFeatures(iAccountManagerResponse, (String)param1Object, arrayOfString);
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "getAccountsByFeatures";
    }
  }
  
  private static class getAccountsByTypeForPackage extends MethodProxy {
    private getAccountsByTypeForPackage() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1VarArgs[0];
      String str = (String)param1VarArgs[1];
      return AccountManagerStub.Mgr.getAccounts((String)param1Object);
    }
    
    public String getMethodName() {
      return "getAccountsByTypeForPackage";
    }
  }
  
  private static class getAccountsForPackage extends MethodProxy {
    private getAccountsForPackage() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1VarArgs[0];
      return AccountManagerStub.Mgr.getAccounts(null);
    }
    
    public String getMethodName() {
      return "getAccountsForPackage";
    }
  }
  
  private static class getAuthToken extends MethodProxy {
    private getAuthToken() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1VarArgs[0];
      Account account = (Account)param1VarArgs[1];
      String str = (String)param1VarArgs[2];
      boolean bool1 = ((Boolean)param1VarArgs[3]).booleanValue();
      boolean bool2 = ((Boolean)param1VarArgs[4]).booleanValue();
      Bundle bundle = (Bundle)param1VarArgs[5];
      AccountManagerStub.Mgr.getAuthToken((IAccountManagerResponse)param1Object, account, str, bool1, bool2, bundle);
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "getAuthToken";
    }
  }
  
  private static class getAuthTokenLabel extends MethodProxy {
    private getAuthTokenLabel() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      IAccountManagerResponse iAccountManagerResponse = (IAccountManagerResponse)param1VarArgs[0];
      param1Object = param1VarArgs[1];
      String str = (String)param1VarArgs[2];
      AccountManagerStub.Mgr.getAuthTokenLabel(iAccountManagerResponse, (String)param1Object, str);
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "getAuthTokenLabel";
    }
  }
  
  private static class getAuthenticatorTypes extends MethodProxy {
    private getAuthenticatorTypes() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return AccountManagerStub.Mgr.getAuthenticatorTypes(VUserHandle.myUserId());
    }
    
    public String getMethodName() {
      return "getAuthenticatorTypes";
    }
  }
  
  private static class getPackagesAndVisibilityForAccount extends MethodProxy {
    private getPackagesAndVisibilityForAccount() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return AccountManagerStub.Mgr.getPackagesAndVisibilityForAccount((Account)param1VarArgs[0]);
    }
    
    public String getMethodName() {
      return "getPackagesAndVisibilityForAccount";
    }
  }
  
  private static class getPassword extends MethodProxy {
    private getPassword() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1VarArgs[0];
      return AccountManagerStub.Mgr.getPassword((Account)param1Object);
    }
    
    public String getMethodName() {
      return "getPassword";
    }
  }
  
  private static class getPreviousName extends MethodProxy {
    private getPreviousName() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1VarArgs[0];
      return AccountManagerStub.Mgr.getPreviousName((Account)param1Object);
    }
    
    public String getMethodName() {
      return "getPreviousName";
    }
  }
  
  private static class getSharedAccountsAsUser extends MethodProxy {
    private getSharedAccountsAsUser() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      ((Integer)param1VarArgs[0]).intValue();
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "getSharedAccountsAsUser";
    }
  }
  
  private static class getUserData extends MethodProxy {
    private getUserData() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1VarArgs[0];
      String str = (String)param1VarArgs[1];
      return AccountManagerStub.Mgr.getUserData((Account)param1Object, str);
    }
    
    public String getMethodName() {
      return "getUserData";
    }
  }
  
  private static class hasFeatures extends MethodProxy {
    private hasFeatures() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      IAccountManagerResponse iAccountManagerResponse = (IAccountManagerResponse)param1VarArgs[0];
      param1Object = param1VarArgs[1];
      String[] arrayOfString = (String[])param1VarArgs[2];
      AccountManagerStub.Mgr.hasFeatures(iAccountManagerResponse, (Account)param1Object, arrayOfString);
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "hasFeatures";
    }
  }
  
  private static class invalidateAuthToken extends MethodProxy {
    private invalidateAuthToken() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1VarArgs[0];
      String str = (String)param1VarArgs[1];
      AccountManagerStub.Mgr.invalidateAuthToken((String)param1Object, str);
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "invalidateAuthToken";
    }
  }
  
  private static class isCredentialsUpdateSuggested extends MethodProxy {
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      AccountManagerStub.Mgr.isCredentialsUpdateSuggested((IAccountManagerResponse)param1VarArgs[0], (Account)param1VarArgs[1], (String)param1VarArgs[2]);
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "isCredentialsUpdateSuggested";
    }
  }
  
  private static class peekAuthToken extends MethodProxy {
    private peekAuthToken() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1VarArgs[0];
      String str = (String)param1VarArgs[1];
      return AccountManagerStub.Mgr.peekAuthToken((Account)param1Object, str);
    }
    
    public String getMethodName() {
      return "peekAuthToken";
    }
  }
  
  private static class registerAccountListener extends MethodProxy {
    private registerAccountListener() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      AccountManagerStub.Mgr.registerAccountListener((String[])param1VarArgs[0]);
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "registerAccountListener";
    }
  }
  
  private static class removeAccount extends MethodProxy {
    private removeAccount() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      IAccountManagerResponse iAccountManagerResponse = (IAccountManagerResponse)param1VarArgs[0];
      param1Object = param1VarArgs[1];
      boolean bool = ((Boolean)param1VarArgs[2]).booleanValue();
      AccountManagerStub.Mgr.removeAccount(iAccountManagerResponse, (Account)param1Object, bool);
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "removeAccount";
    }
  }
  
  private static class removeAccountAsUser extends MethodProxy {
    private removeAccountAsUser() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      IAccountManagerResponse iAccountManagerResponse = (IAccountManagerResponse)param1VarArgs[0];
      param1Object = param1VarArgs[1];
      boolean bool = ((Boolean)param1VarArgs[2]).booleanValue();
      AccountManagerStub.Mgr.removeAccount(iAccountManagerResponse, (Account)param1Object, bool);
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "removeAccountAsUser";
    }
  }
  
  private static class removeAccountExplicitly extends MethodProxy {
    private removeAccountExplicitly() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1VarArgs[0];
      return Boolean.valueOf(AccountManagerStub.Mgr.removeAccountExplicitly((Account)param1Object));
    }
    
    public String getMethodName() {
      return "removeAccountExplicitly";
    }
  }
  
  private static class removeSharedAccountAsUser extends MethodProxy {
    private removeSharedAccountAsUser() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      Account account = (Account)param1VarArgs[0];
      ((Integer)param1VarArgs[1]).intValue();
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "removeSharedAccountAsUser";
    }
  }
  
  private static class renameAccount extends MethodProxy {
    private renameAccount() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      IAccountManagerResponse iAccountManagerResponse = (IAccountManagerResponse)param1VarArgs[0];
      param1Object = param1VarArgs[1];
      String str = (String)param1VarArgs[2];
      AccountManagerStub.Mgr.renameAccount(iAccountManagerResponse, (Account)param1Object, str);
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "renameAccount";
    }
  }
  
  private static class renameSharedAccountAsUser extends MethodProxy {
    private renameSharedAccountAsUser() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      Account account = (Account)param1VarArgs[0];
      String str = (String)param1VarArgs[1];
      ((Integer)param1VarArgs[2]).intValue();
      return param1Method.invoke(param1Object, param1VarArgs);
    }
    
    public String getMethodName() {
      return "renameSharedAccountAsUser";
    }
  }
  
  private static class setAccountVisibility extends MethodProxy {
    private setAccountVisibility() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      return Boolean.valueOf(AccountManagerStub.Mgr.setAccountVisibility((Account)param1VarArgs[0], (String)param1VarArgs[1], ((Integer)param1VarArgs[2]).intValue()));
    }
    
    public String getMethodName() {
      return "setAccountVisibility";
    }
  }
  
  private static class setAuthToken extends MethodProxy {
    private setAuthToken() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      Account account = (Account)param1VarArgs[0];
      param1Object = param1VarArgs[1];
      String str = (String)param1VarArgs[2];
      AccountManagerStub.Mgr.setAuthToken(account, (String)param1Object, str);
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "setAuthToken";
    }
  }
  
  private static class setPassword extends MethodProxy {
    private setPassword() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      param1Object = param1VarArgs[0];
      String str = (String)param1VarArgs[1];
      AccountManagerStub.Mgr.setPassword((Account)param1Object, str);
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "setPassword";
    }
  }
  
  private static class setUserData extends MethodProxy {
    private setUserData() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      Account account = (Account)param1VarArgs[0];
      param1Object = param1VarArgs[1];
      String str = (String)param1VarArgs[2];
      AccountManagerStub.Mgr.setUserData(account, (String)param1Object, str);
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "setUserData";
    }
  }
  
  private static class startAddAccountSession extends MethodProxy {
    private startAddAccountSession() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      AccountManagerStub.Mgr.startAddAccountSession((IAccountManagerResponse)param1VarArgs[0], (String)param1VarArgs[1], (String)param1VarArgs[2], (String[])param1VarArgs[3], ((Boolean)param1VarArgs[4]).booleanValue(), (Bundle)param1VarArgs[5]);
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "startAddAccountSession";
    }
  }
  
  private static class startUpdateCredentialsSession extends MethodProxy {
    private startUpdateCredentialsSession() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      AccountManagerStub.Mgr.startUpdateCredentialsSession((IAccountManagerResponse)param1VarArgs[0], (Account)param1VarArgs[1], (String)param1VarArgs[2], ((Boolean)param1VarArgs[3]).booleanValue(), (Bundle)param1VarArgs[4]);
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "startUpdateCredentialsSession";
    }
  }
  
  private static class unregisterAccountListener extends MethodProxy {
    private unregisterAccountListener() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      AccountManagerStub.Mgr.unregisterAccountListener((String[])param1VarArgs[0]);
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "unregisterAccountListener";
    }
  }
  
  private static class updateAppPermission extends MethodProxy {
    private updateAppPermission() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      Account account = (Account)param1VarArgs[0];
      String str = (String)param1VarArgs[1];
      ((Integer)param1VarArgs[2]).intValue();
      ((Boolean)param1VarArgs[3]).booleanValue();
      param1Method.invoke(param1Object, param1VarArgs);
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "updateAppPermission";
    }
  }
  
  private static class updateCredentials extends MethodProxy {
    private updateCredentials() {}
    
    public Object call(Object param1Object, Method param1Method, Object... param1VarArgs) throws Throwable {
      IAccountManagerResponse iAccountManagerResponse = (IAccountManagerResponse)param1VarArgs[0];
      Account account = (Account)param1VarArgs[1];
      param1Object = param1VarArgs[2];
      boolean bool = ((Boolean)param1VarArgs[3]).booleanValue();
      Bundle bundle = (Bundle)param1VarArgs[4];
      AccountManagerStub.Mgr.updateCredentials(iAccountManagerResponse, account, (String)param1Object, bool, bundle);
      return Integer.valueOf(0);
    }
    
    public String getMethodName() {
      return "updateCredentials";
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\account\AccountManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */