package com.lody.virtual.server.content;

import android.accounts.Account;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VSyncRecord {
  public Map<SyncExtras, PeriodicSyncConfig> configs = new HashMap<SyncExtras, PeriodicSyncConfig>();
  
  public List<SyncExtras> extras = new ArrayList<SyncExtras>();
  
  public boolean isPeriodic = false;
  
  public SyncRecordKey key;
  
  public int syncable = -1;
  
  public int userId;
  
  public VSyncRecord(int paramInt, Account paramAccount, String paramString) {
    this.userId = paramInt;
    this.key = new SyncRecordKey(paramAccount, paramString);
  }
  
  public static boolean equals(Bundle paramBundle1, Bundle paramBundle2, boolean paramBoolean) {
    if (paramBundle1 == paramBundle2)
      return true; 
    if (paramBoolean && paramBundle1.size() != paramBundle2.size())
      return false; 
    Bundle bundle1 = paramBundle1;
    Bundle bundle2 = paramBundle2;
    if (paramBundle1.size() <= paramBundle2.size()) {
      bundle2 = paramBundle1;
      bundle1 = paramBundle2;
    } 
    for (String str : bundle1.keySet()) {
      if (paramBoolean || !isIgnoredKey(str)) {
        if (!bundle2.containsKey(str))
          return false; 
        if (!bundle1.get(str).equals(bundle2.get(str)))
          return false; 
      } 
    } 
    return true;
  }
  
  private static boolean isIgnoredKey(String paramString) {
    return (paramString.equals("expedited") || paramString.equals("ignore_settings") || paramString.equals("ignore_backoff") || paramString.equals("do_not_retry") || paramString.equals("force") || paramString.equals("upload") || paramString.equals("deletions_override") || paramString.equals("discard_deletions") || paramString.equals("expected_upload") || paramString.equals("expected_download") || paramString.equals("sync_priority") || paramString.equals("allow_metered") || paramString.equals("initialize"));
  }
  
  static class PeriodicSyncConfig implements Parcelable {
    public static final Parcelable.Creator<PeriodicSyncConfig> CREATOR = new Parcelable.Creator<PeriodicSyncConfig>() {
        public VSyncRecord.PeriodicSyncConfig createFromParcel(Parcel param2Parcel) {
          return new VSyncRecord.PeriodicSyncConfig(param2Parcel);
        }
        
        public VSyncRecord.PeriodicSyncConfig[] newArray(int param2Int) {
          return new VSyncRecord.PeriodicSyncConfig[param2Int];
        }
      };
    
    long syncRunTimeSecs;
    
    public PeriodicSyncConfig(long param1Long) {
      this.syncRunTimeSecs = param1Long;
    }
    
    PeriodicSyncConfig(Parcel param1Parcel) {
      this.syncRunTimeSecs = param1Parcel.readLong();
    }
    
    public int describeContents() {
      return 0;
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      param1Parcel.writeLong(this.syncRunTimeSecs);
    }
  }
  
  class null implements Parcelable.Creator<PeriodicSyncConfig> {
    public VSyncRecord.PeriodicSyncConfig createFromParcel(Parcel param1Parcel) {
      return new VSyncRecord.PeriodicSyncConfig(param1Parcel);
    }
    
    public VSyncRecord.PeriodicSyncConfig[] newArray(int param1Int) {
      return new VSyncRecord.PeriodicSyncConfig[param1Int];
    }
  }
  
  public static class SyncExtras implements Parcelable {
    public static final Parcelable.Creator<SyncExtras> CREATOR = new Parcelable.Creator<SyncExtras>() {
        public VSyncRecord.SyncExtras createFromParcel(Parcel param2Parcel) {
          return new VSyncRecord.SyncExtras(param2Parcel);
        }
        
        public VSyncRecord.SyncExtras[] newArray(int param2Int) {
          return new VSyncRecord.SyncExtras[param2Int];
        }
      };
    
    Bundle extras;
    
    public SyncExtras(Bundle param1Bundle) {
      this.extras = param1Bundle;
    }
    
    SyncExtras(Parcel param1Parcel) {
      this.extras = param1Parcel.readBundle(getClass().getClassLoader());
    }
    
    public int describeContents() {
      return 0;
    }
    
    public boolean equals(Object param1Object) {
      return VSyncRecord.equals(this.extras, ((SyncExtras)param1Object).extras, false);
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      param1Parcel.writeBundle(this.extras);
    }
  }
  
  class null implements Parcelable.Creator<SyncExtras> {
    public VSyncRecord.SyncExtras createFromParcel(Parcel param1Parcel) {
      return new VSyncRecord.SyncExtras(param1Parcel);
    }
    
    public VSyncRecord.SyncExtras[] newArray(int param1Int) {
      return new VSyncRecord.SyncExtras[param1Int];
    }
  }
  
  public static class SyncRecordKey implements Parcelable {
    public static final Parcelable.Creator<SyncRecordKey> CREATOR = new Parcelable.Creator<SyncRecordKey>() {
        public VSyncRecord.SyncRecordKey createFromParcel(Parcel param2Parcel) {
          return new VSyncRecord.SyncRecordKey(param2Parcel);
        }
        
        public VSyncRecord.SyncRecordKey[] newArray(int param2Int) {
          return new VSyncRecord.SyncRecordKey[param2Int];
        }
      };
    
    Account account;
    
    String authority;
    
    SyncRecordKey(Account param1Account, String param1String) {
      this.account = param1Account;
      this.authority = param1String;
    }
    
    SyncRecordKey(Parcel param1Parcel) {
      this.account = (Account)param1Parcel.readParcelable(Account.class.getClassLoader());
      this.authority = param1Parcel.readString();
    }
    
    public int describeContents() {
      return 0;
    }
    
    public boolean equals(Object param1Object) {
      boolean bool = true;
      if (this == param1Object)
        return true; 
      if (param1Object == null || getClass() != param1Object.getClass())
        return false; 
      SyncRecordKey syncRecordKey = (SyncRecordKey)param1Object;
      param1Object = this.account;
      if ((param1Object != null) ? !param1Object.equals(syncRecordKey.account) : (syncRecordKey.account != null))
        return false; 
      param1Object = this.authority;
      String str = syncRecordKey.authority;
      if (param1Object != null) {
        bool = param1Object.equals(str);
      } else if (str != null) {
        bool = false;
      } 
      return bool;
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      param1Parcel.writeParcelable((Parcelable)this.account, param1Int);
      param1Parcel.writeString(this.authority);
    }
  }
  
  class null implements Parcelable.Creator<SyncRecordKey> {
    public VSyncRecord.SyncRecordKey createFromParcel(Parcel param1Parcel) {
      return new VSyncRecord.SyncRecordKey(param1Parcel);
    }
    
    public VSyncRecord.SyncRecordKey[] newArray(int param1Int) {
      return new VSyncRecord.SyncRecordKey[param1Int];
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\content\VSyncRecord.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */