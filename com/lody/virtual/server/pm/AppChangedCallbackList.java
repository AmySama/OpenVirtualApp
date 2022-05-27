package com.lody.virtual.server.pm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AppChangedCallbackList {
  private static final AppChangedCallbackList sInstance = new AppChangedCallbackList();
  
  private List<IAppChangedCallback> mList = new ArrayList<IAppChangedCallback>(2);
  
  public static AppChangedCallbackList get() {
    return sInstance;
  }
  
  void notifyCallbacks(boolean paramBoolean) {
    Iterator<IAppChangedCallback> iterator = this.mList.iterator();
    while (iterator.hasNext())
      ((IAppChangedCallback)iterator.next()).onCallback(paramBoolean); 
  }
  
  public void register(IAppChangedCallback paramIAppChangedCallback) {
    this.mList.add(paramIAppChangedCallback);
  }
  
  public void unregister(IAppChangedCallback paramIAppChangedCallback) {
    this.mList.remove(paramIAppChangedCallback);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\pm\AppChangedCallbackList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */