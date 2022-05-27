package mirror.android.telephony;

import android.telephony.CellIdentityCdma;
import android.telephony.CellSignalStrengthCdma;
import mirror.RefClass;
import mirror.RefConstructor;
import mirror.RefObject;

public class CellInfoCdma {
  public static Class<?> TYPE = RefClass.load(CellInfoCdma.class, android.telephony.CellInfoCdma.class);
  
  public static RefConstructor<android.telephony.CellInfoCdma> ctor;
  
  public static RefObject<CellIdentityCdma> mCellIdentityCdma;
  
  public static RefObject<CellSignalStrengthCdma> mCellSignalStrengthCdma;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\telephony\CellInfoCdma.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */