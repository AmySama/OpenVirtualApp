package mirror.android.content;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Log;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefMethod;
import mirror.RefObject;

public class AttributionSource {
  private static final String TAG = "AttributionSource";
  
  public static Class<?> TYPE = RefClass.load(AttributionSource.class, "android.content.AttributionSource");
  
  public static Class<?> TYPE_COMP = RefClass.load(AttributionSource.class, "android.content.AttributionSource$ap");
  
  @MethodParams({Object.class})
  public static RefMethod<Boolean> equals;
  
  public static RefMethod<String> getAttributionTag;
  
  public static RefMethod<String> getPackageName;
  
  public static RefMethod<IBinder> getToken;
  
  public static RefObject<Object> mAttributionSourceState;
  
  @MethodParams({Binder.class})
  public static RefMethod<Parcelable> withToken;
  
  public static boolean equals(Object<Boolean> paramObject1, Object paramObject2) {
    paramObject1 = (Object<Boolean>)equals;
    return (paramObject1 == null) ? false : ((Boolean)paramObject1.call(paramObject1, new Object[] { paramObject2 })).booleanValue();
  }
  
  public static String getAttributionTag(Object paramObject) {
    RefMethod<String> refMethod = getAttributionTag;
    return (refMethod != null) ? (String)refMethod.call(paramObject, new Object[0]) : null;
  }
  
  public static String getPackageName(Object paramObject) {
    RefMethod<String> refMethod = getPackageName;
    return (refMethod != null) ? (String)refMethod.call(paramObject, new Object[0]) : null;
  }
  
  public static IBinder getToken(Object paramObject) {
    RefMethod<IBinder> refMethod = getToken;
    return (refMethod != null) ? (IBinder)refMethod.call(paramObject, new Object[0]) : null;
  }
  
  public static Object mAttributionSourceState(Object paramObject) {
    RefObject<Object> refObject = mAttributionSourceState;
    return (refObject != null) ? refObject.get(paramObject) : null;
  }
  
  public static void mAttributionSourceState(Object paramObject1, Object paramObject2) {
    RefObject<Object> refObject = mAttributionSourceState;
    if (refObject != null)
      refObject.set(paramObject1, paramObject2); 
  }
  
  public static Parcelable newInstance(Object paramObject) {
    if (paramObject != null) {
      Parcelable parcelable = withToken(paramObject, null);
      if (parcelable != null) {
        Object object = mAttributionSourceState(parcelable);
        if (object != null) {
          AttributionSourceState.token(object, getToken(paramObject));
          paramObject = mAttributionSourceState(paramObject);
          if (paramObject != null)
            AttributionSourceState.next(object, AttributionSourceState.next(paramObject)); 
          return parcelable;
        } 
      } 
    } 
    Log.w("AttributionSource", "newInstance gamb failed,return null");
    return null;
  }
  
  public static Parcelable withToken(Object paramObject, Binder paramBinder) {
    RefMethod<Parcelable> refMethod = withToken;
    return (refMethod == null) ? null : (Parcelable)refMethod.call(paramObject, new Object[] { paramBinder });
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\content\AttributionSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */