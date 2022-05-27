package io.virtualapp.delegate;

import android.app.ActivityManager;
import com.lody.virtual.client.hook.delegate.TaskDescriptionDelegate;
import com.lody.virtual.os.VUserManager;

public class MyTaskDescDelegate implements TaskDescriptionDelegate {
  public ActivityManager.TaskDescription getTaskDescription(ActivityManager.TaskDescription paramTaskDescription) {
    String str1;
    if (paramTaskDescription == null)
      return null; 
    int i = VUserManager.get().getUserHandle();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(" (");
    stringBuilder.append(i + 1);
    stringBuilder.append(")");
    String str2 = stringBuilder.toString();
    if (paramTaskDescription.getLabel() != null) {
      str1 = paramTaskDescription.getLabel();
    } else {
      str1 = "";
    } 
    if (!str1.endsWith(str2)) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(paramTaskDescription.getLabel());
      stringBuilder1.append(str2);
      return new ActivityManager.TaskDescription(stringBuilder1.toString(), paramTaskDescription.getIcon(), paramTaskDescription.getPrimaryColor());
    } 
    return paramTaskDescription;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\delegate\MyTaskDescDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */