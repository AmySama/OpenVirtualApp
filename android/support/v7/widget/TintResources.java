package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import java.lang.ref.WeakReference;

class TintResources extends ResourcesWrapper {
  private final WeakReference<Context> mContextRef;
  
  public TintResources(Context paramContext, Resources paramResources) {
    super(paramResources);
    this.mContextRef = new WeakReference<Context>(paramContext);
  }
  
  public Drawable getDrawable(int paramInt) throws Resources.NotFoundException {
    Drawable drawable = super.getDrawable(paramInt);
    Context context = this.mContextRef.get();
    if (drawable != null && context != null) {
      AppCompatDrawableManager.get();
      AppCompatDrawableManager.tintDrawableUsingColorFilter(context, paramInt, drawable);
    } 
    return drawable;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\TintResources.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */