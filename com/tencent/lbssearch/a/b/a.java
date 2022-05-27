package com.tencent.lbssearch.a.b;

import android.content.Context;
import com.stub.StubApp;
import com.tencent.lbssearch.a.d.a.g;
import com.tencent.lbssearch.a.d.a.h;
import com.tencent.lbssearch.a.d.j;
import com.tencent.lbssearch.a.d.k;
import com.tencent.lbssearch.a.d.l;
import com.tencent.lbssearch.a.d.q;
import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;

public class a {
  private static k a;
  
  private static String a(String paramString, d paramd) {
    if (paramString == null)
      return null; 
    try {
      String str1 = URLDecoder.decode(paramString, "UTF-8");
      URL uRL = new URL();
      this(str1);
      URI uRI = new URI();
      this(uRL.getProtocol(), uRL.getUserInfo(), uRL.getHost(), uRL.getPort(), uRL.getPath(), uRL.getQuery(), uRL.getRef());
      String str2 = uRI.toASCIIString();
      paramString = str2;
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      unsupportedEncodingException.printStackTrace();
    } catch (MalformedURLException malformedURLException) {
      malformedURLException.printStackTrace();
    } catch (URISyntaxException uRISyntaxException) {
      uRISyntaxException.printStackTrace();
    } 
    String str = paramString;
    if (paramd != null) {
      String str1 = paramd.a().trim();
      str = paramString;
      if (!str1.equals("")) {
        String str2 = "?";
        str = paramString;
        if (!str1.equals("?")) {
          StringBuilder stringBuilder2 = new StringBuilder();
          stringBuilder2.append(paramString);
          if (paramString.contains("?"))
            str2 = "&"; 
          stringBuilder2.append(str2);
          paramString = stringBuilder2.toString();
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append(paramString);
          stringBuilder1.append(str1);
          str = stringBuilder1.toString();
        } 
      } 
    } 
    return str;
  }
  
  public static <T> void a(Context paramContext, String paramString, d paramd, Class<T> paramClass, HttpResponseListener paramHttpResponseListener) {
    if (a == null)
      a = h.a(StubApp.getOrigApplicationContext(paramContext.getApplicationContext())); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("url:");
    stringBuilder.append(paramString);
    com.tencent.lbssearch.a.c.a.b(stringBuilder.toString());
    if (paramd != null)
      com.tencent.lbssearch.a.c.a.b(paramd.toString()); 
    g g = new g(a(paramString, paramd), new l.b<String>(paramClass, paramHttpResponseListener) {
          public void a(String param1String) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("http get return data:\n");
            stringBuilder.append(param1String.toString());
            com.tencent.lbssearch.a.c.a.b(stringBuilder.toString());
            BaseObject baseObject = b.<BaseObject>a(param1String.toString(), this.a);
            if (this.b != null)
              if (baseObject != null && baseObject.isStatusOk()) {
                this.b.onSuccess(baseObject.status, baseObject);
              } else if (baseObject != null) {
                this.b.onFailure(baseObject.status, baseObject.message, null);
              } else {
                this.b.onFailure(-1, "unknown error", null);
              }  
          }
        }new l.a() {
          public void a(q param1q) {}
        });
    a.a((j)g);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\b\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */