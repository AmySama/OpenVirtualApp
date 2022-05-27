package com.lody.virtual.client.stub;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.stub.StubApp;

public class ChooserActivity extends ResolverActivity {
  public static final String ACTION = Intent.createChooser(new Intent(), "").getAction();
  
  public static final String EXTRA_DATA = "android.intent.extra.virtual.data";
  
  public static final String EXTRA_INTENT = "android.intent.extra.virtual.intent";
  
  public static final String EXTRA_REQUEST_CODE = "android.intent.extra.virtual.request_code";
  
  public static final String EXTRA_RESULTTO = "_va|ibinder|resultTo";
  
  public static final String EXTRA_WHO = "android.intent.extra.virtual.who";
  
  public static boolean check(Intent paramIntent) {
    boolean bool = false;
    try {
      if (!TextUtils.equals(ACTION, paramIntent.getAction())) {
        boolean bool1 = TextUtils.equals("android.intent.action.CHOOSER", paramIntent.getAction());
        return bool1 ? true : bool;
      } 
      return true;
    } catch (Exception exception) {
      exception.printStackTrace();
      return false;
    } 
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  static {
    StubApp.interface11(5983);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\stub\ChooserActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */