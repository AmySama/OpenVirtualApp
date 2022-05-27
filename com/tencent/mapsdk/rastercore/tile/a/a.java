package com.tencent.mapsdk.rastercore.tile.a;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;
import com.tencent.mapsdk.rastercore.d;
import com.tencent.mapsdk.rastercore.d.e;
import com.tencent.mapsdk.rastercore.tile.MapTile;
import java.io.File;
import java.security.MessageDigest;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class a {
  private static final String g;
  
  private AtomicBoolean a = new AtomicBoolean(false);
  
  private volatile Context b;
  
  private AtomicInteger c = new AtomicInteger(0);
  
  private b d;
  
  private c e;
  
  private ReentrantReadWriteLock f = new ReentrantReadWriteLock();
  
  static {
    StringBuilder stringBuilder = new StringBuilder("Qmap");
    stringBuilder.append(File.separator);
    g = stringBuilder.toString();
  }
  
  public static a a() {
    return a.a;
  }
  
  public static String a(byte[] paramArrayOfbyte) {
    String str1 = "md5";
    String str2 = str1;
    if (paramArrayOfbyte != null)
      if (paramArrayOfbyte.length == 0) {
        str2 = str1;
      } else {
        try {
          MessageDigest messageDigest = MessageDigest.getInstance("MD5");
          messageDigest.update(paramArrayOfbyte);
          byte[] arrayOfByte = messageDigest.digest();
          StringBuilder stringBuilder = new StringBuilder();
          this();
          for (byte b1 = 0; b1 < arrayOfByte.length; b1++) {
            String str4 = Integer.toHexString(arrayOfByte[b1] & 0xFF);
            String str3 = str4;
            if (str4.length() == 1) {
              StringBuilder stringBuilder1 = new StringBuilder();
              this("0");
              stringBuilder1.append(str4);
              str3 = stringBuilder1.toString();
            } 
            stringBuilder.append(str3);
          } 
          String str = stringBuilder.toString();
        } catch (Exception exception) {
          (new StringBuilder("CacheManager getMd5 failed:")).append(exception.toString());
          str2 = str1;
        } 
      }  
    return str2;
  }
  
  private static byte[] c(byte[] paramArrayOfbyte, String paramString) {
    try {
      byte[] arrayOfByte1 = paramString.getBytes("UTF-8");
      int i = arrayOfByte1.length + paramArrayOfbyte.length;
      byte[] arrayOfByte2 = new byte[i];
      int j;
      for (j = 0; j < arrayOfByte1.length; j++)
        arrayOfByte2[j] = (byte)arrayOfByte1[j]; 
      for (j = arrayOfByte1.length; j < i; j++)
        arrayOfByte2[j] = (byte)paramArrayOfbyte[j - arrayOfByte1.length]; 
      return arrayOfByte2;
    } catch (Exception exception) {
      (new StringBuilder("CacheManager encode:")).append(exception.toString());
      return paramArrayOfbyte;
    } 
  }
  
  private static byte[] d(byte[] paramArrayOfbyte, String paramString) {
    try {
      int i = (paramString.getBytes("UTF-8")).length;
      byte[] arrayOfByte = new byte[paramArrayOfbyte.length - i];
      for (int j = i; j < paramArrayOfbyte.length; j++)
        arrayOfByte[j - i] = (byte)paramArrayOfbyte[j]; 
      return arrayOfByte;
    } catch (Exception exception) {
      return paramArrayOfbyte;
    } 
  }
  
  public final c a(com.tencent.mapsdk.rastercore.tile.a parama) {
    c c1 = new c(null, e.v(), "");
    if (parama.m() == MapTile.MapSource.CUSTOMER || parama.m() == MapTile.MapSource.TRAFFIC)
      return c1; 
    this.f.readLock().lock();
    try {
      return this.e.a(parama);
    } finally {
      Exception exception = null;
    } 
  }
  
  public final void a(Context paramContext) {
    this.c.incrementAndGet();
    if (this.a.compareAndSet(false, true)) {
      this.b = paramContext;
      this.d = new b(this, this.b);
      this.e = new c(this, (Context)this.d);
    } 
  }
  
  public final boolean a(MapTile.MapSource paramMapSource) {
    this.f.writeLock().lock();
    try {
      return this.e.a(paramMapSource);
    } finally {
      paramMapSource = null;
    } 
  }
  
  public final boolean a(MapTile.MapSource paramMapSource, int paramInt) {
    this.f.writeLock().lock();
    try {
      return this.e.a(paramMapSource, paramInt);
    } finally {
      paramMapSource = null;
    } 
  }
  
  public final boolean a(c paramc, com.tencent.mapsdk.rastercore.tile.a parama) {
    if (parama.m() == MapTile.MapSource.CUSTOMER || parama.m() == MapTile.MapSource.TRAFFIC)
      return false; 
    MapTile.MapSource mapSource = parama.m();
    int i = null.a[mapSource.ordinal()];
    if (i != 1) {
      if (i != 2) {
        if (i != 3) {
          i = -1;
        } else {
          i = e.w();
        } 
      } else {
        i = e.v();
      } 
    } else {
      i = e.s();
    } 
    this.f.writeLock().lock();
    StringBuilder stringBuilder = new StringBuilder("CacheManager Put currentVersion:");
    stringBuilder.append(i);
    stringBuilder.append(",tileData.getVersion():");
    stringBuilder.append(parama.l());
    try {
      if (!parama.g() || parama.l() != i || this.e == null)
        return false; 
      stringBuilder = new StringBuilder();
      this("Put: tileData.getVersion()=");
      stringBuilder.append(parama.l());
      stringBuilder.append(",currentVersion=");
      stringBuilder.append(i);
      if (!c.a(this.e, parama))
        return this.e.a(parama, paramc.c()); 
      return this.e.a(parama, paramc.c(), false);
    } finally {
      parama = null;
    } 
  }
  
  public final boolean a(com.tencent.mapsdk.rastercore.tile.a parama, byte[] paramArrayOfbyte, boolean paramBoolean) {
    if (parama.m() == MapTile.MapSource.CUSTOMER || parama.m() == MapTile.MapSource.TRAFFIC)
      return false; 
    this.f.writeLock().lock();
    try {
      paramBoolean = this.e.a(parama, (byte[])null, true);
      return paramBoolean;
    } finally {
      paramArrayOfbyte = null;
    } 
  }
  
  public final String b() {
    boolean bool = Environment.getExternalStorageState().equals("mounted");
    if (this.b == null)
      return null; 
    int i = Build.VERSION.SDK_INT;
    byte b1 = 1;
    int j = b1;
    if (i >= 23) {
      i = this.b.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
      j = this.b.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE");
      if (i == 0 && j == 0) {
        j = b1;
      } else {
        j = 0;
      } 
    } 
    if (bool && j != 0)
      try {
        StringBuilder stringBuilder1 = new StringBuilder();
        this();
        stringBuilder1.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        stringBuilder1.append(File.separator);
        stringBuilder1.append(g);
        String str1 = stringBuilder1.toString();
        d.a.c(str1);
        return str1;
      } catch (Exception exception) {
        exception.printStackTrace();
        System.out.println(exception.toString());
        return null;
      }  
    StringBuilder stringBuilder = new StringBuilder();
    this();
    stringBuilder.append(this.b.getFileStreamPath(""));
    stringBuilder.append(File.separator);
    stringBuilder.append(g);
    String str = stringBuilder.toString();
    d.a.c(str);
    return str;
  }
  
  public final boolean c() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(b());
    stringBuilder.append("RealTimeMap");
    return d.a.b(stringBuilder.toString());
  }
  
  public final void d() {
    if (this.c.decrementAndGet() == 0) {
      c c1 = this.e;
      if (c1 != null)
        c1.close(); 
      this.a.compareAndSet(true, false);
    } 
  }
  
  static final class a {
    public static final a a = new a();
  }
  
  final class b extends ContextWrapper {
    public b(a this$0, Context param1Context) {
      super(param1Context);
    }
    
    public final SQLiteDatabase openOrCreateDatabase(String param1String, int param1Int, SQLiteDatabase.CursorFactory param1CursorFactory) {
      String str = this.a.b();
      if (str == null || str.length() == 0)
        return super.openOrCreateDatabase(param1String, param1Int, param1CursorFactory); 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str);
      stringBuilder.append(param1String);
      return SQLiteDatabase.openOrCreateDatabase(stringBuilder.toString(), null);
    }
    
    public final SQLiteDatabase openOrCreateDatabase(String param1String, int param1Int, SQLiteDatabase.CursorFactory param1CursorFactory, DatabaseErrorHandler param1DatabaseErrorHandler) {
      String str = this.a.b();
      if (str == null || str.length() == 0)
        return super.openOrCreateDatabase(param1String, param1Int, param1CursorFactory, param1DatabaseErrorHandler); 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str);
      stringBuilder.append(param1String);
      return SQLiteDatabase.openOrCreateDatabase(stringBuilder.toString(), null);
    }
  }
  
  final class c extends SQLiteOpenHelper {
    private SQLiteDatabase a = null;
    
    public c(a this$0, Context param1Context) {
      super(param1Context, "Cache.db", null, 1);
      this.a = getReadableDatabase();
      if (Build.VERSION.SDK_INT > 10) {
        this.a.enableWriteAheadLogging();
        return;
      } 
      this.a.setLockingEnabled(true);
    }
    
    private static void a(String param1String, SQLiteDatabase param1SQLiteDatabase) {
      StringBuilder stringBuilder = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
      stringBuilder.append(param1String);
      stringBuilder.append(" (tile");
      stringBuilder.append(" TEXT PRIMARY KEY UNIQUE,version");
      stringBuilder.append(" TEXT,style");
      stringBuilder.append(" TEXT,md5");
      stringBuilder.append(" TEXT,data");
      stringBuilder.append(" BLOB )");
      param1SQLiteDatabase.execSQL(stringBuilder.toString());
    }
    
    private static String b(MapTile.MapSource param1MapSource) {
      int i = a.null.a[param1MapSource.ordinal()];
      return (i != 1) ? ((i != 2) ? ((i != 3) ? "" : "stt") : "tct") : "wmt";
    }
    
    private static String b(com.tencent.mapsdk.rastercore.tile.a param1a) {
      int i = a.null.a[param1a.m().ordinal()];
      if (i != 1) {
        if (i != 2) {
          if (i != 3)
            return null; 
          StringBuilder stringBuilder3 = new StringBuilder();
          stringBuilder3.append(param1a.d());
          stringBuilder3.append("-");
          stringBuilder3.append(param1a.b());
          stringBuilder3.append("-");
          i = param1a.c();
          stringBuilder1 = stringBuilder3;
          stringBuilder1.append(i);
          return stringBuilder1.toString();
        } 
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(stringBuilder1.d());
        stringBuilder.append("-");
        stringBuilder.append(stringBuilder1.b());
        stringBuilder.append("-");
        stringBuilder.append(stringBuilder1.c());
        stringBuilder.append("-zh-");
        i = e.y();
        stringBuilder1 = stringBuilder;
        stringBuilder1.append(i);
        return stringBuilder1.toString();
      } 
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append(stringBuilder1.d());
      stringBuilder2.append("-");
      stringBuilder2.append(stringBuilder1.b());
      stringBuilder2.append("-");
      stringBuilder2.append(stringBuilder1.c());
      stringBuilder2.append("-");
      stringBuilder2.append(e.u());
      stringBuilder2.append("-");
      i = e.t();
      StringBuilder stringBuilder1 = stringBuilder2;
      stringBuilder1.append(i);
      return stringBuilder1.toString();
    }
    
    private static int c(MapTile.MapSource param1MapSource) {
      return (param1MapSource == MapTile.MapSource.BING) ? e.t() : ((param1MapSource == MapTile.MapSource.TENCENT) ? e.y() : -1);
    }
    
    private boolean c(com.tencent.mapsdk.rastercore.tile.a param1a) {
      Cursor cursor;
      String str = b(param1a);
      StringBuilder stringBuilder = new StringBuilder("select count(*) as ct from ");
      stringBuilder.append(b(param1a.m()));
      stringBuilder.append(" where ");
      stringBuilder.append("tile");
      stringBuilder.append(" = '");
      stringBuilder.append(str);
      stringBuilder.append("'");
      str = stringBuilder.toString();
      boolean bool = true;
      param1a = null;
      try {
        Cursor cursor1 = this.a.rawQuery(str, null);
        if (cursor1 != null) {
          cursor = cursor1;
          if (cursor1.getCount() > 0) {
            cursor = cursor1;
            cursor1.moveToFirst();
            cursor = cursor1;
            int i = cursor1.getInt(cursor1.getColumnIndex("ct"));
            if (i <= 0)
              bool = false; 
            return bool;
          } 
        } 
        if (cursor1 != null) {
          cursor = cursor1;
        } else {
          return true;
        } 
      } finally {
        str = null;
      } 
      return true;
    }
    
    public final c a(com.tencent.mapsdk.rastercore.tile.a param1a) {
      if (this.a == null)
        return null; 
      StringBuilder stringBuilder = new StringBuilder("select * from ");
      stringBuilder.append(b(param1a.m()));
      stringBuilder.append(" where tile");
      stringBuilder.append(" = ?");
      String str = stringBuilder.toString();
      try {
        Cursor cursor = this.a.rawQuery(str, new String[] { b(param1a) });
      } finally {
        str = null;
        param1a = null;
      } 
      return new c(null, e.v(), "");
    }
    
    public final boolean a(MapTile.MapSource param1MapSource) {
      // Byte code:
      //   0: aload_0
      //   1: getfield a : Landroid/database/sqlite/SQLiteDatabase;
      //   4: ifnonnull -> 9
      //   7: iconst_0
      //   8: ireturn
      //   9: new java/lang/StringBuilder
      //   12: dup
      //   13: ldc 'delete  from '
      //   15: invokespecial <init> : (Ljava/lang/String;)V
      //   18: astore_2
      //   19: aload_2
      //   20: aload_1
      //   21: invokestatic b : (Lcom/tencent/mapsdk/rastercore/tile/MapTile$MapSource;)Ljava/lang/String;
      //   24: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   27: pop
      //   28: aload_2
      //   29: invokevirtual toString : ()Ljava/lang/String;
      //   32: astore_3
      //   33: aload_1
      //   34: getstatic com/tencent/mapsdk/rastercore/tile/MapTile$MapSource.BING : Lcom/tencent/mapsdk/rastercore/tile/MapTile$MapSource;
      //   37: if_acmpeq -> 49
      //   40: aload_3
      //   41: astore_2
      //   42: aload_1
      //   43: getstatic com/tencent/mapsdk/rastercore/tile/MapTile$MapSource.TENCENT : Lcom/tencent/mapsdk/rastercore/tile/MapTile$MapSource;
      //   46: if_acmpne -> 91
      //   49: new java/lang/StringBuilder
      //   52: dup
      //   53: invokespecial <init> : ()V
      //   56: astore_2
      //   57: aload_2
      //   58: aload_3
      //   59: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   62: pop
      //   63: aload_2
      //   64: ldc ' where style = ''
      //   66: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   69: pop
      //   70: aload_2
      //   71: aload_1
      //   72: invokestatic c : (Lcom/tencent/mapsdk/rastercore/tile/MapTile$MapSource;)I
      //   75: invokevirtual append : (I)Ljava/lang/StringBuilder;
      //   78: pop
      //   79: aload_2
      //   80: ldc '''
      //   82: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   85: pop
      //   86: aload_2
      //   87: invokevirtual toString : ()Ljava/lang/String;
      //   90: astore_2
      //   91: aload_0
      //   92: getfield a : Landroid/database/sqlite/SQLiteDatabase;
      //   95: aload_2
      //   96: invokevirtual execSQL : (Ljava/lang/String;)V
      //   99: iconst_1
      //   100: ireturn
      //   101: astore_1
      //   102: new java/lang/StringBuilder
      //   105: dup
      //   106: ldc 'cleanCache Error:'
      //   108: invokespecial <init> : (Ljava/lang/String;)V
      //   111: aload_1
      //   112: invokevirtual toString : ()Ljava/lang/String;
      //   115: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   118: pop
      //   119: iconst_0
      //   120: ireturn
      // Exception table:
      //   from	to	target	type
      //   91	99	101	finally
    }
    
    public final boolean a(MapTile.MapSource param1MapSource, int param1Int) {
      try {
        String str;
        StringBuilder stringBuilder = new StringBuilder();
        this("delete from ");
        stringBuilder.append(b(param1MapSource));
        MapTile.MapSource mapSource = MapTile.MapSource.SATELLITE;
        if (param1MapSource == mapSource) {
          StringBuilder stringBuilder1 = new StringBuilder();
          this(" where version != '");
          stringBuilder1.append(param1Int);
          stringBuilder1.append("'");
          str = stringBuilder1.toString();
        } else {
          StringBuilder stringBuilder1 = new StringBuilder();
          this(" where style = '");
          stringBuilder1.append(c((MapTile.MapSource)str));
          stringBuilder1.append("' and version");
          stringBuilder1.append(" != '");
          stringBuilder1.append(param1Int);
          stringBuilder1.append("'");
          str = stringBuilder1.toString();
        } 
        return true;
      } finally {
        param1MapSource = null;
        (new StringBuilder("deleteOlderCache error:")).append(param1MapSource.toString());
      } 
    }
    
    public final boolean a(com.tencent.mapsdk.rastercore.tile.a param1a, byte[] param1ArrayOfbyte) {
      if (this.a == null)
        return false; 
      try {
        String str = a.a(param1ArrayOfbyte);
        byte[] arrayOfByte = a.b(param1ArrayOfbyte, str);
        ContentValues contentValues = new ContentValues();
        this();
        contentValues.put("tile", b(param1a));
        contentValues.put("data", arrayOfByte);
        contentValues.put("md5", str);
        MapTile.MapSource mapSource1 = param1a.m();
        MapTile.MapSource mapSource2 = MapTile.MapSource.BING;
        if (mapSource1 == mapSource2)
          contentValues.put("style", Integer.valueOf(e.t())); 
        if (mapSource1 == MapTile.MapSource.TENCENT)
          contentValues.put("style", Integer.valueOf(e.y())); 
        return (l != -1L);
      } finally {
        param1a = null;
        (new StringBuilder("putBitmap Error:")).append(param1a.toString());
      } 
    }
    
    public final boolean a(com.tencent.mapsdk.rastercore.tile.a param1a, byte[] param1ArrayOfbyte, boolean param1Boolean) {
      if (this.a == null)
        return false; 
      try {
        ContentValues contentValues = new ContentValues();
        this();
        String str1 = b(param1a);
        contentValues.put("version", Integer.valueOf(param1a.l()));
        String str2 = a.a(param1ArrayOfbyte);
        return (i > 0);
      } finally {
        param1a = null;
        (new StringBuilder("updateBitmap Error:")).append(param1a.toString());
      } 
    }
    
    public final void onCreate(SQLiteDatabase param1SQLiteDatabase) {
      a("tct", param1SQLiteDatabase);
      a("wmt", param1SQLiteDatabase);
      a("stt", param1SQLiteDatabase);
    }
    
    public final void onUpgrade(SQLiteDatabase param1SQLiteDatabase, int param1Int1, int param1Int2) {
      param1SQLiteDatabase.execSQL("DROP TABLE IF EXISTS  tct");
      param1SQLiteDatabase.execSQL("DROP TABLE IF EXISTS  wmt");
      param1SQLiteDatabase.execSQL("DROP TABLE IF EXISTS  stt");
      onCreate(param1SQLiteDatabase);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\tile\a\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */