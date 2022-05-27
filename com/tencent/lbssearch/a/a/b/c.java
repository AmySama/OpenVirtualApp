package com.tencent.lbssearch.a.a.b;

import com.tencent.lbssearch.a.a.c.a;
import com.tencent.lbssearch.a.a.h;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public final class c {
  private final Map<Type, h<?>> a;
  
  public c(Map<Type, h<?>> paramMap) {
    this.a = paramMap;
  }
  
  private <T> h<T> a(Class<? super T> paramClass) {
    try {
      Constructor<? super T> constructor = paramClass.getDeclaredConstructor(new Class[0]);
      if (!constructor.isAccessible())
        constructor.setAccessible(true); 
      return new h<T>(this, constructor) {
          public T a() {
            try {
              return (T)this.a.newInstance((Object[])null);
            } catch (InstantiationException instantiationException) {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("Failed to invoke ");
              stringBuilder.append(this.a);
              stringBuilder.append(" with no args");
              throw new RuntimeException(stringBuilder.toString(), instantiationException);
            } catch (InvocationTargetException invocationTargetException) {
              StringBuilder stringBuilder = new StringBuilder();
              stringBuilder.append("Failed to invoke ");
              stringBuilder.append(this.a);
              stringBuilder.append(" with no args");
              throw new RuntimeException(stringBuilder.toString(), invocationTargetException.getTargetException());
            } catch (IllegalAccessException illegalAccessException) {
              throw new AssertionError(illegalAccessException);
            } 
          }
        };
    } catch (NoSuchMethodException noSuchMethodException) {
      return null;
    } 
  }
  
  private <T> h<T> a(Type paramType, Class<? super T> paramClass) {
    return Collection.class.isAssignableFrom(paramClass) ? (SortedSet.class.isAssignableFrom(paramClass) ? new h<T>(this) {
        public T a() {
          return (T)new TreeSet();
        }
      } : (Set.class.isAssignableFrom(paramClass) ? new h<T>(this) {
        public T a() {
          return (T)new LinkedHashSet();
        }
      } : (Queue.class.isAssignableFrom(paramClass) ? new h<T>(this) {
        public T a() {
          return (T)new LinkedList();
        }
      } : new h<T>(this) {
        public T a() {
          return (T)new ArrayList();
        }
      }))) : (Map.class.isAssignableFrom(paramClass) ? (SortedMap.class.isAssignableFrom(paramClass) ? new h<T>(this) {
        public T a() {
          return (T)new TreeMap<Object, Object>();
        }
      } : ((paramType instanceof ParameterizedType && !String.class.isAssignableFrom(a.a(((ParameterizedType)paramType).getActualTypeArguments()[0]).a())) ? new h<T>(this) {
        public T a() {
          return (T)new LinkedHashMap<Object, Object>();
        }
      } : new h<T>(this) {
        public T a() {
          return (T)new g<Comparable, Object>();
        }
      })) : null);
  }
  
  private <T> h<T> b(Type paramType, Class<? super T> paramClass) {
    return new h<T>(this, paramClass, paramType) {
        private final k d = k.a();
        
        public T a() {
          try {
            return (T)this.d.a(this.a);
          } catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to invoke no-args constructor for ");
            stringBuilder.append(this.b);
            stringBuilder.append(". ");
            stringBuilder.append("Register an InstanceCreator with Gson for this type may fix this problem.");
            throw new RuntimeException(stringBuilder.toString(), exception);
          } 
        }
      };
  }
  
  public <T> h<T> a(a<T> parama) {
    Type type = parama.b();
    Class<?> clazz = parama.a();
    h h1 = this.a.get(type);
    if (h1 != null)
      return new h<T>(this, h1, type) {
          public T a() {
            return (T)this.a.a(this.b);
          }
        }; 
    h1 = this.a.get(clazz);
    if (h1 != null)
      return new h<T>(this, h1, type) {
          public T a() {
            return (T)this.a.a(this.b);
          }
        }; 
    h<?> h = a(clazz);
    if (h != null)
      return (h)h; 
    h = a(type, clazz);
    return (h)((h != null) ? h : b(type, (Class)clazz));
  }
  
  public String toString() {
    return this.a.toString();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\b\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */