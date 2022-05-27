package com.lody.virtual.client.core;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

public interface AppCallback {
  public static final AppCallback EMPTY = new AppCallback() {
      public void afterActivityOnCreate(Activity param1Activity) {}
      
      public void afterActivityOnDestroy(Activity param1Activity) {}
      
      public void afterActivityOnResume(Activity param1Activity) {}
      
      public void afterActivityOnStart(Activity param1Activity) {}
      
      public void afterActivityOnStop(Activity param1Activity) {}
      
      public void afterApplicationCreate(String param1String1, String param1String2, Application param1Application) {}
      
      public void beforeActivityOnCreate(Activity param1Activity) {}
      
      public void beforeActivityOnDestroy(Activity param1Activity) {}
      
      public void beforeActivityOnResume(Activity param1Activity) {}
      
      public void beforeActivityOnStart(Activity param1Activity) {}
      
      public void beforeActivityOnStop(Activity param1Activity) {}
      
      public void beforeApplicationCreate(String param1String1, String param1String2, Application param1Application) {}
      
      public void beforeStartApplication(String param1String1, String param1String2, Context param1Context) {}
    };
  
  void afterActivityOnCreate(Activity paramActivity);
  
  void afterActivityOnDestroy(Activity paramActivity);
  
  void afterActivityOnResume(Activity paramActivity);
  
  void afterActivityOnStart(Activity paramActivity);
  
  void afterActivityOnStop(Activity paramActivity);
  
  void afterApplicationCreate(String paramString1, String paramString2, Application paramApplication);
  
  void beforeActivityOnCreate(Activity paramActivity);
  
  void beforeActivityOnDestroy(Activity paramActivity);
  
  void beforeActivityOnResume(Activity paramActivity);
  
  void beforeActivityOnStart(Activity paramActivity);
  
  void beforeActivityOnStop(Activity paramActivity);
  
  void beforeApplicationCreate(String paramString1, String paramString2, Application paramApplication);
  
  void beforeStartApplication(String paramString1, String paramString2, Context paramContext);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\core\AppCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */