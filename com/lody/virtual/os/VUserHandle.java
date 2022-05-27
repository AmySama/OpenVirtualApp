package com.lody.virtual.os;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import com.lody.virtual.client.VClient;
import com.lody.virtual.client.core.VirtualCore;
import java.io.PrintWriter;

public final class VUserHandle implements Parcelable {
  public static final VUserHandle ALL = new VUserHandle(-1);
  
  public static final Parcelable.Creator<VUserHandle> CREATOR;
  
  public static final VUserHandle CURRENT = new VUserHandle(-2);
  
  public static final VUserHandle CURRENT_OR_SELF = new VUserHandle(-3);
  
  public static final int FIRST_ISOLATED_UID = 99000;
  
  public static final int FIRST_SHARED_APPLICATION_GID = 50000;
  
  public static final int LAST_ISOLATED_UID = 99999;
  
  public static final int LAST_SHARED_APPLICATION_GID = 59999;
  
  public static final boolean MU_ENABLED = true;
  
  public static final VUserHandle OWNER = new VUserHandle(0);
  
  public static final int PER_USER_RANGE = 100000;
  
  public static final int USER_ALL = -1;
  
  public static final int USER_CURRENT = -2;
  
  public static final int USER_CURRENT_OR_SELF = -3;
  
  public static final int USER_NULL = -10000;
  
  public static final int USER_OWNER = 0;
  
  private static final SparseArray<VUserHandle> userHandles;
  
  final int mHandle;
  
  static {
    CREATOR = new Parcelable.Creator<VUserHandle>() {
        public VUserHandle createFromParcel(Parcel param1Parcel) {
          return new VUserHandle(param1Parcel);
        }
        
        public VUserHandle[] newArray(int param1Int) {
          return new VUserHandle[param1Int];
        }
      };
    userHandles = new SparseArray();
  }
  
  public VUserHandle(int paramInt) {
    this.mHandle = paramInt;
  }
  
  public VUserHandle(Parcel paramParcel) {
    this.mHandle = paramParcel.readInt();
  }
  
  public static boolean accept(int paramInt) {
    return (paramInt == -1 || paramInt == myUserId());
  }
  
  public static String formatUid(int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    formatUid(stringBuilder, paramInt);
    return stringBuilder.toString();
  }
  
  public static void formatUid(PrintWriter paramPrintWriter, int paramInt) {
    if (paramInt < 10000) {
      paramPrintWriter.print(paramInt);
    } else {
      paramPrintWriter.print('u');
      paramPrintWriter.print(getUserId(paramInt));
      paramInt = getAppId(paramInt);
      if (paramInt >= 99000 && paramInt <= 99999) {
        paramPrintWriter.print('i');
        paramPrintWriter.print(paramInt - 99000);
      } else if (paramInt >= 10000) {
        paramPrintWriter.print('a');
        paramPrintWriter.print(paramInt - 10000);
      } else {
        paramPrintWriter.print('s');
        paramPrintWriter.print(paramInt);
      } 
    } 
  }
  
  public static void formatUid(StringBuilder paramStringBuilder, int paramInt) {
    if (paramInt < 10000) {
      paramStringBuilder.append(paramInt);
    } else {
      paramStringBuilder.append('u');
      paramStringBuilder.append(getUserId(paramInt));
      paramInt = getAppId(paramInt);
      if (paramInt >= 99000 && paramInt <= 99999) {
        paramStringBuilder.append('i');
        paramStringBuilder.append(paramInt - 99000);
      } else if (paramInt >= 10000) {
        paramStringBuilder.append('a');
        paramStringBuilder.append(paramInt - 10000);
      } else {
        paramStringBuilder.append('s');
        paramStringBuilder.append(paramInt);
      } 
    } 
  }
  
  public static int getAppId(int paramInt) {
    return paramInt % 100000;
  }
  
  public static int getAppIdFromSharedAppGid(int paramInt) {
    int i = getAppId(paramInt);
    if (i >= 50000 && i <= 59999)
      return i + 10000 - 50000; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(Integer.toString(paramInt));
    stringBuilder.append(" is not a shared app gid");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public static VUserHandle getCallingUserHandle() {
    int i = getUserId(VBinder.getCallingUid());
    VUserHandle vUserHandle1 = (VUserHandle)userHandles.get(i);
    VUserHandle vUserHandle2 = vUserHandle1;
    if (vUserHandle1 == null) {
      vUserHandle2 = new VUserHandle(i);
      userHandles.put(i, vUserHandle2);
    } 
    return vUserHandle2;
  }
  
  public static int getCallingUserId() {
    return getUserId(VBinder.getCallingUid());
  }
  
  public static int getUid(int paramInt1, int paramInt2) {
    return paramInt1 * 100000 + paramInt2 % 100000;
  }
  
  public static int getUserId(int paramInt) {
    return (paramInt < 0) ? 0 : (paramInt / 100000);
  }
  
  public static boolean isApp(int paramInt) {
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (paramInt > 0) {
      paramInt = getAppId(paramInt);
      bool2 = bool1;
      if (paramInt >= 10000) {
        bool2 = bool1;
        if (paramInt <= 19999)
          bool2 = true; 
      } 
    } 
    return bool2;
  }
  
  public static final boolean isIsolated(int paramInt) {
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (paramInt > 0) {
      paramInt = getAppId(paramInt);
      bool2 = bool1;
      if (paramInt >= 99000) {
        bool2 = bool1;
        if (paramInt <= 99999)
          bool2 = true; 
      } 
    } 
    return bool2;
  }
  
  public static final boolean isSameApp(int paramInt1, int paramInt2) {
    boolean bool;
    if (getAppId(paramInt1) == getAppId(paramInt2)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isSameUser(int paramInt1, int paramInt2) {
    boolean bool;
    if (getUserId(paramInt1) == getUserId(paramInt2)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static int myAppId() {
    return getAppId(VClient.get().getVUid());
  }
  
  public static VUserHandle myUserHandle() {
    return new VUserHandle(myUserId());
  }
  
  public static int myUserId() {
    return getUserId(VClient.get().getVUid());
  }
  
  public static VUserHandle readFromParcel(Parcel paramParcel) {
    int i = paramParcel.readInt();
    if (i != -10000) {
      VUserHandle vUserHandle = new VUserHandle(i);
    } else {
      paramParcel = null;
    } 
    return (VUserHandle)paramParcel;
  }
  
  public static int realUserId() {
    return (Build.VERSION.SDK_INT < 17) ? 0 : getUserId(VirtualCore.get().myUid());
  }
  
  public static void writeToParcel(VUserHandle paramVUserHandle, Parcel paramParcel) {
    if (paramVUserHandle != null) {
      paramVUserHandle.writeToParcel(paramParcel, 0);
    } else {
      paramParcel.writeInt(-10000);
    } 
  }
  
  public int describeContents() {
    return 0;
  }
  
  public boolean equals(Object paramObject) {
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (paramObject != null)
      try {
        paramObject = paramObject;
        int i = this.mHandle;
        int j = ((VUserHandle)paramObject).mHandle;
        bool2 = bool1;
        if (i == j)
          bool2 = true; 
      } catch (ClassCastException classCastException) {
        bool2 = bool1;
      }  
    return bool2;
  }
  
  public int getIdentifier() {
    return this.mHandle;
  }
  
  public int hashCode() {
    return this.mHandle;
  }
  
  public final boolean isOwner() {
    return equals(OWNER);
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("VUserHandle{");
    stringBuilder.append(this.mHandle);
    stringBuilder.append("}");
    return stringBuilder.toString();
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeInt(this.mHandle);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\os\VUserHandle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */