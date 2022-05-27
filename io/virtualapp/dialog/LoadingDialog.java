package io.virtualapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.widget.TextView;

public class LoadingDialog extends Dialog implements DialogInterface.OnKeyListener, DialogInterface.OnShowListener, DialogInterface.OnDismissListener {
  public Context context;
  
  private TextView mTextView;
  
  CountDownTimer timer = new CountDownTimer(11000L, 1000L) {
      public void onFinish() {
        LoadingDialog.this.dismiss();
      }
      
      public void onTick(long param1Long) {
        LoadingDialog.this.dismiss();
      }
    };
  
  public LoadingDialog(Context paramContext) {
    super(paramContext, 2131689644);
    this.context = paramContext;
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131427427);
    this.mTextView = (TextView)findViewById(2131296580);
    setCancelable(true);
    setCanceledOnTouchOutside(false);
  }
  
  public void onDismiss(DialogInterface paramDialogInterface) {
    this.timer.cancel();
  }
  
  public boolean onKey(DialogInterface paramDialogInterface, int paramInt, KeyEvent paramKeyEvent) {
    return false;
  }
  
  public void onShow(DialogInterface paramDialogInterface) {
    this.timer.start();
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\dialog\LoadingDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */