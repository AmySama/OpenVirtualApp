package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatDelegate;
import java.lang.ref.WeakReference;

public class VectorEnabledTintResources extends Resources {
  public static final int MAX_SDK_WHERE_REQUIRED = 20;
  
  private final WeakReference<Context> mContextRef;
  
  public VectorEnabledTintResources(Context paramContext, Resources paramResources) {
    super(paramResources.getAssets(), paramResources.getDisplayMetrics(), paramResources.getConfiguration());
    this.mContextRef = new WeakReference<Context>(paramContext);
  }
  
  public static boolean shouldBeUsed() {
    boolean bool;
    if (AppCompatDelegate.isCompatVectorFromResourcesEnabled() && Build.VERSION.SDK_INT <= 20) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public Drawable getDrawable(int paramInt) throws Resources.NotFoundException {
    Context context = this.mContextRef.get();
    return (context != null) ? AppCompatDrawableManager.get().onDrawableLoadedFromResources(context, this, paramInt) : super.getDrawable(paramInt);
  }
  
  final Drawable superGetDrawable(int paramInt) {
    return super.getDrawable(paramInt);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\VectorEnabledTintResources.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */