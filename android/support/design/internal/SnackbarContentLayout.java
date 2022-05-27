package android.support.design.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.R;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SnackbarContentLayout extends LinearLayout implements BaseTransientBottomBar.ContentViewCallback {
  private Button mActionView;
  
  private int mMaxInlineActionWidth;
  
  private int mMaxWidth;
  
  private TextView mMessageView;
  
  public SnackbarContentLayout(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public SnackbarContentLayout(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.SnackbarLayout);
    this.mMaxWidth = typedArray.getDimensionPixelSize(R.styleable.SnackbarLayout_android_maxWidth, -1);
    this.mMaxInlineActionWidth = typedArray.getDimensionPixelSize(R.styleable.SnackbarLayout_maxActionInlineWidth, -1);
    typedArray.recycle();
  }
  
  private static void updateTopBottomPadding(View paramView, int paramInt1, int paramInt2) {
    if (ViewCompat.isPaddingRelative(paramView)) {
      ViewCompat.setPaddingRelative(paramView, ViewCompat.getPaddingStart(paramView), paramInt1, ViewCompat.getPaddingEnd(paramView), paramInt2);
    } else {
      paramView.setPadding(paramView.getPaddingLeft(), paramInt1, paramView.getPaddingRight(), paramInt2);
    } 
  }
  
  private boolean updateViewsWithinLayout(int paramInt1, int paramInt2, int paramInt3) {
    boolean bool2;
    int i = getOrientation();
    boolean bool1 = true;
    if (paramInt1 != i) {
      setOrientation(paramInt1);
      bool2 = true;
    } else {
      bool2 = false;
    } 
    if (this.mMessageView.getPaddingTop() != paramInt2 || this.mMessageView.getPaddingBottom() != paramInt3) {
      updateTopBottomPadding((View)this.mMessageView, paramInt2, paramInt3);
      bool2 = bool1;
    } 
    return bool2;
  }
  
  public void animateContentIn(int paramInt1, int paramInt2) {
    this.mMessageView.setAlpha(0.0F);
    ViewPropertyAnimator viewPropertyAnimator = this.mMessageView.animate().alpha(1.0F);
    long l1 = paramInt2;
    viewPropertyAnimator = viewPropertyAnimator.setDuration(l1);
    long l2 = paramInt1;
    viewPropertyAnimator.setStartDelay(l2).start();
    if (this.mActionView.getVisibility() == 0) {
      this.mActionView.setAlpha(0.0F);
      this.mActionView.animate().alpha(1.0F).setDuration(l1).setStartDelay(l2).start();
    } 
  }
  
  public void animateContentOut(int paramInt1, int paramInt2) {
    this.mMessageView.setAlpha(1.0F);
    ViewPropertyAnimator viewPropertyAnimator = this.mMessageView.animate().alpha(0.0F);
    long l1 = paramInt2;
    viewPropertyAnimator = viewPropertyAnimator.setDuration(l1);
    long l2 = paramInt1;
    viewPropertyAnimator.setStartDelay(l2).start();
    if (this.mActionView.getVisibility() == 0) {
      this.mActionView.setAlpha(1.0F);
      this.mActionView.animate().alpha(0.0F).setDuration(l1).setStartDelay(l2).start();
    } 
  }
  
  public Button getActionView() {
    return this.mActionView;
  }
  
  public TextView getMessageView() {
    return this.mMessageView;
  }
  
  protected void onFinishInflate() {
    super.onFinishInflate();
    this.mMessageView = (TextView)findViewById(R.id.snackbar_text);
    this.mActionView = (Button)findViewById(R.id.snackbar_action);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    // Byte code:
    //   0: aload_0
    //   1: iload_1
    //   2: iload_2
    //   3: invokespecial onMeasure : (II)V
    //   6: iload_1
    //   7: istore_3
    //   8: aload_0
    //   9: getfield mMaxWidth : I
    //   12: ifle -> 50
    //   15: aload_0
    //   16: invokevirtual getMeasuredWidth : ()I
    //   19: istore #4
    //   21: aload_0
    //   22: getfield mMaxWidth : I
    //   25: istore #5
    //   27: iload_1
    //   28: istore_3
    //   29: iload #4
    //   31: iload #5
    //   33: if_icmple -> 50
    //   36: iload #5
    //   38: ldc 1073741824
    //   40: invokestatic makeMeasureSpec : (II)I
    //   43: istore_3
    //   44: aload_0
    //   45: iload_3
    //   46: iload_2
    //   47: invokespecial onMeasure : (II)V
    //   50: aload_0
    //   51: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   54: getstatic android/support/design/R$dimen.design_snackbar_padding_vertical_2lines : I
    //   57: invokevirtual getDimensionPixelSize : (I)I
    //   60: istore #6
    //   62: aload_0
    //   63: invokevirtual getResources : ()Landroid/content/res/Resources;
    //   66: getstatic android/support/design/R$dimen.design_snackbar_padding_vertical : I
    //   69: invokevirtual getDimensionPixelSize : (I)I
    //   72: istore #4
    //   74: aload_0
    //   75: getfield mMessageView : Landroid/widget/TextView;
    //   78: invokevirtual getLayout : ()Landroid/text/Layout;
    //   81: invokevirtual getLineCount : ()I
    //   84: istore_1
    //   85: iconst_0
    //   86: istore #5
    //   88: iload_1
    //   89: iconst_1
    //   90: if_icmple -> 98
    //   93: iconst_1
    //   94: istore_1
    //   95: goto -> 100
    //   98: iconst_0
    //   99: istore_1
    //   100: iload_1
    //   101: ifeq -> 146
    //   104: aload_0
    //   105: getfield mMaxInlineActionWidth : I
    //   108: ifle -> 146
    //   111: aload_0
    //   112: getfield mActionView : Landroid/widget/Button;
    //   115: invokevirtual getMeasuredWidth : ()I
    //   118: aload_0
    //   119: getfield mMaxInlineActionWidth : I
    //   122: if_icmple -> 146
    //   125: iload #5
    //   127: istore_1
    //   128: aload_0
    //   129: iconst_1
    //   130: iload #6
    //   132: iload #6
    //   134: iload #4
    //   136: isub
    //   137: invokespecial updateViewsWithinLayout : (III)Z
    //   140: ifeq -> 174
    //   143: goto -> 172
    //   146: iload_1
    //   147: ifeq -> 157
    //   150: iload #6
    //   152: istore #4
    //   154: goto -> 157
    //   157: iload #5
    //   159: istore_1
    //   160: aload_0
    //   161: iconst_0
    //   162: iload #4
    //   164: iload #4
    //   166: invokespecial updateViewsWithinLayout : (III)Z
    //   169: ifeq -> 174
    //   172: iconst_1
    //   173: istore_1
    //   174: iload_1
    //   175: ifeq -> 184
    //   178: aload_0
    //   179: iload_3
    //   180: iload_2
    //   181: invokespecial onMeasure : (II)V
    //   184: return
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\internal\SnackbarContentLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */