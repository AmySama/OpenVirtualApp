package android.support.v4.app;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.v13.view.DragAndDropPermissionsCompat;
import android.support.v4.content.ContextCompat;
import android.view.DragEvent;
import android.view.View;
import java.util.List;
import java.util.Map;

public class ActivityCompat extends ContextCompat {
  private static PermissionCompatDelegate sDelegate;
  
  public static void finishAffinity(Activity paramActivity) {
    if (Build.VERSION.SDK_INT >= 16) {
      paramActivity.finishAffinity();
    } else {
      paramActivity.finish();
    } 
  }
  
  public static void finishAfterTransition(Activity paramActivity) {
    if (Build.VERSION.SDK_INT >= 21) {
      paramActivity.finishAfterTransition();
    } else {
      paramActivity.finish();
    } 
  }
  
  public static PermissionCompatDelegate getPermissionCompatDelegate() {
    return sDelegate;
  }
  
  public static Uri getReferrer(Activity paramActivity) {
    if (Build.VERSION.SDK_INT >= 22)
      return paramActivity.getReferrer(); 
    Intent intent = paramActivity.getIntent();
    Uri uri = (Uri)intent.getParcelableExtra("android.intent.extra.REFERRER");
    if (uri != null)
      return uri; 
    String str = intent.getStringExtra("android.intent.extra.REFERRER_NAME");
    return (str != null) ? Uri.parse(str) : null;
  }
  
  @Deprecated
  public static boolean invalidateOptionsMenu(Activity paramActivity) {
    paramActivity.invalidateOptionsMenu();
    return true;
  }
  
  public static void postponeEnterTransition(Activity paramActivity) {
    if (Build.VERSION.SDK_INT >= 21)
      paramActivity.postponeEnterTransition(); 
  }
  
  public static DragAndDropPermissionsCompat requestDragAndDropPermissions(Activity paramActivity, DragEvent paramDragEvent) {
    return DragAndDropPermissionsCompat.request(paramActivity, paramDragEvent);
  }
  
  public static void requestPermissions(final Activity activity, final String[] permissions, final int requestCode) {
    PermissionCompatDelegate permissionCompatDelegate = sDelegate;
    if (permissionCompatDelegate != null && permissionCompatDelegate.requestPermissions(activity, permissions, requestCode))
      return; 
    if (Build.VERSION.SDK_INT >= 23) {
      if (activity instanceof RequestPermissionsRequestCodeValidator)
        ((RequestPermissionsRequestCodeValidator)activity).validateRequestPermissionsRequestCode(requestCode); 
      activity.requestPermissions(permissions, requestCode);
    } else if (activity instanceof OnRequestPermissionsResultCallback) {
      (new Handler(Looper.getMainLooper())).post(new Runnable() {
            public void run() {
              int[] arrayOfInt = new int[permissions.length];
              PackageManager packageManager = activity.getPackageManager();
              String str = activity.getPackageName();
              int i = permissions.length;
              for (byte b = 0; b < i; b++)
                arrayOfInt[b] = packageManager.checkPermission(permissions[b], str); 
              ((ActivityCompat.OnRequestPermissionsResultCallback)activity).onRequestPermissionsResult(requestCode, permissions, arrayOfInt);
            }
          });
    } 
  }
  
  public static <T extends View> T requireViewById(Activity paramActivity, int paramInt) {
    View view = paramActivity.findViewById(paramInt);
    if (view != null)
      return (T)view; 
    throw new IllegalArgumentException("ID does not reference a View inside this Activity");
  }
  
  public static void setEnterSharedElementCallback(Activity paramActivity, SharedElementCallback paramSharedElementCallback) {
    int i = Build.VERSION.SDK_INT;
    SharedElementCallback23Impl sharedElementCallback23Impl1 = null;
    SharedElementCallback23Impl sharedElementCallback23Impl2 = null;
    if (i >= 23) {
      if (paramSharedElementCallback != null)
        sharedElementCallback23Impl2 = new SharedElementCallback23Impl(paramSharedElementCallback); 
      paramActivity.setEnterSharedElementCallback(sharedElementCallback23Impl2);
    } else if (Build.VERSION.SDK_INT >= 21) {
      SharedElementCallback21Impl sharedElementCallback21Impl;
      sharedElementCallback23Impl2 = sharedElementCallback23Impl1;
      if (paramSharedElementCallback != null)
        sharedElementCallback21Impl = new SharedElementCallback21Impl(paramSharedElementCallback); 
      paramActivity.setEnterSharedElementCallback(sharedElementCallback21Impl);
    } 
  }
  
  public static void setExitSharedElementCallback(Activity paramActivity, SharedElementCallback paramSharedElementCallback) {
    int i = Build.VERSION.SDK_INT;
    SharedElementCallback23Impl sharedElementCallback23Impl1 = null;
    SharedElementCallback23Impl sharedElementCallback23Impl2 = null;
    if (i >= 23) {
      if (paramSharedElementCallback != null)
        sharedElementCallback23Impl2 = new SharedElementCallback23Impl(paramSharedElementCallback); 
      paramActivity.setExitSharedElementCallback(sharedElementCallback23Impl2);
    } else if (Build.VERSION.SDK_INT >= 21) {
      SharedElementCallback21Impl sharedElementCallback21Impl;
      sharedElementCallback23Impl2 = sharedElementCallback23Impl1;
      if (paramSharedElementCallback != null)
        sharedElementCallback21Impl = new SharedElementCallback21Impl(paramSharedElementCallback); 
      paramActivity.setExitSharedElementCallback(sharedElementCallback21Impl);
    } 
  }
  
  public static void setPermissionCompatDelegate(PermissionCompatDelegate paramPermissionCompatDelegate) {
    sDelegate = paramPermissionCompatDelegate;
  }
  
  public static boolean shouldShowRequestPermissionRationale(Activity paramActivity, String paramString) {
    return (Build.VERSION.SDK_INT >= 23) ? paramActivity.shouldShowRequestPermissionRationale(paramString) : false;
  }
  
  public static void startActivityForResult(Activity paramActivity, Intent paramIntent, int paramInt, Bundle paramBundle) {
    if (Build.VERSION.SDK_INT >= 16) {
      paramActivity.startActivityForResult(paramIntent, paramInt, paramBundle);
    } else {
      paramActivity.startActivityForResult(paramIntent, paramInt);
    } 
  }
  
  public static void startIntentSenderForResult(Activity paramActivity, IntentSender paramIntentSender, int paramInt1, Intent paramIntent, int paramInt2, int paramInt3, int paramInt4, Bundle paramBundle) throws IntentSender.SendIntentException {
    if (Build.VERSION.SDK_INT >= 16) {
      paramActivity.startIntentSenderForResult(paramIntentSender, paramInt1, paramIntent, paramInt2, paramInt3, paramInt4, paramBundle);
    } else {
      paramActivity.startIntentSenderForResult(paramIntentSender, paramInt1, paramIntent, paramInt2, paramInt3, paramInt4);
    } 
  }
  
  public static void startPostponedEnterTransition(Activity paramActivity) {
    if (Build.VERSION.SDK_INT >= 21)
      paramActivity.startPostponedEnterTransition(); 
  }
  
  public static interface OnRequestPermissionsResultCallback {
    void onRequestPermissionsResult(int param1Int, String[] param1ArrayOfString, int[] param1ArrayOfint);
  }
  
  public static interface PermissionCompatDelegate {
    boolean onActivityResult(Activity param1Activity, int param1Int1, int param1Int2, Intent param1Intent);
    
    boolean requestPermissions(Activity param1Activity, String[] param1ArrayOfString, int param1Int);
  }
  
  public static interface RequestPermissionsRequestCodeValidator {
    void validateRequestPermissionsRequestCode(int param1Int);
  }
  
  private static class SharedElementCallback21Impl extends SharedElementCallback {
    protected SharedElementCallback mCallback;
    
    SharedElementCallback21Impl(SharedElementCallback param1SharedElementCallback) {
      this.mCallback = param1SharedElementCallback;
    }
    
    public Parcelable onCaptureSharedElementSnapshot(View param1View, Matrix param1Matrix, RectF param1RectF) {
      return this.mCallback.onCaptureSharedElementSnapshot(param1View, param1Matrix, param1RectF);
    }
    
    public View onCreateSnapshotView(Context param1Context, Parcelable param1Parcelable) {
      return this.mCallback.onCreateSnapshotView(param1Context, param1Parcelable);
    }
    
    public void onMapSharedElements(List<String> param1List, Map<String, View> param1Map) {
      this.mCallback.onMapSharedElements(param1List, param1Map);
    }
    
    public void onRejectSharedElements(List<View> param1List) {
      this.mCallback.onRejectSharedElements(param1List);
    }
    
    public void onSharedElementEnd(List<String> param1List, List<View> param1List1, List<View> param1List2) {
      this.mCallback.onSharedElementEnd(param1List, param1List1, param1List2);
    }
    
    public void onSharedElementStart(List<String> param1List, List<View> param1List1, List<View> param1List2) {
      this.mCallback.onSharedElementStart(param1List, param1List1, param1List2);
    }
  }
  
  private static class SharedElementCallback23Impl extends SharedElementCallback21Impl {
    SharedElementCallback23Impl(SharedElementCallback param1SharedElementCallback) {
      super(param1SharedElementCallback);
    }
    
    public void onSharedElementsArrived(List<String> param1List, List<View> param1List1, final SharedElementCallback.OnSharedElementsReadyListener listener) {
      this.mCallback.onSharedElementsArrived(param1List, param1List1, new SharedElementCallback.OnSharedElementsReadyListener() {
            public void onSharedElementsReady() {
              listener.onSharedElementsReady();
            }
          });
    }
  }
  
  class null implements SharedElementCallback.OnSharedElementsReadyListener {
    public void onSharedElementsReady() {
      listener.onSharedElementsReady();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\app\ActivityCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */