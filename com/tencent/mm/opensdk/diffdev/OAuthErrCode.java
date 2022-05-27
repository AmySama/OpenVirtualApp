package com.tencent.mm.opensdk.diffdev;

public enum OAuthErrCode {
  WechatAuth_Err_Auth_Stopped,
  WechatAuth_Err_Cancel,
  WechatAuth_Err_JsonDecodeErr,
  WechatAuth_Err_NetworkErr,
  WechatAuth_Err_NormalErr,
  WechatAuth_Err_OK(0),
  WechatAuth_Err_Timeout(0);
  
  private int code;
  
  static {
    WechatAuth_Err_NormalErr = new OAuthErrCode("WechatAuth_Err_NormalErr", 1, -1);
    WechatAuth_Err_NetworkErr = new OAuthErrCode("WechatAuth_Err_NetworkErr", 2, -2);
    WechatAuth_Err_JsonDecodeErr = new OAuthErrCode("WechatAuth_Err_JsonDecodeErr", 3, -3);
    WechatAuth_Err_Cancel = new OAuthErrCode("WechatAuth_Err_Cancel", 4, -4);
    WechatAuth_Err_Timeout = new OAuthErrCode("WechatAuth_Err_Timeout", 5, -5);
    OAuthErrCode oAuthErrCode = new OAuthErrCode("WechatAuth_Err_Auth_Stopped", 6, -6);
    WechatAuth_Err_Auth_Stopped = oAuthErrCode;
    $VALUES = new OAuthErrCode[] { WechatAuth_Err_OK, WechatAuth_Err_NormalErr, WechatAuth_Err_NetworkErr, WechatAuth_Err_JsonDecodeErr, WechatAuth_Err_Cancel, WechatAuth_Err_Timeout, oAuthErrCode };
  }
  
  OAuthErrCode(int paramInt1) {
    this.code = paramInt1;
  }
  
  public int getCode() {
    return this.code;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("OAuthErrCode:");
    stringBuilder.append(this.code);
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\diffdev\OAuthErrCode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */