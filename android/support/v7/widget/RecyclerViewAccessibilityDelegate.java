package android.support.v7.widget;

import android.os.Bundle;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

public class RecyclerViewAccessibilityDelegate extends AccessibilityDelegateCompat {
  final AccessibilityDelegateCompat mItemDelegate;
  
  final RecyclerView mRecyclerView;
  
  public RecyclerViewAccessibilityDelegate(RecyclerView paramRecyclerView) {
    this.mRecyclerView = paramRecyclerView;
    this.mItemDelegate = new ItemDelegate(this);
  }
  
  public AccessibilityDelegateCompat getItemDelegate() {
    return this.mItemDelegate;
  }
  
  public void onInitializeAccessibilityEvent(View paramView, AccessibilityEvent paramAccessibilityEvent) {
    super.onInitializeAccessibilityEvent(paramView, paramAccessibilityEvent);
    paramAccessibilityEvent.setClassName(RecyclerView.class.getName());
    if (paramView instanceof RecyclerView && !shouldIgnore()) {
      RecyclerView recyclerView = (RecyclerView)paramView;
      if (recyclerView.getLayoutManager() != null)
        recyclerView.getLayoutManager().onInitializeAccessibilityEvent(paramAccessibilityEvent); 
    } 
  }
  
  public void onInitializeAccessibilityNodeInfo(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat) {
    super.onInitializeAccessibilityNodeInfo(paramView, paramAccessibilityNodeInfoCompat);
    paramAccessibilityNodeInfoCompat.setClassName(RecyclerView.class.getName());
    if (!shouldIgnore() && this.mRecyclerView.getLayoutManager() != null)
      this.mRecyclerView.getLayoutManager().onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfoCompat); 
  }
  
  public boolean performAccessibilityAction(View paramView, int paramInt, Bundle paramBundle) {
    return super.performAccessibilityAction(paramView, paramInt, paramBundle) ? true : ((!shouldIgnore() && this.mRecyclerView.getLayoutManager() != null) ? this.mRecyclerView.getLayoutManager().performAccessibilityAction(paramInt, paramBundle) : false);
  }
  
  boolean shouldIgnore() {
    return this.mRecyclerView.hasPendingAdapterUpdates();
  }
  
  public static class ItemDelegate extends AccessibilityDelegateCompat {
    final RecyclerViewAccessibilityDelegate mRecyclerViewDelegate;
    
    public ItemDelegate(RecyclerViewAccessibilityDelegate param1RecyclerViewAccessibilityDelegate) {
      this.mRecyclerViewDelegate = param1RecyclerViewAccessibilityDelegate;
    }
    
    public void onInitializeAccessibilityNodeInfo(View param1View, AccessibilityNodeInfoCompat param1AccessibilityNodeInfoCompat) {
      super.onInitializeAccessibilityNodeInfo(param1View, param1AccessibilityNodeInfoCompat);
      if (!this.mRecyclerViewDelegate.shouldIgnore() && this.mRecyclerViewDelegate.mRecyclerView.getLayoutManager() != null)
        this.mRecyclerViewDelegate.mRecyclerView.getLayoutManager().onInitializeAccessibilityNodeInfoForItem(param1View, param1AccessibilityNodeInfoCompat); 
    }
    
    public boolean performAccessibilityAction(View param1View, int param1Int, Bundle param1Bundle) {
      return super.performAccessibilityAction(param1View, param1Int, param1Bundle) ? true : ((!this.mRecyclerViewDelegate.shouldIgnore() && this.mRecyclerViewDelegate.mRecyclerView.getLayoutManager() != null) ? this.mRecyclerViewDelegate.mRecyclerView.getLayoutManager().performAccessibilityActionForItem(param1View, param1Int, param1Bundle) : false);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\RecyclerViewAccessibilityDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */