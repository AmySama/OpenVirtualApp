package io.virtualapp.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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

public class DownloadData2Manager {
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
      }
    };
  
  HashMap<String, String> mHashMap;
  
  private ProgressBar mProgress;
  
  private TextView mProgressText;
  
  private String mSavePath;
  
  private int progress;
  
  public DownloadData2Manager(Context paramContext) {
    this.mContext = paramContext;
  }
  
  public DownloadData2Manager(Context paramContext, String paramString1, String paramString2, boolean paramBoolean) {
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
  
  private class downloadApkThread extends Thread {
    private downloadApkThread() {}
    
    public void run() {
      try {
        if (Environment.getExternalStorageState().equals("mounted")) {
          boolean bool = DownloadData2Manager.this.mFileName.equals("ydkbdata2");
          if (bool) {
            SPUtils.put(DownloadData2Manager.this.mContext, "download_is_over", Boolean.valueOf(false));
            Log.e("checkdata2", "false ");
          } 
          bool = DownloadData2Manager.this.mFileName.equals("ydkbdata4");
          if (bool)
            SPUtils.put(DownloadData2Manager.this.mContext, "download_data4_is_over", Boolean.valueOf(false)); 
          StringBuilder stringBuilder1 = new StringBuilder();
          this();
          stringBuilder1.append(Environment.getExternalStorageDirectory());
          stringBuilder1.append("/");
          String str = stringBuilder1.toString();
          DownloadData2Manager downloadData2Manager = DownloadData2Manager.this;
          StringBuilder stringBuilder2 = new StringBuilder();
          this();
          stringBuilder2.append(str);
          stringBuilder2.append("download");
          DownloadData2Manager.access$302(downloadData2Manager, stringBuilder2.toString());
          URL uRL = new URL();
          this(DownloadData2Manager.this.apkUrl);
          HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
          httpURLConnection.connect();
          int i = httpURLConnection.getContentLength();
          InputStream inputStream = httpURLConnection.getInputStream();
          File file = new File();
          this(DownloadData2Manager.this.mSavePath);
          if (!file.exists())
            file.mkdir(); 
          file = new File();
          this(DownloadData2Manager.this.mSavePath, DownloadData2Manager.this.mFileName);
          if (file.exists()) {
            stringBuilder2 = new StringBuilder();
            this();
            stringBuilder2.append("isDeleteOld == ");
            stringBuilder2.append(DownloadData2Manager.this.isDeleteOld);
            Log.e("checkdata2", stringBuilder2.toString());
            if (!DownloadData2Manager.this.isDeleteOld) {
              if (DownloadData2Manager.this.mFileName.equals("ydkbdata2"))
                SPUtils.put(DownloadData2Manager.this.mContext, "download_is_over", Boolean.valueOf(true)); 
              if (DownloadData2Manager.this.mFileName.equals("ydkbdata4"))
                SPUtils.put(DownloadData2Manager.this.mContext, "download_data4_is_over", Boolean.valueOf(true)); 
              return;
            } 
            stringBuilder2 = new StringBuilder();
            this();
            stringBuilder2.append(DownloadData2Manager.this.mSavePath);
            stringBuilder2.append("/");
            stringBuilder2.append(DownloadData2Manager.this.mFileName);
            FileUtils.deleteFile(stringBuilder2.toString());
          } 
          FileOutputStream fileOutputStream = new FileOutputStream();
          this(file);
          byte[] arrayOfByte = new byte[1024];
          int j = 0;
          do {
            int k = inputStream.read(arrayOfByte);
            j += k;
            DownloadData2Manager.access$602(DownloadData2Manager.this, (int)(j / i * 100.0F));
            DownloadData2Manager.this.mHandler.sendEmptyMessage(1);
            if (k <= 0) {
              DownloadData2Manager.this.mHandler.sendEmptyMessage(2);
              break;
            } 
            fileOutputStream.write(arrayOfByte, 0, k);
          } while (!DownloadData2Manager.this.cancelUpdate);
          fileOutputStream.close();
          inputStream.close();
          if (DownloadData2Manager.this.mFileName.equals("ydkbdata2"))
            SPUtils.put(DownloadData2Manager.this.mContext, "download_is_over", Boolean.valueOf(true)); 
          if (DownloadData2Manager.this.mFileName.equals("ydkbdata4"))
            SPUtils.put(DownloadData2Manager.this.mContext, "download_data4_is_over", Boolean.valueOf(true)); 
        } 
      } catch (MalformedURLException malformedURLException) {
        malformedURLException.printStackTrace();
      } catch (IOException iOException) {
        iOException.printStackTrace();
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\Utils\DownloadData2Manager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */