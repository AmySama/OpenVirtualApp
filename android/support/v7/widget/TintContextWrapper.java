package android.support.v7.widget;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class TintContextWrapper extends ContextWrapper {
  private static final Object CACHE_LOCK = new Object();
  
  private static ArrayList<WeakReference<TintContextWrapper>> sCache;
  
  private final Resources mResources;
  
  private final Resources.Theme mTheme;
  
  private TintContextWrapper(Context paramContext) {
    super(paramContext);
    if (VectorEnabledTintResources.shouldBeUsed()) {
      VectorEnabledTintResources vectorEnabledTintResources = new VectorEnabledTintResources((Context)this, paramContext.getResources());
      this.mResources = vectorEnabledTintResources;
      Resources.Theme theme = vectorEnabledTintResources.newTheme();
      this.mTheme = theme;
      theme.setTo(paramContext.getTheme());
    } else {
      this.mResources = new TintResources((Context)this, paramContext.getResources());
      this.mTheme = null;
    } 
  }
  
  private static boolean shouldWrap(Context paramContext) {
    boolean bool = paramContext instanceof TintContextWrapper;
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (!bool) {
      bool2 = bool1;
      if (!(paramContext.getResources() instanceof TintResources))
        if (paramContext.getResources() instanceof VectorEnabledTintResources) {
          bool2 = bool1;
        } else {
          if (Build.VERSION.SDK_INT >= 21) {
            bool2 = bool1;
            if (VectorEnabledTintResources.shouldBeUsed())
              bool2 = true; 
            return bool2;
          } 
          bool2 = true;
        }  
    } 
    return bool2;
  }
  
  public static Context wrap(Context paramContext) {
    if (shouldWrap(paramContext))
      synchronized (CACHE_LOCK) {
        if (sCache == null) {
          ArrayList<WeakReference<TintContextWrapper>> arrayList1 = new ArrayList();
          this();
          sCache = arrayList1;
        } else {
          int i;
          for (i = sCache.size() - 1; i >= 0; i--) {
            WeakReference weakReference1 = sCache.get(i);
            if (weakReference1 == null || weakReference1.get() == null)
              sCache.remove(i); 
          } 
          for (i = sCache.size() - 1; i >= 0; i--) {
            WeakReference<TintContextWrapper> weakReference1 = sCache.get(i);
            if (weakReference1 != null) {
              TintContextWrapper tintContextWrapper1 = weakReference1.get();
            } else {
              weakReference1 = null;
            } 
            if (weakReference1 != null && weakReference1.getBaseContext() == paramContext)
              return (Context)weakReference1; 
          } 
        } 
        TintContextWrapper tintContextWrapper = new TintContextWrapper();
        this(paramContext);
        ArrayList<WeakReference<TintContextWrapper>> arrayList = sCache;
        WeakReference<TintContextWrapper> weakReference = new WeakReference();
        this((T)tintContextWrapper);
        arrayList.add(weakReference);
        return (Context)tintContextWrapper;
      }  
    return paramContext;
  }
  
  public AssetManager getAssets() {
    return this.mResources.getAssets();
  }
  
  public Resources getResources() {
    return this.mResources;
  }
  
  public Resources.Theme getTheme() {
    Resources.Theme theme1 = this.mTheme;
    Resources.Theme theme2 = theme1;
    if (theme1 == null)
      theme2 = super.getTheme(); 
    return theme2;
  }
  
  public void setTheme(int paramInt) {
    Resources.Theme theme = this.mTheme;
    if (theme == null) {
      super.setTheme(paramInt);
    } else {
      theme.applyStyle(paramInt, true);
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\TintContextWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */