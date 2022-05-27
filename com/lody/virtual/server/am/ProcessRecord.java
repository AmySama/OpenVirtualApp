package com.lody.virtual.server.am;

import android.content.pm.ApplicationInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Process;
import com.lody.virtual.client.IVClient;
import com.lody.virtual.client.stub.StubManifest;
import com.lody.virtual.helper.compat.ObjectsCompat;
import com.lody.virtual.os.VUserHandle;
import com.lody.virtual.remote.ClientConfig;
import com.lody.virtual.server.extension.VExtPackageAccessor;
import com.lody.virtual.server.pm.PrivilegeAppOptimizer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

final class ProcessRecord extends Binder {
  public IInterface appThread;
  
  public IVClient client;
  
  public final ApplicationInfo info;
  
  public boolean isExt;
  
  public int pid;
  
  final Set<String> pkgList = Collections.synchronizedSet(new HashSet<String>());
  
  public boolean privilege;
  
  public final String processName;
  
  public int userId;
  
  public int vpid;
  
  public int vuid;
  
  public ProcessRecord(ApplicationInfo paramApplicationInfo, String paramString, int paramInt1, int paramInt2, boolean paramBoolean) {
    this.info = paramApplicationInfo;
    this.vuid = paramInt1;
    this.vpid = paramInt2;
    this.userId = VUserHandle.getUserId(paramInt1);
    this.processName = paramString;
    this.isExt = paramBoolean;
    this.privilege = PrivilegeAppOptimizer.get().isPrivilegeProcess(paramString);
  }
  
  public boolean equals(Object paramObject) {
    boolean bool = true;
    if (this == paramObject)
      return true; 
    if (paramObject == null || getClass() != paramObject.getClass())
      return false; 
    paramObject = paramObject;
    if (this.vuid != ((ProcessRecord)paramObject).vuid || this.vpid != ((ProcessRecord)paramObject).vpid || this.isExt != ((ProcessRecord)paramObject).isExt || this.userId != ((ProcessRecord)paramObject).userId || !ObjectsCompat.equals(this.processName, ((ProcessRecord)paramObject).processName))
      bool = false; 
    return bool;
  }
  
  public ClientConfig getClientConfig() {
    ClientConfig clientConfig = new ClientConfig();
    clientConfig.isExt = this.isExt;
    clientConfig.vuid = this.vuid;
    clientConfig.vpid = this.vpid;
    clientConfig.packageName = this.info.packageName;
    clientConfig.processName = this.processName;
    clientConfig.token = (IBinder)this;
    return clientConfig;
  }
  
  public String getProviderAuthority() {
    return StubManifest.getStubAuthority(this.vpid, this.isExt);
  }
  
  public int hashCode() {
    return ObjectsCompat.hash(new Object[] { this.processName, Integer.valueOf(this.vuid), Integer.valueOf(this.vpid), Boolean.valueOf(this.isExt), Integer.valueOf(this.userId) });
  }
  
  public boolean isPrivilegeProcess() {
    return this.privilege;
  }
  
  public void kill() {
    if (this.isExt) {
      VExtPackageAccessor.forceStop(new int[] { this.pid });
    } else {
      try {
        Process.killProcess(this.pid);
      } finally {
        Exception exception = null;
      } 
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\am\ProcessRecord.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */