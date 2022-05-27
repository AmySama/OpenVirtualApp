package com.tencent.lbssearch.a.d;

import android.net.TrafficStats;
import android.os.Build;
import android.os.Process;
import android.os.SystemClock;
import java.util.concurrent.BlockingQueue;

public class f extends Thread {
  private final BlockingQueue<j<?>> a;
  
  private final e b;
  
  private final m c;
  
  private volatile boolean d = false;
  
  public f(BlockingQueue<j<?>> paramBlockingQueue, e parame, m paramm) {
    this.a = paramBlockingQueue;
    this.b = parame;
    this.c = paramm;
  }
  
  private void a(j<?> paramj) {
    if (Build.VERSION.SDK_INT >= 14)
      TrafficStats.setThreadStatsTag(paramj.b()); 
  }
  
  private void a(j<?> paramj, q paramq) {
    paramq = paramj.a(paramq);
    this.c.a(paramj, paramq);
  }
  
  public void a() {
    this.d = true;
    interrupt();
  }
  
  public void run() {
    Process.setThreadPriority(10);
    while (true) {
      long l = SystemClock.elapsedRealtime();
      try {
        j<?> j = this.a.take();
        try {
          j.a("network-queue-take");
          if (j.d()) {
            j.b("network-discard-cancelled");
            continue;
          } 
          a(j);
          h h = this.b.a(j);
          j.a("network-http-complete");
          if (h.d && j.s()) {
            j.b("not-modified");
            continue;
          } 
          l<?> l1 = j.a(h);
          j.a("network-parse-complete");
          j.r();
          this.c.a(j, l1);
        } catch (q q) {
          q.a(SystemClock.elapsedRealtime() - l);
          a(j, q);
        } catch (Exception exception) {
          r.a(exception, "Unhandled exception %s", new Object[] { exception.toString() });
          exception = new q(exception);
          exception.a(SystemClock.elapsedRealtime() - l);
          this.c.a(j, (q)exception);
        } 
      } catch (InterruptedException interruptedException) {
        if (this.d)
          break; 
      } 
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\d\f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */