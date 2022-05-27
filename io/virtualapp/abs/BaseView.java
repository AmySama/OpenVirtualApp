package io.virtualapp.abs;

import android.app.Activity;
import android.content.Context;

public interface BaseView<T> {
  Activity getActivity();
  
  Context getContext();
  
  void setPresenter(T paramT);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\abs\BaseView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */