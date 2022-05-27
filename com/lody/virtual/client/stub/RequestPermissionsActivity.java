package com.lody.virtual.client.stub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.lody.virtual.helper.compat.BundleCompat;
import com.lody.virtual.server.IRequestPermissionsResult;
import com.stub.StubApp;

public class RequestPermissionsActivity extends Activity {
  private static final int REQUEST_PERMISSION_CODE = 996;
  
  private IRequestPermissionsResult mCallBack;
  
  static {
    StubApp.interface11(5991);
  }
  
  public static void request(Context paramContext, boolean paramBoolean, String[] paramArrayOfString, IRequestPermissionsResult paramIRequestPermissionsResult) {
    Intent intent = new Intent();
    if (paramBoolean) {
      intent.setClassName(StubManifest.EXT_PACKAGE_NAME, RequestPermissionsActivity.class.getName());
    } else {
      intent.setClassName(StubManifest.PACKAGE_NAME, RequestPermissionsActivity.class.getName());
    } 
    intent.setFlags(268435456);
    intent.putExtra("permissions", paramArrayOfString);
    BundleCompat.putBinder(intent, "callback", paramIRequestPermissionsResult.asBinder());
    paramContext.startActivity(intent);
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  public native void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\stub\RequestPermissionsActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */