package android.support.v7.widget;

import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParserException;

class ResourcesWrapper extends Resources {
  private final Resources mResources;
  
  public ResourcesWrapper(Resources paramResources) {
    super(paramResources.getAssets(), paramResources.getDisplayMetrics(), paramResources.getConfiguration());
    this.mResources = paramResources;
  }
  
  public XmlResourceParser getAnimation(int paramInt) throws Resources.NotFoundException {
    return this.mResources.getAnimation(paramInt);
  }
  
  public boolean getBoolean(int paramInt) throws Resources.NotFoundException {
    return this.mResources.getBoolean(paramInt);
  }
  
  public int getColor(int paramInt) throws Resources.NotFoundException {
    return this.mResources.getColor(paramInt);
  }
  
  public ColorStateList getColorStateList(int paramInt) throws Resources.NotFoundException {
    return this.mResources.getColorStateList(paramInt);
  }
  
  public Configuration getConfiguration() {
    return this.mResources.getConfiguration();
  }
  
  public float getDimension(int paramInt) throws Resources.NotFoundException {
    return this.mResources.getDimension(paramInt);
  }
  
  public int getDimensionPixelOffset(int paramInt) throws Resources.NotFoundException {
    return this.mResources.getDimensionPixelOffset(paramInt);
  }
  
  public int getDimensionPixelSize(int paramInt) throws Resources.NotFoundException {
    return this.mResources.getDimensionPixelSize(paramInt);
  }
  
  public DisplayMetrics getDisplayMetrics() {
    return this.mResources.getDisplayMetrics();
  }
  
  public Drawable getDrawable(int paramInt) throws Resources.NotFoundException {
    return this.mResources.getDrawable(paramInt);
  }
  
  public Drawable getDrawable(int paramInt, Resources.Theme paramTheme) throws Resources.NotFoundException {
    return this.mResources.getDrawable(paramInt, paramTheme);
  }
  
  public Drawable getDrawableForDensity(int paramInt1, int paramInt2) throws Resources.NotFoundException {
    return this.mResources.getDrawableForDensity(paramInt1, paramInt2);
  }
  
  public Drawable getDrawableForDensity(int paramInt1, int paramInt2, Resources.Theme paramTheme) {
    return this.mResources.getDrawableForDensity(paramInt1, paramInt2, paramTheme);
  }
  
  public float getFraction(int paramInt1, int paramInt2, int paramInt3) {
    return this.mResources.getFraction(paramInt1, paramInt2, paramInt3);
  }
  
  public int getIdentifier(String paramString1, String paramString2, String paramString3) {
    return this.mResources.getIdentifier(paramString1, paramString2, paramString3);
  }
  
  public int[] getIntArray(int paramInt) throws Resources.NotFoundException {
    return this.mResources.getIntArray(paramInt);
  }
  
  public int getInteger(int paramInt) throws Resources.NotFoundException {
    return this.mResources.getInteger(paramInt);
  }
  
  public XmlResourceParser getLayout(int paramInt) throws Resources.NotFoundException {
    return this.mResources.getLayout(paramInt);
  }
  
  public Movie getMovie(int paramInt) throws Resources.NotFoundException {
    return this.mResources.getMovie(paramInt);
  }
  
  public String getQuantityString(int paramInt1, int paramInt2) throws Resources.NotFoundException {
    return this.mResources.getQuantityString(paramInt1, paramInt2);
  }
  
  public String getQuantityString(int paramInt1, int paramInt2, Object... paramVarArgs) throws Resources.NotFoundException {
    return this.mResources.getQuantityString(paramInt1, paramInt2, paramVarArgs);
  }
  
  public CharSequence getQuantityText(int paramInt1, int paramInt2) throws Resources.NotFoundException {
    return this.mResources.getQuantityText(paramInt1, paramInt2);
  }
  
  public String getResourceEntryName(int paramInt) throws Resources.NotFoundException {
    return this.mResources.getResourceEntryName(paramInt);
  }
  
  public String getResourceName(int paramInt) throws Resources.NotFoundException {
    return this.mResources.getResourceName(paramInt);
  }
  
  public String getResourcePackageName(int paramInt) throws Resources.NotFoundException {
    return this.mResources.getResourcePackageName(paramInt);
  }
  
  public String getResourceTypeName(int paramInt) throws Resources.NotFoundException {
    return this.mResources.getResourceTypeName(paramInt);
  }
  
  public String getString(int paramInt) throws Resources.NotFoundException {
    return this.mResources.getString(paramInt);
  }
  
  public String getString(int paramInt, Object... paramVarArgs) throws Resources.NotFoundException {
    return this.mResources.getString(paramInt, paramVarArgs);
  }
  
  public String[] getStringArray(int paramInt) throws Resources.NotFoundException {
    return this.mResources.getStringArray(paramInt);
  }
  
  public CharSequence getText(int paramInt) throws Resources.NotFoundException {
    return this.mResources.getText(paramInt);
  }
  
  public CharSequence getText(int paramInt, CharSequence paramCharSequence) {
    return this.mResources.getText(paramInt, paramCharSequence);
  }
  
  public CharSequence[] getTextArray(int paramInt) throws Resources.NotFoundException {
    return this.mResources.getTextArray(paramInt);
  }
  
  public void getValue(int paramInt, TypedValue paramTypedValue, boolean paramBoolean) throws Resources.NotFoundException {
    this.mResources.getValue(paramInt, paramTypedValue, paramBoolean);
  }
  
  public void getValue(String paramString, TypedValue paramTypedValue, boolean paramBoolean) throws Resources.NotFoundException {
    this.mResources.getValue(paramString, paramTypedValue, paramBoolean);
  }
  
  public void getValueForDensity(int paramInt1, int paramInt2, TypedValue paramTypedValue, boolean paramBoolean) throws Resources.NotFoundException {
    this.mResources.getValueForDensity(paramInt1, paramInt2, paramTypedValue, paramBoolean);
  }
  
  public XmlResourceParser getXml(int paramInt) throws Resources.NotFoundException {
    return this.mResources.getXml(paramInt);
  }
  
  public TypedArray obtainAttributes(AttributeSet paramAttributeSet, int[] paramArrayOfint) {
    return this.mResources.obtainAttributes(paramAttributeSet, paramArrayOfint);
  }
  
  public TypedArray obtainTypedArray(int paramInt) throws Resources.NotFoundException {
    return this.mResources.obtainTypedArray(paramInt);
  }
  
  public InputStream openRawResource(int paramInt) throws Resources.NotFoundException {
    return this.mResources.openRawResource(paramInt);
  }
  
  public InputStream openRawResource(int paramInt, TypedValue paramTypedValue) throws Resources.NotFoundException {
    return this.mResources.openRawResource(paramInt, paramTypedValue);
  }
  
  public AssetFileDescriptor openRawResourceFd(int paramInt) throws Resources.NotFoundException {
    return this.mResources.openRawResourceFd(paramInt);
  }
  
  public void parseBundleExtra(String paramString, AttributeSet paramAttributeSet, Bundle paramBundle) throws XmlPullParserException {
    this.mResources.parseBundleExtra(paramString, paramAttributeSet, paramBundle);
  }
  
  public void parseBundleExtras(XmlResourceParser paramXmlResourceParser, Bundle paramBundle) throws XmlPullParserException, IOException {
    this.mResources.parseBundleExtras(paramXmlResourceParser, paramBundle);
  }
  
  public void updateConfiguration(Configuration paramConfiguration, DisplayMetrics paramDisplayMetrics) {
    super.updateConfiguration(paramConfiguration, paramDisplayMetrics);
    Resources resources = this.mResources;
    if (resources != null)
      resources.updateConfiguration(paramConfiguration, paramDisplayMetrics); 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\widget\ResourcesWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */