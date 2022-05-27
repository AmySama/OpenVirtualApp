package io.virtualapp.home;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.lody.virtual.client.core.VirtualCore;
import com.stub.StubApp;
import io.virtualapp.abs.ui.VActivity;
import io.virtualapp.home.models.PackageAppData;

public class AppSettingActivity extends VActivity {
  private PackageAppData mData;
  
  private PackageInfo mPkgInfo;
  
  private int mUserId;
  
  static {
    StubApp.interface11(9677);
  }
  
  private void cleanAppData() {
    String str;
    boolean bool = VirtualCore.get().cleanPackageData(this.mPkgInfo.packageName, this.mUserId);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("clean app data ");
    if (bool) {
      str = "success.";
    } else {
      str = "failed.";
    } 
    stringBuilder.append(str);
    Toast.makeText((Context)this, stringBuilder.toString(), 0).show();
  }
  
  public static void enterAppSetting(Context paramContext, String paramString, int paramInt) {
    Intent intent = new Intent(paramContext, AppSettingActivity.class);
    intent.putExtra("extra.PKG", paramString);
    intent.putExtra("extra.UserId", paramInt);
    paramContext.startActivity(intent);
  }
  
  protected native void onCreate(Bundle paramBundle);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\AppSettingActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */