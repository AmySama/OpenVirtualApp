package com.tencent.lbssearch.a.a;

import com.tencent.lbssearch.a.a.b.g;
import java.util.Map;
import java.util.Set;

public final class o extends l {
  private final g<String, l> a = new g();
  
  public l a(String paramString) {
    return (l)this.a.a(paramString);
  }
  
  public Set<Map.Entry<String, l>> a() {
    return this.a.entrySet();
  }
  
  public void a(String paramString, l paraml) {
    l l1 = paraml;
    if (paraml == null)
      l1 = n.a; 
    this.a.a(paramString, l1);
  }
  
  public boolean equals(Object paramObject) {
    return (paramObject == this || (paramObject instanceof o && ((o)paramObject).a.equals(this.a)));
  }
  
  public int hashCode() {
    return this.a.hashCode();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\a\o.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */