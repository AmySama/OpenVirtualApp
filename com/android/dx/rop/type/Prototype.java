package com.android.dx.rop.type;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class Prototype implements Comparable<Prototype> {
  private static final ConcurrentMap<String, Prototype> internTable = new ConcurrentHashMap<String, Prototype>(10000, 0.75F);
  
  private final String descriptor;
  
  private StdTypeList parameterFrameTypes;
  
  private final StdTypeList parameterTypes;
  
  private final Type returnType;
  
  private Prototype(String paramString, Type paramType, StdTypeList paramStdTypeList) {
    if (paramString != null) {
      if (paramType != null) {
        if (paramStdTypeList != null) {
          this.descriptor = paramString;
          this.returnType = paramType;
          this.parameterTypes = paramStdTypeList;
          this.parameterFrameTypes = null;
          return;
        } 
        throw new NullPointerException("parameterTypes == null");
      } 
      throw new NullPointerException("returnType == null");
    } 
    throw new NullPointerException("descriptor == null");
  }
  
  public static void clearInternTable() {
    internTable.clear();
  }
  
  public static Prototype fromDescriptor(String paramString) {
    Prototype prototype = internTable.get(paramString);
    if (prototype != null)
      return prototype; 
    Type[] arrayOfType = makeParameterArray(paramString);
    byte b1 = 0;
    int i = 1;
    byte b2 = 0;
    while (true) {
      int j = paramString.charAt(i);
      if (j == 41) {
        Type type = Type.internReturnType(paramString.substring(i + 1));
        StdTypeList stdTypeList = new StdTypeList(b2);
        for (i = b1; i < b2; i++)
          stdTypeList.set(i, arrayOfType[i]); 
        return new Prototype(paramString, type, stdTypeList);
      } 
      int k = i;
      while (j == 91)
        j = paramString.charAt(++k); 
      if (j == 76) {
        int m = paramString.indexOf(';', k);
        if (m != -1) {
          m++;
        } else {
          throw new IllegalArgumentException("bad descriptor");
        } 
      } else {
        j = k + 1;
      } 
      arrayOfType[b2] = Type.intern(paramString.substring(i, j));
      b2++;
      i = j;
    } 
  }
  
  public static Prototype intern(String paramString) {
    if (paramString != null) {
      Prototype prototype = internTable.get(paramString);
      return (prototype != null) ? prototype : putIntern(fromDescriptor(paramString));
    } 
    throw new NullPointerException("descriptor == null");
  }
  
  public static Prototype intern(String paramString, Type paramType, boolean paramBoolean1, boolean paramBoolean2) {
    Prototype prototype = intern(paramString);
    if (paramBoolean1)
      return prototype; 
    Type type = paramType;
    if (paramBoolean2)
      type = paramType.asUninitialized(2147483647); 
    return prototype.withFirstParameter(type);
  }
  
  public static Prototype internInts(Type paramType, int paramInt) {
    StringBuilder stringBuilder = new StringBuilder(100);
    stringBuilder.append('(');
    for (byte b = 0; b < paramInt; b++)
      stringBuilder.append('I'); 
    stringBuilder.append(')');
    stringBuilder.append(paramType.getDescriptor());
    return intern(stringBuilder.toString());
  }
  
  private static Type[] makeParameterArray(String paramString) {
    int i = paramString.length();
    byte b = 0;
    if (paramString.charAt(0) == '(') {
      int k;
      byte b1 = 1;
      int j = 0;
      while (true) {
        k = b;
        if (b1 < i) {
          char c = paramString.charAt(b1);
          if (c == ')') {
            k = b1;
            break;
          } 
          k = j;
          if (c >= 'A') {
            k = j;
            if (c <= 'Z')
              k = j + 1; 
          } 
          b1++;
          j = k;
          continue;
        } 
        break;
      } 
      if (k != 0 && k != i - 1) {
        if (paramString.indexOf(')', k + 1) == -1)
          return new Type[j]; 
        throw new IllegalArgumentException("bad descriptor");
      } 
      throw new IllegalArgumentException("bad descriptor");
    } 
    throw new IllegalArgumentException("bad descriptor");
  }
  
  private static Prototype putIntern(Prototype paramPrototype) {
    Prototype prototype = internTable.putIfAbsent(paramPrototype.getDescriptor(), paramPrototype);
    if (prototype != null)
      paramPrototype = prototype; 
    return paramPrototype;
  }
  
  public int compareTo(Prototype paramPrototype) {
    if (this == paramPrototype)
      return 0; 
    int i = this.returnType.compareTo(paramPrototype.returnType);
    if (i != 0)
      return i; 
    int j = this.parameterTypes.size();
    int k = paramPrototype.parameterTypes.size();
    int m = Math.min(j, k);
    for (i = 0; i < m; i++) {
      int n = this.parameterTypes.get(i).compareTo(paramPrototype.parameterTypes.get(i));
      if (n != 0)
        return n; 
    } 
    return (j < k) ? -1 : ((j > k) ? 1 : 0);
  }
  
  public boolean equals(Object paramObject) {
    return (this == paramObject) ? true : (!(paramObject instanceof Prototype) ? false : this.descriptor.equals(((Prototype)paramObject).descriptor));
  }
  
  public String getDescriptor() {
    return this.descriptor;
  }
  
  public StdTypeList getParameterFrameTypes() {
    if (this.parameterFrameTypes == null) {
      StdTypeList stdTypeList2;
      int i = this.parameterTypes.size();
      StdTypeList stdTypeList1 = new StdTypeList(i);
      byte b = 0;
      boolean bool = false;
      while (b < i) {
        Type type1 = this.parameterTypes.get(b);
        Type type2 = type1;
        if (type1.isIntlike()) {
          type2 = Type.INT;
          bool = true;
        } 
        stdTypeList1.set(b, type2);
        b++;
      } 
      if (bool) {
        stdTypeList2 = stdTypeList1;
      } else {
        stdTypeList2 = this.parameterTypes;
      } 
      this.parameterFrameTypes = stdTypeList2;
    } 
    return this.parameterFrameTypes;
  }
  
  public StdTypeList getParameterTypes() {
    return this.parameterTypes;
  }
  
  public Type getReturnType() {
    return this.returnType;
  }
  
  public int hashCode() {
    return this.descriptor.hashCode();
  }
  
  public String toString() {
    return this.descriptor;
  }
  
  public Prototype withFirstParameter(Type paramType) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("(");
    stringBuilder.append(paramType.getDescriptor());
    stringBuilder.append(this.descriptor.substring(1));
    String str = stringBuilder.toString();
    StdTypeList stdTypeList = this.parameterTypes.withFirst(paramType);
    stdTypeList.setImmutable();
    return putIntern(new Prototype(str, this.returnType, stdTypeList));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\android\dx\rop\type\Prototype.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */