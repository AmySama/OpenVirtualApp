package mirror.android.content;

import android.accounts.Account;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefConstructor;

public class SyncInfo {
  public static Class<?> TYPE = RefClass.load(SyncInfo.class, android.content.SyncInfo.class);
  
  @MethodParams({int.class, Account.class, String.class, long.class})
  public static RefConstructor<android.content.SyncInfo> ctor;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\content\SyncInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */