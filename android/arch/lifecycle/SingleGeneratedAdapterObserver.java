package android.arch.lifecycle;

public class SingleGeneratedAdapterObserver implements GenericLifecycleObserver {
  private final GeneratedAdapter mGeneratedAdapter;
  
  SingleGeneratedAdapterObserver(GeneratedAdapter paramGeneratedAdapter) {
    this.mGeneratedAdapter = paramGeneratedAdapter;
  }
  
  public void onStateChanged(LifecycleOwner paramLifecycleOwner, Lifecycle.Event paramEvent) {
    this.mGeneratedAdapter.callMethods(paramLifecycleOwner, paramEvent, false, null);
    this.mGeneratedAdapter.callMethods(paramLifecycleOwner, paramEvent, true, null);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\lifecycle\SingleGeneratedAdapterObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */