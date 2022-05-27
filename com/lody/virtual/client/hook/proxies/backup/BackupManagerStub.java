package com.lody.virtual.client.hook.proxies.backup;

import com.lody.virtual.client.hook.base.BinderInvocationProxy;
import com.lody.virtual.client.hook.base.MethodProxy;
import com.lody.virtual.client.hook.base.ResultStaticMethodProxy;
import com.lody.virtual.helper.compat.BuildCompat;
import mirror.android.app.backup.IBackupManager;

public class BackupManagerStub extends BinderInvocationProxy {
  public BackupManagerStub() {
    super(IBackupManager.Stub.asInterface, "backup");
  }
  
  protected void onBindMethods() {
    super.onBindMethods();
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("dataChanged", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("clearBackupData", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("agentConnected", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("agentDisconnected", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("restoreAtInstall", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("setBackupEnabled", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("setBackupProvisioned", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("backupNow", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("fullBackup", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("fullTransportBackup", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("fullRestore", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("acknowledgeFullBackupOrRestore", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("getCurrentTransport", null));
    Boolean bool = Boolean.valueOf(false);
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("listAllTransports", new String[0]));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("selectBackupTransport", null));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("isBackupEnabled", bool));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("setBackupPassword", Boolean.valueOf(true)));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("hasBackupPassword", bool));
    addMethodProxy((MethodProxy)new ResultStaticMethodProxy("beginRestoreSession", null));
    if (BuildCompat.isOreo())
      addMethodProxy((MethodProxy)new ResultStaticMethodProxy("selectBackupTransportAsync", null)); 
    if (BuildCompat.isPie()) {
      addMethodProxy((MethodProxy)new ResultStaticMethodProxy("updateTransportAttributes", null));
      addMethodProxy((MethodProxy)new ResultStaticMethodProxy("isBackupServiceActive", bool));
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\proxies\backup\BackupManagerStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */