package mirror.android.os;

import mirror.RefClass;
import mirror.RefStaticInt;
import mirror.RefStaticMethod;

public class StrictMode {
  public static RefStaticInt DETECT_VM_FILE_URI_EXPOSURE;
  
  public static RefStaticInt PENALTY_DEATH_ON_FILE_URI_EXPOSURE;
  
  public static Class<?> TYPE = RefClass.load(StrictMode.class, "android.os.StrictMode");
  
  public static RefStaticMethod<Void> disableDeathOnFileUriExposure;
  
  public static RefStaticInt sVmPolicyMask;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\os\StrictMode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */