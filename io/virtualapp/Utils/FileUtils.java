package io.virtualapp.Utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Environment;
import android.view.View;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FileUtils {
  public static final int DOWNLOAD = 111;
  
  public static final int DOWNLOAD_FINISH = 222;
  
  public static Bitmap ResizeBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2) {
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    float f1 = paramInt1 / i;
    float f2 = paramInt2 / j;
    Matrix matrix = new Matrix();
    matrix.postScale(f1, f2);
    Bitmap bitmap = Bitmap.createBitmap(paramBitmap, 0, 0, i, j, matrix, true);
    paramBitmap.recycle();
    return bitmap;
  }
  
  public static Bitmap createBitmapFromView(View paramView) {
    if (paramView == null)
      return null; 
    Bitmap bitmap = Bitmap.createBitmap(paramView.getWidth(), paramView.getHeight(), Bitmap.Config.ARGB_8888);
    paramView.draw(new Canvas(bitmap));
    return bitmap;
  }
  
  public static boolean createFile(String paramString) {
    try {
      File file = new File();
      this(paramString);
      if (!file.exists()) {
        if (!file.getParentFile().exists())
          file.getParentFile().mkdirs(); 
        return file.createNewFile();
      } 
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
    return true;
  }
  
  public static File createTmpFile(Context paramContext) {
    if (Environment.getExternalStorageState().equals("mounted")) {
      file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
      String str1 = (new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA)).format(new Date());
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("multi_image_");
      stringBuilder1.append(str1);
      stringBuilder1.append("");
      str1 = stringBuilder1.toString();
      stringBuilder1 = new StringBuilder();
      stringBuilder1.append(str1);
      stringBuilder1.append(".jpg");
      return new File(file, stringBuilder1.toString());
    } 
    File file = file.getCacheDir();
    String str = (new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA)).format(new Date());
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("multi_image_");
    stringBuilder.append(str);
    stringBuilder.append("");
    str = stringBuilder.toString();
    stringBuilder = new StringBuilder();
    stringBuilder.append(str);
    stringBuilder.append(".jpg");
    return new File(file, stringBuilder.toString());
  }
  
  public static boolean deleteFile(String paramString) {
    try {
      File file = new File();
      this(paramString);
      if (file.exists())
        return file.delete(); 
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return false;
  }
  
  public static void doGetBitmap(final String url, final Callback<Bitmap> callBack) {
    (new Thread() {
        public void run() {
          try {
            URL uRL = new URL();
            this(url);
            URLConnection uRLConnection = uRL.openConnection();
            uRLConnection.connect();
            Bitmap bitmap = BitmapFactory.decodeStream(uRLConnection.getInputStream());
            if (bitmap == null) {
              callBack.onError("获取图片失败");
            } else {
              callBack.onSuccess(bitmap);
            } 
          } catch (Exception exception) {
            exception.printStackTrace();
          } 
        }
      }).start();
  }
  
  public static boolean fileExists(String paramString) {
    try {
      File file = new File();
      this(paramString);
      return file.exists();
    } catch (Exception exception) {
      exception.printStackTrace();
      return true;
    } 
  }
  
  public static InputStream readAsset(Context paramContext, String paramString) {
    AssetManager assetManager = paramContext.getAssets();
    try {
      InputStream inputStream = assetManager.open(paramString);
    } catch (IOException iOException) {
      iOException.printStackTrace();
      iOException = null;
    } 
    return (InputStream)iOException;
  }
  
  private static String readDataFromInputStream(InputStream paramInputStream) {
    BufferedInputStream bufferedInputStream = new BufferedInputStream(paramInputStream);
    byte[] arrayOfByte = new byte[1024];
    String str1 = "";
    String str2 = "";
    int i = 0;
    while (true) {
      try {
        int j = bufferedInputStream.read(arrayOfByte);
        i = j;
      } catch (IOException iOException) {
        iOException.printStackTrace();
      } 
      if (i == -1) {
        try {
          bufferedInputStream.close();
        } catch (IOException iOException) {
          iOException.printStackTrace();
        } 
        return str1;
      } 
      try {
        String str = new String();
        this(arrayOfByte, 0, i, "UTF-8");
        str2 = str;
      } catch (UnsupportedEncodingException unsupportedEncodingException) {
        unsupportedEncodingException.printStackTrace();
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str1);
      stringBuilder.append(str2);
      str1 = stringBuilder.toString();
    } 
  }
  
  public static File saveBitmapJPG(Context paramContext, String paramString, Bitmap paramBitmap) throws IOException {
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(paramContext.getExternalCacheDir());
    stringBuilder2.append("/dd");
    File file2 = new File(stringBuilder2.toString());
    if (!file2.exists())
      file2.mkdirs(); 
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("dd");
    stringBuilder1.append(paramString);
    stringBuilder1.append(".jpg");
    File file1 = new File(file2, stringBuilder1.toString());
    FileOutputStream fileOutputStream = new FileOutputStream(file1);
    paramBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
    fileOutputStream.flush();
    fileOutputStream.close();
    paramBitmap.recycle();
    return file1;
  }
  
  public static void writeImage(Bitmap paramBitmap, String paramString, int paramInt) {
    try {
      deleteFile(paramString);
      if (createFile(paramString)) {
        FileOutputStream fileOutputStream = new FileOutputStream();
        this(paramString);
        if (paramBitmap.compress(Bitmap.CompressFormat.JPEG, paramInt, fileOutputStream)) {
          fileOutputStream.flush();
          fileOutputStream.close();
        } 
      } 
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
  }
  
  public static interface Callback<T> {
    void onError(String param1String);
    
    void onSuccess(T param1T);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\Utils\FileUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */