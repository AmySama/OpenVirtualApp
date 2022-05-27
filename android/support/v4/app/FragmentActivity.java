package android.support.v4.app;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelStore;
import android.arch.lifecycle.ViewModelStoreOwner;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.util.SparseArrayCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Iterator;

public class FragmentActivity extends BaseFragmentActivityApi16 implements ViewModelStoreOwner, ActivityCompat.OnRequestPermissionsResultCallback, ActivityCompat.RequestPermissionsRequestCodeValidator {
  static final String ALLOCATED_REQUEST_INDICIES_TAG = "android:support:request_indicies";
  
  static final String FRAGMENTS_TAG = "android:support:fragments";
  
  static final int MAX_NUM_PENDING_FRAGMENT_ACTIVITY_RESULTS = 65534;
  
  static final int MSG_REALLY_STOPPED = 1;
  
  static final int MSG_RESUME_PENDING = 2;
  
  static final String NEXT_CANDIDATE_REQUEST_INDEX_TAG = "android:support:next_request_index";
  
  static final String REQUEST_FRAGMENT_WHO_TAG = "android:support:request_fragment_who";
  
  private static final String TAG = "FragmentActivity";
  
  boolean mCreated;
  
  final FragmentController mFragments = FragmentController.createController(new HostCallbacks());
  
  final Handler mHandler = new Handler() {
      public void handleMessage(Message param1Message) {
        int i = param1Message.what;
        if (i != 1) {
          if (i != 2) {
            super.handleMessage(param1Message);
          } else {
            FragmentActivity.this.onResumeFragments();
            FragmentActivity.this.mFragments.execPendingActions();
          } 
        } else if (FragmentActivity.this.mStopped) {
          FragmentActivity.this.doReallyStop(false);
        } 
      }
    };
  
  LoaderManager mLoaderManager;
  
  int mNextCandidateRequestIndex;
  
  SparseArrayCompat<String> mPendingFragmentActivityResults;
  
  boolean mReallyStopped = true;
  
  boolean mRequestedPermissionsFromFragment;
  
  boolean mResumed;
  
  boolean mRetaining;
  
  boolean mStopped = true;
  
  private ViewModelStore mViewModelStore;
  
  private int allocateRequestIndex(Fragment paramFragment) {
    if (this.mPendingFragmentActivityResults.size() < 65534) {
      while (this.mPendingFragmentActivityResults.indexOfKey(this.mNextCandidateRequestIndex) >= 0)
        this.mNextCandidateRequestIndex = (this.mNextCandidateRequestIndex + 1) % 65534; 
      int i = this.mNextCandidateRequestIndex;
      this.mPendingFragmentActivityResults.put(i, paramFragment.mWho);
      this.mNextCandidateRequestIndex = (this.mNextCandidateRequestIndex + 1) % 65534;
      return i;
    } 
    throw new IllegalStateException("Too many pending Fragment activity results.");
  }
  
  private void markFragmentsCreated() {
    do {
    
    } while (markState(getSupportFragmentManager(), Lifecycle.State.CREATED));
  }
  
  private static boolean markState(FragmentManager paramFragmentManager, Lifecycle.State paramState) {
    Iterator<Fragment> iterator = paramFragmentManager.getFragments().iterator();
    boolean bool = false;
    while (iterator.hasNext()) {
      Fragment fragment = iterator.next();
      if (fragment == null)
        continue; 
      boolean bool1 = bool;
      if (fragment.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
        fragment.mLifecycleRegistry.markState(paramState);
        bool1 = true;
      } 
      FragmentManager fragmentManager = fragment.peekChildFragmentManager();
      bool = bool1;
      if (fragmentManager != null)
        bool = bool1 | markState(fragmentManager, paramState); 
    } 
    return bool;
  }
  
  final View dispatchFragmentsOnCreateView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet) {
    return this.mFragments.onCreateView(paramView, paramString, paramContext, paramAttributeSet);
  }
  
  void doReallyStop(boolean paramBoolean) {
    if (!this.mReallyStopped) {
      this.mReallyStopped = true;
      this.mRetaining = paramBoolean;
      this.mHandler.removeMessages(1);
      onReallyStop();
    } 
  }
  
  public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString) {
    super.dump(paramString, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("Local FragmentActivity ");
    paramPrintWriter.print(Integer.toHexString(System.identityHashCode(this)));
    paramPrintWriter.println(" State:");
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramString);
    stringBuilder.append("  ");
    String str = stringBuilder.toString();
    paramPrintWriter.print(str);
    paramPrintWriter.print("mCreated=");
    paramPrintWriter.print(this.mCreated);
    paramPrintWriter.print("mResumed=");
    paramPrintWriter.print(this.mResumed);
    paramPrintWriter.print(" mStopped=");
    paramPrintWriter.print(this.mStopped);
    paramPrintWriter.print(" mReallyStopped=");
    paramPrintWriter.println(this.mReallyStopped);
    LoaderManager loaderManager = this.mLoaderManager;
    if (loaderManager != null)
      loaderManager.dump(str, paramFileDescriptor, paramPrintWriter, paramArrayOfString); 
    this.mFragments.getSupportFragmentManager().dump(paramString, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
  }
  
  public Object getLastCustomNonConfigurationInstance() {
    NonConfigurationInstances nonConfigurationInstances = (NonConfigurationInstances)getLastNonConfigurationInstance();
    if (nonConfigurationInstances != null) {
      Object object = nonConfigurationInstances.custom;
    } else {
      nonConfigurationInstances = null;
    } 
    return nonConfigurationInstances;
  }
  
  public Lifecycle getLifecycle() {
    return super.getLifecycle();
  }
  
  public FragmentManager getSupportFragmentManager() {
    return this.mFragments.getSupportFragmentManager();
  }
  
  public LoaderManager getSupportLoaderManager() {
    LoaderManager loaderManager = this.mLoaderManager;
    if (loaderManager != null)
      return loaderManager; 
    loaderManager = new LoaderManagerImpl(this, getViewModelStore());
    this.mLoaderManager = loaderManager;
    return loaderManager;
  }
  
  public ViewModelStore getViewModelStore() {
    if (getApplication() != null) {
      if (this.mViewModelStore == null)
        this.mViewModelStore = new ViewModelStore(); 
      return this.mViewModelStore;
    } 
    throw new IllegalStateException("Your activity is not yet attached to the Application instance. You can't request ViewModel before onCreate call.");
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    StringBuilder stringBuilder;
    this.mFragments.noteStateNotSaved();
    int i = paramInt1 >> 16;
    if (i != 0) {
      String str = (String)this.mPendingFragmentActivityResults.get(--i);
      this.mPendingFragmentActivityResults.remove(i);
      if (str == null) {
        Log.w("FragmentActivity", "Activity result delivered for unknown Fragment.");
        return;
      } 
      Fragment fragment = this.mFragments.findFragmentByWho(str);
      if (fragment == null) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("Activity result no fragment exists for who: ");
        stringBuilder.append(str);
        Log.w("FragmentActivity", stringBuilder.toString());
      } else {
        fragment.onActivityResult(paramInt1 & 0xFFFF, paramInt2, (Intent)stringBuilder);
      } 
      return;
    } 
    ActivityCompat.PermissionCompatDelegate permissionCompatDelegate = ActivityCompat.getPermissionCompatDelegate();
    if (permissionCompatDelegate != null && permissionCompatDelegate.onActivityResult(this, paramInt1, paramInt2, (Intent)stringBuilder))
      return; 
    super.onActivityResult(paramInt1, paramInt2, (Intent)stringBuilder);
  }
  
  public void onAttachFragment(Fragment paramFragment) {}
  
  public void onBackPressed() {
    FragmentManager fragmentManager = this.mFragments.getSupportFragmentManager();
    boolean bool = fragmentManager.isStateSaved();
    if (bool && Build.VERSION.SDK_INT <= 25)
      return; 
    if (bool || !fragmentManager.popBackStackImmediate())
      super.onBackPressed(); 
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration) {
    super.onConfigurationChanged(paramConfiguration);
    this.mFragments.noteStateNotSaved();
    this.mFragments.dispatchConfigurationChanged(paramConfiguration);
  }
  
  protected void onCreate(Bundle paramBundle) {
    FragmentController fragmentController = this.mFragments;
    FragmentManagerNonConfig fragmentManagerNonConfig = null;
    fragmentController.attachHost(null);
    super.onCreate(paramBundle);
    NonConfigurationInstances nonConfigurationInstances = (NonConfigurationInstances)getLastNonConfigurationInstance();
    if (nonConfigurationInstances != null)
      this.mViewModelStore = nonConfigurationInstances.viewModelStore; 
    if (paramBundle != null) {
      Parcelable parcelable = paramBundle.getParcelable("android:support:fragments");
      FragmentController fragmentController1 = this.mFragments;
      if (nonConfigurationInstances != null)
        fragmentManagerNonConfig = nonConfigurationInstances.fragments; 
      fragmentController1.restoreAllState(parcelable, fragmentManagerNonConfig);
      if (paramBundle.containsKey("android:support:next_request_index")) {
        this.mNextCandidateRequestIndex = paramBundle.getInt("android:support:next_request_index");
        int[] arrayOfInt = paramBundle.getIntArray("android:support:request_indicies");
        String[] arrayOfString = paramBundle.getStringArray("android:support:request_fragment_who");
        if (arrayOfInt == null || arrayOfString == null || arrayOfInt.length != arrayOfString.length) {
          Log.w("FragmentActivity", "Invalid requestCode mapping in savedInstanceState.");
        } else {
          this.mPendingFragmentActivityResults = new SparseArrayCompat(arrayOfInt.length);
          for (byte b = 0; b < arrayOfInt.length; b++)
            this.mPendingFragmentActivityResults.put(arrayOfInt[b], arrayOfString[b]); 
        } 
      } 
    } 
    if (this.mPendingFragmentActivityResults == null) {
      this.mPendingFragmentActivityResults = new SparseArrayCompat();
      this.mNextCandidateRequestIndex = 0;
    } 
    this.mFragments.dispatchCreate();
  }
  
  public boolean onCreatePanelMenu(int paramInt, Menu paramMenu) {
    return (paramInt == 0) ? (super.onCreatePanelMenu(paramInt, paramMenu) | this.mFragments.dispatchCreateOptionsMenu(paramMenu, getMenuInflater())) : super.onCreatePanelMenu(paramInt, paramMenu);
  }
  
  protected void onDestroy() {
    super.onDestroy();
    doReallyStop(false);
    ViewModelStore viewModelStore = this.mViewModelStore;
    if (viewModelStore != null && !this.mRetaining)
      viewModelStore.clear(); 
    this.mFragments.dispatchDestroy();
  }
  
  public void onLowMemory() {
    super.onLowMemory();
    this.mFragments.dispatchLowMemory();
  }
  
  public boolean onMenuItemSelected(int paramInt, MenuItem paramMenuItem) {
    return super.onMenuItemSelected(paramInt, paramMenuItem) ? true : ((paramInt != 0) ? ((paramInt != 6) ? false : this.mFragments.dispatchContextItemSelected(paramMenuItem)) : this.mFragments.dispatchOptionsItemSelected(paramMenuItem));
  }
  
  public void onMultiWindowModeChanged(boolean paramBoolean) {
    this.mFragments.dispatchMultiWindowModeChanged(paramBoolean);
  }
  
  protected void onNewIntent(Intent paramIntent) {
    super.onNewIntent(paramIntent);
    this.mFragments.noteStateNotSaved();
  }
  
  public void onPanelClosed(int paramInt, Menu paramMenu) {
    if (paramInt == 0)
      this.mFragments.dispatchOptionsMenuClosed(paramMenu); 
    super.onPanelClosed(paramInt, paramMenu);
  }
  
  protected void onPause() {
    super.onPause();
    this.mResumed = false;
    if (this.mHandler.hasMessages(2)) {
      this.mHandler.removeMessages(2);
      onResumeFragments();
    } 
    this.mFragments.dispatchPause();
  }
  
  public void onPictureInPictureModeChanged(boolean paramBoolean) {
    this.mFragments.dispatchPictureInPictureModeChanged(paramBoolean);
  }
  
  protected void onPostResume() {
    super.onPostResume();
    this.mHandler.removeMessages(2);
    onResumeFragments();
    this.mFragments.execPendingActions();
  }
  
  protected boolean onPrepareOptionsPanel(View paramView, Menu paramMenu) {
    return super.onPreparePanel(0, paramView, paramMenu);
  }
  
  public boolean onPreparePanel(int paramInt, View paramView, Menu paramMenu) {
    return (paramInt == 0 && paramMenu != null) ? (onPrepareOptionsPanel(paramView, paramMenu) | this.mFragments.dispatchPrepareOptionsMenu(paramMenu)) : super.onPreparePanel(paramInt, paramView, paramMenu);
  }
  
  void onReallyStop() {
    this.mFragments.dispatchReallyStop();
  }
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {
    this.mFragments.noteStateNotSaved();
    int i = paramInt >> 16 & 0xFFFF;
    if (i != 0) {
      StringBuilder stringBuilder;
      String str = (String)this.mPendingFragmentActivityResults.get(--i);
      this.mPendingFragmentActivityResults.remove(i);
      if (str == null) {
        Log.w("FragmentActivity", "Activity result delivered for unknown Fragment.");
        return;
      } 
      Fragment fragment = this.mFragments.findFragmentByWho(str);
      if (fragment == null) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("Activity result no fragment exists for who: ");
        stringBuilder.append(str);
        Log.w("FragmentActivity", stringBuilder.toString());
      } else {
        fragment.onRequestPermissionsResult(paramInt & 0xFFFF, (String[])stringBuilder, paramArrayOfint);
      } 
    } 
  }
  
  protected void onResume() {
    super.onResume();
    this.mHandler.sendEmptyMessage(2);
    this.mResumed = true;
    this.mFragments.execPendingActions();
  }
  
  protected void onResumeFragments() {
    this.mFragments.dispatchResume();
  }
  
  public Object onRetainCustomNonConfigurationInstance() {
    return null;
  }
  
  public final Object onRetainNonConfigurationInstance() {
    if (this.mStopped)
      doReallyStop(true); 
    Object object = onRetainCustomNonConfigurationInstance();
    FragmentManagerNonConfig fragmentManagerNonConfig = this.mFragments.retainNestedNonConfig();
    if (fragmentManagerNonConfig == null && this.mViewModelStore == null && object == null)
      return null; 
    NonConfigurationInstances nonConfigurationInstances = new NonConfigurationInstances();
    nonConfigurationInstances.custom = object;
    nonConfigurationInstances.viewModelStore = this.mViewModelStore;
    nonConfigurationInstances.fragments = fragmentManagerNonConfig;
    return nonConfigurationInstances;
  }
  
  protected void onSaveInstanceState(Bundle paramBundle) {
    super.onSaveInstanceState(paramBundle);
    markFragmentsCreated();
    Parcelable parcelable = this.mFragments.saveAllState();
    if (parcelable != null)
      paramBundle.putParcelable("android:support:fragments", parcelable); 
    if (this.mPendingFragmentActivityResults.size() > 0) {
      paramBundle.putInt("android:support:next_request_index", this.mNextCandidateRequestIndex);
      int[] arrayOfInt = new int[this.mPendingFragmentActivityResults.size()];
      String[] arrayOfString = new String[this.mPendingFragmentActivityResults.size()];
      for (byte b = 0; b < this.mPendingFragmentActivityResults.size(); b++) {
        arrayOfInt[b] = this.mPendingFragmentActivityResults.keyAt(b);
        arrayOfString[b] = (String)this.mPendingFragmentActivityResults.valueAt(b);
      } 
      paramBundle.putIntArray("android:support:request_indicies", arrayOfInt);
      paramBundle.putStringArray("android:support:request_fragment_who", arrayOfString);
    } 
  }
  
  protected void onStart() {
    super.onStart();
    this.mStopped = false;
    this.mReallyStopped = false;
    this.mHandler.removeMessages(1);
    if (!this.mCreated) {
      this.mCreated = true;
      this.mFragments.dispatchActivityCreated();
    } 
    this.mFragments.noteStateNotSaved();
    this.mFragments.execPendingActions();
    this.mFragments.dispatchStart();
  }
  
  public void onStateNotSaved() {
    this.mFragments.noteStateNotSaved();
  }
  
  protected void onStop() {
    super.onStop();
    this.mStopped = true;
    markFragmentsCreated();
    this.mHandler.sendEmptyMessage(1);
    this.mFragments.dispatchStop();
  }
  
  void requestPermissionsFromFragment(Fragment paramFragment, String[] paramArrayOfString, int paramInt) {
    if (paramInt == -1) {
      ActivityCompat.requestPermissions(this, paramArrayOfString, paramInt);
      return;
    } 
    checkForValidRequestCode(paramInt);
    try {
      this.mRequestedPermissionsFromFragment = true;
      ActivityCompat.requestPermissions(this, paramArrayOfString, (allocateRequestIndex(paramFragment) + 1 << 16) + (paramInt & 0xFFFF));
      return;
    } finally {
      this.mRequestedPermissionsFromFragment = false;
    } 
  }
  
  public void setEnterSharedElementCallback(SharedElementCallback paramSharedElementCallback) {
    ActivityCompat.setEnterSharedElementCallback(this, paramSharedElementCallback);
  }
  
  public void setExitSharedElementCallback(SharedElementCallback paramSharedElementCallback) {
    ActivityCompat.setExitSharedElementCallback(this, paramSharedElementCallback);
  }
  
  public void startActivityForResult(Intent paramIntent, int paramInt) {
    if (!this.mStartedActivityFromFragment && paramInt != -1)
      checkForValidRequestCode(paramInt); 
    super.startActivityForResult(paramIntent, paramInt);
  }
  
  public void startActivityFromFragment(Fragment paramFragment, Intent paramIntent, int paramInt) {
    startActivityFromFragment(paramFragment, paramIntent, paramInt, (Bundle)null);
  }
  
  public void startActivityFromFragment(Fragment paramFragment, Intent paramIntent, int paramInt, Bundle paramBundle) {
    this.mStartedActivityFromFragment = true;
    if (paramInt == -1)
      try {
        ActivityCompat.startActivityForResult(this, paramIntent, -1, paramBundle);
        return;
      } finally {
        this.mStartedActivityFromFragment = false;
      }  
    checkForValidRequestCode(paramInt);
    ActivityCompat.startActivityForResult(this, paramIntent, (allocateRequestIndex(paramFragment) + 1 << 16) + (paramInt & 0xFFFF), paramBundle);
    this.mStartedActivityFromFragment = false;
  }
  
  public void startIntentSenderFromFragment(Fragment paramFragment, IntentSender paramIntentSender, int paramInt1, Intent paramIntent, int paramInt2, int paramInt3, int paramInt4, Bundle paramBundle) throws IntentSender.SendIntentException {
    this.mStartedIntentSenderFromFragment = true;
    if (paramInt1 == -1)
      try {
        ActivityCompat.startIntentSenderForResult(this, paramIntentSender, paramInt1, paramIntent, paramInt2, paramInt3, paramInt4, paramBundle);
        return;
      } finally {
        this.mStartedIntentSenderFromFragment = false;
      }  
    checkForValidRequestCode(paramInt1);
    ActivityCompat.startIntentSenderForResult(this, paramIntentSender, (allocateRequestIndex(paramFragment) + 1 << 16) + (paramInt1 & 0xFFFF), paramIntent, paramInt2, paramInt3, paramInt4, paramBundle);
    this.mStartedIntentSenderFromFragment = false;
  }
  
  public void supportFinishAfterTransition() {
    ActivityCompat.finishAfterTransition(this);
  }
  
  @Deprecated
  public void supportInvalidateOptionsMenu() {
    invalidateOptionsMenu();
  }
  
  public void supportPostponeEnterTransition() {
    ActivityCompat.postponeEnterTransition(this);
  }
  
  public void supportStartPostponedEnterTransition() {
    ActivityCompat.startPostponedEnterTransition(this);
  }
  
  public final void validateRequestPermissionsRequestCode(int paramInt) {
    if (!this.mRequestedPermissionsFromFragment && paramInt != -1)
      checkForValidRequestCode(paramInt); 
  }
  
  class HostCallbacks extends FragmentHostCallback<FragmentActivity> {
    public HostCallbacks() {
      super(FragmentActivity.this);
    }
    
    public void onAttachFragment(Fragment param1Fragment) {
      FragmentActivity.this.onAttachFragment(param1Fragment);
    }
    
    public void onDump(String param1String, FileDescriptor param1FileDescriptor, PrintWriter param1PrintWriter, String[] param1ArrayOfString) {
      FragmentActivity.this.dump(param1String, param1FileDescriptor, param1PrintWriter, param1ArrayOfString);
    }
    
    public View onFindViewById(int param1Int) {
      return FragmentActivity.this.findViewById(param1Int);
    }
    
    public FragmentActivity onGetHost() {
      return FragmentActivity.this;
    }
    
    public LayoutInflater onGetLayoutInflater() {
      return FragmentActivity.this.getLayoutInflater().cloneInContext((Context)FragmentActivity.this);
    }
    
    public int onGetWindowAnimations() {
      int i;
      Window window = FragmentActivity.this.getWindow();
      if (window == null) {
        i = 0;
      } else {
        i = (window.getAttributes()).windowAnimations;
      } 
      return i;
    }
    
    public boolean onHasView() {
      boolean bool;
      Window window = FragmentActivity.this.getWindow();
      if (window != null && window.peekDecorView() != null) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public boolean onHasWindowAnimations() {
      boolean bool;
      if (FragmentActivity.this.getWindow() != null) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public void onRequestPermissionsFromFragment(Fragment param1Fragment, String[] param1ArrayOfString, int param1Int) {
      FragmentActivity.this.requestPermissionsFromFragment(param1Fragment, param1ArrayOfString, param1Int);
    }
    
    public boolean onShouldSaveFragmentState(Fragment param1Fragment) {
      return FragmentActivity.this.isFinishing() ^ true;
    }
    
    public boolean onShouldShowRequestPermissionRationale(String param1String) {
      return ActivityCompat.shouldShowRequestPermissionRationale(FragmentActivity.this, param1String);
    }
    
    public void onStartActivityFromFragment(Fragment param1Fragment, Intent param1Intent, int param1Int) {
      FragmentActivity.this.startActivityFromFragment(param1Fragment, param1Intent, param1Int);
    }
    
    public void onStartActivityFromFragment(Fragment param1Fragment, Intent param1Intent, int param1Int, Bundle param1Bundle) {
      FragmentActivity.this.startActivityFromFragment(param1Fragment, param1Intent, param1Int, param1Bundle);
    }
    
    public void onStartIntentSenderFromFragment(Fragment param1Fragment, IntentSender param1IntentSender, int param1Int1, Intent param1Intent, int param1Int2, int param1Int3, int param1Int4, Bundle param1Bundle) throws IntentSender.SendIntentException {
      FragmentActivity.this.startIntentSenderFromFragment(param1Fragment, param1IntentSender, param1Int1, param1Intent, param1Int2, param1Int3, param1Int4, param1Bundle);
    }
    
    public void onSupportInvalidateOptionsMenu() {
      FragmentActivity.this.supportInvalidateOptionsMenu();
    }
  }
  
  static final class NonConfigurationInstances {
    Object custom;
    
    FragmentManagerNonConfig fragments;
    
    ViewModelStore viewModelStore;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\app\FragmentActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */