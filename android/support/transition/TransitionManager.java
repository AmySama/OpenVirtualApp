package android.support.transition;

import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

public class TransitionManager {
  private static final String LOG_TAG = "TransitionManager";
  
  private static Transition sDefaultTransition = new AutoTransition();
  
  private static ArrayList<ViewGroup> sPendingTransitions;
  
  private static ThreadLocal<WeakReference<ArrayMap<ViewGroup, ArrayList<Transition>>>> sRunningTransitions = new ThreadLocal<WeakReference<ArrayMap<ViewGroup, ArrayList<Transition>>>>();
  
  private ArrayMap<Scene, ArrayMap<Scene, Transition>> mScenePairTransitions = new ArrayMap();
  
  private ArrayMap<Scene, Transition> mSceneTransitions = new ArrayMap();
  
  static {
    sPendingTransitions = new ArrayList<ViewGroup>();
  }
  
  public static void beginDelayedTransition(ViewGroup paramViewGroup) {
    beginDelayedTransition(paramViewGroup, null);
  }
  
  public static void beginDelayedTransition(ViewGroup paramViewGroup, Transition paramTransition) {
    if (!sPendingTransitions.contains(paramViewGroup) && ViewCompat.isLaidOut((View)paramViewGroup)) {
      sPendingTransitions.add(paramViewGroup);
      Transition transition = paramTransition;
      if (paramTransition == null)
        transition = sDefaultTransition; 
      paramTransition = transition.clone();
      sceneChangeSetup(paramViewGroup, paramTransition);
      Scene.setCurrentScene((View)paramViewGroup, null);
      sceneChangeRunTransition(paramViewGroup, paramTransition);
    } 
  }
  
  private static void changeScene(Scene paramScene, Transition paramTransition) {
    ViewGroup viewGroup = paramScene.getSceneRoot();
    if (!sPendingTransitions.contains(viewGroup))
      if (paramTransition == null) {
        paramScene.enter();
      } else {
        sPendingTransitions.add(viewGroup);
        Transition transition = paramTransition.clone();
        transition.setSceneRoot(viewGroup);
        Scene scene = Scene.getCurrentScene((View)viewGroup);
        if (scene != null && scene.isCreatedFromLayoutResource())
          transition.setCanRemoveViews(true); 
        sceneChangeSetup(viewGroup, transition);
        paramScene.enter();
        sceneChangeRunTransition(viewGroup, transition);
      }  
  }
  
  public static void endTransitions(ViewGroup paramViewGroup) {
    sPendingTransitions.remove(paramViewGroup);
    ArrayList<?> arrayList = (ArrayList)getRunningTransitions().get(paramViewGroup);
    if (arrayList != null && !arrayList.isEmpty()) {
      arrayList = new ArrayList(arrayList);
      for (int i = arrayList.size() - 1; i >= 0; i--)
        ((Transition)arrayList.get(i)).forceToEnd(paramViewGroup); 
    } 
  }
  
  static ArrayMap<ViewGroup, ArrayList<Transition>> getRunningTransitions() {
    WeakReference<ArrayMap> weakReference1 = (WeakReference)sRunningTransitions.get();
    if (weakReference1 != null) {
      WeakReference<ArrayMap> weakReference = weakReference1;
      if (weakReference1.get() == null) {
        weakReference = new WeakReference<ArrayMap>(new ArrayMap());
        sRunningTransitions.set(weakReference);
        return weakReference.get();
      } 
      return weakReference.get();
    } 
    WeakReference<ArrayMap> weakReference2 = new WeakReference<ArrayMap>(new ArrayMap());
    sRunningTransitions.set(weakReference2);
    return weakReference2.get();
  }
  
  private Transition getTransition(Scene paramScene) {
    ViewGroup viewGroup = paramScene.getSceneRoot();
    if (viewGroup != null) {
      Scene scene = Scene.getCurrentScene((View)viewGroup);
      if (scene != null) {
        ArrayMap arrayMap = (ArrayMap)this.mScenePairTransitions.get(paramScene);
        if (arrayMap != null) {
          Transition transition1 = (Transition)arrayMap.get(scene);
          if (transition1 != null)
            return transition1; 
        } 
      } 
    } 
    Transition transition = (Transition)this.mSceneTransitions.get(paramScene);
    if (transition == null)
      transition = sDefaultTransition; 
    return transition;
  }
  
  public static void go(Scene paramScene) {
    changeScene(paramScene, sDefaultTransition);
  }
  
  public static void go(Scene paramScene, Transition paramTransition) {
    changeScene(paramScene, paramTransition);
  }
  
  private static void sceneChangeRunTransition(ViewGroup paramViewGroup, Transition paramTransition) {
    if (paramTransition != null && paramViewGroup != null) {
      MultiListener multiListener = new MultiListener(paramTransition, paramViewGroup);
      paramViewGroup.addOnAttachStateChangeListener(multiListener);
      paramViewGroup.getViewTreeObserver().addOnPreDrawListener(multiListener);
    } 
  }
  
  private static void sceneChangeSetup(ViewGroup paramViewGroup, Transition paramTransition) {
    ArrayList arrayList = (ArrayList)getRunningTransitions().get(paramViewGroup);
    if (arrayList != null && arrayList.size() > 0) {
      Iterator<Transition> iterator = arrayList.iterator();
      while (iterator.hasNext())
        ((Transition)iterator.next()).pause((View)paramViewGroup); 
    } 
    if (paramTransition != null)
      paramTransition.captureValues(paramViewGroup, true); 
    Scene scene = Scene.getCurrentScene((View)paramViewGroup);
    if (scene != null)
      scene.exit(); 
  }
  
  public void setTransition(Scene paramScene1, Scene paramScene2, Transition paramTransition) {
    ArrayMap arrayMap1 = (ArrayMap)this.mScenePairTransitions.get(paramScene2);
    ArrayMap arrayMap2 = arrayMap1;
    if (arrayMap1 == null) {
      arrayMap2 = new ArrayMap();
      this.mScenePairTransitions.put(paramScene2, arrayMap2);
    } 
    arrayMap2.put(paramScene1, paramTransition);
  }
  
  public void setTransition(Scene paramScene, Transition paramTransition) {
    this.mSceneTransitions.put(paramScene, paramTransition);
  }
  
  public void transitionTo(Scene paramScene) {
    changeScene(paramScene, getTransition(paramScene));
  }
  
  private static class MultiListener implements ViewTreeObserver.OnPreDrawListener, View.OnAttachStateChangeListener {
    ViewGroup mSceneRoot;
    
    Transition mTransition;
    
    MultiListener(Transition param1Transition, ViewGroup param1ViewGroup) {
      this.mTransition = param1Transition;
      this.mSceneRoot = param1ViewGroup;
    }
    
    private void removeListeners() {
      this.mSceneRoot.getViewTreeObserver().removeOnPreDrawListener(this);
      this.mSceneRoot.removeOnAttachStateChangeListener(this);
    }
    
    public boolean onPreDraw() {
      ArrayList<?> arrayList2;
      removeListeners();
      if (!TransitionManager.sPendingTransitions.remove(this.mSceneRoot))
        return true; 
      final ArrayMap<ViewGroup, ArrayList<Transition>> runningTransitions = TransitionManager.getRunningTransitions();
      ArrayList<?> arrayList1 = (ArrayList)arrayMap.get(this.mSceneRoot);
      ArrayList arrayList = null;
      if (arrayList1 == null) {
        arrayList2 = new ArrayList();
        arrayMap.put(this.mSceneRoot, arrayList2);
      } else {
        arrayList2 = arrayList1;
        if (arrayList1.size() > 0) {
          arrayList = new ArrayList(arrayList1);
          arrayList2 = arrayList1;
        } 
      } 
      arrayList2.add(this.mTransition);
      this.mTransition.addListener(new TransitionListenerAdapter() {
            public void onTransitionEnd(Transition param2Transition) {
              ((ArrayList)runningTransitions.get(TransitionManager.MultiListener.this.mSceneRoot)).remove(param2Transition);
            }
          });
      this.mTransition.captureValues(this.mSceneRoot, false);
      if (arrayList != null) {
        Iterator<Transition> iterator = arrayList.iterator();
        while (iterator.hasNext())
          ((Transition)iterator.next()).resume((View)this.mSceneRoot); 
      } 
      this.mTransition.playTransition(this.mSceneRoot);
      return true;
    }
    
    public void onViewAttachedToWindow(View param1View) {}
    
    public void onViewDetachedFromWindow(View param1View) {
      removeListeners();
      TransitionManager.sPendingTransitions.remove(this.mSceneRoot);
      ArrayList arrayList = (ArrayList)TransitionManager.getRunningTransitions().get(this.mSceneRoot);
      if (arrayList != null && arrayList.size() > 0) {
        Iterator<Transition> iterator = arrayList.iterator();
        while (iterator.hasNext())
          ((Transition)iterator.next()).resume((View)this.mSceneRoot); 
      } 
      this.mTransition.clearValues(true);
    }
  }
  
  class null extends TransitionListenerAdapter {
    public void onTransitionEnd(Transition param1Transition) {
      ((ArrayList)runningTransitions.get(this.this$0.mSceneRoot)).remove(param1Transition);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\TransitionManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */