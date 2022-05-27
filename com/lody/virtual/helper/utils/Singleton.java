package com.lody.virtual.helper.utils;

public abstract class Singleton<T> {
  private T mInstance;
  
  protected abstract T create();
  
  public final T get() {
    // Byte code:
    //   0: aload_0
    //   1: getfield mInstance : Ljava/lang/Object;
    //   4: ifnonnull -> 34
    //   7: aload_0
    //   8: monitorenter
    //   9: aload_0
    //   10: getfield mInstance : Ljava/lang/Object;
    //   13: ifnonnull -> 24
    //   16: aload_0
    //   17: aload_0
    //   18: invokevirtual create : ()Ljava/lang/Object;
    //   21: putfield mInstance : Ljava/lang/Object;
    //   24: aload_0
    //   25: monitorexit
    //   26: goto -> 34
    //   29: astore_1
    //   30: aload_0
    //   31: monitorexit
    //   32: aload_1
    //   33: athrow
    //   34: aload_0
    //   35: getfield mInstance : Ljava/lang/Object;
    //   38: areturn
    // Exception table:
    //   from	to	target	type
    //   9	24	29	finally
    //   24	26	29	finally
    //   30	32	29	finally
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\helpe\\utils\Singleton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */