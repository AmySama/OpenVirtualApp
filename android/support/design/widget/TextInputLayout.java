package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.R;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.Space;
import android.support.v4.widget.TextViewCompat;
import android.support.v4.widget.ViewGroupUtils;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.WithHint;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TextInputLayout extends LinearLayout implements WithHint {
  private static final int ANIMATION_DURATION = 200;
  
  private static final int INVALID_MAX_LENGTH = -1;
  
  private static final String LOG_TAG = "TextInputLayout";
  
  private ValueAnimator mAnimator;
  
  final CollapsingTextHelper mCollapsingTextHelper = new CollapsingTextHelper((View)this);
  
  boolean mCounterEnabled;
  
  private int mCounterMaxLength;
  
  private int mCounterOverflowTextAppearance;
  
  private boolean mCounterOverflowed;
  
  private int mCounterTextAppearance;
  
  private TextView mCounterView;
  
  private ColorStateList mDefaultTextColor;
  
  EditText mEditText;
  
  private CharSequence mError;
  
  private boolean mErrorEnabled;
  
  private boolean mErrorShown;
  
  private int mErrorTextAppearance;
  
  TextView mErrorView;
  
  private ColorStateList mFocusedTextColor;
  
  private boolean mHasPasswordToggleTintList;
  
  private boolean mHasPasswordToggleTintMode;
  
  private boolean mHasReconstructedEditTextBackground;
  
  private CharSequence mHint;
  
  private boolean mHintAnimationEnabled;
  
  private boolean mHintEnabled;
  
  private boolean mHintExpanded;
  
  private boolean mInDrawableStateChanged;
  
  private LinearLayout mIndicatorArea;
  
  private int mIndicatorsAdded;
  
  private final FrameLayout mInputFrame;
  
  private Drawable mOriginalEditTextEndDrawable;
  
  private CharSequence mOriginalHint;
  
  private CharSequence mPasswordToggleContentDesc;
  
  private Drawable mPasswordToggleDrawable;
  
  private Drawable mPasswordToggleDummyDrawable;
  
  private boolean mPasswordToggleEnabled;
  
  private ColorStateList mPasswordToggleTintList;
  
  private PorterDuff.Mode mPasswordToggleTintMode;
  
  private CheckableImageButton mPasswordToggleView;
  
  private boolean mPasswordToggledVisible;
  
  private boolean mRestoringSavedState;
  
  private Paint mTmpPaint;
  
  private final Rect mTmpRect = new Rect();
  
  private Typeface mTypeface;
  
  public TextInputLayout(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public TextInputLayout(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public TextInputLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet);
    ThemeUtils.checkAppCompatTheme(paramContext);
    setOrientation(1);
    setWillNotDraw(false);
    setAddStatesFromChildren(true);
    FrameLayout frameLayout = new FrameLayout(paramContext);
    this.mInputFrame = frameLayout;
    frameLayout.setAddStatesFromChildren(true);
    addView((View)this.mInputFrame);
    this.mCollapsingTextHelper.setTextSizeInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
    this.mCollapsingTextHelper.setPositionInterpolator((Interpolator)new AccelerateInterpolator());
    this.mCollapsingTextHelper.setCollapsedTextGravity(8388659);
    TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(paramContext, paramAttributeSet, R.styleable.TextInputLayout, paramInt, R.style.Widget_Design_TextInputLayout);
    this.mHintEnabled = tintTypedArray.getBoolean(R.styleable.TextInputLayout_hintEnabled, true);
    setHint(tintTypedArray.getText(R.styleable.TextInputLayout_android_hint));
    this.mHintAnimationEnabled = tintTypedArray.getBoolean(R.styleable.TextInputLayout_hintAnimationEnabled, true);
    if (tintTypedArray.hasValue(R.styleable.TextInputLayout_android_textColorHint)) {
      ColorStateList colorStateList = tintTypedArray.getColorStateList(R.styleable.TextInputLayout_android_textColorHint);
      this.mFocusedTextColor = colorStateList;
      this.mDefaultTextColor = colorStateList;
    } 
    if (tintTypedArray.getResourceId(R.styleable.TextInputLayout_hintTextAppearance, -1) != -1)
      setHintTextAppearance(tintTypedArray.getResourceId(R.styleable.TextInputLayout_hintTextAppearance, 0)); 
    this.mErrorTextAppearance = tintTypedArray.getResourceId(R.styleable.TextInputLayout_errorTextAppearance, 0);
    boolean bool1 = tintTypedArray.getBoolean(R.styleable.TextInputLayout_errorEnabled, false);
    boolean bool2 = tintTypedArray.getBoolean(R.styleable.TextInputLayout_counterEnabled, false);
    setCounterMaxLength(tintTypedArray.getInt(R.styleable.TextInputLayout_counterMaxLength, -1));
    this.mCounterTextAppearance = tintTypedArray.getResourceId(R.styleable.TextInputLayout_counterTextAppearance, 0);
    this.mCounterOverflowTextAppearance = tintTypedArray.getResourceId(R.styleable.TextInputLayout_counterOverflowTextAppearance, 0);
    this.mPasswordToggleEnabled = tintTypedArray.getBoolean(R.styleable.TextInputLayout_passwordToggleEnabled, false);
    this.mPasswordToggleDrawable = tintTypedArray.getDrawable(R.styleable.TextInputLayout_passwordToggleDrawable);
    this.mPasswordToggleContentDesc = tintTypedArray.getText(R.styleable.TextInputLayout_passwordToggleContentDescription);
    if (tintTypedArray.hasValue(R.styleable.TextInputLayout_passwordToggleTint)) {
      this.mHasPasswordToggleTintList = true;
      this.mPasswordToggleTintList = tintTypedArray.getColorStateList(R.styleable.TextInputLayout_passwordToggleTint);
    } 
    if (tintTypedArray.hasValue(R.styleable.TextInputLayout_passwordToggleTintMode)) {
      this.mHasPasswordToggleTintMode = true;
      this.mPasswordToggleTintMode = ViewUtils.parseTintMode(tintTypedArray.getInt(R.styleable.TextInputLayout_passwordToggleTintMode, -1), null);
    } 
    tintTypedArray.recycle();
    setErrorEnabled(bool1);
    setCounterEnabled(bool2);
    applyPasswordToggleTint();
    if (ViewCompat.getImportantForAccessibility((View)this) == 0)
      ViewCompat.setImportantForAccessibility((View)this, 1); 
    ViewCompat.setAccessibilityDelegate((View)this, new TextInputAccessibilityDelegate());
  }
  
  private void addIndicator(TextView paramTextView, int paramInt) {
    if (this.mIndicatorArea == null) {
      LinearLayout linearLayout = new LinearLayout(getContext());
      this.mIndicatorArea = linearLayout;
      linearLayout.setOrientation(0);
      addView((View)this.mIndicatorArea, -1, -2);
      Space space = new Space(getContext());
      LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, 0, 1.0F);
      this.mIndicatorArea.addView((View)space, (ViewGroup.LayoutParams)layoutParams);
      if (this.mEditText != null)
        adjustIndicatorPadding(); 
    } 
    this.mIndicatorArea.setVisibility(0);
    this.mIndicatorArea.addView((View)paramTextView, paramInt);
    this.mIndicatorsAdded++;
  }
  
  private void adjustIndicatorPadding() {
    ViewCompat.setPaddingRelative((View)this.mIndicatorArea, ViewCompat.getPaddingStart((View)this.mEditText), 0, ViewCompat.getPaddingEnd((View)this.mEditText), this.mEditText.getPaddingBottom());
  }
  
  private void applyPasswordToggleTint() {
    if (this.mPasswordToggleDrawable != null && (this.mHasPasswordToggleTintList || this.mHasPasswordToggleTintMode)) {
      Drawable drawable = DrawableCompat.wrap(this.mPasswordToggleDrawable).mutate();
      this.mPasswordToggleDrawable = drawable;
      if (this.mHasPasswordToggleTintList)
        DrawableCompat.setTintList(drawable, this.mPasswordToggleTintList); 
      if (this.mHasPasswordToggleTintMode)
        DrawableCompat.setTintMode(this.mPasswordToggleDrawable, this.mPasswordToggleTintMode); 
      CheckableImageButton checkableImageButton = this.mPasswordToggleView;
      if (checkableImageButton != null) {
        Drawable drawable2 = checkableImageButton.getDrawable();
        Drawable drawable1 = this.mPasswordToggleDrawable;
        if (drawable2 != drawable1)
          this.mPasswordToggleView.setImageDrawable(drawable1); 
      } 
    } 
  }
  
  private static boolean arrayContains(int[] paramArrayOfint, int paramInt) {
    int i = paramArrayOfint.length;
    for (byte b = 0; b < i; b++) {
      if (paramArrayOfint[b] == paramInt)
        return true; 
    } 
    return false;
  }
  
  private void collapseHint(boolean paramBoolean) {
    ValueAnimator valueAnimator = this.mAnimator;
    if (valueAnimator != null && valueAnimator.isRunning())
      this.mAnimator.cancel(); 
    if (paramBoolean && this.mHintAnimationEnabled) {
      animateToExpansionFraction(1.0F);
    } else {
      this.mCollapsingTextHelper.setExpansionFraction(1.0F);
    } 
    this.mHintExpanded = false;
  }
  
  private void ensureBackgroundDrawableStateWorkaround() {
    int i = Build.VERSION.SDK_INT;
    if (i != 21 && i != 22)
      return; 
    Drawable drawable = this.mEditText.getBackground();
    if (drawable == null)
      return; 
    if (!this.mHasReconstructedEditTextBackground) {
      Drawable drawable1 = drawable.getConstantState().newDrawable();
      if (drawable instanceof DrawableContainer)
        this.mHasReconstructedEditTextBackground = DrawableUtils.setContainerConstantState((DrawableContainer)drawable, drawable1.getConstantState()); 
      if (!this.mHasReconstructedEditTextBackground) {
        ViewCompat.setBackground((View)this.mEditText, drawable1);
        this.mHasReconstructedEditTextBackground = true;
      } 
    } 
  }
  
  private void expandHint(boolean paramBoolean) {
    ValueAnimator valueAnimator = this.mAnimator;
    if (valueAnimator != null && valueAnimator.isRunning())
      this.mAnimator.cancel(); 
    if (paramBoolean && this.mHintAnimationEnabled) {
      animateToExpansionFraction(0.0F);
    } else {
      this.mCollapsingTextHelper.setExpansionFraction(0.0F);
    } 
    this.mHintExpanded = true;
  }
  
  private boolean hasPasswordTransformation() {
    boolean bool;
    EditText editText = this.mEditText;
    if (editText != null && editText.getTransformationMethod() instanceof PasswordTransformationMethod) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private void passwordVisibilityToggleRequested(boolean paramBoolean) {
    if (this.mPasswordToggleEnabled) {
      int i = this.mEditText.getSelectionEnd();
      if (hasPasswordTransformation()) {
        this.mEditText.setTransformationMethod(null);
        this.mPasswordToggledVisible = true;
      } else {
        this.mEditText.setTransformationMethod((TransformationMethod)PasswordTransformationMethod.getInstance());
        this.mPasswordToggledVisible = false;
      } 
      this.mPasswordToggleView.setChecked(this.mPasswordToggledVisible);
      if (paramBoolean)
        this.mPasswordToggleView.jumpDrawablesToCurrentState(); 
      this.mEditText.setSelection(i);
    } 
  }
  
  private static void recursiveSetEnabled(ViewGroup paramViewGroup, boolean paramBoolean) {
    int i = paramViewGroup.getChildCount();
    for (byte b = 0; b < i; b++) {
      View view = paramViewGroup.getChildAt(b);
      view.setEnabled(paramBoolean);
      if (view instanceof ViewGroup)
        recursiveSetEnabled((ViewGroup)view, paramBoolean); 
    } 
  }
  
  private void removeIndicator(TextView paramTextView) {
    LinearLayout linearLayout = this.mIndicatorArea;
    if (linearLayout != null) {
      linearLayout.removeView((View)paramTextView);
      int i = this.mIndicatorsAdded - 1;
      this.mIndicatorsAdded = i;
      if (i == 0)
        this.mIndicatorArea.setVisibility(8); 
    } 
  }
  
  private void setEditText(EditText paramEditText) {
    if (this.mEditText == null) {
      if (!(paramEditText instanceof TextInputEditText))
        Log.i("TextInputLayout", "EditText added is not a TextInputEditText. Please switch to using that class instead."); 
      this.mEditText = paramEditText;
      if (!hasPasswordTransformation())
        this.mCollapsingTextHelper.setTypefaces(this.mEditText.getTypeface()); 
      this.mCollapsingTextHelper.setExpandedTextSize(this.mEditText.getTextSize());
      int i = this.mEditText.getGravity();
      this.mCollapsingTextHelper.setCollapsedTextGravity(i & 0xFFFFFF8F | 0x30);
      this.mCollapsingTextHelper.setExpandedTextGravity(i);
      this.mEditText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable param1Editable) {
              TextInputLayout textInputLayout = TextInputLayout.this;
              textInputLayout.updateLabelState(textInputLayout.mRestoringSavedState ^ true);
              if (TextInputLayout.this.mCounterEnabled)
                TextInputLayout.this.updateCounter(param1Editable.length()); 
            }
            
            public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
            
            public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
          });
      if (this.mDefaultTextColor == null)
        this.mDefaultTextColor = this.mEditText.getHintTextColors(); 
      if (this.mHintEnabled && TextUtils.isEmpty(this.mHint)) {
        CharSequence charSequence = this.mEditText.getHint();
        this.mOriginalHint = charSequence;
        setHint(charSequence);
        this.mEditText.setHint(null);
      } 
      if (this.mCounterView != null)
        updateCounter(this.mEditText.getText().length()); 
      if (this.mIndicatorArea != null)
        adjustIndicatorPadding(); 
      updatePasswordToggleView();
      updateLabelState(false, true);
      return;
    } 
    throw new IllegalArgumentException("We already have an EditText, can only have one");
  }
  
  private void setError(final CharSequence error, boolean paramBoolean) {
    this.mError = error;
    if (!this.mErrorEnabled) {
      if (TextUtils.isEmpty(error))
        return; 
      setErrorEnabled(true);
    } 
    this.mErrorShown = TextUtils.isEmpty(error) ^ true;
    this.mErrorView.animate().cancel();
    if (this.mErrorShown) {
      this.mErrorView.setText(error);
      this.mErrorView.setVisibility(0);
      if (paramBoolean) {
        if (this.mErrorView.getAlpha() == 1.0F)
          this.mErrorView.setAlpha(0.0F); 
        this.mErrorView.animate().alpha(1.0F).setDuration(200L).setInterpolator((TimeInterpolator)AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
              public void onAnimationStart(Animator param1Animator) {
                TextInputLayout.this.mErrorView.setVisibility(0);
              }
            }).start();
      } else {
        this.mErrorView.setAlpha(1.0F);
      } 
    } else if (this.mErrorView.getVisibility() == 0) {
      if (paramBoolean) {
        this.mErrorView.animate().alpha(0.0F).setDuration(200L).setInterpolator((TimeInterpolator)AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR).setListener((Animator.AnimatorListener)new AnimatorListenerAdapter() {
              public void onAnimationEnd(Animator param1Animator) {
                TextInputLayout.this.mErrorView.setText(error);
                TextInputLayout.this.mErrorView.setVisibility(4);
              }
            }).start();
      } else {
        this.mErrorView.setText(error);
        this.mErrorView.setVisibility(4);
      } 
    } 
    updateEditTextBackground();
    updateLabelState(paramBoolean);
  }
  
  private void setHintInternal(CharSequence paramCharSequence) {
    this.mHint = paramCharSequence;
    this.mCollapsingTextHelper.setText(paramCharSequence);
  }
  
  private boolean shouldShowPasswordIcon() {
    boolean bool;
    if (this.mPasswordToggleEnabled && (hasPasswordTransformation() || this.mPasswordToggledVisible)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  private void updateEditTextBackground() {
    EditText editText = this.mEditText;
    if (editText == null)
      return; 
    Drawable drawable2 = editText.getBackground();
    if (drawable2 == null)
      return; 
    ensureBackgroundDrawableStateWorkaround();
    Drawable drawable1 = drawable2;
    if (DrawableUtils.canSafelyMutateDrawable(drawable2))
      drawable1 = drawable2.mutate(); 
    if (this.mErrorShown) {
      TextView textView = this.mErrorView;
      if (textView != null) {
        drawable1.setColorFilter((ColorFilter)AppCompatDrawableManager.getPorterDuffColorFilter(textView.getCurrentTextColor(), PorterDuff.Mode.SRC_IN));
        return;
      } 
    } 
    if (this.mCounterOverflowed) {
      TextView textView = this.mCounterView;
      if (textView != null) {
        drawable1.setColorFilter((ColorFilter)AppCompatDrawableManager.getPorterDuffColorFilter(textView.getCurrentTextColor(), PorterDuff.Mode.SRC_IN));
        return;
      } 
    } 
    DrawableCompat.clearColorFilter(drawable1);
    this.mEditText.refreshDrawableState();
  }
  
  private void updateInputLayoutMargins() {
    boolean bool;
    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)this.mInputFrame.getLayoutParams();
    if (this.mHintEnabled) {
      if (this.mTmpPaint == null)
        this.mTmpPaint = new Paint(); 
      this.mTmpPaint.setTypeface(this.mCollapsingTextHelper.getCollapsedTypeface());
      this.mTmpPaint.setTextSize(this.mCollapsingTextHelper.getCollapsedTextSize());
      bool = (int)-this.mTmpPaint.ascent();
    } else {
      bool = false;
    } 
    if (bool != layoutParams.topMargin) {
      layoutParams.topMargin = bool;
      this.mInputFrame.requestLayout();
    } 
  }
  
  private void updatePasswordToggleView() {
    if (this.mEditText == null)
      return; 
    if (shouldShowPasswordIcon()) {
      if (this.mPasswordToggleView == null) {
        CheckableImageButton checkableImageButton = (CheckableImageButton)LayoutInflater.from(getContext()).inflate(R.layout.design_text_input_password_icon, (ViewGroup)this.mInputFrame, false);
        this.mPasswordToggleView = checkableImageButton;
        checkableImageButton.setImageDrawable(this.mPasswordToggleDrawable);
        this.mPasswordToggleView.setContentDescription(this.mPasswordToggleContentDesc);
        this.mInputFrame.addView((View)this.mPasswordToggleView);
        this.mPasswordToggleView.setOnClickListener(new View.OnClickListener() {
              public void onClick(View param1View) {
                TextInputLayout.this.passwordVisibilityToggleRequested(false);
              }
            });
      } 
      EditText editText = this.mEditText;
      if (editText != null && ViewCompat.getMinimumHeight((View)editText) <= 0)
        this.mEditText.setMinimumHeight(ViewCompat.getMinimumHeight((View)this.mPasswordToggleView)); 
      this.mPasswordToggleView.setVisibility(0);
      this.mPasswordToggleView.setChecked(this.mPasswordToggledVisible);
      if (this.mPasswordToggleDummyDrawable == null)
        this.mPasswordToggleDummyDrawable = (Drawable)new ColorDrawable(); 
      this.mPasswordToggleDummyDrawable.setBounds(0, 0, this.mPasswordToggleView.getMeasuredWidth(), 1);
      Drawable[] arrayOfDrawable = TextViewCompat.getCompoundDrawablesRelative((TextView)this.mEditText);
      if (arrayOfDrawable[2] != this.mPasswordToggleDummyDrawable)
        this.mOriginalEditTextEndDrawable = arrayOfDrawable[2]; 
      TextViewCompat.setCompoundDrawablesRelative((TextView)this.mEditText, arrayOfDrawable[0], arrayOfDrawable[1], this.mPasswordToggleDummyDrawable, arrayOfDrawable[3]);
      this.mPasswordToggleView.setPadding(this.mEditText.getPaddingLeft(), this.mEditText.getPaddingTop(), this.mEditText.getPaddingRight(), this.mEditText.getPaddingBottom());
    } else {
      CheckableImageButton checkableImageButton = this.mPasswordToggleView;
      if (checkableImageButton != null && checkableImageButton.getVisibility() == 0)
        this.mPasswordToggleView.setVisibility(8); 
      if (this.mPasswordToggleDummyDrawable != null) {
        Drawable[] arrayOfDrawable = TextViewCompat.getCompoundDrawablesRelative((TextView)this.mEditText);
        if (arrayOfDrawable[2] == this.mPasswordToggleDummyDrawable) {
          TextViewCompat.setCompoundDrawablesRelative((TextView)this.mEditText, arrayOfDrawable[0], arrayOfDrawable[1], this.mOriginalEditTextEndDrawable, arrayOfDrawable[3]);
          this.mPasswordToggleDummyDrawable = null;
        } 
      } 
    } 
  }
  
  public void addView(View paramView, int paramInt, ViewGroup.LayoutParams paramLayoutParams) {
    if (paramView instanceof EditText) {
      FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(paramLayoutParams);
      layoutParams.gravity = layoutParams.gravity & 0xFFFFFF8F | 0x10;
      this.mInputFrame.addView(paramView, (ViewGroup.LayoutParams)layoutParams);
      this.mInputFrame.setLayoutParams(paramLayoutParams);
      updateInputLayoutMargins();
      setEditText((EditText)paramView);
    } else {
      super.addView(paramView, paramInt, paramLayoutParams);
    } 
  }
  
  void animateToExpansionFraction(float paramFloat) {
    if (this.mCollapsingTextHelper.getExpansionFraction() == paramFloat)
      return; 
    if (this.mAnimator == null) {
      ValueAnimator valueAnimator = new ValueAnimator();
      this.mAnimator = valueAnimator;
      valueAnimator.setInterpolator((TimeInterpolator)AnimationUtils.LINEAR_INTERPOLATOR);
      this.mAnimator.setDuration(200L);
      this.mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator param1ValueAnimator) {
              TextInputLayout.this.mCollapsingTextHelper.setExpansionFraction(((Float)param1ValueAnimator.getAnimatedValue()).floatValue());
            }
          });
    } 
    this.mAnimator.setFloatValues(new float[] { this.mCollapsingTextHelper.getExpansionFraction(), paramFloat });
    this.mAnimator.start();
  }
  
  public void dispatchProvideAutofillStructure(ViewStructure paramViewStructure, int paramInt) {
    if (this.mOriginalHint != null) {
      EditText editText = this.mEditText;
      if (editText != null) {
        CharSequence charSequence = editText.getHint();
        this.mEditText.setHint(this.mOriginalHint);
        try {
          super.dispatchProvideAutofillStructure(paramViewStructure, paramInt);
          return;
        } finally {
          this.mEditText.setHint(charSequence);
        } 
      } 
    } 
    super.dispatchProvideAutofillStructure(paramViewStructure, paramInt);
  }
  
  protected void dispatchRestoreInstanceState(SparseArray<Parcelable> paramSparseArray) {
    this.mRestoringSavedState = true;
    super.dispatchRestoreInstanceState(paramSparseArray);
    this.mRestoringSavedState = false;
  }
  
  public void draw(Canvas paramCanvas) {
    super.draw(paramCanvas);
    if (this.mHintEnabled)
      this.mCollapsingTextHelper.draw(paramCanvas); 
  }
  
  protected void drawableStateChanged() {
    boolean bool2;
    if (this.mInDrawableStateChanged)
      return; 
    boolean bool1 = true;
    this.mInDrawableStateChanged = true;
    super.drawableStateChanged();
    int[] arrayOfInt = getDrawableState();
    if (!ViewCompat.isLaidOut((View)this) || !isEnabled())
      bool1 = false; 
    updateLabelState(bool1);
    updateEditTextBackground();
    CollapsingTextHelper collapsingTextHelper = this.mCollapsingTextHelper;
    if (collapsingTextHelper != null) {
      bool2 = collapsingTextHelper.setState(arrayOfInt) | false;
    } else {
      bool2 = false;
    } 
    if (bool2)
      invalidate(); 
    this.mInDrawableStateChanged = false;
  }
  
  public int getCounterMaxLength() {
    return this.mCounterMaxLength;
  }
  
  public EditText getEditText() {
    return this.mEditText;
  }
  
  public CharSequence getError() {
    CharSequence charSequence;
    if (this.mErrorEnabled) {
      charSequence = this.mError;
    } else {
      charSequence = null;
    } 
    return charSequence;
  }
  
  public CharSequence getHint() {
    CharSequence charSequence;
    if (this.mHintEnabled) {
      charSequence = this.mHint;
    } else {
      charSequence = null;
    } 
    return charSequence;
  }
  
  public CharSequence getPasswordVisibilityToggleContentDescription() {
    return this.mPasswordToggleContentDesc;
  }
  
  public Drawable getPasswordVisibilityToggleDrawable() {
    return this.mPasswordToggleDrawable;
  }
  
  public Typeface getTypeface() {
    return this.mTypeface;
  }
  
  public boolean isCounterEnabled() {
    return this.mCounterEnabled;
  }
  
  public boolean isErrorEnabled() {
    return this.mErrorEnabled;
  }
  
  public boolean isHintAnimationEnabled() {
    return this.mHintAnimationEnabled;
  }
  
  public boolean isHintEnabled() {
    return this.mHintEnabled;
  }
  
  final boolean isHintExpanded() {
    return this.mHintExpanded;
  }
  
  public boolean isPasswordVisibilityToggleEnabled() {
    return this.mPasswordToggleEnabled;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.mHintEnabled) {
      EditText editText = this.mEditText;
      if (editText != null) {
        Rect rect = this.mTmpRect;
        ViewGroupUtils.getDescendantRect((ViewGroup)this, (View)editText, rect);
        paramInt3 = rect.left + this.mEditText.getCompoundPaddingLeft();
        paramInt1 = rect.right - this.mEditText.getCompoundPaddingRight();
        this.mCollapsingTextHelper.setExpandedBounds(paramInt3, rect.top + this.mEditText.getCompoundPaddingTop(), paramInt1, rect.bottom - this.mEditText.getCompoundPaddingBottom());
        this.mCollapsingTextHelper.setCollapsedBounds(paramInt3, getPaddingTop(), paramInt1, paramInt4 - paramInt2 - getPaddingBottom());
        this.mCollapsingTextHelper.recalculate();
      } 
    } 
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    updatePasswordToggleView();
    super.onMeasure(paramInt1, paramInt2);
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable) {
    if (!(paramParcelable instanceof SavedState)) {
      super.onRestoreInstanceState(paramParcelable);
      return;
    } 
    SavedState savedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(savedState.getSuperState());
    setError(savedState.error);
    if (savedState.isPasswordToggledVisible)
      passwordVisibilityToggleRequested(true); 
    requestLayout();
  }
  
  public Parcelable onSaveInstanceState() {
    SavedState savedState = new SavedState(super.onSaveInstanceState());
    if (this.mErrorShown)
      savedState.error = getError(); 
    savedState.isPasswordToggledVisible = this.mPasswordToggledVisible;
    return (Parcelable)savedState;
  }
  
  public void setCounterEnabled(boolean paramBoolean) {
    if (this.mCounterEnabled != paramBoolean) {
      if (paramBoolean) {
        AppCompatTextView appCompatTextView = new AppCompatTextView(getContext());
        this.mCounterView = (TextView)appCompatTextView;
        appCompatTextView.setId(R.id.textinput_counter);
        Typeface typeface = this.mTypeface;
        if (typeface != null)
          this.mCounterView.setTypeface(typeface); 
        this.mCounterView.setMaxLines(1);
        try {
          TextViewCompat.setTextAppearance(this.mCounterView, this.mCounterTextAppearance);
        } catch (Exception exception) {
          TextViewCompat.setTextAppearance(this.mCounterView, R.style.TextAppearance_AppCompat_Caption);
          this.mCounterView.setTextColor(ContextCompat.getColor(getContext(), R.color.error_color_material));
        } 
        addIndicator(this.mCounterView, -1);
        EditText editText = this.mEditText;
        if (editText == null) {
          updateCounter(0);
        } else {
          updateCounter(editText.getText().length());
        } 
      } else {
        removeIndicator(this.mCounterView);
        this.mCounterView = null;
      } 
      this.mCounterEnabled = paramBoolean;
    } 
  }
  
  public void setCounterMaxLength(int paramInt) {
    if (this.mCounterMaxLength != paramInt) {
      if (paramInt > 0) {
        this.mCounterMaxLength = paramInt;
      } else {
        this.mCounterMaxLength = -1;
      } 
      if (this.mCounterEnabled) {
        EditText editText = this.mEditText;
        if (editText == null) {
          paramInt = 0;
        } else {
          paramInt = editText.getText().length();
        } 
        updateCounter(paramInt);
      } 
    } 
  }
  
  public void setEnabled(boolean paramBoolean) {
    recursiveSetEnabled((ViewGroup)this, paramBoolean);
    super.setEnabled(paramBoolean);
  }
  
  public void setError(CharSequence paramCharSequence) {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic isLaidOut : (Landroid/view/View;)Z
    //   4: ifeq -> 39
    //   7: aload_0
    //   8: invokevirtual isEnabled : ()Z
    //   11: ifeq -> 39
    //   14: aload_0
    //   15: getfield mErrorView : Landroid/widget/TextView;
    //   18: astore_2
    //   19: aload_2
    //   20: ifnull -> 34
    //   23: aload_2
    //   24: invokevirtual getText : ()Ljava/lang/CharSequence;
    //   27: aload_1
    //   28: invokestatic equals : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z
    //   31: ifne -> 39
    //   34: iconst_1
    //   35: istore_3
    //   36: goto -> 41
    //   39: iconst_0
    //   40: istore_3
    //   41: aload_0
    //   42: aload_1
    //   43: iload_3
    //   44: invokespecial setError : (Ljava/lang/CharSequence;Z)V
    //   47: return
  }
  
  public void setErrorEnabled(boolean paramBoolean) {
    // Byte code:
    //   0: aload_0
    //   1: getfield mErrorEnabled : Z
    //   4: iload_1
    //   5: if_icmpeq -> 202
    //   8: aload_0
    //   9: getfield mErrorView : Landroid/widget/TextView;
    //   12: astore_2
    //   13: aload_2
    //   14: ifnull -> 24
    //   17: aload_2
    //   18: invokevirtual animate : ()Landroid/view/ViewPropertyAnimator;
    //   21: invokevirtual cancel : ()V
    //   24: iload_1
    //   25: ifeq -> 175
    //   28: new android/support/v7/widget/AppCompatTextView
    //   31: dup
    //   32: aload_0
    //   33: invokevirtual getContext : ()Landroid/content/Context;
    //   36: invokespecial <init> : (Landroid/content/Context;)V
    //   39: astore_2
    //   40: aload_0
    //   41: aload_2
    //   42: putfield mErrorView : Landroid/widget/TextView;
    //   45: aload_2
    //   46: getstatic android/support/design/R$id.textinput_error : I
    //   49: invokevirtual setId : (I)V
    //   52: aload_0
    //   53: getfield mTypeface : Landroid/graphics/Typeface;
    //   56: astore_2
    //   57: aload_2
    //   58: ifnull -> 69
    //   61: aload_0
    //   62: getfield mErrorView : Landroid/widget/TextView;
    //   65: aload_2
    //   66: invokevirtual setTypeface : (Landroid/graphics/Typeface;)V
    //   69: aload_0
    //   70: getfield mErrorView : Landroid/widget/TextView;
    //   73: aload_0
    //   74: getfield mErrorTextAppearance : I
    //   77: invokestatic setTextAppearance : (Landroid/widget/TextView;I)V
    //   80: getstatic android/os/Build$VERSION.SDK_INT : I
    //   83: bipush #23
    //   85: if_icmplt -> 109
    //   88: aload_0
    //   89: getfield mErrorView : Landroid/widget/TextView;
    //   92: invokevirtual getTextColors : ()Landroid/content/res/ColorStateList;
    //   95: invokevirtual getDefaultColor : ()I
    //   98: istore_3
    //   99: iload_3
    //   100: ldc_w -65281
    //   103: if_icmpne -> 109
    //   106: goto -> 114
    //   109: iconst_0
    //   110: istore_3
    //   111: goto -> 116
    //   114: iconst_1
    //   115: istore_3
    //   116: iload_3
    //   117: ifeq -> 147
    //   120: aload_0
    //   121: getfield mErrorView : Landroid/widget/TextView;
    //   124: getstatic android/support/v7/appcompat/R$style.TextAppearance_AppCompat_Caption : I
    //   127: invokestatic setTextAppearance : (Landroid/widget/TextView;I)V
    //   130: aload_0
    //   131: getfield mErrorView : Landroid/widget/TextView;
    //   134: aload_0
    //   135: invokevirtual getContext : ()Landroid/content/Context;
    //   138: getstatic android/support/v7/appcompat/R$color.error_color_material : I
    //   141: invokestatic getColor : (Landroid/content/Context;I)I
    //   144: invokevirtual setTextColor : (I)V
    //   147: aload_0
    //   148: getfield mErrorView : Landroid/widget/TextView;
    //   151: iconst_4
    //   152: invokevirtual setVisibility : (I)V
    //   155: aload_0
    //   156: getfield mErrorView : Landroid/widget/TextView;
    //   159: iconst_1
    //   160: invokestatic setAccessibilityLiveRegion : (Landroid/view/View;I)V
    //   163: aload_0
    //   164: aload_0
    //   165: getfield mErrorView : Landroid/widget/TextView;
    //   168: iconst_0
    //   169: invokespecial addIndicator : (Landroid/widget/TextView;I)V
    //   172: goto -> 197
    //   175: aload_0
    //   176: iconst_0
    //   177: putfield mErrorShown : Z
    //   180: aload_0
    //   181: invokespecial updateEditTextBackground : ()V
    //   184: aload_0
    //   185: aload_0
    //   186: getfield mErrorView : Landroid/widget/TextView;
    //   189: invokespecial removeIndicator : (Landroid/widget/TextView;)V
    //   192: aload_0
    //   193: aconst_null
    //   194: putfield mErrorView : Landroid/widget/TextView;
    //   197: aload_0
    //   198: iload_1
    //   199: putfield mErrorEnabled : Z
    //   202: return
    //   203: astore_2
    //   204: goto -> 114
    // Exception table:
    //   from	to	target	type
    //   69	99	203	java/lang/Exception
  }
  
  public void setErrorTextAppearance(int paramInt) {
    this.mErrorTextAppearance = paramInt;
    TextView textView = this.mErrorView;
    if (textView != null)
      TextViewCompat.setTextAppearance(textView, paramInt); 
  }
  
  public void setHint(CharSequence paramCharSequence) {
    if (this.mHintEnabled) {
      setHintInternal(paramCharSequence);
      sendAccessibilityEvent(2048);
    } 
  }
  
  public void setHintAnimationEnabled(boolean paramBoolean) {
    this.mHintAnimationEnabled = paramBoolean;
  }
  
  public void setHintEnabled(boolean paramBoolean) {
    if (paramBoolean != this.mHintEnabled) {
      this.mHintEnabled = paramBoolean;
      CharSequence charSequence = this.mEditText.getHint();
      if (!this.mHintEnabled) {
        if (!TextUtils.isEmpty(this.mHint) && TextUtils.isEmpty(charSequence))
          this.mEditText.setHint(this.mHint); 
        setHintInternal((CharSequence)null);
      } else if (!TextUtils.isEmpty(charSequence)) {
        if (TextUtils.isEmpty(this.mHint))
          setHint(charSequence); 
        this.mEditText.setHint(null);
      } 
      if (this.mEditText != null)
        updateInputLayoutMargins(); 
    } 
  }
  
  public void setHintTextAppearance(int paramInt) {
    this.mCollapsingTextHelper.setCollapsedTextAppearance(paramInt);
    this.mFocusedTextColor = this.mCollapsingTextHelper.getCollapsedTextColor();
    if (this.mEditText != null) {
      updateLabelState(false);
      updateInputLayoutMargins();
    } 
  }
  
  public void setPasswordVisibilityToggleContentDescription(int paramInt) {
    CharSequence charSequence;
    if (paramInt != 0) {
      charSequence = getResources().getText(paramInt);
    } else {
      charSequence = null;
    } 
    setPasswordVisibilityToggleContentDescription(charSequence);
  }
  
  public void setPasswordVisibilityToggleContentDescription(CharSequence paramCharSequence) {
    this.mPasswordToggleContentDesc = paramCharSequence;
    CheckableImageButton checkableImageButton = this.mPasswordToggleView;
    if (checkableImageButton != null)
      checkableImageButton.setContentDescription(paramCharSequence); 
  }
  
  public void setPasswordVisibilityToggleDrawable(int paramInt) {
    Drawable drawable;
    if (paramInt != 0) {
      drawable = AppCompatResources.getDrawable(getContext(), paramInt);
    } else {
      drawable = null;
    } 
    setPasswordVisibilityToggleDrawable(drawable);
  }
  
  public void setPasswordVisibilityToggleDrawable(Drawable paramDrawable) {
    this.mPasswordToggleDrawable = paramDrawable;
    CheckableImageButton checkableImageButton = this.mPasswordToggleView;
    if (checkableImageButton != null)
      checkableImageButton.setImageDrawable(paramDrawable); 
  }
  
  public void setPasswordVisibilityToggleEnabled(boolean paramBoolean) {
    if (this.mPasswordToggleEnabled != paramBoolean) {
      this.mPasswordToggleEnabled = paramBoolean;
      if (!paramBoolean && this.mPasswordToggledVisible) {
        EditText editText = this.mEditText;
        if (editText != null)
          editText.setTransformationMethod((TransformationMethod)PasswordTransformationMethod.getInstance()); 
      } 
      this.mPasswordToggledVisible = false;
      updatePasswordToggleView();
    } 
  }
  
  public void setPasswordVisibilityToggleTintList(ColorStateList paramColorStateList) {
    this.mPasswordToggleTintList = paramColorStateList;
    this.mHasPasswordToggleTintList = true;
    applyPasswordToggleTint();
  }
  
  public void setPasswordVisibilityToggleTintMode(PorterDuff.Mode paramMode) {
    this.mPasswordToggleTintMode = paramMode;
    this.mHasPasswordToggleTintMode = true;
    applyPasswordToggleTint();
  }
  
  public void setTypeface(Typeface paramTypeface) {
    Typeface typeface = this.mTypeface;
    if ((typeface != null && !typeface.equals(paramTypeface)) || (this.mTypeface == null && paramTypeface != null)) {
      this.mTypeface = paramTypeface;
      this.mCollapsingTextHelper.setTypefaces(paramTypeface);
      TextView textView = this.mCounterView;
      if (textView != null)
        textView.setTypeface(paramTypeface); 
      textView = this.mErrorView;
      if (textView != null)
        textView.setTypeface(paramTypeface); 
    } 
  }
  
  void updateCounter(int paramInt) {
    boolean bool = this.mCounterOverflowed;
    int i = this.mCounterMaxLength;
    if (i == -1) {
      this.mCounterView.setText(String.valueOf(paramInt));
      this.mCounterOverflowed = false;
    } else {
      boolean bool1;
      if (paramInt > i) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      this.mCounterOverflowed = bool1;
      if (bool != bool1) {
        TextView textView = this.mCounterView;
        if (bool1) {
          i = this.mCounterOverflowTextAppearance;
        } else {
          i = this.mCounterTextAppearance;
        } 
        TextViewCompat.setTextAppearance(textView, i);
      } 
      this.mCounterView.setText(getContext().getString(R.string.character_counter_pattern, new Object[] { Integer.valueOf(paramInt), Integer.valueOf(this.mCounterMaxLength) }));
    } 
    if (this.mEditText != null && bool != this.mCounterOverflowed) {
      updateLabelState(false);
      updateEditTextBackground();
    } 
  }
  
  void updateLabelState(boolean paramBoolean) {
    updateLabelState(paramBoolean, false);
  }
  
  void updateLabelState(boolean paramBoolean1, boolean paramBoolean2) {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual isEnabled : ()Z
    //   4: istore_3
    //   5: aload_0
    //   6: getfield mEditText : Landroid/widget/EditText;
    //   9: astore #4
    //   11: aload #4
    //   13: ifnull -> 33
    //   16: aload #4
    //   18: invokevirtual getText : ()Landroid/text/Editable;
    //   21: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   24: ifne -> 33
    //   27: iconst_1
    //   28: istore #5
    //   30: goto -> 36
    //   33: iconst_0
    //   34: istore #5
    //   36: aload_0
    //   37: invokevirtual getDrawableState : ()[I
    //   40: ldc_w 16842908
    //   43: invokestatic arrayContains : ([II)Z
    //   46: istore #6
    //   48: aload_0
    //   49: invokevirtual getError : ()Ljava/lang/CharSequence;
    //   52: invokestatic isEmpty : (Ljava/lang/CharSequence;)Z
    //   55: istore #7
    //   57: aload_0
    //   58: getfield mDefaultTextColor : Landroid/content/res/ColorStateList;
    //   61: astore #4
    //   63: aload #4
    //   65: ifnull -> 77
    //   68: aload_0
    //   69: getfield mCollapsingTextHelper : Landroid/support/design/widget/CollapsingTextHelper;
    //   72: aload #4
    //   74: invokevirtual setExpandedTextColor : (Landroid/content/res/ColorStateList;)V
    //   77: iload_3
    //   78: ifeq -> 114
    //   81: aload_0
    //   82: getfield mCounterOverflowed : Z
    //   85: ifeq -> 114
    //   88: aload_0
    //   89: getfield mCounterView : Landroid/widget/TextView;
    //   92: astore #4
    //   94: aload #4
    //   96: ifnull -> 114
    //   99: aload_0
    //   100: getfield mCollapsingTextHelper : Landroid/support/design/widget/CollapsingTextHelper;
    //   103: aload #4
    //   105: invokevirtual getTextColors : ()Landroid/content/res/ColorStateList;
    //   108: invokevirtual setCollapsedTextColor : (Landroid/content/res/ColorStateList;)V
    //   111: goto -> 166
    //   114: iload_3
    //   115: ifeq -> 146
    //   118: iload #6
    //   120: ifeq -> 146
    //   123: aload_0
    //   124: getfield mFocusedTextColor : Landroid/content/res/ColorStateList;
    //   127: astore #4
    //   129: aload #4
    //   131: ifnull -> 146
    //   134: aload_0
    //   135: getfield mCollapsingTextHelper : Landroid/support/design/widget/CollapsingTextHelper;
    //   138: aload #4
    //   140: invokevirtual setCollapsedTextColor : (Landroid/content/res/ColorStateList;)V
    //   143: goto -> 166
    //   146: aload_0
    //   147: getfield mDefaultTextColor : Landroid/content/res/ColorStateList;
    //   150: astore #4
    //   152: aload #4
    //   154: ifnull -> 166
    //   157: aload_0
    //   158: getfield mCollapsingTextHelper : Landroid/support/design/widget/CollapsingTextHelper;
    //   161: aload #4
    //   163: invokevirtual setCollapsedTextColor : (Landroid/content/res/ColorStateList;)V
    //   166: iload #5
    //   168: ifne -> 212
    //   171: aload_0
    //   172: invokevirtual isEnabled : ()Z
    //   175: ifeq -> 193
    //   178: iload #6
    //   180: ifne -> 212
    //   183: iconst_1
    //   184: iload #7
    //   186: ixor
    //   187: ifeq -> 193
    //   190: goto -> 212
    //   193: iload_2
    //   194: ifne -> 204
    //   197: aload_0
    //   198: getfield mHintExpanded : Z
    //   201: ifne -> 228
    //   204: aload_0
    //   205: iload_1
    //   206: invokespecial expandHint : (Z)V
    //   209: goto -> 228
    //   212: iload_2
    //   213: ifne -> 223
    //   216: aload_0
    //   217: getfield mHintExpanded : Z
    //   220: ifeq -> 228
    //   223: aload_0
    //   224: iload_1
    //   225: invokespecial collapseHint : (Z)V
    //   228: return
  }
  
  static class SavedState extends AbsSavedState {
    public static final Parcelable.Creator<SavedState> CREATOR = (Parcelable.Creator<SavedState>)new Parcelable.ClassLoaderCreator<SavedState>() {
        public TextInputLayout.SavedState createFromParcel(Parcel param2Parcel) {
          return new TextInputLayout.SavedState(param2Parcel, null);
        }
        
        public TextInputLayout.SavedState createFromParcel(Parcel param2Parcel, ClassLoader param2ClassLoader) {
          return new TextInputLayout.SavedState(param2Parcel, param2ClassLoader);
        }
        
        public TextInputLayout.SavedState[] newArray(int param2Int) {
          return new TextInputLayout.SavedState[param2Int];
        }
      };
    
    CharSequence error;
    
    boolean isPasswordToggledVisible;
    
    SavedState(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      super(param1Parcel, param1ClassLoader);
      this.error = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(param1Parcel);
      int i = param1Parcel.readInt();
      boolean bool = true;
      if (i != 1)
        bool = false; 
      this.isPasswordToggledVisible = bool;
    }
    
    SavedState(Parcelable param1Parcelable) {
      super(param1Parcelable);
    }
    
    public String toString() {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("TextInputLayout.SavedState{");
      stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
      stringBuilder.append(" error=");
      stringBuilder.append(this.error);
      stringBuilder.append("}");
      return stringBuilder.toString();
    }
    
    public void writeToParcel(Parcel param1Parcel, int param1Int) {
      super.writeToParcel(param1Parcel, param1Int);
      TextUtils.writeToParcel(this.error, param1Parcel, param1Int);
      param1Parcel.writeInt(this.isPasswordToggledVisible);
    }
  }
  
  static final class null implements Parcelable.ClassLoaderCreator<SavedState> {
    public TextInputLayout.SavedState createFromParcel(Parcel param1Parcel) {
      return new TextInputLayout.SavedState(param1Parcel, null);
    }
    
    public TextInputLayout.SavedState createFromParcel(Parcel param1Parcel, ClassLoader param1ClassLoader) {
      return new TextInputLayout.SavedState(param1Parcel, param1ClassLoader);
    }
    
    public TextInputLayout.SavedState[] newArray(int param1Int) {
      return new TextInputLayout.SavedState[param1Int];
    }
  }
  
  private class TextInputAccessibilityDelegate extends AccessibilityDelegateCompat {
    public void onInitializeAccessibilityEvent(View param1View, AccessibilityEvent param1AccessibilityEvent) {
      super.onInitializeAccessibilityEvent(param1View, param1AccessibilityEvent);
      param1AccessibilityEvent.setClassName(TextInputLayout.class.getSimpleName());
    }
    
    public void onInitializeAccessibilityNodeInfo(View param1View, AccessibilityNodeInfoCompat param1AccessibilityNodeInfoCompat) {
      super.onInitializeAccessibilityNodeInfo(param1View, param1AccessibilityNodeInfoCompat);
      param1AccessibilityNodeInfoCompat.setClassName(TextInputLayout.class.getSimpleName());
      CharSequence charSequence = TextInputLayout.this.mCollapsingTextHelper.getText();
      if (!TextUtils.isEmpty(charSequence))
        param1AccessibilityNodeInfoCompat.setText(charSequence); 
      if (TextInputLayout.this.mEditText != null)
        param1AccessibilityNodeInfoCompat.setLabelFor((View)TextInputLayout.this.mEditText); 
      if (TextInputLayout.this.mErrorView != null) {
        charSequence = TextInputLayout.this.mErrorView.getText();
      } else {
        charSequence = null;
      } 
      if (!TextUtils.isEmpty(charSequence)) {
        param1AccessibilityNodeInfoCompat.setContentInvalid(true);
        param1AccessibilityNodeInfoCompat.setError(charSequence);
      } 
    }
    
    public void onPopulateAccessibilityEvent(View param1View, AccessibilityEvent param1AccessibilityEvent) {
      super.onPopulateAccessibilityEvent(param1View, param1AccessibilityEvent);
      CharSequence charSequence = TextInputLayout.this.mCollapsingTextHelper.getText();
      if (!TextUtils.isEmpty(charSequence))
        param1AccessibilityEvent.getText().add(charSequence); 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\design\widget\TextInputLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */