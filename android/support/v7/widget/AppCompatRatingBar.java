package android.support.v7.widget;

import android.content.Context;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.RatingBar;

public class AppCompatRatingBar extends RatingBar {
  private final AppCompatProgressBarHelper mAppCompatProgressBarHelper;
  
  public AppCompatRatingBar(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public AppCompatRatingBar(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, R.attr.ratingBarStyle);
  }
  
  public AppCompatRatingBar(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    AppCompatProgressBarHelper appCompatProgressBarHelper = new AppCompatProgressBarHelper((ProgressBar)this);
    this.mAppCompatProgressBarHelper = appCompatProgressBarHelper;
    appCompatProgressBarHelper.loadFromAttributes(paramAttributeSet, paramInt);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: iload_1
    //   4: iload_2
    //   5: invokespecial onMeasure : (II)V
    //   8: aload_0
    //   9: getfield mAppCompatProgressBarHelper : Landroid/support/v7/widget/AppCompatProgressBarHelper;
    //   12: invokevirtual getSampleTime : ()Landroid/graphics/Bitmap;
    //   15: astore_3
    //   16: aload_3
    //   17: ifnull -> 42
    //   20: aload_0
    //   21: aload_3
    //   22: invokevirtual getWidth : ()I
    //   25: aload_0
    //   26: invokevirtual getNumStars : ()I
    //   29: imul
    //   30: iload_1
    //   31: iconst_0
    //   32: invokestatic resolveSizeAndState : (III)I
    //   35: aload_0
    //   36: invokevirtual getMeasuredHeight : ()I
    //   39: invokevirtual setMeasuredDimension : (II)V
    //   42: aload_0
    //   43: monitorexit
    //   44: return
    //   45: astore_3
    //   46: aload_0
    //   47: monitorexit
    //   48: aload_3
    //   49: athrow
    // Exception table:
    //   from	to	target	type
    //   2	16	45	finally
    //   20	42	45	finally
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\AppCompatRatingBar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */