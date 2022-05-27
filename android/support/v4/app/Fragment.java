package android.support.v4.app;

import android.animation.Animator;
import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.ViewModelStore;
import android.arch.lifecycle.ViewModelStoreOwner;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.view.LayoutInflaterCompat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

public class Fragment implements ComponentCallbacks, View.OnCreateContextMenuListener, LifecycleOwner, ViewModelStoreOwner {
  static final int ACTIVITY_CREATED = 2;
  
  static final int CREATED = 1;
  
  static final int INITIALIZING = 0;
  
  static final int RESUMED = 5;
  
  static final int STARTED = 4;
  
  static final int STOPPED = 3;
  
  static final Object USE_DEFAULT_TRANSITION;
  
  private static final SimpleArrayMap<String, Class<?>> sClassMap = new SimpleArrayMap();
  
  boolean mAdded;
  
  AnimationInfo mAnimationInfo;
  
  Bundle mArguments;
  
  int mBackStackNesting;
  
  boolean mCalled;
  
  FragmentManagerImpl mChildFragmentManager;
  
  FragmentManagerNonConfig mChildNonConfig;
  
  ViewGroup mContainer;
  
  int mContainerId;
  
  boolean mDeferStart;
  
  boolean mDetached;
  
  int mFragmentId;
  
  FragmentManagerImpl mFragmentManager;
  
  boolean mFromLayout;
  
  boolean mHasMenu;
  
  boolean mHidden;
  
  boolean mHiddenChanged;
  
  FragmentHostCallback mHost;
  
  boolean mInLayout;
  
  int mIndex = -1;
  
  View mInnerView;
  
  boolean mIsCreated;
  
  boolean mIsNewlyAdded;
  
  LayoutInflater mLayoutInflater;
  
  LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
  
  LoaderManagerImpl mLoaderManager;
  
  boolean mMenuVisible = true;
  
  Fragment mParentFragment;
  
  boolean mPerformedCreateView;
  
  float mPostponedAlpha;
  
  boolean mRemoving;
  
  boolean mRestored;
  
  boolean mRetainInstance;
  
  boolean mRetaining;
  
  Bundle mSavedFragmentState;
  
  Boolean mSavedUserVisibleHint;
  
  SparseArray<Parcelable> mSavedViewState;
  
  int mState = 0;
  
  String mTag;
  
  Fragment mTarget;
  
  int mTargetIndex = -1;
  
  int mTargetRequestCode;
  
  boolean mUserVisibleHint = true;
  
  View mView;
  
  ViewModelStore mViewModelStore;
  
  String mWho;
  
  static {
    USE_DEFAULT_TRANSITION = new Object();
  }
  
  private void callStartTransitionListener() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    OnStartEnterTransitionListener onStartEnterTransitionListener = null;
    if (animationInfo != null) {
      animationInfo.mEnterTransitionPostponed = false;
      onStartEnterTransitionListener = this.mAnimationInfo.mStartEnterTransitionListener;
      this.mAnimationInfo.mStartEnterTransitionListener = null;
    } 
    if (onStartEnterTransitionListener != null)
      onStartEnterTransitionListener.onStartEnterTransition(); 
  }
  
  private AnimationInfo ensureAnimationInfo() {
    if (this.mAnimationInfo == null)
      this.mAnimationInfo = new AnimationInfo(); 
    return this.mAnimationInfo;
  }
  
  public static Fragment instantiate(Context paramContext, String paramString) {
    return instantiate(paramContext, paramString, null);
  }
  
  public static Fragment instantiate(Context paramContext, String paramString, Bundle paramBundle) {
    try {
      Class<?> clazz1 = (Class)sClassMap.get(paramString);
      Class<?> clazz2 = clazz1;
      if (clazz1 == null) {
        clazz2 = paramContext.getClassLoader().loadClass(paramString);
        sClassMap.put(paramString, clazz2);
      } 
      Fragment fragment = clazz2.getConstructor(new Class[0]).newInstance(new Object[0]);
      if (paramBundle != null) {
        paramBundle.setClassLoader(fragment.getClass().getClassLoader());
        fragment.setArguments(paramBundle);
      } 
      return fragment;
    } catch (ClassNotFoundException classNotFoundException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Unable to instantiate fragment ");
      stringBuilder.append(paramString);
      stringBuilder.append(": make sure class name exists, is public, and has an");
      stringBuilder.append(" empty constructor that is public");
      throw new InstantiationException(stringBuilder.toString(), classNotFoundException);
    } catch (InstantiationException instantiationException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Unable to instantiate fragment ");
      stringBuilder.append(paramString);
      stringBuilder.append(": make sure class name exists, is public, and has an");
      stringBuilder.append(" empty constructor that is public");
      throw new InstantiationException(stringBuilder.toString(), instantiationException);
    } catch (IllegalAccessException illegalAccessException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Unable to instantiate fragment ");
      stringBuilder.append(paramString);
      stringBuilder.append(": make sure class name exists, is public, and has an");
      stringBuilder.append(" empty constructor that is public");
      throw new InstantiationException(stringBuilder.toString(), illegalAccessException);
    } catch (NoSuchMethodException noSuchMethodException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Unable to instantiate fragment ");
      stringBuilder.append(paramString);
      stringBuilder.append(": could not find Fragment constructor");
      throw new InstantiationException(stringBuilder.toString(), noSuchMethodException);
    } catch (InvocationTargetException invocationTargetException) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Unable to instantiate fragment ");
      stringBuilder.append(paramString);
      stringBuilder.append(": calling Fragment constructor caused an exception");
      throw new InstantiationException(stringBuilder.toString(), invocationTargetException);
    } 
  }
  
  static boolean isSupportFragmentClass(Context paramContext, String paramString) {
    try {
      Class<?> clazz1 = (Class)sClassMap.get(paramString);
      Class<?> clazz2 = clazz1;
      if (clazz1 == null) {
        clazz2 = paramContext.getClassLoader().loadClass(paramString);
        sClassMap.put(paramString, clazz2);
      } 
      return Fragment.class.isAssignableFrom(clazz2);
    } catch (ClassNotFoundException classNotFoundException) {
      return false;
    } 
  }
  
  public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString) {
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mFragmentId=#");
    paramPrintWriter.print(Integer.toHexString(this.mFragmentId));
    paramPrintWriter.print(" mContainerId=#");
    paramPrintWriter.print(Integer.toHexString(this.mContainerId));
    paramPrintWriter.print(" mTag=");
    paramPrintWriter.println(this.mTag);
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mState=");
    paramPrintWriter.print(this.mState);
    paramPrintWriter.print(" mIndex=");
    paramPrintWriter.print(this.mIndex);
    paramPrintWriter.print(" mWho=");
    paramPrintWriter.print(this.mWho);
    paramPrintWriter.print(" mBackStackNesting=");
    paramPrintWriter.println(this.mBackStackNesting);
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mAdded=");
    paramPrintWriter.print(this.mAdded);
    paramPrintWriter.print(" mRemoving=");
    paramPrintWriter.print(this.mRemoving);
    paramPrintWriter.print(" mFromLayout=");
    paramPrintWriter.print(this.mFromLayout);
    paramPrintWriter.print(" mInLayout=");
    paramPrintWriter.println(this.mInLayout);
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mHidden=");
    paramPrintWriter.print(this.mHidden);
    paramPrintWriter.print(" mDetached=");
    paramPrintWriter.print(this.mDetached);
    paramPrintWriter.print(" mMenuVisible=");
    paramPrintWriter.print(this.mMenuVisible);
    paramPrintWriter.print(" mHasMenu=");
    paramPrintWriter.println(this.mHasMenu);
    paramPrintWriter.print(paramString);
    paramPrintWriter.print("mRetainInstance=");
    paramPrintWriter.print(this.mRetainInstance);
    paramPrintWriter.print(" mRetaining=");
    paramPrintWriter.print(this.mRetaining);
    paramPrintWriter.print(" mUserVisibleHint=");
    paramPrintWriter.println(this.mUserVisibleHint);
    if (this.mFragmentManager != null) {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mFragmentManager=");
      paramPrintWriter.println(this.mFragmentManager);
    } 
    if (this.mHost != null) {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mHost=");
      paramPrintWriter.println(this.mHost);
    } 
    if (this.mParentFragment != null) {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mParentFragment=");
      paramPrintWriter.println(this.mParentFragment);
    } 
    if (this.mArguments != null) {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mArguments=");
      paramPrintWriter.println(this.mArguments);
    } 
    if (this.mSavedFragmentState != null) {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mSavedFragmentState=");
      paramPrintWriter.println(this.mSavedFragmentState);
    } 
    if (this.mSavedViewState != null) {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mSavedViewState=");
      paramPrintWriter.println(this.mSavedViewState);
    } 
    if (this.mTarget != null) {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mTarget=");
      paramPrintWriter.print(this.mTarget);
      paramPrintWriter.print(" mTargetRequestCode=");
      paramPrintWriter.println(this.mTargetRequestCode);
    } 
    if (getNextAnim() != 0) {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mNextAnim=");
      paramPrintWriter.println(getNextAnim());
    } 
    if (this.mContainer != null) {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mContainer=");
      paramPrintWriter.println(this.mContainer);
    } 
    if (this.mView != null) {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mView=");
      paramPrintWriter.println(this.mView);
    } 
    if (this.mInnerView != null) {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mInnerView=");
      paramPrintWriter.println(this.mView);
    } 
    if (getAnimatingAway() != null) {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mAnimatingAway=");
      paramPrintWriter.println(getAnimatingAway());
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mStateAfterAnimating=");
      paramPrintWriter.println(getStateAfterAnimating());
    } 
    if (this.mLoaderManager != null) {
      paramPrintWriter.print(paramString);
      paramPrintWriter.println("Loader Manager:");
      LoaderManagerImpl loaderManagerImpl = this.mLoaderManager;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramString);
      stringBuilder.append("  ");
      loaderManagerImpl.dump(stringBuilder.toString(), paramFileDescriptor, paramPrintWriter, paramArrayOfString);
    } 
    if (this.mChildFragmentManager != null) {
      paramPrintWriter.print(paramString);
      StringBuilder stringBuilder2 = new StringBuilder();
      stringBuilder2.append("Child ");
      stringBuilder2.append(this.mChildFragmentManager);
      stringBuilder2.append(":");
      paramPrintWriter.println(stringBuilder2.toString());
      FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(paramString);
      stringBuilder1.append("  ");
      fragmentManagerImpl.dump(stringBuilder1.toString(), paramFileDescriptor, paramPrintWriter, paramArrayOfString);
    } 
  }
  
  public final boolean equals(Object paramObject) {
    return super.equals(paramObject);
  }
  
  Fragment findFragmentByWho(String paramString) {
    if (paramString.equals(this.mWho))
      return this; 
    FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
    return (fragmentManagerImpl != null) ? fragmentManagerImpl.findFragmentByWho(paramString) : null;
  }
  
  public final FragmentActivity getActivity() {
    FragmentActivity fragmentActivity;
    FragmentHostCallback fragmentHostCallback = this.mHost;
    if (fragmentHostCallback == null) {
      fragmentHostCallback = null;
    } else {
      fragmentActivity = (FragmentActivity)fragmentHostCallback.getActivity();
    } 
    return fragmentActivity;
  }
  
  public boolean getAllowEnterTransitionOverlap() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null || animationInfo.mAllowEnterTransitionOverlap == null) ? true : this.mAnimationInfo.mAllowEnterTransitionOverlap.booleanValue();
  }
  
  public boolean getAllowReturnTransitionOverlap() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null || animationInfo.mAllowReturnTransitionOverlap == null) ? true : this.mAnimationInfo.mAllowReturnTransitionOverlap.booleanValue();
  }
  
  View getAnimatingAway() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null) ? null : animationInfo.mAnimatingAway;
  }
  
  Animator getAnimator() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null) ? null : animationInfo.mAnimator;
  }
  
  public final Bundle getArguments() {
    return this.mArguments;
  }
  
  public final FragmentManager getChildFragmentManager() {
    if (this.mChildFragmentManager == null) {
      instantiateChildFragmentManager();
      int i = this.mState;
      if (i >= 5) {
        this.mChildFragmentManager.dispatchResume();
      } else if (i >= 4) {
        this.mChildFragmentManager.dispatchStart();
      } else if (i >= 2) {
        this.mChildFragmentManager.dispatchActivityCreated();
      } else if (i >= 1) {
        this.mChildFragmentManager.dispatchCreate();
      } 
    } 
    return this.mChildFragmentManager;
  }
  
  public Context getContext() {
    Context context;
    FragmentHostCallback fragmentHostCallback = this.mHost;
    if (fragmentHostCallback == null) {
      fragmentHostCallback = null;
    } else {
      context = fragmentHostCallback.getContext();
    } 
    return context;
  }
  
  public Object getEnterTransition() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null) ? null : animationInfo.mEnterTransition;
  }
  
  SharedElementCallback getEnterTransitionCallback() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null) ? null : animationInfo.mEnterTransitionCallback;
  }
  
  public Object getExitTransition() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null) ? null : animationInfo.mExitTransition;
  }
  
  SharedElementCallback getExitTransitionCallback() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null) ? null : animationInfo.mExitTransitionCallback;
  }
  
  public final FragmentManager getFragmentManager() {
    return this.mFragmentManager;
  }
  
  public final Object getHost() {
    FragmentHostCallback<FragmentHostCallback> fragmentHostCallback = this.mHost;
    if (fragmentHostCallback == null) {
      fragmentHostCallback = null;
    } else {
      fragmentHostCallback = fragmentHostCallback.onGetHost();
    } 
    return fragmentHostCallback;
  }
  
  public final int getId() {
    return this.mFragmentId;
  }
  
  public final LayoutInflater getLayoutInflater() {
    LayoutInflater layoutInflater1 = this.mLayoutInflater;
    LayoutInflater layoutInflater2 = layoutInflater1;
    if (layoutInflater1 == null)
      layoutInflater2 = performGetLayoutInflater(null); 
    return layoutInflater2;
  }
  
  @Deprecated
  public LayoutInflater getLayoutInflater(Bundle paramBundle) {
    FragmentHostCallback fragmentHostCallback = this.mHost;
    if (fragmentHostCallback != null) {
      LayoutInflater layoutInflater = fragmentHostCallback.onGetLayoutInflater();
      getChildFragmentManager();
      LayoutInflaterCompat.setFactory2(layoutInflater, this.mChildFragmentManager.getLayoutInflaterFactory());
      return layoutInflater;
    } 
    throw new IllegalStateException("onGetLayoutInflater() cannot be executed until the Fragment is attached to the FragmentManager.");
  }
  
  public Lifecycle getLifecycle() {
    return (Lifecycle)this.mLifecycleRegistry;
  }
  
  public LoaderManager getLoaderManager() {
    LoaderManagerImpl loaderManagerImpl = this.mLoaderManager;
    if (loaderManagerImpl != null)
      return loaderManagerImpl; 
    loaderManagerImpl = new LoaderManagerImpl(this, getViewModelStore());
    this.mLoaderManager = loaderManagerImpl;
    return loaderManagerImpl;
  }
  
  int getNextAnim() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null) ? 0 : animationInfo.mNextAnim;
  }
  
  int getNextTransition() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null) ? 0 : animationInfo.mNextTransition;
  }
  
  int getNextTransitionStyle() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null) ? 0 : animationInfo.mNextTransitionStyle;
  }
  
  public final Fragment getParentFragment() {
    return this.mParentFragment;
  }
  
  public Object getReenterTransition() {
    Object object = this.mAnimationInfo;
    if (object == null)
      return null; 
    if (((AnimationInfo)object).mReenterTransition == USE_DEFAULT_TRANSITION) {
      Object object1 = getExitTransition();
    } else {
      object = this.mAnimationInfo.mReenterTransition;
    } 
    return object;
  }
  
  public final Resources getResources() {
    return requireContext().getResources();
  }
  
  public final boolean getRetainInstance() {
    return this.mRetainInstance;
  }
  
  public Object getReturnTransition() {
    Object object = this.mAnimationInfo;
    if (object == null)
      return null; 
    if (((AnimationInfo)object).mReturnTransition == USE_DEFAULT_TRANSITION) {
      Object object1 = getEnterTransition();
    } else {
      object = this.mAnimationInfo.mReturnTransition;
    } 
    return object;
  }
  
  public Object getSharedElementEnterTransition() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null) ? null : animationInfo.mSharedElementEnterTransition;
  }
  
  public Object getSharedElementReturnTransition() {
    Object object = this.mAnimationInfo;
    if (object == null)
      return null; 
    if (((AnimationInfo)object).mSharedElementReturnTransition == USE_DEFAULT_TRANSITION) {
      Object object1 = getSharedElementEnterTransition();
    } else {
      object = this.mAnimationInfo.mSharedElementReturnTransition;
    } 
    return object;
  }
  
  int getStateAfterAnimating() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null) ? 0 : animationInfo.mStateAfterAnimating;
  }
  
  public final String getString(int paramInt) {
    return getResources().getString(paramInt);
  }
  
  public final String getString(int paramInt, Object... paramVarArgs) {
    return getResources().getString(paramInt, paramVarArgs);
  }
  
  public final String getTag() {
    return this.mTag;
  }
  
  public final Fragment getTargetFragment() {
    return this.mTarget;
  }
  
  public final int getTargetRequestCode() {
    return this.mTargetRequestCode;
  }
  
  public final CharSequence getText(int paramInt) {
    return getResources().getText(paramInt);
  }
  
  public boolean getUserVisibleHint() {
    return this.mUserVisibleHint;
  }
  
  public View getView() {
    return this.mView;
  }
  
  public ViewModelStore getViewModelStore() {
    if (getContext() != null) {
      if (this.mViewModelStore == null)
        this.mViewModelStore = new ViewModelStore(); 
      return this.mViewModelStore;
    } 
    throw new IllegalStateException("Can't access ViewModels from detached fragment");
  }
  
  public final boolean hasOptionsMenu() {
    return this.mHasMenu;
  }
  
  public final int hashCode() {
    return super.hashCode();
  }
  
  void initState() {
    this.mIndex = -1;
    this.mWho = null;
    this.mAdded = false;
    this.mRemoving = false;
    this.mFromLayout = false;
    this.mInLayout = false;
    this.mRestored = false;
    this.mBackStackNesting = 0;
    this.mFragmentManager = null;
    this.mChildFragmentManager = null;
    this.mHost = null;
    this.mFragmentId = 0;
    this.mContainerId = 0;
    this.mTag = null;
    this.mHidden = false;
    this.mDetached = false;
    this.mRetaining = false;
  }
  
  void instantiateChildFragmentManager() {
    if (this.mHost != null) {
      FragmentManagerImpl fragmentManagerImpl = new FragmentManagerImpl();
      this.mChildFragmentManager = fragmentManagerImpl;
      fragmentManagerImpl.attachController(this.mHost, new FragmentContainer() {
            public Fragment instantiate(Context param1Context, String param1String, Bundle param1Bundle) {
              return Fragment.this.mHost.instantiate(param1Context, param1String, param1Bundle);
            }
            
            public View onFindViewById(int param1Int) {
              if (Fragment.this.mView != null)
                return Fragment.this.mView.findViewById(param1Int); 
              throw new IllegalStateException("Fragment does not have a view");
            }
            
            public boolean onHasView() {
              boolean bool;
              if (Fragment.this.mView != null) {
                bool = true;
              } else {
                bool = false;
              } 
              return bool;
            }
          }this);
      return;
    } 
    throw new IllegalStateException("Fragment has not been attached yet.");
  }
  
  public final boolean isAdded() {
    boolean bool;
    if (this.mHost != null && this.mAdded) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public final boolean isDetached() {
    return this.mDetached;
  }
  
  public final boolean isHidden() {
    return this.mHidden;
  }
  
  boolean isHideReplaced() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null) ? false : animationInfo.mIsHideReplaced;
  }
  
  final boolean isInBackStack() {
    boolean bool;
    if (this.mBackStackNesting > 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public final boolean isInLayout() {
    return this.mInLayout;
  }
  
  public final boolean isMenuVisible() {
    return this.mMenuVisible;
  }
  
  boolean isPostponed() {
    AnimationInfo animationInfo = this.mAnimationInfo;
    return (animationInfo == null) ? false : animationInfo.mEnterTransitionPostponed;
  }
  
  public final boolean isRemoving() {
    return this.mRemoving;
  }
  
  public final boolean isResumed() {
    boolean bool;
    if (this.mState >= 5) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public final boolean isStateSaved() {
    FragmentManagerImpl fragmentManagerImpl = this.mFragmentManager;
    return (fragmentManagerImpl == null) ? false : fragmentManagerImpl.isStateSaved();
  }
  
  public final boolean isVisible() {
    if (isAdded() && !isHidden()) {
      View view = this.mView;
      if (view != null && view.getWindowToken() != null && this.mView.getVisibility() == 0)
        return true; 
    } 
    return false;
  }
  
  void noteStateNotSaved() {
    FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
    if (fragmentManagerImpl != null)
      fragmentManagerImpl.noteStateNotSaved(); 
  }
  
  public void onActivityCreated(Bundle paramBundle) {
    this.mCalled = true;
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {}
  
  @Deprecated
  public void onAttach(Activity paramActivity) {
    this.mCalled = true;
  }
  
  public void onAttach(Context paramContext) {
    Activity activity;
    this.mCalled = true;
    FragmentHostCallback fragmentHostCallback = this.mHost;
    if (fragmentHostCallback == null) {
      fragmentHostCallback = null;
    } else {
      activity = fragmentHostCallback.getActivity();
    } 
    if (activity != null) {
      this.mCalled = false;
      onAttach(activity);
    } 
  }
  
  public void onAttachFragment(Fragment paramFragment) {}
  
  public void onConfigurationChanged(Configuration paramConfiguration) {
    this.mCalled = true;
  }
  
  public boolean onContextItemSelected(MenuItem paramMenuItem) {
    return false;
  }
  
  public void onCreate(Bundle paramBundle) {
    this.mCalled = true;
    restoreChildFragmentState(paramBundle);
    FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
    if (fragmentManagerImpl != null && !fragmentManagerImpl.isStateAtLeast(1))
      this.mChildFragmentManager.dispatchCreate(); 
  }
  
  public Animation onCreateAnimation(int paramInt1, boolean paramBoolean, int paramInt2) {
    return null;
  }
  
  public Animator onCreateAnimator(int paramInt1, boolean paramBoolean, int paramInt2) {
    return null;
  }
  
  public void onCreateContextMenu(ContextMenu paramContextMenu, View paramView, ContextMenu.ContextMenuInfo paramContextMenuInfo) {
    getActivity().onCreateContextMenu(paramContextMenu, paramView, paramContextMenuInfo);
  }
  
  public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater) {}
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    return null;
  }
  
  public void onDestroy() {
    this.mCalled = true;
    if (this.mViewModelStore != null && !this.mHost.mFragmentManager.mStateSaved)
      this.mViewModelStore.clear(); 
  }
  
  public void onDestroyOptionsMenu() {}
  
  public void onDestroyView() {
    this.mCalled = true;
  }
  
  public void onDetach() {
    this.mCalled = true;
  }
  
  public LayoutInflater onGetLayoutInflater(Bundle paramBundle) {
    return getLayoutInflater(paramBundle);
  }
  
  public void onHiddenChanged(boolean paramBoolean) {}
  
  @Deprecated
  public void onInflate(Activity paramActivity, AttributeSet paramAttributeSet, Bundle paramBundle) {
    this.mCalled = true;
  }
  
  public void onInflate(Context paramContext, AttributeSet paramAttributeSet, Bundle paramBundle) {
    Activity activity;
    this.mCalled = true;
    FragmentHostCallback fragmentHostCallback = this.mHost;
    if (fragmentHostCallback == null) {
      fragmentHostCallback = null;
    } else {
      activity = fragmentHostCallback.getActivity();
    } 
    if (activity != null) {
      this.mCalled = false;
      onInflate(activity, paramAttributeSet, paramBundle);
    } 
  }
  
  public void onLowMemory() {
    this.mCalled = true;
  }
  
  public void onMultiWindowModeChanged(boolean paramBoolean) {}
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
    return false;
  }
  
  public void onOptionsMenuClosed(Menu paramMenu) {}
  
  public void onPause() {
    this.mCalled = true;
  }
  
  public void onPictureInPictureModeChanged(boolean paramBoolean) {}
  
  public void onPrepareOptionsMenu(Menu paramMenu) {}
  
  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfint) {}
  
  public void onResume() {
    this.mCalled = true;
  }
  
  public void onSaveInstanceState(Bundle paramBundle) {}
  
  public void onStart() {
    this.mCalled = true;
  }
  
  public void onStop() {
    this.mCalled = true;
  }
  
  public void onViewCreated(View paramView, Bundle paramBundle) {}
  
  public void onViewStateRestored(Bundle paramBundle) {
    this.mCalled = true;
  }
  
  FragmentManager peekChildFragmentManager() {
    return this.mChildFragmentManager;
  }
  
  void performActivityCreated(Bundle paramBundle) {
    FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
    if (fragmentManagerImpl != null)
      fragmentManagerImpl.noteStateNotSaved(); 
    this.mState = 2;
    this.mCalled = false;
    onActivityCreated(paramBundle);
    if (this.mCalled) {
      FragmentManagerImpl fragmentManagerImpl1 = this.mChildFragmentManager;
      if (fragmentManagerImpl1 != null)
        fragmentManagerImpl1.dispatchActivityCreated(); 
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" did not call through to super.onActivityCreated()");
    throw new SuperNotCalledException(stringBuilder.toString());
  }
  
  void performConfigurationChanged(Configuration paramConfiguration) {
    onConfigurationChanged(paramConfiguration);
    FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
    if (fragmentManagerImpl != null)
      fragmentManagerImpl.dispatchConfigurationChanged(paramConfiguration); 
  }
  
  boolean performContextItemSelected(MenuItem paramMenuItem) {
    if (!this.mHidden) {
      if (onContextItemSelected(paramMenuItem))
        return true; 
      FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
      if (fragmentManagerImpl != null && fragmentManagerImpl.dispatchContextItemSelected(paramMenuItem))
        return true; 
    } 
    return false;
  }
  
  void performCreate(Bundle paramBundle) {
    FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
    if (fragmentManagerImpl != null)
      fragmentManagerImpl.noteStateNotSaved(); 
    this.mState = 1;
    this.mCalled = false;
    onCreate(paramBundle);
    this.mIsCreated = true;
    if (this.mCalled) {
      this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" did not call through to super.onCreate()");
    throw new SuperNotCalledException(stringBuilder.toString());
  }
  
  boolean performCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater) {
    boolean bool1 = this.mHidden;
    boolean bool2 = false;
    boolean bool = false;
    if (!bool1) {
      bool1 = bool;
      if (this.mHasMenu) {
        bool1 = bool;
        if (this.mMenuVisible) {
          onCreateOptionsMenu(paramMenu, paramMenuInflater);
          bool1 = true;
        } 
      } 
      FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
      bool2 = bool1;
      if (fragmentManagerImpl != null)
        bool2 = bool1 | fragmentManagerImpl.dispatchCreateOptionsMenu(paramMenu, paramMenuInflater); 
    } 
    return bool2;
  }
  
  View performCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
    FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
    if (fragmentManagerImpl != null)
      fragmentManagerImpl.noteStateNotSaved(); 
    this.mPerformedCreateView = true;
    return onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
  }
  
  void performDestroy() {
    this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
    if (fragmentManagerImpl != null)
      fragmentManagerImpl.dispatchDestroy(); 
    this.mState = 0;
    this.mCalled = false;
    this.mIsCreated = false;
    onDestroy();
    if (this.mCalled) {
      this.mChildFragmentManager = null;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" did not call through to super.onDestroy()");
    throw new SuperNotCalledException(stringBuilder.toString());
  }
  
  void performDestroyView() {
    FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
    if (fragmentManagerImpl != null)
      fragmentManagerImpl.dispatchDestroyView(); 
    this.mState = 1;
    this.mCalled = false;
    onDestroyView();
    if (this.mCalled) {
      LoaderManagerImpl loaderManagerImpl = this.mLoaderManager;
      if (loaderManagerImpl != null)
        loaderManagerImpl.markForRedelivery(); 
      this.mPerformedCreateView = false;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" did not call through to super.onDestroyView()");
    throw new SuperNotCalledException(stringBuilder.toString());
  }
  
  void performDetach() {
    this.mCalled = false;
    onDetach();
    this.mLayoutInflater = null;
    if (this.mCalled) {
      FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
      if (fragmentManagerImpl != null)
        if (this.mRetaining) {
          fragmentManagerImpl.dispatchDestroy();
          this.mChildFragmentManager = null;
        } else {
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append("Child FragmentManager of ");
          stringBuilder1.append(this);
          stringBuilder1.append(" was not ");
          stringBuilder1.append(" destroyed and this fragment is not retaining instance");
          throw new IllegalStateException(stringBuilder1.toString());
        }  
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" did not call through to super.onDetach()");
    throw new SuperNotCalledException(stringBuilder.toString());
  }
  
  LayoutInflater performGetLayoutInflater(Bundle paramBundle) {
    LayoutInflater layoutInflater = onGetLayoutInflater(paramBundle);
    this.mLayoutInflater = layoutInflater;
    return layoutInflater;
  }
  
  void performLowMemory() {
    onLowMemory();
    FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
    if (fragmentManagerImpl != null)
      fragmentManagerImpl.dispatchLowMemory(); 
  }
  
  void performMultiWindowModeChanged(boolean paramBoolean) {
    onMultiWindowModeChanged(paramBoolean);
    FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
    if (fragmentManagerImpl != null)
      fragmentManagerImpl.dispatchMultiWindowModeChanged(paramBoolean); 
  }
  
  boolean performOptionsItemSelected(MenuItem paramMenuItem) {
    if (!this.mHidden) {
      if (this.mHasMenu && this.mMenuVisible && onOptionsItemSelected(paramMenuItem))
        return true; 
      FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
      if (fragmentManagerImpl != null && fragmentManagerImpl.dispatchOptionsItemSelected(paramMenuItem))
        return true; 
    } 
    return false;
  }
  
  void performOptionsMenuClosed(Menu paramMenu) {
    if (!this.mHidden) {
      if (this.mHasMenu && this.mMenuVisible)
        onOptionsMenuClosed(paramMenu); 
      FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
      if (fragmentManagerImpl != null)
        fragmentManagerImpl.dispatchOptionsMenuClosed(paramMenu); 
    } 
  }
  
  void performPause() {
    this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
    if (fragmentManagerImpl != null)
      fragmentManagerImpl.dispatchPause(); 
    this.mState = 4;
    this.mCalled = false;
    onPause();
    if (this.mCalled)
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" did not call through to super.onPause()");
    throw new SuperNotCalledException(stringBuilder.toString());
  }
  
  void performPictureInPictureModeChanged(boolean paramBoolean) {
    onPictureInPictureModeChanged(paramBoolean);
    FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
    if (fragmentManagerImpl != null)
      fragmentManagerImpl.dispatchPictureInPictureModeChanged(paramBoolean); 
  }
  
  boolean performPrepareOptionsMenu(Menu paramMenu) {
    boolean bool1 = this.mHidden;
    boolean bool2 = false;
    boolean bool = false;
    if (!bool1) {
      bool1 = bool;
      if (this.mHasMenu) {
        bool1 = bool;
        if (this.mMenuVisible) {
          onPrepareOptionsMenu(paramMenu);
          bool1 = true;
        } 
      } 
      FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
      bool2 = bool1;
      if (fragmentManagerImpl != null)
        bool2 = bool1 | fragmentManagerImpl.dispatchPrepareOptionsMenu(paramMenu); 
    } 
    return bool2;
  }
  
  void performReallyStop() {
    FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
    if (fragmentManagerImpl != null)
      fragmentManagerImpl.dispatchReallyStop(); 
    this.mState = 2;
  }
  
  void performResume() {
    FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
    if (fragmentManagerImpl != null) {
      fragmentManagerImpl.noteStateNotSaved();
      this.mChildFragmentManager.execPendingActions();
    } 
    this.mState = 5;
    this.mCalled = false;
    onResume();
    if (this.mCalled) {
      fragmentManagerImpl = this.mChildFragmentManager;
      if (fragmentManagerImpl != null) {
        fragmentManagerImpl.dispatchResume();
        this.mChildFragmentManager.execPendingActions();
      } 
      this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" did not call through to super.onResume()");
    throw new SuperNotCalledException(stringBuilder.toString());
  }
  
  void performSaveInstanceState(Bundle paramBundle) {
    onSaveInstanceState(paramBundle);
    FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
    if (fragmentManagerImpl != null) {
      Parcelable parcelable = fragmentManagerImpl.saveAllState();
      if (parcelable != null)
        paramBundle.putParcelable("android:support:fragments", parcelable); 
    } 
  }
  
  void performStart() {
    FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
    if (fragmentManagerImpl != null) {
      fragmentManagerImpl.noteStateNotSaved();
      this.mChildFragmentManager.execPendingActions();
    } 
    this.mState = 4;
    this.mCalled = false;
    onStart();
    if (this.mCalled) {
      fragmentManagerImpl = this.mChildFragmentManager;
      if (fragmentManagerImpl != null)
        fragmentManagerImpl.dispatchStart(); 
      this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" did not call through to super.onStart()");
    throw new SuperNotCalledException(stringBuilder.toString());
  }
  
  void performStop() {
    this.mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    FragmentManagerImpl fragmentManagerImpl = this.mChildFragmentManager;
    if (fragmentManagerImpl != null)
      fragmentManagerImpl.dispatchStop(); 
    this.mState = 3;
    this.mCalled = false;
    onStop();
    if (this.mCalled)
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" did not call through to super.onStop()");
    throw new SuperNotCalledException(stringBuilder.toString());
  }
  
  public void postponeEnterTransition() {
    (ensureAnimationInfo()).mEnterTransitionPostponed = true;
  }
  
  public void registerForContextMenu(View paramView) {
    paramView.setOnCreateContextMenuListener(this);
  }
  
  public final void requestPermissions(String[] paramArrayOfString, int paramInt) {
    FragmentHostCallback fragmentHostCallback = this.mHost;
    if (fragmentHostCallback != null) {
      fragmentHostCallback.onRequestPermissionsFromFragment(this, paramArrayOfString, paramInt);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" not attached to Activity");
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public final FragmentActivity requireActivity() {
    FragmentActivity fragmentActivity = getActivity();
    if (fragmentActivity != null)
      return fragmentActivity; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" not attached to an activity.");
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public final Context requireContext() {
    Context context = getContext();
    if (context != null)
      return context; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" not attached to a context.");
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public final FragmentManager requireFragmentManager() {
    FragmentManager fragmentManager = getFragmentManager();
    if (fragmentManager != null)
      return fragmentManager; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" not associated with a fragment manager.");
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public final Object requireHost() {
    Object object = getHost();
    if (object != null)
      return object; 
    object = new StringBuilder();
    object.append("Fragment ");
    object.append(this);
    object.append(" not attached to a host.");
    throw new IllegalStateException(object.toString());
  }
  
  void restoreChildFragmentState(Bundle paramBundle) {
    if (paramBundle != null) {
      Parcelable parcelable = paramBundle.getParcelable("android:support:fragments");
      if (parcelable != null) {
        if (this.mChildFragmentManager == null)
          instantiateChildFragmentManager(); 
        this.mChildFragmentManager.restoreAllState(parcelable, this.mChildNonConfig);
        this.mChildNonConfig = null;
        this.mChildFragmentManager.dispatchCreate();
      } 
    } 
  }
  
  final void restoreViewState(Bundle paramBundle) {
    SparseArray<Parcelable> sparseArray = this.mSavedViewState;
    if (sparseArray != null) {
      this.mInnerView.restoreHierarchyState(sparseArray);
      this.mSavedViewState = null;
    } 
    this.mCalled = false;
    onViewStateRestored(paramBundle);
    if (this.mCalled)
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" did not call through to super.onViewStateRestored()");
    throw new SuperNotCalledException(stringBuilder.toString());
  }
  
  public void setAllowEnterTransitionOverlap(boolean paramBoolean) {
    AnimationInfo.access$602(ensureAnimationInfo(), Boolean.valueOf(paramBoolean));
  }
  
  public void setAllowReturnTransitionOverlap(boolean paramBoolean) {
    AnimationInfo.access$702(ensureAnimationInfo(), Boolean.valueOf(paramBoolean));
  }
  
  void setAnimatingAway(View paramView) {
    (ensureAnimationInfo()).mAnimatingAway = paramView;
  }
  
  void setAnimator(Animator paramAnimator) {
    (ensureAnimationInfo()).mAnimator = paramAnimator;
  }
  
  public void setArguments(Bundle paramBundle) {
    if (this.mIndex < 0 || !isStateSaved()) {
      this.mArguments = paramBundle;
      return;
    } 
    throw new IllegalStateException("Fragment already active and state has been saved");
  }
  
  public void setEnterSharedElementCallback(SharedElementCallback paramSharedElementCallback) {
    (ensureAnimationInfo()).mEnterTransitionCallback = paramSharedElementCallback;
  }
  
  public void setEnterTransition(Object paramObject) {
    AnimationInfo.access$002(ensureAnimationInfo(), paramObject);
  }
  
  public void setExitSharedElementCallback(SharedElementCallback paramSharedElementCallback) {
    (ensureAnimationInfo()).mExitTransitionCallback = paramSharedElementCallback;
  }
  
  public void setExitTransition(Object paramObject) {
    AnimationInfo.access$202(ensureAnimationInfo(), paramObject);
  }
  
  public void setHasOptionsMenu(boolean paramBoolean) {
    if (this.mHasMenu != paramBoolean) {
      this.mHasMenu = paramBoolean;
      if (isAdded() && !isHidden())
        this.mHost.onSupportInvalidateOptionsMenu(); 
    } 
  }
  
  void setHideReplaced(boolean paramBoolean) {
    (ensureAnimationInfo()).mIsHideReplaced = paramBoolean;
  }
  
  final void setIndex(int paramInt, Fragment paramFragment) {
    this.mIndex = paramInt;
    if (paramFragment != null) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(paramFragment.mWho);
      stringBuilder.append(":");
      stringBuilder.append(this.mIndex);
      this.mWho = stringBuilder.toString();
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("android:fragment:");
      stringBuilder.append(this.mIndex);
      this.mWho = stringBuilder.toString();
    } 
  }
  
  public void setInitialSavedState(SavedState paramSavedState) {
    if (this.mIndex < 0) {
      if (paramSavedState != null && paramSavedState.mState != null) {
        Bundle bundle = paramSavedState.mState;
      } else {
        paramSavedState = null;
      } 
      this.mSavedFragmentState = (Bundle)paramSavedState;
      return;
    } 
    throw new IllegalStateException("Fragment already active");
  }
  
  public void setMenuVisibility(boolean paramBoolean) {
    if (this.mMenuVisible != paramBoolean) {
      this.mMenuVisible = paramBoolean;
      if (this.mHasMenu && isAdded() && !isHidden())
        this.mHost.onSupportInvalidateOptionsMenu(); 
    } 
  }
  
  void setNextAnim(int paramInt) {
    if (this.mAnimationInfo == null && paramInt == 0)
      return; 
    (ensureAnimationInfo()).mNextAnim = paramInt;
  }
  
  void setNextTransition(int paramInt1, int paramInt2) {
    if (this.mAnimationInfo == null && paramInt1 == 0 && paramInt2 == 0)
      return; 
    ensureAnimationInfo();
    this.mAnimationInfo.mNextTransition = paramInt1;
    this.mAnimationInfo.mNextTransitionStyle = paramInt2;
  }
  
  void setOnStartEnterTransitionListener(OnStartEnterTransitionListener paramOnStartEnterTransitionListener) {
    ensureAnimationInfo();
    if (paramOnStartEnterTransitionListener == this.mAnimationInfo.mStartEnterTransitionListener)
      return; 
    if (paramOnStartEnterTransitionListener == null || this.mAnimationInfo.mStartEnterTransitionListener == null) {
      if (this.mAnimationInfo.mEnterTransitionPostponed)
        this.mAnimationInfo.mStartEnterTransitionListener = paramOnStartEnterTransitionListener; 
      if (paramOnStartEnterTransitionListener != null)
        paramOnStartEnterTransitionListener.startListening(); 
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Trying to set a replacement startPostponedEnterTransition on ");
    stringBuilder.append(this);
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public void setReenterTransition(Object paramObject) {
    AnimationInfo.access$302(ensureAnimationInfo(), paramObject);
  }
  
  public void setRetainInstance(boolean paramBoolean) {
    this.mRetainInstance = paramBoolean;
  }
  
  public void setReturnTransition(Object paramObject) {
    AnimationInfo.access$102(ensureAnimationInfo(), paramObject);
  }
  
  public void setSharedElementEnterTransition(Object paramObject) {
    AnimationInfo.access$402(ensureAnimationInfo(), paramObject);
  }
  
  public void setSharedElementReturnTransition(Object paramObject) {
    AnimationInfo.access$502(ensureAnimationInfo(), paramObject);
  }
  
  void setStateAfterAnimating(int paramInt) {
    (ensureAnimationInfo()).mStateAfterAnimating = paramInt;
  }
  
  public void setTargetFragment(Fragment paramFragment, int paramInt) {
    FragmentManager fragmentManager2;
    FragmentManager fragmentManager1 = getFragmentManager();
    if (paramFragment != null) {
      fragmentManager2 = paramFragment.getFragmentManager();
    } else {
      fragmentManager2 = null;
    } 
    if (fragmentManager1 == null || fragmentManager2 == null || fragmentManager1 == fragmentManager2) {
      Fragment fragment = paramFragment;
      while (fragment != null) {
        if (fragment != this) {
          fragment = fragment.getTargetFragment();
          continue;
        } 
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("Setting ");
        stringBuilder1.append(paramFragment);
        stringBuilder1.append(" as the target of ");
        stringBuilder1.append(this);
        stringBuilder1.append(" would create a target cycle");
        throw new IllegalArgumentException(stringBuilder1.toString());
      } 
      this.mTarget = paramFragment;
      this.mTargetRequestCode = paramInt;
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(paramFragment);
    stringBuilder.append(" must share the same FragmentManager to be set as a target fragment");
    throw new IllegalArgumentException(stringBuilder.toString());
  }
  
  public void setUserVisibleHint(boolean paramBoolean) {
    if (!this.mUserVisibleHint && paramBoolean && this.mState < 4 && this.mFragmentManager != null && isAdded())
      this.mFragmentManager.performPendingDeferredStart(this); 
    this.mUserVisibleHint = paramBoolean;
    if (this.mState < 4 && !paramBoolean) {
      paramBoolean = true;
    } else {
      paramBoolean = false;
    } 
    this.mDeferStart = paramBoolean;
    if (this.mSavedFragmentState != null)
      this.mSavedUserVisibleHint = Boolean.valueOf(this.mUserVisibleHint); 
  }
  
  public boolean shouldShowRequestPermissionRationale(String paramString) {
    FragmentHostCallback fragmentHostCallback = this.mHost;
    return (fragmentHostCallback != null) ? fragmentHostCallback.onShouldShowRequestPermissionRationale(paramString) : false;
  }
  
  public void startActivity(Intent paramIntent) {
    startActivity(paramIntent, null);
  }
  
  public void startActivity(Intent paramIntent, Bundle paramBundle) {
    FragmentHostCallback fragmentHostCallback = this.mHost;
    if (fragmentHostCallback != null) {
      fragmentHostCallback.onStartActivityFromFragment(this, paramIntent, -1, paramBundle);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" not attached to Activity");
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public void startActivityForResult(Intent paramIntent, int paramInt) {
    startActivityForResult(paramIntent, paramInt, null);
  }
  
  public void startActivityForResult(Intent paramIntent, int paramInt, Bundle paramBundle) {
    FragmentHostCallback fragmentHostCallback = this.mHost;
    if (fragmentHostCallback != null) {
      fragmentHostCallback.onStartActivityFromFragment(this, paramIntent, paramInt, paramBundle);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" not attached to Activity");
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public void startIntentSenderForResult(IntentSender paramIntentSender, int paramInt1, Intent paramIntent, int paramInt2, int paramInt3, int paramInt4, Bundle paramBundle) throws IntentSender.SendIntentException {
    FragmentHostCallback fragmentHostCallback = this.mHost;
    if (fragmentHostCallback != null) {
      fragmentHostCallback.onStartIntentSenderFromFragment(this, paramIntentSender, paramInt1, paramIntent, paramInt2, paramInt3, paramInt4, paramBundle);
      return;
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Fragment ");
    stringBuilder.append(this);
    stringBuilder.append(" not attached to Activity");
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  public void startPostponedEnterTransition() {
    FragmentManagerImpl fragmentManagerImpl = this.mFragmentManager;
    if (fragmentManagerImpl == null || fragmentManagerImpl.mHost == null) {
      (ensureAnimationInfo()).mEnterTransitionPostponed = false;
      return;
    } 
    if (Looper.myLooper() != this.mFragmentManager.mHost.getHandler().getLooper()) {
      this.mFragmentManager.mHost.getHandler().postAtFrontOfQueue(new Runnable() {
            public void run() {
              Fragment.this.callStartTransitionListener();
            }
          });
    } else {
      callStartTransitionListener();
    } 
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(128);
    DebugUtils.buildShortClassTag(this, stringBuilder);
    if (this.mIndex >= 0) {
      stringBuilder.append(" #");
      stringBuilder.append(this.mIndex);
    } 
    if (this.mFragmentId != 0) {
      stringBuilder.append(" id=0x");
      stringBuilder.append(Integer.toHexString(this.mFragmentId));
    } 
    if (this.mTag != null) {
      stringBuilder.append(" ");
      stringBuilder.append(this.mTag);
    } 
    stringBuilder.append('}');
    return stringBuilder.toString();
  }
  
  public void unregisterForContextMenu(View paramView) {
    paramView.setOnCreateContextMenuListener(null);
  }
  
  static class AnimationInfo {
    private Boolean mAllowEnterTransitionOverlap;
    
    private Boolean mAllowReturnTransitionOverlap;
    
    View mAnimatingAway;
    
    Animator mAnimator;
    
    private Object mEnterTransition = null;
    
    SharedElementCallback mEnterTransitionCallback = null;
    
    boolean mEnterTransitionPostponed;
    
    private Object mExitTransition = null;
    
    SharedElementCallback mExitTransitionCallback = null;
    
    boolean mIsHideReplaced;
    
    int mNextAnim;
    
    int mNextTransition;
    
    int mNextTransitionStyle;
    
    private Object mReenterTransition = Fragment.USE_DEFAULT_TRANSITION;
    
    private Object mReturnTransition = Fragment.USE_DEFAULT_TRANSITION;
    
    private Object mSharedElementEnterTransition = null;
    
    private Object mSharedElementReturnTransition = Fragment.USE_DEFAULT_TRANSITION;
    
    Fragment.OnStartEnterTransitionListener mStartEnterTransitionListener;
    
    int mStateAfterAnimating;
  }
  
  public static class InstantiationException extends RuntimeException {
    public InstantiationException(String param1String, Exception param1Exception) {
      super(param1String, param1Exception);
    }
  }
  
  static interface OnStartEnterTransitionListener {
    void onStartEnterTransition();
    
    void startListening();
  }
  
  public static class SavedState implements Parcelable {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
        public Fragment.SavedState createFromParcel(Parcel param2Parcel) {
          return new Fragment.SavedState(param2Parcel, null);
        }
        
        public Fragment.SavedState[] newArray(int param2Int) {
          return new Fragment.SavedState[param2Int];
        }
      };
    
    final Bundle mState;
    
    SavedState(Bundle param1Bundle) {
      this.mState = param1Bundle;
    }
    
    SavedState(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      Bundle bundle = param1Parcel.readBundle();
      this.mState = bundle;
      if (param1ClassLoader != null && bundle != null)
        bundle.setClassLoader(param1ClassLoader); 
    }
    
    public int describeContents() {
      return 0;
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      param1Parcel.writeBundle(this.mState);
    }
  }
  
  static final class null implements Parcelable.Creator<SavedState> {
    public Fragment.SavedState createFromParcel(Parcel param1Parcel) {
      return new Fragment.SavedState(param1Parcel, null);
    }
    
    public Fragment.SavedState[] newArray(int param1Int) {
      return new Fragment.SavedState[param1Int];
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\app\Fragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */