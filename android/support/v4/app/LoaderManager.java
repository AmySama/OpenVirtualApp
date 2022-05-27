package android.support.v4.app;

import android.os.Bundle;
import android.support.v4.content.Loader;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public abstract class LoaderManager {
  public static void enableDebugLogging(boolean paramBoolean) {
    LoaderManagerImpl.DEBUG = paramBoolean;
  }
  
  public abstract void destroyLoader(int paramInt);
  
  public abstract void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString);
  
  public abstract <D> Loader<D> getLoader(int paramInt);
  
  public boolean hasRunningLoaders() {
    return false;
  }
  
  public abstract <D> Loader<D> initLoader(int paramInt, Bundle paramBundle, LoaderCallbacks<D> paramLoaderCallbacks);
  
  public abstract <D> Loader<D> restartLoader(int paramInt, Bundle paramBundle, LoaderCallbacks<D> paramLoaderCallbacks);
  
  public static interface LoaderCallbacks<D> {
    Loader<D> onCreateLoader(int param1Int, Bundle param1Bundle);
    
    void onLoadFinished(Loader<D> param1Loader, D param1D);
    
    void onLoaderReset(Loader<D> param1Loader);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\app\LoaderManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */