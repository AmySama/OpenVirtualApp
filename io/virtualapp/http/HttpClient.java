package io.virtualapp.http;

import android.util.Log;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

public class HttpClient {
  private static final String BASE_URL = "";
  
  private static AsyncHttpClient client = new AsyncHttpClient();
  
  public static void get(String paramString, RequestParams paramRequestParams, AsyncHttpResponseHandler paramAsyncHttpResponseHandler) {
    client.get(getAbsoluteUrl(paramString), paramRequestParams, (ResponseHandlerInterface)paramAsyncHttpResponseHandler);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(getAbsoluteUrl(paramString));
    stringBuilder.append(paramRequestParams);
    Log.i("HttpManger", stringBuilder.toString());
  }
  
  private static String getAbsoluteUrl(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("");
    stringBuilder.append(paramString);
    return stringBuilder.toString();
  }
  
  public static void getWechat(String paramString, RequestParams paramRequestParams, AsyncHttpResponseHandler paramAsyncHttpResponseHandler) {
    client.get(paramString, paramRequestParams, (ResponseHandlerInterface)paramAsyncHttpResponseHandler);
  }
  
  public static void post(String paramString, RequestParams paramRequestParams, AsyncHttpResponseHandler paramAsyncHttpResponseHandler) {
    client.setConnectTimeout(40000);
    client.setTimeout(40000);
    client.setResponseTimeout(40000);
    client.post(getAbsoluteUrl(paramString), paramRequestParams, (ResponseHandlerInterface)paramAsyncHttpResponseHandler);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(getAbsoluteUrl(paramString));
    stringBuilder.append(paramRequestParams);
    Log.i("HttpManger", stringBuilder.toString());
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\http\HttpClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */