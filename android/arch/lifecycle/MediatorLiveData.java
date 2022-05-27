package android.arch.lifecycle;

import android.arch.core.internal.SafeIterableMap;
import java.util.Iterator;
import java.util.Map;

public class MediatorLiveData<T> extends MutableLiveData<T> {
  private SafeIterableMap<LiveData<?>, Source<?>> mSources = new SafeIterableMap();
  
  public <S> void addSource(LiveData<S> paramLiveData, Observer<S> paramObserver) {
    Source<S> source1 = new Source<S>(paramLiveData, paramObserver);
    Source source = (Source)this.mSources.putIfAbsent(paramLiveData, source1);
    if (source == null || source.mObserver == paramObserver) {
      if (source != null)
        return; 
      if (hasActiveObservers())
        source1.plug(); 
      return;
    } 
    throw new IllegalArgumentException("This source was already added with the different observer");
  }
  
  protected void onActive() {
    Iterator<Map.Entry> iterator = this.mSources.iterator();
    while (iterator.hasNext())
      ((Source)((Map.Entry)iterator.next()).getValue()).plug(); 
  }
  
  protected void onInactive() {
    Iterator<Map.Entry> iterator = this.mSources.iterator();
    while (iterator.hasNext())
      ((Source)((Map.Entry)iterator.next()).getValue()).unplug(); 
  }
  
  public <S> void removeSource(LiveData<S> paramLiveData) {
    Source source = (Source)this.mSources.remove(paramLiveData);
    if (source != null)
      source.unplug(); 
  }
  
  private static class Source<V> implements Observer<V> {
    final LiveData<V> mLiveData;
    
    final Observer<V> mObserver;
    
    int mVersion = -1;
    
    Source(LiveData<V> param1LiveData, Observer<V> param1Observer) {
      this.mLiveData = param1LiveData;
      this.mObserver = param1Observer;
    }
    
    public void onChanged(V param1V) {
      if (this.mVersion != this.mLiveData.getVersion()) {
        this.mVersion = this.mLiveData.getVersion();
        this.mObserver.onChanged(param1V);
      } 
    }
    
    void plug() {
      this.mLiveData.observeForever(this);
    }
    
    void unplug() {
      this.mLiveData.removeObserver(this);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\lifecycle\MediatorLiveData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */