package com.lody.virtual.client.badger;

import android.content.ComponentName;
import android.content.Intent;
import com.lody.virtual.remote.BadgerInfo;

public abstract class BroadcastBadger2 implements IBadger {
  public abstract String getAction();
  
  public abstract String getComponentKey();
  
  public abstract String getCountKey();
  
  public BadgerInfo handleBadger(Intent paramIntent) {
    BadgerInfo badgerInfo = new BadgerInfo();
    ComponentName componentName = ComponentName.unflattenFromString(paramIntent.getStringExtra(getComponentKey()));
    if (componentName != null) {
      badgerInfo.packageName = componentName.getPackageName();
      badgerInfo.className = componentName.getClassName();
      badgerInfo.badgerCount = paramIntent.getIntExtra(getCountKey(), 0);
      return badgerInfo;
    } 
    return null;
  }
  
  static class NewHtcHomeBadger1 extends BroadcastBadger2 {
    public String getAction() {
      return "com.htc.launcher.action.SET_NOTIFICATION";
    }
    
    public String getComponentKey() {
      return "com.htc.launcher.extra.COMPONENT";
    }
    
    public String getCountKey() {
      return "com.htc.launcher.extra.COUNT";
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\badger\BroadcastBadger2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */