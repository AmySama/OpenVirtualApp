package external.org.apache.commons.lang3;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Pattern;

public class StringUtils {
  public static final String EMPTY = "";
  
  public static final int INDEX_NOT_FOUND = -1;
  
  private static final int PAD_LIMIT = 8192;
  
  private static final Pattern WHITESPACE_BLOCK = Pattern.compile("\\s+");
  
  public static String abbreviate(String paramString, int paramInt) {
    return abbreviate(paramString, 0, paramInt);
  }
  
  public static String abbreviate(String paramString, int paramInt1, int paramInt2) {
    if (paramString == null)
      return null; 
    if (paramInt2 >= 4) {
      if (paramString.length() <= paramInt2)
        return paramString; 
      int i = paramInt1;
      if (paramInt1 > paramString.length())
        i = paramString.length(); 
      int j = paramString.length();
      int k = paramInt2 - 3;
      paramInt1 = i;
      if (j - i < k)
        paramInt1 = paramString.length() - k; 
      if (paramInt1 <= 4) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(paramString.substring(0, k));
        stringBuilder.append("...");
        return stringBuilder.toString();
      } 
      if (paramInt2 >= 7) {
        if (paramInt2 + paramInt1 - 3 < paramString.length()) {
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("...");
          stringBuilder1.append(abbreviate(paramString.substring(paramInt1), k));
          return stringBuilder1.toString();
        } 
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("...");
        stringBuilder.append(paramString.substring(paramString.length() - k));
        return stringBuilder.toString();
      } 
      throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
    } 
    throw new IllegalArgumentException("Minimum abbreviation width is 4");
  }
  
  public static String abbreviateMiddle(String paramString1, String paramString2, int paramInt) {
    String str = paramString1;
    if (!isEmpty(paramString1))
      if (isEmpty(paramString2)) {
        str = paramString1;
      } else {
        str = paramString1;
        if (paramInt < paramString1.length())
          if (paramInt < paramString2.length() + 2) {
            str = paramString1;
          } else {
            int i = paramInt - paramString2.length();
            int j = i / 2;
            int k = paramString1.length();
            StringBuilder stringBuilder = new StringBuilder(paramInt);
            stringBuilder.append(paramString1.substring(0, i % 2 + j));
            stringBuilder.append(paramString2);
            stringBuilder.append(paramString1.substring(k - j));
            str = stringBuilder.toString();
          }  
      }  
    return str;
  }
  
  public static String capitalize(String paramString) {
    String str = paramString;
    if (paramString != null) {
      int i = paramString.length();
      if (i == 0) {
        str = paramString;
      } else {
        StringBuilder stringBuilder = new StringBuilder(i);
        stringBuilder.append(Character.toTitleCase(paramString.charAt(0)));
        stringBuilder.append(paramString.substring(1));
        str = stringBuilder.toString();
      } 
    } 
    return str;
  }
  
  public static String center(String paramString, int paramInt) {
    return center(paramString, paramInt, ' ');
  }
  
  public static String center(String paramString, int paramInt, char paramChar) {
    String str = paramString;
    if (paramString != null)
      if (paramInt <= 0) {
        str = paramString;
      } else {
        int i = paramString.length();
        int j = paramInt - i;
        if (j <= 0)
          return paramString; 
        str = rightPad(leftPad(paramString, i + j / 2, paramChar), paramInt, paramChar);
      }  
    return str;
  }
  
  public static String center(String paramString1, int paramInt, String paramString2) {
    String str = paramString1;
    if (paramString1 != null)
      if (paramInt <= 0) {
        str = paramString1;
      } else {
        str = paramString2;
        if (isEmpty(paramString2))
          str = " "; 
        int i = paramString1.length();
        int j = paramInt - i;
        if (j <= 0)
          return paramString1; 
        str = rightPad(leftPad(paramString1, i + j / 2, str), paramInt, str);
      }  
    return str;
  }
  
  public static String chomp(String paramString) {
    int i;
    if (isEmpty(paramString))
      return paramString; 
    if (paramString.length() == 1) {
      i = paramString.charAt(0);
      return (i == 13 || i == 10) ? "" : paramString;
    } 
    int j = paramString.length() - 1;
    char c = paramString.charAt(j);
    if (c == '\n') {
      i = j;
      if (paramString.charAt(j - 1) == '\r')
        i = j - 1; 
    } else {
      i = j;
      if (c != '\r')
        i = j + 1; 
    } 
    return paramString.substring(0, i);
  }
  
  @Deprecated
  public static String chomp(String paramString1, String paramString2) {
    return removeEnd(paramString1, paramString2);
  }
  
  public static String chop(String paramString) {
    if (paramString == null)
      return null; 
    int i = paramString.length();
    if (i < 2)
      return ""; 
    String str = paramString.substring(0, --i);
    if (paramString.charAt(i) == '\n')
      if (str.charAt(--i) == '\r')
        return str.substring(0, i);  
    return str;
  }
  
  public static boolean contains(CharSequence paramCharSequence, int paramInt) {
    boolean bool = isEmpty(paramCharSequence);
    boolean bool1 = false;
    if (bool)
      return false; 
    if (CharSequenceUtils.indexOf(paramCharSequence, paramInt, 0) >= 0)
      bool1 = true; 
    return bool1;
  }
  
  public static boolean contains(CharSequence paramCharSequence1, CharSequence paramCharSequence2) {
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (paramCharSequence1 != null)
      if (paramCharSequence2 == null) {
        bool2 = bool1;
      } else {
        bool2 = bool1;
        if (CharSequenceUtils.indexOf(paramCharSequence1, paramCharSequence2, 0) >= 0)
          bool2 = true; 
      }  
    return bool2;
  }
  
  public static boolean containsAny(CharSequence paramCharSequence1, CharSequence paramCharSequence2) {
    return (paramCharSequence2 == null) ? false : containsAny(paramCharSequence1, CharSequenceUtils.toCharArray(paramCharSequence2));
  }
  
  public static boolean containsAny(CharSequence paramCharSequence, char... paramVarArgs) {
    if (!isEmpty(paramCharSequence) && !ArrayUtils.isEmpty(paramVarArgs)) {
      int i = paramCharSequence.length();
      int j = paramVarArgs.length;
      for (byte b = 0; b < i; b++) {
        char c = paramCharSequence.charAt(b);
        for (byte b1 = 0; b1 < j; b1++) {
          if (paramVarArgs[b1] == c)
            if (Character.isHighSurrogate(c)) {
              if (b1 == j - 1)
                return true; 
              if (b < i - 1 && paramVarArgs[b1 + 1] == paramCharSequence.charAt(b + 1))
                return true; 
            } else {
              return true;
            }  
        } 
      } 
    } 
    return false;
  }
  
  public static boolean containsIgnoreCase(CharSequence paramCharSequence1, CharSequence paramCharSequence2) {
    if (paramCharSequence1 != null && paramCharSequence2 != null) {
      int i = paramCharSequence2.length();
      int j = paramCharSequence1.length();
      for (byte b = 0; b <= j - i; b++) {
        if (CharSequenceUtils.regionMatches(paramCharSequence1, true, b, paramCharSequence2, 0, i))
          return true; 
      } 
    } 
    return false;
  }
  
  public static boolean containsNone(CharSequence paramCharSequence, String paramString) {
    return (paramCharSequence == null || paramString == null) ? true : containsNone(paramCharSequence, paramString.toCharArray());
  }
  
  public static boolean containsNone(CharSequence paramCharSequence, char... paramVarArgs) {
    if (paramCharSequence != null && paramVarArgs != null) {
      int i = paramCharSequence.length();
      int j = paramVarArgs.length;
      for (byte b = 0; b < i; b++) {
        char c = paramCharSequence.charAt(b);
        for (byte b1 = 0; b1 < j; b1++) {
          if (paramVarArgs[b1] == c)
            if (Character.isHighSurrogate(c)) {
              if (b1 == j - 1)
                return false; 
              if (b < i - 1 && paramVarArgs[b1 + 1] == paramCharSequence.charAt(b + 1))
                return false; 
            } else {
              return false;
            }  
        } 
      } 
    } 
    return true;
  }
  
  public static boolean containsOnly(CharSequence paramCharSequence, String paramString) {
    return (paramCharSequence == null || paramString == null) ? false : containsOnly(paramCharSequence, paramString.toCharArray());
  }
  
  public static boolean containsOnly(CharSequence paramCharSequence, char... paramVarArgs) {
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (paramVarArgs != null)
      if (paramCharSequence == null) {
        bool2 = bool1;
      } else {
        if (paramCharSequence.length() == 0)
          return true; 
        if (paramVarArgs.length == 0)
          return false; 
        bool2 = bool1;
        if (indexOfAnyBut(paramCharSequence, paramVarArgs) == -1)
          bool2 = true; 
      }  
    return bool2;
  }
  
  public static boolean containsWhitespace(CharSequence paramCharSequence) {
    if (isEmpty(paramCharSequence))
      return false; 
    int i = paramCharSequence.length();
    for (byte b = 0; b < i; b++) {
      if (Character.isWhitespace(paramCharSequence.charAt(b)))
        return true; 
    } 
    return false;
  }
  
  public static int countMatches(CharSequence paramCharSequence1, CharSequence paramCharSequence2) {
    boolean bool = isEmpty(paramCharSequence1);
    int i = 0;
    if (bool || isEmpty(paramCharSequence2))
      return 0; 
    byte b = 0;
    while (true) {
      i = CharSequenceUtils.indexOf(paramCharSequence1, paramCharSequence2, i);
      if (i != -1) {
        b++;
        i += paramCharSequence2.length();
        continue;
      } 
      return b;
    } 
  }
  
  public static <T extends CharSequence> T defaultIfBlank(T paramT1, T paramT2) {
    T t = paramT1;
    if (isBlank((CharSequence)paramT1))
      t = paramT2; 
    return t;
  }
  
  public static <T extends CharSequence> T defaultIfEmpty(T paramT1, T paramT2) {
    T t = paramT1;
    if (isEmpty((CharSequence)paramT1))
      t = paramT2; 
    return t;
  }
  
  public static String defaultString(String paramString) {
    String str = paramString;
    if (paramString == null)
      str = ""; 
    return str;
  }
  
  public static String defaultString(String paramString1, String paramString2) {
    String str = paramString1;
    if (paramString1 == null)
      str = paramString2; 
    return str;
  }
  
  public static String deleteWhitespace(String paramString) {
    if (isEmpty(paramString))
      return paramString; 
    int i = paramString.length();
    char[] arrayOfChar = new char[i];
    byte b = 0;
    int j;
    for (j = 0; b < i; j = k) {
      int k = j;
      if (!Character.isWhitespace(paramString.charAt(b))) {
        arrayOfChar[j] = paramString.charAt(b);
        k = j + 1;
      } 
      b++;
    } 
    return (j == i) ? paramString : new String(arrayOfChar, 0, j);
  }
  
  public static String difference(String paramString1, String paramString2) {
    if (paramString1 == null)
      return paramString2; 
    if (paramString2 == null)
      return paramString1; 
    int i = indexOfDifference(paramString1, paramString2);
    return (i == -1) ? "" : paramString2.substring(i);
  }
  
  public static boolean endsWith(CharSequence paramCharSequence1, CharSequence paramCharSequence2) {
    return endsWith(paramCharSequence1, paramCharSequence2, false);
  }
  
  private static boolean endsWith(CharSequence paramCharSequence1, CharSequence paramCharSequence2, boolean paramBoolean) {
    boolean bool = false;
    if (paramCharSequence1 == null || paramCharSequence2 == null) {
      paramBoolean = bool;
      if (paramCharSequence1 == null) {
        paramBoolean = bool;
        if (paramCharSequence2 == null)
          paramBoolean = true; 
      } 
      return paramBoolean;
    } 
    return (paramCharSequence2.length() > paramCharSequence1.length()) ? false : CharSequenceUtils.regionMatches(paramCharSequence1, paramBoolean, paramCharSequence1.length() - paramCharSequence2.length(), paramCharSequence2, 0, paramCharSequence2.length());
  }
  
  public static boolean endsWithAny(CharSequence paramCharSequence, CharSequence... paramVarArgs) {
    if (!isEmpty(paramCharSequence) && !ArrayUtils.isEmpty((Object[])paramVarArgs)) {
      int i = paramVarArgs.length;
      for (byte b = 0; b < i; b++) {
        if (endsWith(paramCharSequence, paramVarArgs[b]))
          return true; 
      } 
    } 
    return false;
  }
  
  public static boolean endsWithIgnoreCase(CharSequence paramCharSequence1, CharSequence paramCharSequence2) {
    return endsWith(paramCharSequence1, paramCharSequence2, true);
  }
  
  public static boolean equals(CharSequence paramCharSequence1, CharSequence paramCharSequence2) {
    boolean bool;
    if (paramCharSequence1 == null) {
      if (paramCharSequence2 == null) {
        bool = true;
      } else {
        bool = false;
      } 
    } else {
      bool = paramCharSequence1.equals(paramCharSequence2);
    } 
    return bool;
  }
  
  public static boolean equalsIgnoreCase(CharSequence paramCharSequence1, CharSequence paramCharSequence2) {
    if (paramCharSequence1 == null || paramCharSequence2 == null) {
      boolean bool;
      if (paramCharSequence1 == paramCharSequence2) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    } 
    return CharSequenceUtils.regionMatches(paramCharSequence1, true, 0, paramCharSequence2, 0, Math.max(paramCharSequence1.length(), paramCharSequence2.length()));
  }
  
  public static String getCommonPrefix(String... paramVarArgs) {
    if (paramVarArgs == null || paramVarArgs.length == 0)
      return ""; 
    int i = indexOfDifference((CharSequence[])paramVarArgs);
    return (i == -1) ? ((paramVarArgs[0] == null) ? "" : paramVarArgs[0]) : ((i == 0) ? "" : paramVarArgs[0].substring(0, i));
  }
  
  public static int getLevenshteinDistance(CharSequence paramCharSequence1, CharSequence paramCharSequence2) {
    if (paramCharSequence1 != null && paramCharSequence2 != null) {
      CharSequence charSequence1;
      CharSequence charSequence2;
      int i = paramCharSequence1.length();
      int j = paramCharSequence2.length();
      if (i == 0)
        return j; 
      if (j == 0)
        return i; 
      if (i > j) {
        int m = paramCharSequence1.length();
        i = j;
        j = m;
        charSequence1 = paramCharSequence1;
        charSequence2 = paramCharSequence2;
      } else {
        charSequence2 = paramCharSequence1;
        charSequence1 = paramCharSequence2;
      } 
      int k = i + 1;
      int[] arrayOfInt2 = new int[k];
      int[] arrayOfInt1 = new int[k];
      for (k = 0; k <= i; k++)
        arrayOfInt2[k] = k; 
      k = 1;
      while (true) {
        int[] arrayOfInt = arrayOfInt2;
        if (k <= j) {
          char c = charSequence1.charAt(k - 1);
          arrayOfInt1[0] = k;
          for (byte b = 1; b <= i; b++) {
            byte b1;
            int m = b - 1;
            if (charSequence2.charAt(m) == c) {
              b1 = 0;
            } else {
              b1 = 1;
            } 
            arrayOfInt1[b] = Math.min(Math.min(arrayOfInt1[m] + 1, arrayOfInt[b] + 1), arrayOfInt[m] + b1);
          } 
          k++;
          arrayOfInt2 = arrayOfInt1;
          arrayOfInt1 = arrayOfInt;
          continue;
        } 
        return arrayOfInt[i];
      } 
    } 
    throw new IllegalArgumentException("Strings must not be null");
  }
  
  public static int getLevenshteinDistance(CharSequence paramCharSequence1, CharSequence paramCharSequence2, int paramInt) {
    if (paramCharSequence1 != null && paramCharSequence2 != null) {
      if (paramInt >= 0) {
        int k;
        CharSequence charSequence1;
        CharSequence charSequence2;
        int i = paramCharSequence1.length();
        int j = paramCharSequence2.length();
        if (i == 0) {
          if (j > paramInt)
            j = -1; 
          return j;
        } 
        if (j == 0) {
          if (i > paramInt)
            i = -1; 
          return i;
        } 
        if (i > j) {
          k = paramCharSequence1.length();
          charSequence1 = paramCharSequence1;
          charSequence2 = paramCharSequence2;
        } else {
          k = j;
          j = i;
          charSequence2 = paramCharSequence1;
          charSequence1 = paramCharSequence2;
        } 
        int m = j + 1;
        int[] arrayOfInt1 = new int[m];
        int[] arrayOfInt2 = new int[m];
        int n = Math.min(j, paramInt) + 1;
        for (i = 0; i < n; i++)
          arrayOfInt1[i] = i; 
        Arrays.fill(arrayOfInt1, n, m, 2147483647);
        Arrays.fill(arrayOfInt2, 2147483647);
        i = 1;
        while (true) {
          int[] arrayOfInt = arrayOfInt2;
          if (i <= k) {
            char c = charSequence1.charAt(i - 1);
            arrayOfInt[0] = i;
            n = Math.max(1, i - paramInt);
            int i1 = Math.min(j, i + paramInt);
            if (n > i1)
              return -1; 
            m = n;
            if (n > 1) {
              arrayOfInt[n - 1] = Integer.MAX_VALUE;
              m = n;
            } 
            while (m <= i1) {
              n = m - 1;
              if (charSequence2.charAt(n) == c) {
                arrayOfInt[m] = arrayOfInt1[n];
              } else {
                arrayOfInt[m] = Math.min(Math.min(arrayOfInt[n], arrayOfInt1[m]), arrayOfInt1[n]) + 1;
              } 
              m++;
            } 
            i++;
            arrayOfInt2 = arrayOfInt1;
            arrayOfInt1 = arrayOfInt;
            continue;
          } 
          return (arrayOfInt1[j] <= paramInt) ? arrayOfInt1[j] : -1;
        } 
      } 
      throw new IllegalArgumentException("Threshold must not be negative");
    } 
    throw new IllegalArgumentException("Strings must not be null");
  }
  
  public static int indexOf(CharSequence paramCharSequence, int paramInt) {
    return isEmpty(paramCharSequence) ? -1 : CharSequenceUtils.indexOf(paramCharSequence, paramInt, 0);
  }
  
  public static int indexOf(CharSequence paramCharSequence, int paramInt1, int paramInt2) {
    return isEmpty(paramCharSequence) ? -1 : CharSequenceUtils.indexOf(paramCharSequence, paramInt1, paramInt2);
  }
  
  public static int indexOf(CharSequence paramCharSequence1, CharSequence paramCharSequence2) {
    return (paramCharSequence1 == null || paramCharSequence2 == null) ? -1 : CharSequenceUtils.indexOf(paramCharSequence1, paramCharSequence2, 0);
  }
  
  public static int indexOf(CharSequence paramCharSequence1, CharSequence paramCharSequence2, int paramInt) {
    return (paramCharSequence1 == null || paramCharSequence2 == null) ? -1 : CharSequenceUtils.indexOf(paramCharSequence1, paramCharSequence2, paramInt);
  }
  
  public static int indexOfAny(CharSequence paramCharSequence, String paramString) {
    return (isEmpty(paramCharSequence) || isEmpty(paramString)) ? -1 : indexOfAny(paramCharSequence, paramString.toCharArray());
  }
  
  public static int indexOfAny(CharSequence paramCharSequence, char... paramVarArgs) {
    if (!isEmpty(paramCharSequence) && !ArrayUtils.isEmpty(paramVarArgs)) {
      int i = paramCharSequence.length();
      int j = paramVarArgs.length;
      for (byte b = 0; b < i; b++) {
        char c = paramCharSequence.charAt(b);
        for (byte b1 = 0; b1 < j; b1++) {
          if (paramVarArgs[b1] == c && (b >= i - 1 || b1 >= j - 1 || !Character.isHighSurrogate(c) || paramVarArgs[b1 + 1] == paramCharSequence.charAt(b + 1)))
            return b; 
        } 
      } 
    } 
    return -1;
  }
  
  public static int indexOfAny(CharSequence paramCharSequence, CharSequence... paramVarArgs) {
    byte b = -1;
    int i = b;
    if (paramCharSequence != null)
      if (paramVarArgs == null) {
        i = b;
      } else {
        int j = paramVarArgs.length;
        byte b1 = 0;
        for (i = Integer.MAX_VALUE; b1 < j; i = k) {
          int k;
          CharSequence charSequence = paramVarArgs[b1];
          if (charSequence == null) {
            k = i;
          } else {
            int m = CharSequenceUtils.indexOf(paramCharSequence, charSequence, 0);
            if (m == -1) {
              k = i;
            } else {
              k = i;
              if (m < i)
                k = m; 
            } 
          } 
          b1++;
        } 
        if (i == Integer.MAX_VALUE)
          i = b; 
      }  
    return i;
  }
  
  public static int indexOfAnyBut(CharSequence paramCharSequence1, CharSequence paramCharSequence2) {
    if (!isEmpty(paramCharSequence1) && !isEmpty(paramCharSequence2)) {
      int i = paramCharSequence1.length();
      for (int j = 0; j < i; j = k) {
        boolean bool;
        char c = paramCharSequence1.charAt(j);
        if (CharSequenceUtils.indexOf(paramCharSequence2, c, 0) >= 0) {
          bool = true;
        } else {
          bool = false;
        } 
        int k = j + 1;
        if (k < i && Character.isHighSurrogate(c)) {
          char c1 = paramCharSequence1.charAt(k);
          if (bool && CharSequenceUtils.indexOf(paramCharSequence2, c1, 0) < 0)
            return j; 
        } else if (!bool) {
          return j;
        } 
      } 
    } 
    return -1;
  }
  
  public static int indexOfAnyBut(CharSequence paramCharSequence, char... paramVarArgs) {
    if (!isEmpty(paramCharSequence) && !ArrayUtils.isEmpty(paramVarArgs)) {
      int i = paramCharSequence.length();
      int j = paramVarArgs.length;
      byte b = 0;
      label21: while (b < i) {
        char c = paramCharSequence.charAt(b);
        for (byte b1 = 0; b1 < j; b1++) {
          if (paramVarArgs[b1] == c && (b >= i - 1 || b1 >= j - 1 || !Character.isHighSurrogate(c) || paramVarArgs[b1 + 1] == paramCharSequence.charAt(b + 1))) {
            b++;
            continue label21;
          } 
        } 
        return b;
      } 
    } 
    return -1;
  }
  
  public static int indexOfDifference(CharSequence paramCharSequence1, CharSequence paramCharSequence2) {
    if (paramCharSequence1 == paramCharSequence2)
      return -1; 
    byte b1 = 0;
    byte b2 = 0;
    byte b3 = b1;
    if (paramCharSequence1 != null)
      if (paramCharSequence2 == null) {
        b3 = b1;
      } else {
        while (b2 < paramCharSequence1.length() && b2 < paramCharSequence2.length() && paramCharSequence1.charAt(b2) == paramCharSequence2.charAt(b2))
          b2++; 
        b3 = b2;
        if (b2 >= paramCharSequence2.length())
          if (b2 < paramCharSequence1.length()) {
            b3 = b2;
          } else {
            return -1;
          }  
      }  
    return b3;
  }
  
  public static int indexOfDifference(CharSequence... paramVarArgs) {
    if (paramVarArgs != null && paramVarArgs.length > 1) {
      int i = paramVarArgs.length;
      int j = Integer.MAX_VALUE;
      byte b1 = 0;
      byte b = 1;
      int k = 0;
      byte b2 = 0;
      while (b1 < i) {
        int m;
        if (paramVarArgs[b1] == null) {
          m = 0;
          b2 = 1;
        } else {
          m = Math.min(paramVarArgs[b1].length(), j);
          k = Math.max(paramVarArgs[b1].length(), k);
          b = 0;
        } 
        b1++;
        j = m;
      } 
      if (!b && (k != 0 || b2)) {
        if (j == 0)
          return 0; 
        byte b3 = 0;
        b1 = -1;
        while (true) {
          b2 = b1;
          if (b3 < j) {
            char c = paramVarArgs[0].charAt(b3);
            b = 1;
            while (true) {
              b2 = b1;
              if (b < i) {
                if (paramVarArgs[b].charAt(b3) != c) {
                  b2 = b3;
                  break;
                } 
                b++;
                continue;
              } 
              break;
            } 
            if (b2 != -1)
              break; 
            b3++;
            b1 = b2;
            continue;
          } 
          break;
        } 
        return (b2 == -1 && j != k) ? j : b2;
      } 
    } 
    return -1;
  }
  
  public static int indexOfIgnoreCase(CharSequence paramCharSequence1, CharSequence paramCharSequence2) {
    return indexOfIgnoreCase(paramCharSequence1, paramCharSequence2, 0);
  }
  
  public static int indexOfIgnoreCase(CharSequence paramCharSequence1, CharSequence paramCharSequence2, int paramInt) {
    if (paramCharSequence1 != null && paramCharSequence2 != null) {
      int i = paramInt;
      if (paramInt < 0)
        i = 0; 
      int j = paramCharSequence1.length() - paramCharSequence2.length() + 1;
      if (i > j)
        return -1; 
      paramInt = i;
      if (paramCharSequence2.length() == 0)
        return i; 
      while (paramInt < j) {
        if (CharSequenceUtils.regionMatches(paramCharSequence1, true, paramInt, paramCharSequence2, 0, paramCharSequence2.length()))
          return paramInt; 
        paramInt++;
      } 
    } 
    return -1;
  }
  
  public static boolean isAllLowerCase(CharSequence paramCharSequence) {
    if (paramCharSequence == null || isEmpty(paramCharSequence))
      return false; 
    int i = paramCharSequence.length();
    for (byte b = 0; b < i; b++) {
      if (!Character.isLowerCase(paramCharSequence.charAt(b)))
        return false; 
    } 
    return true;
  }
  
  public static boolean isAllUpperCase(CharSequence paramCharSequence) {
    if (paramCharSequence == null || isEmpty(paramCharSequence))
      return false; 
    int i = paramCharSequence.length();
    for (byte b = 0; b < i; b++) {
      if (!Character.isUpperCase(paramCharSequence.charAt(b)))
        return false; 
    } 
    return true;
  }
  
  public static boolean isAlpha(CharSequence paramCharSequence) {
    if (paramCharSequence == null || paramCharSequence.length() == 0)
      return false; 
    int i = paramCharSequence.length();
    for (byte b = 0; b < i; b++) {
      if (!Character.isLetter(paramCharSequence.charAt(b)))
        return false; 
    } 
    return true;
  }
  
  public static boolean isAlphaSpace(CharSequence paramCharSequence) {
    if (paramCharSequence == null)
      return false; 
    int i = paramCharSequence.length();
    for (byte b = 0; b < i; b++) {
      if (!Character.isLetter(paramCharSequence.charAt(b)) && paramCharSequence.charAt(b) != ' ')
        return false; 
    } 
    return true;
  }
  
  public static boolean isAlphanumeric(CharSequence paramCharSequence) {
    if (paramCharSequence == null || paramCharSequence.length() == 0)
      return false; 
    int i = paramCharSequence.length();
    for (byte b = 0; b < i; b++) {
      if (!Character.isLetterOrDigit(paramCharSequence.charAt(b)))
        return false; 
    } 
    return true;
  }
  
  public static boolean isAlphanumericSpace(CharSequence paramCharSequence) {
    if (paramCharSequence == null)
      return false; 
    int i = paramCharSequence.length();
    for (byte b = 0; b < i; b++) {
      if (!Character.isLetterOrDigit(paramCharSequence.charAt(b)) && paramCharSequence.charAt(b) != ' ')
        return false; 
    } 
    return true;
  }
  
  public static boolean isAsciiPrintable(CharSequence paramCharSequence) {
    if (paramCharSequence == null)
      return false; 
    int i = paramCharSequence.length();
    for (byte b = 0; b < i; b++) {
      if (!CharUtils.isAsciiPrintable(paramCharSequence.charAt(b)))
        return false; 
    } 
    return true;
  }
  
  public static boolean isBlank(CharSequence paramCharSequence) {
    if (paramCharSequence != null) {
      int i = paramCharSequence.length();
      if (i != 0)
        for (byte b = 0; b < i; b++) {
          if (!Character.isWhitespace(paramCharSequence.charAt(b)))
            return false; 
        }  
    } 
    return true;
  }
  
  public static boolean isEmpty(CharSequence paramCharSequence) {
    return (paramCharSequence == null || paramCharSequence.length() == 0);
  }
  
  public static boolean isNotBlank(CharSequence paramCharSequence) {
    return isBlank(paramCharSequence) ^ true;
  }
  
  public static boolean isNotEmpty(CharSequence paramCharSequence) {
    return isEmpty(paramCharSequence) ^ true;
  }
  
  public static boolean isNumeric(CharSequence paramCharSequence) {
    if (paramCharSequence == null || paramCharSequence.length() == 0)
      return false; 
    int i = paramCharSequence.length();
    for (byte b = 0; b < i; b++) {
      if (!Character.isDigit(paramCharSequence.charAt(b)))
        return false; 
    } 
    return true;
  }
  
  public static boolean isNumericSpace(CharSequence paramCharSequence) {
    if (paramCharSequence == null)
      return false; 
    int i = paramCharSequence.length();
    for (byte b = 0; b < i; b++) {
      if (!Character.isDigit(paramCharSequence.charAt(b)) && paramCharSequence.charAt(b) != ' ')
        return false; 
    } 
    return true;
  }
  
  public static boolean isWhitespace(CharSequence paramCharSequence) {
    if (paramCharSequence == null)
      return false; 
    int i = paramCharSequence.length();
    for (byte b = 0; b < i; b++) {
      if (!Character.isWhitespace(paramCharSequence.charAt(b)))
        return false; 
    } 
    return true;
  }
  
  public static String join(Iterable<?> paramIterable, char paramChar) {
    return (paramIterable == null) ? null : join(paramIterable.iterator(), paramChar);
  }
  
  public static String join(Iterable<?> paramIterable, String paramString) {
    return (paramIterable == null) ? null : join(paramIterable.iterator(), paramString);
  }
  
  public static String join(Iterator<?> paramIterator, char paramChar) {
    if (paramIterator == null)
      return null; 
    if (!paramIterator.hasNext())
      return ""; 
    Object object = paramIterator.next();
    if (!paramIterator.hasNext())
      return ObjectUtils.toString(object); 
    StringBuilder stringBuilder = new StringBuilder(256);
    if (object != null)
      stringBuilder.append(object); 
    while (paramIterator.hasNext()) {
      stringBuilder.append(paramChar);
      object = paramIterator.next();
      if (object != null)
        stringBuilder.append(object); 
    } 
    return stringBuilder.toString();
  }
  
  public static String join(Iterator<?> paramIterator, String paramString) {
    if (paramIterator == null)
      return null; 
    if (!paramIterator.hasNext())
      return ""; 
    Object object = paramIterator.next();
    if (!paramIterator.hasNext())
      return ObjectUtils.toString(object); 
    StringBuilder stringBuilder = new StringBuilder(256);
    if (object != null)
      stringBuilder.append(object); 
    while (paramIterator.hasNext()) {
      if (paramString != null)
        stringBuilder.append(paramString); 
      object = paramIterator.next();
      if (object != null)
        stringBuilder.append(object); 
    } 
    return stringBuilder.toString();
  }
  
  public static <T> String join(T... paramVarArgs) {
    return join((Object[])paramVarArgs, (String)null);
  }
  
  public static String join(Object[] paramArrayOfObject, char paramChar) {
    return (paramArrayOfObject == null) ? null : join(paramArrayOfObject, paramChar, 0, paramArrayOfObject.length);
  }
  
  public static String join(Object[] paramArrayOfObject, char paramChar, int paramInt1, int paramInt2) {
    if (paramArrayOfObject == null)
      return null; 
    int i = paramInt2 - paramInt1;
    if (i <= 0)
      return ""; 
    StringBuilder stringBuilder = new StringBuilder(i * 16);
    for (i = paramInt1; i < paramInt2; i++) {
      if (i > paramInt1)
        stringBuilder.append(paramChar); 
      if (paramArrayOfObject[i] != null)
        stringBuilder.append(paramArrayOfObject[i]); 
    } 
    return stringBuilder.toString();
  }
  
  public static String join(Object[] paramArrayOfObject, String paramString) {
    return (paramArrayOfObject == null) ? null : join(paramArrayOfObject, paramString, 0, paramArrayOfObject.length);
  }
  
  public static String join(Object[] paramArrayOfObject, String paramString, int paramInt1, int paramInt2) {
    if (paramArrayOfObject == null)
      return null; 
    String str = paramString;
    if (paramString == null)
      str = ""; 
    int i = paramInt2 - paramInt1;
    if (i <= 0)
      return ""; 
    StringBuilder stringBuilder = new StringBuilder(i * 16);
    for (i = paramInt1; i < paramInt2; i++) {
      if (i > paramInt1)
        stringBuilder.append(str); 
      if (paramArrayOfObject[i] != null)
        stringBuilder.append(paramArrayOfObject[i]); 
    } 
    return stringBuilder.toString();
  }
  
  public static int lastIndexOf(CharSequence paramCharSequence, int paramInt) {
    return isEmpty(paramCharSequence) ? -1 : CharSequenceUtils.lastIndexOf(paramCharSequence, paramInt, paramCharSequence.length());
  }
  
  public static int lastIndexOf(CharSequence paramCharSequence, int paramInt1, int paramInt2) {
    return isEmpty(paramCharSequence) ? -1 : CharSequenceUtils.lastIndexOf(paramCharSequence, paramInt1, paramInt2);
  }
  
  public static int lastIndexOf(CharSequence paramCharSequence1, CharSequence paramCharSequence2) {
    return (paramCharSequence1 == null || paramCharSequence2 == null) ? -1 : CharSequenceUtils.lastIndexOf(paramCharSequence1, paramCharSequence2, paramCharSequence1.length());
  }
  
  public static int lastIndexOf(CharSequence paramCharSequence1, CharSequence paramCharSequence2, int paramInt) {
    return (paramCharSequence1 == null || paramCharSequence2 == null) ? -1 : CharSequenceUtils.lastIndexOf(paramCharSequence1, paramCharSequence2, paramInt);
  }
  
  public static int lastIndexOfAny(CharSequence paramCharSequence, CharSequence... paramVarArgs) {
    int i = -1;
    int j = i;
    if (paramCharSequence != null)
      if (paramVarArgs == null) {
        j = i;
      } else {
        int k = paramVarArgs.length;
        byte b = 0;
        while (true) {
          j = i;
          if (b < k) {
            CharSequence charSequence = paramVarArgs[b];
            if (charSequence == null) {
              j = i;
            } else {
              int m = CharSequenceUtils.lastIndexOf(paramCharSequence, charSequence, paramCharSequence.length());
              j = i;
              if (m > i)
                j = m; 
            } 
            b++;
            i = j;
            continue;
          } 
          break;
        } 
      }  
    return j;
  }
  
  public static int lastIndexOfIgnoreCase(CharSequence paramCharSequence1, CharSequence paramCharSequence2) {
    return (paramCharSequence1 == null || paramCharSequence2 == null) ? -1 : lastIndexOfIgnoreCase(paramCharSequence1, paramCharSequence2, paramCharSequence1.length());
  }
  
  public static int lastIndexOfIgnoreCase(CharSequence paramCharSequence1, CharSequence paramCharSequence2, int paramInt) {
    if (paramCharSequence1 != null && paramCharSequence2 != null) {
      int i = paramInt;
      if (paramInt > paramCharSequence1.length() - paramCharSequence2.length())
        i = paramCharSequence1.length() - paramCharSequence2.length(); 
      if (i < 0)
        return -1; 
      paramInt = i;
      if (paramCharSequence2.length() == 0)
        return i; 
      while (paramInt >= 0) {
        if (CharSequenceUtils.regionMatches(paramCharSequence1, true, paramInt, paramCharSequence2, 0, paramCharSequence2.length()))
          return paramInt; 
        paramInt--;
      } 
    } 
    return -1;
  }
  
  public static int lastOrdinalIndexOf(CharSequence paramCharSequence1, CharSequence paramCharSequence2, int paramInt) {
    return ordinalIndexOf(paramCharSequence1, paramCharSequence2, paramInt, true);
  }
  
  public static String left(String paramString, int paramInt) {
    return (paramString == null) ? null : ((paramInt < 0) ? "" : ((paramString.length() <= paramInt) ? paramString : paramString.substring(0, paramInt)));
  }
  
  public static String leftPad(String paramString, int paramInt) {
    return leftPad(paramString, paramInt, ' ');
  }
  
  public static String leftPad(String paramString, int paramInt, char paramChar) {
    if (paramString == null)
      return null; 
    int i = paramInt - paramString.length();
    return (i <= 0) ? paramString : ((i > 8192) ? leftPad(paramString, paramInt, String.valueOf(paramChar)) : repeat(paramChar, i).concat(paramString));
  }
  
  public static String leftPad(String paramString1, int paramInt, String paramString2) {
    if (paramString1 == null)
      return null; 
    String str = paramString2;
    if (isEmpty(paramString2))
      str = " "; 
    int i = str.length();
    int j = paramInt - paramString1.length();
    if (j <= 0)
      return paramString1; 
    boolean bool = false;
    if (i == 1 && j <= 8192)
      return leftPad(paramString1, paramInt, str.charAt(0)); 
    if (j == i)
      return str.concat(paramString1); 
    if (j < i)
      return str.substring(0, j).concat(paramString1); 
    char[] arrayOfChar1 = new char[j];
    char[] arrayOfChar2 = str.toCharArray();
    for (paramInt = bool; paramInt < j; paramInt++)
      arrayOfChar1[paramInt] = (char)arrayOfChar2[paramInt % i]; 
    return (new String(arrayOfChar1)).concat(paramString1);
  }
  
  public static int length(CharSequence paramCharSequence) {
    int i;
    if (paramCharSequence == null) {
      i = 0;
    } else {
      i = paramCharSequence.length();
    } 
    return i;
  }
  
  public static String lowerCase(String paramString) {
    return (paramString == null) ? null : paramString.toLowerCase();
  }
  
  public static String lowerCase(String paramString, Locale paramLocale) {
    return (paramString == null) ? null : paramString.toLowerCase(paramLocale);
  }
  
  public static String mid(String paramString, int paramInt1, int paramInt2) {
    if (paramString == null)
      return null; 
    if (paramInt2 < 0 || paramInt1 > paramString.length())
      return ""; 
    int i = paramInt1;
    if (paramInt1 < 0)
      i = 0; 
    paramInt1 = paramString.length();
    paramInt2 += i;
    return (paramInt1 <= paramInt2) ? paramString.substring(i) : paramString.substring(i, paramInt2);
  }
  
  public static String normalizeSpace(String paramString) {
    return (paramString == null) ? null : WHITESPACE_BLOCK.matcher(trim(paramString)).replaceAll(" ");
  }
  
  public static int ordinalIndexOf(CharSequence paramCharSequence1, CharSequence paramCharSequence2, int paramInt) {
    return ordinalIndexOf(paramCharSequence1, paramCharSequence2, paramInt, false);
  }
  
  private static int ordinalIndexOf(CharSequence paramCharSequence1, CharSequence paramCharSequence2, int paramInt, boolean paramBoolean) {
    int i = -1;
    int j = i;
    if (paramCharSequence1 != null) {
      j = i;
      if (paramCharSequence2 != null)
        if (paramInt <= 0) {
          j = i;
        } else {
          int k = paramCharSequence2.length();
          j = 0;
          int m = 0;
          if (k == 0) {
            paramInt = m;
            if (paramBoolean)
              paramInt = paramCharSequence1.length(); 
            return paramInt;
          } 
          m = j;
          if (paramBoolean) {
            i = paramCharSequence1.length();
            m = j;
          } 
          do {
            if (paramBoolean) {
              j = CharSequenceUtils.lastIndexOf(paramCharSequence1, paramCharSequence2, i - 1);
            } else {
              j = CharSequenceUtils.indexOf(paramCharSequence1, paramCharSequence2, i + 1);
            } 
            if (j < 0)
              return j; 
            k = m + 1;
            i = j;
            m = k;
          } while (k < paramInt);
        }  
    } 
    return j;
  }
  
  public static String overlay(String paramString1, String paramString2, int paramInt1, int paramInt2) {
    if (paramString1 == null)
      return null; 
    String str = paramString2;
    if (paramString2 == null)
      str = ""; 
    int i = paramString1.length();
    int j = paramInt1;
    if (paramInt1 < 0)
      j = 0; 
    paramInt1 = j;
    if (j > i)
      paramInt1 = i; 
    j = paramInt2;
    if (paramInt2 < 0)
      j = 0; 
    paramInt2 = j;
    if (j > i)
      paramInt2 = i; 
    int k = paramInt1;
    j = paramInt2;
    if (paramInt1 > paramInt2) {
      j = paramInt1;
      k = paramInt2;
    } 
    StringBuilder stringBuilder = new StringBuilder(i + k - j + str.length() + 1);
    stringBuilder.append(paramString1.substring(0, k));
    stringBuilder.append(str);
    stringBuilder.append(paramString1.substring(j));
    return stringBuilder.toString();
  }
  
  public static String remove(String paramString, char paramChar) {
    if (isEmpty(paramString) || paramString.indexOf(paramChar) == -1)
      return paramString; 
    char[] arrayOfChar = paramString.toCharArray();
    byte b = 0;
    int i;
    for (i = 0; b < arrayOfChar.length; i = j) {
      int j = i;
      if (arrayOfChar[b] != paramChar) {
        arrayOfChar[i] = (char)arrayOfChar[b];
        j = i + 1;
      } 
      b++;
    } 
    return new String(arrayOfChar, 0, i);
  }
  
  public static String remove(String paramString1, String paramString2) {
    String str = paramString1;
    if (!isEmpty(paramString1))
      if (isEmpty(paramString2)) {
        str = paramString1;
      } else {
        str = replace(paramString1, paramString2, "", -1);
      }  
    return str;
  }
  
  private static String removeAccentsJava6(CharSequence paramCharSequence) throws IllegalAccessException, InvocationTargetException {
    if (InitStripAccents.java6NormalizeMethod != null && InitStripAccents.java6NormalizerFormNFD != null) {
      paramCharSequence = (String)InitStripAccents.java6NormalizeMethod.invoke(null, new Object[] { paramCharSequence, InitStripAccents.access$400() });
      return InitStripAccents.java6Pattern.matcher(paramCharSequence).replaceAll("");
    } 
    throw new IllegalStateException("java.text.Normalizer is not available", InitStripAccents.java6Exception);
  }
  
  private static String removeAccentsSUN(CharSequence paramCharSequence) throws IllegalAccessException, InvocationTargetException {
    if (InitStripAccents.sunDecomposeMethod != null) {
      paramCharSequence = (String)InitStripAccents.sunDecomposeMethod.invoke(null, new Object[] { paramCharSequence, Boolean.FALSE, Integer.valueOf(0) });
      return InitStripAccents.sunPattern.matcher(paramCharSequence).replaceAll("");
    } 
    throw new IllegalStateException("sun.text.Normalizer is not available", InitStripAccents.sunException);
  }
  
  public static String removeEnd(String paramString1, String paramString2) {
    String str = paramString1;
    if (!isEmpty(paramString1))
      if (isEmpty(paramString2)) {
        str = paramString1;
      } else {
        str = paramString1;
        if (paramString1.endsWith(paramString2))
          str = paramString1.substring(0, paramString1.length() - paramString2.length()); 
      }  
    return str;
  }
  
  public static String removeEndIgnoreCase(String paramString1, String paramString2) {
    String str = paramString1;
    if (!isEmpty(paramString1))
      if (isEmpty(paramString2)) {
        str = paramString1;
      } else {
        str = paramString1;
        if (endsWithIgnoreCase(paramString1, paramString2))
          str = paramString1.substring(0, paramString1.length() - paramString2.length()); 
      }  
    return str;
  }
  
  public static String removeStart(String paramString1, String paramString2) {
    String str = paramString1;
    if (!isEmpty(paramString1))
      if (isEmpty(paramString2)) {
        str = paramString1;
      } else {
        str = paramString1;
        if (paramString1.startsWith(paramString2))
          str = paramString1.substring(paramString2.length()); 
      }  
    return str;
  }
  
  public static String removeStartIgnoreCase(String paramString1, String paramString2) {
    String str = paramString1;
    if (!isEmpty(paramString1))
      if (isEmpty(paramString2)) {
        str = paramString1;
      } else {
        str = paramString1;
        if (startsWithIgnoreCase(paramString1, paramString2))
          str = paramString1.substring(paramString2.length()); 
      }  
    return str;
  }
  
  public static String repeat(char paramChar, int paramInt) {
    char[] arrayOfChar = new char[paramInt];
    while (--paramInt >= 0) {
      arrayOfChar[paramInt] = (char)paramChar;
      paramInt--;
    } 
    return new String(arrayOfChar);
  }
  
  public static String repeat(String paramString, int paramInt) {
    if (paramString == null)
      return null; 
    if (paramInt <= 0)
      return ""; 
    int i = paramString.length();
    String str = paramString;
    if (paramInt != 1)
      if (i == 0) {
        str = paramString;
      } else {
        char[] arrayOfChar;
        char c = Character.MIN_VALUE;
        if (i == 1 && paramInt <= 8192)
          return repeat(paramString.charAt(0), paramInt); 
        int j = i * paramInt;
        if (i != 1) {
          if (i != 2) {
            StringBuilder stringBuilder = new StringBuilder(j);
            while (c < paramInt) {
              stringBuilder.append(paramString);
              c++;
            } 
            return stringBuilder.toString();
          } 
          i = paramString.charAt(0);
          c = paramString.charAt(1);
          arrayOfChar = new char[j];
          for (paramInt = paramInt * 2 - 2; paramInt >= 0; paramInt = paramInt - 1 - 1) {
            arrayOfChar[paramInt] = (char)i;
            arrayOfChar[paramInt + 1] = (char)c;
          } 
          return new String(arrayOfChar);
        } 
        str = repeat(arrayOfChar.charAt(0), paramInt);
      }  
    return str;
  }
  
  public static String repeat(String paramString1, String paramString2, int paramInt) {
    if (paramString1 == null || paramString2 == null)
      return repeat(paramString1, paramInt); 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString1);
    stringBuilder.append(paramString2);
    return removeEnd(repeat(stringBuilder.toString(), paramInt), paramString2);
  }
  
  public static String replace(String paramString1, String paramString2, String paramString3) {
    return replace(paramString1, paramString2, paramString3, -1);
  }
  
  public static String replace(String paramString1, String paramString2, String paramString3, int paramInt) {
    String str = paramString1;
    if (!isEmpty(paramString1)) {
      str = paramString1;
      if (!isEmpty(paramString2)) {
        str = paramString1;
        if (paramString3 != null)
          if (paramInt == 0) {
            str = paramString1;
          } else {
            int i = 0;
            int j = paramString1.indexOf(paramString2, 0);
            if (j == -1)
              return paramString1; 
            int k = paramString2.length();
            int m = paramString3.length() - k;
            int n = m;
            if (m < 0)
              n = 0; 
            m = 64;
            if (paramInt < 0) {
              m = 16;
            } else if (paramInt <= 64) {
              m = paramInt;
            } 
            StringBuilder stringBuilder = new StringBuilder(paramString1.length() + n * m);
            m = paramInt;
            n = j;
            paramInt = i;
            while (true) {
              i = paramInt;
              if (n != -1) {
                stringBuilder.append(paramString1.substring(paramInt, n));
                stringBuilder.append(paramString3);
                paramInt = n + k;
                if (--m == 0) {
                  i = paramInt;
                  break;
                } 
                n = paramString1.indexOf(paramString2, paramInt);
                continue;
              } 
              break;
            } 
            stringBuilder.append(paramString1.substring(i));
            str = stringBuilder.toString();
          }  
      } 
    } 
    return str;
  }
  
  public static String replaceChars(String paramString, char paramChar1, char paramChar2) {
    return (paramString == null) ? null : paramString.replace(paramChar1, paramChar2);
  }
  
  public static String replaceChars(String paramString1, String paramString2, String paramString3) {
    String str = paramString1;
    if (!isEmpty(paramString1))
      if (isEmpty(paramString2)) {
        str = paramString1;
      } else {
        str = paramString3;
        if (paramString3 == null)
          str = ""; 
        int i = str.length();
        int j = paramString1.length();
        StringBuilder stringBuilder = new StringBuilder(j);
        byte b = 0;
        boolean bool = false;
        while (b < j) {
          char c = paramString1.charAt(b);
          int k = paramString2.indexOf(c);
          if (k >= 0) {
            if (k < i)
              stringBuilder.append(str.charAt(k)); 
            bool = true;
          } else {
            stringBuilder.append(c);
          } 
          b++;
        } 
        str = paramString1;
        if (bool)
          str = stringBuilder.toString(); 
      }  
    return str;
  }
  
  public static String replaceEach(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2) {
    return replaceEach(paramString, paramArrayOfString1, paramArrayOfString2, false, 0);
  }
  
  private static String replaceEach(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2, boolean paramBoolean, int paramInt) {
    // Byte code:
    //   0: aload_0
    //   1: ifnull -> 694
    //   4: aload_0
    //   5: invokevirtual length : ()I
    //   8: ifeq -> 694
    //   11: aload_1
    //   12: ifnull -> 694
    //   15: aload_1
    //   16: arraylength
    //   17: ifeq -> 694
    //   20: aload_2
    //   21: ifnull -> 694
    //   24: aload_2
    //   25: arraylength
    //   26: ifne -> 32
    //   29: goto -> 694
    //   32: iload #4
    //   34: iflt -> 683
    //   37: aload_1
    //   38: arraylength
    //   39: istore #5
    //   41: aload_2
    //   42: arraylength
    //   43: istore #6
    //   45: iload #5
    //   47: iload #6
    //   49: if_icmpne -> 633
    //   52: iload #5
    //   54: newarray boolean
    //   56: astore #7
    //   58: iconst_0
    //   59: istore #8
    //   61: iconst_m1
    //   62: istore #6
    //   64: iconst_m1
    //   65: istore #9
    //   67: iload #8
    //   69: iload #5
    //   71: if_icmpge -> 217
    //   74: iload #6
    //   76: istore #10
    //   78: iload #9
    //   80: istore #11
    //   82: aload #7
    //   84: iload #8
    //   86: baload
    //   87: ifne -> 203
    //   90: iload #6
    //   92: istore #10
    //   94: iload #9
    //   96: istore #11
    //   98: aload_1
    //   99: iload #8
    //   101: aaload
    //   102: ifnull -> 203
    //   105: iload #6
    //   107: istore #10
    //   109: iload #9
    //   111: istore #11
    //   113: aload_1
    //   114: iload #8
    //   116: aaload
    //   117: invokevirtual length : ()I
    //   120: ifeq -> 203
    //   123: aload_2
    //   124: iload #8
    //   126: aaload
    //   127: ifnonnull -> 141
    //   130: iload #6
    //   132: istore #10
    //   134: iload #9
    //   136: istore #11
    //   138: goto -> 203
    //   141: aload_0
    //   142: aload_1
    //   143: iload #8
    //   145: aaload
    //   146: invokevirtual indexOf : (Ljava/lang/String;)I
    //   149: istore #12
    //   151: iload #12
    //   153: iconst_m1
    //   154: if_icmpne -> 174
    //   157: aload #7
    //   159: iload #8
    //   161: iconst_1
    //   162: bastore
    //   163: iload #6
    //   165: istore #10
    //   167: iload #9
    //   169: istore #11
    //   171: goto -> 203
    //   174: iload #6
    //   176: iconst_m1
    //   177: if_icmpeq -> 195
    //   180: iload #6
    //   182: istore #10
    //   184: iload #9
    //   186: istore #11
    //   188: iload #12
    //   190: iload #6
    //   192: if_icmpge -> 203
    //   195: iload #8
    //   197: istore #11
    //   199: iload #12
    //   201: istore #10
    //   203: iinc #8, 1
    //   206: iload #10
    //   208: istore #6
    //   210: iload #11
    //   212: istore #9
    //   214: goto -> 67
    //   217: iload #6
    //   219: iconst_m1
    //   220: if_icmpne -> 225
    //   223: aload_0
    //   224: areturn
    //   225: iconst_0
    //   226: istore #8
    //   228: iconst_0
    //   229: istore #11
    //   231: iload #8
    //   233: aload_1
    //   234: arraylength
    //   235: if_icmpge -> 308
    //   238: iload #11
    //   240: istore #10
    //   242: aload_1
    //   243: iload #8
    //   245: aaload
    //   246: ifnull -> 298
    //   249: aload_2
    //   250: iload #8
    //   252: aaload
    //   253: ifnonnull -> 263
    //   256: iload #11
    //   258: istore #10
    //   260: goto -> 298
    //   263: aload_2
    //   264: iload #8
    //   266: aaload
    //   267: invokevirtual length : ()I
    //   270: aload_1
    //   271: iload #8
    //   273: aaload
    //   274: invokevirtual length : ()I
    //   277: isub
    //   278: istore #12
    //   280: iload #11
    //   282: istore #10
    //   284: iload #12
    //   286: ifle -> 298
    //   289: iload #11
    //   291: iload #12
    //   293: iconst_3
    //   294: imul
    //   295: iadd
    //   296: istore #10
    //   298: iinc #8, 1
    //   301: iload #10
    //   303: istore #11
    //   305: goto -> 231
    //   308: iload #11
    //   310: aload_0
    //   311: invokevirtual length : ()I
    //   314: iconst_5
    //   315: idiv
    //   316: invokestatic min : (II)I
    //   319: istore #8
    //   321: new java/lang/StringBuilder
    //   324: dup
    //   325: aload_0
    //   326: invokevirtual length : ()I
    //   329: iload #8
    //   331: iadd
    //   332: invokespecial <init> : (I)V
    //   335: astore #13
    //   337: iconst_0
    //   338: istore #8
    //   340: iload #9
    //   342: istore #10
    //   344: iload #6
    //   346: istore #12
    //   348: iload #8
    //   350: istore #6
    //   352: iload #12
    //   354: iconst_m1
    //   355: if_icmpeq -> 578
    //   358: iload #6
    //   360: iload #12
    //   362: if_icmpge -> 383
    //   365: aload #13
    //   367: aload_0
    //   368: iload #6
    //   370: invokevirtual charAt : (I)C
    //   373: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   376: pop
    //   377: iinc #6, 1
    //   380: goto -> 358
    //   383: aload #13
    //   385: aload_2
    //   386: iload #10
    //   388: aaload
    //   389: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   392: pop
    //   393: aload_1
    //   394: iload #10
    //   396: aaload
    //   397: invokevirtual length : ()I
    //   400: iload #12
    //   402: iadd
    //   403: istore #14
    //   405: iconst_m1
    //   406: istore #11
    //   408: iconst_m1
    //   409: istore #8
    //   411: iconst_0
    //   412: istore #9
    //   414: iload #14
    //   416: istore #6
    //   418: iload #11
    //   420: istore #12
    //   422: iload #8
    //   424: istore #10
    //   426: iload #9
    //   428: iload #5
    //   430: if_icmpge -> 352
    //   433: iload #11
    //   435: istore #10
    //   437: iload #8
    //   439: istore #6
    //   441: aload #7
    //   443: iload #9
    //   445: baload
    //   446: ifne -> 564
    //   449: iload #11
    //   451: istore #10
    //   453: iload #8
    //   455: istore #6
    //   457: aload_1
    //   458: iload #9
    //   460: aaload
    //   461: ifnull -> 564
    //   464: iload #11
    //   466: istore #10
    //   468: iload #8
    //   470: istore #6
    //   472: aload_1
    //   473: iload #9
    //   475: aaload
    //   476: invokevirtual length : ()I
    //   479: ifeq -> 564
    //   482: aload_2
    //   483: iload #9
    //   485: aaload
    //   486: ifnonnull -> 500
    //   489: iload #11
    //   491: istore #10
    //   493: iload #8
    //   495: istore #6
    //   497: goto -> 564
    //   500: aload_0
    //   501: aload_1
    //   502: iload #9
    //   504: aaload
    //   505: iload #14
    //   507: invokevirtual indexOf : (Ljava/lang/String;I)I
    //   510: istore #12
    //   512: iload #12
    //   514: iconst_m1
    //   515: if_icmpne -> 535
    //   518: aload #7
    //   520: iload #9
    //   522: iconst_1
    //   523: bastore
    //   524: iload #11
    //   526: istore #10
    //   528: iload #8
    //   530: istore #6
    //   532: goto -> 564
    //   535: iload #11
    //   537: iconst_m1
    //   538: if_icmpeq -> 556
    //   541: iload #11
    //   543: istore #10
    //   545: iload #8
    //   547: istore #6
    //   549: iload #12
    //   551: iload #11
    //   553: if_icmpge -> 564
    //   556: iload #9
    //   558: istore #6
    //   560: iload #12
    //   562: istore #10
    //   564: iinc #9, 1
    //   567: iload #10
    //   569: istore #11
    //   571: iload #6
    //   573: istore #8
    //   575: goto -> 414
    //   578: aload_0
    //   579: invokevirtual length : ()I
    //   582: istore #9
    //   584: iload #6
    //   586: iload #9
    //   588: if_icmpge -> 609
    //   591: aload #13
    //   593: aload_0
    //   594: iload #6
    //   596: invokevirtual charAt : (I)C
    //   599: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   602: pop
    //   603: iinc #6, 1
    //   606: goto -> 584
    //   609: aload #13
    //   611: invokevirtual toString : ()Ljava/lang/String;
    //   614: astore_0
    //   615: iload_3
    //   616: ifne -> 621
    //   619: aload_0
    //   620: areturn
    //   621: aload_0
    //   622: aload_1
    //   623: aload_2
    //   624: iload_3
    //   625: iload #4
    //   627: iconst_1
    //   628: isub
    //   629: invokestatic replaceEach : (Ljava/lang/String;[Ljava/lang/String;[Ljava/lang/String;ZI)Ljava/lang/String;
    //   632: areturn
    //   633: new java/lang/StringBuilder
    //   636: dup
    //   637: invokespecial <init> : ()V
    //   640: astore_0
    //   641: aload_0
    //   642: ldc_w 'Search and Replace array lengths don't match: '
    //   645: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   648: pop
    //   649: aload_0
    //   650: iload #5
    //   652: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   655: pop
    //   656: aload_0
    //   657: ldc_w ' vs '
    //   660: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   663: pop
    //   664: aload_0
    //   665: iload #6
    //   667: invokevirtual append : (I)Ljava/lang/StringBuilder;
    //   670: pop
    //   671: new java/lang/IllegalArgumentException
    //   674: dup
    //   675: aload_0
    //   676: invokevirtual toString : ()Ljava/lang/String;
    //   679: invokespecial <init> : (Ljava/lang/String;)V
    //   682: athrow
    //   683: new java/lang/IllegalStateException
    //   686: dup
    //   687: ldc_w 'Aborting to protect against StackOverflowError - output of one loop is the input of another'
    //   690: invokespecial <init> : (Ljava/lang/String;)V
    //   693: athrow
    //   694: aload_0
    //   695: areturn
  }
  
  public static String replaceEachRepeatedly(String paramString, String[] paramArrayOfString1, String[] paramArrayOfString2) {
    int i;
    if (paramArrayOfString1 == null) {
      i = 0;
    } else {
      i = paramArrayOfString1.length;
    } 
    return replaceEach(paramString, paramArrayOfString1, paramArrayOfString2, true, i);
  }
  
  public static String replaceOnce(String paramString1, String paramString2, String paramString3) {
    return replace(paramString1, paramString2, paramString3, 1);
  }
  
  public static String reverse(String paramString) {
    return (paramString == null) ? null : (new StringBuilder(paramString)).reverse().toString();
  }
  
  public static String reverseDelimited(String paramString, char paramChar) {
    if (paramString == null)
      return null; 
    String[] arrayOfString = split(paramString, paramChar);
    ArrayUtils.reverse((Object[])arrayOfString);
    return join((Object[])arrayOfString, paramChar);
  }
  
  public static String right(String paramString, int paramInt) {
    return (paramString == null) ? null : ((paramInt < 0) ? "" : ((paramString.length() <= paramInt) ? paramString : paramString.substring(paramString.length() - paramInt)));
  }
  
  public static String rightPad(String paramString, int paramInt) {
    return rightPad(paramString, paramInt, ' ');
  }
  
  public static String rightPad(String paramString, int paramInt, char paramChar) {
    if (paramString == null)
      return null; 
    int i = paramInt - paramString.length();
    return (i <= 0) ? paramString : ((i > 8192) ? rightPad(paramString, paramInt, String.valueOf(paramChar)) : paramString.concat(repeat(paramChar, i)));
  }
  
  public static String rightPad(String paramString1, int paramInt, String paramString2) {
    if (paramString1 == null)
      return null; 
    String str = paramString2;
    if (isEmpty(paramString2))
      str = " "; 
    int i = str.length();
    int j = paramInt - paramString1.length();
    if (j <= 0)
      return paramString1; 
    boolean bool = false;
    if (i == 1 && j <= 8192)
      return rightPad(paramString1, paramInt, str.charAt(0)); 
    if (j == i)
      return paramString1.concat(str); 
    if (j < i)
      return paramString1.concat(str.substring(0, j)); 
    char[] arrayOfChar1 = new char[j];
    char[] arrayOfChar2 = str.toCharArray();
    for (paramInt = bool; paramInt < j; paramInt++)
      arrayOfChar1[paramInt] = (char)arrayOfChar2[paramInt % i]; 
    return paramString1.concat(new String(arrayOfChar1));
  }
  
  public static String[] split(String paramString) {
    return split(paramString, null, -1);
  }
  
  public static String[] split(String paramString, char paramChar) {
    return splitWorker(paramString, paramChar, false);
  }
  
  public static String[] split(String paramString1, String paramString2) {
    return splitWorker(paramString1, paramString2, -1, false);
  }
  
  public static String[] split(String paramString1, String paramString2, int paramInt) {
    return splitWorker(paramString1, paramString2, paramInt, false);
  }
  
  public static String[] splitByCharacterType(String paramString) {
    return splitByCharacterType(paramString, false);
  }
  
  private static String[] splitByCharacterType(String paramString, boolean paramBoolean) {
    if (paramString == null)
      return null; 
    if (paramString.length() == 0)
      return ArrayUtils.EMPTY_STRING_ARRAY; 
    char[] arrayOfChar = paramString.toCharArray();
    ArrayList<String> arrayList = new ArrayList();
    int i = 0;
    int j = Character.getType(arrayOfChar[0]);
    byte b = 1;
    while (b < arrayOfChar.length) {
      int k = Character.getType(arrayOfChar[b]);
      if (k == j) {
        k = j;
      } else {
        if (paramBoolean && k == 2 && j == 1) {
          int m = b - 1;
          j = i;
          if (m != i) {
            arrayList.add(new String(arrayOfChar, i, m - i));
            j = m;
          } 
        } else {
          arrayList.add(new String(arrayOfChar, i, b - i));
          j = b;
        } 
        i = j;
      } 
      b++;
      j = k;
    } 
    arrayList.add(new String(arrayOfChar, i, arrayOfChar.length - i));
    return arrayList.<String>toArray(new String[arrayList.size()]);
  }
  
  public static String[] splitByCharacterTypeCamelCase(String paramString) {
    return splitByCharacterType(paramString, true);
  }
  
  public static String[] splitByWholeSeparator(String paramString1, String paramString2) {
    return splitByWholeSeparatorWorker(paramString1, paramString2, -1, false);
  }
  
  public static String[] splitByWholeSeparator(String paramString1, String paramString2, int paramInt) {
    return splitByWholeSeparatorWorker(paramString1, paramString2, paramInt, false);
  }
  
  public static String[] splitByWholeSeparatorPreserveAllTokens(String paramString1, String paramString2) {
    return splitByWholeSeparatorWorker(paramString1, paramString2, -1, true);
  }
  
  public static String[] splitByWholeSeparatorPreserveAllTokens(String paramString1, String paramString2, int paramInt) {
    return splitByWholeSeparatorWorker(paramString1, paramString2, paramInt, true);
  }
  
  private static String[] splitByWholeSeparatorWorker(String paramString1, String paramString2, int paramInt, boolean paramBoolean) {
    if (paramString1 == null)
      return null; 
    int i = paramString1.length();
    if (i == 0)
      return ArrayUtils.EMPTY_STRING_ARRAY; 
    if (paramString2 == null || "".equals(paramString2))
      return splitWorker(paramString1, null, paramInt, paramBoolean); 
    int j = paramString2.length();
    ArrayList<String> arrayList = new ArrayList();
    int k = 0;
    int m = 0;
    int n = 0;
    while (k < i) {
      int i1 = paramString1.indexOf(paramString2, m);
      if (i1 > -1) {
        if (i1 > m) {
          int i2 = n + 1;
          if (i2 == paramInt) {
            arrayList.add(paramString1.substring(m));
            n = i2;
          } else {
            arrayList.add(paramString1.substring(m, i1));
            k = i1;
            m = k + j;
            n = i2;
          } 
        } else {
          k = i1;
          int i2 = n;
          if (paramBoolean) {
            i2 = n + 1;
            if (i2 == paramInt) {
              arrayList.add(paramString1.substring(m));
              k = i;
            } else {
              arrayList.add("");
              k = i1;
            } 
          } 
          m = k + j;
          n = i2;
        } 
      } else {
        arrayList.add(paramString1.substring(m));
      } 
      k = i;
    } 
    return arrayList.<String>toArray(new String[arrayList.size()]);
  }
  
  public static String[] splitPreserveAllTokens(String paramString) {
    return splitWorker(paramString, null, -1, true);
  }
  
  public static String[] splitPreserveAllTokens(String paramString, char paramChar) {
    return splitWorker(paramString, paramChar, true);
  }
  
  public static String[] splitPreserveAllTokens(String paramString1, String paramString2) {
    return splitWorker(paramString1, paramString2, -1, true);
  }
  
  public static String[] splitPreserveAllTokens(String paramString1, String paramString2, int paramInt) {
    return splitWorker(paramString1, paramString2, paramInt, true);
  }
  
  private static String[] splitWorker(String paramString, char paramChar, boolean paramBoolean) {
    if (paramString == null)
      return null; 
    int i = paramString.length();
    if (i == 0)
      return ArrayUtils.EMPTY_STRING_ARRAY; 
    ArrayList<String> arrayList = new ArrayList();
    int j = 0;
    boolean bool1 = false;
    boolean bool2 = false;
    int k = 0;
    while (j < i) {
      if (paramString.charAt(j) == paramChar) {
        if (bool1 || paramBoolean) {
          arrayList.add(paramString.substring(k, j));
          bool1 = false;
          bool2 = true;
        } 
        k = j + 1;
        j = k;
        continue;
      } 
      j++;
      bool1 = true;
      bool2 = false;
    } 
    if (bool1 || (paramBoolean && bool2))
      arrayList.add(paramString.substring(k, j)); 
    return arrayList.<String>toArray(new String[arrayList.size()]);
  }
  
  private static String[] splitWorker(String paramString1, String paramString2, int paramInt, boolean paramBoolean) {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull -> 6
    //   4: aconst_null
    //   5: areturn
    //   6: aload_0
    //   7: invokevirtual length : ()I
    //   10: istore #4
    //   12: iload #4
    //   14: ifne -> 21
    //   17: getstatic external/org/apache/commons/lang3/ArrayUtils.EMPTY_STRING_ARRAY : [Ljava/lang/String;
    //   20: areturn
    //   21: new java/util/ArrayList
    //   24: dup
    //   25: invokespecial <init> : ()V
    //   28: astore #5
    //   30: aload_1
    //   31: ifnonnull -> 194
    //   34: iconst_0
    //   35: istore #6
    //   37: iconst_0
    //   38: istore #7
    //   40: iconst_0
    //   41: istore #8
    //   43: iconst_0
    //   44: istore #9
    //   46: iconst_1
    //   47: istore #10
    //   49: iload #7
    //   51: istore #11
    //   53: iload #8
    //   55: istore #12
    //   57: iload #9
    //   59: istore #13
    //   61: iload #6
    //   63: istore #14
    //   65: iload #6
    //   67: iload #4
    //   69: if_icmpge -> 523
    //   72: aload_0
    //   73: iload #6
    //   75: invokevirtual charAt : (I)C
    //   78: invokestatic isWhitespace : (C)Z
    //   81: ifeq -> 182
    //   84: iload #7
    //   86: ifne -> 105
    //   89: iload #7
    //   91: istore #13
    //   93: iload #10
    //   95: istore #14
    //   97: iload #6
    //   99: istore #7
    //   101: iload_3
    //   102: ifeq -> 161
    //   105: iload #10
    //   107: iload_2
    //   108: if_icmpne -> 121
    //   111: iload #4
    //   113: istore #7
    //   115: iconst_0
    //   116: istore #6
    //   118: goto -> 132
    //   121: iconst_1
    //   122: istore #8
    //   124: iload #6
    //   126: istore #7
    //   128: iload #8
    //   130: istore #6
    //   132: aload #5
    //   134: aload_0
    //   135: iload #9
    //   137: iload #7
    //   139: invokevirtual substring : (II)Ljava/lang/String;
    //   142: invokeinterface add : (Ljava/lang/Object;)Z
    //   147: pop
    //   148: iload #10
    //   150: iconst_1
    //   151: iadd
    //   152: istore #14
    //   154: iconst_0
    //   155: istore #13
    //   157: iload #6
    //   159: istore #8
    //   161: iload #7
    //   163: iconst_1
    //   164: iadd
    //   165: istore #9
    //   167: iload #9
    //   169: istore #6
    //   171: iload #13
    //   173: istore #7
    //   175: iload #14
    //   177: istore #10
    //   179: goto -> 49
    //   182: iinc #6, 1
    //   185: iconst_1
    //   186: istore #7
    //   188: iconst_0
    //   189: istore #8
    //   191: goto -> 49
    //   194: aload_1
    //   195: invokevirtual length : ()I
    //   198: iconst_1
    //   199: if_icmpne -> 360
    //   202: aload_1
    //   203: iconst_0
    //   204: invokevirtual charAt : (I)C
    //   207: istore #15
    //   209: iconst_0
    //   210: istore #6
    //   212: iconst_0
    //   213: istore #7
    //   215: iconst_0
    //   216: istore #8
    //   218: iconst_0
    //   219: istore #9
    //   221: iconst_1
    //   222: istore #11
    //   224: iload #6
    //   226: istore #12
    //   228: iload #7
    //   230: istore #13
    //   232: iload #8
    //   234: istore #14
    //   236: iload #9
    //   238: istore #10
    //   240: iload #6
    //   242: iload #4
    //   244: if_icmpge -> 505
    //   247: aload_0
    //   248: iload #6
    //   250: invokevirtual charAt : (I)C
    //   253: iload #15
    //   255: if_icmpne -> 348
    //   258: iload #7
    //   260: ifne -> 275
    //   263: iload #6
    //   265: istore #14
    //   267: iload #11
    //   269: istore #10
    //   271: iload_3
    //   272: ifeq -> 331
    //   275: iload #11
    //   277: iload_2
    //   278: if_icmpne -> 291
    //   281: iload #4
    //   283: istore #6
    //   285: iconst_0
    //   286: istore #7
    //   288: goto -> 294
    //   291: iconst_1
    //   292: istore #7
    //   294: aload #5
    //   296: aload_0
    //   297: iload #9
    //   299: iload #6
    //   301: invokevirtual substring : (II)Ljava/lang/String;
    //   304: invokeinterface add : (Ljava/lang/Object;)Z
    //   309: pop
    //   310: iload #11
    //   312: iconst_1
    //   313: iadd
    //   314: istore #10
    //   316: iconst_0
    //   317: istore #9
    //   319: iload #7
    //   321: istore #8
    //   323: iload #9
    //   325: istore #7
    //   327: iload #6
    //   329: istore #14
    //   331: iload #14
    //   333: iconst_1
    //   334: iadd
    //   335: istore #9
    //   337: iload #9
    //   339: istore #6
    //   341: iload #10
    //   343: istore #11
    //   345: goto -> 224
    //   348: iinc #6, 1
    //   351: iconst_1
    //   352: istore #7
    //   354: iconst_0
    //   355: istore #8
    //   357: goto -> 224
    //   360: iconst_0
    //   361: istore #6
    //   363: iconst_0
    //   364: istore #8
    //   366: iconst_0
    //   367: istore #7
    //   369: iconst_0
    //   370: istore #9
    //   372: iconst_1
    //   373: istore #11
    //   375: iload #6
    //   377: istore #12
    //   379: iload #8
    //   381: istore #13
    //   383: iload #7
    //   385: istore #14
    //   387: iload #9
    //   389: istore #10
    //   391: iload #6
    //   393: iload #4
    //   395: if_icmpge -> 505
    //   398: aload_1
    //   399: aload_0
    //   400: iload #6
    //   402: invokevirtual charAt : (I)C
    //   405: invokevirtual indexOf : (I)I
    //   408: iflt -> 493
    //   411: iload #8
    //   413: ifne -> 428
    //   416: iload #6
    //   418: istore #14
    //   420: iload #11
    //   422: istore #10
    //   424: iload_3
    //   425: ifeq -> 476
    //   428: iload #11
    //   430: iload_2
    //   431: if_icmpne -> 444
    //   434: iload #4
    //   436: istore #6
    //   438: iconst_0
    //   439: istore #7
    //   441: goto -> 447
    //   444: iconst_1
    //   445: istore #7
    //   447: aload #5
    //   449: aload_0
    //   450: iload #9
    //   452: iload #6
    //   454: invokevirtual substring : (II)Ljava/lang/String;
    //   457: invokeinterface add : (Ljava/lang/Object;)Z
    //   462: pop
    //   463: iload #11
    //   465: iconst_1
    //   466: iadd
    //   467: istore #10
    //   469: iconst_0
    //   470: istore #8
    //   472: iload #6
    //   474: istore #14
    //   476: iload #14
    //   478: iconst_1
    //   479: iadd
    //   480: istore #9
    //   482: iload #9
    //   484: istore #6
    //   486: iload #10
    //   488: istore #11
    //   490: goto -> 375
    //   493: iinc #6, 1
    //   496: iconst_1
    //   497: istore #8
    //   499: iconst_0
    //   500: istore #7
    //   502: goto -> 375
    //   505: iload #13
    //   507: istore #11
    //   509: iload #14
    //   511: istore_2
    //   512: iload #12
    //   514: istore #14
    //   516: iload #10
    //   518: istore #13
    //   520: iload_2
    //   521: istore #12
    //   523: iload #11
    //   525: ifne -> 537
    //   528: iload_3
    //   529: ifeq -> 553
    //   532: iload #12
    //   534: ifeq -> 553
    //   537: aload #5
    //   539: aload_0
    //   540: iload #13
    //   542: iload #14
    //   544: invokevirtual substring : (II)Ljava/lang/String;
    //   547: invokeinterface add : (Ljava/lang/Object;)Z
    //   552: pop
    //   553: aload #5
    //   555: aload #5
    //   557: invokeinterface size : ()I
    //   562: anewarray java/lang/String
    //   565: invokeinterface toArray : ([Ljava/lang/Object;)[Ljava/lang/Object;
    //   570: checkcast [Ljava/lang/String;
    //   573: areturn
  }
  
  public static boolean startsWith(CharSequence paramCharSequence1, CharSequence paramCharSequence2) {
    return startsWith(paramCharSequence1, paramCharSequence2, false);
  }
  
  private static boolean startsWith(CharSequence paramCharSequence1, CharSequence paramCharSequence2, boolean paramBoolean) {
    boolean bool = false;
    if (paramCharSequence1 == null || paramCharSequence2 == null) {
      paramBoolean = bool;
      if (paramCharSequence1 == null) {
        paramBoolean = bool;
        if (paramCharSequence2 == null)
          paramBoolean = true; 
      } 
      return paramBoolean;
    } 
    return (paramCharSequence2.length() > paramCharSequence1.length()) ? false : CharSequenceUtils.regionMatches(paramCharSequence1, paramBoolean, 0, paramCharSequence2, 0, paramCharSequence2.length());
  }
  
  public static boolean startsWithAny(CharSequence paramCharSequence, CharSequence... paramVarArgs) {
    if (!isEmpty(paramCharSequence) && !ArrayUtils.isEmpty((Object[])paramVarArgs)) {
      int i = paramVarArgs.length;
      for (byte b = 0; b < i; b++) {
        if (startsWith(paramCharSequence, paramVarArgs[b]))
          return true; 
      } 
    } 
    return false;
  }
  
  public static boolean startsWithIgnoreCase(CharSequence paramCharSequence1, CharSequence paramCharSequence2) {
    return startsWith(paramCharSequence1, paramCharSequence2, true);
  }
  
  public static String strip(String paramString) {
    return strip(paramString, null);
  }
  
  public static String strip(String paramString1, String paramString2) {
    return isEmpty(paramString1) ? paramString1 : stripEnd(stripStart(paramString1, paramString2), paramString2);
  }
  
  public static String stripAccents(String paramString) {
    if (paramString == null)
      return null; 
    try {
      StringBuilder stringBuilder;
      if (InitStripAccents.java6NormalizeMethod != null) {
        paramString = removeAccentsJava6(paramString);
      } else {
        if (InitStripAccents.sunDecomposeMethod != null)
          return removeAccentsSUN(paramString); 
        UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException();
        stringBuilder = new StringBuilder();
        this();
        stringBuilder.append("The stripAccents(CharSequence) method requires at least Java6, but got: ");
        stringBuilder.append(InitStripAccents.java6Exception);
        stringBuilder.append("; or a Sun JVM: ");
        stringBuilder.append(InitStripAccents.sunException);
        this(stringBuilder.toString());
        throw unsupportedOperationException;
      } 
      return (String)stringBuilder;
    } catch (IllegalArgumentException illegalArgumentException) {
      throw new RuntimeException("IllegalArgumentException occurred", illegalArgumentException);
    } catch (IllegalAccessException illegalAccessException) {
      throw new RuntimeException("IllegalAccessException occurred", illegalAccessException);
    } catch (InvocationTargetException invocationTargetException) {
      throw new RuntimeException("InvocationTargetException occurred", invocationTargetException);
    } catch (SecurityException securityException) {
      throw new RuntimeException("SecurityException occurred", securityException);
    } 
  }
  
  public static String[] stripAll(String... paramVarArgs) {
    return stripAll(paramVarArgs, null);
  }
  
  public static String[] stripAll(String[] paramArrayOfString, String paramString) {
    if (paramArrayOfString != null) {
      int i = paramArrayOfString.length;
      if (i != 0) {
        String[] arrayOfString = new String[i];
        for (byte b = 0; b < i; b++)
          arrayOfString[b] = strip(paramArrayOfString[b], paramString); 
        return arrayOfString;
      } 
    } 
    return paramArrayOfString;
  }
  
  public static String stripEnd(String paramString1, String paramString2) {
    String str = paramString1;
    if (paramString1 != null) {
      int i = paramString1.length();
      if (i == 0) {
        str = paramString1;
      } else {
        int j;
        if (paramString2 == null) {
          while (true) {
            j = i;
            if (i != 0) {
              j = i;
              if (Character.isWhitespace(paramString1.charAt(i - 1))) {
                i--;
                continue;
              } 
            } 
            break;
          } 
        } else {
          if (paramString2.length() == 0)
            return paramString1; 
          while (true) {
            j = i;
            if (i != 0) {
              j = i;
              if (paramString2.indexOf(paramString1.charAt(i - 1)) != -1) {
                i--;
                continue;
              } 
            } 
            break;
          } 
        } 
        str = paramString1.substring(0, j);
      } 
    } 
    return str;
  }
  
  public static String stripStart(String paramString1, String paramString2) {
    String str = paramString1;
    if (paramString1 != null) {
      int i = paramString1.length();
      if (i == 0) {
        str = paramString1;
      } else {
        int j = 0;
        int k = 0;
        if (paramString2 == null) {
          while (true) {
            j = k;
            if (k != i) {
              j = k;
              if (Character.isWhitespace(paramString1.charAt(k))) {
                k++;
                continue;
              } 
            } 
            break;
          } 
        } else {
          k = j;
          if (paramString2.length() == 0)
            return paramString1; 
          while (true) {
            j = k;
            if (k != i) {
              j = k;
              if (paramString2.indexOf(paramString1.charAt(k)) != -1) {
                k++;
                continue;
              } 
            } 
            break;
          } 
        } 
        str = paramString1.substring(j);
      } 
    } 
    return str;
  }
  
  public static String stripToEmpty(String paramString) {
    if (paramString == null) {
      paramString = "";
    } else {
      paramString = strip(paramString, null);
    } 
    return paramString;
  }
  
  public static String stripToNull(String paramString) {
    String str = null;
    if (paramString == null)
      return null; 
    paramString = strip(paramString, null);
    if (paramString.length() == 0)
      paramString = str; 
    return paramString;
  }
  
  public static String substring(String paramString, int paramInt) {
    if (paramString == null)
      return null; 
    int i = paramInt;
    if (paramInt < 0)
      i = paramInt + paramString.length(); 
    paramInt = i;
    if (i < 0)
      paramInt = 0; 
    return (paramInt > paramString.length()) ? "" : paramString.substring(paramInt);
  }
  
  public static String substring(String paramString, int paramInt1, int paramInt2) {
    if (paramString == null)
      return null; 
    int i = paramInt2;
    if (paramInt2 < 0)
      i = paramInt2 + paramString.length(); 
    paramInt2 = paramInt1;
    if (paramInt1 < 0)
      paramInt2 = paramInt1 + paramString.length(); 
    paramInt1 = i;
    if (i > paramString.length())
      paramInt1 = paramString.length(); 
    if (paramInt2 > paramInt1)
      return ""; 
    i = paramInt2;
    if (paramInt2 < 0)
      i = 0; 
    paramInt2 = paramInt1;
    if (paramInt1 < 0)
      paramInt2 = 0; 
    return paramString.substring(i, paramInt2);
  }
  
  public static String substringAfter(String paramString1, String paramString2) {
    if (isEmpty(paramString1))
      return paramString1; 
    if (paramString2 == null)
      return ""; 
    int i = paramString1.indexOf(paramString2);
    return (i == -1) ? "" : paramString1.substring(i + paramString2.length());
  }
  
  public static String substringAfterLast(String paramString1, String paramString2) {
    if (isEmpty(paramString1))
      return paramString1; 
    if (isEmpty(paramString2))
      return ""; 
    int i = paramString1.lastIndexOf(paramString2);
    return (i == -1 || i == paramString1.length() - paramString2.length()) ? "" : paramString1.substring(i + paramString2.length());
  }
  
  public static String substringBefore(String paramString1, String paramString2) {
    String str = paramString1;
    if (!isEmpty(paramString1))
      if (paramString2 == null) {
        str = paramString1;
      } else {
        if (paramString2.length() == 0)
          return ""; 
        int i = paramString1.indexOf(paramString2);
        if (i == -1)
          return paramString1; 
        str = paramString1.substring(0, i);
      }  
    return str;
  }
  
  public static String substringBeforeLast(String paramString1, String paramString2) {
    String str = paramString1;
    if (!isEmpty(paramString1))
      if (isEmpty(paramString2)) {
        str = paramString1;
      } else {
        int i = paramString1.lastIndexOf(paramString2);
        if (i == -1)
          return paramString1; 
        str = paramString1.substring(0, i);
      }  
    return str;
  }
  
  public static String substringBetween(String paramString1, String paramString2) {
    return substringBetween(paramString1, paramString2, paramString2);
  }
  
  public static String substringBetween(String paramString1, String paramString2, String paramString3) {
    if (paramString1 != null && paramString2 != null && paramString3 != null) {
      int i = paramString1.indexOf(paramString2);
      if (i != -1) {
        int j = paramString1.indexOf(paramString3, paramString2.length() + i);
        if (j != -1)
          return paramString1.substring(i + paramString2.length(), j); 
      } 
    } 
    return null;
  }
  
  public static String[] substringsBetween(String paramString1, String paramString2, String paramString3) {
    if (paramString1 == null || isEmpty(paramString2) || isEmpty(paramString3))
      return null; 
    int i = paramString1.length();
    if (i == 0)
      return ArrayUtils.EMPTY_STRING_ARRAY; 
    int j = paramString3.length();
    int k = paramString2.length();
    ArrayList<String> arrayList = new ArrayList();
    int m;
    for (m = 0; m < i - j; m += j) {
      m = paramString1.indexOf(paramString2, m);
      if (m < 0)
        break; 
      int n = m + k;
      m = paramString1.indexOf(paramString3, n);
      if (m < 0)
        break; 
      arrayList.add(paramString1.substring(n, m));
    } 
    return arrayList.isEmpty() ? null : arrayList.<String>toArray(new String[arrayList.size()]);
  }
  
  public static String swapCase(String paramString) {
    if (isEmpty(paramString))
      return paramString; 
    char[] arrayOfChar = paramString.toCharArray();
    for (byte b = 0; b < arrayOfChar.length; b++) {
      char c = arrayOfChar[b];
      if (Character.isUpperCase(c)) {
        arrayOfChar[b] = Character.toLowerCase(c);
      } else if (Character.isTitleCase(c)) {
        arrayOfChar[b] = Character.toLowerCase(c);
      } else if (Character.isLowerCase(c)) {
        arrayOfChar[b] = Character.toUpperCase(c);
      } 
    } 
    return new String(arrayOfChar);
  }
  
  public static String toString(byte[] paramArrayOfbyte, String paramString) throws UnsupportedEncodingException {
    String str;
    if (paramString == null) {
      str = new String(paramArrayOfbyte);
    } else {
      str = new String((byte[])str, paramString);
    } 
    return str;
  }
  
  public static String trim(String paramString) {
    if (paramString == null) {
      paramString = null;
    } else {
      paramString = paramString.trim();
    } 
    return paramString;
  }
  
  public static String trimToEmpty(String paramString) {
    if (paramString == null) {
      paramString = "";
    } else {
      paramString = paramString.trim();
    } 
    return paramString;
  }
  
  public static String trimToNull(String paramString) {
    String str = trim(paramString);
    paramString = str;
    if (isEmpty(str))
      paramString = null; 
    return paramString;
  }
  
  public static String uncapitalize(String paramString) {
    String str = paramString;
    if (paramString != null) {
      int i = paramString.length();
      if (i == 0) {
        str = paramString;
      } else {
        StringBuilder stringBuilder = new StringBuilder(i);
        stringBuilder.append(Character.toLowerCase(paramString.charAt(0)));
        stringBuilder.append(paramString.substring(1));
        str = stringBuilder.toString();
      } 
    } 
    return str;
  }
  
  public static String upperCase(String paramString) {
    return (paramString == null) ? null : paramString.toUpperCase();
  }
  
  public static String upperCase(String paramString, Locale paramLocale) {
    return (paramString == null) ? null : paramString.toUpperCase(paramLocale);
  }
  
  private static class InitStripAccents {
    private static final Throwable java6Exception;
    
    private static final Method java6NormalizeMethod;
    
    private static final Object java6NormalizerFormNFD;
    
    private static final Pattern java6Pattern;
    
    private static final Method sunDecomposeMethod;
    
    private static final Throwable sunException;
    
    private static final Pattern sunPattern;
    
    static {
      Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
      sunPattern = pattern;
      java6Pattern = pattern;
      Exception exception1 = null;
      try {
        Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass("java.text.Normalizer$Form");
        Object object = clazz.getField("NFD").get(null);
        try {
          Method method = Thread.currentThread().getContextClassLoader().loadClass("java.text.Normalizer").getMethod("normalize", new Class[] { CharSequence.class, clazz });
          Object object1 = null;
          Object object2 = object1;
        } catch (Exception null) {}
      } catch (Exception exception2) {
        pattern = null;
      } 
      try {
        Method method = Thread.currentThread().getContextClassLoader().loadClass("sun.text.Normalizer").getMethod("decompose", new Class[] { String.class, boolean.class, int.class });
        Object object = null;
      } catch (Exception exception) {
        Object object = null;
      } 
      Exception exception3 = null;
      exception1 = exception2;
      exception2 = exception3;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\external\org\apache\commons\lang3\StringUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */