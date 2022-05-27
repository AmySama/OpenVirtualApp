package io.virtualapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import io.virtualapp.Utils.ScreenUtils;

public class WebDialog extends Dialog {
  private TextView confirm;
  
  private String confirmStr;
  
  private Context mContext;
  
  public OnPositionLisenter onPositionLisenter;
  
  private String titleStr;
  
  private String url;
  
  private WebView webview;
  
  public WebDialog(Context paramContext, int paramInt) {
    super(paramContext, paramInt);
    this.mContext = paramContext;
  }
  
  public WebDialog(Context paramContext, String paramString) {
    this(paramContext, 2131689644);
    this.mContext = paramContext;
    this.url = paramString;
  }
  
  private void initView() {
    TextView textView = (TextView)findViewById(2131296418);
    this.confirm = textView;
    textView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            WebDialog.this.onPositionLisenter.onPositionLisenter();
          }
        });
    WebView webView = (WebView)findViewById(2131296798);
    this.webview = webView;
    webView.loadUrl(this.url);
    WebSettings webSettings = this.webview.getSettings();
    webSettings.setUseWideViewPort(true);
    webSettings.setLoadWithOverviewMode(true);
    webSettings.setJavaScriptEnabled(true);
    this.webview.setWebViewClient(new WebViewClient() {
          public boolean shouldOverrideUrlLoading(WebView param1WebView, String param1String) {
            param1WebView.loadUrl(param1String);
            return true;
          }
        });
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setCanceledOnTouchOutside(false);
    setContentView(2131427437);
    Window window = getWindow();
    WindowManager.LayoutParams layoutParams = window.getAttributes();
    layoutParams.width = (int)(ScreenUtils.getScreenWidth(this.mContext) * 0.8D);
    layoutParams.height = (int)(ScreenUtils.getScreenHeight(this.mContext) * 0.8D);
    window.setAttributes(layoutParams);
    initView();
  }
  
  public void setOnPositionLisenter(OnPositionLisenter paramOnPositionLisenter) {
    this.onPositionLisenter = paramOnPositionLisenter;
  }
  
  public static interface OnPositionLisenter {
    void onPositionLisenter();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\dialog\WebDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */