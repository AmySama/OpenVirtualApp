package com.swift.sandhook.wrapper;

import android.text.TextUtils;
import com.swift.sandhook.SandHook;
import com.swift.sandhook.annotation.HookClass;
import com.swift.sandhook.annotation.HookReflectClass;
import com.swift.sandhook.annotation.MethodParams;
import com.swift.sandhook.annotation.MethodReflectParams;
import com.swift.sandhook.annotation.Param;
import com.swift.sandhook.annotation.SkipParamCheck;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HookWrapper {
  public static void addHookClass(ClassLoader paramClassLoader, Class<?> paramClass) throws HookErrorException {
    Class clazz = getTargetHookClass(paramClassLoader, paramClass);
    if (clazz != null) {
      Map<Member, HookEntity> map = getHookMethods(paramClassLoader, clazz, paramClass);
      try {
        fillBackupMethod(paramClassLoader, paramClass, map);
        Iterator<HookEntity> iterator = map.values().iterator();
        return;
      } finally {
        paramClassLoader = null;
      } 
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("error hook wrapper class :");
    stringBuilder.append(paramClass.getName());
    throw new HookErrorException(stringBuilder.toString());
  }
  
  public static void addHookClass(ClassLoader paramClassLoader, Class<?>... paramVarArgs) throws HookErrorException {
    int i = paramVarArgs.length;
    for (byte b = 0; b < i; b++)
      addHookClass(paramClassLoader, paramVarArgs[b]); 
  }
  
  public static void addHookClass(Class<?>... paramVarArgs) throws HookErrorException {
    addHookClass((ClassLoader)null, paramVarArgs);
  }
  
  public static void checkSignature(Member paramMember, Method paramMethod, Class[] paramArrayOfClass) throws HookErrorException {
    if (Modifier.isStatic(paramMethod.getModifiers())) {
      StringBuilder stringBuilder1;
      if (paramMember instanceof java.lang.reflect.Constructor) {
        if (!paramMethod.getReturnType().equals(void.class)) {
          stringBuilder1 = new StringBuilder();
          stringBuilder1.append("error return type! - ");
          stringBuilder1.append(paramMethod.getName());
          throw new HookErrorException(stringBuilder1.toString());
        } 
      } else if (stringBuilder1 instanceof Method) {
        Class<?> clazz = ((Method)stringBuilder1).getReturnType();
        if (clazz != paramMethod.getReturnType() && !clazz.isAssignableFrom(clazz)) {
          stringBuilder1 = new StringBuilder();
          stringBuilder1.append("error return type! - ");
          stringBuilder1.append(paramMethod.getName());
          throw new HookErrorException(stringBuilder1.toString());
        } 
      } 
      Class[] arrayOfClass2 = paramMethod.getParameterTypes();
      byte b1 = 0;
      Class[] arrayOfClass1 = arrayOfClass2;
      if (arrayOfClass2 == null)
        arrayOfClass1 = new Class[0]; 
      arrayOfClass2 = paramArrayOfClass;
      if (paramArrayOfClass == null)
        arrayOfClass2 = new Class[0]; 
      if (arrayOfClass2.length == 0 && arrayOfClass1.length == 0)
        return; 
      boolean bool = Modifier.isStatic(stringBuilder1.getModifiers());
      byte b2 = 1;
      if (!bool) {
        if (arrayOfClass1.length != 0) {
          if (arrayOfClass1[0] == stringBuilder1.getDeclaringClass() || arrayOfClass1[0].isAssignableFrom(stringBuilder1.getDeclaringClass())) {
            if (arrayOfClass1.length != arrayOfClass2.length + 1) {
              stringBuilder1 = new StringBuilder();
              stringBuilder1.append("hook method pars must match the origin method! ");
              stringBuilder1.append(paramMethod.getName());
              throw new HookErrorException(stringBuilder1.toString());
            } 
          } else {
            stringBuilder1 = new StringBuilder();
            stringBuilder1.append("first par must be this! ");
            stringBuilder1.append(paramMethod.getName());
            throw new HookErrorException(stringBuilder1.toString());
          } 
        } else {
          stringBuilder1 = new StringBuilder();
          stringBuilder1.append("first par must be this! ");
          stringBuilder1.append(paramMethod.getName());
          throw new HookErrorException(stringBuilder1.toString());
        } 
      } else if (arrayOfClass1.length == arrayOfClass2.length) {
        b2 = 0;
      } else {
        stringBuilder1 = new StringBuilder();
        stringBuilder1.append("hook method pars must match the origin method! ");
        stringBuilder1.append(paramMethod.getName());
        throw new HookErrorException(stringBuilder1.toString());
      } 
      while (b1 < arrayOfClass2.length) {
        int i = b1 + b2;
        if (arrayOfClass1[i] == arrayOfClass2[b1] || arrayOfClass1[i].isAssignableFrom(arrayOfClass2[b1])) {
          b1++;
          continue;
        } 
        stringBuilder1 = new StringBuilder();
        stringBuilder1.append("hook method pars must match the origin method! ");
        stringBuilder1.append(paramMethod.getName());
        throw new HookErrorException(stringBuilder1.toString());
      } 
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("hook method must static! - ");
    stringBuilder.append(paramMethod.getName());
    throw new HookErrorException(stringBuilder.toString());
  }
  
  private static Class classNameToClass(String paramString, ClassLoader paramClassLoader) throws ClassNotFoundException {
    byte b;
    switch (paramString.hashCode()) {
      default:
        b = -1;
        break;
      case 109413500:
        if (paramString.equals("short")) {
          b = 7;
          break;
        } 
      case 97526364:
        if (paramString.equals("float")) {
          b = 4;
          break;
        } 
      case 64711720:
        if (paramString.equals("boolean")) {
          b = 0;
          break;
        } 
      case 3327612:
        if (paramString.equals("long")) {
          b = 6;
          break;
        } 
      case 3052374:
        if (paramString.equals("char")) {
          b = 2;
          break;
        } 
      case 3039496:
        if (paramString.equals("byte")) {
          b = 1;
          break;
        } 
      case 104431:
        if (paramString.equals("int")) {
          b = 5;
          break;
        } 
      case -1325958191:
        if (paramString.equals("double")) {
          b = 3;
          break;
        } 
    } 
    switch (b) {
      default:
        if (paramClassLoader == null)
          return Class.forName(paramString); 
        break;
      case 7:
        return short.class;
      case 6:
        return long.class;
      case 5:
        return int.class;
      case 4:
        return float.class;
      case 3:
        return double.class;
      case 2:
        return char.class;
      case 1:
        return byte.class;
      case 0:
        return boolean.class;
    } 
    return Class.forName(paramString, true, paramClassLoader);
  }
  
  private static void fillBackupMethod(ClassLoader paramClassLoader, Class<?> paramClass, Map<Member, HookEntity> paramMap) {
    try {
      Field[] arrayOfField = paramClass.getDeclaredFields();
    } finally {
      paramClass = null;
    } 
  }
  
  private static Map<Member, HookEntity> getHookMethods(ClassLoader paramClassLoader, Class paramClass, Class<?> paramClass1) throws HookErrorException {
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    try {
      Method[] arrayOfMethod = paramClass1.getDeclaredMethods();
    } finally {
      paramClass1 = null;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("error hook wrapper class :");
    stringBuilder.append(noSuchMethodException.getName());
    throw new HookErrorException(stringBuilder.toString());
  }
  
  private static int getParsCount(Method paramMethod) {
    int i;
    Class[] arrayOfClass = paramMethod.getParameterTypes();
    if (arrayOfClass == null) {
      i = 0;
    } else {
      i = arrayOfClass.length;
    } 
    return i;
  }
  
  private static Class getRealParType(ClassLoader paramClassLoader, Class paramClass, Annotation[] paramArrayOfAnnotation, boolean paramBoolean) throws Exception {
    if (paramArrayOfAnnotation != null && paramArrayOfAnnotation.length != 0) {
      int i = paramArrayOfAnnotation.length;
      for (byte b = 0; b < i; b++) {
        Annotation annotation = paramArrayOfAnnotation[b];
        if (annotation instanceof Param) {
          Param param = (Param)annotation;
          if (TextUtils.isEmpty(param.value()))
            return paramClass; 
          Class<?> clazz = classNameToClass(param.value(), paramClassLoader);
          if (paramBoolean || clazz.equals(paramClass) || paramClass.isAssignableFrom(clazz))
            return clazz; 
          throw new ClassCastException("hook method par cast error!");
        } 
      } 
    } 
    return paramClass;
  }
  
  private static Class getTargetHookClass(ClassLoader paramClassLoader, Class<?> paramClass) {
    HookClass hookClass = paramClass.<HookClass>getAnnotation(HookClass.class);
    HookReflectClass hookReflectClass = paramClass.<HookReflectClass>getAnnotation(HookReflectClass.class);
    if (hookClass != null)
      return hookClass.value(); 
    if (hookReflectClass != null)
      if (paramClassLoader == null) {
        try {
          return Class.forName(hookReflectClass.value());
        } catch (ClassNotFoundException classNotFoundException) {}
      } else {
        return Class.forName(hookReflectClass.value(), true, (ClassLoader)classNotFoundException);
      }  
    return null;
  }
  
  private static boolean hasThisObject(Method paramMethod) {
    Annotation[][] arrayOfAnnotation = paramMethod.getParameterAnnotations();
    return (arrayOfAnnotation == null || arrayOfAnnotation.length == 0) ? false : isThisObject(arrayOfAnnotation[0]);
  }
  
  private static boolean isThisObject(Annotation[] paramArrayOfAnnotation) {
    if (paramArrayOfAnnotation != null && paramArrayOfAnnotation.length != 0) {
      int i = paramArrayOfAnnotation.length;
      for (byte b = 0; b < i; b++) {
        if (paramArrayOfAnnotation[b] instanceof com.swift.sandhook.annotation.ThisObject)
          return true; 
      } 
    } 
    return false;
  }
  
  private static Class[] parseMethodPars(ClassLoader paramClassLoader, Field paramField) throws HookErrorException {
    StringBuilder stringBuilder;
    MethodParams methodParams = paramField.<MethodParams>getAnnotation(MethodParams.class);
    MethodReflectParams methodReflectParams = paramField.<MethodReflectParams>getAnnotation(MethodReflectParams.class);
    if (methodParams != null)
      return methodParams.value(); 
    methodParams = null;
    if (methodReflectParams != null) {
      if ((methodReflectParams.value()).length == 0)
        return null; 
      Class[] arrayOfClass = new Class[(methodReflectParams.value()).length];
      byte b = 0;
      while (true) {
        Class[] arrayOfClass1 = arrayOfClass;
        if (b < (methodReflectParams.value()).length) {
          try {
            arrayOfClass[b] = classNameToClass(methodReflectParams.value()[b], paramClassLoader);
            b++;
          } catch (ClassNotFoundException classNotFoundException) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("hook method pars error: ");
            stringBuilder.append(paramField.getName());
            throw new HookErrorException(stringBuilder.toString(), classNotFoundException);
          } 
          continue;
        } 
        break;
      } 
    } 
    return (Class[])stringBuilder;
  }
  
  private static Class[] parseMethodPars(ClassLoader paramClassLoader, Method paramMethod) throws HookErrorException {
    MethodParams methodParams = paramMethod.<MethodParams>getAnnotation(MethodParams.class);
    MethodReflectParams methodReflectParams = paramMethod.<MethodReflectParams>getAnnotation(MethodReflectParams.class);
    if (methodParams != null)
      return methodParams.value(); 
    if (methodReflectParams != null) {
      if ((methodReflectParams.value()).length == 0)
        return null; 
      Class[] arrayOfClass = new Class[(methodReflectParams.value()).length];
      byte b = 0;
      while (b < (methodReflectParams.value()).length) {
        try {
          arrayOfClass[b] = classNameToClass(methodReflectParams.value()[b], paramClassLoader);
          b++;
        } catch (ClassNotFoundException classNotFoundException) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("hook method pars error: ");
          stringBuilder.append(paramMethod.getName());
          throw new HookErrorException(stringBuilder.toString(), classNotFoundException);
        } 
      } 
      return arrayOfClass;
    } 
    return (getParsCount(paramMethod) > 0) ? ((getParsCount(paramMethod) == 1) ? (hasThisObject(paramMethod) ? parseMethodParsNew((ClassLoader)classNotFoundException, paramMethod) : null) : parseMethodParsNew((ClassLoader)classNotFoundException, paramMethod)) : null;
  }
  
  private static Class[] parseMethodParsNew(ClassLoader paramClassLoader, Method paramMethod) throws HookErrorException {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual getParameterTypes : ()[Ljava/lang/Class;
    //   4: astore_2
    //   5: aconst_null
    //   6: astore_3
    //   7: aconst_null
    //   8: astore #4
    //   10: aload_3
    //   11: astore #5
    //   13: aload_2
    //   14: ifnull -> 172
    //   17: aload_2
    //   18: arraylength
    //   19: ifne -> 28
    //   22: aload_3
    //   23: astore #5
    //   25: goto -> 172
    //   28: aload_1
    //   29: invokevirtual getParameterAnnotations : ()[[Ljava/lang/annotation/Annotation;
    //   32: astore_3
    //   33: iconst_0
    //   34: istore #6
    //   36: iconst_0
    //   37: istore #7
    //   39: aload #4
    //   41: astore #5
    //   43: iload #6
    //   45: aload_3
    //   46: arraylength
    //   47: if_icmpge -> 172
    //   50: aload_2
    //   51: iload #6
    //   53: aaload
    //   54: astore #8
    //   56: aload_3
    //   57: iload #6
    //   59: aaload
    //   60: astore #5
    //   62: iload #6
    //   64: ifne -> 94
    //   67: aload #5
    //   69: invokestatic isThisObject : ([Ljava/lang/annotation/Annotation;)Z
    //   72: ifeq -> 87
    //   75: aload_3
    //   76: arraylength
    //   77: iconst_1
    //   78: isub
    //   79: anewarray java/lang/Class
    //   82: astore #4
    //   84: goto -> 117
    //   87: aload_3
    //   88: arraylength
    //   89: anewarray java/lang/Class
    //   92: astore #4
    //   94: aload #4
    //   96: iload #7
    //   98: aload_0
    //   99: aload #8
    //   101: aload #5
    //   103: aload_1
    //   104: ldc_w com/swift/sandhook/annotation/SkipParamCheck
    //   107: invokevirtual isAnnotationPresent : (Ljava/lang/Class;)Z
    //   110: invokestatic getRealParType : (Ljava/lang/ClassLoader;Ljava/lang/Class;[Ljava/lang/annotation/Annotation;Z)Ljava/lang/Class;
    //   113: aastore
    //   114: iinc #7, 1
    //   117: iinc #6, 1
    //   120: goto -> 39
    //   123: astore #4
    //   125: new java/lang/StringBuilder
    //   128: dup
    //   129: invokespecial <init> : ()V
    //   132: astore_0
    //   133: aload_0
    //   134: ldc_w 'hook method <'
    //   137: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   140: pop
    //   141: aload_0
    //   142: aload_1
    //   143: invokevirtual getName : ()Ljava/lang/String;
    //   146: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   149: pop
    //   150: aload_0
    //   151: ldc_w '> parser pars error'
    //   154: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   157: pop
    //   158: new com/swift/sandhook/wrapper/HookErrorException
    //   161: dup
    //   162: aload_0
    //   163: invokevirtual toString : ()Ljava/lang/String;
    //   166: aload #4
    //   168: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   171: athrow
    //   172: aload #5
    //   174: areturn
    // Exception table:
    //   from	to	target	type
    //   94	114	123	java/lang/Exception
  }
  
  private static boolean samePars(ClassLoader paramClassLoader, Field paramField, Class[] paramArrayOfClass) {
    try {
      Class[] arrayOfClass3 = parseMethodPars(paramClassLoader, paramField);
      if (arrayOfClass3 == null && paramField.isAnnotationPresent((Class)SkipParamCheck.class))
        return true; 
      Class[] arrayOfClass1 = paramArrayOfClass;
      if (paramArrayOfClass == null)
        arrayOfClass1 = new Class[0]; 
      Class[] arrayOfClass2 = arrayOfClass3;
      if (arrayOfClass3 == null)
        arrayOfClass2 = new Class[0]; 
      if (arrayOfClass1.length != arrayOfClass2.length)
        return false; 
      for (byte b = 0; b < arrayOfClass1.length; b++) {
        Class clazz1 = arrayOfClass1[b];
        Class clazz2 = arrayOfClass2[b];
        if (clazz1 != clazz2)
          return false; 
      } 
      return true;
    } catch (HookErrorException hookErrorException) {
      return false;
    } 
  }
  
  public static class HookEntity {
    public Method backup;
    
    public boolean backupIsStub = true;
    
    public Method hook;
    
    public boolean hookIsStub = false;
    
    public int hookMode;
    
    public boolean initClass = true;
    
    public Class[] pars;
    
    public boolean resolveDexCache = true;
    
    public Member target;
    
    public HookEntity(Member param1Member) {
      this.target = param1Member;
    }
    
    public HookEntity(Member param1Member, Method param1Method1, Method param1Method2) {
      this.target = param1Member;
      this.hook = param1Method1;
      this.backup = param1Method2;
    }
    
    public HookEntity(Member param1Member, Method param1Method1, Method param1Method2, boolean param1Boolean) {
      this.target = param1Member;
      this.hook = param1Method1;
      this.backup = param1Method2;
      this.resolveDexCache = param1Boolean;
    }
    
    public Object callOrigin(Object param1Object, Object... param1VarArgs) throws Throwable {
      return SandHook.callOriginMethod(this.backupIsStub, this.target, this.backup, param1Object, param1VarArgs);
    }
    
    public boolean isCtor() {
      return this.target instanceof java.lang.reflect.Constructor;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\swift\sandhook\wrapper\HookWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */