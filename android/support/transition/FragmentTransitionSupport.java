package android.support.transition;

import android.graphics.Rect;
import android.support.v4.app.FragmentTransitionImpl;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class FragmentTransitionSupport extends FragmentTransitionImpl {
  private static boolean hasSimpleTarget(Transition paramTransition) {
    return (!isNullOrEmpty(paramTransition.getTargetIds()) || !isNullOrEmpty(paramTransition.getTargetNames()) || !isNullOrEmpty(paramTransition.getTargetTypes()));
  }
  
  public void addTarget(Object paramObject, View paramView) {
    if (paramObject != null)
      ((Transition)paramObject).addTarget(paramView); 
  }
  
  public void addTargets(Object paramObject, ArrayList<View> paramArrayList) {
    paramObject = paramObject;
    if (paramObject == null)
      return; 
    boolean bool = paramObject instanceof TransitionSet;
    int i = 0;
    int j = 0;
    if (bool) {
      paramObject = paramObject;
      i = paramObject.getTransitionCount();
      while (j < i) {
        addTargets(paramObject.getTransitionAt(j), paramArrayList);
        j++;
      } 
    } else if (!hasSimpleTarget((Transition)paramObject) && isNullOrEmpty(paramObject.getTargets())) {
      int k = paramArrayList.size();
      for (j = i; j < k; j++)
        paramObject.addTarget(paramArrayList.get(j)); 
    } 
  }
  
  public void beginDelayedTransition(ViewGroup paramViewGroup, Object paramObject) {
    TransitionManager.beginDelayedTransition(paramViewGroup, (Transition)paramObject);
  }
  
  public boolean canHandle(Object paramObject) {
    return paramObject instanceof Transition;
  }
  
  public Object cloneTransition(Object paramObject) {
    if (paramObject != null) {
      paramObject = ((Transition)paramObject).clone();
    } else {
      paramObject = null;
    } 
    return paramObject;
  }
  
  public Object mergeTransitionsInSequence(Object paramObject1, Object paramObject2, Object paramObject3) {
    paramObject1 = paramObject1;
    paramObject2 = paramObject2;
    paramObject3 = paramObject3;
    if (paramObject1 != null && paramObject2 != null) {
      paramObject1 = (new TransitionSet()).addTransition((Transition)paramObject1).addTransition((Transition)paramObject2).setOrdering(1);
    } else if (paramObject1 == null) {
      if (paramObject2 != null) {
        paramObject1 = paramObject2;
      } else {
        paramObject1 = null;
      } 
    } 
    if (paramObject3 != null) {
      paramObject2 = new TransitionSet();
      if (paramObject1 != null)
        paramObject2.addTransition((Transition)paramObject1); 
      paramObject2.addTransition((Transition)paramObject3);
      return paramObject2;
    } 
    return paramObject1;
  }
  
  public Object mergeTransitionsTogether(Object paramObject1, Object paramObject2, Object paramObject3) {
    TransitionSet transitionSet = new TransitionSet();
    if (paramObject1 != null)
      transitionSet.addTransition((Transition)paramObject1); 
    if (paramObject2 != null)
      transitionSet.addTransition((Transition)paramObject2); 
    if (paramObject3 != null)
      transitionSet.addTransition((Transition)paramObject3); 
    return transitionSet;
  }
  
  public void removeTarget(Object paramObject, View paramView) {
    if (paramObject != null)
      ((Transition)paramObject).removeTarget(paramView); 
  }
  
  public void replaceTargets(Object paramObject, ArrayList<View> paramArrayList1, ArrayList<View> paramArrayList2) {
    paramObject = paramObject;
    boolean bool = paramObject instanceof TransitionSet;
    int i = 0;
    int j = 0;
    if (bool) {
      paramObject = paramObject;
      i = paramObject.getTransitionCount();
      while (j < i) {
        replaceTargets(paramObject.getTransitionAt(j), paramArrayList1, paramArrayList2);
        j++;
      } 
    } else if (!hasSimpleTarget((Transition)paramObject)) {
      List<View> list = paramObject.getTargets();
      if (list.size() == paramArrayList1.size() && list.containsAll(paramArrayList1)) {
        if (paramArrayList2 == null) {
          j = 0;
        } else {
          j = paramArrayList2.size();
        } 
        while (i < j) {
          paramObject.addTarget(paramArrayList2.get(i));
          i++;
        } 
        for (j = paramArrayList1.size() - 1; j >= 0; j--)
          paramObject.removeTarget(paramArrayList1.get(j)); 
      } 
    } 
  }
  
  public void scheduleHideFragmentView(Object paramObject, final View fragmentView, final ArrayList<View> exitingViews) {
    ((Transition)paramObject).addListener(new Transition.TransitionListener() {
          public void onTransitionCancel(Transition param1Transition) {}
          
          public void onTransitionEnd(Transition param1Transition) {
            param1Transition.removeListener(this);
            fragmentView.setVisibility(8);
            int i = exitingViews.size();
            for (byte b = 0; b < i; b++)
              ((View)exitingViews.get(b)).setVisibility(0); 
          }
          
          public void onTransitionPause(Transition param1Transition) {}
          
          public void onTransitionResume(Transition param1Transition) {}
          
          public void onTransitionStart(Transition param1Transition) {}
        });
  }
  
  public void scheduleRemoveTargets(Object paramObject1, final Object enterTransition, final ArrayList<View> enteringViews, final Object exitTransition, final ArrayList<View> exitingViews, final Object sharedElementTransition, final ArrayList<View> sharedElementsIn) {
    ((Transition)paramObject1).addListener(new Transition.TransitionListener() {
          public void onTransitionCancel(Transition param1Transition) {}
          
          public void onTransitionEnd(Transition param1Transition) {}
          
          public void onTransitionPause(Transition param1Transition) {}
          
          public void onTransitionResume(Transition param1Transition) {}
          
          public void onTransitionStart(Transition param1Transition) {
            Object object = enterTransition;
            if (object != null)
              FragmentTransitionSupport.this.replaceTargets(object, enteringViews, (ArrayList<View>)null); 
            object = exitTransition;
            if (object != null)
              FragmentTransitionSupport.this.replaceTargets(object, exitingViews, (ArrayList<View>)null); 
            object = sharedElementTransition;
            if (object != null)
              FragmentTransitionSupport.this.replaceTargets(object, sharedElementsIn, (ArrayList<View>)null); 
          }
        });
  }
  
  public void setEpicenter(Object paramObject, final Rect epicenter) {
    if (paramObject != null)
      ((Transition)paramObject).setEpicenterCallback(new Transition.EpicenterCallback() {
            public Rect onGetEpicenter(Transition param1Transition) {
              Rect rect = epicenter;
              return (rect == null || rect.isEmpty()) ? null : epicenter;
            }
          }); 
  }
  
  public void setEpicenter(Object paramObject, View paramView) {
    if (paramView != null) {
      paramObject = paramObject;
      final Rect epicenter = new Rect();
      getBoundsOnScreen(paramView, rect);
      paramObject.setEpicenterCallback(new Transition.EpicenterCallback() {
            public Rect onGetEpicenter(Transition param1Transition) {
              return epicenter;
            }
          });
    } 
  }
  
  public void setSharedElementTargets(Object paramObject, View paramView, ArrayList<View> paramArrayList) {
    paramObject = paramObject;
    List<View> list = paramObject.getTargets();
    list.clear();
    int i = paramArrayList.size();
    for (byte b = 0; b < i; b++)
      bfsAddViewChildren(list, paramArrayList.get(b)); 
    list.add(paramView);
    paramArrayList.add(paramView);
    addTargets(paramObject, paramArrayList);
  }
  
  public void swapSharedElementTargets(Object paramObject, ArrayList<View> paramArrayList1, ArrayList<View> paramArrayList2) {
    paramObject = paramObject;
    if (paramObject != null) {
      paramObject.getTargets().clear();
      paramObject.getTargets().addAll(paramArrayList2);
      replaceTargets(paramObject, paramArrayList1, paramArrayList2);
    } 
  }
  
  public Object wrapTransitionInSet(Object paramObject) {
    if (paramObject == null)
      return null; 
    TransitionSet transitionSet = new TransitionSet();
    transitionSet.addTransition((Transition)paramObject);
    return transitionSet;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\FragmentTransitionSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */