package android.support.v4.content.res;

import android.content.res.Resources;
import android.os.Build;

public final class ConfigurationHelper {
  public static int getDensityDpi(Resources paramResources) {
    return (Build.VERSION.SDK_INT >= 17) ? (paramResources.getConfiguration()).densityDpi : (paramResources.getDisplayMetrics()).densityDpi;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\content\res\ConfigurationHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */