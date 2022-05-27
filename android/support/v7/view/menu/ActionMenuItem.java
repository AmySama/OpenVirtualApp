package android.support.v7.view.menu;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.internal.view.SupportMenuItem;
import android.support.v4.view.ActionProvider;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

public class ActionMenuItem implements SupportMenuItem {
  private static final int CHECKABLE = 1;
  
  private static final int CHECKED = 2;
  
  private static final int ENABLED = 16;
  
  private static final int EXCLUSIVE = 4;
  
  private static final int HIDDEN = 8;
  
  private static final int NO_ICON = 0;
  
  private final int mCategoryOrder;
  
  private MenuItem.OnMenuItemClickListener mClickListener;
  
  private CharSequence mContentDescription;
  
  private Context mContext;
  
  private int mFlags = 16;
  
  private final int mGroup;
  
  private boolean mHasIconTint = false;
  
  private boolean mHasIconTintMode = false;
  
  private Drawable mIconDrawable;
  
  private int mIconResId = 0;
  
  private ColorStateList mIconTintList = null;
  
  private PorterDuff.Mode mIconTintMode = null;
  
  private final int mId;
  
  private Intent mIntent;
  
  private final int mOrdering;
  
  private char mShortcutAlphabeticChar;
  
  private int mShortcutAlphabeticModifiers = 4096;
  
  private char mShortcutNumericChar;
  
  private int mShortcutNumericModifiers = 4096;
  
  private CharSequence mTitle;
  
  private CharSequence mTitleCondensed;
  
  private CharSequence mTooltipText;
  
  public ActionMenuItem(Context paramContext, int paramInt1, int paramInt2, int paramInt3, int paramInt4, CharSequence paramCharSequence) {
    this.mContext = paramContext;
    this.mId = paramInt2;
    this.mGroup = paramInt1;
    this.mCategoryOrder = paramInt3;
    this.mOrdering = paramInt4;
    this.mTitle = paramCharSequence;
  }
  
  private void applyIconTint() {
    if (this.mIconDrawable != null && (this.mHasIconTint || this.mHasIconTintMode)) {
      Drawable drawable = DrawableCompat.wrap(this.mIconDrawable);
      this.mIconDrawable = drawable;
      drawable = drawable.mutate();
      this.mIconDrawable = drawable;
      if (this.mHasIconTint)
        DrawableCompat.setTintList(drawable, this.mIconTintList); 
      if (this.mHasIconTintMode)
        DrawableCompat.setTintMode(this.mIconDrawable, this.mIconTintMode); 
    } 
  }
  
  public boolean collapseActionView() {
    return false;
  }
  
  public boolean expandActionView() {
    return false;
  }
  
  public ActionProvider getActionProvider() {
    throw new UnsupportedOperationException();
  }
  
  public View getActionView() {
    return null;
  }
  
  public int getAlphabeticModifiers() {
    return this.mShortcutAlphabeticModifiers;
  }
  
  public char getAlphabeticShortcut() {
    return this.mShortcutAlphabeticChar;
  }
  
  public CharSequence getContentDescription() {
    return this.mContentDescription;
  }
  
  public int getGroupId() {
    return this.mGroup;
  }
  
  public Drawable getIcon() {
    return this.mIconDrawable;
  }
  
  public ColorStateList getIconTintList() {
    return this.mIconTintList;
  }
  
  public PorterDuff.Mode getIconTintMode() {
    return this.mIconTintMode;
  }
  
  public Intent getIntent() {
    return this.mIntent;
  }
  
  public int getItemId() {
    return this.mId;
  }
  
  public ContextMenu.ContextMenuInfo getMenuInfo() {
    return null;
  }
  
  public int getNumericModifiers() {
    return this.mShortcutNumericModifiers;
  }
  
  public char getNumericShortcut() {
    return this.mShortcutNumericChar;
  }
  
  public int getOrder() {
    return this.mOrdering;
  }
  
  public SubMenu getSubMenu() {
    return null;
  }
  
  public ActionProvider getSupportActionProvider() {
    return null;
  }
  
  public CharSequence getTitle() {
    return this.mTitle;
  }
  
  public CharSequence getTitleCondensed() {
    CharSequence charSequence = this.mTitleCondensed;
    if (charSequence == null)
      charSequence = this.mTitle; 
    return charSequence;
  }
  
  public CharSequence getTooltipText() {
    return this.mTooltipText;
  }
  
  public boolean hasSubMenu() {
    return false;
  }
  
  public boolean invoke() {
    MenuItem.OnMenuItemClickListener onMenuItemClickListener = this.mClickListener;
    if (onMenuItemClickListener != null && onMenuItemClickListener.onMenuItemClick((MenuItem)this))
      return true; 
    Intent intent = this.mIntent;
    if (intent != null) {
      this.mContext.startActivity(intent);
      return true;
    } 
    return false;
  }
  
  public boolean isActionViewExpanded() {
    return false;
  }
  
  public boolean isCheckable() {
    int i = this.mFlags;
    boolean bool = true;
    if ((i & 0x1) == 0)
      bool = false; 
    return bool;
  }
  
  public boolean isChecked() {
    boolean bool;
    if ((this.mFlags & 0x2) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isEnabled() {
    boolean bool;
    if ((this.mFlags & 0x10) != 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public boolean isVisible() {
    boolean bool;
    if ((this.mFlags & 0x8) == 0) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
  
  public MenuItem setActionProvider(ActionProvider paramActionProvider) {
    throw new UnsupportedOperationException();
  }
  
  public SupportMenuItem setActionView(int paramInt) {
    throw new UnsupportedOperationException();
  }
  
  public SupportMenuItem setActionView(View paramView) {
    throw new UnsupportedOperationException();
  }
  
  public MenuItem setAlphabeticShortcut(char paramChar) {
    this.mShortcutAlphabeticChar = Character.toLowerCase(paramChar);
    return (MenuItem)this;
  }
  
  public MenuItem setAlphabeticShortcut(char paramChar, int paramInt) {
    this.mShortcutAlphabeticChar = Character.toLowerCase(paramChar);
    this.mShortcutAlphabeticModifiers = KeyEvent.normalizeMetaState(paramInt);
    return (MenuItem)this;
  }
  
  public MenuItem setCheckable(boolean paramBoolean) {
    this.mFlags = paramBoolean | this.mFlags & 0xFFFFFFFE;
    return (MenuItem)this;
  }
  
  public MenuItem setChecked(boolean paramBoolean) {
    boolean bool;
    int i = this.mFlags;
    if (paramBoolean) {
      bool = true;
    } else {
      bool = false;
    } 
    this.mFlags = bool | i & 0xFFFFFFFD;
    return (MenuItem)this;
  }
  
  public SupportMenuItem setContentDescription(CharSequence paramCharSequence) {
    this.mContentDescription = paramCharSequence;
    return this;
  }
  
  public MenuItem setEnabled(boolean paramBoolean) {
    boolean bool;
    int i = this.mFlags;
    if (paramBoolean) {
      bool = true;
    } else {
      bool = false;
    } 
    this.mFlags = bool | i & 0xFFFFFFEF;
    return (MenuItem)this;
  }
  
  public ActionMenuItem setExclusiveCheckable(boolean paramBoolean) {
    boolean bool;
    int i = this.mFlags;
    if (paramBoolean) {
      bool = true;
    } else {
      bool = false;
    } 
    this.mFlags = bool | i & 0xFFFFFFFB;
    return this;
  }
  
  public MenuItem setIcon(int paramInt) {
    this.mIconResId = paramInt;
    this.mIconDrawable = ContextCompat.getDrawable(this.mContext, paramInt);
    applyIconTint();
    return (MenuItem)this;
  }
  
  public MenuItem setIcon(Drawable paramDrawable) {
    this.mIconDrawable = paramDrawable;
    this.mIconResId = 0;
    applyIconTint();
    return (MenuItem)this;
  }
  
  public MenuItem setIconTintList(ColorStateList paramColorStateList) {
    this.mIconTintList = paramColorStateList;
    this.mHasIconTint = true;
    applyIconTint();
    return (MenuItem)this;
  }
  
  public MenuItem setIconTintMode(PorterDuff.Mode paramMode) {
    this.mIconTintMode = paramMode;
    this.mHasIconTintMode = true;
    applyIconTint();
    return (MenuItem)this;
  }
  
  public MenuItem setIntent(Intent paramIntent) {
    this.mIntent = paramIntent;
    return (MenuItem)this;
  }
  
  public MenuItem setNumericShortcut(char paramChar) {
    this.mShortcutNumericChar = (char)paramChar;
    return (MenuItem)this;
  }
  
  public MenuItem setNumericShortcut(char paramChar, int paramInt) {
    this.mShortcutNumericChar = (char)paramChar;
    this.mShortcutNumericModifiers = KeyEvent.normalizeMetaState(paramInt);
    return (MenuItem)this;
  }
  
  public MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener paramOnActionExpandListener) {
    throw new UnsupportedOperationException();
  }
  
  public MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener paramOnMenuItemClickListener) {
    this.mClickListener = paramOnMenuItemClickListener;
    return (MenuItem)this;
  }
  
  public MenuItem setShortcut(char paramChar1, char paramChar2) {
    this.mShortcutNumericChar = (char)paramChar1;
    this.mShortcutAlphabeticChar = Character.toLowerCase(paramChar2);
    return (MenuItem)this;
  }
  
  public MenuItem setShortcut(char paramChar1, char paramChar2, int paramInt1, int paramInt2) {
    this.mShortcutNumericChar = (char)paramChar1;
    this.mShortcutNumericModifiers = KeyEvent.normalizeMetaState(paramInt1);
    this.mShortcutAlphabeticChar = Character.toLowerCase(paramChar2);
    this.mShortcutAlphabeticModifiers = KeyEvent.normalizeMetaState(paramInt2);
    return (MenuItem)this;
  }
  
  public void setShowAsAction(int paramInt) {}
  
  public SupportMenuItem setShowAsActionFlags(int paramInt) {
    setShowAsAction(paramInt);
    return this;
  }
  
  public SupportMenuItem setSupportActionProvider(ActionProvider paramActionProvider) {
    throw new UnsupportedOperationException();
  }
  
  public MenuItem setTitle(int paramInt) {
    this.mTitle = this.mContext.getResources().getString(paramInt);
    return (MenuItem)this;
  }
  
  public MenuItem setTitle(CharSequence paramCharSequence) {
    this.mTitle = paramCharSequence;
    return (MenuItem)this;
  }
  
  public MenuItem setTitleCondensed(CharSequence paramCharSequence) {
    this.mTitleCondensed = paramCharSequence;
    return (MenuItem)this;
  }
  
  public SupportMenuItem setTooltipText(CharSequence paramCharSequence) {
    this.mTooltipText = paramCharSequence;
    return this;
  }
  
  public MenuItem setVisible(boolean paramBoolean) {
    int i = this.mFlags;
    byte b = 8;
    if (paramBoolean)
      b = 0; 
    this.mFlags = i & 0x8 | b;
    return (MenuItem)this;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\view\menu\ActionMenuItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */