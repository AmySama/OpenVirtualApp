package android.support.v4.view;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;

public final class ViewParentCompat {
  static final ViewParentCompatBaseImpl IMPL;
  
  private static final String TAG = "ViewParentCompat";
  
  static {
    if (Build.VERSION.SDK_INT >= 21) {
      IMPL = new ViewParentCompatApi21Impl();
    } else if (Build.VERSION.SDK_INT >= 19) {
      IMPL = new ViewParentCompatApi19Impl();
    } else {
      IMPL = new ViewParentCompatBaseImpl();
    } 
  }
  
  public static void notifySubtreeAccessibilityStateChanged(ViewParent paramViewParent, View paramView1, View paramView2, int paramInt) {
    IMPL.notifySubtreeAccessibilityStateChanged(paramViewParent, paramView1, paramView2, paramInt);
  }
  
  public static boolean onNestedFling(ViewParent paramViewParent, View paramView, float paramFloat1, float paramFloat2, boolean paramBoolean) {
    return IMPL.onNestedFling(paramViewParent, paramView, paramFloat1, paramFloat2, paramBoolean);
  }
  
  public static boolean onNestedPreFling(ViewParent paramViewParent, View paramView, float paramFloat1, float paramFloat2) {
    return IMPL.onNestedPreFling(paramViewParent, paramView, paramFloat1, paramFloat2);
  }
  
  public static void onNestedPreScroll(ViewParent paramViewParent, View paramView, int paramInt1, int paramInt2, int[] paramArrayOfint) {
    onNestedPreScroll(paramViewParent, paramView, paramInt1, paramInt2, paramArrayOfint, 0);
  }
  
  public static void onNestedPreScroll(ViewParent paramViewParent, View paramView, int paramInt1, int paramInt2, int[] paramArrayOfint, int paramInt3) {
    if (paramViewParent instanceof NestedScrollingParent2) {
      ((NestedScrollingParent2)paramViewParent).onNestedPreScroll(paramView, paramInt1, paramInt2, paramArrayOfint, paramInt3);
    } else if (paramInt3 == 0) {
      IMPL.onNestedPreScroll(paramViewParent, paramView, paramInt1, paramInt2, paramArrayOfint);
    } 
  }
  
  public static void onNestedScroll(ViewParent paramViewParent, View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    onNestedScroll(paramViewParent, paramView, paramInt1, paramInt2, paramInt3, paramInt4, 0);
  }
  
  public static void onNestedScroll(ViewParent paramViewParent, View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    if (paramViewParent instanceof NestedScrollingParent2) {
      ((NestedScrollingParent2)paramViewParent).onNestedScroll(paramView, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    } else if (paramInt5 == 0) {
      IMPL.onNestedScroll(paramViewParent, paramView, paramInt1, paramInt2, paramInt3, paramInt4);
    } 
  }
  
  public static void onNestedScrollAccepted(ViewParent paramViewParent, View paramView1, View paramView2, int paramInt) {
    onNestedScrollAccepted(paramViewParent, paramView1, paramView2, paramInt, 0);
  }
  
  public static void onNestedScrollAccepted(ViewParent paramViewParent, View paramView1, View paramView2, int paramInt1, int paramInt2) {
    if (paramViewParent instanceof NestedScrollingParent2) {
      ((NestedScrollingParent2)paramViewParent).onNestedScrollAccepted(paramView1, paramView2, paramInt1, paramInt2);
    } else if (paramInt2 == 0) {
      IMPL.onNestedScrollAccepted(paramViewParent, paramView1, paramView2, paramInt1);
    } 
  }
  
  public static boolean onStartNestedScroll(ViewParent paramViewParent, View paramView1, View paramView2, int paramInt) {
    return onStartNestedScroll(paramViewParent, paramView1, paramView2, paramInt, 0);
  }
  
  public static boolean onStartNestedScroll(ViewParent paramViewParent, View paramView1, View paramView2, int paramInt1, int paramInt2) {
    return (paramViewParent instanceof NestedScrollingParent2) ? ((NestedScrollingParent2)paramViewParent).onStartNestedScroll(paramView1, paramView2, paramInt1, paramInt2) : ((paramInt2 == 0) ? IMPL.onStartNestedScroll(paramViewParent, paramView1, paramView2, paramInt1) : false);
  }
  
  public static void onStopNestedScroll(ViewParent paramViewParent, View paramView) {
    onStopNestedScroll(paramViewParent, paramView, 0);
  }
  
  public static void onStopNestedScroll(ViewParent paramViewParent, View paramView, int paramInt) {
    if (paramViewParent instanceof NestedScrollingParent2) {
      ((NestedScrollingParent2)paramViewParent).onStopNestedScroll(paramView, paramInt);
    } else if (paramInt == 0) {
      IMPL.onStopNestedScroll(paramViewParent, paramView);
    } 
  }
  
  @Deprecated
  public static boolean requestSendAccessibilityEvent(ViewParent paramViewParent, View paramView, AccessibilityEvent paramAccessibilityEvent) {
    return paramViewParent.requestSendAccessibilityEvent(paramView, paramAccessibilityEvent);
  }
  
  static class ViewParentCompatApi19Impl extends ViewParentCompatBaseImpl {
    public void notifySubtreeAccessibilityStateChanged(ViewParent param1ViewParent, View param1View1, View param1View2, int param1Int) {
      param1ViewParent.notifySubtreeAccessibilityStateChanged(param1View1, param1View2, param1Int);
    }
  }
  
  static class ViewParentCompatApi21Impl extends ViewParentCompatApi19Impl {
    public boolean onNestedFling(ViewParent param1ViewParent, View param1View, float param1Float1, float param1Float2, boolean param1Boolean) {
      try {
        return param1ViewParent.onNestedFling(param1View, param1Float1, param1Float2, param1Boolean);
      } catch (AbstractMethodError abstractMethodError) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ViewParent ");
        stringBuilder.append(param1ViewParent);
        stringBuilder.append(" does not implement interface ");
        stringBuilder.append("method onNestedFling");
        Log.e("ViewParentCompat", stringBuilder.toString(), abstractMethodError);
        return false;
      } 
    }
    
    public boolean onNestedPreFling(ViewParent param1ViewParent, View param1View, float param1Float1, float param1Float2) {
      try {
        return param1ViewParent.onNestedPreFling(param1View, param1Float1, param1Float2);
      } catch (AbstractMethodError abstractMethodError) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ViewParent ");
        stringBuilder.append(param1ViewParent);
        stringBuilder.append(" does not implement interface ");
        stringBuilder.append("method onNestedPreFling");
        Log.e("ViewParentCompat", stringBuilder.toString(), abstractMethodError);
        return false;
      } 
    }
    
    public void onNestedPreScroll(ViewParent param1ViewParent, View param1View, int param1Int1, int param1Int2, int[] param1ArrayOfint) {
      try {
        param1ViewParent.onNestedPreScroll(param1View, param1Int1, param1Int2, param1ArrayOfint);
      } catch (AbstractMethodError abstractMethodError) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ViewParent ");
        stringBuilder.append(param1ViewParent);
        stringBuilder.append(" does not implement interface ");
        stringBuilder.append("method onNestedPreScroll");
        Log.e("ViewParentCompat", stringBuilder.toString(), abstractMethodError);
      } 
    }
    
    public void onNestedScroll(ViewParent param1ViewParent, View param1View, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      try {
        param1ViewParent.onNestedScroll(param1View, param1Int1, param1Int2, param1Int3, param1Int4);
      } catch (AbstractMethodError abstractMethodError) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ViewParent ");
        stringBuilder.append(param1ViewParent);
        stringBuilder.append(" does not implement interface ");
        stringBuilder.append("method onNestedScroll");
        Log.e("ViewParentCompat", stringBuilder.toString(), abstractMethodError);
      } 
    }
    
    public void onNestedScrollAccepted(ViewParent param1ViewParent, View param1View1, View param1View2, int param1Int) {
      try {
        param1ViewParent.onNestedScrollAccepted(param1View1, param1View2, param1Int);
      } catch (AbstractMethodError abstractMethodError) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ViewParent ");
        stringBuilder.append(param1ViewParent);
        stringBuilder.append(" does not implement interface ");
        stringBuilder.append("method onNestedScrollAccepted");
        Log.e("ViewParentCompat", stringBuilder.toString(), abstractMethodError);
      } 
    }
    
    public boolean onStartNestedScroll(ViewParent param1ViewParent, View param1View1, View param1View2, int param1Int) {
      try {
        return param1ViewParent.onStartNestedScroll(param1View1, param1View2, param1Int);
      } catch (AbstractMethodError abstractMethodError) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ViewParent ");
        stringBuilder.append(param1ViewParent);
        stringBuilder.append(" does not implement interface ");
        stringBuilder.append("method onStartNestedScroll");
        Log.e("ViewParentCompat", stringBuilder.toString(), abstractMethodError);
        return false;
      } 
    }
    
    public void onStopNestedScroll(ViewParent param1ViewParent, View param1View) {
      try {
        param1ViewParent.onStopNestedScroll(param1View);
      } catch (AbstractMethodError abstractMethodError) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ViewParent ");
        stringBuilder.append(param1ViewParent);
        stringBuilder.append(" does not implement interface ");
        stringBuilder.append("method onStopNestedScroll");
        Log.e("ViewParentCompat", stringBuilder.toString(), abstractMethodError);
      } 
    }
  }
  
  static class ViewParentCompatBaseImpl {
    public void notifySubtreeAccessibilityStateChanged(ViewParent param1ViewParent, View param1View1, View param1View2, int param1Int) {}
    
    public boolean onNestedFling(ViewParent param1ViewParent, View param1View, float param1Float1, float param1Float2, boolean param1Boolean) {
      return (param1ViewParent instanceof NestedScrollingParent) ? ((NestedScrollingParent)param1ViewParent).onNestedFling(param1View, param1Float1, param1Float2, param1Boolean) : false;
    }
    
    public boolean onNestedPreFling(ViewParent param1ViewParent, View param1View, float param1Float1, float param1Float2) {
      return (param1ViewParent instanceof NestedScrollingParent) ? ((NestedScrollingParent)param1ViewParent).onNestedPreFling(param1View, param1Float1, param1Float2) : false;
    }
    
    public void onNestedPreScroll(ViewParent param1ViewParent, View param1View, int param1Int1, int param1Int2, int[] param1ArrayOfint) {
      if (param1ViewParent instanceof NestedScrollingParent)
        ((NestedScrollingParent)param1ViewParent).onNestedPreScroll(param1View, param1Int1, param1Int2, param1ArrayOfint); 
    }
    
    public void onNestedScroll(ViewParent param1ViewParent, View param1View, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      if (param1ViewParent instanceof NestedScrollingParent)
        ((NestedScrollingParent)param1ViewParent).onNestedScroll(param1View, param1Int1, param1Int2, param1Int3, param1Int4); 
    }
    
    public void onNestedScrollAccepted(ViewParent param1ViewParent, View param1View1, View param1View2, int param1Int) {
      if (param1ViewParent instanceof NestedScrollingParent)
        ((NestedScrollingParent)param1ViewParent).onNestedScrollAccepted(param1View1, param1View2, param1Int); 
    }
    
    public boolean onStartNestedScroll(ViewParent param1ViewParent, View param1View1, View param1View2, int param1Int) {
      return (param1ViewParent instanceof NestedScrollingParent) ? ((NestedScrollingParent)param1ViewParent).onStartNestedScroll(param1View1, param1View2, param1Int) : false;
    }
    
    public void onStopNestedScroll(ViewParent param1ViewParent, View param1View) {
      if (param1ViewParent instanceof NestedScrollingParent)
        ((NestedScrollingParent)param1ViewParent).onStopNestedScroll(param1View); 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\view\ViewParentCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */