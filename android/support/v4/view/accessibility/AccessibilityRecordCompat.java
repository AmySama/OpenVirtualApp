package android.support.v4.view.accessibility;

import android.os.Build;
import android.os.Parcelable;
import android.view.View;
import android.view.accessibility.AccessibilityRecord;
import java.util.List;

public class AccessibilityRecordCompat {
  private final AccessibilityRecord mRecord;
  
  @Deprecated
  public AccessibilityRecordCompat(Object paramObject) {
    this.mRecord = (AccessibilityRecord)paramObject;
  }
  
  public static int getMaxScrollX(AccessibilityRecord paramAccessibilityRecord) {
    return (Build.VERSION.SDK_INT >= 15) ? paramAccessibilityRecord.getMaxScrollX() : 0;
  }
  
  public static int getMaxScrollY(AccessibilityRecord paramAccessibilityRecord) {
    return (Build.VERSION.SDK_INT >= 15) ? paramAccessibilityRecord.getMaxScrollY() : 0;
  }
  
  @Deprecated
  public static AccessibilityRecordCompat obtain() {
    return new AccessibilityRecordCompat(AccessibilityRecord.obtain());
  }
  
  @Deprecated
  public static AccessibilityRecordCompat obtain(AccessibilityRecordCompat paramAccessibilityRecordCompat) {
    return new AccessibilityRecordCompat(AccessibilityRecord.obtain(paramAccessibilityRecordCompat.mRecord));
  }
  
  public static void setMaxScrollX(AccessibilityRecord paramAccessibilityRecord, int paramInt) {
    if (Build.VERSION.SDK_INT >= 15)
      paramAccessibilityRecord.setMaxScrollX(paramInt); 
  }
  
  public static void setMaxScrollY(AccessibilityRecord paramAccessibilityRecord, int paramInt) {
    if (Build.VERSION.SDK_INT >= 15)
      paramAccessibilityRecord.setMaxScrollY(paramInt); 
  }
  
  public static void setSource(AccessibilityRecord paramAccessibilityRecord, View paramView, int paramInt) {
    if (Build.VERSION.SDK_INT >= 16)
      paramAccessibilityRecord.setSource(paramView, paramInt); 
  }
  
  @Deprecated
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject == null)
      return false; 
    if (getClass() != paramObject.getClass())
      return false; 
    paramObject = paramObject;
    AccessibilityRecord accessibilityRecord = this.mRecord;
    if (accessibilityRecord == null) {
      if (((AccessibilityRecordCompat)paramObject).mRecord != null)
        return false; 
    } else if (!accessibilityRecord.equals(((AccessibilityRecordCompat)paramObject).mRecord)) {
      return false;
    } 
    return true;
  }
  
  @Deprecated
  public int getAddedCount() {
    return this.mRecord.getAddedCount();
  }
  
  @Deprecated
  public CharSequence getBeforeText() {
    return this.mRecord.getBeforeText();
  }
  
  @Deprecated
  public CharSequence getClassName() {
    return this.mRecord.getClassName();
  }
  
  @Deprecated
  public CharSequence getContentDescription() {
    return this.mRecord.getContentDescription();
  }
  
  @Deprecated
  public int getCurrentItemIndex() {
    return this.mRecord.getCurrentItemIndex();
  }
  
  @Deprecated
  public int getFromIndex() {
    return this.mRecord.getFromIndex();
  }
  
  @Deprecated
  public Object getImpl() {
    return this.mRecord;
  }
  
  @Deprecated
  public int getItemCount() {
    return this.mRecord.getItemCount();
  }
  
  @Deprecated
  public int getMaxScrollX() {
    return getMaxScrollX(this.mRecord);
  }
  
  @Deprecated
  public int getMaxScrollY() {
    return getMaxScrollY(this.mRecord);
  }
  
  @Deprecated
  public Parcelable getParcelableData() {
    return this.mRecord.getParcelableData();
  }
  
  @Deprecated
  public int getRemovedCount() {
    return this.mRecord.getRemovedCount();
  }
  
  @Deprecated
  public int getScrollX() {
    return this.mRecord.getScrollX();
  }
  
  @Deprecated
  public int getScrollY() {
    return this.mRecord.getScrollY();
  }
  
  @Deprecated
  public AccessibilityNodeInfoCompat getSource() {
    return AccessibilityNodeInfoCompat.wrapNonNullInstance(this.mRecord.getSource());
  }
  
  @Deprecated
  public List<CharSequence> getText() {
    return this.mRecord.getText();
  }
  
  @Deprecated
  public int getToIndex() {
    return this.mRecord.getToIndex();
  }
  
  @Deprecated
  public int getWindowId() {
    return this.mRecord.getWindowId();
  }
  
  @Deprecated
  public int hashCode() {
    int i;
    AccessibilityRecord accessibilityRecord = this.mRecord;
    if (accessibilityRecord == null) {
      i = 0;
    } else {
      i = accessibilityRecord.hashCode();
    } 
    return i;
  }
  
  @Deprecated
  public boolean isChecked() {
    return this.mRecord.isChecked();
  }
  
  @Deprecated
  public boolean isEnabled() {
    return this.mRecord.isEnabled();
  }
  
  @Deprecated
  public boolean isFullScreen() {
    return this.mRecord.isFullScreen();
  }
  
  @Deprecated
  public boolean isPassword() {
    return this.mRecord.isPassword();
  }
  
  @Deprecated
  public boolean isScrollable() {
    return this.mRecord.isScrollable();
  }
  
  @Deprecated
  public void recycle() {
    this.mRecord.recycle();
  }
  
  @Deprecated
  public void setAddedCount(int paramInt) {
    this.mRecord.setAddedCount(paramInt);
  }
  
  @Deprecated
  public void setBeforeText(CharSequence paramCharSequence) {
    this.mRecord.setBeforeText(paramCharSequence);
  }
  
  @Deprecated
  public void setChecked(boolean paramBoolean) {
    this.mRecord.setChecked(paramBoolean);
  }
  
  @Deprecated
  public void setClassName(CharSequence paramCharSequence) {
    this.mRecord.setClassName(paramCharSequence);
  }
  
  @Deprecated
  public void setContentDescription(CharSequence paramCharSequence) {
    this.mRecord.setContentDescription(paramCharSequence);
  }
  
  @Deprecated
  public void setCurrentItemIndex(int paramInt) {
    this.mRecord.setCurrentItemIndex(paramInt);
  }
  
  @Deprecated
  public void setEnabled(boolean paramBoolean) {
    this.mRecord.setEnabled(paramBoolean);
  }
  
  @Deprecated
  public void setFromIndex(int paramInt) {
    this.mRecord.setFromIndex(paramInt);
  }
  
  @Deprecated
  public void setFullScreen(boolean paramBoolean) {
    this.mRecord.setFullScreen(paramBoolean);
  }
  
  @Deprecated
  public void setItemCount(int paramInt) {
    this.mRecord.setItemCount(paramInt);
  }
  
  @Deprecated
  public void setMaxScrollX(int paramInt) {
    setMaxScrollX(this.mRecord, paramInt);
  }
  
  @Deprecated
  public void setMaxScrollY(int paramInt) {
    setMaxScrollY(this.mRecord, paramInt);
  }
  
  @Deprecated
  public void setParcelableData(Parcelable paramParcelable) {
    this.mRecord.setParcelableData(paramParcelable);
  }
  
  @Deprecated
  public void setPassword(boolean paramBoolean) {
    this.mRecord.setPassword(paramBoolean);
  }
  
  @Deprecated
  public void setRemovedCount(int paramInt) {
    this.mRecord.setRemovedCount(paramInt);
  }
  
  @Deprecated
  public void setScrollX(int paramInt) {
    this.mRecord.setScrollX(paramInt);
  }
  
  @Deprecated
  public void setScrollY(int paramInt) {
    this.mRecord.setScrollY(paramInt);
  }
  
  @Deprecated
  public void setScrollable(boolean paramBoolean) {
    this.mRecord.setScrollable(paramBoolean);
  }
  
  @Deprecated
  public void setSource(View paramView) {
    this.mRecord.setSource(paramView);
  }
  
  @Deprecated
  public void setSource(View paramView, int paramInt) {
    setSource(this.mRecord, paramView, paramInt);
  }
  
  @Deprecated
  public void setToIndex(int paramInt) {
    this.mRecord.setToIndex(paramInt);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\view\accessibility\AccessibilityRecordCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */