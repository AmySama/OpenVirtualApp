package external.org.apache.commons.lang3;

public class CharUtils {
  private static final String[] CHAR_STRING_ARRAY = new String[128];
  
  public static final char CR = '\r';
  
  public static final char LF = '\n';
  
  static {
    char c1 = Character.MIN_VALUE;
    char c2 = c1;
    while (true) {
      String[] arrayOfString = CHAR_STRING_ARRAY;
      if (c2 < arrayOfString.length) {
        arrayOfString[c2] = String.valueOf(c2);
        c1 = (char)(c2 + 1);
        c2 = c1;
        continue;
      } 
      break;
    } 
  }
  
  public static boolean isAscii(char paramChar) {
    boolean bool;
    if (paramChar < '') {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isAsciiAlpha(char paramChar) {
    boolean bool;
    if ((paramChar >= 'A' && paramChar <= 'Z') || (paramChar >= 'a' && paramChar <= 'z')) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isAsciiAlphaLower(char paramChar) {
    boolean bool;
    if (paramChar >= 'a' && paramChar <= 'z') {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isAsciiAlphaUpper(char paramChar) {
    boolean bool;
    if (paramChar >= 'A' && paramChar <= 'Z') {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isAsciiAlphanumeric(char paramChar) {
    boolean bool;
    if ((paramChar >= 'A' && paramChar <= 'Z') || (paramChar >= 'a' && paramChar <= 'z') || (paramChar >= '0' && paramChar <= '9')) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isAsciiControl(char paramChar) {
    return (paramChar < ' ' || paramChar == '');
  }
  
  public static boolean isAsciiNumeric(char paramChar) {
    boolean bool;
    if (paramChar >= '0' && paramChar <= '9') {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static boolean isAsciiPrintable(char paramChar) {
    boolean bool;
    if (paramChar >= ' ' && paramChar < '') {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static char toChar(Character paramCharacter) {
    if (paramCharacter != null)
      return paramCharacter.charValue(); 
    throw new IllegalArgumentException("The Character must not be null");
  }
  
  public static char toChar(Character paramCharacter, char paramChar) {
    return (paramCharacter == null) ? paramChar : paramCharacter.charValue();
  }
  
  public static char toChar(String paramString) {
    if (!StringUtils.isEmpty(paramString))
      return paramString.charAt(0); 
    throw new IllegalArgumentException("The String must not be empty");
  }
  
  public static char toChar(String paramString, char paramChar) {
    return StringUtils.isEmpty(paramString) ? paramChar : paramString.charAt(0);
  }
  
  @Deprecated
  public static Character toCharacterObject(char paramChar) {
    return Character.valueOf(paramChar);
  }
  
  public static Character toCharacterObject(String paramString) {
    return StringUtils.isEmpty(paramString) ? null : Character.valueOf(paramString.charAt(0));
  }
  
  public static int toIntValue(char paramChar) {
    if (isAsciiNumeric(paramChar))
      return paramChar - 48; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("The character ");
    stringBuilder.append(paramChar);
    stringBuilder.append(" is not in the range '0' - '9'");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public static int toIntValue(char paramChar, int paramInt) {
    return !isAsciiNumeric(paramChar) ? paramInt : (paramChar - 48);
  }
  
  public static int toIntValue(Character paramCharacter) {
    if (paramCharacter != null)
      return toIntValue(paramCharacter.charValue()); 
    throw new IllegalArgumentException("The character must not be null");
  }
  
  public static int toIntValue(Character paramCharacter, int paramInt) {
    return (paramCharacter == null) ? paramInt : toIntValue(paramCharacter.charValue(), paramInt);
  }
  
  public static String toString(char paramChar) {
    return (paramChar < '') ? CHAR_STRING_ARRAY[paramChar] : new String(new char[] { paramChar });
  }
  
  public static String toString(Character paramCharacter) {
    return (paramCharacter == null) ? null : toString(paramCharacter.charValue());
  }
  
  public static String unicodeEscaped(char paramChar) {
    if (paramChar < '\020') {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("\\u000");
      stringBuilder1.append(Integer.toHexString(paramChar));
      return stringBuilder1.toString();
    } 
    if (paramChar < 'Ā') {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("\\u00");
      stringBuilder1.append(Integer.toHexString(paramChar));
      return stringBuilder1.toString();
    } 
    if (paramChar < 'က') {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("\\u0");
      stringBuilder1.append(Integer.toHexString(paramChar));
      return stringBuilder1.toString();
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("\\u");
    stringBuilder.append(Integer.toHexString(paramChar));
    return stringBuilder.toString();
  }
  
  public static String unicodeEscaped(Character paramCharacter) {
    return (paramCharacter == null) ? null : unicodeEscaped(paramCharacter.charValue());
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\external\org\apache\commons\lang3\CharUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */