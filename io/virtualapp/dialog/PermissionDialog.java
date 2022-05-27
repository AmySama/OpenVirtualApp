package io.virtualapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import io.virtualapp.Utils.ScreenUtils;

public class PermissionDialog extends Dialog {
  private ImageView closeView;
  
  private TextView confirm;
  
  private String confirmStr;
  
  private TextView content;
  
  private String contentStr;
  
  private Context mContext;
  
  public OnCloseLisenter onCloseLisenter;
  
  public OnPositionLisenter onPositionLisenter;
  
  private TextView smallContent;
  
  private String smallContentStr;
  
  private TextView title;
  
  private String titleStr;
  
  public PermissionDialog(Context paramContext, int paramInt) {
    super(paramContext, paramInt);
    this.mContext = paramContext;
  }
  
  public PermissionDialog(Context paramContext, String paramString1, String paramString2) {
    this(paramContext, 2131689644);
    this.mContext = paramContext;
    this.titleStr = paramString1;
    this.contentStr = paramString2;
  }
  
  public PermissionDialog(Context paramContext, String paramString1, String paramString2, String paramString3) {
    this(paramContext, 2131689644);
    this.mContext = paramContext;
    this.titleStr = paramString1;
    this.contentStr = paramString2;
    this.confirmStr = paramString3;
  }
  
  public PermissionDialog(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4) {
    this(paramContext, 2131689644);
    this.mContext = paramContext;
    this.titleStr = paramString1;
    this.contentStr = paramString2;
    this.confirmStr = paramString3;
    this.smallContentStr = paramString4;
  }
  
  private void initView() {
    this.title = (TextView)findViewById(2131296721);
    this.content = (TextView)findViewById(2131296425);
    this.confirm = (TextView)findViewById(2131296418);
    this.closeView = (ImageView)findViewById(2131296414);
    this.smallContent = (TextView)findViewById(2131296677);
    this.closeView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            PermissionDialog.this.onCloseLisenter.onCloseLisenter();
          }
        });
    this.confirm.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            PermissionDialog.this.onPositionLisenter.onPositionLisenter();
          }
        });
    setTipTitle();
    setTipContent();
    setConfirmContent();
    setSmallContent();
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setCanceledOnTouchOutside(false);
    setContentView(2131427434);
    Window window = getWindow();
    WindowManager.LayoutParams layoutParams = window.getAttributes();
    layoutParams.width = (int)(ScreenUtils.getScreenWidth(this.mContext) * 0.8D);
    window.setAttributes(layoutParams);
    initView();
  }
  
  public void setConfirmContent() {
    if (this.confirm == null)
      this.confirm = (TextView)findViewById(2131296418); 
    if (!TextUtils.isEmpty(this.confirmStr))
      this.confirm.setText(this.confirmStr); 
  }
  
  public void setOnCloseLisenter(OnCloseLisenter paramOnCloseLisenter) {
    this.onCloseLisenter = paramOnCloseLisenter;
  }
  
  public void setOnPositionLisenter(OnPositionLisenter paramOnPositionLisenter) {
    this.onPositionLisenter = paramOnPositionLisenter;
  }
  
  public void setSmallContent() {
    if (this.smallContent == null)
      this.smallContent = (TextView)findViewById(2131296677); 
    if (!TextUtils.isEmpty(this.smallContentStr))
      this.smallContent.setText(this.smallContentStr); 
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
  
  public static interface OnCloseLisenter {
    void onCloseLisenter();
  }
  
  public static interface OnPositionLisenter {
    void onPositionLisenter();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\dialog\PermissionDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */