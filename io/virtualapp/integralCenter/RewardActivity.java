package io.virtualapp.integralCenter;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.stub.StubApp;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import io.virtualapp.App;
import io.virtualapp.Utils.BitmapUtil;
import io.virtualapp.Utils.SPUtils;
import io.virtualapp.Utils.ToastUtil;
import io.virtualapp.abs.ui.VActivity;
import io.virtualapp.bean.HttpBean;
import io.virtualapp.bean.MessageEvent;
import io.virtualapp.bean.RewardBean;
import io.virtualapp.dialog.TipsWhiteDialog;
import io.virtualapp.http.HttpCall;
import io.virtualapp.http.HttpManger;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class RewardActivity extends VActivity implements View.OnClickListener, HttpCall {
  private String adJson;
  
  private IWXAPI api;
  
  private ListView appList;
  
  private CountDownTimer cdt;
  
  private boolean isComment;
  
  private MyAdapter mAdapter;
  
  private List<RewardBean> mBeans;
  
  Activity myContext;
  
  private int type = 0;
  
  static {
    StubApp.interface11(9839);
  }
  
  private String buildTransaction(String paramString) {
    if (paramString == null) {
      paramString = String.valueOf(System.currentTimeMillis());
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramString);
      stringBuilder.append(System.currentTimeMillis());
      paramString = stringBuilder.toString();
    } 
    return paramString;
  }
  
  private void initAd() {}
  
  private void initTimer() {
    CountDownTimer countDownTimer = this.cdt;
    if (countDownTimer != null) {
      countDownTimer.cancel();
      this.cdt = null;
    } 
    countDownTimer = new CountDownTimer(5000L, 1000L) {
        public void onFinish() {
          RewardActivity.access$102(RewardActivity.this, true);
        }
        
        public void onTick(long param1Long) {
          RewardActivity.access$102(RewardActivity.this, false);
        }
      };
    this.cdt = countDownTimer;
    countDownTimer.start();
  }
  
  private void shareMoment() {
    WXWebpageObject wXWebpageObject = new WXWebpageObject();
    wXWebpageObject.webpageUrl = "http://sj.qq.com/myapp/detail.htm?apkName=com.sheep2.dkfs";
    WXMediaMessage wXMediaMessage = new WXMediaMessage((WXMediaMessage.IMediaObject)wXWebpageObject);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("好友推荐您下载");
    stringBuilder.append(getResources().getString(2131623977));
    wXMediaMessage.title = stringBuilder.toString();
    wXMediaMessage.description = "无限多开应用与游戏分身,分身支持修改GPS定位位置,伪装各种品牌机型型号.";
    wXMediaMessage.thumbData = BitmapUtil.bmpToByteArray(BitmapFactory.decodeResource(getResources(), 2131558407), true);
    SendMessageToWX.Req req = new SendMessageToWX.Req();
    req.transaction = buildTransaction("webpage");
    req.message = wXMediaMessage;
    req.scene = 1;
    this.api.sendReq((BaseReq)req);
  }
  
  private void shareWechat() {
    WXWebpageObject wXWebpageObject = new WXWebpageObject();
    wXWebpageObject.webpageUrl = "http://sj.qq.com/myapp/detail.htm?apkName=com.sheep2.dkfs";
    WXMediaMessage wXMediaMessage = new WXMediaMessage((WXMediaMessage.IMediaObject)wXWebpageObject);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("好友推荐您下载");
    stringBuilder.append(getResources().getString(2131623977));
    wXMediaMessage.title = stringBuilder.toString();
    wXMediaMessage.description = "无限多开应用与游戏分身,分身支持修改GPS定位位置,伪装各种品牌机型型号.";
    wXMediaMessage.thumbData = BitmapUtil.bmpToByteArray(BitmapFactory.decodeResource(getResources(), 2131558407), true);
    SendMessageToWX.Req req = new SendMessageToWX.Req();
    req.transaction = buildTransaction("webpage");
    req.message = wXMediaMessage;
    req.scene = 0;
    this.api.sendReq((BaseReq)req);
  }
  
  private void toLottery() {
    (new HttpManger(this)).vipGift("-1", "copy_gift_go_lottery");
  }
  
  public void goToMarket(Context paramContext, String paramString) {
    initTimer();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("market://details?id=");
    stringBuilder.append(paramString);
    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(stringBuilder.toString()));
    try {
      paramContext.startActivity(intent);
    } catch (ActivityNotFoundException activityNotFoundException) {
      Toast.makeText(paramContext, "您没有安装应用市场", 0).show();
    } 
  }
  
  protected void initView() {
    ((TextView)findViewById(2131296401)).setText("今日福利");
    this.appList = (ListView)findViewById(2131296314);
    MyAdapter myAdapter = new MyAdapter((Context)this);
    this.mAdapter = myAdapter;
    this.appList.setAdapter((ListAdapter)myAdapter);
    findViewById(2131296568).setOnClickListener(this);
    (new HttpManger(this)).getRewardList();
    this.mAdapter.setPositionLisenter(new OnListItemCliclLisenter() {
          public void setValue(int param1Int) {
            if (param1Int != 1) {
              if (param1Int != 2) {
                if (param1Int != 3) {
                  if (param1Int == 4) {
                    RewardActivity.this.toLottery();
                    RewardActivity.access$102(RewardActivity.this, false);
                  } 
                } else {
                  RewardActivity.access$002(RewardActivity.this, 3);
                  RewardActivity.access$102(RewardActivity.this, false);
                  RewardActivity.this.shareMoment();
                } 
              } else {
                RewardActivity.access$002(RewardActivity.this, 2);
                RewardActivity.access$102(RewardActivity.this, false);
                RewardActivity.this.shareWechat();
              } 
            } else {
              RewardActivity.access$002(RewardActivity.this, 1);
              RewardActivity rewardActivity = RewardActivity.this;
              rewardActivity.goToMarket((Context)rewardActivity, App.getApp().getPackageName());
            } 
          }
        });
  }
  
  public void onClick(View paramView) {
    if (paramView.getId() == 2131296568)
      finish(); 
  }
  
  protected native void onCreate(Bundle paramBundle);
  
  protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
    if (isFinishing() && this.cdt != null)
      this.cdt = null; 
  }
  
  public void onError() {}
  
  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onMesageEvent(MessageEvent paramMessageEvent) {
    if (paramMessageEvent.getType() == 5) {
      Log.d("shareType", String.valueOf(this.type));
      if (this.type == 1)
        (new HttpManger(this)).vipGift("", "copy_gift_market_mark"); 
    } 
  }
  
  protected void onRestart() {
    super.onRestart();
    if (this.isComment) {
      this.isComment = false;
      (new HttpManger(this)).vipGift("", "copy_gift_market_mark");
    } 
    if (this.cdt != null)
      this.cdt = null; 
  }
  
  protected void onResume() {
    super.onResume();
    initAd();
  }
  
  public void onSuccess(String paramString, JSONObject paramJSONObject) {
    HttpBean httpBean = (HttpBean)JSON.parseObject(paramJSONObject.toString(), HttpBean.class);
    if (httpBean != null && !TextUtils.isEmpty(httpBean.getData().toString())) {
      StringBuilder stringBuilder;
      if (httpBean.getStatus().equals("1")) {
        try {
          List<RewardBean> list;
          paramJSONObject = new JSONObject();
          this(httpBean.getData().toString());
          if (paramString.equals(HttpManger.KEY_REWARD_LIST)) {
            if (!paramJSONObject.isNull("list")) {
              list = JSON.parseArray(paramJSONObject.getString("list"), RewardBean.class);
              this.mBeans = list;
              this.mAdapter.setData(list);
            } 
          } else if (list.equals(HttpManger.KEY_VIP_GIFT) && !paramJSONObject.isNull("optcode") && !paramJSONObject.getString("optcode").equals("copy_gift_go_lottery") && !paramJSONObject.isNull("difference") && !paramJSONObject.isNull("value") && Float.valueOf(paramJSONObject.getString("value")).floatValue() > 0.0F) {
            SPUtils.put((Context)this, "my_expried_time", paramJSONObject.getString("difference"));
            SPUtils.put((Context)this, "my_alwaysvip", paramJSONObject.getString("alwaysvip"));
            TipsWhiteDialog tipsWhiteDialog = new TipsWhiteDialog();
            stringBuilder = new StringBuilder();
            this();
            stringBuilder.append("恭喜您，获得VIP时长");
            stringBuilder.append(paramJSONObject.getString("value"));
            stringBuilder.append("天");
            this((Context)this, stringBuilder.toString(), "时间已自动累加到VIP时长里");
            DialogInterface.OnDismissListener onDismissListener = new DialogInterface.OnDismissListener() {
                public void onDismiss(DialogInterface param1DialogInterface) {
                  (new HttpManger(RewardActivity.this)).getRewardList();
                  EventBus.getDefault().post(new MessageEvent(2));
                }
              };
            super(this);
            tipsWhiteDialog.setOnDismissListener(onDismissListener);
            tipsWhiteDialog.show();
          } 
        } catch (JSONException jSONException) {
          jSONException.printStackTrace();
        } 
      } else {
        ToastUtil.showToast(stringBuilder.getInfo());
      } 
    } 
  }
  
  private class MyAdapter extends BaseAdapter {
    private Context mContext;
    
    private List<RewardBean> mDatas;
    
    private RewardActivity.OnListItemCliclLisenter mLisenter;
    
    public MyAdapter(Context param1Context) {
      this.mContext = param1Context;
      this.mDatas = new ArrayList<RewardBean>();
    }
    
    public int getCount() {
      return this.mDatas.size();
    }
    
    public Object getItem(int param1Int) {
      return this.mDatas.get(param1Int);
    }
    
    public long getItemId(int param1Int) {
      return param1Int;
    }
    
    public View getView(int param1Int, View param1View, ViewGroup param1ViewGroup) {
      RewardActivity.ViewHolder viewHolder;
      if (param1View == null) {
        viewHolder = new RewardActivity.ViewHolder();
        param1View = LayoutInflater.from(this.mContext).inflate(2131427452, null);
        viewHolder.rewardTitle = (TextView)param1View.findViewById(2131296554);
        viewHolder.rewardRightTitle = (TextView)param1View.findViewById(2131296553);
        viewHolder.rewardMemo = (TextView)param1View.findViewById(2131296552);
        viewHolder.rewardContent = (TextView)param1View.findViewById(2131296551);
        viewHolder.rewardButton = (TextView)param1View.findViewById(2131296549);
        viewHolder.rewardButtonLayout = (LinearLayout)param1View.findViewById(2131296550);
        viewHolder.rewardImg = (ImageView)param1View.findViewById(2131296632);
        viewHolder.doneImg = (ImageView)param1View.findViewById(2131296556);
        param1View.setTag(viewHolder);
      } else {
        viewHolder = (RewardActivity.ViewHolder)param1View.getTag();
      } 
      final RewardBean bean = this.mDatas.get(param1Int);
      viewHolder.rewardTitle.setText(rewardBean.getTitle());
      viewHolder.rewardMemo.setText(rewardBean.getMemo());
      viewHolder.rewardMemo.setTextColor(RewardActivity.this.getResources().getColor(2131099847));
      viewHolder.rewardRightTitle.setText(rewardBean.getRightTitle());
      viewHolder.rewardContent.setText(rewardBean.getContent());
      viewHolder.rewardButton.setText(rewardBean.getButtonStr());
      viewHolder.rewardImg.setImageDrawable(RewardActivity.this.getResources().getDrawable(2131230991));
      if (rewardBean.isDisabled() && rewardBean.getMethod() != 4 && rewardBean.getMethod() != 2) {
        viewHolder.rewardButton.setText(rewardBean.getButtonDisabledStr());
        viewHolder.rewardButtonLayout.setFocusable(false);
        viewHolder.rewardButtonLayout.setFocusableInTouchMode(false);
        viewHolder.rewardButtonLayout.setClickable(false);
        viewHolder.rewardMemo.setTextColor(RewardActivity.this.getResources().getColor(2131099846));
        viewHolder.doneImg.setVisibility(0);
        viewHolder.rewardButton.setVisibility(8);
        viewHolder.rewardImg.setImageDrawable(RewardActivity.this.getResources().getDrawable(2131230990));
      } else {
        if (rewardBean.getMethod() != 1)
          viewHolder.rewardImg.setImageDrawable(RewardActivity.this.getResources().getDrawable(2131231017)); 
        viewHolder.rewardButton.setText(rewardBean.getButtonStr());
        viewHolder.rewardButtonLayout.setFocusable(true);
        viewHolder.rewardButtonLayout.setClickable(true);
        viewHolder.rewardButtonLayout.setFocusableInTouchMode(true);
        viewHolder.doneImg.setVisibility(8);
        viewHolder.rewardButton.setVisibility(0);
        viewHolder.rewardButtonLayout.setOnClickListener(new View.OnClickListener() {
              public void onClick(View param2View) {
                RewardActivity.MyAdapter.this.mLisenter.setValue(bean.getMethod());
              }
            });
      } 
      return param1View;
    }
    
    public void setData(List<RewardBean> param1List) {
      this.mDatas = param1List;
      notifyDataSetChanged();
    }
    
    public void setPositionLisenter(RewardActivity.OnListItemCliclLisenter param1OnListItemCliclLisenter) {
      this.mLisenter = param1OnListItemCliclLisenter;
    }
  }
  
  class null implements View.OnClickListener {
    public void onClick(View param1View) {
      this.this$1.mLisenter.setValue(bean.getMethod());
    }
  }
  
  public static interface OnListItemCliclLisenter {
    void setValue(int param1Int);
  }
  
  private class ViewHolder {
    ImageView doneImg;
    
    TextView rewardButton;
    
    LinearLayout rewardButtonLayout;
    
    TextView rewardContent;
    
    ImageView rewardImg;
    
    TextView rewardMemo;
    
    TextView rewardRightTitle;
    
    TextView rewardTitle;
    
    private ViewHolder() {}
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\integralCenter\RewardActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */