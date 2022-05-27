package io.virtualapp.Utils;

import android.content.Context;
import android.widget.Toast;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class PayUtils {
  public static void WeChatPay(Context paramContext, String paramString1, String paramString2, String paramString3) {
    IWXAPI iWXAPI = WXAPIFactory.createWXAPI(paramContext, "wxff78878e744f199f");
    if (!iWXAPI.isWXAppInstalled()) {
      Toast.makeText(paramContext, "请先安装微信！", 1).show();
      return;
    } 
    iWXAPI.registerApp("wxff78878e744f199f");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\Utils\PayUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */