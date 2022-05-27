package io.virtualapp.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Property;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import io.virtualapp.R;

public class MaterialRippleLayout extends FrameLayout {
  private static final float DEFAULT_ALPHA = 0.2F;
  
  private static final int DEFAULT_BACKGROUND = 0;
  
  private static final int DEFAULT_COLOR = -16777216;
  
  private static final boolean DEFAULT_DELAY_CLICK = true;
  
  private static final float DEFAULT_DIAMETER_DP = 35.0F;
  
  private static final int DEFAULT_DURATION = 350;
  
  private static final int DEFAULT_FADE_DURATION = 75;
  
  private static final boolean DEFAULT_HOVER = true;
  
  private static final boolean DEFAULT_PERSISTENT = false;
  
  private static final boolean DEFAULT_RIPPLE_OVERLAY = false;
  
  private static final int DEFAULT_ROUNDED_CORNERS = 0;
  
  private static final boolean DEFAULT_SEARCH_ADAPTER = false;
  
  private static final int FADE_EXTRA_DELAY = 50;
  
  private static final long HOVER_DURATION = 2500L;
  
  private final Rect bounds = new Rect();
  
  private View childView;
  
  private Property<MaterialRippleLayout, Integer> circleAlphaProperty = new Property<MaterialRippleLayout, Integer>(Integer.class, "rippleAlpha") {
      public Integer get(MaterialRippleLayout param1MaterialRippleLayout) {
        return Integer.valueOf(param1MaterialRippleLayout.getRippleAlpha());
      }
      
      public void set(MaterialRippleLayout param1MaterialRippleLayout, Integer param1Integer) {
        param1MaterialRippleLayout.setRippleAlpha(param1Integer);
      }
    };
  
  private Point currentCoords = new Point();
  
  private boolean eventCancelled;
  
  private GestureDetector gestureDetector;
  
  private boolean hasPerformedLongPress;
  
  private ObjectAnimator hoverAnimator;
  
  private int layerType;
  
  private GestureDetector.SimpleOnGestureListener longClickListener = new GestureDetector.SimpleOnGestureListener() {
      public boolean onDown(MotionEvent param1MotionEvent) {
        MaterialRippleLayout.access$102(MaterialRippleLayout.this, false);
        return super.onDown(param1MotionEvent);
      }
      
      public void onLongPress(MotionEvent param1MotionEvent) {
        MaterialRippleLayout materialRippleLayout = MaterialRippleLayout.this;
        MaterialRippleLayout.access$102(materialRippleLayout, materialRippleLayout.childView.performLongClick());
        if (MaterialRippleLayout.this.hasPerformedLongPress) {
          if (MaterialRippleLayout.this.rippleHover)
            MaterialRippleLayout.this.startRipple((Runnable)null); 
          MaterialRippleLayout.this.cancelPressedEvent();
        } 
      }
    };
  
  private final Paint paint = new Paint(1);
  
  private AdapterView parentAdapter;
  
  private PerformClickEvent pendingClickEvent;
  
  private PressedEvent pendingPressEvent;
  
  private int positionInAdapter;
  
  private boolean prepressed;
  
  private Point previousCoords = new Point();
  
  private float radius;
  
  private Property<MaterialRippleLayout, Float> radiusProperty = new Property<MaterialRippleLayout, Float>(Float.class, "radius") {
      public Float get(MaterialRippleLayout param1MaterialRippleLayout) {
        return Float.valueOf(param1MaterialRippleLayout.getRadius());
      }
      
      public void set(MaterialRippleLayout param1MaterialRippleLayout, Float param1Float) {
        param1MaterialRippleLayout.setRadius(param1Float.floatValue());
      }
    };
  
  private int rippleAlpha;
  
  private AnimatorSet rippleAnimator;
  
  private Drawable rippleBackground;
  
  private int rippleColor;
  
  private boolean rippleDelayClick;
  
  private int rippleDiameter;
  
  private int rippleDuration;
  
  private int rippleFadeDuration;
  
  private boolean rippleHover;
  
  private boolean rippleInAdapter;
  
  private boolean rippleOverlay;
  
  private boolean ripplePersistent;
  
  private float rippleRoundedCorners;
  
  public MaterialRippleLayout(Context paramContext) {
    this(paramContext, (AttributeSet)null, 0);
  }
  
  public MaterialRippleLayout(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public MaterialRippleLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    setWillNotDraw(false);
    this.gestureDetector = new GestureDetector(paramContext, (GestureDetector.OnGestureListener)this.longClickListener);
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.MaterialRippleLayout);
    this.rippleColor = typedArray.getColor(2, -16777216);
    this.rippleDiameter = typedArray.getDimensionPixelSize(4, (int)dpToPx(getResources(), 35.0F));
    this.rippleOverlay = typedArray.getBoolean(9, false);
    this.rippleHover = typedArray.getBoolean(7, true);
    this.rippleDuration = typedArray.getInt(5, 350);
    this.rippleAlpha = (int)(typedArray.getFloat(0, 0.2F) * 255.0F);
    this.rippleDelayClick = typedArray.getBoolean(3, true);
    this.rippleFadeDuration = typedArray.getInteger(6, 75);
    this.rippleBackground = (Drawable)new ColorDrawable(typedArray.getColor(1, 0));
    this.ripplePersistent = typedArray.getBoolean(10, false);
    this.rippleInAdapter = typedArray.getBoolean(8, false);
    this.rippleRoundedCorners = typedArray.getDimensionPixelSize(11, 0);
    typedArray.recycle();
    this.paint.setColor(this.rippleColor);
    this.paint.setAlpha(this.rippleAlpha);
    enableClipPathSupportIfNecessary();
  }
  
  private boolean adapterPositionChanged() {
    if (this.rippleInAdapter) {
      boolean bool;
      int i = findParentAdapterView().getPositionForView((View)this);
      if (i != this.positionInAdapter) {
        bool = true;
      } else {
        bool = false;
      } 
      this.positionInAdapter = i;
      if (bool) {
        cancelPressedEvent();
        cancelAnimations();
        this.childView.setPressed(false);
        setRadius(0.0F);
      } 
      return bool;
    } 
    return false;
  }
  
  private void cancelAnimations() {
    AnimatorSet animatorSet = this.rippleAnimator;
    if (animatorSet != null) {
      animatorSet.cancel();
      this.rippleAnimator.removeAllListeners();
    } 
    ObjectAnimator objectAnimator = this.hoverAnimator;
    if (objectAnimator != null)
      objectAnimator.cancel(); 
  }
  
  private void cancelPressedEvent() {
    PressedEvent pressedEvent = this.pendingPressEvent;
    if (pressedEvent != null) {
      removeCallbacks(pressedEvent);
      this.prepressed = false;
    } 
  }
  
  static float dpToPx(Resources paramResources, float paramFloat) {
    return TypedValue.applyDimension(1, paramFloat, paramResources.getDisplayMetrics());
  }
  
  private void enableClipPathSupportIfNecessary() {
    if (Build.VERSION.SDK_INT <= 17)
      if (this.rippleRoundedCorners != 0.0F) {
        this.layerType = getLayerType();
        setLayerType(1, null);
      } else {
        setLayerType(this.layerType, null);
      }  
  }
  
  private boolean findClickableViewInChild(View paramView, int paramInt1, int paramInt2) {
    boolean bool = paramView instanceof ViewGroup;
    boolean bool1 = false;
    byte b = 0;
    if (bool) {
      ViewGroup viewGroup = (ViewGroup)paramView;
      while (b < viewGroup.getChildCount()) {
        View view = viewGroup.getChildAt(b);
        Rect rect = new Rect();
        view.getHitRect(rect);
        if (rect.contains(paramInt1, paramInt2))
          return findClickableViewInChild(view, paramInt1 - rect.left, paramInt2 - rect.top); 
        b++;
      } 
    } else {
      if (paramView != this.childView) {
        bool = bool1;
        if (paramView.isEnabled()) {
          if (!paramView.isClickable() && !paramView.isLongClickable()) {
            bool = bool1;
            if (paramView.isFocusableInTouchMode())
              bool = true; 
            return bool;
          } 
        } else {
          return bool;
        } 
      } else {
        return paramView.isFocusableInTouchMode();
      } 
      bool = true;
    } 
    return paramView.isFocusableInTouchMode();
  }
  
  private AdapterView findParentAdapterView() {
    AdapterView adapterView = this.parentAdapter;
    if (adapterView != null)
      return adapterView; 
    ViewParent viewParent = getParent();
    while (true) {
      AdapterView adapterView1;
      if (viewParent instanceof AdapterView) {
        adapterView1 = (AdapterView)viewParent;
        this.parentAdapter = adapterView1;
        return adapterView1;
      } 
      try {
        ViewParent viewParent1 = adapterView1.getParent();
      } catch (NullPointerException nullPointerException) {
        throw new RuntimeException("Could not find a parent AdapterView");
      } 
    } 
  }
  
  private float getEndRadius() {
    int i = getWidth();
    int j = getHeight();
    int k = i / 2;
    int m = j / 2;
    if (k > this.currentCoords.x) {
      i -= this.currentCoords.x;
    } else {
      i = this.currentCoords.x;
    } 
    float f1 = i;
    if (m > this.currentCoords.y) {
      i = j - this.currentCoords.y;
    } else {
      i = this.currentCoords.y;
    } 
    float f2 = i;
    return (float)Math.sqrt(Math.pow(f1, 2.0D) + Math.pow(f2, 2.0D)) * 1.2F;
  }
  
  private float getRadius() {
    return this.radius;
  }
  
  private boolean isInScrollingContainer() {
    for (ViewParent viewParent = getParent(); viewParent != null && viewParent instanceof ViewGroup; viewParent = viewParent.getParent()) {
      if (((ViewGroup)viewParent).shouldDelayChildPressedState())
        return true; 
    } 
    return false;
  }
  
  public static RippleBuilder on(View paramView) {
    return new RippleBuilder(paramView);
  }
  
  private void setPositionInAdapter() {
    if (this.rippleInAdapter)
      this.positionInAdapter = findParentAdapterView().getPositionForView((View)this); 
  }
  
  private void startHover() {
    if (this.eventCancelled)
      return; 
    ObjectAnimator objectAnimator = this.hoverAnimator;
    if (objectAnimator != null)
      objectAnimator.cancel(); 
    float f = (float)(Math.sqrt(Math.pow(getWidth(), 2.0D) + Math.pow(getHeight(), 2.0D)) * 1.2000000476837158D);
    objectAnimator = ObjectAnimator.ofFloat(this, this.radiusProperty, new float[] { this.rippleDiameter, f }).setDuration(2500L);
    this.hoverAnimator = objectAnimator;
    objectAnimator.setInterpolator((TimeInterpolator)new LinearInterpolator());
    this.hoverAnimator.start();
  }
  
  private void startRipple(final Runnable animationEndRunnable) {
    if (this.eventCancelled)
      return; 
    float f = getEndRadius();
    cancelAnimations();
    AnimatorSet animatorSet = new AnimatorSet();
    this.rippleAnimator = animatorSet;
    animatorSet.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
          public void onAnimationEnd(Animator param1Animator) {
            if (!MaterialRippleLayout.this.ripplePersistent) {
              MaterialRippleLayout.this.setRadius(0.0F);
              MaterialRippleLayout materialRippleLayout = MaterialRippleLayout.this;
              materialRippleLayout.setRippleAlpha(Integer.valueOf(materialRippleLayout.rippleAlpha));
            } 
            if (animationEndRunnable != null && MaterialRippleLayout.this.rippleDelayClick)
              animationEndRunnable.run(); 
            MaterialRippleLayout.this.childView.setPressed(false);
          }
        });
    ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(this, this.radiusProperty, new float[] { this.radius, f });
    objectAnimator1.setDuration(this.rippleDuration);
    objectAnimator1.setInterpolator((TimeInterpolator)new DecelerateInterpolator());
    ObjectAnimator objectAnimator2 = ObjectAnimator.ofInt(this, this.circleAlphaProperty, new int[] { this.rippleAlpha, 0 });
    objectAnimator2.setDuration(this.rippleFadeDuration);
    objectAnimator2.setInterpolator((TimeInterpolator)new AccelerateInterpolator());
    objectAnimator2.setStartDelay((this.rippleDuration - this.rippleFadeDuration - 50));
    if (this.ripplePersistent) {
      this.rippleAnimator.play((Animator)objectAnimator1);
    } else if (getRadius() > f) {
      objectAnimator2.setStartDelay(0L);
      this.rippleAnimator.play((Animator)objectAnimator2);
    } else {
      this.rippleAnimator.playTogether(new Animator[] { (Animator)objectAnimator1, (Animator)objectAnimator2 });
    } 
    this.rippleAnimator.start();
  }
  
  public final void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams) {
    if (getChildCount() <= 0) {
      this.childView = paramView;
      super.addView(paramView, paramInt, paramLayoutParams);
      return;
    } 
    throw new IllegalStateException("MaterialRippleLayout can host only one child");
  }
  
  public void draw(Canvas paramCanvas) {
    boolean bool = adapterPositionChanged();
    if (this.rippleOverlay) {
      if (!bool)
        this.rippleBackground.draw(paramCanvas); 
      super.draw(paramCanvas);
      if (!bool) {
        if (this.rippleRoundedCorners != 0.0F) {
          Path path = new Path();
          RectF rectF = new RectF(0.0F, 0.0F, paramCanvas.getWidth(), paramCanvas.getHeight());
          float f = this.rippleRoundedCorners;
          path.addRoundRect(rectF, f, f, Path.Direction.CW);
          paramCanvas.clipPath(path);
        } 
        paramCanvas.drawCircle(this.currentCoords.x, this.currentCoords.y, this.radius, this.paint);
      } 
    } else {
      if (!bool) {
        this.rippleBackground.draw(paramCanvas);
        paramCanvas.drawCircle(this.currentCoords.x, this.currentCoords.y, this.radius, this.paint);
      } 
      super.draw(paramCanvas);
    } 
  }
  
  public <T extends View> T getChildView() {
    return (T)this.childView;
  }
  
  public int getRippleAlpha() {
    return this.paint.getAlpha();
  }
  
  public boolean isInEditMode() {
    return true;
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent) {
    return findClickableViewInChild(this.childView, (int)paramMotionEvent.getX(), (int)paramMotionEvent.getY()) ^ true;
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    this.bounds.set(0, 0, paramInt1, paramInt2);
    this.rippleBackground.setBounds(this.bounds);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    boolean bool = super.onTouchEvent(paramMotionEvent);
    if (!isEnabled() || !this.childView.isEnabled())
      return bool; 
    bool = this.bounds.contains((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY());
    if (bool) {
      this.previousCoords.set(this.currentCoords.x, this.currentCoords.y);
      this.currentCoords.set((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY());
    } 
    if (!this.gestureDetector.onTouchEvent(paramMotionEvent) && !this.hasPerformedLongPress) {
      int i = paramMotionEvent.getActionMasked();
      if (i != 0) {
        if (i != 1) {
          if (i != 2) {
            if (i == 3) {
              if (this.rippleInAdapter) {
                this.currentCoords.set(this.previousCoords.x, this.previousCoords.y);
                this.previousCoords = new Point();
              } 
              this.childView.onTouchEvent(paramMotionEvent);
              if (this.rippleHover) {
                if (!this.prepressed)
                  startRipple((Runnable)null); 
              } else {
                this.childView.setPressed(false);
              } 
              cancelPressedEvent();
            } 
          } else {
            if (this.rippleHover)
              if (bool && !this.eventCancelled) {
                invalidate();
              } else if (!bool) {
                startRipple((Runnable)null);
              }  
            if (!bool) {
              cancelPressedEvent();
              ObjectAnimator objectAnimator = this.hoverAnimator;
              if (objectAnimator != null)
                objectAnimator.cancel(); 
              this.childView.onTouchEvent(paramMotionEvent);
              this.eventCancelled = true;
            } 
          } 
        } else {
          this.pendingClickEvent = new PerformClickEvent();
          if (this.prepressed) {
            this.childView.setPressed(true);
            postDelayed(new Runnable() {
                  public void run() {
                    MaterialRippleLayout.this.childView.setPressed(false);
                  }
                },  ViewConfiguration.getPressedStateDuration());
          } 
          if (bool) {
            startRipple(this.pendingClickEvent);
          } else if (!this.rippleHover) {
            setRadius(0.0F);
          } 
          if (!this.rippleDelayClick && bool)
            this.pendingClickEvent.run(); 
          cancelPressedEvent();
        } 
      } else {
        setPositionInAdapter();
        this.eventCancelled = false;
        this.pendingPressEvent = new PressedEvent(paramMotionEvent);
        if (isInScrollingContainer()) {
          cancelPressedEvent();
          this.prepressed = true;
          postDelayed(this.pendingPressEvent, ViewConfiguration.getTapTimeout());
        } else {
          this.pendingPressEvent.run();
        } 
      } 
    } 
    return true;
  }
  
  public void performRipple() {
    this.currentCoords = new Point(getWidth() / 2, getHeight() / 2);
    startRipple((Runnable)null);
  }
  
  public void performRipple(Point paramPoint) {
    this.currentCoords = new Point(paramPoint.x, paramPoint.y);
    startRipple((Runnable)null);
  }
  
  public void setDefaultRippleAlpha(float paramFloat) {
    int i = (int)(paramFloat * 255.0F);
    this.rippleAlpha = i;
    this.paint.setAlpha(i);
    invalidate();
  }
  
  public void setOnClickListener(View.OnClickListener paramOnClickListener) {
    View view = this.childView;
    if (view != null) {
      view.setOnClickListener(paramOnClickListener);
      return;
    } 
    throw new IllegalStateException("MaterialRippleLayout must have a child view to handle clicks");
  }
  
  public void setOnLongClickListener(View.OnLongClickListener paramOnLongClickListener) {
    View view = this.childView;
    if (view != null) {
      view.setOnLongClickListener(paramOnLongClickListener);
      return;
    } 
    throw new IllegalStateException("MaterialRippleLayout must have a child view to handle clicks");
  }
  
  public void setRadius(float paramFloat) {
    this.radius = paramFloat;
    invalidate();
  }
  
  public void setRippleAlpha(Integer paramInteger) {
    this.paint.setAlpha(paramInteger.intValue());
    invalidate();
  }
  
  public void setRippleBackground(int paramInt) {
    ColorDrawable colorDrawable = new ColorDrawable(paramInt);
    this.rippleBackground = (Drawable)colorDrawable;
    colorDrawable.setBounds(this.bounds);
    invalidate();
  }
  
  public void setRippleColor(int paramInt) {
    this.rippleColor = paramInt;
    this.paint.setColor(paramInt);
    this.paint.setAlpha(this.rippleAlpha);
    invalidate();
  }
  
  public void setRippleDelayClick(boolean paramBoolean) {
    this.rippleDelayClick = paramBoolean;
  }
  
  public void setRippleDiameter(int paramInt) {
    this.rippleDiameter = paramInt;
  }
  
  public void setRippleDuration(int paramInt) {
    this.rippleDuration = paramInt;
  }
  
  public void setRippleFadeDuration(int paramInt) {
    this.rippleFadeDuration = paramInt;
  }
  
  public void setRippleHover(boolean paramBoolean) {
    this.rippleHover = paramBoolean;
  }
  
  public void setRippleInAdapter(boolean paramBoolean) {
    this.rippleInAdapter = paramBoolean;
  }
  
  public void setRippleOverlay(boolean paramBoolean) {
    this.rippleOverlay = paramBoolean;
  }
  
  public void setRipplePersistent(boolean paramBoolean) {
    this.ripplePersistent = paramBoolean;
  }
  
  public void setRippleRoundedCorners(int paramInt) {
    this.rippleRoundedCorners = paramInt;
    enableClipPathSupportIfNecessary();
  }
  
  private class PerformClickEvent implements Runnable {
    private PerformClickEvent() {}
    
    private void clickAdapterView(AdapterView param1AdapterView) {
      long l;
      int i = param1AdapterView.getPositionForView((View)MaterialRippleLayout.this);
      if (param1AdapterView.getAdapter() != null) {
        l = param1AdapterView.getAdapter().getItemId(i);
      } else {
        l = 0L;
      } 
      if (i != -1)
        param1AdapterView.performItemClick((View)MaterialRippleLayout.this, i, l); 
    }
    
    public void run() {
      if (MaterialRippleLayout.this.hasPerformedLongPress)
        return; 
      if (MaterialRippleLayout.this.getParent() instanceof AdapterView) {
        if (!MaterialRippleLayout.this.childView.performClick())
          clickAdapterView((AdapterView)MaterialRippleLayout.this.getParent()); 
      } else if (MaterialRippleLayout.this.rippleInAdapter) {
        clickAdapterView(MaterialRippleLayout.this.findParentAdapterView());
      } else {
        MaterialRippleLayout.this.childView.performClick();
      } 
    }
  }
  
  private final class PressedEvent implements Runnable {
    private final MotionEvent event;
    
    public PressedEvent(MotionEvent param1MotionEvent) {
      this.event = param1MotionEvent;
    }
    
    public void run() {
      MaterialRippleLayout.access$1202(MaterialRippleLayout.this, false);
      MaterialRippleLayout.this.childView.setLongClickable(false);
      MaterialRippleLayout.this.childView.onTouchEvent(this.event);
      MaterialRippleLayout.this.childView.setPressed(true);
      if (MaterialRippleLayout.this.rippleHover)
        MaterialRippleLayout.this.startHover(); 
    }
  }
  
  public static class RippleBuilder {
    private final View child;
    
    private final Context context;
    
    private float rippleAlpha = 0.2F;
    
    private int rippleBackground = 0;
    
    private int rippleColor = -16777216;
    
    private boolean rippleDelayClick = true;
    
    private float rippleDiameter = 35.0F;
    
    private int rippleDuration = 350;
    
    private int rippleFadeDuration = 75;
    
    private boolean rippleHover = true;
    
    private boolean rippleOverlay = false;
    
    private boolean ripplePersistent = false;
    
    private float rippleRoundedCorner = 0.0F;
    
    private boolean rippleSearchAdapter = false;
    
    public RippleBuilder(View param1View) {
      this.child = param1View;
      this.context = param1View.getContext();
    }
    
    public MaterialRippleLayout create() {
      MaterialRippleLayout materialRippleLayout = new MaterialRippleLayout(this.context);
      materialRippleLayout.setRippleColor(this.rippleColor);
      materialRippleLayout.setDefaultRippleAlpha(this.rippleAlpha);
      materialRippleLayout.setRippleDelayClick(this.rippleDelayClick);
      materialRippleLayout.setRippleDiameter((int)MaterialRippleLayout.dpToPx(this.context.getResources(), this.rippleDiameter));
      materialRippleLayout.setRippleDuration(this.rippleDuration);
      materialRippleLayout.setRippleFadeDuration(this.rippleFadeDuration);
      materialRippleLayout.setRippleHover(this.rippleHover);
      materialRippleLayout.setRipplePersistent(this.ripplePersistent);
      materialRippleLayout.setRippleOverlay(this.rippleOverlay);
      materialRippleLayout.setRippleBackground(this.rippleBackground);
      materialRippleLayout.setRippleInAdapter(this.rippleSearchAdapter);
      materialRippleLayout.setRippleRoundedCorners((int)MaterialRippleLayout.dpToPx(this.context.getResources(), this.rippleRoundedCorner));
      ViewGroup.LayoutParams layoutParams = this.child.getLayoutParams();
      ViewGroup viewGroup = (ViewGroup)this.child.getParent();
      if (viewGroup == null || !(viewGroup instanceof MaterialRippleLayout)) {
        boolean bool;
        if (viewGroup != null) {
          bool = viewGroup.indexOfChild(this.child);
          viewGroup.removeView(this.child);
        } else {
          bool = false;
        } 
        materialRippleLayout.addView(this.child, new ViewGroup.LayoutParams(-1, -1));
        if (viewGroup != null)
          viewGroup.addView((View)materialRippleLayout, bool, layoutParams); 
        return materialRippleLayout;
      } 
      throw new IllegalStateException("MaterialRippleLayout could not be created: parent of the view already is a MaterialRippleLayout");
    }
    
    public RippleBuilder rippleAlpha(float param1Float) {
      this.rippleAlpha = param1Float;
      return this;
    }
    
    public RippleBuilder rippleBackground(int param1Int) {
      this.rippleBackground = param1Int;
      return this;
    }
    
    public RippleBuilder rippleColor(int param1Int) {
      this.rippleColor = param1Int;
      return this;
    }
    
    public RippleBuilder rippleDelayClick(boolean param1Boolean) {
      this.rippleDelayClick = param1Boolean;
      return this;
    }
    
    public RippleBuilder rippleDiameterDp(int param1Int) {
      this.rippleDiameter = param1Int;
      return this;
    }
    
    public RippleBuilder rippleDuration(int param1Int) {
      this.rippleDuration = param1Int;
      return this;
    }
    
    public RippleBuilder rippleFadeDuration(int param1Int) {
      this.rippleFadeDuration = param1Int;
      return this;
    }
    
    public RippleBuilder rippleHover(boolean param1Boolean) {
      this.rippleHover = param1Boolean;
      return this;
    }
    
    public RippleBuilder rippleInAdapter(boolean param1Boolean) {
      this.rippleSearchAdapter = param1Boolean;
      return this;
    }
    
    public RippleBuilder rippleOverlay(boolean param1Boolean) {
      this.rippleOverlay = param1Boolean;
      return this;
    }
    
    public RippleBuilder ripplePersistent(boolean param1Boolean) {
      this.ripplePersistent = param1Boolean;
      return this;
    }
    
    public RippleBuilder rippleRoundedCorners(int param1Int) {
      this.rippleRoundedCorner = param1Int;
      return this;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\io\virtualapp\widgets\MaterialRippleLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */