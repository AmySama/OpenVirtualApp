package io.virtualapp.webview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.stub.StubApp;
import io.virtualapp.abs.ui.VActivity;
import io.virtualapp.bean.MessageEvent;
import java.util.HashMap;
import org.greenrobot.eventbus.EventBus;

public class H5PayWebViewActivity extends VActivity implements View.OnClickListener {
  private ImageView backImg;
  
  private ImageView imgQq;
  
  private TextView tvTitle;
  
  private WebView webView;
  
  static {
    StubApp.interface11(9860);
  }
  
  public void onBackPressed() {
    super.onBackPressed();
    EventBus.getDefault().post(new MessageEvent(2));
  }
  
  public void onClick(View paramView) {
    if (paramView.getId() == 2131296515) {
      EventBus.getDefault().post(new MessageEvent(2));
      finish();
    } 
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  public boolean onSupportNavigateUp() {
    finish();
    return true;
  }
  
  private class JavaScriptObject {
    Context mContxt;
    
    public JavaScriptObject(Context param1Context) {
      this.mContxt = param1Context;
    }
    
    @JavascriptInterface
    public void showInfoFromJs(String param1String) {
      Toast.makeText(this.mContxt, "支付成功", 1).show();
      EventBus.getDefault().post(new MessageEvent(2));
      H5PayWebViewActivity.this.finish();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\webview\H5PayWebViewActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */