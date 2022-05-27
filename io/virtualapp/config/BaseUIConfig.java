package io.virtualapp.config;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.mobile.auth.gatewayauth.PhoneNumberAuthHelper;
import com.stub.StubApp;
import io.virtualapp.Utils.AppUtils;

public abstract class BaseUIConfig {
  public Activity mActivity;
  
  public PhoneNumberAuthHelper mAuthHelper;
  
  public Context mContext;
  
  public int mScreenHeightDp;
  
  public int mScreenWidthDp;
  
  public BaseUIConfig(Activity paramActivity, PhoneNumberAuthHelper paramPhoneNumberAuthHelper) {
    this.mActivity = paramActivity;
    this.mContext = StubApp.getOrigApplicationContext(paramActivity.getApplicationContext());
    this.mAuthHelper = paramPhoneNumberAuthHelper;
  }
  
  public static BaseUIConfig init(int paramInt, Activity paramActivity, PhoneNumberAuthHelper paramPhoneNumberAuthHelper) {
    return (paramInt != 0) ? null : new FullPortConfig(paramActivity, paramPhoneNumberAuthHelper);
  }
  
  public abstract void configAuthPage();
  
  protected View initSwitchView(int paramInt) {
    TextView textView = new TextView((Context)this.mActivity);
    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, AppUtils.dp2px((Context)this.mActivity, 50.0F));
    layoutParams.setMargins(0, AppUtils.dp2px(this.mContext, paramInt), 0, 0);
    layoutParams.addRule(14, -1);
    textView.setText(2131624065);
    textView.setTextColor(-16777216);
    textView.setTextSize(2, 16.0F);
    textView.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    return (View)textView;
  }
  
  public void onResume() {}
  
  protected void updateScreenSize(int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mContext : Landroid/content/Context;
    //   4: astore_2
    //   5: aload_2
    //   6: aload_2
    //   7: invokestatic getPhoneHeightPixels : (Landroid/content/Context;)I
    //   10: i2f
    //   11: invokestatic px2dp : (Landroid/content/Context;F)I
    //   14: istore_3
    //   15: aload_0
    //   16: getfield mContext : Landroid/content/Context;
    //   19: astore_2
    //   20: aload_2
    //   21: aload_2
    //   22: invokestatic getPhoneWidthPixels : (Landroid/content/Context;)I
    //   25: i2f
    //   26: invokestatic px2dp : (Landroid/content/Context;F)I
    //   29: istore #4
    //   31: aload_0
    //   32: getfield mActivity : Landroid/app/Activity;
    //   35: invokevirtual getWindowManager : ()Landroid/view/WindowManager;
    //   38: invokeinterface getDefaultDisplay : ()Landroid/view/Display;
    //   43: invokevirtual getRotation : ()I
    //   46: istore #5
    //   48: iload_1
    //   49: istore #6
    //   51: iload_1
    //   52: iconst_3
    //   53: if_icmpne -> 65
    //   56: aload_0
    //   57: getfield mActivity : Landroid/app/Activity;
    //   60: invokevirtual getRequestedOrientation : ()I
    //   63: istore #6
    //   65: iload #6
    //   67: ifeq -> 115
    //   70: iload #6
    //   72: bipush #6
    //   74: if_icmpeq -> 115
    //   77: iload #6
    //   79: bipush #11
    //   81: if_icmpne -> 87
    //   84: goto -> 115
    //   87: iload #6
    //   89: iconst_1
    //   90: if_icmpeq -> 110
    //   93: iload #6
    //   95: bipush #7
    //   97: if_icmpeq -> 110
    //   100: iload #5
    //   102: istore_1
    //   103: iload #6
    //   105: bipush #12
    //   107: if_icmpne -> 117
    //   110: iconst_2
    //   111: istore_1
    //   112: goto -> 117
    //   115: iconst_1
    //   116: istore_1
    //   117: iload_1
    //   118: ifeq -> 153
    //   121: iload_1
    //   122: iconst_1
    //   123: if_icmpeq -> 139
    //   126: iload_1
    //   127: iconst_2
    //   128: if_icmpeq -> 153
    //   131: iload_1
    //   132: iconst_3
    //   133: if_icmpeq -> 139
    //   136: goto -> 164
    //   139: aload_0
    //   140: iload_3
    //   141: putfield mScreenWidthDp : I
    //   144: aload_0
    //   145: iload #4
    //   147: putfield mScreenHeightDp : I
    //   150: goto -> 164
    //   153: aload_0
    //   154: iload #4
    //   156: putfield mScreenWidthDp : I
    //   159: aload_0
    //   160: iload_3
    //   161: putfield mScreenHeightDp : I
    //   164: return
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\config\BaseUIConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */