package io.virtualapp.webview;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.stub.StubApp;
import io.virtualapp.abs.ui.VActivity;

public class WebViewActivity extends VActivity implements View.OnClickListener {
  private LinearLayout backLayout;
  
  private TextView titleView;
  
  private WebView webView;
  
  static {
    StubApp.interface11(9862);
  }
  
  protected void initView() {
    this.backLayout = (LinearLayout)findViewById(2131296568);
    this.titleView = (TextView)findViewById(2131296570);
    this.webView = (WebView)findViewById(2131296798);
    this.backLayout.setOnClickListener(this);
    this.webView.loadUrl(getIntent().getStringExtra("weburl"));
    this.titleView.setText(getIntent().getStringExtra("title"));
    WebSettings webSettings = this.webView.getSettings();
    webSettings.setUseWideViewPort(true);
    webSettings.setLoadWithOverviewMode(true);
    webSettings.setJavaScriptEnabled(true);
    this.webView.setWebViewClient(new WebViewClient() {
          public boolean shouldOverrideUrlLoading(WebView param1WebView, String param1String) {
            param1WebView.loadUrl(param1String);
            return true;
          }
        });
  }
  
  public void onClick(View paramView) {
    if (2131296568 == paramView.getId())
      finish(); 
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  protected void onDestroy() {
    WebView webView = this.webView;
    if (webView != null) {
      webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
      this.webView.clearHistory();
      ((ViewGroup)this.webView.getParent()).removeView((View)this.webView);
      this.webView.destroy();
      this.webView = null;
    } 
    super.onDestroy();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
    if (paramInt == 4 && this.webView.canGoBack()) {
      this.webView.goBack();
      return true;
    } 
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\webview\WebViewActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */