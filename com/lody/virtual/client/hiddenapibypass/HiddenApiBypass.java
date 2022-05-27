package com.lody.virtual.client.hiddenapibypass;

import dalvik.system.VMRuntime;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleInfo;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import sun.misc.Unsafe;

public final class HiddenApiBypass {
  private static final String TAG = "HiddenApiBypass";
  
  private static final long artOffset;
  
  private static final long bias;
  
  private static final long infoOffset;
  
  private static final long memberOffset;
  
  private static final long methodsOffset;
  
  private static final Set<String> signaturePrefixes = new HashSet<String>();
  
  private static final long size;
  
  private static final Unsafe unsafe;
  
  static {
    try {
      Unsafe unsafe = (Unsafe)Unsafe.class.getDeclaredMethod("getUnsafe", new Class[0]).invoke(null, new Object[0]);
      unsafe = unsafe;
      artOffset = unsafe.objectFieldOffset(Helper.MethodHandle.class.getDeclaredField("artFieldOrMethod"));
      infoOffset = unsafe.objectFieldOffset(Helper.MethodHandleImpl.class.getDeclaredField("info"));
      methodsOffset = unsafe.objectFieldOffset(Helper.Class.class.getDeclaredField("methods"));
      memberOffset = unsafe.objectFieldOffset(Helper.HandleInfo.class.getDeclaredField("member"));
      MethodHandle methodHandle1 = MethodHandles.lookup().unreflect(Helper.NeverCall.class.getDeclaredMethod("a", new Class[0]));
      MethodHandle methodHandle2 = MethodHandles.lookup().unreflect(Helper.NeverCall.class.getDeclaredMethod("b", new Class[0]));
      long l1 = unsafe.getLong(methodHandle1, artOffset);
      long l2 = unsafe.getLong(methodHandle2, artOffset);
      long l3 = unsafe.getLong(Helper.NeverCall.class, methodsOffset);
      l2 -= l1;
      size = l2;
      bias = l1 - l3 - l2;
      return;
    } catch (ReflectiveOperationException reflectiveOperationException) {
      throw new ExceptionInInitializerError(reflectiveOperationException);
    } 
  }
  
  public static boolean addHiddenApiExemptions(String... paramVarArgs) {
    signaturePrefixes.addAll(Arrays.asList(paramVarArgs));
    paramVarArgs = new String[signaturePrefixes.size()];
    signaturePrefixes.toArray(paramVarArgs);
    return setHiddenApiExemptions(paramVarArgs);
  }
  
  public static boolean clearHiddenApiExemptions() {
    signaturePrefixes.clear();
    return setHiddenApiExemptions(new String[0]);
  }
  
  public static List<Executable> getDeclaredMethods(Class<?> paramClass) {
    ArrayList<Executable> arrayList = new ArrayList();
    if (!paramClass.isPrimitive() && !paramClass.isArray())
      try {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        byte b = 0;
        MethodHandle methodHandle = lookup.unreflect(Helper.NeverCall.class.getDeclaredMethod("a", new Class[0]));
        long l = unsafe.getLong(paramClass, methodsOffset);
        int i = unsafe.getInt(l);
        while (true) {
          if (b < i) {
            long l1 = b;
            long l2 = size;
            long l3 = bias;
            unsafe.putLong(methodHandle, artOffset, l1 * l2 + l + l3);
            unsafe.putObject(methodHandle, infoOffset, null);
            try {
              MethodHandles.lookup().revealDirect(methodHandle);
            } finally {}
            MethodHandleInfo methodHandleInfo = (MethodHandleInfo)unsafe.getObject(methodHandle, infoOffset);
            arrayList.add((Executable)unsafe.getObject(methodHandleInfo, memberOffset));
            b++;
            continue;
          } 
          return arrayList;
        } 
      } catch (NoSuchMethodException|IllegalAccessException noSuchMethodException) {} 
    return arrayList;
  }
  
  public static boolean setHiddenApiExemptions(String... paramVarArgs) {
    List<Executable> list = getDeclaredMethods(VMRuntime.class);
    Optional<Executable> optional2 = list.stream().filter((Predicate)_$$Lambda$HiddenApiBypass$M_xi5mtUnHodZuM0a_3GJzrUxA0.INSTANCE).findFirst();
    Optional<Executable> optional1 = list.stream().filter((Predicate)_$$Lambda$HiddenApiBypass$z1GyfmOvr0CCdFHDNoheSG_V1mw.INSTANCE).findFirst();
    if (optional2.isPresent() && optional1.isPresent()) {
      ((Executable)optional2.get()).setAccessible(true);
      try {
        Object object = ((Method)optional2.get()).invoke(null, new Object[0]);
        ((Executable)optional1.get()).setAccessible(true);
        ((Method)optional1.get()).invoke(object, new Object[] { paramVarArgs });
        return true;
      } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException illegalAccessException) {}
    } 
    return false;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hiddenapibypass\HiddenApiBypass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */