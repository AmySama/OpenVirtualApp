package io.virtualapp.delegate;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import com.lody.virtual.client.core.AppCallback;
import java.util.HashSet;
import java.util.Set;

public class MyComponentDelegate implements AppCallback {
  private static final boolean sTrace = false;
  
  Set<Class<?>> hookedClasses = new HashSet<Class<?>>();
  
  public void afterActivityOnCreate(Activity paramActivity) {}
  
  public void afterActivityOnDestroy(Activity paramActivity) {}
  
  public void afterActivityOnResume(Activity paramActivity) {}
  
  public void afterActivityOnStart(Activity paramActivity) {}
  
  public void afterActivityOnStop(Activity paramActivity) {}
  
  public void afterApplicationCreate(String paramString1, String paramString2, Application paramApplication) {}
  
  public void beforeActivityOnCreate(Activity paramActivity) {}
  
  public void beforeActivityOnDestroy(Activity paramActivity) {}
  
  public void beforeActivityOnResume(Activity paramActivity) {}
  
  public void beforeActivityOnStart(Activity paramActivity) {}
  
  public void beforeActivityOnStop(Activity paramActivity) {}
  
  public void beforeApplicationCreate(String paramString1, String paramString2, Application paramApplication) {}
  
  public void beforeStartApplication(String paramString1, String paramString2, Context paramContext) {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\delegate\MyComponentDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */