package android.arch.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class ViewModelProviders {
  private static Activity checkActivity(Fragment paramFragment) {
    FragmentActivity fragmentActivity = paramFragment.getActivity();
    if (fragmentActivity != null)
      return (Activity)fragmentActivity; 
    throw new IllegalStateException("Can't create ViewModelProvider for detached fragment");
  }
  
  private static Application checkApplication(Activity paramActivity) {
    Application application = paramActivity.getApplication();
    if (application != null)
      return application; 
    throw new IllegalStateException("Your activity/fragment is not yet attached to Application. You can't request ViewModel before onCreate call.");
  }
  
  public static ViewModelProvider of(Fragment paramFragment) {
    return of(paramFragment, (ViewModelProvider.Factory)null);
  }
  
  public static ViewModelProvider of(Fragment paramFragment, ViewModelProvider.Factory paramFactory) {
    Application application = checkApplication(checkActivity(paramFragment));
    ViewModelProvider.Factory factory = paramFactory;
    if (paramFactory == null)
      factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application); 
    return new ViewModelProvider(ViewModelStores.of(paramFragment), factory);
  }
  
  public static ViewModelProvider of(FragmentActivity paramFragmentActivity) {
    return of(paramFragmentActivity, (ViewModelProvider.Factory)null);
  }
  
  public static ViewModelProvider of(FragmentActivity paramFragmentActivity, ViewModelProvider.Factory paramFactory) {
    Application application = checkApplication((Activity)paramFragmentActivity);
    ViewModelProvider.Factory factory = paramFactory;
    if (paramFactory == null)
      factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application); 
    return new ViewModelProvider(ViewModelStores.of(paramFragmentActivity), factory);
  }
  
  @Deprecated
  public static class DefaultFactory extends ViewModelProvider.AndroidViewModelFactory {
    @Deprecated
    public DefaultFactory(Application param1Application) {
      super(param1Application);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\lifecycle\ViewModelProviders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */