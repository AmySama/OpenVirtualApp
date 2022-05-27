package io.virtualapp.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.stub.StubApp;

public class PermissionRequestActivity extends Activity {
  private static final String EXTRA_APP_NAME = "extra.app_name";
  
  private static final String EXTRA_PACKAGE_NAME = "extra.package_name";
  
  private static final String EXTRA_PERMISSIONS = "extra.permission";
  
  private static final String EXTRA_USER_ID = "extra.user_id";
  
  private static final int REQUEST_PERMISSION_CODE = 995;
  
  private String appName;
  
  private String packageName;
  
  private int userId;
  
  static {
    StubApp.interface11(9715);
  }
  
  public static void requestPermission(Activity paramActivity, String[] paramArrayOfString, String paramString1, int paramInt1, String paramString2, int paramInt2) {
    Intent intent = new Intent((Context)paramActivity, PermissionRequestActivity.class);
    intent.putExtra("extra.permission", paramArrayOfString);
    intent.putExtra("extra.app_name", paramString1);
    intent.putExtra("extra.package_name", paramString2);
    intent.putExtra("extra.user_id", paramInt1);
    paramActivity.startActivityForResult(intent, paramInt2);
    paramActivity.overridePendingTransition(0, 0);
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  public native void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\PermissionRequestActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */