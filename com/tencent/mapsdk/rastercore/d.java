package com.tencent.mapsdk.rastercore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.tencent.mapsdk.raster.model.CameraPosition;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.LatLngBounds;
import com.tencent.mapsdk.rastercore.b.a;
import com.tencent.mapsdk.rastercore.b.c;
import com.tencent.mapsdk.rastercore.c.a;
import com.tencent.mapsdk.rastercore.c.b;
import com.tencent.mapsdk.rastercore.c.c;
import com.tencent.mapsdk.rastercore.c.e;
import com.tencent.mapsdk.rastercore.f.a;
import com.tencent.mapsdk.rastercore.tile.MapTile;
import com.tencent.mapsdk.rastercore.tile.a.a;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Stack;
import org.json.JSONException;
import org.json.JSONObject;

public final class d {
  private String a;
  
  private c b;
  
  private String c;
  
  public d(Context paramContext, b paramb) {
    String str = a.a(paramContext);
    this.c = paramContext.getPackageName();
    StringBuilder stringBuilder = new StringBuilder(256);
    stringBuilder.append("https://confinfo.map.qq.com/confinfo?apikey=");
    stringBuilder.append(str);
    stringBuilder.append("&type=2");
    stringBuilder.append("&pf=Android_2D");
    stringBuilder.append("&uk=");
    stringBuilder.append(a.a());
    this.a = stringBuilder.toString();
    this.b = new c(paramb);
  }
  
  public static boolean a(String paramString) {
    return (paramString == null || paramString.length() == 0);
  }
  
  private static String[] c(String paramString) {
    String[] arrayOfString = new String[2];
    try {
      JSONObject jSONObject = new JSONObject();
      this(paramString);
      arrayOfString[0] = jSONObject.optString("version");
      arrayOfString[1] = jSONObject.optString("data");
    } catch (JSONException jSONException) {}
    return arrayOfString;
  }
  
  public final void a() {
    this.b.execute((Object[])new String[] { this.a, this.c });
  }
  
  public class a {
    public static double a(LatLng param1LatLng1, LatLng param1LatLng2) {
      double d1 = param1LatLng1.getLongitude();
      double d2 = param1LatLng1.getLatitude();
      double d3 = param1LatLng2.getLongitude();
      double d4 = param1LatLng2.getLatitude();
      d1 *= 0.01745329251994329D;
      double d5 = d2 * 0.01745329251994329D;
      d2 = d3 * 0.01745329251994329D;
      double d6 = d4 * 0.01745329251994329D;
      d3 = Math.sin(d1);
      d4 = Math.sin(d5);
      d1 = Math.cos(d1);
      d5 = Math.cos(d5);
      double d7 = Math.sin(d2);
      double d8 = Math.sin(d6);
      d2 = Math.cos(d2);
      d6 = Math.cos(d6);
      double[] arrayOfDouble1 = new double[3];
      double[] arrayOfDouble2 = new double[3];
      arrayOfDouble1[0] = d1 * d5;
      arrayOfDouble1[1] = d5 * d3;
      arrayOfDouble1[2] = d4;
      arrayOfDouble2[0] = d2 * d6;
      arrayOfDouble2[1] = d6 * d7;
      arrayOfDouble2[2] = d8;
      return Math.asin(Math.sqrt((arrayOfDouble1[0] - arrayOfDouble2[0]) * (arrayOfDouble1[0] - arrayOfDouble2[0]) + (arrayOfDouble1[1] - arrayOfDouble2[1]) * (arrayOfDouble1[1] - arrayOfDouble2[1]) + (arrayOfDouble1[2] - arrayOfDouble2[2]) * (arrayOfDouble1[2] - arrayOfDouble2[2])) / 2.0D) * 1.27420015798544E7D;
    }
    
    public static LatLng a(c param1c) {
      float f = (float)(param1c.b() * 180.0D / 2.003750834E7D);
      return new LatLng((float)((Math.atan(Math.exp((float)(param1c.a() * 180.0D / 2.003750834E7D) * Math.PI / 180.0D)) * 2.0D - 1.5707963267948966D) * 57.29577951308232D), f);
    }
    
    public static c a(PointF param1PointF1, c param1c, PointF param1PointF2, a param1a) {
      float f1 = param1PointF1.x;
      float f2 = param1PointF2.x;
      float f3 = param1PointF1.y;
      float f4 = param1PointF2.y;
      return new c(param1c.b() + (f1 - f2) * param1a.d(), param1c.a() - (f3 - f4) * param1a.d());
    }
    
    public static c a(LatLng param1LatLng) {
      if (param1LatLng == null)
        return null; 
      double d = param1LatLng.getLatitude();
      return new c(param1LatLng.getLongitude() * 2.003750834E7D / 180.0D, Math.log(Math.tan((d + 90.0D) * Math.PI / 360.0D)) / 0.017453292519943295D * 2.003750834E7D / 180.0D);
    }
    
    public static a a(float param1Float, Point param1Point) {
      e e = new e();
      e.a(param1Float);
      e.a(param1Point);
      return (a)e;
    }
    
    public static a a(CameraPosition param1CameraPosition) {
      c c = new c();
      c.a(param1CameraPosition);
      return (a)c;
    }
    
    public static a a(LatLngBounds param1LatLngBounds, int param1Int1, int param1Int2, int param1Int3) {
      b b = new b();
      b.a(param1LatLngBounds);
      b.c(param1Int3);
      b.a(param1Int1);
      b.b(param1Int2);
      return (a)b;
    }
    
    public static a a() {
      return a.a;
    }
    
    public static void a(Closeable param1Closeable) {
      if (param1Closeable != null)
        try {
          param1Closeable.close();
        } catch (IOException iOException) {} 
    }
    
    public static boolean a(byte[] param1ArrayOfbyte, String param1String) {
      byte[] arrayOfByte1 = null;
      FileOutputStream fileOutputStream1 = null;
      FileOutputStream fileOutputStream2 = fileOutputStream1;
      try {
        FileOutputStream fileOutputStream = new FileOutputStream();
        fileOutputStream2 = fileOutputStream1;
        this(param1String);
        try {
          fileOutputStream.write(param1ArrayOfbyte);
          return true;
        } catch (IOException null) {
        
        } finally {
          param1ArrayOfbyte = null;
        } 
      } catch (IOException iOException) {
        param1ArrayOfbyte = arrayOfByte1;
      } finally {}
      byte[] arrayOfByte2 = param1ArrayOfbyte;
      StringBuilder stringBuilder = new StringBuilder();
      arrayOfByte2 = param1ArrayOfbyte;
      this("saveData error:");
      arrayOfByte2 = param1ArrayOfbyte;
      stringBuilder.append(iOException.toString());
      arrayOfByte2 = param1ArrayOfbyte;
      iOException.printStackTrace();
      a((Closeable)param1ArrayOfbyte);
      return false;
    }
    
    public static byte[] a(InputStream param1InputStream) {
      // Byte code:
      //   0: aconst_null
      //   1: astore_1
      //   2: new java/io/ByteArrayOutputStream
      //   5: astore_2
      //   6: aload_2
      //   7: invokespecial <init> : ()V
      //   10: aload_2
      //   11: astore_1
      //   12: sipush #4096
      //   15: newarray byte
      //   17: astore_3
      //   18: aload_2
      //   19: astore_1
      //   20: aload_0
      //   21: aload_3
      //   22: iconst_0
      //   23: sipush #4096
      //   26: invokevirtual read : ([BII)I
      //   29: istore #4
      //   31: iload #4
      //   33: iconst_m1
      //   34: if_icmpeq -> 50
      //   37: aload_2
      //   38: astore_1
      //   39: aload_2
      //   40: aload_3
      //   41: iconst_0
      //   42: iload #4
      //   44: invokevirtual write : ([BII)V
      //   47: goto -> 18
      //   50: aload_2
      //   51: astore_1
      //   52: aload_2
      //   53: invokevirtual flush : ()V
      //   56: aload_2
      //   57: astore_1
      //   58: aload_2
      //   59: invokevirtual toByteArray : ()[B
      //   62: astore_0
      //   63: aload_2
      //   64: invokestatic a : (Ljava/io/Closeable;)V
      //   67: aload_0
      //   68: areturn
      //   69: astore_1
      //   70: aload_2
      //   71: astore_0
      //   72: aload_1
      //   73: astore_2
      //   74: goto -> 84
      //   77: astore_0
      //   78: goto -> 116
      //   81: astore_2
      //   82: aconst_null
      //   83: astore_0
      //   84: aload_0
      //   85: astore_1
      //   86: new java/lang/StringBuilder
      //   89: astore_3
      //   90: aload_0
      //   91: astore_1
      //   92: aload_3
      //   93: ldc 'IO:'
      //   95: invokespecial <init> : (Ljava/lang/String;)V
      //   98: aload_0
      //   99: astore_1
      //   100: aload_3
      //   101: aload_2
      //   102: invokevirtual toString : ()Ljava/lang/String;
      //   105: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   108: pop
      //   109: aload_0
      //   110: invokestatic a : (Ljava/io/Closeable;)V
      //   113: aconst_null
      //   114: areturn
      //   115: astore_0
      //   116: aload_1
      //   117: invokestatic a : (Ljava/io/Closeable;)V
      //   120: aload_0
      //   121: athrow
      // Exception table:
      //   from	to	target	type
      //   2	10	81	java/io/IOException
      //   2	10	77	finally
      //   12	18	69	java/io/IOException
      //   12	18	115	finally
      //   20	31	69	java/io/IOException
      //   20	31	115	finally
      //   39	47	69	java/io/IOException
      //   39	47	115	finally
      //   52	56	69	java/io/IOException
      //   52	56	115	finally
      //   58	63	69	java/io/IOException
      //   58	63	115	finally
      //   86	90	115	finally
      //   92	98	115	finally
      //   100	109	115	finally
    }
    
    public static byte[] a(String param1String) {
      // Byte code:
      //   0: aconst_null
      //   1: astore_1
      //   2: aconst_null
      //   3: astore_2
      //   4: new java/io/FileInputStream
      //   7: astore_3
      //   8: aload_3
      //   9: aload_0
      //   10: invokespecial <init> : (Ljava/lang/String;)V
      //   13: new java/io/ByteArrayOutputStream
      //   16: astore #4
      //   18: aload #4
      //   20: invokespecial <init> : ()V
      //   23: aload_3
      //   24: astore #5
      //   26: aload #4
      //   28: astore #6
      //   30: sipush #1024
      //   33: newarray byte
      //   35: astore_1
      //   36: aload_3
      //   37: astore #5
      //   39: aload #4
      //   41: astore #6
      //   43: aload_3
      //   44: aload_1
      //   45: invokevirtual read : ([B)I
      //   48: istore #7
      //   50: iload #7
      //   52: ifle -> 74
      //   55: aload_3
      //   56: astore #5
      //   58: aload #4
      //   60: astore #6
      //   62: aload #4
      //   64: aload_1
      //   65: iconst_0
      //   66: iload #7
      //   68: invokevirtual write : ([BII)V
      //   71: goto -> 36
      //   74: aload_3
      //   75: astore #5
      //   77: aload #4
      //   79: astore #6
      //   81: aload #4
      //   83: invokevirtual toByteArray : ()[B
      //   86: astore #8
      //   88: aload_3
      //   89: astore_1
      //   90: aload #8
      //   92: astore_0
      //   93: aload_1
      //   94: invokestatic a : (Ljava/io/Closeable;)V
      //   97: aload #4
      //   99: invokestatic a : (Ljava/io/Closeable;)V
      //   102: goto -> 230
      //   105: astore #5
      //   107: aload_3
      //   108: astore_1
      //   109: aload #4
      //   111: astore_3
      //   112: aload #5
      //   114: astore #4
      //   116: goto -> 154
      //   119: astore_0
      //   120: aconst_null
      //   121: astore #6
      //   123: goto -> 236
      //   126: astore #4
      //   128: aconst_null
      //   129: astore #5
      //   131: aload_3
      //   132: astore_1
      //   133: aload #5
      //   135: astore_3
      //   136: goto -> 154
      //   139: astore_0
      //   140: aconst_null
      //   141: astore #6
      //   143: aload_1
      //   144: astore_3
      //   145: goto -> 236
      //   148: astore #4
      //   150: aconst_null
      //   151: astore_1
      //   152: aload_1
      //   153: astore_3
      //   154: aload_1
      //   155: astore #5
      //   157: aload_3
      //   158: astore #6
      //   160: new java/lang/StringBuilder
      //   163: astore #8
      //   165: aload_1
      //   166: astore #5
      //   168: aload_3
      //   169: astore #6
      //   171: aload #8
      //   173: ldc 'get '
      //   175: invokespecial <init> : (Ljava/lang/String;)V
      //   178: aload_1
      //   179: astore #5
      //   181: aload_3
      //   182: astore #6
      //   184: aload #8
      //   186: aload_0
      //   187: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   190: pop
      //   191: aload_1
      //   192: astore #5
      //   194: aload_3
      //   195: astore #6
      //   197: aload #8
      //   199: ldc 'failed:'
      //   201: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   204: pop
      //   205: aload_1
      //   206: astore #5
      //   208: aload_3
      //   209: astore #6
      //   211: aload #8
      //   213: aload #4
      //   215: invokevirtual toString : ()Ljava/lang/String;
      //   218: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   221: pop
      //   222: aload_2
      //   223: astore_0
      //   224: aload_3
      //   225: astore #4
      //   227: goto -> 93
      //   230: aload_0
      //   231: areturn
      //   232: astore_0
      //   233: aload #5
      //   235: astore_3
      //   236: aload_3
      //   237: invokestatic a : (Ljava/io/Closeable;)V
      //   240: aload #6
      //   242: invokestatic a : (Ljava/io/Closeable;)V
      //   245: aload_0
      //   246: athrow
      // Exception table:
      //   from	to	target	type
      //   4	13	148	java/lang/Exception
      //   4	13	139	finally
      //   13	23	126	java/lang/Exception
      //   13	23	119	finally
      //   30	36	105	java/lang/Exception
      //   30	36	232	finally
      //   43	50	105	java/lang/Exception
      //   43	50	232	finally
      //   62	71	105	java/lang/Exception
      //   62	71	232	finally
      //   81	88	105	java/lang/Exception
      //   81	88	232	finally
      //   160	165	232	finally
      //   171	178	232	finally
      //   184	191	232	finally
      //   197	205	232	finally
      //   211	222	232	finally
    }
    
    public static String b() {
      StringBuilder stringBuilder = new StringBuilder(a.a().b());
      c(stringBuilder.toString());
      stringBuilder.append("bingLogo.dat");
      return stringBuilder.toString();
    }
    
    public static boolean b(String param1String) {
      if (TextUtils.isEmpty(param1String))
        return false; 
      Stack<String> stack = new Stack();
      stack.push(param1String);
      while (!stack.isEmpty()) {
        File file = new File(stack.peek());
        if (file.exists()) {
          if (file.isDirectory()) {
            File[] arrayOfFile = file.listFiles();
            if (arrayOfFile != null && arrayOfFile.length != 0) {
              int i = arrayOfFile.length;
              for (byte b = 0; b < i; b++) {
                file = arrayOfFile[b];
                if (file.isDirectory()) {
                  stack.push(file.getAbsolutePath());
                } else {
                  file.delete();
                } 
              } 
              continue;
            } 
          } 
          file.delete();
        } 
        stack.pop();
      } 
      return true;
    }
    
    public static String c() {
      c(a.a().b());
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(a.a().b());
      stringBuilder.append("/frontier.dat");
      return stringBuilder.toString();
    }
    
    public static boolean c(String param1String) {
      if (TextUtils.isEmpty(param1String))
        return false; 
      try {
        File file = new File();
        this(param1String);
        if (file.exists() && file.isDirectory())
          return true; 
        if (file.exists() && file.isFile())
          file.delete(); 
        return file.mkdirs();
      } catch (Exception exception) {
        return false;
      } 
    }
    
    private static String d() {
      String str = a.a().b();
      c(str);
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str);
      stringBuilder.append("cache.info");
      return stringBuilder.toString();
    }
    
    public final int a(int param1Int1, int param1Int2) {
      Exception exception;
      try {
        File file = new File();
        this(d());
        if (!file.exists())
          file.createNewFile(); 
        FileInputStream fileInputStream = new FileInputStream();
        this(file);
      } finally {
        exception = null;
      } 
      a((Closeable)exception);
      return -1;
    }
    
    public final int a(String param1String, boolean param1Boolean) {
      String str = d();
      StringBuilder stringBuilder1 = null;
      Properties properties = null;
      FileInputStream fileInputStream = null;
      StringBuilder stringBuilder2 = stringBuilder1;
      try {
        Exception exception;
        File file = new File();
        stringBuilder2 = stringBuilder1;
        this(str);
        FileInputStream fileInputStream1 = fileInputStream;
        stringBuilder2 = stringBuilder1;
        if (file.exists()) {
          fileInputStream1 = fileInputStream;
          stringBuilder2 = stringBuilder1;
          if (file.isFile()) {
            stringBuilder2 = stringBuilder1;
            fileInputStream1 = new FileInputStream();
            stringBuilder2 = stringBuilder1;
            this(file);
            try {
              properties = new Properties();
              this();
              properties.load(fileInputStream1);
              stringBuilder2 = new StringBuilder();
              this();
              stringBuilder2.append(param1String);
              stringBuilder2.append("_style");
              String str2 = stringBuilder2.toString();
              param1String = str2;
              if (param1Boolean) {
                StringBuilder stringBuilder = new StringBuilder();
                this();
                stringBuilder.append(str2);
                stringBuilder.append("_bing");
                str1 = stringBuilder.toString();
              } 
              String str1 = properties.getProperty(str1, String.valueOf(1000));
            } catch (Exception exception1) {
              FileInputStream fileInputStream2 = fileInputStream1;
            } finally {
              Exception exception1;
              param1String = null;
            } 
          } 
        } 
        a((Closeable)exception);
      } catch (Exception exception) {
        Properties properties1 = properties;
        Properties properties2 = properties1;
        StringBuilder stringBuilder = new StringBuilder();
        properties2 = properties1;
        this("error read file:");
        properties2 = properties1;
        stringBuilder.append(str);
        properties2 = properties1;
        stringBuilder.append(" with error:");
        properties2 = properties1;
        stringBuilder.append(exception.getMessage());
        Properties properties3 = properties1;
        a((Closeable)properties3);
      } finally {}
      return 1000;
    }
    
    public final boolean a(int param1Int1, int param1Int2, int param1Int3) {
      // Byte code:
      //   0: aconst_null
      //   1: astore #4
      //   3: new java/util/Properties
      //   6: astore #5
      //   8: aload #5
      //   10: invokespecial <init> : ()V
      //   13: new java/io/File
      //   16: astore #6
      //   18: aload #6
      //   20: invokestatic d : ()Ljava/lang/String;
      //   23: invokespecial <init> : (Ljava/lang/String;)V
      //   26: aload #6
      //   28: invokevirtual exists : ()Z
      //   31: ifeq -> 60
      //   34: new java/io/FileInputStream
      //   37: astore #7
      //   39: aload #7
      //   41: aload #6
      //   43: invokespecial <init> : (Ljava/io/File;)V
      //   46: aload #7
      //   48: astore #8
      //   50: aload #5
      //   52: aload #7
      //   54: invokevirtual load : (Ljava/io/InputStream;)V
      //   57: goto -> 63
      //   60: aconst_null
      //   61: astore #7
      //   63: iload_2
      //   64: ifeq -> 163
      //   67: iload_2
      //   68: iconst_1
      //   69: if_icmpeq -> 155
      //   72: iload_2
      //   73: iconst_2
      //   74: if_icmpeq -> 109
      //   77: iload_2
      //   78: iconst_3
      //   79: if_icmpeq -> 85
      //   82: goto -> 209
      //   85: ldc_w 'satelite_version'
      //   88: astore #4
      //   90: aload #7
      //   92: astore #8
      //   94: aload #5
      //   96: aload #4
      //   98: iload_3
      //   99: invokestatic valueOf : (I)Ljava/lang/String;
      //   102: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      //   105: pop
      //   106: goto -> 209
      //   109: aload #7
      //   111: astore #8
      //   113: new java/lang/StringBuilder
      //   116: astore #4
      //   118: aload #7
      //   120: astore #8
      //   122: aload #4
      //   124: ldc_w 'bing_version__'
      //   127: invokespecial <init> : (Ljava/lang/String;)V
      //   130: aload #7
      //   132: astore #8
      //   134: aload #4
      //   136: iload_1
      //   137: invokevirtual append : (I)Ljava/lang/StringBuilder;
      //   140: pop
      //   141: aload #7
      //   143: astore #8
      //   145: aload #4
      //   147: invokevirtual toString : ()Ljava/lang/String;
      //   150: astore #4
      //   152: goto -> 90
      //   155: ldc_w 'tencent_clean_version'
      //   158: astore #4
      //   160: goto -> 90
      //   163: aload #7
      //   165: astore #8
      //   167: new java/lang/StringBuilder
      //   170: astore #4
      //   172: aload #7
      //   174: astore #8
      //   176: aload #4
      //   178: ldc_w 'tencent_version__'
      //   181: invokespecial <init> : (Ljava/lang/String;)V
      //   184: aload #7
      //   186: astore #8
      //   188: aload #4
      //   190: iload_1
      //   191: invokevirtual append : (I)Ljava/lang/StringBuilder;
      //   194: pop
      //   195: aload #7
      //   197: astore #8
      //   199: aload #4
      //   201: invokevirtual toString : ()Ljava/lang/String;
      //   204: astore #4
      //   206: goto -> 90
      //   209: aload #7
      //   211: astore #8
      //   213: new java/io/FileOutputStream
      //   216: astore #4
      //   218: aload #7
      //   220: astore #8
      //   222: aload #4
      //   224: aload #6
      //   226: invokespecial <init> : (Ljava/io/File;)V
      //   229: aload #5
      //   231: aload #4
      //   233: ldc_w 'mapinfo'
      //   236: invokevirtual store : (Ljava/io/OutputStream;Ljava/lang/String;)V
      //   239: aload #7
      //   241: invokestatic a : (Ljava/io/Closeable;)V
      //   244: aload #4
      //   246: invokestatic a : (Ljava/io/Closeable;)V
      //   249: iconst_1
      //   250: ireturn
      //   251: astore #7
      //   253: aconst_null
      //   254: astore #7
      //   256: aload #8
      //   258: astore #4
      //   260: goto -> 268
      //   263: astore #7
      //   265: aconst_null
      //   266: astore #7
      //   268: aload #4
      //   270: invokestatic a : (Ljava/io/Closeable;)V
      //   273: aload #7
      //   275: invokestatic a : (Ljava/io/Closeable;)V
      //   278: iconst_0
      //   279: ireturn
      //   280: astore #8
      //   282: aload #7
      //   284: astore #8
      //   286: aload #4
      //   288: astore #7
      //   290: goto -> 256
      // Exception table:
      //   from	to	target	type
      //   3	46	263	finally
      //   50	57	251	finally
      //   94	106	251	finally
      //   113	118	251	finally
      //   122	130	251	finally
      //   134	141	251	finally
      //   145	152	251	finally
      //   167	172	251	finally
      //   176	184	251	finally
      //   188	195	251	finally
      //   199	206	251	finally
      //   213	218	251	finally
      //   222	229	251	finally
      //   229	239	280	finally
    }
    
    public final boolean a(String param1String, int param1Int, boolean param1Boolean) {
      Exception exception1;
      File file1;
      File file2;
      Exception exception3;
      String str = d();
      FileInputStream fileInputStream1 = null;
      FileInputStream fileInputStream2 = null;
      try {
        File file;
        Properties properties = new Properties();
        this();
      } catch (Exception exception2) {
      
      } finally {
        exception3 = null;
        file2 = null;
        exception1 = exception2;
      } 
      try {
        StringBuilder stringBuilder = new StringBuilder();
        this("error read file:");
        stringBuilder.append(str);
        stringBuilder.append(" with error:");
        stringBuilder.append(file1.getMessage());
        return false;
      } finally {
        File file;
        file2 = null;
        Exception exception = exception3;
      } 
    }
    
    static final class a {
      public static final d.a a = new d.a();
    }
  }
  
  static final class a {
    public static final d.a a = new d.a();
  }
  
  public static interface b {
    void a(int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6, Bitmap param1Bitmap);
  }
  
  static final class c extends AsyncTask<String, Void, d> {
    private d.b a;
    
    public c(d.b param1b) {
      this.a = param1b;
    }
    
    private d.d a(String... param1VarArgs) {
      // Byte code:
      //   0: iconst_0
      //   1: istore_2
      //   2: new com/tencent/mapsdk/rastercore/d$d
      //   5: dup
      //   6: iconst_0
      //   7: invokespecial <init> : (B)V
      //   10: astore_3
      //   11: aload_1
      //   12: ifnull -> 1207
      //   15: aload_1
      //   16: arraylength
      //   17: iconst_2
      //   18: if_icmpne -> 1207
      //   21: aload_3
      //   22: getstatic com/tencent/mapsdk/rastercore/b.e : I
      //   25: putfield a : I
      //   28: aload_3
      //   29: invokestatic v : ()I
      //   32: putfield b : I
      //   35: aload_3
      //   36: invokestatic t : ()I
      //   39: putfield c : I
      //   42: aload_3
      //   43: invokestatic s : ()I
      //   46: putfield d : I
      //   49: aload_3
      //   50: invokestatic w : ()I
      //   53: putfield e : I
      //   56: aload_3
      //   57: invokestatic x : ()I
      //   60: putfield f : I
      //   63: getstatic com/tencent/mapsdk/rastercore/d$a$a.a : Lcom/tencent/mapsdk/rastercore/d$a;
      //   66: astore #4
      //   68: invokestatic c : ()Ljava/lang/String;
      //   71: invokestatic a : (Ljava/lang/String;)[B
      //   74: astore #5
      //   76: aload #5
      //   78: ifnull -> 123
      //   81: new java/lang/String
      //   84: astore #4
      //   86: aload #4
      //   88: aload #5
      //   90: ldc 'utf-8'
      //   92: invokespecial <init> : ([BLjava/lang/String;)V
      //   95: goto -> 126
      //   98: astore #4
      //   100: new java/lang/StringBuilder
      //   103: dup
      //   104: ldc 'decode frontier.dat to string error:'
      //   106: invokespecial <init> : (Ljava/lang/String;)V
      //   109: aload #4
      //   111: invokevirtual toString : ()Ljava/lang/String;
      //   114: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   117: pop
      //   118: aload #4
      //   120: invokevirtual printStackTrace : ()V
      //   123: aconst_null
      //   124: astore #4
      //   126: aload #4
      //   128: invokestatic a : (Ljava/lang/String;)Z
      //   131: ifne -> 194
      //   134: aload #4
      //   136: invokestatic b : (Ljava/lang/String;)[Ljava/lang/String;
      //   139: astore #5
      //   141: aload #5
      //   143: iconst_0
      //   144: aaload
      //   145: invokestatic a : (Ljava/lang/String;)Z
      //   148: ifne -> 163
      //   151: aload #5
      //   153: iconst_0
      //   154: aaload
      //   155: invokestatic parseInt : (Ljava/lang/String;)I
      //   158: istore #6
      //   160: goto -> 166
      //   163: iconst_0
      //   164: istore #6
      //   166: iload #6
      //   168: istore #7
      //   170: aload #5
      //   172: iconst_1
      //   173: aaload
      //   174: invokestatic a : (Ljava/lang/String;)Z
      //   177: ifne -> 197
      //   180: aload #5
      //   182: iconst_1
      //   183: aaload
      //   184: invokestatic a : (Ljava/lang/String;)V
      //   187: iload #6
      //   189: istore #7
      //   191: goto -> 197
      //   194: iconst_0
      //   195: istore #7
      //   197: new java/net/URL
      //   200: astore #8
      //   202: new java/lang/StringBuilder
      //   205: astore #5
      //   207: aload #5
      //   209: invokespecial <init> : ()V
      //   212: aload #5
      //   214: aload_1
      //   215: iconst_0
      //   216: aaload
      //   217: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   220: pop
      //   221: aload #5
      //   223: ldc '&frontier='
      //   225: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   228: pop
      //   229: aload #5
      //   231: iload #7
      //   233: invokevirtual append : (I)Ljava/lang/StringBuilder;
      //   236: pop
      //   237: aload #8
      //   239: aload #5
      //   241: invokevirtual toString : ()Ljava/lang/String;
      //   244: invokespecial <init> : (Ljava/lang/String;)V
      //   247: aload #8
      //   249: invokevirtual openConnection : ()Ljava/net/URLConnection;
      //   252: checkcast java/net/HttpURLConnection
      //   255: astore #8
      //   257: aload #8
      //   259: ldc 'Accept-Encoding'
      //   261: ldc 'gzip'
      //   263: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
      //   266: aload #8
      //   268: invokevirtual getResponseCode : ()I
      //   271: sipush #200
      //   274: if_icmpne -> 1207
      //   277: aload #8
      //   279: ldc 'Content-Encoding'
      //   281: invokevirtual getHeaderField : (Ljava/lang/String;)Ljava/lang/String;
      //   284: astore #5
      //   286: aload #5
      //   288: ifnull -> 318
      //   291: aload #5
      //   293: invokevirtual length : ()I
      //   296: ifle -> 318
      //   299: aload #5
      //   301: invokevirtual toLowerCase : ()Ljava/lang/String;
      //   304: ldc 'gzip'
      //   306: invokevirtual contains : (Ljava/lang/CharSequence;)Z
      //   309: ifeq -> 318
      //   312: iconst_1
      //   313: istore #6
      //   315: goto -> 321
      //   318: iconst_0
      //   319: istore #6
      //   321: iload #6
      //   323: ifeq -> 344
      //   326: new java/util/zip/GZIPInputStream
      //   329: astore #5
      //   331: aload #5
      //   333: aload #8
      //   335: invokevirtual getInputStream : ()Ljava/io/InputStream;
      //   338: invokespecial <init> : (Ljava/io/InputStream;)V
      //   341: goto -> 351
      //   344: aload #8
      //   346: invokevirtual getInputStream : ()Ljava/io/InputStream;
      //   349: astore #5
      //   351: new java/lang/String
      //   354: astore #8
      //   356: aload #8
      //   358: aload #5
      //   360: invokestatic a : (Ljava/io/InputStream;)[B
      //   363: invokespecial <init> : ([B)V
      //   366: new java/lang/StringBuilder
      //   369: astore #5
      //   371: aload #5
      //   373: ldc 'VersionChecker Response:'
      //   375: invokespecial <init> : (Ljava/lang/String;)V
      //   378: aload #5
      //   380: aload #8
      //   382: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   385: pop
      //   386: new org/json/JSONObject
      //   389: astore #5
      //   391: aload #5
      //   393: aload #8
      //   395: invokespecial <init> : (Ljava/lang/String;)V
      //   398: aload #5
      //   400: ldc 'error'
      //   402: invokevirtual optInt : (Ljava/lang/String;)I
      //   405: ifeq -> 410
      //   408: aload_3
      //   409: areturn
      //   410: aload #5
      //   412: ldc 'info'
      //   414: invokevirtual optJSONObject : (Ljava/lang/String;)Lorg/json/JSONObject;
      //   417: astore #8
      //   419: aload #8
      //   421: ifnonnull -> 426
      //   424: aload_3
      //   425: areturn
      //   426: aload #8
      //   428: ldc 'raster'
      //   430: invokevirtual optJSONObject : (Ljava/lang/String;)Lorg/json/JSONObject;
      //   433: astore #5
      //   435: aload #5
      //   437: ifnonnull -> 442
      //   440: aload_3
      //   441: areturn
      //   442: aload_3
      //   443: aload #5
      //   445: ldc 'style'
      //   447: sipush #1000
      //   450: invokevirtual optInt : (Ljava/lang/String;I)I
      //   453: putfield a : I
      //   456: aload_3
      //   457: aload #5
      //   459: ldc 'version'
      //   461: getstatic com/tencent/mapsdk/rastercore/b.a : I
      //   464: invokevirtual optInt : (Ljava/lang/String;I)I
      //   467: putfield b : I
      //   470: aload #5
      //   472: ldc 'cur'
      //   474: getstatic com/tencent/mapsdk/rastercore/b.b : I
      //   477: invokevirtual optInt : (Ljava/lang/String;I)I
      //   480: istore #9
      //   482: aload #5
      //   484: ldc 'sat'
      //   486: getstatic com/tencent/mapsdk/rastercore/b.d : I
      //   489: invokevirtual optInt : (Ljava/lang/String;I)I
      //   492: istore #10
      //   494: getstatic com/tencent/mapsdk/rastercore/d$a$a.a : Lcom/tencent/mapsdk/rastercore/d$a;
      //   497: aload_3
      //   498: getfield a : I
      //   501: iconst_0
      //   502: aload_3
      //   503: getfield b : I
      //   506: invokevirtual a : (III)Z
      //   509: pop
      //   510: getstatic com/tencent/mapsdk/rastercore/d$a$a.a : Lcom/tencent/mapsdk/rastercore/d$a;
      //   513: aload_1
      //   514: iconst_1
      //   515: aaload
      //   516: aload_3
      //   517: getfield a : I
      //   520: iconst_0
      //   521: invokevirtual a : (Ljava/lang/String;IZ)Z
      //   524: pop
      //   525: getstatic com/tencent/mapsdk/rastercore/d$a$a.a : Lcom/tencent/mapsdk/rastercore/d$a;
      //   528: aload_3
      //   529: getfield a : I
      //   532: iconst_3
      //   533: iload #10
      //   535: invokevirtual a : (III)Z
      //   538: pop
      //   539: getstatic com/tencent/mapsdk/rastercore/d$a$a.a : Lcom/tencent/mapsdk/rastercore/d$a;
      //   542: aload_3
      //   543: getfield a : I
      //   546: iconst_1
      //   547: iload #9
      //   549: invokevirtual a : (III)Z
      //   552: pop
      //   553: aload #8
      //   555: ldc 'world'
      //   557: invokevirtual optJSONObject : (Ljava/lang/String;)Lorg/json/JSONObject;
      //   560: astore #5
      //   562: aload #5
      //   564: ifnonnull -> 569
      //   567: aload_3
      //   568: areturn
      //   569: aload_3
      //   570: aload #5
      //   572: ldc 'style'
      //   574: sipush #1000
      //   577: invokevirtual optInt : (Ljava/lang/String;I)I
      //   580: putfield c : I
      //   583: aload #5
      //   585: ldc 'version'
      //   587: getstatic com/tencent/mapsdk/rastercore/b.c : I
      //   590: invokevirtual optInt : (Ljava/lang/String;I)I
      //   593: istore #11
      //   595: aload #5
      //   597: ldc 'logo'
      //   599: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
      //   602: astore #5
      //   604: new java/io/File
      //   607: astore #12
      //   609: aload #12
      //   611: invokestatic b : ()Ljava/lang/String;
      //   614: invokespecial <init> : (Ljava/lang/String;)V
      //   617: aload #12
      //   619: invokevirtual exists : ()Z
      //   622: iconst_1
      //   623: ixor
      //   624: istore #7
      //   626: iload #7
      //   628: istore #6
      //   630: iload #7
      //   632: ifne -> 707
      //   635: invokestatic b : ()Ljava/lang/String;
      //   638: invokestatic a : (Ljava/lang/String;)[B
      //   641: astore #12
      //   643: aload #12
      //   645: ifnull -> 661
      //   648: aload_3
      //   649: aload #12
      //   651: iconst_0
      //   652: aload #12
      //   654: arraylength
      //   655: invokestatic decodeByteArray : ([BII)Landroid/graphics/Bitmap;
      //   658: putfield g : Landroid/graphics/Bitmap;
      //   661: aload_3
      //   662: getfield g : Landroid/graphics/Bitmap;
      //   665: astore #12
      //   667: iload #7
      //   669: istore #6
      //   671: aload #12
      //   673: ifnonnull -> 707
      //   676: goto -> 704
      //   679: astore #13
      //   681: new java/lang/StringBuilder
      //   684: astore #12
      //   686: aload #12
      //   688: ldc 'decode bing logo error :'
      //   690: invokespecial <init> : (Ljava/lang/String;)V
      //   693: aload #12
      //   695: aload #13
      //   697: invokevirtual toString : ()Ljava/lang/String;
      //   700: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   703: pop
      //   704: iconst_1
      //   705: istore #6
      //   707: iload #6
      //   709: ifeq -> 865
      //   712: aload #5
      //   714: ifnull -> 865
      //   717: aload #5
      //   719: invokevirtual trim : ()Ljava/lang/String;
      //   722: invokevirtual length : ()I
      //   725: istore #6
      //   727: iload #6
      //   729: ifle -> 865
      //   732: new java/net/URL
      //   735: astore #12
      //   737: aload #12
      //   739: aload #5
      //   741: invokespecial <init> : (Ljava/lang/String;)V
      //   744: aload #12
      //   746: invokevirtual openConnection : ()Ljava/net/URLConnection;
      //   749: checkcast java/net/HttpURLConnection
      //   752: astore #5
      //   754: aload #5
      //   756: ldc 'GET'
      //   758: invokevirtual setRequestMethod : (Ljava/lang/String;)V
      //   761: aload #5
      //   763: sipush #5000
      //   766: invokevirtual setConnectTimeout : (I)V
      //   769: aload #5
      //   771: invokevirtual getResponseCode : ()I
      //   774: sipush #200
      //   777: if_icmpne -> 812
      //   780: aload #5
      //   782: invokevirtual getInputStream : ()Ljava/io/InputStream;
      //   785: invokestatic a : (Ljava/io/InputStream;)[B
      //   788: astore #12
      //   790: aload_3
      //   791: aload #12
      //   793: iconst_0
      //   794: aload #12
      //   796: arraylength
      //   797: invokestatic decodeByteArray : ([BII)Landroid/graphics/Bitmap;
      //   800: putfield g : Landroid/graphics/Bitmap;
      //   803: aload #12
      //   805: invokestatic b : ()Ljava/lang/String;
      //   808: invokestatic a : ([BLjava/lang/String;)Z
      //   811: pop
      //   812: aload #5
      //   814: ifnull -> 865
      //   817: aload #5
      //   819: invokevirtual disconnect : ()V
      //   822: goto -> 865
      //   825: astore_1
      //   826: goto -> 838
      //   829: astore #12
      //   831: goto -> 855
      //   834: astore_1
      //   835: aconst_null
      //   836: astore #5
      //   838: aload #5
      //   840: ifnull -> 848
      //   843: aload #5
      //   845: invokevirtual disconnect : ()V
      //   848: aload_1
      //   849: athrow
      //   850: astore #5
      //   852: aconst_null
      //   853: astore #5
      //   855: aload #5
      //   857: ifnull -> 865
      //   860: aload #5
      //   862: invokevirtual disconnect : ()V
      //   865: aload #8
      //   867: ldc_w 'frontier'
      //   870: invokevirtual optJSONObject : (Ljava/lang/String;)Lorg/json/JSONObject;
      //   873: astore #5
      //   875: aload #5
      //   877: ifnull -> 1066
      //   880: aload #5
      //   882: ldc_w 'path'
      //   885: invokevirtual optString : (Ljava/lang/String;)Ljava/lang/String;
      //   888: astore #5
      //   890: aload #5
      //   892: invokestatic a : (Ljava/lang/String;)Z
      //   895: ifne -> 1066
      //   898: new java/net/URL
      //   901: astore #8
      //   903: aload #8
      //   905: aload #5
      //   907: invokespecial <init> : (Ljava/lang/String;)V
      //   910: aload #8
      //   912: invokevirtual openConnection : ()Ljava/net/URLConnection;
      //   915: checkcast java/net/HttpURLConnection
      //   918: astore #8
      //   920: aload #8
      //   922: ldc 'GET'
      //   924: invokevirtual setRequestMethod : (Ljava/lang/String;)V
      //   927: aload #8
      //   929: sipush #5000
      //   932: invokevirtual setConnectTimeout : (I)V
      //   935: aload #8
      //   937: invokevirtual getResponseCode : ()I
      //   940: sipush #200
      //   943: if_icmpne -> 1066
      //   946: new java/util/zip/GZIPInputStream
      //   949: astore #5
      //   951: aload #5
      //   953: aload #8
      //   955: invokevirtual getInputStream : ()Ljava/io/InputStream;
      //   958: invokespecial <init> : (Ljava/io/InputStream;)V
      //   961: aload #5
      //   963: invokestatic a : (Ljava/io/InputStream;)[B
      //   966: invokestatic c : ()Ljava/lang/String;
      //   969: invokestatic a : ([BLjava/lang/String;)Z
      //   972: pop
      //   973: getstatic com/tencent/mapsdk/rastercore/d$a$a.a : Lcom/tencent/mapsdk/rastercore/d$a;
      //   976: astore #5
      //   978: invokestatic c : ()Ljava/lang/String;
      //   981: invokestatic a : (Ljava/lang/String;)[B
      //   984: astore #5
      //   986: aload #5
      //   988: ifnull -> 1005
      //   991: new java/lang/String
      //   994: astore #4
      //   996: aload #4
      //   998: aload #5
      //   1000: ldc 'utf-8'
      //   1002: invokespecial <init> : ([BLjava/lang/String;)V
      //   1005: aload #4
      //   1007: invokestatic a : (Ljava/lang/String;)Z
      //   1010: ifne -> 1066
      //   1013: aload #4
      //   1015: invokestatic b : (Ljava/lang/String;)[Ljava/lang/String;
      //   1018: astore #4
      //   1020: aload #4
      //   1022: iconst_1
      //   1023: aaload
      //   1024: invokestatic a : (Ljava/lang/String;)Z
      //   1027: ifne -> 1066
      //   1030: aload #4
      //   1032: iconst_1
      //   1033: aaload
      //   1034: invokestatic a : (Ljava/lang/String;)V
      //   1037: goto -> 1066
      //   1040: astore #5
      //   1042: new java/lang/StringBuilder
      //   1045: astore #4
      //   1047: aload #4
      //   1049: ldc_w 'frontier is already the new:'
      //   1052: invokespecial <init> : (Ljava/lang/String;)V
      //   1055: aload #4
      //   1057: aload #5
      //   1059: invokevirtual toString : ()Ljava/lang/String;
      //   1062: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1065: pop
      //   1066: getstatic com/tencent/mapsdk/rastercore/d$a$a.a : Lcom/tencent/mapsdk/rastercore/d$a;
      //   1069: aload_3
      //   1070: getfield c : I
      //   1073: iconst_2
      //   1074: iload #11
      //   1076: invokevirtual a : (III)Z
      //   1079: pop
      //   1080: getstatic com/tencent/mapsdk/rastercore/d$a$a.a : Lcom/tencent/mapsdk/rastercore/d$a;
      //   1083: aload_1
      //   1084: iconst_1
      //   1085: aaload
      //   1086: aload_3
      //   1087: getfield c : I
      //   1090: iconst_1
      //   1091: invokevirtual a : (Ljava/lang/String;IZ)Z
      //   1094: pop
      //   1095: iload #9
      //   1097: aload_3
      //   1098: getfield f : I
      //   1101: if_icmpeq -> 1110
      //   1104: iconst_1
      //   1105: istore #14
      //   1107: goto -> 1113
      //   1110: iconst_0
      //   1111: istore #14
      //   1113: iload #11
      //   1115: aload_3
      //   1116: getfield d : I
      //   1119: if_icmpeq -> 1128
      //   1122: iconst_1
      //   1123: istore #15
      //   1125: goto -> 1131
      //   1128: iconst_0
      //   1129: istore #15
      //   1131: iload #10
      //   1133: aload_3
      //   1134: getfield e : I
      //   1137: if_icmpeq -> 1142
      //   1140: iconst_1
      //   1141: istore_2
      //   1142: new com/tencent/mapsdk/rastercore/d$c$1
      //   1145: astore_1
      //   1146: aload_1
      //   1147: aload_0
      //   1148: iload #14
      //   1150: iload #15
      //   1152: iload_2
      //   1153: invokespecial <init> : (Lcom/tencent/mapsdk/rastercore/d$c;ZZZ)V
      //   1156: aload_1
      //   1157: invokevirtual start : ()V
      //   1160: aload_3
      //   1161: iload #9
      //   1163: putfield f : I
      //   1166: aload_3
      //   1167: iload #11
      //   1169: putfield d : I
      //   1172: aload_3
      //   1173: iload #10
      //   1175: putfield e : I
      //   1178: goto -> 1207
      //   1181: astore_1
      //   1182: goto -> 1186
      //   1185: astore_1
      //   1186: new java/lang/StringBuilder
      //   1189: dup
      //   1190: ldc_w 'check version got error:'
      //   1193: invokespecial <init> : (Ljava/lang/String;)V
      //   1196: aload_1
      //   1197: invokevirtual getMessage : ()Ljava/lang/String;
      //   1200: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1203: pop
      //   1204: goto -> 1207
      //   1207: aload_3
      //   1208: areturn
      // Exception table:
      //   from	to	target	type
      //   68	76	98	java/lang/Exception
      //   81	95	98	java/lang/Exception
      //   197	286	1185	java/lang/Exception
      //   291	312	1185	java/lang/Exception
      //   326	341	1185	java/lang/Exception
      //   344	351	1185	java/lang/Exception
      //   351	408	1185	java/lang/Exception
      //   410	419	1185	java/lang/Exception
      //   426	435	1185	java/lang/Exception
      //   442	562	1185	java/lang/Exception
      //   569	626	1185	java/lang/Exception
      //   635	643	1185	java/lang/Exception
      //   648	661	679	java/lang/Exception
      //   661	667	679	java/lang/Exception
      //   681	704	1185	java/lang/Exception
      //   717	727	1185	java/lang/Exception
      //   732	754	850	java/lang/Exception
      //   732	754	834	finally
      //   754	812	829	java/lang/Exception
      //   754	812	825	finally
      //   817	822	1185	java/lang/Exception
      //   843	848	1185	java/lang/Exception
      //   848	850	1185	java/lang/Exception
      //   860	865	1185	java/lang/Exception
      //   865	875	1040	java/lang/Exception
      //   880	986	1040	java/lang/Exception
      //   991	1005	1040	java/lang/Exception
      //   1005	1037	1040	java/lang/Exception
      //   1042	1066	1185	java/lang/Exception
      //   1066	1104	1185	java/lang/Exception
      //   1113	1122	1185	java/lang/Exception
      //   1131	1140	1185	java/lang/Exception
      //   1142	1146	1185	java/lang/Exception
      //   1146	1178	1181	java/lang/Exception
    }
  }
  
  final class null extends Thread {
    null(d this$0, boolean param1Boolean1, boolean param1Boolean2, boolean param1Boolean3) {}
    
    public final void run() {
      if (this.a)
        a.a().a(MapTile.MapSource.TENCENT); 
      if (this.b)
        a.a().a(MapTile.MapSource.BING); 
      if (this.c)
        a.a().a(MapTile.MapSource.SATELLITE); 
    }
  }
  
  static final class d {
    public int a = 1000;
    
    public int b = b.a;
    
    public int c = 1000;
    
    public int d = b.c;
    
    public int e = b.d;
    
    public int f = b.b;
    
    public Bitmap g = null;
    
    private d() {}
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */