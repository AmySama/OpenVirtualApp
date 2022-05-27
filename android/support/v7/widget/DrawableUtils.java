package android.support.v7.widget;

import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.graphics.drawable.WrappedDrawable;
import android.support.v7.graphics.drawable.DrawableWrapper;
import android.util.Log;
import java.lang.reflect.Field;

public class DrawableUtils {
  public static final Rect INSETS_NONE = new Rect();
  
  private static final String TAG = "DrawableUtils";
  
  private static final String VECTOR_DRAWABLE_CLAZZ_NAME = "android.graphics.drawable.VectorDrawable";
  
  private static Class<?> sInsetsClazz;
  
  static {
    if (Build.VERSION.SDK_INT >= 18)
      try {
        sInsetsClazz = Class.forName("android.graphics.Insets");
      } catch (ClassNotFoundException classNotFoundException) {} 
  }
  
  public static boolean canSafelyMutateDrawable(Drawable paramDrawable) {
    Drawable[] arrayOfDrawable;
    if (Build.VERSION.SDK_INT < 15 && paramDrawable instanceof android.graphics.drawable.InsetDrawable)
      return false; 
    if (Build.VERSION.SDK_INT < 15 && paramDrawable instanceof android.graphics.drawable.GradientDrawable)
      return false; 
    if (Build.VERSION.SDK_INT < 17 && paramDrawable instanceof android.graphics.drawable.LayerDrawable)
      return false; 
    if (paramDrawable instanceof DrawableContainer) {
      Drawable.ConstantState constantState = paramDrawable.getConstantState();
      if (constantState instanceof DrawableContainer.DrawableContainerState) {
        arrayOfDrawable = ((DrawableContainer.DrawableContainerState)constantState).getChildren();
        int i = arrayOfDrawable.length;
        for (byte b = 0; b < i; b++) {
          if (!canSafelyMutateDrawable(arrayOfDrawable[b]))
            return false; 
        } 
      } 
    } else {
      if (arrayOfDrawable instanceof WrappedDrawable)
        return canSafelyMutateDrawable(((WrappedDrawable)arrayOfDrawable).getWrappedDrawable()); 
      if (arrayOfDrawable instanceof DrawableWrapper)
        return canSafelyMutateDrawable(((DrawableWrapper)arrayOfDrawable).getWrappedDrawable()); 
      if (arrayOfDrawable instanceof ScaleDrawable)
        return canSafelyMutateDrawable(((ScaleDrawable)arrayOfDrawable).getDrawable()); 
    } 
    return true;
  }
  
  static void fixDrawable(Drawable paramDrawable) {
    if (Build.VERSION.SDK_INT == 21 && "android.graphics.drawable.VectorDrawable".equals(paramDrawable.getClass().getName()))
      fixVectorDrawableTinting(paramDrawable); 
  }
  
  private static void fixVectorDrawableTinting(Drawable paramDrawable) {
    int[] arrayOfInt = paramDrawable.getState();
    if (arrayOfInt == null || arrayOfInt.length == 0) {
      paramDrawable.setState(ThemeUtils.CHECKED_STATE_SET);
    } else {
      paramDrawable.setState(ThemeUtils.EMPTY_STATE_SET);
    } 
    paramDrawable.setState(arrayOfInt);
  }
  
  public static Rect getOpticalBounds(Drawable paramDrawable) {
    if (sInsetsClazz != null)
      try {
        paramDrawable = DrawableCompat.unwrap(paramDrawable);
        Object object = paramDrawable.getClass().getMethod("getOpticalInsets", new Class[0]).invoke(paramDrawable, new Object[0]);
        if (object != null) {
          Rect rect = new Rect();
          this();
          for (Field field : sInsetsClazz.getFields()) {
            String str = field.getName();
            byte b = -1;
            switch (str.hashCode()) {
              case 108511772:
                if (str.equals("right"))
                  b = 2; 
                break;
              case 3317767:
                if (str.equals("left"))
                  b = 0; 
                break;
              case 115029:
                if (str.equals("top"))
                  b = 1; 
                break;
              case -1383228885:
                if (str.equals("bottom"))
                  b = 3; 
                break;
            } 
            if (b != 0) {
              if (b != 1) {
                if (b != 2) {
                  if (b == 3)
                    rect.bottom = field.getInt(object); 
                } else {
                  rect.right = field.getInt(object);
                } 
              } else {
                rect.top = field.getInt(object);
              } 
            } else {
              rect.left = field.getInt(object);
            } 
          } 
          return rect;
        } 
      } catch (Exception exception) {
        Log.e("DrawableUtils", "Couldn't obtain the optical insets. Ignoring.");
      }  
    return INSETS_NONE;
  }
  
  public static PorterDuff.Mode parseTintMode(int paramInt, PorterDuff.Mode paramMode) {
    if (paramInt != 3) {
      if (paramInt != 5) {
        if (paramInt != 9) {
          switch (paramInt) {
            default:
              return paramMode;
            case 16:
              return PorterDuff.Mode.ADD;
            case 15:
              return PorterDuff.Mode.SCREEN;
            case 14:
              break;
          } 
          return PorterDuff.Mode.MULTIPLY;
        } 
        return PorterDuff.Mode.SRC_ATOP;
      } 
      return PorterDuff.Mode.SRC_IN;
    } 
    return PorterDuff.Mode.SRC_OVER;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\DrawableUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */