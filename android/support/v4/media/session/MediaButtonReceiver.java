package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.util.Log;
import android.view.KeyEvent;
import java.util.List;

public class MediaButtonReceiver extends BroadcastReceiver {
  private static final String TAG = "MediaButtonReceiver";
  
  public static PendingIntent buildMediaButtonPendingIntent(Context paramContext, long paramLong) {
    ComponentName componentName = getMediaButtonReceiverComponent(paramContext);
    if (componentName == null) {
      Log.w("MediaButtonReceiver", "A unique media button receiver could not be found in the given context, so couldn't build a pending intent.");
      return null;
    } 
    return buildMediaButtonPendingIntent(paramContext, componentName, paramLong);
  }
  
  public static PendingIntent buildMediaButtonPendingIntent(Context paramContext, ComponentName paramComponentName, long paramLong) {
    StringBuilder stringBuilder;
    if (paramComponentName == null) {
      Log.w("MediaButtonReceiver", "The component name of media button receiver should be provided.");
      return null;
    } 
    int i = PlaybackStateCompat.toKeyCode(paramLong);
    if (i == 0) {
      stringBuilder = new StringBuilder();
      stringBuilder.append("Cannot build a media button pending intent with the given action: ");
      stringBuilder.append(paramLong);
      Log.w("MediaButtonReceiver", stringBuilder.toString());
      return null;
    } 
    Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
    intent.setComponent(paramComponentName);
    intent.putExtra("android.intent.extra.KEY_EVENT", (Parcelable)new KeyEvent(0, i));
    return PendingIntent.getBroadcast((Context)stringBuilder, i, intent, 0);
  }
  
  static ComponentName getMediaButtonReceiverComponent(Context paramContext) {
    ResolveInfo resolveInfo;
    Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
    intent.setPackage(paramContext.getPackageName());
    List<ResolveInfo> list = paramContext.getPackageManager().queryBroadcastReceivers(intent, 0);
    if (list.size() == 1) {
      resolveInfo = list.get(0);
      return new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
    } 
    if (resolveInfo.size() > 1)
      Log.w("MediaButtonReceiver", "More than one BroadcastReceiver that handles android.intent.action.MEDIA_BUTTON was found, returning null."); 
    return null;
  }
  
  private static ComponentName getServiceComponentByAction(Context paramContext, String paramString) {
    ResolveInfo resolveInfo;
    PackageManager packageManager = paramContext.getPackageManager();
    Intent intent = new Intent(paramString);
    intent.setPackage(paramContext.getPackageName());
    List<ResolveInfo> list = packageManager.queryIntentServices(intent, 0);
    if (list.size() == 1) {
      resolveInfo = list.get(0);
      return new ComponentName(resolveInfo.serviceInfo.packageName, resolveInfo.serviceInfo.name);
    } 
    if (resolveInfo.isEmpty())
      return null; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected 1 service that handles ");
    stringBuilder.append(paramString);
    stringBuilder.append(", found ");
    stringBuilder.append(resolveInfo.size());
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public static KeyEvent handleIntent(MediaSessionCompat paramMediaSessionCompat, Intent paramIntent) {
    if (paramMediaSessionCompat == null || paramIntent == null || !"android.intent.action.MEDIA_BUTTON".equals(paramIntent.getAction()) || !paramIntent.hasExtra("android.intent.extra.KEY_EVENT"))
      return null; 
    KeyEvent keyEvent = (KeyEvent)paramIntent.getParcelableExtra("android.intent.extra.KEY_EVENT");
    paramMediaSessionCompat.getController().dispatchMediaButtonEvent(keyEvent);
    return keyEvent;
  }
  
  private static void startForegroundService(Context paramContext, Intent paramIntent) {
    if (Build.VERSION.SDK_INT >= 26) {
      paramContext.startForegroundService(paramIntent);
    } else {
      paramContext.startService(paramIntent);
    } 
  }
  
  public void onReceive(Context paramContext, Intent paramIntent) {
    StringBuilder stringBuilder;
    if (paramIntent == null || !"android.intent.action.MEDIA_BUTTON".equals(paramIntent.getAction()) || !paramIntent.hasExtra("android.intent.extra.KEY_EVENT")) {
      stringBuilder = new StringBuilder();
      stringBuilder.append("Ignore unsupported intent: ");
      stringBuilder.append(paramIntent);
      Log.d("MediaButtonReceiver", stringBuilder.toString());
      return;
    } 
    ComponentName componentName = getServiceComponentByAction((Context)stringBuilder, "android.intent.action.MEDIA_BUTTON");
    if (componentName != null) {
      paramIntent.setComponent(componentName);
      startForegroundService((Context)stringBuilder, paramIntent);
      return;
    } 
    componentName = getServiceComponentByAction((Context)stringBuilder, "android.media.browse.MediaBrowserService");
    if (componentName != null) {
      BroadcastReceiver.PendingResult pendingResult = goAsync();
      Context context = stringBuilder.getApplicationContext();
      MediaButtonConnectionCallback mediaButtonConnectionCallback = new MediaButtonConnectionCallback(context, paramIntent, pendingResult);
      MediaBrowserCompat mediaBrowserCompat = new MediaBrowserCompat(context, componentName, mediaButtonConnectionCallback, null);
      mediaButtonConnectionCallback.setMediaBrowser(mediaBrowserCompat);
      mediaBrowserCompat.connect();
      return;
    } 
    throw new IllegalStateException("Could not find any Service that handles android.intent.action.MEDIA_BUTTON or implements a media browser service.");
  }
  
  private static class MediaButtonConnectionCallback extends MediaBrowserCompat.ConnectionCallback {
    private final Context mContext;
    
    private final Intent mIntent;
    
    private MediaBrowserCompat mMediaBrowser;
    
    private final BroadcastReceiver.PendingResult mPendingResult;
    
    MediaButtonConnectionCallback(Context param1Context, Intent param1Intent, BroadcastReceiver.PendingResult param1PendingResult) {
      this.mContext = param1Context;
      this.mIntent = param1Intent;
      this.mPendingResult = param1PendingResult;
    }
    
    private void finish() {
      this.mMediaBrowser.disconnect();
      this.mPendingResult.finish();
    }
    
    public void onConnected() {
      try {
        MediaControllerCompat mediaControllerCompat = new MediaControllerCompat();
        this(this.mContext, this.mMediaBrowser.getSessionToken());
        mediaControllerCompat.dispatchMediaButtonEvent((KeyEvent)this.mIntent.getParcelableExtra("android.intent.extra.KEY_EVENT"));
      } catch (RemoteException remoteException) {
        Log.e("MediaButtonReceiver", "Failed to create a media controller", (Throwable)remoteException);
      } 
      finish();
    }
    
    public void onConnectionFailed() {
      finish();
    }
    
    public void onConnectionSuspended() {
      finish();
    }
    
    void setMediaBrowser(MediaBrowserCompat param1MediaBrowserCompat) {
      this.mMediaBrowser = param1MediaBrowserCompat;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\media\session\MediaButtonReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */