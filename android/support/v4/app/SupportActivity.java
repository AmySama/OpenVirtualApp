package android.support.v4.app;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.ReportFragment;
import android.os.Bundle;
import android.support.v4.util.SimpleArrayMap;

public class SupportActivity extends Activity implements LifecycleOwner {
  private SimpleArrayMap<Class<? extends ExtraData>, ExtraData> mExtraDataMap = new SimpleArrayMap();
  
  private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
  
  public <T extends ExtraData> T getExtraData(Class<T> paramClass) {
    return (T)this.mExtraDataMap.get(paramClass);
  }
  
  public Lifecycle getLifecycle() {
    return (Lifecycle)this.mLifecycleRegistry;
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    ReportFragment.injectIfNeededIn(this);
  }
  
  protected void onSaveInstanceState(Bundle paramBundle) {
    this.mLifecycleRegistry.markState(Lifecycle.State.CREATED);
    super.onSaveInstanceState(paramBundle);
  }
  
  public void putExtraData(ExtraData paramExtraData) {
    this.mExtraDataMap.put(paramExtraData.getClass(), paramExtraData);
  }
  
  public static class ExtraData {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\app\SupportActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */