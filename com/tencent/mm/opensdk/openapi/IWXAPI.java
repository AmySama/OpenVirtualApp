package com.tencent.mm.opensdk.openapi;

import android.content.Intent;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.utils.ILog;

public interface IWXAPI {
  void detach();
  
  int getWXAppSupportAPI();
  
  boolean handleIntent(Intent paramIntent, IWXAPIEventHandler paramIWXAPIEventHandler);
  
  boolean isWXAppInstalled();
  
  boolean openWXApp();
  
  boolean registerApp(String paramString);
  
  boolean registerApp(String paramString, long paramLong);
  
  boolean sendReq(BaseReq paramBaseReq);
  
  boolean sendResp(BaseResp paramBaseResp);
  
  void setLogImpl(ILog paramILog);
  
  void unregisterApp();
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\openapi\IWXAPI.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */