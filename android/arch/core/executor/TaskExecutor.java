package android.arch.core.executor;

public abstract class TaskExecutor {
  public abstract void executeOnDiskIO(Runnable paramRunnable);
  
  public void executeOnMainThread(Runnable paramRunnable) {
    if (isMainThread()) {
      paramRunnable.run();
    } else {
      postToMainThread(paramRunnable);
    } 
  }
  
  public abstract boolean isMainThread();
  
  public abstract void postToMainThread(Runnable paramRunnable);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\core\executor\TaskExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */