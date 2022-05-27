package android.support.v7.content.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParser;

public final class AppCompatResources {
  private static final String LOG_TAG = "AppCompatResources";
  
  private static final ThreadLocal<TypedValue> TL_TYPED_VALUE = new ThreadLocal<TypedValue>();
  
  private static final Object sColorStateCacheLock;
  
  private static final WeakHashMap<Context, SparseArray<ColorStateListCacheEntry>> sColorStateCaches = new WeakHashMap<Context, SparseArray<ColorStateListCacheEntry>>(0);
  
  static {
    sColorStateCacheLock = new Object();
  }
  
  private static void addColorStateListToCache(Context paramContext, int paramInt, ColorStateList paramColorStateList) {
    synchronized (sColorStateCacheLock) {
      SparseArray<ColorStateListCacheEntry> sparseArray1 = sColorStateCaches.get(paramContext);
      SparseArray<ColorStateListCacheEntry> sparseArray2 = sparseArray1;
      if (sparseArray1 == null) {
        sparseArray2 = new SparseArray();
        this();
        sColorStateCaches.put(paramContext, sparseArray2);
      } 
      ColorStateListCacheEntry colorStateListCacheEntry = new ColorStateListCacheEntry();
      this(paramColorStateList, paramContext.getResources().getConfiguration());
      sparseArray2.append(paramInt, colorStateListCacheEntry);
      return;
    } 
  }
  
  private static ColorStateList getCachedColorStateList(Context paramContext, int paramInt) {
    synchronized (sColorStateCacheLock) {
      SparseArray sparseArray = sColorStateCaches.get(paramContext);
      if (sparseArray != null && sparseArray.size() > 0) {
        ColorStateListCacheEntry colorStateListCacheEntry = (ColorStateListCacheEntry)sparseArray.get(paramInt);
        if (colorStateListCacheEntry != null) {
          if (colorStateListCacheEntry.configuration.equals(paramContext.getResources().getConfiguration()))
            return colorStateListCacheEntry.value; 
          sparseArray.remove(paramInt);
        } 
      } 
      return null;
    } 
  }
  
  public static ColorStateList getColorStateList(Context paramContext, int paramInt) {
    if (Build.VERSION.SDK_INT >= 23)
      return paramContext.getColorStateList(paramInt); 
    ColorStateList colorStateList = getCachedColorStateList(paramContext, paramInt);
    if (colorStateList != null)
      return colorStateList; 
    colorStateList = inflateColorStateList(paramContext, paramInt);
    if (colorStateList != null) {
      addColorStateListToCache(paramContext, paramInt, colorStateList);
      return colorStateList;
    } 
    return ContextCompat.getColorStateList(paramContext, paramInt);
  }
  
  public static Drawable getDrawable(Context paramContext, int paramInt) {
    return AppCompatDrawableManager.get().getDrawable(paramContext, paramInt);
  }
  
  private static TypedValue getTypedValue() {
    TypedValue typedValue1 = TL_TYPED_VALUE.get();
    TypedValue typedValue2 = typedValue1;
    if (typedValue1 == null) {
      typedValue2 = new TypedValue();
      TL_TYPED_VALUE.set(typedValue2);
    } 
    return typedValue2;
  }
  
  private static ColorStateList inflateColorStateList(Context paramContext, int paramInt) {
    if (isColorInt(paramContext, paramInt))
      return null; 
    Resources resources = paramContext.getResources();
    XmlResourceParser xmlResourceParser = resources.getXml(paramInt);
    try {
      return AppCompatColorStateListInflater.createFromXml(resources, (XmlPullParser)xmlResourceParser, paramContext.getTheme());
    } catch (Exception exception) {
      Log.e("AppCompatResources", "Failed to inflate ColorStateList, leaving it to the framework", exception);
      return null;
    } 
  }
  
  private static boolean isColorInt(Context paramContext, int paramInt) {
    Resources resources = paramContext.getResources();
    TypedValue typedValue = getTypedValue();
    boolean bool = true;
    resources.getValue(paramInt, typedValue, true);
    if (typedValue.type < 28 || typedValue.type > 31)
      bool = false; 
    return bool;
  }
  
  private static class ColorStateListCacheEntry {
    final Configuration configuration;
    
    final ColorStateList value;
    
    ColorStateListCacheEntry(ColorStateList param1ColorStateList, Configuration param1Configuration) {
      this.value = param1ColorStateList;
      this.configuration = param1Configuration;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\content\res\AppCompatResources.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */