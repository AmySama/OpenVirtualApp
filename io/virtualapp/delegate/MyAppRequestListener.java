package io.virtualapp.delegate;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.remote.VAppInstallerParams;
import com.lody.virtual.remote.VAppInstallerResult;
import java.io.File;

public class MyAppRequestListener implements VirtualCore.AppRequestListener {
  private final Context mContext;
  
  public MyAppRequestListener(Context paramContext) {
    this.mContext = paramContext;
  }
  
  private static void info(String paramString) {
    VLog.e("AppInstaller", paramString);
  }
  
  public void onRequestInstall(String paramString) {
    String str;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Start installing: ");
    stringBuilder.append(paramString);
    info(stringBuilder.toString());
    VAppInstallerParams vAppInstallerParams = new VAppInstallerParams();
    VAppInstallerResult vAppInstallerResult = VirtualCore.get().installPackage(Uri.fromFile(new File(paramString)), vAppInstallerParams);
    if (vAppInstallerResult.status == 0) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Install ");
      stringBuilder1.append(vAppInstallerResult.packageName);
      stringBuilder1.append(" success.");
      info(stringBuilder1.toString());
      boolean bool = VActivityManager.get().launchApp(0, vAppInstallerResult.packageName);
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append("launch app ");
      if (bool) {
        str = "success.";
      } else {
        str = "fail.";
      } 
      stringBuilder1.append(str);
      info(stringBuilder1.toString());
    } else {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Install ");
      stringBuilder1.append(((VAppInstallerResult)str).packageName);
      stringBuilder1.append(" fail, error code: ");
      stringBuilder1.append(((VAppInstallerResult)str).status);
      info(stringBuilder1.toString());
    } 
  }
  
  public void onRequestUninstall(String paramString) {
    Context context = this.mContext;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Intercept uninstall request: ");
    stringBuilder.append(paramString);
    Toast.makeText(context, stringBuilder.toString(), 0).show();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\delegate\MyAppRequestListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */