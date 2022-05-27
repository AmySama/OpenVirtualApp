package com.lody.virtual.server.am;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.SparseArray;
import java.util.HashMap;
import java.util.WeakHashMap;

public final class AttributeCache {
  private static final AttributeCache sInstance = new AttributeCache();
  
  private final Configuration mConfiguration = new Configuration();
  
  private final WeakHashMap<String, Package> mPackages = new WeakHashMap<String, Package>();
  
  public static AttributeCache instance() {
    return sInstance;
  }
  
  public Entry get(String paramString, int paramInt, int[] paramArrayOfint) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mPackages : Ljava/util/WeakHashMap;
    //   6: aload_1
    //   7: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   10: checkcast com/lody/virtual/server/am/AttributeCache$Package
    //   13: astore #4
    //   15: aload #4
    //   17: ifnull -> 74
    //   20: aload #4
    //   22: invokestatic access$000 : (Lcom/lody/virtual/server/am/AttributeCache$Package;)Landroid/util/SparseArray;
    //   25: iload_2
    //   26: invokevirtual get : (I)Ljava/lang/Object;
    //   29: checkcast java/util/HashMap
    //   32: astore #5
    //   34: aload #4
    //   36: astore #6
    //   38: aload #5
    //   40: astore_1
    //   41: aload #5
    //   43: ifnull -> 108
    //   46: aload #5
    //   48: aload_3
    //   49: invokevirtual get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   52: checkcast com/lody/virtual/server/am/AttributeCache$Entry
    //   55: astore #7
    //   57: aload #4
    //   59: astore #6
    //   61: aload #5
    //   63: astore_1
    //   64: aload #7
    //   66: ifnull -> 108
    //   69: aload_0
    //   70: monitorexit
    //   71: aload #7
    //   73: areturn
    //   74: invokestatic get : ()Lcom/lody/virtual/client/core/VirtualCore;
    //   77: aload_1
    //   78: invokevirtual getResources : (Ljava/lang/String;)Landroid/content/res/Resources;
    //   81: astore #4
    //   83: new com/lody/virtual/server/am/AttributeCache$Package
    //   86: astore #6
    //   88: aload #6
    //   90: aload #4
    //   92: invokespecial <init> : (Landroid/content/res/Resources;)V
    //   95: aload_0
    //   96: getfield mPackages : Ljava/util/WeakHashMap;
    //   99: aload_1
    //   100: aload #6
    //   102: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   105: pop
    //   106: aconst_null
    //   107: astore_1
    //   108: aload_1
    //   109: astore #4
    //   111: aload_1
    //   112: ifnonnull -> 136
    //   115: new java/util/HashMap
    //   118: astore #4
    //   120: aload #4
    //   122: invokespecial <init> : ()V
    //   125: aload #6
    //   127: invokestatic access$000 : (Lcom/lody/virtual/server/am/AttributeCache$Package;)Landroid/util/SparseArray;
    //   130: iload_2
    //   131: aload #4
    //   133: invokevirtual put : (ILjava/lang/Object;)V
    //   136: new com/lody/virtual/server/am/AttributeCache$Entry
    //   139: astore_1
    //   140: aload_1
    //   141: aload #6
    //   143: getfield resources : Landroid/content/res/Resources;
    //   146: aload #6
    //   148: getfield resources : Landroid/content/res/Resources;
    //   151: invokevirtual newTheme : ()Landroid/content/res/Resources$Theme;
    //   154: iload_2
    //   155: aload_3
    //   156: invokevirtual obtainStyledAttributes : (I[I)Landroid/content/res/TypedArray;
    //   159: invokespecial <init> : (Landroid/content/res/Resources;Landroid/content/res/TypedArray;)V
    //   162: aload #4
    //   164: aload_3
    //   165: aload_1
    //   166: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   169: pop
    //   170: aload_0
    //   171: monitorexit
    //   172: aload_1
    //   173: areturn
    //   174: astore_1
    //   175: aload_0
    //   176: monitorexit
    //   177: aconst_null
    //   178: areturn
    //   179: astore_1
    //   180: aload_0
    //   181: monitorexit
    //   182: aconst_null
    //   183: areturn
    //   184: astore_1
    //   185: aload_0
    //   186: monitorexit
    //   187: aload_1
    //   188: athrow
    // Exception table:
    //   from	to	target	type
    //   2	15	184	finally
    //   20	34	184	finally
    //   46	57	184	finally
    //   69	71	184	finally
    //   74	83	179	finally
    //   83	106	184	finally
    //   115	136	184	finally
    //   136	170	174	android/content/res/Resources$NotFoundException
    //   136	170	184	finally
    //   170	172	184	finally
    //   175	177	184	finally
    //   180	182	184	finally
    //   185	187	184	finally
  }
  
  public void removePackage(String paramString) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mPackages : Ljava/util/WeakHashMap;
    //   6: aload_1
    //   7: invokevirtual remove : (Ljava/lang/Object;)Ljava/lang/Object;
    //   10: pop
    //   11: aload_0
    //   12: monitorexit
    //   13: return
    //   14: astore_1
    //   15: aload_0
    //   16: monitorexit
    //   17: aload_1
    //   18: athrow
    // Exception table:
    //   from	to	target	type
    //   2	13	14	finally
    //   15	17	14	finally
  }
  
  public void updateConfiguration(Configuration paramConfiguration) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield mConfiguration : Landroid/content/res/Configuration;
    //   6: aload_1
    //   7: invokevirtual updateFrom : (Landroid/content/res/Configuration;)I
    //   10: ldc -1073741985
    //   12: iand
    //   13: ifeq -> 23
    //   16: aload_0
    //   17: getfield mPackages : Ljava/util/WeakHashMap;
    //   20: invokevirtual clear : ()V
    //   23: aload_0
    //   24: monitorexit
    //   25: return
    //   26: astore_1
    //   27: aload_0
    //   28: monitorexit
    //   29: aload_1
    //   30: athrow
    // Exception table:
    //   from	to	target	type
    //   2	23	26	finally
    //   23	25	26	finally
    //   27	29	26	finally
  }
  
  public static final class Entry {
    public final TypedArray array;
    
    public final Resources resource;
    
    public Entry(Resources param1Resources, TypedArray param1TypedArray) {
      this.resource = param1Resources;
      this.array = param1TypedArray;
    }
  }
  
  public static final class Package {
    private final SparseArray<HashMap<int[], AttributeCache.Entry>> mMap = new SparseArray();
    
    public final Resources resources;
    
    public Package(Resources param1Resources) {
      this.resources = param1Resources;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\am\AttributeCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */