package com.tencent.lbssearch.a.b;

import com.tencent.lbssearch.a.a.j;
import com.tencent.lbssearch.a.a.k;
import com.tencent.lbssearch.a.a.l;
import com.tencent.lbssearch.a.a.p;
import com.tencent.lbssearch.object.result.TransitResultObject;
import java.lang.reflect.Type;

public class g implements k<TransitResultObject.Segment> {
  public TransitResultObject.Segment a(l paraml, Type paramType, j paramj) throws p {
    l l1 = paraml.m().a("mode");
    return (l1 == null) ? null : ("WALKING".equalsIgnoreCase(l1.c()) ? (TransitResultObject.Segment)paramj.a(paraml, TransitResultObject.Walking.class) : ("TRANSIT".equalsIgnoreCase(l1.c()) ? (TransitResultObject.Segment)paramj.a(paraml, TransitResultObject.Transit.class) : null));
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\b\g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */