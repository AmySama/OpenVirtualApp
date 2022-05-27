package com.android.dx;

import com.android.dx.rop.cst.CstType;
import com.android.dx.rop.type.Type;
import java.util.HashMap;
import java.util.Map;

public final class TypeId<T> {
  public static final TypeId<Boolean> BOOLEAN = new TypeId(Type.BOOLEAN);
  
  public static final TypeId<Byte> BYTE = new TypeId(Type.BYTE);
  
  public static final TypeId<Character> CHAR = new TypeId(Type.CHAR);
  
  public static final TypeId<Double> DOUBLE = new TypeId(Type.DOUBLE);
  
  public static final TypeId<Float> FLOAT = new TypeId(Type.FLOAT);
  
  public static final TypeId<Integer> INT = new TypeId(Type.INT);
  
  public static final TypeId<Long> LONG = new TypeId(Type.LONG);
  
  public static final TypeId<Object> OBJECT;
  
  private static final Map<Class<?>, TypeId<?>> PRIMITIVE_TO_TYPE;
  
  public static final TypeId<Short> SHORT = new TypeId(Type.SHORT);
  
  public static final TypeId<String> STRING;
  
  public static final TypeId<Void> VOID = new TypeId(Type.VOID);
  
  final CstType constant;
  
  final String name;
  
  final Type ropType;
  
  static {
    OBJECT = new TypeId(Type.OBJECT);
    STRING = new TypeId(Type.STRING);
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    PRIMITIVE_TO_TYPE = (Map)hashMap;
    hashMap.put(boolean.class, BOOLEAN);
    PRIMITIVE_TO_TYPE.put(byte.class, BYTE);
    PRIMITIVE_TO_TYPE.put(char.class, CHAR);
    PRIMITIVE_TO_TYPE.put(double.class, DOUBLE);
    PRIMITIVE_TO_TYPE.put(float.class, FLOAT);
    PRIMITIVE_TO_TYPE.put(int.class, INT);
    PRIMITIVE_TO_TYPE.put(long.class, LONG);
    PRIMITIVE_TO_TYPE.put(short.class, SHORT);
    PRIMITIVE_TO_TYPE.put(void.class, VOID);
  }
  
  TypeId(Type paramType) {
    this(paramType.getDescriptor(), paramType);
  }
  
  TypeId(String paramString, Type paramType) {
    if (paramString != null && paramType != null) {
      this.name = paramString;
      this.ropType = paramType;
      this.constant = CstType.intern(paramType);
      return;
    } 
    throw null;
  }
  
  public static <T> TypeId<T> get(Class<T> paramClass) {
    String str1;
    if (paramClass.isPrimitive())
      return (TypeId<T>)PRIMITIVE_TO_TYPE.get(paramClass); 
    String str2 = paramClass.getName().replace('.', '/');
    if (paramClass.isArray()) {
      str1 = str2;
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append('L');
      stringBuilder.append(str2);
      stringBuilder.append(';');
      str1 = stringBuilder.toString();
    } 
    return get(str1);
  }
  
  public static <T> TypeId<T> get(String paramString) {
    return new TypeId<T>(paramString, Type.internReturnType(paramString));
  }
  
  public boolean equals(Object paramObject) {
    boolean bool;
    if (paramObject instanceof TypeId && ((TypeId)paramObject).name.equals(this.name)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public MethodId<T, Void> getConstructor(TypeId<?>... paramVarArgs) {
    return new MethodId<T, Void>(this, VOID, "<init>", new TypeList(paramVarArgs));
  }
  
  public <V> FieldId<T, V> getField(TypeId<V> paramTypeId, String paramString) {
    return new FieldId<T, V>(this, paramTypeId, paramString);
  }
  
  public <R> MethodId<T, R> getMethod(TypeId<R> paramTypeId, String paramString, TypeId<?>... paramVarArgs) {
    return new MethodId<T, R>(this, paramTypeId, paramString, new TypeList(paramVarArgs));
  }
  
  public String getName() {
    return this.name;
  }
  
  public MethodId<T, Void> getStaticInitializer() {
    return new MethodId<T, Void>(this, VOID, "<clinit>", new TypeList((TypeId<?>[])new TypeId[0]));
  }
  
  public int hashCode() {
    return this.name.hashCode();
  }
  
  public String toString() {
    return this.name;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\TypeId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */