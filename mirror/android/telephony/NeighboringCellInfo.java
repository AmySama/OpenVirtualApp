package mirror.android.telephony;

import mirror.RefClass;
import mirror.RefInt;

public class NeighboringCellInfo {
  public static Class<?> TYPE = RefClass.load(NeighboringCellInfo.class, android.telephony.NeighboringCellInfo.class);
  
  public static RefInt mCid;
  
  public static RefInt mLac;
  
  public static RefInt mRssi;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\telephony\NeighboringCellInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */