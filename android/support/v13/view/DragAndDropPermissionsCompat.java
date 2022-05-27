package android.support.v13.view;

import android.app.Activity;
import android.os.Build;
import android.view.DragAndDropPermissions;
import android.view.DragEvent;

public final class DragAndDropPermissionsCompat {
  private Object mDragAndDropPermissions;
  
  private DragAndDropPermissionsCompat(Object paramObject) {
    this.mDragAndDropPermissions = paramObject;
  }
  
  public static DragAndDropPermissionsCompat request(Activity paramActivity, DragEvent paramDragEvent) {
    if (Build.VERSION.SDK_INT >= 24) {
      DragAndDropPermissions dragAndDropPermissions = paramActivity.requestDragAndDropPermissions(paramDragEvent);
      if (dragAndDropPermissions != null)
        return new DragAndDropPermissionsCompat(dragAndDropPermissions); 
    } 
    return null;
  }
  
  public void release() {
    if (Build.VERSION.SDK_INT >= 24)
      ((DragAndDropPermissions)this.mDragAndDropPermissions).release(); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v13\view\DragAndDropPermissionsCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */