package com.tencent.lbssearch.a.d;

import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class k {
  private AtomicInteger a = new AtomicInteger();
  
  private final Set<j<?>> b = new HashSet<j<?>>();
  
  private final PriorityBlockingQueue<j<?>> c = new PriorityBlockingQueue<j<?>>();
  
  private final e d;
  
  private final m e;
  
  private f[] f;
  
  private List<a> g = new ArrayList<a>();
  
  public k(e parame) {
    this(parame, 4);
  }
  
  public k(e parame, int paramInt) {
    this(parame, paramInt, new d(new Handler(Looper.getMainLooper())));
  }
  
  public k(e parame, int paramInt, m paramm) {
    this.d = parame;
    this.f = new f[paramInt];
    this.e = paramm;
  }
  
  public <T> j<T> a(j<T> paramj) {
    paramj.a(this);
    synchronized (this.b) {
      this.b.add(paramj);
      paramj.a(c());
      paramj.a("add-to-queue");
      this.c.add(paramj);
      return paramj;
    } 
  }
  
  public void a() {
    b();
    for (byte b = 0; b < this.f.length; b++) {
      f f1 = new f(this.c, this.d, this.e);
      this.f[b] = f1;
      f1.start();
    } 
  }
  
  public void b() {
    byte b = 0;
    while (true) {
      f[] arrayOfF = this.f;
      if (b < arrayOfF.length) {
        if (arrayOfF[b] != null)
          arrayOfF[b].a(); 
        b++;
        continue;
      } 
      break;
    } 
  }
  
  <T> void b(j<T> paramj) {
    synchronized (this.b) {
      this.b.remove(paramj);
      synchronized (this.g) {
        Iterator<a> iterator = this.g.iterator();
        while (iterator.hasNext())
          ((a<T>)iterator.next()).a(paramj); 
        return;
      } 
    } 
  }
  
  public int c() {
    return this.a.incrementAndGet();
  }
  
  public static interface a<T> {
    void a(j<T> param1j);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\d\k.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */