package com.tencent.mm.opensdk.channel;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import com.tencent.mm.opensdk.channel.a.a;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.b;

public class MMessageActV2 {
  public static final String DEFAULT_ENTRY_CLASS_NAME = ".wxapi.WXEntryActivity";
  
  public static final String MM_ENTRY_PACKAGE_NAME = "com.tencent.mm";
  
  public static final String MM_MSG_ENTRY_CLASS_NAME = "com.tencent.mm.plugin.base.stub.WXEntryActivity";
  
  private static final String TAG = "MicroMsg.SDK.MMessageAct";
  
  public static boolean send(Context paramContext, Args paramArgs) {
    String str1;
    if (paramContext == null || paramArgs == null) {
      str1 = "send fail, invalid argument";
      Log.e("MicroMsg.SDK.MMessageAct", str1);
      return false;
    } 
    if (b.b(paramArgs.targetPkgName)) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("send fail, invalid targetPkgName, targetPkgName = ");
      stringBuilder1.append(paramArgs.targetPkgName);
      str1 = stringBuilder1.toString();
      Log.e("MicroMsg.SDK.MMessageAct", str1);
      return false;
    } 
    if (b.b(paramArgs.targetClassName)) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(paramArgs.targetPkgName);
      stringBuilder1.append(".wxapi.WXEntryActivity");
      paramArgs.targetClassName = stringBuilder1.toString();
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("send, targetPkgName = ");
    stringBuilder.append(paramArgs.targetPkgName);
    stringBuilder.append(", targetClassName = ");
    stringBuilder.append(paramArgs.targetClassName);
    stringBuilder.append(", launchMode = ");
    stringBuilder.append(paramArgs.launchMode);
    Log.d("MicroMsg.SDK.MMessageAct", stringBuilder.toString());
    Intent intent = new Intent();
    intent.setClassName(paramArgs.targetPkgName, paramArgs.targetClassName);
    Bundle bundle = paramArgs.bundle;
    if (bundle != null)
      intent.putExtras(bundle); 
    String str2 = str1.getPackageName();
    intent.putExtra("_mmessage_sdkVersion", 638058496);
    intent.putExtra("_mmessage_appPackage", str2);
    intent.putExtra("_mmessage_content", paramArgs.content);
    intent.putExtra("_mmessage_checksum", a.a(paramArgs.content, 638058496, str2));
    intent.putExtra("_message_token", paramArgs.token);
    int i = paramArgs.flags;
    if (i == -1) {
      intent.addFlags(268435456).addFlags(134217728);
    } else {
      intent.setFlags(i);
    } 
    try {
      if (Build.VERSION.SDK_INT >= 29 && paramArgs.launchMode == 2) {
        sendUsingPendingIntent((Context)str1, intent);
      } else {
        str1.startActivity(intent);
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("send mm message, intent=");
      stringBuilder1.append(intent);
      Log.d("MicroMsg.SDK.MMessageAct", stringBuilder1.toString());
      return true;
    } catch (Exception exception) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("send fail, ex = ");
      stringBuilder1.append(exception.getMessage());
      str1 = stringBuilder1.toString();
    } 
    Log.e("MicroMsg.SDK.MMessageAct", str1);
    return false;
  }
  
  private static void sendUsingPendingIntent(Context paramContext, Intent paramIntent) {
    try {
      Log.i("MicroMsg.SDK.MMessageAct", "sendUsingPendingIntent");
      PendingIntent pendingIntent = PendingIntent.getActivity(paramContext, 3, paramIntent, 134217728);
      PendingIntent.OnFinished onFinished = new PendingIntent.OnFinished() {
          public void onSendFinished(PendingIntent param1PendingIntent, Intent param1Intent, int param1Int, String param1String, Bundle param1Bundle) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("sendUsingPendingIntent onSendFinished resultCode: ");
            stringBuilder.append(param1Int);
            stringBuilder.append(", resultData: ");
            stringBuilder.append(param1String);
            Log.i("MicroMsg.SDK.MMessageAct", stringBuilder.toString());
          }
        };
      super();
      pendingIntent.send(paramContext, 4, null, onFinished, null);
    } catch (Exception exception) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("sendUsingPendingIntent fail, ex = ");
      stringBuilder.append(exception.getMessage());
      Log.e("MicroMsg.SDK.MMessageAct", stringBuilder.toString());
      paramContext.startActivity(paramIntent);
    } 
  }
  
  public static class Args {
    public static final int INVALID_FLAGS = -1;
    
    public Bundle bundle;
    
    public String content;
    
    public int flags = -1;
    
    public int launchMode = 2;
    
    public String targetClassName;
    
    public String targetPkgName;
    
    public String token;
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("targetPkgName:");
      stringBuilder.append(this.targetPkgName);
      stringBuilder.append(", targetClassName:");
      stringBuilder.append(this.targetClassName);
      stringBuilder.append(", content:");
      stringBuilder.append(this.content);
      stringBuilder.append(", flags:");
      stringBuilder.append(this.flags);
      stringBuilder.append(", bundle:");
      stringBuilder.append(this.bundle);
      return stringBuilder.toString();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\channel\MMessageActV2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */