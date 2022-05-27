package io.virtualapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import io.virtualapp.Utils.ScreenUtils;

public class AppManageDialog extends Dialog implements View.OnClickListener {
  public static final String APPMANAGECHANGE = "appmanagechange";
  
  public static final String APPMANAGEDELETE = "appmanagedelete";
  
  public static final String APPMANAGELOCATION = "appmanagelocation";
  
  public static final String APPMANAGESHORTCUT = "appmanageshortcut";
  
  private LinearLayout changeLayout;
  
  private LinearLayout deleteLayout;
  
  private String mContent;
  
  private Context mContext;
  
  private PositionLisenter mLisenter;
  
  private String manageType;
  
  private LinearLayout shortcutLayout;
  
  public AppManageDialog(Context paramContext) {
    super(paramContext);
    this.mContext = paramContext;
  }
  
  public AppManageDialog(Context paramContext, int paramInt, String paramString) {
    super(paramContext, paramInt);
    this.mContext = paramContext;
    this.mContent = paramString;
  }
  
  private void initData() {}
  
  private void initView() {
    findViewById(2131296414).setOnClickListener(this);
    this.deleteLayout = (LinearLayout)findViewById(2131296564);
    this.changeLayout = (LinearLayout)findViewById(2131296563);
    this.shortcutLayout = (LinearLayout)findViewById(2131296565);
    this.deleteLayout.setOnClickListener(this);
    this.changeLayout.setOnClickListener(this);
    this.shortcutLayout.setOnClickListener(this);
  }
  
  public void onClick(View paramView) {
    if (paramView.getId() == 2131296414) {
      dismiss();
    } else if (paramView.getId() == 2131296563) {
      this.mLisenter.setValue("appmanagechange");
      dismiss();
    } else if (paramView.getId() == 2131296565) {
      this.mLisenter.setValue("appmanageshortcut");
      dismiss();
    } else if (paramView.getId() == 2131296564) {
      this.mLisenter.setValue("appmanagedelete");
      dismiss();
    } 
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(2131427421);
    setCanceledOnTouchOutside(false);
    setCancelable(true);
    Window window = getWindow();
    WindowManager.LayoutParams layoutParams = window.getAttributes();
    layoutParams.width = (int)(ScreenUtils.getScreenWidth(this.mContext) * 0.8D);
    window.setGravity(17);
    window.setAttributes(layoutParams);
    initView();
    initData();
  }
  
  public void setPositionLisenter(PositionLisenter paramPositionLisenter) {
    this.mLisenter = paramPositionLisenter;
  }
  
  public static interface PositionLisenter {
    void setValue(String param1String);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\dialog\AppManageDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */