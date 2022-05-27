package android.support.v4.view;

import android.content.Context;
import android.util.Log;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

public abstract class ActionProvider {
  private static final String TAG = "ActionProvider(support)";
  
  private final Context mContext;
  
  private SubUiVisibilityListener mSubUiVisibilityListener;
  
  private VisibilityListener mVisibilityListener;
  
  public ActionProvider(Context paramContext) {
    this.mContext = paramContext;
  }
  
  public Context getContext() {
    return this.mContext;
  }
  
  public boolean hasSubMenu() {
    return false;
  }
  
  public boolean isVisible() {
    return true;
  }
  
  public abstract View onCreateActionView();
  
  public View onCreateActionView(MenuItem paramMenuItem) {
    return onCreateActionView();
  }
  
  public boolean onPerformDefaultAction() {
    return false;
  }
  
  public void onPrepareSubMenu(SubMenu paramSubMenu) {}
  
  public boolean overridesItemVisibility() {
    return false;
  }
  
  public void refreshVisibility() {
    if (this.mVisibilityListener != null && overridesItemVisibility())
      this.mVisibilityListener.onActionProviderVisibilityChanged(isVisible()); 
  }
  
  public void reset() {
    this.mVisibilityListener = null;
    this.mSubUiVisibilityListener = null;
  }
  
  public void setSubUiVisibilityListener(SubUiVisibilityListener paramSubUiVisibilityListener) {
    this.mSubUiVisibilityListener = paramSubUiVisibilityListener;
  }
  
  public void setVisibilityListener(VisibilityListener paramVisibilityListener) {
    if (this.mVisibilityListener != null && paramVisibilityListener != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("setVisibilityListener: Setting a new ActionProvider.VisibilityListener when one is already set. Are you reusing this ");
      stringBuilder.append(getClass().getSimpleName());
      stringBuilder.append(" instance while it is still in use somewhere else?");
      Log.w("ActionProvider(support)", stringBuilder.toString());
    } 
    this.mVisibilityListener = paramVisibilityListener;
  }
  
  public void subUiVisibilityChanged(boolean paramBoolean) {
    SubUiVisibilityListener subUiVisibilityListener = this.mSubUiVisibilityListener;
    if (subUiVisibilityListener != null)
      subUiVisibilityListener.onSubUiVisibilityChanged(paramBoolean); 
  }
  
  public static interface SubUiVisibilityListener {
    void onSubUiVisibilityChanged(boolean param1Boolean);
  }
  
  public static interface VisibilityListener {
    void onActionProviderVisibilityChanged(boolean param1Boolean);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\view\ActionProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */