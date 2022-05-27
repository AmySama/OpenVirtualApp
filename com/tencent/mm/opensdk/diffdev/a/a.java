package com.tencent.mm.opensdk.diffdev.a;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import com.tencent.mm.opensdk.diffdev.IDiffDevOAuth;
import com.tencent.mm.opensdk.diffdev.OAuthErrCode;
import com.tencent.mm.opensdk.diffdev.OAuthListener;
import com.tencent.mm.opensdk.utils.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class a implements IDiffDevOAuth {
  private Handler a = null;
  
  private List<OAuthListener> b = new ArrayList<OAuthListener>();
  
  private b c;
  
  private OAuthListener d = new a(this);
  
  public void addListener(OAuthListener paramOAuthListener) {
    if (!this.b.contains(paramOAuthListener))
      this.b.add(paramOAuthListener); 
  }
  
  public boolean auth(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, OAuthListener paramOAuthListener) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("start auth, appId = ");
    stringBuilder.append(paramString1);
    Log.i("MicroMsg.SDK.DiffDevOAuth", stringBuilder.toString());
    if (paramString1 == null || paramString1.length() <= 0 || paramString2 == null || paramString2.length() <= 0) {
      Log.d("MicroMsg.SDK.DiffDevOAuth", String.format("auth fail, invalid argument, appId = %s, scope = %s", new Object[] { paramString1, paramString2 }));
      return false;
    } 
    if (this.a == null)
      this.a = new Handler(Looper.getMainLooper()); 
    if (!this.b.contains(paramOAuthListener))
      this.b.add(paramOAuthListener); 
    if (this.c != null) {
      Log.d("MicroMsg.SDK.DiffDevOAuth", "auth, already running, no need to start auth again");
      return true;
    } 
    b b1 = new b(paramString1, paramString2, paramString3, paramString4, paramString5, this.d);
    this.c = b1;
    if (Build.VERSION.SDK_INT >= 11) {
      b1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
    } else {
      b1.execute((Object[])new Void[0]);
    } 
    return true;
  }
  
  public void detach() {
    Log.i("MicroMsg.SDK.DiffDevOAuth", "detach");
    this.b.clear();
    stopAuth();
  }
  
  public void removeAllListeners() {
    this.b.clear();
  }
  
  public void removeListener(OAuthListener paramOAuthListener) {
    this.b.remove(paramOAuthListener);
  }
  
  public boolean stopAuth() {
    boolean bool;
    Log.i("MicroMsg.SDK.DiffDevOAuth", "stopAuth");
    try {
      if (this.c == null) {
        bool = true;
      } else {
        bool = this.c.a();
      } 
    } catch (Exception exception) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("stopAuth fail, ex = ");
      stringBuilder.append(exception.getMessage());
      Log.w("MicroMsg.SDK.DiffDevOAuth", stringBuilder.toString());
      bool = false;
    } 
    this.c = null;
    return bool;
  }
  
  class a implements OAuthListener {
    a(a this$0) {}
    
    public void onAuthFinish(OAuthErrCode param1OAuthErrCode, String param1String) {
      Log.d("MicroMsg.SDK.ListenerWrapper", String.format("onAuthFinish, errCode = %s, authCode = %s", new Object[] { param1OAuthErrCode.toString(), param1String }));
      a.a(this.a, null);
      ArrayList arrayList = new ArrayList();
      arrayList.addAll(a.a(this.a));
      Iterator<OAuthListener> iterator = arrayList.iterator();
      while (iterator.hasNext())
        ((OAuthListener)iterator.next()).onAuthFinish(param1OAuthErrCode, param1String); 
    }
    
    public void onAuthGotQrcode(String param1String, byte[] param1ArrayOfbyte) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("onAuthGotQrcode, qrcodeImgPath = ");
      stringBuilder.append(param1String);
      Log.d("MicroMsg.SDK.ListenerWrapper", stringBuilder.toString());
      ArrayList arrayList = new ArrayList();
      arrayList.addAll(a.a(this.a));
      Iterator<OAuthListener> iterator = arrayList.iterator();
      while (iterator.hasNext())
        ((OAuthListener)iterator.next()).onAuthGotQrcode(param1String, param1ArrayOfbyte); 
    }
    
    public void onQrcodeScanned() {
      Log.d("MicroMsg.SDK.ListenerWrapper", "onQrcodeScanned");
      if (a.b(this.a) != null)
        a.b(this.a).post(new a(this)); 
    }
    
    class a implements Runnable {
      a(a.a this$0) {}
      
      public void run() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(a.a(this.a.a));
        Iterator<OAuthListener> iterator = arrayList.iterator();
        while (iterator.hasNext())
          ((OAuthListener)iterator.next()).onQrcodeScanned(); 
      }
    }
  }
  
  class a implements Runnable {
    a(a this$0) {}
    
    public void run() {
      ArrayList arrayList = new ArrayList();
      arrayList.addAll(a.a(this.a.a));
      Iterator<OAuthListener> iterator = arrayList.iterator();
      while (iterator.hasNext())
        ((OAuthListener)iterator.next()).onQrcodeScanned(); 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\diffdev\a\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */