package io.virtualapp.abs.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import io.virtualapp.abs.BasePresenter;
import org.jdeferred.android.AndroidDeferredManager;

public class VFragment<T extends BasePresenter> extends Fragment {
  private boolean mAttach;
  
  protected T mPresenter;
  
  protected AndroidDeferredManager defer() {
    return VUiKit.defer();
  }
  
  public void destroy() {
    finishActivity();
  }
  
  public void finishActivity() {
    FragmentActivity fragmentActivity = getActivity();
    if (fragmentActivity != null)
      fragmentActivity.finish(); 
  }
  
  public T getPresenter() {
    return this.mPresenter;
  }
  
  public boolean isAttach() {
    return this.mAttach;
  }
  
  public void onAttach(Context paramContext) {
    this.mAttach = true;
    super.onAttach(paramContext);
  }
  
  public void onDetach() {
    this.mAttach = false;
    super.onDetach();
  }
  
  public void setPresenter(T paramT) {
    this.mPresenter = paramT;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\ab\\ui\VFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */