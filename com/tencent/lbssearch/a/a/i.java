package com.tencent.lbssearch.a.a;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class i extends l implements Iterable<l> {
  private final List<l> a = new ArrayList<l>();
  
  public int a() {
    return this.a.size();
  }
  
  public l a(int paramInt) {
    return this.a.get(paramInt);
  }
  
  public void a(l paraml) {
    l l1 = paraml;
    if (paraml == null)
      l1 = n.a; 
    this.a.add(l1);
  }
  
  public Number b() {
    if (this.a.size() == 1)
      return ((l)this.a.get(0)).b(); 
    throw new IllegalStateException();
  }
  
  public String c() {
    if (this.a.size() == 1)
      return ((l)this.a.get(0)).c(); 
    throw new IllegalStateException();
  }
  
  public double d() {
    if (this.a.size() == 1)
      return ((l)this.a.get(0)).d(); 
    throw new IllegalStateException();
  }
  
  public float e() {
    if (this.a.size() == 1)
      return ((l)this.a.get(0)).e(); 
    throw new IllegalStateException();
  }
  
  public boolean equals(Object paramObject) {
    return (paramObject == this || (paramObject instanceof i && ((i)paramObject).a.equals(this.a)));
  }
  
  public long f() {
    if (this.a.size() == 1)
      return ((l)this.a.get(0)).f(); 
    throw new IllegalStateException();
  }
  
  public int g() {
    if (this.a.size() == 1)
      return ((l)this.a.get(0)).g(); 
    throw new IllegalStateException();
  }
  
  public boolean h() {
    if (this.a.size() == 1)
      return ((l)this.a.get(0)).h(); 
    throw new IllegalStateException();
  }
  
  public int hashCode() {
    return this.a.hashCode();
  }
  
  public Iterator<l> iterator() {
    return this.a.iterator();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */