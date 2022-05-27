package android.arch.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;

public class HolderFragment extends Fragment implements ViewModelStoreOwner {
  public static final String HOLDER_TAG = "android.arch.lifecycle.state.StateProviderHolderFragment";
  
  private static final String LOG_TAG = "ViewModelStores";
  
  private static final HolderFragmentManager sHolderFragmentManager = new HolderFragmentManager();
  
  private ViewModelStore mViewModelStore = new ViewModelStore();
  
  public HolderFragment() {
    setRetainInstance(true);
  }
  
  public static HolderFragment holderFragmentFor(Fragment paramFragment) {
    return sHolderFragmentManager.holderFragmentFor(paramFragment);
  }
  
  public static HolderFragment holderFragmentFor(FragmentActivity paramFragmentActivity) {
    return sHolderFragmentManager.holderFragmentFor(paramFragmentActivity);
  }
  
  public ViewModelStore getViewModelStore() {
    return this.mViewModelStore;
  }
  
  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    sHolderFragmentManager.holderFragmentCreated(this);
  }
  
  public void onDestroy() {
    super.onDestroy();
    this.mViewModelStore.clear();
  }
  
  public void onSaveInstanceState(Bundle paramBundle) {
    super.onSaveInstanceState(paramBundle);
  }
  
  static class HolderFragmentManager {
    private Application.ActivityLifecycleCallbacks mActivityCallbacks = new EmptyActivityLifecycleCallbacks() {
        public void onActivityDestroyed(Activity param2Activity) {
          if ((HolderFragment)HolderFragment.HolderFragmentManager.this.mNotCommittedActivityHolders.remove(param2Activity) != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to save a ViewModel for ");
            stringBuilder.append(param2Activity);
            Log.e("ViewModelStores", stringBuilder.toString());
          } 
        }
      };
    
    private boolean mActivityCallbacksIsAdded = false;
    
    private Map<Activity, HolderFragment> mNotCommittedActivityHolders = new HashMap<Activity, HolderFragment>();
    
    private Map<Fragment, HolderFragment> mNotCommittedFragmentHolders = new HashMap<Fragment, HolderFragment>();
    
    private FragmentManager.FragmentLifecycleCallbacks mParentDestroyedCallback = new FragmentManager.FragmentLifecycleCallbacks() {
        public void onFragmentDestroyed(FragmentManager param2FragmentManager, Fragment param2Fragment) {
          super.onFragmentDestroyed(param2FragmentManager, param2Fragment);
          if ((HolderFragment)HolderFragment.HolderFragmentManager.this.mNotCommittedFragmentHolders.remove(param2Fragment) != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to save a ViewModel for ");
            stringBuilder.append(param2Fragment);
            Log.e("ViewModelStores", stringBuilder.toString());
          } 
        }
      };
    
    private static HolderFragment createHolderFragment(FragmentManager param1FragmentManager) {
      HolderFragment holderFragment = new HolderFragment();
      param1FragmentManager.beginTransaction().add(holderFragment, "android.arch.lifecycle.state.StateProviderHolderFragment").commitAllowingStateLoss();
      return holderFragment;
    }
    
    private static HolderFragment findHolderFragment(FragmentManager param1FragmentManager) {
      if (!param1FragmentManager.isDestroyed()) {
        Fragment fragment = param1FragmentManager.findFragmentByTag("android.arch.lifecycle.state.StateProviderHolderFragment");
        if (fragment == null || fragment instanceof HolderFragment)
          return (HolderFragment)fragment; 
        throw new IllegalStateException("Unexpected fragment instance was returned by HOLDER_TAG");
      } 
      throw new IllegalStateException("Can't access ViewModels from onDestroy");
    }
    
    void holderFragmentCreated(Fragment param1Fragment) {
      Fragment fragment = param1Fragment.getParentFragment();
      if (fragment != null) {
        this.mNotCommittedFragmentHolders.remove(fragment);
        fragment.getFragmentManager().unregisterFragmentLifecycleCallbacks(this.mParentDestroyedCallback);
      } else {
        this.mNotCommittedActivityHolders.remove(param1Fragment.getActivity());
      } 
    }
    
    HolderFragment holderFragmentFor(Fragment param1Fragment) {
      FragmentManager fragmentManager = param1Fragment.getChildFragmentManager();
      HolderFragment holderFragment2 = findHolderFragment(fragmentManager);
      if (holderFragment2 != null)
        return holderFragment2; 
      holderFragment2 = this.mNotCommittedFragmentHolders.get(param1Fragment);
      if (holderFragment2 != null)
        return holderFragment2; 
      param1Fragment.getFragmentManager().registerFragmentLifecycleCallbacks(this.mParentDestroyedCallback, false);
      HolderFragment holderFragment1 = createHolderFragment(fragmentManager);
      this.mNotCommittedFragmentHolders.put(param1Fragment, holderFragment1);
      return holderFragment1;
    }
    
    HolderFragment holderFragmentFor(FragmentActivity param1FragmentActivity) {
      FragmentManager fragmentManager = param1FragmentActivity.getSupportFragmentManager();
      HolderFragment holderFragment2 = findHolderFragment(fragmentManager);
      if (holderFragment2 != null)
        return holderFragment2; 
      holderFragment2 = this.mNotCommittedActivityHolders.get(param1FragmentActivity);
      if (holderFragment2 != null)
        return holderFragment2; 
      if (!this.mActivityCallbacksIsAdded) {
        this.mActivityCallbacksIsAdded = true;
        param1FragmentActivity.getApplication().registerActivityLifecycleCallbacks(this.mActivityCallbacks);
      } 
      HolderFragment holderFragment1 = createHolderFragment(fragmentManager);
      this.mNotCommittedActivityHolders.put(param1FragmentActivity, holderFragment1);
      return holderFragment1;
    }
  }
  
  class null extends EmptyActivityLifecycleCallbacks {
    public void onActivityDestroyed(Activity param1Activity) {
      if ((HolderFragment)this.this$0.mNotCommittedActivityHolders.remove(param1Activity) != null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to save a ViewModel for ");
        stringBuilder.append(param1Activity);
        Log.e("ViewModelStores", stringBuilder.toString());
      } 
    }
  }
  
  class null extends FragmentManager.FragmentLifecycleCallbacks {
    public void onFragmentDestroyed(FragmentManager param1FragmentManager, Fragment param1Fragment) {
      super.onFragmentDestroyed(param1FragmentManager, param1Fragment);
      if ((HolderFragment)this.this$0.mNotCommittedFragmentHolders.remove(param1Fragment) != null) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to save a ViewModel for ");
        stringBuilder.append(param1Fragment);
        Log.e("ViewModelStores", stringBuilder.toString());
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\lifecycle\HolderFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */