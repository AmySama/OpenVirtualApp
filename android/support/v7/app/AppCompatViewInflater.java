package android.support.v7.app;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class AppCompatViewInflater {
  private static final String LOG_TAG = "AppCompatViewInflater";
  
  private static final String[] sClassPrefixList;
  
  private static final Map<String, Constructor<? extends View>> sConstructorMap;
  
  private static final Class<?>[] sConstructorSignature = new Class[] { Context.class, AttributeSet.class };
  
  private static final int[] sOnClickAttrs = new int[] { 16843375 };
  
  private final Object[] mConstructorArgs = new Object[2];
  
  static {
    sClassPrefixList = new String[] { "android.widget.", "android.view.", "android.webkit." };
    sConstructorMap = (Map<String, Constructor<? extends View>>)new ArrayMap();
  }
  
  private void checkOnClickListener(View paramView, AttributeSet paramAttributeSet) {
    Context context = paramView.getContext();
    if (context instanceof ContextWrapper && (Build.VERSION.SDK_INT < 15 || ViewCompat.hasOnClickListeners(paramView))) {
      TypedArray typedArray = context.obtainStyledAttributes(paramAttributeSet, sOnClickAttrs);
      String str = typedArray.getString(0);
      if (str != null)
        paramView.setOnClickListener(new DeclaredOnClickListener(paramView, str)); 
      typedArray.recycle();
    } 
  }
  
  private View createViewByPrefix(Context paramContext, String paramString1, String paramString2) throws ClassNotFoundException, InflateException {
    Constructor constructor = sConstructorMap.get(paramString1);
    Constructor<? extends View> constructor1 = constructor;
    if (constructor == null)
      try {
        String str;
        ClassLoader classLoader = paramContext.getClassLoader();
        if (paramString2 != null) {
          StringBuilder stringBuilder = new StringBuilder();
          this();
          stringBuilder.append(paramString2);
          stringBuilder.append(paramString1);
          str = stringBuilder.toString();
        } else {
          str = paramString1;
        } 
        constructor1 = classLoader.loadClass(str).<View>asSubclass(View.class).getConstructor(sConstructorSignature);
        sConstructorMap.put(paramString1, constructor1);
        constructor1.setAccessible(true);
        return constructor1.newInstance(this.mConstructorArgs);
      } catch (Exception exception) {
        return null;
      }  
    constructor1.setAccessible(true);
    return constructor1.newInstance(this.mConstructorArgs);
  }
  
  private View createViewFromTag(Context paramContext, String paramString, AttributeSet paramAttributeSet) {
    String str = paramString;
    if (paramString.equals("view"))
      str = paramAttributeSet.getAttributeValue(null, "class"); 
    try {
      Object[] arrayOfObject;
      this.mConstructorArgs[0] = paramContext;
      this.mConstructorArgs[1] = paramAttributeSet;
      if (-1 == str.indexOf('.')) {
        for (byte b = 0; b < sClassPrefixList.length; b++) {
          View view = createViewByPrefix(paramContext, str, sClassPrefixList[b]);
          if (view != null)
            return view; 
        } 
        return null;
      } 
      return createViewByPrefix((Context)arrayOfObject, str, null);
    } catch (Exception exception) {
      return null;
    } finally {
      Object[] arrayOfObject = this.mConstructorArgs;
      arrayOfObject[0] = null;
      arrayOfObject[1] = null;
    } 
  }
  
  private static Context themifyContext(Context paramContext, AttributeSet paramAttributeSet, boolean paramBoolean1, boolean paramBoolean2) {
    int i;
    TypedArray typedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.View, 0, 0);
    if (paramBoolean1) {
      i = typedArray.getResourceId(R.styleable.View_android_theme, 0);
    } else {
      i = 0;
    } 
    int j = i;
    if (paramBoolean2) {
      j = i;
      if (!i) {
        i = typedArray.getResourceId(R.styleable.View_theme, 0);
        j = i;
        if (i != 0) {
          Log.i("AppCompatViewInflater", "app:theme is now deprecated. Please move to using android:theme instead.");
          j = i;
        } 
      } 
    } 
    typedArray.recycle();
    Context context = paramContext;
    if (j != 0) {
      if (paramContext instanceof ContextThemeWrapper) {
        context = paramContext;
        return (Context)((((ContextThemeWrapper)paramContext).getThemeResId() != j) ? new ContextThemeWrapper(paramContext, j) : context);
      } 
    } else {
      return context;
    } 
    return (Context)new ContextThemeWrapper(paramContext, j);
  }
  
  private void verifyNotNull(View paramView, String paramString) {
    if (paramView != null)
      return; 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(getClass().getName());
    stringBuilder.append(" asked to inflate view for <");
    stringBuilder.append(paramString);
    stringBuilder.append(">, but returned null");
    throw new IllegalStateException(stringBuilder.toString());
  }
  
  protected AppCompatAutoCompleteTextView createAutoCompleteTextView(Context paramContext, AttributeSet paramAttributeSet) {
    return new AppCompatAutoCompleteTextView(paramContext, paramAttributeSet);
  }
  
  protected AppCompatButton createButton(Context paramContext, AttributeSet paramAttributeSet) {
    return new AppCompatButton(paramContext, paramAttributeSet);
  }
  
  protected AppCompatCheckBox createCheckBox(Context paramContext, AttributeSet paramAttributeSet) {
    return new AppCompatCheckBox(paramContext, paramAttributeSet);
  }
  
  protected AppCompatCheckedTextView createCheckedTextView(Context paramContext, AttributeSet paramAttributeSet) {
    return new AppCompatCheckedTextView(paramContext, paramAttributeSet);
  }
  
  protected AppCompatEditText createEditText(Context paramContext, AttributeSet paramAttributeSet) {
    return new AppCompatEditText(paramContext, paramAttributeSet);
  }
  
  protected AppCompatImageButton createImageButton(Context paramContext, AttributeSet paramAttributeSet) {
    return new AppCompatImageButton(paramContext, paramAttributeSet);
  }
  
  protected AppCompatImageView createImageView(Context paramContext, AttributeSet paramAttributeSet) {
    return new AppCompatImageView(paramContext, paramAttributeSet);
  }
  
  protected AppCompatMultiAutoCompleteTextView createMultiAutoCompleteTextView(Context paramContext, AttributeSet paramAttributeSet) {
    return new AppCompatMultiAutoCompleteTextView(paramContext, paramAttributeSet);
  }
  
  protected AppCompatRadioButton createRadioButton(Context paramContext, AttributeSet paramAttributeSet) {
    return new AppCompatRadioButton(paramContext, paramAttributeSet);
  }
  
  protected AppCompatRatingBar createRatingBar(Context paramContext, AttributeSet paramAttributeSet) {
    return new AppCompatRatingBar(paramContext, paramAttributeSet);
  }
  
  protected AppCompatSeekBar createSeekBar(Context paramContext, AttributeSet paramAttributeSet) {
    return new AppCompatSeekBar(paramContext, paramAttributeSet);
  }
  
  protected AppCompatSpinner createSpinner(Context paramContext, AttributeSet paramAttributeSet) {
    return new AppCompatSpinner(paramContext, paramAttributeSet);
  }
  
  protected AppCompatTextView createTextView(Context paramContext, AttributeSet paramAttributeSet) {
    return new AppCompatTextView(paramContext, paramAttributeSet);
  }
  
  protected View createView(Context paramContext, String paramString, AttributeSet paramAttributeSet) {
    return null;
  }
  
  final View createView(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4) {
    // Byte code:
    //   0: iload #5
    //   2: ifeq -> 18
    //   5: aload_1
    //   6: ifnull -> 18
    //   9: aload_1
    //   10: invokevirtual getContext : ()Landroid/content/Context;
    //   13: astore #9
    //   15: goto -> 21
    //   18: aload_3
    //   19: astore #9
    //   21: iload #6
    //   23: ifne -> 34
    //   26: aload #9
    //   28: astore_1
    //   29: iload #7
    //   31: ifeq -> 46
    //   34: aload #9
    //   36: aload #4
    //   38: iload #6
    //   40: iload #7
    //   42: invokestatic themifyContext : (Landroid/content/Context;Landroid/util/AttributeSet;ZZ)Landroid/content/Context;
    //   45: astore_1
    //   46: aload_1
    //   47: astore #9
    //   49: iload #8
    //   51: ifeq -> 60
    //   54: aload_1
    //   55: invokestatic wrap : (Landroid/content/Context;)Landroid/content/Context;
    //   58: astore #9
    //   60: iconst_m1
    //   61: istore #10
    //   63: aload_2
    //   64: invokevirtual hashCode : ()I
    //   67: lookupswitch default -> 180, -1946472170 -> 381, -1455429095 -> 364, -1346021293 -> 347, -938935918 -> 331, -937446323 -> 315, -658531749 -> 298, -339785223 -> 282, 776382189 -> 265, 1125864064 -> 249, 1413872058 -> 232, 1601505219 -> 215, 1666676343 -> 199, 2001146706 -> 183
    //   180: goto -> 395
    //   183: aload_2
    //   184: ldc_w 'Button'
    //   187: invokevirtual equals : (Ljava/lang/Object;)Z
    //   190: ifeq -> 395
    //   193: iconst_2
    //   194: istore #10
    //   196: goto -> 395
    //   199: aload_2
    //   200: ldc_w 'EditText'
    //   203: invokevirtual equals : (Ljava/lang/Object;)Z
    //   206: ifeq -> 395
    //   209: iconst_3
    //   210: istore #10
    //   212: goto -> 395
    //   215: aload_2
    //   216: ldc_w 'CheckBox'
    //   219: invokevirtual equals : (Ljava/lang/Object;)Z
    //   222: ifeq -> 395
    //   225: bipush #6
    //   227: istore #10
    //   229: goto -> 395
    //   232: aload_2
    //   233: ldc_w 'AutoCompleteTextView'
    //   236: invokevirtual equals : (Ljava/lang/Object;)Z
    //   239: ifeq -> 395
    //   242: bipush #9
    //   244: istore #10
    //   246: goto -> 395
    //   249: aload_2
    //   250: ldc_w 'ImageView'
    //   253: invokevirtual equals : (Ljava/lang/Object;)Z
    //   256: ifeq -> 395
    //   259: iconst_1
    //   260: istore #10
    //   262: goto -> 395
    //   265: aload_2
    //   266: ldc_w 'RadioButton'
    //   269: invokevirtual equals : (Ljava/lang/Object;)Z
    //   272: ifeq -> 395
    //   275: bipush #7
    //   277: istore #10
    //   279: goto -> 395
    //   282: aload_2
    //   283: ldc_w 'Spinner'
    //   286: invokevirtual equals : (Ljava/lang/Object;)Z
    //   289: ifeq -> 395
    //   292: iconst_4
    //   293: istore #10
    //   295: goto -> 395
    //   298: aload_2
    //   299: ldc_w 'SeekBar'
    //   302: invokevirtual equals : (Ljava/lang/Object;)Z
    //   305: ifeq -> 395
    //   308: bipush #12
    //   310: istore #10
    //   312: goto -> 395
    //   315: aload_2
    //   316: ldc_w 'ImageButton'
    //   319: invokevirtual equals : (Ljava/lang/Object;)Z
    //   322: ifeq -> 395
    //   325: iconst_5
    //   326: istore #10
    //   328: goto -> 395
    //   331: aload_2
    //   332: ldc_w 'TextView'
    //   335: invokevirtual equals : (Ljava/lang/Object;)Z
    //   338: ifeq -> 395
    //   341: iconst_0
    //   342: istore #10
    //   344: goto -> 395
    //   347: aload_2
    //   348: ldc_w 'MultiAutoCompleteTextView'
    //   351: invokevirtual equals : (Ljava/lang/Object;)Z
    //   354: ifeq -> 395
    //   357: bipush #10
    //   359: istore #10
    //   361: goto -> 395
    //   364: aload_2
    //   365: ldc_w 'CheckedTextView'
    //   368: invokevirtual equals : (Ljava/lang/Object;)Z
    //   371: ifeq -> 395
    //   374: bipush #8
    //   376: istore #10
    //   378: goto -> 395
    //   381: aload_2
    //   382: ldc_w 'RatingBar'
    //   385: invokevirtual equals : (Ljava/lang/Object;)Z
    //   388: ifeq -> 395
    //   391: bipush #11
    //   393: istore #10
    //   395: iload #10
    //   397: tableswitch default -> 464, 0 -> 693, 1 -> 675, 2 -> 657, 3 -> 639, 4 -> 621, 5 -> 603, 6 -> 585, 7 -> 567, 8 -> 549, 9 -> 531, 10 -> 513, 11 -> 495, 12 -> 477
    //   464: aload_0
    //   465: aload #9
    //   467: aload_2
    //   468: aload #4
    //   470: invokevirtual createView : (Landroid/content/Context;Ljava/lang/String;Landroid/util/AttributeSet;)Landroid/view/View;
    //   473: astore_1
    //   474: goto -> 708
    //   477: aload_0
    //   478: aload #9
    //   480: aload #4
    //   482: invokevirtual createSeekBar : (Landroid/content/Context;Landroid/util/AttributeSet;)Landroid/support/v7/widget/AppCompatSeekBar;
    //   485: astore_1
    //   486: aload_0
    //   487: aload_1
    //   488: aload_2
    //   489: invokespecial verifyNotNull : (Landroid/view/View;Ljava/lang/String;)V
    //   492: goto -> 708
    //   495: aload_0
    //   496: aload #9
    //   498: aload #4
    //   500: invokevirtual createRatingBar : (Landroid/content/Context;Landroid/util/AttributeSet;)Landroid/support/v7/widget/AppCompatRatingBar;
    //   503: astore_1
    //   504: aload_0
    //   505: aload_1
    //   506: aload_2
    //   507: invokespecial verifyNotNull : (Landroid/view/View;Ljava/lang/String;)V
    //   510: goto -> 708
    //   513: aload_0
    //   514: aload #9
    //   516: aload #4
    //   518: invokevirtual createMultiAutoCompleteTextView : (Landroid/content/Context;Landroid/util/AttributeSet;)Landroid/support/v7/widget/AppCompatMultiAutoCompleteTextView;
    //   521: astore_1
    //   522: aload_0
    //   523: aload_1
    //   524: aload_2
    //   525: invokespecial verifyNotNull : (Landroid/view/View;Ljava/lang/String;)V
    //   528: goto -> 708
    //   531: aload_0
    //   532: aload #9
    //   534: aload #4
    //   536: invokevirtual createAutoCompleteTextView : (Landroid/content/Context;Landroid/util/AttributeSet;)Landroid/support/v7/widget/AppCompatAutoCompleteTextView;
    //   539: astore_1
    //   540: aload_0
    //   541: aload_1
    //   542: aload_2
    //   543: invokespecial verifyNotNull : (Landroid/view/View;Ljava/lang/String;)V
    //   546: goto -> 708
    //   549: aload_0
    //   550: aload #9
    //   552: aload #4
    //   554: invokevirtual createCheckedTextView : (Landroid/content/Context;Landroid/util/AttributeSet;)Landroid/support/v7/widget/AppCompatCheckedTextView;
    //   557: astore_1
    //   558: aload_0
    //   559: aload_1
    //   560: aload_2
    //   561: invokespecial verifyNotNull : (Landroid/view/View;Ljava/lang/String;)V
    //   564: goto -> 708
    //   567: aload_0
    //   568: aload #9
    //   570: aload #4
    //   572: invokevirtual createRadioButton : (Landroid/content/Context;Landroid/util/AttributeSet;)Landroid/support/v7/widget/AppCompatRadioButton;
    //   575: astore_1
    //   576: aload_0
    //   577: aload_1
    //   578: aload_2
    //   579: invokespecial verifyNotNull : (Landroid/view/View;Ljava/lang/String;)V
    //   582: goto -> 708
    //   585: aload_0
    //   586: aload #9
    //   588: aload #4
    //   590: invokevirtual createCheckBox : (Landroid/content/Context;Landroid/util/AttributeSet;)Landroid/support/v7/widget/AppCompatCheckBox;
    //   593: astore_1
    //   594: aload_0
    //   595: aload_1
    //   596: aload_2
    //   597: invokespecial verifyNotNull : (Landroid/view/View;Ljava/lang/String;)V
    //   600: goto -> 708
    //   603: aload_0
    //   604: aload #9
    //   606: aload #4
    //   608: invokevirtual createImageButton : (Landroid/content/Context;Landroid/util/AttributeSet;)Landroid/support/v7/widget/AppCompatImageButton;
    //   611: astore_1
    //   612: aload_0
    //   613: aload_1
    //   614: aload_2
    //   615: invokespecial verifyNotNull : (Landroid/view/View;Ljava/lang/String;)V
    //   618: goto -> 708
    //   621: aload_0
    //   622: aload #9
    //   624: aload #4
    //   626: invokevirtual createSpinner : (Landroid/content/Context;Landroid/util/AttributeSet;)Landroid/support/v7/widget/AppCompatSpinner;
    //   629: astore_1
    //   630: aload_0
    //   631: aload_1
    //   632: aload_2
    //   633: invokespecial verifyNotNull : (Landroid/view/View;Ljava/lang/String;)V
    //   636: goto -> 708
    //   639: aload_0
    //   640: aload #9
    //   642: aload #4
    //   644: invokevirtual createEditText : (Landroid/content/Context;Landroid/util/AttributeSet;)Landroid/support/v7/widget/AppCompatEditText;
    //   647: astore_1
    //   648: aload_0
    //   649: aload_1
    //   650: aload_2
    //   651: invokespecial verifyNotNull : (Landroid/view/View;Ljava/lang/String;)V
    //   654: goto -> 708
    //   657: aload_0
    //   658: aload #9
    //   660: aload #4
    //   662: invokevirtual createButton : (Landroid/content/Context;Landroid/util/AttributeSet;)Landroid/support/v7/widget/AppCompatButton;
    //   665: astore_1
    //   666: aload_0
    //   667: aload_1
    //   668: aload_2
    //   669: invokespecial verifyNotNull : (Landroid/view/View;Ljava/lang/String;)V
    //   672: goto -> 708
    //   675: aload_0
    //   676: aload #9
    //   678: aload #4
    //   680: invokevirtual createImageView : (Landroid/content/Context;Landroid/util/AttributeSet;)Landroid/support/v7/widget/AppCompatImageView;
    //   683: astore_1
    //   684: aload_0
    //   685: aload_1
    //   686: aload_2
    //   687: invokespecial verifyNotNull : (Landroid/view/View;Ljava/lang/String;)V
    //   690: goto -> 708
    //   693: aload_0
    //   694: aload #9
    //   696: aload #4
    //   698: invokevirtual createTextView : (Landroid/content/Context;Landroid/util/AttributeSet;)Landroid/support/v7/widget/AppCompatTextView;
    //   701: astore_1
    //   702: aload_0
    //   703: aload_1
    //   704: aload_2
    //   705: invokespecial verifyNotNull : (Landroid/view/View;Ljava/lang/String;)V
    //   708: aload_1
    //   709: astore #11
    //   711: aload_1
    //   712: ifnonnull -> 735
    //   715: aload_1
    //   716: astore #11
    //   718: aload_3
    //   719: aload #9
    //   721: if_acmpeq -> 735
    //   724: aload_0
    //   725: aload #9
    //   727: aload_2
    //   728: aload #4
    //   730: invokespecial createViewFromTag : (Landroid/content/Context;Ljava/lang/String;Landroid/util/AttributeSet;)Landroid/view/View;
    //   733: astore #11
    //   735: aload #11
    //   737: ifnull -> 748
    //   740: aload_0
    //   741: aload #11
    //   743: aload #4
    //   745: invokespecial checkOnClickListener : (Landroid/view/View;Landroid/util/AttributeSet;)V
    //   748: aload #11
    //   750: areturn
  }
  
  private static class DeclaredOnClickListener implements View.OnClickListener {
    private final View mHostView;
    
    private final String mMethodName;
    
    private Context mResolvedContext;
    
    private Method mResolvedMethod;
    
    public DeclaredOnClickListener(View param1View, String param1String) {
      this.mHostView = param1View;
      this.mMethodName = param1String;
    }
    
    private void resolveMethod(Context param1Context, String param1String) {
      String str;
      while (param1Context != null) {
        try {
          if (!param1Context.isRestricted()) {
            Method method = param1Context.getClass().getMethod(this.mMethodName, new Class[] { View.class });
            if (method != null) {
              this.mResolvedMethod = method;
              this.mResolvedContext = param1Context;
              return;
            } 
          } 
        } catch (NoSuchMethodException noSuchMethodException) {}
        if (param1Context instanceof ContextWrapper) {
          param1Context = ((ContextWrapper)param1Context).getBaseContext();
          continue;
        } 
        param1Context = null;
      } 
      int i = this.mHostView.getId();
      if (i == -1) {
        str = "";
      } else {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(" with id '");
        stringBuilder1.append(this.mHostView.getContext().getResources().getResourceEntryName(i));
        stringBuilder1.append("'");
        str = stringBuilder1.toString();
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Could not find method ");
      stringBuilder.append(this.mMethodName);
      stringBuilder.append("(View) in a parent or ancestor Context for android:onClick ");
      stringBuilder.append("attribute defined on view ");
      stringBuilder.append(this.mHostView.getClass());
      stringBuilder.append(str);
      throw new IllegalStateException(stringBuilder.toString());
    }
    
    public void onClick(View param1View) {
      if (this.mResolvedMethod == null)
        resolveMethod(this.mHostView.getContext(), this.mMethodName); 
      try {
        this.mResolvedMethod.invoke(this.mResolvedContext, new Object[] { param1View });
        return;
      } catch (IllegalAccessException illegalAccessException) {
        throw new IllegalStateException("Could not execute non-public method for android:onClick", illegalAccessException);
      } catch (InvocationTargetException invocationTargetException) {
        throw new IllegalStateException("Could not execute method for android:onClick", invocationTargetException);
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\app\AppCompatViewInflater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */