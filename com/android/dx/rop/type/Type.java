package com.android.dx.rop.type;

import com.android.dx.util.Hex;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class Type implements TypeBearer, Comparable<Type> {
  public static final Type ANNOTATION;
  
  public static final Type BOOLEAN;
  
  public static final Type BOOLEAN_ARRAY;
  
  public static final Type BOOLEAN_CLASS;
  
  public static final int BT_ADDR = 10;
  
  public static final int BT_BOOLEAN = 1;
  
  public static final int BT_BYTE = 2;
  
  public static final int BT_CHAR = 3;
  
  public static final int BT_COUNT = 11;
  
  public static final int BT_DOUBLE = 4;
  
  public static final int BT_FLOAT = 5;
  
  public static final int BT_INT = 6;
  
  public static final int BT_LONG = 7;
  
  public static final int BT_OBJECT = 9;
  
  public static final int BT_SHORT = 8;
  
  public static final int BT_VOID = 0;
  
  public static final Type BYTE;
  
  public static final Type BYTE_ARRAY;
  
  public static final Type BYTE_CLASS;
  
  public static final Type CHAR;
  
  public static final Type CHARACTER_CLASS;
  
  public static final Type CHAR_ARRAY;
  
  public static final Type CLASS;
  
  public static final Type CLONEABLE;
  
  public static final Type DOUBLE;
  
  public static final Type DOUBLE_ARRAY;
  
  public static final Type DOUBLE_CLASS;
  
  public static final Type FLOAT;
  
  public static final Type FLOAT_ARRAY;
  
  public static final Type FLOAT_CLASS;
  
  public static final Type INT;
  
  public static final Type INTEGER_CLASS;
  
  public static final Type INT_ARRAY;
  
  public static final Type KNOWN_NULL;
  
  public static final Type LONG;
  
  public static final Type LONG_ARRAY;
  
  public static final Type LONG_CLASS;
  
  public static final Type METHOD_HANDLE;
  
  public static final Type METHOD_TYPE;
  
  public static final Type OBJECT;
  
  public static final Type OBJECT_ARRAY;
  
  public static final Type RETURN_ADDRESS;
  
  public static final Type SERIALIZABLE;
  
  public static final Type SHORT;
  
  public static final Type SHORT_ARRAY;
  
  public static final Type SHORT_CLASS;
  
  public static final Type STRING;
  
  public static final Type THROWABLE;
  
  public static final Type VAR_HANDLE;
  
  public static final Type VOID;
  
  public static final Type VOID_CLASS;
  
  private static final ConcurrentMap<String, Type> internTable = new ConcurrentHashMap<String, Type>(10000, 0.75F);
  
  private Type arrayType;
  
  private final int basicType;
  
  private String className;
  
  private Type componentType;
  
  private final String descriptor;
  
  private Type initializedType;
  
  private final int newAt;
  
  static {
    BOOLEAN = new Type("Z", 1);
    BYTE = new Type("B", 2);
    CHAR = new Type("C", 3);
    DOUBLE = new Type("D", 4);
    FLOAT = new Type("F", 5);
    INT = new Type("I", 6);
    LONG = new Type("J", 7);
    SHORT = new Type("S", 8);
    VOID = new Type("V", 0);
    KNOWN_NULL = new Type("<null>", 9);
    RETURN_ADDRESS = new Type("<addr>", 10);
    ANNOTATION = new Type("Ljava/lang/annotation/Annotation;", 9);
    CLASS = new Type("Ljava/lang/Class;", 9);
    CLONEABLE = new Type("Ljava/lang/Cloneable;", 9);
    METHOD_HANDLE = new Type("Ljava/lang/invoke/MethodHandle;", 9);
    METHOD_TYPE = new Type("Ljava/lang/invoke/MethodType;", 9);
    VAR_HANDLE = new Type("Ljava/lang/invoke/VarHandle;", 9);
    OBJECT = new Type("Ljava/lang/Object;", 9);
    SERIALIZABLE = new Type("Ljava/io/Serializable;", 9);
    STRING = new Type("Ljava/lang/String;", 9);
    THROWABLE = new Type("Ljava/lang/Throwable;", 9);
    BOOLEAN_CLASS = new Type("Ljava/lang/Boolean;", 9);
    BYTE_CLASS = new Type("Ljava/lang/Byte;", 9);
    CHARACTER_CLASS = new Type("Ljava/lang/Character;", 9);
    DOUBLE_CLASS = new Type("Ljava/lang/Double;", 9);
    FLOAT_CLASS = new Type("Ljava/lang/Float;", 9);
    INTEGER_CLASS = new Type("Ljava/lang/Integer;", 9);
    LONG_CLASS = new Type("Ljava/lang/Long;", 9);
    SHORT_CLASS = new Type("Ljava/lang/Short;", 9);
    VOID_CLASS = new Type("Ljava/lang/Void;", 9);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("[");
    stringBuilder.append(BOOLEAN.descriptor);
    BOOLEAN_ARRAY = new Type(stringBuilder.toString(), 9);
    stringBuilder = new StringBuilder();
    stringBuilder.append("[");
    stringBuilder.append(BYTE.descriptor);
    BYTE_ARRAY = new Type(stringBuilder.toString(), 9);
    stringBuilder = new StringBuilder();
    stringBuilder.append("[");
    stringBuilder.append(CHAR.descriptor);
    CHAR_ARRAY = new Type(stringBuilder.toString(), 9);
    stringBuilder = new StringBuilder();
    stringBuilder.append("[");
    stringBuilder.append(DOUBLE.descriptor);
    DOUBLE_ARRAY = new Type(stringBuilder.toString(), 9);
    stringBuilder = new StringBuilder();
    stringBuilder.append("[");
    stringBuilder.append(FLOAT.descriptor);
    FLOAT_ARRAY = new Type(stringBuilder.toString(), 9);
    stringBuilder = new StringBuilder();
    stringBuilder.append("[");
    stringBuilder.append(INT.descriptor);
    INT_ARRAY = new Type(stringBuilder.toString(), 9);
    stringBuilder = new StringBuilder();
    stringBuilder.append("[");
    stringBuilder.append(LONG.descriptor);
    LONG_ARRAY = new Type(stringBuilder.toString(), 9);
    stringBuilder = new StringBuilder();
    stringBuilder.append("[");
    stringBuilder.append(OBJECT.descriptor);
    OBJECT_ARRAY = new Type(stringBuilder.toString(), 9);
    stringBuilder = new StringBuilder();
    stringBuilder.append("[");
    stringBuilder.append(SHORT.descriptor);
    SHORT_ARRAY = new Type(stringBuilder.toString(), 9);
    initInterns();
  }
  
  private Type(String paramString, int paramInt) {
    this(paramString, paramInt, -1);
  }
  
  private Type(String paramString, int paramInt1, int paramInt2) {
    if (paramString != null) {
      if (paramInt1 >= 0 && paramInt1 < 11) {
        if (paramInt2 >= -1) {
          this.descriptor = paramString;
          this.basicType = paramInt1;
          this.newAt = paramInt2;
          this.arrayType = null;
          this.componentType = null;
          this.initializedType = null;
          return;
        } 
        throw new IllegalArgumentException("newAt < -1");
      } 
      throw new IllegalArgumentException("bad basicType");
    } 
    throw new NullPointerException("descriptor == null");
  }
  
  public static void clearInternTable() {
    internTable.clear();
    initInterns();
  }
  
  private static void initInterns() {
    putIntern(BOOLEAN);
    putIntern(BYTE);
    putIntern(CHAR);
    putIntern(DOUBLE);
    putIntern(FLOAT);
    putIntern(INT);
    putIntern(LONG);
    putIntern(SHORT);
    putIntern(ANNOTATION);
    putIntern(CLASS);
    putIntern(CLONEABLE);
    putIntern(METHOD_HANDLE);
    putIntern(VAR_HANDLE);
    putIntern(OBJECT);
    putIntern(SERIALIZABLE);
    putIntern(STRING);
    putIntern(THROWABLE);
    putIntern(BOOLEAN_CLASS);
    putIntern(BYTE_CLASS);
    putIntern(CHARACTER_CLASS);
    putIntern(DOUBLE_CLASS);
    putIntern(FLOAT_CLASS);
    putIntern(INTEGER_CLASS);
    putIntern(LONG_CLASS);
    putIntern(SHORT_CLASS);
    putIntern(VOID_CLASS);
    putIntern(BOOLEAN_ARRAY);
    putIntern(BYTE_ARRAY);
    putIntern(CHAR_ARRAY);
    putIntern(DOUBLE_ARRAY);
    putIntern(FLOAT_ARRAY);
    putIntern(INT_ARRAY);
    putIntern(LONG_ARRAY);
    putIntern(OBJECT_ARRAY);
    putIntern(SHORT_ARRAY);
  }
  
  public static Type intern(String paramString) {
    Type type = internTable.get(paramString);
    if (type != null)
      return type; 
    try {
      char c = paramString.charAt(0);
      if (c == '[')
        return intern(paramString.substring(1)).getArrayType(); 
      int i = paramString.length();
      if (c == 'L')
        if (paramString.charAt(--i) == ';') {
          for (c = '\001'; c < i; c++) {
            char c1 = paramString.charAt(c);
            if (c1 != '(' && c1 != ')' && c1 != '.')
              if (c1 != '/') {
                if (c1 != ';' && c1 != '[')
                  continue; 
              } else {
                if (c == '\001' || c == i || paramString.charAt(c - 1) == '/') {
                  StringBuilder stringBuilder2 = new StringBuilder();
                  stringBuilder2.append("bad descriptor: ");
                  stringBuilder2.append(paramString);
                  throw new IllegalArgumentException(stringBuilder2.toString());
                } 
                continue;
              }  
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append("bad descriptor: ");
            stringBuilder1.append(paramString);
            throw new IllegalArgumentException(stringBuilder1.toString());
          } 
          return putIntern(new Type(paramString, 9));
        }  
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("bad descriptor: ");
      stringBuilder.append(paramString);
      throw new IllegalArgumentException(stringBuilder.toString());
    } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
      throw new IllegalArgumentException("descriptor is empty");
    } catch (NullPointerException nullPointerException) {
      throw new NullPointerException("descriptor == null");
    } 
  }
  
  public static Type internClassName(String paramString) {
    if (paramString != null) {
      if (paramString.startsWith("["))
        return intern(paramString); 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append('L');
      stringBuilder.append(paramString);
      stringBuilder.append(';');
      return intern(stringBuilder.toString());
    } 
    throw new NullPointerException("name == null");
  }
  
  public static Type internReturnType(String paramString) {
    try {
      return paramString.equals("V") ? VOID : intern(paramString);
    } catch (NullPointerException nullPointerException) {
      throw new NullPointerException("descriptor == null");
    } 
  }
  
  private static Type putIntern(Type paramType) {
    Type type = internTable.putIfAbsent(paramType.getDescriptor(), paramType);
    if (type != null)
      paramType = type; 
    return paramType;
  }
  
  public Type asUninitialized(int paramInt) {
    if (paramInt >= 0) {
      if (isReference()) {
        if (!isUninitialized()) {
          StringBuilder stringBuilder2 = new StringBuilder();
          stringBuilder2.append('N');
          stringBuilder2.append(Hex.u2(paramInt));
          stringBuilder2.append(this.descriptor);
          Type type = new Type(stringBuilder2.toString(), 9, paramInt);
          type.initializedType = this;
          return putIntern(type);
        } 
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("already uninitialized: ");
        stringBuilder1.append(this.descriptor);
        throw new IllegalArgumentException(stringBuilder1.toString());
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("not a reference type: ");
      stringBuilder.append(this.descriptor);
      throw new IllegalArgumentException(stringBuilder.toString());
    } 
    throw new IllegalArgumentException("newAt < 0");
  }
  
  public int compareTo(Type paramType) {
    return this.descriptor.compareTo(paramType.descriptor);
  }
  
  public boolean equals(Object paramObject) {
    return (this == paramObject) ? true : (!(paramObject instanceof Type) ? false : this.descriptor.equals(((Type)paramObject).descriptor));
  }
  
  public Type getArrayType() {
    if (this.arrayType == null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append('[');
      stringBuilder.append(this.descriptor);
      this.arrayType = putIntern(new Type(stringBuilder.toString(), 9));
    } 
    return this.arrayType;
  }
  
  public int getBasicFrameType() {
    int i = this.basicType;
    return (i != 1 && i != 2 && i != 3 && i != 6 && i != 8) ? i : 6;
  }
  
  public int getBasicType() {
    return this.basicType;
  }
  
  public int getCategory() {
    int i = this.basicType;
    return (i != 4 && i != 7) ? 1 : 2;
  }
  
  public String getClassName() {
    if (this.className == null)
      if (isReference()) {
        if (this.descriptor.charAt(0) == '[') {
          this.className = this.descriptor;
        } else {
          String str = this.descriptor;
          this.className = str.substring(1, str.length() - 1);
        } 
      } else {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("not an object type: ");
        stringBuilder.append(this.descriptor);
        throw new IllegalArgumentException(stringBuilder.toString());
      }  
    return this.className;
  }
  
  public Type getComponentType() {
    if (this.componentType == null)
      if (this.descriptor.charAt(0) == '[') {
        this.componentType = intern(this.descriptor.substring(1));
      } else {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("not an array type: ");
        stringBuilder.append(this.descriptor);
        throw new IllegalArgumentException(stringBuilder.toString());
      }  
    return this.componentType;
  }
  
  public String getDescriptor() {
    return this.descriptor;
  }
  
  public Type getFrameType() {
    int i = this.basicType;
    return (i != 1 && i != 2 && i != 3 && i != 6 && i != 8) ? this : INT;
  }
  
  public Type getInitializedType() {
    Type type = this.initializedType;
    if (type != null)
      return type; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("initialized type: ");
    stringBuilder.append(this.descriptor);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public int getNewAt() {
    return this.newAt;
  }
  
  public Type getType() {
    return this;
  }
  
  public int hashCode() {
    return this.descriptor.hashCode();
  }
  
  public boolean isArray() {
    String str = this.descriptor;
    boolean bool = false;
    if (str.charAt(0) == '[')
      bool = true; 
    return bool;
  }
  
  public boolean isArrayOrKnownNull() {
    return (isArray() || equals(KNOWN_NULL));
  }
  
  public boolean isCategory1() {
    int i = this.basicType;
    return (i != 4 && i != 7);
  }
  
  public boolean isCategory2() {
    int i = this.basicType;
    return !(i != 4 && i != 7);
  }
  
  public boolean isConstant() {
    return false;
  }
  
  public boolean isIntlike() {
    int i = this.basicType;
    return !(i != 1 && i != 2 && i != 3 && i != 6 && i != 8);
  }
  
  public boolean isPrimitive() {
    switch (this.basicType) {
      default:
        return false;
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
        break;
    } 
    return true;
  }
  
  public boolean isReference() {
    boolean bool;
    if (this.basicType == 9) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isUninitialized() {
    boolean bool;
    if (this.newAt >= 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public String toHuman() {
    switch (this.basicType) {
      default:
        return this.descriptor;
      case 9:
        if (isArray()) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(getComponentType().toHuman());
          stringBuilder.append("[]");
          return stringBuilder.toString();
        } 
        return getClassName().replace("/", ".");
      case 8:
        return "short";
      case 7:
        return "long";
      case 6:
        return "int";
      case 5:
        return "float";
      case 4:
        return "double";
      case 3:
        return "char";
      case 2:
        return "byte";
      case 1:
        return "boolean";
      case 0:
        break;
    } 
    return "void";
  }
  
  public String toString() {
    return this.descriptor;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\type\Type.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */