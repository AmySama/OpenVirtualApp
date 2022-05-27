package io.virtualapp.home.models;

import android.graphics.drawable.Drawable;

public class EmptyAppData extends AppData {
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
    return null;
  }
  
  public String getName() {
    return null;
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


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\models\EmptyAppData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */