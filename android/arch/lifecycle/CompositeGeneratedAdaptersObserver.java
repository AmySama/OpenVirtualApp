package android.arch.lifecycle;

public class CompositeGeneratedAdaptersObserver implements GenericLifecycleObserver {
  private final GeneratedAdapter[] mGeneratedAdapters;
  
  CompositeGeneratedAdaptersObserver(GeneratedAdapter[] paramArrayOfGeneratedAdapter) {
    this.mGeneratedAdapters = paramArrayOfGeneratedAdapter;
  }
  
  public void onStateChanged(LifecycleOwner paramLifecycleOwner, Lifecycle.Event paramEvent) {
    MethodCallsLogger methodCallsLogger = new MethodCallsLogger();
    GeneratedAdapter[] arrayOfGeneratedAdapter = this.mGeneratedAdapters;
    int i = arrayOfGeneratedAdapter.length;
    boolean bool = false;
    byte b;
    for (b = 0; b < i; b++)
      arrayOfGeneratedAdapter[b].callMethods(paramLifecycleOwner, paramEvent, false, methodCallsLogger); 
    arrayOfGeneratedAdapter = this.mGeneratedAdapters;
    i = arrayOfGeneratedAdapter.length;
    for (b = bool; b < i; b++)
      arrayOfGeneratedAdapter[b].callMethods(paramLifecycleOwner, paramEvent, true, methodCallsLogger); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\lifecycle\CompositeGeneratedAdaptersObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */