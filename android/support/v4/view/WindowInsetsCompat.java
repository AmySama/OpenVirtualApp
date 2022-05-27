package android.support.v4.view;

import android.graphics.Rect;
import android.os.Build;
import android.view.WindowInsets;

public class WindowInsetsCompat {
  private final Object mInsets;
  
  public WindowInsetsCompat(WindowInsetsCompat paramWindowInsetsCompat) {
    int i = Build.VERSION.SDK_INT;
    WindowInsetsCompat windowInsetsCompat = null;
    if (i >= 20) {
      WindowInsets windowInsets;
      if (paramWindowInsetsCompat == null) {
        paramWindowInsetsCompat = windowInsetsCompat;
      } else {
        windowInsets = new WindowInsets((WindowInsets)paramWindowInsetsCompat.mInsets);
      } 
      this.mInsets = windowInsets;
    } else {
      this.mInsets = null;
    } 
  }
  
  private WindowInsetsCompat(Object paramObject) {
    this.mInsets = paramObject;
  }
  
  static Object unwrap(WindowInsetsCompat paramWindowInsetsCompat) {
    Object object;
    if (paramWindowInsetsCompat == null) {
      paramWindowInsetsCompat = null;
    } else {
      object = paramWindowInsetsCompat.mInsets;
    } 
    return object;
  }
  
  static WindowInsetsCompat wrap(Object paramObject) {
    if (paramObject == null) {
      paramObject = null;
    } else {
      paramObject = new WindowInsetsCompat(paramObject);
    } 
    return (WindowInsetsCompat)paramObject;
  }
  
  public WindowInsetsCompat consumeStableInsets() {
    return (Build.VERSION.SDK_INT >= 21) ? new WindowInsetsCompat(((WindowInsets)this.mInsets).consumeStableInsets()) : null;
  }
  
  public WindowInsetsCompat consumeSystemWindowInsets() {
    return (Build.VERSION.SDK_INT >= 20) ? new WindowInsetsCompat(((WindowInsets)this.mInsets).consumeSystemWindowInsets()) : null;
  }
  
  public boolean equals(Object paramObject) {
    boolean bool = true;
    if (this == paramObject)
      return true; 
    if (paramObject == null || getClass() != paramObject.getClass())
      return false; 
    WindowInsetsCompat windowInsetsCompat = (WindowInsetsCompat)paramObject;
    paramObject = this.mInsets;
    Object object = windowInsetsCompat.mInsets;
    if (paramObject == null) {
      if (object != null)
        bool = false; 
    } else {
      bool = paramObject.equals(object);
    } 
    return bool;
  }
  
  public int getStableInsetBottom() {
    return (Build.VERSION.SDK_INT >= 21) ? ((WindowInsets)this.mInsets).getStableInsetBottom() : 0;
  }
  
  public int getStableInsetLeft() {
    return (Build.VERSION.SDK_INT >= 21) ? ((WindowInsets)this.mInsets).getStableInsetLeft() : 0;
  }
  
  public int getStableInsetRight() {
    return (Build.VERSION.SDK_INT >= 21) ? ((WindowInsets)this.mInsets).getStableInsetRight() : 0;
  }
  
  public int getStableInsetTop() {
    return (Build.VERSION.SDK_INT >= 21) ? ((WindowInsets)this.mInsets).getStableInsetTop() : 0;
  }
  
  public int getSystemWindowInsetBottom() {
    return (Build.VERSION.SDK_INT >= 20) ? ((WindowInsets)this.mInsets).getSystemWindowInsetBottom() : 0;
  }
  
  public int getSystemWindowInsetLeft() {
    return (Build.VERSION.SDK_INT >= 20) ? ((WindowInsets)this.mInsets).getSystemWindowInsetLeft() : 0;
  }
  
  public int getSystemWindowInsetRight() {
    return (Build.VERSION.SDK_INT >= 20) ? ((WindowInsets)this.mInsets).getSystemWindowInsetRight() : 0;
  }
  
  public int getSystemWindowInsetTop() {
    return (Build.VERSION.SDK_INT >= 20) ? ((WindowInsets)this.mInsets).getSystemWindowInsetTop() : 0;
  }
  
  public boolean hasInsets() {
    return (Build.VERSION.SDK_INT >= 20) ? ((WindowInsets)this.mInsets).hasInsets() : false;
  }
  
  public boolean hasStableInsets() {
    return (Build.VERSION.SDK_INT >= 21) ? ((WindowInsets)this.mInsets).hasStableInsets() : false;
  }
  
  public boolean hasSystemWindowInsets() {
    return (Build.VERSION.SDK_INT >= 20) ? ((WindowInsets)this.mInsets).hasSystemWindowInsets() : false;
  }
  
  public int hashCode() {
    int i;
    Object object = this.mInsets;
    if (object == null) {
      i = 0;
    } else {
      i = object.hashCode();
    } 
    return i;
  }
  
  public boolean isConsumed() {
    return (Build.VERSION.SDK_INT >= 21) ? ((WindowInsets)this.mInsets).isConsumed() : false;
  }
  
  public boolean isRound() {
    return (Build.VERSION.SDK_INT >= 20) ? ((WindowInsets)this.mInsets).isRound() : false;
  }
  
  public WindowInsetsCompat replaceSystemWindowInsets(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    return (Build.VERSION.SDK_INT >= 20) ? new WindowInsetsCompat(((WindowInsets)this.mInsets).replaceSystemWindowInsets(paramInt1, paramInt2, paramInt3, paramInt4)) : null;
  }
  
  public WindowInsetsCompat replaceSystemWindowInsets(Rect paramRect) {
    return (Build.VERSION.SDK_INT >= 21) ? new WindowInsetsCompat(((WindowInsets)this.mInsets).replaceSystemWindowInsets(paramRect)) : null;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\view\WindowInsetsCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */