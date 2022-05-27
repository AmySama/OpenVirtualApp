package io.virtualapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import io.virtualapp.Utils.ScreenUtils;

public class ConfirmDialog extends Dialog implements View.OnClickListener {
  private String closeStr;
  
  private TextView closeView;
  
  private TextView confirm;
  
  private String confirmStr;
  
  private TextView content;
  
  private String contentStr;
  
  private Context mContext;
  
  public OnPositionLisenter onPositionLisenter;
  
  private TextView title;
  
  private String titleStr;
  
  public ConfirmDialog(Context paramContext) {
    super(paramContext);
    this.mContext = paramContext;
  }
  
  public ConfirmDialog(Context paramContext, int paramInt, String paramString1, String paramString2, String paramString3, String paramString4) {
    super(paramContext, paramInt);
    this.mContext = paramContext;
    this.titleStr = paramString1;
    this.contentStr = paramString2;
    this.confirmStr = paramString3;
    this.closeStr = paramString4;
  }
  
  private void initView() {
    this.title = (TextView)findViewById(2131296721);
    this.content = (TextView)findViewById(2131296425);
    this.confirm = (TextView)findViewById(2131296420);
    TextView textView = (TextView)findViewById(2131296396);
    this.closeView = textView;
    textView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            ConfirmDialog.this.dismiss();
          }
        });
    this.confirm.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            ConfirmDialog.this.onPositionLisenter.onPositionLisenter();
          }
        });
    setTipTitle();
    setTipContent();
    setConfirmContent();
    setCloseContent();
  }
  
  public void onClick(View paramView) {}
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131427424);
    setCanceledOnTouchOutside(false);
    Window window = getWindow();
    WindowManager.LayoutParams layoutParams = window.getAttributes();
    layoutParams.width = (int)(ScreenUtils.getScreenWidth(this.mContext) * 0.8D);
    window.setAttributes(layoutParams);
    initView();
  }
  
  public void setCloseContent() {
    if (this.closeView == null)
      this.closeView = (TextView)findViewById(2131296396); 
    if (!TextUtils.isEmpty(this.closeStr))
      this.closeView.setText(this.closeStr); 
  }
  
  public void setConfirmContent() {
    if (this.confirm == null)
      this.confirm = (TextView)findViewById(2131296420); 
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\dialog\ConfirmDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */