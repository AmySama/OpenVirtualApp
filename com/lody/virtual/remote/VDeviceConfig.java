package com.lody.virtual.remote;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.lody.virtual.os.VEnvironment;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class VDeviceConfig implements Parcelable {
  public static final Parcelable.Creator<VDeviceConfig> CREATOR;
  
  public static final int VERSION = 3;
  
  private static final UsedDeviceInfoPool mPool = new UsedDeviceInfoPool();
  
  public String androidId;
  
  public String bluetoothMac;
  
  public final Map<String, String> buildProp;
  
  public String deviceId;
  
  public boolean enable;
  
  public String gmsAdId;
  
  public String iccId;
  
  public String serial;
  
  public String wifiMac;
  
  static {
    CREATOR = new Parcelable.Creator<VDeviceConfig>() {
        public VDeviceConfig createFromParcel(Parcel param1Parcel) {
          return new VDeviceConfig(param1Parcel);
        }
        
        public VDeviceConfig[] newArray(int param1Int) {
          return new VDeviceConfig[param1Int];
        }
      };
  }
  
  public VDeviceConfig() {
    this.buildProp = new HashMap<String, String>();
  }
  
  public VDeviceConfig(Parcel paramParcel) {
    boolean bool;
    this.buildProp = new HashMap<String, String>();
    byte b = paramParcel.readByte();
    byte b1 = 0;
    if (b != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    this.enable = bool;
    this.deviceId = paramParcel.readString();
    this.androidId = paramParcel.readString();
    this.wifiMac = paramParcel.readString();
    this.bluetoothMac = paramParcel.readString();
    this.iccId = paramParcel.readString();
    this.serial = paramParcel.readString();
    this.gmsAdId = paramParcel.readString();
    int i = paramParcel.readInt();
    while (b1 < i) {
      String str1 = paramParcel.readString();
      String str2 = paramParcel.readString();
      this.buildProp.put(str1, str2);
      b1++;
    } 
  }
  
  public static void addToPool(VDeviceConfig paramVDeviceConfig) {
    mPool.deviceIds.add(paramVDeviceConfig.deviceId);
    mPool.androidIds.add(paramVDeviceConfig.androidId);
    mPool.wifiMacs.add(paramVDeviceConfig.wifiMac);
    mPool.bluetoothMacs.add(paramVDeviceConfig.bluetoothMac);
    mPool.iccIds.add(paramVDeviceConfig.iccId);
  }
  
  public static String generate10(long paramLong, int paramInt) {
    Random random = new Random(paramLong);
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b = 0; b < paramInt; b++)
      stringBuilder.append(random.nextInt(10)); 
    return stringBuilder.toString();
  }
  
  public static String generateDeviceId() {
    return generate10(System.currentTimeMillis(), 15);
  }
  
  public static String generateHex(long paramLong, int paramInt) {
    Random random = new Random(paramLong);
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b = 0; b < paramInt; b++) {
      int i = random.nextInt(16);
      if (i < 10) {
        stringBuilder.append(i);
      } else {
        stringBuilder.append((char)(i - 10 + 97));
      } 
    } 
    return stringBuilder.toString();
  }
  
  private static String generateMac() {
    Random random = new Random();
    StringBuilder stringBuilder = new StringBuilder();
    int i = 1;
    int j = 0;
    while (j < 12) {
      int k = random.nextInt(16);
      if (k < 10) {
        stringBuilder.append(k);
      } else {
        stringBuilder.append((char)(k + 87));
      } 
      k = i;
      if (j == i) {
        k = i;
        if (j != 11) {
          stringBuilder.append(":");
          k = i + 2;
        } 
      } 
      j++;
      i = k;
    } 
    return stringBuilder.toString();
  }
  
  private static String generateSerial() {
    String str;
    if (Build.SERIAL == null || Build.SERIAL.length() <= 0) {
      str = "0123456789ABCDEF";
    } else {
      str = Build.SERIAL;
    } 
    ArrayList<Character> arrayList = new ArrayList();
    char[] arrayOfChar = str.toCharArray();
    int i = arrayOfChar.length;
    for (byte b = 0; b < i; b++)
      arrayList.add(Character.valueOf(arrayOfChar[b])); 
    Collections.shuffle(arrayList);
    StringBuilder stringBuilder = new StringBuilder();
    Iterator<Character> iterator = arrayList.iterator();
    while (iterator.hasNext())
      stringBuilder.append(((Character)iterator.next()).charValue()); 
    return stringBuilder.toString();
  }
  
  public static VDeviceConfig random() {
    String str;
    VDeviceConfig vDeviceConfig = new VDeviceConfig();
    do {
      str = generateDeviceId();
      vDeviceConfig.deviceId = str;
    } while (mPool.deviceIds.contains(str));
    do {
      str = generateHex(System.currentTimeMillis(), 16);
      vDeviceConfig.androidId = str;
    } while (mPool.androidIds.contains(str));
    do {
      str = generateMac();
      vDeviceConfig.wifiMac = str;
    } while (mPool.wifiMacs.contains(str));
    do {
      str = generateMac();
      vDeviceConfig.bluetoothMac = str;
    } while (mPool.bluetoothMacs.contains(str));
    while (true) {
      str = generate10(System.currentTimeMillis(), 20);
      vDeviceConfig.iccId = str;
      if (!mPool.iccIds.contains(str)) {
        vDeviceConfig.serial = generateSerial();
        addToPool(vDeviceConfig);
        return vDeviceConfig;
      } 
    } 
  }
  
  public void clear() {
    this.deviceId = null;
    this.androidId = null;
    this.wifiMac = null;
    this.bluetoothMac = null;
    this.iccId = null;
    this.serial = null;
    this.gmsAdId = null;
  }
  
  public int describeContents() {
    return 0;
  }
  
  public String getProp(String paramString) {
    return this.buildProp.get(paramString);
  }
  
  public File getWifiFile(int paramInt, boolean paramBoolean) {
    if (TextUtils.isEmpty(this.wifiMac))
      return null; 
    File file = VEnvironment.getWifiMacFile(paramInt, paramBoolean);
    if (!file.exists())
      try {
        RandomAccessFile randomAccessFile = new RandomAccessFile();
        this(file, "rws");
        StringBuilder stringBuilder = new StringBuilder();
        this();
        stringBuilder.append(this.wifiMac);
        stringBuilder.append("\n");
        randomAccessFile.write(stringBuilder.toString().getBytes());
        randomAccessFile.close();
      } catch (IOException iOException) {
        iOException.printStackTrace();
      }  
    return file;
  }
  
  public void setProp(String paramString1, String paramString2) {
    this.buildProp.put(paramString1, paramString2);
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeByte(this.enable);
    paramParcel.writeString(this.deviceId);
    paramParcel.writeString(this.androidId);
    paramParcel.writeString(this.wifiMac);
    paramParcel.writeString(this.bluetoothMac);
    paramParcel.writeString(this.iccId);
    paramParcel.writeString(this.serial);
    paramParcel.writeString(this.gmsAdId);
    paramParcel.writeInt(this.buildProp.size());
    for (Map.Entry<String, String> entry : this.buildProp.entrySet()) {
      paramParcel.writeString((String)entry.getKey());
      paramParcel.writeString((String)entry.getValue());
    } 
  }
  
  private static final class UsedDeviceInfoPool {
    final List<String> androidIds = new ArrayList<String>();
    
    final List<String> bluetoothMacs = new ArrayList<String>();
    
    final List<String> deviceIds = new ArrayList<String>();
    
    final List<String> iccIds = new ArrayList<String>();
    
    final List<String> wifiMacs = new ArrayList<String>();
    
    private UsedDeviceInfoPool() {}
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\remote\VDeviceConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */