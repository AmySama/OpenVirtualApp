package android.content;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;

public class SyncStatusInfo implements Parcelable {
  public static final Parcelable.Creator<SyncStatusInfo> CREATOR = new Parcelable.Creator<SyncStatusInfo>() {
      public SyncStatusInfo createFromParcel(Parcel param1Parcel) {
        return new SyncStatusInfo(param1Parcel);
      }
      
      public SyncStatusInfo[] newArray(int param1Int) {
        return new SyncStatusInfo[param1Int];
      }
    };
  
  private static final String TAG = "Sync";
  
  static final int VERSION = 2;
  
  public final int authorityId;
  
  public long initialFailureTime;
  
  public boolean initialize;
  
  public String lastFailureMesg;
  
  public int lastFailureSource;
  
  public long lastFailureTime;
  
  public int lastSuccessSource;
  
  public long lastSuccessTime;
  
  public int numSourceLocal;
  
  public int numSourcePeriodic;
  
  public int numSourcePoll;
  
  public int numSourceServer;
  
  public int numSourceUser;
  
  public int numSyncs;
  
  public boolean pending;
  
  private ArrayList<Long> periodicSyncTimes;
  
  public long totalElapsedTime;
  
  public SyncStatusInfo(int paramInt) {
    this.authorityId = paramInt;
  }
  
  public SyncStatusInfo(SyncStatusInfo paramSyncStatusInfo) {
    this.authorityId = paramSyncStatusInfo.authorityId;
    this.totalElapsedTime = paramSyncStatusInfo.totalElapsedTime;
    this.numSyncs = paramSyncStatusInfo.numSyncs;
    this.numSourcePoll = paramSyncStatusInfo.numSourcePoll;
    this.numSourceServer = paramSyncStatusInfo.numSourceServer;
    this.numSourceLocal = paramSyncStatusInfo.numSourceLocal;
    this.numSourceUser = paramSyncStatusInfo.numSourceUser;
    this.numSourcePeriodic = paramSyncStatusInfo.numSourcePeriodic;
    this.lastSuccessTime = paramSyncStatusInfo.lastSuccessTime;
    this.lastSuccessSource = paramSyncStatusInfo.lastSuccessSource;
    this.lastFailureTime = paramSyncStatusInfo.lastFailureTime;
    this.lastFailureSource = paramSyncStatusInfo.lastFailureSource;
    this.lastFailureMesg = paramSyncStatusInfo.lastFailureMesg;
    this.initialFailureTime = paramSyncStatusInfo.initialFailureTime;
    this.pending = paramSyncStatusInfo.pending;
    this.initialize = paramSyncStatusInfo.initialize;
    if (paramSyncStatusInfo.periodicSyncTimes != null)
      this.periodicSyncTimes = new ArrayList<Long>(paramSyncStatusInfo.periodicSyncTimes); 
  }
  
  public SyncStatusInfo(Parcel paramParcel) {
    boolean bool;
    int i = paramParcel.readInt();
    if (i != 2 && i != 1) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Unknown version: ");
      stringBuilder.append(i);
      Log.w("SyncStatusInfo", stringBuilder.toString());
    } 
    this.authorityId = paramParcel.readInt();
    this.totalElapsedTime = paramParcel.readLong();
    this.numSyncs = paramParcel.readInt();
    this.numSourcePoll = paramParcel.readInt();
    this.numSourceServer = paramParcel.readInt();
    this.numSourceLocal = paramParcel.readInt();
    this.numSourceUser = paramParcel.readInt();
    this.lastSuccessTime = paramParcel.readLong();
    this.lastSuccessSource = paramParcel.readInt();
    this.lastFailureTime = paramParcel.readLong();
    this.lastFailureSource = paramParcel.readInt();
    this.lastFailureMesg = paramParcel.readString();
    this.initialFailureTime = paramParcel.readLong();
    int j = paramParcel.readInt();
    byte b = 0;
    if (j != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    this.pending = bool;
    if (paramParcel.readInt() != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    this.initialize = bool;
    if (i == 1) {
      this.periodicSyncTimes = null;
    } else {
      j = paramParcel.readInt();
      if (j < 0) {
        this.periodicSyncTimes = null;
      } else {
        this.periodicSyncTimes = new ArrayList<Long>();
        while (b < j) {
          this.periodicSyncTimes.add(Long.valueOf(paramParcel.readLong()));
          b++;
        } 
      } 
    } 
  }
  
  private void ensurePeriodicSyncTimeSize(int paramInt) {
    if (this.periodicSyncTimes == null)
      this.periodicSyncTimes = new ArrayList<Long>(0); 
    int i = paramInt + 1;
    if (this.periodicSyncTimes.size() < i)
      for (paramInt = this.periodicSyncTimes.size(); paramInt < i; paramInt++)
        this.periodicSyncTimes.add(Long.valueOf(0L));  
  }
  
  public int describeContents() {
    return 0;
  }
  
  public int getLastFailureMesgAsInt(int paramInt) {
    return 0;
  }
  
  public long getPeriodicSyncTime(int paramInt) {
    ArrayList<Long> arrayList = this.periodicSyncTimes;
    return (arrayList != null && paramInt < arrayList.size()) ? ((Long)this.periodicSyncTimes.get(paramInt)).longValue() : 0L;
  }
  
  public void removePeriodicSyncTime(int paramInt) {
    ArrayList<Long> arrayList = this.periodicSyncTimes;
    if (arrayList != null && paramInt < arrayList.size())
      this.periodicSyncTimes.remove(paramInt); 
  }
  
  public void setPeriodicSyncTime(int paramInt, long paramLong) {
    ensurePeriodicSyncTimeSize(paramInt);
    this.periodicSyncTimes.set(paramInt, Long.valueOf(paramLong));
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) {
    paramParcel.writeInt(2);
    paramParcel.writeInt(this.authorityId);
    paramParcel.writeLong(this.totalElapsedTime);
    paramParcel.writeInt(this.numSyncs);
    paramParcel.writeInt(this.numSourcePoll);
    paramParcel.writeInt(this.numSourceServer);
    paramParcel.writeInt(this.numSourceLocal);
    paramParcel.writeInt(this.numSourceUser);
    paramParcel.writeLong(this.lastSuccessTime);
    paramParcel.writeInt(this.lastSuccessSource);
    paramParcel.writeLong(this.lastFailureTime);
    paramParcel.writeInt(this.lastFailureSource);
    paramParcel.writeString(this.lastFailureMesg);
    paramParcel.writeLong(this.initialFailureTime);
    paramParcel.writeInt(this.pending);
    paramParcel.writeInt(this.initialize);
    ArrayList<Long> arrayList = this.periodicSyncTimes;
    if (arrayList != null) {
      paramParcel.writeInt(arrayList.size());
      Iterator<Long> iterator = this.periodicSyncTimes.iterator();
      while (iterator.hasNext())
        paramParcel.writeLong(((Long)iterator.next()).longValue()); 
    } else {
      paramParcel.writeInt(-1);
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\content\SyncStatusInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */