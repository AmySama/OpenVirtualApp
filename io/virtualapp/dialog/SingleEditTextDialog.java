package io.virtualapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import io.virtualapp.Utils.ScreenUtils;

public class SingleEditTextDialog {
  private TextView mCancel;
  
  private TextView mComfir;
  
  private EditText mContentEd;
  
  private Context mContext;
  
  private Dialog mDialog;
  
  private PositionLisenter mLisenter;
  
  private TextView mTitleView;
  
  public SingleEditTextDialog(Context paramContext) {
    this.mContext = paramContext;
    initView();
  }
  
  private void initView() {
    View view = LayoutInflater.from(this.mContext).inflate(2131427432, null);
    Dialog dialog = new Dialog(this.mContext, 2131689644);
    this.mDialog = dialog;
    dialog.setContentView(view);
    Window window = this.mDialog.getWindow();
    WindowManager.LayoutParams layoutParams = window.getAttributes();
    layoutParams.width = (int)(ScreenUtils.getScreenWidth(this.mContext) * 0.8D);
    window.setAttributes(layoutParams);
    this.mTitleView = (TextView)view.findViewById(2131296721);
    this.mContentEd = (EditText)view.findViewById(2131296427);
    this.mCancel = (TextView)view.findViewById(2131296394);
    this.mComfir = (TextView)view.findViewById(2131296417);
    this.mCancel.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            SingleEditTextDialog.this.mDialog.dismiss();
          }
        });
    this.mComfir.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            if (TextUtils.isEmpty(SingleEditTextDialog.this.mContentEd.getText().toString().trim())) {
              SingleEditTextDialog.this.mContentEd.setError("内容不能为空！");
              return;
            } 
            SingleEditTextDialog.this.mLisenter.setValue(SingleEditTextDialog.this.mContentEd.getText().toString().trim());
            SingleEditTextDialog.this.mDialog.dismiss();
          }
        });
  }
  
  public void dismiss() {
    this.mDialog.dismiss();
  }
  
  public void setDialogTitle(int paramInt) {
    if (this.mTitleView != null && !TextUtils.isEmpty(this.mContext.getResources().getString(paramInt)))
      this.mTitleView.setText(this.mContext.getResources().getString(paramInt)); 
  }
  
  public void setDialogTitle(CharSequence paramCharSequence) {
    if (this.mTitleView != null && !TextUtils.isEmpty(paramCharSequence))
      this.mTitleView.setText(paramCharSequence); 
  }
  
  public void setEdHint(int paramInt) {
    if (this.mContentEd != null && !TextUtils.isEmpty(this.mContext.getResources().getString(paramInt)))
      this.mContentEd.setHint(this.mContext.getResources().getString(paramInt)); 
  }
  
  public void setEdHint(CharSequence paramCharSequence) {
    if (this.mContentEd != null && !TextUtils.isEmpty(paramCharSequence))
      this.mContentEd.setHint(paramCharSequence); 
  }
  
  public void setEdInputType(int paramInt) {
    EditText editText = this.mContentEd;
    if (editText != null)
      editText.setInputType(paramInt); 
  }
  
  public void setEdText(int paramInt) {
    String str = this.mContext.getResources().getString(paramInt);
    if (this.mContentEd != null && !TextUtils.isEmpty(str)) {
      this.mContentEd.setText(str);
      this.mContentEd.setSelection(str.length());
    } 
  }
  
  public void setEdText(CharSequence paramCharSequence) {
    if (this.mContentEd != null && !TextUtils.isEmpty(paramCharSequence)) {
      this.mContentEd.setText(paramCharSequence);
      this.mContentEd.setSelection(paramCharSequence.length());
    } 
  }
  
  public void setPositionLisenter(PositionLisenter paramPositionLisenter) {
    this.mLisenter = paramPositionLisenter;
  }
  
  public void show() {
    this.mDialog.show();
  }
  
  public static interface PositionLisenter {
    void setValue(String param1String);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\dialog\SingleEditTextDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */