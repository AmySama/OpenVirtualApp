package io.virtualapp.Utils;

import android.util.Log;
import java.util.UUID;
import org.json.JSONObject;

public class MockRequest {
  private static final String TAG = MockRequest.class.getSimpleName();
  
  public static String getPhoneNumber(String paramString) {
    String str;
    try {
      String str1 = TAG;
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append("一键登录换号：token: ");
      stringBuilder.append(paramString);
      Log.i(str1, stringBuilder.toString());
      Thread.sleep(500L);
      JSONObject jSONObject = new JSONObject();
      this();
      jSONObject.put("account", UUID.randomUUID().toString());
      jSONObject.put("phoneNumber", "***********");
      jSONObject.put("token", paramString);
      paramString = jSONObject.toString();
    } catch (Exception exception) {
      exception.printStackTrace();
      str = "";
    } 
    return str;
  }
  
  public static String verifyNumber(String paramString1, String paramString2) {
    try {
      String str = TAG;
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append("进行本机号码校验：token: ");
      stringBuilder.append(paramString1);
      stringBuilder.append(", phoneNumber: ");
      stringBuilder.append(paramString2);
      Log.i(str, stringBuilder.toString());
      Thread.sleep(500L);
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return "本机号码校验成功";
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\Utils\MockRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */