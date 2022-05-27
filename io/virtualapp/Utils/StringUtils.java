package io.virtualapp.Utils;

import android.content.Context;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class StringUtils {
  private static boolean isTencentMap = true;
  
  public static double doubleFor6(double paramDouble) {
    return isTencentMap ? paramDouble : Double.parseDouble(doubleFor6String(paramDouble));
  }
  
  public static String doubleFor6String(double paramDouble) {
    if (isTencentMap)
      return String.valueOf(paramDouble); 
    DecimalFormat decimalFormat = new DecimalFormat();
    decimalFormat.setMaximumFractionDigits(6);
    return decimalFormat.format(paramDouble);
  }
  
  public static double doubleFor8(double paramDouble) {
    return isTencentMap ? paramDouble : Double.parseDouble(doubleFor8String(paramDouble));
  }
  
  public static String doubleFor8String(double paramDouble) {
    if (isTencentMap)
      return String.valueOf(paramDouble); 
    DecimalFormat decimalFormat = new DecimalFormat();
    decimalFormat.setMaximumFractionDigits(8);
    return decimalFormat.format(paramDouble);
  }
  
  public static String getTime(long paramLong) {
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+0"));
    calendar.setTimeInMillis(paramLong);
    return String.format("%02d:%02d:%02d", new Object[] { Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)), Integer.valueOf(calendar.get(13)) });
  }
  
  public static String getTimeNoHour(long paramLong) {
    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+0"));
    calendar.setTimeInMillis(paramLong);
    return String.format("%02d:%02d", new Object[] { Integer.valueOf(calendar.get(11) * 60 + calendar.get(12)), Integer.valueOf(calendar.get(13)) });
  }
  
  public static boolean isString(String paramString) {
    return (paramString != null && !"".equals(paramString));
  }
  
  public static String timeForString(Context paramContext, long paramLong) {
    Calendar calendar = Calendar.getInstance(Locale.getDefault());
    calendar.setTimeInMillis(paramLong);
    int i = calendar.get(7);
    String str = paramContext.getResources().getStringArray(2130903041)[i - 1];
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("yyyy-MM-dd [");
    stringBuilder.append(str);
    stringBuilder.append("] hh:mm");
    return (new SimpleDateFormat(stringBuilder.toString())).format(Long.valueOf(paramLong));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\Utils\StringUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */