package io.virtualapp.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.method.ScrollingMovementMethod;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import io.virtualapp.App;
import io.virtualapp.bean.MessageEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import org.greenrobot.eventbus.EventBus;

public class DownloadData4Manager {
  private static final int DOWNLOAD = 1;
  
  private static final int DOWNLOAD_FINISH = 2;
  
  private static final int IS_LASTEST_VERSION = 3;
  
  private static final int SHOW_DIALOG = 0;
  
  private String apkUrl;
  
  private boolean cancelUpdate = false;
  
  private boolean isDeleteOld;
  
  private Context mContext;
  
  private Dialog mDownloadDialog;
  
  private String mFileName;
  
  private Handler mHandler = new Handler() {
      public void handleMessage(Message param1Message) {
        int i = param1Message.what;
        if (i != 1) {
          if (i == 2)
            EventBus.getDefault().post(new MessageEvent(6)); 
        } else {
          DownloadData4Manager.this.mProgress.setProgress(DownloadData4Manager.this.progress);
          TextView textView = DownloadData4Manager.this.mProgressText;
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(DownloadData4Manager.this.progress);
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
  
  public DownloadData4Manager(Context paramContext) {
    this.mContext = paramContext;
  }
  
  public DownloadData4Manager(Context paramContext, String paramString1, String paramString2, boolean paramBoolean) {
    this.mContext = paramContext;
    this.apkUrl = paramString1;
    this.mFileName = paramString2;
    this.isDeleteOld = paramBoolean;
  }
  
  private void checkMyPermission() {
    int i = ContextCompat.checkSelfPermission((Context)App.getApp(), "android.permission.READ_EXTERNAL_STORAGE");
    ArrayList<String> arrayList1 = new ArrayList();
    ArrayList<String> arrayList2 = new ArrayList();
    arrayList1.add("android.permission.READ_EXTERNAL_STORAGE");
    for (String str : arrayList1) {
      if (ContextCompat.checkSelfPermission((Context)App.getApp(), str) != 0)
        arrayList2.add(str); 
    } 
    if (i != 0) {
      String[] arrayOfString = arrayList2.<String>toArray(new String[arrayList2.size()]);
      ActivityCompat.requestPermissions((Activity)this.mContext, arrayOfString, 1111);
    } else {
      downloadApk();
    } 
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
  
  public void downloadApk() {
    (new downloadApkThread()).start();
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
          DownloadData4Manager downloadData4Manager = DownloadData4Manager.this;
          StringBuilder stringBuilder2 = new StringBuilder();
          this();
          stringBuilder2.append(str);
          stringBuilder2.append("download");
          DownloadData4Manager.access$402(downloadData4Manager, stringBuilder2.toString());
          URL uRL = new URL();
          this(DownloadData4Manager.this.apkUrl);
          HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
          httpURLConnection.connect();
          int i = httpURLConnection.getContentLength();
          InputStream inputStream = httpURLConnection.getInputStream();
          File file2 = new File();
          this(DownloadData4Manager.this.mSavePath);
          if (!file2.exists())
            file2.mkdir(); 
          File file1 = new File();
          this(DownloadData4Manager.this.mSavePath, DownloadData4Manager.this.mFileName);
          FileOutputStream fileOutputStream = new FileOutputStream();
          this(file1);
          byte[] arrayOfByte = new byte[1024];
          int j = 0;
          do {
            int k = inputStream.read(arrayOfByte);
            j += k;
            DownloadData4Manager.access$002(DownloadData4Manager.this, (int)(j / i * 100.0F));
            DownloadData4Manager.this.mHandler.sendEmptyMessage(1);
            if (k <= 0) {
              DownloadData4Manager.this.mHandler.sendEmptyMessage(2);
              break;
            } 
            fileOutputStream.write(arrayOfByte, 0, k);
          } while (!DownloadData4Manager.this.cancelUpdate);
          fileOutputStream.close();
          inputStream.close();
        } 
      } catch (MalformedURLException malformedURLException) {
        malformedURLException.printStackTrace();
      } catch (IOException iOException) {
        iOException.printStackTrace();
      } 
      DownloadData4Manager.this.mDownloadDialog.dismiss();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\Utils\DownloadData4Manager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */