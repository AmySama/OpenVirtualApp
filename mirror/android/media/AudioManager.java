package mirror.android.media;

import android.os.IInterface;
import mirror.RefClass;
import mirror.RefStaticMethod;
import mirror.RefStaticObject;

public class AudioManager {
  public static Class<?> TYPE = RefClass.load(AudioManager.class, android.media.AudioManager.class);
  
  public static RefStaticMethod getService;
  
  public static RefStaticObject<IInterface> sService;
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\mirror\android\media\AudioManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */