package mirror.android.os;

import android.os.Parcel;
import mirror.RefClass;
import mirror.RefObject;

public class BaseBundle {
  public static Class<?> TYPE = RefClass.load(BaseBundle.class, "android.os.BaseBundle");
  
  public static RefObject<Parcel> mParcelledData;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\os\BaseBundle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */