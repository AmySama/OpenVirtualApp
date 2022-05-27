package android.arch.lifecycle;

import android.arch.core.util.Function;

public class Transformations {
  public static <X, Y> LiveData<Y> map(LiveData<X> paramLiveData, final Function<X, Y> func) {
    final MediatorLiveData<Y> result = new MediatorLiveData();
    mediatorLiveData.addSource(paramLiveData, new Observer<X>() {
          public void onChanged(X param1X) {
            result.setValue(func.apply(param1X));
          }
        });
    return mediatorLiveData;
  }
  
  public static <X, Y> LiveData<Y> switchMap(LiveData<X> paramLiveData, final Function<X, LiveData<Y>> func) {
    final MediatorLiveData<Y> result = new MediatorLiveData();
    mediatorLiveData.addSource(paramLiveData, new Observer<X>() {
          LiveData<Y> mSource;
          
          public void onChanged(X param1X) {
            LiveData<Y> liveData1 = (LiveData)func.apply(param1X);
            LiveData<Y> liveData2 = this.mSource;
            if (liveData2 == liveData1)
              return; 
            if (liveData2 != null)
              result.removeSource(liveData2); 
            this.mSource = liveData1;
            if (liveData1 != null)
              result.addSource(liveData1, new Observer() {
                    public void onChanged(Y param2Y) {
                      result.setValue(param2Y);
                    }
                  }); 
          }
        });
    return mediatorLiveData;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\lifecycle\Transformations.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */