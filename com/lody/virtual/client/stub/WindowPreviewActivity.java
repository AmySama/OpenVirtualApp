package com.lody.virtual.client.stub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.helper.utils.VLog;
import com.stub.StubApp;
import mirror.android.graphics.drawable.LayerDrawable;

public class WindowPreviewActivity extends Activity {
  private long startTime;
  
  static {
    StubApp.interface11(6616);
  }
  
  private boolean isDrawableBroken(Drawable paramDrawable) {
    if (LayerDrawable.TYPE.isInstance(paramDrawable) && LayerDrawable.isProjected != null)
      try {
        return false;
      } finally {
        paramDrawable = null;
        VLog.e("WindowPreviewActivity", "Bad preview background!", new Object[] { paramDrawable });
      }  
    return false;
  }
  
  public static void previewActivity(int paramInt, ActivityInfo paramActivityInfo) {
    Intent intent2;
    Context context = VirtualCore.get().getContext();
    Intent intent1 = new Intent(context, WindowPreviewActivity.class);
    try {
      boolean bool = StubManifest.isFixedOrientationLandscape(paramActivityInfo);
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append("previewActivity isFixedOrientationLandscape:");
      stringBuilder.append(bool);
      stringBuilder.append(",info:");
      stringBuilder.append(paramActivityInfo);
      VLog.d("VA", stringBuilder.toString(), new Object[0]);
      intent2 = intent1;
      if (bool) {
        intent2 = new Intent();
        this(context, WindowPreviewActivity_Land.class);
      } 
    } catch (Exception exception) {
      exception.printStackTrace();
      intent2 = intent1;
    } 
    intent2.putExtra("_VA_|user_id", paramInt);
    intent2.putExtra("_VA_|activity_info", (Parcelable)paramActivityInfo);
    intent2.addFlags(268435456);
    intent2.addFlags(65536);
    context.startActivity(intent2);
  }
  
  public void onBackPressed() {
    if (System.currentTimeMillis() - this.startTime > 5000L)
      finish(); 
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  protected void onStop() {
    super.onStop();
    finish();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\stub\WindowPreviewActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */