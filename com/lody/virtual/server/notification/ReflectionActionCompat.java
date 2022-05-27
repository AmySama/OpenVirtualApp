package com.lody.virtual.server.notification;

import android.widget.RemoteViews;

class ReflectionActionCompat {
  static final int BITMAP = 12;
  
  static final int BOOLEAN = 1;
  
  static final int BUNDLE = 13;
  
  static final int BYTE = 2;
  
  static final int CHAR = 8;
  
  static final int CHAR_SEQUENCE = 10;
  
  static final int COLOR_STATE_LIST = 15;
  
  static final int DOUBLE = 7;
  
  static final int FLOAT = 6;
  
  static final int ICON = 16;
  
  static final int INT = 4;
  
  static final int INTENT = 14;
  
  static final int LONG = 5;
  
  private static final String ReflectionAction = "ReflectionAction";
  
  private static Class ReflectionActionClass;
  
  static final int SHORT = 3;
  
  static final int STRING = 9;
  
  static final int TAG = 2;
  
  static final int URI = 11;
  
  static {
    try {
      StringBuilder stringBuilder = new StringBuilder();
      this();
      stringBuilder.append(RemoteViews.class.getName());
      stringBuilder.append("$");
      stringBuilder.append("ReflectionAction");
      ReflectionActionClass = Class.forName(stringBuilder.toString());
    } catch (ClassNotFoundException classNotFoundException) {}
  }
  
  static boolean isInstance(Object paramObject) {
    boolean bool;
    Class clazz = ReflectionActionClass;
    if (clazz != null && clazz.isInstance(paramObject)) {
      bool = true;
    } else {
      bool = false;
    } 
    return bool;
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\notification\ReflectionActionCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */