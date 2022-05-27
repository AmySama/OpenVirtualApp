package io.virtualapp.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CostomSearchView extends LinearLayout {
  private EditText mCenterEditText;
  
  private Context mContext;
  
  private LinearLayout mLayout;
  
  private TextView mLeftTextView;
  
  private TextView mRightTextView;
  
  public CostomSearchView(Context paramContext) {
    super(paramContext);
    this.mContext = paramContext;
    initView();
  }
  
  public CostomSearchView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    this.mContext = paramContext;
    initView();
  }
  
  public CostomSearchView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    this.mContext = paramContext;
    initView();
  }
  
  private void initView() {
    View view = LayoutInflater.from(this.mContext).inflate(2131427483, (ViewGroup)this);
    this.mLayout = (LinearLayout)view.findViewById(2131296561);
    this.mLeftTextView = (TextView)view.findViewById(2131296569);
    this.mCenterEditText = (EditText)view.findViewById(2131296399);
    this.mRightTextView = (TextView)view.findViewById(2131296638);
  }
  
  public EditText getCenterEditText() {
    return this.mCenterEditText;
  }
  
  public TextView getLeftTextView() {
    return this.mLeftTextView;
  }
  
  public TextView getRightTextView() {
    return this.mRightTextView;
  }
  
  public void setBackground(int paramInt) {
    this.mLayout.setBackgroundResource(paramInt);
  }
  
  public void setEditTextHint(String paramString) {
    this.mCenterEditText.setHint(paramString);
  }
  
  public void setLeftText(String paramString) {
    this.mLeftTextView.setText(paramString);
  }
  
  public void setRightText(String paramString) {
    this.mRightTextView.setText(paramString);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\CostomSearchView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */