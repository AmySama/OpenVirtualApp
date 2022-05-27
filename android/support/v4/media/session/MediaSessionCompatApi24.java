package android.support.v4.media.session;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;

class MediaSessionCompatApi24 {
  private static final String TAG = "MediaSessionCompatApi24";
  
  public static Object createCallback(Callback paramCallback) {
    return new CallbackProxy<Callback>(paramCallback);
  }
  
  public static String getCallingPackage(Object paramObject) {
    paramObject = paramObject;
    try {
      return (String)paramObject.getClass().getMethod("getCallingPackage", new Class[0]).invoke(paramObject, new Object[0]);
    } catch (NoSuchMethodException noSuchMethodException) {
    
    } catch (InvocationTargetException invocationTargetException) {
    
    } catch (IllegalAccessException illegalAccessException) {}
    Log.e("MediaSessionCompatApi24", "Cannot execute MediaSession.getCallingPackage()", illegalAccessException);
    return null;
  }
  
  public static interface Callback extends MediaSessionCompatApi23.Callback {
    void onPrepare();
    
    void onPrepareFromMediaId(String param1String, Bundle param1Bundle);
    
    void onPrepareFromSearch(String param1String, Bundle param1Bundle);
    
    void onPrepareFromUri(Uri param1Uri, Bundle param1Bundle);
  }
  
  static class CallbackProxy<T extends Callback> extends MediaSessionCompatApi23.CallbackProxy<T> {
    public CallbackProxy(T param1T) {
      super(param1T);
    }
    
    public void onPrepare() {
      ((MediaSessionCompatApi24.Callback)this.mCallback).onPrepare();
    }
    
    public void onPrepareFromMediaId(String param1String, Bundle param1Bundle) {
      ((MediaSessionCompatApi24.Callback)this.mCallback).onPrepareFromMediaId(param1String, param1Bundle);
    }
    
    public void onPrepareFromSearch(String param1String, Bundle param1Bundle) {
      ((MediaSessionCompatApi24.Callback)this.mCallback).onPrepareFromSearch(param1String, param1Bundle);
    }
    
    public void onPrepareFromUri(Uri param1Uri, Bundle param1Bundle) {
      ((MediaSessionCompatApi24.Callback)this.mCallback).onPrepareFromUri(param1Uri, param1Bundle);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\media\session\MediaSessionCompatApi24.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */