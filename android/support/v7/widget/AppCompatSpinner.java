package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.TintableBackgroundView;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.view.menu.ShowableListMenu;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.ThemedSpinnerAdapter;

public class AppCompatSpinner extends Spinner implements TintableBackgroundView {
  private static final int[] ATTRS_ANDROID_SPINNERMODE = new int[] { 16843505 };
  
  private static final int MAX_ITEMS_MEASURED = 15;
  
  private static final int MODE_DIALOG = 0;
  
  private static final int MODE_DROPDOWN = 1;
  
  private static final int MODE_THEME = -1;
  
  private static final String TAG = "AppCompatSpinner";
  
  private final AppCompatBackgroundHelper mBackgroundTintHelper;
  
  private int mDropDownWidth;
  
  private ForwardingListener mForwardingListener;
  
  private DropdownPopup mPopup;
  
  private final Context mPopupContext;
  
  private final boolean mPopupSet;
  
  private SpinnerAdapter mTempAdapter;
  
  private final Rect mTempRect;
  
  public AppCompatSpinner(Context paramContext) {
    this(paramContext, (AttributeSet)null);
  }
  
  public AppCompatSpinner(Context paramContext, int paramInt) {
    this(paramContext, (AttributeSet)null, R.attr.spinnerStyle, paramInt);
  }
  
  public AppCompatSpinner(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, R.attr.spinnerStyle);
  }
  
  public AppCompatSpinner(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    this(paramContext, paramAttributeSet, paramInt, -1);
  }
  
  public AppCompatSpinner(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2) {
    this(paramContext, paramAttributeSet, paramInt1, paramInt2, (Resources.Theme)null);
  }
  
  public AppCompatSpinner(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2, Resources.Theme paramTheme) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: aload_2
    //   3: iload_3
    //   4: invokespecial <init> : (Landroid/content/Context;Landroid/util/AttributeSet;I)V
    //   7: aload_0
    //   8: new android/graphics/Rect
    //   11: dup
    //   12: invokespecial <init> : ()V
    //   15: putfield mTempRect : Landroid/graphics/Rect;
    //   18: aload_1
    //   19: aload_2
    //   20: getstatic android/support/v7/appcompat/R$styleable.Spinner : [I
    //   23: iload_3
    //   24: iconst_0
    //   25: invokestatic obtainStyledAttributes : (Landroid/content/Context;Landroid/util/AttributeSet;[III)Landroid/support/v7/widget/TintTypedArray;
    //   28: astore #6
    //   30: aload_0
    //   31: new android/support/v7/widget/AppCompatBackgroundHelper
    //   34: dup
    //   35: aload_0
    //   36: invokespecial <init> : (Landroid/view/View;)V
    //   39: putfield mBackgroundTintHelper : Landroid/support/v7/widget/AppCompatBackgroundHelper;
    //   42: aconst_null
    //   43: astore #7
    //   45: aload #5
    //   47: ifnull -> 67
    //   50: aload_0
    //   51: new android/support/v7/view/ContextThemeWrapper
    //   54: dup
    //   55: aload_1
    //   56: aload #5
    //   58: invokespecial <init> : (Landroid/content/Context;Landroid/content/res/Resources$Theme;)V
    //   61: putfield mPopupContext : Landroid/content/Context;
    //   64: goto -> 123
    //   67: aload #6
    //   69: getstatic android/support/v7/appcompat/R$styleable.Spinner_popupTheme : I
    //   72: iconst_0
    //   73: invokevirtual getResourceId : (II)I
    //   76: istore #8
    //   78: iload #8
    //   80: ifeq -> 100
    //   83: aload_0
    //   84: new android/support/v7/view/ContextThemeWrapper
    //   87: dup
    //   88: aload_1
    //   89: iload #8
    //   91: invokespecial <init> : (Landroid/content/Context;I)V
    //   94: putfield mPopupContext : Landroid/content/Context;
    //   97: goto -> 123
    //   100: getstatic android/os/Build$VERSION.SDK_INT : I
    //   103: bipush #23
    //   105: if_icmpge -> 114
    //   108: aload_1
    //   109: astore #5
    //   111: goto -> 117
    //   114: aconst_null
    //   115: astore #5
    //   117: aload_0
    //   118: aload #5
    //   120: putfield mPopupContext : Landroid/content/Context;
    //   123: aload_0
    //   124: getfield mPopupContext : Landroid/content/Context;
    //   127: ifnull -> 363
    //   130: iload #4
    //   132: istore #9
    //   134: iload #4
    //   136: iconst_m1
    //   137: if_icmpne -> 260
    //   140: aload_1
    //   141: aload_2
    //   142: getstatic android/support/v7/widget/AppCompatSpinner.ATTRS_ANDROID_SPINNERMODE : [I
    //   145: iload_3
    //   146: iconst_0
    //   147: invokevirtual obtainStyledAttributes : (Landroid/util/AttributeSet;[III)Landroid/content/res/TypedArray;
    //   150: astore #5
    //   152: iload #4
    //   154: istore #8
    //   156: aload #5
    //   158: astore #7
    //   160: aload #5
    //   162: iconst_0
    //   163: invokevirtual hasValue : (I)Z
    //   166: ifeq -> 182
    //   169: aload #5
    //   171: astore #7
    //   173: aload #5
    //   175: iconst_0
    //   176: iconst_0
    //   177: invokevirtual getInt : (II)I
    //   180: istore #8
    //   182: iload #8
    //   184: istore #9
    //   186: aload #5
    //   188: ifnull -> 260
    //   191: iload #8
    //   193: istore #4
    //   195: aload #5
    //   197: invokevirtual recycle : ()V
    //   200: iload #4
    //   202: istore #9
    //   204: goto -> 260
    //   207: astore #10
    //   209: goto -> 221
    //   212: astore_1
    //   213: goto -> 248
    //   216: astore #10
    //   218: aconst_null
    //   219: astore #5
    //   221: aload #5
    //   223: astore #7
    //   225: ldc 'AppCompatSpinner'
    //   227: ldc 'Could not read android:spinnerMode'
    //   229: aload #10
    //   231: invokestatic i : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   234: pop
    //   235: iload #4
    //   237: istore #9
    //   239: aload #5
    //   241: ifnull -> 260
    //   244: goto -> 195
    //   247: astore_1
    //   248: aload #7
    //   250: ifnull -> 258
    //   253: aload #7
    //   255: invokevirtual recycle : ()V
    //   258: aload_1
    //   259: athrow
    //   260: iload #9
    //   262: iconst_1
    //   263: if_icmpne -> 363
    //   266: new android/support/v7/widget/AppCompatSpinner$DropdownPopup
    //   269: dup
    //   270: aload_0
    //   271: aload_0
    //   272: getfield mPopupContext : Landroid/content/Context;
    //   275: aload_2
    //   276: iload_3
    //   277: invokespecial <init> : (Landroid/support/v7/widget/AppCompatSpinner;Landroid/content/Context;Landroid/util/AttributeSet;I)V
    //   280: astore #5
    //   282: aload_0
    //   283: getfield mPopupContext : Landroid/content/Context;
    //   286: aload_2
    //   287: getstatic android/support/v7/appcompat/R$styleable.Spinner : [I
    //   290: iload_3
    //   291: iconst_0
    //   292: invokestatic obtainStyledAttributes : (Landroid/content/Context;Landroid/util/AttributeSet;[III)Landroid/support/v7/widget/TintTypedArray;
    //   295: astore #7
    //   297: aload_0
    //   298: aload #7
    //   300: getstatic android/support/v7/appcompat/R$styleable.Spinner_android_dropDownWidth : I
    //   303: bipush #-2
    //   305: invokevirtual getLayoutDimension : (II)I
    //   308: putfield mDropDownWidth : I
    //   311: aload #5
    //   313: aload #7
    //   315: getstatic android/support/v7/appcompat/R$styleable.Spinner_android_popupBackground : I
    //   318: invokevirtual getDrawable : (I)Landroid/graphics/drawable/Drawable;
    //   321: invokevirtual setBackgroundDrawable : (Landroid/graphics/drawable/Drawable;)V
    //   324: aload #5
    //   326: aload #6
    //   328: getstatic android/support/v7/appcompat/R$styleable.Spinner_android_prompt : I
    //   331: invokevirtual getString : (I)Ljava/lang/String;
    //   334: invokevirtual setPromptText : (Ljava/lang/CharSequence;)V
    //   337: aload #7
    //   339: invokevirtual recycle : ()V
    //   342: aload_0
    //   343: aload #5
    //   345: putfield mPopup : Landroid/support/v7/widget/AppCompatSpinner$DropdownPopup;
    //   348: aload_0
    //   349: new android/support/v7/widget/AppCompatSpinner$1
    //   352: dup
    //   353: aload_0
    //   354: aload_0
    //   355: aload #5
    //   357: invokespecial <init> : (Landroid/support/v7/widget/AppCompatSpinner;Landroid/view/View;Landroid/support/v7/widget/AppCompatSpinner$DropdownPopup;)V
    //   360: putfield mForwardingListener : Landroid/support/v7/widget/ForwardingListener;
    //   363: aload #6
    //   365: getstatic android/support/v7/appcompat/R$styleable.Spinner_android_entries : I
    //   368: invokevirtual getTextArray : (I)[Ljava/lang/CharSequence;
    //   371: astore #5
    //   373: aload #5
    //   375: ifnull -> 403
    //   378: new android/widget/ArrayAdapter
    //   381: dup
    //   382: aload_1
    //   383: ldc 17367048
    //   385: aload #5
    //   387: invokespecial <init> : (Landroid/content/Context;I[Ljava/lang/Object;)V
    //   390: astore_1
    //   391: aload_1
    //   392: getstatic android/support/v7/appcompat/R$layout.support_simple_spinner_dropdown_item : I
    //   395: invokevirtual setDropDownViewResource : (I)V
    //   398: aload_0
    //   399: aload_1
    //   400: invokevirtual setAdapter : (Landroid/widget/SpinnerAdapter;)V
    //   403: aload #6
    //   405: invokevirtual recycle : ()V
    //   408: aload_0
    //   409: iconst_1
    //   410: putfield mPopupSet : Z
    //   413: aload_0
    //   414: getfield mTempAdapter : Landroid/widget/SpinnerAdapter;
    //   417: astore_1
    //   418: aload_1
    //   419: ifnull -> 432
    //   422: aload_0
    //   423: aload_1
    //   424: invokevirtual setAdapter : (Landroid/widget/SpinnerAdapter;)V
    //   427: aload_0
    //   428: aconst_null
    //   429: putfield mTempAdapter : Landroid/widget/SpinnerAdapter;
    //   432: aload_0
    //   433: getfield mBackgroundTintHelper : Landroid/support/v7/widget/AppCompatBackgroundHelper;
    //   436: aload_2
    //   437: iload_3
    //   438: invokevirtual loadFromAttributes : (Landroid/util/AttributeSet;I)V
    //   441: return
    // Exception table:
    //   from	to	target	type
    //   140	152	216	java/lang/Exception
    //   140	152	212	finally
    //   160	169	207	java/lang/Exception
    //   160	169	247	finally
    //   173	182	207	java/lang/Exception
    //   173	182	247	finally
    //   225	235	247	finally
  }
  
  int compatMeasureContentWidth(SpinnerAdapter paramSpinnerAdapter, Drawable paramDrawable) {
    int i = 0;
    if (paramSpinnerAdapter == null)
      return 0; 
    int j = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 0);
    int k = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 0);
    int m = Math.max(0, getSelectedItemPosition());
    int n = Math.min(paramSpinnerAdapter.getCount(), m + 15);
    int i1 = Math.max(0, m - 15 - n - m);
    View view = null;
    m = 0;
    while (i1 < n) {
      int i2 = paramSpinnerAdapter.getItemViewType(i1);
      int i3 = i;
      if (i2 != i) {
        view = null;
        i3 = i2;
      } 
      view = paramSpinnerAdapter.getView(i1, view, (ViewGroup)this);
      if (view.getLayoutParams() == null)
        view.setLayoutParams(new ViewGroup.LayoutParams(-2, -2)); 
      view.measure(j, k);
      m = Math.max(m, view.getMeasuredWidth());
      i1++;
      i = i3;
    } 
    i1 = m;
    if (paramDrawable != null) {
      paramDrawable.getPadding(this.mTempRect);
      i1 = m + this.mTempRect.left + this.mTempRect.right;
    } 
    return i1;
  }
  
  protected void drawableStateChanged() {
    super.drawableStateChanged();
    AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
    if (appCompatBackgroundHelper != null)
      appCompatBackgroundHelper.applySupportBackgroundTint(); 
  }
  
  public int getDropDownHorizontalOffset() {
    DropdownPopup dropdownPopup = this.mPopup;
    return (dropdownPopup != null) ? dropdownPopup.getHorizontalOffset() : ((Build.VERSION.SDK_INT >= 16) ? super.getDropDownHorizontalOffset() : 0);
  }
  
  public int getDropDownVerticalOffset() {
    DropdownPopup dropdownPopup = this.mPopup;
    return (dropdownPopup != null) ? dropdownPopup.getVerticalOffset() : ((Build.VERSION.SDK_INT >= 16) ? super.getDropDownVerticalOffset() : 0);
  }
  
  public int getDropDownWidth() {
    return (this.mPopup != null) ? this.mDropDownWidth : ((Build.VERSION.SDK_INT >= 16) ? super.getDropDownWidth() : 0);
  }
  
  public Drawable getPopupBackground() {
    DropdownPopup dropdownPopup = this.mPopup;
    return (dropdownPopup != null) ? dropdownPopup.getBackground() : ((Build.VERSION.SDK_INT >= 16) ? super.getPopupBackground() : null);
  }
  
  public Context getPopupContext() {
    return (this.mPopup != null) ? this.mPopupContext : ((Build.VERSION.SDK_INT >= 23) ? super.getPopupContext() : null);
  }
  
  public CharSequence getPrompt() {
    CharSequence charSequence;
    DropdownPopup dropdownPopup = this.mPopup;
    if (dropdownPopup != null) {
      charSequence = dropdownPopup.getHintText();
    } else {
      charSequence = super.getPrompt();
    } 
    return charSequence;
  }
  
  public ColorStateList getSupportBackgroundTintList() {
    AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
    if (appCompatBackgroundHelper != null) {
      ColorStateList colorStateList = appCompatBackgroundHelper.getSupportBackgroundTintList();
    } else {
      appCompatBackgroundHelper = null;
    } 
    return (ColorStateList)appCompatBackgroundHelper;
  }
  
  public PorterDuff.Mode getSupportBackgroundTintMode() {
    AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
    if (appCompatBackgroundHelper != null) {
      PorterDuff.Mode mode = appCompatBackgroundHelper.getSupportBackgroundTintMode();
    } else {
      appCompatBackgroundHelper = null;
    } 
    return (PorterDuff.Mode)appCompatBackgroundHelper;
  }
  
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    DropdownPopup dropdownPopup = this.mPopup;
    if (dropdownPopup != null && dropdownPopup.isShowing())
      this.mPopup.dismiss(); 
  }
  
  protected void onMeasure(int paramInt1, int paramInt2) {
    super.onMeasure(paramInt1, paramInt2);
    if (this.mPopup != null && View.MeasureSpec.getMode(paramInt1) == Integer.MIN_VALUE)
      setMeasuredDimension(Math.min(Math.max(getMeasuredWidth(), compatMeasureContentWidth(getAdapter(), getBackground())), View.MeasureSpec.getSize(paramInt1)), getMeasuredHeight()); 
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent) {
    ForwardingListener forwardingListener = this.mForwardingListener;
    return (forwardingListener != null && forwardingListener.onTouch((View)this, paramMotionEvent)) ? true : super.onTouchEvent(paramMotionEvent);
  }
  
  public boolean performClick() {
    DropdownPopup dropdownPopup = this.mPopup;
    if (dropdownPopup != null) {
      if (!dropdownPopup.isShowing())
        this.mPopup.show(); 
      return true;
    } 
    return super.performClick();
  }
  
  public void setAdapter(SpinnerAdapter paramSpinnerAdapter) {
    if (!this.mPopupSet) {
      this.mTempAdapter = paramSpinnerAdapter;
      return;
    } 
    super.setAdapter(paramSpinnerAdapter);
    if (this.mPopup != null) {
      Context context1 = this.mPopupContext;
      Context context2 = context1;
      if (context1 == null)
        context2 = getContext(); 
      this.mPopup.setAdapter(new DropDownAdapter(paramSpinnerAdapter, context2.getTheme()));
    } 
  }
  
  public void setBackgroundDrawable(Drawable paramDrawable) {
    super.setBackgroundDrawable(paramDrawable);
    AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
    if (appCompatBackgroundHelper != null)
      appCompatBackgroundHelper.onSetBackgroundDrawable(paramDrawable); 
  }
  
  public void setBackgroundResource(int paramInt) {
    super.setBackgroundResource(paramInt);
    AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
    if (appCompatBackgroundHelper != null)
      appCompatBackgroundHelper.onSetBackgroundResource(paramInt); 
  }
  
  public void setDropDownHorizontalOffset(int paramInt) {
    DropdownPopup dropdownPopup = this.mPopup;
    if (dropdownPopup != null) {
      dropdownPopup.setHorizontalOffset(paramInt);
    } else if (Build.VERSION.SDK_INT >= 16) {
      super.setDropDownHorizontalOffset(paramInt);
    } 
  }
  
  public void setDropDownVerticalOffset(int paramInt) {
    DropdownPopup dropdownPopup = this.mPopup;
    if (dropdownPopup != null) {
      dropdownPopup.setVerticalOffset(paramInt);
    } else if (Build.VERSION.SDK_INT >= 16) {
      super.setDropDownVerticalOffset(paramInt);
    } 
  }
  
  public void setDropDownWidth(int paramInt) {
    if (this.mPopup != null) {
      this.mDropDownWidth = paramInt;
    } else if (Build.VERSION.SDK_INT >= 16) {
      super.setDropDownWidth(paramInt);
    } 
  }
  
  public void setPopupBackgroundDrawable(Drawable paramDrawable) {
    DropdownPopup dropdownPopup = this.mPopup;
    if (dropdownPopup != null) {
      dropdownPopup.setBackgroundDrawable(paramDrawable);
    } else if (Build.VERSION.SDK_INT >= 16) {
      super.setPopupBackgroundDrawable(paramDrawable);
    } 
  }
  
  public void setPopupBackgroundResource(int paramInt) {
    setPopupBackgroundDrawable(AppCompatResources.getDrawable(getPopupContext(), paramInt));
  }
  
  public void setPrompt(CharSequence paramCharSequence) {
    DropdownPopup dropdownPopup = this.mPopup;
    if (dropdownPopup != null) {
      dropdownPopup.setPromptText(paramCharSequence);
    } else {
      super.setPrompt(paramCharSequence);
    } 
  }
  
  public void setSupportBackgroundTintList(ColorStateList paramColorStateList) {
    AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
    if (appCompatBackgroundHelper != null)
      appCompatBackgroundHelper.setSupportBackgroundTintList(paramColorStateList); 
  }
  
  public void setSupportBackgroundTintMode(PorterDuff.Mode paramMode) {
    AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
    if (appCompatBackgroundHelper != null)
      appCompatBackgroundHelper.setSupportBackgroundTintMode(paramMode); 
  }
  
  private static class DropDownAdapter implements ListAdapter, SpinnerAdapter {
    private SpinnerAdapter mAdapter;
    
    private ListAdapter mListAdapter;
    
    public DropDownAdapter(SpinnerAdapter param1SpinnerAdapter, Resources.Theme param1Theme) {
      this.mAdapter = param1SpinnerAdapter;
      if (param1SpinnerAdapter instanceof ListAdapter)
        this.mListAdapter = (ListAdapter)param1SpinnerAdapter; 
      if (param1Theme != null) {
        ThemedSpinnerAdapter themedSpinnerAdapter;
        if (Build.VERSION.SDK_INT >= 23 && param1SpinnerAdapter instanceof ThemedSpinnerAdapter) {
          themedSpinnerAdapter = (ThemedSpinnerAdapter)param1SpinnerAdapter;
          if (themedSpinnerAdapter.getDropDownViewTheme() != param1Theme)
            themedSpinnerAdapter.setDropDownViewTheme(param1Theme); 
        } else if (themedSpinnerAdapter instanceof ThemedSpinnerAdapter) {
          ThemedSpinnerAdapter themedSpinnerAdapter1 = (ThemedSpinnerAdapter)themedSpinnerAdapter;
          if (themedSpinnerAdapter1.getDropDownViewTheme() == null)
            themedSpinnerAdapter1.setDropDownViewTheme(param1Theme); 
        } 
      } 
    }
    
    public boolean areAllItemsEnabled() {
      ListAdapter listAdapter = this.mListAdapter;
      return (listAdapter != null) ? listAdapter.areAllItemsEnabled() : true;
    }
    
    public int getCount() {
      int i;
      SpinnerAdapter spinnerAdapter = this.mAdapter;
      if (spinnerAdapter == null) {
        i = 0;
      } else {
        i = spinnerAdapter.getCount();
      } 
      return i;
    }
    
    public View getDropDownView(int param1Int, View param1View, ViewGroup param1ViewGroup) {
      SpinnerAdapter spinnerAdapter = this.mAdapter;
      if (spinnerAdapter == null) {
        param1View = null;
      } else {
        param1View = spinnerAdapter.getDropDownView(param1Int, param1View, param1ViewGroup);
      } 
      return param1View;
    }
    
    public Object getItem(int param1Int) {
      Object object = this.mAdapter;
      if (object == null) {
        object = null;
      } else {
        object = object.getItem(param1Int);
      } 
      return object;
    }
    
    public long getItemId(int param1Int) {
      long l;
      SpinnerAdapter spinnerAdapter = this.mAdapter;
      if (spinnerAdapter == null) {
        l = -1L;
      } else {
        l = spinnerAdapter.getItemId(param1Int);
      } 
      return l;
    }
    
    public int getItemViewType(int param1Int) {
      return 0;
    }
    
    public View getView(int param1Int, View param1View, ViewGroup param1ViewGroup) {
      return getDropDownView(param1Int, param1View, param1ViewGroup);
    }
    
    public int getViewTypeCount() {
      return 1;
    }
    
    public boolean hasStableIds() {
      boolean bool;
      SpinnerAdapter spinnerAdapter = this.mAdapter;
      if (spinnerAdapter != null && spinnerAdapter.hasStableIds()) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public boolean isEmpty() {
      boolean bool;
      if (getCount() == 0) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public boolean isEnabled(int param1Int) {
      ListAdapter listAdapter = this.mListAdapter;
      return (listAdapter != null) ? listAdapter.isEnabled(param1Int) : true;
    }
    
    public void registerDataSetObserver(DataSetObserver param1DataSetObserver) {
      SpinnerAdapter spinnerAdapter = this.mAdapter;
      if (spinnerAdapter != null)
        spinnerAdapter.registerDataSetObserver(param1DataSetObserver); 
    }
    
    public void unregisterDataSetObserver(DataSetObserver param1DataSetObserver) {
      SpinnerAdapter spinnerAdapter = this.mAdapter;
      if (spinnerAdapter != null)
        spinnerAdapter.unregisterDataSetObserver(param1DataSetObserver); 
    }
  }
  
  private class DropdownPopup extends ListPopupWindow {
    ListAdapter mAdapter;
    
    private CharSequence mHintText;
    
    private final Rect mVisibleRect = new Rect();
    
    public DropdownPopup(Context param1Context, AttributeSet param1AttributeSet, int param1Int) {
      super(param1Context, param1AttributeSet, param1Int);
      setAnchorView((View)AppCompatSpinner.this);
      setModal(true);
      setPromptPosition(0);
      setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> param2AdapterView, View param2View, int param2Int, long param2Long) {
              AppCompatSpinner.this.setSelection(param2Int);
              if (AppCompatSpinner.this.getOnItemClickListener() != null)
                AppCompatSpinner.this.performItemClick(param2View, param2Int, AppCompatSpinner.DropdownPopup.this.mAdapter.getItemId(param2Int)); 
              AppCompatSpinner.DropdownPopup.this.dismiss();
            }
          });
    }
    
    void computeContentWidth() {
      Drawable drawable = getBackground();
      int i = 0;
      if (drawable != null) {
        drawable.getPadding(AppCompatSpinner.this.mTempRect);
        if (ViewUtils.isLayoutRtl((View)AppCompatSpinner.this)) {
          i = AppCompatSpinner.this.mTempRect.right;
        } else {
          i = -AppCompatSpinner.this.mTempRect.left;
        } 
      } else {
        Rect rect = AppCompatSpinner.this.mTempRect;
        AppCompatSpinner.this.mTempRect.right = 0;
        rect.left = 0;
      } 
      int j = AppCompatSpinner.this.getPaddingLeft();
      int k = AppCompatSpinner.this.getPaddingRight();
      int m = AppCompatSpinner.this.getWidth();
      if (AppCompatSpinner.this.mDropDownWidth == -2) {
        int n = AppCompatSpinner.this.compatMeasureContentWidth((SpinnerAdapter)this.mAdapter, getBackground());
        int i1 = (AppCompatSpinner.this.getContext().getResources().getDisplayMetrics()).widthPixels - AppCompatSpinner.this.mTempRect.left - AppCompatSpinner.this.mTempRect.right;
        int i2 = n;
        if (n > i1)
          i2 = i1; 
        setContentWidth(Math.max(i2, m - j - k));
      } else if (AppCompatSpinner.this.mDropDownWidth == -1) {
        setContentWidth(m - j - k);
      } else {
        setContentWidth(AppCompatSpinner.this.mDropDownWidth);
      } 
      if (ViewUtils.isLayoutRtl((View)AppCompatSpinner.this)) {
        i += m - k - getWidth();
      } else {
        i += j;
      } 
      setHorizontalOffset(i);
    }
    
    public CharSequence getHintText() {
      return this.mHintText;
    }
    
    boolean isVisibleToUser(View param1View) {
      boolean bool;
      if (ViewCompat.isAttachedToWindow(param1View) && param1View.getGlobalVisibleRect(this.mVisibleRect)) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public void setAdapter(ListAdapter param1ListAdapter) {
      super.setAdapter(param1ListAdapter);
      this.mAdapter = param1ListAdapter;
    }
    
    public void setPromptText(CharSequence param1CharSequence) {
      this.mHintText = param1CharSequence;
    }
    
    public void show() {
      boolean bool = isShowing();
      computeContentWidth();
      setInputMethodMode(2);
      super.show();
      getListView().setChoiceMode(1);
      setSelection(AppCompatSpinner.this.getSelectedItemPosition());
      if (bool)
        return; 
      ViewTreeObserver viewTreeObserver = AppCompatSpinner.this.getViewTreeObserver();
      if (viewTreeObserver != null) {
        final ViewTreeObserver.OnGlobalLayoutListener layoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
              AppCompatSpinner.DropdownPopup dropdownPopup = AppCompatSpinner.DropdownPopup.this;
              if (!dropdownPopup.isVisibleToUser((View)AppCompatSpinner.this)) {
                AppCompatSpinner.DropdownPopup.this.dismiss();
              } else {
                AppCompatSpinner.DropdownPopup.this.computeContentWidth();
                AppCompatSpinner.DropdownPopup.this.show();
              } 
            }
          };
        viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener);
        setOnDismissListener(new PopupWindow.OnDismissListener() {
              public void onDismiss() {
                ViewTreeObserver viewTreeObserver = AppCompatSpinner.this.getViewTreeObserver();
                if (viewTreeObserver != null)
                  viewTreeObserver.removeGlobalOnLayoutListener(layoutListener); 
              }
            });
      } 
    }
  }
  
  class null implements AdapterView.OnItemClickListener {
    public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
      AppCompatSpinner.this.setSelection(param1Int);
      if (AppCompatSpinner.this.getOnItemClickListener() != null)
        AppCompatSpinner.this.performItemClick(param1View, param1Int, this.this$1.mAdapter.getItemId(param1Int)); 
      this.this$1.dismiss();
    }
  }
  
  class null implements ViewTreeObserver.OnGlobalLayoutListener {
    public void onGlobalLayout() {
      AppCompatSpinner.DropdownPopup dropdownPopup = this.this$1;
      if (!dropdownPopup.isVisibleToUser((View)AppCompatSpinner.this)) {
        this.this$1.dismiss();
      } else {
        this.this$1.computeContentWidth();
        this.this$1.show();
      } 
    }
  }
  
  class null implements PopupWindow.OnDismissListener {
    public void onDismiss() {
      ViewTreeObserver viewTreeObserver = AppCompatSpinner.this.getViewTreeObserver();
      if (viewTreeObserver != null)
        viewTreeObserver.removeGlobalOnLayoutListener(layoutListener); 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\AppCompatSpinner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */