package android.support.v4.view.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.os.Build;
import android.view.accessibility.AccessibilityManager;
import java.util.List;

public final class AccessibilityManagerCompat {
  @Deprecated
  public static boolean addAccessibilityStateChangeListener(AccessibilityManager paramAccessibilityManager, AccessibilityStateChangeListener paramAccessibilityStateChangeListener) {
    return (paramAccessibilityStateChangeListener == null) ? false : paramAccessibilityManager.addAccessibilityStateChangeListener(new AccessibilityStateChangeListenerWrapper(paramAccessibilityStateChangeListener));
  }
  
  public static boolean addTouchExplorationStateChangeListener(AccessibilityManager paramAccessibilityManager, TouchExplorationStateChangeListener paramTouchExplorationStateChangeListener) {
    return (Build.VERSION.SDK_INT >= 19) ? ((paramTouchExplorationStateChangeListener == null) ? false : paramAccessibilityManager.addTouchExplorationStateChangeListener(new TouchExplorationStateChangeListenerWrapper(paramTouchExplorationStateChangeListener))) : false;
  }
  
  @Deprecated
  public static List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(AccessibilityManager paramAccessibilityManager, int paramInt) {
    return paramAccessibilityManager.getEnabledAccessibilityServiceList(paramInt);
  }
  
  @Deprecated
  public static List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(AccessibilityManager paramAccessibilityManager) {
    return paramAccessibilityManager.getInstalledAccessibilityServiceList();
  }
  
  @Deprecated
  public static boolean isTouchExplorationEnabled(AccessibilityManager paramAccessibilityManager) {
    return paramAccessibilityManager.isTouchExplorationEnabled();
  }
  
  @Deprecated
  public static boolean removeAccessibilityStateChangeListener(AccessibilityManager paramAccessibilityManager, AccessibilityStateChangeListener paramAccessibilityStateChangeListener) {
    return (paramAccessibilityStateChangeListener == null) ? false : paramAccessibilityManager.removeAccessibilityStateChangeListener(new AccessibilityStateChangeListenerWrapper(paramAccessibilityStateChangeListener));
  }
  
  public static boolean removeTouchExplorationStateChangeListener(AccessibilityManager paramAccessibilityManager, TouchExplorationStateChangeListener paramTouchExplorationStateChangeListener) {
    return (Build.VERSION.SDK_INT >= 19) ? ((paramTouchExplorationStateChangeListener == null) ? false : paramAccessibilityManager.removeTouchExplorationStateChangeListener(new TouchExplorationStateChangeListenerWrapper(paramTouchExplorationStateChangeListener))) : false;
  }
  
  @Deprecated
  public static interface AccessibilityStateChangeListener {
    @Deprecated
    void onAccessibilityStateChanged(boolean param1Boolean);
  }
  
  @Deprecated
  public static abstract class AccessibilityStateChangeListenerCompat implements AccessibilityStateChangeListener {}
  
  private static class AccessibilityStateChangeListenerWrapper implements AccessibilityManager.AccessibilityStateChangeListener {
    AccessibilityManagerCompat.AccessibilityStateChangeListener mListener;
    
    AccessibilityStateChangeListenerWrapper(AccessibilityManagerCompat.AccessibilityStateChangeListener param1AccessibilityStateChangeListener) {
      this.mListener = param1AccessibilityStateChangeListener;
    }
    
    public boolean equals(Object param1Object) {
      if (this == param1Object)
        return true; 
      if (param1Object == null || getClass() != param1Object.getClass())
        return false; 
      param1Object = param1Object;
      return this.mListener.equals(((AccessibilityStateChangeListenerWrapper)param1Object).mListener);
    }
    
    public int hashCode() {
      return this.mListener.hashCode();
    }
    
    public void onAccessibilityStateChanged(boolean param1Boolean) {
      this.mListener.onAccessibilityStateChanged(param1Boolean);
    }
  }
  
  public static interface TouchExplorationStateChangeListener {
    void onTouchExplorationStateChanged(boolean param1Boolean);
  }
  
  private static class TouchExplorationStateChangeListenerWrapper implements AccessibilityManager.TouchExplorationStateChangeListener {
    final AccessibilityManagerCompat.TouchExplorationStateChangeListener mListener;
    
    TouchExplorationStateChangeListenerWrapper(AccessibilityManagerCompat.TouchExplorationStateChangeListener param1TouchExplorationStateChangeListener) {
      this.mListener = param1TouchExplorationStateChangeListener;
    }
    
    public boolean equals(Object param1Object) {
      if (this == param1Object)
        return true; 
      if (param1Object == null || getClass() != param1Object.getClass())
        return false; 
      param1Object = param1Object;
      return this.mListener.equals(((TouchExplorationStateChangeListenerWrapper)param1Object).mListener);
    }
    
    public int hashCode() {
      return this.mListener.hashCode();
    }
    
    public void onTouchExplorationStateChanged(boolean param1Boolean) {
      this.mListener.onTouchExplorationStateChanged(param1Boolean);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\view\accessibility\AccessibilityManagerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */