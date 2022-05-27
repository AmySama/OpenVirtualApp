package com.tencent.lbssearch.a.a;

import com.tencent.lbssearch.a.a.b.a;
import com.tencent.lbssearch.a.a.b.a.l;
import com.tencent.lbssearch.a.a.b.d;
import com.tencent.lbssearch.a.a.c.a;
import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class g {
  private d a = d.a;
  
  private u b = u.a;
  
  private e c = d.a;
  
  private final Map<Type, h<?>> d = new HashMap<Type, h<?>>();
  
  private final List<x> e = new ArrayList<x>();
  
  private final List<x> f = new ArrayList<x>();
  
  private boolean g;
  
  private String h;
  
  private int i = 2;
  
  private int j = 2;
  
  private boolean k;
  
  private boolean l;
  
  private boolean m = true;
  
  private boolean n;
  
  private boolean o;
  
  private void a(String paramString, int paramInt1, int paramInt2, List<x> paramList) {
    a a;
    if (paramString != null && !"".equals(paramString.trim())) {
      a = new a(paramString);
    } else if (paramInt1 != 2 && paramInt2 != 2) {
      a = new a(paramInt1, paramInt2);
    } else {
      return;
    } 
    paramList.add(v.a(a.b(Date.class), a));
    paramList.add(v.a(a.b(Timestamp.class), a));
    paramList.add(v.a(a.b(Date.class), a));
  }
  
  public f a() {
    ArrayList<x> arrayList = new ArrayList();
    arrayList.addAll(this.e);
    Collections.reverse(arrayList);
    arrayList.addAll(this.f);
    a(this.h, this.i, this.j, arrayList);
    return new f(this.a, this.c, this.d, this.g, this.k, this.o, this.m, this.n, this.l, this.b, arrayList);
  }
  
  public g a(Type paramType, Object paramObject) {
    boolean bool1;
    boolean bool = paramObject instanceof s;
    if (bool || paramObject instanceof k || paramObject instanceof h || paramObject instanceof w) {
      bool1 = true;
    } else {
      bool1 = false;
    } 
    a.a(bool1);
    if (paramObject instanceof h)
      this.d.put(paramType, (h)paramObject); 
    if (bool || paramObject instanceof k) {
      a<?> a = a.a(paramType);
      this.e.add(v.b(a, paramObject));
    } 
    if (paramObject instanceof w)
      this.e.add(l.a(a.a(paramType), (w)paramObject)); 
    return this;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */