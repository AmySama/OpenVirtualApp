package com.swift.sandhook.xposedcompat.methodgen;

import com.swift.sandhook.xposedcompat.XposedCompat;
import com.swift.sandhook.xposedcompat.hookstub.HookMethodEntity;
import com.swift.sandhook.xposedcompat.utils.DexLog;
import com.swift.sandhook.xposedcompat.utils.FileUtils;
import de.robv.android.xposed.XposedBridge;
import java.io.File;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public final class DynamicBridge {
  private static HookMaker defaultHookMaker;
  
  private static File dexDir;
  
  private static final AtomicBoolean dexPathInited = new AtomicBoolean(false);
  
  private static final Map<Member, HookMethodEntity> entityMap = new HashMap<Member, HookMethodEntity>();
  
  private static final HashMap<Member, Method> hookedInfo = new HashMap<Member, Method>();
  
  private static boolean checkMember(Member paramMember) {
    if (paramMember instanceof Method)
      return true; 
    if (paramMember instanceof java.lang.reflect.Constructor)
      return true; 
    if (paramMember.getDeclaringClass().isInterface()) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Cannot hook interfaces: ");
      stringBuilder1.append(paramMember.toString());
      DexLog.e(stringBuilder1.toString());
      return false;
    } 
    if (Modifier.isAbstract(paramMember.getModifiers())) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Cannot hook abstract methods: ");
      stringBuilder1.append(paramMember.toString());
      DexLog.e(stringBuilder1.toString());
      return false;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Only methods and constructors can be hooked: ");
    stringBuilder.append(paramMember.toString());
    DexLog.e(stringBuilder.toString());
    return false;
  }
  
  public static void clearOatFile() {
    File file = new File(XposedCompat.getCacheDir().getAbsolutePath(), "/sandxposed/oat/");
    if (!file.exists())
      return; 
    try {
      FileUtils.delete(file);
      file.mkdirs();
    } finally {}
  }
  
  public static void hookMethod(Member paramMember, XposedBridge.AdditionalHookInfo paramAdditionalHookInfo) {
    // Byte code:
    //   0: ldc com/swift/sandhook/xposedcompat/methodgen/DynamicBridge
    //   2: monitorenter
    //   3: aload_0
    //   4: invokestatic checkMember : (Ljava/lang/reflect/Member;)Z
    //   7: istore_2
    //   8: iload_2
    //   9: ifne -> 16
    //   12: ldc com/swift/sandhook/xposedcompat/methodgen/DynamicBridge
    //   14: monitorexit
    //   15: return
    //   16: getstatic com/swift/sandhook/xposedcompat/methodgen/DynamicBridge.hookedInfo : Ljava/util/HashMap;
    //   19: aload_0
    //   20: invokevirtual containsKey : (Ljava/lang/Object;)Z
    //   23: ifne -> 423
    //   26: getstatic com/swift/sandhook/xposedcompat/methodgen/DynamicBridge.entityMap : Ljava/util/Map;
    //   29: aload_0
    //   30: invokeinterface containsKey : (Ljava/lang/Object;)Z
    //   35: istore_2
    //   36: iload_2
    //   37: ifeq -> 43
    //   40: goto -> 423
    //   43: getstatic com/swift/sandhook/xposedcompat/methodgen/DynamicBridge.dexPathInited : Ljava/util/concurrent/atomic/AtomicBoolean;
    //   46: iconst_0
    //   47: iconst_1
    //   48: invokevirtual compareAndSet : (ZZ)Z
    //   51: istore_2
    //   52: iload_2
    //   53: ifeq -> 107
    //   56: invokestatic getCacheDir : ()Ljava/io/File;
    //   59: invokevirtual getAbsolutePath : ()Ljava/lang/String;
    //   62: astore_3
    //   63: new java/io/File
    //   66: astore #4
    //   68: aload #4
    //   70: aload_3
    //   71: ldc '/sandxposed/'
    //   73: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;)V
    //   76: aload #4
    //   78: putstatic com/swift/sandhook/xposedcompat/methodgen/DynamicBridge.dexDir : Ljava/io/File;
    //   81: aload #4
    //   83: invokevirtual exists : ()Z
    //   86: ifne -> 107
    //   89: getstatic com/swift/sandhook/xposedcompat/methodgen/DynamicBridge.dexDir : Ljava/io/File;
    //   92: invokevirtual mkdirs : ()Z
    //   95: pop
    //   96: goto -> 107
    //   99: astore_3
    //   100: ldc 'error when init dex path'
    //   102: aload_3
    //   103: invokestatic e : (Ljava/lang/String;Ljava/lang/Throwable;)I
    //   106: pop
    //   107: ldc 'SandHook-Xposed'
    //   109: invokestatic beginSection : (Ljava/lang/String;)V
    //   112: invokestatic currentTimeMillis : ()J
    //   115: lstore #5
    //   117: getstatic com/swift/sandhook/xposedcompat/XposedCompat.useInternalStub : Z
    //   120: istore_2
    //   121: aconst_null
    //   122: astore #7
    //   124: iload_2
    //   125: ifeq -> 151
    //   128: aload_0
    //   129: invokestatic canNotHookByStub : (Ljava/lang/reflect/Member;)Z
    //   132: ifne -> 151
    //   135: aload_0
    //   136: invokestatic canNotHookByBridge : (Ljava/lang/reflect/Member;)Z
    //   139: ifne -> 151
    //   142: aload_0
    //   143: aload_1
    //   144: invokestatic getHookMethodEntity : (Ljava/lang/reflect/Member;Lde/robv/android/xposed/XposedBridge$AdditionalHookInfo;)Lcom/swift/sandhook/xposedcompat/hookstub/HookMethodEntity;
    //   147: astore_3
    //   148: goto -> 153
    //   151: aconst_null
    //   152: astore_3
    //   153: aload_3
    //   154: ifnull -> 193
    //   157: new com/swift/sandhook/wrapper/HookWrapper$HookEntity
    //   160: astore_1
    //   161: aload_1
    //   162: aload_0
    //   163: aload_3
    //   164: getfield hook : Ljava/lang/reflect/Method;
    //   167: aload_3
    //   168: getfield backup : Ljava/lang/reflect/Method;
    //   171: iconst_0
    //   172: invokespecial <init> : (Ljava/lang/reflect/Member;Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;Z)V
    //   175: aload_1
    //   176: invokestatic hook : (Lcom/swift/sandhook/wrapper/HookWrapper$HookEntity;)V
    //   179: getstatic com/swift/sandhook/xposedcompat/methodgen/DynamicBridge.entityMap : Ljava/util/Map;
    //   182: aload_0
    //   183: aload_3
    //   184: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   189: pop
    //   190: goto -> 287
    //   193: aload_0
    //   194: invokestatic canNotHookByBridge : (Ljava/lang/reflect/Member;)Z
    //   197: ifeq -> 213
    //   200: new com/swift/sandhook/xposedcompat/methodgen/HookerDexMaker
    //   203: astore #4
    //   205: aload #4
    //   207: invokespecial <init> : ()V
    //   210: goto -> 218
    //   213: getstatic com/swift/sandhook/xposedcompat/methodgen/DynamicBridge.defaultHookMaker : Lcom/swift/sandhook/xposedcompat/methodgen/HookMaker;
    //   216: astore #4
    //   218: new com/swift/sandhook/xposedcompat/classloaders/ProxyClassLoader
    //   221: astore #8
    //   223: aload #8
    //   225: ldc com/swift/sandhook/xposedcompat/methodgen/DynamicBridge
    //   227: invokevirtual getClassLoader : ()Ljava/lang/ClassLoader;
    //   230: aload_0
    //   231: invokeinterface getDeclaringClass : ()Ljava/lang/Class;
    //   236: invokevirtual getClassLoader : ()Ljava/lang/ClassLoader;
    //   239: invokespecial <init> : (Ljava/lang/ClassLoader;Ljava/lang/ClassLoader;)V
    //   242: getstatic com/swift/sandhook/xposedcompat/methodgen/DynamicBridge.dexDir : Ljava/io/File;
    //   245: ifnonnull -> 251
    //   248: goto -> 259
    //   251: getstatic com/swift/sandhook/xposedcompat/methodgen/DynamicBridge.dexDir : Ljava/io/File;
    //   254: invokevirtual getAbsolutePath : ()Ljava/lang/String;
    //   257: astore #7
    //   259: aload #4
    //   261: aload_0
    //   262: aload_1
    //   263: aload #8
    //   265: aload #7
    //   267: invokeinterface start : (Ljava/lang/reflect/Member;Lde/robv/android/xposed/XposedBridge$AdditionalHookInfo;Ljava/lang/ClassLoader;Ljava/lang/String;)V
    //   272: getstatic com/swift/sandhook/xposedcompat/methodgen/DynamicBridge.hookedInfo : Ljava/util/HashMap;
    //   275: aload_0
    //   276: aload #4
    //   278: invokeinterface getCallBackupMethod : ()Ljava/lang/reflect/Method;
    //   283: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   286: pop
    //   287: new java/lang/StringBuilder
    //   290: astore #4
    //   292: aload #4
    //   294: invokespecial <init> : ()V
    //   297: aload #4
    //   299: ldc 'hook method <'
    //   301: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   304: pop
    //   305: aload #4
    //   307: aload_0
    //   308: invokevirtual toString : ()Ljava/lang/String;
    //   311: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   314: pop
    //   315: aload #4
    //   317: ldc '> cost '
    //   319: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   322: pop
    //   323: aload #4
    //   325: invokestatic currentTimeMillis : ()J
    //   328: lload #5
    //   330: lsub
    //   331: invokevirtual append : (J)Ljava/lang/StringBuilder;
    //   334: pop
    //   335: aload #4
    //   337: ldc ' ms, by '
    //   339: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   342: pop
    //   343: aload_3
    //   344: ifnull -> 353
    //   347: ldc 'internal stub'
    //   349: astore_1
    //   350: goto -> 356
    //   353: ldc 'dex maker'
    //   355: astore_1
    //   356: aload #4
    //   358: aload_1
    //   359: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   362: pop
    //   363: aload #4
    //   365: invokevirtual toString : ()Ljava/lang/String;
    //   368: invokestatic d : (Ljava/lang/String;)I
    //   371: pop
    //   372: invokestatic endSection : ()V
    //   375: goto -> 419
    //   378: astore_3
    //   379: new java/lang/StringBuilder
    //   382: astore_1
    //   383: aload_1
    //   384: invokespecial <init> : ()V
    //   387: aload_1
    //   388: ldc 'error occur when hook method <'
    //   390: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   393: pop
    //   394: aload_1
    //   395: aload_0
    //   396: invokevirtual toString : ()Ljava/lang/String;
    //   399: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   402: pop
    //   403: aload_1
    //   404: ldc '>'
    //   406: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   409: pop
    //   410: aload_1
    //   411: invokevirtual toString : ()Ljava/lang/String;
    //   414: aload_3
    //   415: invokestatic e : (Ljava/lang/String;Ljava/lang/Throwable;)I
    //   418: pop
    //   419: ldc com/swift/sandhook/xposedcompat/methodgen/DynamicBridge
    //   421: monitorexit
    //   422: return
    //   423: new java/lang/StringBuilder
    //   426: astore_1
    //   427: aload_1
    //   428: invokespecial <init> : ()V
    //   431: aload_1
    //   432: ldc 'already hook method:'
    //   434: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   437: pop
    //   438: aload_1
    //   439: aload_0
    //   440: invokevirtual toString : ()Ljava/lang/String;
    //   443: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   446: pop
    //   447: aload_1
    //   448: invokevirtual toString : ()Ljava/lang/String;
    //   451: invokestatic w : (Ljava/lang/String;)I
    //   454: pop
    //   455: ldc com/swift/sandhook/xposedcompat/methodgen/DynamicBridge
    //   457: monitorexit
    //   458: return
    //   459: astore_0
    //   460: ldc com/swift/sandhook/xposedcompat/methodgen/DynamicBridge
    //   462: monitorexit
    //   463: aload_0
    //   464: athrow
    // Exception table:
    //   from	to	target	type
    //   3	8	459	finally
    //   16	36	459	finally
    //   43	52	378	finally
    //   56	96	99	finally
    //   100	107	378	finally
    //   107	121	378	finally
    //   128	148	378	finally
    //   157	190	378	finally
    //   193	210	378	finally
    //   213	218	378	finally
    //   218	248	378	finally
    //   251	259	378	finally
    //   259	287	378	finally
    //   287	343	378	finally
    //   356	375	378	finally
    //   379	419	459	finally
    //   423	455	459	finally
  }
  
  static {
    HookerDexMaker hookerDexMaker;
    if (XposedCompat.useNewCallBackup) {
      HookerDexMakerNew hookerDexMakerNew = new HookerDexMakerNew();
    } else {
      hookerDexMaker = new HookerDexMaker();
    } 
    defaultHookMaker = hookerDexMaker;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\swift\sandhook\xposedcompat\methodgen\DynamicBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */