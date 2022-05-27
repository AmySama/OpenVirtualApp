package com.tencent.mapsdk.rastercore.e;

import android.graphics.Canvas;

public interface b {
  boolean checkInBounds();
  
  void destroy();
  
  void draw(Canvas paramCanvas);
  
  boolean equalsRemote(b paramb);
  
  String getId();
  
  float getZIndex();
  
  int hashCodeRemote();
  
  boolean isVisible();
  
  void remove();
  
  void setVisible(boolean paramBoolean);
  
  void setZIndex(float paramFloat);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\tencent\mapsdk\rastercore\e\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */