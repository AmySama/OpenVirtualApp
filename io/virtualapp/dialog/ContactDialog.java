package io.virtualapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import io.virtualapp.App;
import io.virtualapp.Utils.AppUtils;
import io.virtualapp.Utils.ScreenUtils;

public class ContactDialog extends Dialog {
  private ImageView closeView;
  
  private TextView confirm;
  
  private TextView confirmQq;
  
  private String confirmStr;
  
  private TextView content;
  
  private String contentStr;
  
  private Context mContext;
  
  public OnPositionLisenter onPositionLisenter;
  
  private TextView title;
  
  private String titleStr;
  
  public ContactDialog(Context paramContext, int paramInt) {
    super(paramContext, paramInt);
    this.mContext = paramContext;
  }
  
  public ContactDialog(Context paramContext, String paramString1, String paramString2) {
    this(paramContext, 2131689644);
    this.mContext = paramContext;
    this.titleStr = paramString1;
    this.contentStr = paramString2;
  }
  
  public ContactDialog(Context paramContext, String paramString1, String paramString2, String paramString3) {
    this(paramContext, 2131689644);
    this.mContext = paramContext;
    this.titleStr = paramString1;
    this.contentStr = paramString2;
    this.confirmStr = paramString3;
  }
  
  private void initView() {
    this.title = (TextView)findViewById(2131296721);
    this.content = (TextView)findViewById(2131296425);
    this.confirm = (TextView)findViewById(2131296418);
    this.confirmQq = (TextView)findViewById(2131296419);
    ImageView imageView = (ImageView)findViewById(2131296414);
    this.closeView = imageView;
    imageView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            ContactDialog.this.dismiss();
          }
        });
    this.confirm.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            AppUtils.joinQQGroup(ContactDialog.this.mContext, "4MdcZXJu7FQQhHWPvaQ_cIkaf0ZolMV0");
            ContactDialog.this.dismiss();
          }
        });
    this.confirmQq.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            try {
              Context context = ContactDialog.this.mContext;
              Intent intent = new Intent();
              this("android.intent.action.VIEW", Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=1922459020"));
              context.startActivity(intent);
            } catch (Exception exception) {
              Toast.makeText((Context)App.getApp(), "无法跳转到QQ，请检查您是否安装了QQ！", 0).show();
            } 
            ContactDialog.this.dismiss();
          }
        });
    setTipTitle();
    setTipContent();
    setConfirmContent();
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setCanceledOnTouchOutside(false);
    setContentView(2131427425);
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
  
  public void setOnPositionLisenter(OnPositionLisenter paramOnPositionLisenter) {
    this.onPositionLisenter = paramOnPositionLisenter;
  }
  
  public void setTipContent() {
    if (this.content == null)
      this.content = (TextView)findViewById(2131296425); 
    if (!TextUtils.isEmpty(this.contentStr))
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\dialog\ContactDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */