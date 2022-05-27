package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.R;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseTransientBottomBar<B extends BaseTransientBottomBar<B>> {
  static final int ANIMATION_DURATION = 250;
  
  static final int ANIMATION_FADE_DURATION = 180;
  
  public static final int LENGTH_INDEFINITE = -2;
  
  public static final int LENGTH_LONG = 0;
  
  public static final int LENGTH_SHORT = -1;
  
  static final int MSG_DISMISS = 1;
  
  static final int MSG_SHOW = 0;
  
  private static final boolean USE_OFFSET_API;
  
  static final Handler sHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        public boolean handleMessage(Message param1Message) {
          int i = param1Message.what;
          if (i != 0) {
            if (i != 1)
              return false; 
            ((BaseTransientBottomBar)param1Message.obj).hideView(param1Message.arg1);
            return true;
          } 
          ((BaseTransientBottomBar)param1Message.obj).showView();
          return true;
        }
      });
  
  private final AccessibilityManager mAccessibilityManager;
  
  private List<BaseCallback<B>> mCallbacks;
  
  private final ContentViewCallback mContentViewCallback;
  
  private final Context mContext;
  
  private int mDuration;
  
  final SnackbarManager.Callback mManagerCallback = new SnackbarManager.Callback() {
      public void dismiss(int param1Int) {
        BaseTransientBottomBar.sHandler.sendMessage(BaseTransientBottomBar.sHandler.obtainMessage(1, param1Int, 0, BaseTransientBottomBar.this));
      }
      
      public void show() {
        BaseTransientBottomBar.sHandler.sendMessage(BaseTransientBottomBar.sHandler.obtainMessage(0, BaseTransientBottomBar.this));
      }
    };
  
  private final ViewGroup mTargetParent;
  
  final SnackbarBaseLayout mView;
  
  protected BaseTransientBottomBar(ViewGroup paramViewGroup, View paramView, ContentViewCallback paramContentViewCallback) {
    if (paramViewGroup != null) {
      if (paramView != null) {
        if (paramContentViewCallback != null) {
          this.mTargetParent = paramViewGroup;
          this.mContentViewCallback = paramContentViewCallback;
          Context context = paramViewGroup.getContext();
          this.mContext = context;
          ThemeUtils.checkAppCompatTheme(context);
          SnackbarBaseLayout snackbarBaseLayout = (SnackbarBaseLayout)LayoutInflater.from(this.mContext).inflate(R.layout.design_layout_snackbar, this.mTargetParent, false);
          this.mView = snackbarBaseLayout;
          snackbarBaseLayout.addView(paramView);
          ViewCompat.setAccessibilityLiveRegion((View)this.mView, 1);
          ViewCompat.setImportantForAccessibility((View)this.mView, 1);
          ViewCompat.setFitsSystemWindows((View)this.mView, true);
          ViewCompat.setOnApplyWindowInsetsListener((View)this.mView, new OnApplyWindowInsetsListener() {
                public WindowInsetsCompat onApplyWindowInsets(View param1View, WindowInsetsCompat param1WindowInsetsCompat) {
                  param1View.setPadding(param1View.getPaddingLeft(), param1View.getPaddingTop(), param1View.getPaddingRight(), param1WindowInsetsCompat.getSystemWindowInsetBottom());
                  return param1WindowInsetsCompat;
                }
              });
          this.mAccessibilityManager = (AccessibilityManager)this.mContext.getSystemService("accessibility");
          return;
        } 
        throw new IllegalArgumentException("Transient bottom bar must have non-null callback");
      } 
      throw new IllegalArgumentException("Transient bottom bar must have non-null content");
    } 
    throw new IllegalArgumentException("Transient bottom bar must have non-null parent");
  }
  
  private void animateViewOut(final int event) {
    if (Build.VERSION.SDK_INT >= 12) {
      ValueAnimator valueAnimator = new ValueAnimator();
      valueAnimator.setIntValues(new int[] { 0, this.mView.getHeight() });
      valueAnimator.setInterpolator((TimeInterpolator)AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
      valueAnimator.setDuration(250L);
      valueAnimator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator param1Animator) {
              BaseTransientBottomBar.this.onViewHidden(event);
            }
            
            public void onAnimationStart(Animator param1Animator) {
              BaseTransientBottomBar.this.mContentViewCallback.animateContentOut(0, 180);
            }
          });
      valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private int mPreviousAnimatedIntValue = 0;
            
            public void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
              int i = ((Integer)param1ValueAnimator.getAnimatedValue()).intValue();
              if (BaseTransientBottomBar.USE_OFFSET_API) {
                ViewCompat.offsetTopAndBottom((View)BaseTransientBottomBar.this.mView, i - this.mPreviousAnimatedIntValue);
              } else {
                BaseTransientBottomBar.this.mView.setTranslationY(i);
              } 
              this.mPreviousAnimatedIntValue = i;
            }
          });
      valueAnimator.start();
    } else {
      Animation animation = AnimationUtils.loadAnimation(this.mView.getContext(), R.anim.design_snackbar_out);
      animation.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
      animation.setDuration(250L);
      animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation param1Animation) {
              BaseTransientBottomBar.this.onViewHidden(event);
            }
            
            public void onAnimationRepeat(Animation param1Animation) {}
            
            public void onAnimationStart(Animation param1Animation) {}
          });
      this.mView.startAnimation(animation);
    } 
  }
  
  public B addCallback(BaseCallback<B> paramBaseCallback) {
    if (paramBaseCallback == null)
      return (B)this; 
    if (this.mCallbacks == null)
      this.mCallbacks = new ArrayList<BaseCallback<B>>(); 
    this.mCallbacks.add(paramBaseCallback);
    return (B)this;
  }
  
  void animateViewIn() {
    if (Build.VERSION.SDK_INT >= 12) {
      final int viewHeight = this.mView.getHeight();
      if (USE_OFFSET_API) {
        ViewCompat.offsetTopAndBottom((View)this.mView, i);
      } else {
        this.mView.setTranslationY(i);
      } 
      ValueAnimator valueAnimator = new ValueAnimator();
      valueAnimator.setIntValues(new int[] { i, 0 });
      valueAnimator.setInterpolator((TimeInterpolator)AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
      valueAnimator.setDuration(250L);
      valueAnimator.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator param1Animator) {
              BaseTransientBottomBar.this.onViewShown();
            }
            
            public void onAnimationStart(Animator param1Animator) {
              BaseTransientBottomBar.this.mContentViewCallback.animateContentIn(70, 180);
            }
          });
      valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private int mPreviousAnimatedIntValue = viewHeight;
            
            public void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
              int i = ((Integer)param1ValueAnimator.getAnimatedValue()).intValue();
              if (BaseTransientBottomBar.USE_OFFSET_API) {
                ViewCompat.offsetTopAndBottom((View)BaseTransientBottomBar.this.mView, i - this.mPreviousAnimatedIntValue);
              } else {
                BaseTransientBottomBar.this.mView.setTranslationY(i);
              } 
              this.mPreviousAnimatedIntValue = i;
            }
          });
      valueAnimator.start();
    } else {
      Animation animation = AnimationUtils.loadAnimation(this.mView.getContext(), R.anim.design_snackbar_in);
      animation.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
      animation.setDuration(250L);
      animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation param1Animation) {
              BaseTransientBottomBar.this.onViewShown();
            }
            
            public void onAnimationRepeat(Animation param1Animation) {}
            
            public void onAnimationStart(Animation param1Animation) {}
          });
      this.mView.startAnimation(animation);
    } 
  }
  
  public void dismiss() {
    dispatchDismiss(3);
  }
  
  void dispatchDismiss(int paramInt) {
    SnackbarManager.getInstance().dismiss(this.mManagerCallback, paramInt);
  }
  
  public Context getContext() {
    return this.mContext;
  }
  
  public int getDuration() {
    return this.mDuration;
  }
  
  public View getView() {
    return (View)this.mView;
  }
  
  final void hideView(int paramInt) {
    if (shouldAnimate() && this.mView.getVisibility() == 0) {
      animateViewOut(paramInt);
    } else {
      onViewHidden(paramInt);
    } 
  }
  
  public boolean isShown() {
    return SnackbarManager.getInstance().isCurrent(this.mManagerCallback);
  }
  
  public boolean isShownOrQueued() {
    return SnackbarManager.getInstance().isCurrentOrNext(this.mManagerCallback);
  }
  
  void onViewHidden(int paramInt) {
    SnackbarManager.getInstance().onDismissed(this.mManagerCallback);
    List<BaseCallback<B>> list = this.mCallbacks;
    if (list != null)
      for (int i = list.size() - 1; i >= 0; i--)
        ((BaseCallback<BaseTransientBottomBar>)this.mCallbacks.get(i)).onDismissed(this, paramInt);  
    if (Build.VERSION.SDK_INT < 11)
      this.mView.setVisibility(8); 
    ViewParent viewParent = this.mView.getParent();
    if (viewParent instanceof ViewGroup)
      ((ViewGroup)viewParent).removeView((View)this.mView); 
  }
  
  void onViewShown() {
    SnackbarManager.getInstance().onShown(this.mManagerCallback);
    List<BaseCallback<B>> list = this.mCallbacks;
    if (list != null)
      for (int i = list.size() - 1; i >= 0; i--)
        ((BaseCallback<BaseTransientBottomBar>)this.mCallbacks.get(i)).onShown(this);  
  }
  
  public B removeCallback(BaseCallback<B> paramBaseCallback) {
    if (paramBaseCallback == null)
      return (B)this; 
    List<BaseCallback<B>> list = this.mCallbacks;
    if (list == null)
      return (B)this; 
    list.remove(paramBaseCallback);
    return (B)this;
  }
  
  public B setDuration(int paramInt) {
    this.mDuration = paramInt;
    return (B)this;
  }
  
  boolean shouldAnimate() {
    return this.mAccessibilityManager.isEnabled() ^ true;
  }
  
  public void show() {
    SnackbarManager.getInstance().show(this.mDuration, this.mManagerCallback);
  }
  
  final void showView() {
    if (this.mView.getParent() == null) {
      ViewGroup.LayoutParams layoutParams = this.mView.getLayoutParams();
      if (layoutParams instanceof CoordinatorLayout.LayoutParams) {
        CoordinatorLayout.LayoutParams layoutParams1 = (CoordinatorLayout.LayoutParams)layoutParams;
        Behavior behavior = new Behavior();
        behavior.setStartAlphaSwipeDistance(0.1F);
        behavior.setEndAlphaSwipeDistance(0.6F);
        behavior.setSwipeDirection(0);
        behavior.setListener(new SwipeDismissBehavior.OnDismissListener() {
              public void onDismiss(View param1View) {
                param1View.setVisibility(8);
                BaseTransientBottomBar.this.dispatchDismiss(0);
              }
              
              public void onDragStateChanged(int param1Int) {
                if (param1Int != 0) {
                  if (param1Int == 1 || param1Int == 2)
                    SnackbarManager.getInstance().pauseTimeout(BaseTransientBottomBar.this.mManagerCallback); 
                } else {
                  SnackbarManager.getInstance().restoreTimeoutIfPaused(BaseTransientBottomBar.this.mManagerCallback);
                } 
              }
            });
        layoutParams1.setBehavior(behavior);
        layoutParams1.insetEdge = 80;
      } 
      this.mTargetParent.addView((View)this.mView);
    } 
    this.mView.setOnAttachStateChangeListener(new OnAttachStateChangeListener() {
          public void onViewAttachedToWindow(View param1View) {}
          
          public void onViewDetachedFromWindow(View param1View) {
            if (BaseTransientBottomBar.this.isShownOrQueued())
              BaseTransientBottomBar.sHandler.post(new Runnable() {
                    public void run() {
                      BaseTransientBottomBar.this.onViewHidden(3);
                    }
                  }); 
          }
        });
    if (ViewCompat.isLaidOut((View)this.mView)) {
      if (shouldAnimate()) {
        animateViewIn();
      } else {
        onViewShown();
      } 
    } else {
      this.mView.setOnLayoutChangeListener(new OnLayoutChangeListener() {
            public void onLayoutChange(View param1View, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
              BaseTransientBottomBar.this.mView.setOnLayoutChangeListener((BaseTransientBottomBar.OnLayoutChangeListener)null);
              if (BaseTransientBottomBar.this.shouldAnimate()) {
                BaseTransientBottomBar.this.animateViewIn();
              } else {
                BaseTransientBottomBar.this.onViewShown();
              } 
            }
          });
    } 
  }
  
  static {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT <= 19) {
      bool = true;
    } else {
      bool = false;
    } 
    USE_OFFSET_API = bool;
  }
  
  public static abstract class BaseCallback<B> {
    public static final int DISMISS_EVENT_ACTION = 1;
    
    public static final int DISMISS_EVENT_CONSECUTIVE = 4;
    
    public static final int DISMISS_EVENT_MANUAL = 3;
    
    public static final int DISMISS_EVENT_SWIPE = 0;
    
    public static final int DISMISS_EVENT_TIMEOUT = 2;
    
    public void onDismissed(B param1B, int param1Int) {}
    
    public void onShown(B param1B) {}
    
    @Retention(RetentionPolicy.SOURCE)
    public static @interface DismissEvent {}
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface DismissEvent {}
  
  final class Behavior extends SwipeDismissBehavior<SnackbarBaseLayout> {
    public boolean canSwipeDismissView(View param1View) {
      return param1View instanceof BaseTransientBottomBar.SnackbarBaseLayout;
    }
    
    public boolean onInterceptTouchEvent(CoordinatorLayout param1CoordinatorLayout, BaseTransientBottomBar.SnackbarBaseLayout param1SnackbarBaseLayout, MotionEvent param1MotionEvent) {
      int i = param1MotionEvent.getActionMasked();
      if (i != 0) {
        if (i == 1 || i == 3)
          SnackbarManager.getInstance().restoreTimeoutIfPaused(BaseTransientBottomBar.this.mManagerCallback); 
      } else if (param1CoordinatorLayout.isPointInChildBounds((View)param1SnackbarBaseLayout, (int)param1MotionEvent.getX(), (int)param1MotionEvent.getY())) {
        SnackbarManager.getInstance().pauseTimeout(BaseTransientBottomBar.this.mManagerCallback);
      } 
      return super.onInterceptTouchEvent(param1CoordinatorLayout, param1SnackbarBaseLayout, param1MotionEvent);
    }
  }
  
  public static interface ContentViewCallback {
    void animateContentIn(int param1Int1, int param1Int2);
    
    void animateContentOut(int param1Int1, int param1Int2);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface Duration {}
  
  static interface OnAttachStateChangeListener {
    void onViewAttachedToWindow(View param1View);
    
    void onViewDetachedFromWindow(View param1View);
  }
  
  static interface OnLayoutChangeListener {
    void onLayoutChange(View param1View, int param1Int1, int param1Int2, int param1Int3, int param1Int4);
  }
  
  static class SnackbarBaseLayout extends FrameLayout {
    private BaseTransientBottomBar.OnAttachStateChangeListener mOnAttachStateChangeListener;
    
    private BaseTransientBottomBar.OnLayoutChangeListener mOnLayoutChangeListener;
    
    SnackbarBaseLayout(Context param1Context) {
      this(param1Context, (AttributeSet)null);
    }
    
    SnackbarBaseLayout(Context param1Context, AttributeSet param1AttributeSet) {
      super(param1Context, param1AttributeSet);
      TypedArray typedArray = param1Context.obtainStyledAttributes(param1AttributeSet, R.styleable.SnackbarLayout);
      if (typedArray.hasValue(R.styleable.SnackbarLayout_elevation))
        ViewCompat.setElevation((View)this, typedArray.getDimensionPixelSize(R.styleable.SnackbarLayout_elevation, 0)); 
      typedArray.recycle();
      setClickable(true);
    }
    
    protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      BaseTransientBottomBar.OnAttachStateChangeListener onAttachStateChangeListener = this.mOnAttachStateChangeListener;
      if (onAttachStateChangeListener != null)
        onAttachStateChangeListener.onViewAttachedToWindow((View)this); 
      ViewCompat.requestApplyInsets((View)this);
    }
    
    protected void onDetachedFromWindow() {
      super.onDetachedFromWindow();
      BaseTransientBottomBar.OnAttachStateChangeListener onAttachStateChangeListener = this.mOnAttachStateChangeListener;
      if (onAttachStateChangeListener != null)
        onAttachStateChangeListener.onViewDetachedFromWindow((View)this); 
    }
    
    protected void onLayout(boolean param1Boolean, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      super.onLayout(param1Boolean, param1Int1, param1Int2, param1Int3, param1Int4);
      BaseTransientBottomBar.OnLayoutChangeListener onLayoutChangeListener = this.mOnLayoutChangeListener;
      if (onLayoutChangeListener != null)
        onLayoutChangeListener.onLayoutChange((View)this, param1Int1, param1Int2, param1Int3, param1Int4); 
    }
    
    void setOnAttachStateChangeListener(BaseTransientBottomBar.OnAttachStateChangeListener param1OnAttachStateChangeListener) {
      this.mOnAttachStateChangeListener = param1OnAttachStateChangeListener;
    }
    
    void setOnLayoutChangeListener(BaseTransientBottomBar.OnLayoutChangeListener param1OnLayoutChangeListener) {
      this.mOnLayoutChangeListener = param1OnLayoutChangeListener;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\widget\BaseTransientBottomBar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */