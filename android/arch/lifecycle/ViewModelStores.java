package android.arch.lifecycle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class ViewModelStores {
  public static ViewModelStore of(Fragment paramFragment) {
    return (paramFragment instanceof ViewModelStoreOwner) ? ((ViewModelStoreOwner)paramFragment).getViewModelStore() : HolderFragment.holderFragmentFor(paramFragment).getViewModelStore();
  }
  
  public static ViewModelStore of(FragmentActivity paramFragmentActivity) {
    return (paramFragmentActivity instanceof ViewModelStoreOwner) ? ((ViewModelStoreOwner)paramFragmentActivity).getViewModelStore() : HolderFragment.holderFragmentFor(paramFragmentActivity).getViewModelStore();
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\lifecycle\ViewModelStores.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */