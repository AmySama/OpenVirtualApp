package mirror.android.app;

import android.content.Context;
import android.content.pm.PackageManager;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefMethod;
import mirror.RefObject;

public class ContextImpl {
  public static Class<?> TYPE = RefClass.load(ContextImpl.class, "android.app.ContextImpl");
  
  public static RefMethod<Object> getAttributionSource;
  
  public static RefMethod<Context> getReceiverRestrictedContext;
  
  public static RefObject<Object> mAttributionSource;
  
  @MethodParams({Context.class})
  public static RefObject<String> mBasePackageName;
  
  public static RefObject<Object> mPackageInfo;
  
  public static RefObject<PackageManager> mPackageManager;
  
  @MethodParams({Context.class})
  public static RefMethod<Void> setOuterContext;
  
  public static Object getAttributionSource(Object paramObject) {
    RefMethod<Object> refMethod = getAttributionSource;
    return (refMethod != null) ? refMethod.call(paramObject, new Object[0]) : null;
  }
  
  public static Object mAttributionSource(Object paramObject) {
    RefObject<Object> refObject = mAttributionSource;
    return (refObject != null) ? refObject.get(paramObject) : null;
  }
  
  public static void mAttributionSource(Object paramObject1, Object paramObject2) {
    RefObject<Object> refObject = mAttributionSource;
    if (refObject != null)
      refObject.set(paramObject1, paramObject2); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\app\ContextImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */