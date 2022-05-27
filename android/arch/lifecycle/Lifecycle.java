package android.arch.lifecycle;

public abstract class Lifecycle {
  public abstract void addObserver(LifecycleObserver paramLifecycleObserver);
  
  public abstract State getCurrentState();
  
  public abstract void removeObserver(LifecycleObserver paramLifecycleObserver);
  
  public enum Event {
    ON_ANY, ON_CREATE, ON_DESTROY, ON_PAUSE, ON_RESUME, ON_START, ON_STOP;
    
    static {
      ON_PAUSE = new Event("ON_PAUSE", 3);
      ON_STOP = new Event("ON_STOP", 4);
      ON_DESTROY = new Event("ON_DESTROY", 5);
      Event event = new Event("ON_ANY", 6);
      ON_ANY = event;
      $VALUES = new Event[] { ON_CREATE, ON_START, ON_RESUME, ON_PAUSE, ON_STOP, ON_DESTROY, event };
    }
  }
  
  public enum State {
    DESTROYED, CREATED, INITIALIZED, RESUMED, STARTED;
    
    static {
      State state = new State("RESUMED", 4);
      RESUMED = state;
      $VALUES = new State[] { DESTROYED, INITIALIZED, CREATED, STARTED, state };
    }
    
    public boolean isAtLeast(State param1State) {
      boolean bool;
      if (compareTo(param1State) >= 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\arch\lifecycle\Lifecycle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */