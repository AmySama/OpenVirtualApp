package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.LruCache;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class AppCompatDrawableManager {
  private static final int[] COLORFILTER_COLOR_BACKGROUND_MULTIPLY;
  
  private static final int[] COLORFILTER_COLOR_CONTROL_ACTIVATED;
  
  private static final int[] COLORFILTER_TINT_COLOR_CONTROL_NORMAL;
  
  private static final ColorFilterLruCache COLOR_FILTER_CACHE;
  
  private static final boolean DEBUG = false;
  
  private static final PorterDuff.Mode DEFAULT_MODE = PorterDuff.Mode.SRC_IN;
  
  private static AppCompatDrawableManager INSTANCE;
  
  private static final String PLATFORM_VD_CLAZZ = "android.graphics.drawable.VectorDrawable";
  
  private static final String SKIP_DRAWABLE_TAG = "appcompat_skip_skip";
  
  private static final String TAG = "AppCompatDrawableManag";
  
  private static final int[] TINT_CHECKABLE_BUTTON_LIST;
  
  private static final int[] TINT_COLOR_CONTROL_NORMAL;
  
  private static final int[] TINT_COLOR_CONTROL_STATE_LIST;
  
  private ArrayMap<String, InflateDelegate> mDelegates;
  
  private final Object mDrawableCacheLock = new Object();
  
  private final WeakHashMap<Context, LongSparseArray<WeakReference<Drawable.ConstantState>>> mDrawableCaches = new WeakHashMap<Context, LongSparseArray<WeakReference<Drawable.ConstantState>>>(0);
  
  private boolean mHasCheckedVectorDrawableSetup;
  
  private SparseArrayCompat<String> mKnownDrawableIdTags;
  
  private WeakHashMap<Context, SparseArrayCompat<ColorStateList>> mTintLists;
  
  private TypedValue mTypedValue;
  
  static {
    COLOR_FILTER_CACHE = new ColorFilterLruCache(6);
    COLORFILTER_TINT_COLOR_CONTROL_NORMAL = new int[] { R.drawable.abc_textfield_search_default_mtrl_alpha, R.drawable.abc_textfield_default_mtrl_alpha, R.drawable.abc_ab_share_pack_mtrl_alpha };
    TINT_COLOR_CONTROL_NORMAL = new int[] { R.drawable.abc_ic_commit_search_api_mtrl_alpha, R.drawable.abc_seekbar_tick_mark_material, R.drawable.abc_ic_menu_share_mtrl_alpha, R.drawable.abc_ic_menu_copy_mtrl_am_alpha, R.drawable.abc_ic_menu_cut_mtrl_alpha, R.drawable.abc_ic_menu_selectall_mtrl_alpha, R.drawable.abc_ic_menu_paste_mtrl_am_alpha };
    COLORFILTER_COLOR_CONTROL_ACTIVATED = new int[] { R.drawable.abc_textfield_activated_mtrl_alpha, R.drawable.abc_textfield_search_activated_mtrl_alpha, R.drawable.abc_cab_background_top_mtrl_alpha, R.drawable.abc_text_cursor_material, R.drawable.abc_text_select_handle_left_mtrl_dark, R.drawable.abc_text_select_handle_middle_mtrl_dark, R.drawable.abc_text_select_handle_right_mtrl_dark, R.drawable.abc_text_select_handle_left_mtrl_light, R.drawable.abc_text_select_handle_middle_mtrl_light, R.drawable.abc_text_select_handle_right_mtrl_light };
    COLORFILTER_COLOR_BACKGROUND_MULTIPLY = new int[] { R.drawable.abc_popup_background_mtrl_mult, R.drawable.abc_cab_background_internal_bg, R.drawable.abc_menu_hardkey_panel_mtrl_mult };
    TINT_COLOR_CONTROL_STATE_LIST = new int[] { R.drawable.abc_tab_indicator_material, R.drawable.abc_textfield_search_material };
    TINT_CHECKABLE_BUTTON_LIST = new int[] { R.drawable.abc_btn_check_material, R.drawable.abc_btn_radio_material };
  }
  
  private void addDelegate(String paramString, InflateDelegate paramInflateDelegate) {
    if (this.mDelegates == null)
      this.mDelegates = new ArrayMap(); 
    this.mDelegates.put(paramString, paramInflateDelegate);
  }
  
  private boolean addDrawableToCache(Context paramContext, long paramLong, Drawable paramDrawable) {
    Drawable.ConstantState constantState = paramDrawable.getConstantState();
    if (constantState != null)
      synchronized (this.mDrawableCacheLock) {
        LongSparseArray<WeakReference<Drawable.ConstantState>> longSparseArray2 = this.mDrawableCaches.get(paramContext);
        LongSparseArray<WeakReference<Drawable.ConstantState>> longSparseArray1 = longSparseArray2;
        if (longSparseArray2 == null) {
          longSparseArray1 = new LongSparseArray();
          this();
          this.mDrawableCaches.put(paramContext, longSparseArray1);
        } 
        WeakReference weakReference = new WeakReference();
        this((T)constantState);
        longSparseArray1.put(paramLong, weakReference);
        return true;
      }  
    return false;
  }
  
  private void addTintListToCache(Context paramContext, int paramInt, ColorStateList paramColorStateList) {
    if (this.mTintLists == null)
      this.mTintLists = new WeakHashMap<Context, SparseArrayCompat<ColorStateList>>(); 
    SparseArrayCompat<ColorStateList> sparseArrayCompat1 = this.mTintLists.get(paramContext);
    SparseArrayCompat<ColorStateList> sparseArrayCompat2 = sparseArrayCompat1;
    if (sparseArrayCompat1 == null) {
      sparseArrayCompat2 = new SparseArrayCompat();
      this.mTintLists.put(paramContext, sparseArrayCompat2);
    } 
    sparseArrayCompat2.append(paramInt, paramColorStateList);
  }
  
  private static boolean arrayContains(int[] paramArrayOfint, int paramInt) {
    int i = paramArrayOfint.length;
    for (byte b = 0; b < i; b++) {
      if (paramArrayOfint[b] == paramInt)
        return true; 
    } 
    return false;
  }
  
  private void checkVectorDrawableSetup(Context paramContext) {
    if (this.mHasCheckedVectorDrawableSetup)
      return; 
    this.mHasCheckedVectorDrawableSetup = true;
    Drawable drawable = getDrawable(paramContext, R.drawable.abc_vector_test);
    if (drawable != null && isVectorDrawable(drawable))
      return; 
    this.mHasCheckedVectorDrawableSetup = false;
    throw new IllegalStateException("This app has been built with an incorrect configuration. Please configure your build for VectorDrawableCompat.");
  }
  
  private ColorStateList createBorderlessButtonColorStateList(Context paramContext) {
    return createButtonColorStateList(paramContext, 0);
  }
  
  private ColorStateList createButtonColorStateList(Context paramContext, int paramInt) {
    int i = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlHighlight);
    int j = ThemeUtils.getDisabledThemeAttrColor(paramContext, R.attr.colorButtonNormal);
    int[] arrayOfInt2 = ThemeUtils.DISABLED_STATE_SET;
    int[] arrayOfInt1 = ThemeUtils.PRESSED_STATE_SET;
    int k = ColorUtils.compositeColors(i, paramInt);
    int[] arrayOfInt3 = ThemeUtils.FOCUSED_STATE_SET;
    i = ColorUtils.compositeColors(i, paramInt);
    return new ColorStateList(new int[][] { arrayOfInt2, arrayOfInt1, arrayOfInt3, ThemeUtils.EMPTY_STATE_SET }, new int[] { j, k, i, paramInt });
  }
  
  private static long createCacheKey(TypedValue paramTypedValue) {
    return paramTypedValue.assetCookie << 32L | paramTypedValue.data;
  }
  
  private ColorStateList createColoredButtonColorStateList(Context paramContext) {
    return createButtonColorStateList(paramContext, ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorAccent));
  }
  
  private ColorStateList createDefaultButtonColorStateList(Context paramContext) {
    return createButtonColorStateList(paramContext, ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorButtonNormal));
  }
  
  private Drawable createDrawableIfNeeded(Context paramContext, int paramInt) {
    LayerDrawable layerDrawable;
    if (this.mTypedValue == null)
      this.mTypedValue = new TypedValue(); 
    TypedValue typedValue = this.mTypedValue;
    paramContext.getResources().getValue(paramInt, typedValue, true);
    long l = createCacheKey(typedValue);
    Drawable drawable = getCachedDrawable(paramContext, l);
    if (drawable != null)
      return drawable; 
    if (paramInt == R.drawable.abc_cab_background_top_material)
      layerDrawable = new LayerDrawable(new Drawable[] { getDrawable(paramContext, R.drawable.abc_cab_background_internal_bg), getDrawable(paramContext, R.drawable.abc_cab_background_top_mtrl_alpha) }); 
    if (layerDrawable != null) {
      layerDrawable.setChangingConfigurations(typedValue.changingConfigurations);
      addDrawableToCache(paramContext, l, (Drawable)layerDrawable);
    } 
    return (Drawable)layerDrawable;
  }
  
  private ColorStateList createSwitchThumbColorStateList(Context paramContext) {
    int[][] arrayOfInt = new int[3][];
    int[] arrayOfInt1 = new int[3];
    ColorStateList colorStateList = ThemeUtils.getThemeAttrColorStateList(paramContext, R.attr.colorSwitchThumbNormal);
    if (colorStateList != null && colorStateList.isStateful()) {
      arrayOfInt[0] = ThemeUtils.DISABLED_STATE_SET;
      arrayOfInt1[0] = colorStateList.getColorForState(arrayOfInt[0], 0);
      arrayOfInt[1] = ThemeUtils.CHECKED_STATE_SET;
      arrayOfInt1[1] = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlActivated);
      arrayOfInt[2] = ThemeUtils.EMPTY_STATE_SET;
      arrayOfInt1[2] = colorStateList.getDefaultColor();
    } else {
      arrayOfInt[0] = ThemeUtils.DISABLED_STATE_SET;
      arrayOfInt1[0] = ThemeUtils.getDisabledThemeAttrColor(paramContext, R.attr.colorSwitchThumbNormal);
      arrayOfInt[1] = ThemeUtils.CHECKED_STATE_SET;
      arrayOfInt1[1] = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorControlActivated);
      arrayOfInt[2] = ThemeUtils.EMPTY_STATE_SET;
      arrayOfInt1[2] = ThemeUtils.getThemeAttrColor(paramContext, R.attr.colorSwitchThumbNormal);
    } 
    return new ColorStateList(arrayOfInt, arrayOfInt1);
  }
  
  private static PorterDuffColorFilter createTintFilter(ColorStateList paramColorStateList, PorterDuff.Mode paramMode, int[] paramArrayOfint) {
    return (paramColorStateList == null || paramMode == null) ? null : getPorterDuffColorFilter(paramColorStateList.getColorForState(paramArrayOfint, 0), paramMode);
  }
  
  public static AppCompatDrawableManager get() {
    if (INSTANCE == null) {
      AppCompatDrawableManager appCompatDrawableManager = new AppCompatDrawableManager();
      INSTANCE = appCompatDrawableManager;
      installDefaultInflateDelegates(appCompatDrawableManager);
    } 
    return INSTANCE;
  }
  
  private Drawable getCachedDrawable(Context paramContext, long paramLong) {
    synchronized (this.mDrawableCacheLock) {
      LongSparseArray longSparseArray = this.mDrawableCaches.get(paramContext);
      if (longSparseArray == null)
        return null; 
      WeakReference<Drawable.ConstantState> weakReference = (WeakReference)longSparseArray.get(paramLong);
      if (weakReference != null) {
        Drawable.ConstantState constantState = weakReference.get();
        if (constantState != null)
          return constantState.newDrawable(paramContext.getResources()); 
        longSparseArray.delete(paramLong);
      } 
      return null;
    } 
  }
  
  public static PorterDuffColorFilter getPorterDuffColorFilter(int paramInt, PorterDuff.Mode paramMode) {
    PorterDuffColorFilter porterDuffColorFilter1 = COLOR_FILTER_CACHE.get(paramInt, paramMode);
    PorterDuffColorFilter porterDuffColorFilter2 = porterDuffColorFilter1;
    if (porterDuffColorFilter1 == null) {
      porterDuffColorFilter2 = new PorterDuffColorFilter(paramInt, paramMode);
      COLOR_FILTER_CACHE.put(paramInt, paramMode, porterDuffColorFilter2);
    } 
    return porterDuffColorFilter2;
  }
  
  private ColorStateList getTintListFromCache(Context paramContext, int paramInt) {
    WeakHashMap<Context, SparseArrayCompat<ColorStateList>> weakHashMap = this.mTintLists;
    ColorStateList colorStateList1 = null;
    ColorStateList colorStateList2 = colorStateList1;
    if (weakHashMap != null) {
      SparseArrayCompat sparseArrayCompat = weakHashMap.get(paramContext);
      colorStateList2 = colorStateList1;
      if (sparseArrayCompat != null)
        colorStateList2 = (ColorStateList)sparseArrayCompat.get(paramInt); 
    } 
    return colorStateList2;
  }
  
  static PorterDuff.Mode getTintMode(int paramInt) {
    PorterDuff.Mode mode;
    if (paramInt == R.drawable.abc_switch_thumb_material) {
      mode = PorterDuff.Mode.MULTIPLY;
    } else {
      mode = null;
    } 
    return mode;
  }
  
  private static void installDefaultInflateDelegates(AppCompatDrawableManager paramAppCompatDrawableManager) {
    if (Build.VERSION.SDK_INT < 24) {
      paramAppCompatDrawableManager.addDelegate("vector", new VdcInflateDelegate());
      paramAppCompatDrawableManager.addDelegate("animated-vector", new AvdcInflateDelegate());
    } 
  }
  
  private static boolean isVectorDrawable(Drawable paramDrawable) {
    return (paramDrawable instanceof VectorDrawableCompat || "android.graphics.drawable.VectorDrawable".equals(paramDrawable.getClass().getName()));
  }
  
  private Drawable loadDrawableFromDelegates(Context paramContext, int paramInt) {
    ArrayMap<String, InflateDelegate> arrayMap = this.mDelegates;
    if (arrayMap != null && !arrayMap.isEmpty()) {
      SparseArrayCompat<String> sparseArrayCompat = this.mKnownDrawableIdTags;
      if (sparseArrayCompat != null) {
        String str = (String)sparseArrayCompat.get(paramInt);
        if ("appcompat_skip_skip".equals(str) || (str != null && this.mDelegates.get(str) == null))
          return null; 
      } else {
        this.mKnownDrawableIdTags = new SparseArrayCompat();
      } 
      if (this.mTypedValue == null)
        this.mTypedValue = new TypedValue(); 
      TypedValue typedValue = this.mTypedValue;
      Resources resources = paramContext.getResources();
      resources.getValue(paramInt, typedValue, true);
      long l = createCacheKey(typedValue);
      Drawable drawable1 = getCachedDrawable(paramContext, l);
      if (drawable1 != null)
        return drawable1; 
      Drawable drawable2 = drawable1;
      if (typedValue.string != null) {
        drawable2 = drawable1;
        if (typedValue.string.toString().endsWith(".xml")) {
          drawable2 = drawable1;
          try {
            int i;
            XmlResourceParser xmlResourceParser = resources.getXml(paramInt);
            drawable2 = drawable1;
            AttributeSet attributeSet = Xml.asAttributeSet((XmlPullParser)xmlResourceParser);
            while (true) {
              drawable2 = drawable1;
              i = xmlResourceParser.next();
              if (i != 2 && i != 1)
                continue; 
              break;
            } 
            if (i == 2) {
              drawable2 = drawable1;
              String str = xmlResourceParser.getName();
              drawable2 = drawable1;
              this.mKnownDrawableIdTags.append(paramInt, str);
              drawable2 = drawable1;
              InflateDelegate inflateDelegate = (InflateDelegate)this.mDelegates.get(str);
              Drawable drawable = drawable1;
              if (inflateDelegate != null) {
                drawable2 = drawable1;
                drawable = inflateDelegate.createFromXmlInner(paramContext, (XmlPullParser)xmlResourceParser, attributeSet, paramContext.getTheme());
              } 
              drawable2 = drawable;
              if (drawable != null) {
                drawable2 = drawable;
                drawable.setChangingConfigurations(typedValue.changingConfigurations);
                drawable2 = drawable;
                addDrawableToCache(paramContext, l, drawable);
                drawable2 = drawable;
              } 
            } else {
              drawable2 = drawable1;
              XmlPullParserException xmlPullParserException = new XmlPullParserException();
              drawable2 = drawable1;
              this("No start tag found");
              drawable2 = drawable1;
              throw xmlPullParserException;
            } 
          } catch (Exception exception) {
            Log.e("AppCompatDrawableManag", "Exception while inflating drawable", exception);
          } 
        } 
      } 
      if (drawable2 == null)
        this.mKnownDrawableIdTags.append(paramInt, "appcompat_skip_skip"); 
      return drawable2;
    } 
    return null;
  }
  
  private void removeDelegate(String paramString, InflateDelegate paramInflateDelegate) {
    ArrayMap<String, InflateDelegate> arrayMap = this.mDelegates;
    if (arrayMap != null && arrayMap.get(paramString) == paramInflateDelegate)
      this.mDelegates.remove(paramString); 
  }
  
  private static void setPorterDuffColorFilter(Drawable paramDrawable, int paramInt, PorterDuff.Mode paramMode) {
    Drawable drawable = paramDrawable;
    if (DrawableUtils.canSafelyMutateDrawable(paramDrawable))
      drawable = paramDrawable.mutate(); 
    PorterDuff.Mode mode = paramMode;
    if (paramMode == null)
      mode = DEFAULT_MODE; 
    drawable.setColorFilter((ColorFilter)getPorterDuffColorFilter(paramInt, mode));
  }
  
  private Drawable tintDrawable(Context paramContext, int paramInt, boolean paramBoolean, Drawable paramDrawable) {
    Drawable drawable;
    PorterDuff.Mode mode1;
    PorterDuff.Mode mode2;
    ColorStateList colorStateList = getTintList(paramContext, paramInt);
    if (colorStateList != null) {
      drawable = paramDrawable;
      if (DrawableUtils.canSafelyMutateDrawable(paramDrawable))
        drawable = paramDrawable.mutate(); 
      drawable = DrawableCompat.wrap(drawable);
      DrawableCompat.setTintList(drawable, colorStateList);
      mode1 = getTintMode(paramInt);
      Drawable drawable1 = drawable;
      if (mode1 != null) {
        DrawableCompat.setTintMode(drawable, mode1);
        drawable1 = drawable;
      } 
    } else if (paramInt == R.drawable.abc_seekbar_track_material) {
      LayerDrawable layerDrawable = (LayerDrawable)mode1;
      setPorterDuffColorFilter(layerDrawable.findDrawableByLayerId(16908288), ThemeUtils.getThemeAttrColor((Context)drawable, R.attr.colorControlNormal), DEFAULT_MODE);
      setPorterDuffColorFilter(layerDrawable.findDrawableByLayerId(16908303), ThemeUtils.getThemeAttrColor((Context)drawable, R.attr.colorControlNormal), DEFAULT_MODE);
      setPorterDuffColorFilter(layerDrawable.findDrawableByLayerId(16908301), ThemeUtils.getThemeAttrColor((Context)drawable, R.attr.colorControlActivated), DEFAULT_MODE);
      mode2 = mode1;
    } else {
      if (paramInt == R.drawable.abc_ratingbar_material || paramInt == R.drawable.abc_ratingbar_indicator_material || paramInt == R.drawable.abc_ratingbar_small_material) {
        LayerDrawable layerDrawable = (LayerDrawable)mode1;
        setPorterDuffColorFilter(layerDrawable.findDrawableByLayerId(16908288), ThemeUtils.getDisabledThemeAttrColor((Context)drawable, R.attr.colorControlNormal), DEFAULT_MODE);
        setPorterDuffColorFilter(layerDrawable.findDrawableByLayerId(16908303), ThemeUtils.getThemeAttrColor((Context)drawable, R.attr.colorControlActivated), DEFAULT_MODE);
        setPorterDuffColorFilter(layerDrawable.findDrawableByLayerId(16908301), ThemeUtils.getThemeAttrColor((Context)drawable, R.attr.colorControlActivated), DEFAULT_MODE);
        return (Drawable)mode1;
      } 
      mode2 = mode1;
      if (!tintDrawableUsingColorFilter((Context)drawable, paramInt, (Drawable)mode1)) {
        mode2 = mode1;
        if (paramBoolean)
          mode2 = null; 
      } 
    } 
    return (Drawable)mode2;
  }
  
  static void tintDrawable(Drawable paramDrawable, TintInfo paramTintInfo, int[] paramArrayOfint) {
    if (DrawableUtils.canSafelyMutateDrawable(paramDrawable) && paramDrawable.mutate() != paramDrawable) {
      Log.d("AppCompatDrawableManag", "Mutated drawable is not the same instance as the input.");
      return;
    } 
    if (paramTintInfo.mHasTintList || paramTintInfo.mHasTintMode) {
      PorterDuff.Mode mode;
      ColorStateList colorStateList;
      if (paramTintInfo.mHasTintList) {
        colorStateList = paramTintInfo.mTintList;
      } else {
        colorStateList = null;
      } 
      if (paramTintInfo.mHasTintMode) {
        mode = paramTintInfo.mTintMode;
      } else {
        mode = DEFAULT_MODE;
      } 
      paramDrawable.setColorFilter((ColorFilter)createTintFilter(colorStateList, mode, paramArrayOfint));
    } else {
      paramDrawable.clearColorFilter();
    } 
    if (Build.VERSION.SDK_INT <= 23)
      paramDrawable.invalidateSelf(); 
  }
  
  static boolean tintDrawableUsingColorFilter(Context paramContext, int paramInt, Drawable paramDrawable) {
    // Byte code:
    //   0: getstatic android/support/v7/widget/AppCompatDrawableManager.DEFAULT_MODE : Landroid/graphics/PorterDuff$Mode;
    //   3: astore_3
    //   4: getstatic android/support/v7/widget/AppCompatDrawableManager.COLORFILTER_TINT_COLOR_CONTROL_NORMAL : [I
    //   7: iload_1
    //   8: invokestatic arrayContains : ([II)Z
    //   11: istore #4
    //   13: ldc_w 16842801
    //   16: istore #5
    //   18: iload #4
    //   20: ifeq -> 36
    //   23: getstatic android/support/v7/appcompat/R$attr.colorControlNormal : I
    //   26: istore_1
    //   27: iconst_m1
    //   28: istore #5
    //   30: iconst_1
    //   31: istore #6
    //   33: goto -> 116
    //   36: getstatic android/support/v7/widget/AppCompatDrawableManager.COLORFILTER_COLOR_CONTROL_ACTIVATED : [I
    //   39: iload_1
    //   40: invokestatic arrayContains : ([II)Z
    //   43: ifeq -> 53
    //   46: getstatic android/support/v7/appcompat/R$attr.colorControlActivated : I
    //   49: istore_1
    //   50: goto -> 27
    //   53: getstatic android/support/v7/widget/AppCompatDrawableManager.COLORFILTER_COLOR_BACKGROUND_MULTIPLY : [I
    //   56: iload_1
    //   57: invokestatic arrayContains : ([II)Z
    //   60: ifeq -> 73
    //   63: getstatic android/graphics/PorterDuff$Mode.MULTIPLY : Landroid/graphics/PorterDuff$Mode;
    //   66: astore_3
    //   67: iload #5
    //   69: istore_1
    //   70: goto -> 27
    //   73: iload_1
    //   74: getstatic android/support/v7/appcompat/R$drawable.abc_list_divider_mtrl_alpha : I
    //   77: if_icmpne -> 95
    //   80: ldc_w 16842800
    //   83: istore_1
    //   84: ldc_w 40.8
    //   87: invokestatic round : (F)I
    //   90: istore #5
    //   92: goto -> 30
    //   95: iload_1
    //   96: getstatic android/support/v7/appcompat/R$drawable.abc_dialog_material_background : I
    //   99: if_icmpne -> 108
    //   102: iload #5
    //   104: istore_1
    //   105: goto -> 27
    //   108: iconst_m1
    //   109: istore #5
    //   111: iconst_0
    //   112: istore #6
    //   114: iconst_0
    //   115: istore_1
    //   116: iload #6
    //   118: ifeq -> 166
    //   121: aload_2
    //   122: astore #7
    //   124: aload_2
    //   125: invokestatic canSafelyMutateDrawable : (Landroid/graphics/drawable/Drawable;)Z
    //   128: ifeq -> 137
    //   131: aload_2
    //   132: invokevirtual mutate : ()Landroid/graphics/drawable/Drawable;
    //   135: astore #7
    //   137: aload #7
    //   139: aload_0
    //   140: iload_1
    //   141: invokestatic getThemeAttrColor : (Landroid/content/Context;I)I
    //   144: aload_3
    //   145: invokestatic getPorterDuffColorFilter : (ILandroid/graphics/PorterDuff$Mode;)Landroid/graphics/PorterDuffColorFilter;
    //   148: invokevirtual setColorFilter : (Landroid/graphics/ColorFilter;)V
    //   151: iload #5
    //   153: iconst_m1
    //   154: if_icmpeq -> 164
    //   157: aload #7
    //   159: iload #5
    //   161: invokevirtual setAlpha : (I)V
    //   164: iconst_1
    //   165: ireturn
    //   166: iconst_0
    //   167: ireturn
  }
  
  public Drawable getDrawable(Context paramContext, int paramInt) {
    return getDrawable(paramContext, paramInt, false);
  }
  
  Drawable getDrawable(Context paramContext, int paramInt, boolean paramBoolean) {
    checkVectorDrawableSetup(paramContext);
    Drawable drawable1 = loadDrawableFromDelegates(paramContext, paramInt);
    Drawable drawable2 = drawable1;
    if (drawable1 == null)
      drawable2 = createDrawableIfNeeded(paramContext, paramInt); 
    drawable1 = drawable2;
    if (drawable2 == null)
      drawable1 = ContextCompat.getDrawable(paramContext, paramInt); 
    drawable2 = drawable1;
    if (drawable1 != null)
      drawable2 = tintDrawable(paramContext, paramInt, paramBoolean, drawable1); 
    if (drawable2 != null)
      DrawableUtils.fixDrawable(drawable2); 
    return drawable2;
  }
  
  ColorStateList getTintList(Context paramContext, int paramInt) {
    ColorStateList colorStateList1 = getTintListFromCache(paramContext, paramInt);
    ColorStateList colorStateList2 = colorStateList1;
    if (colorStateList1 == null) {
      if (paramInt == R.drawable.abc_edit_text_material) {
        colorStateList1 = AppCompatResources.getColorStateList(paramContext, R.color.abc_tint_edittext);
      } else if (paramInt == R.drawable.abc_switch_track_mtrl_alpha) {
        colorStateList1 = AppCompatResources.getColorStateList(paramContext, R.color.abc_tint_switch_track);
      } else if (paramInt == R.drawable.abc_switch_thumb_material) {
        colorStateList1 = createSwitchThumbColorStateList(paramContext);
      } else if (paramInt == R.drawable.abc_btn_default_mtrl_shape) {
        colorStateList1 = createDefaultButtonColorStateList(paramContext);
      } else if (paramInt == R.drawable.abc_btn_borderless_material) {
        colorStateList1 = createBorderlessButtonColorStateList(paramContext);
      } else if (paramInt == R.drawable.abc_btn_colored_material) {
        colorStateList1 = createColoredButtonColorStateList(paramContext);
      } else if (paramInt == R.drawable.abc_spinner_mtrl_am_alpha || paramInt == R.drawable.abc_spinner_textfield_background_material) {
        colorStateList1 = AppCompatResources.getColorStateList(paramContext, R.color.abc_tint_spinner);
      } else if (arrayContains(TINT_COLOR_CONTROL_NORMAL, paramInt)) {
        colorStateList1 = ThemeUtils.getThemeAttrColorStateList(paramContext, R.attr.colorControlNormal);
      } else if (arrayContains(TINT_COLOR_CONTROL_STATE_LIST, paramInt)) {
        colorStateList1 = AppCompatResources.getColorStateList(paramContext, R.color.abc_tint_default);
      } else if (arrayContains(TINT_CHECKABLE_BUTTON_LIST, paramInt)) {
        colorStateList1 = AppCompatResources.getColorStateList(paramContext, R.color.abc_tint_btn_checkable);
      } else if (paramInt == R.drawable.abc_seekbar_thumb_material) {
        colorStateList1 = AppCompatResources.getColorStateList(paramContext, R.color.abc_tint_seek_thumb);
      } 
      colorStateList2 = colorStateList1;
      if (colorStateList1 != null) {
        addTintListToCache(paramContext, paramInt, colorStateList1);
        colorStateList2 = colorStateList1;
      } 
    } 
    return colorStateList2;
  }
  
  public void onConfigurationChanged(Context paramContext) {
    synchronized (this.mDrawableCacheLock) {
      LongSparseArray longSparseArray = this.mDrawableCaches.get(paramContext);
      if (longSparseArray != null)
        longSparseArray.clear(); 
      return;
    } 
  }
  
  Drawable onDrawableLoadedFromResources(Context paramContext, VectorEnabledTintResources paramVectorEnabledTintResources, int paramInt) {
    Drawable drawable1 = loadDrawableFromDelegates(paramContext, paramInt);
    Drawable drawable2 = drawable1;
    if (drawable1 == null)
      drawable2 = paramVectorEnabledTintResources.superGetDrawable(paramInt); 
    return (drawable2 != null) ? tintDrawable(paramContext, paramInt, false, drawable2) : null;
  }
  
  private static class AvdcInflateDelegate implements InflateDelegate {
    public Drawable createFromXmlInner(Context param1Context, XmlPullParser param1XmlPullParser, AttributeSet param1AttributeSet, Resources.Theme param1Theme) {
      try {
        return (Drawable)AnimatedVectorDrawableCompat.createFromXmlInner(param1Context, param1Context.getResources(), param1XmlPullParser, param1AttributeSet, param1Theme);
      } catch (Exception exception) {
        Log.e("AvdcInflateDelegate", "Exception while inflating <animated-vector>", exception);
        return null;
      } 
    }
  }
  
  private static class ColorFilterLruCache extends LruCache<Integer, PorterDuffColorFilter> {
    public ColorFilterLruCache(int param1Int) {
      super(param1Int);
    }
    
    private static int generateCacheKey(int param1Int, PorterDuff.Mode param1Mode) {
      return (param1Int + 31) * 31 + param1Mode.hashCode();
    }
    
    PorterDuffColorFilter get(int param1Int, PorterDuff.Mode param1Mode) {
      return (PorterDuffColorFilter)get(Integer.valueOf(generateCacheKey(param1Int, param1Mode)));
    }
    
    PorterDuffColorFilter put(int param1Int, PorterDuff.Mode param1Mode, PorterDuffColorFilter param1PorterDuffColorFilter) {
      return (PorterDuffColorFilter)put(Integer.valueOf(generateCacheKey(param1Int, param1Mode)), param1PorterDuffColorFilter);
    }
  }
  
  private static interface InflateDelegate {
    Drawable createFromXmlInner(Context param1Context, XmlPullParser param1XmlPullParser, AttributeSet param1AttributeSet, Resources.Theme param1Theme);
  }
  
  private static class VdcInflateDelegate implements InflateDelegate {
    public Drawable createFromXmlInner(Context param1Context, XmlPullParser param1XmlPullParser, AttributeSet param1AttributeSet, Resources.Theme param1Theme) {
      try {
        return (Drawable)VectorDrawableCompat.createFromXmlInner(param1Context.getResources(), param1XmlPullParser, param1AttributeSet, param1Theme);
      } catch (Exception exception) {
        Log.e("VdcInflateDelegate", "Exception while inflating <vector>", exception);
        return null;
      } 
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\AppCompatDrawableManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */