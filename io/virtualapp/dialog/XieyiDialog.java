package io.virtualapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import io.virtualapp.Utils.ScreenUtils;
import io.virtualapp.webview.WebViewActivity;
import java.util.Random;

public class XieyiDialog extends Dialog {
  private TextView cancel;
  
  private String cancelStr;
  
  private TextView confirm;
  
  private String confirmStr;
  
  private TextView content;
  
  private String contentStr;
  
  private Context mContext;
  
  public OnPositionLisenter onPositionLisenter;
  
  private TextView title;
  
  private String titleStr;
  
  private TextView tvXieyi;
  
  private TextView tvYinsiXieyi;
  
  public XieyiDialog(Context paramContext, int paramInt) {
    super(paramContext, paramInt);
    this.mContext = paramContext;
  }
  
  public XieyiDialog(Context paramContext, String paramString1, String paramString2) {
    this(paramContext, 2131689644);
    this.mContext = paramContext;
    this.titleStr = paramString1;
    this.contentStr = paramString2;
  }
  
  public XieyiDialog(Context paramContext, String paramString1, String paramString2, String paramString3) {
    this(paramContext, 2131689644);
    this.mContext = paramContext;
    this.titleStr = paramString1;
    this.contentStr = paramString2;
    this.confirmStr = paramString3;
  }
  
  public XieyiDialog(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4) {
    this(paramContext, 2131689644);
    this.mContext = paramContext;
    this.titleStr = paramString1;
    this.contentStr = paramString2;
    this.confirmStr = paramString3;
    this.cancelStr = paramString4;
  }
  
  private void initView() {
    this.title = (TextView)findViewById(2131296721);
    this.content = (TextView)findViewById(2131296425);
    this.confirm = (TextView)findViewById(2131296418);
    TextView textView = (TextView)findViewById(2131296394);
    this.cancel = textView;
    textView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            XieyiDialog.this.dismiss();
            System.exit(0);
          }
        });
    this.confirm.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            XieyiDialog.this.onPositionLisenter.onPositionLisenter();
          }
        });
    textView = (TextView)findViewById(2131296809);
    this.tvXieyi = textView;
    textView.getPaint().setAntiAlias(true);
    this.tvXieyi.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            XieyiDialog.this.mContext.startActivity((new Intent(XieyiDialog.this.mContext, WebViewActivity.class)).putExtra("weburl", "https://xiaoyintech.com/user_soft_service_info.html").putExtra("title", XieyiDialog.this.mContext.getResources().getString(2131624054)));
          }
        });
    textView = (TextView)findViewById(2131296810);
    this.tvYinsiXieyi = textView;
    textView.getPaint().setAntiAlias(true);
    this.tvYinsiXieyi.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            Random random = new Random();
            Context context = XieyiDialog.this.mContext;
            Intent intent = new Intent(XieyiDialog.this.mContext, WebViewActivity.class);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("https://xiaoyintech.com/user_privacy.html?t=");
            stringBuilder.append(random.nextInt(9999));
            context.startActivity(intent.putExtra("weburl", stringBuilder.toString()).putExtra("title", "隐私保护"));
          }
        });
    setTipTitle();
    setConfirmContent();
    setCancelContent();
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setCanceledOnTouchOutside(false);
    setContentView(2131427440);
    Window window = getWindow();
    WindowManager.LayoutParams layoutParams = window.getAttributes();
    layoutParams.width = (int)(ScreenUtils.getScreenWidth(this.mContext) * 0.9D);
    window.setAttributes(layoutParams);
    initView();
  }
  
  public void setCancelContent() {
    if (this.cancel == null)
      this.cancel = (TextView)findViewById(2131296394); 
    if (!TextUtils.isEmpty(this.cancelStr))
      this.cancel.setText(this.cancelStr); 
  }
  
  public void setConfirmContent() {
    if (this.confirm == null)
      this.confirm = (TextView)findViewById(2131296418); 
    if (!TextUtils.isEmpty(this.confirmStr))
      this.confirm.setText(this.confirmStr); 
  }
  
  public void setOnPositionLisenter(OnPositionLisenter paramOnPositionLisenter) {
    this.onPositionLisenter = paramOnPositionLisenter;
  }
  
  public void setTipContent() {
    if (this.content == null)
      this.content = (TextView)findViewById(2131296425); 
    this.content.setText(this.contentStr);
  }
  
  public void setTipTitle() {
    if (this.title == null)
      this.title = (TextView)findViewById(2131296721); 
    this.title.setText(this.titleStr);
  }
  
  public static interface OnPositionLisenter {
    void onPositionLisenter();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\dialog\XieyiDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */