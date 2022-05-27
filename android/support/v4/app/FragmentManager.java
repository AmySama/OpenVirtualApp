package android.support.v4.app;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public abstract class FragmentManager {
  public static final int POP_BACK_STACK_INCLUSIVE = 1;
  
  public static void enableDebugLogging(boolean paramBoolean) {
    FragmentManagerImpl.DEBUG = paramBoolean;
  }
  
  public abstract void addOnBackStackChangedListener(OnBackStackChangedListener paramOnBackStackChangedListener);
  
  public abstract FragmentTransaction beginTransaction();
  
  public abstract void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString);
  
  public abstract boolean executePendingTransactions();
  
  public abstract Fragment findFragmentById(int paramInt);
  
  public abstract Fragment findFragmentByTag(String paramString);
  
  public abstract BackStackEntry getBackStackEntryAt(int paramInt);
  
  public abstract int getBackStackEntryCount();
  
  public abstract Fragment getFragment(Bundle paramBundle, String paramString);
  
  public abstract List<Fragment> getFragments();
  
  public abstract Fragment getPrimaryNavigationFragment();
  
  public abstract boolean isDestroyed();
  
  public abstract boolean isStateSaved();
  
  @Deprecated
  public FragmentTransaction openTransaction() {
    return beginTransaction();
  }
  
  public abstract void popBackStack();
  
  public abstract void popBackStack(int paramInt1, int paramInt2);
  
  public abstract void popBackStack(String paramString, int paramInt);
  
  public abstract boolean popBackStackImmediate();
  
  public abstract boolean popBackStackImmediate(int paramInt1, int paramInt2);
  
  public abstract boolean popBackStackImmediate(String paramString, int paramInt);
  
  public abstract void putFragment(Bundle paramBundle, String paramString, Fragment paramFragment);
  
  public abstract void registerFragmentLifecycleCallbacks(FragmentLifecycleCallbacks paramFragmentLifecycleCallbacks, boolean paramBoolean);
  
  public abstract void removeOnBackStackChangedListener(OnBackStackChangedListener paramOnBackStackChangedListener);
  
  public abstract Fragment.SavedState saveFragmentInstanceState(Fragment paramFragment);
  
  public abstract void unregisterFragmentLifecycleCallbacks(FragmentLifecycleCallbacks paramFragmentLifecycleCallbacks);
  
  public static interface BackStackEntry {
    CharSequence getBreadCrumbShortTitle();
    
    int getBreadCrumbShortTitleRes();
    
    CharSequence getBreadCrumbTitle();
    
    int getBreadCrumbTitleRes();
    
    int getId();
    
    String getName();
  }
  
  public static abstract class FragmentLifecycleCallbacks {
    public void onFragmentActivityCreated(FragmentManager param1FragmentManager, Fragment param1Fragment, Bundle param1Bundle) {}
    
    public void onFragmentAttached(FragmentManager param1FragmentManager, Fragment param1Fragment, Context param1Context) {}
    
    public void onFragmentCreated(FragmentManager param1FragmentManager, Fragment param1Fragment, Bundle param1Bundle) {}
    
    public void onFragmentDestroyed(FragmentManager param1FragmentManager, Fragment param1Fragment) {}
    
    public void onFragmentDetached(FragmentManager param1FragmentManager, Fragment param1Fragment) {}
    
    public void onFragmentPaused(FragmentManager param1FragmentManager, Fragment param1Fragment) {}
    
    public void onFragmentPreAttached(FragmentManager param1FragmentManager, Fragment param1Fragment, Context param1Context) {}
    
    public void onFragmentPreCreated(FragmentManager param1FragmentManager, Fragment param1Fragment, Bundle param1Bundle) {}
    
    public void onFragmentResumed(FragmentManager param1FragmentManager, Fragment param1Fragment) {}
    
    public void onFragmentSaveInstanceState(FragmentManager param1FragmentManager, Fragment param1Fragment, Bundle param1Bundle) {}
    
    public void onFragmentStarted(FragmentManager param1FragmentManager, Fragment param1Fragment) {}
    
    public void onFragmentStopped(FragmentManager param1FragmentManager, Fragment param1Fragment) {}
    
    public void onFragmentViewCreated(FragmentManager param1FragmentManager, Fragment param1Fragment, View param1View, Bundle param1Bundle) {}
    
    public void onFragmentViewDestroyed(FragmentManager param1FragmentManager, Fragment param1Fragment) {}
  }
  
  public static interface OnBackStackChangedListener {
    void onBackStackChanged();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\app\FragmentManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */