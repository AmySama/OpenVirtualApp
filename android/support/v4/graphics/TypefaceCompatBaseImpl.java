package android.support.v4.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.CancellationSignal;
import android.support.v4.content.res.FontResourcesParserCompat;
import android.support.v4.provider.FontsContractCompat;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

class TypefaceCompatBaseImpl implements TypefaceCompat.TypefaceCompatImpl {
  private static final String CACHE_FILE_PREFIX = "cached_font_";
  
  private static final String TAG = "TypefaceCompatBaseImpl";
  
  private FontResourcesParserCompat.FontFileResourceEntry findBestEntry(FontResourcesParserCompat.FontFamilyFilesResourceEntry paramFontFamilyFilesResourceEntry, int paramInt) {
    return findBestFont(paramFontFamilyFilesResourceEntry.getEntries(), paramInt, new StyleExtractor<FontResourcesParserCompat.FontFileResourceEntry>() {
          public int getWeight(FontResourcesParserCompat.FontFileResourceEntry param1FontFileResourceEntry) {
            return param1FontFileResourceEntry.getWeight();
          }
          
          public boolean isItalic(FontResourcesParserCompat.FontFileResourceEntry param1FontFileResourceEntry) {
            return param1FontFileResourceEntry.isItalic();
          }
        });
  }
  
  private static <T> T findBestFont(T[] paramArrayOfT, int paramInt, StyleExtractor<T> paramStyleExtractor) {
    // Byte code:
    //   0: iload_1
    //   1: iconst_1
    //   2: iand
    //   3: ifne -> 13
    //   6: sipush #400
    //   9: istore_3
    //   10: goto -> 17
    //   13: sipush #700
    //   16: istore_3
    //   17: iload_1
    //   18: iconst_2
    //   19: iand
    //   20: ifeq -> 29
    //   23: iconst_1
    //   24: istore #4
    //   26: goto -> 32
    //   29: iconst_0
    //   30: istore #4
    //   32: aconst_null
    //   33: astore #5
    //   35: ldc 2147483647
    //   37: istore #6
    //   39: aload_0
    //   40: arraylength
    //   41: istore #7
    //   43: iconst_0
    //   44: istore_1
    //   45: iload_1
    //   46: iload #7
    //   48: if_icmpge -> 136
    //   51: aload_0
    //   52: iload_1
    //   53: aaload
    //   54: astore #8
    //   56: aload_2
    //   57: aload #8
    //   59: invokeinterface getWeight : (Ljava/lang/Object;)I
    //   64: iload_3
    //   65: isub
    //   66: invokestatic abs : (I)I
    //   69: istore #9
    //   71: aload_2
    //   72: aload #8
    //   74: invokeinterface isItalic : (Ljava/lang/Object;)Z
    //   79: iload #4
    //   81: if_icmpne -> 90
    //   84: iconst_0
    //   85: istore #10
    //   87: goto -> 93
    //   90: iconst_1
    //   91: istore #10
    //   93: iload #9
    //   95: iconst_2
    //   96: imul
    //   97: iload #10
    //   99: iadd
    //   100: istore #9
    //   102: aload #5
    //   104: ifnull -> 118
    //   107: iload #6
    //   109: istore #10
    //   111: iload #6
    //   113: iload #9
    //   115: if_icmple -> 126
    //   118: aload #8
    //   120: astore #5
    //   122: iload #9
    //   124: istore #10
    //   126: iinc #1, 1
    //   129: iload #10
    //   131: istore #6
    //   133: goto -> 45
    //   136: aload #5
    //   138: areturn
  }
  
  public Typeface createFromFontFamilyFilesResourceEntry(Context paramContext, FontResourcesParserCompat.FontFamilyFilesResourceEntry paramFontFamilyFilesResourceEntry, Resources paramResources, int paramInt) {
    FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry = findBestEntry(paramFontFamilyFilesResourceEntry, paramInt);
    return (fontFileResourceEntry == null) ? null : TypefaceCompat.createFromResourcesFontFile(paramContext, paramResources, fontFileResourceEntry.getResourceId(), fontFileResourceEntry.getFileName(), paramInt);
  }
  
  public Typeface createFromFontInfo(Context paramContext, CancellationSignal paramCancellationSignal, FontsContractCompat.FontInfo[] paramArrayOfFontInfo, int paramInt) {
    int i = paramArrayOfFontInfo.length;
    FontsContractCompat.FontInfo fontInfo2 = null;
    if (i < 1)
      return null; 
    FontsContractCompat.FontInfo fontInfo1 = findBestInfo(paramArrayOfFontInfo, paramInt);
    try {
      InputStream inputStream = paramContext.getContentResolver().openInputStream(fontInfo1.getUri());
      try {
        return typeface;
      } catch (IOException iOException) {
      
      } finally {}
      TypefaceCompatUtil.closeQuietly(inputStream);
      throw paramContext;
    } catch (IOException iOException) {
    
    } finally {
      fontInfo1 = fontInfo2;
      TypefaceCompatUtil.closeQuietly((Closeable)fontInfo1);
    } 
    TypefaceCompatUtil.closeQuietly((Closeable)fontInfo1);
    return null;
  }
  
  protected Typeface createFromInputStream(Context paramContext, InputStream paramInputStream) {
    File file = TypefaceCompatUtil.getTempFile(paramContext);
    if (file == null)
      return null; 
    try {
      boolean bool = TypefaceCompatUtil.copyToFile(file, paramInputStream);
      if (!bool)
        return null; 
      return Typeface.createFromFile(file.getPath());
    } catch (RuntimeException runtimeException) {
      return null;
    } finally {
      file.delete();
    } 
  }
  
  public Typeface createFromResourcesFontFile(Context paramContext, Resources paramResources, int paramInt1, String paramString, int paramInt2) {
    File file = TypefaceCompatUtil.getTempFile(paramContext);
    if (file == null)
      return null; 
    try {
      boolean bool = TypefaceCompatUtil.copyToFile(file, paramResources, paramInt1);
      if (!bool)
        return null; 
      return Typeface.createFromFile(file.getPath());
    } catch (RuntimeException runtimeException) {
      return null;
    } finally {
      file.delete();
    } 
  }
  
  protected FontsContractCompat.FontInfo findBestInfo(FontsContractCompat.FontInfo[] paramArrayOfFontInfo, int paramInt) {
    return findBestFont(paramArrayOfFontInfo, paramInt, new StyleExtractor<FontsContractCompat.FontInfo>() {
          public int getWeight(FontsContractCompat.FontInfo param1FontInfo) {
            return param1FontInfo.getWeight();
          }
          
          public boolean isItalic(FontsContractCompat.FontInfo param1FontInfo) {
            return param1FontInfo.isItalic();
          }
        });
  }
  
  private static interface StyleExtractor<T> {
    int getWeight(T param1T);
    
    boolean isItalic(T param1T);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\graphics\TypefaceCompatBaseImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */