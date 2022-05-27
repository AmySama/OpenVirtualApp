package android.support.v7.app;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class AppCompatDialogFragment extends DialogFragment {
  public Dialog onCreateDialog(Bundle paramBundle) {
    return new AppCompatDialog(getContext(), getTheme());
  }
  
  public void setupDialog(Dialog paramDialog, int paramInt) {
    if (paramDialog instanceof AppCompatDialog) {
      AppCompatDialog appCompatDialog = (AppCompatDialog)paramDialog;
      if (paramInt != 1 && paramInt != 2) {
        if (paramInt != 3)
          return; 
        paramDialog.getWindow().addFlags(24);
      } 
      appCompatDialog.supportRequestWindowFeature(1);
    } else {
      super.setupDialog(paramDialog, paramInt);
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\app\AppCompatDialogFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */