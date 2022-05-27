package com.swift.sandhook;

import com.swift.sandhook.wrapper.HookErrorException;
import com.swift.sandhook.wrapper.HookWrapper;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class PendingHookHandler {
  private static boolean canUsePendingHook;
  
  private static Map<Class, Vector<HookWrapper.HookEntity>> pendingHooks = (Map)new ConcurrentHashMap<Class<?>, Vector<HookWrapper.HookEntity>>();
  
  static {
    if (SandHookConfig.delayHook)
      canUsePendingHook = SandHook.initForPendingHook(); 
  }
  
  public static void addPendingHook(HookWrapper.HookEntity paramHookEntity) {
    // Byte code:
    //   0: ldc com/swift/sandhook/PendingHookHandler
    //   2: monitorenter
    //   3: getstatic com/swift/sandhook/PendingHookHandler.pendingHooks : Ljava/util/Map;
    //   6: aload_0
    //   7: getfield target : Ljava/lang/reflect/Member;
    //   10: invokeinterface getDeclaringClass : ()Ljava/lang/Class;
    //   15: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   20: checkcast java/util/Vector
    //   23: astore_1
    //   24: aload_1
    //   25: astore_2
    //   26: aload_1
    //   27: ifnonnull -> 57
    //   30: new java/util/Vector
    //   33: astore_2
    //   34: aload_2
    //   35: invokespecial <init> : ()V
    //   38: getstatic com/swift/sandhook/PendingHookHandler.pendingHooks : Ljava/util/Map;
    //   41: aload_0
    //   42: getfield target : Ljava/lang/reflect/Member;
    //   45: invokeinterface getDeclaringClass : ()Ljava/lang/Class;
    //   50: aload_2
    //   51: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   56: pop
    //   57: aload_2
    //   58: aload_0
    //   59: invokevirtual add : (Ljava/lang/Object;)Z
    //   62: pop
    //   63: aload_0
    //   64: getfield target : Ljava/lang/reflect/Member;
    //   67: invokestatic addPendingHookNative : (Ljava/lang/reflect/Member;)V
    //   70: ldc com/swift/sandhook/PendingHookHandler
    //   72: monitorexit
    //   73: return
    //   74: astore_0
    //   75: ldc com/swift/sandhook/PendingHookHandler
    //   77: monitorexit
    //   78: aload_0
    //   79: athrow
    // Exception table:
    //   from	to	target	type
    //   3	24	74	finally
    //   30	57	74	finally
    //   57	70	74	finally
  }
  
  public static boolean canWork() {
    boolean bool;
    if (canUsePendingHook && SandHook.canGetObject()) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public static void onClassInit(long paramLong) {
    if (paramLong == 0L)
      return; 
    Class clazz = (Class)SandHook.getObject(paramLong);
    if (clazz == null)
      return; 
    Vector vector = pendingHooks.get(clazz);
    if (vector == null)
      return; 
    for (HookWrapper.HookEntity hookEntity : vector) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("do pending hook for method: ");
      stringBuilder.append(hookEntity.target.toString());
      HookLog.w(stringBuilder.toString());
      try {
        hookEntity.initClass = false;
        SandHook.hook(hookEntity);
      } catch (HookErrorException hookErrorException) {
        HookLog.e("Pending Hook Error!", (Throwable)hookErrorException);
      } 
    } 
    pendingHooks.remove(clazz);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\swift\sandhook\PendingHookHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */