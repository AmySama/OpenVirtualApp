package android.support.v4.app;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DialogFragment extends Fragment implements DialogInterface.OnCancelListener, DialogInterface.OnDismissListener {
  private static final String SAVED_BACK_STACK_ID = "android:backStackId";
  
  private static final String SAVED_CANCELABLE = "android:cancelable";
  
  private static final String SAVED_DIALOG_STATE_TAG = "android:savedDialogState";
  
  private static final String SAVED_SHOWS_DIALOG = "android:showsDialog";
  
  private static final String SAVED_STYLE = "android:style";
  
  private static final String SAVED_THEME = "android:theme";
  
  public static final int STYLE_NORMAL = 0;
  
  public static final int STYLE_NO_FRAME = 2;
  
  public static final int STYLE_NO_INPUT = 3;
  
  public static final int STYLE_NO_TITLE = 1;
  
  int mBackStackId = -1;
  
  boolean mCancelable = true;
  
  Dialog mDialog;
  
  boolean mDismissed;
  
  boolean mShownByMe;
  
  boolean mShowsDialog = true;
  
  int mStyle = 0;
  
  int mTheme = 0;
  
  boolean mViewDestroyed;
  
  public void dismiss() {
    dismissInternal(false);
  }
  
  public void dismissAllowingStateLoss() {
    dismissInternal(true);
  }
  
  void dismissInternal(boolean paramBoolean) {
    if (this.mDismissed)
      return; 
    this.mDismissed = true;
    this.mShownByMe = false;
    Dialog dialog = this.mDialog;
    if (dialog != null)
      dialog.dismiss(); 
    this.mViewDestroyed = true;
    if (this.mBackStackId >= 0) {
      getFragmentManager().popBackStack(this.mBackStackId, 1);
      this.mBackStackId = -1;
    } else {
      FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
      fragmentTransaction.remove(this);
      if (paramBoolean) {
        fragmentTransaction.commitAllowingStateLoss();
      } else {
        fragmentTransaction.commit();
      } 
    } 
  }
  
  public Dialog getDialog() {
    return this.mDialog;
  }
  
  public boolean getShowsDialog() {
    return this.mShowsDialog;
  }
  
  public int getTheme() {
    return this.mTheme;
  }
  
  public boolean isCancelable() {
    return this.mCancelable;
  }
  
  public void onActivityCreated(Bundle paramBundle) {
    super.onActivityCreated(paramBundle);
    if (!this.mShowsDialog)
      return; 
    View view = getView();
    if (view != null)
      if (view.getParent() == null) {
        this.mDialog.setContentView(view);
      } else {
        throw new IllegalStateException("DialogFragment can not be attached to a container view");
      }  
    FragmentActivity fragmentActivity = getActivity();
    if (fragmentActivity != null)
      this.mDialog.setOwnerActivity(fragmentActivity); 
    this.mDialog.setCancelable(this.mCancelable);
    this.mDialog.setOnCancelListener(this);
    this.mDialog.setOnDismissListener(this);
    if (paramBundle != null) {
      paramBundle = paramBundle.getBundle("android:savedDialogState");
      if (paramBundle != null)
        this.mDialog.onRestoreInstanceState(paramBundle); 
    } 
  }
  
  public void onAttach(Context paramContext) {
    super.onAttach(paramContext);
    if (!this.mShownByMe)
      this.mDismissed = false; 
  }
  
  public void onCancel(DialogInterface paramDialogInterface) {}
  
  public void onCreate(Bundle paramBundle) {
    boolean bool;
    super.onCreate(paramBundle);
    if (this.mContainerId == 0) {
      bool = true;
    } else {
      bool = false;
    } 
    this.mShowsDialog = bool;
    if (paramBundle != null) {
      this.mStyle = paramBundle.getInt("android:style", 0);
      this.mTheme = paramBundle.getInt("android:theme", 0);
      this.mCancelable = paramBundle.getBoolean("android:cancelable", true);
      this.mShowsDialog = paramBundle.getBoolean("android:showsDialog", this.mShowsDialog);
      this.mBackStackId = paramBundle.getInt("android:backStackId", -1);
    } 
  }
  
  public Dialog onCreateDialog(Bundle paramBundle) {
    return new Dialog((Context)getActivity(), getTheme());
  }
  
  public void onDestroyView() {
    super.onDestroyView();
    Dialog dialog = this.mDialog;
    if (dialog != null) {
      this.mViewDestroyed = true;
      dialog.dismiss();
      this.mDialog = null;
    } 
  }
  
  public void onDetach() {
    super.onDetach();
    if (!this.mShownByMe && !this.mDismissed)
      this.mDismissed = true; 
  }
  
  public void onDismiss(DialogInterface paramDialogInterface) {
    if (!this.mViewDestroyed)
      dismissInternal(true); 
  }
  
  public LayoutInflater onGetLayoutInflater(Bundle paramBundle) {
    if (!this.mShowsDialog)
      return super.onGetLayoutInflater(paramBundle); 
    Dialog dialog = onCreateDialog(paramBundle);
    this.mDialog = dialog;
    if (dialog != null) {
      setupDialog(dialog, this.mStyle);
      return (LayoutInflater)this.mDialog.getContext().getSystemService("layout_inflater");
    } 
    return (LayoutInflater)this.mHost.getContext().getSystemService("layout_inflater");
  }
  
  public void onSaveInstanceState(Bundle paramBundle) {
    super.onSaveInstanceState(paramBundle);
    Dialog dialog = this.mDialog;
    if (dialog != null) {
      Bundle bundle = dialog.onSaveInstanceState();
      if (bundle != null)
        paramBundle.putBundle("android:savedDialogState", bundle); 
    } 
    int i = this.mStyle;
    if (i != 0)
      paramBundle.putInt("android:style", i); 
    i = this.mTheme;
    if (i != 0)
      paramBundle.putInt("android:theme", i); 
    boolean bool = this.mCancelable;
    if (!bool)
      paramBundle.putBoolean("android:cancelable", bool); 
    bool = this.mShowsDialog;
    if (!bool)
      paramBundle.putBoolean("android:showsDialog", bool); 
    i = this.mBackStackId;
    if (i != -1)
      paramBundle.putInt("android:backStackId", i); 
  }
  
  public void onStart() {
    super.onStart();
    Dialog dialog = this.mDialog;
    if (dialog != null) {
      this.mViewDestroyed = false;
      dialog.show();
    } 
  }
  
  public void onStop() {
    super.onStop();
    Dialog dialog = this.mDialog;
    if (dialog != null)
      dialog.hide(); 
  }
  
  public void setCancelable(boolean paramBoolean) {
    this.mCancelable = paramBoolean;
    Dialog dialog = this.mDialog;
    if (dialog != null)
      dialog.setCancelable(paramBoolean); 
  }
  
  public void setShowsDialog(boolean paramBoolean) {
    this.mShowsDialog = paramBoolean;
  }
  
  public void setStyle(int paramInt1, int paramInt2) {
    this.mStyle = paramInt1;
    if (paramInt1 == 2 || paramInt1 == 3)
      this.mTheme = 16973913; 
    if (paramInt2 != 0)
      this.mTheme = paramInt2; 
  }
  
  public void setupDialog(Dialog paramDialog, int paramInt) {
    if (paramInt != 1 && paramInt != 2) {
      if (paramInt != 3)
        return; 
      paramDialog.getWindow().addFlags(24);
    } 
    paramDialog.requestWindowFeature(1);
  }
  
  public int show(FragmentTransaction paramFragmentTransaction, String paramString) {
    this.mDismissed = false;
    this.mShownByMe = true;
    paramFragmentTransaction.add(this, paramString);
    this.mViewDestroyed = false;
    int i = paramFragmentTransaction.commit();
    this.mBackStackId = i;
    return i;
  }
  
  public void show(FragmentManager paramFragmentManager, String paramString) {
    this.mDismissed = false;
    this.mShownByMe = true;
    FragmentTransaction fragmentTransaction = paramFragmentManager.beginTransaction();
    fragmentTransaction.add(this, paramString);
    fragmentTransaction.commit();
  }
  
  public void showNow(FragmentManager paramFragmentManager, String paramString) {
    this.mDismissed = false;
    this.mShownByMe = true;
    FragmentTransaction fragmentTransaction = paramFragmentManager.beginTransaction();
    fragmentTransaction.add(this, paramString);
    fragmentTransaction.commitNow();
  }
  
  @Retention(RetentionPolicy.SOURCE)
  private static @interface DialogStyle {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\app\DialogFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */