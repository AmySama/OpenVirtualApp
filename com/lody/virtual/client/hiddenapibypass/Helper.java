package com.lody.virtual.client.hiddenapibypass;

import java.lang.invoke.MethodHandleInfo;
import java.lang.invoke.MethodType;
import java.lang.reflect.Member;

public class Helper {
  public static final class Class {
    private transient int accessFlags;
    
    private transient int classFlags;
    
    private transient ClassLoader classLoader;
    
    private transient int classSize;
    
    private transient int clinitThreadId;
    
    private transient Class<?> componentType;
    
    private transient short copiedMethodsOffset;
    
    private transient Object dexCache;
    
    private transient int dexClassDefIndex;
    
    private volatile transient int dexTypeIndex;
    
    private transient Object extData;
    
    private transient long iFields;
    
    private transient Object[] ifTable;
    
    private transient long methods;
    
    private transient String name;
    
    private transient int numReferenceInstanceFields;
    
    private transient int numReferenceStaticFields;
    
    private transient int objectSize;
    
    private transient int objectSizeAllocFastPath;
    
    private transient int primitiveType;
    
    private transient int referenceInstanceOffsets;
    
    private transient long sFields;
    
    private transient int status;
    
    private transient Class<?> superClass;
    
    private transient short virtualMethodsOffset;
    
    private transient Object vtable;
  }
  
  public static final class HandleInfo {
    private final Helper.MethodHandle handle = null;
    
    private final Member member = null;
  }
  
  public static class MethodHandle {
    protected final long artFieldOrMethod = 0L;
    
    private MethodHandle cachedSpreadInvoker;
    
    protected final int handleKind = 0;
    
    private MethodType nominalType;
    
    private final MethodType type = null;
  }
  
  public static final class MethodHandleImpl extends MethodHandle {
    private final MethodHandleInfo info = null;
  }
  
  public static class NeverCall {
    static void a() {}
    
    static void b() {}
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\hiddenapibypass\Helper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */