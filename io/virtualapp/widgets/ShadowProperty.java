package io.virtualapp.widgets;

public class ShadowProperty {
  public static final int ALL = 4369;
  
  public static final int BOTTOM = 4096;
  
  public static final int LEFT = 1;
  
  public static final int RIGHT = 256;
  
  public static final int TOP = 16;
  
  private int shadowColor;
  
  private int shadowDx;
  
  private int shadowDy;
  
  private int shadowRadius;
  
  private int shadowSide = 4369;
  
  public int getShadowColor() {
    return this.shadowColor;
  }
  
  public int getShadowDx() {
    return this.shadowDx;
  }
  
  public int getShadowDy() {
    return this.shadowDy;
  }
  
  public int getShadowOffset() {
    return getShadowOffsetHalf() * 2;
  }
  
  public int getShadowOffsetHalf() {
    int i;
    if (this.shadowRadius <= 0) {
      i = 0;
    } else {
      i = Math.max(this.shadowDx, this.shadowDy) + this.shadowRadius;
    } 
    return i;
  }
  
  public int getShadowRadius() {
    return this.shadowRadius;
  }
  
  public int getShadowSide() {
    return this.shadowSide;
  }
  
  public ShadowProperty setShadowColor(int paramInt) {
    this.shadowColor = paramInt;
    return this;
  }
  
  public ShadowProperty setShadowDx(int paramInt) {
    this.shadowDx = paramInt;
    return this;
  }
  
  public ShadowProperty setShadowDy(int paramInt) {
    this.shadowDy = paramInt;
    return this;
  }
  
  public ShadowProperty setShadowRadius(int paramInt) {
    this.shadowRadius = paramInt;
    return this;
  }
  
  public ShadowProperty setShadowSide(int paramInt) {
    this.shadowSide = paramInt;
    return this;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\ShadowProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */