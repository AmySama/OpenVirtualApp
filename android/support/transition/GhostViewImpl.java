package android.support.transition;

import android.graphics.Matrix;
import android.view.View;
import android.view.ViewGroup;

interface GhostViewImpl {
  void reserveEndViewTransition(ViewGroup paramViewGroup, View paramView);
  
  void setVisibility(int paramInt);
  
  public static interface Creator {
    GhostViewImpl addGhost(View param1View, ViewGroup param1ViewGroup, Matrix param1Matrix);
    
    void removeGhost(View param1View);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\GhostViewImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */