package android.support.v4.media.session;

import android.media.session.PlaybackState;
import android.os.Bundle;
import java.util.Iterator;
import java.util.List;

class PlaybackStateCompatApi21 {
  public static long getActions(Object paramObject) {
    return ((PlaybackState)paramObject).getActions();
  }
  
  public static long getActiveQueueItemId(Object paramObject) {
    return ((PlaybackState)paramObject).getActiveQueueItemId();
  }
  
  public static long getBufferedPosition(Object paramObject) {
    return ((PlaybackState)paramObject).getBufferedPosition();
  }
  
  public static List<Object> getCustomActions(Object paramObject) {
    return ((PlaybackState)paramObject).getCustomActions();
  }
  
  public static CharSequence getErrorMessage(Object paramObject) {
    return ((PlaybackState)paramObject).getErrorMessage();
  }
  
  public static long getLastPositionUpdateTime(Object paramObject) {
    return ((PlaybackState)paramObject).getLastPositionUpdateTime();
  }
  
  public static float getPlaybackSpeed(Object paramObject) {
    return ((PlaybackState)paramObject).getPlaybackSpeed();
  }
  
  public static long getPosition(Object paramObject) {
    return ((PlaybackState)paramObject).getPosition();
  }
  
  public static int getState(Object paramObject) {
    return ((PlaybackState)paramObject).getState();
  }
  
  public static Object newInstance(int paramInt, long paramLong1, long paramLong2, float paramFloat, long paramLong3, CharSequence paramCharSequence, long paramLong4, List<Object> paramList, long paramLong5) {
    PlaybackState.Builder builder = new PlaybackState.Builder();
    builder.setState(paramInt, paramLong1, paramFloat, paramLong4);
    builder.setBufferedPosition(paramLong2);
    builder.setActions(paramLong3);
    builder.setErrorMessage(paramCharSequence);
    Iterator<PlaybackState.CustomAction> iterator = paramList.iterator();
    while (iterator.hasNext())
      builder.addCustomAction(iterator.next()); 
    builder.setActiveQueueItemId(paramLong5);
    return builder.build();
  }
  
  static final class CustomAction {
    public static String getAction(Object param1Object) {
      return ((PlaybackState.CustomAction)param1Object).getAction();
    }
    
    public static Bundle getExtras(Object param1Object) {
      return ((PlaybackState.CustomAction)param1Object).getExtras();
    }
    
    public static int getIcon(Object param1Object) {
      return ((PlaybackState.CustomAction)param1Object).getIcon();
    }
    
    public static CharSequence getName(Object param1Object) {
      return ((PlaybackState.CustomAction)param1Object).getName();
    }
    
    public static Object newInstance(String param1String, CharSequence param1CharSequence, int param1Int, Bundle param1Bundle) {
      PlaybackState.CustomAction.Builder builder = new PlaybackState.CustomAction.Builder(param1String, param1CharSequence, param1Int);
      builder.setExtras(param1Bundle);
      return builder.build();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\media\session\PlaybackStateCompatApi21.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */