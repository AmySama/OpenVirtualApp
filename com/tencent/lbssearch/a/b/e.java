package com.tencent.lbssearch.a.b;

import com.tencent.lbssearch.a.a.i;
import com.tencent.lbssearch.a.a.j;
import com.tencent.lbssearch.a.a.k;
import com.tencent.lbssearch.a.a.l;
import com.tencent.lbssearch.a.a.o;
import com.tencent.lbssearch.a.a.p;
import com.tencent.lbssearch.object.result.RoutePlanningObject;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class e implements k<RoutePlanningObject.Step> {
  public RoutePlanningObject.Step a(l paraml, Type paramType, j paramj) throws p {
    RoutePlanningObject.Step step = new RoutePlanningObject.Step();
    o o = paraml.m();
    l l2 = o.a("accessorial_desc");
    if (l2 != null)
      step.accessorial_desc = l2.c(); 
    l2 = o.a("act_desc");
    if (l2 != null)
      step.act_desc = l2.c(); 
    l2 = o.a("dir_desc");
    if (l2 != null)
      step.dir_desc = l2.c(); 
    l2 = o.a("distance");
    if (l2 != null)
      step.distance = l2.e(); 
    l2 = o.a("duration");
    if (l2 != null)
      step.duration = l2.e(); 
    l2 = o.a("road_name");
    if (l2 != null)
      step.road_name = l2.c(); 
    l2 = o.a("instruction");
    if (l2 != null)
      step.instruction = l2.c(); 
    l l1 = o.a("polyline_idx");
    if (l1 != null) {
      i i = l1.n();
      if (i.a() == 2) {
        step.polyline_idx = new ArrayList();
        step.polyline_idx.add(Integer.valueOf(i.a(0).g() / 2));
        step.polyline_idx.add(Integer.valueOf(i.a(1).g() / 2));
      } 
    } 
    return step;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\b\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */