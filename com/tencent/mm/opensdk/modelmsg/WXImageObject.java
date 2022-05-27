package com.tencent.mm.opensdk.modelmsg;

import android.graphics.Bitmap;
import android.os.Bundle;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.mm.opensdk.utils.b;
import java.io.ByteArrayOutputStream;

public class WXImageObject implements WXMediaMessage.IMediaObject {
  private static final int CONTENT_LENGTH_LIMIT = 26214400;
  
  private static final int PATH_LENGTH_LIMIT = 10240;
  
  private static final String TAG = "MicroMsg.SDK.WXImageObject";
  
  public byte[] imageData;
  
  public String imagePath;
  
  public WXImageObject() {}
  
  public WXImageObject(Bitmap paramBitmap) {
    try {
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      this();
      paramBitmap.compress(Bitmap.CompressFormat.JPEG, 85, byteArrayOutputStream);
      this.imageData = byteArrayOutputStream.toByteArray();
      byteArrayOutputStream.close();
    } catch (Exception exception) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("WXImageObject <init>, exception:");
      stringBuilder.append(exception.getMessage());
      Log.e("MicroMsg.SDK.WXImageObject", stringBuilder.toString());
    } 
  }
  
  public WXImageObject(byte[] paramArrayOfbyte) {
    this.imageData = paramArrayOfbyte;
  }
  
  private int getFileSize(String paramString) {
    return b.a(paramString);
  }
  
  public boolean checkArgs() {
    byte[] arrayOfByte = this.imageData;
    if (arrayOfByte == null || arrayOfByte.length == 0) {
      String str1 = this.imagePath;
      if (str1 == null || str1.length() == 0) {
        str1 = "checkArgs fail, all arguments are null";
        Log.e("MicroMsg.SDK.WXImageObject", str1);
        return false;
      } 
    } 
    arrayOfByte = this.imageData;
    if (arrayOfByte != null && arrayOfByte.length > 26214400) {
      String str1 = "checkArgs fail, content is too large";
      Log.e("MicroMsg.SDK.WXImageObject", str1);
      return false;
    } 
    String str = this.imagePath;
    if (str != null && str.length() > 10240) {
      str = "checkArgs fail, path is invalid";
      Log.e("MicroMsg.SDK.WXImageObject", str);
      return false;
    } 
    str = this.imagePath;
    if (str != null && getFileSize(str) > 26214400) {
      str = "checkArgs fail, image content is too large";
      Log.e("MicroMsg.SDK.WXImageObject", str);
      return false;
    } 
    return true;
  }
  
  public void serialize(Bundle paramBundle) {
    paramBundle.putByteArray("_wximageobject_imageData", this.imageData);
    paramBundle.putString("_wximageobject_imagePath", this.imagePath);
  }
  
  public void setImagePath(String paramString) {
    this.imagePath = paramString;
  }
  
  public int type() {
    return 2;
  }
  
  public void unserialize(Bundle paramBundle) {
    this.imageData = paramBundle.getByteArray("_wximageobject_imageData");
    this.imagePath = paramBundle.getString("_wximageobject_imagePath");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mm\opensdk\modelmsg\WXImageObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */