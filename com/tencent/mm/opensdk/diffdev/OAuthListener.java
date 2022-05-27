package com.tencent.mm.opensdk.diffdev;

public interface OAuthListener {
  void onAuthFinish(OAuthErrCode paramOAuthErrCode, String paramString);
  
  void onAuthGotQrcode(String paramString, byte[] paramArrayOfbyte);
  
  void onQrcodeScanned();
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\diffdev\OAuthListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */