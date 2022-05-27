package android.support.v4.os;

import java.util.Locale;

final class LocaleHelper {
  static Locale forLanguageTag(String paramString) {
    if (paramString.contains("-")) {
      String[] arrayOfString = paramString.split("-");
      if (arrayOfString.length > 2)
        return new Locale(arrayOfString[0], arrayOfString[1], arrayOfString[2]); 
      if (arrayOfString.length > 1)
        return new Locale(arrayOfString[0], arrayOfString[1]); 
      if (arrayOfString.length == 1)
        return new Locale(arrayOfString[0]); 
    } else {
      if (paramString.contains("_")) {
        String[] arrayOfString = paramString.split("_");
        if (arrayOfString.length > 2)
          return new Locale(arrayOfString[0], arrayOfString[1], arrayOfString[2]); 
        if (arrayOfString.length > 1)
          return new Locale(arrayOfString[0], arrayOfString[1]); 
        if (arrayOfString.length == 1)
          return new Locale(arrayOfString[0]); 
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Can not parse language tag: [");
        stringBuilder1.append(paramString);
        stringBuilder1.append("]");
        throw new IllegalArgumentException(stringBuilder1.toString());
      } 
      return new Locale(paramString);
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Can not parse language tag: [");
    stringBuilder.append(paramString);
    stringBuilder.append("]");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  static String toLanguageTag(Locale paramLocale) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramLocale.getLanguage());
    String str = paramLocale.getCountry();
    if (str != null && !str.isEmpty()) {
      stringBuilder.append("-");
      stringBuilder.append(paramLocale.getCountry());
    } 
    return stringBuilder.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\os\LocaleHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */