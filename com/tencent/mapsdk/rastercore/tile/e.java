package com.tencent.mapsdk.rastercore.tile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.tencent.mapsdk.rastercore.tile.a.a;
import com.tencent.mapsdk.rastercore.tile.a.c;
import com.tencent.mapsdk.rastercore.tile.b.b;
import com.tencent.tencentmap.mapsdk.map.TencentMap;
import java.util.concurrent.Callable;

public final class e implements Callable<Bitmap> {
  private a a;
  
  private Bitmap b = null;
  
  private a c;
  
  private boolean d = false;
  
  private c e = null;
  
  public e(a parama, a parama1) {
    this.a = parama;
    this.c = parama1;
  }
  
  private Bitmap d() throws Exception {
    null = this.a;
    if (null != null)
      null.b(this); 
    try {
      Bitmap bitmap = f();
      this.b = bitmap;
      return bitmap;
    } finally {
      e();
    } 
  }
  
  private void e() {
    a a1 = this.a;
    if (a1 != null)
      a1.a(this); 
    this.a = null;
    Bitmap bitmap = this.b;
    if (bitmap != null && !bitmap.isRecycled())
      this.b.recycle(); 
    this.b = null;
  }
  
  private Bitmap f() {
    Bitmap bitmap;
    String str = null;
    for (byte b = 0; b < 4; b++) {
      Bitmap bitmap1;
      String str1 = str;
      try {
        StringBuilder stringBuilder;
        String str2;
        Bitmap bitmap2;
        a a1 = this.c;
        str1 = str;
        boolean bool = this.d;
        str1 = str;
        if (this.e != null) {
          str1 = str;
          str3 = this.e.a();
        } else {
          str3 = null;
        } 
        str1 = str;
        byte[] arrayOfByte = a1.a(bool, str3);
        String str3 = str;
        if (arrayOfByte != null) {
          Bitmap bitmap3;
          str1 = str;
          if (arrayOfByte.length == 1 && arrayOfByte[0] == -1) {
            str1 = str;
            if (this.d) {
              str1 = str;
              a.a().a(this.c, null, true);
              str1 = str;
              if (this.e != null) {
                str1 = str;
                if (this.e.b() != null) {
                  str1 = str;
                  com.tencent.mapsdk.rastercore.d.e.e++;
                  str1 = str;
                  com.tencent.mapsdk.rastercore.d.e.a++;
                  str1 = str;
                  return this.e.b();
                } 
              } 
              return null;
            } 
          } 
          try {
            Bitmap bitmap4 = BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length);
            bitmap2 = bitmap4;
            if (bitmap4 != null) {
              bitmap3 = bitmap4;
              if (this.d) {
                bitmap3 = bitmap4;
                com.tencent.mapsdk.rastercore.d.e.f++;
                bitmap3 = bitmap4;
                com.tencent.mapsdk.rastercore.d.e.c++;
              } 
              bitmap3 = bitmap4;
              int i = arrayOfByte.length;
              if (i < 2097152) {
                try {
                  c c1 = new c();
                } finally {
                  Exception exception = null;
                  bitmap3 = bitmap4;
                  bitmap2 = bitmap4;
                } 
              } else {
                bitmap3 = bitmap4;
                bitmap2 = bitmap4;
                if (TencentMap.getErrorListener() != null) {
                  bitmap3 = bitmap4;
                  TencentMap.OnErrorListener onErrorListener = TencentMap.getErrorListener();
                  bitmap3 = bitmap4;
                  StringBuilder stringBuilder1 = new StringBuilder();
                  bitmap3 = bitmap4;
                  this("TileNetFetcher downLoad function,the downloaded data length-");
                  bitmap3 = bitmap4;
                  stringBuilder1.append(arrayOfByte.length);
                  bitmap3 = bitmap4;
                  stringBuilder1.append(";tileInfo:x=");
                  bitmap3 = bitmap4;
                  stringBuilder1.append(this.c.b());
                  bitmap3 = bitmap4;
                  stringBuilder1.append(",y=");
                  bitmap3 = bitmap4;
                  stringBuilder1.append(this.c.c());
                  bitmap3 = bitmap4;
                  stringBuilder1.append("z=");
                  bitmap3 = bitmap4;
                  stringBuilder1.append(this.c.d());
                  bitmap3 = bitmap4;
                  onErrorListener.collectErrorInfo(stringBuilder1.toString());
                  Bitmap bitmap5 = bitmap4;
                } 
              } 
            } 
          } catch (Exception exception) {
            Bitmap bitmap4 = bitmap3;
            StringBuilder stringBuilder1 = new StringBuilder();
            bitmap4 = bitmap3;
            this("decoder bitmap error:");
            bitmap4 = bitmap3;
            stringBuilder1.append(exception.getMessage());
            bitmap2 = bitmap3;
          } 
        } 
        if (bitmap2 != null)
          return bitmap2; 
        if (b == 0) {
          Bitmap bitmap3 = bitmap2;
          Thread.sleep(300L);
          bitmap3 = bitmap2;
          stringBuilder = new StringBuilder();
          bitmap3 = bitmap2;
          this();
          bitmap3 = bitmap2;
          stringBuilder.append(this.c.l());
          str2 = ",重试次数：2";
        } else if (b == 1) {
          Bitmap bitmap3 = bitmap2;
          Thread.sleep(500L);
          bitmap3 = bitmap2;
          stringBuilder = new StringBuilder();
          bitmap3 = bitmap2;
          this();
          bitmap3 = bitmap2;
          stringBuilder.append(this.c.l());
          str2 = ",重试次数3";
        } else {
          Bitmap bitmap3 = bitmap2;
          if (b == 2) {
            Bitmap bitmap4 = bitmap2;
            Thread.sleep(700L);
            bitmap4 = bitmap2;
            stringBuilder = new StringBuilder();
            bitmap4 = bitmap2;
            this();
            bitmap4 = bitmap2;
            stringBuilder.append(this.c.l());
            str2 = ",重试次数4";
          } else {
            continue;
          } 
        } 
        bitmap1 = bitmap2;
        stringBuilder.append(str2);
        bitmap = bitmap2;
      } catch (Exception exception) {
        (new StringBuilder("Error occured:")).append(exception.getMessage());
        bitmap = bitmap1;
      } 
      continue;
    } 
    if (this.c.e().getClass() == b.class && bitmap == null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(this.c.l());
      stringBuilder.append(",重试4次，仍然失败");
    } 
    return bitmap;
  }
  
  public final Bitmap a() {
    return this.b;
  }
  
  public final void a(c paramc) {
    this.e = paramc;
  }
  
  public final void a(boolean paramBoolean) {
    this.d = paramBoolean;
  }
  
  public final String b() {
    a a1 = this.c;
    return (a1 != null) ? a1.toString() : "";
  }
  
  public final void c() {
    Bitmap bitmap = this.b;
    if (bitmap != null)
      bitmap.recycle(); 
    this.b = null;
  }
  
  public static interface a {
    void a(e param1e);
    
    void b(e param1e);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\tile\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */