package mirror.android.content;

import android.os.IBinder;
import android.os.Parcelable;
import android.os.Process;
import mirror.RefClass;
import mirror.RefObject;

public class AttributionSourceState {
  public static Class<?> TYPE = RefClass.load(AttributionSourceState.class, "android.content.AttributionSourceState");
  
  public static RefObject<Parcelable[]> next;
  
  public static RefObject<String> packageName;
  
  public static RefObject<IBinder> token;
  
  public static RefObject<Integer> uid;
  
  public static void next(Object paramObject, Parcelable[] paramArrayOfParcelable) {
    RefObject<Parcelable[]> refObject = next;
    if (refObject != null)
      refObject.set(paramObject, paramArrayOfParcelable); 
  }
  
  public static Parcelable[] next(Object paramObject) {
    RefObject<Parcelable[]> refObject = next;
    return (refObject != null) ? (Parcelable[])refObject.get(paramObject) : null;
  }
  
  public static String packageName(Object paramObject) {
    RefObject<String> refObject = packageName;
    return (refObject != null) ? (String)refObject.get(paramObject) : null;
  }
  
  public static void packageName(Object paramObject, String paramString) {
    RefObject<String> refObject = packageName;
    if (refObject != null)
      refObject.set(paramObject, paramString); 
  }
  
  public static IBinder token(Object paramObject) {
    RefObject<IBinder> refObject = token;
    return (refObject != null) ? (IBinder)refObject.get(paramObject) : null;
  }
  
  public static void token(Object paramObject, IBinder paramIBinder) {
    RefObject<IBinder> refObject = token;
    if (refObject != null)
      refObject.set(paramObject, paramIBinder); 
  }
  
  public static int uid(Object paramObject) {
    RefObject<Integer> refObject = uid;
    return (refObject != null) ? ((Integer)refObject.get(paramObject)).intValue() : Process.myUid();
  }
  
  public static void uid(Object paramObject, int paramInt) {
    RefObject<Integer> refObject = uid;
    if (refObject != null)
      refObject.set(paramObject, Integer.valueOf(paramInt)); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\content\AttributionSourceState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */