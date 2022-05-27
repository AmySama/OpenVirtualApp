package com.tencent.mm.opensdk.openapi;

import android.content.Context;
import com.tencent.mm.opensdk.utils.Log;

public class WXAPIFactory {
  private static final String TAG = "MicroMsg.PaySdk.WXFactory";
  
  private WXAPIFactory() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(WXAPIFactory.class.getSimpleName());
    stringBuilder.append(" should not be instantiated");
    throw new RuntimeException(stringBuilder.toString());
  }
  
  public static IWXAPI createWXAPI(Context paramContext, String paramString) {
    return createWXAPI(paramContext, paramString, true);
  }
  
  public static IWXAPI createWXAPI(Context paramContext, String paramString, boolean paramBoolean) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("createWXAPI, appId = ");
    stringBuilder.append(paramString);
    stringBuilder.append(", checkSignature = ");
    stringBuilder.append(paramBoolean);
    Log.d("MicroMsg.PaySdk.WXFactory", stringBuilder.toString());
    return createWXAPI(paramContext, paramString, paramBoolean, 2);
  }
  
  public static IWXAPI createWXAPI(Context paramContext, String paramString, boolean paramBoolean, int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("createWXAPI, appId = ");
    stringBuilder.append(paramString);
    stringBuilder.append(", checkSignature = ");
    stringBuilder.append(paramBoolean);
    stringBuilder.append(", launchMode = ");
    stringBuilder.append(paramInt);
    Log.d("MicroMsg.PaySdk.WXFactory", stringBuilder.toString());
    return new WXApiImplV10(paramContext, paramString, paramBoolean, paramInt);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\openapi\WXAPIFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */