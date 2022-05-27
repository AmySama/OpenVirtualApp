package mirror.android.content.pm;

import android.os.Parcelable;
import java.util.List;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefConstructor;
import mirror.RefMethod;
import mirror.RefStaticObject;

public class ParceledListSlice {
  public static RefStaticObject<Parcelable.Creator> CREATOR;
  
  public static Class<?> TYPE = RefClass.load(ParceledListSlice.class, "android.content.pm.ParceledListSlice");
  
  public static RefMethod<Boolean> append;
  
  public static RefConstructor<Parcelable> ctor;
  
  @MethodParams({List.class})
  public static RefConstructor<Parcelable> ctorQ;
  
  public static RefMethod<List<?>> getList;
  
  public static RefMethod<Boolean> isLastSlice;
  
  public static RefMethod<Parcelable> populateList;
  
  public static RefMethod<Void> setLastSlice;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\content\pm\ParceledListSlice.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */