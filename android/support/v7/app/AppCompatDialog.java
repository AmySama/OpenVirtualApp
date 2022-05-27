package android.support.v7.app;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.appcompat.R;
import android.support.v7.view.ActionMode;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

public class AppCompatDialog extends Dialog implements AppCompatCallback {
  private AppCompatDelegate mDelegate;
  
  public AppCompatDialog(Context paramContext) {
    this(paramContext, 0);
  }
  
  public AppCompatDialog(Context paramContext, int paramInt) {
    super(paramContext, getThemeResId(paramContext, paramInt));
    getDelegate().onCreate(null);
    getDelegate().applyDayNight();
  }
  
  protected AppCompatDialog(Context paramContext, boolean paramBoolean, DialogInterface.OnCancelListener paramOnCancelListener) {
    super(paramContext, paramBoolean, paramOnCancelListener);
  }
  
  private static int getThemeResId(Context paramContext, int paramInt) {
    int i = paramInt;
    if (paramInt == 0) {
      TypedValue typedValue = new TypedValue();
      paramContext.getTheme().resolveAttribute(R.attr.dialogTheme, typedValue, true);
      i = typedValue.resourceId;
    } 
    return i;
  }
  
  public void addContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams) {
    getDelegate().addContentView(paramView, paramLayoutParams);
  }
  
  public <T extends View> T findViewById(int paramInt) {
    return getDelegate().findViewById(paramInt);
  }
  
  public AppCompatDelegate getDelegate() {
    if (this.mDelegate == null)
      this.mDelegate = AppCompatDelegate.create(this, this); 
    return this.mDelegate;
  }
  
  public ActionBar getSupportActionBar() {
    return getDelegate().getSupportActionBar();
  }
  
  public void invalidateOptionsMenu() {
    getDelegate().invalidateOptionsMenu();
  }
  
  protected void onCreate(Bundle paramBundle) {
    getDelegate().installViewFactory();
    super.onCreate(paramBundle);
    getDelegate().onCreate(paramBundle);
  }
  
  protected void onStop() {
    super.onStop();
    getDelegate().onStop();
  }
  
  public void onSupportActionModeFinished(ActionMode paramActionMode) {}
  
  public void onSupportActionModeStarted(ActionMode paramActionMode) {}
  
  public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback paramCallback) {
    return null;
  }
  
  public void setContentView(int paramInt) {
    getDelegate().setContentView(paramInt);
  }
  
  public void setContentView(View paramView) {
    getDelegate().setContentView(paramView);
  }
  
  public void setContentView(View paramView, ViewGroup.LayoutParams paramLayoutParams) {
    getDelegate().setContentView(paramView, paramLayoutParams);
  }
  
  public void setTitle(int paramInt) {
    super.setTitle(paramInt);
    getDelegate().setTitle(getContext().getString(paramInt));
  }
  
  public void setTitle(CharSequence paramCharSequence) {
    super.setTitle(paramCharSequence);
    getDelegate().setTitle(paramCharSequence);
  }
  
  public boolean supportRequestWindowFeature(int paramInt) {
    return getDelegate().requestWindowFeature(paramInt);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\app\AppCompatDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */