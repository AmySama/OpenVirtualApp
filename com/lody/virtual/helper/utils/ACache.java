package com.lody.virtual.helper.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Process;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONArray;
import org.json.JSONObject;

public class ACache {
  private static final int MAX_COUNT = 2147483647;
  
  private static final int MAX_SIZE = 50000000;
  
  public static final int TIME_DAY = 86400;
  
  public static final int TIME_HOUR = 3600;
  
  private static Map<String, ACache> mInstanceMap = new HashMap<String, ACache>();
  
  private ACacheManager mCache;
  
  private ACache(File paramFile, long paramLong, int paramInt) {
    if (paramFile.exists() || paramFile.mkdirs()) {
      this.mCache = new ACacheManager(paramFile, paramLong, paramInt);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("can't make dirs in ");
    stringBuilder.append(paramFile.getAbsolutePath());
    throw new RuntimeException(stringBuilder.toString());
  }
  
  public static ACache get(Context paramContext) {
    return get(paramContext, "ACache");
  }
  
  public static ACache get(Context paramContext, long paramLong, int paramInt) {
    return get(new File(paramContext.getCacheDir(), "ACache"), paramLong, paramInt);
  }
  
  public static ACache get(Context paramContext, String paramString) {
    return get(new File(paramContext.getCacheDir(), paramString), 50000000L, 2147483647);
  }
  
  public static ACache get(File paramFile) {
    return get(paramFile, 50000000L, 2147483647);
  }
  
  public static ACache get(File paramFile, long paramLong, int paramInt) {
    Map<String, ACache> map = mInstanceMap;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramFile.getAbsoluteFile());
    stringBuilder.append(myPid());
    ACache aCache1 = map.get(stringBuilder.toString());
    ACache aCache2 = aCache1;
    if (aCache1 == null) {
      aCache2 = new ACache(paramFile, paramLong, paramInt);
      Map<String, ACache> map1 = mInstanceMap;
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(paramFile.getAbsolutePath());
      stringBuilder1.append(myPid());
      map1.put(stringBuilder1.toString(), aCache2);
    } 
    return aCache2;
  }
  
  private static String myPid() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("_");
    stringBuilder.append(Process.myPid());
    return stringBuilder.toString();
  }
  
  public void clear() {
    this.mCache.clear();
  }
  
  public File file(String paramString) {
    File file = this.mCache.newFile(paramString);
    return file.exists() ? file : null;
  }
  
  public InputStream get(String paramString) throws FileNotFoundException {
    File file = this.mCache.get(paramString);
    return !file.exists() ? null : new FileInputStream(file);
  }
  
  public byte[] getAsBinary(String paramString) {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aload_0
    //   3: getfield mCache : Lcom/lody/virtual/helper/utils/ACache$ACacheManager;
    //   6: aload_1
    //   7: invokestatic access$500 : (Lcom/lody/virtual/helper/utils/ACache$ACacheManager;Ljava/lang/String;)Ljava/io/File;
    //   10: astore_3
    //   11: aload_3
    //   12: invokevirtual exists : ()Z
    //   15: ifne -> 20
    //   18: aconst_null
    //   19: areturn
    //   20: new java/io/RandomAccessFile
    //   23: astore #4
    //   25: aload #4
    //   27: aload_3
    //   28: ldc 'r'
    //   30: invokespecial <init> : (Ljava/io/File;Ljava/lang/String;)V
    //   33: aload #4
    //   35: astore_2
    //   36: aload #4
    //   38: invokevirtual length : ()J
    //   41: l2i
    //   42: newarray byte
    //   44: astore_3
    //   45: aload #4
    //   47: astore_2
    //   48: aload #4
    //   50: aload_3
    //   51: invokevirtual read : ([B)I
    //   54: pop
    //   55: aload #4
    //   57: astore_2
    //   58: aload_3
    //   59: invokestatic access$900 : ([B)Z
    //   62: ifne -> 88
    //   65: aload #4
    //   67: astore_2
    //   68: aload_3
    //   69: invokestatic access$1000 : ([B)[B
    //   72: astore_1
    //   73: aload #4
    //   75: invokevirtual close : ()V
    //   78: goto -> 86
    //   81: astore_2
    //   82: aload_2
    //   83: invokevirtual printStackTrace : ()V
    //   86: aload_1
    //   87: areturn
    //   88: aload #4
    //   90: invokevirtual close : ()V
    //   93: goto -> 101
    //   96: astore_2
    //   97: aload_2
    //   98: invokevirtual printStackTrace : ()V
    //   101: aload_0
    //   102: aload_1
    //   103: invokevirtual remove : (Ljava/lang/String;)Z
    //   106: pop
    //   107: aconst_null
    //   108: areturn
    //   109: astore_2
    //   110: aload #4
    //   112: astore_1
    //   113: aload_2
    //   114: astore #4
    //   116: goto -> 127
    //   119: astore_1
    //   120: goto -> 153
    //   123: astore #4
    //   125: aconst_null
    //   126: astore_1
    //   127: aload_1
    //   128: astore_2
    //   129: aload #4
    //   131: invokevirtual printStackTrace : ()V
    //   134: aload_1
    //   135: ifnull -> 150
    //   138: aload_1
    //   139: invokevirtual close : ()V
    //   142: goto -> 150
    //   145: astore_1
    //   146: aload_1
    //   147: invokevirtual printStackTrace : ()V
    //   150: aconst_null
    //   151: areturn
    //   152: astore_1
    //   153: aload_2
    //   154: ifnull -> 169
    //   157: aload_2
    //   158: invokevirtual close : ()V
    //   161: goto -> 169
    //   164: astore_2
    //   165: aload_2
    //   166: invokevirtual printStackTrace : ()V
    //   169: aload_1
    //   170: athrow
    // Exception table:
    //   from	to	target	type
    //   2	18	123	java/lang/Exception
    //   2	18	119	finally
    //   20	33	123	java/lang/Exception
    //   20	33	119	finally
    //   36	45	109	java/lang/Exception
    //   36	45	152	finally
    //   48	55	109	java/lang/Exception
    //   48	55	152	finally
    //   58	65	109	java/lang/Exception
    //   58	65	152	finally
    //   68	73	109	java/lang/Exception
    //   68	73	152	finally
    //   73	78	81	java/io/IOException
    //   88	93	96	java/io/IOException
    //   129	134	152	finally
    //   138	142	145	java/io/IOException
    //   157	161	164	java/io/IOException
  }
  
  public Bitmap getAsBitmap(String paramString) {
    return (getAsBinary(paramString) == null) ? null : Utils.Bytes2Bimap(getAsBinary(paramString));
  }
  
  public Drawable getAsDrawable(String paramString) {
    return (getAsBinary(paramString) == null) ? null : Utils.bitmap2Drawable(Utils.Bytes2Bimap(getAsBinary(paramString)));
  }
  
  public JSONArray getAsJSONArray(String paramString) {
    paramString = getAsString(paramString);
    try {
      return new JSONArray(paramString);
    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    } 
  }
  
  public JSONObject getAsJSONObject(String paramString) {
    paramString = getAsString(paramString);
    try {
      return new JSONObject(paramString);
    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    } 
  }
  
  public Object getAsObject(String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokevirtual getAsBinary : (Ljava/lang/String;)[B
    //   5: astore_2
    //   6: aload_2
    //   7: ifnull -> 201
    //   10: new java/io/ByteArrayInputStream
    //   13: astore_1
    //   14: aload_1
    //   15: aload_2
    //   16: invokespecial <init> : ([B)V
    //   19: new java/io/ObjectInputStream
    //   22: astore_3
    //   23: aload_3
    //   24: aload_1
    //   25: invokespecial <init> : (Ljava/io/InputStream;)V
    //   28: aload_1
    //   29: astore_2
    //   30: aload_3
    //   31: astore #4
    //   33: aload_3
    //   34: invokevirtual readObject : ()Ljava/lang/Object;
    //   37: astore #5
    //   39: aload_1
    //   40: invokevirtual close : ()V
    //   43: goto -> 51
    //   46: astore_1
    //   47: aload_1
    //   48: invokevirtual printStackTrace : ()V
    //   51: aload_3
    //   52: invokevirtual close : ()V
    //   55: goto -> 63
    //   58: astore_1
    //   59: aload_1
    //   60: invokevirtual printStackTrace : ()V
    //   63: aload #5
    //   65: areturn
    //   66: astore #6
    //   68: aload_1
    //   69: astore #5
    //   71: aload_3
    //   72: astore_1
    //   73: goto -> 111
    //   76: astore_2
    //   77: aconst_null
    //   78: astore #4
    //   80: goto -> 165
    //   83: astore #6
    //   85: aconst_null
    //   86: astore_2
    //   87: aload_1
    //   88: astore #5
    //   90: aload_2
    //   91: astore_1
    //   92: goto -> 111
    //   95: astore_2
    //   96: aconst_null
    //   97: astore_1
    //   98: aload_1
    //   99: astore #4
    //   101: goto -> 165
    //   104: astore #6
    //   106: aconst_null
    //   107: astore_1
    //   108: aload_1
    //   109: astore #5
    //   111: aload #5
    //   113: astore_2
    //   114: aload_1
    //   115: astore #4
    //   117: aload #6
    //   119: invokevirtual printStackTrace : ()V
    //   122: aload #5
    //   124: ifnull -> 140
    //   127: aload #5
    //   129: invokevirtual close : ()V
    //   132: goto -> 140
    //   135: astore_2
    //   136: aload_2
    //   137: invokevirtual printStackTrace : ()V
    //   140: aload_1
    //   141: ifnull -> 156
    //   144: aload_1
    //   145: invokevirtual close : ()V
    //   148: goto -> 156
    //   151: astore_1
    //   152: aload_1
    //   153: invokevirtual printStackTrace : ()V
    //   156: aconst_null
    //   157: areturn
    //   158: astore #5
    //   160: aload_2
    //   161: astore_1
    //   162: aload #5
    //   164: astore_2
    //   165: aload_1
    //   166: ifnull -> 181
    //   169: aload_1
    //   170: invokevirtual close : ()V
    //   173: goto -> 181
    //   176: astore_1
    //   177: aload_1
    //   178: invokevirtual printStackTrace : ()V
    //   181: aload #4
    //   183: ifnull -> 199
    //   186: aload #4
    //   188: invokevirtual close : ()V
    //   191: goto -> 199
    //   194: astore_1
    //   195: aload_1
    //   196: invokevirtual printStackTrace : ()V
    //   199: aload_2
    //   200: athrow
    //   201: aconst_null
    //   202: areturn
    // Exception table:
    //   from	to	target	type
    //   10	19	104	java/lang/Exception
    //   10	19	95	finally
    //   19	28	83	java/lang/Exception
    //   19	28	76	finally
    //   33	39	66	java/lang/Exception
    //   33	39	158	finally
    //   39	43	46	java/io/IOException
    //   51	55	58	java/io/IOException
    //   117	122	158	finally
    //   127	132	135	java/io/IOException
    //   144	148	151	java/io/IOException
    //   169	173	176	java/io/IOException
    //   186	191	194	java/io/IOException
  }
  
  public String getAsString(String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mCache : Lcom/lody/virtual/helper/utils/ACache$ACacheManager;
    //   4: aload_1
    //   5: invokestatic access$500 : (Lcom/lody/virtual/helper/utils/ACache$ACacheManager;Ljava/lang/String;)Ljava/io/File;
    //   8: astore_2
    //   9: aload_2
    //   10: invokevirtual exists : ()Z
    //   13: istore_3
    //   14: aconst_null
    //   15: astore #4
    //   17: iload_3
    //   18: ifne -> 23
    //   21: aconst_null
    //   22: areturn
    //   23: new java/io/BufferedReader
    //   26: astore #5
    //   28: new java/io/FileReader
    //   31: astore #6
    //   33: aload #6
    //   35: aload_2
    //   36: invokespecial <init> : (Ljava/io/File;)V
    //   39: aload #5
    //   41: aload #6
    //   43: invokespecial <init> : (Ljava/io/Reader;)V
    //   46: ldc ''
    //   48: astore_2
    //   49: aload #5
    //   51: astore #4
    //   53: aload #5
    //   55: invokevirtual readLine : ()Ljava/lang/String;
    //   58: astore #7
    //   60: aload #7
    //   62: ifnull -> 119
    //   65: aload #5
    //   67: astore #4
    //   69: new java/lang/StringBuilder
    //   72: astore #6
    //   74: aload #5
    //   76: astore #4
    //   78: aload #6
    //   80: invokespecial <init> : ()V
    //   83: aload #5
    //   85: astore #4
    //   87: aload #6
    //   89: aload_2
    //   90: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   93: pop
    //   94: aload #5
    //   96: astore #4
    //   98: aload #6
    //   100: aload #7
    //   102: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   105: pop
    //   106: aload #5
    //   108: astore #4
    //   110: aload #6
    //   112: invokevirtual toString : ()Ljava/lang/String;
    //   115: astore_2
    //   116: goto -> 49
    //   119: aload #5
    //   121: astore #4
    //   123: aload_2
    //   124: invokestatic access$600 : (Ljava/lang/String;)Z
    //   127: ifne -> 156
    //   130: aload #5
    //   132: astore #4
    //   134: aload_2
    //   135: invokestatic access$700 : (Ljava/lang/String;)Ljava/lang/String;
    //   138: astore_1
    //   139: aload #5
    //   141: invokevirtual close : ()V
    //   144: goto -> 154
    //   147: astore #4
    //   149: aload #4
    //   151: invokevirtual printStackTrace : ()V
    //   154: aload_1
    //   155: areturn
    //   156: aload #5
    //   158: invokevirtual close : ()V
    //   161: goto -> 171
    //   164: astore #4
    //   166: aload #4
    //   168: invokevirtual printStackTrace : ()V
    //   171: aload_0
    //   172: aload_1
    //   173: invokevirtual remove : (Ljava/lang/String;)Z
    //   176: pop
    //   177: aconst_null
    //   178: areturn
    //   179: astore #4
    //   181: aload #5
    //   183: astore_1
    //   184: aload #4
    //   186: astore #5
    //   188: goto -> 199
    //   191: astore_1
    //   192: goto -> 226
    //   195: astore #5
    //   197: aconst_null
    //   198: astore_1
    //   199: aload_1
    //   200: astore #4
    //   202: aload #5
    //   204: invokevirtual printStackTrace : ()V
    //   207: aload_1
    //   208: ifnull -> 223
    //   211: aload_1
    //   212: invokevirtual close : ()V
    //   215: goto -> 223
    //   218: astore_1
    //   219: aload_1
    //   220: invokevirtual printStackTrace : ()V
    //   223: aconst_null
    //   224: areturn
    //   225: astore_1
    //   226: aload #4
    //   228: ifnull -> 246
    //   231: aload #4
    //   233: invokevirtual close : ()V
    //   236: goto -> 246
    //   239: astore #4
    //   241: aload #4
    //   243: invokevirtual printStackTrace : ()V
    //   246: aload_1
    //   247: athrow
    // Exception table:
    //   from	to	target	type
    //   23	46	195	java/io/IOException
    //   23	46	191	finally
    //   53	60	179	java/io/IOException
    //   53	60	225	finally
    //   69	74	179	java/io/IOException
    //   69	74	225	finally
    //   78	83	179	java/io/IOException
    //   78	83	225	finally
    //   87	94	179	java/io/IOException
    //   87	94	225	finally
    //   98	106	179	java/io/IOException
    //   98	106	225	finally
    //   110	116	179	java/io/IOException
    //   110	116	225	finally
    //   123	130	179	java/io/IOException
    //   123	130	225	finally
    //   134	139	179	java/io/IOException
    //   134	139	225	finally
    //   139	144	147	java/io/IOException
    //   156	161	164	java/io/IOException
    //   202	207	225	finally
    //   211	215	218	java/io/IOException
    //   231	236	239	java/io/IOException
  }
  
  public OutputStream put(String paramString) throws FileNotFoundException {
    return new xFileOutputStream(this.mCache.newFile(paramString));
  }
  
  public void put(String paramString, Bitmap paramBitmap) {
    put(paramString, Utils.Bitmap2Bytes(paramBitmap));
  }
  
  public void put(String paramString, Bitmap paramBitmap, int paramInt) {
    put(paramString, Utils.Bitmap2Bytes(paramBitmap), paramInt);
  }
  
  public void put(String paramString, Drawable paramDrawable) {
    put(paramString, Utils.drawable2Bitmap(paramDrawable));
  }
  
  public void put(String paramString, Drawable paramDrawable, int paramInt) {
    put(paramString, Utils.drawable2Bitmap(paramDrawable), paramInt);
  }
  
  public void put(String paramString, Serializable paramSerializable) {
    put(paramString, paramSerializable, -1);
  }
  
  public void put(String paramString, Serializable paramSerializable, int paramInt) {
    String str1 = null;
    ObjectOutputStream objectOutputStream1 = null;
    ObjectOutputStream objectOutputStream2 = objectOutputStream1;
    try {
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      objectOutputStream2 = objectOutputStream1;
      this();
      objectOutputStream2 = objectOutputStream1;
      ObjectOutputStream objectOutputStream = new ObjectOutputStream();
      objectOutputStream2 = objectOutputStream1;
      this(byteArrayOutputStream);
      try {
        objectOutputStream.writeObject(paramSerializable);
        byte[] arrayOfByte = byteArrayOutputStream.toByteArray();
        if (paramInt != -1) {
          put(paramString, arrayOfByte, paramInt);
        } else {
          put(paramString, arrayOfByte);
        } 
      } catch (Exception null) {
      
      } finally {
        paramString = null;
      } 
    } catch (Exception exception) {
      paramString = str1;
    } finally {}
    String str2 = paramString;
    exception.printStackTrace();
    paramString.close();
  }
  
  public void put(String paramString1, String paramString2) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mCache : Lcom/lody/virtual/helper/utils/ACache$ACacheManager;
    //   4: aload_1
    //   5: invokestatic access$300 : (Lcom/lody/virtual/helper/utils/ACache$ACacheManager;Ljava/lang/String;)Ljava/io/File;
    //   8: astore_3
    //   9: aconst_null
    //   10: astore #4
    //   12: aconst_null
    //   13: astore #5
    //   15: aload #5
    //   17: astore_1
    //   18: new java/io/BufferedWriter
    //   21: astore #6
    //   23: aload #5
    //   25: astore_1
    //   26: new java/io/FileWriter
    //   29: astore #7
    //   31: aload #5
    //   33: astore_1
    //   34: aload #7
    //   36: aload_3
    //   37: invokespecial <init> : (Ljava/io/File;)V
    //   40: aload #5
    //   42: astore_1
    //   43: aload #6
    //   45: aload #7
    //   47: sipush #1024
    //   50: invokespecial <init> : (Ljava/io/Writer;I)V
    //   53: aload #6
    //   55: aload_2
    //   56: invokevirtual write : (Ljava/lang/String;)V
    //   59: aload #6
    //   61: invokevirtual flush : ()V
    //   64: aload #6
    //   66: invokevirtual close : ()V
    //   69: goto -> 135
    //   72: astore_1
    //   73: goto -> 131
    //   76: astore_1
    //   77: aload #6
    //   79: astore_2
    //   80: goto -> 144
    //   83: astore_1
    //   84: aload #6
    //   86: astore_2
    //   87: aload_1
    //   88: astore #6
    //   90: goto -> 108
    //   93: astore #6
    //   95: aload_1
    //   96: astore_2
    //   97: aload #6
    //   99: astore_1
    //   100: goto -> 144
    //   103: astore #6
    //   105: aload #4
    //   107: astore_2
    //   108: aload_2
    //   109: astore_1
    //   110: aload #6
    //   112: invokevirtual printStackTrace : ()V
    //   115: aload_2
    //   116: ifnull -> 135
    //   119: aload_2
    //   120: invokevirtual flush : ()V
    //   123: aload_2
    //   124: invokevirtual close : ()V
    //   127: goto -> 135
    //   130: astore_1
    //   131: aload_1
    //   132: invokevirtual printStackTrace : ()V
    //   135: aload_0
    //   136: getfield mCache : Lcom/lody/virtual/helper/utils/ACache$ACacheManager;
    //   139: aload_3
    //   140: invokestatic access$200 : (Lcom/lody/virtual/helper/utils/ACache$ACacheManager;Ljava/io/File;)V
    //   143: return
    //   144: aload_2
    //   145: ifnull -> 164
    //   148: aload_2
    //   149: invokevirtual flush : ()V
    //   152: aload_2
    //   153: invokevirtual close : ()V
    //   156: goto -> 164
    //   159: astore_2
    //   160: aload_2
    //   161: invokevirtual printStackTrace : ()V
    //   164: aload_0
    //   165: getfield mCache : Lcom/lody/virtual/helper/utils/ACache$ACacheManager;
    //   168: aload_3
    //   169: invokestatic access$200 : (Lcom/lody/virtual/helper/utils/ACache$ACacheManager;Ljava/io/File;)V
    //   172: aload_1
    //   173: athrow
    // Exception table:
    //   from	to	target	type
    //   18	23	103	java/io/IOException
    //   18	23	93	finally
    //   26	31	103	java/io/IOException
    //   26	31	93	finally
    //   34	40	103	java/io/IOException
    //   34	40	93	finally
    //   43	53	103	java/io/IOException
    //   43	53	93	finally
    //   53	59	83	java/io/IOException
    //   53	59	76	finally
    //   59	69	72	java/io/IOException
    //   110	115	93	finally
    //   119	127	130	java/io/IOException
    //   148	156	159	java/io/IOException
  }
  
  public void put(String paramString1, String paramString2, int paramInt) {
    put(paramString1, Utils.newStringWithDateInfo(paramInt, paramString2));
  }
  
  public void put(String paramString, JSONArray paramJSONArray) {
    put(paramString, paramJSONArray.toString());
  }
  
  public void put(String paramString, JSONArray paramJSONArray, int paramInt) {
    put(paramString, paramJSONArray.toString(), paramInt);
  }
  
  public void put(String paramString, JSONObject paramJSONObject) {
    put(paramString, paramJSONObject.toString());
  }
  
  public void put(String paramString, JSONObject paramJSONObject, int paramInt) {
    put(paramString, paramJSONObject.toString(), paramInt);
  }
  
  public void put(String paramString, byte[] paramArrayOfbyte) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mCache : Lcom/lody/virtual/helper/utils/ACache$ACacheManager;
    //   4: aload_1
    //   5: invokestatic access$300 : (Lcom/lody/virtual/helper/utils/ACache$ACacheManager;Ljava/lang/String;)Ljava/io/File;
    //   8: astore_3
    //   9: aconst_null
    //   10: astore #4
    //   12: aconst_null
    //   13: astore #5
    //   15: aload #5
    //   17: astore_1
    //   18: new java/io/FileOutputStream
    //   21: astore #6
    //   23: aload #5
    //   25: astore_1
    //   26: aload #6
    //   28: aload_3
    //   29: invokespecial <init> : (Ljava/io/File;)V
    //   32: aload #6
    //   34: aload_2
    //   35: invokevirtual write : ([B)V
    //   38: aload #6
    //   40: invokevirtual flush : ()V
    //   43: aload #6
    //   45: invokevirtual close : ()V
    //   48: goto -> 108
    //   51: astore_1
    //   52: goto -> 104
    //   55: astore_2
    //   56: aload #6
    //   58: astore_1
    //   59: goto -> 117
    //   62: astore_1
    //   63: aload #6
    //   65: astore_2
    //   66: aload_1
    //   67: astore #6
    //   69: goto -> 81
    //   72: astore_2
    //   73: goto -> 117
    //   76: astore #6
    //   78: aload #4
    //   80: astore_2
    //   81: aload_2
    //   82: astore_1
    //   83: aload #6
    //   85: invokevirtual printStackTrace : ()V
    //   88: aload_2
    //   89: ifnull -> 108
    //   92: aload_2
    //   93: invokevirtual flush : ()V
    //   96: aload_2
    //   97: invokevirtual close : ()V
    //   100: goto -> 108
    //   103: astore_1
    //   104: aload_1
    //   105: invokevirtual printStackTrace : ()V
    //   108: aload_0
    //   109: getfield mCache : Lcom/lody/virtual/helper/utils/ACache$ACacheManager;
    //   112: aload_3
    //   113: invokestatic access$200 : (Lcom/lody/virtual/helper/utils/ACache$ACacheManager;Ljava/io/File;)V
    //   116: return
    //   117: aload_1
    //   118: ifnull -> 137
    //   121: aload_1
    //   122: invokevirtual flush : ()V
    //   125: aload_1
    //   126: invokevirtual close : ()V
    //   129: goto -> 137
    //   132: astore_1
    //   133: aload_1
    //   134: invokevirtual printStackTrace : ()V
    //   137: aload_0
    //   138: getfield mCache : Lcom/lody/virtual/helper/utils/ACache$ACacheManager;
    //   141: aload_3
    //   142: invokestatic access$200 : (Lcom/lody/virtual/helper/utils/ACache$ACacheManager;Ljava/io/File;)V
    //   145: aload_2
    //   146: athrow
    // Exception table:
    //   from	to	target	type
    //   18	23	76	java/lang/Exception
    //   18	23	72	finally
    //   26	32	76	java/lang/Exception
    //   26	32	72	finally
    //   32	38	62	java/lang/Exception
    //   32	38	55	finally
    //   38	48	51	java/io/IOException
    //   83	88	72	finally
    //   92	100	103	java/io/IOException
    //   121	129	132	java/io/IOException
  }
  
  public void put(String paramString, byte[] paramArrayOfbyte, int paramInt) {
    put(paramString, Utils.newByteArrayWithDateInfo(paramInt, paramArrayOfbyte));
  }
  
  public boolean remove(String paramString) {
    return this.mCache.remove(paramString);
  }
  
  public class ACacheManager {
    private final AtomicInteger cacheCount;
    
    protected File cacheDir;
    
    private final AtomicLong cacheSize;
    
    private final int countLimit;
    
    private final Map<File, Long> lastUsageDates = Collections.synchronizedMap(new HashMap<File, Long>());
    
    private final long sizeLimit;
    
    private ACacheManager(File param1File, long param1Long, int param1Int) {
      this.cacheDir = param1File;
      this.sizeLimit = param1Long;
      this.countLimit = param1Int;
      this.cacheSize = new AtomicLong();
      this.cacheCount = new AtomicInteger();
      calculateCacheSizeAndCacheCount();
    }
    
    private void calculateCacheSizeAndCacheCount() {
      (new Thread(new Runnable() {
            public void run() {
              File[] arrayOfFile = ACache.ACacheManager.this.cacheDir.listFiles();
              if (arrayOfFile != null) {
                int i = arrayOfFile.length;
                byte b1 = 0;
                int j = 0;
                byte b2 = 0;
                while (b1 < i) {
                  File file = arrayOfFile[b1];
                  j = (int)(j + ACache.ACacheManager.this.calculateSize(file));
                  b2++;
                  ACache.ACacheManager.this.lastUsageDates.put(file, Long.valueOf(file.lastModified()));
                  b1++;
                } 
                ACache.ACacheManager.this.cacheSize.set(j);
                ACache.ACacheManager.this.cacheCount.set(b2);
              } 
            }
          })).start();
    }
    
    private long calculateSize(File param1File) {
      return param1File.length();
    }
    
    private void clear() {
      this.lastUsageDates.clear();
      this.cacheSize.set(0L);
      File[] arrayOfFile = this.cacheDir.listFiles();
      if (arrayOfFile != null) {
        int i = arrayOfFile.length;
        for (byte b = 0; b < i; b++)
          arrayOfFile[b].delete(); 
      } 
    }
    
    private File get(String param1String) {
      File file = newFile(param1String);
      Long long_ = Long.valueOf(System.currentTimeMillis());
      file.setLastModified(long_.longValue());
      this.lastUsageDates.put(file, long_);
      return file;
    }
    
    private File newFile(String param1String) {
      File file = this.cacheDir;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(param1String.hashCode());
      stringBuilder.append("");
      return new File(file, stringBuilder.toString());
    }
    
    private void put(File param1File) {
      for (int i = this.cacheCount.get(); i + 1 > this.countLimit; i = this.cacheCount.addAndGet(-1)) {
        long l = removeNext();
        this.cacheSize.addAndGet(-l);
      } 
      this.cacheCount.addAndGet(1);
      long l2 = calculateSize(param1File);
      for (long l1 = this.cacheSize.get(); l1 + l2 > this.sizeLimit; l1 = this.cacheSize.addAndGet(-l1))
        l1 = removeNext(); 
      this.cacheSize.addAndGet(l2);
      Long long_ = Long.valueOf(System.currentTimeMillis());
      param1File.setLastModified(long_.longValue());
      this.lastUsageDates.put(param1File, long_);
    }
    
    private boolean remove(String param1String) {
      return get(param1String).delete();
    }
    
    private long removeNext() {
      if (this.lastUsageDates.isEmpty())
        return 0L; 
      null = this.lastUsageDates.entrySet();
      synchronized (this.lastUsageDates) {
        File file;
        Iterator<Map.Entry<File, Long>> iterator = null.iterator();
        null = null;
        Long long_ = null;
        while (iterator.hasNext()) {
          Map.Entry entry = iterator.next();
          if (null == null) {
            file = (File)entry.getKey();
            long_ = (Long)entry.getValue();
            continue;
          } 
          Long long_1 = (Long)entry.getValue();
          if (long_1.longValue() < long_.longValue()) {
            file = (File)entry.getKey();
            long_ = long_1;
          } 
        } 
        long l = calculateSize(file);
        if (file.delete())
          this.lastUsageDates.remove(file); 
        return l;
      } 
    }
  }
  
  class null implements Runnable {
    public void run() {
      File[] arrayOfFile = this.this$1.cacheDir.listFiles();
      if (arrayOfFile != null) {
        int i = arrayOfFile.length;
        byte b1 = 0;
        int j = 0;
        byte b2 = 0;
        while (b1 < i) {
          File file = arrayOfFile[b1];
          j = (int)(j + this.this$1.calculateSize(file));
          b2++;
          this.this$1.lastUsageDates.put(file, Long.valueOf(file.lastModified()));
          b1++;
        } 
        this.this$1.cacheSize.set(j);
        this.this$1.cacheCount.set(b2);
      } 
    }
  }
  
  private static class Utils {
    private static final char mSeparator = ' ';
    
    private static byte[] Bitmap2Bytes(Bitmap param1Bitmap) {
      if (param1Bitmap == null)
        return null; 
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      param1Bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
      return byteArrayOutputStream.toByteArray();
    }
    
    private static Bitmap Bytes2Bimap(byte[] param1ArrayOfbyte) {
      return (param1ArrayOfbyte.length == 0) ? null : BitmapFactory.decodeByteArray(param1ArrayOfbyte, 0, param1ArrayOfbyte.length);
    }
    
    private static Drawable bitmap2Drawable(Bitmap param1Bitmap) {
      if (param1Bitmap == null)
        return null; 
      (new BitmapDrawable(param1Bitmap)).setTargetDensity(param1Bitmap.getDensity());
      return (Drawable)new BitmapDrawable(param1Bitmap);
    }
    
    private static String clearDateInfo(String param1String) {
      String str = param1String;
      if (param1String != null) {
        str = param1String;
        if (hasDateInfo(param1String.getBytes()))
          str = param1String.substring(param1String.indexOf(' ') + 1, param1String.length()); 
      } 
      return str;
    }
    
    private static byte[] clearDateInfo(byte[] param1ArrayOfbyte) {
      byte[] arrayOfByte = param1ArrayOfbyte;
      if (hasDateInfo(param1ArrayOfbyte))
        arrayOfByte = copyOfRange(param1ArrayOfbyte, indexOf(param1ArrayOfbyte, ' ') + 1, param1ArrayOfbyte.length); 
      return arrayOfByte;
    }
    
    private static byte[] copyOfRange(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) {
      int i = param1Int2 - param1Int1;
      if (i >= 0) {
        byte[] arrayOfByte = new byte[i];
        System.arraycopy(param1ArrayOfbyte, param1Int1, arrayOfByte, 0, Math.min(param1ArrayOfbyte.length - param1Int1, i));
        return arrayOfByte;
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(param1Int1);
      stringBuilder.append(" > ");
      stringBuilder.append(param1Int2);
      throw new IllegalArgumentException(stringBuilder.toString());
    }
    
    private static String createDateInfo(int param1Int) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(System.currentTimeMillis());
      stringBuilder1.append("");
      String str;
      for (str = stringBuilder1.toString(); str.length() < 13; str = stringBuilder.toString()) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("0");
        stringBuilder.append(str);
      } 
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append(str);
      stringBuilder2.append("-");
      stringBuilder2.append(param1Int);
      stringBuilder2.append(' ');
      return stringBuilder2.toString();
    }
    
    private static Bitmap drawable2Bitmap(Drawable param1Drawable) {
      Bitmap.Config config;
      if (param1Drawable == null)
        return null; 
      int i = param1Drawable.getIntrinsicWidth();
      int j = param1Drawable.getIntrinsicHeight();
      if (param1Drawable.getOpacity() != -1) {
        config = Bitmap.Config.ARGB_8888;
      } else {
        config = Bitmap.Config.RGB_565;
      } 
      Bitmap bitmap = Bitmap.createBitmap(i, j, config);
      Canvas canvas = new Canvas(bitmap);
      param1Drawable.setBounds(0, 0, i, j);
      param1Drawable.draw(canvas);
      return bitmap;
    }
    
    private static String[] getDateInfoFromDate(byte[] param1ArrayOfbyte) {
      return hasDateInfo(param1ArrayOfbyte) ? new String[] { new String(copyOfRange(param1ArrayOfbyte, 0, 13)), new String(copyOfRange(param1ArrayOfbyte, 14, indexOf(param1ArrayOfbyte, ' '))) } : null;
    }
    
    private static boolean hasDateInfo(byte[] param1ArrayOfbyte) {
      boolean bool;
      if (param1ArrayOfbyte != null && param1ArrayOfbyte.length > 15 && param1ArrayOfbyte[13] == 45 && indexOf(param1ArrayOfbyte, ' ') > 14) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    private static int indexOf(byte[] param1ArrayOfbyte, char param1Char) {
      for (byte b = 0; b < param1ArrayOfbyte.length; b++) {
        if (param1ArrayOfbyte[b] == param1Char)
          return b; 
      } 
      return -1;
    }
    
    private static boolean isDue(String param1String) {
      return isDue(param1String.getBytes());
    }
    
    private static boolean isDue(byte[] param1ArrayOfbyte) {
      String[] arrayOfString = getDateInfoFromDate(param1ArrayOfbyte);
      if (arrayOfString != null && arrayOfString.length == 2) {
        String str;
        for (str = arrayOfString[0]; str.startsWith("0"); str = str.substring(1, str.length()));
        long l1 = Long.valueOf(str).longValue();
        long l2 = Long.valueOf(arrayOfString[1]).longValue();
        if (System.currentTimeMillis() > l1 + l2 * 1000L)
          return true; 
      } 
      return false;
    }
    
    private static byte[] newByteArrayWithDateInfo(int param1Int, byte[] param1ArrayOfbyte) {
      byte[] arrayOfByte1 = createDateInfo(param1Int).getBytes();
      byte[] arrayOfByte2 = new byte[arrayOfByte1.length + param1ArrayOfbyte.length];
      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, arrayOfByte1.length);
      System.arraycopy(param1ArrayOfbyte, 0, arrayOfByte2, arrayOfByte1.length, param1ArrayOfbyte.length);
      return arrayOfByte2;
    }
    
    private static String newStringWithDateInfo(int param1Int, String param1String) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(createDateInfo(param1Int));
      stringBuilder.append(param1String);
      return stringBuilder.toString();
    }
  }
  
  class xFileOutputStream extends FileOutputStream {
    File file;
    
    public xFileOutputStream(File param1File) throws FileNotFoundException {
      super(param1File);
      this.file = param1File;
    }
    
    public void close() throws IOException {
      super.close();
      ACache.this.mCache.put(this.file);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helpe\\utils\ACache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */