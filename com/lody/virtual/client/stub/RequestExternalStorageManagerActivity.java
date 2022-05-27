package com.lody.virtual.client.stub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

public class RequestExternalStorageManagerActivity extends Activity {
  public static void request(Context paramContext, boolean paramBoolean) {
    Intent intent = new Intent();
    if (paramBoolean) {
      intent.setClassName(StubManifest.EXT_PACKAGE_NAME, RequestExternalStorageManagerActivity.class.getName());
    } else {
      intent.setClassName(StubManifest.PACKAGE_NAME, RequestExternalStorageManagerActivity.class.getName());
    } 
    intent.setFlags(268435456);
    paramContext.startActivity(intent);
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    if (Build.VERSION.SDK_INT >= 30 && !Environment.isExternalStorageManager()) {
      Intent intent = new Intent("android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION");
      intent.addFlags(268435456);
      startActivity(intent);
    } 
    finish();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\stub\RequestExternalStorageManagerActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */