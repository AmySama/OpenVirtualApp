package android.support.v7.view;

import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import java.util.List;

public class WindowCallbackWrapper implements Window.Callback {
  final Window.Callback mWrapped;
  
  public WindowCallbackWrapper(Window.Callback paramCallback) {
    if (paramCallback != null) {
      this.mWrapped = paramCallback;
      return;
    } 
    throw new IllegalArgumentException("Window callback may not be null");
  }
  
  public boolean dispatchGenericMotionEvent(MotionEvent paramMotionEvent) {
    return this.mWrapped.dispatchGenericMotionEvent(paramMotionEvent);
  }
  
  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent) {
    return this.mWrapped.dispatchKeyEvent(paramKeyEvent);
  }
  
  public boolean dispatchKeyShortcutEvent(KeyEvent paramKeyEvent) {
    return this.mWrapped.dispatchKeyShortcutEvent(paramKeyEvent);
  }
  
  public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent) {
    return this.mWrapped.dispatchPopulateAccessibilityEvent(paramAccessibilityEvent);
  }
  
  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent) {
    return this.mWrapped.dispatchTouchEvent(paramMotionEvent);
  }
  
  public boolean dispatchTrackballEvent(MotionEvent paramMotionEvent) {
    return this.mWrapped.dispatchTrackballEvent(paramMotionEvent);
  }
  
  public void onActionModeFinished(ActionMode paramActionMode) {
    this.mWrapped.onActionModeFinished(paramActionMode);
  }
  
  public void onActionModeStarted(ActionMode paramActionMode) {
    this.mWrapped.onActionModeStarted(paramActionMode);
  }
  
  public void onAttachedToWindow() {
    this.mWrapped.onAttachedToWindow();
  }
  
  public void onContentChanged() {
    this.mWrapped.onContentChanged();
  }
  
  public boolean onCreatePanelMenu(int paramInt, Menu paramMenu) {
    return this.mWrapped.onCreatePanelMenu(paramInt, paramMenu);
  }
  
  public View onCreatePanelView(int paramInt) {
    return this.mWrapped.onCreatePanelView(paramInt);
  }
  
  public void onDetachedFromWindow() {
    this.mWrapped.onDetachedFromWindow();
  }
  
  public boolean onMenuItemSelected(int paramInt, MenuItem paramMenuItem) {
    return this.mWrapped.onMenuItemSelected(paramInt, paramMenuItem);
  }
  
  public boolean onMenuOpened(int paramInt, Menu paramMenu) {
    return this.mWrapped.onMenuOpened(paramInt, paramMenu);
  }
  
  public void onPanelClosed(int paramInt, Menu paramMenu) {
    this.mWrapped.onPanelClosed(paramInt, paramMenu);
  }
  
  public void onPointerCaptureChanged(boolean paramBoolean) {
    this.mWrapped.onPointerCaptureChanged(paramBoolean);
  }
  
  public boolean onPreparePanel(int paramInt, View paramView, Menu paramMenu) {
    return this.mWrapped.onPreparePanel(paramInt, paramView, paramMenu);
  }
  
  public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> paramList, Menu paramMenu, int paramInt) {
    this.mWrapped.onProvideKeyboardShortcuts(paramList, paramMenu, paramInt);
  }
  
  public boolean onSearchRequested() {
    return this.mWrapped.onSearchRequested();
  }
  
  public boolean onSearchRequested(SearchEvent paramSearchEvent) {
    return this.mWrapped.onSearchRequested(paramSearchEvent);
  }
  
  public void onWindowAttributesChanged(WindowManager.LayoutParams paramLayoutParams) {
    this.mWrapped.onWindowAttributesChanged(paramLayoutParams);
  }
  
  public void onWindowFocusChanged(boolean paramBoolean) {
    this.mWrapped.onWindowFocusChanged(paramBoolean);
  }
  
  public ActionMode onWindowStartingActionMode(ActionMode.Callback paramCallback) {
    return this.mWrapped.onWindowStartingActionMode(paramCallback);
  }
  
  public ActionMode onWindowStartingActionMode(ActionMode.Callback paramCallback, int paramInt) {
    return this.mWrapped.onWindowStartingActionMode(paramCallback, paramInt);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\view\WindowCallbackWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */