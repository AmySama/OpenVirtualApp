package com.tencent.mapsdk.rastercore.tile;

import android.graphics.Bitmap;
import com.tencent.mapsdk.rastercore.d.e;
import com.tencent.mapsdk.rastercore.f.a;
import com.tencent.mapsdk.rastercore.tile.a.a;
import com.tencent.mapsdk.rastercore.tile.a.c;
import com.tencent.tencentmap.mapsdk.map.TencentMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class b implements e.a {
  private static final TimeUnit a = TimeUnit.SECONDS;
  
  private static final int b = Runtime.getRuntime().availableProcessors();
  
  private e c;
  
  private Map<String, List<a>> d;
  
  private Map<String, List<a>> e;
  
  private final BlockingQueue<Runnable> f;
  
  private final BlockingQueue<Runnable> g;
  
  private ExecutorService h;
  
  private ExecutorService i;
  
  private ThreadFactory j;
  
  public b(e parame) {
    byte b1;
    byte b2;
    this.d = new HashMap<String, List<a>>();
    this.e = new HashMap<String, List<a>>();
    this.f = new LinkedBlockingQueue<Runnable>();
    this.g = new LinkedBlockingQueue<Runnable>();
    this.j = new ThreadFactory(this) {
        private int a = 0;
        
        public final Thread newThread(Runnable param1Runnable) {
          StringBuilder stringBuilder = new StringBuilder("TileFetchThread#");
          int i = this.a;
          this.a = i + 1;
          stringBuilder.append(i);
          param1Runnable = new Thread(param1Runnable, stringBuilder.toString());
          param1Runnable.setPriority(10);
          return (Thread)param1Runnable;
        }
      };
    this.c = parame;
    if (b < 4) {
      b1 = 3;
      b2 = 3;
    } else {
      b1 = 4;
      b2 = 4;
    } 
    this.i = new ThreadPoolExecutor(b1, b2, 30L, TimeUnit.SECONDS, this.g, this.j);
    this.h = new ThreadPoolExecutor(1, 1, 30L, a, this.f);
  }
  
  public final void a() {
    BlockingQueue<Runnable> blockingQueue = this.f;
    if (blockingQueue != null)
      blockingQueue.clear(); 
    blockingQueue = this.g;
    if (blockingQueue != null)
      blockingQueue.clear(); 
    ExecutorService executorService = this.h;
    if (executorService != null) {
      executorService.shutdownNow();
      this.h = null;
    } 
    executorService = this.i;
    if (executorService != null) {
      executorService.shutdown();
      this.i = null;
    } 
  }
  
  public final void a(e parame) {
    if (parame != null) {
      String str = parame.b();
      Bitmap bitmap = parame.a();
      synchronized (this.d) {
        List list = this.e.remove(str);
        this.d.remove(str);
        if (list != null && bitmap != null && !bitmap.isRecycled())
          for (a a1 : list) {
            if (!a1.i())
              a1.a(bitmap.copy(bitmap.getConfig(), false)); 
          }  
        parame.c();
      } 
    } 
    this.c.c().postInvalidate();
  }
  
  public final void a(ArrayList<MapTile> paramArrayList) {
    if (a.a(paramArrayList))
      return; 
    this.f.clear();
    Runnable runnable = new Runnable(this, paramArrayList) {
        public final void run() {
          b.a(this.b).clear();
          synchronized (b.b(this.b)) {
            b.b(this.b).clear();
            b.b(this.b).putAll(b.c(this.b));
            for (byte b1 = 0; b1 < this.a.size(); b1++) {
              for (a a : ((MapTile)this.a.get(b1)).b()) {
                Exception exception;
                try {
                  c c = a.a().a(a);
                } finally {
                  exception = null;
                  if (TencentMap.getErrorListener() != null) {
                    TencentMap.OnErrorListener onErrorListener = TencentMap.getErrorListener();
                    StringBuilder stringBuilder = new StringBuilder("TileEngineManager getTiles Runnable call CacheManager Get occured Exception,tileInfo:x=");
                    stringBuilder.append(a.b());
                    stringBuilder.append(",y=");
                    stringBuilder.append(a.c());
                    stringBuilder.append(",z=");
                    stringBuilder.append(a.d());
                    stringBuilder.append("Exception Info:");
                    stringBuilder.append(exception.toString());
                    onErrorListener.collectErrorInfo(stringBuilder.toString());
                  } 
                } 
                if (exception.b() == null) {
                  b.a(this.b, a, false, null);
                  if (a.m() == MapTile.MapSource.TENCENT) {
                    b.d(this.b);
                    e.c++;
                  } 
                  if (a.m() == MapTile.MapSource.BING) {
                    b.d(this.b);
                    e.d++;
                  } 
                } 
              } 
              b.d(this.b).c().postInvalidate();
            } 
            return;
          } 
        }
      };
    try {
      if (!this.h.isShutdown())
        this.h.execute(runnable); 
      return;
    } catch (Exception exception) {
      (new StringBuilder("getTiles get error:")).append(exception.getMessage());
      return;
    } 
  }
  
  public final void b(e parame) {
    if (parame != null) {
      null = parame.b();
      synchronized (this.d) {
        List<a> list = this.d.remove(null);
        this.e.put(null, list);
        return;
      } 
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\tile\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */