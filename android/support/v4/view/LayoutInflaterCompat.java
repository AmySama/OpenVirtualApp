package android.support.v4.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import java.lang.reflect.Field;

public final class LayoutInflaterCompat {
  static final LayoutInflaterCompatBaseImpl IMPL;
  
  private static final String TAG = "LayoutInflaterCompatHC";
  
  private static boolean sCheckedField;
  
  private static Field sLayoutInflaterFactory2Field;
  
  static {
    if (Build.VERSION.SDK_INT >= 21) {
      IMPL = new LayoutInflaterCompatApi21Impl();
    } else {
      IMPL = new LayoutInflaterCompatBaseImpl();
    } 
  }
  
  static void forceSetFactory2(LayoutInflater paramLayoutInflater, LayoutInflater.Factory2 paramFactory2) {
    if (!sCheckedField) {
      try {
        Field field1 = LayoutInflater.class.getDeclaredField("mFactory2");
        sLayoutInflaterFactory2Field = field1;
        field1.setAccessible(true);
      } catch (NoSuchFieldException noSuchFieldException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("forceSetFactory2 Could not find field 'mFactory2' on class ");
        stringBuilder.append(LayoutInflater.class.getName());
        stringBuilder.append("; inflation may have unexpected results.");
        Log.e("LayoutInflaterCompatHC", stringBuilder.toString(), noSuchFieldException);
      } 
      sCheckedField = true;
    } 
    Field field = sLayoutInflaterFactory2Field;
    if (field != null)
      try {
        field.set(paramLayoutInflater, paramFactory2);
      } catch (IllegalAccessException illegalAccessException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("forceSetFactory2 could not set the Factory2 on LayoutInflater ");
        stringBuilder.append(paramLayoutInflater);
        stringBuilder.append("; inflation may have unexpected results.");
        Log.e("LayoutInflaterCompatHC", stringBuilder.toString(), illegalAccessException);
      }  
  }
  
  @Deprecated
  public static LayoutInflaterFactory getFactory(LayoutInflater paramLayoutInflater) {
    return IMPL.getFactory(paramLayoutInflater);
  }
  
  @Deprecated
  public static void setFactory(LayoutInflater paramLayoutInflater, LayoutInflaterFactory paramLayoutInflaterFactory) {
    IMPL.setFactory(paramLayoutInflater, paramLayoutInflaterFactory);
  }
  
  public static void setFactory2(LayoutInflater paramLayoutInflater, LayoutInflater.Factory2 paramFactory2) {
    IMPL.setFactory2(paramLayoutInflater, paramFactory2);
  }
  
  static class Factory2Wrapper implements LayoutInflater.Factory2 {
    final LayoutInflaterFactory mDelegateFactory;
    
    Factory2Wrapper(LayoutInflaterFactory param1LayoutInflaterFactory) {
      this.mDelegateFactory = param1LayoutInflaterFactory;
    }
    
    public View onCreateView(View param1View, String param1String, Context param1Context, AttributeSet param1AttributeSet) {
      return this.mDelegateFactory.onCreateView(param1View, param1String, param1Context, param1AttributeSet);
    }
    
    public View onCreateView(String param1String, Context param1Context, AttributeSet param1AttributeSet) {
      return this.mDelegateFactory.onCreateView(null, param1String, param1Context, param1AttributeSet);
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(getClass().getName());
      stringBuilder.append("{");
      stringBuilder.append(this.mDelegateFactory);
      stringBuilder.append("}");
      return stringBuilder.toString();
    }
  }
  
  static class LayoutInflaterCompatApi21Impl extends LayoutInflaterCompatBaseImpl {
    public void setFactory(LayoutInflater param1LayoutInflater, LayoutInflaterFactory param1LayoutInflaterFactory) {
      if (param1LayoutInflaterFactory != null) {
        LayoutInflaterCompat.Factory2Wrapper factory2Wrapper = new LayoutInflaterCompat.Factory2Wrapper(param1LayoutInflaterFactory);
      } else {
        param1LayoutInflaterFactory = null;
      } 
      param1LayoutInflater.setFactory2((LayoutInflater.Factory2)param1LayoutInflaterFactory);
    }
    
    public void setFactory2(LayoutInflater param1LayoutInflater, LayoutInflater.Factory2 param1Factory2) {
      param1LayoutInflater.setFactory2(param1Factory2);
    }
  }
  
  static class LayoutInflaterCompatBaseImpl {
    public LayoutInflaterFactory getFactory(LayoutInflater param1LayoutInflater) {
      LayoutInflater.Factory factory = param1LayoutInflater.getFactory();
      return (factory instanceof LayoutInflaterCompat.Factory2Wrapper) ? ((LayoutInflaterCompat.Factory2Wrapper)factory).mDelegateFactory : null;
    }
    
    public void setFactory(LayoutInflater param1LayoutInflater, LayoutInflaterFactory param1LayoutInflaterFactory) {
      if (param1LayoutInflaterFactory != null) {
        LayoutInflaterCompat.Factory2Wrapper factory2Wrapper = new LayoutInflaterCompat.Factory2Wrapper(param1LayoutInflaterFactory);
      } else {
        param1LayoutInflaterFactory = null;
      } 
      setFactory2(param1LayoutInflater, (LayoutInflater.Factory2)param1LayoutInflaterFactory);
    }
    
    public void setFactory2(LayoutInflater param1LayoutInflater, LayoutInflater.Factory2 param1Factory2) {
      param1LayoutInflater.setFactory2(param1Factory2);
      LayoutInflater.Factory factory = param1LayoutInflater.getFactory();
      if (factory instanceof LayoutInflater.Factory2) {
        LayoutInflaterCompat.forceSetFactory2(param1LayoutInflater, (LayoutInflater.Factory2)factory);
      } else {
        LayoutInflaterCompat.forceSetFactory2(param1LayoutInflater, param1Factory2);
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\view\LayoutInflaterCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */