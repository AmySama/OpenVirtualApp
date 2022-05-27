package com.tencent.lbssearch.a.b;

import com.tencent.lbssearch.a.a.i;
import com.tencent.lbssearch.a.a.j;
import com.tencent.lbssearch.a.a.k;
import com.tencent.lbssearch.a.a.l;
import com.tencent.lbssearch.a.a.o;
import com.tencent.lbssearch.a.a.p;
import com.tencent.lbssearch.object.Location;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class c implements k<List<Location>> {
  protected static List<Location> a(List<Float> paramList) {
    if (paramList != null) {
      int i = paramList.size();
      byte b = 2;
      if (i >= 2) {
        ArrayList<Location> arrayList = new ArrayList();
        arrayList.add(new Location(((Float)paramList.get(0)).floatValue(), ((Float)paramList.get(1)).floatValue()));
        while (b < paramList.size()) {
          i = b / 2 - 1;
          float f1 = ((Location)arrayList.get(i)).lng;
          float f2 = Float.valueOf(((Float)paramList.get(b + 1)).floatValue()).floatValue() / 1000000.0F;
          arrayList.add(new Location((float)(Math.round((((Location)arrayList.get(i)).lat + Float.valueOf(((Float)paramList.get(b)).floatValue()).floatValue() / 1000000.0F) * 1000000.0D) / 1000000.0D), (float)(Math.round((f1 + f2) * 1000000.0D) / 1000000.0D)));
          b += 2;
        } 
        return arrayList;
      } 
    } 
    return null;
  }
  
  public List<Location> a(l paraml, Type paramType, j paramj) throws p {
    i i = paraml.n();
    if (i.a() > 0) {
      boolean bool = false;
      byte b = 0;
      if (i.a(0).j()) {
        ArrayList<Location> arrayList1 = new ArrayList();
        while (b < i.a()) {
          o o = i.a(b).m();
          arrayList1.add(new Location(o.a("lat").e(), o.a("lng").e()));
          b++;
        } 
        return arrayList1;
      } 
      ArrayList<Float> arrayList = new ArrayList();
      for (b = bool; b < i.a(); b++)
        arrayList.add(Float.valueOf(i.a(b).e())); 
      return a(arrayList);
    } 
    return null;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\b\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */