package android.support.v4.app;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelStore;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.content.Loader;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.SparseArrayCompat;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;

class LoaderManagerImpl extends LoaderManager {
  static boolean DEBUG = false;
  
  static final String TAG = "LoaderManager";
  
  private boolean mCreatingLoader;
  
  private final LifecycleOwner mLifecycleOwner;
  
  private final LoaderViewModel mLoaderViewModel;
  
  LoaderManagerImpl(LifecycleOwner paramLifecycleOwner, ViewModelStore paramViewModelStore) {
    this.mLifecycleOwner = paramLifecycleOwner;
    this.mLoaderViewModel = LoaderViewModel.getInstance(paramViewModelStore);
  }
  
  private <D> Loader<D> createAndInstallLoader(int paramInt, Bundle paramBundle, LoaderManager.LoaderCallbacks<D> paramLoaderCallbacks, Loader<D> paramLoader) {
    try {
      this.mCreatingLoader = true;
      Loader<D> loader = paramLoaderCallbacks.onCreateLoader(paramInt, paramBundle);
      if (!loader.getClass().isMemberClass() || Modifier.isStatic(loader.getClass().getModifiers())) {
        LoaderInfo<D> loaderInfo = new LoaderInfo();
        this(paramInt, paramBundle, loader, paramLoader);
        if (DEBUG) {
          StringBuilder stringBuilder1 = new StringBuilder();
          this();
          stringBuilder1.append("  Created new loader ");
          stringBuilder1.append(loaderInfo);
          Log.v("LoaderManager", stringBuilder1.toString());
        } 
        this.mLoaderViewModel.putLoader(paramInt, loaderInfo);
        return loaderInfo.setCallback(this.mLifecycleOwner, paramLoaderCallbacks);
      } 
      IllegalArgumentException illegalArgumentException = new IllegalArgumentException();
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append("Object returned from onCreateLoader must not be a non-static inner member class: ");
      stringBuilder.append(loader);
      this(stringBuilder.toString());
      throw illegalArgumentException;
    } finally {
      this.mCreatingLoader = false;
    } 
  }
  
  public void destroyLoader(int paramInt) {
    if (!this.mCreatingLoader) {
      if (Looper.getMainLooper() == Looper.myLooper()) {
        if (DEBUG) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("destroyLoader in ");
          stringBuilder.append(this);
          stringBuilder.append(" of ");
          stringBuilder.append(paramInt);
          Log.v("LoaderManager", stringBuilder.toString());
        } 
        LoaderInfo<?> loaderInfo = this.mLoaderViewModel.getLoader(paramInt);
        if (loaderInfo != null) {
          loaderInfo.destroy(true);
          this.mLoaderViewModel.removeLoader(paramInt);
        } 
        return;
      } 
      throw new IllegalStateException("destroyLoader must be called on the main thread");
    } 
    throw new IllegalStateException("Called while creating a loader");
  }
  
  public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString) {
    this.mLoaderViewModel.dump(paramString, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
  }
  
  public <D> Loader<D> getLoader(int paramInt) {
    if (!this.mCreatingLoader) {
      LoaderInfo<?> loaderInfo = this.mLoaderViewModel.getLoader(paramInt);
      if (loaderInfo != null) {
        Loader<?> loader = loaderInfo.getLoader();
      } else {
        loaderInfo = null;
      } 
      return (Loader)loaderInfo;
    } 
    throw new IllegalStateException("Called while creating a loader");
  }
  
  public boolean hasRunningLoaders() {
    return this.mLoaderViewModel.hasRunningLoaders();
  }
  
  public <D> Loader<D> initLoader(int paramInt, Bundle paramBundle, LoaderManager.LoaderCallbacks<D> paramLoaderCallbacks) {
    if (!this.mCreatingLoader) {
      if (Looper.getMainLooper() == Looper.myLooper()) {
        LoaderInfo<?> loaderInfo = this.mLoaderViewModel.getLoader(paramInt);
        if (DEBUG) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("initLoader in ");
          stringBuilder.append(this);
          stringBuilder.append(": args=");
          stringBuilder.append(paramBundle);
          Log.v("LoaderManager", stringBuilder.toString());
        } 
        if (loaderInfo == null)
          return createAndInstallLoader(paramInt, paramBundle, paramLoaderCallbacks, null); 
        if (DEBUG) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("  Re-using existing loader ");
          stringBuilder.append(loaderInfo);
          Log.v("LoaderManager", stringBuilder.toString());
        } 
        return (Loader)loaderInfo.setCallback(this.mLifecycleOwner, paramLoaderCallbacks);
      } 
      throw new IllegalStateException("initLoader must be called on the main thread");
    } 
    throw new IllegalStateException("Called while creating a loader");
  }
  
  void markForRedelivery() {
    this.mLoaderViewModel.markForRedelivery();
  }
  
  public <D> Loader<D> restartLoader(int paramInt, Bundle paramBundle, LoaderManager.LoaderCallbacks<D> paramLoaderCallbacks) {
    if (!this.mCreatingLoader) {
      if (Looper.getMainLooper() == Looper.myLooper()) {
        if (DEBUG) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("restartLoader in ");
          stringBuilder.append(this);
          stringBuilder.append(": args=");
          stringBuilder.append(paramBundle);
          Log.v("LoaderManager", stringBuilder.toString());
        } 
        LoaderInfo<?> loaderInfo = this.mLoaderViewModel.getLoader(paramInt);
        Loader<?> loader = null;
        if (loaderInfo != null)
          loader = loaderInfo.destroy(false); 
        return createAndInstallLoader(paramInt, paramBundle, paramLoaderCallbacks, (Loader)loader);
      } 
      throw new IllegalStateException("restartLoader must be called on the main thread");
    } 
    throw new IllegalStateException("Called while creating a loader");
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder(128);
    stringBuilder.append("LoaderManager{");
    stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
    stringBuilder.append(" in ");
    DebugUtils.buildShortClassTag(this.mLifecycleOwner, stringBuilder);
    stringBuilder.append("}}");
    return stringBuilder.toString();
  }
  
  public static class LoaderInfo<D> extends MutableLiveData<D> implements Loader.OnLoadCompleteListener<D> {
    private final Bundle mArgs;
    
    private final int mId;
    
    private LifecycleOwner mLifecycleOwner;
    
    private final Loader<D> mLoader;
    
    private LoaderManagerImpl.LoaderObserver<D> mObserver;
    
    private Loader<D> mPriorLoader;
    
    LoaderInfo(int param1Int, Bundle param1Bundle, Loader<D> param1Loader1, Loader<D> param1Loader2) {
      this.mId = param1Int;
      this.mArgs = param1Bundle;
      this.mLoader = param1Loader1;
      this.mPriorLoader = param1Loader2;
      param1Loader1.registerListener(param1Int, this);
    }
    
    Loader<D> destroy(boolean param1Boolean) {
      if (LoaderManagerImpl.DEBUG) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  Destroying: ");
        stringBuilder.append(this);
        Log.v("LoaderManager", stringBuilder.toString());
      } 
      this.mLoader.cancelLoad();
      this.mLoader.abandon();
      LoaderManagerImpl.LoaderObserver<D> loaderObserver = this.mObserver;
      if (loaderObserver != null) {
        removeObserver(loaderObserver);
        if (param1Boolean)
          loaderObserver.reset(); 
      } 
      this.mLoader.unregisterListener(this);
      if ((loaderObserver != null && !loaderObserver.hasDeliveredData()) || param1Boolean) {
        this.mLoader.reset();
        return this.mPriorLoader;
      } 
      return this.mLoader;
    }
    
    public void dump(String param1String, FileDescriptor param1FileDescriptor, PrintWriter param1PrintWriter, String[] param1ArrayOfString) {
      param1PrintWriter.print(param1String);
      param1PrintWriter.print("mId=");
      param1PrintWriter.print(this.mId);
      param1PrintWriter.print(" mArgs=");
      param1PrintWriter.println(this.mArgs);
      param1PrintWriter.print(param1String);
      param1PrintWriter.print("mLoader=");
      param1PrintWriter.println(this.mLoader);
      Loader<D> loader = this.mLoader;
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(param1String);
      stringBuilder.append("  ");
      loader.dump(stringBuilder.toString(), param1FileDescriptor, param1PrintWriter, param1ArrayOfString);
      if (this.mObserver != null) {
        param1PrintWriter.print(param1String);
        param1PrintWriter.print("mCallbacks=");
        param1PrintWriter.println(this.mObserver);
        LoaderManagerImpl.LoaderObserver<D> loaderObserver = this.mObserver;
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(param1String);
        stringBuilder1.append("  ");
        loaderObserver.dump(stringBuilder1.toString(), param1PrintWriter);
      } 
      param1PrintWriter.print(param1String);
      param1PrintWriter.print("mData=");
      param1PrintWriter.println(getLoader().dataToString(getValue()));
      param1PrintWriter.print(param1String);
      param1PrintWriter.print("mStarted=");
      param1PrintWriter.println(hasActiveObservers());
    }
    
    Loader<D> getLoader() {
      return this.mLoader;
    }
    
    boolean isCallbackWaitingForData() {
      boolean bool = hasActiveObservers();
      boolean bool1 = false;
      if (!bool)
        return false; 
      LoaderManagerImpl.LoaderObserver<D> loaderObserver = this.mObserver;
      bool = bool1;
      if (loaderObserver != null) {
        bool = bool1;
        if (!loaderObserver.hasDeliveredData())
          bool = true; 
      } 
      return bool;
    }
    
    void markForRedelivery() {
      LifecycleOwner lifecycleOwner = this.mLifecycleOwner;
      LoaderManagerImpl.LoaderObserver<D> loaderObserver = this.mObserver;
      if (lifecycleOwner != null && loaderObserver != null) {
        super.removeObserver(loaderObserver);
        observe(lifecycleOwner, loaderObserver);
      } 
    }
    
    protected void onActive() {
      if (LoaderManagerImpl.DEBUG) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  Starting: ");
        stringBuilder.append(this);
        Log.v("LoaderManager", stringBuilder.toString());
      } 
      this.mLoader.startLoading();
    }
    
    protected void onInactive() {
      if (LoaderManagerImpl.DEBUG) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  Stopping: ");
        stringBuilder.append(this);
        Log.v("LoaderManager", stringBuilder.toString());
      } 
      this.mLoader.stopLoading();
    }
    
    public void onLoadComplete(Loader<D> param1Loader, D param1D) {
      if (LoaderManagerImpl.DEBUG) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onLoadComplete: ");
        stringBuilder.append(this);
        Log.v("LoaderManager", stringBuilder.toString());
      } 
      if (Looper.myLooper() == Looper.getMainLooper()) {
        setValue(param1D);
      } else {
        if (LoaderManagerImpl.DEBUG)
          Log.w("LoaderManager", "onLoadComplete was incorrectly called on a background thread"); 
        postValue(param1D);
      } 
    }
    
    public void removeObserver(Observer<D> param1Observer) {
      super.removeObserver(param1Observer);
      this.mLifecycleOwner = null;
      this.mObserver = null;
    }
    
    Loader<D> setCallback(LifecycleOwner param1LifecycleOwner, LoaderManager.LoaderCallbacks<D> param1LoaderCallbacks) {
      LoaderManagerImpl.LoaderObserver<D> loaderObserver1 = new LoaderManagerImpl.LoaderObserver<D>(this.mLoader, param1LoaderCallbacks);
      observe(param1LifecycleOwner, loaderObserver1);
      LoaderManagerImpl.LoaderObserver<D> loaderObserver2 = this.mObserver;
      if (loaderObserver2 != null)
        removeObserver(loaderObserver2); 
      this.mLifecycleOwner = param1LifecycleOwner;
      this.mObserver = loaderObserver1;
      return this.mLoader;
    }
    
    public void setValue(D param1D) {
      super.setValue(param1D);
      Loader<D> loader = this.mPriorLoader;
      if (loader != null) {
        loader.reset();
        this.mPriorLoader = null;
      } 
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder(64);
      stringBuilder.append("LoaderInfo{");
      stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
      stringBuilder.append(" #");
      stringBuilder.append(this.mId);
      stringBuilder.append(" : ");
      DebugUtils.buildShortClassTag(this.mLoader, stringBuilder);
      stringBuilder.append("}}");
      return stringBuilder.toString();
    }
  }
  
  static class LoaderObserver<D> implements Observer<D> {
    private final LoaderManager.LoaderCallbacks<D> mCallback;
    
    private boolean mDeliveredData = false;
    
    private final Loader<D> mLoader;
    
    LoaderObserver(Loader<D> param1Loader, LoaderManager.LoaderCallbacks<D> param1LoaderCallbacks) {
      this.mLoader = param1Loader;
      this.mCallback = param1LoaderCallbacks;
    }
    
    public void dump(String param1String, PrintWriter param1PrintWriter) {
      param1PrintWriter.print(param1String);
      param1PrintWriter.print("mDeliveredData=");
      param1PrintWriter.println(this.mDeliveredData);
    }
    
    boolean hasDeliveredData() {
      return this.mDeliveredData;
    }
    
    public void onChanged(D param1D) {
      if (LoaderManagerImpl.DEBUG) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  onLoadFinished in ");
        stringBuilder.append(this.mLoader);
        stringBuilder.append(": ");
        stringBuilder.append(this.mLoader.dataToString(param1D));
        Log.v("LoaderManager", stringBuilder.toString());
      } 
      this.mCallback.onLoadFinished(this.mLoader, param1D);
      this.mDeliveredData = true;
    }
    
    void reset() {
      if (this.mDeliveredData) {
        if (LoaderManagerImpl.DEBUG) {
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("  Resetting: ");
          stringBuilder.append(this.mLoader);
          Log.v("LoaderManager", stringBuilder.toString());
        } 
        this.mCallback.onLoaderReset(this.mLoader);
      } 
    }
    
    public String toString() {
      return this.mCallback.toString();
    }
  }
  
  static class LoaderViewModel extends ViewModel {
    private static final ViewModelProvider.Factory FACTORY = new ViewModelProvider.Factory() {
        public <T extends ViewModel> T create(Class<T> param2Class) {
          return (T)new LoaderManagerImpl.LoaderViewModel();
        }
      };
    
    private SparseArrayCompat<LoaderManagerImpl.LoaderInfo> mLoaders = new SparseArrayCompat();
    
    static LoaderViewModel getInstance(ViewModelStore param1ViewModelStore) {
      return (LoaderViewModel)(new ViewModelProvider(param1ViewModelStore, FACTORY)).get(LoaderViewModel.class);
    }
    
    public void dump(String param1String, FileDescriptor param1FileDescriptor, PrintWriter param1PrintWriter, String[] param1ArrayOfString) {
      if (this.mLoaders.size() > 0) {
        param1PrintWriter.print(param1String);
        param1PrintWriter.println("Loaders:");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(param1String);
        stringBuilder.append("    ");
        String str = stringBuilder.toString();
        for (byte b = 0; b < this.mLoaders.size(); b++) {
          LoaderManagerImpl.LoaderInfo loaderInfo = (LoaderManagerImpl.LoaderInfo)this.mLoaders.valueAt(b);
          param1PrintWriter.print(param1String);
          param1PrintWriter.print("  #");
          param1PrintWriter.print(this.mLoaders.keyAt(b));
          param1PrintWriter.print(": ");
          param1PrintWriter.println(loaderInfo.toString());
          loaderInfo.dump(str, param1FileDescriptor, param1PrintWriter, param1ArrayOfString);
        } 
      } 
    }
    
    <D> LoaderManagerImpl.LoaderInfo<D> getLoader(int param1Int) {
      return (LoaderManagerImpl.LoaderInfo<D>)this.mLoaders.get(param1Int);
    }
    
    boolean hasRunningLoaders() {
      int i = this.mLoaders.size();
      for (byte b = 0; b < i; b++) {
        if (((LoaderManagerImpl.LoaderInfo)this.mLoaders.valueAt(b)).isCallbackWaitingForData())
          return true; 
      } 
      return false;
    }
    
    void markForRedelivery() {
      int i = this.mLoaders.size();
      for (byte b = 0; b < i; b++)
        ((LoaderManagerImpl.LoaderInfo)this.mLoaders.valueAt(b)).markForRedelivery(); 
    }
    
    protected void onCleared() {
      super.onCleared();
      int i = this.mLoaders.size();
      for (byte b = 0; b < i; b++)
        ((LoaderManagerImpl.LoaderInfo)this.mLoaders.valueAt(b)).destroy(true); 
      this.mLoaders.clear();
    }
    
    void putLoader(int param1Int, LoaderManagerImpl.LoaderInfo param1LoaderInfo) {
      this.mLoaders.put(param1Int, param1LoaderInfo);
    }
    
    void removeLoader(int param1Int) {
      this.mLoaders.remove(param1Int);
    }
  }
  
  static final class null implements ViewModelProvider.Factory {
    public <T extends ViewModel> T create(Class<T> param1Class) {
      return (T)new LoaderManagerImpl.LoaderViewModel();
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\app\LoaderManagerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */