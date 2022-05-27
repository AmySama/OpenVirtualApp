package io.virtualapp.home;

import android.app.Activity;
import java.util.ArrayList;
import java.util.List;

public class AtyContainer {
  private static List<Activity> activityStack;
  
  private static AtyContainer instance = new AtyContainer();
  
  static {
    activityStack = new ArrayList<Activity>();
  }
  
  public static void finishAllActivity() {
    int i = activityStack.size();
    for (byte b = 0; b < i; b++) {
      if (activityStack.get(b) != null)
        ((Activity)activityStack.get(b)).finish(); 
    } 
    activityStack.clear();
  }
  
  public static AtyContainer getInstance() {
    return instance;
  }
  
  public void addActivity(Activity paramActivity) {
    activityStack.add(paramActivity);
  }
  
  public void removeActivity(Activity paramActivity) {
    activityStack.remove(paramActivity);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\home\AtyContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */