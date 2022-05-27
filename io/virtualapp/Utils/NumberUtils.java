package io.virtualapp.Utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

public class NumberUtils {
  public static String format3(double paramDouble) {
    NumberFormat numberFormat = NumberFormat.getNumberInstance();
    numberFormat.setMaximumFractionDigits(2);
    numberFormat.setMinimumFractionDigits(2);
    numberFormat.setRoundingMode(RoundingMode.HALF_UP);
    numberFormat.setGroupingUsed(false);
    return numberFormat.format(paramDouble);
  }
  
  public static String format4(double paramDouble) {
    return (new DecimalFormat(",###")).format(paramDouble);
  }
  
  public static String format5(double paramDouble) {
    NumberFormat numberFormat = NumberFormat.getNumberInstance();
    numberFormat.setMaximumFractionDigits(2);
    numberFormat.setMinimumFractionDigits(2);
    numberFormat.setRoundingMode(RoundingMode.HALF_UP);
    numberFormat.setGroupingUsed(true);
    return numberFormat.format(paramDouble);
  }
  
  public static String getRandom(int paramInt, boolean paramBoolean) {
    StringBuilder stringBuilder1;
    Random random = new Random();
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append(random.nextInt(paramInt ^ 0xA));
    stringBuilder2.append("");
    String str2 = stringBuilder2.toString();
    String str1 = str2;
    if (str2.length() < paramInt) {
      str1 = str2;
      if (paramBoolean) {
        byte b = 0;
        while (true) {
          str1 = str2;
          if (b < paramInt - str2.length()) {
            stringBuilder1 = new StringBuilder();
            stringBuilder1.append("0");
            stringBuilder1.append(str2);
            str2 = stringBuilder1.toString();
            b++;
            continue;
          } 
          break;
        } 
      } 
    } 
    return (String)stringBuilder1;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\Utils\NumberUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */