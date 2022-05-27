package android.support.v4.view;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;

public class AccessibilityDelegateCompat {
  private static final View.AccessibilityDelegate DEFAULT_DELEGATE = new View.AccessibilityDelegate();
  
  private static final AccessibilityDelegateBaseImpl IMPL;
  
  final View.AccessibilityDelegate mBridge = IMPL.newAccessibilityDelegateBridge(this);
  
  public boolean dispatchPopulateAccessibilityEvent(View paramView, AccessibilityEvent paramAccessibilityEvent) {
    return DEFAULT_DELEGATE.dispatchPopulateAccessibilityEvent(paramView, paramAccessibilityEvent);
  }
  
  public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View paramView) {
    return IMPL.getAccessibilityNodeProvider(DEFAULT_DELEGATE, paramView);
  }
  
  View.AccessibilityDelegate getBridge() {
    return this.mBridge;
  }
  
  public void onInitializeAccessibilityEvent(View paramView, AccessibilityEvent paramAccessibilityEvent) {
    DEFAULT_DELEGATE.onInitializeAccessibilityEvent(paramView, paramAccessibilityEvent);
  }
  
  public void onInitializeAccessibilityNodeInfo(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat) {
    DEFAULT_DELEGATE.onInitializeAccessibilityNodeInfo(paramView, paramAccessibilityNodeInfoCompat.unwrap());
  }
  
  public void onPopulateAccessibilityEvent(View paramView, AccessibilityEvent paramAccessibilityEvent) {
    DEFAULT_DELEGATE.onPopulateAccessibilityEvent(paramView, paramAccessibilityEvent);
  }
  
  public boolean onRequestSendAccessibilityEvent(ViewGroup paramViewGroup, View paramView, AccessibilityEvent paramAccessibilityEvent) {
    return DEFAULT_DELEGATE.onRequestSendAccessibilityEvent(paramViewGroup, paramView, paramAccessibilityEvent);
  }
  
  public boolean performAccessibilityAction(View paramView, int paramInt, Bundle paramBundle) {
    return IMPL.performAccessibilityAction(DEFAULT_DELEGATE, paramView, paramInt, paramBundle);
  }
  
  public void sendAccessibilityEvent(View paramView, int paramInt) {
    DEFAULT_DELEGATE.sendAccessibilityEvent(paramView, paramInt);
  }
  
  public void sendAccessibilityEventUnchecked(View paramView, AccessibilityEvent paramAccessibilityEvent) {
    DEFAULT_DELEGATE.sendAccessibilityEventUnchecked(paramView, paramAccessibilityEvent);
  }
  
  static {
    if (Build.VERSION.SDK_INT >= 16) {
      IMPL = new AccessibilityDelegateApi16Impl();
    } else {
      IMPL = new AccessibilityDelegateBaseImpl();
    } 
  }
  
  static class AccessibilityDelegateApi16Impl extends AccessibilityDelegateBaseImpl {
    public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View.AccessibilityDelegate param1AccessibilityDelegate, View param1View) {
      AccessibilityNodeProvider accessibilityNodeProvider = param1AccessibilityDelegate.getAccessibilityNodeProvider(param1View);
      return (accessibilityNodeProvider != null) ? new AccessibilityNodeProviderCompat(accessibilityNodeProvider) : null;
    }
    
    public View.AccessibilityDelegate newAccessibilityDelegateBridge(final AccessibilityDelegateCompat compat) {
      return new View.AccessibilityDelegate() {
          public boolean dispatchPopulateAccessibilityEvent(View param2View, AccessibilityEvent param2AccessibilityEvent) {
            return compat.dispatchPopulateAccessibilityEvent(param2View, param2AccessibilityEvent);
          }
          
          public AccessibilityNodeProvider getAccessibilityNodeProvider(View param2View) {
            AccessibilityNodeProviderCompat accessibilityNodeProviderCompat = compat.getAccessibilityNodeProvider(param2View);
            if (accessibilityNodeProviderCompat != null) {
              AccessibilityNodeProvider accessibilityNodeProvider = (AccessibilityNodeProvider)accessibilityNodeProviderCompat.getProvider();
            } else {
              accessibilityNodeProviderCompat = null;
            } 
            return (AccessibilityNodeProvider)accessibilityNodeProviderCompat;
          }
          
          public void onInitializeAccessibilityEvent(View param2View, AccessibilityEvent param2AccessibilityEvent) {
            compat.onInitializeAccessibilityEvent(param2View, param2AccessibilityEvent);
          }
          
          public void onInitializeAccessibilityNodeInfo(View param2View, AccessibilityNodeInfo param2AccessibilityNodeInfo) {
            compat.onInitializeAccessibilityNodeInfo(param2View, AccessibilityNodeInfoCompat.wrap(param2AccessibilityNodeInfo));
          }
          
          public void onPopulateAccessibilityEvent(View param2View, AccessibilityEvent param2AccessibilityEvent) {
            compat.onPopulateAccessibilityEvent(param2View, param2AccessibilityEvent);
          }
          
          public boolean onRequestSendAccessibilityEvent(ViewGroup param2ViewGroup, View param2View, AccessibilityEvent param2AccessibilityEvent) {
            return compat.onRequestSendAccessibilityEvent(param2ViewGroup, param2View, param2AccessibilityEvent);
          }
          
          public boolean performAccessibilityAction(View param2View, int param2Int, Bundle param2Bundle) {
            return compat.performAccessibilityAction(param2View, param2Int, param2Bundle);
          }
          
          public void sendAccessibilityEvent(View param2View, int param2Int) {
            compat.sendAccessibilityEvent(param2View, param2Int);
          }
          
          public void sendAccessibilityEventUnchecked(View param2View, AccessibilityEvent param2AccessibilityEvent) {
            compat.sendAccessibilityEventUnchecked(param2View, param2AccessibilityEvent);
          }
        };
    }
    
    public boolean performAccessibilityAction(View.AccessibilityDelegate param1AccessibilityDelegate, View param1View, int param1Int, Bundle param1Bundle) {
      return param1AccessibilityDelegate.performAccessibilityAction(param1View, param1Int, param1Bundle);
    }
  }
  
  class null extends View.AccessibilityDelegate {
    public boolean dispatchPopulateAccessibilityEvent(View param1View, AccessibilityEvent param1AccessibilityEvent) {
      return compat.dispatchPopulateAccessibilityEvent(param1View, param1AccessibilityEvent);
    }
    
    public AccessibilityNodeProvider getAccessibilityNodeProvider(View param1View) {
      AccessibilityNodeProviderCompat accessibilityNodeProviderCompat = compat.getAccessibilityNodeProvider(param1View);
      if (accessibilityNodeProviderCompat != null) {
        AccessibilityNodeProvider accessibilityNodeProvider = (AccessibilityNodeProvider)accessibilityNodeProviderCompat.getProvider();
      } else {
        accessibilityNodeProviderCompat = null;
      } 
      return (AccessibilityNodeProvider)accessibilityNodeProviderCompat;
    }
    
    public void onInitializeAccessibilityEvent(View param1View, AccessibilityEvent param1AccessibilityEvent) {
      compat.onInitializeAccessibilityEvent(param1View, param1AccessibilityEvent);
    }
    
    public void onInitializeAccessibilityNodeInfo(View param1View, AccessibilityNodeInfo param1AccessibilityNodeInfo) {
      compat.onInitializeAccessibilityNodeInfo(param1View, AccessibilityNodeInfoCompat.wrap(param1AccessibilityNodeInfo));
    }
    
    public void onPopulateAccessibilityEvent(View param1View, AccessibilityEvent param1AccessibilityEvent) {
      compat.onPopulateAccessibilityEvent(param1View, param1AccessibilityEvent);
    }
    
    public boolean onRequestSendAccessibilityEvent(ViewGroup param1ViewGroup, View param1View, AccessibilityEvent param1AccessibilityEvent) {
      return compat.onRequestSendAccessibilityEvent(param1ViewGroup, param1View, param1AccessibilityEvent);
    }
    
    public boolean performAccessibilityAction(View param1View, int param1Int, Bundle param1Bundle) {
      return compat.performAccessibilityAction(param1View, param1Int, param1Bundle);
    }
    
    public void sendAccessibilityEvent(View param1View, int param1Int) {
      compat.sendAccessibilityEvent(param1View, param1Int);
    }
    
    public void sendAccessibilityEventUnchecked(View param1View, AccessibilityEvent param1AccessibilityEvent) {
      compat.sendAccessibilityEventUnchecked(param1View, param1AccessibilityEvent);
    }
  }
  
  static class AccessibilityDelegateBaseImpl {
    public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View.AccessibilityDelegate param1AccessibilityDelegate, View param1View) {
      return null;
    }
    
    public View.AccessibilityDelegate newAccessibilityDelegateBridge(final AccessibilityDelegateCompat compat) {
      return new View.AccessibilityDelegate() {
          public boolean dispatchPopulateAccessibilityEvent(View param2View, AccessibilityEvent param2AccessibilityEvent) {
            return compat.dispatchPopulateAccessibilityEvent(param2View, param2AccessibilityEvent);
          }
          
          public void onInitializeAccessibilityEvent(View param2View, AccessibilityEvent param2AccessibilityEvent) {
            compat.onInitializeAccessibilityEvent(param2View, param2AccessibilityEvent);
          }
          
          public void onInitializeAccessibilityNodeInfo(View param2View, AccessibilityNodeInfo param2AccessibilityNodeInfo) {
            compat.onInitializeAccessibilityNodeInfo(param2View, AccessibilityNodeInfoCompat.wrap(param2AccessibilityNodeInfo));
          }
          
          public void onPopulateAccessibilityEvent(View param2View, AccessibilityEvent param2AccessibilityEvent) {
            compat.onPopulateAccessibilityEvent(param2View, param2AccessibilityEvent);
          }
          
          public boolean onRequestSendAccessibilityEvent(ViewGroup param2ViewGroup, View param2View, AccessibilityEvent param2AccessibilityEvent) {
            return compat.onRequestSendAccessibilityEvent(param2ViewGroup, param2View, param2AccessibilityEvent);
          }
          
          public void sendAccessibilityEvent(View param2View, int param2Int) {
            compat.sendAccessibilityEvent(param2View, param2Int);
          }
          
          public void sendAccessibilityEventUnchecked(View param2View, AccessibilityEvent param2AccessibilityEvent) {
            compat.sendAccessibilityEventUnchecked(param2View, param2AccessibilityEvent);
          }
        };
    }
    
    public boolean performAccessibilityAction(View.AccessibilityDelegate param1AccessibilityDelegate, View param1View, int param1Int, Bundle param1Bundle) {
      return false;
    }
  }
  
  class null extends View.AccessibilityDelegate {
    public boolean dispatchPopulateAccessibilityEvent(View param1View, AccessibilityEvent param1AccessibilityEvent) {
      return compat.dispatchPopulateAccessibilityEvent(param1View, param1AccessibilityEvent);
    }
    
    public void onInitializeAccessibilityEvent(View param1View, AccessibilityEvent param1AccessibilityEvent) {
      compat.onInitializeAccessibilityEvent(param1View, param1AccessibilityEvent);
    }
    
    public void onInitializeAccessibilityNodeInfo(View param1View, AccessibilityNodeInfo param1AccessibilityNodeInfo) {
      compat.onInitializeAccessibilityNodeInfo(param1View, AccessibilityNodeInfoCompat.wrap(param1AccessibilityNodeInfo));
    }
    
    public void onPopulateAccessibilityEvent(View param1View, AccessibilityEvent param1AccessibilityEvent) {
      compat.onPopulateAccessibilityEvent(param1View, param1AccessibilityEvent);
    }
    
    public boolean onRequestSendAccessibilityEvent(ViewGroup param1ViewGroup, View param1View, AccessibilityEvent param1AccessibilityEvent) {
      return compat.onRequestSendAccessibilityEvent(param1ViewGroup, param1View, param1AccessibilityEvent);
    }
    
    public void sendAccessibilityEvent(View param1View, int param1Int) {
      compat.sendAccessibilityEvent(param1View, param1Int);
    }
    
    public void sendAccessibilityEventUnchecked(View param1View, AccessibilityEvent param1AccessibilityEvent) {
      compat.sendAccessibilityEventUnchecked(param1View, param1AccessibilityEvent);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\view\AccessibilityDelegateCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */