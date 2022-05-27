package android.support.v7.text;

import android.content.Context;
import android.graphics.Rect;
import android.text.method.TransformationMethod;
import android.view.View;
import java.util.Locale;

public class AllCapsTransformationMethod implements TransformationMethod {
  private Locale mLocale;
  
  public AllCapsTransformationMethod(Context paramContext) {
    this.mLocale = (paramContext.getResources().getConfiguration()).locale;
  }
  
  public CharSequence getTransformation(CharSequence paramCharSequence, View paramView) {
    if (paramCharSequence != null) {
      paramCharSequence = paramCharSequence.toString().toUpperCase(this.mLocale);
    } else {
      paramCharSequence = null;
    } 
    return paramCharSequence;
  }
  
  public void onFocusChanged(View paramView, CharSequence paramCharSequence, boolean paramBoolean, int paramInt, Rect paramRect) {}
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\text\AllCapsTransformationMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */