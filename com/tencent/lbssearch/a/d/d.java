package com.tencent.lbssearch.a.d;

import android.os.Handler;
import java.util.concurrent.Executor;

public class d implements m {
  private final Executor a;
  
  public d(Handler paramHandler) {
    this.a = new Executor(this, paramHandler) {
        public void execute(Runnable param1Runnable) {
          this.a.post(param1Runnable);
        }
      };
  }
  
  public void a(j<?> paramj, l<?> paraml) {
    a(paramj, paraml, null);
  }
  
  public void a(j<?> paramj, l<?> paraml, Runnable paramRunnable) {
    paramj.r();
    paramj.a("post-response");
    this.a.execute(new a(this, paramj, paraml, paramRunnable));
  }
  
  public void a(j<?> paramj, q paramq) {
    paramj.a("post-error");
    l<?> l = l.a(paramq);
    this.a.execute(new a(this, paramj, l, null));
  }
  
  private class a implements Runnable {
    private final j b;
    
    private final l c;
    
    private final Runnable d;
    
    public a(d this$0, j param1j, l param1l, Runnable param1Runnable) {
      this.b = param1j;
      this.c = param1l;
      this.d = param1Runnable;
    }
    
    public void run() {
      if (this.b.d()) {
        this.b.b("canceled-at-delivery");
        return;
      } 
      if (this.c.a()) {
        this.b.a(this.c.a);
      } else {
        this.b.b(this.c.b);
      } 
      if (this.c.c) {
        this.b.a("intermediate-response");
      } else {
        this.b.b("done");
      } 
      Runnable runnable = this.d;
      if (runnable != null)
        runnable.run(); 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\d\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */