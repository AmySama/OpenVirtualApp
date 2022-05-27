package de.robv.android.xposed;

import com.swift.sandhook.SandHook;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class XposedBridge {
  public static final ClassLoader BOOTCLASSLOADER = XposedBridge.class.getClassLoader();
  
  static long BOOT_START_TIME = 0L;
  
  private static final Object[] EMPTY_ARRAY;
  
  private static final int RUNTIME_ART = 2;
  
  private static final int RUNTIME_DALVIK = 1;
  
  public static final String TAG = "SandXposed";
  
  @Deprecated
  public static int XPOSED_BRIDGE_VERSION;
  
  public static boolean disableHooks;
  
  static boolean isZygote = true;
  
  private static int runtime = 2;
  
  public static final Map<Member, CopyOnWriteSortedSet<XC_MethodHook>> sHookedMethodCallbacks;
  
  static final CopyOnWriteSortedSet<XC_InitPackageResources> sInitPackageResourcesCallbacks;
  
  public static final CopyOnWriteSortedSet<XC_LoadPackage> sLoadedPackageCallbacks;
  
  static {
    disableHooks = false;
    EMPTY_ARRAY = new Object[0];
    sHookedMethodCallbacks = new HashMap<Member, CopyOnWriteSortedSet<XC_MethodHook>>();
    sLoadedPackageCallbacks = new CopyOnWriteSortedSet<XC_LoadPackage>();
    sInitPackageResourcesCallbacks = new CopyOnWriteSortedSet<XC_InitPackageResources>();
  }
  
  private static File ensureSuperDexFile(String paramString, Class<?> paramClass1, Class<?> paramClass2) throws IOException {
    return null;
  }
  
  public static int getXposedVersion() {
    return 90;
  }
  
  public static Set<XC_MethodHook.Unhook> hookAllConstructors(Class<?> paramClass, XC_MethodHook paramXC_MethodHook) {
    HashSet<XC_MethodHook.Unhook> hashSet = new HashSet();
    Constructor[] arrayOfConstructor = (Constructor[])paramClass.getDeclaredConstructors();
    int i = arrayOfConstructor.length;
    for (byte b = 0; b < i; b++)
      hashSet.add(hookMethod(arrayOfConstructor[b], paramXC_MethodHook)); 
    return hashSet;
  }
  
  public static Set<XC_MethodHook.Unhook> hookAllMethods(Class<?> paramClass, String paramString, XC_MethodHook paramXC_MethodHook) {
    HashSet<XC_MethodHook.Unhook> hashSet = new HashSet();
    for (Method method : paramClass.getDeclaredMethods()) {
      if (method.getName().equals(paramString))
        hashSet.add(hookMethod(method, paramXC_MethodHook)); 
    } 
    return hashSet;
  }
  
  public static void hookInitPackageResources(XC_InitPackageResources paramXC_InitPackageResources) {}
  
  public static void hookLoadPackage(XC_LoadPackage paramXC_LoadPackage) {
    synchronized (sLoadedPackageCallbacks) {
      sLoadedPackageCallbacks.add(paramXC_LoadPackage);
      return;
    } 
  }
  
  public static XC_MethodHook.Unhook hookMethod(Member paramMember, XC_MethodHook paramXC_MethodHook) {
    boolean bool = paramMember instanceof Method;
    if (bool || paramMember instanceof Constructor) {
      if (!paramMember.getDeclaringClass().isInterface()) {
        if (!Modifier.isAbstract(paramMember.getModifiers())) {
          if (paramXC_MethodHook != null)
            synchronized (sHookedMethodCallbacks) {
              int i;
              CopyOnWriteSortedSet<XC_MethodHook> copyOnWriteSortedSet = sHookedMethodCallbacks.get(paramMember);
              byte b = 0;
              if (copyOnWriteSortedSet == null) {
                copyOnWriteSortedSet = new CopyOnWriteSortedSet();
                this();
                sHookedMethodCallbacks.put(paramMember, copyOnWriteSortedSet);
                i = 1;
              } else {
                i = 0;
              } 
              copyOnWriteSortedSet.add(paramXC_MethodHook);
              if (i) {
                Class[] arrayOfClass;
                Class<?> clazz = paramMember.getDeclaringClass();
                if (runtime == 2) {
                  null = null;
                  Map<Member, CopyOnWriteSortedSet<XC_MethodHook>> map = null;
                  i = b;
                } else if (bool) {
                  i = XposedHelpers.getIntField(paramMember, "slot");
                  Method method = (Method)paramMember;
                  arrayOfClass = method.getParameterTypes();
                  Class<?> clazz1 = method.getReturnType();
                } else {
                  i = XposedHelpers.getIntField(paramMember, "slot");
                  arrayOfClass = ((Constructor)paramMember).getParameterTypes();
                  null = null;
                } 
                hookMethodNative(paramMember, clazz, i, new AdditionalHookInfo(copyOnWriteSortedSet, arrayOfClass, (Class)null));
              } 
              Objects.requireNonNull(paramXC_MethodHook);
              return new XC_MethodHook.Unhook(paramXC_MethodHook, paramMember);
            }  
          throw new IllegalArgumentException("callback should not be null!");
        } 
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Cannot hook abstract methods: ");
        stringBuilder2.append(paramMember.toString());
        throw new IllegalArgumentException(stringBuilder2.toString());
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append("Cannot hook interfaces: ");
      stringBuilder1.append(paramMember.toString());
      throw new IllegalArgumentException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Only methods and constructors can be hooked: ");
    stringBuilder.append(paramMember.toString());
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  private static void hookMethodNative(Member paramMember, Class<?> paramClass, int paramInt, Object paramObject) {
    // Byte code:
    //   0: ldc de/robv/android/xposed/XposedBridge
    //   2: monitorenter
    //   3: aload_0
    //   4: aload_3
    //   5: checkcast de/robv/android/xposed/XposedBridge$AdditionalHookInfo
    //   8: invokestatic hookMethod : (Ljava/lang/reflect/Member;Lde/robv/android/xposed/XposedBridge$AdditionalHookInfo;)V
    //   11: ldc de/robv/android/xposed/XposedBridge
    //   13: monitorexit
    //   14: return
    //   15: astore_0
    //   16: ldc de/robv/android/xposed/XposedBridge
    //   18: monitorexit
    //   19: aload_0
    //   20: athrow
    // Exception table:
    //   from	to	target	type
    //   3	11	15	finally
  }
  
  private static void initXResources() throws IOException {}
  
  public static Object invokeOriginalMethod(Member paramMember, Object paramObject, Object[] paramArrayOfObject) throws NullPointerException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    try {
      return SandHook.callOriginMethod(paramMember, paramObject, paramArrayOfObject);
    } catch (NullPointerException nullPointerException) {
      throw nullPointerException;
    } catch (IllegalAccessException illegalAccessException) {
      throw illegalAccessException;
    } catch (IllegalArgumentException illegalArgumentException) {
      throw illegalArgumentException;
    } catch (InvocationTargetException invocationTargetException) {
      throw invocationTargetException;
    } finally {
      paramMember = null;
    } 
  }
  
  public static void log(String paramString) {
    // Byte code:
    //   0: ldc de/robv/android/xposed/XposedBridge
    //   2: monitorenter
    //   3: getstatic com/swift/sandhook/xposedcompat/utils/DexLog.DEBUG : Z
    //   6: ifeq -> 16
    //   9: ldc 'SandXposed'
    //   11: aload_0
    //   12: invokestatic i : (Ljava/lang/String;Ljava/lang/String;)I
    //   15: pop
    //   16: ldc de/robv/android/xposed/XposedBridge
    //   18: monitorexit
    //   19: return
    //   20: astore_0
    //   21: ldc de/robv/android/xposed/XposedBridge
    //   23: monitorexit
    //   24: aload_0
    //   25: athrow
    // Exception table:
    //   from	to	target	type
    //   3	16	20	finally
  }
  
  public static void log(Throwable paramThrowable) {
    // Byte code:
    //   0: ldc de/robv/android/xposed/XposedBridge
    //   2: monitorenter
    //   3: getstatic com/swift/sandhook/xposedcompat/utils/DexLog.DEBUG : Z
    //   6: ifeq -> 19
    //   9: ldc 'SandXposed'
    //   11: aload_0
    //   12: invokestatic getStackTraceString : (Ljava/lang/Throwable;)Ljava/lang/String;
    //   15: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)I
    //   18: pop
    //   19: ldc de/robv/android/xposed/XposedBridge
    //   21: monitorexit
    //   22: return
    //   23: astore_0
    //   24: ldc de/robv/android/xposed/XposedBridge
    //   26: monitorexit
    //   27: aload_0
    //   28: athrow
    // Exception table:
    //   from	to	target	type
    //   3	19	23	finally
  }
  
  public static void main(String[] paramArrayOfString) {}
  
  @Deprecated
  public static void unhookMethod(Member paramMember, XC_MethodHook paramXC_MethodHook) {
    synchronized (sHookedMethodCallbacks) {
      CopyOnWriteSortedSet<XC_MethodHook> copyOnWriteSortedSet = sHookedMethodCallbacks.get(paramMember);
      if (copyOnWriteSortedSet == null)
        return; 
      copyOnWriteSortedSet.remove(paramXC_MethodHook);
      return;
    } 
  }
  
  public static class AdditionalHookInfo {
    public final XposedBridge.CopyOnWriteSortedSet<XC_MethodHook> callbacks;
    
    public final Class<?>[] parameterTypes;
    
    public final Class<?> returnType;
    
    private AdditionalHookInfo(XposedBridge.CopyOnWriteSortedSet<XC_MethodHook> param1CopyOnWriteSortedSet, Class<?>[] param1ArrayOfClass, Class<?> param1Class) {
      this.callbacks = param1CopyOnWriteSortedSet;
      this.parameterTypes = param1ArrayOfClass;
      this.returnType = param1Class;
    }
  }
  
  public static final class CopyOnWriteSortedSet<E> {
    private volatile transient Object[] elements = XposedBridge.EMPTY_ARRAY;
    
    private int indexOf(Object param1Object) {
      for (byte b = 0; b < this.elements.length; b++) {
        if (param1Object.equals(this.elements[b]))
          return b; 
      } 
      return -1;
    }
    
    public boolean add(E param1E) {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: aload_1
      //   4: invokespecial indexOf : (Ljava/lang/Object;)I
      //   7: istore_2
      //   8: iload_2
      //   9: iflt -> 16
      //   12: aload_0
      //   13: monitorexit
      //   14: iconst_0
      //   15: ireturn
      //   16: aload_0
      //   17: getfield elements : [Ljava/lang/Object;
      //   20: arraylength
      //   21: iconst_1
      //   22: iadd
      //   23: anewarray java/lang/Object
      //   26: astore_3
      //   27: aload_0
      //   28: getfield elements : [Ljava/lang/Object;
      //   31: iconst_0
      //   32: aload_3
      //   33: iconst_0
      //   34: aload_0
      //   35: getfield elements : [Ljava/lang/Object;
      //   38: arraylength
      //   39: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
      //   42: aload_3
      //   43: aload_0
      //   44: getfield elements : [Ljava/lang/Object;
      //   47: arraylength
      //   48: aload_1
      //   49: aastore
      //   50: aload_3
      //   51: invokestatic sort : ([Ljava/lang/Object;)V
      //   54: aload_0
      //   55: aload_3
      //   56: putfield elements : [Ljava/lang/Object;
      //   59: aload_0
      //   60: monitorexit
      //   61: iconst_1
      //   62: ireturn
      //   63: astore_1
      //   64: aload_0
      //   65: monitorexit
      //   66: aload_1
      //   67: athrow
      // Exception table:
      //   from	to	target	type
      //   2	8	63	finally
      //   16	59	63	finally
    }
    
    public Object[] getSnapshot() {
      return this.elements;
    }
    
    public boolean remove(E param1E) {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: aload_1
      //   4: invokespecial indexOf : (Ljava/lang/Object;)I
      //   7: istore_2
      //   8: iload_2
      //   9: iconst_m1
      //   10: if_icmpne -> 17
      //   13: aload_0
      //   14: monitorexit
      //   15: iconst_0
      //   16: ireturn
      //   17: aload_0
      //   18: getfield elements : [Ljava/lang/Object;
      //   21: arraylength
      //   22: iconst_1
      //   23: isub
      //   24: anewarray java/lang/Object
      //   27: astore_1
      //   28: aload_0
      //   29: getfield elements : [Ljava/lang/Object;
      //   32: iconst_0
      //   33: aload_1
      //   34: iconst_0
      //   35: iload_2
      //   36: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
      //   39: aload_0
      //   40: getfield elements : [Ljava/lang/Object;
      //   43: iload_2
      //   44: iconst_1
      //   45: iadd
      //   46: aload_1
      //   47: iload_2
      //   48: aload_0
      //   49: getfield elements : [Ljava/lang/Object;
      //   52: arraylength
      //   53: iload_2
      //   54: isub
      //   55: iconst_1
      //   56: isub
      //   57: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
      //   60: aload_0
      //   61: aload_1
      //   62: putfield elements : [Ljava/lang/Object;
      //   65: aload_0
      //   66: monitorexit
      //   67: iconst_1
      //   68: ireturn
      //   69: astore_1
      //   70: aload_0
      //   71: monitorexit
      //   72: aload_1
      //   73: athrow
      // Exception table:
      //   from	to	target	type
      //   2	8	69	finally
      //   17	65	69	finally
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\de\robv\android\xposed\XposedBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */