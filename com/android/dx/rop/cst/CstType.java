package com.android.dx.rop.cst;

import com.android.dx.rop.type.Type;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class CstType extends TypedConstant {
  public static final CstType BOOLEAN;
  
  public static final CstType BOOLEAN_ARRAY;
  
  public static final CstType BYTE;
  
  public static final CstType BYTE_ARRAY;
  
  public static final CstType CHARACTER;
  
  public static final CstType CHAR_ARRAY;
  
  public static final CstType DOUBLE;
  
  public static final CstType DOUBLE_ARRAY;
  
  public static final CstType FLOAT;
  
  public static final CstType FLOAT_ARRAY;
  
  public static final CstType INTEGER;
  
  public static final CstType INT_ARRAY;
  
  public static final CstType LONG;
  
  public static final CstType LONG_ARRAY;
  
  public static final CstType METHOD_HANDLE;
  
  public static final CstType OBJECT;
  
  public static final CstType SHORT;
  
  public static final CstType SHORT_ARRAY;
  
  public static final CstType VAR_HANDLE;
  
  public static final CstType VOID;
  
  private static final ConcurrentMap<Type, CstType> interns = new ConcurrentHashMap<Type, CstType>(1000, 0.75F);
  
  private CstString descriptor;
  
  private final Type type;
  
  static {
    OBJECT = new CstType(Type.OBJECT);
    BOOLEAN = new CstType(Type.BOOLEAN_CLASS);
    BYTE = new CstType(Type.BYTE_CLASS);
    CHARACTER = new CstType(Type.CHARACTER_CLASS);
    DOUBLE = new CstType(Type.DOUBLE_CLASS);
    FLOAT = new CstType(Type.FLOAT_CLASS);
    LONG = new CstType(Type.LONG_CLASS);
    INTEGER = new CstType(Type.INTEGER_CLASS);
    SHORT = new CstType(Type.SHORT_CLASS);
    VOID = new CstType(Type.VOID_CLASS);
    BOOLEAN_ARRAY = new CstType(Type.BOOLEAN_ARRAY);
    BYTE_ARRAY = new CstType(Type.BYTE_ARRAY);
    CHAR_ARRAY = new CstType(Type.CHAR_ARRAY);
    DOUBLE_ARRAY = new CstType(Type.DOUBLE_ARRAY);
    FLOAT_ARRAY = new CstType(Type.FLOAT_ARRAY);
    LONG_ARRAY = new CstType(Type.LONG_ARRAY);
    INT_ARRAY = new CstType(Type.INT_ARRAY);
    SHORT_ARRAY = new CstType(Type.SHORT_ARRAY);
    METHOD_HANDLE = new CstType(Type.METHOD_HANDLE);
    VAR_HANDLE = new CstType(Type.VAR_HANDLE);
    initInterns();
  }
  
  public CstType(Type paramType) {
    if (paramType != null) {
      if (paramType != Type.KNOWN_NULL) {
        this.type = paramType;
        this.descriptor = null;
        return;
      } 
      throw new UnsupportedOperationException("KNOWN_NULL is not representable");
    } 
    throw new NullPointerException("type == null");
  }
  
  public static void clearInternTable() {
    interns.clear();
    initInterns();
  }
  
  public static CstType forBoxedPrimitiveType(Type paramType) {
    StringBuilder stringBuilder;
    switch (paramType.getBasicType()) {
      default:
        stringBuilder = new StringBuilder();
        stringBuilder.append("not primitive: ");
        stringBuilder.append(paramType);
        throw new IllegalArgumentException(stringBuilder.toString());
      case 8:
        return SHORT;
      case 7:
        return LONG;
      case 6:
        return INTEGER;
      case 5:
        return FLOAT;
      case 4:
        return DOUBLE;
      case 3:
        return CHARACTER;
      case 2:
        return BYTE;
      case 1:
        return BOOLEAN;
      case 0:
        break;
    } 
    return VOID;
  }
  
  private static void initInterns() {
    internInitial(OBJECT);
    internInitial(BOOLEAN);
    internInitial(BYTE);
    internInitial(CHARACTER);
    internInitial(DOUBLE);
    internInitial(FLOAT);
    internInitial(LONG);
    internInitial(INTEGER);
    internInitial(SHORT);
    internInitial(VOID);
    internInitial(BOOLEAN_ARRAY);
    internInitial(BYTE_ARRAY);
    internInitial(CHAR_ARRAY);
    internInitial(DOUBLE_ARRAY);
    internInitial(FLOAT_ARRAY);
    internInitial(LONG_ARRAY);
    internInitial(INT_ARRAY);
    internInitial(SHORT_ARRAY);
    internInitial(METHOD_HANDLE);
  }
  
  public static CstType intern(Type paramType) {
    CstType cstType2 = new CstType(paramType);
    CstType cstType3 = interns.putIfAbsent(paramType, cstType2);
    CstType cstType1 = cstType2;
    if (cstType3 != null)
      cstType1 = cstType3; 
    return cstType1;
  }
  
  private static void internInitial(CstType paramCstType) {
    if (interns.putIfAbsent(paramCstType.getClassType(), paramCstType) == null)
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Attempted re-init of ");
    stringBuilder.append(paramCstType);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  protected int compareTo0(Constant paramConstant) {
    return this.type.getDescriptor().compareTo(((CstType)paramConstant).type.getDescriptor());
  }
  
  public boolean equals(Object paramObject) {
    boolean bool = paramObject instanceof CstType;
    boolean bool1 = false;
    if (!bool)
      return false; 
    if (this.type == ((CstType)paramObject).type)
      bool1 = true; 
    return bool1;
  }
  
  public Type getClassType() {
    return this.type;
  }
  
  public CstString getDescriptor() {
    if (this.descriptor == null)
      this.descriptor = new CstString(this.type.getDescriptor()); 
    return this.descriptor;
  }
  
  public String getPackageName() {
    String str = getDescriptor().getString();
    int i = str.lastIndexOf('/');
    int j = str.lastIndexOf('[');
    return (i == -1) ? "default" : str.substring(j + 2, i).replace('/', '.');
  }
  
  public Type getType() {
    return Type.CLASS;
  }
  
  public int hashCode() {
    return this.type.hashCode();
  }
  
  public boolean isCategory2() {
    return false;
  }
  
  public String toHuman() {
    return this.type.toHuman();
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("type{");
    stringBuilder.append(toHuman());
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  public String typeName() {
    return "type";
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\cst\CstType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */