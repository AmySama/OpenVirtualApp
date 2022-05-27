package android.arch.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.stub.StubApp;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

class LifecycleDispatcher {
  private static final String REPORT_FRAGMENT_TAG = "android.arch.lifecycle.LifecycleDispatcher.report_fragment_tag";
  
  private static AtomicBoolean sInitialized = new AtomicBoolean(false);
  
  private static void dispatchIfLifecycleOwner(Fragment paramFragment, Lifecycle.Event paramEvent) {
    if (paramFragment instanceof LifecycleRegistryOwner)
      ((LifecycleRegistryOwner)paramFragment).getLifecycle().handleLifecycleEvent(paramEvent); 
  }
  
  static void init(Context paramContext) {
    if (sInitialized.getAndSet(true))
      return; 
    ((Application)StubApp.getOrigApplicationContext(paramContext.getApplicationContext())).registerActivityLifecycleCallbacks(new DispatcherActivityCallback());
  }
  
  private static void markState(FragmentActivity paramFragmentActivity, Lifecycle.State paramState) {
    markStateIn(paramFragmentActivity, paramState);
    markState(paramFragmentActivity.getSupportFragmentManager(), paramState);
  }
  
  private static void markState(FragmentManager paramFragmentManager, Lifecycle.State paramState) {
    List list = paramFragmentManager.getFragments();
    if (list == null)
      return; 
    for (Fragment fragment : list) {
      if (fragment == null)
        continue; 
      markStateIn(fragment, paramState);
      if (fragment.isAdded())
        markState(fragment.getChildFragmentManager(), paramState); 
    } 
  }
  
  private static void markStateIn(Object paramObject, Lifecycle.State paramState) {
    if (paramObject instanceof LifecycleRegistryOwner)
      ((LifecycleRegistryOwner)paramObject).getLifecycle().markState(paramState); 
  }
  
  public static class DestructionReportFragment extends Fragment {
    protected void dispatch(Lifecycle.Event param1Event) {
      LifecycleDispatcher.dispatchIfLifecycleOwner(getParentFragment(), param1Event);
    }
    
    public void onDestroy() {
      super.onDestroy();
      dispatch(Lifecycle.Event.ON_DESTROY);
    }
    
    public void onPause() {
      super.onPause();
      dispatch(Lifecycle.Event.ON_PAUSE);
    }
    
    public void onStop() {
      super.onStop();
      dispatch(Lifecycle.Event.ON_STOP);
    }
  }
  
  static class DispatcherActivityCallback extends EmptyActivityLifecycleCallbacks {
    private final LifecycleDispatcher.FragmentCallback mFragmentCallback = new LifecycleDispatcher.FragmentCallback();
    
    public void onActivityCreated(Activity param1Activity, Bundle param1Bundle) {
      if (param1Activity instanceof FragmentActivity)
        ((FragmentActivity)param1Activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(this.mFragmentCallback, true); 
      ReportFragment.injectIfNeededIn(param1Activity);
    }
    
    public void onActivitySaveInstanceState(Activity param1Activity, Bundle param1Bundle) {
      if (param1Activity instanceof FragmentActivity)
        LifecycleDispatcher.markState((FragmentActivity)param1Activity, Lifecycle.State.CREATED); 
    }
    
    public void onActivityStopped(Activity param1Activity) {
      if (param1Activity instanceof FragmentActivity)
        LifecycleDispatcher.markState((FragmentActivity)param1Activity, Lifecycle.State.CREATED); 
    }
  }
  
  static class FragmentCallback extends FragmentManager.FragmentLifecycleCallbacks {
    public void onFragmentCreated(FragmentManager param1FragmentManager, Fragment param1Fragment, Bundle param1Bundle) {
      LifecycleDispatcher.dispatchIfLifecycleOwner(param1Fragment, Lifecycle.Event.ON_CREATE);
      if (!(param1Fragment instanceof LifecycleRegistryOwner))
        return; 
      if (param1Fragment.getChildFragmentManager().findFragmentByTag("android.arch.lifecycle.LifecycleDispatcher.report_fragment_tag") == null)
        param1Fragment.getChildFragmentManager().beginTransaction().add(new LifecycleDispatcher.DestructionReportFragment(), "android.arch.lifecycle.LifecycleDispatcher.report_fragment_tag").commit(); 
    }
    
    public void onFragmentResumed(FragmentManager param1FragmentManager, Fragment param1Fragment) {
      LifecycleDispatcher.dispatchIfLifecycleOwner(param1Fragment, Lifecycle.Event.ON_RESUME);
    }
    
    public void onFragmentStarted(FragmentManager param1FragmentManager, Fragment param1Fragment) {
      LifecycleDispatcher.dispatchIfLifecycleOwner(param1Fragment, Lifecycle.Event.ON_START);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\lifecycle\LifecycleDispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */