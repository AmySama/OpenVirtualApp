package com.lody.virtual.client.hook.proxies.user;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ReplaceCallingPkgMethodProxy;
import com.lody.virtual.client.hook.base.ReplaceLastUserIdMethodProxy;
import com.lody.virtual.client.hook.base.ResultStaticMethodProxy;
import java.util.Collections;
import mirror.RefConstructor;
import mirror.android.content.pm.UserInfo;
import mirror.android.os.IUserManager;

public class UserManagerStub extends BinderInvocationProxy {
  public UserManagerStub() {
    super(IUserManager.Stub.asInterface, "user");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("setApplicationRestrictions"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getApplicationRestrictions"));
    addMethodProxy((MethodProxy)new ReplaceCallingPkgMethodProxy("getApplicationRestrictionsForUser"));
    addMethodProxy((MethodProxy)new ReplaceLastUserIdMethodProxy("isUserUnlockingOrUnlocked"));
    addMethodProxy((MethodProxy)new ReplaceLastUserIdMethodProxy("isManagedProfile"));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getProfileParent", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getUserIcon", null));
    RefConstructor refConstructor = UserInfo.ctor;
    Integer integer = Integer.valueOf(0);
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getUserInfo", refConstructor.newInstance(new Object[] { integer, "Admin", Integer.valueOf(UserInfo.FLAG_PRIMARY.get()) })));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getDefaultGuestRestrictions", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("setDefaultGuestRestrictions", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("removeRestrictions", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getUsers", Collections.singletonList(UserInfo.ctor.newInstance(new Object[] { integer, "Admin", Integer.valueOf(UserInfo.FLAG_PRIMARY.get()) }))));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("createUser", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("createProfileForUser", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getProfiles", Collections.EMPTY_LIST));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxie\\user\UserManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */