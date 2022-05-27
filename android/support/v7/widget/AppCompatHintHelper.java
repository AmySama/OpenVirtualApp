package android.support.v7.widget;

import android.view.View;
import android.view.ViewParent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

class AppCompatHintHelper {
  static InputConnection onCreateInputConnection(InputConnection paramInputConnection, EditorInfo paramEditorInfo, View paramView) {
    if (paramInputConnection != null && paramEditorInfo.hintText == null)
      for (ViewParent viewParent = paramView.getParent(); viewParent instanceof View; viewParent = viewParent.getParent()) {
        if (viewParent instanceof WithHint) {
          paramEditorInfo.hintText = ((WithHint)viewParent).getHint();
          break;
        } 
      }  
    return paramInputConnection;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\AppCompatHintHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */