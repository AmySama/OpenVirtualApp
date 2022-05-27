package io.virtualapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import io.virtualapp.Utils.ScreenUtils;

public class SelectPayTypeDialog extends Dialog implements View.OnClickListener {
  private String accessToken;
  
  private String bizid;
  
  private String integral;
  
  private Context mContext;
  
  private String total;
  
  private TextView tvAliPay;
  
  private TextView tvContent;
  
  private TextView tvWechatPay;
  
  public SelectPayTypeDialog(Context paramContext) {
    super(paramContext);
    this.mContext = paramContext;
  }
  
  public SelectPayTypeDialog(Context paramContext, int paramInt, String paramString1, String paramString2, String paramString3, String paramString4) {
    super(paramContext, paramInt);
    this.mContext = paramContext;
    this.accessToken = paramString1;
    this.total = paramString2;
    this.bizid = paramString3;
    this.integral = paramString4;
  }
  
  protected SelectPayTypeDialog(Context paramContext, boolean paramBoolean, DialogInterface.OnCancelListener paramOnCancelListener) {
    super(paramContext, paramBoolean, paramOnCancelListener);
    this.mContext = paramContext;
  }
  
  private void initData() {
    if (TextUtils.isEmpty(this.total))
      this.total = "0"; 
    if (TextUtils.isEmpty(this.integral))
      this.integral = "0"; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("<font  color='#ed1c24'>");
    stringBuilder.append(this.mContext.getResources().getString(2131623937, new Object[] { this.total }));
    stringBuilder.append("</font>充值");
    stringBuilder.append(this.integral);
    stringBuilder.append("积分");
    String str = stringBuilder.toString();
    this.tvContent.setText((CharSequence)Html.fromHtml(str));
  }
  
  private void initView() {
    findViewById(2131296414).setOnClickListener(this);
    this.tvContent = (TextView)findViewById(2131296425);
    TextView textView = (TextView)findViewById(2131296801);
    this.tvWechatPay = textView;
    textView.setOnClickListener(this);
    textView = (TextView)findViewById(2131296305);
    this.tvAliPay = textView;
    textView.setOnClickListener(this);
  }
  
  public void onClick(View paramView) {
    if (paramView.getId() == 2131296414) {
      dismiss();
    } else if (paramView.getId() == 2131296801) {
      dismiss();
    } else if (paramView.getId() == 2131296305) {
      dismiss();
    } 
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131427430);
    setCanceledOnTouchOutside(false);
    setCancelable(true);
    Window window = getWindow();
    WindowManager.LayoutParams layoutParams = window.getAttributes();
    layoutParams.width = (int)(ScreenUtils.getScreenWidth(this.mContext) * 0.8D);
    window.setAttributes(layoutParams);
    initView();
    initData();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\dialog\SelectPayTypeDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */