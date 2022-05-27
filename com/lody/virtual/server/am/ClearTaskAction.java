package com.lody.virtual.server.am;

public enum ClearTaskAction {
  ACTIVITY, NONE, TASK, TOP;
  
  static {
    ACTIVITY = new ClearTaskAction("ACTIVITY", 2);
    ClearTaskAction clearTaskAction = new ClearTaskAction("TOP", 3);
    TOP = clearTaskAction;
    $VALUES = new ClearTaskAction[] { NONE, TASK, ACTIVITY, clearTaskAction };
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\am\ClearTaskAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */