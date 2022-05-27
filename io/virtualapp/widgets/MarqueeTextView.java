package io.virtualapp.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class MarqueeTextView extends AppCompatTextView {
  private boolean isStop = false;
  
  public MarqueeTextView(Context paramContext) {
    super(paramContext);
  }
  
  public MarqueeTextView(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
  }
  
  public MarqueeTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public boolean isFocused() {
    return this.isStop ? super.isFocused() : true;
  }
  
  protected void onDetachedFromWindow() {
    stopScroll();
    super.onDetachedFromWindow();
  }
  
  public void start() {
    this.isStop = false;
  }
  
  public void stopScroll() {
    this.isStop = true;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\MarqueeTextView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */