package mirror.android.location;

import mirror.RefClass;
import mirror.RefObject;

public class GeocoderParams {
  public static Class<?> TYPE = RefClass.load(GeocoderParams.class, "android.location.GeocoderParams");
  
  public static RefObject<String> mPackageName;
  
  public static RefObject<Integer> mUid;
  
  public static String mPackageName(Object paramObject) {
    RefObject<String> refObject = mPackageName;
    return (refObject != null) ? (String)refObject.get(paramObject) : null;
  }
  
  public static void mPackageName(Object paramObject, String paramString) {
    RefObject<String> refObject = mPackageName;
    if (refObject != null)
      refObject.set(paramObject, paramString); 
  }
  
  public static int mUid(Object paramObject) {
    RefObject<Integer> refObject = mUid;
    return (refObject != null) ? ((Integer)refObject.get(paramObject)).intValue() : 0;
  }
  
  public static void mUid(Object paramObject, int paramInt) {
    RefObject<Integer> refObject = mUid;
    if (refObject != null)
      refObject.set(paramObject, Integer.valueOf(paramInt)); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\location\GeocoderParams.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */