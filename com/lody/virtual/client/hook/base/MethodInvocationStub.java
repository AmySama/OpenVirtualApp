package com.lody.virtual.client.hook.base;

import android.text.TextUtils;
import com.lody.virtual.client.hook.annotations.LogInvocation;
import com.lody.virtual.client.hook.utils.MethodParameterUtils;
import com.lody.virtual.helper.utils.VLog;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MethodInvocationStub<T> {
  private static final String TAG = MethodInvocationStub.class.getSimpleName();
  
  private T mBaseInterface;
  
  private MethodProxy mDefaultProxy;
  
  private Map<String, MethodProxy> mInternalMethodProxies = new HashMap<String, MethodProxy>();
  
  private LogInvocation.Condition mInvocationLoggingCondition = LogInvocation.Condition.NEVER;
  
  private T mProxyInterface;
  
  public MethodInvocationStub(T paramT) {
    this(paramT, (Class[])null);
  }
  
  public MethodInvocationStub(T paramT, Class<?>... paramVarArgs) {
    this.mBaseInterface = paramT;
    if (paramT != null) {
      Class<?>[] arrayOfClass = paramVarArgs;
      if (paramVarArgs == null)
        arrayOfClass = MethodParameterUtils.getAllInterface(paramT.getClass()); 
      this.mProxyInterface = (T)Proxy.newProxyInstance(paramT.getClass().getClassLoader(), arrayOfClass, new HookInvocationHandler());
    } 
  }
  
  public static String argToString(Object paramObject) {
    if (paramObject != null && paramObject.getClass().isArray()) {
      StringBuilder stringBuilder = new StringBuilder();
      paramObject = paramObject;
      for (byte b = 0; b < paramObject.length; b++) {
        stringBuilder.append(paramObject[b]);
        if (b != paramObject.length - 1)
          stringBuilder.append(", "); 
      } 
      return stringBuilder.toString();
    } 
    return String.valueOf(paramObject);
  }
  
  public static String argsToString(Object[] paramArrayOfObject) {
    if (paramArrayOfObject == null)
      return "null"; 
    int i = paramArrayOfObject.length - 1;
    if (i == -1)
      return "<>"; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append('<');
    for (int j = 0;; j++) {
      stringBuilder.append(argToString(paramArrayOfObject[j]));
      if (j == i) {
        stringBuilder.append('>');
        return stringBuilder.toString();
      } 
      stringBuilder.append(", ");
    } 
  }
  
  private void dumpMethodProxies() {
    StringBuilder stringBuilder = new StringBuilder(50);
    stringBuilder.append("*********************");
    Iterator<MethodProxy> iterator = this.mInternalMethodProxies.values().iterator();
    while (iterator.hasNext()) {
      stringBuilder.append(((MethodProxy)iterator.next()).getMethodName());
      stringBuilder.append("\n");
    } 
    stringBuilder.append("*********************");
    VLog.e(TAG, stringBuilder.toString());
  }
  
  public MethodProxy addMethodProxy(MethodProxy paramMethodProxy) {
    if (paramMethodProxy != null && !TextUtils.isEmpty(paramMethodProxy.getMethodName())) {
      if (this.mInternalMethodProxies.containsKey(paramMethodProxy.getMethodName())) {
        VLog.w(TAG, "The Hook(%s, %s) you added has been in existence.", new Object[] { paramMethodProxy.getMethodName(), paramMethodProxy.getClass().getName() });
        return paramMethodProxy;
      } 
      this.mInternalMethodProxies.put(paramMethodProxy.getMethodName(), paramMethodProxy);
    } 
    return paramMethodProxy;
  }
  
  public void copyMethodProxies(MethodInvocationStub paramMethodInvocationStub) {
    this.mInternalMethodProxies.putAll(paramMethodInvocationStub.getAllHooks());
  }
  
  public Map<String, MethodProxy> getAllHooks() {
    return this.mInternalMethodProxies;
  }
  
  public T getBaseInterface() {
    return this.mBaseInterface;
  }
  
  public LogInvocation.Condition getInvocationLoggingCondition() {
    return this.mInvocationLoggingCondition;
  }
  
  public int getMethodProxiesCount() {
    return this.mInternalMethodProxies.size();
  }
  
  public <H extends MethodProxy> H getMethodProxy(String paramString) {
    MethodProxy methodProxy2 = this.mInternalMethodProxies.get(paramString);
    MethodProxy methodProxy1 = methodProxy2;
    if (methodProxy2 == null)
      methodProxy1 = this.mDefaultProxy; 
    return (H)methodProxy1;
  }
  
  public T getProxyInterface() {
    return this.mProxyInterface;
  }
  
  public void removeAllMethodProxies() {
    this.mInternalMethodProxies.clear();
  }
  
  public MethodProxy removeMethodProxy(String paramString) {
    return this.mInternalMethodProxies.remove(paramString);
  }
  
  public void removeMethodProxy(MethodProxy paramMethodProxy) {
    if (paramMethodProxy != null)
      removeMethodProxy(paramMethodProxy.getMethodName()); 
  }
  
  public void setDefaultMethodProxy(MethodProxy paramMethodProxy) {
    this.mDefaultProxy = paramMethodProxy;
  }
  
  public void setInvocationLoggingCondition(LogInvocation.Condition paramCondition) {
    this.mInvocationLoggingCondition = paramCondition;
  }
  
  private class HookInvocationHandler implements InvocationHandler {
    private HookInvocationHandler() {}
    
    public Object invoke(Object param1Object, Method param1Method, Object[] param1ArrayOfObject) throws Throwable {
      // Byte code:
      //   0: aload_0
      //   1: getfield this$0 : Lcom/lody/virtual/client/hook/base/MethodInvocationStub;
      //   4: aload_2
      //   5: invokevirtual getName : ()Ljava/lang/String;
      //   8: invokevirtual getMethodProxy : (Ljava/lang/String;)Lcom/lody/virtual/client/hook/base/MethodProxy;
      //   11: astore #4
      //   13: invokestatic get : ()Lcom/lody/virtual/client/core/VirtualCore;
      //   16: invokevirtual isStartup : ()Z
      //   19: istore #5
      //   21: iconst_1
      //   22: istore #6
      //   24: iload #5
      //   26: ifeq -> 48
      //   29: aload #4
      //   31: ifnull -> 48
      //   34: aload #4
      //   36: invokevirtual isEnable : ()Z
      //   39: ifeq -> 48
      //   42: iconst_1
      //   43: istore #5
      //   45: goto -> 51
      //   48: iconst_0
      //   49: istore #5
      //   51: aload_0
      //   52: getfield this$0 : Lcom/lody/virtual/client/hook/base/MethodInvocationStub;
      //   55: invokestatic access$100 : (Lcom/lody/virtual/client/hook/base/MethodInvocationStub;)Lcom/lody/virtual/client/hook/annotations/LogInvocation$Condition;
      //   58: getstatic com/lody/virtual/client/hook/annotations/LogInvocation$Condition.NEVER : Lcom/lody/virtual/client/hook/annotations/LogInvocation$Condition;
      //   61: if_acmpne -> 89
      //   64: aload #4
      //   66: ifnull -> 83
      //   69: aload #4
      //   71: invokevirtual getInvocationLoggingCondition : ()Lcom/lody/virtual/client/hook/annotations/LogInvocation$Condition;
      //   74: getstatic com/lody/virtual/client/hook/annotations/LogInvocation$Condition.NEVER : Lcom/lody/virtual/client/hook/annotations/LogInvocation$Condition;
      //   77: if_acmpeq -> 83
      //   80: goto -> 89
      //   83: iconst_0
      //   84: istore #7
      //   86: goto -> 92
      //   89: iconst_1
      //   90: istore #7
      //   92: invokestatic get : ()Lcom/lody/virtual/client/core/VirtualCore;
      //   95: invokevirtual isVAppProcess : ()Z
      //   98: ifne -> 104
      //   101: iconst_0
      //   102: istore #7
      //   104: aconst_null
      //   105: astore #8
      //   107: iload #7
      //   109: ifeq -> 169
      //   112: aload_3
      //   113: invokestatic argsToString : ([Ljava/lang/Object;)Ljava/lang/String;
      //   116: astore_1
      //   117: aload_1
      //   118: iconst_1
      //   119: aload_1
      //   120: invokevirtual length : ()I
      //   123: iconst_1
      //   124: isub
      //   125: invokevirtual substring : (II)Ljava/lang/String;
      //   128: astore_1
      //   129: goto -> 171
      //   132: astore_1
      //   133: new java/lang/StringBuilder
      //   136: dup
      //   137: invokespecial <init> : ()V
      //   140: astore #9
      //   142: aload #9
      //   144: ldc ''
      //   146: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   149: pop
      //   150: aload #9
      //   152: aload_1
      //   153: invokevirtual getMessage : ()Ljava/lang/String;
      //   156: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   159: pop
      //   160: aload #9
      //   162: invokevirtual toString : ()Ljava/lang/String;
      //   165: astore_1
      //   166: goto -> 171
      //   169: aconst_null
      //   170: astore_1
      //   171: ldc 'void'
      //   173: astore #9
      //   175: iload #5
      //   177: ifeq -> 245
      //   180: aload #8
      //   182: astore #10
      //   184: aload #4
      //   186: aload_0
      //   187: getfield this$0 : Lcom/lody/virtual/client/hook/base/MethodInvocationStub;
      //   190: invokestatic access$200 : (Lcom/lody/virtual/client/hook/base/MethodInvocationStub;)Ljava/lang/Object;
      //   193: aload_2
      //   194: aload_3
      //   195: invokevirtual beforeCall : (Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Z
      //   198: ifeq -> 245
      //   201: aload #8
      //   203: astore #10
      //   205: aload #4
      //   207: aload_0
      //   208: getfield this$0 : Lcom/lody/virtual/client/hook/base/MethodInvocationStub;
      //   211: invokestatic access$200 : (Lcom/lody/virtual/client/hook/base/MethodInvocationStub;)Ljava/lang/Object;
      //   214: aload_2
      //   215: aload_3
      //   216: invokevirtual call : (Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljava/lang/Object;
      //   219: astore #8
      //   221: aload #8
      //   223: astore #10
      //   225: aload #4
      //   227: aload_0
      //   228: getfield this$0 : Lcom/lody/virtual/client/hook/base/MethodInvocationStub;
      //   231: invokestatic access$200 : (Lcom/lody/virtual/client/hook/base/MethodInvocationStub;)Ljava/lang/Object;
      //   234: aload_2
      //   235: aload_3
      //   236: aload #8
      //   238: invokevirtual afterCall : (Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
      //   241: astore_3
      //   242: goto -> 262
      //   245: aload #8
      //   247: astore #10
      //   249: aload_2
      //   250: aload_0
      //   251: getfield this$0 : Lcom/lody/virtual/client/hook/base/MethodInvocationStub;
      //   254: invokestatic access$200 : (Lcom/lody/virtual/client/hook/base/MethodInvocationStub;)Ljava/lang/Object;
      //   257: aload_3
      //   258: invokevirtual invoke : (Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
      //   261: astore_3
      //   262: iload #7
      //   264: ifeq -> 425
      //   267: aload_0
      //   268: getfield this$0 : Lcom/lody/virtual/client/hook/base/MethodInvocationStub;
      //   271: invokestatic access$100 : (Lcom/lody/virtual/client/hook/base/MethodInvocationStub;)Lcom/lody/virtual/client/hook/annotations/LogInvocation$Condition;
      //   274: iload #5
      //   276: iconst_0
      //   277: invokevirtual getLogLevel : (ZZ)I
      //   280: istore #11
      //   282: iload #11
      //   284: istore #7
      //   286: aload #4
      //   288: ifnull -> 309
      //   291: iload #11
      //   293: aload #4
      //   295: invokevirtual getInvocationLoggingCondition : ()Lcom/lody/virtual/client/hook/annotations/LogInvocation$Condition;
      //   298: iload #5
      //   300: iconst_0
      //   301: invokevirtual getLogLevel : (ZZ)I
      //   304: invokestatic max : (II)I
      //   307: istore #7
      //   309: iload #7
      //   311: iflt -> 425
      //   314: aload_2
      //   315: invokevirtual getReturnType : ()Ljava/lang/Class;
      //   318: getstatic java/lang/Void.TYPE : Ljava/lang/Class;
      //   321: invokevirtual equals : (Ljava/lang/Object;)Z
      //   324: ifeq -> 330
      //   327: goto -> 336
      //   330: aload_3
      //   331: invokestatic argToString : (Ljava/lang/Object;)Ljava/lang/String;
      //   334: astore #9
      //   336: invokestatic access$300 : ()Ljava/lang/String;
      //   339: astore #8
      //   341: new java/lang/StringBuilder
      //   344: dup
      //   345: invokespecial <init> : ()V
      //   348: astore #10
      //   350: aload #10
      //   352: aload_2
      //   353: invokevirtual getDeclaringClass : ()Ljava/lang/Class;
      //   356: invokevirtual getSimpleName : ()Ljava/lang/String;
      //   359: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   362: pop
      //   363: aload #10
      //   365: ldc '.'
      //   367: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   370: pop
      //   371: aload #10
      //   373: aload_2
      //   374: invokevirtual getName : ()Ljava/lang/String;
      //   377: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   380: pop
      //   381: aload #10
      //   383: ldc '('
      //   385: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   388: pop
      //   389: aload #10
      //   391: aload_1
      //   392: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   395: pop
      //   396: aload #10
      //   398: ldc ') => '
      //   400: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   403: pop
      //   404: aload #10
      //   406: aload #9
      //   408: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   411: pop
      //   412: iload #7
      //   414: aload #8
      //   416: aload #10
      //   418: invokevirtual toString : ()Ljava/lang/String;
      //   421: invokestatic println : (ILjava/lang/String;Ljava/lang/String;)I
      //   424: pop
      //   425: aload_3
      //   426: areturn
      //   427: astore_3
      //   428: aload_3
      //   429: astore #8
      //   431: aload_3
      //   432: astore #12
      //   434: aload_3
      //   435: instanceof java/lang/reflect/InvocationTargetException
      //   438: ifeq -> 469
      //   441: aload_3
      //   442: astore #8
      //   444: aload_3
      //   445: astore #12
      //   447: aload_3
      //   448: checkcast java/lang/reflect/InvocationTargetException
      //   451: invokevirtual getTargetException : ()Ljava/lang/Throwable;
      //   454: ifnull -> 469
      //   457: aload_3
      //   458: astore #12
      //   460: aload_3
      //   461: checkcast java/lang/reflect/InvocationTargetException
      //   464: invokevirtual getTargetException : ()Ljava/lang/Throwable;
      //   467: astore #8
      //   469: aload #8
      //   471: astore #12
      //   473: aload #8
      //   475: athrow
      //   476: astore #8
      //   478: iload #7
      //   480: ifeq -> 692
      //   483: aload_0
      //   484: getfield this$0 : Lcom/lody/virtual/client/hook/base/MethodInvocationStub;
      //   487: invokestatic access$100 : (Lcom/lody/virtual/client/hook/base/MethodInvocationStub;)Lcom/lody/virtual/client/hook/annotations/LogInvocation$Condition;
      //   490: astore_3
      //   491: aload #12
      //   493: ifnull -> 502
      //   496: iconst_1
      //   497: istore #13
      //   499: goto -> 505
      //   502: iconst_0
      //   503: istore #13
      //   505: aload_3
      //   506: iload #5
      //   508: iload #13
      //   510: invokevirtual getLogLevel : (ZZ)I
      //   513: istore #11
      //   515: iload #11
      //   517: istore #7
      //   519: aload #4
      //   521: ifnull -> 560
      //   524: aload #4
      //   526: invokevirtual getInvocationLoggingCondition : ()Lcom/lody/virtual/client/hook/annotations/LogInvocation$Condition;
      //   529: astore_3
      //   530: aload #12
      //   532: ifnull -> 542
      //   535: iload #6
      //   537: istore #13
      //   539: goto -> 545
      //   542: iconst_0
      //   543: istore #13
      //   545: iload #11
      //   547: aload_3
      //   548: iload #5
      //   550: iload #13
      //   552: invokevirtual getLogLevel : (ZZ)I
      //   555: invokestatic max : (II)I
      //   558: istore #7
      //   560: iload #7
      //   562: iflt -> 692
      //   565: aload #12
      //   567: ifnonnull -> 598
      //   570: aload_2
      //   571: invokevirtual getReturnType : ()Ljava/lang/Class;
      //   574: getstatic java/lang/Void.TYPE : Ljava/lang/Class;
      //   577: invokevirtual equals : (Ljava/lang/Object;)Z
      //   580: ifeq -> 589
      //   583: aload #9
      //   585: astore_3
      //   586: goto -> 604
      //   589: aload #10
      //   591: invokestatic argToString : (Ljava/lang/Object;)Ljava/lang/String;
      //   594: astore_3
      //   595: goto -> 604
      //   598: aload #12
      //   600: invokevirtual toString : ()Ljava/lang/String;
      //   603: astore_3
      //   604: invokestatic access$300 : ()Ljava/lang/String;
      //   607: astore #9
      //   609: new java/lang/StringBuilder
      //   612: dup
      //   613: invokespecial <init> : ()V
      //   616: astore #10
      //   618: aload #10
      //   620: aload_2
      //   621: invokevirtual getDeclaringClass : ()Ljava/lang/Class;
      //   624: invokevirtual getSimpleName : ()Ljava/lang/String;
      //   627: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   630: pop
      //   631: aload #10
      //   633: ldc '.'
      //   635: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   638: pop
      //   639: aload #10
      //   641: aload_2
      //   642: invokevirtual getName : ()Ljava/lang/String;
      //   645: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   648: pop
      //   649: aload #10
      //   651: ldc '('
      //   653: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   656: pop
      //   657: aload #10
      //   659: aload_1
      //   660: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   663: pop
      //   664: aload #10
      //   666: ldc ') => '
      //   668: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   671: pop
      //   672: aload #10
      //   674: aload_3
      //   675: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   678: pop
      //   679: iload #7
      //   681: aload #9
      //   683: aload #10
      //   685: invokevirtual toString : ()Ljava/lang/String;
      //   688: invokestatic println : (ILjava/lang/String;Ljava/lang/String;)I
      //   691: pop
      //   692: aload #8
      //   694: athrow
      // Exception table:
      //   from	to	target	type
      //   112	129	132	finally
      //   184	201	427	finally
      //   205	221	427	finally
      //   225	242	427	finally
      //   249	262	427	finally
      //   434	441	476	finally
      //   447	457	476	finally
      //   460	469	476	finally
      //   473	476	476	finally
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hook\base\MethodInvocationStub.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */