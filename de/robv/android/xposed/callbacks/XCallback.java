package de.robv.android.xposed.callbacks;

import android.os.Bundle;
import de.robv.android.xposed.XposedBridge;
import java.io.Serializable;

public abstract class XCallback implements Comparable<XCallback> {
  public static final int PRIORITY_DEFAULT = 50;
  
  public static final int PRIORITY_HIGHEST = 10000;
  
  public static final int PRIORITY_LOWEST = -10000;
  
  public final int priority = 50;
  
  @Deprecated
  public XCallback() {}
  
  public XCallback(int paramInt) {}
  
  public static void callAll(Param paramParam) {
    if (paramParam.callbacks != null) {
      for (byte b = 0; b < paramParam.callbacks.length; b++) {
        try {
          ((XCallback)paramParam.callbacks[b]).call(paramParam);
        } finally {
          Exception exception = null;
        } 
      } 
      return;
    } 
    throw new IllegalStateException("This object was not created for use with callAll");
  }
  
  protected void call(Param paramParam) throws Throwable {}
  
  public int compareTo(XCallback paramXCallback) {
    if (this == paramXCallback)
      return 0; 
    int i = paramXCallback.priority;
    int j = this.priority;
    return (i != j) ? (i - j) : ((System.identityHashCode(this) < System.identityHashCode(paramXCallback)) ? -1 : 1);
  }
  
  public static abstract class Param {
    public final Object[] callbacks = null;
    
    private Bundle extra;
    
    @Deprecated
    protected Param() {}
    
    protected Param(XposedBridge.CopyOnWriteSortedSet<? extends XCallback> param1CopyOnWriteSortedSet) {}
    
    public Bundle getExtra() {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield extra : Landroid/os/Bundle;
      //   6: ifnonnull -> 22
      //   9: new android/os/Bundle
      //   12: astore_1
      //   13: aload_1
      //   14: invokespecial <init> : ()V
      //   17: aload_0
      //   18: aload_1
      //   19: putfield extra : Landroid/os/Bundle;
      //   22: aload_0
      //   23: getfield extra : Landroid/os/Bundle;
      //   26: astore_1
      //   27: aload_0
      //   28: monitorexit
      //   29: aload_1
      //   30: areturn
      //   31: astore_1
      //   32: aload_0
      //   33: monitorexit
      //   34: aload_1
      //   35: athrow
      // Exception table:
      //   from	to	target	type
      //   2	22	31	finally
      //   22	27	31	finally
    }
    
    public Object getObjectExtra(String param1String) {
      Serializable serializable = getExtra().getSerializable(param1String);
      return (serializable instanceof SerializeWrapper) ? ((SerializeWrapper)serializable).object : null;
    }
    
    public void setObjectExtra(String param1String, Object param1Object) {
      getExtra().putSerializable(param1String, new SerializeWrapper(param1Object));
    }
    
    private static class SerializeWrapper implements Serializable {
      private static final long serialVersionUID = 1L;
      
      private final Object object;
      
      public SerializeWrapper(Object param2Object) {
        this.object = param2Object;
      }
    }
  }
  
  private static class SerializeWrapper implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final Object object;
    
    public SerializeWrapper(Object param1Object) {
      this.object = param1Object;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\de\robv\android\xposed\callbacks\XCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */