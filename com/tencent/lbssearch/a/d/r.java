package com.tencent.lbssearch.a.d;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class r {
  public static String a = "Volley";
  
  public static boolean b = Log.isLoggable("Volley", 2);
  
  public static void a(String paramString, Object... paramVarArgs) {
    Log.d(a, c(paramString, paramVarArgs));
  }
  
  public static void a(Throwable paramThrowable, String paramString, Object... paramVarArgs) {
    Log.e(a, c(paramString, paramVarArgs), paramThrowable);
  }
  
  public static void b(String paramString, Object... paramVarArgs) {
    Log.e(a, c(paramString, paramVarArgs));
  }
  
  private static String c(String paramString, Object... paramVarArgs) {
    String str;
    if (paramVarArgs != null)
      paramString = String.format(Locale.US, paramString, paramVarArgs); 
    StackTraceElement[] arrayOfStackTraceElement = (new Throwable()).fillInStackTrace().getStackTrace();
    byte b = 2;
    while (true) {
      if (b < arrayOfStackTraceElement.length) {
        if (!arrayOfStackTraceElement[b].getClass().equals(r.class)) {
          String str2 = arrayOfStackTraceElement[b].getClassName();
          str2 = str2.substring(str2.lastIndexOf('.') + 1);
          String str3 = str2.substring(str2.lastIndexOf('$') + 1);
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(str3);
          stringBuilder.append(".");
          stringBuilder.append(arrayOfStackTraceElement[b].getMethodName());
          String str1 = stringBuilder.toString();
          break;
        } 
        b++;
        continue;
      } 
      str = "<unknown>";
      break;
    } 
    return String.format(Locale.US, "[%d] %s: %s", new Object[] { Long.valueOf(Thread.currentThread().getId()), str, paramString });
  }
  
  static class a {
    public static final boolean a = r.b;
    
    private final List<a> b = new ArrayList<a>();
    
    private boolean c = false;
    
    private long a() {
      if (this.b.size() == 0)
        return 0L; 
      long l = ((a)this.b.get(0)).c;
      List<a> list = this.b;
      return ((a)list.get(list.size() - 1)).c - l;
    }
    
    public void a(String param1String) {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: iconst_1
      //   4: putfield c : Z
      //   7: aload_0
      //   8: invokespecial a : ()J
      //   11: lstore_2
      //   12: lload_2
      //   13: lconst_0
      //   14: lcmp
      //   15: ifgt -> 21
      //   18: aload_0
      //   19: monitorexit
      //   20: return
      //   21: aload_0
      //   22: getfield b : Ljava/util/List;
      //   25: iconst_0
      //   26: invokeinterface get : (I)Ljava/lang/Object;
      //   31: checkcast com/tencent/lbssearch/a/d/r$a$a
      //   34: getfield c : J
      //   37: lstore #4
      //   39: ldc '(%-4d ms) %s'
      //   41: iconst_2
      //   42: anewarray java/lang/Object
      //   45: dup
      //   46: iconst_0
      //   47: lload_2
      //   48: invokestatic valueOf : (J)Ljava/lang/Long;
      //   51: aastore
      //   52: dup
      //   53: iconst_1
      //   54: aload_1
      //   55: aastore
      //   56: invokestatic a : (Ljava/lang/String;[Ljava/lang/Object;)V
      //   59: aload_0
      //   60: getfield b : Ljava/util/List;
      //   63: invokeinterface iterator : ()Ljava/util/Iterator;
      //   68: astore #6
      //   70: aload #6
      //   72: invokeinterface hasNext : ()Z
      //   77: ifeq -> 138
      //   80: aload #6
      //   82: invokeinterface next : ()Ljava/lang/Object;
      //   87: checkcast com/tencent/lbssearch/a/d/r$a$a
      //   90: astore_1
      //   91: aload_1
      //   92: getfield c : J
      //   95: lstore_2
      //   96: ldc '(+%-4d) [%2d] %s'
      //   98: iconst_3
      //   99: anewarray java/lang/Object
      //   102: dup
      //   103: iconst_0
      //   104: lload_2
      //   105: lload #4
      //   107: lsub
      //   108: invokestatic valueOf : (J)Ljava/lang/Long;
      //   111: aastore
      //   112: dup
      //   113: iconst_1
      //   114: aload_1
      //   115: getfield b : J
      //   118: invokestatic valueOf : (J)Ljava/lang/Long;
      //   121: aastore
      //   122: dup
      //   123: iconst_2
      //   124: aload_1
      //   125: getfield a : Ljava/lang/String;
      //   128: aastore
      //   129: invokestatic a : (Ljava/lang/String;[Ljava/lang/Object;)V
      //   132: lload_2
      //   133: lstore #4
      //   135: goto -> 70
      //   138: aload_0
      //   139: monitorexit
      //   140: return
      //   141: astore_1
      //   142: aload_0
      //   143: monitorexit
      //   144: aload_1
      //   145: athrow
      // Exception table:
      //   from	to	target	type
      //   2	12	141	finally
      //   21	70	141	finally
      //   70	132	141	finally
    }
    
    public void a(String param1String, long param1Long) {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield c : Z
      //   6: ifne -> 43
      //   9: aload_0
      //   10: getfield b : Ljava/util/List;
      //   13: astore #4
      //   15: new com/tencent/lbssearch/a/d/r$a$a
      //   18: astore #5
      //   20: aload #5
      //   22: aload_1
      //   23: lload_2
      //   24: invokestatic elapsedRealtime : ()J
      //   27: invokespecial <init> : (Ljava/lang/String;JJ)V
      //   30: aload #4
      //   32: aload #5
      //   34: invokeinterface add : (Ljava/lang/Object;)Z
      //   39: pop
      //   40: aload_0
      //   41: monitorexit
      //   42: return
      //   43: new java/lang/IllegalStateException
      //   46: astore_1
      //   47: aload_1
      //   48: ldc 'Marker added to finished log'
      //   50: invokespecial <init> : (Ljava/lang/String;)V
      //   53: aload_1
      //   54: athrow
      //   55: astore_1
      //   56: aload_0
      //   57: monitorexit
      //   58: aload_1
      //   59: athrow
      // Exception table:
      //   from	to	target	type
      //   2	40	55	finally
      //   43	55	55	finally
    }
    
    protected void finalize() throws Throwable {
      if (!this.c) {
        a("Request on the loose");
        r.b("Marker log finalized without finish() - uncaught exit point for request", new Object[0]);
      } 
    }
    
    private static class a {
      public final String a;
      
      public final long b;
      
      public final long c;
      
      public a(String param2String, long param2Long1, long param2Long2) {
        this.a = param2String;
        this.b = param2Long1;
        this.c = param2Long2;
      }
    }
  }
  
  private static class a {
    public final String a;
    
    public final long b;
    
    public final long c;
    
    public a(String param1String, long param1Long1, long param1Long2) {
      this.a = param1String;
      this.b = param1Long1;
      this.c = param1Long2;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\d\r.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */