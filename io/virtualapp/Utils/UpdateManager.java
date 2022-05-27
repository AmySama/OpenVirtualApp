package io.virtualapp.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import io.virtualapp.App;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class UpdateManager {
  private static final int DOWNLOAD = 1;
  
  private static final int DOWNLOAD_FINISH = 2;
  
  private static final int IS_LASTEST_VERSION = 3;
  
  private static final int SHOW_DIALOG = 0;
  
  private String apkUrl;
  
  private boolean cancelUpdate = false;
  
  private String content;
  
  private Context mContext;
  
  private Dialog mDownloadDialog;
  
  private Handler mHandler = new Handler() {
      public void handleMessage(Message param1Message) {
        int i = param1Message.what;
        if (i != 1) {
          if (i != 2) {
            if (i == 3)
              ToastUtil.showToast(2131624015); 
          } else {
            UpdateManager.this.installApk();
          } 
        } else {
          UpdateManager.this.mProgress.setProgress(UpdateManager.this.progress);
          TextView textView = UpdateManager.this.mProgressText;
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(UpdateManager.this.progress);
          stringBuilder.append("%");
          textView.setText(stringBuilder.toString());
        } 
      }
    };
  
  HashMap<String, String> mHashMap;
  
  private ProgressBar mProgress;
  
  private TextView mProgressText;
  
  private String mSavePath;
  
  private int progress;
  
  public UpdateManager(Context paramContext) {
    this.mContext = paramContext;
  }
  
  public UpdateManager(Context paramContext, String paramString1, String paramString2) {
    this.mContext = paramContext;
    this.apkUrl = paramString1;
    this.content = paramString2;
  }
  
  private void checkMyPermission() {
    int i = ContextCompat.checkSelfPermission((Context)App.getApp(), "android.permission.WRITE_EXTERNAL_STORAGE");
    ArrayList<String> arrayList1 = new ArrayList();
    ArrayList<String> arrayList2 = new ArrayList();
    arrayList1.add("android.permission.WRITE_EXTERNAL_STORAGE");
    for (String str : arrayList1) {
      if (ContextCompat.checkSelfPermission((Context)App.getApp(), str) != 0)
        arrayList2.add(str); 
    } 
    if (i != 0) {
      String[] arrayOfString = arrayList2.<String>toArray(new String[arrayList2.size()]);
      ActivityCompat.requestPermissions((Activity)this.mContext, arrayOfString, 1111);
    } else {
      showDownloadDialog();
    } 
  }
  
  private void downloadApk() {
    (new downloadApkThread()).start();
  }
  
  private InputStream getUpdateStream() {
    try {
      URL uRL = new URL();
      this("http://api.hunanedu.com/update/update.xml");
      return uRL.openConnection().getInputStream();
    } catch (MalformedURLException malformedURLException) {
      malformedURLException.printStackTrace();
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
    return null;
  }
  
  private int getVersionCode(Context paramContext) {
    int i = 0;
    try {
      int j = (paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0)).versionCode;
      i = j;
    } catch (android.content.pm.PackageManager.NameNotFoundException nameNotFoundException) {
      nameNotFoundException.printStackTrace();
    } 
    return i;
  }
  
  private void installApk() {
    File file = new File(this.mSavePath, "fsdk");
    if (!file.exists())
      return; 
    Intent intent = new Intent("android.intent.action.VIEW");
    if (Build.VERSION.SDK_INT >= 24) {
      intent.setFlags(1);
      Context context = this.mContext;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(App.getApp().getPackageName());
      stringBuilder.append(".fileProvider");
      intent.setDataAndType(FileProvider.getUriForFile(context, stringBuilder.toString(), file), "application/vnd.android.package-archive");
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("file://");
      stringBuilder.append(file.toString());
      intent.setDataAndType(Uri.parse(stringBuilder.toString()), "application/vnd.android.package-archive");
      intent.setFlags(268435456);
    } 
    this.mContext.startActivity(intent);
  }
  
  public void checkUpdate(boolean paramBoolean) {}
  
  public void down() {
    checkMyPermission();
  }
  
  public void installApk(Context paramContext, String paramString1, String paramString2) {
    File file = new File(paramString1, paramString2);
    if (!file.exists())
      return; 
    Intent intent = new Intent("android.intent.action.VIEW");
    if (Build.VERSION.SDK_INT >= 24) {
      intent.setFlags(1);
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(App.getApp().getPackageName());
      stringBuilder.append(".fileProvider");
      intent.setDataAndType(FileProvider.getUriForFile(paramContext, stringBuilder.toString(), file), "application/vnd.android.package-archive");
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("file://");
      stringBuilder.append(file.toString());
      intent.setDataAndType(Uri.parse(stringBuilder.toString()), "application/vnd.android.package-archive");
      intent.setFlags(268435456);
    } 
    paramContext.startActivity(intent);
  }
  
  public void showDownloadDialog() {
    Dialog dialog = new Dialog(this.mContext, 2131689644);
    this.mDownloadDialog = dialog;
    dialog.setCanceledOnTouchOutside(false);
    this.mDownloadDialog.setCancelable(false);
    this.mDownloadDialog.setContentView(2131427433);
    Window window = this.mDownloadDialog.getWindow();
    WindowManager.LayoutParams layoutParams = window.getAttributes();
    layoutParams.width = (int)(ScreenUtils.getScreenWidth(this.mContext) * 0.8D);
    window.setAttributes(layoutParams);
    this.mProgress = (ProgressBar)this.mDownloadDialog.findViewById(2131296760);
    this.mProgressText = (TextView)this.mDownloadDialog.findViewById(2131296763);
    TextView textView = (TextView)this.mDownloadDialog.findViewById(2131296762);
    ((TextView)this.mDownloadDialog.findViewById(2131296761)).setMovementMethod(ScrollingMovementMethod.getInstance());
    this.mDownloadDialog.show();
    downloadApk();
  }
  
  public void showNoticeDialog() {
    final Dialog dialog = new Dialog(this.mContext, 2131689644);
    dialog.setCanceledOnTouchOutside(false);
    dialog.setCancelable(false);
    dialog.setContentView(2131427428);
    Window window = dialog.getWindow();
    WindowManager.LayoutParams layoutParams = window.getAttributes();
    layoutParams.width = (int)(ScreenUtils.getScreenWidth(this.mContext) * 0.8D);
    window.setAttributes(layoutParams);
    ((TextView)dialog.findViewById(2131296721)).setText(2131624085);
    ((TextView)dialog.findViewById(2131296425)).setText(this.content);
    TextView textView1 = (TextView)dialog.findViewById(2131296403);
    TextView textView2 = (TextView)dialog.findViewById(2131296596);
    textView1.setText(2131624040);
    textView2.setText(2131624002);
    textView2.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            ToastUtil.cancelToast();
            dialog.dismiss();
            System.exit(0);
            Process.killProcess(Process.myPid());
          }
        });
    textView1.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            dialog.dismiss();
            UpdateManager.this.checkMyPermission();
          }
        });
    dialog.show();
  }
  
  public void showTipDialog() {
    final Dialog dialog = new Dialog(this.mContext, 2131689644);
    dialog.setCanceledOnTouchOutside(true);
    dialog.setCancelable(true);
    dialog.setContentView(2131427428);
    Window window = dialog.getWindow();
    WindowManager.LayoutParams layoutParams = window.getAttributes();
    layoutParams.width = (int)(ScreenUtils.getScreenWidth(this.mContext) * 0.8D);
    window.setAttributes(layoutParams);
    TextView textView2 = (TextView)dialog.findViewById(2131296425);
    ((TextView)dialog.findViewById(2131296721)).setText(2131624085);
    textView2.setText(this.content);
    textView2 = (TextView)dialog.findViewById(2131296403);
    TextView textView1 = (TextView)dialog.findViewById(2131296596);
    textView2.setText(2131624040);
    textView1.setText(2131624035);
    textView1.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            dialog.dismiss();
          }
        });
    textView2.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            dialog.dismiss();
            UpdateManager.this.checkMyPermission();
          }
        });
    dialog.show();
  }
  
  private class downloadApkThread extends Thread {
    private downloadApkThread() {}
    
    public void run() {
      try {
        if (Environment.getExternalStorageState().equals("mounted")) {
          StringBuilder stringBuilder1 = new StringBuilder();
          this();
          stringBuilder1.append(Environment.getExternalStorageDirectory());
          stringBuilder1.append("/");
          String str = stringBuilder1.toString();
          UpdateManager updateManager = UpdateManager.this;
          StringBuilder stringBuilder2 = new StringBuilder();
          this();
          stringBuilder2.append(str);
          stringBuilder2.append("download");
          UpdateManager.access$602(updateManager, stringBuilder2.toString());
          URL uRL = new URL();
          this(UpdateManager.this.apkUrl);
          HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
          httpURLConnection.connect();
          int i = httpURLConnection.getContentLength();
          InputStream inputStream = httpURLConnection.getInputStream();
          File file1 = new File();
          this(UpdateManager.this.mSavePath);
          if (!file1.exists())
            file1.mkdir(); 
          File file2 = new File();
          this(UpdateManager.this.mSavePath, "fsdk");
          FileOutputStream fileOutputStream = new FileOutputStream();
          this(file2);
          byte[] arrayOfByte = new byte[1024];
          int j = 0;
          do {
            int k = inputStream.read(arrayOfByte);
            j += k;
            UpdateManager.access$002(UpdateManager.this, (int)(j / i * 100.0F));
            UpdateManager.this.mHandler.sendEmptyMessage(1);
            if (k <= 0) {
              UpdateManager.this.mHandler.sendEmptyMessage(2);
              break;
            } 
            fileOutputStream.write(arrayOfByte, 0, k);
          } while (!UpdateManager.this.cancelUpdate);
          fileOutputStream.close();
          inputStream.close();
        } 
      } catch (MalformedURLException malformedURLException) {
        malformedURLException.printStackTrace();
      } catch (IOException iOException) {
        iOException.printStackTrace();
      } 
      UpdateManager.this.mDownloadDialog.dismiss();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\Utils\UpdateManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */