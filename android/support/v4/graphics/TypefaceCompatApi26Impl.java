package android.support.v4.graphics;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.support.v4.content.res.FontResourcesParserCompat;
import android.support.v4.provider.FontsContractCompat;
import android.util.Log;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.Map;

public class TypefaceCompatApi26Impl extends TypefaceCompatApi21Impl {
  private static final String ABORT_CREATION_METHOD = "abortCreation";
  
  private static final String ADD_FONT_FROM_ASSET_MANAGER_METHOD = "addFontFromAssetManager";
  
  private static final String ADD_FONT_FROM_BUFFER_METHOD = "addFontFromBuffer";
  
  private static final String CREATE_FROM_FAMILIES_WITH_DEFAULT_METHOD = "createFromFamiliesWithDefault";
  
  private static final String FONT_FAMILY_CLASS = "android.graphics.FontFamily";
  
  private static final String FREEZE_METHOD = "freeze";
  
  private static final int RESOLVE_BY_FONT_TABLE = -1;
  
  private static final String TAG = "TypefaceCompatApi26Impl";
  
  private static final Method sAbortCreation;
  
  private static final Method sAddFontFromAssetManager;
  
  private static final Method sAddFontFromBuffer;
  
  private static final Method sCreateFromFamiliesWithDefault;
  
  private static final Class sFontFamily;
  
  private static final Constructor sFontFamilyCtor;
  
  private static final Method sFreeze;
  
  static {
    ClassNotFoundException classNotFoundException2;
    ClassNotFoundException classNotFoundException3;
    ClassNotFoundException classNotFoundException4;
    ClassNotFoundException classNotFoundException5;
    ClassNotFoundException classNotFoundException6;
    ClassNotFoundException classNotFoundException7;
    ClassNotFoundException classNotFoundException1 = null;
    try {
      Class<?> clazz = Class.forName("android.graphics.FontFamily");
      Constructor<?> constructor = clazz.getConstructor(new Class[0]);
      Method method1 = clazz.getMethod("addFontFromAssetManager", new Class[] { AssetManager.class, String.class, int.class, boolean.class, int.class, int.class, int.class, FontVariationAxis[].class });
      Method method2 = clazz.getMethod("addFontFromBuffer", new Class[] { ByteBuffer.class, int.class, FontVariationAxis[].class, int.class, int.class });
      Method method3 = clazz.getMethod("freeze", new Class[0]);
      Method method4 = clazz.getMethod("abortCreation", new Class[0]);
      Method method5 = Typeface.class.getDeclaredMethod("createFromFamiliesWithDefault", new Class[] { Array.newInstance(clazz, 1).getClass(), int.class, int.class });
      method5.setAccessible(true);
    } catch (ClassNotFoundException classNotFoundException8) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Unable to collect necessary methods for class ");
      stringBuilder.append(classNotFoundException8.getClass().getName());
      Log.e("TypefaceCompatApi26Impl", stringBuilder.toString(), classNotFoundException8);
      ClassNotFoundException classNotFoundException9 = null;
      classNotFoundException8 = classNotFoundException9;
      classNotFoundException3 = classNotFoundException8;
      classNotFoundException4 = classNotFoundException3;
      classNotFoundException2 = classNotFoundException4;
      classNotFoundException6 = classNotFoundException2;
      classNotFoundException5 = classNotFoundException2;
      classNotFoundException7 = classNotFoundException8;
      classNotFoundException2 = classNotFoundException9;
      classNotFoundException8 = classNotFoundException1;
    } catch (NoSuchMethodException noSuchMethodException) {}
    sFontFamilyCtor = (Constructor)noSuchMethodException;
    sFontFamily = (Class)classNotFoundException2;
    sAddFontFromAssetManager = (Method)classNotFoundException3;
    sAddFontFromBuffer = (Method)classNotFoundException4;
    sFreeze = (Method)classNotFoundException5;
    sAbortCreation = (Method)classNotFoundException6;
    sCreateFromFamiliesWithDefault = (Method)classNotFoundException7;
  }
  
  private static void abortCreation(Object paramObject) {
    try {
      sAbortCreation.invoke(paramObject, new Object[0]);
      return;
    } catch (IllegalAccessException illegalAccessException) {
    
    } catch (InvocationTargetException invocationTargetException) {}
    throw new RuntimeException(invocationTargetException);
  }
  
  private static boolean addFontFromAssetManager(Context paramContext, Object paramObject, String paramString, int paramInt1, int paramInt2, int paramInt3) {
    try {
      return ((Boolean)sAddFontFromAssetManager.invoke(paramObject, new Object[] { paramContext.getAssets(), paramString, Integer.valueOf(0), Boolean.valueOf(false), Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), null })).booleanValue();
    } catch (IllegalAccessException illegalAccessException) {
    
    } catch (InvocationTargetException invocationTargetException) {}
    throw new RuntimeException(invocationTargetException);
  }
  
  private static boolean addFontFromBuffer(Object paramObject, ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3) {
    try {
      return ((Boolean)sAddFontFromBuffer.invoke(paramObject, new Object[] { paramByteBuffer, Integer.valueOf(paramInt1), null, Integer.valueOf(paramInt2), Integer.valueOf(paramInt3) })).booleanValue();
    } catch (IllegalAccessException illegalAccessException) {
    
    } catch (InvocationTargetException invocationTargetException) {}
    throw new RuntimeException(invocationTargetException);
  }
  
  private static Typeface createFromFamiliesWithDefault(Object paramObject) {
    try {
      Object object = Array.newInstance(sFontFamily, 1);
      Array.set(object, 0, paramObject);
      return (Typeface)sCreateFromFamiliesWithDefault.invoke(null, new Object[] { object, Integer.valueOf(-1), Integer.valueOf(-1) });
    } catch (IllegalAccessException illegalAccessException) {
    
    } catch (InvocationTargetException invocationTargetException) {}
    throw new RuntimeException(invocationTargetException);
  }
  
  private static boolean freeze(Object paramObject) {
    try {
      return ((Boolean)sFreeze.invoke(paramObject, new Object[0])).booleanValue();
    } catch (IllegalAccessException illegalAccessException) {
    
    } catch (InvocationTargetException invocationTargetException) {}
    throw new RuntimeException(invocationTargetException);
  }
  
  private static boolean isFontFamilyPrivateAPIAvailable() {
    boolean bool;
    if (sAddFontFromAssetManager == null)
      Log.w("TypefaceCompatApi26Impl", "Unable to collect necessary private methods. Fallback to legacy implementation."); 
    if (sAddFontFromAssetManager != null) {
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
    if (!isFontFamilyPrivateAPIAvailable())
      return super.createFromFontFamilyFilesResourceEntry(paramContext, paramFontFamilyFilesResourceEntry, paramResources, paramInt); 
    Object object = newFamily();
    FontResourcesParserCompat.FontFileResourceEntry[] arrayOfFontFileResourceEntry = paramFontFamilyFilesResourceEntry.getEntries();
    int i = arrayOfFontFileResourceEntry.length;
    for (paramInt = 0; paramInt < i; paramInt++) {
      FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry = arrayOfFontFileResourceEntry[paramInt];
      if (!addFontFromAssetManager(paramContext, object, fontFileResourceEntry.getFileName(), 0, fontFileResourceEntry.getWeight(), fontFileResourceEntry.isItalic())) {
        abortCreation(object);
        return null;
      } 
    } 
    return !freeze(object) ? null : createFromFamiliesWithDefault(object);
  }
  
  public Typeface createFromFontInfo(Context paramContext, CancellationSignal paramCancellationSignal, FontsContractCompat.FontInfo[] paramArrayOfFontInfo, int paramInt) {
    FontsContractCompat.FontInfo fontInfo;
    if (paramArrayOfFontInfo.length < 1)
      return null; 
    if (!isFontFamilyPrivateAPIAvailable()) {
      fontInfo = findBestInfo(paramArrayOfFontInfo, paramInt);
      ContentResolver contentResolver = paramContext.getContentResolver();
      try {
        ParcelFileDescriptor parcelFileDescriptor = contentResolver.openFileDescriptor(fontInfo.getUri(), "r", paramCancellationSignal);
        if (parcelFileDescriptor == null) {
          if (parcelFileDescriptor != null)
            parcelFileDescriptor.close(); 
          return null;
        } 
        try {
          Typeface.Builder builder = new Typeface.Builder();
          this(parcelFileDescriptor.getFileDescriptor());
          return builder.setWeight(fontInfo.getWeight()).setItalic(fontInfo.isItalic()).build();
        } finally {
          fontInfo = null;
        } 
      } catch (IOException iOException) {
        return null;
      } 
    } 
    Map map = FontsContractCompat.prepareFontData((Context)iOException, (FontsContractCompat.FontInfo[])fontInfo, paramCancellationSignal);
    Object object = newFamily();
    int i = fontInfo.length;
    byte b = 0;
    boolean bool = false;
    while (b < i) {
      FontsContractCompat.FontInfo fontInfo1 = fontInfo[b];
      ByteBuffer byteBuffer = (ByteBuffer)map.get(fontInfo1.getUri());
      if (byteBuffer != null) {
        if (!addFontFromBuffer(object, byteBuffer, fontInfo1.getTtcIndex(), fontInfo1.getWeight(), fontInfo1.isItalic())) {
          abortCreation(object);
          return null;
        } 
        bool = true;
      } 
      b++;
    } 
    if (!bool) {
      abortCreation(object);
      return null;
    } 
    return !freeze(object) ? null : Typeface.create(createFromFamiliesWithDefault(object), paramInt);
  }
  
  public Typeface createFromResourcesFontFile(Context paramContext, Resources paramResources, int paramInt1, String paramString, int paramInt2) {
    if (!isFontFamilyPrivateAPIAvailable())
      return super.createFromResourcesFontFile(paramContext, paramResources, paramInt1, paramString, paramInt2); 
    Object object = newFamily();
    if (!addFontFromAssetManager(paramContext, object, paramString, 0, -1, -1)) {
      abortCreation(object);
      return null;
    } 
    return !freeze(object) ? null : createFromFamiliesWithDefault(object);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\graphics\TypefaceCompatApi26Impl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */