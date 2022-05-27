package com.tencent.lbssearch.a.d;

public class c implements n {
  private int a;
  
  private int b;
  
  private final int c;
  
  private final float d;
  
  public c() {
    this(2500, 1, 1.0F);
  }
  
  public c(int paramInt1, int paramInt2, float paramFloat) {
    this.a = paramInt1;
    this.c = paramInt2;
    this.d = paramFloat;
  }
  
  public int a() {
    return this.a;
  }
  
  public void a(q paramq) throws q {
    this.b++;
    int i = this.a;
    this.a = (int)(i + i * this.d);
    if (c())
      return; 
    throw paramq;
  }
  
  public int b() {
    return this.b;
  }
  
  protected boolean c() {
    boolean bool;
    if (this.b <= this.c) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\lbssearch\a\d\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */