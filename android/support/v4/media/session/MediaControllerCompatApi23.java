package android.support.v4.media.session;

import android.media.session.MediaController;
import android.net.Uri;
import android.os.Bundle;

class MediaControllerCompatApi23 {
  public static class TransportControls extends MediaControllerCompatApi21.TransportControls {
    public static void playFromUri(Object param1Object, Uri param1Uri, Bundle param1Bundle) {
      ((MediaController.TransportControls)param1Object).playFromUri(param1Uri, param1Bundle);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\media\session\MediaControllerCompatApi23.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */