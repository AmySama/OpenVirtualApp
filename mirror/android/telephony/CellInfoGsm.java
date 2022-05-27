package mirror.android.telephony;

import android.telephony.CellIdentityGsm;
import android.telephony.CellSignalStrengthGsm;
import mirror.RefClass;
import mirror.RefConstructor;
import mirror.RefObject;

public class CellInfoGsm {
  public static Class<?> TYPE = RefClass.load(CellInfoGsm.class, android.telephony.CellInfoGsm.class);
  
  public static RefConstructor<android.telephony.CellInfoGsm> ctor;
  
  public static RefObject<CellIdentityGsm> mCellIdentityGsm;
  
  public static RefObject<CellSignalStrengthGsm> mCellSignalStrengthGsm;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\telephony\CellInfoGsm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */