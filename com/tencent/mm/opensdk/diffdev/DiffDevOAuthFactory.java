package com.tencent.mm.opensdk.diffdev;

import com.tencent.mm.opensdk.diffdev.a.a;
import com.tencent.mm.opensdk.utils.Log;

public class DiffDevOAuthFactory {
  public static final int MAX_SUPPORTED_VERSION = 1;
  
  private static final String TAG = "MicroMsg.SDK.DiffDevOAuthFactory";
  
  public static final int VERSION_1 = 1;
  
  private static IDiffDevOAuth v1Instance;
  
  public static IDiffDevOAuth getDiffDevOAuth() {
    return getDiffDevOAuth(1);
  }
  
  public static IDiffDevOAuth getDiffDevOAuth(int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("getDiffDevOAuth, version = ");
    stringBuilder.append(paramInt);
    Log.v("MicroMsg.SDK.DiffDevOAuthFactory", stringBuilder.toString());
    if (paramInt > 1) {
      stringBuilder = new StringBuilder();
      stringBuilder.append("getDiffDevOAuth fail, unsupported version = ");
      stringBuilder.append(paramInt);
      Log.e("MicroMsg.SDK.DiffDevOAuthFactory", stringBuilder.toString());
      return null;
    } 
    if (paramInt != 1)
      return null; 
    if (v1Instance == null)
      v1Instance = (IDiffDevOAuth)new a(); 
    return v1Instance;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\diffdev\DiffDevOAuthFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */