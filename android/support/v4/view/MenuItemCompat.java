package android.support.v4.view;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v4.internal.view.SupportMenuItem;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

public final class MenuItemCompat {
  static final MenuVersionImpl IMPL;
  
  @Deprecated
  public static final int SHOW_AS_ACTION_ALWAYS = 2;
  
  @Deprecated
  public static final int SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW = 8;
  
  @Deprecated
  public static final int SHOW_AS_ACTION_IF_ROOM = 1;
  
  @Deprecated
  public static final int SHOW_AS_ACTION_NEVER = 0;
  
  @Deprecated
  public static final int SHOW_AS_ACTION_WITH_TEXT = 4;
  
  private static final String TAG = "MenuItemCompat";
  
  static {
    if (Build.VERSION.SDK_INT >= 26) {
      IMPL = new MenuItemCompatApi26Impl();
    } else {
      IMPL = new MenuItemCompatBaseImpl();
    } 
  }
  
  @Deprecated
  public static boolean collapseActionView(MenuItem paramMenuItem) {
    return paramMenuItem.collapseActionView();
  }
  
  @Deprecated
  public static boolean expandActionView(MenuItem paramMenuItem) {
    return paramMenuItem.expandActionView();
  }
  
  public static ActionProvider getActionProvider(MenuItem paramMenuItem) {
    if (paramMenuItem instanceof SupportMenuItem)
      return ((SupportMenuItem)paramMenuItem).getSupportActionProvider(); 
    Log.w("MenuItemCompat", "getActionProvider: item does not implement SupportMenuItem; returning null");
    return null;
  }
  
  @Deprecated
  public static View getActionView(MenuItem paramMenuItem) {
    return paramMenuItem.getActionView();
  }
  
  public static int getAlphabeticModifiers(MenuItem paramMenuItem) {
    return (paramMenuItem instanceof SupportMenuItem) ? ((SupportMenuItem)paramMenuItem).getAlphabeticModifiers() : IMPL.getAlphabeticModifiers(paramMenuItem);
  }
  
  public static CharSequence getContentDescription(MenuItem paramMenuItem) {
    return (paramMenuItem instanceof SupportMenuItem) ? ((SupportMenuItem)paramMenuItem).getContentDescription() : IMPL.getContentDescription(paramMenuItem);
  }
  
  public static ColorStateList getIconTintList(MenuItem paramMenuItem) {
    return (paramMenuItem instanceof SupportMenuItem) ? ((SupportMenuItem)paramMenuItem).getIconTintList() : IMPL.getIconTintList(paramMenuItem);
  }
  
  public static PorterDuff.Mode getIconTintMode(MenuItem paramMenuItem) {
    return (paramMenuItem instanceof SupportMenuItem) ? ((SupportMenuItem)paramMenuItem).getIconTintMode() : IMPL.getIconTintMode(paramMenuItem);
  }
  
  public static int getNumericModifiers(MenuItem paramMenuItem) {
    return (paramMenuItem instanceof SupportMenuItem) ? ((SupportMenuItem)paramMenuItem).getNumericModifiers() : IMPL.getNumericModifiers(paramMenuItem);
  }
  
  public static CharSequence getTooltipText(MenuItem paramMenuItem) {
    return (paramMenuItem instanceof SupportMenuItem) ? ((SupportMenuItem)paramMenuItem).getTooltipText() : IMPL.getTooltipText(paramMenuItem);
  }
  
  @Deprecated
  public static boolean isActionViewExpanded(MenuItem paramMenuItem) {
    return paramMenuItem.isActionViewExpanded();
  }
  
  public static MenuItem setActionProvider(MenuItem paramMenuItem, ActionProvider paramActionProvider) {
    if (paramMenuItem instanceof SupportMenuItem)
      return (MenuItem)((SupportMenuItem)paramMenuItem).setSupportActionProvider(paramActionProvider); 
    Log.w("MenuItemCompat", "setActionProvider: item does not implement SupportMenuItem; ignoring");
    return paramMenuItem;
  }
  
  @Deprecated
  public static MenuItem setActionView(MenuItem paramMenuItem, int paramInt) {
    return paramMenuItem.setActionView(paramInt);
  }
  
  @Deprecated
  public static MenuItem setActionView(MenuItem paramMenuItem, View paramView) {
    return paramMenuItem.setActionView(paramView);
  }
  
  public static void setAlphabeticShortcut(MenuItem paramMenuItem, char paramChar, int paramInt) {
    if (paramMenuItem instanceof SupportMenuItem) {
      ((SupportMenuItem)paramMenuItem).setAlphabeticShortcut(paramChar, paramInt);
    } else {
      IMPL.setAlphabeticShortcut(paramMenuItem, paramChar, paramInt);
    } 
  }
  
  public static void setContentDescription(MenuItem paramMenuItem, CharSequence paramCharSequence) {
    if (paramMenuItem instanceof SupportMenuItem) {
      ((SupportMenuItem)paramMenuItem).setContentDescription(paramCharSequence);
    } else {
      IMPL.setContentDescription(paramMenuItem, paramCharSequence);
    } 
  }
  
  public static void setIconTintList(MenuItem paramMenuItem, ColorStateList paramColorStateList) {
    if (paramMenuItem instanceof SupportMenuItem) {
      ((SupportMenuItem)paramMenuItem).setIconTintList(paramColorStateList);
    } else {
      IMPL.setIconTintList(paramMenuItem, paramColorStateList);
    } 
  }
  
  public static void setIconTintMode(MenuItem paramMenuItem, PorterDuff.Mode paramMode) {
    if (paramMenuItem instanceof SupportMenuItem) {
      ((SupportMenuItem)paramMenuItem).setIconTintMode(paramMode);
    } else {
      IMPL.setIconTintMode(paramMenuItem, paramMode);
    } 
  }
  
  public static void setNumericShortcut(MenuItem paramMenuItem, char paramChar, int paramInt) {
    if (paramMenuItem instanceof SupportMenuItem) {
      ((SupportMenuItem)paramMenuItem).setNumericShortcut(paramChar, paramInt);
    } else {
      IMPL.setNumericShortcut(paramMenuItem, paramChar, paramInt);
    } 
  }
  
  @Deprecated
  public static MenuItem setOnActionExpandListener(MenuItem paramMenuItem, final OnActionExpandListener listener) {
    return paramMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
          public boolean onMenuItemActionCollapse(MenuItem param1MenuItem) {
            return listener.onMenuItemActionCollapse(param1MenuItem);
          }
          
          public boolean onMenuItemActionExpand(MenuItem param1MenuItem) {
            return listener.onMenuItemActionExpand(param1MenuItem);
          }
        });
  }
  
  public static void setShortcut(MenuItem paramMenuItem, char paramChar1, char paramChar2, int paramInt1, int paramInt2) {
    if (paramMenuItem instanceof SupportMenuItem) {
      ((SupportMenuItem)paramMenuItem).setShortcut(paramChar1, paramChar2, paramInt1, paramInt2);
    } else {
      IMPL.setShortcut(paramMenuItem, paramChar1, paramChar2, paramInt1, paramInt2);
    } 
  }
  
  @Deprecated
  public static void setShowAsAction(MenuItem paramMenuItem, int paramInt) {
    paramMenuItem.setShowAsAction(paramInt);
  }
  
  public static void setTooltipText(MenuItem paramMenuItem, CharSequence paramCharSequence) {
    if (paramMenuItem instanceof SupportMenuItem) {
      ((SupportMenuItem)paramMenuItem).setTooltipText(paramCharSequence);
    } else {
      IMPL.setTooltipText(paramMenuItem, paramCharSequence);
    } 
  }
  
  static class MenuItemCompatApi26Impl extends MenuItemCompatBaseImpl {
    public int getAlphabeticModifiers(MenuItem param1MenuItem) {
      return param1MenuItem.getAlphabeticModifiers();
    }
    
    public CharSequence getContentDescription(MenuItem param1MenuItem) {
      return param1MenuItem.getContentDescription();
    }
    
    public ColorStateList getIconTintList(MenuItem param1MenuItem) {
      return param1MenuItem.getIconTintList();
    }
    
    public PorterDuff.Mode getIconTintMode(MenuItem param1MenuItem) {
      return param1MenuItem.getIconTintMode();
    }
    
    public int getNumericModifiers(MenuItem param1MenuItem) {
      return param1MenuItem.getNumericModifiers();
    }
    
    public CharSequence getTooltipText(MenuItem param1MenuItem) {
      return param1MenuItem.getTooltipText();
    }
    
    public void setAlphabeticShortcut(MenuItem param1MenuItem, char param1Char, int param1Int) {
      param1MenuItem.setAlphabeticShortcut(param1Char, param1Int);
    }
    
    public void setContentDescription(MenuItem param1MenuItem, CharSequence param1CharSequence) {
      param1MenuItem.setContentDescription(param1CharSequence);
    }
    
    public void setIconTintList(MenuItem param1MenuItem, ColorStateList param1ColorStateList) {
      param1MenuItem.setIconTintList(param1ColorStateList);
    }
    
    public void setIconTintMode(MenuItem param1MenuItem, PorterDuff.Mode param1Mode) {
      param1MenuItem.setIconTintMode(param1Mode);
    }
    
    public void setNumericShortcut(MenuItem param1MenuItem, char param1Char, int param1Int) {
      param1MenuItem.setNumericShortcut(param1Char, param1Int);
    }
    
    public void setShortcut(MenuItem param1MenuItem, char param1Char1, char param1Char2, int param1Int1, int param1Int2) {
      param1MenuItem.setShortcut(param1Char1, param1Char2, param1Int1, param1Int2);
    }
    
    public void setTooltipText(MenuItem param1MenuItem, CharSequence param1CharSequence) {
      param1MenuItem.setTooltipText(param1CharSequence);
    }
  }
  
  static class MenuItemCompatBaseImpl implements MenuVersionImpl {
    public int getAlphabeticModifiers(MenuItem param1MenuItem) {
      return 0;
    }
    
    public CharSequence getContentDescription(MenuItem param1MenuItem) {
      return null;
    }
    
    public ColorStateList getIconTintList(MenuItem param1MenuItem) {
      return null;
    }
    
    public PorterDuff.Mode getIconTintMode(MenuItem param1MenuItem) {
      return null;
    }
    
    public int getNumericModifiers(MenuItem param1MenuItem) {
      return 0;
    }
    
    public CharSequence getTooltipText(MenuItem param1MenuItem) {
      return null;
    }
    
    public void setAlphabeticShortcut(MenuItem param1MenuItem, char param1Char, int param1Int) {}
    
    public void setContentDescription(MenuItem param1MenuItem, CharSequence param1CharSequence) {}
    
    public void setIconTintList(MenuItem param1MenuItem, ColorStateList param1ColorStateList) {}
    
    public void setIconTintMode(MenuItem param1MenuItem, PorterDuff.Mode param1Mode) {}
    
    public void setNumericShortcut(MenuItem param1MenuItem, char param1Char, int param1Int) {}
    
    public void setShortcut(MenuItem param1MenuItem, char param1Char1, char param1Char2, int param1Int1, int param1Int2) {}
    
    public void setTooltipText(MenuItem param1MenuItem, CharSequence param1CharSequence) {}
  }
  
  static interface MenuVersionImpl {
    int getAlphabeticModifiers(MenuItem param1MenuItem);
    
    CharSequence getContentDescription(MenuItem param1MenuItem);
    
    ColorStateList getIconTintList(MenuItem param1MenuItem);
    
    PorterDuff.Mode getIconTintMode(MenuItem param1MenuItem);
    
    int getNumericModifiers(MenuItem param1MenuItem);
    
    CharSequence getTooltipText(MenuItem param1MenuItem);
    
    void setAlphabeticShortcut(MenuItem param1MenuItem, char param1Char, int param1Int);
    
    void setContentDescription(MenuItem param1MenuItem, CharSequence param1CharSequence);
    
    void setIconTintList(MenuItem param1MenuItem, ColorStateList param1ColorStateList);
    
    void setIconTintMode(MenuItem param1MenuItem, PorterDuff.Mode param1Mode);
    
    void setNumericShortcut(MenuItem param1MenuItem, char param1Char, int param1Int);
    
    void setShortcut(MenuItem param1MenuItem, char param1Char1, char param1Char2, int param1Int1, int param1Int2);
    
    void setTooltipText(MenuItem param1MenuItem, CharSequence param1CharSequence);
  }
  
  @Deprecated
  public static interface OnActionExpandListener {
    boolean onMenuItemActionCollapse(MenuItem param1MenuItem);
    
    boolean onMenuItemActionExpand(MenuItem param1MenuItem);
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\view\MenuItemCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */