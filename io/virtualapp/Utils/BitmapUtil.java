package io.virtualapp.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.ImageView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Locale;

public class BitmapUtil {
  private ImageView img;
  
  private Context mContext;
  
  public BitmapUtil(Context paramContext, ImageView paramImageView) {
    this.mContext = paramContext;
    this.img = paramImageView;
  }
  
  public static byte[] DrawableToByte(Drawable paramDrawable) {
    Bitmap.Config config;
    int i = paramDrawable.getIntrinsicWidth();
    int j = paramDrawable.getIntrinsicHeight();
    if (paramDrawable.getOpacity() != -1) {
      config = Bitmap.Config.ARGB_8888;
    } else {
      config = Bitmap.Config.RGB_565;
    } 
    Bitmap bitmap = Bitmap.createBitmap(i, j, config);
    Canvas canvas = new Canvas(bitmap);
    paramDrawable.setBounds(0, 0, i, j);
    paramDrawable.draw(canvas);
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
    return byteArrayOutputStream.toByteArray();
  }
  
  public static byte[] bitmapToBytes(Bitmap paramBitmap, int paramInt) {
    if (paramBitmap != null) {
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      paramBitmap.compress(Bitmap.CompressFormat.JPEG, paramInt, byteArrayOutputStream);
      if ((byteArrayOutputStream.toByteArray()).length / 1024 > 256) {
        byteArrayOutputStream.reset();
        paramBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
      } 
      byte[] arrayOfByte = byteArrayOutputStream.toByteArray();
    } else {
      paramBitmap = null;
    } 
    return (byte[])paramBitmap;
  }
  
  public static byte[] bmpToByteArray(Bitmap paramBitmap, boolean paramBoolean) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    paramBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
    if (paramBoolean)
      paramBitmap.recycle(); 
    byte[] arrayOfByte = byteArrayOutputStream.toByteArray();
    try {
      byteArrayOutputStream.close();
    } catch (Exception exception) {
      exception.printStackTrace();
    } 
    return arrayOfByte;
  }
  
  private Bitmap getImageBitmap(String paramString) {
    Bitmap bitmap1;
    Bitmap bitmap2 = null;
    Bitmap bitmap3 = bitmap2;
    try {
      URL uRL = new URL();
      bitmap3 = bitmap2;
      this(paramString);
      bitmap3 = bitmap2;
      HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
      bitmap3 = bitmap2;
      httpURLConnection.setRequestMethod("GET");
      bitmap3 = bitmap2;
      httpURLConnection.connect();
      bitmap3 = bitmap2;
      InputStream inputStream = httpURLConnection.getInputStream();
      bitmap3 = bitmap2;
      bitmap1 = BitmapFactory.decodeStream(inputStream);
      bitmap3 = bitmap1;
      inputStream.close();
    } catch (IOException iOException) {
      iOException.printStackTrace();
      bitmap1 = bitmap3;
    } 
    return bitmap1;
  }
  
  public static String saveImage(Bitmap paramBitmap, String paramString) {
    StringBuilder stringBuilder1 = new StringBuilder();
    new DateFormat();
    stringBuilder1.append(DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)));
    stringBuilder1.append(".png");
    String str1 = stringBuilder1.toString();
    File file = new File(paramString);
    if (!file.exists())
      file.mkdirs(); 
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(paramString);
    stringBuilder2.append(File.separator);
    stringBuilder2.append(str1);
    String str2 = stringBuilder2.toString();
    stringBuilder2 = null;
    String str3 = null;
    paramString = str3;
    try {
      FileOutputStream fileOutputStream = new FileOutputStream();
      paramString = str3;
      this(str2);
      try {
        paramBitmap.compress(Bitmap.CompressFormat.JPEG, 75, fileOutputStream);
        return str2;
      } catch (FileNotFoundException fileNotFoundException1) {
        FileOutputStream fileOutputStream1 = fileOutputStream;
      } finally {
        FileNotFoundException fileNotFoundException1;
        paramBitmap = null;
      } 
    } catch (FileNotFoundException fileNotFoundException) {
      StringBuilder stringBuilder = stringBuilder2;
    } finally {}
    Bitmap bitmap = paramBitmap;
    fileNotFoundException.printStackTrace();
    if (paramBitmap != null)
      try {
        paramBitmap.flush();
        paramBitmap.close();
      } catch (IOException iOException) {
        iOException.printStackTrace();
      }  
    return "";
  }
  
  public static Bitmap toRoundCorner(Bitmap paramBitmap, int paramInt) {
    Bitmap bitmap = Bitmap.createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    Paint paint = new Paint();
    Rect rect = new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight());
    RectF rectF = new RectF(rect);
    float f = paramInt;
    paint.setAntiAlias(true);
    canvas.drawARGB(0, 0, 0, 0);
    paint.setColor(-12434878);
    canvas.drawRoundRect(rectF, f, f, paint);
    paint.setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    canvas.drawBitmap(paramBitmap, rect, rect, paint);
    return bitmap;
  }
  
  public static Drawable urlToDrawable(String paramString) {
    try {
      URL uRL = new URL();
      this(paramString);
      Drawable drawable = Drawable.createFromStream(uRL.openStream(), "image.jpg");
    } catch (IOException iOException) {
      Log.w("test", iOException.getMessage());
      iOException = null;
    } 
    if (iOException == null) {
      Log.w("test", "null drawable");
    } else {
      Log.w("test", "not null drawable");
    } 
    return (Drawable)iOException;
  }
  
  public void downImg(String paramString) {
    (new DownImgAsyncTask()).execute((Object[])new String[] { paramString });
  }
  
  class DownImgAsyncTask extends AsyncTask<String, Void, Bitmap> {
    protected Bitmap doInBackground(String... param1VarArgs) {
      return BitmapUtil.this.getImageBitmap(param1VarArgs[0]);
    }
    
    protected void onPostExecute(Bitmap param1Bitmap) {
      super.onPostExecute(param1Bitmap);
      if (param1Bitmap != null)
        BitmapUtil.this.img.setImageBitmap(param1Bitmap); 
    }
    
    protected void onPreExecute() {
      super.onPreExecute();
      BitmapUtil.this.img.setImageBitmap(null);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\Utils\BitmapUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */