package android.support.v7.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.appcompat.R;
import android.view.LayoutInflater;

public class ContextThemeWrapper extends ContextWrapper {
  private LayoutInflater mInflater;
  
  private Configuration mOverrideConfiguration;
  
  private Resources mResources;
  
  private Resources.Theme mTheme;
  
  private int mThemeResource;
  
  public ContextThemeWrapper() {
    super(null);
  }
  
  public ContextThemeWrapper(Context paramContext, int paramInt) {
    super(paramContext);
    this.mThemeResource = paramInt;
  }
  
  public ContextThemeWrapper(Context paramContext, Resources.Theme paramTheme) {
    super(paramContext);
    this.mTheme = paramTheme;
  }
  
  private Resources getResourcesInternal() {
    if (this.mResources == null)
      if (this.mOverrideConfiguration == null) {
        this.mResources = super.getResources();
      } else if (Build.VERSION.SDK_INT >= 17) {
        this.mResources = createConfigurationContext(this.mOverrideConfiguration).getResources();
      }  
    return this.mResources;
  }
  
  private void initializeTheme() {
    boolean bool;
    if (this.mTheme == null) {
      bool = true;
    } else {
      bool = false;
    } 
    if (bool) {
      this.mTheme = getResources().newTheme();
      Resources.Theme theme = getBaseContext().getTheme();
      if (theme != null)
        this.mTheme.setTo(theme); 
    } 
    onApplyThemeResource(this.mTheme, this.mThemeResource, bool);
  }
  
  public void applyOverrideConfiguration(Configuration paramConfiguration) {
    if (this.mResources == null) {
      if (this.mOverrideConfiguration == null) {
        this.mOverrideConfiguration = new Configuration(paramConfiguration);
        return;
      } 
      throw new IllegalStateException("Override configuration has already been set");
    } 
    throw new IllegalStateException("getResources() or getAssets() has already been called");
  }
  
  protected void attachBaseContext(Context paramContext) {
    super.attachBaseContext(paramContext);
  }
  
  public AssetManager getAssets() {
    return getResources().getAssets();
  }
  
  public Resources getResources() {
    return getResourcesInternal();
  }
  
  public Object getSystemService(String paramString) {
    if ("layout_inflater".equals(paramString)) {
      if (this.mInflater == null)
        this.mInflater = LayoutInflater.from(getBaseContext()).cloneInContext((Context)this); 
      return this.mInflater;
    } 
    return getBaseContext().getSystemService(paramString);
  }
  
  public Resources.Theme getTheme() {
    Resources.Theme theme = this.mTheme;
    if (theme != null)
      return theme; 
    if (this.mThemeResource == 0)
      this.mThemeResource = R.style.Theme_AppCompat_Light; 
    initializeTheme();
    return this.mTheme;
  }
  
  public int getThemeResId() {
    return this.mThemeResource;
  }
  
  protected void onApplyThemeResource(Resources.Theme paramTheme, int paramInt, boolean paramBoolean) {
    paramTheme.applyStyle(paramInt, true);
  }
  
  public void setTheme(int paramInt) {
    if (this.mThemeResource != paramInt) {
      this.mThemeResource = paramInt;
      initializeTheme();
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\view\ContextThemeWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */