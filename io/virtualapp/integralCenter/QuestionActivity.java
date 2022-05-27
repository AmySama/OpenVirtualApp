package io.virtualapp.integralCenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.stub.StubApp;
import io.virtualapp.Utils.ToastUtil;
import io.virtualapp.abs.ui.VActivity;
import io.virtualapp.http.HttpCall;
import org.json.JSONObject;

public class QuestionActivity extends VActivity implements View.OnClickListener, HttpCall {
  private ImageView bugImg;
  
  private RelativeLayout bugLayout;
  
  private TextView bugText;
  
  private EditText etContact;
  
  private EditText etContent;
  
  Activity myContext;
  
  private ImageView otherImg;
  
  private RelativeLayout otherLayout;
  
  private TextView otherText;
  
  private ImageView promoteImg;
  
  private RelativeLayout promoteLayout;
  
  private TextView promoteText;
  
  private TextView submitBtn;
  
  private int type = 1;
  
  private TextView zixunBtn;
  
  static {
    StubApp.interface11(9825);
  }
  
  private void initChoose() {
    this.bugText.setTextColor(ContextCompat.getColor((Context)this, 2131099846));
    this.promoteText.setTextColor(ContextCompat.getColor((Context)this, 2131099846));
    this.otherText.setTextColor(ContextCompat.getColor((Context)this, 2131099846));
    this.bugImg.setImageDrawable(ContextCompat.getDrawable((Context)this, 2131231007));
    this.promoteImg.setImageDrawable(ContextCompat.getDrawable((Context)this, 2131231007));
    this.otherImg.setImageDrawable(ContextCompat.getDrawable((Context)this, 2131231007));
  }
  
  private void submit() {
    String str1 = this.etContent.getText().toString();
    String str2 = this.etContact.getText().toString();
    if (TextUtils.isEmpty(str1)) {
      ToastUtil.showToast("请填写问题描述");
      return;
    } 
    if (TextUtils.isEmpty(str2))
      ToastUtil.showToast("请填写联系方式，方便与您联系"); 
  }
  
  protected void initView() {
    ((TextView)findViewById(2131296401)).setText("问题反馈");
    this.bugLayout = (RelativeLayout)findViewById(2131296389);
    this.promoteLayout = (RelativeLayout)findViewById(2131296626);
    this.otherLayout = (RelativeLayout)findViewById(2131296606);
    this.bugText = (TextView)findViewById(2131296390);
    this.promoteText = (TextView)findViewById(2131296627);
    this.otherText = (TextView)findViewById(2131296607);
    this.bugImg = (ImageView)findViewById(2131296388);
    this.promoteImg = (ImageView)findViewById(2131296625);
    this.otherImg = (ImageView)findViewById(2131296605);
    this.etContent = (EditText)findViewById(2131296530);
    this.etContact = (EditText)findViewById(2131296527);
    this.submitBtn = (TextView)findViewById(2131296695);
    this.zixunBtn = (TextView)findViewById(2131296812);
    this.bugLayout.setOnClickListener(this);
    this.promoteLayout.setOnClickListener(this);
    this.otherLayout.setOnClickListener(this);
    this.submitBtn.setOnClickListener(this);
    this.zixunBtn.setOnClickListener(this);
    findViewById(2131296568).setOnClickListener(this);
  }
  
  public void onClick(View paramView) {
    Intent intent;
    switch (paramView.getId()) {
      default:
        return;
      case 2131296812:
        intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("http://www.vxiaoya.com/kf/kefu.html"));
        startActivity(intent);
      case 2131296695:
        submit();
      case 2131296626:
        initChoose();
        this.type = 2;
        this.promoteText.setTextColor(ContextCompat.getColor((Context)this, 2131099843));
        this.promoteImg.setImageDrawable(ContextCompat.getDrawable((Context)this, 2131231006));
      case 2131296606:
        initChoose();
        this.type = 3;
        this.otherText.setTextColor(ContextCompat.getColor((Context)this, 2131099843));
        this.otherImg.setImageDrawable(ContextCompat.getDrawable((Context)this, 2131231006));
      case 2131296568:
        finish();
      case 2131296389:
        break;
    } 
    initChoose();
    this.type = 1;
    this.bugText.setTextColor(ContextCompat.getColor((Context)this, 2131099843));
    this.bugImg.setImageDrawable(ContextCompat.getDrawable((Context)this, 2131231006));
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  public void onError() {}
  
  protected void onRestart() {
    super.onRestart();
  }
  
  protected void onResume() {
    super.onResume();
  }
  
  public void onSuccess(String paramString, JSONObject paramJSONObject) {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\integralCenter\QuestionActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */