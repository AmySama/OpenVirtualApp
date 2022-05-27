package android.support.v4.widget;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.os.BuildCompat;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class TextViewCompat {
  public static final int AUTO_SIZE_TEXT_TYPE_NONE = 0;
  
  public static final int AUTO_SIZE_TEXT_TYPE_UNIFORM = 1;
  
  static final TextViewCompatBaseImpl IMPL;
  
  static {
    if (BuildCompat.isAtLeastOMR1()) {
      IMPL = new TextViewCompatApi27Impl();
    } else if (Build.VERSION.SDK_INT >= 26) {
      IMPL = new TextViewCompatApi26Impl();
    } else if (Build.VERSION.SDK_INT >= 23) {
      IMPL = new TextViewCompatApi23Impl();
    } else if (Build.VERSION.SDK_INT >= 18) {
      IMPL = new TextViewCompatApi18Impl();
    } else if (Build.VERSION.SDK_INT >= 17) {
      IMPL = new TextViewCompatApi17Impl();
    } else if (Build.VERSION.SDK_INT >= 16) {
      IMPL = new TextViewCompatApi16Impl();
    } else {
      IMPL = new TextViewCompatBaseImpl();
    } 
  }
  
  public static int getAutoSizeMaxTextSize(TextView paramTextView) {
    return IMPL.getAutoSizeMaxTextSize(paramTextView);
  }
  
  public static int getAutoSizeMinTextSize(TextView paramTextView) {
    return IMPL.getAutoSizeMinTextSize(paramTextView);
  }
  
  public static int getAutoSizeStepGranularity(TextView paramTextView) {
    return IMPL.getAutoSizeStepGranularity(paramTextView);
  }
  
  public static int[] getAutoSizeTextAvailableSizes(TextView paramTextView) {
    return IMPL.getAutoSizeTextAvailableSizes(paramTextView);
  }
  
  public static int getAutoSizeTextType(TextView paramTextView) {
    return IMPL.getAutoSizeTextType(paramTextView);
  }
  
  public static Drawable[] getCompoundDrawablesRelative(TextView paramTextView) {
    return IMPL.getCompoundDrawablesRelative(paramTextView);
  }
  
  public static int getMaxLines(TextView paramTextView) {
    return IMPL.getMaxLines(paramTextView);
  }
  
  public static int getMinLines(TextView paramTextView) {
    return IMPL.getMinLines(paramTextView);
  }
  
  public static void setAutoSizeTextTypeUniformWithConfiguration(TextView paramTextView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) throws IllegalArgumentException {
    IMPL.setAutoSizeTextTypeUniformWithConfiguration(paramTextView, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public static void setAutoSizeTextTypeUniformWithPresetSizes(TextView paramTextView, int[] paramArrayOfint, int paramInt) throws IllegalArgumentException {
    IMPL.setAutoSizeTextTypeUniformWithPresetSizes(paramTextView, paramArrayOfint, paramInt);
  }
  
  public static void setAutoSizeTextTypeWithDefaults(TextView paramTextView, int paramInt) {
    IMPL.setAutoSizeTextTypeWithDefaults(paramTextView, paramInt);
  }
  
  public static void setCompoundDrawablesRelative(TextView paramTextView, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4) {
    IMPL.setCompoundDrawablesRelative(paramTextView, paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
  }
  
  public static void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView paramTextView, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    IMPL.setCompoundDrawablesRelativeWithIntrinsicBounds(paramTextView, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public static void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView paramTextView, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3, Drawable paramDrawable4) {
    IMPL.setCompoundDrawablesRelativeWithIntrinsicBounds(paramTextView, paramDrawable1, paramDrawable2, paramDrawable3, paramDrawable4);
  }
  
  public static void setCustomSelectionActionModeCallback(TextView paramTextView, ActionMode.Callback paramCallback) {
    IMPL.setCustomSelectionActionModeCallback(paramTextView, paramCallback);
  }
  
  public static void setTextAppearance(TextView paramTextView, int paramInt) {
    IMPL.setTextAppearance(paramTextView, paramInt);
  }
  
  @Retention(RetentionPolicy.SOURCE)
  public static @interface AutoSizeTextType {}
  
  static class TextViewCompatApi16Impl extends TextViewCompatBaseImpl {
    public int getMaxLines(TextView param1TextView) {
      return param1TextView.getMaxLines();
    }
    
    public int getMinLines(TextView param1TextView) {
      return param1TextView.getMinLines();
    }
  }
  
  static class TextViewCompatApi17Impl extends TextViewCompatApi16Impl {
    public Drawable[] getCompoundDrawablesRelative(TextView param1TextView) {
      int i = param1TextView.getLayoutDirection();
      boolean bool = true;
      if (i != 1)
        bool = false; 
      Drawable[] arrayOfDrawable = param1TextView.getCompoundDrawables();
      if (bool) {
        Drawable drawable1 = arrayOfDrawable[2];
        Drawable drawable2 = arrayOfDrawable[0];
        arrayOfDrawable[0] = drawable1;
        arrayOfDrawable[2] = drawable2;
      } 
      return arrayOfDrawable;
    }
    
    public void setCompoundDrawablesRelative(TextView param1TextView, Drawable param1Drawable1, Drawable param1Drawable2, Drawable param1Drawable3, Drawable param1Drawable4) {
      Drawable drawable;
      int i = param1TextView.getLayoutDirection();
      boolean bool = true;
      if (i != 1)
        bool = false; 
      if (bool) {
        drawable = param1Drawable3;
      } else {
        drawable = param1Drawable1;
      } 
      if (!bool)
        param1Drawable1 = param1Drawable3; 
      param1TextView.setCompoundDrawables(drawable, param1Drawable2, param1Drawable1, param1Drawable4);
    }
    
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView param1TextView, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      int i = param1TextView.getLayoutDirection();
      boolean bool = true;
      if (i != 1)
        bool = false; 
      if (bool) {
        i = param1Int3;
      } else {
        i = param1Int1;
      } 
      if (!bool)
        param1Int1 = param1Int3; 
      param1TextView.setCompoundDrawablesWithIntrinsicBounds(i, param1Int2, param1Int1, param1Int4);
    }
    
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView param1TextView, Drawable param1Drawable1, Drawable param1Drawable2, Drawable param1Drawable3, Drawable param1Drawable4) {
      Drawable drawable;
      int i = param1TextView.getLayoutDirection();
      boolean bool = true;
      if (i != 1)
        bool = false; 
      if (bool) {
        drawable = param1Drawable3;
      } else {
        drawable = param1Drawable1;
      } 
      if (!bool)
        param1Drawable1 = param1Drawable3; 
      param1TextView.setCompoundDrawablesWithIntrinsicBounds(drawable, param1Drawable2, param1Drawable1, param1Drawable4);
    }
  }
  
  static class TextViewCompatApi18Impl extends TextViewCompatApi17Impl {
    public Drawable[] getCompoundDrawablesRelative(TextView param1TextView) {
      return param1TextView.getCompoundDrawablesRelative();
    }
    
    public void setCompoundDrawablesRelative(TextView param1TextView, Drawable param1Drawable1, Drawable param1Drawable2, Drawable param1Drawable3, Drawable param1Drawable4) {
      param1TextView.setCompoundDrawablesRelative(param1Drawable1, param1Drawable2, param1Drawable3, param1Drawable4);
    }
    
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView param1TextView, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      param1TextView.setCompoundDrawablesRelativeWithIntrinsicBounds(param1Int1, param1Int2, param1Int3, param1Int4);
    }
    
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView param1TextView, Drawable param1Drawable1, Drawable param1Drawable2, Drawable param1Drawable3, Drawable param1Drawable4) {
      param1TextView.setCompoundDrawablesRelativeWithIntrinsicBounds(param1Drawable1, param1Drawable2, param1Drawable3, param1Drawable4);
    }
  }
  
  static class TextViewCompatApi23Impl extends TextViewCompatApi18Impl {
    public void setTextAppearance(TextView param1TextView, int param1Int) {
      param1TextView.setTextAppearance(param1Int);
    }
  }
  
  static class TextViewCompatApi26Impl extends TextViewCompatApi23Impl {
    public void setCustomSelectionActionModeCallback(final TextView textView, final ActionMode.Callback callback) {
      if (Build.VERSION.SDK_INT != 26 && Build.VERSION.SDK_INT != 27) {
        super.setCustomSelectionActionModeCallback(textView, callback);
        return;
      } 
      textView.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            private static final int MENU_ITEM_ORDER_PROCESS_TEXT_INTENT_ACTIONS_START = 100;
            
            private boolean mCanUseMenuBuilderReferences;
            
            private boolean mInitializedMenuBuilderReferences = false;
            
            private Class mMenuBuilderClass;
            
            private Method mMenuBuilderRemoveItemAtMethod;
            
            private Intent createProcessTextIntent() {
              return (new Intent()).setAction("android.intent.action.PROCESS_TEXT").setType("text/plain");
            }
            
            private Intent createProcessTextIntentForResolveInfo(ResolveInfo param2ResolveInfo, TextView param2TextView) {
              return createProcessTextIntent().putExtra("android.intent.extra.PROCESS_TEXT_READONLY", isEditable(param2TextView) ^ true).setClassName(param2ResolveInfo.activityInfo.packageName, param2ResolveInfo.activityInfo.name);
            }
            
            private List<ResolveInfo> getSupportedActivities(Context param2Context, PackageManager param2PackageManager) {
              ArrayList<ResolveInfo> arrayList = new ArrayList();
              if (!(param2Context instanceof android.app.Activity))
                return arrayList; 
              for (ResolveInfo resolveInfo : param2PackageManager.queryIntentActivities(createProcessTextIntent(), 0)) {
                if (isSupportedActivity(resolveInfo, param2Context))
                  arrayList.add(resolveInfo); 
              } 
              return arrayList;
            }
            
            private boolean isEditable(TextView param2TextView) {
              boolean bool;
              if (param2TextView instanceof android.text.Editable && param2TextView.onCheckIsTextEditor() && param2TextView.isEnabled()) {
                bool = true;
              } else {
                bool = false;
              } 
              return bool;
            }
            
            private boolean isSupportedActivity(ResolveInfo param2ResolveInfo, Context param2Context) {
              boolean bool = param2Context.getPackageName().equals(param2ResolveInfo.activityInfo.packageName);
              boolean bool1 = true;
              if (bool)
                return true; 
              if (!param2ResolveInfo.activityInfo.exported)
                return false; 
              bool = bool1;
              if (param2ResolveInfo.activityInfo.permission != null)
                if (param2Context.checkSelfPermission(param2ResolveInfo.activityInfo.permission) == 0) {
                  bool = bool1;
                } else {
                  bool = false;
                }  
              return bool;
            }
            
            private void recomputeProcessTextMenuItems(Menu param2Menu) {
              Context context = textView.getContext();
              PackageManager packageManager = context.getPackageManager();
              if (!this.mInitializedMenuBuilderReferences) {
                this.mInitializedMenuBuilderReferences = true;
                try {
                  Class<?> clazz = Class.forName("com.android.internal.view.menu.MenuBuilder");
                  this.mMenuBuilderClass = clazz;
                  this.mMenuBuilderRemoveItemAtMethod = clazz.getDeclaredMethod("removeItemAt", new Class[] { int.class });
                  this.mCanUseMenuBuilderReferences = true;
                } catch (ClassNotFoundException|NoSuchMethodException classNotFoundException) {
                  this.mMenuBuilderClass = null;
                  this.mMenuBuilderRemoveItemAtMethod = null;
                  this.mCanUseMenuBuilderReferences = false;
                } 
              } 
              try {
                Method method;
                if (this.mCanUseMenuBuilderReferences && this.mMenuBuilderClass.isInstance(param2Menu)) {
                  method = this.mMenuBuilderRemoveItemAtMethod;
                } else {
                  method = param2Menu.getClass().getDeclaredMethod("removeItemAt", new Class[] { int.class });
                } 
                int i;
                for (i = param2Menu.size() - 1; i >= 0; i--) {
                  MenuItem menuItem = param2Menu.getItem(i);
                  if (menuItem.getIntent() != null && "android.intent.action.PROCESS_TEXT".equals(menuItem.getIntent().getAction()))
                    method.invoke(param2Menu, new Object[] { Integer.valueOf(i) }); 
                } 
                List<ResolveInfo> list = getSupportedActivities(context, packageManager);
                for (i = 0; i < list.size(); i++) {
                  ResolveInfo resolveInfo = list.get(i);
                  param2Menu.add(0, 0, i + 100, resolveInfo.loadLabel(packageManager)).setIntent(createProcessTextIntentForResolveInfo(resolveInfo, textView)).setShowAsAction(1);
                } 
              } catch (NoSuchMethodException|IllegalAccessException|java.lang.reflect.InvocationTargetException noSuchMethodException) {}
            }
            
            public boolean onActionItemClicked(ActionMode param2ActionMode, MenuItem param2MenuItem) {
              return callback.onActionItemClicked(param2ActionMode, param2MenuItem);
            }
            
            public boolean onCreateActionMode(ActionMode param2ActionMode, Menu param2Menu) {
              return callback.onCreateActionMode(param2ActionMode, param2Menu);
            }
            
            public void onDestroyActionMode(ActionMode param2ActionMode) {
              callback.onDestroyActionMode(param2ActionMode);
            }
            
            public boolean onPrepareActionMode(ActionMode param2ActionMode, Menu param2Menu) {
              recomputeProcessTextMenuItems(param2Menu);
              return callback.onPrepareActionMode(param2ActionMode, param2Menu);
            }
          });
    }
  }
  
  class null implements ActionMode.Callback {
    private static final int MENU_ITEM_ORDER_PROCESS_TEXT_INTENT_ACTIONS_START = 100;
    
    private boolean mCanUseMenuBuilderReferences;
    
    private boolean mInitializedMenuBuilderReferences = false;
    
    private Class mMenuBuilderClass;
    
    private Method mMenuBuilderRemoveItemAtMethod;
    
    private Intent createProcessTextIntent() {
      return (new Intent()).setAction("android.intent.action.PROCESS_TEXT").setType("text/plain");
    }
    
    private Intent createProcessTextIntentForResolveInfo(ResolveInfo param1ResolveInfo, TextView param1TextView) {
      return createProcessTextIntent().putExtra("android.intent.extra.PROCESS_TEXT_READONLY", isEditable(param1TextView) ^ true).setClassName(param1ResolveInfo.activityInfo.packageName, param1ResolveInfo.activityInfo.name);
    }
    
    private List<ResolveInfo> getSupportedActivities(Context param1Context, PackageManager param1PackageManager) {
      ArrayList<ResolveInfo> arrayList = new ArrayList();
      if (!(param1Context instanceof android.app.Activity))
        return arrayList; 
      for (ResolveInfo resolveInfo : param1PackageManager.queryIntentActivities(createProcessTextIntent(), 0)) {
        if (isSupportedActivity(resolveInfo, param1Context))
          arrayList.add(resolveInfo); 
      } 
      return arrayList;
    }
    
    private boolean isEditable(TextView param1TextView) {
      boolean bool;
      if (param1TextView instanceof android.text.Editable && param1TextView.onCheckIsTextEditor() && param1TextView.isEnabled()) {
        bool = true;
      } else {
        bool = false;
      } 
      return bool;
    }
    
    private boolean isSupportedActivity(ResolveInfo param1ResolveInfo, Context param1Context) {
      boolean bool = param1Context.getPackageName().equals(param1ResolveInfo.activityInfo.packageName);
      boolean bool1 = true;
      if (bool)
        return true; 
      if (!param1ResolveInfo.activityInfo.exported)
        return false; 
      bool = bool1;
      if (param1ResolveInfo.activityInfo.permission != null)
        if (param1Context.checkSelfPermission(param1ResolveInfo.activityInfo.permission) == 0) {
          bool = bool1;
        } else {
          bool = false;
        }  
      return bool;
    }
    
    private void recomputeProcessTextMenuItems(Menu param1Menu) {
      Context context = textView.getContext();
      PackageManager packageManager = context.getPackageManager();
      if (!this.mInitializedMenuBuilderReferences) {
        this.mInitializedMenuBuilderReferences = true;
        try {
          Class<?> clazz = Class.forName("com.android.internal.view.menu.MenuBuilder");
          this.mMenuBuilderClass = clazz;
          this.mMenuBuilderRemoveItemAtMethod = clazz.getDeclaredMethod("removeItemAt", new Class[] { int.class });
          this.mCanUseMenuBuilderReferences = true;
        } catch (ClassNotFoundException|NoSuchMethodException classNotFoundException) {
          this.mMenuBuilderClass = null;
          this.mMenuBuilderRemoveItemAtMethod = null;
          this.mCanUseMenuBuilderReferences = false;
        } 
      } 
      try {
        Method method;
        if (this.mCanUseMenuBuilderReferences && this.mMenuBuilderClass.isInstance(param1Menu)) {
          method = this.mMenuBuilderRemoveItemAtMethod;
        } else {
          method = param1Menu.getClass().getDeclaredMethod("removeItemAt", new Class[] { int.class });
        } 
        int i;
        for (i = param1Menu.size() - 1; i >= 0; i--) {
          MenuItem menuItem = param1Menu.getItem(i);
          if (menuItem.getIntent() != null && "android.intent.action.PROCESS_TEXT".equals(menuItem.getIntent().getAction()))
            method.invoke(param1Menu, new Object[] { Integer.valueOf(i) }); 
        } 
        List<ResolveInfo> list = getSupportedActivities(context, packageManager);
        for (i = 0; i < list.size(); i++) {
          ResolveInfo resolveInfo = list.get(i);
          param1Menu.add(0, 0, i + 100, resolveInfo.loadLabel(packageManager)).setIntent(createProcessTextIntentForResolveInfo(resolveInfo, textView)).setShowAsAction(1);
        } 
      } catch (NoSuchMethodException|IllegalAccessException|java.lang.reflect.InvocationTargetException noSuchMethodException) {}
    }
    
    public boolean onActionItemClicked(ActionMode param1ActionMode, MenuItem param1MenuItem) {
      return callback.onActionItemClicked(param1ActionMode, param1MenuItem);
    }
    
    public boolean onCreateActionMode(ActionMode param1ActionMode, Menu param1Menu) {
      return callback.onCreateActionMode(param1ActionMode, param1Menu);
    }
    
    public void onDestroyActionMode(ActionMode param1ActionMode) {
      callback.onDestroyActionMode(param1ActionMode);
    }
    
    public boolean onPrepareActionMode(ActionMode param1ActionMode, Menu param1Menu) {
      recomputeProcessTextMenuItems(param1Menu);
      return callback.onPrepareActionMode(param1ActionMode, param1Menu);
    }
  }
  
  static class TextViewCompatApi27Impl extends TextViewCompatApi26Impl {
    public int getAutoSizeMaxTextSize(TextView param1TextView) {
      return param1TextView.getAutoSizeMaxTextSize();
    }
    
    public int getAutoSizeMinTextSize(TextView param1TextView) {
      return param1TextView.getAutoSizeMinTextSize();
    }
    
    public int getAutoSizeStepGranularity(TextView param1TextView) {
      return param1TextView.getAutoSizeStepGranularity();
    }
    
    public int[] getAutoSizeTextAvailableSizes(TextView param1TextView) {
      return param1TextView.getAutoSizeTextAvailableSizes();
    }
    
    public int getAutoSizeTextType(TextView param1TextView) {
      return param1TextView.getAutoSizeTextType();
    }
    
    public void setAutoSizeTextTypeUniformWithConfiguration(TextView param1TextView, int param1Int1, int param1Int2, int param1Int3, int param1Int4) throws IllegalArgumentException {
      param1TextView.setAutoSizeTextTypeUniformWithConfiguration(param1Int1, param1Int2, param1Int3, param1Int4);
    }
    
    public void setAutoSizeTextTypeUniformWithPresetSizes(TextView param1TextView, int[] param1ArrayOfint, int param1Int) throws IllegalArgumentException {
      param1TextView.setAutoSizeTextTypeUniformWithPresetSizes(param1ArrayOfint, param1Int);
    }
    
    public void setAutoSizeTextTypeWithDefaults(TextView param1TextView, int param1Int) {
      param1TextView.setAutoSizeTextTypeWithDefaults(param1Int);
    }
  }
  
  static class TextViewCompatBaseImpl {
    private static final int LINES = 1;
    
    private static final String LOG_TAG = "TextViewCompatBase";
    
    private static Field sMaxModeField;
    
    private static boolean sMaxModeFieldFetched;
    
    private static Field sMaximumField;
    
    private static boolean sMaximumFieldFetched;
    
    private static Field sMinModeField;
    
    private static boolean sMinModeFieldFetched;
    
    private static Field sMinimumField;
    
    private static boolean sMinimumFieldFetched;
    
    private static Field retrieveField(String param1String) {
      Field field = null;
      try {
        Field field1 = TextView.class.getDeclaredField(param1String);
        field = field1;
        field1.setAccessible(true);
        field = field1;
      } catch (NoSuchFieldException noSuchFieldException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not retrieve ");
        stringBuilder.append(param1String);
        stringBuilder.append(" field.");
        Log.e("TextViewCompatBase", stringBuilder.toString());
      } 
      return field;
    }
    
    private static int retrieveIntFromField(Field param1Field, TextView param1TextView) {
      try {
        return param1Field.getInt(param1TextView);
      } catch (IllegalAccessException illegalAccessException) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not retrieve value of ");
        stringBuilder.append(param1Field.getName());
        stringBuilder.append(" field.");
        Log.d("TextViewCompatBase", stringBuilder.toString());
        return -1;
      } 
    }
    
    public int getAutoSizeMaxTextSize(TextView param1TextView) {
      return (param1TextView instanceof AutoSizeableTextView) ? ((AutoSizeableTextView)param1TextView).getAutoSizeMaxTextSize() : -1;
    }
    
    public int getAutoSizeMinTextSize(TextView param1TextView) {
      return (param1TextView instanceof AutoSizeableTextView) ? ((AutoSizeableTextView)param1TextView).getAutoSizeMinTextSize() : -1;
    }
    
    public int getAutoSizeStepGranularity(TextView param1TextView) {
      return (param1TextView instanceof AutoSizeableTextView) ? ((AutoSizeableTextView)param1TextView).getAutoSizeStepGranularity() : -1;
    }
    
    public int[] getAutoSizeTextAvailableSizes(TextView param1TextView) {
      return (param1TextView instanceof AutoSizeableTextView) ? ((AutoSizeableTextView)param1TextView).getAutoSizeTextAvailableSizes() : new int[0];
    }
    
    public int getAutoSizeTextType(TextView param1TextView) {
      return (param1TextView instanceof AutoSizeableTextView) ? ((AutoSizeableTextView)param1TextView).getAutoSizeTextType() : 0;
    }
    
    public Drawable[] getCompoundDrawablesRelative(TextView param1TextView) {
      return param1TextView.getCompoundDrawables();
    }
    
    public int getMaxLines(TextView param1TextView) {
      if (!sMaxModeFieldFetched) {
        sMaxModeField = retrieveField("mMaxMode");
        sMaxModeFieldFetched = true;
      } 
      Field field = sMaxModeField;
      if (field != null && retrieveIntFromField(field, param1TextView) == 1) {
        if (!sMaximumFieldFetched) {
          sMaximumField = retrieveField("mMaximum");
          sMaximumFieldFetched = true;
        } 
        field = sMaximumField;
        if (field != null)
          return retrieveIntFromField(field, param1TextView); 
      } 
      return -1;
    }
    
    public int getMinLines(TextView param1TextView) {
      if (!sMinModeFieldFetched) {
        sMinModeField = retrieveField("mMinMode");
        sMinModeFieldFetched = true;
      } 
      Field field = sMinModeField;
      if (field != null && retrieveIntFromField(field, param1TextView) == 1) {
        if (!sMinimumFieldFetched) {
          sMinimumField = retrieveField("mMinimum");
          sMinimumFieldFetched = true;
        } 
        field = sMinimumField;
        if (field != null)
          return retrieveIntFromField(field, param1TextView); 
      } 
      return -1;
    }
    
    public void setAutoSizeTextTypeUniformWithConfiguration(TextView param1TextView, int param1Int1, int param1Int2, int param1Int3, int param1Int4) throws IllegalArgumentException {
      if (param1TextView instanceof AutoSizeableTextView)
        ((AutoSizeableTextView)param1TextView).setAutoSizeTextTypeUniformWithConfiguration(param1Int1, param1Int2, param1Int3, param1Int4); 
    }
    
    public void setAutoSizeTextTypeUniformWithPresetSizes(TextView param1TextView, int[] param1ArrayOfint, int param1Int) throws IllegalArgumentException {
      if (param1TextView instanceof AutoSizeableTextView)
        ((AutoSizeableTextView)param1TextView).setAutoSizeTextTypeUniformWithPresetSizes(param1ArrayOfint, param1Int); 
    }
    
    public void setAutoSizeTextTypeWithDefaults(TextView param1TextView, int param1Int) {
      if (param1TextView instanceof AutoSizeableTextView)
        ((AutoSizeableTextView)param1TextView).setAutoSizeTextTypeWithDefaults(param1Int); 
    }
    
    public void setCompoundDrawablesRelative(TextView param1TextView, Drawable param1Drawable1, Drawable param1Drawable2, Drawable param1Drawable3, Drawable param1Drawable4) {
      param1TextView.setCompoundDrawables(param1Drawable1, param1Drawable2, param1Drawable3, param1Drawable4);
    }
    
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView param1TextView, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
      param1TextView.setCompoundDrawablesWithIntrinsicBounds(param1Int1, param1Int2, param1Int3, param1Int4);
    }
    
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(TextView param1TextView, Drawable param1Drawable1, Drawable param1Drawable2, Drawable param1Drawable3, Drawable param1Drawable4) {
      param1TextView.setCompoundDrawablesWithIntrinsicBounds(param1Drawable1, param1Drawable2, param1Drawable3, param1Drawable4);
    }
    
    public void setCustomSelectionActionModeCallback(TextView param1TextView, ActionMode.Callback param1Callback) {
      param1TextView.setCustomSelectionActionModeCallback(param1Callback);
    }
    
    public void setTextAppearance(TextView param1TextView, int param1Int) {
      param1TextView.setTextAppearance(param1TextView.getContext(), param1Int);
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\android\support\v4\widget\TextViewCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */