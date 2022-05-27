package com.tencent.lbssearch.a.b;

import android.text.TextUtils;
import com.tencent.lbssearch.a.a.c.a;
import com.tencent.lbssearch.a.a.f;
import com.tencent.lbssearch.a.a.g;
import com.tencent.lbssearch.a.a.t;
import com.tencent.lbssearch.a.c.a;
import com.tencent.lbssearch.object.Location;
import com.tencent.lbssearch.object.result.RoutePlanningObject;
import com.tencent.lbssearch.object.result.TransitResultObject;
import java.util.List;

public class b {
  public static <T> T a(String paramString, Class<T> paramClass) {
    boolean bool = TextUtils.isEmpty(paramString);
    g g1 = null;
    g g2 = g1;
    if (!bool)
      if (paramClass == null) {
        g2 = g1;
      } else {
        g2 = new g();
        g2.a((new a<List<Location>>() {
            
            },  ).b(), new c());
        g2.a(RoutePlanningObject.Step.class, new e());
        g2.a(TransitResultObject.Segment.class, new g());
        g2.a(TransitResultObject.LatLngBounds.class, new f());
        f f = g2.a();
        try {
          Object object = f.a(paramString, paramClass);
        } catch (t t) {
          t.printStackTrace();
          a.a(t.toString());
          g2 = g1;
        } 
      }  
    return (T)g2;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\b\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */