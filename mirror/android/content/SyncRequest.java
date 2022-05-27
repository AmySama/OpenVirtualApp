package mirror.android.content;

import android.accounts.Account;
import android.os.Bundle;
import mirror.RefBoolean;
import mirror.RefClass;
import mirror.RefLong;
import mirror.RefObject;

public class SyncRequest {
  public static Class<?> TYPE = RefClass.load(SyncRequest.class, android.content.SyncRequest.class);
  
  public static RefObject<Account> mAccountToSync;
  
  public static RefObject<String> mAuthority;
  
  public static RefObject<Bundle> mExtras;
  
  public static RefBoolean mIsPeriodic;
  
  public static RefLong mSyncFlexTimeSecs;
  
  public static RefLong mSyncRunTimeSecs;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\content\SyncRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */