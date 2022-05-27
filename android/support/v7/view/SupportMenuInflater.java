package android.support.v7.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.PorterDuff;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuItemWrapperICS;
import android.support.v7.widget.DrawableUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class SupportMenuInflater extends MenuInflater {
  static final Class<?>[] ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE;
  
  static final Class<?>[] ACTION_VIEW_CONSTRUCTOR_SIGNATURE;
  
  static final String LOG_TAG = "SupportMenuInflater";
  
  static final int NO_ID = 0;
  
  private static final String XML_GROUP = "group";
  
  private static final String XML_ITEM = "item";
  
  private static final String XML_MENU = "menu";
  
  final Object[] mActionProviderConstructorArguments;
  
  final Object[] mActionViewConstructorArguments;
  
  Context mContext;
  
  private Object mRealOwner;
  
  static {
    Class[] arrayOfClass = new Class[1];
    arrayOfClass[0] = Context.class;
    ACTION_VIEW_CONSTRUCTOR_SIGNATURE = arrayOfClass;
    ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE = arrayOfClass;
  }
  
  public SupportMenuInflater(Context paramContext) {
    super(paramContext);
    this.mContext = paramContext;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramContext;
    this.mActionViewConstructorArguments = arrayOfObject;
    this.mActionProviderConstructorArguments = arrayOfObject;
  }
  
  private Object findRealOwner(Object paramObject) {
    if (paramObject instanceof android.app.Activity)
      return paramObject; 
    Object object = paramObject;
    if (paramObject instanceof ContextWrapper)
      object = findRealOwner(((ContextWrapper)paramObject).getBaseContext()); 
    return object;
  }
  
  private void parseMenu(XmlPullParser paramXmlPullParser, AttributeSet paramAttributeSet, Menu paramMenu) throws XmlPullParserException, IOException {
    StringBuilder stringBuilder;
    MenuState menuState = new MenuState(paramMenu);
    int i = paramXmlPullParser.getEventType();
    while (true) {
      if (i == 2) {
        String str = paramXmlPullParser.getName();
        if (str.equals("menu")) {
          i = paramXmlPullParser.next();
          break;
        } 
        stringBuilder = new StringBuilder();
        stringBuilder.append("Expecting menu, got ");
        stringBuilder.append(str);
        throw new RuntimeException(stringBuilder.toString());
      } 
      int k = stringBuilder.next();
      i = k;
      if (k == 1) {
        i = k;
        break;
      } 
    } 
    Menu menu = null;
    boolean bool = false;
    int j = 0;
    while (!bool) {
      if (i != 1) {
        Menu menu1;
        boolean bool1;
        if (i != 2) {
          if (i != 3) {
            bool1 = bool;
            i = j;
            paramMenu = menu;
          } else {
            String str = stringBuilder.getName();
            if (j && str.equals(menu)) {
              paramMenu = null;
              i = 0;
              bool1 = bool;
            } else if (str.equals("group")) {
              menuState.resetGroup();
              bool1 = bool;
              i = j;
              paramMenu = menu;
            } else if (str.equals("item")) {
              bool1 = bool;
              i = j;
              paramMenu = menu;
              if (!menuState.hasAddedItem())
                if (menuState.itemActionProvider != null && menuState.itemActionProvider.hasSubMenu()) {
                  menuState.addSubMenuItem();
                  bool1 = bool;
                  i = j;
                  paramMenu = menu;
                } else {
                  menuState.addItem();
                  bool1 = bool;
                  i = j;
                  paramMenu = menu;
                }  
            } else {
              bool1 = bool;
              i = j;
              paramMenu = menu;
              if (str.equals("menu")) {
                bool1 = true;
                i = j;
                paramMenu = menu;
              } 
            } 
          } 
        } else if (j) {
          bool1 = bool;
          i = j;
          paramMenu = menu;
        } else {
          String str = stringBuilder.getName();
          if (str.equals("group")) {
            menuState.readGroup(paramAttributeSet);
            bool1 = bool;
            i = j;
            menu1 = menu;
          } else if (menu1.equals("item")) {
            menuState.readItem(paramAttributeSet);
            bool1 = bool;
            i = j;
            menu1 = menu;
          } else if (menu1.equals("menu")) {
            parseMenu((XmlPullParser)stringBuilder, paramAttributeSet, (Menu)menuState.addSubMenuItem());
            bool1 = bool;
            i = j;
            menu1 = menu;
          } else {
            i = 1;
            bool1 = bool;
          } 
        } 
        int k = stringBuilder.next();
        bool = bool1;
        j = i;
        menu = menu1;
        i = k;
        continue;
      } 
      throw new RuntimeException("Unexpected end of document");
    } 
  }
  
  Object getRealOwner() {
    if (this.mRealOwner == null)
      this.mRealOwner = findRealOwner(this.mContext); 
    return this.mRealOwner;
  }
  
  public void inflate(int paramInt, Menu paramMenu) {
    InflateException inflateException1;
    InflateException inflateException2;
    if (!(paramMenu instanceof android.support.v4.internal.view.SupportMenu)) {
      super.inflate(paramInt, paramMenu);
      return;
    } 
    XmlResourceParser xmlResourceParser1 = null;
    XmlResourceParser xmlResourceParser2 = null;
    XmlResourceParser xmlResourceParser3 = null;
    try {
      XmlResourceParser xmlResourceParser = this.mContext.getResources().getLayout(paramInt);
      xmlResourceParser3 = xmlResourceParser;
      xmlResourceParser1 = xmlResourceParser;
      xmlResourceParser2 = xmlResourceParser;
      parseMenu((XmlPullParser)xmlResourceParser, Xml.asAttributeSet((XmlPullParser)xmlResourceParser), paramMenu);
      if (xmlResourceParser != null)
        xmlResourceParser.close(); 
      return;
    } catch (XmlPullParserException xmlPullParserException) {
      xmlResourceParser3 = xmlResourceParser2;
      inflateException1 = new InflateException();
      xmlResourceParser3 = xmlResourceParser2;
      this("Error inflating menu XML", (Throwable)xmlPullParserException);
      xmlResourceParser3 = xmlResourceParser2;
      throw inflateException1;
    } catch (IOException iOException) {
      inflateException2 = inflateException1;
      InflateException inflateException = new InflateException();
      inflateException2 = inflateException1;
      this("Error inflating menu XML", iOException);
      inflateException2 = inflateException1;
      throw inflateException;
    } finally {}
    if (inflateException2 != null)
      inflateException2.close(); 
    throw paramMenu;
  }
  
  private static class InflatedOnMenuItemClickListener implements MenuItem.OnMenuItemClickListener {
    private static final Class<?>[] PARAM_TYPES = new Class[] { MenuItem.class };
    
    private Method mMethod;
    
    private Object mRealOwner;
    
    public InflatedOnMenuItemClickListener(Object param1Object, String param1String) {
      this.mRealOwner = param1Object;
      Class<?> clazz = param1Object.getClass();
      try {
        this.mMethod = clazz.getMethod(param1String, PARAM_TYPES);
        return;
      } catch (Exception exception) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Couldn't resolve menu item onClick handler ");
        stringBuilder.append(param1String);
        stringBuilder.append(" in class ");
        stringBuilder.append(clazz.getName());
        InflateException inflateException = new InflateException(stringBuilder.toString());
        inflateException.initCause(exception);
        throw inflateException;
      } 
    }
    
    public boolean onMenuItemClick(MenuItem param1MenuItem) {
      try {
        if (this.mMethod.getReturnType() == boolean.class)
          return ((Boolean)this.mMethod.invoke(this.mRealOwner, new Object[] { param1MenuItem })).booleanValue(); 
        this.mMethod.invoke(this.mRealOwner, new Object[] { param1MenuItem });
        return true;
      } catch (Exception exception) {
        throw new RuntimeException(exception);
      } 
    }
  }
  
  private class MenuState {
    private static final int defaultGroupId = 0;
    
    private static final int defaultItemCategory = 0;
    
    private static final int defaultItemCheckable = 0;
    
    private static final boolean defaultItemChecked = false;
    
    private static final boolean defaultItemEnabled = true;
    
    private static final int defaultItemId = 0;
    
    private static final int defaultItemOrder = 0;
    
    private static final boolean defaultItemVisible = true;
    
    private int groupCategory;
    
    private int groupCheckable;
    
    private boolean groupEnabled;
    
    private int groupId;
    
    private int groupOrder;
    
    private boolean groupVisible;
    
    ActionProvider itemActionProvider;
    
    private String itemActionProviderClassName;
    
    private String itemActionViewClassName;
    
    private int itemActionViewLayout;
    
    private boolean itemAdded;
    
    private int itemAlphabeticModifiers;
    
    private char itemAlphabeticShortcut;
    
    private int itemCategoryOrder;
    
    private int itemCheckable;
    
    private boolean itemChecked;
    
    private CharSequence itemContentDescription;
    
    private boolean itemEnabled;
    
    private int itemIconResId;
    
    private ColorStateList itemIconTintList = null;
    
    private PorterDuff.Mode itemIconTintMode = null;
    
    private int itemId;
    
    private String itemListenerMethodName;
    
    private int itemNumericModifiers;
    
    private char itemNumericShortcut;
    
    private int itemShowAsAction;
    
    private CharSequence itemTitle;
    
    private CharSequence itemTitleCondensed;
    
    private CharSequence itemTooltipText;
    
    private boolean itemVisible;
    
    private Menu menu;
    
    public MenuState(Menu param1Menu) {
      this.menu = param1Menu;
      resetGroup();
    }
    
    private char getShortcut(String param1String) {
      return (param1String == null) ? Character.MIN_VALUE : param1String.charAt(0);
    }
    
    private <T> T newInstance(String param1String, Class<?>[] param1ArrayOfClass, Object[] param1ArrayOfObject) {
      try {
        null = SupportMenuInflater.this.mContext.getClassLoader().loadClass(param1String).getConstructor(param1ArrayOfClass);
        null.setAccessible(true);
        return (T)null.newInstance(param1ArrayOfObject);
      } catch (Exception exception) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot instantiate class: ");
        stringBuilder.append(param1String);
        Log.w("SupportMenuInflater", stringBuilder.toString(), exception);
        return null;
      } 
    }
    
    private void setItem(MenuItem param1MenuItem) {
      MenuItem menuItem = param1MenuItem.setChecked(this.itemChecked).setVisible(this.itemVisible).setEnabled(this.itemEnabled);
      int i = this.itemCheckable;
      boolean bool = false;
      if (i >= 1) {
        bool1 = true;
      } else {
        bool1 = false;
      } 
      menuItem.setCheckable(bool1).setTitleCondensed(this.itemTitleCondensed).setIcon(this.itemIconResId);
      i = this.itemShowAsAction;
      if (i >= 0)
        param1MenuItem.setShowAsAction(i); 
      if (this.itemListenerMethodName != null)
        if (!SupportMenuInflater.this.mContext.isRestricted()) {
          param1MenuItem.setOnMenuItemClickListener(new SupportMenuInflater.InflatedOnMenuItemClickListener(SupportMenuInflater.this.getRealOwner(), this.itemListenerMethodName));
        } else {
          throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
        }  
      boolean bool1 = param1MenuItem instanceof MenuItemImpl;
      if (bool1)
        MenuItemImpl menuItemImpl = (MenuItemImpl)param1MenuItem; 
      if (this.itemCheckable >= 2)
        if (bool1) {
          ((MenuItemImpl)param1MenuItem).setExclusiveCheckable(true);
        } else if (param1MenuItem instanceof MenuItemWrapperICS) {
          ((MenuItemWrapperICS)param1MenuItem).setExclusiveCheckable(true);
        }  
      String str = this.itemActionViewClassName;
      if (str != null) {
        param1MenuItem.setActionView(newInstance(str, SupportMenuInflater.ACTION_VIEW_CONSTRUCTOR_SIGNATURE, SupportMenuInflater.this.mActionViewConstructorArguments));
        bool = true;
      } 
      i = this.itemActionViewLayout;
      if (i > 0)
        if (!bool) {
          param1MenuItem.setActionView(i);
        } else {
          Log.w("SupportMenuInflater", "Ignoring attribute 'itemActionViewLayout'. Action view already specified.");
        }  
      ActionProvider actionProvider = this.itemActionProvider;
      if (actionProvider != null)
        MenuItemCompat.setActionProvider(param1MenuItem, actionProvider); 
      MenuItemCompat.setContentDescription(param1MenuItem, this.itemContentDescription);
      MenuItemCompat.setTooltipText(param1MenuItem, this.itemTooltipText);
      MenuItemCompat.setAlphabeticShortcut(param1MenuItem, this.itemAlphabeticShortcut, this.itemAlphabeticModifiers);
      MenuItemCompat.setNumericShortcut(param1MenuItem, this.itemNumericShortcut, this.itemNumericModifiers);
      PorterDuff.Mode mode = this.itemIconTintMode;
      if (mode != null)
        MenuItemCompat.setIconTintMode(param1MenuItem, mode); 
      ColorStateList colorStateList = this.itemIconTintList;
      if (colorStateList != null)
        MenuItemCompat.setIconTintList(param1MenuItem, colorStateList); 
    }
    
    public void addItem() {
      this.itemAdded = true;
      setItem(this.menu.add(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle));
    }
    
    public SubMenu addSubMenuItem() {
      this.itemAdded = true;
      SubMenu subMenu = this.menu.addSubMenu(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle);
      setItem(subMenu.getItem());
      return subMenu;
    }
    
    public boolean hasAddedItem() {
      return this.itemAdded;
    }
    
    public void readGroup(AttributeSet param1AttributeSet) {
      TypedArray typedArray = SupportMenuInflater.this.mContext.obtainStyledAttributes(param1AttributeSet, R.styleable.MenuGroup);
      this.groupId = typedArray.getResourceId(R.styleable.MenuGroup_android_id, 0);
      this.groupCategory = typedArray.getInt(R.styleable.MenuGroup_android_menuCategory, 0);
      this.groupOrder = typedArray.getInt(R.styleable.MenuGroup_android_orderInCategory, 0);
      this.groupCheckable = typedArray.getInt(R.styleable.MenuGroup_android_checkableBehavior, 0);
      this.groupVisible = typedArray.getBoolean(R.styleable.MenuGroup_android_visible, true);
      this.groupEnabled = typedArray.getBoolean(R.styleable.MenuGroup_android_enabled, true);
      typedArray.recycle();
    }
    
    public void readItem(AttributeSet param1AttributeSet) {
      boolean bool;
      TypedArray typedArray = SupportMenuInflater.this.mContext.obtainStyledAttributes(param1AttributeSet, R.styleable.MenuItem);
      this.itemId = typedArray.getResourceId(R.styleable.MenuItem_android_id, 0);
      this.itemCategoryOrder = typedArray.getInt(R.styleable.MenuItem_android_menuCategory, this.groupCategory) & 0xFFFF0000 | typedArray.getInt(R.styleable.MenuItem_android_orderInCategory, this.groupOrder) & 0xFFFF;
      this.itemTitle = typedArray.getText(R.styleable.MenuItem_android_title);
      this.itemTitleCondensed = typedArray.getText(R.styleable.MenuItem_android_titleCondensed);
      this.itemIconResId = typedArray.getResourceId(R.styleable.MenuItem_android_icon, 0);
      this.itemAlphabeticShortcut = getShortcut(typedArray.getString(R.styleable.MenuItem_android_alphabeticShortcut));
      this.itemAlphabeticModifiers = typedArray.getInt(R.styleable.MenuItem_alphabeticModifiers, 4096);
      this.itemNumericShortcut = getShortcut(typedArray.getString(R.styleable.MenuItem_android_numericShortcut));
      this.itemNumericModifiers = typedArray.getInt(R.styleable.MenuItem_numericModifiers, 4096);
      if (typedArray.hasValue(R.styleable.MenuItem_android_checkable)) {
        this.itemCheckable = typedArray.getBoolean(R.styleable.MenuItem_android_checkable, false);
      } else {
        this.itemCheckable = this.groupCheckable;
      } 
      this.itemChecked = typedArray.getBoolean(R.styleable.MenuItem_android_checked, false);
      this.itemVisible = typedArray.getBoolean(R.styleable.MenuItem_android_visible, this.groupVisible);
      this.itemEnabled = typedArray.getBoolean(R.styleable.MenuItem_android_enabled, this.groupEnabled);
      this.itemShowAsAction = typedArray.getInt(R.styleable.MenuItem_showAsAction, -1);
      this.itemListenerMethodName = typedArray.getString(R.styleable.MenuItem_android_onClick);
      this.itemActionViewLayout = typedArray.getResourceId(R.styleable.MenuItem_actionLayout, 0);
      this.itemActionViewClassName = typedArray.getString(R.styleable.MenuItem_actionViewClass);
      String str = typedArray.getString(R.styleable.MenuItem_actionProviderClass);
      this.itemActionProviderClassName = str;
      if (str != null) {
        bool = true;
      } else {
        bool = false;
      } 
      if (bool && this.itemActionViewLayout == 0 && this.itemActionViewClassName == null) {
        this.itemActionProvider = newInstance(this.itemActionProviderClassName, SupportMenuInflater.ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE, SupportMenuInflater.this.mActionProviderConstructorArguments);
      } else {
        if (bool)
          Log.w("SupportMenuInflater", "Ignoring attribute 'actionProviderClass'. Action view already specified."); 
        this.itemActionProvider = null;
      } 
      this.itemContentDescription = typedArray.getText(R.styleable.MenuItem_contentDescription);
      this.itemTooltipText = typedArray.getText(R.styleable.MenuItem_tooltipText);
      if (typedArray.hasValue(R.styleable.MenuItem_iconTintMode)) {
        this.itemIconTintMode = DrawableUtils.parseTintMode(typedArray.getInt(R.styleable.MenuItem_iconTintMode, -1), this.itemIconTintMode);
      } else {
        this.itemIconTintMode = null;
      } 
      if (typedArray.hasValue(R.styleable.MenuItem_iconTint)) {
        this.itemIconTintList = typedArray.getColorStateList(R.styleable.MenuItem_iconTint);
      } else {
        this.itemIconTintList = null;
      } 
      typedArray.recycle();
      this.itemAdded = false;
    }
    
    public void resetGroup() {
      this.groupId = 0;
      this.groupCategory = 0;
      this.groupOrder = 0;
      this.groupCheckable = 0;
      this.groupVisible = true;
      this.groupEnabled = true;
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v7\view\SupportMenuInflater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */