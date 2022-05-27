package org.jdeferred.android;

public enum AndroidExecutionScope {
  BACKGROUND, UI;
  
  static {
    AndroidExecutionScope androidExecutionScope = new AndroidExecutionScope("UI", 1);
    UI = androidExecutionScope;
    $VALUES = new AndroidExecutionScope[] { BACKGROUND, androidExecutionScope };
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\org\jdeferred\android\AndroidExecutionScope.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */