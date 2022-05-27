package com.lody.virtual.server.vs;

import android.util.SparseArray;
import com.lody.virtual.server.interfaces.IVirtualStorageService;
import com.lody.virtual.server.pm.VUserManagerService;
import java.io.File;
import java.util.HashMap;

public class VirtualStorageService extends IVirtualStorageService.Stub {
  private static final String[] sPublicDirs;
  
  private static final VirtualStorageService sService = new VirtualStorageService();
  
  private final SparseArray<HashMap<String, VSConfig>> mConfigs = new SparseArray();
  
  private final VSPersistenceLayer mLayer = new VSPersistenceLayer(this);
  
  static {
    sPublicDirs = new String[] { "DCIM", "Music", "Pictures" };
  }
  
  private VirtualStorageService() {
    this.mLayer.read();
  }
  
  private void checkUserId(int paramInt) {
    if (VUserManagerService.get().exists(paramInt))
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Invalid userId ");
    stringBuilder.append(paramInt);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public static VirtualStorageService get() {
    return sService;
  }
  
  private VSConfig getOrCreateVSConfigLocked(String paramString, int paramInt) {
    HashMap<Object, Object> hashMap1 = (HashMap)this.mConfigs.get(paramInt);
    HashMap<Object, Object> hashMap2 = hashMap1;
    if (hashMap1 == null) {
      hashMap2 = new HashMap<Object, Object>();
      this.mConfigs.put(paramInt, hashMap2);
    } 
    VSConfig vSConfig2 = (VSConfig)hashMap2.get(paramString);
    VSConfig vSConfig1 = vSConfig2;
    if (vSConfig2 == null) {
      vSConfig1 = new VSConfig();
      vSConfig1.enable = true;
      hashMap2.put(paramString, vSConfig1);
    } 
    return vSConfig1;
  }
  
  private void preInitPublicPath(String paramString) {
    new File(paramString, "DCIM");
    String[] arrayOfString = sPublicDirs;
    int i = arrayOfString.length;
    for (byte b = 0; b < i; b++) {
      File file = new File(paramString, arrayOfString[b]);
      if (!file.exists())
        file.mkdirs(); 
    } 
  }
  
  SparseArray<HashMap<String, VSConfig>> getConfigs() {
    return this.mConfigs;
  }
  
  public String getVirtualStorage(String paramString, int paramInt) {
    checkUserId(paramInt);
    synchronized (this.mConfigs) {
      paramString = (getOrCreateVSConfigLocked(paramString, paramInt)).vsPath;
      return paramString;
    } 
  }
  
  public boolean isVirtualStorageEnable(String paramString, int paramInt) {
    checkUserId(paramInt);
    synchronized (this.mConfigs) {
      return (getOrCreateVSConfigLocked(paramString, paramInt)).enable;
    } 
  }
  
  public void setVirtualStorage(String paramString1, int paramInt, String paramString2) {
    checkUserId(paramInt);
    synchronized (this.mConfigs) {
      (getOrCreateVSConfigLocked(paramString1, paramInt)).vsPath = paramString2;
      this.mLayer.save();
      preInitPublicPath(paramString2);
      return;
    } 
  }
  
  public void setVirtualStorageState(String paramString, int paramInt, boolean paramBoolean) {
    checkUserId(paramInt);
    synchronized (this.mConfigs) {
      (getOrCreateVSConfigLocked(paramString, paramInt)).enable = paramBoolean;
      this.mLayer.save();
      return;
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\vs\VirtualStorageService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */