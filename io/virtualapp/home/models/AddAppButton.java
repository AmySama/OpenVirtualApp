package io.virtualapp.home.models;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class AddAppButton extends AppData {
  private Drawable icon;
  
  private String name;
  
  public AddAppButton(Context paramContext) {
    this.name = paramContext.getResources().getString(2131623974);
    this.icon = paramContext.getResources().getDrawable(2131230944);
  }
  
  public boolean canCreateShortcut() {
    return false;
  }
  
  public boolean canDelete() {
    return false;
  }
  
  public boolean canLaunch() {
    return false;
  }
  
  public boolean canReorder() {
    return false;
  }
  
  public Drawable getIcon() {
    return this.icon;
  }
  
  public String getName() {
    return this.name;
  }
  
  public String getPackageName() {
    return null;
  }
  
  public int getUserId() {
    return -1;
  }
  
  public boolean isFirstOpen() {
    return false;
  }
  
  public boolean isLoading() {
    return false;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\models\AddAppButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */