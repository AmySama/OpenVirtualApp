package io.virtualapp.Utils;

import android.text.TextUtils;
import android.widget.Toast;
import com.stub.StubApp;
import io.virtualapp.App;

public class ToastUtil {
  private static String oldMsg;
  
  private static long oneTime;
  
  protected static Toast toast;
  
  private static long twoTime;
  
  public static void cancelToast() {
    Toast toast = toast;
    if (toast != null)
      toast.cancel(); 
  }
  
  public static void showToast(int paramInt) {
    showToast(StubApp.getOrigApplicationContext(App.getApp().getApplicationContext()).getString(paramInt));
  }
  
  public static void showToast(String paramString) {
    Toast toast;
    if (TextUtils.isEmpty(paramString))
      return; 
    if (toast == null) {
      toast = Toast.makeText(StubApp.getOrigApplicationContext(App.getApp().getApplicationContext()), paramString, 0);
      toast = toast;
      toast.show();
      oneTime = System.currentTimeMillis();
    } else {
      twoTime = System.currentTimeMillis();
      if (toast.equals(oldMsg)) {
        if (twoTime - oneTime > 0L)
          toast.show(); 
      } else {
        oldMsg = (String)toast;
        toast.setText((CharSequence)toast);
        toast.show();
      } 
    } 
    oneTime = twoTime;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\Utils\ToastUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */