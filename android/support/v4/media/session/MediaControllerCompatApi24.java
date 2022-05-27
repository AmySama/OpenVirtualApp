package android.support.v4.media.session;

import android.media.session.MediaController;
import android.net.Uri;
import android.os.Bundle;

class MediaControllerCompatApi24 {
  public static class TransportControls extends MediaControllerCompatApi23.TransportControls {
    public static void prepare(Object param1Object) {
      ((MediaController.TransportControls)param1Object).prepare();
    }
    
    public static void prepareFromMediaId(Object param1Object, String param1String, Bundle param1Bundle) {
      ((MediaController.TransportControls)param1Object).prepareFromMediaId(param1String, param1Bundle);
    }
    
    public static void prepareFromSearch(Object param1Object, String param1String, Bundle param1Bundle) {
      ((MediaController.TransportControls)param1Object).prepareFromSearch(param1String, param1Bundle);
    }
    
    public static void prepareFromUri(Object param1Object, Uri param1Uri, Bundle param1Bundle) {
      ((MediaController.TransportControls)param1Object).prepareFromUri(param1Uri, param1Bundle);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\media\session\MediaControllerCompatApi24.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */