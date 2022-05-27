package android.support.v4.os;

import android.content.res.Configuration;
import android.os.Build;
import java.util.Locale;

public final class ConfigurationCompat {
  public static LocaleListCompat getLocales(Configuration paramConfiguration) {
    return (Build.VERSION.SDK_INT >= 24) ? LocaleListCompat.wrap(paramConfiguration.getLocales()) : LocaleListCompat.create(new Locale[] { paramConfiguration.locale });
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\os\ConfigurationCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */