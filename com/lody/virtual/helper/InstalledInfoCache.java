package com.lody.virtual.helper;

import android.graphics.drawable.Drawable;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.helper.utils.ACache;
import java.io.Serializable;

public class InstalledInfoCache {
  private static ACache diskCache = ACache.get(VirtualCore.get().getContext(), "AppInfoCache");
  
  public static CacheItem get(String paramString) {
    ACache aCache = diskCache;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("va_installed_info_cache@");
    stringBuilder.append(paramString);
    return (CacheItem)aCache.getAsObject(stringBuilder.toString());
  }
  
  public static void save(CacheItem paramCacheItem) {
    ACache aCache = diskCache;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("va_installed_info_cache@");
    stringBuilder.append(paramCacheItem.packageName);
    aCache.put(stringBuilder.toString(), paramCacheItem);
    paramCacheItem.saveIcon();
  }
  
  public static class CacheItem implements Serializable {
    public static final transient String ICON_CACHE_PREFIX = "va_installed_icon_cache@";
    
    public static final transient String INFO_CACHE_PREFIX = "va_installed_info_cache@";
    
    private static final long serialVersionUID = 1L;
    
    public transient Drawable icon;
    
    public String label;
    
    public String packageName;
    
    public CacheItem(String param1String1, String param1String2, Drawable param1Drawable) {
      this.packageName = param1String1;
      this.label = param1String2;
      this.icon = param1Drawable;
    }
    
    public Drawable getIcon() {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield icon : Landroid/graphics/drawable/Drawable;
      //   6: ifnonnull -> 49
      //   9: invokestatic access$000 : ()Lcom/lody/virtual/helper/utils/ACache;
      //   12: astore_1
      //   13: new java/lang/StringBuilder
      //   16: astore_2
      //   17: aload_2
      //   18: invokespecial <init> : ()V
      //   21: aload_2
      //   22: ldc 'va_installed_icon_cache@'
      //   24: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   27: pop
      //   28: aload_2
      //   29: aload_0
      //   30: getfield packageName : Ljava/lang/String;
      //   33: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   36: pop
      //   37: aload_0
      //   38: aload_1
      //   39: aload_2
      //   40: invokevirtual toString : ()Ljava/lang/String;
      //   43: invokevirtual getAsDrawable : (Ljava/lang/String;)Landroid/graphics/drawable/Drawable;
      //   46: putfield icon : Landroid/graphics/drawable/Drawable;
      //   49: aload_0
      //   50: getfield icon : Landroid/graphics/drawable/Drawable;
      //   53: astore_2
      //   54: aload_0
      //   55: monitorexit
      //   56: aload_2
      //   57: areturn
      //   58: astore_2
      //   59: aload_0
      //   60: monitorexit
      //   61: aload_2
      //   62: athrow
      // Exception table:
      //   from	to	target	type
      //   2	49	58	finally
      //   49	54	58	finally
    }
    
    public String getLabel() {
      return this.label;
    }
    
    public String getPackageName() {
      return this.packageName;
    }
    
    public void saveIcon() {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield icon : Landroid/graphics/drawable/Drawable;
      //   6: ifnull -> 49
      //   9: invokestatic access$000 : ()Lcom/lody/virtual/helper/utils/ACache;
      //   12: astore_1
      //   13: new java/lang/StringBuilder
      //   16: astore_2
      //   17: aload_2
      //   18: invokespecial <init> : ()V
      //   21: aload_2
      //   22: ldc 'va_installed_icon_cache@'
      //   24: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   27: pop
      //   28: aload_2
      //   29: aload_0
      //   30: getfield packageName : Ljava/lang/String;
      //   33: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   36: pop
      //   37: aload_1
      //   38: aload_2
      //   39: invokevirtual toString : ()Ljava/lang/String;
      //   42: aload_0
      //   43: getfield icon : Landroid/graphics/drawable/Drawable;
      //   46: invokevirtual put : (Ljava/lang/String;Landroid/graphics/drawable/Drawable;)V
      //   49: aload_0
      //   50: monitorexit
      //   51: return
      //   52: astore_1
      //   53: aload_0
      //   54: monitorexit
      //   55: aload_1
      //   56: athrow
      // Exception table:
      //   from	to	target	type
      //   2	49	52	finally
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helper\InstalledInfoCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */