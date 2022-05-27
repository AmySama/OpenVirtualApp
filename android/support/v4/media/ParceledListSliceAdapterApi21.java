package android.support.v4.media;

import android.media.browse.MediaBrowser;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

class ParceledListSliceAdapterApi21 {
  private static Constructor sConstructor;
  
  static {
    try {
      sConstructor = Class.forName("android.content.pm.ParceledListSlice").getConstructor(new Class[] { List.class });
    } catch (ClassNotFoundException classNotFoundException) {
      classNotFoundException.printStackTrace();
    } catch (NoSuchMethodException noSuchMethodException) {}
  }
  
  static Object newInstance(List<MediaBrowser.MediaItem> paramList) {
    try {
      paramList = sConstructor.newInstance(new Object[] { paramList });
    } catch (InstantiationException instantiationException) {
      instantiationException.printStackTrace();
      instantiationException = null;
    } catch (IllegalAccessException illegalAccessException) {
    
    } catch (InvocationTargetException invocationTargetException) {}
    return invocationTargetException;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\media\ParceledListSliceAdapterApi21.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */