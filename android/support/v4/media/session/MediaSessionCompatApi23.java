package android.support.v4.media.session;

import android.net.Uri;
import android.os.Bundle;

class MediaSessionCompatApi23 {
  public static Object createCallback(Callback paramCallback) {
    return new CallbackProxy<Callback>(paramCallback);
  }
  
  public static interface Callback extends MediaSessionCompatApi21.Callback {
    void onPlayFromUri(Uri param1Uri, Bundle param1Bundle);
  }
  
  static class CallbackProxy<T extends Callback> extends MediaSessionCompatApi21.CallbackProxy<T> {
    public CallbackProxy(T param1T) {
      super(param1T);
    }
    
    public void onPlayFromUri(Uri param1Uri, Bundle param1Bundle) {
      ((MediaSessionCompatApi23.Callback)this.mCallback).onPlayFromUri(param1Uri, param1Bundle);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\media\session\MediaSessionCompatApi23.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */