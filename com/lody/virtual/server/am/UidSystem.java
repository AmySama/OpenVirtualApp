package com.lody.virtual.server.am;

import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.helper.utils.FileUtils;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.os.VEnvironment;
import com.lody.virtual.server.pm.parser.VPackage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class UidSystem {
  private static final String TAG = UidSystem.class.getSimpleName();
  
  private int mFreeUid = 10000;
  
  private final HashMap<String, Integer> mSharedUserIdMap = new HashMap<String, Integer>();
  
  private boolean loadUidList(File paramFile) {
    if (!paramFile.exists())
      return false; 
    try {
      ObjectInputStream objectInputStream = new ObjectInputStream();
      FileInputStream fileInputStream = new FileInputStream();
      this(paramFile);
      this(fileInputStream);
      this.mFreeUid = objectInputStream.readInt();
      HashMap<? extends String, ? extends Integer> hashMap = (HashMap)objectInputStream.readObject();
      this.mSharedUserIdMap.putAll(hashMap);
      return true;
    } finally {
      paramFile = null;
    } 
  }
  
  private void save() {
    File file1 = VEnvironment.getUidListFile();
    File file2 = VEnvironment.getBakUidListFile();
    if (file1.exists()) {
      if (file2.exists() && !file2.delete()) {
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Warning: Unable to delete the expired file --\n ");
        stringBuilder.append(file2.getPath());
        VLog.w(str, stringBuilder.toString(), new Object[0]);
      } 
      try {
        FileUtils.copyFile(file1, file2);
      } catch (IOException iOException) {
        iOException.printStackTrace();
      } 
    } 
    try {
      ObjectOutputStream objectOutputStream = new ObjectOutputStream();
      FileOutputStream fileOutputStream = new FileOutputStream();
      this(file1);
      this(fileOutputStream);
      objectOutputStream.writeInt(this.mFreeUid);
      objectOutputStream.writeObject(this.mSharedUserIdMap);
      objectOutputStream.close();
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
  }
  
  public int getOrCreateUid(VPackage paramVPackage) {
    synchronized (this.mSharedUserIdMap) {
      String str1 = paramVPackage.mSharedUserId;
      String str2 = str1;
      if (str1 == null)
        str2 = paramVPackage.packageName; 
      Integer integer = this.mSharedUserIdMap.get(str2);
      if (integer != null)
        return integer.intValue(); 
      int j = this.mFreeUid + 1;
      this.mFreeUid = j;
      int i = j;
      if (j == VirtualCore.get().myUid()) {
        i = this.mFreeUid + 1;
        this.mFreeUid = i;
      } 
      this.mSharedUserIdMap.put(str2, Integer.valueOf(i));
      save();
      return i;
    } 
  }
  
  public int getUid(String paramString) {
    synchronized (this.mSharedUserIdMap) {
      Integer integer = this.mSharedUserIdMap.get(paramString);
      if (integer != null)
        return integer.intValue(); 
      return -1;
    } 
  }
  
  public void initUidList() {
    this.mSharedUserIdMap.clear();
    if (!loadUidList(VEnvironment.getUidListFile()))
      loadUidList(VEnvironment.getBakUidListFile()); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\am\UidSystem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */