package android.support.v4.view.accessibility;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class AccessibilityNodeInfoCompat {
  public static final int ACTION_ACCESSIBILITY_FOCUS = 64;
  
  public static final String ACTION_ARGUMENT_COLUMN_INT = "android.view.accessibility.action.ARGUMENT_COLUMN_INT";
  
  public static final String ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN = "ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN";
  
  public static final String ACTION_ARGUMENT_HTML_ELEMENT_STRING = "ACTION_ARGUMENT_HTML_ELEMENT_STRING";
  
  public static final String ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT = "ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT";
  
  public static final String ACTION_ARGUMENT_PROGRESS_VALUE = "android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE";
  
  public static final String ACTION_ARGUMENT_ROW_INT = "android.view.accessibility.action.ARGUMENT_ROW_INT";
  
  public static final String ACTION_ARGUMENT_SELECTION_END_INT = "ACTION_ARGUMENT_SELECTION_END_INT";
  
  public static final String ACTION_ARGUMENT_SELECTION_START_INT = "ACTION_ARGUMENT_SELECTION_START_INT";
  
  public static final String ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE = "ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE";
  
  public static final int ACTION_CLEAR_ACCESSIBILITY_FOCUS = 128;
  
  public static final int ACTION_CLEAR_FOCUS = 2;
  
  public static final int ACTION_CLEAR_SELECTION = 8;
  
  public static final int ACTION_CLICK = 16;
  
  public static final int ACTION_COLLAPSE = 524288;
  
  public static final int ACTION_COPY = 16384;
  
  public static final int ACTION_CUT = 65536;
  
  public static final int ACTION_DISMISS = 1048576;
  
  public static final int ACTION_EXPAND = 262144;
  
  public static final int ACTION_FOCUS = 1;
  
  public static final int ACTION_LONG_CLICK = 32;
  
  public static final int ACTION_NEXT_AT_MOVEMENT_GRANULARITY = 256;
  
  public static final int ACTION_NEXT_HTML_ELEMENT = 1024;
  
  public static final int ACTION_PASTE = 32768;
  
  public static final int ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = 512;
  
  public static final int ACTION_PREVIOUS_HTML_ELEMENT = 2048;
  
  public static final int ACTION_SCROLL_BACKWARD = 8192;
  
  public static final int ACTION_SCROLL_FORWARD = 4096;
  
  public static final int ACTION_SELECT = 4;
  
  public static final int ACTION_SET_SELECTION = 131072;
  
  public static final int ACTION_SET_TEXT = 2097152;
  
  public static final int FOCUS_ACCESSIBILITY = 2;
  
  public static final int FOCUS_INPUT = 1;
  
  public static final int MOVEMENT_GRANULARITY_CHARACTER = 1;
  
  public static final int MOVEMENT_GRANULARITY_LINE = 4;
  
  public static final int MOVEMENT_GRANULARITY_PAGE = 16;
  
  public static final int MOVEMENT_GRANULARITY_PARAGRAPH = 8;
  
  public static final int MOVEMENT_GRANULARITY_WORD = 2;
  
  private static final String ROLE_DESCRIPTION_KEY = "AccessibilityNodeInfo.roleDescription";
  
  private final AccessibilityNodeInfo mInfo;
  
  public int mParentVirtualDescendantId = -1;
  
  private AccessibilityNodeInfoCompat(AccessibilityNodeInfo paramAccessibilityNodeInfo) {
    this.mInfo = paramAccessibilityNodeInfo;
  }
  
  @Deprecated
  public AccessibilityNodeInfoCompat(Object paramObject) {
    this.mInfo = (AccessibilityNodeInfo)paramObject;
  }
  
  private static String getActionSymbolicName(int paramInt) {
    if (paramInt != 1) {
      if (paramInt != 2) {
        switch (paramInt) {
          default:
            return "ACTION_UNKNOWN";
          case 131072:
            return "ACTION_SET_SELECTION";
          case 65536:
            return "ACTION_CUT";
          case 32768:
            return "ACTION_PASTE";
          case 16384:
            return "ACTION_COPY";
          case 8192:
            return "ACTION_SCROLL_BACKWARD";
          case 4096:
            return "ACTION_SCROLL_FORWARD";
          case 2048:
            return "ACTION_PREVIOUS_HTML_ELEMENT";
          case 1024:
            return "ACTION_NEXT_HTML_ELEMENT";
          case 512:
            return "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY";
          case 256:
            return "ACTION_NEXT_AT_MOVEMENT_GRANULARITY";
          case 128:
            return "ACTION_CLEAR_ACCESSIBILITY_FOCUS";
          case 64:
            return "ACTION_ACCESSIBILITY_FOCUS";
          case 32:
            return "ACTION_LONG_CLICK";
          case 16:
            return "ACTION_CLICK";
          case 8:
            return "ACTION_CLEAR_SELECTION";
          case 4:
            break;
        } 
        return "ACTION_SELECT";
      } 
      return "ACTION_CLEAR_FOCUS";
    } 
    return "ACTION_FOCUS";
  }
  
  public static AccessibilityNodeInfoCompat obtain() {
    return wrap(AccessibilityNodeInfo.obtain());
  }
  
  public static AccessibilityNodeInfoCompat obtain(AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat) {
    return wrap(AccessibilityNodeInfo.obtain(paramAccessibilityNodeInfoCompat.mInfo));
  }
  
  public static AccessibilityNodeInfoCompat obtain(View paramView) {
    return wrap(AccessibilityNodeInfo.obtain(paramView));
  }
  
  public static AccessibilityNodeInfoCompat obtain(View paramView, int paramInt) {
    return (Build.VERSION.SDK_INT >= 16) ? wrapNonNullInstance(AccessibilityNodeInfo.obtain(paramView, paramInt)) : null;
  }
  
  public static AccessibilityNodeInfoCompat wrap(AccessibilityNodeInfo paramAccessibilityNodeInfo) {
    return new AccessibilityNodeInfoCompat(paramAccessibilityNodeInfo);
  }
  
  static AccessibilityNodeInfoCompat wrapNonNullInstance(Object paramObject) {
    return (paramObject != null) ? new AccessibilityNodeInfoCompat(paramObject) : null;
  }
  
  public void addAction(int paramInt) {
    this.mInfo.addAction(paramInt);
  }
  
  public void addAction(AccessibilityActionCompat paramAccessibilityActionCompat) {
    if (Build.VERSION.SDK_INT >= 21)
      this.mInfo.addAction((AccessibilityNodeInfo.AccessibilityAction)paramAccessibilityActionCompat.mAction); 
  }
  
  public void addChild(View paramView) {
    this.mInfo.addChild(paramView);
  }
  
  public void addChild(View paramView, int paramInt) {
    if (Build.VERSION.SDK_INT >= 16)
      this.mInfo.addChild(paramView, paramInt); 
  }
  
  public boolean canOpenPopup() {
    return (Build.VERSION.SDK_INT >= 19) ? this.mInfo.canOpenPopup() : false;
  }
  
  public boolean equals(Object paramObject) {
    if (this == paramObject)
      return true; 
    if (paramObject == null)
      return false; 
    if (getClass() != paramObject.getClass())
      return false; 
    paramObject = paramObject;
    AccessibilityNodeInfo accessibilityNodeInfo = this.mInfo;
    if (accessibilityNodeInfo == null) {
      if (((AccessibilityNodeInfoCompat)paramObject).mInfo != null)
        return false; 
    } else if (!accessibilityNodeInfo.equals(((AccessibilityNodeInfoCompat)paramObject).mInfo)) {
      return false;
    } 
    return true;
  }
  
  public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText(String paramString) {
    ArrayList<AccessibilityNodeInfoCompat> arrayList = new ArrayList();
    List<AccessibilityNodeInfo> list = this.mInfo.findAccessibilityNodeInfosByText(paramString);
    int i = list.size();
    for (byte b = 0; b < i; b++)
      arrayList.add(wrap(list.get(b))); 
    return arrayList;
  }
  
  public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByViewId(String paramString) {
    if (Build.VERSION.SDK_INT >= 18) {
      List list = this.mInfo.findAccessibilityNodeInfosByViewId(paramString);
      ArrayList<AccessibilityNodeInfoCompat> arrayList = new ArrayList();
      Iterator<AccessibilityNodeInfo> iterator = list.iterator();
      while (iterator.hasNext())
        arrayList.add(wrap(iterator.next())); 
      return arrayList;
    } 
    return Collections.emptyList();
  }
  
  public AccessibilityNodeInfoCompat findFocus(int paramInt) {
    return (Build.VERSION.SDK_INT >= 16) ? wrapNonNullInstance(this.mInfo.findFocus(paramInt)) : null;
  }
  
  public AccessibilityNodeInfoCompat focusSearch(int paramInt) {
    return (Build.VERSION.SDK_INT >= 16) ? wrapNonNullInstance(this.mInfo.focusSearch(paramInt)) : null;
  }
  
  public List<AccessibilityActionCompat> getActionList() {
    List list;
    if (Build.VERSION.SDK_INT >= 21) {
      list = this.mInfo.getActionList();
    } else {
      list = null;
    } 
    if (list != null) {
      ArrayList<AccessibilityActionCompat> arrayList = new ArrayList();
      int i = list.size();
      for (byte b = 0; b < i; b++)
        arrayList.add(new AccessibilityActionCompat(list.get(b))); 
      return arrayList;
    } 
    return Collections.emptyList();
  }
  
  public int getActions() {
    return this.mInfo.getActions();
  }
  
  public void getBoundsInParent(Rect paramRect) {
    this.mInfo.getBoundsInParent(paramRect);
  }
  
  public void getBoundsInScreen(Rect paramRect) {
    this.mInfo.getBoundsInScreen(paramRect);
  }
  
  public AccessibilityNodeInfoCompat getChild(int paramInt) {
    return wrapNonNullInstance(this.mInfo.getChild(paramInt));
  }
  
  public int getChildCount() {
    return this.mInfo.getChildCount();
  }
  
  public CharSequence getClassName() {
    return this.mInfo.getClassName();
  }
  
  public CollectionInfoCompat getCollectionInfo() {
    if (Build.VERSION.SDK_INT >= 19) {
      AccessibilityNodeInfo.CollectionInfo collectionInfo = this.mInfo.getCollectionInfo();
      if (collectionInfo != null)
        return new CollectionInfoCompat(collectionInfo); 
    } 
    return null;
  }
  
  public CollectionItemInfoCompat getCollectionItemInfo() {
    if (Build.VERSION.SDK_INT >= 19) {
      AccessibilityNodeInfo.CollectionItemInfo collectionItemInfo = this.mInfo.getCollectionItemInfo();
      if (collectionItemInfo != null)
        return new CollectionItemInfoCompat(collectionItemInfo); 
    } 
    return null;
  }
  
  public CharSequence getContentDescription() {
    return this.mInfo.getContentDescription();
  }
  
  public int getDrawingOrder() {
    return (Build.VERSION.SDK_INT >= 24) ? this.mInfo.getDrawingOrder() : 0;
  }
  
  public CharSequence getError() {
    return (Build.VERSION.SDK_INT >= 21) ? this.mInfo.getError() : null;
  }
  
  public Bundle getExtras() {
    return (Build.VERSION.SDK_INT >= 19) ? this.mInfo.getExtras() : new Bundle();
  }
  
  @Deprecated
  public Object getInfo() {
    return this.mInfo;
  }
  
  public int getInputType() {
    return (Build.VERSION.SDK_INT >= 19) ? this.mInfo.getInputType() : 0;
  }
  
  public AccessibilityNodeInfoCompat getLabelFor() {
    return (Build.VERSION.SDK_INT >= 17) ? wrapNonNullInstance(this.mInfo.getLabelFor()) : null;
  }
  
  public AccessibilityNodeInfoCompat getLabeledBy() {
    return (Build.VERSION.SDK_INT >= 17) ? wrapNonNullInstance(this.mInfo.getLabeledBy()) : null;
  }
  
  public int getLiveRegion() {
    return (Build.VERSION.SDK_INT >= 19) ? this.mInfo.getLiveRegion() : 0;
  }
  
  public int getMaxTextLength() {
    return (Build.VERSION.SDK_INT >= 21) ? this.mInfo.getMaxTextLength() : -1;
  }
  
  public int getMovementGranularities() {
    return (Build.VERSION.SDK_INT >= 16) ? this.mInfo.getMovementGranularities() : 0;
  }
  
  public CharSequence getPackageName() {
    return this.mInfo.getPackageName();
  }
  
  public AccessibilityNodeInfoCompat getParent() {
    return wrapNonNullInstance(this.mInfo.getParent());
  }
  
  public RangeInfoCompat getRangeInfo() {
    if (Build.VERSION.SDK_INT >= 19) {
      AccessibilityNodeInfo.RangeInfo rangeInfo = this.mInfo.getRangeInfo();
      if (rangeInfo != null)
        return new RangeInfoCompat(rangeInfo); 
    } 
    return null;
  }
  
  public CharSequence getRoleDescription() {
    return (Build.VERSION.SDK_INT >= 19) ? this.mInfo.getExtras().getCharSequence("AccessibilityNodeInfo.roleDescription") : null;
  }
  
  public CharSequence getText() {
    return this.mInfo.getText();
  }
  
  public int getTextSelectionEnd() {
    return (Build.VERSION.SDK_INT >= 18) ? this.mInfo.getTextSelectionEnd() : -1;
  }
  
  public int getTextSelectionStart() {
    return (Build.VERSION.SDK_INT >= 18) ? this.mInfo.getTextSelectionStart() : -1;
  }
  
  public AccessibilityNodeInfoCompat getTraversalAfter() {
    return (Build.VERSION.SDK_INT >= 22) ? wrapNonNullInstance(this.mInfo.getTraversalAfter()) : null;
  }
  
  public AccessibilityNodeInfoCompat getTraversalBefore() {
    return (Build.VERSION.SDK_INT >= 22) ? wrapNonNullInstance(this.mInfo.getTraversalBefore()) : null;
  }
  
  public String getViewIdResourceName() {
    return (Build.VERSION.SDK_INT >= 18) ? this.mInfo.getViewIdResourceName() : null;
  }
  
  public AccessibilityWindowInfoCompat getWindow() {
    return (Build.VERSION.SDK_INT >= 21) ? AccessibilityWindowInfoCompat.wrapNonNullInstance(this.mInfo.getWindow()) : null;
  }
  
  public int getWindowId() {
    return this.mInfo.getWindowId();
  }
  
  public int hashCode() {
    int i;
    AccessibilityNodeInfo accessibilityNodeInfo = this.mInfo;
    if (accessibilityNodeInfo == null) {
      i = 0;
    } else {
      i = accessibilityNodeInfo.hashCode();
    } 
    return i;
  }
  
  public boolean isAccessibilityFocused() {
    return (Build.VERSION.SDK_INT >= 16) ? this.mInfo.isAccessibilityFocused() : false;
  }
  
  public boolean isCheckable() {
    return this.mInfo.isCheckable();
  }
  
  public boolean isChecked() {
    return this.mInfo.isChecked();
  }
  
  public boolean isClickable() {
    return this.mInfo.isClickable();
  }
  
  public boolean isContentInvalid() {
    return (Build.VERSION.SDK_INT >= 19) ? this.mInfo.isContentInvalid() : false;
  }
  
  public boolean isContextClickable() {
    return (Build.VERSION.SDK_INT >= 23) ? this.mInfo.isContextClickable() : false;
  }
  
  public boolean isDismissable() {
    return (Build.VERSION.SDK_INT >= 19) ? this.mInfo.isDismissable() : false;
  }
  
  public boolean isEditable() {
    return (Build.VERSION.SDK_INT >= 18) ? this.mInfo.isEditable() : false;
  }
  
  public boolean isEnabled() {
    return this.mInfo.isEnabled();
  }
  
  public boolean isFocusable() {
    return this.mInfo.isFocusable();
  }
  
  public boolean isFocused() {
    return this.mInfo.isFocused();
  }
  
  public boolean isImportantForAccessibility() {
    return (Build.VERSION.SDK_INT >= 24) ? this.mInfo.isImportantForAccessibility() : true;
  }
  
  public boolean isLongClickable() {
    return this.mInfo.isLongClickable();
  }
  
  public boolean isMultiLine() {
    return (Build.VERSION.SDK_INT >= 19) ? this.mInfo.isMultiLine() : false;
  }
  
  public boolean isPassword() {
    return this.mInfo.isPassword();
  }
  
  public boolean isScrollable() {
    return this.mInfo.isScrollable();
  }
  
  public boolean isSelected() {
    return this.mInfo.isSelected();
  }
  
  public boolean isVisibleToUser() {
    return (Build.VERSION.SDK_INT >= 16) ? this.mInfo.isVisibleToUser() : false;
  }
  
  public boolean performAction(int paramInt) {
    return this.mInfo.performAction(paramInt);
  }
  
  public boolean performAction(int paramInt, Bundle paramBundle) {
    return (Build.VERSION.SDK_INT >= 16) ? this.mInfo.performAction(paramInt, paramBundle) : false;
  }
  
  public void recycle() {
    this.mInfo.recycle();
  }
  
  public boolean refresh() {
    return (Build.VERSION.SDK_INT >= 18) ? this.mInfo.refresh() : false;
  }
  
  public boolean removeAction(AccessibilityActionCompat paramAccessibilityActionCompat) {
    return (Build.VERSION.SDK_INT >= 21) ? this.mInfo.removeAction((AccessibilityNodeInfo.AccessibilityAction)paramAccessibilityActionCompat.mAction) : false;
  }
  
  public boolean removeChild(View paramView) {
    return (Build.VERSION.SDK_INT >= 21) ? this.mInfo.removeChild(paramView) : false;
  }
  
  public boolean removeChild(View paramView, int paramInt) {
    return (Build.VERSION.SDK_INT >= 21) ? this.mInfo.removeChild(paramView, paramInt) : false;
  }
  
  public void setAccessibilityFocused(boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 16)
      this.mInfo.setAccessibilityFocused(paramBoolean); 
  }
  
  public void setBoundsInParent(Rect paramRect) {
    this.mInfo.setBoundsInParent(paramRect);
  }
  
  public void setBoundsInScreen(Rect paramRect) {
    this.mInfo.setBoundsInScreen(paramRect);
  }
  
  public void setCanOpenPopup(boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 19)
      this.mInfo.setCanOpenPopup(paramBoolean); 
  }
  
  public void setCheckable(boolean paramBoolean) {
    this.mInfo.setCheckable(paramBoolean);
  }
  
  public void setChecked(boolean paramBoolean) {
    this.mInfo.setChecked(paramBoolean);
  }
  
  public void setClassName(CharSequence paramCharSequence) {
    this.mInfo.setClassName(paramCharSequence);
  }
  
  public void setClickable(boolean paramBoolean) {
    this.mInfo.setClickable(paramBoolean);
  }
  
  public void setCollectionInfo(Object paramObject) {
    if (Build.VERSION.SDK_INT >= 19)
      this.mInfo.setCollectionInfo((AccessibilityNodeInfo.CollectionInfo)((CollectionInfoCompat)paramObject).mInfo); 
  }
  
  public void setCollectionItemInfo(Object paramObject) {
    if (Build.VERSION.SDK_INT >= 19)
      this.mInfo.setCollectionItemInfo((AccessibilityNodeInfo.CollectionItemInfo)((CollectionItemInfoCompat)paramObject).mInfo); 
  }
  
  public void setContentDescription(CharSequence paramCharSequence) {
    this.mInfo.setContentDescription(paramCharSequence);
  }
  
  public void setContentInvalid(boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 19)
      this.mInfo.setContentInvalid(paramBoolean); 
  }
  
  public void setContextClickable(boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 23)
      this.mInfo.setContextClickable(paramBoolean); 
  }
  
  public void setDismissable(boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 19)
      this.mInfo.setDismissable(paramBoolean); 
  }
  
  public void setDrawingOrder(int paramInt) {
    if (Build.VERSION.SDK_INT >= 24)
      this.mInfo.setDrawingOrder(paramInt); 
  }
  
  public void setEditable(boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 18)
      this.mInfo.setEditable(paramBoolean); 
  }
  
  public void setEnabled(boolean paramBoolean) {
    this.mInfo.setEnabled(paramBoolean);
  }
  
  public void setError(CharSequence paramCharSequence) {
    if (Build.VERSION.SDK_INT >= 21)
      this.mInfo.setError(paramCharSequence); 
  }
  
  public void setFocusable(boolean paramBoolean) {
    this.mInfo.setFocusable(paramBoolean);
  }
  
  public void setFocused(boolean paramBoolean) {
    this.mInfo.setFocused(paramBoolean);
  }
  
  public void setImportantForAccessibility(boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 24)
      this.mInfo.setImportantForAccessibility(paramBoolean); 
  }
  
  public void setInputType(int paramInt) {
    if (Build.VERSION.SDK_INT >= 19)
      this.mInfo.setInputType(paramInt); 
  }
  
  public void setLabelFor(View paramView) {
    if (Build.VERSION.SDK_INT >= 17)
      this.mInfo.setLabelFor(paramView); 
  }
  
  public void setLabelFor(View paramView, int paramInt) {
    if (Build.VERSION.SDK_INT >= 17)
      this.mInfo.setLabelFor(paramView, paramInt); 
  }
  
  public void setLabeledBy(View paramView) {
    if (Build.VERSION.SDK_INT >= 17)
      this.mInfo.setLabeledBy(paramView); 
  }
  
  public void setLabeledBy(View paramView, int paramInt) {
    if (Build.VERSION.SDK_INT >= 17)
      this.mInfo.setLabeledBy(paramView, paramInt); 
  }
  
  public void setLiveRegion(int paramInt) {
    if (Build.VERSION.SDK_INT >= 19)
      this.mInfo.setLiveRegion(paramInt); 
  }
  
  public void setLongClickable(boolean paramBoolean) {
    this.mInfo.setLongClickable(paramBoolean);
  }
  
  public void setMaxTextLength(int paramInt) {
    if (Build.VERSION.SDK_INT >= 21)
      this.mInfo.setMaxTextLength(paramInt); 
  }
  
  public void setMovementGranularities(int paramInt) {
    if (Build.VERSION.SDK_INT >= 16)
      this.mInfo.setMovementGranularities(paramInt); 
  }
  
  public void setMultiLine(boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 19)
      this.mInfo.setMultiLine(paramBoolean); 
  }
  
  public void setPackageName(CharSequence paramCharSequence) {
    this.mInfo.setPackageName(paramCharSequence);
  }
  
  public void setParent(View paramView) {
    this.mInfo.setParent(paramView);
  }
  
  public void setParent(View paramView, int paramInt) {
    this.mParentVirtualDescendantId = paramInt;
    if (Build.VERSION.SDK_INT >= 16)
      this.mInfo.setParent(paramView, paramInt); 
  }
  
  public void setPassword(boolean paramBoolean) {
    this.mInfo.setPassword(paramBoolean);
  }
  
  public void setRangeInfo(RangeInfoCompat paramRangeInfoCompat) {
    if (Build.VERSION.SDK_INT >= 19)
      this.mInfo.setRangeInfo((AccessibilityNodeInfo.RangeInfo)paramRangeInfoCompat.mInfo); 
  }
  
  public void setRoleDescription(CharSequence paramCharSequence) {
    if (Build.VERSION.SDK_INT >= 19)
      this.mInfo.getExtras().putCharSequence("AccessibilityNodeInfo.roleDescription", paramCharSequence); 
  }
  
  public void setScrollable(boolean paramBoolean) {
    this.mInfo.setScrollable(paramBoolean);
  }
  
  public void setSelected(boolean paramBoolean) {
    this.mInfo.setSelected(paramBoolean);
  }
  
  public void setSource(View paramView) {
    this.mInfo.setSource(paramView);
  }
  
  public void setSource(View paramView, int paramInt) {
    if (Build.VERSION.SDK_INT >= 16)
      this.mInfo.setSource(paramView, paramInt); 
  }
  
  public void setText(CharSequence paramCharSequence) {
    this.mInfo.setText(paramCharSequence);
  }
  
  public void setTextSelection(int paramInt1, int paramInt2) {
    if (Build.VERSION.SDK_INT >= 18)
      this.mInfo.setTextSelection(paramInt1, paramInt2); 
  }
  
  public void setTraversalAfter(View paramView) {
    if (Build.VERSION.SDK_INT >= 22)
      this.mInfo.setTraversalAfter(paramView); 
  }
  
  public void setTraversalAfter(View paramView, int paramInt) {
    if (Build.VERSION.SDK_INT >= 22)
      this.mInfo.setTraversalAfter(paramView, paramInt); 
  }
  
  public void setTraversalBefore(View paramView) {
    if (Build.VERSION.SDK_INT >= 22)
      this.mInfo.setTraversalBefore(paramView); 
  }
  
  public void setTraversalBefore(View paramView, int paramInt) {
    if (Build.VERSION.SDK_INT >= 22)
      this.mInfo.setTraversalBefore(paramView, paramInt); 
  }
  
  public void setViewIdResourceName(String paramString) {
    if (Build.VERSION.SDK_INT >= 18)
      this.mInfo.setViewIdResourceName(paramString); 
  }
  
  public void setVisibleToUser(boolean paramBoolean) {
    if (Build.VERSION.SDK_INT >= 16)
      this.mInfo.setVisibleToUser(paramBoolean); 
  }
  
  public String toString() {
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append(super.toString());
    Rect rect = new Rect();
    getBoundsInParent(rect);
    StringBuilder stringBuilder3 = new StringBuilder();
    stringBuilder3.append("; boundsInParent: ");
    stringBuilder3.append(rect);
    stringBuilder1.append(stringBuilder3.toString());
    getBoundsInScreen(rect);
    stringBuilder3 = new StringBuilder();
    stringBuilder3.append("; boundsInScreen: ");
    stringBuilder3.append(rect);
    stringBuilder1.append(stringBuilder3.toString());
    stringBuilder1.append("; packageName: ");
    stringBuilder1.append(getPackageName());
    stringBuilder1.append("; className: ");
    stringBuilder1.append(getClassName());
    stringBuilder1.append("; text: ");
    stringBuilder1.append(getText());
    stringBuilder1.append("; contentDescription: ");
    stringBuilder1.append(getContentDescription());
    stringBuilder1.append("; viewId: ");
    stringBuilder1.append(getViewIdResourceName());
    stringBuilder1.append("; checkable: ");
    stringBuilder1.append(isCheckable());
    stringBuilder1.append("; checked: ");
    stringBuilder1.append(isChecked());
    stringBuilder1.append("; focusable: ");
    stringBuilder1.append(isFocusable());
    stringBuilder1.append("; focused: ");
    stringBuilder1.append(isFocused());
    stringBuilder1.append("; selected: ");
    stringBuilder1.append(isSelected());
    stringBuilder1.append("; clickable: ");
    stringBuilder1.append(isClickable());
    stringBuilder1.append("; longClickable: ");
    stringBuilder1.append(isLongClickable());
    stringBuilder1.append("; enabled: ");
    stringBuilder1.append(isEnabled());
    stringBuilder1.append("; password: ");
    stringBuilder1.append(isPassword());
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("; scrollable: ");
    stringBuilder2.append(isScrollable());
    stringBuilder1.append(stringBuilder2.toString());
    stringBuilder1.append("; [");
    int i = getActions();
    while (i != 0) {
      int j = 1 << Integer.numberOfTrailingZeros(i);
      int k = i & j;
      stringBuilder1.append(getActionSymbolicName(j));
      i = k;
      if (k != 0) {
        stringBuilder1.append(", ");
        i = k;
      } 
    } 
    stringBuilder1.append("]");
    return stringBuilder1.toString();
  }
  
  public AccessibilityNodeInfo unwrap() {
    return this.mInfo;
  }
  
  public static class AccessibilityActionCompat {
    public static final AccessibilityActionCompat ACTION_ACCESSIBILITY_FOCUS;
    
    public static final AccessibilityActionCompat ACTION_CLEAR_ACCESSIBILITY_FOCUS;
    
    public static final AccessibilityActionCompat ACTION_CLEAR_FOCUS = new AccessibilityActionCompat(2, null);
    
    public static final AccessibilityActionCompat ACTION_CLEAR_SELECTION;
    
    public static final AccessibilityActionCompat ACTION_CLICK;
    
    public static final AccessibilityActionCompat ACTION_COLLAPSE;
    
    public static final AccessibilityActionCompat ACTION_CONTEXT_CLICK;
    
    public static final AccessibilityActionCompat ACTION_COPY;
    
    public static final AccessibilityActionCompat ACTION_CUT;
    
    public static final AccessibilityActionCompat ACTION_DISMISS;
    
    public static final AccessibilityActionCompat ACTION_EXPAND;
    
    public static final AccessibilityActionCompat ACTION_FOCUS = new AccessibilityActionCompat(1, null);
    
    public static final AccessibilityActionCompat ACTION_LONG_CLICK;
    
    public static final AccessibilityActionCompat ACTION_NEXT_AT_MOVEMENT_GRANULARITY;
    
    public static final AccessibilityActionCompat ACTION_NEXT_HTML_ELEMENT;
    
    public static final AccessibilityActionCompat ACTION_PASTE;
    
    public static final AccessibilityActionCompat ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY;
    
    public static final AccessibilityActionCompat ACTION_PREVIOUS_HTML_ELEMENT;
    
    public static final AccessibilityActionCompat ACTION_SCROLL_BACKWARD;
    
    public static final AccessibilityActionCompat ACTION_SCROLL_DOWN;
    
    public static final AccessibilityActionCompat ACTION_SCROLL_FORWARD;
    
    public static final AccessibilityActionCompat ACTION_SCROLL_LEFT;
    
    public static final AccessibilityActionCompat ACTION_SCROLL_RIGHT;
    
    public static final AccessibilityActionCompat ACTION_SCROLL_TO_POSITION;
    
    public static final AccessibilityActionCompat ACTION_SCROLL_UP;
    
    public static final AccessibilityActionCompat ACTION_SELECT = new AccessibilityActionCompat(4, null);
    
    public static final AccessibilityActionCompat ACTION_SET_PROGRESS;
    
    public static final AccessibilityActionCompat ACTION_SET_SELECTION;
    
    public static final AccessibilityActionCompat ACTION_SET_TEXT;
    
    public static final AccessibilityActionCompat ACTION_SHOW_ON_SCREEN;
    
    final Object mAction;
    
    static {
      ACTION_CLEAR_SELECTION = new AccessibilityActionCompat(8, null);
      ACTION_CLICK = new AccessibilityActionCompat(16, null);
      ACTION_LONG_CLICK = new AccessibilityActionCompat(32, null);
      ACTION_ACCESSIBILITY_FOCUS = new AccessibilityActionCompat(64, null);
      ACTION_CLEAR_ACCESSIBILITY_FOCUS = new AccessibilityActionCompat(128, null);
      ACTION_NEXT_AT_MOVEMENT_GRANULARITY = new AccessibilityActionCompat(256, null);
      ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = new AccessibilityActionCompat(512, null);
      ACTION_NEXT_HTML_ELEMENT = new AccessibilityActionCompat(1024, null);
      ACTION_PREVIOUS_HTML_ELEMENT = new AccessibilityActionCompat(2048, null);
      ACTION_SCROLL_FORWARD = new AccessibilityActionCompat(4096, null);
      ACTION_SCROLL_BACKWARD = new AccessibilityActionCompat(8192, null);
      ACTION_COPY = new AccessibilityActionCompat(16384, null);
      ACTION_PASTE = new AccessibilityActionCompat(32768, null);
      ACTION_CUT = new AccessibilityActionCompat(65536, null);
      ACTION_SET_SELECTION = new AccessibilityActionCompat(131072, null);
      ACTION_EXPAND = new AccessibilityActionCompat(262144, null);
      ACTION_COLLAPSE = new AccessibilityActionCompat(524288, null);
      ACTION_DISMISS = new AccessibilityActionCompat(1048576, null);
      ACTION_SET_TEXT = new AccessibilityActionCompat(2097152, null);
      if (Build.VERSION.SDK_INT >= 23) {
        accessibilityAction2 = AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_ON_SCREEN;
      } else {
        accessibilityAction2 = null;
      } 
      ACTION_SHOW_ON_SCREEN = new AccessibilityActionCompat(accessibilityAction2);
      if (Build.VERSION.SDK_INT >= 23) {
        accessibilityAction2 = AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_TO_POSITION;
      } else {
        accessibilityAction2 = null;
      } 
      ACTION_SCROLL_TO_POSITION = new AccessibilityActionCompat(accessibilityAction2);
      if (Build.VERSION.SDK_INT >= 23) {
        accessibilityAction2 = AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP;
      } else {
        accessibilityAction2 = null;
      } 
      ACTION_SCROLL_UP = new AccessibilityActionCompat(accessibilityAction2);
      if (Build.VERSION.SDK_INT >= 23) {
        accessibilityAction2 = AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_LEFT;
      } else {
        accessibilityAction2 = null;
      } 
      ACTION_SCROLL_LEFT = new AccessibilityActionCompat(accessibilityAction2);
      if (Build.VERSION.SDK_INT >= 23) {
        accessibilityAction2 = AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN;
      } else {
        accessibilityAction2 = null;
      } 
      ACTION_SCROLL_DOWN = new AccessibilityActionCompat(accessibilityAction2);
      if (Build.VERSION.SDK_INT >= 23) {
        accessibilityAction2 = AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_RIGHT;
      } else {
        accessibilityAction2 = null;
      } 
      ACTION_SCROLL_RIGHT = new AccessibilityActionCompat(accessibilityAction2);
      if (Build.VERSION.SDK_INT >= 23) {
        accessibilityAction2 = AccessibilityNodeInfo.AccessibilityAction.ACTION_CONTEXT_CLICK;
      } else {
        accessibilityAction2 = null;
      } 
      ACTION_CONTEXT_CLICK = new AccessibilityActionCompat(accessibilityAction2);
      AccessibilityNodeInfo.AccessibilityAction accessibilityAction2 = accessibilityAction1;
      if (Build.VERSION.SDK_INT >= 24)
        accessibilityAction2 = AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS; 
      ACTION_SET_PROGRESS = new AccessibilityActionCompat(accessibilityAction2);
    }
    
    public AccessibilityActionCompat(int param1Int, CharSequence param1CharSequence) {
      this(param1CharSequence);
    }
    
    AccessibilityActionCompat(Object param1Object) {
      this.mAction = param1Object;
    }
    
    public int getId() {
      return (Build.VERSION.SDK_INT >= 21) ? ((AccessibilityNodeInfo.AccessibilityAction)this.mAction).getId() : 0;
    }
    
    public CharSequence getLabel() {
      return (Build.VERSION.SDK_INT >= 21) ? ((AccessibilityNodeInfo.AccessibilityAction)this.mAction).getLabel() : null;
    }
    
    static {
      AccessibilityNodeInfo.AccessibilityAction accessibilityAction1 = null;
    }
  }
  
  public static class CollectionInfoCompat {
    public static final int SELECTION_MODE_MULTIPLE = 2;
    
    public static final int SELECTION_MODE_NONE = 0;
    
    public static final int SELECTION_MODE_SINGLE = 1;
    
    final Object mInfo;
    
    CollectionInfoCompat(Object param1Object) {
      this.mInfo = param1Object;
    }
    
    public static CollectionInfoCompat obtain(int param1Int1, int param1Int2, boolean param1Boolean) {
      return (Build.VERSION.SDK_INT >= 19) ? new CollectionInfoCompat(AccessibilityNodeInfo.CollectionInfo.obtain(param1Int1, param1Int2, param1Boolean)) : new CollectionInfoCompat(null);
    }
    
    public static CollectionInfoCompat obtain(int param1Int1, int param1Int2, boolean param1Boolean, int param1Int3) {
      return (Build.VERSION.SDK_INT >= 21) ? new CollectionInfoCompat(AccessibilityNodeInfo.CollectionInfo.obtain(param1Int1, param1Int2, param1Boolean, param1Int3)) : ((Build.VERSION.SDK_INT >= 19) ? new CollectionInfoCompat(AccessibilityNodeInfo.CollectionInfo.obtain(param1Int1, param1Int2, param1Boolean)) : new CollectionInfoCompat(null));
    }
    
    public int getColumnCount() {
      return (Build.VERSION.SDK_INT >= 19) ? ((AccessibilityNodeInfo.CollectionInfo)this.mInfo).getColumnCount() : 0;
    }
    
    public int getRowCount() {
      return (Build.VERSION.SDK_INT >= 19) ? ((AccessibilityNodeInfo.CollectionInfo)this.mInfo).getRowCount() : 0;
    }
    
    public int getSelectionMode() {
      return (Build.VERSION.SDK_INT >= 21) ? ((AccessibilityNodeInfo.CollectionInfo)this.mInfo).getSelectionMode() : 0;
    }
    
    public boolean isHierarchical() {
      return (Build.VERSION.SDK_INT >= 19) ? ((AccessibilityNodeInfo.CollectionInfo)this.mInfo).isHierarchical() : false;
    }
  }
  
  public static class CollectionItemInfoCompat {
    final Object mInfo;
    
    CollectionItemInfoCompat(Object param1Object) {
      this.mInfo = param1Object;
    }
    
    public static CollectionItemInfoCompat obtain(int param1Int1, int param1Int2, int param1Int3, int param1Int4, boolean param1Boolean) {
      return (Build.VERSION.SDK_INT >= 19) ? new CollectionItemInfoCompat(AccessibilityNodeInfo.CollectionItemInfo.obtain(param1Int1, param1Int2, param1Int3, param1Int4, param1Boolean)) : new CollectionItemInfoCompat(null);
    }
    
    public static CollectionItemInfoCompat obtain(int param1Int1, int param1Int2, int param1Int3, int param1Int4, boolean param1Boolean1, boolean param1Boolean2) {
      return (Build.VERSION.SDK_INT >= 21) ? new CollectionItemInfoCompat(AccessibilityNodeInfo.CollectionItemInfo.obtain(param1Int1, param1Int2, param1Int3, param1Int4, param1Boolean1, param1Boolean2)) : ((Build.VERSION.SDK_INT >= 19) ? new CollectionItemInfoCompat(AccessibilityNodeInfo.CollectionItemInfo.obtain(param1Int1, param1Int2, param1Int3, param1Int4, param1Boolean1)) : new CollectionItemInfoCompat(null));
    }
    
    public int getColumnIndex() {
      return (Build.VERSION.SDK_INT >= 19) ? ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).getColumnIndex() : 0;
    }
    
    public int getColumnSpan() {
      return (Build.VERSION.SDK_INT >= 19) ? ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).getColumnSpan() : 0;
    }
    
    public int getRowIndex() {
      return (Build.VERSION.SDK_INT >= 19) ? ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).getRowIndex() : 0;
    }
    
    public int getRowSpan() {
      return (Build.VERSION.SDK_INT >= 19) ? ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).getRowSpan() : 0;
    }
    
    public boolean isHeading() {
      return (Build.VERSION.SDK_INT >= 19) ? ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).isHeading() : false;
    }
    
    public boolean isSelected() {
      return (Build.VERSION.SDK_INT >= 21) ? ((AccessibilityNodeInfo.CollectionItemInfo)this.mInfo).isSelected() : false;
    }
  }
  
  public static class RangeInfoCompat {
    public static final int RANGE_TYPE_FLOAT = 1;
    
    public static final int RANGE_TYPE_INT = 0;
    
    public static final int RANGE_TYPE_PERCENT = 2;
    
    final Object mInfo;
    
    RangeInfoCompat(Object param1Object) {
      this.mInfo = param1Object;
    }
    
    public static RangeInfoCompat obtain(int param1Int, float param1Float1, float param1Float2, float param1Float3) {
      return (Build.VERSION.SDK_INT >= 19) ? new RangeInfoCompat(AccessibilityNodeInfo.RangeInfo.obtain(param1Int, param1Float1, param1Float2, param1Float3)) : new RangeInfoCompat(null);
    }
    
    public float getCurrent() {
      return (Build.VERSION.SDK_INT >= 19) ? ((AccessibilityNodeInfo.RangeInfo)this.mInfo).getCurrent() : 0.0F;
    }
    
    public float getMax() {
      return (Build.VERSION.SDK_INT >= 19) ? ((AccessibilityNodeInfo.RangeInfo)this.mInfo).getMax() : 0.0F;
    }
    
    public float getMin() {
      return (Build.VERSION.SDK_INT >= 19) ? ((AccessibilityNodeInfo.RangeInfo)this.mInfo).getMin() : 0.0F;
    }
    
    public int getType() {
      return (Build.VERSION.SDK_INT >= 19) ? ((AccessibilityNodeInfo.RangeInfo)this.mInfo).getType() : 0;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\view\accessibility\AccessibilityNodeInfoCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */