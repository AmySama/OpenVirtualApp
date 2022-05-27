package android.support.transition;

import android.view.View;

public abstract class VisibilityPropagation extends TransitionPropagation {
  private static final String PROPNAME_VIEW_CENTER = "android:visibilityPropagation:center";
  
  private static final String PROPNAME_VISIBILITY = "android:visibilityPropagation:visibility";
  
  private static final String[] VISIBILITY_PROPAGATION_VALUES = new String[] { "android:visibilityPropagation:visibility", "android:visibilityPropagation:center" };
  
  private static int getViewCoordinate(TransitionValues paramTransitionValues, int paramInt) {
    if (paramTransitionValues == null)
      return -1; 
    int[] arrayOfInt = (int[])paramTransitionValues.values.get("android:visibilityPropagation:center");
    return (arrayOfInt == null) ? -1 : arrayOfInt[paramInt];
  }
  
  public void captureValues(TransitionValues paramTransitionValues) {
    View view = paramTransitionValues.view;
    Integer integer1 = (Integer)paramTransitionValues.values.get("android:visibility:visibility");
    Integer integer2 = integer1;
    if (integer1 == null)
      integer2 = Integer.valueOf(view.getVisibility()); 
    paramTransitionValues.values.put("android:visibilityPropagation:visibility", integer2);
    int[] arrayOfInt = new int[2];
    view.getLocationOnScreen(arrayOfInt);
    arrayOfInt[0] = arrayOfInt[0] + Math.round(view.getTranslationX());
    arrayOfInt[0] = arrayOfInt[0] + view.getWidth() / 2;
    arrayOfInt[1] = arrayOfInt[1] + Math.round(view.getTranslationY());
    arrayOfInt[1] = arrayOfInt[1] + view.getHeight() / 2;
    paramTransitionValues.values.put("android:visibilityPropagation:center", arrayOfInt);
  }
  
  public String[] getPropagationProperties() {
    return VISIBILITY_PROPAGATION_VALUES;
  }
  
  public int getViewVisibility(TransitionValues paramTransitionValues) {
    if (paramTransitionValues == null)
      return 8; 
    Integer integer = (Integer)paramTransitionValues.values.get("android:visibilityPropagation:visibility");
    return (integer == null) ? 8 : integer.intValue();
  }
  
  public int getViewX(TransitionValues paramTransitionValues) {
    return getViewCoordinate(paramTransitionValues, 0);
  }
  
  public int getViewY(TransitionValues paramTransitionValues) {
    return getViewCoordinate(paramTransitionValues, 1);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\VisibilityPropagation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */