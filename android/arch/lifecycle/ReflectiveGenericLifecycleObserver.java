package android.arch.lifecycle;

class ReflectiveGenericLifecycleObserver implements GenericLifecycleObserver {
  private final ClassesInfoCache.CallbackInfo mInfo;
  
  private final Object mWrapped;
  
  ReflectiveGenericLifecycleObserver(Object paramObject) {
    this.mWrapped = paramObject;
    this.mInfo = ClassesInfoCache.sInstance.getInfo(this.mWrapped.getClass());
  }
  
  public void onStateChanged(LifecycleOwner paramLifecycleOwner, Lifecycle.Event paramEvent) {
    this.mInfo.invokeCallbacks(paramLifecycleOwner, paramEvent, this.mWrapped);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\lifecycle\ReflectiveGenericLifecycleObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */