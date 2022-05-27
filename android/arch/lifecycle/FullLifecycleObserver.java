package android.arch.lifecycle;

interface FullLifecycleObserver extends LifecycleObserver {
  void onCreate(LifecycleOwner paramLifecycleOwner);
  
  void onDestroy(LifecycleOwner paramLifecycleOwner);
  
  void onPause(LifecycleOwner paramLifecycleOwner);
  
  void onResume(LifecycleOwner paramLifecycleOwner);
  
  void onStart(LifecycleOwner paramLifecycleOwner);
  
  void onStop(LifecycleOwner paramLifecycleOwner);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\lifecycle\FullLifecycleObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */