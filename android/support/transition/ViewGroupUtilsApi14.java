package android.support.transition;

import android.animation.LayoutTransition;
import android.util.Log;
import android.view.ViewGroup;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ViewGroupUtilsApi14 implements ViewGroupUtilsImpl {
  private static final int LAYOUT_TRANSITION_CHANGING = 4;
  
  private static final String TAG = "ViewGroupUtilsApi14";
  
  private static Method sCancelMethod;
  
  private static boolean sCancelMethodFetched;
  
  private static LayoutTransition sEmptyLayoutTransition;
  
  private static Field sLayoutSuppressedField;
  
  private static boolean sLayoutSuppressedFieldFetched;
  
  private static void cancelLayoutTransition(LayoutTransition paramLayoutTransition) {
    if (!sCancelMethodFetched) {
      try {
        Method method1 = LayoutTransition.class.getDeclaredMethod("cancel", new Class[0]);
        sCancelMethod = method1;
        method1.setAccessible(true);
      } catch (NoSuchMethodException noSuchMethodException) {
        Log.i("ViewGroupUtilsApi14", "Failed to access cancel method by reflection");
      } 
      sCancelMethodFetched = true;
    } 
    Method method = sCancelMethod;
    if (method != null)
      try {
        method.invoke(paramLayoutTransition, new Object[0]);
      } catch (IllegalAccessException illegalAccessException) {
        Log.i("ViewGroupUtilsApi14", "Failed to access cancel method by reflection");
      } catch (InvocationTargetException invocationTargetException) {
        Log.i("ViewGroupUtilsApi14", "Failed to invoke cancel method by reflection");
      }  
  }
  
  public ViewGroupOverlayImpl getOverlay(ViewGroup paramViewGroup) {
    return ViewGroupOverlayApi14.createFrom(paramViewGroup);
  }
  
  public void suppressLayout(ViewGroup paramViewGroup, boolean paramBoolean) {
    // Byte code:
    //   0: getstatic android/support/transition/ViewGroupUtilsApi14.sEmptyLayoutTransition : Landroid/animation/LayoutTransition;
    //   3: astore_3
    //   4: iconst_0
    //   5: istore #4
    //   7: iconst_0
    //   8: istore #5
    //   10: aload_3
    //   11: ifnonnull -> 65
    //   14: new android/support/transition/ViewGroupUtilsApi14$1
    //   17: dup
    //   18: aload_0
    //   19: invokespecial <init> : (Landroid/support/transition/ViewGroupUtilsApi14;)V
    //   22: astore_3
    //   23: aload_3
    //   24: putstatic android/support/transition/ViewGroupUtilsApi14.sEmptyLayoutTransition : Landroid/animation/LayoutTransition;
    //   27: aload_3
    //   28: iconst_2
    //   29: aconst_null
    //   30: invokevirtual setAnimator : (ILandroid/animation/Animator;)V
    //   33: getstatic android/support/transition/ViewGroupUtilsApi14.sEmptyLayoutTransition : Landroid/animation/LayoutTransition;
    //   36: iconst_0
    //   37: aconst_null
    //   38: invokevirtual setAnimator : (ILandroid/animation/Animator;)V
    //   41: getstatic android/support/transition/ViewGroupUtilsApi14.sEmptyLayoutTransition : Landroid/animation/LayoutTransition;
    //   44: iconst_1
    //   45: aconst_null
    //   46: invokevirtual setAnimator : (ILandroid/animation/Animator;)V
    //   49: getstatic android/support/transition/ViewGroupUtilsApi14.sEmptyLayoutTransition : Landroid/animation/LayoutTransition;
    //   52: iconst_3
    //   53: aconst_null
    //   54: invokevirtual setAnimator : (ILandroid/animation/Animator;)V
    //   57: getstatic android/support/transition/ViewGroupUtilsApi14.sEmptyLayoutTransition : Landroid/animation/LayoutTransition;
    //   60: iconst_4
    //   61: aconst_null
    //   62: invokevirtual setAnimator : (ILandroid/animation/Animator;)V
    //   65: iload_2
    //   66: ifeq -> 114
    //   69: aload_1
    //   70: invokevirtual getLayoutTransition : ()Landroid/animation/LayoutTransition;
    //   73: astore_3
    //   74: aload_3
    //   75: ifnull -> 104
    //   78: aload_3
    //   79: invokevirtual isRunning : ()Z
    //   82: ifeq -> 89
    //   85: aload_3
    //   86: invokestatic cancelLayoutTransition : (Landroid/animation/LayoutTransition;)V
    //   89: aload_3
    //   90: getstatic android/support/transition/ViewGroupUtilsApi14.sEmptyLayoutTransition : Landroid/animation/LayoutTransition;
    //   93: if_acmpeq -> 104
    //   96: aload_1
    //   97: getstatic android/support/transition/R$id.transition_layout_save : I
    //   100: aload_3
    //   101: invokevirtual setTag : (ILjava/lang/Object;)V
    //   104: aload_1
    //   105: getstatic android/support/transition/ViewGroupUtilsApi14.sEmptyLayoutTransition : Landroid/animation/LayoutTransition;
    //   108: invokevirtual setLayoutTransition : (Landroid/animation/LayoutTransition;)V
    //   111: goto -> 241
    //   114: aload_1
    //   115: aconst_null
    //   116: invokevirtual setLayoutTransition : (Landroid/animation/LayoutTransition;)V
    //   119: getstatic android/support/transition/ViewGroupUtilsApi14.sLayoutSuppressedFieldFetched : Z
    //   122: ifne -> 158
    //   125: ldc android/view/ViewGroup
    //   127: ldc 'mLayoutSuppressed'
    //   129: invokevirtual getDeclaredField : (Ljava/lang/String;)Ljava/lang/reflect/Field;
    //   132: astore_3
    //   133: aload_3
    //   134: putstatic android/support/transition/ViewGroupUtilsApi14.sLayoutSuppressedField : Ljava/lang/reflect/Field;
    //   137: aload_3
    //   138: iconst_1
    //   139: invokevirtual setAccessible : (Z)V
    //   142: goto -> 154
    //   145: astore_3
    //   146: ldc 'ViewGroupUtilsApi14'
    //   148: ldc 'Failed to access mLayoutSuppressed field by reflection'
    //   150: invokestatic i : (Ljava/lang/String;Ljava/lang/String;)I
    //   153: pop
    //   154: iconst_1
    //   155: putstatic android/support/transition/ViewGroupUtilsApi14.sLayoutSuppressedFieldFetched : Z
    //   158: getstatic android/support/transition/ViewGroupUtilsApi14.sLayoutSuppressedField : Ljava/lang/reflect/Field;
    //   161: astore_3
    //   162: iload #4
    //   164: istore_2
    //   165: aload_3
    //   166: ifnull -> 205
    //   169: aload_3
    //   170: aload_1
    //   171: invokevirtual getBoolean : (Ljava/lang/Object;)Z
    //   174: istore_2
    //   175: iload_2
    //   176: ifeq -> 194
    //   179: getstatic android/support/transition/ViewGroupUtilsApi14.sLayoutSuppressedField : Ljava/lang/reflect/Field;
    //   182: aload_1
    //   183: iconst_0
    //   184: invokevirtual setBoolean : (Ljava/lang/Object;Z)V
    //   187: goto -> 194
    //   190: astore_3
    //   191: goto -> 197
    //   194: goto -> 205
    //   197: ldc 'ViewGroupUtilsApi14'
    //   199: ldc 'Failed to get mLayoutSuppressed field by reflection'
    //   201: invokestatic i : (Ljava/lang/String;Ljava/lang/String;)I
    //   204: pop
    //   205: iload_2
    //   206: ifeq -> 213
    //   209: aload_1
    //   210: invokevirtual requestLayout : ()V
    //   213: aload_1
    //   214: getstatic android/support/transition/R$id.transition_layout_save : I
    //   217: invokevirtual getTag : (I)Ljava/lang/Object;
    //   220: checkcast android/animation/LayoutTransition
    //   223: astore_3
    //   224: aload_3
    //   225: ifnull -> 241
    //   228: aload_1
    //   229: getstatic android/support/transition/R$id.transition_layout_save : I
    //   232: aconst_null
    //   233: invokevirtual setTag : (ILjava/lang/Object;)V
    //   236: aload_1
    //   237: aload_3
    //   238: invokevirtual setLayoutTransition : (Landroid/animation/LayoutTransition;)V
    //   241: return
    //   242: astore_3
    //   243: iload #5
    //   245: istore_2
    //   246: goto -> 197
    // Exception table:
    //   from	to	target	type
    //   125	142	145	java/lang/NoSuchFieldException
    //   169	175	242	java/lang/IllegalAccessException
    //   179	187	190	java/lang/IllegalAccessException
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\transition\ViewGroupUtilsApi14.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */