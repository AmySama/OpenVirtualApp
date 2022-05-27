package io.virtualapp.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import io.virtualapp.bean.HttpBean;
import io.virtualapp.bean.MessageEvent;
import io.virtualapp.http.HttpCall;
import io.virtualapp.http.HttpManger;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class AliPayUtil {
  public static final String APPID = "2018010201504177";
  
  public static final String RSA2_PRIVATE = "";
  
  public static final String RSA_PRIVATE = "";
  
  private static final int SDK_PAY_FLAG = 1;
  
  private Activity mContext;
  
  private Handler mHandler = new Handler() {
      public void handleMessage(Message param1Message) {
        if (param1Message.what == 1) {
          PayResult payResult = new PayResult((Map<String, String>)param1Message.obj);
          payResult.getResult();
          String str = payResult.getResultStatus();
          EventBus.getDefault().post(new MessageEvent(2));
          EventBus.getDefault().post(new MessageEvent(4));
          if (TextUtils.equals(str, "9000")) {
            Toast.makeText((Context)AliPayUtil.this.mContext, "支付成功", 0).show();
          } else {
            Toast.makeText((Context)AliPayUtil.this.mContext, "支付失败", 0).show();
          } 
        } 
      }
    };
  
  public AliPayUtil(Activity paramActivity) {
    this.mContext = paramActivity;
  }
  
  public void payV2(final String orderInfo) {
    if (TextUtils.isEmpty(orderInfo)) {
      (new AlertDialog.Builder((Context)this.mContext)).setTitle("警告").setMessage("未获取到订单信息").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface param1DialogInterface, int param1Int) {}
          }).show();
      return;
    } 
    (new Thread(new Runnable() {
          public void run() {
            Map map = (new PayTask(AliPayUtil.this.mContext)).payV2(orderInfo, true);
            Message message = new Message();
            message.what = 1;
            message.obj = map;
            AliPayUtil.this.mHandler.sendMessage(message);
          }
        })).start();
  }
  
  public void requestOrder(String paramString1, String paramString2, String paramString3) {
    (new HttpManger(new HttpCall() {
          public void onError() {}
          
          public void onSuccess(String param1String, JSONObject param1JSONObject) {
            if (param1JSONObject != null)
              try {
                HttpBean httpBean = (HttpBean)JSON.parseObject(param1JSONObject.toString(), HttpBean.class);
                if (!TextUtils.isEmpty(httpBean.getData().toString())) {
                  JSONObject jSONObject = new JSONObject();
                  this(httpBean.getData().toString());
                  if (param1String.equals(HttpManger.KEY_ALIPAY) && !jSONObject.isNull("orderInfo"))
                    AliPayUtil.this.payV2(jSONObject.getString("orderInfo")); 
                } 
              } catch (JSONException jSONException) {
                jSONException.printStackTrace();
              }  
          }
        })).getAliPayInfo(paramString2, paramString3);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\Utils\AliPayUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */