package android.support.transition;

import android.view.ViewGroup;

public abstract class TransitionPropagation {
  public abstract void captureValues(TransitionValues paramTransitionValues);
  
  public abstract String[] getPropagationProperties();
  
  public abstract long getStartDelay(ViewGroup paramViewGroup, Transition paramTransition, TransitionValues paramTransitionValues1, TransitionValues paramTransitionValues2);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\TransitionPropagation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */