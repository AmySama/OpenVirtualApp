package com.android.dx.rop.type;

import com.android.dx.util.FixedSizeList;

public final class StdTypeList extends FixedSizeList implements TypeList {
  public static final StdTypeList BOOLEANARR_INT;
  
  public static final StdTypeList BYTEARR_INT;
  
  public static final StdTypeList CHARARR_INT;
  
  public static final StdTypeList DOUBLE;
  
  public static final StdTypeList DOUBLEARR_INT;
  
  public static final StdTypeList DOUBLE_DOUBLE;
  
  public static final StdTypeList DOUBLE_DOUBLEARR_INT;
  
  public static final StdTypeList DOUBLE_OBJECT;
  
  public static final StdTypeList EMPTY = new StdTypeList(0);
  
  public static final StdTypeList FLOAT;
  
  public static final StdTypeList FLOATARR_INT;
  
  public static final StdTypeList FLOAT_FLOAT;
  
  public static final StdTypeList FLOAT_FLOATARR_INT;
  
  public static final StdTypeList FLOAT_OBJECT;
  
  public static final StdTypeList INT = make(Type.INT);
  
  public static final StdTypeList INTARR_INT;
  
  public static final StdTypeList INT_BOOLEANARR_INT;
  
  public static final StdTypeList INT_BYTEARR_INT;
  
  public static final StdTypeList INT_CHARARR_INT;
  
  public static final StdTypeList INT_INT;
  
  public static final StdTypeList INT_INTARR_INT;
  
  public static final StdTypeList INT_OBJECT;
  
  public static final StdTypeList INT_SHORTARR_INT;
  
  public static final StdTypeList LONG = make(Type.LONG);
  
  public static final StdTypeList LONGARR_INT;
  
  public static final StdTypeList LONG_INT;
  
  public static final StdTypeList LONG_LONG;
  
  public static final StdTypeList LONG_LONGARR_INT;
  
  public static final StdTypeList LONG_OBJECT;
  
  public static final StdTypeList OBJECT;
  
  public static final StdTypeList OBJECTARR_INT;
  
  public static final StdTypeList OBJECT_OBJECT;
  
  public static final StdTypeList OBJECT_OBJECTARR_INT;
  
  public static final StdTypeList RETURN_ADDRESS;
  
  public static final StdTypeList SHORTARR_INT;
  
  public static final StdTypeList THROWABLE;
  
  static {
    FLOAT = make(Type.FLOAT);
    DOUBLE = make(Type.DOUBLE);
    OBJECT = make(Type.OBJECT);
    RETURN_ADDRESS = make(Type.RETURN_ADDRESS);
    THROWABLE = make(Type.THROWABLE);
    INT_INT = make(Type.INT, Type.INT);
    LONG_LONG = make(Type.LONG, Type.LONG);
    FLOAT_FLOAT = make(Type.FLOAT, Type.FLOAT);
    DOUBLE_DOUBLE = make(Type.DOUBLE, Type.DOUBLE);
    OBJECT_OBJECT = make(Type.OBJECT, Type.OBJECT);
    INT_OBJECT = make(Type.INT, Type.OBJECT);
    LONG_OBJECT = make(Type.LONG, Type.OBJECT);
    FLOAT_OBJECT = make(Type.FLOAT, Type.OBJECT);
    DOUBLE_OBJECT = make(Type.DOUBLE, Type.OBJECT);
    LONG_INT = make(Type.LONG, Type.INT);
    INTARR_INT = make(Type.INT_ARRAY, Type.INT);
    LONGARR_INT = make(Type.LONG_ARRAY, Type.INT);
    FLOATARR_INT = make(Type.FLOAT_ARRAY, Type.INT);
    DOUBLEARR_INT = make(Type.DOUBLE_ARRAY, Type.INT);
    OBJECTARR_INT = make(Type.OBJECT_ARRAY, Type.INT);
    BOOLEANARR_INT = make(Type.BOOLEAN_ARRAY, Type.INT);
    BYTEARR_INT = make(Type.BYTE_ARRAY, Type.INT);
    CHARARR_INT = make(Type.CHAR_ARRAY, Type.INT);
    SHORTARR_INT = make(Type.SHORT_ARRAY, Type.INT);
    INT_INTARR_INT = make(Type.INT, Type.INT_ARRAY, Type.INT);
    LONG_LONGARR_INT = make(Type.LONG, Type.LONG_ARRAY, Type.INT);
    FLOAT_FLOATARR_INT = make(Type.FLOAT, Type.FLOAT_ARRAY, Type.INT);
    DOUBLE_DOUBLEARR_INT = make(Type.DOUBLE, Type.DOUBLE_ARRAY, Type.INT);
    OBJECT_OBJECTARR_INT = make(Type.OBJECT, Type.OBJECT_ARRAY, Type.INT);
    INT_BOOLEANARR_INT = make(Type.INT, Type.BOOLEAN_ARRAY, Type.INT);
    INT_BYTEARR_INT = make(Type.INT, Type.BYTE_ARRAY, Type.INT);
    INT_CHARARR_INT = make(Type.INT, Type.CHAR_ARRAY, Type.INT);
    INT_SHORTARR_INT = make(Type.INT, Type.SHORT_ARRAY, Type.INT);
  }
  
  public StdTypeList(int paramInt) {
    super(paramInt);
  }
  
  public static int compareContents(TypeList paramTypeList1, TypeList paramTypeList2) {
    int i = paramTypeList1.size();
    int j = paramTypeList2.size();
    int k = Math.min(i, j);
    for (byte b = 0; b < k; b++) {
      int m = paramTypeList1.getType(b).compareTo(paramTypeList2.getType(b));
      if (m != 0)
        return m; 
    } 
    return (i == j) ? 0 : ((i < j) ? -1 : 1);
  }
  
  public static boolean equalContents(TypeList paramTypeList1, TypeList paramTypeList2) {
    int i = paramTypeList1.size();
    if (paramTypeList2.size() != i)
      return false; 
    for (byte b = 0; b < i; b++) {
      if (!paramTypeList1.getType(b).equals(paramTypeList2.getType(b)))
        return false; 
    } 
    return true;
  }
  
  public static int hashContents(TypeList paramTypeList) {
    int i = paramTypeList.size();
    byte b = 0;
    int j = 0;
    while (b < i) {
      j = j * 31 + paramTypeList.getType(b).hashCode();
      b++;
    } 
    return j;
  }
  
  public static StdTypeList make(Type paramType) {
    StdTypeList stdTypeList = new StdTypeList(1);
    stdTypeList.set(0, paramType);
    return stdTypeList;
  }
  
  public static StdTypeList make(Type paramType1, Type paramType2) {
    StdTypeList stdTypeList = new StdTypeList(2);
    stdTypeList.set(0, paramType1);
    stdTypeList.set(1, paramType2);
    return stdTypeList;
  }
  
  public static StdTypeList make(Type paramType1, Type paramType2, Type paramType3) {
    StdTypeList stdTypeList = new StdTypeList(3);
    stdTypeList.set(0, paramType1);
    stdTypeList.set(1, paramType2);
    stdTypeList.set(2, paramType3);
    return stdTypeList;
  }
  
  public static StdTypeList make(Type paramType1, Type paramType2, Type paramType3, Type paramType4) {
    StdTypeList stdTypeList = new StdTypeList(4);
    stdTypeList.set(0, paramType1);
    stdTypeList.set(1, paramType2);
    stdTypeList.set(2, paramType3);
    stdTypeList.set(3, paramType4);
    return stdTypeList;
  }
  
  public static String toHuman(TypeList paramTypeList) {
    int i = paramTypeList.size();
    if (i == 0)
      return "<empty>"; 
    StringBuilder stringBuilder = new StringBuilder(100);
    for (byte b = 0; b < i; b++) {
      if (b != 0)
        stringBuilder.append(", "); 
      stringBuilder.append(paramTypeList.getType(b).toHuman());
    } 
    return stringBuilder.toString();
  }
  
  public Type get(int paramInt) {
    return (Type)get0(paramInt);
  }
  
  public Type getType(int paramInt) {
    return get(paramInt);
  }
  
  public int getWordCount() {
    int i = size();
    byte b = 0;
    int j = 0;
    while (b < i) {
      j += get(b).getCategory();
      b++;
    } 
    return j;
  }
  
  public void set(int paramInt, Type paramType) {
    set0(paramInt, paramType);
  }
  
  public TypeList withAddedType(Type paramType) {
    int i = size();
    StdTypeList stdTypeList = new StdTypeList(i + 1);
    for (byte b = 0; b < i; b++)
      stdTypeList.set0(b, get0(b)); 
    stdTypeList.set(i, paramType);
    stdTypeList.setImmutable();
    return stdTypeList;
  }
  
  public StdTypeList withFirst(Type paramType) {
    int i = size();
    StdTypeList stdTypeList = new StdTypeList(i + 1);
    int j = 0;
    stdTypeList.set0(0, paramType);
    while (j < i) {
      int k = j + 1;
      stdTypeList.set0(k, getOrNull0(j));
      j = k;
    } 
    return stdTypeList;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\type\StdTypeList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */