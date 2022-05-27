package com.tencent.lbssearch.a.a.c;

import com.tencent.lbssearch.a.a.b.b;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class a<T> {
  final Class<? super T> a;
  
  final Type b;
  
  final int c;
  
  protected a() {
    Type type = a(getClass());
    this.b = type;
    this.a = b.e(type);
    this.c = this.b.hashCode();
  }
  
  a(Type paramType) {
    paramType = b.d((Type)com.tencent.lbssearch.a.a.b.a.a(paramType));
    this.b = paramType;
    this.a = b.e(paramType);
    this.c = this.b.hashCode();
  }
  
  public static a<?> a(Type paramType) {
    return new a(paramType);
  }
  
  static Type a(Class<?> paramClass) {
    Type type = paramClass.getGenericSuperclass();
    if (!(type instanceof Class))
      return b.d(((ParameterizedType)type).getActualTypeArguments()[0]); 
    throw new RuntimeException("Missing type parameter.");
  }
  
  public static <T> a<T> b(Class<T> paramClass) {
    return new a<T>(paramClass);
  }
  
  public final Class<? super T> a() {
    return this.a;
  }
  
  public final Type b() {
    return this.b;
  }
  
  public final boolean equals(Object paramObject) {
    boolean bool;
    if (paramObject instanceof a && b.a(this.b, ((a)paramObject).b)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public final int hashCode() {
    return this.c;
  }
  
  public final String toString() {
    return b.f(this.b);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\c\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */