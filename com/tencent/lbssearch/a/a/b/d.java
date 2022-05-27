package com.tencent.lbssearch.a.a.b;

import com.tencent.lbssearch.a.a.a.a;
import com.tencent.lbssearch.a.a.a.c;
import com.tencent.lbssearch.a.a.b;
import com.tencent.lbssearch.a.a.c;
import com.tencent.lbssearch.a.a.c.a;
import com.tencent.lbssearch.a.a.d.a;
import com.tencent.lbssearch.a.a.d.c;
import com.tencent.lbssearch.a.a.f;
import com.tencent.lbssearch.a.a.w;
import com.tencent.lbssearch.a.a.x;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class d implements x, Cloneable {
  public static final d a = new d();
  
  private double b = -1.0D;
  
  private int c = 136;
  
  private boolean d = true;
  
  private boolean e;
  
  private List<b> f = Collections.emptyList();
  
  private List<b> g = Collections.emptyList();
  
  private boolean a(c paramc) {
    return !(paramc != null && paramc.a() > this.b);
  }
  
  private boolean a(c paramc, com.tencent.lbssearch.a.a.a.d paramd) {
    boolean bool;
    if (a(paramc) && a(paramd)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private boolean a(com.tencent.lbssearch.a.a.a.d paramd) {
    return !(paramd != null && paramd.a() <= this.b);
  }
  
  private boolean a(Class<?> paramClass) {
    boolean bool;
    if (!Enum.class.isAssignableFrom(paramClass) && (paramClass.isAnonymousClass() || paramClass.isLocalClass())) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private boolean b(Class<?> paramClass) {
    boolean bool;
    if (paramClass.isMemberClass() && !c(paramClass)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private boolean c(Class<?> paramClass) {
    boolean bool;
    if ((paramClass.getModifiers() & 0x8) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  protected d a() {
    try {
      return (d)super.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      throw new AssertionError();
    } 
  }
  
  public <T> w<T> a(f paramf, a<T> parama) {
    Class<?> clazz = parama.a();
    boolean bool1 = a(clazz, true);
    boolean bool2 = a(clazz, false);
    return (!bool1 && !bool2) ? null : new w<T>(this, bool2, bool1, paramf, parama) {
        private w<T> f;
        
        private w<T> a() {
          w<T> w1 = this.f;
          if (w1 == null) {
            w1 = this.c.a(this.e, this.d);
            this.f = w1;
          } 
          return w1;
        }
        
        public void a(c param1c, T param1T) throws IOException {
          if (this.b) {
            param1c.f();
            return;
          } 
          a().a(param1c, param1T);
        }
        
        public T b(a param1a) throws IOException {
          if (this.a) {
            param1a.n();
            return null;
          } 
          return (T)a().b(param1a);
        }
      };
  }
  
  public boolean a(Class<?> paramClass, boolean paramBoolean) {
    List<b> list;
    if (this.b != -1.0D && !a(paramClass.<c>getAnnotation(c.class), paramClass.<com.tencent.lbssearch.a.a.a.d>getAnnotation(com.tencent.lbssearch.a.a.a.d.class)))
      return true; 
    if (!this.d && b(paramClass))
      return true; 
    if (a(paramClass))
      return true; 
    if (paramBoolean) {
      list = this.f;
    } else {
      list = this.g;
    } 
    Iterator<b> iterator = list.iterator();
    while (iterator.hasNext()) {
      if (((b)iterator.next()).a(paramClass))
        return true; 
    } 
    return false;
  }
  
  public boolean a(Field paramField, boolean paramBoolean) {
    List<b> list;
    if ((this.c & paramField.getModifiers()) != 0)
      return true; 
    if (this.b != -1.0D && !a(paramField.<c>getAnnotation(c.class), paramField.<com.tencent.lbssearch.a.a.a.d>getAnnotation(com.tencent.lbssearch.a.a.a.d.class)))
      return true; 
    if (paramField.isSynthetic())
      return true; 
    if (this.e) {
      a a = paramField.<a>getAnnotation(a.class);
      if (a == null || (paramBoolean ? !a.a() : !a.b()))
        return true; 
    } 
    if (!this.d && b(paramField.getType()))
      return true; 
    if (a(paramField.getType()))
      return true; 
    if (paramBoolean) {
      list = this.f;
    } else {
      list = this.g;
    } 
    if (!list.isEmpty()) {
      c c = new c(paramField);
      Iterator<b> iterator = list.iterator();
      while (iterator.hasNext()) {
        if (((b)iterator.next()).a(c))
          return true; 
      } 
    } 
    return false;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\b\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */