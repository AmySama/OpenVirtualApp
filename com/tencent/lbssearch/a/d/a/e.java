package com.tencent.lbssearch.a.d.a;

import com.tencent.lbssearch.a.d.j;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class e implements d {
  private final a a;
  
  private final SSLSocketFactory b;
  
  public e() {
    this(null);
  }
  
  public e(a parama) {
    this(parama, null);
  }
  
  public e(a parama, SSLSocketFactory paramSSLSocketFactory) {
    this.a = parama;
    this.b = paramSSLSocketFactory;
  }
  
  private HttpURLConnection a(URL paramURL, j<?> paramj) throws IOException {
    HttpURLConnection httpURLConnection = a(paramURL);
    int i = paramj.p();
    httpURLConnection.setConnectTimeout(i);
    httpURLConnection.setReadTimeout(i);
    httpURLConnection.setUseCaches(false);
    httpURLConnection.setDoInput(true);
    if ("https".equals(paramURL.getProtocol())) {
      SSLSocketFactory sSLSocketFactory = this.b;
      if (sSLSocketFactory != null)
        ((HttpsURLConnection)httpURLConnection).setSSLSocketFactory(sSLSocketFactory); 
    } 
    return httpURLConnection;
  }
  
  static void a(HttpURLConnection paramHttpURLConnection, j<?> paramj) throws IOException, com.tencent.lbssearch.a.d.a {
    switch (paramj.a()) {
      default:
        throw new IllegalStateException("Unknown method type.");
      case 7:
        paramHttpURLConnection.setRequestMethod("PATCH");
        b(paramHttpURLConnection, paramj);
        return;
      case 6:
        paramHttpURLConnection.setRequestMethod("TRACE");
        return;
      case 5:
        paramHttpURLConnection.setRequestMethod("OPTIONS");
        return;
      case 4:
        paramHttpURLConnection.setRequestMethod("HEAD");
        return;
      case 3:
        paramHttpURLConnection.setRequestMethod("DELETE");
        return;
      case 2:
        paramHttpURLConnection.setRequestMethod("PUT");
        b(paramHttpURLConnection, paramj);
        return;
      case 1:
        paramHttpURLConnection.setRequestMethod("POST");
        b(paramHttpURLConnection, paramj);
        return;
      case 0:
        paramHttpURLConnection.setRequestMethod("GET");
        return;
      case -1:
        break;
    } 
    byte[] arrayOfByte = paramj.i();
    if (arrayOfByte != null) {
      paramHttpURLConnection.setDoOutput(true);
      paramHttpURLConnection.setRequestMethod("POST");
      paramHttpURLConnection.addRequestProperty("Content-Type", paramj.h());
      DataOutputStream dataOutputStream = new DataOutputStream(paramHttpURLConnection.getOutputStream());
      dataOutputStream.write(arrayOfByte);
      dataOutputStream.close();
    } 
  }
  
  private static void b(HttpURLConnection paramHttpURLConnection, j<?> paramj) throws IOException, com.tencent.lbssearch.a.d.a {
    byte[] arrayOfByte = paramj.m();
    if (arrayOfByte != null) {
      paramHttpURLConnection.setDoOutput(true);
      paramHttpURLConnection.addRequestProperty("Content-Type", paramj.l());
      DataOutputStream dataOutputStream = new DataOutputStream(paramHttpURLConnection.getOutputStream());
      dataOutputStream.write(arrayOfByte);
      dataOutputStream.close();
    } 
  }
  
  public HttpURLConnection a(j<?> paramj, Map<String, String> paramMap) throws IOException, com.tencent.lbssearch.a.d.a {
    StringBuilder stringBuilder;
    String str2 = paramj.c();
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    hashMap.putAll(paramj.e());
    hashMap.putAll(paramMap);
    a a1 = this.a;
    str1 = str2;
    if (a1 != null) {
      str1 = a1.a(str2);
      if (str1 == null) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("URL blocked by rewriter: ");
        stringBuilder.append(str2);
        throw new IOException(stringBuilder.toString());
      } 
    } 
    HttpURLConnection httpURLConnection = a(new URL(str1), (j<?>)stringBuilder);
    for (String str1 : hashMap.keySet())
      httpURLConnection.addRequestProperty(str1, (String)hashMap.get(str1)); 
    a(httpURLConnection, (j<?>)stringBuilder);
    return httpURLConnection;
  }
  
  protected HttpURLConnection a(URL paramURL) throws IOException {
    HttpURLConnection httpURLConnection = (HttpURLConnection)paramURL.openConnection();
    httpURLConnection.setInstanceFollowRedirects(HttpURLConnection.getFollowRedirects());
    return httpURLConnection;
  }
  
  public static interface a {
    String a(String param1String);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\d\a\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */