package android.support.v4.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public abstract class FragmentHostCallback<E> extends FragmentContainer {
  private final Activity mActivity;
  
  final Context mContext;
  
  final FragmentManagerImpl mFragmentManager = new FragmentManagerImpl();
  
  private final Handler mHandler;
  
  final int mWindowAnimations;
  
  FragmentHostCallback(Activity paramActivity, Context paramContext, Handler paramHandler, int paramInt) {
    this.mActivity = paramActivity;
    this.mContext = paramContext;
    this.mHandler = paramHandler;
    this.mWindowAnimations = paramInt;
  }
  
  public FragmentHostCallback(Context paramContext, Handler paramHandler, int paramInt) {
    this(activity, paramContext, paramHandler, paramInt);
  }
  
  FragmentHostCallback(FragmentActivity paramFragmentActivity) {
    this(paramFragmentActivity, (Context)paramFragmentActivity, paramFragmentActivity.mHandler, 0);
  }
  
  Activity getActivity() {
    return this.mActivity;
  }
  
  Context getContext() {
    return this.mContext;
  }
  
  FragmentManagerImpl getFragmentManagerImpl() {
    return this.mFragmentManager;
  }
  
  Handler getHandler() {
    return this.mHandler;
  }
  
  void onAttachFragment(Fragment paramFragment) {}
  
  public void onDump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString) {}
  
  public View onFindViewById(int paramInt) {
    return null;
  }
  
  public abstract E onGetHost();
  
  public LayoutInflater onGetLayoutInflater() {
    return LayoutInflater.from(this.mContext);
  }
  
  public int onGetWindowAnimations() {
    return this.mWindowAnimations;
  }
  
  public boolean onHasView() {
    return true;
  }
  
  public boolean onHasWindowAnimations() {
    return true;
  }
  
  public void onRequestPermissionsFromFragment(Fragment paramFragment, String[] paramArrayOfString, int paramInt) {}
  
  public boolean onShouldSaveFragmentState(Fragment paramFragment) {
    return true;
  }
  
  public boolean onShouldShowRequestPermissionRationale(String paramString) {
    return false;
  }
  
  public void onStartActivityFromFragment(Fragment paramFragment, Intent paramIntent, int paramInt) {
    onStartActivityFromFragment(paramFragment, paramIntent, paramInt, null);
  }
  
  public void onStartActivityFromFragment(Fragment paramFragment, Intent paramIntent, int paramInt, Bundle paramBundle) {
    if (paramInt == -1) {
      this.mContext.startActivity(paramIntent);
      return;
    } 
    throw new IllegalStateException("Starting activity with a requestCode requires a FragmentActivity host");
  }
  
  public void onStartIntentSenderFromFragment(Fragment paramFragment, IntentSender paramIntentSender, int paramInt1, Intent paramIntent, int paramInt2, int paramInt3, int paramInt4, Bundle paramBundle) throws IntentSender.SendIntentException {
    if (paramInt1 == -1) {
      ActivityCompat.startIntentSenderForResult(this.mActivity, paramIntentSender, paramInt1, paramIntent, paramInt2, paramInt3, paramInt4, paramBundle);
      return;
    } 
    throw new IllegalStateException("Starting intent sender with a requestCode requires a FragmentActivity host");
  }
  
  public void onSupportInvalidateOptionsMenu() {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\app\FragmentHostCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */