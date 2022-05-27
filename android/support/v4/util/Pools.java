package android.support.v4.util;

public final class Pools {
  public static interface Pool<T> {
    T acquire();
    
    boolean release(T param1T);
  }
  
  public static class SimplePool<T> implements Pool<T> {
    private final Object[] mPool;
    
    private int mPoolSize;
    
    public SimplePool(int param1Int) {
      if (param1Int > 0) {
        this.mPool = new Object[param1Int];
        return;
      } 
      throw new IllegalArgumentException("The max pool size must be > 0");
    }
    
    private boolean isInPool(T param1T) {
      for (byte b = 0; b < this.mPoolSize; b++) {
        if (this.mPool[b] == param1T)
          return true; 
      } 
      return false;
    }
    
    public T acquire() {
      int i = this.mPoolSize;
      if (i > 0) {
        int j = i - 1;
        Object[] arrayOfObject = this.mPool;
        Object object = arrayOfObject[j];
        arrayOfObject[j] = null;
        this.mPoolSize = i - 1;
        return (T)object;
      } 
      return null;
    }
    
    public boolean release(T param1T) {
      if (!isInPool(param1T)) {
        int i = this.mPoolSize;
        Object[] arrayOfObject = this.mPool;
        if (i < arrayOfObject.length) {
          arrayOfObject[i] = param1T;
          this.mPoolSize = i + 1;
          return true;
        } 
        return false;
      } 
      throw new IllegalStateException("Already in the pool!");
    }
  }
  
  public static class SynchronizedPool<T> extends SimplePool<T> {
    private final Object mLock = new Object();
    
    public SynchronizedPool(int param1Int) {
      super(param1Int);
    }
    
    public T acquire() {
      synchronized (this.mLock) {
        return super.acquire();
      } 
    }
    
    public boolean release(T param1T) {
      synchronized (this.mLock) {
        return super.release(param1T);
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v\\util\Pools.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */