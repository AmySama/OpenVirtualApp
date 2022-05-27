package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Build;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import org.xmlpull.v1.XmlPullParser;

public class ChangeTransform extends Transition {
  static {
    NON_TRANSLATIONS_PROPERTY = new Property<PathAnimatorMatrix, float[]>(float[].class, "nonTranslations") {
        public float[] get(ChangeTransform.PathAnimatorMatrix param1PathAnimatorMatrix) {
          return null;
        }
        
        public void set(ChangeTransform.PathAnimatorMatrix param1PathAnimatorMatrix, float[] param1ArrayOffloat) {
          param1PathAnimatorMatrix.setValues(param1ArrayOffloat);
        }
      };
    TRANSLATIONS_PROPERTY = new Property<PathAnimatorMatrix, PointF>(PointF.class, "translations") {
        public PointF get(ChangeTransform.PathAnimatorMatrix param1PathAnimatorMatrix) {
          return null;
        }
        
        public void set(ChangeTransform.PathAnimatorMatrix param1PathAnimatorMatrix, PointF param1PointF) {
          param1PathAnimatorMatrix.setTranslation(param1PointF);
        }
      };
    if (Build.VERSION.SDK_INT >= 21) {
      bool = true;
    } else {
      bool = false;
    } 
    SUPPORTS_VIEW_REMOVAL_SUPPRESSION = bool;
  }
  
  public ChangeTransform() {}
  
  public ChangeTransform(Context paramContext, AttributeSet paramAttributeSet) {
    super(paramContext, paramAttributeSet);
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, Styleable.CHANGE_TRANSFORM);
    XmlPullParser xmlPullParser = (XmlPullParser)paramAttributeSet;
    this.mUseOverlay = TypedArrayUtils.getNamedBoolean(typedArray, xmlPullParser, "reparentWithOverlay", 1, true);
    this.mReparent = TypedArrayUtils.getNamedBoolean(typedArray, xmlPullParser, "reparent", 0, true);
    typedArray.recycle();
  }
  
  private void captureValues(TransitionValues paramTransitionValues) {
    View view = paramTransitionValues.view;
    if (view.getVisibility() == 8)
      return; 
    paramTransitionValues.values.put("android:changeTransform:parent", view.getParent());
    Transforms transforms = new Transforms(view);
    paramTransitionValues.values.put("android:changeTransform:transforms", transforms);
    Matrix matrix = view.getMatrix();
    if (matrix == null || matrix.isIdentity()) {
      matrix = null;
    } else {
      matrix = new Matrix(matrix);
    } 
    paramTransitionValues.values.put("android:changeTransform:matrix", matrix);
    if (this.mReparent) {
      Matrix matrix1 = new Matrix();
      ViewGroup viewGroup = (ViewGroup)view.getParent();
      ViewUtils.transformMatrixToGlobal((View)viewGroup, matrix1);
      matrix1.preTranslate(-viewGroup.getScrollX(), -viewGroup.getScrollY());
      paramTransitionValues.values.put("android:changeTransform:parentMatrix", matrix1);
      paramTransitionValues.values.put("android:changeTransform:intermediateMatrix", view.getTag(R.id.transition_transform));
      paramTransitionValues.values.put("android:changeTransform:intermediateParentMatrix", view.getTag(R.id.parent_matrix));
    } 
  }
  
  private void createGhostView(ViewGroup paramViewGroup, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2) {
    TransitionSet transitionSet;
    View view = paramTransitionValues2.view;
    Matrix matrix = new Matrix((Matrix)paramTransitionValues2.values.get("android:changeTransform:parentMatrix"));
    ViewUtils.transformMatrixToLocal((View)paramViewGroup, matrix);
    GhostViewImpl ghostViewImpl = GhostViewUtils.addGhost(view, paramViewGroup, matrix);
    if (ghostViewImpl == null)
      return; 
    ghostViewImpl.reserveEndViewTransition((ViewGroup)paramTransitionValues1.values.get("android:changeTransform:parent"), paramTransitionValues1.view);
    ChangeTransform changeTransform = this;
    while (changeTransform.mParent != null)
      transitionSet = changeTransform.mParent; 
    transitionSet.addListener(new GhostListener(view, ghostViewImpl));
    if (SUPPORTS_VIEW_REMOVAL_SUPPRESSION) {
      if (paramTransitionValues1.view != paramTransitionValues2.view)
        ViewUtils.setTransitionAlpha(paramTransitionValues1.view, 0.0F); 
      ViewUtils.setTransitionAlpha(view, 1.0F);
    } 
  }
  
  private ObjectAnimator createTransformAnimator(TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2, final boolean handleParentChange) {
    final Matrix finalEndMatrix = (Matrix)paramTransitionValues1.values.get("android:changeTransform:matrix");
    Matrix matrix2 = (Matrix)paramTransitionValues2.values.get("android:changeTransform:matrix");
    Matrix matrix3 = matrix1;
    if (matrix1 == null)
      matrix3 = MatrixUtils.IDENTITY_MATRIX; 
    matrix1 = matrix2;
    if (matrix2 == null)
      matrix1 = MatrixUtils.IDENTITY_MATRIX; 
    if (matrix3.equals(matrix1))
      return null; 
    final Transforms transforms = (Transforms)paramTransitionValues2.values.get("android:changeTransform:transforms");
    final View view = paramTransitionValues2.view;
    setIdentityTransforms(view);
    float[] arrayOfFloat1 = new float[9];
    matrix3.getValues(arrayOfFloat1);
    float[] arrayOfFloat2 = new float[9];
    matrix1.getValues(arrayOfFloat2);
    final PathAnimatorMatrix pathAnimatorMatrix = new PathAnimatorMatrix(view, arrayOfFloat1);
    PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofObject(NON_TRANSLATIONS_PROPERTY, new FloatArrayEvaluator(new float[9]), (Object[])new float[][] { arrayOfFloat1, arrayOfFloat2 });
    Path path = getPathMotion().getPath(arrayOfFloat1[2], arrayOfFloat1[5], arrayOfFloat2[2], arrayOfFloat2[5]);
    ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(pathAnimatorMatrix, new PropertyValuesHolder[] { propertyValuesHolder, PropertyValuesHolderUtils.ofPointF(TRANSLATIONS_PROPERTY, path) });
    AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() {
        private boolean mIsCanceled;
        
        private Matrix mTempMatrix = new Matrix();
        
        private void setCurrentMatrix(Matrix param1Matrix) {
          this.mTempMatrix.set(param1Matrix);
          view.setTag(R.id.transition_transform, this.mTempMatrix);
          transforms.restore(view);
        }
        
        public void onAnimationCancel(Animator param1Animator) {
          this.mIsCanceled = true;
        }
        
        public void onAnimationEnd(Animator param1Animator) {
          if (!this.mIsCanceled)
            if (handleParentChange && ChangeTransform.this.mUseOverlay) {
              setCurrentMatrix(finalEndMatrix);
            } else {
              view.setTag(R.id.transition_transform, null);
              view.setTag(R.id.parent_matrix, null);
            }  
          ViewUtils.setAnimationMatrix(view, null);
          transforms.restore(view);
        }
        
        public void onAnimationPause(Animator param1Animator) {
          setCurrentMatrix(pathAnimatorMatrix.getMatrix());
        }
        
        public void onAnimationResume(Animator param1Animator) {
          ChangeTransform.setIdentityTransforms(view);
        }
      };
    objectAnimator.addListener((Animator.AnimatorListener)animatorListenerAdapter);
    AnimatorUtils.addPauseListener((Animator)objectAnimator, animatorListenerAdapter);
    return objectAnimator;
  }
  
  private boolean parentsMatch(ViewGroup paramViewGroup1, ViewGroup paramViewGroup2) {
    boolean bool = isValidTarget((View)paramViewGroup1);
    boolean bool1 = true;
    null = false;
    if (!bool || !isValidTarget((View)paramViewGroup2)) {
      if (paramViewGroup1 == paramViewGroup2)
        return bool1; 
    } else {
      TransitionValues transitionValues = getMatchedTransitionValues((View)paramViewGroup1, true);
      if (transitionValues != null) {
        if (paramViewGroup2 == transitionValues.view)
          return bool1; 
      } else {
        return null;
      } 
    } 
    return false;
  }
  
  private static void setIdentityTransforms(View paramView) {
    setTransforms(paramView, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F);
  }
  
  private void setMatricesForParent(TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2) {
    Matrix matrix2 = (Matrix)paramTransitionValues2.values.get("android:changeTransform:parentMatrix");
    paramTransitionValues2.view.setTag(R.id.parent_matrix, matrix2);
    Matrix matrix3 = this.mTempMatrix;
    matrix3.reset();
    matrix2.invert(matrix3);
    matrix2 = (Matrix)paramTransitionValues1.values.get("android:changeTransform:matrix");
    Matrix matrix1 = matrix2;
    if (matrix2 == null) {
      matrix1 = new Matrix();
      paramTransitionValues1.values.put("android:changeTransform:matrix", matrix1);
    } 
    matrix1.postConcat((Matrix)paramTransitionValues1.values.get("android:changeTransform:parentMatrix"));
    matrix1.postConcat(matrix3);
  }
  
  private static void setTransforms(View paramView, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8) {
    paramView.setTranslationX(paramFloat1);
    paramView.setTranslationY(paramFloat2);
    ViewCompat.setTranslationZ(paramView, paramFloat3);
    paramView.setScaleX(paramFloat4);
    paramView.setScaleY(paramFloat5);
    paramView.setRotationX(paramFloat6);
    paramView.setRotationY(paramFloat7);
    paramView.setRotation(paramFloat8);
  }
  
  public void captureEndValues(TransitionValues paramTransitionValues) {
    captureValues(paramTransitionValues);
  }
  
  public void captureStartValues(TransitionValues paramTransitionValues) {
    captureValues(paramTransitionValues);
    if (!SUPPORTS_VIEW_REMOVAL_SUPPRESSION)
      ((ViewGroup)paramTransitionValues.view.getParent()).startViewTransition(paramTransitionValues.view); 
  }
  
  public Animator createAnimator(ViewGroup paramViewGroup, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2) {
    boolean bool;
    if (paramTransitionValues1 == null || paramTransitionValues2 == null || !paramTransitionValues1.values.containsKey("android:changeTransform:parent") || !paramTransitionValues2.values.containsKey("android:changeTransform:parent"))
      return null; 
    ViewGroup viewGroup1 = (ViewGroup)paramTransitionValues1.values.get("android:changeTransform:parent");
    ViewGroup viewGroup2 = (ViewGroup)paramTransitionValues2.values.get("android:changeTransform:parent");
    if (this.mReparent && !parentsMatch(viewGroup1, viewGroup2)) {
      bool = true;
    } else {
      bool = false;
    } 
    Matrix matrix = (Matrix)paramTransitionValues1.values.get("android:changeTransform:intermediateMatrix");
    if (matrix != null)
      paramTransitionValues1.values.put("android:changeTransform:matrix", matrix); 
    matrix = (Matrix)paramTransitionValues1.values.get("android:changeTransform:intermediateParentMatrix");
    if (matrix != null)
      paramTransitionValues1.values.put("android:changeTransform:parentMatrix", matrix); 
    if (bool)
      setMatricesForParent(paramTransitionValues1, paramTransitionValues2); 
    ObjectAnimator objectAnimator = createTransformAnimator(paramTransitionValues1, paramTransitionValues2, bool);
    if (bool && objectAnimator != null && this.mUseOverlay) {
      createGhostView(paramViewGroup, paramTransitionValues1, paramTransitionValues2);
    } else if (!SUPPORTS_VIEW_REMOVAL_SUPPRESSION) {
      viewGroup1.endViewTransition(paramTransitionValues1.view);
    } 
    return (Animator)objectAnimator;
  }
  
  public boolean getReparent() {
    return this.mReparent;
  }
  
  public boolean getReparentWithOverlay() {
    return this.mUseOverlay;
  }
  
  public String[] getTransitionProperties() {
    return sTransitionProperties;
  }
  
  public void setReparent(boolean paramBoolean) {
    this.mReparent = paramBoolean;
  }
  
  public void setReparentWithOverlay(boolean paramBoolean) {
    this.mUseOverlay = paramBoolean;
  }
  
  static {
    boolean bool;
  }
  
  private static final Property<PathAnimatorMatrix, float[]> NON_TRANSLATIONS_PROPERTY;
  
  private static final String PROPNAME_INTERMEDIATE_MATRIX = "android:changeTransform:intermediateMatrix";
  
  private static final String PROPNAME_INTERMEDIATE_PARENT_MATRIX = "android:changeTransform:intermediateParentMatrix";
  
  private static final String PROPNAME_MATRIX = "android:changeTransform:matrix";
  
  private static final String PROPNAME_PARENT = "android:changeTransform:parent";
  
  private static final String PROPNAME_PARENT_MATRIX = "android:changeTransform:parentMatrix";
  
  private static final String PROPNAME_TRANSFORMS = "android:changeTransform:transforms";
  
  private static final boolean SUPPORTS_VIEW_REMOVAL_SUPPRESSION;
  
  private static final Property<PathAnimatorMatrix, PointF> TRANSLATIONS_PROPERTY;
  
  private static final String[] sTransitionProperties = new String[] { "android:changeTransform:matrix", "android:changeTransform:transforms", "android:changeTransform:parentMatrix" };
  
  private boolean mReparent = true;
  
  private Matrix mTempMatrix = new Matrix();
  
  private boolean mUseOverlay = true;
  
  private static class GhostListener extends TransitionListenerAdapter {
    private GhostViewImpl mGhostView;
    
    private View mView;
    
    GhostListener(View param1View, GhostViewImpl param1GhostViewImpl) {
      this.mView = param1View;
      this.mGhostView = param1GhostViewImpl;
    }
    
    public void onTransitionEnd(Transition param1Transition) {
      param1Transition.removeListener(this);
      GhostViewUtils.removeGhost(this.mView);
      this.mView.setTag(R.id.transition_transform, null);
      this.mView.setTag(R.id.parent_matrix, null);
    }
    
    public void onTransitionPause(Transition param1Transition) {
      this.mGhostView.setVisibility(4);
    }
    
    public void onTransitionResume(Transition param1Transition) {
      this.mGhostView.setVisibility(0);
    }
  }
  
  private static class PathAnimatorMatrix {
    private final Matrix mMatrix = new Matrix();
    
    private float mTranslationX;
    
    private float mTranslationY;
    
    private final float[] mValues;
    
    private final View mView;
    
    PathAnimatorMatrix(View param1View, float[] param1ArrayOffloat) {
      this.mView = param1View;
      float[] arrayOfFloat = (float[])param1ArrayOffloat.clone();
      this.mValues = arrayOfFloat;
      this.mTranslationX = arrayOfFloat[2];
      this.mTranslationY = arrayOfFloat[5];
      setAnimationMatrix();
    }
    
    private void setAnimationMatrix() {
      float[] arrayOfFloat = this.mValues;
      arrayOfFloat[2] = this.mTranslationX;
      arrayOfFloat[5] = this.mTranslationY;
      this.mMatrix.setValues(arrayOfFloat);
      ViewUtils.setAnimationMatrix(this.mView, this.mMatrix);
    }
    
    Matrix getMatrix() {
      return this.mMatrix;
    }
    
    void setTranslation(PointF param1PointF) {
      this.mTranslationX = param1PointF.x;
      this.mTranslationY = param1PointF.y;
      setAnimationMatrix();
    }
    
    void setValues(float[] param1ArrayOffloat) {
      System.arraycopy(param1ArrayOffloat, 0, this.mValues, 0, param1ArrayOffloat.length);
      setAnimationMatrix();
    }
  }
  
  private static class Transforms {
    final float mRotationX;
    
    final float mRotationY;
    
    final float mRotationZ;
    
    final float mScaleX;
    
    final float mScaleY;
    
    final float mTranslationX;
    
    final float mTranslationY;
    
    final float mTranslationZ;
    
    Transforms(View param1View) {
      this.mTranslationX = param1View.getTranslationX();
      this.mTranslationY = param1View.getTranslationY();
      this.mTranslationZ = ViewCompat.getTranslationZ(param1View);
      this.mScaleX = param1View.getScaleX();
      this.mScaleY = param1View.getScaleY();
      this.mRotationX = param1View.getRotationX();
      this.mRotationY = param1View.getRotationY();
      this.mRotationZ = param1View.getRotation();
    }
    
    public boolean equals(Object param1Object) {
      boolean bool = param1Object instanceof Transforms;
      boolean bool1 = false;
      if (!bool)
        return false; 
      param1Object = param1Object;
      bool = bool1;
      if (((Transforms)param1Object).mTranslationX == this.mTranslationX) {
        bool = bool1;
        if (((Transforms)param1Object).mTranslationY == this.mTranslationY) {
          bool = bool1;
          if (((Transforms)param1Object).mTranslationZ == this.mTranslationZ) {
            bool = bool1;
            if (((Transforms)param1Object).mScaleX == this.mScaleX) {
              bool = bool1;
              if (((Transforms)param1Object).mScaleY == this.mScaleY) {
                bool = bool1;
                if (((Transforms)param1Object).mRotationX == this.mRotationX) {
                  bool = bool1;
                  if (((Transforms)param1Object).mRotationY == this.mRotationY) {
                    bool = bool1;
                    if (((Transforms)param1Object).mRotationZ == this.mRotationZ)
                      bool = true; 
                  } 
                } 
              } 
            } 
          } 
        } 
      } 
      return bool;
    }
    
    public int hashCode() {
      byte b1;
      byte b2;
      byte b3;
      byte b4;
      byte b5;
      byte b6;
      byte b7;
      float f = this.mTranslationX;
      int i = 0;
      if (f != 0.0F) {
        b1 = Float.floatToIntBits(f);
      } else {
        b1 = 0;
      } 
      f = this.mTranslationY;
      if (f != 0.0F) {
        b2 = Float.floatToIntBits(f);
      } else {
        b2 = 0;
      } 
      f = this.mTranslationZ;
      if (f != 0.0F) {
        b3 = Float.floatToIntBits(f);
      } else {
        b3 = 0;
      } 
      f = this.mScaleX;
      if (f != 0.0F) {
        b4 = Float.floatToIntBits(f);
      } else {
        b4 = 0;
      } 
      f = this.mScaleY;
      if (f != 0.0F) {
        b5 = Float.floatToIntBits(f);
      } else {
        b5 = 0;
      } 
      f = this.mRotationX;
      if (f != 0.0F) {
        b6 = Float.floatToIntBits(f);
      } else {
        b6 = 0;
      } 
      f = this.mRotationY;
      if (f != 0.0F) {
        b7 = Float.floatToIntBits(f);
      } else {
        b7 = 0;
      } 
      f = this.mRotationZ;
      if (f != 0.0F)
        i = Float.floatToIntBits(f); 
      return ((((((b1 * 31 + b2) * 31 + b3) * 31 + b4) * 31 + b5) * 31 + b6) * 31 + b7) * 31 + i;
    }
    
    public void restore(View param1View) {
      ChangeTransform.setTransforms(param1View, this.mTranslationX, this.mTranslationY, this.mTranslationZ, this.mScaleX, this.mScaleY, this.mRotationX, this.mRotationY, this.mRotationZ);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\ChangeTransform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */