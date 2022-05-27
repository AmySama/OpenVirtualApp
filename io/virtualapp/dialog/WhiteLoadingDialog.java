package io.virtualapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

public class WhiteLoadingDialog extends Dialog {
  public Context context;
  
  private TextView mTextView;
  
  public WhiteLoadingDialog(Context paramContext) {
    super(paramContext, 2131689644);
    this.context = paramContext;
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131427438);
    this.mTextView = (TextView)findViewById(2131296580);
    setCancelable(true);
    setCanceledOnTouchOutside(false);
  }
  
  public void setText(int paramInt) {
    this.mTextView.setText(this.context.getResources().getString(paramInt));
  }
  
  public void setText(String paramString) {
    this.mTextView.setText(paramString);
  }
  
  public void show(int paramInt) {
    show();
    this.mTextView.setText(paramInt);
  }
  
  public void show(String paramString) {
    show();
    this.mTextView.setText(paramString);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\dialog\WhiteLoadingDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */