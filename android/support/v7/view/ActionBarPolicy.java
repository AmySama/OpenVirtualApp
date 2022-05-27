package android.support.v7.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.appcompat.R;
import android.view.ViewConfiguration;

public class ActionBarPolicy {
  private Context mContext;
  
  private ActionBarPolicy(Context paramContext) {
    this.mContext = paramContext;
  }
  
  public static ActionBarPolicy get(Context paramContext) {
    return new ActionBarPolicy(paramContext);
  }
  
  public boolean enableHomeButtonByDefault() {
    boolean bool;
    if ((this.mContext.getApplicationInfo()).targetSdkVersion < 14) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public int getEmbeddedMenuWidthLimit() {
    return (this.mContext.getResources().getDisplayMetrics()).widthPixels / 2;
  }
  
  public int getMaxActionButtons() {
    Configuration configuration = this.mContext.getResources().getConfiguration();
    int i = configuration.screenWidthDp;
    int j = configuration.screenHeightDp;
    return (configuration.smallestScreenWidthDp > 600 || i > 600 || (i > 960 && j > 720) || (i > 720 && j > 960)) ? 5 : ((i >= 500 || (i > 640 && j > 480) || (i > 480 && j > 640)) ? 4 : ((i >= 360) ? 3 : 2));
  }
  
  public int getStackedTabMaxWidth() {
    return this.mContext.getResources().getDimensionPixelSize(R.dimen.abc_action_bar_stacked_tab_max_width);
  }
  
  public int getTabContainerHeight() {
    TypedArray typedArray = this.mContext.obtainStyledAttributes(null, R.styleable.ActionBar, R.attr.actionBarStyle, 0);
    int i = typedArray.getLayoutDimension(R.styleable.ActionBar_height, 0);
    Resources resources = this.mContext.getResources();
    int j = i;
    if (!hasEmbeddedTabs())
      j = Math.min(i, resources.getDimensionPixelSize(R.dimen.abc_action_bar_stacked_max_height)); 
    typedArray.recycle();
    return j;
  }
  
  public boolean hasEmbeddedTabs() {
    return this.mContext.getResources().getBoolean(R.bool.abc_action_bar_embed_tabs);
  }
  
  public boolean showsOverflowMenuButton() {
    return (Build.VERSION.SDK_INT >= 19) ? true : (ViewConfiguration.get(this.mContext).hasPermanentMenuKey() ^ true);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\view\ActionBarPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */