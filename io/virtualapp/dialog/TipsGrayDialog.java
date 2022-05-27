package io.virtualapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import io.virtualapp.Utils.ScreenUtils;

public class TipsGrayDialog extends Dialog {
  private TextView content;
  
  private String contentStr;
  
  private Context mContext;
  
  private LinearLayout tipsLayout;
  
  public TipsGrayDialog(Context paramContext, int paramInt) {
    super(paramContext, paramInt);
    this.mContext = paramContext;
  }
  
  public TipsGrayDialog(Context paramContext, String paramString) {
    this(paramContext, 2131689644);
    this.mContext = paramContext;
    this.contentStr = paramString;
  }
  
  private void initView() {
    this.content = (TextView)findViewById(2131296425);
    LinearLayout linearLayout = (LinearLayout)findViewById(2131296720);
    this.tipsLayout = linearLayout;
    linearLayout.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            TipsGrayDialog.this.dismiss();
          }
        });
    setTipContent();
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setCanceledOnTouchOutside(false);
    setContentView(2131427426);
    Window window = getWindow();
    WindowManager.LayoutParams layoutParams = window.getAttributes();
    layoutParams.width = (int)(ScreenUtils.getScreenWidth(this.mContext) * 0.7D);
    window.setGravity(80);
    layoutParams.alpha = 0.6F;
    window.setAttributes(layoutParams);
    window.setDimAmount(0.0F);
    setCanceledOnTouchOutside(true);
    setCancelable(true);
    initView();
  }
  
  public void setTipContent() {
    if (this.content == null)
      this.content = (TextView)findViewById(2131296425); 
    if (!TextUtils.isEmpty(this.contentStr))
      this.content.setText(this.contentStr); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\dialog\TipsGrayDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */