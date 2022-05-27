package android.support.multidex;

import android.app.Application;
import android.content.Context;

public class MultiDexApplication extends Application {
  protected void attachBaseContext(Context paramContext) {
    super.attachBaseContext(paramContext);
    MultiDex.install((Context)this);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\multidex\MultiDexApplication.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */