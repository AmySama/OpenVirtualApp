package de.robv.android.xposed;

import android.content.SharedPreferences;
import android.os.Environment;
import java.io.File;
import java.util.Map;
import java.util.Set;

public final class XSharedPreferences implements SharedPreferences {
  private static final String TAG = "XSharedPreferences";
  
  private final File mFile;
  
  private long mFileSize;
  
  private final String mFilename;
  
  private long mLastModified;
  
  private boolean mLoaded = false;
  
  private Map<String, Object> mMap;
  
  public XSharedPreferences(File paramFile) {
    this.mFile = paramFile;
    this.mFilename = paramFile.getAbsolutePath();
    startLoadFromDisk();
  }
  
  public XSharedPreferences(String paramString) {
    this(paramString, stringBuilder.toString());
  }
  
  public XSharedPreferences(String paramString1, String paramString2) {
    File file2 = Environment.getDataDirectory();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("data/");
    stringBuilder.append(paramString1);
    stringBuilder.append("/shared_prefs/");
    stringBuilder.append(paramString2);
    stringBuilder.append(".xml");
    File file1 = new File(file2, stringBuilder.toString());
    this.mFile = file1;
    this.mFilename = file1.getAbsolutePath();
    startLoadFromDisk();
  }
  
  private void awaitLoadedLocked() {
    while (!this.mLoaded) {
      try {
        wait();
      } catch (InterruptedException interruptedException) {}
    } 
  }
  
  private void loadFromDiskLocked() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mLoaded : Z
    //   4: ifeq -> 8
    //   7: return
    //   8: aconst_null
    //   9: astore_1
    //   10: aconst_null
    //   11: astore_2
    //   12: aconst_null
    //   13: astore_3
    //   14: aconst_null
    //   15: astore #4
    //   17: aconst_null
    //   18: astore #5
    //   20: aconst_null
    //   21: astore #6
    //   23: aconst_null
    //   24: astore #7
    //   26: invokestatic getAppDataFileService : ()Lde/robv/android/xposed/services/BaseService;
    //   29: aload_0
    //   30: getfield mFilename : Ljava/lang/String;
    //   33: aload_0
    //   34: getfield mFileSize : J
    //   37: aload_0
    //   38: getfield mLastModified : J
    //   41: invokevirtual getFileInputStream : (Ljava/lang/String;JJ)Lde/robv/android/xposed/services/FileResult;
    //   44: astore #8
    //   46: aload #7
    //   48: astore #9
    //   50: aload_1
    //   51: astore #4
    //   53: aload_2
    //   54: astore #5
    //   56: aload #8
    //   58: getfield stream : Ljava/io/InputStream;
    //   61: ifnull -> 106
    //   64: aload #7
    //   66: astore #9
    //   68: aload_1
    //   69: astore #4
    //   71: aload_2
    //   72: astore #5
    //   74: aload #8
    //   76: getfield stream : Ljava/io/InputStream;
    //   79: invokestatic readMapXml : (Ljava/io/InputStream;)Ljava/util/HashMap;
    //   82: astore_3
    //   83: aload_3
    //   84: astore #9
    //   86: aload_3
    //   87: astore #4
    //   89: aload_3
    //   90: astore #5
    //   92: aload #8
    //   94: getfield stream : Ljava/io/InputStream;
    //   97: invokevirtual close : ()V
    //   100: aload_3
    //   101: astore #9
    //   103: goto -> 124
    //   106: aload #7
    //   108: astore #9
    //   110: aload_1
    //   111: astore #4
    //   113: aload_2
    //   114: astore #5
    //   116: aload_0
    //   117: getfield mMap : Ljava/util/Map;
    //   120: astore_3
    //   121: aload_3
    //   122: astore #9
    //   124: aload #9
    //   126: astore #5
    //   128: aload #8
    //   130: astore_3
    //   131: aload #8
    //   133: ifnull -> 438
    //   136: aload #9
    //   138: astore #5
    //   140: aload #8
    //   142: astore_3
    //   143: aload #8
    //   145: getfield stream : Ljava/io/InputStream;
    //   148: ifnull -> 438
    //   151: aload #8
    //   153: getfield stream : Ljava/io/InputStream;
    //   156: invokevirtual close : ()V
    //   159: aload #9
    //   161: astore #5
    //   163: aload #8
    //   165: astore_3
    //   166: goto -> 438
    //   169: astore_3
    //   170: aload #9
    //   172: astore #5
    //   174: aload #8
    //   176: astore_3
    //   177: goto -> 438
    //   180: astore #8
    //   182: aload #8
    //   184: athrow
    //   185: astore_3
    //   186: aload #8
    //   188: astore #5
    //   190: aload_3
    //   191: astore #8
    //   193: aload #5
    //   195: astore_3
    //   196: goto -> 489
    //   199: astore_3
    //   200: aload #9
    //   202: astore #5
    //   204: aload_3
    //   205: astore #9
    //   207: goto -> 242
    //   210: astore_3
    //   211: aload #8
    //   213: astore_3
    //   214: aload #4
    //   216: astore #8
    //   218: goto -> 320
    //   221: astore_3
    //   222: aload_3
    //   223: astore #9
    //   225: goto -> 362
    //   228: astore #8
    //   230: goto -> 489
    //   233: astore #9
    //   235: aconst_null
    //   236: astore #5
    //   238: aload #4
    //   240: astore #8
    //   242: aload #8
    //   244: astore_3
    //   245: ldc 'XSharedPreferences'
    //   247: ldc 'getSharedPreferences'
    //   249: aload #9
    //   251: invokestatic w : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   254: pop
    //   255: aload #8
    //   257: astore #9
    //   259: aload #5
    //   261: astore_3
    //   262: aload #8
    //   264: ifnull -> 432
    //   267: aload #8
    //   269: astore #9
    //   271: aload #5
    //   273: astore_3
    //   274: aload #8
    //   276: getfield stream : Ljava/io/InputStream;
    //   279: ifnull -> 432
    //   282: aload #8
    //   284: astore #9
    //   286: aload #5
    //   288: astore_3
    //   289: aload #8
    //   291: getfield stream : Ljava/io/InputStream;
    //   294: invokevirtual close : ()V
    //   297: aload #8
    //   299: astore #9
    //   301: aload #5
    //   303: astore_3
    //   304: goto -> 432
    //   307: astore #8
    //   309: aload #8
    //   311: athrow
    //   312: astore #8
    //   314: aconst_null
    //   315: astore #8
    //   317: aload #5
    //   319: astore_3
    //   320: aload_3
    //   321: ifnull -> 346
    //   324: aload_3
    //   325: getfield stream : Ljava/io/InputStream;
    //   328: ifnull -> 346
    //   331: aload_3
    //   332: getfield stream : Ljava/io/InputStream;
    //   335: invokevirtual close : ()V
    //   338: goto -> 346
    //   341: astore #8
    //   343: aload #8
    //   345: athrow
    //   346: aload #8
    //   348: astore #5
    //   350: goto -> 438
    //   353: astore #9
    //   355: aconst_null
    //   356: astore #5
    //   358: aload #6
    //   360: astore #8
    //   362: aload #8
    //   364: astore_3
    //   365: ldc 'XSharedPreferences'
    //   367: ldc 'getSharedPreferences'
    //   369: aload #9
    //   371: invokestatic w : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   374: pop
    //   375: aload #8
    //   377: astore #9
    //   379: aload #5
    //   381: astore_3
    //   382: aload #8
    //   384: ifnull -> 432
    //   387: aload #8
    //   389: astore #9
    //   391: aload #5
    //   393: astore_3
    //   394: aload #8
    //   396: getfield stream : Ljava/io/InputStream;
    //   399: ifnull -> 432
    //   402: aload #8
    //   404: astore #9
    //   406: aload #5
    //   408: astore_3
    //   409: aload #8
    //   411: getfield stream : Ljava/io/InputStream;
    //   414: invokevirtual close : ()V
    //   417: aload #8
    //   419: astore #9
    //   421: aload #5
    //   423: astore_3
    //   424: goto -> 432
    //   427: astore #8
    //   429: aload #8
    //   431: athrow
    //   432: aload_3
    //   433: astore #5
    //   435: aload #9
    //   437: astore_3
    //   438: aload_0
    //   439: iconst_1
    //   440: putfield mLoaded : Z
    //   443: aload #5
    //   445: ifnull -> 473
    //   448: aload_0
    //   449: aload #5
    //   451: putfield mMap : Ljava/util/Map;
    //   454: aload_0
    //   455: aload_3
    //   456: getfield mtime : J
    //   459: putfield mLastModified : J
    //   462: aload_0
    //   463: aload_3
    //   464: getfield size : J
    //   467: putfield mFileSize : J
    //   470: goto -> 484
    //   473: aload_0
    //   474: new java/util/HashMap
    //   477: dup
    //   478: invokespecial <init> : ()V
    //   481: putfield mMap : Ljava/util/Map;
    //   484: aload_0
    //   485: invokevirtual notifyAll : ()V
    //   488: return
    //   489: aload_3
    //   490: ifnull -> 515
    //   493: aload_3
    //   494: getfield stream : Ljava/io/InputStream;
    //   497: ifnull -> 515
    //   500: aload_3
    //   501: getfield stream : Ljava/io/InputStream;
    //   504: invokevirtual close : ()V
    //   507: goto -> 515
    //   510: astore #8
    //   512: aload #8
    //   514: athrow
    //   515: aload #8
    //   517: athrow
    //   518: astore #8
    //   520: goto -> 432
    //   523: astore #5
    //   525: goto -> 346
    //   528: astore_3
    //   529: goto -> 515
    // Exception table:
    //   from	to	target	type
    //   26	46	353	org/xmlpull/v1/XmlPullParserException
    //   26	46	312	java/io/FileNotFoundException
    //   26	46	233	java/io/IOException
    //   26	46	228	finally
    //   56	64	221	org/xmlpull/v1/XmlPullParserException
    //   56	64	210	java/io/FileNotFoundException
    //   56	64	199	java/io/IOException
    //   56	64	185	finally
    //   74	83	221	org/xmlpull/v1/XmlPullParserException
    //   74	83	210	java/io/FileNotFoundException
    //   74	83	199	java/io/IOException
    //   74	83	185	finally
    //   92	100	221	org/xmlpull/v1/XmlPullParserException
    //   92	100	210	java/io/FileNotFoundException
    //   92	100	199	java/io/IOException
    //   92	100	185	finally
    //   116	121	221	org/xmlpull/v1/XmlPullParserException
    //   116	121	210	java/io/FileNotFoundException
    //   116	121	199	java/io/IOException
    //   116	121	185	finally
    //   151	159	180	java/lang/RuntimeException
    //   151	159	169	java/lang/Exception
    //   245	255	228	finally
    //   289	297	307	java/lang/RuntimeException
    //   289	297	518	java/lang/Exception
    //   331	338	341	java/lang/RuntimeException
    //   331	338	523	java/lang/Exception
    //   365	375	228	finally
    //   409	417	427	java/lang/RuntimeException
    //   409	417	518	java/lang/Exception
    //   500	507	510	java/lang/RuntimeException
    //   500	507	528	java/lang/Exception
  }
  
  private void startLoadFromDisk() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: iconst_0
    //   4: putfield mLoaded : Z
    //   7: aload_0
    //   8: monitorexit
    //   9: new de/robv/android/xposed/XSharedPreferences$1
    //   12: dup
    //   13: aload_0
    //   14: ldc 'XSharedPreferences-load'
    //   16: invokespecial <init> : (Lde/robv/android/xposed/XSharedPreferences;Ljava/lang/String;)V
    //   19: invokevirtual start : ()V
    //   22: return
    //   23: astore_1
    //   24: aload_0
    //   25: monitorexit
    //   26: aload_1
    //   27: athrow
    // Exception table:
    //   from	to	target	type
    //   2	9	23	finally
    //   24	26	23	finally
  }
  
  public boolean contains(String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokespecial awaitLoadedLocked : ()V
    //   6: aload_0
    //   7: getfield mMap : Ljava/util/Map;
    //   10: aload_1
    //   11: invokeinterface containsKey : (Ljava/lang/Object;)Z
    //   16: istore_2
    //   17: aload_0
    //   18: monitorexit
    //   19: iload_2
    //   20: ireturn
    //   21: astore_1
    //   22: aload_0
    //   23: monitorexit
    //   24: aload_1
    //   25: athrow
    // Exception table:
    //   from	to	target	type
    //   2	19	21	finally
    //   22	24	21	finally
  }
  
  @Deprecated
  public SharedPreferences.Editor edit() {
    throw new UnsupportedOperationException("read-only implementation");
  }
  
  public Map<String, ?> getAll() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokespecial awaitLoadedLocked : ()V
    //   6: new java/util/HashMap
    //   9: astore_1
    //   10: aload_1
    //   11: aload_0
    //   12: getfield mMap : Ljava/util/Map;
    //   15: invokespecial <init> : (Ljava/util/Map;)V
    //   18: aload_0
    //   19: monitorexit
    //   20: aload_1
    //   21: areturn
    //   22: astore_1
    //   23: aload_0
    //   24: monitorexit
    //   25: aload_1
    //   26: athrow
    // Exception table:
    //   from	to	target	type
    //   2	20	22	finally
    //   23	25	22	finally
  }
  
  public boolean getBoolean(String paramString, boolean paramBoolean) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokespecial awaitLoadedLocked : ()V
    //   6: aload_0
    //   7: getfield mMap : Ljava/util/Map;
    //   10: aload_1
    //   11: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   16: checkcast java/lang/Boolean
    //   19: astore_1
    //   20: aload_1
    //   21: ifnull -> 29
    //   24: aload_1
    //   25: invokevirtual booleanValue : ()Z
    //   28: istore_2
    //   29: aload_0
    //   30: monitorexit
    //   31: iload_2
    //   32: ireturn
    //   33: astore_1
    //   34: aload_0
    //   35: monitorexit
    //   36: aload_1
    //   37: athrow
    // Exception table:
    //   from	to	target	type
    //   2	20	33	finally
    //   24	29	33	finally
    //   29	31	33	finally
    //   34	36	33	finally
  }
  
  public File getFile() {
    return this.mFile;
  }
  
  public float getFloat(String paramString, float paramFloat) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokespecial awaitLoadedLocked : ()V
    //   6: aload_0
    //   7: getfield mMap : Ljava/util/Map;
    //   10: aload_1
    //   11: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   16: checkcast java/lang/Float
    //   19: astore_1
    //   20: aload_1
    //   21: ifnull -> 29
    //   24: aload_1
    //   25: invokevirtual floatValue : ()F
    //   28: fstore_2
    //   29: aload_0
    //   30: monitorexit
    //   31: fload_2
    //   32: freturn
    //   33: astore_1
    //   34: aload_0
    //   35: monitorexit
    //   36: aload_1
    //   37: athrow
    // Exception table:
    //   from	to	target	type
    //   2	20	33	finally
    //   24	29	33	finally
    //   29	31	33	finally
    //   34	36	33	finally
  }
  
  public int getInt(String paramString, int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokespecial awaitLoadedLocked : ()V
    //   6: aload_0
    //   7: getfield mMap : Ljava/util/Map;
    //   10: aload_1
    //   11: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   16: checkcast java/lang/Integer
    //   19: astore_1
    //   20: aload_1
    //   21: ifnull -> 29
    //   24: aload_1
    //   25: invokevirtual intValue : ()I
    //   28: istore_2
    //   29: aload_0
    //   30: monitorexit
    //   31: iload_2
    //   32: ireturn
    //   33: astore_1
    //   34: aload_0
    //   35: monitorexit
    //   36: aload_1
    //   37: athrow
    // Exception table:
    //   from	to	target	type
    //   2	20	33	finally
    //   24	29	33	finally
    //   29	31	33	finally
    //   34	36	33	finally
  }
  
  public long getLong(String paramString, long paramLong) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokespecial awaitLoadedLocked : ()V
    //   6: aload_0
    //   7: getfield mMap : Ljava/util/Map;
    //   10: aload_1
    //   11: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   16: checkcast java/lang/Long
    //   19: astore_1
    //   20: aload_1
    //   21: ifnull -> 29
    //   24: aload_1
    //   25: invokevirtual longValue : ()J
    //   28: lstore_2
    //   29: aload_0
    //   30: monitorexit
    //   31: lload_2
    //   32: lreturn
    //   33: astore_1
    //   34: aload_0
    //   35: monitorexit
    //   36: aload_1
    //   37: athrow
    // Exception table:
    //   from	to	target	type
    //   2	20	33	finally
    //   24	29	33	finally
    //   29	31	33	finally
    //   34	36	33	finally
  }
  
  public String getString(String paramString1, String paramString2) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokespecial awaitLoadedLocked : ()V
    //   6: aload_0
    //   7: getfield mMap : Ljava/util/Map;
    //   10: aload_1
    //   11: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   16: checkcast java/lang/String
    //   19: astore_1
    //   20: aload_1
    //   21: ifnull -> 26
    //   24: aload_1
    //   25: astore_2
    //   26: aload_0
    //   27: monitorexit
    //   28: aload_2
    //   29: areturn
    //   30: astore_1
    //   31: aload_0
    //   32: monitorexit
    //   33: aload_1
    //   34: athrow
    // Exception table:
    //   from	to	target	type
    //   2	20	30	finally
    //   26	28	30	finally
    //   31	33	30	finally
  }
  
  public Set<String> getStringSet(String paramString, Set<String> paramSet) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokespecial awaitLoadedLocked : ()V
    //   6: aload_0
    //   7: getfield mMap : Ljava/util/Map;
    //   10: aload_1
    //   11: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   16: checkcast java/util/Set
    //   19: astore_1
    //   20: aload_1
    //   21: ifnull -> 26
    //   24: aload_1
    //   25: astore_2
    //   26: aload_0
    //   27: monitorexit
    //   28: aload_2
    //   29: areturn
    //   30: astore_1
    //   31: aload_0
    //   32: monitorexit
    //   33: aload_1
    //   34: athrow
    // Exception table:
    //   from	to	target	type
    //   2	20	30	finally
    //   26	28	30	finally
    //   31	33	30	finally
  }
  
  public boolean hasFileChanged() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: iconst_1
    //   3: istore_1
    //   4: invokestatic getAppDataFileService : ()Lde/robv/android/xposed/services/BaseService;
    //   7: aload_0
    //   8: getfield mFilename : Ljava/lang/String;
    //   11: invokevirtual statFile : (Ljava/lang/String;)Lde/robv/android/xposed/services/FileResult;
    //   14: astore_2
    //   15: iload_1
    //   16: istore_3
    //   17: aload_0
    //   18: getfield mLastModified : J
    //   21: aload_2
    //   22: getfield mtime : J
    //   25: lcmp
    //   26: ifne -> 56
    //   29: aload_0
    //   30: getfield mFileSize : J
    //   33: lstore #4
    //   35: aload_2
    //   36: getfield size : J
    //   39: lstore #6
    //   41: lload #4
    //   43: lload #6
    //   45: lcmp
    //   46: ifeq -> 54
    //   49: iload_1
    //   50: istore_3
    //   51: goto -> 56
    //   54: iconst_0
    //   55: istore_3
    //   56: aload_0
    //   57: monitorexit
    //   58: iload_3
    //   59: ireturn
    //   60: astore_2
    //   61: goto -> 78
    //   64: astore_2
    //   65: ldc 'XSharedPreferences'
    //   67: ldc 'hasFileChanged'
    //   69: aload_2
    //   70: invokestatic w : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   73: pop
    //   74: aload_0
    //   75: monitorexit
    //   76: iconst_1
    //   77: ireturn
    //   78: aload_0
    //   79: monitorexit
    //   80: aload_2
    //   81: athrow
    //   82: astore_2
    //   83: aload_0
    //   84: monitorexit
    //   85: iconst_1
    //   86: ireturn
    // Exception table:
    //   from	to	target	type
    //   4	15	82	java/io/FileNotFoundException
    //   4	15	64	java/io/IOException
    //   4	15	60	finally
    //   17	41	82	java/io/FileNotFoundException
    //   17	41	64	java/io/IOException
    //   17	41	60	finally
    //   65	74	60	finally
  }
  
  public boolean makeWorldReadable() {
    return !SELinuxHelper.getAppDataFileService().hasDirectFileAccess() ? false : (!this.mFile.exists() ? false : this.mFile.setReadable(true, false));
  }
  
  @Deprecated
  public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener) {
    throw new UnsupportedOperationException("listeners are not supported in this implementation");
  }
  
  public void reload() {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual hasFileChanged : ()Z
    //   6: ifeq -> 13
    //   9: aload_0
    //   10: invokespecial startLoadFromDisk : ()V
    //   13: aload_0
    //   14: monitorexit
    //   15: return
    //   16: astore_1
    //   17: aload_0
    //   18: monitorexit
    //   19: aload_1
    //   20: athrow
    // Exception table:
    //   from	to	target	type
    //   2	13	16	finally
  }
  
  @Deprecated
  public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener) {
    throw new UnsupportedOperationException("listeners are not supported in this implementation");
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\de\robv\android\xposed\XSharedPreferences.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */