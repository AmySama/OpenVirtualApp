package android.support.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.graphics.PathParser;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class VectorDrawableCompat extends VectorDrawableCommon {
  private static final boolean DBG_VECTOR_DRAWABLE = false;
  
  static final PorterDuff.Mode DEFAULT_TINT_MODE = PorterDuff.Mode.SRC_IN;
  
  private static final int LINECAP_BUTT = 0;
  
  private static final int LINECAP_ROUND = 1;
  
  private static final int LINECAP_SQUARE = 2;
  
  private static final int LINEJOIN_BEVEL = 2;
  
  private static final int LINEJOIN_MITER = 0;
  
  private static final int LINEJOIN_ROUND = 1;
  
  static final String LOGTAG = "VectorDrawableCompat";
  
  private static final int MAX_CACHED_BITMAP_SIZE = 2048;
  
  private static final String SHAPE_CLIP_PATH = "clip-path";
  
  private static final String SHAPE_GROUP = "group";
  
  private static final String SHAPE_PATH = "path";
  
  private static final String SHAPE_VECTOR = "vector";
  
  private boolean mAllowCaching = true;
  
  private Drawable.ConstantState mCachedConstantStateDelegate;
  
  private ColorFilter mColorFilter;
  
  private boolean mMutated;
  
  private PorterDuffColorFilter mTintFilter;
  
  private final Rect mTmpBounds = new Rect();
  
  private final float[] mTmpFloats = new float[9];
  
  private final Matrix mTmpMatrix = new Matrix();
  
  private VectorDrawableCompatState mVectorState = new VectorDrawableCompatState();
  
  VectorDrawableCompat() {}
  
  VectorDrawableCompat(VectorDrawableCompatState paramVectorDrawableCompatState) {
    this.mTintFilter = updateTintFilter(this.mTintFilter, paramVectorDrawableCompatState.mTint, paramVectorDrawableCompatState.mTintMode);
  }
  
  static int applyAlpha(int paramInt, float paramFloat) {
    return paramInt & 0xFFFFFF | (int)(Color.alpha(paramInt) * paramFloat) << 24;
  }
  
  public static VectorDrawableCompat create(Resources paramResources, int paramInt, Resources.Theme paramTheme) {
    if (Build.VERSION.SDK_INT >= 24) {
      VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
      vectorDrawableCompat.mDelegateDrawable = ResourcesCompat.getDrawable(paramResources, paramInt, paramTheme);
      vectorDrawableCompat.mCachedConstantStateDelegate = new VectorDrawableDelegateState(vectorDrawableCompat.mDelegateDrawable.getConstantState());
      return vectorDrawableCompat;
    } 
    try {
      XmlResourceParser xmlResourceParser = paramResources.getXml(paramInt);
      AttributeSet attributeSet = Xml.asAttributeSet((XmlPullParser)xmlResourceParser);
      while (true) {
        paramInt = xmlResourceParser.next();
        if (paramInt != 2 && paramInt != 1)
          continue; 
        break;
      } 
      if (paramInt == 2)
        return createFromXmlInner(paramResources, (XmlPullParser)xmlResourceParser, attributeSet, paramTheme); 
      XmlPullParserException xmlPullParserException = new XmlPullParserException();
      this("No start tag found");
      throw xmlPullParserException;
    } catch (XmlPullParserException xmlPullParserException) {
      Log.e("VectorDrawableCompat", "parser error", (Throwable)xmlPullParserException);
    } catch (IOException iOException) {
      Log.e("VectorDrawableCompat", "parser error", iOException);
    } 
    return null;
  }
  
  public static VectorDrawableCompat createFromXmlInner(Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme) throws XmlPullParserException, IOException {
    VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
    vectorDrawableCompat.inflate(paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
    return vectorDrawableCompat;
  }
  
  private void inflateInternal(Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme) throws XmlPullParserException, IOException {
    VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
    VPathRenderer vPathRenderer = vectorDrawableCompatState.mVPathRenderer;
    ArrayDeque<VGroup> arrayDeque = new ArrayDeque();
    arrayDeque.push(vPathRenderer.mRootGroup);
    int i = paramXmlPullParser.getEventType();
    int j = paramXmlPullParser.getDepth();
    int k;
    for (k = 1; i != 1 && (paramXmlPullParser.getDepth() >= j + 1 || i != 3); k = m) {
      int m;
      if (i == 2) {
        VFullPath vFullPath;
        String str = paramXmlPullParser.getName();
        VGroup vGroup = arrayDeque.peek();
        if ("path".equals(str)) {
          vFullPath = new VFullPath();
          vFullPath.inflate(paramResources, paramAttributeSet, paramTheme, paramXmlPullParser);
          vGroup.mChildren.add(vFullPath);
          if (vFullPath.getPathName() != null)
            vPathRenderer.mVGTargetsMap.put(vFullPath.getPathName(), vFullPath); 
          m = 0;
          k = vectorDrawableCompatState.mChangingConfigurations;
          vectorDrawableCompatState.mChangingConfigurations = vFullPath.mChangingConfigurations | k;
        } else {
          VClipPath vClipPath;
          if ("clip-path".equals(vFullPath)) {
            vClipPath = new VClipPath();
            vClipPath.inflate(paramResources, paramAttributeSet, paramTheme, paramXmlPullParser);
            vGroup.mChildren.add(vClipPath);
            if (vClipPath.getPathName() != null)
              vPathRenderer.mVGTargetsMap.put(vClipPath.getPathName(), vClipPath); 
            m = vectorDrawableCompatState.mChangingConfigurations;
            vectorDrawableCompatState.mChangingConfigurations = vClipPath.mChangingConfigurations | m;
            m = k;
          } else {
            m = k;
            if ("group".equals(vClipPath)) {
              VGroup vGroup1 = new VGroup();
              vGroup1.inflate(paramResources, paramAttributeSet, paramTheme, paramXmlPullParser);
              vGroup.mChildren.add(vGroup1);
              arrayDeque.push(vGroup1);
              if (vGroup1.getGroupName() != null)
                vPathRenderer.mVGTargetsMap.put(vGroup1.getGroupName(), vGroup1); 
              m = vectorDrawableCompatState.mChangingConfigurations;
              vectorDrawableCompatState.mChangingConfigurations = vGroup1.mChangingConfigurations | m;
              m = k;
            } 
          } 
        } 
      } else {
        m = k;
        if (i == 3) {
          m = k;
          if ("group".equals(paramXmlPullParser.getName())) {
            arrayDeque.pop();
            m = k;
          } 
        } 
      } 
      i = paramXmlPullParser.next();
    } 
    if (k == 0)
      return; 
    throw new XmlPullParserException("no path defined");
  }
  
  private boolean needMirroring() {
    int i = Build.VERSION.SDK_INT;
    boolean bool1 = false;
    boolean bool2 = bool1;
    if (i >= 17) {
      bool2 = bool1;
      if (isAutoMirrored()) {
        bool2 = bool1;
        if (DrawableCompat.getLayoutDirection(this) == 1)
          bool2 = true; 
      } 
    } 
    return bool2;
  }
  
  private static PorterDuff.Mode parseTintModeCompat(int paramInt, PorterDuff.Mode paramMode) {
    if (paramInt != 3) {
      if (paramInt != 5) {
        if (paramInt != 9) {
          switch (paramInt) {
            default:
              return paramMode;
            case 16:
              return PorterDuff.Mode.ADD;
            case 15:
              return PorterDuff.Mode.SCREEN;
            case 14:
              break;
          } 
          return PorterDuff.Mode.MULTIPLY;
        } 
        return PorterDuff.Mode.SRC_ATOP;
      } 
      return PorterDuff.Mode.SRC_IN;
    } 
    return PorterDuff.Mode.SRC_OVER;
  }
  
  private void printGroupTree(VGroup paramVGroup, int paramInt) {
    boolean bool = false;
    String str = "";
    byte b;
    for (b = 0; b < paramInt; b++) {
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(str);
      stringBuilder1.append("    ");
      str = stringBuilder1.toString();
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str);
    stringBuilder.append("current group is :");
    stringBuilder.append(paramVGroup.getGroupName());
    stringBuilder.append(" rotation is ");
    stringBuilder.append(paramVGroup.mRotate);
    Log.v("VectorDrawableCompat", stringBuilder.toString());
    stringBuilder = new StringBuilder();
    stringBuilder.append(str);
    stringBuilder.append("matrix is :");
    stringBuilder.append(paramVGroup.getLocalMatrix().toString());
    Log.v("VectorDrawableCompat", stringBuilder.toString());
    for (b = bool; b < paramVGroup.mChildren.size(); b++) {
      str = (String)paramVGroup.mChildren.get(b);
      if (str instanceof VGroup) {
        printGroupTree((VGroup)str, paramInt + 1);
      } else {
        ((VPath)str).printVPath(paramInt + 1);
      } 
    } 
  }
  
  private void updateStateFromTypedArray(TypedArray paramTypedArray, XmlPullParser paramXmlPullParser) throws XmlPullParserException {
    String str;
    VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
    VPathRenderer vPathRenderer = vectorDrawableCompatState.mVPathRenderer;
    vectorDrawableCompatState.mTintMode = parseTintModeCompat(TypedArrayUtils.getNamedInt(paramTypedArray, paramXmlPullParser, "tintMode", 6, -1), PorterDuff.Mode.SRC_IN);
    ColorStateList colorStateList = paramTypedArray.getColorStateList(1);
    if (colorStateList != null)
      vectorDrawableCompatState.mTint = colorStateList; 
    vectorDrawableCompatState.mAutoMirrored = TypedArrayUtils.getNamedBoolean(paramTypedArray, paramXmlPullParser, "autoMirrored", 5, vectorDrawableCompatState.mAutoMirrored);
    vPathRenderer.mViewportWidth = TypedArrayUtils.getNamedFloat(paramTypedArray, paramXmlPullParser, "viewportWidth", 7, vPathRenderer.mViewportWidth);
    vPathRenderer.mViewportHeight = TypedArrayUtils.getNamedFloat(paramTypedArray, paramXmlPullParser, "viewportHeight", 8, vPathRenderer.mViewportHeight);
    if (vPathRenderer.mViewportWidth > 0.0F) {
      if (vPathRenderer.mViewportHeight > 0.0F) {
        vPathRenderer.mBaseWidth = paramTypedArray.getDimension(3, vPathRenderer.mBaseWidth);
        vPathRenderer.mBaseHeight = paramTypedArray.getDimension(2, vPathRenderer.mBaseHeight);
        if (vPathRenderer.mBaseWidth > 0.0F) {
          if (vPathRenderer.mBaseHeight > 0.0F) {
            vPathRenderer.setAlpha(TypedArrayUtils.getNamedFloat(paramTypedArray, paramXmlPullParser, "alpha", 4, vPathRenderer.getAlpha()));
            str = paramTypedArray.getString(0);
            if (str != null) {
              vPathRenderer.mRootName = str;
              vPathRenderer.mVGTargetsMap.put(str, vPathRenderer);
            } 
            return;
          } 
          StringBuilder stringBuilder3 = new StringBuilder();
          stringBuilder3.append(str.getPositionDescription());
          stringBuilder3.append("<vector> tag requires height > 0");
          throw new XmlPullParserException(stringBuilder3.toString());
        } 
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(str.getPositionDescription());
        stringBuilder2.append("<vector> tag requires width > 0");
        throw new XmlPullParserException(stringBuilder2.toString());
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      stringBuilder1.append(str.getPositionDescription());
      stringBuilder1.append("<vector> tag requires viewportHeight > 0");
      throw new XmlPullParserException(stringBuilder1.toString());
    } 
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(str.getPositionDescription());
    stringBuilder.append("<vector> tag requires viewportWidth > 0");
    throw new XmlPullParserException(stringBuilder.toString());
  }
  
  public boolean canApplyTheme() {
    if (this.mDelegateDrawable != null)
      DrawableCompat.canApplyTheme(this.mDelegateDrawable); 
    return false;
  }
  
  public void draw(Canvas paramCanvas) {
    if (this.mDelegateDrawable != null) {
      this.mDelegateDrawable.draw(paramCanvas);
      return;
    } 
    copyBounds(this.mTmpBounds);
    if (this.mTmpBounds.width() > 0 && this.mTmpBounds.height() > 0) {
      PorterDuffColorFilter porterDuffColorFilter;
      ColorFilter colorFilter1 = this.mColorFilter;
      ColorFilter colorFilter2 = colorFilter1;
      if (colorFilter1 == null)
        porterDuffColorFilter = this.mTintFilter; 
      paramCanvas.getMatrix(this.mTmpMatrix);
      this.mTmpMatrix.getValues(this.mTmpFloats);
      float f1 = Math.abs(this.mTmpFloats[0]);
      float f2 = Math.abs(this.mTmpFloats[4]);
      float f3 = Math.abs(this.mTmpFloats[1]);
      float f4 = Math.abs(this.mTmpFloats[3]);
      if (f3 != 0.0F || f4 != 0.0F) {
        f1 = 1.0F;
        f2 = 1.0F;
      } 
      int i = (int)(this.mTmpBounds.width() * f1);
      int j = (int)(this.mTmpBounds.height() * f2);
      i = Math.min(2048, i);
      int k = Math.min(2048, j);
      if (i > 0 && k > 0) {
        j = paramCanvas.save();
        paramCanvas.translate(this.mTmpBounds.left, this.mTmpBounds.top);
        if (needMirroring()) {
          paramCanvas.translate(this.mTmpBounds.width(), 0.0F);
          paramCanvas.scale(-1.0F, 1.0F);
        } 
        this.mTmpBounds.offsetTo(0, 0);
        this.mVectorState.createCachedBitmapIfNeeded(i, k);
        if (!this.mAllowCaching) {
          this.mVectorState.updateCachedBitmap(i, k);
        } else if (!this.mVectorState.canReuseCache()) {
          this.mVectorState.updateCachedBitmap(i, k);
          this.mVectorState.updateCacheStates();
        } 
        this.mVectorState.drawCachedBitmapWithRootAlpha(paramCanvas, (ColorFilter)porterDuffColorFilter, this.mTmpBounds);
        paramCanvas.restoreToCount(j);
      } 
    } 
  }
  
  public int getAlpha() {
    return (this.mDelegateDrawable != null) ? DrawableCompat.getAlpha(this.mDelegateDrawable) : this.mVectorState.mVPathRenderer.getRootAlpha();
  }
  
  public int getChangingConfigurations() {
    return (this.mDelegateDrawable != null) ? this.mDelegateDrawable.getChangingConfigurations() : (super.getChangingConfigurations() | this.mVectorState.getChangingConfigurations());
  }
  
  public Drawable.ConstantState getConstantState() {
    if (this.mDelegateDrawable != null && Build.VERSION.SDK_INT >= 24)
      return new VectorDrawableDelegateState(this.mDelegateDrawable.getConstantState()); 
    this.mVectorState.mChangingConfigurations = getChangingConfigurations();
    return this.mVectorState;
  }
  
  public int getIntrinsicHeight() {
    return (this.mDelegateDrawable != null) ? this.mDelegateDrawable.getIntrinsicHeight() : (int)this.mVectorState.mVPathRenderer.mBaseHeight;
  }
  
  public int getIntrinsicWidth() {
    return (this.mDelegateDrawable != null) ? this.mDelegateDrawable.getIntrinsicWidth() : (int)this.mVectorState.mVPathRenderer.mBaseWidth;
  }
  
  public int getOpacity() {
    return (this.mDelegateDrawable != null) ? this.mDelegateDrawable.getOpacity() : -3;
  }
  
  public float getPixelSize() {
    VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
    if (vectorDrawableCompatState == null || vectorDrawableCompatState.mVPathRenderer == null || this.mVectorState.mVPathRenderer.mBaseWidth == 0.0F || this.mVectorState.mVPathRenderer.mBaseHeight == 0.0F || this.mVectorState.mVPathRenderer.mViewportHeight == 0.0F || this.mVectorState.mVPathRenderer.mViewportWidth == 0.0F)
      return 1.0F; 
    float f1 = this.mVectorState.mVPathRenderer.mBaseWidth;
    float f2 = this.mVectorState.mVPathRenderer.mBaseHeight;
    float f3 = this.mVectorState.mVPathRenderer.mViewportWidth;
    float f4 = this.mVectorState.mVPathRenderer.mViewportHeight;
    return Math.min(f3 / f1, f4 / f2);
  }
  
  Object getTargetByName(String paramString) {
    return this.mVectorState.mVPathRenderer.mVGTargetsMap.get(paramString);
  }
  
  public void inflate(Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet) throws XmlPullParserException, IOException {
    if (this.mDelegateDrawable != null) {
      this.mDelegateDrawable.inflate(paramResources, paramXmlPullParser, paramAttributeSet);
      return;
    } 
    inflate(paramResources, paramXmlPullParser, paramAttributeSet, (Resources.Theme)null);
  }
  
  public void inflate(Resources paramResources, XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Resources.Theme paramTheme) throws XmlPullParserException, IOException {
    if (this.mDelegateDrawable != null) {
      DrawableCompat.inflate(this.mDelegateDrawable, paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
      return;
    } 
    VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
    vectorDrawableCompatState.mVPathRenderer = new VPathRenderer();
    TypedArray typedArray = TypedArrayUtils.obtainAttributes(paramResources, paramTheme, paramAttributeSet, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_TYPE_ARRAY);
    updateStateFromTypedArray(typedArray, paramXmlPullParser);
    typedArray.recycle();
    vectorDrawableCompatState.mChangingConfigurations = getChangingConfigurations();
    vectorDrawableCompatState.mCacheDirty = true;
    inflateInternal(paramResources, paramXmlPullParser, paramAttributeSet, paramTheme);
    this.mTintFilter = updateTintFilter(this.mTintFilter, vectorDrawableCompatState.mTint, vectorDrawableCompatState.mTintMode);
  }
  
  public void invalidateSelf() {
    if (this.mDelegateDrawable != null) {
      this.mDelegateDrawable.invalidateSelf();
      return;
    } 
    super.invalidateSelf();
  }
  
  public boolean isAutoMirrored() {
    return (this.mDelegateDrawable != null) ? DrawableCompat.isAutoMirrored(this.mDelegateDrawable) : this.mVectorState.mAutoMirrored;
  }
  
  public boolean isStateful() {
    if (this.mDelegateDrawable != null)
      return this.mDelegateDrawable.isStateful(); 
    if (!super.isStateful()) {
      VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
      return (vectorDrawableCompatState != null && vectorDrawableCompatState.mTint != null && this.mVectorState.mTint.isStateful());
    } 
    return true;
  }
  
  public Drawable mutate() {
    if (this.mDelegateDrawable != null) {
      this.mDelegateDrawable.mutate();
      return this;
    } 
    if (!this.mMutated && super.mutate() == this) {
      this.mVectorState = new VectorDrawableCompatState(this.mVectorState);
      this.mMutated = true;
    } 
    return this;
  }
  
  protected void onBoundsChange(Rect paramRect) {
    if (this.mDelegateDrawable != null)
      this.mDelegateDrawable.setBounds(paramRect); 
  }
  
  protected boolean onStateChange(int[] paramArrayOfint) {
    if (this.mDelegateDrawable != null)
      return this.mDelegateDrawable.setState(paramArrayOfint); 
    VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
    if (vectorDrawableCompatState.mTint != null && vectorDrawableCompatState.mTintMode != null) {
      this.mTintFilter = updateTintFilter(this.mTintFilter, vectorDrawableCompatState.mTint, vectorDrawableCompatState.mTintMode);
      invalidateSelf();
      return true;
    } 
    return false;
  }
  
  public void scheduleSelf(Runnable paramRunnable, long paramLong) {
    if (this.mDelegateDrawable != null) {
      this.mDelegateDrawable.scheduleSelf(paramRunnable, paramLong);
      return;
    } 
    super.scheduleSelf(paramRunnable, paramLong);
  }
  
  void setAllowCaching(boolean paramBoolean) {
    this.mAllowCaching = paramBoolean;
  }
  
  public void setAlpha(int paramInt) {
    if (this.mDelegateDrawable != null) {
      this.mDelegateDrawable.setAlpha(paramInt);
      return;
    } 
    if (this.mVectorState.mVPathRenderer.getRootAlpha() != paramInt) {
      this.mVectorState.mVPathRenderer.setRootAlpha(paramInt);
      invalidateSelf();
    } 
  }
  
  public void setAutoMirrored(boolean paramBoolean) {
    if (this.mDelegateDrawable != null) {
      DrawableCompat.setAutoMirrored(this.mDelegateDrawable, paramBoolean);
      return;
    } 
    this.mVectorState.mAutoMirrored = paramBoolean;
  }
  
  public void setColorFilter(ColorFilter paramColorFilter) {
    if (this.mDelegateDrawable != null) {
      this.mDelegateDrawable.setColorFilter(paramColorFilter);
      return;
    } 
    this.mColorFilter = paramColorFilter;
    invalidateSelf();
  }
  
  public void setTint(int paramInt) {
    if (this.mDelegateDrawable != null) {
      DrawableCompat.setTint(this.mDelegateDrawable, paramInt);
      return;
    } 
    setTintList(ColorStateList.valueOf(paramInt));
  }
  
  public void setTintList(ColorStateList paramColorStateList) {
    if (this.mDelegateDrawable != null) {
      DrawableCompat.setTintList(this.mDelegateDrawable, paramColorStateList);
      return;
    } 
    VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
    if (vectorDrawableCompatState.mTint != paramColorStateList) {
      vectorDrawableCompatState.mTint = paramColorStateList;
      this.mTintFilter = updateTintFilter(this.mTintFilter, paramColorStateList, vectorDrawableCompatState.mTintMode);
      invalidateSelf();
    } 
  }
  
  public void setTintMode(PorterDuff.Mode paramMode) {
    if (this.mDelegateDrawable != null) {
      DrawableCompat.setTintMode(this.mDelegateDrawable, paramMode);
      return;
    } 
    VectorDrawableCompatState vectorDrawableCompatState = this.mVectorState;
    if (vectorDrawableCompatState.mTintMode != paramMode) {
      vectorDrawableCompatState.mTintMode = paramMode;
      this.mTintFilter = updateTintFilter(this.mTintFilter, vectorDrawableCompatState.mTint, paramMode);
      invalidateSelf();
    } 
  }
  
  public boolean setVisible(boolean paramBoolean1, boolean paramBoolean2) {
    return (this.mDelegateDrawable != null) ? this.mDelegateDrawable.setVisible(paramBoolean1, paramBoolean2) : super.setVisible(paramBoolean1, paramBoolean2);
  }
  
  public void unscheduleSelf(Runnable paramRunnable) {
    if (this.mDelegateDrawable != null) {
      this.mDelegateDrawable.unscheduleSelf(paramRunnable);
      return;
    } 
    super.unscheduleSelf(paramRunnable);
  }
  
  PorterDuffColorFilter updateTintFilter(PorterDuffColorFilter paramPorterDuffColorFilter, ColorStateList paramColorStateList, PorterDuff.Mode paramMode) {
    return (paramColorStateList == null || paramMode == null) ? null : new PorterDuffColorFilter(paramColorStateList.getColorForState(getState(), 0), paramMode);
  }
  
  private static class VClipPath extends VPath {
    public VClipPath() {}
    
    public VClipPath(VClipPath param1VClipPath) {
      super(param1VClipPath);
    }
    
    private void updateStateFromTypedArray(TypedArray param1TypedArray) {
      String str2 = param1TypedArray.getString(0);
      if (str2 != null)
        this.mPathName = str2; 
      String str1 = param1TypedArray.getString(1);
      if (str1 != null)
        this.mNodes = PathParser.createNodesFromPathData(str1); 
    }
    
    public void inflate(Resources param1Resources, AttributeSet param1AttributeSet, Resources.Theme param1Theme, XmlPullParser param1XmlPullParser) {
      if (!TypedArrayUtils.hasAttribute(param1XmlPullParser, "pathData"))
        return; 
      TypedArray typedArray = TypedArrayUtils.obtainAttributes(param1Resources, param1Theme, param1AttributeSet, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_CLIP_PATH);
      updateStateFromTypedArray(typedArray);
      typedArray.recycle();
    }
    
    public boolean isClipPath() {
      return true;
    }
  }
  
  private static class VFullPath extends VPath {
    private static final int FILL_TYPE_WINDING = 0;
    
    float mFillAlpha = 1.0F;
    
    int mFillColor = 0;
    
    int mFillRule = 0;
    
    float mStrokeAlpha = 1.0F;
    
    int mStrokeColor = 0;
    
    Paint.Cap mStrokeLineCap = Paint.Cap.BUTT;
    
    Paint.Join mStrokeLineJoin = Paint.Join.MITER;
    
    float mStrokeMiterlimit = 4.0F;
    
    float mStrokeWidth = 0.0F;
    
    private int[] mThemeAttrs;
    
    float mTrimPathEnd = 1.0F;
    
    float mTrimPathOffset = 0.0F;
    
    float mTrimPathStart = 0.0F;
    
    public VFullPath() {}
    
    public VFullPath(VFullPath param1VFullPath) {
      super(param1VFullPath);
      this.mThemeAttrs = param1VFullPath.mThemeAttrs;
      this.mStrokeColor = param1VFullPath.mStrokeColor;
      this.mStrokeWidth = param1VFullPath.mStrokeWidth;
      this.mStrokeAlpha = param1VFullPath.mStrokeAlpha;
      this.mFillColor = param1VFullPath.mFillColor;
      this.mFillRule = param1VFullPath.mFillRule;
      this.mFillAlpha = param1VFullPath.mFillAlpha;
      this.mTrimPathStart = param1VFullPath.mTrimPathStart;
      this.mTrimPathEnd = param1VFullPath.mTrimPathEnd;
      this.mTrimPathOffset = param1VFullPath.mTrimPathOffset;
      this.mStrokeLineCap = param1VFullPath.mStrokeLineCap;
      this.mStrokeLineJoin = param1VFullPath.mStrokeLineJoin;
      this.mStrokeMiterlimit = param1VFullPath.mStrokeMiterlimit;
    }
    
    private Paint.Cap getStrokeLineCap(int param1Int, Paint.Cap param1Cap) {
      return (param1Int != 0) ? ((param1Int != 1) ? ((param1Int != 2) ? param1Cap : Paint.Cap.SQUARE) : Paint.Cap.ROUND) : Paint.Cap.BUTT;
    }
    
    private Paint.Join getStrokeLineJoin(int param1Int, Paint.Join param1Join) {
      return (param1Int != 0) ? ((param1Int != 1) ? ((param1Int != 2) ? param1Join : Paint.Join.BEVEL) : Paint.Join.ROUND) : Paint.Join.MITER;
    }
    
    private void updateStateFromTypedArray(TypedArray param1TypedArray, XmlPullParser param1XmlPullParser) {
      this.mThemeAttrs = null;
      if (!TypedArrayUtils.hasAttribute(param1XmlPullParser, "pathData"))
        return; 
      String str = param1TypedArray.getString(0);
      if (str != null)
        this.mPathName = str; 
      str = param1TypedArray.getString(2);
      if (str != null)
        this.mNodes = PathParser.createNodesFromPathData(str); 
      this.mFillColor = TypedArrayUtils.getNamedColor(param1TypedArray, param1XmlPullParser, "fillColor", 1, this.mFillColor);
      this.mFillAlpha = TypedArrayUtils.getNamedFloat(param1TypedArray, param1XmlPullParser, "fillAlpha", 12, this.mFillAlpha);
      this.mStrokeLineCap = getStrokeLineCap(TypedArrayUtils.getNamedInt(param1TypedArray, param1XmlPullParser, "strokeLineCap", 8, -1), this.mStrokeLineCap);
      this.mStrokeLineJoin = getStrokeLineJoin(TypedArrayUtils.getNamedInt(param1TypedArray, param1XmlPullParser, "strokeLineJoin", 9, -1), this.mStrokeLineJoin);
      this.mStrokeMiterlimit = TypedArrayUtils.getNamedFloat(param1TypedArray, param1XmlPullParser, "strokeMiterLimit", 10, this.mStrokeMiterlimit);
      this.mStrokeColor = TypedArrayUtils.getNamedColor(param1TypedArray, param1XmlPullParser, "strokeColor", 3, this.mStrokeColor);
      this.mStrokeAlpha = TypedArrayUtils.getNamedFloat(param1TypedArray, param1XmlPullParser, "strokeAlpha", 11, this.mStrokeAlpha);
      this.mStrokeWidth = TypedArrayUtils.getNamedFloat(param1TypedArray, param1XmlPullParser, "strokeWidth", 4, this.mStrokeWidth);
      this.mTrimPathEnd = TypedArrayUtils.getNamedFloat(param1TypedArray, param1XmlPullParser, "trimPathEnd", 6, this.mTrimPathEnd);
      this.mTrimPathOffset = TypedArrayUtils.getNamedFloat(param1TypedArray, param1XmlPullParser, "trimPathOffset", 7, this.mTrimPathOffset);
      this.mTrimPathStart = TypedArrayUtils.getNamedFloat(param1TypedArray, param1XmlPullParser, "trimPathStart", 5, this.mTrimPathStart);
      this.mFillRule = TypedArrayUtils.getNamedInt(param1TypedArray, param1XmlPullParser, "fillType", 13, this.mFillRule);
    }
    
    public void applyTheme(Resources.Theme param1Theme) {
      if (this.mThemeAttrs == null);
    }
    
    public boolean canApplyTheme() {
      boolean bool;
      if (this.mThemeAttrs != null) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    float getFillAlpha() {
      return this.mFillAlpha;
    }
    
    int getFillColor() {
      return this.mFillColor;
    }
    
    float getStrokeAlpha() {
      return this.mStrokeAlpha;
    }
    
    int getStrokeColor() {
      return this.mStrokeColor;
    }
    
    float getStrokeWidth() {
      return this.mStrokeWidth;
    }
    
    float getTrimPathEnd() {
      return this.mTrimPathEnd;
    }
    
    float getTrimPathOffset() {
      return this.mTrimPathOffset;
    }
    
    float getTrimPathStart() {
      return this.mTrimPathStart;
    }
    
    public void inflate(Resources param1Resources, AttributeSet param1AttributeSet, Resources.Theme param1Theme, XmlPullParser param1XmlPullParser) {
      TypedArray typedArray = TypedArrayUtils.obtainAttributes(param1Resources, param1Theme, param1AttributeSet, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_PATH);
      updateStateFromTypedArray(typedArray, param1XmlPullParser);
      typedArray.recycle();
    }
    
    void setFillAlpha(float param1Float) {
      this.mFillAlpha = param1Float;
    }
    
    void setFillColor(int param1Int) {
      this.mFillColor = param1Int;
    }
    
    void setStrokeAlpha(float param1Float) {
      this.mStrokeAlpha = param1Float;
    }
    
    void setStrokeColor(int param1Int) {
      this.mStrokeColor = param1Int;
    }
    
    void setStrokeWidth(float param1Float) {
      this.mStrokeWidth = param1Float;
    }
    
    void setTrimPathEnd(float param1Float) {
      this.mTrimPathEnd = param1Float;
    }
    
    void setTrimPathOffset(float param1Float) {
      this.mTrimPathOffset = param1Float;
    }
    
    void setTrimPathStart(float param1Float) {
      this.mTrimPathStart = param1Float;
    }
  }
  
  private static class VGroup {
    int mChangingConfigurations;
    
    final ArrayList<Object> mChildren = new ArrayList();
    
    private String mGroupName = null;
    
    private final Matrix mLocalMatrix = new Matrix();
    
    private float mPivotX = 0.0F;
    
    private float mPivotY = 0.0F;
    
    float mRotate = 0.0F;
    
    private float mScaleX = 1.0F;
    
    private float mScaleY = 1.0F;
    
    private final Matrix mStackedMatrix = new Matrix();
    
    private int[] mThemeAttrs;
    
    private float mTranslateX = 0.0F;
    
    private float mTranslateY = 0.0F;
    
    public VGroup() {}
    
    public VGroup(VGroup param1VGroup, ArrayMap<String, Object> param1ArrayMap) {
      this.mRotate = param1VGroup.mRotate;
      this.mPivotX = param1VGroup.mPivotX;
      this.mPivotY = param1VGroup.mPivotY;
      this.mScaleX = param1VGroup.mScaleX;
      this.mScaleY = param1VGroup.mScaleY;
      this.mTranslateX = param1VGroup.mTranslateX;
      this.mTranslateY = param1VGroup.mTranslateY;
      this.mThemeAttrs = param1VGroup.mThemeAttrs;
      String str = param1VGroup.mGroupName;
      this.mGroupName = str;
      this.mChangingConfigurations = param1VGroup.mChangingConfigurations;
      if (str != null)
        param1ArrayMap.put(str, this); 
      this.mLocalMatrix.set(param1VGroup.mLocalMatrix);
      ArrayList<Object> arrayList = param1VGroup.mChildren;
      for (byte b = 0; b < arrayList.size(); b++) {
        param1VGroup = (VGroup)arrayList.get(b);
        if (param1VGroup instanceof VGroup) {
          param1VGroup = param1VGroup;
          this.mChildren.add(new VGroup(param1VGroup, param1ArrayMap));
        } else {
          VectorDrawableCompat.VFullPath vFullPath;
          VectorDrawableCompat.VClipPath vClipPath;
          if (param1VGroup instanceof VectorDrawableCompat.VFullPath) {
            vFullPath = new VectorDrawableCompat.VFullPath((VectorDrawableCompat.VFullPath)param1VGroup);
          } else if (vFullPath instanceof VectorDrawableCompat.VClipPath) {
            vClipPath = new VectorDrawableCompat.VClipPath((VectorDrawableCompat.VClipPath)vFullPath);
          } else {
            throw new IllegalStateException("Unknown object in the tree!");
          } 
          this.mChildren.add(vClipPath);
          if (vClipPath.mPathName != null)
            param1ArrayMap.put(vClipPath.mPathName, vClipPath); 
        } 
      } 
    }
    
    private void updateLocalMatrix() {
      this.mLocalMatrix.reset();
      this.mLocalMatrix.postTranslate(-this.mPivotX, -this.mPivotY);
      this.mLocalMatrix.postScale(this.mScaleX, this.mScaleY);
      this.mLocalMatrix.postRotate(this.mRotate, 0.0F, 0.0F);
      this.mLocalMatrix.postTranslate(this.mTranslateX + this.mPivotX, this.mTranslateY + this.mPivotY);
    }
    
    private void updateStateFromTypedArray(TypedArray param1TypedArray, XmlPullParser param1XmlPullParser) {
      this.mThemeAttrs = null;
      this.mRotate = TypedArrayUtils.getNamedFloat(param1TypedArray, param1XmlPullParser, "rotation", 5, this.mRotate);
      this.mPivotX = param1TypedArray.getFloat(1, this.mPivotX);
      this.mPivotY = param1TypedArray.getFloat(2, this.mPivotY);
      this.mScaleX = TypedArrayUtils.getNamedFloat(param1TypedArray, param1XmlPullParser, "scaleX", 3, this.mScaleX);
      this.mScaleY = TypedArrayUtils.getNamedFloat(param1TypedArray, param1XmlPullParser, "scaleY", 4, this.mScaleY);
      this.mTranslateX = TypedArrayUtils.getNamedFloat(param1TypedArray, param1XmlPullParser, "translateX", 6, this.mTranslateX);
      this.mTranslateY = TypedArrayUtils.getNamedFloat(param1TypedArray, param1XmlPullParser, "translateY", 7, this.mTranslateY);
      String str = param1TypedArray.getString(0);
      if (str != null)
        this.mGroupName = str; 
      updateLocalMatrix();
    }
    
    public String getGroupName() {
      return this.mGroupName;
    }
    
    public Matrix getLocalMatrix() {
      return this.mLocalMatrix;
    }
    
    public float getPivotX() {
      return this.mPivotX;
    }
    
    public float getPivotY() {
      return this.mPivotY;
    }
    
    public float getRotation() {
      return this.mRotate;
    }
    
    public float getScaleX() {
      return this.mScaleX;
    }
    
    public float getScaleY() {
      return this.mScaleY;
    }
    
    public float getTranslateX() {
      return this.mTranslateX;
    }
    
    public float getTranslateY() {
      return this.mTranslateY;
    }
    
    public void inflate(Resources param1Resources, AttributeSet param1AttributeSet, Resources.Theme param1Theme, XmlPullParser param1XmlPullParser) {
      TypedArray typedArray = TypedArrayUtils.obtainAttributes(param1Resources, param1Theme, param1AttributeSet, AndroidResources.STYLEABLE_VECTOR_DRAWABLE_GROUP);
      updateStateFromTypedArray(typedArray, param1XmlPullParser);
      typedArray.recycle();
    }
    
    public void setPivotX(float param1Float) {
      if (param1Float != this.mPivotX) {
        this.mPivotX = param1Float;
        updateLocalMatrix();
      } 
    }
    
    public void setPivotY(float param1Float) {
      if (param1Float != this.mPivotY) {
        this.mPivotY = param1Float;
        updateLocalMatrix();
      } 
    }
    
    public void setRotation(float param1Float) {
      if (param1Float != this.mRotate) {
        this.mRotate = param1Float;
        updateLocalMatrix();
      } 
    }
    
    public void setScaleX(float param1Float) {
      if (param1Float != this.mScaleX) {
        this.mScaleX = param1Float;
        updateLocalMatrix();
      } 
    }
    
    public void setScaleY(float param1Float) {
      if (param1Float != this.mScaleY) {
        this.mScaleY = param1Float;
        updateLocalMatrix();
      } 
    }
    
    public void setTranslateX(float param1Float) {
      if (param1Float != this.mTranslateX) {
        this.mTranslateX = param1Float;
        updateLocalMatrix();
      } 
    }
    
    public void setTranslateY(float param1Float) {
      if (param1Float != this.mTranslateY) {
        this.mTranslateY = param1Float;
        updateLocalMatrix();
      } 
    }
  }
  
  private static class VPath {
    int mChangingConfigurations;
    
    protected PathParser.PathDataNode[] mNodes = null;
    
    String mPathName;
    
    public VPath() {}
    
    public VPath(VPath param1VPath) {
      this.mPathName = param1VPath.mPathName;
      this.mChangingConfigurations = param1VPath.mChangingConfigurations;
      this.mNodes = PathParser.deepCopyNodes(param1VPath.mNodes);
    }
    
    public void applyTheme(Resources.Theme param1Theme) {}
    
    public boolean canApplyTheme() {
      return false;
    }
    
    public PathParser.PathDataNode[] getPathData() {
      return this.mNodes;
    }
    
    public String getPathName() {
      return this.mPathName;
    }
    
    public boolean isClipPath() {
      return false;
    }
    
    public String nodesToString(PathParser.PathDataNode[] param1ArrayOfPathDataNode) {
      String str = " ";
      for (byte b = 0; b < param1ArrayOfPathDataNode.length; b++) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append((param1ArrayOfPathDataNode[b]).mType);
        stringBuilder.append(":");
        str = stringBuilder.toString();
        float[] arrayOfFloat = (param1ArrayOfPathDataNode[b]).mParams;
        for (byte b1 = 0; b1 < arrayOfFloat.length; b1++) {
          StringBuilder stringBuilder1 = new StringBuilder();
          stringBuilder1.append(str);
          stringBuilder1.append(arrayOfFloat[b1]);
          stringBuilder1.append(",");
          str = stringBuilder1.toString();
        } 
      } 
      return str;
    }
    
    public void printVPath(int param1Int) {
      String str = "";
      for (byte b = 0; b < param1Int; b++) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append(str);
        stringBuilder1.append("    ");
        str = stringBuilder1.toString();
      } 
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(str);
      stringBuilder.append("current path is :");
      stringBuilder.append(this.mPathName);
      stringBuilder.append(" pathData is ");
      stringBuilder.append(nodesToString(this.mNodes));
      Log.v("VectorDrawableCompat", stringBuilder.toString());
    }
    
    public void setPathData(PathParser.PathDataNode[] param1ArrayOfPathDataNode) {
      if (!PathParser.canMorph(this.mNodes, param1ArrayOfPathDataNode)) {
        this.mNodes = PathParser.deepCopyNodes(param1ArrayOfPathDataNode);
      } else {
        PathParser.updateNodes(this.mNodes, param1ArrayOfPathDataNode);
      } 
    }
    
    public void toPath(Path param1Path) {
      param1Path.reset();
      PathParser.PathDataNode[] arrayOfPathDataNode = this.mNodes;
      if (arrayOfPathDataNode != null)
        PathParser.PathDataNode.nodesToPath(arrayOfPathDataNode, param1Path); 
    }
  }
  
  private static class VPathRenderer {
    private static final Matrix IDENTITY_MATRIX = new Matrix();
    
    float mBaseHeight = 0.0F;
    
    float mBaseWidth = 0.0F;
    
    private int mChangingConfigurations;
    
    private Paint mFillPaint;
    
    private final Matrix mFinalPathMatrix = new Matrix();
    
    private final Path mPath;
    
    private PathMeasure mPathMeasure;
    
    private final Path mRenderPath;
    
    int mRootAlpha = 255;
    
    final VectorDrawableCompat.VGroup mRootGroup;
    
    String mRootName = null;
    
    private Paint mStrokePaint;
    
    final ArrayMap<String, Object> mVGTargetsMap;
    
    float mViewportHeight = 0.0F;
    
    float mViewportWidth = 0.0F;
    
    public VPathRenderer() {
      this.mVGTargetsMap = new ArrayMap();
      this.mRootGroup = new VectorDrawableCompat.VGroup();
      this.mPath = new Path();
      this.mRenderPath = new Path();
    }
    
    public VPathRenderer(VPathRenderer param1VPathRenderer) {
      ArrayMap<String, Object> arrayMap = new ArrayMap();
      this.mVGTargetsMap = arrayMap;
      this.mRootGroup = new VectorDrawableCompat.VGroup(param1VPathRenderer.mRootGroup, arrayMap);
      this.mPath = new Path(param1VPathRenderer.mPath);
      this.mRenderPath = new Path(param1VPathRenderer.mRenderPath);
      this.mBaseWidth = param1VPathRenderer.mBaseWidth;
      this.mBaseHeight = param1VPathRenderer.mBaseHeight;
      this.mViewportWidth = param1VPathRenderer.mViewportWidth;
      this.mViewportHeight = param1VPathRenderer.mViewportHeight;
      this.mChangingConfigurations = param1VPathRenderer.mChangingConfigurations;
      this.mRootAlpha = param1VPathRenderer.mRootAlpha;
      this.mRootName = param1VPathRenderer.mRootName;
      String str = param1VPathRenderer.mRootName;
      if (str != null)
        this.mVGTargetsMap.put(str, this); 
    }
    
    private static float cross(float param1Float1, float param1Float2, float param1Float3, float param1Float4) {
      return param1Float1 * param1Float4 - param1Float2 * param1Float3;
    }
    
    private void drawGroupTree(VectorDrawableCompat.VGroup param1VGroup, Matrix param1Matrix, Canvas param1Canvas, int param1Int1, int param1Int2, ColorFilter param1ColorFilter) {
      param1VGroup.mStackedMatrix.set(param1Matrix);
      param1VGroup.mStackedMatrix.preConcat(param1VGroup.mLocalMatrix);
      param1Canvas.save();
      for (byte b = 0; b < param1VGroup.mChildren.size(); b++) {
        param1Matrix = (Matrix)param1VGroup.mChildren.get(b);
        if (param1Matrix instanceof VectorDrawableCompat.VGroup) {
          drawGroupTree((VectorDrawableCompat.VGroup)param1Matrix, param1VGroup.mStackedMatrix, param1Canvas, param1Int1, param1Int2, param1ColorFilter);
        } else if (param1Matrix instanceof VectorDrawableCompat.VPath) {
          drawPath(param1VGroup, (VectorDrawableCompat.VPath)param1Matrix, param1Canvas, param1Int1, param1Int2, param1ColorFilter);
        } 
      } 
      param1Canvas.restore();
    }
    
    private void drawPath(VectorDrawableCompat.VGroup param1VGroup, VectorDrawableCompat.VPath param1VPath, Canvas param1Canvas, int param1Int1, int param1Int2, ColorFilter param1ColorFilter) {
      float f1 = param1Int1 / this.mViewportWidth;
      float f2 = param1Int2 / this.mViewportHeight;
      float f3 = Math.min(f1, f2);
      Matrix matrix = param1VGroup.mStackedMatrix;
      this.mFinalPathMatrix.set(matrix);
      this.mFinalPathMatrix.postScale(f1, f2);
      f1 = getMatrixScale(matrix);
      if (f1 == 0.0F)
        return; 
      param1VPath.toPath(this.mPath);
      Path path = this.mPath;
      this.mRenderPath.reset();
      if (param1VPath.isClipPath()) {
        this.mRenderPath.addPath(path, this.mFinalPathMatrix);
        param1Canvas.clipPath(this.mRenderPath);
      } else {
        param1VPath = param1VPath;
        if (((VectorDrawableCompat.VFullPath)param1VPath).mTrimPathStart != 0.0F || ((VectorDrawableCompat.VFullPath)param1VPath).mTrimPathEnd != 1.0F) {
          float f4 = ((VectorDrawableCompat.VFullPath)param1VPath).mTrimPathStart;
          float f5 = ((VectorDrawableCompat.VFullPath)param1VPath).mTrimPathOffset;
          float f6 = ((VectorDrawableCompat.VFullPath)param1VPath).mTrimPathEnd;
          float f7 = ((VectorDrawableCompat.VFullPath)param1VPath).mTrimPathOffset;
          if (this.mPathMeasure == null)
            this.mPathMeasure = new PathMeasure(); 
          this.mPathMeasure.setPath(this.mPath, false);
          f2 = this.mPathMeasure.getLength();
          f5 = (f4 + f5) % 1.0F * f2;
          f7 = (f6 + f7) % 1.0F * f2;
          path.reset();
          if (f5 > f7) {
            this.mPathMeasure.getSegment(f5, f2, path, true);
            this.mPathMeasure.getSegment(0.0F, f7, path, true);
          } else {
            this.mPathMeasure.getSegment(f5, f7, path, true);
          } 
          path.rLineTo(0.0F, 0.0F);
        } 
        this.mRenderPath.addPath(path, this.mFinalPathMatrix);
        if (((VectorDrawableCompat.VFullPath)param1VPath).mFillColor != 0) {
          Path.FillType fillType;
          if (this.mFillPaint == null) {
            Paint paint1 = new Paint();
            this.mFillPaint = paint1;
            paint1.setStyle(Paint.Style.FILL);
            this.mFillPaint.setAntiAlias(true);
          } 
          Paint paint = this.mFillPaint;
          paint.setColor(VectorDrawableCompat.applyAlpha(((VectorDrawableCompat.VFullPath)param1VPath).mFillColor, ((VectorDrawableCompat.VFullPath)param1VPath).mFillAlpha));
          paint.setColorFilter(param1ColorFilter);
          Path path1 = this.mRenderPath;
          if (((VectorDrawableCompat.VFullPath)param1VPath).mFillRule == 0) {
            fillType = Path.FillType.WINDING;
          } else {
            fillType = Path.FillType.EVEN_ODD;
          } 
          path1.setFillType(fillType);
          param1Canvas.drawPath(this.mRenderPath, paint);
        } 
        if (((VectorDrawableCompat.VFullPath)param1VPath).mStrokeColor != 0) {
          if (this.mStrokePaint == null) {
            Paint paint1 = new Paint();
            this.mStrokePaint = paint1;
            paint1.setStyle(Paint.Style.STROKE);
            this.mStrokePaint.setAntiAlias(true);
          } 
          Paint paint = this.mStrokePaint;
          if (((VectorDrawableCompat.VFullPath)param1VPath).mStrokeLineJoin != null)
            paint.setStrokeJoin(((VectorDrawableCompat.VFullPath)param1VPath).mStrokeLineJoin); 
          if (((VectorDrawableCompat.VFullPath)param1VPath).mStrokeLineCap != null)
            paint.setStrokeCap(((VectorDrawableCompat.VFullPath)param1VPath).mStrokeLineCap); 
          paint.setStrokeMiter(((VectorDrawableCompat.VFullPath)param1VPath).mStrokeMiterlimit);
          paint.setColor(VectorDrawableCompat.applyAlpha(((VectorDrawableCompat.VFullPath)param1VPath).mStrokeColor, ((VectorDrawableCompat.VFullPath)param1VPath).mStrokeAlpha));
          paint.setColorFilter(param1ColorFilter);
          paint.setStrokeWidth(((VectorDrawableCompat.VFullPath)param1VPath).mStrokeWidth * f3 * f1);
          param1Canvas.drawPath(this.mRenderPath, paint);
        } 
      } 
    }
    
    private float getMatrixScale(Matrix param1Matrix) {
      float[] arrayOfFloat = new float[4];
      arrayOfFloat[0] = 0.0F;
      arrayOfFloat[1] = 1.0F;
      arrayOfFloat[2] = 1.0F;
      arrayOfFloat[3] = 0.0F;
      param1Matrix.mapVectors(arrayOfFloat);
      float f1 = (float)Math.hypot(arrayOfFloat[0], arrayOfFloat[1]);
      float f2 = (float)Math.hypot(arrayOfFloat[2], arrayOfFloat[3]);
      float f3 = cross(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3]);
      f2 = Math.max(f1, f2);
      f1 = 0.0F;
      if (f2 > 0.0F)
        f1 = Math.abs(f3) / f2; 
      return f1;
    }
    
    public void draw(Canvas param1Canvas, int param1Int1, int param1Int2, ColorFilter param1ColorFilter) {
      drawGroupTree(this.mRootGroup, IDENTITY_MATRIX, param1Canvas, param1Int1, param1Int2, param1ColorFilter);
    }
    
    public float getAlpha() {
      return getRootAlpha() / 255.0F;
    }
    
    public int getRootAlpha() {
      return this.mRootAlpha;
    }
    
    public void setAlpha(float param1Float) {
      setRootAlpha((int)(param1Float * 255.0F));
    }
    
    public void setRootAlpha(int param1Int) {
      this.mRootAlpha = param1Int;
    }
  }
  
  private static class VectorDrawableCompatState extends Drawable.ConstantState {
    boolean mAutoMirrored;
    
    boolean mCacheDirty;
    
    boolean mCachedAutoMirrored;
    
    Bitmap mCachedBitmap;
    
    int mCachedRootAlpha;
    
    int[] mCachedThemeAttrs;
    
    ColorStateList mCachedTint;
    
    PorterDuff.Mode mCachedTintMode;
    
    int mChangingConfigurations;
    
    Paint mTempPaint;
    
    ColorStateList mTint = null;
    
    PorterDuff.Mode mTintMode = VectorDrawableCompat.DEFAULT_TINT_MODE;
    
    VectorDrawableCompat.VPathRenderer mVPathRenderer;
    
    public VectorDrawableCompatState() {
      this.mVPathRenderer = new VectorDrawableCompat.VPathRenderer();
    }
    
    public VectorDrawableCompatState(VectorDrawableCompatState param1VectorDrawableCompatState) {
      if (param1VectorDrawableCompatState != null) {
        this.mChangingConfigurations = param1VectorDrawableCompatState.mChangingConfigurations;
        this.mVPathRenderer = new VectorDrawableCompat.VPathRenderer(param1VectorDrawableCompatState.mVPathRenderer);
        if (param1VectorDrawableCompatState.mVPathRenderer.mFillPaint != null)
          VectorDrawableCompat.VPathRenderer.access$002(this.mVPathRenderer, new Paint(param1VectorDrawableCompatState.mVPathRenderer.mFillPaint)); 
        if (param1VectorDrawableCompatState.mVPathRenderer.mStrokePaint != null)
          VectorDrawableCompat.VPathRenderer.access$102(this.mVPathRenderer, new Paint(param1VectorDrawableCompatState.mVPathRenderer.mStrokePaint)); 
        this.mTint = param1VectorDrawableCompatState.mTint;
        this.mTintMode = param1VectorDrawableCompatState.mTintMode;
        this.mAutoMirrored = param1VectorDrawableCompatState.mAutoMirrored;
      } 
    }
    
    public boolean canReuseBitmap(int param1Int1, int param1Int2) {
      return (param1Int1 == this.mCachedBitmap.getWidth() && param1Int2 == this.mCachedBitmap.getHeight());
    }
    
    public boolean canReuseCache() {
      return (!this.mCacheDirty && this.mCachedTint == this.mTint && this.mCachedTintMode == this.mTintMode && this.mCachedAutoMirrored == this.mAutoMirrored && this.mCachedRootAlpha == this.mVPathRenderer.getRootAlpha());
    }
    
    public void createCachedBitmapIfNeeded(int param1Int1, int param1Int2) {
      if (this.mCachedBitmap == null || !canReuseBitmap(param1Int1, param1Int2)) {
        this.mCachedBitmap = Bitmap.createBitmap(param1Int1, param1Int2, Bitmap.Config.ARGB_8888);
        this.mCacheDirty = true;
      } 
    }
    
    public void drawCachedBitmapWithRootAlpha(Canvas param1Canvas, ColorFilter param1ColorFilter, Rect param1Rect) {
      Paint paint = getPaint(param1ColorFilter);
      param1Canvas.drawBitmap(this.mCachedBitmap, null, param1Rect, paint);
    }
    
    public int getChangingConfigurations() {
      return this.mChangingConfigurations;
    }
    
    public Paint getPaint(ColorFilter param1ColorFilter) {
      if (!hasTranslucentRoot() && param1ColorFilter == null)
        return null; 
      if (this.mTempPaint == null) {
        Paint paint = new Paint();
        this.mTempPaint = paint;
        paint.setFilterBitmap(true);
      } 
      this.mTempPaint.setAlpha(this.mVPathRenderer.getRootAlpha());
      this.mTempPaint.setColorFilter(param1ColorFilter);
      return this.mTempPaint;
    }
    
    public boolean hasTranslucentRoot() {
      boolean bool;
      if (this.mVPathRenderer.getRootAlpha() < 255) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    public Drawable newDrawable() {
      return new VectorDrawableCompat(this);
    }
    
    public Drawable newDrawable(Resources param1Resources) {
      return new VectorDrawableCompat(this);
    }
    
    public void updateCacheStates() {
      this.mCachedTint = this.mTint;
      this.mCachedTintMode = this.mTintMode;
      this.mCachedRootAlpha = this.mVPathRenderer.getRootAlpha();
      this.mCachedAutoMirrored = this.mAutoMirrored;
      this.mCacheDirty = false;
    }
    
    public void updateCachedBitmap(int param1Int1, int param1Int2) {
      this.mCachedBitmap.eraseColor(0);
      Canvas canvas = new Canvas(this.mCachedBitmap);
      this.mVPathRenderer.draw(canvas, param1Int1, param1Int2, null);
    }
  }
  
  private static class VectorDrawableDelegateState extends Drawable.ConstantState {
    private final Drawable.ConstantState mDelegateState;
    
    public VectorDrawableDelegateState(Drawable.ConstantState param1ConstantState) {
      this.mDelegateState = param1ConstantState;
    }
    
    public boolean canApplyTheme() {
      return this.mDelegateState.canApplyTheme();
    }
    
    public int getChangingConfigurations() {
      return this.mDelegateState.getChangingConfigurations();
    }
    
    public Drawable newDrawable() {
      VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
      vectorDrawableCompat.mDelegateDrawable = this.mDelegateState.newDrawable();
      return vectorDrawableCompat;
    }
    
    public Drawable newDrawable(Resources param1Resources) {
      VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
      vectorDrawableCompat.mDelegateDrawable = this.mDelegateState.newDrawable(param1Resources);
      return vectorDrawableCompat;
    }
    
    public Drawable newDrawable(Resources param1Resources, Resources.Theme param1Theme) {
      VectorDrawableCompat vectorDrawableCompat = new VectorDrawableCompat();
      vectorDrawableCompat.mDelegateDrawable = this.mDelegateState.newDrawable(param1Resources, param1Theme);
      return vectorDrawableCompat;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\graphics\drawable\VectorDrawableCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */