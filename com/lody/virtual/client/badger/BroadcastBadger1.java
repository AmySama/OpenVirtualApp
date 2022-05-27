package com.lody.virtual.client.badger;

import android.content.Intent;
import com.lody.virtual.remote.BadgerInfo;

public abstract class BroadcastBadger1 implements IBadger {
  public abstract String getAction();
  
  public abstract String getClassNameKey();
  
  public abstract String getCountKey();
  
  public abstract String getPackageKey();
  
  public BadgerInfo handleBadger(Intent paramIntent) {
    BadgerInfo badgerInfo = new BadgerInfo();
    badgerInfo.packageName = paramIntent.getStringExtra(getPackageKey());
    if (getClassNameKey() != null)
      badgerInfo.className = paramIntent.getStringExtra(getClassNameKey()); 
    badgerInfo.badgerCount = paramIntent.getIntExtra(getCountKey(), 0);
    return badgerInfo;
  }
  
  static class AdwHomeBadger extends BroadcastBadger1 {
    public String getAction() {
      return "org.adw.launcher.counter.SEND";
    }
    
    public String getClassNameKey() {
      return "CNAME";
    }
    
    public String getCountKey() {
      return "COUNT";
    }
    
    public String getPackageKey() {
      return "PNAME";
    }
  }
  
  static class AospHomeBadger extends BroadcastBadger1 {
    public String getAction() {
      return "android.intent.action.BADGE_COUNT_UPDATE";
    }
    
    public String getClassNameKey() {
      return "badge_count_class_name";
    }
    
    public String getCountKey() {
      return "badge_count";
    }
    
    public String getPackageKey() {
      return "badge_count_package_name";
    }
  }
  
  static class LGHomeBadger extends BroadcastBadger1 {
    public String getAction() {
      return "android.intent.action.BADGE_COUNT_UPDATE";
    }
    
    public String getClassNameKey() {
      return "badge_count_class_name";
    }
    
    public String getCountKey() {
      return "badge_count";
    }
    
    public String getPackageKey() {
      return "badge_count_package_name";
    }
  }
  
  static class NewHtcHomeBadger2 extends BroadcastBadger1 {
    public String getAction() {
      return "com.htc.launcher.action.UPDATE_SHORTCUT";
    }
    
    public String getClassNameKey() {
      return null;
    }
    
    public String getCountKey() {
      return "count";
    }
    
    public String getPackageKey() {
      return "packagename";
    }
  }
  
  static class OPPOHomeBader extends BroadcastBadger1 {
    public String getAction() {
      return "com.oppo.unsettledevent";
    }
    
    public String getClassNameKey() {
      return null;
    }
    
    public String getCountKey() {
      return "number";
    }
    
    public String getPackageKey() {
      return "pakeageName";
    }
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\client\badger\BroadcastBadger1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */