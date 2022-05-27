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

public class TipsWhiteDialog extends Dialog {
  private ImageView closeView;
  
  private TextView content;
  
  private String contentStr;
  
  private Context mContext;
  
  public OnPositionLisenter onPositionLisenter;
  
  private TextView smallContent;
  
  private String smallContentStr;
  
  public TipsWhiteDialog(Context paramContext, int paramInt) {
    super(paramContext, paramInt);
    this.mContext = paramContext;
  }
  
  public TipsWhiteDialog(Context paramContext, String paramString) {
    this(paramContext, 2131689644);
    this.mContext = paramContext;
    this.contentStr = paramString;
  }
  
  public TipsWhiteDialog(Context paramContext, String paramString1, String paramString2) {
    this(paramContext, 2131689644);
    this.mContext = paramContext;
    this.contentStr = paramString1;
    this.smallContentStr = paramString2;
  }
  
  private void initView() {
    this.content = (TextView)findViewById(2131296425);
    this.closeView = (ImageView)findViewById(2131296414);
    this.smallContent = (TextView)findViewById(2131296677);
    this.closeView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            TipsWhiteDialog.this.dismiss();
          }
        });
    setTipContent();
    setSmallContent();
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setCanceledOnTouchOutside(false);
    setContentView(2131427439);
    Window window = getWindow();
    WindowManager.LayoutParams layoutParams = window.getAttributes();
    layoutParams.width = (int)(ScreenUtils.getScreenWidth(this.mContext) * 0.8D);
    window.setAttributes(layoutParams);
    initView();
  }
  
  public void setOnPositionLisenter(OnPositionLisenter paramOnPositionLisenter) {
    this.onPositionLisenter = paramOnPositionLisenter;
  }
  
  public void setSmallContent() {
    if (this.smallContent == null)
      this.smallContent = (TextView)findViewById(2131296418); 
    if (!TextUtils.isEmpty(this.smallContentStr))
      this.smallContent.setText(this.smallContentStr); 
  }
  
  public void setTipContent() {
    if (this.content == null)
      this.content = (TextView)findViewById(2131296425); 
    if (!TextUtils.isEmpty(this.contentStr))
      this.content.setText(this.contentStr); 
  }
  
  public static interface OnPositionLisenter {
    void onPositionLisenter();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\dialog\TipsWhiteDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */