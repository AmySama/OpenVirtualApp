package com.tencent.lbssearch.a.a.b;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

public final class b {
  static final Type[] a = new Type[0];
  
  private static int a(Object[] paramArrayOfObject, Object paramObject) {
    for (byte b1 = 0; b1 < paramArrayOfObject.length; b1++) {
      if (paramObject.equals(paramArrayOfObject[b1]))
        return b1; 
    } 
    throw new NoSuchElementException();
  }
  
  private static Class<?> a(TypeVariable<?> paramTypeVariable) {
    paramTypeVariable = (TypeVariable<?>)paramTypeVariable.getGenericDeclaration();
    if (paramTypeVariable instanceof Class) {
      Class clazz = (Class)paramTypeVariable;
    } else {
      paramTypeVariable = null;
    } 
    return (Class<?>)paramTypeVariable;
  }
  
  public static GenericArrayType a(Type paramType) {
    return new a(paramType);
  }
  
  public static ParameterizedType a(Type paramType1, Type paramType2, Type... paramVarArgs) {
    return new b(paramType1, paramType2, paramVarArgs);
  }
  
  public static Type a(Type paramType, Class<?> paramClass) {
    Type type = b(paramType, paramClass, Collection.class);
    paramType = type;
    if (type instanceof WildcardType)
      paramType = ((WildcardType)type).getUpperBounds()[0]; 
    return (paramType instanceof ParameterizedType) ? ((ParameterizedType)paramType).getActualTypeArguments()[0] : Object.class;
  }
  
  static Type a(Type<?> paramType, Class<?> paramClass1, Class<?> paramClass2) {
    if (paramClass2 == paramClass1)
      return paramType; 
    if (paramClass2.isInterface()) {
      Class[] arrayOfClass = paramClass1.getInterfaces();
      byte b1 = 0;
      int i = arrayOfClass.length;
      while (b1 < i) {
        if (arrayOfClass[b1] == paramClass2)
          return paramClass1.getGenericInterfaces()[b1]; 
        if (paramClass2.isAssignableFrom(arrayOfClass[b1]))
          return a(paramClass1.getGenericInterfaces()[b1], arrayOfClass[b1], paramClass2); 
        b1++;
      } 
    } 
    if (!paramClass1.isInterface())
      while (paramClass1 != Object.class) {
        paramType = paramClass1.getSuperclass();
        if (paramType == paramClass2)
          return paramClass1.getGenericSuperclass(); 
        if (paramClass2.isAssignableFrom((Class<?>)paramType))
          return a(paramClass1.getGenericSuperclass(), (Class<?>)paramType, paramClass2); 
        Type<?> type = paramType;
      }  
    return paramClass2;
  }
  
  public static Type a(Type<?> paramType1, Class<?> paramClass, Type<?> paramType2) {
    Type[] arrayOfType1;
    Type type;
    while (paramType2 instanceof TypeVariable) {
      type = paramType2;
      paramType2 = a(paramType1, paramClass, (TypeVariable<?>)type);
      if (paramType2 == type)
        return paramType2; 
    } 
    if (paramType2 instanceof Class) {
      type = paramType2;
      if (type.isArray()) {
        paramType2 = type.getComponentType();
        paramType1 = a(paramType1, paramClass, paramType2);
        if (paramType2 == paramType1) {
          paramType1 = type;
        } else {
          paramType1 = a(paramType1);
        } 
        return paramType1;
      } 
    } 
    if (paramType2 instanceof GenericArrayType) {
      paramType2 = paramType2;
      type = paramType2.getGenericComponentType();
      paramType1 = a(paramType1, paramClass, type);
      if (type == paramType1) {
        paramType1 = paramType2;
      } else {
        paramType1 = a(paramType1);
      } 
      return paramType1;
    } 
    boolean bool = paramType2 instanceof ParameterizedType;
    byte b1 = 0;
    if (bool) {
      boolean bool1;
      ParameterizedType parameterizedType = (ParameterizedType)paramType2;
      paramType2 = parameterizedType.getOwnerType();
      Type type1 = a(paramType1, paramClass, paramType2);
      if (type1 != paramType2) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      arrayOfType1 = parameterizedType.getActualTypeArguments();
      int i = arrayOfType1.length;
      while (b1 < i) {
        Type type2 = a(paramType1, paramClass, arrayOfType1[b1]);
        boolean bool2 = bool1;
        Type[] arrayOfType = arrayOfType1;
        if (type2 != arrayOfType1[b1]) {
          bool2 = bool1;
          arrayOfType = arrayOfType1;
          if (!bool1) {
            arrayOfType = (Type[])arrayOfType1.clone();
            bool2 = true;
          } 
          arrayOfType[b1] = type2;
        } 
        b1++;
        bool1 = bool2;
        arrayOfType1 = arrayOfType;
      } 
      paramType1 = parameterizedType;
      if (bool1)
        paramType1 = a(type1, parameterizedType.getRawType(), arrayOfType1); 
      return paramType1;
    } 
    Type[] arrayOfType2 = arrayOfType1;
    if (arrayOfType1 instanceof WildcardType) {
      WildcardType wildcardType = (WildcardType)arrayOfType1;
      Type[] arrayOfType4 = wildcardType.getLowerBounds();
      Type[] arrayOfType3 = wildcardType.getUpperBounds();
      if (arrayOfType4.length == 1) {
        paramType1 = a(paramType1, paramClass, arrayOfType4[0]);
        type = wildcardType;
        if (paramType1 != arrayOfType4[0])
          return c(paramType1); 
      } else {
        type = wildcardType;
        if (arrayOfType3.length == 1) {
          type = arrayOfType3[0];
          try {
            paramType1 = a(paramType1, paramClass, type);
            type = wildcardType;
            return (paramType1 != arrayOfType3[0]) ? b(paramType1) : type;
          } finally {}
        } 
      } 
    } 
    return type;
  }
  
  static Type a(Type paramType, Class<?> paramClass, TypeVariable<?> paramTypeVariable) {
    Class<?> clazz = a(paramTypeVariable);
    if (clazz == null)
      return paramTypeVariable; 
    paramType = a(paramType, paramClass, clazz);
    if (paramType instanceof ParameterizedType) {
      int i = a((Object[])clazz.getTypeParameters(), paramTypeVariable);
      return ((ParameterizedType)paramType).getActualTypeArguments()[i];
    } 
    return paramTypeVariable;
  }
  
  static boolean a(Object paramObject1, Object paramObject2) {
    return (paramObject1 == paramObject2 || (paramObject1 != null && paramObject1.equals(paramObject2)));
  }
  
  public static boolean a(Type paramType1, Type paramType2) {
    boolean bool1 = true;
    boolean bool2 = true;
    boolean bool3 = true;
    if (paramType1 == paramType2)
      return true; 
    if (paramType1 instanceof Class)
      return paramType1.equals(paramType2); 
    if (paramType1 instanceof ParameterizedType) {
      if (!(paramType2 instanceof ParameterizedType))
        return false; 
      paramType1 = paramType1;
      paramType2 = paramType2;
      if (!a(paramType1.getOwnerType(), paramType2.getOwnerType()) || !paramType1.getRawType().equals(paramType2.getRawType()) || !Arrays.equals((Object[])paramType1.getActualTypeArguments(), (Object[])paramType2.getActualTypeArguments()))
        bool3 = false; 
      return bool3;
    } 
    if (paramType1 instanceof GenericArrayType) {
      if (!(paramType2 instanceof GenericArrayType))
        return false; 
      paramType1 = paramType1;
      paramType2 = paramType2;
      return a(paramType1.getGenericComponentType(), paramType2.getGenericComponentType());
    } 
    if (paramType1 instanceof WildcardType) {
      if (!(paramType2 instanceof WildcardType))
        return false; 
      paramType1 = paramType1;
      paramType2 = paramType2;
      if (Arrays.equals((Object[])paramType1.getUpperBounds(), (Object[])paramType2.getUpperBounds()) && Arrays.equals((Object[])paramType1.getLowerBounds(), (Object[])paramType2.getLowerBounds())) {
        bool3 = bool1;
      } else {
        bool3 = false;
      } 
      return bool3;
    } 
    if (paramType1 instanceof TypeVariable) {
      if (!(paramType2 instanceof TypeVariable))
        return false; 
      paramType1 = paramType1;
      paramType2 = paramType2;
      if (paramType1.getGenericDeclaration() == paramType2.getGenericDeclaration() && paramType1.getName().equals(paramType2.getName())) {
        bool3 = bool2;
      } else {
        bool3 = false;
      } 
      return bool3;
    } 
    return false;
  }
  
  private static int b(Object paramObject) {
    boolean bool;
    if (paramObject != null) {
      bool = paramObject.hashCode();
    } else {
      bool = false;
    } 
    return bool;
  }
  
  static Type b(Type paramType, Class<?> paramClass1, Class<?> paramClass2) {
    a.a(paramClass2.isAssignableFrom(paramClass1));
    return a(paramType, paramClass1, a(paramType, paramClass1, paramClass2));
  }
  
  public static WildcardType b(Type paramType) {
    Type[] arrayOfType = a;
    return new c(new Type[] { paramType }, arrayOfType);
  }
  
  public static Type[] b(Type paramType, Class<?> paramClass) {
    if (paramType == Properties.class)
      return new Type[] { String.class, String.class }; 
    paramType = b(paramType, paramClass, Map.class);
    return (paramType instanceof ParameterizedType) ? ((ParameterizedType)paramType).getActualTypeArguments() : new Type[] { Object.class, Object.class };
  }
  
  public static WildcardType c(Type paramType) {
    return new c(new Type[] { Object.class }, new Type[] { paramType });
  }
  
  public static Type d(Type paramType) {
    if (paramType instanceof Class) {
      Class clazz = (Class)paramType;
      paramType = clazz;
      if (clazz.isArray())
        paramType = new a(d(clazz.getComponentType())); 
      return paramType;
    } 
    if (paramType instanceof ParameterizedType) {
      paramType = paramType;
      return new b(paramType.getOwnerType(), paramType.getRawType(), paramType.getActualTypeArguments());
    } 
    if (paramType instanceof GenericArrayType)
      return new a(((GenericArrayType)paramType).getGenericComponentType()); 
    if (paramType instanceof WildcardType) {
      paramType = paramType;
      return new c(paramType.getUpperBounds(), paramType.getLowerBounds());
    } 
    return paramType;
  }
  
  public static Class<?> e(Type paramType) {
    String str;
    if (paramType instanceof Class)
      return (Class)paramType; 
    if (paramType instanceof ParameterizedType) {
      paramType = ((ParameterizedType)paramType).getRawType();
      a.a(paramType instanceof Class);
      return (Class)paramType;
    } 
    if (paramType instanceof GenericArrayType)
      return Array.newInstance(e(((GenericArrayType)paramType).getGenericComponentType()), 0).getClass(); 
    if (paramType instanceof TypeVariable)
      return Object.class; 
    if (paramType instanceof WildcardType)
      return e(((WildcardType)paramType).getUpperBounds()[0]); 
    if (paramType == null) {
      str = "null";
    } else {
      str = paramType.getClass().getName();
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Expected a Class, ParameterizedType, or GenericArrayType, but <");
    stringBuilder.append(paramType);
    stringBuilder.append("> is of type ");
    stringBuilder.append(str);
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public static String f(Type paramType) {
    String str;
    if (paramType instanceof Class) {
      str = ((Class)paramType).getName();
    } else {
      str = str.toString();
    } 
    return str;
  }
  
  public static Type g(Type<?> paramType) {
    if (paramType instanceof GenericArrayType) {
      paramType = ((GenericArrayType)paramType).getGenericComponentType();
    } else {
      paramType = ((Class)paramType).getComponentType();
    } 
    return paramType;
  }
  
  private static void i(Type paramType) {
    boolean bool;
    if (!(paramType instanceof Class) || !((Class)paramType).isPrimitive()) {
      bool = true;
    } else {
      bool = false;
    } 
    a.a(bool);
  }
  
  private static final class a implements Serializable, GenericArrayType {
    private final Type a;
    
    public a(Type param1Type) {
      this.a = b.d(param1Type);
    }
    
    public boolean equals(Object param1Object) {
      boolean bool;
      if (param1Object instanceof GenericArrayType && b.a(this, (GenericArrayType)param1Object)) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public Type getGenericComponentType() {
      return this.a;
    }
    
    public int hashCode() {
      return this.a.hashCode();
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(b.f(this.a));
      stringBuilder.append("[]");
      return stringBuilder.toString();
    }
  }
  
  private static final class b implements Serializable, ParameterizedType {
    private final Type a;
    
    private final Type b;
    
    private final Type[] c;
    
    public b(Type param1Type1, Type param1Type2, Type... param1VarArgs) {
      boolean bool = param1Type2 instanceof Class;
      byte b1 = 0;
      if (bool) {
        Class clazz = (Class)param1Type2;
        boolean bool1 = true;
        if (param1Type1 != null || clazz.getEnclosingClass() == null) {
          bool = true;
        } else {
          bool = false;
        } 
        a.a(bool);
        bool = bool1;
        if (param1Type1 != null)
          if (clazz.getEnclosingClass() != null) {
            bool = bool1;
          } else {
            bool = false;
          }  
        a.a(bool);
      } 
      if (param1Type1 == null) {
        param1Type1 = null;
      } else {
        param1Type1 = b.d(param1Type1);
      } 
      this.a = param1Type1;
      this.b = b.d(param1Type2);
      this.c = (Type[])param1VarArgs.clone();
      while (true) {
        Type[] arrayOfType = this.c;
        if (b1 < arrayOfType.length) {
          a.a(arrayOfType[b1]);
          b.h(this.c[b1]);
          arrayOfType = this.c;
          arrayOfType[b1] = b.d(arrayOfType[b1]);
          b1++;
          continue;
        } 
        break;
      } 
    }
    
    public boolean equals(Object param1Object) {
      boolean bool;
      if (param1Object instanceof ParameterizedType && b.a(this, (ParameterizedType)param1Object)) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public Type[] getActualTypeArguments() {
      return (Type[])this.c.clone();
    }
    
    public Type getOwnerType() {
      return this.a;
    }
    
    public Type getRawType() {
      return this.b;
    }
    
    public int hashCode() {
      return Arrays.hashCode((Object[])this.c) ^ this.b.hashCode() ^ b.a(this.a);
    }
    
    public String toString() {
      int i = this.c.length;
      byte b1 = 1;
      StringBuilder stringBuilder = new StringBuilder((i + 1) * 30);
      stringBuilder.append(b.f(this.b));
      if (this.c.length == 0)
        return stringBuilder.toString(); 
      stringBuilder.append("<");
      stringBuilder.append(b.f(this.c[0]));
      while (b1 < this.c.length) {
        stringBuilder.append(", ");
        stringBuilder.append(b.f(this.c[b1]));
        b1++;
      } 
      stringBuilder.append(">");
      return stringBuilder.toString();
    }
  }
  
  private static final class c implements Serializable, WildcardType {
    private final Type a;
    
    private final Type b;
    
    public c(Type[] param1ArrayOfType1, Type[] param1ArrayOfType2) {
      boolean bool2;
      int i = param1ArrayOfType2.length;
      boolean bool1 = true;
      if (i <= 1) {
        bool2 = true;
      } else {
        bool2 = false;
      } 
      a.a(bool2);
      if (param1ArrayOfType1.length == 1) {
        bool2 = true;
      } else {
        bool2 = false;
      } 
      a.a(bool2);
      if (param1ArrayOfType2.length == 1) {
        a.a(param1ArrayOfType2[0]);
        b.h(param1ArrayOfType2[0]);
        if (param1ArrayOfType1[0] == Object.class) {
          bool2 = bool1;
        } else {
          bool2 = false;
        } 
        a.a(bool2);
        this.b = b.d(param1ArrayOfType2[0]);
        this.a = Object.class;
      } else {
        a.a(param1ArrayOfType1[0]);
        b.h(param1ArrayOfType1[0]);
        this.b = null;
        this.a = b.d(param1ArrayOfType1[0]);
      } 
    }
    
    public boolean equals(Object param1Object) {
      boolean bool;
      if (param1Object instanceof WildcardType && b.a(this, (WildcardType)param1Object)) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public Type[] getLowerBounds() {
      Type[] arrayOfType;
      Type type = this.b;
      if (type != null) {
        arrayOfType = new Type[1];
        arrayOfType[0] = type;
      } else {
        arrayOfType = b.a;
      } 
      return arrayOfType;
    }
    
    public Type[] getUpperBounds() {
      return new Type[] { this.a };
    }
    
    public int hashCode() {
      boolean bool;
      Type type = this.b;
      if (type != null) {
        bool = type.hashCode() + 31;
      } else {
        bool = true;
      } 
      return bool ^ this.a.hashCode() + 31;
    }
    
    public String toString() {
      if (this.b != null) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("? super ");
        stringBuilder1.append(b.f(this.b));
        return stringBuilder1.toString();
      } 
      if (this.a == Object.class)
        return "?"; 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("? extends ");
      stringBuilder.append(b.f(this.a));
      return stringBuilder.toString();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\b\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */