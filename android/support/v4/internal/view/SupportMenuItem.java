package android.support.v4.internal.view;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.v4.view.ActionProvider;
import android.view.MenuItem;
import android.view.View;

public interface SupportMenuItem extends MenuItem {
  public static final int SHOW_AS_ACTION_ALWAYS = 2;
  
  public static final int SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW = 8;
  
  public static final int SHOW_AS_ACTION_IF_ROOM = 1;
  
  public static final int SHOW_AS_ACTION_NEVER = 0;
  
  public static final int SHOW_AS_ACTION_WITH_TEXT = 4;
  
  boolean collapseActionView();
  
  boolean expandActionView();
  
  View getActionView();
  
  int getAlphabeticModifiers();
  
  CharSequence getContentDescription();
  
  ColorStateList getIconTintList();
  
  PorterDuff.Mode getIconTintMode();
  
  int getNumericModifiers();
  
  ActionProvider getSupportActionProvider();
  
  CharSequence getTooltipText();
  
  boolean isActionViewExpanded();
  
  MenuItem setActionView(int paramInt);
  
  MenuItem setActionView(View paramView);
  
  MenuItem setAlphabeticShortcut(char paramChar, int paramInt);
  
  SupportMenuItem setContentDescription(CharSequence paramCharSequence);
  
  MenuItem setIconTintList(ColorStateList paramColorStateList);
  
  MenuItem setIconTintMode(PorterDuff.Mode paramMode);
  
  MenuItem setNumericShortcut(char paramChar, int paramInt);
  
  MenuItem setShortcut(char paramChar1, char paramChar2, int paramInt1, int paramInt2);
  
  void setShowAsAction(int paramInt);
  
  MenuItem setShowAsActionFlags(int paramInt);
  
  SupportMenuItem setSupportActionProvider(ActionProvider paramActionProvider);
  
  SupportMenuItem setTooltipText(CharSequence paramCharSequence);
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\internal\view\SupportMenuItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */