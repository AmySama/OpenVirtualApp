package io.virtualapp.appManage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.stub.StubApp;
import io.virtualapp.abs.ui.VActivity;
import io.virtualapp.home.models.AppInfoBean;
import io.virtualapp.integralCenter.PersonalCenterActivity;

public class TipRenewalActivity extends VActivity implements View.OnClickListener {
  private TextView delayRenewalBnt;
  
  private AppInfoBean mAppInfoBean;
  
  private TextView nowRenewalBnt;
  
  static {
    StubApp.interface11(9568);
  }
  
  private void initView() {
    this.nowRenewalBnt = (TextView)findViewById(2131296603);
    this.delayRenewalBnt = (TextView)findViewById(2131296436);
    this.nowRenewalBnt.setOnClickListener(this);
    this.delayRenewalBnt.setOnClickListener(this);
  }
  
  public void onClick(View paramView) {
    int i = paramView.getId();
    if (i != 2131296436) {
      if (i == 2131296603) {
        Intent intent = new Intent();
        intent.setClass((Context)this, PersonalCenterActivity.class);
        startActivity(intent);
      } 
    } else {
      finish();
    } 
  }
  
  protected native void onCreate(Bundle paramBundle);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\appManage\TipRenewalActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */