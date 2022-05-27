package android.support.v4.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CancellationSignal;
import android.support.v4.content.res.FontResourcesParserCompat;
import android.support.v4.provider.FontsContractCompat;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.List;

class TypefaceCompatApi24Impl extends TypefaceCompatBaseImpl {
  private static final String ADD_FONT_WEIGHT_STYLE_METHOD = "addFontWeightStyle";
  
  private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
  
  private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
  
  private static final String TAG = "TypefaceCompatApi24Impl";
  
  private static final Method sAddFontWeightStyle;
  
  private static final Method sCreateFromFamiliesWithDefault;
  
  private static final Class sFontFamily;
  
  private static final Constructor sFontFamilyCtor;
  
  static {
    ClassNotFoundException classNotFoundException1;
    ClassNotFoundException classNotFoundException2;
    Constructor<?> constructor = null;
    try {
      Class<?> clazz = Class.forName("android.graphics.FontFamily");
      Constructor<?> constructor1 = clazz.getConstructor(new Class[0]);
      Method method1 = clazz.getMethod("addFontWeightStyle", new Class[] { ByteBuffer.class, int.class, List.class, int.class, boolean.class });
      Method method2 = Typeface.class.getMethod("createFromFamiliesWithDefault", new Class[] { Array.newInstance(clazz, 1).getClass() });
      constructor = constructor1;
    } catch (ClassNotFoundException classNotFoundException) {
      Log.e("TypefaceCompatApi24Impl", classNotFoundException.getClass().getName(), classNotFoundException);
      classNotFoundException1 = null;
      classNotFoundException = classNotFoundException1;
      classNotFoundException2 = classNotFoundException;
    } catch (NoSuchMethodException noSuchMethodException) {}
    sFontFamilyCtor = constructor;
    sFontFamily = (Class)classNotFoundException1;
    sAddFontWeightStyle = (Method)classNotFoundException2;
    sCreateFromFamiliesWithDefault = (Method)noSuchMethodException;
  }
  
  private static boolean addFontWeightStyle(Object paramObject, ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, boolean paramBoolean) {
    try {
      return ((Boolean)sAddFontWeightStyle.invoke(paramObject, new Object[] { paramByteBuffer, Integer.valueOf(paramInt1), null, Integer.valueOf(paramInt2), Boolean.valueOf(paramBoolean) })).booleanValue();
    } catch (IllegalAccessException illegalAccessException) {
    
    } catch (InvocationTargetException invocationTargetException) {}
    throw new RuntimeException(invocationTargetException);
  }
  
  private static Typeface createFromFamiliesWithDefault(Object paramObject) {
    try {
      Object object = Array.newInstance(sFontFamily, 1);
      Array.set(object, 0, paramObject);
      return (Typeface)sCreateFromFamiliesWithDefault.invoke(null, new Object[] { object });
    } catch (IllegalAccessException illegalAccessException) {
    
    } catch (InvocationTargetException invocationTargetException) {}
    throw new RuntimeException(invocationTargetException);
  }
  
  public static boolean isUsable() {
    boolean bool;
    if (sAddFontWeightStyle == null)
      Log.w("TypefaceCompatApi24Impl", "Unable to collect necessary private methods.Fallback to legacy implementation."); 
    if (sAddFontWeightStyle != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private static Object newFamily() {
    try {
      return sFontFamilyCtor.newInstance(new Object[0]);
    } catch (IllegalAccessException illegalAccessException) {
    
    } catch (InstantiationException instantiationException) {
    
    } catch (InvocationTargetException invocationTargetException) {}
    throw new RuntimeException(invocationTargetException);
  }
  
  public Typeface createFromFontFamilyFilesResourceEntry(Context paramContext, FontResourcesParserCompat.FontFamilyFilesResourceEntry paramFontFamilyFilesResourceEntry, Resources paramResources, int paramInt) {
    Object object = newFamily();
    FontResourcesParserCompat.FontFileResourceEntry[] arrayOfFontFileResourceEntry = paramFontFamilyFilesResourceEntry.getEntries();
    int i = arrayOfFontFileResourceEntry.length;
    for (paramInt = 0; paramInt < i; paramInt++) {
      FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry = arrayOfFontFileResourceEntry[paramInt];
      ByteBuffer byteBuffer = TypefaceCompatUtil.copyToDirectBuffer(paramContext, paramResources, fontFileResourceEntry.getResourceId());
      if (byteBuffer == null)
        return null; 
      if (!addFontWeightStyle(object, byteBuffer, 0, fontFileResourceEntry.getWeight(), fontFileResourceEntry.isItalic()))
        return null; 
    } 
    return createFromFamiliesWithDefault(object);
  }
  
  public Typeface createFromFontInfo(Context paramContext, CancellationSignal paramCancellationSignal, FontsContractCompat.FontInfo[] paramArrayOfFontInfo, int paramInt) {
    Object object = newFamily();
    SimpleArrayMap simpleArrayMap = new SimpleArrayMap();
    int i = paramArrayOfFontInfo.length;
    for (byte b = 0; b < i; b++) {
      FontsContractCompat.FontInfo fontInfo = paramArrayOfFontInfo[b];
      Uri uri = fontInfo.getUri();
      ByteBuffer byteBuffer1 = (ByteBuffer)simpleArrayMap.get(uri);
      ByteBuffer byteBuffer2 = byteBuffer1;
      if (byteBuffer1 == null) {
        byteBuffer2 = TypefaceCompatUtil.mmap(paramContext, paramCancellationSignal, uri);
        simpleArrayMap.put(uri, byteBuffer2);
      } 
      if (!addFontWeightStyle(object, byteBuffer2, fontInfo.getTtcIndex(), fontInfo.getWeight(), fontInfo.isItalic()))
        return null; 
    } 
    return Typeface.create(createFromFamiliesWithDefault(object), paramInt);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\graphics\TypefaceCompatApi24Impl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */