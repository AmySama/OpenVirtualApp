package android.support.v4.content.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.graphics.TypefaceCompat;
import android.support.v4.util.Preconditions;
import android.util.Log;
import android.util.TypedValue;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class ResourcesCompat {
  private static final String TAG = "ResourcesCompat";
  
  public static int getColor(Resources paramResources, int paramInt, Resources.Theme paramTheme) throws Resources.NotFoundException {
    return (Build.VERSION.SDK_INT >= 23) ? paramResources.getColor(paramInt, paramTheme) : paramResources.getColor(paramInt);
  }
  
  public static ColorStateList getColorStateList(Resources paramResources, int paramInt, Resources.Theme paramTheme) throws Resources.NotFoundException {
    return (Build.VERSION.SDK_INT >= 23) ? paramResources.getColorStateList(paramInt, paramTheme) : paramResources.getColorStateList(paramInt);
  }
  
  public static Drawable getDrawable(Resources paramResources, int paramInt, Resources.Theme paramTheme) throws Resources.NotFoundException {
    return (Build.VERSION.SDK_INT >= 21) ? paramResources.getDrawable(paramInt, paramTheme) : paramResources.getDrawable(paramInt);
  }
  
  public static Drawable getDrawableForDensity(Resources paramResources, int paramInt1, int paramInt2, Resources.Theme paramTheme) throws Resources.NotFoundException {
    return (Build.VERSION.SDK_INT >= 21) ? paramResources.getDrawableForDensity(paramInt1, paramInt2, paramTheme) : ((Build.VERSION.SDK_INT >= 15) ? paramResources.getDrawableForDensity(paramInt1, paramInt2) : paramResources.getDrawable(paramInt1));
  }
  
  public static Typeface getFont(Context paramContext, int paramInt) throws Resources.NotFoundException {
    return paramContext.isRestricted() ? null : loadFont(paramContext, paramInt, new TypedValue(), 0, null, null, false);
  }
  
  public static Typeface getFont(Context paramContext, int paramInt1, TypedValue paramTypedValue, int paramInt2, FontCallback paramFontCallback) throws Resources.NotFoundException {
    return paramContext.isRestricted() ? null : loadFont(paramContext, paramInt1, paramTypedValue, paramInt2, paramFontCallback, null, true);
  }
  
  public static void getFont(Context paramContext, int paramInt, FontCallback paramFontCallback, Handler paramHandler) throws Resources.NotFoundException {
    Preconditions.checkNotNull(paramFontCallback);
    if (paramContext.isRestricted()) {
      paramFontCallback.callbackFailAsync(-4, paramHandler);
      return;
    } 
    loadFont(paramContext, paramInt, new TypedValue(), 0, paramFontCallback, paramHandler, false);
  }
  
  private static Typeface loadFont(Context paramContext, int paramInt1, TypedValue paramTypedValue, int paramInt2, FontCallback paramFontCallback, Handler paramHandler, boolean paramBoolean) {
    Resources resources = paramContext.getResources();
    resources.getValue(paramInt1, paramTypedValue, true);
    Typeface typeface = loadFont(paramContext, resources, paramTypedValue, paramInt1, paramInt2, paramFontCallback, paramHandler, paramBoolean);
    if (typeface != null || paramFontCallback != null)
      return typeface; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Font resource ID #0x");
    stringBuilder.append(Integer.toHexString(paramInt1));
    stringBuilder.append(" could not be retrieved.");
    throw new Resources.NotFoundException(stringBuilder.toString());
  }
  
  private static Typeface loadFont(Context paramContext, Resources paramResources, TypedValue paramTypedValue, int paramInt1, int paramInt2, FontCallback paramFontCallback, Handler paramHandler, boolean paramBoolean) {
    StringBuilder stringBuilder2;
    String str;
    if (paramTypedValue.string != null) {
      str = paramTypedValue.string.toString();
      if (!str.startsWith("res/")) {
        if (paramFontCallback != null)
          paramFontCallback.callbackFailAsync(-3, paramHandler); 
        return null;
      } 
      Typeface typeface = TypefaceCompat.findFromCache(paramResources, paramInt1, paramInt2);
      if (typeface != null) {
        if (paramFontCallback != null)
          paramFontCallback.callbackSuccessAsync(typeface, paramHandler); 
        return typeface;
      } 
      try {
        if (str.toLowerCase().endsWith(".xml")) {
          FontResourcesParserCompat.FamilyResourceEntry familyResourceEntry = FontResourcesParserCompat.parse((XmlPullParser)paramResources.getXml(paramInt1), paramResources);
          if (familyResourceEntry == null) {
            Log.e("ResourcesCompat", "Failed to find font-family tag");
            if (paramFontCallback != null)
              paramFontCallback.callbackFailAsync(-3, paramHandler); 
            return null;
          } 
          return TypefaceCompat.createFromResourcesFamilyXml(paramContext, familyResourceEntry, paramResources, paramInt1, paramInt2, paramFontCallback, paramHandler, paramBoolean);
        } 
        Typeface typeface1 = TypefaceCompat.createFromResourcesFontFile(paramContext, paramResources, paramInt1, str, paramInt2);
        if (paramFontCallback != null)
          if (typeface1 != null) {
            paramFontCallback.callbackSuccessAsync(typeface1, paramHandler);
          } else {
            paramFontCallback.callbackFailAsync(-3, paramHandler);
          }  
        return typeface1;
      } catch (XmlPullParserException xmlPullParserException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to parse xml resource ");
        stringBuilder.append(str);
        Log.e("ResourcesCompat", stringBuilder.toString(), (Throwable)xmlPullParserException);
      } catch (IOException iOException) {
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Failed to read xml resource ");
        stringBuilder2.append(str);
        Log.e("ResourcesCompat", stringBuilder2.toString(), iOException);
      } 
      if (paramFontCallback != null)
        paramFontCallback.callbackFailAsync(-3, paramHandler); 
      return null;
    } 
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("Resource \"");
    stringBuilder1.append(stringBuilder2.getResourceName(paramInt1));
    stringBuilder1.append("\" (");
    stringBuilder1.append(Integer.toHexString(paramInt1));
    stringBuilder1.append(") is not a Font: ");
    stringBuilder1.append(str);
    throw new Resources.NotFoundException(stringBuilder1.toString());
  }
  
  public static abstract class FontCallback {
    public final void callbackFailAsync(final int reason, Handler param1Handler) {
      Handler handler = param1Handler;
      if (param1Handler == null)
        handler = new Handler(Looper.getMainLooper()); 
      handler.post(new Runnable() {
            public void run() {
              ResourcesCompat.FontCallback.this.onFontRetrievalFailed(reason);
            }
          });
    }
    
    public final void callbackSuccessAsync(final Typeface typeface, Handler param1Handler) {
      Handler handler = param1Handler;
      if (param1Handler == null)
        handler = new Handler(Looper.getMainLooper()); 
      handler.post(new Runnable() {
            public void run() {
              ResourcesCompat.FontCallback.this.onFontRetrieved(typeface);
            }
          });
    }
    
    public abstract void onFontRetrievalFailed(int param1Int);
    
    public abstract void onFontRetrieved(Typeface param1Typeface);
  }
  
  class null implements Runnable {
    public void run() {
      this.this$0.onFontRetrieved(typeface);
    }
  }
  
  class null implements Runnable {
    public void run() {
      this.this$0.onFontRetrievalFailed(reason);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\content\res\ResourcesCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */