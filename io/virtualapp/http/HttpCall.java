package io.virtualapp.http;

import org.json.JSONObject;

public interface HttpCall {
  void onError();
  
  void onSuccess(String paramString, JSONObject paramJSONObject);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\http\HttpCall.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */