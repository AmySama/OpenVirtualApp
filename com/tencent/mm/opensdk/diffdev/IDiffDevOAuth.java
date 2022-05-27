package com.tencent.mm.opensdk.diffdev;

public interface IDiffDevOAuth {
  void addListener(OAuthListener paramOAuthListener);
  
  boolean auth(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, OAuthListener paramOAuthListener);
  
  void detach();
  
  void removeAllListeners();
  
  void removeListener(OAuthListener paramOAuthListener);
  
  boolean stopAuth();
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\diffdev\IDiffDevOAuth.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */