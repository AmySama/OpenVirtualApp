package android.support.v4.widget;

import android.os.Build;
import android.view.View;
import android.widget.PopupMenu;

public final class PopupMenuCompat {
  public static View.OnTouchListener getDragToOpenListener(Object paramObject) {
    return (Build.VERSION.SDK_INT >= 19) ? ((PopupMenu)paramObject).getDragToOpenListener() : null;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\widget\PopupMenuCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */